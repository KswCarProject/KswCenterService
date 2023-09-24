package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.TtmlUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import com.android.internal.view.menu.ActionMenuItemView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;

/* loaded from: classes4.dex */
public class ActionMenuView extends LinearLayout implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    /* loaded from: classes4.dex */
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        @UnsupportedAppUsage
        boolean needsDividerBefore();
    }

    /* loaded from: classes4.dex */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, null);
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

    public void setPopupTheme(int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = this.mContext;
            } else {
                this.mPopupContext = new ContextThemeWrapper(this.mContext, resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    @Override // android.view.View
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

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (this.mFormatItems && this.mMenu != null && widthSize != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.rightMargin = 0;
            lp.leftMargin = 0;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02d9  */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r13v25 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onMeasureExactFormat(int widthMeasureSpec, int heightMeasureSpec) {
        boolean needsExpansion;
        int visibleItemCount;
        int visibleItemCount2;
        boolean singleItem;
        boolean centerSingleExpandedItem;
        int cellsRemaining;
        int visibleItemCount3;
        int cellSizeRemaining;
        int visibleItemCount4;
        ?? r13;
        int heightPadding;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding2 = getPaddingTop() + getPaddingBottom();
        int itemHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding2, -2);
        int widthSize2 = widthSize - widthPadding;
        int cellCount = widthSize2 / this.mMinCellSize;
        int cellSizeRemaining2 = widthSize2 % this.mMinCellSize;
        if (cellCount == 0) {
            setMeasuredDimension(widthSize2, 0);
            return;
        }
        int cellSize = this.mMinCellSize + (cellSizeRemaining2 / cellCount);
        boolean hasOverflow = false;
        long smallestItemsAt = 0;
        int childCount = getChildCount();
        int heightSize2 = 0;
        int maxChildHeight = 0;
        int visibleItemCount5 = 0;
        int expandableItemCount = 0;
        int maxCellsUsed = cellCount;
        int cellsRemaining2 = 0;
        while (true) {
            int widthPadding2 = widthPadding;
            if (cellsRemaining2 >= childCount) {
                break;
            }
            View child = getChildAt(cellsRemaining2);
            int cellCount2 = cellCount;
            if (child.getVisibility() == 8) {
                heightPadding = heightPadding2;
                cellSizeRemaining = cellSizeRemaining2;
            } else {
                boolean isGeneratedItem = child instanceof ActionMenuItemView;
                int visibleItemCount6 = maxChildHeight + 1;
                if (isGeneratedItem) {
                    cellSizeRemaining = cellSizeRemaining2;
                    visibleItemCount4 = visibleItemCount6;
                    r13 = 0;
                    child.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                } else {
                    cellSizeRemaining = cellSizeRemaining2;
                    visibleItemCount4 = visibleItemCount6;
                    r13 = 0;
                }
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.expanded = r13;
                lp.extraPixels = r13;
                lp.cellsUsed = r13;
                lp.expandable = r13;
                lp.leftMargin = r13;
                lp.rightMargin = r13;
                lp.preventEdgeOffset = isGeneratedItem && ((ActionMenuItemView) child).hasText();
                int cellsAvailable = lp.isOverflowButton ? 1 : maxCellsUsed;
                int cellsUsed = measureChildForCells(child, cellSize, cellsAvailable, itemHeightSpec, heightPadding2);
                expandableItemCount = Math.max(expandableItemCount, cellsUsed);
                heightPadding = heightPadding2;
                if (lp.expandable) {
                    visibleItemCount5++;
                }
                if (lp.isOverflowButton) {
                    hasOverflow = true;
                }
                maxCellsUsed -= cellsUsed;
                heightSize2 = Math.max(heightSize2, child.getMeasuredHeight());
                if (cellsUsed == 1) {
                    smallestItemsAt |= 1 << cellsRemaining2;
                    maxChildHeight = visibleItemCount4;
                    heightSize2 = heightSize2;
                } else {
                    maxChildHeight = visibleItemCount4;
                }
            }
            cellsRemaining2++;
            widthPadding = widthPadding2;
            cellCount = cellCount2;
            cellSizeRemaining2 = cellSizeRemaining;
            heightPadding2 = heightPadding;
        }
        boolean centerSingleExpandedItem2 = hasOverflow && maxChildHeight == 2;
        boolean needsExpansion2 = false;
        while (visibleItemCount5 > 0 && maxCellsUsed > 0) {
            long minCellsAt = 0;
            int minCells = Integer.MAX_VALUE;
            int minCellsItemCount = 0;
            int minCells2 = 0;
            while (true) {
                int i = minCells2;
                if (i >= childCount) {
                    break;
                }
                boolean needsExpansion3 = needsExpansion2;
                LayoutParams lp2 = (LayoutParams) getChildAt(i).getLayoutParams();
                if (!lp2.expandable) {
                    visibleItemCount3 = maxChildHeight;
                } else if (lp2.cellsUsed < minCells) {
                    int minCells3 = lp2.cellsUsed;
                    int minCells4 = 1 << i;
                    visibleItemCount3 = maxChildHeight;
                    long minCellsAt2 = minCells4;
                    minCellsItemCount = 1;
                    minCellsAt = minCellsAt2;
                    minCells = minCells3;
                } else {
                    visibleItemCount3 = maxChildHeight;
                    if (lp2.cellsUsed == minCells) {
                        minCellsItemCount++;
                        minCellsAt |= 1 << i;
                    }
                }
                minCells2 = i + 1;
                needsExpansion2 = needsExpansion3;
                maxChildHeight = visibleItemCount3;
            }
            needsExpansion = needsExpansion2;
            visibleItemCount = maxChildHeight;
            smallestItemsAt |= minCellsAt;
            if (minCellsItemCount > maxCellsUsed) {
                break;
            }
            int minCells5 = minCells + 1;
            int i2 = 0;
            while (i2 < childCount) {
                View child2 = getChildAt(i2);
                LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                int minCellsItemCount2 = minCellsItemCount;
                int minCellsItemCount3 = 1 << i2;
                int cellsRemaining3 = maxCellsUsed;
                if ((minCellsAt & minCellsItemCount3) == 0) {
                    if (lp3.cellsUsed == minCells5) {
                        smallestItemsAt |= 1 << i2;
                    }
                    centerSingleExpandedItem = centerSingleExpandedItem2;
                    maxCellsUsed = cellsRemaining3;
                } else {
                    if (centerSingleExpandedItem2 && lp3.preventEdgeOffset) {
                        cellsRemaining = cellsRemaining3;
                        if (cellsRemaining == 1) {
                            centerSingleExpandedItem = centerSingleExpandedItem2;
                            child2.setPadding(this.mGeneratedItemPadding + cellSize, 0, this.mGeneratedItemPadding, 0);
                        } else {
                            centerSingleExpandedItem = centerSingleExpandedItem2;
                        }
                    } else {
                        centerSingleExpandedItem = centerSingleExpandedItem2;
                        cellsRemaining = cellsRemaining3;
                    }
                    lp3.cellsUsed++;
                    lp3.expanded = true;
                    maxCellsUsed = cellsRemaining - 1;
                }
                i2++;
                minCellsItemCount = minCellsItemCount2;
                centerSingleExpandedItem2 = centerSingleExpandedItem;
            }
            needsExpansion2 = true;
            maxChildHeight = visibleItemCount;
        }
        needsExpansion = needsExpansion2;
        visibleItemCount = maxChildHeight;
        if (hasOverflow) {
            visibleItemCount2 = visibleItemCount;
        } else {
            visibleItemCount2 = visibleItemCount;
            if (visibleItemCount2 == 1) {
                singleItem = true;
                if (maxCellsUsed <= 0 && smallestItemsAt != 0) {
                    if (maxCellsUsed < visibleItemCount2 - 1 || singleItem || expandableItemCount > 1) {
                        float expandCount = Long.bitCount(smallestItemsAt);
                        if (!singleItem) {
                            if ((smallestItemsAt & 1) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                                expandCount -= 0.5f;
                            }
                            if ((smallestItemsAt & (1 << (childCount - 1))) != 0 && !((LayoutParams) getChildAt(childCount - 1).getLayoutParams()).preventEdgeOffset) {
                                expandCount -= 0.5f;
                            }
                        }
                        int extraPixels = expandCount > 0.0f ? (int) ((maxCellsUsed * cellSize) / expandCount) : 0;
                        int i3 = 0;
                        while (i3 < childCount) {
                            boolean singleItem2 = singleItem;
                            float expandCount2 = expandCount;
                            if ((smallestItemsAt & (1 << i3)) != 0) {
                                View child3 = getChildAt(i3);
                                LayoutParams lp4 = (LayoutParams) child3.getLayoutParams();
                                if (child3 instanceof ActionMenuItemView) {
                                    lp4.extraPixels = extraPixels;
                                    lp4.expanded = true;
                                    if (i3 == 0 && !lp4.preventEdgeOffset) {
                                        lp4.leftMargin = (-extraPixels) / 2;
                                    }
                                    needsExpansion = true;
                                } else {
                                    if (lp4.isOverflowButton) {
                                        lp4.extraPixels = extraPixels;
                                        lp4.expanded = true;
                                        lp4.rightMargin = (-extraPixels) / 2;
                                        needsExpansion = true;
                                    } else {
                                        if (i3 != 0) {
                                            lp4.leftMargin = extraPixels / 2;
                                        }
                                        if (i3 != childCount - 1) {
                                            lp4.rightMargin = extraPixels / 2;
                                        }
                                    }
                                    i3++;
                                    singleItem = singleItem2;
                                    expandCount = expandCount2;
                                }
                            }
                            i3++;
                            singleItem = singleItem2;
                            expandCount = expandCount2;
                        }
                    }
                }
                if (needsExpansion) {
                    int i4 = 0;
                    while (true) {
                        int i5 = i4;
                        if (i5 >= childCount) {
                            break;
                        }
                        View child4 = getChildAt(i5);
                        LayoutParams lp5 = (LayoutParams) child4.getLayoutParams();
                        if (lp5.expanded) {
                            int width = (lp5.cellsUsed * cellSize) + lp5.extraPixels;
                            child4.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), itemHeightSpec);
                        }
                        i4 = i5 + 1;
                    }
                }
                setMeasuredDimension(widthSize2, heightMode == 1073741824 ? heightSize2 : heightSize);
            }
        }
        singleItem = false;
        if (maxCellsUsed <= 0) {
        }
        if (needsExpansion) {
        }
        setMeasuredDimension(widthSize2, heightMode == 1073741824 ? heightSize2 : heightSize);
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSize = View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding;
        int childHeightMode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode);
        ActionMenuItemView itemView = child instanceof ActionMenuItemView ? (ActionMenuItemView) child : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (cellsRemaining > 0 && (!hasText || cellsRemaining >= 2)) {
            int childWidthSpec = View.MeasureSpec.makeMeasureSpec(cellSize * cellsRemaining, Integer.MIN_VALUE);
            child.measure(childWidthSpec, childHeightSpec);
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
        int targetWidth = cellsUsed * cellSize;
        child.measure(View.MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int dividerWidth;
        int overflowWidth;
        int midVertical;
        boolean isLayoutRtl;
        int r;
        int l;
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
        boolean isLayoutRtl2 = isLayoutRtl();
        int widthRemaining2 = widthRemaining;
        int widthRemaining3 = 0;
        int nonOverflowWidth = 0;
        int overflowWidth2 = 0;
        while (overflowWidth2 < childCount) {
            View v = getChildAt(overflowWidth2);
            if (v.getVisibility() == 8) {
                midVertical = midVertical2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    int overflowWidth3 = v.getMeasuredWidth();
                    if (hasDividerBeforeChildAt(overflowWidth2)) {
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
                    int midVertical3 = t + height;
                    v.layout(l, t, r, midVertical3);
                    widthRemaining2 -= overflowWidth3;
                    hasOverflow = true;
                    nonOverflowWidth = overflowWidth3;
                } else {
                    midVertical = midVertical2;
                    isLayoutRtl = isLayoutRtl2;
                    int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    widthRemaining3 += size;
                    widthRemaining2 -= size;
                    if (hasDividerBeforeChildAt(overflowWidth2)) {
                        widthRemaining3 += dividerWidth2;
                    }
                    nonOverflowCount++;
                }
            }
            overflowWidth2++;
            isLayoutRtl2 = isLayoutRtl;
            midVertical2 = midVertical;
        }
        int midVertical4 = midVertical2;
        boolean isLayoutRtl3 = isLayoutRtl2;
        if (childCount == 1 && !hasOverflow) {
            View v2 = getChildAt(0);
            int width = v2.getMeasuredWidth();
            int height2 = v2.getMeasuredHeight();
            int midHorizontal = (right - left) / 2;
            int l2 = midHorizontal - (width / 2);
            int t2 = midVertical4 - (height2 / 2);
            v2.layout(l2, t2, l2 + width, t2 + height2);
            return;
        }
        int spacerCount = nonOverflowCount - (hasOverflow ? 0 : 1);
        int i = 0;
        int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining2 / spacerCount : 0);
        if (!isLayoutRtl3) {
            int startLeft = getPaddingLeft();
            while (i < childCount) {
                View v3 = getChildAt(i);
                LayoutParams lp = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp.isOverflowButton) {
                    int startLeft2 = startLeft + lp.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical4 - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp.rightMargin + width2 + spacerSize;
                }
                i++;
            }
            return;
        }
        int startRight = getWidth() - getPaddingRight();
        while (i < childCount) {
            View v4 = getChildAt(i);
            LayoutParams lp2 = (LayoutParams) v4.getLayoutParams();
            int spacerCount2 = spacerCount;
            if (v4.getVisibility() == 8) {
                dividerWidth = dividerWidth2;
                overflowWidth = nonOverflowWidth;
            } else if (lp2.isOverflowButton) {
                dividerWidth = dividerWidth2;
                overflowWidth = nonOverflowWidth;
            } else {
                int startRight2 = startRight - lp2.rightMargin;
                int width3 = v4.getMeasuredWidth();
                int height4 = v4.getMeasuredHeight();
                int t4 = midVertical4 - (height4 / 2);
                dividerWidth = dividerWidth2;
                overflowWidth = nonOverflowWidth;
                int overflowWidth4 = t4 + height4;
                v4.layout(startRight2 - width3, t4, startRight2, overflowWidth4);
                startRight = startRight2 - ((lp2.leftMargin + width3) + spacerSize);
            }
            i++;
            spacerCount = spacerCount2;
            dividerWidth2 = dividerWidth;
            nonOverflowWidth = overflowWidth;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    @UnsupportedAppUsage
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams result;
        if (p != null) {
            if (p instanceof LayoutParams) {
                result = new LayoutParams((LayoutParams) p);
            } else {
                result = new LayoutParams(p);
            }
            if (result.gravity <= 0) {
                result.gravity = 16;
            }
            return result;
        }
        LayoutParams result2 = generateDefaultLayoutParams();
        return result2;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && (p instanceof LayoutParams);
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    @Override // com.android.internal.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    @Override // com.android.internal.view.menu.MenuView
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

    @UnsupportedAppUsage
    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    @UnsupportedAppUsage
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

    @UnsupportedAppUsage
    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    @Override // android.widget.LinearLayout
    @UnsupportedAppUsage
    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex > 0 && (child instanceof ActionMenuChildView)) {
            return result | ((ActionMenuChildView) child).needsDividerBefore();
        }
        return result;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        return false;
    }

    @UnsupportedAppUsage
    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    /* loaded from: classes4.dex */
    private class MenuBuilderCallback implements MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    /* loaded from: classes4.dex */
    private class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    /* loaded from: classes4.dex */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        @UnsupportedAppUsage
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int cellsUsed;
        @UnsupportedAppUsage
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean expandable;
        @UnsupportedAppUsage
        public boolean expanded;
        @UnsupportedAppUsage
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int extraPixels;
        @UnsupportedAppUsage
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean isOverflowButton;
        @UnsupportedAppUsage
        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public boolean preventEdgeOffset;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((LinearLayout.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        public LayoutParams(int width, int height, boolean isOverflowButton) {
            super(width, height);
            this.isOverflowButton = isOverflowButton;
        }

        @Override // android.widget.LinearLayout.LayoutParams, android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("layout:overFlowButton", this.isOverflowButton);
            encoder.addProperty("layout:cellsUsed", this.cellsUsed);
            encoder.addProperty("layout:extraPixels", this.extraPixels);
            encoder.addProperty("layout:expandable", this.expandable);
            encoder.addProperty("layout:preventEdgeOffset", this.preventEdgeOffset);
        }
    }
}
