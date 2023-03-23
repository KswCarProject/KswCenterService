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
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.SystemStatus;
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

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Object, byte[]] */
    /* JADX WARNING: type inference failed for: r7v1, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v18, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v22, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v25, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v25, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v28, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v31, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v34, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v37, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v44, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v47, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v50, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v53, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v56, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v59, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v62, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v65, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v68, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v69, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v70, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v75, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v51, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v52, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v134, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v138, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v19, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v145, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v23, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v152, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v26, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v157, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v29, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v162, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v32, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v166, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v168, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v170, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v34, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v138, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v176, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v142, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v180, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v148, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v149, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v168, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v173, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v175, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v176, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v177, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v178, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v179, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r0v180, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v184, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v196, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v198, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v49, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v202, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v204, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v205, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v207, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v208, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v210, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v10, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r12v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v16, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v221, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v222, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v223, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v224, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v226, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v57, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v58, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v19, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v2, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v61, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v28, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v32, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v34, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v62, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r10v39, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v66, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v68, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v24, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v19, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v71, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v74, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v77, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v82, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v83, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v85, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v87, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v28, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v27, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v29, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v90, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v93, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v95, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v30, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v33, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v35, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v98, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v101, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v103, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r7v106, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v250, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v253, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v112, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v115, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v118, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v121, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v266, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v137, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r9v33, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v223, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v274, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v281, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v284, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v287, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v289, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v44, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v238, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v303, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v308, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v311, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v314, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v317, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v320, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r6v325, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v327, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v154, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v259, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v339, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v41, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r13v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r15v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r15v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r15v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v43, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v10, types: [byte] */
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
            r9 = 5
            r10 = 0
            r11 = 1
            if (r0 == r6) goto L_0x0b66
            r6 = 23
            r12 = 8
            if (r0 == r6) goto L_0x0b56
            r6 = 161(0xa1, float:2.26E-43)
            r14 = 3
            r15 = 16
            r7 = 4
            r8 = 2
            r13 = 255(0xff, float:3.57E-43)
            if (r0 == r6) goto L_0x06f7
            r6 = 224(0xe0, float:3.14E-43)
            if (r0 == r6) goto L_0x06de
            switch(r0) {
                case 16: goto L_0x06bd;
                case 17: goto L_0x0697;
                case 18: goto L_0x0686;
                default: goto L_0x0063;
            }
        L_0x0063:
            switch(r0) {
                case 27: goto L_0x0650;
                case 28: goto L_0x064e;
                case 29: goto L_0x0619;
                case 30: goto L_0x05d9;
                case 31: goto L_0x03fe;
                case 32: goto L_0x03b0;
                case 33: goto L_0x031a;
                case 34: goto L_0x02be;
                case 35: goto L_0x02a2;
                case 36: goto L_0x0249;
                case 37: goto L_0x0200;
                case 38: goto L_0x0126;
                case 39: goto L_0x0068;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x0bef
        L_0x0068:
            byte r0 = r5[r10]
            r0 = r0 & r11
            if (r0 < r11) goto L_0x006f
            r0 = r11
            goto L_0x0070
        L_0x006f:
            r0 = r10
        L_0x0070:
            byte r6 = r5[r10]
            r6 = r6 & r8
            if (r6 <= r11) goto L_0x0077
            r6 = r11
            goto L_0x0078
        L_0x0077:
            r6 = r10
        L_0x0078:
            byte r9 = r5[r10]
            r9 = r9 & r12
            if (r9 <= r11) goto L_0x007f
            r9 = r11
            goto L_0x0080
        L_0x007f:
            r9 = r10
        L_0x0080:
            byte r12 = r5[r10]
            r12 = r12 & r15
            if (r12 <= r11) goto L_0x0087
            r12 = r11
            goto L_0x0088
        L_0x0087:
            r12 = r10
        L_0x0088:
            byte r13 = r5[r10]
            r13 = r13 & 32
            if (r13 <= r11) goto L_0x0090
            r13 = r11
            goto L_0x0091
        L_0x0090:
            r13 = r10
        L_0x0091:
            byte r15 = r5[r10]
            int r15 = com.wits.pms.utils.Utils.getIndexValue2DataNew(r15, r10, r11)
            if (r15 != 0) goto L_0x009b
            r15 = r11
            goto L_0x009c
        L_0x009b:
            r15 = r10
        L_0x009c:
            r0 = r15
            byte r15 = r5[r10]
            int r15 = com.wits.pms.utils.Utils.getIndexValue2DataNew(r15, r11, r11)
            if (r15 != 0) goto L_0x00a7
            r15 = r11
            goto L_0x00a8
        L_0x00a7:
            r15 = r10
        L_0x00a8:
            r6 = r15
            byte r15 = r5[r10]
            int r8 = com.wits.pms.utils.Utils.getIndexValue2DataNew(r15, r8, r11)
            if (r8 != 0) goto L_0x00b3
            r8 = r11
            goto L_0x00b4
        L_0x00b3:
            r8 = r10
        L_0x00b4:
            byte r9 = r5[r10]
            int r9 = com.wits.pms.utils.Utils.getIndexValue2DataNew(r9, r14, r11)
            if (r9 != 0) goto L_0x00be
            r9 = r11
            goto L_0x00bf
        L_0x00be:
            r9 = r10
        L_0x00bf:
            byte r12 = r5[r10]
            int r7 = com.wits.pms.utils.Utils.getIndexValue2DataNew(r12, r7, r11)
            if (r7 != 0) goto L_0x00c9
            r7 = r11
            goto L_0x00ca
        L_0x00c9:
            r7 = r10
        L_0x00ca:
            java.lang.String r12 = "KswMcuListener"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "onMcuMessage: CMD_TXZ_DATA isCarDoorClose ="
            r13.append(r14)
            r13.append(r0)
            java.lang.String r14 = "  isSeatbeltOn ="
            r13.append(r14)
            r13.append(r6)
            java.lang.String r14 = "  isNoEnoughOil ="
            r13.append(r14)
            r13.append(r8)
            java.lang.String r14 = " isNormalSpeed ="
            r13.append(r14)
            r13.append(r9)
            java.lang.String r14 = " isTempNormal ="
            r13.append(r14)
            r13.append(r7)
            java.lang.String r13 = r13.toString()
            android.util.Log.d(r12, r13)
            if (r0 == 0) goto L_0x010d
            if (r6 == 0) goto L_0x010d
            if (r8 == 0) goto L_0x010d
            if (r9 == 0) goto L_0x010d
            if (r7 == 0) goto L_0x010d
            r17 = r11
            goto L_0x010f
        L_0x010d:
            r17 = r10
        L_0x010f:
            com.wits.pms.core.CenterControlImpl r16 = com.wits.pms.core.CenterControlImpl.getImpl()
            com.wits.pms.statuscontrol.McuStatus$CarData r10 = r4.carData
            r18 = r0
            r19 = r6
            r20 = r9
            r21 = r7
            r22 = r8
            r23 = r10
            r16.sendCarInfo2Txz(r17, r18, r19, r20, r21, r22, r23)
            goto L_0x0bef
        L_0x0126:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            byte r0 = r5[r10]
            switch(r0) {
                case 1: goto L_0x01ca;
                case 2: goto L_0x01bc;
                case 3: goto L_0x01ae;
                case 4: goto L_0x01a0;
                case 5: goto L_0x0192;
                case 6: goto L_0x0134;
                default: goto L_0x0132;
            }
        L_0x0132:
            goto L_0x01d8
        L_0x0134:
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r0 = r4.mediaPlayStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData
            com.wits.pms.statuscontrol.McuStatus$MediaData$MODE r0 = r0.mode
            byte r6 = r5[r11]
            if (r6 != r11) goto L_0x0145
            r6 = r11
            goto L_0x0146
        L_0x0145:
            r6 = r10
        L_0x0146:
            r0.ASL = r6
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r0 = r4.mediaPlayStatus
            byte r6 = r5[r11]
            if (r6 != r11) goto L_0x0150
            r6 = r11
            goto L_0x0151
        L_0x0150:
            r6 = r10
        L_0x0151:
            r0.ALS = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "ASL "
            r6.append(r7)
            byte r7 = r5[r11]
            if (r7 != r11) goto L_0x0166
            java.lang.String r7 = "ON"
            goto L_0x0168
        L_0x0166:
            java.lang.String r7 = "OFF"
        L_0x0168:
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
            goto L_0x01d8
        L_0x0192:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.BAL = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "BAL"
            r0.changeVol = r6
            goto L_0x01d8
        L_0x01a0:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.FAD = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "FAD"
            r0.changeVol = r6
            goto L_0x01d8
        L_0x01ae:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.TRE = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "TRE"
            r0.changeVol = r6
            goto L_0x01d8
        L_0x01bc:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.MID = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "MID"
            r0.changeVol = r6
            goto L_0x01d8
        L_0x01ca:
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            byte r6 = r5[r11]
            int r6 = r6 - r15
            r0.BAS = r6
            com.wits.pms.statuscontrol.McuStatus$EqData r0 = r4.eqData
            java.lang.String r6 = "BAS"
            r0.changeVol = r6
        L_0x01d8:
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
            goto L_0x0bef
        L_0x0200:
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
            if (r8 == r11) goto L_0x0222
            if (r8 == r7) goto L_0x021d
            goto L_0x0227
        L_0x021d:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r7 = r4.bluetoothStatus
            r7.settingsInfo = r6
            goto L_0x0227
        L_0x0222:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r7 = r4.bluetoothStatus
            r7.name = r6
        L_0x0227:
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
            goto L_0x0bef
        L_0x0249:
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
            if (r6 <= r11) goto L_0x0261
            r6 = r11
            goto L_0x0262
        L_0x0261:
            r6 = r10
        L_0x0262:
            r0.isCalling = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & 224(0xe0, float:3.14E-43)
            int r6 = r6 >> r9
            r0.callSignal = r6
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r6 = r6 & r12
            if (r6 <= r11) goto L_0x0275
            goto L_0x0276
        L_0x0275:
            r11 = r10
        L_0x0276:
            r0.playingMusic = r11
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r0 = r4.bluetoothStatus
            byte r6 = r5[r10]
            r7 = 7
            r6 = r6 & r7
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
            goto L_0x0bef
        L_0x02a2:
            byte r0 = r5[r10]     // Catch:{ Exception -> 0x02bb }
            r0 = r0 & 127(0x7f, float:1.78E-43)
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x02bb }
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 <= 0) goto L_0x02ae
            r10 = r11
        L_0x02ae:
            r6 = r10
            java.lang.String r7 = "mcu_volume_level"
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusInt(r7, r0)     // Catch:{ Exception -> 0x02bb }
            java.lang.String r7 = "mcu_volume_mute"
            com.wits.pms.statuscontrol.PowerManagerApp.setBooleanStatus(r7, r6)     // Catch:{ Exception -> 0x02bb }
            goto L_0x0bef
        L_0x02bb:
            r0 = move-exception
            goto L_0x0bef
        L_0x02be:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r0 = r4.discStatus
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            java.lang.String r0 = ""
            byte r6 = r5[r10]
            switch(r6) {
                case 1: goto L_0x02d9;
                case 2: goto L_0x02d6;
                case 3: goto L_0x02d3;
                case 4: goto L_0x02d0;
                case 5: goto L_0x02cd;
                default: goto L_0x02cc;
            }
        L_0x02cc:
            goto L_0x02dc
        L_0x02cd:
            java.lang.String r0 = "WAIT"
            goto L_0x02dc
        L_0x02d0:
            java.lang.String r0 = "DISC FULL"
            goto L_0x02dc
        L_0x02d3:
            java.lang.String r0 = "DISC IN"
            goto L_0x02dc
        L_0x02d6:
            java.lang.String r0 = "EJECT"
            goto L_0x02dc
        L_0x02d9:
            java.lang.String r0 = "LOAD"
        L_0x02dc:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            r7 = 6
            boolean[] r7 = new boolean[r7]
            r6.discInsert = r7
            r6 = r10
        L_0x02e4:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r7 = r4.discStatus
            boolean[] r7 = r7.discInsert
            int r7 = r7.length
            if (r6 >= r7) goto L_0x02ff
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r7 = r4.discStatus
            boolean[] r7 = r7.discInsert
            byte r9 = r5[r11]
            r9 = r9 & r13
            int r12 = r11 << r6
            r9 = r9 & r12
            if (r9 < r11) goto L_0x02f9
            r9 = r11
            goto L_0x02fa
        L_0x02f9:
            r9 = r10
        L_0x02fa:
            r7[r6] = r9
            int r6 = r6 + 1
            goto L_0x02e4
        L_0x02ff:
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            r6.status = r0
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r6 = r4.discStatus
            byte r7 = r5[r8]
            r6.range = r7
            java.lang.String r6 = "mcuDiscStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            com.wits.pms.statuscontrol.McuStatus$DiscStatus r8 = r4.discStatus
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)
            return
        L_0x031a:
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
                case 0: goto L_0x0369;
                case 1: goto L_0x0366;
                case 6: goto L_0x0352;
                case 7: goto L_0x034f;
                case 8: goto L_0x034c;
                case 14: goto L_0x0349;
                case 15: goto L_0x0346;
                default: goto L_0x0345;
            }
        L_0x0345:
            goto L_0x036c
        L_0x0346:
            java.lang.String r0 = "UNKNOWN"
            goto L_0x036c
        L_0x0349:
            java.lang.String r0 = "AUDIO OFF"
            goto L_0x036c
        L_0x034c:
            java.lang.String r0 = "NO MUSIC"
            goto L_0x036c
        L_0x034f:
            java.lang.String r0 = "ERROR"
            goto L_0x036c
        L_0x0352:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            int r6 = r6.type
            if (r6 != r15) goto L_0x035b
            java.lang.String r0 = "READING DISC"
            goto L_0x036c
        L_0x035b:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            int r6 = r6.type
            r7 = 17
            if (r6 != r7) goto L_0x036c
            java.lang.String r0 = "READING FILE"
            goto L_0x036c
        L_0x0366:
            java.lang.String r0 = "PAUSE"
            goto L_0x036c
        L_0x0369:
            java.lang.String r0 = "PLAY"
        L_0x036c:
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            r6.status = r0
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r7 = r5[r8]
            r7 = r7 & r15
            if (r7 <= r11) goto L_0x0379
            r7 = r11
            goto L_0x037a
        L_0x0379:
            r7 = r10
        L_0x037a:
            r6.RPT = r7
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r7 = r5[r8]
            r7 = r7 & r12
            if (r7 <= r11) goto L_0x0385
            r7 = r11
            goto L_0x0386
        L_0x0385:
            r7 = r10
        L_0x0386:
            r6.ST = r7
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r7 = r5[r8]
            r7 = r7 & r8
            if (r7 <= r11) goto L_0x0391
            r7 = r11
            goto L_0x0392
        L_0x0391:
            r7 = r10
        L_0x0392:
            r6.SCAN = r7
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r6 = r4.mediaPlayStatus
            byte r7 = r5[r8]
            r7 = r7 & r11
            if (r7 < r11) goto L_0x039d
            r10 = r11
        L_0x039d:
            r6.RAND = r10
            java.lang.String r6 = "mcuMediaPlayStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaPlayStatus r8 = r4.mediaPlayStatus
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)
            return
        L_0x03b0:
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r0 = r4.mediaStringInfo
            if (r0 != 0) goto L_0x03b5
            return
        L_0x03b5:
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r0 = r4.mediaStringInfo
            int r6 = r0.times
            int r6 = r6 + r11
            r0.times = r6
            java.lang.String r0 = r1.autoGetString(r5)
            if (r0 != 0) goto L_0x03c3
            return
        L_0x03c3:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData
            com.wits.pms.statuscontrol.McuStatus$MediaData$BaseMediaInfo r6 = r6.getCurrentMediaInfo()
            if (r6 != 0) goto L_0x03cc
            return
        L_0x03cc:
            byte r7 = r5[r10]
            switch(r7) {
                case 1: goto L_0x03db;
                case 2: goto L_0x03d8;
                case 3: goto L_0x03d5;
                case 4: goto L_0x03d2;
                default: goto L_0x03d1;
            }
        L_0x03d1:
            goto L_0x03ed
        L_0x03d2:
            r6.folderName = r0
            goto L_0x03ed
        L_0x03d5:
            r6.album = r0
            goto L_0x03ed
        L_0x03d8:
            r6.artist = r0
            goto L_0x03ed
        L_0x03db:
            java.lang.String r7 = r6.name
            if (r7 == 0) goto L_0x03ea
            java.lang.String r7 = r6.name
            boolean r7 = r7.equals(r0)
            if (r7 != 0) goto L_0x03ea
            r6.reset()
        L_0x03ea:
            r6.name = r0
        L_0x03ed:
            java.lang.String r7 = "mcuMediaJson"
            com.google.gson.Gson r8 = new com.google.gson.Gson
            r8.<init>()
            com.wits.pms.statuscontrol.McuStatus$MediaData r9 = r4.mediaData
            java.lang.String r8 = r8.toJson((java.lang.Object) r9)
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r7, r8)
            return
        L_0x03fe:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            int r6 = r0.times     // Catch:{ Exception -> 0x05d7 }
            int r6 = r6 + r11
            r0.times = r6     // Catch:{ Exception -> 0x05d7 }
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x05d7 }
            r0.type = r6     // Catch:{ Exception -> 0x05d7 }
            byte r6 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            r15 = -1
            if (r6 != r13) goto L_0x0410
            r5[r11] = r15     // Catch:{ Exception -> 0x05d7 }
        L_0x0410:
            byte r6 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            if (r6 != r13) goto L_0x0416
            r5[r8] = r15     // Catch:{ Exception -> 0x05d7 }
        L_0x0416:
            byte r6 = r5[r9]     // Catch:{ Exception -> 0x05d7 }
            if (r6 != r13) goto L_0x041c
            r5[r9] = r15     // Catch:{ Exception -> 0x05d7 }
        L_0x041c:
            r6 = 6
            byte r9 = r5[r6]     // Catch:{ Exception -> 0x05d7 }
            if (r9 != r13) goto L_0x0423
            r5[r6] = r15     // Catch:{ Exception -> 0x05d7 }
        L_0x0423:
            byte r6 = r5[r10]     // Catch:{ Exception -> 0x05d7 }
            if (r6 == r11) goto L_0x051c
            r9 = 21
            if (r6 == r9) goto L_0x04f5
            r9 = 64
            if (r6 == r9) goto L_0x04b6
            switch(r6) {
                case 16: goto L_0x0490;
                case 17: goto L_0x0462;
                case 18: goto L_0x0434;
                default: goto L_0x0432;
            }     // Catch:{ Exception -> 0x05d7 }
        L_0x0432:
            goto L_0x05c6
        L_0x0434:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData$MP3 r6 = r6.mp3     // Catch:{ Exception -> 0x05d7 }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            int r8 = r8 << r12
            int r9 = r9 + r8
            r6.folderNumber = r9     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r14]     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            int r7 = r7 << r12
            int r8 = r8 + r7
            r6.fileNumber = r8     // Catch:{ Exception -> 0x05d7 }
            r7 = 5
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.min = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 6
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.sec = r7     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.min     // Catch:{ Exception -> 0x05d7 }
            r7.min = r8     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.sec     // Catch:{ Exception -> 0x05d7 }
            r7.sec = r8     // Catch:{ Exception -> 0x05d7 }
            r0.mp3 = r6     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05c6
        L_0x0462:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Usb r6 = r6.usb     // Catch:{ Exception -> 0x05d7 }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            int r8 = r8 << r12
            int r9 = r9 + r8
            r6.folderNumber = r9     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r14]     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            int r7 = r7 << r12
            int r8 = r8 + r7
            r6.fileNumber = r8     // Catch:{ Exception -> 0x05d7 }
            r7 = 5
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.min = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 6
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.sec = r7     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.min     // Catch:{ Exception -> 0x05d7 }
            r7.min = r8     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.sec     // Catch:{ Exception -> 0x05d7 }
            r7.sec = r8     // Catch:{ Exception -> 0x05d7 }
            r0.usb = r6     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05c6
        L_0x0490:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Disc r6 = r6.disc     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            r6.number = r7     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            r6.track = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 5
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.min = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 6
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.sec = r7     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.min     // Catch:{ Exception -> 0x05d7 }
            r7.min = r8     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaStringInfo r7 = r4.mediaStringInfo     // Catch:{ Exception -> 0x05d7 }
            int r8 = r6.sec     // Catch:{ Exception -> 0x05d7 }
            r7.sec = r8     // Catch:{ Exception -> 0x05d7 }
            r0.disc = r6     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05c6
        L_0x04b6:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData$MODE r6 = r6.mode     // Catch:{ Exception -> 0x05d7 }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            if (r9 != r11) goto L_0x04c0
            r9 = r11
            goto L_0x04c1
        L_0x04c0:
            r9 = r10
        L_0x04c1:
            r6.ASL = r9     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            if (r8 != r11) goto L_0x04c9
            r8 = r11
            goto L_0x04ca
        L_0x04c9:
            r8 = r10
        L_0x04ca:
            r6.ST = r8     // Catch:{ Exception -> 0x05d7 }
            byte r8 = r5[r14]     // Catch:{ Exception -> 0x05d7 }
            if (r8 != r11) goto L_0x04d2
            r8 = r11
            goto L_0x04d3
        L_0x04d2:
            r8 = r10
        L_0x04d3:
            r6.RAND = r8     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            if (r7 != r11) goto L_0x04db
            r7 = r11
            goto L_0x04dc
        L_0x04db:
            r7 = r10
        L_0x04dc:
            r6.RPT = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 5
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            if (r7 != r11) goto L_0x04e5
            r7 = r11
            goto L_0x04e6
        L_0x04e5:
            r7 = r10
        L_0x04e6:
            r6.PAUSE = r7     // Catch:{ Exception -> 0x05d7 }
            r7 = 6
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            if (r7 != r11) goto L_0x04ef
            r10 = r11
        L_0x04ef:
            r6.SCAN = r10     // Catch:{ Exception -> 0x05d7 }
            r0.mode = r6     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05c6
        L_0x04f5:
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x05d7 }
            int r7 = r6.times     // Catch:{ Exception -> 0x05d7 }
            int r7 = r7 + r11
            r6.times = r7     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x05d7 }
            r7 = 5
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.min = r7     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r6 = r4.bluetoothStatus     // Catch:{ Exception -> 0x05d7 }
            r7 = 6
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.sec = r7     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r6 = "mcuBluetoothStatus"
            com.google.gson.Gson r7 = new com.google.gson.Gson     // Catch:{ Exception -> 0x05d7 }
            r7.<init>()     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$CarBluetoothStatus r8 = r4.bluetoothStatus     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05c6
        L_0x051c:
            com.wits.pms.statuscontrol.McuStatus$MediaData r6 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData$Fm r6 = r6.fm     // Catch:{ Exception -> 0x05d7 }
            byte r9 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            switch(r9) {
                case 0: goto L_0x056d;
                case 1: goto L_0x056d;
                case 2: goto L_0x056d;
                case 3: goto L_0x056d;
                default: goto L_0x0525;
            }     // Catch:{ Exception -> 0x05d7 }
        L_0x0525:
            switch(r9) {
                case 16: goto L_0x052a;
                case 17: goto L_0x052a;
                case 18: goto L_0x052a;
                case 19: goto L_0x052a;
                default: goto L_0x0528;
            }     // Catch:{ Exception -> 0x05d7 }
        L_0x0528:
            goto L_0x05b1
        L_0x052a:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d7 }
            r9.<init>()     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r10 = "AM"
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            r15 = 16
            if (r10 != r15) goto L_0x053d
            java.lang.String r10 = ""
            goto L_0x0544
        L_0x053d:
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            int r10 = r10 - r15
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x05d7 }
        L_0x0544:
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x05d7 }
            r6.name = r9     // Catch:{ Exception -> 0x05d7 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d7 }
            r9.<init>()     // Catch:{ Exception -> 0x05d7 }
            byte r10 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            r10 = r10 & r13
            int r10 = r10 << r12
            byte r12 = r5[r14]     // Catch:{ Exception -> 0x05d7 }
            r12 = r12 & r13
            int r10 = r10 + r12
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r10 = "Khz"
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x05d7 }
            r6.freq = r9     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.preFreq = r7     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05b1
        L_0x056d:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d7 }
            r9.<init>()     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r10 = "FM"
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            if (r10 != 0) goto L_0x057e
            java.lang.String r10 = ""
            goto L_0x0584
        L_0x057e:
            byte r10 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            java.lang.Byte r10 = java.lang.Byte.valueOf(r10)     // Catch:{ Exception -> 0x05d7 }
        L_0x0584:
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x05d7 }
            r6.name = r9     // Catch:{ Exception -> 0x05d7 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d7 }
            r9.<init>()     // Catch:{ Exception -> 0x05d7 }
            byte r10 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            r10 = r10 & r13
            int r10 = r10 << r12
            byte r12 = r5[r14]     // Catch:{ Exception -> 0x05d7 }
            r12 = r12 & r13
            int r10 = r10 + r12
            float r10 = (float) r10     // Catch:{ Exception -> 0x05d7 }
            r12 = 1120403456(0x42c80000, float:100.0)
            float r10 = r10 / r12
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r10 = "Mhz"
            r9.append(r10)     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x05d7 }
            r6.freq = r9     // Catch:{ Exception -> 0x05d7 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x05d7 }
            r6.preFreq = r7     // Catch:{ Exception -> 0x05d7 }
        L_0x05b1:
            byte r7 = r5[r11]     // Catch:{ Exception -> 0x05d7 }
            r9 = -1
            if (r7 != r9) goto L_0x05ba
            java.lang.String r7 = "-"
            r6.name = r7     // Catch:{ Exception -> 0x05d7 }
        L_0x05ba:
            byte r7 = r5[r8]     // Catch:{ Exception -> 0x05d7 }
            r8 = -1
            if (r7 != r8) goto L_0x05c3
            java.lang.String r7 = "-"
            r6.freq = r7     // Catch:{ Exception -> 0x05d7 }
        L_0x05c3:
            r0.fm = r6     // Catch:{ Exception -> 0x05d7 }
        L_0x05c6:
            java.lang.String r6 = "mcuMediaJson"
            com.google.gson.Gson r7 = new com.google.gson.Gson     // Catch:{ Exception -> 0x05d7 }
            r7.<init>()     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.McuStatus$MediaData r8 = r4.mediaData     // Catch:{ Exception -> 0x05d7 }
            java.lang.String r7 = r7.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x05d7 }
            com.wits.pms.statuscontrol.PowerManagerApp.setStatusString(r6, r7)     // Catch:{ Exception -> 0x05d7 }
            goto L_0x05d8
        L_0x05d7:
            r0 = move-exception
        L_0x05d8:
            return
        L_0x05d9:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r6 = "com.wits.pms.MCU_CHECK_CAR"
            r0.<init>((java.lang.String) r6)
            r6 = 16777248(0x1000020, float:2.3509977E-38)
            r0.addFlags(r6)
            java.lang.String r6 = "checkCanStatus"
            r9 = 6
            byte[] r9 = new byte[r9]
            byte r12 = r5[r10]
            r9[r10] = r12
            byte r10 = r5[r11]
            r9[r11] = r10
            byte r10 = r5[r8]
            r9[r8] = r10
            byte r8 = r5[r14]
            r9[r14] = r8
            byte r8 = r5[r7]
            r9[r7] = r8
            r7 = 5
            byte r8 = r5[r7]
            r9[r7] = r8
            r0.putExtra((java.lang.String) r6, (byte[]) r9)
            android.content.Context r6 = r1.mContext
            android.content.Context r7 = r1.mContext
            android.content.pm.ApplicationInfo r7 = r7.getApplicationInfo()
            int r7 = r7.uid
            android.os.UserHandle r7 = android.os.UserHandle.getUserHandleForUid(r7)
            r6.sendBroadcastAsUser(r0, r7)
            return
        L_0x0619:
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r10]
            if (r6 != r11) goto L_0x0621
            r6 = r11
            goto L_0x0622
        L_0x0621:
            r6 = r10
        L_0x0622:
            r0.highChassisSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r11]
            r0.airMaticStatus = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r8]
            if (r6 != r11) goto L_0x0632
            r6 = r11
            goto L_0x0633
        L_0x0632:
            r6 = r10
        L_0x0633:
            r0.auxiliaryRadar = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r14]
            r0.light1 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r7]
            r0.light2 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            r6 = 5
            byte r6 = r5[r6]
            if (r6 != r11) goto L_0x064a
            r10 = r11
        L_0x064a:
            r0.airBagSystem = r10
            goto L_0x0bef
        L_0x064e:
            goto L_0x0bef
        L_0x0650:
            byte r0 = r5[r10]
            r0 = r0 & r13
            int r0 = r0 << r12
            byte r6 = r5[r11]
            r6 = r6 & r13
            int r0 = r0 + r6
            byte r6 = r5[r8]
            r6 = r6 & r13
            int r6 = r6 << r12
            byte r9 = r5[r14]
            r9 = r9 & r13
            int r6 = r6 + r9
            byte r7 = r5[r7]
            if (r7 != r11) goto L_0x0666
            r9 = r11
            goto L_0x0667
        L_0x0666:
            r9 = r10
        L_0x0667:
            if (r9 == 0) goto L_0x0674
            com.wits.pms.utils.TouchControl r11 = r1.mTouchControl
            boolean r11 = r11.isDown()
            if (r11 == 0) goto L_0x0672
            goto L_0x067e
        L_0x0672:
            r8 = r10
            goto L_0x067e
        L_0x0674:
            com.wits.pms.utils.TouchControl r10 = r1.mTouchControl
            boolean r10 = r10.isDown()
            if (r10 == 0) goto L_0x067e
            r8 = r11
        L_0x067e:
            com.wits.pms.utils.TouchControl r10 = r1.mTouchControl
            int r13 = r13 - r6
            r10.opPointerEvent(r0, r13, r8)
            goto L_0x0bef
        L_0x0686:
            r0 = 40
            byte[] r0 = new byte[r0]
            int r6 = r0.length
            java.lang.System.arraycopy(r5, r10, r0, r10, r6)
            java.lang.String r6 = new java.lang.String
            r6.<init>(r0)
            r4.mcuVerison = r6
            goto L_0x0bef
        L_0x0697:
            byte r0 = r5[r10]
            if (r0 != r7) goto L_0x06a6
            byte r0 = r5[r11]
            if (r0 != r11) goto L_0x06a6
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.closeScreen(r10)
        L_0x06a6:
            byte r0 = r5[r10]
            if (r0 > r14) goto L_0x06b1
            byte r0 = r5[r11]
            r3.setCcd(r0)
            goto L_0x0bef
        L_0x06b1:
            byte r0 = r5[r10]
            r6 = 5
            if (r0 != r6) goto L_0x0bef
            byte r0 = r5[r11]
            r3.setAcc(r0)
            goto L_0x0bef
        L_0x06bd:
            byte r0 = r5[r10]
            if (r0 != r11) goto L_0x06c3
            r10 = r11
        L_0x06c3:
            r0 = r10
            if (r0 == 0) goto L_0x06d0
            r3.setAcc(r11)
            r6 = 500(0x1f4, double:2.47E-321)
            com.wits.pms.mcu.custom.utils.AccLight.show(r6)
            goto L_0x0bef
        L_0x06d0:
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU
            r7.<init>()
            r8 = 1000(0x3e8, double:4.94E-321)
            r6.postDelayed(r7, r8)
            goto L_0x0bef
        L_0x06de:
            byte r0 = r5[r10]
            if (r0 != r7) goto L_0x0bef
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            if (r0 == 0) goto L_0x06f3
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x06f3
            android.content.Context r0 = r1.mContext
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.fix(r0)
        L_0x06f3:
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.NEEDFIX = r11
            goto L_0x0bef
        L_0x06f7:
            byte r0 = r5[r10]
            r6 = 16
            if (r0 == r6) goto L_0x0aee
            r6 = 18
            if (r0 == r6) goto L_0x0ae4
            r6 = 28
            r9 = 15
            if (r0 == r6) goto L_0x0a4c
            switch(r0) {
                case 23: goto L_0x0876;
                case 24: goto L_0x0851;
                case 25: goto L_0x0781;
                case 26: goto L_0x070c;
                default: goto L_0x070a;
            }
        L_0x070a:
            goto L_0x0aec
        L_0x070c:
            byte r0 = r5[r11]
            if (r0 != r8) goto L_0x074b
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.stopMedia()
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0747 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x0747 }
            java.lang.String r6 = "CarDisplay"
            int r0 = android.provider.Settings.System.getInt(r0, r6)     // Catch:{ SettingNotFoundException -> 0x0747 }
            if (r0 != 0) goto L_0x0746
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0747 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x0747 }
            java.lang.String r6 = "OEM_FM"
            int r0 = android.provider.Settings.System.getInt(r0, r6, r10)     // Catch:{ SettingNotFoundException -> 0x0747 }
            if (r0 != 0) goto L_0x0746
            android.content.Intent r0 = new android.content.Intent     // Catch:{ SettingNotFoundException -> 0x0747 }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0747 }
            java.lang.Class<com.wits.pms.ClockActivity> r7 = com.wits.pms.ClockActivity.class
            r0.<init>((android.content.Context) r6, (java.lang.Class<?>) r7)     // Catch:{ SettingNotFoundException -> 0x0747 }
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r0.setFlags(r6)     // Catch:{ SettingNotFoundException -> 0x0747 }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0747 }
            r6.startActivity(r0)     // Catch:{ SettingNotFoundException -> 0x0747 }
        L_0x0746:
            goto L_0x074b
        L_0x0747:
            r0 = move-exception
            r0.printStackTrace()
        L_0x074b:
            byte r0 = r5[r11]
            r4.systemMode = r0
            int r0 = r4.systemMode
            if (r0 != r11) goto L_0x0aec
            r6 = 0
            r7 = 0
            r8 = r10
            java.lang.String r0 = "callStatus"
            int r0 = com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt(r0)     // Catch:{ RemoteException -> 0x0769 }
            r8 = r0
            r9 = 7
            if (r8 != r9) goto L_0x0761
            goto L_0x0762
        L_0x0761:
            r11 = r10
        L_0x0762:
            r6 = r11
            boolean r0 = com.wits.pms.statuscontrol.BtPhoneStatus.isCalling(r8)     // Catch:{ RemoteException -> 0x0769 }
            r7 = r0
            goto L_0x076a
        L_0x0769:
            r0 = move-exception
        L_0x076a:
            java.lang.String r0 = "persist.sys.hicar_connect"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r9 = "true"
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x077f
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.stopHicarMusic(r10)
        L_0x077f:
            goto L_0x0aec
        L_0x0781:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r11]
            r6 = r6 & r13
            int r6 = r6 << r12
            byte r10 = r5[r8]
            r10 = r10 & r13
            int r6 = r6 + r10
            r0.mileage = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r14]
            r6 = r6 & r13
            int r6 = r6 << r12
            byte r7 = r5[r7]
            r7 = r7 & r13
            int r6 = r6 + r7
            float r6 = (float) r6
            r7 = 1092616192(0x41200000, float:10.0)
            float r6 = r6 / r7
            r0.oilWear = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 5
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r6 = r6 << r12
            r7 = 6
            byte r7 = r5[r7]
            r7 = r7 & r13
            int r6 = r6 + r7
            float r6 = (float) r6
            r7 = 1092616192(0x41200000, float:10.0)
            float r6 = r6 / r7
            r0.averSpeed = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 7
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r6 = r6 << r12
            byte r7 = r5[r12]
            r7 = r7 & r13
            int r6 = r6 + r7
            r0.speed = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 9
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r6 = r6 << r12
            r7 = 10
            byte r7 = r5[r7]
            r7 = r7 & r13
            int r6 = r6 + r7
            r0.engineTurnS = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 11
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r6 = r6 << r12
            r7 = 12
            byte r7 = r5[r7]
            r7 = r7 & r13
            int r6 = r6 + r7
            r0.oilSum = r6
            r0 = 13
            byte r6 = r5[r0]
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 <= 0) goto L_0x0803
            byte r0 = r5[r0]
            r0 = r0 & r13
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r0 = r0 + r6
            int r0 = ~r0
            int r0 = r0 + r11
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r7 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r0
            double r14 = (double) r7
            r16 = -4631501856787818086(0xbfb999999999999a, double:-0.1)
            double r14 = r14 * r16
            float r7 = (float) r14
            r6.airTemperature = r7
            goto L_0x0821
        L_0x0803:
            r0 = 13
            byte r0 = r5[r0]
            r0 = r0 & r13
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & r13
            int r0 = r0 + r6
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r7 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r0
            double r14 = (double) r7
            r16 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            double r14 = r14 * r16
            float r7 = (float) r14
            r6.airTemperature = r7
        L_0x0821:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r9]
            r6 = r6 & r12
            r0.distanceUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r9]
            r6 = r6 & r8
            r0.oilUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            int r6 = r0.oilUnitType
            byte r7 = r5[r9]
            r7 = r7 & r13
            r7 = r7 & r11
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
        L_0x0851:
            r6 = 7
            byte[] r0 = new byte[r6]
            int r6 = r0.length
            java.lang.System.arraycopy(r5, r11, r0, r10, r6)
            r6 = 6
            byte r6 = r0[r6]
            if (r6 == 0) goto L_0x085e
            goto L_0x085f
        L_0x085e:
        L_0x085f:
            android.content.Context r15 = r1.mContext
            byte r6 = r0[r10]
            r6 = r6 & r13
            byte r17 = r0[r11]
            byte r18 = r0[r8]
            byte r19 = r0[r14]
            byte r20 = r0[r7]
            r7 = 5
            byte r21 = r0[r7]
            r16 = r6
            com.wits.pms.utils.TimeSetting.setTime(r15, r16, r17, r18, r19, r20, r21)
            goto L_0x0aec
        L_0x0876:
            byte r0 = r5[r8]
            if (r0 == r11) goto L_0x087b
            return
        L_0x087b:
            byte r0 = r5[r11]
            r6 = 13
            if (r0 == r6) goto L_0x0a42
            r6 = 20
            if (r0 == r6) goto L_0x0a41
            switch(r0) {
                case 3: goto L_0x0a37;
                case 4: goto L_0x0a2d;
                case 5: goto L_0x0a23;
                case 6: goto L_0x09ea;
                case 7: goto L_0x09b1;
                default: goto L_0x0888;
            }
        L_0x0888:
            switch(r0) {
                case 30: goto L_0x091f;
                case 31: goto L_0x088d;
                default: goto L_0x088b;
            }
        L_0x088b:
            goto L_0x0a4a
        L_0x088d:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0908
            java.lang.String r0 = "vendor.wits.carplayWried.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x08aa
            goto L_0x0908
        L_0x08aa:
            java.lang.String r0 = "vendor.wits.androidAuto.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x08ff
            java.lang.String r0 = "vendor.wits.androidMirror.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x08c7
            goto L_0x08ff
        L_0x08c7:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x08e0
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.rejectPhone()
            goto L_0x0a4a
        L_0x08e0:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.mirror.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x08f6
            r0 = 112(0x70, float:1.57E-43)
            r6 = 7
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r6, r0)
            goto L_0x0a4a
        L_0x08f6:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.handUpPhone()
            goto L_0x0a4a
        L_0x08ff:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleAutoCall()
            goto L_0x0a4a
        L_0x0908:
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "com.zjinnova.zlink"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x0a4a
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x0a4a
        L_0x091f:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x099a
            java.lang.String r0 = "vendor.wits.carplayWried.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x093c
            goto L_0x099a
        L_0x093c:
            java.lang.String r0 = "vendor.wits.androidAuto.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0991
            java.lang.String r0 = "vendor.wits.androidMirror.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0959
            goto L_0x0991
        L_0x0959:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.mirror.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0972
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.acceptPhone()
            goto L_0x0a4a
        L_0x0972:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.mirror.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0988
            r0 = 111(0x6f, float:1.56E-43)
            r6 = 7
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r6, r0)
            goto L_0x0a4a
        L_0x0988:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.acceptPhone()
            goto L_0x0a4a
        L_0x0991:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleAutoCall()
            goto L_0x0a4a
        L_0x099a:
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "com.zjinnova.zlink"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x0a4a
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x0a4a
        L_0x09b1:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapLeft()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x09c9
            return
        L_0x09c9:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r6 = "AUTONAVI_STANDARD_BROADCAST_RECV"
            r0.setAction(r6)
            java.lang.String r6 = "KEY_TYPE"
            r7 = 10027(0x272b, float:1.4051E-41)
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_TYPE"
            r0.putExtra((java.lang.String) r6, (int) r11)
            java.lang.String r6 = "EXTRA_OPERA"
            r0.putExtra((java.lang.String) r6, (int) r11)
            android.content.Context r6 = r1.mContext
            r6.sendBroadcast(r0)
            goto L_0x0a4a
        L_0x09ea:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapRight()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x0a02
            return
        L_0x0a02:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r6 = "AUTONAVI_STANDARD_BROADCAST_RECV"
            r0.setAction(r6)
            java.lang.String r6 = "KEY_TYPE"
            r7 = 10027(0x272b, float:1.4051E-41)
            r0.putExtra((java.lang.String) r6, (int) r7)
            java.lang.String r6 = "EXTRA_TYPE"
            r0.putExtra((java.lang.String) r6, (int) r11)
            java.lang.String r6 = "EXTRA_OPERA"
            r0.putExtra((java.lang.String) r6, (int) r10)
            android.content.Context r6 = r1.mContext
            r6.sendBroadcast(r0)
            goto L_0x0a4a
        L_0x0a23:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.enter()
            goto L_0x0a4a
        L_0x0a2d:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapDown()
            goto L_0x0a4a
        L_0x0a37:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapUp()
            goto L_0x0a4a
        L_0x0a41:
            goto L_0x0a4a
        L_0x0a42:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.openSettings()
        L_0x0a4a:
            goto L_0x0aec
        L_0x0a4c:
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 == 0) goto L_0x0a56
            r6 = r11
            goto L_0x0a57
        L_0x0a56:
            r6 = r10
        L_0x0a57:
            r0.isOpen = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & 64
            if (r6 == 0) goto L_0x0a63
            r6 = r11
            goto L_0x0a64
        L_0x0a63:
            r6 = r10
        L_0x0a64:
            r0.AC_Switch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & 32
            if (r6 == 0) goto L_0x0a70
            r6 = r11
            goto L_0x0a71
        L_0x0a70:
            r6 = r10
        L_0x0a71:
            r0.loop = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r13 = 16
            r6 = r6 & r13
            if (r6 == 0) goto L_0x0a7e
            r6 = r8
            goto L_0x0a82
        L_0x0a7e:
            com.wits.pms.statuscontrol.McuStatus$ACData r6 = r4.acData
            int r6 = r6.loop
        L_0x0a82:
            r0.loop = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & r12
            if (r6 == 0) goto L_0x0a8d
            r6 = r11
            goto L_0x0a8e
        L_0x0a8d:
            r6 = r10
        L_0x0a8e:
            r0.frontMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & r8
            if (r6 == 0) goto L_0x0a99
            r6 = r11
            goto L_0x0a9a
        L_0x0a99:
            r6 = r10
        L_0x0a9a:
            r0.backMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r11]
            r6 = r6 & r11
            if (r6 == 0) goto L_0x0aa5
            r6 = r11
            goto L_0x0aa6
        L_0x0aa5:
            r6 = r10
        L_0x0aa6:
            r0.sync = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r8]
            r0.mode = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r14]
            r0.setLeftTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r7]
            r0.setRightTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 5
            byte r7 = r5[r6]
            r6 = r7 & 32
            if (r6 == 0) goto L_0x0ac7
            r6 = r11
            goto L_0x0ac8
        L_0x0ac7:
            r6 = r10
        L_0x0ac8:
            r0.eco = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 5
            byte r7 = r5[r6]
            r8 = 16
            r7 = r7 & r8
            if (r7 == 0) goto L_0x0ad6
            r10 = r11
        L_0x0ad6:
            r0.autoSwitch = r10
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r6]
            r6 = r6 & r9
            float r6 = (float) r6
            r7 = 1056964608(0x3f000000, float:0.5)
            float r6 = r6 * r7
            r0.speed = r6
            goto L_0x0aec
        L_0x0ae4:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r11]
            r6 = r6 & r13
            r0.carDoor = r6
        L_0x0aec:
            goto L_0x0bef
        L_0x0aee:
            byte r0 = r5[r11]
            r0 = r0 & r13
            r0 = r0 & r11
            r3.ill = r0
            byte r0 = r5[r11]
            r0 = r0 & r13
            r0 = r0 & r12
            r3.setEpb(r0)
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r11]
            r6 = r6 & r13
            r6 = r6 & r12
            if (r6 == 0) goto L_0x0b05
            r6 = r11
            goto L_0x0b06
        L_0x0b05:
            r6 = r10
        L_0x0b06:
            r0.handbrake = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & r13
            r6 = r6 & r11
            if (r6 == 0) goto L_0x0b12
            r10 = r11
        L_0x0b12:
            r0.safetyBelt = r10
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & r13
            r7 = 6
            r6 = r6 & r7
            r0.carGear = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & r13
            r6 = r6 & r12
            r0.signalLeft = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & r13
            r7 = 16
            r6 = r6 & r7
            r0.signalRight = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & r13
            r6 = r6 & 32
            r0.signalDouble = r6
            com.wits.pms.custom.CallBackServiceImpl r0 = com.wits.pms.custom.CallBackServiceImpl.getCallBackServiceImpl()
            r0.handleLRReverse()
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.core.PowerManagerImpl r0 = r0.getPms()
            java.lang.String r6 = "mcuJson"
            com.google.gson.Gson r7 = new com.google.gson.Gson
            r7.<init>()
            java.lang.String r7 = r7.toJson((java.lang.Object) r4)
            r0.updateMcuJsonStatus(r6, r7)
            return
        L_0x0b56:
            int r0 = r5.length
            r6 = 9
            if (r0 < r6) goto L_0x0bef
            com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.getSettings()
            byte r6 = r5[r12]
            r0.setUpProtocolForMcuListen(r6)
            goto L_0x0bef
        L_0x0b66:
            java.lang.String r0 = "KswMcuListener"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "memory mode: "
            r6.append(r7)
            byte r7 = r5[r11]
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.i(r0, r6)
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0baa }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x0baa }
            java.lang.String r6 = "memory_mode_for_freedom"
            int r0 = android.provider.Settings.System.getInt(r0, r6)     // Catch:{ SettingNotFoundException -> 0x0baa }
            byte r6 = r5[r11]     // Catch:{ SettingNotFoundException -> 0x0baa }
            r7 = 13
            if (r6 != r7) goto L_0x0b9c
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0baa }
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x0baa }
            java.lang.String r7 = "memory_mode_for_freedom"
            android.provider.Settings.System.putInt(r6, r7, r10)     // Catch:{ SettingNotFoundException -> 0x0baa }
            goto L_0x0ba9
        L_0x0b9c:
            if (r0 != 0) goto L_0x0ba9
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x0baa }
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x0baa }
            java.lang.String r7 = "memory_mode_for_freedom"
            android.provider.Settings.System.putInt(r6, r7, r11)     // Catch:{ SettingNotFoundException -> 0x0baa }
        L_0x0ba9:
            goto L_0x0bb6
        L_0x0baa:
            r0 = move-exception
            android.content.Context r6 = r1.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r7 = "memory_mode_for_freedom"
            android.provider.Settings.System.putInt(r6, r7, r11)
        L_0x0bb6:
            byte r0 = r5[r10]
            if (r0 != r11) goto L_0x0bef
            byte r0 = r5[r11]
            if (r0 == 0) goto L_0x0bda
            byte r0 = r5[r11]
            r6 = 6
            if (r0 == r6) goto L_0x0bda
            byte r0 = r5[r11]
            r6 = 9
            if (r0 == r6) goto L_0x0bda
            byte r0 = r5[r11]
            r6 = 5
            if (r0 == r6) goto L_0x0bda
            byte r0 = r5[r11]
            r6 = 11
            if (r0 == r6) goto L_0x0bda
            int r0 = r1.bootTime
            if (r0 != 0) goto L_0x0bda
            r10 = r11
        L_0x0bda:
            r0 = r10
            r1.bootTime = r11
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$U11nBajvlH9FvPJVYF0TydID9Vs r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$U11nBajvlH9FvPJVYF0TydID9Vs
            r7.<init>(r3, r5, r0)
            if (r0 == 0) goto L_0x0be9
            r8 = 5000(0x1388, double:2.4703E-320)
            goto L_0x0beb
        L_0x0be9:
            r8 = 0
        L_0x0beb:
            r6.postDelayed(r7, r8)
        L_0x0bef:
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
                String musicPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_MUSIC_PKG");
                String string = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_MUSIC_CLS");
                Log.d(TAG, "memory music musicPkg = " + musicPkg);
                if (TextUtils.isEmpty(musicPkg)) {
                    CenterControlImpl.getImpl().openMusic(true);
                    return;
                } else if (musicPkg.equals("cls.local.music")) {
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
                String videoPkg = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_VIDEO_PKG");
                String string2 = Settings.System.getString(kswMcuListener.mContext.getContentResolver(), "KEY_THIRD_APP_VIDEO_CLS");
                Log.d(TAG, "memory video videoPkg = " + videoPkg);
                if (TextUtils.isEmpty(videoPkg)) {
                    CenterControlImpl.getImpl().openVideo(true);
                    return;
                } else if (videoPkg.equals("cls.local.video")) {
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
