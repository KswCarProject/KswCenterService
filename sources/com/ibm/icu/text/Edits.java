package com.ibm.icu.text;

import java.nio.BufferOverflowException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class Edits {
    private static final int LENGTH_IN_1TRAIL = 61;
    private static final int LENGTH_IN_2TRAIL = 62;
    private static final int MAX_SHORT_CHANGE = 28671;
    private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
    private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
    private static final int MAX_UNCHANGED = 4095;
    private static final int MAX_UNCHANGED_LENGTH = 4096;
    private static final int SHORT_CHANGE_NUM_MASK = 511;
    private static final int STACK_CAPACITY = 100;
    private char[] array = new char[100];
    private int delta;
    private int length;
    private int numChanges;

    public void reset() {
        this.numChanges = 0;
        this.delta = 0;
        this.length = 0;
    }

    private void setLastUnit(int last) {
        this.array[this.length - 1] = (char) last;
    }

    private int lastUnit() {
        if (this.length > 0) {
            return this.array[this.length - 1];
        }
        return 65535;
    }

    public void addUnchanged(int unchangedLength) {
        if (unchangedLength < 0) {
            throw new IllegalArgumentException("addUnchanged(" + unchangedLength + "): length must not be negative");
        }
        int last = lastUnit();
        if (last < 4095) {
            int remaining = 4095 - last;
            if (remaining >= unchangedLength) {
                setLastUnit(last + unchangedLength);
                return;
            } else {
                setLastUnit(4095);
                unchangedLength -= remaining;
            }
        }
        while (unchangedLength >= 4096) {
            append(4095);
            unchangedLength -= 4096;
        }
        if (unchangedLength > 0) {
            append(unchangedLength - 1);
        }
    }

    public void addReplace(int oldLength, int newLength) {
        int head;
        int head2;
        int head3;
        if (oldLength < 0 || newLength < 0) {
            throw new IllegalArgumentException("addReplace(" + oldLength + ", " + newLength + "): both lengths must be non-negative");
        } else if (oldLength == 0 && newLength == 0) {
        } else {
            this.numChanges++;
            int newDelta = newLength - oldLength;
            if (newDelta != 0) {
                if ((newDelta > 0 && this.delta >= 0 && newDelta > Integer.MAX_VALUE - this.delta) || (newDelta < 0 && this.delta < 0 && newDelta < Integer.MIN_VALUE - this.delta)) {
                    throw new IndexOutOfBoundsException();
                }
                this.delta += newDelta;
            }
            if (oldLength > 0 && oldLength <= 6 && newLength <= 7) {
                int u = (oldLength << 12) | (newLength << 9);
                int last = lastUnit();
                if (4095 < last && last < MAX_SHORT_CHANGE && (last & (-512)) == u && (last & 511) < 511) {
                    setLastUnit(last + 1);
                } else {
                    append(u);
                }
            } else if (oldLength < 61 && newLength < 61) {
                int head4 = (oldLength << 6) | 28672;
                append(head4 | newLength);
            } else if (this.array.length - this.length >= 5 || growArray()) {
                int limit = this.length + 1;
                if (oldLength < 61) {
                    head = (oldLength << 6) | 28672;
                } else if (oldLength > 32767) {
                    head = (((oldLength >> 30) + 62) << 6) | 28672;
                    int limit2 = limit + 1;
                    this.array[limit] = (char) ((oldLength >> 15) | 32768);
                    limit = limit2 + 1;
                    this.array[limit2] = (char) (oldLength | 32768);
                } else {
                    head = 28672 | 3904;
                    this.array[limit] = (char) (oldLength | 32768);
                    limit++;
                }
                if (newLength < 61) {
                    head2 = head | newLength;
                } else if (newLength <= 32767) {
                    this.array[limit] = (char) (newLength | 32768);
                    head3 = head | 61;
                    limit++;
                    this.array[this.length] = (char) head3;
                    this.length = limit;
                } else {
                    head2 = head | ((newLength >> 30) + 62);
                    int limit3 = limit + 1;
                    this.array[limit] = (char) ((newLength >> 15) | 32768);
                    limit = limit3 + 1;
                    this.array[limit3] = (char) (newLength | 32768);
                }
                head3 = head2;
                this.array[this.length] = (char) head3;
                this.length = limit;
            }
        }
    }

    private void append(int r) {
        if (this.length < this.array.length || growArray()) {
            char[] cArr = this.array;
            int i = this.length;
            this.length = i + 1;
            cArr[i] = (char) r;
        }
    }

    private boolean growArray() {
        int newCapacity;
        if (this.array.length == 100) {
            newCapacity = 2000;
        } else if (this.array.length == Integer.MAX_VALUE) {
            throw new BufferOverflowException();
        } else {
            if (this.array.length >= 1073741823) {
                newCapacity = Integer.MAX_VALUE;
            } else {
                newCapacity = this.array.length * 2;
            }
        }
        if (newCapacity - this.array.length < 5) {
            throw new BufferOverflowException();
        }
        this.array = Arrays.copyOf(this.array, newCapacity);
        return true;
    }

    public int lengthDelta() {
        return this.delta;
    }

    public boolean hasChanges() {
        return this.numChanges != 0;
    }

    public int numberOfChanges() {
        return this.numChanges;
    }

    /* loaded from: classes5.dex */
    public static final class Iterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final char[] array;
        private boolean changed;
        private final boolean coarse;
        private int destIndex;
        private int dir;
        private int index;
        private final int length;
        private int newLength_;
        private int oldLength_;
        private final boolean onlyChanges_;
        private int remaining;
        private int replIndex;
        private int srcIndex;

        private Iterator(char[] a, int len, boolean oc, boolean crs) {
            this.array = a;
            this.length = len;
            this.onlyChanges_ = oc;
            this.coarse = crs;
        }

        private int readLength(int head) {
            if (head < 61) {
                return head;
            }
            if (head < 62) {
                char[] cArr = this.array;
                int i = this.index;
                this.index = i + 1;
                return cArr[i] & '\u7fff';
            }
            int len = ((head & 1) << 30) | ((this.array[this.index] & '\u7fff') << 15) | (this.array[this.index + 1] & '\u7fff');
            this.index += 2;
            return len;
        }

        private void updateNextIndexes() {
            this.srcIndex += this.oldLength_;
            if (this.changed) {
                this.replIndex += this.newLength_;
            }
            this.destIndex += this.newLength_;
        }

        private void updatePreviousIndexes() {
            this.srcIndex -= this.oldLength_;
            if (this.changed) {
                this.replIndex -= this.newLength_;
            }
            this.destIndex -= this.newLength_;
        }

        private boolean noNext() {
            this.dir = 0;
            this.changed = false;
            this.newLength_ = 0;
            this.oldLength_ = 0;
            return false;
        }

        public boolean next() {
            return next(this.onlyChanges_);
        }

        private boolean next(boolean onlyChanges) {
            char c;
            if (this.dir > 0) {
                updateNextIndexes();
            } else if (this.dir < 0 && this.remaining > 0) {
                this.index++;
                this.dir = 1;
                return true;
            } else {
                this.dir = 1;
            }
            if (this.remaining >= 1) {
                if (this.remaining > 1) {
                    this.remaining--;
                    return true;
                }
                this.remaining = 0;
            }
            if (this.index >= this.length) {
                return noNext();
            }
            char[] cArr = this.array;
            int i = this.index;
            this.index = i + 1;
            char c2 = cArr[i];
            if (c2 <= '\u0fff') {
                this.changed = false;
                this.oldLength_ = c2 + 1;
                while (this.index < this.length) {
                    char c3 = this.array[this.index];
                    c2 = c3;
                    if (c3 > '\u0fff') {
                        break;
                    }
                    this.index++;
                    this.oldLength_ += c2 + 1;
                }
                this.newLength_ = this.oldLength_;
                if (!onlyChanges) {
                    return true;
                }
                updateNextIndexes();
                if (this.index >= this.length) {
                    return noNext();
                }
                this.index++;
            }
            this.changed = true;
            if (c2 <= Edits.MAX_SHORT_CHANGE) {
                int oldLen = c2 >> '\f';
                int newLen = (c2 >> '\t') & 7;
                int num = (c2 & '\u01ff') + 1;
                if (this.coarse) {
                    this.oldLength_ = num * oldLen;
                    this.newLength_ = num * newLen;
                } else {
                    this.oldLength_ = oldLen;
                    this.newLength_ = newLen;
                    if (num > 1) {
                        this.remaining = num;
                    }
                    return true;
                }
            } else {
                this.oldLength_ = readLength((c2 >> 6) & 63);
                this.newLength_ = readLength(c2 & '?');
                if (!this.coarse) {
                    return true;
                }
            }
            while (this.index < this.length && (c = this.array[this.index]) > '\u0fff') {
                this.index++;
                if (c <= Edits.MAX_SHORT_CHANGE) {
                    int num2 = (c & '\u01ff') + 1;
                    this.oldLength_ += (c >> '\f') * num2;
                    this.newLength_ += ((c >> '\t') & 7) * num2;
                } else {
                    this.oldLength_ += readLength((c >> 6) & 63);
                    this.newLength_ += readLength(c & '?');
                }
            }
            return true;
        }

        private boolean previous() {
            char c;
            char c2;
            char c3;
            if (this.dir >= 0) {
                if (this.dir > 0) {
                    if (this.remaining > 0) {
                        this.index--;
                        this.dir = -1;
                        return true;
                    }
                    updateNextIndexes();
                }
                this.dir = -1;
            }
            if (this.remaining > 0) {
                if (this.remaining <= (this.array[this.index] & '\u01ff')) {
                    this.remaining++;
                    updatePreviousIndexes();
                    return true;
                }
                this.remaining = 0;
            }
            int u = this.index;
            if (u <= 0) {
                return noNext();
            }
            char[] cArr = this.array;
            int i = this.index - 1;
            this.index = i;
            char c4 = cArr[i];
            if (c4 <= '\u0fff') {
                this.changed = false;
                this.oldLength_ = c4 + 1;
                while (this.index > 0 && (c3 = this.array[this.index - 1]) <= '\u0fff') {
                    this.index--;
                    this.oldLength_ += c3 + 1;
                }
                this.newLength_ = this.oldLength_;
                updatePreviousIndexes();
                return true;
            }
            this.changed = true;
            if (c4 <= Edits.MAX_SHORT_CHANGE) {
                int oldLen = c4 >> '\f';
                int newLen = (c4 >> '\t') & 7;
                int num = (c4 & '\u01ff') + 1;
                if (this.coarse) {
                    this.oldLength_ = num * oldLen;
                    this.newLength_ = num * newLen;
                } else {
                    this.oldLength_ = oldLen;
                    this.newLength_ = newLen;
                    if (num > 1) {
                        this.remaining = 1;
                    }
                    updatePreviousIndexes();
                    return true;
                }
            } else {
                if (c4 <= '\u7fff') {
                    this.oldLength_ = readLength((c4 >> 6) & 63);
                    this.newLength_ = readLength(c4 & '?');
                } else {
                    do {
                        char[] cArr2 = this.array;
                        int i2 = this.index - 1;
                        this.index = i2;
                        c = cArr2[i2];
                    } while (c > '\u7fff');
                    int headIndex = this.index;
                    this.index = headIndex + 1;
                    this.oldLength_ = readLength((c >> 6) & 63);
                    this.newLength_ = readLength(c & '?');
                    this.index = headIndex;
                }
                if (!this.coarse) {
                    updatePreviousIndexes();
                    return true;
                }
            }
            while (this.index > 0 && (c2 = this.array[this.index - 1]) > '\u0fff') {
                this.index--;
                if (c2 <= Edits.MAX_SHORT_CHANGE) {
                    int num2 = (c2 & '\u01ff') + 1;
                    this.oldLength_ += (c2 >> '\f') * num2;
                    this.newLength_ += ((c2 >> '\t') & 7) * num2;
                } else if (c2 <= '\u7fff') {
                    int headIndex2 = this.index;
                    this.index = headIndex2 + 1;
                    this.oldLength_ += readLength((c2 >> 6) & 63);
                    this.newLength_ += readLength(c2 & '?');
                    this.index = headIndex2;
                }
            }
            updatePreviousIndexes();
            return true;
        }

        public boolean findSourceIndex(int i) {
            return findIndex(i, true) == 0;
        }

        public boolean findDestinationIndex(int i) {
            return findIndex(i, false) == 0;
        }

        private int findIndex(int i, boolean findSource) {
            int spanStart;
            int spanLength;
            int spanStart2;
            int spanLength2;
            if (i < 0) {
                return -1;
            }
            if (findSource) {
                spanStart = this.srcIndex;
                spanLength = this.oldLength_;
            } else {
                spanStart = this.destIndex;
                spanLength = this.newLength_;
            }
            if (i < spanStart) {
                if (i >= spanStart / 2) {
                    while (true) {
                        previous();
                        int spanStart3 = findSource ? this.srcIndex : this.destIndex;
                        if (i >= spanStart3) {
                            return 0;
                        }
                        if (this.remaining > 0) {
                            int spanLength3 = findSource ? this.oldLength_ : this.newLength_;
                            int num = ((this.array[this.index] & '\u01ff') + 1) - this.remaining;
                            int len = num * spanLength3;
                            if (i >= spanStart3 - len) {
                                int n = (((spanStart3 - i) - 1) / spanLength3) + 1;
                                this.srcIndex -= this.oldLength_ * n;
                                this.replIndex -= this.newLength_ * n;
                                this.destIndex -= this.newLength_ * n;
                                this.remaining += n;
                                return 0;
                            }
                            this.srcIndex -= this.oldLength_ * num;
                            this.replIndex -= this.newLength_ * num;
                            this.destIndex -= this.newLength_ * num;
                            this.remaining = 0;
                        }
                    }
                } else {
                    this.dir = 0;
                    this.destIndex = 0;
                    this.replIndex = 0;
                    this.srcIndex = 0;
                    this.newLength_ = 0;
                    this.oldLength_ = 0;
                    this.remaining = 0;
                    this.index = 0;
                }
            } else if (i < spanStart + spanLength) {
                return 0;
            }
            while (next(false)) {
                if (findSource) {
                    spanStart2 = this.srcIndex;
                    spanLength2 = this.oldLength_;
                } else {
                    spanStart2 = this.destIndex;
                    spanLength2 = this.newLength_;
                }
                if (i < spanStart2 + spanLength2) {
                    return 0;
                }
                if (this.remaining > 1) {
                    int len2 = this.remaining * spanLength2;
                    if (i < spanStart2 + len2) {
                        int n2 = (i - spanStart2) / spanLength2;
                        this.srcIndex += this.oldLength_ * n2;
                        this.replIndex += this.newLength_ * n2;
                        this.destIndex += this.newLength_ * n2;
                        this.remaining -= n2;
                        return 0;
                    }
                    this.oldLength_ *= this.remaining;
                    this.newLength_ *= this.remaining;
                    this.remaining = 0;
                }
            }
            return 1;
        }

        public int destinationIndexFromSourceIndex(int i) {
            int where = findIndex(i, true);
            if (where < 0) {
                return 0;
            }
            if (where > 0 || i == this.srcIndex) {
                return this.destIndex;
            }
            if (this.changed) {
                return this.destIndex + this.newLength_;
            }
            return this.destIndex + (i - this.srcIndex);
        }

        public int sourceIndexFromDestinationIndex(int i) {
            int where = findIndex(i, false);
            if (where < 0) {
                return 0;
            }
            if (where > 0 || i == this.destIndex) {
                return this.srcIndex;
            }
            if (this.changed) {
                return this.srcIndex + this.oldLength_;
            }
            return this.srcIndex + (i - this.destIndex);
        }

        public boolean hasChange() {
            return this.changed;
        }

        public int oldLength() {
            return this.oldLength_;
        }

        public int newLength() {
            return this.newLength_;
        }

        public int sourceIndex() {
            return this.srcIndex;
        }

        public int replacementIndex() {
            return this.replIndex;
        }

        public int destinationIndex() {
            return this.destIndex;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("{ src[");
            sb.append(this.srcIndex);
            sb.append("..");
            sb.append(this.srcIndex + this.oldLength_);
            if (this.changed) {
                sb.append("] \u21dd dest[");
            } else {
                sb.append("] \u2261 dest[");
            }
            sb.append(this.destIndex);
            sb.append("..");
            sb.append(this.destIndex + this.newLength_);
            if (this.changed) {
                sb.append("], repl[");
                sb.append(this.replIndex);
                sb.append("..");
                sb.append(this.replIndex + this.newLength_);
                sb.append("] }");
            } else {
                sb.append("] (no-change) }");
            }
            return sb.toString();
        }
    }

    public Iterator getCoarseChangesIterator() {
        return new Iterator(this.array, this.length, true, true);
    }

    public Iterator getCoarseIterator() {
        return new Iterator(this.array, this.length, false, true);
    }

    public Iterator getFineChangesIterator() {
        return new Iterator(this.array, this.length, true, false);
    }

    public Iterator getFineIterator() {
        return new Iterator(this.array, this.length, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0068, code lost:
        if (r6 != 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006b, code lost:
        if (r10 != 0) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006d, code lost:
        if (r2 == 0) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006f, code lost:
        addReplace(r10, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0072, code lost:
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007a, code lost:
        throw new java.lang.IllegalArgumentException("The ab output string is shorter than the bc input string.");
     */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00f8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00f3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Edits mergeAndAppend(Edits ab, Edits bc) {
        Iterator abIter = ab.getFineIterator();
        Iterator bcIter = bc.getFineIterator();
        boolean bcHasNext = true;
        int ab_bLength = 0;
        int bc_bLength = 0;
        int cLength = 0;
        int pending_aLength = 0;
        int aLength = 0;
        boolean abHasNext = true;
        int pending_cLength = 0;
        while (true) {
            if (bc_bLength == 0 && bcHasNext) {
                boolean next = bcIter.next();
                bcHasNext = next;
                if (next) {
                    bc_bLength = bcIter.oldLength();
                    cLength = bcIter.newLength();
                    if (bc_bLength == 0) {
                        if (ab_bLength == 0 || !abIter.hasChange()) {
                            addReplace(pending_aLength, pending_cLength + cLength);
                            pending_cLength = 0;
                            pending_aLength = 0;
                        } else {
                            pending_cLength += cLength;
                        }
                    }
                }
            }
            if (ab_bLength == 0) {
                if (!abHasNext) {
                    break;
                }
                boolean next2 = abIter.next();
                abHasNext = next2;
                if (!next2) {
                    break;
                }
                aLength = abIter.oldLength();
                ab_bLength = abIter.newLength();
                if (ab_bLength == 0) {
                    if (bc_bLength == bcIter.oldLength() || !bcIter.hasChange()) {
                        addReplace(pending_aLength + aLength, pending_cLength);
                        pending_cLength = 0;
                        pending_aLength = 0;
                    } else {
                        pending_aLength += aLength;
                    }
                }
            }
            if (bc_bLength == 0) {
                throw new IllegalArgumentException("The bc input string is shorter than the ab output string.");
            }
            if (!abIter.hasChange() && !bcIter.hasChange()) {
                if (pending_aLength != 0 || pending_cLength != 0) {
                    addReplace(pending_aLength, pending_cLength);
                    pending_cLength = 0;
                    pending_aLength = 0;
                }
                int unchangedLength = aLength <= cLength ? aLength : cLength;
                addUnchanged(unchangedLength);
                int i = aLength - unchangedLength;
                aLength = i;
                ab_bLength = i;
                int i2 = cLength - unchangedLength;
                cLength = i2;
                bc_bLength = i2;
            } else if (!abIter.hasChange() && bcIter.hasChange()) {
                if (ab_bLength >= bc_bLength) {
                    addReplace(pending_aLength + bc_bLength, pending_cLength + cLength);
                    pending_cLength = 0;
                    pending_aLength = 0;
                    int i3 = ab_bLength - bc_bLength;
                    ab_bLength = i3;
                    aLength = i3;
                    bc_bLength = 0;
                } else {
                    pending_aLength += aLength;
                    pending_cLength += cLength;
                    if (ab_bLength < bc_bLength) {
                    }
                }
            } else if (abIter.hasChange() && !bcIter.hasChange()) {
                if (ab_bLength <= bc_bLength) {
                    addReplace(pending_aLength + aLength, pending_cLength + ab_bLength);
                    pending_cLength = 0;
                    pending_aLength = 0;
                    int i4 = bc_bLength - ab_bLength;
                    bc_bLength = i4;
                    cLength = i4;
                    ab_bLength = 0;
                } else {
                    pending_aLength += aLength;
                    pending_cLength += cLength;
                    if (ab_bLength < bc_bLength) {
                    }
                }
            } else if (ab_bLength == bc_bLength) {
                addReplace(pending_aLength + aLength, pending_cLength + cLength);
                pending_cLength = 0;
                pending_aLength = 0;
                bc_bLength = 0;
                ab_bLength = 0;
            } else {
                pending_aLength += aLength;
                pending_cLength += cLength;
                if (ab_bLength < bc_bLength) {
                    bc_bLength -= ab_bLength;
                    ab_bLength = 0;
                    cLength = 0;
                } else {
                    ab_bLength -= bc_bLength;
                    bc_bLength = 0;
                    aLength = 0;
                }
            }
        }
    }
}
