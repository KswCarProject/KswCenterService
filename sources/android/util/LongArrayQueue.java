package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.NoSuchElementException;
import libcore.util.EmptyArray;

public class LongArrayQueue {
    private int mHead;
    private int mSize;
    private int mTail;
    private long[] mValues;

    public LongArrayQueue(int initialCapacity) {
        if (initialCapacity == 0) {
            this.mValues = EmptyArray.LONG;
        } else {
            this.mValues = ArrayUtils.newUnpaddedLongArray(initialCapacity);
        }
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public LongArrayQueue() {
        this(16);
    }

    private void grow() {
        if (this.mSize >= this.mValues.length) {
            long[] newArray = ArrayUtils.newUnpaddedLongArray(GrowingArrayUtils.growSize(this.mSize));
            int r = this.mValues.length - this.mHead;
            System.arraycopy(this.mValues, this.mHead, newArray, 0, r);
            System.arraycopy(this.mValues, 0, newArray, r, this.mHead);
            this.mValues = newArray;
            this.mHead = 0;
            this.mTail = this.mSize;
            return;
        }
        throw new IllegalStateException("Queue not full yet!");
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public void addLast(long value) {
        if (this.mSize == this.mValues.length) {
            grow();
        }
        this.mValues[this.mTail] = value;
        this.mTail = (this.mTail + 1) % this.mValues.length;
        this.mSize++;
    }

    public long removeFirst() {
        if (this.mSize != 0) {
            long ret = this.mValues[this.mHead];
            this.mHead = (this.mHead + 1) % this.mValues.length;
            this.mSize--;
            return ret;
        }
        throw new NoSuchElementException("Queue is empty!");
    }

    public long get(int position) {
        if (position < 0 || position >= this.mSize) {
            throw new IndexOutOfBoundsException("Index " + position + " not valid for a queue of size " + this.mSize);
        }
        return this.mValues[(this.mHead + position) % this.mValues.length];
    }

    public long peekFirst() {
        if (this.mSize != 0) {
            return this.mValues[this.mHead];
        }
        throw new NoSuchElementException("Queue is empty!");
    }

    public long peekLast() {
        if (this.mSize != 0) {
            return this.mValues[(this.mTail == 0 ? this.mValues.length : this.mTail) - 1];
        }
        throw new NoSuchElementException("Queue is empty!");
    }
}
