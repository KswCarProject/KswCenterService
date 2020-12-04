package android.net.wifi.aware;

import android.telephony.SmsManager;
import java.nio.BufferOverflowException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import libcore.io.Memory;

public class TlvBufferUtils {
    private TlvBufferUtils() {
    }

    public static class TlvConstructor {
        private byte[] mArray;
        private int mArrayLength;
        private ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
        private int mLengthSize;
        private int mPosition;
        private int mTypeSize;

        public TlvConstructor(int typeSize, int lengthSize) {
            if (typeSize < 0 || typeSize > 2 || lengthSize <= 0 || lengthSize > 2) {
                throw new IllegalArgumentException("Invalid sizes - typeSize=" + typeSize + ", lengthSize=" + lengthSize);
            }
            this.mTypeSize = typeSize;
            this.mLengthSize = lengthSize;
            this.mPosition = 0;
        }

        public TlvConstructor setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
            return this;
        }

        public TlvConstructor wrap(byte[] array) {
            this.mArray = array;
            this.mArrayLength = array == null ? 0 : array.length;
            this.mPosition = 0;
            return this;
        }

        public TlvConstructor allocate(int capacity) {
            this.mArray = new byte[capacity];
            this.mArrayLength = capacity;
            this.mPosition = 0;
            return this;
        }

        public TlvConstructor allocateAndPut(List<byte[]> list) {
            if (list != null) {
                int size = 0;
                for (byte[] field : list) {
                    size += this.mTypeSize + this.mLengthSize;
                    if (field != null) {
                        size += field.length;
                    }
                }
                allocate(size);
                for (byte[] field2 : list) {
                    putByteArray(0, field2);
                }
            }
            return this;
        }

        public TlvConstructor putByte(int type, byte b) {
            checkLength(1);
            addHeader(type, 1);
            byte[] bArr = this.mArray;
            int i = this.mPosition;
            this.mPosition = i + 1;
            bArr[i] = b;
            return this;
        }

        public TlvConstructor putRawByte(byte b) {
            checkRawLength(1);
            byte[] bArr = this.mArray;
            int i = this.mPosition;
            this.mPosition = i + 1;
            bArr[i] = b;
            return this;
        }

        public TlvConstructor putByteArray(int type, byte[] array, int offset, int length) {
            checkLength(length);
            addHeader(type, length);
            if (length != 0) {
                System.arraycopy(array, offset, this.mArray, this.mPosition, length);
            }
            this.mPosition += length;
            return this;
        }

        public TlvConstructor putByteArray(int type, byte[] array) {
            return putByteArray(type, array, 0, array == null ? 0 : array.length);
        }

        public TlvConstructor putRawByteArray(byte[] array) {
            if (array == null) {
                return this;
            }
            checkRawLength(array.length);
            System.arraycopy(array, 0, this.mArray, this.mPosition, array.length);
            this.mPosition += array.length;
            return this;
        }

        public TlvConstructor putZeroLengthElement(int type) {
            checkLength(0);
            addHeader(type, 0);
            return this;
        }

        public TlvConstructor putShort(int type, short data) {
            checkLength(2);
            addHeader(type, 2);
            Memory.pokeShort(this.mArray, this.mPosition, data, this.mByteOrder);
            this.mPosition += 2;
            return this;
        }

        public TlvConstructor putInt(int type, int data) {
            checkLength(4);
            addHeader(type, 4);
            Memory.pokeInt(this.mArray, this.mPosition, data, this.mByteOrder);
            this.mPosition += 4;
            return this;
        }

        public TlvConstructor putString(int type, String data) {
            byte[] bytes = null;
            int length = 0;
            if (data != null) {
                bytes = data.getBytes();
                length = bytes.length;
            }
            return putByteArray(type, bytes, 0, length);
        }

        public byte[] getArray() {
            return Arrays.copyOf(this.mArray, getActualLength());
        }

        private int getActualLength() {
            return this.mPosition;
        }

        private void checkLength(int dataLength) {
            if (this.mPosition + this.mTypeSize + this.mLengthSize + dataLength > this.mArrayLength) {
                throw new BufferOverflowException();
            }
        }

        private void checkRawLength(int dataLength) {
            if (this.mPosition + dataLength > this.mArrayLength) {
                throw new BufferOverflowException();
            }
        }

        private void addHeader(int type, int length) {
            if (this.mTypeSize == 1) {
                this.mArray[this.mPosition] = (byte) type;
            } else if (this.mTypeSize == 2) {
                Memory.pokeShort(this.mArray, this.mPosition, (short) type, this.mByteOrder);
            }
            this.mPosition += this.mTypeSize;
            if (this.mLengthSize == 1) {
                this.mArray[this.mPosition] = (byte) length;
            } else if (this.mLengthSize == 2) {
                Memory.pokeShort(this.mArray, this.mPosition, (short) length, this.mByteOrder);
            }
            this.mPosition += this.mLengthSize;
        }
    }

    public static class TlvElement {
        public ByteOrder byteOrder;
        public int length;
        /* access modifiers changed from: private */
        public byte[] mRefArray;
        public int offset;
        public int type;

        private TlvElement(int type2, int length2, byte[] refArray, int offset2) {
            this.byteOrder = ByteOrder.BIG_ENDIAN;
            this.type = type2;
            this.length = length2;
            this.mRefArray = refArray;
            this.offset = offset2;
            if (offset2 + length2 > refArray.length) {
                throw new BufferOverflowException();
            }
        }

        public byte[] getRawData() {
            return Arrays.copyOfRange(this.mRefArray, this.offset, this.offset + this.length);
        }

        public byte getByte() {
            if (this.length == 1) {
                return this.mRefArray[this.offset];
            }
            throw new IllegalArgumentException("Accesing a byte from a TLV element of length " + this.length);
        }

        public short getShort() {
            if (this.length == 2) {
                return Memory.peekShort(this.mRefArray, this.offset, this.byteOrder);
            }
            throw new IllegalArgumentException("Accesing a short from a TLV element of length " + this.length);
        }

        public int getInt() {
            if (this.length == 4) {
                return Memory.peekInt(this.mRefArray, this.offset, this.byteOrder);
            }
            throw new IllegalArgumentException("Accesing an int from a TLV element of length " + this.length);
        }

        public String getString() {
            return new String(this.mRefArray, this.offset, this.length);
        }
    }

    public static class TlvIterable implements Iterable<TlvElement> {
        /* access modifiers changed from: private */
        public byte[] mArray;
        /* access modifiers changed from: private */
        public int mArrayLength;
        /* access modifiers changed from: private */
        public ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
        /* access modifiers changed from: private */
        public int mLengthSize;
        /* access modifiers changed from: private */
        public int mTypeSize;

        public TlvIterable(int typeSize, int lengthSize, byte[] array) {
            if (typeSize < 0 || typeSize > 2 || lengthSize <= 0 || lengthSize > 2) {
                throw new IllegalArgumentException("Invalid sizes - typeSize=" + typeSize + ", lengthSize=" + lengthSize);
            }
            this.mTypeSize = typeSize;
            this.mLengthSize = lengthSize;
            this.mArray = array;
            this.mArrayLength = array == null ? 0 : array.length;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            boolean first = true;
            Iterator<TlvElement> it = iterator();
            while (it.hasNext()) {
                TlvElement tlv = it.next();
                if (!first) {
                    builder.append(SmsManager.REGEX_PREFIX_DELIMITER);
                }
                first = false;
                builder.append(" (");
                if (this.mTypeSize != 0) {
                    builder.append("T=" + tlv.type + SmsManager.REGEX_PREFIX_DELIMITER);
                }
                builder.append("L=" + tlv.length + ") ");
                if (tlv.length == 0) {
                    builder.append("<null>");
                } else if (tlv.length == 1) {
                    builder.append(tlv.getByte());
                } else if (tlv.length == 2) {
                    builder.append(tlv.getShort());
                } else if (tlv.length == 4) {
                    builder.append(tlv.getInt());
                } else {
                    builder.append("<bytes>");
                }
                if (tlv.length != 0) {
                    builder.append(" (S='" + tlv.getString() + "')");
                }
            }
            builder.append("]");
            return builder.toString();
        }

        public List<byte[]> toList() {
            List<byte[]> list = new ArrayList<>();
            Iterator<TlvElement> it = iterator();
            while (it.hasNext()) {
                TlvElement tlv = it.next();
                list.add(Arrays.copyOfRange(tlv.mRefArray, tlv.offset, tlv.offset + tlv.length));
            }
            return list;
        }

        public Iterator<TlvElement> iterator() {
            return new Iterator<TlvElement>() {
                private int mOffset = 0;

                public boolean hasNext() {
                    return this.mOffset < TlvIterable.this.mArrayLength;
                }

                /* JADX WARNING: type inference failed for: r2v7, types: [byte[]] */
                /* JADX WARNING: type inference failed for: r1v7, types: [byte] */
                /* JADX WARNING: type inference failed for: r1v13, types: [byte[]] */
                /* JADX WARNING: type inference failed for: r0v5, types: [byte] */
                /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v5, types: [byte] */
                /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v7, types: [byte] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public android.net.wifi.aware.TlvBufferUtils.TlvElement next() {
                    /*
                        r10 = this;
                        boolean r0 = r10.hasNext()
                        if (r0 == 0) goto L_0x0099
                        r0 = 0
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r1 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r1 = r1.mTypeSize
                        r2 = 2
                        r3 = 1
                        if (r1 != r3) goto L_0x001c
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r1 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        byte[] r1 = r1.mArray
                        int r4 = r10.mOffset
                        byte r0 = r1[r4]
                        goto L_0x0036
                    L_0x001c:
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r1 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r1 = r1.mTypeSize
                        if (r1 != r2) goto L_0x0036
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r1 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        byte[] r1 = r1.mArray
                        int r4 = r10.mOffset
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r5 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        java.nio.ByteOrder r5 = r5.mByteOrder
                        short r0 = libcore.io.Memory.peekShort(r1, r4, r5)
                    L_0x0036:
                        int r1 = r10.mOffset
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r4 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r4 = r4.mTypeSize
                        int r1 = r1 + r4
                        r10.mOffset = r1
                        r1 = 0
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r4 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r4 = r4.mLengthSize
                        if (r4 != r3) goto L_0x0055
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r2 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        byte[] r2 = r2.mArray
                        int r3 = r10.mOffset
                        byte r1 = r2[r3]
                        goto L_0x006f
                    L_0x0055:
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r3 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r3 = r3.mLengthSize
                        if (r3 != r2) goto L_0x006f
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r2 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        byte[] r2 = r2.mArray
                        int r3 = r10.mOffset
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r4 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        java.nio.ByteOrder r4 = r4.mByteOrder
                        short r1 = libcore.io.Memory.peekShort(r2, r3, r4)
                    L_0x006f:
                        int r2 = r10.mOffset
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r3 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        int r3 = r3.mLengthSize
                        int r2 = r2 + r3
                        r10.mOffset = r2
                        android.net.wifi.aware.TlvBufferUtils$TlvElement r2 = new android.net.wifi.aware.TlvBufferUtils$TlvElement
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r3 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        byte[] r7 = r3.mArray
                        int r8 = r10.mOffset
                        r9 = 0
                        r4 = r2
                        r5 = r0
                        r6 = r1
                        r4.<init>(r5, r6, r7, r8)
                        android.net.wifi.aware.TlvBufferUtils$TlvIterable r3 = android.net.wifi.aware.TlvBufferUtils.TlvIterable.this
                        java.nio.ByteOrder r3 = r3.mByteOrder
                        r2.byteOrder = r3
                        int r3 = r10.mOffset
                        int r3 = r3 + r1
                        r10.mOffset = r3
                        return r2
                    L_0x0099:
                        java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
                        r0.<init>()
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.aware.TlvBufferUtils.TlvIterable.AnonymousClass1.next():android.net.wifi.aware.TlvBufferUtils$TlvElement");
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    public static boolean isValid(byte[] array, int typeSize, int lengthSize) {
        return isValidEndian(array, typeSize, lengthSize, ByteOrder.BIG_ENDIAN);
    }

    public static boolean isValidEndian(byte[] array, int typeSize, int lengthSize, ByteOrder byteOrder) {
        if (typeSize < 0 || typeSize > 2) {
            throw new IllegalArgumentException("Invalid arguments - typeSize must be 0, 1, or 2: typeSize=" + typeSize);
        } else if (lengthSize <= 0 || lengthSize > 2) {
            throw new IllegalArgumentException("Invalid arguments - lengthSize must be 1 or 2: lengthSize=" + lengthSize);
        } else if (array == null) {
            return true;
        } else {
            int nextTlvIndex = 0;
            while (nextTlvIndex + typeSize + lengthSize <= array.length) {
                int nextTlvIndex2 = nextTlvIndex + typeSize;
                if (lengthSize == 1) {
                    nextTlvIndex = nextTlvIndex2 + array[nextTlvIndex2] + lengthSize;
                } else {
                    nextTlvIndex = nextTlvIndex2 + Memory.peekShort(array, nextTlvIndex2, byteOrder) + lengthSize;
                }
            }
            if (nextTlvIndex == array.length) {
                return true;
            }
            return false;
        }
    }
}
