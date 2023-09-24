package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.io.IOException;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
class KhmerBreakEngine extends DictionaryBreakEngine {
    private static final byte KHMER_LOOKAHEAD = 3;
    private static final byte KHMER_MIN_WORD = 2;
    private static final byte KHMER_MIN_WORD_SPAN = 4;
    private static final byte KHMER_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte KHMER_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fKhmerWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();

    static {
        fKhmerWordSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]]");
        fKhmerWordSet.compact();
        fMarkSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fKhmerWordSet);
        fBeginWordSet.add(6016, 6067);
        fEndWordSet.remove(6098);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fKhmerWordSet.m211freeze();
        fMarkSet.m211freeze();
        fEndWordSet.m211freeze();
        fBeginWordSet.m211freeze();
    }

    public KhmerBreakEngine() throws IOException {
        setCharacters(fKhmerWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Khmr");
    }

    public boolean equals(Object obj) {
        return obj instanceof KhmerBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int c) {
        int script = UCharacter.getIntPropertyValue(c, 4106);
        return script == 23;
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine
    public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DictionaryBreakEngine.DequeI foundBreaks) {
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
                boolean foundBest = false;
                if (fIter.getIndex() < rangeEnd) {
                    do {
                        if (words[(wordsFound + 1) % i2].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                            if (1 < 2) {
                                words[wordsFound % 3].markCurrent();
                            }
                            if (fIter.getIndex() >= rangeEnd) {
                                break;
                            }
                            while (true) {
                                if (words[(wordsFound + 2) % i2].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                                    words[wordsFound % 3].markCurrent();
                                    foundBest = true;
                                    break;
                                } else if (!words[(wordsFound + 1) % i2].backUp(fIter)) {
                                    break;
                                }
                            }
                        }
                        int wordsMatched = wordsFound % 3;
                        if (!words[wordsMatched].backUp(fIter)) {
                            break;
                        }
                    } while (!foundBest);
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
