package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(@StyleRes int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || this.mMenu == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX WARNING: Removed duplicated region for block: B:136:0x02aa  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x02d9  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01e1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onMeasureExactFormat(int r42, int r43) {
        /*
            r41 = this;
            r0 = r41
            int r1 = android.view.View.MeasureSpec.getMode(r43)
            int r2 = android.view.View.MeasureSpec.getSize(r42)
            int r3 = android.view.View.MeasureSpec.getSize(r43)
            int r4 = r41.getPaddingLeft()
            int r5 = r41.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r41.getPaddingTop()
            int r6 = r41.getPaddingBottom()
            int r5 = r5 + r6
            r6 = -2
            r7 = r43
            int r6 = getChildMeasureSpec(r7, r5, r6)
            int r2 = r2 - r4
            int r8 = r0.mMinCellSize
            int r8 = r2 / r8
            int r9 = r0.mMinCellSize
            int r9 = r2 % r9
            r10 = 0
            if (r8 != 0) goto L_0x0037
            r0.setMeasuredDimension(r2, r10)
            return
        L_0x0037:
            int r11 = r0.mMinCellSize
            int r12 = r9 / r8
            int r11 = r11 + r12
            r12 = r8
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            int r10 = r41.getChildCount()
            r21 = r3
            r3 = r13
            r13 = r16
            r16 = r15
            r15 = r14
            r14 = r12
            r12 = 0
        L_0x0054:
            r22 = r4
            if (r12 >= r10) goto L_0x00f0
            android.view.View r4 = r0.getChildAt(r12)
            int r7 = r4.getVisibility()
            r23 = r8
            r8 = 8
            if (r7 != r8) goto L_0x006c
            r26 = r5
            r24 = r9
            goto L_0x00e2
        L_0x006c:
            boolean r7 = r4 instanceof android.support.v7.view.menu.ActionMenuItemView
            int r13 = r13 + 1
            if (r7 == 0) goto L_0x007f
            int r8 = r0.mGeneratedItemPadding
            r24 = r9
            int r9 = r0.mGeneratedItemPadding
            r25 = r13
            r13 = 0
            r4.setPadding(r8, r13, r9, r13)
            goto L_0x0084
        L_0x007f:
            r24 = r9
            r25 = r13
            r13 = 0
        L_0x0084:
            android.view.ViewGroup$LayoutParams r8 = r4.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r8 = (android.support.v7.widget.ActionMenuView.LayoutParams) r8
            r8.expanded = r13
            r8.extraPixels = r13
            r8.cellsUsed = r13
            r8.expandable = r13
            r8.leftMargin = r13
            r8.rightMargin = r13
            if (r7 == 0) goto L_0x00a3
            r9 = r4
            android.support.v7.view.menu.ActionMenuItemView r9 = (android.support.v7.view.menu.ActionMenuItemView) r9
            boolean r9 = r9.hasText()
            if (r9 == 0) goto L_0x00a3
            r9 = 1
            goto L_0x00a4
        L_0x00a3:
            r9 = 0
        L_0x00a4:
            r8.preventEdgeOffset = r9
            boolean r9 = r8.isOverflowButton
            if (r9 == 0) goto L_0x00ac
            r9 = 1
            goto L_0x00ad
        L_0x00ac:
            r9 = r14
        L_0x00ad:
            int r13 = measureChildForCells(r4, r11, r9, r6, r5)
            int r15 = java.lang.Math.max(r15, r13)
            r26 = r5
            boolean r5 = r8.expandable
            if (r5 == 0) goto L_0x00bd
            int r16 = r16 + 1
        L_0x00bd:
            boolean r5 = r8.isOverflowButton
            if (r5 == 0) goto L_0x00c3
            r17 = 1
        L_0x00c3:
            int r14 = r14 - r13
            int r5 = r4.getMeasuredHeight()
            int r3 = java.lang.Math.max(r3, r5)
            r5 = 1
            if (r13 != r5) goto L_0x00de
            int r5 = r5 << r12
            r28 = r3
            r27 = r4
            long r3 = (long) r5
            long r3 = r18 | r3
            r18 = r3
            r13 = r25
            r3 = r28
            goto L_0x00e2
        L_0x00de:
            r28 = r3
            r13 = r25
        L_0x00e2:
            int r12 = r12 + 1
            r4 = r22
            r8 = r23
            r9 = r24
            r5 = r26
            r7 = r43
            goto L_0x0054
        L_0x00f0:
            r26 = r5
            r23 = r8
            r24 = r9
            r4 = 2
            if (r17 == 0) goto L_0x00fd
            if (r13 != r4) goto L_0x00fd
            r5 = 1
            goto L_0x00fe
        L_0x00fd:
            r5 = 0
        L_0x00fe:
            r7 = 0
        L_0x00ff:
            if (r16 <= 0) goto L_0x01c7
            if (r14 <= 0) goto L_0x01c7
            r12 = 2147483647(0x7fffffff, float:NaN)
            r27 = 0
            r25 = 0
            r8 = r12
            r4 = r25
            r12 = 0
        L_0x010e:
            r9 = r12
            if (r9 >= r10) goto L_0x0153
            android.view.View r12 = r0.getChildAt(r9)
            android.view.ViewGroup$LayoutParams r25 = r12.getLayoutParams()
            r31 = r7
            r7 = r25
            android.support.v7.widget.ActionMenuView$LayoutParams r7 = (android.support.v7.widget.ActionMenuView.LayoutParams) r7
            r32 = r12
            boolean r12 = r7.expandable
            if (r12 != 0) goto L_0x0128
            r34 = r13
            goto L_0x014c
        L_0x0128:
            int r12 = r7.cellsUsed
            if (r12 >= r8) goto L_0x013c
            int r8 = r7.cellsUsed
            r33 = r8
            r12 = 1
            int r8 = r12 << r9
            r34 = r13
            long r12 = (long) r8
            r4 = 1
            r27 = r12
            r8 = r33
            goto L_0x014c
        L_0x013c:
            r34 = r13
            int r12 = r7.cellsUsed
            if (r12 != r8) goto L_0x014c
            r12 = 1
            int r13 = r12 << r9
            long r12 = (long) r13
            long r12 = r27 | r12
            int r4 = r4 + 1
            r27 = r12
        L_0x014c:
            int r12 = r9 + 1
            r7 = r31
            r13 = r34
            goto L_0x010e
        L_0x0153:
            r31 = r7
            r34 = r13
            long r18 = r18 | r27
            if (r4 <= r14) goto L_0x015f
            r37 = r5
            goto L_0x01cd
        L_0x015f:
            int r8 = r8 + 1
            r7 = 0
        L_0x0162:
            if (r7 >= r10) goto L_0x01bc
            android.view.View r9 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r12 = r9.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r12 = (android.support.v7.widget.ActionMenuView.LayoutParams) r12
            r35 = r4
            r13 = 1
            int r4 = r13 << r7
            r36 = r14
            long r13 = (long) r4
            long r13 = r27 & r13
            r29 = 0
            int r4 = (r13 > r29 ? 1 : (r13 == r29 ? 0 : -1))
            if (r4 != 0) goto L_0x018d
            int r4 = r12.cellsUsed
            if (r4 != r8) goto L_0x0188
            r4 = 1
            int r13 = r4 << r7
            long r13 = (long) r13
            long r18 = r18 | r13
        L_0x0188:
            r37 = r5
            r14 = r36
            goto L_0x01b5
        L_0x018d:
            r4 = 1
            if (r5 == 0) goto L_0x01a7
            boolean r13 = r12.preventEdgeOffset
            if (r13 == 0) goto L_0x01a7
            r14 = r36
            if (r14 != r4) goto L_0x01a4
            int r4 = r0.mGeneratedItemPadding
            int r4 = r4 + r11
            int r13 = r0.mGeneratedItemPadding
            r37 = r5
            r5 = 0
            r9.setPadding(r4, r5, r13, r5)
            goto L_0x01ab
        L_0x01a4:
            r37 = r5
            goto L_0x01ab
        L_0x01a7:
            r37 = r5
            r14 = r36
        L_0x01ab:
            int r4 = r12.cellsUsed
            r5 = 1
            int r4 = r4 + r5
            r12.cellsUsed = r4
            r12.expanded = r5
            int r14 = r14 + -1
        L_0x01b5:
            int r7 = r7 + 1
            r4 = r35
            r5 = r37
            goto L_0x0162
        L_0x01bc:
            r35 = r4
            r37 = r5
            r7 = 1
            r13 = r34
            r4 = 2
            goto L_0x00ff
        L_0x01c7:
            r37 = r5
            r31 = r7
            r34 = r13
        L_0x01cd:
            if (r17 != 0) goto L_0x01d6
            r13 = r34
            r4 = 1
            if (r13 != r4) goto L_0x01d8
            r4 = 1
            goto L_0x01d9
        L_0x01d6:
            r13 = r34
        L_0x01d8:
            r4 = 0
        L_0x01d9:
            if (r14 <= 0) goto L_0x02aa
            r7 = 0
            int r5 = (r18 > r7 ? 1 : (r18 == r7 ? 0 : -1))
            if (r5 == 0) goto L_0x02aa
            int r5 = r13 + -1
            if (r14 < r5) goto L_0x01ef
            if (r4 != 0) goto L_0x01ef
            r5 = 1
            if (r15 <= r5) goto L_0x01eb
            goto L_0x01ef
        L_0x01eb:
            r39 = r4
            goto L_0x02ac
        L_0x01ef:
            int r5 = java.lang.Long.bitCount(r18)
            float r5 = (float) r5
            if (r4 != 0) goto L_0x0235
            r7 = 1
            long r7 = r18 & r7
            r27 = 0
            int r7 = (r7 > r27 ? 1 : (r7 == r27 ? 0 : -1))
            r8 = 1056964608(0x3f000000, float:0.5)
            if (r7 == 0) goto L_0x0213
            r7 = 0
            android.view.View r9 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r9 = (android.support.v7.widget.ActionMenuView.LayoutParams) r9
            boolean r12 = r9.preventEdgeOffset
            if (r12 != 0) goto L_0x0214
            float r5 = r5 - r8
            goto L_0x0214
        L_0x0213:
            r7 = 0
        L_0x0214:
            int r9 = r10 + -1
            r12 = 1
            int r9 = r12 << r9
            long r7 = (long) r9
            long r7 = r18 & r7
            r27 = 0
            int r7 = (r7 > r27 ? 1 : (r7 == r27 ? 0 : -1))
            if (r7 == 0) goto L_0x0235
            int r7 = r10 + -1
            android.view.View r7 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r7 = (android.support.v7.widget.ActionMenuView.LayoutParams) r7
            boolean r8 = r7.preventEdgeOffset
            if (r8 != 0) goto L_0x0235
            r8 = 1056964608(0x3f000000, float:0.5)
            float r5 = r5 - r8
        L_0x0235:
            r7 = 0
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x0240
            int r7 = r14 * r11
            float r7 = (float) r7
            float r7 = r7 / r5
            int r7 = (int) r7
            goto L_0x0241
        L_0x0240:
            r7 = 0
        L_0x0241:
            r8 = 0
        L_0x0242:
            if (r8 >= r10) goto L_0x02a4
            r9 = 1
            int r12 = r9 << r8
            r39 = r4
            r40 = r5
            long r4 = (long) r12
            long r4 = r18 & r4
            r27 = 0
            int r4 = (r4 > r27 ? 1 : (r4 == r27 ? 0 : -1))
            if (r4 != 0) goto L_0x0255
            goto L_0x0275
        L_0x0255:
            android.view.View r4 = r0.getChildAt(r8)
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r5 = (android.support.v7.widget.ActionMenuView.LayoutParams) r5
            boolean r9 = r4 instanceof android.support.v7.view.menu.ActionMenuItemView
            if (r9 == 0) goto L_0x0279
            r5.extraPixels = r7
            r9 = 1
            r5.expanded = r9
            if (r8 != 0) goto L_0x0273
            boolean r9 = r5.preventEdgeOffset
            if (r9 != 0) goto L_0x0273
            int r9 = -r7
            r12 = 2
            int r9 = r9 / r12
            r5.leftMargin = r9
        L_0x0273:
            r31 = 1
        L_0x0275:
            r9 = 1
            r20 = 2
            goto L_0x029d
        L_0x0279:
            boolean r9 = r5.isOverflowButton
            if (r9 == 0) goto L_0x028c
            r5.extraPixels = r7
            r9 = 1
            r5.expanded = r9
            int r12 = -r7
            r20 = 2
            int r12 = r12 / 2
            r5.rightMargin = r12
            r31 = 1
            goto L_0x029d
        L_0x028c:
            r9 = 1
            r20 = 2
            if (r8 == 0) goto L_0x0295
            int r12 = r7 / 2
            r5.leftMargin = r12
        L_0x0295:
            int r12 = r10 + -1
            if (r8 == r12) goto L_0x029d
            int r12 = r7 / 2
            r5.rightMargin = r12
        L_0x029d:
            int r8 = r8 + 1
            r4 = r39
            r5 = r40
            goto L_0x0242
        L_0x02a4:
            r39 = r4
            r40 = r5
            r14 = 0
            goto L_0x02ac
        L_0x02aa:
            r39 = r4
        L_0x02ac:
            r4 = 1073741824(0x40000000, float:2.0)
            if (r31 == 0) goto L_0x02d5
            r38 = 0
        L_0x02b2:
            r5 = r38
            if (r5 >= r10) goto L_0x02d5
            android.view.View r7 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            android.support.v7.widget.ActionMenuView$LayoutParams r8 = (android.support.v7.widget.ActionMenuView.LayoutParams) r8
            boolean r9 = r8.expanded
            if (r9 != 0) goto L_0x02c5
            goto L_0x02d2
        L_0x02c5:
            int r9 = r8.cellsUsed
            int r9 = r9 * r11
            int r12 = r8.extraPixels
            int r9 = r9 + r12
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r4)
            r7.measure(r12, r6)
        L_0x02d2:
            int r38 = r5 + 1
            goto L_0x02b2
        L_0x02d5:
            if (r1 == r4) goto L_0x02d9
            r4 = r3
            goto L_0x02db
        L_0x02d9:
            r4 = r21
        L_0x02db:
            r0.setMeasuredDimension(r2, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuView.onMeasureExactFormat(int, int):void");
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        View view = child;
        int i = cellsRemaining;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, View.MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            child.measure(View.MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        child.measure(View.MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int overflowWidth;
        int dividerWidth;
        int midVertical;
        boolean isLayoutRtl;
        int l;
        int r;
        if (!this.mFormatItems) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }
        int childCount = getChildCount();
        int midVertical2 = (bottom - top) / 2;
        int dividerWidth2 = getDividerWidth();
        int nonOverflowCount = 0;
        int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
        boolean hasOverflow = false;
        boolean isLayoutRtl2 = ViewUtils.isLayoutRtl(this);
        int widthRemaining2 = widthRemaining;
        int nonOverflowWidth = 0;
        int overflowWidth2 = 0;
        int i = 0;
        while (i < childCount) {
            View v = getChildAt(i);
            if (v.getVisibility() == 8) {
                midVertical = midVertical2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    int overflowWidth3 = v.getMeasuredWidth();
                    if (hasSupportDividerBeforeChildAt(i) != 0) {
                        overflowWidth3 += dividerWidth2;
                    }
                    int height = v.getMeasuredHeight();
                    if (isLayoutRtl2) {
                        isLayoutRtl = isLayoutRtl2;
                        l = getPaddingLeft() + p.leftMargin;
                        r = l + overflowWidth3;
                    } else {
                        isLayoutRtl = isLayoutRtl2;
                        r = (getWidth() - getPaddingRight()) - p.rightMargin;
                        l = r - overflowWidth3;
                    }
                    int t = midVertical2 - (height / 2);
                    midVertical = midVertical2;
                    v.layout(l, t, r, t + height);
                    widthRemaining2 -= overflowWidth3;
                    hasOverflow = true;
                    overflowWidth2 = overflowWidth3;
                } else {
                    midVertical = midVertical2;
                    isLayoutRtl = isLayoutRtl2;
                    int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    nonOverflowWidth += size;
                    widthRemaining2 -= size;
                    if (hasSupportDividerBeforeChildAt(i)) {
                        nonOverflowWidth += dividerWidth2;
                    }
                    nonOverflowCount++;
                }
            }
            i++;
            isLayoutRtl2 = isLayoutRtl;
            midVertical2 = midVertical;
        }
        int midVertical3 = midVertical2;
        boolean isLayoutRtl3 = isLayoutRtl2;
        int i2 = 1;
        if (childCount != 1 || hasOverflow) {
            if (hasOverflow) {
                i2 = 0;
            }
            int spacerCount = nonOverflowCount - i2;
            int i3 = 0;
            int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining2 / spacerCount : 0);
            if (isLayoutRtl3) {
                int startRight = getWidth() - getPaddingRight();
                while (i3 < childCount) {
                    View v2 = getChildAt(i3);
                    LayoutParams lp = (LayoutParams) v2.getLayoutParams();
                    int spacerCount2 = spacerCount;
                    if (v2.getVisibility() == 8) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else if (lp.isOverflowButton) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else {
                        int startRight2 = startRight - lp.rightMargin;
                        int width = v2.getMeasuredWidth();
                        int height2 = v2.getMeasuredHeight();
                        int t2 = midVertical3 - (height2 / 2);
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                        v2.layout(startRight2 - width, t2, startRight2, t2 + height2);
                        startRight = startRight2 - ((lp.leftMargin + width) + spacerSize);
                    }
                    i3++;
                    spacerCount = spacerCount2;
                    dividerWidth2 = dividerWidth;
                    overflowWidth2 = overflowWidth;
                }
                int i4 = dividerWidth2;
                int i5 = overflowWidth2;
                return;
            }
            int i6 = dividerWidth2;
            int i7 = overflowWidth2;
            int startLeft = getPaddingLeft();
            while (i3 < childCount) {
                View v3 = getChildAt(i3);
                LayoutParams lp2 = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp2.isOverflowButton) {
                    int startLeft2 = startLeft + lp2.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical3 - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp2.rightMargin + width2 + spacerSize;
                }
                i3++;
            }
            return;
        }
        View v4 = getChildAt(0);
        int width3 = v4.getMeasuredWidth();
        int height4 = v4.getMeasuredHeight();
        int l2 = ((right - left) / 2) - (width3 / 2);
        int t4 = midVertical3 - (height4 / 2);
        int i8 = width3;
        v4.layout(l2, t4, l2 + width3, t4 + height4);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(@Nullable Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams result = p instanceof LayoutParams ? new LayoutParams((LayoutParams) p) : new LayoutParams(p);
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && (p instanceof LayoutParams);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getWindowAnimations() {
        return 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback());
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex <= 0 || !(child instanceof ActionMenuChildView)) {
            return result;
        }
        return result | ((ActionMenuChildView) child).needsDividerBefore();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int width, int height, boolean isOverflowButton2) {
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }
}
