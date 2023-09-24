package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.internal.C3132R;

@Deprecated
/* loaded from: classes4.dex */
public class TwoLineListItem extends RelativeLayout {
    private TextView mText1;
    private TextView mText2;

    public TwoLineListItem(Context context) {
        this(context, null, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.TwoLineListItem, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, C3132R.styleable.TwoLineListItem, attrs, a, defStyleAttr, defStyleRes);
        a.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mText1 = (TextView) findViewById(16908308);
        this.mText2 = (TextView) findViewById(16908309);
    }

    public TextView getText1() {
        return this.mText1;
    }

    public TextView getText2() {
        return this.mText2;
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TwoLineListItem.class.getName();
    }
}
