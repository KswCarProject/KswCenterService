package com.ibm.icu.text;

/* loaded from: classes5.dex */
public final class UnicodeDecompressor implements SCSU {
    private static final int BUFSIZE = 3;
    private int fCurrentWindow = 0;
    private int[] fOffsets = new int[8];
    private int fMode = 0;
    private byte[] fBuffer = new byte[3];
    private int fBufferLength = 0;

    public UnicodeDecompressor() {
        reset();
    }

    public static String decompress(byte[] buffer) {
        char[] buf = decompress(buffer, 0, buffer.length);
        return new String(buf);
    }

    public static char[] decompress(byte[] buffer, int start, int limit) {
        UnicodeDecompressor comp = new UnicodeDecompressor();
        int len = Math.max(2, (limit - start) * 2);
        char[] temp = new char[len];
        int charCount = comp.decompress(buffer, start, limit, null, temp, 0, len);
        char[] result = new char[charCount];
        System.arraycopy(temp, 0, result, 0, charCount);
        return result;
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x005b, code lost:
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x005b, code lost:
        continue;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int decompress(byte[] byteBuffer, int byteBufferStart, int byteBufferLimit, int[] bytesRead, char[] charBuffer, int charBufferStart, int charBufferLimit) {
        int i;
        int bytePos;
        int bytePos2;
        int aByte;
        int bytePos3;
        int ucPos;
        int bytePos4 = byteBufferStart;
        int ucPos2 = charBufferStart;
        int aByte2 = 0;
        if (charBuffer.length < 2 || charBufferLimit - charBufferStart < 2) {
            throw new IllegalArgumentException("charBuffer.length < 2");
        }
        if (this.fBufferLength > 0) {
            int newBytes = 0;
            if (this.fBufferLength != 3) {
                int newBytes2 = this.fBuffer.length - this.fBufferLength;
                int newBytes3 = byteBufferLimit - byteBufferStart;
                newBytes = newBytes3 < newBytes2 ? byteBufferLimit - byteBufferStart : newBytes2;
                System.arraycopy(byteBuffer, byteBufferStart, this.fBuffer, this.fBufferLength, newBytes);
            }
            int newBytes4 = newBytes;
            this.fBufferLength = 0;
            i = 0;
            int count = decompress(this.fBuffer, 0, this.fBuffer.length, null, charBuffer, charBufferStart, charBufferLimit);
            ucPos2 += count;
            bytePos4 += newBytes4;
        } else {
            i = 0;
        }
        int aByte3 = bytePos4;
        int ucPos3 = ucPos2;
        while (aByte3 < byteBufferLimit && ucPos3 < charBufferLimit) {
            int bytePos5 = 65536;
            switch (this.fMode) {
                case 0:
                    while (true) {
                        if (aByte3 >= byteBufferLimit) {
                            continue;
                        } else if (ucPos3 < charBufferLimit) {
                            bytePos2 = aByte3 + 1;
                            aByte = byteBuffer[aByte3] & 255;
                            switch (aByte) {
                                case 0:
                                case 9:
                                case 10:
                                case 13:
                                case 32:
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                case 38:
                                case 39:
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                case 44:
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                case 49:
                                case 50:
                                case 51:
                                case 52:
                                case 53:
                                case 54:
                                case 55:
                                case 56:
                                case 57:
                                case 58:
                                case 59:
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                case 67:
                                case 68:
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                case 75:
                                case 76:
                                case 77:
                                case 78:
                                case 79:
                                case 80:
                                case 81:
                                case 82:
                                case 83:
                                case 84:
                                case 85:
                                case 86:
                                case 87:
                                case 88:
                                case 89:
                                case 90:
                                case 91:
                                case 92:
                                case 93:
                                case 94:
                                case 95:
                                case 96:
                                case 97:
                                case 98:
                                case 99:
                                case 100:
                                case 101:
                                case 102:
                                case 103:
                                case 104:
                                case 105:
                                case 106:
                                case 107:
                                case 108:
                                case 109:
                                case 110:
                                case 111:
                                case 112:
                                case 113:
                                case 114:
                                case 115:
                                case 116:
                                case 117:
                                case 118:
                                case 119:
                                case 120:
                                case 121:
                                case 122:
                                case 123:
                                case 124:
                                case 125:
                                case 126:
                                case 127:
                                    bytePos3 = bytePos5;
                                    charBuffer[ucPos3] = (char) aByte;
                                    aByte2 = aByte;
                                    aByte3 = bytePos2;
                                    ucPos3++;
                                    bytePos5 = bytePos3;
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                    bytePos3 = bytePos5;
                                    if (bytePos2 >= byteBufferLimit) {
                                        int bytePos6 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos6, this.fBuffer, i, byteBufferLimit - bytePos6);
                                        this.fBufferLength = byteBufferLimit - bytePos6;
                                        bytePos = bytePos6 + this.fBufferLength;
                                        break;
                                    } else {
                                        int bytePos7 = bytePos2 + 1;
                                        int dByte = byteBuffer[bytePos2] & 255;
                                        ucPos = ucPos3 + 1;
                                        charBuffer[ucPos3] = (char) (((dByte < 0 || dByte >= 128) ? this.fOffsets[aByte - 1] - 128 : sOffsets[aByte - 1]) + dByte);
                                        aByte2 = aByte;
                                        aByte3 = bytePos7;
                                        ucPos3 = ucPos;
                                        bytePos5 = bytePos3;
                                    }
                                case 11:
                                    if (bytePos2 + 1 >= byteBufferLimit) {
                                        int bytePos8 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos8, this.fBuffer, i, byteBufferLimit - bytePos8);
                                        this.fBufferLength = byteBufferLimit - bytePos8;
                                        bytePos = bytePos8 + this.fBufferLength;
                                        break;
                                    } else {
                                        int bytePos9 = bytePos2 + 1;
                                        int aByte4 = byteBuffer[bytePos2] & 255;
                                        this.fCurrentWindow = (aByte4 & 224) >> 5;
                                        bytePos3 = 65536;
                                        this.fOffsets[this.fCurrentWindow] = (((byteBuffer[bytePos9] & 255) | ((aByte4 & 31) << 8)) * 128) + 65536;
                                        aByte2 = aByte4;
                                        aByte3 = bytePos9 + 1;
                                        bytePos5 = bytePos3;
                                    }
                                case 12:
                                default:
                                    bytePos3 = bytePos5;
                                    aByte2 = aByte;
                                    aByte3 = bytePos2;
                                    bytePos5 = bytePos3;
                                case 14:
                                    if (bytePos2 + 1 >= byteBufferLimit) {
                                        int bytePos10 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos10, this.fBuffer, i, byteBufferLimit - bytePos10);
                                        this.fBufferLength = byteBufferLimit - bytePos10;
                                        bytePos = bytePos10 + this.fBufferLength;
                                        break;
                                    } else {
                                        int bytePos11 = bytePos2 + 1;
                                        int aByte5 = byteBuffer[bytePos2];
                                        charBuffer[ucPos3] = (char) ((byteBuffer[bytePos11] & 255) | (aByte5 << 8));
                                        aByte2 = aByte5;
                                        ucPos3++;
                                        aByte3 = bytePos11 + 1;
                                        bytePos3 = 65536;
                                        bytePos5 = bytePos3;
                                    }
                                case 15:
                                    this.fMode = 1;
                                    break;
                                case 16:
                                case 17:
                                case 18:
                                case 19:
                                case 20:
                                case 21:
                                case 22:
                                case 23:
                                    this.fCurrentWindow = aByte - 16;
                                    bytePos3 = 65536;
                                    aByte2 = aByte;
                                    aByte3 = bytePos2;
                                    bytePos5 = bytePos3;
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                case 30:
                                case 31:
                                    if (bytePos2 >= byteBufferLimit) {
                                        int bytePos12 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos12, this.fBuffer, i, byteBufferLimit - bytePos12);
                                        this.fBufferLength = byteBufferLimit - bytePos12;
                                        bytePos = bytePos12 + this.fBufferLength;
                                        break;
                                    } else {
                                        this.fCurrentWindow = aByte - 24;
                                        this.fOffsets[this.fCurrentWindow] = sOffsetTable[byteBuffer[bytePos2] & 255];
                                        aByte2 = aByte;
                                        aByte3 = bytePos2 + 1;
                                        bytePos3 = 65536;
                                        bytePos5 = bytePos3;
                                    }
                                case 128:
                                case 129:
                                case 130:
                                case 131:
                                case 132:
                                case 133:
                                case 134:
                                case 135:
                                case 136:
                                case 137:
                                case 138:
                                case 139:
                                case 140:
                                case 141:
                                case 142:
                                case 143:
                                case 144:
                                case 145:
                                case 146:
                                case 147:
                                case 148:
                                case 149:
                                case 150:
                                case 151:
                                case 152:
                                case 153:
                                case 154:
                                case 155:
                                case 156:
                                case 157:
                                case 158:
                                case 159:
                                case 160:
                                case 161:
                                case 162:
                                case 163:
                                case 164:
                                case 165:
                                case 166:
                                case 167:
                                case 168:
                                case 169:
                                case 170:
                                case 171:
                                case 172:
                                case 173:
                                case 174:
                                case 175:
                                case 176:
                                case 177:
                                case 178:
                                case 179:
                                case 180:
                                case 181:
                                case 182:
                                case 183:
                                case 184:
                                case 185:
                                case 186:
                                case 187:
                                case 188:
                                case 189:
                                case 190:
                                case 191:
                                case 192:
                                case 193:
                                case 194:
                                case 195:
                                case 196:
                                case 197:
                                case 198:
                                case 199:
                                case 200:
                                case 201:
                                case 202:
                                case 203:
                                case 204:
                                case 205:
                                case 206:
                                case 207:
                                case 208:
                                case 209:
                                case 210:
                                case 211:
                                case 212:
                                case 213:
                                case 214:
                                case 215:
                                case 216:
                                case 217:
                                case 218:
                                case 219:
                                case 220:
                                case 221:
                                case 222:
                                case 223:
                                case 224:
                                case 225:
                                case 226:
                                case 227:
                                case 228:
                                case 229:
                                case 230:
                                case 231:
                                case 232:
                                case 233:
                                case 234:
                                case 235:
                                case 236:
                                case 237:
                                case 238:
                                case 239:
                                case 240:
                                case 241:
                                case 242:
                                case 243:
                                case 244:
                                case 245:
                                case 246:
                                case 247:
                                case 248:
                                case 249:
                                case 250:
                                case 251:
                                case 252:
                                case 253:
                                case 254:
                                case 255:
                                    if (this.fOffsets[this.fCurrentWindow] <= 65535) {
                                        ucPos = ucPos3 + 1;
                                        charBuffer[ucPos3] = (char) ((this.fOffsets[this.fCurrentWindow] + aByte) - 128);
                                        aByte2 = aByte;
                                        aByte3 = bytePos2;
                                        bytePos3 = bytePos5;
                                        ucPos3 = ucPos;
                                        bytePos5 = bytePos3;
                                    } else if (ucPos3 + 1 >= charBufferLimit) {
                                        int bytePos13 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos13, this.fBuffer, i, byteBufferLimit - bytePos13);
                                        this.fBufferLength = byteBufferLimit - bytePos13;
                                        bytePos = bytePos13 + this.fBufferLength;
                                        break;
                                    } else {
                                        int normalizedBase = this.fOffsets[this.fCurrentWindow] - bytePos5;
                                        int ucPos4 = ucPos3 + 1;
                                        charBuffer[ucPos3] = (char) (55296 + (normalizedBase >> 10));
                                        ucPos3 = ucPos4 + 1;
                                        charBuffer[ucPos4] = (char) ((normalizedBase & 1023) + UTF16.TRAIL_SURROGATE_MIN_VALUE + (aByte & 127));
                                        aByte2 = aByte;
                                        aByte3 = bytePos2;
                                        bytePos3 = 65536;
                                        bytePos5 = bytePos3;
                                    }
                            }
                        }
                    }
                    if (bytesRead != null) {
                        bytesRead[i] = bytePos - byteBufferStart;
                    }
                    return ucPos3 - charBufferStart;
                case 1:
                    while (true) {
                        if (aByte3 >= byteBufferLimit) {
                            continue;
                        } else if (ucPos3 < charBufferLimit) {
                            bytePos2 = aByte3 + 1;
                            aByte = byteBuffer[aByte3] & 255;
                            switch (aByte) {
                                case 224:
                                case 225:
                                case 226:
                                case 227:
                                case 228:
                                case 229:
                                case 230:
                                case 231:
                                    this.fCurrentWindow = aByte - 224;
                                    this.fMode = i;
                                    break;
                                case 232:
                                case 233:
                                case 234:
                                case 235:
                                case 236:
                                case 237:
                                case 238:
                                case 239:
                                    if (bytePos2 < byteBufferLimit) {
                                        this.fCurrentWindow = aByte - 232;
                                        this.fOffsets[this.fCurrentWindow] = sOffsetTable[byteBuffer[bytePos2] & 255];
                                        this.fMode = i;
                                        aByte2 = aByte;
                                        aByte3 = bytePos2 + 1;
                                        break;
                                    } else {
                                        int bytePos14 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos14, this.fBuffer, i, byteBufferLimit - bytePos14);
                                        this.fBufferLength = byteBufferLimit - bytePos14;
                                        bytePos = bytePos14 + this.fBufferLength;
                                        break;
                                    }
                                case 240:
                                    if (bytePos2 >= byteBufferLimit - 1) {
                                        int bytePos15 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos15, this.fBuffer, i, byteBufferLimit - bytePos15);
                                        this.fBufferLength = byteBufferLimit - bytePos15;
                                        bytePos = bytePos15 + this.fBufferLength;
                                        break;
                                    } else {
                                        int bytePos16 = bytePos2 + 1;
                                        aByte2 = byteBuffer[bytePos2];
                                        int aByte6 = ucPos3 + 1;
                                        charBuffer[ucPos3] = (char) ((aByte2 << 8) | (byteBuffer[bytePos16] & 255));
                                        ucPos3 = aByte6;
                                        aByte3 = bytePos16 + 1;
                                    }
                                case 241:
                                    if (bytePos2 + 1 < byteBufferLimit) {
                                        int bytePos17 = bytePos2 + 1;
                                        int aByte7 = byteBuffer[bytePos2] & 255;
                                        this.fCurrentWindow = (aByte7 & 224) >> 5;
                                        this.fOffsets[this.fCurrentWindow] = (((byteBuffer[bytePos17] & 255) | ((aByte7 & 31) << 8)) * 128) + 65536;
                                        this.fMode = i;
                                        aByte2 = aByte7;
                                        aByte3 = bytePos17 + 1;
                                        break;
                                    } else {
                                        int bytePos18 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos18, this.fBuffer, i, byteBufferLimit - bytePos18);
                                        this.fBufferLength = byteBufferLimit - bytePos18;
                                        bytePos = bytePos18 + this.fBufferLength;
                                        break;
                                    }
                                default:
                                    if (bytePos2 >= byteBufferLimit) {
                                        int bytePos19 = bytePos2 - 1;
                                        System.arraycopy(byteBuffer, bytePos19, this.fBuffer, i, byteBufferLimit - bytePos19);
                                        this.fBufferLength = byteBufferLimit - bytePos19;
                                        bytePos = bytePos19 + this.fBufferLength;
                                        break;
                                    } else {
                                        charBuffer[ucPos3] = (char) ((byteBuffer[bytePos2] & 255) | (aByte << 8));
                                        aByte2 = aByte;
                                        ucPos3++;
                                        aByte3 = bytePos2 + 1;
                                    }
                            }
                        }
                    }
                    if (bytesRead != null) {
                    }
                    return ucPos3 - charBufferStart;
            }
            aByte2 = aByte;
            aByte3 = bytePos2;
        }
        bytePos = aByte3;
        if (bytesRead != null) {
        }
        return ucPos3 - charBufferStart;
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
