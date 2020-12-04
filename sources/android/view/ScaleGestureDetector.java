package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;

public class ScaleGestureDetector {
    private static final int ANCHORED_SCALE_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_SCALE_MODE_NONE = 0;
    private static final int ANCHORED_SCALE_MODE_STYLUS = 2;
    private static final float SCALE_FACTOR = 0.5f;
    private static final String TAG = "ScaleGestureDetector";
    private static final long TOUCH_STABILIZE_TIME = 128;
    /* access modifiers changed from: private */
    public int mAnchoredScaleMode;
    /* access modifiers changed from: private */
    public float mAnchoredScaleStartX;
    /* access modifiers changed from: private */
    public float mAnchoredScaleStartY;
    private final Context mContext;
    private float mCurrSpan;
    private float mCurrSpanX;
    private float mCurrSpanY;
    private long mCurrTime;
    private boolean mEventBeforeOrAboveStartingGestureEvent;
    private float mFocusX;
    private float mFocusY;
    private GestureDetector mGestureDetector;
    private final Handler mHandler;
    private boolean mInProgress;
    private float mInitialSpan;
    private final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    @UnsupportedAppUsage
    private final OnScaleGestureListener mListener;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768938)
    private int mMinSpan;
    private float mPrevSpan;
    private float mPrevSpanX;
    private float mPrevSpanY;
    private long mPrevTime;
    private boolean mQuickScaleEnabled;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768938)
    private int mSpanSlop;
    private boolean mStylusScaleEnabled;

    public interface OnScaleGestureListener {
        boolean onScale(ScaleGestureDetector scaleGestureDetector);

        boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector);

        void onScaleEnd(ScaleGestureDetector scaleGestureDetector);
    }

    public static class SimpleOnScaleGestureListener implements OnScaleGestureListener {
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener listener) {
        this(context, listener, (Handler) null);
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener listener, Handler handler) {
        this.mAnchoredScaleMode = 0;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mContext = context;
        this.mListener = listener;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mSpanSlop = viewConfiguration.getScaledTouchSlop() * 2;
        this.mMinSpan = viewConfiguration.getScaledMinimumScalingSpan();
        this.mHandler = handler;
        int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        if (targetSdkVersion > 18) {
            setQuickScaleEnabled(true);
        }
        if (targetSdkVersion > 22) {
            setStylusScaleEnabled(true);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float focusX;
        float focusY;
        float span;
        MotionEvent motionEvent = event;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 0);
        }
        this.mCurrTime = event.getEventTime();
        int action = event.getActionMasked();
        if (this.mQuickScaleEnabled) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        int count = event.getPointerCount();
        boolean isStylusButtonDown = (event.getButtonState() & 32) != 0;
        boolean anchoredScaleCancelled = this.mAnchoredScaleMode == 2 && !isStylusButtonDown;
        boolean streamComplete = action == 1 || action == 3 || anchoredScaleCancelled;
        if (action == 0 || streamComplete) {
            if (this.mInProgress) {
                this.mListener.onScaleEnd(this);
                this.mInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            } else if (inAnchoredScaleMode() && streamComplete) {
                this.mInProgress = false;
                this.mInitialSpan = 0.0f;
                this.mAnchoredScaleMode = 0;
            }
            if (streamComplete) {
                return true;
            }
        }
        if (!this.mInProgress && this.mStylusScaleEnabled && !inAnchoredScaleMode() && !streamComplete && isStylusButtonDown) {
            this.mAnchoredScaleStartX = event.getX();
            this.mAnchoredScaleStartY = event.getY();
            this.mAnchoredScaleMode = 2;
            this.mInitialSpan = 0.0f;
        }
        boolean configChanged = action == 0 || action == 6 || action == 5 || anchoredScaleCancelled;
        boolean pointerUp = action == 6;
        int skipIndex = pointerUp ? event.getActionIndex() : -1;
        float sumX = 0.0f;
        float sumY = 0.0f;
        int div = pointerUp ? count - 1 : count;
        if (inAnchoredScaleMode()) {
            focusX = this.mAnchoredScaleStartX;
            focusY = this.mAnchoredScaleStartY;
            if (event.getY() < focusY) {
                this.mEventBeforeOrAboveStartingGestureEvent = true;
            } else {
                this.mEventBeforeOrAboveStartingGestureEvent = false;
            }
        } else {
            for (int i = 0; i < count; i++) {
                if (skipIndex != i) {
                    sumX += motionEvent.getX(i);
                    sumY += motionEvent.getY(i);
                }
            }
            focusX = sumX / ((float) div);
            focusY = sumY / ((float) div);
        }
        float devSumY = 0.0f;
        float devSumX = 0.0f;
        for (int i2 = 0; i2 < count; i2++) {
            if (skipIndex != i2) {
                devSumX += Math.abs(motionEvent.getX(i2) - focusX);
                devSumY += Math.abs(motionEvent.getY(i2) - focusY);
            }
        }
        float devX = devSumX / ((float) div);
        float devY = devSumY / ((float) div);
        int i3 = count;
        float spanX = devX * 2.0f;
        boolean z = isStylusButtonDown;
        float spanY = devY * 2.0f;
        if (inAnchoredScaleMode()) {
            span = spanY;
            float f = devX;
            boolean z2 = anchoredScaleCancelled;
            boolean z3 = streamComplete;
            boolean z4 = pointerUp;
        } else {
            float f2 = devX;
            boolean z5 = anchoredScaleCancelled;
            boolean z6 = streamComplete;
            boolean z7 = pointerUp;
            span = (float) Math.hypot((double) spanX, (double) spanY);
        }
        float span2 = span;
        boolean wasInProgress = this.mInProgress;
        this.mFocusX = focusX;
        this.mFocusY = focusY;
        if (!inAnchoredScaleMode() && this.mInProgress && (span2 < ((float) this.mMinSpan) || configChanged)) {
            this.mListener.onScaleEnd(this);
            this.mInProgress = false;
            this.mInitialSpan = span2;
        }
        if (configChanged) {
            this.mCurrSpanX = spanX;
            this.mPrevSpanX = spanX;
            this.mCurrSpanY = spanY;
            this.mPrevSpanY = spanY;
            this.mCurrSpan = span2;
            this.mPrevSpan = span2;
            this.mInitialSpan = span2;
        }
        int minSpan = inAnchoredScaleMode() ? this.mSpanSlop : this.mMinSpan;
        if (this.mInProgress || span2 < ((float) minSpan)) {
            boolean z8 = wasInProgress;
            float f3 = focusX;
        } else {
            if (!wasInProgress) {
                float f4 = devY;
                if (Math.abs(span2 - this.mInitialSpan) <= ((float) this.mSpanSlop)) {
                    boolean z9 = wasInProgress;
                    float f5 = focusX;
                }
            }
            this.mCurrSpanX = spanX;
            this.mPrevSpanX = spanX;
            this.mCurrSpanY = spanY;
            this.mPrevSpanY = spanY;
            this.mCurrSpan = span2;
            this.mPrevSpan = span2;
            boolean z10 = wasInProgress;
            float f6 = focusX;
            this.mPrevTime = this.mCurrTime;
            this.mInProgress = this.mListener.onScaleBegin(this);
        }
        if (action != 2) {
            return true;
        }
        this.mCurrSpanX = spanX;
        this.mCurrSpanY = spanY;
        this.mCurrSpan = span2;
        boolean updatePrev = true;
        if (this.mInProgress) {
            updatePrev = this.mListener.onScale(this);
        }
        if (!updatePrev) {
            return true;
        }
        this.mPrevSpanX = this.mCurrSpanX;
        this.mPrevSpanY = this.mCurrSpanY;
        this.mPrevSpan = this.mCurrSpan;
        this.mPrevTime = this.mCurrTime;
        return true;
    }

    private boolean inAnchoredScaleMode() {
        return this.mAnchoredScaleMode != 0;
    }

    public void setQuickScaleEnabled(boolean scales) {
        this.mQuickScaleEnabled = scales;
        if (this.mQuickScaleEnabled && this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.mContext, new GestureDetector.SimpleOnGestureListener() {
                public boolean onDoubleTap(MotionEvent e) {
                    float unused = ScaleGestureDetector.this.mAnchoredScaleStartX = e.getX();
                    float unused2 = ScaleGestureDetector.this.mAnchoredScaleStartY = e.getY();
                    int unused3 = ScaleGestureDetector.this.mAnchoredScaleMode = 1;
                    return true;
                }
            }, this.mHandler);
        }
    }

    public boolean isQuickScaleEnabled() {
        return this.mQuickScaleEnabled;
    }

    public void setStylusScaleEnabled(boolean scales) {
        this.mStylusScaleEnabled = scales;
    }

    public boolean isStylusScaleEnabled() {
        return this.mStylusScaleEnabled;
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getCurrentSpan() {
        return this.mCurrSpan;
    }

    public float getCurrentSpanX() {
        return this.mCurrSpanX;
    }

    public float getCurrentSpanY() {
        return this.mCurrSpanY;
    }

    public float getPreviousSpan() {
        return this.mPrevSpan;
    }

    public float getPreviousSpanX() {
        return this.mPrevSpanX;
    }

    public float getPreviousSpanY() {
        return this.mPrevSpanY;
    }

    public float getScaleFactor() {
        if (inAnchoredScaleMode()) {
            boolean scaleUp = (this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan < this.mPrevSpan) || (!this.mEventBeforeOrAboveStartingGestureEvent && this.mCurrSpan > this.mPrevSpan);
            float spanDiff = Math.abs(1.0f - (this.mCurrSpan / this.mPrevSpan)) * SCALE_FACTOR;
            if (this.mPrevSpan <= ((float) this.mSpanSlop)) {
                return 1.0f;
            }
            return scaleUp ? 1.0f + spanDiff : 1.0f - spanDiff;
        } else if (this.mPrevSpan > 0.0f) {
            return this.mCurrSpan / this.mPrevSpan;
        } else {
            return 1.0f;
        }
    }

    public long getTimeDelta() {
        return this.mCurrTime - this.mPrevTime;
    }

    public long getEventTime() {
        return this.mCurrTime;
    }
}
