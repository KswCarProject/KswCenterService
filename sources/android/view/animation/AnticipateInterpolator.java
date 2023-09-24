package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.internal.C3132R;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

@HasNativeInterpolator
/* loaded from: classes4.dex */
public class AnticipateInterpolator extends BaseInterpolator implements NativeInterpolatorFactory {
    private final float mTension;

    public AnticipateInterpolator() {
        this.mTension = 2.0f;
    }

    public AnticipateInterpolator(float tension) {
        this.mTension = tension;
    }

    public AnticipateInterpolator(Context context, AttributeSet attrs) {
        this(context.getResources(), context.getTheme(), attrs);
    }

    public AnticipateInterpolator(Resources res, Resources.Theme theme, AttributeSet attrs) {
        TypedArray a;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, C3132R.styleable.AnticipateInterpolator, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, C3132R.styleable.AnticipateInterpolator);
        }
        this.mTension = a.getFloat(0, 2.0f);
        setChangingConfiguration(a.getChangingConfigurations());
        a.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return t * t * (((this.mTension + 1.0f) * t) - this.mTension);
    }

    @Override // com.android.internal.view.animation.NativeInterpolatorFactory
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAnticipateInterpolator(this.mTension);
    }
}
