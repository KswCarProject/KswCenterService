package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.transition.Transition;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;

/* loaded from: classes4.dex */
public class ChangeText extends Transition {
    public static final int CHANGE_BEHAVIOR_IN = 2;
    public static final int CHANGE_BEHAVIOR_KEEP = 0;
    public static final int CHANGE_BEHAVIOR_OUT = 1;
    public static final int CHANGE_BEHAVIOR_OUT_IN = 3;
    private static final String LOG_TAG = "TextChange";
    private static final String PROPNAME_TEXT_COLOR = "android:textchange:textColor";
    private int mChangeBehavior = 0;
    private static final String PROPNAME_TEXT = "android:textchange:text";
    private static final String PROPNAME_TEXT_SELECTION_START = "android:textchange:textSelectionStart";
    private static final String PROPNAME_TEXT_SELECTION_END = "android:textchange:textSelectionEnd";
    private static final String[] sTransitionProperties = {PROPNAME_TEXT, PROPNAME_TEXT_SELECTION_START, PROPNAME_TEXT_SELECTION_END};

    public ChangeText setChangeBehavior(int changeBehavior) {
        if (changeBehavior >= 0 && changeBehavior <= 3) {
            this.mChangeBehavior = changeBehavior;
        }
        return this;
    }

    @Override // android.transition.Transition
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

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b4  */
    @Override // android.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        int startSelectionStart;
        int endSelectionEnd;
        int endSelectionStart;
        int startSelectionEnd;
        int startSelectionStart2;
        int startSelectionEnd2;
        ValueAnimator outAnim;
        char c;
        int i;
        int endColor;
        int i2;
        final int endColor2;
        ValueAnimator inAnim;
        int endColor3;
        Animator anim;
        if (startValues == null || endValues == null || !(startValues.view instanceof TextView) || !(endValues.view instanceof TextView)) {
            return null;
        }
        final TextView view = (TextView) endValues.view;
        Map<String, Object> startVals = startValues.values;
        Map<String, Object> endVals = endValues.values;
        final CharSequence startText = startVals.get(PROPNAME_TEXT) != null ? (CharSequence) startVals.get(PROPNAME_TEXT) : "";
        final CharSequence endText = endVals.get(PROPNAME_TEXT) != null ? (CharSequence) endVals.get(PROPNAME_TEXT) : "";
        if (view instanceof EditText) {
            startSelectionStart = startVals.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) startVals.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            int startSelectionEnd3 = startVals.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) startVals.get(PROPNAME_TEXT_SELECTION_END)).intValue() : startSelectionStart;
            int endSelectionStart2 = endVals.get(PROPNAME_TEXT_SELECTION_START) != null ? ((Integer) endVals.get(PROPNAME_TEXT_SELECTION_START)).intValue() : -1;
            endSelectionStart = endSelectionStart2;
            startSelectionEnd = startSelectionEnd3;
            endSelectionEnd = endVals.get(PROPNAME_TEXT_SELECTION_END) != null ? ((Integer) endVals.get(PROPNAME_TEXT_SELECTION_END)).intValue() : endSelectionStart2;
        } else {
            startSelectionStart = -1;
            endSelectionEnd = -1;
            endSelectionStart = -1;
            startSelectionEnd = -1;
        }
        int startSelectionStart3 = startSelectionStart;
        if (startText.equals(endText)) {
            return null;
        }
        if (this.mChangeBehavior != 2) {
            view.setText(startText);
            if (view instanceof EditText) {
                setSelection((EditText) view, startSelectionStart3, startSelectionEnd);
            }
        }
        if (this.mChangeBehavior == 0) {
            endColor3 = 0;
            Animator anim2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            startSelectionEnd2 = startSelectionEnd;
            final int i3 = endSelectionStart;
            startSelectionStart2 = startSelectionStart3;
            final int startSelectionStart4 = endSelectionEnd;
            anim2.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (startText.equals(view.getText())) {
                        view.setText(endText);
                        if (view instanceof EditText) {
                            ChangeText.this.setSelection((EditText) view, i3, startSelectionStart4);
                        }
                    }
                }
            });
            anim = anim2;
        } else {
            startSelectionStart2 = startSelectionStart3;
            startSelectionEnd2 = startSelectionEnd;
            final int startColor = ((Integer) startVals.get(PROPNAME_TEXT_COLOR)).intValue();
            final int endColor4 = ((Integer) endVals.get(PROPNAME_TEXT_COLOR)).intValue();
            if (this.mChangeBehavior == 3 || this.mChangeBehavior == 1) {
                ValueAnimator outAnim2 = ValueAnimator.ofInt(Color.alpha(startColor), 0);
                outAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.transition.ChangeText.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currAlpha = ((Integer) animation.getAnimatedValue()).intValue();
                        view.setTextColor((currAlpha << 24) | (startColor & 16777215));
                    }
                });
                outAnim = outAnim2;
                c = 1;
                final int i4 = endSelectionStart;
                i = 3;
                final int i5 = endSelectionEnd;
                endColor = endColor4;
                outAnim.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        if (startText.equals(view.getText())) {
                            view.setText(endText);
                            if (view instanceof EditText) {
                                ChangeText.this.setSelection((EditText) view, i4, i5);
                            }
                        }
                        view.setTextColor(endColor4);
                    }
                });
            } else {
                outAnim = null;
                c = 1;
                endColor = endColor4;
                i = 3;
            }
            if (this.mChangeBehavior != i) {
                i2 = 2;
                if (this.mChangeBehavior != 2) {
                    inAnim = null;
                    endColor2 = endColor;
                    if (outAnim == null && inAnim != null) {
                        anim = new AnimatorSet();
                        Animator[] animatorArr = new Animator[2];
                        animatorArr[0] = outAnim;
                        animatorArr[c] = inAnim;
                        ((AnimatorSet) anim).playSequentially(animatorArr);
                    } else if (outAnim == null) {
                        anim = outAnim;
                    } else {
                        endColor3 = endColor2;
                        anim = inAnim;
                    }
                    endColor3 = endColor2;
                }
            } else {
                i2 = 2;
            }
            int[] iArr = new int[i2];
            iArr[0] = 0;
            iArr[c] = Color.alpha(endColor);
            ValueAnimator inAnim2 = ValueAnimator.ofInt(iArr);
            endColor2 = endColor;
            inAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.transition.ChangeText.4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currAlpha = ((Integer) animation.getAnimatedValue()).intValue();
                    view.setTextColor((currAlpha << 24) | (endColor2 & 16777215));
                }
            });
            inAnim2.addListener(new AnimatorListenerAdapter() { // from class: android.transition.ChangeText.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    view.setTextColor(endColor2);
                }
            });
            inAnim = inAnim2;
            if (outAnim == null) {
            }
            if (outAnim == null) {
            }
        }
        Animator anim3 = anim;
        final int i6 = endSelectionStart;
        final int i7 = endSelectionEnd;
        final int i8 = endColor3;
        final int i9 = startSelectionStart2;
        final int i10 = startSelectionEnd2;
        Transition.TransitionListener transitionListener = new TransitionListenerAdapter() { // from class: android.transition.ChangeText.6
            int mPausedColor = 0;

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    view.setText(endText);
                    if (view instanceof EditText) {
                        ChangeText.this.setSelection((EditText) view, i6, i7);
                    }
                }
                if (ChangeText.this.mChangeBehavior > 0) {
                    this.mPausedColor = view.getCurrentTextColor();
                    view.setTextColor(i8);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition) {
                if (ChangeText.this.mChangeBehavior != 2) {
                    view.setText(startText);
                    if (view instanceof EditText) {
                        ChangeText.this.setSelection((EditText) view, i9, i10);
                    }
                }
                if (ChangeText.this.mChangeBehavior > 0) {
                    view.setTextColor(this.mPausedColor);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }
        };
        addListener(transitionListener);
        return anim3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelection(EditText editText, int start, int end) {
        if (start >= 0 && end >= 0) {
            editText.setSelection(start, end);
        }
    }
}
