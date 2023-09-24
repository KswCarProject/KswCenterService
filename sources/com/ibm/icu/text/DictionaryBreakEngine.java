package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
abstract class DictionaryBreakEngine implements LanguageBreakEngine {
    UnicodeSet fSet = new UnicodeSet();

    abstract int divideUpDictionaryRange(CharacterIterator characterIterator, int i, int i2, DequeI dequeI);

    /* loaded from: classes5.dex */
    static class PossibleWord {
        private static final int POSSIBLE_WORD_LIST_MAX = 20;
        private int current;
        private int mark;
        private int prefix;
        private int[] lengths = new int[20];
        private int[] count = new int[1];
        private int offset = -1;

        public int candidates(CharacterIterator fIter, DictionaryMatcher dict, int rangeEnd) {
            int start = fIter.getIndex();
            if (start != this.offset) {
                this.offset = start;
                this.prefix = dict.matches(fIter, rangeEnd - start, this.lengths, this.count, this.lengths.length);
                if (this.count[0] <= 0) {
                    fIter.setIndex(start);
                }
            }
            if (this.count[0] > 0) {
                fIter.setIndex(this.lengths[this.count[0] - 1] + start);
            }
            this.current = this.count[0] - 1;
            this.mark = this.current;
            return this.count[0];
        }

        public int acceptMarked(CharacterIterator fIter) {
            fIter.setIndex(this.offset + this.lengths[this.mark]);
            return this.lengths[this.mark];
        }

        public boolean backUp(CharacterIterator fIter) {
            if (this.current > 0) {
                int i = this.offset;
                int[] iArr = this.lengths;
                int i2 = this.current - 1;
                this.current = i2;
                fIter.setIndex(i + iArr[i2]);
                return true;
            }
            return false;
        }

        public int longestPrefix() {
            return this.prefix;
        }

        public void markCurrent() {
            this.mark = this.current;
        }
    }

    /* loaded from: classes5.dex */
    static class DequeI implements Cloneable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int[] data = new int[50];
        private int lastIdx = 4;
        private int firstIdx = 4;

        DequeI() {
        }

        public Object clone() throws CloneNotSupportedException {
            DequeI result = (DequeI) super.clone();
            result.data = (int[]) this.data.clone();
            return result;
        }

        int size() {
            return this.firstIdx - this.lastIdx;
        }

        boolean isEmpty() {
            return size() == 0;
        }

        private void grow() {
            int[] newData = new int[this.data.length * 2];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }

        void offer(int v) {
            int[] iArr = this.data;
            int i = this.lastIdx - 1;
            this.lastIdx = i;
            iArr[i] = v;
        }

        void push(int v) {
            if (this.firstIdx >= this.data.length) {
                grow();
            }
            int[] iArr = this.data;
            int i = this.firstIdx;
            this.firstIdx = i + 1;
            iArr[i] = v;
        }

        int pop() {
            int[] iArr = this.data;
            int i = this.firstIdx - 1;
            this.firstIdx = i;
            return iArr[i];
        }

        int peek() {
            return this.data[this.firstIdx - 1];
        }

        int peekLast() {
            return this.data[this.lastIdx];
        }

        int pollLast() {
            int[] iArr = this.data;
            int i = this.lastIdx;
            this.lastIdx = i + 1;
            return iArr[i];
        }

        boolean contains(int v) {
            for (int i = this.lastIdx; i < this.firstIdx; i++) {
                if (this.data[i] == v) {
                    return true;
                }
            }
            return false;
        }

        int elementAt(int i) {
            return this.data[this.lastIdx + i];
        }

        void removeAllElements() {
            this.firstIdx = 4;
            this.lastIdx = 4;
        }
    }

    @Override // com.ibm.icu.text.LanguageBreakEngine
    public boolean handles(int c) {
        return this.fSet.contains(c);
    }

    @Override // com.ibm.icu.text.LanguageBreakEngine
    public int findBreaks(CharacterIterator text, int startPos, int endPos, DequeI foundBreaks) {
        int current;
        int start = text.getIndex();
        int c = CharacterIteration.current32(text);
        while (true) {
            current = text.getIndex();
            if (current >= endPos || !this.fSet.contains(c)) {
                break;
            }
            CharacterIteration.next32(text);
            c = CharacterIteration.current32(text);
        }
        int result = divideUpDictionaryRange(text, start, current, foundBreaks);
        text.setIndex(current);
        return result;
    }

    void setCharacters(UnicodeSet set) {
        this.fSet = new UnicodeSet(set);
        this.fSet.compact();
    }
}
