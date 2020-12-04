package com.android.framework.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(Utf8.class.getName());
    private static final Processor processor = (UnsafeProcessor.isAvailable() ? new UnsafeProcessor() : new SafeProcessor());

    public static boolean isValidUtf8(byte[] bytes) {
        return processor.isValidUtf8(bytes, 0, bytes.length);
    }

    public static boolean isValidUtf8(byte[] bytes, int index, int limit) {
        return processor.isValidUtf8(bytes, index, limit);
    }

    public static int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
        return processor.partialIsValidUtf8(state, bytes, index, limit);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1) {
        if (byte1 > -12) {
            return -1;
        }
        return byte1;
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2) {
        if (byte1 > -12 || byte2 > -65) {
            return -1;
        }
        return (byte2 << 8) ^ byte1;
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int byte1, int byte2, int byte3) {
        if (byte1 > -12 || byte2 > -65 || byte3 > -65) {
            return -1;
        }
        return ((byte2 << 8) ^ byte1) ^ (byte3 << 16);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bytes, int index, int limit) {
        byte byte1 = bytes[index - 1];
        switch (limit - index) {
            case 0:
                return incompleteStateFor(byte1);
            case 1:
                return incompleteStateFor(byte1, bytes[index]);
            case 2:
                return incompleteStateFor((int) byte1, (int) bytes[index], (int) bytes[index + 1]);
            default:
                throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer buffer, int byte1, int index, int remaining) {
        switch (remaining) {
            case 0:
                return incompleteStateFor(byte1);
            case 1:
                return incompleteStateFor(byte1, buffer.get(index));
            case 2:
                return incompleteStateFor(byte1, (int) buffer.get(index), (int) buffer.get(index + 1));
            default:
                throw new AssertionError();
        }
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        private UnpairedSurrogateException(int index, int length) {
            super("Unpaired surrogate at index " + index + " of " + length);
        }
    }

    static int encodedLength(CharSequence sequence) {
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;
        while (i < utf16Length && sequence.charAt(i) < 128) {
            i++;
        }
        while (true) {
            if (i < utf16Length) {
                char c = sequence.charAt(i);
                if (c >= 2048) {
                    utf8Length += encodedLengthGeneral(sequence, i);
                    break;
                }
                utf8Length += (127 - c) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (utf8Length >= utf16Length) {
            return utf8Length;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) utf8Length) + 4294967296L));
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        int i = start;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 2048) {
                utf8Length += (127 - c) >>> 31;
            } else {
                utf8Length += 2;
                if (55296 <= c && c <= 57343) {
                    if (Character.codePointAt(sequence, i) >= 65536) {
                        i++;
                    } else {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    }
                }
            }
            i++;
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

    /* access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer buffer, int index, int limit) {
        int i = index;
        int lim = limit - 7;
        while (i < lim && (buffer.getLong(i) & ASCII_MASK_LONG) == 0) {
            i += 8;
        }
        return i - index;
    }

    static abstract class Processor {
        /* access modifiers changed from: package-private */
        public abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2);

        /* access modifiers changed from: package-private */
        public abstract void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer);

        /* access modifiers changed from: package-private */
        public abstract int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3);

        /* access modifiers changed from: package-private */
        public abstract int partialIsValidUtf8Direct(int i, ByteBuffer byteBuffer, int i2, int i3);

        Processor() {
        }

        /* access modifiers changed from: package-private */
        public final boolean isValidUtf8(byte[] bytes, int index, int limit) {
            return partialIsValidUtf8(0, bytes, index, limit) == 0;
        }

        /* access modifiers changed from: package-private */
        public final boolean isValidUtf8(ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8(0, buffer, index, limit) == 0;
        }

        /* access modifiers changed from: package-private */
        public final int partialIsValidUtf8(int state, ByteBuffer buffer, int index, int limit) {
            if (buffer.hasArray()) {
                int offset = buffer.arrayOffset();
                return partialIsValidUtf8(state, buffer.array(), offset + index, offset + limit);
            } else if (buffer.isDirect() != 0) {
                return partialIsValidUtf8Direct(state, buffer, index, limit);
            } else {
                return partialIsValidUtf8Default(state, buffer, index, limit);
            }
        }

        /* access modifiers changed from: package-private */
        public final int partialIsValidUtf8Default(int state, ByteBuffer buffer, int index, int limit) {
            int index2;
            if (state == 0) {
                index2 = index;
            } else if (index >= limit) {
                return state;
            } else {
                byte byte1 = (byte) state;
                if (byte1 < -32) {
                    if (byte1 >= -62) {
                        index2 = index + 1;
                        if (buffer.get(index) > -65) {
                            int i = index2;
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
                            int i2 = index2;
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
                            return Utf8.incompleteStateFor((int) byte1, (int) byte22, (int) byte3);
                        }
                        index = index5;
                    }
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0 && byte3 <= -65) {
                        int index6 = index + 1;
                        if (buffer.get(index) > -65) {
                            int i3 = index6;
                        } else {
                            index2 = index6;
                        }
                    }
                    return -1;
                }
            }
            return partialIsValidUtf8(buffer, index2, limit);
        }

        private static int partialIsValidUtf8(ByteBuffer buffer, int index, int limit) {
            int index2 = index + Utf8.estimateConsecutiveAscii(buffer, index, limit);
            while (index2 < limit) {
                int index3 = index2 + 1;
                int index4 = buffer.get(index2);
                int byte1 = index4;
                if (index4 >= 0) {
                    index2 = index3;
                } else if (byte1 < -32) {
                    if (index3 >= limit) {
                        return byte1;
                    }
                    if (byte1 < -62 || buffer.get(index3) > -65) {
                        return -1;
                    }
                    index2 = index3 + 1;
                } else if (byte1 < -16) {
                    if (index3 >= limit - 1) {
                        return Utf8.incompleteStateFor(buffer, byte1, index3, limit - index3);
                    }
                    int index5 = index3 + 1;
                    byte byte2 = buffer.get(index3);
                    if (byte2 > -65 || ((byte1 == -32 && byte2 < -96) || ((byte1 == -19 && byte2 >= -96) || buffer.get(index5) > -65))) {
                        return -1;
                    }
                    index2 = index5 + 1;
                } else if (index3 >= limit - 2) {
                    return Utf8.incompleteStateFor(buffer, byte1, index3, limit - index3);
                } else {
                    int index6 = index3 + 1;
                    int byte22 = buffer.get(index3);
                    if (byte22 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0) {
                        int index7 = index6 + 1;
                        if (buffer.get(index6) <= -65) {
                            index2 = index7 + 1;
                            if (buffer.get(index7) > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public final void encodeUtf8(CharSequence in, ByteBuffer out) {
            if (out.hasArray()) {
                int offset = out.arrayOffset();
                out.position(Utf8.encode(in, out.array(), out.position() + offset, out.remaining()) - offset);
            } else if (out.isDirect()) {
                encodeUtf8Direct(in, out);
            } else {
                encodeUtf8Default(in, out);
            }
        }

        /* access modifiers changed from: package-private */
        public final void encodeUtf8Default(CharSequence in, ByteBuffer out) {
            int outIx;
            int inLength = in.length();
            int outIx2 = out.position();
            int inIx = 0;
            while (inIx < inLength) {
                try {
                    char charAt = in.charAt(inIx);
                    char c = charAt;
                    if (charAt >= 128) {
                        break;
                    }
                    out.put(outIx2 + inIx, (byte) c);
                    inIx++;
                } catch (IndexOutOfBoundsException e) {
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
                }
            }
            if (inIx == inLength) {
                out.position(outIx2 + inIx);
                return;
            }
            outIx2 += inIx;
            while (inIx < inLength) {
                char c2 = in.charAt(inIx);
                if (c2 < 128) {
                    out.put(outIx2, (byte) c2);
                } else if (c2 < 2048) {
                    outIx = outIx2 + 1;
                    try {
                        out.put(outIx2, (byte) ((c2 >>> 6) | 192));
                        out.put(outIx, (byte) ((c2 & 63) | 128));
                        outIx2 = outIx;
                    } catch (IndexOutOfBoundsException e2) {
                        outIx2 = outIx;
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
                    }
                } else if (c2 < 55296 || 57343 < c2) {
                    outIx = outIx2 + 1;
                    out.put(outIx2, (byte) ((c2 >>> 12) | 224));
                    outIx2 = outIx + 1;
                    out.put(outIx, (byte) (((c2 >>> 6) & 63) | 128));
                    out.put(outIx2, (byte) ((c2 & 63) | 128));
                } else {
                    if (inIx + 1 != inLength) {
                        inIx++;
                        char charAt2 = in.charAt(inIx);
                        char low = charAt2;
                        if (Character.isSurrogatePair(c2, charAt2)) {
                            int codePoint = Character.toCodePoint(c2, low);
                            int outIx3 = outIx2 + 1;
                            try {
                                out.put(outIx2, (byte) ((codePoint >>> 18) | 240));
                                outIx2 = outIx3 + 1;
                                out.put(outIx3, (byte) (((codePoint >>> 12) & 63) | 128));
                                outIx3 = outIx2 + 1;
                                out.put(outIx2, (byte) (((codePoint >>> 6) & 63) | 128));
                                out.put(outIx3, (byte) ((codePoint & 63) | 128));
                                outIx2 = outIx3;
                            } catch (IndexOutOfBoundsException e3) {
                                outIx2 = outIx3;
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + (out.position() + Math.max(inIx, (outIx2 - out.position()) + 1)));
                            }
                        }
                    }
                    throw new UnpairedSurrogateException(inIx, inLength);
                }
                inIx++;
                outIx2++;
            }
            out.position(outIx2);
        }
    }

    static final class SafeProcessor extends Processor {
        SafeProcessor() {
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v8, types: [byte, int] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v10, types: [byte, int] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v4, types: [byte, int] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8(int r8, byte[] r9, int r10, int r11) {
            /*
                r7 = this;
                if (r8 == 0) goto L_0x0084
                if (r10 < r11) goto L_0x0005
                return r8
            L_0x0005:
                byte r0 = (byte) r8
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L_0x0019
                r1 = -62
                if (r0 < r1) goto L_0x0018
                int r1 = r10 + 1
                byte r10 = r9[r10]
                if (r10 <= r3) goto L_0x0085
                r10 = r1
            L_0x0018:
                return r2
            L_0x0019:
                r4 = -16
                if (r0 >= r4) goto L_0x0047
                int r4 = r8 >> 8
                int r4 = ~r4
                byte r4 = (byte) r4
                if (r4 != 0) goto L_0x002f
                int r5 = r10 + 1
                byte r4 = r9[r10]
                if (r5 < r11) goto L_0x002e
                int r10 = com.android.framework.protobuf.Utf8.incompleteStateFor(r0, r4)
                return r10
            L_0x002e:
                r10 = r5
            L_0x002f:
                if (r4 > r3) goto L_0x0046
                r5 = -96
                if (r0 != r1) goto L_0x0037
                if (r4 < r5) goto L_0x0046
            L_0x0037:
                r1 = -19
                if (r0 != r1) goto L_0x003d
                if (r4 >= r5) goto L_0x0046
            L_0x003d:
                int r1 = r10 + 1
                byte r10 = r9[r10]
                if (r10 <= r3) goto L_0x0045
                r10 = r1
                goto L_0x0046
            L_0x0045:
                goto L_0x0085
            L_0x0046:
                return r2
            L_0x0047:
                int r1 = r8 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                r4 = 0
                if (r1 != 0) goto L_0x005b
                int r5 = r10 + 1
                byte r1 = r9[r10]
                if (r5 < r11) goto L_0x0059
                int r10 = com.android.framework.protobuf.Utf8.incompleteStateFor(r0, r1)
                return r10
            L_0x0059:
                r10 = r5
                goto L_0x005e
            L_0x005b:
                int r5 = r8 >> 16
                byte r4 = (byte) r5
            L_0x005e:
                if (r4 != 0) goto L_0x006c
                int r5 = r10 + 1
                byte r4 = r9[r10]
                if (r5 < r11) goto L_0x006b
                int r10 = com.android.framework.protobuf.Utf8.incompleteStateFor((int) r0, (int) r1, (int) r4)
                return r10
            L_0x006b:
                r10 = r5
            L_0x006c:
                if (r1 > r3) goto L_0x0083
                int r5 = r0 << 28
                int r6 = r1 + 112
                int r5 = r5 + r6
                int r5 = r5 >> 30
                if (r5 != 0) goto L_0x0083
                if (r4 > r3) goto L_0x0083
                int r5 = r10 + 1
                byte r10 = r9[r10]
                if (r10 <= r3) goto L_0x0081
                r10 = r5
                goto L_0x0083
            L_0x0081:
                r1 = r5
                goto L_0x0085
            L_0x0083:
                return r2
            L_0x0084:
                r1 = r10
            L_0x0085:
                int r10 = partialIsValidUtf8(r9, r1, r11)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.Utf8.SafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* access modifiers changed from: package-private */
        public int partialIsValidUtf8Direct(int state, ByteBuffer buffer, int index, int limit) {
            return partialIsValidUtf8Default(state, buffer, index, limit);
        }

        /* access modifiers changed from: package-private */
        public int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            int j;
            int utf16Length = in.length();
            int j2 = offset;
            int i = 0;
            int limit = offset + length;
            while (i < utf16Length && i + j2 < limit) {
                char charAt = in.charAt(i);
                char c = charAt;
                if (charAt >= 128) {
                    break;
                }
                out[j2 + i] = (byte) c;
                i++;
            }
            if (i == utf16Length) {
                return j2 + utf16Length;
            }
            int j3 = j2 + i;
            while (i < utf16Length) {
                char c2 = in.charAt(i);
                if (c2 < 128 && j3 < limit) {
                    j = j3 + 1;
                    out[j3] = (byte) c2;
                } else if (c2 < 2048 && j3 <= limit - 2) {
                    int j4 = j3 + 1;
                    out[j3] = (byte) ((c2 >>> 6) | 960);
                    j3 = j4 + 1;
                    out[j4] = (byte) ((c2 & '?') | 128);
                    i++;
                } else if ((c2 < 55296 || 57343 < c2) && j3 <= limit - 3) {
                    int j5 = j3 + 1;
                    out[j3] = (byte) ((c2 >>> 12) | 480);
                    int j6 = j5 + 1;
                    out[j5] = (byte) (((c2 >>> 6) & 63) | 128);
                    j = j6 + 1;
                    out[j6] = (byte) ((c2 & '?') | 128);
                } else if (j3 <= limit - 4) {
                    if (i + 1 != in.length()) {
                        i++;
                        char charAt2 = in.charAt(i);
                        char low = charAt2;
                        if (Character.isSurrogatePair(c2, charAt2)) {
                            int codePoint = Character.toCodePoint(c2, low);
                            int j7 = j3 + 1;
                            out[j3] = (byte) ((codePoint >>> 18) | 240);
                            int j8 = j7 + 1;
                            out[j7] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int j9 = j8 + 1;
                            out[j8] = (byte) (((codePoint >>> 6) & 63) | 128);
                            j3 = j9 + 1;
                            out[j9] = (byte) ((codePoint & 63) | 128);
                            i++;
                        }
                    }
                    throw new UnpairedSurrogateException(i - 1, utf16Length);
                } else if (55296 > c2 || c2 > 57343 || (i + 1 != in.length() && Character.isSurrogatePair(c2, in.charAt(i + 1)))) {
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + c2 + " at index " + j3);
                } else {
                    throw new UnpairedSurrogateException(i, utf16Length);
                }
                j3 = j;
                i++;
            }
            return j3;
        }

        /* access modifiers changed from: package-private */
        public void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
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
                byte index3 = bytes[index];
                int byte1 = index3;
                if (index3 >= 0) {
                    index = index2;
                } else if (byte1 < -32) {
                    if (index2 >= limit) {
                        return byte1;
                    }
                    if (byte1 >= -62) {
                        index = index2 + 1;
                        if (bytes[index2] > -65) {
                        }
                    }
                    return -1;
                } else if (byte1 < -16) {
                    if (index2 >= limit - 1) {
                        return Utf8.incompleteStateFor(bytes, index2, limit);
                    }
                    int index4 = index2 + 1;
                    byte index5 = bytes[index2];
                    int byte2 = index5;
                    if (index5 > -65 || ((byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96))) {
                    } else {
                        index = index4 + 1;
                        if (bytes[index4] > -65) {
                        }
                    }
                    return -1;
                } else if (index2 >= limit - 2) {
                    return Utf8.incompleteStateFor(bytes, index2, limit);
                } else {
                    int index6 = index2 + 1;
                    byte index7 = bytes[index2];
                    int byte22 = index7;
                    if (index7 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0) {
                        int index8 = index6 + 1;
                        if (bytes[index6] <= -65) {
                            index = index8 + 1;
                            if (bytes[index8] > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

    static final class UnsafeProcessor extends Processor {
        private static final int ARRAY_BASE_OFFSET = byteArrayBaseOffset();
        private static final boolean AVAILABLE = (BUFFER_ADDRESS_OFFSET != -1 && ARRAY_BASE_OFFSET % 8 == 0);
        private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(field(Buffer.class, "address"));
        private static final Unsafe UNSAFE = getUnsafe();

        UnsafeProcessor() {
        }

        static boolean isAvailable() {
            return AVAILABLE;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
            if (UNSAFE.getByte(r1, r2) > -65) goto L_0x0036;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0068, code lost:
            if (UNSAFE.getByte(r1, r2) > -65) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b3, code lost:
            if (UNSAFE.getByte(r1, r2) > -65) goto L_0x00b7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8(int r17, byte[] r18, int r19, int r20) {
            /*
                r16 = this;
                r0 = r17
                r1 = r18
                r2 = r19 | r20
                int r3 = r1.length
                int r3 = r3 - r20
                r2 = r2 | r3
                if (r2 < 0) goto L_0x00c1
                int r2 = ARRAY_BASE_OFFSET
                int r2 = r2 + r19
                long r2 = (long) r2
                int r4 = ARRAY_BASE_OFFSET
                int r4 = r4 + r20
                long r4 = (long) r4
                if (r0 == 0) goto L_0x00b8
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 < 0) goto L_0x001d
                return r0
            L_0x001d:
                byte r6 = (byte) r0
                r7 = -32
                r8 = -1
                r9 = -65
                r10 = 1
                if (r6 >= r7) goto L_0x0037
                r7 = -62
                if (r6 < r7) goto L_0x0035
                sun.misc.Unsafe r7 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r7.getByte(r1, r2)
                if (r2 <= r9) goto L_0x00b9
                goto L_0x0036
            L_0x0035:
                r10 = r2
            L_0x0036:
                return r8
            L_0x0037:
                r12 = -16
                if (r6 >= r12) goto L_0x006e
                int r12 = r0 >> 8
                int r12 = ~r12
                byte r12 = (byte) r12
                if (r12 != 0) goto L_0x0053
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r12 = r13.getByte(r1, r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x0052
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor(r6, r12)
                return r2
            L_0x0052:
                r2 = r14
            L_0x0053:
                if (r12 > r9) goto L_0x006c
                r13 = -96
                if (r6 != r7) goto L_0x005b
                if (r12 < r13) goto L_0x006c
            L_0x005b:
                r7 = -19
                if (r6 != r7) goto L_0x0061
                if (r12 >= r13) goto L_0x006c
            L_0x0061:
                sun.misc.Unsafe r7 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r7.getByte(r1, r2)
                if (r2 <= r9) goto L_0x006b
                goto L_0x006d
            L_0x006b:
                goto L_0x00b9
            L_0x006c:
                r10 = r2
            L_0x006d:
                return r8
            L_0x006e:
                int r7 = r0 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                r12 = 0
                if (r7 != 0) goto L_0x0088
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r7 = r13.getByte(r1, r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x0086
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor(r6, r7)
                return r2
            L_0x0086:
                r2 = r14
                goto L_0x008b
            L_0x0088:
                int r13 = r0 >> 16
                byte r12 = (byte) r13
            L_0x008b:
                if (r12 != 0) goto L_0x009f
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r12 = r13.getByte(r1, r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x009e
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor((int) r6, (int) r7, (int) r12)
                return r2
            L_0x009e:
                r2 = r14
            L_0x009f:
                if (r7 > r9) goto L_0x00b6
                int r13 = r6 << 28
                int r14 = r7 + 112
                int r13 = r13 + r14
                int r13 = r13 >> 30
                if (r13 != 0) goto L_0x00b6
                if (r12 > r9) goto L_0x00b6
                sun.misc.Unsafe r13 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r13.getByte(r1, r2)
                if (r2 <= r9) goto L_0x00b9
                goto L_0x00b7
            L_0x00b6:
                r10 = r2
            L_0x00b7:
                return r8
            L_0x00b8:
                r10 = r2
            L_0x00b9:
                long r2 = r4 - r10
                int r2 = (int) r2
                int r2 = partialIsValidUtf8(r1, r10, r2)
                return r2
            L_0x00c1:
                java.lang.ArrayIndexOutOfBoundsException r2 = new java.lang.ArrayIndexOutOfBoundsException
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r4 = 0
                int r5 = r1.length
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                r3[r4] = r5
                r4 = 1
                java.lang.Integer r5 = java.lang.Integer.valueOf(r19)
                r3[r4] = r5
                r4 = 2
                java.lang.Integer r5 = java.lang.Integer.valueOf(r20)
                r3[r4] = r5
                java.lang.String r4 = "Array length=%d, index=%d, limit=%d"
                java.lang.String r3 = java.lang.String.format(r4, r3)
                r2.<init>(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
            if (UNSAFE.getByte(r2) > -65) goto L_0x0039;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x006b, code lost:
            if (UNSAFE.getByte(r2) > -65) goto L_0x0070;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b6, code lost:
            if (UNSAFE.getByte(r2) > -65) goto L_0x00ba;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8Direct(int r17, java.nio.ByteBuffer r18, int r19, int r20) {
            /*
                r16 = this;
                r0 = r17
                r1 = r19
                r2 = r1 | r20
                int r3 = r18.limit()
                int r3 = r3 - r20
                r2 = r2 | r3
                if (r2 < 0) goto L_0x00c4
                long r2 = addressOffset(r18)
                long r4 = (long) r1
                long r2 = r2 + r4
                int r4 = r20 - r1
                long r4 = (long) r4
                long r4 = r4 + r2
                if (r0 == 0) goto L_0x00bb
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 < 0) goto L_0x0020
                return r0
            L_0x0020:
                byte r6 = (byte) r0
                r7 = -32
                r8 = -1
                r9 = -65
                r10 = 1
                if (r6 >= r7) goto L_0x003a
                r7 = -62
                if (r6 < r7) goto L_0x0038
                sun.misc.Unsafe r7 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r7.getByte(r2)
                if (r2 <= r9) goto L_0x00bc
                goto L_0x0039
            L_0x0038:
                r10 = r2
            L_0x0039:
                return r8
            L_0x003a:
                r12 = -16
                if (r6 >= r12) goto L_0x0071
                int r12 = r0 >> 8
                int r12 = ~r12
                byte r12 = (byte) r12
                if (r12 != 0) goto L_0x0056
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r12 = r13.getByte(r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x0055
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor(r6, r12)
                return r2
            L_0x0055:
                r2 = r14
            L_0x0056:
                if (r12 > r9) goto L_0x006f
                r13 = -96
                if (r6 != r7) goto L_0x005e
                if (r12 < r13) goto L_0x006f
            L_0x005e:
                r7 = -19
                if (r6 != r7) goto L_0x0064
                if (r12 >= r13) goto L_0x006f
            L_0x0064:
                sun.misc.Unsafe r7 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r7.getByte(r2)
                if (r2 <= r9) goto L_0x006e
                goto L_0x0070
            L_0x006e:
                goto L_0x00bc
            L_0x006f:
                r10 = r2
            L_0x0070:
                return r8
            L_0x0071:
                int r7 = r0 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                r12 = 0
                if (r7 != 0) goto L_0x008b
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r7 = r13.getByte(r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x0089
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor(r6, r7)
                return r2
            L_0x0089:
                r2 = r14
                goto L_0x008e
            L_0x008b:
                int r13 = r0 >> 16
                byte r12 = (byte) r13
            L_0x008e:
                if (r12 != 0) goto L_0x00a2
                sun.misc.Unsafe r13 = UNSAFE
                long r14 = r2 + r10
                byte r12 = r13.getByte(r2)
                int r2 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x00a1
                int r2 = com.android.framework.protobuf.Utf8.incompleteStateFor((int) r6, (int) r7, (int) r12)
                return r2
            L_0x00a1:
                r2 = r14
            L_0x00a2:
                if (r7 > r9) goto L_0x00b9
                int r13 = r6 << 28
                int r14 = r7 + 112
                int r13 = r13 + r14
                int r13 = r13 >> 30
                if (r13 != 0) goto L_0x00b9
                if (r12 > r9) goto L_0x00b9
                sun.misc.Unsafe r13 = UNSAFE
                long r10 = r10 + r2
                byte r2 = r13.getByte(r2)
                if (r2 <= r9) goto L_0x00bc
                goto L_0x00ba
            L_0x00b9:
                r10 = r2
            L_0x00ba:
                return r8
            L_0x00bb:
                r10 = r2
            L_0x00bc:
                long r2 = r4 - r10
                int r2 = (int) r2
                int r2 = partialIsValidUtf8(r10, r2)
                return r2
            L_0x00c4:
                java.lang.ArrayIndexOutOfBoundsException r2 = new java.lang.ArrayIndexOutOfBoundsException
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r4 = 0
                int r5 = r18.limit()
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                r3[r4] = r5
                r4 = 1
                java.lang.Integer r5 = java.lang.Integer.valueOf(r19)
                r3[r4] = r5
                r4 = 2
                java.lang.Integer r5 = java.lang.Integer.valueOf(r20)
                r3[r4] = r5
                java.lang.String r4 = "buffer limit=%d, index=%d, limit=%d"
                java.lang.String r3 = java.lang.String.format(r4, r3)
                r2.<init>(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8Direct(int, java.nio.ByteBuffer, int, int):int");
        }

        /* access modifiers changed from: package-private */
        public int encodeUtf8(CharSequence in, byte[] out, int offset, int length) {
            long outIx;
            char c;
            long j;
            long outIx2;
            char c2;
            CharSequence charSequence = in;
            byte[] bArr = out;
            int i = offset;
            int i2 = length;
            long outIx3 = (long) (ARRAY_BASE_OFFSET + i);
            long outLimit = ((long) i2) + outIx3;
            int inLimit = in.length();
            if (inLimit > i2 || bArr.length - i2 < i) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(inLimit - 1) + " at index " + (offset + length));
            }
            int inIx = 0;
            while (true) {
                c = 128;
                j = 1;
                if (inIx >= inLimit) {
                    break;
                }
                char charAt = charSequence.charAt(inIx);
                char c3 = charAt;
                if (charAt >= 128) {
                    break;
                }
                UNSAFE.putByte(bArr, outIx, (byte) c3);
                inIx++;
                outIx3 = 1 + outIx;
            }
            if (inIx == inLimit) {
                return (int) (outIx - ((long) ARRAY_BASE_OFFSET));
            }
            while (inIx < inLimit) {
                char c4 = charSequence.charAt(inIx);
                if (c4 >= c || outIx >= outLimit) {
                    if (c4 < 2048 && outIx <= outLimit - 2) {
                        long outIx4 = outIx + j;
                        UNSAFE.putByte(bArr, outIx, (byte) ((c4 >>> 6) | 960));
                        UNSAFE.putByte(bArr, outIx4, (byte) ((c4 & '?') | 128));
                        char c5 = c4;
                        outIx = outIx4 + 1;
                    } else if ((c4 < 55296 || 57343 < c4) && outIx <= outLimit - 3) {
                        long outIx5 = outIx + 1;
                        UNSAFE.putByte(bArr, outIx, (byte) ((c4 >>> 12) | 480));
                        long outIx6 = outIx5 + 1;
                        UNSAFE.putByte(bArr, outIx5, (byte) (((c4 >>> 6) & 63) | 128));
                        UNSAFE.putByte(bArr, outIx6, (byte) ((c4 & '?') | 128));
                        char c6 = c4;
                        outIx = outIx6 + 1;
                    } else if (outIx <= outLimit - 4) {
                        if (inIx + 1 != inLimit) {
                            inIx++;
                            char charAt2 = charSequence.charAt(inIx);
                            char low = charAt2;
                            if (Character.isSurrogatePair(c4, charAt2)) {
                                int codePoint = Character.toCodePoint(c4, low);
                                long outIx7 = outIx + 1;
                                UNSAFE.putByte(bArr, outIx, (byte) ((codePoint >>> 18) | 240));
                                long outIx8 = outIx7 + 1;
                                UNSAFE.putByte(bArr, outIx7, (byte) (((codePoint >>> 12) & 63) | 128));
                                char c7 = c4;
                                long outIx9 = outIx8 + 1;
                                c2 = 128;
                                UNSAFE.putByte(bArr, outIx8, (byte) (((codePoint >>> 6) & 63) | 128));
                                outIx2 = 1;
                                outIx = outIx9 + 1;
                                UNSAFE.putByte(bArr, outIx9, (byte) ((codePoint & 63) | 128));
                            }
                        }
                        throw new UnpairedSurrogateException(inIx - 1, inLimit);
                    } else {
                        char c8 = c4;
                        if (55296 > c8 || c8 > 57343 || (inIx + 1 != inLimit && Character.isSurrogatePair(c8, charSequence.charAt(inIx + 1)))) {
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + c8 + " at index " + outIx);
                        }
                        throw new UnpairedSurrogateException(inIx, inLimit);
                    }
                    c2 = 128;
                    outIx2 = 1;
                } else {
                    UNSAFE.putByte(bArr, outIx, (byte) c4);
                    char c9 = c4;
                    outIx += j;
                    c2 = 128;
                    outIx2 = j;
                }
                inIx++;
                c = c2;
                j = outIx2;
                int i3 = offset;
                int i4 = length;
            }
            return (int) (outIx - ((long) ARRAY_BASE_OFFSET));
        }

        /* access modifiers changed from: package-private */
        public void encodeUtf8Direct(CharSequence in, ByteBuffer out) {
            long outIx;
            char c;
            long j;
            long address;
            long address2;
            CharSequence charSequence = in;
            ByteBuffer byteBuffer = out;
            long address3 = addressOffset(out);
            long outIx2 = ((long) out.position()) + address3;
            long outLimit = ((long) out.limit()) + address3;
            int inLimit = in.length();
            if (((long) inLimit) <= outLimit - outIx2) {
                int inIx = 0;
                while (true) {
                    c = 128;
                    j = 1;
                    if (inIx >= inLimit) {
                        break;
                    }
                    char charAt = charSequence.charAt(inIx);
                    char c2 = charAt;
                    if (charAt >= 128) {
                        break;
                    }
                    UNSAFE.putByte(outIx, (byte) c2);
                    inIx++;
                    outIx2 = 1 + outIx;
                }
                if (inIx == inLimit) {
                    byteBuffer.position((int) (outIx - address3));
                    return;
                }
                while (inIx < inLimit) {
                    char c3 = charSequence.charAt(inIx);
                    if (c3 < c && outIx < outLimit) {
                        UNSAFE.putByte(outIx, (byte) c3);
                        address = address3;
                        outIx += j;
                    } else if (c3 >= 2048 || outIx > outLimit - 2) {
                        if (c3 >= 55296 && 57343 >= c3) {
                            address2 = address3;
                        } else if (outIx <= outLimit - 3) {
                            long outIx3 = outIx + 1;
                            UNSAFE.putByte(outIx, (byte) ((c3 >>> 12) | 480));
                            address = address3;
                            long outIx4 = outIx3 + 1;
                            UNSAFE.putByte(outIx3, (byte) (((c3 >>> 6) & 63) | 128));
                            UNSAFE.putByte(outIx4, (byte) ((c3 & '?') | 128));
                            outIx = outIx4 + 1;
                        } else {
                            address2 = address3;
                        }
                        if (outIx <= outLimit - 4) {
                            if (inIx + 1 != inLimit) {
                                inIx++;
                                char charAt2 = charSequence.charAt(inIx);
                                char low = charAt2;
                                if (Character.isSurrogatePair(c3, charAt2)) {
                                    int codePoint = Character.toCodePoint(c3, low);
                                    long outIx5 = outIx + 1;
                                    UNSAFE.putByte(outIx, (byte) ((codePoint >>> 18) | 240));
                                    long outIx6 = outIx5 + 1;
                                    c = 128;
                                    UNSAFE.putByte(outIx5, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long outIx7 = outIx6 + 1;
                                    UNSAFE.putByte(outIx6, (byte) (((codePoint >>> 6) & 63) | 128));
                                    outIx = outIx7 + 1;
                                    UNSAFE.putByte(outIx7, (byte) ((codePoint & 63) | 128));
                                    inIx++;
                                    address3 = address;
                                    ByteBuffer byteBuffer2 = out;
                                    j = 1;
                                }
                            }
                            throw new UnpairedSurrogateException(inIx - 1, inLimit);
                        } else if (55296 > c3 || c3 > 57343 || (inIx + 1 != inLimit && Character.isSurrogatePair(c3, charSequence.charAt(inIx + 1)))) {
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + c3 + " at index " + outIx);
                        } else {
                            throw new UnpairedSurrogateException(inIx, inLimit);
                        }
                    } else {
                        long outIx8 = outIx + j;
                        UNSAFE.putByte(outIx, (byte) ((c3 >>> 6) | 960));
                        UNSAFE.putByte(outIx8, (byte) ((c3 & '?') | 128));
                        address = address3;
                        outIx = outIx8 + 1;
                    }
                    c = 128;
                    inIx++;
                    address3 = address;
                    ByteBuffer byteBuffer22 = out;
                    j = 1;
                }
                out.position((int) (outIx - address3));
                return;
            }
            ByteBuffer byteBuffer3 = byteBuffer;
            throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(inLimit - 1) + " at index " + out.limit());
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bytes, long offset, int maxChars) {
            int remaining = maxChars;
            if (remaining < 16) {
                return 0;
            }
            int unaligned = ((int) offset) & 7;
            long offset2 = offset;
            int j = unaligned;
            while (j > 0) {
                long offset3 = 1 + offset2;
                if (UNSAFE.getByte(bytes, offset2) < 0) {
                    return unaligned - j;
                }
                j--;
                offset2 = offset3;
            }
            int remaining2 = remaining - unaligned;
            while (remaining2 >= 8 && (UNSAFE.getLong(bytes, offset2) & Utf8.ASCII_MASK_LONG) == 0) {
                offset2 += 8;
                remaining2 -= 8;
            }
            return maxChars - remaining2;
        }

        private static int unsafeEstimateConsecutiveAscii(long address, int maxChars) {
            int remaining = maxChars;
            if (remaining < 16) {
                return 0;
            }
            int unaligned = ((int) address) & 7;
            long address2 = address;
            int j = unaligned;
            while (j > 0) {
                long address3 = 1 + address2;
                if (UNSAFE.getByte(address2) < 0) {
                    return unaligned - j;
                }
                j--;
                address2 = address3;
            }
            int remaining2 = remaining - unaligned;
            while (remaining2 >= 8 && (UNSAFE.getLong(address2) & Utf8.ASCII_MASK_LONG) == 0) {
                address2 += 8;
                remaining2 -= 8;
            }
            return maxChars - remaining2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0073, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ab, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(byte[] r11, long r12, int r14) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r11, r12, r14)
                int r14 = r14 - r0
                long r1 = (long) r0
                long r12 = r12 + r1
            L_0x0007:
                r1 = 0
                r2 = r12
                r12 = r1
            L_0x000a:
                r4 = 1
                if (r14 <= 0) goto L_0x001e
                sun.misc.Unsafe r13 = UNSAFE
                long r6 = r2 + r4
                byte r13 = r13.getByte(r11, r2)
                r12 = r13
                if (r13 < 0) goto L_0x001d
                int r14 = r14 + -1
                r2 = r6
                goto L_0x000a
            L_0x001d:
                r2 = r6
            L_0x001e:
                if (r14 != 0) goto L_0x0021
                return r1
            L_0x0021:
                int r14 = r14 + -1
                r13 = -32
                r1 = -65
                r6 = -1
                if (r12 >= r13) goto L_0x0042
                if (r14 != 0) goto L_0x002d
                return r12
            L_0x002d:
                int r14 = r14 + -1
                r13 = -62
                if (r12 < r13) goto L_0x0041
                sun.misc.Unsafe r13 = UNSAFE
                long r4 = r4 + r2
                byte r13 = r13.getByte(r11, r2)
                if (r13 <= r1) goto L_0x003e
                r2 = r4
                goto L_0x0041
            L_0x003e:
                r12 = r4
                goto L_0x00a8
            L_0x0041:
                return r6
            L_0x0042:
                r7 = -16
                if (r12 >= r7) goto L_0x0074
                r7 = 2
                if (r14 >= r7) goto L_0x004e
                int r13 = unsafeIncompleteStateFor(r11, r12, r2, r14)
                return r13
            L_0x004e:
                int r14 = r14 + -2
                sun.misc.Unsafe r7 = UNSAFE
                long r8 = r2 + r4
                byte r2 = r7.getByte(r11, r2)
                r3 = r2
                if (r2 > r1) goto L_0x0072
                r2 = -96
                if (r12 != r13) goto L_0x0061
                if (r3 < r2) goto L_0x0072
            L_0x0061:
                r13 = -19
                if (r12 != r13) goto L_0x0067
                if (r3 >= r2) goto L_0x0072
            L_0x0067:
                sun.misc.Unsafe r13 = UNSAFE
                long r4 = r4 + r8
                byte r13 = r13.getByte(r11, r8)
                if (r13 <= r1) goto L_0x0071
                goto L_0x0073
            L_0x0071:
                goto L_0x003e
            L_0x0072:
                r4 = r8
            L_0x0073:
                return r6
            L_0x0074:
                r13 = 3
                if (r14 >= r13) goto L_0x007c
                int r13 = unsafeIncompleteStateFor(r11, r12, r2, r14)
                return r13
            L_0x007c:
                int r14 = r14 + -3
                sun.misc.Unsafe r13 = UNSAFE
                long r7 = r2 + r4
                byte r13 = r13.getByte(r11, r2)
                r2 = r13
                if (r13 > r1) goto L_0x00ab
                int r13 = r12 << 28
                int r3 = r2 + 112
                int r13 = r13 + r3
                int r13 = r13 >> 30
                if (r13 != 0) goto L_0x00ab
                sun.misc.Unsafe r13 = UNSAFE
                long r9 = r7 + r4
                byte r13 = r13.getByte(r11, r7)
                if (r13 > r1) goto L_0x00aa
                sun.misc.Unsafe r13 = UNSAFE
                long r7 = r9 + r4
                byte r13 = r13.getByte(r11, r9)
                if (r13 <= r1) goto L_0x00a7
                goto L_0x00ab
            L_0x00a7:
                r12 = r7
            L_0x00a8:
                goto L_0x0007
            L_0x00aa:
                r7 = r9
            L_0x00ab:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(byte[], long, int):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ac, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(long r11, int r13) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r11, r13)
                long r1 = (long) r0
                long r11 = r11 + r1
                int r13 = r13 - r0
            L_0x0007:
                r1 = 0
                r2 = r11
                r11 = r1
            L_0x000a:
                r4 = 1
                if (r13 <= 0) goto L_0x001e
                sun.misc.Unsafe r12 = UNSAFE
                long r6 = r2 + r4
                byte r12 = r12.getByte(r2)
                r11 = r12
                if (r12 < 0) goto L_0x001d
                int r13 = r13 + -1
                r2 = r6
                goto L_0x000a
            L_0x001d:
                r2 = r6
            L_0x001e:
                if (r13 != 0) goto L_0x0021
                return r1
            L_0x0021:
                int r13 = r13 + -1
                r12 = -32
                r1 = -65
                r6 = -1
                if (r11 >= r12) goto L_0x0042
                if (r13 != 0) goto L_0x002d
                return r11
            L_0x002d:
                int r13 = r13 + -1
                r12 = -62
                if (r11 < r12) goto L_0x0041
                sun.misc.Unsafe r12 = UNSAFE
                long r4 = r4 + r2
                byte r12 = r12.getByte(r2)
                if (r12 <= r1) goto L_0x003e
                r2 = r4
                goto L_0x0041
            L_0x003e:
                r11 = r4
                goto L_0x00a9
            L_0x0041:
                return r6
            L_0x0042:
                r7 = -16
                if (r11 >= r7) goto L_0x0076
                r7 = 2
                if (r13 >= r7) goto L_0x004e
                int r12 = unsafeIncompleteStateFor(r2, r11, r13)
                return r12
            L_0x004e:
                int r13 = r13 + -2
                sun.misc.Unsafe r7 = UNSAFE
                long r8 = r2 + r4
                byte r2 = r7.getByte(r2)
                if (r2 > r1) goto L_0x0074
                r3 = -96
                if (r11 != r12) goto L_0x0060
                if (r2 < r3) goto L_0x0074
            L_0x0060:
                r12 = -19
                if (r11 != r12) goto L_0x0066
                if (r2 >= r3) goto L_0x0074
            L_0x0066:
                sun.misc.Unsafe r12 = UNSAFE
                long r3 = r8 + r4
                byte r12 = r12.getByte(r8)
                if (r12 <= r1) goto L_0x0071
                goto L_0x0075
            L_0x0071:
                r11 = r3
                goto L_0x00a9
            L_0x0074:
                r3 = r8
            L_0x0075:
                return r6
            L_0x0076:
                r12 = 3
                if (r13 >= r12) goto L_0x007e
                int r12 = unsafeIncompleteStateFor(r2, r11, r13)
                return r12
            L_0x007e:
                int r13 = r13 + -3
                sun.misc.Unsafe r12 = UNSAFE
                long r7 = r2 + r4
                byte r12 = r12.getByte(r2)
                if (r12 > r1) goto L_0x00ac
                int r2 = r11 << 28
                int r3 = r12 + 112
                int r2 = r2 + r3
                int r2 = r2 >> 30
                if (r2 != 0) goto L_0x00ac
                sun.misc.Unsafe r2 = UNSAFE
                long r9 = r7 + r4
                byte r2 = r2.getByte(r7)
                if (r2 > r1) goto L_0x00ab
                sun.misc.Unsafe r2 = UNSAFE
                long r7 = r9 + r4
                byte r2 = r2.getByte(r9)
                if (r2 <= r1) goto L_0x00a8
                goto L_0x00ac
            L_0x00a8:
                r11 = r7
            L_0x00a9:
                goto L_0x0007
            L_0x00ab:
                r7 = r9
            L_0x00ac:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(long, int):int");
        }

        private static int unsafeIncompleteStateFor(byte[] bytes, int byte1, long offset, int remaining) {
            switch (remaining) {
                case 0:
                    return Utf8.incompleteStateFor(byte1);
                case 1:
                    return Utf8.incompleteStateFor(byte1, UNSAFE.getByte(bytes, offset));
                case 2:
                    return Utf8.incompleteStateFor(byte1, (int) UNSAFE.getByte(bytes, offset), (int) UNSAFE.getByte(bytes, 1 + offset));
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
                    return Utf8.incompleteStateFor(byte1, (int) UNSAFE.getByte(address), (int) UNSAFE.getByte(1 + address));
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
            Logger access$700 = Utf8.logger;
            Level level = Level.FINEST;
            Object[] objArr = new Object[3];
            objArr[0] = clazz.getName();
            objArr[1] = fieldName;
            objArr[2] = field != null ? "available" : "unavailable";
            access$700.log(level, "{0}.{1}: {2}", objArr);
            return field;
        }

        private static long fieldOffset(Field field) {
            if (field == null || UNSAFE == null) {
                return -1;
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
                unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                    public Unsafe run() throws Exception {
                        Class<Unsafe> k = Unsafe.class;
                        UnsafeProcessor.checkRequiredMethods(k);
                        for (Field f : k.getDeclaredFields()) {
                            f.setAccessible(true);
                            Object x = f.get((Object) null);
                            if (k.isInstance(x)) {
                                return k.cast(x);
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

        /* access modifiers changed from: private */
        public static void checkRequiredMethods(Class<Unsafe> clazz) throws NoSuchMethodException, SecurityException {
            clazz.getMethod("arrayBaseOffset", new Class[]{Class.class});
            clazz.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("objectFieldOffset", new Class[]{Field.class});
            clazz.getMethod("getByte", new Class[]{Long.TYPE});
            clazz.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            clazz.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            clazz.getMethod("getLong", new Class[]{Long.TYPE});
        }
    }

    private Utf8() {
    }
}
