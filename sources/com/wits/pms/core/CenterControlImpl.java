package com.wits.pms.core;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.job.JobInfo;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidHost;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.PreciseDisconnectCause;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TimedRemoteCaller;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.internal.logging.nano.MetricsProto;
import com.google.gson.Gson;
import com.wits.pms.ICmdListener;
import com.wits.pms.R;
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
import java.util.Calendar;
import java.util.Set;
import org.mozilla.universalchardet.prober.HebrewProber;

public class CenterControlImpl extends ICmdListener.Stub implements CenterControl, KswFunction {
    static final boolean DBG = false;
    static final String TAG = "CenterControl";
    /* access modifiers changed from: private */
    public static int clockSort = -1;
    private static CenterControlImpl mCenterControl;
    private int beforeMuteVolume;
    private Thread benzDialSenderThread;
    private int cpCallStatus = 0;
    private boolean flgReleaseMicInConnected = true;
    private boolean flgZlinkConnected = false;
    private AlertDialog installAppDialog;
    boolean iszlinkCall = false;
    private ActivityManager mActivityManager;
    private final AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public Context mContext;
    private int mCurVolume = -1;
    private final Handler mHandler;
    private LogicSystem mLogicSystem;
    private McuSender mMcuSender;
    private MemoryKiller mMemoryKiller;
    int mode;
    private boolean muteStatus;
    int originMode = -1;
    private ProgressBar progressBar;
    private int[] sourceArray = {1, 2, 3, 5, 6, 9};
    private boolean statusChange;
    private Runnable stopNaviProtocolRunnable = $$Lambda$CenterControlImpl$23q0S7nyA9AiGRMC2fProAbCiZ0.INSTANCE;
    private final Runnable volumeDownBySystemRun = new Runnable() {
        public void run() {
            CenterControlImpl.this.volumeDownBySystem();
        }
    };
    private final Runnable volumeUpBySystemRun = new Runnable() {
        public void run() {
            CenterControlImpl.this.volumeUpBySystem();
        }
    };
    boolean zlinkCalling;
    boolean zlinkShowing;

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
        if (context != null) {
            mCenterControl = new CenterControlImpl(context);
            CenterCallBackImpl.init(context);
            AutoKitCallBackImpl.init(context);
            ReceiverHandler.init(context);
        }
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
        try {
            this.mContext.getPackageManager().getPackageInfo(uri, 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0321, code lost:
        if (r0.equals("music.loop.single") != false) goto L_0x0362;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x03e5, code lost:
        if (r0.equals("radio.open") != false) goto L_0x03fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00bb, code lost:
        if (r0.equals("txz.voice.init.success") != false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x047a, code lost:
        if (r0.equals("music.close") != false) goto L_0x04b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x0616, code lost:
        if (r0.equals("volume.up") != false) goto L_0x0624;
     */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0401  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0405  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0409  */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x040d  */
    /* JADX WARNING: Removed duplicated region for block: B:365:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(com.wits.pms.bean.TxzMessage r18) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            java.lang.String r3 = r2.action
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "handle: TxzMessage keyType = "
            r4.append(r5)
            int r5 = r2.keyType
            r4.append(r5)
            java.lang.String r5 = "  action = "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.i(r0, r4)
            java.lang.String r0 = "vendor.wits.txz.init"
            java.lang.String r4 = "1"
            com.wits.pms.mirror.SystemProperties.set(r0, r4)
            int r0 = r2.keyType
            r4 = 9
            r5 = 8
            r6 = 13
            r7 = 7
            r8 = 5
            r9 = 6
            r10 = 4
            r11 = -1
            r12 = 2
            r13 = 3
            r14 = 0
            r15 = 1
            switch(r0) {
                case 1000: goto L_0x0719;
                case 1010: goto L_0x06dc;
                case 1020: goto L_0x0662;
                case 1030: goto L_0x05ca;
                case 1040: goto L_0x0413;
                case 1050: goto L_0x03ba;
                case 1060: goto L_0x02e6;
                case 1061: goto L_0x02e6;
                case 1091: goto L_0x01eb;
                case 2021: goto L_0x0097;
                case 3001: goto L_0x0043;
                case 10000: goto L_0x01c2;
                default: goto L_0x0041;
            }
        L_0x0041:
            goto L_0x0755
        L_0x0043:
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "TXZ mesage -- 3001: action is "
            r4.append(r5)
            java.lang.String r5 = r2.action
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
            java.lang.String r0 = r2.action
            int r4 = r0.hashCode()
            r5 = -1311128991(0xffffffffb1d9c261, float:-6.3376295E-9)
            if (r4 == r5) goto L_0x0067
            goto L_0x0070
        L_0x0067:
            java.lang.String r4 = "carmode.open"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0070
            goto L_0x0071
        L_0x0070:
            r14 = r11
        L_0x0071:
            if (r14 == 0) goto L_0x0075
            goto L_0x0755
        L_0x0075:
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "TXZ mesage -- 3001: action is "
            r4.append(r5)
            java.lang.String r5 = r2.action
            r4.append(r5)
            java.lang.String r5 = " -- SWITCH TO CARMODE"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
            r1.systemModeSwitch(r12)
            goto L_0x0755
        L_0x0097:
            java.lang.String r0 = r2.action
            int r4 = r0.hashCode()
            switch(r4) {
                case -1835123299: goto L_0x00c8;
                case -1076571994: goto L_0x00be;
                case -1005105447: goto L_0x00b5;
                case -912126038: goto L_0x00ab;
                case 470324425: goto L_0x00a1;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            goto L_0x00d2
        L_0x00a1:
            java.lang.String r4 = "txz.voice.disable.success"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00d2
            r10 = r12
            goto L_0x00d3
        L_0x00ab:
            java.lang.String r4 = "txz.recorder.status"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00d2
            r10 = r13
            goto L_0x00d3
        L_0x00b5:
            java.lang.String r4 = "txz.voice.init.success"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00d2
            goto L_0x00d3
        L_0x00be:
            java.lang.String r4 = "txz.voice.status"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00d2
            r10 = r15
            goto L_0x00d3
        L_0x00c8:
            java.lang.String r4 = "txz.init.success"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00d2
            r10 = r14
            goto L_0x00d3
        L_0x00d2:
            r10 = r11
        L_0x00d3:
            switch(r10) {
                case 0: goto L_0x0186;
                case 1: goto L_0x011f;
                case 2: goto L_0x0116;
                case 3: goto L_0x0103;
                case 4: goto L_0x00d8;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x01c2
        L_0x00d8:
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ RemoteException -> 0x00fd }
            java.lang.String r4 = "centerService"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x00fd }
            r5.<init>()     // Catch:{ RemoteException -> 0x00fd }
            java.lang.String r6 = "get txz.voice.init.success, send Support_TXZ "
            r5.append(r6)     // Catch:{ RemoteException -> 0x00fd }
            r5.append(r0)     // Catch:{ RemoteException -> 0x00fd }
            java.lang.String r5 = r5.toString()     // Catch:{ RemoteException -> 0x00fd }
            android.util.Log.d(r4, r5)     // Catch:{ RemoteException -> 0x00fd }
            if (r0 != r15) goto L_0x00f8
            r14 = r15
        L_0x00f8:
            r1.setTxzSwitch(r14)     // Catch:{ RemoteException -> 0x00fd }
            goto L_0x01c2
        L_0x00fd:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x01c2
        L_0x0103:
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "before.sleep"
            boolean r0 = r0.getBoolean(r4)
            if (r0 != 0) goto L_0x01c2
            r4 = 115(0x73, float:1.61E-43)
            java.lang.String r5 = ""
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r13, r4, r5)
            goto L_0x01c2
        L_0x0116:
            r0 = 115(0x73, float:1.61E-43)
            java.lang.String r4 = ""
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r13, r0, r4)
            goto L_0x01c2
        L_0x011f:
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "isDisable"
            boolean r4 = r0.getBoolean(r4)
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "TXZhandle: TxzMessage app isDisable ="
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r0, r5)
            boolean r0 = r17.isCarplayConnected()
            if (r0 != 0) goto L_0x01c2
            boolean r0 = r17.isBTCalling()
            if (r0 != 0) goto L_0x01c2
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ RemoteException -> 0x0181 }
            java.lang.String r5 = "CenterControl"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0181 }
            r6.<init>()     // Catch:{ RemoteException -> 0x0181 }
            java.lang.String r7 = "TXZhandle: TxzMessage system state isDisable ="
            r6.append(r7)     // Catch:{ RemoteException -> 0x0181 }
            if (r0 != r15) goto L_0x015f
            r7 = r15
            goto L_0x0160
        L_0x015f:
            r7 = r14
        L_0x0160:
            r6.append(r7)     // Catch:{ RemoteException -> 0x0181 }
            java.lang.String r6 = r6.toString()     // Catch:{ RemoteException -> 0x0181 }
            android.util.Log.d(r5, r6)     // Catch:{ RemoteException -> 0x0181 }
            if (r4 == 0) goto L_0x016e
            if (r0 == r15) goto L_0x0172
        L_0x016e:
            if (r4 != 0) goto L_0x0185
            if (r0 == r15) goto L_0x0185
        L_0x0172:
            if (r0 != r15) goto L_0x0176
            r14 = r15
        L_0x0176:
            r1.setTxzSwitch(r14)     // Catch:{ RemoteException -> 0x0181 }
            java.lang.String r5 = "CenterControl"
            java.lang.String r6 = "TxzMessage handle: txz state != system state, update  txz state"
            android.util.Log.d(r5, r6)     // Catch:{ RemoteException -> 0x0181 }
            goto L_0x0185
        L_0x0181:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0185:
            goto L_0x01c2
        L_0x0186:
            boolean r0 = r17.isCarplayConnected()
            if (r0 != 0) goto L_0x01c2
            boolean r0 = r17.isBTCalling()
            if (r0 != 0) goto L_0x01c2
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ RemoteException -> 0x01bd }
            java.lang.String r4 = "CenterControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x01bd }
            r5.<init>()     // Catch:{ RemoteException -> 0x01bd }
            java.lang.String r6 = "TxzMessage  set Support_TXZ "
            r5.append(r6)     // Catch:{ RemoteException -> 0x01bd }
            r5.append(r0)     // Catch:{ RemoteException -> 0x01bd }
            java.lang.String r5 = r5.toString()     // Catch:{ RemoteException -> 0x01bd }
            android.util.Log.d(r4, r5)     // Catch:{ RemoteException -> 0x01bd }
            if (r0 != r15) goto L_0x01b2
            r14 = r15
        L_0x01b2:
            r1.setTxzSwitch(r14)     // Catch:{ RemoteException -> 0x01bd }
            java.lang.String r4 = "CenterControl"
            java.lang.String r5 = "TxzMessage handle: syn txz state after boot "
            android.util.Log.d(r4, r5)     // Catch:{ RemoteException -> 0x01bd }
            goto L_0x01c1
        L_0x01bd:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01c1:
        L_0x01c2:
            android.os.Bundle r0 = r2.bundle     // Catch:{ Exception -> 0x01cd }
            java.lang.String r4 = "curVolumeInt"
            int r0 = r0.getInt(r4)     // Catch:{ Exception -> 0x01cd }
            r1.mCurVolume = r0     // Catch:{ Exception -> 0x01cd }
            goto L_0x01d1
        L_0x01cd:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01d1:
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "handle: mCurVolume ="
            r4.append(r5)
            int r5 = r1.mCurVolume
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
            goto L_0x0755
        L_0x01eb:
            java.lang.String r0 = "dvr.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0200
            com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.getSettings()
            java.lang.String r4 = "DVRApk_PackageName"
            java.lang.String r0 = r0.getSettingsString(r4)
            r1.openApp(r0)
        L_0x0200:
            java.lang.String r0 = "dvr.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0215
            com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.getSettings()
            java.lang.String r4 = "DVRApk_PackageName"
            java.lang.String r0 = r0.getSettingsString(r4)
            r1.closeApp(r0)
        L_0x0215:
            java.lang.String r0 = "video.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0225
            java.lang.String r0 = "com.txznet.music"
            r1.killApp(r0)
            r17.openVideo()
        L_0x0225:
            java.lang.String r0 = "video.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0230
            r17.closeVideo()
        L_0x0230:
            java.lang.String r0 = "tv.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x023b
            r1.openDtv(r15)
        L_0x023b:
            java.lang.String r0 = "tv.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0246
            r1.systemModeSwitch(r15)
        L_0x0246:
            java.lang.String r0 = "aux.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0251
            r1.openAux(r15)
        L_0x0251:
            java.lang.String r0 = "aux.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x025c
            r1.systemModeSwitch(r15)
        L_0x025c:
            java.lang.String r0 = "original_car.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x027f
            com.wits.pms.mcu.custom.KswMcuSender r0 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            r4 = 103(0x67, float:1.44E-43)
            byte[] r5 = new byte[r15]
            r5[r14] = r14
            r0.sendMessage(r4, r5)
            com.wits.pms.mcu.custom.KswMcuSender r0 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            r4 = 105(0x69, float:1.47E-43)
            byte[] r5 = new byte[r12]
            r5 = {18, 2} // fill-array
            r0.sendMessage(r4, r5)
        L_0x027f:
            java.lang.String r0 = "mobile_internet.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x028c
            java.lang.String r0 = "net.easyconn"
            r1.openApp(r0)
        L_0x028c:
            java.lang.String r0 = "android.home"
            r0.equals(r3)
            com.wits.pms.mcu.custom.KswMcuSender r0 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            r4 = 105(0x69, float:1.47E-43)
            byte[] r5 = new byte[r12]
            r5 = {18, 1} // fill-array
            r0.sendMessage(r4, r5)
            java.lang.String r0 = "music.play.local"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0755
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "path"
            java.lang.String r4 = r0.getString(r4)
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "music.play.local   path = "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r0, r5)
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            r5 = r0
            java.lang.String r0 = "com.wits.media.MUSIC"
            r5.setAction(r0)
            java.lang.String r0 = "txzPlay"
            r5.putExtra((java.lang.String) r0, (boolean) r15)
            java.lang.String r0 = "path"
            r5.putExtra((java.lang.String) r0, (java.lang.String) r4)
            r1.startAction((android.content.Intent) r5)     // Catch:{ Exception -> 0x02de }
            goto L_0x02e4
        L_0x02de:
            r0 = move-exception
            r6 = r0
            r0 = r6
            r0.printStackTrace()
        L_0x02e4:
            goto L_0x0755
        L_0x02e6:
            java.lang.String r0 = r2.action
            int r6 = r0.hashCode()
            switch(r6) {
                case -1362146736: goto L_0x0357;
                case -825432804: goto L_0x034d;
                case -825393037: goto L_0x0343;
                case -825367203: goto L_0x0339;
                case -825361316: goto L_0x032f;
                case -770549500: goto L_0x0324;
                case -734529399: goto L_0x031b;
                case 171428079: goto L_0x0311;
                case 183111917: goto L_0x0307;
                case 1438537900: goto L_0x02fc;
                case 1461409024: goto L_0x02f1;
                default: goto L_0x02ef;
            }
        L_0x02ef:
            goto L_0x0361
        L_0x02f1:
            java.lang.String r4 = "music.loop.all"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r5
            goto L_0x0362
        L_0x02fc:
            java.lang.String r4 = "music.random"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r7
            goto L_0x0362
        L_0x0307:
            java.lang.String r4 = "music.pause"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r10
            goto L_0x0362
        L_0x0311:
            java.lang.String r4 = "music.close"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r15
            goto L_0x0362
        L_0x031b:
            java.lang.String r5 = "music.loop.single"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x0361
            goto L_0x0362
        L_0x0324:
            java.lang.String r4 = "music.loop.random"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = 10
            goto L_0x0362
        L_0x032f:
            java.lang.String r4 = "music.prev"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r9
            goto L_0x0362
        L_0x0339:
            java.lang.String r4 = "music.play"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r12
            goto L_0x0362
        L_0x0343:
            java.lang.String r4 = "music.open"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r14
            goto L_0x0362
        L_0x034d:
            java.lang.String r4 = "music.next"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r8
            goto L_0x0362
        L_0x0357:
            java.lang.String r4 = "music.continue"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0361
            r4 = r13
            goto L_0x0362
        L_0x0361:
            r4 = r11
        L_0x0362:
            r5 = 500(0x1f4, double:2.47E-321)
            switch(r4) {
                case 0: goto L_0x03b4;
                case 1: goto L_0x03b0;
                case 2: goto L_0x0397;
                case 3: goto L_0x038c;
                case 4: goto L_0x0381;
                case 5: goto L_0x0376;
                case 6: goto L_0x036b;
                case 7: goto L_0x036a;
                case 8: goto L_0x0369;
                case 9: goto L_0x0368;
                default: goto L_0x0367;
            }
        L_0x0367:
            goto L_0x03b8
        L_0x0368:
            goto L_0x03b8
        L_0x0369:
            goto L_0x03b8
        L_0x036a:
            goto L_0x03b8
        L_0x036b:
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$DWovm9NibHt3DLTeYdTBgUEQaMQ r4 = new com.wits.pms.core.-$$Lambda$DWovm9NibHt3DLTeYdTBgUEQaMQ
            r4.<init>()
            r0.postDelayed(r4, r5)
            goto L_0x03b8
        L_0x0376:
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$g8OnSrjKr4iJCaVGk2HNyM7Qk3M r4 = new com.wits.pms.core.-$$Lambda$g8OnSrjKr4iJCaVGk2HNyM7Qk3M
            r4.<init>()
            r0.postDelayed(r4, r5)
            goto L_0x03b8
        L_0x0381:
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$821yd6MZoim56feZSH7LwWBSB30 r4 = new com.wits.pms.core.-$$Lambda$821yd6MZoim56feZSH7LwWBSB30
            r4.<init>()
            r0.postDelayed(r4, r5)
            goto L_0x03b8
        L_0x038c:
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$zpVvdyJDIdGRAYWptfdkD6nqGGo r4 = new com.wits.pms.core.-$$Lambda$zpVvdyJDIdGRAYWptfdkD6nqGGo
            r4.<init>()
            r0.postDelayed(r4, r5)
            goto L_0x03b8
        L_0x0397:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r4 = "package"
            java.lang.String r5 = "com.txznet.music"
            r0.putString(r4, r5)
            com.wits.pms.bean.TxzMessage r4 = new com.wits.pms.bean.TxzMessage
            r5 = 1000(0x3e8, float:1.401E-42)
            java.lang.String r6 = "app.open"
            r4.<init>(r5, r6, r0)
            r1.handle((com.wits.pms.bean.TxzMessage) r4)
            goto L_0x03b8
        L_0x03b0:
            r17.closeMusic()
            goto L_0x03b8
        L_0x03b4:
            r17.openMusic()
        L_0x03b8:
            goto L_0x0755
        L_0x03ba:
            java.lang.String r0 = r2.action
            int r4 = r0.hashCode()
            r5 = -1480935867(0xffffffffa7bab645, float:-5.1822988E-15)
            if (r4 == r5) goto L_0x03f2
            r5 = -47450874(0xfffffffffd2bf506, float:-1.4285639E37)
            if (r4 == r5) goto L_0x03e8
            r5 = -47411107(0xfffffffffd2c905d, float:-1.433605E37)
            if (r4 == r5) goto L_0x03df
            r5 = -47379386(0xfffffffffd2d0c46, float:-1.4376261E37)
            if (r4 == r5) goto L_0x03d5
            goto L_0x03fc
        L_0x03d5:
            java.lang.String r4 = "radio.prev"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x03fc
            r14 = r12
            goto L_0x03fd
        L_0x03df:
            java.lang.String r4 = "radio.open"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x03fc
            goto L_0x03fd
        L_0x03e8:
            java.lang.String r4 = "radio.next"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x03fc
            r14 = r13
            goto L_0x03fd
        L_0x03f2:
            java.lang.String r4 = "radio.close"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x03fc
            r14 = r15
            goto L_0x03fd
        L_0x03fc:
            r14 = r11
        L_0x03fd:
            switch(r14) {
                case 0: goto L_0x040d;
                case 1: goto L_0x0409;
                case 2: goto L_0x0405;
                case 3: goto L_0x0401;
                default: goto L_0x0400;
            }
        L_0x0400:
            goto L_0x0411
        L_0x0401:
            r17.fmNext()
            goto L_0x0411
        L_0x0405:
            r17.fmPrevious()
            goto L_0x0411
        L_0x0409:
            r17.fmClose()
            goto L_0x0411
        L_0x040d:
            r17.fmOpen()
        L_0x0411:
            goto L_0x0755
        L_0x0413:
            java.lang.String r0 = r2.action
            int r16 = r0.hashCode()
            switch(r16) {
                case -1362146736: goto L_0x04a6;
                case -825432804: goto L_0x049b;
                case -825393037: goto L_0x0491;
                case -825361316: goto L_0x0487;
                case 19491040: goto L_0x047d;
                case 171428079: goto L_0x0474;
                case 179334558: goto L_0x046a;
                case 179706250: goto L_0x0460;
                case 183111917: goto L_0x0455;
                case 486135880: goto L_0x044a;
                case 685022669: goto L_0x043f;
                case 974886623: goto L_0x0434;
                case 1017115442: goto L_0x0429;
                case 1264734904: goto L_0x041e;
                default: goto L_0x041c;
            }
        L_0x041c:
            goto L_0x04b1
        L_0x041e:
            java.lang.String r4 = "bluetooth.close"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r15
            goto L_0x04b2
        L_0x0429:
            java.lang.String r4 = "bluetooth.status"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r9
            goto L_0x04b2
        L_0x0434:
            java.lang.String r4 = "bluetooth.reject"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r8
            goto L_0x04b2
        L_0x043f:
            java.lang.String r4 = "bluetooth.hangup"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r13
            goto L_0x04b2
        L_0x044a:
            java.lang.String r4 = "bluetooth.accept"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r10
            goto L_0x04b2
        L_0x0455:
            java.lang.String r4 = "music.pause"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = 10
            goto L_0x04b2
        L_0x0460:
            java.lang.String r4 = "bluetooth.open"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r14
            goto L_0x04b2
        L_0x046a:
            java.lang.String r4 = "bluetooth.call"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r12
            goto L_0x04b2
        L_0x0474:
            java.lang.String r5 = "music.close"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x04b1
            goto L_0x04b2
        L_0x047d:
            java.lang.String r4 = "bluetooth.contact"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r7
            goto L_0x04b2
        L_0x0487:
            java.lang.String r4 = "music.prev"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r6
            goto L_0x04b2
        L_0x0491:
            java.lang.String r4 = "music.open"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = r5
            goto L_0x04b2
        L_0x049b:
            java.lang.String r4 = "music.next"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = 12
            goto L_0x04b2
        L_0x04a6:
            java.lang.String r4 = "music.continue"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x04b1
            r4 = 11
            goto L_0x04b2
        L_0x04b1:
            r4 = r11
        L_0x04b2:
            switch(r4) {
                case 0: goto L_0x05c4;
                case 1: goto L_0x0567;
                case 2: goto L_0x0553;
                case 3: goto L_0x054f;
                case 4: goto L_0x054a;
                case 5: goto L_0x0545;
                case 6: goto L_0x04de;
                case 7: goto L_0x04d5;
                case 8: goto L_0x04d0;
                case 9: goto L_0x04cb;
                case 10: goto L_0x04c6;
                case 11: goto L_0x04c1;
                case 12: goto L_0x04bc;
                case 13: goto L_0x04b7;
                default: goto L_0x04b5;
            }
        L_0x04b5:
            goto L_0x05c8
        L_0x04b7:
            r17.mediaPrevious()
            goto L_0x05c8
        L_0x04bc:
            r17.mediaNext()
            goto L_0x05c8
        L_0x04c1:
            r17.mediaPlay()
            goto L_0x05c8
        L_0x04c6:
            r17.mediaPause()
            goto L_0x05c8
        L_0x04cb:
            r17.btMusicStop()
            goto L_0x05c8
        L_0x04d0:
            r17.btMusicOpen()
            goto L_0x05c8
        L_0x04d5:
            r0 = 200(0xc8, float:2.8E-43)
            java.lang.String r4 = ""
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r13, r0, r4)
            goto L_0x05c8
        L_0x04de:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r4 = r0
            r5 = r14
            java.lang.String r0 = "isConnected"
            boolean r0 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusBoolean(r0)     // Catch:{ RemoteException -> 0x053f }
            r6 = 2040(0x7f8, float:2.859E-42)
            if (r0 == 0) goto L_0x052c
            java.lang.String r7 = "callStatus"
            int r7 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r7)     // Catch:{ RemoteException -> 0x053f }
            r5 = r7
            switch(r5) {
                case 4: goto L_0x0514;
                case 5: goto L_0x050c;
                case 6: goto L_0x0504;
                case 7: goto L_0x04fc;
                default: goto L_0x04f9;
            }     // Catch:{ RemoteException -> 0x053f }
        L_0x04f9:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            goto L_0x051c
        L_0x04fc:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r8 = "bluetooth.hangup"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
            goto L_0x0521
        L_0x0504:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r8 = "bluetooth.offhook"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
            goto L_0x0521
        L_0x050c:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r8 = "bluetooth.incoming"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
            goto L_0x0521
        L_0x0514:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r8 = "bluetooth.call"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
            goto L_0x0521
        L_0x051c:
            java.lang.String r8 = "bluetooth.idle"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
        L_0x0521:
            if (r5 != 0) goto L_0x0533
            com.wits.pms.bean.TxzMessage r8 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r9 = "bluetooth.connect"
            r8.<init>(r6, r9, r4)     // Catch:{ RemoteException -> 0x053f }
            r7 = r8
            goto L_0x0533
        L_0x052c:
            com.wits.pms.bean.TxzMessage r7 = new com.wits.pms.bean.TxzMessage     // Catch:{ RemoteException -> 0x053f }
            java.lang.String r8 = "bluetooth.disconnect"
            r7.<init>(r6, r8, r4)     // Catch:{ RemoteException -> 0x053f }
        L_0x0533:
            r6 = r7
            android.content.Context r7 = r1.mContext     // Catch:{ RemoteException -> 0x053f }
            com.wits.pms.receiver.CenterCallBackImpl r7 = com.wits.pms.receiver.CenterCallBackImpl.getImpl(r7)     // Catch:{ RemoteException -> 0x053f }
            r7.sendBroadCast(r6)     // Catch:{ RemoteException -> 0x053f }
            goto L_0x05c8
        L_0x053f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x05c8
        L_0x0545:
            r17.handUpPhone()
            goto L_0x05c8
        L_0x054a:
            r17.acceptPhone()
            goto L_0x05c8
        L_0x054f:
            r17.handUpPhone()
            goto L_0x05c8
        L_0x0553:
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "name"
            java.lang.String r0 = r0.getString(r4)
            android.os.Bundle r4 = r2.bundle
            java.lang.String r5 = "number"
            java.lang.String r4 = r4.getString(r5)
            r1.btPhoneCall(r0, r4)
            goto L_0x05c8
        L_0x0567:
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "topApp = "
            r4.append(r5)
            com.wits.pms.statuscontrol.SystemStatus r5 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r5 = r5.topApp
            r4.append(r5)
            java.lang.String r5 = "   isPlayingMusic = "
            r4.append(r5)
            com.wits.pms.interfaces.LogicSystem r5 = r1.mLogicSystem
            com.wits.pms.statuscontrol.BtPhoneStatus r5 = r5.getBtPhoneStatus()
            boolean r5 = r5.isPlayingMusic
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r4 = "com.wits.ksw.bt"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x05ad
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$nPreiVZHf-0USHRkVw4TYz6IFrQ r4 = new com.wits.pms.core.-$$Lambda$nPreiVZHf-0USHRkVw4TYz6IFrQ
            r4.<init>()
            r5 = 800(0x320, double:3.953E-321)
            r0.postDelayed(r4, r5)
        L_0x05ad:
            com.wits.pms.interfaces.LogicSystem r0 = r1.mLogicSystem
            com.wits.pms.statuscontrol.BtPhoneStatus r0 = r0.getBtPhoneStatus()
            boolean r0 = r0.isPlayingMusic
            if (r0 == 0) goto L_0x05c8
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$MnsNT1fc1_cXAfpJhxBta4fOU_k r4 = new com.wits.pms.core.-$$Lambda$MnsNT1fc1_cXAfpJhxBta4fOU_k
            r4.<init>()
            r5 = 0
            r0.postDelayed(r4, r5)
            goto L_0x05c8
        L_0x05c4:
            r1.openBluetooth(r15)
        L_0x05c8:
            goto L_0x0755
        L_0x05ca:
            java.lang.String r0 = r2.action
            int r4 = r0.hashCode()
            switch(r4) {
                case -2128329265: goto L_0x0619;
                case -2128329233: goto L_0x0610;
                case -1553713362: goto L_0x0606;
                case -1553708278: goto L_0x05fc;
                case -1553704816: goto L_0x05f2;
                case -1553704578: goto L_0x05e8;
                case -920463626: goto L_0x05de;
                case -920189843: goto L_0x05d4;
                default: goto L_0x05d3;
            }
        L_0x05d3:
            goto L_0x0623
        L_0x05d4:
            java.lang.String r4 = "volume.mute"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r7
            goto L_0x0624
        L_0x05de:
            java.lang.String r4 = "volume.down"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r15
            goto L_0x0624
        L_0x05e8:
            java.lang.String r4 = "volume.min"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r13
            goto L_0x0624
        L_0x05f2:
            java.lang.String r4 = "volume.max"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r12
            goto L_0x0624
        L_0x05fc:
            java.lang.String r4 = "volume.inc"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r8
            goto L_0x0624
        L_0x0606:
            java.lang.String r4 = "volume.dec"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r9
            goto L_0x0624
        L_0x0610:
            java.lang.String r4 = "volume.up"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            goto L_0x0624
        L_0x0619:
            java.lang.String r4 = "volume.to"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0623
            r14 = r10
            goto L_0x0624
        L_0x0623:
            r14 = r11
        L_0x0624:
            r4 = 300(0x12c, double:1.48E-321)
            switch(r14) {
                case 0: goto L_0x0651;
                case 1: goto L_0x0642;
                case 2: goto L_0x063e;
                case 3: goto L_0x063a;
                case 4: goto L_0x0639;
                case 5: goto L_0x0638;
                case 6: goto L_0x0637;
                case 7: goto L_0x062a;
                default: goto L_0x0629;
            }
        L_0x0629:
            goto L_0x0660
        L_0x062a:
            android.os.Handler r0 = r1.mHandler
            com.wits.pms.core.-$$Lambda$CenterControlImpl$VcInPsCoMHCPUEGHro8Z0FBgpNg r4 = new com.wits.pms.core.-$$Lambda$CenterControlImpl$VcInPsCoMHCPUEGHro8Z0FBgpNg
            r4.<init>(r2)
            r5 = 100
            r0.postDelayed(r4, r5)
            goto L_0x0660
        L_0x0637:
            goto L_0x0660
        L_0x0638:
            goto L_0x0660
        L_0x0639:
            goto L_0x0660
        L_0x063a:
            r17.volumeMin()
            goto L_0x0660
        L_0x063e:
            r17.volumeMax()
            goto L_0x0660
        L_0x0642:
            android.os.Handler r0 = r1.mHandler
            java.lang.Runnable r6 = r1.volumeDownBySystemRun
            r0.removeCallbacks(r6)
            android.os.Handler r0 = r1.mHandler
            java.lang.Runnable r6 = r1.volumeDownBySystemRun
            r0.postDelayed(r6, r4)
            goto L_0x0660
        L_0x0651:
            android.os.Handler r0 = r1.mHandler
            java.lang.Runnable r6 = r1.volumeUpBySystemRun
            r0.removeCallbacks(r6)
            android.os.Handler r0 = r1.mHandler
            java.lang.Runnable r6 = r1.volumeUpBySystemRun
            r0.postDelayed(r6, r4)
        L_0x0660:
            goto L_0x0755
        L_0x0662:
            android.content.Context r0 = r1.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r4 = "screen_brightness"
            r5 = 30
            int r0 = android.provider.Settings.System.getInt(r0, r4, r5)
            java.lang.String r4 = "light.up"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x0685
            int r0 = r0 + 42
            android.content.Context r4 = r1.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r5, r0)
        L_0x0685:
            java.lang.String r4 = "light.down"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x069a
            int r0 = r0 + -42
            android.content.Context r4 = r1.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r5, r0)
        L_0x069a:
            java.lang.String r4 = "light.min"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x06af
            r0 = 10
            android.content.Context r4 = r1.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r5, r0)
        L_0x06af:
            java.lang.String r4 = "light.max"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x06c4
            r0 = 255(0xff, float:3.57E-43)
            android.content.Context r4 = r1.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r5, r0)
        L_0x06c4:
            java.lang.String r4 = "CenterControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Brightness set to "
            r5.append(r6)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.i(r4, r5)
            goto L_0x0755
        L_0x06dc:
            java.lang.String r0 = "wifi.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x06e9
            r1.wifiOperation(r15)
            goto L_0x0755
        L_0x06e9:
            java.lang.String r0 = "wifi.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x06f5
            r1.wifiOperation(r14)
            goto L_0x0755
        L_0x06f5:
            java.lang.String r0 = "go.home"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0701
            com.wits.pms.utils.KeyUtils.pressKey(r13)
            goto L_0x0755
        L_0x0701:
            java.lang.String r0 = "screen.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x070d
            r1.displayScreen(r14)
            goto L_0x0755
        L_0x070d:
            java.lang.String r0 = "screen.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0755
            r1.displayScreen(r15)
            goto L_0x0755
        L_0x0719:
            java.lang.String r0 = "app.open"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0742
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "package"
            java.lang.String r0 = r0.getString(r4)
            java.lang.String r4 = "com.txznet.music"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x073b
            boolean r4 = r1.isAppInstalled(r0)
            if (r4 != 0) goto L_0x073b
            r17.openMusic()
            return
        L_0x073b:
            r1.openSourceMode(r6)
            r1.openApp(r0)
            goto L_0x0755
        L_0x0742:
            java.lang.String r0 = "app.close"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0755
            android.os.Bundle r0 = r2.bundle
            java.lang.String r4 = "package"
            java.lang.String r0 = r0.getString(r4)
            r1.closeApp(r0)
        L_0x0755:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.core.CenterControlImpl.handle(com.wits.pms.bean.TxzMessage):void");
    }

    public boolean isUsingCarPlay() {
        return SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") || SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1");
    }

    public boolean isUsingUsbCarPlay() {
        return SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x0486, code lost:
        if (r0.equals("CMD_MIC_START") != false) goto L_0x0494;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r0.equals("ENTER") != false) goto L_0x00bc;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(com.wits.pms.bean.ZlinkMessage r13) {
        /*
            r12 = this;
            java.lang.String r0 = r13.status
            r1 = 3
            r2 = 4
            r3 = 6
            r4 = 5
            r5 = -1
            r6 = 2
            r7 = 1
            r8 = 0
            if (r0 == 0) goto L_0x0428
            java.lang.String r0 = r13.status
            int r9 = r0.hashCode()
            r10 = 11
            r11 = 9
            switch(r9) {
                case -2087582999: goto L_0x00b1;
                case -1843701849: goto L_0x00a7;
                case -497207953: goto L_0x009d;
                case -150661894: goto L_0x0093;
                case 2142494: goto L_0x0089;
                case 66129592: goto L_0x0080;
                case 143012129: goto L_0x0075;
                case 143018830: goto L_0x006b;
                case 161176669: goto L_0x0061;
                case 701509265: goto L_0x0055;
                case 1015497884: goto L_0x004a;
                case 1709675476: goto L_0x003f;
                case 1766422463: goto L_0x0033;
                case 2008068401: goto L_0x0027;
                case 2120564979: goto L_0x001b;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x00bb
        L_0x001b:
            java.lang.String r1 = "MAIN_AUDIO_START"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 13
            goto L_0x00bc
        L_0x0027:
            java.lang.String r1 = "MAIN_AUDIO_STOP"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 14
            goto L_0x00bc
        L_0x0033:
            java.lang.String r1 = "PHONE_CALL_OFF"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 10
            goto L_0x00bc
        L_0x003f:
            java.lang.String r1 = "MAIN_PAGE_HIDDEN"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r3
            goto L_0x00bc
        L_0x004a:
            java.lang.String r1 = "DISCONNECT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r6
            goto L_0x00bc
        L_0x0055:
            java.lang.String r1 = "PHONE_RING_OFF"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 8
            goto L_0x00bc
        L_0x0061:
            java.lang.String r1 = "PHONE_RING_ON"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 7
            goto L_0x00bc
        L_0x006b:
            java.lang.String r1 = "SWITCHOTG"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r10
            goto L_0x00bc
        L_0x0075:
            java.lang.String r1 = "SWITCHHUB"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = 12
            goto L_0x00bc
        L_0x0080:
            java.lang.String r2 = "ENTER"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x00bb
            goto L_0x00bc
        L_0x0089:
            java.lang.String r1 = "EXIT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r2
            goto L_0x00bc
        L_0x0093:
            java.lang.String r1 = "ACTION_ZJ_PHONEFOUND"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r8
            goto L_0x00bc
        L_0x009d:
            java.lang.String r1 = "PHONE_CALL_ON"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r11
            goto L_0x00bc
        L_0x00a7:
            java.lang.String r1 = "MAIN_PAGE_SHOW"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r4
            goto L_0x00bc
        L_0x00b1:
            java.lang.String r1 = "CONNECTED"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bb
            r1 = r7
            goto L_0x00bc
        L_0x00bb:
            r1 = r5
        L_0x00bc:
            r0 = 99
            switch(r1) {
                case 0: goto L_0x0411;
                case 1: goto L_0x034d;
                case 2: goto L_0x0273;
                case 3: goto L_0x026a;
                case 4: goto L_0x0261;
                case 5: goto L_0x024a;
                case 6: goto L_0x0234;
                case 7: goto L_0x01e3;
                case 8: goto L_0x01df;
                case 9: goto L_0x015f;
                case 10: goto L_0x00f0;
                case 11: goto L_0x00e7;
                case 12: goto L_0x00de;
                case 13: goto L_0x00c3;
                default: goto L_0x00c1;
            }
        L_0x00c1:
            goto L_0x0426
        L_0x00c3:
            boolean r0 = r12.iszlinkCall
            if (r0 == 0) goto L_0x0426
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0426
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "2"
            com.wits.pms.mirror.SystemProperties.set(r0, r1)
            goto L_0x0426
        L_0x00de:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  SWITCHHUB"
            android.util.Log.d(r0, r1)
            goto L_0x0426
        L_0x00e7:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  SWITCHOTG"
            android.util.Log.d(r0, r1)
            goto L_0x0426
        L_0x00f0:
            java.lang.String r1 = "CenterControl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0143 }
            r2.<init>()     // Catch:{ RemoteException -> 0x0143 }
            java.lang.String r7 = "handle  PHONE_CALL_OFF   originMode = "
            r2.append(r7)     // Catch:{ RemoteException -> 0x0143 }
            int r7 = r12.originMode     // Catch:{ RemoteException -> 0x0143 }
            r2.append(r7)     // Catch:{ RemoteException -> 0x0143 }
            java.lang.String r2 = r2.toString()     // Catch:{ RemoteException -> 0x0143 }
            android.util.Log.d(r1, r2)     // Catch:{ RemoteException -> 0x0143 }
            com.wits.pms.mcu.custom.KswMcuSender r1 = com.wits.pms.mcu.custom.KswMcuSender.getSender()     // Catch:{ RemoteException -> 0x0143 }
            byte[] r2 = new byte[r6]     // Catch:{ RemoteException -> 0x0143 }
            r2 = {0, 0} // fill-array     // Catch:{ RemoteException -> 0x0143 }
            r1.sendMessage(r0, r2)     // Catch:{ RemoteException -> 0x0143 }
            int r0 = r12.originMode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r5) goto L_0x0142
            int r0 = r12.originMode     // Catch:{ RemoteException -> 0x0143 }
            java.lang.String r1 = "systemMode"
            int r1 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r1)     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r1) goto L_0x0142
            int r0 = r12.mode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r3) goto L_0x0142
            int r0 = r12.mode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r11) goto L_0x0142
            int r0 = r12.mode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r4) goto L_0x0142
            int r0 = r12.mode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r10) goto L_0x0142
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  PHONE_CALL_OFF   systemModeSwitch "
            android.util.Log.d(r0, r1)     // Catch:{ RemoteException -> 0x0143 }
            int r0 = r12.originMode     // Catch:{ RemoteException -> 0x0143 }
            if (r0 == r6) goto L_0x0142
            int r0 = r12.originMode     // Catch:{ RemoteException -> 0x0143 }
            r12.systemModeSwitch(r0)     // Catch:{ RemoteException -> 0x0143 }
        L_0x0142:
            goto L_0x0147
        L_0x0143:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0147:
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "0"
            com.wits.pms.mirror.SystemProperties.set(r0, r1)
            android.content.Context r0 = r12.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "isZlinkCalling"
            android.provider.Settings.System.putInt(r0, r1, r8)
            r12.zlinkCalling = r8
            r12.cpCallStatus = r8
            goto L_0x0426
        L_0x015f:
            com.wits.pms.core.SystemStatusControl r1 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.statuscontrol.SystemStatus r1 = r1.getSystemStatus()
            int r1 = r1.lastMode
            r12.mode = r1
            java.lang.String r1 = "systemMode"
            int r1 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r1)     // Catch:{ RemoteException -> 0x0196 }
            r12.originMode = r1     // Catch:{ RemoteException -> 0x0196 }
            java.lang.String r1 = "CenterControl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0196 }
            r2.<init>()     // Catch:{ RemoteException -> 0x0196 }
            java.lang.String r3 = "handle  PHONE_CALL_ON  mode = "
            r2.append(r3)     // Catch:{ RemoteException -> 0x0196 }
            int r3 = r12.originMode     // Catch:{ RemoteException -> 0x0196 }
            r2.append(r3)     // Catch:{ RemoteException -> 0x0196 }
            java.lang.String r3 = "   lastmode = "
            r2.append(r3)     // Catch:{ RemoteException -> 0x0196 }
            int r3 = r12.mode     // Catch:{ RemoteException -> 0x0196 }
            r2.append(r3)     // Catch:{ RemoteException -> 0x0196 }
            java.lang.String r2 = r2.toString()     // Catch:{ RemoteException -> 0x0196 }
            android.util.Log.d(r1, r2)     // Catch:{ RemoteException -> 0x0196 }
            goto L_0x019a
        L_0x0196:
            r1 = move-exception
            r1.printStackTrace()
        L_0x019a:
            java.lang.String r1 = "vendor.wits.zlink.call"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            android.content.Context r1 = r12.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "isZlinkCalling"
            android.provider.Settings.System.putInt(r1, r2, r7)
            int r1 = r12.cpCallStatus
            if (r1 != 0) goto L_0x01b2
            r12.cpCallStatus = r6
        L_0x01b2:
            int r1 = r12.cpCallStatus
            if (r1 != r6) goto L_0x01c3
            com.wits.pms.mcu.custom.KswMcuSender r1 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            byte[] r2 = new byte[r6]
            r2 = {0, 3} // fill-array
            r1.sendMessage(r0, r2)
            goto L_0x01cf
        L_0x01c3:
            com.wits.pms.mcu.custom.KswMcuSender r1 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            byte[] r2 = new byte[r6]
            r2 = {0, 2} // fill-array
            r1.sendMessage(r0, r2)
        L_0x01cf:
            com.wits.pms.mcu.custom.KswMcuSender r1 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            byte[] r2 = new byte[r6]
            r2 = {0, 1} // fill-array
            r1.sendMessage(r0, r2)
            r12.zlinkCalling = r7
            goto L_0x0426
        L_0x01df:
            r12.cpCallStatus = r6
            goto L_0x0426
        L_0x01e3:
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.statuscontrol.SystemStatus r0 = r0.getSystemStatus()
            int r0 = r0.lastMode
            r12.mode = r0
            java.lang.String r0 = "systemMode"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r0)     // Catch:{ RemoteException -> 0x021a }
            r12.originMode = r0     // Catch:{ RemoteException -> 0x021a }
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x021a }
            r1.<init>()     // Catch:{ RemoteException -> 0x021a }
            java.lang.String r2 = "handle  PHONE_RING_ON  mode = "
            r1.append(r2)     // Catch:{ RemoteException -> 0x021a }
            int r2 = r12.originMode     // Catch:{ RemoteException -> 0x021a }
            r1.append(r2)     // Catch:{ RemoteException -> 0x021a }
            java.lang.String r2 = "   lastmode = "
            r1.append(r2)     // Catch:{ RemoteException -> 0x021a }
            int r2 = r12.mode     // Catch:{ RemoteException -> 0x021a }
            r1.append(r2)     // Catch:{ RemoteException -> 0x021a }
            java.lang.String r1 = r1.toString()     // Catch:{ RemoteException -> 0x021a }
            android.util.Log.d(r0, r1)     // Catch:{ RemoteException -> 0x021a }
            goto L_0x021e
        L_0x021a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x021e:
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "1"
            com.wits.pms.mirror.SystemProperties.set(r0, r1)
            android.content.Context r0 = r12.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "isZlinkCalling"
            android.provider.Settings.System.putInt(r0, r1, r7)
            r12.cpCallStatus = r7
            goto L_0x0426
        L_0x0234:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  MAIN_PAGE_HIDDEN"
            android.util.Log.d(r0, r1)
            boolean r0 = r12.flgReleaseMicInConnected
            if (r0 != 0) goto L_0x0246
            boolean r0 = r12.flgZlinkConnected
            if (r0 != 0) goto L_0x0246
            r12.setTxzSleep(r8)
        L_0x0246:
            r12.zlinkShowing = r8
            goto L_0x0426
        L_0x024a:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  MAIN_PAGE_SHOW"
            android.util.Log.d(r0, r1)
            r12.zlinkShowing = r7
            android.os.Handler r0 = r12.mHandler
            com.wits.pms.core.-$$Lambda$CenterControlImpl$MU0h5QLUe0voNYR_9jssAIq6o_U r1 = new com.wits.pms.core.-$$Lambda$CenterControlImpl$MU0h5QLUe0voNYR_9jssAIq6o_U
            r1.<init>()
            r2 = 1500(0x5dc, double:7.41E-321)
            r0.postDelayed(r1, r2)
            goto L_0x0426
        L_0x0261:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  EXIT"
            android.util.Log.d(r0, r1)
            goto L_0x0426
        L_0x026a:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  ENTER"
            android.util.Log.d(r0, r1)
            goto L_0x0426
        L_0x0273:
            android.os.Bundle r0 = r13.bundle
            java.lang.String r1 = "phoneMode"
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r1 = "CenterControl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "handle DISCONNECT "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = ", Build.VERSION is "
            r2.append(r3)
            java.lang.String r3 = android.os.Build.VERSION.RELEASE
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            if (r0 == 0) goto L_0x02c6
            java.lang.String r1 = "carplay_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x02c6
            java.lang.String r1 = "vendor.wits.zlink.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.openBluetooth(r8)
            java.lang.String r1 = android.os.Build.VERSION.RELEASE
            java.lang.String r2 = "11"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x02c6
            java.lang.String r1 = android.os.Build.DISPLAY
            java.lang.String r2 = "M600"
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L_0x02c6
            r12.closeWifiHotspot()
        L_0x02c6:
            if (r0 == 0) goto L_0x02e9
            java.lang.String r1 = "airplay_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x02d8
            java.lang.String r1 = "airplay_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x02e9
        L_0x02d8:
            java.lang.String r1 = "vendor.wits.airplay.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            java.lang.String r1 = "vendor.wits.airplay.wired.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.openBluetooth(r8)
        L_0x02e9:
            if (r0 == 0) goto L_0x02fd
            java.lang.String r1 = "carplay_wired"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x02fd
            java.lang.String r1 = "vendor.wits.carplayWried.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.openBluetooth(r8)
        L_0x02fd:
            if (r0 == 0) goto L_0x0316
            java.lang.String r1 = "auto_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x030f
            java.lang.String r1 = "auto_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0316
        L_0x030f:
            java.lang.String r1 = "vendor.wits.androidAuto.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x0316:
            if (r0 == 0) goto L_0x032f
            java.lang.String r1 = "android_mirror_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x0328
            java.lang.String r1 = "android_mirror_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x032f
        L_0x0328:
            java.lang.String r1 = "vendor.wits.androidMirror.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x032f:
            java.lang.String r1 = "Support_TXZ"
            int r1 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r1)     // Catch:{ Exception -> 0x0345 }
            if (r1 == 0) goto L_0x0344
            java.lang.String r2 = "systemMode"
            int r2 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r2)     // Catch:{ Exception -> 0x0345 }
            if (r2 != r7) goto L_0x0344
            r12.setTxzSleep(r8)     // Catch:{ Exception -> 0x0345 }
            r12.flgZlinkConnected = r8     // Catch:{ Exception -> 0x0345 }
        L_0x0344:
            goto L_0x0349
        L_0x0345:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0349:
            r12.zlinkCalling = r8
            goto L_0x0426
        L_0x034d:
            android.os.Bundle r0 = r13.bundle
            java.lang.String r1 = "phoneMode"
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r1 = "CenterControl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "handle  CONNECTED   "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            if (r0 == 0) goto L_0x037f
            java.lang.String r1 = "carplay_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x037f
            java.lang.String r1 = "vendor.wits.zlink.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.closeBluetooth()
        L_0x037f:
            if (r0 == 0) goto L_0x03b2
            java.lang.String r1 = "airplay_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x0391
            java.lang.String r1 = "airplay_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x03b2
        L_0x0391:
            java.lang.String r1 = "airplay_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x03a4
            java.lang.String r1 = "vendor.wits.airplay.wired.connected"
            java.lang.String r2 = "0"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.closeBluetooth()
            goto L_0x03ab
        L_0x03a4:
            java.lang.String r1 = "vendor.wits.airplay.wired.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x03ab:
            java.lang.String r1 = "vendor.wits.airplay.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x03b2:
            if (r0 == 0) goto L_0x03c6
            java.lang.String r1 = "carplay_wired"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x03c6
            java.lang.String r1 = "vendor.wits.carplayWried.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
            r12.closeBluetooth()
        L_0x03c6:
            if (r0 == 0) goto L_0x03df
            java.lang.String r1 = "auto_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x03d8
            java.lang.String r1 = "auto_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x03df
        L_0x03d8:
            java.lang.String r1 = "vendor.wits.androidAuto.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x03df:
            if (r0 == 0) goto L_0x03f8
            java.lang.String r1 = "android_mirror_wired"
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x03f1
            java.lang.String r1 = "android_mirror_wireless"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x03f8
        L_0x03f1:
            java.lang.String r1 = "vendor.wits.androidMirror.connected"
            java.lang.String r2 = "1"
            com.wits.pms.mirror.SystemProperties.set(r1, r2)
        L_0x03f8:
            java.lang.String r1 = "Support_TXZ"
            int r1 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r1)     // Catch:{ Exception -> 0x040a }
            if (r1 == 0) goto L_0x0409
            boolean r2 = r12.flgReleaseMicInConnected     // Catch:{ Exception -> 0x040a }
            if (r2 == 0) goto L_0x0407
            r12.setTxzSleep(r7)     // Catch:{ Exception -> 0x040a }
        L_0x0407:
            r12.flgZlinkConnected = r7     // Catch:{ Exception -> 0x040a }
        L_0x0409:
            goto L_0x040e
        L_0x040a:
            r1 = move-exception
            r1.printStackTrace()
        L_0x040e:
            r12.zlinkCalling = r8
            goto L_0x0426
        L_0x0411:
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ Exception -> 0x0421 }
            if (r0 == 0) goto L_0x0420
            boolean r1 = r12.flgReleaseMicInConnected     // Catch:{ Exception -> 0x0421 }
            if (r1 != 0) goto L_0x0420
            r12.setTxzSleep(r7)     // Catch:{ Exception -> 0x0421 }
        L_0x0420:
            goto L_0x0426
        L_0x0421:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0426:
            goto L_0x04bc
        L_0x0428:
            java.lang.String r0 = r13.command
            if (r0 == 0) goto L_0x04bc
            java.lang.String r0 = "CenterControl"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "#11581 -- message.command != null --> message.command is "
            r9.append(r10)
            java.lang.String r10 = r13.command
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r0, r9)
            java.lang.String r0 = r13.command
            int r9 = r0.hashCode()
            switch(r9) {
                case -2126047073: goto L_0x0489;
                case -1482963131: goto L_0x0480;
                case -1478680495: goto L_0x0476;
                case -150661894: goto L_0x046c;
                case 871291637: goto L_0x0462;
                case 1405544733: goto L_0x0458;
                case 1920379072: goto L_0x044e;
                default: goto L_0x044d;
            }
        L_0x044d:
            goto L_0x0493
        L_0x044e:
            java.lang.String r1 = "ACTION_ZJ_IPODFOUND"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r6
            goto L_0x0494
        L_0x0458:
            java.lang.String r1 = "SIRI_OFF"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r3
            goto L_0x0494
        L_0x0462:
            java.lang.String r1 = "REQ_OS_AUDIO_FOCUS"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r8
            goto L_0x0494
        L_0x046c:
            java.lang.String r1 = "ACTION_ZJ_PHONEFOUND"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r7
            goto L_0x0494
        L_0x0476:
            java.lang.String r1 = "SIRI_ON"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r4
            goto L_0x0494
        L_0x0480:
            java.lang.String r2 = "CMD_MIC_START"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0493
            goto L_0x0494
        L_0x0489:
            java.lang.String r1 = "CMD_MIC_STOP"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0493
            r1 = r2
            goto L_0x0494
        L_0x0493:
            r1 = r5
        L_0x0494:
            switch(r1) {
                case 0: goto L_0x04bb;
                case 1: goto L_0x04ba;
                case 2: goto L_0x04b9;
                case 3: goto L_0x049f;
                case 4: goto L_0x049e;
                case 5: goto L_0x049b;
                case 6: goto L_0x0498;
                default: goto L_0x0497;
            }
        L_0x0497:
            goto L_0x04bc
        L_0x0498:
            r12.iszlinkCall = r8
            goto L_0x04bc
        L_0x049b:
            r12.iszlinkCall = r7
            goto L_0x04bc
        L_0x049e:
            goto L_0x04bc
        L_0x049f:
            boolean r0 = r12.iszlinkCall
            if (r0 == 0) goto L_0x04bc
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x04bc
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "2"
            com.wits.pms.mirror.SystemProperties.set(r0, r1)
            goto L_0x04bc
        L_0x04b9:
            goto L_0x04bc
        L_0x04ba:
            goto L_0x04bc
        L_0x04bb:
        L_0x04bc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.core.CenterControlImpl.handle(com.wits.pms.bean.ZlinkMessage):void");
    }

    public static /* synthetic */ void lambda$handle$1(CenterControlImpl centerControlImpl) {
        if (!centerControlImpl.zlinkCalling && centerControlImpl.zlinkShowing) {
            Log.d(TAG, "handle  MAIN_PAGE_SHOW   BT MODE");
            getImpl().openSourceMode(3);
        }
    }

    public void handle(EcarMessage msg) {
        if (msg.ecarSendKey != null) {
            String str = msg.ecarSendKey;
            char c = 65535;
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
                    Log.d(TAG, "handle  EcarCallStart");
                    new TxzMessage(2040, "bluetooth.call", (Bundle) null).sendBroadCast(this.mContext);
                    return;
                case 1:
                    Log.d(TAG, "handle  EcarCallEnd");
                    new TxzMessage(2040, "bluetooth.hangup", (Bundle) null).sendBroadCast(this.mContext);
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
                    Log.d(TAG, "handle  AUTO BOX DISCONNECT");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CONNECT, "0");
                    openBluetooth(false);
                    try {
                        if (PowerManagerApp.getSettingsInt("Support_TXZ") != 0 && PowerManagerApp.getStatusInt("systemMode") == 1) {
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
                    Log.d(TAG, "handle  PHONE_CALL_ON");
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
                    Log.d(TAG, "handle  PHONE_CALL_ON");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CALL, "0");
                    return;
                case 5:
                    Log.d(TAG, "handle  AUTO BOX CONNECT");
                    SystemProperties.set(AutoKitMessage.AUTOBOX_CONNECT, "1");
                    closeBluetooth();
                    try {
                        if (PowerManagerApp.getSettingsInt("Support_TXZ") != 0 && PowerManagerApp.getStatusInt("systemMode") == 1) {
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
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.mcu.carinfo", bundle).sendBroadCast(this.mContext);
    }

    public void openSpeech() {
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.window.open", (Bundle) null).sendBroadCast(this.mContext);
    }

    public void closeSpeech() {
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.window.close", (Bundle) null).sendBroadCast(this.mContext);
    }

    public void setTxzWindow(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("mode", on ? "top" : "none");
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.float.mode", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzSleep(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("status", on ? "before.sleep" : "wakeup");
        new TxzMessage(2010, "system.status", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzQuickQuit(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("status", on ? "reverse.enter" : "reverse.quit");
        new TxzMessage(2010, "system.status", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzSwitch(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("enable", on);
        new TxzMessage(PreciseDisconnectCause.IWLAN_DPD_FAILURE, "txz.enable", bundle).sendBroadCast(this.mContext);
    }

    public void openNavi() {
        try {
            closeSpeech();
            openApp(PowerManagerApp.getSettingsString("NaviApp"));
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

    public void openGallery() {
    }

    public void openAndroidBluetooth() {
        try {
            startAction("com.wits.bt.action.OPEN");
        } catch (Exception e) {
        }
    }

    public void sendCloseBTAppBroadcast() {
        this.mContext.sendBroadcast(new Intent("com.wits.bt.CLOSE_BT_APP"));
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

    public void btMusicOpen() {
    }

    public void wifiOperation(boolean open) {
        ((WifiManager) this.mContext.getSystemService("wifi")).setWifiEnabled(open);
    }

    public void closeApp(String pkgName) {
        Log.i(TAG, "close App in center control - " + pkgName);
        if ("com.txznet.music".equals(pkgName)) {
            this.mContext.sendBroadcast(new Intent("txz.tongting.exit"));
        } else {
            killApp(pkgName);
        }
    }

    public void openApp(String pkgName) {
        Intent lastIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (!TextUtils.isEmpty(pkgName) && !filter(pkgName)) {
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
    }

    public void openApp(String pkgName, String action) {
        Intent lastIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
        lastIntent.setAction(action);
        if (!filter(pkgName)) {
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
    }

    public void kuWoMusicPlay(boolean coldBoot) {
        Log.d(TAG, "kuWoMusicPlay  coldBoot = " + coldBoot);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                new TxzMessage(2060, "music.play", (Bundle) null).sendBroadCast(CenterControlImpl.this.mContext);
            }
        }, coldBoot ? 3000 : 500);
    }

    public void kuWoMusicPause() {
        if (KswSettings.getSettings().getSettingsString("currentMediaPkg").contains("kwmusiccar")) {
            KeyUtils.pressKey(127);
        }
    }

    public void txzMusicPlay(boolean coldBoot) {
        Log.d(TAG, "txzMusicPlay  coldBoot = " + coldBoot);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                new TxzMessage(2060, "music.play", (Bundle) null).sendBroadCast(CenterControlImpl.this.mContext);
            }
        }, coldBoot ? 500 : 100);
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

    public void stopMedia() {
        stopZlinkMusic();
        if (SystemProperties.get("persist.sys.hicar_connect").equals("true")) {
            stopHicarMusic(true);
        }
        CenterCallBackImpl.getImpl(this.mContext).sendBroadCast(new TxzMessage(2062, "mcu.open", (Bundle) null));
        this.mContext.sendBroadcast(new Intent("mcu.car_mode.open"));
        this.mContext.sendBroadcast(new Intent("com.android.minwin.clos"));
        if (Settings.System.getInt(this.mContext.getContentResolver(), "CarDisplay", 1) != 0) {
            Log.d(TAG, "stopMedia  CarDisplay != 0");
            backToHome();
        }
    }

    private void stopMusic() {
        Log.d(TAG, "stopMusic");
        KeyUtils.pressKey(86);
    }

    public void stopZlinkMusic() {
        Log.d(TAG, "stopZlinkMusic");
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 127);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
    }

    public void stopHicarMusic(boolean isStop) {
        if (this.mAudioManager == null) {
            Log.d(TAG, "AudioManager is mull, can't stop Hicar Music");
        } else if (isStop) {
            Log.d(TAG, "stop Hicar Music");
            this.mAudioManager.requestAudioFocus((AudioManager.OnAudioFocusChangeListener) null, 3, 1);
        } else {
            this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
        }
    }

    public void exitZlink() {
        Log.d(TAG, "exitZlink");
        Bundle bundle = new Bundle();
        bundle.putString("command", "ACTION_EXIT");
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "ACTION_EXIT", bundle).sendBroadCast(this.mContext);
    }

    public void enterZlink() {
        Log.d(TAG, "enterZlink");
        Bundle bundle = new Bundle();
        bundle.putString("command", "ACTION_ENTER");
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "ACTION_ENTER", bundle).sendBroadCast(this.mContext);
    }

    public void handupEcarPhone() {
        Log.d(TAG, "handupEcarPhone");
        this.mContext.sendBroadcast(new Intent("com.ecat.video.HangOnEvent"));
    }

    public void zlinkHandleCall() {
        String callStatus = SystemProperties.get(ZlinkMessage.ZLINK_CALL);
        Log.d(TAG, "zlinkHandleCall  callStatus = " + callStatus);
        if (callStatus.equals("1")) {
            zlinkCallOn();
        } else if (callStatus.equals("2")) {
            zlinkCallOff();
        }
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
        Log.d(TAG, "autoKitHandleCall  callStatus = " + callStatus);
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
        int callStatus;
        try {
            if (!PowerManagerApp.getStatusBoolean("isConnected") || ((callStatus = PowerManagerApp.getStatusInt("callStatus")) != 4 && callStatus != 6 && callStatus != 5)) {
                return false;
            }
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void home() {
        try {
            String naviApp = Settings.System.getString(this.mContext.getContentResolver(), "NaviApp");
            String currentPkg = Settings.System.getString(this.mContext.getContentResolver(), "currentPkg");
            boolean isTalking = false;
            boolean isNavi = (currentPkg == null || naviApp == null || !currentPkg.equals(naviApp)) ? false : true;
            if (Settings.System.getInt(this.mContext.getContentResolver(), "isCalling") == 1) {
                isTalking = true;
            }
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
            boolean isTalking = false;
            boolean isNavi = (currentPkg == null || naviApp == null || !currentPkg.equals(naviApp)) ? false : true;
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "ksw_home_ban") == 1) {
                isTalking = true;
            }
            if (!isNavi && !isTalking) {
                KeyUtils.pressKey(3);
            }
        } catch (Exception e) {
            KeyUtils.pressKey(3);
        }
    }

    public void mediaPrevious() {
        KeyUtils.pressKey(88);
    }

    public void mediaNext() {
        KeyUtils.pressKey(87);
    }

    public void mediaPause() {
        KeyUtils.pressKey(127);
    }

    public void mediaPlay() {
        KeyUtils.pressKey(126);
    }

    public void mediaPlayPause() {
        KeyUtils.pressKey(85);
    }

    public void muteSwitch() {
        KeyUtils.pressKey(164);
    }

    public void mute(boolean on) {
        if (on) {
            this.mAudioManager.adjustStreamVolume(3, -100, 8);
        } else {
            this.mAudioManager.adjustStreamVolume(3, 100, 8);
        }
    }

    /* access modifiers changed from: private */
    public void muteWithUI(boolean on) {
        Log.d(TAG, "muteWithUI: isPlayingMusic =" + this.mLogicSystem.getBtPhoneStatus().isPlayingMusic + "  on =" + on);
        if (on) {
            if (this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
                this.mAudioManager.adjustStreamVolume(2, -100, 1);
            } else {
                this.mAudioManager.adjustStreamVolume(3, -100, 1);
            }
        } else if (this.mLogicSystem.getBtPhoneStatus().isPlayingMusic) {
            this.mAudioManager.adjustStreamVolume(2, 100, 1);
        } else {
            this.mAudioManager.adjustStreamVolume(3, 100, 1);
        }
    }

    public void volumeUp() {
        this.mAudioManager.adjustStreamVolume(3, 1, 1);
    }

    public void volumeDown() {
        this.mAudioManager.adjustStreamVolume(3, -1, 1);
    }

    /* access modifiers changed from: private */
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

    /* access modifiers changed from: private */
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
        if (btPhoneStatus < 4 || btPhoneStatus > 6) {
            return false;
        }
        return true;
    }

    public void volumeMax() {
        this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 1);
    }

    public void volumeMin() {
        this.mAudioManager.setStreamVolume(3, 1, 1);
    }

    public void powerOff() {
        SystemStatusControl.getDefault().dormant(true);
    }

    public void sourceSwitch() {
        int nextMode = 1;
        for (int i = 0; i < this.sourceArray.length; i++) {
            if (this.sourceArray[i] == SystemStatusControl.getDefault().getSystemStatus().lastMode) {
                if (i == this.sourceArray.length - 1) {
                    nextMode = this.sourceArray[0];
                } else {
                    nextMode = this.sourceArray[i + 1];
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
        Log.i(TAG, "try to open source:" + source);
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
            default:
                return;
        }
    }

    public void fmOpen() {
    }

    public void fmClose() {
    }

    public void fmPrevious() {
    }

    public void fmNext() {
    }

    public void openSettings() {
        try {
            startAction("com.on.systemUi.start.voice");
        } catch (Exception e) {
        }
    }

    public void call() {
        btPhoneCall("", "");
    }

    public void unCall() {
        handUpPhone();
    }

    public void displayScreen(boolean on) {
        SystemStatusControl.getDefault().getSystemStatus().screenSwitch = on;
        closeScreen(!on);
    }

    public void openBluetooth(boolean open) {
        try {
            if (PowerManagerApp.getSettingsInt("BT_Type") == 1) {
                openCarBt();
            } else {
                this.mHandler.post(new Runnable(open) {
                    private final /* synthetic */ boolean f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        CenterControlImpl.lambda$openBluetooth$2(CenterControlImpl.this, this.f$1);
                    }
                });
            }
        } catch (RemoteException e) {
            openAndroidBluetooth();
        }
    }

    public static /* synthetic */ void lambda$openBluetooth$2(CenterControlImpl centerControlImpl, boolean open) {
        if (!open || !SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || !SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1") || !"true".equals(SystemProperties.get("persist.sys.hicar_connect")) || !SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") || !SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1")) {
            Log.i(TAG, "openBluetooth:  open: " + open + " ZLINK_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") + " hicar_connect: " + "true".equals(SystemProperties.get("persist.sys.hicar_connect")) + " ZLINK_AIRPLAY_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_AIRPLAY_CONNECT).equals("1") + " ZLINK_CARPLAY_WRIED_CONNECT: " + SystemProperties.get(ZlinkMessage.ZLINK_CARPLAY_WRIED_CONNECT).equals("1"));
            if (!"1".equals(SystemProperties.get(ZlinkMessage.ZLINK_CONNECT))) {
                Settings.System.putInt(centerControlImpl.mContext.getContentResolver(), "btSwitch", 1);
            }
        }
        if (!WitsCommand.sendCommandGetResult(3, 107, open ? "true" : "false")) {
            centerControlImpl.openAndroidBluetooth();
        }
    }

    public void closeBluetooth() {
        Settings.System.putInt(this.mContext.getContentResolver(), "btSwitch", 0);
        WitsCommand.sendCommand(3, 105, "");
    }

    public void btPhoneCall(String name, String number) {
        WitsCommand.sendCommand(3, 108, number + "");
    }

    public void handUpPhone() {
        try {
            if (PowerManagerApp.getStatusBoolean("isConnected")) {
                WitsCommand.sendCommand(3, 109, "");
            }
        } catch (RemoteException e) {
        }
    }

    public void acceptPhone() {
        try {
            if (PowerManagerApp.getStatusBoolean("isConnected")) {
                WitsCommand.sendCommand(3, 112, "");
            }
        } catch (RemoteException e) {
        }
    }

    public void btMusicPlayPause() {
        WitsCommand.sendCommand(3, 102, "");
    }

    public void btMusicPrev() {
        WitsCommand.sendCommand(3, 100, "");
    }

    public void btMusicNext() {
        WitsCommand.sendCommand(3, 101, "");
    }

    public void btMusicContinue() {
        WitsCommand.sendCommand(3, 103, "");
    }

    public void btMusicStop() {
        WitsCommand.sendCommand(3, 104, "");
    }

    public void btMusicRelease() {
        WitsCommand.sendCommand(3, 104, "");
    }

    public void switchSoundToCar() {
        WitsCommand.sendCommand(3, 111, "");
    }

    public void switchSoundToPhone() {
        WitsCommand.sendCommand(3, 110, "");
    }

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
                        if (this.installAppDialog != null) {
                            if (this.installAppDialog.isShowing()) {
                                String arg = witsCommand.getJsonArg();
                                if (!TextUtils.isEmpty(arg)) {
                                    this.progressBar.setProgress(Integer.parseInt(arg));
                                    break;
                                }
                            }
                        } else {
                            this.progressBar = new ProgressBar(this.mContext, (AttributeSet) null, 16842872);
                            this.progressBar.setPadding(20, 20, 20, 20);
                            this.installAppDialog = new AlertDialog.Builder(this.mContext, R.style.AlertDialogCustom).setCancelable(true).setTitle((int) R.string.install_app).setView((View) this.progressBar).create();
                            this.installAppDialog.getWindow().setType(2003);
                            this.installAppDialog.show();
                            break;
                        }
                        break;
                    case 127:
                        if (this.installAppDialog != null && this.installAppDialog.isShowing()) {
                            this.installAppDialog.dismiss();
                        }
                        Toast.makeText(this.mContext, (int) R.string.install_app_done, 0).show();
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
                                new PowerManagerMirror((PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE)).shutdown(false, "CenterService shutdown", true);
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
                                                exitSource(Integer.parseInt(jsonArg));
                                            }
                                            return true;
                                        } catch (Exception e2) {
                                            return false;
                                        }
                                    case 604:
                                        try {
                                            openSourceMode(Integer.parseInt(witsCommand.getJsonArg()));
                                            return true;
                                        } catch (Exception e3) {
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
                                                this.mAudioManager.requestAudioFocus((AudioManager.OnAudioFocusChangeListener) null, 3, -3);
                                            } else {
                                                this.mAudioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
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
                                        } catch (Exception e4) {
                                            Log.i(TAG, "error AIRCON_CONTROL", e4);
                                        }
                                        return true;
                                    case 613:
                                        KswMcuSender.getSender().sendMessage(104, new byte[]{10, 0});
                                        KswMcuSender.getSender().sendMessage(KswMessage.obtainKswMcuMsg(11));
                                        return true;
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
            new Thread() {
                public void run() {
                    touchControl.opPointerEvent(0, 0, 0);
                    touchControl.opPointerEvent(0, 0, 1);
                }
            }.start();
        } else if (witsCommand.getCommand() == 9) {
            Log.d(TAG, "handleCommand: OTA_TYPE  subCommand =" + witsCommand.getSubCommand());
            int subCommand2 = witsCommand.getSubCommand();
            if (subCommand2 == 100) {
                return true;
            }
            if (subCommand2 != 108) {
                switch (subCommand2) {
                    case 103:
                        boolean otaNetUpdated = false;
                        try {
                            otaNetUpdated = PowerManagerApp.getStatusBoolean("ota_net_updated");
                        } catch (RemoteException e5) {
                            e5.printStackTrace();
                        }
                        if (otaNetUpdated) {
                            Log.d(TAG, "handleCommand: ota updated,just need reboot");
                            return true;
                        }
                        if (Integer.parseInt(Build.VERSION.RELEASE) <= 10 || !Build.DISPLAY.contains("M600")) {
                            NetOTAUpdate.checkFile(this.mContext);
                        } else {
                            ABNetOTAUpdate.checkFile(this.mContext);
                        }
                        return true;
                    case 104:
                        Log.d(TAG, "handleCommand: OTA_UPGRADING progress =" + witsCommand.getJsonArg());
                        return true;
                }
            } else {
                Log.d(TAG, "handleCommand: OTA_REBOOT_DEVICE");
                PowerManager pm = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
                if (pm != null) {
                    pm.reboot("");
                    Log.d(TAG, "handleCommand: OTA_REBOOT_DEVICE reboot");
                }
                return true;
            }
        } else if (witsCommand.getCommand() == 20 && witsCommand.getSubCommand() == 100) {
            try {
                IActivityTaskManager activityTaskManager = IActivityTaskManager.Stub.asInterface(ServiceManager.getService(Context.ACTIVITY_TASK_SERVICE));
                String[] arg2 = witsCommand.getJsonArg().split(SmsManager.REGEX_PREFIX_DELIMITER);
                Log.d(TAG, "FREE_FORM_LAUNCH  arg[0]=" + arg2[0] + " arg[1]=" + Integer.parseInt(arg2[1]));
                String pkg = arg2[0];
                int tempTaskId = getFreeformTaskId(this.mContext, pkg);
                Bundle bundle = getWindowModeActivityOptions(Integer.parseInt(arg2[1])).toBundle();
                if (tempTaskId != 0) {
                    activityTaskManager.startActivityFromRecents(tempTaskId, bundle);
                } else {
                    PackageManager packageManager = this.mContext.getPackageManager();
                    if (packageManager.getLaunchIntentForPackage(pkg) != null) {
                        this.mContext.startActivity(packageManager.getLaunchIntentForPackage(pkg), bundle);
                    }
                }
            } catch (Exception e6) {
                Log.e(TAG, "FREE_FORM_LAUNCH error", e6);
            }
        }
        return false;
    }

    private ActivityOptions getWindowModeActivityOptions(int windowsMode) {
        ActivityOptions activityOptions = ActivityOptions.makeBasic();
        Class<ActivityOptions> cls = ActivityOptions.class;
        try {
            cls.getMethod("setLaunchWindowingMode", new Class[]{Integer.TYPE}).invoke(activityOptions, new Object[]{Integer.valueOf(windowsMode)});
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
        for (ActivityManager.RunningTaskInfo taskInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(100)) {
            if (taskInfo.topActivity.getPackageName().equals(packageName)) {
                int taskId = taskInfo.id;
                Log.i(TAG, "Abel ActivityTaskManagerService.java getFreeformTaskId packageName=" + packageName + ", taskId=" + taskId);
                return taskId;
            }
        }
        return 0;
    }

    private void sendAirControl(int data1, int data2) {
        KswMcuSender.getSender().sendMessage(122, new byte[]{(byte) data1, (byte) data2});
        KswMcuSender.getSender().sendMessageDelay(122, new byte[]{0, 0}, 200);
    }

    public void startDialSenderLooper() {
        Log.d(TAG, "startDialSenderLooper");
        if (this.benzDialSenderThread == null) {
            this.benzDialSenderThread = new Thread() {
                public void run() {
                    while (true) {
                        Log.d(CenterControlImpl.TAG, "startDialSenderLooper run");
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
        Log.d(TAG, "sendDial hour ： " + hour + ", minute : " + minute);
        KswMcuSender.getSender().sendMessage(123, new byte[]{(byte) clockSort2, (byte) hour, (byte) minute});
    }

    public void clearMemory() {
    }

    private void sendBenzControlData(McuStatus.BenzData benzData) {
        KswMcuSender sender = KswMcuSender.getSender();
        byte[] bArr = new byte[6];
        int i = 0;
        bArr[0] = (byte) (benzData.pressButton == 1 ? 1 : 0);
        bArr[1] = (byte) (benzData.pressButton == 2 ? 1 : 0);
        if (benzData.pressButton == 3) {
            i = 1;
        }
        bArr[2] = (byte) i;
        bArr[3] = (byte) benzData.light1;
        bArr[4] = (byte) benzData.light2;
        bArr[5] = (byte) benzData.key3;
        sender.sendMessage(121, bArr);
    }

    private void callButton() {
        KeyUtils.pressKey(5);
    }

    public void handleKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
            Log.i(TAG, "handleKeyEvent keycode - " + event.getKeyCode());
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
                Log.i(TAG, "catch custom keycode - " + event.getKeyCode());
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
                    Log.w(TAG, "chen--onMcuMessage: txzstatus =" + txzStatus);
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
                case MetricsProto.MetricsEvent.ACTION_WINDOW_UNDOCK_MAX /*390*/:
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
                    Log.d(TAG, "handleKeyEvent: OTA_START_UPGRADE");
                    WitsCommand.sendCommand(9, 103);
                    return;
                case 2022:
                    Log.d(TAG, "handleKeyEvent: OTA_REBOOT_DEVICE");
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
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.updateLocationEnabled(this.mContext, true, UserHandle.myUserId(), 1);
                    return;
                case 3024:
                    sendCarInfo2Txz(true, true, true, true, true, true, (McuStatus.CarData) null);
                    return;
                case 3025:
                    sendCarInfo2Txz(false, false, true, true, true, true, (McuStatus.CarData) null);
                    return;
                case 3026:
                    sendCarInfo2Txz(false, true, true, true, true, false, (McuStatus.CarData) null);
                    return;
                case 5000:
                    KswSettings.getSettings().clearKeys();
                    return;
                case BluetoothHidHost.INPUT_CONNECT_FAILED_ALREADY_CONNECTED:
                    KswSettings.getSettings().syncStatus();
                    return;
                case BluetoothHidHost.INPUT_CONNECT_FAILED_ATTEMPT_FAILED:
                    KswSettings.getSettings().setInt("testabcdefg", 123);
                    Set<String> intKeysFromSp = KswSettings.getSettings().getIntKeysFromSp();
                    Log.i("lktTest", intKeysFromSp.toArray()[0] + "");
                    return;
                case BluetoothHidHost.INPUT_OPERATION_GENERIC_FAILURE:
                    new Thread() {
                        public void run() {
                            int i = 0;
                            while (i < 120) {
                                i++;
                                KswMcuLogic.getTestMcu().getListener().onMcuMessage(McuStatus.ACData.getTestMsg2());
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    }.start();
                    return;
                case BluetoothHidHost.INPUT_OPERATION_SUCCESS:
                    KswMcuLogic.getTestMcu().getListener().onMcuMessage(new KswMessage(31, new byte[]{1, 2, 41, BluetoothHidDevice.ERROR_RSP_UNKNOWN, 4}));
                    return;
                case 5005:
                    final KswMcuListener listener = KswMcuLogic.getTestMcu().getListener();
                    listener.onMcuMessage(new KswMessage(31, new byte[]{17, 3, 6, 0, 0, 3, 22}));
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(TimedRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
                            } catch (InterruptedException e) {
                            }
                            try {
                                byte[] bytes = "渡口".getBytes();
                                byte[] bytes1 = "蔡琴".getBytes();
                                byte[] bytes2 = "惠威试音碟3".getBytes();
                                byte[] bytes3 = "\\音乐 1/20".getBytes();
                                byte[] data1 = new byte[(bytes.length + 1)];
                                byte[] data2 = new byte[(bytes1.length + 1)];
                                byte[] data3 = new byte[(bytes2.length + 1)];
                                byte[] data4 = new byte[(bytes3.length + 1)];
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
                            } catch (Exception e2) {
                            }
                        }
                    }.start();
                    return;
                case 5006:
                    McuStatus.MediaStringInfo mediaStringInfo = new McuStatus.MediaStringInfo();
                    mediaStringInfo.name = "渡口";
                    mediaStringInfo.artist = "蔡琴";
                    mediaStringInfo.album = "惠威试音碟3";
                    mediaStringInfo.folderName = "\\\\音乐 1/20";
                    PowerManagerApp.setStatusString("mcuMediaStringInfo", new Gson().toJson((Object) mediaStringInfo));
                    McuStatus.MediaPlayStatus mediaPlayStatus = new McuStatus.MediaPlayStatus();
                    mediaPlayStatus.ST = true;
                    mediaPlayStatus.ALS = true;
                    mediaPlayStatus.SCAN = true;
                    mediaPlayStatus.status = "WAIT";
                    mediaPlayStatus.type = 17;
                    PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson((Object) mediaPlayStatus));
                    return;
                case 5007:
                    McuStatus.MediaStringInfo mediaStringInfoNew = new McuStatus.MediaStringInfo();
                    mediaStringInfoNew.name = "渡口1";
                    mediaStringInfoNew.artist = "蔡琴2";
                    mediaStringInfoNew.album = "惠威试音碟32";
                    mediaStringInfoNew.folderName = "\\音乐1 1/220";
                    PowerManagerApp.setStatusString("mcuMediaStringInfo", new Gson().toJson((Object) mediaStringInfoNew));
                    McuStatus.MediaPlayStatus mediaPlayStatusNew = new McuStatus.MediaPlayStatus();
                    mediaPlayStatusNew.status = "PLAY";
                    mediaPlayStatusNew.type = 17;
                    PowerManagerApp.setStatusString("mcuMediaPlayStatus", new Gson().toJson((Object) mediaPlayStatusNew));
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
            Log.e(TAG, "open car fm error", e);
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

    public void updateStatusInfo(String jsonMsg) throws RemoteException {
    }

    public void setBrightness(int brightness) {
        KswMcuSender.getSender().sendMessage(108, new byte[]{1, (byte) brightness});
    }

    public void systemModeSwitch(int mode2) {
        KswMcuSender sender = KswMcuSender.getSender();
        int i = 2;
        byte[] bArr = new byte[2];
        bArr[0] = 18;
        if (mode2 != 2) {
            i = 1;
        }
        bArr[1] = (byte) i;
        sender.sendMessage(105, bArr);
    }

    public void closeScreen(boolean isClose) {
        KswMcuSender.getSender().sendMessage(108, new byte[]{2, isClose ^ true ? (byte) 1 : 0});
        SystemStatusControl.getDefault().getSystemStatus().screenSwitch = isClose ^ true ? 1 : 0;
        SystemStatusControl.getDefault().handleSystemStatus();
    }

    public void exitSource(int source) {
        KswMcuSender.getSender().sendMessage(104, new byte[]{4, (byte) source});
    }

    public void usingNaviProtocol(boolean using) {
        if (using) {
            this.mHandler.removeCallbacks(this.stopNaviProtocolRunnable);
            KswMcuSender.getSender().sendMessage(105, new byte[]{19, 1, 0});
            return;
        }
        this.mHandler.postDelayed(this.stopNaviProtocolRunnable, 500);
    }

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
        } else if (!SystemStatusControl.getStatus().topApp.equals(KswSettings.getSettings().getSettingsString("NaviApp"))) {
            return false;
        } else {
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
        }
    }

    public void saveForwardCamMirror(int on) {
        Log.d(TAG, "forwardCamMirror init is " + on);
        try {
            PowerManagerApp.setSettingsInt("forwardCamMirror", on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initBenzClockSort(int clockSort2) {
        Log.d(TAG, "clockSort init is " + clockSort2);
        if (hasClockConfigFunction()) {
            updateClockSort(clockSort2);
            startDialSenderLooper();
        }
    }

    private void updateClockSort(int clockSort2) {
        Log.d(TAG, "updateClockSort " + clockSort2);
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
        Log.d(TAG, "configDialByUser " + clockSort2);
        if (hasClockConfigFunction()) {
            updateClockSort(clockSort2);
            sendDial(clockSort2);
        }
    }

    public int getCpCallStatus() {
        return this.cpCallStatus;
    }

    private void closeWifiHotspot() {
        new ConnectivityManagerMirror((ConnectivityManager) this.mContext.getSystemService("connectivity")).stopTethering(0);
    }
}
