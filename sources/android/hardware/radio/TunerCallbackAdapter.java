package android.hardware.radio;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioTuner;
import android.p007os.Handler;
import android.p007os.Looper;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
class TunerCallbackAdapter extends ITunerCallback.Stub {
    private static final String TAG = "BroadcastRadio.TunerCallbackAdapter";
    private final RadioTuner.Callback mCallback;
    RadioManager.ProgramInfo mCurrentProgramInfo;
    private final Handler mHandler;
    List<RadioManager.ProgramInfo> mLastCompleteList;
    ProgramList mProgramList;
    private final Object mLock = new Object();
    boolean mIsAntennaConnected = true;
    private boolean mDelayedCompleteCallback = false;

    TunerCallbackAdapter(RadioTuner.Callback callback, Handler handler) {
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        } else {
            this.mHandler = handler;
        }
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                this.mProgramList.close();
            }
        }
    }

    void setProgramListObserver(final ProgramList programList, final ProgramList.OnCloseListener closeListener) {
        Objects.requireNonNull(closeListener);
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                Log.m64w(TAG, "Previous program list observer wasn't properly closed, closing it...");
                this.mProgramList.close();
            }
            this.mProgramList = programList;
            if (programList == null) {
                return;
            }
            programList.setOnCloseListener(new ProgramList.OnCloseListener() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$Hl80-0ppQ17uTjZuGamwBQMrO6Y
                @Override // android.hardware.radio.ProgramList.OnCloseListener
                public final void onClose() {
                    TunerCallbackAdapter.lambda$setProgramListObserver$0(TunerCallbackAdapter.this, programList, closeListener);
                }
            });
            programList.addOnCompleteListener(new ProgramList.OnCompleteListener() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$V-mJUy8dIlOVjsZ1ckkgn490jFI
                @Override // android.hardware.radio.ProgramList.OnCompleteListener
                public final void onComplete() {
                    TunerCallbackAdapter.lambda$setProgramListObserver$1(TunerCallbackAdapter.this, programList);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$setProgramListObserver$0(TunerCallbackAdapter tunerCallbackAdapter, ProgramList programList, ProgramList.OnCloseListener closeListener) {
        synchronized (tunerCallbackAdapter.mLock) {
            if (tunerCallbackAdapter.mProgramList != programList) {
                return;
            }
            tunerCallbackAdapter.mProgramList = null;
            tunerCallbackAdapter.mLastCompleteList = null;
            closeListener.onClose();
        }
    }

    public static /* synthetic */ void lambda$setProgramListObserver$1(TunerCallbackAdapter tunerCallbackAdapter, ProgramList programList) {
        synchronized (tunerCallbackAdapter.mLock) {
            if (tunerCallbackAdapter.mProgramList != programList) {
                return;
            }
            tunerCallbackAdapter.mLastCompleteList = programList.toList();
            if (tunerCallbackAdapter.mDelayedCompleteCallback) {
                Log.m72d(TAG, "Sending delayed onBackgroundScanComplete callback");
                tunerCallbackAdapter.sendBackgroundScanCompleteLocked();
            }
        }
    }

    List<RadioManager.ProgramInfo> getLastCompleteList() {
        List<RadioManager.ProgramInfo> list;
        synchronized (this.mLock) {
            list = this.mLastCompleteList;
        }
        return list;
    }

    void clearLastCompleteList() {
        synchronized (this.mLock) {
            this.mLastCompleteList = null;
        }
    }

    RadioManager.ProgramInfo getCurrentProgramInformation() {
        RadioManager.ProgramInfo programInfo;
        synchronized (this.mLock) {
            programInfo = this.mCurrentProgramInfo;
        }
        return programInfo;
    }

    boolean isAntennaConnected() {
        return this.mIsAntennaConnected;
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onError(final int status) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$jl29exheqPoYrltfLs9fLsjsI1A
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onError(status);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001f, code lost:
        if (r4 != (-1)) goto L13;
     */
    @Override // android.hardware.radio.ITunerCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onTuneFailed(final int status, final ProgramSelector selector) {
        final int errorCode;
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onTuneFailed(status, selector);
            }
        });
        if (status != Integer.MIN_VALUE && status != -38) {
            if (status != -32) {
                if (status != -22 && status != -19) {
                }
            }
            errorCode = 1;
            this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE
                @Override // java.lang.Runnable
                public final void run() {
                    TunerCallbackAdapter.this.mCallback.onError(errorCode);
                }
            });
        }
        Log.m68i(TAG, "Got an error with no mapping to the legacy API (" + status + "), doing a best-effort conversion to ERROR_SCAN_TIMEOUT");
        errorCode = 3;
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onError(errorCode);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onConfigurationChanged(final RadioManager.BandConfig config) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$B4BuskgdSatf-Xt5wzgLniEltQk
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onConfigurationChanged(config);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onCurrentProgramInfoChanged(final RadioManager.ProgramInfo info) {
        if (info == null) {
            Log.m70e(TAG, "ProgramInfo must not be null");
            return;
        }
        synchronized (this.mLock) {
            this.mCurrentProgramInfo = info;
        }
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$RSNrzX5-O3nayC2_jg0kAR6KkKY
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.lambda$onCurrentProgramInfoChanged$6(TunerCallbackAdapter.this, info);
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

    @Override // android.hardware.radio.ITunerCallback
    public void onTrafficAnnouncement(final boolean active) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$tiaoLZrR2K56rYeqHvSRh5lRdBI
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onTrafficAnnouncement(active);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onEmergencyAnnouncement(final boolean active) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$ZwPm3xxjeLvbP12KweyzqFJVnj4
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onEmergencyAnnouncement(active);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onAntennaState(final boolean connected) {
        this.mIsAntennaConnected = connected;
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$dR-VQmFrL_tBD2wpNvborTd8W08
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onAntennaState(connected);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanAvailabilityChange(final boolean isAvailable) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$4zf9n0sz_rU8z6a9GJmRInWrYkQ
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onBackgroundScanAvailabilityChange(isAvailable);
            }
        });
    }

    private void sendBackgroundScanCompleteLocked() {
        this.mDelayedCompleteCallback = false;
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$xIUT1Qu5TkA83V8ttYy1zv-JuFo
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onBackgroundScanComplete();
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanComplete() {
        synchronized (this.mLock) {
            if (this.mLastCompleteList == null) {
                Log.m68i(TAG, "Got onBackgroundScanComplete callback, but the program list didn't get through yet. Delaying it...");
                this.mDelayedCompleteCallback = true;
                return;
            }
            sendBackgroundScanCompleteLocked();
        }
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListChanged() {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$UsmGhKordXy4lhCylRP0mm2NcYc
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onProgramListChanged();
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListUpdated(ProgramList.Chunk chunk) {
        synchronized (this.mLock) {
            if (this.mProgramList == null) {
                return;
            }
            this.mProgramList.apply((ProgramList.Chunk) Objects.requireNonNull(chunk));
        }
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onParametersUpdated(final Map parameters) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.-$$Lambda$TunerCallbackAdapter$Yz-4KCDu1MOynGdkDf_oMxqhjeY
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.mCallback.onParametersUpdated(parameters);
            }
        });
    }
}
