package android.support.p011v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p011v4.graphics.drawable.DrawableWrapperApi14;

@RequiresApi(19)
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi19 */
/* loaded from: classes3.dex */
class DrawableWrapperApi19 extends DrawableWrapperApi14 {
    DrawableWrapperApi19(Drawable drawable) {
        super(drawable);
    }

    DrawableWrapperApi19(DrawableWrapperApi14.DrawableWrapperState state, Resources resources) {
        super(state, resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean mirrored) {
        this.mDrawable.setAutoMirrored(mirrored);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14
    @NonNull
    DrawableWrapperApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi19$DrawableWrapperStateKitKat */
    /* loaded from: classes3.dex */
    private static class DrawableWrapperStateKitKat extends DrawableWrapperApi14.DrawableWrapperState {
        DrawableWrapperStateKitKat(@Nullable DrawableWrapperApi14.DrawableWrapperState orig, @Nullable Resources res) {
            super(orig, res);
        }

        @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(@Nullable Resources res) {
            return new DrawableWrapperApi19(this, res);
        }
    }
}
