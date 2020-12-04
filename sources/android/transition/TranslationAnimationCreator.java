package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.transition.Transition;
import android.view.View;
import com.android.internal.R;

class TranslationAnimationCreator {
    TranslationAnimationCreator() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: int[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.animation.Animator createAnimation(android.view.View r23, android.transition.TransitionValues r24, int r25, int r26, float r27, float r28, float r29, float r30, android.animation.TimeInterpolator r31, android.transition.Transition r32) {
        /*
            r8 = r23
            r9 = r24
            r10 = r29
            r11 = r30
            float r12 = r23.getTranslationX()
            float r13 = r23.getTranslationY()
            android.view.View r0 = r9.view
            r1 = 16909489(0x10204b1, float:2.3880595E-38)
            java.lang.Object r0 = r0.getTag(r1)
            r14 = r0
            int[] r14 = (int[]) r14
            if (r14 == 0) goto L_0x002f
            r0 = 0
            r0 = r14[r0]
            int r0 = r0 - r25
            float r0 = (float) r0
            float r0 = r0 + r12
            r1 = 1
            r1 = r14[r1]
            int r1 = r1 - r26
            float r1 = (float) r1
            float r1 = r1 + r13
            r7 = r0
            r6 = r1
            goto L_0x0033
        L_0x002f:
            r7 = r27
            r6 = r28
        L_0x0033:
            float r0 = r7 - r12
            int r0 = java.lang.Math.round(r0)
            int r15 = r25 + r0
            float r0 = r6 - r13
            int r0 = java.lang.Math.round(r0)
            int r16 = r26 + r0
            r8.setTranslationX(r7)
            r8.setTranslationY(r6)
            int r0 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x0053
            int r0 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0053
            r0 = 0
            return r0
        L_0x0053:
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            r5 = r0
            r5.moveTo(r7, r6)
            r5.lineTo(r10, r11)
            android.util.Property<android.view.View, java.lang.Float> r0 = android.view.View.TRANSLATION_X
            android.util.Property<android.view.View, java.lang.Float> r1 = android.view.View.TRANSLATION_Y
            android.animation.ObjectAnimator r4 = android.animation.ObjectAnimator.ofFloat(r8, r0, r1, (android.graphics.Path) r5)
            android.transition.TranslationAnimationCreator$TransitionPositionListener r17 = new android.transition.TranslationAnimationCreator$TransitionPositionListener
            android.view.View r2 = r9.view
            r18 = 0
            r0 = r17
            r1 = r23
            r3 = r15
            r19 = r4
            r4 = r16
            r20 = r5
            r5 = r12
            r21 = r6
            r6 = r13
            r22 = r7
            r7 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r1 = r32
            r1.addListener(r0)
            r2 = r19
            r2.addListener(r0)
            r2.addPauseListener(r0)
            r3 = r31
            r2.setInterpolator(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.TranslationAnimationCreator.createAnimation(android.view.View, android.transition.TransitionValues, int, int, float, float, float, float, android.animation.TimeInterpolator, android.transition.Transition):android.animation.Animator");
    }

    private static class TransitionPositionListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        private final View mMovingView;
        private float mPausedX;
        private float mPausedY;
        private final int mStartX;
        private final int mStartY;
        private final float mTerminalX;
        private final float mTerminalY;
        private int[] mTransitionPosition;
        private final View mViewInHierarchy;

        private TransitionPositionListener(View movingView, View viewInHierarchy, int startX, int startY, float terminalX, float terminalY) {
            this.mMovingView = movingView;
            this.mViewInHierarchy = viewInHierarchy;
            this.mStartX = startX - Math.round(this.mMovingView.getTranslationX());
            this.mStartY = startY - Math.round(this.mMovingView.getTranslationY());
            this.mTerminalX = terminalX;
            this.mTerminalY = terminalY;
            this.mTransitionPosition = (int[]) this.mViewInHierarchy.getTag(R.id.transitionPosition);
            if (this.mTransitionPosition != null) {
                this.mViewInHierarchy.setTagInternal(R.id.transitionPosition, (Object) null);
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (this.mTransitionPosition == null) {
                this.mTransitionPosition = new int[2];
            }
            this.mTransitionPosition[0] = Math.round(((float) this.mStartX) + this.mMovingView.getTranslationX());
            this.mTransitionPosition[1] = Math.round(((float) this.mStartY) + this.mMovingView.getTranslationY());
            this.mViewInHierarchy.setTagInternal(R.id.transitionPosition, this.mTransitionPosition);
        }

        public void onAnimationEnd(Animator animator) {
        }

        public void onAnimationPause(Animator animator) {
            this.mPausedX = this.mMovingView.getTranslationX();
            this.mPausedY = this.mMovingView.getTranslationY();
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
        }

        public void onAnimationResume(Animator animator) {
            this.mMovingView.setTranslationX(this.mPausedX);
            this.mMovingView.setTranslationY(this.mPausedY);
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionEnd(Transition transition) {
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
            transition.removeListener(this);
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }
}
