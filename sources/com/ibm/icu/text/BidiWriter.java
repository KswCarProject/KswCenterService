package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;

final class BidiWriter {
    static final char LRM_CHAR = '‎';
    static final int MASK_R_AL = 8194;
    static final char RLM_CHAR = '‏';

    BidiWriter() {
    }

    private static boolean IsCombining(int type) {
        return ((1 << type) & 448) != 0;
    }

    private static String doWriteForward(String src, int options) {
        int i = options & 10;
        if (i == 0) {
            return src;
        }
        int c = 0;
        if (i == 2) {
            StringBuffer dest = new StringBuffer(src.length());
            while (true) {
                int i2 = c;
                int c2 = UTF16.charAt(src, i2);
                int i3 = i2 + UTF16.getCharCount(c2);
                UTF16.append(dest, UCharacter.getMirror(c2));
                if (i3 >= src.length()) {
                    return dest.toString();
                }
                c = i3;
            }
        } else if (i != 8) {
            StringBuffer dest2 = new StringBuffer(src.length());
            while (true) {
                int i4 = c;
                int c3 = UTF16.charAt(src, i4);
                int i5 = i4 + UTF16.getCharCount(c3);
                if (!Bidi.IsBidiControlChar(c3)) {
                    UTF16.append(dest2, UCharacter.getMirror(c3));
                }
                if (i5 >= src.length()) {
                    return dest2.toString();
                }
                c = i5;
            }
        } else {
            StringBuilder dest3 = new StringBuilder(src.length());
            do {
                int i6 = c;
                c = i6 + 1;
                char c4 = src.charAt(i6);
                if (!Bidi.IsBidiControlChar(c4)) {
                    dest3.append(c4);
                }
            } while (c < src.length());
            return dest3.toString();
        }
    }

    private static String doWriteForward(char[] text, int start, int limit, int options) {
        return doWriteForward(new String(text, start, limit - start), options);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a A[LOOP:0: B:4:0x0017->B:11:0x003a, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a4 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String writeReverse(java.lang.String r6, int r7) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            int r1 = r6.length()
            r0.<init>(r1)
            r1 = r7 & 11
            switch(r1) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0013;
                default: goto L_0x000e;
            }
        L_0x000e:
            int r1 = r6.length()
            goto L_0x0056
        L_0x0013:
            int r1 = r6.length()
        L_0x0017:
            r2 = r1
        L_0x0018:
            int r3 = r2 + -1
            int r3 = com.ibm.icu.text.UTF16.charAt((java.lang.String) r6, (int) r3)
            int r4 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r2 = r2 - r4
            if (r2 <= 0) goto L_0x002f
            int r4 = com.ibm.icu.lang.UCharacter.getType(r3)
            boolean r4 = IsCombining(r4)
            if (r4 != 0) goto L_0x0018
        L_0x002f:
            java.lang.String r4 = r6.substring(r2, r1)
            r0.append(r4)
            if (r2 > 0) goto L_0x003a
            goto L_0x00a4
        L_0x003a:
            r1 = r2
            goto L_0x0017
        L_0x003c:
            int r1 = r6.length()
        L_0x0040:
            r2 = r1
            int r3 = r1 + -1
            int r3 = com.ibm.icu.text.UTF16.charAt((java.lang.String) r6, (int) r3)
            int r3 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r1 = r1 - r3
            java.lang.String r3 = r6.substring(r1, r2)
            r0.append(r3)
            if (r1 > 0) goto L_0x0040
            goto L_0x00a4
        L_0x0056:
            r2 = r1
            int r3 = r1 + -1
            int r3 = com.ibm.icu.text.UTF16.charAt((java.lang.String) r6, (int) r3)
            int r4 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r1 = r1 - r4
            r4 = r7 & 1
            if (r4 == 0) goto L_0x007f
        L_0x0067:
            if (r1 <= 0) goto L_0x007f
            int r4 = com.ibm.icu.lang.UCharacter.getType(r3)
            boolean r4 = IsCombining(r4)
            if (r4 == 0) goto L_0x007f
            int r4 = r1 + -1
            int r3 = com.ibm.icu.text.UTF16.charAt((java.lang.String) r6, (int) r4)
            int r4 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r1 = r1 - r4
            goto L_0x0067
        L_0x007f:
            r4 = r7 & 8
            if (r4 == 0) goto L_0x008a
            boolean r4 = com.ibm.icu.text.Bidi.IsBidiControlChar(r3)
            if (r4 == 0) goto L_0x008a
            goto L_0x00a2
        L_0x008a:
            r4 = r1
            r5 = r7 & 2
            if (r5 == 0) goto L_0x009b
            int r3 = com.ibm.icu.lang.UCharacter.getMirror(r3)
            com.ibm.icu.text.UTF16.append(r0, r3)
            int r5 = com.ibm.icu.text.UTF16.getCharCount(r3)
            int r4 = r4 + r5
        L_0x009b:
            java.lang.String r5 = r6.substring(r4, r2)
            r0.append(r5)
        L_0x00a2:
            if (r1 > 0) goto L_0x0056
        L_0x00a4:
            java.lang.String r1 = r0.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.BidiWriter.writeReverse(java.lang.String, int):java.lang.String");
    }

    static String doWriteReverse(char[] text, int start, int limit, int options) {
        return writeReverse(new String(text, start, limit - start), options);
    }

    static String writeReordered(Bidi bidi, int options) {
        char uc;
        char uc2;
        char uc3;
        char uc4;
        char[] text = bidi.text;
        int runCount = bidi.countRuns();
        if ((bidi.reorderingOptions & 1) != 0) {
            options = (options | 4) & -9;
        }
        if ((bidi.reorderingOptions & 2) != 0) {
            options = (options | 8) & -5;
        }
        if (!(bidi.reorderingMode == 4 || bidi.reorderingMode == 5 || bidi.reorderingMode == 6 || bidi.reorderingMode == 3)) {
            options &= -5;
        }
        StringBuilder dest = new StringBuilder((options & 4) != 0 ? bidi.length * 2 : bidi.length);
        if ((options & 16) == 0) {
            int run = 0;
            if ((options & 4) == 0) {
                while (true) {
                    int run2 = run;
                    if (run2 >= runCount) {
                        break;
                    }
                    BidiRun bidiRun = bidi.getVisualRun(run2);
                    if (bidiRun.isEvenRun()) {
                        dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options & -3));
                    } else {
                        dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options));
                    }
                    run = run2 + 1;
                }
            } else {
                byte[] dirProps = bidi.dirProps;
                int run3 = 0;
                while (run3 < runCount) {
                    BidiRun bidiRun2 = bidi.getVisualRun(run3);
                    int markFlag = bidi.runs[run3].insertRemove;
                    if (markFlag < 0) {
                        markFlag = 0;
                    }
                    if (bidiRun2.isEvenRun()) {
                        if (bidi.isInverse() && dirProps[bidiRun2.start] != 0) {
                            markFlag |= 1;
                        }
                        if ((markFlag & 1) != 0) {
                            uc3 = LRM_CHAR;
                        } else if ((markFlag & 4) != 0) {
                            uc3 = RLM_CHAR;
                        } else {
                            uc3 = 0;
                        }
                        if (uc3 != 0) {
                            dest.append(uc3);
                        }
                        dest.append(doWriteForward(text, bidiRun2.start, bidiRun2.limit, options & -3));
                        if (bidi.isInverse() && dirProps[bidiRun2.limit - 1] != 0) {
                            markFlag |= 2;
                        }
                        if ((markFlag & 2) != 0) {
                            uc4 = LRM_CHAR;
                        } else if ((markFlag & 8) != 0) {
                            uc4 = RLM_CHAR;
                        } else {
                            uc4 = 0;
                        }
                        if (uc4 != 0) {
                            dest.append(uc4);
                        }
                    } else {
                        if (bidi.isInverse() != 0 && !bidi.testDirPropFlagAt(8194, bidiRun2.limit - 1)) {
                            markFlag |= 4;
                        }
                        if ((markFlag & 1) != 0) {
                            uc = LRM_CHAR;
                        } else if ((markFlag & 4) != 0) {
                            uc = RLM_CHAR;
                        } else {
                            uc = 0;
                        }
                        if (uc != 0) {
                            dest.append(uc);
                        }
                        dest.append(doWriteReverse(text, bidiRun2.start, bidiRun2.limit, options));
                        if (bidi.isInverse() && (Bidi.DirPropFlag(dirProps[bidiRun2.start]) & 8194) == 0) {
                            markFlag |= 8;
                        }
                        if ((markFlag & 2) != 0) {
                            uc2 = LRM_CHAR;
                        } else if ((markFlag & 8) != 0) {
                            uc2 = RLM_CHAR;
                        } else {
                            uc2 = 0;
                        }
                        if (uc2 != 0) {
                            dest.append(uc2);
                        }
                    }
                    run3++;
                }
                int i = run3;
            }
        } else if ((options & 4) == 0) {
            int run4 = runCount;
            while (true) {
                run4--;
                if (run4 < 0) {
                    break;
                }
                BidiRun bidiRun3 = bidi.getVisualRun(run4);
                if (bidiRun3.isEvenRun()) {
                    dest.append(doWriteReverse(text, bidiRun3.start, bidiRun3.limit, options & -3));
                } else {
                    dest.append(doWriteForward(text, bidiRun3.start, bidiRun3.limit, options));
                }
            }
        } else {
            byte[] dirProps2 = bidi.dirProps;
            int run5 = runCount;
            while (true) {
                run5--;
                if (run5 < 0) {
                    break;
                }
                BidiRun bidiRun4 = bidi.getVisualRun(run5);
                if (bidiRun4.isEvenRun()) {
                    if (dirProps2[bidiRun4.limit - 1] != 0) {
                        dest.append(LRM_CHAR);
                    }
                    dest.append(doWriteReverse(text, bidiRun4.start, bidiRun4.limit, options & -3));
                    if (dirProps2[bidiRun4.start] != 0) {
                        dest.append(LRM_CHAR);
                    }
                } else {
                    if ((Bidi.DirPropFlag(dirProps2[bidiRun4.start]) & 8194) == 0) {
                        dest.append(RLM_CHAR);
                    }
                    dest.append(doWriteForward(text, bidiRun4.start, bidiRun4.limit, options));
                    if ((Bidi.DirPropFlag(dirProps2[bidiRun4.limit - 1]) & 8194) == 0) {
                        dest.append(RLM_CHAR);
                    }
                }
            }
            int i2 = run5;
        }
        return dest.toString();
    }
}
