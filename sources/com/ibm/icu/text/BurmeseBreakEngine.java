package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import java.io.IOException;

class BurmeseBreakEngine extends DictionaryBreakEngine {
    private static final byte BURMESE_LOOKAHEAD = 3;
    private static final byte BURMESE_MIN_WORD = 2;
    private static final byte BURMESE_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte BURMESE_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fBeginWordSet = new UnicodeSet();
    private static UnicodeSet fBurmeseWordSet = new UnicodeSet();
    private static UnicodeSet fEndWordSet = new UnicodeSet(fBurmeseWordSet);
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private DictionaryMatcher fDictionary = DictionaryData.loadDictionaryFor("Mymr");

    static {
        fBurmeseWordSet.applyPattern("[[:Mymr:]&[:LineBreak=SA:]]");
        fBurmeseWordSet.compact();
        fMarkSet.applyPattern("[[:Mymr:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fBeginWordSet.add(4096, 4138);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fBurmeseWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }

    public BurmeseBreakEngine() throws IOException {
        setCharacters(fBurmeseWordSet);
    }

    public boolean equals(Object obj) {
        return obj instanceof BurmeseBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean handles(int c) {
        return UCharacter.getIntPropertyValue(c, 4106) == 28;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x013e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int divideUpDictionaryRange(java.text.CharacterIterator r16, int r17, int r18, com.ibm.icu.text.DictionaryBreakEngine.DequeI r19) {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r2 = r18
            int r3 = r2 - r17
            r4 = 0
            r5 = 2
            if (r3 >= r5) goto L_0x000c
            return r4
        L_0x000c:
            r3 = 0
            r6 = 3
            com.ibm.icu.text.DictionaryBreakEngine$PossibleWord[] r7 = new com.ibm.icu.text.DictionaryBreakEngine.PossibleWord[r6]
            r8 = r4
        L_0x0011:
            if (r8 >= r6) goto L_0x001d
            com.ibm.icu.text.DictionaryBreakEngine$PossibleWord r9 = new com.ibm.icu.text.DictionaryBreakEngine$PossibleWord
            r9.<init>()
            r7[r8] = r9
            int r8 = r8 + 1
            goto L_0x0011
        L_0x001d:
            r16.setIndex(r17)
        L_0x0020:
            int r8 = r16.getIndex()
            r9 = r8
            if (r8 >= r2) goto L_0x0145
            r8 = 0
            int r10 = r3 % 3
            r10 = r7[r10]
            com.ibm.icu.text.DictionaryMatcher r11 = r0.fDictionary
            int r10 = r10.candidates(r1, r11, r2)
            r11 = 1
            if (r10 != r11) goto L_0x0040
            int r12 = r3 % 3
            r12 = r7[r12]
            int r8 = r12.acceptMarked(r1)
            int r3 = r3 + 1
            goto L_0x00a4
        L_0x0040:
            if (r10 <= r11) goto L_0x00a4
            r12 = 0
            int r13 = r16.getIndex()
            if (r13 >= r2) goto L_0x009a
        L_0x0049:
            r13 = 1
            int r14 = r3 + 1
            int r14 = r14 % r6
            r14 = r7[r14]
            com.ibm.icu.text.DictionaryMatcher r4 = r0.fDictionary
            int r4 = r14.candidates(r1, r4, r2)
            if (r4 <= 0) goto L_0x008b
            if (r13 >= r5) goto L_0x0061
            int r4 = r3 % 3
            r4 = r7[r4]
            r4.markCurrent()
            r13 = 2
        L_0x0061:
            int r4 = r16.getIndex()
            if (r4 < r2) goto L_0x0068
            goto L_0x009a
        L_0x0068:
            int r4 = r3 + 2
            int r4 = r4 % r6
            r4 = r7[r4]
            com.ibm.icu.text.DictionaryMatcher r14 = r0.fDictionary
            int r4 = r4.candidates(r1, r14, r2)
            if (r4 <= 0) goto L_0x0080
            int r4 = r3 % 3
            r4 = r7[r4]
            r4.markCurrent()
            r4 = 1
            r12 = r4
            goto L_0x008b
        L_0x0080:
            int r4 = r3 + 1
            int r4 = r4 % r6
            r4 = r7[r4]
            boolean r4 = r4.backUp(r1)
            if (r4 != 0) goto L_0x0068
        L_0x008b:
            int r4 = r3 % 3
            r4 = r7[r4]
            boolean r4 = r4.backUp(r1)
            if (r4 == 0) goto L_0x009a
            if (r12 == 0) goto L_0x0098
            goto L_0x009a
        L_0x0098:
            r4 = 0
            goto L_0x0049
        L_0x009a:
            int r4 = r3 % 3
            r4 = r7[r4]
            int r8 = r4.acceptMarked(r1)
            int r3 = r3 + 1
        L_0x00a4:
            int r4 = r16.getIndex()
            if (r4 >= r2) goto L_0x010f
            if (r8 >= r6) goto L_0x010f
            int r4 = r3 % 3
            r4 = r7[r4]
            com.ibm.icu.text.DictionaryMatcher r12 = r0.fDictionary
            int r4 = r4.candidates(r1, r12, r2)
            if (r4 > 0) goto L_0x010a
            if (r8 == 0) goto L_0x00c4
            int r4 = r3 % 3
            r4 = r7[r4]
            int r4 = r4.longestPrefix()
            if (r4 >= r6) goto L_0x010a
        L_0x00c4:
            int r4 = r9 + r8
            int r4 = r2 - r4
            char r12 = r16.current()
            r13 = r12
            r12 = r4
            r4 = 0
        L_0x00cf:
            r16.next()
            char r14 = r16.current()
            int r4 = r4 + r11
            int r12 = r12 + -1
            if (r12 > 0) goto L_0x00dc
            goto L_0x0100
        L_0x00dc:
            com.ibm.icu.text.UnicodeSet r5 = fEndWordSet
            boolean r5 = r5.contains((int) r13)
            if (r5 == 0) goto L_0x0106
            com.ibm.icu.text.UnicodeSet r5 = fBeginWordSet
            boolean r5 = r5.contains((int) r14)
            if (r5 == 0) goto L_0x0106
            int r5 = r3 + 1
            int r5 = r5 % r6
            r5 = r7[r5]
            com.ibm.icu.text.DictionaryMatcher r6 = r0.fDictionary
            int r5 = r5.candidates(r1, r6, r2)
            int r6 = r9 + r8
            int r6 = r6 + r4
            r1.setIndex(r6)
            if (r5 <= 0) goto L_0x0106
        L_0x0100:
            if (r8 > 0) goto L_0x0104
            int r3 = r3 + 1
        L_0x0104:
            int r8 = r8 + r4
            goto L_0x010f
        L_0x0106:
            r13 = r14
            r5 = 2
            r6 = 3
            goto L_0x00cf
        L_0x010a:
            int r4 = r9 + r8
            r1.setIndex(r4)
        L_0x010f:
            int r4 = r16.getIndex()
            r5 = r4
            if (r4 >= r2) goto L_0x012c
            com.ibm.icu.text.UnicodeSet r4 = fMarkSet
            char r6 = r16.current()
            boolean r4 = r4.contains((int) r6)
            if (r4 == 0) goto L_0x012c
            r16.next()
            int r4 = r16.getIndex()
            int r4 = r4 - r5
            int r8 = r8 + r4
            goto L_0x010f
        L_0x012c:
            if (r8 <= 0) goto L_0x013e
            int r4 = r9 + r8
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            int r4 = r4.intValue()
            r6 = r19
            r6.push(r4)
            goto L_0x0140
        L_0x013e:
            r6 = r19
        L_0x0140:
            r4 = 0
            r5 = 2
            r6 = 3
            goto L_0x0020
        L_0x0145:
            r6 = r19
            int r4 = r19.peek()
            if (r4 < r2) goto L_0x0152
            r19.pop()
            int r3 = r3 + -1
        L_0x0152:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.BurmeseBreakEngine.divideUpDictionaryRange(java.text.CharacterIterator, int, int, com.ibm.icu.text.DictionaryBreakEngine$DequeI):int");
    }
}
