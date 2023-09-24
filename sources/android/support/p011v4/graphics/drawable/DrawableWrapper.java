package android.support.p011v4.graphics.drawable;

import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapper */
/* loaded from: classes3.dex */
public interface DrawableWrapper {
    Drawable getWrappedDrawable();

    void setWrappedDrawable(Drawable drawable);
}
