package android.transition;

import android.widget.EditText;
import android.widget.TextView;

public class ChangeText extends Transition {
    public static final int CHANGE_BEHAVIOR_IN = 2;
    public static final int CHANGE_BEHAVIOR_KEEP = 0;
    public static final int CHANGE_BEHAVIOR_OUT = 1;
    public static final int CHANGE_BEHAVIOR_OUT_IN = 3;
    private static final String LOG_TAG = "TextChange";
    private static final String PROPNAME_TEXT = "android:textchange:text";
    private static final String PROPNAME_TEXT_COLOR = "android:textchange:textColor";
    private static final String PROPNAME_TEXT_SELECTION_END = "android:textchange:textSelectionEnd";
    private static final String PROPNAME_TEXT_SELECTION_START = "android:textchange:textSelectionStart";
    private static final String[] sTransitionProperties = {PROPNAME_TEXT, PROPNAME_TEXT_SELECTION_START, PROPNAME_TEXT_SELECTION_END};
    /* access modifiers changed from: private */
    public int mChangeBehavior = 0;

    public ChangeText setChangeBehavior(int changeBehavior) {
        if (changeBehavior >= 0 && changeBehavior <= 3) {
            this.mChangeBehavior = changeBehavior;
        }
        return this;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public int getChangeBehavior() {
        return this.mChangeBehavior;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            TextView textview = (TextView) transitionValues.view;
            transitionValues.values.put(PROPNAME_TEXT, textview.getText());
            if (textview instanceof EditText) {
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_START, Integer.valueOf(textview.getSelectionStart()));
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_END, Integer.valueOf(textview.getSelectionEnd()));
            }
            if (this.mChangeBehavior > 0) {
                transitionValues.values.put(PROPNAME_TEXT_COLOR, Integer.valueOf(textview.getCurrentTextColor()));
            }
        }
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x01b2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator createAnimator(android.view.ViewGroup r27, android.transition.TransitionValues r28, android.transition.TransitionValues r29) {
        /*
            r26 = this;
            r10 = r26
            r11 = r28
            r12 = r29
            r0 = 0
            if (r11 == 0) goto L_0x01de
            if (r12 == 0) goto L_0x01de
            android.view.View r1 = r11.view
            boolean r1 = r1 instanceof android.widget.TextView
            if (r1 == 0) goto L_0x01de
            android.view.View r1 = r12.view
            boolean r1 = r1 instanceof android.widget.TextView
            if (r1 != 0) goto L_0x0019
            goto L_0x01de
        L_0x0019:
            android.view.View r1 = r12.view
            r13 = r1
            android.widget.TextView r13 = (android.widget.TextView) r13
            java.util.Map<java.lang.String, java.lang.Object> r14 = r11.values
            java.util.Map<java.lang.String, java.lang.Object> r15 = r12.values
            java.lang.String r1 = "android:textchange:text"
            java.lang.Object r1 = r14.get(r1)
            if (r1 == 0) goto L_0x0033
            java.lang.String r1 = "android:textchange:text"
            java.lang.Object r1 = r14.get(r1)
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            goto L_0x0035
        L_0x0033:
            java.lang.String r1 = ""
        L_0x0035:
            r9 = r1
            java.lang.String r1 = "android:textchange:text"
            java.lang.Object r1 = r15.get(r1)
            if (r1 == 0) goto L_0x0047
            java.lang.String r1 = "android:textchange:text"
            java.lang.Object r1 = r15.get(r1)
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            goto L_0x0049
        L_0x0047:
            java.lang.String r1 = ""
        L_0x0049:
            r8 = r1
            boolean r1 = r13 instanceof android.widget.EditText
            r2 = -1
            if (r1 == 0) goto L_0x00ac
            java.lang.String r1 = "android:textchange:textSelectionStart"
            java.lang.Object r1 = r14.get(r1)
            if (r1 == 0) goto L_0x0064
            java.lang.String r1 = "android:textchange:textSelectionStart"
            java.lang.Object r1 = r14.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            goto L_0x0065
        L_0x0064:
            r1 = r2
        L_0x0065:
            java.lang.String r3 = "android:textchange:textSelectionEnd"
            java.lang.Object r3 = r14.get(r3)
            if (r3 == 0) goto L_0x007a
            java.lang.String r3 = "android:textchange:textSelectionEnd"
            java.lang.Object r3 = r14.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            goto L_0x007b
        L_0x007a:
            r3 = r1
        L_0x007b:
            java.lang.String r4 = "android:textchange:textSelectionStart"
            java.lang.Object r4 = r15.get(r4)
            if (r4 == 0) goto L_0x0090
            java.lang.String r2 = "android:textchange:textSelectionStart"
            java.lang.Object r2 = r15.get(r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
        L_0x0090:
            java.lang.String r4 = "android:textchange:textSelectionEnd"
            java.lang.Object r4 = r15.get(r4)
            if (r4 == 0) goto L_0x00a5
            java.lang.String r4 = "android:textchange:textSelectionEnd"
            java.lang.Object r4 = r15.get(r4)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            goto L_0x00a6
        L_0x00a5:
            r4 = r2
        L_0x00a6:
            r16 = r2
            r7 = r3
            r17 = r4
            goto L_0x00b4
        L_0x00ac:
            r1 = r2
            r3 = r2
            r4 = r2
            r17 = r1
            r16 = r3
            r7 = r4
        L_0x00b4:
            r6 = r1
            boolean r1 = r9.equals(r8)
            if (r1 != 0) goto L_0x01d4
            int r0 = r10.mChangeBehavior
            r5 = 2
            if (r0 == r5) goto L_0x00cd
            r13.setText((java.lang.CharSequence) r9)
            boolean r0 = r13 instanceof android.widget.EditText
            if (r0 == 0) goto L_0x00cd
            r0 = r13
            android.widget.EditText r0 = (android.widget.EditText) r0
            r10.setSelection(r0, r6, r7)
        L_0x00cd:
            int r0 = r10.mChangeBehavior
            r18 = 0
            if (r0 != 0) goto L_0x00fd
            r19 = r18
            float[] r0 = new float[r5]
            r0 = {0, 1065353216} // fill-array
            android.animation.ValueAnimator r5 = android.animation.ValueAnimator.ofFloat(r0)
            android.transition.ChangeText$1 r4 = new android.transition.ChangeText$1
            r0 = r4
            r1 = r26
            r2 = r9
            r3 = r13
            r20 = r7
            r7 = r4
            r4 = r8
            r11 = r5
            r5 = r16
            r21 = r6
            r6 = r17
            r0.<init>(r2, r3, r4, r5, r6)
            r11.addListener(r7)
            r0 = r11
            r24 = r14
            r22 = r18
            goto L_0x01b7
        L_0x00fd:
            r21 = r6
            r20 = r7
            java.lang.String r0 = "android:textchange:textColor"
            java.lang.Object r0 = r14.get(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r11 = r0.intValue()
            java.lang.String r0 = "android:textchange:textColor"
            java.lang.Object r0 = r15.get(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r7 = r0.intValue()
            r0 = 0
            r19 = 0
            int r1 = r10.mChangeBehavior
            r6 = 3
            r4 = 1
            if (r1 == r6) goto L_0x0132
            int r1 = r10.mChangeBehavior
            if (r1 != r4) goto L_0x0127
            goto L_0x0132
        L_0x0127:
            r12 = r0
            r23 = r4
            r25 = r7
            r22 = r11
            r24 = r14
            r14 = r6
            goto L_0x0166
        L_0x0132:
            int[] r1 = new int[r5]
            int r2 = android.graphics.Color.alpha((int) r11)
            r1[r18] = r2
            r1[r4] = r18
            android.animation.ValueAnimator r3 = android.animation.ValueAnimator.ofInt(r1)
            android.transition.ChangeText$2 r0 = new android.transition.ChangeText$2
            r0.<init>(r13, r11)
            r3.addUpdateListener(r0)
            android.transition.ChangeText$3 r2 = new android.transition.ChangeText$3
            r0 = r2
            r1 = r26
            r22 = r11
            r11 = r2
            r2 = r9
            r12 = r3
            r3 = r13
            r23 = r4
            r4 = r8
            r24 = r14
            r14 = r5
            r5 = r16
            r14 = r6
            r6 = r17
            r25 = r7
            r0.<init>(r2, r3, r4, r5, r6, r7)
            r12.addListener(r11)
        L_0x0166:
            int r0 = r10.mChangeBehavior
            if (r0 == r14) goto L_0x0175
            int r0 = r10.mChangeBehavior
            r1 = 2
            if (r0 != r1) goto L_0x0170
            goto L_0x0176
        L_0x0170:
            r5 = r19
            r2 = r25
            goto L_0x0197
        L_0x0175:
            r1 = 2
        L_0x0176:
            int[] r0 = new int[r1]
            r0[r18] = r18
            int r1 = android.graphics.Color.alpha((int) r25)
            r0[r23] = r1
            android.animation.ValueAnimator r0 = android.animation.ValueAnimator.ofInt(r0)
            android.transition.ChangeText$4 r1 = new android.transition.ChangeText$4
            r2 = r25
            r1.<init>(r13, r2)
            r0.addUpdateListener(r1)
            android.transition.ChangeText$5 r1 = new android.transition.ChangeText$5
            r1.<init>(r13, r2)
            r0.addListener(r1)
            r5 = r0
        L_0x0197:
            if (r12 == 0) goto L_0x01b0
            if (r5 == 0) goto L_0x01b0
            android.animation.AnimatorSet r0 = new android.animation.AnimatorSet
            r0.<init>()
            r1 = r0
            android.animation.AnimatorSet r1 = (android.animation.AnimatorSet) r1
            r3 = 2
            android.animation.Animator[] r3 = new android.animation.Animator[r3]
            r3[r18] = r12
            r3[r23] = r5
            r1.playSequentially((android.animation.Animator[]) r3)
        L_0x01ad:
            r19 = r2
            goto L_0x01b7
        L_0x01b0:
            if (r12 == 0) goto L_0x01b4
            r0 = r12
            goto L_0x01ad
        L_0x01b4:
            r19 = r2
            r0 = r5
        L_0x01b7:
            r11 = r0
            android.transition.ChangeText$6 r12 = new android.transition.ChangeText$6
            r0 = r12
            r1 = r26
            r2 = r13
            r3 = r8
            r4 = r16
            r5 = r17
            r6 = r19
            r7 = r9
            r14 = r8
            r8 = r21
            r18 = r9
            r9 = r20
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            r10.addListener(r0)
            return r11
        L_0x01d4:
            r21 = r6
            r20 = r7
            r18 = r9
            r24 = r14
            r14 = r8
            return r0
        L_0x01de:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.ChangeText.createAnimator(android.view.ViewGroup, android.transition.TransitionValues, android.transition.TransitionValues):android.animation.Animator");
    }

    /* access modifiers changed from: private */
    public void setSelection(EditText editText, int start, int end) {
        if (start >= 0 && end >= 0) {
            editText.setSelection(start, end);
        }
    }
}
