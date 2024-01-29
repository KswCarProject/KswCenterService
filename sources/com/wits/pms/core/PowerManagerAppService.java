package com.wits.pms.core;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.PackageParser;
import android.content.p002pm.ResolveInfo;
import android.media.AudioManager;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Message;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimedRemoteCaller;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.bean.APKInfo;
import com.wits.pms.custom.CallBackServiceImpl;
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
import com.wits.pms.mirror.ServiceManager;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.McuUpdater;
import com.wits.pms.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class PowerManagerAppService extends Service {
    private static final int MSG_UPDATE_PROGRESS = 0;
    private static final int MSG_UPDATE_SUCCESS = 1;
    private static final String TAG = PowerManagerAppService.class.getName();
    private static final String WITS_FIRSTTIME_UPDATEAPK = "wits_firstTime_updateApk";
    public static Context serviceContext;
    private AlertDialog installAppDialog;
    private CallBackServiceImpl mCallBackBinder;
    private Handler mHandler;
    private PowerManagerImpl mPmasBinder;
    private SilentPackageInstaller mSilentPackageInstaller;
    private ProgressBar progressBar;
    private int start360ErrorCount;
    boolean startBt;
    private List<String> updateApks;
    private int count = 0;
    private boolean IS_HAVE_OPEN_360_APP = false;
    private Runnable checkAndroidverison = new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.2
        @Override // java.lang.Runnable
        public void run() {
            Exception e;
            File file = new File("/mnt/vendor/persist/OEM/ksw_android11");
            if (!file.exists()) {
                return;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String targetVersion = "";
                while (true) {
                    try {
                        String temp = bufferedReader.readLine();
                        if (temp == null) {
                            break;
                        }
                        targetVersion = targetVersion + temp;
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        return;
                    }
                }
                bufferedReader.close();
                Log.m68i(PowerManagerAppService.TAG, "checkAndroidverison: version value = " + targetVersion);
                if (targetVersion != null && !targetVersion.isEmpty() && targetVersion.matches("[0-9]{1,}")) {
                    SystemProperties.set("ksw_android11", targetVersion);
                    return;
                }
                Log.m70e(PowerManagerAppService.TAG, "is null or empty or is not all digit");
            } catch (Exception e3) {
                e = e3;
            }
        }
    };
    private int checkCount = 0;
    private Runnable installAPK = new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.3
        @Override // java.lang.Runnable
        public void run() {
            Log.m72d(PowerManagerAppService.TAG, "firstboot Runnable ");
            if (TextUtils.isEmpty(SystemStatusControl.getStatus().topApp) || !SystemProperties.get("sys.boot_completed").equals("1")) {
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
            } else if (!SystemProperties.get("vendor.wits.firstboot").equals("1")) {
                if (SystemProperties.get("vendor.wits.firstboot").equals("0")) {
                    Log.m72d(PowerManagerAppService.TAG, "firstboot  finish install ");
                    Settings.System.putInt(PowerManagerAppService.this.getContentResolver(), "wits_firstTime_boot", 1);
                    WitsCommand.sendCommand(1, 127, "");
                    return;
                }
                Log.m72d(PowerManagerAppService.TAG, "firstboot  don't install ");
                if (PowerManagerAppService.this.checkCount < 20) {
                    PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                    PowerManagerAppService.access$508(PowerManagerAppService.this);
                }
            } else {
                String str_num = SystemProperties.get("vendor.wits.installnum");
                String str_progress = SystemProperties.get("vendor.wits.installprocess");
                int int_progress = 0;
                if (!TextUtils.isEmpty(str_progress)) {
                    int num = Integer.parseInt(str_num);
                    int_progress = (Integer.parseInt(str_progress) * 100) / num;
                }
                String str = PowerManagerAppService.TAG;
                Log.m72d(str, "firstboot  installing  app   " + int_progress);
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                WitsCommand.sendCommand(1, 126, "" + int_progress);
            }
        }
    };
    private Runnable kswDefaultLauncherRunnable = new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.4
        @Override // java.lang.Runnable
        public void run() {
            if (Settings.System.getInt(PowerManagerAppService.this.getContentResolver(), "wits_firstTime_boot", 0) != 0) {
                Log.m72d(PowerManagerAppService.TAG, "kswDefaultLauncherRunnable:  not first boot return");
                return;
            }
            PackageManager mPm = PowerManagerAppService.this.getPackageManager();
            ArrayList<ResolveInfo> homeActivities = new ArrayList<>();
            if (mPm.getLaunchIntentForPackage("com.wits.ksw") != null) {
                Log.m72d(PowerManagerAppService.TAG, "kswDefaultLauncherRunnable   hasActivity");
                ComponentName DefaultLauncher = new ComponentName("com.wits.ksw", "com.wits.ksw.MainActivity");
                ComponentName currentDefaultHome = mPm.getHomeActivities(homeActivities);
                String str = PowerManagerAppService.TAG;
                Log.m72d(str, "homeActivities = " + homeActivities + "   currentDefaultHome =" + currentDefaultHome);
                ComponentName[] mHomeComponentSet = new ComponentName[homeActivities.size()];
                for (int i = 0; i < homeActivities.size(); i++) {
                    ResolveInfo candidate = homeActivities.get(i);
                    ActivityInfo info = candidate.activityInfo;
                    ComponentName activityName = new ComponentName(info.packageName, info.name);
                    mHomeComponentSet[i] = activityName;
                }
                IntentFilter mHomeFilter = new IntentFilter(Intent.ACTION_MAIN);
                mHomeFilter.addCategory(Intent.CATEGORY_HOME);
                mHomeFilter.addCategory(Intent.CATEGORY_DEFAULT);
                new ArrayList();
                mPm.replacePreferredActivity(mHomeFilter, 1048576, mHomeComponentSet, DefaultLauncher);
                return;
            }
            Log.m72d(PowerManagerAppService.TAG, "kswDefaultLauncherRunnable  dont  hasActivity");
            PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.kswDefaultLauncherRunnable, 500L);
        }
    };
    private Runnable initCarplay = new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.6
        @Override // java.lang.Runnable
        public void run() {
            if (Settings.System.getInt(PowerManagerAppService.this.getContentResolver(), "wits_firstTime_boot", 0) == 0) {
                Log.m72d(PowerManagerAppService.TAG, "01 set sys.carplay.start  delay ");
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.initCarplay, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                return;
            }
            String carplay = KswSettings.getSettings().getSettingsInt("speed_play_switch", 1) + "";
            Log.m72d(PowerManagerAppService.TAG, "01 set sys.carplay.start  " + carplay);
            SystemProperties.set("sys.carplay.start", carplay);
        }
    };
    private Runnable updateAPKRun = new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.7
        @Override // java.lang.Runnable
        public void run() {
            if (TextUtils.isEmpty(SystemStatusControl.getStatus().topApp)) {
                PowerManagerAppService.this.mHandler.postDelayed(PowerManagerAppService.this.updateAPKRun, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
            } else if (!TextUtils.equals(SystemProperties.get("persist.wits.ota"), "true")) {
                Log.m72d(PowerManagerAppService.TAG, "updateAPKRun: not need update apks");
            } else if (Build.VERSION.SDK_INT >= 28) {
                PowerManagerAppService.this.createProgressDialog(PowerManagerAppService.serviceContext);
                PowerManagerAppService.this.updateAPK();
            }
        }
    };

    static /* synthetic */ int access$508(PowerManagerAppService x0) {
        int i = x0.checkCount;
        x0.checkCount = i + 1;
        return i;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        AccLight.show(1500L);
        serviceContext = this;
        this.mPmasBinder = new PowerManagerImpl(this);
        this.mCallBackBinder = CallBackServiceImpl.getCallBackServiceImpl();
        ServiceManager.addService("wits_pms", this.mPmasBinder);
        ServiceManager.addService("wits_callback", this.mCallBackBinder);
        CenterControlImpl.init(this);
        initMcuService();
        ServiceManager.addService("wits_key", new SystemKeyService(this));
        this.mHandler = new Handler(serviceContext.getMainLooper()) { // from class: com.wits.pms.core.PowerManagerAppService.1
            @Override // android.p007os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        PowerManagerAppService.this.progressBar.setProgress(msg.arg1);
                        return;
                    case 1:
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (PowerManagerAppService.this.installAppDialog != null && PowerManagerAppService.this.installAppDialog.isShowing()) {
                            PowerManagerAppService.this.installAppDialog.dismiss();
                        }
                        Toast.makeText(PowerManagerAppService.serviceContext, PowerManagerAppService.serviceContext.getString(C3580R.string.app_update_complete), 1).show();
                        return;
                    default:
                        return;
                }
            }
        };
        setupUsbMode();
        registerMountBroadCast();
        initStatusObs();
        problemFix();
        boot();
    }

    private void start360App() {
        if (this.start360ErrorCount > 3) {
            return;
        }
        try {
            if (!Utils.isAppPakExist(this, "com.ivicar.avm")) {
                PowerManagerApp.setBooleanStatus("show_360_on_boot", false);
                return;
            }
            Log.m68i(TAG, "start360APP");
            int groupValue = PowerManagerApp.getSettingsInt("RearCamType");
            boolean isClose360OnStart = PowerManagerApp.getSettingsInt("close_360_on_boot") == 1;
            String str = TAG;
            Log.m68i(str, "start360APP:" + groupValue + ";" + isClose360OnStart);
            if (groupValue != 3) {
                PowerManagerApp.setBooleanStatus("show_360_on_boot", false);
                return;
            }
            PowerManagerApp.setBooleanStatus("show_360_on_boot", isClose360OnStart ? false : true);
            if (!isClose360OnStart) {
                PowerManagerApp.setBooleanStatus("googleDisableBoot", true);
                Utils.setApplicationEnabledSetting(this, 3);
            }
        } catch (Exception e) {
            String str2 = TAG;
            Log.m68i(str2, "start360APP error:" + e.getMessage());
            this.start360ErrorCount = this.start360ErrorCount + 1;
            start360App();
        }
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
        if (!this.IS_HAVE_OPEN_360_APP) {
            this.IS_HAVE_OPEN_360_APP = true;
            start360App();
        }
        KswSettings.init(this);
        if (SilentPackageInstaller.isUsed) {
            this.mSilentPackageInstaller = new SilentPackageInstaller(this);
        }
        int times = Settings.System.getInt(getContentResolver(), "bootTimes", 0);
        Settings.System.putInt(getContentResolver(), "bootTimes", times + 1);
        Intent intent = new Intent("com.wits.boot.Start");
        intent.addFlags(16777248);
        sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(getApplicationInfo().uid));
        final boolean isFirst = Settings.System.getInt(getContentResolver(), "firstTime_boot", 0) == 0;
        boolean isFirst_wits = Settings.System.getInt(getContentResolver(), "wits_firstTime_boot", 0) == 0;
        kswMaxVol();
        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$PowerManagerAppService$gJS-dVYHez6H9QgRRp7MHLobH48
            @Override // java.lang.Runnable
            public final void run() {
                PowerManagerAppService.lambda$boot$0(PowerManagerAppService.this, isFirst);
            }
        }, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        Settings.System.putInt(getContentResolver(), "wits_key", 0);
        Settings.System.putInt(getContentResolver(), "wits_call", 0);
        if (isFirst_wits) {
            if (!SilentPackageInstaller.isUsed) {
                this.mHandler.postDelayed(this.installAPK, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
            }
            Settings.System.putInt(getContentResolver(), WITS_FIRSTTIME_UPDATEAPK, 1);
        } else {
            boolean isFirstAddUpdateApk = Settings.System.getInt(getContentResolver(), WITS_FIRSTTIME_UPDATEAPK, 0) == 0;
            if (isFirstAddUpdateApk) {
                Settings.System.putInt(getContentResolver(), WITS_FIRSTTIME_UPDATEAPK, 1);
                android.p007os.SystemProperties.set("persist.wits.ota", "true");
            }
            this.mHandler.postDelayed(this.updateAPKRun, TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
        }
        if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
            this.mHandler.postDelayed(this.kswDefaultLauncherRunnable, 1000L);
        } else {
            this.mHandler.postDelayed(this.checkAndroidverison, 500L);
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
        registerReceiver(new BroadcastReceiver() { // from class: com.wits.pms.core.PowerManagerAppService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
                    String path = intent.getData().getPath();
                    String str = PowerManagerAppService.TAG;
                    Log.m72d(str, "OTA CHECK PATH: " + path);
                    if (!TextUtils.isEmpty(path)) {
                        if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
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
        CenterListener centerListener = new CenterListener(this, logicSystem);
        WitsMemoryController witsMemoryController = new WitsMemoryController(this);
        centerListener.setMemoryKiller(witsMemoryController);
    }

    private void initMcuService() {
        startService(new Intent(this, McuService.class));
        KswMcuLogic.init(this);
        KswMcuSender.getSender().sendMessage(97, new byte[]{0});
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 28)
    public void updateAPK() {
        List<APKInfo> apkInfoList = getAllAPKInfo();
        this.updateApks = new ArrayList();
        if (apkInfoList.size() > 0) {
            for (int i = 0; i < apkInfoList.size(); i++) {
                String pkgName = apkInfoList.get(i).getPkgName();
                Intent intent = serviceContext.getPackageManager().getLaunchIntentForPackage(pkgName);
                if (intent != null) {
                    String apkFileVersion = apkInfoList.get(i).getVersionName();
                    String installedVersion = Utils.getInstalledVersionName(serviceContext, pkgName);
                    int diff = Utils.compareVersion(installedVersion, apkFileVersion);
                    Log.m68i(TAG, " updateAPK: pkg = " + pkgName + " installed =" + installedVersion + "  apk =" + apkFileVersion + "  diff =" + diff);
                    if (diff > 0) {
                        this.updateApks.add(apkInfoList.get(i).getApkPath());
                    } else {
                        long installedVersionCode = Utils.getInstalledVersionCode(serviceContext, pkgName);
                        long apkVersionCode = apkInfoList.get(i).getVersionCode();
                        Log.m68i(TAG, "updateAPK: pkg = " + pkgName + " installedVersionCode = " + installedVersionCode + " apkVersionCode = " + apkVersionCode);
                        if (apkVersionCode > installedVersionCode) {
                            this.updateApks.add(apkInfoList.get(i).getApkPath());
                        }
                    }
                }
            }
        }
        this.count = 0;
        Log.m72d(TAG, "updateAPK: updateApks.size() =" + this.updateApks.size());
        if (this.updateApks.size() > 0) {
            if (this.installAppDialog != null) {
                this.installAppDialog.show();
            }
            if (this.progressBar != null) {
                this.progressBar.setMax(this.updateApks.size());
            }
            new Thread(new Runnable() { // from class: com.wits.pms.core.PowerManagerAppService.8
                @Override // java.lang.Runnable
                public void run() {
                    PowerManagerAppService.this.installUpdateApks();
                }
            }).start();
        }
        SystemProperties.set("persist.wits.ota", "false");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void installUpdateApks() {
        for (int i = 0; i < this.updateApks.size(); i++) {
            boolean success = Utils.installApp(this.updateApks.get(i));
            Log.m68i(TAG, "updateAPK: path =" + this.updateApks.get(i));
            if (success) {
                this.count++;
                if (this.mHandler != null) {
                    this.mHandler.obtainMessage(0, this.count, 0).sendToTarget();
                }
            } else {
                Log.m70e(TAG, "updateAPK: update fail =" + this.updateApks.get(i));
            }
        }
        if (this.mHandler != null) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createProgressDialog(Context context) {
        if (this.installAppDialog == null) {
            this.progressBar = new ProgressBar(context, null, 16842872);
            this.progressBar.setPadding(20, 20, 20, 20);
            this.installAppDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.app_update).setView(this.progressBar).create();
            this.installAppDialog.getWindow().setType(2003);
        }
    }

    @RequiresApi(api = 28)
    private List<APKInfo> getAllAPKInfo() {
        List<APKInfo> apkInfos = new ArrayList<>();
        File file = new File("/system/PreInstall/");
        if (!file.exists()) {
            return apkInfos;
        }
        File[] files = file.listFiles();
        if (files == null) {
            Log.m72d(TAG, "getAllAPKInfo no apk file");
            return apkInfos;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(PackageParser.APK_FILE_EXTENSION)) {
                APKInfo apkInfo = Utils.getApkInfo(this, "/system/PreInstall/" + files[i].getName());
                apkInfos.add(apkInfo);
            }
        }
        String str = TAG;
        Log.m72d(str, "getAllAPKInfo size=" + apkInfos.size());
        return apkInfos;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("PowerManagerAppService dump");
        if (this.mSilentPackageInstaller != null) {
            this.mSilentPackageInstaller.dump(fd, pw, args);
        }
        KswSettings.getSettings().dump(fd, pw, args);
    }
}
