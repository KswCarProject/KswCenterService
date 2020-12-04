package android.hardware.camera2.legacy;

import android.hardware.Camera;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import com.android.internal.util.Preconditions;

public class LegacyFocusStateMapper {
    private static final boolean DEBUG = false;
    /* access modifiers changed from: private */
    public static String TAG = "LegacyFocusStateMapper";
    private String mAfModePrevious = null;
    /* access modifiers changed from: private */
    public int mAfRun = 0;
    /* access modifiers changed from: private */
    public int mAfState = 0;
    private int mAfStatePrevious = 0;
    private final Camera mCamera;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();

    public LegacyFocusStateMapper(Camera camera) {
        this.mCamera = (Camera) Preconditions.checkNotNull(camera, "camera must not be null");
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processRequestTriggers(android.hardware.camera2.CaptureRequest r17, android.hardware.Camera.Parameters r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            java.lang.String r0 = "captureRequest must not be null"
            com.android.internal.util.Preconditions.checkNotNull(r2, r0)
            android.hardware.camera2.CaptureRequest$Key<java.lang.Integer> r0 = android.hardware.camera2.CaptureRequest.CONTROL_AF_TRIGGER
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            java.lang.Object r0 = android.hardware.camera2.utils.ParamsUtils.getOrDefault(r2, r0, r4)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r4 = r0.intValue()
            java.lang.String r5 = r18.getFocusMode()
            java.lang.String r0 = r1.mAfModePrevious
            boolean r0 = java.util.Objects.equals(r0, r5)
            r6 = 1
            if (r0 != 0) goto L_0x003b
            java.lang.Object r7 = r1.mLock
            monitor-enter(r7)
            int r0 = r1.mAfRun     // Catch:{ all -> 0x0038 }
            int r0 = r0 + r6
            r1.mAfRun = r0     // Catch:{ all -> 0x0038 }
            r1.mAfState = r3     // Catch:{ all -> 0x0038 }
            monitor-exit(r7)     // Catch:{ all -> 0x0038 }
            android.hardware.Camera r0 = r1.mCamera
            r0.cancelAutoFocus()
            goto L_0x003b
        L_0x0038:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0038 }
            throw r0
        L_0x003b:
            r1.mAfModePrevious = r5
            java.lang.Object r7 = r1.mLock
            monitor-enter(r7)
            int r0 = r1.mAfRun     // Catch:{ all -> 0x0129 }
            monitor-exit(r7)     // Catch:{ all -> 0x0129 }
            android.hardware.camera2.legacy.LegacyFocusStateMapper$1 r7 = new android.hardware.camera2.legacy.LegacyFocusStateMapper$1
            r7.<init>(r0, r5)
            int r8 = r5.hashCode()
            r9 = 2
            r10 = 3
            r11 = 910005312(0x363d9440, float:2.8249488E-6)
            r12 = 103652300(0x62d9bcc, float:3.2652145E-35)
            r13 = 3005871(0x2dddaf, float:4.212122E-39)
            r14 = -194628547(0xfffffffff466343d, float:-7.2954577E31)
            r15 = -1
            if (r8 == r14) goto L_0x0082
            if (r8 == r13) goto L_0x0078
            if (r8 == r12) goto L_0x006e
            if (r8 == r11) goto L_0x0064
            goto L_0x008c
        L_0x0064:
            java.lang.String r8 = "continuous-picture"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x008c
            r8 = r9
            goto L_0x008d
        L_0x006e:
            java.lang.String r8 = "macro"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x008c
            r8 = r6
            goto L_0x008d
        L_0x0078:
            java.lang.String r8 = "auto"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x008c
            r8 = r3
            goto L_0x008d
        L_0x0082:
            java.lang.String r8 = "continuous-video"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x008c
            r8 = r10
            goto L_0x008d
        L_0x008c:
            r8 = r15
        L_0x008d:
            switch(r8) {
                case 0: goto L_0x0091;
                case 1: goto L_0x0091;
                case 2: goto L_0x0091;
                case 3: goto L_0x0091;
                default: goto L_0x0090;
            }
        L_0x0090:
            goto L_0x0096
        L_0x0091:
            android.hardware.Camera r8 = r1.mCamera
            r8.setAutoFocusMoveCallback(r7)
        L_0x0096:
            switch(r4) {
                case 0: goto L_0x0127;
                case 1: goto L_0x00ce;
                case 2: goto L_0x00b2;
                default: goto L_0x0099;
            }
        L_0x0099:
            java.lang.String r0 = TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "processRequestTriggers - ignoring unknown control.afTrigger = "
            r3.append(r6)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r3)
            goto L_0x0128
        L_0x00b2:
            java.lang.Object r7 = r1.mLock
            monitor-enter(r7)
            java.lang.Object r8 = r1.mLock     // Catch:{ all -> 0x00cb }
            monitor-enter(r8)     // Catch:{ all -> 0x00cb }
            int r0 = r1.mAfRun     // Catch:{ all -> 0x00c8 }
            int r0 = r0 + r6
            r1.mAfRun = r0     // Catch:{ all -> 0x00c8 }
            r1.mAfState = r3     // Catch:{ all -> 0x00c8 }
            monitor-exit(r8)     // Catch:{ all -> 0x00c8 }
            android.hardware.Camera r3 = r1.mCamera     // Catch:{ all -> 0x00cb }
            r3.cancelAutoFocus()     // Catch:{ all -> 0x00cb }
            monitor-exit(r7)     // Catch:{ all -> 0x00cb }
            goto L_0x0128
        L_0x00c8:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00c8 }
            throw r0     // Catch:{ all -> 0x00cb }
        L_0x00cb:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00cb }
            throw r0
        L_0x00ce:
            int r0 = r5.hashCode()
            if (r0 == r14) goto L_0x00f9
            if (r0 == r13) goto L_0x00ef
            if (r0 == r12) goto L_0x00e5
            if (r0 == r11) goto L_0x00db
            goto L_0x0102
        L_0x00db:
            java.lang.String r0 = "continuous-picture"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0102
            r15 = r9
            goto L_0x0102
        L_0x00e5:
            java.lang.String r0 = "macro"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0102
            r15 = r6
            goto L_0x0102
        L_0x00ef:
            java.lang.String r0 = "auto"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0102
            r15 = r3
            goto L_0x0102
        L_0x00f9:
            java.lang.String r0 = "continuous-video"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0102
            r15 = r10
        L_0x0102:
            switch(r15) {
                case 0: goto L_0x0108;
                case 1: goto L_0x0108;
                case 2: goto L_0x0106;
                case 3: goto L_0x0106;
                default: goto L_0x0105;
            }
        L_0x0105:
            goto L_0x010a
        L_0x0106:
            r3 = 1
            goto L_0x010a
        L_0x0108:
            r3 = 3
        L_0x010a:
            java.lang.Object r7 = r1.mLock
            monitor-enter(r7)
            int r0 = r1.mAfRun     // Catch:{ all -> 0x0124 }
            int r0 = r0 + r6
            r1.mAfRun = r0     // Catch:{ all -> 0x0124 }
            r1.mAfState = r3     // Catch:{ all -> 0x0124 }
            monitor-exit(r7)     // Catch:{ all -> 0x0124 }
            if (r3 != 0) goto L_0x0119
            goto L_0x0128
        L_0x0119:
            android.hardware.Camera r6 = r1.mCamera
            android.hardware.camera2.legacy.LegacyFocusStateMapper$2 r7 = new android.hardware.camera2.legacy.LegacyFocusStateMapper$2
            r7.<init>(r0, r5)
            r6.autoFocus(r7)
            goto L_0x0128
        L_0x0124:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0124 }
            throw r0
        L_0x0127:
        L_0x0128:
            return
        L_0x0129:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0129 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.legacy.LegacyFocusStateMapper.processRequestTriggers(android.hardware.camera2.CaptureRequest, android.hardware.Camera$Parameters):void");
    }

    public void mapResultTriggers(CameraMetadataNative result) {
        int newAfState;
        Preconditions.checkNotNull(result, "result must not be null");
        synchronized (this.mLock) {
            newAfState = this.mAfState;
        }
        result.set(CaptureResult.CONTROL_AF_STATE, Integer.valueOf(newAfState));
        this.mAfStatePrevious = newAfState;
    }

    private static String afStateToString(int afState) {
        switch (afState) {
            case 0:
                return "INACTIVE";
            case 1:
                return "PASSIVE_SCAN";
            case 2:
                return "PASSIVE_FOCUSED";
            case 3:
                return "ACTIVE_SCAN";
            case 4:
                return "FOCUSED_LOCKED";
            case 5:
                return "NOT_FOCUSED_LOCKED";
            case 6:
                return "PASSIVE_UNFOCUSED";
            default:
                return "UNKNOWN(" + afState + ")";
        }
    }
}
