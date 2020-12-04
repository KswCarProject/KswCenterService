package com.android.internal.colorextraction.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;

public class ScrimDrawable extends Drawable {
    private static final long COLOR_ANIMATION_DURATION = 2000;
    private static final String TAG = "ScrimDrawable";
    private int mAlpha = 255;
    /* access modifiers changed from: private */
    public ValueAnimator mColorAnimation;
    private int mMainColor;
    private int mMainColorTo;
    private final Paint mPaint = new Paint();

    public ScrimDrawable() {
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int mainColor, boolean animated) {
        if (mainColor != this.mMainColorTo) {
            if (this.mColorAnimation != null && this.mColorAnimation.isRunning()) {
                this.mColorAnimation.cancel();
            }
            this.mMainColorTo = mainColor;
            if (animated) {
                int mainFrom = this.mMainColor;
                ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
                anim.setDuration((long) COLOR_ANIMATION_DURATION);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(mainFrom, mainColor) {
                    private final /* synthetic */ int f$1;
                    private final /* synthetic */ int f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ScrimDrawable.lambda$setColor$0(ScrimDrawable.this, this.f$1, this.f$2, valueAnimator);
                    }
                });
                anim.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation, boolean isReverse) {
                        if (ScrimDrawable.this.mColorAnimation == animation) {
                            ValueAnimator unused = ScrimDrawable.this.mColorAnimation = null;
                        }
                    }
                });
                anim.setInterpolator(new DecelerateInterpolator());
                anim.start();
                this.mColorAnimation = anim;
                return;
            }
            this.mMainColor = mainColor;
            invalidateSelf();
        }
    }

    public static /* synthetic */ void lambda$setColor$0(ScrimDrawable scrimDrawable, int mainFrom, int mainColor, ValueAnimator animation) {
        scrimDrawable.mMainColor = ColorUtils.blendARGB(mainFrom, mainColor, ((Float) animation.getAnimatedValue()).floatValue());
        scrimDrawable.invalidateSelf();
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mAlpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public void setXfermode(Xfermode mode) {
        this.mPaint.setXfermode(mode);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(Canvas canvas) {
        this.mPaint.setColor(this.mMainColor);
        this.mPaint.setAlpha(this.mAlpha);
        canvas.drawRect(getBounds(), this.mPaint);
    }

    @VisibleForTesting
    public int getMainColor() {
        return this.mMainColor;
    }
}
