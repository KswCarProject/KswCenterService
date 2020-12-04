package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.StatsLog;

public class GestureDetector {
    private static final int DOUBLE_TAP_MIN_TIME = ViewConfiguration.getDoubleTapMinTime();
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private boolean mAlwaysInBiggerTapRegion;
    @UnsupportedAppUsage
    private boolean mAlwaysInTapRegion;
    private OnContextClickListener mContextClickListener;
    /* access modifiers changed from: private */
    public MotionEvent mCurrentDownEvent;
    private MotionEvent mCurrentMotionEvent;
    /* access modifiers changed from: private */
    public boolean mDeferConfirmSingleTap;
    /* access modifiers changed from: private */
    public OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private int mDoubleTapTouchSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mHasRecordedClassification;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextClick;
    private boolean mInLongPress;
    private final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    @UnsupportedAppUsage
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    /* access modifiers changed from: private */
    public boolean mStillDown;
    @UnsupportedAppUsage
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;

    public interface OnContextClickListener {
        boolean onContextClick(MotionEvent motionEvent);
    }

    public interface OnDoubleTapListener {
        boolean onDoubleTap(MotionEvent motionEvent);

        boolean onDoubleTapEvent(MotionEvent motionEvent);

        boolean onSingleTapConfirmed(MotionEvent motionEvent);
    }

    public interface OnGestureListener {
        boolean onDown(MotionEvent motionEvent);

        boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onLongPress(MotionEvent motionEvent);

        boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onShowPress(MotionEvent motionEvent);

        boolean onSingleTapUp(MotionEvent motionEvent);
    }

    public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener {
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onDown(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        public boolean onContextClick(MotionEvent e) {
            return false;
        }
    }

    private class GestureHandler extends Handler {
        GestureHandler() {
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    GestureDetector.this.mListener.onShowPress(GestureDetector.this.mCurrentDownEvent);
                    return;
                case 2:
                    GestureDetector.this.recordGestureClassification(msg.arg1);
                    GestureDetector.this.dispatchLongPress();
                    return;
                case 3:
                    if (GestureDetector.this.mDoubleTapListener == null) {
                        return;
                    }
                    if (!GestureDetector.this.mStillDown) {
                        GestureDetector.this.recordGestureClassification(1);
                        GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetector.this.mCurrentDownEvent);
                        return;
                    }
                    boolean unused = GestureDetector.this.mDeferConfirmSingleTap = true;
                    return;
                default:
                    throw new RuntimeException("Unknown message " + msg);
            }
        }
    }

    @Deprecated
    public GestureDetector(OnGestureListener listener, Handler handler) {
        this((Context) null, listener, handler);
    }

    @Deprecated
    public GestureDetector(OnGestureListener listener) {
        this((Context) null, listener, (Handler) null);
    }

    public GestureDetector(Context context, OnGestureListener listener) {
        this(context, listener, (Handler) null);
    }

    public GestureDetector(Context context, OnGestureListener listener, Handler handler) {
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        if (handler != null) {
            this.mHandler = new GestureHandler(handler);
        } else {
            this.mHandler = new GestureHandler();
        }
        this.mListener = listener;
        if (listener instanceof OnDoubleTapListener) {
            setOnDoubleTapListener((OnDoubleTapListener) listener);
        }
        if (listener instanceof OnContextClickListener) {
            setContextClickListener((OnContextClickListener) listener);
        }
        init(context);
    }

    public GestureDetector(Context context, OnGestureListener listener, Handler handler, boolean unused) {
        this(context, listener, handler);
    }

    private void init(Context context) {
        int doubleTapTouchSlop;
        int touchSlop;
        int touchSlop2;
        if (this.mListener != null) {
            this.mIsLongpressEnabled = true;
            if (context == null) {
                touchSlop2 = ViewConfiguration.getTouchSlop();
                touchSlop = touchSlop2;
                doubleTapTouchSlop = ViewConfiguration.getDoubleTapSlop();
                this.mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
                this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            } else {
                ViewConfiguration configuration = ViewConfiguration.get(context);
                int touchSlop3 = configuration.getScaledTouchSlop();
                int doubleTapTouchSlop2 = configuration.getScaledDoubleTapTouchSlop();
                int doubleTapSlop = configuration.getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
                touchSlop2 = touchSlop3;
                touchSlop = doubleTapTouchSlop2;
                doubleTapTouchSlop = doubleTapSlop;
            }
            this.mTouchSlopSquare = touchSlop2 * touchSlop2;
            this.mDoubleTapTouchSlopSquare = touchSlop * touchSlop;
            this.mDoubleTapSlopSquare = doubleTapTouchSlop * doubleTapTouchSlop;
            return;
        }
        throw new NullPointerException("OnGestureListener must not be null");
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.mDoubleTapListener = onDoubleTapListener;
    }

    public void setContextClickListener(OnContextClickListener onContextClickListener) {
        this.mContextClickListener = onContextClickListener;
    }

    public void setIsLongpressEnabled(boolean isLongpressEnabled) {
        this.mIsLongpressEnabled = isLongpressEnabled;
    }

    public boolean isLongpressEnabled() {
        return this.mIsLongpressEnabled;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0410, code lost:
        r14 = r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0412, code lost:
        if (r14 != false) goto L_0x041e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0416, code lost:
        if (r0.mInputEventConsistencyVerifier == null) goto L_0x041e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0418, code lost:
        r0.mInputEventConsistencyVerifier.onUnhandledEvent(r1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x041e, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0124, code lost:
        r26 = r8;
        r27 = r9;
        r29 = r10;
        r30 = r11;
        r32 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x032e  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0339  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x03bd  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x03da  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r38) {
        /*
            r37 = this;
            r0 = r37
            r1 = r38
            android.view.InputEventConsistencyVerifier r2 = r0.mInputEventConsistencyVerifier
            r3 = 0
            if (r2 == 0) goto L_0x000e
            android.view.InputEventConsistencyVerifier r2 = r0.mInputEventConsistencyVerifier
            r2.onTouchEvent(r1, r3)
        L_0x000e:
            int r2 = r38.getAction()
            android.view.MotionEvent r4 = r0.mCurrentMotionEvent
            if (r4 == 0) goto L_0x001b
            android.view.MotionEvent r4 = r0.mCurrentMotionEvent
            r4.recycle()
        L_0x001b:
            android.view.MotionEvent r4 = android.view.MotionEvent.obtain(r38)
            r0.mCurrentMotionEvent = r4
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            if (r4 != 0) goto L_0x002b
            android.view.VelocityTracker r4 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r4
        L_0x002b:
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            r4.addMovement(r1)
            r4 = r2 & 255(0xff, float:3.57E-43)
            r5 = 6
            if (r4 != r5) goto L_0x0037
            r4 = 1
            goto L_0x0038
        L_0x0037:
            r4 = r3
        L_0x0038:
            if (r4 == 0) goto L_0x003f
            int r5 = r38.getActionIndex()
            goto L_0x0040
        L_0x003f:
            r5 = -1
        L_0x0040:
            int r7 = r38.getFlags()
            r7 = r7 & 8
            if (r7 == 0) goto L_0x004b
            r7 = 1
            goto L_0x004c
        L_0x004b:
            r7 = r3
        L_0x004c:
            r8 = 0
            r9 = 0
            int r10 = r38.getPointerCount()
            r11 = r9
            r9 = r8
            r8 = r3
        L_0x0055:
            if (r8 >= r10) goto L_0x0067
            if (r5 != r8) goto L_0x005a
            goto L_0x0064
        L_0x005a:
            float r12 = r1.getX(r8)
            float r9 = r9 + r12
            float r12 = r1.getY(r8)
            float r11 = r11 + r12
        L_0x0064:
            int r8 = r8 + 1
            goto L_0x0055
        L_0x0067:
            if (r4 == 0) goto L_0x006c
            int r8 = r10 + -1
            goto L_0x006d
        L_0x006c:
            r8 = r10
        L_0x006d:
            float r12 = (float) r8
            float r12 = r9 / r12
            float r13 = (float) r8
            float r13 = r11 / r13
            r14 = 0
            r15 = r2 & 255(0xff, float:3.57E-43)
            r3 = 1000(0x3e8, float:1.401E-42)
            r6 = 2
            switch(r15) {
                case 0: goto L_0x0356;
                case 1: goto L_0x0299;
                case 2: goto L_0x0130;
                case 3: goto L_0x011b;
                case 4: goto L_0x007c;
                case 5: goto L_0x0109;
                case 6: goto L_0x008e;
                default: goto L_0x007c;
            }
        L_0x007c:
            r18 = r2
            r20 = r4
            r22 = r5
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            goto L_0x0410
        L_0x008e:
            r0.mLastFocusX = r12
            r0.mDownFocusX = r12
            r0.mLastFocusY = r13
            r0.mDownFocusY = r13
            android.view.VelocityTracker r6 = r0.mVelocityTracker
            int r15 = r0.mMaximumFlingVelocity
            float r15 = (float) r15
            r6.computeCurrentVelocity(r3, r15)
            int r3 = r38.getActionIndex()
            int r6 = r1.getPointerId(r3)
            android.view.VelocityTracker r15 = r0.mVelocityTracker
            float r15 = r15.getXVelocity(r6)
            r18 = r2
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            float r2 = r2.getYVelocity(r6)
            r16 = 0
        L_0x00b6:
            r19 = r16
            r20 = r4
            r4 = r19
            if (r4 >= r10) goto L_0x0100
            if (r4 != r3) goto L_0x00c9
            r24 = r2
            r21 = r3
            r22 = r5
            r23 = r6
            goto L_0x00f3
        L_0x00c9:
            r21 = r3
            int r3 = r1.getPointerId(r4)
            r22 = r5
            android.view.VelocityTracker r5 = r0.mVelocityTracker
            float r5 = r5.getXVelocity(r3)
            float r5 = r5 * r15
            r23 = r6
            android.view.VelocityTracker r6 = r0.mVelocityTracker
            float r6 = r6.getYVelocity(r3)
            float r6 = r6 * r2
            float r16 = r5 + r6
            r17 = 0
            int r17 = (r16 > r17 ? 1 : (r16 == r17 ? 0 : -1))
            if (r17 >= 0) goto L_0x00f1
            r24 = r2
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            r2.clear()
            goto L_0x0108
        L_0x00f1:
            r24 = r2
        L_0x00f3:
            int r16 = r4 + 1
            r4 = r20
            r3 = r21
            r5 = r22
            r6 = r23
            r2 = r24
            goto L_0x00b6
        L_0x0100:
            r24 = r2
            r21 = r3
            r22 = r5
            r23 = r6
        L_0x0108:
            goto L_0x0124
        L_0x0109:
            r18 = r2
            r20 = r4
            r22 = r5
            r0.mLastFocusX = r12
            r0.mDownFocusX = r12
            r0.mLastFocusY = r13
            r0.mDownFocusY = r13
            r37.cancelTaps()
            goto L_0x0124
        L_0x011b:
            r18 = r2
            r20 = r4
            r22 = r5
            r37.cancel()
        L_0x0124:
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            goto L_0x0410
        L_0x0130:
            r18 = r2
            r20 = r4
            r22 = r5
            boolean r2 = r0.mInLongPress
            if (r2 != 0) goto L_0x028d
            boolean r2 = r0.mInContextClick
            if (r2 == 0) goto L_0x013f
            goto L_0x0124
        L_0x013f:
            int r2 = r38.getClassification()
            android.os.Handler r3 = r0.mHandler
            boolean r3 = r3.hasMessages(r6)
            float r4 = r0.mLastFocusX
            float r4 = r4 - r12
            float r5 = r0.mLastFocusY
            float r5 = r5 - r13
            boolean r15 = r0.mIsDoubleTapping
            if (r15 == 0) goto L_0x016b
            r0.recordGestureClassification(r6)
            android.view.GestureDetector$OnDoubleTapListener r15 = r0.mDoubleTapListener
            boolean r15 = r15.onDoubleTapEvent(r1)
            r14 = r14 | r15
            r34 = r2
            r33 = r3
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            goto L_0x0265
        L_0x016b:
            boolean r15 = r0.mAlwaysInTapRegion
            if (r15 == 0) goto L_0x0230
            float r15 = r0.mDownFocusX
            float r15 = r12 - r15
            int r15 = (int) r15
            float r6 = r0.mDownFocusY
            float r6 = r13 - r6
            int r6 = (int) r6
            int r19 = r15 * r15
            int r21 = r6 * r6
            r25 = r6
            int r6 = r19 + r21
            if (r7 == 0) goto L_0x0187
            r26 = r8
            r8 = 0
            goto L_0x018b
        L_0x0187:
            r26 = r8
            int r8 = r0.mTouchSlopSquare
        L_0x018b:
            r27 = r9
            r9 = 1
            if (r2 != r9) goto L_0x0192
            r9 = 1
            goto L_0x0193
        L_0x0192:
            r9 = 0
        L_0x0193:
            if (r3 == 0) goto L_0x019a
            if (r9 == 0) goto L_0x019a
            r19 = 1
            goto L_0x019c
        L_0x019a:
            r19 = 0
        L_0x019c:
            if (r19 == 0) goto L_0x01eb
            float r21 = android.view.ViewConfiguration.getAmbiguousGestureMultiplier()
            if (r6 <= r8) goto L_0x01d7
            r28 = r9
            android.os.Handler r9 = r0.mHandler
            r29 = r10
            r10 = 2
            r9.removeMessages(r10)
            int r9 = android.view.ViewConfiguration.getLongPressTimeout()
            r30 = r11
            long r10 = (long) r9
            android.os.Handler r9 = r0.mHandler
            r31 = r15
            android.os.Handler r15 = r0.mHandler
            r34 = r2
            r33 = r3
            r32 = r14
            r2 = 3
            r3 = 2
            r14 = 0
            android.os.Message r15 = r15.obtainMessage(r3, r2, r14)
            long r2 = r38.getDownTime()
            float r14 = (float) r10
            float r14 = r14 * r21
            r35 = r10
            long r10 = (long) r14
            long r2 = r2 + r10
            r9.sendMessageAtTime(r15, r2)
            goto L_0x01e5
        L_0x01d7:
            r34 = r2
            r33 = r3
            r28 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            r31 = r15
        L_0x01e5:
            float r2 = (float) r8
            float r3 = r21 * r21
            float r2 = r2 * r3
            int r8 = (int) r2
            goto L_0x01f9
        L_0x01eb:
            r34 = r2
            r33 = r3
            r28 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            r31 = r15
        L_0x01f9:
            if (r6 <= r8) goto L_0x0221
            r2 = 5
            r0.recordGestureClassification(r2)
            android.view.GestureDetector$OnGestureListener r2 = r0.mListener
            android.view.MotionEvent r3 = r0.mCurrentDownEvent
            boolean r14 = r2.onScroll(r3, r1, r4, r5)
            r0.mLastFocusX = r12
            r0.mLastFocusY = r13
            r2 = 0
            r0.mAlwaysInTapRegion = r2
            android.os.Handler r2 = r0.mHandler
            r3 = 3
            r2.removeMessages(r3)
            android.os.Handler r2 = r0.mHandler
            r3 = 1
            r2.removeMessages(r3)
            android.os.Handler r2 = r0.mHandler
            r3 = 2
            r2.removeMessages(r3)
            goto L_0x0223
        L_0x0221:
            r14 = r32
        L_0x0223:
            if (r7 == 0) goto L_0x0227
            r3 = 0
            goto L_0x0229
        L_0x0227:
            int r3 = r0.mDoubleTapTouchSlopSquare
        L_0x0229:
            r2 = r3
            if (r6 <= r2) goto L_0x022f
            r3 = 0
            r0.mAlwaysInBiggerTapRegion = r3
        L_0x022f:
            goto L_0x0265
        L_0x0230:
            r34 = r2
            r33 = r3
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            float r2 = java.lang.Math.abs(r4)
            r3 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x0254
            float r2 = java.lang.Math.abs(r5)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x0251
            goto L_0x0254
        L_0x0251:
            r14 = r32
            goto L_0x0265
        L_0x0254:
            r2 = 5
            r0.recordGestureClassification(r2)
            android.view.GestureDetector$OnGestureListener r2 = r0.mListener
            android.view.MotionEvent r3 = r0.mCurrentDownEvent
            boolean r2 = r2.onScroll(r3, r1, r4, r5)
            r0.mLastFocusX = r12
            r0.mLastFocusY = r13
            r14 = r2
        L_0x0265:
            r2 = r34
            r3 = 2
            if (r2 != r3) goto L_0x026d
            r16 = 1
            goto L_0x026f
        L_0x026d:
            r16 = 0
        L_0x026f:
            r3 = r16
            if (r3 == 0) goto L_0x028a
            if (r33 == 0) goto L_0x028a
            android.os.Handler r6 = r0.mHandler
            r8 = 2
            r6.removeMessages(r8)
            android.os.Handler r6 = r0.mHandler
            android.os.Handler r9 = r0.mHandler
            r10 = 4
            r11 = 0
            android.os.Message r8 = r9.obtainMessage(r8, r10, r11)
            r6.sendMessage(r8)
            goto L_0x0412
        L_0x028a:
            r11 = 0
            goto L_0x0412
        L_0x028d:
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            goto L_0x0410
        L_0x0299:
            r18 = r2
            r20 = r4
            r22 = r5
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            r11 = 0
            r0.mStillDown = r11
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r38)
            boolean r4 = r0.mIsDoubleTapping
            if (r4 == 0) goto L_0x02c2
            r3 = 2
            r0.recordGestureClassification(r3)
            android.view.GestureDetector$OnDoubleTapListener r3 = r0.mDoubleTapListener
            boolean r3 = r3.onDoubleTapEvent(r1)
            r3 = r32 | r3
        L_0x02c0:
            r14 = r3
            goto L_0x032a
        L_0x02c2:
            boolean r4 = r0.mInLongPress
            if (r4 == 0) goto L_0x02d0
            android.os.Handler r3 = r0.mHandler
            r4 = 3
            r3.removeMessages(r4)
            r3 = 0
            r0.mInLongPress = r3
            goto L_0x0328
        L_0x02d0:
            boolean r4 = r0.mAlwaysInTapRegion
            if (r4 == 0) goto L_0x02f0
            boolean r4 = r0.mIgnoreNextUpEvent
            if (r4 != 0) goto L_0x02f0
            r3 = 1
            r0.recordGestureClassification(r3)
            android.view.GestureDetector$OnGestureListener r3 = r0.mListener
            boolean r3 = r3.onSingleTapUp(r1)
            boolean r4 = r0.mDeferConfirmSingleTap
            if (r4 == 0) goto L_0x02c0
            android.view.GestureDetector$OnDoubleTapListener r4 = r0.mDoubleTapListener
            if (r4 == 0) goto L_0x02c0
            android.view.GestureDetector$OnDoubleTapListener r4 = r0.mDoubleTapListener
            r4.onSingleTapConfirmed(r1)
            goto L_0x02c0
        L_0x02f0:
            boolean r4 = r0.mIgnoreNextUpEvent
            if (r4 != 0) goto L_0x0328
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            r5 = 0
            int r6 = r1.getPointerId(r5)
            int r5 = r0.mMaximumFlingVelocity
            float r5 = (float) r5
            r4.computeCurrentVelocity(r3, r5)
            float r3 = r4.getYVelocity(r6)
            float r5 = r4.getXVelocity(r6)
            float r8 = java.lang.Math.abs(r3)
            int r9 = r0.mMinimumFlingVelocity
            float r9 = (float) r9
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 > 0) goto L_0x031f
            float r8 = java.lang.Math.abs(r5)
            int r9 = r0.mMinimumFlingVelocity
            float r9 = (float) r9
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 <= 0) goto L_0x0328
        L_0x031f:
            android.view.GestureDetector$OnGestureListener r8 = r0.mListener
            android.view.MotionEvent r9 = r0.mCurrentDownEvent
            boolean r3 = r8.onFling(r9, r1, r5, r3)
            goto L_0x02c0
        L_0x0328:
            r14 = r32
        L_0x032a:
            android.view.MotionEvent r3 = r0.mPreviousUpEvent
            if (r3 == 0) goto L_0x0333
            android.view.MotionEvent r3 = r0.mPreviousUpEvent
            r3.recycle()
        L_0x0333:
            r0.mPreviousUpEvent = r2
            android.view.VelocityTracker r3 = r0.mVelocityTracker
            if (r3 == 0) goto L_0x0341
            android.view.VelocityTracker r3 = r0.mVelocityTracker
            r3.recycle()
            r3 = 0
            r0.mVelocityTracker = r3
        L_0x0341:
            r3 = 0
            r0.mIsDoubleTapping = r3
            r0.mDeferConfirmSingleTap = r3
            r0.mIgnoreNextUpEvent = r3
            android.os.Handler r3 = r0.mHandler
            r4 = 1
            r3.removeMessages(r4)
            android.os.Handler r3 = r0.mHandler
            r4 = 2
            r3.removeMessages(r4)
            goto L_0x0412
        L_0x0356:
            r18 = r2
            r20 = r4
            r22 = r5
            r26 = r8
            r27 = r9
            r29 = r10
            r30 = r11
            r32 = r14
            android.view.GestureDetector$OnDoubleTapListener r2 = r0.mDoubleTapListener
            if (r2 == 0) goto L_0x03af
            android.os.Handler r2 = r0.mHandler
            r3 = 3
            boolean r2 = r2.hasMessages(r3)
            if (r2 == 0) goto L_0x0378
            android.os.Handler r4 = r0.mHandler
            r4.removeMessages(r3)
        L_0x0378:
            android.view.MotionEvent r3 = r0.mCurrentDownEvent
            if (r3 == 0) goto L_0x03a6
            android.view.MotionEvent r3 = r0.mPreviousUpEvent
            if (r3 == 0) goto L_0x03a6
            if (r2 == 0) goto L_0x03a6
            android.view.MotionEvent r3 = r0.mCurrentDownEvent
            android.view.MotionEvent r4 = r0.mPreviousUpEvent
            boolean r3 = r0.isConsideredDoubleTap(r3, r4, r1)
            if (r3 == 0) goto L_0x03a6
            r3 = 1
            r0.mIsDoubleTapping = r3
            r3 = 2
            r0.recordGestureClassification(r3)
            android.view.GestureDetector$OnDoubleTapListener r3 = r0.mDoubleTapListener
            android.view.MotionEvent r4 = r0.mCurrentDownEvent
            boolean r3 = r3.onDoubleTap(r4)
            r3 = r32 | r3
            android.view.GestureDetector$OnDoubleTapListener r4 = r0.mDoubleTapListener
            boolean r4 = r4.onDoubleTapEvent(r1)
            r14 = r3 | r4
            goto L_0x03b1
        L_0x03a6:
            android.os.Handler r3 = r0.mHandler
            int r4 = DOUBLE_TAP_TIMEOUT
            long r4 = (long) r4
            r6 = 3
            r3.sendEmptyMessageDelayed(r6, r4)
        L_0x03af:
            r14 = r32
        L_0x03b1:
            r0.mLastFocusX = r12
            r0.mDownFocusX = r12
            r0.mLastFocusY = r13
            r0.mDownFocusY = r13
            android.view.MotionEvent r2 = r0.mCurrentDownEvent
            if (r2 == 0) goto L_0x03c2
            android.view.MotionEvent r2 = r0.mCurrentDownEvent
            r2.recycle()
        L_0x03c2:
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r38)
            r0.mCurrentDownEvent = r2
            r2 = 1
            r0.mAlwaysInTapRegion = r2
            r0.mAlwaysInBiggerTapRegion = r2
            r0.mStillDown = r2
            r2 = 0
            r0.mInLongPress = r2
            r0.mDeferConfirmSingleTap = r2
            r0.mHasRecordedClassification = r2
            boolean r3 = r0.mIsLongpressEnabled
            if (r3 == 0) goto L_0x03f8
            android.os.Handler r3 = r0.mHandler
            r4 = 2
            r3.removeMessages(r4)
            android.os.Handler r3 = r0.mHandler
            android.os.Handler r5 = r0.mHandler
            r6 = 3
            android.os.Message r4 = r5.obtainMessage(r4, r6, r2)
            android.view.MotionEvent r2 = r0.mCurrentDownEvent
            long r5 = r2.getDownTime()
            int r2 = android.view.ViewConfiguration.getLongPressTimeout()
            long r8 = (long) r2
            long r5 = r5 + r8
            r3.sendMessageAtTime(r4, r5)
        L_0x03f8:
            android.os.Handler r2 = r0.mHandler
            android.view.MotionEvent r3 = r0.mCurrentDownEvent
            long r3 = r3.getDownTime()
            int r5 = TAP_TIMEOUT
            long r5 = (long) r5
            long r3 = r3 + r5
            r5 = 1
            r2.sendEmptyMessageAtTime(r5, r3)
            android.view.GestureDetector$OnGestureListener r2 = r0.mListener
            boolean r2 = r2.onDown(r1)
            r14 = r14 | r2
            goto L_0x0412
        L_0x0410:
            r14 = r32
        L_0x0412:
            if (r14 != 0) goto L_0x041e
            android.view.InputEventConsistencyVerifier r2 = r0.mInputEventConsistencyVerifier
            if (r2 == 0) goto L_0x041e
            android.view.InputEventConsistencyVerifier r2 = r0.mInputEventConsistencyVerifier
            r3 = 0
            r2.onUnhandledEvent(r1, r3)
        L_0x041e:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.GestureDetector.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onGenericMotionEvent(MotionEvent ev) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(ev, 0);
        }
        int actionButton = ev.getActionButton();
        switch (ev.getActionMasked()) {
            case 11:
                if (this.mContextClickListener != null && !this.mInContextClick && !this.mInLongPress && ((actionButton == 32 || actionButton == 2) && this.mContextClickListener.onContextClick(ev))) {
                    this.mInContextClick = true;
                    this.mHandler.removeMessages(2);
                    this.mHandler.removeMessages(3);
                    return true;
                }
            case 12:
                if (this.mInContextClick && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextClick = false;
                    this.mIgnoreNextUpEvent = true;
                    break;
                }
        }
        return false;
    }

    private void cancel() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        this.mIsDoubleTapping = false;
        this.mStillDown = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private void cancelTaps() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mIsDoubleTapping = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp, MotionEvent secondDown) {
        if (!this.mAlwaysInBiggerTapRegion) {
            return false;
        }
        long deltaTime = secondDown.getEventTime() - firstUp.getEventTime();
        if (deltaTime > ((long) DOUBLE_TAP_TIMEOUT) || deltaTime < ((long) DOUBLE_TAP_MIN_TIME)) {
            return false;
        }
        int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
        int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
        if ((deltaX * deltaX) + (deltaY * deltaY) < ((firstDown.getFlags() & 8) != 0 ? 0 : this.mDoubleTapSlopSquare)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void dispatchLongPress() {
        this.mHandler.removeMessages(3);
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = true;
        this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    /* access modifiers changed from: private */
    public void recordGestureClassification(int classification) {
        if (!this.mHasRecordedClassification && classification != 0) {
            if (this.mCurrentDownEvent == null || this.mCurrentMotionEvent == null) {
                this.mHasRecordedClassification = true;
                return;
            }
            StatsLog.write(177, getClass().getName(), classification, (int) (SystemClock.uptimeMillis() - this.mCurrentMotionEvent.getDownTime()), (float) Math.hypot((double) (this.mCurrentMotionEvent.getRawX() - this.mCurrentDownEvent.getRawX()), (double) (this.mCurrentMotionEvent.getRawY() - this.mCurrentDownEvent.getRawY())));
            this.mHasRecordedClassification = true;
        }
    }
}
