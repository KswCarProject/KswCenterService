package android.util;

import android.annotation.UnsupportedAppUsage;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    /* loaded from: classes4.dex */
    static abstract class Coder {

        /* renamed from: op */
        public int f288op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
        }
    }

    public static byte[] decode(String str, int flags) {
        return decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[(len * 3) / 4]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.f288op == decoder.output.length) {
            return decoder.output;
        }
        byte[] temp = new byte[decoder.f288op];
        System.arraycopy(decoder.output, 0, temp, 0, decoder.f288op);
        return temp;
    }

    /* loaded from: classes4.dex */
    static class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int flags, byte[] output) {
            this.output = output;
            this.alphabet = (flags & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // android.util.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 3) / 4) + 10;
        }

        /* JADX WARN: Removed duplicated region for block: B:53:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00ef  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x00e5 A[SYNTHETIC] */
        @Override // android.util.Base64.Coder
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean process(byte[] input, int offset, int len, boolean finish) {
            if (this.state == 6) {
                return false;
            }
            int p = offset;
            int len2 = len + offset;
            int state = this.state;
            int value = this.value;
            int op = 0;
            byte[] output = this.output;
            int[] alphabet = this.alphabet;
            while (p < len2) {
                if (state == 0) {
                    while (p + 4 <= len2) {
                        int i = (alphabet[input[p] & 255] << 18) | (alphabet[input[p + 1] & 255] << 12) | (alphabet[input[p + 2] & 255] << 6) | alphabet[input[p + 3] & 255];
                        value = i;
                        if (i >= 0) {
                            output[op + 2] = (byte) value;
                            output[op + 1] = (byte) (value >> 8);
                            output[op] = (byte) (value >> 16);
                            op += 3;
                            p += 4;
                        } else if (p >= len2) {
                            if (finish) {
                                this.state = state;
                                this.value = value;
                                this.f288op = op;
                                return true;
                            }
                            switch (state) {
                                case 1:
                                    this.state = 6;
                                    return false;
                                case 2:
                                    output[op] = (byte) (value >> 4);
                                    op++;
                                    break;
                                case 3:
                                    int op2 = op + 1;
                                    output[op] = (byte) (value >> 10);
                                    op = op2 + 1;
                                    output[op2] = (byte) (value >> 2);
                                    break;
                                case 4:
                                    this.state = 6;
                                    return false;
                            }
                            this.state = state;
                            this.f288op = op;
                            return true;
                        }
                    }
                    if (p >= len2) {
                    }
                }
                int p2 = p + 1;
                int d = alphabet[input[p] & 255];
                switch (state) {
                    case 0:
                        if (d >= 0) {
                            value = d;
                            state++;
                            break;
                        } else if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 1:
                        if (d >= 0) {
                            value = (value << 6) | d;
                            state++;
                            break;
                        } else if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 2:
                        if (d >= 0) {
                            value = (value << 6) | d;
                            state++;
                            break;
                        } else if (d == -2) {
                            output[op] = (byte) (value >> 4);
                            state = 4;
                            op++;
                            break;
                        } else if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 3:
                        if (d >= 0) {
                            value = (value << 6) | d;
                            output[op + 2] = (byte) value;
                            output[op + 1] = (byte) (value >> 8);
                            output[op] = (byte) (value >> 16);
                            op += 3;
                            state = 0;
                            break;
                        } else if (d == -2) {
                            output[op + 1] = (byte) (value >> 2);
                            output[op] = (byte) (value >> 10);
                            op += 2;
                            state = 5;
                            break;
                        } else if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 4:
                        if (d == -2) {
                            state++;
                            break;
                        } else if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 5:
                        if (d == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                }
                p = p2;
            }
            if (finish) {
            }
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(encode(input, offset, len, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        Encoder encoder = new Encoder(flags, null);
        int output_len = (len / 3) * 4;
        if (encoder.do_padding) {
            if (len % 3 > 0) {
                output_len += 4;
            }
        } else {
            switch (len % 3) {
                case 1:
                    output_len += 2;
                    break;
                case 2:
                    output_len += 3;
                    break;
            }
        }
        if (encoder.do_newline && len > 0) {
            output_len += (((len - 1) / 57) + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[output_len];
        encoder.process(input, offset, len, true);
        return encoder.output;
    }

    /* loaded from: classes4.dex */
    static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        public Encoder(int flags, byte[] output) {
            this.output = output;
            this.do_padding = (flags & 1) == 0;
            this.do_newline = (flags & 2) == 0;
            this.do_cr = (flags & 4) != 0;
            this.alphabet = (flags & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        @Override // android.util.Base64.Coder
        public int maxOutputSize(int len) {
            return ((len * 8) / 5) + 10;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.util.Base64.Coder
        public boolean process(byte[] input, int offset, int len, boolean finish) {
            int p;
            int op;
            int p2;
            int p3;
            int p4;
            int p5;
            int op2;
            byte[] alphabet = this.alphabet;
            byte[] output = this.output;
            int op3 = 0;
            int count = this.count;
            int len2 = len + offset;
            int v = -1;
            switch (this.tailLen) {
                case 0:
                default:
                    p = offset;
                    break;
                case 1:
                    if (offset + 2 <= len2) {
                        int p6 = offset + 1;
                        int p7 = input[offset];
                        v = ((p7 & 255) << 8) | ((this.tail[0] & 255) << 16) | (input[p6] & 255);
                        this.tailLen = 0;
                        p = p6 + 1;
                        break;
                    }
                    p = offset;
                    break;
                case 2:
                    if (offset + 1 <= len2) {
                        int i = ((this.tail[0] & 255) << 16) | ((this.tail[1] & 255) << 8);
                        p = offset + 1;
                        int p8 = input[offset];
                        v = i | (p8 & 255);
                        this.tailLen = 0;
                        break;
                    }
                    p = offset;
                    break;
            }
            if (v != -1) {
                int op4 = 0 + 1;
                output[0] = alphabet[(v >> 18) & 63];
                int op5 = op4 + 1;
                output[op4] = alphabet[(v >> 12) & 63];
                int op6 = op5 + 1;
                output[op5] = alphabet[(v >> 6) & 63];
                op3 = op6 + 1;
                output[op6] = alphabet[v & 63];
                count--;
                if (count == 0) {
                    if (this.do_cr) {
                        output[op3] = 13;
                        op3++;
                    }
                    int op7 = op3 + 1;
                    output[op3] = 10;
                    count = 19;
                    op3 = op7;
                }
            }
            while (p + 3 <= len2) {
                int v2 = ((input[p] & 255) << 16) | ((input[p + 1] & 255) << 8) | (input[p + 2] & 255);
                output[op3] = alphabet[(v2 >> 18) & 63];
                output[op3 + 1] = alphabet[(v2 >> 12) & 63];
                output[op3 + 2] = alphabet[(v2 >> 6) & 63];
                output[op3 + 3] = alphabet[v2 & 63];
                p += 3;
                op3 += 4;
                count--;
                if (count == 0) {
                    if (this.do_cr) {
                        output[op3] = 13;
                        op3++;
                    }
                    int op8 = op3 + 1;
                    output[op3] = 10;
                    count = 19;
                    op3 = op8;
                }
            }
            if (finish) {
                if (p - this.tailLen == len2 - 1) {
                    int t = 0;
                    if (this.tailLen > 0) {
                        int t2 = 0 + 1;
                        int t3 = this.tail[0];
                        p2 = p;
                        p5 = t3;
                        t = t2;
                    } else {
                        p2 = p + 1;
                        p5 = input[p];
                    }
                    int v3 = (p5 & 255) << 4;
                    this.tailLen -= t;
                    int op9 = op3 + 1;
                    output[op3] = alphabet[(v3 >> 6) & 63];
                    op3 = op9 + 1;
                    output[op9] = alphabet[v3 & 63];
                    if (this.do_padding) {
                        int op10 = op3 + 1;
                        output[op3] = 61;
                        op3 = op10 + 1;
                        output[op10] = 61;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            op2 = op3 + 1;
                            output[op3] = 13;
                        } else {
                            op2 = op3;
                        }
                        op3 = op2 + 1;
                        output[op2] = 10;
                    }
                } else if (p - this.tailLen == len2 - 2) {
                    int t4 = 0;
                    if (this.tailLen > 1) {
                        int t5 = 0 + 1;
                        int t6 = this.tail[0];
                        p2 = p;
                        p3 = t6;
                        t4 = t5;
                    } else {
                        p2 = p + 1;
                        p3 = input[p];
                    }
                    int i2 = (p3 & 255) << 10;
                    if (this.tailLen > 0) {
                        p4 = this.tail[t4];
                        t4++;
                    } else {
                        int p9 = p2 + 1;
                        int p10 = input[p2];
                        p2 = p9;
                        p4 = p10;
                    }
                    int v4 = i2 | ((p4 & 255) << 2);
                    this.tailLen -= t4;
                    int op11 = op3 + 1;
                    output[op3] = alphabet[(v4 >> 12) & 63];
                    int op12 = op11 + 1;
                    output[op11] = alphabet[(v4 >> 6) & 63];
                    int op13 = op12 + 1;
                    output[op12] = alphabet[v4 & 63];
                    if (this.do_padding) {
                        output[op13] = 61;
                        op13++;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            output[op13] = 13;
                            op13++;
                        }
                        output[op13] = 10;
                        op13++;
                    }
                    op3 = op13;
                } else if (this.do_newline && op3 > 0 && count != 19) {
                    if (this.do_cr) {
                        op = op3 + 1;
                        output[op3] = 13;
                    } else {
                        op = op3;
                    }
                    op3 = op + 1;
                    output[op] = 10;
                }
            } else if (p == len2 - 1) {
                byte[] bArr = this.tail;
                int i3 = this.tailLen;
                this.tailLen = i3 + 1;
                bArr[i3] = input[p];
            } else if (p == len2 - 2) {
                byte[] bArr2 = this.tail;
                int i4 = this.tailLen;
                this.tailLen = i4 + 1;
                bArr2[i4] = input[p];
                byte[] bArr3 = this.tail;
                int i5 = this.tailLen;
                this.tailLen = i5 + 1;
                bArr3[i5] = input[p + 1];
            }
            this.f288op = op3;
            this.count = count;
            return true;
        }
    }

    @UnsupportedAppUsage
    private Base64() {
    }
}
