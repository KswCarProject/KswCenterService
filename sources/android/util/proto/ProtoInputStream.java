package android.util.proto;

import android.net.wifi.WifiScanner;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        this.mState = 0;
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
        this.mState = 0;
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
            this.mState = (byte) (this.mState & -5);
            return this.mFieldNumber;
        }
        if ((this.mState & 1) == 1) {
            skip();
            this.mState = (byte) (this.mState & -2);
        }
        if ((this.mState & 2) == 2) {
            if (getOffset() < this.mPackedEnd) {
                this.mState = (byte) (this.mState | 1);
                return this.mFieldNumber;
            } else if (getOffset() == this.mPackedEnd) {
                this.mState = (byte) (this.mState & -3);
            } else {
                throw new ProtoParseException("Unexpectedly reached end of packed field at offset 0x" + Integer.toHexString(this.mPackedEnd) + dumpDebugData());
            }
        }
        if (this.mDepth < 0 || getOffset() != getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue())) {
            readTag();
        } else {
            this.mFieldNumber = -1;
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
            this.mState = (byte) (this.mState & -2);
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
            this.mState = (byte) (this.mState & -2);
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
                if (i != 17) {
                    switch (i) {
                        case 13:
                        case 14:
                            break;
                        case 15:
                            break;
                        default:
                            throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an int" + dumpDebugData());
                    }
                } else {
                    assertWireType(0);
                    value = decodeZigZag32((int) readVarint());
                    this.mState = (byte) (this.mState & -2);
                    return value;
                }
            }
            assertWireType(5);
            value = readFixed32();
            this.mState = (byte) (this.mState & -2);
            return value;
        }
        assertWireType(0);
        value = (int) readVarint();
        this.mState = (byte) (this.mState & -2);
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
        } else if (i != 18) {
            switch (i) {
                case 3:
                case 4:
                    assertWireType(0);
                    value = readVarint();
                    break;
                default:
                    throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an long" + dumpDebugData());
            }
        } else {
            assertWireType(0);
            value = decodeZigZag64(readVarint());
        }
        this.mState = (byte) (this.mState & -2);
        return value;
    }

    public boolean readBoolean(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        checkPacked(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 8) {
            boolean value = false;
            assertWireType(0);
            if (readVarint() != 0) {
                value = true;
            }
            this.mState = (byte) (this.mState & -2);
            return value;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(fieldId) + ") is not an boolean" + dumpDebugData());
    }

    public String readString(long fieldId) throws IOException {
        assertFreshData();
        assertFieldNumber(fieldId);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & fieldId) >>> 32)) == 9) {
            assertWireType(2);
            String value = readRawString((int) readVarint());
            this.mState = (byte) (this.mState & -2);
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
                byte[] value = readRawBytes((int) readVarint());
                this.mState = (byte) (this.mState & -2);
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
        if (this.mDepth <= 0 || getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) <= getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth - 1).longValue())) {
            this.mState = (byte) (this.mState & -2);
            return this.mExpectedObjectTokenStack.get(this.mDepth).longValue();
        }
        throw new ProtoParseException("Embedded Object (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) + ") ends after of parent Objects's (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth - 1).longValue()) + ") end" + dumpDebugData());
    }

    public void end(long token) {
        if (this.mExpectedObjectTokenStack.get(this.mDepth).longValue() == token) {
            if (getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) > getOffset()) {
                incOffset(getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) - getOffset());
            }
            this.mDepth--;
            this.mState = (byte) (this.mState & -2);
            return;
        }
        throw new ProtoParseException("end token " + token + " does not match current message token " + this.mExpectedObjectTokenStack.get(this.mDepth) + dumpDebugData());
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
            int i = 0;
            while (i < fragment) {
                byte b = this.mBuffer[this.mOffset + i];
                value |= (((long) b) & 127) << shift2;
                if ((b & 128) == 0) {
                    incOffset(i + 1);
                    return value;
                }
                shift2 += 7;
                if (shift2 <= 63) {
                    i++;
                } else {
                    throw new ProtoParseException("Varint is too large at offset 0x" + Integer.toHexString(getOffset() + i) + dumpDebugData());
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
            return (this.mBuffer[this.mOffset - 4] & 255) | ((this.mBuffer[this.mOffset - 3] & 255) << 8) | ((this.mBuffer[this.mOffset - 2] & 255) << WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) | ((this.mBuffer[this.mOffset - 1] & 255) << 24);
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
            return ((((long) this.mBuffer[this.mOffset - 7]) & 255) << 8) | (((long) this.mBuffer[this.mOffset - 8]) & 255) | ((((long) this.mBuffer[this.mOffset - 6]) & 255) << 16) | ((((long) this.mBuffer[this.mOffset - 5]) & 255) << 24) | ((((long) this.mBuffer[this.mOffset - 4]) & 255) << 32) | ((((long) this.mBuffer[this.mOffset - 3]) & 255) << 40) | ((((long) this.mBuffer[this.mOffset - 2]) & 255) << 48) | ((((long) this.mBuffer[this.mOffset - 1]) & 255) << 56);
        }
        long value = 0;
        int shift = 0;
        while (bytesLeft > 0) {
            fillBuffer();
            int fragment = this.mEnd - this.mOffset < bytesLeft ? this.mEnd - this.mOffset : bytesLeft;
            incOffset(fragment);
            bytesLeft -= fragment;
            while (fragment > 0) {
                value |= (((long) this.mBuffer[this.mOffset - fragment]) & 255) << shift;
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
        } else if (n > this.mBufferSize) {
            return new String(readRawBytes(n), 0, n, StandardCharsets.UTF_8);
        } else {
            int stringHead = this.mEnd - this.mOffset;
            System.arraycopy(this.mBuffer, this.mOffset, this.mBuffer, 0, stringHead);
            this.mEnd = this.mStream.read(this.mBuffer, stringHead, n - stringHead) + stringHead;
            this.mDiscardedBytes += this.mOffset;
            this.mOffset = 0;
            String value2 = new String(this.mBuffer, this.mOffset, n, StandardCharsets.UTF_8);
            incOffset(n);
            return value2;
        }
    }

    private void fillBuffer() throws IOException {
        if (this.mOffset >= this.mEnd && this.mStream != null) {
            this.mOffset -= this.mEnd;
            this.mDiscardedBytes += this.mEnd;
            if (this.mOffset >= this.mBufferSize) {
                int skipped = (int) this.mStream.skip((long) ((this.mOffset / this.mBufferSize) * this.mBufferSize));
                this.mDiscardedBytes += skipped;
                this.mOffset -= skipped;
            }
            this.mEnd = this.mStream.read(this.mBuffer);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skip() throws java.io.IOException {
        /*
            r3 = this;
            byte r0 = r3.mState
            r1 = 2
            r0 = r0 & r1
            if (r0 != r1) goto L_0x0011
            int r0 = r3.mPackedEnd
            int r1 = r3.getOffset()
            int r0 = r0 - r1
            r3.incOffset(r0)
            goto L_0x0070
        L_0x0011:
            int r0 = r3.mWireType
            r1 = 5
            if (r0 == r1) goto L_0x006b
            switch(r0) {
                case 0: goto L_0x0059;
                case 1: goto L_0x0053;
                case 2: goto L_0x0047;
                default: goto L_0x0019;
            }
        L_0x0019:
            android.util.proto.ProtoParseException r0 = new android.util.proto.ProtoParseException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unexpected wire type: "
            r1.append(r2)
            int r2 = r3.mWireType
            r1.append(r2)
            java.lang.String r2 = " at offset 0x"
            r1.append(r2)
            int r2 = r3.mOffset
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r1.append(r2)
            java.lang.String r2 = r3.dumpDebugData()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0047:
            r3.fillBuffer()
            long r0 = r3.readVarint()
            int r0 = (int) r0
            r3.incOffset(r0)
            goto L_0x0070
        L_0x0053:
            r0 = 8
            r3.incOffset(r0)
            goto L_0x0070
        L_0x0059:
            r3.fillBuffer()
            byte[] r0 = r3.mBuffer
            int r1 = r3.mOffset
            byte r0 = r0[r1]
            r1 = 1
            r3.incOffset(r1)
            r1 = r0 & 128(0x80, float:1.794E-43)
            if (r1 != 0) goto L_0x0059
            goto L_0x0070
        L_0x006b:
            r0 = 4
            r3.incOffset(r0)
        L_0x0070:
            byte r0 = r3.mState
            r0 = r0 & -2
            byte r0 = (byte) r0
            r3.mState = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoInputStream.skip():void");
    }

    private void incOffset(int n) {
        this.mOffset += n;
        if (this.mDepth >= 0 && getOffset() > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth).longValue())) {
            throw new ProtoParseException("Unexpectedly reached end of embedded object.  " + token2String(this.mExpectedObjectTokenStack.get(this.mDepth).longValue()) + dumpDebugData());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        r4.mWireType = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0088, code lost:
        throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(r5) + ") packed length " + r0 + " is not aligned for fixed32" + dumpDebugData());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x008b, code lost:
        if ((r0 % 8) != 0) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008d, code lost:
        r4.mWireType = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00bf, code lost:
        throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(r5) + ") packed length " + r0 + " is not aligned for fixed64" + dumpDebugData());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x004e, code lost:
        r4.mWireType = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0054, code lost:
        if ((r0 % 4) != 0) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkPacked(long r5) throws java.io.IOException {
        /*
            r4 = this;
            int r0 = r4.mWireType
            r1 = 2
            if (r0 != r1) goto L_0x00c0
            long r2 = r4.readVarint()
            int r0 = (int) r2
            int r2 = r4.getOffset()
            int r2 = r2 + r0
            r4.mPackedEnd = r2
            byte r2 = r4.mState
            r1 = r1 | r2
            byte r1 = (byte) r1
            r4.mState = r1
            r1 = 1095216660480(0xff00000000, double:5.41108926696E-312)
            long r1 = r1 & r5
            r3 = 32
            long r1 = r1 >>> r3
            int r1 = (int) r1
            switch(r1) {
                case 1: goto L_0x0089;
                case 2: goto L_0x0052;
                case 3: goto L_0x004e;
                case 4: goto L_0x004e;
                case 5: goto L_0x004e;
                case 6: goto L_0x0089;
                case 7: goto L_0x0052;
                case 8: goto L_0x004e;
                default: goto L_0x0024;
            }
        L_0x0024:
            switch(r1) {
                case 13: goto L_0x004e;
                case 14: goto L_0x004e;
                case 15: goto L_0x0052;
                case 16: goto L_0x0089;
                case 17: goto L_0x004e;
                case 18: goto L_0x004e;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Requested field id ("
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r5)
            r2.append(r3)
            java.lang.String r3 = ") is not a packable field"
            r2.append(r3)
            java.lang.String r3 = r4.dumpDebugData()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x004e:
            r1 = 0
            r4.mWireType = r1
            goto L_0x00c0
        L_0x0052:
            int r1 = r0 % 4
            if (r1 != 0) goto L_0x005a
            r1 = 5
            r4.mWireType = r1
            goto L_0x00c0
        L_0x005a:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Requested field id ("
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r5)
            r2.append(r3)
            java.lang.String r3 = ") packed length "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = " is not aligned for fixed32"
            r2.append(r3)
            java.lang.String r3 = r4.dumpDebugData()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0089:
            int r1 = r0 % 8
            if (r1 != 0) goto L_0x0091
            r1 = 1
            r4.mWireType = r1
            goto L_0x00c0
        L_0x0091:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Requested field id ("
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r5)
            r2.append(r3)
            java.lang.String r3 = ") packed length "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = " is not aligned for fixed64"
            r2.append(r3)
            java.lang.String r3 = r4.dumpDebugData()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoInputStream.checkPacked(long):void");
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
