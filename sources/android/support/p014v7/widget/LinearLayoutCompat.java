package android.support.p014v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.p007os.Build;
import android.support.annotation.RestrictTo;
import android.support.p011v4.view.GravityCompat;
import android.support.p011v4.view.ViewCompat;
import android.support.p014v7.appcompat.C2132R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v7.widget.LinearLayoutCompat */
/* loaded from: classes3.dex */
public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$DividerMode */
    /* loaded from: classes3.dex */
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$OrientationMode */
    /* loaded from: classes3.dex */
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C2132R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(C2132R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(C2132R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(C2132R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(C2132R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(C2132R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(C2132R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(C2132R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(C2132R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(C2132R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider == this.mDivider) {
            return;
        }
        this.mDivider = divider;
        if (divider != null) {
            this.mDividerWidth = divider.getIntrinsicWidth();
            this.mDividerHeight = divider.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int top = (child.getTop() - lp.topMargin) - this.mDividerHeight;
                drawHorizontalDivider(canvas, top);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                bottom = child2.getBottom() + lp2.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    position2 = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
                }
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                position = isLayoutRtl ? (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth : child2.getRight() + lp2.rightMargin;
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    @Override // android.view.View
    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View child = getChildAt(this.mBaselineAlignedChildIndex);
        int childBaseline = child.getBaseline();
        if (childBaseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int childTop = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (majorGravity = this.mGravity & 112) != 48) {
            if (majorGravity == 16) {
                childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (majorGravity == 80) {
                childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return lp.topMargin + childTop + childBaseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return (this.mShowDividers & 1) != 0;
        } else if (childIndex == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != 8) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:160:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x045c  */
    /* JADX WARN: Removed duplicated region for block: B:196:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int count;
        int maxWidth;
        int heightMode;
        float totalWeight;
        int delta;
        int largestChildHeight;
        int maxWidth2;
        int heightMode2;
        int baselineChildIndex;
        float weightSum;
        boolean matchWidthLocally;
        int allFillParent;
        int weightedMaxWidth;
        float totalWeight2;
        int delta2;
        int maxWidth3;
        int i;
        int childState;
        int maxWidth4;
        boolean skippedMeasure;
        int heightMode3;
        int weightedMaxWidth2;
        int count2;
        int count3;
        LayoutParams lp;
        int i2;
        int largestChildHeight2;
        int allFillParent2;
        int alternativeMaxWidth;
        this.mTotalLength = 0;
        int i3 = 0;
        float totalWeight3 = 0.0f;
        int count4 = getVirtualChildCount();
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode4 = View.MeasureSpec.getMode(heightMeasureSpec);
        boolean skippedMeasure2 = false;
        int baselineChildIndex2 = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        boolean matchWidth = false;
        int childState2 = 0;
        int alternativeMaxWidth2 = 0;
        int maxWidth5 = 0;
        int weightedMaxWidth3 = 0;
        int weightedMaxWidth4 = Integer.MIN_VALUE;
        int largestChildHeight3 = 1;
        while (true) {
            int weightedMaxWidth5 = weightedMaxWidth3;
            if (maxWidth5 < count4) {
                View child = getVirtualChildAt(maxWidth5);
                if (child == null) {
                    int childState3 = i3;
                    int childState4 = this.mTotalLength;
                    this.mTotalLength = childState4 + measureNullChild(maxWidth5);
                    count2 = count4;
                    heightMode3 = heightMode4;
                    weightedMaxWidth3 = weightedMaxWidth5;
                    i3 = childState3;
                } else {
                    int childState5 = i3;
                    int childState6 = child.getVisibility();
                    int maxWidth6 = alternativeMaxWidth2;
                    if (childState6 == 8) {
                        maxWidth5 += getChildrenSkipCount(child, maxWidth5);
                        count2 = count4;
                        heightMode3 = heightMode4;
                        weightedMaxWidth3 = weightedMaxWidth5;
                        i3 = childState5;
                        alternativeMaxWidth2 = maxWidth6;
                    } else {
                        if (hasDividerBeforeChildAt(maxWidth5)) {
                            this.mTotalLength += this.mDividerHeight;
                        }
                        LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                        float totalWeight4 = totalWeight3 + lp2.weight;
                        if (heightMode4 == 1073741824 && lp2.height == 0 && lp2.weight > 0.0f) {
                            int totalLength = this.mTotalLength;
                            int i4 = maxWidth5;
                            int i5 = lp2.bottomMargin;
                            this.mTotalLength = Math.max(totalLength, lp2.topMargin + totalLength + i5);
                            lp = lp2;
                            count2 = count4;
                            heightMode3 = heightMode4;
                            skippedMeasure = true;
                            weightedMaxWidth2 = weightedMaxWidth5;
                            childState = childState5;
                            maxWidth4 = maxWidth6;
                            i = i4;
                            count3 = childState2;
                        } else {
                            int i6 = maxWidth5;
                            int oldHeight = Integer.MIN_VALUE;
                            if (lp2.height == 0 && lp2.weight > 0.0f) {
                                oldHeight = 0;
                                lp2.height = -2;
                            }
                            int oldHeight2 = oldHeight;
                            i = i6;
                            childState = childState5;
                            maxWidth4 = maxWidth6;
                            skippedMeasure = skippedMeasure2;
                            int largestChildHeight4 = weightedMaxWidth4;
                            heightMode3 = heightMode4;
                            weightedMaxWidth2 = weightedMaxWidth5;
                            count2 = count4;
                            count3 = childState2;
                            int alternativeMaxWidth3 = totalWeight4 == 0.0f ? this.mTotalLength : 0;
                            measureChildBeforeLayout(child, i, widthMeasureSpec, 0, heightMeasureSpec, alternativeMaxWidth3);
                            if (oldHeight2 != Integer.MIN_VALUE) {
                                lp = lp2;
                                lp.height = oldHeight2;
                            } else {
                                lp = lp2;
                            }
                            int childHeight = child.getMeasuredHeight();
                            int totalLength2 = this.mTotalLength;
                            child = child;
                            this.mTotalLength = Math.max(totalLength2, totalLength2 + childHeight + lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
                            weightedMaxWidth4 = useLargestChild ? Math.max(childHeight, largestChildHeight4) : largestChildHeight4;
                        }
                        if (baselineChildIndex2 >= 0) {
                            i2 = i;
                            if (baselineChildIndex2 == i2 + 1) {
                                this.mBaselineChildTop = this.mTotalLength;
                            }
                        } else {
                            i2 = i;
                        }
                        if (i2 < baselineChildIndex2 && lp.weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        boolean matchWidthLocally2 = false;
                        if (widthMode != 1073741824 && lp.width == -1) {
                            matchWidth = true;
                            matchWidthLocally2 = true;
                        }
                        int margin = lp.leftMargin + lp.rightMargin;
                        int measuredWidth = child.getMeasuredWidth() + margin;
                        int maxWidth7 = Math.max(maxWidth4, measuredWidth);
                        int childState7 = View.combineMeasuredStates(childState, child.getMeasuredState());
                        if (largestChildHeight3 != 0) {
                            largestChildHeight2 = weightedMaxWidth4;
                            if (lp.width == -1) {
                                allFillParent2 = 1;
                                if (lp.weight <= 0.0f) {
                                    weightedMaxWidth2 = Math.max(weightedMaxWidth2, matchWidthLocally2 ? margin : measuredWidth);
                                    alternativeMaxWidth = count3;
                                } else {
                                    alternativeMaxWidth = Math.max(count3, matchWidthLocally2 ? margin : measuredWidth);
                                }
                                int alternativeMaxWidth4 = getChildrenSkipCount(child, i2);
                                maxWidth5 = i2 + alternativeMaxWidth4;
                                largestChildHeight3 = allFillParent2;
                                i3 = childState7;
                                childState2 = alternativeMaxWidth;
                                weightedMaxWidth3 = weightedMaxWidth2;
                                alternativeMaxWidth2 = maxWidth7;
                                totalWeight3 = totalWeight4;
                                skippedMeasure2 = skippedMeasure;
                                weightedMaxWidth4 = largestChildHeight2;
                            }
                        } else {
                            largestChildHeight2 = weightedMaxWidth4;
                        }
                        allFillParent2 = 0;
                        if (lp.weight <= 0.0f) {
                        }
                        int alternativeMaxWidth42 = getChildrenSkipCount(child, i2);
                        maxWidth5 = i2 + alternativeMaxWidth42;
                        largestChildHeight3 = allFillParent2;
                        i3 = childState7;
                        childState2 = alternativeMaxWidth;
                        weightedMaxWidth3 = weightedMaxWidth2;
                        alternativeMaxWidth2 = maxWidth7;
                        totalWeight3 = totalWeight4;
                        skippedMeasure2 = skippedMeasure;
                        weightedMaxWidth4 = largestChildHeight2;
                    }
                }
                maxWidth5++;
                heightMode4 = heightMode3;
                count4 = count2;
            } else {
                int childState8 = i3;
                int maxWidth8 = alternativeMaxWidth2;
                int count5 = count4;
                int heightMode5 = heightMode4;
                boolean skippedMeasure3 = skippedMeasure2;
                int largestChildHeight5 = weightedMaxWidth4;
                int alternativeMaxWidth5 = childState2;
                int childState9 = this.mTotalLength;
                if (childState9 > 0) {
                    count = count5;
                    if (hasDividerBeforeChildAt(count)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    count = count5;
                }
                if (useLargestChild) {
                    heightMode = heightMode5;
                    if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
                        this.mTotalLength = 0;
                        int i7 = 0;
                        while (i7 < count) {
                            View child2 = getVirtualChildAt(i7);
                            if (child2 == null) {
                                this.mTotalLength += measureNullChild(i7);
                            } else if (child2.getVisibility() == 8) {
                                i7 += getChildrenSkipCount(child2, i7);
                            } else {
                                LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                                int totalLength3 = this.mTotalLength;
                                maxWidth3 = maxWidth8;
                                int maxWidth9 = lp3.topMargin;
                                this.mTotalLength = Math.max(totalLength3, totalLength3 + largestChildHeight5 + maxWidth9 + lp3.bottomMargin + getNextLocationOffset(child2));
                                i7++;
                                maxWidth8 = maxWidth3;
                            }
                            maxWidth3 = maxWidth8;
                            i7++;
                            maxWidth8 = maxWidth3;
                        }
                        maxWidth = maxWidth8;
                    } else {
                        maxWidth = maxWidth8;
                    }
                } else {
                    maxWidth = maxWidth8;
                    heightMode = heightMode5;
                }
                int maxWidth10 = this.mTotalLength;
                this.mTotalLength = maxWidth10 + getPaddingTop() + getPaddingBottom();
                int weightedMaxWidth6 = weightedMaxWidth5;
                int heightSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), heightMeasureSpec, 0);
                int heightSize = heightSizeAndState & 16777215;
                int delta3 = heightSize - this.mTotalLength;
                if (skippedMeasure3) {
                    totalWeight = totalWeight3;
                    delta = delta3;
                } else if (delta3 == 0 || totalWeight3 <= 0.0f) {
                    alternativeMaxWidth5 = Math.max(alternativeMaxWidth5, weightedMaxWidth6);
                    if (useLargestChild && heightMode != 1073741824) {
                        int i8 = 0;
                        while (true) {
                            int i9 = i8;
                            if (i9 >= count) {
                                break;
                            }
                            int heightSize2 = heightSize;
                            View child3 = getVirtualChildAt(i9);
                            if (child3 != null) {
                                weightedMaxWidth = weightedMaxWidth6;
                                int weightedMaxWidth7 = child3.getVisibility();
                                totalWeight2 = totalWeight3;
                                if (weightedMaxWidth7 == 8) {
                                    delta2 = delta3;
                                } else if (((LayoutParams) child3.getLayoutParams()).weight > 0.0f) {
                                    delta2 = delta3;
                                    child3.measure(View.MeasureSpec.makeMeasureSpec(child3.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(largestChildHeight5, 1073741824));
                                } else {
                                    delta2 = delta3;
                                }
                            } else {
                                weightedMaxWidth = weightedMaxWidth6;
                                totalWeight2 = totalWeight3;
                                delta2 = delta3;
                            }
                            i8 = i9 + 1;
                            heightSize = heightSize2;
                            weightedMaxWidth6 = weightedMaxWidth;
                            totalWeight3 = totalWeight2;
                            delta3 = delta2;
                        }
                    }
                    maxWidth2 = maxWidth;
                    largestChildHeight = widthMeasureSpec;
                    if (largestChildHeight3 == 0 && widthMode != 1073741824) {
                        maxWidth2 = alternativeMaxWidth5;
                    }
                    setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth2 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), largestChildHeight, childState8), heightSizeAndState);
                    if (matchWidth) {
                        return;
                    }
                    forceUniformWidth(count, heightMeasureSpec);
                    return;
                } else {
                    totalWeight = totalWeight3;
                    delta = delta3;
                }
                float weightSum2 = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
                this.mTotalLength = 0;
                int i10 = 0;
                maxWidth2 = maxWidth;
                int delta4 = delta;
                while (i10 < count) {
                    View child4 = getVirtualChildAt(i10);
                    boolean useLargestChild2 = useLargestChild;
                    int largestChildHeight6 = largestChildHeight5;
                    if (child4.getVisibility() == 8) {
                        heightMode2 = heightMode;
                        baselineChildIndex = baselineChildIndex2;
                    } else {
                        LayoutParams lp4 = (LayoutParams) child4.getLayoutParams();
                        float childExtra = lp4.weight;
                        if (childExtra > 0.0f) {
                            baselineChildIndex = baselineChildIndex2;
                            int share = (int) ((delta4 * childExtra) / weightSum2);
                            float weightSum3 = weightSum2 - childExtra;
                            int delta5 = delta4 - share;
                            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp4.leftMargin + lp4.rightMargin, lp4.width);
                            if (lp4.height != 0) {
                                heightMode2 = heightMode;
                            } else if (heightMode != 1073741824) {
                                heightMode2 = heightMode;
                            } else {
                                heightMode2 = heightMode;
                                child4.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824));
                                childState8 = View.combineMeasuredStates(childState8, child4.getMeasuredState() & (-256));
                                weightSum2 = weightSum3;
                                delta4 = delta5;
                            }
                            int heightMode6 = child4.getMeasuredHeight();
                            int childHeight2 = heightMode6 + share;
                            if (childHeight2 < 0) {
                                childHeight2 = 0;
                            }
                            child4.measure(childWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(childHeight2, 1073741824));
                            childState8 = View.combineMeasuredStates(childState8, child4.getMeasuredState() & (-256));
                            weightSum2 = weightSum3;
                            delta4 = delta5;
                        } else {
                            heightMode2 = heightMode;
                            baselineChildIndex = baselineChildIndex2;
                        }
                        int heightMode7 = lp4.leftMargin;
                        int margin2 = heightMode7 + lp4.rightMargin;
                        int measuredWidth2 = child4.getMeasuredWidth() + margin2;
                        maxWidth2 = Math.max(maxWidth2, measuredWidth2);
                        if (widthMode != 1073741824) {
                            weightSum = weightSum2;
                            if (lp4.width == -1) {
                                matchWidthLocally = true;
                                int alternativeMaxWidth6 = Math.max(alternativeMaxWidth5, !matchWidthLocally ? margin2 : measuredWidth2);
                                if (largestChildHeight3 != 0 && lp4.width == -1) {
                                    allFillParent = 1;
                                    int totalLength4 = this.mTotalLength;
                                    int alternativeMaxWidth7 = lp4.topMargin;
                                    this.mTotalLength = Math.max(totalLength4, totalLength4 + child4.getMeasuredHeight() + alternativeMaxWidth7 + lp4.bottomMargin + getNextLocationOffset(child4));
                                    largestChildHeight3 = allFillParent;
                                    weightSum2 = weightSum;
                                    alternativeMaxWidth5 = alternativeMaxWidth6;
                                }
                                allFillParent = 0;
                                int totalLength42 = this.mTotalLength;
                                int alternativeMaxWidth72 = lp4.topMargin;
                                this.mTotalLength = Math.max(totalLength42, totalLength42 + child4.getMeasuredHeight() + alternativeMaxWidth72 + lp4.bottomMargin + getNextLocationOffset(child4));
                                largestChildHeight3 = allFillParent;
                                weightSum2 = weightSum;
                                alternativeMaxWidth5 = alternativeMaxWidth6;
                            }
                        } else {
                            weightSum = weightSum2;
                        }
                        matchWidthLocally = false;
                        int alternativeMaxWidth62 = Math.max(alternativeMaxWidth5, !matchWidthLocally ? margin2 : measuredWidth2);
                        if (largestChildHeight3 != 0) {
                            allFillParent = 1;
                            int totalLength422 = this.mTotalLength;
                            int alternativeMaxWidth722 = lp4.topMargin;
                            this.mTotalLength = Math.max(totalLength422, totalLength422 + child4.getMeasuredHeight() + alternativeMaxWidth722 + lp4.bottomMargin + getNextLocationOffset(child4));
                            largestChildHeight3 = allFillParent;
                            weightSum2 = weightSum;
                            alternativeMaxWidth5 = alternativeMaxWidth62;
                        }
                        allFillParent = 0;
                        int totalLength4222 = this.mTotalLength;
                        int alternativeMaxWidth7222 = lp4.topMargin;
                        this.mTotalLength = Math.max(totalLength4222, totalLength4222 + child4.getMeasuredHeight() + alternativeMaxWidth7222 + lp4.bottomMargin + getNextLocationOffset(child4));
                        largestChildHeight3 = allFillParent;
                        weightSum2 = weightSum;
                        alternativeMaxWidth5 = alternativeMaxWidth62;
                    }
                    i10++;
                    useLargestChild = useLargestChild2;
                    largestChildHeight5 = largestChildHeight6;
                    baselineChildIndex2 = baselineChildIndex;
                    heightMode = heightMode2;
                }
                largestChildHeight = widthMeasureSpec;
                this.mTotalLength += getPaddingTop() + getPaddingBottom();
                if (largestChildHeight3 == 0) {
                    maxWidth2 = alternativeMaxWidth5;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth2 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), largestChildHeight, childState8), heightSizeAndState);
                if (matchWidth) {
                }
            }
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:227:0x05e4  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x060e  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0616  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0220  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int childState;
        int maxHeight;
        int maxHeight2;
        int widthMode;
        int largestChildWidth;
        float totalWeight;
        int widthSizeAndState;
        int count;
        int alternativeMaxHeight;
        int delta;
        int descent;
        int widthMode2;
        int widthMode3;
        int widthSizeAndState2;
        int count2;
        boolean useLargestChild;
        int delta2;
        int widthSizeAndState3;
        float weightSum;
        int childBaseline;
        int largestChildWidth2;
        int alternativeMaxHeight2;
        float totalWeight2;
        int i;
        int maxHeight3;
        int i2;
        int weightedMaxHeight;
        int alternativeMaxHeight3;
        int maxHeight4;
        boolean baselineAligned;
        int widthMode4;
        int widthMode5;
        LayoutParams lp;
        int largestChildWidth3;
        boolean matchHeightLocally;
        int margin;
        int margin2;
        int weightedMaxHeight2;
        int alternativeMaxHeight4;
        int childBaseline2;
        int i3;
        this.mTotalLength = 0;
        float totalWeight3 = 0.0f;
        int count3 = getVirtualChildCount();
        int widthMode6 = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned2 = this.mBaselineAligned;
        boolean skippedMeasure = false;
        boolean useLargestChild2 = this.mUseLargestChild;
        boolean isExactly = widthMode6 == 1073741824;
        int childState2 = 0;
        int childState3 = Integer.MIN_VALUE;
        int largestChildWidth4 = 0;
        boolean matchHeight = true;
        int childState4 = 0;
        int maxHeight5 = 0;
        int weightedMaxHeight3 = 0;
        int alternativeMaxHeight5 = 0;
        while (maxHeight5 < count3) {
            View child = getVirtualChildAt(maxHeight5);
            if (child == null) {
                int largestChildWidth5 = childState3;
                int largestChildWidth6 = this.mTotalLength;
                this.mTotalLength = largestChildWidth6 + measureNullChild(maxHeight5);
                baselineAligned = baselineAligned2;
                widthMode4 = widthMode6;
                childState3 = largestChildWidth5;
            } else {
                int largestChildWidth7 = childState3;
                int largestChildWidth8 = child.getVisibility();
                int weightedMaxHeight4 = alternativeMaxHeight5;
                if (largestChildWidth8 == 8) {
                    maxHeight5 += getChildrenSkipCount(child, maxHeight5);
                    baselineAligned = baselineAligned2;
                    widthMode4 = widthMode6;
                    childState3 = largestChildWidth7;
                    alternativeMaxHeight5 = weightedMaxHeight4;
                } else {
                    if (hasDividerBeforeChildAt(maxHeight5)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                    float totalWeight4 = totalWeight3 + lp2.weight;
                    if (widthMode6 == 1073741824 && lp2.width == 0 && lp2.weight > 0.0f) {
                        if (isExactly) {
                            int i4 = this.mTotalLength;
                            int i5 = lp2.leftMargin;
                            i3 = maxHeight5;
                            int i6 = lp2.rightMargin;
                            this.mTotalLength = i4 + i5 + i6;
                        } else {
                            i3 = maxHeight5;
                            int i7 = this.mTotalLength;
                            this.mTotalLength = Math.max(i7, lp2.leftMargin + i7 + lp2.rightMargin);
                        }
                        if (baselineAligned2) {
                            int freeSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            child.measure(freeSpec, freeSpec);
                            lp = lp2;
                            alternativeMaxHeight3 = weightedMaxHeight3;
                            maxHeight4 = childState4;
                            baselineAligned = baselineAligned2;
                            widthMode4 = widthMode6;
                            largestChildWidth3 = largestChildWidth7;
                            weightedMaxHeight = weightedMaxHeight4;
                            i2 = i3;
                            widthMode5 = -1;
                            largestChildWidth7 = largestChildWidth3;
                            matchHeightLocally = false;
                            if (heightMode != 1073741824) {
                            }
                            margin = lp.topMargin + lp.bottomMargin;
                            int childHeight = child.getMeasuredHeight() + margin;
                            int childState5 = View.combineMeasuredStates(childState2, child.getMeasuredState());
                            if (baselineAligned) {
                            }
                            margin2 = margin;
                            int maxHeight6 = Math.max(maxHeight4, childHeight);
                            if (matchHeight) {
                            }
                            if (lp.weight <= 0.0f) {
                            }
                            int alternativeMaxHeight6 = i2;
                            maxHeight5 = alternativeMaxHeight6 + getChildrenSkipCount(child, alternativeMaxHeight6);
                            childState2 = childState5;
                            matchHeight = allFillParent;
                            weightedMaxHeight3 = alternativeMaxHeight4;
                            totalWeight3 = totalWeight4;
                            childState3 = largestChildWidth7;
                            childState4 = maxHeight6;
                            alternativeMaxHeight5 = weightedMaxHeight2;
                        } else {
                            skippedMeasure = true;
                            lp = lp2;
                            alternativeMaxHeight3 = weightedMaxHeight3;
                            maxHeight4 = childState4;
                            baselineAligned = baselineAligned2;
                            widthMode4 = widthMode6;
                            weightedMaxHeight = weightedMaxHeight4;
                            i2 = i3;
                            widthMode5 = -1;
                            matchHeightLocally = false;
                            if (heightMode != 1073741824) {
                            }
                            margin = lp.topMargin + lp.bottomMargin;
                            int childHeight2 = child.getMeasuredHeight() + margin;
                            int childState52 = View.combineMeasuredStates(childState2, child.getMeasuredState());
                            if (baselineAligned) {
                            }
                            margin2 = margin;
                            int maxHeight62 = Math.max(maxHeight4, childHeight2);
                            if (matchHeight) {
                            }
                            if (lp.weight <= 0.0f) {
                            }
                            int alternativeMaxHeight62 = i2;
                            maxHeight5 = alternativeMaxHeight62 + getChildrenSkipCount(child, alternativeMaxHeight62);
                            childState2 = childState52;
                            matchHeight = allFillParent;
                            weightedMaxHeight3 = alternativeMaxHeight4;
                            totalWeight3 = totalWeight4;
                            childState3 = largestChildWidth7;
                            childState4 = maxHeight62;
                            alternativeMaxHeight5 = weightedMaxHeight2;
                        }
                    } else {
                        int i8 = maxHeight5;
                        int oldWidth = Integer.MIN_VALUE;
                        if (lp2.width == 0 && lp2.weight > 0.0f) {
                            oldWidth = 0;
                            lp2.width = -2;
                        }
                        int oldWidth2 = oldWidth;
                        i2 = i8;
                        weightedMaxHeight = weightedMaxHeight4;
                        alternativeMaxHeight3 = weightedMaxHeight3;
                        maxHeight4 = childState4;
                        int maxHeight7 = totalWeight4 == 0.0f ? this.mTotalLength : 0;
                        baselineAligned = baselineAligned2;
                        widthMode4 = widthMode6;
                        widthMode5 = -1;
                        measureChildBeforeLayout(child, i2, widthMeasureSpec, maxHeight7, heightMeasureSpec, 0);
                        if (oldWidth2 != Integer.MIN_VALUE) {
                            lp = lp2;
                            lp.width = oldWidth2;
                        } else {
                            lp = lp2;
                        }
                        int childWidth = child.getMeasuredWidth();
                        if (isExactly) {
                            this.mTotalLength += lp.leftMargin + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        } else {
                            int totalLength = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                        }
                        if (useLargestChild2) {
                            largestChildWidth7 = Math.max(childWidth, largestChildWidth7);
                            matchHeightLocally = false;
                            if (heightMode != 1073741824 && lp.height == widthMode5) {
                                largestChildWidth4 = 1;
                                matchHeightLocally = true;
                            }
                            margin = lp.topMargin + lp.bottomMargin;
                            int childHeight22 = child.getMeasuredHeight() + margin;
                            int childState522 = View.combineMeasuredStates(childState2, child.getMeasuredState());
                            if (baselineAligned || (childBaseline2 = child.getBaseline()) == widthMode5) {
                                margin2 = margin;
                            } else {
                                int gravity = (lp.gravity < 0 ? this.mGravity : lp.gravity) & 112;
                                int index = ((gravity >> 4) & (-2)) >> 1;
                                maxAscent[index] = Math.max(maxAscent[index], childBaseline2);
                                margin2 = margin;
                                maxDescent[index] = Math.max(maxDescent[index], childHeight22 - childBaseline2);
                            }
                            int maxHeight622 = Math.max(maxHeight4, childHeight22);
                            boolean allFillParent = !matchHeight && lp.height == -1;
                            if (lp.weight <= 0.0f) {
                                weightedMaxHeight2 = Math.max(weightedMaxHeight, matchHeightLocally ? margin2 : childHeight22);
                                alternativeMaxHeight4 = alternativeMaxHeight3;
                            } else {
                                weightedMaxHeight2 = weightedMaxHeight;
                                alternativeMaxHeight4 = Math.max(alternativeMaxHeight3, matchHeightLocally ? margin2 : childHeight22);
                            }
                            int alternativeMaxHeight622 = i2;
                            maxHeight5 = alternativeMaxHeight622 + getChildrenSkipCount(child, alternativeMaxHeight622);
                            childState2 = childState522;
                            matchHeight = allFillParent;
                            weightedMaxHeight3 = alternativeMaxHeight4;
                            totalWeight3 = totalWeight4;
                            childState3 = largestChildWidth7;
                            childState4 = maxHeight622;
                            alternativeMaxHeight5 = weightedMaxHeight2;
                        } else {
                            largestChildWidth3 = largestChildWidth7;
                            largestChildWidth7 = largestChildWidth3;
                            matchHeightLocally = false;
                            if (heightMode != 1073741824) {
                                largestChildWidth4 = 1;
                                matchHeightLocally = true;
                            }
                            margin = lp.topMargin + lp.bottomMargin;
                            int childHeight222 = child.getMeasuredHeight() + margin;
                            int childState5222 = View.combineMeasuredStates(childState2, child.getMeasuredState());
                            if (baselineAligned) {
                            }
                            margin2 = margin;
                            int maxHeight6222 = Math.max(maxHeight4, childHeight222);
                            if (matchHeight) {
                            }
                            if (lp.weight <= 0.0f) {
                            }
                            int alternativeMaxHeight6222 = i2;
                            maxHeight5 = alternativeMaxHeight6222 + getChildrenSkipCount(child, alternativeMaxHeight6222);
                            childState2 = childState5222;
                            matchHeight = allFillParent;
                            weightedMaxHeight3 = alternativeMaxHeight4;
                            totalWeight3 = totalWeight4;
                            childState3 = largestChildWidth7;
                            childState4 = maxHeight6222;
                            alternativeMaxHeight5 = weightedMaxHeight2;
                        }
                    }
                }
            }
            maxHeight5++;
            baselineAligned2 = baselineAligned;
            widthMode6 = widthMode4;
        }
        int maxHeight8 = childState4;
        boolean baselineAligned3 = baselineAligned2;
        int widthMode7 = widthMode6;
        int childState6 = childState2;
        int weightedMaxHeight5 = alternativeMaxHeight5;
        int largestChildWidth9 = childState3;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count3)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1) {
            childState = childState6;
            maxHeight = maxHeight8;
        } else {
            int ascent = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int i9 = maxDescent[3];
            int i10 = maxDescent[0];
            int i11 = maxDescent[1];
            childState = childState6;
            int childState7 = maxDescent[2];
            int descent2 = Math.max(i9, Math.max(i10, Math.max(i11, childState7)));
            maxHeight = Math.max(maxHeight8, ascent + descent2);
        }
        if (useLargestChild2) {
            widthMode = widthMode7;
            if (widthMode == Integer.MIN_VALUE || widthMode == 0) {
                this.mTotalLength = 0;
                int i12 = 0;
                while (i12 < count3) {
                    View child2 = getVirtualChildAt(i12);
                    if (child2 == null) {
                        this.mTotalLength += measureNullChild(i12);
                    } else if (child2.getVisibility() == 8) {
                        i12 += getChildrenSkipCount(child2, i12);
                    } else {
                        LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                        if (isExactly) {
                            int i13 = this.mTotalLength;
                            i = i12;
                            int i14 = lp3.leftMargin;
                            maxHeight3 = maxHeight;
                            int maxHeight9 = lp3.rightMargin;
                            this.mTotalLength = i13 + i14 + largestChildWidth9 + maxHeight9 + getNextLocationOffset(child2);
                        } else {
                            i = i12;
                            maxHeight3 = maxHeight;
                            int i15 = this.mTotalLength;
                            this.mTotalLength = Math.max(i15, i15 + largestChildWidth9 + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child2));
                        }
                        i12 = i + 1;
                        maxHeight = maxHeight3;
                    }
                    i = i12;
                    maxHeight3 = maxHeight;
                    i12 = i + 1;
                    maxHeight = maxHeight3;
                }
                maxHeight2 = maxHeight;
            } else {
                maxHeight2 = maxHeight;
            }
        } else {
            maxHeight2 = maxHeight;
            widthMode = widthMode7;
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int widthSizeAndState4 = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
        int widthSize = widthSizeAndState4 & 16777215;
        int delta3 = widthSize - this.mTotalLength;
        if (skippedMeasure) {
            largestChildWidth = weightedMaxHeight3;
            totalWeight = totalWeight3;
        } else if (delta3 == 0 || totalWeight3 <= 0.0f) {
            int alternativeMaxHeight7 = Math.max(weightedMaxHeight3, weightedMaxHeight5);
            if (useLargestChild2) {
                if (widthMode == 1073741824) {
                    alternativeMaxHeight = alternativeMaxHeight7;
                    widthSizeAndState = widthSizeAndState4;
                    count = count3;
                    delta = heightMeasureSpec;
                    if (!matchHeight && heightMode != 1073741824) {
                        maxHeight2 = alternativeMaxHeight;
                    }
                    setMeasuredDimension(widthSizeAndState | (childState & (-16777216)), View.resolveSizeAndState(Math.max(maxHeight2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), delta, childState << 16));
                    if (largestChildWidth4 != 0) {
                        forceUniformHeight(count, widthMeasureSpec);
                        return;
                    }
                    return;
                }
                int i16 = 0;
                while (true) {
                    int i17 = i16;
                    if (i17 >= count3) {
                        break;
                    }
                    int widthSize2 = widthSize;
                    View child3 = getVirtualChildAt(i17);
                    if (child3 != null) {
                        alternativeMaxHeight2 = alternativeMaxHeight7;
                        int alternativeMaxHeight8 = child3.getVisibility();
                        totalWeight2 = totalWeight3;
                        if (alternativeMaxHeight8 == 8) {
                            largestChildWidth2 = largestChildWidth9;
                        } else if (((LayoutParams) child3.getLayoutParams()).weight > 0.0f) {
                            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(largestChildWidth9, 1073741824);
                            largestChildWidth2 = largestChildWidth9;
                            int largestChildWidth10 = child3.getMeasuredHeight();
                            child3.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(largestChildWidth10, 1073741824));
                        } else {
                            largestChildWidth2 = largestChildWidth9;
                        }
                    } else {
                        largestChildWidth2 = largestChildWidth9;
                        alternativeMaxHeight2 = alternativeMaxHeight7;
                        totalWeight2 = totalWeight3;
                    }
                    i16 = i17 + 1;
                    widthSize = widthSize2;
                    alternativeMaxHeight7 = alternativeMaxHeight2;
                    totalWeight3 = totalWeight2;
                    largestChildWidth9 = largestChildWidth2;
                }
            }
            alternativeMaxHeight = alternativeMaxHeight7;
            widthSizeAndState = widthSizeAndState4;
            count = count3;
            delta = heightMeasureSpec;
            if (!matchHeight) {
                maxHeight2 = alternativeMaxHeight;
            }
            setMeasuredDimension(widthSizeAndState | (childState & (-16777216)), View.resolveSizeAndState(Math.max(maxHeight2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), delta, childState << 16));
            if (largestChildWidth4 != 0) {
            }
        } else {
            largestChildWidth = weightedMaxHeight3;
            totalWeight = totalWeight3;
        }
        float weightSum2 = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        int maxHeight10 = -1;
        this.mTotalLength = 0;
        int alternativeMaxHeight9 = largestChildWidth;
        int childState8 = childState;
        float weightSum3 = weightSum2;
        int i18 = 0;
        while (i18 < count3) {
            int weightedMaxHeight6 = weightedMaxHeight5;
            View child4 = getVirtualChildAt(i18);
            if (child4 != null) {
                useLargestChild = useLargestChild2;
                if (child4.getVisibility() == 8) {
                    widthMode2 = widthMode;
                    widthMode3 = delta3;
                    widthSizeAndState2 = widthSizeAndState4;
                    count2 = count3;
                } else {
                    LayoutParams lp4 = (LayoutParams) child4.getLayoutParams();
                    float childExtra = lp4.weight;
                    if (childExtra > 0.0f) {
                        count2 = count3;
                        int share = (int) ((delta3 * childExtra) / weightSum3);
                        weightSum = weightSum3 - childExtra;
                        delta2 = delta3 - share;
                        widthSizeAndState2 = widthSizeAndState4;
                        widthSizeAndState3 = 1073741824;
                        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp4.topMargin + lp4.bottomMargin, lp4.height);
                        if (lp4.width == 0 && widthMode == 1073741824) {
                            child4.measure(View.MeasureSpec.makeMeasureSpec(share > 0 ? share : 0, 1073741824), childHeightMeasureSpec);
                            widthMode2 = widthMode;
                        } else {
                            int childWidth2 = child4.getMeasuredWidth() + share;
                            if (childWidth2 < 0) {
                                childWidth2 = 0;
                            }
                            widthMode2 = widthMode;
                            child4.measure(View.MeasureSpec.makeMeasureSpec(childWidth2, 1073741824), childHeightMeasureSpec);
                        }
                        childState8 = View.combineMeasuredStates(childState8, child4.getMeasuredState() & (-16777216));
                    } else {
                        widthMode2 = widthMode;
                        widthSizeAndState2 = widthSizeAndState4;
                        count2 = count3;
                        widthSizeAndState3 = 1073741824;
                        delta2 = delta3;
                        weightSum = weightSum3;
                    }
                    if (isExactly) {
                        this.mTotalLength += child4.getMeasuredWidth() + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child4);
                    } else {
                        int totalLength2 = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength2, child4.getMeasuredWidth() + totalLength2 + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child4));
                    }
                    boolean matchHeightLocally2 = heightMode != widthSizeAndState3 && lp4.height == -1;
                    int margin3 = lp4.topMargin + lp4.bottomMargin;
                    int childHeight3 = child4.getMeasuredHeight() + margin3;
                    maxHeight10 = Math.max(maxHeight10, childHeight3);
                    alternativeMaxHeight9 = Math.max(alternativeMaxHeight9, matchHeightLocally2 ? margin3 : childHeight3);
                    boolean allFillParent2 = matchHeight && lp4.height == -1;
                    if (baselineAligned3 && (childBaseline = child4.getBaseline()) != -1) {
                        int gravity2 = (lp4.gravity < 0 ? this.mGravity : lp4.gravity) & 112;
                        int index2 = ((gravity2 >> 4) & (-2)) >> 1;
                        int gravity3 = maxAscent[index2];
                        maxAscent[index2] = Math.max(gravity3, childBaseline);
                        maxDescent[index2] = Math.max(maxDescent[index2], childHeight3 - childBaseline);
                    }
                    matchHeight = allFillParent2;
                    weightSum3 = weightSum;
                    i18++;
                    weightedMaxHeight5 = weightedMaxHeight6;
                    useLargestChild2 = useLargestChild;
                    count3 = count2;
                    delta3 = delta2;
                    widthSizeAndState4 = widthSizeAndState2;
                    widthMode = widthMode2;
                }
            } else {
                widthMode2 = widthMode;
                widthMode3 = delta3;
                widthSizeAndState2 = widthSizeAndState4;
                count2 = count3;
                useLargestChild = useLargestChild2;
            }
            delta2 = widthMode3;
            i18++;
            weightedMaxHeight5 = weightedMaxHeight6;
            useLargestChild2 = useLargestChild;
            count3 = count2;
            delta3 = delta2;
            widthSizeAndState4 = widthSizeAndState2;
            widthMode = widthMode2;
        }
        widthSizeAndState = widthSizeAndState4;
        count = count3;
        delta = heightMeasureSpec;
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1) {
            descent = maxHeight10;
        } else {
            int ascent2 = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int descent3 = Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2])));
            descent = Math.max(maxHeight10, ascent2 + descent3);
        }
        alternativeMaxHeight = alternativeMaxHeight9;
        maxHeight2 = descent;
        childState = childState8;
        if (!matchHeight) {
        }
        setMeasuredDimension(widthSizeAndState | (childState & (-16777216)), View.resolveSizeAndState(Math.max(maxHeight2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), delta, childState << 16));
        if (largestChildWidth4 != 0) {
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    int measureNullChild(int childIndex) {
        return 0;
    }

    void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    int getLocationOffset(View child) {
        return 0;
    }

    int getNextLocationOffset(View child) {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int majorGravity;
        int paddingLeft;
        int childLeft;
        int paddingLeft2 = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft2) - getPaddingRight();
        int count = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 112;
        int minorGravity = this.mGravity & 8388615;
        if (majorGravity2 == 16) {
            int childTop2 = getPaddingTop();
            childTop = childTop2 + (((bottom - top) - this.mTotalLength) / 2);
        } else {
            childTop = majorGravity2 != 80 ? getPaddingTop() : ((getPaddingTop() + bottom) - top) - this.mTotalLength;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= count) {
                return;
            }
            View child = getVirtualChildAt(i2);
            if (child == null) {
                childTop += measureNullChild(i2);
                majorGravity = majorGravity2;
                paddingLeft = paddingLeft2;
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                int layoutDirection = ViewCompat.getLayoutDirection(this);
                int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, layoutDirection);
                int gravity2 = absoluteGravity & 7;
                majorGravity = majorGravity2;
                if (gravity2 == 1) {
                    int childLeft2 = childSpace - childWidth;
                    childLeft = (((childLeft2 / 2) + paddingLeft2) + lp.leftMargin) - lp.rightMargin;
                } else if (gravity2 == 5) {
                    childLeft = (childRight - childWidth) - lp.rightMargin;
                } else {
                    childLeft = lp.leftMargin + paddingLeft2;
                }
                if (hasDividerBeforeChildAt(i2)) {
                    childTop += this.mDividerHeight;
                }
                int childTop3 = childTop + lp.topMargin;
                int childTop4 = getLocationOffset(child);
                paddingLeft = paddingLeft2;
                setChildFrame(child, childLeft, childTop3 + childTop4, childWidth, childHeight);
                int childTop5 = childTop3 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i2 += getChildrenSkipCount(child, i2);
                childTop = childTop5;
            } else {
                majorGravity = majorGravity2;
                paddingLeft = paddingLeft2;
            }
            i = i2 + 1;
            majorGravity2 = majorGravity;
            paddingLeft2 = paddingLeft;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void layoutHorizontal(int left, int top, int right, int bottom) {
        int childLeft;
        int majorGravity;
        int[] maxDescent;
        int[] maxAscent;
        boolean baselineAligned;
        int count;
        boolean isLayoutRtl;
        int childBaseline;
        int gravity;
        int i;
        int childTop;
        boolean isLayoutRtl2 = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int height = bottom - top;
        int childBottom = height - getPaddingBottom();
        int childSpace = (height - paddingTop) - getPaddingBottom();
        int count2 = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 8388615;
        int minorGravity = this.mGravity & 112;
        boolean baselineAligned2 = this.mBaselineAligned;
        int[] maxAscent2 = this.mMaxAscent;
        int[] maxDescent2 = this.mMaxDescent;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(majorGravity2, layoutDirection);
        if (absoluteGravity == 1) {
            int childLeft2 = getPaddingLeft();
            int layoutDirection2 = this.mTotalLength;
            childLeft = childLeft2 + (((right - left) - layoutDirection2) / 2);
        } else if (absoluteGravity == 5) {
            int childLeft3 = getPaddingLeft();
            childLeft = ((childLeft3 + right) - left) - this.mTotalLength;
        } else {
            childLeft = getPaddingLeft();
        }
        int childLeft4 = childLeft;
        int start = 0;
        int dir = 1;
        if (isLayoutRtl2) {
            start = count2 - 1;
            dir = -1;
        }
        int i2 = 0;
        int childLeft5 = childLeft4;
        while (true) {
            int i3 = i2;
            if (i3 >= count2) {
                return;
            }
            int childIndex = start + (dir * i3);
            View child = getVirtualChildAt(childIndex);
            if (child == null) {
                childLeft5 += measureNullChild(childIndex);
                maxDescent = maxDescent2;
                maxAscent = maxAscent2;
                baselineAligned = baselineAligned2;
                majorGravity = majorGravity2;
                count = count2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                int i4 = child.getVisibility();
                majorGravity = majorGravity2;
                if (i4 == 8) {
                    maxDescent = maxDescent2;
                    maxAscent = maxAscent2;
                    baselineAligned = baselineAligned2;
                    count = count2;
                    isLayoutRtl = isLayoutRtl2;
                    i3 = i3;
                } else {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (baselineAligned2) {
                        baselineAligned = baselineAligned2;
                        if (lp.height != -1) {
                            childBaseline = child.getBaseline();
                            gravity = lp.gravity;
                            if (gravity < 0) {
                                gravity = minorGravity;
                            }
                            i = gravity & 112;
                            count = count2;
                            if (i != 16) {
                                int childTop2 = childSpace - childHeight;
                                childTop = (((childTop2 / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                            } else if (i == 48) {
                                int childTop3 = lp.topMargin;
                                childTop = childTop3 + paddingTop;
                                if (childBaseline != -1) {
                                    childTop += maxAscent2[1] - childBaseline;
                                }
                            } else if (i == 80) {
                                childTop = (childBottom - childHeight) - lp.bottomMargin;
                                if (childBaseline != -1) {
                                    int descent = child.getMeasuredHeight() - childBaseline;
                                    childTop -= maxDescent2[2] - descent;
                                }
                            } else {
                                childTop = paddingTop;
                            }
                            if (hasDividerBeforeChildAt(childIndex)) {
                                childLeft5 += this.mDividerWidth;
                            }
                            int childLeft6 = childLeft5 + lp.leftMargin;
                            maxDescent = maxDescent2;
                            maxAscent = maxAscent2;
                            isLayoutRtl = isLayoutRtl2;
                            setChildFrame(child, childLeft6 + getLocationOffset(child), childTop, childWidth, childHeight);
                            childLeft5 = childLeft6 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                            i3 += getChildrenSkipCount(child, childIndex);
                        }
                    } else {
                        baselineAligned = baselineAligned2;
                    }
                    childBaseline = -1;
                    gravity = lp.gravity;
                    if (gravity < 0) {
                    }
                    i = gravity & 112;
                    count = count2;
                    if (i != 16) {
                    }
                    if (hasDividerBeforeChildAt(childIndex)) {
                    }
                    int childLeft62 = childLeft5 + lp.leftMargin;
                    maxDescent = maxDescent2;
                    maxAscent = maxAscent2;
                    isLayoutRtl = isLayoutRtl2;
                    setChildFrame(child, childLeft62 + getLocationOffset(child), childTop, childWidth, childHeight);
                    childLeft5 = childLeft62 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                    i3 += getChildrenSkipCount(child, childIndex);
                }
            }
            i2 = i3 + 1;
            majorGravity2 = majorGravity;
            baselineAligned2 = baselineAligned;
            maxDescent2 = maxDescent;
            count2 = count;
            maxAscent2 = maxAscent;
            isLayoutRtl2 = isLayoutRtl;
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= 8388611;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & 8388615;
        if ((8388615 & this.mGravity) != gravity) {
            this.mGravity = (this.mGravity & (-8388616)) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & (-113)) | gravity;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutCompat$LayoutParams */
    /* loaded from: classes3.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, C2132R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(C2132R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(C2132R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.MarginLayoutParams) source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }
}
