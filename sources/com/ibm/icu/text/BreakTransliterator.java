package com.ibm.icu.text;

import android.net.wifi.WifiEnterpriseConfig;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
final class BreakTransliterator extends Transliterator {
    static final int LETTER_OR_MARK_MASK = 510;

    /* renamed from: bi */
    private BreakIterator f2541bi;
    private int[] boundaries;
    private int boundaryCount;
    private String insertion;

    public BreakTransliterator(String ID, UnicodeFilter filter, BreakIterator bi, String insertion) {
        super(ID, filter);
        this.boundaries = new int[50];
        this.boundaryCount = 0;
        this.f2541bi = bi;
        this.insertion = insertion;
    }

    public BreakTransliterator(String ID, UnicodeFilter filter) {
        this(ID, filter, null, WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
    }

    public String getInsertion() {
        return this.insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public BreakIterator getBreakIterator() {
        if (this.f2541bi == null) {
            this.f2541bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
        }
        return this.f2541bi;
    }

    public void setBreakIterator(BreakIterator bi) {
        this.f2541bi = bi;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected synchronized void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean incremental) {
        this.boundaryCount = 0;
        getBreakIterator();
        this.f2541bi.setText(new ReplaceableCharacterIterator(text, pos.start, pos.limit, pos.start));
        int first = this.f2541bi.first();
        while (true) {
            int boundary = first;
            if (boundary == -1 || boundary >= pos.limit) {
                break;
            }
            if (boundary != 0) {
                int cp = UTF16.charAt(text, boundary - 1);
                int type = UCharacter.getType(cp);
                if (((1 << type) & 510) != 0) {
                    int cp2 = UTF16.charAt(text, boundary);
                    int type2 = UCharacter.getType(cp2);
                    if (((1 << type2) & 510) != 0) {
                        if (this.boundaryCount >= this.boundaries.length) {
                            int[] temp = new int[this.boundaries.length * 2];
                            System.arraycopy(this.boundaries, 0, temp, 0, this.boundaries.length);
                            this.boundaries = temp;
                        }
                        int[] temp2 = this.boundaries;
                        int i = this.boundaryCount;
                        this.boundaryCount = i + 1;
                        temp2[i] = boundary;
                    }
                }
            }
            first = this.f2541bi.next();
        }
        int delta = 0;
        int lastBoundary = 0;
        if (this.boundaryCount != 0) {
            delta = this.boundaryCount * this.insertion.length();
            lastBoundary = this.boundaries[this.boundaryCount - 1];
            while (this.boundaryCount > 0) {
                int[] iArr = this.boundaries;
                int i2 = this.boundaryCount - 1;
                this.boundaryCount = i2;
                int boundary2 = iArr[i2];
                text.replace(boundary2, boundary2, this.insertion);
            }
        }
        pos.contextLimit += delta;
        pos.limit += delta;
        pos.start = incremental ? lastBoundary + delta : pos.limit;
    }

    static void register() {
        Transliterator trans = new BreakTransliterator("Any-BreakInternal", null);
        Transliterator.registerInstance(trans, false);
    }

    /* loaded from: classes5.dex */
    static final class ReplaceableCharacterIterator implements CharacterIterator {
        private int begin;
        private int end;
        private int pos;
        private Replaceable text;

        public ReplaceableCharacterIterator(Replaceable text, int begin, int end, int pos) {
            if (text == null) {
                throw new NullPointerException();
            }
            this.text = text;
            if (begin < 0 || begin > end || end > text.length()) {
                throw new IllegalArgumentException("Invalid substring range");
            }
            if (pos < begin || pos > end) {
                throw new IllegalArgumentException("Invalid position");
            }
            this.begin = begin;
            this.end = end;
            this.pos = pos;
        }

        public void setText(Replaceable text) {
            if (text == null) {
                throw new NullPointerException();
            }
            this.text = text;
            this.begin = 0;
            this.end = text.length();
            this.pos = 0;
        }

        @Override // java.text.CharacterIterator
        public char first() {
            this.pos = this.begin;
            return current();
        }

        @Override // java.text.CharacterIterator
        public char last() {
            if (this.end != this.begin) {
                this.pos = this.end - 1;
            } else {
                this.pos = this.end;
            }
            return current();
        }

        @Override // java.text.CharacterIterator
        public char setIndex(int p) {
            if (p < this.begin || p > this.end) {
                throw new IllegalArgumentException("Invalid index");
            }
            this.pos = p;
            return current();
        }

        @Override // java.text.CharacterIterator
        public char current() {
            if (this.pos >= this.begin && this.pos < this.end) {
                return this.text.charAt(this.pos);
            }
            return '\uffff';
        }

        @Override // java.text.CharacterIterator
        public char next() {
            if (this.pos < this.end - 1) {
                this.pos++;
                return this.text.charAt(this.pos);
            }
            this.pos = this.end;
            return '\uffff';
        }

        @Override // java.text.CharacterIterator
        public char previous() {
            if (this.pos > this.begin) {
                this.pos--;
                return this.text.charAt(this.pos);
            }
            return '\uffff';
        }

        @Override // java.text.CharacterIterator
        public int getBeginIndex() {
            return this.begin;
        }

        @Override // java.text.CharacterIterator
        public int getEndIndex() {
            return this.end;
        }

        @Override // java.text.CharacterIterator
        public int getIndex() {
            return this.pos;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ReplaceableCharacterIterator) {
                ReplaceableCharacterIterator that = (ReplaceableCharacterIterator) obj;
                return hashCode() == that.hashCode() && this.text.equals(that.text) && this.pos == that.pos && this.begin == that.begin && this.end == that.end;
            }
            return false;
        }

        public int hashCode() {
            return ((this.text.hashCode() ^ this.pos) ^ this.begin) ^ this.end;
        }

        @Override // java.text.CharacterIterator
        public Object clone() {
            try {
                ReplaceableCharacterIterator other = (ReplaceableCharacterIterator) super.clone();
                return other;
            } catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException();
            }
        }
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = getFilterAsUnicodeSet(inputFilter);
        if (myFilter.size() != 0) {
            targetSet.addAll(this.insertion);
        }
    }
}
