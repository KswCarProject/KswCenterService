package com.wits.pms.mcu.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.mcu.custom.utils.CanLogUtils;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.utils.SystemProperties;

public class KswMcuListener extends KswMcuReceiver {
    public static final String TAG = "KswMcuListener";
    /* access modifiers changed from: private */
    public final Context mContext = PowerManagerAppService.serviceContext;
    private final Handler mHandler = new Handler(this.mContext.getMainLooper());
    private final KswMcuSender mMcuSender = CenterControlImpl.getMcuSender();
    private String memoryPackage;
    private boolean openBluetooth;

    public static final class MediaType {
        public static final int SRC_ALL_APP = 13;
        public static final int SRC_AUX = 6;
        public static final int SRC_BT = 3;
        public static final int SRC_BT_MUSIC = 4;
        public static final int SRC_CAR = 0;
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
    /* JADX WARNING: type inference failed for: r11v1, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r0v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v11, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v6, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r0v16, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v13, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v20, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v23, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v26, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v36, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v7, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v9, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v48, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v51, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v54, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v57, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v58, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v59, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v60, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v63, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v66, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v41, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v42, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v113, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v117, types: [byte] */
    /* JADX WARNING: type inference failed for: r12v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v123, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v129, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v6, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v133, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v138, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v15, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v142, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v144, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v146, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v14, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v103, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v152, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v107, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v156, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v113, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v114, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r0v139, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v144, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v146, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v147, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v148, types: [byte] */
    /* JADX WARNING: type inference failed for: r0v149, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r0v150, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r0v151, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v174, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v176, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v177, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v179, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v180, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v181, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v29, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r9v8, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v9, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v10, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v22, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v23, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v190, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r6v191, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v32, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v12, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v29, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v14, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v37, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r9v17, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v21, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v38, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v23, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v44, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r9v25, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v40, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v45, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v46, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v47, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r8v42, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v48, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v50, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v52, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v54, types: [byte] */
    /* JADX WARNING: type inference failed for: r7v56, types: [byte] */
    /* JADX WARNING: type inference failed for: r8v46, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v65, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v66, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v67, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r8v47, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v68, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v70, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r8v49, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v73, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r7v74, types: [byte, int] */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMcuMessage(com.wits.pms.mcu.custom.KswMessage r24) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
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
            com.wits.pms.mcu.custom.KswMcuLogic.handleMsg(r24)
            com.wits.pms.mcu.custom.utils.UpdateHelper r0 = com.wits.pms.mcu.custom.utils.UpdateHelper.getInstance()
            r0.handleMessage(r2)
            boolean r0 = com.wits.pms.mcu.custom.utils.CanLogUtils.isCating()
            if (r0 == 0) goto L_0x0030
            com.wits.pms.mcu.custom.utils.CanLogUtils.canLog(r24)
        L_0x0030:
            com.wits.pms.statuscontrol.SystemStatus r3 = com.wits.pms.core.SystemStatusControl.getStatus()
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            com.wits.pms.statuscontrol.McuStatus r4 = r0.getMcuStatus()
            byte[] r5 = r24.getData()
            int r0 = r24.getCmdType()
            r6 = 21
            r8 = 9
            r9 = 6
            r10 = 5
            r11 = 0
            r12 = 1
            if (r0 == r6) goto L_0x06b7
            r6 = 23
            r13 = 8
            if (r0 == r6) goto L_0x06aa
            r6 = 161(0xa1, float:2.26E-43)
            r14 = 3
            r15 = 4
            r7 = 2
            if (r0 == r6) goto L_0x02ac
            r6 = 224(0xe0, float:3.14E-43)
            if (r0 == r6) goto L_0x0293
            switch(r0) {
                case 16: goto L_0x0272;
                case 17: goto L_0x024d;
                case 18: goto L_0x023c;
                default: goto L_0x0062;
            }
        L_0x0062:
            switch(r0) {
                case 28: goto L_0x023a;
                case 29: goto L_0x0206;
                case 30: goto L_0x01c8;
                case 31: goto L_0x0067;
                default: goto L_0x0065;
            }
        L_0x0065:
            goto L_0x0703
        L_0x0067:
            com.wits.pms.statuscontrol.McuStatus$MediaData r0 = new com.wits.pms.statuscontrol.McuStatus$MediaData     // Catch:{ Exception -> 0x01c5 }
            r0.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r6 = r5[r11]     // Catch:{ Exception -> 0x01c5 }
            r0.type = r6     // Catch:{ Exception -> 0x01c5 }
            byte r6 = r5[r11]     // Catch:{ Exception -> 0x01c5 }
            if (r6 == r12) goto L_0x0113
            r8 = 33
            if (r6 == r8) goto L_0x00fa
            r8 = 64
            if (r6 == r8) goto L_0x00bc
            switch(r6) {
                case 16: goto L_0x00a3;
                case 17: goto L_0x0081;
                default: goto L_0x007f;
            }     // Catch:{ Exception -> 0x01c5 }
        L_0x007f:
            goto L_0x01ad
        L_0x0081:
            com.wits.pms.statuscontrol.McuStatus$MediaData$Usb r6 = new com.wits.pms.statuscontrol.McuStatus$MediaData$Usb     // Catch:{ Exception -> 0x01c5 }
            r6.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            int r8 = r8 + r7
            int r7 = r8 << 8
            r6.folderNumber = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r14]     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r15]     // Catch:{ Exception -> 0x01c5 }
            int r7 = r7 + r8
            int r7 = r7 << r13
            r6.fileNumber = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r10]     // Catch:{ Exception -> 0x01c5 }
            r6.min = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x01c5 }
            r6.sec = r7     // Catch:{ Exception -> 0x01c5 }
            r0.usb = r6     // Catch:{ Exception -> 0x01c5 }
            goto L_0x01ad
        L_0x00a3:
            com.wits.pms.statuscontrol.McuStatus$MediaData$Disc r6 = new com.wits.pms.statuscontrol.McuStatus$MediaData$Disc     // Catch:{ Exception -> 0x01c5 }
            r6.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            r6.number = r8     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            r6.track = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r10]     // Catch:{ Exception -> 0x01c5 }
            r6.min = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x01c5 }
            r6.sec = r7     // Catch:{ Exception -> 0x01c5 }
            r0.disc = r6     // Catch:{ Exception -> 0x01c5 }
            goto L_0x01ad
        L_0x00bc:
            com.wits.pms.statuscontrol.McuStatus$MediaData$MODE r6 = new com.wits.pms.statuscontrol.McuStatus$MediaData$MODE     // Catch:{ Exception -> 0x01c5 }
            r6.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            if (r8 != r12) goto L_0x00c7
            r8 = r12
            goto L_0x00c8
        L_0x00c7:
            r8 = r11
        L_0x00c8:
            r6.ASL = r8     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            if (r7 != r12) goto L_0x00d0
            r7 = r12
            goto L_0x00d1
        L_0x00d0:
            r7 = r11
        L_0x00d1:
            r6.ST = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r14]     // Catch:{ Exception -> 0x01c5 }
            if (r7 != r12) goto L_0x00d9
            r7 = r12
            goto L_0x00da
        L_0x00d9:
            r7 = r11
        L_0x00da:
            r6.RAND = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r15]     // Catch:{ Exception -> 0x01c5 }
            if (r7 != r12) goto L_0x00e2
            r7 = r12
            goto L_0x00e3
        L_0x00e2:
            r7 = r11
        L_0x00e3:
            r6.RPT = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r10]     // Catch:{ Exception -> 0x01c5 }
            if (r7 != r12) goto L_0x00eb
            r7 = r12
            goto L_0x00ec
        L_0x00eb:
            r7 = r11
        L_0x00ec:
            r6.PAUSE = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x01c5 }
            if (r7 != r12) goto L_0x00f4
            r11 = r12
        L_0x00f4:
            r6.SCAN = r11     // Catch:{ Exception -> 0x01c5 }
            r0.mode = r6     // Catch:{ Exception -> 0x01c5 }
            goto L_0x01ad
        L_0x00fa:
            com.wits.pms.statuscontrol.McuStatus$MediaData$DVD r6 = new com.wits.pms.statuscontrol.McuStatus$MediaData$DVD     // Catch:{ Exception -> 0x01c5 }
            r6.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            r6.chapterNumber = r8     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            r6.totalChapter = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r10]     // Catch:{ Exception -> 0x01c5 }
            r6.min = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r9]     // Catch:{ Exception -> 0x01c5 }
            r6.sec = r7     // Catch:{ Exception -> 0x01c5 }
            r0.dvd = r6     // Catch:{ Exception -> 0x01c5 }
            goto L_0x01ad
        L_0x0113:
            com.wits.pms.statuscontrol.McuStatus$MediaData$Fm r6 = new com.wits.pms.statuscontrol.McuStatus$MediaData$Fm     // Catch:{ Exception -> 0x01c5 }
            r6.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r8 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            switch(r8) {
                case 0: goto L_0x0164;
                case 1: goto L_0x0164;
                case 2: goto L_0x0164;
                case 3: goto L_0x0164;
                default: goto L_0x011d;
            }     // Catch:{ Exception -> 0x01c5 }
        L_0x011d:
            switch(r8) {
                case 16: goto L_0x0122;
                case 17: goto L_0x0122;
                case 18: goto L_0x0122;
                case 19: goto L_0x0122;
                default: goto L_0x0120;
            }     // Catch:{ Exception -> 0x01c5 }
        L_0x0120:
            goto L_0x01aa
        L_0x0122:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c5 }
            r8.<init>()     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r9 = "AM"
            r8.append(r9)     // Catch:{ Exception -> 0x01c5 }
            byte r9 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            if (r9 != 0) goto L_0x0133
            java.lang.String r9 = ""
            goto L_0x0139
        L_0x0133:
            byte r9 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)     // Catch:{ Exception -> 0x01c5 }
        L_0x0139:
            r8.append(r9)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x01c5 }
            r6.name = r8     // Catch:{ Exception -> 0x01c5 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c5 }
            r8.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << r13
            byte r9 = r5[r14]     // Catch:{ Exception -> 0x01c5 }
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r7 = r7 + r9
            r8.append(r7)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r7 = "Khz"
            r8.append(r7)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r7 = r8.toString()     // Catch:{ Exception -> 0x01c5 }
            r6.freq = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r15]     // Catch:{ Exception -> 0x01c5 }
            r6.preFreq = r7     // Catch:{ Exception -> 0x01c5 }
            goto L_0x01aa
        L_0x0164:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c5 }
            r8.<init>()     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r9 = "FM"
            r8.append(r9)     // Catch:{ Exception -> 0x01c5 }
            byte r9 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            if (r9 != 0) goto L_0x0175
            java.lang.String r9 = ""
            goto L_0x017b
        L_0x0175:
            byte r9 = r5[r12]     // Catch:{ Exception -> 0x01c5 }
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)     // Catch:{ Exception -> 0x01c5 }
        L_0x017b:
            r8.append(r9)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x01c5 }
            r6.name = r8     // Catch:{ Exception -> 0x01c5 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c5 }
            r8.<init>()     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r7]     // Catch:{ Exception -> 0x01c5 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << r13
            byte r9 = r5[r14]     // Catch:{ Exception -> 0x01c5 }
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r7 = r7 + r9
            float r7 = (float) r7     // Catch:{ Exception -> 0x01c5 }
            r9 = 1120403456(0x42c80000, float:100.0)
            float r7 = r7 / r9
            r8.append(r7)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r7 = "Mhz"
            r8.append(r7)     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r7 = r8.toString()     // Catch:{ Exception -> 0x01c5 }
            r6.freq = r7     // Catch:{ Exception -> 0x01c5 }
            byte r7 = r5[r15]     // Catch:{ Exception -> 0x01c5 }
            r6.preFreq = r7     // Catch:{ Exception -> 0x01c5 }
        L_0x01aa:
            r0.fm = r6     // Catch:{ Exception -> 0x01c5 }
        L_0x01ad:
            com.wits.pms.core.SystemStatusControl r6 = com.wits.pms.core.SystemStatusControl.getDefault()     // Catch:{ Exception -> 0x01c5 }
            com.wits.pms.core.PowerManagerImpl r6 = r6.getPms()     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r7 = "mcuMediaJson"
            com.google.gson.Gson r8 = new com.google.gson.Gson     // Catch:{ Exception -> 0x01c5 }
            r8.<init>()     // Catch:{ Exception -> 0x01c5 }
            java.lang.String r8 = r8.toJson((java.lang.Object) r0)     // Catch:{ Exception -> 0x01c5 }
            r6.updateMcuJsonStatus(r7, r8)     // Catch:{ Exception -> 0x01c5 }
            goto L_0x0703
        L_0x01c5:
            r0 = move-exception
            goto L_0x0703
        L_0x01c8:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r6 = "com.wits.pms.MCU_CHECK_CAR"
            r0.<init>((java.lang.String) r6)
            r6 = 16777248(0x1000020, float:2.3509977E-38)
            r0.addFlags(r6)
            java.lang.String r6 = "checkCanStatus"
            byte[] r8 = new byte[r9]
            byte r9 = r5[r11]
            r8[r11] = r9
            byte r9 = r5[r12]
            r8[r12] = r9
            byte r9 = r5[r7]
            r8[r7] = r9
            byte r7 = r5[r14]
            r8[r14] = r7
            byte r7 = r5[r15]
            r8[r15] = r7
            byte r7 = r5[r10]
            r8[r10] = r7
            r0.putExtra((java.lang.String) r6, (byte[]) r8)
            android.content.Context r6 = r1.mContext
            android.content.Context r7 = r1.mContext
            android.content.pm.ApplicationInfo r7 = r7.getApplicationInfo()
            int r7 = r7.uid
            android.os.UserHandle r7 = android.os.UserHandle.getUserHandleForUid(r7)
            r6.sendBroadcastAsUser(r0, r7)
            return
        L_0x0206:
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r11]
            if (r6 != r12) goto L_0x020e
            r6 = r12
            goto L_0x020f
        L_0x020e:
            r6 = r11
        L_0x020f:
            r0.highChassisSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r12]
            r0.airMaticStatus = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r7]
            if (r6 != r12) goto L_0x021f
            r6 = r12
            goto L_0x0220
        L_0x021f:
            r6 = r11
        L_0x0220:
            r0.auxiliaryRadar = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r14]
            r0.light1 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r15]
            r0.light2 = r6
            com.wits.pms.statuscontrol.McuStatus$BenzData r0 = r4.benzData
            byte r6 = r5[r10]
            if (r6 != r12) goto L_0x0236
            r11 = r12
        L_0x0236:
            r0.airBagSystem = r11
            goto L_0x0703
        L_0x023a:
            goto L_0x0703
        L_0x023c:
            r0 = 40
            byte[] r0 = new byte[r0]
            int r6 = r0.length
            java.lang.System.arraycopy(r5, r11, r0, r11, r6)
            java.lang.String r6 = new java.lang.String
            r6.<init>(r0)
            r4.mcuVerison = r6
            goto L_0x0703
        L_0x024d:
            byte r0 = r5[r11]
            if (r0 != r15) goto L_0x025c
            byte r0 = r5[r12]
            if (r0 != r12) goto L_0x025c
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.closeScreen(r11)
        L_0x025c:
            byte r0 = r5[r11]
            if (r0 > r14) goto L_0x0267
            byte r0 = r5[r12]
            r3.setCcd(r0)
            goto L_0x0703
        L_0x0267:
            byte r0 = r5[r11]
            if (r0 != r10) goto L_0x0703
            byte r0 = r5[r12]
            r3.setAcc(r0)
            goto L_0x0703
        L_0x0272:
            byte r0 = r5[r11]
            if (r0 != r12) goto L_0x0278
            r11 = r12
        L_0x0278:
            r0 = r11
            if (r0 == 0) goto L_0x0285
            r3.setAcc(r12)
            r6 = 500(0x1f4, double:2.47E-321)
            com.wits.pms.mcu.custom.utils.AccLight.show(r6)
            goto L_0x0703
        L_0x0285:
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$dnBl0E8Va6LDu6VpvpqujA_FbHU
            r7.<init>()
            r8 = 1000(0x3e8, double:4.94E-321)
            r6.postDelayed(r7, r8)
            goto L_0x0703
        L_0x0293:
            byte r0 = r5[r11]
            if (r0 != r15) goto L_0x0703
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            if (r0 == 0) goto L_0x02a8
            java.io.File r0 = com.wits.pms.mcu.custom.utils.ForceMcuUpdate.mcuFile
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x02a8
            android.content.Context r0 = r1.mContext
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.fix(r0)
        L_0x02a8:
            com.wits.pms.mcu.custom.utils.ForceMcuUpdate.NEEDFIX = r12
            goto L_0x0703
        L_0x02ac:
            byte r0 = r5[r11]
            r6 = 16
            if (r0 == r6) goto L_0x0647
            r6 = 18
            if (r0 == r6) goto L_0x063b
            r6 = 28
            r18 = 15
            if (r0 == r6) goto L_0x05ac
            r11 = 7
            switch(r0) {
                case 23: goto L_0x044e;
                case 24: goto L_0x0427;
                case 25: goto L_0x034b;
                case 26: goto L_0x02c2;
                default: goto L_0x02c0;
            }
        L_0x02c0:
            goto L_0x0645
        L_0x02c2:
            byte r0 = r5[r12]
            if (r0 != r7) goto L_0x02f0
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.stopMedia()
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x02ef }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x02ef }
            java.lang.String r6 = "CarDisplay"
            int r0 = android.provider.Settings.System.getInt(r0, r6)     // Catch:{ SettingNotFoundException -> 0x02ef }
            if (r0 != 0) goto L_0x02ee
            android.content.Intent r0 = new android.content.Intent     // Catch:{ SettingNotFoundException -> 0x02ef }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x02ef }
            java.lang.Class<com.wits.pms.ClockActivity> r7 = com.wits.pms.ClockActivity.class
            r0.<init>((android.content.Context) r6, (java.lang.Class<?>) r7)     // Catch:{ SettingNotFoundException -> 0x02ef }
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r0.setFlags(r6)     // Catch:{ SettingNotFoundException -> 0x02ef }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x02ef }
            r6.startActivity(r0)     // Catch:{ SettingNotFoundException -> 0x02ef }
        L_0x02ee:
            goto L_0x02f0
        L_0x02ef:
            r0 = move-exception
        L_0x02f0:
            byte r0 = r5[r12]
            r4.systemMode = r0
            int r0 = r4.systemMode
            if (r0 != r12) goto L_0x0645
            int r0 = r3.lastMode
            if (r0 == r9) goto L_0x0645
            int r0 = r3.lastMode
            if (r0 == r8) goto L_0x0645
            int r0 = r3.lastMode
            if (r0 == r10) goto L_0x0645
            int r0 = r3.lastMode
            r6 = 11
            if (r0 == r6) goto L_0x0645
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0645
            java.lang.String r0 = "vendor.wits.zlink.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "2"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0645
            java.lang.String r0 = "vendor.wits.autobox.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0645
            java.lang.String r0 = "vendor.wits.autobox.call"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "2"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x0645
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.home()
            goto L_0x0645
        L_0x034b:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r12]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            byte r12 = r5[r7]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r12
            r0.mileage = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r14]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            byte r12 = r5[r15]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r12
            float r6 = (float) r6
            r12 = 1092616192(0x41200000, float:10.0)
            float r6 = r6 / r12
            r0.oilWear = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r10]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            byte r9 = r5[r9]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            float r6 = (float) r6
            r9 = 1092616192(0x41200000, float:10.0)
            float r6 = r6 / r9
            r0.averSpeed = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r11]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            byte r9 = r5[r13]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            r0.speed = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r8]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            r8 = 10
            byte r8 = r5[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r8
            r0.engineTurnS = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 11
            byte r6 = r5[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << r13
            r8 = 12
            byte r8 = r5[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r8
            r0.oilSum = r6
            r0 = 13
            byte r6 = r5[r0]
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 <= 0) goto L_0x03d6
            byte r0 = r5[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r0 = r0 + r6
            int r0 = ~r0
            r6 = 1
            int r0 = r0 + r6
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r8 = 65535(0xffff, float:9.1834E-41)
            r8 = r8 & r0
            double r8 = (double) r8
            r10 = -4631501856787818086(0xbfb999999999999a, double:-0.1)
            double r8 = r8 * r10
            float r8 = (float) r8
            r6.airTemperature = r8
            goto L_0x03f5
        L_0x03d6:
            r0 = 13
            byte r0 = r5[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 * 256
            r6 = 14
            byte r6 = r5[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r0 = r0 + r6
            com.wits.pms.statuscontrol.McuStatus$CarData r6 = r4.carData
            r8 = 65535(0xffff, float:9.1834E-41)
            r8 = r8 & r0
            double r8 = (double) r8
            r10 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            double r8 = r8 * r10
            float r8 = (float) r8
            r6.airTemperature = r8
        L_0x03f5:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r18]
            r6 = r6 & r13
            r0.distanceUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r18]
            r6 = r6 & r7
            r0.oilUnitType = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            int r6 = r0.oilUnitType
            byte r7 = r5[r18]
            r7 = r7 & 255(0xff, float:3.57E-43)
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
        L_0x0427:
            byte[] r0 = new byte[r11]
            int r6 = r0.length
            r8 = 1
            r11 = 0
            java.lang.System.arraycopy(r5, r8, r0, r11, r6)
            byte r6 = r0[r9]
            if (r6 == 0) goto L_0x0434
            goto L_0x0435
        L_0x0434:
        L_0x0435:
            android.content.Context r6 = r1.mContext
            byte r9 = r0[r11]
            r9 = r9 & 255(0xff, float:3.57E-43)
            byte r18 = r0[r8]
            byte r19 = r0[r7]
            byte r20 = r0[r14]
            byte r21 = r0[r15]
            byte r22 = r0[r10]
            r16 = r6
            r17 = r9
            com.wits.pms.utils.TimeSetting.setTime(r16, r17, r18, r19, r20, r21, r22)
            goto L_0x0645
        L_0x044e:
            byte r0 = r5[r7]
            r6 = 1
            if (r0 == r6) goto L_0x0454
            return
        L_0x0454:
            byte r0 = r5[r6]
            r6 = 13
            if (r0 == r6) goto L_0x05a2
            r6 = 20
            if (r0 == r6) goto L_0x0595
            switch(r0) {
                case 3: goto L_0x058b;
                case 4: goto L_0x0581;
                case 5: goto L_0x0577;
                case 6: goto L_0x053c;
                case 7: goto L_0x0502;
                default: goto L_0x0461;
            }
        L_0x0461:
            switch(r0) {
                case 30: goto L_0x04b4;
                case 31: goto L_0x0466;
                default: goto L_0x0464;
            }
        L_0x0464:
            goto L_0x05aa
        L_0x0466:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x047d
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x05aa
        L_0x047d:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0496
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.rejectPhone()
            goto L_0x05aa
        L_0x0496:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.utils.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x04ab
            r0 = 112(0x70, float:1.57E-43)
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r11, r0)
            goto L_0x05aa
        L_0x04ab:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.handUpPhone()
            goto L_0x05aa
        L_0x04b4:
            java.lang.String r0 = "vendor.wits.zlink.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x04cb
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.zlinkHandleCall()
            goto L_0x05aa
        L_0x04cb:
            java.lang.String r0 = "vendor.wits.autobox.connected"
            java.lang.String r0 = com.wits.pms.utils.SystemProperties.get(r0)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x04e4
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.acceptPhone()
            goto L_0x05aa
        L_0x04e4:
            java.lang.String r0 = "true"
            java.lang.String r6 = "persist.sys.hicar_connect"
            java.lang.String r6 = com.wits.pms.utils.SystemProperties.get(r6)
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x04f9
            r0 = 111(0x6f, float:1.56E-43)
            com.wits.pms.statuscontrol.WitsCommand.sendCommand(r11, r0)
            goto L_0x05aa
        L_0x04f9:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.acceptPhone()
            goto L_0x05aa
        L_0x0502:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapLeft()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x051a
            return
        L_0x051a:
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
            goto L_0x05aa
        L_0x053c:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapRight()
            com.wits.pms.statuscontrol.SystemStatus r0 = com.wits.pms.core.SystemStatusControl.getStatus()
            java.lang.String r0 = r0.topApp
            java.lang.String r6 = "autonavi"
            boolean r0 = r0.contains(r6)
            if (r0 != 0) goto L_0x0554
            return
        L_0x0554:
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
            r7 = 0
            r0.putExtra((java.lang.String) r6, (int) r7)
            android.content.Context r6 = r1.mContext
            r6.sendBroadcast(r0)
            goto L_0x05aa
        L_0x0577:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.enter()
            goto L_0x05aa
        L_0x0581:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapDown()
            goto L_0x05aa
        L_0x058b:
            android.content.Context r0 = r1.mContext
            com.wits.pms.receiver.AutoKitCallBackImpl r0 = com.wits.pms.receiver.AutoKitCallBackImpl.getImpl(r0)
            r0.drapUp()
            goto L_0x05aa
        L_0x0595:
            android.content.Context r0 = r1.mContext
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = "start.txz.ksw"
            r6.<init>((java.lang.String) r7)
            r0.sendBroadcast(r6)
            goto L_0x05aa
        L_0x05a2:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            r0.openSettings()
        L_0x05aa:
            goto L_0x0645
        L_0x05ac:
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 1
            byte r8 = r5[r6]
            r6 = r8 & 128(0x80, float:1.794E-43)
            if (r6 == 0) goto L_0x05b7
            r6 = 1
            goto L_0x05b8
        L_0x05b7:
            r6 = 0
        L_0x05b8:
            r0.isOpen = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 1
            byte r8 = r5[r6]
            r6 = r8 & 64
            if (r6 == 0) goto L_0x05c5
            r6 = 1
            goto L_0x05c6
        L_0x05c5:
            r6 = 0
        L_0x05c6:
            r0.AC_Switch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r6 = 1
            byte r8 = r5[r6]
            r6 = r8 & 32
            if (r6 == 0) goto L_0x05d3
            r6 = 1
            goto L_0x05d4
        L_0x05d3:
            r6 = 0
        L_0x05d4:
            r0.loop = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            r21 = 1
            byte r6 = r5[r21]
            r6 = r6 & r13
            if (r6 == 0) goto L_0x05e2
            r6 = r21
            goto L_0x05e3
        L_0x05e2:
            r6 = 0
        L_0x05e3:
            r0.frontMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r21]
            r6 = r6 & r7
            if (r6 == 0) goto L_0x05ef
            r6 = r21
            goto L_0x05f0
        L_0x05ef:
            r6 = 0
        L_0x05f0:
            r0.backMistSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r21]
            r6 = r6 & 1
            if (r6 == 0) goto L_0x05fc
            r6 = 1
            goto L_0x05fd
        L_0x05fc:
            r6 = 0
        L_0x05fd:
            r0.sync = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r7]
            r0.mode = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r14]
            r0.setLeftTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r15]
            r0.setRightTmp((int) r6)
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r10]
            r6 = r6 & 32
            if (r6 == 0) goto L_0x061d
            r6 = 1
            goto L_0x061e
        L_0x061d:
            r6 = 0
        L_0x061e:
            r0.eco = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r10]
            r7 = 16
            r6 = r6 & r7
            if (r6 == 0) goto L_0x062b
            r6 = 1
            goto L_0x062c
        L_0x062b:
            r6 = 0
        L_0x062c:
            r0.autoSwitch = r6
            com.wits.pms.statuscontrol.McuStatus$ACData r0 = r4.acData
            byte r6 = r5[r10]
            r6 = r6 & 15
            float r6 = (float) r6
            r7 = 1056964608(0x3f000000, float:0.5)
            float r6 = r6 * r7
            r0.speed = r6
            goto L_0x0645
        L_0x063b:
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            r6 = 1
            byte r6 = r5[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            r0.carDoor = r6
        L_0x0645:
            goto L_0x0703
        L_0x0647:
            r6 = r12
            byte r0 = r5[r6]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r0 = r0 & r6
            r3.ill = r0
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r8 = r5[r6]
            r6 = r8 & 255(0xff, float:3.57E-43)
            r6 = r6 & r13
            if (r6 == 0) goto L_0x065a
            r6 = 1
            goto L_0x065b
        L_0x065a:
            r6 = 0
        L_0x065b:
            r0.handbrake = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            r8 = 1
            r6 = r6 & r8
            if (r6 == 0) goto L_0x0669
            r6 = 1
            goto L_0x066a
        L_0x0669:
            r6 = 0
        L_0x066a:
            r0.safetyBelt = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            r6 = r6 & r9
            r0.carGear = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            r6 = r6 & r13
            r0.signalLeft = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            r8 = 16
            r6 = r6 & r8
            r0.signalRight = r6
            com.wits.pms.statuscontrol.McuStatus$CarData r0 = r4.carData
            byte r6 = r5[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
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
        L_0x06aa:
            int r0 = r5.length
            if (r0 < r8) goto L_0x0703
            com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.getSettings()
            byte r6 = r5[r13]
            r0.setUpProtocolForMcuListen(r6)
            goto L_0x0703
        L_0x06b7:
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
            r21 = 0
            byte r0 = r5[r21]
            if (r0 != r7) goto L_0x0703
            byte r0 = r5[r7]
            if (r0 == 0) goto L_0x06ef
            byte r0 = r5[r7]
            if (r0 == r9) goto L_0x06ef
            byte r0 = r5[r7]
            if (r0 == r8) goto L_0x06ef
            byte r0 = r5[r7]
            if (r0 == r10) goto L_0x06ef
            byte r0 = r5[r7]
            r6 = 11
            if (r0 == r6) goto L_0x06ef
            r21 = r7
        L_0x06ef:
            r0 = r21
            android.os.Handler r6 = r1.mHandler
            com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$2iFjihNkqO3GLFrGC04t-xzetM4 r7 = new com.wits.pms.mcu.custom.-$$Lambda$KswMcuListener$2iFjihNkqO3GLFrGC04t-xzetM4
            r7.<init>(r3, r5)
            if (r0 == 0) goto L_0x06fd
            r8 = 5000(0x1388, double:2.4703E-320)
            goto L_0x06ff
        L_0x06fd:
            r8 = 0
        L_0x06ff:
            r6.postDelayed(r7, r8)
        L_0x0703:
            com.wits.pms.core.SystemStatusControl r0 = com.wits.pms.core.SystemStatusControl.getDefault()
            r0.handle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.mcu.custom.KswMcuListener.onMcuMessage(com.wits.pms.mcu.custom.KswMessage):void");
    }

    public static /* synthetic */ void lambda$onMcuMessage$0(KswMcuListener kswMcuListener, SystemStatus systemStatus, byte[] data) {
        systemStatus.lastMode = data[1];
        int i = 0;
        switch (data[1]) {
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CenterControlImpl.getImpl().openMusic(true);
                return;
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
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                CenterControlImpl.getImpl().openVideo(true);
                return;
            case 3:
                CenterControlImpl.getImpl().openBluetooth(true);
                return;
            case 5:
                try {
                    if (Settings.System.getInt(kswMcuListener.mContext.getContentResolver(), "DVR_Type") == 1) {
                        kswMcuListener.mMcuSender.sendMessage(103, new byte[]{5});
                        return;
                    }
                    return;
                } catch (Settings.SettingNotFoundException e3) {
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
