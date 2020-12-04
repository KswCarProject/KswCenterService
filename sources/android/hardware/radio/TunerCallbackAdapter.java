package android.hardware.radio;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioTuner;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class TunerCallbackAdapter extends ITunerCallback.Stub {
    private static final String TAG = "BroadcastRadio.TunerCallbackAdapter";
    private final RadioTuner.Callback mCallback;
    RadioManager.ProgramInfo mCurrentProgramInfo;
    private boolean mDelayedCompleteCallback = false;
    private final Handler mHandler;
    boolean mIsAntennaConnected = true;
    List<RadioManager.ProgramInfo> mLastCompleteList;
    private final Object mLock = new Object();
    ProgramList mProgramList;

    TunerCallbackAdapter(RadioTuner.Callback callback, Handler handler) {
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        } else {
            this.mHandler = handler;
        }
    }

    /* access modifiers changed from: package-private */
    public void close() {
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                this.mProgramList.close();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setProgramListObserver(ProgramList programList, ProgramList.OnCloseListener closeListener) {
        Objects.requireNonNull(closeListener);
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                Log.w(TAG, "Previous program list observer wasn't properly closed, closing it...");
                this.mProgramList.close();
            }
            this.mProgramList = programList;
            if (programList != null) {
                programList.setOnCloseListener(new ProgramList.OnCloseListener(programList, closeListener) {
                    private final /* synthetic */ ProgramList f$1;
                    private final /* synthetic */ ProgramList.OnCloseListener f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void onClose() {
                        TunerCallbackAdapter.lambda$setProgramListObserver$0(TunerCallbackAdapter.this, this.f$1, this.f$2);
                    }
                });
                programList.addOnCompleteListener(new ProgramList.OnCompleteListener(programList) {
                    private final /* synthetic */ ProgramList f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void onComplete() {
                        TunerCallbackAdapter.lambda$setProgramListObserver$1(TunerCallbackAdapter.this, this.f$1);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$setProgramListObserver$0(TunerCallbackAdapter tunerCallbackAdapter, ProgramList programList, ProgramList.OnCloseListener closeListener) {
        synchronized (tunerCallbackAdapter.mLock) {
            if (tunerCallbackAdapter.mProgramList == programList) {
                tunerCallbackAdapter.mProgramList = null;
                tunerCallbackAdapter.mLastCompleteList = null;
                closeListener.onClose();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void lambda$setProgramListObserver$1(android.hardware.radio.TunerCallbackAdapter r3, android.hardware.radio.ProgramList r4) {
        /*
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            android.hardware.radio.ProgramList r1 = r3.mProgramList     // Catch:{ all -> 0x001f }
            if (r1 == r4) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x0009:
            java.util.List r1 = r4.toList()     // Catch:{ all -> 0x001f }
            r3.mLastCompleteList = r1     // Catch:{ all -> 0x001f }
            boolean r1 = r3.mDelayedCompleteCallback     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x001d
            java.lang.String r1 = "BroadcastRadio.TunerCallbackAdapter"
            java.lang.String r2 = "Sending delayed onBackgroundScanComplete callback"
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x001f }
            r3.sendBackgroundScanCompleteLocked()     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x001f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.TunerCallbackAdapter.lambda$setProgramListObserver$1(android.hardware.radio.TunerCallbackAdapter, android.hardware.radio.ProgramList):void");
    }

    /* access modifiers changed from: package-private */
    public List<RadioManager.ProgramInfo> getLastCompleteList() {
        List<RadioManager.ProgramInfo> list;
        synchronized (this.mLock) {
            list = this.mLastCompleteList;
        }
        return list;
    }

    /* access modifiers changed from: package-private */
    public void clearLastCompleteList() {
        synchronized (this.mLock) {
            this.mLastCompleteList = null;
        }
    }

    /* access modifiers changed from: package-private */
    public RadioManager.ProgramInfo getCurrentProgramInformation() {
        RadioManager.ProgramInfo programInfo;
        synchronized (this.mLock) {
            programInfo = this.mCurrentProgramInfo;
        }
        return programInfo;
    }

    /* access modifiers changed from: package-private */
    public boolean isAntennaConnected() {
        return this.mIsAntennaConnected;
    }

    public void onError(int status) {
        this.mHandler.post(new Runnable(status) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onError(this.f$1);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r4 != -1) goto L_0x003f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTuneFailed(int r4, android.hardware.radio.ProgramSelector r5) {
        /*
            r3 = this;
            android.os.Handler r0 = r3.mHandler
            android.hardware.radio.-$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w r1 = new android.hardware.radio.-$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w
            r1.<init>(r4, r5)
            r0.post(r1)
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r4 == r0) goto L_0x0024
            r0 = -38
            if (r4 == r0) goto L_0x0024
            r0 = -32
            if (r4 == r0) goto L_0x0022
            r0 = -22
            if (r4 == r0) goto L_0x0024
            r0 = -19
            if (r4 == r0) goto L_0x0024
            r0 = -1
            if (r4 == r0) goto L_0x0022
            goto L_0x003f
        L_0x0022:
            r0 = 1
            goto L_0x0040
        L_0x0024:
            java.lang.String r0 = "BroadcastRadio.TunerCallbackAdapter"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Got an error with no mapping to the legacy API ("
            r1.append(r2)
            r1.append(r4)
            java.lang.String r2 = "), doing a best-effort conversion to ERROR_SCAN_TIMEOUT"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
        L_0x003f:
            r0 = 3
        L_0x0040:
            android.os.Handler r1 = r3.mHandler
            android.hardware.radio.-$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE r2 = new android.hardware.radio.-$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE
            r2.<init>(r0)
            r1.post(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.TunerCallbackAdapter.onTuneFailed(int, android.hardware.radio.ProgramSelector):void");
    }

    public void onConfigurationChanged(RadioManager.BandConfig config) {
        this.mHandler.post(new Runnable(config) {
            private final /* synthetic */ RadioManager.BandConfig f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onConfigurationChanged(this.f$1);
            }
        });
    }

    public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo info) {
        if (info == null) {
            Log.e(TAG, "ProgramInfo must not be null");
            return;
        }
        synchronized (this.mLock) {
            this.mCurrentProgramInfo = info;
        }
        this.mHandler.post(new Runnable(info) {
            private final /* synthetic */ RadioManager.ProgramInfo f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.lambda$onCurrentProgramInfoChanged$6(TunerCallbackAdapter.this, this.f$1);
            }
        });
    }

    public static /* synthetic */ void lambda$onCurrentProgramInfoChanged$6(TunerCallbackAdapter tunerCallbackAdapter, RadioManager.ProgramInfo info) {
        tunerCallbackAdapter.mCallback.onProgramInfoChanged(info);
        RadioMetadata metadata = info.getMetadata();
        if (metadata != null) {
            tunerCallbackAdapter.mCallback.onMetadataChanged(metadata);
        }
    }

    public void onTrafficAnnouncement(boolean active) {
        this.mHandler.post(new Runnable(active) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onTrafficAnnouncement(this.f$1);
            }
        });
    }

    public void onEmergencyAnnouncement(boolean active) {
        this.mHandler.post(new Runnable(active) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onEmergencyAnnouncement(this.f$1);
            }
        });
    }

    public void onAntennaState(boolean connected) {
        this.mIsAntennaConnected = connected;
        this.mHandler.post(new Runnable(connected) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onAntennaState(this.f$1);
            }
        });
    }

    public void onBackgroundScanAvailabilityChange(boolean isAvailable) {
        this.mHandler.post(new Runnable(isAvailable) {
            private final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onBackgroundScanAvailabilityChange(this.f$1);
            }
        });
    }

    private void sendBackgroundScanCompleteLocked() {
        this.mDelayedCompleteCallback = false;
        this.mHandler.post(new Runnable() {
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onBackgroundScanComplete();
            }
        });
    }

    public void onBackgroundScanComplete() {
        synchronized (this.mLock) {
            if (this.mLastCompleteList == null) {
                Log.i(TAG, "Got onBackgroundScanComplete callback, but the program list didn't get through yet. Delaying it...");
                this.mDelayedCompleteCallback = true;
                return;
            }
            sendBackgroundScanCompleteLocked();
        }
    }

    public void onProgramListChanged() {
        this.mHandler.post(new Runnable() {
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onProgramListChanged();
            }
        });
    }

    public void onProgramListUpdated(ProgramList.Chunk chunk) {
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                this.mProgramList.apply((ProgramList.Chunk) Objects.requireNonNull(chunk));
            }
        }
    }

    public void onParametersUpdated(Map parameters) {
        this.mHandler.post(new Runnable(parameters) {
            private final /* synthetic */ Map f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TunerCallbackAdapter.this.mCallback.onParametersUpdated(this.f$1);
            }
        });
    }
}
