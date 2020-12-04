package android.hardware.camera2.legacy;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Handler;
import android.util.Log;
import com.android.internal.telephony.IccCardConstants;

public class CameraDeviceState {
    private static final boolean DEBUG = false;
    public static final int NO_CAPTURE_ERROR = -1;
    private static final int STATE_CAPTURING = 4;
    private static final int STATE_CONFIGURING = 2;
    private static final int STATE_ERROR = 0;
    private static final int STATE_IDLE = 3;
    private static final int STATE_UNCONFIGURED = 1;
    private static final String TAG = "CameraDeviceState";
    private static final String[] sStateNames = {"ERROR", "UNCONFIGURED", "CONFIGURING", "IDLE", "CAPTURING"};
    /* access modifiers changed from: private */
    public int mCurrentError = -1;
    private Handler mCurrentHandler = null;
    /* access modifiers changed from: private */
    public CameraDeviceStateListener mCurrentListener = null;
    /* access modifiers changed from: private */
    public RequestHolder mCurrentRequest = null;
    private int mCurrentState = 1;

    public interface CameraDeviceStateListener {
        void onBusy();

        void onCaptureResult(CameraMetadataNative cameraMetadataNative, RequestHolder requestHolder);

        void onCaptureStarted(RequestHolder requestHolder, long j);

        void onConfiguring();

        void onError(int i, Object obj, RequestHolder requestHolder);

        void onIdle();

        void onRepeatingRequestError(long j, int i);

        void onRequestQueueEmpty();
    }

    public synchronized void setError(int error) {
        this.mCurrentError = error;
        doStateTransition(0);
    }

    public synchronized boolean setConfiguring() {
        doStateTransition(2);
        return this.mCurrentError == -1;
    }

    public synchronized boolean setIdle() {
        doStateTransition(3);
        return this.mCurrentError == -1;
    }

    public synchronized boolean setCaptureStart(RequestHolder request, long timestamp, int captureError) {
        this.mCurrentRequest = request;
        doStateTransition(4, timestamp, captureError);
        return this.mCurrentError == -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002d, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean setCaptureResult(final android.hardware.camera2.legacy.RequestHolder r7, final android.hardware.camera2.impl.CameraMetadataNative r8, final int r9, final java.lang.Object r10) {
        /*
            r6 = this;
            monitor-enter(r6)
            int r0 = r6.mCurrentState     // Catch:{ all -> 0x0055 }
            r1 = 4
            r2 = 0
            r3 = 1
            r4 = -1
            if (r0 == r1) goto L_0x002e
            java.lang.String r0 = "CameraDeviceState"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0055 }
            r1.<init>()     // Catch:{ all -> 0x0055 }
            java.lang.String r5 = "Cannot receive result while in state: "
            r1.append(r5)     // Catch:{ all -> 0x0055 }
            int r5 = r6.mCurrentState     // Catch:{ all -> 0x0055 }
            r1.append(r5)     // Catch:{ all -> 0x0055 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0055 }
            android.util.Log.e(r0, r1)     // Catch:{ all -> 0x0055 }
            r6.mCurrentError = r3     // Catch:{ all -> 0x0055 }
            r6.doStateTransition(r2)     // Catch:{ all -> 0x0055 }
            int r0 = r6.mCurrentError     // Catch:{ all -> 0x0055 }
            if (r0 != r4) goto L_0x002c
            r2 = r3
        L_0x002c:
            monitor-exit(r6)
            return r2
        L_0x002e:
            android.os.Handler r0 = r6.mCurrentHandler     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x004d
            android.hardware.camera2.legacy.CameraDeviceState$CameraDeviceStateListener r0 = r6.mCurrentListener     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x004d
            if (r9 == r4) goto L_0x0043
            android.os.Handler r0 = r6.mCurrentHandler     // Catch:{ all -> 0x0055 }
            android.hardware.camera2.legacy.CameraDeviceState$1 r1 = new android.hardware.camera2.legacy.CameraDeviceState$1     // Catch:{ all -> 0x0055 }
            r1.<init>(r9, r10, r7)     // Catch:{ all -> 0x0055 }
            r0.post(r1)     // Catch:{ all -> 0x0055 }
            goto L_0x004d
        L_0x0043:
            android.os.Handler r0 = r6.mCurrentHandler     // Catch:{ all -> 0x0055 }
            android.hardware.camera2.legacy.CameraDeviceState$2 r1 = new android.hardware.camera2.legacy.CameraDeviceState$2     // Catch:{ all -> 0x0055 }
            r1.<init>(r8, r7)     // Catch:{ all -> 0x0055 }
            r0.post(r1)     // Catch:{ all -> 0x0055 }
        L_0x004d:
            int r0 = r6.mCurrentError     // Catch:{ all -> 0x0055 }
            if (r0 != r4) goto L_0x0053
            r2 = r3
        L_0x0053:
            monitor-exit(r6)
            return r2
        L_0x0055:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.legacy.CameraDeviceState.setCaptureResult(android.hardware.camera2.legacy.RequestHolder, android.hardware.camera2.impl.CameraMetadataNative, int, java.lang.Object):boolean");
    }

    public synchronized boolean setCaptureResult(RequestHolder request, CameraMetadataNative result) {
        return setCaptureResult(request, result, -1, (Object) null);
    }

    public synchronized void setRepeatingRequestError(final long lastFrameNumber, final int repeatingRequestId) {
        this.mCurrentHandler.post(new Runnable() {
            public void run() {
                CameraDeviceState.this.mCurrentListener.onRepeatingRequestError(lastFrameNumber, repeatingRequestId);
            }
        });
    }

    public synchronized void setRequestQueueEmpty() {
        this.mCurrentHandler.post(new Runnable() {
            public void run() {
                CameraDeviceState.this.mCurrentListener.onRequestQueueEmpty();
            }
        });
    }

    public synchronized void setCameraDeviceCallbacks(Handler handler, CameraDeviceStateListener listener) {
        this.mCurrentHandler = handler;
        this.mCurrentListener = listener;
    }

    private void doStateTransition(int newState) {
        doStateTransition(newState, 0, -1);
    }

    private void doStateTransition(int newState, final long timestamp, final int error) {
        if (newState != this.mCurrentState) {
            String stateName = IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
            if (newState >= 0 && newState < sStateNames.length) {
                stateName = sStateNames[newState];
            }
            Log.i(TAG, "Legacy camera service transitioning to state " + stateName);
        }
        if (!(newState == 0 || newState == 3 || this.mCurrentState == newState || this.mCurrentHandler == null || this.mCurrentListener == null)) {
            this.mCurrentHandler.post(new Runnable() {
                public void run() {
                    CameraDeviceState.this.mCurrentListener.onBusy();
                }
            });
        }
        if (newState != 0) {
            switch (newState) {
                case 2:
                    if (this.mCurrentState == 1 || this.mCurrentState == 3) {
                        if (!(this.mCurrentState == 2 || this.mCurrentHandler == null || this.mCurrentListener == null)) {
                            this.mCurrentHandler.post(new Runnable() {
                                public void run() {
                                    CameraDeviceState.this.mCurrentListener.onConfiguring();
                                }
                            });
                        }
                        this.mCurrentState = 2;
                        return;
                    }
                    Log.e(TAG, "Cannot call configure while in state: " + this.mCurrentState);
                    this.mCurrentError = 1;
                    doStateTransition(0);
                    return;
                case 3:
                    if (this.mCurrentState != 3) {
                        if (this.mCurrentState == 2 || this.mCurrentState == 4) {
                            if (!(this.mCurrentState == 3 || this.mCurrentHandler == null || this.mCurrentListener == null)) {
                                this.mCurrentHandler.post(new Runnable() {
                                    public void run() {
                                        CameraDeviceState.this.mCurrentListener.onIdle();
                                    }
                                });
                            }
                            this.mCurrentState = 3;
                            return;
                        }
                        Log.e(TAG, "Cannot call idle while in state: " + this.mCurrentState);
                        this.mCurrentError = 1;
                        doStateTransition(0);
                        return;
                    }
                    return;
                case 4:
                    if (this.mCurrentState == 3 || this.mCurrentState == 4) {
                        if (!(this.mCurrentHandler == null || this.mCurrentListener == null)) {
                            if (error != -1) {
                                this.mCurrentHandler.post(new Runnable() {
                                    public void run() {
                                        CameraDeviceState.this.mCurrentListener.onError(error, (Object) null, CameraDeviceState.this.mCurrentRequest);
                                    }
                                });
                            } else {
                                this.mCurrentHandler.post(new Runnable() {
                                    public void run() {
                                        CameraDeviceState.this.mCurrentListener.onCaptureStarted(CameraDeviceState.this.mCurrentRequest, timestamp);
                                    }
                                });
                            }
                        }
                        this.mCurrentState = 4;
                        return;
                    }
                    Log.e(TAG, "Cannot call capture while in state: " + this.mCurrentState);
                    this.mCurrentError = 1;
                    doStateTransition(0);
                    return;
                default:
                    throw new IllegalStateException("Transition to unknown state: " + newState);
            }
        } else {
            if (!(this.mCurrentState == 0 || this.mCurrentHandler == null || this.mCurrentListener == null)) {
                this.mCurrentHandler.post(new Runnable() {
                    public void run() {
                        CameraDeviceState.this.mCurrentListener.onError(CameraDeviceState.this.mCurrentError, (Object) null, CameraDeviceState.this.mCurrentRequest);
                    }
                });
            }
            this.mCurrentState = 0;
        }
    }
}
