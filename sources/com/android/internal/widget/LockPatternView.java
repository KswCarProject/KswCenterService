package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.internal.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LockPatternView extends View {
    private static final int ASPECT_LOCK_HEIGHT = 2;
    private static final int ASPECT_LOCK_WIDTH = 1;
    private static final int ASPECT_SQUARE = 0;
    public static final boolean DEBUG_A11Y = false;
    private static final float DRAG_THRESHHOLD = 0.0f;
    private static final float LINE_FADE_ALPHA_MULTIPLIER = 1.5f;
    private static final int MILLIS_PER_CIRCLE_ANIMATING = 700;
    private static final boolean PROFILE_DRAWING = false;
    private static final String TAG = "LockPatternView";
    public static final int VIRTUAL_BASE_VIEW_ID = 1;
    private long mAnimatingPeriodStart;
    private int mAspect;
    private AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public final CellState[][] mCellStates;
    private final Path mCurrentPath;
    /* access modifiers changed from: private */
    public final int mDotSize;
    /* access modifiers changed from: private */
    public final int mDotSizeActivated;
    private boolean mDrawingProfilingStarted;
    private boolean mEnableHapticFeedback;
    private int mErrorColor;
    private PatternExploreByTouchHelper mExploreByTouchHelper;
    private boolean mFadePattern;
    /* access modifiers changed from: private */
    public final Interpolator mFastOutSlowInInterpolator;
    /* access modifiers changed from: private */
    public float mHitFactor;
    private float mInProgressX;
    private float mInProgressY;
    @UnsupportedAppUsage
    private boolean mInStealthMode;
    private boolean mInputEnabled;
    private final Rect mInvalidate;
    private long[] mLineFadeStart;
    private final Interpolator mLinearOutSlowInInterpolator;
    private Drawable mNotSelectedDrawable;
    private OnPatternListener mOnPatternListener;
    @UnsupportedAppUsage
    private final Paint mPaint;
    @UnsupportedAppUsage
    private final Paint mPathPaint;
    private final int mPathWidth;
    @UnsupportedAppUsage
    private final ArrayList<Cell> mPattern;
    @UnsupportedAppUsage
    private DisplayMode mPatternDisplayMode;
    /* access modifiers changed from: private */
    public final boolean[][] mPatternDrawLookup;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public boolean mPatternInProgress;
    private int mRegularColor;
    private Drawable mSelectedDrawable;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public float mSquareHeight;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public float mSquareWidth;
    private int mSuccessColor;
    private final Rect mTmpInvalidateRect;
    private boolean mUseLockPatternDrawable;

    public static class CellState {
        float alpha = 1.0f;
        int col;
        boolean hwAnimating;
        CanvasProperty<Float> hwCenterX;
        CanvasProperty<Float> hwCenterY;
        CanvasProperty<Paint> hwPaint;
        CanvasProperty<Float> hwRadius;
        public ValueAnimator lineAnimator;
        public float lineEndX = Float.MIN_VALUE;
        public float lineEndY = Float.MIN_VALUE;
        float radius;
        int row;
        float translationY;
    }

    public enum DisplayMode {
        Correct,
        Animate,
        Wrong
    }

    public interface OnPatternListener {
        void onPatternCellAdded(List<Cell> list);

        void onPatternCleared();

        void onPatternDetected(List<Cell> list);

        void onPatternStart();
    }

    public static final class Cell {
        private static final Cell[][] sCells = createCells();
        @UnsupportedAppUsage
        final int column;
        @UnsupportedAppUsage
        final int row;

        private static Cell[][] createCells() {
            Cell[][] res = (Cell[][]) Array.newInstance(Cell.class, new int[]{3, 3});
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    res[i][j] = new Cell(i, j);
                }
            }
            return res;
        }

        private Cell(int row2, int column2) {
            checkRange(row2, column2);
            this.row = row2;
            this.column = column2;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public static Cell of(int row2, int column2) {
            checkRange(row2, column2);
            return sCells[row2][column2];
        }

        private static void checkRange(int row2, int column2) {
            if (row2 < 0 || row2 > 2) {
                throw new IllegalArgumentException("row must be in range 0-2");
            } else if (column2 < 0 || column2 > 2) {
                throw new IllegalArgumentException("column must be in range 0-2");
            }
        }

        public String toString() {
            return "(row=" + this.row + ",clmn=" + this.column + ")";
        }
    }

    public LockPatternView(Context context) {
        this(context, (AttributeSet) null);
    }

    @UnsupportedAppUsage
    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDrawingProfilingStarted = false;
        this.mPaint = new Paint();
        this.mPathPaint = new Paint();
        this.mPattern = new ArrayList<>(9);
        this.mPatternDrawLookup = (boolean[][]) Array.newInstance(boolean.class, new int[]{3, 3});
        this.mInProgressX = -1.0f;
        this.mInProgressY = -1.0f;
        this.mLineFadeStart = new long[9];
        this.mPatternDisplayMode = DisplayMode.Correct;
        this.mInputEnabled = true;
        this.mInStealthMode = false;
        this.mEnableHapticFeedback = true;
        this.mPatternInProgress = false;
        this.mFadePattern = true;
        this.mHitFactor = 0.6f;
        this.mCurrentPath = new Path();
        this.mInvalidate = new Rect();
        this.mTmpInvalidateRect = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LockPatternView, R.attr.lockPatternStyle, R.style.Widget_LockPatternView);
        String aspect = a.getString(0);
        if ("square".equals(aspect)) {
            this.mAspect = 0;
        } else if ("lock_width".equals(aspect)) {
            this.mAspect = 1;
        } else if ("lock_height".equals(aspect)) {
            this.mAspect = 2;
        } else {
            this.mAspect = 0;
        }
        setClickable(true);
        this.mPathPaint.setAntiAlias(true);
        this.mPathPaint.setDither(true);
        this.mRegularColor = a.getColor(3, 0);
        this.mErrorColor = a.getColor(1, 0);
        this.mSuccessColor = a.getColor(4, 0);
        this.mPathPaint.setColor(a.getColor(2, this.mRegularColor));
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPathWidth = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_line_width);
        this.mPathPaint.setStrokeWidth((float) this.mPathWidth);
        this.mDotSize = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size);
        this.mDotSizeActivated = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size_activated);
        this.mUseLockPatternDrawable = getResources().getBoolean(R.bool.use_lock_pattern_drawable);
        if (this.mUseLockPatternDrawable) {
            this.mSelectedDrawable = getResources().getDrawable(R.drawable.lockscreen_selected);
            this.mNotSelectedDrawable = getResources().getDrawable(R.drawable.lockscreen_notselected);
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mCellStates = (CellState[][]) Array.newInstance(CellState.class, new int[]{3, 3});
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mCellStates[i][j] = new CellState();
                this.mCellStates[i][j].radius = (float) (this.mDotSize / 2);
                this.mCellStates[i][j].row = i;
                this.mCellStates[i][j].col = j;
            }
        }
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563661);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mExploreByTouchHelper = new PatternExploreByTouchHelper(this);
        setAccessibilityDelegate(this.mExploreByTouchHelper);
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        a.recycle();
    }

    @UnsupportedAppUsage
    public CellState[][] getCellStates() {
        return this.mCellStates;
    }

    public boolean isInStealthMode() {
        return this.mInStealthMode;
    }

    public boolean isTactileFeedbackEnabled() {
        return this.mEnableHapticFeedback;
    }

    @UnsupportedAppUsage
    public void setInStealthMode(boolean inStealthMode) {
        this.mInStealthMode = inStealthMode;
    }

    public void setFadePattern(boolean fadePattern) {
        this.mFadePattern = fadePattern;
    }

    @UnsupportedAppUsage
    public void setTactileFeedbackEnabled(boolean tactileFeedbackEnabled) {
        this.mEnableHapticFeedback = tactileFeedbackEnabled;
    }

    @UnsupportedAppUsage
    public void setOnPatternListener(OnPatternListener onPatternListener) {
        this.mOnPatternListener = onPatternListener;
    }

    public void setPattern(DisplayMode displayMode, List<Cell> pattern) {
        this.mPattern.clear();
        this.mPattern.addAll(pattern);
        clearPatternDrawLookup();
        for (Cell cell : pattern) {
            this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        }
        setDisplayMode(displayMode);
    }

    @UnsupportedAppUsage
    public void setDisplayMode(DisplayMode displayMode) {
        this.mPatternDisplayMode = displayMode;
        if (displayMode == DisplayMode.Animate) {
            if (this.mPattern.size() != 0) {
                this.mAnimatingPeriodStart = SystemClock.elapsedRealtime();
                Cell first = this.mPattern.get(0);
                this.mInProgressX = getCenterXForColumn(first.getColumn());
                this.mInProgressY = getCenterYForRow(first.getRow());
                clearPatternDrawLookup();
            } else {
                throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
        }
        invalidate();
    }

    public void startCellStateAnimation(CellState cellState, float startAlpha, float endAlpha, float startTranslationY, float endTranslationY, float startScale, float endScale, long delay, long duration, Interpolator interpolator, Runnable finishRunnable) {
        if (isHardwareAccelerated()) {
            startCellStateAnimationHw(cellState, startAlpha, endAlpha, startTranslationY, endTranslationY, startScale, endScale, delay, duration, interpolator, finishRunnable);
        } else {
            startCellStateAnimationSw(cellState, startAlpha, endAlpha, startTranslationY, endTranslationY, startScale, endScale, delay, duration, interpolator, finishRunnable);
        }
    }

    private void startCellStateAnimationSw(CellState cellState, float startAlpha, float endAlpha, float startTranslationY, float endTranslationY, float startScale, float endScale, long delay, long duration, Interpolator interpolator, Runnable finishRunnable) {
        CellState cellState2 = cellState;
        cellState2.alpha = startAlpha;
        cellState2.translationY = startTranslationY;
        cellState2.radius = ((float) (this.mDotSize / 2)) * startScale;
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        final CellState cellState3 = cellState;
        final float f = startAlpha;
        AnonymousClass1 r10 = r0;
        final float f2 = endAlpha;
        final float f3 = startTranslationY;
        final float f4 = endTranslationY;
        final float f5 = startScale;
        final float f6 = endScale;
        AnonymousClass1 r0 = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = ((Float) animation.getAnimatedValue()).floatValue();
                cellState3.alpha = ((1.0f - t) * f) + (f2 * t);
                cellState3.translationY = ((1.0f - t) * f3) + (f4 * t);
                cellState3.radius = ((float) (LockPatternView.this.mDotSize / 2)) * (((1.0f - t) * f5) + (f6 * t));
                LockPatternView.this.invalidate();
            }
        };
        animator.addUpdateListener(r10);
        final Runnable runnable = finishRunnable;
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        animator.start();
    }

    private void startCellStateAnimationHw(final CellState cellState, float startAlpha, float endAlpha, float startTranslationY, float endTranslationY, float startScale, float endScale, long delay, long duration, Interpolator interpolator, Runnable finishRunnable) {
        CellState cellState2 = cellState;
        float f = endTranslationY;
        cellState2.alpha = endAlpha;
        cellState2.translationY = f;
        cellState2.radius = ((float) (this.mDotSize / 2)) * endScale;
        cellState2.hwAnimating = true;
        cellState2.hwCenterY = CanvasProperty.createFloat(getCenterYForRow(cellState2.row) + startTranslationY);
        cellState2.hwCenterX = CanvasProperty.createFloat(getCenterXForColumn(cellState2.col));
        cellState2.hwRadius = CanvasProperty.createFloat(((float) (this.mDotSize / 2)) * startScale);
        this.mPaint.setColor(getCurrentColor(false));
        this.mPaint.setAlpha((int) (255.0f * startAlpha));
        cellState2.hwPaint = CanvasProperty.createPaint(new Paint(this.mPaint));
        long j = delay;
        long j2 = duration;
        Interpolator interpolator2 = interpolator;
        startRtFloatAnimation(cellState2.hwCenterY, getCenterYForRow(cellState2.row) + f, j, j2, interpolator2);
        startRtFloatAnimation(cellState2.hwRadius, ((float) (this.mDotSize / 2)) * endScale, j, j2, interpolator2);
        final Runnable runnable = finishRunnable;
        startRtAlphaAnimation(cellState, endAlpha, j, j2, interpolator, new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                cellState.hwAnimating = false;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        invalidate();
    }

    private void startRtAlphaAnimation(CellState cellState, float endAlpha, long delay, long duration, Interpolator interpolator, Animator.AnimatorListener listener) {
        RenderNodeAnimator animator = new RenderNodeAnimator(cellState.hwPaint, 1, (float) ((int) (255.0f * endAlpha)));
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        animator.setTarget((View) this);
        animator.addListener(listener);
        animator.start();
    }

    private void startRtFloatAnimation(CanvasProperty<Float> property, float endValue, long delay, long duration, Interpolator interpolator) {
        RenderNodeAnimator animator = new RenderNodeAnimator(property, endValue);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        animator.setTarget((View) this);
        animator.start();
    }

    private void notifyCellAdded() {
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCellAdded(this.mPattern);
        }
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void notifyPatternStarted() {
        sendAccessEvent(R.string.lockscreen_access_pattern_start);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternStart();
        }
    }

    @UnsupportedAppUsage
    private void notifyPatternDetected() {
        sendAccessEvent(R.string.lockscreen_access_pattern_detected);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternDetected(this.mPattern);
        }
    }

    private void notifyPatternCleared() {
        sendAccessEvent(R.string.lockscreen_access_pattern_cleared);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCleared();
        }
    }

    @UnsupportedAppUsage
    public void clearPattern() {
        resetPattern();
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event) | this.mExploreByTouchHelper.dispatchHoverEvent(event);
    }

    private void resetPattern() {
        this.mPattern.clear();
        clearPatternDrawLookup();
        this.mPatternDisplayMode = DisplayMode.Correct;
        invalidate();
    }

    private void clearPatternDrawLookup() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mPatternDrawLookup[i][j] = false;
                this.mLineFadeStart[(j * 3) + i] = 0;
            }
        }
    }

    @UnsupportedAppUsage
    public void disableInput() {
        this.mInputEnabled = false;
    }

    @UnsupportedAppUsage
    public void enableInput() {
        this.mInputEnabled = true;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = (w - this.mPaddingLeft) - this.mPaddingRight;
        this.mSquareWidth = ((float) width) / 3.0f;
        int height = (h - this.mPaddingTop) - this.mPaddingBottom;
        this.mSquareHeight = ((float) height) / 3.0f;
        this.mExploreByTouchHelper.invalidateRoot();
        if (this.mUseLockPatternDrawable) {
            this.mNotSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, width, height);
            this.mSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, width, height);
        }
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int mode = View.MeasureSpec.getMode(measureSpec);
        if (mode == Integer.MIN_VALUE) {
            return Math.max(specSize, desired);
        }
        if (mode != 0) {
            return specSize;
        }
        return desired;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int viewWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
        int viewHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
        switch (this.mAspect) {
            case 0:
                int min = Math.min(viewWidth, viewHeight);
                viewHeight = min;
                viewWidth = min;
                break;
            case 1:
                viewHeight = Math.min(viewWidth, viewHeight);
                break;
            case 2:
                viewWidth = Math.min(viewWidth, viewHeight);
                break;
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    private Cell detectAndAddHit(float x, float y) {
        Cell cell = checkForNewHit(x, y);
        if (cell == null) {
            return null;
        }
        Cell fillInGapCell = null;
        ArrayList<Cell> pattern = this.mPattern;
        if (!pattern.isEmpty()) {
            Cell lastCell = pattern.get(pattern.size() - 1);
            int dRow = cell.row - lastCell.row;
            int dColumn = cell.column - lastCell.column;
            int fillInRow = lastCell.row;
            int fillInColumn = lastCell.column;
            int i = -1;
            if (Math.abs(dRow) == 2 && Math.abs(dColumn) != 1) {
                fillInRow = lastCell.row + (dRow > 0 ? 1 : -1);
            }
            if (Math.abs(dColumn) == 2 && Math.abs(dRow) != 1) {
                int i2 = lastCell.column;
                if (dColumn > 0) {
                    i = 1;
                }
                fillInColumn = i2 + i;
            }
            fillInGapCell = Cell.of(fillInRow, fillInColumn);
        }
        if (fillInGapCell != null && !this.mPatternDrawLookup[fillInGapCell.row][fillInGapCell.column]) {
            addCellToPattern(fillInGapCell);
        }
        addCellToPattern(cell);
        if (this.mEnableHapticFeedback) {
            performHapticFeedback(1, 3);
        }
        return cell;
    }

    private void addCellToPattern(Cell newCell) {
        this.mPatternDrawLookup[newCell.getRow()][newCell.getColumn()] = true;
        this.mPattern.add(newCell);
        if (!this.mInStealthMode) {
            startCellActivatedAnimation(newCell);
        }
        notifyCellAdded();
    }

    private void startCellActivatedAnimation(Cell cell) {
        final CellState cellState = this.mCellStates[cell.row][cell.column];
        startRadiusAnimation((float) (this.mDotSize / 2), (float) (this.mDotSizeActivated / 2), 96, this.mLinearOutSlowInInterpolator, cellState, new Runnable() {
            public void run() {
                LockPatternView.this.startRadiusAnimation((float) (LockPatternView.this.mDotSizeActivated / 2), (float) (LockPatternView.this.mDotSize / 2), 192, LockPatternView.this.mFastOutSlowInInterpolator, cellState, (Runnable) null);
            }
        });
        startLineEndAnimation(cellState, this.mInProgressX, this.mInProgressY, getCenterXForColumn(cell.column), getCenterYForRow(cell.row));
    }

    private void startLineEndAnimation(final CellState state, float startX, float startY, float targetX, float targetY) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        final CellState cellState = state;
        final float f = startX;
        final float f2 = targetX;
        final float f3 = startY;
        final float f4 = targetY;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = ((Float) animation.getAnimatedValue()).floatValue();
                cellState.lineEndX = ((1.0f - t) * f) + (f2 * t);
                cellState.lineEndY = ((1.0f - t) * f3) + (f4 * t);
                LockPatternView.this.invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                state.lineAnimator = null;
            }
        });
        valueAnimator.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimator.setDuration(100);
        valueAnimator.start();
        state.lineAnimator = valueAnimator;
    }

    /* access modifiers changed from: private */
    public void startRadiusAnimation(float start, float end, long duration, Interpolator interpolator, final CellState state, final Runnable endRunnable) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                state.radius = ((Float) animation.getAnimatedValue()).floatValue();
                LockPatternView.this.invalidate();
            }
        });
        if (endRunnable != null) {
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    endRunnable.run();
                }
            });
        }
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    private Cell checkForNewHit(float x, float y) {
        int columnHit;
        int rowHit = getRowHit(y);
        if (rowHit >= 0 && (columnHit = getColumnHit(x)) >= 0 && !this.mPatternDrawLookup[rowHit][columnHit]) {
            return Cell.of(rowHit, columnHit);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public int getRowHit(float y) {
        float squareHeight = this.mSquareHeight;
        float hitSize = this.mHitFactor * squareHeight;
        float offset = ((float) this.mPaddingTop) + ((squareHeight - hitSize) / 2.0f);
        for (int i = 0; i < 3; i++) {
            float hitTop = (((float) i) * squareHeight) + offset;
            if (y >= hitTop && y <= hitTop + hitSize) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public int getColumnHit(float x) {
        float squareWidth = this.mSquareWidth;
        float hitSize = this.mHitFactor * squareWidth;
        float offset = ((float) this.mPaddingLeft) + ((squareWidth - hitSize) / 2.0f);
        for (int i = 0; i < 3; i++) {
            float hitLeft = (((float) i) * squareWidth) + offset;
            if (x >= hitLeft && x <= hitLeft + hitSize) {
                return i;
            }
        }
        return -1;
    }

    public boolean onHoverEvent(MotionEvent event) {
        if (AccessibilityManager.getInstance(this.mContext).isTouchExplorationEnabled()) {
            int action = event.getAction();
            if (action != 7) {
                switch (action) {
                    case 9:
                        event.setAction(0);
                        break;
                    case 10:
                        event.setAction(1);
                        break;
                }
            } else {
                event.setAction(2);
            }
            onTouchEvent(event);
            event.setAction(action);
        }
        return super.onHoverEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mInputEnabled || !isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                handleActionDown(event);
                return true;
            case 1:
                handleActionUp();
                return true;
            case 2:
                handleActionMove(event);
                return true;
            case 3:
                if (this.mPatternInProgress) {
                    setPatternInProgress(false);
                    resetPattern();
                    notifyPatternCleared();
                }
                return true;
            default:
                return false;
        }
    }

    private void setPatternInProgress(boolean progress) {
        this.mPatternInProgress = progress;
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void handleActionMove(MotionEvent event) {
        float radius;
        boolean invalidateNow;
        int historySize;
        MotionEvent motionEvent = event;
        float radius2 = (float) this.mPathWidth;
        int historySize2 = event.getHistorySize();
        this.mTmpInvalidateRect.setEmpty();
        boolean invalidateNow2 = false;
        int i = 0;
        while (i < historySize2 + 1) {
            float x = i < historySize2 ? motionEvent.getHistoricalX(i) : event.getX();
            float y = i < historySize2 ? motionEvent.getHistoricalY(i) : event.getY();
            Cell hitCell = detectAndAddHit(x, y);
            int patternSize = this.mPattern.size();
            if (hitCell != null && patternSize == 1) {
                setPatternInProgress(true);
                notifyPatternStarted();
            }
            float dx = Math.abs(x - this.mInProgressX);
            float dy = Math.abs(y - this.mInProgressY);
            if (dx > 0.0f || dy > 0.0f) {
                invalidateNow2 = true;
            }
            if (!this.mPatternInProgress || patternSize <= 0) {
                radius = radius2;
                historySize = historySize2;
                invalidateNow = invalidateNow2;
            } else {
                Cell lastCell = this.mPattern.get(patternSize - 1);
                float lastCellCenterX = getCenterXForColumn(lastCell.column);
                float lastCellCenterY = getCenterYForRow(lastCell.row);
                float left = Math.min(lastCellCenterX, x) - radius2;
                historySize = historySize2;
                float right = Math.max(lastCellCenterX, x) + radius2;
                invalidateNow = invalidateNow2;
                float top = Math.min(lastCellCenterY, y) - radius2;
                float f = x;
                float bottom = Math.max(lastCellCenterY, y) + radius2;
                if (hitCell != null) {
                    radius = radius2;
                    float width = this.mSquareWidth * 0.5f;
                    float f2 = y;
                    float height = this.mSquareHeight * 0.5f;
                    int i2 = patternSize;
                    float hitCellCenterX = getCenterXForColumn(hitCell.column);
                    float f3 = dx;
                    float hitCellCenterY = getCenterYForRow(hitCell.row);
                    Cell cell = hitCell;
                    left = Math.min(hitCellCenterX - width, left);
                    right = Math.max(hitCellCenterX + width, right);
                    top = Math.min(hitCellCenterY - height, top);
                    bottom = Math.max(hitCellCenterY + height, bottom);
                } else {
                    radius = radius2;
                    float f4 = y;
                    Cell cell2 = hitCell;
                    int i3 = patternSize;
                    float f5 = dx;
                }
                this.mTmpInvalidateRect.union(Math.round(left), Math.round(top), Math.round(right), Math.round(bottom));
            }
            i++;
            historySize2 = historySize;
            invalidateNow2 = invalidateNow;
            radius2 = radius;
            motionEvent = event;
        }
        int i4 = historySize2;
        this.mInProgressX = event.getX();
        this.mInProgressY = event.getY();
        if (invalidateNow2) {
            this.mInvalidate.union(this.mTmpInvalidateRect);
            invalidate(this.mInvalidate);
            this.mInvalidate.set(this.mTmpInvalidateRect);
        }
    }

    private void sendAccessEvent(int resId) {
        announceForAccessibility(this.mContext.getString(resId));
    }

    private void handleActionUp() {
        if (!this.mPattern.isEmpty()) {
            setPatternInProgress(false);
            cancelLineAnimations();
            notifyPatternDetected();
            if (this.mFadePattern) {
                clearPatternDrawLookup();
                this.mPatternDisplayMode = DisplayMode.Correct;
            }
            invalidate();
        }
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                CellState state = this.mCellStates[i][j];
                if (state.lineAnimator != null) {
                    state.lineAnimator.cancel();
                    state.lineEndX = Float.MIN_VALUE;
                    state.lineEndY = Float.MIN_VALUE;
                }
            }
        }
    }

    private void handleActionDown(MotionEvent event) {
        resetPattern();
        float x = event.getX();
        float y = event.getY();
        Cell hitCell = detectAndAddHit(x, y);
        if (hitCell != null) {
            setPatternInProgress(true);
            this.mPatternDisplayMode = DisplayMode.Correct;
            notifyPatternStarted();
        } else if (this.mPatternInProgress) {
            setPatternInProgress(false);
            notifyPatternCleared();
        }
        if (hitCell != null) {
            float startX = getCenterXForColumn(hitCell.column);
            float startY = getCenterYForRow(hitCell.row);
            float widthOffset = this.mSquareWidth / 2.0f;
            float heightOffset = this.mSquareHeight / 2.0f;
            invalidate((int) (startX - widthOffset), (int) (startY - heightOffset), (int) (startX + widthOffset), (int) (startY + heightOffset));
        }
        this.mInProgressX = x;
        this.mInProgressY = y;
    }

    /* access modifiers changed from: private */
    public float getCenterXForColumn(int column) {
        return ((float) this.mPaddingLeft) + (((float) column) * this.mSquareWidth) + (this.mSquareWidth / 2.0f);
    }

    /* access modifiers changed from: private */
    public float getCenterYForRow(int row) {
        return ((float) this.mPaddingTop) + (((float) row) * this.mSquareHeight) + (this.mSquareHeight / 2.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Path currentPath;
        long elapsedRealtime;
        boolean anyCircles;
        boolean drawPath;
        Path currentPath2;
        Path currentPath3;
        int j;
        Canvas canvas2 = canvas;
        ArrayList<Cell> pattern = this.mPattern;
        int count = pattern.size();
        boolean[][] drawLookup = this.mPatternDrawLookup;
        if (this.mPatternDisplayMode == DisplayMode.Animate) {
            int oneCycle = (count + 1) * 700;
            int spotInCycle = ((int) (SystemClock.elapsedRealtime() - this.mAnimatingPeriodStart)) % oneCycle;
            int numCircles = spotInCycle / 700;
            clearPatternDrawLookup();
            for (int i = 0; i < numCircles; i++) {
                Cell cell = pattern.get(i);
                drawLookup[cell.getRow()][cell.getColumn()] = true;
            }
            if (numCircles > 0 && numCircles < count) {
                float percentageOfNextCircle = ((float) (spotInCycle % 700)) / 700.0f;
                Cell currentCell = pattern.get(numCircles - 1);
                float centerX = getCenterXForColumn(currentCell.column);
                float centerY = getCenterYForRow(currentCell.row);
                Cell nextCell = pattern.get(numCircles);
                int i2 = oneCycle;
                this.mInProgressX = centerX + ((getCenterXForColumn(nextCell.column) - centerX) * percentageOfNextCircle);
                this.mInProgressY = centerY + ((getCenterYForRow(nextCell.row) - centerY) * percentageOfNextCircle);
            }
            invalidate();
        }
        Path currentPath4 = this.mCurrentPath;
        currentPath4.rewind();
        int i3 = 0;
        while (true) {
            int i4 = i3;
            int i5 = 3;
            if (i4 >= 3) {
                break;
            }
            float centerY2 = getCenterYForRow(i4);
            int j2 = 0;
            while (true) {
                int j3 = j2;
                if (j3 >= i5) {
                    break;
                }
                CellState cellState = this.mCellStates[i4][j3];
                float centerX2 = getCenterXForColumn(j3);
                float translationY = cellState.translationY;
                if (this.mUseLockPatternDrawable) {
                    float f = translationY;
                    float f2 = centerX2;
                    currentPath3 = currentPath4;
                    CellState cellState2 = cellState;
                    drawCellDrawable(canvas, i4, j3, cellState.radius, drawLookup[i4][j3]);
                } else {
                    float translationY2 = translationY;
                    float centerX3 = centerX2;
                    currentPath3 = currentPath4;
                    CellState cellState3 = cellState;
                    if (!isHardwareAccelerated() || !cellState3.hwAnimating) {
                        j = j3;
                        drawCircle(canvas, (float) ((int) centerX3), ((float) ((int) centerY2)) + translationY2, cellState3.radius, drawLookup[i4][j3], cellState3.alpha);
                        j2 = j + 1;
                        currentPath4 = currentPath3;
                        i5 = 3;
                    } else {
                        ((RecordingCanvas) canvas2).drawCircle(cellState3.hwCenterX, cellState3.hwCenterY, cellState3.hwRadius, cellState3.hwPaint);
                    }
                }
                j = j3;
                j2 = j + 1;
                currentPath4 = currentPath3;
                i5 = 3;
            }
            i3 = i4 + 1;
        }
        Path currentPath5 = currentPath4;
        boolean drawPath2 = !this.mInStealthMode;
        if (drawPath2) {
            this.mPathPaint.setColor(getCurrentColor(true));
            boolean anyCircles2 = false;
            float lastX = 0.0f;
            float lastY = 0.0f;
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            int i6 = 0;
            while (true) {
                int i7 = i6;
                if (i7 >= count) {
                    long j4 = elapsedRealtime2;
                    currentPath = currentPath5;
                    break;
                }
                Cell cell2 = pattern.get(i7);
                if (!drawLookup[cell2.row][cell2.column]) {
                    boolean z = drawPath2;
                    long j5 = elapsedRealtime2;
                    currentPath = currentPath5;
                    break;
                }
                if (this.mLineFadeStart[i7] == 0) {
                    this.mLineFadeStart[i7] = SystemClock.elapsedRealtime();
                }
                float centerX4 = getCenterXForColumn(cell2.column);
                float centerY3 = getCenterYForRow(cell2.row);
                if (i7 != 0) {
                    drawPath = drawPath2;
                    anyCircles = true;
                    int lineFadeVal = (int) Math.min(((float) (elapsedRealtime2 - this.mLineFadeStart[i7])) * LINE_FADE_ALPHA_MULTIPLIER, 255.0f);
                    CellState state = this.mCellStates[cell2.row][cell2.column];
                    currentPath5.rewind();
                    currentPath2 = currentPath5;
                    currentPath2.moveTo(lastX, lastY);
                    elapsedRealtime = elapsedRealtime2;
                    if (state.lineEndX == Float.MIN_VALUE || state.lineEndY == Float.MIN_VALUE) {
                        currentPath2.lineTo(centerX4, centerY3);
                        if (this.mFadePattern) {
                            this.mPathPaint.setAlpha(255 - lineFadeVal);
                        } else {
                            this.mPathPaint.setAlpha(255);
                        }
                    } else {
                        currentPath2.lineTo(state.lineEndX, state.lineEndY);
                        if (this.mFadePattern) {
                            this.mPathPaint.setAlpha(255 - lineFadeVal);
                        } else {
                            this.mPathPaint.setAlpha(255);
                        }
                    }
                    canvas2.drawPath(currentPath2, this.mPathPaint);
                } else {
                    drawPath = drawPath2;
                    anyCircles = true;
                    elapsedRealtime = elapsedRealtime2;
                    currentPath2 = currentPath5;
                }
                lastX = centerX4;
                lastY = centerY3;
                i6 = i7 + 1;
                currentPath5 = currentPath2;
                drawPath2 = drawPath;
                anyCircles2 = anyCircles;
                elapsedRealtime2 = elapsedRealtime;
            }
            if ((this.mPatternInProgress || this.mPatternDisplayMode == DisplayMode.Animate) && anyCircles2) {
                currentPath.rewind();
                currentPath.moveTo(lastX, lastY);
                currentPath.lineTo(this.mInProgressX, this.mInProgressY);
                this.mPathPaint.setAlpha((int) (calculateLastSegmentAlpha(this.mInProgressX, this.mInProgressY, lastX, lastY) * 255.0f));
                canvas2.drawPath(currentPath, this.mPathPaint);
                return;
            }
            return;
        }
        Path path = currentPath5;
    }

    private float calculateLastSegmentAlpha(float x, float y, float lastX, float lastY) {
        float diffX = x - lastX;
        float diffY = y - lastY;
        return Math.min(1.0f, Math.max(0.0f, ((((float) Math.sqrt((double) ((diffX * diffX) + (diffY * diffY)))) / this.mSquareWidth) - 0.3f) * 4.0f));
    }

    private int getCurrentColor(boolean partOfPattern) {
        if (!partOfPattern || this.mInStealthMode || this.mPatternInProgress) {
            return this.mRegularColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Correct || this.mPatternDisplayMode == DisplayMode.Animate) {
            return this.mSuccessColor;
        }
        throw new IllegalStateException("unknown display mode " + this.mPatternDisplayMode);
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float radius, boolean partOfPattern, float alpha) {
        this.mPaint.setColor(getCurrentColor(partOfPattern));
        this.mPaint.setAlpha((int) (255.0f * alpha));
        canvas.drawCircle(centerX, centerY, radius, this.mPaint);
    }

    private void drawCellDrawable(Canvas canvas, int i, int j, float radius, boolean partOfPattern) {
        Rect dst = new Rect((int) (((float) this.mPaddingLeft) + (((float) j) * this.mSquareWidth)), (int) (((float) this.mPaddingTop) + (((float) i) * this.mSquareHeight)), (int) (((float) this.mPaddingLeft) + (((float) (j + 1)) * this.mSquareWidth)), (int) (((float) this.mPaddingTop) + (((float) (i + 1)) * this.mSquareHeight)));
        float scale = radius / ((float) (this.mDotSize / 2));
        canvas.save();
        canvas.clipRect(dst);
        canvas.scale(scale, scale, (float) dst.centerX(), (float) dst.centerY());
        if (!partOfPattern || scale > 1.0f) {
            this.mNotSelectedDrawable.draw(canvas);
        } else {
            this.mSelectedDrawable.draw(canvas);
        }
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        byte[] patternBytes = LockPatternUtils.patternToByteArray(this.mPattern);
        return new SavedState(superState, patternBytes != null ? new String(patternBytes) : null, this.mPatternDisplayMode.ordinal(), this.mInputEnabled, this.mInStealthMode, this.mEnableHapticFeedback);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setPattern(DisplayMode.Correct, LockPatternUtils.stringToPattern(ss.getSerializedPattern()));
        this.mPatternDisplayMode = DisplayMode.values()[ss.getDisplayMode()];
        this.mInputEnabled = ss.isInputEnabled();
        this.mInStealthMode = ss.isInStealthMode();
        this.mEnableHapticFeedback = ss.isTactileFeedbackEnabled();
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mInputEnabled;
        private final String mSerializedPattern;
        private final boolean mTactileFeedbackEnabled;

        @UnsupportedAppUsage
        private SavedState(Parcelable superState, String serializedPattern, int displayMode, boolean inputEnabled, boolean inStealthMode, boolean tactileFeedbackEnabled) {
            super(superState);
            this.mSerializedPattern = serializedPattern;
            this.mDisplayMode = displayMode;
            this.mInputEnabled = inputEnabled;
            this.mInStealthMode = inStealthMode;
            this.mTactileFeedbackEnabled = tactileFeedbackEnabled;
        }

        @UnsupportedAppUsage
        private SavedState(Parcel in) {
            super(in);
            this.mSerializedPattern = in.readString();
            this.mDisplayMode = in.readInt();
            this.mInputEnabled = ((Boolean) in.readValue((ClassLoader) null)).booleanValue();
            this.mInStealthMode = ((Boolean) in.readValue((ClassLoader) null)).booleanValue();
            this.mTactileFeedbackEnabled = ((Boolean) in.readValue((ClassLoader) null)).booleanValue();
        }

        public String getSerializedPattern() {
            return this.mSerializedPattern;
        }

        public int getDisplayMode() {
            return this.mDisplayMode;
        }

        public boolean isInputEnabled() {
            return this.mInputEnabled;
        }

        public boolean isInStealthMode() {
            return this.mInStealthMode;
        }

        public boolean isTactileFeedbackEnabled() {
            return this.mTactileFeedbackEnabled;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.mSerializedPattern);
            dest.writeInt(this.mDisplayMode);
            dest.writeValue(Boolean.valueOf(this.mInputEnabled));
            dest.writeValue(Boolean.valueOf(this.mInStealthMode));
            dest.writeValue(Boolean.valueOf(this.mTactileFeedbackEnabled));
        }
    }

    private final class PatternExploreByTouchHelper extends ExploreByTouchHelper {
        private final SparseArray<VirtualViewContainer> mItems = new SparseArray<>();
        private Rect mTempRect = new Rect();

        class VirtualViewContainer {
            CharSequence description;

            public VirtualViewContainer(CharSequence description2) {
                this.description = description2;
            }
        }

        public PatternExploreByTouchHelper(View forView) {
            super(forView);
            for (int i = 1; i < 10; i++) {
                this.mItems.put(i, new VirtualViewContainer(getTextForVirtualView(i)));
            }
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float x, float y) {
            return getVirtualViewIdForHit(x, y);
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(IntArray virtualViewIds) {
            if (LockPatternView.this.mPatternInProgress) {
                for (int i = 1; i < 10; i++) {
                    virtualViewIds.add(i);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            VirtualViewContainer container = this.mItems.get(virtualViewId);
            if (container != null) {
                event.getText().add(container.description);
            }
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            if (!LockPatternView.this.mPatternInProgress) {
                event.setContentDescription(LockPatternView.this.getContext().getText(R.string.lockscreen_access_pattern_area));
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo node) {
            node.setText(getTextForVirtualView(virtualViewId));
            node.setContentDescription(getTextForVirtualView(virtualViewId));
            if (LockPatternView.this.mPatternInProgress) {
                node.setFocusable(true);
                if (isClickable(virtualViewId)) {
                    node.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    node.setClickable(isClickable(virtualViewId));
                }
            }
            node.setBoundsInParent(getBoundsForVirtualView(virtualViewId));
        }

        private boolean isClickable(int virtualViewId) {
            if (virtualViewId == Integer.MIN_VALUE) {
                return false;
            }
            return !LockPatternView.this.mPatternDrawLookup[(virtualViewId - 1) / 3][(virtualViewId - 1) % 3];
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action != 16) {
                return false;
            }
            return onItemClicked(virtualViewId);
        }

        /* access modifiers changed from: package-private */
        public boolean onItemClicked(int index) {
            invalidateVirtualView(index);
            sendEventForVirtualView(index, 1);
            return true;
        }

        private Rect getBoundsForVirtualView(int virtualViewId) {
            int ordinal = virtualViewId - 1;
            Rect bounds = this.mTempRect;
            int row = ordinal / 3;
            int col = ordinal % 3;
            CellState cellState = LockPatternView.this.mCellStates[row][col];
            float centerX = LockPatternView.this.getCenterXForColumn(col);
            float centerY = LockPatternView.this.getCenterYForRow(row);
            float cellheight = LockPatternView.this.mSquareHeight * LockPatternView.this.mHitFactor * 0.5f;
            float cellwidth = LockPatternView.this.mSquareWidth * LockPatternView.this.mHitFactor * 0.5f;
            bounds.left = (int) (centerX - cellwidth);
            bounds.right = (int) (centerX + cellwidth);
            bounds.top = (int) (centerY - cellheight);
            bounds.bottom = (int) (centerY + cellheight);
            return bounds;
        }

        private CharSequence getTextForVirtualView(int virtualViewId) {
            return LockPatternView.this.getResources().getString(R.string.lockscreen_access_pattern_cell_added_verbose, Integer.valueOf(virtualViewId));
        }

        private int getVirtualViewIdForHit(float x, float y) {
            int columnHit;
            int rowHit = LockPatternView.this.getRowHit(y);
            if (rowHit < 0 || (columnHit = LockPatternView.this.getColumnHit(x)) < 0) {
                return Integer.MIN_VALUE;
            }
            int dotId = (rowHit * 3) + columnHit + 1;
            if (LockPatternView.this.mPatternDrawLookup[rowHit][columnHit]) {
                return dotId;
            }
            return Integer.MIN_VALUE;
        }
    }
}
