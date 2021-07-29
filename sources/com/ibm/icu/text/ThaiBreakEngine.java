package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import java.io.IOException;

class ThaiBreakEngine extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = 3;
    private static final char THAI_MAIYAMOK = 'ๆ';
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private static final char THAI_PAIYANNOI = 'ฯ';
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fBeginWordSet = new UnicodeSet();
    private static UnicodeSet fEndWordSet = new UnicodeSet(fThaiWordSet);
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fSuffixSet = new UnicodeSet();
    private static UnicodeSet fThaiWordSet = new UnicodeSet();
    private DictionaryMatcher fDictionary = DictionaryData.loadDictionaryFor("Thai");

    static {
        fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        fThaiWordSet.compact();
        fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet.remove(3633);
        fEndWordSet.remove(3648, 3652);
        fBeginWordSet.add(3585, 3630);
        fBeginWordSet.add(3648, 3652);
        fSuffixSet.add(3631);
        fSuffixSet.add(3654);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fSuffixSet.compact();
        fThaiWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
        fSuffixSet.freeze();
    }

    public ThaiBreakEngine() throws IOException {
        setCharacters(fThaiWordSet);
    }

    public boolean equals(Object obj) {
        return obj instanceof ThaiBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean handles(int c) {
        return UCharacter.getIntPropertyValue(c, 4106) == 38;
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x0092 A[EDGE_INSN: B:89:0x0092->B:29:0x0092 ?: BREAK  , SYNTHETIC] */
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
            if (r7 >= r2) goto L_0x0199
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
            goto L_0x009c
        L_0x0040:
            if (r9 <= r10) goto L_0x009c
            int r11 = r16.getIndex()
            if (r11 >= r2) goto L_0x0092
        L_0x0048:
            r11 = 1
            int r12 = r3 + 1
            int r12 = r12 % r5
            r12 = r6[r12]
            com.ibm.icu.text.DictionaryMatcher r13 = r0.fDictionary
            int r12 = r12.candidates(r1, r13, r2)
            if (r12 <= 0) goto L_0x0088
            r12 = 2
            if (r11 >= r12) goto L_0x0061
            int r12 = r3 % 3
            r12 = r6[r12]
            r12.markCurrent()
            r11 = 2
        L_0x0061:
            int r12 = r16.getIndex()
            if (r12 < r2) goto L_0x0068
            goto L_0x0092
        L_0x0068:
            int r12 = r3 + 2
            int r12 = r12 % r5
            r12 = r6[r12]
            com.ibm.icu.text.DictionaryMatcher r13 = r0.fDictionary
            int r12 = r12.candidates(r1, r13, r2)
            if (r12 <= 0) goto L_0x007d
            int r12 = r3 % 3
            r12 = r6[r12]
            r12.markCurrent()
            goto L_0x0092
        L_0x007d:
            int r12 = r3 + 1
            int r12 = r12 % r5
            r12 = r6[r12]
            boolean r12 = r12.backUp(r1)
            if (r12 != 0) goto L_0x0068
        L_0x0088:
            int r11 = r3 % 3
            r11 = r6[r11]
            boolean r11 = r11.backUp(r1)
            if (r11 != 0) goto L_0x0048
        L_0x0092:
            int r11 = r3 % 3
            r11 = r6[r11]
            int r7 = r11.acceptMarked(r1)
            int r3 = r3 + 1
        L_0x009c:
            int r11 = r16.getIndex()
            if (r11 >= r2) goto L_0x0107
            if (r7 >= r5) goto L_0x0107
            int r11 = r3 % 3
            r11 = r6[r11]
            com.ibm.icu.text.DictionaryMatcher r12 = r0.fDictionary
            int r11 = r11.candidates(r1, r12, r2)
            if (r11 > 0) goto L_0x0102
            if (r7 == 0) goto L_0x00bc
            int r11 = r3 % 3
            r11 = r6[r11]
            int r11 = r11.longestPrefix()
            if (r11 >= r5) goto L_0x0102
        L_0x00bc:
            int r11 = r8 + r7
            int r11 = r2 - r11
            char r12 = r16.current()
            r13 = r12
            r12 = r11
            r11 = r4
        L_0x00c7:
            r16.next()
            char r14 = r16.current()
            int r11 = r11 + r10
            int r12 = r12 + -1
            if (r12 > 0) goto L_0x00d4
            goto L_0x00f8
        L_0x00d4:
            com.ibm.icu.text.UnicodeSet r4 = fEndWordSet
            boolean r4 = r4.contains((int) r13)
            if (r4 == 0) goto L_0x00fe
            com.ibm.icu.text.UnicodeSet r4 = fBeginWordSet
            boolean r4 = r4.contains((int) r14)
            if (r4 == 0) goto L_0x00fe
            int r4 = r3 + 1
            int r4 = r4 % r5
            r4 = r6[r4]
            com.ibm.icu.text.DictionaryMatcher r5 = r0.fDictionary
            int r4 = r4.candidates(r1, r5, r2)
            int r5 = r8 + r7
            int r5 = r5 + r11
            r1.setIndex(r5)
            if (r4 <= 0) goto L_0x00fe
        L_0x00f8:
            if (r7 > 0) goto L_0x00fc
            int r3 = r3 + 1
        L_0x00fc:
            int r7 = r7 + r11
            goto L_0x0107
        L_0x00fe:
            r13 = r14
            r4 = 0
            r5 = 3
            goto L_0x00c7
        L_0x0102:
            int r4 = r8 + r7
            r1.setIndex(r4)
        L_0x0107:
            int r4 = r16.getIndex()
            r5 = r4
            if (r4 >= r2) goto L_0x0124
            com.ibm.icu.text.UnicodeSet r4 = fMarkSet
            char r10 = r16.current()
            boolean r4 = r4.contains((int) r10)
            if (r4 == 0) goto L_0x0124
            r16.next()
            int r4 = r16.getIndex()
            int r4 = r4 - r5
            int r7 = r7 + r4
            goto L_0x0107
        L_0x0124:
            int r4 = r16.getIndex()
            if (r4 >= r2) goto L_0x0181
            if (r7 <= 0) goto L_0x0181
            int r4 = r3 % 3
            r4 = r6[r4]
            com.ibm.icu.text.DictionaryMatcher r10 = r0.fDictionary
            int r4 = r4.candidates(r1, r10, r2)
            if (r4 > 0) goto L_0x017c
            com.ibm.icu.text.UnicodeSet r4 = fSuffixSet
            char r10 = r16.current()
            r11 = r10
            boolean r4 = r4.contains((int) r10)
            if (r4 == 0) goto L_0x017c
            r4 = 3631(0xe2f, float:5.088E-42)
            if (r11 != r4) goto L_0x0165
            com.ibm.icu.text.UnicodeSet r4 = fSuffixSet
            char r10 = r16.previous()
            boolean r4 = r4.contains((int) r10)
            if (r4 != 0) goto L_0x0162
            r16.next()
            r16.next()
            int r7 = r7 + 1
            char r11 = r16.current()
            goto L_0x0165
        L_0x0162:
            r16.next()
        L_0x0165:
            r4 = 3654(0xe46, float:5.12E-42)
            if (r11 != r4) goto L_0x0181
            char r10 = r16.previous()
            if (r10 == r4) goto L_0x0178
            r16.next()
            r16.next()
            int r7 = r7 + 1
            goto L_0x0181
        L_0x0178:
            r16.next()
            goto L_0x0181
        L_0x017c:
            int r4 = r8 + r7
            r1.setIndex(r4)
        L_0x0181:
            if (r7 <= 0) goto L_0x0193
            int r4 = r8 + r7
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            int r4 = r4.intValue()
            r10 = r19
            r10.push(r4)
            goto L_0x0195
        L_0x0193:
            r10 = r19
        L_0x0195:
            r4 = 0
            r5 = 3
            goto L_0x0020
        L_0x0199:
            r10 = r19
            int r4 = r19.peek()
            if (r4 < r2) goto L_0x01a6
            r19.pop()
            int r3 = r3 + -1
        L_0x01a6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.ThaiBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
