package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import java.util.ArrayList;

public final class IconMenuView extends ViewGroup implements MenuBuilder.ItemInvoker, MenuView, Runnable {
    private static final int ITEM_CAPTION_CYCLE_DELAY = 1000;
    private int mAnimations;
    private boolean mHasStaleChildren;
    private Drawable mHorizontalDivider;
    private int mHorizontalDividerHeight;
    private ArrayList<Rect> mHorizontalDividerRects;
    @UnsupportedAppUsage
    private Drawable mItemBackground;
    private boolean mLastChildrenCaptionMode;
    private int[] mLayout;
    private int mLayoutNumRows;
    @UnsupportedAppUsage
    private int mMaxItems;
    private int mMaxItemsPerRow;
    private int mMaxRows;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public MenuBuilder mMenu;
    private boolean mMenuBeingLongpressed = false;
    private Drawable mMoreIcon;
    private int mNumActualItemsShown;
    private int mRowHeight;
    private Drawable mVerticalDivider;
    private ArrayList<Rect> mVerticalDividerRects;
    private int mVerticalDividerWidth;

    public IconMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconMenuView, 0, 0);
        this.mRowHeight = a.getDimensionPixelSize(0, 64);
        this.mMaxRows = a.getInt(1, 2);
        this.mMaxItems = a.getInt(4, 6);
        this.mMaxItemsPerRow = a.getInt(2, 3);
        this.mMoreIcon = a.getDrawable(3);
        a.recycle();
        TypedArray a2 = context.obtainStyledAttributes(attrs, R.styleable.MenuView, 0, 0);
        this.mItemBackground = a2.getDrawable(5);
        this.mHorizontalDivider = a2.getDrawable(2);
        this.mHorizontalDividerRects = new ArrayList<>();
        this.mVerticalDivider = a2.getDrawable(3);
        this.mVerticalDividerRects = new ArrayList<>();
        this.mAnimations = a2.getResourceId(0, 0);
        a2.recycle();
        if (this.mHorizontalDivider != null) {
            this.mHorizontalDividerHeight = this.mHorizontalDivider.getIntrinsicHeight();
            if (this.mHorizontalDividerHeight == -1) {
                this.mHorizontalDividerHeight = 1;
            }
        }
        if (this.mVerticalDivider != null) {
            this.mVerticalDividerWidth = this.mVerticalDivider.getIntrinsicWidth();
            if (this.mVerticalDividerWidth == -1) {
                this.mVerticalDividerWidth = 1;
            }
        }
        this.mLayout = new int[this.mMaxRows];
        setWillNotDraw(false);
        setFocusableInTouchMode(true);
        setDescendantFocusability(262144);
    }

    /* access modifiers changed from: package-private */
    public int getMaxItems() {
        return this.mMaxItems;
    }

    private void layoutItems(int width) {
        int numItems = getChildCount();
        if (numItems == 0) {
            this.mLayoutNumRows = 0;
            return;
        }
        int curNumRows = Math.min((int) Math.ceil((double) (((float) numItems) / ((float) this.mMaxItemsPerRow))), this.mMaxRows);
        while (curNumRows <= this.mMaxRows) {
            layoutItemsUsingGravity(curNumRows, numItems);
            if (curNumRows < numItems && !doItemsFit()) {
                curNumRows++;
            } else {
                return;
            }
        }
    }

    private void layoutItemsUsingGravity(int numRows, int numItems) {
        int numBaseItemsPerRow = numItems / numRows;
        int rowsThatGetALeftoverItem = numRows - (numItems % numRows);
        int[] layout = this.mLayout;
        for (int i = 0; i < numRows; i++) {
            layout[i] = numBaseItemsPerRow;
            if (i >= rowsThatGetALeftoverItem) {
                layout[i] = layout[i] + 1;
            }
        }
        this.mLayoutNumRows = numRows;
    }

    private boolean doItemsFit() {
        int[] layout = this.mLayout;
        int numRows = this.mLayoutNumRows;
        int itemPos = 0;
        for (int row = 0; row < numRows; row++) {
            int numItemsOnRow = layout[row];
            if (numItemsOnRow == 1) {
                itemPos++;
            } else {
                int itemPos2 = itemPos;
                int itemsOnRowCounter = numItemsOnRow;
                while (itemsOnRowCounter > 0) {
                    int itemPos3 = itemPos2 + 1;
                    if (((LayoutParams) getChildAt(itemPos2).getLayoutParams()).maxNumItemsOnRow < numItemsOnRow) {
                        return false;
                    }
                    itemsOnRowCounter--;
                    itemPos2 = itemPos3;
                }
                itemPos = itemPos2;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public Drawable getItemBackgroundDrawable() {
        return this.mItemBackground.getConstantState().newDrawable(getContext().getResources());
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public IconMenuItemView createMoreItemView() {
        Context context = getContext();
        IconMenuItemView itemView = (IconMenuItemView) LayoutInflater.from(context).inflate((int) R.layout.icon_menu_item_layout, (ViewGroup) null);
        itemView.initialize(context.getResources().getText(R.string.more_item_label), this.mMoreIcon);
        itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IconMenuView.this.mMenu.changeMenuMode();
            }
        });
        return itemView;
    }

    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    /* JADX WARNING: type inference failed for: r2v12, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void positionChildren(int r21, int r22) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            android.graphics.drawable.Drawable r2 = r0.mHorizontalDivider
            if (r2 == 0) goto L_0x000d
            java.util.ArrayList<android.graphics.Rect> r2 = r0.mHorizontalDividerRects
            r2.clear()
        L_0x000d:
            android.graphics.drawable.Drawable r2 = r0.mVerticalDivider
            if (r2 == 0) goto L_0x0016
            java.util.ArrayList<android.graphics.Rect> r2 = r0.mVerticalDividerRects
            r2.clear()
        L_0x0016:
            int r2 = r0.mLayoutNumRows
            int r3 = r2 + -1
            int[] r4 = r0.mLayout
            r5 = 0
            r6 = 0
            r7 = 0
            int r8 = r0.mHorizontalDividerHeight
            int r9 = r2 + -1
            int r8 = r8 * r9
            int r8 = r22 - r8
            float r8 = (float) r8
            float r10 = (float) r2
            float r8 = r8 / r10
            r11 = r7
            r7 = r5
            r5 = 0
        L_0x002c:
            if (r5 >= r2) goto L_0x00d4
            r12 = 0
            int r13 = r0.mVerticalDividerWidth
            r14 = r4[r5]
            int r14 = r14 + -1
            int r13 = r13 * r14
            int r13 = r1 - r13
            float r13 = (float) r13
            r14 = r4[r5]
            float r14 = (float) r14
            float r13 = r13 / r14
            r14 = r12
            r12 = r6
            r6 = 0
        L_0x0040:
            r15 = r4[r5]
            if (r6 >= r15) goto L_0x00a5
            android.view.View r15 = r0.getChildAt(r7)
            int r10 = (int) r13
            r16 = r2
            r2 = 1073741824(0x40000000, float:2.0)
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r2)
            r17 = r4
            int r4 = (int) r8
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
            r15.measure(r10, r2)
            android.view.ViewGroup$LayoutParams r2 = r15.getLayoutParams()
            r12 = r2
            com.android.internal.view.menu.IconMenuView$LayoutParams r12 = (com.android.internal.view.menu.IconMenuView.LayoutParams) r12
            int r2 = (int) r14
            r12.left = r2
            float r2 = r14 + r13
            int r2 = (int) r2
            r12.right = r2
            int r2 = (int) r11
            r12.top = r2
            float r2 = r11 + r8
            int r2 = (int) r2
            r12.bottom = r2
            float r14 = r14 + r13
            int r7 = r7 + 1
            android.graphics.drawable.Drawable r2 = r0.mVerticalDivider
            if (r2 == 0) goto L_0x0092
            java.util.ArrayList<android.graphics.Rect> r2 = r0.mVerticalDividerRects
            android.graphics.Rect r4 = new android.graphics.Rect
            int r10 = (int) r14
            r18 = r7
            int r7 = (int) r11
            int r9 = r0.mVerticalDividerWidth
            float r9 = (float) r9
            float r9 = r9 + r14
            int r9 = (int) r9
            r19 = r12
            float r12 = r11 + r8
            int r12 = (int) r12
            r4.<init>(r10, r7, r9, r12)
            r2.add(r4)
            goto L_0x0096
        L_0x0092:
            r18 = r7
            r19 = r12
        L_0x0096:
            int r2 = r0.mVerticalDividerWidth
            float r2 = (float) r2
            float r14 = r14 + r2
            int r6 = r6 + 1
            r2 = r16
            r4 = r17
            r7 = r18
            r12 = r19
            goto L_0x0040
        L_0x00a5:
            r16 = r2
            r17 = r4
            if (r12 == 0) goto L_0x00ad
            r12.right = r1
        L_0x00ad:
            float r11 = r11 + r8
            android.graphics.drawable.Drawable r2 = r0.mHorizontalDivider
            if (r2 == 0) goto L_0x00ca
            if (r5 >= r3) goto L_0x00ca
            java.util.ArrayList<android.graphics.Rect> r2 = r0.mHorizontalDividerRects
            android.graphics.Rect r4 = new android.graphics.Rect
            int r6 = (int) r11
            int r9 = r0.mHorizontalDividerHeight
            float r9 = (float) r9
            float r9 = r9 + r11
            int r9 = (int) r9
            r10 = 0
            r4.<init>(r10, r6, r1, r9)
            r2.add(r4)
            int r2 = r0.mHorizontalDividerHeight
            float r2 = (float) r2
            float r11 = r11 + r2
            goto L_0x00cb
        L_0x00ca:
            r10 = 0
        L_0x00cb:
            int r5 = r5 + 1
            r6 = r12
            r2 = r16
            r4 = r17
            goto L_0x002c
        L_0x00d4:
            r16 = r2
            r17 = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.menu.IconMenuView.positionChildren(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = resolveSize(Integer.MAX_VALUE, widthMeasureSpec);
        calculateItemFittingMetadata(measuredWidth);
        layoutItems(measuredWidth);
        int layoutNumRows = this.mLayoutNumRows;
        setMeasuredDimension(measuredWidth, resolveSize(((this.mRowHeight + this.mHorizontalDividerHeight) * layoutNumRows) - this.mHorizontalDividerHeight, heightMeasureSpec));
        if (layoutNumRows > 0) {
            positionChildren(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            LayoutParams childLayoutParams = (LayoutParams) child.getLayoutParams();
            child.layout(childLayoutParams.left, childLayoutParams.top, childLayoutParams.right, childLayoutParams.bottom);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable drawable = this.mHorizontalDivider;
        if (drawable != null) {
            ArrayList<Rect> rects = this.mHorizontalDividerRects;
            for (int i = rects.size() - 1; i >= 0; i--) {
                drawable.setBounds(rects.get(i));
                drawable.draw(canvas);
            }
        }
        Drawable drawable2 = this.mVerticalDivider;
        if (drawable2 != null) {
            ArrayList<Rect> rects2 = this.mVerticalDividerRects;
            for (int i2 = rects2.size() - 1; i2 >= 0; i2--) {
                drawable2.setBounds(rects2.get(i2));
                drawable2.draw(canvas);
            }
        }
    }

    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: package-private */
    public void markStaleChildren() {
        if (!this.mHasStaleChildren) {
            this.mHasStaleChildren = true;
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int getNumActualItemsShown() {
        return this.mNumActualItemsShown;
    }

    /* access modifiers changed from: package-private */
    public void setNumActualItemsShown(int count) {
        this.mNumActualItemsShown = count;
    }

    public int getWindowAnimations() {
        return this.mAnimations;
    }

    public int[] getLayout() {
        return this.mLayout;
    }

    public int getLayoutNumRows() {
        return this.mLayoutNumRows;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 82) {
            if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                removeCallbacks(this);
                postDelayed(this, (long) ViewConfiguration.getLongPressTimeout());
            } else if (event.getAction() == 1) {
                if (this.mMenuBeingLongpressed) {
                    setCycleShortcutCaptionMode(false);
                    return true;
                }
                removeCallbacks(this);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestFocus();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setCycleShortcutCaptionMode(false);
        super.onDetachedFromWindow();
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            setCycleShortcutCaptionMode(false);
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void setCycleShortcutCaptionMode(boolean cycleShortcutAndNormal) {
        if (!cycleShortcutAndNormal) {
            removeCallbacks(this);
            setChildrenCaptionMode(false);
            this.mMenuBeingLongpressed = false;
            return;
        }
        setChildrenCaptionMode(true);
    }

    public void run() {
        if (this.mMenuBeingLongpressed) {
            setChildrenCaptionMode(!this.mLastChildrenCaptionMode);
        } else {
            this.mMenuBeingLongpressed = true;
            setCycleShortcutCaptionMode(true);
        }
        postDelayed(this, 1000);
    }

    private void setChildrenCaptionMode(boolean shortcut) {
        this.mLastChildrenCaptionMode = shortcut;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            ((IconMenuItemView) getChildAt(i)).setCaptionMode(shortcut);
        }
    }

    private void calculateItemFittingMetadata(int width) {
        int maxNumItemsPerRow = this.mMaxItemsPerRow;
        int numItems = getChildCount();
        for (int i = 0; i < numItems; i++) {
            LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
            lp.maxNumItemsOnRow = 1;
            int curNumItemsPerRow = maxNumItemsPerRow;
            while (true) {
                if (curNumItemsPerRow <= 0) {
                    break;
                } else if (lp.desiredWidth < width / curNumItemsPerRow) {
                    lp.maxNumItemsOnRow = curNumItemsPerRow;
                    break;
                } else {
                    curNumItemsPerRow--;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        View focusedView = getFocusedChild();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            if (getChildAt(i) == focusedView) {
                return new SavedState(superState, i);
            }
        }
        return new SavedState(superState, -1);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        View v;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.focusedPosition < getChildCount() && (v = getChildAt(ss.focusedPosition)) != null) {
            v.requestFocus();
        }
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
        int focusedPosition;

        public SavedState(Parcelable superState, int focusedPosition2) {
            super(superState);
            this.focusedPosition = focusedPosition2;
        }

        @UnsupportedAppUsage
        private SavedState(Parcel in) {
            super(in);
            this.focusedPosition = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.focusedPosition);
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        int bottom;
        int desiredWidth;
        int left;
        int maxNumItemsOnRow;
        int right;
        int top;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
