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
public class AnticipateOvershootInterpolator extends BaseInterpolator implements NativeInterpolatorFactory {
    private final float mTension;

    public AnticipateOvershootInterpolator() {
        this.mTension = 3.0f;
    }

    public AnticipateOvershootInterpolator(float tension) {
        this.mTension = 1.5f * tension;
    }

    public AnticipateOvershootInterpolator(float tension, float extraTension) {
        this.mTension = tension * extraTension;
    }

    public AnticipateOvershootInterpolator(Context context, AttributeSet attrs) {
        this(context.getResources(), context.getTheme(), attrs);
    }

    public AnticipateOvershootInterpolator(Resources res, Resources.Theme theme, AttributeSet attrs) {
        TypedArray a;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, C3132R.styleable.AnticipateOvershootInterpolator, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, C3132R.styleable.AnticipateOvershootInterpolator);
        }
        this.mTension = a.getFloat(0, 2.0f) * a.getFloat(1, 1.5f);
        setChangingConfiguration(a.getChangingConfigurations());
        a.recycle();
    }

    /* renamed from: a */
    private static float m42a(float t, float s) {
        return t * t * (((1.0f + s) * t) - s);
    }

    /* renamed from: o */
    private static float m41o(float t, float s) {
        return t * t * (((1.0f + s) * t) + s);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return t < 0.5f ? m42a(2.0f * t, this.mTension) * 0.5f : (m41o((t * 2.0f) - 2.0f, this.mTension) + 2.0f) * 0.5f;
    }

    @Override // com.android.internal.view.animation.NativeInterpolatorFactory
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAnticipateOvershootInterpolator(this.mTension);
    }
}
