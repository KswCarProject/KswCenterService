package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.p007os.Handler;
import android.p007os.Message;
import android.p007os.SystemClock;
import android.util.StatsLog;

/* loaded from: classes4.dex */
public class GestureDetector {
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private boolean mAlwaysInBiggerTapRegion;
    @UnsupportedAppUsage
    private boolean mAlwaysInTapRegion;
    private OnContextClickListener mContextClickListener;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mCurrentMotionEvent;
    private boolean mDeferConfirmSingleTap;
    private OnDoubleTapListener mDoubleTapListener;
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
    @UnsupportedAppUsage
    private final OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    @UnsupportedAppUsage
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    @UnsupportedAppUsage
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int DOUBLE_TAP_MIN_TIME = ViewConfiguration.getDoubleTapMinTime();

    /* loaded from: classes4.dex */
    public interface OnContextClickListener {
        boolean onContextClick(MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnDoubleTapListener {
        boolean onDoubleTap(MotionEvent motionEvent);

        boolean onDoubleTapEvent(MotionEvent motionEvent);

        boolean onSingleTapConfirmed(MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnGestureListener {
        boolean onDown(MotionEvent motionEvent);

        boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onLongPress(MotionEvent motionEvent);

        boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onShowPress(MotionEvent motionEvent);

        boolean onSingleTapUp(MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener {
        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent e) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override // android.view.GestureDetector.OnContextClickListener
        public boolean onContextClick(MotionEvent e) {
            return false;
        }
    }

    /* loaded from: classes4.dex */
    private class GestureHandler extends Handler {
        GestureHandler() {
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        @Override // android.p007os.Handler
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
                    if (GestureDetector.this.mDoubleTapListener != null) {
                        if (!GestureDetector.this.mStillDown) {
                            GestureDetector.this.recordGestureClassification(1);
                            GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetector.this.mCurrentDownEvent);
                            return;
                        }
                        GestureDetector.this.mDeferConfirmSingleTap = true;
                        return;
                    }
                    return;
                default:
                    throw new RuntimeException("Unknown message " + msg);
            }
        }
    }

    @Deprecated
    public GestureDetector(OnGestureListener listener, Handler handler) {
        this(null, listener, handler);
    }

    @Deprecated
    public GestureDetector(OnGestureListener listener) {
        this(null, listener, null);
    }

    public GestureDetector(Context context, OnGestureListener listener) {
        this(context, listener, null);
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
        int touchSlop;
        int touchSlop2;
        int doubleTapTouchSlop;
        if (this.mListener == null) {
            throw new NullPointerException("OnGestureListener must not be null");
        }
        this.mIsLongpressEnabled = true;
        if (context == null) {
            touchSlop = ViewConfiguration.getTouchSlop();
            touchSlop2 = touchSlop;
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
            touchSlop = touchSlop3;
            touchSlop2 = doubleTapTouchSlop2;
            doubleTapTouchSlop = doubleTapSlop;
        }
        int doubleTapSlop2 = touchSlop * touchSlop;
        this.mTouchSlopSquare = doubleTapSlop2;
        this.mDoubleTapTouchSlopSquare = touchSlop2 * touchSlop2;
        this.mDoubleTapSlopSquare = doubleTapTouchSlop * doubleTapTouchSlop;
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

    /* JADX WARN: Removed duplicated region for block: B:131:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handled;
        boolean handled2;
        boolean onFling;
        boolean handled3;
        int motionClassification;
        boolean hasPendingLongPress;
        boolean handled4;
        int upIndex;
        int skipIndex;
        int id1;
        float y1;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(ev, 0);
        }
        int action = ev.getAction();
        if (this.mCurrentMotionEvent != null) {
            this.mCurrentMotionEvent.recycle();
        }
        this.mCurrentMotionEvent = MotionEvent.obtain(ev);
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        boolean pointerUp = (action & 255) == 6;
        int skipIndex2 = pointerUp ? ev.getActionIndex() : -1;
        boolean isGeneratedGesture = (ev.getFlags() & 8) != 0;
        int count = ev.getPointerCount();
        float sumY = 0.0f;
        float sumX = 0.0f;
        for (int i = 0; i < count; i++) {
            if (skipIndex2 != i) {
                sumX += ev.getX(i);
                sumY += ev.getY(i);
            }
        }
        int div = pointerUp ? count - 1 : count;
        float focusX = sumX / div;
        float focusY = sumY / div;
        switch (action & 255) {
            case 0:
                if (this.mDoubleTapListener != null) {
                    boolean hadTapMessage = this.mHandler.hasMessages(3);
                    if (hadTapMessage) {
                        this.mHandler.removeMessages(3);
                    }
                    if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && hadTapMessage && isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, ev)) {
                        this.mIsDoubleTapping = true;
                        recordGestureClassification(2);
                        boolean handled5 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent);
                        handled = handled5 | this.mDoubleTapListener.onDoubleTapEvent(ev);
                        this.mLastFocusX = focusX;
                        this.mDownFocusX = focusX;
                        this.mLastFocusY = focusY;
                        this.mDownFocusY = focusY;
                        if (this.mCurrentDownEvent != null) {
                            this.mCurrentDownEvent.recycle();
                        }
                        this.mCurrentDownEvent = MotionEvent.obtain(ev);
                        this.mAlwaysInTapRegion = true;
                        this.mAlwaysInBiggerTapRegion = true;
                        this.mStillDown = true;
                        this.mInLongPress = false;
                        this.mDeferConfirmSingleTap = false;
                        this.mHasRecordedClassification = false;
                        if (this.mIsLongpressEnabled) {
                            this.mHandler.removeMessages(2);
                            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, 3, 0), this.mCurrentDownEvent.getDownTime() + ViewConfiguration.getLongPressTimeout());
                        }
                        this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                        handled2 = handled | this.mListener.onDown(ev);
                        break;
                    } else {
                        this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
                    }
                }
                handled = false;
                this.mLastFocusX = focusX;
                this.mDownFocusX = focusX;
                this.mLastFocusY = focusY;
                this.mDownFocusY = focusY;
                if (this.mCurrentDownEvent != null) {
                }
                this.mCurrentDownEvent = MotionEvent.obtain(ev);
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                this.mHasRecordedClassification = false;
                if (this.mIsLongpressEnabled) {
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                handled2 = handled | this.mListener.onDown(ev);
                break;
            case 1:
                this.mStillDown = false;
                MotionEvent currentUpEvent = MotionEvent.obtain(ev);
                if (!this.mIsDoubleTapping) {
                    if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (this.mAlwaysInTapRegion && !this.mIgnoreNextUpEvent) {
                        recordGestureClassification(1);
                        onFling = this.mListener.onSingleTapUp(ev);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(ev);
                        }
                    } else if (!this.mIgnoreNextUpEvent) {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        int pointerId = ev.getPointerId(0);
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                        float velocityY = velocityTracker.getYVelocity(pointerId);
                        float velocityX = velocityTracker.getXVelocity(pointerId);
                        if (Math.abs(velocityY) > this.mMinimumFlingVelocity || Math.abs(velocityX) > this.mMinimumFlingVelocity) {
                            onFling = this.mListener.onFling(this.mCurrentDownEvent, ev, velocityX, velocityY);
                        }
                    }
                    handled2 = false;
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle();
                    }
                    this.mPreviousUpEvent = currentUpEvent;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mIgnoreNextUpEvent = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    break;
                } else {
                    recordGestureClassification(2);
                    onFling = false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                }
                handled2 = onFling;
                if (this.mPreviousUpEvent != null) {
                }
                this.mPreviousUpEvent = currentUpEvent;
                if (this.mVelocityTracker != null) {
                }
                this.mIsDoubleTapping = false;
                this.mDeferConfirmSingleTap = false;
                this.mIgnoreNextUpEvent = false;
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
                break;
            case 2:
                if (!this.mInLongPress) {
                    if (!this.mInContextClick) {
                        int motionClassification2 = ev.getClassification();
                        boolean hasPendingLongPress2 = this.mHandler.hasMessages(2);
                        float scrollX = this.mLastFocusX - focusX;
                        float scrollY = this.mLastFocusY - focusY;
                        if (this.mIsDoubleTapping) {
                            recordGestureClassification(2);
                            handled2 = false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                            motionClassification = motionClassification2;
                            hasPendingLongPress = hasPendingLongPress2;
                        } else if (this.mAlwaysInTapRegion) {
                            int deltaX = (int) (focusX - this.mDownFocusX);
                            int deltaY = (int) (focusY - this.mDownFocusY);
                            int deltaY2 = (deltaX * deltaX) + (deltaY * deltaY);
                            int div2 = isGeneratedGesture ? 0 : this.mTouchSlopSquare;
                            boolean ambiguousGesture = motionClassification2 == 1;
                            boolean shouldInhibitDefaultAction = hasPendingLongPress2 && ambiguousGesture;
                            if (shouldInhibitDefaultAction) {
                                float multiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
                                if (deltaY2 > div2) {
                                    this.mHandler.removeMessages(2);
                                    long longPressTimeout = ViewConfiguration.getLongPressTimeout();
                                    motionClassification = motionClassification2;
                                    hasPendingLongPress = hasPendingLongPress2;
                                    handled4 = false;
                                    this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, 3, 0), ev.getDownTime() + (((float) longPressTimeout) * multiplier));
                                } else {
                                    motionClassification = motionClassification2;
                                    hasPendingLongPress = hasPendingLongPress2;
                                    handled4 = false;
                                }
                                div2 = (int) (div2 * multiplier * multiplier);
                            } else {
                                motionClassification = motionClassification2;
                                hasPendingLongPress = hasPendingLongPress2;
                                handled4 = false;
                            }
                            if (deltaY2 > div2) {
                                recordGestureClassification(5);
                                handled2 = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
                                this.mLastFocusX = focusX;
                                this.mLastFocusY = focusY;
                                this.mAlwaysInTapRegion = false;
                                this.mHandler.removeMessages(3);
                                this.mHandler.removeMessages(1);
                                this.mHandler.removeMessages(2);
                            } else {
                                handled2 = handled4;
                            }
                            int doubleTapSlopSquare = isGeneratedGesture ? 0 : this.mDoubleTapTouchSlopSquare;
                            if (deltaY2 > doubleTapSlopSquare) {
                                this.mAlwaysInBiggerTapRegion = false;
                            }
                        } else {
                            motionClassification = motionClassification2;
                            hasPendingLongPress = hasPendingLongPress2;
                            if (Math.abs(scrollX) >= 1.0f || Math.abs(scrollY) >= 1.0f) {
                                recordGestureClassification(5);
                                boolean handled6 = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
                                this.mLastFocusX = focusX;
                                this.mLastFocusY = focusY;
                                handled2 = handled6;
                            } else {
                                handled2 = false;
                            }
                        }
                        boolean deepPress = motionClassification == 2;
                        if (!deepPress || !hasPendingLongPress) {
                            break;
                        } else {
                            this.mHandler.removeMessages(2);
                            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, 4, 0));
                            break;
                        }
                    }
                    handled3 = false;
                    handled2 = handled3;
                    break;
                } else {
                    handled3 = false;
                    handled2 = handled3;
                }
                break;
            case 3:
                cancel();
                handled3 = false;
                handled2 = handled3;
                break;
            case 4:
            default:
                handled3 = false;
                handled2 = handled3;
                break;
            case 5:
                this.mLastFocusX = focusX;
                this.mDownFocusX = focusX;
                this.mLastFocusY = focusY;
                this.mDownFocusY = focusY;
                cancelTaps();
                handled3 = false;
                handled2 = handled3;
                break;
            case 6:
                this.mLastFocusX = focusX;
                this.mDownFocusX = focusX;
                this.mLastFocusY = focusY;
                this.mDownFocusY = focusY;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                int upIndex2 = ev.getActionIndex();
                int id12 = ev.getPointerId(upIndex2);
                float x1 = this.mVelocityTracker.getXVelocity(id12);
                float y12 = this.mVelocityTracker.getYVelocity(id12);
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    boolean pointerUp2 = pointerUp;
                    if (i3 < count) {
                        if (i3 == upIndex2) {
                            y1 = y12;
                            upIndex = upIndex2;
                            skipIndex = skipIndex2;
                            id1 = id12;
                        } else {
                            upIndex = upIndex2;
                            int id2 = ev.getPointerId(i3);
                            skipIndex = skipIndex2;
                            float x = this.mVelocityTracker.getXVelocity(id2) * x1;
                            id1 = id12;
                            float y = this.mVelocityTracker.getYVelocity(id2) * y12;
                            float dot = x + y;
                            if (dot < 0.0f) {
                                this.mVelocityTracker.clear();
                            } else {
                                y1 = y12;
                            }
                        }
                        i2 = i3 + 1;
                        pointerUp = pointerUp2;
                        upIndex2 = upIndex;
                        skipIndex2 = skipIndex;
                        id12 = id1;
                        y12 = y1;
                    }
                }
                handled3 = false;
                handled2 = handled3;
                break;
        }
        if (!handled2 && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(ev, 0);
        }
        return handled2;
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
                break;
            case 12:
                if (this.mInContextClick && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextClick = false;
                    this.mIgnoreNextUpEvent = true;
                    break;
                }
                break;
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
        if (this.mAlwaysInBiggerTapRegion) {
            long deltaTime = secondDown.getEventTime() - firstUp.getEventTime();
            if (deltaTime > DOUBLE_TAP_TIMEOUT || deltaTime < DOUBLE_TAP_MIN_TIME) {
                return false;
            }
            int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
            int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
            boolean isGeneratedGesture = (firstDown.getFlags() & 8) != 0;
            int slopSquare = isGeneratedGesture ? 0 : this.mDoubleTapSlopSquare;
            return (deltaX * deltaX) + (deltaY * deltaY) < slopSquare;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLongPress() {
        this.mHandler.removeMessages(3);
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = true;
        this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int classification) {
        if (this.mHasRecordedClassification || classification == 0) {
            return;
        }
        if (this.mCurrentDownEvent == null || this.mCurrentMotionEvent == null) {
            this.mHasRecordedClassification = true;
            return;
        }
        StatsLog.write(177, getClass().getName(), classification, (int) (SystemClock.uptimeMillis() - this.mCurrentMotionEvent.getDownTime()), (float) Math.hypot(this.mCurrentMotionEvent.getRawX() - this.mCurrentDownEvent.getRawX(), this.mCurrentMotionEvent.getRawY() - this.mCurrentDownEvent.getRawY()));
        this.mHasRecordedClassification = true;
    }
}
