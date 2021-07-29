package com.ibm.icu.text;

public final class UnicodeDecompressor implements SCSU {
    private static final int BUFSIZE = 3;
    private byte[] fBuffer = new byte[3];
    private int fBufferLength = 0;
    private int fCurrentWindow = 0;
    private int fMode = 0;
    private int[] fOffsets = new int[8];

    public UnicodeDecompressor() {
        reset();
    }

    public static String decompress(byte[] buffer) {
        return new String(decompress(buffer, 0, buffer.length));
    }

    public static char[] decompress(byte[] buffer, int start, int limit) {
        UnicodeDecompressor comp = new UnicodeDecompressor();
        int len = Math.max(2, (limit - start) * 2);
        char[] temp = new char[len];
        int charCount = comp.decompress(buffer, start, limit, (int[]) null, temp, 0, len);
        char[] result = new char[charCount];
        System.arraycopy(temp, 0, result, 0, charCount);
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x005b, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x005b, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x005b, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006a, code lost:
        if (r0 >= r11) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        if (r1 >= r14) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        r2 = r0 + 1;
        r0 = r9[r0] & 255;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0074, code lost:
        switch(r0) {
            case 224: goto L_0x0125;
            case 225: goto L_0x0125;
            case 226: goto L_0x0125;
            case 227: goto L_0x0125;
            case 228: goto L_0x0125;
            case 229: goto L_0x0125;
            case 230: goto L_0x0125;
            case 231: goto L_0x0125;
            case 232: goto L_0x00f5;
            case 233: goto L_0x00f5;
            case 234: goto L_0x00f5;
            case 235: goto L_0x00f5;
            case 236: goto L_0x00f5;
            case 237: goto L_0x00f5;
            case 238: goto L_0x00f5;
            case 239: goto L_0x00f5;
            case 240: goto L_0x00c8;
            case 241: goto L_0x008b;
            default: goto L_0x0077;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0077, code lost:
        if (r2 < r11) goto L_0x012d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0079, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008d, code lost:
        if ((r2 + 1) < r11) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008f, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a1, code lost:
        r5 = r2 + 1;
        r0 = r9[r2] & 255;
        r8.fCurrentWindow = (r0 & 224) >> 5;
        r8.fOffsets[r8.fCurrentWindow] = (((r9[r5] & 255) | ((r0 & 31) << 8)) * 128) + 65536;
        r8.fMode = r13;
        r17 = r0;
        r0 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ca, code lost:
        if (r2 < (r11 - 1)) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cc, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00de, code lost:
        r5 = r2 + 1;
        r17 = r9[r2];
        r12[r1] = (char) ((r17 << 8) | (r9[r5] & 255));
        r1 = r1 + 1;
        r0 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00f5, code lost:
        if (r2 < r11) goto L_0x0109;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f7, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0109, code lost:
        r8.fCurrentWindow = r0 - 232;
        r8.fOffsets[r8.fCurrentWindow] = sOffsetTable[r9[r2] & 255];
        r8.fMode = r13;
        r17 = r0;
        r0 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0125, code lost:
        r8.fCurrentWindow = r0 - 224;
        r8.fMode = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x012d, code lost:
        r12[r1] = (char) ((r9[r2] & 255) | (r0 << 8));
        r17 = r0;
        r1 = r1 + 1;
        r0 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0142, code lost:
        if (r0 >= r11) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0144, code lost:
        if (r1 >= r14) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0146, code lost:
        r2 = r0 + 1;
        r0 = r9[r0] & 255;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x014c, code lost:
        switch(r0) {
            case 0: goto L_0x0291;
            case 1: goto L_0x0259;
            case 2: goto L_0x0259;
            case 3: goto L_0x0259;
            case 4: goto L_0x0259;
            case 5: goto L_0x0259;
            case 6: goto L_0x0259;
            case 7: goto L_0x0259;
            case 8: goto L_0x0259;
            case 9: goto L_0x0291;
            case 10: goto L_0x0291;
            case 11: goto L_0x021d;
            case 12: goto L_0x014f;
            case 13: goto L_0x0291;
            case 14: goto L_0x01ec;
            case 15: goto L_0x01e3;
            case 16: goto L_0x01da;
            case 17: goto L_0x01da;
            case 18: goto L_0x01da;
            case 19: goto L_0x01da;
            case 20: goto L_0x01da;
            case 21: goto L_0x01da;
            case 22: goto L_0x01da;
            case 23: goto L_0x01da;
            case 24: goto L_0x01ad;
            case 25: goto L_0x01ad;
            case 26: goto L_0x01ad;
            case 27: goto L_0x01ad;
            case 28: goto L_0x01ad;
            case 29: goto L_0x01ad;
            case 30: goto L_0x01ad;
            case 31: goto L_0x01ad;
            case 32: goto L_0x0291;
            case 33: goto L_0x0291;
            case 34: goto L_0x0291;
            case 35: goto L_0x0291;
            case 36: goto L_0x0291;
            case 37: goto L_0x0291;
            case 38: goto L_0x0291;
            case 39: goto L_0x0291;
            case 40: goto L_0x0291;
            case 41: goto L_0x0291;
            case 42: goto L_0x0291;
            case 43: goto L_0x0291;
            case 44: goto L_0x0291;
            case 45: goto L_0x0291;
            case 46: goto L_0x0291;
            case 47: goto L_0x0291;
            case 48: goto L_0x0291;
            case 49: goto L_0x0291;
            case 50: goto L_0x0291;
            case 51: goto L_0x0291;
            case 52: goto L_0x0291;
            case 53: goto L_0x0291;
            case 54: goto L_0x0291;
            case 55: goto L_0x0291;
            case 56: goto L_0x0291;
            case 57: goto L_0x0291;
            case 58: goto L_0x0291;
            case 59: goto L_0x0291;
            case 60: goto L_0x0291;
            case 61: goto L_0x0291;
            case 62: goto L_0x0291;
            case 63: goto L_0x0291;
            case 64: goto L_0x0291;
            case 65: goto L_0x0291;
            case 66: goto L_0x0291;
            case 67: goto L_0x0291;
            case 68: goto L_0x0291;
            case 69: goto L_0x0291;
            case 70: goto L_0x0291;
            case 71: goto L_0x0291;
            case 72: goto L_0x0291;
            case 73: goto L_0x0291;
            case 74: goto L_0x0291;
            case 75: goto L_0x0291;
            case 76: goto L_0x0291;
            case 77: goto L_0x0291;
            case 78: goto L_0x0291;
            case 79: goto L_0x0291;
            case 80: goto L_0x0291;
            case 81: goto L_0x0291;
            case 82: goto L_0x0291;
            case 83: goto L_0x0291;
            case 84: goto L_0x0291;
            case 85: goto L_0x0291;
            case 86: goto L_0x0291;
            case 87: goto L_0x0291;
            case 88: goto L_0x0291;
            case 89: goto L_0x0291;
            case 90: goto L_0x0291;
            case 91: goto L_0x0291;
            case 92: goto L_0x0291;
            case 93: goto L_0x0291;
            case 94: goto L_0x0291;
            case 95: goto L_0x0291;
            case 96: goto L_0x0291;
            case 97: goto L_0x0291;
            case 98: goto L_0x0291;
            case 99: goto L_0x0291;
            case 100: goto L_0x0291;
            case 101: goto L_0x0291;
            case 102: goto L_0x0291;
            case 103: goto L_0x0291;
            case 104: goto L_0x0291;
            case 105: goto L_0x0291;
            case 106: goto L_0x0291;
            case 107: goto L_0x0291;
            case 108: goto L_0x0291;
            case 109: goto L_0x0291;
            case 110: goto L_0x0291;
            case 111: goto L_0x0291;
            case 112: goto L_0x0291;
            case 113: goto L_0x0291;
            case 114: goto L_0x0291;
            case 115: goto L_0x0291;
            case 116: goto L_0x0291;
            case 117: goto L_0x0291;
            case 118: goto L_0x0291;
            case 119: goto L_0x0291;
            case 120: goto L_0x0291;
            case 121: goto L_0x0291;
            case 122: goto L_0x0291;
            case 123: goto L_0x0291;
            case 124: goto L_0x0291;
            case 125: goto L_0x0291;
            case 126: goto L_0x0291;
            case 127: goto L_0x0291;
            case 128: goto L_0x0152;
            case 129: goto L_0x0152;
            case 130: goto L_0x0152;
            case 131: goto L_0x0152;
            case 132: goto L_0x0152;
            case 133: goto L_0x0152;
            case 134: goto L_0x0152;
            case 135: goto L_0x0152;
            case 136: goto L_0x0152;
            case 137: goto L_0x0152;
            case 138: goto L_0x0152;
            case 139: goto L_0x0152;
            case 140: goto L_0x0152;
            case 141: goto L_0x0152;
            case 142: goto L_0x0152;
            case 143: goto L_0x0152;
            case 144: goto L_0x0152;
            case 145: goto L_0x0152;
            case 146: goto L_0x0152;
            case 147: goto L_0x0152;
            case 148: goto L_0x0152;
            case 149: goto L_0x0152;
            case 150: goto L_0x0152;
            case 151: goto L_0x0152;
            case 152: goto L_0x0152;
            case 153: goto L_0x0152;
            case 154: goto L_0x0152;
            case 155: goto L_0x0152;
            case 156: goto L_0x0152;
            case 157: goto L_0x0152;
            case 158: goto L_0x0152;
            case 159: goto L_0x0152;
            case 160: goto L_0x0152;
            case 161: goto L_0x0152;
            case 162: goto L_0x0152;
            case 163: goto L_0x0152;
            case 164: goto L_0x0152;
            case 165: goto L_0x0152;
            case 166: goto L_0x0152;
            case 167: goto L_0x0152;
            case 168: goto L_0x0152;
            case 169: goto L_0x0152;
            case 170: goto L_0x0152;
            case 171: goto L_0x0152;
            case 172: goto L_0x0152;
            case 173: goto L_0x0152;
            case 174: goto L_0x0152;
            case 175: goto L_0x0152;
            case 176: goto L_0x0152;
            case 177: goto L_0x0152;
            case 178: goto L_0x0152;
            case 179: goto L_0x0152;
            case 180: goto L_0x0152;
            case 181: goto L_0x0152;
            case 182: goto L_0x0152;
            case 183: goto L_0x0152;
            case 184: goto L_0x0152;
            case 185: goto L_0x0152;
            case 186: goto L_0x0152;
            case 187: goto L_0x0152;
            case 188: goto L_0x0152;
            case 189: goto L_0x0152;
            case 190: goto L_0x0152;
            case 191: goto L_0x0152;
            case 192: goto L_0x0152;
            case 193: goto L_0x0152;
            case 194: goto L_0x0152;
            case 195: goto L_0x0152;
            case 196: goto L_0x0152;
            case 197: goto L_0x0152;
            case 198: goto L_0x0152;
            case 199: goto L_0x0152;
            case 200: goto L_0x0152;
            case 201: goto L_0x0152;
            case 202: goto L_0x0152;
            case 203: goto L_0x0152;
            case 204: goto L_0x0152;
            case 205: goto L_0x0152;
            case 206: goto L_0x0152;
            case 207: goto L_0x0152;
            case 208: goto L_0x0152;
            case 209: goto L_0x0152;
            case 210: goto L_0x0152;
            case 211: goto L_0x0152;
            case 212: goto L_0x0152;
            case 213: goto L_0x0152;
            case 214: goto L_0x0152;
            case 215: goto L_0x0152;
            case 216: goto L_0x0152;
            case 217: goto L_0x0152;
            case 218: goto L_0x0152;
            case 219: goto L_0x0152;
            case 220: goto L_0x0152;
            case 221: goto L_0x0152;
            case 222: goto L_0x0152;
            case 223: goto L_0x0152;
            case 224: goto L_0x0152;
            case 225: goto L_0x0152;
            case 226: goto L_0x0152;
            case 227: goto L_0x0152;
            case 228: goto L_0x0152;
            case 229: goto L_0x0152;
            case 230: goto L_0x0152;
            case 231: goto L_0x0152;
            case 232: goto L_0x0152;
            case 233: goto L_0x0152;
            case 234: goto L_0x0152;
            case 235: goto L_0x0152;
            case 236: goto L_0x0152;
            case 237: goto L_0x0152;
            case 238: goto L_0x0152;
            case 239: goto L_0x0152;
            case 240: goto L_0x0152;
            case 241: goto L_0x0152;
            case 242: goto L_0x0152;
            case 243: goto L_0x0152;
            case 244: goto L_0x0152;
            case 245: goto L_0x0152;
            case 246: goto L_0x0152;
            case 247: goto L_0x0152;
            case 248: goto L_0x0152;
            case 249: goto L_0x0152;
            case 250: goto L_0x0152;
            case 251: goto L_0x0152;
            case 252: goto L_0x0152;
            case 253: goto L_0x0152;
            case 254: goto L_0x0152;
            case 255: goto L_0x0152;
            default: goto L_0x014f;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x014f, code lost:
        r6 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x015b, code lost:
        if (r8.fOffsets[r8.fCurrentWindow] > 65535) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x015d, code lost:
        r5 = r1 + 1;
        r12[r1] = (char) ((r8.fOffsets[r8.fCurrentWindow] + r0) - 128);
        r17 = r0;
        r0 = r2;
        r6 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016e, code lost:
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0173, code lost:
        if ((r1 + 1) < r14) goto L_0x0187;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0175, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0187, code lost:
        r5 = r8.fOffsets[r8.fCurrentWindow] - r3;
        r6 = r1 + 1;
        r12[r1] = (char) (55296 + (r5 >> 10));
        r1 = r6 + 1;
        r12[r6] = (char) (((r5 & 1023) + com.ibm.icu.text.UTF16.TRAIL_SURROGATE_MIN_VALUE) + (r0 & 127));
        r17 = r0;
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01ad, code lost:
        if (r2 < r11) goto L_0x01c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01af, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01c1, code lost:
        r8.fCurrentWindow = r0 - 24;
        r8.fOffsets[r8.fCurrentWindow] = sOffsetTable[r9[r2] & 255];
        r17 = r0;
        r0 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01da, code lost:
        r8.fCurrentWindow = r0 - 16;
        r6 = 65536;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01e3, code lost:
        r8.fMode = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01e7, code lost:
        r17 = r0;
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01ee, code lost:
        if ((r2 + 1) < r11) goto L_0x0202;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01f0, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0202, code lost:
        r3 = r2 + 1;
        r0 = r9[r2];
        r12[r1] = (char) ((r9[r3] & 255) | (r0 << 8));
        r17 = r0;
        r1 = r1 + 1;
        r0 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0219, code lost:
        r6 = 65536;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x021f, code lost:
        if ((r2 + 1) < r11) goto L_0x0233;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0221, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0233, code lost:
        r3 = r2 + 1;
        r0 = r9[r2] & 255;
        r8.fCurrentWindow = (r0 & 224) >> 5;
        r6 = 65536;
        r8.fOffsets[r8.fCurrentWindow] = (((r9[r3] & 255) | ((r0 & 31) << 8)) * 128) + 65536;
        r17 = r0;
        r0 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0259, code lost:
        r6 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x025a, code lost:
        if (r2 < r11) goto L_0x026d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x025c, code lost:
        r2 = r2 - 1;
        java.lang.System.arraycopy(r9, r2, r8.fBuffer, r13, r11 - r2);
        r8.fBufferLength = r11 - r2;
        r2 = r2 + r8.fBufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x026d, code lost:
        r3 = r2 + 1;
        r2 = r9[r2] & 255;
        r5 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0275, code lost:
        if (r2 < 0) goto L_0x0280;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0277, code lost:
        if (r2 >= 128) goto L_0x0280;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0279, code lost:
        r7 = sOffsets[r0 - 1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0280, code lost:
        r7 = r8.fOffsets[r0 - 1] - 128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0287, code lost:
        r12[r1] = (char) (r7 + r2);
        r17 = r0;
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0291, code lost:
        r6 = r3;
        r12[r1] = (char) r0;
        r17 = r0;
        r0 = r2;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x029d, code lost:
        r17 = r0;
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x02a0, code lost:
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x005b, code lost:
        continue;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r17v20, types: [int, byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int decompress(byte[] r20, int r21, int r22, int[] r23, char[] r24, int r25, int r26) {
        /*
            r19 = this;
            r8 = r19
            r9 = r20
            r10 = r21
            r11 = r22
            r12 = r24
            r14 = r26
            r15 = r21
            r16 = r25
            r17 = 0
            int r0 = r12.length
            r1 = 2
            if (r0 < r1) goto L_0x02b1
            int r0 = r14 - r25
            if (r0 < r1) goto L_0x02b1
            int r0 = r8.fBufferLength
            r7 = 0
            if (r0 <= 0) goto L_0x0057
            r0 = 0
            int r1 = r8.fBufferLength
            r2 = 3
            if (r1 == r2) goto L_0x003a
            byte[] r1 = r8.fBuffer
            int r1 = r1.length
            int r2 = r8.fBufferLength
            int r1 = r1 - r2
            int r0 = r11 - r10
            if (r0 >= r1) goto L_0x0032
            int r0 = r11 - r10
            goto L_0x0033
        L_0x0032:
            r0 = r1
        L_0x0033:
            byte[] r1 = r8.fBuffer
            int r2 = r8.fBufferLength
            java.lang.System.arraycopy(r9, r10, r1, r2, r0)
        L_0x003a:
            r18 = r0
            r8.fBufferLength = r7
            byte[] r1 = r8.fBuffer
            r2 = 0
            byte[] r0 = r8.fBuffer
            int r3 = r0.length
            r4 = 0
            r0 = r19
            r5 = r24
            r6 = r25
            r13 = r7
            r7 = r26
            int r0 = r0.decompress(r1, r2, r3, r4, r5, r6, r7)
            int r16 = r16 + r0
            int r15 = r15 + r18
            goto L_0x0058
        L_0x0057:
            r13 = r7
        L_0x0058:
            r0 = r15
            r1 = r16
        L_0x005b:
            if (r0 >= r11) goto L_0x02a5
            if (r1 >= r14) goto L_0x02a5
            int r2 = r8.fMode
            r3 = 65536(0x10000, float:9.18355E-41)
            r4 = 128(0x80, float:1.794E-43)
            switch(r2) {
                case 0: goto L_0x0142;
                case 1: goto L_0x006a;
                default: goto L_0x0068;
            }
        L_0x0068:
            goto L_0x02a3
        L_0x006a:
            if (r0 >= r11) goto L_0x02a3
            if (r1 >= r14) goto L_0x02a3
            int r2 = r0 + 1
            byte r0 = r9[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            switch(r0) {
                case 224: goto L_0x0125;
                case 225: goto L_0x0125;
                case 226: goto L_0x0125;
                case 227: goto L_0x0125;
                case 228: goto L_0x0125;
                case 229: goto L_0x0125;
                case 230: goto L_0x0125;
                case 231: goto L_0x0125;
                case 232: goto L_0x00f5;
                case 233: goto L_0x00f5;
                case 234: goto L_0x00f5;
                case 235: goto L_0x00f5;
                case 236: goto L_0x00f5;
                case 237: goto L_0x00f5;
                case 238: goto L_0x00f5;
                case 239: goto L_0x00f5;
                case 240: goto L_0x00c8;
                case 241: goto L_0x008b;
                default: goto L_0x0077;
            }
        L_0x0077:
            if (r2 < r11) goto L_0x012d
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x008b:
            int r5 = r2 + 1
            if (r5 < r11) goto L_0x00a1
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x00a1:
            int r5 = r2 + 1
            byte r2 = r9[r2]
            r0 = r2 & 255(0xff, float:3.57E-43)
            r2 = r0 & 224(0xe0, float:3.14E-43)
            int r2 = r2 >> 5
            r8.fCurrentWindow = r2
            int[] r2 = r8.fOffsets
            int r6 = r8.fCurrentWindow
            r7 = r0 & 31
            int r7 = r7 << 8
            int r15 = r5 + 1
            byte r5 = r9[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r5 = r5 | r7
            int r5 = r5 * r4
            int r5 = r5 + r3
            r2[r6] = r5
            r8.fMode = r13
            r17 = r0
            r0 = r15
            goto L_0x02a3
        L_0x00c8:
            int r5 = r11 + -1
            if (r2 < r5) goto L_0x00de
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x00de:
            int r5 = r2 + 1
            byte r17 = r9[r2]
            int r0 = r1 + 1
            int r2 = r17 << 8
            int r6 = r5 + 1
            byte r5 = r9[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r2 = r2 | r5
            char r2 = (char) r2
            r12[r1] = r2
            r1 = r0
            r0 = r6
            goto L_0x006a
        L_0x00f5:
            if (r2 < r11) goto L_0x0109
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x0109:
            int r3 = r0 + -232
            r8.fCurrentWindow = r3
            int[] r3 = r8.fOffsets
            int r4 = r8.fCurrentWindow
            int[] r5 = sOffsetTable
            int r6 = r2 + 1
            byte r2 = r9[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r2 = r5[r2]
            r3[r4] = r2
            r8.fMode = r13
            r17 = r0
            r0 = r6
            goto L_0x02a3
        L_0x0125:
            int r3 = r0 + -224
            r8.fCurrentWindow = r3
            r8.fMode = r13
            goto L_0x01e7
        L_0x012d:
            int r5 = r1 + 1
            int r6 = r0 << 8
            int r7 = r2 + 1
            byte r2 = r9[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r2 = r2 | r6
            char r2 = (char) r2
            r12[r1] = r2
            r17 = r0
            r1 = r5
            r0 = r7
            goto L_0x006a
        L_0x0142:
            if (r0 >= r11) goto L_0x02a3
            if (r1 >= r14) goto L_0x02a3
            int r2 = r0 + 1
            byte r0 = r9[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            switch(r0) {
                case 0: goto L_0x0291;
                case 1: goto L_0x0259;
                case 2: goto L_0x0259;
                case 3: goto L_0x0259;
                case 4: goto L_0x0259;
                case 5: goto L_0x0259;
                case 6: goto L_0x0259;
                case 7: goto L_0x0259;
                case 8: goto L_0x0259;
                case 9: goto L_0x0291;
                case 10: goto L_0x0291;
                case 11: goto L_0x021d;
                case 12: goto L_0x014f;
                case 13: goto L_0x0291;
                case 14: goto L_0x01ec;
                case 15: goto L_0x01e3;
                case 16: goto L_0x01da;
                case 17: goto L_0x01da;
                case 18: goto L_0x01da;
                case 19: goto L_0x01da;
                case 20: goto L_0x01da;
                case 21: goto L_0x01da;
                case 22: goto L_0x01da;
                case 23: goto L_0x01da;
                case 24: goto L_0x01ad;
                case 25: goto L_0x01ad;
                case 26: goto L_0x01ad;
                case 27: goto L_0x01ad;
                case 28: goto L_0x01ad;
                case 29: goto L_0x01ad;
                case 30: goto L_0x01ad;
                case 31: goto L_0x01ad;
                case 32: goto L_0x0291;
                case 33: goto L_0x0291;
                case 34: goto L_0x0291;
                case 35: goto L_0x0291;
                case 36: goto L_0x0291;
                case 37: goto L_0x0291;
                case 38: goto L_0x0291;
                case 39: goto L_0x0291;
                case 40: goto L_0x0291;
                case 41: goto L_0x0291;
                case 42: goto L_0x0291;
                case 43: goto L_0x0291;
                case 44: goto L_0x0291;
                case 45: goto L_0x0291;
                case 46: goto L_0x0291;
                case 47: goto L_0x0291;
                case 48: goto L_0x0291;
                case 49: goto L_0x0291;
                case 50: goto L_0x0291;
                case 51: goto L_0x0291;
                case 52: goto L_0x0291;
                case 53: goto L_0x0291;
                case 54: goto L_0x0291;
                case 55: goto L_0x0291;
                case 56: goto L_0x0291;
                case 57: goto L_0x0291;
                case 58: goto L_0x0291;
                case 59: goto L_0x0291;
                case 60: goto L_0x0291;
                case 61: goto L_0x0291;
                case 62: goto L_0x0291;
                case 63: goto L_0x0291;
                case 64: goto L_0x0291;
                case 65: goto L_0x0291;
                case 66: goto L_0x0291;
                case 67: goto L_0x0291;
                case 68: goto L_0x0291;
                case 69: goto L_0x0291;
                case 70: goto L_0x0291;
                case 71: goto L_0x0291;
                case 72: goto L_0x0291;
                case 73: goto L_0x0291;
                case 74: goto L_0x0291;
                case 75: goto L_0x0291;
                case 76: goto L_0x0291;
                case 77: goto L_0x0291;
                case 78: goto L_0x0291;
                case 79: goto L_0x0291;
                case 80: goto L_0x0291;
                case 81: goto L_0x0291;
                case 82: goto L_0x0291;
                case 83: goto L_0x0291;
                case 84: goto L_0x0291;
                case 85: goto L_0x0291;
                case 86: goto L_0x0291;
                case 87: goto L_0x0291;
                case 88: goto L_0x0291;
                case 89: goto L_0x0291;
                case 90: goto L_0x0291;
                case 91: goto L_0x0291;
                case 92: goto L_0x0291;
                case 93: goto L_0x0291;
                case 94: goto L_0x0291;
                case 95: goto L_0x0291;
                case 96: goto L_0x0291;
                case 97: goto L_0x0291;
                case 98: goto L_0x0291;
                case 99: goto L_0x0291;
                case 100: goto L_0x0291;
                case 101: goto L_0x0291;
                case 102: goto L_0x0291;
                case 103: goto L_0x0291;
                case 104: goto L_0x0291;
                case 105: goto L_0x0291;
                case 106: goto L_0x0291;
                case 107: goto L_0x0291;
                case 108: goto L_0x0291;
                case 109: goto L_0x0291;
                case 110: goto L_0x0291;
                case 111: goto L_0x0291;
                case 112: goto L_0x0291;
                case 113: goto L_0x0291;
                case 114: goto L_0x0291;
                case 115: goto L_0x0291;
                case 116: goto L_0x0291;
                case 117: goto L_0x0291;
                case 118: goto L_0x0291;
                case 119: goto L_0x0291;
                case 120: goto L_0x0291;
                case 121: goto L_0x0291;
                case 122: goto L_0x0291;
                case 123: goto L_0x0291;
                case 124: goto L_0x0291;
                case 125: goto L_0x0291;
                case 126: goto L_0x0291;
                case 127: goto L_0x0291;
                case 128: goto L_0x0152;
                case 129: goto L_0x0152;
                case 130: goto L_0x0152;
                case 131: goto L_0x0152;
                case 132: goto L_0x0152;
                case 133: goto L_0x0152;
                case 134: goto L_0x0152;
                case 135: goto L_0x0152;
                case 136: goto L_0x0152;
                case 137: goto L_0x0152;
                case 138: goto L_0x0152;
                case 139: goto L_0x0152;
                case 140: goto L_0x0152;
                case 141: goto L_0x0152;
                case 142: goto L_0x0152;
                case 143: goto L_0x0152;
                case 144: goto L_0x0152;
                case 145: goto L_0x0152;
                case 146: goto L_0x0152;
                case 147: goto L_0x0152;
                case 148: goto L_0x0152;
                case 149: goto L_0x0152;
                case 150: goto L_0x0152;
                case 151: goto L_0x0152;
                case 152: goto L_0x0152;
                case 153: goto L_0x0152;
                case 154: goto L_0x0152;
                case 155: goto L_0x0152;
                case 156: goto L_0x0152;
                case 157: goto L_0x0152;
                case 158: goto L_0x0152;
                case 159: goto L_0x0152;
                case 160: goto L_0x0152;
                case 161: goto L_0x0152;
                case 162: goto L_0x0152;
                case 163: goto L_0x0152;
                case 164: goto L_0x0152;
                case 165: goto L_0x0152;
                case 166: goto L_0x0152;
                case 167: goto L_0x0152;
                case 168: goto L_0x0152;
                case 169: goto L_0x0152;
                case 170: goto L_0x0152;
                case 171: goto L_0x0152;
                case 172: goto L_0x0152;
                case 173: goto L_0x0152;
                case 174: goto L_0x0152;
                case 175: goto L_0x0152;
                case 176: goto L_0x0152;
                case 177: goto L_0x0152;
                case 178: goto L_0x0152;
                case 179: goto L_0x0152;
                case 180: goto L_0x0152;
                case 181: goto L_0x0152;
                case 182: goto L_0x0152;
                case 183: goto L_0x0152;
                case 184: goto L_0x0152;
                case 185: goto L_0x0152;
                case 186: goto L_0x0152;
                case 187: goto L_0x0152;
                case 188: goto L_0x0152;
                case 189: goto L_0x0152;
                case 190: goto L_0x0152;
                case 191: goto L_0x0152;
                case 192: goto L_0x0152;
                case 193: goto L_0x0152;
                case 194: goto L_0x0152;
                case 195: goto L_0x0152;
                case 196: goto L_0x0152;
                case 197: goto L_0x0152;
                case 198: goto L_0x0152;
                case 199: goto L_0x0152;
                case 200: goto L_0x0152;
                case 201: goto L_0x0152;
                case 202: goto L_0x0152;
                case 203: goto L_0x0152;
                case 204: goto L_0x0152;
                case 205: goto L_0x0152;
                case 206: goto L_0x0152;
                case 207: goto L_0x0152;
                case 208: goto L_0x0152;
                case 209: goto L_0x0152;
                case 210: goto L_0x0152;
                case 211: goto L_0x0152;
                case 212: goto L_0x0152;
                case 213: goto L_0x0152;
                case 214: goto L_0x0152;
                case 215: goto L_0x0152;
                case 216: goto L_0x0152;
                case 217: goto L_0x0152;
                case 218: goto L_0x0152;
                case 219: goto L_0x0152;
                case 220: goto L_0x0152;
                case 221: goto L_0x0152;
                case 222: goto L_0x0152;
                case 223: goto L_0x0152;
                case 224: goto L_0x0152;
                case 225: goto L_0x0152;
                case 226: goto L_0x0152;
                case 227: goto L_0x0152;
                case 228: goto L_0x0152;
                case 229: goto L_0x0152;
                case 230: goto L_0x0152;
                case 231: goto L_0x0152;
                case 232: goto L_0x0152;
                case 233: goto L_0x0152;
                case 234: goto L_0x0152;
                case 235: goto L_0x0152;
                case 236: goto L_0x0152;
                case 237: goto L_0x0152;
                case 238: goto L_0x0152;
                case 239: goto L_0x0152;
                case 240: goto L_0x0152;
                case 241: goto L_0x0152;
                case 242: goto L_0x0152;
                case 243: goto L_0x0152;
                case 244: goto L_0x0152;
                case 245: goto L_0x0152;
                case 246: goto L_0x0152;
                case 247: goto L_0x0152;
                case 248: goto L_0x0152;
                case 249: goto L_0x0152;
                case 250: goto L_0x0152;
                case 251: goto L_0x0152;
                case 252: goto L_0x0152;
                case 253: goto L_0x0152;
                case 254: goto L_0x0152;
                case 255: goto L_0x0152;
                default: goto L_0x014f;
            }
        L_0x014f:
            r6 = r3
            goto L_0x029d
        L_0x0152:
            int[] r5 = r8.fOffsets
            int r6 = r8.fCurrentWindow
            r5 = r5[r6]
            r6 = 65535(0xffff, float:9.1834E-41)
            if (r5 > r6) goto L_0x0171
            int r5 = r1 + 1
            int[] r6 = r8.fOffsets
            int r7 = r8.fCurrentWindow
            r6 = r6[r7]
            int r6 = r6 + r0
            int r6 = r6 - r4
            char r6 = (char) r6
            r12[r1] = r6
            r17 = r0
            r0 = r2
            r6 = r3
        L_0x016e:
            r1 = r5
            goto L_0x02a0
        L_0x0171:
            int r5 = r1 + 1
            if (r5 < r14) goto L_0x0187
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x0187:
            int[] r5 = r8.fOffsets
            int r6 = r8.fCurrentWindow
            r5 = r5[r6]
            int r5 = r5 - r3
            int r6 = r1 + 1
            r7 = 55296(0xd800, float:7.7486E-41)
            int r15 = r5 >> 10
            int r7 = r7 + r15
            char r7 = (char) r7
            r12[r1] = r7
            int r1 = r6 + 1
            r7 = 56320(0xdc00, float:7.8921E-41)
            r3 = r5 & 1023(0x3ff, float:1.434E-42)
            int r3 = r3 + r7
            r7 = r0 & 127(0x7f, float:1.78E-43)
            int r3 = r3 + r7
            char r3 = (char) r3
            r12[r6] = r3
            r17 = r0
            r0 = r2
            goto L_0x0219
        L_0x01ad:
            if (r2 < r11) goto L_0x01c1
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x01c1:
            int r3 = r0 + -24
            r8.fCurrentWindow = r3
            int[] r3 = r8.fOffsets
            int r5 = r8.fCurrentWindow
            int[] r6 = sOffsetTable
            int r7 = r2 + 1
            byte r2 = r9[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r2 = r6[r2]
            r3[r5] = r2
            r17 = r0
            r0 = r7
            goto L_0x0219
        L_0x01da:
            int r3 = r0 + -16
            r8.fCurrentWindow = r3
            r6 = 65536(0x10000, float:9.18355E-41)
            goto L_0x029d
        L_0x01e3:
            r3 = 1
            r8.fMode = r3
        L_0x01e7:
            r17 = r0
            r0 = r2
            goto L_0x02a3
        L_0x01ec:
            int r3 = r2 + 1
            if (r3 < r11) goto L_0x0202
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x0202:
            int r3 = r2 + 1
            byte r0 = r9[r2]
            int r2 = r1 + 1
            int r5 = r0 << 8
            int r6 = r3 + 1
            byte r3 = r9[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r3 = r3 | r5
            char r3 = (char) r3
            r12[r1] = r3
            r17 = r0
            r1 = r2
            r0 = r6
        L_0x0219:
            r6 = 65536(0x10000, float:9.18355E-41)
            goto L_0x02a0
        L_0x021d:
            int r3 = r2 + 1
            if (r3 < r11) goto L_0x0233
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x0233:
            int r3 = r2 + 1
            byte r2 = r9[r2]
            r0 = r2 & 255(0xff, float:3.57E-43)
            r2 = r0 & 224(0xe0, float:3.14E-43)
            int r2 = r2 >> 5
            r8.fCurrentWindow = r2
            int[] r2 = r8.fOffsets
            int r5 = r8.fCurrentWindow
            r6 = r0 & 31
            int r6 = r6 << 8
            int r7 = r3 + 1
            byte r3 = r9[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r3 = r3 | r6
            int r3 = r3 * r4
            r6 = 65536(0x10000, float:9.18355E-41)
            int r3 = r3 + r6
            r2[r5] = r3
            r17 = r0
            r0 = r7
            goto L_0x02a0
        L_0x0259:
            r6 = r3
            if (r2 < r11) goto L_0x026d
            int r2 = r2 + -1
            byte[] r3 = r8.fBuffer
            int r4 = r11 - r2
            java.lang.System.arraycopy(r9, r2, r3, r13, r4)
            int r3 = r11 - r2
            r8.fBufferLength = r3
            int r3 = r8.fBufferLength
            int r2 = r2 + r3
            goto L_0x02a8
        L_0x026d:
            int r3 = r2 + 1
            byte r2 = r9[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r5 = r1 + 1
            if (r2 < 0) goto L_0x0280
            if (r2 >= r4) goto L_0x0280
            int[] r7 = sOffsets
            int r15 = r0 + -1
            r7 = r7[r15]
            goto L_0x0287
        L_0x0280:
            int[] r7 = r8.fOffsets
            int r15 = r0 + -1
            r7 = r7[r15]
            int r7 = r7 - r4
        L_0x0287:
            int r7 = r7 + r2
            char r7 = (char) r7
            r12[r1] = r7
            r17 = r0
            r0 = r3
            goto L_0x016e
        L_0x0291:
            r6 = r3
            int r3 = r1 + 1
            char r5 = (char) r0
            r12[r1] = r5
            r17 = r0
            r0 = r2
            r1 = r3
            goto L_0x02a0
        L_0x029d:
            r17 = r0
            r0 = r2
        L_0x02a0:
            r3 = r6
            goto L_0x0142
        L_0x02a3:
            goto L_0x005b
        L_0x02a5:
            r2 = r0
            r0 = r17
        L_0x02a8:
            if (r23 == 0) goto L_0x02ae
            int r3 = r2 - r10
            r23[r13] = r3
        L_0x02ae:
            int r4 = r1 - r25
            return r4
        L_0x02b1:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "charBuffer.length < 2"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeDecompressor.decompress(byte[], int, int, int[], char[], int, int):int");
    }

    public void reset() {
        this.fOffsets[0] = 128;
        this.fOffsets[1] = 192;
        this.fOffsets[2] = 1024;
        this.fOffsets[3] = 1536;
        this.fOffsets[4] = 2304;
        this.fOffsets[5] = 12352;
        this.fOffsets[6] = 12448;
        this.fOffsets[7] = 65280;
        this.fCurrentWindow = 0;
        this.fMode = 0;
        this.fBufferLength = 0;
    }
}
