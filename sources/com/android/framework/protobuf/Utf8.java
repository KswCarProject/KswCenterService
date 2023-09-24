package com.android.framework.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Logger logger = Logger.getLogger(Utf8.class.getName());
    private static final Processor processor;

    static {
        processor = UnsafeProcessor.isAvailable() ? new UnsafeProcessor() : new SafeProcessor();
    }

    public static boolean isValidUtf8(byte[] bytes) {
        return processor.isValidUtf8(bytes, 0, bytes.length);
    }

    public static boolean isValidUtf8(byte[] bytes, int index, int limit) {
        return processor.isValidUtf8(bytes, index, limit);
    }

    public static int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
        return processor.partialIsValidUtf8(state, bytes, index, limit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int byte1) {
        if (byte1 <= -12) {
            return byte1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2) {
        if (byte1 > -12 || byte2 > -65) {
            return -1;
        }
        return (byte2 << 8) ^ byte1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2, int byte3) {
        if (byte1 > -12 || byte2 > -65 || byte3 > -65) {
            return -1;
        }
        return ((byte2 << 8) ^ byte1) ^ (byte3 << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bytes, int index, int limit) {
        int byte1 = bytes[index - 1];
        switch (limit - index) {
            case 0:
                return incompleteStateFor(byte1);
            case 1:
                return incompleteStateFor(byte1, bytes[index]);
            case 2:
                return incompleteStateFor(byte1, bytes[index], bytes[index + 1]);
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer buffer, int byte1, int index, int remaining) {
        switch (remaining) {
            case 0:
                return incompleteStateFor(byte1);
            case 1:
                return incompleteStateFor(byte1, buffer.get(index));
            case 2:
                return incompleteStateFor(byte1, buffer.get(index), buffer.get(index + 1));
            default:
                throw new AssertionError();
        }
    }

    /* loaded from: classes4.dex */
    static class UnpairedSurrogateException extends IllegalArgumentException {
        private UnpairedSurrogateException(int index, int length) {
            super("Unpaired surrogate at index " + index + " of " + length);
        }
    }

    static int encodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < '\u0080') {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c < '\u0800') {
                    utf8Length += ('\u007f' - c) >>> 31;
                    i++;
                } else {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
            } else {
                break;
            }
        }
        if (utf8Length < utf16Length) {
            throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (utf8Length + 4294967296L));
        }
        return utf8Length;
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int utf8Length2 = start;
        while (utf8Length2 < utf16Length) {
            char c = sequence.charAt(utf8Length2);
            if (c < '\u0800') {
                utf8Length += ('\u007f' - c) >>> 31;
            } else {
                utf8Length += 2;
                if ('\ud800' <= c && c <= '\udfff') {
                    int cp = Character.codePointAt(sequence, utf8Length2);
                    if (cp < 65536) {
                        throw new UnpairedSurrogateException(utf8Length2, utf16Length);
                    }
                    utf8Length2++;
                }
            }
            utf8Length2++;
        }
        return utf8Length;
    }

    static int encode(CharSequence in, byte[] out, int offset, int length) {
        return processor.encodeUtf8(in, out, offset, length);
    }

    static boolean isValidUtf8(ByteBuffer buffer) {
        return processor.isValidUtf8(buffer, buffer.position(), buffer.remaining());
    }

    static int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
        return processor.partialIsValidUtf8(state, buffer, index, limit);
    }

    static void encodeUtf8(CharSequence in, ByteBuffer out) {
        processor.encodeUtf8(in, out);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer buffer, int index, int limit) {
        int i = index;
        int lim = limit - 7;
        while (i < lim && (buffer.getLong(i) & ASCII_MASK_LONG) == 0) {
            i += 8;
        }
        return i - index;
    }

    /* loaded from: classes4.dex */
    static abstract class Processor {
        abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2);

        abstract void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer);

        abstract int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3);

        abstract int partialIsValidUtf8Direct(int i, ByteBuffer byteBuffer, int i2, int i3);

        Processor() {
        }

        final boolean isValidUtf8(byte[] bytes, int index, int limit) {
            return partialIsValidUtf8(0, bytes, index, limit) == 0;
        }

        final boolean isValidUtf8(ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8(0, buffer, index, limit) == 0;
        }

        final int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
            if (buffer.hasArray()) {
                int offset = buffer.arrayOffset();
                return partialIsValidUtf8(state, buffer.array(), offset + index, offset + limit);
            } else if (buffer.isDirect()) {
                return partialIsValidUtf8Direct(state, buffer, index, limit);
            } else {
                return partialIsValidUtf8Default(state, buffer, index, limit);
            }
        }

        final int partialIsValidUtf8Default(int state, ByteBuffer buffer, int index, int limit) {
            int index2;
            if (state != 0) {
                if (index >= limit) {
                    return state;
                }
                byte byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        index2 = index + 1;
                        if (buffer.get(index) > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    byte byte2 = (byte) (~(state >> 8));
                    if (byte2 == 0) {
                        int index3 = index + 1;
                        byte2 = buffer.get(index);
                        if (index3 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte2);
                        }
                        index = index3;
                    }
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        index2 = index + 1;
                        if (buffer.get(index) > -65) {
                        }
                    }
                    return -1;
                } else {
                    byte byte22 = (byte) (~(state >> 8));
                    byte byte3 = 0;
                    if (byte22 == 0) {
                        int index4 = index + 1;
                        byte22 = buffer.get(index);
                        if (index4 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22);
                        }
                        index = index4;
                    } else {
                        byte3 = (byte) (state >> 16);
                    }
                    if (byte3 == 0) {
                        int index5 = index + 1;
                        byte3 = buffer.get(index);
                        if (index5 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22, byte3);
                        }
                        index = index5;
                    }
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        int index6 = index + 1;
                        if (buffer.get(index) <= -65) {
                            index2 = index6;
                        }
                    }
                    return -1;
                }
            }
            index2 = index;
            return partialIsValidUtf8(buffer, index2, limit);
        }

        private static int partialIsValidUtf8(ByteBuffer buffer, int index, int limit) {
            int index2 = index + Utf8.estimateConsecutiveAscii(buffer, index, limit);
            while (index2 < limit) {
                int index3 = index2 + 1;
                int index4 = buffer.get(index2);
                if (index4 >= 0) {
                    index2 = index3;
                } else if (index4 < -32) {
                    if (index3 >= limit) {
                        return index4;
                    }
                    if (index4 < -62 || buffer.get(index3) > -65) {
                        return -1;
                    }
                    index2 = index3 + 1;
                } else if (index4 < -16) {
                    if (index3 >= limit - 1) {
                        return Utf8.incompleteStateFor(buffer, index4, index3, limit - index3);
                    }
                    int index5 = index3 + 1;
                    int index6 = buffer.get(index3);
                    if (index6 > -65 || ((index4 == -32 && index6 < -96) || ((index4 == -19 && index6 >= -96) || buffer.get(index5) > -65))) {
                        return -1;
                    }
                    index2 = index5 + 1;
                } else if (index3 >= limit - 2) {
                    return Utf8.incompleteStateFor(buffer, index4, index3, limit - index3);
                } else {
                    int index7 = index3 + 1;
                    int index8 = buffer.get(index3);
                    if (index8 <= -65 && (((index4 << 28) + (index8 + 112)) >> 30) == 0) {
                        int index9 = index7 + 1;
                        if (buffer.get(index7) <= -65) {
                            index2 = index9 + 1;
                            if (buffer.get(index9) > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }

        final void encodeUtf8(CharSequence in, ByteBuffer out) {
            if (out.hasArray()) {
                int offset = out.arrayOffset();
                int endIndex = Utf8.encode(in, out.array(), out.position() + offset, out.remaining());
                out.position(endIndex - offset);
            } else if (out.isDirect()) {
                encodeUtf8Direct(in, out);
            } else {
                encodeUtf8Default(in, out);
            }
        }

        final void encodeUtf8Default(CharSequence in, ByteBuffer out) {
            int inLength = in.length();
            int outIx = out.position();
            int inIx = 0;
            while (inIx < inLength) {
                try {
                    char c = in.charAt(inIx);
                    if (c >= '\u0080') {
                        break;
                    }
                    out.put(outIx + inIx, (byte) c);
                    inIx++;
                } catch (IndexOutOfBoundsException e) {
                    int badWriteIndex = out.position() + Math.max(inIx, (outIx - out.position()) + 1);
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + badWriteIndex);
                }
            }
            if (inIx == inLength) {
                out.position(outIx + inIx);
                return;
            }
            int outIx2 = outIx + inIx;
            while (inIx < inLength) {
                char c2 = in.charAt(inIx);
                if (c2 < '\u0080') {
                    out.put(outIx2, (byte) c2);
                } else if (c2 < '\u0800') {
                    int outIx3 = outIx2 + 1;
                    try {
                        out.put(outIx2, (byte) ((c2 >>> 6) | 192));
                        out.put(outIx3, (byte) ((c2 & '?') | 128));
                        outIx2 = outIx3;
                    } catch (IndexOutOfBoundsException e2) {
                        outIx = outIx3;
                        int badWriteIndex2 = out.position() + Math.max(inIx, (outIx - out.position()) + 1);
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + badWriteIndex2);
                    }
                } else if (c2 >= '\ud800' && '\udfff' >= c2) {
                    if (inIx + 1 != inLength) {
                        inIx++;
                        char low = in.charAt(inIx);
                        if (Character.isSurrogatePair(c2, low)) {
                            int codePoint = Character.toCodePoint(c2, low);
                            int outIx4 = outIx2 + 1;
                            try {
                                out.put(outIx2, (byte) ((codePoint >>> 18) | 240));
                                int outIx5 = outIx4 + 1;
                                out.put(outIx4, (byte) (((codePoint >>> 12) & 63) | 128));
                                int outIx6 = outIx5 + 1;
                                out.put(outIx5, (byte) (((codePoint >>> 6) & 63) | 128));
                                out.put(outIx6, (byte) ((codePoint & 63) | 128));
                                outIx2 = outIx6;
                            } catch (IndexOutOfBoundsException e3) {
                                outIx = outIx4;
                                int badWriteIndex22 = out.position() + Math.max(inIx, (outIx - out.position()) + 1);
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + badWriteIndex22);
                            }
                        }
                    }
                    throw new UnpairedSurrogateException(inIx, inLength);
                } else {
                    int outIx7 = outIx2 + 1;
                    out.put(outIx2, (byte) ((c2 >>> '\f') | 224));
                    outIx2 = outIx7 + 1;
                    out.put(outIx7, (byte) (((c2 >>> 6) & 63) | 128));
                    out.put(outIx2, (byte) ((c2 & '?') | 128));
                }
                inIx++;
                outIx2++;
            }
            out.position(outIx2);
        }
    }

    /* loaded from: classes4.dex */
    static final class SafeProcessor extends Processor {
        SafeProcessor() {
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
            int index2;
            if (state != 0) {
                if (index >= limit) {
                    return state;
                }
                int byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        index2 = index + 1;
                        if (bytes[index] > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    int byte2 = (byte) (~(state >> 8));
                    if (byte2 == 0) {
                        int index3 = index + 1;
                        byte2 = bytes[index];
                        if (index3 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte2);
                        }
                        index = index3;
                    }
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        index2 = index + 1;
                        if (bytes[index] > -65) {
                        }
                    }
                    return -1;
                } else {
                    int byte22 = (byte) (~(state >> 8));
                    int byte3 = 0;
                    if (byte22 == 0) {
                        int index4 = index + 1;
                        byte22 = bytes[index];
                        if (index4 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22);
                        }
                        index = index4;
                    } else {
                        byte3 = (byte) (state >> 16);
                    }
                    if (byte3 == 0) {
                        int index5 = index + 1;
                        byte3 = bytes[index];
                        if (index5 >= limit) {
                            return Utf8.incompleteStateFor(byte1, byte22, byte3);
                        }
                        index = index5;
                    }
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        int index6 = index + 1;
                        if (bytes[index] <= -65) {
                            index2 = index6;
                        }
                    }
                    return -1;
                }
            }
            index2 = index;
            return partialIsValidUtf8(bytes, index2, limit);
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8Default(state, buffer, index, limit);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
            return r13 + r0;
         */
        @Override // com.android.framework.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            int j;
            char c;
            int utf16Length = in.length();
            int i = 0;
            int limit = offset + length;
            while (i < utf16Length && i + offset < limit && (c = in.charAt(i)) < '\u0080') {
                out[offset + i] = (byte) c;
                i++;
            }
            int j2 = offset + i;
            while (i < utf16Length) {
                char c2 = in.charAt(i);
                if (c2 < '\u0080' && j2 < limit) {
                    j = j2 + 1;
                    out[j2] = (byte) c2;
                } else {
                    if (c2 < '\u0800' && j2 <= limit - 2) {
                        int j3 = j2 + 1;
                        out[j2] = (byte) ((c2 >>> 6) | 960);
                        j2 = j3 + 1;
                        out[j3] = (byte) ((c2 & '?') | 128);
                    } else if ((c2 < '\ud800' || '\udfff' < c2) && j2 <= limit - 3) {
                        int j4 = j2 + 1;
                        out[j2] = (byte) ((c2 >>> '\f') | 480);
                        int j5 = j4 + 1;
                        out[j4] = (byte) (((c2 >>> 6) & 63) | 128);
                        j = j5 + 1;
                        out[j5] = (byte) ((c2 & '?') | 128);
                    } else if (j2 <= limit - 4) {
                        if (i + 1 != in.length()) {
                            i++;
                            char low = in.charAt(i);
                            if (Character.isSurrogatePair(c2, low)) {
                                int codePoint = Character.toCodePoint(c2, low);
                                int j6 = j2 + 1;
                                out[j2] = (byte) ((codePoint >>> 18) | 240);
                                int j7 = j6 + 1;
                                out[j6] = (byte) (((codePoint >>> 12) & 63) | 128);
                                int j8 = j7 + 1;
                                out[j7] = (byte) (((codePoint >>> 6) & 63) | 128);
                                j2 = j8 + 1;
                                out[j8] = (byte) ((codePoint & 63) | 128);
                            }
                        }
                        throw new UnpairedSurrogateException(i - 1, utf16Length);
                    } else if ('\ud800' <= c2 && c2 <= '\udfff' && (i + 1 == in.length() || !Character.isSurrogatePair(c2, in.charAt(i + 1)))) {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    } else {
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + c2 + " at index " + j2);
                    }
                    i++;
                }
                j2 = j;
                i++;
            }
            return j2;
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
            encodeUtf8Default(in, out);
        }

        private static int partialIsValidUtf8(byte[] bytes, int index, int limit) {
            while (index < limit && bytes[index] >= 0) {
                index++;
            }
            if (index >= limit) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bytes, index, limit);
        }

        private static int partialIsValidUtf8NonAscii(byte[] bytes, int index, int limit) {
            while (index < limit) {
                int index2 = index + 1;
                int index3 = bytes[index];
                if (index3 >= 0) {
                    index = index2;
                } else if (index3 < -32) {
                    if (index2 >= limit) {
                        return index3;
                    }
                    if (index3 >= -62) {
                        index = index2 + 1;
                        if (bytes[index2] > -65) {
                        }
                    }
                    return -1;
                } else if (index3 < -16) {
                    if (index2 >= limit - 1) {
                        return Utf8.incompleteStateFor(bytes, index2, limit);
                    }
                    int index4 = index2 + 1;
                    int index5 = bytes[index2];
                    if (index5 <= -65 && ((index3 != -32 || index5 >= -96) && (index3 != -19 || index5 < -96))) {
                        index = index4 + 1;
                        if (bytes[index4] > -65) {
                        }
                    }
                    return -1;
                } else {
                    int index6 = limit - 2;
                    if (index2 >= index6) {
                        return Utf8.incompleteStateFor(bytes, index2, limit);
                    }
                    int index7 = index2 + 1;
                    int index8 = bytes[index2];
                    if (index8 <= -65 && (((index3 << 28) + (index8 + 112)) >> 30) == 0) {
                        int index9 = index7 + 1;
                        if (bytes[index7] <= -65) {
                            index = index9 + 1;
                            if (bytes[index9] > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    static final class UnsafeProcessor extends Processor {
        private static final boolean AVAILABLE;
        private static final Unsafe UNSAFE = getUnsafe();
        private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(field(Buffer.class, "address"));
        private static final int ARRAY_BASE_OFFSET = byteArrayBaseOffset();

        UnsafeProcessor() {
        }

        static {
            AVAILABLE = BUFFER_ADDRESS_OFFSET != -1 && ARRAY_BASE_OFFSET % 8 == 0;
        }

        static boolean isAvailable() {
            return AVAILABLE;
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r18, r2) > (-65)) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0068, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r18, r2) > (-65)) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00b3, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r18, r2) > (-65)) goto L58;
         */
        @Override // com.android.framework.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
            long offset;
            if ((index | limit | (bytes.length - limit)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bytes.length), Integer.valueOf(index), Integer.valueOf(limit)));
            }
            long offset2 = ARRAY_BASE_OFFSET + index;
            long offsetLimit = ARRAY_BASE_OFFSET + limit;
            if (state != 0) {
                if (offset2 >= offsetLimit) {
                    return state;
                }
                int byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        offset = 1 + offset2;
                    }
                    return -1;
                } else if (byte1 < -16) {
                    int byte2 = (byte) (~(state >> 8));
                    if (byte2 == 0) {
                        long offset3 = offset2 + 1;
                        byte2 = UNSAFE.getByte(bytes, offset2);
                        if (offset3 >= offsetLimit) {
                            return Utf8.incompleteStateFor(byte1, byte2);
                        }
                        offset2 = offset3;
                    }
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        offset = 1 + offset2;
                    }
                    return -1;
                } else {
                    int byte22 = (byte) (~(state >> 8));
                    int byte3 = 0;
                    if (byte22 == 0) {
                        long offset4 = offset2 + 1;
                        byte22 = UNSAFE.getByte(bytes, offset2);
                        if (offset4 >= offsetLimit) {
                            return Utf8.incompleteStateFor(byte1, byte22);
                        }
                        offset2 = offset4;
                    } else {
                        byte3 = (byte) (state >> 16);
                    }
                    if (byte3 == 0) {
                        long offset5 = offset2 + 1;
                        byte3 = UNSAFE.getByte(bytes, offset2);
                        if (offset5 >= offsetLimit) {
                            return Utf8.incompleteStateFor(byte1, byte22, byte3);
                        }
                        offset2 = offset5;
                    }
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        offset = 1 + offset2;
                    }
                    return -1;
                }
            }
            offset = offset2;
            return partialIsValidUtf8(bytes, offset, (int) (offsetLimit - offset));
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r2) > (-65)) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x006b, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r2) > (-65)) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00b6, code lost:
            if (com.android.framework.protobuf.Utf8.UnsafeProcessor.UNSAFE.getByte(r2) > (-65)) goto L58;
         */
        @Override // com.android.framework.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
            long address;
            if ((index | limit | (buffer.limit() - limit)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(buffer.limit()), Integer.valueOf(index), Integer.valueOf(limit)));
            }
            long address2 = addressOffset(buffer) + index;
            long addressLimit = (limit - index) + address2;
            if (state != 0) {
                if (address2 >= addressLimit) {
                    return state;
                }
                int byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        address = 1 + address2;
                    }
                    return -1;
                } else if (byte1 < -16) {
                    int byte2 = (byte) (~(state >> 8));
                    if (byte2 == 0) {
                        long address3 = address2 + 1;
                        byte2 = UNSAFE.getByte(address2);
                        if (address3 >= addressLimit) {
                            return Utf8.incompleteStateFor(byte1, byte2);
                        }
                        address2 = address3;
                    }
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        address = 1 + address2;
                    }
                    return -1;
                } else {
                    int byte22 = (byte) (~(state >> 8));
                    int byte3 = 0;
                    if (byte22 == 0) {
                        long address4 = address2 + 1;
                        byte22 = UNSAFE.getByte(address2);
                        if (address4 >= addressLimit) {
                            return Utf8.incompleteStateFor(byte1, byte22);
                        }
                        address2 = address4;
                    } else {
                        byte3 = (byte) (state >> 16);
                    }
                    if (byte3 == 0) {
                        long address5 = address2 + 1;
                        byte3 = UNSAFE.getByte(address2);
                        if (address5 >= addressLimit) {
                            return Utf8.incompleteStateFor(byte1, byte22, byte3);
                        }
                        address2 = address5;
                    }
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        address = 1 + address2;
                    }
                    return -1;
                }
            }
            address = address2;
            return partialIsValidUtf8(address, (int) (addressLimit - address));
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            char c;
            long j;
            char c2;
            long outIx;
            char c3;
            long outIx2 = ARRAY_BASE_OFFSET + offset;
            long outLimit = length + outIx2;
            int inLimit = in.length();
            if (inLimit > length || out.length - length < offset) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inLimit - 1) + " at index " + (offset + length));
            }
            int inIx = 0;
            while (true) {
                c = '\u0080';
                j = 1;
                if (inIx >= inLimit || (c3 = in.charAt(inIx)) >= '\u0080') {
                    break;
                }
                UNSAFE.putByte(out, outIx2, (byte) c3);
                inIx++;
                outIx2 = 1 + outIx2;
            }
            if (inIx == inLimit) {
                return (int) (outIx2 - ARRAY_BASE_OFFSET);
            }
            while (inIx < inLimit) {
                char c4 = in.charAt(inIx);
                if (c4 >= c || outIx2 >= outLimit) {
                    if (c4 < '\u0800' && outIx2 <= outLimit - 2) {
                        long outIx3 = outIx2 + j;
                        UNSAFE.putByte(out, outIx2, (byte) ((c4 >>> 6) | 960));
                        UNSAFE.putByte(out, outIx3, (byte) ((c4 & '?') | 128));
                        outIx2 = outIx3 + 1;
                    } else if ((c4 >= '\ud800' && '\udfff' >= c4) || outIx2 > outLimit - 3) {
                        if (outIx2 > outLimit - 4) {
                            if ('\ud800' > c4 || c4 > '\udfff' || (inIx + 1 != inLimit && Character.isSurrogatePair(c4, in.charAt(inIx + 1)))) {
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + c4 + " at index " + outIx2);
                            }
                            throw new UnpairedSurrogateException(inIx, inLimit);
                        }
                        if (inIx + 1 != inLimit) {
                            inIx++;
                            char low = in.charAt(inIx);
                            if (Character.isSurrogatePair(c4, low)) {
                                int codePoint = Character.toCodePoint(c4, low);
                                long outIx4 = outIx2 + 1;
                                UNSAFE.putByte(out, outIx2, (byte) ((codePoint >>> 18) | 240));
                                long outIx5 = outIx4 + 1;
                                UNSAFE.putByte(out, outIx4, (byte) (((codePoint >>> 12) & 63) | 128));
                                long outIx6 = outIx5 + 1;
                                c2 = '\u0080';
                                UNSAFE.putByte(out, outIx5, (byte) (((codePoint >>> 6) & 63) | 128));
                                outIx = 1;
                                outIx2 = outIx6 + 1;
                                UNSAFE.putByte(out, outIx6, (byte) ((codePoint & 63) | 128));
                            }
                        }
                        throw new UnpairedSurrogateException(inIx - 1, inLimit);
                    } else {
                        long outIx7 = outIx2 + 1;
                        UNSAFE.putByte(out, outIx2, (byte) ((c4 >>> '\f') | 480));
                        long outIx8 = outIx7 + 1;
                        UNSAFE.putByte(out, outIx7, (byte) (((c4 >>> 6) & 63) | 128));
                        UNSAFE.putByte(out, outIx8, (byte) ((c4 & '?') | 128));
                        outIx2 = outIx8 + 1;
                    }
                    c2 = '\u0080';
                    outIx = 1;
                } else {
                    UNSAFE.putByte(out, outIx2, (byte) c4);
                    outIx2 += j;
                    c2 = '\u0080';
                    outIx = j;
                }
                inIx++;
                c = c2;
                j = outIx;
            }
            return (int) (outIx2 - ARRAY_BASE_OFFSET);
        }

        @Override // com.android.framework.protobuf.Utf8.Processor
        void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
            char c;
            long j;
            long address;
            char c2;
            long address2 = addressOffset(out);
            long outIx = out.position() + address2;
            long outLimit = out.limit() + address2;
            int inLimit = in.length();
            if (inLimit > outLimit - outIx) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inLimit - 1) + " at index " + out.limit());
            }
            int inIx = 0;
            while (true) {
                c = '\u0080';
                j = 1;
                if (inIx >= inLimit || (c2 = in.charAt(inIx)) >= '\u0080') {
                    break;
                }
                UNSAFE.putByte(outIx, (byte) c2);
                inIx++;
                outIx = 1 + outIx;
            }
            if (inIx == inLimit) {
                out.position((int) (outIx - address2));
                return;
            }
            while (inIx < inLimit) {
                char c3 = in.charAt(inIx);
                if (c3 < c && outIx < outLimit) {
                    UNSAFE.putByte(outIx, (byte) c3);
                    address = address2;
                    outIx += j;
                } else if (c3 >= '\u0800' || outIx > outLimit - 2) {
                    if (c3 >= '\ud800' && '\udfff' >= c3) {
                        address = address2;
                    } else if (outIx <= outLimit - 3) {
                        long outIx2 = outIx + 1;
                        UNSAFE.putByte(outIx, (byte) ((c3 >>> '\f') | 480));
                        address = address2;
                        long outIx3 = outIx2 + 1;
                        UNSAFE.putByte(outIx2, (byte) (((c3 >>> 6) & 63) | 128));
                        UNSAFE.putByte(outIx3, (byte) ((c3 & '?') | 128));
                        outIx = outIx3 + 1;
                    } else {
                        address = address2;
                    }
                    if (outIx > outLimit - 4) {
                        if ('\ud800' <= c3 && c3 <= '\udfff' && (inIx + 1 == inLimit || !Character.isSurrogatePair(c3, in.charAt(inIx + 1)))) {
                            throw new UnpairedSurrogateException(inIx, inLimit);
                        }
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + c3 + " at index " + outIx);
                    }
                    if (inIx + 1 != inLimit) {
                        inIx++;
                        char low = in.charAt(inIx);
                        if (Character.isSurrogatePair(c3, low)) {
                            int codePoint = Character.toCodePoint(c3, low);
                            long outIx4 = outIx + 1;
                            UNSAFE.putByte(outIx, (byte) ((codePoint >>> 18) | 240));
                            long outIx5 = outIx4 + 1;
                            c = '\u0080';
                            UNSAFE.putByte(outIx4, (byte) (((codePoint >>> 12) & 63) | 128));
                            long outIx6 = outIx5 + 1;
                            UNSAFE.putByte(outIx5, (byte) (((codePoint >>> 6) & 63) | 128));
                            outIx = outIx6 + 1;
                            UNSAFE.putByte(outIx6, (byte) ((codePoint & 63) | 128));
                            inIx++;
                            address2 = address;
                            j = 1;
                        }
                    }
                    throw new UnpairedSurrogateException(inIx - 1, inLimit);
                } else {
                    long outIx7 = outIx + j;
                    UNSAFE.putByte(outIx, (byte) ((c3 >>> 6) | 960));
                    UNSAFE.putByte(outIx7, (byte) ((c3 & '?') | 128));
                    address = address2;
                    outIx = outIx7 + 1;
                }
                c = '\u0080';
                inIx++;
                address2 = address;
                j = 1;
            }
            out.position((int) (outIx - address2));
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bytes, long offset, int maxChars) {
            if (maxChars < 16) {
                return 0;
            }
            int unaligned = ((int) offset) & 7;
            long offset2 = offset;
            int j = unaligned;
            while (j > 0) {
                long offset3 = 1 + offset2;
                if (UNSAFE.getByte(bytes, offset2) >= 0) {
                    j--;
                    offset2 = offset3;
                } else {
                    return unaligned - j;
                }
            }
            int remaining = maxChars - unaligned;
            while (remaining >= 8 && (UNSAFE.getLong(bytes, offset2) & Utf8.ASCII_MASK_LONG) == 0) {
                offset2 += 8;
                remaining -= 8;
            }
            return maxChars - remaining;
        }

        private static int unsafeEstimateConsecutiveAscii(long address, int maxChars) {
            if (maxChars < 16) {
                return 0;
            }
            int unaligned = ((int) address) & 7;
            long address2 = address;
            int j = unaligned;
            while (j > 0) {
                long address3 = 1 + address2;
                if (UNSAFE.getByte(address2) >= 0) {
                    j--;
                    address2 = address3;
                } else {
                    return unaligned - j;
                }
            }
            int remaining = maxChars - unaligned;
            while (remaining >= 8 && (UNSAFE.getLong(address2) & Utf8.ASCII_MASK_LONG) == 0) {
                address2 += 8;
                remaining -= 8;
            }
            return maxChars - remaining;
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0041, code lost:
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0073, code lost:
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00ab, code lost:
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int partialIsValidUtf8(byte[] bytes, long offset, int remaining) {
            long offset2;
            int skipped = unsafeEstimateConsecutiveAscii(bytes, offset, remaining);
            int remaining2 = remaining - skipped;
            long offset3 = offset + skipped;
            while (true) {
                long offset4 = offset3;
                int byte1 = 0;
                while (true) {
                    if (remaining2 <= 0) {
                        break;
                    }
                    long offset5 = offset4 + 1;
                    int i = UNSAFE.getByte(bytes, offset4);
                    byte1 = i;
                    if (i >= 0) {
                        remaining2--;
                        offset4 = offset5;
                    } else {
                        offset4 = offset5;
                        break;
                    }
                }
                if (remaining2 == 0) {
                    return 0;
                }
                int remaining3 = remaining2 - 1;
                if (byte1 < -32) {
                    if (remaining3 == 0) {
                        return byte1;
                    }
                    remaining2 = remaining3 - 1;
                    if (byte1 < -62) {
                        break;
                    }
                    offset2 = 1 + offset4;
                    if (UNSAFE.getByte(bytes, offset4) > -65) {
                        break;
                    }
                } else if (byte1 < -16) {
                    if (remaining3 < 2) {
                        return unsafeIncompleteStateFor(bytes, byte1, offset4, remaining3);
                    }
                    remaining2 = remaining3 - 2;
                    long offset6 = offset4 + 1;
                    byte b = UNSAFE.getByte(bytes, offset4);
                    if (b <= -65 && ((byte1 != -32 || b >= -96) && (byte1 != -19 || b < -96))) {
                        offset2 = 1 + offset6;
                        if (UNSAFE.getByte(bytes, offset6) > -65) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else if (remaining3 < 3) {
                    return unsafeIncompleteStateFor(bytes, byte1, offset4, remaining3);
                } else {
                    remaining2 = remaining3 - 3;
                    long offset7 = offset4 + 1;
                    int byte2 = UNSAFE.getByte(bytes, offset4);
                    if (byte2 > -65 || (((byte1 << 28) + (byte2 + 112)) >> 30) != 0) {
                        break;
                    }
                    long offset8 = offset7 + 1;
                    if (UNSAFE.getByte(bytes, offset7) > -65) {
                        break;
                    }
                    long offset9 = offset8 + 1;
                    if (UNSAFE.getByte(bytes, offset8) > -65) {
                        break;
                    }
                    offset3 = offset9;
                }
                offset3 = offset2;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0041, code lost:
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0075, code lost:
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00ac, code lost:
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int partialIsValidUtf8(long address, int remaining) {
            int skipped = unsafeEstimateConsecutiveAscii(address, remaining);
            long address2 = address + skipped;
            int remaining2 = remaining - skipped;
            while (true) {
                long address3 = address2;
                int byte1 = 0;
                while (true) {
                    if (remaining2 <= 0) {
                        break;
                    }
                    long address4 = address3 + 1;
                    int i = UNSAFE.getByte(address3);
                    byte1 = i;
                    if (i >= 0) {
                        remaining2--;
                        address3 = address4;
                    } else {
                        address3 = address4;
                        break;
                    }
                }
                if (remaining2 == 0) {
                    return 0;
                }
                int remaining3 = remaining2 - 1;
                if (byte1 < -32) {
                    if (remaining3 == 0) {
                        return byte1;
                    }
                    remaining2 = remaining3 - 1;
                    if (byte1 < -62) {
                        break;
                    }
                    long address5 = 1 + address3;
                    if (UNSAFE.getByte(address3) > -65) {
                        break;
                    }
                    address2 = address5;
                } else if (byte1 < -16) {
                    if (remaining3 < 2) {
                        return unsafeIncompleteStateFor(address3, byte1, remaining3);
                    }
                    remaining2 = remaining3 - 2;
                    long address6 = address3 + 1;
                    byte byte2 = UNSAFE.getByte(address3);
                    if (byte2 > -65 || ((byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96))) {
                        break;
                    }
                    long address7 = address6 + 1;
                    if (UNSAFE.getByte(address6) > -65) {
                        break;
                    }
                    address2 = address7;
                } else if (remaining3 < 3) {
                    return unsafeIncompleteStateFor(address3, byte1, remaining3);
                } else {
                    remaining2 = remaining3 - 3;
                    long address8 = address3 + 1;
                    byte byte22 = UNSAFE.getByte(address3);
                    if (byte22 > -65 || (((byte1 << 28) + (byte22 + 112)) >> 30) != 0) {
                        break;
                    }
                    long address9 = address8 + 1;
                    if (UNSAFE.getByte(address8) > -65) {
                        break;
                    }
                    long address10 = address9 + 1;
                    if (UNSAFE.getByte(address9) > -65) {
                        break;
                    }
                    address2 = address10;
                }
            }
        }

        private static int unsafeIncompleteStateFor(byte[] bytes, int byte1, long offset, int remaining) {
            switch (remaining) {
                case 0:
                    return Utf8.incompleteStateFor(byte1);
                case 1:
                    return Utf8.incompleteStateFor(byte1, UNSAFE.getByte(bytes, offset));
                case 2:
                    return Utf8.incompleteStateFor(byte1, UNSAFE.getByte(bytes, offset), UNSAFE.getByte(bytes, 1 + offset));
                default:
                    throw new AssertionError();
            }
        }

        private static int unsafeIncompleteStateFor(long address, int byte1, int remaining) {
            switch (remaining) {
                case 0:
                    return Utf8.incompleteStateFor(byte1);
                case 1:
                    return Utf8.incompleteStateFor(byte1, UNSAFE.getByte(address));
                case 2:
                    return Utf8.incompleteStateFor(byte1, UNSAFE.getByte(address), UNSAFE.getByte(1 + address));
                default:
                    throw new AssertionError();
            }
        }

        private static Field field(Class<?> clazz, String fieldName) {
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (Throwable th) {
                field = null;
            }
            Logger logger = Utf8.logger;
            Level level = Level.FINEST;
            Object[] objArr = new Object[3];
            objArr[0] = clazz.getName();
            objArr[1] = fieldName;
            objArr[2] = field != null ? "available" : "unavailable";
            logger.log(level, "{0}.{1}: {2}", objArr);
            return field;
        }

        private static long fieldOffset(Field field) {
            if (field == null || UNSAFE == null) {
                return -1L;
            }
            return UNSAFE.objectFieldOffset(field);
        }

        private static <T> int byteArrayBaseOffset() {
            if (UNSAFE == null) {
                return -1;
            }
            return UNSAFE.arrayBaseOffset(byte[].class);
        }

        private static long addressOffset(ByteBuffer buffer) {
            return UNSAFE.getLong(buffer, BUFFER_ADDRESS_OFFSET);
        }

        private static Unsafe getUnsafe() {
            Unsafe unsafe = null;
            try {
                unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.android.framework.protobuf.Utf8.UnsafeProcessor.1
                    @Override // java.security.PrivilegedExceptionAction
                    public Unsafe run() throws Exception {
                        Field[] declaredFields;
                        UnsafeProcessor.checkRequiredMethods(Unsafe.class);
                        for (Field f : Unsafe.class.getDeclaredFields()) {
                            f.setAccessible(true);
                            Object x = f.get(null);
                            if (Unsafe.class.isInstance(x)) {
                                return (Unsafe) Unsafe.class.cast(x);
                            }
                        }
                        return null;
                    }
                });
            } catch (Throwable th) {
            }
            Utf8.logger.log(Level.FINEST, "sun.misc.Unsafe: {}", unsafe != null ? "available" : "unavailable");
            return unsafe;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void checkRequiredMethods(Class<Unsafe> clazz) throws NoSuchMethodException, SecurityException {
            clazz.getMethod("arrayBaseOffset", Class.class);
            clazz.getMethod("getByte", Object.class, Long.TYPE);
            clazz.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            clazz.getMethod("objectFieldOffset", Field.class);
            clazz.getMethod("getByte", Long.TYPE);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            clazz.getMethod("putByte", Long.TYPE, Byte.TYPE);
            clazz.getMethod("getLong", Long.TYPE);
        }
    }

    private Utf8() {
    }
}
