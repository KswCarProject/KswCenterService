package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.transition.TransitionUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ChangeImageTransform extends Transition {
    private static Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") {
        public void set(ImageView object, Matrix value) {
            object.animateTransform(value);
        }

        public Matrix get(ImageView object) {
            return null;
        }
    };
    private static TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>() {
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            return null;
        }
    };
    private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final String TAG = "ChangeImageTransform";
    private static final String[] sTransitionProperties = {PROPNAME_MATRIX, PROPNAME_BOUNDS};

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000f, code lost:
        r2 = (android.widget.ImageView) r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void captureValues(android.transition.TransitionValues r17) {
        /*
            r16 = this;
            r0 = r17
            android.view.View r1 = r0.view
            boolean r2 = r1 instanceof android.widget.ImageView
            if (r2 == 0) goto L_0x0070
            int r2 = r1.getVisibility()
            if (r2 == 0) goto L_0x000f
            goto L_0x0070
        L_0x000f:
            r2 = r1
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            android.graphics.drawable.Drawable r3 = r2.getDrawable()
            if (r3 != 0) goto L_0x0019
            return
        L_0x0019:
            java.util.Map<java.lang.String, java.lang.Object> r4 = r0.values
            int r5 = r1.getLeft()
            int r6 = r1.getTop()
            int r7 = r1.getRight()
            int r8 = r1.getBottom()
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>(r5, r6, r7, r8)
            java.lang.String r10 = "android:changeImageTransform:bounds"
            r4.put(r10, r9)
            android.widget.ImageView$ScaleType r10 = r2.getScaleType()
            int r11 = r3.getIntrinsicWidth()
            int r12 = r3.getIntrinsicHeight()
            android.widget.ImageView$ScaleType r13 = android.widget.ImageView.ScaleType.FIT_XY
            if (r10 != r13) goto L_0x0060
            if (r11 <= 0) goto L_0x0060
            if (r12 <= 0) goto L_0x0060
            int r13 = r9.width()
            float r13 = (float) r13
            float r14 = (float) r11
            float r13 = r13 / r14
            int r14 = r9.height()
            float r14 = (float) r14
            float r15 = (float) r12
            float r14 = r14 / r15
            android.graphics.Matrix r15 = new android.graphics.Matrix
            r15.<init>()
            r15.setScale(r13, r14)
            goto L_0x0069
        L_0x0060:
            android.graphics.Matrix r15 = new android.graphics.Matrix
            android.graphics.Matrix r13 = r2.getImageMatrix()
            r15.<init>(r13)
        L_0x0069:
            r13 = r15
            java.lang.String r14 = "android:changeImageTransform:matrix"
            r4.put(r14, r13)
            return
        L_0x0070:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.ChangeImageTransform.captureValues(android.transition.TransitionValues):void");
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        Matrix startMatrix = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        Matrix endMatrix = (Matrix) endValues.values.get(PROPNAME_MATRIX);
        if (startBounds == null || endBounds == null || startMatrix == null || endMatrix == null) {
            return null;
        }
        if (startBounds.equals(endBounds) && startMatrix.equals(endMatrix)) {
            return null;
        }
        ImageView imageView = (ImageView) endValues.view;
        Drawable drawable = imageView.getDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        if (drawableWidth <= 0 || drawableHeight <= 0) {
            return createNullAnimator(imageView);
        }
        ANIMATED_TRANSFORM_PROPERTY.set(imageView, startMatrix);
        return createMatrixAnimator(imageView, startMatrix, endMatrix);
    }

    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, (V[]) new Matrix[]{Matrix.IDENTITY_MATRIX, Matrix.IDENTITY_MATRIX});
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix startMatrix, Matrix endMatrix) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), (V[]) new Matrix[]{startMatrix, endMatrix});
    }
}
