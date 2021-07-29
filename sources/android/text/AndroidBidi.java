package android.text;

import android.annotation.UnsupportedAppUsage;
import android.icu.lang.UCharacter;
import android.icu.text.BidiClassifier;
import com.android.internal.annotations.VisibleForTesting;
import com.ibm.icu.text.Bidi;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
public class AndroidBidi {
    private static final EmojiBidiOverride sEmojiBidiOverride = new EmojiBidiOverride();

    public static class EmojiBidiOverride extends BidiClassifier {
        private static final int NO_OVERRIDE = (UCharacter.getIntPropertyMaxValue(4096) + 1);

        public EmojiBidiOverride() {
            super((Object) null);
        }

        public int classify(int c) {
            if (Emoji.isNewEmoji(c)) {
                return 10;
            }
            return NO_OVERRIDE;
        }
    }

    @UnsupportedAppUsage
    public static int bidi(int dir, char[] chs, byte[] chInfo) {
        byte paraLevel;
        if (chs == null || chInfo == null) {
            throw new NullPointerException();
        }
        int length = chs.length;
        if (chInfo.length >= length) {
            switch (dir) {
                case -2:
                    paraLevel = Bidi.LEVEL_DEFAULT_RTL;
                    break;
                case -1:
                    paraLevel = 1;
                    break;
                case 1:
                    paraLevel = 0;
                    break;
                case 2:
                    paraLevel = Bidi.LEVEL_DEFAULT_LTR;
                    break;
                default:
                    paraLevel = 0;
                    break;
            }
            android.icu.text.Bidi icuBidi = new android.icu.text.Bidi(length, 0);
            icuBidi.setCustomClassifier(sEmojiBidiOverride);
            icuBidi.setPara(chs, paraLevel, (byte[]) null);
            for (int i = 0; i < length; i++) {
                chInfo[i] = icuBidi.getLevelAt(i);
            }
            return (icuBidi.getParaLevel() & 1) == 0 ? 1 : -1;
        }
        throw new IndexOutOfBoundsException();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v30, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: byte} */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x007a, code lost:
        if (r4 >= r3) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007c, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r7v0, types: [byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.text.Layout.Directions directions(int r25, byte[] r26, int r27, char[] r28, int r29, int r30) {
        /*
            r2 = r30
            if (r2 != 0) goto L_0x0007
            android.text.Layout$Directions r3 = android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT
            return r3
        L_0x0007:
            r4 = 1
            r5 = r25
            if (r5 != r4) goto L_0x000e
            r6 = 0
            goto L_0x000f
        L_0x000e:
            r6 = r4
        L_0x000f:
            byte r7 = r26[r27]
            r8 = r7
            r9 = 1
            int r10 = r27 + 1
            int r11 = r27 + r2
        L_0x0017:
            if (r10 >= r11) goto L_0x0023
            byte r12 = r26[r10]
            if (r12 == r7) goto L_0x0020
            r7 = r12
            int r9 = r9 + 1
        L_0x0020:
            int r10 = r10 + 1
            goto L_0x0017
        L_0x0023:
            r10 = r30
            r11 = r7 & 1
            r12 = r6 & 1
            if (r11 == r12) goto L_0x0049
        L_0x002b:
            int r10 = r10 + -1
            if (r10 < 0) goto L_0x0044
            int r12 = r29 + r10
            char r12 = r28[r12]
            r13 = 10
            if (r12 != r13) goto L_0x003a
            int r10 = r10 + -1
            goto L_0x0044
        L_0x003a:
            r13 = 32
            if (r12 == r13) goto L_0x0043
            r13 = 9
            if (r12 == r13) goto L_0x0043
            goto L_0x0044
        L_0x0043:
            goto L_0x002b
        L_0x0044:
            int r10 = r10 + r4
            if (r10 == r2) goto L_0x0049
            int r9 = r9 + 1
        L_0x0049:
            if (r9 != r4) goto L_0x0057
            if (r8 != r6) goto L_0x0057
            r3 = r8 & 1
            if (r3 == 0) goto L_0x0054
            android.text.Layout$Directions r3 = android.text.Layout.DIRS_ALL_RIGHT_TO_LEFT
            return r3
        L_0x0054:
            android.text.Layout$Directions r3 = android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT
            return r3
        L_0x0057:
            int r12 = r9 * 2
            int[] r12 = new int[r12]
            r13 = r8
            int r14 = r8 << 26
            r15 = 1
            r16 = r27
            r7 = r8
            r17 = r27
            int r18 = r27 + r10
            r3 = r8
            r7 = r17
        L_0x0069:
            r19 = r18
            r4 = r19
            if (r7 >= r4) goto L_0x0096
            r21 = r4
            byte r4 = r26[r7]
            if (r4 == r8) goto L_0x0090
            r8 = r4
            if (r4 <= r13) goto L_0x007a
            r13 = r4
            goto L_0x007d
        L_0x007a:
            if (r4 >= r3) goto L_0x007d
            r3 = r4
        L_0x007d:
            int r17 = r15 + 1
            int r18 = r7 - r16
            r18 = r18 | r14
            r12[r15] = r18
            int r15 = r17 + 1
            int r18 = r7 - r27
            r12[r17] = r18
            int r14 = r8 << 26
            r4 = r7
            r16 = r4
        L_0x0090:
            int r7 = r7 + 1
            r18 = r21
            r4 = 1
            goto L_0x0069
        L_0x0096:
            int r4 = r27 + r10
            int r4 = r4 - r16
            r4 = r4 | r14
            r12[r15] = r4
            if (r10 >= r2) goto L_0x00ab
            r4 = 1
            int r15 = r15 + r4
            r12[r15] = r10
            int r15 = r15 + r4
            int r4 = r2 - r10
            int r7 = r6 << 26
            r4 = r4 | r7
            r12[r15] = r4
        L_0x00ab:
            r4 = r3 & 1
            if (r4 != r6) goto L_0x00bb
            int r3 = r3 + 1
            if (r13 <= r3) goto L_0x00b6
            r20 = 1
            goto L_0x00b8
        L_0x00b6:
            r20 = 0
        L_0x00b8:
            r4 = r20
            goto L_0x00c0
        L_0x00bb:
            r4 = 1
            if (r9 <= r4) goto L_0x00bf
            goto L_0x00c0
        L_0x00bf:
            r4 = 0
        L_0x00c0:
            if (r4 == 0) goto L_0x0123
            int r7 = r13 + -1
        L_0x00c4:
            if (r7 < r3) goto L_0x0123
            r15 = 0
        L_0x00c7:
            r22 = r15
            int r1 = r12.length
            r2 = r22
            if (r2 >= r1) goto L_0x011c
            r1 = r12[r2]
            byte r1 = r26[r1]
            if (r1 < r7) goto L_0x0113
            int r22 = r2 + 2
        L_0x00d6:
            r1 = r22
            r23 = r3
            int r3 = r12.length
            if (r1 >= r3) goto L_0x00e8
            r3 = r12[r1]
            byte r3 = r26[r3]
            if (r3 < r7) goto L_0x00e8
            int r22 = r1 + 2
            r3 = r23
            goto L_0x00d6
        L_0x00e8:
            r3 = r2
            int r15 = r1 + -2
        L_0x00eb:
            r24 = r15
            r0 = r24
            if (r3 >= r0) goto L_0x010e
            r15 = r12[r3]
            r16 = r12[r0]
            r12[r3] = r16
            r12[r0] = r15
            int r16 = r3 + 1
            r15 = r12[r16]
            int r16 = r3 + 1
            int r24 = r0 + 1
            r17 = r12[r24]
            r12[r16] = r17
            int r24 = r0 + 1
            r12[r24] = r15
            int r3 = r3 + 2
            int r15 = r0 + -2
            goto L_0x00eb
        L_0x010e:
            int r22 = r1 + 2
            r2 = r22
            goto L_0x0115
        L_0x0113:
            r23 = r3
        L_0x0115:
            int r15 = r2 + 2
            r3 = r23
            r2 = r30
            goto L_0x00c7
        L_0x011c:
            r23 = r3
            int r7 = r7 + -1
            r2 = r30
            goto L_0x00c4
        L_0x0123:
            r23 = r3
            android.text.Layout$Directions r0 = new android.text.Layout$Directions
            r0.<init>(r12)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.AndroidBidi.directions(int, byte[], int, char[], int, int):android.text.Layout$Directions");
    }
}
