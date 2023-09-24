package android.view;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Looper;
import android.p007os.MessageQueue;
import android.util.Log;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public abstract class DisplayEventReceiver {
    private static final String TAG = "DisplayEventReceiver";
    public static final int VSYNC_SOURCE_APP = 0;
    public static final int VSYNC_SOURCE_SURFACE_FLINGER = 1;
    private final CloseGuard mCloseGuard;
    private MessageQueue mMessageQueue;
    @UnsupportedAppUsage
    private long mReceiverPtr;

    private static native void nativeDispose(long j);

    private static native long nativeInit(WeakReference<DisplayEventReceiver> weakReference, MessageQueue messageQueue, int i);

    @FastNative
    private static native void nativeScheduleVsync(long j);

    @UnsupportedAppUsage
    public DisplayEventReceiver(Looper looper) {
        this(looper, 0);
    }

    public DisplayEventReceiver(Looper looper, int vsyncSource) {
        this.mCloseGuard = CloseGuard.get();
        if (looper == null) {
            throw new IllegalArgumentException("looper must not be null");
        }
        this.mMessageQueue = looper.getQueue();
        this.mReceiverPtr = nativeInit(new WeakReference(this), this.mMessageQueue, vsyncSource);
        this.mCloseGuard.open("dispose");
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public void dispose() {
        dispose(false);
    }

    private void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mReceiverPtr != 0) {
            nativeDispose(this.mReceiverPtr);
            this.mReceiverPtr = 0L;
        }
        this.mMessageQueue = null;
    }

    @UnsupportedAppUsage
    public void onVsync(long timestampNanos, long physicalDisplayId, int frame) {
    }

    @UnsupportedAppUsage
    public void onHotplug(long timestampNanos, long physicalDisplayId, boolean connected) {
    }

    public void onConfigChanged(long timestampNanos, long physicalDisplayId, int configId) {
    }

    @UnsupportedAppUsage
    public void scheduleVsync() {
        if (this.mReceiverPtr == 0) {
            Log.m64w(TAG, "Attempted to schedule a vertical sync pulse but the display event receiver has already been disposed.");
        } else {
            nativeScheduleVsync(this.mReceiverPtr);
        }
    }

    @UnsupportedAppUsage
    private void dispatchVsync(long timestampNanos, long physicalDisplayId, int frame) {
        onVsync(timestampNanos, physicalDisplayId, frame);
    }

    @UnsupportedAppUsage
    private void dispatchHotplug(long timestampNanos, long physicalDisplayId, boolean connected) {
        onHotplug(timestampNanos, physicalDisplayId, connected);
    }

    private void dispatchConfigChanged(long timestampNanos, long physicalDisplayId, int configId) {
        onConfigChanged(timestampNanos, physicalDisplayId, configId);
    }
}
