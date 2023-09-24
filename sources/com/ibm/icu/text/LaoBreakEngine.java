package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.io.IOException;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
class LaoBreakEngine extends DictionaryBreakEngine {
    private static final byte LAO_LOOKAHEAD = 3;
    private static final byte LAO_MIN_WORD = 2;
    private static final byte LAO_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte LAO_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fEndWordSet;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fLaoWordSet = new UnicodeSet();
    private static UnicodeSet fMarkSet = new UnicodeSet();
    private static UnicodeSet fBeginWordSet = new UnicodeSet();

    static {
        fLaoWordSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]]");
        fLaoWordSet.compact();
        fMarkSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fLaoWordSet);
        fEndWordSet.remove(3776, 3780);
        fBeginWordSet.add(3713, 3758);
        fBeginWordSet.add(3804, 3805);
        fBeginWordSet.add(3776, 3780);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fLaoWordSet.m211freeze();
        fMarkSet.m211freeze();
        fEndWordSet.m211freeze();
        fBeginWordSet.m211freeze();
    }

    public LaoBreakEngine() throws IOException {
        setCharacters(fLaoWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Laoo");
    }

    public boolean equals(Object obj) {
        return obj instanceof LaoBreakEngine;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine, com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int c) {
        int script = UCharacter.getIntPropertyValue(c, 4106);
        return script == 24;
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine
    public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DictionaryBreakEngine.DequeI foundBreaks) {
        char c = 2;
        if (rangeEnd - rangeStart < 2) {
            return 0;
        }
        int wordsFound = 0;
        int i = 3;
        DictionaryBreakEngine.PossibleWord[] words = new DictionaryBreakEngine.PossibleWord[3];
        for (int i2 = 0; i2 < 3; i2++) {
            words[i2] = new DictionaryBreakEngine.PossibleWord();
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
                    while (true) {
                        if (words[(wordsFound + 1) % i].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                            if (1 < c) {
                                words[wordsFound % 3].markCurrent();
                            }
                            if (fIter.getIndex() >= rangeEnd) {
                                break;
                            }
                            while (true) {
                                if (words[(wordsFound + 2) % i].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                                    words[wordsFound % 3].markCurrent();
                                    foundBest = true;
                                    break;
                                } else if (!words[(wordsFound + 1) % i].backUp(fIter)) {
                                    break;
                                }
                            }
                        }
                        if (!words[wordsFound % 3].backUp(fIter) || foundBest) {
                            break;
                        }
                    }
                }
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                wordsFound++;
            }
            if (fIter.getIndex() < rangeEnd && wordLength < i) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0 && (wordLength == 0 || words[wordsFound % 3].longestPrefix() < i)) {
                    int remaining = rangeEnd - (current + wordLength);
                    int pc = fIter.current();
                    int pc2 = pc;
                    int remaining2 = remaining;
                    int chars = 0;
                    while (true) {
                        fIter.next();
                        int uc = fIter.current();
                        chars++;
                        remaining2--;
                        if (remaining2 <= 0) {
                            break;
                        }
                        if (fEndWordSet.contains(pc2) && fBeginWordSet.contains(uc)) {
                            int candidate = words[(wordsFound + 1) % i].candidates(fIter, this.fDictionary, rangeEnd);
                            fIter.setIndex(current + wordLength + chars);
                            if (candidate > 0) {
                                break;
                            }
                        }
                        pc2 = uc;
                        i = 3;
                    }
                    if (wordLength <= 0) {
                        wordsFound++;
                    }
                    wordLength += chars;
                } else {
                    int chars2 = current + wordLength;
                    fIter.setIndex(chars2);
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
            c = 2;
            i = 3;
        }
        if (foundBreaks.peek() >= rangeEnd) {
            foundBreaks.pop();
            return wordsFound - 1;
        }
        return wordsFound;
    }
}
