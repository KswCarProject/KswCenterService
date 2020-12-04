package android.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TransitionUtils {
    private static int MAX_IMAGE_SIZE = 1048576;

    static Animator mergeAnimators(Animator animator1, Animator animator2) {
        if (animator1 == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator1;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);
        return animatorSet;
    }

    public static Transition mergeTransitions(Transition... transitions) {
        int nonNullIndex = -1;
        int count = 0;
        for (int i = 0; i < transitions.length; i++) {
            if (transitions[i] != null) {
                count++;
                nonNullIndex = i;
            }
        }
        if (count == 0) {
            return null;
        }
        if (count == 1) {
            return transitions[nonNullIndex];
        }
        TransitionSet transitionSet = new TransitionSet();
        for (int i2 = 0; i2 < transitions.length; i2++) {
            if (transitions[i2] != null) {
                transitionSet.addTransition(transitions[i2]);
            }
        }
        return transitionSet;
    }

    public static View copyViewImage(ViewGroup sceneRoot, View view, View parent) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
        view.transformMatrixToGlobal(matrix);
        sceneRoot.transformMatrixToLocal(matrix);
        RectF bounds = new RectF(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
        matrix.mapRect(bounds);
        int left = Math.round(bounds.left);
        int top = Math.round(bounds.top);
        int right = Math.round(bounds.right);
        int bottom = Math.round(bounds.bottom);
        ImageView copy = new ImageView(view.getContext());
        copy.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bitmap = createViewBitmap(view, matrix, bounds, sceneRoot);
        if (bitmap != null) {
            copy.setImageBitmap(bitmap);
        }
        copy.measure(View.MeasureSpec.makeMeasureSpec(right - left, 1073741824), View.MeasureSpec.makeMeasureSpec(bottom - top, 1073741824));
        copy.layout(left, top, right, bottom);
        return copy;
    }

    public static Bitmap createDrawableBitmap(Drawable drawable, View hostView) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        float scale = Math.min(1.0f, ((float) MAX_IMAGE_SIZE) / ((float) (width * height)));
        if ((drawable instanceof BitmapDrawable) && scale == 1.0f) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Picture picture = new Picture();
        Canvas canvas = picture.beginRecording(width, height);
        Rect existingBounds = drawable.getBounds();
        int left = existingBounds.left;
        int top = existingBounds.top;
        int right = existingBounds.right;
        int bottom = existingBounds.bottom;
        drawable.setBounds(0, 0, (int) (((float) width) * scale), (int) (((float) height) * scale));
        drawable.draw(canvas);
        drawable.setBounds(left, top, right, bottom);
        picture.endRecording();
        return Bitmap.createBitmap(picture);
    }

    /* JADX WARNING: type inference failed for: r3v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap createViewBitmap(android.view.View r9, android.graphics.Matrix r10, android.graphics.RectF r11, android.view.ViewGroup r12) {
        /*
            boolean r0 = r9.isAttachedToWindow()
            r0 = r0 ^ 1
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x0028
            if (r12 == 0) goto L_0x0026
            boolean r3 = r12.isAttachedToWindow()
            if (r3 != 0) goto L_0x0013
            goto L_0x0026
        L_0x0013:
            android.view.ViewParent r3 = r9.getParent()
            r1 = r3
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r2 = r1.indexOfChild(r9)
            android.view.ViewGroupOverlay r3 = r12.getOverlay()
            r3.add(r9)
            goto L_0x0028
        L_0x0026:
            r3 = 0
            return r3
        L_0x0028:
            r3 = 0
            float r4 = r11.width()
            int r4 = java.lang.Math.round(r4)
            float r5 = r11.height()
            int r5 = java.lang.Math.round(r5)
            if (r4 <= 0) goto L_0x0072
            if (r5 <= 0) goto L_0x0072
            r6 = 1065353216(0x3f800000, float:1.0)
            int r7 = MAX_IMAGE_SIZE
            float r7 = (float) r7
            int r8 = r4 * r5
            float r8 = (float) r8
            float r7 = r7 / r8
            float r6 = java.lang.Math.min(r6, r7)
            float r7 = (float) r4
            float r7 = r7 * r6
            int r4 = (int) r7
            float r7 = (float) r5
            float r7 = r7 * r6
            int r5 = (int) r7
            float r7 = r11.left
            float r7 = -r7
            float r8 = r11.top
            float r8 = -r8
            r10.postTranslate(r7, r8)
            r10.postScale(r6, r6)
            android.graphics.Picture r7 = new android.graphics.Picture
            r7.<init>()
            android.graphics.Canvas r8 = r7.beginRecording(r4, r5)
            r8.concat(r10)
            r9.draw(r8)
            r7.endRecording()
            android.graphics.Bitmap r3 = android.graphics.Bitmap.createBitmap((android.graphics.Picture) r7)
        L_0x0072:
            if (r0 == 0) goto L_0x007e
            android.view.ViewGroupOverlay r6 = r12.getOverlay()
            r6.remove(r9)
            r1.addView((android.view.View) r9, (int) r2)
        L_0x007e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TransitionUtils.createViewBitmap(android.view.View, android.graphics.Matrix, android.graphics.RectF, android.view.ViewGroup):android.graphics.Bitmap");
    }

    public static class MatrixEvaluator implements TypeEvaluator<Matrix> {
        float[] mTempEndValues = new float[9];
        Matrix mTempMatrix = new Matrix();
        float[] mTempStartValues = new float[9];

        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            startValue.getValues(this.mTempStartValues);
            endValue.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; i++) {
                this.mTempEndValues[i] = this.mTempStartValues[i] + (fraction * (this.mTempEndValues[i] - this.mTempStartValues[i]));
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }
}
