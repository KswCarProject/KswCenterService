package android.support.p014v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.p007os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p014v7.appcompat.C2132R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

@RequiresApi(9)
/* renamed from: android.support.v7.widget.AppCompatTextHelper */
/* loaded from: classes3.dex */
class AppCompatTextHelper {
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mStyle = 0;
    final TextView mView;

    static AppCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    AppCompatTextHelper(TextView view) {
        this.mView = view;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C2132R.styleable.AppCompatTextHelper, defStyleAttr, 0);
        int ap = a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C2132R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, drawableManager, a.getResourceId(C2132R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        a.recycle();
        boolean hasPwdTm = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean allCapsSet = false;
        ColorStateList textColor = null;
        ColorStateList textColorHint = null;
        ColorStateList textColorLink = null;
        if (ap != -1) {
            TintTypedArray a2 = TintTypedArray.obtainStyledAttributes(context, ap, C2132R.styleable.TextAppearance);
            if (!hasPwdTm && a2.hasValue(C2132R.styleable.TextAppearance_textAllCaps)) {
                allCaps = a2.getBoolean(C2132R.styleable.TextAppearance_textAllCaps, false);
                allCapsSet = true;
            }
            updateTypefaceAndStyle(context, a2);
            if (Build.VERSION.SDK_INT < 23) {
                if (a2.hasValue(C2132R.styleable.TextAppearance_android_textColor)) {
                    textColor = a2.getColorStateList(C2132R.styleable.TextAppearance_android_textColor);
                }
                if (a2.hasValue(C2132R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a2.getColorStateList(C2132R.styleable.TextAppearance_android_textColorHint);
                }
                if (a2.hasValue(C2132R.styleable.TextAppearance_android_textColorLink)) {
                    textColorLink = a2.getColorStateList(C2132R.styleable.TextAppearance_android_textColorLink);
                }
            }
            a2.recycle();
        }
        TintTypedArray a3 = TintTypedArray.obtainStyledAttributes(context, attrs, C2132R.styleable.TextAppearance, defStyleAttr, 0);
        if (!hasPwdTm && a3.hasValue(C2132R.styleable.TextAppearance_textAllCaps)) {
            allCapsSet = true;
            allCaps = a3.getBoolean(C2132R.styleable.TextAppearance_textAllCaps, false);
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (a3.hasValue(C2132R.styleable.TextAppearance_android_textColor)) {
                textColor = a3.getColorStateList(C2132R.styleable.TextAppearance_android_textColor);
            }
            if (a3.hasValue(C2132R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a3.getColorStateList(C2132R.styleable.TextAppearance_android_textColorHint);
            }
            if (a3.hasValue(C2132R.styleable.TextAppearance_android_textColorLink)) {
                textColorLink = a3.getColorStateList(C2132R.styleable.TextAppearance_android_textColorLink);
            }
        }
        updateTypefaceAndStyle(context, a3);
        a3.recycle();
        if (textColor != null) {
            this.mView.setTextColor(textColor);
        }
        if (textColorHint != null) {
            this.mView.setHintTextColor(textColorHint);
        }
        if (textColorLink != null) {
            this.mView.setLinkTextColor(textColorLink);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= 26 && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            int[] autoSizeTextSizesInPx = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
            if (autoSizeTextSizesInPx.length > 0) {
                if (this.mView.getAutoSizeStepGranularity() == -1.0f) {
                    this.mView.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextSizesInPx, 0);
                } else {
                    this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
                }
            }
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray a) {
        this.mStyle = a.getInt(C2132R.styleable.TextAppearance_android_textStyle, this.mStyle);
        if (a.hasValue(C2132R.styleable.TextAppearance_android_fontFamily) || a.hasValue(C2132R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int fontFamilyId = a.hasValue(C2132R.styleable.TextAppearance_android_fontFamily) ? C2132R.styleable.TextAppearance_android_fontFamily : C2132R.styleable.TextAppearance_fontFamily;
            if (!context.isRestricted()) {
                try {
                    this.mFontTypeface = a.getFont(fontFamilyId, this.mStyle, this.mView);
                } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                }
            }
            if (this.mFontTypeface == null) {
                String fontFamilyName = a.getString(fontFamilyId);
                this.mFontTypeface = Typeface.create(fontFamilyName, this.mStyle);
            }
        }
    }

    void onSetTextAppearance(Context context, int resId) {
        ColorStateList textColor;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, C2132R.styleable.TextAppearance);
        if (a.hasValue(C2132R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C2132R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && a.hasValue(C2132R.styleable.TextAppearance_android_textColor) && (textColor = a.getColorStateList(C2132R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(textColor);
        }
        updateTypefaceAndStyle(context, a);
        a.recycle();
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
    }

    void setAllCaps(boolean allCaps) {
        this.mView.setAllCaps(allCaps);
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
    }

    final void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList != null) {
            TintInfo tintInfo = new TintInfo();
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = tintList;
            return tintInfo;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT < 26) {
            autoSizeText();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void setTextSize(int unit, float size) {
        if (Build.VERSION.SDK_INT < 26 && !isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int unit, float size) {
        this.mAutoSizeTextHelper.setTextSizeInternal(unit, size);
    }

    void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }
}
