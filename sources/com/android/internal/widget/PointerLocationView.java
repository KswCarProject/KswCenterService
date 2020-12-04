package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;
import android.view.ISystemGestureExclusionListener;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.widget.PointerLocationView;
import java.util.ArrayList;

public class PointerLocationView extends View implements InputManager.InputDeviceListener, WindowManagerPolicyConstants.PointerEventListener {
    private static final String ALT_STRATEGY_PROPERY_KEY = "debug.velocitytracker.alt";
    private static final String TAG = "Pointer";
    private int mActivePointerId;
    private final VelocityTracker mAltVelocity;
    @UnsupportedAppUsage
    private boolean mCurDown;
    @UnsupportedAppUsage
    private int mCurNumPointers;
    private final Paint mCurrentPointPaint;
    private int mHeaderBottom;
    private int mHeaderPaddingTop = 0;
    private final InputManager mIm;
    @UnsupportedAppUsage
    private int mMaxNumPointers;
    private final Paint mPaint;
    private final Paint mPathPaint;
    @UnsupportedAppUsage
    private final ArrayList<PointerState> mPointers = new ArrayList<>();
    @UnsupportedAppUsage
    private boolean mPrintCoords = true;
    private RectF mReusableOvalRect = new RectF();
    /* access modifiers changed from: private */
    public final Region mSystemGestureExclusion = new Region();
    private ISystemGestureExclusionListener mSystemGestureExclusionListener = new ISystemGestureExclusionListener.Stub() {
        public void onSystemGestureExclusionChanged(int displayId, Region systemGestureExclusion) {
            Region exclusion = Region.obtain(systemGestureExclusion);
            Handler handler = PointerLocationView.this.getHandler();
            if (handler != null) {
                handler.post(new Runnable(exclusion) {
                    private final /* synthetic */ Region f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        PointerLocationView.AnonymousClass1.lambda$onSystemGestureExclusionChanged$0(PointerLocationView.AnonymousClass1.this, this.f$1);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$onSystemGestureExclusionChanged$0(AnonymousClass1 r1, Region exclusion) {
            PointerLocationView.this.mSystemGestureExclusion.set(exclusion);
            exclusion.recycle();
            PointerLocationView.this.invalidate();
        }
    };
    private final Paint mSystemGestureExclusionPaint;
    private final Path mSystemGestureExclusionPath = new Path();
    private final Paint mTargetPaint;
    private final MotionEvent.PointerCoords mTempCoords = new MotionEvent.PointerCoords();
    private final FasterStringBuilder mText = new FasterStringBuilder();
    private final Paint mTextBackgroundPaint;
    private final Paint mTextLevelPaint;
    private final Paint.FontMetricsInt mTextMetrics = new Paint.FontMetricsInt();
    private final Paint mTextPaint;
    private final ViewConfiguration mVC;
    private final VelocityTracker mVelocity;

    public static class PointerState {
        /* access modifiers changed from: private */
        public VelocityTracker.Estimator mAltEstimator = new VelocityTracker.Estimator();
        /* access modifiers changed from: private */
        public float mAltXVelocity;
        /* access modifiers changed from: private */
        public float mAltYVelocity;
        /* access modifiers changed from: private */
        public float mBoundingBottom;
        /* access modifiers changed from: private */
        public float mBoundingLeft;
        /* access modifiers changed from: private */
        public float mBoundingRight;
        /* access modifiers changed from: private */
        public float mBoundingTop;
        /* access modifiers changed from: private */
        public MotionEvent.PointerCoords mCoords = new MotionEvent.PointerCoords();
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public boolean mCurDown;
        /* access modifiers changed from: private */
        public VelocityTracker.Estimator mEstimator = new VelocityTracker.Estimator();
        /* access modifiers changed from: private */
        public boolean mHasBoundingBox;
        /* access modifiers changed from: private */
        public int mToolType;
        /* access modifiers changed from: private */
        public int mTraceCount;
        /* access modifiers changed from: private */
        public boolean[] mTraceCurrent = new boolean[32];
        /* access modifiers changed from: private */
        public float[] mTraceX = new float[32];
        /* access modifiers changed from: private */
        public float[] mTraceY = new float[32];
        /* access modifiers changed from: private */
        public float mXVelocity;
        /* access modifiers changed from: private */
        public float mYVelocity;

        public void clearTrace() {
            this.mTraceCount = 0;
        }

        public void addTrace(float x, float y, boolean current) {
            int traceCapacity = this.mTraceX.length;
            if (this.mTraceCount == traceCapacity) {
                int traceCapacity2 = traceCapacity * 2;
                float[] newTraceX = new float[traceCapacity2];
                System.arraycopy(this.mTraceX, 0, newTraceX, 0, this.mTraceCount);
                this.mTraceX = newTraceX;
                float[] newTraceY = new float[traceCapacity2];
                System.arraycopy(this.mTraceY, 0, newTraceY, 0, this.mTraceCount);
                this.mTraceY = newTraceY;
                boolean[] newTraceCurrent = new boolean[traceCapacity2];
                System.arraycopy(this.mTraceCurrent, 0, newTraceCurrent, 0, this.mTraceCount);
                this.mTraceCurrent = newTraceCurrent;
            }
            this.mTraceX[this.mTraceCount] = x;
            this.mTraceY[this.mTraceCount] = y;
            this.mTraceCurrent[this.mTraceCount] = current;
            this.mTraceCount++;
        }
    }

    public PointerLocationView(Context c) {
        super(c);
        setFocusableInTouchMode(true);
        this.mIm = (InputManager) c.getSystemService(InputManager.class);
        this.mVC = ViewConfiguration.get(c);
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(getResources().getDisplayMetrics().density * 10.0f);
        this.mTextPaint.setARGB(255, 0, 0, 0);
        this.mTextBackgroundPaint = new Paint();
        this.mTextBackgroundPaint.setAntiAlias(false);
        this.mTextBackgroundPaint.setARGB(128, 255, 255, 255);
        this.mTextLevelPaint = new Paint();
        this.mTextLevelPaint.setAntiAlias(false);
        this.mTextLevelPaint.setARGB(192, 255, 0, 0);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setARGB(255, 255, 255, 255);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(2.0f);
        this.mCurrentPointPaint = new Paint();
        this.mCurrentPointPaint.setAntiAlias(true);
        this.mCurrentPointPaint.setARGB(255, 255, 0, 0);
        this.mCurrentPointPaint.setStyle(Paint.Style.STROKE);
        this.mCurrentPointPaint.setStrokeWidth(2.0f);
        this.mTargetPaint = new Paint();
        this.mTargetPaint.setAntiAlias(false);
        this.mTargetPaint.setARGB(255, 0, 0, 192);
        this.mPathPaint = new Paint();
        this.mPathPaint.setAntiAlias(false);
        this.mPathPaint.setARGB(255, 0, 96, 255);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(1.0f);
        this.mSystemGestureExclusionPaint = new Paint();
        this.mSystemGestureExclusionPaint.setARGB(25, 255, 0, 0);
        this.mSystemGestureExclusionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPointers.add(new PointerState());
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
        String altStrategy = SystemProperties.get(ALT_STRATEGY_PROPERY_KEY);
        if (altStrategy.length() != 0) {
            Log.d(TAG, "Comparing default velocity tracker strategy with " + altStrategy);
            this.mAltVelocity = VelocityTracker.obtain(altStrategy);
            return;
        }
        this.mAltVelocity = null;
    }

    public void setPrintCoords(boolean state) {
        this.mPrintCoords = state;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (insets.getDisplayCutout() != null) {
            this.mHeaderPaddingTop = insets.getDisplayCutout().getSafeInsetTop();
        } else {
            this.mHeaderPaddingTop = 0;
        }
        return super.onApplyWindowInsets(insets);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mTextPaint.getFontMetricsInt(this.mTextMetrics);
        this.mHeaderBottom = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + this.mTextMetrics.descent + 2;
    }

    private void drawOval(Canvas canvas, float x, float y, float major, float minor, float angle, Paint paint) {
        canvas.save(1);
        canvas.rotate((float) (((double) (180.0f * angle)) / 3.141592653589793d), x, y);
        this.mReusableOvalRect.left = x - (minor / 2.0f);
        this.mReusableOvalRect.right = (minor / 2.0f) + x;
        this.mReusableOvalRect.top = y - (major / 2.0f);
        this.mReusableOvalRect.bottom = (major / 2.0f) + y;
        canvas.drawOval(this.mReusableOvalRect, paint);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x054a  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x056a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r29) {
        /*
            r28 = this;
            r8 = r28
            r9 = r29
            int r10 = r28.getWidth()
            int r11 = r10 / 7
            int r0 = r8.mHeaderPaddingTop
            android.graphics.Paint$FontMetricsInt r1 = r8.mTextMetrics
            int r1 = r1.ascent
            int r0 = r0 - r1
            r6 = 1
            int r12 = r0 + 1
            int r13 = r8.mHeaderBottom
            java.util.ArrayList<com.android.internal.widget.PointerLocationView$PointerState> r0 = r8.mPointers
            int r14 = r0.size()
            android.graphics.Region r0 = r8.mSystemGestureExclusion
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0037
            android.graphics.Path r0 = r8.mSystemGestureExclusionPath
            r0.reset()
            android.graphics.Region r0 = r8.mSystemGestureExclusion
            android.graphics.Path r1 = r8.mSystemGestureExclusionPath
            r0.getBoundaryPath(r1)
            android.graphics.Path r0 = r8.mSystemGestureExclusionPath
            android.graphics.Paint r1 = r8.mSystemGestureExclusionPaint
            r9.drawPath(r0, r1)
        L_0x0037:
            int r0 = r8.mActivePointerId
            r15 = 0
            if (r0 < 0) goto L_0x02be
            java.util.ArrayList<com.android.internal.widget.PointerLocationView$PointerState> r0 = r8.mPointers
            int r1 = r8.mActivePointerId
            java.lang.Object r0 = r0.get(r1)
            r16 = r0
            com.android.internal.widget.PointerLocationView$PointerState r16 = (com.android.internal.widget.PointerLocationView.PointerState) r16
            r1 = 0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 + -1
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "P: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            int r1 = r8.mCurNumPointers
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((int) r1)
            java.lang.String r1 = " / "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            int r1 = r8.mMaxNumPointers
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((int) r1)
            java.lang.String r0 = r0.toString()
            float r1 = (float) r12
            android.graphics.Paint r2 = r8.mTextPaint
            r5 = 1065353216(0x3f800000, float:1.0)
            r9.drawText(r0, r5, r1, r2)
            int r17 = r16.mTraceCount
            boolean r0 = r8.mCurDown
            if (r0 == 0) goto L_0x008f
            boolean r0 = r16.mCurDown
            if (r0 != 0) goto L_0x0091
        L_0x008f:
            if (r17 != 0) goto L_0x0102
        L_0x0091:
            float r1 = (float) r11
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 2
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r0 = r8.mTextBackgroundPaint
            r18 = r0
            r0 = r29
            r19 = r5
            r5 = r18
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "X: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            android.view.MotionEvent$PointerCoords r1 = r16.mCoords
            float r1 = r1.x
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r6)
            java.lang.String r0 = r0.toString()
            int r1 = r11 + 1
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            int r0 = r11 * 2
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 3
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "Y: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            android.view.MotionEvent$PointerCoords r1 = r16.mCoords
            float r1 = r1.y
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r6)
            java.lang.String r0 = r0.toString()
            int r1 = r11 * 2
            int r1 = r1 + r6
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            goto L_0x01b1
        L_0x0102:
            r19 = r5
            float[] r0 = r16.mTraceX
            int r1 = r17 + -1
            r0 = r0[r1]
            float[] r1 = r16.mTraceX
            r1 = r1[r15]
            float r5 = r0 - r1
            float[] r0 = r16.mTraceY
            int r1 = r17 + -1
            r0 = r0[r1]
            float[] r1 = r16.mTraceY
            r1 = r1[r15]
            float r4 = r0 - r1
            float r1 = (float) r11
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 2
            int r0 = r0 - r6
            float r3 = (float) r0
            float r0 = (float) r13
            float r18 = java.lang.Math.abs(r5)
            android.view.ViewConfiguration r15 = r8.mVC
            int r15 = r15.getScaledTouchSlop()
            float r15 = (float) r15
            int r15 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1))
            if (r15 >= 0) goto L_0x013f
            android.graphics.Paint r15 = r8.mTextBackgroundPaint
            goto L_0x0141
        L_0x013f:
            android.graphics.Paint r15 = r8.mTextLevelPaint
        L_0x0141:
            r18 = r0
            r0 = r29
            r21 = r4
            r4 = r18
            r7 = r5
            r5 = r15
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "dX: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r7, (int) r6)
            java.lang.String r0 = r0.toString()
            int r1 = r11 + 1
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            int r0 = r11 * 2
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 3
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            float r0 = java.lang.Math.abs(r21)
            android.view.ViewConfiguration r5 = r8.mVC
            int r5 = r5.getScaledTouchSlop()
            float r5 = (float) r5
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0189
            android.graphics.Paint r0 = r8.mTextBackgroundPaint
        L_0x0187:
            r5 = r0
            goto L_0x018c
        L_0x0189:
            android.graphics.Paint r0 = r8.mTextLevelPaint
            goto L_0x0187
        L_0x018c:
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "dY: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            r1 = r21
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r6)
            java.lang.String r0 = r0.toString()
            int r2 = r11 * 2
            int r2 = r2 + r6
            float r2 = (float) r2
            float r3 = (float) r12
            android.graphics.Paint r4 = r8.mTextPaint
            r9.drawText(r0, r2, r3, r4)
        L_0x01b1:
            int r0 = r11 * 3
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 4
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "Xv: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            float r1 = r16.mXVelocity
            r7 = 3
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r7)
            java.lang.String r0 = r0.toString()
            int r1 = r11 * 3
            int r1 = r1 + r6
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            int r0 = r11 * 4
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 5
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "Yv: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            float r1 = r16.mYVelocity
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r7)
            java.lang.String r0 = r0.toString()
            int r1 = r11 * 4
            int r1 = r1 + r6
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            int r0 = r11 * 5
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 6
            int r0 = r0 - r6
            float r3 = (float) r0
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            int r0 = r11 * 5
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 5
            float r0 = (float) r0
            android.view.MotionEvent$PointerCoords r3 = r16.mCoords
            float r3 = r3.pressure
            float r4 = (float) r11
            float r3 = r3 * r4
            float r0 = r0 + r3
            float r3 = r0 - r19
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextLevelPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "Prs: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            android.view.MotionEvent$PointerCoords r1 = r16.mCoords
            float r1 = r1.pressure
            r2 = 2
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r2)
            java.lang.String r0 = r0.toString()
            int r1 = r11 * 5
            int r1 = r1 + r6
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            int r0 = r11 * 6
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            float r3 = (float) r10
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextBackgroundPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            int r0 = r11 * 6
            float r1 = (float) r0
            int r0 = r8.mHeaderPaddingTop
            float r2 = (float) r0
            int r0 = r11 * 6
            float r0 = (float) r0
            android.view.MotionEvent$PointerCoords r3 = r16.mCoords
            float r3 = r3.size
            float r4 = (float) r11
            float r3 = r3 * r4
            float r0 = r0 + r3
            float r3 = r0 - r19
            float r4 = (float) r13
            android.graphics.Paint r5 = r8.mTextLevelPaint
            r0 = r29
            r0.drawRect(r1, r2, r3, r4, r5)
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r8.mText
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.clear()
            java.lang.String r1 = "Size: "
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((java.lang.String) r1)
            android.view.MotionEvent$PointerCoords r1 = r16.mCoords
            float r1 = r1.size
            r7 = 2
            com.android.internal.widget.PointerLocationView$FasterStringBuilder r0 = r0.append((float) r1, (int) r7)
            java.lang.String r0 = r0.toString()
            int r1 = r11 * 6
            int r1 = r1 + r6
            float r1 = (float) r1
            float r2 = (float) r12
            android.graphics.Paint r3 = r8.mTextPaint
            r9.drawText(r0, r1, r2, r3)
            goto L_0x02bf
        L_0x02be:
            r7 = 2
        L_0x02bf:
            r0 = 0
        L_0x02c0:
            r15 = r0
            if (r15 >= r14) goto L_0x0575
            java.util.ArrayList<com.android.internal.widget.PointerLocationView$PointerState> r0 = r8.mPointers
            java.lang.Object r0 = r0.get(r15)
            r16 = r0
            com.android.internal.widget.PointerLocationView$PointerState r16 = (com.android.internal.widget.PointerLocationView.PointerState) r16
            int r6 = r16.mTraceCount
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            android.graphics.Paint r4 = r8.mPaint
            r5 = 128(0x80, float:1.794E-43)
            r23 = r10
            r10 = 255(0xff, float:3.57E-43)
            r4.setARGB(r10, r5, r10, r10)
            r4 = r0
            r18 = r2
            r17 = r3
            r0 = 0
            r3 = r1
        L_0x02e7:
            r2 = r0
            if (r2 >= r6) goto L_0x0351
            float[] r0 = r16.mTraceX
            r19 = r0[r2]
            float[] r0 = r16.mTraceY
            r21 = r0[r2]
            boolean r0 = java.lang.Float.isNaN(r19)
            if (r0 == 0) goto L_0x0308
            r0 = 0
            r18 = r0
            r24 = r2
            r25 = r11
            r26 = r12
            r12 = r5
            goto L_0x0347
        L_0x0308:
            if (r18 == 0) goto L_0x0337
            android.graphics.Paint r1 = r8.mPathPaint
            r0 = r29
            r22 = r1
            r1 = r4
            r24 = r2
            r2 = r3
            r10 = r3
            r3 = r19
            r25 = r11
            r11 = r4
            r4 = r21
            r26 = r12
            r12 = r5
            r5 = r22
            r0.drawLine(r1, r2, r3, r4, r5)
            boolean[] r0 = r16.mTraceCurrent
            boolean r0 = r0[r24]
            if (r0 == 0) goto L_0x032f
            android.graphics.Paint r0 = r8.mCurrentPointPaint
            goto L_0x0331
        L_0x032f:
            android.graphics.Paint r0 = r8.mPaint
        L_0x0331:
            r9.drawPoint(r11, r10, r0)
            r17 = 1
            goto L_0x0340
        L_0x0337:
            r24 = r2
            r10 = r3
            r25 = r11
            r26 = r12
            r11 = r4
            r12 = r5
        L_0x0340:
            r4 = r19
            r3 = r21
            r0 = 1
            r18 = r0
        L_0x0347:
            int r0 = r24 + 1
            r5 = r12
            r11 = r25
            r12 = r26
            r10 = 255(0xff, float:3.57E-43)
            goto L_0x02e7
        L_0x0351:
            r10 = r3
            r25 = r11
            r26 = r12
            r11 = r4
            r12 = r5
            if (r17 == 0) goto L_0x03a7
            android.graphics.Paint r0 = r8.mPaint
            r5 = 64
            r1 = 255(0xff, float:3.57E-43)
            r0.setARGB(r1, r1, r5, r12)
            float r0 = r16.mXVelocity
            r19 = 1098907648(0x41800000, float:16.0)
            float r21 = r0 * r19
            float r0 = r16.mYVelocity
            float r22 = r0 * r19
            float r3 = r11 + r21
            float r4 = r10 + r22
            android.graphics.Paint r2 = r8.mPaint
            r0 = r29
            r1 = r11
            r24 = r2
            r2 = r10
            r7 = r5
            r5 = r24
            r0.drawLine(r1, r2, r3, r4, r5)
            android.view.VelocityTracker r0 = r8.mAltVelocity
            if (r0 == 0) goto L_0x03a7
            android.graphics.Paint r0 = r8.mPaint
            r1 = 255(0xff, float:3.57E-43)
            r0.setARGB(r1, r7, r1, r12)
            float r0 = r16.mAltXVelocity
            float r7 = r0 * r19
            float r0 = r16.mAltYVelocity
            float r19 = r19 * r0
            float r3 = r11 + r7
            float r4 = r10 + r19
            android.graphics.Paint r5 = r8.mPaint
            r0 = r29
            r1 = r11
            r2 = r10
            r0.drawLine(r1, r2, r3, r4, r5)
        L_0x03a7:
            boolean r0 = r8.mCurDown
            if (r0 == 0) goto L_0x0568
            boolean r0 = r16.mCurDown
            if (r0 == 0) goto L_0x0568
            r1 = 0
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r2 = r0.y
            int r0 = r28.getWidth()
            float r3 = (float) r0
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r4 = r0.y
            android.graphics.Paint r5 = r8.mTargetPaint
            r0 = r29
            r0.drawLine(r1, r2, r3, r4, r5)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r1 = r0.x
            r2 = 0
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r3 = r0.x
            int r0 = r28.getHeight()
            float r4 = (float) r0
            android.graphics.Paint r5 = r8.mTargetPaint
            r0 = r29
            r0.drawLine(r1, r2, r3, r4, r5)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.pressure
            r1 = 1132396544(0x437f0000, float:255.0)
            float r0 = r0 * r1
            int r7 = (int) r0
            android.graphics.Paint r0 = r8.mPaint
            r1 = 255(0xff, float:3.57E-43)
            int r2 = 255 - r7
            r0.setARGB(r1, r7, r1, r2)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.x
            android.view.MotionEvent$PointerCoords r2 = r16.mCoords
            float r2 = r2.y
            android.graphics.Paint r3 = r8.mPaint
            r9.drawPoint(r0, r2, r3)
            android.graphics.Paint r0 = r8.mPaint
            int r2 = 255 - r7
            r0.setARGB(r1, r7, r2, r12)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r2 = r0.x
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r3 = r0.y
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r4 = r0.touchMajor
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r5 = r0.touchMinor
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r1 = r0.orientation
            android.graphics.Paint r0 = r8.mPaint
            r19 = r0
            r0 = r28
            r21 = r1
            r1 = r29
            r22 = r6
            r6 = r21
            r12 = r7
            r27 = r10
            r10 = 2
            r7 = r19
            r0.drawOval(r1, r2, r3, r4, r5, r6, r7)
            android.graphics.Paint r0 = r8.mPaint
            r1 = 255(0xff, float:3.57E-43)
            int r2 = 255 - r12
            r3 = 128(0x80, float:1.794E-43)
            r0.setARGB(r1, r12, r3, r2)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r2 = r0.x
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r3 = r0.y
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r4 = r0.toolMajor
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r5 = r0.toolMinor
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r6 = r0.orientation
            android.graphics.Paint r7 = r8.mPaint
            r0 = r28
            r1 = r29
            r0.drawOval(r1, r2, r3, r4, r5, r6, r7)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.toolMajor
            r1 = 1060320051(0x3f333333, float:0.7)
            float r0 = r0 * r1
            r1 = 1101004800(0x41a00000, float:20.0)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x0487
            r0 = 1101004800(0x41a00000, float:20.0)
        L_0x0487:
            r6 = r0
            android.graphics.Paint r0 = r8.mPaint
            r1 = 255(0xff, float:3.57E-43)
            r7 = 0
            r0.setARGB(r1, r12, r1, r7)
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.orientation
            double r0 = (double) r0
            double r0 = java.lang.Math.sin(r0)
            double r2 = (double) r6
            double r0 = r0 * r2
            float r5 = (float) r0
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.orientation
            double r0 = (double) r0
            double r0 = java.lang.Math.cos(r0)
            double r0 = -r0
            double r2 = (double) r6
            double r0 = r0 * r2
            float r4 = (float) r0
            int r0 = r16.mToolType
            if (r0 == r10) goto L_0x04f2
            int r0 = r16.mToolType
            r1 = 4
            if (r0 != r1) goto L_0x04bf
            r21 = r4
            r19 = r5
            goto L_0x04f6
        L_0x04bf:
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.x
            float r1 = r0 - r5
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.y
            float r2 = r0 - r4
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.x
            float r3 = r0 + r5
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.y
            float r19 = r0 + r4
            android.graphics.Paint r0 = r8.mPaint
            r20 = r0
            r0 = r29
            r21 = r4
            r4 = r19
            r19 = r5
            r5 = r20
            r0.drawLine(r1, r2, r3, r4, r5)
            goto L_0x0519
        L_0x04f2:
            r21 = r4
            r19 = r5
        L_0x04f6:
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r1 = r0.x
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r2 = r0.y
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.x
            float r3 = r0 + r19
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.y
            float r4 = r0 + r21
            android.graphics.Paint r5 = r8.mPaint
            r0 = r29
            r0.drawLine(r1, r2, r3, r4, r5)
        L_0x0519:
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            r1 = 25
            float r0 = r0.getAxisValue(r1)
            double r0 = (double) r0
            double r0 = java.lang.Math.sin(r0)
            float r5 = (float) r0
            android.view.MotionEvent$PointerCoords r0 = r16.mCoords
            float r0 = r0.x
            float r1 = r19 * r5
            float r0 = r0 + r1
            android.view.MotionEvent$PointerCoords r1 = r16.mCoords
            float r1 = r1.y
            float r4 = r21 * r5
            float r1 = r1 + r4
            r2 = 1077936128(0x40400000, float:3.0)
            android.graphics.Paint r3 = r8.mPaint
            r9.drawCircle(r0, r1, r2, r3)
            boolean r0 = r16.mHasBoundingBox
            if (r0 == 0) goto L_0x056a
            float r1 = r16.mBoundingLeft
            float r2 = r16.mBoundingTop
            float r3 = r16.mBoundingRight
            float r4 = r16.mBoundingBottom
            android.graphics.Paint r0 = r8.mPaint
            r20 = r0
            r0 = r29
            r24 = r5
            r5 = r20
            r0.drawRect(r1, r2, r3, r4, r5)
            goto L_0x056a
        L_0x0568:
            r7 = 0
            r10 = 2
        L_0x056a:
            int r0 = r15 + 1
            r7 = r10
            r10 = r23
            r11 = r25
            r12 = r26
            goto L_0x02c0
        L_0x0575:
            r23 = r10
            r25 = r11
            r26 = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.PointerLocationView.onDraw(android.graphics.Canvas):void");
    }

    private void logMotionEvent(String type, MotionEvent event) {
        MotionEvent motionEvent = event;
        int action = event.getAction();
        int N = event.getHistorySize();
        int NI = event.getPointerCount();
        int historyPos = 0;
        while (true) {
            int historyPos2 = historyPos;
            if (historyPos2 >= N) {
                break;
            }
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= NI) {
                    break;
                }
                int id = motionEvent.getPointerId(i2);
                motionEvent.getHistoricalPointerCoords(i2, historyPos2, this.mTempCoords);
                logCoords(type, action, i2, this.mTempCoords, id, event);
                i = i2 + 1;
            }
            historyPos = historyPos2 + 1;
        }
        for (int i3 = 0; i3 < NI; i3++) {
            int id2 = motionEvent.getPointerId(i3);
            motionEvent.getPointerCoords(i3, this.mTempCoords);
            logCoords(type, action, i3, this.mTempCoords, id2, event);
        }
    }

    private void logCoords(String type, int action, int index, MotionEvent.PointerCoords coords, int id, MotionEvent event) {
        String prefix;
        int i = action;
        int i2 = index;
        MotionEvent.PointerCoords pointerCoords = coords;
        MotionEvent motionEvent = event;
        int toolType = motionEvent.getToolType(i2);
        int buttonState = event.getButtonState();
        switch (i & 255) {
            case 0:
                prefix = "DOWN";
                break;
            case 1:
                prefix = "UP";
                break;
            case 2:
                prefix = "MOVE";
                break;
            case 3:
                prefix = "CANCEL";
                break;
            case 4:
                prefix = "OUTSIDE";
                break;
            case 5:
                if (i2 != ((i & 65280) >> 8)) {
                    prefix = "MOVE";
                    break;
                } else {
                    prefix = "DOWN";
                    break;
                }
            case 6:
                if (i2 != ((i & 65280) >> 8)) {
                    prefix = "MOVE";
                    break;
                } else {
                    prefix = "UP";
                    break;
                }
            case 7:
                prefix = "HOVER MOVE";
                break;
            case 8:
                prefix = "SCROLL";
                break;
            case 9:
                prefix = "HOVER ENTER";
                break;
            case 10:
                prefix = "HOVER EXIT";
                break;
            default:
                prefix = Integer.toString(action);
                break;
        }
        Log.i(TAG, this.mText.clear().append(type).append(" id ").append(id + 1).append(": ").append(prefix).append(" (").append(pointerCoords.x, 3).append(", ").append(pointerCoords.y, 3).append(") Pressure=").append(pointerCoords.pressure, 3).append(" Size=").append(pointerCoords.size, 3).append(" TouchMajor=").append(pointerCoords.touchMajor, 3).append(" TouchMinor=").append(pointerCoords.touchMinor, 3).append(" ToolMajor=").append(pointerCoords.toolMajor, 3).append(" ToolMinor=").append(pointerCoords.toolMinor, 3).append(" Orientation=").append((float) (((double) (pointerCoords.orientation * 180.0f)) / 3.141592653589793d), 1).append("deg").append(" Tilt=").append((float) (((double) (pointerCoords.getAxisValue(25) * 180.0f)) / 3.141592653589793d), 1).append("deg").append(" Distance=").append(pointerCoords.getAxisValue(24), 1).append(" VScroll=").append(pointerCoords.getAxisValue(9), 1).append(" HScroll=").append(pointerCoords.getAxisValue(10), 1).append(" BoundingBox=[(").append(motionEvent.getAxisValue(32), 3).append(", ").append(motionEvent.getAxisValue(33), 3).append(")").append(", (").append(motionEvent.getAxisValue(34), 3).append(", ").append(motionEvent.getAxisValue(35), 3).append(")]").append(" ToolType=").append(MotionEvent.toolTypeToString(toolType)).append(" ButtonState=").append(MotionEvent.buttonStateToString(buttonState)).toString());
    }

    public void onPointerEvent(MotionEvent event) {
        MotionEvent.PointerCoords coords;
        PointerState ps;
        int id;
        char c;
        int N;
        int historyPos;
        int i;
        MotionEvent.PointerCoords coords2;
        PointerState ps2;
        MotionEvent motionEvent = event;
        int action = event.getAction();
        int NP = this.mPointers.size();
        int i2 = 1;
        if (action == 0 || (action & 255) == 5) {
            int index = (action & 65280) >> 8;
            if (action == 0) {
                for (int p = 0; p < NP; p++) {
                    PointerState ps3 = this.mPointers.get(p);
                    ps3.clearTrace();
                    boolean unused = ps3.mCurDown = false;
                }
                this.mCurDown = true;
                this.mCurNumPointers = 0;
                this.mMaxNumPointers = 0;
                this.mVelocity.clear();
                if (this.mAltVelocity != null) {
                    this.mAltVelocity.clear();
                }
            }
            this.mCurNumPointers++;
            if (this.mMaxNumPointers < this.mCurNumPointers) {
                this.mMaxNumPointers = this.mCurNumPointers;
            }
            int id2 = motionEvent.getPointerId(index);
            while (NP <= id2) {
                this.mPointers.add(new PointerState());
                NP++;
            }
            if (this.mActivePointerId < 0 || !this.mPointers.get(this.mActivePointerId).mCurDown) {
                this.mActivePointerId = id2;
            }
            PointerState ps4 = this.mPointers.get(id2);
            boolean unused2 = ps4.mCurDown = true;
            InputDevice device = InputDevice.getDevice(event.getDeviceId());
            boolean unused3 = ps4.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
        }
        int NP2 = NP;
        int NI = event.getPointerCount();
        this.mVelocity.addMovement(motionEvent);
        this.mVelocity.computeCurrentVelocity(1);
        if (this.mAltVelocity != null) {
            this.mAltVelocity.addMovement(motionEvent);
            this.mAltVelocity.computeCurrentVelocity(1);
        }
        int N2 = event.getHistorySize();
        int historyPos2 = 0;
        while (true) {
            int historyPos3 = historyPos2;
            if (historyPos3 >= N2) {
                break;
            }
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 >= NI) {
                    break;
                }
                int id3 = motionEvent.getPointerId(i4);
                PointerState ps5 = this.mCurDown ? this.mPointers.get(id3) : null;
                MotionEvent.PointerCoords coords3 = ps5 != null ? ps5.mCoords : this.mTempCoords;
                motionEvent.getHistoricalPointerCoords(i4, historyPos3, coords3);
                if (this.mPrintCoords) {
                    coords2 = coords3;
                    ps2 = ps5;
                    i = i4;
                    historyPos = historyPos3;
                    N = N2;
                    logCoords(TAG, action, i4, coords2, id3, event);
                } else {
                    coords2 = coords3;
                    ps2 = ps5;
                    int i5 = id3;
                    i = i4;
                    historyPos = historyPos3;
                    N = N2;
                }
                if (ps2 != null) {
                    MotionEvent.PointerCoords coords4 = coords2;
                    ps2.addTrace(coords4.x, coords4.y, false);
                }
                i3 = i + 1;
                historyPos3 = historyPos;
                N2 = N;
            }
            int i6 = N2;
            historyPos2 = historyPos3 + 1;
        }
        int i7 = 0;
        while (true) {
            int i8 = i7;
            if (i8 >= NI) {
                break;
            }
            int id4 = motionEvent.getPointerId(i8);
            PointerState ps6 = this.mCurDown ? this.mPointers.get(id4) : null;
            MotionEvent.PointerCoords coords5 = ps6 != null ? ps6.mCoords : this.mTempCoords;
            motionEvent.getPointerCoords(i8, coords5);
            if (this.mPrintCoords) {
                coords = coords5;
                ps = ps6;
                id = id4;
                logCoords(TAG, action, i8, coords5, id4, event);
            } else {
                coords = coords5;
                ps = ps6;
                id = id4;
            }
            if (ps != null) {
                MotionEvent.PointerCoords coords6 = coords;
                ps.addTrace(coords6.x, coords6.y, true);
                float unused4 = ps.mXVelocity = this.mVelocity.getXVelocity(id);
                float unused5 = ps.mYVelocity = this.mVelocity.getYVelocity(id);
                this.mVelocity.getEstimator(id, ps.mEstimator);
                if (this.mAltVelocity != null) {
                    float unused6 = ps.mAltXVelocity = this.mAltVelocity.getXVelocity(id);
                    float unused7 = ps.mAltYVelocity = this.mAltVelocity.getYVelocity(id);
                    this.mAltVelocity.getEstimator(id, ps.mAltEstimator);
                }
                int unused8 = ps.mToolType = motionEvent.getToolType(i8);
                if (ps.mHasBoundingBox) {
                    c = ' ';
                    float unused9 = ps.mBoundingLeft = motionEvent.getAxisValue(32, i8);
                    float unused10 = ps.mBoundingTop = motionEvent.getAxisValue(33, i8);
                    float unused11 = ps.mBoundingRight = motionEvent.getAxisValue(34, i8);
                    float unused12 = ps.mBoundingBottom = motionEvent.getAxisValue(35, i8);
                    i7 = i8 + 1;
                    char c2 = c;
                }
            }
            c = ' ';
            i7 = i8 + 1;
            char c22 = c;
        }
        if (action == 1 || action == 3 || (action & 255) == 6) {
            int index2 = (65280 & action) >> 8;
            int id5 = motionEvent.getPointerId(index2);
            if (id5 >= NP2) {
                Slog.wtf(TAG, "Got pointer ID out of bounds: id=" + id5 + " arraysize=" + NP2 + " pointerindex=" + index2 + " action=0x" + Integer.toHexString(action));
                return;
            }
            PointerState ps7 = this.mPointers.get(id5);
            boolean unused13 = ps7.mCurDown = false;
            if (action == 1 || action == 3) {
                this.mCurDown = false;
                this.mCurNumPointers = 0;
            } else {
                this.mCurNumPointers--;
                if (this.mActivePointerId == id5) {
                    if (index2 != 0) {
                        i2 = 0;
                    }
                    this.mActivePointerId = motionEvent.getPointerId(i2);
                }
                ps7.addTrace(Float.NaN, Float.NaN, false);
            }
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        onPointerEvent(event);
        if (event.getAction() != 0 || isFocused()) {
            return true;
        }
        requestFocus();
        return true;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        int source = event.getSource();
        if ((source & 2) != 0) {
            onPointerEvent(event);
            return true;
        } else if ((source & 16) != 0) {
            logMotionEvent("Joystick", event);
            return true;
        } else if ((source & 8) != 0) {
            logMotionEvent("Position", event);
            return true;
        } else {
            logMotionEvent("Generic", event);
            return true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!shouldLogKey(keyCode)) {
            return super.onKeyDown(keyCode, event);
        }
        int repeatCount = event.getRepeatCount();
        if (repeatCount == 0) {
            Log.i(TAG, "Key Down: " + event);
            return true;
        }
        Log.i(TAG, "Key Repeat #" + repeatCount + ": " + event);
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!shouldLogKey(keyCode)) {
            return super.onKeyUp(keyCode, event);
        }
        Log.i(TAG, "Key Up: " + event);
        return true;
    }

    private static boolean shouldLogKey(int keyCode) {
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return true;
            default:
                if (KeyEvent.isGamepadButton(keyCode) || KeyEvent.isModifierKey(keyCode)) {
                    return true;
                }
                return false;
        }
    }

    public boolean onTrackballEvent(MotionEvent event) {
        logMotionEvent("Trackball", event);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIm.registerInputDeviceListener(this, getHandler());
        if (shouldShowSystemGestureExclusion()) {
            try {
                WindowManagerGlobal.getWindowManagerService().registerSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            this.mSystemGestureExclusion.setEmpty();
        }
        logInputDevices();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIm.unregisterInputDeviceListener(this);
        try {
            WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onInputDeviceAdded(int deviceId) {
        logInputDeviceState(deviceId, "Device Added");
    }

    public void onInputDeviceChanged(int deviceId) {
        logInputDeviceState(deviceId, "Device Changed");
    }

    public void onInputDeviceRemoved(int deviceId) {
        logInputDeviceState(deviceId, "Device Removed");
    }

    private void logInputDevices() {
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int logInputDeviceState : deviceIds) {
            logInputDeviceState(logInputDeviceState, "Device Enumerated");
        }
    }

    private void logInputDeviceState(int deviceId, String state) {
        InputDevice device = this.mIm.getInputDevice(deviceId);
        if (device != null) {
            Log.i(TAG, state + ": " + device);
            return;
        }
        Log.i(TAG, state + ": " + deviceId);
    }

    private static boolean shouldShowSystemGestureExclusion() {
        return SystemProperties.getBoolean("debug.pointerlocation.showexclusion", false);
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public FasterStringBuilder append(String value) {
            int valueLength = value.length();
            value.getChars(0, valueLength, this.mChars, reserve(valueLength));
            this.mLength += valueLength;
            return this;
        }

        public FasterStringBuilder append(int value) {
            return append(value, 0);
        }

        public FasterStringBuilder append(int value, int zeroPadWidth) {
            int index;
            boolean negative = value < 0;
            if (!negative || (value = -value) >= 0) {
                int index2 = reserve(11);
                char[] chars = this.mChars;
                if (value == 0) {
                    int i = index2 + 1;
                    chars[index2] = '0';
                    this.mLength++;
                    return this;
                }
                if (negative) {
                    index = index2 + 1;
                    chars[index2] = '-';
                } else {
                    index = index2;
                }
                int divisor = 1000000000;
                int index3 = index;
                int numberWidth = 10;
                while (value < divisor) {
                    divisor /= 10;
                    numberWidth--;
                    if (numberWidth < zeroPadWidth) {
                        chars[index3] = '0';
                        index3++;
                    }
                }
                while (true) {
                    int digit = value / divisor;
                    value -= digit * divisor;
                    divisor /= 10;
                    int index4 = index3 + 1;
                    chars[index3] = (char) (digit + 48);
                    if (divisor == 0) {
                        this.mLength = index4;
                        return this;
                    }
                    index3 = index4;
                }
            } else {
                append("-2147483648");
                return this;
            }
        }

        public FasterStringBuilder append(float value, int precision) {
            int scale = 1;
            for (int i = 0; i < precision; i++) {
                scale *= 10;
            }
            float value2 = (float) (Math.rint((double) (((float) scale) * value)) / ((double) scale));
            if (((int) value2) == 0 && value2 < 0.0f) {
                append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append((int) value2);
            if (precision != 0) {
                append(".");
                float value3 = Math.abs(value2);
                append((int) (((float) scale) * ((float) (((double) value3) - Math.floor((double) value3)))), precision);
            }
            return this;
        }

        public String toString() {
            return new String(this.mChars, 0, this.mLength);
        }

        private int reserve(int length) {
            int oldLength = this.mLength;
            int newLength = this.mLength + length;
            char[] oldChars = this.mChars;
            int oldCapacity = oldChars.length;
            if (newLength > oldCapacity) {
                char[] newChars = new char[(oldCapacity * 2)];
                System.arraycopy(oldChars, 0, newChars, 0, oldLength);
                this.mChars = newChars;
            }
            return oldLength;
        }
    }
}
