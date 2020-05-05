package com.wits.pms.core;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import com.wits.pms.custom.BtController;
import com.wits.pms.custom.KswMemoryController;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.custom.KswStatusHandler;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.listener.CenterListener;
import com.wits.pms.mcu.McuService;
import com.wits.pms.mcu.custom.KswMcuLogic;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.utils.AccLight;
import com.wits.pms.mcu.custom.utils.ForceMcuUpdate;
import com.wits.pms.mcu.custom.utils.OTAUpdate;
import com.wits.pms.mcu.custom.utils.SplashFlasher;
import com.wits.pms.mcu.custom.utils.SymlinkHelper;
import com.wits.pms.utils.McuUpdater;
import com.wits.pms.utils.ServiceManager;

public class PowerManagerAppService extends Service {
    /* access modifiers changed from: private */
    public static final String TAG = PowerManagerAppService.class.getName();
    public static Context serviceContext;
    private Handler mHandler;
    private PowerManagerImpl mPmasBinder;
    boolean startBt;

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

    @RequiresApi(api = 24)
    private void boot() {
        KswSettings.init(this);
        boolean isFirst = true;
        Settings.System.putInt(getContentResolver(), "bootTimes", Settings.System.getInt(getContentResolver(), "bootTimes", 0) + 1);
        Intent intent = new Intent("com.wits.boot.Start");
        intent.addFlags(16777248);
        sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(getApplicationInfo().uid));
        if (Settings.System.getInt(getContentResolver(), "firstTime_boot", 0) != 0) {
            isFirst = false;
        }
        this.mHandler.postDelayed(new PowerManagerAppService$$Lambda$0(this, isFirst), 5000);
        Settings.System.putInt(getContentResolver(), "wits_key", 0);
        Settings.System.putInt(getContentResolver(), "wits_call", 0);
        BtController.fixBt(this);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$boot$0$PowerManagerAppService(boolean isFirst) {
        if (isFirst) {
            OTAUpdate.reinstallApk();
            Settings.System.putInt(getContentResolver(), "firstTime_boot", 1);
        }
    }

    private void registerMountBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addDataScheme("file");
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.MEDIA_MOUNTED".equals(intent.getAction())) {
                    String path = intent.getData().getPath();
                    String access$000 = PowerManagerAppService.TAG;
                    Log.d(access$000, "OTA CHECK PATH: " + path);
                    if (!TextUtils.isEmpty(path)) {
                        OTAUpdate.checkFile(PowerManagerAppService.this, path);
                        SplashFlasher.check(PowerManagerAppService.this, path);
                        ForceMcuUpdate.check(PowerManagerAppService.this, path);
                        KswSettings.getSettings().check(PowerManagerAppService.this, path);
                        McuUpdater.check(PowerManagerAppService.this, path);
                        if (!intent.getExtras().getBoolean("isUsb")) {
                            SymlinkHelper.setUp(path);
                        }
                    }
                    path.contains("/storage/emulated");
                } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(intent.getAction()) && !intent.getExtras().getBoolean("isUsb")) {
                    SymlinkHelper.cleanUp();
                }
            }
        }, intentFilter);
    }

    private void problemFix() {
        Settings.Secure.putInt(getContentResolver(), "user_setup_complete", 1);
        SystemStatusControl.getDefault().boot(this.mPmasBinder);
    }

    private void setupUsbMode() {
    }

    private void initStatusObs() {
        LogicSystem logicSystem = new KswStatusHandler();
        CenterControlImpl.getImpl().setLogic(logicSystem);
        new CenterListener(this, logicSystem);
        CenterControlImpl.getImpl().setMemoryKiller(new KswMemoryController(this));
    }

    private void initMcuService() {
        startService(new Intent(this, McuService.class));
        KswMcuLogic.init(this);
        KswMcuSender.getSender().sendMessage(97, new byte[]{0});
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
