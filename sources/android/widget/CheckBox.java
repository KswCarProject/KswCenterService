package android.widget;

import android.content.Context;
import android.util.AttributeSet;

public class CheckBox extends CompoundButton {
    public CheckBox(Context context) {
        this(context, (AttributeSet) null);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 16842860);
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CharSequence getAccessibilityClassName() {
        return CheckBox.class.getName();
    }
}
