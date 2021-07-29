package com.ibm.icu.text;

public final class UnicodeCompressor implements SCSU {
    private static boolean[] sSingleTagTable = {false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static boolean[] sUnicodeTagTable = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private int fCurrentWindow = 0;
    private int[] fIndexCount = new int[256];
    private int fMode = 0;
    private int[] fOffsets = new int[8];
    private int fTimeStamp = 0;
    private int[] fTimeStamps = new int[8];

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
        int byteCount = comp.compress(buffer, start, limit, (int[]) null, temp, 0, len);
        byte[] result = new byte[byteCount];
        System.arraycopy(temp, 0, result, 0, byteCount);
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x025e, code lost:
        r16 = r5;
        r5 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02d8, code lost:
        r16 = r5;
        r5 = r7;
        r10 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0425, code lost:
        r16 = r5;
        r25 = r8;
        r5 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b0, code lost:
        r16 = r6;
        r6 = r7;
        r25 = r18;
        r10 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x019a, code lost:
        if ((r7 + 2) < r4) goto L_0x01a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x019c, code lost:
        r5 = r5 - 1;
        r16 = r6;
        r6 = r7;
        r25 = r8;
        r11 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01a6, code lost:
        r9 = getLRDefinedWindow();
        r11 = r7 + 1;
        r3[r7] = (byte) (r9 + 232);
        r7 = r11 + 1;
        r3[r11] = (byte) r8;
        r11 = r7 + 1;
        r24 = r10;
        r3[r7] = (byte) ((r6 - sOffsetTable[r8]) + 128);
        r0.fOffsets[r9] = sOffsetTable[r8];
        r0.fCurrentWindow = r9;
        r7 = r0.fTimeStamps;
        r10 = r0.fTimeStamp + 1;
        r0.fTimeStamp = r10;
        r7[r9] = r10;
        r0.fMode = 0;
        r7 = r6;
        r6 = r11;
        r12 = r22;
        r10 = r24;
        r11 = r9;
        r9 = r23;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r10v14, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r10v70, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r9v16, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r9v3, types: [char] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(char[] r34, int r35, int r36, int[] r37, byte[] r38, int r39, int r40) {
        /*
            r33 = this;
            r0 = r33
            r2 = r36
            r3 = r38
            r4 = r40
            r5 = r39
            r6 = r35
            r7 = -1
            r8 = -1
            r9 = -1
            r10 = -1
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = r5
            int r5 = r3.length
            r15 = r6
            r6 = 4
            if (r5 < r6) goto L_0x0487
            int r5 = r4 - r39
            if (r5 < r6) goto L_0x0487
            r6 = r14
            r5 = r15
        L_0x001f:
            r16 = r7
            if (r5 >= r2) goto L_0x0477
            if (r6 >= r4) goto L_0x0477
            int r7 = r0.fMode
            r14 = -16
            r17 = r5
            switch(r7) {
                case 0: goto L_0x023a;
                case 1: goto L_0x0034;
                default: goto L_0x002e;
            }
        L_0x002e:
            r7 = r16
            r5 = r17
            goto L_0x0471
        L_0x0034:
            r7 = r6
            r6 = r17
        L_0x0037:
            if (r6 >= r2) goto L_0x0228
            if (r7 >= r4) goto L_0x0228
            int r5 = r6 + 1
            char r6 = r34[r6]
            if (r5 >= r2) goto L_0x0044
            char r9 = r34[r5]
            goto L_0x0045
        L_0x0044:
            r9 = -1
        L_0x0045:
            boolean r15 = isCompressible(r6)
            if (r15 == 0) goto L_0x01e9
            r18 = r8
            r8 = -1
            if (r9 == r8) goto L_0x005e
            boolean r8 = isCompressible(r9)
            if (r8 != 0) goto L_0x005e
            r23 = r9
            r19 = r10
            r22 = r12
            goto L_0x01f1
        L_0x005e:
            r8 = 128(0x80, float:1.794E-43)
            if (r6 >= r8) goto L_0x00cb
            r13 = r6 & 255(0xff, float:3.57E-43)
            r8 = -1
            if (r9 == r8) goto L_0x00a8
            r8 = 128(0x80, float:1.794E-43)
            if (r9 >= r8) goto L_0x00a8
            boolean[] r8 = sSingleTagTable
            boolean r8 = r8[r13]
            if (r8 != 0) goto L_0x00a8
            int r8 = r7 + 1
            if (r8 < r4) goto L_0x007e
            r8 = -1
            int r5 = r5 + r8
            r16 = r6
            r6 = r7
            r25 = r18
            goto L_0x047d
        L_0x007e:
            int r8 = r0.fCurrentWindow
            int r11 = r7 + 1
            r19 = r10
            int r10 = r8 + 224
            byte r10 = (byte) r10
            r3[r7] = r10
            int r7 = r11 + 1
            byte r10 = (byte) r13
            r3[r11] = r10
            int[] r10 = r0.fTimeStamps
            int r11 = r0.fTimeStamp
            r14 = 1
            int r11 = r11 + r14
            r0.fTimeStamp = r11
            r10[r8] = r11
            r10 = 0
            r0.fMode = r10
            r11 = r8
            r8 = r18
            r10 = r19
            r32 = r7
            r7 = r6
            r6 = r32
            goto L_0x0471
        L_0x00a8:
            r19 = r10
            int r8 = r7 + 1
            if (r8 < r4) goto L_0x00b9
            r8 = -1
            int r5 = r5 + r8
        L_0x00b0:
            r16 = r6
            r6 = r7
            r25 = r18
            r10 = r19
            goto L_0x047d
        L_0x00b9:
            int r8 = r7 + 1
            r10 = 0
            r3[r7] = r10
            int r7 = r8 + 1
            byte r10 = (byte) r13
            r3[r8] = r10
        L_0x00c3:
            r16 = r6
            r8 = r18
            r10 = r19
            goto L_0x0225
        L_0x00cb:
            r19 = r10
            int r8 = r0.findDynamicWindow(r6)
            r11 = r8
            r10 = -1
            if (r8 == r10) goto L_0x0132
            boolean r8 = r0.inDynamicWindow(r9, r11)
            if (r8 == 0) goto L_0x0111
            int r8 = r7 + 1
            if (r8 < r4) goto L_0x00e1
            int r5 = r5 + r10
            goto L_0x00b0
        L_0x00e1:
            int r8 = r7 + 1
            int r10 = r11 + 224
            byte r10 = (byte) r10
            r3[r7] = r10
            int r7 = r8 + 1
            int[] r10 = r0.fOffsets
            r10 = r10[r11]
            int r10 = r6 - r10
            r20 = r7
            r7 = 128(0x80, float:1.794E-43)
            int r10 = r10 + r7
            byte r7 = (byte) r10
            r3[r8] = r7
            int[] r7 = r0.fTimeStamps
            int r8 = r0.fTimeStamp
            r10 = 1
            int r8 = r8 + r10
            r0.fTimeStamp = r8
            r7[r11] = r8
            r0.fCurrentWindow = r11
            r7 = 0
            r0.fMode = r7
            r7 = r6
            r8 = r18
            r10 = r19
            r6 = r20
            goto L_0x0471
        L_0x0111:
            int r8 = r7 + 2
            if (r8 < r4) goto L_0x0118
            r8 = -1
            int r5 = r5 + r8
            goto L_0x00b0
        L_0x0118:
            int r12 = r6 >>> 8
            r13 = r6 & 255(0xff, float:3.57E-43)
            boolean[] r8 = sUnicodeTagTable
            boolean r8 = r8[r12]
            if (r8 == 0) goto L_0x0127
            int r8 = r7 + 1
            r3[r7] = r14
            r7 = r8
        L_0x0127:
            int r8 = r7 + 1
            byte r10 = (byte) r12
            r3[r7] = r10
            int r7 = r8 + 1
            byte r10 = (byte) r13
            r3[r8] = r10
            goto L_0x00c3
        L_0x0132:
            int r8 = makeIndex(r6)
            int[] r10 = r0.fIndexCount
            r15 = r10[r8]
            r16 = 1
            int r15 = r15 + 1
            r10[r8] = r15
            int r10 = r5 + 1
            if (r10 >= r2) goto L_0x0149
            int r10 = r5 + 1
            char r10 = r34[r10]
            goto L_0x014a
        L_0x0149:
            r10 = -1
        L_0x014a:
            r21 = r11
            int[] r11 = r0.fIndexCount
            r11 = r11[r8]
            r22 = r12
            r12 = 1
            if (r11 > r12) goto L_0x0196
            int r11 = makeIndex(r9)
            if (r8 != r11) goto L_0x0164
            int r11 = makeIndex(r10)
            if (r8 != r11) goto L_0x0164
            r23 = r9
            goto L_0x0198
        L_0x0164:
            int r11 = r7 + 2
            if (r11 < r4) goto L_0x0175
            r11 = -1
            int r5 = r5 + r11
            r16 = r6
            r6 = r7
            r25 = r8
            r11 = r21
            r12 = r22
            goto L_0x047d
        L_0x0175:
            int r12 = r6 >>> 8
            r13 = r6 & 255(0xff, float:3.57E-43)
            boolean[] r11 = sUnicodeTagTable
            boolean r11 = r11[r12]
            if (r11 == 0) goto L_0x0184
            int r11 = r7 + 1
            r3[r7] = r14
            r7 = r11
        L_0x0184:
            int r11 = r7 + 1
            r23 = r9
            byte r9 = (byte) r12
            r3[r7] = r9
            int r7 = r11 + 1
            byte r9 = (byte) r13
            r3[r11] = r9
            r16 = r6
            r11 = r21
            goto L_0x0223
        L_0x0196:
            r23 = r9
        L_0x0198:
            int r9 = r7 + 2
            if (r9 < r4) goto L_0x01a6
            r9 = -1
            int r5 = r5 + r9
            r16 = r6
            r6 = r7
            r25 = r8
            r11 = r21
            goto L_0x01fe
        L_0x01a6:
            int r9 = r33.getLRDefinedWindow()
            int r11 = r7 + 1
            int r12 = r9 + 232
            byte r12 = (byte) r12
            r3[r7] = r12
            int r7 = r11 + 1
            byte r12 = (byte) r8
            r3[r11] = r12
            int r11 = r7 + 1
            int[] r12 = sOffsetTable
            r12 = r12[r8]
            int r12 = r6 - r12
            r24 = r10
            r10 = 128(0x80, float:1.794E-43)
            int r12 = r12 + r10
            byte r10 = (byte) r12
            r3[r7] = r10
            int[] r7 = r0.fOffsets
            int[] r10 = sOffsetTable
            r10 = r10[r8]
            r7[r9] = r10
            r0.fCurrentWindow = r9
            int[] r7 = r0.fTimeStamps
            int r10 = r0.fTimeStamp
            r12 = 1
            int r10 = r10 + r12
            r0.fTimeStamp = r10
            r7[r9] = r10
            r7 = 0
            r0.fMode = r7
            r7 = r6
            r6 = r11
            r12 = r22
            r10 = r24
            r11 = r9
            r9 = r23
            goto L_0x0471
        L_0x01e9:
            r18 = r8
            r23 = r9
            r19 = r10
            r22 = r12
        L_0x01f1:
            int r8 = r7 + 2
            if (r8 < r4) goto L_0x0204
            r8 = -1
            int r5 = r5 + r8
            r16 = r6
            r6 = r7
            r25 = r18
            r10 = r19
        L_0x01fe:
            r12 = r22
            r9 = r23
            goto L_0x047d
        L_0x0204:
            int r12 = r6 >>> 8
            r13 = r6 & 255(0xff, float:3.57E-43)
            boolean[] r8 = sUnicodeTagTable
            boolean r8 = r8[r12]
            if (r8 == 0) goto L_0x0213
            int r8 = r7 + 1
            r3[r7] = r14
            r7 = r8
        L_0x0213:
            int r8 = r7 + 1
            byte r9 = (byte) r12
            r3[r7] = r9
            int r7 = r8 + 1
            byte r9 = (byte) r13
            r3[r8] = r9
            r16 = r6
            r8 = r18
            r10 = r19
        L_0x0223:
            r9 = r23
        L_0x0225:
            r6 = r5
            goto L_0x0037
        L_0x0228:
            r18 = r8
            r19 = r10
            r22 = r12
            r5 = r6
            r6 = r7
            r7 = r16
            r8 = r18
            r10 = r19
            r12 = r22
            goto L_0x0471
        L_0x023a:
            r5 = r17
        L_0x023c:
            if (r5 >= r2) goto L_0x0467
            if (r6 >= r4) goto L_0x0467
            int r7 = r5 + 1
            char r5 = r34[r5]
            if (r7 >= r2) goto L_0x0249
            char r9 = r34[r7]
            goto L_0x024a
        L_0x0249:
            r9 = -1
        L_0x024a:
            r25 = r8
            r8 = 128(0x80, float:1.794E-43)
            if (r5 >= r8) goto L_0x0278
            r13 = r5 & 255(0xff, float:3.57E-43)
            boolean[] r8 = sSingleTagTable
            boolean r8 = r8[r13]
            if (r8 == 0) goto L_0x0269
            int r8 = r6 + 1
            if (r8 < r4) goto L_0x0263
            r8 = -1
            int r7 = r7 + r8
        L_0x025e:
            r16 = r5
            r5 = r7
            goto L_0x047d
        L_0x0263:
            int r8 = r6 + 1
            r15 = 1
            r3[r6] = r15
            r6 = r8
        L_0x0269:
            int r8 = r6 + 1
            r26 = r8
            byte r8 = (byte) r13
            r3[r6] = r8
            r16 = r5
            r5 = r7
            r8 = r25
            r6 = r26
            goto L_0x023c
        L_0x0278:
            int r8 = r0.fCurrentWindow
            boolean r8 = r0.inDynamicWindow(r5, r8)
            if (r8 == 0) goto L_0x029e
            int r8 = r6 + 1
            r27 = r8
            int[] r8 = r0.fOffsets
            r28 = r10
            int r10 = r0.fCurrentWindow
            r8 = r8[r10]
            int r8 = r5 - r8
            r10 = 128(0x80, float:1.794E-43)
            int r8 = r8 + r10
            byte r8 = (byte) r8
            r3[r6] = r8
            r16 = r5
            r5 = r7
            r8 = r25
            r6 = r27
        L_0x029b:
            r10 = r28
            goto L_0x023c
        L_0x029e:
            r28 = r10
            boolean r8 = isCompressible(r5)
            r10 = 15
            if (r8 != 0) goto L_0x030e
            r8 = -1
            if (r9 == r8) goto L_0x02d2
            boolean r15 = isCompressible(r9)
            if (r15 == 0) goto L_0x02d2
            int r10 = r6 + 2
            if (r10 < r4) goto L_0x02b7
            int r7 = r7 + r8
            goto L_0x02d8
        L_0x02b7:
            int r8 = r6 + 1
            r10 = 14
            r3[r6] = r10
            int r6 = r8 + 1
            int r10 = r5 >>> 8
            byte r10 = (byte) r10
            r3[r8] = r10
            int r8 = r6 + 1
            r10 = r5 & 255(0xff, float:3.57E-43)
            byte r10 = (byte) r10
            r3[r6] = r10
            r16 = r5
            r5 = r7
            r6 = r8
        L_0x02cf:
            r8 = r25
            goto L_0x029b
        L_0x02d2:
            int r8 = r6 + 3
            if (r8 < r4) goto L_0x02df
            r8 = -1
            int r7 = r7 + r8
        L_0x02d8:
            r16 = r5
            r5 = r7
            r10 = r28
            goto L_0x047d
        L_0x02df:
            int r8 = r6 + 1
            r3[r6] = r10
            int r6 = r5 >>> 8
            r10 = r5 & 255(0xff, float:3.57E-43)
            boolean[] r12 = sUnicodeTagTable
            boolean r12 = r12[r6]
            if (r12 == 0) goto L_0x02f2
            int r12 = r8 + 1
            r3[r8] = r14
            r8 = r12
        L_0x02f2:
            int r12 = r8 + 1
            byte r13 = (byte) r6
            r3[r8] = r13
            int r8 = r12 + 1
            byte r13 = (byte) r10
            r3[r12] = r13
            r12 = 1
            r0.fMode = r12
            r12 = r6
            r6 = r8
            r13 = r10
            r8 = r25
            r10 = r28
        L_0x0307:
            r32 = r7
            r7 = r5
            r5 = r32
            goto L_0x0471
        L_0x030e:
            int r8 = r0.findDynamicWindow(r5)
            r11 = r8
            r10 = -1
            if (r8 == r10) goto L_0x0397
            int r8 = r7 + 1
            if (r8 >= r2) goto L_0x0320
            int r8 = r7 + 1
            char r8 = r34[r8]
        L_0x031e:
            r10 = r8
            goto L_0x0322
        L_0x0320:
            r8 = -1
            goto L_0x031e
        L_0x0322:
            boolean r8 = r0.inDynamicWindow(r9, r11)
            if (r8 == 0) goto L_0x0366
            boolean r8 = r0.inDynamicWindow(r10, r11)
            if (r8 == 0) goto L_0x0366
            int r8 = r6 + 1
            if (r8 < r4) goto L_0x0336
            r8 = -1
            int r7 = r7 + r8
            goto L_0x025e
        L_0x0336:
            int r8 = r6 + 1
            r29 = r10
            int r10 = r11 + 16
            byte r10 = (byte) r10
            r3[r6] = r10
            int r6 = r8 + 1
            int[] r10 = r0.fOffsets
            r10 = r10[r11]
            int r10 = r5 - r10
            r30 = r6
            r6 = 128(0x80, float:1.794E-43)
            int r10 = r10 + r6
            byte r6 = (byte) r10
            r3[r8] = r6
            int[] r6 = r0.fTimeStamps
            int r8 = r0.fTimeStamp
            r10 = 1
            int r8 = r8 + r10
            r0.fTimeStamp = r8
            r6[r11] = r8
            r0.fCurrentWindow = r11
            r16 = r5
            r5 = r7
            r8 = r25
            r10 = r29
            r6 = r30
            goto L_0x023c
        L_0x0366:
            r29 = r10
            int r8 = r6 + 1
            if (r8 < r4) goto L_0x0375
            r8 = -1
            int r7 = r7 + r8
            r16 = r5
            r5 = r7
            r10 = r29
            goto L_0x047d
        L_0x0375:
            int r8 = r6 + 1
            int r10 = r11 + 1
            byte r10 = (byte) r10
            r3[r6] = r10
            int r6 = r8 + 1
            int[] r10 = r0.fOffsets
            r10 = r10[r11]
            int r10 = r5 - r10
            r31 = r6
            r6 = 128(0x80, float:1.794E-43)
            int r10 = r10 + r6
            byte r6 = (byte) r10
            r3[r8] = r6
            r16 = r5
            r5 = r7
            r8 = r25
            r10 = r29
            r6 = r31
            goto L_0x023c
        L_0x0397:
            int r8 = findStaticWindow(r5)
            r11 = r8
            r10 = -1
            if (r8 == r10) goto L_0x03c3
            boolean r8 = inStaticWindow(r9, r11)
            if (r8 != 0) goto L_0x03c3
            int r8 = r6 + 1
            if (r8 < r4) goto L_0x03ac
            int r7 = r7 + r10
            goto L_0x02d8
        L_0x03ac:
            int r8 = r6 + 1
            int r10 = r11 + 1
            byte r10 = (byte) r10
            r3[r6] = r10
            int r6 = r8 + 1
            int[] r10 = sOffsets
            r10 = r10[r11]
            int r10 = r5 - r10
            byte r10 = (byte) r10
            r3[r8] = r10
            r16 = r5
            r5 = r7
            goto L_0x02cf
        L_0x03c3:
            int r8 = makeIndex(r5)
            int[] r10 = r0.fIndexCount
            r15 = r10[r8]
            r16 = 1
            int r15 = r15 + 1
            r10[r8] = r15
            int r10 = r7 + 1
            if (r10 >= r2) goto L_0x03da
            int r10 = r7 + 1
            char r10 = r34[r10]
            goto L_0x03db
        L_0x03da:
            r10 = -1
        L_0x03db:
            int[] r1 = r0.fIndexCount
            r1 = r1[r8]
            r2 = 1
            if (r1 > r2) goto L_0x041f
            int r1 = makeIndex(r9)
            if (r8 != r1) goto L_0x03ef
            int r1 = makeIndex(r10)
            if (r8 != r1) goto L_0x03ef
            goto L_0x041f
        L_0x03ef:
            int r1 = r6 + 3
            if (r1 < r4) goto L_0x03f6
            r1 = -1
            int r1 = r1 + r7
            goto L_0x0425
        L_0x03f6:
            int r1 = r6 + 1
            r2 = 15
            r3[r6] = r2
            int r2 = r5 >>> 8
            r6 = r5 & 255(0xff, float:3.57E-43)
            boolean[] r12 = sUnicodeTagTable
            boolean r12 = r12[r2]
            if (r12 == 0) goto L_0x040b
            int r12 = r1 + 1
            r3[r1] = r14
            goto L_0x040c
        L_0x040b:
            r12 = r1
        L_0x040c:
            int r1 = r12 + 1
            byte r13 = (byte) r2
            r3[r12] = r13
            int r12 = r1 + 1
            byte r13 = (byte) r6
            r3[r1] = r13
            r1 = 1
            r0.fMode = r1
            r13 = r6
            r6 = r12
            r12 = r2
            goto L_0x0307
        L_0x041f:
            int r1 = r6 + 2
            if (r1 < r4) goto L_0x042b
            r1 = -1
            int r1 = r1 + r7
        L_0x0425:
            r16 = r5
            r25 = r8
            r5 = r1
            goto L_0x047d
        L_0x042b:
            r1 = -1
            int r11 = r33.getLRDefinedWindow()
            int r2 = r6 + 1
            int r1 = r11 + 24
            byte r1 = (byte) r1
            r3[r6] = r1
            int r1 = r2 + 1
            byte r6 = (byte) r8
            r3[r2] = r6
            int r6 = r1 + 1
            int[] r2 = sOffsetTable
            r2 = r2[r8]
            int r2 = r5 - r2
            r4 = 128(0x80, float:1.794E-43)
            int r2 = r2 + r4
            byte r2 = (byte) r2
            r3[r1] = r2
            int[] r1 = r0.fOffsets
            int[] r2 = sOffsetTable
            r2 = r2[r8]
            r1[r11] = r2
            r0.fCurrentWindow = r11
            int[] r1 = r0.fTimeStamps
            int r2 = r0.fTimeStamp
            r15 = 1
            int r2 = r2 + r15
            r0.fTimeStamp = r2
            r1[r11] = r2
            r16 = r5
            r5 = r7
            r2 = r36
            r4 = r40
            goto L_0x023c
        L_0x0467:
            r25 = r8
            r28 = r10
            r7 = r16
            r8 = r25
            r10 = r28
        L_0x0471:
            r2 = r36
            r4 = r40
            goto L_0x001f
        L_0x0477:
            r17 = r5
            r25 = r8
            r5 = r17
        L_0x047d:
            if (r37 == 0) goto L_0x0484
            int r2 = r5 - r35
            r4 = 0
            r37[r4] = r2
        L_0x0484:
            int r2 = r6 - r39
            return r2
        L_0x0487:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "byteBuffer.length < 4"
            r2.<init>(r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeCompressor.compress(char[], int, int, int[], byte[], int, int):int");
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
        if (c >= 65376 && c < 65439) {
            return 255;
        }
        if (c >= 128 && c < 13312) {
            return (c / 128) & 255;
        }
        if (c < 57344 || c > 65535) {
            return 0;
        }
        return ((c - 44032) / 128) & 255;
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
