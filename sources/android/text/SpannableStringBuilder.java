package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.net.wifi.WifiEnterpriseConfig;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import libcore.util.EmptyArray;

public class SpannableStringBuilder implements CharSequence, GetChars, Spannable, Editable, Appendable, GraphicsOperations {
    private static final int END_MASK = 15;
    private static final int MARK = 1;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int PARAGRAPH = 3;
    private static final int POINT = 2;
    private static final int SPAN_ADDED = 2048;
    private static final int SPAN_END_AT_END = 32768;
    private static final int SPAN_END_AT_START = 16384;
    private static final int SPAN_START_AT_END = 8192;
    private static final int SPAN_START_AT_START = 4096;
    private static final int SPAN_START_END_MASK = 61440;
    private static final int START_MASK = 240;
    private static final int START_SHIFT = 4;
    private static final String TAG = "SpannableStringBuilder";
    @GuardedBy({"sCachedIntBuffer"})
    private static final int[][] sCachedIntBuffer = ((int[][]) Array.newInstance(int.class, new int[]{6, 0}));
    private InputFilter[] mFilters;
    @UnsupportedAppUsage
    private int mGapLength;
    @UnsupportedAppUsage
    private int mGapStart;
    private IdentityHashMap<Object, Integer> mIndexOfSpan;
    private int mLowWaterMark;
    @UnsupportedAppUsage
    private int mSpanCount;
    @UnsupportedAppUsage
    private int[] mSpanEnds;
    @UnsupportedAppUsage
    private int[] mSpanFlags;
    private int mSpanInsertCount;
    private int[] mSpanMax;
    private int[] mSpanOrder;
    @UnsupportedAppUsage
    private int[] mSpanStarts;
    @UnsupportedAppUsage
    private Object[] mSpans;
    @UnsupportedAppUsage
    private char[] mText;
    private int mTextWatcherDepth;

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(CharSequence text) {
        this(text, 0, text.length());
    }

    public SpannableStringBuilder(CharSequence text, int start, int end) {
        int en;
        CharSequence charSequence = text;
        int i = start;
        int i2 = end;
        this.mFilters = NO_FILTERS;
        int srclen = i2 - i;
        if (srclen >= 0) {
            this.mText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(srclen));
            this.mGapStart = srclen;
            this.mGapLength = this.mText.length - srclen;
            int i3 = 0;
            TextUtils.getChars(charSequence, i, i2, this.mText, 0);
            this.mSpanCount = 0;
            this.mSpanInsertCount = 0;
            this.mSpans = EmptyArray.OBJECT;
            this.mSpanStarts = EmptyArray.INT;
            this.mSpanEnds = EmptyArray.INT;
            this.mSpanFlags = EmptyArray.INT;
            this.mSpanMax = EmptyArray.INT;
            this.mSpanOrder = EmptyArray.INT;
            if (charSequence instanceof Spanned) {
                Spanned sp = (Spanned) charSequence;
                Object[] spans = sp.getSpans(i, i2, Object.class);
                while (true) {
                    int i4 = i3;
                    if (i4 < spans.length) {
                        if (!(spans[i4] instanceof NoCopySpan)) {
                            int st = sp.getSpanStart(spans[i4]) - i;
                            int en2 = sp.getSpanEnd(spans[i4]) - i;
                            int fl = sp.getSpanFlags(spans[i4]);
                            st = st < 0 ? 0 : st;
                            int st2 = st > i2 - i ? i2 - i : st;
                            en2 = en2 < 0 ? 0 : en2;
                            if (en2 > i2 - i) {
                                en = i2 - i;
                            } else {
                                en = en2;
                            }
                            setSpan(false, spans[i4], st2, en, fl, false);
                        }
                        i3 = i4 + 1;
                    } else {
                        restoreInvariants();
                        return;
                    }
                }
            }
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    public static SpannableStringBuilder valueOf(CharSequence source) {
        if (source instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder) source;
        }
        return new SpannableStringBuilder(source);
    }

    public char charAt(int where) {
        int len = length();
        if (where < 0) {
            throw new IndexOutOfBoundsException("charAt: " + where + " < 0");
        } else if (where >= len) {
            throw new IndexOutOfBoundsException("charAt: " + where + " >= length " + len);
        } else if (where >= this.mGapStart) {
            return this.mText[this.mGapLength + where];
        } else {
            return this.mText[where];
        }
    }

    public int length() {
        return this.mText.length - this.mGapLength;
    }

    private void resizeFor(int size) {
        int oldLength = this.mText.length;
        if (size + 1 > oldLength) {
            char[] newText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(size));
            System.arraycopy(this.mText, 0, newText, 0, this.mGapStart);
            int newLength = newText.length;
            int delta = newLength - oldLength;
            int after = oldLength - (this.mGapStart + this.mGapLength);
            System.arraycopy(this.mText, oldLength - after, newText, newLength - after, after);
            this.mText = newText;
            this.mGapLength += delta;
            if (this.mGapLength < 1) {
                new Exception("mGapLength < 1").printStackTrace();
            }
            if (this.mSpanCount != 0) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    if (this.mSpanStarts[i] > this.mGapStart) {
                        int[] iArr = this.mSpanStarts;
                        iArr[i] = iArr[i] + delta;
                    }
                    if (this.mSpanEnds[i] > this.mGapStart) {
                        int[] iArr2 = this.mSpanEnds;
                        iArr2[i] = iArr2[i] + delta;
                    }
                }
                calcMax(treeRoot());
            }
        }
    }

    private void moveGapTo(int where) {
        int flag;
        int flag2;
        if (where != this.mGapStart) {
            boolean atEnd = where == length();
            if (where < this.mGapStart) {
                int overlap = this.mGapStart - where;
                System.arraycopy(this.mText, where, this.mText, (this.mGapStart + this.mGapLength) - overlap, overlap);
            } else {
                int overlap2 = where - this.mGapStart;
                System.arraycopy(this.mText, (this.mGapLength + where) - overlap2, this.mText, this.mGapStart, overlap2);
            }
            if (this.mSpanCount != 0) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    int start = this.mSpanStarts[i];
                    int end = this.mSpanEnds[i];
                    if (start > this.mGapStart) {
                        start -= this.mGapLength;
                    }
                    if (start > where) {
                        start += this.mGapLength;
                    } else if (start == where && ((flag2 = (this.mSpanFlags[i] & 240) >> 4) == 2 || (atEnd && flag2 == 3))) {
                        start += this.mGapLength;
                    }
                    if (end > this.mGapStart) {
                        end -= this.mGapLength;
                    }
                    if (end > where) {
                        end += this.mGapLength;
                    } else if (end == where && ((flag = this.mSpanFlags[i] & 15) == 2 || (atEnd && flag == 3))) {
                        end += this.mGapLength;
                    }
                    this.mSpanStarts[i] = start;
                    this.mSpanEnds[i] = end;
                }
                calcMax(treeRoot());
            }
            this.mGapStart = where;
        }
    }

    public SpannableStringBuilder insert(int where, CharSequence tb, int start, int end) {
        return replace(where, where, tb, start, end);
    }

    public SpannableStringBuilder insert(int where, CharSequence tb) {
        return replace(where, where, tb, 0, tb.length());
    }

    public SpannableStringBuilder delete(int start, int end) {
        SpannableStringBuilder ret = replace(start, end, (CharSequence) "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return ret;
    }

    public void clear() {
        replace(0, length(), (CharSequence) "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    public void clearSpans() {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            Object what = this.mSpans[i];
            int ostart = this.mSpanStarts[i];
            int oend = this.mSpanEnds[i];
            if (ostart > this.mGapStart) {
                ostart -= this.mGapLength;
            }
            if (oend > this.mGapStart) {
                oend -= this.mGapLength;
            }
            this.mSpanCount = i;
            this.mSpans[i] = null;
            sendSpanRemoved(what, ostart, oend);
        }
        if (this.mIndexOfSpan != null) {
            this.mIndexOfSpan.clear();
        }
        this.mSpanInsertCount = 0;
    }

    public SpannableStringBuilder append(CharSequence text) {
        int length = length();
        return replace(length, length, text, 0, text.length());
    }

    public SpannableStringBuilder append(CharSequence text, Object what, int flags) {
        int start = length();
        append(text);
        setSpan(what, start, length(), flags);
        return this;
    }

    public SpannableStringBuilder append(CharSequence text, int start, int end) {
        int length = length();
        return replace(length, length, text, start, end);
    }

    public SpannableStringBuilder append(char text) {
        return append((CharSequence) String.valueOf(text));
    }

    private boolean removeSpansForChange(int start, int end, boolean textIsRemoved, int i) {
        if ((i & 1) != 0 && resolveGap(this.mSpanMax[i]) >= start && removeSpansForChange(start, end, textIsRemoved, leftChild(i))) {
            return true;
        }
        if (i >= this.mSpanCount) {
            return false;
        }
        if ((this.mSpanFlags[i] & 33) == 33 && this.mSpanStarts[i] >= start && this.mSpanStarts[i] < this.mGapStart + this.mGapLength && this.mSpanEnds[i] >= start && this.mSpanEnds[i] < this.mGapStart + this.mGapLength && (textIsRemoved || this.mSpanStarts[i] > start || this.mSpanEnds[i] < this.mGapStart)) {
            this.mIndexOfSpan.remove(this.mSpans[i]);
            removeSpan(i, 0);
            return true;
        } else if (resolveGap(this.mSpanStarts[i]) > end || (i & 1) == 0 || !removeSpansForChange(start, end, textIsRemoved, rightChild(i))) {
            return false;
        } else {
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x00e9 A[LOOP:3: B:58:0x00e9->B:61:0x00f5, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void change(int r26, int r27, java.lang.CharSequence r28, int r29, int r30) {
        /*
            r25 = this;
            r13 = r25
            r14 = r26
            r15 = r27
            r12 = r28
            r11 = r29
            r10 = r30
            int r16 = r15 - r14
            int r17 = r10 - r11
            int r9 = r17 - r16
            r0 = 0
            int r1 = r13.mSpanCount
            r7 = 1
            int r1 = r1 - r7
            r18 = r0
        L_0x0019:
            r8 = r1
            if (r8 < 0) goto L_0x00c8
            int[] r0 = r13.mSpanStarts
            r0 = r0[r8]
            int r1 = r13.mGapStart
            if (r0 <= r1) goto L_0x0027
            int r1 = r13.mGapLength
            int r0 = r0 - r1
        L_0x0027:
            int[] r1 = r13.mSpanEnds
            r1 = r1[r8]
            int r2 = r13.mGapStart
            if (r1 <= r2) goto L_0x0032
            int r2 = r13.mGapLength
            int r1 = r1 - r2
        L_0x0032:
            int[] r2 = r13.mSpanFlags
            r2 = r2[r8]
            r3 = 51
            r2 = r2 & r3
            if (r2 != r3) goto L_0x00a4
            r6 = r0
            r5 = r1
            int r4 = r25.length()
            r2 = 10
            if (r0 <= r14) goto L_0x0059
            if (r0 > r15) goto L_0x0059
            r0 = r27
        L_0x0049:
            if (r0 >= r4) goto L_0x0059
            if (r0 <= r15) goto L_0x0056
            int r3 = r0 + -1
            char r3 = r13.charAt(r3)
            if (r3 != r2) goto L_0x0056
            goto L_0x0059
        L_0x0056:
            int r0 = r0 + 1
            goto L_0x0049
        L_0x0059:
            r3 = r0
            if (r1 <= r14) goto L_0x0072
            if (r1 > r15) goto L_0x0072
            r0 = r27
        L_0x0060:
            if (r0 >= r4) goto L_0x0070
            if (r0 <= r15) goto L_0x006d
            int r1 = r0 + -1
            char r1 = r13.charAt(r1)
            if (r1 != r2) goto L_0x006d
            goto L_0x0070
        L_0x006d:
            int r0 = r0 + 1
            goto L_0x0060
        L_0x0070:
            r2 = r0
            goto L_0x0073
        L_0x0072:
            r2 = r1
        L_0x0073:
            if (r3 != r6) goto L_0x007b
            if (r2 == r5) goto L_0x0078
            goto L_0x007b
        L_0x0078:
            r1 = r2
            r0 = r3
            goto L_0x00a4
        L_0x007b:
            r1 = 0
            java.lang.Object[] r0 = r13.mSpans
            r19 = r0[r8]
            int[] r0 = r13.mSpanFlags
            r20 = r0[r8]
            r21 = 1
            r0 = r25
            r22 = r2
            r2 = r19
            r19 = r3
            r23 = r4
            r4 = r22
            r24 = r5
            r5 = r20
            r20 = r6
            r6 = r21
            r0.setSpan(r1, r2, r3, r4, r5, r6)
            r0 = 1
            r18 = r0
            r0 = r19
            r1 = r22
        L_0x00a4:
            r2 = 0
            if (r0 != r14) goto L_0x00aa
            r2 = r2 | 4096(0x1000, float:5.74E-42)
            goto L_0x00b0
        L_0x00aa:
            int r3 = r15 + r9
            if (r0 != r3) goto L_0x00b0
            r2 = r2 | 8192(0x2000, float:1.14794E-41)
        L_0x00b0:
            if (r1 != r14) goto L_0x00b5
            r2 = r2 | 16384(0x4000, float:2.2959E-41)
            goto L_0x00bd
        L_0x00b5:
            int r3 = r15 + r9
            if (r1 != r3) goto L_0x00bd
            r3 = 32768(0x8000, float:4.5918E-41)
            r2 = r2 | r3
        L_0x00bd:
            int[] r3 = r13.mSpanFlags
            r4 = r3[r8]
            r4 = r4 | r2
            r3[r8] = r4
            int r1 = r8 + -1
            goto L_0x0019
        L_0x00c8:
            if (r18 == 0) goto L_0x00cd
            r25.restoreInvariants()
        L_0x00cd:
            r13.moveGapTo(r15)
            int r0 = r13.mGapLength
            if (r9 < r0) goto L_0x00de
            char[] r0 = r13.mText
            int r0 = r0.length
            int r0 = r0 + r9
            int r1 = r13.mGapLength
            int r0 = r0 - r1
            r13.resizeFor(r0)
        L_0x00de:
            r19 = 0
            if (r17 != 0) goto L_0x00e4
            r0 = r7
            goto L_0x00e6
        L_0x00e4:
            r0 = r19
        L_0x00e6:
            r8 = r0
            if (r16 <= 0) goto L_0x00f8
        L_0x00e9:
            int r0 = r13.mSpanCount
            if (r0 <= 0) goto L_0x00f8
            int r0 = r25.treeRoot()
            boolean r0 = r13.removeSpansForChange(r14, r15, r8, r0)
            if (r0 == 0) goto L_0x00f8
            goto L_0x00e9
        L_0x00f8:
            int r0 = r13.mGapStart
            int r0 = r0 + r9
            r13.mGapStart = r0
            int r0 = r13.mGapLength
            int r0 = r0 - r9
            r13.mGapLength = r0
            int r0 = r13.mGapLength
            if (r0 >= r7) goto L_0x0110
            java.lang.Exception r0 = new java.lang.Exception
            java.lang.String r1 = "mGapLength < 1"
            r0.<init>(r1)
            r0.printStackTrace()
        L_0x0110:
            char[] r0 = r13.mText
            android.text.TextUtils.getChars(r12, r11, r10, r0, r14)
            if (r16 <= 0) goto L_0x0182
            int r0 = r13.mGapStart
            int r1 = r13.mGapLength
            int r0 = r0 + r1
            char[] r1 = r13.mText
            int r1 = r1.length
            if (r0 != r1) goto L_0x0123
            r5 = r7
            goto L_0x0125
        L_0x0123:
            r5 = r19
        L_0x0125:
            r0 = r19
        L_0x0127:
            r7 = r0
            int r0 = r13.mSpanCount
            if (r7 >= r0) goto L_0x0177
            int[] r0 = r13.mSpanFlags
            r0 = r0[r7]
            r0 = r0 & 240(0xf0, float:3.36E-43)
            int r20 = r0 >> 4
            int[] r6 = r13.mSpanStarts
            int[] r0 = r13.mSpanStarts
            r1 = r0[r7]
            r0 = r25
            r2 = r26
            r3 = r9
            r4 = r20
            r21 = r6
            r6 = r8
            int r0 = r0.updatedIntervalBound(r1, r2, r3, r4, r5, r6)
            r21[r7] = r0
            int[] r0 = r13.mSpanFlags
            r0 = r0[r7]
            r0 = r0 & 15
            int[] r1 = r13.mSpanEnds
            int[] r2 = r13.mSpanEnds
            r2 = r2[r7]
            r6 = r25
            r3 = r7
            r7 = r2
            r21 = r8
            r8 = r26
            r22 = r9
            r4 = r10
            r10 = r0
            r2 = r11
            r11 = r5
            r15 = r12
            r12 = r21
            int r6 = r6.updatedIntervalBound(r7, r8, r9, r10, r11, r12)
            r1[r3] = r6
            int r0 = r3 + 1
            r11 = r2
            r10 = r4
            r12 = r15
            r8 = r21
            r15 = r27
            goto L_0x0127
        L_0x0177:
            r21 = r8
            r22 = r9
            r4 = r10
            r2 = r11
            r15 = r12
            r25.restoreInvariants()
            goto L_0x0189
        L_0x0182:
            r21 = r8
            r22 = r9
            r4 = r10
            r2 = r11
            r15 = r12
        L_0x0189:
            boolean r0 = r15 instanceof android.text.Spanned
            if (r0 == 0) goto L_0x01e7
            r7 = r15
            android.text.Spanned r7 = (android.text.Spanned) r7
            java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
            java.lang.Object[] r8 = r7.getSpans(r2, r4, r0)
        L_0x0197:
            r9 = r19
            int r0 = r8.length
            if (r9 >= r0) goto L_0x01e4
            r0 = r8[r9]
            int r0 = r7.getSpanStart(r0)
            r1 = r8[r9]
            int r1 = r7.getSpanEnd(r1)
            if (r0 >= r2) goto L_0x01ac
            r0 = r29
        L_0x01ac:
            r10 = r0
            if (r1 <= r4) goto L_0x01b1
            r1 = r30
        L_0x01b1:
            r11 = r1
            r0 = r8[r9]
            int r0 = r13.getSpanStart(r0)
            if (r0 >= 0) goto L_0x01dd
            int r0 = r10 - r2
            int r12 = r0 + r14
            int r0 = r11 - r2
            int r19 = r0 + r14
            r0 = r8[r9]
            int r0 = r7.getSpanFlags(r0)
            r6 = r0 | 2048(0x800, float:2.87E-42)
            r1 = 0
            r3 = r8[r9]
            r20 = 0
            r0 = r25
            r2 = r3
            r3 = r12
            r4 = r19
            r5 = r6
            r23 = r6
            r6 = r20
            r0.setSpan(r1, r2, r3, r4, r5, r6)
        L_0x01dd:
            int r19 = r9 + 1
            r2 = r29
            r4 = r30
            goto L_0x0197
        L_0x01e4:
            r25.restoreInvariants()
        L_0x01e7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.change(int, int, java.lang.CharSequence, int, int):void");
    }

    private int updatedIntervalBound(int offset, int start, int nbNewChars, int flag, boolean atEnd, boolean textIsRemoved) {
        if (offset >= start && offset < this.mGapStart + this.mGapLength) {
            if (flag == 2) {
                if (textIsRemoved || offset > start) {
                    return this.mGapStart + this.mGapLength;
                }
            } else if (flag == 3) {
                if (atEnd) {
                    return this.mGapStart + this.mGapLength;
                }
            } else if (textIsRemoved || offset < this.mGapStart - nbNewChars) {
                return start;
            } else {
                return this.mGapStart;
            }
        }
        return offset;
    }

    private void removeSpan(int i, int flags) {
        Object object = this.mSpans[i];
        int start = this.mSpanStarts[i];
        int end = this.mSpanEnds[i];
        if (start > this.mGapStart) {
            start -= this.mGapLength;
        }
        if (end > this.mGapStart) {
            end -= this.mGapLength;
        }
        int count = this.mSpanCount - (i + 1);
        System.arraycopy(this.mSpans, i + 1, this.mSpans, i, count);
        System.arraycopy(this.mSpanStarts, i + 1, this.mSpanStarts, i, count);
        System.arraycopy(this.mSpanEnds, i + 1, this.mSpanEnds, i, count);
        System.arraycopy(this.mSpanFlags, i + 1, this.mSpanFlags, i, count);
        System.arraycopy(this.mSpanOrder, i + 1, this.mSpanOrder, i, count);
        this.mSpanCount--;
        invalidateIndex(i);
        this.mSpans[this.mSpanCount] = null;
        restoreInvariants();
        if ((flags & 512) == 0) {
            sendSpanRemoved(object, start, end);
        }
    }

    public SpannableStringBuilder replace(int start, int end, CharSequence tb) {
        return replace(start, end, tb, 0, tb.length());
    }

    public SpannableStringBuilder replace(int start, int end, CharSequence tb, int tbstart, int tbend) {
        int newLen;
        int selectionEnd;
        int i = start;
        int i2 = end;
        checkRange("replace", i, i2);
        int filtercount = this.mFilters.length;
        boolean adjustSelection = false;
        CharSequence tb2 = tb;
        int tbstart2 = tbstart;
        int tbend2 = tbend;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= filtercount) {
                break;
            }
            CharSequence repl = this.mFilters[i4].filter(tb2, tbstart2, tbend2, this, start, end);
            if (repl != null) {
                tbend2 = repl.length();
                tb2 = repl;
                tbstart2 = 0;
            }
            i3 = i4 + 1;
        }
        int origLen = i2 - i;
        int newLen2 = tbend2 - tbstart2;
        if (origLen == 0 && newLen2 == 0 && !hasNonExclusiveExclusiveSpanAt(tb2, tbstart2)) {
            return this;
        }
        TextWatcher[] textWatchers = (TextWatcher[]) getSpans(i, i + origLen, TextWatcher.class);
        sendBeforeTextChanged(textWatchers, i, origLen, newLen2);
        if (!(origLen == 0 || newLen2 == 0)) {
            adjustSelection = true;
        }
        int selectionStart = 0;
        int selectionEnd2 = 0;
        if (adjustSelection) {
            selectionStart = Selection.getSelectionStart(this);
            selectionEnd2 = Selection.getSelectionEnd(this);
        }
        int i5 = filtercount;
        int selectionEnd3 = selectionEnd2;
        CharSequence charSequence = tb2;
        CharSequence charSequence2 = tb2;
        int selectionStart2 = selectionStart;
        int selectionStart3 = tbstart2;
        int i6 = tbstart2;
        TextWatcher[] textWatchers2 = textWatchers;
        change(start, end, charSequence, selectionStart3, tbend2);
        if (adjustSelection) {
            boolean changed = false;
            if (selectionStart2 <= i || selectionStart2 >= i2) {
                newLen = newLen2;
            } else {
                long diff = (long) (selectionStart2 - i);
                long j = diff;
                int selectionStart4 = i + Math.toIntExact((((long) newLen2) * diff) / ((long) origLen));
                boolean z = adjustSelection;
                newLen = newLen2;
                setSpan(false, Selection.SELECTION_START, selectionStart4, selectionStart4, 34, true);
                changed = true;
            }
            if (selectionEnd3 <= i || selectionEnd3 >= i2) {
                selectionEnd = selectionEnd3;
            } else {
                long diff2 = (long) (selectionEnd3 - i);
                int selectionEnd4 = i + Math.toIntExact((((long) newLen) * diff2) / ((long) origLen));
                selectionEnd = selectionEnd4;
                long j2 = diff2;
                setSpan(false, Selection.SELECTION_END, selectionEnd, selectionEnd4, 34, true);
                changed = true;
            }
            if (changed) {
                restoreInvariants();
            }
            int i7 = selectionEnd;
        } else {
            newLen = newLen2;
        }
        sendTextChanged(textWatchers2, i, origLen, newLen);
        sendAfterTextChanged(textWatchers2);
        sendToSpanWatchers(i, i2, newLen - origLen);
        return this;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(CharSequence text, int offset) {
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            for (Object span : spanned.getSpans(offset, offset, Object.class)) {
                if (spanned.getSpanFlags(span) != 33) {
                    return true;
                }
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    private void sendToSpanWatchers(int replaceStart, int replaceEnd, int nbNewChars) {
        int i = replaceStart;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= this.mSpanCount) {
                break;
            }
            int spanFlags = this.mSpanFlags[i4];
            if ((spanFlags & 2048) == 0) {
                int spanStart = this.mSpanStarts[i4];
                int spanEnd = this.mSpanEnds[i4];
                if (spanStart > this.mGapStart) {
                    spanStart -= this.mGapLength;
                }
                int spanStart2 = spanStart;
                if (spanEnd > this.mGapStart) {
                    spanEnd -= this.mGapLength;
                }
                int spanEnd2 = spanEnd;
                int newReplaceEnd = replaceEnd + nbNewChars;
                boolean spanChanged = false;
                int previousSpanStart = spanStart2;
                if (spanStart2 > newReplaceEnd) {
                    if (nbNewChars != 0) {
                        previousSpanStart -= nbNewChars;
                        spanChanged = true;
                    }
                } else if (spanStart2 >= i && !((spanStart2 == i && (spanFlags & 4096) == 4096) || (spanStart2 == newReplaceEnd && (spanFlags & 8192) == 8192))) {
                    spanChanged = true;
                }
                int previousSpanStart2 = previousSpanStart;
                int previousSpanEnd = spanEnd2;
                if (spanEnd2 > newReplaceEnd) {
                    if (nbNewChars != 0) {
                        previousSpanEnd -= nbNewChars;
                        spanChanged = true;
                    }
                } else if (spanEnd2 >= i && !((spanEnd2 == i && (spanFlags & 16384) == 16384) || (spanEnd2 == newReplaceEnd && (spanFlags & 32768) == 32768))) {
                    spanChanged = true;
                }
                int previousSpanEnd2 = previousSpanEnd;
                if (spanChanged) {
                    sendSpanChanged(this.mSpans[i4], previousSpanStart2, previousSpanEnd2, spanStart2, spanEnd2);
                }
                int[] iArr = this.mSpanFlags;
                iArr[i4] = iArr[i4] & -61441;
            }
            i3 = i4 + 1;
        }
        while (true) {
            int i5 = i2;
            if (i5 < this.mSpanCount) {
                if ((this.mSpanFlags[i5] & 2048) != 0) {
                    int[] iArr2 = this.mSpanFlags;
                    iArr2[i5] = iArr2[i5] & -2049;
                    int spanStart3 = this.mSpanStarts[i5];
                    int spanEnd3 = this.mSpanEnds[i5];
                    if (spanStart3 > this.mGapStart) {
                        spanStart3 -= this.mGapLength;
                    }
                    if (spanEnd3 > this.mGapStart) {
                        spanEnd3 -= this.mGapLength;
                    }
                    sendSpanAdded(this.mSpans[i5], spanStart3, spanEnd3);
                }
                i2 = i5 + 1;
            } else {
                return;
            }
        }
    }

    public void setSpan(Object what, int start, int end, int flags) {
        setSpan(true, what, start, end, flags, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0119  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setSpan(boolean r20, java.lang.Object r21, int r22, int r23, int r24, boolean r25) {
        /*
            r19 = this;
            r6 = r19
            r7 = r21
            r0 = r22
            r1 = r23
            r8 = r24
            java.lang.String r2 = "setSpan"
            r6.checkRange(r2, r0, r1)
            r2 = r8 & 240(0xf0, float:3.36E-43)
            int r10 = r2 >> 4
            boolean r2 = r6.isInvalidParagraph(r0, r10)
            if (r2 == 0) goto L_0x0047
            if (r25 != 0) goto L_0x001d
            return
        L_0x001d:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "PARAGRAPH span must start at paragraph boundary ("
            r3.append(r4)
            r3.append(r0)
            java.lang.String r4 = " follows "
            r3.append(r4)
            int r4 = r0 + -1
            char r4 = r6.charAt(r4)
            r3.append(r4)
            java.lang.String r4 = ")"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0047:
            r11 = r8 & 15
            boolean r2 = r6.isInvalidParagraph(r1, r11)
            if (r2 == 0) goto L_0x007c
            if (r25 != 0) goto L_0x0052
            return
        L_0x0052:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "PARAGRAPH span must end at paragraph boundary ("
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = " follows "
            r3.append(r4)
            int r4 = r1 + -1
            char r4 = r6.charAt(r4)
            r3.append(r4)
            java.lang.String r4 = ")"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x007c:
            r2 = 2
            r3 = 1
            if (r10 != r2) goto L_0x008e
            if (r11 != r3) goto L_0x008e
            if (r0 != r1) goto L_0x008e
            if (r20 == 0) goto L_0x008d
            java.lang.String r2 = "SpannableStringBuilder"
            java.lang.String r3 = "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length"
            android.util.Log.e(r2, r3)
        L_0x008d:
            return
        L_0x008e:
            r12 = r22
            r13 = r23
            int r4 = r6.mGapStart
            r5 = 3
            if (r0 <= r4) goto L_0x009c
            int r4 = r6.mGapLength
            int r0 = r0 + r4
        L_0x009a:
            r14 = r0
            goto L_0x00ae
        L_0x009c:
            int r4 = r6.mGapStart
            if (r0 != r4) goto L_0x009a
            if (r10 == r2) goto L_0x00aa
            if (r10 != r5) goto L_0x009a
            int r4 = r19.length()
            if (r0 != r4) goto L_0x009a
        L_0x00aa:
            int r4 = r6.mGapLength
            int r0 = r0 + r4
            goto L_0x009a
        L_0x00ae:
            int r0 = r6.mGapStart
            if (r1 <= r0) goto L_0x00b7
            int r0 = r6.mGapLength
            int r0 = r0 + r1
        L_0x00b5:
            r5 = r0
            goto L_0x00ca
        L_0x00b7:
            int r0 = r6.mGapStart
            if (r1 != r0) goto L_0x00c9
            if (r11 == r2) goto L_0x00c5
            if (r11 != r5) goto L_0x00c9
            int r0 = r19.length()
            if (r1 != r0) goto L_0x00c9
        L_0x00c5:
            int r0 = r6.mGapLength
            int r0 = r0 + r1
            goto L_0x00b5
        L_0x00c9:
            r5 = r1
        L_0x00ca:
            java.util.IdentityHashMap<java.lang.Object, java.lang.Integer> r0 = r6.mIndexOfSpan
            if (r0 == 0) goto L_0x0119
            java.util.IdentityHashMap<java.lang.Object, java.lang.Integer> r0 = r6.mIndexOfSpan
            java.lang.Object r0 = r0.get(r7)
            r15 = r0
            java.lang.Integer r15 = (java.lang.Integer) r15
            if (r15 == 0) goto L_0x0119
            int r16 = r15.intValue()
            int[] r0 = r6.mSpanStarts
            r0 = r0[r16]
            int[] r1 = r6.mSpanEnds
            r1 = r1[r16]
            int r2 = r6.mGapStart
            if (r0 <= r2) goto L_0x00ec
            int r2 = r6.mGapLength
            int r0 = r0 - r2
        L_0x00ec:
            r17 = r0
            int r0 = r6.mGapStart
            if (r1 <= r0) goto L_0x00f5
            int r0 = r6.mGapLength
            int r1 = r1 - r0
        L_0x00f5:
            r18 = r1
            int[] r0 = r6.mSpanStarts
            r0[r16] = r14
            int[] r0 = r6.mSpanEnds
            r0[r16] = r5
            int[] r0 = r6.mSpanFlags
            r0[r16] = r8
            if (r20 == 0) goto L_0x0117
            r19.restoreInvariants()
            r0 = r19
            r1 = r21
            r2 = r17
            r3 = r18
            r4 = r12
            r9 = r5
            r5 = r13
            r0.sendSpanChanged(r1, r2, r3, r4, r5)
            goto L_0x0118
        L_0x0117:
            r9 = r5
        L_0x0118:
            return
        L_0x0119:
            r9 = r5
            java.lang.Object[] r0 = r6.mSpans
            int r1 = r6.mSpanCount
            java.lang.Object[] r0 = com.android.internal.util.GrowingArrayUtils.append((T[]) r0, (int) r1, r7)
            r6.mSpans = r0
            int[] r0 = r6.mSpanStarts
            int r1 = r6.mSpanCount
            int[] r0 = com.android.internal.util.GrowingArrayUtils.append((int[]) r0, (int) r1, (int) r14)
            r6.mSpanStarts = r0
            int[] r0 = r6.mSpanEnds
            int r1 = r6.mSpanCount
            int[] r0 = com.android.internal.util.GrowingArrayUtils.append((int[]) r0, (int) r1, (int) r9)
            r6.mSpanEnds = r0
            int[] r0 = r6.mSpanFlags
            int r1 = r6.mSpanCount
            int[] r0 = com.android.internal.util.GrowingArrayUtils.append((int[]) r0, (int) r1, (int) r8)
            r6.mSpanFlags = r0
            int[] r0 = r6.mSpanOrder
            int r1 = r6.mSpanCount
            int r4 = r6.mSpanInsertCount
            int[] r0 = com.android.internal.util.GrowingArrayUtils.append((int[]) r0, (int) r1, (int) r4)
            r6.mSpanOrder = r0
            int r0 = r6.mSpanCount
            r6.invalidateIndex(r0)
            int r0 = r6.mSpanCount
            int r0 = r0 + r3
            r6.mSpanCount = r0
            int r0 = r6.mSpanInsertCount
            int r0 = r0 + r3
            r6.mSpanInsertCount = r0
            int r0 = r19.treeRoot()
            int r0 = r0 * r2
            int r0 = r0 + r3
            int[] r1 = r6.mSpanMax
            int r1 = r1.length
            if (r1 >= r0) goto L_0x016c
            int[] r1 = new int[r0]
            r6.mSpanMax = r1
        L_0x016c:
            if (r20 == 0) goto L_0x0174
            r19.restoreInvariants()
            r6.sendSpanAdded(r7, r12, r13)
        L_0x0174:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.setSpan(boolean, java.lang.Object, int, int, int, boolean):void");
    }

    private boolean isInvalidParagraph(int index, int flag) {
        return (flag != 3 || index == 0 || index == length() || charAt(index + -1) == 10) ? false : true;
    }

    public void removeSpan(Object what) {
        removeSpan(what, 0);
    }

    public void removeSpan(Object what, int flags) {
        Integer i;
        if (this.mIndexOfSpan != null && (i = this.mIndexOfSpan.remove(what)) != null) {
            removeSpan(i.intValue(), flags);
        }
    }

    private int resolveGap(int i) {
        return i > this.mGapStart ? i - this.mGapLength : i;
    }

    public int getSpanStart(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanStarts[i.intValue()]);
    }

    public int getSpanEnd(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanEnds[i.intValue()]);
    }

    public int getSpanFlags(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return 0;
        }
        return this.mSpanFlags[i.intValue()];
    }

    public <T> T[] getSpans(int queryStart, int queryEnd, Class<T> kind) {
        return getSpans(queryStart, queryEnd, kind, true);
    }

    @UnsupportedAppUsage
    public <T> T[] getSpans(int queryStart, int queryEnd, Class<T> kind, boolean sortByInsertionOrder) {
        Class<T> cls = kind;
        if (cls == null) {
            return ArrayUtils.emptyArray(Object.class);
        }
        if (this.mSpanCount == 0) {
            return ArrayUtils.emptyArray(kind);
        }
        int count = countSpans(queryStart, queryEnd, cls, treeRoot());
        if (count == 0) {
            return ArrayUtils.emptyArray(kind);
        }
        T[] ret = (Object[]) Array.newInstance(cls, count);
        int[] prioSortBuffer = sortByInsertionOrder ? obtain(count) : EmptyArray.INT;
        int[] orderSortBuffer = sortByInsertionOrder ? obtain(count) : EmptyArray.INT;
        int[] orderSortBuffer2 = orderSortBuffer;
        int[] prioSortBuffer2 = prioSortBuffer;
        T[] ret2 = ret;
        getSpansRec(queryStart, queryEnd, kind, treeRoot(), ret, prioSortBuffer, orderSortBuffer, 0, sortByInsertionOrder);
        if (sortByInsertionOrder) {
            int[] orderSortBuffer3 = orderSortBuffer2;
            sort(ret2, prioSortBuffer2, orderSortBuffer3);
            recycle(prioSortBuffer2);
            recycle(orderSortBuffer3);
        }
        return ret2;
    }

    private int countSpans(int queryStart, int queryEnd, Class kind, int i) {
        int count = 0;
        if ((i & 1) != 0) {
            int left = leftChild(i);
            int spanMax = this.mSpanMax[left];
            if (spanMax > this.mGapStart) {
                spanMax -= this.mGapLength;
            }
            if (spanMax >= queryStart) {
                count = countSpans(queryStart, queryEnd, kind, left);
            }
        }
        if (i >= this.mSpanCount) {
            return count;
        }
        int spanStart = this.mSpanStarts[i];
        if (spanStart > this.mGapStart) {
            spanStart -= this.mGapLength;
        }
        if (spanStart > queryEnd) {
            return count;
        }
        int spanEnd = this.mSpanEnds[i];
        if (spanEnd > this.mGapStart) {
            spanEnd -= this.mGapLength;
        }
        if (spanEnd >= queryStart && ((spanStart == spanEnd || queryStart == queryEnd || !(spanStart == queryEnd || spanEnd == queryStart)) && (Object.class == kind || kind.isInstance(this.mSpans[i])))) {
            count++;
        }
        if ((i & 1) != 0) {
            return count + countSpans(queryStart, queryEnd, kind, rightChild(i));
        }
        return count;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> int getSpansRec(int r22, int r23, java.lang.Class<T> r24, int r25, T[] r26, int[] r27, int[] r28, int r29, boolean r30) {
        /*
            r21 = this;
            r10 = r21
            r11 = r22
            r12 = r23
            r13 = r24
            r14 = r25
            r15 = r26
            r0 = r14 & 1
            if (r0 == 0) goto L_0x003d
            int r16 = leftChild(r25)
            int[] r0 = r10.mSpanMax
            r0 = r0[r16]
            int r1 = r10.mGapStart
            if (r0 <= r1) goto L_0x001f
            int r1 = r10.mGapLength
            int r0 = r0 - r1
        L_0x001f:
            r9 = r0
            if (r9 < r11) goto L_0x003d
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r16
            r5 = r26
            r6 = r27
            r7 = r28
            r8 = r29
            r17 = r9
            r9 = r30
            int r0 = r0.getSpansRec(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x003f
        L_0x003d:
            r0 = r29
        L_0x003f:
            int r1 = r10.mSpanCount
            if (r14 < r1) goto L_0x0044
            return r0
        L_0x0044:
            int[] r1 = r10.mSpanStarts
            r1 = r1[r14]
            int r2 = r10.mGapStart
            if (r1 <= r2) goto L_0x004f
            int r2 = r10.mGapLength
            int r1 = r1 - r2
        L_0x004f:
            r9 = r1
            if (r9 > r12) goto L_0x00db
            int[] r1 = r10.mSpanEnds
            r1 = r1[r14]
            int r2 = r10.mGapStart
            if (r1 <= r2) goto L_0x005d
            int r2 = r10.mGapLength
            int r1 = r1 - r2
        L_0x005d:
            r8 = r1
            if (r8 < r11) goto L_0x00ab
            if (r9 == r8) goto L_0x0068
            if (r11 == r12) goto L_0x0068
            if (r9 == r12) goto L_0x00ab
            if (r8 == r11) goto L_0x00ab
        L_0x0068:
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            if (r1 == r13) goto L_0x0076
            java.lang.Object[] r1 = r10.mSpans
            r1 = r1[r14]
            boolean r1 = r13.isInstance(r1)
            if (r1 == 0) goto L_0x00ab
        L_0x0076:
            int[] r1 = r10.mSpanFlags
            r1 = r1[r14]
            r2 = 16711680(0xff0000, float:2.3418052E-38)
            r1 = r1 & r2
            r3 = r0
            if (r30 == 0) goto L_0x0089
            r27[r3] = r1
            int[] r2 = r10.mSpanOrder
            r2 = r2[r14]
            r28[r3] = r2
            goto L_0x00a3
        L_0x0089:
            if (r1 == 0) goto L_0x00a3
            r4 = 0
        L_0x008c:
            if (r4 >= r0) goto L_0x009b
            r5 = r15[r4]
            int r5 = r10.getSpanFlags(r5)
            r5 = r5 & r2
            if (r1 <= r5) goto L_0x0098
            goto L_0x009b
        L_0x0098:
            int r4 = r4 + 1
            goto L_0x008c
        L_0x009b:
            int r2 = r4 + 1
            int r5 = r0 - r4
            java.lang.System.arraycopy(r15, r4, r15, r2, r5)
            r3 = r4
        L_0x00a3:
            java.lang.Object[] r2 = r10.mSpans
            r2 = r2[r14]
            r15[r3] = r2
            int r0 = r0 + 1
        L_0x00ab:
            r7 = r0
            int r0 = r15.length
            if (r7 >= r0) goto L_0x00d4
            r0 = r14 & 1
            if (r0 == 0) goto L_0x00d4
            int r4 = rightChild(r25)
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r5 = r26
            r6 = r27
            r18 = r7
            r7 = r28
            r19 = r8
            r8 = r18
            r20 = r9
            r9 = r30
            int r0 = r0.getSpansRec(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x00dd
        L_0x00d4:
            r18 = r7
            r20 = r9
            r0 = r18
            goto L_0x00dd
        L_0x00db:
            r20 = r9
        L_0x00dd:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.getSpansRec(int, int, java.lang.Class, int, java.lang.Object[], int[], int[], int, boolean):int");
    }

    private static int[] obtain(int elementCount) {
        int[] result = null;
        synchronized (sCachedIntBuffer) {
            int candidateIndex = -1;
            int i = sCachedIntBuffer.length - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                if (sCachedIntBuffer[i] != null) {
                    if (sCachedIntBuffer[i].length >= elementCount) {
                        candidateIndex = i;
                        break;
                    } else if (candidateIndex == -1) {
                        candidateIndex = i;
                    }
                }
                i--;
            }
            if (candidateIndex != -1) {
                result = sCachedIntBuffer[candidateIndex];
                sCachedIntBuffer[candidateIndex] = null;
            }
        }
        return checkSortBuffer(result, elementCount);
    }

    private static void recycle(int[] buffer) {
        synchronized (sCachedIntBuffer) {
            int i = 0;
            while (true) {
                if (i >= sCachedIntBuffer.length) {
                    break;
                } else if (sCachedIntBuffer[i] == null) {
                    break;
                } else if (buffer.length > sCachedIntBuffer[i].length) {
                    break;
                } else {
                    i++;
                }
            }
            sCachedIntBuffer[i] = buffer;
        }
    }

    private static int[] checkSortBuffer(int[] buffer, int size) {
        if (buffer == null || size > buffer.length) {
            return ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(size));
        }
        return buffer;
    }

    private final <T> void sort(T[] array, int[] priority, int[] insertionOrder) {
        T[] tArr = array;
        int size = tArr.length;
        int i = (size / 2) - 1;
        while (true) {
            int i2 = i;
            if (i2 < 0) {
                break;
            }
            siftDown(i2, array, size, priority, insertionOrder);
            i = i2 - 1;
        }
        int i3 = size - 1;
        while (true) {
            int i4 = i3;
            if (i4 > 0) {
                T tmpSpan = tArr[0];
                tArr[0] = tArr[i4];
                tArr[i4] = tmpSpan;
                int tmpPriority = priority[0];
                priority[0] = priority[i4];
                priority[i4] = tmpPriority;
                int tmpOrder = insertionOrder[0];
                insertionOrder[0] = insertionOrder[i4];
                insertionOrder[i4] = tmpOrder;
                siftDown(0, array, i4, priority, insertionOrder);
                i3 = i4 - 1;
            } else {
                return;
            }
        }
    }

    private final <T> void siftDown(int index, T[] array, int size, int[] priority, int[] insertionOrder) {
        int left = (index * 2) + 1;
        while (left < size) {
            if (left < size - 1 && compareSpans(left, left + 1, priority, insertionOrder) < 0) {
                left++;
            }
            if (compareSpans(index, left, priority, insertionOrder) < 0) {
                T tmpSpan = array[index];
                array[index] = array[left];
                array[left] = tmpSpan;
                int tmpPriority = priority[index];
                priority[index] = priority[left];
                priority[left] = tmpPriority;
                int tmpOrder = insertionOrder[index];
                insertionOrder[index] = insertionOrder[left];
                insertionOrder[left] = tmpOrder;
                index = left;
                left = (index * 2) + 1;
            } else {
                return;
            }
        }
    }

    private final int compareSpans(int left, int right, int[] priority, int[] insertionOrder) {
        int priority1 = priority[left];
        int priority2 = priority[right];
        if (priority1 == priority2) {
            return Integer.compare(insertionOrder[left], insertionOrder[right]);
        }
        return Integer.compare(priority2, priority1);
    }

    public int nextSpanTransition(int start, int limit, Class kind) {
        if (this.mSpanCount == 0) {
            return limit;
        }
        if (kind == null) {
            kind = Object.class;
        }
        return nextSpanTransitionRec(start, limit, kind, treeRoot());
    }

    private int nextSpanTransitionRec(int start, int limit, Class kind, int i) {
        if ((i & 1) != 0) {
            int left = leftChild(i);
            if (resolveGap(this.mSpanMax[left]) > start) {
                limit = nextSpanTransitionRec(start, limit, kind, left);
            }
        }
        if (i >= this.mSpanCount) {
            return limit;
        }
        int st = resolveGap(this.mSpanStarts[i]);
        int en = resolveGap(this.mSpanEnds[i]);
        if (st > start && st < limit && kind.isInstance(this.mSpans[i])) {
            limit = st;
        }
        if (en > start && en < limit && kind.isInstance(this.mSpans[i])) {
            limit = en;
        }
        if (st >= limit || (i & 1) == 0) {
            return limit;
        }
        return nextSpanTransitionRec(start, limit, kind, rightChild(i));
    }

    public CharSequence subSequence(int start, int end) {
        return new SpannableStringBuilder(this, start, end);
    }

    public void getChars(int start, int end, char[] dest, int destoff) {
        checkRange("getChars", start, end);
        if (end <= this.mGapStart) {
            System.arraycopy(this.mText, start, dest, destoff, end - start);
        } else if (start >= this.mGapStart) {
            System.arraycopy(this.mText, this.mGapLength + start, dest, destoff, end - start);
        } else {
            System.arraycopy(this.mText, start, dest, destoff, this.mGapStart - start);
            System.arraycopy(this.mText, this.mGapStart + this.mGapLength, dest, (this.mGapStart - start) + destoff, end - this.mGapStart);
        }
    }

    public String toString() {
        int len = length();
        char[] buf = new char[len];
        getChars(0, len, buf, 0);
        return new String(buf);
    }

    @UnsupportedAppUsage
    public String substring(int start, int end) {
        char[] buf = new char[(end - start)];
        getChars(start, end, buf, 0);
        return new String(buf);
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher beforeTextChanged : watchers) {
            beforeTextChanged.beforeTextChanged(this, start, before, after);
        }
        this.mTextWatcherDepth--;
    }

    private void sendTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher onTextChanged : watchers) {
            onTextChanged.onTextChanged(this, start, before, after);
        }
        this.mTextWatcherDepth--;
    }

    private void sendAfterTextChanged(TextWatcher[] watchers) {
        this.mTextWatcherDepth++;
        for (TextWatcher afterTextChanged : watchers) {
            afterTextChanged.afterTextChanged(this);
        }
        this.mTextWatcherDepth--;
    }

    private void sendSpanAdded(Object what, int start, int end) {
        for (SpanWatcher onSpanAdded : (SpanWatcher[]) getSpans(start, end, SpanWatcher.class)) {
            onSpanAdded.onSpanAdded(this, what, start, end);
        }
    }

    private void sendSpanRemoved(Object what, int start, int end) {
        for (SpanWatcher onSpanRemoved : (SpanWatcher[]) getSpans(start, end, SpanWatcher.class)) {
            onSpanRemoved.onSpanRemoved(this, what, start, end);
        }
    }

    private void sendSpanChanged(Object what, int oldStart, int oldEnd, int start, int end) {
        for (SpanWatcher onSpanChanged : (SpanWatcher[]) getSpans(Math.min(oldStart, start), Math.min(Math.max(oldEnd, end), length()), SpanWatcher.class)) {
            onSpanChanged.onSpanChanged(this, what, oldStart, oldEnd, start, end);
        }
    }

    private static String region(int start, int end) {
        return "(" + start + " ... " + end + ")";
    }

    private void checkRange(String operation, int start, int end) {
        if (end >= start) {
            int len = length();
            if (start > len || end > len) {
                throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " ends beyond length " + len);
            } else if (start < 0 || end < 0) {
                throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " starts before 0");
            }
        } else {
            throw new IndexOutOfBoundsException(operation + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + region(start, end) + " has end before start");
        }
    }

    public void drawText(BaseCanvas c, int start, int end, float x, float y, Paint p) {
        checkRange("drawText", start, end);
        if (end <= this.mGapStart) {
            c.drawText(this.mText, start, end - start, x, y, p);
        } else if (start >= this.mGapStart) {
            c.drawText(this.mText, start + this.mGapLength, end - start, x, y, p);
        } else {
            char[] buf = TextUtils.obtain(end - start);
            getChars(start, end, buf, 0);
            c.drawText(buf, 0, end - start, x, y, p);
            TextUtils.recycle(buf);
        }
    }

    public void drawTextRun(BaseCanvas c, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint p) {
        int i = start;
        int i2 = end;
        int i3 = contextStart;
        int i4 = contextEnd;
        checkRange("drawTextRun", i, i2);
        int contextLen = i4 - i3;
        int len = i2 - i;
        if (i4 <= this.mGapStart) {
            c.drawTextRun(this.mText, start, len, contextStart, contextLen, x, y, isRtl, p);
        } else if (i3 >= this.mGapStart) {
            c.drawTextRun(this.mText, i + this.mGapLength, len, i3 + this.mGapLength, contextLen, x, y, isRtl, p);
        } else {
            char[] buf = TextUtils.obtain(contextLen);
            getChars(i3, i4, buf, 0);
            c.drawTextRun(buf, i - i3, len, 0, contextLen, x, y, isRtl, p);
            TextUtils.recycle(buf);
        }
    }

    public float measureText(int start, int end, Paint p) {
        checkRange("measureText", start, end);
        if (end <= this.mGapStart) {
            return p.measureText(this.mText, start, end - start);
        }
        if (start >= this.mGapStart) {
            return p.measureText(this.mText, this.mGapLength + start, end - start);
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        float ret = p.measureText(buf, 0, end - start);
        TextUtils.recycle(buf);
        return ret;
    }

    public int getTextWidths(int start, int end, float[] widths, Paint p) {
        checkRange("getTextWidths", start, end);
        if (end <= this.mGapStart) {
            return p.getTextWidths(this.mText, start, end - start, widths);
        }
        if (start >= this.mGapStart) {
            return p.getTextWidths(this.mText, this.mGapLength + start, end - start, widths);
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        int ret = p.getTextWidths(buf, 0, end - start, widths);
        TextUtils.recycle(buf);
        return ret;
    }

    public float getTextRunAdvances(int start, int end, int contextStart, int contextEnd, boolean isRtl, float[] advances, int advancesPos, Paint p) {
        int i = start;
        int i2 = end;
        int i3 = contextStart;
        int i4 = contextEnd;
        int contextLen = i4 - i3;
        int len = i2 - i;
        if (i2 <= this.mGapStart) {
            return p.getTextRunAdvances(this.mText, start, len, contextStart, contextLen, isRtl, advances, advancesPos);
        } else if (i >= this.mGapStart) {
            return p.getTextRunAdvances(this.mText, i + this.mGapLength, len, i3 + this.mGapLength, contextLen, isRtl, advances, advancesPos);
        } else {
            char[] buf = TextUtils.obtain(contextLen);
            getChars(i3, i4, buf, 0);
            float ret = p.getTextRunAdvances(buf, i - i3, len, 0, contextLen, isRtl, advances, advancesPos);
            TextUtils.recycle(buf);
            return ret;
        }
    }

    @Deprecated
    public int getTextRunCursor(int contextStart, int contextEnd, int dir, int offset, int cursorOpt, Paint p) {
        boolean z = true;
        if (dir != 1) {
            z = false;
        }
        return getTextRunCursor(contextStart, contextEnd, z, offset, cursorOpt, p);
    }

    public int getTextRunCursor(int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt, Paint p) {
        int contextLen = contextEnd - contextStart;
        if (contextEnd <= this.mGapStart) {
            return p.getTextRunCursor(this.mText, contextStart, contextLen, isRtl, offset, cursorOpt);
        } else if (contextStart >= this.mGapStart) {
            return p.getTextRunCursor(this.mText, contextStart + this.mGapLength, contextLen, isRtl, offset + this.mGapLength, cursorOpt) - this.mGapLength;
        } else {
            char[] buf = TextUtils.obtain(contextLen);
            getChars(contextStart, contextEnd, buf, 0);
            int ret = p.getTextRunCursor(buf, 0, contextLen, isRtl, offset - contextStart, cursorOpt) + contextStart;
            TextUtils.recycle(buf);
            return ret;
        }
    }

    public void setFilters(InputFilter[] filters) {
        if (filters != null) {
            this.mFilters = filters;
            return;
        }
        throw new IllegalArgumentException();
    }

    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    public boolean equals(Object o) {
        if ((o instanceof Spanned) && toString().equals(o.toString())) {
            Spanned other = (Spanned) o;
            Object[] otherSpans = other.getSpans(0, other.length(), Object.class);
            Object[] thisSpans = getSpans(0, length(), Object.class);
            if (this.mSpanCount == otherSpans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    Object thisSpan = thisSpans[i];
                    Object otherSpan = otherSpans[i];
                    if (thisSpan == this) {
                        if (other != otherSpan || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                            return false;
                        }
                    } else if (!thisSpan.equals(otherSpan) || getSpanStart(thisSpan) != other.getSpanStart(otherSpan) || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan) || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = (toString().hashCode() * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object span = this.mSpans[i];
            if (span != this) {
                hash = (hash * 31) + span.hashCode();
            }
            hash = (((((hash * 31) + getSpanStart(span)) * 31) + getSpanEnd(span)) * 31) + getSpanFlags(span);
        }
        return hash;
    }

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private static int leftChild(int i) {
        return i - (((i + 1) & (~i)) >> 1);
    }

    private static int rightChild(int i) {
        return (((i + 1) & (~i)) >> 1) + i;
    }

    private int calcMax(int i) {
        int max = 0;
        if ((i & 1) != 0) {
            max = calcMax(leftChild(i));
        }
        if (i < this.mSpanCount) {
            max = Math.max(max, this.mSpanEnds[i]);
            if ((i & 1) != 0) {
                max = Math.max(max, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = max;
        return max;
    }

    private void restoreInvariants() {
        if (this.mSpanCount != 0) {
            for (int i = 1; i < this.mSpanCount; i++) {
                if (this.mSpanStarts[i] < this.mSpanStarts[i - 1]) {
                    Object span = this.mSpans[i];
                    int start = this.mSpanStarts[i];
                    int end = this.mSpanEnds[i];
                    int flags = this.mSpanFlags[i];
                    int insertionOrder = this.mSpanOrder[i];
                    int j = i;
                    do {
                        this.mSpans[j] = this.mSpans[j - 1];
                        this.mSpanStarts[j] = this.mSpanStarts[j - 1];
                        this.mSpanEnds[j] = this.mSpanEnds[j - 1];
                        this.mSpanFlags[j] = this.mSpanFlags[j - 1];
                        this.mSpanOrder[j] = this.mSpanOrder[j - 1];
                        j--;
                        if (j <= 0 || start >= this.mSpanStarts[j - 1]) {
                            this.mSpans[j] = span;
                            this.mSpanStarts[j] = start;
                            this.mSpanEnds[j] = end;
                            this.mSpanFlags[j] = flags;
                            this.mSpanOrder[j] = insertionOrder;
                            invalidateIndex(j);
                        }
                        this.mSpans[j] = this.mSpans[j - 1];
                        this.mSpanStarts[j] = this.mSpanStarts[j - 1];
                        this.mSpanEnds[j] = this.mSpanEnds[j - 1];
                        this.mSpanFlags[j] = this.mSpanFlags[j - 1];
                        this.mSpanOrder[j] = this.mSpanOrder[j - 1];
                        j--;
                        break;
                    } while (start >= this.mSpanStarts[j - 1]);
                    this.mSpans[j] = span;
                    this.mSpanStarts[j] = start;
                    this.mSpanEnds[j] = end;
                    this.mSpanFlags[j] = flags;
                    this.mSpanOrder[j] = insertionOrder;
                    invalidateIndex(j);
                }
            }
            calcMax(treeRoot());
            if (this.mIndexOfSpan == null) {
                this.mIndexOfSpan = new IdentityHashMap<>();
            }
            for (int i2 = this.mLowWaterMark; i2 < this.mSpanCount; i2++) {
                Integer existing = this.mIndexOfSpan.get(this.mSpans[i2]);
                if (existing == null || existing.intValue() != i2) {
                    this.mIndexOfSpan.put(this.mSpans[i2], Integer.valueOf(i2));
                }
            }
            this.mLowWaterMark = Integer.MAX_VALUE;
        }
    }

    private void invalidateIndex(int i) {
        this.mLowWaterMark = Math.min(i, this.mLowWaterMark);
    }
}
