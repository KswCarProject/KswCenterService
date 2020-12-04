package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.FrameInfo;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.TimeUtils;
import android.view.animation.AnimationUtils;
import java.io.PrintWriter;

public final class Choreographer {
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_COMMIT = 4;
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_INSETS_ANIMATION = 2;
    private static final int CALLBACK_LAST = 4;
    private static final String[] CALLBACK_TRACE_TITLES = {"input", "animation", "insets_animation", "traversal", "commit"};
    public static final int CALLBACK_TRAVERSAL = 3;
    private static final boolean DEBUG_FRAMES = false;
    private static final boolean DEBUG_JANK = false;
    private static final long DEFAULT_FRAME_DELAY = 10;
    /* access modifiers changed from: private */
    public static final Object FRAME_CALLBACK_TOKEN = new Object() {
        public String toString() {
            return "FRAME_CALLBACK_TOKEN";
        }
    };
    private static final int MOTION_EVENT_ACTION_CANCEL = 3;
    private static final int MOTION_EVENT_ACTION_DOWN = 0;
    private static final int MOTION_EVENT_ACTION_MOVE = 2;
    private static final int MOTION_EVENT_ACTION_UP = 1;
    private static final int MSG_DO_FRAME = 0;
    private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
    private static final int MSG_DO_SCHEDULE_VSYNC = 1;
    private static final boolean OPTS_INPUT = true;
    private static final int SKIPPED_FRAME_WARNING_LIMIT = SystemProperties.getInt("debug.choreographer.skipwarning", 30);
    private static final String TAG = "Choreographer";
    private static final boolean USE_FRAME_TIME = SystemProperties.getBoolean("debug.choreographer.frametime", true);
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769497)
    private static final boolean USE_VSYNC = SystemProperties.getBoolean("debug.choreographer.vsync", true);
    /* access modifiers changed from: private */
    public static volatile Choreographer mMainInstance;
    private static volatile long sFrameDelay = DEFAULT_FRAME_DELAY;
    private static final ThreadLocal<Choreographer> sSfThreadInstance = new ThreadLocal<Choreographer>() {
        /* access modifiers changed from: protected */
        public Choreographer initialValue() {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                return new Choreographer(looper, 1);
            }
            throw new IllegalStateException("The current thread must have a looper!");
        }
    };
    private static final ThreadLocal<Choreographer> sThreadInstance = new ThreadLocal<Choreographer>() {
        /* access modifiers changed from: protected */
        public Choreographer initialValue() {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                Choreographer choreographer = new Choreographer(looper, 0);
                if (looper == Looper.getMainLooper()) {
                    Choreographer unused = Choreographer.mMainInstance = choreographer;
                }
                return choreographer;
            }
            throw new IllegalStateException("The current thread must have a looper!");
        }
    };
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
    /* access modifiers changed from: private */
    public final FrameHandler mHandler;
    private boolean mIsVsyncScheduled;
    @UnsupportedAppUsage
    private long mLastFrameTimeNanos;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private final Object mLock;
    private final Looper mLooper;
    private int mMotionEventType;
    private int mTouchMoveNum;

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
        this.mFrameIntervalNanos = (long) (1.0E9f / getRefreshRate());
        this.mCallbackQueues = new CallbackQueue[5];
        for (int i = 0; i <= 4; i++) {
            this.mCallbackQueues[i] = new CallbackQueue();
        }
        setFPSDivisor(SystemProperties.getInt(ThreadedRenderer.DEBUG_FPS_DIVISOR, 1));
    }

    private static float getRefreshRate() {
        return DisplayManagerGlobal.getInstance().getDisplayInfo(0).getMode().getRefreshRate();
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
        sThreadInstance.remove();
        sThreadInstance.get().dispose();
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
            return 0;
        }
        return delayMillis - frameDelay;
    }

    public long getFrameIntervalNanos() {
        return this.mFrameIntervalNanos;
    }

    /* access modifiers changed from: package-private */
    public void dump(String prefix, PrintWriter writer) {
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
        postCallbackDelayed(callbackType, action, token, 0);
    }

    public void postCallbackDelayed(int callbackType, Runnable action, Object token, long delayMillis) {
        if (action == null) {
            throw new IllegalArgumentException("action must not be null");
        } else if (callbackType < 0 || callbackType > 4) {
            throw new IllegalArgumentException("callbackType is invalid");
        } else {
            postCallbackDelayedInternal(callbackType, action, token, delayMillis);
        }
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
        postFrameCallbackDelayed(callback, 0);
    }

    public void postFrameCallbackDelayed(FrameCallback callback, long delayMillis) {
        if (callback != null) {
            postCallbackDelayedInternal(1, callback, FRAME_CALLBACK_TOKEN, delayMillis);
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public void removeFrameCallback(FrameCallback callback) {
        if (callback != null) {
            removeCallbacksInternal(1, callback, FRAME_CALLBACK_TOKEN);
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    @UnsupportedAppUsage
    public long getFrameTime() {
        return getFrameTimeNanos() / TimeUtils.NANOS_PER_MS;
    }

    @UnsupportedAppUsage
    public long getFrameTimeNanos() {
        long nanoTime;
        synchronized (this.mLock) {
            if (this.mCallbacksRunning) {
                nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
            } else {
                throw new IllegalStateException("This method must only be called as part of a callback while a frame is in progress.");
            }
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
                Trace.traceBegin(8, "scheduleFrameLocked-mMotionEventType:" + this.mMotionEventType + " mTouchMoveNum:" + this.mTouchMoveNum + " mConsumedDown:" + this.mConsumedDown + " mConsumedMove:" + this.mConsumedMove);
                Trace.traceEnd(8);
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
                    }
                }
            }
            if (!USE_VSYNC) {
                long nextFrameTime = Math.max((this.mLastFrameTimeNanos / TimeUtils.NANOS_PER_MS) + sFrameDelay, now);
                Message msg3 = this.mHandler.obtainMessage(0);
                msg3.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(msg3, nextFrameTime);
            } else if (isRunningOnLooperThreadLocked()) {
                scheduleVsyncLocked();
            } else {
                Message msg4 = this.mHandler.obtainMessage(1);
                msg4.setAsynchronous(true);
                this.mHandler.sendMessageAtFrontOfQueue(msg4);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setFPSDivisor(int divisor) {
        if (divisor <= 0) {
            divisor = 1;
        }
        this.mFPSDivisor = divisor;
        ThreadedRenderer.setFPSDivisor(divisor);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void doFrame(long frameTimeNanos, int frame) {
        long frameTimeNanos2;
        synchronized (this.mLock) {
            try {
                this.mIsVsyncScheduled = false;
                if (this.mFrameScheduled) {
                    long intendedFrameTimeNanos = frameTimeNanos;
                    long startNanos = System.nanoTime();
                    long jitterNanos = startNanos - frameTimeNanos;
                    if (jitterNanos >= this.mFrameIntervalNanos) {
                        long skippedFrames = jitterNanos / this.mFrameIntervalNanos;
                        if (skippedFrames >= ((long) SKIPPED_FRAME_WARNING_LIMIT)) {
                            Log.i(TAG, "Skipped " + skippedFrames + " frames!  The application may be doing too much work on its main thread.");
                        }
                        frameTimeNanos2 = startNanos - (jitterNanos % this.mFrameIntervalNanos);
                    } else {
                        frameTimeNanos2 = frameTimeNanos;
                    }
                    try {
                        if (frameTimeNanos2 < this.mLastFrameTimeNanos) {
                            scheduleVsyncLocked();
                            return;
                        }
                        if (this.mFPSDivisor > 1) {
                            long timeSinceVsync = frameTimeNanos2 - this.mLastFrameTimeNanos;
                            if (timeSinceVsync < this.mFrameIntervalNanos * ((long) this.mFPSDivisor) && timeSinceVsync > 0) {
                                scheduleVsyncLocked();
                                return;
                            }
                        }
                        this.mFrameInfo.setVsync(intendedFrameTimeNanos, frameTimeNanos2);
                        this.mFrameScheduled = false;
                        this.mLastFrameTimeNanos = frameTimeNanos2;
                        long j = startNanos;
                        try {
                            Trace.traceBegin(8, "Choreographer#doFrame");
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
                            Trace.traceEnd(8);
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                long j2 = frameTimeNanos;
                throw th;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        android.os.Trace.traceBegin(8, CALLBACK_TRACE_TITLES[r2]);
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
        if (r0 == null) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        r0.run(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        r0 = r0.next;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        r5 = r1.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r1.mCallbacksRunning = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005d, code lost:
        r0 = r3.next;
        recycleCallbackLocked(r3);
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        if (r3 != null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0066, code lost:
        android.os.Trace.traceEnd(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0070, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0073, code lost:
        monitor-enter(r1.mLock);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r1.mCallbacksRunning = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0076, code lost:
        r4 = r3.next;
        recycleCallbackLocked(r3);
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007c, code lost:
        if (r3 != null) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0080, code lost:
        android.os.Trace.traceEnd(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0085, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doCallbacks(int r16, long r17) {
        /*
            r15 = this;
            r1 = r15
            r2 = r16
            java.lang.Object r3 = r1.mLock
            monitor-enter(r3)
            long r4 = java.lang.System.nanoTime()     // Catch:{ all -> 0x008c }
            android.view.Choreographer$CallbackQueue[] r0 = r1.mCallbackQueues     // Catch:{ all -> 0x008c }
            r0 = r0[r2]     // Catch:{ all -> 0x008c }
            r6 = 1000000(0xf4240, double:4.940656E-318)
            long r6 = r4 / r6
            android.view.Choreographer$CallbackRecord r0 = r0.extractDueCallbacksLocked(r6)     // Catch:{ all -> 0x008c }
            if (r0 != 0) goto L_0x001b
            monitor-exit(r3)     // Catch:{ all -> 0x008c }
            return
        L_0x001b:
            r6 = 1
            r1.mCallbacksRunning = r6     // Catch:{ all -> 0x008c }
            r6 = 4
            r7 = 8
            if (r2 != r6) goto L_0x0040
            long r11 = r4 - r17
            java.lang.String r6 = "jitterNanos"
            int r13 = (int) r11     // Catch:{ all -> 0x008c }
            android.os.Trace.traceCounter(r7, r6, r13)     // Catch:{ all -> 0x008c }
            r13 = 2
            long r7 = r1.mFrameIntervalNanos     // Catch:{ all -> 0x008c }
            long r7 = r7 * r13
            int r6 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r6 < 0) goto L_0x0040
            long r6 = r1.mFrameIntervalNanos     // Catch:{ all -> 0x008c }
            long r6 = r11 % r6
            long r13 = r1.mFrameIntervalNanos     // Catch:{ all -> 0x008c }
            long r6 = r6 + r13
            long r8 = r4 - r6
            r1.mLastFrameTimeNanos = r8     // Catch:{ all -> 0x0089 }
            goto L_0x0042
        L_0x0040:
            r8 = r17
        L_0x0042:
            monitor-exit(r3)     // Catch:{ all -> 0x0089 }
            r3 = r0
            r4 = 0
            java.lang.String[] r0 = CALLBACK_TRACE_TITLES     // Catch:{ all -> 0x0070 }
            r0 = r0[r2]     // Catch:{ all -> 0x0070 }
            r5 = 8
            android.os.Trace.traceBegin(r5, r0)     // Catch:{ all -> 0x0070 }
            r0 = r3
        L_0x004f:
            if (r0 == 0) goto L_0x0058
            r0.run(r8)     // Catch:{ all -> 0x0070 }
            android.view.Choreographer$CallbackRecord r5 = r0.next     // Catch:{ all -> 0x0070 }
            r0 = r5
            goto L_0x004f
        L_0x0058:
            java.lang.Object r5 = r1.mLock
            monitor-enter(r5)
            r1.mCallbacksRunning = r4     // Catch:{ all -> 0x006d }
        L_0x005d:
            android.view.Choreographer$CallbackRecord r0 = r3.next     // Catch:{ all -> 0x006d }
            r15.recycleCallbackLocked(r3)     // Catch:{ all -> 0x006d }
            r3 = r0
            if (r3 != 0) goto L_0x005d
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            r4 = 8
            android.os.Trace.traceEnd(r4)
            return
        L_0x006d:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            throw r0
        L_0x0070:
            r0 = move-exception
            java.lang.Object r5 = r1.mLock
            monitor-enter(r5)
            r1.mCallbacksRunning = r4     // Catch:{ all -> 0x0086 }
        L_0x0076:
            android.view.Choreographer$CallbackRecord r4 = r3.next     // Catch:{ all -> 0x0086 }
            r15.recycleCallbackLocked(r3)     // Catch:{ all -> 0x0086 }
            r3 = r4
            if (r3 == 0) goto L_0x007f
            goto L_0x0076
        L_0x007f:
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            r4 = 8
            android.os.Trace.traceEnd(r4)
            throw r0
        L_0x0086:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            throw r0
        L_0x0089:
            r0 = move-exception
            r9 = r8
            goto L_0x008f
        L_0x008c:
            r0 = move-exception
            r9 = r17
        L_0x008f:
            monitor-exit(r3)     // Catch:{ all -> 0x0091 }
            throw r0
        L_0x0091:
            r0 = move-exception
            goto L_0x008f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.Choreographer.doCallbacks(int, long):void");
    }

    /* access modifiers changed from: package-private */
    public void doScheduleVsync() {
        synchronized (this.mLock) {
            if (this.mFrameScheduled) {
                scheduleVsyncLocked();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void doScheduleCallback(int callbackType) {
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

    /* access modifiers changed from: private */
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

    /* access modifiers changed from: private */
    public void recycleCallbackLocked(CallbackRecord callback) {
        callback.action = null;
        callback.token = null;
        callback.next = this.mCallbackPool;
        this.mCallbackPool = callback;
    }

    private final class FrameHandler extends Handler {
        public FrameHandler(Looper looper) {
            super(looper);
        }

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

    private final class FrameDisplayEventReceiver extends DisplayEventReceiver implements Runnable {
        private int mFrame;
        private boolean mHavePendingVsync;
        private long mTimestampNanos;

        public FrameDisplayEventReceiver(Looper looper, int vsyncSource) {
            super(looper, vsyncSource);
        }

        public void onVsync(long timestampNanos, long physicalDisplayId, int frame) {
            long now = System.nanoTime();
            if (timestampNanos > now) {
                Log.w(Choreographer.TAG, "Frame time is " + (((float) (timestampNanos - now)) * 1.0E-6f) + " ms in the future!  Check that graphics HAL is generating vsync timestamps using the correct timebase.");
                timestampNanos = now;
            }
            if (this.mHavePendingVsync) {
                Log.w(Choreographer.TAG, "Already have a pending vsync event.  There should only be one at a time.");
            } else {
                this.mHavePendingVsync = true;
            }
            this.mTimestampNanos = timestampNanos;
            this.mFrame = frame;
            Message msg = Message.obtain((Handler) Choreographer.this.mHandler, (Runnable) this);
            msg.setAsynchronous(true);
            Choreographer.this.mHandler.sendMessageAtTime(msg, timestampNanos / TimeUtils.NANOS_PER_MS);
        }

        public void run() {
            this.mHavePendingVsync = false;
            Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame);
        }
    }

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
