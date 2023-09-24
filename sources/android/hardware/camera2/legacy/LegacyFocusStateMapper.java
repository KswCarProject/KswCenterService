package android.hardware.camera2.legacy;

import android.hardware.Camera;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.utils.ParamsUtils;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes.dex */
public class LegacyFocusStateMapper {
    private static final boolean DEBUG = false;
    private static String TAG = "LegacyFocusStateMapper";
    private final Camera mCamera;
    private int mAfStatePrevious = 0;
    private String mAfModePrevious = null;
    private final Object mLock = new Object();
    private int mAfRun = 0;
    private int mAfState = 0;

    public LegacyFocusStateMapper(Camera camera) {
        this.mCamera = (Camera) Preconditions.checkNotNull(camera, "camera must not be null");
    }

    public void processRequestTriggers(CaptureRequest captureRequest, Camera.Parameters parameters) {
        final int currentAfRun;
        boolean z;
        final int currentAfRun2;
        Preconditions.checkNotNull(captureRequest, "captureRequest must not be null");
        int afStateAfterStart = 0;
        int afTrigger = ((Integer) ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_AF_TRIGGER, 0)).intValue();
        final String afMode = parameters.getFocusMode();
        if (!Objects.equals(this.mAfModePrevious, afMode)) {
            synchronized (this.mLock) {
                this.mAfRun++;
                this.mAfState = 0;
            }
            this.mCamera.cancelAutoFocus();
        }
        this.mAfModePrevious = afMode;
        synchronized (this.mLock) {
            currentAfRun = this.mAfRun;
        }
        Camera.AutoFocusMoveCallback afMoveCallback = new Camera.AutoFocusMoveCallback() { // from class: android.hardware.camera2.legacy.LegacyFocusStateMapper.1
            /* JADX WARN: Removed duplicated region for block: B:26:0x005e A[Catch: all -> 0x0082, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x0011, B:7:0x002c, B:13:0x0035, B:25:0x005b, B:26:0x005e, B:28:0x0064, B:29:0x007b, B:30:0x0080, B:18:0x0047, B:21:0x0051), top: B:35:0x0007 }] */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0063  */
            @Override // android.hardware.Camera.AutoFocusMoveCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onAutoFocusMoving(boolean start, Camera camera) {
                synchronized (LegacyFocusStateMapper.this.mLock) {
                    int latestAfRun = LegacyFocusStateMapper.this.mAfRun;
                    if (currentAfRun != latestAfRun) {
                        Log.m72d(LegacyFocusStateMapper.TAG, "onAutoFocusMoving - ignoring move callbacks from old af run" + currentAfRun);
                        return;
                    }
                    boolean z2 = true;
                    int newAfState = start ? 1 : 2;
                    String str = afMode;
                    int hashCode = str.hashCode();
                    if (hashCode != -194628547) {
                        if (hashCode == 910005312 && str.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                            z2 = false;
                            switch (z2) {
                                case false:
                                case true:
                                    break;
                                default:
                                    Log.m64w(LegacyFocusStateMapper.TAG, "onAutoFocus - got unexpected onAutoFocus in mode " + afMode);
                                    break;
                            }
                            LegacyFocusStateMapper.this.mAfState = newAfState;
                        }
                        z2 = true;
                        switch (z2) {
                        }
                        LegacyFocusStateMapper.this.mAfState = newAfState;
                    }
                    if (str.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        switch (z2) {
                        }
                        LegacyFocusStateMapper.this.mAfState = newAfState;
                    }
                    z2 = true;
                    switch (z2) {
                    }
                    LegacyFocusStateMapper.this.mAfState = newAfState;
                }
            }
        };
        int hashCode = afMode.hashCode();
        char c = '\uffff';
        if (hashCode == -194628547) {
            if (afMode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                z = true;
            }
            z = true;
        } else if (hashCode == 3005871) {
            if (afMode.equals("auto")) {
                z = false;
            }
            z = true;
        } else if (hashCode != 103652300) {
            if (hashCode == 910005312 && afMode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                z = true;
            }
            z = true;
        } else {
            if (afMode.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {
                z = true;
            }
            z = true;
        }
        switch (z) {
            case false:
            case true:
            case true:
            case true:
                this.mCamera.setAutoFocusMoveCallback(afMoveCallback);
                break;
        }
        switch (afTrigger) {
            case 0:
                return;
            case 1:
                int hashCode2 = afMode.hashCode();
                if (hashCode2 != -194628547) {
                    if (hashCode2 != 3005871) {
                        if (hashCode2 != 103652300) {
                            if (hashCode2 == 910005312 && afMode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                                c = 2;
                            }
                        } else if (afMode.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {
                            c = 1;
                        }
                    } else if (afMode.equals("auto")) {
                        c = 0;
                    }
                } else if (afMode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                    c = 3;
                }
                switch (c) {
                    case 0:
                    case 1:
                        afStateAfterStart = 3;
                        break;
                    case 2:
                    case 3:
                        afStateAfterStart = 1;
                        break;
                }
                synchronized (this.mLock) {
                    currentAfRun2 = this.mAfRun + 1;
                    this.mAfRun = currentAfRun2;
                    this.mAfState = afStateAfterStart;
                }
                if (afStateAfterStart != 0) {
                    this.mCamera.autoFocus(new Camera.AutoFocusCallback() { // from class: android.hardware.camera2.legacy.LegacyFocusStateMapper.2
                        /* JADX WARN: Removed duplicated region for block: B:35:0x0080 A[Catch: all -> 0x00a4, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x0014, B:7:0x0032, B:12:0x0039, B:34:0x007d, B:35:0x0080, B:37:0x0086, B:38:0x009d, B:39:0x00a2, B:21:0x0055, B:24:0x005e, B:27:0x0068, B:30:0x0072), top: B:44:0x0007 }] */
                        /* JADX WARN: Removed duplicated region for block: B:36:0x0085  */
                        @Override // android.hardware.Camera.AutoFocusCallback
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void onAutoFocus(boolean success, Camera camera) {
                            int newAfState;
                            synchronized (LegacyFocusStateMapper.this.mLock) {
                                int latestAfRun = LegacyFocusStateMapper.this.mAfRun;
                                char c2 = 1;
                                if (latestAfRun != currentAfRun2) {
                                    Log.m72d(LegacyFocusStateMapper.TAG, String.format("onAutoFocus - ignoring AF callback (old run %d, new run %d)", Integer.valueOf(currentAfRun2), Integer.valueOf(latestAfRun)));
                                    return;
                                }
                                if (success) {
                                    newAfState = 4;
                                } else {
                                    newAfState = 5;
                                }
                                String str = afMode;
                                int hashCode3 = str.hashCode();
                                if (hashCode3 == -194628547) {
                                    if (str.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                                        c2 = 2;
                                        switch (c2) {
                                        }
                                        LegacyFocusStateMapper.this.mAfState = newAfState;
                                    }
                                    c2 = '\uffff';
                                    switch (c2) {
                                    }
                                    LegacyFocusStateMapper.this.mAfState = newAfState;
                                } else if (hashCode3 == 3005871) {
                                    if (str.equals("auto")) {
                                        c2 = 0;
                                        switch (c2) {
                                        }
                                        LegacyFocusStateMapper.this.mAfState = newAfState;
                                    }
                                    c2 = '\uffff';
                                    switch (c2) {
                                    }
                                    LegacyFocusStateMapper.this.mAfState = newAfState;
                                } else if (hashCode3 != 103652300) {
                                    if (hashCode3 == 910005312 && str.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                                        switch (c2) {
                                            case 0:
                                            case 1:
                                            case 2:
                                            case 3:
                                                break;
                                            default:
                                                Log.m64w(LegacyFocusStateMapper.TAG, "onAutoFocus - got unexpected onAutoFocus in mode " + afMode);
                                                break;
                                        }
                                        LegacyFocusStateMapper.this.mAfState = newAfState;
                                    }
                                    c2 = '\uffff';
                                    switch (c2) {
                                    }
                                    LegacyFocusStateMapper.this.mAfState = newAfState;
                                } else {
                                    if (str.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {
                                        c2 = 3;
                                        switch (c2) {
                                        }
                                        LegacyFocusStateMapper.this.mAfState = newAfState;
                                    }
                                    c2 = '\uffff';
                                    switch (c2) {
                                    }
                                    LegacyFocusStateMapper.this.mAfState = newAfState;
                                }
                            }
                        }
                    });
                    return;
                }
                return;
            case 2:
                synchronized (this.mLock) {
                    synchronized (this.mLock) {
                        this.mAfRun++;
                        this.mAfState = 0;
                    }
                    this.mCamera.cancelAutoFocus();
                }
                return;
            default:
                Log.m64w(TAG, "processRequestTriggers - ignoring unknown control.afTrigger = " + afTrigger);
                return;
        }
    }

    public void mapResultTriggers(CameraMetadataNative result) {
        int newAfState;
        Preconditions.checkNotNull(result, "result must not be null");
        synchronized (this.mLock) {
            newAfState = this.mAfState;
        }
        result.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.CONTROL_AF_STATE, (CaptureResult.Key<Integer>) Integer.valueOf(newAfState));
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
