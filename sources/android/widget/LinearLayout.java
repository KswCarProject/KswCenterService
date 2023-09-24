package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.slice.Slice;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.TtmlUtils;
import android.security.keystore.KeyProperties;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.C3132R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class LinearLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    @UnsupportedAppUsage
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    @UnsupportedAppUsage
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private static boolean sCompatibilityDone = false;
    private static boolean sRemeasureWeightedChildren = true;
    private final boolean mAllowInconsistentMeasurement;
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mBaselineAligned;
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private int mBaselineAlignedChildIndex;
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mBaselineChildTop;
    @UnsupportedAppUsage
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = -1, mask = -1, name = KeyProperties.DIGEST_NONE), @ViewDebug.FlagToString(equals = 0, mask = 0, name = KeyProperties.DIGEST_NONE), @ViewDebug.FlagToString(equals = 48, mask = 48, name = "TOP"), @ViewDebug.FlagToString(equals = 80, mask = 80, name = "BOTTOM"), @ViewDebug.FlagToString(equals = 3, mask = 3, name = "LEFT"), @ViewDebug.FlagToString(equals = 5, mask = 5, name = "RIGHT"), @ViewDebug.FlagToString(equals = 8388611, mask = 8388611, name = "START"), @ViewDebug.FlagToString(equals = 8388613, mask = 8388613, name = "END"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "CENTER_VERTICAL"), @ViewDebug.FlagToString(equals = 112, mask = 112, name = "FILL_VERTICAL"), @ViewDebug.FlagToString(equals = 1, mask = 1, name = "CENTER_HORIZONTAL"), @ViewDebug.FlagToString(equals = 7, mask = 7, name = "FILL_HORIZONTAL"), @ViewDebug.FlagToString(equals = 17, mask = 17, name = "CENTER"), @ViewDebug.FlagToString(equals = 119, mask = 119, name = "FILL"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "RELATIVE")}, formatToHexString = true)
    private int mGravity;
    private int mLayoutDirection;
    @UnsupportedAppUsage
    private int[] mMaxAscent;
    @UnsupportedAppUsage
    private int[] mMaxDescent;
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mOrientation;
    private int mShowDividers;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mTotalLength;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mUseLargestChild;
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface OrientationMode {
    }

    /* loaded from: classes4.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = -1, m46to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 0, m46to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 48, m46to = "TOP"), @ViewDebug.IntToString(from = 80, m46to = "BOTTOM"), @ViewDebug.IntToString(from = 3, m46to = "LEFT"), @ViewDebug.IntToString(from = 5, m46to = "RIGHT"), @ViewDebug.IntToString(from = 8388611, m46to = "START"), @ViewDebug.IntToString(from = 8388613, m46to = "END"), @ViewDebug.IntToString(from = 16, m46to = "CENTER_VERTICAL"), @ViewDebug.IntToString(from = 112, m46to = "FILL_VERTICAL"), @ViewDebug.IntToString(from = 1, m46to = "CENTER_HORIZONTAL"), @ViewDebug.IntToString(from = 7, m46to = "FILL_HORIZONTAL"), @ViewDebug.IntToString(from = 17, m46to = "CENTER"), @ViewDebug.IntToString(from = 119, m46to = "FILL")})
        public int gravity;
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public float weight;

        /* loaded from: classes4.dex */
        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_gravityId;
            private int mLayout_weightId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mLayout_weightId = propertyMapper.mapFloat("layout_weight", 16843137);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams node, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, node.gravity);
                propertyReader.readFloat(this.mLayout_weightId, node.weight);
            }
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, C3132R.styleable.LinearLayout_Layout);
            this.weight = a.getFloat(3, 0.0f);
            this.gravity = a.getInt(0, -1);
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

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String output) {
            return output + "LinearLayout.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " weight=" + this.weight + "}";
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        @UnsupportedAppUsage
        protected void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("layout:weight", this.weight);
            encoder.addProperty("layout:gravity", this.gravity);
        }
    }

    /* loaded from: classes4.dex */
    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LinearLayout> {
        private int mBaselineAlignedChildIndexId;
        private int mBaselineAlignedId;
        private int mDividerId;
        private int mGravityId;
        private int mMeasureWithLargestChildId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mWeightSumId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mBaselineAlignedId = propertyMapper.mapBoolean("baselineAligned", 16843046);
            this.mBaselineAlignedChildIndexId = propertyMapper.mapInt("baselineAlignedChildIndex", 16843047);
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mMeasureWithLargestChildId = propertyMapper.mapBoolean("measureWithLargestChild", 16843476);
            SparseArray<String> orientationEnumMapping = new SparseArray<>();
            orientationEnumMapping.put(0, Slice.HINT_HORIZONTAL);
            orientationEnumMapping.put(1, "vertical");
            Objects.requireNonNull(orientationEnumMapping);
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(orientationEnumMapping));
            this.mWeightSumId = propertyMapper.mapFloat("weightSum", 16843048);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(LinearLayout node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mBaselineAlignedId, node.isBaselineAligned());
            propertyReader.readInt(this.mBaselineAlignedChildIndexId, node.getBaselineAlignedChildIndex());
            propertyReader.readObject(this.mDividerId, node.getDividerDrawable());
            propertyReader.readGravity(this.mGravityId, node.getGravity());
            propertyReader.readBoolean(this.mMeasureWithLargestChildId, node.isMeasureWithLargestChildEnabled());
            propertyReader.readIntEnum(this.mOrientationId, node.getOrientation());
            propertyReader.readFloat(this.mWeightSumId, node.getWeightSum());
        }
    }

    public LinearLayout(Context context) {
        this(context, null);
    }

    public LinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z;
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        if (!sCompatibilityDone && context != null) {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion >= 28) {
                z = true;
            } else {
                z = false;
            }
            sRemeasureWeightedChildren = z;
            sCompatibilityDone = true;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.LinearLayout, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, C3132R.styleable.LinearLayout, attrs, a, defStyleAttr, defStyleRes);
        int index = a.getInt(1, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(0, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(2, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(3, -1);
        this.mUseLargestChild = a.getBoolean(6, false);
        this.mShowDividers = a.getInt(7, 0);
        this.mDividerPadding = a.getDimensionPixelSize(8, 0);
        setDividerDrawable(a.getDrawable(5));
        int version = context.getApplicationInfo().targetSdkVersion;
        this.mAllowInconsistentMeasurement = version <= 23;
        a.recycle();
    }

    private boolean isShowingDividers() {
        return (this.mShowDividers == 0 || this.mDivider == null) ? false : true;
    }

    public void setShowDividers(int showDividers) {
        if (showDividers == this.mShowDividers) {
            return;
        }
        this.mShowDividers = showDividers;
        setWillNotDraw(!isShowingDividers());
        requestLayout();
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
        setWillNotDraw(!isShowingDividers());
        requestLayout();
    }

    public void setDividerPadding(int padding) {
        if (padding == this.mDividerPadding) {
            return;
        }
        this.mDividerPadding = padding;
        if (isShowingDividers()) {
            requestLayout();
            invalidate();
        }
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

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
            View child2 = getLastNonGoneChild();
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                bottom = child2.getBottom() + lp2.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    private View getLastNonGoneChild() {
        for (int i = getVirtualChildCount() - 1; i >= 0; i--) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8) {
                return child;
            }
        }
        return null;
    }

    void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = isLayoutRtl();
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
            View child2 = getLastNonGoneChild();
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

    @RemotableViewMethod
    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    @RemotableViewMethod
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
                childTop += ((((this.mBottom - this.mTop) - this.mPaddingTop) - this.mPaddingBottom) - this.mTotalLength) / 2;
            } else if (majorGravity == 80) {
                childTop = ((this.mBottom - this.mTop) - this.mPaddingBottom) - this.mTotalLength;
            }
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return lp.topMargin + childTop + childBaseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    @RemotableViewMethod
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

    @RemotableViewMethod
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
        if (childIndex == getVirtualChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        boolean allViewsAreGoneBefore = allViewsAreGoneBefore(childIndex);
        return allViewsAreGoneBefore ? (this.mShowDividers & 1) != 0 : (this.mShowDividers & 2) != 0;
    }

    private boolean allViewsAreGoneBefore(int childIndex) {
        for (int i = childIndex - 1; i >= 0; i--) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:166:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x044a  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x019d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode;
        float totalWeight;
        int childState;
        int childState2;
        int largestChildHeight;
        int heightMode2;
        int largestChildHeight2;
        boolean useLargestChild;
        int baselineChildIndex;
        int margin;
        boolean matchWidthLocally;
        int allFillParent;
        int alternativeMaxWidth;
        float totalWeight2;
        int childState3;
        int i;
        int i2;
        int i3;
        int maxWidth;
        int weightedMaxWidth;
        boolean skippedMeasure;
        int alternativeMaxWidth2;
        int heightMode3;
        int heightMode4;
        int childState4;
        LayoutParams lp;
        int largestChildHeight3;
        int i4;
        int largestChildHeight4;
        int allFillParent2;
        int allFillParent3;
        int weightedMaxWidth2;
        int alternativeMaxWidth3;
        this.mTotalLength = 0;
        float totalWeight3 = 0.0f;
        int count = getVirtualChildCount();
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode5 = View.MeasureSpec.getMode(heightMeasureSpec);
        boolean skippedMeasure2 = false;
        int baselineChildIndex2 = this.mBaselineAlignedChildIndex;
        boolean useLargestChild2 = this.mUseLargestChild;
        int consumedExcessSpace = 0;
        int nonSkippedChildCount = 0;
        boolean matchWidth = false;
        int childState5 = 0;
        int childState6 = 0;
        int maxWidth2 = 0;
        int weightedMaxWidth3 = 0;
        int alternativeMaxWidth4 = 0;
        int largestChildHeight5 = 1;
        int largestChildHeight6 = Integer.MIN_VALUE;
        while (true) {
            int largestChildHeight7 = largestChildHeight6;
            if (maxWidth2 < count) {
                View child = getVirtualChildAt(maxWidth2);
                if (child == null) {
                    int maxWidth3 = childState6;
                    int maxWidth4 = this.mTotalLength;
                    this.mTotalLength = maxWidth4 + measureNullChild(maxWidth2);
                    heightMode3 = heightMode5;
                    largestChildHeight6 = largestChildHeight7;
                    childState6 = maxWidth3;
                } else {
                    int maxWidth5 = childState6;
                    int maxWidth6 = child.getVisibility();
                    int weightedMaxWidth4 = alternativeMaxWidth4;
                    if (maxWidth6 == 8) {
                        maxWidth2 += getChildrenSkipCount(child, maxWidth2);
                        heightMode3 = heightMode5;
                        largestChildHeight6 = largestChildHeight7;
                        childState6 = maxWidth5;
                        alternativeMaxWidth4 = weightedMaxWidth4;
                    } else {
                        nonSkippedChildCount++;
                        if (hasDividerBeforeChildAt(maxWidth2)) {
                            this.mTotalLength += this.mDividerHeight;
                        }
                        LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                        float totalWeight4 = totalWeight3 + lp2.weight;
                        boolean useExcessSpace = lp2.height == 0 && lp2.weight > 0.0f;
                        if (heightMode5 == 1073741824 && useExcessSpace) {
                            int totalLength = this.mTotalLength;
                            int i5 = maxWidth2;
                            int i6 = lp2.bottomMargin;
                            this.mTotalLength = Math.max(totalLength, lp2.topMargin + totalLength + i6);
                            lp = lp2;
                            childState4 = childState5;
                            heightMode3 = heightMode5;
                            skippedMeasure = true;
                            largestChildHeight3 = largestChildHeight7;
                            maxWidth = maxWidth5;
                            weightedMaxWidth = weightedMaxWidth4;
                            i3 = i5;
                            heightMode4 = 1073741824;
                            alternativeMaxWidth2 = weightedMaxWidth3;
                        } else {
                            int i7 = maxWidth2;
                            if (useExcessSpace) {
                                lp2.height = -2;
                            }
                            int childState7 = childState5;
                            int childState8 = totalWeight4 == 0.0f ? this.mTotalLength : 0;
                            i3 = i7;
                            maxWidth = maxWidth5;
                            weightedMaxWidth = weightedMaxWidth4;
                            skippedMeasure = skippedMeasure2;
                            alternativeMaxWidth2 = weightedMaxWidth3;
                            heightMode3 = heightMode5;
                            heightMode4 = 1073741824;
                            childState4 = childState7;
                            measureChildBeforeLayout(child, i3, widthMeasureSpec, 0, heightMeasureSpec, childState8);
                            int childHeight = child.getMeasuredHeight();
                            if (useExcessSpace) {
                                lp = lp2;
                                lp.height = 0;
                                consumedExcessSpace += childHeight;
                            } else {
                                lp = lp2;
                            }
                            int totalLength2 = this.mTotalLength;
                            child = child;
                            this.mTotalLength = Math.max(totalLength2, totalLength2 + childHeight + lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
                            largestChildHeight3 = useLargestChild2 ? Math.max(childHeight, largestChildHeight7) : largestChildHeight7;
                        }
                        if (baselineChildIndex2 >= 0) {
                            i4 = i3;
                            if (baselineChildIndex2 == i4 + 1) {
                                this.mBaselineChildTop = this.mTotalLength;
                            }
                        } else {
                            i4 = i3;
                        }
                        if (i4 < baselineChildIndex2 && lp.weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        boolean matchWidthLocally2 = false;
                        if (widthMode != heightMode4 && lp.width == -1) {
                            matchWidth = true;
                            matchWidthLocally2 = true;
                        }
                        int margin2 = lp.leftMargin + lp.rightMargin;
                        int measuredWidth = child.getMeasuredWidth() + margin2;
                        int maxWidth7 = Math.max(maxWidth, measuredWidth);
                        childState5 = combineMeasuredStates(childState4, child.getMeasuredState());
                        if (largestChildHeight5 != 0) {
                            largestChildHeight4 = largestChildHeight3;
                            if (lp.width == -1) {
                                allFillParent2 = 1;
                                if (lp.weight <= 0.0f) {
                                    allFillParent3 = allFillParent2;
                                    weightedMaxWidth2 = Math.max(weightedMaxWidth, matchWidthLocally2 ? margin2 : measuredWidth);
                                    alternativeMaxWidth3 = alternativeMaxWidth2;
                                } else {
                                    allFillParent3 = allFillParent2;
                                    weightedMaxWidth2 = weightedMaxWidth;
                                    alternativeMaxWidth3 = Math.max(alternativeMaxWidth2, matchWidthLocally2 ? margin2 : measuredWidth);
                                }
                                int alternativeMaxWidth5 = getChildrenSkipCount(child, i4);
                                int i8 = i4 + alternativeMaxWidth5;
                                alternativeMaxWidth4 = weightedMaxWidth2;
                                maxWidth2 = i8;
                                childState6 = maxWidth7;
                                weightedMaxWidth3 = alternativeMaxWidth3;
                                totalWeight3 = totalWeight4;
                                skippedMeasure2 = skippedMeasure;
                                largestChildHeight6 = largestChildHeight4;
                                largestChildHeight5 = allFillParent3;
                            }
                        } else {
                            largestChildHeight4 = largestChildHeight3;
                        }
                        allFillParent2 = 0;
                        if (lp.weight <= 0.0f) {
                        }
                        int alternativeMaxWidth52 = getChildrenSkipCount(child, i4);
                        int i82 = i4 + alternativeMaxWidth52;
                        alternativeMaxWidth4 = weightedMaxWidth2;
                        maxWidth2 = i82;
                        childState6 = maxWidth7;
                        weightedMaxWidth3 = alternativeMaxWidth3;
                        totalWeight3 = totalWeight4;
                        skippedMeasure2 = skippedMeasure;
                        largestChildHeight6 = largestChildHeight4;
                        largestChildHeight5 = allFillParent3;
                    }
                }
                maxWidth2++;
                heightMode5 = heightMode3;
            } else {
                int maxWidth8 = childState6;
                int weightedMaxWidth5 = alternativeMaxWidth4;
                int heightMode6 = heightMode5;
                boolean skippedMeasure3 = skippedMeasure2;
                int alternativeMaxWidth6 = weightedMaxWidth3;
                if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                if (useLargestChild2) {
                    heightMode = heightMode6;
                    if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
                        this.mTotalLength = 0;
                        int i9 = 0;
                        while (i9 < count) {
                            View child2 = getVirtualChildAt(i9);
                            if (child2 == null) {
                                this.mTotalLength += measureNullChild(i9);
                                i = i9;
                            } else if (child2.getVisibility() == 8) {
                                i2 = i9 + getChildrenSkipCount(child2, i9);
                                i9 = i2 + 1;
                            } else {
                                LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                                int totalLength3 = this.mTotalLength;
                                i = i9;
                                int i10 = lp3.topMargin;
                                this.mTotalLength = Math.max(totalLength3, totalLength3 + largestChildHeight7 + i10 + lp3.bottomMargin + getNextLocationOffset(child2));
                            }
                            i2 = i;
                            i9 = i2 + 1;
                        }
                    }
                } else {
                    heightMode = heightMode6;
                }
                this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
                int heightSize = this.mTotalLength;
                int largestChildHeight8 = largestChildHeight7;
                int heightSizeAndState = resolveSizeAndState(Math.max(heightSize, getSuggestedMinimumHeight()), heightMeasureSpec, 0);
                int heightSize2 = heightSizeAndState & 16777215;
                int remainingExcess = (heightSize2 - this.mTotalLength) + (this.mAllowInconsistentMeasurement ? 0 : consumedExcessSpace);
                if (skippedMeasure3) {
                    totalWeight = totalWeight3;
                    childState = childState5;
                } else if ((!sRemeasureWeightedChildren && remainingExcess == 0) || totalWeight3 <= 0.0f) {
                    int alternativeMaxWidth7 = Math.max(alternativeMaxWidth6, weightedMaxWidth5);
                    if (useLargestChild2 && heightMode != 1073741824) {
                        int i11 = 0;
                        while (true) {
                            int i12 = i11;
                            if (i12 >= count) {
                                break;
                            }
                            int weightedMaxWidth6 = weightedMaxWidth5;
                            View child3 = getVirtualChildAt(i12);
                            if (child3 != null) {
                                alternativeMaxWidth = alternativeMaxWidth7;
                                int alternativeMaxWidth8 = child3.getVisibility();
                                totalWeight2 = totalWeight3;
                                if (alternativeMaxWidth8 == 8) {
                                    childState3 = childState5;
                                } else {
                                    float childExtra = ((LayoutParams) child3.getLayoutParams()).weight;
                                    if (childExtra > 0.0f) {
                                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(child3.getMeasuredWidth(), 1073741824);
                                        childState3 = childState5;
                                        int childState9 = View.MeasureSpec.makeMeasureSpec(largestChildHeight8, 1073741824);
                                        child3.measure(makeMeasureSpec, childState9);
                                    } else {
                                        childState3 = childState5;
                                    }
                                }
                            } else {
                                alternativeMaxWidth = alternativeMaxWidth7;
                                totalWeight2 = totalWeight3;
                                childState3 = childState5;
                            }
                            i11 = i12 + 1;
                            weightedMaxWidth5 = weightedMaxWidth6;
                            alternativeMaxWidth7 = alternativeMaxWidth;
                            totalWeight3 = totalWeight2;
                            childState5 = childState3;
                        }
                    }
                    alternativeMaxWidth6 = alternativeMaxWidth7;
                    childState2 = childState5;
                    largestChildHeight = widthMeasureSpec;
                    if (largestChildHeight5 == 0 && widthMode != 1073741824) {
                        maxWidth8 = alternativeMaxWidth6;
                    }
                    setMeasuredDimension(resolveSizeAndState(Math.max(maxWidth8 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), largestChildHeight, childState2), heightSizeAndState);
                    if (matchWidth) {
                        return;
                    }
                    forceUniformWidth(count, heightMeasureSpec);
                    return;
                } else {
                    totalWeight = totalWeight3;
                    childState = childState5;
                }
                float remainingWeightSum = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
                this.mTotalLength = 0;
                float remainingWeightSum2 = remainingWeightSum;
                childState2 = childState;
                int i13 = 0;
                while (i13 < count) {
                    View child4 = getVirtualChildAt(i13);
                    if (child4 != null) {
                        useLargestChild = useLargestChild2;
                        baselineChildIndex = baselineChildIndex2;
                        if (child4.getVisibility() == 8) {
                            heightMode2 = heightMode;
                            largestChildHeight2 = largestChildHeight8;
                        } else {
                            LayoutParams lp4 = (LayoutParams) child4.getLayoutParams();
                            float childWeight = lp4.weight;
                            if (childWeight > 0.0f) {
                                int share = (int) ((remainingExcess * childWeight) / remainingWeightSum2);
                                remainingExcess -= share;
                                float remainingWeightSum3 = remainingWeightSum2 - childWeight;
                                int childHeight2 = (!this.mUseLargestChild || heightMode == 1073741824) ? (lp4.height != 0 || (this.mAllowInconsistentMeasurement && heightMode != 1073741824)) ? child4.getMeasuredHeight() + share : share : largestChildHeight8;
                                heightMode2 = heightMode;
                                int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, childHeight2), 1073741824);
                                int i14 = this.mPaddingLeft;
                                int childHeight3 = this.mPaddingRight;
                                largestChildHeight2 = largestChildHeight8;
                                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, i14 + childHeight3 + lp4.leftMargin + lp4.rightMargin, lp4.width);
                                child4.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                                childState2 = combineMeasuredStates(childState2, child4.getMeasuredState() & (-256));
                                remainingWeightSum2 = remainingWeightSum3;
                            } else {
                                heightMode2 = heightMode;
                                largestChildHeight2 = largestChildHeight8;
                            }
                            int heightMode7 = lp4.leftMargin;
                            int margin3 = heightMode7 + lp4.rightMargin;
                            int measuredWidth2 = child4.getMeasuredWidth() + margin3;
                            maxWidth8 = Math.max(maxWidth8, measuredWidth2);
                            float remainingWeightSum4 = remainingWeightSum2;
                            if (widthMode != 1073741824) {
                                margin = margin3;
                                if (lp4.width == -1) {
                                    matchWidthLocally = true;
                                    int alternativeMaxWidth9 = Math.max(alternativeMaxWidth6, !matchWidthLocally ? margin : measuredWidth2);
                                    if (largestChildHeight5 != 0 && lp4.width == -1) {
                                        allFillParent = 1;
                                        int totalLength4 = this.mTotalLength;
                                        int alternativeMaxWidth10 = lp4.topMargin;
                                        this.mTotalLength = Math.max(totalLength4, totalLength4 + child4.getMeasuredHeight() + alternativeMaxWidth10 + lp4.bottomMargin + getNextLocationOffset(child4));
                                        largestChildHeight5 = allFillParent;
                                        remainingWeightSum2 = remainingWeightSum4;
                                        alternativeMaxWidth6 = alternativeMaxWidth9;
                                    }
                                    allFillParent = 0;
                                    int totalLength42 = this.mTotalLength;
                                    int alternativeMaxWidth102 = lp4.topMargin;
                                    this.mTotalLength = Math.max(totalLength42, totalLength42 + child4.getMeasuredHeight() + alternativeMaxWidth102 + lp4.bottomMargin + getNextLocationOffset(child4));
                                    largestChildHeight5 = allFillParent;
                                    remainingWeightSum2 = remainingWeightSum4;
                                    alternativeMaxWidth6 = alternativeMaxWidth9;
                                }
                            } else {
                                margin = margin3;
                            }
                            matchWidthLocally = false;
                            int alternativeMaxWidth92 = Math.max(alternativeMaxWidth6, !matchWidthLocally ? margin : measuredWidth2);
                            if (largestChildHeight5 != 0) {
                                allFillParent = 1;
                                int totalLength422 = this.mTotalLength;
                                int alternativeMaxWidth1022 = lp4.topMargin;
                                this.mTotalLength = Math.max(totalLength422, totalLength422 + child4.getMeasuredHeight() + alternativeMaxWidth1022 + lp4.bottomMargin + getNextLocationOffset(child4));
                                largestChildHeight5 = allFillParent;
                                remainingWeightSum2 = remainingWeightSum4;
                                alternativeMaxWidth6 = alternativeMaxWidth92;
                            }
                            allFillParent = 0;
                            int totalLength4222 = this.mTotalLength;
                            int alternativeMaxWidth10222 = lp4.topMargin;
                            this.mTotalLength = Math.max(totalLength4222, totalLength4222 + child4.getMeasuredHeight() + alternativeMaxWidth10222 + lp4.bottomMargin + getNextLocationOffset(child4));
                            largestChildHeight5 = allFillParent;
                            remainingWeightSum2 = remainingWeightSum4;
                            alternativeMaxWidth6 = alternativeMaxWidth92;
                        }
                    } else {
                        heightMode2 = heightMode;
                        largestChildHeight2 = largestChildHeight8;
                        useLargestChild = useLargestChild2;
                        baselineChildIndex = baselineChildIndex2;
                    }
                    i13++;
                    useLargestChild2 = useLargestChild;
                    baselineChildIndex2 = baselineChildIndex;
                    heightMode = heightMode2;
                    largestChildHeight8 = largestChildHeight2;
                }
                largestChildHeight = widthMeasureSpec;
                this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
                if (largestChildHeight5 == 0) {
                    maxWidth8 = alternativeMaxWidth6;
                }
                setMeasuredDimension(resolveSizeAndState(Math.max(maxWidth8 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), largestChildHeight, childState2), heightSizeAndState);
                if (matchWidth) {
                }
            }
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8) {
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

    /* JADX WARN: Removed duplicated region for block: B:208:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0602  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int childState;
        int maxHeight;
        int widthMode;
        float totalWeight;
        int remainingExcess;
        int widthSizeAndState;
        int count;
        int remainingExcess2;
        int descent;
        int alternativeMaxHeight;
        int widthMode2;
        int largestChildWidth;
        int remainingExcess3;
        int widthSizeAndState2;
        int count2;
        int remainingExcess4;
        int share;
        float remainingWeightSum;
        int allFillParent;
        int allFillParent2;
        int widthSize;
        float totalWeight2;
        int remainingExcess5;
        int i;
        int maxHeight2;
        int i2;
        int weightedMaxHeight;
        int alternativeMaxHeight2;
        int maxHeight3;
        boolean baselineAligned;
        int widthMode3;
        int widthMode4;
        LayoutParams lp;
        int margin;
        int alternativeMaxHeight3;
        int weightedMaxHeight2;
        int childBaseline;
        int i3;
        this.mTotalLength = 0;
        int alternativeMaxHeight4 = 0;
        float totalWeight3 = 0.0f;
        int count3 = getVirtualChildCount();
        int widthMode5 = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        boolean matchHeight = false;
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
        boolean useLargestChild = this.mUseLargestChild;
        boolean isExactly = widthMode5 == 1073741824;
        int usedExcessSpace = 0;
        int nonSkippedChildCount = 0;
        int childState2 = 0;
        int childState3 = 0;
        int weightedMaxHeight3 = 0;
        int maxHeight4 = 0;
        int largestChildWidth2 = 1;
        int largestChildWidth3 = Integer.MIN_VALUE;
        while (maxHeight4 < count3) {
            View child = getVirtualChildAt(maxHeight4);
            if (child == null) {
                int weightedMaxHeight4 = childState3;
                int weightedMaxHeight5 = this.mTotalLength;
                this.mTotalLength = weightedMaxHeight5 + measureNullChild(maxHeight4);
                baselineAligned = baselineAligned2;
                widthMode3 = widthMode5;
                childState3 = weightedMaxHeight4;
            } else {
                int weightedMaxHeight6 = childState3;
                int weightedMaxHeight7 = child.getVisibility();
                int alternativeMaxHeight5 = alternativeMaxHeight4;
                if (weightedMaxHeight7 == 8) {
                    maxHeight4 += getChildrenSkipCount(child, maxHeight4);
                    baselineAligned = baselineAligned2;
                    widthMode3 = widthMode5;
                    childState3 = weightedMaxHeight6;
                    alternativeMaxHeight4 = alternativeMaxHeight5;
                } else {
                    nonSkippedChildCount++;
                    if (hasDividerBeforeChildAt(maxHeight4)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams lp2 = (LayoutParams) child.getLayoutParams();
                    float totalWeight4 = totalWeight3 + lp2.weight;
                    boolean useExcessSpace = lp2.width == 0 && lp2.weight > 0.0f;
                    if (widthMode5 == 1073741824 && useExcessSpace) {
                        if (isExactly) {
                            i3 = maxHeight4;
                            this.mTotalLength += lp2.leftMargin + lp2.rightMargin;
                        } else {
                            i3 = maxHeight4;
                            int i4 = this.mTotalLength;
                            this.mTotalLength = Math.max(i4, lp2.leftMargin + i4 + lp2.rightMargin);
                        }
                        if (baselineAligned2) {
                            int freeWidthSpec = View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec), 0);
                            int freeHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), 0);
                            child.measure(freeWidthSpec, freeHeightSpec);
                        } else {
                            skippedMeasure = true;
                        }
                        lp = lp2;
                        maxHeight3 = weightedMaxHeight3;
                        baselineAligned = baselineAligned2;
                        widthMode3 = widthMode5;
                        weightedMaxHeight = weightedMaxHeight6;
                        alternativeMaxHeight2 = alternativeMaxHeight5;
                        i2 = i3;
                        widthMode4 = -1;
                    } else {
                        int i5 = maxHeight4;
                        if (useExcessSpace) {
                            lp2.width = -2;
                        }
                        int largestChildWidth4 = largestChildWidth3;
                        int largestChildWidth5 = totalWeight4 == 0.0f ? this.mTotalLength : 0;
                        i2 = i5;
                        weightedMaxHeight = weightedMaxHeight6;
                        alternativeMaxHeight2 = alternativeMaxHeight5;
                        maxHeight3 = weightedMaxHeight3;
                        baselineAligned = baselineAligned2;
                        widthMode3 = widthMode5;
                        widthMode4 = -1;
                        measureChildBeforeLayout(child, i2, widthMeasureSpec, largestChildWidth5, heightMeasureSpec, 0);
                        int childWidth = child.getMeasuredWidth();
                        if (useExcessSpace) {
                            lp = lp2;
                            lp.width = 0;
                            usedExcessSpace += childWidth;
                        } else {
                            lp = lp2;
                        }
                        if (isExactly) {
                            this.mTotalLength += lp.leftMargin + childWidth + lp.rightMargin + getNextLocationOffset(child);
                        } else {
                            int totalLength = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                        }
                        largestChildWidth3 = useLargestChild ? Math.max(childWidth, largestChildWidth4) : largestChildWidth4;
                    }
                    boolean matchHeightLocally = false;
                    if (heightMode != 1073741824 && lp.height == widthMode4) {
                        matchHeight = true;
                        matchHeightLocally = true;
                    }
                    int margin2 = lp.topMargin + lp.bottomMargin;
                    int childHeight = child.getMeasuredHeight() + margin2;
                    int childState4 = combineMeasuredStates(childState2, child.getMeasuredState());
                    if (!baselineAligned || (childBaseline = child.getBaseline()) == widthMode4) {
                        margin = margin2;
                    } else {
                        int gravity = (lp.gravity < 0 ? this.mGravity : lp.gravity) & 112;
                        int index = ((gravity >> 4) & (-2)) >> 1;
                        maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                        margin = margin2;
                        maxDescent[index] = Math.max(maxDescent[index], childHeight - childBaseline);
                    }
                    int maxHeight5 = Math.max(maxHeight3, childHeight);
                    int allFillParent3 = (largestChildWidth2 == 0 || lp.height != -1) ? 0 : 1;
                    if (lp.weight > 0.0f) {
                        weightedMaxHeight2 = Math.max(weightedMaxHeight, matchHeightLocally ? margin : childHeight);
                        alternativeMaxHeight3 = alternativeMaxHeight2;
                    } else {
                        int weightedMaxHeight8 = weightedMaxHeight;
                        alternativeMaxHeight3 = Math.max(alternativeMaxHeight2, matchHeightLocally ? margin : childHeight);
                        weightedMaxHeight2 = weightedMaxHeight8;
                    }
                    int weightedMaxHeight9 = i2;
                    weightedMaxHeight3 = maxHeight5;
                    childState2 = childState4;
                    largestChildWidth2 = allFillParent3;
                    totalWeight3 = totalWeight4;
                    alternativeMaxHeight4 = alternativeMaxHeight3;
                    maxHeight4 = weightedMaxHeight9 + getChildrenSkipCount(child, weightedMaxHeight9);
                    childState3 = weightedMaxHeight2;
                }
            }
            maxHeight4++;
            baselineAligned2 = baselineAligned;
            widthMode5 = widthMode3;
        }
        int i6 = alternativeMaxHeight4;
        int maxHeight6 = weightedMaxHeight3;
        int largestChildWidth6 = largestChildWidth3;
        boolean baselineAligned3 = baselineAligned2;
        int widthMode6 = widthMode5;
        int childState5 = childState2;
        int widthMode7 = childState3;
        if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count3)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1) {
            childState = childState5;
        } else {
            int ascent = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int i7 = maxDescent[3];
            int i8 = maxDescent[0];
            int i9 = maxDescent[1];
            childState = childState5;
            int childState6 = maxDescent[2];
            int descent2 = Math.max(i7, Math.max(i8, Math.max(i9, childState6)));
            maxHeight6 = Math.max(maxHeight6, ascent + descent2);
        }
        if (useLargestChild) {
            widthMode = widthMode6;
            if (widthMode == Integer.MIN_VALUE || widthMode == 0) {
                this.mTotalLength = 0;
                int i10 = 0;
                while (i10 < count3) {
                    View child2 = getVirtualChildAt(i10);
                    if (child2 == null) {
                        this.mTotalLength += measureNullChild(i10);
                    } else if (child2.getVisibility() == 8) {
                        i10 += getChildrenSkipCount(child2, i10);
                    } else {
                        LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                        if (isExactly) {
                            int i11 = this.mTotalLength;
                            i = i10;
                            maxHeight2 = maxHeight6;
                            int maxHeight7 = lp3.rightMargin;
                            this.mTotalLength = i11 + lp3.leftMargin + largestChildWidth6 + maxHeight7 + getNextLocationOffset(child2);
                        } else {
                            i = i10;
                            maxHeight2 = maxHeight6;
                            int i12 = this.mTotalLength;
                            this.mTotalLength = Math.max(i12, i12 + largestChildWidth6 + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child2));
                        }
                        i10 = i + 1;
                        maxHeight6 = maxHeight2;
                    }
                    i = i10;
                    maxHeight2 = maxHeight6;
                    i10 = i + 1;
                    maxHeight6 = maxHeight2;
                }
                maxHeight = maxHeight6;
            } else {
                maxHeight = maxHeight6;
            }
        } else {
            maxHeight = maxHeight6;
            widthMode = widthMode6;
        }
        this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
        int largestChildWidth7 = largestChildWidth6;
        int widthSizeAndState3 = resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, 0);
        int widthSize2 = widthSizeAndState3 & 16777215;
        int remainingExcess6 = (widthSize2 - this.mTotalLength) + (this.mAllowInconsistentMeasurement ? 0 : usedExcessSpace);
        if (skippedMeasure) {
            totalWeight = totalWeight3;
            remainingExcess = remainingExcess6;
        } else if ((!sRemeasureWeightedChildren && remainingExcess6 == 0) || totalWeight3 <= 0.0f) {
            int alternativeMaxHeight6 = Math.max(i6, widthMode7);
            if (useLargestChild && widthMode != 1073741824) {
                int i13 = 0;
                while (true) {
                    int i14 = i13;
                    if (i14 >= count3) {
                        break;
                    }
                    int alternativeMaxHeight7 = alternativeMaxHeight6;
                    View child3 = getVirtualChildAt(i14);
                    if (child3 != null) {
                        widthSize = widthSize2;
                        totalWeight2 = totalWeight3;
                        if (child3.getVisibility() == 8) {
                            remainingExcess5 = remainingExcess6;
                        } else {
                            float childExtra = ((LayoutParams) child3.getLayoutParams()).weight;
                            if (childExtra > 0.0f) {
                                remainingExcess5 = remainingExcess6;
                                child3.measure(View.MeasureSpec.makeMeasureSpec(largestChildWidth7, 1073741824), View.MeasureSpec.makeMeasureSpec(child3.getMeasuredHeight(), 1073741824));
                            } else {
                                remainingExcess5 = remainingExcess6;
                            }
                        }
                    } else {
                        widthSize = widthSize2;
                        totalWeight2 = totalWeight3;
                        remainingExcess5 = remainingExcess6;
                    }
                    i13 = i14 + 1;
                    alternativeMaxHeight6 = alternativeMaxHeight7;
                    widthSize2 = widthSize;
                    totalWeight3 = totalWeight2;
                    remainingExcess6 = remainingExcess5;
                }
            }
            alternativeMaxHeight = alternativeMaxHeight6;
            widthSizeAndState = widthSizeAndState3;
            count = count3;
            remainingExcess2 = heightMeasureSpec;
            if (largestChildWidth2 == 0 && heightMode != 1073741824) {
                maxHeight = alternativeMaxHeight;
            }
            setMeasuredDimension(widthSizeAndState | (childState & (-16777216)), resolveSizeAndState(Math.max(maxHeight + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), remainingExcess2, childState << 16));
            if (matchHeight) {
                return;
            }
            forceUniformHeight(count, widthMeasureSpec);
            return;
        } else {
            totalWeight = totalWeight3;
            remainingExcess = remainingExcess6;
        }
        float remainingWeightSum2 = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        int maxHeight8 = -1;
        this.mTotalLength = 0;
        int childState7 = childState;
        int remainingExcess7 = remainingExcess;
        int alternativeMaxHeight8 = i6;
        int alternativeMaxHeight9 = 0;
        while (alternativeMaxHeight9 < count3) {
            boolean useLargestChild2 = useLargestChild;
            View child4 = getVirtualChildAt(alternativeMaxHeight9);
            if (child4 != null) {
                count2 = count3;
                if (child4.getVisibility() == 8) {
                    widthMode2 = widthMode;
                    largestChildWidth = largestChildWidth7;
                    remainingExcess3 = remainingExcess7;
                    widthSizeAndState2 = widthSizeAndState3;
                } else {
                    LayoutParams lp4 = (LayoutParams) child4.getLayoutParams();
                    float childWeight = lp4.weight;
                    if (childWeight > 0.0f) {
                        widthSizeAndState2 = widthSizeAndState3;
                        int share2 = (int) ((remainingExcess7 * childWeight) / remainingWeightSum2);
                        int remainingExcess8 = remainingExcess7 - share2;
                        remainingWeightSum = remainingWeightSum2 - childWeight;
                        int childWidth2 = (!this.mUseLargestChild || widthMode == 1073741824) ? (lp4.width != 0 || (this.mAllowInconsistentMeasurement && widthMode != 1073741824)) ? child4.getMeasuredWidth() + share2 : share2 : largestChildWidth7;
                        widthMode2 = widthMode;
                        largestChildWidth = largestChildWidth7;
                        int largestChildWidth8 = Math.max(0, childWidth2);
                        int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(largestChildWidth8, 1073741824);
                        int i15 = this.mPaddingTop;
                        int childWidth3 = this.mPaddingBottom;
                        remainingExcess4 = remainingExcess8;
                        share = 1073741824;
                        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, i15 + childWidth3 + lp4.topMargin + lp4.bottomMargin, lp4.height);
                        child4.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                        childState7 = combineMeasuredStates(childState7, child4.getMeasuredState() & (-16777216));
                    } else {
                        widthMode2 = widthMode;
                        largestChildWidth = largestChildWidth7;
                        widthSizeAndState2 = widthSizeAndState3;
                        share = 1073741824;
                        remainingWeightSum = remainingWeightSum2;
                        remainingExcess4 = remainingExcess7;
                    }
                    if (isExactly) {
                        this.mTotalLength += child4.getMeasuredWidth() + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child4);
                    } else {
                        int totalLength2 = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength2, child4.getMeasuredWidth() + totalLength2 + lp4.leftMargin + lp4.rightMargin + getNextLocationOffset(child4));
                    }
                    boolean matchHeightLocally2 = heightMode != share && lp4.height == -1;
                    int margin3 = lp4.topMargin + lp4.bottomMargin;
                    int childHeight2 = child4.getMeasuredHeight() + margin3;
                    maxHeight8 = Math.max(maxHeight8, childHeight2);
                    int alternativeMaxHeight10 = Math.max(alternativeMaxHeight8, matchHeightLocally2 ? margin3 : childHeight2);
                    if (largestChildWidth2 != 0 && lp4.height == -1) {
                        allFillParent = 1;
                        if (baselineAligned3) {
                            allFillParent2 = allFillParent;
                        } else {
                            int childBaseline2 = child4.getBaseline();
                            allFillParent2 = allFillParent;
                            if (childBaseline2 != -1) {
                                int gravity2 = (lp4.gravity < 0 ? this.mGravity : lp4.gravity) & 112;
                                int index2 = ((gravity2 >> 4) & (-2)) >> 1;
                                int gravity3 = maxAscent[index2];
                                maxAscent[index2] = Math.max(gravity3, childBaseline2);
                                maxDescent[index2] = Math.max(maxDescent[index2], childHeight2 - childBaseline2);
                            }
                        }
                        alternativeMaxHeight8 = alternativeMaxHeight10;
                        remainingWeightSum2 = remainingWeightSum;
                        largestChildWidth2 = allFillParent2;
                        alternativeMaxHeight9++;
                        useLargestChild = useLargestChild2;
                        count3 = count2;
                        widthSizeAndState3 = widthSizeAndState2;
                        widthMode = widthMode2;
                        largestChildWidth7 = largestChildWidth;
                        remainingExcess7 = remainingExcess4;
                    }
                    allFillParent = 0;
                    if (baselineAligned3) {
                    }
                    alternativeMaxHeight8 = alternativeMaxHeight10;
                    remainingWeightSum2 = remainingWeightSum;
                    largestChildWidth2 = allFillParent2;
                    alternativeMaxHeight9++;
                    useLargestChild = useLargestChild2;
                    count3 = count2;
                    widthSizeAndState3 = widthSizeAndState2;
                    widthMode = widthMode2;
                    largestChildWidth7 = largestChildWidth;
                    remainingExcess7 = remainingExcess4;
                }
            } else {
                widthMode2 = widthMode;
                largestChildWidth = largestChildWidth7;
                remainingExcess3 = remainingExcess7;
                widthSizeAndState2 = widthSizeAndState3;
                count2 = count3;
            }
            remainingExcess4 = remainingExcess3;
            alternativeMaxHeight9++;
            useLargestChild = useLargestChild2;
            count3 = count2;
            widthSizeAndState3 = widthSizeAndState2;
            widthMode = widthMode2;
            largestChildWidth7 = largestChildWidth;
            remainingExcess7 = remainingExcess4;
        }
        widthSizeAndState = widthSizeAndState3;
        count = count3;
        remainingExcess2 = heightMeasureSpec;
        this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
        if (maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1) {
            descent = maxHeight8;
        } else {
            int ascent2 = Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2])));
            int descent3 = Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2])));
            descent = Math.max(maxHeight8, ascent2 + descent3);
        }
        maxHeight = descent;
        childState = childState7;
        alternativeMaxHeight = alternativeMaxHeight8;
        if (largestChildWidth2 == 0) {
            maxHeight = alternativeMaxHeight;
        }
        setMeasuredDimension(widthSizeAndState | (childState & (-16777216)), resolveSizeAndState(Math.max(maxHeight + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), remainingExcess2, childState << 16));
        if (matchHeight) {
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8) {
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
        int paddingLeft2 = this.mPaddingLeft;
        int width = right - left;
        int childRight = width - this.mPaddingRight;
        int childSpace = (width - paddingLeft2) - this.mPaddingRight;
        int count = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 112;
        int minorGravity = this.mGravity & 8388615;
        if (majorGravity2 == 16) {
            int childTop2 = this.mPaddingTop;
            childTop = childTop2 + (((bottom - top) - this.mTotalLength) / 2);
        } else {
            childTop = majorGravity2 != 80 ? this.mPaddingTop : ((this.mPaddingTop + bottom) - top) - this.mTotalLength;
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
                int layoutDirection = getLayoutDirection();
                int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
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

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (layoutDirection != this.mLayoutDirection) {
            this.mLayoutDirection = layoutDirection;
            if (this.mOrientation == 0) {
                requestLayout();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0102  */
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
        boolean isLayoutRtl2 = isLayoutRtl();
        int paddingTop = this.mPaddingTop;
        int height = bottom - top;
        int childBottom = height - this.mPaddingBottom;
        int childSpace = (height - paddingTop) - this.mPaddingBottom;
        int count2 = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 8388615;
        int minorGravity = this.mGravity & 112;
        boolean baselineAligned2 = this.mBaselineAligned;
        int[] maxAscent2 = this.mMaxAscent;
        int[] maxDescent2 = this.mMaxDescent;
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(majorGravity2, layoutDirection);
        if (absoluteGravity == 1) {
            int childLeft2 = this.mPaddingLeft;
            int layoutDirection2 = this.mTotalLength;
            childLeft = childLeft2 + (((right - left) - layoutDirection2) / 2);
        } else if (absoluteGravity == 5) {
            int childLeft3 = this.mPaddingLeft;
            childLeft = ((childLeft3 + right) - left) - this.mTotalLength;
        } else {
            childLeft = this.mPaddingLeft;
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

    @RemotableViewMethod
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

    @RemotableViewMethod
    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & 8388615;
        if ((8388615 & this.mGravity) != gravity) {
            this.mGravity = (this.mGravity & (-8388616)) | gravity;
            requestLayout();
        }
    }

    @RemotableViewMethod
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
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (lp instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) lp);
            }
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
            }
        }
        return new LayoutParams(lp);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return LinearLayout.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder encoder) {
        super.encodeProperties(encoder);
        encoder.addProperty("layout:baselineAligned", this.mBaselineAligned);
        encoder.addProperty("layout:baselineAlignedChildIndex", this.mBaselineAlignedChildIndex);
        encoder.addProperty("measurement:baselineChildTop", this.mBaselineChildTop);
        encoder.addProperty("measurement:orientation", this.mOrientation);
        encoder.addProperty("measurement:gravity", this.mGravity);
        encoder.addProperty("measurement:totalLength", this.mTotalLength);
        encoder.addProperty("layout:totalLength", this.mTotalLength);
        encoder.addProperty("layout:useLargestChild", this.mUseLargestChild);
    }
}
