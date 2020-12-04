package android.text.method;

import android.text.AutoText;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.util.SparseArray;
import android.view.View;

public class QwertyKeyListener extends BaseKeyListener {
    private static SparseArray<String> PICKER_SETS = new SparseArray<>();
    private static QwertyKeyListener sFullKeyboardInstance;
    private static QwertyKeyListener[] sInstance = new QwertyKeyListener[(TextKeyListener.Capitalize.values().length * 2)];
    private TextKeyListener.Capitalize mAutoCap;
    private boolean mAutoText;
    private boolean mFullKeyboard;

    static {
        PICKER_SETS.put(65, "ÀÁÂÄÆÃÅĄĀ");
        PICKER_SETS.put(67, "ÇĆČ");
        PICKER_SETS.put(68, "Ď");
        PICKER_SETS.put(69, "ÈÉÊËĘĚĒ");
        PICKER_SETS.put(71, "Ğ");
        PICKER_SETS.put(76, "Ł");
        PICKER_SETS.put(73, "ÌÍÎÏĪİ");
        PICKER_SETS.put(78, "ÑŃŇ");
        PICKER_SETS.put(79, "ØŒÕÒÓÔÖŌ");
        PICKER_SETS.put(82, "Ř");
        PICKER_SETS.put(83, "ŚŠŞ");
        PICKER_SETS.put(84, "Ť");
        PICKER_SETS.put(85, "ÙÚÛÜŮŪ");
        PICKER_SETS.put(89, "ÝŸ");
        PICKER_SETS.put(90, "ŹŻŽ");
        PICKER_SETS.put(97, "àáâäæãåąā");
        PICKER_SETS.put(99, "çćč");
        PICKER_SETS.put(100, "ď");
        PICKER_SETS.put(101, "èéêëęěē");
        PICKER_SETS.put(103, "ğ");
        PICKER_SETS.put(105, "ìíîïīı");
        PICKER_SETS.put(108, "ł");
        PICKER_SETS.put(110, "ñńň");
        PICKER_SETS.put(111, "øœõòóôöō");
        PICKER_SETS.put(114, "ř");
        PICKER_SETS.put(115, "§ßśšş");
        PICKER_SETS.put(116, "ť");
        PICKER_SETS.put(117, "ùúûüůū");
        PICKER_SETS.put(121, "ýÿ");
        PICKER_SETS.put(122, "źżž");
        PICKER_SETS.put(61185, "…¥•®©±[]{}\\|");
        PICKER_SETS.put(47, "\\");
        PICKER_SETS.put(49, "¹½⅓¼⅛");
        PICKER_SETS.put(50, "²⅔");
        PICKER_SETS.put(51, "³¾⅜");
        PICKER_SETS.put(52, "⁴");
        PICKER_SETS.put(53, "⅝");
        PICKER_SETS.put(55, "⅞");
        PICKER_SETS.put(48, "ⁿ∅");
        PICKER_SETS.put(36, "¢£€¥₣₤₱");
        PICKER_SETS.put(37, "‰");
        PICKER_SETS.put(42, "†‡");
        PICKER_SETS.put(45, "–—");
        PICKER_SETS.put(43, "±");
        PICKER_SETS.put(40, "[{<");
        PICKER_SETS.put(41, "]}>");
        PICKER_SETS.put(33, "¡");
        PICKER_SETS.put(34, "“”«»˝");
        PICKER_SETS.put(63, "¿");
        PICKER_SETS.put(44, "‚„");
        PICKER_SETS.put(61, "≠≈∞");
        PICKER_SETS.put(60, "≤«‹");
        PICKER_SETS.put(62, "≥»›");
    }

    private QwertyKeyListener(TextKeyListener.Capitalize cap, boolean autoText, boolean fullKeyboard) {
        this.mAutoCap = cap;
        this.mAutoText = autoText;
        this.mFullKeyboard = fullKeyboard;
    }

    public QwertyKeyListener(TextKeyListener.Capitalize cap, boolean autoText) {
        this(cap, autoText, false);
    }

    public static QwertyKeyListener getInstance(boolean autoText, TextKeyListener.Capitalize cap) {
        int off = (cap.ordinal() * 2) + (autoText);
        if (sInstance[off] == null) {
            sInstance[off] = new QwertyKeyListener(cap, autoText);
        }
        return sInstance[off];
    }

    public static QwertyKeyListener getInstanceForFullKeyboard() {
        if (sFullKeyboardInstance == null) {
            sFullKeyboardInstance = new QwertyKeyListener(TextKeyListener.Capitalize.NONE, false, true);
        }
        return sFullKeyboardInstance;
    }

    public int getInputType() {
        return makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0285, code lost:
        if (r27.hasModifiers(2) != false) goto L_0x028b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(android.view.View r24, android.text.Editable r25, int r26, android.view.KeyEvent r27) {
        /*
            r23 = this;
            r7 = r23
            r8 = r24
            r9 = r25
            r10 = r27
            r0 = 0
            if (r8 == 0) goto L_0x0017
            android.text.method.TextKeyListener r1 = android.text.method.TextKeyListener.getInstance()
            android.content.Context r2 = r24.getContext()
            int r0 = r1.getPrefs(r2)
        L_0x0017:
            r11 = r0
            int r0 = android.text.Selection.getSelectionStart(r25)
            int r1 = android.text.Selection.getSelectionEnd(r25)
            int r2 = java.lang.Math.min(r0, r1)
            int r3 = java.lang.Math.max(r0, r1)
            r12 = 0
            if (r2 < 0) goto L_0x0031
            if (r3 >= 0) goto L_0x002e
            goto L_0x0031
        L_0x002e:
            r13 = r2
            r14 = r3
            goto L_0x0037
        L_0x0031:
            r3 = r12
            r2 = r12
            android.text.Selection.setSelection(r9, r12, r12)
            goto L_0x002e
        L_0x0037:
            java.lang.Object r0 = android.text.method.TextKeyListener.ACTIVE
            int r15 = r9.getSpanStart(r0)
            java.lang.Object r0 = android.text.method.TextKeyListener.ACTIVE
            int r6 = r9.getSpanEnd(r0)
            int r0 = getMetaState((java.lang.CharSequence) r9, (android.view.KeyEvent) r10)
            int r5 = r10.getUnicodeChar(r0)
            boolean r0 = r7.mFullKeyboard
            r16 = 1
            if (r0 != 0) goto L_0x0088
            int r0 = r27.getRepeatCount()
            if (r0 <= 0) goto L_0x0088
            if (r13 != r14) goto L_0x0088
            if (r13 <= 0) goto L_0x0088
            int r1 = r13 + -1
            char r4 = r9.charAt(r1)
            if (r4 == r5) goto L_0x006d
            int r1 = java.lang.Character.toUpperCase(r5)
            if (r4 != r1) goto L_0x006a
            goto L_0x006d
        L_0x006a:
            r12 = r5
            r10 = r6
            goto L_0x008a
        L_0x006d:
            if (r8 == 0) goto L_0x0088
            r17 = 0
            r1 = r23
            r2 = r24
            r3 = r25
            r18 = r4
            r12 = r5
            r5 = r17
            r10 = r6
            r6 = r0
            boolean r1 = r1.showCharacterPicker(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L_0x008a
            resetMetaState(r25)
            return r16
        L_0x0088:
            r12 = r5
            r10 = r6
        L_0x008a:
            r0 = 61185(0xef01, float:8.5738E-41)
            if (r12 != r0) goto L_0x00a3
            if (r8 == 0) goto L_0x009f
            r4 = 61185(0xef01, float:8.5738E-41)
            r5 = 1
            r6 = 1
            r1 = r23
            r2 = r24
            r3 = r25
            r1.showCharacterPicker(r2, r3, r4, r5, r6)
        L_0x009f:
            resetMetaState(r25)
            return r16
        L_0x00a3:
            r0 = 61184(0xef00, float:8.5737E-41)
            if (r12 != r0) goto L_0x00da
            r0 = 16
            if (r13 != r14) goto L_0x00c3
            r1 = r14
        L_0x00ad:
            if (r1 <= 0) goto L_0x00c4
            int r2 = r14 - r1
            r3 = 4
            if (r2 >= r3) goto L_0x00c4
            int r2 = r1 + -1
            char r2 = r9.charAt(r2)
            int r2 = java.lang.Character.digit(r2, r0)
            if (r2 < 0) goto L_0x00c4
            int r1 = r1 + -1
            goto L_0x00ad
        L_0x00c3:
            r1 = r13
        L_0x00c4:
            r2 = -1
            java.lang.String r3 = android.text.TextUtils.substring(r9, r1, r14)     // Catch:{ NumberFormatException -> 0x00cf }
            int r0 = java.lang.Integer.parseInt(r3, r0)     // Catch:{ NumberFormatException -> 0x00cf }
            r2 = r0
            goto L_0x00d0
        L_0x00cf:
            r0 = move-exception
        L_0x00d0:
            if (r2 < 0) goto L_0x00d8
            r13 = r1
            android.text.Selection.setSelection(r9, r13, r14)
            r5 = r2
            goto L_0x00db
        L_0x00d8:
            r5 = 0
            goto L_0x00db
        L_0x00da:
            r5 = r12
        L_0x00db:
            r0 = 10
            if (r5 == 0) goto L_0x0271
            r3 = 0
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r4 & r5
            if (r4 == 0) goto L_0x00ea
            r3 = 1
            r4 = 2147483647(0x7fffffff, float:NaN)
            r5 = r5 & r4
        L_0x00ea:
            if (r15 != r13) goto L_0x010d
            if (r10 != r14) goto L_0x010d
            r4 = 0
            int r6 = r14 - r13
            int r6 = r6 + -1
            if (r6 != 0) goto L_0x0102
            char r6 = r9.charAt(r13)
            int r12 = android.view.KeyEvent.getDeadChar(r6, r5)
            if (r12 == 0) goto L_0x0102
            r5 = r12
            r4 = 1
            r3 = 0
        L_0x0102:
            if (r4 != 0) goto L_0x010d
            android.text.Selection.setSelection(r9, r14)
            java.lang.Object r6 = android.text.method.TextKeyListener.ACTIVE
            r9.removeSpan(r6)
            r13 = r14
        L_0x010d:
            r4 = r11 & 1
            if (r4 == 0) goto L_0x0156
            boolean r4 = java.lang.Character.isLowerCase(r5)
            if (r4 == 0) goto L_0x0156
            android.text.method.TextKeyListener$Capitalize r4 = r7.mAutoCap
            boolean r4 = android.text.method.TextKeyListener.shouldCap(r4, r9, r13)
            if (r4 == 0) goto L_0x0156
            java.lang.Object r4 = android.text.method.TextKeyListener.CAPPED
            int r4 = r9.getSpanEnd(r4)
            java.lang.Object r6 = android.text.method.TextKeyListener.CAPPED
            int r6 = r9.getSpanFlags(r6)
            if (r4 != r13) goto L_0x013c
            int r12 = r6 >> 16
            r17 = 65535(0xffff, float:9.1834E-41)
            r12 = r12 & r17
            if (r12 != r5) goto L_0x013c
            java.lang.Object r12 = android.text.method.TextKeyListener.CAPPED
            r9.removeSpan(r12)
            goto L_0x0156
        L_0x013c:
            int r6 = r5 << 16
            int r5 = java.lang.Character.toUpperCase(r5)
            if (r13 != 0) goto L_0x014d
            java.lang.Object r12 = android.text.method.TextKeyListener.CAPPED
            r1 = r6 | 17
            r2 = 0
            r9.setSpan(r12, r2, r2, r1)
            goto L_0x0156
        L_0x014d:
            java.lang.Object r1 = android.text.method.TextKeyListener.CAPPED
            int r2 = r13 + -1
            r12 = r6 | 33
            r9.setSpan(r1, r2, r13, r12)
        L_0x0156:
            if (r13 == r14) goto L_0x015b
            android.text.Selection.setSelection(r9, r14)
        L_0x015b:
            java.lang.Object r1 = OLD_SEL_START
            r2 = 17
            r9.setSpan(r1, r13, r13, r2)
            char r1 = (char) r5
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r9.replace(r13, r14, r1)
            java.lang.Object r1 = OLD_SEL_START
            int r1 = r9.getSpanStart(r1)
            int r2 = android.text.Selection.getSelectionEnd(r25)
            if (r1 >= r2) goto L_0x0187
            java.lang.Object r4 = android.text.method.TextKeyListener.LAST_TYPED
            r6 = 33
            r9.setSpan(r4, r1, r2, r6)
            if (r3 == 0) goto L_0x0187
            android.text.Selection.setSelection(r9, r1, r2)
            java.lang.Object r4 = android.text.method.TextKeyListener.ACTIVE
            r9.setSpan(r4, r1, r2, r6)
        L_0x0187:
            adjustMetaAfterKeypress((android.text.Spannable) r25)
            r4 = r11 & 2
            r6 = 22
            r12 = 32
            if (r4 == 0) goto L_0x0212
            boolean r4 = r7.mAutoText
            if (r4 == 0) goto L_0x0212
            if (r5 == r12) goto L_0x01bc
            r4 = 9
            if (r5 == r4) goto L_0x01bc
            if (r5 == r0) goto L_0x01bc
            r0 = 44
            if (r5 == r0) goto L_0x01bc
            r0 = 46
            if (r5 == r0) goto L_0x01bc
            r0 = 33
            if (r5 == r0) goto L_0x01bc
            r0 = 63
            if (r5 == r0) goto L_0x01bc
            r0 = 34
            if (r5 == r0) goto L_0x01bc
            int r0 = java.lang.Character.getType(r5)
            if (r0 != r6) goto L_0x01b9
            goto L_0x01bc
        L_0x01b9:
            r19 = r2
            goto L_0x0214
        L_0x01bc:
            java.lang.Object r0 = android.text.method.TextKeyListener.INHIBIT_REPLACEMENT
            int r0 = r9.getSpanEnd(r0)
            if (r0 == r1) goto L_0x0212
            r0 = r1
        L_0x01c5:
            if (r0 <= 0) goto L_0x01db
            int r4 = r0 + -1
            char r4 = r9.charAt(r4)
            r14 = 39
            if (r4 == r14) goto L_0x01d8
            boolean r14 = java.lang.Character.isLetter(r4)
            if (r14 != 0) goto L_0x01d8
            goto L_0x01db
        L_0x01d8:
            int r0 = r0 + -1
            goto L_0x01c5
        L_0x01db:
            java.lang.String r4 = r7.getReplacement(r9, r0, r1, r8)
            if (r4 == 0) goto L_0x0212
            int r14 = r25.length()
            java.lang.Class<android.text.method.QwertyKeyListener$Replaced> r6 = android.text.method.QwertyKeyListener.Replaced.class
            r12 = 0
            java.lang.Object[] r6 = r9.getSpans(r12, r14, r6)
            android.text.method.QwertyKeyListener$Replaced[] r6 = (android.text.method.QwertyKeyListener.Replaced[]) r6
            r12 = 0
        L_0x01ef:
            int r14 = r6.length
            if (r12 >= r14) goto L_0x01fa
            r14 = r6[r12]
            r9.removeSpan(r14)
            int r12 = r12 + 1
            goto L_0x01ef
        L_0x01fa:
            int r12 = r1 - r0
            char[] r12 = new char[r12]
            r14 = 0
            android.text.TextUtils.getChars(r9, r0, r1, r12, r14)
            android.text.method.QwertyKeyListener$Replaced r14 = new android.text.method.QwertyKeyListener$Replaced
            r14.<init>(r12)
            r19 = r2
            r2 = 33
            r9.setSpan(r14, r0, r1, r2)
            r9.replace(r0, r1, r4)
            goto L_0x0214
        L_0x0212:
            r19 = r2
        L_0x0214:
            r0 = r11 & 4
            if (r0 == 0) goto L_0x026e
            boolean r0 = r7.mAutoText
            if (r0 == 0) goto L_0x026e
            int r2 = android.text.Selection.getSelectionEnd(r25)
            int r0 = r2 + -3
            if (r0 < 0) goto L_0x0270
            int r0 = r2 + -1
            char r0 = r9.charAt(r0)
            r4 = 32
            if (r0 != r4) goto L_0x0270
            int r0 = r2 + -2
            char r0 = r9.charAt(r0)
            if (r0 != r4) goto L_0x0270
            int r0 = r2 + -3
            char r0 = r9.charAt(r0)
            int r4 = r2 + -3
        L_0x023e:
            if (r4 <= 0) goto L_0x0258
            r6 = 34
            if (r0 == r6) goto L_0x024d
            int r6 = java.lang.Character.getType(r0)
            r12 = 22
            if (r6 != r12) goto L_0x0258
            goto L_0x024f
        L_0x024d:
            r12 = 22
        L_0x024f:
            int r6 = r4 + -1
            char r0 = r9.charAt(r6)
            int r4 = r4 + -1
            goto L_0x023e
        L_0x0258:
            boolean r4 = java.lang.Character.isLetter(r0)
            if (r4 != 0) goto L_0x0264
            boolean r4 = java.lang.Character.isDigit(r0)
            if (r4 == 0) goto L_0x0270
        L_0x0264:
            int r4 = r2 + -2
            int r6 = r2 + -1
            java.lang.String r12 = "."
            r9.replace(r4, r6, r12)
            goto L_0x0270
        L_0x026e:
            r2 = r19
        L_0x0270:
            return r16
        L_0x0271:
            r1 = 67
            r2 = r26
            if (r2 != r1) goto L_0x0307
            boolean r1 = r27.hasNoModifiers()
            if (r1 != 0) goto L_0x0288
            r1 = 2
            r4 = r10
            r3 = r27
            boolean r1 = r3.hasModifiers(r1)
            if (r1 == 0) goto L_0x030a
            goto L_0x028b
        L_0x0288:
            r4 = r10
            r3 = r27
        L_0x028b:
            if (r13 != r14) goto L_0x030a
            r1 = 1
            java.lang.Object r6 = android.text.method.TextKeyListener.LAST_TYPED
            int r6 = r9.getSpanEnd(r6)
            if (r6 != r13) goto L_0x029f
            int r6 = r13 + -1
            char r6 = r9.charAt(r6)
            if (r6 == r0) goto L_0x029f
            r1 = 2
        L_0x029f:
            int r0 = r13 - r1
            java.lang.Class<android.text.method.QwertyKeyListener$Replaced> r6 = android.text.method.QwertyKeyListener.Replaced.class
            java.lang.Object[] r0 = r9.getSpans(r0, r13, r6)
            android.text.method.QwertyKeyListener$Replaced[] r0 = (android.text.method.QwertyKeyListener.Replaced[]) r0
            int r6 = r0.length
            if (r6 <= 0) goto L_0x030a
            r6 = 0
            r10 = r0[r6]
            int r10 = r9.getSpanStart(r10)
            r12 = r0[r6]
            int r12 = r9.getSpanEnd(r12)
            r20 = r1
            java.lang.String r1 = new java.lang.String
            r17 = r0[r6]
            char[] r6 = r17.mText
            r1.<init>(r6)
            r6 = 0
            r6 = r0[r6]
            r9.removeSpan(r6)
            if (r13 < r12) goto L_0x02fb
            java.lang.Object r6 = android.text.method.TextKeyListener.INHIBIT_REPLACEMENT
            r21 = r0
            r0 = 34
            r9.setSpan(r6, r12, r12, r0)
            r9.replace(r10, r12, r1)
            java.lang.Object r0 = android.text.method.TextKeyListener.INHIBIT_REPLACEMENT
            int r0 = r9.getSpanStart(r0)
            int r6 = r0 + -1
            if (r6 < 0) goto L_0x02f0
            java.lang.Object r6 = android.text.method.TextKeyListener.INHIBIT_REPLACEMENT
            int r12 = r0 + -1
            r22 = r1
            r1 = 33
            r9.setSpan(r6, r12, r0, r1)
            goto L_0x02f7
        L_0x02f0:
            r22 = r1
            java.lang.Object r1 = android.text.method.TextKeyListener.INHIBIT_REPLACEMENT
            r9.removeSpan(r1)
        L_0x02f7:
            adjustMetaAfterKeypress((android.text.Spannable) r25)
            return r16
        L_0x02fb:
            r21 = r0
            r22 = r1
            adjustMetaAfterKeypress((android.text.Spannable) r25)
            boolean r0 = super.onKeyDown(r24, r25, r26, r27)
            return r0
        L_0x0307:
            r4 = r10
            r3 = r27
        L_0x030a:
            boolean r0 = super.onKeyDown(r24, r25, r26, r27)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.QwertyKeyListener.onKeyDown(android.view.View, android.text.Editable, int, android.view.KeyEvent):boolean");
    }

    private String getReplacement(CharSequence src, int start, int end, View view) {
        String out;
        int len = end - start;
        boolean changecase = false;
        String replacement = AutoText.get(src, start, end, view);
        if (replacement == null) {
            replacement = AutoText.get(TextUtils.substring(src, start, end).toLowerCase(), 0, end - start, view);
            changecase = true;
            if (replacement == null) {
                return null;
            }
        }
        int j = 0;
        if (changecase) {
            int caps = 0;
            for (int j2 = start; j2 < end; j2++) {
                if (Character.isUpperCase(src.charAt(j2))) {
                    caps++;
                }
            }
            j = caps;
        }
        if (j == 0) {
            out = replacement;
        } else if (j == 1) {
            out = toTitleCase(replacement);
        } else if (j == len) {
            out = replacement.toUpperCase();
        } else {
            out = toTitleCase(replacement);
        }
        if (out.length() != len || !TextUtils.regionMatches(src, start, out, 0, len)) {
            return out;
        }
        return null;
    }

    public static void markAsReplaced(Spannable content, int start, int end, String original) {
        Replaced[] repl = (Replaced[]) content.getSpans(0, content.length(), Replaced.class);
        for (Replaced removeSpan : repl) {
            content.removeSpan(removeSpan);
        }
        int len = original.length();
        char[] orig = new char[len];
        original.getChars(0, len, orig, 0);
        content.setSpan(new Replaced(orig), start, end, 33);
    }

    private boolean showCharacterPicker(View view, Editable content, char c, boolean insert, int count) {
        String set = PICKER_SETS.get(c);
        if (set == null) {
            return false;
        }
        if (count == 1) {
            new CharacterPickerDialog(view.getContext(), view, content, set, insert).show();
        }
        return true;
    }

    private static String toTitleCase(String src) {
        return Character.toUpperCase(src.charAt(0)) + src.substring(1);
    }

    static class Replaced implements NoCopySpan {
        /* access modifiers changed from: private */
        public char[] mText;

        public Replaced(char[] text) {
            this.mText = text;
        }
    }
}
