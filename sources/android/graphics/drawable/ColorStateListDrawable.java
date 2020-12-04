package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;

public class ColorStateListDrawable extends Drawable implements Drawable.Callback {
    private ColorDrawable mColorDrawable;
    private boolean mMutated;
    private ColorStateListDrawableState mState;

    public ColorStateListDrawable() {
        this.mMutated = false;
        this.mState = new ColorStateListDrawableState();
        initializeColorDrawable();
    }

    public ColorStateListDrawable(ColorStateList colorStateList) {
        this.mMutated = false;
        this.mState = new ColorStateListDrawableState();
        initializeColorDrawable();
        setColorStateList(colorStateList);
    }

    public void draw(Canvas canvas) {
        this.mColorDrawable.draw(canvas);
    }

    public int getAlpha() {
        return this.mColorDrawable.getAlpha();
    }

    public boolean isStateful() {
        return this.mState.isStateful();
    }

    public boolean hasFocusStateSpecified() {
        return this.mState.hasFocusStateSpecified();
    }

    public Drawable getCurrent() {
        return this.mColorDrawable;
    }

    public void applyTheme(Resources.Theme t) {
        super.applyTheme(t);
        if (this.mState.mColor != null) {
            setColorStateList(this.mState.mColor.obtainForTheme(t));
        }
        if (this.mState.mTint != null) {
            setTintList(this.mState.mTint.obtainForTheme(t));
        }
    }

    public boolean canApplyTheme() {
        return super.canApplyTheme() || this.mState.canApplyTheme();
    }

    public void setAlpha(int alpha) {
        this.mState.mAlpha = alpha;
        onStateChange(getState());
    }

    public void clearAlpha() {
        this.mState.mAlpha = -1;
        onStateChange(getState());
    }

    public void setTintList(ColorStateList tint) {
        this.mState.mTint = tint;
        this.mColorDrawable.setTintList(tint);
        onStateChange(getState());
    }

    public void setTintBlendMode(BlendMode blendMode) {
        this.mState.mBlendMode = blendMode;
        this.mColorDrawable.setTintBlendMode(blendMode);
        onStateChange(getState());
    }

    public ColorFilter getColorFilter() {
        return this.mColorDrawable.getColorFilter();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorDrawable.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return this.mColorDrawable.getOpacity();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mColorDrawable.setBounds(bounds);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        if (this.mState.mColor == null) {
            return false;
        }
        int color = this.mState.mColor.getColorForState(state, this.mState.mColor.getDefaultColor());
        if (this.mState.mAlpha != -1) {
            color = (16777215 & color) | (MathUtils.constrain(this.mState.mAlpha, 0, 255) << 24);
        }
        if (color == this.mColorDrawable.getColor()) {
            return this.mColorDrawable.setState(state);
        }
        this.mColorDrawable.setColor(color);
        this.mColorDrawable.setState(state);
        return true;
    }

    public void invalidateDrawable(Drawable who) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().unscheduleDrawable(this, what);
        }
    }

    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |= getChangingConfigurations() & (~this.mState.getChangingConfigurations());
        return this.mState;
    }

    public ColorStateList getColorStateList() {
        if (this.mState.mColor == null) {
            return ColorStateList.valueOf(this.mColorDrawable.getColor());
        }
        return this.mState.mColor;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.getChangingConfigurations();
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new ColorStateListDrawableState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    public void setColorStateList(ColorStateList colorStateList) {
        this.mState.mColor = colorStateList;
        onStateChange(getState());
    }

    static final class ColorStateListDrawableState extends Drawable.ConstantState {
        int mAlpha = -1;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations = 0;
        ColorStateList mColor = null;
        ColorStateList mTint = null;

        ColorStateListDrawableState() {
        }

        ColorStateListDrawableState(ColorStateListDrawableState state) {
            this.mColor = state.mColor;
            this.mTint = state.mTint;
            this.mAlpha = state.mAlpha;
            this.mBlendMode = state.mBlendMode;
            this.mChangingConfigurations = state.mChangingConfigurations;
        }

        public Drawable newDrawable() {
            return new ColorStateListDrawable(this);
        }

        public int getChangingConfigurations() {
            int i = 0;
            int changingConfigurations = this.mChangingConfigurations | (this.mColor != null ? this.mColor.getChangingConfigurations() : 0);
            if (this.mTint != null) {
                i = this.mTint.getChangingConfigurations();
            }
            return changingConfigurations | i;
        }

        public boolean isStateful() {
            return (this.mColor != null && this.mColor.isStateful()) || (this.mTint != null && this.mTint.isStateful());
        }

        public boolean hasFocusStateSpecified() {
            return (this.mColor != null && this.mColor.hasFocusStateSpecified()) || (this.mTint != null && this.mTint.hasFocusStateSpecified());
        }

        public boolean canApplyTheme() {
            return (this.mColor != null && this.mColor.canApplyTheme()) || (this.mTint != null && this.mTint.canApplyTheme());
        }
    }

    private ColorStateListDrawable(ColorStateListDrawableState state) {
        this.mMutated = false;
        this.mState = state;
        initializeColorDrawable();
    }

    private void initializeColorDrawable() {
        this.mColorDrawable = new ColorDrawable();
        this.mColorDrawable.setCallback(this);
        if (this.mState.mTint != null) {
            this.mColorDrawable.setTintList(this.mState.mTint);
        }
        if (this.mState.mBlendMode != DEFAULT_BLEND_MODE) {
            this.mColorDrawable.setTintBlendMode(this.mState.mBlendMode);
        }
    }
}
