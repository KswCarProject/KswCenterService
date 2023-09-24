package android.inputmethodservice;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.internal.C3132R;

/* loaded from: classes.dex */
public class ExtractEditLayout extends LinearLayout {
    Button mExtractActionButton;

    public ExtractEditLayout(Context context) {
        super(context);
    }

    public ExtractEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mExtractActionButton = (Button) findViewById(C3132R.C3134id.inputExtractAction);
    }
}
