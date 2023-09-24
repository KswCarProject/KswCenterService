package com.ibm.icu.text;

import android.bluetooth.BluetoothHidDevice;
import com.android.internal.midi.MidiConstants;

/* loaded from: classes5.dex */
public final class UnicodeCompressor implements SCSU {
    private static boolean[] sSingleTagTable = {false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static boolean[] sUnicodeTagTable = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private int fCurrentWindow = 0;
    private int[] fOffsets = new int[8];
    private int fMode = 0;
    private int[] fIndexCount = new int[256];
    private int[] fTimeStamps = new int[8];
    private int fTimeStamp = 0;

    public UnicodeCompressor() {
        reset();
    }

    public static byte[] compress(String buffer) {
        return compress(buffer.toCharArray(), 0, buffer.length());
    }

    public static byte[] compress(char[] buffer, int start, int limit) {
        UnicodeCompressor comp = new UnicodeCompressor();
        int len = Math.max(4, ((limit - start) * 3) + 1);
        byte[] temp = new byte[len];
        int byteCount = comp.compress(buffer, start, limit, null, temp, 0, len);
        byte[] result = new byte[byteCount];
        System.arraycopy(temp, 0, result, 0, byteCount);
        return result;
    }

    /* JADX WARN: Code restructure failed: missing block: B:109:0x025e, code lost:
        r5 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x02d8, code lost:
        r5 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0425, code lost:
        r5 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0477, code lost:
        r5 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b0, code lost:
        r6 = r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int compress(char[] charBuffer, int charBufferStart, int charBufferLimit, int[] charsRead, byte[] byteBuffer, int byteBufferStart, int byteBufferLimit) {
        int ucPos;
        int ucPos2;
        int i;
        int ucPos3;
        int i2;
        int bytePos;
        int curIndex;
        int nextUC;
        int forwardUC;
        int hiByte;
        int forwardUC2;
        int i3 = charBufferLimit;
        int i4 = byteBufferLimit;
        int curUC = -1;
        int curIndex2 = -1;
        int forwardUC3 = '\uffff';
        int hiByte2 = 0;
        int bytePos2 = byteBuffer.length;
        if (bytePos2 < 4 || i4 - byteBufferStart < 4) {
            throw new IllegalArgumentException("byteBuffer.length < 4");
        }
        int bytePos3 = byteBufferStart;
        int ucPos4 = charBufferStart;
        while (true) {
            int curUC2 = curUC;
            if (ucPos4 < i3 && bytePos3 < i4) {
                int ucPos5 = ucPos4;
                switch (this.fMode) {
                    case 0:
                        ucPos4 = ucPos5;
                        while (ucPos4 < i3 && bytePos3 < i4) {
                            int ucPos6 = ucPos4 + 1;
                            char c = charBuffer[ucPos4];
                            char c2 = ucPos6 < i3 ? charBuffer[ucPos6] : '\uffff';
                            int curIndex3 = curIndex2;
                            if (c < '\u0080') {
                                int loByte = c & '\u00ff';
                                if (sSingleTagTable[loByte]) {
                                    if (bytePos3 + 1 >= i4) {
                                        ucPos2 = ucPos6 - 1;
                                        break;
                                    } else {
                                        byteBuffer[bytePos3] = 1;
                                        bytePos3++;
                                    }
                                }
                                byteBuffer[bytePos3] = (byte) loByte;
                                curUC2 = c;
                                ucPos4 = ucPos6;
                                curIndex2 = curIndex3;
                                bytePos3++;
                            } else {
                                if (inDynamicWindow(c, this.fCurrentWindow)) {
                                    int[] iArr = this.fOffsets;
                                    i = forwardUC3;
                                    int forwardUC4 = this.fCurrentWindow;
                                    byteBuffer[bytePos3] = (byte) ((c - iArr[forwardUC4]) + 128);
                                    curUC2 = c;
                                    ucPos4 = ucPos6;
                                    curIndex2 = curIndex3;
                                    bytePos3++;
                                } else {
                                    i = forwardUC3;
                                    if (isCompressible(c)) {
                                        int whichWindow = findDynamicWindow(c);
                                        if (whichWindow != -1) {
                                            char c3 = ucPos6 + 1 < i3 ? charBuffer[ucPos6 + 1] : '\uffff';
                                            if (inDynamicWindow(c2, whichWindow) && inDynamicWindow(c3, whichWindow)) {
                                                if (bytePos3 + 1 >= i4) {
                                                    ucPos2 = ucPos6 - 1;
                                                    break;
                                                } else {
                                                    int bytePos4 = bytePos3 + 1;
                                                    int forwardUC5 = whichWindow + 16;
                                                    byteBuffer[bytePos3] = (byte) forwardUC5;
                                                    int bytePos5 = bytePos4 + 1;
                                                    byteBuffer[bytePos4] = (byte) ((c - this.fOffsets[whichWindow]) + 128);
                                                    int[] iArr2 = this.fTimeStamps;
                                                    int i5 = this.fTimeStamp + 1;
                                                    this.fTimeStamp = i5;
                                                    iArr2[whichWindow] = i5;
                                                    this.fCurrentWindow = whichWindow;
                                                    curUC2 = c;
                                                    ucPos4 = ucPos6;
                                                    curIndex2 = curIndex3;
                                                    forwardUC3 = c3;
                                                    bytePos3 = bytePos5;
                                                }
                                            } else if (bytePos3 + 1 >= i4) {
                                                ucPos = ucPos6 - 1;
                                                break;
                                            } else {
                                                int bytePos6 = bytePos3 + 1;
                                                byteBuffer[bytePos3] = (byte) (whichWindow + 1);
                                                int bytePos7 = bytePos6 + 1;
                                                byteBuffer[bytePos6] = (byte) ((c - this.fOffsets[whichWindow]) + 128);
                                                curUC2 = c;
                                                ucPos4 = ucPos6;
                                                curIndex2 = curIndex3;
                                                forwardUC3 = c3;
                                                bytePos3 = bytePos7;
                                            }
                                        } else {
                                            int whichWindow2 = findStaticWindow(c);
                                            if (whichWindow2 == -1 || inStaticWindow(c2, whichWindow2)) {
                                                curIndex2 = makeIndex(c);
                                                int[] iArr3 = this.fIndexCount;
                                                iArr3[curIndex2] = iArr3[curIndex2] + 1;
                                                forwardUC3 = ucPos6 + 1 < i3 ? charBuffer[ucPos6 + 1] : -1;
                                                if (this.fIndexCount[curIndex2] > 1 || (curIndex2 == makeIndex(c2) && curIndex2 == makeIndex(forwardUC3))) {
                                                    if (bytePos3 + 2 >= i4) {
                                                        i2 = (-1) + ucPos6;
                                                        break;
                                                    } else {
                                                        int whichWindow3 = getLRDefinedWindow();
                                                        int bytePos8 = bytePos3 + 1;
                                                        byteBuffer[bytePos3] = (byte) (whichWindow3 + 24);
                                                        int bytePos9 = bytePos8 + 1;
                                                        byteBuffer[bytePos8] = (byte) curIndex2;
                                                        bytePos3 = bytePos9 + 1;
                                                        byteBuffer[bytePos9] = (byte) ((c - sOffsetTable[curIndex2]) + 128);
                                                        this.fOffsets[whichWindow3] = sOffsetTable[curIndex2];
                                                        this.fCurrentWindow = whichWindow3;
                                                        int[] iArr4 = this.fTimeStamps;
                                                        int i6 = this.fTimeStamp + 1;
                                                        this.fTimeStamp = i6;
                                                        iArr4[whichWindow3] = i6;
                                                        curUC2 = c;
                                                        ucPos4 = ucPos6;
                                                        i3 = charBufferLimit;
                                                        i4 = byteBufferLimit;
                                                    }
                                                } else if (bytePos3 + 3 >= i4) {
                                                    i2 = (-1) + ucPos6;
                                                    break;
                                                } else {
                                                    int bytePos10 = bytePos3 + 1;
                                                    byteBuffer[bytePos3] = MidiConstants.STATUS_CHANNEL_MASK;
                                                    int hiByte3 = c >>> '\b';
                                                    int loByte2 = c & '\u00ff';
                                                    if (sUnicodeTagTable[hiByte3]) {
                                                        bytePos = bytePos10 + 1;
                                                        byteBuffer[bytePos10] = -16;
                                                    } else {
                                                        bytePos = bytePos10;
                                                    }
                                                    int bytePos11 = bytePos + 1;
                                                    byteBuffer[bytePos] = (byte) hiByte3;
                                                    byteBuffer[bytePos11] = (byte) loByte2;
                                                    this.fMode = 1;
                                                    bytePos3 = bytePos11 + 1;
                                                    hiByte2 = hiByte3;
                                                    curUC = c;
                                                    ucPos4 = ucPos6;
                                                }
                                            } else if (bytePos3 + 1 >= i4) {
                                                ucPos3 = ucPos6 - 1;
                                                break;
                                            } else {
                                                int bytePos12 = bytePos3 + 1;
                                                byteBuffer[bytePos3] = (byte) (whichWindow2 + 1);
                                                bytePos3 = bytePos12 + 1;
                                                byteBuffer[bytePos12] = (byte) (c - sOffsets[whichWindow2]);
                                                curUC2 = c;
                                                ucPos4 = ucPos6;
                                                curIndex2 = curIndex3;
                                            }
                                        }
                                    } else if (c2 == '\uffff' || !isCompressible(c2)) {
                                        int curIndex4 = bytePos3 + 3;
                                        if (curIndex4 < i4) {
                                            int bytePos13 = bytePos3 + 1;
                                            byteBuffer[bytePos3] = MidiConstants.STATUS_CHANNEL_MASK;
                                            int bytePos14 = c >>> '\b';
                                            int loByte3 = c & '\u00ff';
                                            if (sUnicodeTagTable[bytePos14]) {
                                                byteBuffer[bytePos13] = -16;
                                                bytePos13++;
                                            }
                                            int bytePos15 = bytePos13 + 1;
                                            byteBuffer[bytePos13] = (byte) bytePos14;
                                            int bytePos16 = bytePos15 + 1;
                                            byteBuffer[bytePos15] = (byte) loByte3;
                                            this.fMode = 1;
                                            hiByte2 = bytePos14;
                                            bytePos3 = bytePos16;
                                            curIndex2 = curIndex3;
                                            forwardUC3 = i;
                                            curUC = c;
                                            ucPos4 = ucPos6;
                                            break;
                                        } else {
                                            ucPos3 = ucPos6 - 1;
                                            break;
                                        }
                                    } else if (bytePos3 + 2 >= i4) {
                                        ucPos3 = ucPos6 - 1;
                                        break;
                                    } else {
                                        int bytePos17 = bytePos3 + 1;
                                        byteBuffer[bytePos3] = BluetoothHidDevice.ERROR_RSP_UNKNOWN;
                                        int bytePos18 = bytePos17 + 1;
                                        byteBuffer[bytePos17] = (byte) (c >>> '\b');
                                        byteBuffer[bytePos18] = (byte) (c & '\u00ff');
                                        curUC2 = c;
                                        ucPos4 = ucPos6;
                                        bytePos3 = bytePos18 + 1;
                                        curIndex2 = curIndex3;
                                    }
                                }
                                forwardUC3 = i;
                            }
                        }
                        curUC = curUC2;
                        curIndex2 = curIndex2;
                        forwardUC3 = forwardUC3;
                        break;
                    case 1:
                        int bytePos19 = bytePos3;
                        int ucPos7 = ucPos5;
                        while (ucPos7 < i3 && bytePos19 < i4) {
                            ucPos4 = ucPos7 + 1;
                            char c4 = charBuffer[ucPos7];
                            int i7 = ucPos4 < i3 ? charBuffer[ucPos4] : '\uffff';
                            if (isCompressible(c4)) {
                                curIndex = curIndex2;
                                if (i7 != -1 && !isCompressible(i7)) {
                                    nextUC = i7;
                                    forwardUC = forwardUC3;
                                    hiByte = hiByte2;
                                } else if (c4 >= '\u0080') {
                                    forwardUC2 = forwardUC3;
                                    int whichWindow4 = findDynamicWindow(c4);
                                    if (whichWindow4 == -1) {
                                        curIndex2 = makeIndex(c4);
                                        int[] iArr5 = this.fIndexCount;
                                        iArr5[curIndex2] = iArr5[curIndex2] + 1;
                                        forwardUC3 = ucPos4 + 1 < i3 ? charBuffer[ucPos4 + 1] : '\uffff';
                                        hiByte = hiByte2;
                                        if (this.fIndexCount[curIndex2] > 1) {
                                            nextUC = i7;
                                        } else if (curIndex2 == makeIndex(i7) && curIndex2 == makeIndex(forwardUC3)) {
                                            nextUC = i7;
                                        } else if (bytePos19 + 2 >= i4) {
                                            ucPos = ucPos4 - 1;
                                            bytePos3 = bytePos19;
                                            break;
                                        } else {
                                            hiByte2 = c4 >>> '\b';
                                            int loByte4 = c4 & '\u00ff';
                                            if (sUnicodeTagTable[hiByte2]) {
                                                byteBuffer[bytePos19] = -16;
                                                bytePos19++;
                                            }
                                            int bytePos20 = bytePos19 + 1;
                                            nextUC = i7;
                                            byteBuffer[bytePos19] = (byte) hiByte2;
                                            bytePos19 = bytePos20 + 1;
                                            byteBuffer[bytePos20] = (byte) loByte4;
                                            curUC2 = c4;
                                            ucPos7 = ucPos4;
                                        }
                                        int nextUC2 = bytePos19 + 2;
                                        if (nextUC2 < i4) {
                                            int whichWindow5 = getLRDefinedWindow();
                                            int bytePos21 = bytePos19 + 1;
                                            byteBuffer[bytePos19] = (byte) (whichWindow5 + 232);
                                            int bytePos22 = bytePos21 + 1;
                                            byteBuffer[bytePos21] = (byte) curIndex2;
                                            int bytePos23 = bytePos22 + 1;
                                            int i8 = forwardUC3;
                                            byteBuffer[bytePos22] = (byte) ((c4 - sOffsetTable[curIndex2]) + 128);
                                            this.fOffsets[whichWindow5] = sOffsetTable[curIndex2];
                                            this.fCurrentWindow = whichWindow5;
                                            int[] iArr6 = this.fTimeStamps;
                                            int i9 = this.fTimeStamp + 1;
                                            this.fTimeStamp = i9;
                                            iArr6[whichWindow5] = i9;
                                            this.fMode = 0;
                                            curUC = c4;
                                            bytePos3 = bytePos23;
                                            hiByte2 = hiByte;
                                            forwardUC3 = i8;
                                            break;
                                        } else {
                                            ucPos = ucPos4 - 1;
                                            bytePos3 = bytePos19;
                                            break;
                                        }
                                    } else if (inDynamicWindow(i7, whichWindow4)) {
                                        if (bytePos19 + 1 < i4) {
                                            int bytePos24 = bytePos19 + 1;
                                            byteBuffer[bytePos19] = (byte) (whichWindow4 + 224);
                                            int bytePos25 = bytePos24 + 1;
                                            byteBuffer[bytePos24] = (byte) ((c4 - this.fOffsets[whichWindow4]) + 128);
                                            int[] iArr7 = this.fTimeStamps;
                                            int i10 = this.fTimeStamp + 1;
                                            this.fTimeStamp = i10;
                                            iArr7[whichWindow4] = i10;
                                            this.fCurrentWindow = whichWindow4;
                                            this.fMode = 0;
                                            curUC = c4;
                                            curIndex2 = curIndex;
                                            forwardUC3 = forwardUC2;
                                            bytePos3 = bytePos25;
                                            break;
                                        } else {
                                            ucPos = ucPos4 - 1;
                                            break;
                                        }
                                    } else if (bytePos19 + 2 >= i4) {
                                        ucPos = ucPos4 - 1;
                                        break;
                                    } else {
                                        hiByte2 = c4 >>> '\b';
                                        int loByte5 = c4 & '\u00ff';
                                        if (sUnicodeTagTable[hiByte2]) {
                                            byteBuffer[bytePos19] = -16;
                                            bytePos19++;
                                        }
                                        int bytePos26 = bytePos19 + 1;
                                        byteBuffer[bytePos19] = (byte) hiByte2;
                                        bytePos19 = bytePos26 + 1;
                                        byteBuffer[bytePos26] = (byte) loByte5;
                                        curUC2 = c4;
                                        curIndex2 = curIndex;
                                        forwardUC3 = forwardUC2;
                                        ucPos7 = ucPos4;
                                    }
                                } else {
                                    int loByte6 = c4 & '\u00ff';
                                    if (i7 != -1 && i7 < 128 && !sSingleTagTable[loByte6]) {
                                        if (bytePos19 + 1 < i4) {
                                            int whichWindow6 = this.fCurrentWindow;
                                            int whichWindow7 = bytePos19 + 1;
                                            int forwardUC6 = forwardUC3;
                                            int forwardUC7 = whichWindow6 + 224;
                                            byteBuffer[bytePos19] = (byte) forwardUC7;
                                            int bytePos27 = whichWindow7 + 1;
                                            byteBuffer[whichWindow7] = (byte) loByte6;
                                            int[] iArr8 = this.fTimeStamps;
                                            int i11 = this.fTimeStamp + 1;
                                            this.fTimeStamp = i11;
                                            iArr8[whichWindow6] = i11;
                                            this.fMode = 0;
                                            curIndex2 = curIndex;
                                            forwardUC3 = forwardUC6;
                                            curUC = c4;
                                            bytePos3 = bytePos27;
                                            break;
                                        } else {
                                            ucPos = ucPos4 - 1;
                                            bytePos3 = bytePos19;
                                            break;
                                        }
                                    } else {
                                        forwardUC2 = forwardUC3;
                                        if (bytePos19 + 1 >= i4) {
                                            ucPos = ucPos4 - 1;
                                            break;
                                        } else {
                                            int bytePos28 = bytePos19 + 1;
                                            byteBuffer[bytePos19] = 0;
                                            bytePos19 = bytePos28 + 1;
                                            byteBuffer[bytePos28] = (byte) loByte6;
                                            curUC2 = c4;
                                            curIndex2 = curIndex;
                                            forwardUC3 = forwardUC2;
                                            ucPos7 = ucPos4;
                                        }
                                    }
                                }
                            } else {
                                curIndex = curIndex2;
                                nextUC = i7;
                                forwardUC = forwardUC3;
                                hiByte = hiByte2;
                            }
                            int curIndex5 = bytePos19 + 2;
                            if (curIndex5 >= i4) {
                                ucPos = ucPos4 - 1;
                                bytePos3 = bytePos19;
                                break;
                            } else {
                                hiByte2 = c4 >>> '\b';
                                int loByte7 = c4 & '\u00ff';
                                if (sUnicodeTagTable[hiByte2]) {
                                    byteBuffer[bytePos19] = -16;
                                    bytePos19++;
                                }
                                int bytePos29 = bytePos19 + 1;
                                byteBuffer[bytePos19] = (byte) hiByte2;
                                bytePos19 = bytePos29 + 1;
                                byteBuffer[bytePos29] = (byte) loByte7;
                                curUC2 = c4;
                                curIndex2 = curIndex;
                                forwardUC3 = forwardUC;
                                ucPos7 = ucPos4;
                            }
                        }
                        ucPos4 = ucPos7;
                        bytePos3 = bytePos19;
                        curUC = curUC2;
                        curIndex2 = curIndex2;
                        forwardUC3 = forwardUC3;
                        hiByte2 = hiByte2;
                        break;
                    default:
                        curUC = curUC2;
                        ucPos4 = ucPos5;
                        break;
                }
                i3 = charBufferLimit;
                i4 = byteBufferLimit;
            }
        }
        if (charsRead != null) {
            charsRead[0] = ucPos - charBufferStart;
        }
        return bytePos3 - byteBufferStart;
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
        for (int i = 0; i < 8; i++) {
            this.fTimeStamps[i] = 0;
        }
        for (int i2 = 0; i2 <= 255; i2++) {
            this.fIndexCount[i2] = 0;
        }
        this.fTimeStamp = 0;
        this.fCurrentWindow = 0;
        this.fMode = 0;
    }

    private static int makeIndex(int c) {
        if (c >= 192 && c < 320) {
            return 249;
        }
        if (c >= 592 && c < 720) {
            return 250;
        }
        if (c >= 880 && c < 1008) {
            return 251;
        }
        if (c >= 1328 && c < 1424) {
            return 252;
        }
        if (c >= 12352 && c < 12448) {
            return 253;
        }
        if (c >= 12448 && c < 12576) {
            return 254;
        }
        if (c < 65376 || c >= 65439) {
            if (c >= 128 && c < 13312) {
                return (c / 128) & 255;
            }
            if (c >= 57344 && c <= 65535) {
                return ((c - 44032) / 128) & 255;
            }
            return 0;
        }
        return 255;
    }

    private boolean inDynamicWindow(int c, int whichWindow) {
        return c >= this.fOffsets[whichWindow] && c < this.fOffsets[whichWindow] + 128;
    }

    private static boolean inStaticWindow(int c, int whichWindow) {
        return c >= sOffsets[whichWindow] && c < sOffsets[whichWindow] + 128;
    }

    private static boolean isCompressible(int c) {
        return c < 13312 || c >= 57344;
    }

    private int findDynamicWindow(int c) {
        for (int i = 7; i >= 0; i--) {
            if (inDynamicWindow(c, i)) {
                int[] iArr = this.fTimeStamps;
                iArr[i] = iArr[i] + 1;
                return i;
            }
        }
        return -1;
    }

    private static int findStaticWindow(int c) {
        for (int i = 7; i >= 0; i--) {
            if (inStaticWindow(c, i)) {
                return i;
            }
        }
        return -1;
    }

    private int getLRDefinedWindow() {
        int leastRU = Integer.MAX_VALUE;
        int whichWindow = -1;
        for (int i = 7; i >= 0; i--) {
            if (this.fTimeStamps[i] < leastRU) {
                leastRU = this.fTimeStamps[i];
                whichWindow = i;
            }
        }
        return whichWindow;
    }
}
