package android.text.method;

import android.net.wifi.WifiEnterpriseConfig;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.method.TextKeyListener;
import android.util.SparseArray;

public class MultiTapKeyListener extends BaseKeyListener implements SpanWatcher {
    private static MultiTapKeyListener[] sInstance = new MultiTapKeyListener[(TextKeyListener.Capitalize.values().length * 2)];
    private static final SparseArray<String> sRecs = new SparseArray<>();
    private boolean mAutoText;
    private TextKeyListener.Capitalize mCapitalize;

    static {
        sRecs.put(8, ".,1!@#$%^&*:/?'=()");
        sRecs.put(9, "abc2ABC");
        sRecs.put(10, "def3DEF");
        sRecs.put(11, "ghi4GHI");
        sRecs.put(12, "jkl5JKL");
        sRecs.put(13, "mno6MNO");
        sRecs.put(14, "pqrs7PQRS");
        sRecs.put(15, "tuv8TUV");
        sRecs.put(16, "wxyz9WXYZ");
        sRecs.put(7, "0+");
        sRecs.put(18, WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
    }

    public MultiTapKeyListener(TextKeyListener.Capitalize cap, boolean autotext) {
        this.mCapitalize = cap;
        this.mAutoText = autotext;
    }

    public static MultiTapKeyListener getInstance(boolean autotext, TextKeyListener.Capitalize cap) {
        int off = (cap.ordinal() * 2) + (autotext);
        if (sInstance[off] == null) {
            sInstance[off] = new MultiTapKeyListener(cap, autotext);
        }
        return sInstance[off];
    }

    public int getInputType() {
        return makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0180  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(android.view.View r21, android.text.Editable r22, int r23, android.view.KeyEvent r24) {
        /*
            r20 = this;
            r0 = r20
            r7 = r22
            r8 = r23
            r1 = 0
            if (r21 == 0) goto L_0x0015
            android.text.method.TextKeyListener r2 = android.text.method.TextKeyListener.getInstance()
            android.content.Context r3 = r21.getContext()
            int r1 = r2.getPrefs(r3)
        L_0x0015:
            r9 = r1
            int r1 = android.text.Selection.getSelectionStart(r22)
            int r2 = android.text.Selection.getSelectionEnd(r22)
            int r10 = java.lang.Math.min(r1, r2)
            int r11 = java.lang.Math.max(r1, r2)
            java.lang.Object r1 = android.text.method.TextKeyListener.ACTIVE
            int r12 = r7.getSpanStart(r1)
            java.lang.Object r1 = android.text.method.TextKeyListener.ACTIVE
            int r13 = r7.getSpanEnd(r1)
            java.lang.Object r1 = android.text.method.TextKeyListener.ACTIVE
            int r1 = r7.getSpanFlags(r1)
            r2 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = r1 & r2
            int r14 = r1 >>> 24
            r1 = 17
            r6 = 1
            if (r12 != r10) goto L_0x00de
            if (r13 != r11) goto L_0x00de
            int r2 = r11 - r10
            if (r2 != r6) goto L_0x00de
            if (r14 < 0) goto L_0x00de
            android.util.SparseArray<java.lang.String> r2 = sRecs
            int r2 = r2.size()
            if (r14 >= r2) goto L_0x00de
            if (r8 != r1) goto L_0x008e
            char r2 = r7.charAt(r10)
            boolean r3 = java.lang.Character.isLowerCase(r2)
            if (r3 == 0) goto L_0x0073
            java.lang.String r1 = java.lang.String.valueOf(r2)
            java.lang.String r1 = r1.toUpperCase()
            r7.replace(r10, r11, r1)
            removeTimeouts(r22)
            android.text.method.MultiTapKeyListener$Timeout r1 = new android.text.method.MultiTapKeyListener$Timeout
            r1.<init>(r7)
            return r6
        L_0x0073:
            boolean r3 = java.lang.Character.isUpperCase(r2)
            if (r3 == 0) goto L_0x008e
            java.lang.String r1 = java.lang.String.valueOf(r2)
            java.lang.String r1 = r1.toLowerCase()
            r7.replace(r10, r11, r1)
            removeTimeouts(r22)
            android.text.method.MultiTapKeyListener$Timeout r1 = new android.text.method.MultiTapKeyListener$Timeout
            r1.<init>(r7)
            return r6
        L_0x008e:
            android.util.SparseArray<java.lang.String> r2 = sRecs
            int r2 = r2.indexOfKey(r8)
            if (r2 != r14) goto L_0x00cd
            android.util.SparseArray<java.lang.String> r2 = sRecs
            java.lang.Object r2 = r2.valueAt(r14)
            r5 = r2
            java.lang.String r5 = (java.lang.String) r5
            char r4 = r7.charAt(r10)
            int r2 = r5.indexOf(r4)
            if (r2 < 0) goto L_0x00cd
            int r1 = r2 + 1
            int r3 = r5.length()
            int r15 = r1 % r3
            int r16 = r15 + 1
            r1 = r22
            r2 = r10
            r3 = r11
            r17 = r4
            r4 = r5
            r18 = r5
            r5 = r15
            r19 = r6
            r6 = r16
            r1.replace(r2, r3, r4, r5, r6)
            removeTimeouts(r22)
            android.text.method.MultiTapKeyListener$Timeout r1 = new android.text.method.MultiTapKeyListener$Timeout
            r1.<init>(r7)
            return r19
        L_0x00cd:
            r19 = r6
            android.util.SparseArray<java.lang.String> r2 = sRecs
            int r2 = r2.indexOfKey(r8)
            if (r2 < 0) goto L_0x00e6
            android.text.Selection.setSelection(r7, r11, r11)
            r3 = r11
            r10 = r2
            r14 = r3
            goto L_0x00e8
        L_0x00de:
            r19 = r6
            android.util.SparseArray<java.lang.String> r2 = sRecs
            int r2 = r2.indexOfKey(r8)
        L_0x00e6:
            r14 = r10
            r10 = r2
        L_0x00e8:
            if (r10 < 0) goto L_0x0180
            android.util.SparseArray<java.lang.String> r2 = sRecs
            java.lang.Object r2 = r2.valueAt(r10)
            r6 = r2
            java.lang.String r6 = (java.lang.String) r6
            r2 = 0
            r3 = r9 & 1
            r5 = 0
            if (r3 == 0) goto L_0x0117
            android.text.method.TextKeyListener$Capitalize r3 = r0.mCapitalize
            boolean r3 = android.text.method.TextKeyListener.shouldCap(r3, r7, r14)
            if (r3 == 0) goto L_0x0117
            r3 = r5
        L_0x0102:
            int r4 = r6.length()
            if (r3 >= r4) goto L_0x0117
            char r4 = r6.charAt(r3)
            boolean r4 = java.lang.Character.isUpperCase(r4)
            if (r4 == 0) goto L_0x0114
            r2 = r3
            goto L_0x0117
        L_0x0114:
            int r3 = r3 + 1
            goto L_0x0102
        L_0x0117:
            r15 = r2
            if (r14 == r11) goto L_0x011d
            android.text.Selection.setSelection(r7, r11)
        L_0x011d:
            java.lang.Object r2 = OLD_SEL_START
            r7.setSpan(r2, r14, r14, r1)
            int r16 = r15 + 1
            r1 = r22
            r2 = r14
            r3 = r11
            r4 = r6
            r8 = r5
            r5 = r15
            r17 = r6
            r6 = r16
            r1.replace(r2, r3, r4, r5, r6)
            java.lang.Object r1 = OLD_SEL_START
            int r1 = r7.getSpanStart(r1)
            int r2 = android.text.Selection.getSelectionEnd(r22)
            if (r2 == r1) goto L_0x0150
            android.text.Selection.setSelection(r7, r1, r2)
            java.lang.Object r3 = android.text.method.TextKeyListener.LAST_TYPED
            r4 = 33
            r7.setSpan(r3, r1, r2, r4)
            java.lang.Object r3 = android.text.method.TextKeyListener.ACTIVE
            int r5 = r10 << 24
            r4 = r4 | r5
            r7.setSpan(r3, r1, r2, r4)
        L_0x0150:
            removeTimeouts(r22)
            android.text.method.MultiTapKeyListener$Timeout r3 = new android.text.method.MultiTapKeyListener$Timeout
            r3.<init>(r7)
            int r3 = r7.getSpanStart(r0)
            if (r3 >= 0) goto L_0x017f
            int r3 = r22.length()
            java.lang.Class<android.text.method.KeyListener> r4 = android.text.method.KeyListener.class
            java.lang.Object[] r3 = r7.getSpans(r8, r3, r4)
            android.text.method.KeyListener[] r3 = (android.text.method.KeyListener[]) r3
            int r4 = r3.length
            r5 = r8
        L_0x016c:
            if (r5 >= r4) goto L_0x0176
            r6 = r3[r5]
            r7.removeSpan(r6)
            int r5 = r5 + 1
            goto L_0x016c
        L_0x0176:
            int r4 = r22.length()
            r5 = 18
            r7.setSpan(r0, r8, r4, r5)
        L_0x017f:
            return r19
        L_0x0180:
            boolean r1 = super.onKeyDown(r21, r22, r23, r24)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.MultiTapKeyListener.onKeyDown(android.view.View, android.text.Editable, int, android.view.KeyEvent):boolean");
    }

    public void onSpanChanged(Spannable buf, Object what, int s, int e, int start, int stop) {
        if (what == Selection.SELECTION_END) {
            buf.removeSpan(TextKeyListener.ACTIVE);
            removeTimeouts(buf);
        }
    }

    private static void removeTimeouts(Spannable buf) {
        int i = 0;
        Timeout[] timeout = (Timeout[]) buf.getSpans(0, buf.length(), Timeout.class);
        while (true) {
            int i2 = i;
            if (i2 < timeout.length) {
                Timeout t = timeout[i2];
                t.removeCallbacks(t);
                Editable unused = t.mBuffer = null;
                buf.removeSpan(t);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private class Timeout extends Handler implements Runnable {
        /* access modifiers changed from: private */
        public Editable mBuffer;

        public Timeout(Editable buffer) {
            this.mBuffer = buffer;
            this.mBuffer.setSpan(this, 0, this.mBuffer.length(), 18);
            postAtTime(this, SystemClock.uptimeMillis() + 2000);
        }

        public void run() {
            Spannable buf = this.mBuffer;
            if (buf != null) {
                int st = Selection.getSelectionStart(buf);
                int en = Selection.getSelectionEnd(buf);
                int start = buf.getSpanStart(TextKeyListener.ACTIVE);
                int end = buf.getSpanEnd(TextKeyListener.ACTIVE);
                if (st == start && en == end) {
                    Selection.setSelection(buf, Selection.getSelectionEnd(buf));
                }
                buf.removeSpan(this);
            }
        }
    }

    public void onSpanAdded(Spannable s, Object what, int start, int end) {
    }

    public void onSpanRemoved(Spannable s, Object what, int start, int end) {
    }
}
