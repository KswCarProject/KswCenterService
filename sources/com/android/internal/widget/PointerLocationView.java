package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.p007os.Handler;
import android.p007os.RemoteException;
import android.p007os.SystemProperties;
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
import com.ibm.icu.text.PluralRules;
import java.util.ArrayList;

/* loaded from: classes4.dex */
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
    private int mHeaderPaddingTop;
    private final InputManager mIm;
    @UnsupportedAppUsage
    private int mMaxNumPointers;
    private final Paint mPaint;
    private final Paint mPathPaint;
    @UnsupportedAppUsage
    private final ArrayList<PointerState> mPointers;
    @UnsupportedAppUsage
    private boolean mPrintCoords;
    private RectF mReusableOvalRect;
    private final Region mSystemGestureExclusion;
    private ISystemGestureExclusionListener mSystemGestureExclusionListener;
    private final Paint mSystemGestureExclusionPaint;
    private final Path mSystemGestureExclusionPath;
    private final Paint mTargetPaint;
    private final MotionEvent.PointerCoords mTempCoords;
    private final FasterStringBuilder mText;
    private final Paint mTextBackgroundPaint;
    private final Paint mTextLevelPaint;
    private final Paint.FontMetricsInt mTextMetrics;
    private final Paint mTextPaint;
    private final ViewConfiguration mVC;
    private final VelocityTracker mVelocity;

    /* loaded from: classes4.dex */
    public static class PointerState {
        private float mAltXVelocity;
        private float mAltYVelocity;
        private float mBoundingBottom;
        private float mBoundingLeft;
        private float mBoundingRight;
        private float mBoundingTop;
        @UnsupportedAppUsage
        private boolean mCurDown;
        private boolean mHasBoundingBox;
        private int mToolType;
        private int mTraceCount;
        private float mXVelocity;
        private float mYVelocity;
        private float[] mTraceX = new float[32];
        private float[] mTraceY = new float[32];
        private boolean[] mTraceCurrent = new boolean[32];
        private MotionEvent.PointerCoords mCoords = new MotionEvent.PointerCoords();
        private VelocityTracker.Estimator mEstimator = new VelocityTracker.Estimator();
        private VelocityTracker.Estimator mAltEstimator = new VelocityTracker.Estimator();

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
        this.mTextMetrics = new Paint.FontMetricsInt();
        this.mHeaderPaddingTop = 0;
        this.mPointers = new ArrayList<>();
        this.mTempCoords = new MotionEvent.PointerCoords();
        this.mSystemGestureExclusion = new Region();
        this.mSystemGestureExclusionPath = new Path();
        this.mText = new FasterStringBuilder();
        this.mPrintCoords = true;
        this.mReusableOvalRect = new RectF();
        this.mSystemGestureExclusionListener = new BinderC33761();
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
        PointerState ps = new PointerState();
        this.mPointers.add(ps);
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
        String altStrategy = SystemProperties.get(ALT_STRATEGY_PROPERY_KEY);
        if (altStrategy.length() != 0) {
            Log.m72d(TAG, "Comparing default velocity tracker strategy with " + altStrategy);
            this.mAltVelocity = VelocityTracker.obtain(altStrategy);
            return;
        }
        this.mAltVelocity = null;
    }

    public void setPrintCoords(boolean state) {
        this.mPrintCoords = state;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (insets.getDisplayCutout() != null) {
            this.mHeaderPaddingTop = insets.getDisplayCutout().getSafeInsetTop();
        } else {
            this.mHeaderPaddingTop = 0;
        }
        return super.onApplyWindowInsets(insets);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mTextPaint.getFontMetricsInt(this.mTextMetrics);
        this.mHeaderBottom = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + this.mTextMetrics.descent + 2;
    }

    private void drawOval(Canvas canvas, float x, float y, float major, float minor, float angle, Paint paint) {
        canvas.save(1);
        canvas.rotate((float) ((180.0f * angle) / 3.141592653589793d), x, y);
        this.mReusableOvalRect.left = x - (minor / 2.0f);
        this.mReusableOvalRect.right = (minor / 2.0f) + x;
        this.mReusableOvalRect.top = y - (major / 2.0f);
        this.mReusableOvalRect.bottom = (major / 2.0f) + y;
        canvas.drawOval(this.mReusableOvalRect, paint);
        canvas.restore();
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x056a A[SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onDraw(Canvas canvas) {
        char c;
        float orientationVectorY;
        float orientationVectorX;
        int i;
        int itemW;
        int base;
        int base2;
        float f;
        Paint paint;
        int w = getWidth();
        int itemW2 = w / 7;
        int base3 = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + 1;
        int bottom = this.mHeaderBottom;
        int NP = this.mPointers.size();
        if (!this.mSystemGestureExclusion.isEmpty()) {
            this.mSystemGestureExclusionPath.reset();
            this.mSystemGestureExclusion.getBoundaryPath(this.mSystemGestureExclusionPath);
            canvas.drawPath(this.mSystemGestureExclusionPath, this.mSystemGestureExclusionPaint);
        }
        if (this.mActivePointerId >= 0) {
            PointerState ps = this.mPointers.get(this.mActivePointerId);
            canvas.drawRect(0.0f, this.mHeaderPaddingTop, itemW2 - 1, bottom, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("P: ").append(this.mCurNumPointers).append(" / ").append(this.mMaxNumPointers).toString(), 1.0f, base3, this.mTextPaint);
            int N = ps.mTraceCount;
            if ((this.mCurDown && ps.mCurDown) || N == 0) {
                f = 1.0f;
                canvas.drawRect(itemW2, this.mHeaderPaddingTop, (itemW2 * 2) - 1, bottom, this.mTextBackgroundPaint);
                canvas.drawText(this.mText.clear().append("X: ").append(ps.mCoords.f2401x, 1).toString(), itemW2 + 1, base3, this.mTextPaint);
                canvas.drawRect(itemW2 * 2, this.mHeaderPaddingTop, (itemW2 * 3) - 1, bottom, this.mTextBackgroundPaint);
                canvas.drawText(this.mText.clear().append("Y: ").append(ps.mCoords.f2402y, 1).toString(), (itemW2 * 2) + 1, base3, this.mTextPaint);
            } else {
                f = 1.0f;
                float dx = ps.mTraceX[N - 1] - ps.mTraceX[0];
                float dy = ps.mTraceY[N - 1] - ps.mTraceY[0];
                float f2 = itemW2;
                float f3 = this.mHeaderPaddingTop;
                float f4 = (itemW2 * 2) - 1;
                float f5 = bottom;
                if (Math.abs(dx) >= this.mVC.getScaledTouchSlop()) {
                    paint = this.mTextLevelPaint;
                } else {
                    paint = this.mTextBackgroundPaint;
                }
                canvas.drawRect(f2, f3, f4, f5, paint);
                canvas.drawText(this.mText.clear().append("dX: ").append(dx, 1).toString(), itemW2 + 1, base3, this.mTextPaint);
                canvas.drawRect(itemW2 * 2, this.mHeaderPaddingTop, (itemW2 * 3) - 1, bottom, Math.abs(dy) < ((float) this.mVC.getScaledTouchSlop()) ? this.mTextBackgroundPaint : this.mTextLevelPaint);
                canvas.drawText(this.mText.clear().append("dY: ").append(dy, 1).toString(), (itemW2 * 2) + 1, base3, this.mTextPaint);
            }
            canvas.drawRect(itemW2 * 3, this.mHeaderPaddingTop, (itemW2 * 4) - 1, bottom, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Xv: ").append(ps.mXVelocity, 3).toString(), (itemW2 * 3) + 1, base3, this.mTextPaint);
            canvas.drawRect(itemW2 * 4, this.mHeaderPaddingTop, (itemW2 * 5) - 1, bottom, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Yv: ").append(ps.mYVelocity, 3).toString(), (itemW2 * 4) + 1, base3, this.mTextPaint);
            canvas.drawRect(itemW2 * 5, this.mHeaderPaddingTop, (itemW2 * 6) - 1, bottom, this.mTextBackgroundPaint);
            canvas.drawRect(itemW2 * 5, this.mHeaderPaddingTop, ((itemW2 * 5) + (ps.mCoords.pressure * itemW2)) - f, bottom, this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("Prs: ").append(ps.mCoords.pressure, 2).toString(), (itemW2 * 5) + 1, base3, this.mTextPaint);
            canvas.drawRect(itemW2 * 6, this.mHeaderPaddingTop, w, bottom, this.mTextBackgroundPaint);
            canvas.drawRect(itemW2 * 6, this.mHeaderPaddingTop, ((itemW2 * 6) + (ps.mCoords.size * itemW2)) - f, bottom, this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("Size: ").append(ps.mCoords.size, 2).toString(), (itemW2 * 6) + 1, base3, this.mTextPaint);
        }
        int p = 0;
        while (true) {
            int p2 = p;
            if (p2 >= NP) {
                return;
            }
            PointerState ps2 = this.mPointers.get(p2);
            int N2 = ps2.mTraceCount;
            int i2 = 128;
            int w2 = w;
            this.mPaint.setARGB(255, 128, 255, 255);
            float lastX = 0.0f;
            boolean haveLast = false;
            boolean drawn = false;
            int i3 = 0;
            float lastY = 0.0f;
            while (true) {
                int i4 = i3;
                if (i4 >= N2) {
                    break;
                }
                float x = ps2.mTraceX[i4];
                float y = ps2.mTraceY[i4];
                if (Float.isNaN(x)) {
                    haveLast = false;
                    i = i4;
                    itemW = itemW2;
                    base = base3;
                    base2 = i2;
                } else {
                    if (haveLast) {
                        i = i4;
                        float lastY2 = lastY;
                        itemW = itemW2;
                        float lastX2 = lastX;
                        base = base3;
                        base2 = i2;
                        canvas.drawLine(lastX, lastY, x, y, this.mPathPaint);
                        Paint paint2 = ps2.mTraceCurrent[i] ? this.mCurrentPointPaint : this.mPaint;
                        canvas.drawPoint(lastX2, lastY2, paint2);
                        drawn = true;
                    } else {
                        i = i4;
                        itemW = itemW2;
                        base = base3;
                        base2 = i2;
                    }
                    lastX = x;
                    lastY = y;
                    haveLast = true;
                }
                i3 = i + 1;
                i2 = base2;
                itemW2 = itemW;
                base3 = base;
            }
            float lastY3 = lastY;
            int itemW3 = itemW2;
            int base4 = base3;
            float lastX3 = lastX;
            int base5 = i2;
            if (drawn) {
                this.mPaint.setARGB(255, 255, 64, base5);
                float xVel = ps2.mXVelocity * 16.0f;
                float yVel = ps2.mYVelocity * 16.0f;
                canvas.drawLine(lastX3, lastY3, lastX3 + xVel, lastY3 + yVel, this.mPaint);
                if (this.mAltVelocity != null) {
                    this.mPaint.setARGB(255, 64, 255, base5);
                    float xVel2 = ps2.mAltXVelocity * 16.0f;
                    float yVel2 = 16.0f * ps2.mAltYVelocity;
                    canvas.drawLine(lastX3, lastY3, lastX3 + xVel2, lastY3 + yVel2, this.mPaint);
                }
            }
            if (this.mCurDown && ps2.mCurDown) {
                canvas.drawLine(0.0f, ps2.mCoords.f2402y, getWidth(), ps2.mCoords.f2402y, this.mTargetPaint);
                canvas.drawLine(ps2.mCoords.f2401x, 0.0f, ps2.mCoords.f2401x, getHeight(), this.mTargetPaint);
                int pressureLevel = (int) (ps2.mCoords.pressure * 255.0f);
                this.mPaint.setARGB(255, pressureLevel, 255, 255 - pressureLevel);
                canvas.drawPoint(ps2.mCoords.f2401x, ps2.mCoords.f2402y, this.mPaint);
                this.mPaint.setARGB(255, pressureLevel, 255 - pressureLevel, base5);
                c = 2;
                drawOval(canvas, ps2.mCoords.f2401x, ps2.mCoords.f2402y, ps2.mCoords.touchMajor, ps2.mCoords.touchMinor, ps2.mCoords.orientation, this.mPaint);
                this.mPaint.setARGB(255, pressureLevel, 128, 255 - pressureLevel);
                drawOval(canvas, ps2.mCoords.f2401x, ps2.mCoords.f2402y, ps2.mCoords.toolMajor, ps2.mCoords.toolMinor, ps2.mCoords.orientation, this.mPaint);
                float arrowSize = ps2.mCoords.toolMajor * 0.7f;
                if (arrowSize < 20.0f) {
                    arrowSize = 20.0f;
                }
                float arrowSize2 = arrowSize;
                this.mPaint.setARGB(255, pressureLevel, 255, 0);
                float orientationVectorX2 = (float) (Math.sin(ps2.mCoords.orientation) * arrowSize2);
                float orientationVectorY2 = (float) ((-Math.cos(ps2.mCoords.orientation)) * arrowSize2);
                if (ps2.mToolType != 2) {
                    if (ps2.mToolType == 4) {
                        orientationVectorY = orientationVectorY2;
                        orientationVectorX = orientationVectorX2;
                    } else {
                        orientationVectorY = orientationVectorY2;
                        orientationVectorX = orientationVectorX2;
                        canvas.drawLine(ps2.mCoords.f2401x - orientationVectorX2, ps2.mCoords.f2402y - orientationVectorY2, ps2.mCoords.f2401x + orientationVectorX2, ps2.mCoords.f2402y + orientationVectorY2, this.mPaint);
                        float tiltScale = (float) Math.sin(ps2.mCoords.getAxisValue(25));
                        canvas.drawCircle(ps2.mCoords.f2401x + (orientationVectorX * tiltScale), ps2.mCoords.f2402y + (orientationVectorY * tiltScale), 3.0f, this.mPaint);
                        if (!ps2.mHasBoundingBox) {
                            canvas.drawRect(ps2.mBoundingLeft, ps2.mBoundingTop, ps2.mBoundingRight, ps2.mBoundingBottom, this.mPaint);
                        }
                    }
                } else {
                    orientationVectorY = orientationVectorY2;
                    orientationVectorX = orientationVectorX2;
                }
                canvas.drawLine(ps2.mCoords.f2401x, ps2.mCoords.f2402y, ps2.mCoords.f2401x + orientationVectorX, ps2.mCoords.f2402y + orientationVectorY, this.mPaint);
                float tiltScale2 = (float) Math.sin(ps2.mCoords.getAxisValue(25));
                canvas.drawCircle(ps2.mCoords.f2401x + (orientationVectorX * tiltScale2), ps2.mCoords.f2402y + (orientationVectorY * tiltScale2), 3.0f, this.mPaint);
                if (!ps2.mHasBoundingBox) {
                }
            } else {
                c = 2;
            }
            p = p2 + 1;
            w = w2;
            itemW2 = itemW3;
            base3 = base4;
        }
    }

    private void logMotionEvent(String type, MotionEvent event) {
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
                if (i2 < NI) {
                    int id = event.getPointerId(i2);
                    event.getHistoricalPointerCoords(i2, historyPos2, this.mTempCoords);
                    logCoords(type, action, i2, this.mTempCoords, id, event);
                    i = i2 + 1;
                }
            }
            historyPos = historyPos2 + 1;
        }
        for (int i3 = 0; i3 < NI; i3++) {
            int id2 = event.getPointerId(i3);
            event.getPointerCoords(i3, this.mTempCoords);
            logCoords(type, action, i3, this.mTempCoords, id2, event);
        }
    }

    private void logCoords(String type, int action, int index, MotionEvent.PointerCoords coords, int id, MotionEvent event) {
        String prefix;
        int toolType = event.getToolType(index);
        int buttonState = event.getButtonState();
        switch (action & 255) {
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
                if (index == ((action & 65280) >> 8)) {
                    prefix = "DOWN";
                    break;
                } else {
                    prefix = "MOVE";
                    break;
                }
            case 6:
                if (index == ((action & 65280) >> 8)) {
                    prefix = "UP";
                    break;
                } else {
                    prefix = "MOVE";
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
        Log.m68i(TAG, this.mText.clear().append(type).append(" id ").append(id + 1).append(PluralRules.KEYWORD_RULE_SEPARATOR).append(prefix).append(" (").append(coords.f2401x, 3).append(", ").append(coords.f2402y, 3).append(") Pressure=").append(coords.pressure, 3).append(" Size=").append(coords.size, 3).append(" TouchMajor=").append(coords.touchMajor, 3).append(" TouchMinor=").append(coords.touchMinor, 3).append(" ToolMajor=").append(coords.toolMajor, 3).append(" ToolMinor=").append(coords.toolMinor, 3).append(" Orientation=").append((float) ((coords.orientation * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Tilt=").append((float) ((coords.getAxisValue(25) * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Distance=").append(coords.getAxisValue(24), 1).append(" VScroll=").append(coords.getAxisValue(9), 1).append(" HScroll=").append(coords.getAxisValue(10), 1).append(" BoundingBox=[(").append(event.getAxisValue(32), 3).append(", ").append(event.getAxisValue(33), 3).append(")").append(", (").append(event.getAxisValue(34), 3).append(", ").append(event.getAxisValue(35), 3).append(")]").append(" ToolType=").append(MotionEvent.toolTypeToString(toolType)).append(" ButtonState=").append(MotionEvent.buttonStateToString(buttonState)).toString());
    }

    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(MotionEvent event) {
        MotionEvent.PointerCoords pointerCoords;
        MotionEvent.PointerCoords coords;
        PointerState ps;
        int id;
        char c;
        MotionEvent.PointerCoords pointerCoords2;
        MotionEvent.PointerCoords coords2;
        PointerState ps2;
        int i;
        int historyPos;
        int N;
        int action = event.getAction();
        int NP = this.mPointers.size();
        if (action == 0 || (action & 255) == 5) {
            int index = (action & 65280) >> 8;
            if (action == 0) {
                for (int p = 0; p < NP; p++) {
                    PointerState ps3 = this.mPointers.get(p);
                    ps3.clearTrace();
                    ps3.mCurDown = false;
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
            int id2 = event.getPointerId(index);
            while (NP <= id2) {
                this.mPointers.add(new PointerState());
                NP++;
            }
            if (this.mActivePointerId < 0 || !this.mPointers.get(this.mActivePointerId).mCurDown) {
                this.mActivePointerId = id2;
            }
            PointerState ps4 = this.mPointers.get(id2);
            ps4.mCurDown = true;
            InputDevice device = InputDevice.getDevice(event.getDeviceId());
            ps4.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
        }
        int NP2 = NP;
        int NI = event.getPointerCount();
        this.mVelocity.addMovement(event);
        this.mVelocity.computeCurrentVelocity(1);
        if (this.mAltVelocity != null) {
            this.mAltVelocity.addMovement(event);
            this.mAltVelocity.computeCurrentVelocity(1);
        }
        int N2 = event.getHistorySize();
        int historyPos2 = 0;
        while (true) {
            int historyPos3 = historyPos2;
            if (historyPos3 >= N2) {
                break;
            }
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < NI) {
                    int id3 = event.getPointerId(i3);
                    PointerState ps5 = this.mCurDown ? this.mPointers.get(id3) : null;
                    if (ps5 != null) {
                        pointerCoords2 = ps5.mCoords;
                    } else {
                        pointerCoords2 = this.mTempCoords;
                    }
                    MotionEvent.PointerCoords coords3 = pointerCoords2;
                    event.getHistoricalPointerCoords(i3, historyPos3, coords3);
                    if (this.mPrintCoords) {
                        coords2 = coords3;
                        ps2 = ps5;
                        i = i3;
                        historyPos = historyPos3;
                        N = N2;
                        logCoords(TAG, action, i3, coords2, id3, event);
                    } else {
                        coords2 = coords3;
                        ps2 = ps5;
                        i = i3;
                        historyPos = historyPos3;
                        N = N2;
                    }
                    if (ps2 != null) {
                        MotionEvent.PointerCoords coords4 = coords2;
                        ps2.addTrace(coords4.f2401x, coords4.f2402y, false);
                    }
                    i2 = i + 1;
                    historyPos3 = historyPos;
                    N2 = N;
                }
            }
            historyPos2 = historyPos3 + 1;
        }
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= NI) {
                break;
            }
            int id4 = event.getPointerId(i5);
            PointerState ps6 = this.mCurDown ? this.mPointers.get(id4) : null;
            if (ps6 != null) {
                pointerCoords = ps6.mCoords;
            } else {
                pointerCoords = this.mTempCoords;
            }
            MotionEvent.PointerCoords coords5 = pointerCoords;
            event.getPointerCoords(i5, coords5);
            if (this.mPrintCoords) {
                coords = coords5;
                ps = ps6;
                id = id4;
                logCoords(TAG, action, i5, coords5, id4, event);
            } else {
                coords = coords5;
                ps = ps6;
                id = id4;
            }
            if (ps != null) {
                MotionEvent.PointerCoords coords6 = coords;
                ps.addTrace(coords6.f2401x, coords6.f2402y, true);
                ps.mXVelocity = this.mVelocity.getXVelocity(id);
                ps.mYVelocity = this.mVelocity.getYVelocity(id);
                this.mVelocity.getEstimator(id, ps.mEstimator);
                if (this.mAltVelocity != null) {
                    ps.mAltXVelocity = this.mAltVelocity.getXVelocity(id);
                    ps.mAltYVelocity = this.mAltVelocity.getYVelocity(id);
                    this.mAltVelocity.getEstimator(id, ps.mAltEstimator);
                }
                ps.mToolType = event.getToolType(i5);
                if (ps.mHasBoundingBox) {
                    c = ' ';
                    ps.mBoundingLeft = event.getAxisValue(32, i5);
                    ps.mBoundingTop = event.getAxisValue(33, i5);
                    ps.mBoundingRight = event.getAxisValue(34, i5);
                    ps.mBoundingBottom = event.getAxisValue(35, i5);
                    i4 = i5 + 1;
                }
            }
            c = ' ';
            i4 = i5 + 1;
        }
        if (action == 1 || action == 3 || (action & 255) == 6) {
            int index2 = (65280 & action) >> 8;
            int id5 = event.getPointerId(index2);
            if (id5 >= NP2) {
                Slog.wtf(TAG, "Got pointer ID out of bounds: id=" + id5 + " arraysize=" + NP2 + " pointerindex=" + index2 + " action=0x" + Integer.toHexString(action));
                return;
            }
            PointerState ps7 = this.mPointers.get(id5);
            ps7.mCurDown = false;
            if (action == 1 || action == 3) {
                this.mCurDown = false;
                this.mCurNumPointers = 0;
            } else {
                this.mCurNumPointers--;
                if (this.mActivePointerId == id5) {
                    this.mActivePointerId = event.getPointerId(index2 != 0 ? 0 : 1);
                }
                ps7.addTrace(Float.NaN, Float.NaN, false);
            }
        }
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        onPointerEvent(event);
        if (event.getAction() == 0 && !isFocused()) {
            requestFocus();
            return true;
        }
        return true;
    }

    @Override // android.view.View
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

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (shouldLogKey(keyCode)) {
            int repeatCount = event.getRepeatCount();
            if (repeatCount == 0) {
                Log.m68i(TAG, "Key Down: " + event);
                return true;
            }
            Log.m68i(TAG, "Key Repeat #" + repeatCount + PluralRules.KEYWORD_RULE_SEPARATOR + event);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (shouldLogKey(keyCode)) {
            Log.m68i(TAG, "Key Up: " + event);
            return true;
        }
        return super.onKeyUp(keyCode, event);
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

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent event) {
        logMotionEvent("Trackball", event);
        return true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
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

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIm.unregisterInputDeviceListener(this);
        try {
            WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int deviceId) {
        logInputDeviceState(deviceId, "Device Added");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int deviceId) {
        logInputDeviceState(deviceId, "Device Changed");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int deviceId) {
        logInputDeviceState(deviceId, "Device Removed");
    }

    private void logInputDevices() {
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int i : deviceIds) {
            logInputDeviceState(i, "Device Enumerated");
        }
    }

    private void logInputDeviceState(int deviceId, String state) {
        InputDevice device = this.mIm.getInputDevice(deviceId);
        if (device != null) {
            Log.m68i(TAG, state + PluralRules.KEYWORD_RULE_SEPARATOR + device);
            return;
        }
        Log.m68i(TAG, state + PluralRules.KEYWORD_RULE_SEPARATOR + deviceId);
    }

    private static boolean shouldShowSystemGestureExclusion() {
        return SystemProperties.getBoolean("debug.pointerlocation.showexclusion", false);
    }

    /* loaded from: classes4.dex */
    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public FasterStringBuilder append(String value) {
            int valueLength = value.length();
            int index = reserve(valueLength);
            value.getChars(0, valueLength, this.mChars, index);
            this.mLength += valueLength;
            return this;
        }

        public FasterStringBuilder append(int value) {
            return append(value, 0);
        }

        public FasterStringBuilder append(int value, int zeroPadWidth) {
            int index;
            boolean negative = value < 0;
            if (negative && (value = -value) < 0) {
                append("-2147483648");
                return this;
            }
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
                if (divisor != 0) {
                    index3 = index4;
                } else {
                    this.mLength = index4;
                    return this;
                }
            }
        }

        public FasterStringBuilder append(float value, int precision) {
            int scale = 1;
            for (int i = 0; i < precision; i++) {
                scale *= 10;
            }
            float value2 = (float) (Math.rint(scale * value) / scale);
            if (((int) value2) == 0 && value2 < 0.0f) {
                append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append((int) value2);
            if (precision != 0) {
                append(".");
                float value3 = Math.abs(value2);
                append((int) (scale * ((float) (value3 - Math.floor(value3)))), precision);
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
                int newCapacity = oldCapacity * 2;
                char[] newChars = new char[newCapacity];
                System.arraycopy(oldChars, 0, newChars, 0, oldLength);
                this.mChars = newChars;
            }
            return oldLength;
        }
    }

    /* renamed from: com.android.internal.widget.PointerLocationView$1 */
    /* loaded from: classes4.dex */
    class BinderC33761 extends ISystemGestureExclusionListener.Stub {
        BinderC33761() {
        }

        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int displayId, Region systemGestureExclusion) {
            final Region exclusion = Region.obtain(systemGestureExclusion);
            Handler handler = PointerLocationView.this.getHandler();
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.android.internal.widget.-$$Lambda$PointerLocationView$1$utsjc18145VWAe5S9LSLblHeqxc
                    @Override // java.lang.Runnable
                    public final void run() {
                        PointerLocationView.BinderC33761.lambda$onSystemGestureExclusionChanged$0(PointerLocationView.BinderC33761.this, exclusion);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$onSystemGestureExclusionChanged$0(BinderC33761 binderC33761, Region exclusion) {
            PointerLocationView.this.mSystemGestureExclusion.set(exclusion);
            exclusion.recycle();
            PointerLocationView.this.invalidate();
        }
    }
}
