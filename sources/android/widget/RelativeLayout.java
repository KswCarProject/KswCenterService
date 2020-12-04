package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ResourceId;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Pools;
import android.util.SparseArray;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@RemoteViews.RemoteView
public class RelativeLayout extends ViewGroup {
    public static final int ABOVE = 2;
    public static final int ALIGN_BASELINE = 4;
    public static final int ALIGN_BOTTOM = 8;
    public static final int ALIGN_END = 19;
    public static final int ALIGN_LEFT = 5;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int ALIGN_PARENT_END = 21;
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_START = 20;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_RIGHT = 7;
    public static final int ALIGN_START = 18;
    public static final int ALIGN_TOP = 6;
    public static final int BELOW = 3;
    public static final int CENTER_HORIZONTAL = 14;
    public static final int CENTER_IN_PARENT = 13;
    public static final int CENTER_VERTICAL = 15;
    private static final int DEFAULT_WIDTH = 65536;
    public static final int END_OF = 17;
    public static final int LEFT_OF = 0;
    public static final int RIGHT_OF = 1;
    private static final int[] RULES_HORIZONTAL = {0, 1, 5, 7, 16, 17, 18, 19};
    private static final int[] RULES_VERTICAL = {2, 3, 4, 6, 8};
    public static final int START_OF = 16;
    public static final int TRUE = -1;
    private static final int VALUE_NOT_SET = Integer.MIN_VALUE;
    private static final int VERB_COUNT = 22;
    private boolean mAllowBrokenMeasureSpecs;
    private View mBaselineView;
    private final Rect mContentBounds;
    private boolean mDirtyHierarchy;
    private final DependencyGraph mGraph;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private int mGravity;
    private int mIgnoreGravity;
    private boolean mMeasureVerticalWithPaddingMargin;
    private final Rect mSelfBounds;
    private View[] mSortedHorizontalChildren;
    private View[] mSortedVerticalChildren;
    private SortedSet<View> mTopToBottomLeftToRightSet;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<RelativeLayout> {
        private int mGravityId;
        private int mIgnoreGravityId;
        private boolean mPropertiesMapped = false;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mIgnoreGravityId = propertyMapper.mapInt("ignoreGravity", 16843263);
            this.mPropertiesMapped = true;
        }

        public void readProperties(RelativeLayout node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readGravity(this.mGravityId, node.getGravity());
                propertyReader.readInt(this.mIgnoreGravityId, node.getIgnoreGravity());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public RelativeLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public RelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mBaselineView = null;
        this.mGravity = 8388659;
        this.mContentBounds = new Rect();
        this.mSelfBounds = new Rect();
        this.mTopToBottomLeftToRightSet = null;
        this.mGraph = new DependencyGraph();
        this.mAllowBrokenMeasureSpecs = false;
        this.mMeasureVerticalWithPaddingMargin = false;
        initFromAttributes(context, attrs, defStyleAttr, defStyleRes);
        queryCompatibilityModes(context);
    }

    private void initFromAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelativeLayout, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.RelativeLayout, attrs, a, defStyleAttr, defStyleRes);
        this.mIgnoreGravity = a.getResourceId(1, -1);
        this.mGravity = a.getInt(0, this.mGravity);
        a.recycle();
    }

    private void queryCompatibilityModes(Context context) {
        int version = context.getApplicationInfo().targetSdkVersion;
        boolean z = false;
        this.mAllowBrokenMeasureSpecs = version <= 17;
        if (version >= 18) {
            z = true;
        }
        this.mMeasureVerticalWithPaddingMargin = z;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @RemotableViewMethod
    public void setIgnoreGravity(int viewId) {
        this.mIgnoreGravity = viewId;
    }

    public int getIgnoreGravity() {
        return this.mIgnoreGravity;
    }

    public int getGravity() {
        return this.mGravity;
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

    public int getBaseline() {
        return this.mBaselineView != null ? this.mBaselineView.getBaseline() : super.getBaseline();
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
    }

    private void sortChildren() {
        int count = getChildCount();
        if (this.mSortedVerticalChildren == null || this.mSortedVerticalChildren.length != count) {
            this.mSortedVerticalChildren = new View[count];
        }
        if (this.mSortedHorizontalChildren == null || this.mSortedHorizontalChildren.length != count) {
            this.mSortedHorizontalChildren = new View[count];
        }
        DependencyGraph graph = this.mGraph;
        graph.clear();
        for (int i = 0; i < count; i++) {
            graph.add(getChildAt(i));
        }
        graph.getSortedViews(this.mSortedVerticalChildren, RULES_VERTICAL);
        graph.getSortedViews(this.mSortedHorizontalChildren, RULES_HORIZONTAL);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x03a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r58, int r59) {
        /*
            r57 = this;
            r0 = r57
            boolean r1 = r0.mDirtyHierarchy
            r2 = 0
            if (r1 == 0) goto L_0x000c
            r0.mDirtyHierarchy = r2
            r57.sortChildren()
        L_0x000c:
            r1 = -1
            r3 = -1
            r4 = 0
            r5 = 0
            int r6 = android.view.View.MeasureSpec.getMode(r58)
            int r7 = android.view.View.MeasureSpec.getMode(r59)
            int r8 = android.view.View.MeasureSpec.getSize(r58)
            int r9 = android.view.View.MeasureSpec.getSize(r59)
            if (r6 == 0) goto L_0x0023
            r1 = r8
        L_0x0023:
            if (r7 == 0) goto L_0x0026
            r3 = r9
        L_0x0026:
            r10 = 1073741824(0x40000000, float:2.0)
            if (r6 != r10) goto L_0x002b
            r4 = r1
        L_0x002b:
            if (r7 != r10) goto L_0x002e
            r5 = r3
        L_0x002e:
            r11 = 0
            int r12 = r0.mGravity
            r13 = 8388615(0x800007, float:1.1754953E-38)
            r12 = r12 & r13
            r13 = 8388611(0x800003, float:1.1754948E-38)
            if (r12 == r13) goto L_0x003e
            if (r12 == 0) goto L_0x003e
            r13 = 1
            goto L_0x003f
        L_0x003e:
            r13 = r2
        L_0x003f:
            int r15 = r0.mGravity
            r12 = r15 & 112(0x70, float:1.57E-43)
            r15 = 48
            if (r12 == r15) goto L_0x004b
            if (r12 == 0) goto L_0x004b
            r15 = 1
            goto L_0x004c
        L_0x004b:
            r15 = r2
        L_0x004c:
            r16 = 2147483647(0x7fffffff, float:NaN)
            r17 = 2147483647(0x7fffffff, float:NaN)
            r18 = -2147483648(0xffffffff80000000, float:-0.0)
            r19 = -2147483648(0xffffffff80000000, float:-0.0)
            r20 = 0
            r21 = 0
            r2 = -1
            if (r13 != 0) goto L_0x005f
            if (r15 == 0) goto L_0x0069
        L_0x005f:
            int r14 = r0.mIgnoreGravity
            if (r14 == r2) goto L_0x0069
            int r14 = r0.mIgnoreGravity
            android.view.View r11 = r0.findViewById(r14)
        L_0x0069:
            if (r6 == r10) goto L_0x006d
            r14 = 1
            goto L_0x006e
        L_0x006d:
            r14 = 0
        L_0x006e:
            if (r7 == r10) goto L_0x0073
            r23 = 1
            goto L_0x0075
        L_0x0073:
            r23 = 0
        L_0x0075:
            r10 = r23
            int r2 = r57.getLayoutDirection()
            boolean r23 = r57.isLayoutRtl()
            if (r23 == 0) goto L_0x0089
            r30 = r4
            r4 = -1
            if (r1 != r4) goto L_0x008b
            r1 = 65536(0x10000, float:9.18355E-41)
            goto L_0x008b
        L_0x0089:
            r30 = r4
        L_0x008b:
            android.view.View[] r4 = r0.mSortedHorizontalChildren
            r31 = r5
            int r5 = r4.length
            r29 = r20
            r20 = 0
        L_0x0094:
            r32 = r20
            r33 = r6
            r6 = r32
            if (r6 >= r5) goto L_0x00d0
            r34 = r5
            r5 = r4[r6]
            r35 = r4
            int r4 = r5.getVisibility()
            r36 = r7
            r7 = 8
            if (r4 == r7) goto L_0x00c5
            android.view.ViewGroup$LayoutParams r4 = r5.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r4 = (android.widget.RelativeLayout.LayoutParams) r4
            int[] r7 = r4.getRules(r2)
            r0.applyHorizontalSizeRules(r4, r1, r7)
            r0.measureChildHorizontal(r5, r4, r1, r3)
            boolean r20 = r0.positionChildHorizontal(r5, r4, r1, r14)
            if (r20 == 0) goto L_0x00c5
            r4 = 1
            r29 = r4
        L_0x00c5:
            int r20 = r6 + 1
            r6 = r33
            r5 = r34
            r4 = r35
            r7 = r36
            goto L_0x0094
        L_0x00d0:
            r35 = r4
            r34 = r5
            r36 = r7
            android.view.View[] r4 = r0.mSortedVerticalChildren
            int r5 = r4.length
            android.content.Context r6 = r57.getContext()
            android.content.pm.ApplicationInfo r6 = r6.getApplicationInfo()
            int r6 = r6.targetSdkVersion
            r40 = r2
            r37 = r8
            r38 = r9
            r39 = r12
            r12 = r16
            r2 = r17
            r41 = r18
            r42 = r19
            r8 = r30
            r9 = r31
            r7 = 0
        L_0x00f8:
            if (r7 >= r5) goto L_0x01d3
            r43 = r5
            r5 = r4[r7]
            r44 = r4
            int r4 = r5.getVisibility()
            r45 = r7
            r7 = 8
            if (r4 == r7) goto L_0x01c3
            android.view.ViewGroup$LayoutParams r4 = r5.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r4 = (android.widget.RelativeLayout.LayoutParams) r4
            int r7 = r5.getBaseline()
            r0.applyVerticalSizeRules(r4, r3, r7)
            r0.measureChild(r5, r4, r1, r3)
            boolean r7 = r0.positionChildVertical(r5, r4, r3, r10)
            if (r7 == 0) goto L_0x0122
            r21 = 1
        L_0x0122:
            r7 = 19
            if (r14 == 0) goto L_0x0166
            boolean r16 = r57.isLayoutRtl()
            if (r16 == 0) goto L_0x014b
            if (r6 >= r7) goto L_0x013b
            int r16 = r4.mLeft
            int r7 = r1 - r16
            int r8 = java.lang.Math.max(r8, r7)
            r46 = r3
            goto L_0x0168
        L_0x013b:
            int r7 = r4.mLeft
            int r7 = r1 - r7
            r46 = r3
            int r3 = r4.leftMargin
            int r7 = r7 + r3
            int r8 = java.lang.Math.max(r8, r7)
            goto L_0x0168
        L_0x014b:
            r46 = r3
            r3 = 19
            if (r6 >= r3) goto L_0x015a
            int r3 = r4.mRight
            int r8 = java.lang.Math.max(r8, r3)
            goto L_0x0168
        L_0x015a:
            int r3 = r4.mRight
            int r7 = r4.rightMargin
            int r3 = r3 + r7
            int r8 = java.lang.Math.max(r8, r3)
            goto L_0x0168
        L_0x0166:
            r46 = r3
        L_0x0168:
            if (r10 == 0) goto L_0x0182
            r3 = 19
            if (r6 >= r3) goto L_0x0177
            int r3 = r4.mBottom
            int r9 = java.lang.Math.max(r9, r3)
            goto L_0x0182
        L_0x0177:
            int r3 = r4.mBottom
            int r7 = r4.bottomMargin
            int r3 = r3 + r7
            int r9 = java.lang.Math.max(r9, r3)
        L_0x0182:
            if (r5 != r11) goto L_0x0186
            if (r15 == 0) goto L_0x019c
        L_0x0186:
            int r3 = r4.mLeft
            int r7 = r4.leftMargin
            int r3 = r3 - r7
            int r12 = java.lang.Math.min(r12, r3)
            int r3 = r4.mTop
            int r7 = r4.topMargin
            int r3 = r3 - r7
            int r2 = java.lang.Math.min(r2, r3)
        L_0x019c:
            if (r5 != r11) goto L_0x01a0
            if (r13 == 0) goto L_0x01c9
        L_0x01a0:
            int r3 = r4.mRight
            int r7 = r4.rightMargin
            int r3 = r3 + r7
            r7 = r41
            int r3 = java.lang.Math.max(r7, r3)
            int r7 = r4.mBottom
            r47 = r2
            int r2 = r4.bottomMargin
            int r7 = r7 + r2
            r2 = r42
            int r2 = java.lang.Math.max(r2, r7)
            r42 = r2
            r41 = r3
            r2 = r47
            goto L_0x01c9
        L_0x01c3:
            r46 = r3
            r7 = r41
            r19 = r42
        L_0x01c9:
            int r7 = r45 + 1
            r5 = r43
            r4 = r44
            r3 = r46
            goto L_0x00f8
        L_0x01d3:
            r46 = r3
            r44 = r4
            r43 = r5
            r7 = r41
            r19 = r42
            r3 = 0
            r4 = 0
            r5 = r4
            r4 = r3
            r3 = 0
        L_0x01e2:
            r48 = r6
            r6 = r43
            if (r3 >= r6) goto L_0x0215
            r16 = r44[r3]
            r49 = r1
            int r1 = r16.getVisibility()
            r50 = r11
            r11 = 8
            if (r1 == r11) goto L_0x020a
            android.view.ViewGroup$LayoutParams r1 = r16.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r1 = (android.widget.RelativeLayout.LayoutParams) r1
            if (r4 == 0) goto L_0x0206
            if (r5 == 0) goto L_0x0206
            int r11 = r0.compareLayoutPosition(r1, r5)
            if (r11 >= 0) goto L_0x020a
        L_0x0206:
            r4 = r16
            r5 = r1
        L_0x020a:
            int r3 = r3 + 1
            r43 = r6
            r6 = r48
            r1 = r49
            r11 = r50
            goto L_0x01e2
        L_0x0215:
            r49 = r1
            r50 = r11
            r0.mBaselineView = r4
            if (r14 == 0) goto L_0x029a
            int r3 = r0.mPaddingRight
            int r8 = r8 + r3
            android.view.ViewGroup$LayoutParams r3 = r0.mLayoutParams
            if (r3 == 0) goto L_0x0232
            android.view.ViewGroup$LayoutParams r3 = r0.mLayoutParams
            int r3 = r3.width
            if (r3 < 0) goto L_0x0232
            android.view.ViewGroup$LayoutParams r3 = r0.mLayoutParams
            int r3 = r3.width
            int r8 = java.lang.Math.max(r8, r3)
        L_0x0232:
            int r3 = r57.getSuggestedMinimumWidth()
            int r3 = java.lang.Math.max(r8, r3)
            r11 = r58
            int r8 = resolveSize(r3, r11)
            if (r29 == 0) goto L_0x029a
            r3 = 0
        L_0x0243:
            if (r3 >= r6) goto L_0x029a
            r1 = r44[r3]
            r51 = r4
            int r4 = r1.getVisibility()
            r52 = r5
            r5 = 8
            if (r4 == r5) goto L_0x028d
            android.view.ViewGroup$LayoutParams r4 = r1.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r4 = (android.widget.RelativeLayout.LayoutParams) r4
            r5 = r40
            int[] r16 = r4.getRules(r5)
            r17 = 13
            r18 = r16[r17]
            if (r18 != 0) goto L_0x0289
            r17 = 14
            r17 = r16[r17]
            if (r17 == 0) goto L_0x026c
            goto L_0x0289
        L_0x026c:
            r17 = 11
            r17 = r16[r17]
            if (r17 == 0) goto L_0x028f
            int r17 = r1.getMeasuredWidth()
            int r11 = r0.mPaddingRight
            int r11 = r8 - r11
            int r11 = r11 - r17
            int unused = r4.mLeft = r11
            int r11 = r4.mLeft
            int r11 = r11 + r17
            int unused = r4.mRight = r11
            goto L_0x028f
        L_0x0289:
            centerHorizontal(r1, r4, r8)
            goto L_0x028f
        L_0x028d:
            r5 = r40
        L_0x028f:
            int r3 = r3 + 1
            r40 = r5
            r4 = r51
            r5 = r52
            r11 = r58
            goto L_0x0243
        L_0x029a:
            r51 = r4
            r52 = r5
            r5 = r40
            if (r10 == 0) goto L_0x0320
            int r1 = r0.mPaddingBottom
            int r9 = r9 + r1
            android.view.ViewGroup$LayoutParams r1 = r0.mLayoutParams
            if (r1 == 0) goto L_0x02b7
            android.view.ViewGroup$LayoutParams r1 = r0.mLayoutParams
            int r1 = r1.height
            if (r1 < 0) goto L_0x02b7
            android.view.ViewGroup$LayoutParams r1 = r0.mLayoutParams
            int r1 = r1.height
            int r9 = java.lang.Math.max(r9, r1)
        L_0x02b7:
            int r1 = r57.getSuggestedMinimumHeight()
            int r1 = java.lang.Math.max(r9, r1)
            r3 = r59
            int r9 = resolveSize(r1, r3)
            if (r21 == 0) goto L_0x0320
            r1 = 0
        L_0x02c8:
            if (r1 >= r6) goto L_0x0320
            r4 = r44[r1]
            int r11 = r4.getVisibility()
            r3 = 8
            if (r11 == r3) goto L_0x0315
            android.view.ViewGroup$LayoutParams r3 = r4.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r3 = (android.widget.RelativeLayout.LayoutParams) r3
            int[] r11 = r3.getRules(r5)
            r16 = 13
            r17 = r11[r16]
            if (r17 != 0) goto L_0x030f
            r17 = 15
            r17 = r11[r17]
            if (r17 == 0) goto L_0x02ed
            r53 = r10
            goto L_0x0311
        L_0x02ed:
            r17 = 12
            r17 = r11[r17]
            if (r17 == 0) goto L_0x030c
            int r17 = r4.getMeasuredHeight()
            r53 = r10
            int r10 = r0.mPaddingBottom
            int r10 = r9 - r10
            int r10 = r10 - r17
            int unused = r3.mTop = r10
            int r10 = r3.mTop
            int r10 = r10 + r17
            int unused = r3.mBottom = r10
            goto L_0x0319
        L_0x030c:
            r53 = r10
            goto L_0x0319
        L_0x030f:
            r53 = r10
        L_0x0311:
            centerVertical(r4, r3, r9)
            goto L_0x0319
        L_0x0315:
            r53 = r10
            r16 = 13
        L_0x0319:
            int r1 = r1 + 1
            r10 = r53
            r3 = r59
            goto L_0x02c8
        L_0x0320:
            r53 = r10
            if (r13 != 0) goto L_0x032d
            if (r15 == 0) goto L_0x0327
            goto L_0x032d
        L_0x0327:
            r55 = r2
            r2 = r50
            goto L_0x039a
        L_0x032d:
            android.graphics.Rect r1 = r0.mSelfBounds
            int r3 = r0.mPaddingLeft
            int r4 = r0.mPaddingTop
            int r10 = r0.mPaddingRight
            int r10 = r8 - r10
            int r11 = r0.mPaddingBottom
            int r11 = r9 - r11
            r1.set(r3, r4, r10, r11)
            android.graphics.Rect r3 = r0.mContentBounds
            int r4 = r0.mGravity
            int r24 = r7 - r12
            int r25 = r19 - r2
            r23 = r4
            r26 = r1
            r27 = r3
            r28 = r5
            android.view.Gravity.apply(r23, r24, r25, r26, r27, r28)
            int r4 = r3.left
            int r4 = r4 - r12
            int r10 = r3.top
            int r10 = r10 - r2
            if (r4 != 0) goto L_0x035b
            if (r10 == 0) goto L_0x0327
        L_0x035b:
            r11 = 0
        L_0x035c:
            if (r11 >= r6) goto L_0x0396
            r54 = r1
            r1 = r44[r11]
            r55 = r2
            int r2 = r1.getVisibility()
            r56 = r3
            r3 = 8
            if (r2 == r3) goto L_0x0389
            r2 = r50
            if (r1 == r2) goto L_0x038b
            android.view.ViewGroup$LayoutParams r3 = r1.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r3 = (android.widget.RelativeLayout.LayoutParams) r3
            if (r13 == 0) goto L_0x0380
            android.widget.RelativeLayout.LayoutParams.access$112(r3, r4)
            android.widget.RelativeLayout.LayoutParams.access$212(r3, r4)
        L_0x0380:
            if (r15 == 0) goto L_0x038b
            android.widget.RelativeLayout.LayoutParams.access$412(r3, r10)
            android.widget.RelativeLayout.LayoutParams.access$312(r3, r10)
            goto L_0x038b
        L_0x0389:
            r2 = r50
        L_0x038b:
            int r11 = r11 + 1
            r50 = r2
            r1 = r54
            r2 = r55
            r3 = r56
            goto L_0x035c
        L_0x0396:
            r55 = r2
            r2 = r50
        L_0x039a:
            boolean r1 = r57.isLayoutRtl()
            if (r1 == 0) goto L_0x03c1
            int r1 = r49 - r8
            r22 = 0
        L_0x03a4:
            r3 = r22
            if (r3 >= r6) goto L_0x03c1
            r4 = r44[r3]
            int r10 = r4.getVisibility()
            r11 = 8
            if (r10 == r11) goto L_0x03be
            android.view.ViewGroup$LayoutParams r10 = r4.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r10 = (android.widget.RelativeLayout.LayoutParams) r10
            android.widget.RelativeLayout.LayoutParams.access$120(r10, r1)
            android.widget.RelativeLayout.LayoutParams.access$220(r10, r1)
        L_0x03be:
            int r22 = r3 + 1
            goto L_0x03a4
        L_0x03c1:
            r0.setMeasuredDimension(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.RelativeLayout.onMeasure(int, int):void");
    }

    private int compareLayoutPosition(LayoutParams p1, LayoutParams p2) {
        int topDiff = p1.mTop - p2.mTop;
        if (topDiff != 0) {
            return topDiff;
        }
        return p1.mLeft - p2.mLeft;
    }

    private void measureChild(View child, LayoutParams params, int myWidth, int myHeight) {
        child.measure(getChildMeasureSpec(params.mLeft, params.mRight, params.width, params.leftMargin, params.rightMargin, this.mPaddingLeft, this.mPaddingRight, myWidth), getChildMeasureSpec(params.mTop, params.mBottom, params.height, params.topMargin, params.bottomMargin, this.mPaddingTop, this.mPaddingBottom, myHeight));
    }

    private void measureChildHorizontal(View child, LayoutParams params, int myWidth, int myHeight) {
        int maxHeight;
        int maxHeight2;
        int heightMode;
        int childWidthMeasureSpec = getChildMeasureSpec(params.mLeft, params.mRight, params.width, params.leftMargin, params.rightMargin, this.mPaddingLeft, this.mPaddingRight, myWidth);
        if (myHeight >= 0 || this.mAllowBrokenMeasureSpecs) {
            if (this.mMeasureVerticalWithPaddingMargin) {
                maxHeight2 = Math.max(0, (((myHeight - this.mPaddingTop) - this.mPaddingBottom) - params.topMargin) - params.bottomMargin);
            } else {
                maxHeight2 = Math.max(0, myHeight);
            }
            if (params.height == -1) {
                heightMode = 1073741824;
            } else {
                heightMode = Integer.MIN_VALUE;
            }
            maxHeight = View.MeasureSpec.makeMeasureSpec(maxHeight2, heightMode);
        } else if (params.height >= 0) {
            maxHeight = View.MeasureSpec.makeMeasureSpec(params.height, 1073741824);
        } else {
            maxHeight = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthMeasureSpec, maxHeight);
    }

    private int getChildMeasureSpec(int childStart, int childEnd, int childSize, int startMargin, int endMargin, int startPadding, int endPadding, int mySize) {
        int childSpecSize;
        int childSpecMode;
        int i = childStart;
        int i2 = childEnd;
        int i3 = childSize;
        int childSpecMode2 = 0;
        int childSpecSize2 = 0;
        boolean isUnspecified = mySize < 0;
        if (isUnspecified) {
            if (!this.mAllowBrokenMeasureSpecs) {
                if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
                    childSpecSize = Math.max(0, i2 - i);
                    childSpecMode = 1073741824;
                } else if (i3 >= 0) {
                    childSpecSize = childSize;
                    childSpecMode = 1073741824;
                } else {
                    childSpecSize = 0;
                    childSpecMode = 0;
                }
                return View.MeasureSpec.makeMeasureSpec(childSpecSize, childSpecMode);
            }
        }
        int tempStart = childStart;
        int tempEnd = childEnd;
        if (tempStart == Integer.MIN_VALUE) {
            tempStart = startPadding + startMargin;
        }
        if (tempEnd == Integer.MIN_VALUE) {
            tempEnd = (mySize - endPadding) - endMargin;
        }
        int maxAvailable = tempEnd - tempStart;
        int i4 = 1073741824;
        if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
            if (isUnspecified) {
                i4 = 0;
            }
            childSpecMode2 = i4;
            childSpecSize2 = Math.max(0, maxAvailable);
        } else if (i3 >= 0) {
            childSpecMode2 = 1073741824;
            if (maxAvailable >= 0) {
                childSpecSize2 = Math.min(maxAvailable, i3);
            } else {
                childSpecSize2 = childSize;
            }
        } else if (i3 == -1) {
            if (isUnspecified) {
                i4 = 0;
            }
            childSpecMode2 = i4;
            childSpecSize2 = Math.max(0, maxAvailable);
        } else if (i3 == -2) {
            if (maxAvailable >= 0) {
                childSpecMode2 = Integer.MIN_VALUE;
                childSpecSize2 = maxAvailable;
            } else {
                childSpecMode2 = 0;
                childSpecSize2 = 0;
            }
        }
        return View.MeasureSpec.makeMeasureSpec(childSpecSize2, childSpecMode2);
    }

    private boolean positionChildHorizontal(View child, LayoutParams params, int myWidth, boolean wrapContent) {
        int[] rules = params.getRules(getLayoutDirection());
        if (params.mLeft == Integer.MIN_VALUE && params.mRight != Integer.MIN_VALUE) {
            int unused = params.mLeft = params.mRight - child.getMeasuredWidth();
        } else if (params.mLeft != Integer.MIN_VALUE && params.mRight == Integer.MIN_VALUE) {
            int unused2 = params.mRight = params.mLeft + child.getMeasuredWidth();
        } else if (params.mLeft == Integer.MIN_VALUE && params.mRight == Integer.MIN_VALUE) {
            if (rules[13] == 0 && rules[14] == 0) {
                positionAtEdge(child, params, myWidth);
            } else {
                if (!wrapContent) {
                    centerHorizontal(child, params, myWidth);
                } else {
                    positionAtEdge(child, params, myWidth);
                }
                return true;
            }
        }
        if (rules[21] != 0) {
            return true;
        }
        return false;
    }

    private void positionAtEdge(View child, LayoutParams params, int myWidth) {
        if (isLayoutRtl()) {
            int unused = params.mRight = (myWidth - this.mPaddingRight) - params.rightMargin;
            int unused2 = params.mLeft = params.mRight - child.getMeasuredWidth();
            return;
        }
        int unused3 = params.mLeft = this.mPaddingLeft + params.leftMargin;
        int unused4 = params.mRight = params.mLeft + child.getMeasuredWidth();
    }

    private boolean positionChildVertical(View child, LayoutParams params, int myHeight, boolean wrapContent) {
        int[] rules = params.getRules();
        if (params.mTop == Integer.MIN_VALUE && params.mBottom != Integer.MIN_VALUE) {
            int unused = params.mTop = params.mBottom - child.getMeasuredHeight();
        } else if (params.mTop != Integer.MIN_VALUE && params.mBottom == Integer.MIN_VALUE) {
            int unused2 = params.mBottom = params.mTop + child.getMeasuredHeight();
        } else if (params.mTop == Integer.MIN_VALUE && params.mBottom == Integer.MIN_VALUE) {
            if (rules[13] == 0 && rules[15] == 0) {
                int unused3 = params.mTop = this.mPaddingTop + params.topMargin;
                int unused4 = params.mBottom = params.mTop + child.getMeasuredHeight();
            } else {
                if (!wrapContent) {
                    centerVertical(child, params, myHeight);
                } else {
                    int unused5 = params.mTop = this.mPaddingTop + params.topMargin;
                    int unused6 = params.mBottom = params.mTop + child.getMeasuredHeight();
                }
                return true;
            }
        }
        if (rules[12] != 0) {
            return true;
        }
        return false;
    }

    private void applyHorizontalSizeRules(LayoutParams childParams, int myWidth, int[] rules) {
        int unused = childParams.mLeft = Integer.MIN_VALUE;
        int unused2 = childParams.mRight = Integer.MIN_VALUE;
        LayoutParams anchorParams = getRelatedViewParams(rules, 0);
        if (anchorParams != null) {
            int unused3 = childParams.mRight = anchorParams.mLeft - (anchorParams.leftMargin + childParams.rightMargin);
        } else if (childParams.alignWithParent && rules[0] != 0 && myWidth >= 0) {
            int unused4 = childParams.mRight = (myWidth - this.mPaddingRight) - childParams.rightMargin;
        }
        LayoutParams anchorParams2 = getRelatedViewParams(rules, 1);
        if (anchorParams2 != null) {
            int unused5 = childParams.mLeft = anchorParams2.mRight + anchorParams2.rightMargin + childParams.leftMargin;
        } else if (childParams.alignWithParent && rules[1] != 0) {
            int unused6 = childParams.mLeft = this.mPaddingLeft + childParams.leftMargin;
        }
        LayoutParams anchorParams3 = getRelatedViewParams(rules, 5);
        if (anchorParams3 != null) {
            int unused7 = childParams.mLeft = anchorParams3.mLeft + childParams.leftMargin;
        } else if (childParams.alignWithParent && rules[5] != 0) {
            int unused8 = childParams.mLeft = this.mPaddingLeft + childParams.leftMargin;
        }
        LayoutParams anchorParams4 = getRelatedViewParams(rules, 7);
        if (anchorParams4 != null) {
            int unused9 = childParams.mRight = anchorParams4.mRight - childParams.rightMargin;
        } else if (childParams.alignWithParent && rules[7] != 0 && myWidth >= 0) {
            int unused10 = childParams.mRight = (myWidth - this.mPaddingRight) - childParams.rightMargin;
        }
        if (rules[9] != 0) {
            int unused11 = childParams.mLeft = this.mPaddingLeft + childParams.leftMargin;
        }
        if (rules[11] != 0 && myWidth >= 0) {
            int unused12 = childParams.mRight = (myWidth - this.mPaddingRight) - childParams.rightMargin;
        }
    }

    private void applyVerticalSizeRules(LayoutParams childParams, int myHeight, int myBaseline) {
        int[] rules = childParams.getRules();
        int baselineOffset = getRelatedViewBaselineOffset(rules);
        if (baselineOffset != -1) {
            if (myBaseline != -1) {
                baselineOffset -= myBaseline;
            }
            int unused = childParams.mTop = baselineOffset;
            int unused2 = childParams.mBottom = Integer.MIN_VALUE;
            return;
        }
        int unused3 = childParams.mTop = Integer.MIN_VALUE;
        int unused4 = childParams.mBottom = Integer.MIN_VALUE;
        LayoutParams anchorParams = getRelatedViewParams(rules, 2);
        if (anchorParams != null) {
            int unused5 = childParams.mBottom = anchorParams.mTop - (anchorParams.topMargin + childParams.bottomMargin);
        } else if (childParams.alignWithParent && rules[2] != 0 && myHeight >= 0) {
            int unused6 = childParams.mBottom = (myHeight - this.mPaddingBottom) - childParams.bottomMargin;
        }
        LayoutParams anchorParams2 = getRelatedViewParams(rules, 3);
        if (anchorParams2 != null) {
            int unused7 = childParams.mTop = anchorParams2.mBottom + anchorParams2.bottomMargin + childParams.topMargin;
        } else if (childParams.alignWithParent && rules[3] != 0) {
            int unused8 = childParams.mTop = this.mPaddingTop + childParams.topMargin;
        }
        LayoutParams anchorParams3 = getRelatedViewParams(rules, 6);
        if (anchorParams3 != null) {
            int unused9 = childParams.mTop = anchorParams3.mTop + childParams.topMargin;
        } else if (childParams.alignWithParent && rules[6] != 0) {
            int unused10 = childParams.mTop = this.mPaddingTop + childParams.topMargin;
        }
        LayoutParams anchorParams4 = getRelatedViewParams(rules, 8);
        if (anchorParams4 != null) {
            int unused11 = childParams.mBottom = anchorParams4.mBottom - childParams.bottomMargin;
        } else if (childParams.alignWithParent && rules[8] != 0 && myHeight >= 0) {
            int unused12 = childParams.mBottom = (myHeight - this.mPaddingBottom) - childParams.bottomMargin;
        }
        if (rules[10] != 0) {
            int unused13 = childParams.mTop = this.mPaddingTop + childParams.topMargin;
        }
        if (rules[12] != 0 && myHeight >= 0) {
            int unused14 = childParams.mBottom = (myHeight - this.mPaddingBottom) - childParams.bottomMargin;
        }
    }

    private View getRelatedView(int[] rules, int relation) {
        DependencyGraph.Node node;
        int id = rules[relation];
        if (id == 0 || (node = (DependencyGraph.Node) this.mGraph.mKeyNodes.get(id)) == null) {
            return null;
        }
        View v = node.view;
        while (v.getVisibility() == 8) {
            DependencyGraph.Node node2 = (DependencyGraph.Node) this.mGraph.mKeyNodes.get(((LayoutParams) v.getLayoutParams()).getRules(v.getLayoutDirection())[relation]);
            if (node2 == null || v == node2.view) {
                return null;
            }
            v = node2.view;
        }
        return v;
    }

    private LayoutParams getRelatedViewParams(int[] rules, int relation) {
        View v = getRelatedView(rules, relation);
        if (v == null || !(v.getLayoutParams() instanceof LayoutParams)) {
            return null;
        }
        return (LayoutParams) v.getLayoutParams();
    }

    private int getRelatedViewBaselineOffset(int[] rules) {
        int baseline;
        View v = getRelatedView(rules, 4);
        if (v == null || (baseline = v.getBaseline()) == -1 || !(v.getLayoutParams() instanceof LayoutParams)) {
            return -1;
        }
        return ((LayoutParams) v.getLayoutParams()).mTop + baseline;
    }

    private static void centerHorizontal(View child, LayoutParams params, int myWidth) {
        int childWidth = child.getMeasuredWidth();
        int left = (myWidth - childWidth) / 2;
        int unused = params.mLeft = left;
        int unused2 = params.mRight = left + childWidth;
    }

    private static void centerVertical(View child, LayoutParams params, int myHeight) {
        int childHeight = child.getMeasuredHeight();
        int top = (myHeight - childHeight) / 2;
        int unused = params.mTop = top;
        int unused2 = params.mBottom = top + childHeight;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams st = (LayoutParams) child.getLayoutParams();
                child.layout(st.mLeft, st.mTop, st.mRight, st.mBottom);
            }
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
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

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        if (this.mTopToBottomLeftToRightSet == null) {
            this.mTopToBottomLeftToRightSet = new TreeSet(new TopToBottomLeftToRightComparator());
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            this.mTopToBottomLeftToRightSet.add(getChildAt(i));
        }
        for (View view : this.mTopToBottomLeftToRightSet) {
            if (view.getVisibility() == 0 && view.dispatchPopulateAccessibilityEvent(event)) {
                this.mTopToBottomLeftToRightSet.clear();
                return true;
            }
        }
        this.mTopToBottomLeftToRightSet.clear();
        return false;
    }

    public CharSequence getAccessibilityClassName() {
        return RelativeLayout.class.getName();
    }

    private class TopToBottomLeftToRightComparator implements Comparator<View> {
        private TopToBottomLeftToRightComparator() {
        }

        public int compare(View first, View second) {
            int topDifference = first.getTop() - second.getTop();
            if (topDifference != 0) {
                return topDifference;
            }
            int leftDifference = first.getLeft() - second.getLeft();
            if (leftDifference != 0) {
                return leftDifference;
            }
            int heightDiference = first.getHeight() - second.getHeight();
            if (heightDiference != 0) {
                return heightDiference;
            }
            int widthDiference = first.getWidth() - second.getWidth();
            if (widthDiference != 0) {
                return widthDiference;
            }
            return 0;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category = "layout")
        public boolean alignWithParent;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public int mBottom;
        private int[] mInitialRules = new int[22];
        private boolean mIsRtlCompatibilityMode = false;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public int mLeft;
        private boolean mNeedsLayoutResolution;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public int mRight;
        /* access modifiers changed from: private */
        @ViewDebug.ExportedProperty(category = "layout", indexMapping = {@ViewDebug.IntToString(from = 2, to = "above"), @ViewDebug.IntToString(from = 4, to = "alignBaseline"), @ViewDebug.IntToString(from = 8, to = "alignBottom"), @ViewDebug.IntToString(from = 5, to = "alignLeft"), @ViewDebug.IntToString(from = 12, to = "alignParentBottom"), @ViewDebug.IntToString(from = 9, to = "alignParentLeft"), @ViewDebug.IntToString(from = 11, to = "alignParentRight"), @ViewDebug.IntToString(from = 10, to = "alignParentTop"), @ViewDebug.IntToString(from = 7, to = "alignRight"), @ViewDebug.IntToString(from = 6, to = "alignTop"), @ViewDebug.IntToString(from = 3, to = "below"), @ViewDebug.IntToString(from = 14, to = "centerHorizontal"), @ViewDebug.IntToString(from = 13, to = "center"), @ViewDebug.IntToString(from = 15, to = "centerVertical"), @ViewDebug.IntToString(from = 0, to = "leftOf"), @ViewDebug.IntToString(from = 1, to = "rightOf"), @ViewDebug.IntToString(from = 18, to = "alignStart"), @ViewDebug.IntToString(from = 19, to = "alignEnd"), @ViewDebug.IntToString(from = 20, to = "alignParentStart"), @ViewDebug.IntToString(from = 21, to = "alignParentEnd"), @ViewDebug.IntToString(from = 16, to = "startOf"), @ViewDebug.IntToString(from = 17, to = "endOf")}, mapping = {@ViewDebug.IntToString(from = -1, to = "true"), @ViewDebug.IntToString(from = 0, to = "false/NO_ID")}, resolveId = true)
        public int[] mRules = new int[22];
        private boolean mRulesChanged = false;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public int mTop;

        static /* synthetic */ int access$112(LayoutParams x0, int x1) {
            int i = x0.mLeft + x1;
            x0.mLeft = i;
            return i;
        }

        static /* synthetic */ int access$120(LayoutParams x0, int x1) {
            int i = x0.mLeft - x1;
            x0.mLeft = i;
            return i;
        }

        static /* synthetic */ int access$212(LayoutParams x0, int x1) {
            int i = x0.mRight + x1;
            x0.mRight = i;
            return i;
        }

        static /* synthetic */ int access$220(LayoutParams x0, int x1) {
            int i = x0.mRight - x1;
            x0.mRight = i;
            return i;
        }

        static /* synthetic */ int access$312(LayoutParams x0, int x1) {
            int i = x0.mBottom + x1;
            x0.mBottom = i;
            return i;
        }

        static /* synthetic */ int access$412(LayoutParams x0, int x1) {
            int i = x0.mTop + x1;
            x0.mTop = i;
            return i;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.RelativeLayout_Layout);
            this.mIsRtlCompatibilityMode = c.getApplicationInfo().targetSdkVersion < 17 || !c.getApplicationInfo().hasRtlSupport();
            int[] rules = this.mRules;
            int[] initialRules = this.mInitialRules;
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                int i2 = -1;
                switch (attr) {
                    case 0:
                        rules[0] = a.getResourceId(attr, 0);
                        break;
                    case 1:
                        rules[1] = a.getResourceId(attr, 0);
                        break;
                    case 2:
                        rules[2] = a.getResourceId(attr, 0);
                        break;
                    case 3:
                        rules[3] = a.getResourceId(attr, 0);
                        break;
                    case 4:
                        rules[4] = a.getResourceId(attr, 0);
                        break;
                    case 5:
                        rules[5] = a.getResourceId(attr, 0);
                        break;
                    case 6:
                        rules[6] = a.getResourceId(attr, 0);
                        break;
                    case 7:
                        rules[7] = a.getResourceId(attr, 0);
                        break;
                    case 8:
                        rules[8] = a.getResourceId(attr, 0);
                        break;
                    case 9:
                        rules[9] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 10:
                        rules[10] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 11:
                        rules[11] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 12:
                        rules[12] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 13:
                        rules[13] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 14:
                        rules[14] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 15:
                        rules[15] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 16:
                        this.alignWithParent = a.getBoolean(attr, false);
                        break;
                    case 17:
                        rules[16] = a.getResourceId(attr, 0);
                        break;
                    case 18:
                        rules[17] = a.getResourceId(attr, 0);
                        break;
                    case 19:
                        rules[18] = a.getResourceId(attr, 0);
                        break;
                    case 20:
                        rules[19] = a.getResourceId(attr, 0);
                        break;
                    case 21:
                        rules[20] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                    case 22:
                        rules[21] = !a.getBoolean(attr, false) ? 0 : i2;
                        break;
                }
            }
            this.mRulesChanged = true;
            System.arraycopy(rules, 0, initialRules, 0, 22);
            a.recycle();
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.MarginLayoutParams) source);
            this.mIsRtlCompatibilityMode = source.mIsRtlCompatibilityMode;
            this.mRulesChanged = source.mRulesChanged;
            this.alignWithParent = source.alignWithParent;
            System.arraycopy(source.mRules, 0, this.mRules, 0, 22);
            System.arraycopy(source.mInitialRules, 0, this.mInitialRules, 0, 22);
        }

        public String debug(String output) {
            return output + "ViewGroup.LayoutParams={ width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " }";
        }

        public void addRule(int verb) {
            addRule(verb, -1);
        }

        public void addRule(int verb, int subject) {
            if (!this.mNeedsLayoutResolution && isRelativeRule(verb) && this.mInitialRules[verb] != 0 && subject == 0) {
                this.mNeedsLayoutResolution = true;
            }
            this.mRules[verb] = subject;
            this.mInitialRules[verb] = subject;
            this.mRulesChanged = true;
        }

        public void removeRule(int verb) {
            addRule(verb, 0);
        }

        public int getRule(int verb) {
            return this.mRules[verb];
        }

        private boolean hasRelativeRules() {
            return (this.mInitialRules[16] == 0 && this.mInitialRules[17] == 0 && this.mInitialRules[18] == 0 && this.mInitialRules[19] == 0 && this.mInitialRules[20] == 0 && this.mInitialRules[21] == 0) ? false : true;
        }

        private boolean isRelativeRule(int rule) {
            return rule == 16 || rule == 17 || rule == 18 || rule == 19 || rule == 20 || rule == 21;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:95:0x0175, code lost:
            if (r0.mRules[11] != 0) goto L_0x017a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void resolveRules(int r18) {
            /*
                r17 = this;
                r0 = r17
                r1 = 1
                r2 = 0
                r3 = r18
                if (r3 != r1) goto L_0x000a
                r4 = r1
                goto L_0x000b
            L_0x000a:
                r4 = r2
            L_0x000b:
                int[] r5 = r0.mInitialRules
                int[] r6 = r0.mRules
                r7 = 22
                java.lang.System.arraycopy(r5, r2, r6, r2, r7)
                boolean r5 = r0.mIsRtlCompatibilityMode
                r7 = 9
                r8 = 7
                r9 = 5
                r10 = 21
                r11 = 20
                r12 = 17
                r13 = 16
                r14 = 19
                r15 = 18
                if (r5 == 0) goto L_0x00bc
                int[] r5 = r0.mRules
                r5 = r5[r15]
                if (r5 == 0) goto L_0x0040
                int[] r5 = r0.mRules
                r5 = r5[r9]
                if (r5 != 0) goto L_0x003c
                int[] r5 = r0.mRules
                int[] r6 = r0.mRules
                r6 = r6[r15]
                r5[r9] = r6
            L_0x003c:
                int[] r5 = r0.mRules
                r5[r15] = r2
            L_0x0040:
                int[] r5 = r0.mRules
                r5 = r5[r14]
                if (r5 == 0) goto L_0x0058
                int[] r5 = r0.mRules
                r5 = r5[r8]
                if (r5 != 0) goto L_0x0054
                int[] r5 = r0.mRules
                int[] r6 = r0.mRules
                r6 = r6[r14]
                r5[r8] = r6
            L_0x0054:
                int[] r5 = r0.mRules
                r5[r14] = r2
            L_0x0058:
                int[] r5 = r0.mRules
                r5 = r5[r13]
                if (r5 == 0) goto L_0x0070
                int[] r5 = r0.mRules
                r5 = r5[r2]
                if (r5 != 0) goto L_0x006c
                int[] r5 = r0.mRules
                int[] r6 = r0.mRules
                r6 = r6[r13]
                r5[r2] = r6
            L_0x006c:
                int[] r5 = r0.mRules
                r5[r13] = r2
            L_0x0070:
                int[] r5 = r0.mRules
                r5 = r5[r12]
                if (r5 == 0) goto L_0x0088
                int[] r5 = r0.mRules
                r5 = r5[r1]
                if (r5 != 0) goto L_0x0084
                int[] r5 = r0.mRules
                int[] r6 = r0.mRules
                r6 = r6[r12]
                r5[r1] = r6
            L_0x0084:
                int[] r1 = r0.mRules
                r1[r12] = r2
            L_0x0088:
                int[] r1 = r0.mRules
                r1 = r1[r11]
                if (r1 == 0) goto L_0x00a0
                int[] r1 = r0.mRules
                r1 = r1[r7]
                if (r1 != 0) goto L_0x009c
                int[] r1 = r0.mRules
                int[] r5 = r0.mRules
                r5 = r5[r11]
                r1[r7] = r5
            L_0x009c:
                int[] r1 = r0.mRules
                r1[r11] = r2
            L_0x00a0:
                int[] r1 = r0.mRules
                r1 = r1[r10]
                if (r1 == 0) goto L_0x01af
                int[] r1 = r0.mRules
                r5 = 11
                r1 = r1[r5]
                if (r1 != 0) goto L_0x00b6
                int[] r1 = r0.mRules
                int[] r6 = r0.mRules
                r6 = r6[r10]
                r1[r5] = r6
            L_0x00b6:
                int[] r1 = r0.mRules
                r1[r10] = r2
                goto L_0x01af
            L_0x00bc:
                int[] r5 = r0.mRules
                r5 = r5[r15]
                if (r5 != 0) goto L_0x00c8
                int[] r5 = r0.mRules
                r5 = r5[r14]
                if (r5 == 0) goto L_0x00dc
            L_0x00c8:
                int[] r5 = r0.mRules
                r5 = r5[r9]
                if (r5 != 0) goto L_0x00d4
                int[] r5 = r0.mRules
                r5 = r5[r8]
                if (r5 == 0) goto L_0x00dc
            L_0x00d4:
                int[] r5 = r0.mRules
                r5[r9] = r2
                int[] r5 = r0.mRules
                r5[r8] = r2
            L_0x00dc:
                int[] r5 = r0.mRules
                r5 = r5[r15]
                if (r5 == 0) goto L_0x00f3
                int[] r5 = r0.mRules
                if (r4 == 0) goto L_0x00e8
                r6 = r8
                goto L_0x00e9
            L_0x00e8:
                r6 = r9
            L_0x00e9:
                int[] r8 = r0.mRules
                r8 = r8[r15]
                r5[r6] = r8
                int[] r5 = r0.mRules
                r5[r15] = r2
            L_0x00f3:
                int[] r5 = r0.mRules
                r5 = r5[r14]
                if (r5 == 0) goto L_0x010c
                int[] r5 = r0.mRules
                if (r4 == 0) goto L_0x0100
                r16 = r9
                goto L_0x0102
            L_0x0100:
                r16 = 7
            L_0x0102:
                int[] r6 = r0.mRules
                r6 = r6[r14]
                r5[r16] = r6
                int[] r5 = r0.mRules
                r5[r14] = r2
            L_0x010c:
                int[] r5 = r0.mRules
                r5 = r5[r13]
                if (r5 != 0) goto L_0x0118
                int[] r5 = r0.mRules
                r5 = r5[r12]
                if (r5 == 0) goto L_0x012c
            L_0x0118:
                int[] r5 = r0.mRules
                r5 = r5[r2]
                if (r5 != 0) goto L_0x0124
                int[] r5 = r0.mRules
                r5 = r5[r1]
                if (r5 == 0) goto L_0x012c
            L_0x0124:
                int[] r5 = r0.mRules
                r5[r2] = r2
                int[] r5 = r0.mRules
                r5[r1] = r2
            L_0x012c:
                int[] r5 = r0.mRules
                r5 = r5[r13]
                if (r5 == 0) goto L_0x0143
                int[] r5 = r0.mRules
                if (r4 == 0) goto L_0x0138
                r6 = r1
                goto L_0x0139
            L_0x0138:
                r6 = r2
            L_0x0139:
                int[] r8 = r0.mRules
                r8 = r8[r13]
                r5[r6] = r8
                int[] r5 = r0.mRules
                r5[r13] = r2
            L_0x0143:
                int[] r5 = r0.mRules
                r5 = r5[r12]
                if (r5 == 0) goto L_0x0159
                int[] r5 = r0.mRules
                if (r4 == 0) goto L_0x014f
                r1 = r2
            L_0x014f:
                int[] r6 = r0.mRules
                r6 = r6[r12]
                r5[r1] = r6
                int[] r1 = r0.mRules
                r1[r12] = r2
            L_0x0159:
                int[] r1 = r0.mRules
                r1 = r1[r11]
                if (r1 != 0) goto L_0x0169
                int[] r1 = r0.mRules
                r1 = r1[r10]
                if (r1 == 0) goto L_0x0166
                goto L_0x0169
            L_0x0166:
                r5 = 11
                goto L_0x0182
            L_0x0169:
                int[] r1 = r0.mRules
                r1 = r1[r7]
                if (r1 != 0) goto L_0x0178
                int[] r1 = r0.mRules
                r5 = 11
                r1 = r1[r5]
                if (r1 == 0) goto L_0x0182
                goto L_0x017a
            L_0x0178:
                r5 = 11
            L_0x017a:
                int[] r1 = r0.mRules
                r1[r7] = r2
                int[] r1 = r0.mRules
                r1[r5] = r2
            L_0x0182:
                int[] r1 = r0.mRules
                r1 = r1[r11]
                if (r1 == 0) goto L_0x0199
                int[] r1 = r0.mRules
                if (r4 == 0) goto L_0x018e
                r6 = r5
                goto L_0x018f
            L_0x018e:
                r6 = r7
            L_0x018f:
                int[] r8 = r0.mRules
                r8 = r8[r11]
                r1[r6] = r8
                int[] r1 = r0.mRules
                r1[r11] = r2
            L_0x0199:
                int[] r1 = r0.mRules
                r1 = r1[r10]
                if (r1 == 0) goto L_0x01af
                int[] r1 = r0.mRules
                if (r4 == 0) goto L_0x01a5
                r5 = r7
            L_0x01a5:
                int[] r6 = r0.mRules
                r6 = r6[r10]
                r1[r5] = r6
                int[] r1 = r0.mRules
                r1[r10] = r2
            L_0x01af:
                r0.mRulesChanged = r2
                r0.mNeedsLayoutResolution = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.RelativeLayout.LayoutParams.resolveRules(int):void");
        }

        public int[] getRules(int layoutDirection) {
            resolveLayoutDirection(layoutDirection);
            return this.mRules;
        }

        public int[] getRules() {
            return this.mRules;
        }

        public void resolveLayoutDirection(int layoutDirection) {
            if (shouldResolveLayoutDirection(layoutDirection)) {
                resolveRules(layoutDirection);
            }
            super.resolveLayoutDirection(layoutDirection);
        }

        private boolean shouldResolveLayoutDirection(int layoutDirection) {
            return (this.mNeedsLayoutResolution || hasRelativeRules()) && (this.mRulesChanged || layoutDirection != getLayoutDirection());
        }

        /* access modifiers changed from: protected */
        public void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("layout:alignWithParent", this.alignWithParent);
        }

        public static final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mAboveId;
            private int mAlignBaselineId;
            private int mAlignBottomId;
            private int mAlignEndId;
            private int mAlignLeftId;
            private int mAlignParentBottomId;
            private int mAlignParentEndId;
            private int mAlignParentLeftId;
            private int mAlignParentRightId;
            private int mAlignParentStartId;
            private int mAlignParentTopId;
            private int mAlignRightId;
            private int mAlignStartId;
            private int mAlignTopId;
            private int mAlignWithParentIfMissingId;
            private int mBelowId;
            private int mCenterHorizontalId;
            private int mCenterInParentId;
            private int mCenterVerticalId;
            private boolean mPropertiesMapped;
            private int mToEndOfId;
            private int mToLeftOfId;
            private int mToRightOfId;
            private int mToStartOfId;

            public void mapProperties(PropertyMapper propertyMapper) {
                this.mPropertiesMapped = true;
                this.mAboveId = propertyMapper.mapResourceId("layout_above", 16843140);
                this.mAlignBaselineId = propertyMapper.mapResourceId("layout_alignBaseline", 16843142);
                this.mAlignBottomId = propertyMapper.mapResourceId("layout_alignBottom", 16843146);
                this.mAlignEndId = propertyMapper.mapResourceId("layout_alignEnd", 16843706);
                this.mAlignLeftId = propertyMapper.mapResourceId("layout_alignLeft", 16843143);
                this.mAlignParentBottomId = propertyMapper.mapBoolean("layout_alignParentBottom", 16843150);
                this.mAlignParentEndId = propertyMapper.mapBoolean("layout_alignParentEnd", 16843708);
                this.mAlignParentLeftId = propertyMapper.mapBoolean("layout_alignParentLeft", 16843147);
                this.mAlignParentRightId = propertyMapper.mapBoolean("layout_alignParentRight", 16843149);
                this.mAlignParentStartId = propertyMapper.mapBoolean("layout_alignParentStart", 16843707);
                this.mAlignParentTopId = propertyMapper.mapBoolean("layout_alignParentTop", 16843148);
                this.mAlignRightId = propertyMapper.mapResourceId("layout_alignRight", 16843145);
                this.mAlignStartId = propertyMapper.mapResourceId("layout_alignStart", 16843705);
                this.mAlignTopId = propertyMapper.mapResourceId("layout_alignTop", 16843144);
                this.mAlignWithParentIfMissingId = propertyMapper.mapBoolean("layout_alignWithParentIfMissing", 16843154);
                this.mBelowId = propertyMapper.mapResourceId("layout_below", 16843141);
                this.mCenterHorizontalId = propertyMapper.mapBoolean("layout_centerHorizontal", 16843152);
                this.mCenterInParentId = propertyMapper.mapBoolean("layout_centerInParent", 16843151);
                this.mCenterVerticalId = propertyMapper.mapBoolean("layout_centerVertical", 16843153);
                this.mToEndOfId = propertyMapper.mapResourceId("layout_toEndOf", 16843704);
                this.mToLeftOfId = propertyMapper.mapResourceId("layout_toLeftOf", 16843138);
                this.mToRightOfId = propertyMapper.mapResourceId("layout_toRightOf", 16843139);
                this.mToStartOfId = propertyMapper.mapResourceId("layout_toStartOf", 16843703);
            }

            public void readProperties(LayoutParams node, PropertyReader propertyReader) {
                if (this.mPropertiesMapped) {
                    int[] rules = node.getRules();
                    propertyReader.readResourceId(this.mAboveId, rules[2]);
                    propertyReader.readResourceId(this.mAlignBaselineId, rules[4]);
                    propertyReader.readResourceId(this.mAlignBottomId, rules[8]);
                    propertyReader.readResourceId(this.mAlignEndId, rules[19]);
                    propertyReader.readResourceId(this.mAlignLeftId, rules[5]);
                    propertyReader.readBoolean(this.mAlignParentBottomId, rules[12] == -1);
                    propertyReader.readBoolean(this.mAlignParentEndId, rules[21] == -1);
                    propertyReader.readBoolean(this.mAlignParentLeftId, rules[9] == -1);
                    propertyReader.readBoolean(this.mAlignParentRightId, rules[11] == -1);
                    propertyReader.readBoolean(this.mAlignParentStartId, rules[20] == -1);
                    propertyReader.readBoolean(this.mAlignParentTopId, rules[10] == -1);
                    propertyReader.readResourceId(this.mAlignRightId, rules[7]);
                    propertyReader.readResourceId(this.mAlignStartId, rules[18]);
                    propertyReader.readResourceId(this.mAlignTopId, rules[6]);
                    propertyReader.readBoolean(this.mAlignWithParentIfMissingId, node.alignWithParent);
                    propertyReader.readResourceId(this.mBelowId, rules[3]);
                    propertyReader.readBoolean(this.mCenterHorizontalId, rules[14] == -1);
                    propertyReader.readBoolean(this.mCenterInParentId, rules[13] == -1);
                    propertyReader.readBoolean(this.mCenterVerticalId, rules[15] == -1);
                    propertyReader.readResourceId(this.mToEndOfId, rules[17]);
                    propertyReader.readResourceId(this.mToLeftOfId, rules[0]);
                    propertyReader.readResourceId(this.mToRightOfId, rules[1]);
                    propertyReader.readResourceId(this.mToStartOfId, rules[16]);
                    return;
                }
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
        }
    }

    private static class DependencyGraph {
        /* access modifiers changed from: private */
        public SparseArray<Node> mKeyNodes;
        private ArrayList<Node> mNodes;
        private ArrayDeque<Node> mRoots;

        private DependencyGraph() {
            this.mNodes = new ArrayList<>();
            this.mKeyNodes = new SparseArray<>();
            this.mRoots = new ArrayDeque<>();
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            ArrayList<Node> nodes = this.mNodes;
            int count = nodes.size();
            for (int i = 0; i < count; i++) {
                nodes.get(i).release();
            }
            nodes.clear();
            this.mKeyNodes.clear();
            this.mRoots.clear();
        }

        /* access modifiers changed from: package-private */
        public void add(View view) {
            int id = view.getId();
            Node node = Node.acquire(view);
            if (id != -1) {
                this.mKeyNodes.put(id, node);
            }
            this.mNodes.add(node);
        }

        /* access modifiers changed from: package-private */
        public void getSortedViews(View[] sorted, int... rules) {
            ArrayDeque<Node> roots = findRoots(rules);
            int index = 0;
            while (true) {
                Node pollLast = roots.pollLast();
                Node node = pollLast;
                if (pollLast == null) {
                    break;
                }
                View view = node.view;
                int key = view.getId();
                int index2 = index + 1;
                sorted[index] = view;
                ArrayMap<Node, DependencyGraph> dependents = node.dependents;
                int count = dependents.size();
                for (int i = 0; i < count; i++) {
                    Node dependent = dependents.keyAt(i);
                    SparseArray<Node> dependencies = dependent.dependencies;
                    dependencies.remove(key);
                    if (dependencies.size() == 0) {
                        roots.add(dependent);
                    }
                }
                index = index2;
            }
            if (index < sorted.length) {
                throw new IllegalStateException("Circular dependencies cannot exist in RelativeLayout");
            }
        }

        private ArrayDeque<Node> findRoots(int[] rulesFilter) {
            Node dependency;
            SparseArray<Node> keyNodes = this.mKeyNodes;
            ArrayList<Node> nodes = this.mNodes;
            int count = nodes.size();
            for (int i = 0; i < count; i++) {
                Node node = nodes.get(i);
                node.dependents.clear();
                node.dependencies.clear();
            }
            for (int i2 = 0; i2 < count; i2++) {
                Node node2 = nodes.get(i2);
                int[] rules = ((LayoutParams) node2.view.getLayoutParams()).mRules;
                for (int i3 : rulesFilter) {
                    int rule = rules[i3];
                    if (!((rule <= 0 && !ResourceId.isValid(rule)) || (dependency = keyNodes.get(rule)) == null || dependency == node2)) {
                        dependency.dependents.put(node2, this);
                        node2.dependencies.put(rule, dependency);
                    }
                }
            }
            ArrayDeque<Node> roots = this.mRoots;
            roots.clear();
            for (int i4 = 0; i4 < count; i4++) {
                Node node3 = nodes.get(i4);
                if (node3.dependencies.size() == 0) {
                    roots.addLast(node3);
                }
            }
            return roots;
        }

        static class Node {
            private static final int POOL_LIMIT = 100;
            private static final Pools.SynchronizedPool<Node> sPool = new Pools.SynchronizedPool<>(100);
            final SparseArray<Node> dependencies = new SparseArray<>();
            final ArrayMap<Node, DependencyGraph> dependents = new ArrayMap<>();
            View view;

            Node() {
            }

            static Node acquire(View view2) {
                Node node = sPool.acquire();
                if (node == null) {
                    node = new Node();
                }
                node.view = view2;
                return node;
            }

            /* access modifiers changed from: package-private */
            public void release() {
                this.view = null;
                this.dependents.clear();
                this.dependencies.clear();
                sPool.release(this);
            }
        }
    }
}
