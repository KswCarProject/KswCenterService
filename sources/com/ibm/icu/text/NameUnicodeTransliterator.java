package com.ibm.icu.text;

import com.ibm.icu.text.Transliterator;

class NameUnicodeTransliterator extends Transliterator {
    static final char CLOSE_DELIM = '}';
    static final char OPEN_DELIM = '\\';
    static final String OPEN_PAT = "\\N~{~";
    static final char SPACE = ' ';
    static final String _ID = "Name-Any";

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory() {
            public Transliterator getInstance(String ID) {
                return new NameUnicodeTransliterator((UnicodeFilter) null);
            }
        });
    }

    public NameUnicodeTransliterator(UnicodeFilter filter) {
        super(_ID, filter);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleTransliterate(com.ibm.icu.text.Replaceable r16, com.ibm.icu.text.Transliterator.Position r17, boolean r18) {
        /*
            r15 = this;
            r0 = r16
            r1 = r17
            com.ibm.icu.impl.UCharacterName r2 = com.ibm.icu.impl.UCharacterName.INSTANCE
            int r2 = r2.getMaxCharNameLength()
            int r2 = r2 + 1
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>(r2)
            com.ibm.icu.text.UnicodeSet r4 = new com.ibm.icu.text.UnicodeSet
            r4.<init>()
            com.ibm.icu.impl.UCharacterName r5 = com.ibm.icu.impl.UCharacterName.INSTANCE
            r5.getCharNameCharacters(r4)
            int r5 = r1.start
            int r6 = r1.limit
            r7 = 0
            r8 = -1
            r9 = r7
            r7 = r6
            r6 = r5
            r5 = r8
        L_0x0025:
            if (r6 >= r7) goto L_0x00bf
            int r10 = r0.char32At(r6)
            switch(r9) {
                case 0: goto L_0x00a0;
                case 1: goto L_0x0030;
                default: goto L_0x002e;
            }
        L_0x002e:
            goto L_0x00b8
        L_0x0030:
            boolean r11 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r10)
            r12 = 32
            if (r11 == 0) goto L_0x0055
            int r11 = r3.length()
            if (r11 <= 0) goto L_0x00b8
            int r11 = r3.length()
            int r11 = r11 + -1
            char r11 = r3.charAt(r11)
            if (r11 == r12) goto L_0x00b8
            r3.append(r12)
            int r11 = r3.length()
            if (r11 <= r2) goto L_0x00b8
            r9 = 0
            goto L_0x00b8
        L_0x0055:
            r11 = 125(0x7d, float:1.75E-43)
            if (r10 != r11) goto L_0x008b
            int r11 = r3.length()
            if (r11 <= 0) goto L_0x006c
            int r13 = r11 + -1
            char r13 = r3.charAt(r13)
            if (r13 != r12) goto L_0x006c
            int r11 = r11 + -1
            r3.setLength(r11)
        L_0x006c:
            java.lang.String r12 = r3.toString()
            int r10 = com.ibm.icu.lang.UCharacter.getCharFromExtendedName(r12)
            if (r10 == r8) goto L_0x0088
            int r6 = r6 + 1
            java.lang.String r12 = com.ibm.icu.text.UTF16.valueOf(r10)
            r0.replace(r5, r6, r12)
            int r13 = r6 - r5
            int r14 = r12.length()
            int r13 = r13 - r14
            int r6 = r6 - r13
            int r7 = r7 - r13
        L_0x0088:
            r9 = 0
            r5 = -1
            goto L_0x0025
        L_0x008b:
            boolean r11 = r4.contains((int) r10)
            if (r11 == 0) goto L_0x009c
            com.ibm.icu.text.UTF16.append(r3, r10)
            int r11 = r3.length()
            if (r11 < r2) goto L_0x00b8
            r9 = 0
            goto L_0x00b8
        L_0x009c:
            int r6 = r6 + -1
            r9 = 0
            goto L_0x00b8
        L_0x00a0:
            r11 = 92
            if (r10 != r11) goto L_0x00b8
            r5 = r6
            java.lang.String r11 = "\\N~{~"
            int r11 = com.ibm.icu.impl.Utility.parsePattern(r11, r0, r6, r7)
            if (r11 < 0) goto L_0x00b7
            if (r11 >= r7) goto L_0x00b7
            r9 = 1
            r12 = 0
            r3.setLength(r12)
            r6 = r11
            goto L_0x0025
        L_0x00b7:
        L_0x00b8:
            int r11 = com.ibm.icu.text.UTF16.getCharCount(r10)
            int r6 = r6 + r11
            goto L_0x0025
        L_0x00bf:
            int r8 = r1.contextLimit
            int r10 = r1.limit
            int r10 = r7 - r10
            int r8 = r8 + r10
            r1.contextLimit = r8
            r1.limit = r7
            if (r18 == 0) goto L_0x00d0
            if (r5 < 0) goto L_0x00d0
            r10 = r5
            goto L_0x00d1
        L_0x00d0:
            r10 = r6
        L_0x00d1:
            r1.start = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NameUnicodeTransliterator.handleTransliterate(com.ibm.icu.text.Replaceable, com.ibm.icu.text.Transliterator$Position, boolean):void");
    }

    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = getFilterAsUnicodeSet(inputFilter);
        if (myFilter.containsAll("\\N{") && myFilter.contains(125)) {
            UnicodeSet items = new UnicodeSet().addAll(48, 57).addAll(65, 70).addAll(97, 122).add(60).add(62).add(40).add(41).add(45).add(32).addAll((CharSequence) "\\N{").add(125);
            items.retainAll(myFilter);
            if (items.size() > 0) {
                sourceSet.addAll(items);
                targetSet.addAll(0, 1114111);
            }
        }
    }
}
