package com.wits.pms.listener;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.wits.pms.ICmdListener;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.WitsMemoryController;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.MusicStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.VideoStatus;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.statuscontrol.WitsStatus;
import com.wits.pms.utils.SystemProperties;
import com.wits.pms.utils.UsbUtil;
import java.util.List;

public class CenterListener {
    /* access modifiers changed from: private */
    public static final String TAG = CenterListener.class.getName();
    private Thread checkBootThread = new Thread() {
        public void run() {
            for (int i = 0; i < 60; i++) {
                try {
                    sleep(2);
                } catch (InterruptedException e) {
                }
            }
        }
    };
    private Handler handler;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public final LogicSystem mLogicSystem;
    private MemoryKiller mMemoryKiller;
    /* access modifiers changed from: private */
    public boolean muted;
    private WifiReceiver wifiReceiver;

    public CenterListener(Context context, LogicSystem logicSystem) {
        this.mContext = context;
        this.handler = new Handler(context.getMainLooper());
        this.mLogicSystem = logicSystem;
        initListener();
    }

    private void initListener() {
        observeKswStatus();
        observeWifiSwitch();
        observeActivity();
        checkBootComplete();
        try {
            PowerManagerApp.getManager().registerCmdListener(new ICmdListener.Stub() {
                public boolean handleCommand(String jsonMsg) throws RemoteException {
                    WitsCommand witsCommand = WitsCommand.getWitsCommandFormJson(jsonMsg);
                    int command = witsCommand.getCommand();
                    String access$000 = CenterListener.TAG;
                    Log.d(access$000, "handleCommand   command = " + command + "  SubCommand = " + witsCommand.getSubCommand());
                    if (command != 1 || witsCommand.getSubCommand() != 604) {
                        return false;
                    }
                    int lastMode = PowerManagerApp.getStatusInt("lastMode");
                    String access$0002 = CenterListener.TAG;
                    Log.i(access$0002, "handleCommand  lastMode = " + lastMode);
                    return false;
                }

                public void updateStatusInfo(String jsonMsg) throws RemoteException {
                    WitsStatus witsStatus = WitsStatus.getWitsStatusFormJson(jsonMsg);
                    String access$000 = CenterListener.TAG;
                    Log.i(access$000, "updateStatusInfo: Status type=" + witsStatus.getType() + "  jsonMsg v2 = " + jsonMsg);
                    int type = witsStatus.getType();
                    if (type == 1) {
                        CenterListener.this.mLogicSystem.updateStatus(SystemStatus.getStatusFormJson(witsStatus.jsonArg));
                        int lastMode = SystemStatus.getStatusFormJson(witsStatus.jsonArg).getLastMode();
                        String access$0002 = CenterListener.TAG;
                        Log.d(access$0002, "updateStatusInfo   lastMode = " + lastMode);
                        if (lastMode == 5 || lastMode == 8) {
                            CenterControlImpl.getImpl().stopZlinkMusic();
                        }
                        if (CenterListener.this.mLogicSystem.getSystemStatus().getAcc() == 0) {
                            CenterControlImpl.getImpl().mute(true);
                            boolean unused = CenterListener.this.muted = true;
                        } else if (CenterListener.this.muted) {
                            CenterControlImpl.getImpl().mute(false);
                        }
                    } else if (type == 3) {
                        CenterListener.this.mLogicSystem.updateStatus(BtPhoneStatus.getStatusForJson(witsStatus.jsonArg));
                        if (BtPhoneStatus.getStatusForJson(witsStatus.jsonArg).isConnected && Settings.System.getInt(CenterListener.this.mContext.getContentResolver(), "zlink_auto_start", 0) == 1) {
                            CenterControlImpl.getImpl().enterZlink();
                        }
                    } else if (type != 5) {
                        switch (type) {
                            case 21:
                                CenterListener.this.mLogicSystem.updateStatus(MusicStatus.getStatusFromJson(witsStatus.jsonArg));
                                break;
                            case 22:
                                CenterListener.this.mLogicSystem.updateStatus(VideoStatus.getStatusFromJson(witsStatus.jsonArg));
                                break;
                        }
                    } else {
                        CenterListener.this.mLogicSystem.updateStatus(McuStatus.getStatusFromJson(witsStatus.jsonArg));
                        int systemMode = McuStatus.getStatusFromJson(witsStatus.jsonArg).systemMode;
                        String access$0003 = CenterListener.TAG;
                        Log.d(access$0003, "updateStatusInfo  systemMode = " + systemMode);
                        if (systemMode == 2) {
                            Log.d(CenterListener.TAG, "updateStatusInfo  CAR_MODE");
                            CenterControlImpl.getImpl().stopZlinkMusic();
                            AutoKitCallBackImpl.getImpl(CenterListener.this.mContext).musicPause();
                            CenterControlImpl.getImpl().kuWoMusicPause();
                        }
                    }
                    CenterListener.this.mLogicSystem.handle();
                }
            });
        } catch (RemoteException e) {
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("wits_usb"), true, new ContentObserver(this.handler) {
            public void onChange(boolean selfChange) {
                try {
                    UsbUtil.updateUsbMode(Settings.System.getInt(CenterListener.this.mContext.getContentResolver(), "wits_usb"));
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        });
    }

    private void checkBootComplete() {
        this.checkBootThread.start();
    }

    private void observeKswStatus() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("usingNavi"), true, new ContentObserver(this.handler) {
            public void onChange(boolean selfChange) {
                try {
                    int value = Settings.System.getInt(CenterListener.this.mContext.getContentResolver(), "usingNavi");
                    CenterListener.this.setBtVolume(value == 1);
                    ((AudioManager) CenterListener.this.mContext.getSystemService("audio")).setStreamVolume(5, (int) ((Settings.System.getFloat(CenterListener.this.mContext.getContentResolver(), "NaviMix") * 10.0f) - 1.0f), 0);
                    KswMcuSender.getSender().sendMessage(105, new byte[]{19, (byte) value, 0});
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setBtVolume(boolean on) throws Settings.SettingNotFoundException {
        ((AudioManager) this.mContext.getSystemService("audio")).setStreamVolume(5, on ? (int) ((Settings.System.getFloat(this.mContext.getContentResolver(), "NaviMix") * 10.0f) - 1.0f) : 7, 0);
    }

    public void appStarted(String packageName, String className) {
        String str = TAG;
        Log.d(str, "appStarted  packageName = " + packageName);
        SystemStatusControl.getStatus().topApp = packageName;
        SystemStatusControl.getDefault().handleSystemStatus();
        if (packageName.contains("net.easyconn")) {
            CenterControlImpl.getImpl().mediaPause();
            CenterControlImpl.getImpl().openSourceMode(3);
            CenterControlImpl.getImpl().stopZlinkMusic();
        }
        if (packageName.contains("com.wits.ksw.bt") || packageName.contains("com.wits.ksw.media")) {
            CenterControlImpl.getImpl().stopZlinkMusic();
        }
        if (packageName.contains("com.mxtech")) {
            CenterControlImpl.getImpl().openSourceMode(13);
        }
        if (packageName.contains(ZlinkMessage.ZLINK_NORMAL_ACTION) || packageName.contains("com.suding.speedplay")) {
            CenterControlImpl.getImpl().openSourceMode(3);
            Bundle bundle = new Bundle();
            bundle.putString("command", "REQ_SPEC_FUNC_CMD");
            bundle.putInt("specFuncCode", 126);
        }
        if (packageName.contains(AutoKitCallBackImpl.AutoKitPkgName)) {
            CenterControlImpl.getImpl().openSourceMode(3);
        }
        if (packageName.contains("com.txznet.music")) {
            CenterControlImpl.getImpl().openSourceMode(13);
            if (className.equals("com.txznet.music.ui.SplashActivity")) {
                CenterControlImpl.getImpl().txzMusicPlay(true);
            } else {
                CenterControlImpl.getImpl().txzMusicPlay(false);
            }
        }
        if (packageName.equals("cn.kuwo.kwmusiccar")) {
            CenterControlImpl.getImpl().openSourceMode(13);
            if (className.equals("cn.kuwo.kwmusiccar.WelcomeActivity")) {
                CenterControlImpl.getImpl().kuWoMusicPlay(true);
            } else {
                CenterControlImpl.getImpl().kuWoMusicPlay(false);
            }
        }
    }

    private void observeActivity() {
        final ActivityManager am = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        new Thread() {
            private String packageName;

            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e2) {
                    }
                    int i = 1;
                    List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
                    if (runningTasks.size() > 0) {
                        String packageName2 = runningTasks.get(0).topActivity.getPackageName();
                        String className = runningTasks.get(0).topActivity.getClassName();
                        if (!packageName2.equals(this.packageName)) {
                            this.packageName = packageName2;
                            CenterListener.this.appStarted(packageName2, className);
                            Settings.System.putString(CenterListener.this.mContext.getContentResolver(), "currentPkg", packageName2);
                        }
                        try {
                            int btSwitch = Settings.System.getInt(CenterListener.this.mContext.getContentResolver(), "btSwitch", 1);
                            if (btSwitch == Integer.parseInt(SystemProperties.get(ZlinkMessage.ZLINK_CONNECT))) {
                                ContentResolver contentResolver = CenterListener.this.mContext.getContentResolver();
                                if (btSwitch == 1) {
                                    i = 0;
                                }
                                Settings.System.putInt(contentResolver, "btSwitch", i);
                            }
                        } catch (Exception e3) {
                        }
                        CenterListener.this.checkMemory();
                    }
                }
            }
        }.start();
    }

    private void observeWifiSwitch() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        this.wifiReceiver = new WifiReceiver();
        this.mContext.registerReceiver(this.wifiReceiver, filter);
    }

    public void setMemoryKiller(WitsMemoryController witsMemoryController) {
        this.mMemoryKiller = witsMemoryController;
    }

    /* access modifiers changed from: private */
    public void checkMemory() {
        try {
            this.mMemoryKiller.clearMemoryWits();
        } catch (Exception e) {
            Log.e("MemoryKiller", "ERROR!", e);
        }
    }

    class WifiReceiver extends BroadcastReceiver {
        WifiReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()) || intent.getIntExtra("wifi_state", 0) != 1) {
            }
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (!info.getState().equals(NetworkInfo.State.DISCONNECTED) && info.getState().equals(NetworkInfo.State.CONNECTED)) {
                    ((WifiManager) CenterListener.this.mContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
                }
            }
        }
    }
}
