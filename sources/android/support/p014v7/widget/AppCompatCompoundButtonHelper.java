package android.support.p014v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.p007os.Build;
import android.support.annotation.Nullable;
import android.support.p011v4.graphics.drawable.DrawableCompat;
import android.support.p011v4.widget.CompoundButtonCompat;
import android.support.p014v7.appcompat.C2132R;
import android.support.p014v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/* renamed from: android.support.v7.widget.AppCompatCompoundButtonHelper */
/* loaded from: classes3.dex */
class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private PorterDuff.Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    /* renamed from: android.support.v7.widget.AppCompatCompoundButtonHelper$DirectSetButtonDrawableInterface */
    /* loaded from: classes3.dex */
    interface DirectSetButtonDrawableInterface {
        void setButtonDrawable(Drawable drawable);
    }

    AppCompatCompoundButtonHelper(CompoundButton view) {
        this.mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        int resourceId;
        TypedArray a = this.mView.getContext().obtainStyledAttributes(attrs, C2132R.styleable.CompoundButton, defStyleAttr, 0);
        try {
            if (a.hasValue(C2132R.styleable.CompoundButton_android_button) && (resourceId = a.getResourceId(C2132R.styleable.CompoundButton_android_button, 0)) != 0) {
                this.mView.setButtonDrawable(AppCompatResources.getDrawable(this.mView.getContext(), resourceId));
            }
            if (a.hasValue(C2132R.styleable.CompoundButton_buttonTint)) {
                CompoundButtonCompat.setButtonTintList(this.mView, a.getColorStateList(C2132R.styleable.CompoundButton_buttonTint));
            }
            if (a.hasValue(C2132R.styleable.CompoundButton_buttonTintMode)) {
                CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(C2132R.styleable.CompoundButton_buttonTintMode, -1), null));
            }
        } finally {
            a.recycle();
        }
    }

    void setSupportButtonTintList(ColorStateList tint) {
        this.mButtonTintList = tint;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    void setSupportButtonTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.mButtonTintMode = tintMode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }

    PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    void onSetButtonDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
            return;
        }
        this.mSkipNextApply = true;
        applyButtonTint();
    }

    void applyButtonTint() {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable buttonDrawable2 = DrawableCompat.wrap(buttonDrawable).mutate();
                if (this.mHasButtonTint) {
                    DrawableCompat.setTintList(buttonDrawable2, this.mButtonTintList);
                }
                if (this.mHasButtonTintMode) {
                    DrawableCompat.setTintMode(buttonDrawable2, this.mButtonTintMode);
                }
                if (buttonDrawable2.isStateful()) {
                    buttonDrawable2.setState(this.mView.getDrawableState());
                }
                this.mView.setButtonDrawable(buttonDrawable2);
            }
        }
    }

    int getCompoundPaddingLeft(int superValue) {
        Drawable buttonDrawable;
        if (Build.VERSION.SDK_INT < 17 && (buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView)) != null) {
            return superValue + buttonDrawable.getIntrinsicWidth();
        }
        return superValue;
    }
}