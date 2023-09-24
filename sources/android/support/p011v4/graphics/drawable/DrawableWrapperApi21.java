package android.support.p011v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.p007os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p011v4.graphics.drawable.DrawableWrapperApi14;
import android.util.Log;
import java.lang.reflect.Method;

@RequiresApi(21)
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi21 */
/* loaded from: classes3.dex */
class DrawableWrapperApi21 extends DrawableWrapperApi19 {
    private static final String TAG = "DrawableWrapperApi21";
    private static Method sIsProjectedDrawableMethod;

    DrawableWrapperApi21(Drawable drawable) {
        super(drawable);
        findAndCacheIsProjectedDrawableMethod();
    }

    DrawableWrapperApi21(DrawableWrapperApi14.DrawableWrapperState state, Resources resources) {
        super(state, resources);
        findAndCacheIsProjectedDrawableMethod();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float x, float y) {
        this.mDrawable.setHotspot(x, y);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        this.mDrawable.setHotspotBounds(left, top, right, bottom);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        this.mDrawable.getOutline(outline);
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        return this.mDrawable.getDirtyBounds();
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14, android.graphics.drawable.Drawable
    public void setTintList(ColorStateList tint) {
        if (isCompatTintEnabled()) {
            super.setTintList(tint);
        } else {
            this.mDrawable.setTintList(tint);
        }
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14, android.graphics.drawable.Drawable, android.support.p011v4.graphics.drawable.TintAwareDrawable
    public void setTint(int tintColor) {
        if (isCompatTintEnabled()) {
            super.setTint(tintColor);
        } else {
            this.mDrawable.setTint(tintColor);
        }
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14, android.graphics.drawable.Drawable, android.support.p011v4.graphics.drawable.TintAwareDrawable
    public void setTintMode(PorterDuff.Mode tintMode) {
        if (isCompatTintEnabled()) {
            super.setTintMode(tintMode);
        } else {
            this.mDrawable.setTintMode(tintMode);
        }
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14, android.graphics.drawable.Drawable
    public boolean setState(int[] stateSet) {
        if (super.setState(stateSet)) {
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14
    protected boolean isCompatTintEnabled() {
        if (Build.VERSION.SDK_INT == 21) {
            Drawable drawable = this.mDrawable;
            return (drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isProjected() {
        if (this.mDrawable != null && sIsProjectedDrawableMethod != null) {
            try {
                return ((Boolean) sIsProjectedDrawableMethod.invoke(this.mDrawable, new Object[0])).booleanValue();
            } catch (Exception ex) {
                Log.m63w(TAG, "Error calling Drawable#isProjected() method", ex);
            }
        }
        return false;
    }

    @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi19, android.support.p011v4.graphics.drawable.DrawableWrapperApi14
    @NonNull
    DrawableWrapperApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateLollipop(this.mState, null);
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi21$DrawableWrapperStateLollipop */
    /* loaded from: classes3.dex */
    private static class DrawableWrapperStateLollipop extends DrawableWrapperApi14.DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable DrawableWrapperApi14.DrawableWrapperState orig, @Nullable Resources res) {
            super(orig, res);
        }

        @Override // android.support.p011v4.graphics.drawable.DrawableWrapperApi14.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(@Nullable Resources res) {
            return new DrawableWrapperApi21(this, res);
        }
    }

    private void findAndCacheIsProjectedDrawableMethod() {
        if (sIsProjectedDrawableMethod == null) {
            try {
                sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception ex) {
                Log.m63w(TAG, "Failed to retrieve Drawable#isProjected() method", ex);
            }
        }
    }
}
