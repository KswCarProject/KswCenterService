package com.ibm.icu.text;

import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Transliterator;

class UnescapeTransliterator extends Transliterator {
    private static final char END = '￿';
    private char[] spec;

    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{2, 0, 16, 4, 6, 'U', '+', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[]{2, 0, 16, 4, 4, '\\', 'u', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/C", new char[]{2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, 8, 8, '\\', 'U', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[]{3, 1, 16, 1, 6, '&', '#', EpicenterTranslateClipReveal.StateProperty.TARGET_X, ';', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[]{2, 1, 10, 1, 7, '&', '#', ';', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[]{3, 1, 16, 1, 6, '\\', EpicenterTranslateClipReveal.StateProperty.TARGET_X, '{', '}', 65535});
            }
        });
        Transliterator.registerFactory("Hex-Any", new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any", new char[]{2, 0, 16, 4, 6, 'U', '+', 2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, 8, 8, '\\', 'U', 3, 1, 16, 1, 6, '&', '#', EpicenterTranslateClipReveal.StateProperty.TARGET_X, ';', 2, 1, 10, 1, 7, '&', '#', ';', 3, 1, 16, 1, 6, '\\', EpicenterTranslateClipReveal.StateProperty.TARGET_X, '{', '}', 65535});
            }
        });
    }

    UnescapeTransliterator(String ID, char[] spec2) {
        super(ID, (UnicodeFilter) null);
        this.spec = spec2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005c, code lost:
        if (r14 == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005e, code lost:
        r16 = 0;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0062, code lost:
        if (r15 < r5) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        if (r15 <= r4) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0066, code lost:
        if (r24 == false) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006a, code lost:
        r19 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
        r19 = r12;
        r12 = r1.char32At(r15);
        r18 = com.ibm.icu.lang.UCharacter.digit(r12, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0077, code lost:
        if (r18 >= 0) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007a, code lost:
        r15 = r15 + com.ibm.icu.text.UTF16.getCharCount(r12);
        r16 = (r16 * r9) + r18;
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0086, code lost:
        if (r6 != r11) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0089, code lost:
        if (r6 < r10) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008b, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008d, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        r14 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008f, code lost:
        if (r14 == false) goto L_0x00d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0091, code lost:
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0092, code lost:
        if (r12 >= r8) goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0094, code lost:
        if (r15 < r5) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0096, code lost:
        if (r15 <= r4) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
        if (r24 == false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009b, code lost:
        r14 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009d, code lost:
        r18 = r15 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ab, code lost:
        if (r1.charAt(r15) == r0.spec[(r13 + r7) + r12]) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ad, code lost:
        r14 = false;
        r15 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b3, code lost:
        r12 = r12 + 1;
        r15 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b8, code lost:
        if (r14 == false) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ba, code lost:
        r3 = com.ibm.icu.text.UTF16.valueOf(r16);
        r1.replace(r4, r15, r3);
        r5 = r5 - ((r15 - r4) - r3.length());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cc, code lost:
        r19 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cf, code lost:
        r12 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d3, code lost:
        r19 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00dc, code lost:
        if (r4 >= r5) goto L_0x000a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00de, code lost:
        r4 = r4 + com.ibm.icu.text.UTF16.getCharCount(r1.char32At(r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00d5, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleTransliterate(com.ibm.icu.text.Replaceable r22, com.ibm.icu.text.Transliterator.Position r23, boolean r24) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            int r4 = r2.start
            int r5 = r2.limit
        L_0x000a:
            if (r4 >= r5) goto L_0x00e9
            r7 = 0
        L_0x000d:
            char[] r8 = r0.spec
            char r8 = r8[r7]
            r9 = 65535(0xffff, float:9.1834E-41)
            if (r8 == r9) goto L_0x00db
            char[] r8 = r0.spec
            int r9 = r7 + 1
            char r7 = r8[r7]
            char[] r8 = r0.spec
            int r10 = r9 + 1
            char r8 = r8[r9]
            char[] r9 = r0.spec
            int r11 = r10 + 1
            char r9 = r9[r10]
            char[] r10 = r0.spec
            int r12 = r11 + 1
            char r10 = r10[r11]
            char[] r11 = r0.spec
            int r13 = r12 + 1
            char r11 = r11[r12]
            r12 = r4
            r14 = 1
            r15 = r12
            r12 = 0
        L_0x0038:
            if (r12 >= r7) goto L_0x005c
            if (r15 < r5) goto L_0x0044
            if (r12 <= 0) goto L_0x0044
            if (r24 == 0) goto L_0x0042
            goto L_0x00e9
        L_0x0042:
            r14 = 0
            goto L_0x005c
        L_0x0044:
            int r16 = r15 + 1
            char r15 = r1.charAt(r15)
            char[] r6 = r0.spec
            int r18 = r13 + r12
            char r6 = r6[r18]
            if (r15 == r6) goto L_0x0057
            r14 = 0
            r15 = r16
            goto L_0x005c
        L_0x0057:
            int r12 = r12 + 1
            r15 = r16
            goto L_0x0038
        L_0x005c:
            if (r14 == 0) goto L_0x00d3
            r6 = 0
            r16 = r6
            r6 = 0
        L_0x0062:
            if (r15 < r5) goto L_0x006d
            if (r15 <= r4) goto L_0x006a
            if (r24 == 0) goto L_0x006a
            goto L_0x00e9
        L_0x006a:
            r19 = r12
            goto L_0x0089
        L_0x006d:
            r19 = r12
            int r12 = r1.char32At(r15)
            int r18 = com.ibm.icu.lang.UCharacter.digit(r12, r9)
            if (r18 >= 0) goto L_0x007a
            goto L_0x0089
        L_0x007a:
            int r20 = com.ibm.icu.text.UTF16.getCharCount(r12)
            int r15 = r15 + r20
            int r20 = r16 * r9
            int r16 = r20 + r18
            int r6 = r6 + 1
            if (r6 != r11) goto L_0x00cf
        L_0x0089:
            if (r6 < r10) goto L_0x008d
            r12 = 1
            goto L_0x008e
        L_0x008d:
            r12 = 0
        L_0x008e:
            r14 = r12
            if (r14 == 0) goto L_0x00d5
            r12 = 0
        L_0x0092:
            if (r12 >= r8) goto L_0x00b8
            if (r15 < r5) goto L_0x009d
            if (r15 <= r4) goto L_0x009b
            if (r24 == 0) goto L_0x009b
            goto L_0x00e9
        L_0x009b:
            r14 = 0
            goto L_0x00b8
        L_0x009d:
            int r18 = r15 + 1
            char r15 = r1.charAt(r15)
            char[] r3 = r0.spec
            int r19 = r13 + r7
            int r19 = r19 + r12
            char r3 = r3[r19]
            if (r15 == r3) goto L_0x00b3
            r3 = 0
            r14 = r3
            r15 = r18
            goto L_0x00b8
        L_0x00b3:
            int r12 = r12 + 1
            r15 = r18
            goto L_0x0092
        L_0x00b8:
            if (r14 == 0) goto L_0x00cc
            java.lang.String r3 = com.ibm.icu.text.UTF16.valueOf(r16)
            r1.replace(r4, r15, r3)
            int r17 = r15 - r4
            int r18 = r3.length()
            int r17 = r17 - r18
            int r5 = r5 - r17
            goto L_0x00dc
        L_0x00cc:
            r19 = r12
            goto L_0x00d5
        L_0x00cf:
            r12 = r19
            goto L_0x0062
        L_0x00d3:
            r19 = r12
        L_0x00d5:
            int r3 = r7 + r8
            int r7 = r13 + r3
            goto L_0x000d
        L_0x00db:
            r13 = r7
        L_0x00dc:
            if (r4 >= r5) goto L_0x000a
            int r3 = r1.char32At(r4)
            int r3 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r4 = r4 + r3
            goto L_0x000a
        L_0x00e9:
            int r3 = r2.contextLimit
            int r6 = r2.limit
            int r6 = r5 - r6
            int r3 = r3 + r6
            r2.contextLimit = r3
            r2.limit = r5
            r2.start = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnescapeTransliterator.handleTransliterate(com.ibm.icu.text.Replaceable, com.ibm.icu.text.Transliterator$Position, boolean):void");
    }

    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = getFilterAsUnicodeSet(inputFilter);
        UnicodeSet items = new UnicodeSet();
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while (this.spec[i] != 65535) {
            int end = this.spec[i] + i + this.spec[i + 1] + 5;
            char radix = this.spec[i + 2];
            for (int j = 0; j < radix; j++) {
                Utility.appendNumber(buffer, j, radix, 0);
            }
            for (int j2 = i + 5; j2 < end; j2++) {
                items.add((int) this.spec[j2]);
            }
            i = end;
        }
        items.addAll((CharSequence) buffer.toString());
        items.retainAll(myFilter);
        if (items.size() > 0) {
            sourceSet.addAll(items);
            targetSet.addAll(0, 1114111);
        }
    }
}
