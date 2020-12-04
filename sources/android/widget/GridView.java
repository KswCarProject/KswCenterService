package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.RemotableViewMethod;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.GridLayoutAnimationController;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsListView;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.IntFunction;

@RemoteViews.RemoteView
public class GridView extends AbsListView {
    public static final int AUTO_FIT = -1;
    public static final int NO_STRETCH = 0;
    public static final int STRETCH_COLUMN_WIDTH = 2;
    public static final int STRETCH_SPACING = 1;
    public static final int STRETCH_SPACING_UNIFORM = 3;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 117521079)
    private int mColumnWidth;
    private int mGravity;
    @UnsupportedAppUsage
    private int mHorizontalSpacing;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 117521080)
    private int mNumColumns;
    private View mReferenceView;
    private View mReferenceViewInSelectedRow;
    @UnsupportedAppUsage
    private int mRequestedColumnWidth;
    @UnsupportedAppUsage
    private int mRequestedHorizontalSpacing;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769395)
    private int mRequestedNumColumns;
    private int mStretchMode;
    private final Rect mTempRect;
    @UnsupportedAppUsage
    private int mVerticalSpacing;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StretchMode {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<GridView> {
        private int mColumnWidthId;
        private int mGravityId;
        private int mHorizontalSpacingId;
        private int mNumColumnsId;
        private boolean mPropertiesMapped = false;
        private int mStretchModeId;
        private int mVerticalSpacingId;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mColumnWidthId = propertyMapper.mapInt("columnWidth", 16843031);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHorizontalSpacingId = propertyMapper.mapInt("horizontalSpacing", 16843028);
            this.mNumColumnsId = propertyMapper.mapInt("numColumns", 16843032);
            SparseArray<String> stretchModeEnumMapping = new SparseArray<>();
            stretchModeEnumMapping.put(0, "none");
            stretchModeEnumMapping.put(1, "spacingWidth");
            stretchModeEnumMapping.put(2, "columnWidth");
            stretchModeEnumMapping.put(3, "spacingWidthUniform");
            Objects.requireNonNull(stretchModeEnumMapping);
            this.mStretchModeId = propertyMapper.mapIntEnum("stretchMode", 16843030, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mVerticalSpacingId = propertyMapper.mapInt("verticalSpacing", 16843029);
            this.mPropertiesMapped = true;
        }

        public void readProperties(GridView node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readInt(this.mColumnWidthId, node.getColumnWidth());
                propertyReader.readGravity(this.mGravityId, node.getGravity());
                propertyReader.readInt(this.mHorizontalSpacingId, node.getHorizontalSpacing());
                propertyReader.readInt(this.mNumColumnsId, node.getNumColumns());
                propertyReader.readIntEnum(this.mStretchModeId, node.getStretchMode());
                propertyReader.readInt(this.mVerticalSpacingId, node.getVerticalSpacing());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public GridView(Context context) {
        this(context, (AttributeSet) null);
    }

    public GridView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842865);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mNumColumns = -1;
        this.mHorizontalSpacing = 0;
        this.mVerticalSpacing = 0;
        this.mStretchMode = 2;
        this.mReferenceView = null;
        this.mReferenceViewInSelectedRow = null;
        this.mGravity = 8388611;
        this.mTempRect = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridView, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.GridView, attrs, a, defStyleAttr, defStyleRes);
        setHorizontalSpacing(a.getDimensionPixelOffset(1, 0));
        setVerticalSpacing(a.getDimensionPixelOffset(2, 0));
        int index = a.getInt(3, 2);
        if (index >= 0) {
            setStretchMode(index);
        }
        int columnWidth = a.getDimensionPixelOffset(4, -1);
        if (columnWidth > 0) {
            setColumnWidth(columnWidth);
        }
        setNumColumns(a.getInt(5, 1));
        int index2 = a.getInt(0, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        a.recycle();
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
        this.mAdapter = adapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(adapter);
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.mDataChanged = true;
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
            checkSelectionChanged();
        } else {
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public int lookForSelectablePosition(int position, boolean lookDown) {
        if (this.mAdapter == null || isInTouchMode() || position < 0 || position >= this.mItemCount) {
            return -1;
        }
        return position;
    }

    /* access modifiers changed from: package-private */
    public void fillGap(boolean down) {
        int position;
        int numColumns = this.mNumColumns;
        int verticalSpacing = this.mVerticalSpacing;
        int count = getChildCount();
        if (down) {
            int paddingTop = 0;
            if ((this.mGroupFlags & 34) == 34) {
                paddingTop = getListPaddingTop();
            }
            int startOffset = count > 0 ? getChildAt(count - 1).getBottom() + verticalSpacing : paddingTop;
            int position2 = this.mFirstPosition + count;
            if (this.mStackFromBottom) {
                position2 += numColumns - 1;
            }
            fillDown(position2, startOffset);
            correctTooHigh(numColumns, verticalSpacing, getChildCount());
            return;
        }
        int paddingBottom = 0;
        if ((this.mGroupFlags & 34) == 34) {
            paddingBottom = getListPaddingBottom();
        }
        int startOffset2 = count > 0 ? getChildAt(0).getTop() - verticalSpacing : getHeight() - paddingBottom;
        int position3 = this.mFirstPosition;
        if (!this.mStackFromBottom) {
            position = position3 - numColumns;
        } else {
            position = position3 - 1;
        }
        fillUp(position, startOffset2);
        correctTooLow(numColumns, verticalSpacing, getChildCount());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private View fillDown(int pos, int nextTop) {
        View selectedView = null;
        int end = this.mBottom - this.mTop;
        if ((this.mGroupFlags & 34) == 34) {
            end -= this.mListPadding.bottom;
        }
        while (nextTop < end && pos < this.mItemCount) {
            View temp = makeRow(pos, nextTop, true);
            if (temp != null) {
                selectedView = temp;
            }
            nextTop = this.mReferenceView.getBottom() + this.mVerticalSpacing;
            pos += this.mNumColumns;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View makeRow(int startPos, int y, boolean flow) {
        int nextLeft;
        int last;
        int startPos2;
        int columnWidth = this.mColumnWidth;
        int horizontalSpacing = this.mHorizontalSpacing;
        boolean isLayoutRtl = isLayoutRtl();
        boolean z = false;
        if (isLayoutRtl) {
            nextLeft = ((getWidth() - this.mListPadding.right) - columnWidth) - (this.mStretchMode == 3 ? horizontalSpacing : 0);
        } else {
            nextLeft = this.mListPadding.left + (this.mStretchMode == 3 ? horizontalSpacing : 0);
        }
        int nextLeft2 = nextLeft;
        if (this.mStackFromBottom == 0) {
            last = Math.min(startPos + this.mNumColumns, this.mItemCount);
            startPos2 = startPos;
        } else {
            last = startPos + 1;
            int startPos3 = Math.max(0, (startPos - this.mNumColumns) + 1);
            if (last - startPos3 < this.mNumColumns) {
                nextLeft2 += (isLayoutRtl ? -1 : 1) * (this.mNumColumns - (last - startPos3)) * (columnWidth + horizontalSpacing);
            }
            startPos2 = startPos3;
        }
        int last2 = last;
        boolean hasFocus = shouldShowSelector();
        boolean inClick = touchModeDrawsInPressedState();
        int selectedPosition = this.mSelectedPosition;
        int nextChildDir = isLayoutRtl ? -1 : 1;
        View selectedView = null;
        int nextLeft3 = nextLeft2;
        View child = null;
        int pos = startPos2;
        while (true) {
            int pos2 = pos;
            if (pos2 >= last2) {
                break;
            }
            boolean selected = pos2 == selectedPosition ? true : z;
            int pos3 = pos2;
            View view = child;
            int selectedPosition2 = selectedPosition;
            child = makeAndAddView(pos2, y, flow, nextLeft3, selected, flow ? -1 : pos2 - startPos2);
            nextLeft3 += nextChildDir * columnWidth;
            if (pos3 < last2 - 1) {
                nextLeft3 += nextChildDir * horizontalSpacing;
            }
            if (selected && (hasFocus || inClick)) {
                selectedView = child;
            }
            pos = pos3 + 1;
            selectedPosition = selectedPosition2;
            z = false;
        }
        int i = selectedPosition;
        this.mReferenceView = child;
        if (selectedView != null) {
            this.mReferenceViewInSelectedRow = this.mReferenceView;
        }
        return selectedView;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    private View fillUp(int pos, int nextBottom) {
        View selectedView = null;
        int end = 0;
        if ((this.mGroupFlags & 34) == 34) {
            end = this.mListPadding.top;
        }
        while (nextBottom > end && pos >= 0) {
            View temp = makeRow(pos, nextBottom, false);
            if (temp != null) {
                selectedView = temp;
            }
            nextBottom = this.mReferenceView.getTop() - this.mVerticalSpacing;
            this.mFirstPosition = pos;
            pos -= this.mNumColumns;
        }
        if (this.mStackFromBottom) {
            this.mFirstPosition = Math.max(0, pos + 1);
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillFromTop(int nextTop) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        this.mFirstPosition -= this.mFirstPosition % this.mNumColumns;
        return fillDown(this.mFirstPosition, nextTop);
    }

    private View fillFromBottom(int lastPosition, int nextBottom) {
        int invertedPosition = (this.mItemCount - 1) - Math.min(Math.max(lastPosition, this.mSelectedPosition), this.mItemCount - 1);
        return fillUp((this.mItemCount - 1) - (invertedPosition - (invertedPosition % this.mNumColumns)), nextBottom);
    }

    private View fillSelection(int childrenTop, int childrenBottom) {
        int invertedSelection;
        int selectedPosition = reconcileSelectedPosition();
        int numColumns = this.mNumColumns;
        int verticalSpacing = this.mVerticalSpacing;
        int rowEnd = -1;
        if (!this.mStackFromBottom) {
            invertedSelection = selectedPosition - (selectedPosition % numColumns);
        } else {
            int invertedSelection2 = (this.mItemCount - 1) - selectedPosition;
            rowEnd = (this.mItemCount - 1) - (invertedSelection2 - (invertedSelection2 % numColumns));
            invertedSelection = Math.max(0, (rowEnd - numColumns) + 1);
        }
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        View sel = makeRow(this.mStackFromBottom ? rowEnd : invertedSelection, getTopSelectionPixel(childrenTop, fadingEdgeLength, invertedSelection), true);
        this.mFirstPosition = invertedSelection;
        View referenceView = this.mReferenceView;
        if (!this.mStackFromBottom) {
            fillDown(invertedSelection + numColumns, referenceView.getBottom() + verticalSpacing);
            pinToBottom(childrenBottom);
            fillUp(invertedSelection - numColumns, referenceView.getTop() - verticalSpacing);
            adjustViewsUpOrDown();
        } else {
            offsetChildrenTopAndBottom(getBottomSelectionPixel(childrenBottom, fadingEdgeLength, numColumns, invertedSelection) - referenceView.getBottom());
            fillUp(invertedSelection - 1, referenceView.getTop() - verticalSpacing);
            pinToTop(childrenTop);
            fillDown(rowEnd + numColumns, referenceView.getBottom() + verticalSpacing);
            adjustViewsUpOrDown();
        }
        return sel;
    }

    private void pinToTop(int childrenTop) {
        int offset;
        if (this.mFirstPosition == 0 && (offset = childrenTop - getChildAt(0).getTop()) < 0) {
            offsetChildrenTopAndBottom(offset);
        }
    }

    private void pinToBottom(int childrenBottom) {
        int offset;
        int count = getChildCount();
        if (this.mFirstPosition + count == this.mItemCount && (offset = childrenBottom - getChildAt(count - 1).getBottom()) > 0) {
            offsetChildrenTopAndBottom(offset);
        }
    }

    /* access modifiers changed from: package-private */
    public int findMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return -1;
        }
        int numColumns = this.mNumColumns;
        if (!this.mStackFromBottom) {
            for (int i = 0; i < childCount; i += numColumns) {
                if (y <= getChildAt(i).getBottom()) {
                    return this.mFirstPosition + i;
                }
            }
            return -1;
        }
        for (int i2 = childCount - 1; i2 >= 0; i2 -= numColumns) {
            if (y >= getChildAt(i2).getTop()) {
                return this.mFirstPosition + i2;
            }
        }
        return -1;
    }

    private View fillSpecific(int position, int top) {
        int invertedSelection;
        View below;
        View above;
        int numColumns = this.mNumColumns;
        int motionRowEnd = -1;
        if (!this.mStackFromBottom) {
            invertedSelection = position - (position % numColumns);
        } else {
            int invertedSelection2 = (this.mItemCount - 1) - position;
            motionRowEnd = (this.mItemCount - 1) - (invertedSelection2 - (invertedSelection2 % numColumns));
            invertedSelection = Math.max(0, (motionRowEnd - numColumns) + 1);
        }
        View temp = makeRow(this.mStackFromBottom ? motionRowEnd : invertedSelection, top, true);
        this.mFirstPosition = invertedSelection;
        View referenceView = this.mReferenceView;
        if (referenceView == null) {
            return null;
        }
        int verticalSpacing = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            above = fillUp(invertedSelection - numColumns, referenceView.getTop() - verticalSpacing);
            adjustViewsUpOrDown();
            below = fillDown(invertedSelection + numColumns, referenceView.getBottom() + verticalSpacing);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(numColumns, verticalSpacing, childCount);
            }
        } else {
            below = fillDown(motionRowEnd + numColumns, referenceView.getBottom() + verticalSpacing);
            adjustViewsUpOrDown();
            above = fillUp(invertedSelection - 1, referenceView.getTop() - verticalSpacing);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(numColumns, verticalSpacing, childCount2);
            }
        }
        if (temp != null) {
            return temp;
        }
        if (above != null) {
            return above;
        }
        return below;
    }

    private void correctTooHigh(int numColumns, int verticalSpacing, int childCount) {
        int i = 1;
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
                    int i2 = this.mFirstPosition;
                    if (!this.mStackFromBottom) {
                        i = numColumns;
                    }
                    fillUp(i2 - i, firstChild.getTop() - verticalSpacing);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int numColumns, int verticalSpacing, int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            int firstTop = getChildAt(0).getTop();
            int start = this.mListPadding.top;
            int end = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int topOffset = firstTop - start;
            View lastChild = getChildAt(childCount - 1);
            int lastBottom = lastChild.getBottom();
            int i = 1;
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
                    if (this.mStackFromBottom) {
                        i = numColumns;
                    }
                    fillDown(i + lastPosition, lastChild.getBottom() + verticalSpacing);
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private View fillFromSelection(int selectedTop, int childrenTop, int childrenBottom) {
        int invertedSelection;
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int numColumns = this.mNumColumns;
        int verticalSpacing = this.mVerticalSpacing;
        int rowEnd = -1;
        if (!this.mStackFromBottom) {
            invertedSelection = selectedPosition - (selectedPosition % numColumns);
        } else {
            int invertedSelection2 = (this.mItemCount - 1) - selectedPosition;
            rowEnd = (this.mItemCount - 1) - (invertedSelection2 - (invertedSelection2 % numColumns));
            invertedSelection = Math.max(0, (rowEnd - numColumns) + 1);
        }
        int topSelectionPixel = getTopSelectionPixel(childrenTop, fadingEdgeLength, invertedSelection);
        int bottomSelectionPixel = getBottomSelectionPixel(childrenBottom, fadingEdgeLength, numColumns, invertedSelection);
        View sel = makeRow(this.mStackFromBottom ? rowEnd : invertedSelection, selectedTop, true);
        this.mFirstPosition = invertedSelection;
        View referenceView = this.mReferenceView;
        adjustForTopFadingEdge(referenceView, topSelectionPixel, bottomSelectionPixel);
        adjustForBottomFadingEdge(referenceView, topSelectionPixel, bottomSelectionPixel);
        if (!this.mStackFromBottom) {
            fillUp(invertedSelection - numColumns, referenceView.getTop() - verticalSpacing);
            adjustViewsUpOrDown();
            fillDown(invertedSelection + numColumns, referenceView.getBottom() + verticalSpacing);
        } else {
            fillDown(rowEnd + numColumns, referenceView.getBottom() + verticalSpacing);
            adjustViewsUpOrDown();
            fillUp(invertedSelection - 1, referenceView.getTop() - verticalSpacing);
        }
        return sel;
    }

    private int getBottomSelectionPixel(int childrenBottom, int fadingEdgeLength, int numColumns, int rowStart) {
        int bottomSelectionPixel = childrenBottom;
        if ((rowStart + numColumns) - 1 < this.mItemCount - 1) {
            return bottomSelectionPixel - fadingEdgeLength;
        }
        return bottomSelectionPixel;
    }

    private int getTopSelectionPixel(int childrenTop, int fadingEdgeLength, int rowStart) {
        int topSelectionPixel = childrenTop;
        if (rowStart > 0) {
            return topSelectionPixel + fadingEdgeLength;
        }
        return topSelectionPixel;
    }

    private void adjustForBottomFadingEdge(View childInSelectedRow, int topSelectionPixel, int bottomSelectionPixel) {
        if (childInSelectedRow.getBottom() > bottomSelectionPixel) {
            offsetChildrenTopAndBottom(-Math.min(childInSelectedRow.getTop() - topSelectionPixel, childInSelectedRow.getBottom() - bottomSelectionPixel));
        }
    }

    private void adjustForTopFadingEdge(View childInSelectedRow, int topSelectionPixel, int bottomSelectionPixel) {
        if (childInSelectedRow.getTop() < topSelectionPixel) {
            offsetChildrenTopAndBottom(Math.min(topSelectionPixel - childInSelectedRow.getTop(), bottomSelectionPixel - childInSelectedRow.getBottom()));
        }
    }

    @RemotableViewMethod
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }

    @RemotableViewMethod
    public void smoothScrollByOffset(int offset) {
        super.smoothScrollByOffset(offset);
    }

    private View moveSelection(int delta, int childrenTop, int childrenBottom) {
        int rowStart;
        int oldRowStart;
        View referenceView;
        View sel;
        int fadingEdgeLength = getVerticalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int numColumns = this.mNumColumns;
        int verticalSpacing = this.mVerticalSpacing;
        int rowEnd = -1;
        if (!this.mStackFromBottom) {
            oldRowStart = (selectedPosition - delta) - ((selectedPosition - delta) % numColumns);
            rowStart = selectedPosition - (selectedPosition % numColumns);
        } else {
            int invertedSelection = (this.mItemCount - 1) - selectedPosition;
            rowEnd = (this.mItemCount - 1) - (invertedSelection - (invertedSelection % numColumns));
            rowStart = Math.max(0, (rowEnd - numColumns) + 1);
            int invertedSelection2 = (this.mItemCount - 1) - (selectedPosition - delta);
            oldRowStart = Math.max(0, (((this.mItemCount - 1) - (invertedSelection2 - (invertedSelection2 % numColumns))) - numColumns) + 1);
        }
        int invertedSelection3 = rowStart - oldRowStart;
        int topSelectionPixel = getTopSelectionPixel(childrenTop, fadingEdgeLength, rowStart);
        int bottomSelectionPixel = getBottomSelectionPixel(childrenBottom, fadingEdgeLength, numColumns, rowStart);
        this.mFirstPosition = rowStart;
        if (invertedSelection3 > 0) {
            int i = fadingEdgeLength;
            View sel2 = makeRow(this.mStackFromBottom ? rowEnd : rowStart, (this.mReferenceViewInSelectedRow == null ? 0 : this.mReferenceViewInSelectedRow.getBottom()) + verticalSpacing, true);
            View referenceView2 = this.mReferenceView;
            adjustForBottomFadingEdge(referenceView2, topSelectionPixel, bottomSelectionPixel);
            View view = referenceView2;
            referenceView = sel2;
            sel = view;
        } else {
            if (invertedSelection3 < 0) {
                referenceView = makeRow(this.mStackFromBottom ? rowEnd : rowStart, (this.mReferenceViewInSelectedRow == null ? 0 : this.mReferenceViewInSelectedRow.getTop()) - verticalSpacing, false);
                View referenceView3 = this.mReferenceView;
                adjustForTopFadingEdge(referenceView3, topSelectionPixel, bottomSelectionPixel);
                sel = referenceView3;
            } else {
                referenceView = makeRow(this.mStackFromBottom ? rowEnd : rowStart, this.mReferenceViewInSelectedRow == null ? 0 : this.mReferenceViewInSelectedRow.getTop(), true);
                sel = this.mReferenceView;
            }
        }
        if (!this.mStackFromBottom) {
            fillUp(rowStart - numColumns, sel.getTop() - verticalSpacing);
            adjustViewsUpOrDown();
            fillDown(rowStart + numColumns, sel.getBottom() + verticalSpacing);
        } else {
            fillDown(rowEnd + numColumns, sel.getBottom() + verticalSpacing);
            adjustViewsUpOrDown();
            fillUp(rowStart - 1, sel.getTop() - verticalSpacing);
        }
        return referenceView;
    }

    @UnsupportedAppUsage
    private boolean determineColumns(int availableSpace) {
        int requestedHorizontalSpacing = this.mRequestedHorizontalSpacing;
        int stretchMode = this.mStretchMode;
        int requestedColumnWidth = this.mRequestedColumnWidth;
        boolean didNotInitiallyFit = false;
        if (this.mRequestedNumColumns != -1) {
            this.mNumColumns = this.mRequestedNumColumns;
        } else if (requestedColumnWidth > 0) {
            this.mNumColumns = (availableSpace + requestedHorizontalSpacing) / (requestedColumnWidth + requestedHorizontalSpacing);
        } else {
            this.mNumColumns = 2;
        }
        if (this.mNumColumns <= 0) {
            this.mNumColumns = 1;
        }
        if (stretchMode != 0) {
            int spaceLeftOver = (availableSpace - (this.mNumColumns * requestedColumnWidth)) - ((this.mNumColumns - 1) * requestedHorizontalSpacing);
            if (spaceLeftOver < 0) {
                didNotInitiallyFit = true;
            }
            switch (stretchMode) {
                case 1:
                    this.mColumnWidth = requestedColumnWidth;
                    if (this.mNumColumns <= 1) {
                        this.mHorizontalSpacing = requestedHorizontalSpacing + spaceLeftOver;
                        break;
                    } else {
                        this.mHorizontalSpacing = (spaceLeftOver / (this.mNumColumns - 1)) + requestedHorizontalSpacing;
                        break;
                    }
                case 2:
                    this.mColumnWidth = (spaceLeftOver / this.mNumColumns) + requestedColumnWidth;
                    this.mHorizontalSpacing = requestedHorizontalSpacing;
                    break;
                case 3:
                    this.mColumnWidth = requestedColumnWidth;
                    if (this.mNumColumns <= 1) {
                        this.mHorizontalSpacing = requestedHorizontalSpacing + spaceLeftOver;
                        break;
                    } else {
                        this.mHorizontalSpacing = (spaceLeftOver / (this.mNumColumns + 1)) + requestedHorizontalSpacing;
                        break;
                    }
            }
        } else {
            this.mColumnWidth = requestedColumnWidth;
            this.mHorizontalSpacing = requestedHorizontalSpacing;
        }
        return didNotInitiallyFit;
    }

    /* JADX WARNING: type inference failed for: r15v11, types: [android.view.ViewGroup$LayoutParams] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r18, int r19) {
        /*
            r17 = this;
            r0 = r17
            super.onMeasure(r18, r19)
            int r1 = android.view.View.MeasureSpec.getMode(r18)
            int r2 = android.view.View.MeasureSpec.getMode(r19)
            int r3 = android.view.View.MeasureSpec.getSize(r18)
            int r4 = android.view.View.MeasureSpec.getSize(r19)
            if (r1 != 0) goto L_0x0036
            int r5 = r0.mColumnWidth
            if (r5 <= 0) goto L_0x0028
            int r5 = r0.mColumnWidth
            android.graphics.Rect r6 = r0.mListPadding
            int r6 = r6.left
            int r5 = r5 + r6
            android.graphics.Rect r6 = r0.mListPadding
            int r6 = r6.right
            int r5 = r5 + r6
            goto L_0x0031
        L_0x0028:
            android.graphics.Rect r5 = r0.mListPadding
            int r5 = r5.left
            android.graphics.Rect r6 = r0.mListPadding
            int r6 = r6.right
            int r5 = r5 + r6
        L_0x0031:
            int r3 = r17.getVerticalScrollbarWidth()
            int r3 = r3 + r5
        L_0x0036:
            android.graphics.Rect r5 = r0.mListPadding
            int r5 = r5.left
            int r5 = r3 - r5
            android.graphics.Rect r6 = r0.mListPadding
            int r6 = r6.right
            int r5 = r5 - r6
            boolean r6 = r0.determineColumns(r5)
            r7 = 0
            r8 = 0
            android.widget.ListAdapter r9 = r0.mAdapter
            r10 = 0
            if (r9 != 0) goto L_0x004e
            r9 = r10
            goto L_0x0054
        L_0x004e:
            android.widget.ListAdapter r9 = r0.mAdapter
            int r9 = r9.getCount()
        L_0x0054:
            r0.mItemCount = r9
            int r9 = r0.mItemCount
            r12 = 1
            if (r9 <= 0) goto L_0x00c1
            boolean[] r13 = r0.mIsScrap
            android.view.View r13 = r0.obtainView(r10, r13)
            android.view.ViewGroup$LayoutParams r14 = r13.getLayoutParams()
            android.widget.AbsListView$LayoutParams r14 = (android.widget.AbsListView.LayoutParams) r14
            if (r14 != 0) goto L_0x0073
            android.view.ViewGroup$LayoutParams r15 = r17.generateDefaultLayoutParams()
            r14 = r15
            android.widget.AbsListView$LayoutParams r14 = (android.widget.AbsListView.LayoutParams) r14
            r13.setLayoutParams(r14)
        L_0x0073:
            android.widget.ListAdapter r15 = r0.mAdapter
            int r15 = r15.getItemViewType(r10)
            r14.viewType = r15
            android.widget.ListAdapter r15 = r0.mAdapter
            boolean r15 = r15.isEnabled(r10)
            r14.isEnabled = r15
            r14.forceAdd = r12
            int r15 = android.view.View.MeasureSpec.getSize(r19)
            int r15 = android.view.View.MeasureSpec.makeSafeMeasureSpec(r15, r10)
            int r12 = r14.height
            int r12 = getChildMeasureSpec(r15, r10, r12)
            int r15 = r0.mColumnWidth
            r11 = 1073741824(0x40000000, float:2.0)
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r11)
            int r15 = r14.width
            int r11 = getChildMeasureSpec(r11, r10, r15)
            r13.measure(r11, r12)
            int r7 = r13.getMeasuredHeight()
            int r15 = r13.getMeasuredState()
            int r8 = combineMeasuredStates(r8, r15)
            android.widget.AbsListView$RecycleBin r15 = r0.mRecycler
            int r10 = r14.viewType
            boolean r10 = r15.shouldRecycleViewType(r10)
            if (r10 == 0) goto L_0x00c1
            android.widget.AbsListView$RecycleBin r10 = r0.mRecycler
            r15 = -1
            r10.addScrapView(r13, r15)
        L_0x00c1:
            if (r2 != 0) goto L_0x00d5
            android.graphics.Rect r10 = r0.mListPadding
            int r10 = r10.top
            android.graphics.Rect r11 = r0.mListPadding
            int r11 = r11.bottom
            int r10 = r10 + r11
            int r10 = r10 + r7
            int r11 = r17.getVerticalFadingEdgeLength()
            int r11 = r11 * 2
            int r4 = r10 + r11
        L_0x00d5:
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r2 != r10) goto L_0x00fa
            android.graphics.Rect r11 = r0.mListPadding
            int r11 = r11.top
            android.graphics.Rect r12 = r0.mListPadding
            int r12 = r12.bottom
            int r11 = r11 + r12
            int r12 = r0.mNumColumns
            r16 = 0
        L_0x00e6:
            r13 = r16
            if (r13 >= r9) goto L_0x00f9
            int r11 = r11 + r7
            int r14 = r13 + r12
            if (r14 >= r9) goto L_0x00f2
            int r14 = r0.mVerticalSpacing
            int r11 = r11 + r14
        L_0x00f2:
            if (r11 < r4) goto L_0x00f6
            r11 = r4
            goto L_0x00f9
        L_0x00f6:
            int r16 = r13 + r12
            goto L_0x00e6
        L_0x00f9:
            r4 = r11
        L_0x00fa:
            if (r1 != r10) goto L_0x011f
            int r10 = r0.mRequestedNumColumns
            r11 = -1
            if (r10 == r11) goto L_0x011f
            int r10 = r0.mRequestedNumColumns
            int r11 = r0.mColumnWidth
            int r10 = r10 * r11
            int r11 = r0.mRequestedNumColumns
            r12 = 1
            int r11 = r11 - r12
            int r12 = r0.mHorizontalSpacing
            int r11 = r11 * r12
            int r10 = r10 + r11
            android.graphics.Rect r11 = r0.mListPadding
            int r11 = r11.left
            int r10 = r10 + r11
            android.graphics.Rect r11 = r0.mListPadding
            int r11 = r11.right
            int r10 = r10 + r11
            if (r10 > r3) goto L_0x011c
            if (r6 == 0) goto L_0x011f
        L_0x011c:
            r11 = 16777216(0x1000000, float:2.3509887E-38)
            r3 = r3 | r11
        L_0x011f:
            r0.setMeasuredDimension(r3, r4)
            r10 = r18
            r0.mWidthMeasureSpec = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {
        GridLayoutAnimationController.AnimationParameters animationParams = (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;
        if (animationParams == null) {
            animationParams = new GridLayoutAnimationController.AnimationParameters();
            params.layoutAnimationParameters = animationParams;
        }
        animationParams.count = count;
        animationParams.index = index;
        animationParams.columnsCount = this.mNumColumns;
        animationParams.rowsCount = count / this.mNumColumns;
        if (!this.mStackFromBottom) {
            animationParams.column = index % this.mNumColumns;
            animationParams.row = index / this.mNumColumns;
            return;
        }
        int invertedIndex = (count - 1) - index;
        animationParams.column = (this.mNumColumns - 1) - (invertedIndex % this.mNumColumns);
        animationParams.row = (animationParams.rowsCount - 1) - (invertedIndex / this.mNumColumns);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01c0 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x01cc A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0216 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0266 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0273 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x0289 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0291  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:185:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00d6 A[Catch:{ all -> 0x0297 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00f2 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0105 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x010a A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0110 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x011a A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0124 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0131 A[Catch:{ all -> 0x0295 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0143 A[Catch:{ all -> 0x0295 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layoutChildren() {
        /*
            r27 = this;
            r1 = r27
            boolean r2 = r1.mBlockLayoutRequests
            r0 = 1
            if (r2 != 0) goto L_0x0009
            r1.mBlockLayoutRequests = r0
        L_0x0009:
            r3 = 0
            super.layoutChildren()     // Catch:{ all -> 0x0297 }
            r27.invalidate()     // Catch:{ all -> 0x0297 }
            android.widget.ListAdapter r4 = r1.mAdapter     // Catch:{ all -> 0x0297 }
            if (r4 != 0) goto L_0x0024
            r27.resetList()     // Catch:{ all -> 0x001f }
            r27.invokeOnItemScrollListener()     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x001e
            r1.mBlockLayoutRequests = r3
        L_0x001e:
            return
        L_0x001f:
            r0 = move-exception
            r21 = r2
            goto L_0x029a
        L_0x0024:
            android.graphics.Rect r4 = r1.mListPadding     // Catch:{ all -> 0x0297 }
            int r4 = r4.top     // Catch:{ all -> 0x0297 }
            int r5 = r1.mBottom     // Catch:{ all -> 0x0297 }
            int r6 = r1.mTop     // Catch:{ all -> 0x0297 }
            int r5 = r5 - r6
            android.graphics.Rect r6 = r1.mListPadding     // Catch:{ all -> 0x0297 }
            int r6 = r6.bottom     // Catch:{ all -> 0x0297 }
            int r5 = r5 - r6
            int r6 = r27.getChildCount()     // Catch:{ all -> 0x0297 }
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            int r11 = r1.mLayoutMode     // Catch:{ all -> 0x0297 }
            switch(r11) {
                case 1: goto L_0x005c;
                case 2: goto L_0x004d;
                case 3: goto L_0x005c;
                case 4: goto L_0x005c;
                case 5: goto L_0x005c;
                case 6: goto L_0x0042;
                default: goto L_0x003f;
            }     // Catch:{ all -> 0x0297 }
        L_0x003f:
            int r11 = r1.mSelectedPosition     // Catch:{ all -> 0x0297 }
            goto L_0x005d
        L_0x0042:
            int r11 = r1.mNextSelectedPosition     // Catch:{ all -> 0x001f }
            if (r11 < 0) goto L_0x006e
            int r11 = r1.mNextSelectedPosition     // Catch:{ all -> 0x001f }
            int r12 = r1.mSelectedPosition     // Catch:{ all -> 0x001f }
            int r7 = r11 - r12
            goto L_0x006e
        L_0x004d:
            int r11 = r1.mNextSelectedPosition     // Catch:{ all -> 0x001f }
            int r12 = r1.mFirstPosition     // Catch:{ all -> 0x001f }
            int r11 = r11 - r12
            if (r11 < 0) goto L_0x006e
            if (r11 >= r6) goto L_0x006e
            android.view.View r12 = r1.getChildAt(r11)     // Catch:{ all -> 0x001f }
            r10 = r12
            goto L_0x006e
        L_0x005c:
            goto L_0x006e
        L_0x005d:
            int r12 = r1.mFirstPosition     // Catch:{ all -> 0x0297 }
            int r11 = r11 - r12
            if (r11 < 0) goto L_0x0069
            if (r11 >= r6) goto L_0x0069
            android.view.View r12 = r1.getChildAt(r11)     // Catch:{ all -> 0x001f }
            r8 = r12
        L_0x0069:
            android.view.View r12 = r1.getChildAt(r3)     // Catch:{ all -> 0x0297 }
            r9 = r12
        L_0x006e:
            boolean r11 = r1.mDataChanged     // Catch:{ all -> 0x0297 }
            if (r11 == 0) goto L_0x0075
            r27.handleDataChanged()     // Catch:{ all -> 0x001f }
        L_0x0075:
            int r12 = r1.mItemCount     // Catch:{ all -> 0x0297 }
            if (r12 != 0) goto L_0x0084
            r27.resetList()     // Catch:{ all -> 0x001f }
            r27.invokeOnItemScrollListener()     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x0083
            r1.mBlockLayoutRequests = r3
        L_0x0083:
            return
        L_0x0084:
            int r12 = r1.mNextSelectedPosition     // Catch:{ all -> 0x0297 }
            r1.setSelectedPositionInt(r12)     // Catch:{ all -> 0x0297 }
            r12 = 0
            r13 = 0
            r14 = -1
            android.view.ViewRootImpl r15 = r27.getViewRootImpl()     // Catch:{ all -> 0x0297 }
            if (r15 == 0) goto L_0x00ce
            android.view.View r16 = r15.getAccessibilityFocusedHost()     // Catch:{ all -> 0x001f }
            r17 = r16
            r3 = r17
            if (r3 == 0) goto L_0x00ce
            android.view.View r16 = r1.getAccessibilityFocusedChild(r3)     // Catch:{ all -> 0x001f }
            r18 = r16
            r0 = r18
            if (r0 == 0) goto L_0x00ce
            if (r11 == 0) goto L_0x00b8
            boolean r16 = r0.hasTransientState()     // Catch:{ all -> 0x001f }
            if (r16 != 0) goto L_0x00b8
            r19 = r12
            boolean r12 = r1.mAdapterHasStableIds     // Catch:{ all -> 0x001f }
            if (r12 == 0) goto L_0x00b5
            goto L_0x00ba
        L_0x00b5:
            r12 = r19
            goto L_0x00c5
        L_0x00b8:
            r19 = r12
        L_0x00ba:
            r12 = r3
            android.view.accessibility.AccessibilityNodeInfo r13 = r15.getAccessibilityFocusedVirtualView()     // Catch:{ all -> 0x001f }
            r26 = r13
            r13 = r12
            r12 = r26
        L_0x00c5:
            int r16 = r1.getPositionForView(r0)     // Catch:{ all -> 0x001f }
            r14 = r16
            r19 = r12
            goto L_0x00d0
        L_0x00ce:
            r19 = r12
        L_0x00d0:
            int r0 = r1.mFirstPosition     // Catch:{ all -> 0x0297 }
            android.widget.AbsListView$RecycleBin r3 = r1.mRecycler     // Catch:{ all -> 0x0297 }
            if (r11 == 0) goto L_0x00f2
            r12 = 0
        L_0x00d7:
            if (r12 >= r6) goto L_0x00ed
            r20 = r11
            android.view.View r11 = r1.getChildAt(r12)     // Catch:{ all -> 0x0297 }
            r21 = r2
            int r2 = r0 + r12
            r3.addScrapView(r11, r2)     // Catch:{ all -> 0x0295 }
            int r12 = r12 + 1
            r11 = r20
            r2 = r21
            goto L_0x00d7
        L_0x00ed:
            r21 = r2
            r20 = r11
            goto L_0x00f9
        L_0x00f2:
            r21 = r2
            r20 = r11
            r3.fillActiveViews(r6, r0)     // Catch:{ all -> 0x0295 }
        L_0x00f9:
            r27.detachAllViewsFromParent()     // Catch:{ all -> 0x0295 }
            r3.removeSkippedScrap()     // Catch:{ all -> 0x0295 }
            int r2 = r1.mLayoutMode     // Catch:{ all -> 0x0295 }
            r11 = -1
            switch(r2) {
                case 1: goto L_0x0143;
                case 2: goto L_0x0131;
                case 3: goto L_0x0124;
                case 4: goto L_0x011a;
                case 5: goto L_0x0110;
                case 6: goto L_0x010a;
                default: goto L_0x0105;
            }     // Catch:{ all -> 0x0295 }
        L_0x0105:
            if (r6 != 0) goto L_0x0185
            boolean r2 = r1.mStackFromBottom     // Catch:{ all -> 0x0295 }
            goto L_0x014f
        L_0x010a:
            android.view.View r2 = r1.moveSelection(r7, r4, r5)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x0110:
            int r2 = r1.mSyncPosition     // Catch:{ all -> 0x0295 }
            int r12 = r1.mSpecificTop     // Catch:{ all -> 0x0295 }
            android.view.View r2 = r1.fillSpecific(r2, r12)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x011a:
            int r2 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            int r12 = r1.mSpecificTop     // Catch:{ all -> 0x0295 }
            android.view.View r2 = r1.fillSpecific(r2, r12)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x0124:
            int r2 = r1.mItemCount     // Catch:{ all -> 0x0295 }
            r12 = 1
            int r2 = r2 - r12
            android.view.View r2 = r1.fillUp(r2, r5)     // Catch:{ all -> 0x0295 }
            r27.adjustViewsUpOrDown()     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x0131:
            if (r10 == 0) goto L_0x013d
            int r2 = r10.getTop()     // Catch:{ all -> 0x0295 }
            android.view.View r2 = r1.fillFromSelection(r2, r4, r5)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x013d:
            android.view.View r2 = r1.fillSelection(r4, r5)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x0143:
            r2 = 0
            r1.mFirstPosition = r2     // Catch:{ all -> 0x0295 }
            android.view.View r2 = r1.fillFromTop(r4)     // Catch:{ all -> 0x0295 }
            r27.adjustViewsUpOrDown()     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x014f:
            if (r2 != 0) goto L_0x0169
            android.widget.ListAdapter r2 = r1.mAdapter     // Catch:{ all -> 0x0295 }
            if (r2 == 0) goto L_0x015f
            boolean r2 = r27.isInTouchMode()     // Catch:{ all -> 0x0295 }
            if (r2 == 0) goto L_0x015c
            goto L_0x015f
        L_0x015c:
            r2 = 0
            goto L_0x0161
        L_0x015f:
            r2 = r11
        L_0x0161:
            r1.setSelectedPositionInt(r2)     // Catch:{ all -> 0x0295 }
            android.view.View r2 = r1.fillFromTop(r4)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x0169:
            int r2 = r1.mItemCount     // Catch:{ all -> 0x0295 }
            r12 = 1
            int r2 = r2 - r12
            android.widget.ListAdapter r12 = r1.mAdapter     // Catch:{ all -> 0x0295 }
            if (r12 == 0) goto L_0x017a
            boolean r12 = r27.isInTouchMode()     // Catch:{ all -> 0x0295 }
            if (r12 == 0) goto L_0x0178
            goto L_0x017a
        L_0x0178:
            r12 = r2
            goto L_0x017c
        L_0x017a:
            r12 = r11
        L_0x017c:
            r1.setSelectedPositionInt(r12)     // Catch:{ all -> 0x0295 }
            android.view.View r12 = r1.fillFromBottom(r2, r5)     // Catch:{ all -> 0x0295 }
            r2 = r12
            goto L_0x01bb
        L_0x0185:
            int r2 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            if (r2 < 0) goto L_0x019f
            int r2 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            int r12 = r1.mItemCount     // Catch:{ all -> 0x0295 }
            if (r2 >= r12) goto L_0x019f
            int r2 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            if (r8 != 0) goto L_0x0196
            r12 = r4
            goto L_0x019a
        L_0x0196:
            int r12 = r8.getTop()     // Catch:{ all -> 0x0295 }
        L_0x019a:
            android.view.View r2 = r1.fillSpecific(r2, r12)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x019f:
            int r2 = r1.mFirstPosition     // Catch:{ all -> 0x0295 }
            int r12 = r1.mItemCount     // Catch:{ all -> 0x0295 }
            if (r2 >= r12) goto L_0x01b5
            int r2 = r1.mFirstPosition     // Catch:{ all -> 0x0295 }
            if (r9 != 0) goto L_0x01ac
            r12 = r4
            goto L_0x01b0
        L_0x01ac:
            int r12 = r9.getTop()     // Catch:{ all -> 0x0295 }
        L_0x01b0:
            android.view.View r2 = r1.fillSpecific(r2, r12)     // Catch:{ all -> 0x0295 }
            goto L_0x01bb
        L_0x01b5:
            r2 = 0
            android.view.View r12 = r1.fillSpecific(r2, r4)     // Catch:{ all -> 0x0295 }
            r2 = r12
        L_0x01bb:
            r3.scrapActiveViews()     // Catch:{ all -> 0x0295 }
            if (r2 == 0) goto L_0x01cc
            r1.positionSelector(r11, r2)     // Catch:{ all -> 0x0295 }
            int r12 = r2.getTop()     // Catch:{ all -> 0x0295 }
            r1.mSelectedTop = r12     // Catch:{ all -> 0x0295 }
            r22 = r0
            goto L_0x020d
        L_0x01cc:
            int r12 = r1.mTouchMode     // Catch:{ all -> 0x0295 }
            if (r12 <= 0) goto L_0x01d7
            int r12 = r1.mTouchMode     // Catch:{ all -> 0x0295 }
            r11 = 3
            if (r12 >= r11) goto L_0x01d7
            r11 = 1
            goto L_0x01d8
        L_0x01d7:
            r11 = 0
        L_0x01d8:
            if (r11 == 0) goto L_0x01ed
            int r12 = r1.mMotionPosition     // Catch:{ all -> 0x0295 }
            r22 = r0
            int r0 = r1.mFirstPosition     // Catch:{ all -> 0x0295 }
            int r12 = r12 - r0
            android.view.View r0 = r1.getChildAt(r12)     // Catch:{ all -> 0x0295 }
            if (r0 == 0) goto L_0x01ec
            int r12 = r1.mMotionPosition     // Catch:{ all -> 0x0295 }
            r1.positionSelector(r12, r0)     // Catch:{ all -> 0x0295 }
        L_0x01ec:
            goto L_0x020d
        L_0x01ed:
            r22 = r0
            int r0 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            r12 = -1
            if (r0 == r12) goto L_0x0205
            int r0 = r1.mSelectorPosition     // Catch:{ all -> 0x0295 }
            int r12 = r1.mFirstPosition     // Catch:{ all -> 0x0295 }
            int r0 = r0 - r12
            android.view.View r0 = r1.getChildAt(r0)     // Catch:{ all -> 0x0295 }
            if (r0 == 0) goto L_0x0204
            int r12 = r1.mSelectorPosition     // Catch:{ all -> 0x0295 }
            r1.positionSelector(r12, r0)     // Catch:{ all -> 0x0295 }
        L_0x0204:
            goto L_0x020d
        L_0x0205:
            r12 = 0
            r1.mSelectedTop = r12     // Catch:{ all -> 0x0295 }
            android.graphics.Rect r0 = r1.mSelectorRect     // Catch:{ all -> 0x0295 }
            r0.setEmpty()     // Catch:{ all -> 0x0295 }
        L_0x020d:
            r0 = 0
            if (r15 == 0) goto L_0x0266
            android.view.View r11 = r15.getAccessibilityFocusedHost()     // Catch:{ all -> 0x0295 }
            if (r11 != 0) goto L_0x0266
            if (r13 == 0) goto L_0x0246
            boolean r12 = r13.isAttachedToWindow()     // Catch:{ all -> 0x0295 }
            if (r12 == 0) goto L_0x0246
            android.view.accessibility.AccessibilityNodeProvider r12 = r13.getAccessibilityNodeProvider()     // Catch:{ all -> 0x0295 }
            if (r19 == 0) goto L_0x023e
            if (r12 == 0) goto L_0x023e
            long r16 = r19.getSourceNodeId()     // Catch:{ all -> 0x0295 }
            int r16 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(r16)     // Catch:{ all -> 0x0295 }
            r23 = r16
            r24 = r2
            r2 = 64
            r25 = r3
            r3 = r23
            r12.performAction(r3, r2, r0)     // Catch:{ all -> 0x0295 }
            goto L_0x0245
        L_0x023e:
            r24 = r2
            r25 = r3
            r13.requestAccessibilityFocus()     // Catch:{ all -> 0x0295 }
        L_0x0245:
            goto L_0x026a
        L_0x0246:
            r24 = r2
            r25 = r3
            r2 = -1
            if (r14 == r2) goto L_0x026a
            int r2 = r1.mFirstPosition     // Catch:{ all -> 0x0295 }
            int r2 = r14 - r2
            int r3 = r27.getChildCount()     // Catch:{ all -> 0x0295 }
            r12 = 1
            int r3 = r3 - r12
            r12 = 0
            int r2 = android.util.MathUtils.constrain((int) r2, (int) r12, (int) r3)     // Catch:{ all -> 0x0295 }
            android.view.View r3 = r1.getChildAt(r2)     // Catch:{ all -> 0x0295 }
            if (r3 == 0) goto L_0x026a
            r3.requestAccessibilityFocus()     // Catch:{ all -> 0x0295 }
            goto L_0x026a
        L_0x0266:
            r24 = r2
            r25 = r3
        L_0x026a:
            r2 = 0
            r1.mLayoutMode = r2     // Catch:{ all -> 0x0295 }
            r1.mDataChanged = r2     // Catch:{ all -> 0x0295 }
            java.lang.Runnable r2 = r1.mPositionScrollAfterLayout     // Catch:{ all -> 0x0295 }
            if (r2 == 0) goto L_0x027a
            java.lang.Runnable r2 = r1.mPositionScrollAfterLayout     // Catch:{ all -> 0x0295 }
            r1.post(r2)     // Catch:{ all -> 0x0295 }
            r1.mPositionScrollAfterLayout = r0     // Catch:{ all -> 0x0295 }
        L_0x027a:
            r2 = 0
            r1.mNeedSync = r2     // Catch:{ all -> 0x0295 }
            int r0 = r1.mSelectedPosition     // Catch:{ all -> 0x0295 }
            r1.setNextSelectedPositionInt(r0)     // Catch:{ all -> 0x0295 }
            r27.updateScrollIndicators()     // Catch:{ all -> 0x0295 }
            int r0 = r1.mItemCount     // Catch:{ all -> 0x0295 }
            if (r0 <= 0) goto L_0x028c
            r27.checkSelectionChanged()     // Catch:{ all -> 0x0295 }
        L_0x028c:
            r27.invokeOnItemScrollListener()     // Catch:{ all -> 0x0295 }
            if (r21 != 0) goto L_0x0294
            r2 = 0
            r1.mBlockLayoutRequests = r2
        L_0x0294:
            return
        L_0x0295:
            r0 = move-exception
            goto L_0x029a
        L_0x0297:
            r0 = move-exception
            r21 = r2
        L_0x029a:
            if (r21 != 0) goto L_0x029f
            r2 = 0
            r1.mBlockLayoutRequests = r2
        L_0x029f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.layoutChildren():void");
    }

    private View makeAndAddView(int position, int y, boolean flow, int childrenLeft, boolean selected, int where) {
        View activeView;
        int i = position;
        if (this.mDataChanged || (activeView = this.mRecycler.getActiveView(position)) == null) {
            View child = obtainView(position, this.mIsScrap);
            setupChild(child, position, y, flow, childrenLeft, selected, this.mIsScrap[0], where);
            return child;
        }
        setupChild(activeView, position, y, flow, childrenLeft, selected, true, where);
        return activeView;
    }

    /* JADX WARNING: type inference failed for: r18v0, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setupChild(android.view.View r24, int r25, int r26, boolean r27, int r28, boolean r29, boolean r30, int r31) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r25
            r5 = r31
            java.lang.String r6 = "setupGridItem"
            r7 = 8
            android.os.Trace.traceBegin(r7, r6)
            if (r29 == 0) goto L_0x001a
            boolean r10 = r23.shouldShowSelector()
            if (r10 == 0) goto L_0x001a
            r10 = 1
            goto L_0x001b
        L_0x001a:
            r10 = 0
        L_0x001b:
            boolean r11 = r24.isSelected()
            if (r10 == r11) goto L_0x0023
            r11 = 1
            goto L_0x0024
        L_0x0023:
            r11 = 0
        L_0x0024:
            int r12 = r0.mTouchMode
            r13 = 3
            if (r12 <= 0) goto L_0x0031
            if (r12 >= r13) goto L_0x0031
            int r14 = r0.mMotionPosition
            if (r14 != r2) goto L_0x0031
            r14 = 1
            goto L_0x0032
        L_0x0031:
            r14 = 0
        L_0x0032:
            boolean r7 = r24.isPressed()
            if (r14 == r7) goto L_0x003a
            r7 = 1
            goto L_0x003b
        L_0x003a:
            r7 = 0
        L_0x003b:
            if (r30 == 0) goto L_0x0048
            if (r11 != 0) goto L_0x0048
            boolean r8 = r24.isLayoutRequested()
            if (r8 == 0) goto L_0x0046
            goto L_0x0048
        L_0x0046:
            r8 = 0
            goto L_0x0049
        L_0x0048:
            r8 = 1
        L_0x0049:
            android.view.ViewGroup$LayoutParams r17 = r24.getLayoutParams()
            android.widget.AbsListView$LayoutParams r17 = (android.widget.AbsListView.LayoutParams) r17
            if (r17 != 0) goto L_0x0059
            android.view.ViewGroup$LayoutParams r18 = r23.generateDefaultLayoutParams()
            r17 = r18
            android.widget.AbsListView$LayoutParams r17 = (android.widget.AbsListView.LayoutParams) r17
        L_0x0059:
            r13 = r17
            android.widget.ListAdapter r6 = r0.mAdapter
            int r6 = r6.getItemViewType(r2)
            r13.viewType = r6
            android.widget.ListAdapter r6 = r0.mAdapter
            boolean r6 = r6.isEnabled(r2)
            r13.isEnabled = r6
            if (r11 == 0) goto L_0x0075
            r1.setSelected(r10)
            if (r10 == 0) goto L_0x0075
            r23.requestFocus()
        L_0x0075:
            if (r7 == 0) goto L_0x007a
            r1.setPressed(r14)
        L_0x007a:
            int r6 = r0.mChoiceMode
            if (r6 == 0) goto L_0x00aa
            android.util.SparseBooleanArray r6 = r0.mCheckStates
            if (r6 == 0) goto L_0x00aa
            boolean r6 = r1 instanceof android.widget.Checkable
            if (r6 == 0) goto L_0x0093
            r6 = r1
            android.widget.Checkable r6 = (android.widget.Checkable) r6
            android.util.SparseBooleanArray r9 = r0.mCheckStates
            boolean r9 = r9.get(r2)
            r6.setChecked(r9)
            goto L_0x00aa
        L_0x0093:
            android.content.Context r6 = r23.getContext()
            android.content.pm.ApplicationInfo r6 = r6.getApplicationInfo()
            int r6 = r6.targetSdkVersion
            r9 = 11
            if (r6 < r9) goto L_0x00aa
            android.util.SparseBooleanArray r6 = r0.mCheckStates
            boolean r6 = r6.get(r2)
            r1.setActivated(r6)
        L_0x00aa:
            if (r30 == 0) goto L_0x00c6
            boolean r6 = r13.forceAdd
            if (r6 != 0) goto L_0x00c6
            r0.attachViewToParent(r1, r5, r13)
            if (r30 == 0) goto L_0x00c2
            android.view.ViewGroup$LayoutParams r6 = r24.getLayoutParams()
            android.widget.AbsListView$LayoutParams r6 = (android.widget.AbsListView.LayoutParams) r6
            int r6 = r6.scrappedFromPosition
            if (r6 == r2) goto L_0x00c0
            goto L_0x00c2
        L_0x00c0:
            r6 = 0
            goto L_0x00cd
        L_0x00c2:
            r24.jumpDrawablesToCurrentState()
            goto L_0x00c0
        L_0x00c6:
            r6 = 0
            r13.forceAdd = r6
            r9 = 1
            r0.addViewInLayout(r1, r5, r13, r9)
        L_0x00cd:
            if (r8 == 0) goto L_0x00ed
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r6)
            int r2 = r13.height
            int r2 = android.view.ViewGroup.getChildMeasureSpec(r9, r6, r2)
            int r9 = r0.mColumnWidth
            r6 = 1073741824(0x40000000, float:2.0)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r6)
            int r9 = r13.width
            r4 = 0
            int r4 = android.view.ViewGroup.getChildMeasureSpec(r6, r4, r9)
            r1.measure(r4, r2)
            goto L_0x00f0
        L_0x00ed:
            r23.cleanupLayoutState(r24)
        L_0x00f0:
            int r2 = r24.getMeasuredWidth()
            int r4 = r24.getMeasuredHeight()
            if (r27 == 0) goto L_0x00fd
            r17 = r26
            goto L_0x00ff
        L_0x00fd:
            int r17 = r26 - r4
        L_0x00ff:
            r19 = r17
            int r5 = r23.getLayoutDirection()
            int r6 = r0.mGravity
            int r6 = android.view.Gravity.getAbsoluteGravity(r6, r5)
            r20 = r5
            r5 = r6 & 7
            r21 = r6
            r6 = 1
            if (r5 == r6) goto L_0x0126
            r6 = 3
            if (r5 == r6) goto L_0x0123
            r6 = 5
            if (r5 == r6) goto L_0x011d
            r5 = r28
            goto L_0x012e
        L_0x011d:
            int r5 = r0.mColumnWidth
            int r5 = r28 + r5
            int r5 = r5 - r2
            goto L_0x012e
        L_0x0123:
            r5 = r28
            goto L_0x012e
        L_0x0126:
            int r5 = r0.mColumnWidth
            int r5 = r5 - r2
            int r5 = r5 / 2
            int r5 = r28 + r5
        L_0x012e:
            if (r8 == 0) goto L_0x013d
            int r6 = r5 + r2
            r22 = r2
            r2 = r19
            int r3 = r2 + r4
            r1.layout(r5, r2, r6, r3)
            goto L_0x0153
        L_0x013d:
            r22 = r2
            r2 = r19
            int r3 = r24.getLeft()
            int r3 = r5 - r3
            r1.offsetLeftAndRight(r3)
            int r3 = r24.getTop()
            int r3 = r2 - r3
            r1.offsetTopAndBottom(r3)
        L_0x0153:
            boolean r3 = r0.mCachingStarted
            if (r3 == 0) goto L_0x0161
            boolean r3 = r24.isDrawingCacheEnabled()
            if (r3 != 0) goto L_0x0161
            r3 = 1
            r1.setDrawingCacheEnabled(r3)
        L_0x0161:
            r15 = 8
            android.os.Trace.traceEnd(r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.GridView.setupChild(android.view.View, int, int, boolean, int, boolean, boolean, int):void");
    }

    public void setSelection(int position) {
        if (!isInTouchMode()) {
            setNextSelectedPositionInt(position);
        } else {
            this.mResurrectToPosition = position;
        }
        this.mLayoutMode = 2;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void setSelectionInt(int position) {
        int next;
        int previousSelectedPosition = this.mNextSelectedPosition;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        setNextSelectedPositionInt(position);
        layoutChildren();
        if (this.mStackFromBottom) {
            next = (this.mItemCount - 1) - this.mNextSelectedPosition;
        } else {
            next = this.mNextSelectedPosition;
        }
        if (next / this.mNumColumns != (this.mStackFromBottom ? (this.mItemCount - 1) - previousSelectedPosition : previousSelectedPosition) / this.mNumColumns) {
            awakenScrollBars();
        }
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

    private boolean commonKey(int keyCode, int count, KeyEvent event) {
        if (this.mAdapter == null) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        boolean handled = false;
        int action = event.getAction();
        if (KeyEvent.isConfirmKey(keyCode) && event.hasNoModifiers() && action != 1 && !(handled = resurrectSelectionIfNeeded()) && event.getRepeatCount() == 0 && getChildCount() > 0) {
            keyPressed();
            handled = true;
        }
        if (!handled && action != 1) {
            switch (keyCode) {
                case 19:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(33);
                            break;
                        }
                    } else {
                        handled = resurrectSelectionIfNeeded() || arrowScroll(33);
                        break;
                    }
                    break;
                case 20:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(130);
                            break;
                        }
                    } else {
                        handled = resurrectSelectionIfNeeded() || arrowScroll(130);
                        break;
                    }
                    break;
                case 21:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || arrowScroll(17);
                        break;
                    }
                    break;
                case 22:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || arrowScroll(66);
                        break;
                    }
                    break;
                case 61:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(1)) {
                            handled = resurrectSelectionIfNeeded() || sequenceScroll(1);
                            break;
                        }
                    } else {
                        handled = resurrectSelectionIfNeeded() || sequenceScroll(2);
                        break;
                    }
                    break;
                case 92:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(33);
                            break;
                        }
                    } else {
                        handled = resurrectSelectionIfNeeded() || pageScroll(33);
                        break;
                    }
                    break;
                case 93:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(130);
                            break;
                        }
                    } else {
                        handled = resurrectSelectionIfNeeded() || pageScroll(130);
                        break;
                    }
                    break;
                case 122:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(33);
                        break;
                    }
                    break;
                case 123:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(130);
                        break;
                    }
                    break;
            }
        } else {
            switch (keyCode) {
                case 691:
                    handled = resurrectSelectionIfNeeded() || arrowScroll(33);
                    break;
                case 692:
                    handled = resurrectSelectionIfNeeded() || arrowScroll(130);
                    break;
            }
        }
        if (handled || sendToTextFilter(keyCode, count, event)) {
            return true;
        }
        switch (action) {
            case 0:
                return super.onKeyDown(keyCode, event);
            case 1:
                return super.onKeyUp(keyCode, event);
            case 2:
                return super.onKeyMultiple(keyCode, count, event);
            default:
                return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean pageScroll(int direction) {
        int nextPage = -1;
        if (direction == 33) {
            nextPage = Math.max(0, this.mSelectedPosition - getChildCount());
        } else if (direction == 130) {
            nextPage = Math.min(this.mItemCount - 1, this.mSelectedPosition + getChildCount());
        }
        if (nextPage < 0) {
            return false;
        }
        setSelectionInt(nextPage);
        invokeOnItemScrollListener();
        awakenScrollBars();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean fullScroll(int direction) {
        boolean moved = false;
        if (direction == 33) {
            this.mLayoutMode = 2;
            setSelectionInt(0);
            invokeOnItemScrollListener();
            moved = true;
        } else if (direction == 130) {
            this.mLayoutMode = 2;
            setSelectionInt(this.mItemCount - 1);
            invokeOnItemScrollListener();
            moved = true;
        }
        if (moved) {
            awakenScrollBars();
        }
        return moved;
    }

    /* access modifiers changed from: package-private */
    public boolean arrowScroll(int direction) {
        int endOfRowPos;
        int startOfRowPos;
        int selectedPosition = this.mSelectedPosition;
        int numColumns = this.mNumColumns;
        boolean moved = false;
        if (!this.mStackFromBottom) {
            startOfRowPos = (selectedPosition / numColumns) * numColumns;
            endOfRowPos = Math.min((startOfRowPos + numColumns) - 1, this.mItemCount - 1);
        } else {
            endOfRowPos = (this.mItemCount - 1) - ((((this.mItemCount - 1) - selectedPosition) / numColumns) * numColumns);
            startOfRowPos = Math.max(0, (endOfRowPos - numColumns) + 1);
        }
        if (direction != 33) {
            if (direction == 130 && endOfRowPos < this.mItemCount - 1) {
                this.mLayoutMode = 6;
                setSelectionInt(Math.min(selectedPosition + numColumns, this.mItemCount - 1));
                moved = true;
            }
        } else if (startOfRowPos > 0) {
            this.mLayoutMode = 6;
            setSelectionInt(Math.max(0, selectedPosition - numColumns));
            moved = true;
        }
        boolean isLayoutRtl = isLayoutRtl();
        if (selectedPosition > startOfRowPos && ((direction == 17 && !isLayoutRtl) || (direction == 66 && isLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(Math.max(0, selectedPosition - 1));
            moved = true;
        } else if (selectedPosition < endOfRowPos && ((direction == 17 && isLayoutRtl) || (direction == 66 && !isLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(Math.min(selectedPosition + 1, this.mItemCount - 1));
            moved = true;
        }
        if (moved) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            invokeOnItemScrollListener();
        }
        if (moved) {
            awakenScrollBars();
        }
        return moved;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean sequenceScroll(int direction) {
        int endOfRow;
        int startOfRow;
        int selectedPosition = this.mSelectedPosition;
        int numColumns = this.mNumColumns;
        int count = this.mItemCount;
        boolean z = false;
        if (!this.mStackFromBottom) {
            startOfRow = (selectedPosition / numColumns) * numColumns;
            endOfRow = Math.min((startOfRow + numColumns) - 1, count - 1);
        } else {
            endOfRow = (count - 1) - ((((count - 1) - selectedPosition) / numColumns) * numColumns);
            startOfRow = Math.max(0, (endOfRow - numColumns) + 1);
        }
        boolean moved = false;
        boolean showScroll = false;
        switch (direction) {
            case 1:
                if (selectedPosition > 0) {
                    this.mLayoutMode = 6;
                    setSelectionInt(selectedPosition - 1);
                    moved = true;
                    if (selectedPosition == startOfRow) {
                        z = true;
                    }
                    showScroll = z;
                    break;
                }
                break;
            case 2:
                if (selectedPosition < count - 1) {
                    this.mLayoutMode = 6;
                    setSelectionInt(selectedPosition + 1);
                    moved = true;
                    if (selectedPosition == endOfRow) {
                        z = true;
                    }
                    showScroll = z;
                    break;
                }
                break;
        }
        if (moved) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            invokeOnItemScrollListener();
        }
        if (showScroll) {
            awakenScrollBars();
        }
        return moved;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        int closestChildIndex = -1;
        if (gainFocus && previouslyFocusedRect != null) {
            previouslyFocusedRect.offset(this.mScrollX, this.mScrollY);
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (isCandidateSelection(i, direction)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestChildIndex = i;
                    }
                }
            }
        }
        if (closestChildIndex >= 0) {
            setSelection(this.mFirstPosition + closestChildIndex);
        } else {
            requestLayout();
        }
    }

    private boolean isCandidateSelection(int childIndex, int direction) {
        int rowEnd;
        int rowStart;
        int count = getChildCount();
        int invertedIndex = (count - 1) - childIndex;
        if (!this.mStackFromBottom) {
            rowStart = childIndex - (childIndex % this.mNumColumns);
            rowEnd = Math.min((this.mNumColumns + rowStart) - 1, count);
        } else {
            rowEnd = (count - 1) - (invertedIndex - (invertedIndex % this.mNumColumns));
            rowStart = Math.max(0, (rowEnd - this.mNumColumns) + 1);
        }
        if (direction != 17) {
            if (direction != 33) {
                if (direction != 66) {
                    if (direction != 130) {
                        switch (direction) {
                            case 1:
                                if (childIndex == rowEnd && rowEnd == count - 1) {
                                    return true;
                                }
                                return false;
                            case 2:
                                if (childIndex == rowStart && rowStart == 0) {
                                    return true;
                                }
                                return false;
                            default:
                                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
                        }
                    } else if (rowStart == 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (childIndex == rowStart) {
                    return true;
                } else {
                    return false;
                }
            } else if (rowEnd == count - 1) {
                return true;
            } else {
                return false;
            }
        } else if (childIndex == rowEnd) {
            return true;
        } else {
            return false;
        }
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            this.mGravity = gravity;
            requestLayoutIfNecessary();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        if (horizontalSpacing != this.mRequestedHorizontalSpacing) {
            this.mRequestedHorizontalSpacing = horizontalSpacing;
            requestLayoutIfNecessary();
        }
    }

    public int getHorizontalSpacing() {
        return this.mHorizontalSpacing;
    }

    public int getRequestedHorizontalSpacing() {
        return this.mRequestedHorizontalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        if (verticalSpacing != this.mVerticalSpacing) {
            this.mVerticalSpacing = verticalSpacing;
            requestLayoutIfNecessary();
        }
    }

    public int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    public void setStretchMode(int stretchMode) {
        if (stretchMode != this.mStretchMode) {
            this.mStretchMode = stretchMode;
            requestLayoutIfNecessary();
        }
    }

    public int getStretchMode() {
        return this.mStretchMode;
    }

    public void setColumnWidth(int columnWidth) {
        if (columnWidth != this.mRequestedColumnWidth) {
            this.mRequestedColumnWidth = columnWidth;
            requestLayoutIfNecessary();
        }
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

    public int getRequestedColumnWidth() {
        return this.mRequestedColumnWidth;
    }

    public void setNumColumns(int numColumns) {
        if (numColumns != this.mRequestedNumColumns) {
            this.mRequestedNumColumns = numColumns;
            requestLayoutIfNecessary();
        }
    }

    @ViewDebug.ExportedProperty
    public int getNumColumns() {
        return this.mNumColumns;
    }

    private void adjustViewsUpOrDown() {
        int delta;
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                delta = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    delta -= this.mVerticalSpacing;
                }
                if (delta < 0) {
                    delta = 0;
                }
            } else {
                int delta2 = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    delta2 += this.mVerticalSpacing;
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

    /* access modifiers changed from: protected */
    public int computeVerticalScrollExtent() {
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }
        int numColumns = this.mNumColumns;
        int extent = (((count + numColumns) - 1) / numColumns) * 100;
        View view = getChildAt(0);
        int top = view.getTop();
        int height = view.getHeight();
        if (height > 0) {
            extent += (top * 100) / height;
        }
        View view2 = getChildAt(count - 1);
        int bottom = view2.getBottom();
        int height2 = view2.getHeight();
        if (height2 > 0) {
            return extent - (((bottom - getHeight()) * 100) / height2);
        }
        return extent;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollOffset() {
        int oddItemsOnFirstRow;
        if (this.mFirstPosition >= 0 && getChildCount() > 0) {
            View view = getChildAt(0);
            int top = view.getTop();
            int height = view.getHeight();
            if (height > 0) {
                int numColumns = this.mNumColumns;
                int rowCount = ((this.mItemCount + numColumns) - 1) / numColumns;
                if (isStackFromBottom()) {
                    oddItemsOnFirstRow = (rowCount * numColumns) - this.mItemCount;
                } else {
                    oddItemsOnFirstRow = 0;
                }
                return Math.max(((((this.mFirstPosition + oddItemsOnFirstRow) / numColumns) * 100) - ((top * 100) / height)) + ((int) ((((float) this.mScrollY) / ((float) getHeight())) * ((float) rowCount) * 100.0f)), 0);
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        int numColumns = this.mNumColumns;
        int rowCount = ((this.mItemCount + numColumns) - 1) / numColumns;
        int result = Math.max(rowCount * 100, 0);
        if (this.mScrollY != 0) {
            return result + Math.abs((int) ((((float) this.mScrollY) / ((float) getHeight())) * ((float) rowCount) * 100.0f));
        }
        return result;
    }

    public CharSequence getAccessibilityClassName() {
        return GridView.class.getName();
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        int columnsCount = getNumColumns();
        int rowsCount = getCount() / columnsCount;
        info.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(rowsCount, columnsCount, false, getSelectionModeForAccessibility()));
        if (columnsCount > 0 || rowsCount > 0) {
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
        int numColumns = getNumColumns();
        int row = arguments.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
        int position = Math.min(row * numColumns, getCount() - 1);
        if (row < 0) {
            return false;
        }
        smoothScrollToPosition(position);
        return true;
    }

    public void onInitializeAccessibilityNodeInfoForItem(View view, int position, AccessibilityNodeInfo info) {
        int row;
        int invertedIndex;
        super.onInitializeAccessibilityNodeInfoForItem(view, position, info);
        int count = getCount();
        int columnsCount = getNumColumns();
        int rowsCount = count / columnsCount;
        if (!this.mStackFromBottom) {
            invertedIndex = position % columnsCount;
            row = position / columnsCount;
        } else {
            int invertedIndex2 = (count - 1) - position;
            row = (rowsCount - 1) - (invertedIndex2 / columnsCount);
            invertedIndex = (columnsCount - 1) - (invertedIndex2 % columnsCount);
        }
        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        info.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(row, 1, invertedIndex, 1, lp != null && lp.viewType == -2, isItemChecked(position)));
    }

    /* access modifiers changed from: protected */
    public void encodeProperties(ViewHierarchyEncoder encoder) {
        super.encodeProperties(encoder);
        encoder.addProperty("numColumns", getNumColumns());
    }
}
