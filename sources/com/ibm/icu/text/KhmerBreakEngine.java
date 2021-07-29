package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import java.io.IOException;

class KhmerBreakEngine extends DictionaryBreakEngine {
    private static final byte KHMER_LOOKAHEAD = 3;
    private static final byte KHMER_MIN_WORD = 2;
    private static final byte KHMER_MIN_WORD_SPAN = 4;
    private static final byte KHMER_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte KHMER_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fBeginWordSet = new UnicodeSet();
    private static UnicodeSet fEndWordSet = new UnicodeSet(fKhmerWordSet);
    private static UnicodeSet fKhmerWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private DictionaryMatcher fDictionary = DictionaryData.loadDictionaryFor("Khmr");

    static {
        fKhmerWordSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]]");
        fKhmerWordSet.compact();
        fMarkSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fBeginWordSet.add(6016, 6067);
        fEndWordSet.remove(6098);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fKhmerWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }

    public KhmerBreakEngine() throws IOException {
        setCharacters(fKhmerWordSet);
    }

    public boolean equals(Object obj) {
        return obj instanceof KhmerBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean handles(int c) {
        return UCharacter.getIntPropertyValue(c, 4106) == 23;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x013a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int divideUpDictionaryRange(java.text.CharacterIterator r16, int r17, int r18, com.ibm.icu.text.DictionaryBreakEngine.DequeI r19) {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r2 = r18
            int r3 = r2 - r17
            r4 = 0
            r5 = 4
            if (r3 >= r5) goto L_0x000c
            return r4
        L_0x000c:
            r3 = 0
            r5 = 3
            com.ibm.icu.text.DictionaryBreakEngine$PossibleWord[] r6 = new com.ibm.icu.text.DictionaryBreakEngine.PossibleWord[r5]
            r7 = r4
        L_0x0011:
            if (r7 >= r5) goto L_0x001d
            com.ibm.icu.text.DictionaryBreakEngine$PossibleWord r8 = new com.ibm.icu.text.DictionaryBreakEngine$PossibleWord
            r8.<init>()
            r6[r7] = r8
            int r7 = r7 + 1
            goto L_0x0011
        L_0x001d:
            r16.setIndex(r17)
        L_0x0020:
            int r7 = r16.getIndex()
            r8 = r7
            if (r7 >= r2) goto L_0x0140
            r7 = 0
            int r9 = r3 % 3
            r9 = r6[r9]
            com.ibm.icu.text.DictionaryMatcher r10 = r0.fDictionary
            int r9 = r9.candidates(r1, r10, r2)
            r10 = 1
            if (r9 != r10) goto L_0x0040
            int r11 = r3 % 3
            r11 = r6[r11]
            int r7 = r11.acceptMarked(r1)
            int r3 = r3 + 1
            goto L_0x00a0
        L_0x0040:
            if (r9 <= r10) goto L_0x00a0
            r11 = 0
            int r12 = r16.getIndex()
            if (r12 >= r2) goto L_0x0096
        L_0x0049:
            r12 = 1
            int r13 = r3 + 1
            int r13 = r13 % r5
            r13 = r6[r13]
            com.ibm.icu.text.DictionaryMatcher r14 = r0.fDictionary
            int r13 = r13.candidates(r1, r14, r2)
            if (r13 <= 0) goto L_0x008a
            r13 = 2
            if (r12 >= r13) goto L_0x0062
            int r13 = r3 % 3
            r13 = r6[r13]
            r13.markCurrent()
            r12 = 2
        L_0x0062:
            int r13 = r16.getIndex()
            if (r13 < r2) goto L_0x0069
            goto L_0x0096
        L_0x0069:
            int r13 = r3 + 2
            int r13 = r13 % r5
            r13 = r6[r13]
            com.ibm.icu.text.DictionaryMatcher r14 = r0.fDictionary
            int r13 = r13.candidates(r1, r14, r2)
            if (r13 <= 0) goto L_0x007f
            int r13 = r3 % 3
            r13 = r6[r13]
            r13.markCurrent()
            r11 = 1
            goto L_0x008a
        L_0x007f:
            int r13 = r3 + 1
            int r13 = r13 % r5
            r13 = r6[r13]
            boolean r13 = r13.backUp(r1)
            if (r13 != 0) goto L_0x0069
        L_0x008a:
            int r12 = r3 % 3
            r12 = r6[r12]
            boolean r12 = r12.backUp(r1)
            if (r12 == 0) goto L_0x0096
            if (r11 == 0) goto L_0x0049
        L_0x0096:
            int r12 = r3 % 3
            r12 = r6[r12]
            int r7 = r12.acceptMarked(r1)
            int r3 = r3 + 1
        L_0x00a0:
            int r11 = r16.getIndex()
            if (r11 >= r2) goto L_0x010b
            if (r7 >= r5) goto L_0x010b
            int r11 = r3 % 3
            r11 = r6[r11]
            com.ibm.icu.text.DictionaryMatcher r12 = r0.fDictionary
            int r11 = r11.candidates(r1, r12, r2)
            if (r11 > 0) goto L_0x0106
            if (r7 == 0) goto L_0x00c0
            int r11 = r3 % 3
            r11 = r6[r11]
            int r11 = r11.longestPrefix()
            if (r11 >= r5) goto L_0x0106
        L_0x00c0:
            int r11 = r8 + r7
            int r11 = r2 - r11
            char r12 = r16.current()
            r13 = r12
            r12 = r11
            r11 = r4
        L_0x00cb:
            r16.next()
            char r14 = r16.current()
            int r11 = r11 + r10
            int r12 = r12 + -1
            if (r12 > 0) goto L_0x00d8
            goto L_0x00fc
        L_0x00d8:
            com.ibm.icu.text.UnicodeSet r4 = fEndWordSet
            boolean r4 = r4.contains((int) r13)
            if (r4 == 0) goto L_0x0102
            com.ibm.icu.text.UnicodeSet r4 = fBeginWordSet
            boolean r4 = r4.contains((int) r14)
            if (r4 == 0) goto L_0x0102
            int r4 = r3 + 1
            int r4 = r4 % r5
            r4 = r6[r4]
            com.ibm.icu.text.DictionaryMatcher r5 = r0.fDictionary
            int r4 = r4.candidates(r1, r5, r2)
            int r5 = r8 + r7
            int r5 = r5 + r11
            r1.setIndex(r5)
            if (r4 <= 0) goto L_0x0102
        L_0x00fc:
            if (r7 > 0) goto L_0x0100
            int r3 = r3 + 1
        L_0x0100:
            int r7 = r7 + r11
            goto L_0x010b
        L_0x0102:
            r13 = r14
            r4 = 0
            r5 = 3
            goto L_0x00cb
        L_0x0106:
            int r4 = r8 + r7
            r1.setIndex(r4)
        L_0x010b:
            int r4 = r16.getIndex()
            r5 = r4
            if (r4 >= r2) goto L_0x0128
            com.ibm.icu.text.UnicodeSet r4 = fMarkSet
            char r10 = r16.current()
            boolean r4 = r4.contains((int) r10)
            if (r4 == 0) goto L_0x0128
            r16.next()
            int r4 = r16.getIndex()
            int r4 = r4 - r5
            int r7 = r7 + r4
            goto L_0x010b
        L_0x0128:
            if (r7 <= 0) goto L_0x013a
            int r4 = r8 + r7
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            int r4 = r4.intValue()
            r10 = r19
            r10.push(r4)
            goto L_0x013c
        L_0x013a:
            r10 = r19
        L_0x013c:
            r4 = 0
            r5 = 3
            goto L_0x0020
        L_0x0140:
            r10 = r19
            int r4 = r19.peek()
            if (r4 < r2) goto L_0x014d
            r19.pop()
            int r3 = r3 + -1
        L_0x014d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.KhmerBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
