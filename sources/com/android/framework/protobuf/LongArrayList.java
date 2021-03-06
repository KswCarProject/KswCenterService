package com.android.framework.protobuf;

import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class LongArrayList extends AbstractProtobufList<Long> implements Internal.LongList, RandomAccess {
    private static final LongArrayList EMPTY_LIST = new LongArrayList();
    private long[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static LongArrayList emptyList() {
        return EMPTY_LIST;
    }

    LongArrayList() {
        this(new long[10], 0);
    }

    private LongArrayList(long[] array2, int size2) {
        this.array = array2;
        this.size = size2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntArrayList)) {
            return super.equals(o);
        }
        LongArrayList other = (LongArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        long[] arr = other.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != arr[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; i++) {
            result = (result * 31) + Internal.hashLong(this.array[i]);
        }
        return result;
    }

    public Internal.LongList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new LongArrayList(Arrays.copyOf(this.array, capacity), this.size);
        }
        throw new IllegalArgumentException();
    }

    public Long get(int index) {
        return Long.valueOf(getLong(index));
    }

    public long getLong(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public Long set(int index, Long element) {
        return Long.valueOf(setLong(index, element.longValue()));
    }

    public long setLong(int index, long element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        long previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }

    public void add(int index, Long element) {
        addLong(index, element.longValue());
    }

    public void addLong(long element) {
        addLong(this.size, element);
    }

    private void addLong(int index, long element) {
        ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        } else {
            long[] newArray = new long[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Long> collection) {
        ensureIsMutable();
        if (collection == null) {
            throw new NullPointerException();
        } else if (!(collection instanceof LongArrayList)) {
            return super.addAll(collection);
        } else {
            LongArrayList list = (LongArrayList) collection;
            if (list.size == 0) {
                return false;
            }
            if (Integer.MAX_VALUE - this.size >= list.size) {
                int newSize = this.size + list.size;
                if (newSize > this.array.length) {
                    this.array = Arrays.copyOf(this.array, newSize);
                }
                System.arraycopy(list.array, 0, this.array, this.size, list.size);
                this.size = newSize;
                this.modCount++;
                return true;
            }
            throw new OutOfMemoryError();
        }
    }

    public boolean remove(Object o) {
        ensureIsMutable();
        for (int i = 0; i < this.size; i++) {
            if (o.equals(Long.valueOf(this.array[i]))) {
                System.arraycopy(this.array, i + 1, this.array, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public Long remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        long value = this.array[index];
        System.arraycopy(this.array, index + 1, this.array, index, this.size - index);
        this.size--;
        this.modCount++;
        return Long.valueOf(value);
    }

    private void ensureIndexInRange(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int index) {
        return "Index:" + index + ", Size:" + this.size;
    }
}
