package com.ibm.icu.text;

import android.opengl.EGLExt;
import com.android.internal.logging.nano.MetricsProto;
import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.text.DictionaryBreakEngine;
import java.io.IOException;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
class CjkBreakEngine extends DictionaryBreakEngine {
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int kMaxKatakanaLength = 8;
    private static final int kint32max = Integer.MAX_VALUE;
    private static final int maxSnlp = 255;
    private DictionaryMatcher fDictionary;
    private static final UnicodeSet fHangulWordSet = new UnicodeSet();
    private static final UnicodeSet fHanWordSet = new UnicodeSet();
    private static final UnicodeSet fKatakanaWordSet = new UnicodeSet();
    private static final UnicodeSet fHiraganaWordSet = new UnicodeSet();

    static {
        fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        fHanWordSet.applyPattern("[:Han:]");
        fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        fHiraganaWordSet.applyPattern("[:Hiragana:]");
        fHangulWordSet.m211freeze();
        fHanWordSet.m211freeze();
        fKatakanaWordSet.m211freeze();
        fHiraganaWordSet.m211freeze();
    }

    public CjkBreakEngine(boolean korean) throws IOException {
        this.fDictionary = null;
        this.fDictionary = DictionaryData.loadDictionaryFor("Hira");
        if (korean) {
            setCharacters(fHangulWordSet);
            return;
        }
        UnicodeSet cjSet = new UnicodeSet();
        cjSet.addAll(fHanWordSet);
        cjSet.addAll(fKatakanaWordSet);
        cjSet.addAll(fHiraganaWordSet);
        cjSet.add(65392);
        cjSet.add(EGLExt.EGL_CONTEXT_FLAGS_KHR);
        setCharacters(cjSet);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CjkBreakEngine) {
            CjkBreakEngine other = (CjkBreakEngine) obj;
            return this.fSet.equals(other.fSet);
        }
        return false;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    private static int getKatakanaCost(int wordlength) {
        int[] katakanaCost = {8192, 984, MetricsProto.MetricsEvent.ACTION_NOTIFICATION_GROUP_EXPANDER, 240, 204, 252, 300, 372, 480};
        if (wordlength > 8) {
            return 8192;
        }
        return katakanaCost[wordlength];
    }

    private static boolean isKatakana(int value) {
        return (value >= 12449 && value <= 12542 && value != 12539) || (value >= 65382 && value <= 65439);
    }

    @Override // com.ibm.icu.text.DictionaryBreakEngine
    public int divideUpDictionaryRange(CharacterIterator inText, int startPos, int endPos, DictionaryBreakEngine.DequeI foundBreaks) {
        int[] charPositions;
        CharacterIterator text;
        int i;
        int numBreaks;
        StringBuffer s;
        int[] lengths;
        int[] values;
        int[] prev;
        int[] bestSnlp;
        int newSnlp;
        if (startPos >= endPos) {
            return 0;
        }
        inText.setIndex(startPos);
        int inputLength = endPos - startPos;
        int[] charPositions2 = new int[inputLength + 1];
        StringBuffer s2 = new StringBuffer("");
        inText.setIndex(startPos);
        while (inText.getIndex() < endPos) {
            s2.append(inText.current());
            inText.next();
        }
        String prenormstr = s2.toString();
        boolean isNormalized = Normalizer.quickCheck(prenormstr, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(prenormstr, Normalizer.NFKC, 0);
        int numCodePts = 0;
        if (isNormalized) {
            CharacterIterator text2 = new java.text.StringCharacterIterator(prenormstr);
            int index = 0;
            charPositions2[0] = 0;
            while (index < prenormstr.length()) {
                int codepoint = prenormstr.codePointAt(index);
                index += Character.charCount(codepoint);
                numCodePts++;
                charPositions2[numCodePts] = index;
            }
            charPositions = charPositions2;
            text = text2;
        } else {
            String normStr = Normalizer.normalize(prenormstr, Normalizer.NFKC);
            CharacterIterator text3 = new java.text.StringCharacterIterator(normStr);
            int[] charPositions3 = new int[normStr.length() + 1];
            Normalizer normalizer = new Normalizer(prenormstr, Normalizer.NFKC, 0);
            int index2 = 0;
            charPositions3[0] = 0;
            while (index2 < normalizer.endIndex()) {
                normalizer.next();
                numCodePts++;
                index2 = normalizer.getIndex();
                charPositions3[numCodePts] = index2;
            }
            charPositions = charPositions3;
            text = text3;
        }
        int[] bestSnlp2 = new int[numCodePts + 1];
        bestSnlp2[0] = 0;
        int i2 = 1;
        while (true) {
            i = Integer.MAX_VALUE;
            if (i2 > numCodePts) {
                break;
            }
            bestSnlp2[i2] = Integer.MAX_VALUE;
            i2++;
        }
        int i3 = numCodePts + 1;
        int[] prev2 = new int[i3];
        for (int i4 = 0; i4 <= numCodePts; i4++) {
            prev2[i4] = -1;
        }
        int[] values2 = new int[numCodePts];
        int[] lengths2 = new int[numCodePts];
        text.setIndex(0);
        boolean is_prev_katakana = false;
        int ix = 0;
        while (ix < numCodePts) {
            int ix2 = text.getIndex();
            int inputLength2 = inputLength;
            if (bestSnlp2[ix] == i) {
                s = s2;
                values = values2;
                prev = prev2;
                lengths = lengths2;
                bestSnlp = bestSnlp2;
            } else {
                s = s2;
                lengths = lengths2;
                int maxSearchLength = ix + 20 < numCodePts ? 20 : numCodePts - ix;
                int[] count_ = new int[1];
                values = values2;
                prev = prev2;
                bestSnlp = bestSnlp2;
                this.fDictionary.matches(text, maxSearchLength, lengths, count_, maxSearchLength, values);
                int count = count_[0];
                text.setIndex(ix2);
                if ((count == 0 || lengths[0] != 1) && CharacterIteration.current32(text) != Integer.MAX_VALUE && !fHangulWordSet.contains(CharacterIteration.current32(text))) {
                    values[count] = 255;
                    lengths[count] = 1;
                    count++;
                }
                for (int j = 0; j < count; j++) {
                    int newSnlp2 = bestSnlp[ix] + values[j];
                    if (newSnlp2 < bestSnlp[lengths[j] + ix]) {
                        bestSnlp[lengths[j] + ix] = newSnlp2;
                        prev[lengths[j] + ix] = ix;
                    }
                }
                int j2 = CharacterIteration.current32(text);
                boolean is_katakana = isKatakana(j2);
                if (!is_prev_katakana && is_katakana) {
                    int j3 = ix + 1;
                    CharacterIteration.next32(text);
                    while (j3 < numCodePts && j3 - ix < 20 && isKatakana(CharacterIteration.current32(text))) {
                        CharacterIteration.next32(text);
                        j3++;
                    }
                    if (j3 - ix < 20 && (newSnlp = bestSnlp[ix] + getKatakanaCost(j3 - ix)) < bestSnlp[j3]) {
                        bestSnlp[j3] = newSnlp;
                        prev[j3] = ix;
                    }
                }
                is_prev_katakana = is_katakana;
            }
            ix++;
            text.setIndex(ix2);
            CharacterIteration.next32(text);
            inputLength = inputLength2;
            s2 = s;
            lengths2 = lengths;
            bestSnlp2 = bestSnlp;
            values2 = values;
            prev2 = prev;
            i = Integer.MAX_VALUE;
        }
        int[] prev3 = prev2;
        int[] t_boundary = new int[numCodePts + 1];
        if (bestSnlp2[numCodePts] != Integer.MAX_VALUE) {
            int numBreaks2 = 0;
            for (int numBreaks3 = numCodePts; numBreaks3 > 0; numBreaks3 = prev3[numBreaks3]) {
                t_boundary[numBreaks2] = numBreaks3;
                numBreaks2++;
            }
            int i5 = numBreaks2 - 1;
            Assert.assrt(prev3[t_boundary[i5]] == 0);
            numBreaks = numBreaks2;
        } else {
            t_boundary[0] = numCodePts;
            numBreaks = 0 + 1;
        }
        if (foundBreaks.size() == 0 || foundBreaks.peek() < startPos) {
            t_boundary[numBreaks] = 0;
            numBreaks++;
        }
        int correctedNumBreaks = 0;
        for (int i6 = numBreaks - 1; i6 >= 0; i6--) {
            int pos = charPositions[t_boundary[i6]] + startPos;
            if (!foundBreaks.contains(pos) && pos != startPos) {
                foundBreaks.push(charPositions[t_boundary[i6]] + startPos);
                correctedNumBreaks++;
            }
        }
        if (!foundBreaks.isEmpty() && foundBreaks.peek() == endPos) {
            foundBreaks.pop();
            correctedNumBreaks--;
        }
        if (!foundBreaks.isEmpty()) {
            inText.setIndex(foundBreaks.peek());
        }
        return correctedNumBreaks;
    }
}
