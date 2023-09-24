package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.C3132R;

@RemoteViews.RemoteView
@Deprecated
/* loaded from: classes4.dex */
public class AbsoluteLayout extends ViewGroup {

    /* loaded from: classes4.dex */
    public static class LayoutParams extends ViewGroup.LayoutParams {

        /* renamed from: x */
        public int f2441x;

        /* renamed from: y */
        public int f2442y;

        /* loaded from: classes4.dex */
        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_xId;
            private int mLayout_yId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_xId = propertyMapper.mapInt("layout_x", 16843135);
                this.mLayout_yId = propertyMapper.mapInt("layout_y", 16843136);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams node, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_xId, node.f2441x);
                propertyReader.readInt(this.mLayout_yId, node.f2442y);
            }
        }

        public LayoutParams(int width, int height, int x, int y) {
            super(width, height);
            this.f2441x = x;
            this.f2442y = y;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, C3132R.styleable.AbsoluteLayout_Layout);
            this.f2441x = a.getDimensionPixelOffset(0, 0);
            this.f2442y = a.getDimensionPixelOffset(1, 0);
            a.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String output) {
            return output + "Absolute.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " x=" + this.f2441x + " y=" + this.f2442y + "}";
        }
    }

    public AbsoluteLayout(Context context) {
        this(context, null);
    }

    public AbsoluteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxWidth = 0;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxHeight = 0;
        for (int maxHeight2 = 0; maxHeight2 < count; maxHeight2++) {
            View child = getChildAt(maxHeight2);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childRight = lp.f2441x + child.getMeasuredWidth();
                int childBottom = lp.f2442y + child.getMeasuredHeight();
                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }
        int i = this.mPaddingLeft;
        int maxWidth2 = maxWidth + i + this.mPaddingRight;
        int maxHeight3 = Math.max(maxHeight + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight());
        int maxHeight4 = getSuggestedMinimumWidth();
        setMeasuredDimension(resolveSizeAndState(Math.max(maxWidth2, maxHeight4), widthMeasureSpec, 0), resolveSizeAndState(maxHeight3, heightMeasureSpec, 0));
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2, 0, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childLeft = this.mPaddingLeft + lp.f2441x;
                int childTop = this.mPaddingTop + lp.f2442y;
                child.layout(childLeft, childTop, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop);
            }
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
