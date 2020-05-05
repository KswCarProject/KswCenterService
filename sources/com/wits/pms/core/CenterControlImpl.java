package com.wits.pms.core;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.wits.pms.BuildConfig;
import com.wits.pms.ICmdListener;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.EcarMessage;
import com.wits.pms.bean.TxzMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.consts.KeycodeCustom;
import com.wits.pms.custom.BtController;
import com.wits.pms.custom.KswFunction;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.interfaces.CenterControl;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.interfaces.MemoryKiller;
import com.wits.pms.mcu.McuSender;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.McuCommandListener;
import com.wits.pms.mcu.custom.utils.SplashFlasher;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.receiver.AutoKitCallBackImpl;
import com.wits.pms.receiver.CenterCallBackImpl;
import com.wits.pms.receiver.ReceiverHandler;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.AmsUtil;
import com.wits.pms.utils.KeyUtils;
import com.wits.pms.utils.SystemProperties;
import com.wits.pms.utils.Test;
import com.wits.pms.utils.UsbUtil;
import java.io.File;

public class CenterControlImpl extends ICmdListener.Stub implements CenterControl, KswFunction {
    static final String TAG = "CenterControl";
    private static CenterControlImpl mCenterControl;
    private int beforeMuteVolume;
    private ActivityManager mActivityManager;
    private final AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public Context mContext;
    private final Handler mHandler;
    private LogicSystem mLogicSystem;
    private McuSender mMcuSender;
    private MemoryKiller mMemoryKiller;
    private boolean muteStatus;
    private int[] sourceArray = {1, 2, 3, 5, 6, 9};
    private boolean statusChange;
    private Runnable stopNaviProtocolRunnable = CenterControlImpl$$Lambda$0.$instance;

    public CenterControlImpl(Context context) {
        this.mContext = context;
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
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
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0223, code lost:
        if (r3.equals("bluetooth.hangup") != false) goto L_0x0285;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x02db, code lost:
        if (r3.equals("volume.mute") != false) goto L_0x0325;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        if (r3.equals("music.random") != false) goto L_0x00b5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(com.wits.pms.bean.TxzMessage r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            java.lang.String r2 = r1.action
            int r3 = r1.keyType
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r3 == r4) goto L_0x0402
            r5 = 1010(0x3f2, float:1.415E-42)
            r6 = 3
            r7 = 0
            r8 = 1
            if (r3 == r5) goto L_0x03c6
            r5 = 1020(0x3fc, float:1.43E-42)
            if (r3 == r5) goto L_0x034a
            r5 = 1030(0x406, float:1.443E-42)
            r9 = 5
            r10 = 6
            r11 = 4
            r12 = 7
            r13 = -1
            r14 = 2
            if (r3 == r5) goto L_0x02cb
            r5 = 1040(0x410, float:1.457E-42)
            r15 = 10
            r16 = 9
            r17 = 8
            if (r3 == r5) goto L_0x01fc
            r5 = 1050(0x41a, float:1.471E-42)
            if (r3 == r5) goto L_0x01a4
            r5 = 1091(0x443, float:1.529E-42)
            if (r3 == r5) goto L_0x00ed
            switch(r3) {
                case 1060: goto L_0x0038;
                case 1061: goto L_0x0038;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x043b
        L_0x0038:
            java.lang.String r3 = r1.action
            int r5 = r3.hashCode()
            switch(r5) {
                case -1362146736: goto L_0x00aa;
                case -825432804: goto L_0x00a0;
                case -825393037: goto L_0x0096;
                case -825367203: goto L_0x008c;
                case -825361316: goto L_0x0082;
                case -770549500: goto L_0x0077;
                case -734529399: goto L_0x006c;
                case 171428079: goto L_0x0062;
                case 183111917: goto L_0x0058;
                case 1438537900: goto L_0x004f;
                case 1461409024: goto L_0x0043;
                default: goto L_0x0041;
            }
        L_0x0041:
            goto L_0x00b4
        L_0x0043:
            java.lang.String r5 = "music.loop.all"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 8
            goto L_0x00b5
        L_0x004f:
            java.lang.String r5 = "music.random"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            goto L_0x00b5
        L_0x0058:
            java.lang.String r5 = "music.pause"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 4
            goto L_0x00b5
        L_0x0062:
            java.lang.String r5 = "music.close"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 1
            goto L_0x00b5
        L_0x006c:
            java.lang.String r5 = "music.loop.single"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 9
            goto L_0x00b5
        L_0x0077:
            java.lang.String r5 = "music.loop.random"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 10
            goto L_0x00b5
        L_0x0082:
            java.lang.String r5 = "music.prev"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 6
            goto L_0x00b5
        L_0x008c:
            java.lang.String r5 = "music.play"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 2
            goto L_0x00b5
        L_0x0096:
            java.lang.String r5 = "music.open"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 0
            goto L_0x00b5
        L_0x00a0:
            java.lang.String r5 = "music.next"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 5
            goto L_0x00b5
        L_0x00aa:
            java.lang.String r5 = "music.continue"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x00b4
            r12 = 3
            goto L_0x00b5
        L_0x00b4:
            r12 = -1
        L_0x00b5:
            switch(r12) {
                case 0: goto L_0x00e7;
                case 1: goto L_0x00e3;
                case 2: goto L_0x00cc;
                case 3: goto L_0x00c8;
                case 4: goto L_0x00c4;
                case 5: goto L_0x00c0;
                case 6: goto L_0x00bc;
                case 7: goto L_0x00bb;
                case 8: goto L_0x00ba;
                case 9: goto L_0x00b9;
                default: goto L_0x00b8;
            }
        L_0x00b8:
            goto L_0x00eb
        L_0x00b9:
            goto L_0x00eb
        L_0x00ba:
            goto L_0x00eb
        L_0x00bb:
            goto L_0x00eb
        L_0x00bc:
            r18.mediaPrevious()
            goto L_0x00eb
        L_0x00c0:
            r18.mediaNext()
            goto L_0x00eb
        L_0x00c4:
            r18.mediaPause()
            goto L_0x00eb
        L_0x00c8:
            r18.mediaPlay()
            goto L_0x00eb
        L_0x00cc:
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
            java.lang.String r5 = "package"
            java.lang.String r6 = "com.txznet.music"
            r3.putString(r5, r6)
            com.wits.pms.bean.TxzMessage r5 = new com.wits.pms.bean.TxzMessage
            java.lang.String r6 = "app.open"
            r5.<init>(r4, r6, r3)
            r0.handle((com.wits.pms.bean.TxzMessage) r5)
            goto L_0x00eb
        L_0x00e3:
            r18.closeMusic()
            goto L_0x00eb
        L_0x00e7:
            r18.openMusic()
        L_0x00eb:
            goto L_0x043b
        L_0x00ed:
            java.lang.String r3 = "dvr.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0102
            com.wits.pms.custom.KswSettings r3 = com.wits.pms.custom.KswSettings.getSettings()
            java.lang.String r4 = "DVRApk_PackageName"
            java.lang.String r3 = r3.getSettingsString(r4)
            r0.openApp(r3)
        L_0x0102:
            java.lang.String r3 = "dvr.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0117
            com.wits.pms.custom.KswSettings r3 = com.wits.pms.custom.KswSettings.getSettings()
            java.lang.String r4 = "DVRApk_PackageName"
            java.lang.String r3 = r3.getSettingsString(r4)
            r0.closeApp(r3)
        L_0x0117:
            java.lang.String r3 = "video.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0127
            java.lang.String r3 = "com.txznet.music"
            r0.killApp(r3)
            r18.openVideo()
        L_0x0127:
            java.lang.String r3 = "video.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0132
            r18.closeVideo()
        L_0x0132:
            java.lang.String r3 = "tv.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x013d
            r0.openDtv(r8)
        L_0x013d:
            java.lang.String r3 = "tv.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0148
            r0.systemModeSwitch(r8)
        L_0x0148:
            java.lang.String r3 = "aux.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0153
            r0.openAux(r8)
        L_0x0153:
            java.lang.String r3 = "aux.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x015e
            r0.systemModeSwitch(r8)
        L_0x015e:
            java.lang.String r3 = "original_car.open"
            boolean r3 = r3.equals(r2)
            r4 = 105(0x69, float:1.47E-43)
            if (r3 == 0) goto L_0x0181
            com.wits.pms.mcu.custom.KswMcuSender r3 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            r5 = 103(0x67, float:1.44E-43)
            byte[] r6 = new byte[r8]
            r6[r7] = r7
            r3.sendMessage(r5, r6)
            com.wits.pms.mcu.custom.KswMcuSender r3 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            byte[] r5 = new byte[r14]
            r5 = {18, 2} // fill-array
            r3.sendMessage(r4, r5)
        L_0x0181:
            java.lang.String r3 = "mobile_internet.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x018e
            java.lang.String r3 = "net.easyconn"
            r0.openApp(r3)
        L_0x018e:
            java.lang.String r3 = "android.home"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x043b
            com.wits.pms.mcu.custom.KswMcuSender r3 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            byte[] r5 = new byte[r14]
            r5 = {18, 1} // fill-array
            r3.sendMessage(r4, r5)
            goto L_0x043b
        L_0x01a4:
            java.lang.String r3 = r1.action
            int r4 = r3.hashCode()
            r5 = -1480935867(0xffffffffa7bab645, float:-5.1822988E-15)
            if (r4 == r5) goto L_0x01dd
            r5 = -47450874(0xfffffffffd2bf506, float:-1.4285639E37)
            if (r4 == r5) goto L_0x01d3
            r5 = -47411107(0xfffffffffd2c905d, float:-1.433605E37)
            if (r4 == r5) goto L_0x01c9
            r5 = -47379386(0xfffffffffd2d0c46, float:-1.4376261E37)
            if (r4 == r5) goto L_0x01bf
            goto L_0x01e6
        L_0x01bf:
            java.lang.String r4 = "radio.prev"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01e6
            r13 = 2
            goto L_0x01e6
        L_0x01c9:
            java.lang.String r4 = "radio.open"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01e6
            r13 = 0
            goto L_0x01e6
        L_0x01d3:
            java.lang.String r4 = "radio.next"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01e6
            r13 = 3
            goto L_0x01e6
        L_0x01dd:
            java.lang.String r4 = "radio.close"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01e6
            r13 = 1
        L_0x01e6:
            switch(r13) {
                case 0: goto L_0x01f6;
                case 1: goto L_0x01f2;
                case 2: goto L_0x01ee;
                case 3: goto L_0x01ea;
                default: goto L_0x01e9;
            }
        L_0x01e9:
            goto L_0x01fa
        L_0x01ea:
            r18.fmNext()
            goto L_0x01fa
        L_0x01ee:
            r18.fmPrevious()
            goto L_0x01fa
        L_0x01f2:
            r18.fmClose()
            goto L_0x01fa
        L_0x01f6:
            r18.fmOpen()
        L_0x01fa:
            goto L_0x043b
        L_0x01fc:
            java.lang.String r3 = r1.action
            int r4 = r3.hashCode()
            switch(r4) {
                case -1362146736: goto L_0x0279;
                case -825432804: goto L_0x026e;
                case -825393037: goto L_0x0264;
                case -825361316: goto L_0x0259;
                case 171428079: goto L_0x024f;
                case 179334558: goto L_0x0245;
                case 179706250: goto L_0x023b;
                case 183111917: goto L_0x0230;
                case 486135880: goto L_0x0226;
                case 685022669: goto L_0x021d;
                case 974886623: goto L_0x0212;
                case 1264734904: goto L_0x0207;
                default: goto L_0x0205;
            }
        L_0x0205:
            goto L_0x0284
        L_0x0207:
            java.lang.String r4 = "bluetooth.close"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 1
            goto L_0x0285
        L_0x0212:
            java.lang.String r4 = "bluetooth.reject"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 5
            goto L_0x0285
        L_0x021d:
            java.lang.String r4 = "bluetooth.hangup"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            goto L_0x0285
        L_0x0226:
            java.lang.String r4 = "bluetooth.accept"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 4
            goto L_0x0285
        L_0x0230:
            java.lang.String r4 = "music.pause"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 8
            goto L_0x0285
        L_0x023b:
            java.lang.String r4 = "bluetooth.open"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 0
            goto L_0x0285
        L_0x0245:
            java.lang.String r4 = "bluetooth.call"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 2
            goto L_0x0285
        L_0x024f:
            java.lang.String r4 = "music.close"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 7
            goto L_0x0285
        L_0x0259:
            java.lang.String r4 = "music.prev"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 11
            goto L_0x0285
        L_0x0264:
            java.lang.String r4 = "music.open"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 6
            goto L_0x0285
        L_0x026e:
            java.lang.String r4 = "music.next"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 10
            goto L_0x0285
        L_0x0279:
            java.lang.String r4 = "music.continue"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0284
            r6 = 9
            goto L_0x0285
        L_0x0284:
            r6 = -1
        L_0x0285:
            switch(r6) {
                case 0: goto L_0x02c5;
                case 1: goto L_0x02c1;
                case 2: goto L_0x02ad;
                case 3: goto L_0x02a9;
                case 4: goto L_0x02a5;
                case 5: goto L_0x02a1;
                case 6: goto L_0x029d;
                case 7: goto L_0x0299;
                case 8: goto L_0x0295;
                case 9: goto L_0x0291;
                case 10: goto L_0x028d;
                case 11: goto L_0x0289;
                default: goto L_0x0288;
            }
        L_0x0288:
            goto L_0x02c9
        L_0x0289:
            r18.mediaPrevious()
            goto L_0x02c9
        L_0x028d:
            r18.mediaNext()
            goto L_0x02c9
        L_0x0291:
            r18.mediaPlay()
            goto L_0x02c9
        L_0x0295:
            r18.mediaPause()
            goto L_0x02c9
        L_0x0299:
            r18.btMusicStop()
            goto L_0x02c9
        L_0x029d:
            r18.btMusicOpen()
            goto L_0x02c9
        L_0x02a1:
            r18.handUpPhone()
            goto L_0x02c9
        L_0x02a5:
            r18.acceptPhone()
            goto L_0x02c9
        L_0x02a9:
            r18.handUpPhone()
            goto L_0x02c9
        L_0x02ad:
            android.os.Bundle r3 = r1.bundle
            java.lang.String r4 = "name"
            java.lang.String r3 = r3.getString(r4)
            android.os.Bundle r4 = r1.bundle
            java.lang.String r5 = "number"
            java.lang.String r4 = r4.getString(r5)
            r0.btPhoneCall(r3, r4)
            goto L_0x02c9
        L_0x02c1:
            r18.closeBluetooth()
            goto L_0x02c9
        L_0x02c5:
            r0.openBluetooth(r8)
        L_0x02c9:
            goto L_0x043b
        L_0x02cb:
            java.lang.String r3 = r1.action
            int r4 = r3.hashCode()
            switch(r4) {
                case -2128329265: goto L_0x031a;
                case -2128329233: goto L_0x0310;
                case -1553713362: goto L_0x0306;
                case -1553708278: goto L_0x02fc;
                case -1553704816: goto L_0x02f2;
                case -1553704578: goto L_0x02e8;
                case -920463626: goto L_0x02de;
                case -920189843: goto L_0x02d5;
                default: goto L_0x02d4;
            }
        L_0x02d4:
            goto L_0x0324
        L_0x02d5:
            java.lang.String r4 = "volume.mute"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            goto L_0x0325
        L_0x02de:
            java.lang.String r4 = "volume.down"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 1
            goto L_0x0325
        L_0x02e8:
            java.lang.String r4 = "volume.min"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 3
            goto L_0x0325
        L_0x02f2:
            java.lang.String r4 = "volume.max"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 2
            goto L_0x0325
        L_0x02fc:
            java.lang.String r4 = "volume.inc"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 5
            goto L_0x0325
        L_0x0306:
            java.lang.String r4 = "volume.dec"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 6
            goto L_0x0325
        L_0x0310:
            java.lang.String r4 = "volume.up"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 0
            goto L_0x0325
        L_0x031a:
            java.lang.String r4 = "volume.to"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0324
            r12 = 4
            goto L_0x0325
        L_0x0324:
            r12 = -1
        L_0x0325:
            switch(r12) {
                case 0: goto L_0x0344;
                case 1: goto L_0x0340;
                case 2: goto L_0x033c;
                case 3: goto L_0x0338;
                case 4: goto L_0x0337;
                case 5: goto L_0x0336;
                case 6: goto L_0x0335;
                case 7: goto L_0x0329;
                default: goto L_0x0328;
            }
        L_0x0328:
            goto L_0x0348
        L_0x0329:
            android.os.Bundle r3 = r1.bundle
            java.lang.String r4 = "mute"
            boolean r3 = r3.getBoolean(r4)
            r0.mute(r3)
            goto L_0x0348
        L_0x0335:
            goto L_0x0348
        L_0x0336:
            goto L_0x0348
        L_0x0337:
            goto L_0x0348
        L_0x0338:
            r18.volumeMin()
            goto L_0x0348
        L_0x033c:
            r18.volumeMax()
            goto L_0x0348
        L_0x0340:
            r18.volumeDown()
            goto L_0x0348
        L_0x0344:
            r18.volumeUp()
        L_0x0348:
            goto L_0x043b
        L_0x034a:
            android.content.Context r3 = r0.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.lang.String r4 = "screen_brightness"
            r5 = 30
            int r3 = android.provider.Settings.System.getInt(r3, r4, r5)
            java.lang.String r4 = "light_up"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x036d
            int r3 = r3 + 42
            android.content.Context r4 = r0.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r6 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r6, r3)
        L_0x036d:
            java.lang.String r4 = "light_down"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x0382
            int r3 = r3 + -42
            android.content.Context r4 = r0.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r6 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r6, r3)
        L_0x0382:
            java.lang.String r4 = "light_min"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x0397
            r3 = 30
            android.content.Context r4 = r0.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r6 = "screen_brightness"
            android.provider.Settings.System.putInt(r4, r6, r5)
        L_0x0397:
            java.lang.String r4 = "light_max"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x03ae
            r3 = 255(0xff, float:3.57E-43)
            android.content.Context r4 = r0.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "screen_brightness"
            r6 = 255(0xff, float:3.57E-43)
            android.provider.Settings.System.putInt(r4, r5, r6)
        L_0x03ae:
            java.lang.String r4 = "CenterControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Brightness set to "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.i(r4, r5)
            goto L_0x043b
        L_0x03c6:
            java.lang.String r3 = "wifi.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x03d2
            r0.wifiOperation(r8)
            goto L_0x043b
        L_0x03d2:
            java.lang.String r3 = "wifi.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x03de
            r0.wifiOperation(r7)
            goto L_0x043b
        L_0x03de:
            java.lang.String r3 = "go.home"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x03ea
            com.wits.pms.utils.KeyUtils.pressKey(r6)
            goto L_0x043b
        L_0x03ea:
            java.lang.String r3 = "screen.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x03f6
            r0.displayScreen(r7)
            goto L_0x043b
        L_0x03f6:
            java.lang.String r3 = "screen.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x043b
            r0.displayScreen(r8)
            goto L_0x043b
        L_0x0402:
            java.lang.String r3 = "app.open"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0428
            android.os.Bundle r3 = r1.bundle
            java.lang.String r4 = "package"
            java.lang.String r3 = r3.getString(r4)
            java.lang.String r4 = "com.txznet.music"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x0424
            boolean r4 = r0.isAppInstalled(r3)
            if (r4 != 0) goto L_0x0424
            r18.openMusic()
            return
        L_0x0424:
            r0.openApp(r3)
            goto L_0x043b
        L_0x0428:
            java.lang.String r3 = "app.close"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x043b
            android.os.Bundle r3 = r1.bundle
            java.lang.String r4 = "package"
            java.lang.String r3 = r3.getString(r4)
            r0.closeApp(r3)
        L_0x043b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.core.CenterControlImpl.handle(com.wits.pms.bean.TxzMessage):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006d, code lost:
        if (r0.equals("MAIN_PAGE_SHOW") != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x015b, code lost:
        if (r0.equals("CMD_MIC_STOP") != false) goto L_0x015f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(com.wits.pms.bean.ZlinkMessage r9) {
        /*
            r8 = this;
            java.lang.String r0 = r9.status
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = -1
            r5 = 0
            r6 = 1
            if (r0 == 0) goto L_0x011f
            java.lang.String r0 = r9.status
            int r7 = r0.hashCode()
            switch(r7) {
                case -2087582999: goto L_0x0070;
                case -1843701849: goto L_0x0067;
                case -497207953: goto L_0x005d;
                case 2142494: goto L_0x0053;
                case 66129592: goto L_0x0049;
                case 143012129: goto L_0x003e;
                case 143018830: goto L_0x0033;
                case 1015497884: goto L_0x0029;
                case 1709675476: goto L_0x001f;
                case 1766422463: goto L_0x0015;
                default: goto L_0x0013;
            }
        L_0x0013:
            goto L_0x007a
        L_0x0015:
            java.lang.String r1 = "PHONE_CALL_OFF"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 7
            goto L_0x007b
        L_0x001f:
            java.lang.String r1 = "MAIN_PAGE_HIDDEN"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 5
            goto L_0x007b
        L_0x0029:
            java.lang.String r1 = "DISCONNECT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 1
            goto L_0x007b
        L_0x0033:
            java.lang.String r1 = "SWITCHOTG"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 8
            goto L_0x007b
        L_0x003e:
            java.lang.String r1 = "SWITCHHUB"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 9
            goto L_0x007b
        L_0x0049:
            java.lang.String r1 = "ENTER"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 2
            goto L_0x007b
        L_0x0053:
            java.lang.String r1 = "EXIT"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 3
            goto L_0x007b
        L_0x005d:
            java.lang.String r1 = "PHONE_CALL_ON"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 6
            goto L_0x007b
        L_0x0067:
            java.lang.String r2 = "MAIN_PAGE_SHOW"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x007a
            goto L_0x007b
        L_0x0070:
            java.lang.String r1 = "CONNECTED"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007a
            r1 = 0
            goto L_0x007b
        L_0x007a:
            r1 = -1
        L_0x007b:
            switch(r1) {
                case 0: goto L_0x00fc;
                case 1: goto L_0x00d2;
                case 2: goto L_0x00ca;
                case 3: goto L_0x00c2;
                case 4: goto L_0x00ba;
                case 5: goto L_0x00b2;
                case 6: goto L_0x00a2;
                case 7: goto L_0x0092;
                case 8: goto L_0x0089;
                case 9: goto L_0x0080;
                default: goto L_0x007e;
            }
        L_0x007e:
            goto L_0x011e
        L_0x0080:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  SWITCHHUB"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x0089:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  SWITCHOTG"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x0092:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  PHONE_CALL_OFF"
            android.util.Log.d(r0, r1)
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "0"
            com.wits.pms.utils.SystemProperties.set(r0, r1)
            goto L_0x011e
        L_0x00a2:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  PHONE_CALL_ON"
            android.util.Log.d(r0, r1)
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "1"
            com.wits.pms.utils.SystemProperties.set(r0, r1)
            goto L_0x011e
        L_0x00b2:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  MAIN_PAGE_HIDDEN"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x00ba:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  MAIN_PAGE_SHOW"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x00c2:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  EXIT"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x00ca:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  ENTER"
            android.util.Log.d(r0, r1)
            goto L_0x011e
        L_0x00d2:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  DISCONNECT"
            android.util.Log.d(r0, r1)
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r1 = "0"
            com.wits.pms.utils.SystemProperties.set(r0, r1)
            r8.openBluetooth(r5)
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ Exception -> 0x00f7 }
            if (r0 == 0) goto L_0x00f6
            java.lang.String r1 = "systemMode"
            int r1 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r1)     // Catch:{ Exception -> 0x00f7 }
            if (r1 != r6) goto L_0x00f6
            r8.setTxzSleep(r5)     // Catch:{ Exception -> 0x00f7 }
        L_0x00f6:
            goto L_0x011e
        L_0x00f7:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x011e
        L_0x00fc:
            java.lang.String r0 = "CenterControl"
            java.lang.String r1 = "handle  CONNECTED"
            android.util.Log.d(r0, r1)
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r1 = "1"
            com.wits.pms.utils.SystemProperties.set(r0, r1)
            r8.closeBluetooth()
            java.lang.String r0 = "Support_TXZ"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getSettingsInt(r0)     // Catch:{ Exception -> 0x0119 }
            if (r0 == 0) goto L_0x0118
            r8.setTxzSleep(r6)     // Catch:{ Exception -> 0x0119 }
        L_0x0118:
            goto L_0x011e
        L_0x0119:
            r0 = move-exception
            r0.printStackTrace()
        L_0x011e:
            goto L_0x017c
        L_0x011f:
            java.lang.String r0 = r9.command
            if (r0 == 0) goto L_0x017c
            java.lang.String r0 = r9.command
            int r7 = r0.hashCode()
            switch(r7) {
                case -2126047073: goto L_0x0155;
                case -1482963131: goto L_0x014b;
                case -150661894: goto L_0x0141;
                case 871291637: goto L_0x0137;
                case 1920379072: goto L_0x012d;
                default: goto L_0x012c;
            }
        L_0x012c:
            goto L_0x015e
        L_0x012d:
            java.lang.String r1 = "ACTION_ZJ_IPODFOUND"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x015e
            r1 = 2
            goto L_0x015f
        L_0x0137:
            java.lang.String r1 = "REQ_OS_AUDIO_FOCUS"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x015e
            r1 = 0
            goto L_0x015f
        L_0x0141:
            java.lang.String r1 = "ACTION_ZJ_PHONEFOUND"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x015e
            r1 = 1
            goto L_0x015f
        L_0x014b:
            java.lang.String r1 = "CMD_MIC_START"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x015e
            r1 = 3
            goto L_0x015f
        L_0x0155:
            java.lang.String r2 = "CMD_MIC_STOP"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x015e
            goto L_0x015f
        L_0x015e:
            r1 = -1
        L_0x015f:
            switch(r1) {
                case 0: goto L_0x017b;
                case 1: goto L_0x017a;
                case 2: goto L_0x0179;
                case 3: goto L_0x0163;
                default: goto L_0x0162;
            }
        L_0x0162:
            goto L_0x017c
        L_0x0163:
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x017c
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r1 = "2"
            com.wits.pms.utils.SystemProperties.set(r0, r1)
            goto L_0x017c
        L_0x0179:
            goto L_0x017c
        L_0x017a:
            goto L_0x017c
        L_0x017b:
        L_0x017c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.core.CenterControlImpl.handle(com.wits.pms.bean.ZlinkMessage):void");
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

    public void openSpeech() {
        new TxzMessage(2400, "txz.window.open", (Bundle) null).sendBroadCast(this.mContext);
    }

    public void closeSpeech() {
        new TxzMessage(2400, "txz.window.close", (Bundle) null).sendBroadCast(this.mContext);
    }

    public void setTxzWindow(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString("mode", on ? "top" : "none");
        new TxzMessage(2400, "txz.float.mode", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzSleep(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString(NotificationCompat.CATEGORY_STATUS, on ? "before.sleep" : "wakeup");
        new TxzMessage(2010, "system.status", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzQuickQuit(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putString(NotificationCompat.CATEGORY_STATUS, on ? "reverse.enter" : "reverse.quit");
        new TxzMessage(2010, "system.status", bundle).sendBroadCast(this.mContext);
    }

    public void setTxzSwitch(boolean on) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("enable", on);
        new TxzMessage(2400, "txz.enable", bundle).sendBroadCast(this.mContext);
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

    public void closeVideo() {
        WitsCommand.sendCommand(2, 112, BuildConfig.FLAVOR);
    }

    public void closeMusic() {
        WitsCommand.sendCommand(2, 106, BuildConfig.FLAVOR);
    }

    public void closePIP() {
        WitsCommand.sendCommand(2, 118, BuildConfig.FLAVOR);
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
        }
        for (ActivityManager.RunningTaskInfo task : this.mActivityManager.getRunningTasks(20)) {
            if (pkgName.equals(task.topActivity.getPackageName())) {
                int id = task.id;
                Class<ActivityManager> cls = ActivityManager.class;
                try {
                    cls.getMethod("removeTask", new Class[]{Integer.TYPE}).invoke(this.mActivityManager, new Object[]{Integer.valueOf(id)});
                } catch (Exception e) {
                }
            }
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
        int pid = AmsUtil.getPid("cn.kuwo.kwmusiccar");
        Log.d(TAG, "kuWoMusicPause  pid = " + pid);
        if (pid != -1) {
            KeyUtils.pressKey(127);
        }
        KeyUtils.pressKey(127);
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
        CenterCallBackImpl.getImpl(this.mContext).sendBroadCast(new TxzMessage(2062, "mcu.open", (Bundle) null));
        this.mContext.sendBroadcast(new Intent("mcu.car_mode.open"));
        this.mContext.sendBroadcast(new Intent("com.android.minwin.clos"));
        if (Settings.System.getInt(this.mContext.getContentResolver(), "CarDisplay", 1) != 0) {
            backToHome();
        }
    }

    private void stopMusic() {
        KeyUtils.pressKey(86);
    }

    public void stopZlinkMusic() {
        Log.d(TAG, "stopZlinkMusic");
        if (SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1")) {
            new ZlinkMessage(ZlinkMessage.ZLINK_POWER_OFF_ACTION).sendBroadCast(this.mContext);
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

    private void playZlinkMusic() {
        Bundle bundle = new Bundle();
        bundle.putString("command", "REQ_SPEC_FUNC_CMD");
        bundle.putInt("specFuncCode", 126);
        new ZlinkMessage(ZlinkMessage.ZLINK_NORMAL_ACTION, "REQ_SPEC_FUNC_CMD", bundle).sendBroadCast(this.mContext);
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

    public void volumeUp() {
        this.mAudioManager.adjustStreamVolume(3, 1, 1);
    }

    public void volumeDown() {
        this.mAudioManager.adjustStreamVolume(3, -1, 1);
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
        String topApp = SystemStatusControl.getStatus().topApp;
        openSource(nextMode);
        if (nextMode != 2) {
            killApp(topApp);
        }
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
        btPhoneCall(BuildConfig.FLAVOR, BuildConfig.FLAVOR);
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
                this.mHandler.post(new CenterControlImpl$$Lambda$1(this, open));
            }
        } catch (RemoteException e) {
            openAndroidBluetooth();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$openBluetooth$0$CenterControlImpl(boolean open) {
        if (!open || !SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1") || !SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT).equals("1")) {
            Settings.System.putInt(this.mContext.getContentResolver(), "btSwitch", 1);
        }
        if (!WitsCommand.sendCommandGetResult(3, 107, open ? "true" : "false")) {
            openAndroidBluetooth();
        }
    }

    public void closeBluetooth() {
        Settings.System.putInt(this.mContext.getContentResolver(), "btSwitch", 0);
        WitsCommand.sendCommand(3, 105, BuildConfig.FLAVOR);
    }

    public void btPhoneCall(String name, String number) {
        WitsCommand.sendCommand(3, 108, "{number:" + number + "}");
    }

    public void handUpPhone() {
        WitsCommand.sendCommand(3, 109, BuildConfig.FLAVOR);
    }

    public void acceptPhone() {
        WitsCommand.sendCommand(3, 112, BuildConfig.FLAVOR);
    }

    public void btMusicPlayPause() {
        WitsCommand.sendCommand(3, 102, BuildConfig.FLAVOR);
    }

    public void btMusicPrev() {
        WitsCommand.sendCommand(3, 100, BuildConfig.FLAVOR);
    }

    public void btMusicNext() {
        WitsCommand.sendCommand(3, 101, BuildConfig.FLAVOR);
    }

    public void btMusicContinue() {
        WitsCommand.sendCommand(3, 103, BuildConfig.FLAVOR);
    }

    public void btMusicStop() {
        WitsCommand.sendCommand(3, 104, BuildConfig.FLAVOR);
    }

    public void btMusicRelease() {
        WitsCommand.sendCommand(3, 104, BuildConfig.FLAVOR);
    }

    public void switchSoundToCar() {
        WitsCommand.sendCommand(3, 111, BuildConfig.FLAVOR);
    }

    public void switchSoundToPhone() {
        WitsCommand.sendCommand(3, 110, BuildConfig.FLAVOR);
    }

    public boolean handleCommand(String jsonMsg) throws RemoteException {
        WitsCommand witsCommand = WitsCommand.getWitsCommandFormJson(jsonMsg);
        if (witsCommand.getCommand() == 1) {
            int subCommand = witsCommand.getSubCommand();
            if (subCommand == 200) {
                File config = new File(witsCommand.getJsonArg());
                if (config.exists()) {
                    KswSettings.getSettings().updateConfig(config);
                }
                return true;
            } else if (subCommand == 300) {
                clearMemory();
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
                    case WitsCommand.SystemCommand.MEDIA_PLAY_PAUSE /*121*/:
                        mediaPlayPause();
                        return true;
                    case WitsCommand.SystemCommand.USB_HOST /*122*/:
                        if (TextUtils.isEmpty(witsCommand.getJsonArg())) {
                            return false;
                        }
                        usbHost("on".equals(witsCommand.getJsonArg()));
                        return true;
                    case WitsCommand.SystemCommand.CALL_BUTTON /*123*/:
                        callButton();
                        return true;
                    case WitsCommand.SystemCommand.FLASH_SPLASH /*124*/:
                        SplashFlasher.flashLogo(witsCommand.getJsonArg());
                        break;
                    case WitsCommand.SystemCommand.SAVECONFIG_AND_REBOOT /*125*/:
                        KswSettings.getSettings().saveAndReboot();
                        break;
                    default:
                        switch (subCommand) {
                            case WitsCommand.SystemCommand.CAR_MODE /*601*/:
                                systemModeSwitch(2);
                                return true;
                            case WitsCommand.SystemCommand.ANDROID_MODE /*602*/:
                                systemModeSwitch(1);
                                return true;
                            case WitsCommand.SystemCommand.OUT_MODE /*603*/:
                                try {
                                    String jsonArg = witsCommand.getJsonArg();
                                    if (TextUtils.isEmpty(jsonArg)) {
                                        exitSource(Integer.parseInt(jsonArg));
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            case WitsCommand.SystemCommand.OPEN_MODE /*604*/:
                                try {
                                    openSourceMode(Integer.parseInt(witsCommand.getJsonArg()));
                                    return true;
                                } catch (Exception e2) {
                                    return false;
                                }
                            case WitsCommand.SystemCommand.OPEN_AUX /*605*/:
                                openAux(false);
                                return true;
                            case WitsCommand.SystemCommand.OPEN_DTV /*606*/:
                                openDtv(false);
                                return true;
                            case WitsCommand.SystemCommand.OPEN_BT /*607*/:
                                openBluetooth(true);
                                return true;
                            case WitsCommand.SystemCommand.USING_NAVI /*608*/:
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
                            case WitsCommand.SystemCommand.OPEN_CVBSDVR /*609*/:
                                openCarDvr();
                                return true;
                            case WitsCommand.SystemCommand.OPEN_F_CAM /*610*/:
                                openFrontCamera(false);
                                return true;
                        }
                }
            } else {
                BtController.rebootBt();
            }
        } else if (witsCommand.getCommand() == 5) {
            McuCommandListener.handleCommand(witsCommand);
        }
        return false;
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
        sender.sendMessage(WitsCommand.SystemCommand.MEDIA_PLAY_PAUSE, bArr);
    }

    private void callButton() {
        KeyUtils.pressKey(5);
    }

    public void handleKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
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
                case KeycodeCustom.KEYCODE_MODE:
                    sourceSwitch();
                    return;
                case KeycodeCustom.KEYCODE_VOICE_ASSIST:
                    openSpeech();
                    return;
                case KeycodeCustom.KEYCODE_NAVI:
                    openNavi();
                    return;
                case KeycodeCustom.KEYVCODE_OPEN_BT:
                    openBluetooth(false);
                    return;
                case KeycodeCustom.KEYVCODE_CLOSE_BT:
                    closeBluetooth();
                    return;
                case 380:
                    systemModeSwitch(2);
                    break;
                case 381:
                    break;
                case 390:
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
                case 5000:
                    this.mMemoryKiller.clearMemoryWits();
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
        getMcuSender().sendMessage(103, new byte[]{(byte) i});
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

    public void systemModeSwitch(int mode) {
        KswMcuSender sender = KswMcuSender.getSender();
        int i = 2;
        byte[] bArr = new byte[2];
        bArr[0] = 18;
        if (mode != 2) {
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
        if (SystemProperties.get(ZlinkMessage.ZLINK_CONNECT).equals("1")) {
            zlinkHandleCall();
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
}
