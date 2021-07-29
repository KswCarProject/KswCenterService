package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.slice.Slice;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.IntFunction;

@RemoteViews.RemoteView
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
    @ViewDebug.ExportedProperty(category = "layout")
    private boolean mBaselineAligned;
    @ViewDebug.ExportedProperty(category = "layout")
    private int mBaselineAlignedChildIndex;
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mBaselineChildTop;
    @UnsupportedAppUsage
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = -1, mask = -1, name = "NONE"), @ViewDebug.FlagToString(equals = 0, mask = 0, name = "NONE"), @ViewDebug.FlagToString(equals = 48, mask = 48, name = "TOP"), @ViewDebug.FlagToString(equals = 80, mask = 80, name = "BOTTOM"), @ViewDebug.FlagToString(equals = 3, mask = 3, name = "LEFT"), @ViewDebug.FlagToString(equals = 5, mask = 5, name = "RIGHT"), @ViewDebug.FlagToString(equals = 8388611, mask = 8388611, name = "START"), @ViewDebug.FlagToString(equals = 8388613, mask = 8388613, name = "END"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "CENTER_VERTICAL"), @ViewDebug.FlagToString(equals = 112, mask = 112, name = "FILL_VERTICAL"), @ViewDebug.FlagToString(equals = 1, mask = 1, name = "CENTER_HORIZONTAL"), @ViewDebug.FlagToString(equals = 7, mask = 7, name = "FILL_HORIZONTAL"), @ViewDebug.FlagToString(equals = 17, mask = 17, name = "CENTER"), @ViewDebug.FlagToString(equals = 119, mask = 119, name = "FILL"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "RELATIVE")}, formatToHexString = true)
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private int mGravity;
    private int mLayoutDirection;
    @UnsupportedAppUsage
    private int[] mMaxAscent;
    @UnsupportedAppUsage
    private int[] mMaxDescent;
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mOrientation;
    private int mShowDividers;
    @ViewDebug.ExportedProperty(category = "measurement")
    @UnsupportedAppUsage
    private int mTotalLength;
    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage
    private boolean mUseLargestChild;
    @ViewDebug.ExportedProperty(category = "layout")
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category = "layout", mapping = {@ViewDebug.IntToString(from = -1, to = "NONE"), @ViewDebug.IntToString(from = 0, to = "NONE"), @ViewDebug.IntToString(from = 48, to = "TOP"), @ViewDebug.IntToString(from = 80, to = "BOTTOM"), @ViewDebug.IntToString(from = 3, to = "LEFT"), @ViewDebug.IntToString(from = 5, to = "RIGHT"), @ViewDebug.IntToString(from = 8388611, to = "START"), @ViewDebug.IntToString(from = 8388613, to = "END"), @ViewDebug.IntToString(from = 16, to = "CENTER_VERTICAL"), @ViewDebug.IntToString(from = 112, to = "FILL_VERTICAL"), @ViewDebug.IntToString(from = 1, to = "CENTER_HORIZONTAL"), @ViewDebug.IntToString(from = 7, to = "FILL_HORIZONTAL"), @ViewDebug.IntToString(from = 17, to = "CENTER"), @ViewDebug.IntToString(from = 119, to = "FILL")})
        public int gravity;
        @ViewDebug.ExportedProperty(category = "layout")
        public float weight;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_gravityId;
            private int mLayout_weightId;
            private boolean mPropertiesMapped = false;

            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mLayout_weightId = propertyMapper.mapFloat("layout_weight", 16843137);
                this.mPropertiesMapped = true;
            }

            public void readProperties(LayoutParams node, PropertyReader propertyReader) {
                if (this.mPropertiesMapped) {
                    propertyReader.readGravity(this.mLayout_gravityId, node.gravity);
                    propertyReader.readFloat(this.mLayout_weightId, node.weight);
                    return;
                }
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLayout_Layout);
            this.weight = a.getFloat(3, 0.0f);
            this.gravity = a.getInt(0, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight2) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight2;
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

        public String debug(String output) {
            return output + "LinearLayout.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " weight=" + this.weight + "}";
        }

        /* access modifiers changed from: protected */
        @UnsupportedAppUsage
        public void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("layout:weight", this.weight);
            encoder.addProperty("layout:gravity", this.gravity);
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LinearLayout> {
        private int mBaselineAlignedChildIndexId;
        private int mBaselineAlignedId;
        private int mDividerId;
        private int mGravityId;
        private int mMeasureWithLargestChildId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mWeightSumId;

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
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mWeightSumId = propertyMapper.mapFloat("weightSum", 16843048);
            this.mPropertiesMapped = true;
        }

        public void readProperties(LinearLayout node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readBoolean(this.mBaselineAlignedId, node.isBaselineAligned());
                propertyReader.readInt(this.mBaselineAlignedChildIndexId, node.getBaselineAlignedChildIndex());
                propertyReader.readObject(this.mDividerId, node.getDividerDrawable());
                propertyReader.readGravity(this.mGravityId, node.getGravity());
                propertyReader.readBoolean(this.mMeasureWithLargestChildId, node.isMeasureWithLargestChildEnabled());
                propertyReader.readIntEnum(this.mOrientationId, node.getOrientation());
                propertyReader.readFloat(this.mWeightSumId, node.getWeightSum());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public LinearLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public LinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z = true;
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        if (!sCompatibilityDone && context != null) {
            sRemeasureWeightedChildren = context.getApplicationInfo().targetSdkVersion >= 28;
            sCompatibilityDone = true;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinearLayout, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.LinearLayout, attrs, a, defStyleAttr, defStyleRes);
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
        this.mAllowInconsistentMeasurement = context.getApplicationInfo().targetSdkVersion > 23 ? false : z;
        a.recycle();
    }

    private boolean isShowingDividers() {
        return (this.mShowDividers == 0 || this.mDivider == null) ? false : true;
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            this.mShowDividers = showDividers;
            setWillNotDraw(!isShowingDividers());
            requestLayout();
        }
    }

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
        if (divider != this.mDivider) {
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
    }

    public void setDividerPadding(int padding) {
        if (padding != this.mDividerPadding) {
            this.mDividerPadding = padding;
            if (isShowingDividers()) {
                requestLayout();
                invalidate();
            }
        }
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                drawHorizontalDivider(canvas, (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getLastNonGoneChild();
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = child2.getBottom() + ((LayoutParams) child2.getLayoutParams()).bottomMargin;
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

    /* access modifiers changed from: package-private */
    public void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = isLayoutRtl();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    position2 = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getLastNonGoneChild();
            if (child2 != null) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    position = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                } else {
                    position = child2.getRight() + lp2.rightMargin;
                }
            } else if (isLayoutRtl) {
                position = getPaddingLeft();
            } else {
                position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, position);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    /* access modifiers changed from: package-private */
    public void drawVerticalDivider(Canvas canvas, int left) {
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

    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() > this.mBaselineAlignedChildIndex) {
            View child = getChildAt(this.mBaselineAlignedChildIndex);
            int childBaseline = child.getBaseline();
            if (childBaseline != -1) {
                int childTop = this.mBaselineChildTop;
                if (this.mOrientation == 1 && (majorGravity = this.mGravity & 112) != 48) {
                    if (majorGravity == 16) {
                        childTop += ((((this.mBottom - this.mTop) - this.mPaddingTop) - this.mPaddingBottom) - this.mTotalLength) / 2;
                    } else if (majorGravity == 80) {
                        childTop = ((this.mBottom - this.mTop) - this.mPaddingBottom) - this.mTotalLength;
                    }
                }
                return ((LayoutParams) child.getLayoutParams()).topMargin + childTop + childBaseline;
            } else if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
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

    /* access modifiers changed from: package-private */
    public View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    /* access modifiers changed from: package-private */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @RemotableViewMethod
    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == getVirtualChildCount()) {
            if ((this.mShowDividers & 4) != 0) {
                return true;
            }
            return false;
        } else if (allViewsAreGoneBefore(childIndex)) {
            if ((this.mShowDividers & 1) != 0) {
                return true;
            }
            return false;
        } else if ((this.mShowDividers & 2) != 0) {
            return true;
        } else {
            return false;
        }
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

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x03c2  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x03c5  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x03cc  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x03d6  */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x044a  */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0450  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x019d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureVertical(int r62, int r63) {
        /*
            r61 = this;
            r7 = r61
            r8 = r62
            r9 = r63
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r61.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r62)
            int r13 = android.view.View.MeasureSpec.getMode(r63)
            r6 = 0
            r14 = 0
            int r15 = r7.mBaselineAlignedChildIndex
            boolean r10 = r7.mUseLargestChild
            r17 = -2147483648(0xffffffff80000000, float:-0.0)
            r18 = 0
            r19 = 0
            r20 = r6
            r6 = r1
            r1 = r0
            r0 = 0
            r59 = r3
            r3 = r2
            r2 = r59
            r60 = r17
            r17 = r4
            r4 = r60
        L_0x0037:
            r21 = r4
            r23 = 0
            r25 = 1
            if (r0 >= r11) goto L_0x01c6
            android.view.View r4 = r7.getVirtualChildAt(r0)
            if (r4 != 0) goto L_0x005a
            r27 = r1
            int r1 = r7.mTotalLength
            int r22 = r7.measureNullChild(r0)
            int r1 = r1 + r22
            r7.mTotalLength = r1
            r36 = r13
            r4 = r21
            r1 = r27
            goto L_0x01bc
        L_0x005a:
            r27 = r1
            int r1 = r4.getVisibility()
            r28 = r2
            r2 = 8
            if (r1 != r2) goto L_0x0076
            int r1 = r7.getChildrenSkipCount(r4, r0)
            int r0 = r0 + r1
            r36 = r13
            r4 = r21
            r1 = r27
            r2 = r28
            goto L_0x01bc
        L_0x0076:
            int r19 = r19 + 1
            boolean r1 = r7.hasDividerBeforeChildAt(r0)
            if (r1 == 0) goto L_0x0085
            int r1 = r7.mTotalLength
            int r2 = r7.mDividerHeight
            int r1 = r1 + r2
            r7.mTotalLength = r1
        L_0x0085:
            android.view.ViewGroup$LayoutParams r1 = r4.getLayoutParams()
            r2 = r1
            android.widget.LinearLayout$LayoutParams r2 = (android.widget.LinearLayout.LayoutParams) r2
            float r1 = r2.weight
            float r24 = r5 + r1
            int r1 = r2.height
            if (r1 != 0) goto L_0x009d
            float r1 = r2.weight
            int r1 = (r1 > r23 ? 1 : (r1 == r23 ? 0 : -1))
            if (r1 <= 0) goto L_0x009d
            r1 = r25
            goto L_0x009e
        L_0x009d:
            r1 = 0
        L_0x009e:
            r29 = r1
            r5 = 1073741824(0x40000000, float:2.0)
            if (r13 != r5) goto L_0x00cc
            if (r29 == 0) goto L_0x00cc
            int r1 = r7.mTotalLength
            int r5 = r2.topMargin
            int r5 = r5 + r1
            r30 = r0
            int r0 = r2.bottomMargin
            int r5 = r5 + r0
            int r0 = java.lang.Math.max(r1, r5)
            r7.mTotalLength = r0
            r14 = 1
            r1 = r2
            r37 = r6
            r36 = r13
            r34 = r14
            r0 = r21
            r8 = r27
            r33 = r28
            r31 = r30
            r13 = 1073741824(0x40000000, float:2.0)
            r14 = r3
            goto L_0x0133
        L_0x00cc:
            r30 = r0
            if (r29 == 0) goto L_0x00d3
            r0 = -2
            r2.height = r0
        L_0x00d3:
            int r0 = (r24 > r23 ? 1 : (r24 == r23 ? 0 : -1))
            if (r0 != 0) goto L_0x00da
            int r0 = r7.mTotalLength
            goto L_0x00db
        L_0x00da:
            r0 = 0
        L_0x00db:
            r5 = r6
            r6 = r0
            r26 = 0
            r1 = r30
            r0 = r61
            r31 = r1
            r8 = r27
            r1 = r4
            r32 = r2
            r33 = r28
            r2 = r31
            r34 = r14
            r14 = r3
            r3 = r62
            r35 = r4
            r36 = r13
            r9 = r21
            r13 = 1073741824(0x40000000, float:2.0)
            r4 = r26
            r37 = r5
            r5 = r63
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            int r0 = r35.getMeasuredHeight()
            if (r29 == 0) goto L_0x0112
            r1 = r32
            r2 = 0
            r1.height = r2
            int r18 = r18 + r0
            goto L_0x0114
        L_0x0112:
            r1 = r32
        L_0x0114:
            int r2 = r7.mTotalLength
            int r3 = r2 + r0
            int r4 = r1.topMargin
            int r3 = r3 + r4
            int r4 = r1.bottomMargin
            int r3 = r3 + r4
            r4 = r35
            int r5 = r7.getNextLocationOffset(r4)
            int r3 = r3 + r5
            int r3 = java.lang.Math.max(r2, r3)
            r7.mTotalLength = r3
            if (r10 == 0) goto L_0x0132
            int r0 = java.lang.Math.max(r0, r9)
            goto L_0x0133
        L_0x0132:
            r0 = r9
        L_0x0133:
            if (r15 < 0) goto L_0x0140
            r2 = r31
            int r3 = r2 + 1
            if (r15 != r3) goto L_0x0142
            int r3 = r7.mTotalLength
            r7.mBaselineChildTop = r3
            goto L_0x0142
        L_0x0140:
            r2 = r31
        L_0x0142:
            if (r2 >= r15) goto L_0x0153
            float r3 = r1.weight
            int r3 = (r3 > r23 ? 1 : (r3 == r23 ? 0 : -1))
            if (r3 > 0) goto L_0x014b
            goto L_0x0153
        L_0x014b:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r5 = "A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex."
            r3.<init>(r5)
            throw r3
        L_0x0153:
            r3 = 0
            if (r12 == r13) goto L_0x015f
            int r5 = r1.width
            r6 = -1
            if (r5 != r6) goto L_0x0160
            r20 = 1
            r3 = 1
            goto L_0x0160
        L_0x015f:
            r6 = -1
        L_0x0160:
            int r5 = r1.leftMargin
            int r9 = r1.rightMargin
            int r5 = r5 + r9
            int r9 = r4.getMeasuredWidth()
            int r9 = r9 + r5
            int r8 = java.lang.Math.max(r8, r9)
            int r13 = r4.getMeasuredState()
            r6 = r37
            int r6 = combineMeasuredStates(r6, r13)
            if (r17 == 0) goto L_0x0184
            int r13 = r1.width
            r38 = r0
            r0 = -1
            if (r13 != r0) goto L_0x0186
            r0 = r25
            goto L_0x0187
        L_0x0184:
            r38 = r0
        L_0x0186:
            r0 = 0
        L_0x0187:
            float r13 = r1.weight
            int r13 = (r13 > r23 ? 1 : (r13 == r23 ? 0 : -1))
            if (r13 <= 0) goto L_0x019d
            if (r3 == 0) goto L_0x0192
            r13 = r5
            goto L_0x0193
        L_0x0192:
            r13 = r9
        L_0x0193:
            r39 = r0
            r0 = r33
            int r0 = java.lang.Math.max(r0, r13)
            r13 = r14
            goto L_0x01aa
        L_0x019d:
            r39 = r0
            r0 = r33
            if (r3 == 0) goto L_0x01a5
            r13 = r5
            goto L_0x01a6
        L_0x01a5:
            r13 = r9
        L_0x01a6:
            int r13 = java.lang.Math.max(r14, r13)
        L_0x01aa:
            int r14 = r7.getChildrenSkipCount(r4, r2)
            int r1 = r2 + r14
            r2 = r0
            r0 = r1
            r1 = r8
            r3 = r13
            r5 = r24
            r14 = r34
            r4 = r38
            r17 = r39
        L_0x01bc:
            int r0 = r0 + 1
            r13 = r36
            r8 = r62
            r9 = r63
            goto L_0x0037
        L_0x01c6:
            r8 = r1
            r0 = r2
            r36 = r13
            r34 = r14
            r9 = r21
            r13 = 1073741824(0x40000000, float:2.0)
            r14 = r3
            if (r19 <= 0) goto L_0x01e0
            boolean r1 = r7.hasDividerBeforeChildAt(r11)
            if (r1 == 0) goto L_0x01e0
            int r1 = r7.mTotalLength
            int r2 = r7.mDividerHeight
            int r1 = r1 + r2
            r7.mTotalLength = r1
        L_0x01e0:
            if (r10 == 0) goto L_0x023a
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r36
            if (r2 == r1) goto L_0x01ea
            if (r2 != 0) goto L_0x023c
        L_0x01ea:
            r1 = 0
            r7.mTotalLength = r1
            r1 = 0
        L_0x01ee:
            if (r1 >= r11) goto L_0x023c
            android.view.View r3 = r7.getVirtualChildAt(r1)
            if (r3 != 0) goto L_0x0204
            int r4 = r7.mTotalLength
            int r21 = r7.measureNullChild(r1)
            int r4 = r4 + r21
            r7.mTotalLength = r4
            r40 = r1
            goto L_0x0233
        L_0x0204:
            int r4 = r3.getVisibility()
            r13 = 8
            if (r4 != r13) goto L_0x0212
            int r4 = r7.getChildrenSkipCount(r3, r1)
            int r1 = r1 + r4
            goto L_0x0235
        L_0x0212:
            android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r4 = (android.widget.LinearLayout.LayoutParams) r4
            int r13 = r7.mTotalLength
            int r21 = r13 + r9
            r40 = r1
            int r1 = r4.topMargin
            int r21 = r21 + r1
            int r1 = r4.bottomMargin
            int r21 = r21 + r1
            int r1 = r7.getNextLocationOffset(r3)
            int r1 = r21 + r1
            int r1 = java.lang.Math.max(r13, r1)
            r7.mTotalLength = r1
        L_0x0233:
            r1 = r40
        L_0x0235:
            int r1 = r1 + 1
            r13 = 1073741824(0x40000000, float:2.0)
            goto L_0x01ee
        L_0x023a:
            r2 = r36
        L_0x023c:
            int r1 = r7.mTotalLength
            int r3 = r7.mPaddingTop
            int r4 = r7.mPaddingBottom
            int r3 = r3 + r4
            int r1 = r1 + r3
            r7.mTotalLength = r1
            int r1 = r7.mTotalLength
            int r3 = r61.getSuggestedMinimumHeight()
            int r1 = java.lang.Math.max(r1, r3)
            r4 = r9
            r3 = r63
            r9 = 0
            int r13 = resolveSizeAndState(r1, r3, r9)
            r9 = 16777215(0xffffff, float:2.3509886E-38)
            r1 = r13 & r9
            int r9 = r7.mTotalLength
            int r9 = r1 - r9
            r41 = r1
            boolean r1 = r7.mAllowInconsistentMeasurement
            if (r1 == 0) goto L_0x0269
            r1 = 0
            goto L_0x026b
        L_0x0269:
            r1 = r18
        L_0x026b:
            int r9 = r9 + r1
            if (r34 != 0) goto L_0x02fa
            boolean r1 = sRemeasureWeightedChildren
            if (r1 != 0) goto L_0x0274
            if (r9 == 0) goto L_0x0280
        L_0x0274:
            int r1 = (r5 > r23 ? 1 : (r5 == r23 ? 0 : -1))
            if (r1 <= 0) goto L_0x0280
            r42 = r0
            r44 = r5
            r47 = r6
            goto L_0x0300
        L_0x0280:
            int r1 = java.lang.Math.max(r14, r0)
            if (r10 == 0) goto L_0x02e2
            r14 = 1073741824(0x40000000, float:2.0)
            if (r2 == r14) goto L_0x02e2
            r16 = 0
        L_0x028c:
            r14 = r16
            if (r14 >= r11) goto L_0x02e2
            r42 = r0
            android.view.View r0 = r7.getVirtualChildAt(r14)
            if (r0 == 0) goto L_0x02d1
            r43 = r1
            int r1 = r0.getVisibility()
            r44 = r5
            r5 = 8
            if (r1 != r5) goto L_0x02a8
            r47 = r6
            goto L_0x02d7
        L_0x02a8:
            android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r1 = (android.widget.LinearLayout.LayoutParams) r1
            float r5 = r1.weight
            int r16 = (r5 > r23 ? 1 : (r5 == r23 ? 0 : -1))
            if (r16 <= 0) goto L_0x02ce
            r45 = r1
            int r1 = r0.getMeasuredWidth()
            r46 = r5
            r5 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r5)
            r47 = r6
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r5)
            r0.measure(r1, r6)
            goto L_0x02d7
        L_0x02ce:
            r47 = r6
            goto L_0x02d7
        L_0x02d1:
            r43 = r1
            r44 = r5
            r47 = r6
        L_0x02d7:
            int r16 = r14 + 1
            r0 = r42
            r1 = r43
            r5 = r44
            r6 = r47
            goto L_0x028c
        L_0x02e2:
            r42 = r0
            r43 = r1
            r44 = r5
            r47 = r6
            r51 = r2
            r54 = r4
            r48 = r10
            r49 = r15
            r14 = r43
            r6 = r47
            r4 = r62
            goto L_0x042c
        L_0x02fa:
            r42 = r0
            r44 = r5
            r47 = r6
        L_0x0300:
            float r0 = r7.mWeightSum
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 <= 0) goto L_0x0309
            float r5 = r7.mWeightSum
            goto L_0x030b
        L_0x0309:
            r5 = r44
        L_0x030b:
            r0 = r5
            r1 = 0
            r7.mTotalLength = r1
            r1 = r0
            r6 = r47
            r0 = 0
        L_0x0313:
            if (r0 >= r11) goto L_0x0417
            android.view.View r5 = r7.getVirtualChildAt(r0)
            if (r5 == 0) goto L_0x03ff
            r48 = r10
            int r10 = r5.getVisibility()
            r49 = r15
            r15 = 8
            if (r10 != r15) goto L_0x0330
            r51 = r2
            r54 = r4
            r4 = r62
            goto L_0x0409
        L_0x0330:
            android.view.ViewGroup$LayoutParams r10 = r5.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r10 = (android.widget.LinearLayout.LayoutParams) r10
            float r15 = r10.weight
            int r21 = (r15 > r23 ? 1 : (r15 == r23 ? 0 : -1))
            if (r21 <= 0) goto L_0x0398
            float r3 = (float) r9
            float r3 = r3 * r15
            float r3 = r3 / r1
            int r3 = (int) r3
            int r9 = r9 - r3
            float r1 = r1 - r15
            r50 = r1
            boolean r1 = r7.mUseLargestChild
            if (r1 == 0) goto L_0x034e
            r1 = 1073741824(0x40000000, float:2.0)
            if (r2 == r1) goto L_0x034e
            r1 = r4
            goto L_0x0361
        L_0x034e:
            int r1 = r10.height
            if (r1 != 0) goto L_0x035c
            boolean r1 = r7.mAllowInconsistentMeasurement
            if (r1 == 0) goto L_0x035a
            r1 = 1073741824(0x40000000, float:2.0)
            if (r2 != r1) goto L_0x035c
        L_0x035a:
            r1 = r3
            goto L_0x0361
        L_0x035c:
            int r1 = r5.getMeasuredHeight()
            int r1 = r1 + r3
        L_0x0361:
            r51 = r2
            r52 = r3
            r2 = 0
            int r3 = java.lang.Math.max(r2, r1)
            r2 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
            int r2 = r7.mPaddingLeft
            r53 = r1
            int r1 = r7.mPaddingRight
            int r2 = r2 + r1
            int r1 = r10.leftMargin
            int r2 = r2 + r1
            int r1 = r10.rightMargin
            int r2 = r2 + r1
            int r1 = r10.width
            r54 = r4
            r4 = r62
            int r1 = getChildMeasureSpec(r4, r2, r1)
            r5.measure(r1, r3)
            int r2 = r5.getMeasuredState()
            r2 = r2 & -256(0xffffffffffffff00, float:NaN)
            int r6 = combineMeasuredStates(r6, r2)
            r1 = r50
            goto L_0x039e
        L_0x0398:
            r51 = r2
            r54 = r4
            r4 = r62
        L_0x039e:
            int r2 = r10.leftMargin
            int r3 = r10.rightMargin
            int r2 = r2 + r3
            int r3 = r5.getMeasuredWidth()
            int r3 = r3 + r2
            int r8 = java.lang.Math.max(r8, r3)
            r55 = r1
            r1 = 1073741824(0x40000000, float:2.0)
            if (r12 == r1) goto L_0x03bc
            int r1 = r10.width
            r56 = r2
            r2 = -1
            if (r1 != r2) goto L_0x03be
            r1 = r25
            goto L_0x03bf
        L_0x03bc:
            r56 = r2
        L_0x03be:
            r1 = 0
        L_0x03bf:
            if (r1 == 0) goto L_0x03c5
            r2 = r56
            goto L_0x03c6
        L_0x03c5:
            r2 = r3
        L_0x03c6:
            int r2 = java.lang.Math.max(r14, r2)
            if (r17 == 0) goto L_0x03d6
            int r14 = r10.width
            r57 = r1
            r1 = -1
            if (r14 != r1) goto L_0x03d9
            r14 = r25
            goto L_0x03da
        L_0x03d6:
            r57 = r1
            r1 = -1
        L_0x03d9:
            r14 = 0
        L_0x03da:
            int r1 = r7.mTotalLength
            int r16 = r5.getMeasuredHeight()
            int r16 = r1 + r16
            r58 = r2
            int r2 = r10.topMargin
            int r16 = r16 + r2
            int r2 = r10.bottomMargin
            int r16 = r16 + r2
            int r2 = r7.getNextLocationOffset(r5)
            int r2 = r16 + r2
            int r2 = java.lang.Math.max(r1, r2)
            r7.mTotalLength = r2
            r17 = r14
            r1 = r55
            r14 = r58
            goto L_0x0409
        L_0x03ff:
            r51 = r2
            r54 = r4
            r48 = r10
            r49 = r15
            r4 = r62
        L_0x0409:
            int r0 = r0 + 1
            r10 = r48
            r15 = r49
            r2 = r51
            r4 = r54
            r3 = r63
            goto L_0x0313
        L_0x0417:
            r51 = r2
            r54 = r4
            r48 = r10
            r49 = r15
            r4 = r62
            int r0 = r7.mTotalLength
            int r2 = r7.mPaddingTop
            int r3 = r7.mPaddingBottom
            int r2 = r2 + r3
            int r0 = r0 + r2
            r7.mTotalLength = r0
        L_0x042c:
            if (r17 != 0) goto L_0x0433
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 == r0) goto L_0x0433
            r8 = r14
        L_0x0433:
            int r0 = r7.mPaddingLeft
            int r1 = r7.mPaddingRight
            int r0 = r0 + r1
            int r8 = r8 + r0
            int r0 = r61.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r8, r0)
            int r1 = resolveSizeAndState(r0, r4, r6)
            r7.setMeasuredDimension(r1, r13)
            if (r20 == 0) goto L_0x0450
            r1 = r63
            r7.forceUniformWidth(r11, r1)
            goto L_0x0452
        L_0x0450:
            r1 = r63
        L_0x0452:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.measureVertical(int, int):void");
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8)) {
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

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x050d  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x0543  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x05fa  */
    /* JADX WARNING: Removed duplicated region for block: B:235:0x0602  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureHorizontal(int r69, int r70) {
        /*
            r68 = this;
            r7 = r68
            r8 = r69
            r9 = r70
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r68.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r69)
            int r13 = android.view.View.MeasureSpec.getMode(r70)
            r6 = 0
            r14 = 0
            int[] r15 = r7.mMaxAscent
            if (r15 == 0) goto L_0x0025
            int[] r15 = r7.mMaxDescent
            if (r15 != 0) goto L_0x002e
        L_0x0025:
            r15 = 4
            int[] r10 = new int[r15]
            r7.mMaxAscent = r10
            int[] r10 = new int[r15]
            r7.mMaxDescent = r10
        L_0x002e:
            int[] r10 = r7.mMaxAscent
            int[] r15 = r7.mMaxDescent
            r17 = 3
            r18 = r6
            r6 = -1
            r10[r17] = r6
            r19 = 2
            r10[r19] = r6
            r20 = 1
            r10[r20] = r6
            r16 = 0
            r10[r16] = r6
            r15[r17] = r6
            r15[r19] = r6
            r15[r20] = r6
            r15[r16] = r6
            boolean r6 = r7.mBaselineAligned
            r22 = r14
            boolean r14 = r7.mUseLargestChild
            r9 = 1073741824(0x40000000, float:2.0)
            if (r12 != r9) goto L_0x005a
            r23 = r20
            goto L_0x005c
        L_0x005a:
            r23 = 0
        L_0x005c:
            r24 = -2147483648(0xffffffff80000000, float:-0.0)
            r25 = 0
            r26 = 0
            r27 = r1
            r1 = r3
            r3 = r0
            r0 = 0
            r67 = r24
            r24 = r4
            r4 = r67
        L_0x006d:
            r29 = 0
            if (r0 >= r11) goto L_0x0240
            android.view.View r9 = r7.getVirtualChildAt(r0)
            if (r9 != 0) goto L_0x008c
            r31 = r1
            int r1 = r7.mTotalLength
            int r28 = r7.measureNullChild(r0)
            int r1 = r1 + r28
            r7.mTotalLength = r1
            r21 = r6
            r41 = r12
            r1 = r31
            goto L_0x0234
        L_0x008c:
            r31 = r1
            int r1 = r9.getVisibility()
            r32 = r2
            r2 = 8
            if (r1 != r2) goto L_0x00a8
            int r1 = r7.getChildrenSkipCount(r9, r0)
            int r0 = r0 + r1
            r21 = r6
            r41 = r12
            r1 = r31
            r2 = r32
            goto L_0x0234
        L_0x00a8:
            int r26 = r26 + 1
            boolean r1 = r7.hasDividerBeforeChildAt(r0)
            if (r1 == 0) goto L_0x00b7
            int r1 = r7.mTotalLength
            int r2 = r7.mDividerWidth
            int r1 = r1 + r2
            r7.mTotalLength = r1
        L_0x00b7:
            android.view.ViewGroup$LayoutParams r1 = r9.getLayoutParams()
            r2 = r1
            android.widget.LinearLayout$LayoutParams r2 = (android.widget.LinearLayout.LayoutParams) r2
            float r1 = r2.weight
            float r30 = r5 + r1
            int r1 = r2.width
            if (r1 != 0) goto L_0x00cf
            float r1 = r2.weight
            int r1 = (r1 > r29 ? 1 : (r1 == r29 ? 0 : -1))
            if (r1 <= 0) goto L_0x00cf
            r1 = r20
            goto L_0x00d0
        L_0x00cf:
            r1 = 0
        L_0x00d0:
            r33 = r1
            r1 = 1073741824(0x40000000, float:2.0)
            if (r12 != r1) goto L_0x0123
            if (r33 == 0) goto L_0x0123
            if (r23 == 0) goto L_0x00e7
            int r1 = r7.mTotalLength
            int r5 = r2.leftMargin
            r34 = r0
            int r0 = r2.rightMargin
            int r5 = r5 + r0
            int r1 = r1 + r5
            r7.mTotalLength = r1
            goto L_0x00f7
        L_0x00e7:
            r34 = r0
            int r0 = r7.mTotalLength
            int r1 = r2.leftMargin
            int r1 = r1 + r0
            int r5 = r2.rightMargin
            int r1 = r1 + r5
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
        L_0x00f7:
            if (r6 == 0) goto L_0x0120
            int r0 = android.view.View.MeasureSpec.getSize(r69)
            r1 = 0
            int r0 = android.view.View.MeasureSpec.makeSafeMeasureSpec(r0, r1)
            int r5 = android.view.View.MeasureSpec.getSize(r70)
            int r5 = android.view.View.MeasureSpec.makeSafeMeasureSpec(r5, r1)
            r9.measure(r0, r5)
        L_0x0110:
            r1 = r2
            r40 = r3
            r21 = r6
            r41 = r12
            r37 = r31
            r39 = r32
            r36 = r34
            r12 = -1
            goto L_0x0196
        L_0x0120:
            r22 = 1
            goto L_0x0110
        L_0x0123:
            r34 = r0
            if (r33 == 0) goto L_0x012a
            r0 = -2
            r2.width = r0
        L_0x012a:
            int r0 = (r30 > r29 ? 1 : (r30 == r29 ? 0 : -1))
            if (r0 != 0) goto L_0x0131
            int r0 = r7.mTotalLength
            goto L_0x0132
        L_0x0131:
            r0 = 0
        L_0x0132:
            r5 = r4
            r4 = r0
            r35 = 0
            r1 = r34
            r0 = r68
            r36 = r1
            r37 = r31
            r1 = r9
            r38 = r2
            r39 = r32
            r2 = r36
            r40 = r3
            r3 = r69
            r8 = r5
            r5 = r70
            r21 = r6
            r41 = r12
            r12 = -1
            r6 = r35
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            int r0 = r9.getMeasuredWidth()
            if (r33 == 0) goto L_0x0164
            r1 = r38
            r2 = 0
            r1.width = r2
            int r25 = r25 + r0
            goto L_0x0166
        L_0x0164:
            r1 = r38
        L_0x0166:
            if (r23 == 0) goto L_0x0179
            int r2 = r7.mTotalLength
            int r3 = r1.leftMargin
            int r3 = r3 + r0
            int r5 = r1.rightMargin
            int r3 = r3 + r5
            int r5 = r7.getNextLocationOffset(r9)
            int r3 = r3 + r5
            int r2 = r2 + r3
            r7.mTotalLength = r2
            goto L_0x018e
        L_0x0179:
            int r2 = r7.mTotalLength
            int r3 = r2 + r0
            int r5 = r1.leftMargin
            int r3 = r3 + r5
            int r5 = r1.rightMargin
            int r3 = r3 + r5
            int r5 = r7.getNextLocationOffset(r9)
            int r3 = r3 + r5
            int r3 = java.lang.Math.max(r2, r3)
            r7.mTotalLength = r3
        L_0x018e:
            if (r14 == 0) goto L_0x0195
            int r4 = java.lang.Math.max(r0, r8)
            goto L_0x0196
        L_0x0195:
            r4 = r8
        L_0x0196:
            r0 = 0
            r2 = 1073741824(0x40000000, float:2.0)
            if (r13 == r2) goto L_0x01a2
            int r2 = r1.height
            if (r2 != r12) goto L_0x01a2
            r18 = 1
            r0 = 1
        L_0x01a2:
            int r2 = r1.topMargin
            int r3 = r1.bottomMargin
            int r2 = r2 + r3
            int r3 = r9.getMeasuredHeight()
            int r3 = r3 + r2
            int r5 = r9.getMeasuredState()
            r6 = r27
            int r5 = combineMeasuredStates(r6, r5)
            if (r21 == 0) goto L_0x01e6
            int r6 = r9.getBaseline()
            if (r6 == r12) goto L_0x01e6
            int r8 = r1.gravity
            if (r8 >= 0) goto L_0x01c5
            int r8 = r7.mGravity
            goto L_0x01c7
        L_0x01c5:
            int r8 = r1.gravity
        L_0x01c7:
            r8 = r8 & 112(0x70, float:1.57E-43)
            int r27 = r8 >> 4
            r28 = -2
            r27 = r27 & -2
            int r27 = r27 >> 1
            r12 = r10[r27]
            int r12 = java.lang.Math.max(r12, r6)
            r10[r27] = r12
            r12 = r15[r27]
            r42 = r2
            int r2 = r3 - r6
            int r2 = java.lang.Math.max(r12, r2)
            r15[r27] = r2
            goto L_0x01e8
        L_0x01e6:
            r42 = r2
        L_0x01e8:
            r2 = r40
            int r2 = java.lang.Math.max(r2, r3)
            if (r24 == 0) goto L_0x01f8
            int r6 = r1.height
            r8 = -1
            if (r6 != r8) goto L_0x01f8
            r6 = r20
            goto L_0x01f9
        L_0x01f8:
            r6 = 0
        L_0x01f9:
            float r8 = r1.weight
            int r8 = (r8 > r29 ? 1 : (r8 == r29 ? 0 : -1))
            if (r8 <= 0) goto L_0x0211
            if (r0 == 0) goto L_0x0205
            r8 = r42
            goto L_0x0206
        L_0x0205:
            r8 = r3
        L_0x0206:
            r12 = r37
            int r8 = java.lang.Math.max(r12, r8)
            r43 = r0
            r0 = r39
            goto L_0x0222
        L_0x0211:
            r12 = r37
            if (r0 == 0) goto L_0x0218
            r8 = r42
            goto L_0x0219
        L_0x0218:
            r8 = r3
        L_0x0219:
            r43 = r0
            r0 = r39
            int r0 = java.lang.Math.max(r0, r8)
            r8 = r12
        L_0x0222:
            r12 = r36
            int r24 = r7.getChildrenSkipCount(r9, r12)
            int r1 = r12 + r24
            r3 = r2
            r27 = r5
            r24 = r6
            r5 = r30
            r2 = r0
            r0 = r1
            r1 = r8
        L_0x0234:
            int r0 = r0 + 1
            r6 = r21
            r12 = r41
            r8 = r69
            r9 = 1073741824(0x40000000, float:2.0)
            goto L_0x006d
        L_0x0240:
            r0 = r2
            r2 = r3
            r8 = r4
            r21 = r6
            r41 = r12
            r6 = r27
            r12 = r1
            if (r26 <= 0) goto L_0x0259
            boolean r1 = r7.hasDividerBeforeChildAt(r11)
            if (r1 == 0) goto L_0x0259
            int r1 = r7.mTotalLength
            int r3 = r7.mDividerWidth
            int r1 = r1 + r3
            r7.mTotalLength = r1
        L_0x0259:
            r1 = r10[r20]
            r3 = -1
            if (r1 != r3) goto L_0x026f
            r1 = 0
            r4 = r10[r1]
            if (r4 != r3) goto L_0x026f
            r1 = r10[r19]
            if (r1 != r3) goto L_0x026f
            r1 = r10[r17]
            if (r1 == r3) goto L_0x026c
            goto L_0x026f
        L_0x026c:
            r44 = r6
            goto L_0x02a2
        L_0x026f:
            r1 = r10[r17]
            r3 = 0
            r4 = r10[r3]
            r9 = r10[r20]
            r3 = r10[r19]
            int r3 = java.lang.Math.max(r9, r3)
            int r3 = java.lang.Math.max(r4, r3)
            int r1 = java.lang.Math.max(r1, r3)
            r3 = r15[r17]
            r4 = 0
            r9 = r15[r4]
            r4 = r15[r20]
            r44 = r6
            r6 = r15[r19]
            int r4 = java.lang.Math.max(r4, r6)
            int r4 = java.lang.Math.max(r9, r4)
            int r3 = java.lang.Math.max(r3, r4)
            int r4 = r1 + r3
            int r3 = java.lang.Math.max(r2, r4)
            r2 = r3
        L_0x02a2:
            if (r14 == 0) goto L_0x0319
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r41
            if (r3 == r1) goto L_0x02b1
            if (r3 != 0) goto L_0x02ad
            goto L_0x02b1
        L_0x02ad:
            r46 = r2
            goto L_0x031d
        L_0x02b1:
            r1 = 0
            r7.mTotalLength = r1
            r1 = 0
        L_0x02b5:
            if (r1 >= r11) goto L_0x0316
            android.view.View r4 = r7.getVirtualChildAt(r1)
            if (r4 != 0) goto L_0x02c7
            int r6 = r7.mTotalLength
            int r9 = r7.measureNullChild(r1)
            int r6 = r6 + r9
            r7.mTotalLength = r6
            goto L_0x02d5
        L_0x02c7:
            int r6 = r4.getVisibility()
            r9 = 8
            if (r6 != r9) goto L_0x02da
            int r6 = r7.getChildrenSkipCount(r4, r1)
            int r1 = r1 + r6
        L_0x02d5:
            r45 = r1
            r46 = r2
            goto L_0x0311
        L_0x02da:
            android.view.ViewGroup$LayoutParams r6 = r4.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r6 = (android.widget.LinearLayout.LayoutParams) r6
            if (r23 == 0) goto L_0x02f8
            int r9 = r7.mTotalLength
            r45 = r1
            int r1 = r6.leftMargin
            int r1 = r1 + r8
            r46 = r2
            int r2 = r6.rightMargin
            int r1 = r1 + r2
            int r2 = r7.getNextLocationOffset(r4)
            int r1 = r1 + r2
            int r9 = r9 + r1
            r7.mTotalLength = r9
            goto L_0x0311
        L_0x02f8:
            r45 = r1
            r46 = r2
            int r1 = r7.mTotalLength
            int r2 = r1 + r8
            int r9 = r6.leftMargin
            int r2 = r2 + r9
            int r9 = r6.rightMargin
            int r2 = r2 + r9
            int r9 = r7.getNextLocationOffset(r4)
            int r2 = r2 + r9
            int r2 = java.lang.Math.max(r1, r2)
            r7.mTotalLength = r2
        L_0x0311:
            int r1 = r45 + 1
            r2 = r46
            goto L_0x02b5
        L_0x0316:
            r46 = r2
            goto L_0x031d
        L_0x0319:
            r46 = r2
            r3 = r41
        L_0x031d:
            int r1 = r7.mTotalLength
            int r2 = r7.mPaddingLeft
            int r4 = r7.mPaddingRight
            int r2 = r2 + r4
            int r1 = r1 + r2
            r7.mTotalLength = r1
            int r1 = r7.mTotalLength
            int r2 = r68.getSuggestedMinimumWidth()
            int r1 = java.lang.Math.max(r1, r2)
            r4 = r8
            r2 = r69
            r6 = 0
            int r8 = resolveSizeAndState(r1, r2, r6)
            r6 = 16777215(0xffffff, float:2.3509886E-38)
            r1 = r8 & r6
            int r6 = r7.mTotalLength
            int r6 = r1 - r6
            boolean r9 = r7.mAllowInconsistentMeasurement
            if (r9 == 0) goto L_0x0348
            r9 = 0
            goto L_0x034a
        L_0x0348:
            r9 = r25
        L_0x034a:
            int r6 = r6 + r9
            if (r22 != 0) goto L_0x03d9
            boolean r27 = sRemeasureWeightedChildren
            if (r27 != 0) goto L_0x0353
            if (r6 == 0) goto L_0x035f
        L_0x0353:
            int r27 = (r5 > r29 ? 1 : (r5 == r29 ? 0 : -1))
            if (r27 <= 0) goto L_0x035f
            r48 = r1
            r49 = r5
            r52 = r6
            goto L_0x03df
        L_0x035f:
            int r0 = java.lang.Math.max(r0, r12)
            if (r14 == 0) goto L_0x03c1
            r9 = 1073741824(0x40000000, float:2.0)
            if (r3 == r9) goto L_0x03c1
            r16 = 0
        L_0x036b:
            r9 = r16
            if (r9 >= r11) goto L_0x03c1
            r47 = r0
            android.view.View r0 = r7.getVirtualChildAt(r9)
            if (r0 == 0) goto L_0x03b0
            r48 = r1
            int r1 = r0.getVisibility()
            r49 = r5
            r5 = 8
            if (r1 != r5) goto L_0x0387
            r52 = r6
            goto L_0x03b6
        L_0x0387:
            android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r1 = (android.widget.LinearLayout.LayoutParams) r1
            float r5 = r1.weight
            int r16 = (r5 > r29 ? 1 : (r5 == r29 ? 0 : -1))
            if (r16 <= 0) goto L_0x03ad
            r50 = r1
            r51 = r5
            r1 = 1073741824(0x40000000, float:2.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r1)
            r52 = r6
            int r6 = r0.getMeasuredHeight()
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r1)
            r0.measure(r5, r6)
            goto L_0x03b6
        L_0x03ad:
            r52 = r6
            goto L_0x03b6
        L_0x03b0:
            r48 = r1
            r49 = r5
            r52 = r6
        L_0x03b6:
            int r16 = r9 + 1
            r0 = r47
            r1 = r48
            r5 = r49
            r6 = r52
            goto L_0x036b
        L_0x03c1:
            r47 = r0
            r48 = r1
            r49 = r5
            r52 = r6
            r58 = r3
            r59 = r4
            r56 = r8
            r55 = r11
            r53 = r12
            r54 = r14
            r6 = r70
            goto L_0x05d2
        L_0x03d9:
            r48 = r1
            r49 = r5
            r52 = r6
        L_0x03df:
            float r1 = r7.mWeightSum
            int r1 = (r1 > r29 ? 1 : (r1 == r29 ? 0 : -1))
            if (r1 <= 0) goto L_0x03e8
            float r5 = r7.mWeightSum
            goto L_0x03ea
        L_0x03e8:
            r5 = r49
        L_0x03ea:
            r1 = r5
            r5 = -1
            r10[r17] = r5
            r10[r19] = r5
            r10[r20] = r5
            r6 = 0
            r10[r6] = r5
            r15[r17] = r5
            r15[r19] = r5
            r15[r20] = r5
            r15[r6] = r5
            r5 = -1
            r7.mTotalLength = r6
            r53 = r12
            r9 = r44
            r6 = r52
            r12 = r0
            r0 = 0
        L_0x0408:
            if (r0 >= r11) goto L_0x056f
            r54 = r14
            android.view.View r14 = r7.getVirtualChildAt(r0)
            if (r14 == 0) goto L_0x054d
            int r2 = r14.getVisibility()
            r55 = r11
            r11 = 8
            if (r2 != r11) goto L_0x042b
            r58 = r3
            r59 = r4
            r52 = r6
            r56 = r8
            r6 = r70
            r27 = -2
            goto L_0x055b
        L_0x042b:
            android.view.ViewGroup$LayoutParams r2 = r14.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r2 = (android.widget.LinearLayout.LayoutParams) r2
            float r11 = r2.weight
            int r27 = (r11 > r29 ? 1 : (r11 == r29 ? 0 : -1))
            if (r27 <= 0) goto L_0x049a
            r56 = r8
            float r8 = (float) r6
            float r8 = r8 * r11
            float r8 = r8 / r1
            int r8 = (int) r8
            int r6 = r6 - r8
            float r1 = r1 - r11
            r57 = r1
            boolean r1 = r7.mUseLargestChild
            if (r1 == 0) goto L_0x044b
            r1 = 1073741824(0x40000000, float:2.0)
            if (r3 == r1) goto L_0x044b
            r1 = r4
            goto L_0x045e
        L_0x044b:
            int r1 = r2.width
            if (r1 != 0) goto L_0x0459
            boolean r1 = r7.mAllowInconsistentMeasurement
            if (r1 == 0) goto L_0x0457
            r1 = 1073741824(0x40000000, float:2.0)
            if (r3 != r1) goto L_0x0459
        L_0x0457:
            r1 = r8
            goto L_0x045e
        L_0x0459:
            int r1 = r14.getMeasuredWidth()
            int r1 = r1 + r8
        L_0x045e:
            r58 = r3
            r59 = r4
            r3 = 0
            int r4 = java.lang.Math.max(r3, r1)
            r3 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r3)
            int r3 = r7.mPaddingTop
            r60 = r1
            int r1 = r7.mPaddingBottom
            int r3 = r3 + r1
            int r1 = r2.topMargin
            int r3 = r3 + r1
            int r1 = r2.bottomMargin
            int r3 = r3 + r1
            int r1 = r2.height
            r61 = r6
            r62 = r8
            r6 = r70
            r8 = 1073741824(0x40000000, float:2.0)
            int r1 = getChildMeasureSpec(r6, r3, r1)
            r14.measure(r4, r1)
            int r3 = r14.getMeasuredState()
            r27 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r3 = r3 & r27
            int r9 = combineMeasuredStates(r9, r3)
            goto L_0x04aa
        L_0x049a:
            r58 = r3
            r59 = r4
            r52 = r6
            r56 = r8
            r6 = r70
            r8 = 1073741824(0x40000000, float:2.0)
            r57 = r1
            r61 = r52
        L_0x04aa:
            if (r23 == 0) goto L_0x04c1
            int r1 = r7.mTotalLength
            int r3 = r14.getMeasuredWidth()
            int r4 = r2.leftMargin
            int r3 = r3 + r4
            int r4 = r2.rightMargin
            int r3 = r3 + r4
            int r4 = r7.getNextLocationOffset(r14)
            int r3 = r3 + r4
            int r1 = r1 + r3
            r7.mTotalLength = r1
            goto L_0x04d9
        L_0x04c1:
            int r1 = r7.mTotalLength
            int r3 = r14.getMeasuredWidth()
            int r3 = r3 + r1
            int r4 = r2.leftMargin
            int r3 = r3 + r4
            int r4 = r2.rightMargin
            int r3 = r3 + r4
            int r4 = r7.getNextLocationOffset(r14)
            int r3 = r3 + r4
            int r3 = java.lang.Math.max(r1, r3)
            r7.mTotalLength = r3
        L_0x04d9:
            if (r13 == r8) goto L_0x04e3
            int r1 = r2.height
            r3 = -1
            if (r1 != r3) goto L_0x04e3
            r1 = r20
            goto L_0x04e4
        L_0x04e3:
            r1 = 0
        L_0x04e4:
            int r3 = r2.topMargin
            int r4 = r2.bottomMargin
            int r3 = r3 + r4
            int r4 = r14.getMeasuredHeight()
            int r4 = r4 + r3
            int r5 = java.lang.Math.max(r5, r4)
            if (r1 == 0) goto L_0x04f7
            r8 = r3
            goto L_0x04f8
        L_0x04f7:
            r8 = r4
        L_0x04f8:
            int r8 = java.lang.Math.max(r12, r8)
            if (r24 == 0) goto L_0x0508
            int r12 = r2.height
            r63 = r1
            r1 = -1
            if (r12 != r1) goto L_0x050a
            r1 = r20
            goto L_0x050b
        L_0x0508:
            r63 = r1
        L_0x050a:
            r1 = 0
        L_0x050b:
            if (r21 == 0) goto L_0x0543
            int r12 = r14.getBaseline()
            r64 = r1
            r1 = -1
            if (r12 == r1) goto L_0x0540
            int r1 = r2.gravity
            if (r1 >= 0) goto L_0x051d
            int r1 = r7.mGravity
            goto L_0x051f
        L_0x051d:
            int r1 = r2.gravity
        L_0x051f:
            r1 = r1 & 112(0x70, float:1.57E-43)
            int r24 = r1 >> 4
            r27 = -2
            r24 = r24 & -2
            int r24 = r24 >> 1
            r65 = r1
            r1 = r10[r24]
            int r1 = java.lang.Math.max(r1, r12)
            r10[r24] = r1
            r1 = r15[r24]
            r66 = r2
            int r2 = r4 - r12
            int r1 = java.lang.Math.max(r1, r2)
            r15[r24] = r1
            goto L_0x0547
        L_0x0540:
            r27 = -2
            goto L_0x0547
        L_0x0543:
            r64 = r1
            r27 = -2
        L_0x0547:
            r12 = r8
            r1 = r57
            r24 = r64
            goto L_0x055d
        L_0x054d:
            r58 = r3
            r59 = r4
            r52 = r6
            r56 = r8
            r55 = r11
            r6 = r70
            r27 = -2
        L_0x055b:
            r61 = r52
        L_0x055d:
            int r0 = r0 + 1
            r14 = r54
            r11 = r55
            r8 = r56
            r3 = r58
            r4 = r59
            r6 = r61
            r2 = r69
            goto L_0x0408
        L_0x056f:
            r58 = r3
            r59 = r4
            r52 = r6
            r56 = r8
            r55 = r11
            r54 = r14
            r6 = r70
            int r0 = r7.mTotalLength
            int r2 = r7.mPaddingLeft
            int r3 = r7.mPaddingRight
            int r2 = r2 + r3
            int r0 = r0 + r2
            r7.mTotalLength = r0
            r0 = r10[r20]
            r2 = -1
            if (r0 != r2) goto L_0x059c
            r0 = 0
            r3 = r10[r0]
            if (r3 != r2) goto L_0x059c
            r0 = r10[r19]
            if (r0 != r2) goto L_0x059c
            r0 = r10[r17]
            if (r0 == r2) goto L_0x059a
            goto L_0x059c
        L_0x059a:
            r2 = r5
            goto L_0x05cc
        L_0x059c:
            r0 = r10[r17]
            r2 = 0
            r3 = r10[r2]
            r4 = r10[r20]
            r8 = r10[r19]
            int r4 = java.lang.Math.max(r4, r8)
            int r3 = java.lang.Math.max(r3, r4)
            int r0 = java.lang.Math.max(r0, r3)
            r3 = r15[r17]
            r2 = r15[r2]
            r4 = r15[r20]
            r8 = r15[r19]
            int r4 = java.lang.Math.max(r4, r8)
            int r2 = java.lang.Math.max(r2, r4)
            int r2 = java.lang.Math.max(r3, r2)
            int r3 = r0 + r2
            int r0 = java.lang.Math.max(r5, r3)
            r2 = r0
        L_0x05cc:
            r46 = r2
            r44 = r9
            r47 = r12
        L_0x05d2:
            if (r24 != 0) goto L_0x05da
            r0 = 1073741824(0x40000000, float:2.0)
            if (r13 == r0) goto L_0x05da
            r46 = r47
        L_0x05da:
            int r0 = r7.mPaddingTop
            int r1 = r7.mPaddingBottom
            int r0 = r0 + r1
            int r0 = r46 + r0
            int r1 = r68.getSuggestedMinimumHeight()
            int r0 = java.lang.Math.max(r0, r1)
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = r44 & r1
            r1 = r56 | r1
            int r2 = r44 << 16
            int r2 = resolveSizeAndState(r0, r6, r2)
            r7.setMeasuredDimension(r1, r2)
            if (r18 == 0) goto L_0x0602
            r2 = r55
            r1 = r69
            r7.forceUniformHeight(r2, r1)
            goto L_0x0606
        L_0x0602:
            r2 = r55
            r1 = r69
        L_0x0606:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.measureHorizontal(int, int):void");
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8)) {
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

    /* access modifiers changed from: package-private */
    public int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int measureNullChild(int childIndex) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    /* access modifiers changed from: package-private */
    public int getLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getNextLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    /* access modifiers changed from: package-private */
    public void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int paddingLeft;
        int majorGravity;
        int childLeft;
        int paddingLeft2 = this.mPaddingLeft;
        int width = right - left;
        int childRight = width - this.mPaddingRight;
        int childSpace = (width - paddingLeft2) - this.mPaddingRight;
        int count = getVirtualChildCount();
        int majorGravity2 = this.mGravity & 112;
        int minorGravity = this.mGravity & 8388615;
        if (majorGravity2 == 16) {
            childTop = this.mPaddingTop + (((bottom - top) - this.mTotalLength) / 2);
        } else if (majorGravity2 != 80) {
            childTop = this.mPaddingTop;
        } else {
            childTop = ((this.mPaddingTop + bottom) - top) - this.mTotalLength;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < count) {
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
                    int gravity2 = gravity;
                    int gravity3 = Gravity.getAbsoluteGravity(gravity, layoutDirection) & 7;
                    majorGravity = majorGravity2;
                    if (gravity3 != 1) {
                        childLeft = gravity3 != 5 ? lp.leftMargin + paddingLeft2 : (childRight - childWidth) - lp.rightMargin;
                    } else {
                        childLeft = ((((childSpace - childWidth) / 2) + paddingLeft2) + lp.leftMargin) - lp.rightMargin;
                    }
                    int i3 = gravity2;
                    if (hasDividerBeforeChildAt(i2)) {
                        childTop += this.mDividerHeight;
                    }
                    int childTop2 = childTop + lp.topMargin;
                    int i4 = layoutDirection;
                    LayoutParams lp2 = lp;
                    View child2 = child;
                    paddingLeft = paddingLeft2;
                    int paddingLeft3 = i2;
                    setChildFrame(child, childLeft, childTop2 + getLocationOffset(child), childWidth, childHeight);
                    int childTop3 = childTop2 + childHeight + lp2.bottomMargin + getNextLocationOffset(child2);
                    i2 = paddingLeft3 + getChildrenSkipCount(child2, paddingLeft3);
                    childTop = childTop3;
                } else {
                    majorGravity = majorGravity2;
                    paddingLeft = paddingLeft2;
                    int paddingLeft4 = i2;
                }
                i = i2 + 1;
                majorGravity2 = majorGravity;
                paddingLeft2 = paddingLeft;
            } else {
                int i5 = paddingLeft2;
                return;
            }
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (layoutDirection != this.mLayoutDirection) {
            this.mLayoutDirection = layoutDirection;
            if (this.mOrientation == 0) {
                requestLayout();
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0102  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layoutHorizontal(int r32, int r33, int r34, int r35) {
        /*
            r31 = this;
            r6 = r31
            boolean r9 = r31.isLayoutRtl()
            int r10 = r6.mPaddingTop
            int r13 = r35 - r33
            int r0 = r6.mPaddingBottom
            int r14 = r13 - r0
            int r0 = r13 - r10
            int r1 = r6.mPaddingBottom
            int r15 = r0 - r1
            int r5 = r31.getVirtualChildCount()
            int r0 = r6.mGravity
            r1 = 8388615(0x800007, float:1.1754953E-38)
            r4 = r0 & r1
            int r0 = r6.mGravity
            r16 = r0 & 112(0x70, float:1.57E-43)
            boolean r2 = r6.mBaselineAligned
            int[] r1 = r6.mMaxAscent
            int[] r0 = r6.mMaxDescent
            int r3 = r31.getLayoutDirection()
            int r11 = android.view.Gravity.getAbsoluteGravity(r4, r3)
            r17 = 2
            r12 = 1
            if (r11 == r12) goto L_0x0048
            r12 = 5
            if (r11 == r12) goto L_0x003e
            int r11 = r6.mPaddingLeft
        L_0x003b:
            r18 = r3
            goto L_0x0055
        L_0x003e:
            int r11 = r6.mPaddingLeft
            int r11 = r11 + r34
            int r11 = r11 - r32
            int r12 = r6.mTotalLength
            int r11 = r11 - r12
            goto L_0x003b
        L_0x0048:
            int r11 = r6.mPaddingLeft
            int r12 = r34 - r32
            r18 = r3
            int r3 = r6.mTotalLength
            int r12 = r12 - r3
            int r12 = r12 / 2
            int r11 = r11 + r12
        L_0x0055:
            r3 = r11
            r11 = 0
            r12 = 1
            if (r9 == 0) goto L_0x005d
            int r11 = r5 + -1
            r12 = -1
        L_0x005d:
            r19 = 0
            r20 = r3
        L_0x0061:
            r3 = r19
            if (r3 >= r5) goto L_0x0154
            int r19 = r12 * r3
            int r7 = r11 + r19
            android.view.View r8 = r6.getVirtualChildAt(r7)
            if (r8 != 0) goto L_0x0083
            int r19 = r6.measureNullChild(r7)
            int r20 = r20 + r19
            r26 = r0
            r28 = r1
            r25 = r2
            r22 = r4
            r27 = r5
            r30 = r9
            goto L_0x0143
        L_0x0083:
            r21 = r3
            int r3 = r8.getVisibility()
            r22 = r4
            r4 = 8
            if (r3 == r4) goto L_0x0137
            int r19 = r8.getMeasuredWidth()
            int r23 = r8.getMeasuredHeight()
            r3 = -1
            android.view.ViewGroup$LayoutParams r4 = r8.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r4 = (android.widget.LinearLayout.LayoutParams) r4
            r24 = r3
            r3 = -1
            if (r2 == 0) goto L_0x00af
            r25 = r2
            int r2 = r4.height
            if (r2 == r3) goto L_0x00b1
            int r2 = r8.getBaseline()
            goto L_0x00b3
        L_0x00af:
            r25 = r2
        L_0x00b1:
            r2 = r24
        L_0x00b3:
            int r3 = r4.gravity
            if (r3 >= 0) goto L_0x00b9
            r3 = r16
        L_0x00b9:
            r24 = r3
            r3 = r24 & 112(0x70, float:1.57E-43)
            r27 = r5
            r5 = 16
            if (r3 == r5) goto L_0x00ef
            r5 = 48
            if (r3 == r5) goto L_0x00e1
            r5 = 80
            if (r3 == r5) goto L_0x00cd
            r3 = r10
            goto L_0x00fb
        L_0x00cd:
            int r3 = r14 - r23
            int r5 = r4.bottomMargin
            int r3 = r3 - r5
            r5 = -1
            if (r2 == r5) goto L_0x00fb
            int r5 = r8.getMeasuredHeight()
            int r5 = r5 - r2
            r26 = r0[r17]
            int r26 = r26 - r5
            int r3 = r3 - r26
            goto L_0x00fb
        L_0x00e1:
            int r3 = r4.topMargin
            int r3 = r3 + r10
            r5 = -1
            if (r2 == r5) goto L_0x00fb
            r5 = 1
            r26 = r1[r5]
            int r26 = r26 - r2
            int r3 = r3 + r26
            goto L_0x00fb
        L_0x00ef:
            int r3 = r15 - r23
            int r3 = r3 / 2
            int r3 = r3 + r10
            int r5 = r4.topMargin
            int r3 = r3 + r5
            int r5 = r4.bottomMargin
            int r3 = r3 - r5
        L_0x00fb:
            boolean r5 = r6.hasDividerBeforeChildAt(r7)
            if (r5 == 0) goto L_0x0106
            int r5 = r6.mDividerWidth
            int r20 = r20 + r5
        L_0x0106:
            int r5 = r4.leftMargin
            int r20 = r20 + r5
            int r5 = r6.getLocationOffset(r8)
            int r5 = r20 + r5
            r26 = r0
            r0 = r31
            r28 = r1
            r1 = r8
            r29 = r2
            r2 = r5
            r5 = r4
            r4 = r19
            r30 = r9
            r9 = r5
            r5 = r23
            r0.setChildFrame(r1, r2, r3, r4, r5)
            int r0 = r9.rightMargin
            int r0 = r19 + r0
            int r1 = r6.getNextLocationOffset(r8)
            int r0 = r0 + r1
            int r20 = r20 + r0
            int r0 = r6.getChildrenSkipCount(r8, r7)
            int r3 = r21 + r0
            goto L_0x0143
        L_0x0137:
            r26 = r0
            r28 = r1
            r25 = r2
            r27 = r5
            r30 = r9
            r3 = r21
        L_0x0143:
            r0 = 1
            int r19 = r3 + 1
            r4 = r22
            r2 = r25
            r0 = r26
            r5 = r27
            r1 = r28
            r9 = r30
            goto L_0x0061
        L_0x0154:
            r26 = r0
            r28 = r1
            r25 = r2
            r22 = r4
            r27 = r5
            r30 = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.LinearLayout.layoutHorizontal(int, int, int, int):void");
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
            this.mGravity = (this.mGravity & -8388616) | gravity;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
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

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public CharSequence getAccessibilityClassName() {
        return LinearLayout.class.getName();
    }

    /* access modifiers changed from: protected */
    public void encodeProperties(ViewHierarchyEncoder encoder) {
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
