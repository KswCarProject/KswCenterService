package com.wits.pms.core;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.job.JobInfo;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidHost;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.p007os.Build;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.PowerManager;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.support.p014v7.app.AlertDialog;
import android.telephony.PreciseDisconnectCause;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimedRemoteCaller;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.internal.logging.nano.MetricsProto;
import com.google.gson.Gson;
import com.wits.pms.C3580R;
import com.wits.pms.ICmdListener;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.EcarMessage;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.custom.BtController;
import com.wits.pms.custom.KswFunction;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.interfaces.CenterControl;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.mcu.McuSender;
import com.wits.pms.mcu.custom.KswMcuListener;
import com.wits.pms.mcu.custom.KswMcuLogic;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.KswMessage;
import com.wits.pms.mcu.custom.McuCommandListener;
import com.wits.pms.mcu.custom.utils.ABNetOTAUpdate;
import com.wits.pms.mcu.custom.utils.NetOTAUpdate;
import com.wits.pms.mcu.custom.utils.SplashFlasher;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.mirror.ConnectivityManagerMirror;
import com.wits.pms.mirror.PowerManagerMirror;
import com.wits.pms.mirror.ServiceManager;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.receiver.CenterCallBackImpl;
import com.wits.pms.receiver.ReceiverHandler;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.AmsUtil;
import com.wits.pms.utils.KeyUtils;
import com.wits.pms.utils.Test;
import com.wits.pms.utils.TouchControl;
import com.wits.pms.utils.UsbUtil;
import com.wits.pms.utils.Utils;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.mozilla.universalchardet.prober.HebrewProber;

/* loaded from: classes2.dex */
public class CenterControlImpl extends ICmdListener.Stub implements CenterControl, KswFunction {
    static final boolean DBG = false;
    static final String TAG = "CenterControl";
    private static int clockSort = -1;
    private static CenterControlImpl mCenterControl;
    private int beforeMuteVolume;
    private Thread benzDialSenderThread;
    private AlertDialog installAppDialog;
    private boolean isSendZlinkCarBroadCast;
    private ActivityManager mActivityManager;
    private final AudioManager mAudioManager;
    private Context mContext;
    private final Handler mHandler;
    private LogicSystem mLogicSystem;
    private McuSender mMcuSender;
    private MemoryKiller mMemoryKiller;
    int mode;
    private boolean muteStatus;
    private ProgressBar progressBar;
    private boolean statusChange;
    boolean zlinkCalling;
    boolean zlinkShowing;
    private int mCurVolume = -1;
    private boolean flgReleaseMicInConnected = true;
    private boolean flgZlinkConnected = false;
    private int cpCallStatus = 0;
    private boolean isLinkHicar = false;
    int originMode = -1;
    boolean iszlinkCall = false;
    private final Runnable volumeUpBySystemRun = new Runnable() { // from class: com.wits.pms.core.CenterControlImpl.3
        @Override // java.lang.Runnable
        public void run() {
            CenterControlImpl.this.volumeUpBySystem();
        }
    };
    private final Runnable volumeDownBySystemRun = new Runnable() { // from class: com.wits.pms.core.CenterControlImpl.4
        @Override // java.lang.Runnable
        public void run() {
            CenterControlImpl.this.volumeDownBySystem();
        }
    };
    private int[] sourceArray = {1, 2, 3, 5, 6, 9};
    private Runnable stopNaviProtocolRunnable = new Runnable() { // from class: com.wits.pms.core.-$$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0
        @Override // java.lang.Runnable
        public final void run() {
            KswMcuSender.getSender().sendMessage(105, new byte[]{19, 0, 0});
        }
    };

    public CenterControlImpl(Context context) {
        this.mContext = context;
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mHandler = new Handler(context.getMainLooper());
        PowerManagerApp.registerICmdListener(this);
        startTxzService();
        Test.test();
    }

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        mCenterControl = new CenterControlImpl(context);
        CenterCallBackImpl.init(context);
        AutoKitCallBackImpl.init(context);
        ReceiverHandler.init(context);
    }

    public static KswMcuSender getMcuSender() {
        return KswMcuSender.getSender();
    }

    public static CenterControlImpl getImpl() {
        if (mCenterControl == null) {
            init(PowerManagerAppService.serviceContext);
        }
        return mCenterControl;
    }

    public void setMemoryKiller(MemoryKiller memoryKiller) {
        this.mMemoryKiller = memoryKiller;
    }

    public boolean isAppInstalled(String uri) {
        PackageManager pm = this.mContext.getPackageManager();
        try {
            pm.getPackageInfo(uri, 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x034f, code lost:
        if (r0.equals("music.loop.single") != false) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0413, code lost:
        if (r0.equals("radio.open") != false) goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x04a8, code lost:
        if (r0.equals("music.close") != false) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x0644, code lost:
        if (r0.equals("volume.up") != false) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e9, code lost:
        if (r0.equals("txz.voice.init.success") != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handle(final TxzMessage message) {
        TxzMessage txzMessage;
        String action = message.action;
        Log.m68i(TAG, "handle: TxzMessage keyType = " + message.keyType + "  action = " + action);
        SystemProperties.set("vendor.wits.txz.init", "1");
        char c = '\t';
        char c2 = 4;
        switch (message.keyType) {
            case 1000:
                if (!"app.open".equals(action)) {
                    if ("app.close".equals(action)) {
                        closeApp(message.bundle.getString("package"));
                        return;
                    }
                    return;
                }
                String pkgName = message.bundle.getString("package");
                if ("com.txznet.music".equals(pkgName) && !isAppInstalled(pkgName)) {
                    openMusic();
                    return;
                }
                openSourceMode(13);
                openApp(pkgName);
                return;
            case 1010:
                if ("wifi.open".equals(action)) {
                    wifiOperation(true);
                    return;
                } else if ("wifi.close".equals(action)) {
                    wifiOperation(false);
                    return;
                } else if ("go.home".equals(action)) {
                    KeyUtils.pressKey(3);
                    return;
                } else if ("screen.close".equals(action)) {
                    displayScreen(false);
                    return;
                } else if ("screen.open".equals(action)) {
                    displayScreen(true);
                    return;
                } else {
                    return;
                }
            case 1020:
                int currentBrightness = Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
                if ("light.up".equals(action)) {
                    currentBrightness += 42;
                    Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, currentBrightness);
                }
                if ("light.down".equals(action)) {
                    currentBrightness -= 42;
                    Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, currentBrightness);
                }
                if ("light.min".equals(action)) {
                    currentBrightness = 10;
                    Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 10);
                }
                if ("light.max".equals(action)) {
                    currentBrightness = 255;
                    Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
                }
                Log.m68i(TAG, "Brightness set to " + currentBrightness);
                return;
            case 1030:
                String str = message.action;
                switch (str.hashCode()) {
                    case -2128329265:
                        if (str.equals("volume.to")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -2128329233:
                        break;
                    case -1553713362:
                        if (str.equals("volume.dec")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -1553708278:
                        if (str.equals("volume.inc")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -1553704816:
                        if (str.equals("volume.max")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -1553704578:
                        if (str.equals("volume.min")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -920463626:
                        if (str.equals("volume.down")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    case -920189843:
                        if (str.equals("volume.mute")) {
                            r14 = true;
                            break;
                        }
                        r14 = true;
                        break;
                    default:
                        r14 = true;
                        break;
                }
                switch (r14) {
                    case false:
                        this.mHandler.removeCallbacks(this.volumeUpBySystemRun);
                        this.mHandler.postDelayed(this.volumeUpBySystemRun, 300L);
                        return;
                    case true:
                        this.mHandler.removeCallbacks(this.volumeDownBySystemRun);
                        this.mHandler.postDelayed(this.volumeDownBySystemRun, 300L);
                        return;
                    case true:
                        volumeMax();
                        return;
                    case true:
                        volumeMin();
                        return;
                    case true:
                    case true:
                    case true:
                    default:
                        return;
                    case true:
                        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$CenterControlImpl$VcInPsCoMHCPUEGHro8Z0FBgpNg
                            @Override // java.lang.Runnable
                            public final void run() {
                                CenterControlImpl.this.muteWithUI(message.bundle.getBoolean("mute"));
                            }
                        }, 100L);
                        return;
                }
            case 1040:
                String str2 = message.action;
                switch (str2.hashCode()) {
                    case -1362146736:
                        if (str2.equals("music.continue")) {
                            c = 11;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825432804:
                        if (str2.equals("music.next")) {
                            c = '\f';
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825393037:
                        if (str2.equals("music.open")) {
                            c = '\b';
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825361316:
                        if (str2.equals("music.prev")) {
                            c = '\r';
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 19491040:
                        if (str2.equals("bluetooth.contact")) {
                            c = 7;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 171428079:
                        break;
                    case 179334558:
                        if (str2.equals("bluetooth.call")) {
                            c = 2;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 179706250:
                        if (str2.equals("bluetooth.open")) {
                            c = 0;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 183111917:
                        if (str2.equals("music.pause")) {
                            c = '\n';
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 486135880:
                        if (str2.equals("bluetooth.accept")) {
                            c = 4;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 685022669:
                        if (str2.equals("bluetooth.hangup")) {
                            c = 3;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 974886623:
                        if (str2.equals("bluetooth.reject")) {
                            c = 5;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 1017115442:
                        if (str2.equals("bluetooth.status")) {
                            c = 6;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 1264734904:
                        if (str2.equals("bluetooth.close")) {
                            c = 1;
                            break;
                        }
                        c = '\uffff';
                        break;
                    default:
                        c = '\uffff';
                        break;
                }
                switch (c) {
                    case 0:
                        openBluetooth(true);
                        return;
                    case 1:
                        Log.m72d(TAG, "topApp = " + SystemStatusControl.getStatus().topApp + "   isPlayingMusic = " + this.mLogicSystem.getBtPhoneStatus().isPlayingMusic);
                        if (SystemStatusControl.getStatus().topApp.equals("com.wits.ksw.bt")) {
                            this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$nPreiVZHf-0USHRkVw4TYz6IFrQ
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CenterControlImpl.this.sendCloseBTAppBroadcast();
                                }
                            }, 800L);
                        }
                        if (this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
                            this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$MnsNT1fc1_cXAfpJhxBta4fOU_k
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CenterControlImpl.this.btMusicStop();
                                }
                            }, 0L);
                            return;
                        }
                        return;
                    case 2:
                        btPhoneCall(message.bundle.getString("name"), message.bundle.getString("number"));
                        return;
                    case 3:
                        handUpPhone();
                        return;
                    case 4:
                        acceptPhone();
                        return;
                    case 5:
                        handUpPhone();
                        return;
                    case 6:
                        Bundle bundle = new Bundle();
                        try {
                            boolean isConnected = PowerManagerApp.getStatusBoolean("isConnected");
                            if (isConnected) {
                                int callStatus = PowerManagerApp.getStatusInt("callStatus");
                                switch (callStatus) {
                                    case 4:
                                        txzMessage = new TxzMessage(2040, "bluetooth.call", bundle);
                                        break;
                                    case 5:
                                        txzMessage = new TxzMessage(2040, "bluetooth.incoming", bundle);
                                        break;
                                    case 6:
                                        txzMessage = new TxzMessage(2040, "bluetooth.offhook", bundle);
                                        break;
                                    case 7:
                                        txzMessage = new TxzMessage(2040, "bluetooth.hangup", bundle);
                                        break;
                                    default:
                                        txzMessage = new TxzMessage(2040, "bluetooth.idle", bundle);
                                        break;
                                }
                                if (callStatus == 0) {
                                    txzMessage = new TxzMessage(2040, "bluetooth.connect", bundle);
                                }
                            } else {
                                txzMessage = new TxzMessage(2040, "bluetooth.disconnect", bundle);
                            }
                            CenterCallBackImpl.getImpl(this.mContext).sendBroadCast(txzMessage);
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                    case 7:
                        WitsCommand.sendCommand(3, 200, "");
                        return;
                    case '\b':
                        btMusicOpen();
                        return;
                    case '\t':
                        btMusicStop();
                        return;
                    case '\n':
                        mediaPause();
                        return;
                    case 11:
                        mediaPlay();
                        return;
                    case '\f':
                        mediaNext();
                        return;
                    case '\r':
                        mediaPrevious();
                        return;
                    default:
                        return;
                }
            case 1050:
                String str3 = message.action;
                int hashCode = str3.hashCode();
                if (hashCode == -1480935867) {
                    if (str3.equals("radio.close")) {
                        r14 = true;
                    }
                    r14 = true;
                } else if (hashCode == -47450874) {
                    if (str3.equals("radio.next")) {
                        r14 = true;
                    }
                    r14 = true;
                } else if (hashCode == -47411107) {
                    break;
                } else {
                    if (hashCode == -47379386 && str3.equals("radio.prev")) {
                        r14 = true;
                    }
                    r14 = true;
                }
                switch (r14) {
                    case false:
                        fmOpen();
                        return;
                    case true:
                        fmClose();
                        return;
                    case true:
                        fmPrevious();
                        return;
                    case true:
                        fmNext();
                        return;
                    default:
                        return;
                }
            case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX /* 1060 */:
            case Process.OTA_UPDATE_UID /* 1061 */:
                String str4 = message.action;
                switch (str4.hashCode()) {
                    case -1362146736:
                        if (str4.equals("music.continue")) {
                            c = 3;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825432804:
                        if (str4.equals("music.next")) {
                            c = 5;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825393037:
                        if (str4.equals("music.open")) {
                            c = 0;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825367203:
                        if (str4.equals("music.play")) {
                            c = 2;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -825361316:
                        if (str4.equals("music.prev")) {
                            c = 6;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -770549500:
                        if (str4.equals("music.loop.random")) {
                            c = '\n';
                            break;
                        }
                        c = '\uffff';
                        break;
                    case -734529399:
                        break;
                    case 171428079:
                        if (str4.equals("music.close")) {
                            c = 1;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 183111917:
                        if (str4.equals("music.pause")) {
                            c = 4;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 1438537900:
                        if (str4.equals("music.random")) {
                            c = 7;
                            break;
                        }
                        c = '\uffff';
                        break;
                    case 1461409024:
                        if (str4.equals("music.loop.all")) {
                            c = '\b';
                            break;
                        }
                        c = '\uffff';
                        break;
                    default:
                        c = '\uffff';
                        break;
                }
                switch (c) {
                    case 0:
                        openMusic();
                        return;
                    case 1:
                        closeMusic();
                        return;
                    case 2:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("package", "com.txznet.music");
                        handle(new TxzMessage(1000, "app.open", bundle2));
                        return;
                    case 3:
                        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$zpVvdyJDIdGRAYWptfdkD6nqGGo
                            @Override // java.lang.Runnable
                            public final void run() {
                                CenterControlImpl.this.mediaPlay();
                            }
                        }, 500L);
                        return;
                    case 4:
                        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$821yd6MZoim56feZSH7LwWBSB30
                            @Override // java.lang.Runnable
                            public final void run() {
                                CenterControlImpl.this.mediaPause();
                            }
                        }, 500L);
                        return;
                    case 5:
                        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$g8OnSrjKr4iJCaVGk2HNyM7Qk3M
                            @Override // java.lang.Runnable
                            public final void run() {
                                CenterControlImpl.this.mediaNext();
                            }
                        }, 500L);
                        return;
                    case 6:
                        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$DWovm9NibHt3DLTeYdTBgUEQaMQ
                            @Override // java.lang.Runnable
                            public final void run() {
                                CenterControlImpl.this.mediaPrevious();
                            }
                        }, 500L);
                        return;
                    case 7:
                    case '\b':
                    case '\t':
                    default:
                        return;
                }
            case 1091:
                if ("dvr.open".equals(action)) {
                    openApp(KswSettings.getSettings().getSettingsString("DVRApk_PackageName"));
                }
                if ("dvr.close".equals(action)) {
                    closeApp(KswSettings.getSettings().getSettingsString("DVRApk_PackageName"));
                }
                if ("video.open".equals(action)) {
                    killApp("com.txznet.music");
                    openVideo();
                }
                if ("video.close".equals(action)) {
                    closeVideo();
                }
                if ("tv.open".equals(action)) {
                    openDtv(true);
                }
                if ("tv.close".equals(action)) {
                    systemModeSwitch(1);
                }
                if ("aux.open".equals(action)) {
                    openAux(true);
                }
                if ("aux.close".equals(action)) {
                    systemModeSwitch(1);
                }
                if ("original_car.open".equals(action)) {
                    KswMcuSender.getSender().sendMessage(103, new byte[]{0});
                    KswMcuSender.getSender().sendMessage(105, new byte[]{18, 2});
                }
                if ("mobile_internet.open".equals(action)) {
                    openApp("net.easyconn");
                }
                "android.home".equals(action);
                KswMcuSender.getSender().sendMessage(105, new byte[]{18, 1});
                if ("music.play.local".equals(action)) {
                    String path = message.bundle.getString("path");
                    Log.m72d(TAG, "music.play.local   path = " + path);
                    Intent intentMusic = new Intent();
                    intentMusic.setAction("com.wits.media.MUSIC");
                    intentMusic.putExtra("txzPlay", true);
                    intentMusic.putExtra("path", path);
                    try {
                        startAction(intentMusic);
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            case 2021:
                String str5 = message.action;
                switch (str5.hashCode()) {
                    case -1835123299:
                        if (str5.equals("txz.init.success")) {
                            c2 = 0;
                            break;
                        }
                        c2 = '\uffff';
                        break;
                    case -1076571994:
                        if (str5.equals("txz.voice.status")) {
                            c2 = 1;
                            break;
                        }
                        c2 = '\uffff';
                        break;
                    case -1005105447:
                        break;
                    case -912126038:
                        if (str5.equals("txz.recorder.status")) {
                            c2 = 3;
                            break;
                        }
                        c2 = '\uffff';
                        break;
                    case 470324425:
                        if (str5.equals("txz.voice.disable.success")) {
                            c2 = 2;
                            break;
                        }
                        c2 = '\uffff';
                        break;
                    default:
                        c2 = '\uffff';
                        break;
                }
                switch (c2) {
                    case 0:
                        if (!isCarplayConnected() && !isBTCalling()) {
                            try {
                                int txzStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                                Log.m72d(TAG, "TxzMessage  set Support_TXZ " + txzStatus);
                                setTxzSwitch(txzStatus == 1);
                                Log.m72d(TAG, "TxzMessage handle: syn txz state after boot ");
                                break;
                            } catch (RemoteException e3) {
                                e3.printStackTrace();
                                break;
                            }
                        }
                        break;
                    case 1:
                        boolean state = message.bundle.getBoolean("isDisable");
                        Log.m72d(TAG, "TXZhandle: TxzMessage app isDisable =" + state);
                        if (!isCarplayConnected() && !isBTCalling()) {
                            try {
                                int txzStatus2 = PowerManagerApp.getSettingsInt("Support_TXZ");
                                StringBuilder sb = new StringBuilder();
                                sb.append("TXZhandle: TxzMessage system state isDisable =");
                                sb.append(txzStatus2 == 1);
                                Log.m72d(TAG, sb.toString());
                                if ((state && txzStatus2 == 1) || (!state && txzStatus2 != 1)) {
                                    setTxzSwitch(txzStatus2 == 1);
                                    Log.m72d(TAG, "TxzMessage handle: txz state != system state, update  txz state");
                                    break;
                                }
                            } catch (RemoteException e4) {
                                e4.printStackTrace();
                                break;
                            }
                        }
                        break;
                    case 2:
                        WitsCommand.sendCommand(3, 115, "");
                        break;
                    case 3:
                        boolean fBeforeSleepSts = message.bundle.getBoolean("before.sleep");
                        if (!fBeforeSleepSts) {
                            WitsCommand.sendCommand(3, 115, "");
                            break;
                        }
                        break;
                    case 4:
                        try {
                            int txzStatus3 = PowerManagerApp.getSettingsInt("Support_TXZ");
                            Log.m72d("centerService", "get txz.voice.init.success, send Support_TXZ " + txzStatus3);
                            setTxzSwitch(txzStatus3 == 1);
                            break;
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                            break;
                        }
                }
            case 2070:
                int isPGear = 1;
                McuStatus mcuStatus = SystemStatusControl.getDefault().getMcuStatus();
                String str6 = message.action;
                if (str6.hashCode() != -299518397 || !str6.equals("car.gear.check")) {
                    r14 = true;
                }
                if (!r14) {
                    if (mcuStatus.carData.carGear == 0) {
                        isPGear = 0;
                    }
                    TxzCheckGearAck(isPGear);
                    return;
                }
                return;
            case CamcorderProfile.QUALITY_8KUHD /* 3001 */:
                Log.m72d(TAG, "TXZ mesage -- 3001: action is " + message.action);
                String str7 = message.action;
                if (str7.hashCode() != -1311128991 || !str7.equals("carmode.open")) {
                    r14 = true;
                }
                if (!r14) {
                    Log.m72d(TAG, "TXZ mesage -- 3001: action is " + message.action + " -- SWITCH TO CARMODE");
                    systemModeSwitch(2);
                    return;
                }
                return;
            case 10000:
                break;
            default:
                return;
        }
        try {
            this.mCurVolume = message.bundle.getInt("curVolumeInt");
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        Log.m72d(TAG, "handle: mCurVolume =" + this.mCurVolume);
    }

    public void TxzCheckGearAck(int isPGear) {
        Bundle bundle = new Bundle();
        bundle.putInt("status", isPGear);
        TxzMessage txzMessage = new TxzMessage(2070, "car.gear.status", bundle);
        Log.m72d(TAG, "TXZ mesage -- 2070: TxzCheckGearAck msg " + txzMessage);
        CenterCallBackImpl.getImpl(this.mContext).sendBroadCast(txzMessage);
    }

    public boolean isUsingCarPlay() {
        return SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1");
    }

    public boolean isUsingUsbCarPlay() {
        return SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x0546, code lost:
        if (r0.equals("CMD_MIC_START") != false) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0086, code lost:
        if (r0.equals("ENTER") != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handle(ZlinkMessage message) {
        char c = 3;
        if (message.status != null) {
            String str = message.status;
            switch (str.hashCode()) {
                case -2087582999:
                    if (str.equals("CONNECTED")) {
                        c = 1;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -1843701849:
                    if (str.equals("MAIN_PAGE_SHOW")) {
                        c = 5;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -497207953:
                    if (str.equals("PHONE_CALL_ON")) {
                        c = '\t';
                        break;
                    }
                    c = '\uffff';
                    break;
                case -150661894:
                    if (str.equals("ACTION_ZJ_PHONEFOUND")) {
                        c = 0;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 2142494:
                    if (str.equals("EXIT")) {
                        c = 4;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 66129592:
                    break;
                case 143012129:
                    if (str.equals("SWITCHHUB")) {
                        c = '\f';
                        break;
                    }
                    c = '\uffff';
                    break;
                case 143018830:
                    if (str.equals("SWITCHOTG")) {
                        c = 11;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 161176669:
                    if (str.equals("PHONE_RING_ON")) {
                        c = 7;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 701509265:
                    if (str.equals("PHONE_RING_OFF")) {
                        c = '\b';
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1015497884:
                    if (str.equals("DISCONNECT")) {
                        c = 2;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1709675476:
                    if (str.equals("MAIN_PAGE_HIDDEN")) {
                        c = 6;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1766422463:
                    if (str.equals("PHONE_CALL_OFF")) {
                        c = '\n';
                        break;
                    }
                    c = '\uffff';
                    break;
                case 2008068401:
                    if (str.equals("MAIN_AUDIO_STOP")) {
                        c = 14;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 2120564979:
                    if (str.equals("MAIN_AUDIO_START")) {
                        c = '\r';
                        break;
                    }
                    c = '\uffff';
                    break;
                default:
                    c = '\uffff';
                    break;
            }
            switch (c) {
                case 0:
                    try {
                        int oldStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                        if (oldStatus == 0 || this.flgReleaseMicInConnected) {
                            return;
                        }
                        setTxzSleep(true);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 1:
                    String phoneMode = message.bundle.getString("phoneMode");
                    Log.m72d(TAG, "handle  CONNECTED   " + phoneMode);
                    if (phoneMode != null && phoneMode.equals("carplay_wireless")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CONNECT, "1");
                        closeBluetooth();
                    }
                    if (phoneMode != null && (phoneMode.equals("airplay_wired") || phoneMode.equals("airplay_wireless"))) {
                        if (phoneMode.equals("airplay_wireless")) {
                            SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_WIRED_CONNECT, "0");
                            closeBluetooth();
                        } else {
                            SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_WIRED_CONNECT, "1");
                        }
                        SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_CONNECT, "1");
                    }
                    if (phoneMode != null && phoneMode.equals("carplay_wired")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT, "1");
                        closeBluetooth();
                    }
                    if ((phoneMode != null && phoneMode.equals("hicar_wireless")) || phoneMode.equals("hicar_wired")) {
                        this.isLinkHicar = true;
                    }
                    if (phoneMode != null && (phoneMode.equals("auto_wired") || phoneMode.equals("auto_wireless"))) {
                        SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT, "1");
                    }
                    if (phoneMode != null && (phoneMode.equals("android_mirror_wired") || phoneMode.equals("android_mirror_wireless"))) {
                        SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT, "1");
                    }
                    if (phoneMode != null && phoneMode.equals("hicar_wired")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRED_CONNECT, "1");
                    }
                    if (phoneMode != null && phoneMode.equals("hicar_wireless")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRELESS_CONNECT, "1");
                    }
                    try {
                        int oldStatus2 = PowerManagerApp.getSettingsInt("Support_TXZ");
                        if (oldStatus2 != 0) {
                            if (this.flgReleaseMicInConnected) {
                                setTxzSleep(true);
                            }
                            this.flgZlinkConnected = true;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    this.zlinkCalling = false;
                    return;
                case 2:
                    String phoneMode2 = message.bundle.getString("phoneMode");
                    Log.m72d(TAG, "handle DISCONNECT " + phoneMode2 + ", Build.VERSION is " + Build.VERSION.RELEASE);
                    if (phoneMode2 != null && phoneMode2.equals("carplay_wireless")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CONNECT, "0");
                        openBluetooth(false);
                        if (Build.VERSION.RELEASE.equals("11") && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
                            closeWifiHotspot();
                        }
                    }
                    if (phoneMode2 != null && (phoneMode2.equals("airplay_wired") || phoneMode2.equals("airplay_wireless"))) {
                        SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_CONNECT, "0");
                        SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_WIRED_CONNECT, "0");
                        openBluetooth(false);
                    }
                    if (phoneMode2 != null && phoneMode2.equals("carplay_wired")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT, "0");
                        openBluetooth(false);
                    }
                    if (phoneMode2 != null && (phoneMode2.equals("auto_wired") || phoneMode2.equals("auto_wireless"))) {
                        SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT, "0");
                    }
                    if (phoneMode2 != null && (phoneMode2.equals("android_mirror_wired") || phoneMode2.equals("android_mirror_wireless"))) {
                        SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT, "0");
                    }
                    if (phoneMode2 != null && phoneMode2.equals("hicar_wired")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRED_CONNECT, "0");
                    }
                    if (phoneMode2 != null && phoneMode2.equals("hicar_wireless")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRELESS_CONNECT, "0");
                    }
                    try {
                        int oldStatus3 = PowerManagerApp.getSettingsInt("Support_TXZ");
                        if (oldStatus3 != 0 && PowerManagerApp.getStatusInt("systemMode") == 1) {
                            setTxzSleep(false);
                            this.flgZlinkConnected = false;
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    this.zlinkCalling = false;
                    SystemProperties.set(ZlinkMessage.ZLINK_CALL, "0");
                    Settings.System.putInt(this.mContext.getContentResolver(), "isZlinkCalling", 0);
                    this.isLinkHicar = false;
                    return;
                case 3:
                    SystemProperties.set(ZlinkMessage.ZLINK_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_AIRPLAY_WIRED_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRED_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_HICAR_WIRELESS_CONNECT, "0");
                    SystemProperties.set(ZlinkMessage.ZLINK_CALL, "0");
                    Settings.System.putInt(this.mContext.getContentResolver(), "isZlinkCalling", 0);
                    Log.m72d(TAG, "handle  ENTER");
                    return;
                case 4:
                    Log.m72d(TAG, "handle  EXIT");
                    return;
                case 5:
                    Log.m72d(TAG, "handle  MAIN_PAGE_SHOW");
                    this.zlinkShowing = true;
                    this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$CenterControlImpl$MU0h5QLUe0voNYR_9jssAIq6o_U
                        @Override // java.lang.Runnable
                        public final void run() {
                            CenterControlImpl.lambda$handle$1(CenterControlImpl.this);
                        }
                    }, 1500L);
                    return;
                case 6:
                    Log.m72d(TAG, "handle  MAIN_PAGE_HIDDEN");
                    if (!this.flgReleaseMicInConnected && !this.flgZlinkConnected) {
                        setTxzSleep(false);
                    }
                    this.zlinkShowing = false;
                    return;
                case 7:
                    this.mode = SystemStatusControl.getDefault().getSystemStatus().lastMode;
                    try {
                        this.originMode = PowerManagerApp.getStatusInt("systemMode");
                        Log.m72d(TAG, "handle  PHONE_RING_ON  mode = " + this.originMode + "   lastmode = " + this.mode);
                    } catch (RemoteException e4) {
                        e4.printStackTrace();
                    }
                    SystemProperties.set(ZlinkMessage.ZLINK_CALL, "1");
                    Settings.System.putInt(this.mContext.getContentResolver(), "isZlinkCalling", 1);
                    this.cpCallStatus = 1;
                    return;
                case '\b':
                    this.cpCallStatus = 2;
                    return;
                case '\t':
                    this.mode = SystemStatusControl.getDefault().getSystemStatus().lastMode;
                    try {
                        this.originMode = PowerManagerApp.getStatusInt("systemMode");
                        Log.m72d(TAG, "handle  PHONE_CALL_ON  mode = " + this.originMode + "   lastmode = " + this.mode);
                    } catch (RemoteException e5) {
                        e5.printStackTrace();
                    }
                    SystemProperties.set(ZlinkMessage.ZLINK_CALL, "1");
                    Settings.System.putInt(this.mContext.getContentResolver(), "isZlinkCalling", 1);
                    if (this.cpCallStatus == 0) {
                        this.cpCallStatus = 2;
                    }
                    if (this.cpCallStatus == 2) {
                        KswMcuSender.getSender().sendMessage(99, new byte[]{0, 3});
                    } else {
                        KswMcuSender.getSender().sendMessage(99, new byte[]{0, 2});
                    }
                    KswMcuSender.getSender().sendMessage(99, new byte[]{0, 1});
                    this.zlinkCalling = true;
                    return;
                case '\n':
                    try {
                        Log.m72d(TAG, "handle  PHONE_CALL_OFF   originMode = " + this.originMode);
                        KswMcuSender.getSender().sendMessage(99, new byte[]{0, 0});
                        if (this.originMode != -1 && this.originMode != PowerManagerApp.getStatusInt("systemMode") && this.mode != 6 && this.mode != 9 && this.mode != 5 && this.mode != 11) {
                            Log.m72d(TAG, "handle  PHONE_CALL_OFF   systemModeSwitch ");
                            if (this.originMode != 2) {
                                systemModeSwitch(this.originMode);
                            }
                        }
                    } catch (RemoteException e6) {
                        e6.printStackTrace();
                    }
                    SystemProperties.set(ZlinkMessage.ZLINK_CALL, "0");
                    Settings.System.putInt(this.mContext.getContentResolver(), "isZlinkCalling", 0);
                    this.zlinkCalling = false;
                    this.cpCallStatus = 0;
                    return;
                case 11:
                    Log.m72d(TAG, "handle  SWITCHOTG");
                    return;
                case '\f':
                    Log.m72d(TAG, "handle  SWITCHHUB");
                    return;
                case '\r':
                    if (this.iszlinkCall && SystemProperties.get(ZlinkMessage.ZLINK_CALL).equals("1")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CALL, "2");
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (message.command != null) {
            Log.m72d(TAG, "#11581 -- message.command != null --> message.command is " + message.command);
            String str2 = message.command;
            switch (str2.hashCode()) {
                case -2126047073:
                    if (str2.equals("CMD_MIC_STOP")) {
                        c = 4;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -1482963131:
                    break;
                case -1478680495:
                    if (str2.equals("SIRI_ON")) {
                        c = 5;
                        break;
                    }
                    c = '\uffff';
                    break;
                case -150661894:
                    if (str2.equals("ACTION_ZJ_PHONEFOUND")) {
                        c = 1;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 871291637:
                    if (str2.equals("REQ_OS_AUDIO_FOCUS")) {
                        c = 0;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1405544733:
                    if (str2.equals("SIRI_OFF")) {
                        c = 6;
                        break;
                    }
                    c = '\uffff';
                    break;
                case 1920379072:
                    if (str2.equals("ACTION_ZJ_IPODFOUND")) {
                        c = 2;
                        break;
                    }
                    c = '\uffff';
                    break;
                default:
                    c = '\uffff';
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 4:
                default:
                    return;
                case 3:
                    if (this.iszlinkCall && SystemProperties.get(ZlinkMessage.ZLINK_CALL).equals("1")) {
                        SystemProperties.set(ZlinkMessage.ZLINK_CALL, "2");
                        return;
                    }
                    return;
                case 5:
                    this.iszlinkCall = true;
                    return;
                case 6:
                    this.iszlinkCall = false;
                    return;
            }
        }
    }

    public static /* synthetic */ void lambda$handle$1(CenterControlImpl centerControlImpl) {
        if (!centerControlImpl.zlinkCalling && centerControlImpl.zlinkShowing) {
            Log.m72d(TAG, "handle  MAIN_PAGE_SHOW   BT MODE");
            getImpl().openSourceMode(3);
        }
    }

    public void handle(EcarMessage msg) {
        if (msg.ecarSendKey != null) {
            String str = msg.ecarSendKey;
            char c = '\uffff';
            int hashCode = str.hashCode();
            if (hashCode != -1909808850) {
                if (hashCode == -1362160651 && str.equals("EcarCallStart")) {
                    c = 0;
                }
            } else if (str.equals("EcarCallEnd")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    Log.m72d(TAG, "handle  EcarCallStart");
                    new TxzMessage(2040, "bluetooth.call", null).sendBroadCast(this.mContext);
                    return;
                case 1:
                    Log.m72d(TAG, "handle  EcarCallEnd");
                    new TxzMessage(2040, "bluetooth.hangup", null).sendBroadCast(this.mContext);
                    return;
                default:
                    return;
            }
        }
    }

    public void handle(AutoKitMessage msg) {
        int i = msg.receiveKey;
        if (i != 12) {
            switch (i) {
                case 2:
                    Log.m72d(TAG, "handle  AUTO BOX DISCONNECT");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CONNECT, "0");
                    openBluetooth(false);
                    try {
                        int oldStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                        if (oldStatus != 0 && PowerManagerApp.getStatusInt("systemMode") == 1) {
                            setTxzSleep(false);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 3:
                    getImpl().openSourceMode(3);
                    Log.m72d(TAG, "handle  PHONE_CALL_ON");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CALL, "1");
                    try {
                        if (!PowerManagerApp.getStatusString("topApp").equals(AutoKitCallBackImpl.AutoKitPkgName)) {
                            openApp(AutoKitCallBackImpl.AutoKitPkgName);
                            return;
                        }
                        return;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 4:
                    Log.m72d(TAG, "handle  PHONE_CALL_ON");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CALL, "0");
                    return;
                case 5:
                    Log.m72d(TAG, "handle  AUTO BOX CONNECT");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CONNECT, "1");
                    closeBluetooth();
                    try {
                        int oldStatus2 = PowerManagerApp.getSettingsInt("Support_TXZ");
                        if (oldStatus2 != 0 && PowerManagerApp.getStatusInt("systemMode") == 1) {
                            setTxzSleep(true);
                            return;
                        }
                        return;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return;
                    }
                default:
                    return;
            }
        } else if (SystemProperties.get(AutoKitMessage.AUTOBOX_CALL).equals("1")) {
            SystemProperties.set(AutoKitMessage.AUTOBOX_CALL, "2");
        }
    }

    public void sendCarInfo2Txz(boolean isNormalState, boolean iscarDoorClose, boolean isSeatBeltOn, boolean isNormalSpeed, boolean isTempNormal, boolean isEnoughOil, McuStatus.CarData cardata) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNormalState", isNormalState);
        bundle.putBoolean("cardoor", iscarDoorClose);
        bundle.putBoolean("seatbelt", isSeatBeltOn);
        bundle.putBoolean("isNormalSpeed", isNormalSpeed);
        bundle.putInt("speed", cardata == null ? 0 : cardata.speed);
        bundle.putBoolean("isEnoughOil", isEnoughOil);
        bundle.putBoolean("isTempNormal", isTempNormal);
        bundle.putFloat("ambient_temp", cardata == null ? 0.0f : cardata.airTemperature);
        TxzMessage txzMessage = new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.mcu.carinfo", bundle);
        txzMessage.sendBroadCast(this.mContext);
    }

    public void openSpeech() {
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.window.open", null).sendBroadCast(this.mContext);
    }

    public void closeSpeech() {
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.window.close", null).sendBroadCast(this.mContext);
    }

    public void setTxzWindow(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("mode", on ? "top" : "none");
        TxzMessage txzMessage = new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.float.mode", bundle);
        txzMessage.sendBroadCast(this.mContext);
    }

    public void setTxzSleep(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("status", on ? "before.sleep" : "wakeup");
        TxzMessage txzMessage = new TxzMessage(2010, "system.status", bundle);
        txzMessage.sendBroadCast(this.mContext);
    }

    public void setTxzQuickQuit(boolean on) {
        int camera360AppIsForeground = Settings.System.getInt(this.mContext.getContentResolver(), "camera360AppIsForeground", 0);
        Log.m68i(TAG, "setTxzQuickQuit camera360AppIsForeground: " + camera360AppIsForeground);
        if (camera360AppIsForeground > 0) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("status", on ? "reverse.enter" : "reverse.quit");
        TxzMessage txzMessage = new TxzMessage(2010, "system.status", bundle);
        txzMessage.sendBroadCast(this.mContext);
    }

    public void setTxzSwitch(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("enable", on);
        TxzMessage txzMessage = new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.enable", bundle);
        txzMessage.sendBroadCast(this.mContext);
    }

    public void openNavi() {
        try {
            closeSpeech();
            String naviApp = PowerManagerApp.getSettingsString("NaviApp");
            String freedomPkg = Settings.System.getString(this.mContext.getContentResolver(), "wits_freedom_pkg");
            if (naviApp.equals(freedomPkg)) {
                startActivityByWindowMode(naviApp, 1);
            } else {
                openApp(naviApp);
            }
        } catch (RemoteException e) {
        }
    }

    public void openMusic() {
        openMusic(false);
    }

    public void openMusic(boolean isAcc) {
        Intent intent = new Intent("com.wits.media.MUSIC");
        if (isAcc) {
            intent.putExtra("isAcc", true);
        }
        try {
            startAction(intent);
        } catch (Exception e) {
        }
    }

    public void openVideo() {
        openVideo(false);
    }

    public void openVideo(boolean isAcc) {
        Intent intent = new Intent("com.wits.media.VIDEO");
        if (isAcc) {
            intent.putExtra("isAcc", true);
        }
        try {
            startAction(intent);
        } catch (Exception e) {
        }
    }

    public void openVideoFirst(boolean isAcc) {
        Intent intent = this.mContext.getPackageManager().getLaunchIntentForPackage("com.wits.ksw.video");
        intent.setFlags(270532608);
        intent.putExtra("isAcc", isAcc);
        this.mContext.startActivity(intent);
    }

    public void openGallery() {
    }

    public void openAndroidBluetooth() {
        try {
            startAction("com.wits.bt.action.OPEN");
        } catch (Exception e) {
        }
    }

    public void sendCloseBTAppBroadcast() {
        Intent intent = new Intent("com.wits.bt.CLOSE_BT_APP");
        this.mContext.sendBroadcast(intent);
    }

    public void closeVideo() {
        WitsCommand.sendCommand(2, 112, "");
    }

    public void closeMusic() {
        WitsCommand.sendCommand(2, 106, "");
    }

    public void closePIP() {
        WitsCommand.sendCommand(2, 118, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicOpen() {
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void wifiOperation(boolean open) {
        WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        wifiManager.setWifiEnabled(open);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void closeApp(String pkgName) {
        Log.m68i(TAG, "close App in center control - " + pkgName);
        if ("com.txznet.music".equals(pkgName)) {
            this.mContext.sendBroadcast(new Intent("txz.tongting.exit"));
        } else {
            killApp(pkgName);
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void openApp(String pkgName) {
        Intent lastIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (TextUtils.isEmpty(pkgName) || filter(pkgName)) {
            return;
        }
        try {
            this.mContext.startActivity(lastIntent);
        } catch (Exception e) {
            if (pkgName.equals("com.autonavi.amapauto")) {
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName("com.autonavi.amapauto", "com.autonavi.auto.remote.fill.UsbFillActivity"));
                this.mContext.startActivity(launchIntent);
            }
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void openApp(String pkgName, String action) {
        Intent lastIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
        lastIntent.setAction(action);
        if (filter(pkgName)) {
            return;
        }
        try {
            this.mContext.startActivity(lastIntent);
        } catch (Exception e) {
            if (pkgName.equals("com.autonavi.amapauto")) {
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName("com.autonavi.amapauto", "com.autonavi.auto.remote.fill.UsbFillActivity"));
                this.mContext.startActivity(launchIntent);
            }
        }
    }

    public void kuWoMusicPlay(boolean coldBoot) {
        Log.m72d(TAG, "kuWoMusicPlay  coldBoot = " + coldBoot);
        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.CenterControlImpl.1
            @Override // java.lang.Runnable
            public void run() {
                new TxzMessage(2060, "music.play", null).sendBroadCast(CenterControlImpl.this.mContext);
            }
        }, coldBoot ? 3000L : 500L);
    }

    public void kuWoMusicPause() {
        if (KswSettings.getSettings().getSettingsString("currentMediaPkg").contains("kwmusiccar")) {
            KeyUtils.pressKey(127);
        }
    }

    public void handleZlinkBackCar(boolean isStart) {
        Log.m72d(TAG, "handleZlinkBackCar isStart: " + isStart);
        if (isStart) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("muteMedia", true);
            bundle.putString("command", "REQ_SPEC_FUNC_CMD");
            this.isSendZlinkCarBroadCast = true;
            new ZlinkMessage(ZlinkMessage.ZLINK_BACKCAR_START_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
        } else if (this.isSendZlinkCarBroadCast) {
            int camera360AppIsForeground = Settings.System.getInt(this.mContext.getContentResolver(), "camera360AppIsForeground", 0);
            Log.m68i(TAG, "handleZlinkBackCar camera360AppIsForeground: " + camera360AppIsForeground);
            if (camera360AppIsForeground > 0) {
                return;
            }
            this.isSendZlinkCarBroadCast = false;
            new ZlinkMessage(ZlinkMessage.ZLINK_BACKCAR_STOP_ACTION).sendBroadCast(this.mContext);
        }
    }

    public void txzMusicPlay(boolean coldBoot) {
        Log.m72d(TAG, "txzMusicPlay  coldBoot = " + coldBoot);
        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.core.CenterControlImpl.2
            @Override // java.lang.Runnable
            public void run() {
                new TxzMessage(2060, "music.play", null).sendBroadCast(CenterControlImpl.this.mContext);
            }
        }, coldBoot ? 500L : 100L);
    }

    public void startAction(String action) throws Exception {
        Intent lastIntent = new Intent();
        lastIntent.setAction(action);
        lastIntent.setFlags(268435456);
        this.mContext.startActivity(lastIntent);
    }

    public void startAction(Intent intent) throws Exception {
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    private boolean filter(String pkg) {
        pkg.getClass();
        return false;
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void stopMedia() {
        stopZlinkMusic();
        if (SystemProperties.get("persist.sys.hicar_connect").equals("true")) {
            stopHicarMusic(true);
        }
        TxzMessage txzMessage = new TxzMessage(2062, "mcu.open", null);
        CenterCallBackImpl.getImpl(this.mContext).sendBroadCast(txzMessage);
        this.mContext.sendBroadcast(new Intent("mcu.car_mode.open"));
        this.mContext.sendBroadcast(new Intent("com.android.minwin.clos"));
        if (Settings.System.getInt(this.mContext.getContentResolver(), "CarDisplay", 1) == 0) {
            return;
        }
        Log.m72d(TAG, "stopMedia  CarDisplay != 0");
        backToHome();
    }

    private void stopMusic() {
        Log.m72d(TAG, "stopMusic");
        KeyUtils.pressKey(86);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void stopZlinkMusic() {
        Log.m72d(TAG, "stopZlinkMusic");
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 127);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    public void stopHicarMusic(boolean isStop) {
        if (this.mAudioManager == null) {
            Log.m72d(TAG, "AudioManager is mull, can't stop Hicar Music");
        } else if (!isStop) {
            this.mAudioManager.abandonAudioFocus(null);
        } else {
            Log.m72d(TAG, "stop Hicar Music");
            this.mAudioManager.requestAudioFocus(null, 3, 1);
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void exitZlink() {
        Log.m72d(TAG, "exitZlink");
        Bundle bundle = new Bundle();
        bundle.putString("command", "ACTION_EXIT");
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "ACTION_EXIT", bundle).sendBroadCast(this.mContext);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void enterZlink() {
        Log.m72d(TAG, "enterZlink");
        Bundle bundle = new Bundle();
        bundle.putString("command", "ACTION_ENTER");
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "ACTION_ENTER", bundle).sendBroadCast(this.mContext);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void handupEcarPhone() {
        Log.m72d(TAG, "handupEcarPhone");
        Intent intent = new Intent("com.ecat.video.HangOnEvent");
        this.mContext.sendBroadcast(intent);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void zlinkHandleCall() {
        String callStatus = SystemProperties.get(ZlinkMessage.ZLINK_CALL);
        Log.m72d(TAG, "zlinkHandleCall  callStatus = " + callStatus);
        if (callStatus.equals("1")) {
            zlinkCallOn();
        } else if (callStatus.equals("2")) {
            zlinkCallOff();
        }
    }

    public boolean getHicarStatus() {
        return this.isLinkHicar;
    }

    public void zlinkHandleAutoCall() {
        int callStatus = 0;
        try {
            callStatus = PowerManagerApp.getStatusInt("callStatus");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        switch (callStatus) {
            case 4:
            case 6:
                handUpPhone();
                return;
            case 5:
                acceptPhone();
                return;
            default:
                return;
        }
    }

    public void autoKitHandleCall() {
        String callStatus = SystemProperties.get(AutoKitMessage.AUTOBOX_CALL);
        Log.m72d(TAG, "autoKitHandleCall  callStatus = " + callStatus);
        if (callStatus.equals("1")) {
            AutoKitCallBackImpl.getImpl(this.mContext).acceptPhone();
        } else if (callStatus.equals("2")) {
            AutoKitCallBackImpl.getImpl(this.mContext).rejectPhone();
        }
    }

    private void zlinkCallOn() {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 5);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    private void zlinkCallOff() {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 6);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    public void zlinkSiri() {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 1500);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    private void playZlinkMusic() {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 126);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    public void iDriveZlinkMsg(int keyCode) {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", keyCode);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    public boolean isCarplayConnected() {
        return SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT).equals("1");
    }

    public boolean isBTCalling() {
        try {
            boolean isConnected = PowerManagerApp.getStatusBoolean("isConnected");
            if (isConnected) {
                int callStatus = PowerManagerApp.getStatusInt("callStatus");
                if (callStatus == 4 || callStatus == 6 || callStatus == 5) {
                    return true;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void home() {
        try {
            String naviApp = Settings.System.getString(this.mContext.getContentResolver(), "NaviApp");
            String currentPkg = Settings.System.getString(this.mContext.getContentResolver(), "currentPkg");
            boolean isNavi = (currentPkg == null || naviApp == null || !currentPkg.equals(naviApp)) ? false : true;
            boolean isTalking = Settings.System.getInt(this.mContext.getContentResolver(), "isCalling") == 1;
            if (!isNavi && !isTalking) {
                KeyUtils.pressKey(3);
            }
        } catch (Exception e) {
            KeyUtils.pressKey(3);
        }
    }

    public void backToHome() {
        KeyUtils.pressKey(86);
        try {
            String naviApp = Settings.System.getString(this.mContext.getContentResolver(), "NaviApp");
            String currentPkg = Settings.System.getString(this.mContext.getContentResolver(), "currentPkg");
            boolean isNavi = (currentPkg == null || naviApp == null || !currentPkg.equals(naviApp)) ? false : true;
            boolean isTalking = Settings.Global.getInt(this.mContext.getContentResolver(), "ksw_home_ban") == 1;
            if (!isNavi && !isTalking) {
                KeyUtils.pressKey(3);
            }
        } catch (Exception e) {
            KeyUtils.pressKey(3);
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mediaPrevious() {
        KeyUtils.pressKey(88);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mediaNext() {
        KeyUtils.pressKey(87);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mediaPause() {
        KeyUtils.pressKey(127);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mediaPlay() {
        KeyUtils.pressKey(126);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mediaPlayPause() {
        KeyUtils.pressKey(85);
    }

    public void muteSwitch() {
        KeyUtils.pressKey(164);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void mute(boolean on) {
        if (on) {
            this.mAudioManager.adjustStreamVolume(3, -100, 8);
        } else {
            this.mAudioManager.adjustStreamVolume(3, 100, 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void muteWithUI(boolean on) {
        Log.m72d(TAG, "muteWithUI: isPlayingMusic =" + this.mLogicSystem.getBtPhoneStatus().isPlayingMusic + "  on =" + on);
        if (on) {
            if (!this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
                this.mAudioManager.adjustStreamVolume(3, -100, 1);
            } else {
                this.mAudioManager.adjustStreamVolume(2, -100, 1);
            }
        } else if (!this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
            this.mAudioManager.adjustStreamVolume(3, 100, 1);
        } else {
            this.mAudioManager.adjustStreamVolume(2, 100, 1);
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void volumeUp() {
        this.mAudioManager.adjustStreamVolume(3, 1, 1);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void volumeDown() {
        this.mAudioManager.adjustStreamVolume(3, -1, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void volumeUpBySystem() {
        if (this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
            this.mAudioManager.adjustStreamVolume(2, 1, 1);
            return;
        }
        if (this.mCurVolume != -1) {
            int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
            if (this.mCurVolume > streamMaxVolume - 1) {
                this.mAudioManager.setStreamVolume(3, streamMaxVolume, 1);
            }
            this.mCurVolume = -1;
        }
        this.mAudioManager.adjustStreamVolume(3, 1, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void volumeDownBySystem() {
        if (this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
            this.mAudioManager.adjustStreamVolume(2, -1, 1);
            return;
        }
        if (this.mCurVolume != -1 && this.mCurVolume > 0) {
            this.mAudioManager.setStreamVolume(3, this.mCurVolume, 1);
            this.mCurVolume = -1;
        }
        this.mAudioManager.adjustStreamVolume(3, -1, 1);
    }

    public boolean isBTCallingorTalking() {
        int btPhoneStatus = this.mLogicSystem.getBtPhoneStatus().getCallStatus();
        if (btPhoneStatus >= 4 && btPhoneStatus <= 6) {
            return true;
        }
        return false;
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void volumeMax() {
        int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        this.mAudioManager.setStreamVolume(3, streamMaxVolume, 1);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void volumeMin() {
        this.mAudioManager.setStreamVolume(3, 1, 1);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void powerOff() {
        SystemStatusControl.getDefault().dormant(true);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void sourceSwitch() {
        int nextMode = 1;
        for (int nextMode2 = 0; nextMode2 < this.sourceArray.length; nextMode2++) {
            if (this.sourceArray[nextMode2] == SystemStatusControl.getDefault().getSystemStatus().lastMode) {
                if (nextMode2 == this.sourceArray.length - 1) {
                    nextMode = this.sourceArray[0];
                } else {
                    nextMode = this.sourceArray[nextMode2 + 1];
                }
            }
        }
        if (nextMode == 5) {
            try {
                if (PowerManagerApp.getSettingsInt("DVR_Type") != 1) {
                    nextMode = 6;
                }
            } catch (RemoteException e) {
            }
        }
        if (nextMode == 6) {
            try {
                if (PowerManagerApp.getSettingsInt("AUX_Type") == 0) {
                    nextMode = 9;
                }
            } catch (RemoteException e2) {
            }
        }
        if (nextMode == 9) {
            try {
                if (PowerManagerApp.getSettingsInt("DTV_Type") == 0) {
                    nextMode = 1;
                }
            } catch (RemoteException e3) {
            }
        }
        String str = SystemStatusControl.getStatus().topApp;
        openSource(nextMode);
    }

    private void killApp(String pkgName) {
        if (!pkgName.equals("com.wits.ksw") && !pkgName.equals("com.wits.ksw.bt")) {
            AmsUtil.forceStopPackage(this.mContext, pkgName);
        }
    }

    public void openSource(int source) {
        Log.m68i(TAG, "try to open source:" + source);
        switch (source) {
            case 0:
                systemModeSwitch(2);
                return;
            case 1:
                openMusic();
                return;
            case 2:
                openVideo();
                return;
            case 3:
                openBluetooth(true);
                return;
            case 4:
            case 7:
            case 8:
            case 10:
            case 12:
            default:
                return;
            case 5:
                openCarDvr();
                return;
            case 6:
                openAux(true);
                return;
            case 9:
                openDtv(true);
                return;
            case 11:
                openFrontCamera(true);
                return;
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void fmOpen() {
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void fmClose() {
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void fmPrevious() {
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void fmNext() {
    }

    public void openSettings() {
        try {
            startAction("com.on.systemUi.start.voice");
        } catch (Exception e) {
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void call() {
        btPhoneCall("", "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void unCall() {
        handUpPhone();
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void displayScreen(boolean on) {
        SystemStatusControl.getDefault().getSystemStatus().screenSwitch = on ? 1 : 0;
        closeScreen(!on ? 1 : 0);
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void openBluetooth(final boolean open) {
        try {
            if (PowerManagerApp.getSettingsInt("BT_Type") == 1) {
                openCarBt();
            } else {
                this.mHandler.post(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$CenterControlImpl$gJb8KKPRIfzXSn_Q7tVXEAkTl3c
                    @Override // java.lang.Runnable
                    public final void run() {
                        CenterControlImpl.lambda$openBluetooth$2(CenterControlImpl.this, open);
                    }
                });
            }
        } catch (RemoteException e) {
            openAndroidBluetooth();
        }
    }

    public static /* synthetic */ void lambda$openBluetooth$2(CenterControlImpl centerControlImpl, boolean open) {
        if (!open || !SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || !SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1") || !"true".equals(SystemProperties.get("persist.sys.hicar_connect")) || !SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") || !SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1")) {
            Log.m68i(TAG, "openBluetooth:  open: " + open + " ZLINK_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") + " hicar_connect: " + "true".equals(SystemProperties.get("persist.sys.hicar_connect")) + " ZLINK_AIRPLAY_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") + " ZLINK_CARPLAY_WRIED_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1"));
            if (!"1".equals(SystemProperties.get(ZlinkMessage.ZLINK_CONNECT))) {
                Settings.System.putInt(centerControlImpl.mContext.getContentResolver(), "btSwitch", 1);
            }
        }
        if (!WitsCommand.sendCommandGetResult(3, 107, open ? "true" : "false")) {
            centerControlImpl.openAndroidBluetooth();
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void closeBluetooth() {
        Settings.System.putInt(this.mContext.getContentResolver(), "btSwitch", 0);
        WitsCommand.sendCommand(3, 105, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btPhoneCall(String name, String number) {
        WitsCommand.sendCommand(3, 108, number + "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void handUpPhone() {
        try {
            boolean isConnected = PowerManagerApp.getStatusBoolean("isConnected");
            if (isConnected) {
                WitsCommand.sendCommand(3, 109, "");
            }
        } catch (RemoteException e) {
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void acceptPhone() {
        try {
            boolean isConnected = PowerManagerApp.getStatusBoolean("isConnected");
            if (isConnected) {
                WitsCommand.sendCommand(3, 112, "");
            }
        } catch (RemoteException e) {
        }
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicPlayPause() {
        WitsCommand.sendCommand(3, 102, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicPrev() {
        WitsCommand.sendCommand(3, 100, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicNext() {
        WitsCommand.sendCommand(3, 101, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicContinue() {
        WitsCommand.sendCommand(3, 103, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicStop() {
        WitsCommand.sendCommand(3, 104, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void btMusicRelease() {
        WitsCommand.sendCommand(3, 104, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void switchSoundToCar() {
        WitsCommand.sendCommand(3, 111, "");
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void switchSoundToPhone() {
        WitsCommand.sendCommand(3, 110, "");
    }

    /* JADX WARN: Type inference failed for: r2v26, types: [com.wits.pms.core.CenterControlImpl$5] */
    @Override // com.wits.pms.ICmdListener
    public boolean handleCommand(String jsonMsg) throws RemoteException {
        WitsCommand witsCommand = WitsCommand.getWitsCommandFormJson(jsonMsg);
        if (witsCommand.getCommand() == 1) {
            int subCommand = witsCommand.getSubCommand();
            if (subCommand == 300) {
                clearMemory();
            } else if (subCommand == 699) {
                try {
                    McuStatus.KswMcuMsg kswMcuMsg = McuStatus.KswMcuMsg.getMsgFormJson(witsCommand.getJsonArg());
                    KswMcuSender.getSender().sendMessage(kswMcuMsg.cmdType, kswMcuMsg.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (subCommand == 801) {
                sendBenzControlData(McuStatus.BenzData.getStatusFromJson(witsCommand.getJsonArg()));
                return true;
            } else if (subCommand != 1001) {
                switch (subCommand) {
                    case 100:
                        mute(true);
                        return true;
                    case 101:
                        volumeUp();
                        return true;
                    case 102:
                        volumeDown();
                        return true;
                    case 103:
                        mediaPrevious();
                        return true;
                    case 104:
                        mediaNext();
                        return true;
                    case 105:
                        mediaPlay();
                        return true;
                    case 106:
                        mediaPause();
                        return true;
                    case 107:
                        sourceSwitch();
                        return true;
                    case 108:
                        openNavi();
                        return true;
                    case 109:
                        openSpeech();
                        return true;
                    case 110:
                        fmOpen();
                        return true;
                    case 111:
                        openSettings();
                        return true;
                    case 112:
                        displayScreen(true);
                        return true;
                    case 113:
                        displayScreen(false);
                        return true;
                    case 114:
                        KeyUtils.pressKey(3);
                        return true;
                    case 115:
                        KeyUtils.pressKey(4);
                        return true;
                    case 116:
                        acceptPhone();
                        return true;
                    case 117:
                        handUpPhone();
                        return true;
                    case 118:
                        powerOff();
                        return true;
                    case 119:
                        fmPrevious();
                        return true;
                    case 120:
                        fmNext();
                        return true;
                    case 121:
                        mediaPlayPause();
                        return true;
                    case 122:
                        if (TextUtils.isEmpty(witsCommand.getJsonArg())) {
                            return false;
                        }
                        usbHost("on".equals(witsCommand.getJsonArg()));
                        return true;
                    case 123:
                        callButton();
                        return true;
                    case 124:
                        SplashFlasher.flashLogo(witsCommand.getJsonArg());
                        break;
                    case 125:
                        KswSettings.getSettings().saveAndReboot();
                        break;
                    case 126:
                        if (this.installAppDialog == null) {
                            this.progressBar = new ProgressBar(this.mContext, null, 16842872);
                            this.progressBar.setPadding(20, 20, 20, 20);
                            this.installAppDialog = new AlertDialog.Builder(this.mContext, C3580R.C3583style.AlertDialogCustom).setCancelable(true).setTitle(C3580R.string.install_app).setView(this.progressBar).create();
                            this.installAppDialog.getWindow().setType(2003);
                            this.installAppDialog.show();
                            break;
                        } else if (this.installAppDialog.isShowing()) {
                            String arg = witsCommand.getJsonArg();
                            if (!TextUtils.isEmpty(arg)) {
                                int progress = Integer.parseInt(arg);
                                this.progressBar.setProgress(progress);
                                break;
                            }
                        }
                        break;
                    case 127:
                        if (this.installAppDialog != null && this.installAppDialog.isShowing()) {
                            this.installAppDialog.dismiss();
                        }
                        Toast.makeText(this.mContext, (int) C3580R.string.install_app_done, 0).show();
                        break;
                    default:
                        switch (subCommand) {
                            case 200:
                                File config = new File(witsCommand.getJsonArg());
                                if (config.exists()) {
                                    KswSettings.getSettings().updateConfig(config);
                                }
                                return true;
                            case 201:
                                PowerManager powerManager = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
                                new PowerManagerMirror(powerManager).shutdown(false, "CenterService shutdown", true);
                                return true;
                            case 202:
                                Log.m72d(TAG, "Task#17059 -- handleCommand: MCU_REBOOT");
                                try {
                                    KswMcuSender.getSender().sendMessage(105, new byte[]{WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, 5, 0});
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                return true;
                            default:
                                switch (subCommand) {
                                    case 601:
                                        systemModeSwitch(2);
                                        return true;
                                    case 602:
                                        systemModeSwitch(1);
                                        return true;
                                    case 603:
                                        try {
                                            String jsonArg = witsCommand.getJsonArg();
                                            if (TextUtils.isEmpty(jsonArg)) {
                                                int source = Integer.parseInt(jsonArg);
                                                exitSource(source);
                                            }
                                            return true;
                                        } catch (Exception e3) {
                                            return false;
                                        }
                                    case 604:
                                        try {
                                            openSourceMode(Integer.parseInt(witsCommand.getJsonArg()));
                                            return true;
                                        } catch (Exception e4) {
                                            return false;
                                        }
                                    case 605:
                                        openAux(false);
                                        return true;
                                    case 606:
                                        openDtv(false);
                                        return true;
                                    case 607:
                                        openBluetooth(true);
                                        return true;
                                    case 608:
                                        boolean using = witsCommand.getJsonArg().equals("true");
                                        if (getCallingPid() == AmsUtil.getPid("com.papago.M11General") || getCallingPid() == AmsUtil.getPid("com.papago.s1OBU")) {
                                            if (using) {
                                                this.mAudioManager.requestAudioFocus(null, 3, -3);
                                            } else {
                                                this.mAudioManager.abandonAudioFocus(null);
                                            }
                                        }
                                        usingNaviProtocol(using);
                                        return true;
                                    case 609:
                                        openCarDvr();
                                        return true;
                                    case 610:
                                        openFrontCamera(false);
                                        return true;
                                    case 611:
                                        checkCarToMcu();
                                        break;
                                    case 612:
                                        try {
                                            String[] data = witsCommand.getJsonArg().split(SmsManager.REGEX_PREFIX_DELIMITER);
                                            sendAirControl(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                                        } catch (Exception e5) {
                                            Log.m67i(TAG, "error AIRCON_CONTROL", e5);
                                        }
                                        return true;
                                    case 613:
                                        KswMcuSender.getSender().sendMessage(104, new byte[]{10, 0});
                                        KswMcuSender.getSender().sendMessage(KswMessage.obtainKswMcuMsg(11));
                                        return true;
                                    case 614:
                                        KswMcuSender.getSender().sendMessage(105, new byte[]{21, 1});
                                        Log.m68i(TAG, "Task#18097 -- handleCommand: STS_360_START");
                                        break;
                                    case 615:
                                        KswMcuSender.getSender().sendMessage(105, new byte[]{21, 0});
                                        Log.m68i(TAG, "Task#18097 -- handleCommand: STS_360_STOP");
                                        break;
                                }
                        }
                }
            } else {
                BtController.rebootBt();
            }
        } else if (witsCommand.getCommand() == 5) {
            McuCommandListener.handleCommand(witsCommand);
        } else if (witsCommand.getCommand() == 100) {
            final TouchControl touchControl = new TouchControl(this.mContext);
            new Thread() { // from class: com.wits.pms.core.CenterControlImpl.5
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    touchControl.opPointerEvent(0, 0, 0);
                    touchControl.opPointerEvent(0, 0, 1);
                }
            }.start();
        } else if (witsCommand.getCommand() == 9) {
            Log.m72d(TAG, "handleCommand: OTA_TYPE  subCommand =" + witsCommand.getSubCommand());
            int subCommand2 = witsCommand.getSubCommand();
            if (subCommand2 != 100) {
                if (subCommand2 != 108) {
                    switch (subCommand2) {
                        case 103:
                            boolean otaNetUpdated = false;
                            try {
                                otaNetUpdated = PowerManagerApp.getStatusBoolean("ota_net_updated");
                            } catch (RemoteException e6) {
                                e6.printStackTrace();
                            }
                            if (otaNetUpdated) {
                                Log.m72d(TAG, "handleCommand: ota updated,just need reboot");
                                return true;
                            }
                            if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && (Build.DISPLAY.contains("M600") || Build.DISPLAY.contains("M700"))) {
                                ABNetOTAUpdate.checkFile(this.mContext);
                            } else {
                                NetOTAUpdate.checkFile(this.mContext);
                            }
                            return true;
                        case 104:
                            Log.m72d(TAG, "handleCommand: OTA_UPGRADING progress =" + witsCommand.getJsonArg());
                            return true;
                    }
                }
                Log.m72d(TAG, "handleCommand: OTA_REBOOT_DEVICE");
                PowerManager pm = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
                if (pm != null) {
                    pm.reboot("");
                    Log.m72d(TAG, "handleCommand: OTA_REBOOT_DEVICE reboot");
                }
                return true;
            }
            return true;
        } else if (witsCommand.getCommand() == 20 && witsCommand.getSubCommand() == 100) {
            try {
                String[] arg2 = witsCommand.getJsonArg().split(SmsManager.REGEX_PREFIX_DELIMITER);
                Log.m72d(TAG, "FREE_FORM_LAUNCH  arg[0]=" + arg2[0] + " arg[1]=" + Integer.parseInt(arg2[1]));
                startActivityByWindowMode(arg2[0], Integer.parseInt(arg2[1]));
            } catch (Exception e7) {
                Log.m69e(TAG, "FREE_FORM_LAUNCH error", e7);
            }
        }
        return false;
    }

    private void startActivityByWindowMode(String pkg, int windowMode) {
        IActivityTaskManager activityTaskManager = IActivityTaskManager.Stub.asInterface(ServiceManager.getService(Context.ACTIVITY_TASK_SERVICE));
        int tempTaskId = getFreeformTaskId(this.mContext, pkg);
        Bundle bundle = getWindowModeActivityOptions(windowMode).toBundle();
        if (tempTaskId != 0) {
            try {
                activityTaskManager.startActivityFromRecents(tempTaskId, bundle);
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.getLaunchIntentForPackage(pkg) == null) {
            return;
        }
        Intent intent = packageManager.getLaunchIntentForPackage(pkg);
        this.mContext.startActivity(intent, bundle);
    }

    private ActivityOptions getWindowModeActivityOptions(int windowsMode) {
        ActivityOptions activityOptions = ActivityOptions.makeBasic();
        try {
            Method method = ActivityOptions.class.getMethod("setLaunchWindowingMode", Integer.TYPE);
            method.invoke(activityOptions, Integer.valueOf(windowsMode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityOptions.setLaunchBounds(getDisplayRect(windowsMode));
        return activityOptions;
    }

    private Rect getDisplayRect(int windowsMode) {
        if (windowsMode == 5) {
            return new Rect(180, 120, 870, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_READ_CONTACTS);
        }
        return new Rect(0, 0, 1280, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH);
    }

    private int getFreeformTaskId(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            if (taskInfo.topActivity.getPackageName().equals(packageName)) {
                int taskId = taskInfo.f11id;
                Log.m68i(TAG, "Abel ActivityTaskManagerService.java getFreeformTaskId packageName=" + packageName + ", taskId=" + taskId);
                return taskId;
            }
        }
        return 0;
    }

    private void sendAirControl(int data1, int data2) {
        KswMcuSender.getSender().sendMessage(122, new byte[]{(byte) data1, (byte) data2});
        KswMcuSender.getSender().sendMessageDelay(122, new byte[]{0, 0}, 200L);
    }

    public void startDialSenderLooper() {
        Log.m72d(TAG, "startDialSenderLooper");
        if (this.benzDialSenderThread == null) {
            this.benzDialSenderThread = new Thread() { // from class: com.wits.pms.core.CenterControlImpl.6
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    while (true) {
                        Log.m72d(CenterControlImpl.TAG, "startDialSenderLooper run");
                        CenterControlImpl.this.sendDial(CenterControlImpl.clockSort);
                        try {
                            Thread.sleep(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            this.benzDialSenderThread.start();
        }
    }

    public void sendDial(int clockSort2) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(11);
        int minute = calendar.get(12);
        Log.m72d(TAG, "sendDial hour \uff1a " + hour + ", minute : " + minute);
        KswMcuSender.getSender().sendMessage(123, new byte[]{(byte) clockSort2, (byte) hour, (byte) minute});
    }

    @Override // com.wits.pms.interfaces.CenterControl
    public void clearMemory() {
    }

    private void sendBenzControlData(McuStatus.BenzData benzData) {
        KswMcuSender sender = KswMcuSender.getSender();
        byte[] bArr = new byte[6];
        bArr[0] = (byte) (benzData.pressButton == 1 ? 1 : 0);
        bArr[1] = (byte) (benzData.pressButton == 2 ? 1 : 0);
        bArr[2] = (byte) (benzData.pressButton == 3 ? 1 : 0);
        bArr[3] = (byte) benzData.light1;
        bArr[4] = (byte) benzData.light2;
        bArr[5] = (byte) benzData.key3;
        sender.sendMessage(121, bArr);
    }

    private void callButton() {
        KeyUtils.pressKey(5);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r0v28, types: [com.wits.pms.core.CenterControlImpl$7] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.wits.pms.core.CenterControlImpl$8] */
    public void handleKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
            Log.m68i(TAG, "handleKeyEvent keycode - " + event.getKeyCode());
            if (event.getKeyCode() == 10000) {
                UpdateHelper.getInstance().sendUpdateMessage(new File("/sdcard/ksw_mcu.bin"));
            }
            if (SystemStatusControl.getStatus().lastMode == 3 || (this.mLogicSystem != null && this.mLogicSystem.getBtPhoneStatus().isPlayingMusic)) {
                switch (event.getKeyCode()) {
                    case 85:
                        btMusicPlayPause();
                        break;
                    case 87:
                        btMusicNext();
                        break;
                    case 88:
                        btMusicPrev();
                        break;
                    case 126:
                        btMusicContinue();
                        break;
                    case 127:
                        btMusicStop();
                        break;
                }
            }
            if (event.getKeyCode() >= 300) {
                Log.m68i(TAG, "catch custom keycode - " + event.getKeyCode());
            }
            switch (event.getKeyCode()) {
                case 300:
                    closeScreen(true);
                    return;
                case 301:
                    sourceSwitch();
                    return;
                case 302:
                    if (isCarplayConnected()) {
                        getImpl().zlinkSiri();
                        return;
                    }
                    String txzStatus = SystemProperties.get(TxzMessage.TXZ_SHOW_STATUS);
                    Log.m64w(TAG, "chen--onMcuMessage: txzstatus =" + txzStatus);
                    if (txzStatus.equals("1")) {
                        closeSpeech();
                        return;
                    } else {
                        openSpeech();
                        return;
                    }
                case 303:
                    openNavi();
                    return;
                case 320:
                    openBluetooth(false);
                    return;
                case 321:
                    closeBluetooth();
                    return;
                case 380:
                    systemModeSwitch(2);
                    break;
                case 381:
                    break;
                case MetricsProto.MetricsEvent.ACTION_WINDOW_UNDOCK_MAX /* 390 */:
                    openCarDvr();
                    return;
                case 400:
                    setTxzSwitch(false);
                    return;
                case 401:
                    setTxzSwitch(true);
                    return;
                case 500:
                    closeScreen(true);
                    return;
                case 501:
                    closeScreen(false);
                    return;
                case 1314:
                    usbHost(false);
                    return;
                case 1315:
                    muteWithUI(true);
                    return;
                case 1316:
                    muteWithUI(false);
                    return;
                case 1317:
                    WitsCommand.sendCommand(1, 201, "");
                    return;
                case 2021:
                    Log.m72d(TAG, "handleKeyEvent: OTA_START_UPGRADE");
                    WitsCommand.sendCommand(9, 103);
                    return;
                case 2022:
                    Log.m72d(TAG, "handleKeyEvent: OTA_REBOOT_DEVICE");
                    WitsCommand.sendCommand(9, 108);
                    return;
                case 2023:
                    KswMcuSender.getSender().sendMessage(112, new byte[]{HebrewProber.SPACE, -50});
                    return;
                case 2025:
                    KswMcuSender.getSender().sendMessage(112, new byte[]{31, 50});
                    return;
                case 2026:
                    try {
                        Utils.updateLocationEnabled(this.mContext, false, UserHandle.myUserId(), 1);
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.updateLocationEnabled(this.mContext, true, UserHandle.myUserId(), 1);
                    return;
                case 3024:
                    sendCarInfo2Txz(true, true, true, true, true, true, null);
                    return;
                case 3025:
                    sendCarInfo2Txz(false, false, true, true, true, true, null);
                    return;
                case 3026:
                    sendCarInfo2Txz(false, true, true, true, true, false, null);
                    return;
                case 5000:
                    KswSettings.getSettings().clearKeys();
                    return;
                case BluetoothHidHost.INPUT_CONNECT_FAILED_ALREADY_CONNECTED /* 5001 */:
                    KswSettings.getSettings().syncStatus();
                    return;
                case BluetoothHidHost.INPUT_CONNECT_FAILED_ATTEMPT_FAILED /* 5002 */:
                    KswSettings.getSettings().setInt("testabcdefg", 123);
                    Set<String> intKeysFromSp = KswSettings.getSettings().getIntKeysFromSp();
                    Log.m68i("lktTest", intKeysFromSp.toArray()[0] + "");
                    return;
                case BluetoothHidHost.INPUT_OPERATION_GENERIC_FAILURE /* 5003 */:
                    new Thread() { // from class: com.wits.pms.core.CenterControlImpl.7
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            int i = 0;
                            while (i < 120) {
                                i++;
                                KswMessage testMsg = McuStatus.ACData.getTestMsg2();
                                KswMcuLogic.getTestMcu().getListener().onMcuMessage(testMsg);
                                try {
                                    Thread.sleep(500L);
                                } catch (InterruptedException e2) {
                                }
                            }
                        }
                    }.start();
                    return;
                case BluetoothHidHost.INPUT_OPERATION_SUCCESS /* 5004 */:
                    KswMcuLogic.getTestMcu().getListener().onMcuMessage(new KswMessage(31, new byte[]{1, 2, 41, BluetoothHidDevice.ERROR_RSP_UNKNOWN, 4}));
                    return;
                case 5005:
                    final KswMcuListener listener = KswMcuLogic.getTestMcu().getListener();
                    listener.onMcuMessage(new KswMessage(31, new byte[]{17, 3, 6, 0, 0, 3, 22}));
                    new Thread() { // from class: com.wits.pms.core.CenterControlImpl.8
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Thread.sleep(TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                            } catch (InterruptedException e2) {
                            }
                            try {
                                byte[] bytes = "\u6e21\u53e3".getBytes();
                                byte[] bytes1 = "\u8521\u7434".getBytes();
                                byte[] bytes2 = "\u60e0\u5a01\u8bd5\u97f3\u789f3".getBytes();
                                byte[] bytes3 = "\\\u97f3\u4e50 1/20".getBytes();
                                byte[] data1 = new byte[bytes.length + 1];
                                byte[] data2 = new byte[bytes1.length + 1];
                                byte[] data3 = new byte[bytes2.length + 1];
                                byte[] data4 = new byte[bytes3.length + 1];
                                data1[0] = 1;
                                data2[0] = 2;
                                data3[0] = 3;
                                data4[0] = 4;
                                System.arraycopy(bytes, 0, data1, 1, bytes.length);
                                System.arraycopy(bytes1, 0, data2, 1, bytes.length);
                                System.arraycopy(bytes2, 0, data3, 1, bytes.length);
                                System.arraycopy(bytes3, 0, data4, 1, bytes.length);
                                listener.onMcuMessage(new KswMessage(32, data1));
                                listener.onMcuMessage(new KswMessage(32, data2));
                                listener.onMcuMessage(new KswMessage(32, data3));
                                listener.onMcuMessage(new KswMessage(32, data4));
                            } catch (Exception e3) {
                            }
                        }
                    }.start();
                    return;
                case 5006:
                    McuStatus.MediaStringInfo mediaStringInfo = new McuStatus.MediaStringInfo();
                    mediaStringInfo.name = "\u6e21\u53e3";
                    mediaStringInfo.artist = "\u8521\u7434";
                    mediaStringInfo.album = "\u60e0\u5a01\u8bd5\u97f3\u789f3";
                    mediaStringInfo.folderName = "\\\\\u97f3\u4e50 1/20";
                    PowerManagerApp.setStatusString("mcuMediaStringInfo", new Gson().toJson(mediaStringInfo));
                    McuStatus.MediaPlayStatus mediaPlayStatus = new McuStatus.MediaPlayStatus();
                    mediaPlayStatus.f2580ST = true;
                    mediaPlayStatus.ALS = true;
                    mediaPlayStatus.SCAN = true;
                    mediaPlayStatus.status = "WAIT";
                    mediaPlayStatus.type = 17;
                    PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson(mediaPlayStatus));
                    return;
                case 5007:
                    McuStatus.MediaStringInfo mediaStringInfoNew = new McuStatus.MediaStringInfo();
                    mediaStringInfoNew.name = "\u6e21\u53e31";
                    mediaStringInfoNew.artist = "\u8521\u74342";
                    mediaStringInfoNew.album = "\u60e0\u5a01\u8bd5\u97f3\u789f32";
                    mediaStringInfoNew.folderName = "\\\u97f3\u4e501 1/220";
                    PowerManagerApp.setStatusString("mcuMediaStringInfo", new Gson().toJson(mediaStringInfoNew));
                    McuStatus.MediaPlayStatus mediaPlayStatusNew = new McuStatus.MediaPlayStatus();
                    mediaPlayStatusNew.status = "PLAY";
                    mediaPlayStatusNew.type = 17;
                    PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson(mediaPlayStatusNew));
                    return;
                case 5211:
                    this.mAudioManager.setParameters("hfp_enable=true");
                    return;
                case 5212:
                    this.mAudioManager.setParameters("hfp_enable=false");
                    return;
                default:
                    return;
            }
            systemModeSwitch(1);
        }
    }

    public void startTxzService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.txznet.adapter", "com.txznet.sdk.TXZService"));
        this.mContext.startService(intent);
    }

    public void openSourceMode(int i) {
        try {
            if (PowerManagerApp.getSettingsInt("car_manufacturer") == 4 && PowerManagerApp.getSettingsInt("OEM_FM") == 1 && i == 0) {
                i = 14;
            }
            getMcuSender().sendMessage(103, new byte[]{(byte) i});
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void usbHost(boolean on) {
        UsbUtil.updateUsbMode(on);
    }

    private void openCarDvr() {
        backToHome();
        try {
            if (PowerManagerApp.getSettingsInt("DVR_Type") == 1) {
                getMcuSender().sendMessage(103, new byte[]{5});
            }
        } catch (RemoteException e) {
        }
    }

    public void openDtv(boolean home) {
        try {
            if (PowerManagerApp.getSettingsInt("DTV_Type") == 1) {
                if (home) {
                    home();
                }
                stopMusic();
                getMcuSender().sendMessage(103, new byte[]{9});
            }
        } catch (RemoteException e) {
        }
    }

    public void checkCarToMcu() {
        getMcuSender().sendMessage(KswMessage.obtain(104, new byte[]{9, 0}));
    }

    public void openAux(boolean home) {
        try {
            if (PowerManagerApp.getSettingsInt("AUX_Type") == 1) {
                if (home) {
                    home();
                }
                stopMusic();
                getMcuSender().sendMessage(103, new byte[]{6});
            }
        } catch (RemoteException e) {
        }
    }

    public void openCarFm() {
        stopMusic();
        this.mContext.sendBroadcast(new Intent("mcu.car_mode.open"));
        this.mContext.sendBroadcast(new Intent("com.android.minwin.clos"));
        ComponentName componentName = new ComponentName("com.wits.ksw", "com.wits.ksw.launcher.view.lexus.OEMFMActivity");
        Intent carFmIntent = new Intent();
        carFmIntent.setComponent(componentName);
        try {
            getImpl().startAction(carFmIntent);
        } catch (Exception e) {
            Log.m69e(TAG, "open car fm error", e);
        }
    }

    public void openFrontCamera(boolean home) {
        try {
            if (PowerManagerApp.getSettingsInt("Front_view_camera") == 1) {
                if (home) {
                    home();
                }
                stopMusic();
                getMcuSender().sendMessage(103, new byte[]{11});
            }
        } catch (RemoteException e) {
        }
    }

    public void openCarBt() {
        getMcuSender().sendMessage(103, new byte[]{0});
        getMcuSender().sendMessage(105, new byte[]{18, 2});
    }

    @Override // com.wits.pms.ICmdListener
    public void updateStatusInfo(String jsonMsg) throws RemoteException {
    }

    @Override // com.wits.pms.custom.KswFunction
    public void setBrightness(int brightness) {
        KswMcuSender.getSender().sendMessage(108, new byte[]{1, (byte) brightness});
    }

    @Override // com.wits.pms.custom.KswFunction
    public void systemModeSwitch(int mode) {
        KswMcuSender sender = KswMcuSender.getSender();
        byte[] bArr = new byte[2];
        bArr[0] = 18;
        bArr[1] = (byte) (mode != 2 ? 1 : 2);
        sender.sendMessage(105, bArr);
    }

    @Override // com.wits.pms.custom.KswFunction
    public void closeScreen(boolean isClose) {
        KswMcuSender.getSender().sendMessage(108, new byte[]{2, (byte) (!isClose ? 1 : 0)});
        SystemStatusControl.getDefault().getSystemStatus().screenSwitch = !isClose ? 1 : 0;
        SystemStatusControl.getDefault().handleSystemStatus();
    }

    @Override // com.wits.pms.custom.KswFunction
    public void exitSource(int source) {
        KswMcuSender.getSender().sendMessage(104, new byte[]{4, (byte) source});
    }

    @Override // com.wits.pms.custom.KswFunction
    public void usingNaviProtocol(boolean using) {
        if (using) {
            this.mHandler.removeCallbacks(this.stopNaviProtocolRunnable);
            KswMcuSender.getSender().sendMessage(105, new byte[]{19, 1, 0});
            return;
        }
        this.mHandler.postDelayed(this.stopNaviProtocolRunnable, 500L);
    }

    @Override // com.wits.pms.custom.KswFunction
    public void getCarData() {
        KswMcuSender.getSender().sendMessage(104, new byte[]{5, 0});
    }

    public void setLogic(LogicSystem logicSystem) {
        this.mLogicSystem = logicSystem;
    }

    public boolean handleCall() throws RemoteException {
        if (SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1")) {
            zlinkHandleCall();
            return true;
        } else if (SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_AUTO_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_ANDROID_MIRROR_CONNECT).equals("1")) {
            zlinkHandleAutoCall();
            return true;
        } else if (SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
            autoKitHandleCall();
            return true;
        } else if (SystemStatusControl.getStatus().topApp.equals(KswSettings.getSettings().getSettingsString("NaviApp"))) {
            int callStatus = PowerManagerApp.getStatusInt("callStatus");
            switch (callStatus) {
                case 4:
                case 6:
                    handUpPhone();
                    break;
                case 5:
                    acceptPhone();
                    break;
            }
            return BtPhoneStatus.isCalling(callStatus);
        } else {
            return false;
        }
    }

    public void saveForwardCamMirror(int on) {
        Log.m72d(TAG, "forwardCamMirror init is " + on);
        try {
            PowerManagerApp.setSettingsInt("forwardCamMirror", on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initBenzClockSort(int clockSort2) {
        Log.m72d(TAG, "clockSort init is " + clockSort2);
        if (!hasClockConfigFunction()) {
            return;
        }
        updateClockSort(clockSort2);
        startDialSenderLooper();
    }

    private void updateClockSort(int clockSort2) {
        Log.m72d(TAG, "updateClockSort " + clockSort2);
        try {
            KswSettings.getSettings().setIntWithoutMCU("benzClockSort", clockSort2);
            clockSort = clockSort2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasClockConfigFunction() {
        try {
            return PowerManagerApp.getSettingsInt("car_manufacturer") == 2;
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void configDialByUser(int clockSort2) {
        Log.m72d(TAG, "configDialByUser " + clockSort2);
        if (!hasClockConfigFunction()) {
            return;
        }
        updateClockSort(clockSort2);
        sendDial(clockSort2);
    }

    public int getCpCallStatus() {
        return this.cpCallStatus;
    }

    private void closeWifiHotspot() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        new ConnectivityManagerMirror(mConnectivityManager).stopTethering(0);
    }
}
