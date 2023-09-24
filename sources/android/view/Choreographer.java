package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.FrameInfo;
import android.hardware.display.DisplayManagerGlobal;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.SystemClock;
import android.p007os.SystemProperties;
import android.p007os.Trace;
import android.util.Log;
import android.util.TimeUtils;
import android.view.animation.AnimationUtils;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public final class Choreographer {
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_COMMIT = 4;
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_INSETS_ANIMATION = 2;
    private static final int CALLBACK_LAST = 4;
    public static final int CALLBACK_TRAVERSAL = 3;
    private static final boolean DEBUG_FRAMES = false;
    private static final boolean DEBUG_JANK = false;
    private static final int MOTION_EVENT_ACTION_CANCEL = 3;
    private static final int MOTION_EVENT_ACTION_DOWN = 0;
    private static final int MOTION_EVENT_ACTION_MOVE = 2;
    private static final int MOTION_EVENT_ACTION_UP = 1;
    private static final int MSG_DO_FRAME = 0;
    private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
    private static final int MSG_DO_SCHEDULE_VSYNC = 1;
    private static final boolean OPTS_INPUT = true;
    private static final String TAG = "Choreographer";
    private static volatile Choreographer mMainInstance;
    private CallbackRecord mCallbackPool;
    @UnsupportedAppUsage
    private final CallbackQueue[] mCallbackQueues;
    private boolean mCallbacksRunning;
    private boolean mConsumedDown;
    private boolean mConsumedMove;
    private boolean mDebugPrintNextFrameTimeDelta;
    @UnsupportedAppUsage
    private final FrameDisplayEventReceiver mDisplayEventReceiver;
    private int mFPSDivisor;
    FrameInfo mFrameInfo;
    @UnsupportedAppUsage
    private long mFrameIntervalNanos;
    private boolean mFrameScheduled;
    private final FrameHandler mHandler;
    private boolean mIsVsyncScheduled;
    @UnsupportedAppUsage
    private long mLastFrameTimeNanos;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private final Object mLock;
    private final Looper mLooper;
    private int mMotionEventType;
    private int mTouchMoveNum;
    private static final long DEFAULT_FRAME_DELAY = 10;
    private static volatile long sFrameDelay = DEFAULT_FRAME_DELAY;
    private static final ThreadLocal<Choreographer> sThreadInstance = new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
            Looper looper = Looper.myLooper();
            if (looper == null) {
                throw new IllegalStateException("The current thread must have a looper!");
            }
            Choreographer choreographer = new Choreographer(looper, 0);
            if (looper == Looper.getMainLooper()) {
                Choreographer unused = Choreographer.mMainInstance = choreographer;
            }
            return choreographer;
        }
    };
    private static final ThreadLocal<Choreographer> sSfThreadInstance = new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
            Looper looper = Looper.myLooper();
            if (looper == null) {
                throw new IllegalStateException("The current thread must have a looper!");
            }
            return new Choreographer(looper, 1);
        }
    };
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769497)
    private static final boolean USE_VSYNC = SystemProperties.getBoolean("debug.choreographer.vsync", true);
    private static final boolean USE_FRAME_TIME = SystemProperties.getBoolean("debug.choreographer.frametime", true);
    private static final int SKIPPED_FRAME_WARNING_LIMIT = SystemProperties.getInt("debug.choreographer.skipwarning", 30);
    private static final Object FRAME_CALLBACK_TOKEN = new Object() { // from class: android.view.Choreographer.3
        public String toString() {
            return "FRAME_CALLBACK_TOKEN";
        }
    };
    private static final String[] CALLBACK_TRACE_TITLES = {"input", "animation", "insets_animation", "traversal", "commit"};

    /* loaded from: classes4.dex */
    public interface FrameCallback {
        void doFrame(long j);
    }

    private Choreographer(Looper looper, int vsyncSource) {
        FrameDisplayEventReceiver frameDisplayEventReceiver;
        this.mLock = new Object();
        this.mFPSDivisor = 1;
        this.mTouchMoveNum = -1;
        this.mMotionEventType = -1;
        this.mConsumedMove = false;
        this.mConsumedDown = false;
        this.mIsVsyncScheduled = false;
        this.mFrameInfo = new FrameInfo();
        this.mLooper = looper;
        this.mHandler = new FrameHandler(looper);
        if (USE_VSYNC) {
            frameDisplayEventReceiver = new FrameDisplayEventReceiver(looper, vsyncSource);
        } else {
            frameDisplayEventReceiver = null;
        }
        this.mDisplayEventReceiver = frameDisplayEventReceiver;
        this.mLastFrameTimeNanos = Long.MIN_VALUE;
        this.mFrameIntervalNanos = 1.0E9f / getRefreshRate();
        this.mCallbackQueues = new CallbackQueue[5];
        for (int i = 0; i <= 4; i++) {
            this.mCallbackQueues[i] = new CallbackQueue();
        }
        setFPSDivisor(SystemProperties.getInt(ThreadedRenderer.DEBUG_FPS_DIVISOR, 1));
    }

    private static float getRefreshRate() {
        DisplayInfo di = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
        return di.getMode().getRefreshRate();
    }

    public static Choreographer getInstance() {
        return sThreadInstance.get();
    }

    @UnsupportedAppUsage
    public static Choreographer getSfInstance() {
        return sSfThreadInstance.get();
    }

    public void setMotionEventInfo(int motionEventType, int touchMoveNum) {
        synchronized (this) {
            this.mTouchMoveNum = touchMoveNum;
            this.mMotionEventType = motionEventType;
        }
    }

    public static Choreographer getMainThreadInstance() {
        return mMainInstance;
    }

    public static void releaseInstance() {
        Choreographer old = sThreadInstance.get();
        sThreadInstance.remove();
        old.dispose();
    }

    private void dispose() {
        this.mDisplayEventReceiver.dispose();
    }

    public static long getFrameDelay() {
        return sFrameDelay;
    }

    public static void setFrameDelay(long frameDelay) {
        sFrameDelay = frameDelay;
    }

    public static long subtractFrameDelay(long delayMillis) {
        long frameDelay = sFrameDelay;
        if (delayMillis <= frameDelay) {
            return 0L;
        }
        return delayMillis - frameDelay;
    }

    public long getFrameIntervalNanos() {
        return this.mFrameIntervalNanos;
    }

    void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.println("Choreographer:");
        writer.print(innerPrefix);
        writer.print("mFrameScheduled=");
        writer.println(this.mFrameScheduled);
        writer.print(innerPrefix);
        writer.print("mLastFrameTime=");
        writer.println(TimeUtils.formatUptime(this.mLastFrameTimeNanos / TimeUtils.NANOS_PER_MS));
    }

    public void postCallback(int callbackType, Runnable action, Object token) {
        postCallbackDelayed(callbackType, action, token, 0L);
    }

    public void postCallbackDelayed(int callbackType, Runnable action, Object token, long delayMillis) {
        if (action == null) {
            throw new IllegalArgumentException("action must not be null");
        }
        if (callbackType < 0 || callbackType > 4) {
            throw new IllegalArgumentException("callbackType is invalid");
        }
        postCallbackDelayedInternal(callbackType, action, token, delayMillis);
    }

    private void postCallbackDelayedInternal(int callbackType, Object action, Object token, long delayMillis) {
        synchronized (this.mLock) {
            long now = SystemClock.uptimeMillis();
            long dueTime = now + delayMillis;
            this.mCallbackQueues[callbackType].addCallbackLocked(dueTime, action, token);
            if (dueTime <= now) {
                scheduleFrameLocked(now);
            } else {
                Message msg = this.mHandler.obtainMessage(2, action);
                msg.arg1 = callbackType;
                msg.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(msg, dueTime);
            }
        }
    }

    public void removeCallbacks(int callbackType, Runnable action, Object token) {
        if (callbackType < 0 || callbackType > 4) {
            throw new IllegalArgumentException("callbackType is invalid");
        }
        removeCallbacksInternal(callbackType, action, token);
    }

    private void removeCallbacksInternal(int callbackType, Object action, Object token) {
        synchronized (this.mLock) {
            this.mCallbackQueues[callbackType].removeCallbacksLocked(action, token);
            if (action != null && token == null) {
                this.mHandler.removeMessages(2, action);
            }
        }
    }

    public void postFrameCallback(FrameCallback callback) {
        postFrameCallbackDelayed(callback, 0L);
    }

    public void postFrameCallbackDelayed(FrameCallback callback, long delayMillis) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        postCallbackDelayedInternal(1, callback, FRAME_CALLBACK_TOKEN, delayMillis);
    }

    public void removeFrameCallback(FrameCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        removeCallbacksInternal(1, callback, FRAME_CALLBACK_TOKEN);
    }

    @UnsupportedAppUsage
    public long getFrameTime() {
        return getFrameTimeNanos() / TimeUtils.NANOS_PER_MS;
    }

    @UnsupportedAppUsage
    public long getFrameTimeNanos() {
        long nanoTime;
        synchronized (this.mLock) {
            if (!this.mCallbacksRunning) {
                throw new IllegalStateException("This method must only be called as part of a callback while a frame is in progress.");
            }
            nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
        }
        return nanoTime;
    }

    public long getLastFrameTimeNanos() {
        long nanoTime;
        synchronized (this.mLock) {
            nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
        }
        return nanoTime;
    }

    private void scheduleFrameLocked(long now) {
        if (!this.mFrameScheduled) {
            this.mFrameScheduled = true;
            if (!this.mIsVsyncScheduled && System.nanoTime() - this.mLastFrameTimeNanos > this.mFrameIntervalNanos) {
                Trace.traceBegin(8L, "scheduleFrameLocked-mMotionEventType:" + this.mMotionEventType + " mTouchMoveNum:" + this.mTouchMoveNum + " mConsumedDown:" + this.mConsumedDown + " mConsumedMove:" + this.mConsumedMove);
                Trace.traceEnd(8L);
                synchronized (this) {
                    switch (this.mMotionEventType) {
                        case 0:
                            this.mConsumedMove = false;
                            if (!this.mConsumedDown) {
                                Message msg = this.mHandler.obtainMessage(0);
                                msg.setAsynchronous(true);
                                this.mHandler.sendMessageAtFrontOfQueue(msg);
                                this.mConsumedDown = true;
                                return;
                            }
                            break;
                        case 1:
                        case 3:
                            this.mConsumedMove = false;
                            this.mConsumedDown = false;
                            break;
                        case 2:
                            this.mConsumedDown = false;
                            if (this.mTouchMoveNum == 1 && !this.mConsumedMove) {
                                Message msg2 = this.mHandler.obtainMessage(0);
                                msg2.setAsynchronous(true);
                                this.mHandler.sendMessageAtFrontOfQueue(msg2);
                                this.mConsumedMove = true;
                                return;
                            }
                            break;
                    }
                }
            }
            if (USE_VSYNC) {
                if (!isRunningOnLooperThreadLocked()) {
                    Message msg3 = this.mHandler.obtainMessage(1);
                    msg3.setAsynchronous(true);
                    this.mHandler.sendMessageAtFrontOfQueue(msg3);
                    return;
                }
                scheduleVsyncLocked();
                return;
            }
            long nextFrameTime = Math.max((this.mLastFrameTimeNanos / TimeUtils.NANOS_PER_MS) + sFrameDelay, now);
            Message msg4 = this.mHandler.obtainMessage(0);
            msg4.setAsynchronous(true);
            this.mHandler.sendMessageAtTime(msg4, nextFrameTime);
        }
    }

    void setFPSDivisor(int divisor) {
        if (divisor <= 0) {
            divisor = 1;
        }
        this.mFPSDivisor = divisor;
        ThreadedRenderer.setFPSDivisor(divisor);
    }

    @UnsupportedAppUsage
    void doFrame(long frameTimeNanos, int frame) {
        long frameTimeNanos2;
        synchronized (this.mLock) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                this.mIsVsyncScheduled = false;
                if (this.mFrameScheduled) {
                    long startNanos = System.nanoTime();
                    long jitterNanos = startNanos - frameTimeNanos;
                    if (jitterNanos >= this.mFrameIntervalNanos) {
                        long skippedFrames = jitterNanos / this.mFrameIntervalNanos;
                        if (skippedFrames >= SKIPPED_FRAME_WARNING_LIMIT) {
                            Log.m68i(TAG, "Skipped " + skippedFrames + " frames!  The application may be doing too much work on its main thread.");
                        }
                        long lastFrameOffset = jitterNanos % this.mFrameIntervalNanos;
                        frameTimeNanos2 = startNanos - lastFrameOffset;
                    } else {
                        frameTimeNanos2 = frameTimeNanos;
                    }
                    if (frameTimeNanos2 < this.mLastFrameTimeNanos) {
                        scheduleVsyncLocked();
                        return;
                    }
                    if (this.mFPSDivisor > 1) {
                        long timeSinceVsync = frameTimeNanos2 - this.mLastFrameTimeNanos;
                        if (timeSinceVsync < this.mFrameIntervalNanos * this.mFPSDivisor && timeSinceVsync > 0) {
                            scheduleVsyncLocked();
                            return;
                        }
                    }
                    this.mFrameInfo.setVsync(frameTimeNanos, frameTimeNanos2);
                    this.mFrameScheduled = false;
                    this.mLastFrameTimeNanos = frameTimeNanos2;
                    try {
                        Trace.traceBegin(8L, "Choreographer#doFrame");
                        AnimationUtils.lockAnimationClock(frameTimeNanos2 / TimeUtils.NANOS_PER_MS);
                        this.mFrameInfo.markInputHandlingStart();
                        doCallbacks(0, frameTimeNanos2);
                        this.mFrameInfo.markAnimationsStart();
                        doCallbacks(1, frameTimeNanos2);
                        doCallbacks(2, frameTimeNanos2);
                        this.mFrameInfo.markPerformTraversalsStart();
                        doCallbacks(3, frameTimeNanos2);
                        doCallbacks(4, frameTimeNanos2);
                    } finally {
                        AnimationUtils.unlockAnimationClock();
                        Trace.traceEnd(8L);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0051 A[Catch: all -> 0x0070, TRY_LEAVE, TryCatch #0 {all -> 0x0070, blocks: (B:18:0x0045, B:20:0x0051), top: B:54:0x0045 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void doCallbacks(int callbackType, long frameTimeNanos) {
        long frameTimeNanos2;
        CallbackRecord callbacks;
        CallbackRecord c;
        synchronized (this.mLock) {
            try {
                long now = System.nanoTime();
                CallbackRecord callbacks2 = this.mCallbackQueues[callbackType].extractDueCallbacksLocked(now / TimeUtils.NANOS_PER_MS);
                if (callbacks2 == null) {
                    return;
                }
                this.mCallbacksRunning = true;
                try {
                    if (callbackType == 4) {
                        long jitterNanos = now - frameTimeNanos;
                        Trace.traceCounter(8L, "jitterNanos", (int) jitterNanos);
                        if (jitterNanos >= this.mFrameIntervalNanos * 2) {
                            long lastFrameOffset = (jitterNanos % this.mFrameIntervalNanos) + this.mFrameIntervalNanos;
                            frameTimeNanos2 = now - lastFrameOffset;
                            try {
                                this.mLastFrameTimeNanos = frameTimeNanos2;
                                callbacks = callbacks2;
                                Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[callbackType]);
                                for (c = callbacks; c != null; c = c.next) {
                                    c.run(frameTimeNanos2);
                                }
                                synchronized (this.mLock) {
                                    this.mCallbacksRunning = false;
                                    do {
                                        CallbackRecord next = callbacks.next;
                                        recycleCallbackLocked(callbacks);
                                        callbacks = next;
                                    } while (callbacks != null);
                                }
                                Trace.traceEnd(8L);
                                return;
                            } catch (Throwable th) {
                                th = th;
                                while (true) {
                                    try {
                                        break;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                    Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[callbackType]);
                    while (c != null) {
                    }
                    synchronized (this.mLock) {
                    }
                } catch (Throwable th3) {
                    synchronized (this.mLock) {
                        this.mCallbacksRunning = false;
                        while (true) {
                            CallbackRecord next2 = callbacks.next;
                            recycleCallbackLocked(callbacks);
                            callbacks = next2;
                            if (callbacks == null) {
                                break;
                            }
                        }
                        Trace.traceEnd(8L);
                        throw th3;
                    }
                }
                frameTimeNanos2 = frameTimeNanos;
                callbacks = callbacks2;
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    void doScheduleVsync() {
        synchronized (this.mLock) {
            if (this.mFrameScheduled) {
                scheduleVsyncLocked();
            }
        }
    }

    void doScheduleCallback(int callbackType) {
        synchronized (this.mLock) {
            if (!this.mFrameScheduled) {
                long now = SystemClock.uptimeMillis();
                if (this.mCallbackQueues[callbackType].hasDueCallbacksLocked(now)) {
                    scheduleFrameLocked(now);
                }
            }
        }
    }

    @UnsupportedAppUsage
    private void scheduleVsyncLocked() {
        this.mDisplayEventReceiver.scheduleVsync();
        this.mIsVsyncScheduled = true;
    }

    private boolean isRunningOnLooperThreadLocked() {
        return Looper.myLooper() == this.mLooper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CallbackRecord obtainCallbackLocked(long dueTime, Object action, Object token) {
        CallbackRecord callback = this.mCallbackPool;
        if (callback == null) {
            callback = new CallbackRecord();
        } else {
            this.mCallbackPool = callback.next;
            callback.next = null;
        }
        callback.dueTime = dueTime;
        callback.action = action;
        callback.token = token;
        return callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleCallbackLocked(CallbackRecord callback) {
        callback.action = null;
        callback.token = null;
        callback.next = this.mCallbackPool;
        this.mCallbackPool = callback;
    }

    /* loaded from: classes4.dex */
    private final class FrameHandler extends Handler {
        public FrameHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Choreographer.this.doFrame(System.nanoTime(), 0);
                    return;
                case 1:
                    Choreographer.this.doScheduleVsync();
                    return;
                case 2:
                    Choreographer.this.doScheduleCallback(msg.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class FrameDisplayEventReceiver extends DisplayEventReceiver implements Runnable {
        private int mFrame;
        private boolean mHavePendingVsync;
        private long mTimestampNanos;

        public FrameDisplayEventReceiver(Looper looper, int vsyncSource) {
            super(looper, vsyncSource);
        }

        @Override // android.view.DisplayEventReceiver
        public void onVsync(long timestampNanos, long physicalDisplayId, int frame) {
            long now = System.nanoTime();
            if (timestampNanos > now) {
                Log.m64w(Choreographer.TAG, "Frame time is " + (((float) (timestampNanos - now)) * 1.0E-6f) + " ms in the future!  Check that graphics HAL is generating vsync timestamps using the correct timebase.");
                timestampNanos = now;
            }
            if (this.mHavePendingVsync) {
                Log.m64w(Choreographer.TAG, "Already have a pending vsync event.  There should only be one at a time.");
            } else {
                this.mHavePendingVsync = true;
            }
            this.mTimestampNanos = timestampNanos;
            this.mFrame = frame;
            Message msg = Message.obtain(Choreographer.this.mHandler, this);
            msg.setAsynchronous(true);
            Choreographer.this.mHandler.sendMessageAtTime(msg, timestampNanos / TimeUtils.NANOS_PER_MS);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mHavePendingVsync = false;
            Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame);
        }
    }

    /* loaded from: classes4.dex */
    private static final class CallbackRecord {
        public Object action;
        public long dueTime;
        public CallbackRecord next;
        public Object token;

        private CallbackRecord() {
        }

        @UnsupportedAppUsage
        public void run(long frameTimeNanos) {
            if (this.token == Choreographer.FRAME_CALLBACK_TOKEN) {
                ((FrameCallback) this.action).doFrame(frameTimeNanos);
            } else {
                ((Runnable) this.action).run();
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class CallbackQueue {
        private CallbackRecord mHead;

        private CallbackQueue() {
        }

        public boolean hasDueCallbacksLocked(long now) {
            return this.mHead != null && this.mHead.dueTime <= now;
        }

        public CallbackRecord extractDueCallbacksLocked(long now) {
            CallbackRecord callbacks = this.mHead;
            if (callbacks == null || callbacks.dueTime > now) {
                return null;
            }
            CallbackRecord last = callbacks;
            CallbackRecord next = last.next;
            while (true) {
                if (next == null) {
                    break;
                } else if (next.dueTime > now) {
                    last.next = null;
                    break;
                } else {
                    last = next;
                    next = next.next;
                }
            }
            this.mHead = next;
            return callbacks;
        }

        @UnsupportedAppUsage
        public void addCallbackLocked(long dueTime, Object action, Object token) {
            CallbackRecord callback = Choreographer.this.obtainCallbackLocked(dueTime, action, token);
            CallbackRecord entry = this.mHead;
            if (entry == null) {
                this.mHead = callback;
            } else if (dueTime < entry.dueTime) {
                callback.next = entry;
                this.mHead = callback;
            } else {
                while (true) {
                    if (entry.next == null) {
                        break;
                    } else if (dueTime < entry.next.dueTime) {
                        callback.next = entry.next;
                        break;
                    } else {
                        entry = entry.next;
                    }
                }
                entry.next = callback;
            }
        }

        public void removeCallbacksLocked(Object action, Object token) {
            CallbackRecord predecessor = null;
            CallbackRecord callback = this.mHead;
            while (callback != null) {
                CallbackRecord next = callback.next;
                if ((action == null || callback.action == action) && (token == null || callback.token == token)) {
                    if (predecessor != null) {
                        predecessor.next = next;
                    } else {
                        this.mHead = next;
                    }
                    Choreographer.this.recycleCallbackLocked(callback);
                } else {
                    predecessor = callback;
                }
                callback = next;
            }
        }
    }
}
