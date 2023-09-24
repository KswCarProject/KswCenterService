package android.util.proto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class ProtoInputStream extends ProtoStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int NO_MORE_FIELDS = -1;
    private static final byte STATE_FIELD_MISS = 4;
    private static final byte STATE_READING_PACKED = 2;
    private static final byte STATE_STARTED_FIELD_READ = 1;
    private byte[] mBuffer;
    private final int mBufferSize;
    private int mDepth;
    private int mDiscardedBytes;
    private int mEnd;
    private ArrayList<Long> mExpectedObjectTokenStack;
    private int mFieldNumber;
    private int mOffset;
    private int mPackedEnd;
    private byte mState;
    private InputStream mStream;
    private int mWireType;

    public ProtoInputStream(InputStream stream, int bufferSize) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mStream = stream;
        if (bufferSize > 0) {
            this.mBufferSize = bufferSize;
        } else {
            this.mBufferSize = 8192;
        }
        this.mBuffer = new byte[this.mBufferSize];
    }

    public ProtoInputStream(InputStream stream) {
        this(stream, 8192);
    }

    public ProtoInputStream(byte[] buffer) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mBufferSize = buffer.length;
        this.mEnd = buffer.length;
        this.mBuffer = buffer;
        this.mStream = null;
    }

    public int getFieldNumber() {
        return this.mFieldNumber;
    }

    public int getWireType() {
        if ((this.mState & 2) == 2) {
            return 2;
        }
        return this.mWireType;
    }

    public int getOffset() {
        return this.mOffset + this.mDiscardedBytes;
    }

    public int nextField() throws IOException {
        if ((this.mState & 4) == 4) {
            this.mState = (byte) (this.mState & (-5));
            return this.mFieldNumber;
        }
        if ((this.mState & 1) == 1) {
            skip();
            this.mState = (byte) (this.mState & (-2));
        }
        if ((this.mState & 2) == 2) {
            if (getOffset() < this.mPackedEnd) {
                this.mState = (byte) (this.mState | 1);
                return this.mFieldNumber;
            } else if (getOffset() == this.mPackedEnd) {
                this.mState = (byte) (this.mState & (-3));
            } else {
                throw new ProtoParseException("Unexpectedly reached end of packed field at offset 0x" + Integer.toHexString(this.mPackedEnd) + dumpDebugData());
            }
        }
        if (this.mDepth >= 0 && getOffset() == getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue())) {
            this.mFieldNumber = -1;
        } else {
            readTag();
        }
        return this.mFieldNumber;
    }

    public boolean isNextField(long fieldId) throws IOException {
        if (nextField() == ((int) fieldId)) {
            return true;
        }
        this.mState = (byte) (this.mState | 4);
        return false;
    }

    public double readDouble(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 1) {
            assertWireType(1);
            double value = Double.longBitsToDouble(readFixed64());
            this.mState = (byte) (this.mState & (-2));
            return value;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") cannot be read as a double" + dumpDebugData());
    }

    public float readFloat(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 2) {
            assertWireType(5);
            float value = Float.intBitsToFloat(readFixed32());
            this.mState = (byte) (this.mState & (-2));
            return value;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not a float" + dumpDebugData());
    }

    public int readInt(long fieldId) throws IOException {
        int value;
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        int i = (int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32);
        if (i != 5) {
            if (i != 7) {
                if (i == 17) {
                    assertWireType(0);
                    value = decodeZigZag32((int) readVarint());
                    this.mState = (byte) (this.mState & (-2));
                    return value;
                }
                switch (i) {
                    case 13:
                    case 14:
                        break;
                    case 15:
                        break;
                    default:
                        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an int" + dumpDebugData());
                }
                this.mState = (byte) (this.mState & (-2));
                return value;
            }
            assertWireType(5);
            value = readFixed32();
            this.mState = (byte) (this.mState & (-2));
            return value;
        }
        assertWireType(0);
        value = (int) readVarint();
        this.mState = (byte) (this.mState & (-2));
        return value;
    }

    public long readLong(long fieldId) throws IOException {
        long value;
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        int i = (int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32);
        if (i == 6 || i == 16) {
            assertWireType(1);
            value = readFixed64();
        } else if (i == 18) {
            assertWireType(0);
            value = decodeZigZag64(readVarint());
        } else {
            switch (i) {
                case 3:
                case 4:
                    assertWireType(0);
                    value = readVarint();
                    break;
                default:
                    throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an long" + dumpDebugData());
            }
        }
        this.mState = (byte) (this.mState & (-2));
        return value;
    }

    public boolean readBoolean(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 8) {
            assertWireType(0);
            boolean value = readVarint() != 0;
            this.mState = (byte) (this.mState & (-2));
            return value;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an boolean" + dumpDebugData());
    }

    public String readString(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 9) {
            assertWireType(2);
            int len = (int) readVarint();
            String value = readRawString(len);
            this.mState = (byte) (this.mState & (-2));
            return value;
        }
        throw new IllegalArgumentException("Requested field id(" + getFieldIdString(fieldId) + ") is not an string" + dumpDebugData());
    }

    public byte[] readBytes(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        switch ((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) {
            case 11:
            case 12:
                assertWireType(2);
                int len = (int) readVarint();
                byte[] value = readRawBytes(len);
                this.mState = (byte) (this.mState & (-2));
                return value;
            default:
                throw new IllegalArgumentException("Requested field type (" + getFieldIdString(fieldId) + ") cannot be read as raw bytes" + dumpDebugData());
        }
    }

    public long start(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        assertWireType(2);
        int messageSize = (int) readVarint();
        if (this.mExpectedObjectTokenStack == null) {
            this.mExpectedObjectTokenStack = new ArrayList<>();
        }
        int i = this.mDepth + 1;
        this.mDepth = i;
        if (i == this.mExpectedObjectTokenStack.size()) {
            this.mExpectedObjectTokenStack.add(Long.valueOf(makeToken(0, (fieldId & ProtoStream.FIELD_COUNT_REPEATED) == ProtoStream.FIELD_COUNT_REPEATED, this.mDepth, (int) fieldId, getOffset() + messageSize)));
        } else {
            this.mExpectedObjectTokenStack.set(this.mDepth, Long.valueOf(makeToken(0, (fieldId & ProtoStream.FIELD_COUNT_REPEATED) == ProtoStream.FIELD_COUNT_REPEATED, this.mDepth, (int) fieldId, getOffset() + messageSize)));
        }
        if (this.mDepth > 0 && getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth - 1).longValue())) {
            throw new ProtoParseException("Embedded Object (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) + ") ends after of parent Objects's (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth - 1).longValue()) + ") end" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return this.mExpectedObjectTokenStack.get(this.mDepth).longValue();
    }

    public void end(long token) {
        if (this.mExpectedObjectTokenStack.get(this.mDepth).longValue() != token) {
            throw new ProtoParseException("end token " + token + " does not match current message token " + this.mExpectedObjectTokenStack.get(this.mDepth) + dumpDebugData());
        }
        if (getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) > getOffset()) {
            incOffset(getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) - getOffset());
        }
        this.mDepth--;
        this.mState = (byte) (this.mState & (-2));
    }

    private void readTag() throws IOException {
        fillBuffer();
        if (this.mOffset >= this.mEnd) {
            this.mFieldNumber = -1;
            return;
        }
        int tag = (int) readVarint();
        this.mFieldNumber = tag >>> 3;
        this.mWireType = tag & 7;
        this.mState = (byte) (this.mState | 1);
    }

    public int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private long readVarint() throws IOException {
        long value = 0;
        int shift = 0;
        while (true) {
            fillBuffer();
            int fragment = this.mEnd - this.mOffset;
            int shift2 = shift;
            for (int shift3 = 0; shift3 < fragment; shift3++) {
                byte b = this.mBuffer[this.mOffset + shift3];
                value |= (b & 127) << shift2;
                if ((b & 128) == 0) {
                    incOffset(shift3 + 1);
                    return value;
                }
                shift2 += 7;
                if (shift2 > 63) {
                    throw new ProtoParseException("Varint is too large at offset 0x" + Integer.toHexString(getOffset() + shift3) + dumpDebugData());
                }
            }
            incOffset(fragment);
            shift = shift2;
        }
    }

    private int readFixed32() throws IOException {
        int bytesLeft = 4;
        if (this.mOffset + 4 <= this.mEnd) {
            incOffset(4);
            return (this.mBuffer[this.mOffset - 4] & 255) | ((this.mBuffer[this.mOffset - 3] & 255) << 8) | ((this.mBuffer[this.mOffset - 2] & 255) << 16) | ((this.mBuffer[this.mOffset - 1] & 255) << 24);
        }
        int value = 0;
        int shift = 0;
        while (bytesLeft > 0) {
            fillBuffer();
            int fragment = this.mEnd - this.mOffset < bytesLeft ? this.mEnd - this.mOffset : bytesLeft;
            incOffset(fragment);
            bytesLeft -= fragment;
            while (fragment > 0) {
                value |= (this.mBuffer[this.mOffset - fragment] & 255) << shift;
                fragment--;
                shift += 8;
            }
        }
        return value;
    }

    private long readFixed64() throws IOException {
        int bytesLeft = 8;
        if (this.mOffset + 8 <= this.mEnd) {
            incOffset(8);
            return ((this.mBuffer[this.mOffset - 7] & 255) << 8) | (this.mBuffer[this.mOffset - 8] & 255) | ((this.mBuffer[this.mOffset - 6] & 255) << 16) | ((this.mBuffer[this.mOffset - 5] & 255) << 24) | ((this.mBuffer[this.mOffset - 4] & 255) << 32) | ((this.mBuffer[this.mOffset - 3] & 255) << 40) | ((this.mBuffer[this.mOffset - 2] & 255) << 48) | ((this.mBuffer[this.mOffset - 1] & 255) << 56);
        }
        long value = 0;
        int shift = 0;
        while (bytesLeft > 0) {
            fillBuffer();
            int fragment = this.mEnd - this.mOffset < bytesLeft ? this.mEnd - this.mOffset : bytesLeft;
            incOffset(fragment);
            bytesLeft -= fragment;
            while (fragment > 0) {
                value |= (this.mBuffer[this.mOffset - fragment] & 255) << shift;
                fragment--;
                shift += 8;
            }
        }
        return value;
    }

    private byte[] readRawBytes(int n) throws IOException {
        byte[] buffer = new byte[n];
        int pos = 0;
        while ((this.mOffset + n) - pos > this.mEnd) {
            int fragment = this.mEnd - this.mOffset;
            if (fragment > 0) {
                System.arraycopy(this.mBuffer, this.mOffset, buffer, pos, fragment);
                incOffset(fragment);
                pos += fragment;
            }
            fillBuffer();
            if (this.mOffset >= this.mEnd) {
                throw new ProtoParseException("Unexpectedly reached end of the InputStream at offset 0x" + Integer.toHexString(this.mEnd) + dumpDebugData());
            }
        }
        System.arraycopy(this.mBuffer, this.mOffset, buffer, pos, n - pos);
        incOffset(n - pos);
        return buffer;
    }

    private String readRawString(int n) throws IOException {
        fillBuffer();
        if (this.mOffset + n <= this.mEnd) {
            String value = new String(this.mBuffer, this.mOffset, n, StandardCharsets.UTF_8);
            incOffset(n);
            return value;
        } else if (n <= this.mBufferSize) {
            int stringHead = this.mEnd - this.mOffset;
            System.arraycopy(this.mBuffer, this.mOffset, this.mBuffer, 0, stringHead);
            this.mEnd = this.mStream.read(this.mBuffer, stringHead, n - stringHead) + stringHead;
            this.mDiscardedBytes += this.mOffset;
            this.mOffset = 0;
            String value2 = new String(this.mBuffer, this.mOffset, n, StandardCharsets.UTF_8);
            incOffset(n);
            return value2;
        } else {
            return new String(readRawBytes(n), 0, n, StandardCharsets.UTF_8);
        }
    }

    private void fillBuffer() throws IOException {
        if (this.mOffset >= this.mEnd && this.mStream != null) {
            this.mOffset -= this.mEnd;
            this.mDiscardedBytes += this.mEnd;
            if (this.mOffset >= this.mBufferSize) {
                int skipped = (int) this.mStream.skip((this.mOffset / this.mBufferSize) * this.mBufferSize);
                this.mDiscardedBytes += skipped;
                this.mOffset -= skipped;
            }
            this.mEnd = this.mStream.read(this.mBuffer);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void skip() throws IOException {
        byte b;
        if ((this.mState & 2) == 2) {
            incOffset(this.mPackedEnd - getOffset());
        } else {
            int i = this.mWireType;
            if (i != 5) {
                switch (i) {
                    case 0:
                        do {
                            fillBuffer();
                            b = this.mBuffer[this.mOffset];
                            incOffset(1);
                        } while ((b & 128) != 0);
                        break;
                    case 1:
                        incOffset(8);
                        break;
                    case 2:
                        fillBuffer();
                        int length = (int) readVarint();
                        incOffset(length);
                        break;
                    default:
                        throw new ProtoParseException("Unexpected wire type: " + this.mWireType + " at offset 0x" + Integer.toHexString(this.mOffset) + dumpDebugData());
                }
            } else {
                incOffset(4);
            }
        }
        this.mState = (byte) (this.mState & (-2));
    }

    private void incOffset(int n) {
        this.mOffset += n;
        if (this.mDepth >= 0 && getOffset() > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue())) {
            throw new ProtoParseException("Unexpectedly reached end of embedded object.  " + token2String(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) + dumpDebugData());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void checkPacked(long fieldId) throws IOException {
        if (this.mWireType == 2) {
            int length = (int) readVarint();
            this.mPackedEnd = getOffset() + length;
            this.mState = (byte) (2 | this.mState);
            int i = (int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32);
            switch (i) {
                case 1:
                case 6:
                    if (length % 8 != 0) {
                        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") packed length " + length + " is not aligned for fixed64" + dumpDebugData());
                    }
                    this.mWireType = 1;
                    return;
                case 2:
                case 7:
                    if (length % 4 != 0) {
                        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") packed length " + length + " is not aligned for fixed32" + dumpDebugData());
                    }
                    this.mWireType = 5;
                    return;
                case 3:
                case 4:
                case 5:
                case 8:
                    this.mWireType = 0;
                    return;
                default:
                    switch (i) {
                        case 13:
                        case 14:
                        case 17:
                        case 18:
                            break;
                        case 15:
                            break;
                        case 16:
                            break;
                        default:
                            throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not a packable field" + dumpDebugData());
                    }
            }
        }
    }

    private void assertFieldNumber(long fieldId) {
        if (((int) fieldId) != this.mFieldNumber) {
            throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") does not match current field number (0x" + Integer.toHexString(this.mFieldNumber) + ") at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    private void assertWireType(int wireType) {
        if (wireType != this.mWireType) {
            throw new WireTypeMismatchException("Current wire type " + getWireTypeString(this.mWireType) + " does not match expected wire type " + getWireTypeString(wireType) + " at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    private void assertFreshData() {
        if ((this.mState & 1) != 1) {
            throw new ProtoParseException("Attempting to read already read field at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    public String dumpDebugData() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nmFieldNumber : 0x" + Integer.toHexString(this.mFieldNumber));
        sb.append("\nmWireType : 0x" + Integer.toHexString(this.mWireType));
        sb.append("\nmState : 0x" + Integer.toHexString(this.mState));
        sb.append("\nmDiscardedBytes : 0x" + Integer.toHexString(this.mDiscardedBytes));
        sb.append("\nmOffset : 0x" + Integer.toHexString(this.mOffset));
        sb.append("\nmExpectedObjectTokenStack : ");
        if (this.mExpectedObjectTokenStack == null) {
            sb.append("null");
        } else {
            sb.append(this.mExpectedObjectTokenStack);
        }
        sb.append("\nmDepth : 0x" + Integer.toHexString(this.mDepth));
        sb.append("\nmBuffer : ");
        if (this.mBuffer == null) {
            sb.append("null");
        } else {
            sb.append(this.mBuffer);
        }
        sb.append("\nmBufferSize : 0x" + Integer.toHexString(this.mBufferSize));
        sb.append("\nmEnd : 0x" + Integer.toHexString(this.mEnd));
        return sb.toString();
    }
}
