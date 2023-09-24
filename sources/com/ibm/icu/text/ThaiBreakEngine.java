package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.io.IOException;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
class ThaiBreakEngine extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = 3;
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fThaiWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();
    private static UnicodeSet fSuffixSet = new UnicodeSet();

    static {
        fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        fThaiWordSet.compact();
        fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fThaiWordSet);
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
        fThaiWordSet.m211freeze();
        fMarkSet.m211freeze();
        fEndWordSet.m211freeze();
        fBeginWordSet.m211freeze();
        fSuffixSet.m211freeze();
    }

    public ThaiBreakEngine() throws IOException {
        setCharacters(fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }

    public boolean equals(Object obj) {
        return obj instanceof ThaiBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int c) {
        int script = UCharacter.getIntPropertyValue(c, 4106);
        return script == 38;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0092 A[EDGE_INSN: B:102:0x0092->B:32:0x0092 ?: BREAK  , SYNTHETIC] */
    @Override // com.ibm.icu.text.DictionaryBreakEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DictionaryBreakEngine.DequeI foundBreaks) {
        int wordsMatched;
        int i = 0;
        if (rangeEnd - rangeStart < 4) {
            return 0;
        }
        int wordsFound = 0;
        int i2 = 3;
        DictionaryBreakEngine.PossibleWord[] words = new DictionaryBreakEngine.PossibleWord[3];
        for (int i3 = 0; i3 < 3; i3++) {
            words[i3] = new DictionaryBreakEngine.PossibleWord();
        }
        fIter.setIndex(rangeStart);
        while (true) {
            int current = fIter.getIndex();
            if (current >= rangeEnd) {
                break;
            }
            int wordLength = 0;
            int candidates = words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd);
            if (candidates == 1) {
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                wordsFound++;
            } else if (candidates > 1) {
                if (fIter.getIndex() < rangeEnd) {
                    while (true) {
                        if (words[(wordsFound + 1) % i2].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                            if (1 < 2) {
                                words[wordsFound % 3].markCurrent();
                            }
                            if (fIter.getIndex() < rangeEnd) {
                                while (words[(wordsFound + 2) % i2].candidates(fIter, this.fDictionary, rangeEnd) <= 0) {
                                    if (!words[(wordsFound + 1) % i2].backUp(fIter)) {
                                        wordsMatched = wordsFound % 3;
                                        if (!words[wordsMatched].backUp(fIter)) {
                                        }
                                    }
                                }
                                words[wordsFound % 3].markCurrent();
                                break;
                            }
                            break;
                        }
                        wordsMatched = wordsFound % 3;
                        if (!words[wordsMatched].backUp(fIter)) {
                            break;
                        }
                    }
                }
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                wordsFound++;
            }
            if (fIter.getIndex() < rangeEnd && wordLength < i2) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0 && (wordLength == 0 || words[wordsFound % 3].longestPrefix() < i2)) {
                    int remaining = rangeEnd - (current + wordLength);
                    int pc = fIter.current();
                    int pc2 = pc;
                    int remaining2 = remaining;
                    int chars = i;
                    while (true) {
                        fIter.next();
                        int uc = fIter.current();
                        chars++;
                        remaining2--;
                        if (remaining2 <= 0) {
                            break;
                        }
                        if (fEndWordSet.contains(pc2) && fBeginWordSet.contains(uc)) {
                            int candidate = words[(wordsFound + 1) % i2].candidates(fIter, this.fDictionary, rangeEnd);
                            fIter.setIndex(current + wordLength + chars);
                            if (candidate > 0) {
                                break;
                            }
                        }
                        pc2 = uc;
                        i2 = 3;
                    }
                    if (wordLength <= 0) {
                        wordsFound++;
                    }
                    wordLength += chars;
                } else {
                    fIter.setIndex(current + wordLength);
                }
            }
            while (true) {
                int currPos = fIter.getIndex();
                if (currPos >= rangeEnd || !fMarkSet.contains(fIter.current())) {
                    break;
                }
                fIter.next();
                wordLength += fIter.getIndex() - currPos;
            }
            if (fIter.getIndex() < rangeEnd && wordLength > 0) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0) {
                    UnicodeSet unicodeSet = fSuffixSet;
                    int current2 = fIter.current();
                    int uc2 = current2;
                    if (unicodeSet.contains(current2)) {
                        if (uc2 == 3631) {
                            if (!fSuffixSet.contains(fIter.previous())) {
                                fIter.next();
                                fIter.next();
                                wordLength++;
                                uc2 = fIter.current();
                            } else {
                                fIter.next();
                            }
                        }
                        if (uc2 == 3654) {
                            if (fIter.previous() != '\u0e46') {
                                fIter.next();
                                fIter.next();
                                wordLength++;
                            } else {
                                fIter.next();
                            }
                        }
                    }
                }
                fIter.setIndex(current + wordLength);
            }
            if (wordLength > 0) {
                foundBreaks.push(Integer.valueOf(current + wordLength).intValue());
            }
            i = 0;
            i2 = 3;
        }
        if (foundBreaks.peek() >= rangeEnd) {
            foundBreaks.pop();
            return wordsFound - 1;
        }
        return wordsFound;
    }
}
