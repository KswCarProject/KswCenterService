package com.android.framework.protobuf;

import com.android.framework.protobuf.MessageLite;
import com.ibm.icu.text.Bidi;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class CodedInputStream {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit;
    private boolean enableAliasing;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit;
    private RefillCallback refillCallback;
    private int sizeLimit;
    private int totalBytesRetired;

    /* loaded from: classes4.dex */
    private interface RefillCallback {
        void onRefill();
    }

    public static CodedInputStream newInstance(InputStream input) {
        return new CodedInputStream(input, 4096);
    }

    static CodedInputStream newInstance(InputStream input, int bufferSize) {
        return new CodedInputStream(input, bufferSize);
    }

    public static CodedInputStream newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputStream newInstance(byte[] buf, int off, int len) {
        return newInstance(buf, off, len, false);
    }

    static CodedInputStream newInstance(byte[] buf, int off, int len, boolean bufferIsImmutable) {
        CodedInputStream result = new CodedInputStream(buf, off, len, bufferIsImmutable);
        try {
            result.pushLimit(len);
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer buf) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
        }
        ByteBuffer temp = buf.duplicate();
        byte[] buffer = new byte[temp.remaining()];
        temp.get(buffer);
        return newInstance(buffer);
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        return this.lastTag;
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public int getLastTag() {
        return this.lastTag;
    }

    public boolean skipField(int tag) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                skipRawVarint();
                return true;
            case 1:
                skipRawBytes(8);
                return true;
            case 2:
                skipRawBytes(readRawVarint32());
                return true;
            case 3:
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            case 4:
                return false;
            case 5:
                skipRawBytes(4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                long value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            case 1:
                long value2 = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value2);
                return true;
            case 2:
                ByteString value3 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value3);
                return true;
            case 3:
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            case 4:
                return false;
            case 5:
                int value4 = readRawLittleEndian32();
                output.writeRawVarint32(tag);
                output.writeFixed32NoTag(value4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public void skipMessage() throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag));
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag, output));
    }

    /* loaded from: classes4.dex */
    private class SkippedDataSink implements RefillCallback {
        private ByteArrayOutputStream byteArrayStream;
        private int lastPos;

        private SkippedDataSink() {
            this.lastPos = CodedInputStream.this.bufferPos;
        }

        @Override // com.android.framework.protobuf.CodedInputStream.RefillCallback
        public void onRefill() {
            if (this.byteArrayStream == null) {
                this.byteArrayStream = new ByteArrayOutputStream();
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            this.lastPos = 0;
        }

        ByteBuffer getSkippedData() {
            if (this.byteArrayStream == null) {
                return ByteBuffer.wrap(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos);
            return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint64() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            String result = new String(this.buffer, this.bufferPos, size, Internal.UTF_8);
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return "";
        } else {
            if (size <= this.bufferSize) {
                refillBuffer(size);
                String result2 = new String(this.buffer, this.bufferPos, size, Internal.UTF_8);
                this.bufferPos += size;
                return result2;
            }
            return new String(readRawBytesSlowPath(size), Internal.UTF_8);
        }
    }

    public String readStringRequireUtf8() throws IOException {
        byte[] bytes;
        int pos;
        int size = readRawVarint32();
        int oldPos = this.bufferPos;
        if (size <= this.bufferSize - oldPos && size > 0) {
            bytes = this.buffer;
            this.bufferPos = oldPos + size;
            pos = oldPos;
        } else if (size == 0) {
            return "";
        } else {
            if (size <= this.bufferSize) {
                refillBuffer(size);
                bytes = this.buffer;
                pos = 0;
                this.bufferPos = 0 + size;
            } else {
                bytes = readRawBytesSlowPath(size);
                pos = 0;
            }
        }
        if (!Utf8.isValidUtf8(bytes, pos, pos + size)) {
            throw InvalidProtocolBufferException.invalidUtf8();
        }
        return new String(bytes, pos, size, Internal.UTF_8);
    }

    public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
    }

    public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        T result = parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
        return result;
    }

    @Deprecated
    public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
        readGroup(fieldNumber, builder, (ExtensionRegistryLite) null);
    }

    public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        T result = parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
        return result;
    }

    public ByteString readBytes() throws IOException {
        ByteString result;
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            if (this.bufferIsImmutable && this.enableAliasing) {
                result = ByteString.wrap(this.buffer, this.bufferPos, size);
            } else {
                result = ByteString.copyFrom(this.buffer, this.bufferPos, size);
            }
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return ByteString.EMPTY;
        } else {
            return ByteString.wrap(readRawBytesSlowPath(size));
        }
    }

    public byte[] readByteArray() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            byte[] result = Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size);
            this.bufferPos += size;
            return result;
        }
        byte[] result2 = readRawBytesSlowPath(size);
        return result2;
    }

    public ByteBuffer readByteBuffer() throws IOException {
        ByteBuffer result;
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            if (this.input == null && !this.bufferIsImmutable && this.enableAliasing) {
                result = ByteBuffer.wrap(this.buffer, this.bufferPos, size).slice();
            } else {
                result = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size));
            }
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
        } else {
            return ByteBuffer.wrap(readRawBytesSlowPath(size));
        }
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0071, code lost:
        if (r1[r2] < 0) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int readRawVarint32() throws IOException {
        int pos;
        int x;
        int pos2;
        int pos3 = this.bufferPos;
        if (this.bufferSize != pos3) {
            byte[] buffer = this.buffer;
            int pos4 = pos3 + 1;
            int pos5 = buffer[pos3];
            if (pos5 >= 0) {
                this.bufferPos = pos4;
                return pos5;
            } else if (this.bufferSize - pos4 >= 9) {
                int pos6 = pos4 + 1;
                int x2 = (buffer[pos4] << 7) ^ pos5;
                if (x2 < 0) {
                    pos2 = x2 ^ (-128);
                } else {
                    pos = pos6 + 1;
                    int x3 = (buffer[pos6] << 14) ^ x2;
                    if (x3 >= 0) {
                        x = x3 ^ 16256;
                    } else {
                        pos6 = pos + 1;
                        int x4 = (buffer[pos] << 21) ^ x3;
                        if (x4 < 0) {
                            pos2 = (-2080896) ^ x4;
                        } else {
                            pos = pos6 + 1;
                            int y = buffer[pos6];
                            x = (x4 ^ (y << 28)) ^ 266354560;
                            if (y < 0) {
                                int pos7 = pos + 1;
                                if (buffer[pos] < 0) {
                                    pos = pos7 + 1;
                                    if (buffer[pos7] < 0) {
                                        pos7 = pos + 1;
                                        if (buffer[pos] < 0) {
                                            pos = pos7 + 1;
                                            if (buffer[pos7] < 0) {
                                                pos7 = pos + 1;
                                            }
                                        }
                                    }
                                }
                                pos = pos7;
                            }
                        }
                    }
                    this.bufferPos = pos;
                    return x;
                }
                x = pos2;
                pos = pos6;
                this.bufferPos = pos;
                return x;
            }
        }
        return (int) readRawVarint64SlowPath();
    }

    private void skipRawVarint() throws IOException {
        if (this.bufferSize - this.bufferPos >= 10) {
            byte[] buffer = this.buffer;
            int pos = this.bufferPos;
            int i = 0;
            while (i < 10) {
                int pos2 = pos + 1;
                if (buffer[pos] < 0) {
                    i++;
                    pos = pos2;
                } else {
                    this.bufferPos = pos2;
                    return;
                }
            }
        }
        skipRawVarintSlowPath();
    }

    private void skipRawVarintSlowPath() throws IOException {
        for (int i = 0; i < 10; i++) {
            if (readRawByte() >= 0) {
                return;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream input) throws IOException {
        int firstByte = input.read();
        if (firstByte == -1) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return readRawVarint32(firstByte, input);
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & 127;
        int offset = 7;
        while (offset < 32) {
            int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result |= (b & 127) << offset;
            if ((b & 128) != 0) {
                offset += 7;
            } else {
                return result;
            }
        }
        while (offset < 64) {
            int b2 = input.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((b2 & 128) != 0) {
                offset += 7;
            } else {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bd, code lost:
        if (r1[r2] < 0) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long readRawVarint64() throws IOException {
        long x;
        int pos = this.bufferPos;
        if (this.bufferSize != pos) {
            byte[] buffer = this.buffer;
            int pos2 = pos + 1;
            int pos3 = buffer[pos];
            if (pos3 >= 0) {
                this.bufferPos = pos2;
                return pos3;
            } else if (this.bufferSize - pos2 >= 9) {
                int pos4 = pos2 + 1;
                int y = (buffer[pos2] << 7) ^ pos3;
                if (y < 0) {
                    x = y ^ (-128);
                } else {
                    int pos5 = pos4 + 1;
                    int y2 = (buffer[pos4] << 14) ^ y;
                    if (y2 >= 0) {
                        x = y2 ^ 16256;
                    } else {
                        pos4 = pos5 + 1;
                        int y3 = (buffer[pos5] << 21) ^ y2;
                        if (y3 < 0) {
                            x = (-2080896) ^ y3;
                        } else {
                            long j = y3;
                            pos5 = pos4 + 1;
                            long x2 = j ^ (buffer[pos4] << 28);
                            if (x2 >= 0) {
                                x = 266354560 ^ x2;
                            } else {
                                pos4 = pos5 + 1;
                                long x3 = (buffer[pos5] << 35) ^ x2;
                                if (x3 < 0) {
                                    x = (-34093383808L) ^ x3;
                                } else {
                                    pos5 = pos4 + 1;
                                    long x4 = (buffer[pos4] << 42) ^ x3;
                                    if (x4 >= 0) {
                                        x = 4363953127296L ^ x4;
                                    } else {
                                        pos4 = pos5 + 1;
                                        long x5 = (buffer[pos5] << 49) ^ x4;
                                        if (x5 < 0) {
                                            x = (-558586000294016L) ^ x5;
                                        } else {
                                            pos5 = pos4 + 1;
                                            x = ((buffer[pos4] << 56) ^ x5) ^ 71499008037633920L;
                                            if (x < 0) {
                                                pos4 = pos5 + 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    pos4 = pos5;
                }
                this.bufferPos = pos4;
                return x;
            }
        }
        return readRawVarint64SlowPath();
    }

    long readRawVarint64SlowPath() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = readRawByte();
            result |= (b & Bidi.LEVEL_DEFAULT_RTL) << shift;
            if ((b & 128) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 4) {
            refillBuffer(4);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 4;
        return (buffer[pos] & 255) | ((buffer[pos + 1] & 255) << 8) | ((buffer[pos + 2] & 255) << 16) | ((buffer[pos + 3] & 255) << 24);
    }

    public long readRawLittleEndian64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 8) {
            refillBuffer(8);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 8;
        return (buffer[pos] & 255) | ((buffer[pos + 1] & 255) << 8) | ((buffer[pos + 2] & 255) << 16) | ((buffer[pos + 3] & 255) << 24) | ((buffer[pos + 4] & 255) << 32) | ((buffer[pos + 5] & 255) << 40) | ((buffer[pos + 6] & 255) << 48) | ((buffer[pos + 7] & 255) << 56);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private CodedInputStream(byte[] buffer, int off, int len, boolean bufferIsImmutable) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 100;
        this.sizeLimit = 67108864;
        this.refillCallback = null;
        this.buffer = buffer;
        this.bufferSize = off + len;
        this.bufferPos = off;
        this.totalBytesRetired = -off;
        this.input = null;
        this.bufferIsImmutable = bufferIsImmutable;
    }

    private CodedInputStream(InputStream input, int bufferSize) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 100;
        this.sizeLimit = 67108864;
        this.refillCallback = null;
        this.buffer = new byte[bufferSize];
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input;
        this.bufferIsImmutable = false;
    }

    public void enableAliasing(boolean enabled) {
        this.enableAliasing = enabled;
    }

    public int setRecursionLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
        }
        int oldLimit = this.recursionLimit;
        this.recursionLimit = limit;
        return oldLimit;
    }

    public int setSizeLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
        }
        int oldLimit = this.sizeLimit;
        this.sizeLimit = limit;
        return oldLimit;
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = -this.bufferPos;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        int byteLimit2 = byteLimit + this.totalBytesRetired + this.bufferPos;
        int oldLimit = this.currentLimit;
        if (byteLimit2 > oldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        this.currentLimit = byteLimit2;
        recomputeBufferSizeAfterLimit();
        return oldLimit;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.totalBytesRetired + this.bufferSize;
        if (bufferEnd > this.currentLimit) {
            this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        int currentAbsolutePosition = this.totalBytesRetired + this.bufferPos;
        return this.currentLimit - currentAbsolutePosition;
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !tryRefillBuffer(1);
    }

    public int getTotalBytesRead() {
        return this.totalBytesRetired + this.bufferPos;
    }

    private void refillBuffer(int n) throws IOException {
        if (!tryRefillBuffer(n)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        if (this.bufferPos + n <= this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when " + n + " bytes were already available in buffer");
        } else if (this.totalBytesRetired + this.bufferPos + n > this.currentLimit) {
            return false;
        } else {
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            if (this.input != null) {
                int pos = this.bufferPos;
                if (pos > 0) {
                    if (this.bufferSize > pos) {
                        System.arraycopy(this.buffer, pos, this.buffer, 0, this.bufferSize - pos);
                    }
                    this.totalBytesRetired += pos;
                    this.bufferSize -= pos;
                    this.bufferPos = 0;
                }
                int bytesRead = this.input.read(this.buffer, this.bufferSize, this.buffer.length - this.bufferSize);
                if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                    throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + bytesRead + "\nThe InputStream implementation is buggy.");
                } else if (bytesRead > 0) {
                    this.bufferSize += bytesRead;
                    if ((this.totalBytesRetired + n) - this.sizeLimit > 0) {
                        throw InvalidProtocolBufferException.sizeLimitExceeded();
                    }
                    recomputeBufferSizeAfterLimit();
                    if (this.bufferSize >= n) {
                        return true;
                    }
                    return tryRefillBuffer(n);
                }
            }
            return false;
        }
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(1);
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    public byte[] readRawBytes(int size) throws IOException {
        int pos = this.bufferPos;
        if (size <= this.bufferSize - pos && size > 0) {
            this.bufferPos = pos + size;
            return Arrays.copyOfRange(this.buffer, pos, pos + size);
        }
        return readRawBytesSlowPath(size);
    }

    private byte[] readRawBytesSlowPath(int size) throws IOException {
        if (size <= 0) {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        int currentMessageSize = this.totalBytesRetired + this.bufferPos + size;
        if (currentMessageSize > this.sizeLimit) {
            throw InvalidProtocolBufferException.sizeLimitExceeded();
        }
        if (currentMessageSize > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (this.input == null) {
            throw InvalidProtocolBufferException.truncatedMessage();
        } else {
            int originalBufferPos = this.bufferPos;
            int bufferedBytes = this.bufferSize - this.bufferPos;
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            this.bufferSize = 0;
            int sizeLeft = size - bufferedBytes;
            if (sizeLeft < 4096 || sizeLeft <= this.input.available()) {
                byte[] bytes = new byte[size];
                System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
                int pos = bufferedBytes;
                while (pos < bytes.length) {
                    int n = this.input.read(bytes, pos, size - pos);
                    if (n == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n;
                    pos += n;
                }
                return bytes;
            }
            List<byte[]> chunks = new ArrayList<>();
            while (sizeLeft > 0) {
                byte[] chunk = new byte[Math.min(sizeLeft, 4096)];
                int pos2 = 0;
                while (pos2 < chunk.length) {
                    int n2 = this.input.read(chunk, pos2, chunk.length - pos2);
                    if (n2 == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n2;
                    pos2 += n2;
                }
                sizeLeft -= chunk.length;
                chunks.add(chunk);
            }
            byte[] bytes2 = new byte[size];
            System.arraycopy(this.buffer, originalBufferPos, bytes2, 0, bufferedBytes);
            int pos3 = bufferedBytes;
            for (byte[] chunk2 : chunks) {
                System.arraycopy(chunk2, 0, bytes2, pos3, chunk2.length);
                pos3 += chunk2.length;
            }
            return bytes2;
        }
    }

    public void skipRawBytes(int size) throws IOException {
        if (size <= this.bufferSize - this.bufferPos && size >= 0) {
            this.bufferPos += size;
        } else {
            skipRawBytesSlowPath(size);
        }
    }

    private void skipRawBytesSlowPath(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (this.totalBytesRetired + this.bufferPos + size > this.currentLimit) {
            int pos = this.currentLimit;
            skipRawBytes((pos - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int pos2 = this.bufferSize - this.bufferPos;
        this.bufferPos = this.bufferSize;
        refillBuffer(1);
        while (size - pos2 > this.bufferSize) {
            pos2 += this.bufferSize;
            this.bufferPos = this.bufferSize;
            refillBuffer(1);
        }
        this.bufferPos = size - pos2;
    }
}
