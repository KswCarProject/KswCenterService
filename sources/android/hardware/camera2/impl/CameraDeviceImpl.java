package android.hardware.camera2.impl;

import android.app.ActivityThread;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraDeviceImpl extends CameraDevice implements IBinder.DeathRecipient {
    private static final long NANO_PER_SECOND = 1000000000;
    private static final int REQUEST_ID_NONE = -1;
    private final boolean DEBUG = false;
    /* access modifiers changed from: private */
    public final String TAG;
    private int customOpMode = 0;
    private final int mAppTargetSdkVersion;
    private final Runnable mCallOnActive = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onActive(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x0020 }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x0020 }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onActive(r1)
            L_0x001f:
                return
            L_0x0020:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass3.run():void");
        }
    };
    private final Runnable mCallOnBusy = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onBusy(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x0020 }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x0020 }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onBusy(r1)
            L_0x001f:
                return
            L_0x0020:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass4.run():void");
        }
    };
    private final Runnable mCallOnClosed = new Runnable() {
        private boolean mClosedOnce = false;

        public void run() {
            StateCallbackKK sessionCallback;
            if (!this.mClosedOnce) {
                synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                    sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                }
                if (sessionCallback != null) {
                    sessionCallback.onClosed(CameraDeviceImpl.this);
                }
                CameraDeviceImpl.this.mDeviceCallback.onClosed(CameraDeviceImpl.this);
                this.mClosedOnce = true;
                return;
            }
            throw new AssertionError("Don't post #onClosed more than once");
        }
    };
    /* access modifiers changed from: private */
    public final Runnable mCallOnDisconnected = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onDisconnected(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            android.hardware.camera2.impl.CameraDeviceImpl.access$200(r3.this$0).onDisconnected(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_0x001f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x002b }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x002b }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x002b }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x002b }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onDisconnected(r1)
            L_0x001f:
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                android.hardware.camera2.CameraDevice$StateCallback r1 = r1.mDeviceCallback
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r1.onDisconnected(r2)
                return
            L_0x002b:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass7.run():void");
        }
    };
    /* access modifiers changed from: private */
    public final Runnable mCallOnIdle = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onIdle(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x0020 }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x0020 }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onIdle(r1)
            L_0x001f:
                return
            L_0x0020:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass6.run():void");
        }
    };
    private final Runnable mCallOnOpened = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onOpened(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            android.hardware.camera2.impl.CameraDeviceImpl.access$200(r3.this$0).onOpened(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_0x001f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x002b }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x002b }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x002b }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x002b }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onOpened(r1)
            L_0x001f:
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                android.hardware.camera2.CameraDevice$StateCallback r1 = r1.mDeviceCallback
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r1.onOpened(r2)
                return
            L_0x002b:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass1.run():void");
        }
    };
    private final Runnable mCallOnUnconfigured = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            r0.onUnconfigured(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            if (r0 == null) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                r0 = 0
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r1 = r1.mInterfaceLock
                monitor-enter(r1)
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r2 = r2.mRemoteDevice     // Catch:{ all -> 0x0020 }
                if (r2 != 0) goto L_0x0010
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                return
            L_0x0010:
                android.hardware.camera2.impl.CameraDeviceImpl r2 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0020 }
                android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.mSessionStateCallback     // Catch:{ all -> 0x0020 }
                r0 = r2
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                if (r0 == 0) goto L_0x001f
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this
                r0.onUnconfigured(r1)
            L_0x001f:
                return
            L_0x0020:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0020 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass2.run():void");
        }
    };
    private final CameraDeviceCallbacks mCallbacks = new CameraDeviceCallbacks();
    private final String mCameraId;
    /* access modifiers changed from: private */
    public final SparseArray<CaptureCallbackHolder> mCaptureCallbackMap = new SparseArray<>();
    private final CameraCharacteristics mCharacteristics;
    private final AtomicBoolean mClosing = new AtomicBoolean();
    private AbstractMap.SimpleEntry<Integer, InputConfiguration> mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, (Object) null);
    /* access modifiers changed from: private */
    public final SparseArray<OutputConfiguration> mConfiguredOutputs = new SparseArray<>();
    /* access modifiers changed from: private */
    public CameraCaptureSessionCore mCurrentSession;
    /* access modifiers changed from: private */
    public final CameraDevice.StateCallback mDeviceCallback;
    /* access modifiers changed from: private */
    public final Executor mDeviceExecutor;
    /* access modifiers changed from: private */
    public final FrameNumberTracker mFrameNumberTracker = new FrameNumberTracker();
    /* access modifiers changed from: private */
    public boolean mIdle = true;
    /* access modifiers changed from: private */
    public boolean mInError = false;
    final Object mInterfaceLock = new Object();
    private boolean mIsPrivilegedApp = false;
    private int mNextSessionId = 0;
    /* access modifiers changed from: private */
    public ICameraDeviceUserWrapper mRemoteDevice;
    /* access modifiers changed from: private */
    public int mRepeatingRequestId = -1;
    /* access modifiers changed from: private */
    public int[] mRepeatingRequestTypes;
    private final List<RequestLastFrameNumbersHolder> mRequestLastFrameNumbersList = new ArrayList();
    /* access modifiers changed from: private */
    public volatile StateCallbackKK mSessionStateCallback;
    /* access modifiers changed from: private */
    public final int mTotalPartialCount;

    public interface CaptureCallback {
        public static final int NO_FRAMES_CAPTURED = -1;

        void onCaptureBufferLost(CameraDevice cameraDevice, CaptureRequest captureRequest, Surface surface, long j);

        void onCaptureCompleted(CameraDevice cameraDevice, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult);

        void onCaptureFailed(CameraDevice cameraDevice, CaptureRequest captureRequest, CaptureFailure captureFailure);

        void onCapturePartial(CameraDevice cameraDevice, CaptureRequest captureRequest, CaptureResult captureResult);

        void onCaptureProgressed(CameraDevice cameraDevice, CaptureRequest captureRequest, CaptureResult captureResult);

        void onCaptureSequenceAborted(CameraDevice cameraDevice, int i);

        void onCaptureSequenceCompleted(CameraDevice cameraDevice, int i, long j);

        void onCaptureStarted(CameraDevice cameraDevice, CaptureRequest captureRequest, long j, long j2);
    }

    public CameraDeviceImpl(String cameraId, CameraDevice.StateCallback callback, Executor executor, CameraCharacteristics characteristics, int appTargetSdkVersion) {
        if (cameraId == null || callback == null || executor == null || characteristics == null) {
            throw new IllegalArgumentException("Null argument given");
        }
        this.mCameraId = cameraId;
        this.mDeviceCallback = callback;
        this.mDeviceExecutor = executor;
        this.mCharacteristics = characteristics;
        this.mAppTargetSdkVersion = appTargetSdkVersion;
        String tag = String.format("CameraDevice-JV-%s", new Object[]{this.mCameraId});
        this.TAG = tag.length() > 23 ? tag.substring(0, 23) : tag;
        Integer partialCount = (Integer) this.mCharacteristics.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if (partialCount == null) {
            this.mTotalPartialCount = 1;
        } else {
            this.mTotalPartialCount = partialCount.intValue();
        }
        this.mIsPrivilegedApp = checkPrivilegedAppList();
    }

    public CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    public void setRemoteDevice(ICameraDeviceUser remoteDevice) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInError) {
                this.mRemoteDevice = new ICameraDeviceUserWrapper(remoteDevice);
                IBinder remoteDeviceBinder = remoteDevice.asBinder();
                if (remoteDeviceBinder != null) {
                    try {
                        remoteDeviceBinder.linkToDeath(this, 0);
                    } catch (RemoteException e) {
                        this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                        throw new CameraAccessException(2, "The camera device has encountered a serious error");
                    }
                }
                this.mDeviceExecutor.execute(this.mCallOnOpened);
                this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            }
        }
    }

    public void setRemoteFailure(ServiceSpecificException failure) {
        int failureCode = 4;
        boolean failureIsError = true;
        int i = failure.errorCode;
        if (i == 4) {
            failureIsError = false;
        } else if (i != 10) {
            switch (i) {
                case 6:
                    failureCode = 3;
                    break;
                case 7:
                    failureCode = 1;
                    break;
                case 8:
                    failureCode = 2;
                    break;
                default:
                    String str = this.TAG;
                    Log.e(str, "Unexpected failure in opening camera device: " + failure.errorCode + failure.getMessage());
                    break;
            }
        } else {
            failureCode = 4;
        }
        final int code = failureCode;
        final boolean isError = failureIsError;
        synchronized (this.mInterfaceLock) {
            this.mInError = true;
            this.mDeviceExecutor.execute(new Runnable() {
                public void run() {
                    if (isError) {
                        CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, code);
                    } else {
                        CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
                    }
                }
            });
        }
    }

    public void setVendorStreamConfigMode(int fpsrange) {
        this.customOpMode = fpsrange;
    }

    public String getId() {
        return this.mCameraId;
    }

    public void configureOutputs(List<Surface> outputs) throws CameraAccessException {
        ArrayList<OutputConfiguration> outputConfigs = new ArrayList<>(outputs.size());
        for (Surface s : outputs) {
            outputConfigs.add(new OutputConfiguration(s));
        }
        configureStreamsChecked((InputConfiguration) null, outputConfigs, 0, (CaptureRequest) null);
    }

    public boolean configureStreamsChecked(InputConfiguration inputConfig, List<OutputConfiguration> outputs, int operatingMode, CaptureRequest sessionParams) throws CameraAccessException {
        if (outputs == null) {
            outputs = new ArrayList<>();
        }
        if (outputs.size() != 0 || inputConfig == null) {
            checkInputConfiguration(inputConfig);
            synchronized (this.mInterfaceLock) {
                checkIfCameraClosedOrInError();
                HashSet<OutputConfiguration> addSet = new HashSet<>(outputs);
                List<Integer> deleteList = new ArrayList<>();
                for (int i = 0; i < this.mConfiguredOutputs.size(); i++) {
                    int streamId = this.mConfiguredOutputs.keyAt(i);
                    OutputConfiguration outConfig = this.mConfiguredOutputs.valueAt(i);
                    if (outputs.contains(outConfig)) {
                        if (!outConfig.isDeferredConfiguration()) {
                            addSet.remove(outConfig);
                        }
                    }
                    deleteList.add(Integer.valueOf(streamId));
                }
                this.mDeviceExecutor.execute(this.mCallOnBusy);
                stopRepeating();
                try {
                    waitUntilIdle();
                    this.mRemoteDevice.beginConfigure();
                    InputConfiguration currentInputConfig = this.mConfiguredInput.getValue();
                    if (inputConfig != currentInputConfig && (inputConfig == null || !inputConfig.equals(currentInputConfig))) {
                        if (currentInputConfig != null) {
                            this.mRemoteDevice.deleteStream(this.mConfiguredInput.getKey().intValue());
                            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, (Object) null);
                        }
                        if (inputConfig != null) {
                            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(Integer.valueOf(this.mRemoteDevice.createInputStream(inputConfig.getWidth(), inputConfig.getHeight(), inputConfig.getFormat())), inputConfig);
                        }
                    }
                    for (Integer streamId2 : deleteList) {
                        this.mRemoteDevice.deleteStream(streamId2.intValue());
                        this.mConfiguredOutputs.delete(streamId2.intValue());
                    }
                    for (OutputConfiguration outConfig2 : outputs) {
                        if (addSet.contains(outConfig2)) {
                            this.mConfiguredOutputs.put(this.mRemoteDevice.createStream(outConfig2), outConfig2);
                        }
                    }
                    int operatingMode2 = operatingMode | (this.customOpMode << 16);
                    if (sessionParams != null) {
                        this.mRemoteDevice.endConfigure(operatingMode2, sessionParams.getNativeCopy());
                    } else {
                        this.mRemoteDevice.endConfigure(operatingMode2, (CameraMetadataNative) null);
                    }
                    if (1 != 0) {
                        if (outputs.size() > 0) {
                            this.mDeviceExecutor.execute(this.mCallOnIdle);
                        }
                    }
                    this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
                } catch (IllegalArgumentException e) {
                    Log.w(this.TAG, "Stream configuration failed due to: " + e.getMessage());
                    if (0 != 0) {
                        if (outputs.size() > 0) {
                            this.mDeviceExecutor.execute(this.mCallOnIdle);
                            return false;
                        }
                    }
                    this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
                    return false;
                } catch (CameraAccessException e2) {
                    if (e2.getReason() == 4) {
                        throw new IllegalStateException("The camera is currently busy. You must wait until the previous operation completes.", e2);
                    }
                    throw e2;
                } catch (Throwable th) {
                    if (0 == 0 || outputs.size() <= 0) {
                        this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
                    } else {
                        this.mDeviceExecutor.execute(this.mCallOnIdle);
                    }
                    throw th;
                }
            }
            return true;
        }
        throw new IllegalArgumentException("cannot configure an input stream without any output streams");
    }

    public void createCaptureSession(List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        ArrayList arrayList = new ArrayList(outputs.size());
        for (Surface surface : outputs) {
            arrayList.add(new OutputConfiguration(surface));
        }
        createCaptureSessionInternal((InputConfiguration) null, arrayList, callback, checkAndWrapHandler(handler), 0, (CaptureRequest) null);
    }

    public void createCaptureSessionByOutputConfigurations(List<OutputConfiguration> outputConfigurations, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        createCaptureSessionInternal((InputConfiguration) null, new ArrayList<>(outputConfigurations), callback, checkAndWrapHandler(handler), 0, (CaptureRequest) null);
    }

    public void createReprocessableCaptureSession(InputConfiguration inputConfig, List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (inputConfig != null) {
            ArrayList arrayList = new ArrayList(outputs.size());
            for (Surface surface : outputs) {
                arrayList.add(new OutputConfiguration(surface));
            }
            createCaptureSessionInternal(inputConfig, arrayList, callback, checkAndWrapHandler(handler), 0, (CaptureRequest) null);
            return;
        }
        throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
    }

    public void createReprocessableCaptureSessionByConfigurations(InputConfiguration inputConfig, List<OutputConfiguration> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (inputConfig == null) {
            throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        } else if (outputs != null) {
            ArrayList arrayList = new ArrayList();
            for (OutputConfiguration output : outputs) {
                arrayList.add(new OutputConfiguration(output));
            }
            createCaptureSessionInternal(inputConfig, arrayList, callback, checkAndWrapHandler(handler), 0, (CaptureRequest) null);
        } else {
            throw new IllegalArgumentException("Output configurations cannot be null when creating a reprocessable capture session");
        }
    }

    public void createConstrainedHighSpeedCaptureSession(List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (outputs == null || outputs.size() == 0 || outputs.size() > 2) {
            throw new IllegalArgumentException("Output surface list must not be null and the size must be no more than 2");
        }
        ArrayList arrayList = new ArrayList(outputs.size());
        for (Surface surface : outputs) {
            arrayList.add(new OutputConfiguration(surface));
        }
        createCaptureSessionInternal((InputConfiguration) null, arrayList, callback, checkAndWrapHandler(handler), 1, (CaptureRequest) null);
    }

    public void createCustomCaptureSession(InputConfiguration inputConfig, List<OutputConfiguration> outputs, int operatingMode, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        ArrayList arrayList = new ArrayList();
        for (OutputConfiguration output : outputs) {
            arrayList.add(new OutputConfiguration(output));
        }
        createCaptureSessionInternal(inputConfig, arrayList, callback, checkAndWrapHandler(handler), operatingMode, (CaptureRequest) null);
    }

    public void createCaptureSession(SessionConfiguration config) throws CameraAccessException {
        if (config != null) {
            List<OutputConfiguration> outputConfigs = config.getOutputConfigurations();
            if (outputConfigs == null) {
                throw new IllegalArgumentException("Invalid output configurations");
            } else if (config.getExecutor() != null) {
                createCaptureSessionInternal(config.getInputConfiguration(), outputConfigs, config.getStateCallback(), config.getExecutor(), config.getSessionType(), config.getSessionParameters());
            } else {
                throw new IllegalArgumentException("Invalid executor");
            }
        } else {
            throw new IllegalArgumentException("Invalid session configuration");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.hardware.camera2.impl.CameraCaptureSessionImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.hardware.camera2.impl.CameraCaptureSessionImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.hardware.camera2.impl.CameraCaptureSessionImpl} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void createCaptureSessionInternal(android.hardware.camera2.params.InputConfiguration r24, java.util.List<android.hardware.camera2.params.OutputConfiguration> r25, android.hardware.camera2.CameraCaptureSession.StateCallback r26, java.util.concurrent.Executor r27, int r28, android.hardware.camera2.CaptureRequest r29) throws android.hardware.camera2.CameraAccessException {
        /*
            r23 = this;
            r9 = r23
            r10 = r24
            r11 = r28
            java.lang.Object r12 = r9.mInterfaceLock
            monitor-enter(r12)
            r23.checkIfCameraClosedOrInError()     // Catch:{ all -> 0x00d4 }
            r0 = 1
            if (r11 != r0) goto L_0x0011
            r1 = r0
            goto L_0x0012
        L_0x0011:
            r1 = 0
        L_0x0012:
            r13 = r1
            if (r13 == 0) goto L_0x0020
            if (r10 != 0) goto L_0x0018
            goto L_0x0020
        L_0x0018:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00d4 }
            java.lang.String r1 = "Constrained high speed session doesn't support input configuration yet."
            r0.<init>(r1)     // Catch:{ all -> 0x00d4 }
            throw r0     // Catch:{ all -> 0x00d4 }
        L_0x0020:
            android.hardware.camera2.impl.CameraCaptureSessionCore r1 = r9.mCurrentSession     // Catch:{ all -> 0x00d4 }
            if (r1 == 0) goto L_0x0029
            android.hardware.camera2.impl.CameraCaptureSessionCore r1 = r9.mCurrentSession     // Catch:{ all -> 0x00d4 }
            r1.replaceSessionClose()     // Catch:{ all -> 0x00d4 }
        L_0x0029:
            r1 = 1
            r2 = 0
            r3 = 0
            r4 = r3
            r14 = r25
            r15 = r29
            boolean r5 = r9.configureStreamsChecked(r10, r14, r11, r15)     // Catch:{ CameraAccessException -> 0x0048 }
            r1 = r5
            if (r1 != r0) goto L_0x0041
            if (r10 == 0) goto L_0x0041
            android.hardware.camera2.impl.ICameraDeviceUserWrapper r0 = r9.mRemoteDevice     // Catch:{ CameraAccessException -> 0x0048 }
            android.view.Surface r0 = r0.getInputSurface()     // Catch:{ CameraAccessException -> 0x0048 }
            r4 = r0
        L_0x0041:
            r16 = r1
            r17 = r2
            r0 = r4
            goto L_0x0050
        L_0x0048:
            r0 = move-exception
            r1 = 0
            r2 = r0
            r0 = 0
            r16 = r1
            r17 = r2
        L_0x0050:
            r18 = 0
            if (r13 == 0) goto L_0x00ab
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x00db }
            int r2 = r25.size()     // Catch:{ all -> 0x00db }
            r1.<init>(r2)     // Catch:{ all -> 0x00db }
            r8 = r1
            java.util.Iterator r1 = r25.iterator()     // Catch:{ all -> 0x00db }
        L_0x0062:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x00db }
            if (r2 == 0) goto L_0x0076
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x00db }
            android.hardware.camera2.params.OutputConfiguration r2 = (android.hardware.camera2.params.OutputConfiguration) r2     // Catch:{ all -> 0x00db }
            android.view.Surface r4 = r2.getSurface()     // Catch:{ all -> 0x00db }
            r8.add(r4)     // Catch:{ all -> 0x00db }
            goto L_0x0062
        L_0x0076:
            android.hardware.camera2.CameraCharacteristics r1 = r23.getCharacteristics()     // Catch:{ all -> 0x00db }
            android.hardware.camera2.CameraCharacteristics$Key<android.hardware.camera2.params.StreamConfigurationMap> r2 = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP     // Catch:{ all -> 0x00db }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x00db }
            android.hardware.camera2.params.StreamConfigurationMap r1 = (android.hardware.camera2.params.StreamConfigurationMap) r1     // Catch:{ all -> 0x00db }
            r7 = r1
            android.hardware.camera2.utils.SurfaceUtils.checkConstrainedHighSpeedSurfaces(r8, r3, r7)     // Catch:{ all -> 0x00db }
            android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl r19 = new android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl     // Catch:{ all -> 0x00db }
            int r2 = r9.mNextSessionId     // Catch:{ all -> 0x00db }
            int r1 = r2 + 1
            r9.mNextSessionId = r1     // Catch:{ all -> 0x00db }
            java.util.concurrent.Executor r6 = r9.mDeviceExecutor     // Catch:{ all -> 0x00db }
            android.hardware.camera2.CameraCharacteristics r5 = r9.mCharacteristics     // Catch:{ all -> 0x00db }
            r1 = r19
            r3 = r26
            r4 = r27
            r20 = r5
            r5 = r23
            r21 = r7
            r7 = r16
            r22 = r8
            r8 = r20
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00db }
            r1 = r19
            goto L_0x00c5
        L_0x00ab:
            android.hardware.camera2.impl.CameraCaptureSessionImpl r19 = new android.hardware.camera2.impl.CameraCaptureSessionImpl     // Catch:{ all -> 0x00db }
            int r2 = r9.mNextSessionId     // Catch:{ all -> 0x00db }
            int r1 = r2 + 1
            r9.mNextSessionId = r1     // Catch:{ all -> 0x00db }
            java.util.concurrent.Executor r7 = r9.mDeviceExecutor     // Catch:{ all -> 0x00db }
            r1 = r19
            r3 = r0
            r4 = r26
            r5 = r27
            r6 = r23
            r8 = r16
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00db }
            r1 = r19
        L_0x00c5:
            r9.mCurrentSession = r1     // Catch:{ all -> 0x00db }
            if (r17 != 0) goto L_0x00d3
            android.hardware.camera2.impl.CameraCaptureSessionCore r2 = r9.mCurrentSession     // Catch:{ all -> 0x00db }
            android.hardware.camera2.impl.CameraDeviceImpl$StateCallbackKK r2 = r2.getDeviceStateCallback()     // Catch:{ all -> 0x00db }
            r9.mSessionStateCallback = r2     // Catch:{ all -> 0x00db }
            monitor-exit(r12)     // Catch:{ all -> 0x00db }
            return
        L_0x00d3:
            throw r17     // Catch:{ all -> 0x00db }
        L_0x00d4:
            r0 = move-exception
            r14 = r25
            r15 = r29
        L_0x00d9:
            monitor-exit(r12)     // Catch:{ all -> 0x00db }
            throw r0
        L_0x00db:
            r0 = move-exception
            goto L_0x00d9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.createCaptureSessionInternal(android.hardware.camera2.params.InputConfiguration, java.util.List, android.hardware.camera2.CameraCaptureSession$StateCallback, java.util.concurrent.Executor, int, android.hardware.camera2.CaptureRequest):void");
    }

    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfig) throws CameraAccessException, UnsupportedOperationException, IllegalArgumentException {
        boolean isSessionConfigurationSupported;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            isSessionConfigurationSupported = this.mRemoteDevice.isSessionConfigurationSupported(sessionConfig);
        }
        return isSessionConfigurationSupported;
    }

    public void setSessionListener(StateCallbackKK sessionCallback) {
        synchronized (this.mInterfaceLock) {
            this.mSessionStateCallback = sessionCallback;
        }
    }

    private void overrideEnableZsl(CameraMetadataNative request, boolean newValue) {
        if (((Boolean) request.get(CaptureRequest.CONTROL_ENABLE_ZSL)) != null) {
            request.set(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.valueOf(newValue));
        }
    }

    public CaptureRequest.Builder createCaptureRequest(int templateType, Set<String> physicalCameraIdSet) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (String physicalId : physicalCameraIdSet) {
                if (physicalId == getId()) {
                    throw new IllegalStateException("Physical id matches the logical id!");
                }
            }
            CameraMetadataNative templatedRequest = this.mRemoteDevice.createDefaultRequest(templateType);
            if (this.mAppTargetSdkVersion < 26 || templateType != 2) {
                overrideEnableZsl(templatedRequest, false);
            }
            builder = new CaptureRequest.Builder(templatedRequest, false, -1, getId(), physicalCameraIdSet);
        }
        return builder;
    }

    public CaptureRequest.Builder createCaptureRequest(int templateType) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            CameraMetadataNative templatedRequest = this.mRemoteDevice.createDefaultRequest(templateType);
            if (this.mAppTargetSdkVersion < 26 || templateType != 2) {
                overrideEnableZsl(templatedRequest, false);
            }
            builder = new CaptureRequest.Builder(templatedRequest, false, -1, getId(), (Set<String>) null);
        }
        return builder;
    }

    public CaptureRequest.Builder createReprocessCaptureRequest(TotalCaptureResult inputResult) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            builder = new CaptureRequest.Builder(new CameraMetadataNative(inputResult.getNativeCopy()), true, inputResult.getSessionId(), getId(), (Set<String>) null);
        }
        return builder;
    }

    public void prepare(Surface surface) throws CameraAccessException {
        if (surface != null) {
            synchronized (this.mInterfaceLock) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    } else if (this.mConfiguredOutputs.valueAt(i).getSurfaces().contains(surface)) {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (streamId != -1) {
                    this.mRemoteDevice.prepare(streamId);
                } else {
                    throw new IllegalArgumentException("Surface is not part of this session");
                }
            }
            return;
        }
        throw new IllegalArgumentException("Surface is null");
    }

    public void prepare(int maxCount, Surface surface) throws CameraAccessException {
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        } else if (maxCount > 0) {
            synchronized (this.mInterfaceLock) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    } else if (surface == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (streamId != -1) {
                    this.mRemoteDevice.prepare2(maxCount, streamId);
                } else {
                    throw new IllegalArgumentException("Surface is not part of this session");
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid maxCount given: " + maxCount);
        }
    }

    public void updateOutputConfiguration(OutputConfiguration config) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                } else if (config.getSurface() == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                } else {
                    i++;
                }
            }
            if (streamId != -1) {
                this.mRemoteDevice.updateOutputConfiguration(streamId, config);
                this.mConfiguredOutputs.put(streamId, config);
            } else {
                throw new IllegalArgumentException("Invalid output configuration");
            }
        }
    }

    public void tearDown(Surface surface) throws CameraAccessException {
        if (surface != null) {
            synchronized (this.mInterfaceLock) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    } else if (surface == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (streamId != -1) {
                    this.mRemoteDevice.tearDown(streamId);
                } else {
                    throw new IllegalArgumentException("Surface is not part of this session");
                }
            }
            return;
        }
        throw new IllegalArgumentException("Surface is null");
    }

    public void finalizeOutputConfigs(List<OutputConfiguration> outputConfigs) throws CameraAccessException {
        if (outputConfigs == null || outputConfigs.size() == 0) {
            throw new IllegalArgumentException("deferred config is null or empty");
        }
        synchronized (this.mInterfaceLock) {
            for (OutputConfiguration config : outputConfigs) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    } else if (config.equals(this.mConfiguredOutputs.valueAt(i))) {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (streamId == -1) {
                    throw new IllegalArgumentException("Deferred config is not part of this session");
                } else if (config.getSurfaces().size() != 0) {
                    this.mRemoteDevice.finalizeOutputConfigurations(streamId, config);
                    this.mConfiguredOutputs.put(streamId, config);
                } else {
                    throw new IllegalArgumentException("The final config for stream " + streamId + " must have at least 1 surface");
                }
            }
        }
    }

    public int capture(CaptureRequest request, CaptureCallback callback, Executor executor) throws CameraAccessException {
        List<CaptureRequest> requestList = new ArrayList<>();
        requestList.add(request);
        return submitCaptureRequest(requestList, callback, executor, false);
    }

    public int captureBurst(List<CaptureRequest> requests, CaptureCallback callback, Executor executor) throws CameraAccessException {
        if (requests != null && !requests.isEmpty()) {
            return submitCaptureRequest(requests, callback, executor, false);
        }
        throw new IllegalArgumentException("At least one request must be given");
    }

    /* access modifiers changed from: private */
    public void checkEarlyTriggerSequenceComplete(final int requestId, long lastFrameNumber, int[] repeatingRequestTypes) {
        if (lastFrameNumber == -1) {
            int index = this.mCaptureCallbackMap.indexOfKey(requestId);
            final CaptureCallbackHolder holder = index >= 0 ? this.mCaptureCallbackMap.valueAt(index) : null;
            if (holder != null) {
                this.mCaptureCallbackMap.removeAt(index);
            }
            if (holder != null) {
                Runnable resultDispatch = new Runnable() {
                    public void run() {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            holder.getCallback().onCaptureSequenceAborted(CameraDeviceImpl.this, requestId);
                        }
                    }
                };
                long ident = Binder.clearCallingIdentity();
                try {
                    holder.getExecutor().execute(resultDispatch);
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            } else {
                Log.w(this.TAG, String.format("did not register callback to request %d", new Object[]{Integer.valueOf(requestId)}));
            }
        } else {
            this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(requestId, lastFrameNumber, repeatingRequestTypes));
            checkAndFireSequenceComplete();
        }
    }

    private int[] getRequestTypes(CaptureRequest[] requestArray) {
        int[] requestTypes = new int[requestArray.length];
        for (int i = 0; i < requestArray.length; i++) {
            requestTypes[i] = requestArray[i].getRequestType();
        }
        return requestTypes;
    }

    private int submitCaptureRequest(List<CaptureRequest> requestList, CaptureCallback callback, Executor executor, boolean repeating) throws CameraAccessException {
        int requestId;
        List<CaptureRequest> list = requestList;
        CaptureCallback captureCallback = callback;
        boolean z = repeating;
        Executor executor2 = checkExecutor(executor, captureCallback);
        for (CaptureRequest request : requestList) {
            if (!request.getTargets().isEmpty()) {
                Iterator<Surface> it = request.getTargets().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Surface surface = it.next();
                        if (surface != null) {
                            int i = 0;
                            while (true) {
                                if (i < this.mConfiguredOutputs.size()) {
                                    OutputConfiguration configuration = this.mConfiguredOutputs.valueAt(i);
                                    if (!configuration.isForPhysicalCamera() || !configuration.getSurfaces().contains(surface) || !request.isReprocess()) {
                                        i++;
                                    } else {
                                        throw new IllegalArgumentException("Reprocess request on physical stream is not allowed");
                                    }
                                }
                            }
                        } else {
                            throw new IllegalArgumentException("Null Surface targets are not allowed");
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Each request must have at least one Surface target");
            }
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (z) {
                stopRepeating();
            }
            CaptureRequest[] requestArray = (CaptureRequest[]) list.toArray(new CaptureRequest[requestList.size()]);
            for (CaptureRequest request2 : requestArray) {
                request2.convertSurfaceToStreamId(this.mConfiguredOutputs);
            }
            SubmitInfo requestInfo = this.mRemoteDevice.submitRequestList(requestArray, z);
            for (CaptureRequest request3 : requestArray) {
                request3.recoverStreamIdToSurface();
            }
            if (captureCallback != null) {
                CaptureCallbackHolder captureCallbackHolder = r2;
                CaptureCallbackHolder captureCallbackHolder2 = new CaptureCallbackHolder(callback, requestList, executor2, repeating, this.mNextSessionId - 1);
                this.mCaptureCallbackMap.put(requestInfo.getRequestId(), captureCallbackHolder);
            }
            if (z) {
                if (this.mRepeatingRequestId != -1) {
                    checkEarlyTriggerSequenceComplete(this.mRepeatingRequestId, requestInfo.getLastFrameNumber(), this.mRepeatingRequestTypes);
                }
                this.mRepeatingRequestId = requestInfo.getRequestId();
                this.mRepeatingRequestTypes = getRequestTypes(requestArray);
            } else {
                this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(list, requestInfo));
            }
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnActive);
            }
            this.mIdle = false;
            requestId = requestInfo.getRequestId();
        }
        return requestId;
    }

    public int setRepeatingRequest(CaptureRequest request, CaptureCallback callback, Executor executor) throws CameraAccessException {
        List<CaptureRequest> requestList = new ArrayList<>();
        requestList.add(request);
        return submitCaptureRequest(requestList, callback, executor, true);
    }

    public int setRepeatingBurst(List<CaptureRequest> requests, CaptureCallback callback, Executor executor) throws CameraAccessException {
        if (requests != null && !requests.isEmpty()) {
            return submitCaptureRequest(requests, callback, executor, true);
        }
        throw new IllegalArgumentException("At least one request must be given");
    }

    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                int requestId = this.mRepeatingRequestId;
                this.mRepeatingRequestId = -1;
                int[] requestTypes = this.mRepeatingRequestTypes;
                this.mRepeatingRequestTypes = null;
                try {
                    checkEarlyTriggerSequenceComplete(requestId, this.mRemoteDevice.cancelRequest(requestId), requestTypes);
                } catch (IllegalArgumentException e) {
                }
            }
        }
    }

    private void waitUntilIdle() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId == -1) {
                this.mRemoteDevice.waitUntilIdle();
            } else {
                throw new IllegalStateException("Active repeating request ongoing");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void flush() throws android.hardware.camera2.CameraAccessException {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mInterfaceLock
            monitor-enter(r0)
            r6.checkIfCameraClosedOrInError()     // Catch:{ all -> 0x0033 }
            java.util.concurrent.Executor r1 = r6.mDeviceExecutor     // Catch:{ all -> 0x0033 }
            java.lang.Runnable r2 = r6.mCallOnBusy     // Catch:{ all -> 0x0033 }
            r1.execute(r2)     // Catch:{ all -> 0x0033 }
            boolean r1 = r6.mIdle     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x001a
            java.util.concurrent.Executor r1 = r6.mDeviceExecutor     // Catch:{ all -> 0x0033 }
            java.lang.Runnable r2 = r6.mCallOnIdle     // Catch:{ all -> 0x0033 }
            r1.execute(r2)     // Catch:{ all -> 0x0033 }
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x001a:
            android.hardware.camera2.impl.ICameraDeviceUserWrapper r1 = r6.mRemoteDevice     // Catch:{ all -> 0x0033 }
            long r1 = r1.flush()     // Catch:{ all -> 0x0033 }
            int r3 = r6.mRepeatingRequestId     // Catch:{ all -> 0x0033 }
            r4 = -1
            if (r3 == r4) goto L_0x0031
            int r3 = r6.mRepeatingRequestId     // Catch:{ all -> 0x0033 }
            int[] r5 = r6.mRepeatingRequestTypes     // Catch:{ all -> 0x0033 }
            r6.checkEarlyTriggerSequenceComplete(r3, r1, r5)     // Catch:{ all -> 0x0033 }
            r6.mRepeatingRequestId = r4     // Catch:{ all -> 0x0033 }
            r3 = 0
            r6.mRepeatingRequestTypes = r3     // Catch:{ all -> 0x0033 }
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.flush():void");
    }

    public void close() {
        synchronized (this.mInterfaceLock) {
            if (!this.mClosing.getAndSet(true)) {
                if (this.mRemoteDevice != null) {
                    this.mRemoteDevice.disconnect();
                    this.mRemoteDevice.unlinkToDeath(this, 0);
                }
                if (this.mRemoteDevice != null || this.mInError) {
                    this.mDeviceExecutor.execute(this.mCallOnClosed);
                }
                this.mRemoteDevice = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private boolean checkPrivilegedAppList() {
        String packageName = ActivityThread.currentOpPackageName();
        String packageList = SystemProperties.get("persist.vendor.camera.privapp.list");
        if (packageList.length() <= 0) {
            return false;
        }
        TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
        splitter.setString(packageList);
        for (String str : splitter) {
            if (packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPrivilegedApp() {
        return this.mIsPrivilegedApp;
    }

    private void checkInputConfiguration(InputConfiguration inputConfig) {
        if (inputConfig != null) {
            StreamConfigurationMap configMap = (StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (isPrivilegedApp()) {
                Log.w(this.TAG, "ignore input format/size check for white listed app");
                return;
            }
            boolean validFormat = false;
            for (int format : configMap.getInputFormats()) {
                if (format == inputConfig.getFormat()) {
                    validFormat = true;
                }
            }
            if (validFormat) {
                boolean validSize = false;
                for (Size s : configMap.getInputSizes(inputConfig.getFormat())) {
                    if (inputConfig.getWidth() == s.getWidth() && inputConfig.getHeight() == s.getHeight()) {
                        validSize = true;
                    }
                }
                if (!validSize) {
                    throw new IllegalArgumentException("input size " + inputConfig.getWidth() + "x" + inputConfig.getHeight() + " is not valid");
                }
                return;
            }
            throw new IllegalArgumentException("input format " + inputConfig.getFormat() + " is not valid");
        }
    }

    public static abstract class StateCallbackKK extends CameraDevice.StateCallback {
        public void onUnconfigured(CameraDevice camera) {
        }

        public void onActive(CameraDevice camera) {
        }

        public void onBusy(CameraDevice camera) {
        }

        public void onIdle(CameraDevice camera) {
        }

        public void onRequestQueueEmpty() {
        }

        public void onSurfacePrepared(Surface surface) {
        }
    }

    static class CaptureCallbackHolder {
        private final CaptureCallback mCallback;
        private final Executor mExecutor;
        private final boolean mHasBatchedOutputs;
        private final boolean mRepeating;
        private final List<CaptureRequest> mRequestList;
        private final int mSessionId;

        CaptureCallbackHolder(CaptureCallback callback, List<CaptureRequest> requestList, Executor executor, boolean repeating, int sessionId) {
            if (callback == null || executor == null) {
                throw new UnsupportedOperationException("Must have a valid handler and a valid callback");
            }
            this.mRepeating = repeating;
            this.mExecutor = executor;
            this.mRequestList = new ArrayList(requestList);
            this.mCallback = callback;
            this.mSessionId = sessionId;
            boolean hasBatchedOutputs = true;
            int i = 0;
            while (true) {
                if (i >= requestList.size()) {
                    break;
                }
                CaptureRequest request = requestList.get(i);
                if (request.isPartOfCRequestList()) {
                    if (i == 0 && request.getTargets().size() != 2) {
                        hasBatchedOutputs = false;
                        break;
                    }
                    i++;
                } else {
                    hasBatchedOutputs = false;
                    break;
                }
            }
            this.mHasBatchedOutputs = hasBatchedOutputs;
        }

        public boolean isRepeating() {
            return this.mRepeating;
        }

        public CaptureCallback getCallback() {
            return this.mCallback;
        }

        public CaptureRequest getRequest(int subsequenceId) {
            if (subsequenceId >= this.mRequestList.size()) {
                throw new IllegalArgumentException(String.format("Requested subsequenceId %d is larger than request list size %d.", new Object[]{Integer.valueOf(subsequenceId), Integer.valueOf(this.mRequestList.size())}));
            } else if (subsequenceId >= 0) {
                return this.mRequestList.get(subsequenceId);
            } else {
                throw new IllegalArgumentException(String.format("Requested subsequenceId %d is negative", new Object[]{Integer.valueOf(subsequenceId)}));
            }
        }

        public CaptureRequest getRequest() {
            return getRequest(0);
        }

        public Executor getExecutor() {
            return this.mExecutor;
        }

        public int getSessionId() {
            return this.mSessionId;
        }

        public int getRequestCount() {
            return this.mRequestList.size();
        }

        public boolean hasBatchedOutputs() {
            return this.mHasBatchedOutputs;
        }
    }

    static class RequestLastFrameNumbersHolder {
        private final long mLastRegularFrameNumber;
        private final long mLastReprocessFrameNumber;
        private final long mLastZslStillFrameNumber;
        private final int mRequestId;

        public RequestLastFrameNumbersHolder(List<CaptureRequest> requestList, SubmitInfo requestInfo) {
            long lastRegularFrameNumber = -1;
            long lastReprocessFrameNumber = -1;
            long lastZslStillFrameNumber = -1;
            long frameNumber = requestInfo.getLastFrameNumber();
            int i = 1;
            if (requestInfo.getLastFrameNumber() >= ((long) (requestList.size() - 1))) {
                int i2 = requestList.size() - 1;
                while (true) {
                    if (i2 >= 0) {
                        int requestType = requestList.get(i2).getRequestType();
                        if (requestType == i && lastReprocessFrameNumber == -1) {
                            lastReprocessFrameNumber = frameNumber;
                        } else if (requestType == 2 && lastZslStillFrameNumber == -1) {
                            lastZslStillFrameNumber = frameNumber;
                        } else if (requestType == 0 && lastRegularFrameNumber == -1) {
                            lastRegularFrameNumber = frameNumber;
                        }
                        if (lastReprocessFrameNumber != -1 && lastZslStillFrameNumber != -1 && lastRegularFrameNumber != -1) {
                            break;
                        }
                        frameNumber--;
                        i2--;
                        i = 1;
                    } else {
                        List<CaptureRequest> list = requestList;
                        break;
                    }
                }
                this.mLastRegularFrameNumber = lastRegularFrameNumber;
                this.mLastReprocessFrameNumber = lastReprocessFrameNumber;
                this.mLastZslStillFrameNumber = lastZslStillFrameNumber;
                this.mRequestId = requestInfo.getRequestId();
                return;
            }
            List<CaptureRequest> list2 = requestList;
            throw new IllegalArgumentException("lastFrameNumber: " + requestInfo.getLastFrameNumber() + " should be at least " + (requestList.size() - 1) + " for the number of  requests in the list: " + requestList.size());
        }

        RequestLastFrameNumbersHolder(int requestId, long lastFrameNumber, int[] repeatingRequestTypes) {
            long lastRegularFrameNumber = -1;
            long lastZslStillFrameNumber = -1;
            if (repeatingRequestTypes == null) {
                throw new IllegalArgumentException("repeatingRequest list must not be null");
            } else if (lastFrameNumber >= ((long) (repeatingRequestTypes.length - 1))) {
                long frameNumber = lastFrameNumber;
                int i = repeatingRequestTypes.length;
                while (true) {
                    i--;
                    if (i >= 0) {
                        if (repeatingRequestTypes[i] == 2 && lastZslStillFrameNumber == -1) {
                            lastZslStillFrameNumber = frameNumber;
                        } else if (repeatingRequestTypes[i] == 0 && lastRegularFrameNumber == -1) {
                            lastRegularFrameNumber = frameNumber;
                        }
                        if (lastZslStillFrameNumber != -1 && lastRegularFrameNumber != -1) {
                            break;
                        }
                        frameNumber--;
                    } else {
                        break;
                    }
                }
                this.mLastRegularFrameNumber = lastRegularFrameNumber;
                this.mLastZslStillFrameNumber = lastZslStillFrameNumber;
                this.mLastReprocessFrameNumber = -1;
                this.mRequestId = requestId;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("lastFrameNumber: ");
                sb.append(lastFrameNumber);
                sb.append(" should be at least ");
                sb.append(repeatingRequestTypes.length - 1);
                sb.append(" for the number of requests in the list: ");
                sb.append(repeatingRequestTypes.length);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        public long getLastRegularFrameNumber() {
            return this.mLastRegularFrameNumber;
        }

        public long getLastReprocessFrameNumber() {
            return this.mLastReprocessFrameNumber;
        }

        public long getLastZslStillFrameNumber() {
            return this.mLastZslStillFrameNumber;
        }

        public long getLastFrameNumber() {
            return Math.max(this.mLastZslStillFrameNumber, Math.max(this.mLastRegularFrameNumber, this.mLastReprocessFrameNumber));
        }

        public int getRequestId() {
            return this.mRequestId;
        }
    }

    public class FrameNumberTracker {
        private long[] mCompletedFrameNumber = new long[3];
        private final TreeMap<Long, Integer> mFutureErrorMap = new TreeMap<>();
        private final HashMap<Long, List<CaptureResult>> mPartialResults = new HashMap<>();
        private final LinkedList<Long>[] mSkippedFrameNumbers = new LinkedList[3];
        private final LinkedList<Long>[] mSkippedOtherFrameNumbers = new LinkedList[3];

        public FrameNumberTracker() {
            for (int i = 0; i < 3; i++) {
                this.mCompletedFrameNumber[i] = -1;
                this.mSkippedOtherFrameNumbers[i] = new LinkedList<>();
                this.mSkippedFrameNumbers[i] = new LinkedList<>();
            }
        }

        private void update() {
            Iterator iter = this.mFutureErrorMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = iter.next();
                Long errorFrameNumber = (Long) pair.getKey();
                int requestType = ((Integer) pair.getValue()).intValue();
                Boolean removeError = false;
                if (errorFrameNumber.longValue() != this.mCompletedFrameNumber[requestType] + 1) {
                    if (this.mSkippedFrameNumbers[requestType].isEmpty()) {
                        int i = 1;
                        while (true) {
                            if (i >= 3) {
                                break;
                            }
                            int otherType = (requestType + i) % 3;
                            if (!this.mSkippedOtherFrameNumbers[otherType].isEmpty() && errorFrameNumber == this.mSkippedOtherFrameNumbers[otherType].element()) {
                                this.mCompletedFrameNumber[requestType] = errorFrameNumber.longValue();
                                this.mSkippedOtherFrameNumbers[otherType].remove();
                                removeError = true;
                                break;
                            }
                            i++;
                        }
                    } else if (errorFrameNumber == this.mSkippedFrameNumbers[requestType].element()) {
                        this.mCompletedFrameNumber[requestType] = errorFrameNumber.longValue();
                        this.mSkippedFrameNumbers[requestType].remove();
                        removeError = true;
                    }
                } else {
                    this.mCompletedFrameNumber[requestType] = errorFrameNumber.longValue();
                    removeError = true;
                }
                if (removeError.booleanValue() != 0) {
                    iter.remove();
                }
            }
        }

        public void updateTracker(long frameNumber, boolean isError, int requestType) {
            if (isError) {
                this.mFutureErrorMap.put(Long.valueOf(frameNumber), Integer.valueOf(requestType));
            } else {
                try {
                    updateCompletedFrameNumber(frameNumber, requestType);
                } catch (IllegalArgumentException e) {
                    Log.e(CameraDeviceImpl.this.TAG, e.getMessage());
                }
            }
            update();
        }

        public void updateTracker(long frameNumber, CaptureResult result, boolean partial, int requestType) {
            if (!partial) {
                updateTracker(frameNumber, false, requestType);
            } else if (result != null) {
                List<CaptureResult> partials = this.mPartialResults.get(Long.valueOf(frameNumber));
                if (partials == null) {
                    partials = new ArrayList<>();
                    this.mPartialResults.put(Long.valueOf(frameNumber), partials);
                }
                partials.add(result);
            }
        }

        public List<CaptureResult> popPartialResults(long frameNumber) {
            return this.mPartialResults.remove(Long.valueOf(frameNumber));
        }

        public long getCompletedFrameNumber() {
            return this.mCompletedFrameNumber[0];
        }

        public long getCompletedReprocessFrameNumber() {
            return this.mCompletedFrameNumber[1];
        }

        public long getCompletedZslStillFrameNumber() {
            return this.mCompletedFrameNumber[2];
        }

        private void updateCompletedFrameNumber(long frameNumber, int requestType) throws IllegalArgumentException {
            int index;
            LinkedList<Long> dstList;
            LinkedList<Long> srcList;
            long j = frameNumber;
            if (j > this.mCompletedFrameNumber[requestType]) {
                int otherType1 = (requestType + 1) % 3;
                int otherType2 = (requestType + 2) % 3;
                long maxOtherFrameNumberSeen = Math.max(this.mCompletedFrameNumber[otherType1], this.mCompletedFrameNumber[otherType2]);
                if (j >= maxOtherFrameNumberSeen) {
                    long i = Math.max(maxOtherFrameNumberSeen, this.mCompletedFrameNumber[requestType]);
                    while (true) {
                        i++;
                        if (i >= j) {
                            break;
                        }
                        this.mSkippedOtherFrameNumbers[requestType].add(Long.valueOf(i));
                    }
                } else if (this.mSkippedFrameNumbers[requestType].isEmpty()) {
                    int index1 = this.mSkippedOtherFrameNumbers[otherType1].indexOf(Long.valueOf(frameNumber));
                    int index2 = this.mSkippedOtherFrameNumbers[otherType2].indexOf(Long.valueOf(frameNumber));
                    int i2 = 0;
                    boolean inSkippedOther2 = true;
                    boolean inSkippedOther1 = index1 != -1;
                    if (index2 == -1) {
                        inSkippedOther2 = false;
                    }
                    if (inSkippedOther1 ^ inSkippedOther2) {
                        if (inSkippedOther1) {
                            srcList = this.mSkippedOtherFrameNumbers[otherType1];
                            dstList = this.mSkippedFrameNumbers[otherType2];
                            index = index1;
                        } else {
                            srcList = this.mSkippedOtherFrameNumbers[otherType2];
                            dstList = this.mSkippedFrameNumbers[otherType1];
                            index = index2;
                        }
                        while (i2 < index) {
                            dstList.add(srcList.removeFirst());
                            i2++;
                            otherType1 = otherType1;
                        }
                        srcList.remove();
                    } else {
                        throw new IllegalArgumentException("frame number " + j + " is a repeat or invalid");
                    }
                } else if (j < this.mSkippedFrameNumbers[requestType].element().longValue()) {
                    throw new IllegalArgumentException("frame number " + j + " is a repeat");
                } else if (j <= this.mSkippedFrameNumbers[requestType].element().longValue()) {
                    this.mSkippedFrameNumbers[requestType].remove();
                    int i3 = otherType1;
                } else {
                    throw new IllegalArgumentException("frame number " + j + " comes out of order. Expecting " + this.mSkippedFrameNumbers[requestType].element());
                }
                this.mCompletedFrameNumber[requestType] = j;
                return;
            }
            throw new IllegalArgumentException("frame number " + j + " is a repeat");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007e, code lost:
        r2 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007f, code lost:
        if (r2 == null) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0081, code lost:
        if (r11 == false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0083, code lost:
        r9.remove();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0086, code lost:
        if (r11 == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0088, code lost:
        r3 = new android.hardware.camera2.impl.CameraDeviceImpl.AnonymousClass10(r1);
        r13 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r2.getExecutor().execute(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009e, code lost:
        android.os.Binder.restoreCallingIdentity(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a1, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkAndFireSequenceComplete() {
        /*
            r24 = this;
            r1 = r24
            android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r0 = r1.mFrameNumberTracker
            long r2 = r0.getCompletedFrameNumber()
            android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r0 = r1.mFrameNumberTracker
            long r4 = r0.getCompletedReprocessFrameNumber()
            android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r0 = r1.mFrameNumberTracker
            long r6 = r0.getCompletedZslStillFrameNumber()
            r8 = 0
            java.util.List<android.hardware.camera2.impl.CameraDeviceImpl$RequestLastFrameNumbersHolder> r0 = r1.mRequestLastFrameNumbersList
            java.util.Iterator r0 = r0.iterator()
        L_0x001b:
            r9 = r0
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L_0x00af
            java.lang.Object r0 = r9.next()
            r10 = r0
            android.hardware.camera2.impl.CameraDeviceImpl$RequestLastFrameNumbersHolder r10 = (android.hardware.camera2.impl.CameraDeviceImpl.RequestLastFrameNumbersHolder) r10
            r11 = 0
            int r12 = r10.getRequestId()
            java.lang.Object r13 = r1.mInterfaceLock
            monitor-enter(r13)
            android.hardware.camera2.impl.ICameraDeviceUserWrapper r0 = r1.mRemoteDevice     // Catch:{ all -> 0x00a8 }
            if (r0 != 0) goto L_0x0043
            java.lang.String r0 = r1.TAG     // Catch:{ all -> 0x003e }
            java.lang.String r14 = "Camera closed while checking sequences"
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r14)     // Catch:{ all -> 0x003e }
            monitor-exit(r13)     // Catch:{ all -> 0x003e }
            return
        L_0x003e:
            r0 = move-exception
            r22 = r2
            goto L_0x00ab
        L_0x0043:
            android.util.SparseArray<android.hardware.camera2.impl.CameraDeviceImpl$CaptureCallbackHolder> r0 = r1.mCaptureCallbackMap     // Catch:{ all -> 0x00a8 }
            int r0 = r0.indexOfKey(r12)     // Catch:{ all -> 0x00a8 }
            if (r0 < 0) goto L_0x0054
            android.util.SparseArray<android.hardware.camera2.impl.CameraDeviceImpl$CaptureCallbackHolder> r14 = r1.mCaptureCallbackMap     // Catch:{ all -> 0x003e }
            java.lang.Object r14 = r14.valueAt(r0)     // Catch:{ all -> 0x003e }
            android.hardware.camera2.impl.CameraDeviceImpl$CaptureCallbackHolder r14 = (android.hardware.camera2.impl.CameraDeviceImpl.CaptureCallbackHolder) r14     // Catch:{ all -> 0x003e }
            goto L_0x0055
        L_0x0054:
            r14 = 0
        L_0x0055:
            if (r14 == 0) goto L_0x007b
            long r15 = r10.getLastRegularFrameNumber()     // Catch:{ all -> 0x00a8 }
            long r17 = r10.getLastReprocessFrameNumber()     // Catch:{ all -> 0x00a8 }
            long r19 = r10.getLastZslStillFrameNumber()     // Catch:{ all -> 0x00a8 }
            int r21 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r21 > 0) goto L_0x007b
            int r21 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            if (r21 > 0) goto L_0x007b
            int r21 = (r19 > r6 ? 1 : (r19 == r6 ? 0 : -1))
            if (r21 > 0) goto L_0x007b
            r11 = 1
            r22 = r2
            android.util.SparseArray<android.hardware.camera2.impl.CameraDeviceImpl$CaptureCallbackHolder> r2 = r1.mCaptureCallbackMap     // Catch:{ all -> 0x00ad }
            r2.removeAt(r0)     // Catch:{ all -> 0x00ad }
            goto L_0x007d
        L_0x007b:
            r22 = r2
        L_0x007d:
            monitor-exit(r13)     // Catch:{ all -> 0x00ad }
            r2 = r14
            if (r2 == 0) goto L_0x0083
            if (r11 == 0) goto L_0x0086
        L_0x0083:
            r9.remove()
        L_0x0086:
            if (r11 == 0) goto L_0x00a2
            android.hardware.camera2.impl.CameraDeviceImpl$10 r0 = new android.hardware.camera2.impl.CameraDeviceImpl$10
            r0.<init>(r12, r2, r10)
            r3 = r0
            long r13 = android.os.Binder.clearCallingIdentity()
            java.util.concurrent.Executor r0 = r2.getExecutor()     // Catch:{ all -> 0x009d }
            r0.execute(r3)     // Catch:{ all -> 0x009d }
            android.os.Binder.restoreCallingIdentity(r13)
            goto L_0x00a2
        L_0x009d:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r13)
            throw r0
        L_0x00a2:
            r0 = r9
            r2 = r22
            goto L_0x001b
        L_0x00a8:
            r0 = move-exception
            r22 = r2
        L_0x00ab:
            monitor-exit(r13)     // Catch:{ all -> 0x00ad }
            throw r0
        L_0x00ad:
            r0 = move-exception
            goto L_0x00ab
        L_0x00af:
            r22 = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.checkAndFireSequenceComplete():void");
    }

    public class CameraDeviceCallbacks extends ICameraDeviceCallbacks.Stub {
        public CameraDeviceCallbacks() {
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005d, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onDeviceError(int r6, android.hardware.camera2.impl.CaptureResultExtras r7) {
            /*
                r5 = this;
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r0 = r0.mInterfaceLock
                monitor-enter(r0)
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x005e }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r1 = r1.mRemoteDevice     // Catch:{ all -> 0x005e }
                if (r1 != 0) goto L_0x000f
                monitor-exit(r0)     // Catch:{ all -> 0x005e }
                return
            L_0x000f:
                switch(r6) {
                    case 0: goto L_0x0023;
                    case 1: goto L_0x001e;
                    case 2: goto L_0x0012;
                    case 3: goto L_0x001a;
                    case 4: goto L_0x001a;
                    case 5: goto L_0x001a;
                    case 6: goto L_0x0015;
                    default: goto L_0x0012;
                }     // Catch:{ all -> 0x005e }
            L_0x0012:
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x005e }
                goto L_0x0040
            L_0x0015:
                r1 = 3
                r5.scheduleNotifyError(r1)     // Catch:{ all -> 0x005e }
                goto L_0x005c
            L_0x001a:
                r5.onCaptureErrorLocked(r6, r7)     // Catch:{ all -> 0x005e }
                goto L_0x005c
            L_0x001e:
                r1 = 4
                r5.scheduleNotifyError(r1)     // Catch:{ all -> 0x005e }
                goto L_0x005c
            L_0x0023:
                long r1 = android.os.Binder.clearCallingIdentity()     // Catch:{ all -> 0x005e }
                android.hardware.camera2.impl.CameraDeviceImpl r3 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003b }
                java.util.concurrent.Executor r3 = r3.mDeviceExecutor     // Catch:{ all -> 0x003b }
                android.hardware.camera2.impl.CameraDeviceImpl r4 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003b }
                java.lang.Runnable r4 = r4.mCallOnDisconnected     // Catch:{ all -> 0x003b }
                r3.execute(r4)     // Catch:{ all -> 0x003b }
                android.os.Binder.restoreCallingIdentity(r1)     // Catch:{ all -> 0x005e }
                goto L_0x005c
            L_0x003b:
                r3 = move-exception
                android.os.Binder.restoreCallingIdentity(r1)     // Catch:{ all -> 0x005e }
                throw r3     // Catch:{ all -> 0x005e }
            L_0x0040:
                java.lang.String r1 = r1.TAG     // Catch:{ all -> 0x005e }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
                r2.<init>()     // Catch:{ all -> 0x005e }
                java.lang.String r3 = "Unknown error from camera device: "
                r2.append(r3)     // Catch:{ all -> 0x005e }
                r2.append(r6)     // Catch:{ all -> 0x005e }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x005e }
                android.util.Log.e(r1, r2)     // Catch:{ all -> 0x005e }
                r1 = 5
                r5.scheduleNotifyError(r1)     // Catch:{ all -> 0x005e }
            L_0x005c:
                monitor-exit(r0)     // Catch:{ all -> 0x005e }
                return
            L_0x005e:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005e }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.onDeviceError(int, android.hardware.camera2.impl.CaptureResultExtras):void");
        }

        private void scheduleNotifyError(int code) {
            boolean unused = CameraDeviceImpl.this.mInError = true;
            long ident = Binder.clearCallingIdentity();
            try {
                CameraDeviceImpl.this.mDeviceExecutor.execute(PooledLambda.obtainRunnable($$Lambda$CameraDeviceImpl$CameraDeviceCallbacks$Sm85frAzwGZVMAKNE_gwckYXVQ.INSTANCE, this, Integer.valueOf(code)).recycleOnUse());
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* access modifiers changed from: private */
        public void notifyError(int code) {
            if (!CameraDeviceImpl.this.isClosed()) {
                CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, code);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x003c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003e, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onRepeatingRequestError(long r6, int r8) {
            /*
                r5 = this;
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r0 = r0.mInterfaceLock
                monitor-enter(r0)
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r1 = r1.mRemoteDevice     // Catch:{ all -> 0x003f }
                if (r1 == 0) goto L_0x003d
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                int r1 = r1.mRepeatingRequestId     // Catch:{ all -> 0x003f }
                r2 = -1
                if (r1 != r2) goto L_0x0017
                goto L_0x003d
            L_0x0017:
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                android.hardware.camera2.impl.CameraDeviceImpl r3 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                int r3 = r3.mRepeatingRequestId     // Catch:{ all -> 0x003f }
                android.hardware.camera2.impl.CameraDeviceImpl r4 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                int[] r4 = r4.mRepeatingRequestTypes     // Catch:{ all -> 0x003f }
                r1.checkEarlyTriggerSequenceComplete(r3, r6, r4)     // Catch:{ all -> 0x003f }
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                int r1 = r1.mRepeatingRequestId     // Catch:{ all -> 0x003f }
                if (r1 != r8) goto L_0x003b
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                int unused = r1.mRepeatingRequestId = r2     // Catch:{ all -> 0x003f }
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x003f }
                r2 = 0
                int[] unused = r1.mRepeatingRequestTypes = r2     // Catch:{ all -> 0x003f }
            L_0x003b:
                monitor-exit(r0)     // Catch:{ all -> 0x003f }
                return
            L_0x003d:
                monitor-exit(r0)     // Catch:{ all -> 0x003f }
                return
            L_0x003f:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x003f }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.onRepeatingRequestError(long, int):void");
        }

        /* JADX INFO: finally extract failed */
        public void onDeviceIdle() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice != null) {
                    if (!CameraDeviceImpl.this.mIdle) {
                        long ident = Binder.clearCallingIdentity();
                        try {
                            CameraDeviceImpl.this.mDeviceExecutor.execute(CameraDeviceImpl.this.mCallOnIdle);
                            Binder.restoreCallingIdentity(ident);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(ident);
                            throw th;
                        }
                    }
                    boolean unused = CameraDeviceImpl.this.mIdle = true;
                }
            }
        }

        public void onCaptureStarted(CaptureResultExtras resultExtras, long timestamp) {
            int requestId = resultExtras.getRequestId();
            long frameNumber = resultExtras.getFrameNumber();
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice != null) {
                    CaptureCallbackHolder holder = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                    if (holder != null) {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            long ident = Binder.clearCallingIdentity();
                            try {
                                Executor executor = holder.getExecutor();
                                final CaptureResultExtras captureResultExtras = resultExtras;
                                final CaptureCallbackHolder captureCallbackHolder = holder;
                                final long j = timestamp;
                                AnonymousClass1 r9 = r1;
                                final long j2 = frameNumber;
                                AnonymousClass1 r1 = new Runnable() {
                                    public void run() {
                                        if (!CameraDeviceImpl.this.isClosed()) {
                                            int subsequenceId = captureResultExtras.getSubsequenceId();
                                            CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
                                            if (captureCallbackHolder.hasBatchedOutputs()) {
                                                Range<Integer> fpsRange = (Range) request.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                                for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                                    captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), j - ((((long) (subsequenceId - i)) * 1000000000) / ((long) fpsRange.getUpper().intValue())), j2 - ((long) (subsequenceId - i)));
                                                }
                                                return;
                                            }
                                            captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(captureResultExtras.getSubsequenceId()), j, j2);
                                        }
                                    }
                                };
                                executor.execute(r9);
                            } finally {
                                Binder.restoreCallingIdentity(ident);
                            }
                        }
                    }
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: android.hardware.camera2.TotalCaptureResult} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$2} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$3} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative r33, android.hardware.camera2.impl.CaptureResultExtras r34, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] r35) throws android.os.RemoteException {
            /*
                r32 = this;
                r15 = r32
                r14 = r33
                int r13 = r34.getRequestId()
                long r11 = r34.getFrameNumber()
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this
                java.lang.Object r8 = r0.mInterfaceLock
                monitor-enter(r8)
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.ICameraDeviceUserWrapper r0 = r0.mRemoteDevice     // Catch:{ all -> 0x0152 }
                if (r0 != 0) goto L_0x0025
                monitor-exit(r8)     // Catch:{ all -> 0x001b }
                return
            L_0x001b:
                r0 = move-exception
                r17 = r8
                r24 = r11
                r18 = r13
                r4 = r15
                goto L_0x015a
            L_0x0025:
                android.hardware.camera2.CameraCharacteristics$Key<android.util.Size> r0 = android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.CameraCharacteristics r1 = r1.getCharacteristics()     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.CameraCharacteristics$Key<android.util.Size> r2 = android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE     // Catch:{ all -> 0x0152 }
                java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0152 }
                android.util.Size r1 = (android.util.Size) r1     // Catch:{ all -> 0x0152 }
                r14.set(r0, r1)     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                android.util.SparseArray r0 = r0.mCaptureCallbackMap     // Catch:{ all -> 0x0152 }
                java.lang.Object r0 = r0.get(r13)     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.CameraDeviceImpl$CaptureCallbackHolder r0 = (android.hardware.camera2.impl.CameraDeviceImpl.CaptureCallbackHolder) r0     // Catch:{ all -> 0x0152 }
                r9 = r0
                int r0 = r34.getSubsequenceId()     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.CaptureRequest r0 = r9.getRequest(r0)     // Catch:{ all -> 0x0152 }
                r10 = r0
                int r0 = r34.getPartialResultCount()     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                int r1 = r1.mTotalPartialCount     // Catch:{ all -> 0x0152 }
                if (r0 >= r1) goto L_0x005d
                r0 = 1
                goto L_0x005e
            L_0x005d:
                r0 = 0
            L_0x005e:
                r30 = r0
                int r29 = r10.getRequestType()     // Catch:{ all -> 0x0152 }
                if (r9 != 0) goto L_0x0077
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x001b }
                android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r24 = r0.mFrameNumberTracker     // Catch:{ all -> 0x001b }
                r27 = 0
                r25 = r11
                r28 = r30
                r24.updateTracker(r25, r27, r28, r29)     // Catch:{ all -> 0x001b }
                monitor-exit(r8)     // Catch:{ all -> 0x001b }
                return
            L_0x0077:
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                boolean r0 = r0.isClosed()     // Catch:{ all -> 0x0152 }
                if (r0 == 0) goto L_0x0090
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x001b }
                android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r1 = r0.mFrameNumberTracker     // Catch:{ all -> 0x001b }
                r4 = 0
                r2 = r11
                r5 = r30
                r6 = r29
                r1.updateTracker(r2, r4, r5, r6)     // Catch:{ all -> 0x001b }
                monitor-exit(r8)     // Catch:{ all -> 0x001b }
                return
            L_0x0090:
                r0 = 0
                boolean r1 = r9.hasBatchedOutputs()     // Catch:{ all -> 0x0152 }
                if (r1 == 0) goto L_0x009d
                android.hardware.camera2.impl.CameraMetadataNative r1 = new android.hardware.camera2.impl.CameraMetadataNative     // Catch:{ all -> 0x001b }
                r1.<init>(r14)     // Catch:{ all -> 0x001b }
                goto L_0x009e
            L_0x009d:
                r1 = 0
            L_0x009e:
                r4 = r1
                if (r30 == 0) goto L_0x00c7
                android.hardware.camera2.CaptureResult r7 = new android.hardware.camera2.CaptureResult     // Catch:{ all -> 0x001b }
                r6 = r34
                r7.<init>(r14, r10, r6)     // Catch:{ all -> 0x001b }
                android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$2 r16 = new android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$2     // Catch:{ all -> 0x001b }
                r1 = r16
                r2 = r32
                r3 = r9
                r5 = r34
                r6 = r10
                r1.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x001b }
                r0 = r16
                r1 = r7
                r31 = r4
                r17 = r8
                r3 = r9
                r2 = r10
                r24 = r11
                r18 = r13
                r4 = r15
                r8 = r1
                r1 = r0
                goto L_0x011e
            L_0x00c7:
                android.hardware.camera2.impl.CameraDeviceImpl r1 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r1 = r1.mFrameNumberTracker     // Catch:{ all -> 0x0152 }
                java.util.List r21 = r1.popPartialResults(r11)     // Catch:{ all -> 0x0152 }
                android.hardware.camera2.CaptureResult$Key<java.lang.Long> r1 = android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP     // Catch:{ all -> 0x0152 }
                java.lang.Object r1 = r14.get(r1)     // Catch:{ all -> 0x0152 }
                java.lang.Long r1 = (java.lang.Long) r1     // Catch:{ all -> 0x0152 }
                long r1 = r1.longValue()     // Catch:{ all -> 0x0152 }
                r3 = r9
                r7 = r10
                r9 = r1
                android.hardware.camera2.CaptureRequest$Key<android.util.Range<java.lang.Integer>> r1 = android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE     // Catch:{ all -> 0x0152 }
                java.lang.Object r1 = r7.get(r1)     // Catch:{ all -> 0x0152 }
                android.util.Range r1 = (android.util.Range) r1     // Catch:{ all -> 0x0152 }
                r24 = r11
                r12 = r1
                int r11 = r34.getSubsequenceId()     // Catch:{ all -> 0x014b }
                android.hardware.camera2.TotalCaptureResult r1 = new android.hardware.camera2.TotalCaptureResult     // Catch:{ all -> 0x014b }
                int r22 = r3.getSessionId()     // Catch:{ all -> 0x014b }
                r17 = r1
                r18 = r33
                r19 = r7
                r20 = r34
                r23 = r35
                r17.<init>(r18, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x014b }
                r16 = r1
                android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$3 r1 = new android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks$3     // Catch:{ all -> 0x014b }
                r5 = r1
                r6 = r32
                r2 = r7
                r7 = r3
                r17 = r8
                r8 = r4
                r18 = r13
                r13 = r34
                r14 = r21
                r31 = r4
                r4 = r15
                r15 = r2
                r5.<init>(r7, r8, r9, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x015c }
                r0 = r1
                r8 = r16
            L_0x011e:
                long r5 = android.os.Binder.clearCallingIdentity()     // Catch:{ all -> 0x015c }
                r11 = r5
                java.util.concurrent.Executor r0 = r3.getExecutor()     // Catch:{ all -> 0x0146 }
                r0.execute(r1)     // Catch:{ all -> 0x0146 }
                android.os.Binder.restoreCallingIdentity(r11)     // Catch:{ all -> 0x015c }
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x015c }
                android.hardware.camera2.impl.CameraDeviceImpl$FrameNumberTracker r5 = r0.mFrameNumberTracker     // Catch:{ all -> 0x015c }
                r6 = r24
                r9 = r30
                r10 = r29
                r5.updateTracker(r6, r8, r9, r10)     // Catch:{ all -> 0x015c }
                if (r30 != 0) goto L_0x0144
                android.hardware.camera2.impl.CameraDeviceImpl r0 = android.hardware.camera2.impl.CameraDeviceImpl.this     // Catch:{ all -> 0x015c }
                r0.checkAndFireSequenceComplete()     // Catch:{ all -> 0x015c }
            L_0x0144:
                monitor-exit(r17)     // Catch:{ all -> 0x015c }
                return
            L_0x0146:
                r0 = move-exception
                android.os.Binder.restoreCallingIdentity(r11)     // Catch:{ all -> 0x015c }
                throw r0     // Catch:{ all -> 0x015c }
            L_0x014b:
                r0 = move-exception
                r17 = r8
                r18 = r13
                r4 = r15
                goto L_0x015a
            L_0x0152:
                r0 = move-exception
                r17 = r8
                r24 = r11
                r18 = r13
                r4 = r15
            L_0x015a:
                monitor-exit(r17)     // Catch:{ all -> 0x015c }
                throw r0
            L_0x015c:
                r0 = move-exception
                goto L_0x015a
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.onResultReceived(android.hardware.camera2.impl.CameraMetadataNative, android.hardware.camera2.impl.CaptureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[]):void");
        }

        public void onPrepared(int streamId) {
            OutputConfiguration output;
            StateCallbackKK sessionCallback;
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                output = (OutputConfiguration) CameraDeviceImpl.this.mConfiguredOutputs.get(streamId);
                sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
            }
            if (sessionCallback != null) {
                if (output == null) {
                    Log.w(CameraDeviceImpl.this.TAG, "onPrepared invoked for unknown output Surface");
                    return;
                }
                for (Surface surface : output.getSurfaces()) {
                    sessionCallback.onSurfacePrepared(surface);
                }
            }
        }

        public void onRequestQueueEmpty() {
            StateCallbackKK sessionCallback;
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
            }
            if (sessionCallback != null) {
                sessionCallback.onRequestQueueEmpty();
            }
        }

        /* JADX INFO: finally extract failed */
        private void onCaptureErrorLocked(int errorCode, CaptureResultExtras resultExtras) {
            int i = errorCode;
            int requestId = resultExtras.getRequestId();
            int subsequenceId = resultExtras.getSubsequenceId();
            long frameNumber = resultExtras.getFrameNumber();
            String errorPhysicalCameraId = resultExtras.getErrorPhysicalCameraId();
            CaptureCallbackHolder holder = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
            CaptureRequest request = holder.getRequest(subsequenceId);
            if (i == 5) {
                for (Surface surface : ((OutputConfiguration) CameraDeviceImpl.this.mConfiguredOutputs.get(resultExtras.getErrorStreamId())).getSurfaces()) {
                    if (request.containsTarget(surface)) {
                        final CaptureCallbackHolder captureCallbackHolder = holder;
                        final CaptureRequest captureRequest = request;
                        final Surface surface2 = surface;
                        Surface surface3 = surface;
                        final long j = frameNumber;
                        AnonymousClass4 r1 = new Runnable() {
                            public void run() {
                                if (!CameraDeviceImpl.this.isClosed()) {
                                    captureCallbackHolder.getCallback().onCaptureBufferLost(CameraDeviceImpl.this, captureRequest, surface2, j);
                                }
                            }
                        };
                        long ident = Binder.clearCallingIdentity();
                        try {
                            holder.getExecutor().execute(r1);
                            Binder.restoreCallingIdentity(ident);
                            AnonymousClass4 r0 = r1;
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(ident);
                            throw th;
                        }
                    }
                }
                long j2 = frameNumber;
                CaptureCallbackHolder captureCallbackHolder2 = holder;
                CaptureRequest captureRequest2 = request;
                int i2 = subsequenceId;
                int i3 = requestId;
                return;
            }
            int reason = 0;
            final CaptureRequest request2 = request;
            boolean mayHaveBuffers = i == 4;
            if (CameraDeviceImpl.this.mCurrentSession != null && CameraDeviceImpl.this.mCurrentSession.isAborting()) {
                reason = 1;
            }
            final CaptureCallbackHolder holder2 = holder;
            long frameNumber2 = frameNumber;
            int i4 = subsequenceId;
            int i5 = requestId;
            final CaptureFailure captureFailure = new CaptureFailure(request2, reason, mayHaveBuffers, requestId, frameNumber2, errorPhysicalCameraId);
            AnonymousClass5 r10 = new Runnable() {
                public void run() {
                    if (!CameraDeviceImpl.this.isClosed()) {
                        holder2.getCallback().onCaptureFailed(CameraDeviceImpl.this, request2, captureFailure);
                    }
                }
            };
            CameraDeviceImpl.this.mFrameNumberTracker.updateTracker(frameNumber2, true, request2.getRequestType());
            CameraDeviceImpl.this.checkAndFireSequenceComplete();
            long ident2 = Binder.clearCallingIdentity();
            try {
                holder2.getExecutor().execute(r10);
                Binder.restoreCallingIdentity(ident2);
                AnonymousClass5 r02 = r10;
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(ident2);
                throw th2;
            }
        }
    }

    private static class CameraHandlerExecutor implements Executor {
        private final Handler mHandler;

        public CameraHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(Runnable command) {
            this.mHandler.post(command);
        }
    }

    static Executor checkExecutor(Executor executor) {
        return executor == null ? checkAndWrapHandler((Handler) null) : executor;
    }

    public static <T> Executor checkExecutor(Executor executor, T callback) {
        return callback != null ? checkExecutor(executor) : executor;
    }

    public static Executor checkAndWrapHandler(Handler handler) {
        return new CameraHandlerExecutor(checkHandler(handler));
    }

    static Handler checkHandler(Handler handler) {
        if (handler != null) {
            return handler;
        }
        Looper looper = Looper.myLooper();
        if (looper != null) {
            return new Handler(looper);
        }
        throw new IllegalArgumentException("No handler given, and current thread has no looper!");
    }

    static <T> Handler checkHandler(Handler handler, T callback) {
        if (callback != null) {
            return checkHandler(handler);
        }
        return handler;
    }

    private void checkIfCameraClosedOrInError() throws CameraAccessException {
        if (this.mRemoteDevice == null) {
            throw new IllegalStateException("CameraDevice was already closed");
        } else if (this.mInError) {
            throw new CameraAccessException(3, "The camera device has encountered a serious error");
        }
    }

    /* access modifiers changed from: private */
    public boolean isClosed() {
        return this.mClosing.get();
    }

    /* access modifiers changed from: private */
    public CameraCharacteristics getCharacteristics() {
        return this.mCharacteristics;
    }

    public void binderDied() {
        String str = this.TAG;
        Log.w(str, "CameraDevice " + this.mCameraId + " died unexpectedly");
        if (this.mRemoteDevice != null) {
            this.mInError = true;
            Runnable r = new Runnable() {
                public void run() {
                    if (!CameraDeviceImpl.this.isClosed()) {
                        CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, 5);
                    }
                }
            };
            long ident = Binder.clearCallingIdentity();
            try {
                this.mDeviceExecutor.execute(r);
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }
}
