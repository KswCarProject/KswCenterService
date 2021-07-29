package com.wits.pms.core;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimedRemoteCaller;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.custom.KswStatusHandler;
import com.wits.pms.custom.WitsMemoryController;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.listener.CenterListener;
import com.wits.pms.mcu.McuService;
import com.wits.pms.mcu.custom.KswMcuLogic;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.utils.ABOTAUpdate;
import com.wits.pms.mcu.custom.utils.AccLight;
import com.wits.pms.mcu.custom.utils.ForceMcuUpdate;
import com.wits.pms.mcu.custom.utils.OTAUpdate;
import com.wits.pms.mcu.custom.utils.SplashFlasher;
import com.wits.pms.mcu.custom.utils.SymlinkHelper;
import com.wits.pms.mcu.custom.utils.TpUpdate;
import com.wits.pms.mcu.custom.utils.WitsAutoLog;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.McuUpdater;
import com.wits.pms.utils.ServiceManager;
import com.wits.pms.utils.SystemProperties;
import java.util.ArrayList;

public class PowerManagerAppService extends Service {
    /* access modifiers changed from: private */
    public static final String TAG = PowerManagerAppService.class.getName();
    public static Context serviceContext;
    /* access modifiers changed from: private */
    public int checkCount = 0;
    /* access modifiers changed from: private */
    public Runnable initCarplay = new Runnable() {
        public void run() {
            if (Settings.System.getInt(PowerManagerAppService.this.getContentResolver(), "wits_firstTime_boot", 0) != 0) {
                String carplay = KswSettings.getSettings().getSettingsInt("speed_play_switch", 1) + "";
                Log.d(PowerManagerAppService.TAG, "01 set sys.carplay.start  " + carplay);
                SystemProperties.set("sys.carplay.start", carplay);
                return;
            }
            Log.d(PowerManagerAppService.TAG, "01 set sys.carplay.start  delay ");
            PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.initCarplay, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        }
    };
    /* access modifiers changed from: private */
    public Runnable installAPK = new Runnable() {
        public void run() {
            Log.d(PowerManagerAppService.TAG, "firstboot Runnable ");
            if (TextUtils.isEmpty(SystemStatusControl.getStatus().topApp) || !SystemProperties.get("sys.boot_completed").equals("1")) {
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
            } else if (SystemProperties.get("vendor.wits.firstboot").equals("1")) {
                String str_num = SystemProperties.get("vendor.wits.installnum");
                String str_progress = SystemProperties.get("vendor.wits.installprocess");
                int int_progress = 0;
                if (!TextUtils.isEmpty(str_progress)) {
                    int_progress = (Integer.parseInt(str_progress) * 100) / Integer.parseInt(str_num);
                }
                String access$000 = PowerManagerAppService.TAG;
                Log.d(access$000, "firstboot  installing  app   " + int_progress);
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                WitsCommand.sendCommand(1, 126, "" + int_progress);
            } else if (SystemProperties.get("vendor.wits.firstboot").equals("0")) {
                Log.d(PowerManagerAppService.TAG, "firstboot  finish install ");
                Settings.System.putInt(PowerManagerAppService.this.getContentResolver(), "wits_firstTime_boot", 1);
                WitsCommand.sendCommand(1, 127, "");
            } else {
                Log.d(PowerManagerAppService.TAG, "firstboot  don't install ");
                if (PowerManagerAppService.this.checkCount < 20) {
                    PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                    PowerManagerAppService.access$308(PowerManagerAppService.this);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable kswDefaultLauncherRunnable = new Runnable() {
        public void run() {
            if (SystemProperties.get("vendor.wits.firstboot").equals("1")) {
                PackageManager mPm = PowerManagerAppService.this.getPackageManager();
                ArrayList<ResolveInfo> homeActivities = new ArrayList<>();
                if (mPm.getLaunchIntentForPackage("com.wits.ksw") != null) {
                    Log.d(PowerManagerAppService.TAG, "kswDefaultLauncherRunnable   hasActivity");
                    ComponentName DefaultLauncher = new ComponentName("com.wits.ksw", "com.wits.ksw.MainActivity");
                    mPm.getHomeActivities(homeActivities);
                    String access$000 = PowerManagerAppService.TAG;
                    Log.d(access$000, "homeActivities = " + homeActivities);
                    ComponentName[] mHomeComponentSet = new ComponentName[homeActivities.size()];
                    for (int i = 0; i < homeActivities.size(); i++) {
                        ActivityInfo info = homeActivities.get(i).activityInfo;
                        mHomeComponentSet[i] = new ComponentName(info.packageName, info.name);
                    }
                    IntentFilter mHomeFilter = new IntentFilter(Intent.ACTION_MAIN);
                    mHomeFilter.addCategory(Intent.CATEGORY_HOME);
                    mHomeFilter.addCategory(Intent.CATEGORY_DEFAULT);
                    new ArrayList();
                    mPm.replacePreferredActivity(mHomeFilter, 1048576, mHomeComponentSet, DefaultLauncher);
                    return;
                }
                Log.d(PowerManagerAppService.TAG, "kswDefaultLauncherRunnable  dont  hasActivity");
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.kswDefaultLauncherRunnable, 500);
            }
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler;
    private PowerManagerImpl mPmasBinder;
    boolean startBt;

    static /* synthetic */ int access$308(PowerManagerAppService x0) {
        int i = x0.checkCount;
        x0.checkCount = i + 1;
        return i;
    }

    public void onCreate() {
        super.onCreate();
        AccLight.show(1500);
        serviceContext = this;
        this.mPmasBinder = new PowerManagerImpl(this);
        ServiceManager.addService("wits_pms", this.mPmasBinder);
        CenterControlImpl.init(this);
        initMcuService();
        ServiceManager.addService("wits_key", new SystemKeyService(this));
        this.mHandler = new Handler();
        setupUsbMode();
        registerMountBroadCast();
        initStatusObs();
        problemFix();
        boot();
    }

    private void kswMaxVol() {
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        audioManager.setStreamVolume(3, 15, 8);
        audioManager.setStreamVolume(0, 5, 8);
        audioManager.setStreamVolume(4, 7, 8);
        audioManager.setStreamVolume(2, 7, 8);
    }

    @RequiresApi(api = 24)
    private void boot() {
        KswSettings.init(this);
        boolean isFirst_wits = true;
        Settings.System.putInt(getContentResolver(), "bootTimes", Settings.System.getInt(getContentResolver(), "bootTimes", 0) + 1);
        Intent intent = new Intent("com.wits.boot.Start");
        intent.addFlags(16777248);
        sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(getApplicationInfo().uid));
        boolean isFirst = Settings.System.getInt(getContentResolver(), "firstTime_boot", 0) == 0;
        if (Settings.System.getInt(getContentResolver(), "wits_firstTime_boot", 0) != 0) {
            isFirst_wits = false;
        }
        kswMaxVol();
        this.mHandler.postDelayed(new Runnable(isFirst) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                PowerManagerAppService.lambda$boot$0(PowerManagerAppService.this, this.f$1);
            }
        }, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        Settings.System.putInt(getContentResolver(), "wits_key", 0);
        Settings.System.putInt(getContentResolver(), "wits_call", 0);
        if (isFirst_wits) {
            this.mHandler.postDelayed(this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        }
        if (Build.VERSION.RELEASE.contains("11")) {
            this.mHandler.postDelayed(this.kswDefaultLauncherRunnable, 1000);
        }
    }

    public static /* synthetic */ void lambda$boot$0(PowerManagerAppService powerManagerAppService, boolean isFirst) {
        if (isFirst) {
            OTAUpdate.reinstallApk();
            Settings.System.putInt(powerManagerAppService.getContentResolver(), "firstTime_boot", 1);
        }
    }

    private void registerMountBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme(ContentResolver.SCHEME_FILE);
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
                    String path = intent.getData().getPath();
                    String access$000 = PowerManagerAppService.TAG;
                    Log.d(access$000, "OTA CHECK PATH: " + path);
                    if (!TextUtils.isEmpty(path)) {
                        if (Build.VERSION.RELEASE.equals("11")) {
                            ABOTAUpdate.checkFile(PowerManagerAppService.this, path);
                        } else {
                            OTAUpdate.checkFile(PowerManagerAppService.this, path);
                        }
                        SplashFlasher.check(PowerManagerAppService.this, path);
                        ForceMcuUpdate.check(PowerManagerAppService.this, path);
                        KswSettings.getSettings().check(PowerManagerAppService.this, path);
                        McuUpdater.check(PowerManagerAppService.this, path);
                        WitsAutoLog.checkFile(PowerManagerAppService.this, path);
                        TpUpdate.check(PowerManagerAppService.this, path);
                        if (!intent.getExtras().getBoolean("isUsb")) {
                            SymlinkHelper.setUp(path);
                        }
                    }
                    path.contains("/storage/emulated");
                } else if (Intent.ACTION_MEDIA_UNMOUNTED.equals(intent.getAction()) && !intent.getExtras().getBoolean("isUsb")) {
                    SymlinkHelper.cleanUp();
                }
            }
        }, intentFilter);
    }

    private void problemFix() {
        Settings.System.putInt(getContentResolver(), "btSwitch", 1);
        Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 1);
        SystemStatusControl.getDefault().boot(this.mPmasBinder);
        if (Settings.System.getInt(getContentResolver(), "firstTime_boot", 0) == 0) {
            this.mHandler.post(this.initCarplay);
            return;
        }
        SystemProperties.set("sys.carplay.start", KswSettings.getSettings().getSettingsInt("speed_play_switch", 1) + "");
    }

    private void setupUsbMode() {
    }

    private void initStatusObs() {
        LogicSystem logicSystem = new KswStatusHandler();
        CenterControlImpl.getImpl().setLogic(logicSystem);
        new CenterListener(this, logicSystem).setMemoryKiller(new WitsMemoryController(this));
    }

    private void initMcuService() {
        startService(new Intent((Context) this, (Class<?>) McuService.class));
        KswMcuLogic.init(this);
        KswMcuSender.getSender().sendMessage(97, new byte[]{0});
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
