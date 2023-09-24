package android.support.p014v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.p014v7.appcompat.C2132R;
import android.util.AttributeSet;
import android.widget.TextView;

@RequiresApi(17)
/* renamed from: android.support.v7.widget.AppCompatTextHelperV17 */
/* loaded from: classes3.dex */
class AppCompatTextHelperV17 extends AppCompatTextHelper {
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableStartTint;

    AppCompatTextHelperV17(TextView view) {
        super(view);
    }

    @Override // android.support.p014v7.widget.AppCompatTextHelper
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        super.loadFromAttributes(attrs, defStyleAttr);
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        TypedArray a = context.obtainStyledAttributes(attrs, C2132R.styleable.AppCompatTextHelper, defStyleAttr, 0);
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableStart)) {
            this.mDrawableStartTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.mDrawableEndTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        a.recycle();
    }

    @Override // android.support.p014v7.widget.AppCompatTextHelper
    void applyCompoundDrawablesTints() {
        super.applyCompoundDrawablesTints();
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableEndTint);
        }
    }
}
