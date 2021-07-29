package com.wits.pms.mcu.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.mcu.custom.utils.CanLogUtils;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.utils.SystemProperties;
import com.wits.pms.utils.TouchControl;
import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

public class KswMcuListener extends KswMcuReceiver {
    public static final String TAG = "KswMcuListener";
    private int bootTime = 0;
    /* access modifiers changed from: private */
    public final Context mContext = PowerManagerAppService.serviceContext;
    private final Handler mHandler = new Handler(this.mContext.getMainLooper());
    private final KswMcuSender mMcuSender = CenterControlImpl.getMcuSender();
    private TouchControl mTouchControl;
    private String memoryPackage;
    private boolean openBluetooth;

    public static final class MediaType {
        public static final int SRC_ALL_APP = 13;
        public static final int SRC_AUX = 6;
        public static final int SRC_BT = 3;
        public static final int SRC_BT_MUSIC = 4;
        public static final int SRC_CAR = 0;
        public static final int SRC_CAR_FM = 14;
        public static final int SRC_DTV = 9;
        public static final int SRC_DVD = 8;
        public static final int SRC_DVD_YUV = 12;
        public static final int SRC_DVR = 5;
        public static final int SRC_F_CAM = 11;
        public static final int SRC_MUSIC = 1;
        public static final int SRC_PHONELINK = 7;
        public static final int SRC_RADIO = 10;
        public static final int SRC_VIDEO = 2;
    }

    public KswMcuListener() {
        UpdateHelper.init(this.mMcuSender);
        this.mTouchControl = new TouchControl(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("can_bus_switch"), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                boolean z = true;
                if (Settings.System.getInt(KswMcuListener.this.mContext.getContentResolver(), "can_bus_switch", 0) != 1) {
                    z = false;
                }
                CanLogUtils.canLogSwitch(z);
            }
        });
        Settings.System.putInt(this.mContext.getContentResolver(), "can_bus_switch", 0);
    }

    public void onMcuMessage(byte[] pack) {
        onMcuMessage(KswMessage.parse(pack));
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v1, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v9, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v18, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v20, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v28, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v31, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v34, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v6, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v42, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v45, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v48, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v52, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v54, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v55, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v56, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v59, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v61, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v47, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v48, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v9, types: [byte] */
    /* JADX WARNING: type inference failed for: r11v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v14, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v26, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v30, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v36, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v14, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v41, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v46, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v20, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v102, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v107, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v110, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v52, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v109, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v116, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v113, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v120, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v119, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v120, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v150, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v155, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v157, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v158, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v159, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v160, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v161, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v162, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v166, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v138, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v31, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v33, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v72, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v144, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v146, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v148, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v151, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v152, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v153, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v38, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r12v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v26, types: [byte] */
    /* JADX WARNING: type inference failed for: r11v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r11v6, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v29, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v73, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v164, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v165, types: [byte] */
    /* JADX WARNING: type inference failed for: r15v2, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v167, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v168, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v169, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v40, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v80, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v82, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v9, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v30, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v32, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v85, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v18, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v22, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v36, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v38, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v86, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v29, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v89, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v90, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r12v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v93, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v95, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v97, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v102, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v104, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v105, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v106, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v42, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r11v8, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v45, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v109, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v111, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v112, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v47, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r11v11, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v50, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v115, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v117, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v118, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v121, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v193, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v196, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v58, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v61, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v64, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v128, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v209, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v138, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v75, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v205, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v217, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v224, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v227, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v230, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v232, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v82, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v220, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v246, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v251, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v254, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v257, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v260, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v263, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v268, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v270, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v154, types: [byte] */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMcuMessage(com.wits.pms.mcu.custom.KswMessage r25) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            java.lang.String r0 = "KswMcuListener"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = ""
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.i(r0, r3)
            if (r2 != 0) goto L_0x001d
            return
        L_0x001d:
            com.wits.pms.mcu.custom.KswMcuLogic.handleMsg(r25)
            com.wits.pms.mcu.custom.utils.UpdateHelper r0 = com.wits.pms.mcu.custom.utils.UpdateHelper.getInstance()
            r0.handleMessage(r2)
            boolean r0 = com.wits.pms.mcu.custom.utils.CanLogUtils.isCating()
            if (r0 == 0) goto L_0x0030
            com.wits.pms.mcu.custom.utils.CanLogUtils.canLog(r25)
        L_0x0030:
            com.wits.pms.statuscontrol.SystemStatus r3 = com.wits.pms.core.SystemStatusControl.getStatus()
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.statuscontrol.McuStatus r4 = r0.getMcuStatus()
            byte[] r5 = r25.getData()
            int r0 = r25.getCmdType()
            r6 = 21
            r8 = 6
            r9 = 5
            r10 = 0
            r11 = 1
            if (r0 == r6) goto L_0x0a82
            r6 = 23
            r12 = 8
            if (r0 == r6) goto L_0x0a73
            r6 = 161(0xa1, float:2.26E-43)
            r13 = 7
            r15 = 16
            r7 = 4
            r14 = 255(0xff, float:3.57E-43)
            if (r0 == r6) goto L_0x0647
            r6 = 224(0xe0, float:3.14E-43)
            if (r0 == r6) goto L_0x062e
            switch(r0) {
                case 16: goto L_0x060d;
                case 17: goto L_0x05e7;
                case 18: goto L_0x05d6;
                default: goto L_0x0063;
            }
        L_0x0063:
            switch(r0) {
                case 27: goto L_0x0595;
                case 28: goto L_0x0593;
                case 29: goto L_0x055d;
                case 30: goto L_0x051d;
                case 31: goto L_0x0340;
                case 32: goto L_0x02f2;
                case 33: goto L_0x025b;
                case 34: goto L_0x01ff;
                case 35: goto L_0x01e3;
                case 36: goto L_0x018b;
                case 37: goto L_0x0142;
                case 38: goto L_0x0068;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x0ad3
        L_0x0068:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            byte r0 = r5[r10]
            switch(r0) {
                case 1: goto L_0x010c;
                case 2: goto L_0x00fe;
                case 3: goto L_0x00f0;
                case 4: goto L_0x00e2;
                case 5: goto L_0x00d4;
                case 6: goto L_0x0076;
                default: goto L_0x0074;
            }
        L_0x0074:
            goto L_0x011a
        L_0x0076:
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r0 = r4.mediaPlayStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            com.wits.pms.statuscontrol.McuStatus$MediaData$MODE r0 = r0.mode
            byte r6 = r5[r11]
            if (r6 != r11) goto L_0x0087
            r6 = r11
            goto L_0x0088
        L_0x0087:
            r6 = r10
        L_0x0088:
            r0.ASL = r6
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r0 = r4.mediaPlayStatus
            byte r6 = r5[r11]
            if (r6 != r11) goto L_0x0092
            r6 = r11
            goto L_0x0093
        L_0x0092:
            r6 = r10
        L_0x0093:
            r0.ALS = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "ASL "
            r6.append(r7)
            byte r7 = r5[r11]
            if (r7 != r11) goto L_0x00a8
            java.lang.String r7 = "ON"
            goto L_0x00aa
        L_0x00a8:
            java.lang.String r7 = "OFF"
        L_0x00aa:
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.changeVol = r6
            java.lang.String r0 = "mcuMediaJson"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r7 = r4.mediaData
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            java.lang.String r0 = "mcuMediaPlayStatus"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r7 = r4.mediaPlayStatus
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            goto L_0x011a
        L_0x00d4:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.BAL = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "BAL"
            r0.changeVol = r6
            goto L_0x011a
        L_0x00e2:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.FAD = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "FAD"
            r0.changeVol = r6
            goto L_0x011a
        L_0x00f0:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.TRE = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "TRE"
            r0.changeVol = r6
            goto L_0x011a
        L_0x00fe:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.MID = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "MID"
            r0.changeVol = r6
            goto L_0x011a
        L_0x010c:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.BAS = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "BAS"
            r0.changeVol = r6
        L_0x011a:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.volume = r6
            java.lang.String r0 = "mcuEqData"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$EqData r7 = r4.eqData
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            com.wits.pms.mcu.custom.KswMcuSender r0 = com.wits.pms.mcu.custom.KswMcuSender.getSender()
            r6 = 123(0x7b, float:1.72E-43)
            byte[] r7 = new byte[r11]
            r7[r10] = r10
            r8 = 3500(0xdac, double:1.729E-320)
            r0.sendMessageDelay(r6, r7, r8)
            goto L_0x0ad3
        L_0x0142:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            int r0 = r5.length
            int r0 = r0 - r11
            byte[] r0 = new byte[r0]
            int r6 = r0.length
            java.lang.System.arraycopy(r5, r11, r0, r10, r6)
            java.lang.String r6 = new java.lang.String
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.US_ASCII
            r6.<init>(r0, r8)
            byte r8 = r5[r10]
            if (r8 == r11) goto L_0x0164
            if (r8 == r7) goto L_0x015f
            goto L_0x0169
        L_0x015f:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r7 = r4.bluetoothStatus
            r7.settingsInfo = r6
            goto L_0x0169
        L_0x0164:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r7 = r4.bluetoothStatus
            r7.name = r6
        L_0x0169:
            java.lang.String r7 = "mcuBluetoothStatus"
            com.google.gson.Gson r8 = new com.google.gson.Gson
            r8.<init>()
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r9 = r4.bluetoothStatus
            java.lang.String r8 = r8.toJson((java.lang.Object) r9)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r7, r8)
            java.lang.String r7 = "mcuMediaJson"
            com.google.gson.Gson r8 = new com.google.gson.Gson
            r8.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r9 = r4.mediaData
            java.lang.String r8 = r8.toJson((java.lang.Object) r9)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r7, r8)
            goto L_0x0ad3
        L_0x018b:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 <= r11) goto L_0x01a3
            r6 = r11
            goto L_0x01a4
        L_0x01a3:
            r6 = r10
        L_0x01a4:
            r0.isCalling = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & 224(0xe0, float:3.14E-43)
            int r6 = r6 >> r9
            r0.callSignal = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & r12
            if (r6 <= r11) goto L_0x01b7
            goto L_0x01b8
        L_0x01b7:
            r11 = r10
        L_0x01b8:
            r0.playingMusic = r11
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & r13
            r0.batteryStatus = r6
            java.lang.String r0 = "mcuBluetoothStatus"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r7 = r4.bluetoothStatus
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            java.lang.String r0 = "mcuMediaJson"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r7 = r4.mediaData
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            goto L_0x0ad3
        L_0x01e3:
            byte r0 = r5[r10]     // Catch:{ Exception -> 0x01fc }
            r0 = r0 & 127(0x7f, float:1.78E-43)
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x01fc }
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 <= 0) goto L_0x01ef
            r10 = r11
        L_0x01ef:
            r6 = r10
            java.lang.String r7 = "mcu_volume_level"
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusInt(r7, r0)     // Catch:{ Exception -> 0x01fc }
            java.lang.String r7 = "mcu_volume_mute"
            com.wits.pms.statuscontrol.PowerManagerApp.setBooleanStatus(r7, r6)     // Catch:{ Exception -> 0x01fc }
            goto L_0x0ad3
        L_0x01fc:
            r0 = move-exception
            goto L_0x0ad3
        L_0x01ff:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r0 = r4.discStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            java.lang.String r0 = ""
            byte r6 = r5[r10]
            switch(r6) {
                case 1: goto L_0x021a;
                case 2: goto L_0x0217;
                case 3: goto L_0x0214;
                case 4: goto L_0x0211;
                case 5: goto L_0x020e;
                default: goto L_0x020d;
            }
        L_0x020d:
            goto L_0x021d
        L_0x020e:
            java.lang.String r0 = "WAIT"
            goto L_0x021d
        L_0x0211:
            java.lang.String r0 = "DISC FULL"
            goto L_0x021d
        L_0x0214:
            java.lang.String r0 = "DISC IN"
            goto L_0x021d
        L_0x0217:
            java.lang.String r0 = "EJECT"
            goto L_0x021d
        L_0x021a:
            java.lang.String r0 = "LOAD"
        L_0x021d:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            boolean[] r7 = new boolean[r8]
            r6.discInsert = r7
            r6 = r10
        L_0x0224:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r7 = r4.discStatus
            boolean[] r7 = r7.discInsert
            int r7 = r7.length
            if (r6 >= r7) goto L_0x023f
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r7 = r4.discStatus
            boolean[] r7 = r7.discInsert
            byte r8 = r5[r11]
            r8 = r8 & r14
            int r9 = r11 << r6
            r8 = r8 & r9
            if (r8 < r11) goto L_0x0239
            r8 = r11
            goto L_0x023a
        L_0x0239:
            r8 = r10
        L_0x023a:
            r7[r6] = r8
            int r6 = r6 + 1
            goto L_0x0224
        L_0x023f:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            r6.status = r0
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            r7 = 2
            byte r7 = r5[r7]
            r6.range = r7
            java.lang.String r6 = "mcuDiscStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r8 = r4.discStatus
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)
            return
        L_0x025b:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r0 = r4.mediaPlayStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            byte r6 = r5[r10]
            r0.type = r6
            java.lang.String r0 = "mcuMediaJson"
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r7 = r4.mediaData
            java.lang.String r6 = r6.toJson((java.lang.Object) r7)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r0, r6)
            java.lang.String r0 = ""
            byte r6 = r5[r11]
            switch(r6) {
                case 0: goto L_0x02aa;
                case 1: goto L_0x02a7;
                case 6: goto L_0x0293;
                case 7: goto L_0x0290;
                case 8: goto L_0x028d;
                case 14: goto L_0x028a;
                case 15: goto L_0x0287;
                default: goto L_0x0286;
            }
        L_0x0286:
            goto L_0x02ad
        L_0x0287:
            java.lang.String r0 = "UNKNOWN"
            goto L_0x02ad
        L_0x028a:
            java.lang.String r0 = "AUDIO OFF"
            goto L_0x02ad
        L_0x028d:
            java.lang.String r0 = "NO MUSIC"
            goto L_0x02ad
        L_0x0290:
            java.lang.String r0 = "ERROR"
            goto L_0x02ad
        L_0x0293:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            int r6 = r6.type
            if (r6 != r15) goto L_0x029c
            java.lang.String r0 = "READING DISC"
            goto L_0x02ad
        L_0x029c:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            int r6 = r6.type
            r7 = 17
            if (r6 != r7) goto L_0x02ad
            java.lang.String r0 = "READING FILE"
            goto L_0x02ad
        L_0x02a7:
            java.lang.String r0 = "PAUSE"
            goto L_0x02ad
        L_0x02aa:
            java.lang.String r0 = "PLAY"
        L_0x02ad:
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            r6.status = r0
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            r7 = 2
            byte r8 = r5[r7]
            r8 = r8 & r15
            if (r8 <= r11) goto L_0x02bb
            r8 = r11
            goto L_0x02bc
        L_0x02bb:
            r8 = r10
        L_0x02bc:
            r6.RPT = r8
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r8 = r5[r7]
            r8 = r8 & r12
            if (r8 <= r11) goto L_0x02c7
            r8 = r11
            goto L_0x02c8
        L_0x02c7:
            r8 = r10
        L_0x02c8:
            r6.ST = r8
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r8 = r5[r7]
            r8 = r8 & r7
            if (r8 <= r11) goto L_0x02d3
            r8 = r11
            goto L_0x02d4
        L_0x02d3:
            r8 = r10
        L_0x02d4:
            r6.SCAN = r8
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r7 = r5[r7]
            r7 = r7 & r11
            if (r7 < r11) goto L_0x02df
            r10 = r11
        L_0x02df:
            r6.RAND = r10
            java.lang.String r6 = "mcuMediaPlayStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r8 = r4.mediaPlayStatus
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)
            return
        L_0x02f2:
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r0 = r4.mediaStringInfo
            if (r0 != 0) goto L_0x02f7
            return
        L_0x02f7:
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r0 = r4.mediaStringInfo
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            java.lang.String r0 = r1.autoGetString(r5)
            if (r0 != 0) goto L_0x0305
            return
        L_0x0305:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            com.wits.pms.statuscontrol.McuStatus$MediaData$BaseMediaInfo r6 = r6.getCurrentMediaInfo()
            if (r6 != 0) goto L_0x030e
            return
        L_0x030e:
            byte r7 = r5[r10]
            switch(r7) {
                case 1: goto L_0x031d;
                case 2: goto L_0x031a;
                case 3: goto L_0x0317;
                case 4: goto L_0x0314;
                default: goto L_0x0313;
            }
        L_0x0313:
            goto L_0x032f
        L_0x0314:
            r6.folderName = r0
            goto L_0x032f
        L_0x0317:
            r6.album = r0
            goto L_0x032f
        L_0x031a:
            r6.artist = r0
            goto L_0x032f
        L_0x031d:
            java.lang.String r7 = r6.name
            if (r7 == 0) goto L_0x032c
            java.lang.String r7 = r6.name
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L_0x032c
            r6.reset()
        L_0x032c:
            r6.name = r0
        L_0x032f:
            java.lang.String r7 = "mcuMediaJson"
            com.google.gson.Gson r8 = new com.google.gson.Gson
            r8.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r9 = r4.mediaData
            java.lang.String r8 = r8.toJson((java.lang.Object) r9)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r7, r8)
            return
        L_0x0340:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            int r6 = r0.times     // Catch:{ Exception -> 0x051b }
            int r6 = r6 + r11
            r0.times = r6     // Catch:{ Exception -> 0x051b }
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x051b }
            r0.type = r6     // Catch:{ Exception -> 0x051b }
            byte r6 = r5[r11]     // Catch:{ Exception -> 0x051b }
            r13 = -1
            if (r6 != r14) goto L_0x0352
            r5[r11] = r13     // Catch:{ Exception -> 0x051b }
        L_0x0352:
            r6 = 2
            byte r15 = r5[r6]     // Catch:{ Exception -> 0x051b }
            if (r15 != r14) goto L_0x0359
            r5[r6] = r13     // Catch:{ Exception -> 0x051b }
        L_0x0359:
            byte r6 = r5[r9]     // Catch:{ Exception -> 0x051b }
            if (r6 != r14) goto L_0x035f
            r5[r9] = r13     // Catch:{ Exception -> 0x051b }
        L_0x035f:
            byte r6 = r5[r8]     // Catch:{ Exception -> 0x051b }
            if (r6 != r14) goto L_0x0365
            r5[r8] = r13     // Catch:{ Exception -> 0x051b }
        L_0x0365:
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x051b }
            if (r6 == r11) goto L_0x045b
            r13 = 21
            if (r6 == r13) goto L_0x0436
            r13 = 64
            if (r6 == r13) goto L_0x03f7
            switch(r6) {
                case 16: goto L_0x03d2;
                case 17: goto L_0x03a4;
                case 18: goto L_0x0376;
                default: goto L_0x0374;
            }     // Catch:{ Exception -> 0x051b }
        L_0x0374:
            goto L_0x050a
        L_0x0376:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData$MP3 r6 = r6.mp3     // Catch:{ Exception -> 0x051b }
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x051b }
            r11 = 2
            byte r11 = r5[r11]     // Catch:{ Exception -> 0x051b }
            int r11 = r11 << r12
            int r10 = r10 + r11
            r6.folderNumber = r10     // Catch:{ Exception -> 0x051b }
            r10 = 3
            byte r10 = r5[r10]     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            int r7 = r7 << r12
            int r10 = r10 + r7
            r6.fileNumber = r10     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r6.min = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x051b }
            r6.sec = r7     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.min     // Catch:{ Exception -> 0x051b }
            r7.min = r8     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.sec     // Catch:{ Exception -> 0x051b }
            r7.sec = r8     // Catch:{ Exception -> 0x051b }
            r0.mp3 = r6     // Catch:{ Exception -> 0x051b }
            goto L_0x050a
        L_0x03a4:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Usb r6 = r6.usb     // Catch:{ Exception -> 0x051b }
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x051b }
            r11 = 2
            byte r11 = r5[r11]     // Catch:{ Exception -> 0x051b }
            int r11 = r11 << r12
            int r10 = r10 + r11
            r6.folderNumber = r10     // Catch:{ Exception -> 0x051b }
            r10 = 3
            byte r10 = r5[r10]     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            int r7 = r7 << r12
            int r10 = r10 + r7
            r6.fileNumber = r10     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r6.min = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x051b }
            r6.sec = r7     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.min     // Catch:{ Exception -> 0x051b }
            r7.min = r8     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.sec     // Catch:{ Exception -> 0x051b }
            r7.sec = r8     // Catch:{ Exception -> 0x051b }
            r0.usb = r6     // Catch:{ Exception -> 0x051b }
            goto L_0x050a
        L_0x03d2:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Disc r6 = r6.disc     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r11]     // Catch:{ Exception -> 0x051b }
            r6.number = r7     // Catch:{ Exception -> 0x051b }
            r7 = 2
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            r6.track = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r6.min = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x051b }
            r6.sec = r7     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.min     // Catch:{ Exception -> 0x051b }
            r7.min = r8     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x051b }
            int r8 = r6.sec     // Catch:{ Exception -> 0x051b }
            r7.sec = r8     // Catch:{ Exception -> 0x051b }
            r0.disc = r6     // Catch:{ Exception -> 0x051b }
            goto L_0x050a
        L_0x03f7:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData$MODE r6 = r6.mode     // Catch:{ Exception -> 0x051b }
            byte r12 = r5[r11]     // Catch:{ Exception -> 0x051b }
            if (r12 != r11) goto L_0x0401
            r12 = r11
            goto L_0x0402
        L_0x0401:
            r12 = r10
        L_0x0402:
            r6.ASL = r12     // Catch:{ Exception -> 0x051b }
            r12 = 2
            byte r12 = r5[r12]     // Catch:{ Exception -> 0x051b }
            if (r12 != r11) goto L_0x040b
            r12 = r11
            goto L_0x040c
        L_0x040b:
            r12 = r10
        L_0x040c:
            r6.ST = r12     // Catch:{ Exception -> 0x051b }
            r12 = 3
            byte r12 = r5[r12]     // Catch:{ Exception -> 0x051b }
            if (r12 != r11) goto L_0x0415
            r12 = r11
            goto L_0x0416
        L_0x0415:
            r12 = r10
        L_0x0416:
            r6.RAND = r12     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            if (r7 != r11) goto L_0x041e
            r7 = r11
            goto L_0x041f
        L_0x041e:
            r7 = r10
        L_0x041f:
            r6.RPT = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x051b }
            if (r7 != r11) goto L_0x0427
            r7 = r11
            goto L_0x0428
        L_0x0427:
            r7 = r10
        L_0x0428:
            r6.PAUSE = r7     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x051b }
            if (r7 != r11) goto L_0x0430
            r10 = r11
        L_0x0430:
            r6.SCAN = r10     // Catch:{ Exception -> 0x051b }
            r0.mode = r6     // Catch:{ Exception -> 0x051b }
            goto L_0x050a
        L_0x0436:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x051b }
            int r7 = r6.times     // Catch:{ Exception -> 0x051b }
            int r7 = r7 + r11
            r6.times = r7     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r6.min = r7     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x051b }
            r6.sec = r7     // Catch:{ Exception -> 0x051b }
            java.lang.String r6 = "mcuBluetoothStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson     // Catch:{ Exception -> 0x051b }
            r7.<init>()     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r8 = r4.bluetoothStatus     // Catch:{ Exception -> 0x051b }
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)     // Catch:{ Exception -> 0x051b }
            goto L_0x050a
        L_0x045b:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Fm r6 = r6.fm     // Catch:{ Exception -> 0x051b }
            byte r8 = r5[r11]     // Catch:{ Exception -> 0x051b }
            switch(r8) {
                case 0: goto L_0x04af;
                case 1: goto L_0x04af;
                case 2: goto L_0x04af;
                case 3: goto L_0x04af;
                default: goto L_0x0464;
            }     // Catch:{ Exception -> 0x051b }
        L_0x0464:
            switch(r8) {
                case 16: goto L_0x0469;
                case 17: goto L_0x0469;
                case 18: goto L_0x0469;
                case 19: goto L_0x0469;
                default: goto L_0x0467;
            }     // Catch:{ Exception -> 0x051b }
        L_0x0467:
            goto L_0x04f6
        L_0x0469:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x051b }
            r8.<init>()     // Catch:{ Exception -> 0x051b }
            java.lang.String r9 = "AM"
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x051b }
            r10 = 16
            if (r9 != r10) goto L_0x047c
            java.lang.String r9 = ""
            goto L_0x0483
        L_0x047c:
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x051b }
            int r9 = r9 - r10
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x051b }
        L_0x0483:
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x051b }
            r6.name = r8     // Catch:{ Exception -> 0x051b }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x051b }
            r8.<init>()     // Catch:{ Exception -> 0x051b }
            r9 = 2
            byte r10 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r9 = r10 & 255(0xff, float:3.57E-43)
            int r9 = r9 << r12
            r10 = 3
            byte r10 = r5[r10]     // Catch:{ Exception -> 0x051b }
            r10 = r10 & r14
            int r9 = r9 + r10
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r9 = "Khz"
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x051b }
            r6.freq = r8     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            r6.preFreq = r7     // Catch:{ Exception -> 0x051b }
            goto L_0x04f6
        L_0x04af:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x051b }
            r8.<init>()     // Catch:{ Exception -> 0x051b }
            java.lang.String r9 = "FM"
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x051b }
            if (r9 != 0) goto L_0x04c0
            java.lang.String r9 = ""
            goto L_0x04c6
        L_0x04c0:
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x051b }
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)     // Catch:{ Exception -> 0x051b }
        L_0x04c6:
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x051b }
            r6.name = r8     // Catch:{ Exception -> 0x051b }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x051b }
            r8.<init>()     // Catch:{ Exception -> 0x051b }
            r9 = 2
            byte r10 = r5[r9]     // Catch:{ Exception -> 0x051b }
            r9 = r10 & 255(0xff, float:3.57E-43)
            int r9 = r9 << r12
            r10 = 3
            byte r10 = r5[r10]     // Catch:{ Exception -> 0x051b }
            r10 = r10 & r14
            int r9 = r9 + r10
            float r9 = (float) r9     // Catch:{ Exception -> 0x051b }
            r10 = 1120403456(0x42c80000, float:100.0)
            float r9 = r9 / r10
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r9 = "Mhz"
            r8.append(r9)     // Catch:{ Exception -> 0x051b }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x051b }
            r6.freq = r8     // Catch:{ Exception -> 0x051b }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            r6.preFreq = r7     // Catch:{ Exception -> 0x051b }
        L_0x04f6:
            byte r7 = r5[r11]     // Catch:{ Exception -> 0x051b }
            if (r7 != r13) goto L_0x04fe
            java.lang.String r7 = "-"
            r6.name = r7     // Catch:{ Exception -> 0x051b }
        L_0x04fe:
            r7 = 2
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x051b }
            if (r7 != r13) goto L_0x0507
            java.lang.String r7 = "-"
            r6.freq = r7     // Catch:{ Exception -> 0x051b }
        L_0x0507:
            r0.fm = r6     // Catch:{ Exception -> 0x051b }
        L_0x050a:
            java.lang.String r6 = "mcuMediaJson"
            com.google.gson.Gson r7 = new com.google.gson.Gson     // Catch:{ Exception -> 0x051b }
            r7.<init>()     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.McuStatus$MediaData r8 = r4.mediaData     // Catch:{ Exception -> 0x051b }
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x051b }
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)     // Catch:{ Exception -> 0x051b }
            goto L_0x051c
        L_0x051b:
            r0 = move-exception
        L_0x051c:
            return
        L_0x051d:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r6 = "com.wits.pms.MCU_CHECK_CAR"
            r0.<init>((java.lang.String) r6)
            r6 = 16777248(0x1000020, float:2.3509977E-38)
            r0.addFlags(r6)
            java.lang.String r6 = "checkCanStatus"
            byte[] r8 = new byte[r8]
            byte r12 = r5[r10]
            r8[r10] = r12
            byte r10 = r5[r11]
            r8[r11] = r10
            r10 = 2
            byte r11 = r5[r10]
            r8[r10] = r11
            r10 = 3
            byte r11 = r5[r10]
            r8[r10] = r11
            byte r10 = r5[r7]
            r8[r7] = r10
            byte r7 = r5[r9]
            r8[r9] = r7
            r0.putExtra((java.lang.String) r6, (byte[]) r8)
            android.content.Context r6 = r1.mContext
            android.content.Context r7 = r1.mContext
            android.content.pm.ApplicationInfo r7 = r7.getApplicationInfo()
            int r7 = r7.uid
            android.os.UserHandle r7 = android.os.UserHandle.getUserHandleForUid(r7)
            r6.sendBroadcastAsUser(r0, r7)
            return
        L_0x055d:
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r10]
            if (r6 != r11) goto L_0x0565
            r6 = r11
            goto L_0x0566
        L_0x0565:
            r6 = r10
        L_0x0566:
            r0.highChassisSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r11]
            r0.airMaticStatus = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            r6 = 2
            byte r6 = r5[r6]
            if (r6 != r11) goto L_0x0577
            r6 = r11
            goto L_0x0578
        L_0x0577:
            r6 = r10
        L_0x0578:
            r0.auxiliaryRadar = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            r6 = 3
            byte r6 = r5[r6]
            r0.light1 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r7]
            r0.light2 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r9]
            if (r6 != r11) goto L_0x058f
            r10 = r11
        L_0x058f:
            r0.airBagSystem = r10
            goto L_0x0ad3
        L_0x0593:
            goto L_0x0ad3
        L_0x0595:
            byte r0 = r5[r10]
            r0 = r0 & r14
            int r0 = r0 << r12
            byte r6 = r5[r11]
            r6 = r6 & r14
            int r0 = r0 + r6
            r6 = 2
            byte r8 = r5[r6]
            r6 = r8 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r12
            r8 = 3
            byte r8 = r5[r8]
            r8 = r8 & r14
            int r6 = r6 + r8
            byte r7 = r5[r7]
            if (r7 != r11) goto L_0x05ae
            r8 = r11
            goto L_0x05af
        L_0x05ae:
            r8 = r10
        L_0x05af:
            if (r8 == 0) goto L_0x05c0
            com.wits.pms.utils.TouchControl r9 = r1.mTouchControl
            boolean r9 = r9.isDown()
            if (r9 == 0) goto L_0x05bc
        L_0x05b9:
            r16 = 2
            goto L_0x05cc
        L_0x05bc:
            r16 = r10
            goto L_0x05cc
        L_0x05c0:
            com.wits.pms.utils.TouchControl r9 = r1.mTouchControl
            boolean r9 = r9.isDown()
            if (r9 == 0) goto L_0x05cb
            r16 = r11
            goto L_0x05cc
        L_0x05cb:
            goto L_0x05b9
        L_0x05cc:
            r9 = r16
            com.wits.pms.utils.TouchControl r10 = r1.mTouchControl
            int r14 = r14 - r6
            r10.opPointerEvent(r0, r14, r9)
            goto L_0x0ad3
        L_0x05d6:
            r0 = 40
            byte[] r0 = new byte[r0]
            int r6 = r0.length
            java.lang.System.arraycopy(r5, r10, r0, r10, r6)
            java.lang.String r6 = new java.lang.String
            r6.<init>(r0)
            r4.mcuVerison = r6
            goto L_0x0ad3
        L_0x05e7:
            byte r0 = r5[r10]
            if (r0 != r7) goto L_0x05f6
            byte r0 = r5[r11]
            if (r0 != r11) goto L_0x05f6
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.closeScreen(r10)
        L_0x05f6:
            byte r0 = r5[r10]
            r6 = 3
            if (r0 > r6) goto L_0x0602
            byte r0 = r5[r11]
            r3.setCcd(r0)
            goto L_0x0ad3
        L_0x0602:
            byte r0 = r5[r10]
            if (r0 != r9) goto L_0x0ad3
            byte r0 = r5[r11]
            r3.setAcc(r0)
            goto L_0x0ad3
        L_0x060d:
            byte r0 = r5[r10]
            if (r0 != r11) goto L_0x0613
            r10 = r11
        L_0x0613:
            r0 = r10
            if (r0 == 0) goto L_0x0620
            r3.setAcc(r11)
            r6 = 500(0x1f4, double:2.47E-321)
            com.wits.pms.mcu.custom.utils.AccLight.show(r6)
            goto L_0x0ad3
        L_0x0620:
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU
            r7.<init>()
            r8 = 1000(0x3e8, double:4.94E-321)
            r6.postDelayed(r7, r8)
            goto L_0x0ad3
        L_0x062e:
            byte r0 = r5[r10]
            if (r0 != r7) goto L_0x0ad3
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            if (r0 == 0) goto L_0x0643
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x0643
            android.content.Context r0 = r1.mContext
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.fix(r0)
        L_0x0643:
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.NEEDFIX = r11
            goto L_0x0ad3
        L_0x0647:
            byte r0 = r5[r10]
            r6 = 16
            if (r0 == r6) goto L_0x0a0d
            r6 = 18
            if (r0 == r6) goto L_0x0a02
            r6 = 28
            if (r0 == r6) goto L_0x095e
            r6 = 13
            switch(r0) {
                case 23: goto L_0x0802;
                case 24: goto L_0x07db;
                case 25: goto L_0x0709;
                case 26: goto L_0x065c;
                default: goto L_0x065a;
            }
        L_0x065a:
            goto L_0x0a0b
        L_0x065c:
            byte r0 = r5[r11]
            r6 = 2
            if (r0 != r6) goto L_0x068b
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.stopMedia()
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x068a }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x068a }
            java.lang.String r6 = "CarDisplay"
            int r0 = android.provider.Settings.System.getInt(r0, r6)     // Catch:{ SettingNotFoundException -> 0x068a }
            if (r0 != 0) goto L_0x0689
            android.content.Intent r0 = new android.content.Intent     // Catch:{ SettingNotFoundException -> 0x068a }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x068a }
            java.lang.Class<com.wits.pms.ClockActivity> r7 = com.wits.pms.ClockActivity.class
            r0.<init>((android.content.Context) r6, (java.lang.Class<?>) r7)     // Catch:{ SettingNotFoundException -> 0x068a }
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r0.setFlags(r6)     // Catch:{ SettingNotFoundException -> 0x068a }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x068a }
            r6.startActivity(r0)     // Catch:{ SettingNotFoundException -> 0x068a }
        L_0x0689:
            goto L_0x068b
        L_0x068a:
            r0 = move-exception
        L_0x068b:
            byte r0 = r5[r11]
            r4.systemMode = r0
            int r0 = r4.systemMode
            if (r0 != r11) goto L_0x0a0b
            r6 = 0
            r7 = 0
            r12 = r10
            java.lang.String r0 = "callStatus"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r0)     // Catch:{ RemoteException -> 0x06a8 }
            r12 = r0
            if (r12 != r13) goto L_0x06a1
            r10 = r11
        L_0x06a1:
            r6 = r10
            boolean r0 = com.wits.pms.statuscontrol.BtPhoneStatus.isCalling(r12)     // Catch:{ RemoteException -> 0x06a8 }
            r7 = r0
            goto L_0x06a9
        L_0x06a8:
            r0 = move-exception
        L_0x06a9:
            int r0 = r3.lastMode
            if (r0 == r8) goto L_0x0707
            int r0 = r3.lastMode
            r8 = 9
            if (r0 == r8) goto L_0x0707
            int r0 = r3.lastMode
            if (r0 == r9) goto L_0x0707
            int r0 = r3.lastMode
            r8 = 11
            if (r0 == r8) goto L_0x0707
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r8 = "1"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L_0x0707
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r8 = "2"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L_0x0707
            java.lang.String r0 = "vendor.wits.autobox.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r8 = "1"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L_0x0707
            java.lang.String r0 = "vendor.wits.autobox.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r8 = "2"
            boolean r0 = r0.equals(r8)
            if (r0 != 0) goto L_0x0707
            if (r7 != 0) goto L_0x0707
            if (r6 != 0) goto L_0x0707
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r8 = "-----lkt temp home1"
            r0.println(r8)
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.home()
        L_0x0707:
            goto L_0x0a0b
        L_0x0709:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r10 = r5[r11]
            r10 = r10 & r14
            int r10 = r10 << r12
            r15 = 2
            byte r11 = r5[r15]
            r11 = r11 & r14
            int r10 = r10 + r11
            r0.mileage = r10
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r10 = 3
            byte r10 = r5[r10]
            r10 = r10 & r14
            int r10 = r10 << r12
            byte r7 = r5[r7]
            r7 = r7 & r14
            int r10 = r10 + r7
            float r7 = (float) r10
            r10 = 1092616192(0x41200000, float:10.0)
            float r7 = r7 / r10
            r0.oilWear = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r7 = r5[r9]
            r7 = r7 & r14
            int r7 = r7 << r12
            byte r8 = r5[r8]
            r8 = r8 & r14
            int r7 = r7 + r8
            float r7 = (float) r7
            r8 = 1092616192(0x41200000, float:10.0)
            float r7 = r7 / r8
            r0.averSpeed = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r7 = r5[r13]
            r7 = r7 & r14
            int r7 = r7 << r12
            byte r8 = r5[r12]
            r8 = r8 & r14
            int r7 = r7 + r8
            r0.speed = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r7 = 9
            byte r7 = r5[r7]
            r7 = r7 & r14
            int r7 = r7 << r12
            r8 = 10
            byte r8 = r5[r8]
            r8 = r8 & r14
            int r7 = r7 + r8
            r0.engineTurnS = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r7 = 11
            byte r7 = r5[r7]
            r7 = r7 & r14
            int r7 = r7 << r12
            r8 = 12
            byte r8 = r5[r8]
            r8 = r8 & r14
            int r7 = r7 + r8
            r0.oilSum = r7
            byte r0 = r5[r6]
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 <= 0) goto L_0x0788
            byte r0 = r5[r6]
            r0 = r0 & r14
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & r14
            int r0 = r0 + r6
            int r0 = ~r0
            r6 = 1
            int r0 = r0 + r6
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r7 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r0
            double r7 = (double) r7
            r9 = -4631501856787818086(0xbfb999999999999a, double:-0.1)
            double r7 = r7 * r9
            float r7 = (float) r7
            r6.airTemperature = r7
            goto L_0x07a3
        L_0x0788:
            byte r0 = r5[r6]
            r0 = r0 & r14
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & r14
            int r0 = r0 + r6
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r7 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r0
            double r7 = (double) r7
            r9 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            double r7 = r7 * r9
            float r7 = (float) r7
            r6.airTemperature = r7
        L_0x07a3:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 15
            byte r6 = r5[r6]
            r6 = r6 & r12
            r0.distanceUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 15
            byte r6 = r5[r6]
            r7 = 2
            r6 = r6 & r7
            r0.oilUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            int r6 = r0.oilUnitType
            r7 = 15
            byte r7 = r5[r7]
            r7 = r7 & r14
            r8 = 1
            r7 = r7 & r8
            int r6 = r6 + r7
            r0.oilUnitType = r6
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.core.PowerManagerImpl r0 = r0.getPms()
            java.lang.String r6 = "mcuJson"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            java.lang.String r7 = r7.toJson((java.lang.Object) r4)
            r0.updateMcuJsonStatus(r6, r7)
            return
        L_0x07db:
            byte[] r0 = new byte[r13]
            int r6 = r0.length
            r11 = 1
            java.lang.System.arraycopy(r5, r11, r0, r10, r6)
            byte r6 = r0[r8]
            if (r6 == 0) goto L_0x07e7
            goto L_0x07e8
        L_0x07e7:
        L_0x07e8:
            android.content.Context r6 = r1.mContext
            byte r8 = r0[r10]
            r8 = r8 & r14
            byte r19 = r0[r11]
            r10 = 2
            byte r20 = r0[r10]
            r10 = 3
            byte r21 = r0[r10]
            byte r22 = r0[r7]
            byte r23 = r0[r9]
            r17 = r6
            r18 = r8
            com.wits.pms.utils.TimeSetting.setTime(r17, r18, r19, r20, r21, r22, r23)
            goto L_0x0a0b
        L_0x0802:
            r0 = 2
            byte r0 = r5[r0]
            r7 = 1
            if (r0 == r7) goto L_0x0809
            return
        L_0x0809:
            byte r0 = r5[r7]
            if (r0 == r6) goto L_0x0954
            r6 = 20
            if (r0 == r6) goto L_0x0947
            switch(r0) {
                case 3: goto L_0x093d;
                case 4: goto L_0x0933;
                case 5: goto L_0x0929;
                case 6: goto L_0x08ef;
                case 7: goto L_0x08b5;
                default: goto L_0x0814;
            }
        L_0x0814:
            switch(r0) {
                case 30: goto L_0x0867;
                case 31: goto L_0x0819;
                default: goto L_0x0817;
            }
        L_0x0817:
            goto L_0x095c
        L_0x0819:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0830
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x095c
        L_0x0830:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0849
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.rejectPhone()
            goto L_0x095c
        L_0x0849:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.utils.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x085e
            r0 = 112(0x70, float:1.57E-43)
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r13, r0)
            goto L_0x095c
        L_0x085e:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.handUpPhone()
            goto L_0x095c
        L_0x0867:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x087e
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x095c
        L_0x087e:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0897
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.acceptPhone()
            goto L_0x095c
        L_0x0897:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.utils.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x08ac
            r0 = 111(0x6f, float:1.56E-43)
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r13, r0)
            goto L_0x095c
        L_0x08ac:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.acceptPhone()
            goto L_0x095c
        L_0x08b5:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapLeft()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x08cd
            return
        L_0x08cd:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r6 = "AUTONAVI_STANDARD_BROADCAST_RECV"
            r0.setAction(r6)
            java.lang.String r6 = "KEY_TYPE"
            r7 = 10027(0x272b, float:1.4051E-41)
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_TYPE"
            r7 = 1
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_OPERA"
            r0.putExtra((java.lang.String) r6, (int) r7)
            android.content.Context r6 = r1.mContext
            r6.sendBroadcast(r0)
            goto L_0x095c
        L_0x08ef:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapRight()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x0907
            return
        L_0x0907:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r6 = "AUTONAVI_STANDARD_BROADCAST_RECV"
            r0.setAction(r6)
            java.lang.String r6 = "KEY_TYPE"
            r7 = 10027(0x272b, float:1.4051E-41)
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_TYPE"
            r7 = 1
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_OPERA"
            r0.putExtra((java.lang.String) r6, (int) r10)
            android.content.Context r6 = r1.mContext
            r6.sendBroadcast(r0)
            goto L_0x095c
        L_0x0929:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.enter()
            goto L_0x095c
        L_0x0933:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapDown()
            goto L_0x095c
        L_0x093d:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapUp()
            goto L_0x095c
        L_0x0947:
            android.content.Context r0 = r1.mContext
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = "start.txz.ksw"
            r6.<init>((java.lang.String) r7)
            r0.sendBroadcast(r6)
            goto L_0x095c
        L_0x0954:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.openSettings()
        L_0x095c:
            goto L_0x0a0b
        L_0x095e:
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r18 = 1
            byte r6 = r5[r18]
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 == 0) goto L_0x096b
            r6 = r18
            goto L_0x096c
        L_0x096b:
            r6 = r10
        L_0x096c:
            r0.isOpen = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r18]
            r6 = r6 & 64
            if (r6 == 0) goto L_0x0978
            r6 = 1
            goto L_0x0979
        L_0x0978:
            r6 = r10
        L_0x0979:
            r0.AC_Switch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 1
            byte r8 = r5[r6]
            r6 = r8 & 32
            if (r6 == 0) goto L_0x0986
            r6 = 1
            goto L_0x0987
        L_0x0986:
            r6 = r10
        L_0x0987:
            r0.loop = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 1
            byte r8 = r5[r6]
            r6 = 16
            r8 = r8 & r6
            if (r8 == 0) goto L_0x0995
            r14 = 2
            goto L_0x0999
        L_0x0995:
            com.wits.pms.statuscontrol.McuStatus$ACData r6 = r4.acData
            int r14 = r6.loop
        L_0x0999:
            r0.loop = r14
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r18 = 1
            byte r6 = r5[r18]
            r6 = r6 & r12
            if (r6 == 0) goto L_0x09a7
            r6 = r18
            goto L_0x09a8
        L_0x09a7:
            r6 = r10
        L_0x09a8:
            r0.frontMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r18]
            r8 = 2
            r6 = r6 & r8
            if (r6 == 0) goto L_0x09b5
            r6 = r18
            goto L_0x09b6
        L_0x09b5:
            r6 = r10
        L_0x09b6:
            r0.backMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r18]
            r6 = r6 & 1
            if (r6 == 0) goto L_0x09c2
            r6 = 1
            goto L_0x09c3
        L_0x09c2:
            r6 = r10
        L_0x09c3:
            r0.sync = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 2
            byte r6 = r5[r6]
            r0.mode = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 3
            byte r6 = r5[r6]
            r0.setLeftTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r7]
            r0.setRightTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r9]
            r6 = r6 & 32
            if (r6 == 0) goto L_0x09e5
            r6 = 1
            goto L_0x09e6
        L_0x09e5:
            r6 = r10
        L_0x09e6:
            r0.eco = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r9]
            r7 = 16
            r6 = r6 & r7
            if (r6 == 0) goto L_0x09f3
            r10 = 1
        L_0x09f3:
            r0.autoSwitch = r10
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r9]
            r6 = r6 & 15
            float r6 = (float) r6
            r7 = 1056964608(0x3f000000, float:0.5)
            float r6 = r6 * r7
            r0.speed = r6
            goto L_0x0a0b
        L_0x0a02:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 1
            byte r6 = r5[r6]
            r6 = r6 & r14
            r0.carDoor = r6
        L_0x0a0b:
            goto L_0x0ad3
        L_0x0a0d:
            r6 = r11
            byte r0 = r5[r6]
            r0 = r0 & r14
            r0 = r0 & r6
            r3.ill = r0
            byte r0 = r5[r6]
            r0 = r0 & r14
            r0 = r0 & r12
            r3.setEpb(r0)
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r7 = r5[r6]
            r6 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6 & r12
            if (r6 == 0) goto L_0x0a26
            r6 = 1
            goto L_0x0a27
        L_0x0a26:
            r6 = r10
        L_0x0a27:
            r0.handbrake = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 2
            byte r7 = r5[r6]
            r6 = r7 & 255(0xff, float:3.57E-43)
            r7 = 1
            r6 = r6 & r7
            if (r6 == 0) goto L_0x0a36
            r10 = 1
        L_0x0a36:
            r0.safetyBelt = r10
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 2
            byte r7 = r5[r6]
            r7 = r7 & r14
            r7 = r7 & r8
            r0.carGear = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r7 = r5[r6]
            r7 = r7 & r14
            r7 = r7 & r12
            r0.signalLeft = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r7 = r5[r6]
            r7 = r7 & r14
            r8 = 16
            r7 = r7 & r8
            r0.signalRight = r7
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r6]
            r6 = r6 & r14
            r6 = r6 & 32
            r0.signalDouble = r6
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.core.PowerManagerImpl r0 = r0.getPms()
            java.lang.String r6 = "mcuJson"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            java.lang.String r7 = r7.toJson((java.lang.Object) r4)
            r0.updateMcuJsonStatus(r6, r7)
            return
        L_0x0a73:
            int r0 = r5.length
            r6 = 9
            if (r0 < r6) goto L_0x0ad3
            com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.getSettings()
            byte r6 = r5[r12]
            r0.setUpProtocolForMcuListen(r6)
            goto L_0x0ad3
        L_0x0a82:
            java.lang.String r0 = "KswMcuListener"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "memory mode: "
            r6.append(r7)
            r7 = 1
            byte r11 = r5[r7]
            r6.append(r11)
            java.lang.String r6 = r6.toString()
            android.util.Log.i(r0, r6)
            byte r0 = r5[r10]
            if (r0 != r7) goto L_0x0ad3
            byte r0 = r5[r7]
            if (r0 == 0) goto L_0x0abd
            byte r0 = r5[r7]
            if (r0 == r8) goto L_0x0abd
            byte r0 = r5[r7]
            r6 = 9
            if (r0 == r6) goto L_0x0abd
            byte r0 = r5[r7]
            if (r0 == r9) goto L_0x0abd
            byte r0 = r5[r7]
            r6 = 11
            if (r0 == r6) goto L_0x0abd
            int r0 = r1.bootTime
            if (r0 != 0) goto L_0x0abd
            r10 = 1
        L_0x0abd:
            r0 = r10
            r6 = 1
            r1.bootTime = r6
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$U11nBajvlH9FvPJVYF0TydID9Vs r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$U11nBajvlH9FvPJVYF0TydID9Vs
            r7.<init>(r3, r5, r0)
            if (r0 == 0) goto L_0x0acd
            r8 = 5000(0x1388, double:2.4703E-320)
            goto L_0x0acf
        L_0x0acd:
            r8 = 0
        L_0x0acf:
            r6.postDelayed(r7, r8)
        L_0x0ad3:
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            r0.handle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.mcu.custom.KswMcuListener.onMcuMessage(com.wits.pms.mcu.custom.KswMessage):void");
    }

    public static /* synthetic */ void lambda$onMcuMessage$0(KswMcuListener kswMcuListener, SystemStatus systemStatus, byte[] data, boolean isDelay) {
        systemStatus.lastMode = data[1];
        int i = 0;
        switch (data[1]) {
            case 0:
            case 14:
                try {
                    if (PowerManagerApp.getSettingsInt("OEM_FM") != 1) {
                        return;
                    }
                } catch (RemoteException e) {
                }
                CenterControlImpl.getImpl().openCarFm();
                return;
            case 1:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.d(TAG, "memory music 8917 delay 02");
                        while (true) {
                            int i2 = i;
                            if (i2 < 20) {
                                Thread.sleep(500);
                                if (!SystemStatusControl.getStatus().topApp.equals("com.wits.ksw")) {
                                    i = i2 + 1;
                                }
                            }
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                String musicPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_APP_MUSIC_PKG");
                String string = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_APP_MUSIC_CLS");
                if (TextUtils.isEmpty(musicPkg)) {
                    CenterControlImpl.getImpl().openMusic(true);
                    return;
                } else if (musicPkg.equals("com.wits.ksw.media")) {
                    CenterControlImpl.getImpl().openMusic(true);
                    return;
                } else {
                    CenterControlImpl.getImpl().openApp(musicPkg);
                    return;
                }
            case 2:
                if (Build.DEVICE.contains("8937")) {
                    try {
                        Log.d(TAG, "memory music 8917 delay  02");
                        while (true) {
                            int i3 = i;
                            if (i3 < 20) {
                                Thread.sleep(500);
                                if (!SystemStatusControl.getStatus().topApp.equals("com.wits.ksw")) {
                                    i = i3 + 1;
                                }
                            }
                        }
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                    }
                }
                String videoPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_APP_VIDEO_PKG");
                String string2 = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_APP_VIDEO_CLS");
                if (TextUtils.isEmpty(videoPkg)) {
                    CenterControlImpl.getImpl().openVideo(true);
                    return;
                } else if (videoPkg.equals("com.wits.ksw.media")) {
                    CenterControlImpl.getImpl().openVideo(true);
                    return;
                } else {
                    CenterControlImpl.getImpl().openApp(videoPkg);
                    return;
                }
            case 3:
                if (!isDelay) {
                    CenterControlImpl.getImpl().openBluetooth(true);
                    return;
                } else if (SystemStatusControl.getStatus().topApp != null && !SystemStatusControl.getStatus().topApp.equals("com.wits.ksw.bt")) {
                    Log.d(TAG, "memory bt");
                    CenterControlImpl.getImpl().openBluetooth(true);
                    return;
                } else {
                    return;
                }
            case 5:
                try {
                    if (Settings.System.getInt(kswMcuListener.mContext.getContentResolver(), "DVR_Type") == 1) {
                        kswMcuListener.mMcuSender.sendMessage(103, new byte[]{5});
                        return;
                    }
                    return;
                } catch (Settings.SettingNotFoundException e4) {
                    return;
                }
            case 6:
                CenterControlImpl.getImpl().openAux(true);
                return;
            case 9:
                CenterControlImpl.getImpl().openDtv(true);
                return;
            case 11:
                CenterControlImpl.getImpl().openFrontCamera(true);
                return;
            default:
                return;
        }
    }

    @Nullable
    private String autoGetString(byte[] data) {
        try {
            byte[] checkStringBytes = new byte[((data.length - 1) * 20)];
            byte[] stringBytes = new byte[(data.length - 1)];
            for (int i = 0; i < 20; i++) {
                System.arraycopy(data, 1, checkStringBytes, (data.length - 1) * i, stringBytes.length);
            }
            System.arraycopy(data, 1, stringBytes, 0, stringBytes.length);
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(checkStringBytes);
            CharsetMatch charsetMatch = charsetDetector.detect();
            String charSet = charsetMatch.getName();
            UniversalDetector detector = new UniversalDetector((CharsetListener) null);
            detector.handleData(checkStringBytes, 0, checkStringBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            if (encoding != null && encoding.contains("GB")) {
                return new String(stringBytes, encoding);
            }
            if (!charSet.contains("windows") && !charSet.equals("UTF-16LE")) {
                if (charsetMatch.getConfidence() >= 10) {
                    if (charsetMatch.getName().equals("Big5") && charsetMatch.getConfidence() >= 10) {
                        if (new String(stringBytes, "Unicode").length() < new String(stringBytes, charSet).length()) {
                            charSet = "Unicode";
                        }
                    }
                    return new String(stringBytes, charSet);
                }
            }
            charSet = "Unicode";
            return new String(stringBytes, charSet);
        } catch (Exception e) {
            Log.e(TAG, "ksw media string info parse error", e);
            return null;
        }
    }

    private void openApp() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                KswMcuListener.lambda$openApp$1(KswMcuListener.this);
            }
        });
    }

    public static /* synthetic */ void lambda$openApp$1(KswMcuListener kswMcuListener) {
        Intent lastIntent = kswMcuListener.mContext.getPackageManager().getLaunchIntentForPackage(kswMcuListener.memoryPackage);
        if (lastIntent == null) {
            lastIntent = new Intent();
            String str = kswMcuListener.memoryPackage;
            char c = 65535;
            if (str.hashCode() == 1411998635 && str.equals("com.wits.video")) {
                c = 0;
            }
            if (c == 0) {
                lastIntent.setComponent(new ComponentName(kswMcuListener.memoryPackage, "com.wits.video.MainActivity"));
            }
        }
        if (lastIntent != null) {
            try {
                kswMcuListener.mContext.startActivity(lastIntent);
            } catch (Exception e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void shutdown() {
        SystemProperties.set("sys.powerctl", "shutdown");
    }
}
