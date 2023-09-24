package com.ibm.icu.text;

import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Transliterator;

/* loaded from: classes5.dex */
class UnescapeTransliterator extends Transliterator {
    private static final char END = '\uffff';
    private char[] spec;

    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{2, 0, 16, 4, 6, 'U', '+', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.2
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[]{2, 0, 16, 4, 4, '\\', 'u', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.3
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/C", new char[]{2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, '\b', '\b', '\\', 'U', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.4
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[]{3, 1, 16, 1, 6, '&', '#', EpicenterTranslateClipReveal.StateProperty.TARGET_X, ';', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.5
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[]{2, 1, '\n', 1, 7, '&', '#', ';', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.6
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[]{3, 1, 16, 1, 6, '\\', EpicenterTranslateClipReveal.StateProperty.TARGET_X, '{', '}', '\uffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any", new Transliterator.Factory() { // from class: com.ibm.icu.text.UnescapeTransliterator.7
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new UnescapeTransliterator("Hex-Any", new char[]{2, 0, 16, 4, 6, 'U', '+', 2, 0, 16, 4, 4, '\\', 'u', 2, 0, 16, '\b', '\b', '\\', 'U', 3, 1, 16, 1, 6, '&', '#', EpicenterTranslateClipReveal.StateProperty.TARGET_X, ';', 2, 1, '\n', 1, 7, '&', '#', ';', 3, 1, 16, 1, 6, '\\', EpicenterTranslateClipReveal.StateProperty.TARGET_X, '{', '}', '\uffff'});
            }
        });
    }

    UnescapeTransliterator(String ID, char[] spec) {
        super(ID, null);
        this.spec = spec;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
        if (r14 == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005e, code lost:
        r16 = 0;
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0062, code lost:
        if (r15 < r5) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
        if (r15 <= r4) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
        if (r24 == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
        r19 = r12;
        r12 = r22.char32At(r15);
        r18 = com.ibm.icu.lang.UCharacter.digit(r12, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0077, code lost:
        if (r18 >= 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007a, code lost:
        r15 = r15 + com.ibm.icu.text.UTF16.getCharCount(r12);
        r16 = (r16 * r9) + r18;
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0086, code lost:
        if (r6 != r11) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0089, code lost:
        if (r6 < r10) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008b, code lost:
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008d, code lost:
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
        r14 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008f, code lost:
        if (r14 == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0091, code lost:
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0092, code lost:
        if (r12 >= r8) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0094, code lost:
        if (r15 < r5) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0096, code lost:
        if (r15 <= r4) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0098, code lost:
        if (r24 == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x009b, code lost:
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009d, code lost:
        r18 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ab, code lost:
        if (r22.charAt(r15) == r21.spec[(r13 + r7) + r12]) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ad, code lost:
        r14 = false;
        r15 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b3, code lost:
        r12 = r12 + 1;
        r15 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b8, code lost:
        if (r14 == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ba, code lost:
        r3 = com.ibm.icu.text.UTF16.valueOf(r16);
        r22.replace(r4, r15, r3);
        r5 = r5 - ((r15 - r4) - r3.length());
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00cf, code lost:
        r12 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00dc, code lost:
        if (r4 >= r5) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00de, code lost:
        r4 = r4 + com.ibm.icu.text.UTF16.getCharCount(r22.char32At(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d5, code lost:
        continue;
     */
    @Override // com.ibm.icu.text.Transliterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean isIncremental) {
        char c;
        char c2;
        int ipat;
        int start = pos.start;
        int limit = pos.limit;
        loop0: while (start < limit) {
            int prefixLen = 0;
            while (true) {
                if (this.spec[prefixLen] == '\uffff') {
                    break;
                }
                int ipat2 = prefixLen + 1;
                c = this.spec[prefixLen];
                int ipat3 = ipat2 + 1;
                c2 = this.spec[ipat2];
                int ipat4 = ipat3 + 1;
                char c3 = this.spec[ipat3];
                int ipat5 = ipat4 + 1;
                char c4 = this.spec[ipat4];
                ipat = ipat5 + 1;
                char c5 = this.spec[ipat5];
                boolean match = true;
                int s = start;
                int s2 = 0;
                while (true) {
                    if (s2 >= c) {
                        break;
                    } else if (s >= limit && s2 > 0) {
                        if (isIncremental) {
                            break loop0;
                        }
                        match = false;
                    } else {
                        int s3 = s + 1;
                        if (text.charAt(s) == this.spec[ipat + s2]) {
                            s2++;
                            s = s3;
                        } else {
                            match = false;
                            s = s3;
                            break;
                        }
                    }
                }
                prefixLen = ipat + c + c2;
            }
        }
        pos.contextLimit += limit - pos.limit;
        pos.limit = limit;
        pos.start = start;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = getFilterAsUnicodeSet(inputFilter);
        UnicodeSet items = new UnicodeSet();
        StringBuilder buffer = new StringBuilder();
        int i = 0;
        while (this.spec[i] != '\uffff') {
            int end = this.spec[i] + i + this.spec[i + 1] + 5;
            char c = this.spec[i + 2];
            for (int j = 0; j < c; j++) {
                Utility.appendNumber(buffer, j, c, 0);
            }
            for (int j2 = i + 5; j2 < end; j2++) {
                items.add(this.spec[j2]);
            }
            i = end;
        }
        items.addAll(buffer.toString());
        items.retainAll(myFilter);
        if (items.size() > 0) {
            sourceSet.addAll(items);
            targetSet.addAll(0, 1114111);
        }
    }
}
