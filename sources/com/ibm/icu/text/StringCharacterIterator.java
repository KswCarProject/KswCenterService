package com.ibm.icu.text;

import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.text.CharacterIterator;

@Deprecated
/* loaded from: classes5.dex */
public final class StringCharacterIterator implements CharacterIterator {
    private int begin;
    private int end;
    private int pos;
    private String text;

    @Deprecated
    public StringCharacterIterator(String text) {
        this(text, 0);
    }

    @Deprecated
    public StringCharacterIterator(String text, int pos) {
        this(text, 0, text.length(), pos);
    }

    @Deprecated
    public StringCharacterIterator(String text, int begin, int end, int pos) {
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

    @Deprecated
    public void setText(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.begin = 0;
        this.end = text.length();
        this.pos = 0;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char first() {
        this.pos = this.begin;
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char last() {
        if (this.end != this.begin) {
            this.pos = this.end - 1;
        } else {
            this.pos = this.end;
        }
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char setIndex(int p) {
        if (p < this.begin || p > this.end) {
            throw new IllegalArgumentException("Invalid index");
        }
        this.pos = p;
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char current() {
        if (this.pos >= this.begin && this.pos < this.end) {
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char next() {
        if (this.pos < this.end - 1) {
            this.pos++;
            return this.text.charAt(this.pos);
        }
        this.pos = this.end;
        return '\uffff';
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char previous() {
        if (this.pos > this.begin) {
            this.pos--;
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getBeginIndex() {
        return this.begin;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getEndIndex() {
        return this.end;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getIndex() {
        return this.pos;
    }

    @Deprecated
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StringCharacterIterator) {
            StringCharacterIterator that = (StringCharacterIterator) obj;
            return hashCode() == that.hashCode() && this.text.equals(that.text) && this.pos == that.pos && this.begin == that.begin && this.end == that.end;
        }
        return false;
    }

    @Deprecated
    public int hashCode() {
        return ((this.text.hashCode() ^ this.pos) ^ this.begin) ^ this.end;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public Object clone() {
        try {
            StringCharacterIterator other = (StringCharacterIterator) super.clone();
            return other;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }
}
