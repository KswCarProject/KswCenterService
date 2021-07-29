package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseBooleanArray;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsListView;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.google.android.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RemoteViews.RemoteView
public class ListView extends AbsListView {
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    static final String TAG = "ListView";
    @UnsupportedAppUsage
    private boolean mAreAllItemsSelectable;
    private final ArrowScrollFocusResult mArrowScrollFocusResult;
    @UnsupportedAppUsage
    Drawable mDivider;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    int mDividerHeight;
    private boolean mDividerIsOpaque;
    private Paint mDividerPaint;
    private FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    @UnsupportedAppUsage
    ArrayList<FixedViewInfo> mFooterViewInfos;
    private boolean mHeaderDividersEnabled;
    @UnsupportedAppUsage
    ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mIsCacheColorOpaque;
    private boolean mItemsCanFocus;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private final Rect mTempRect;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ListView> {
        private int mDividerHeightId;
        private int mDividerId;
        private int mFooterDividersEnabledId;
        private int mHeaderDividersEnabledId;
        private boolean mPropertiesMapped = false;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mDividerHeightId = propertyMapper.mapInt("dividerHeight", 16843050);
            this.mFooterDividersEnabledId = propertyMapper.mapBoolean("footerDividersEnabled", 16843311);
            this.mHeaderDividersEnabledId = propertyMapper.mapBoolean("headerDividersEnabled", 16843310);
            this.mPropertiesMapped = true;
        }

        public void readProperties(ListView node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readObject(this.mDividerId, node.getDivider());
                propertyReader.readInt(this.mDividerHeightId, node.getDividerHeight());
                propertyReader.readBoolean(this.mFooterDividersEnabledId, node.areFooterDividersEnabled());
                propertyReader.readBoolean(this.mHeaderDividersEnabledId, node.areHeaderDividersEnabled());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;

        public FixedViewInfo() {
        }
    }

    public ListView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842868);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int dividerHeight;
        this.mHeaderViewInfos = Lists.newArrayList();
        this.mFooterViewInfos = Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListView, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.ListView, attrs, a, defStyleAttr, defStyleRes);
        CharSequence[] entries = a.getTextArray(0);
        if (entries != null) {
            setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, (T[]) entries));
        }
        Drawable d = a.getDrawable(1);
        if (d != null) {
            setDivider(d);
        }
        Drawable osHeader = a.getDrawable(5);
        if (osHeader != null) {
            setOverscrollHeader(osHeader);
        }
        Drawable osFooter = a.getDrawable(6);
        if (osFooter != null) {
            setOverscrollFooter(osFooter);
        }
        if (a.hasValueOrEmpty(2) && (dividerHeight = a.getDimensionPixelSize(2, 0)) != 0) {
            setDividerHeight(dividerHeight);
        }
        this.mHeaderDividersEnabled = a.getBoolean(3, true);
        this.mFooterDividersEnabled = a.getBoolean(4, true);
        a.recycle();
    }

    public int getMaxScrollAmount() {
        return (int) (((float) (this.mBottom - this.mTop)) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsUpOrDown() {
        int delta;
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                delta = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    delta -= this.mDividerHeight;
                }
                if (delta < 0) {
                    delta = 0;
                }
            } else {
                int delta2 = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    delta2 += this.mDividerHeight;
                }
                if (delta > 0) {
                    delta = 0;
                }
            }
            if (delta != 0) {
                offsetChildrenTopAndBottom(-delta);
            }
        }
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (!(v.getParent() == null || v.getParent() == this || !Log.isLoggable(TAG, 5))) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mHeaderViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addHeaderView(View v) {
        addHeaderView(v, (Object) null, true);
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mHeaderViewInfos);
        return result;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; i++) {
            if (where.get(i).view == v) {
                where.remove(i);
                return;
            }
        }
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        if (!(v.getParent() == null || v.getParent() == this || !Log.isLoggable(TAG, 5))) {
            Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        this.mAreAllItemsSelectable &= isSelectable;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, (Object) null, true);
    }

    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mFooterViewInfos);
        return result;
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    public void setAdapter(ListAdapter adapter) {
        int position;
        if (!(this.mAdapter == null || this.mDataSetObserver == null)) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(adapter);
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromBottom) {
                position = lookForSelectablePosition(this.mItemCount - 1, false);
            } else {
                position = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mAreAllItemsSelectable = true;
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void resetList() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        super.resetList();
        this.mLayoutMode = 0;
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            int count = infos.size();
            for (int i = 0; i < count; i++) {
                ViewGroup.LayoutParams params = infos.get(i).view.getLayoutParams();
                if (checkLayoutParams(params)) {
                    ((AbsListView.LayoutParams) params).recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingTopFadingEdge() {
        return this.mFirstPosition > 0 || getChildAt(0).getTop() > this.mScrollY + this.mListPadding.top;
    }

    private boolean showingBottomFadingEdge() {
        int childCount = getChildCount();
        int bottomOfBottomChild = getChildAt(childCount - 1).getBottom();
        int lastVisiblePosition = (this.mFirstPosition + childCount) - 1;
        int listBottom = (this.mScrollY + getHeight()) - this.mListPadding.bottom;
        if (lastVisiblePosition < this.mItemCount - 1 || bottomOfBottomChild < listBottom) {
            return true;
        }
        return false;
    }

    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        int scrollYDelta;
        int scrollYDelta2;
        int rectTopWithinChild = rect.top;
        rect.offset(child.getLeft(), child.getTop());
        rect.offset(-child.getScrollX(), -child.getScrollY());
        int height = getHeight();
        int listUnfadedTop = getScrollY();
        int listUnfadedBottom = listUnfadedTop + height;
        int fadingEdge = getVerticalFadingEdgeLength();
        if (showingTopFadingEdge() && (this.mSelectedPosition > 0 || rectTopWithinChild > fadingEdge)) {
            listUnfadedTop += fadingEdge;
        }
        int bottomOfBottomChild = getChildAt(getChildCount() - 1).getBottom();
        boolean scroll = true;
        if (showingBottomFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.bottom < bottomOfBottomChild - fadingEdge)) {
            listUnfadedBottom -= fadingEdge;
        }
        int scrollYDelta3 = 0;
        if (rect.bottom > listUnfadedBottom && rect.top > listUnfadedTop) {
            if (rect.height() > height) {
                scrollYDelta2 = 0 + (rect.top - listUnfadedTop);
            } else {
                scrollYDelta2 = 0 + (rect.bottom - listUnfadedBottom);
            }
            scrollYDelta3 = Math.min(scrollYDelta2, bottomOfBottomChild - listUnfadedBottom);
        } else if (rect.top < listUnfadedTop && rect.bottom < listUnfadedBottom) {
            if (rect.height() > height) {
                scrollYDelta = 0 - (listUnfadedBottom - rect.bottom);
            } else {
                scrollYDelta = 0 - (listUnfadedTop - rect.top);
            }
            scrollYDelta3 = Math.max(scrollYDelta, getChildAt(0).getTop() - listUnfadedTop);
        }
        if (scrollYDelta3 == 0) {
            scroll = false;
        }
        if (scroll) {
            scrollListItemsBy(-scrollYDelta3);
            positionSelector(-1, child);
            this.mSelectedTop = child.getTop();
            invalidate();
        }
        return scroll;
    }

    /* access modifiers changed from: package-private */
    public void fillGap(boolean down) {
        int startOffset;
        int startOffset2;
        int count = getChildCount();
        if (down) {
            int paddingTop = 0;
            if ((this.mGroupFlags & 34) == 34) {
                paddingTop = getListPaddingTop();
            }
            if (count > 0) {
                startOffset2 = getChildAt(count - 1).getBottom() + this.mDividerHeight;
            } else {
                startOffset2 = paddingTop;
            }
            fillDown(this.mFirstPosition + count, startOffset2);
            correctTooHigh(getChildCount());
            return;
        }
        int paddingBottom = 0;
        if ((this.mGroupFlags & 34) == 34) {
            paddingBottom = getListPaddingBottom();
        }
        if (count > 0) {
            startOffset = getChildAt(0).getTop() - this.mDividerHeight;
        } else {
            startOffset = getHeight() - paddingBottom;
        }
        fillUp(this.mFirstPosition - 1, startOffset);
        correctTooLow(getChildCount());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private View fillDown(int pos, int nextTop) {
        View selectedView = null;
        int end = this.mBottom - this.mTop;
        if ((this.mGroupFlags & 34) == 34) {
            end -= this.mListPadding.bottom;
        }
        while (true) {
            boolean z = true;
            if (nextTop >= end || pos >= this.mItemCount) {
                setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
            } else {
                if (pos != this.mSelectedPosition) {
                    z = false;
                }
                boolean selected = z;
                View child = makeAndAddView(pos, nextTop, true, this.mListPadding.left, selected);
                nextTop = child.getBottom() + this.mDividerHeight;
                if (selected) {
                    selectedView = child;
                }
                pos++;
            }
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private View fillUp(int pos, int nextBottom) {
        View selectedView = null;
        int end = 0;
        if ((this.mGroupFlags & 34) == 34) {
            end = this.mListPadding.top;
        }
        while (true) {
            boolean z = true;
            if (nextBottom <= end || pos < 0) {
                this.mFirstPosition = pos + 1;
                setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
            } else {
                if (pos != this.mSelectedPosition) {
                    z = false;
                }
                boolean selected = z;
                View child = makeAndAddView(pos, nextBottom, false, this.mListPadding.left, selected);
                nextBottom = child.getTop() - this.mDividerHeight;
                if (selected) {
                    selectedView = child;
                }
                pos--;
            }
        }
        this.mFirstPosition = pos + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillFromTop(int nextTop) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillDown(this.mFirstPosition, nextTop);
    }

    private View fillFromMiddle(int childrenTop, int childrenBottom) {
        int height = childrenBottom - childrenTop;
        int position = reconcileSelectedPosition();
        View sel = makeAndAddView(position, childrenTop, true, this.mListPadding.left, true);
        this.mFirstPosition = position;
        int selHeight = sel.getMeasuredHeight();
        if (selHeight <= height) {
            sel.offsetTopAndBottom((height - selHeight) / 2);
        }
        fillAboveAndBelow(sel, position);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private void fillAboveAndBelow(View sel, int position) {
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            fillUp(position - 1, sel.getTop() - dividerHeight);
            adjustViewsUpOrDown();
            fillDown(position + 1, sel.getBottom() + dividerHeight);
            return;
        }
        fillDown(position + 1, sel.getBottom() + dividerHeight);
        adjustViewsUpOrDown();
        fillUp(position - 1, sel.getTop() - dividerHeight);
    }

    private View fillFromSelection(int selectedTop, int childrenTop, int childrenBottom) {
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(childrenTop, fadingEdgeLength, selectedPosition);
        int bottomSelectionPixel = getBottomSelectionPixel(childrenBottom, fadingEdgeLength, selectedPosition);
        View sel = makeAndAddView(selectedPosition, selectedTop, true, this.mListPadding.left, true);
        if (sel.getBottom() > bottomSelectionPixel) {
            sel.offsetTopAndBottom(-Math.min(sel.getTop() - topSelectionPixel, sel.getBottom() - bottomSelectionPixel));
        } else if (sel.getTop() < topSelectionPixel) {
            sel.offsetTopAndBottom(Math.min(topSelectionPixel - sel.getTop(), bottomSelectionPixel - sel.getBottom()));
        }
        fillAboveAndBelow(sel, selectedPosition);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return sel;
    }

    private int getBottomSelectionPixel(int childrenBottom, int fadingEdgeLength, int selectedPosition) {
        int bottomSelectionPixel = childrenBottom;
        if (selectedPosition != this.mItemCount - 1) {
            return bottomSelectionPixel - fadingEdgeLength;
        }
        return bottomSelectionPixel;
    }

    private int getTopSelectionPixel(int childrenTop, int fadingEdgeLength, int selectedPosition) {
        int topSelectionPixel = childrenTop;
        if (selectedPosition > 0) {
            return topSelectionPixel + fadingEdgeLength;
        }
        return topSelectionPixel;
    }

    @RemotableViewMethod
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }

    @RemotableViewMethod
    public void smoothScrollByOffset(int offset) {
        super.smoothScrollByOffset(offset);
    }

    private View moveSelection(View oldSel, View newSel, int delta, int childrenTop, int childrenBottom) {
        View sel;
        int i = childrenTop;
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i, fadingEdgeLength, selectedPosition);
        int bottomSelectionPixel = getBottomSelectionPixel(i, fadingEdgeLength, selectedPosition);
        if (delta > 0) {
            View oldSel2 = makeAndAddView(selectedPosition - 1, oldSel.getTop(), true, this.mListPadding.left, false);
            int dividerHeight = this.mDividerHeight;
            sel = makeAndAddView(selectedPosition, oldSel2.getBottom() + dividerHeight, true, this.mListPadding.left, true);
            if (sel.getBottom() > bottomSelectionPixel) {
                int offset = Math.min(Math.min(sel.getTop() - topSelectionPixel, sel.getBottom() - bottomSelectionPixel), (childrenBottom - i) / 2);
                oldSel2.offsetTopAndBottom(-offset);
                sel.offsetTopAndBottom(-offset);
            }
            if (this.mStackFromBottom == 0) {
                fillUp(this.mSelectedPosition - 2, sel.getTop() - dividerHeight);
                adjustViewsUpOrDown();
                fillDown(this.mSelectedPosition + 1, sel.getBottom() + dividerHeight);
            } else {
                fillDown(this.mSelectedPosition + 1, sel.getBottom() + dividerHeight);
                adjustViewsUpOrDown();
                fillUp(this.mSelectedPosition - 2, sel.getTop() - dividerHeight);
            }
            View view = oldSel2;
        } else {
            if (delta < 0) {
                if (newSel != null) {
                    sel = makeAndAddView(selectedPosition, newSel.getTop(), true, this.mListPadding.left, true);
                } else {
                    sel = makeAndAddView(selectedPosition, oldSel.getTop(), false, this.mListPadding.left, true);
                }
                if (sel.getTop() < topSelectionPixel) {
                    sel.offsetTopAndBottom(Math.min(Math.min(topSelectionPixel - sel.getTop(), bottomSelectionPixel - sel.getBottom()), (childrenBottom - i) / 2));
                }
                fillAboveAndBelow(sel, selectedPosition);
            } else {
                int oldTop = oldSel.getTop();
                sel = makeAndAddView(selectedPosition, oldTop, true, this.mListPadding.left, true);
                if (oldTop < i && sel.getBottom() < i + 20) {
                    sel.offsetTopAndBottom(i - sel.getTop());
                }
                fillAboveAndBelow(sel, selectedPosition);
            }
        }
        return sel;
    }

    private class FocusSelector implements Runnable {
        private static final int STATE_REQUEST_FOCUS = 3;
        private static final int STATE_SET_SELECTION = 1;
        private static final int STATE_WAIT_FOR_LAYOUT = 2;
        private int mAction;
        private int mPosition;
        private int mPositionTop;

        private FocusSelector() {
        }

        /* access modifiers changed from: package-private */
        public FocusSelector setupForSetSelection(int position, int top) {
            this.mPosition = position;
            this.mPositionTop = top;
            this.mAction = 1;
            return this;
        }

        public void run() {
            if (this.mAction == 1) {
                ListView.this.setSelectionFromTop(this.mPosition, this.mPositionTop);
                this.mAction = 2;
            } else if (this.mAction == 3) {
                View child = ListView.this.getChildAt(this.mPosition - ListView.this.mFirstPosition);
                if (child != null) {
                    child.requestFocus();
                }
                this.mAction = -1;
            }
        }

        /* access modifiers changed from: package-private */
        public Runnable setupFocusIfValid(int position) {
            if (this.mAction != 2 || position != this.mPosition) {
                return null;
            }
            this.mAction = 3;
            return this;
        }

        /* access modifiers changed from: package-private */
        public void onLayoutComplete() {
            if (this.mAction == 2) {
                this.mAction = -1;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mFocusSelector != null) {
            removeCallbacks(this.mFocusSelector);
            this.mFocusSelector = null;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        View focusedChild;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int childPosition = this.mFirstPosition + indexOfChild(focusedChild);
            int top = focusedChild.getTop() - Math.max(0, focusedChild.getBottom() - (h - this.mPaddingTop));
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new FocusSelector();
            }
            post(this.mFocusSelector.setupForSetSelection(childPosition, top));
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int heightSize;
        int i2 = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        int childState = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
            View child = obtainView(0, this.mIsScrap);
            measureScrapChild(child, 0, i2, heightSize2);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            childState = combineMeasuredStates(0, child.getMeasuredState());
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((AbsListView.LayoutParams) child.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(child, 0);
            }
        }
        int childWidth2 = childWidth;
        int childHeight2 = childHeight;
        int childState2 = childState;
        if (widthMode == 0) {
            i = this.mListPadding.left + this.mListPadding.right + childWidth2 + getVerticalScrollbarWidth();
        } else {
            i = (-16777216 & childState2) | widthSize;
        }
        int widthSize2 = i;
        if (heightMode == 0) {
            heightSize = this.mListPadding.top + this.mListPadding.bottom + childHeight2 + (getVerticalFadingEdgeLength() * 2);
        } else {
            heightSize = heightSize2;
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightSize = measureHeightOfChildren(widthMeasureSpec, 0, -1, heightSize, -1);
        }
        setMeasuredDimension(widthSize2, heightSize);
        this.mWidthMeasureSpec = i2;
    }

    /* JADX WARNING: type inference failed for: r1v9, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void measureScrapChild(android.view.View r5, int r6, int r7, int r8) {
        /*
            r4 = this;
            android.view.ViewGroup$LayoutParams r0 = r5.getLayoutParams()
            android.widget.AbsListView$LayoutParams r0 = (android.widget.AbsListView.LayoutParams) r0
            if (r0 != 0) goto L_0x0012
            android.view.ViewGroup$LayoutParams r1 = r4.generateDefaultLayoutParams()
            r0 = r1
            android.widget.AbsListView$LayoutParams r0 = (android.widget.AbsListView.LayoutParams) r0
            r5.setLayoutParams(r0)
        L_0x0012:
            android.widget.ListAdapter r1 = r4.mAdapter
            int r1 = r1.getItemViewType(r6)
            r0.viewType = r1
            android.widget.ListAdapter r1 = r4.mAdapter
            boolean r1 = r1.isEnabled(r6)
            r0.isEnabled = r1
            r1 = 1
            r0.forceAdd = r1
            android.graphics.Rect r1 = r4.mListPadding
            int r1 = r1.left
            android.graphics.Rect r2 = r4.mListPadding
            int r2 = r2.right
            int r1 = r1 + r2
            int r2 = r0.width
            int r1 = android.view.ViewGroup.getChildMeasureSpec(r7, r1, r2)
            int r2 = r0.height
            if (r2 <= 0) goto L_0x003f
            r3 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r3)
            goto L_0x0044
        L_0x003f:
            r3 = 0
            int r3 = android.view.View.MeasureSpec.makeSafeMeasureSpec(r8, r3)
        L_0x0044:
            r5.measure(r1, r3)
            r5.forceLayout()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.measureScrapChild(android.view.View, int, int, int):void");
    }

    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(category = "list")
    public boolean recycleOnMeasure() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public final int measureHeightOfChildren(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
        int i = maxHeight;
        int i2 = disallowPartialChildPosition;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int returnedHeight = this.mListPadding.top + this.mListPadding.bottom;
        int dividerHeight = this.mDividerHeight;
        int i3 = endPosition;
        int endPosition2 = i3 == -1 ? adapter.getCount() - 1 : i3;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recyle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int prevHeightWithoutPartialChild = 0;
        int returnedHeight2 = returnedHeight;
        int i4 = startPosition;
        while (i4 <= endPosition2) {
            View child = obtainView(i4, isScrap);
            measureScrapChild(child, i4, widthMeasureSpec, i);
            if (i4 > 0) {
                returnedHeight2 += dividerHeight;
            }
            if (recyle && recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) child.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(child, -1);
            }
            returnedHeight2 += child.getMeasuredHeight();
            if (returnedHeight2 >= i) {
                return (i2 < 0 || i4 <= i2 || prevHeightWithoutPartialChild <= 0 || returnedHeight2 == i) ? i : prevHeightWithoutPartialChild;
            }
            if (i2 >= 0 && i4 >= i2) {
                prevHeightWithoutPartialChild = returnedHeight2;
            }
            i4++;
        }
        int i5 = widthMeasureSpec;
        return returnedHeight2;
    }

    /* access modifiers changed from: package-private */
    public int findMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return -1;
        }
        if (!this.mStackFromBottom) {
            for (int i = 0; i < childCount; i++) {
                if (y <= getChildAt(i).getBottom()) {
                    return this.mFirstPosition + i;
                }
            }
            return -1;
        }
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            if (y >= getChildAt(i2).getTop()) {
                return this.mFirstPosition + i2;
            }
        }
        return -1;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private View fillSpecific(int position, int top) {
        View below;
        View above;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, top, true, this.mListPadding.left, tempIsSelected);
        this.mFirstPosition = position;
        int dividerHeight = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            above = fillUp(position - 1, temp.getTop() - dividerHeight);
            adjustViewsUpOrDown();
            below = fillDown(position + 1, temp.getBottom() + dividerHeight);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            below = fillDown(position + 1, temp.getBottom() + dividerHeight);
            adjustViewsUpOrDown();
            above = fillUp(position - 1, temp.getTop() - dividerHeight);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
        }
        if (tempIsSelected) {
            return temp;
        }
        if (above != null) {
            return above;
        }
        return below;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private void correctTooHigh(int childCount) {
        if ((this.mFirstPosition + childCount) - 1 == this.mItemCount - 1 && childCount > 0) {
            int bottomOffset = ((this.mBottom - this.mTop) - this.mListPadding.bottom) - getChildAt(childCount - 1).getBottom();
            View firstChild = getChildAt(0);
            int firstTop = firstChild.getTop();
            if (bottomOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstTop < this.mListPadding.top) {
                if (this.mFirstPosition == 0) {
                    bottomOffset = Math.min(bottomOffset, this.mListPadding.top - firstTop);
                }
                offsetChildrenTopAndBottom(bottomOffset);
                if (this.mFirstPosition > 0) {
                    fillUp(this.mFirstPosition - 1, firstChild.getTop() - this.mDividerHeight);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            int firstTop = getChildAt(0).getTop();
            int start = this.mListPadding.top;
            int end = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int topOffset = firstTop - start;
            View lastChild = getChildAt(childCount - 1);
            int lastBottom = lastChild.getBottom();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (topOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount - 1 || lastBottom > end) {
                if (lastPosition == this.mItemCount - 1) {
                    topOffset = Math.min(topOffset, lastBottom - end);
                }
                offsetChildrenTopAndBottom(-topOffset);
                if (lastPosition < this.mItemCount - 1) {
                    fillDown(lastPosition + 1, lastChild.getBottom() + this.mDividerHeight);
                    adjustViewsUpOrDown();
                }
            } else if (lastPosition == this.mItemCount - 1) {
                adjustViewsUpOrDown();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0115, code lost:
        if (r7.mAdapterHasStableIds == false) goto L_0x0122;
     */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0141 A[SYNTHETIC, Splitter:B:108:0x0141] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x015e A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0176 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0189 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01b1 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x01c7 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01e2 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0263 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x02a9 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x02b9 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x02ca A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x0303 A[Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x031f A[ADDED_TO_REGION, Catch:{ all -> 0x03b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x03bd  */
    /* JADX WARNING: Removed duplicated region for block: B:244:0x03c4  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0093 A[SYNTHETIC, Splitter:B:47:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009a A[SYNTHETIC, Splitter:B:52:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ae A[SYNTHETIC, Splitter:B:59:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0105  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layoutChildren() {
        /*
            r26 = this;
            r7 = r26
            boolean r8 = r7.mBlockLayoutRequests
            if (r8 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 1
            r7.mBlockLayoutRequests = r0
            r9 = 0
            super.layoutChildren()     // Catch:{ all -> 0x03b6 }
            r26.invalidate()     // Catch:{ all -> 0x03b6 }
            android.widget.ListAdapter r1 = r7.mAdapter     // Catch:{ all -> 0x03b6 }
            if (r1 != 0) goto L_0x002e
            r26.resetList()     // Catch:{ all -> 0x0029 }
            r26.invokeOnItemScrollListener()     // Catch:{ all -> 0x0029 }
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            if (r0 == 0) goto L_0x0024
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            r0.onLayoutComplete()
        L_0x0024:
            if (r8 != 0) goto L_0x0028
            r7.mBlockLayoutRequests = r9
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            r25 = r8
            goto L_0x03b9
        L_0x002e:
            android.graphics.Rect r1 = r7.mListPadding     // Catch:{ all -> 0x03b6 }
            int r1 = r1.top     // Catch:{ all -> 0x03b6 }
            r10 = r1
            int r1 = r7.mBottom     // Catch:{ all -> 0x03b6 }
            int r2 = r7.mTop     // Catch:{ all -> 0x03b6 }
            int r1 = r1 - r2
            android.graphics.Rect r2 = r7.mListPadding     // Catch:{ all -> 0x03b6 }
            int r2 = r2.bottom     // Catch:{ all -> 0x03b6 }
            int r11 = r1 - r2
            int r1 = r26.getChildCount()     // Catch:{ all -> 0x03b6 }
            r12 = r1
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            int r6 = r7.mLayoutMode     // Catch:{ all -> 0x03b6 }
            switch(r6) {
                case 1: goto L_0x0060;
                case 2: goto L_0x0050;
                case 3: goto L_0x0060;
                case 4: goto L_0x0060;
                case 5: goto L_0x0060;
                default: goto L_0x004d;
            }     // Catch:{ all -> 0x03b6 }
        L_0x004d:
            int r6 = r7.mSelectedPosition     // Catch:{ all -> 0x03b6 }
            goto L_0x0069
        L_0x0050:
            int r6 = r7.mNextSelectedPosition     // Catch:{ all -> 0x0029 }
            int r13 = r7.mFirstPosition     // Catch:{ all -> 0x0029 }
            int r1 = r6 - r13
            if (r1 < 0) goto L_0x0061
            if (r1 >= r12) goto L_0x0061
            android.view.View r6 = r7.getChildAt(r1)     // Catch:{ all -> 0x0029 }
            r5 = r6
            goto L_0x0061
        L_0x0060:
        L_0x0061:
            r16 = r1
            r17 = r2
            r13 = r3
            r14 = r4
            r15 = r5
            goto L_0x008d
        L_0x0069:
            int r13 = r7.mFirstPosition     // Catch:{ all -> 0x03b6 }
            int r1 = r6 - r13
            if (r1 < 0) goto L_0x0076
            if (r1 >= r12) goto L_0x0076
            android.view.View r6 = r7.getChildAt(r1)     // Catch:{ all -> 0x0029 }
            r3 = r6
        L_0x0076:
            android.view.View r6 = r7.getChildAt(r9)     // Catch:{ all -> 0x03b6 }
            r4 = r6
            int r6 = r7.mNextSelectedPosition     // Catch:{ all -> 0x03b6 }
            if (r6 < 0) goto L_0x0085
            int r6 = r7.mNextSelectedPosition     // Catch:{ all -> 0x0029 }
            int r13 = r7.mSelectedPosition     // Catch:{ all -> 0x0029 }
            int r2 = r6 - r13
        L_0x0085:
            int r6 = r1 + r2
            android.view.View r6 = r7.getChildAt(r6)     // Catch:{ all -> 0x03b6 }
            r5 = r6
            goto L_0x0061
        L_0x008d:
            boolean r1 = r7.mDataChanged     // Catch:{ all -> 0x03b6 }
            r18 = r1
            if (r18 == 0) goto L_0x0096
            r26.handleDataChanged()     // Catch:{ all -> 0x0029 }
        L_0x0096:
            int r1 = r7.mItemCount     // Catch:{ all -> 0x03b6 }
            if (r1 != 0) goto L_0x00ae
            r26.resetList()     // Catch:{ all -> 0x0029 }
            r26.invokeOnItemScrollListener()     // Catch:{ all -> 0x0029 }
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            if (r0 == 0) goto L_0x00a9
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            r0.onLayoutComplete()
        L_0x00a9:
            if (r8 != 0) goto L_0x00ad
            r7.mBlockLayoutRequests = r9
        L_0x00ad:
            return
        L_0x00ae:
            int r1 = r7.mItemCount     // Catch:{ all -> 0x03b6 }
            android.widget.ListAdapter r2 = r7.mAdapter     // Catch:{ all -> 0x03b6 }
            int r2 = r2.getCount()     // Catch:{ all -> 0x03b6 }
            if (r1 != r2) goto L_0x0378
            int r1 = r7.mNextSelectedPosition     // Catch:{ all -> 0x03b6 }
            r7.setSelectedPositionInt(r1)     // Catch:{ all -> 0x03b6 }
            r1 = 0
            r2 = 0
            r3 = -1
            android.view.ViewRootImpl r4 = r26.getViewRootImpl()     // Catch:{ all -> 0x03b6 }
            r19 = r4
            if (r19 == 0) goto L_0x00f7
            android.view.View r4 = r19.getAccessibilityFocusedHost()     // Catch:{ all -> 0x0029 }
            if (r4 == 0) goto L_0x00f7
            android.view.View r5 = r7.getAccessibilityFocusedChild(r4)     // Catch:{ all -> 0x0029 }
            if (r5 == 0) goto L_0x00f7
            if (r18 == 0) goto L_0x00e6
            boolean r6 = r7.isDirectChildHeaderOrFooter(r5)     // Catch:{ all -> 0x0029 }
            if (r6 != 0) goto L_0x00e6
            boolean r6 = r5.hasTransientState()     // Catch:{ all -> 0x0029 }
            if (r6 == 0) goto L_0x00ed
            boolean r6 = r7.mAdapterHasStableIds     // Catch:{ all -> 0x0029 }
            if (r6 == 0) goto L_0x00ed
        L_0x00e6:
            r2 = r4
            android.view.accessibility.AccessibilityNodeInfo r6 = r19.getAccessibilityFocusedVirtualView()     // Catch:{ all -> 0x0029 }
            r1 = r6
        L_0x00ed:
            int r6 = r7.getPositionForView(r5)     // Catch:{ all -> 0x0029 }
            r3 = r6
            r20 = r1
            r21 = r2
            goto L_0x00fc
        L_0x00f7:
            r20 = r1
            r21 = r2
            r6 = r3
        L_0x00fc:
            r1 = 0
            r2 = 0
            android.view.View r3 = r26.getFocusedChild()     // Catch:{ all -> 0x03b6 }
            r5 = r3
            if (r5 == 0) goto L_0x0125
            if (r18 == 0) goto L_0x0117
            boolean r3 = r7.isDirectChildHeaderOrFooter(r5)     // Catch:{ all -> 0x0029 }
            if (r3 != 0) goto L_0x0117
            boolean r3 = r5.hasTransientState()     // Catch:{ all -> 0x0029 }
            if (r3 != 0) goto L_0x0117
            boolean r3 = r7.mAdapterHasStableIds     // Catch:{ all -> 0x0029 }
            if (r3 == 0) goto L_0x0122
        L_0x0117:
            r1 = r5
            android.view.View r3 = r26.findFocus()     // Catch:{ all -> 0x0029 }
            r2 = r3
            if (r2 == 0) goto L_0x0122
            r2.dispatchStartTemporaryDetach()     // Catch:{ all -> 0x0029 }
        L_0x0122:
            r26.requestFocus()     // Catch:{ all -> 0x0029 }
        L_0x0125:
            r4 = r1
            r22 = r2
            int r1 = r7.mFirstPosition     // Catch:{ all -> 0x03b6 }
            r3 = r1
            android.widget.AbsListView$RecycleBin r1 = r7.mRecycler     // Catch:{ all -> 0x03b6 }
            r2 = r1
            if (r18 == 0) goto L_0x0141
            r1 = r9
        L_0x0131:
            if (r1 >= r12) goto L_0x0144
            android.view.View r9 = r7.getChildAt(r1)     // Catch:{ all -> 0x0029 }
            int r0 = r3 + r1
            r2.addScrapView(r9, r0)     // Catch:{ all -> 0x0029 }
            int r1 = r1 + 1
            r0 = 1
            r9 = 0
            goto L_0x0131
        L_0x0141:
            r2.fillActiveViews(r12, r3)     // Catch:{ all -> 0x03b6 }
        L_0x0144:
            r26.detachAllViewsFromParent()     // Catch:{ all -> 0x03b6 }
            r2.removeSkippedScrap()     // Catch:{ all -> 0x03b6 }
            int r0 = r7.mLayoutMode     // Catch:{ all -> 0x03b6 }
            switch(r0) {
                case 1: goto L_0x01e2;
                case 2: goto L_0x01c7;
                case 3: goto L_0x01b1;
                case 4: goto L_0x0189;
                case 5: goto L_0x0176;
                case 6: goto L_0x015e;
                default: goto L_0x014f;
            }
        L_0x014f:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            if (r12 != 0) goto L_0x021e
            boolean r1 = r7.mStackFromBottom     // Catch:{ all -> 0x03b4 }
            goto L_0x01f6
        L_0x015e:
            r1 = r26
            r0 = r2
            r2 = r13
            r9 = r3
            r3 = r15
            r23 = r9
            r9 = r4
            r4 = r17
            r24 = r5
            r5 = r10
            r25 = r8
            r8 = r6
            r6 = r11
            android.view.View r1 = r1.moveSelection(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x0176:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            int r1 = r7.mSyncPosition     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mSpecificTop     // Catch:{ all -> 0x03b4 }
            android.view.View r1 = r7.fillSpecific(r1, r2)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x0189:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            int r1 = r26.reconcileSelectedPosition()     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mSpecificTop     // Catch:{ all -> 0x03b4 }
            android.view.View r2 = r7.fillSpecific(r1, r2)     // Catch:{ all -> 0x03b4 }
            if (r2 != 0) goto L_0x01ae
            android.widget.ListView$FocusSelector r3 = r7.mFocusSelector     // Catch:{ all -> 0x03b4 }
            if (r3 == 0) goto L_0x01ae
            android.widget.ListView$FocusSelector r3 = r7.mFocusSelector     // Catch:{ all -> 0x03b4 }
            java.lang.Runnable r3 = r3.setupFocusIfValid(r1)     // Catch:{ all -> 0x03b4 }
            if (r3 == 0) goto L_0x01ad
            r7.post(r3)     // Catch:{ all -> 0x03b4 }
        L_0x01ad:
        L_0x01ae:
            r1 = r2
            goto L_0x0253
        L_0x01b1:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            int r1 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            r2 = 1
            int r1 = r1 - r2
            android.view.View r1 = r7.fillUp(r1, r11)     // Catch:{ all -> 0x03b4 }
            r26.adjustViewsUpOrDown()     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x01c7:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            if (r15 == 0) goto L_0x01dc
            int r1 = r15.getTop()     // Catch:{ all -> 0x03b4 }
            android.view.View r1 = r7.fillFromSelection(r1, r10, r11)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x01dc:
            android.view.View r1 = r7.fillFromMiddle(r10, r11)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x01e2:
            r0 = r2
            r23 = r3
            r9 = r4
            r24 = r5
            r25 = r8
            r8 = r6
            r1 = 0
            r7.mFirstPosition = r1     // Catch:{ all -> 0x03b4 }
            android.view.View r1 = r7.fillFromTop(r10)     // Catch:{ all -> 0x03b4 }
            r26.adjustViewsUpOrDown()     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x01f6:
            if (r1 != 0) goto L_0x0208
            r1 = 0
            r2 = 1
            int r3 = r7.lookForSelectablePosition(r1, r2)     // Catch:{ all -> 0x03b4 }
            r1 = r3
            r7.setSelectedPositionInt(r1)     // Catch:{ all -> 0x03b4 }
            android.view.View r2 = r7.fillFromTop(r10)     // Catch:{ all -> 0x03b4 }
            r1 = r2
            goto L_0x0253
        L_0x0208:
            int r1 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            r2 = 1
            int r1 = r1 - r2
            r2 = 0
            int r1 = r7.lookForSelectablePosition(r1, r2)     // Catch:{ all -> 0x03b4 }
            r7.setSelectedPositionInt(r1)     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            r3 = 1
            int r2 = r2 - r3
            android.view.View r2 = r7.fillUp(r2, r11)     // Catch:{ all -> 0x03b4 }
            r1 = r2
            goto L_0x0253
        L_0x021e:
            int r1 = r7.mSelectedPosition     // Catch:{ all -> 0x03b4 }
            if (r1 < 0) goto L_0x0237
            int r1 = r7.mSelectedPosition     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            if (r1 >= r2) goto L_0x0237
            int r1 = r7.mSelectedPosition     // Catch:{ all -> 0x03b4 }
            if (r13 != 0) goto L_0x022e
            r2 = r10
            goto L_0x0232
        L_0x022e:
            int r2 = r13.getTop()     // Catch:{ all -> 0x03b4 }
        L_0x0232:
            android.view.View r1 = r7.fillSpecific(r1, r2)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x0237:
            int r1 = r7.mFirstPosition     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            if (r1 >= r2) goto L_0x024c
            int r1 = r7.mFirstPosition     // Catch:{ all -> 0x03b4 }
            if (r14 != 0) goto L_0x0243
            r2 = r10
            goto L_0x0247
        L_0x0243:
            int r2 = r14.getTop()     // Catch:{ all -> 0x03b4 }
        L_0x0247:
            android.view.View r1 = r7.fillSpecific(r1, r2)     // Catch:{ all -> 0x03b4 }
            goto L_0x0253
        L_0x024c:
            r1 = 0
            android.view.View r2 = r7.fillSpecific(r1, r10)     // Catch:{ all -> 0x03b4 }
            goto L_0x01ae
        L_0x0253:
            r0.scrapActiveViews()     // Catch:{ all -> 0x03b4 }
            java.util.ArrayList<android.widget.ListView$FixedViewInfo> r2 = r7.mHeaderViewInfos     // Catch:{ all -> 0x03b4 }
            r7.removeUnusedFixedViews(r2)     // Catch:{ all -> 0x03b4 }
            java.util.ArrayList<android.widget.ListView$FixedViewInfo> r2 = r7.mFooterViewInfos     // Catch:{ all -> 0x03b4 }
            r7.removeUnusedFixedViews(r2)     // Catch:{ all -> 0x03b4 }
            r2 = -1
            if (r1 == 0) goto L_0x02a9
            boolean r3 = r7.mItemsCanFocus     // Catch:{ all -> 0x03b4 }
            if (r3 == 0) goto L_0x029f
            boolean r3 = r26.hasFocus()     // Catch:{ all -> 0x03b4 }
            if (r3 == 0) goto L_0x029f
            boolean r3 = r1.hasFocus()     // Catch:{ all -> 0x03b4 }
            if (r3 != 0) goto L_0x029f
            if (r1 != r9) goto L_0x027d
            if (r22 == 0) goto L_0x027d
            boolean r3 = r22.requestFocus()     // Catch:{ all -> 0x03b4 }
            if (r3 != 0) goto L_0x0283
        L_0x027d:
            boolean r3 = r1.requestFocus()     // Catch:{ all -> 0x03b4 }
            if (r3 == 0) goto L_0x0285
        L_0x0283:
            r3 = 1
            goto L_0x0286
        L_0x0285:
            r3 = 0
        L_0x0286:
            if (r3 != 0) goto L_0x0295
            android.view.View r4 = r26.getFocusedChild()     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x0291
            r4.clearFocus()     // Catch:{ all -> 0x03b4 }
        L_0x0291:
            r7.positionSelector(r2, r1)     // Catch:{ all -> 0x03b4 }
            goto L_0x029e
        L_0x0295:
            r4 = 0
            r1.setSelected(r4)     // Catch:{ all -> 0x03b4 }
            android.graphics.Rect r4 = r7.mSelectorRect     // Catch:{ all -> 0x03b4 }
            r4.setEmpty()     // Catch:{ all -> 0x03b4 }
        L_0x029e:
            goto L_0x02a2
        L_0x029f:
            r7.positionSelector(r2, r1)     // Catch:{ all -> 0x03b4 }
        L_0x02a2:
            int r3 = r1.getTop()     // Catch:{ all -> 0x03b4 }
            r7.mSelectedTop = r3     // Catch:{ all -> 0x03b4 }
            goto L_0x02f2
        L_0x02a9:
            int r3 = r7.mTouchMode     // Catch:{ all -> 0x03b4 }
            r4 = 1
            if (r3 == r4) goto L_0x02b6
            int r3 = r7.mTouchMode     // Catch:{ all -> 0x03b4 }
            r4 = 2
            if (r3 != r4) goto L_0x02b4
            goto L_0x02b6
        L_0x02b4:
            r3 = 0
            goto L_0x02b7
        L_0x02b6:
            r3 = 1
        L_0x02b7:
            if (r3 == 0) goto L_0x02ca
            int r4 = r7.mMotionPosition     // Catch:{ all -> 0x03b4 }
            int r5 = r7.mFirstPosition     // Catch:{ all -> 0x03b4 }
            int r4 = r4 - r5
            android.view.View r4 = r7.getChildAt(r4)     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x02c9
            int r5 = r7.mMotionPosition     // Catch:{ all -> 0x03b4 }
            r7.positionSelector(r5, r4)     // Catch:{ all -> 0x03b4 }
        L_0x02c9:
            goto L_0x02e7
        L_0x02ca:
            int r4 = r7.mSelectorPosition     // Catch:{ all -> 0x03b4 }
            if (r4 == r2) goto L_0x02df
            int r4 = r7.mSelectorPosition     // Catch:{ all -> 0x03b4 }
            int r5 = r7.mFirstPosition     // Catch:{ all -> 0x03b4 }
            int r4 = r4 - r5
            android.view.View r4 = r7.getChildAt(r4)     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x02de
            int r5 = r7.mSelectorPosition     // Catch:{ all -> 0x03b4 }
            r7.positionSelector(r5, r4)     // Catch:{ all -> 0x03b4 }
        L_0x02de:
            goto L_0x02e7
        L_0x02df:
            r4 = 0
            r7.mSelectedTop = r4     // Catch:{ all -> 0x03b4 }
            android.graphics.Rect r4 = r7.mSelectorRect     // Catch:{ all -> 0x03b4 }
            r4.setEmpty()     // Catch:{ all -> 0x03b4 }
        L_0x02e7:
            boolean r4 = r26.hasFocus()     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x02f2
            if (r22 == 0) goto L_0x02f2
            r22.requestFocus()     // Catch:{ all -> 0x03b4 }
        L_0x02f2:
            r3 = 0
            if (r19 == 0) goto L_0x0339
            android.view.View r4 = r19.getAccessibilityFocusedHost()     // Catch:{ all -> 0x03b4 }
            if (r4 != 0) goto L_0x0339
            if (r21 == 0) goto L_0x031f
            boolean r5 = r21.isAttachedToWindow()     // Catch:{ all -> 0x03b4 }
            if (r5 == 0) goto L_0x031f
            android.view.accessibility.AccessibilityNodeProvider r2 = r21.getAccessibilityNodeProvider()     // Catch:{ all -> 0x03b4 }
            if (r20 == 0) goto L_0x031b
            if (r2 == 0) goto L_0x031b
            long r5 = r20.getSourceNodeId()     // Catch:{ all -> 0x03b4 }
            int r5 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(r5)     // Catch:{ all -> 0x03b4 }
            r6 = 64
            r2.performAction(r5, r6, r3)     // Catch:{ all -> 0x03b4 }
            goto L_0x031e
        L_0x031b:
            r21.requestAccessibilityFocus()     // Catch:{ all -> 0x03b4 }
        L_0x031e:
            goto L_0x0339
        L_0x031f:
            if (r8 == r2) goto L_0x0339
            int r2 = r7.mFirstPosition     // Catch:{ all -> 0x03b4 }
            int r6 = r8 - r2
            int r2 = r26.getChildCount()     // Catch:{ all -> 0x03b4 }
            r5 = 1
            int r2 = r2 - r5
            r5 = 0
            int r2 = android.util.MathUtils.constrain((int) r6, (int) r5, (int) r2)     // Catch:{ all -> 0x03b4 }
            android.view.View r5 = r7.getChildAt(r2)     // Catch:{ all -> 0x03b4 }
            if (r5 == 0) goto L_0x0339
            r5.requestAccessibilityFocus()     // Catch:{ all -> 0x03b4 }
        L_0x0339:
            if (r22 == 0) goto L_0x0344
            android.os.IBinder r2 = r22.getWindowToken()     // Catch:{ all -> 0x03b4 }
            if (r2 == 0) goto L_0x0344
            r22.dispatchFinishTemporaryDetach()     // Catch:{ all -> 0x03b4 }
        L_0x0344:
            r2 = 0
            r7.mLayoutMode = r2     // Catch:{ all -> 0x03b4 }
            r7.mDataChanged = r2     // Catch:{ all -> 0x03b4 }
            java.lang.Runnable r2 = r7.mPositionScrollAfterLayout     // Catch:{ all -> 0x03b4 }
            if (r2 == 0) goto L_0x0354
            java.lang.Runnable r2 = r7.mPositionScrollAfterLayout     // Catch:{ all -> 0x03b4 }
            r7.post(r2)     // Catch:{ all -> 0x03b4 }
            r7.mPositionScrollAfterLayout = r3     // Catch:{ all -> 0x03b4 }
        L_0x0354:
            r2 = 0
            r7.mNeedSync = r2     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mSelectedPosition     // Catch:{ all -> 0x03b4 }
            r7.setNextSelectedPositionInt(r2)     // Catch:{ all -> 0x03b4 }
            r26.updateScrollIndicators()     // Catch:{ all -> 0x03b4 }
            int r2 = r7.mItemCount     // Catch:{ all -> 0x03b4 }
            if (r2 <= 0) goto L_0x0366
            r26.checkSelectionChanged()     // Catch:{ all -> 0x03b4 }
        L_0x0366:
            r26.invokeOnItemScrollListener()     // Catch:{ all -> 0x03b4 }
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            if (r0 == 0) goto L_0x0372
            android.widget.ListView$FocusSelector r0 = r7.mFocusSelector
            r0.onLayoutComplete()
        L_0x0372:
            if (r25 != 0) goto L_0x0377
            r1 = 0
            r7.mBlockLayoutRequests = r1
        L_0x0377:
            return
        L_0x0378:
            r25 = r8
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x03b4 }
            r1.<init>()     // Catch:{ all -> 0x03b4 }
            java.lang.String r2 = "The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. Make sure your adapter calls notifyDataSetChanged() when its content changes. [in ListView("
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            int r2 = r26.getId()     // Catch:{ all -> 0x03b4 }
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            java.lang.String r2 = ", "
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            java.lang.Class r2 = r26.getClass()     // Catch:{ all -> 0x03b4 }
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            java.lang.String r2 = ") with Adapter("
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            android.widget.ListAdapter r2 = r7.mAdapter     // Catch:{ all -> 0x03b4 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x03b4 }
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            java.lang.String r2 = ")]"
            r1.append(r2)     // Catch:{ all -> 0x03b4 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x03b4 }
            r0.<init>(r1)     // Catch:{ all -> 0x03b4 }
            throw r0     // Catch:{ all -> 0x03b4 }
        L_0x03b4:
            r0 = move-exception
            goto L_0x03b9
        L_0x03b6:
            r0 = move-exception
            r25 = r8
        L_0x03b9:
            android.widget.ListView$FocusSelector r1 = r7.mFocusSelector
            if (r1 == 0) goto L_0x03c2
            android.widget.ListView$FocusSelector r1 = r7.mFocusSelector
            r1.onLayoutComplete()
        L_0x03c2:
            if (r25 != 0) goto L_0x03c7
            r1 = 0
            r7.mBlockLayoutRequests = r1
        L_0x03c7:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.layoutChildren():void");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean trackMotionScroll(int deltaY, int incrementalDeltaY) {
        boolean result = super.trackMotionScroll(deltaY, incrementalDeltaY);
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        return result;
    }

    private void removeUnusedFixedViews(List<FixedViewInfo> infoList) {
        if (infoList != null) {
            for (int i = infoList.size() - 1; i >= 0; i--) {
                View view = infoList.get(i).view;
                AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
                if (view.getParent() == null && lp != null && lp.recycledHeaderFooter) {
                    removeDetachedView(view, false);
                    lp.recycledHeaderFooter = false;
                }
            }
        }
    }

    @UnsupportedAppUsage
    private boolean isDirectChildHeaderOrFooter(View child) {
        ArrayList<FixedViewInfo> headers = this.mHeaderViewInfos;
        int numHeaders = headers.size();
        for (int i = 0; i < numHeaders; i++) {
            if (child == headers.get(i).view) {
                return true;
            }
        }
        ArrayList<FixedViewInfo> footers = this.mFooterViewInfos;
        int numFooters = footers.size();
        for (int i2 = 0; i2 < numFooters; i2++) {
            if (child == footers.get(i2).view) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    private View makeAndAddView(int position, int y, boolean flow, int childrenLeft, boolean selected) {
        View activeView;
        if (this.mDataChanged || (activeView = this.mRecycler.getActiveView(position)) == null) {
            View child = obtainView(position, this.mIsScrap);
            setupChild(child, position, y, flow, childrenLeft, selected, this.mIsScrap[0]);
            return child;
        }
        setupChild(activeView, position, y, flow, childrenLeft, selected, true);
        return activeView;
    }

    /* JADX WARNING: type inference failed for: r16v0, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setupChild(android.view.View r19, int r20, int r21, boolean r22, int r23, boolean r24, boolean r25) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r4 = r23
            java.lang.String r5 = "setupListItem"
            r6 = 8
            android.os.Trace.traceBegin(r6, r5)
            r8 = 0
            if (r24 == 0) goto L_0x001b
            boolean r9 = r18.shouldShowSelector()
            if (r9 == 0) goto L_0x001b
            r9 = 1
            goto L_0x001c
        L_0x001b:
            r9 = r8
        L_0x001c:
            boolean r10 = r19.isSelected()
            if (r9 == r10) goto L_0x0024
            r10 = 1
            goto L_0x0025
        L_0x0024:
            r10 = r8
        L_0x0025:
            int r11 = r0.mTouchMode
            if (r11 <= 0) goto L_0x0032
            r12 = 3
            if (r11 >= r12) goto L_0x0032
            int r12 = r0.mMotionPosition
            if (r12 != r2) goto L_0x0032
            r12 = 1
            goto L_0x0033
        L_0x0032:
            r12 = r8
        L_0x0033:
            boolean r13 = r19.isPressed()
            if (r12 == r13) goto L_0x003b
            r13 = 1
            goto L_0x003c
        L_0x003b:
            r13 = r8
        L_0x003c:
            if (r25 == 0) goto L_0x0049
            if (r10 != 0) goto L_0x0049
            boolean r14 = r19.isLayoutRequested()
            if (r14 == 0) goto L_0x0047
            goto L_0x0049
        L_0x0047:
            r14 = r8
            goto L_0x004a
        L_0x0049:
            r14 = 1
        L_0x004a:
            android.view.ViewGroup$LayoutParams r15 = r19.getLayoutParams()
            android.widget.AbsListView$LayoutParams r15 = (android.widget.AbsListView.LayoutParams) r15
            if (r15 != 0) goto L_0x005a
            android.view.ViewGroup$LayoutParams r16 = r18.generateDefaultLayoutParams()
            r15 = r16
            android.widget.AbsListView$LayoutParams r15 = (android.widget.AbsListView.LayoutParams) r15
        L_0x005a:
            r6 = r15
            android.widget.ListAdapter r7 = r0.mAdapter
            int r7 = r7.getItemViewType(r2)
            r6.viewType = r7
            android.widget.ListAdapter r7 = r0.mAdapter
            boolean r7 = r7.isEnabled(r2)
            r6.isEnabled = r7
            if (r10 == 0) goto L_0x0070
            r1.setSelected(r9)
        L_0x0070:
            if (r13 == 0) goto L_0x0075
            r1.setPressed(r12)
        L_0x0075:
            int r7 = r0.mChoiceMode
            if (r7 == 0) goto L_0x00a5
            android.util.SparseBooleanArray r7 = r0.mCheckStates
            if (r7 == 0) goto L_0x00a5
            boolean r7 = r1 instanceof android.widget.Checkable
            if (r7 == 0) goto L_0x008e
            r7 = r1
            android.widget.Checkable r7 = (android.widget.Checkable) r7
            android.util.SparseBooleanArray r5 = r0.mCheckStates
            boolean r5 = r5.get(r2)
            r7.setChecked(r5)
            goto L_0x00a5
        L_0x008e:
            android.content.Context r5 = r18.getContext()
            android.content.pm.ApplicationInfo r5 = r5.getApplicationInfo()
            int r5 = r5.targetSdkVersion
            r7 = 11
            if (r5 < r7) goto L_0x00a5
            android.util.SparseBooleanArray r5 = r0.mCheckStates
            boolean r5 = r5.get(r2)
            r1.setActivated(r5)
        L_0x00a5:
            if (r25 == 0) goto L_0x00ab
            boolean r7 = r6.forceAdd
            if (r7 == 0) goto L_0x00b4
        L_0x00ab:
            boolean r7 = r6.recycledHeaderFooter
            r5 = -2
            if (r7 == 0) goto L_0x00cc
            int r7 = r6.viewType
            if (r7 != r5) goto L_0x00cc
        L_0x00b4:
            if (r22 == 0) goto L_0x00b8
            r5 = -1
            goto L_0x00b9
        L_0x00b8:
            r5 = r8
        L_0x00b9:
            r0.attachViewToParent(r1, r5, r6)
            if (r25 == 0) goto L_0x00e2
            android.view.ViewGroup$LayoutParams r5 = r19.getLayoutParams()
            android.widget.AbsListView$LayoutParams r5 = (android.widget.AbsListView.LayoutParams) r5
            int r5 = r5.scrappedFromPosition
            if (r5 == r2) goto L_0x00e2
            r19.jumpDrawablesToCurrentState()
            goto L_0x00e2
        L_0x00cc:
            r6.forceAdd = r8
            int r7 = r6.viewType
            if (r7 != r5) goto L_0x00d6
            r5 = 1
            r6.recycledHeaderFooter = r5
            goto L_0x00d7
        L_0x00d6:
            r5 = 1
        L_0x00d7:
            if (r22 == 0) goto L_0x00db
            r7 = -1
            goto L_0x00dc
        L_0x00db:
            r7 = r8
        L_0x00dc:
            r0.addViewInLayout(r1, r7, r6, r5)
            r19.resolveRtlPropertiesIfNeeded()
        L_0x00e2:
            if (r14 == 0) goto L_0x010e
            int r5 = r0.mWidthMeasureSpec
            android.graphics.Rect r7 = r0.mListPadding
            int r7 = r7.left
            android.graphics.Rect r8 = r0.mListPadding
            int r8 = r8.right
            int r7 = r7 + r8
            int r8 = r6.width
            int r5 = android.view.ViewGroup.getChildMeasureSpec(r5, r7, r8)
            int r7 = r6.height
            if (r7 <= 0) goto L_0x0100
            r8 = 1073741824(0x40000000, float:2.0)
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r8)
            goto L_0x0109
        L_0x0100:
            int r8 = r18.getMeasuredHeight()
            r2 = 0
            int r8 = android.view.View.MeasureSpec.makeSafeMeasureSpec(r8, r2)
        L_0x0109:
            r2 = r8
            r1.measure(r5, r2)
            goto L_0x0111
        L_0x010e:
            r18.cleanupLayoutState(r19)
        L_0x0111:
            int r2 = r19.getMeasuredWidth()
            int r5 = r19.getMeasuredHeight()
            if (r22 == 0) goto L_0x011e
            r8 = r21
            goto L_0x0120
        L_0x011e:
            int r8 = r21 - r5
        L_0x0120:
            if (r14 == 0) goto L_0x012c
            int r3 = r4 + r2
            r17 = r2
            int r2 = r8 + r5
            r1.layout(r4, r8, r3, r2)
            goto L_0x0140
        L_0x012c:
            r17 = r2
            int r2 = r19.getLeft()
            int r2 = r4 - r2
            r1.offsetLeftAndRight(r2)
            int r2 = r19.getTop()
            int r2 = r8 - r2
            r1.offsetTopAndBottom(r2)
        L_0x0140:
            boolean r2 = r0.mCachingStarted
            if (r2 == 0) goto L_0x014e
            boolean r2 = r19.isDrawingCacheEnabled()
            if (r2 != 0) goto L_0x014e
            r2 = 1
            r1.setDrawingCacheEnabled(r2)
        L_0x014e:
            r2 = 8
            android.os.Trace.traceEnd(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.setupChild(android.view.View, int, int, boolean, int, boolean, boolean):void");
    }

    /* access modifiers changed from: protected */
    public boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    public void setSelection(int position) {
        setSelectionFromTop(position, 0);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setSelectionInt(int position) {
        setNextSelectedPositionInt(position);
        boolean awakeScrollbars = false;
        int selectedPosition = this.mSelectedPosition;
        if (selectedPosition >= 0) {
            if (position == selectedPosition - 1) {
                awakeScrollbars = true;
            } else if (position == selectedPosition + 1) {
                awakeScrollbars = true;
            }
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        layoutChildren();
        if (awakeScrollbars) {
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int lookForSelectablePosition(int position, boolean lookDown) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter.getCount();
        if (!this.mAreAllItemsSelectable) {
            if (lookDown) {
                position = Math.max(0, position);
                while (position < count && !adapter.isEnabled(position)) {
                    position++;
                }
            } else {
                position = Math.min(position, count - 1);
                while (position >= 0 && !adapter.isEnabled(position)) {
                    position = position - 1;
                }
            }
        }
        if (position < 0 || position >= count) {
            return -1;
        }
        return position;
    }

    /* access modifiers changed from: package-private */
    public int lookForSelectablePositionAfter(int current, int position, boolean lookDown) {
        int position2;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int after = lookForSelectablePosition(position, lookDown);
        if (after != -1) {
            return after;
        }
        int count = adapter.getCount();
        int current2 = MathUtils.constrain(current, -1, count - 1);
        if (lookDown) {
            position2 = Math.min(position - 1, count - 1);
            while (position2 > current2 && !adapter.isEnabled(position2)) {
                position2--;
            }
            if (position2 <= current2) {
                return -1;
            }
        } else {
            int position3 = Math.max(0, position + 1);
            while (position2 < current2 && !adapter.isEnabled(position2)) {
                position3 = position2 + 1;
            }
            if (position2 >= current2) {
                return -1;
            }
        }
        return position2;
    }

    public void setSelectionAfterHeaderView() {
        int count = getHeaderViewsCount();
        if (count > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(count);
        } else {
            this.mNextSelectedPosition = count;
            this.mLayoutMode = 2;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled || getFocusedChild() == null || event.getAction() != 0) {
            return handled;
        }
        return onKeyDown(event.getKeyCode(), event);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return commonKey(keyCode, repeatCount, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x017a, code lost:
        r9 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean commonKey(int r8, int r9, android.view.KeyEvent r10) {
        /*
            r7 = this;
            android.widget.ListAdapter r0 = r7.mAdapter
            r1 = 0
            if (r0 == 0) goto L_0x01b0
            boolean r0 = r7.isAttachedToWindow()
            if (r0 != 0) goto L_0x000d
            goto L_0x01b0
        L_0x000d:
            boolean r0 = r7.mDataChanged
            if (r0 == 0) goto L_0x0014
            r7.layoutChildren()
        L_0x0014:
            r0 = 0
            int r2 = r10.getAction()
            boolean r3 = android.view.KeyEvent.isConfirmKey(r8)
            r4 = 1
            if (r3 == 0) goto L_0x003e
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x003e
            if (r2 == r4) goto L_0x003e
            boolean r0 = r7.resurrectSelectionIfNeeded()
            if (r0 != 0) goto L_0x003e
            int r3 = r10.getRepeatCount()
            if (r3 != 0) goto L_0x003e
            int r3 = r7.getChildCount()
            if (r3 <= 0) goto L_0x003e
            r7.keyPressed()
            r0 = 1
        L_0x003e:
            if (r0 != 0) goto L_0x0193
            if (r2 == r4) goto L_0x0193
            r3 = 2
            r5 = 33
            r6 = 130(0x82, float:1.82E-43)
            switch(r8) {
                case 19: goto L_0x0161;
                case 20: goto L_0x0130;
                case 21: goto L_0x0122;
                case 22: goto L_0x0114;
                case 61: goto L_0x00e2;
                case 92: goto L_0x00b0;
                case 93: goto L_0x007e;
                case 122: goto L_0x0065;
                case 123: goto L_0x004c;
                case 691: goto L_0x0161;
                case 692: goto L_0x0130;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x0193
        L_0x004c:
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x0061
            boolean r3 = r7.fullScroll(r6)
            if (r3 == 0) goto L_0x005f
            goto L_0x0061
        L_0x005f:
            r3 = r1
            goto L_0x0062
        L_0x0061:
            r3 = r4
        L_0x0062:
            r0 = r3
            goto L_0x0193
        L_0x0065:
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x007a
            boolean r3 = r7.fullScroll(r5)
            if (r3 == 0) goto L_0x0078
            goto L_0x007a
        L_0x0078:
            r3 = r1
            goto L_0x007b
        L_0x007a:
            r3 = r4
        L_0x007b:
            r0 = r3
            goto L_0x0193
        L_0x007e:
            boolean r5 = r10.hasNoModifiers()
            if (r5 == 0) goto L_0x0097
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x0093
            boolean r3 = r7.pageScroll(r6)
            if (r3 == 0) goto L_0x0091
            goto L_0x0093
        L_0x0091:
            r3 = r1
            goto L_0x0094
        L_0x0093:
            r3 = r4
        L_0x0094:
            r0 = r3
            goto L_0x0193
        L_0x0097:
            boolean r3 = r10.hasModifiers(r3)
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x00ac
            boolean r3 = r7.fullScroll(r6)
            if (r3 == 0) goto L_0x00aa
            goto L_0x00ac
        L_0x00aa:
            r3 = r1
            goto L_0x00ad
        L_0x00ac:
            r3 = r4
        L_0x00ad:
            r0 = r3
            goto L_0x0193
        L_0x00b0:
            boolean r6 = r10.hasNoModifiers()
            if (r6 == 0) goto L_0x00c9
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x00c5
            boolean r3 = r7.pageScroll(r5)
            if (r3 == 0) goto L_0x00c3
            goto L_0x00c5
        L_0x00c3:
            r3 = r1
            goto L_0x00c6
        L_0x00c5:
            r3 = r4
        L_0x00c6:
            r0 = r3
            goto L_0x0193
        L_0x00c9:
            boolean r3 = r10.hasModifiers(r3)
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x00de
            boolean r3 = r7.fullScroll(r5)
            if (r3 == 0) goto L_0x00dc
            goto L_0x00de
        L_0x00dc:
            r3 = r1
            goto L_0x00df
        L_0x00de:
            r3 = r4
        L_0x00df:
            r0 = r3
            goto L_0x0193
        L_0x00e2:
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x00fb
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x00f7
            boolean r3 = r7.arrowScroll(r6)
            if (r3 == 0) goto L_0x00f5
            goto L_0x00f7
        L_0x00f5:
            r3 = r1
            goto L_0x00f8
        L_0x00f7:
            r3 = r4
        L_0x00f8:
            r0 = r3
            goto L_0x0193
        L_0x00fb:
            boolean r3 = r10.hasModifiers(r4)
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x0110
            boolean r3 = r7.arrowScroll(r5)
            if (r3 == 0) goto L_0x010e
            goto L_0x0110
        L_0x010e:
            r3 = r1
            goto L_0x0111
        L_0x0110:
            r3 = r4
        L_0x0111:
            r0 = r3
            goto L_0x0193
        L_0x0114:
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x0193
            r3 = 66
            boolean r0 = r7.handleHorizontalFocusWithinListItem(r3)
            goto L_0x0193
        L_0x0122:
            boolean r3 = r10.hasNoModifiers()
            if (r3 == 0) goto L_0x0193
            r3 = 17
            boolean r0 = r7.handleHorizontalFocusWithinListItem(r3)
            goto L_0x0193
        L_0x0130:
            boolean r5 = r10.hasNoModifiers()
            if (r5 == 0) goto L_0x0149
            boolean r0 = r7.resurrectSelectionIfNeeded()
            if (r0 != 0) goto L_0x0193
        L_0x013c:
            int r3 = r9 + -1
            if (r9 <= 0) goto L_0x017a
            boolean r9 = r7.arrowScroll(r6)
            if (r9 == 0) goto L_0x017a
            r0 = 1
            r9 = r3
            goto L_0x013c
        L_0x0149:
            boolean r3 = r10.hasModifiers(r3)
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x015e
            boolean r3 = r7.fullScroll(r6)
            if (r3 == 0) goto L_0x015c
            goto L_0x015e
        L_0x015c:
            r3 = r1
            goto L_0x015f
        L_0x015e:
            r3 = r4
        L_0x015f:
            r0 = r3
            goto L_0x0193
        L_0x0161:
            boolean r6 = r10.hasNoModifiers()
            if (r6 == 0) goto L_0x017c
            boolean r0 = r7.resurrectSelectionIfNeeded()
            if (r0 != 0) goto L_0x0193
        L_0x016d:
            int r3 = r9 + -1
            if (r9 <= 0) goto L_0x017a
            boolean r9 = r7.arrowScroll(r5)
            if (r9 == 0) goto L_0x017a
            r0 = 1
            r9 = r3
            goto L_0x016d
        L_0x017a:
            r9 = r3
            goto L_0x0193
        L_0x017c:
            boolean r3 = r10.hasModifiers(r3)
            if (r3 == 0) goto L_0x0193
            boolean r3 = r7.resurrectSelectionIfNeeded()
            if (r3 != 0) goto L_0x0191
            boolean r3 = r7.fullScroll(r5)
            if (r3 == 0) goto L_0x018f
            goto L_0x0191
        L_0x018f:
            r3 = r1
            goto L_0x0192
        L_0x0191:
            r3 = r4
        L_0x0192:
            r0 = r3
        L_0x0193:
            if (r0 == 0) goto L_0x0196
            return r4
        L_0x0196:
            boolean r3 = r7.sendToTextFilter(r8, r9, r10)
            if (r3 == 0) goto L_0x019d
            return r4
        L_0x019d:
            switch(r2) {
                case 0: goto L_0x01ab;
                case 1: goto L_0x01a6;
                case 2: goto L_0x01a1;
                default: goto L_0x01a0;
            }
        L_0x01a0:
            return r1
        L_0x01a1:
            boolean r1 = super.onKeyMultiple(r8, r9, r10)
            return r1
        L_0x01a6:
            boolean r1 = super.onKeyUp(r8, r10)
            return r1
        L_0x01ab:
            boolean r1 = super.onKeyDown(r8, r10)
            return r1
        L_0x01b0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ListView.commonKey(int, int, android.view.KeyEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean pageScroll(int direction) {
        boolean down;
        int nextPage;
        int position;
        if (direction == 33) {
            nextPage = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
            down = false;
        } else if (direction != 130) {
            return false;
        } else {
            nextPage = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            down = true;
        }
        if (nextPage < 0 || (position = lookForSelectablePositionAfter(this.mSelectedPosition, nextPage, down)) < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        this.mSpecificTop = this.mPaddingTop + getVerticalFadingEdgeLength();
        if (down && position > this.mItemCount - getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!down && position < getChildCount()) {
            this.mLayoutMode = 1;
        }
        setSelectionInt(position);
        invokeOnItemScrollListener();
        if (!awakenScrollBars()) {
            invalidate();
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean fullScroll(int direction) {
        int lastItem;
        boolean moved = false;
        if (direction == 33) {
            if (this.mSelectedPosition != 0) {
                int position = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (position >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if (direction == 130 && this.mSelectedPosition < (lastItem = this.mItemCount - 1)) {
            int position2 = lookForSelectablePositionAfter(this.mSelectedPosition, lastItem, false);
            if (position2 >= 0) {
                this.mLayoutMode = 3;
                setSelectionInt(position2);
                invokeOnItemScrollListener();
            }
            moved = true;
        }
        if (moved && !awakenScrollBars()) {
            awakenScrollBars();
            invalidate();
        }
        return moved;
    }

    private boolean handleHorizontalFocusWithinListItem(int direction) {
        View selectedView;
        if (direction == 17 || direction == 66) {
            int numChildren = getChildCount();
            if (!this.mItemsCanFocus || numChildren <= 0 || this.mSelectedPosition == -1 || (selectedView = getSelectedView()) == null || !selectedView.hasFocus() || !(selectedView instanceof ViewGroup)) {
                return false;
            }
            View currentFocus = selectedView.findFocus();
            View nextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, currentFocus, direction);
            if (nextFocus != null) {
                Rect focusedRect = this.mTempRect;
                if (currentFocus != null) {
                    currentFocus.getFocusedRect(focusedRect);
                    offsetDescendantRectToMyCoords(currentFocus, focusedRect);
                    offsetRectIntoDescendantCoords(nextFocus, focusedRect);
                } else {
                    focusedRect = null;
                }
                if (nextFocus.requestFocus(direction, focusedRect)) {
                    return true;
                }
            }
            View globalNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), currentFocus, direction);
            if (globalNextFocus != null) {
                return isViewAncestorOf(globalNextFocus, this);
            }
            return false;
        }
        throw new IllegalArgumentException("direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT}");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean arrowScroll(int direction) {
        try {
            this.mInLayout = true;
            boolean handled = arrowScrollImpl(direction);
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            this.mInLayout = false;
            return handled;
        } catch (Throwable th) {
            this.mInLayout = false;
            throw th;
        }
    }

    private final int nextSelectedPositionForDirection(View selectedView, int selectedPos, int direction) {
        int nextSelected;
        int i;
        boolean z = true;
        if (direction == 130) {
            int listBottom = getHeight() - this.mListPadding.bottom;
            if (selectedView == null || selectedView.getBottom() > listBottom) {
                return -1;
            }
            if (selectedPos == -1 || selectedPos < this.mFirstPosition) {
                nextSelected = this.mFirstPosition;
            } else {
                nextSelected = selectedPos + 1;
            }
        } else {
            int listTop = this.mListPadding.top;
            if (selectedView == null || selectedView.getTop() < listTop) {
                return -1;
            }
            int lastPos = (this.mFirstPosition + getChildCount()) - 1;
            if (selectedPos == -1 || selectedPos > lastPos) {
                i = lastPos;
            } else {
                i = selectedPos - 1;
            }
            nextSelected = i;
        }
        int nextSelected2 = nextSelected;
        if (nextSelected2 < 0 || nextSelected2 >= this.mAdapter.getCount()) {
            return -1;
        }
        if (direction != 130) {
            z = false;
        }
        return lookForSelectablePosition(nextSelected2, z);
    }

    private boolean arrowScrollImpl(int direction) {
        View focused;
        View focused2;
        if (getChildCount() <= 0) {
            return false;
        }
        View selectedView = getSelectedView();
        int selectedPos = this.mSelectedPosition;
        int nextSelectedPosition = nextSelectedPositionForDirection(selectedView, selectedPos, direction);
        int amountToScroll = amountToScroll(direction, nextSelectedPosition);
        ArrowScrollFocusResult focusResult = this.mItemsCanFocus ? arrowScrollFocused(direction) : null;
        if (focusResult != null) {
            nextSelectedPosition = focusResult.getSelectedPosition();
            amountToScroll = focusResult.getAmountToScroll();
        }
        boolean needToRedraw = focusResult != null;
        if (nextSelectedPosition != -1) {
            handleNewSelectionChange(selectedView, direction, nextSelectedPosition, focusResult != null);
            setSelectedPositionInt(nextSelectedPosition);
            setNextSelectedPositionInt(nextSelectedPosition);
            selectedView = getSelectedView();
            selectedPos = nextSelectedPosition;
            if (this.mItemsCanFocus && focusResult == null && (focused2 = getFocusedChild()) != null) {
                focused2.clearFocus();
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            scrollListItemsBy(direction == 33 ? amountToScroll : -amountToScroll);
            needToRedraw = true;
        }
        if (this.mItemsCanFocus && focusResult == null && selectedView != null && selectedView.hasFocus() && (focused = selectedView.findFocus()) != null && (!isViewAncestorOf(focused, this) || distanceToView(focused) > 0)) {
            focused.clearFocus();
        }
        if (nextSelectedPosition == -1 && selectedView != null && !isViewAncestorOf(selectedView, this)) {
            selectedView = null;
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!needToRedraw) {
            return false;
        }
        if (selectedView != null) {
            positionSelectorLikeFocus(selectedPos, selectedView);
            this.mSelectedTop = selectedView.getTop();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        View bottomView;
        View topView;
        int bottomViewIndex;
        int topViewIndex;
        if (newSelectedPosition != -1) {
            boolean topSelected = false;
            int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
            int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
            if (direction == 33) {
                topViewIndex = nextSelectedIndex;
                bottomViewIndex = selectedIndex;
                topView = getChildAt(topViewIndex);
                bottomView = selectedView;
                topSelected = true;
            } else {
                topViewIndex = selectedIndex;
                bottomViewIndex = nextSelectedIndex;
                topView = selectedView;
                bottomView = getChildAt(bottomViewIndex);
            }
            int numChildren = getChildCount();
            boolean z = true;
            if (topView != null) {
                topView.setSelected(!newFocusAssigned && topSelected);
                measureAndAdjustDown(topView, topViewIndex, numChildren);
            }
            if (bottomView != null) {
                if (newFocusAssigned || topSelected) {
                    z = false;
                }
                bottomView.setSelected(z);
                measureAndAdjustDown(bottomView, bottomViewIndex, numChildren);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("newSelectedPosition needs to be valid");
    }

    private void measureAndAdjustDown(View child, int childIndex, int numChildren) {
        int oldHeight = child.getHeight();
        measureItem(child);
        if (child.getMeasuredHeight() != oldHeight) {
            relayoutMeasuredItem(child);
            int heightDelta = child.getMeasuredHeight() - oldHeight;
            for (int i = childIndex + 1; i < numChildren; i++) {
                getChildAt(i).offsetTopAndBottom(heightDelta);
            }
        }
    }

    private void measureItem(View child) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private void relayoutMeasuredItem(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = this.mListPadding.left;
        int childTop = child.getTop();
        child.layout(childLeft, childTop, childLeft + w, childTop + h);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getVerticalFadingEdgeLength());
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        int listBottom = getHeight() - this.mListPadding.bottom;
        int listTop = this.mListPadding.top;
        int numChildren = getChildCount();
        if (direction == 130) {
            int indexToMakeVisible = numChildren - 1;
            if (nextSelectedPosition != -1) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            while (numChildren <= indexToMakeVisible) {
                addViewBelow(getChildAt(numChildren - 1), (this.mFirstPosition + numChildren) - 1);
                numChildren++;
            }
            int positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            View viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalBottom = listBottom;
            if (positionToMakeVisible < this.mItemCount - 1) {
                goalBottom -= getArrowScrollPreviewLength();
            }
            if (viewToMakeVisible.getBottom() <= goalBottom) {
                return 0;
            }
            if (nextSelectedPosition != -1 && goalBottom - viewToMakeVisible.getTop() >= getMaxScrollAmount()) {
                return 0;
            }
            int amountToScroll = viewToMakeVisible.getBottom() - goalBottom;
            if (this.mFirstPosition + numChildren == this.mItemCount) {
                amountToScroll = Math.min(amountToScroll, getChildAt(numChildren - 1).getBottom() - listBottom);
            }
            return Math.min(amountToScroll, getMaxScrollAmount());
        }
        int indexToMakeVisible2 = 0;
        if (nextSelectedPosition != -1) {
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        while (indexToMakeVisible2 < 0) {
            addViewAbove(getChildAt(0), this.mFirstPosition);
            this.mFirstPosition--;
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        int positionToMakeVisible2 = this.mFirstPosition + indexToMakeVisible2;
        View viewToMakeVisible2 = getChildAt(indexToMakeVisible2);
        int goalTop = listTop;
        if (positionToMakeVisible2 > 0) {
            goalTop += getArrowScrollPreviewLength();
        }
        if (viewToMakeVisible2.getTop() >= goalTop) {
            return 0;
        }
        if (nextSelectedPosition != -1 && viewToMakeVisible2.getBottom() - goalTop >= getMaxScrollAmount()) {
            return 0;
        }
        int amountToScroll2 = goalTop - viewToMakeVisible2.getTop();
        if (this.mFirstPosition == 0) {
            amountToScroll2 = Math.min(amountToScroll2, listTop - getChildAt(0).getTop());
        }
        return Math.min(amountToScroll2, getMaxScrollAmount());
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {
        }

        /* access modifiers changed from: package-private */
        public void populate(int selectedPosition, int amountToScroll) {
            this.mSelectedPosition = selectedPosition;
            this.mAmountToScroll = amountToScroll;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }
    }

    private int lookForSelectablePositionOnScreen(int direction) {
        int startPos;
        int startPos2;
        int firstPosition = this.mFirstPosition;
        if (direction == 130) {
            if (this.mSelectedPosition != -1) {
                startPos2 = this.mSelectedPosition + 1;
            } else {
                startPos2 = firstPosition;
            }
            if (startPos2 >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos2 < firstPosition) {
                startPos2 = firstPosition;
            }
            int lastVisiblePos = getLastVisiblePosition();
            ListAdapter adapter = getAdapter();
            for (int pos = startPos2; pos <= lastVisiblePos; pos++) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
            }
        } else {
            int last = (getChildCount() + firstPosition) - 1;
            if (this.mSelectedPosition != -1) {
                startPos = this.mSelectedPosition - 1;
            } else {
                startPos = (getChildCount() + firstPosition) - 1;
            }
            if (startPos < 0 || startPos >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos > last) {
                startPos = last;
            }
            ListAdapter adapter2 = getAdapter();
            for (int pos2 = startPos; pos2 >= firstPosition; pos2--) {
                if (adapter2.isEnabled(pos2) && getChildAt(pos2 - firstPosition).getVisibility() == 0) {
                    return pos2;
                }
            }
        }
        return -1;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int direction) {
        View oldFocus;
        int selectablePosition;
        int ySearchPoint;
        int ySearchPoint2;
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            boolean topFadingEdgeShowing = true;
            if (direction == 130) {
                if (this.mFirstPosition <= 0) {
                    topFadingEdgeShowing = false;
                }
                int listTop = this.mListPadding.top + (topFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView == null || selectedView.getTop() <= listTop) {
                    ySearchPoint2 = listTop;
                } else {
                    ySearchPoint2 = selectedView.getTop();
                }
                this.mTempRect.set(0, ySearchPoint2, 0, ySearchPoint2);
            } else {
                if ((this.mFirstPosition + getChildCount()) - 1 >= this.mItemCount) {
                    topFadingEdgeShowing = false;
                }
                int listBottom = (getHeight() - this.mListPadding.bottom) - (topFadingEdgeShowing ? getArrowScrollPreviewLength() : 0);
                if (selectedView == null || selectedView.getBottom() >= listBottom) {
                    ySearchPoint = listBottom;
                } else {
                    ySearchPoint = selectedView.getBottom();
                }
                this.mTempRect.set(0, ySearchPoint, 0, ySearchPoint);
            }
            oldFocus = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, direction);
        } else {
            oldFocus = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), direction);
        }
        if (oldFocus != null) {
            int positionOfNewFocus = positionOfNewFocus(oldFocus);
            if (this.mSelectedPosition != -1 && positionOfNewFocus != this.mSelectedPosition && (selectablePosition = lookForSelectablePositionOnScreen(direction)) != -1 && ((direction == 130 && selectablePosition < positionOfNewFocus) || (direction == 33 && selectablePosition > positionOfNewFocus))) {
                return null;
            }
            int focusScroll = amountToScrollToNewFocus(direction, oldFocus, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (focusScroll < maxScrollAmount) {
                oldFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, focusScroll);
                return this.mArrowScrollFocusResult;
            } else if (distanceToView(oldFocus) < maxScrollAmount) {
                oldFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private int positionOfNewFocus(View newFocus) {
        int numChildren = getChildCount();
        for (int i = 0; i < numChildren; i++) {
            if (isViewAncestorOf(newFocus, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean isViewAncestorOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        if (!(theParent instanceof ViewGroup) || !isViewAncestorOf((View) theParent, parent)) {
            return false;
        }
        return true;
    }

    private int amountToScrollToNewFocus(int direction, View newFocus, int positionOfNewFocus) {
        newFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(newFocus, this.mTempRect);
        if (direction != 33) {
            int listBottom = getHeight() - this.mListPadding.bottom;
            if (this.mTempRect.bottom <= listBottom) {
                return 0;
            }
            int amountToScroll = this.mTempRect.bottom - listBottom;
            if (positionOfNewFocus < this.mItemCount - 1) {
                return amountToScroll + getArrowScrollPreviewLength();
            }
            return amountToScroll;
        } else if (this.mTempRect.top >= this.mListPadding.top) {
            return 0;
        } else {
            int amountToScroll2 = this.mListPadding.top - this.mTempRect.top;
            if (positionOfNewFocus > 0) {
                return amountToScroll2 + getArrowScrollPreviewLength();
            }
            return amountToScroll2;
        }
    }

    private int distanceToView(View descendant) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int listBottom = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        if (this.mTempRect.bottom < this.mListPadding.top) {
            return this.mListPadding.top - this.mTempRect.bottom;
        }
        if (this.mTempRect.top > listBottom) {
            return this.mTempRect.top - listBottom;
        }
        return 0;
    }

    @UnsupportedAppUsage
    private void scrollListItemsBy(int amount) {
        int lastVisiblePosition;
        offsetChildrenTopAndBottom(amount);
        int listBottom = getHeight() - this.mListPadding.bottom;
        int listTop = this.mListPadding.top;
        AbsListView.RecycleBin recycleBin = this.mRecycler;
        if (amount < 0) {
            int numChildren = getChildCount();
            View last = getChildAt(numChildren - 1);
            while (last.getBottom() < listBottom && (this.mFirstPosition + numChildren) - 1 < this.mItemCount - 1) {
                last = addViewBelow(last, lastVisiblePosition);
                numChildren++;
            }
            if (last.getBottom() < listBottom) {
                offsetChildrenTopAndBottom(listBottom - last.getBottom());
            }
            View first = getChildAt(0);
            while (first.getBottom() < listTop) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) first.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(first, this.mFirstPosition);
                }
                detachViewFromParent(first);
                first = getChildAt(0);
                this.mFirstPosition++;
            }
        } else {
            View first2 = getChildAt(0);
            while (first2.getTop() > listTop && this.mFirstPosition > 0) {
                first2 = addViewAbove(first2, this.mFirstPosition);
                this.mFirstPosition--;
            }
            if (first2.getTop() > listTop) {
                offsetChildrenTopAndBottom(listTop - first2.getTop());
            }
            int lastIndex = getChildCount() - 1;
            View last2 = getChildAt(lastIndex);
            while (last2.getTop() > listBottom) {
                if (recycleBin.shouldRecycleViewType(((AbsListView.LayoutParams) last2.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(last2, this.mFirstPosition + lastIndex);
                }
                detachViewFromParent(last2);
                lastIndex--;
                last2 = getChildAt(lastIndex);
            }
        }
        recycleBin.fullyDetachScrapViews();
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
    }

    private View addViewAbove(View theView, int position) {
        int abovePosition = position - 1;
        View view = obtainView(abovePosition, this.mIsScrap);
        setupChild(view, abovePosition, theView.getTop() - this.mDividerHeight, false, this.mListPadding.left, false, this.mIsScrap[0]);
        return view;
    }

    private View addViewBelow(View theView, int position) {
        int belowPosition = position + 1;
        View view = obtainView(belowPosition, this.mIsScrap);
        setupChild(view, belowPosition, theView.getBottom() + this.mDividerHeight, true, this.mListPadding.left, false, this.mIsScrap[0]);
        return view;
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public boolean isOpaque() {
        boolean retValue = (this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque && hasOpaqueScrollbars()) || super.isOpaque();
        if (retValue) {
            int listTop = this.mListPadding != null ? this.mListPadding.top : this.mPaddingTop;
            View first = getChildAt(0);
            if (first == null || first.getTop() > listTop) {
                return false;
            }
            int listBottom = getHeight() - (this.mListPadding != null ? this.mListPadding.bottom : this.mPaddingBottom);
            View last = getChildAt(getChildCount() - 1);
            if (last == null || last.getBottom() < listBottom) {
                return false;
            }
        }
        return retValue;
    }

    public void setCacheColorHint(int color) {
        boolean opaque = (color >>> 24) == 255;
        this.mIsCacheColorOpaque = opaque;
        if (opaque) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new Paint();
            }
            this.mDividerPaint.setColor(color);
        }
        super.setCacheColorHint(color);
    }

    /* access modifiers changed from: package-private */
    public void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect bounds) {
        int height = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(bounds);
        if (bounds.bottom - bounds.top < height) {
            bounds.top = bounds.bottom - height;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect bounds) {
        int height = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(bounds);
        if (bounds.bottom - bounds.top < height) {
            bounds.bottom = bounds.top + height;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        ListAdapter adapter;
        int itemCount;
        int effectivePaddingTop;
        int effectivePaddingBottom;
        Drawable overscrollHeader;
        boolean footerDividers;
        int start;
        int effectivePaddingTop2;
        Drawable overscrollFooter;
        int first;
        int bottom;
        Drawable overscrollFooter2;
        boolean drawDividers;
        int listBottom;
        boolean drawOverscrollHeader;
        ListAdapter adapter2;
        Paint paint;
        Canvas canvas2 = canvas;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int dividerHeight = this.mDividerHeight;
        Drawable overscrollHeader2 = this.mOverScrollHeader;
        Drawable overscrollFooter3 = this.mOverScrollFooter;
        boolean drawOverscrollHeader2 = overscrollHeader2 != null;
        boolean drawOverscrollFooter = overscrollFooter3 != null;
        boolean drawDividers2 = dividerHeight > 0 && this.mDivider != null;
        if (drawDividers2 || drawOverscrollHeader2 || drawOverscrollFooter) {
            Rect bounds = this.mTempRect;
            bounds.left = this.mPaddingLeft;
            bounds.right = (this.mRight - this.mLeft) - this.mPaddingRight;
            int count = getChildCount();
            int headerCount = getHeaderViewsCount();
            int itemCount2 = this.mItemCount;
            int footerLimit = itemCount2 - this.mFooterViewInfos.size();
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers2 = this.mFooterDividersEnabled;
            int first2 = this.mFirstPosition;
            Drawable overscrollFooter4 = overscrollFooter3;
            boolean z = this.mAreAllItemsSelectable;
            ListAdapter adapter3 = this.mAdapter;
            boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
            if (fillForMissingDividers) {
                itemCount = itemCount2;
                if (this.mDividerPaint != null || !this.mIsCacheColorOpaque) {
                    adapter = adapter3;
                } else {
                    this.mDividerPaint = new Paint();
                    adapter = adapter3;
                    this.mDividerPaint.setColor(getCacheColorHint());
                }
            } else {
                adapter = adapter3;
                itemCount = itemCount2;
            }
            Paint paint2 = this.mDividerPaint;
            if ((this.mGroupFlags & 34) == 34) {
                effectivePaddingTop = this.mListPadding.top;
                effectivePaddingBottom = this.mListPadding.bottom;
            } else {
                effectivePaddingBottom = 0;
                effectivePaddingTop = 0;
            }
            int effectivePaddingTop3 = effectivePaddingTop;
            boolean drawOverscrollFooter2 = drawOverscrollFooter;
            int listBottom2 = ((this.mBottom - this.mTop) - effectivePaddingBottom) + this.mScrollY;
            int i = effectivePaddingBottom;
            if (!this.mStackFromBottom) {
                int scrollY = this.mScrollY;
                if (count <= 0 || scrollY >= 0) {
                    bottom = 0;
                } else if (drawOverscrollHeader2) {
                    bottom = 0;
                    bounds.bottom = 0;
                    bounds.top = scrollY;
                    drawOverscrollHeader(canvas2, overscrollHeader2, bounds);
                } else {
                    bottom = 0;
                    if (drawDividers2) {
                        bounds.bottom = 0;
                        bounds.top = -dividerHeight;
                        drawDivider(canvas2, bounds, -1);
                    }
                }
                int i2 = scrollY;
                int bottom2 = bottom;
                int i3 = 0;
                while (i3 < count) {
                    Drawable overscrollHeader3 = overscrollHeader2;
                    int itemIndex = first2 + i3;
                    boolean isHeader = itemIndex < headerCount;
                    boolean isFooter = itemIndex >= footerLimit;
                    if ((headerDividers || !isHeader) && (footerDividers2 || !isFooter)) {
                        bottom2 = getChildAt(i3).getBottom();
                        drawOverscrollHeader = drawOverscrollHeader2;
                        boolean isLastItem = i3 == count + -1;
                        if (!drawDividers2 || bottom2 >= listBottom2) {
                            drawDividers = drawDividers2;
                            listBottom = listBottom2;
                            adapter2 = adapter;
                            paint = paint2;
                            i3++;
                            paint2 = paint;
                            adapter = adapter2;
                            overscrollHeader2 = overscrollHeader3;
                            drawOverscrollHeader2 = drawOverscrollHeader;
                            listBottom2 = listBottom;
                            drawDividers2 = drawDividers;
                        } else if (!drawOverscrollFooter2 || !isLastItem) {
                            listBottom = listBottom2;
                            int nextIndex = itemIndex + 1;
                            drawDividers = drawDividers2;
                            adapter2 = adapter;
                            if (!adapter2.isEnabled(itemIndex)) {
                            } else if ((headerDividers || (!isHeader && nextIndex >= headerCount)) && (isLastItem || (adapter2.isEnabled(nextIndex) && (footerDividers2 || (!isFooter && nextIndex < footerLimit))))) {
                                bounds.top = bottom2;
                                int i4 = itemIndex;
                                bounds.bottom = bottom2 + dividerHeight;
                                drawDivider(canvas2, bounds, i3);
                                paint = paint2;
                                i3++;
                                paint2 = paint;
                                adapter = adapter2;
                                overscrollHeader2 = overscrollHeader3;
                                drawOverscrollHeader2 = drawOverscrollHeader;
                                listBottom2 = listBottom;
                                drawDividers2 = drawDividers;
                            } else {
                                int i5 = itemIndex;
                            }
                            if (fillForMissingDividers) {
                                bounds.top = bottom2;
                                bounds.bottom = bottom2 + dividerHeight;
                                paint = paint2;
                                canvas2.drawRect(bounds, paint);
                            } else {
                                paint = paint2;
                            }
                            i3++;
                            paint2 = paint;
                            adapter = adapter2;
                            overscrollHeader2 = overscrollHeader3;
                            drawOverscrollHeader2 = drawOverscrollHeader;
                            listBottom2 = listBottom;
                            drawDividers2 = drawDividers;
                        }
                    } else {
                        drawOverscrollHeader = drawOverscrollHeader2;
                    }
                    drawDividers = drawDividers2;
                    listBottom = listBottom2;
                    adapter2 = adapter;
                    paint = paint2;
                    i3++;
                    paint2 = paint;
                    adapter = adapter2;
                    overscrollHeader2 = overscrollHeader3;
                    drawOverscrollHeader2 = drawOverscrollHeader;
                    listBottom2 = listBottom;
                    drawDividers2 = drawDividers;
                }
                Drawable overscrollHeader4 = overscrollHeader2;
                boolean z2 = drawOverscrollHeader2;
                boolean z3 = drawDividers2;
                int i6 = listBottom2;
                ListAdapter listAdapter = adapter;
                Paint paint3 = paint2;
                int overFooterBottom = this.mBottom + this.mScrollY;
                if (!drawOverscrollFooter2) {
                    overscrollFooter2 = overscrollFooter4;
                    int i7 = itemCount;
                } else if (first2 + count != itemCount || overFooterBottom <= bottom2) {
                    overscrollFooter2 = overscrollFooter4;
                } else {
                    bounds.top = bottom2;
                    bounds.bottom = overFooterBottom;
                    overscrollFooter2 = overscrollFooter4;
                    drawOverscrollFooter(canvas2, overscrollFooter2, bounds);
                }
                Drawable drawable = overscrollFooter2;
                Drawable drawable2 = overscrollHeader4;
            } else {
                Drawable overscrollHeader5 = overscrollHeader2;
                boolean drawOverscrollHeader3 = drawOverscrollHeader2;
                boolean drawDividers3 = drawDividers2;
                int listBottom3 = listBottom2;
                Drawable overscrollFooter5 = overscrollFooter4;
                int itemCount3 = itemCount;
                ListAdapter adapter4 = adapter;
                Paint paint4 = paint2;
                int scrollY2 = this.mScrollY;
                if (count <= 0 || !drawOverscrollHeader3) {
                    overscrollHeader = overscrollHeader5;
                } else {
                    bounds.top = scrollY2;
                    bounds.bottom = getChildAt(0).getTop();
                    overscrollHeader = overscrollHeader5;
                    drawOverscrollHeader(canvas2, overscrollHeader, bounds);
                }
                int i8 = drawOverscrollHeader3 ? 1 : 0;
                int start2 = i8;
                while (true) {
                    Drawable overscrollHeader6 = overscrollHeader;
                    int i9 = i8;
                    if (i9 >= count) {
                        break;
                    }
                    int itemCount4 = itemCount3;
                    int itemCount5 = first2 + i9;
                    boolean isHeader2 = itemCount5 < headerCount;
                    boolean isFooter2 = itemCount5 >= footerLimit;
                    if ((headerDividers || !isHeader2) && (footerDividers2 || !isFooter2)) {
                        first = first2;
                        int top = getChildAt(i9).getTop();
                        if (drawDividers3) {
                            overscrollFooter = overscrollFooter5;
                            int effectivePaddingTop4 = effectivePaddingTop3;
                            if (top > effectivePaddingTop4) {
                                effectivePaddingTop2 = effectivePaddingTop4;
                                int effectivePaddingTop5 = start2;
                                boolean isFirstItem = i9 == effectivePaddingTop5;
                                start = effectivePaddingTop5;
                                int start3 = itemCount5 - 1;
                                if (!adapter4.isEnabled(itemCount5)) {
                                    footerDividers = footerDividers2;
                                } else if ((headerDividers || (!isHeader2 && start3 >= headerCount)) && (isFirstItem || (adapter4.isEnabled(start3) && (footerDividers2 || (!isFooter2 && start3 < footerLimit))))) {
                                    footerDividers = footerDividers2;
                                    bounds.top = top - dividerHeight;
                                    bounds.bottom = top;
                                    drawDivider(canvas2, bounds, i9 - 1);
                                } else {
                                    footerDividers = footerDividers2;
                                }
                                if (fillForMissingDividers) {
                                    bounds.top = top - dividerHeight;
                                    bounds.bottom = top;
                                    canvas2.drawRect(bounds, paint4);
                                }
                            } else {
                                footerDividers = footerDividers2;
                                effectivePaddingTop2 = effectivePaddingTop4;
                                start = start2;
                            }
                        } else {
                            footerDividers = footerDividers2;
                            overscrollFooter = overscrollFooter5;
                            effectivePaddingTop2 = effectivePaddingTop3;
                            start = start2;
                        }
                    } else {
                        footerDividers = footerDividers2;
                        first = first2;
                        overscrollFooter = overscrollFooter5;
                        effectivePaddingTop2 = effectivePaddingTop3;
                        start = start2;
                    }
                    i8 = i9 + 1;
                    overscrollHeader = overscrollHeader6;
                    itemCount3 = itemCount4;
                    first2 = first;
                    overscrollFooter5 = overscrollFooter;
                    effectivePaddingTop3 = effectivePaddingTop2;
                    start2 = start;
                    footerDividers2 = footerDividers;
                }
                int i10 = first2;
                Drawable overscrollFooter6 = overscrollFooter5;
                int i11 = itemCount3;
                int i12 = effectivePaddingTop3;
                int i13 = start2;
                if (count <= 0 || scrollY2 <= 0) {
                } else if (drawOverscrollFooter2) {
                    int absListBottom = this.mBottom;
                    bounds.top = absListBottom;
                    bounds.bottom = absListBottom + scrollY2;
                    drawOverscrollFooter(canvas2, overscrollFooter6, bounds);
                } else {
                    if (drawDividers3) {
                        int listBottom4 = listBottom3;
                        bounds.top = listBottom4;
                        bounds.bottom = listBottom4 + dividerHeight;
                        drawDivider(canvas2, bounds, -1);
                    }
                }
            }
        } else {
            Drawable drawable3 = overscrollHeader2;
            Drawable drawable4 = overscrollFooter3;
            boolean z4 = drawOverscrollHeader2;
            boolean z5 = drawOverscrollFooter;
            boolean z6 = drawDividers2;
        }
        super.dispatchDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean more = super.drawChild(canvas, child, drawingTime);
        if (this.mCachingActive && child.mCachingFailed) {
            this.mCachingActive = false;
        }
        return more;
    }

    /* access modifiers changed from: package-private */
    public void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        Drawable divider = this.mDivider;
        divider.setBounds(bounds);
        divider.draw(canvas);
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

    public void setDivider(Drawable divider) {
        boolean z = false;
        if (divider != null) {
            this.mDividerHeight = divider.getIntrinsicHeight();
        } else {
            this.mDividerHeight = 0;
        }
        this.mDivider = divider;
        if (divider == null || divider.getOpacity() == -1) {
            z = true;
        }
        this.mDividerIsOpaque = z;
        requestLayout();
        invalidate();
    }

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    public void setDividerHeight(int height) {
        this.mDividerHeight = height;
        requestLayout();
        invalidate();
    }

    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        this.mHeaderDividersEnabled = headerDividersEnabled;
        invalidate();
    }

    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        this.mFooterDividersEnabled = footerDividersEnabled;
        invalidate();
    }

    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    public void setOverscrollHeader(Drawable header) {
        this.mOverScrollHeader = header;
        if (this.mScrollY < 0) {
            invalidate();
        }
    }

    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    public void setOverscrollFooter(Drawable footer) {
        this.mOverScrollFooter = footer;
        invalidate();
    }

    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = -1;
        int closestChildTop = 0;
        if (!(adapter == null || !gainFocus || previouslyFocusedRect == null)) {
            previouslyFocusedRect.offset(this.mScrollX, this.mScrollY);
            if (adapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            int firstPosition = this.mFirstPosition;
            for (int i = 0; i < childCount; i++) {
                if (adapter.isEnabled(firstPosition + i)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closetChildIndex = i;
                        closestChildTop = other.getTop();
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromTop(this.mFirstPosition + closetChildIndex, closestChildTop);
        } else {
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                addHeaderView(getChildAt(i));
            }
            removeAllViews();
        }
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewTraversal(int id) {
        View v = super.findViewTraversal(id);
        if (v == null) {
            View v2 = findViewInHeadersOrFooters(this.mHeaderViewInfos, id);
            if (v2 != null) {
                return v2;
            }
            v = findViewInHeadersOrFooters(this.mFooterViewInfos, id);
            if (v != null) {
                return v;
            }
        }
        return v;
    }

    /* access modifiers changed from: package-private */
    public View findViewInHeadersOrFooters(ArrayList<FixedViewInfo> where, int id) {
        View v;
        if (where == null) {
            return null;
        }
        int len = where.size();
        for (int i = 0; i < len; i++) {
            View v2 = where.get(i).view;
            if (!v2.isRootNamespace() && (v = v2.findViewById(id)) != null) {
                return v;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewWithTagTraversal(Object tag) {
        View v = super.findViewWithTagTraversal(tag);
        if (v == null) {
            View v2 = findViewWithTagInHeadersOrFooters(this.mHeaderViewInfos, tag);
            if (v2 != null) {
                return v2;
            }
            v = findViewWithTagInHeadersOrFooters(this.mFooterViewInfos, tag);
            if (v != null) {
                return v;
            }
        }
        return v;
    }

    /* access modifiers changed from: package-private */
    public View findViewWithTagInHeadersOrFooters(ArrayList<FixedViewInfo> where, Object tag) {
        View v;
        if (where == null) {
            return null;
        }
        int len = where.size();
        for (int i = 0; i < len; i++) {
            View v2 = where.get(i).view;
            if (!v2.isRootNamespace() && (v = v2.findViewWithTag(tag)) != null) {
                return v;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View childToSkip) {
        View v = super.findViewByPredicateTraversal(predicate, childToSkip);
        if (v == null) {
            View v2 = findViewByPredicateInHeadersOrFooters(this.mHeaderViewInfos, predicate, childToSkip);
            if (v2 != null) {
                return v2;
            }
            v = findViewByPredicateInHeadersOrFooters(this.mFooterViewInfos, predicate, childToSkip);
            if (v != null) {
                return v;
            }
        }
        return v;
    }

    /* access modifiers changed from: package-private */
    public View findViewByPredicateInHeadersOrFooters(ArrayList<FixedViewInfo> where, Predicate<View> predicate, View childToSkip) {
        View v;
        if (where == null) {
            return null;
        }
        int len = where.size();
        for (int i = 0; i < len; i++) {
            View v2 = where.get(i).view;
            if (v2 != childToSkip && !v2.isRootNamespace() && (v = v2.findViewByPredicate(predicate)) != null) {
                return v;
            }
        }
        return null;
    }

    @Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return getCheckedItemIds();
        }
        if (this.mChoiceMode == 0 || this.mCheckStates == null || this.mAdapter == null) {
            return new long[0];
        }
        SparseBooleanArray states = this.mCheckStates;
        int count = states.size();
        long[] ids = new long[count];
        ListAdapter adapter = this.mAdapter;
        int checkedCount = 0;
        for (int i = 0; i < count; i++) {
            if (states.valueAt(i)) {
                ids[checkedCount] = adapter.getItemId(states.keyAt(i));
                checkedCount++;
            }
        }
        if (checkedCount == count) {
            return ids;
        }
        long[] result = new long[checkedCount];
        System.arraycopy(ids, 0, result, 0, checkedCount);
        return result;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int getHeightForPosition(int position) {
        int height = super.getHeightForPosition(position);
        if (shouldAdjustHeightForDivider(position)) {
            return this.mDividerHeight + height;
        }
        return height;
    }

    private boolean shouldAdjustHeightForDivider(int itemIndex) {
        int i = itemIndex;
        int dividerHeight = this.mDividerHeight;
        Drawable overscrollHeader = this.mOverScrollHeader;
        Drawable overscrollFooter = this.mOverScrollFooter;
        boolean drawOverscrollHeader = overscrollHeader != null;
        boolean drawOverscrollFooter = overscrollFooter != null;
        if (dividerHeight > 0 && this.mDivider != null) {
            boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
            int itemCount = this.mItemCount;
            int headerCount = getHeaderViewsCount();
            int footerLimit = itemCount - this.mFooterViewInfos.size();
            boolean isHeader = i < headerCount;
            boolean isFooter = i >= footerLimit;
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers = this.mFooterDividersEnabled;
            if ((headerDividers || !isHeader) && (footerDividers || !isFooter)) {
                int i2 = dividerHeight;
                ListAdapter adapter = this.mAdapter;
                Drawable drawable = overscrollHeader;
                if (!this.mStackFromBottom) {
                    boolean isLastItem = i == itemCount + -1;
                    if (drawOverscrollFooter && isLastItem) {
                        return false;
                    }
                    int nextIndex = i + 1;
                    if (adapter.isEnabled(i) && (headerDividers || (!isHeader && nextIndex >= headerCount))) {
                        if (isLastItem) {
                            return true;
                        }
                        if (adapter.isEnabled(nextIndex)) {
                            if (footerDividers) {
                                return true;
                            }
                            if (!isFooter && nextIndex < footerLimit) {
                                return true;
                            }
                        }
                    }
                    if (fillForMissingDividers) {
                        return true;
                    }
                    return false;
                }
                int start = drawOverscrollHeader ? 1 : 0;
                boolean isFirstItem = i == start;
                if (isFirstItem) {
                    return false;
                }
                int i3 = start;
                int start2 = i - 1;
                if (adapter.isEnabled(i) && (headerDividers || (!isHeader && start2 >= headerCount))) {
                    if (isFirstItem) {
                        return true;
                    }
                    if (adapter.isEnabled(start2)) {
                        if (footerDividers) {
                            return true;
                        }
                        if (!isFooter && start2 < footerLimit) {
                            return true;
                        }
                    }
                }
                if (fillForMissingDividers) {
                    return true;
                }
                return false;
            }
            int i4 = dividerHeight;
            Drawable drawable2 = overscrollHeader;
            return false;
        }
        Drawable drawable3 = overscrollHeader;
        return false;
    }

    public CharSequence getAccessibilityClassName() {
        return ListView.class.getName();
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        int rowsCount = getCount();
        info.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(rowsCount, 1, false, getSelectionModeForAccessibility()));
        if (rowsCount > 0) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        }
    }

    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        if (action != 16908343) {
            return false;
        }
        int row = arguments.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
        int position = Math.min(row, getCount() - 1);
        if (row < 0) {
            return false;
        }
        smoothScrollToPosition(position);
        return true;
    }

    public void onInitializeAccessibilityNodeInfoForItem(View view, int position, AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoForItem(view, position, info);
        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        info.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(position, 1, 0, 1, lp != null && lp.viewType == -2, isItemChecked(position)));
    }

    /* access modifiers changed from: protected */
    public void encodeProperties(ViewHierarchyEncoder encoder) {
        super.encodeProperties(encoder);
        encoder.addProperty("recycleOnMeasure", recycleOnMeasure());
    }

    /* access modifiers changed from: protected */
    public HeaderViewListAdapter wrapHeaderListAdapterInternal(ArrayList<FixedViewInfo> headerViewInfos, ArrayList<FixedViewInfo> footerViewInfos, ListAdapter adapter) {
        return new HeaderViewListAdapter(headerViewInfos, footerViewInfos, adapter);
    }

    /* access modifiers changed from: protected */
    public void wrapHeaderListAdapterInternal() {
        this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
    }

    /* access modifiers changed from: protected */
    public void dispatchDataSetObserverOnChangedInternal() {
        if (this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }
}
