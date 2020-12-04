package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;
import java.util.ArrayList;

public class DialogViewAnimator extends ViewAnimator {
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);

    public DialogViewAnimator(Context context) {
        super(context);
    }

    public DialogViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;
        int i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int i5 = 1073741824;
        boolean measureMatchParentChildren = (View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824 && View.MeasureSpec.getMode(heightMeasureSpec) == 1073741824) ? false : true;
        int count = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int state = 0;
        while (true) {
            int i6 = state;
            i = -1;
            if (i6 >= count) {
                break;
            }
            View child = getChildAt(i6);
            if (getMeasureAllChildren() || child.getVisibility() != 8) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
                boolean matchWidth = lp.width == -1;
                boolean matchHeight = lp.height == -1;
                if (measureMatchParentChildren && (matchWidth || matchHeight)) {
                    this.mMatchParentChildren.add(child);
                }
                FrameLayout.LayoutParams lp2 = lp;
                View child2 = child;
                i2 = i6;
                int childState2 = childState;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                int state2 = 0;
                if (measureMatchParentChildren && !matchWidth) {
                    maxWidth = Math.max(maxWidth, child2.getMeasuredWidth() + lp2.leftMargin + lp2.rightMargin);
                    state2 = 0 | (child2.getMeasuredWidthAndState() & -16777216);
                }
                if (measureMatchParentChildren && !matchHeight) {
                    maxHeight = Math.max(maxHeight, child2.getMeasuredHeight() + lp2.topMargin + lp2.bottomMargin);
                    state2 |= (child2.getMeasuredHeightAndState() >> 16) & -256;
                }
                childState = combineMeasuredStates(childState2, state2);
            } else {
                i2 = i6;
            }
            state = i2 + 1;
        }
        int childState3 = childState;
        int maxWidth2 = maxWidth + getPaddingLeft() + getPaddingRight();
        int maxHeight2 = Math.max(maxHeight + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int maxWidth3 = Math.max(maxWidth2, getSuggestedMinimumWidth());
        Drawable drawable = getForeground();
        if (drawable != null) {
            maxHeight2 = Math.max(maxHeight2, drawable.getMinimumHeight());
            maxWidth3 = Math.max(maxWidth3, drawable.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(maxWidth3, i3, childState3), resolveSizeAndState(maxHeight2, i4, childState3 << 16));
        int matchCount = this.mMatchParentChildren.size();
        int i7 = 0;
        while (true) {
            int i8 = i7;
            if (i8 < matchCount) {
                View child3 = this.mMatchParentChildren.get(i8);
                ViewGroup.MarginLayoutParams lp3 = (ViewGroup.MarginLayoutParams) child3.getLayoutParams();
                if (lp3.width == i) {
                    childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - lp3.leftMargin) - lp3.rightMargin, i5);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(i3, getPaddingLeft() + getPaddingRight() + lp3.leftMargin + lp3.rightMargin, lp3.width);
                }
                if (lp3.height == -1) {
                    childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - lp3.topMargin) - lp3.bottomMargin, 1073741824);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(i4, getPaddingTop() + getPaddingBottom() + lp3.topMargin + lp3.bottomMargin, lp3.height);
                }
                child3.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                i7 = i8 + 1;
                i = -1;
                i5 = 1073741824;
            } else {
                this.mMatchParentChildren.clear();
                return;
            }
        }
    }
}
