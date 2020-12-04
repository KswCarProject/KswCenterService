package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.GetChars;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.view.View;
import java.lang.ref.WeakReference;

public class PasswordTransformationMethod implements TransformationMethod, TextWatcher {
    /* access modifiers changed from: private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static char DOT = 8226;
    @UnsupportedAppUsage
    private static PasswordTransformationMethod sInstance;

    public CharSequence getTransformation(CharSequence source, View view) {
        if (source instanceof Spannable) {
            Spannable sp = (Spannable) source;
            ViewReference[] vr = (ViewReference[]) sp.getSpans(0, sp.length(), ViewReference.class);
            for (ViewReference removeSpan : vr) {
                sp.removeSpan(removeSpan);
            }
            removeVisibleSpans(sp);
            sp.setSpan(new ViewReference(view), 0, 0, 34);
        }
        return new PasswordCharSequence(source);
    }

    public static PasswordTransformationMethod getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new PasswordTransformationMethod();
        return sInstance;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTextChanged(java.lang.CharSequence r8, int r9, int r10, int r11) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof android.text.Spannable
            if (r0 == 0) goto L_0x0052
            r0 = r8
            android.text.Spannable r0 = (android.text.Spannable) r0
            int r1 = r8.length()
            java.lang.Class<android.text.method.PasswordTransformationMethod$ViewReference> r2 = android.text.method.PasswordTransformationMethod.ViewReference.class
            r3 = 0
            java.lang.Object[] r1 = r0.getSpans(r3, r1, r2)
            android.text.method.PasswordTransformationMethod$ViewReference[] r1 = (android.text.method.PasswordTransformationMethod.ViewReference[]) r1
            int r2 = r1.length
            if (r2 != 0) goto L_0x0018
            return
        L_0x0018:
            r2 = 0
        L_0x001a:
            if (r2 != 0) goto L_0x002b
            int r4 = r1.length
            if (r3 >= r4) goto L_0x002b
            r4 = r1[r3]
            java.lang.Object r4 = r4.get()
            r2 = r4
            android.view.View r2 = (android.view.View) r2
            int r3 = r3 + 1
            goto L_0x001a
        L_0x002b:
            if (r2 != 0) goto L_0x002e
            return
        L_0x002e:
            android.text.method.TextKeyListener r3 = android.text.method.TextKeyListener.getInstance()
            android.content.Context r4 = r2.getContext()
            int r3 = r3.getPrefs(r4)
            r4 = r3 & 8
            if (r4 == 0) goto L_0x0052
            if (r11 <= 0) goto L_0x0052
            removeVisibleSpans(r0)
            r4 = 1
            if (r11 != r4) goto L_0x0052
            android.text.method.PasswordTransformationMethod$Visible r4 = new android.text.method.PasswordTransformationMethod$Visible
            r4.<init>(r0, r7)
            int r5 = r9 + r11
            r6 = 33
            r0.setSpan(r4, r9, r5, r6)
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.PasswordTransformationMethod.onTextChanged(java.lang.CharSequence, int, int, int):void");
    }

    public void afterTextChanged(Editable s) {
    }

    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
        if (!focused && (sourceText instanceof Spannable)) {
            removeVisibleSpans((Spannable) sourceText);
        }
    }

    private static void removeVisibleSpans(Spannable sp) {
        int i = 0;
        Visible[] old = (Visible[]) sp.getSpans(0, sp.length(), Visible.class);
        while (true) {
            int i2 = i;
            if (i2 < old.length) {
                sp.removeSpan(old[i2]);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static class PasswordCharSequence implements CharSequence, GetChars {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            this.mSource = source;
        }

        public int length() {
            return this.mSource.length();
        }

        public char charAt(int i) {
            if (this.mSource instanceof Spanned) {
                Spanned sp = (Spanned) this.mSource;
                int st = sp.getSpanStart(TextKeyListener.ACTIVE);
                int en = sp.getSpanEnd(TextKeyListener.ACTIVE);
                if (i < st || i >= en) {
                    int a = 0;
                    Visible[] visible = (Visible[]) sp.getSpans(0, sp.length(), Visible.class);
                    while (true) {
                        int a2 = a;
                        if (a2 >= visible.length) {
                            break;
                        }
                        if (sp.getSpanStart(visible[a2].mTransformer) >= 0) {
                            int st2 = sp.getSpanStart(visible[a2]);
                            int en2 = sp.getSpanEnd(visible[a2]);
                            if (i >= st2 && i < en2) {
                                return this.mSource.charAt(i);
                            }
                        }
                        a = a2 + 1;
                    }
                } else {
                    return this.mSource.charAt(i);
                }
            }
            return PasswordTransformationMethod.DOT;
        }

        public CharSequence subSequence(int start, int end) {
            char[] buf = new char[(end - start)];
            getChars(start, end, buf, 0);
            return new String(buf);
        }

        public String toString() {
            return subSequence(0, length()).toString();
        }

        public void getChars(int start, int end, char[] dest, int off) {
            TextUtils.getChars(this.mSource, start, end, dest, off);
            int st = -1;
            int en = -1;
            int nvisible = 0;
            int[] starts = null;
            int[] ends = null;
            if (this.mSource instanceof Spanned) {
                Spanned sp = (Spanned) this.mSource;
                st = sp.getSpanStart(TextKeyListener.ACTIVE);
                en = sp.getSpanEnd(TextKeyListener.ACTIVE);
                Visible[] visible = (Visible[]) sp.getSpans(0, sp.length(), Visible.class);
                nvisible = visible.length;
                starts = new int[nvisible];
                ends = new int[nvisible];
                for (int i = 0; i < nvisible; i++) {
                    if (sp.getSpanStart(visible[i].mTransformer) >= 0) {
                        starts[i] = sp.getSpanStart(visible[i]);
                        ends[i] = sp.getSpanEnd(visible[i]);
                    }
                }
            }
            for (int i2 = start; i2 < end; i2++) {
                if (i2 < st || i2 >= en) {
                    boolean visible2 = false;
                    int a = 0;
                    while (true) {
                        if (a < nvisible) {
                            if (i2 >= starts[a] && i2 < ends[a]) {
                                visible2 = true;
                                break;
                            }
                            a++;
                        } else {
                            break;
                        }
                    }
                    if (!visible2) {
                        dest[(i2 - start) + off] = PasswordTransformationMethod.DOT;
                    }
                }
            }
        }
    }

    private static class Visible extends Handler implements UpdateLayout, Runnable {
        private Spannable mText;
        /* access modifiers changed from: private */
        public PasswordTransformationMethod mTransformer;

        public Visible(Spannable sp, PasswordTransformationMethod ptm) {
            this.mText = sp;
            this.mTransformer = ptm;
            postAtTime(this, SystemClock.uptimeMillis() + 1500);
        }

        public void run() {
            this.mText.removeSpan(this);
        }
    }

    private static class ViewReference extends WeakReference<View> implements NoCopySpan {
        public ViewReference(View v) {
            super(v);
        }
    }
}
