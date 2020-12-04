package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static final TextLine[] sCached = new TextLine[3];
    private final TextPaint mActivePaint = new TextPaint();
    private float mAddedWidthForJustify;
    @UnsupportedAppUsage
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<>(CharacterStyle.class);
    private char[] mChars;
    private boolean mCharsValid;
    private PrecomputedText mComputed;
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList<>();
    private int mDir;
    private Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    @UnsupportedAppUsage
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet = new SpanSet<>(MetricAffectingSpan.class);
    private TextPaint mPaint;
    @UnsupportedAppUsage
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet = new SpanSet<>(ReplacementSpan.class);
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Spanned mSpanned;
    private int mStart;
    private Layout.TabStops mTabs;
    @UnsupportedAppUsage
    private CharSequence mText;
    private final TextPaint mWorkPaint = new TextPaint();

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @UnsupportedAppUsage
    public static TextLine obtain() {
        synchronized (sCached) {
            int i = sCached.length;
            do {
                i--;
                if (i < 0) {
                    return new TextLine();
                }
            } while (sCached[i] == null);
            TextLine tl = sCached[i];
            sCached[i] = null;
            return tl;
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public static TextLine recycle(TextLine tl) {
        tl.mText = null;
        tl.mPaint = null;
        tl.mDirections = null;
        tl.mSpanned = null;
        tl.mTabs = null;
        tl.mChars = null;
        tl.mComputed = null;
        tl.mMetricAffectingSpanSpanSet.recycle();
        tl.mCharacterStyleSpanSet.recycle();
        tl.mReplacementSpanSpanSet.recycle();
        synchronized (sCached) {
            int i = 0;
            while (true) {
                if (i >= sCached.length) {
                    break;
                } else if (sCached[i] == null) {
                    sCached[i] = tl;
                    break;
                } else {
                    i++;
                }
            }
        }
        return null;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void set(TextPaint paint, CharSequence text, int start, int limit, int dir, Layout.Directions directions, boolean hasTabs, Layout.TabStops tabStops, int ellipsisStart, int ellipsisEnd) {
        TextPaint textPaint = paint;
        CharSequence charSequence = text;
        int i = start;
        int i2 = limit;
        int i3 = ellipsisStart;
        int i4 = ellipsisEnd;
        this.mPaint = textPaint;
        this.mText = charSequence;
        this.mStart = i;
        this.mLen = i2 - i;
        this.mDir = dir;
        this.mDirections = directions;
        if (this.mDirections != null) {
            this.mHasTabs = hasTabs;
            this.mSpanned = null;
            boolean hasReplacement = false;
            int i5 = 1;
            if (charSequence instanceof Spanned) {
                this.mSpanned = (Spanned) charSequence;
                this.mReplacementSpanSpanSet.init(this.mSpanned, i, i2);
                hasReplacement = this.mReplacementSpanSpanSet.numberOfSpans > 0;
            }
            this.mComputed = null;
            if (charSequence instanceof PrecomputedText) {
                this.mComputed = (PrecomputedText) charSequence;
                if (!this.mComputed.getParams().getTextPaint().equalsForTextMeasurement(textPaint)) {
                    this.mComputed = null;
                }
            }
            this.mCharsValid = hasReplacement;
            if (this.mCharsValid) {
                if (this.mChars == null || this.mChars.length < this.mLen) {
                    this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
                }
                TextUtils.getChars(charSequence, i, i2, this.mChars, 0);
                if (hasReplacement) {
                    char[] chars = this.mChars;
                    int i6 = i;
                    while (i6 < i2) {
                        int inext = this.mReplacementSpanSpanSet.getNextTransition(i6, i2);
                        if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i6, inext) && (i6 - i >= i4 || inext - i <= i3)) {
                            chars[i6 - i] = 65532;
                            int j = (i6 - i) + i5;
                            int e = inext - i;
                            while (true) {
                                int e2 = e;
                                if (j >= e2) {
                                    break;
                                }
                                chars[j] = 65279;
                                j++;
                                e = e2;
                            }
                        }
                        i6 = inext;
                        i5 = 1;
                    }
                }
            }
            this.mTabs = tabStops;
            this.mAddedWidthForJustify = 0.0f;
            int i7 = 0;
            this.mIsJustifying = false;
            this.mEllipsisStart = i3 != i4 ? i3 : 0;
            if (i3 != i4) {
                i7 = i4;
            }
            this.mEllipsisEnd = i7;
            return;
        }
        boolean z = hasTabs;
        Layout.TabStops tabStops2 = tabStops;
        throw new IllegalArgumentException("Directions cannot be null");
    }

    private char charAt(int i) {
        return this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i);
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void justify(float justifyWidth) {
        int end = this.mLen;
        while (end > 0 && isLineEndSpace(this.mText.charAt((this.mStart + end) - 1))) {
            end--;
        }
        int spaces = countStretchableSpaces(0, end);
        if (spaces != 0) {
            this.mAddedWidthForJustify = (justifyWidth - Math.abs(measure(end, false, (Paint.FontMetricsInt) null))) / ((float) spaces);
            this.mIsJustifying = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void draw(Canvas c, float x, int top, int y, int bottom) {
        int j;
        int runCount = this.mDirections.getRunCount();
        float h = 0.0f;
        int runIndex = 0;
        while (true) {
            int runIndex2 = runIndex;
            if (runIndex2 < runCount) {
                int runStart = this.mDirections.getRunStart(runIndex2);
                int runLimit = Math.min(this.mDirections.getRunLength(runIndex2) + runStart, this.mLen);
                boolean runIsRtl = this.mDirections.isRunRtl(runIndex2);
                int segStart = runStart;
                int j2 = this.mHasTabs ? runStart : runLimit;
                int segStart2 = segStart;
                float h2 = h;
                while (true) {
                    int j3 = j2;
                    if (j3 > runLimit) {
                        break;
                    }
                    if (j3 == runLimit || charAt(j3) == 9) {
                        j = j3;
                        h2 += drawRun(c, segStart2, j3, runIsRtl, x + h2, top, y, bottom, (runIndex2 == runCount + -1 && j3 == this.mLen) ? false : true);
                        if (j != runLimit) {
                            h2 = ((float) this.mDir) * nextTab(((float) this.mDir) * h2);
                        }
                        segStart2 = j + 1;
                    } else {
                        j = j3;
                    }
                    j2 = j + 1;
                }
                runIndex = runIndex2 + 1;
                h = h2;
            } else {
                return;
            }
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public float metrics(Paint.FontMetricsInt fmi) {
        return measure(this.mLen, false, fmi);
    }

    public float measure(int offset, boolean trailing, Paint.FontMetricsInt fmi) {
        float h;
        int j;
        int i = offset;
        if (i <= this.mLen) {
            int target = trailing ? i - 1 : i;
            if (target < 0) {
                return 0.0f;
            }
            float h2 = 0.0f;
            int j2 = 0;
            while (true) {
                int runIndex = j2;
                if (runIndex < this.mDirections.getRunCount()) {
                    int runStart = this.mDirections.getRunStart(runIndex);
                    int runLimit = Math.min(this.mDirections.getRunLength(runIndex) + runStart, this.mLen);
                    boolean runIsRtl = this.mDirections.isRunRtl(runIndex);
                    int segStart = runStart;
                    int j3 = this.mHasTabs ? runStart : runLimit;
                    int segStart2 = segStart;
                    h = h2;
                    while (true) {
                        int j4 = j3;
                        if (j4 > runLimit) {
                            break;
                        }
                        if (j4 == runLimit || charAt(j4) == 9) {
                            boolean z = true;
                            boolean targetIsInThisSegment = target >= segStart2 && target < j4;
                            if ((this.mDir == -1) != runIsRtl) {
                                z = false;
                            }
                            boolean sameDirection = z;
                            if (!targetIsInThisSegment || !sameDirection) {
                                int j5 = j4;
                                float segmentWidth = measureRun(segStart2, j4, j4, runIsRtl, fmi);
                                h += sameDirection ? segmentWidth : -segmentWidth;
                                if (targetIsInThisSegment) {
                                    float f = segmentWidth;
                                    return measureRun(segStart2, offset, j5, runIsRtl, (Paint.FontMetricsInt) null) + h;
                                }
                                j = j5;
                                if (j != runLimit) {
                                    if (i == j) {
                                        return h;
                                    }
                                    float h3 = ((float) this.mDir) * nextTab(((float) this.mDir) * h);
                                    if (target == j) {
                                        return h3;
                                    }
                                    h = h3;
                                }
                                segStart2 = j + 1;
                            } else {
                                int i2 = j4;
                                return measureRun(segStart2, offset, j4, runIsRtl, fmi) + h;
                            }
                        } else {
                            j = j4;
                        }
                        j3 = j + 1;
                    }
                } else {
                    return h2;
                }
                j2 = runIndex + 1;
                h2 = h;
            }
        } else {
            throw new IndexOutOfBoundsException("offset(" + i + ") should be less than line limit(" + this.mLen + ")");
        }
    }

    @VisibleForTesting
    public float[] measureAllOffsets(boolean[] trailing, Paint.FontMetricsInt fmi) {
        int j;
        float w;
        int offset;
        boolean j2 = true;
        float[] measurement = new float[(this.mLen + 1)];
        int[] target = new int[(this.mLen + 1)];
        boolean z = false;
        for (int offset2 = 0; offset2 < target.length; offset2++) {
            target[offset2] = trailing[offset2] ? offset2 - 1 : offset2;
        }
        if (target[0] < 0) {
            measurement[0] = 0.0f;
        }
        float h = 0.0f;
        int runIndex = 0;
        while (true) {
            int runIndex2 = runIndex;
            if (runIndex2 >= this.mDirections.getRunCount()) {
                break;
            }
            int runStart = this.mDirections.getRunStart(runIndex2);
            int runLimit = Math.min(this.mDirections.getRunLength(runIndex2) + runStart, this.mLen);
            boolean runIsRtl = this.mDirections.isRunRtl(runIndex2);
            int segStart = runStart;
            int j3 = this.mHasTabs ? runStart : runLimit;
            int segStart2 = segStart;
            float h2 = h;
            while (true) {
                int j4 = j3;
                if (j4 > runLimit) {
                    break;
                }
                if (j4 == runLimit || charAt(j4) == 9) {
                    float oldh = h2;
                    boolean advance = (this.mDir == -1 ? j2 : z) == runIsRtl ? j2 : z;
                    j = j4;
                    int segStart3 = segStart2;
                    float w2 = measureRun(segStart2, j4, j4, runIsRtl, fmi);
                    h2 += advance ? w2 : -w2;
                    float baseh = advance ? oldh : h2;
                    Paint.FontMetricsInt crtfmi = advance ? fmi : null;
                    int offset3 = segStart3;
                    while (true) {
                        int offset4 = offset3;
                        if (offset4 > j || offset4 > this.mLen) {
                        } else {
                            if (target[offset4] < segStart3 || target[offset4] >= j) {
                                offset = offset4;
                                w = w2;
                            } else {
                                offset = offset4;
                                w = w2;
                                measurement[offset] = baseh + measureRun(segStart3, offset4, j, runIsRtl, crtfmi);
                            }
                            offset3 = offset + 1;
                            w2 = w;
                        }
                    }
                    if (j != runLimit) {
                        if (target[j] == j) {
                            measurement[j] = h2;
                        }
                        float h3 = ((float) this.mDir) * nextTab(((float) this.mDir) * h2);
                        if (target[j + 1] == j) {
                            measurement[j + 1] = h3;
                        }
                        h2 = h3;
                    }
                    segStart2 = j + 1;
                } else {
                    j = j4;
                }
                j3 = j + 1;
                j2 = true;
                z = false;
            }
            runIndex = runIndex2 + 1;
            h = h2;
            j2 = true;
            z = false;
        }
        if (target[this.mLen] == this.mLen) {
            measurement[this.mLen] = h;
        }
        return measurement;
    }

    private float drawRun(Canvas c, int start, int limit, boolean runIsRtl, float x, int top, int y, int bottom, boolean needWidth) {
        boolean z = true;
        if (this.mDir != 1) {
            z = false;
        }
        if (z != runIsRtl) {
            return handleRun(start, limit, limit, runIsRtl, c, x, top, y, bottom, (Paint.FontMetricsInt) null, needWidth);
        }
        int i = start;
        int i2 = limit;
        int i3 = limit;
        boolean z2 = runIsRtl;
        float w = -measureRun(i, i2, i3, z2, (Paint.FontMetricsInt) null);
        handleRun(i, i2, i3, z2, c, x + w, top, y, bottom, (Paint.FontMetricsInt) null, false);
        return w;
    }

    private float measureRun(int start, int offset, int limit, boolean runIsRtl, Paint.FontMetricsInt fmi) {
        return handleRun(start, offset, limit, runIsRtl, (Canvas) null, 0.0f, 0, 0, 0, fmi, true);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0178 A[EDGE_INSN: B:124:0x0178->B:106:0x0178 ?: BREAK  
    EDGE_INSN: B:125:0x0178->B:106:0x0178 ?: BREAK  ] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x017d  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x010f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getOffsetToLeftRightOf(int r28, boolean r29) {
        /*
            r27 = this;
            r7 = r27
            r8 = r28
            r9 = r29
            r10 = 0
            int r11 = r7.mLen
            int r0 = r7.mDir
            r12 = -1
            if (r0 != r12) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            r15 = r0
            android.text.Layout$Directions r0 = r7.mDirections
            int[] r6 = r0.mDirections
            r0 = 0
            r1 = r10
            r2 = r11
            r16 = -1
            r3 = 0
            r17 = 67108863(0x3ffffff, float:1.5046327E-36)
            if (r8 != r10) goto L_0x002f
            r4 = -2
        L_0x0022:
            r14 = r0
            r18 = r1
            r21 = r3
            r12 = r4
            r8 = r6
            r13 = r16
            r16 = r2
            goto L_0x00fe
        L_0x002f:
            if (r8 != r11) goto L_0x0033
            int r4 = r6.length
            goto L_0x0022
        L_0x0033:
            r4 = r2
            r2 = r1
            r1 = 0
        L_0x0036:
            int r5 = r6.length
            if (r1 >= r5) goto L_0x00ab
            r5 = r6[r1]
            int r2 = r10 + r5
            if (r8 < r2) goto L_0x00a3
            int r5 = r1 + 1
            r5 = r6[r5]
            r5 = r5 & r17
            int r5 = r5 + r2
            if (r5 <= r11) goto L_0x0049
            r5 = r11
        L_0x0049:
            if (r8 >= r5) goto L_0x009f
            int r4 = r1 + 1
            r4 = r6[r4]
            int r4 = r4 >>> 26
            r0 = r4 & 63
            if (r8 != r2) goto L_0x0096
            int r4 = r8 + -1
            r18 = 0
        L_0x0059:
            r19 = r18
            int r13 = r6.length
            r14 = r19
            if (r14 >= r13) goto L_0x008d
            r13 = r6[r14]
            int r13 = r13 + r10
            if (r4 < r13) goto L_0x0085
            int r19 = r14 + 1
            r18 = r6[r19]
            r18 = r18 & r17
            int r12 = r13 + r18
            if (r12 <= r11) goto L_0x0070
            r12 = r11
        L_0x0070:
            if (r4 >= r12) goto L_0x0085
            int r19 = r14 + 1
            r18 = r6[r19]
            int r18 = r18 >>> 26
            r20 = r2
            r2 = r18 & 63
            if (r2 >= r0) goto L_0x0087
            r1 = r14
            r0 = r2
            r18 = r13
            r5 = r12
            r3 = 1
            goto L_0x0091
        L_0x0085:
            r20 = r2
        L_0x0087:
            int r18 = r14 + 2
            r2 = r20
            r12 = -1
            goto L_0x0059
        L_0x008d:
            r20 = r2
            r18 = r20
        L_0x0091:
            r13 = r0
            r12 = r1
            r14 = r5
            r5 = r3
            goto L_0x00b1
        L_0x0096:
            r20 = r2
            r13 = r0
            r12 = r1
            r14 = r5
            r18 = r20
            r5 = r3
            goto L_0x00b1
        L_0x009f:
            r20 = r2
            r4 = r5
            goto L_0x00a5
        L_0x00a3:
            r20 = r2
        L_0x00a5:
            int r1 = r1 + 2
            r2 = r20
            r12 = -1
            goto L_0x0036
        L_0x00ab:
            r13 = r0
            r12 = r1
            r18 = r2
            r5 = r3
            r14 = r4
        L_0x00b1:
            int r0 = r6.length
            if (r12 == r0) goto L_0x00f4
            r0 = r13 & 1
            if (r0 == 0) goto L_0x00ba
            r0 = 1
            goto L_0x00bb
        L_0x00ba:
            r0 = 0
        L_0x00bb:
            r4 = r0
            if (r9 != r4) goto L_0x00c0
            r0 = 1
            goto L_0x00c1
        L_0x00c0:
            r0 = 0
        L_0x00c1:
            r3 = r0
            if (r3 == 0) goto L_0x00c6
            r0 = r14
            goto L_0x00c8
        L_0x00c6:
            r0 = r18
        L_0x00c8:
            if (r8 != r0) goto L_0x00d1
            if (r3 == r5) goto L_0x00cd
            goto L_0x00d1
        L_0x00cd:
            r21 = r5
            r8 = r6
            goto L_0x00f7
        L_0x00d1:
            r0 = r27
            r1 = r12
            r2 = r18
            r19 = r3
            r3 = r14
            r20 = r4
            r21 = r5
            r5 = r28
            r8 = r6
            r6 = r19
            int r0 = r0.getOffsetBeforeAfter(r1, r2, r3, r4, r5, r6)
            if (r19 == 0) goto L_0x00ea
            r1 = r14
            goto L_0x00ec
        L_0x00ea:
            r1 = r18
        L_0x00ec:
            if (r0 == r1) goto L_0x00ef
            return r0
        L_0x00ef:
            r16 = r14
            r14 = r13
            r13 = r0
            goto L_0x00fe
        L_0x00f4:
            r21 = r5
            r8 = r6
        L_0x00f7:
            r26 = r14
            r14 = r13
            r13 = r16
            r16 = r26
        L_0x00fe:
            if (r9 != r15) goto L_0x0102
            r0 = 1
            goto L_0x0103
        L_0x0102:
            r0 = 0
        L_0x0103:
            if (r0 == 0) goto L_0x0107
            r1 = 2
            goto L_0x0108
        L_0x0107:
            r1 = -2
        L_0x0108:
            int r6 = r12 + r1
            if (r6 < 0) goto L_0x0178
            int r1 = r8.length
            if (r6 >= r1) goto L_0x0178
            r1 = r8[r6]
            int r19 = r10 + r1
            int r1 = r6 + 1
            r1 = r8[r1]
            r1 = r1 & r17
            int r1 = r19 + r1
            if (r1 <= r11) goto L_0x011e
            r1 = r11
        L_0x011e:
            r20 = r1
            int r1 = r6 + 1
            r1 = r8[r1]
            int r1 = r1 >>> 26
            r5 = r1 & 63
            r1 = r5 & 1
            if (r1 == 0) goto L_0x012e
            r1 = 1
            goto L_0x012f
        L_0x012e:
            r1 = 0
        L_0x012f:
            r4 = r1
            if (r9 != r4) goto L_0x0134
            r1 = 1
            goto L_0x0135
        L_0x0134:
            r1 = 0
        L_0x0135:
            r22 = r1
            r0 = -1
            if (r13 != r0) goto L_0x0165
            if (r22 == 0) goto L_0x0140
            r23 = r19
            goto L_0x0142
        L_0x0140:
            r23 = r20
        L_0x0142:
            r0 = r27
            r1 = r6
            r2 = r19
            r3 = r20
            r24 = r4
            r25 = r5
            r5 = r23
            r23 = r6
            r6 = r22
            int r13 = r0.getOffsetBeforeAfter(r1, r2, r3, r4, r5, r6)
            if (r22 == 0) goto L_0x015c
            r0 = r20
            goto L_0x015e
        L_0x015c:
            r0 = r19
        L_0x015e:
            if (r13 != r0) goto L_0x018e
            r12 = r23
            r14 = r25
            goto L_0x00fe
        L_0x0165:
            r24 = r4
            r25 = r5
            r23 = r6
            r0 = r25
            if (r0 >= r14) goto L_0x018e
            if (r22 == 0) goto L_0x0174
            r1 = r19
            goto L_0x0176
        L_0x0174:
            r1 = r20
        L_0x0176:
            r13 = r1
            goto L_0x018e
        L_0x0178:
            r23 = r6
            r1 = -1
            if (r13 != r1) goto L_0x0186
            if (r0 == 0) goto L_0x0184
            int r1 = r7.mLen
            r2 = 1
            int r1 = r1 + r2
        L_0x0184:
            r13 = r1
            goto L_0x018e
        L_0x0186:
            if (r13 > r11) goto L_0x018e
            if (r0 == 0) goto L_0x018c
            r1 = r11
            goto L_0x018d
        L_0x018c:
            r1 = r10
        L_0x018d:
            r13 = r1
        L_0x018e:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.getOffsetToLeftRightOf(int, boolean):int");
    }

    private int getOffsetBeforeAfter(int runIndex, int runStart, int runLimit, boolean runIsRtl, int offset, boolean after) {
        int spanLimit;
        int spanStart;
        int spanLimit2;
        int i = offset;
        if (runIndex >= 0) {
            int i2 = 0;
            if (i != (after ? this.mLen : 0)) {
                TextPaint wp = this.mWorkPaint;
                wp.set(this.mPaint);
                if (this.mIsJustifying) {
                    wp.setWordSpacing(this.mAddedWidthForJustify);
                }
                int spanStart2 = runStart;
                if (this.mSpanned == null) {
                    spanStart = spanStart2;
                    spanLimit = runLimit;
                } else {
                    int target = after ? i + 1 : i;
                    int limit = this.mStart + runLimit;
                    while (true) {
                        spanLimit2 = this.mSpanned.nextSpanTransition(this.mStart + spanStart2, limit, MetricAffectingSpan.class) - this.mStart;
                        if (spanLimit2 >= target) {
                            break;
                        }
                        spanStart2 = spanLimit2;
                    }
                    MetricAffectingSpan[] spans = (MetricAffectingSpan[]) TextUtils.removeEmptySpans((MetricAffectingSpan[]) this.mSpanned.getSpans(this.mStart + spanStart2, this.mStart + spanLimit2, MetricAffectingSpan.class), this.mSpanned, MetricAffectingSpan.class);
                    if (spans.length > 0) {
                        ReplacementSpan replacement = null;
                        for (MetricAffectingSpan span : spans) {
                            if (span instanceof ReplacementSpan) {
                                replacement = (ReplacementSpan) span;
                            } else {
                                span.updateMeasureState(wp);
                            }
                        }
                        if (replacement != null) {
                            return after ? spanLimit2 : spanStart2;
                        }
                    }
                    spanStart = spanStart2;
                    spanLimit = spanLimit2;
                }
                if (!after) {
                    i2 = 2;
                }
                int cursorOpt = i2;
                if (this.mCharsValid) {
                    return wp.getTextRunCursor(this.mChars, spanStart, spanLimit - spanStart, runIsRtl, offset, cursorOpt);
                }
                TextPaint textPaint = wp;
                return wp.getTextRunCursor(this.mText, this.mStart + spanStart, this.mStart + spanLimit, runIsRtl, this.mStart + i, cursorOpt) - this.mStart;
            }
        }
        if (after) {
            return TextUtils.getOffsetAfter(this.mText, this.mStart + i) - this.mStart;
        }
        return TextUtils.getOffsetBefore(this.mText, this.mStart + i) - this.mStart;
    }

    private static void expandMetricsFromPaint(Paint.FontMetricsInt fmi, TextPaint wp) {
        int previousTop = fmi.top;
        int previousAscent = fmi.ascent;
        int previousDescent = fmi.descent;
        int previousBottom = fmi.bottom;
        int previousLeading = fmi.leading;
        wp.getFontMetricsInt(fmi);
        updateMetrics(fmi, previousTop, previousAscent, previousDescent, previousBottom, previousLeading);
    }

    static void updateMetrics(Paint.FontMetricsInt fmi, int previousTop, int previousAscent, int previousDescent, int previousBottom, int previousLeading) {
        fmi.top = Math.min(fmi.top, previousTop);
        fmi.ascent = Math.min(fmi.ascent, previousAscent);
        fmi.descent = Math.max(fmi.descent, previousDescent);
        fmi.bottom = Math.max(fmi.bottom, previousBottom);
        fmi.leading = Math.max(fmi.leading, previousLeading);
    }

    private static void drawStroke(TextPaint wp, Canvas c, int color, float position, float thickness, float xleft, float xright, float baseline) {
        float strokeTop = baseline + ((float) wp.baselineShift) + position;
        int previousColor = wp.getColor();
        Paint.Style previousStyle = wp.getStyle();
        boolean previousAntiAlias = wp.isAntiAlias();
        wp.setStyle(Paint.Style.FILL);
        wp.setAntiAlias(true);
        int i = color;
        wp.setColor(color);
        c.drawRect(xleft, strokeTop, xright, strokeTop + thickness, wp);
        wp.setStyle(previousStyle);
        wp.setColor(previousColor);
        wp.setAntiAlias(previousAntiAlias);
    }

    private float getRunAdvance(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, int offset) {
        if (this.mCharsValid) {
            return wp.getRunAdvance(this.mChars, start, end, contextStart, contextEnd, runIsRtl, offset);
        }
        int delta = this.mStart;
        if (this.mComputed != null) {
            return this.mComputed.getWidth(start + delta, end + delta);
        }
        return wp.getRunAdvance(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, runIsRtl, delta + offset);
    }

    private float handleText(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth, int offset, ArrayList<DecorationInfo> decorations) {
        int numDecorations;
        float totalWidth;
        float leftX;
        float rightX;
        float decorationXLeft;
        float f;
        int i;
        TextPaint textPaint = wp;
        int i2 = start;
        int i3 = y;
        Paint.FontMetricsInt fontMetricsInt = fmi;
        ArrayList<DecorationInfo> arrayList = decorations;
        if (this.mIsJustifying) {
            textPaint.setWordSpacing(this.mAddedWidthForJustify);
        }
        if (fontMetricsInt != null) {
            expandMetricsFromPaint(fontMetricsInt, textPaint);
        }
        if (end == i2) {
            return 0.0f;
        }
        float totalWidth2 = 0.0f;
        int i4 = 0;
        int numDecorations2 = arrayList == null ? 0 : decorations.size();
        if (needWidth || !(c == null || (textPaint.bgColor == 0 && numDecorations2 == 0 && !runIsRtl))) {
            numDecorations = numDecorations2;
            totalWidth2 = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, offset);
        } else {
            numDecorations = numDecorations2;
        }
        if (c != null) {
            if (runIsRtl) {
                leftX = x - totalWidth2;
                rightX = x;
            } else {
                leftX = x;
                rightX = x + totalWidth2;
            }
            float leftX2 = leftX;
            float rightX2 = rightX;
            if (textPaint.bgColor != 0) {
                int previousColor = wp.getColor();
                Paint.Style previousStyle = wp.getStyle();
                textPaint.setColor(textPaint.bgColor);
                textPaint.setStyle(Paint.Style.FILL);
                c.drawRect(leftX2, (float) top, rightX2, (float) bottom, wp);
                textPaint.setStyle(previousStyle);
                textPaint.setColor(previousColor);
            }
            totalWidth = totalWidth2;
            drawTextRun(c, wp, start, end, contextStart, contextEnd, runIsRtl, leftX2, i3 + textPaint.baselineShift);
            if (numDecorations != 0) {
                while (true) {
                    int i5 = i4;
                    if (i5 >= numDecorations) {
                        break;
                    }
                    DecorationInfo info = arrayList.get(i5);
                    int decorationStart = Math.max(info.start, i2);
                    int decorationEnd = Math.min(info.end, offset);
                    TextPaint textPaint2 = wp;
                    int i6 = start;
                    int i7 = end;
                    int i8 = contextStart;
                    int i9 = contextEnd;
                    boolean z = runIsRtl;
                    float decorationStartAdvance = getRunAdvance(textPaint2, i6, i7, i8, i9, z, decorationStart);
                    float decorationEndAdvance = getRunAdvance(textPaint2, i6, i7, i8, i9, z, decorationEnd);
                    if (runIsRtl) {
                        decorationXLeft = rightX2 - decorationEndAdvance;
                        f = rightX2 - decorationStartAdvance;
                    } else {
                        decorationXLeft = leftX2 + decorationStartAdvance;
                        f = leftX2 + decorationEndAdvance;
                    }
                    float decorationXLeft2 = decorationXLeft;
                    float decorationXRight = f;
                    if (info.underlineColor != 0) {
                        drawStroke(wp, c, info.underlineColor, wp.getUnderlinePosition(), info.underlineThickness, decorationXLeft2, decorationXRight, (float) i3);
                    }
                    if (info.isUnderlineText) {
                        i = i3;
                        drawStroke(wp, c, wp.getColor(), wp.getUnderlinePosition(), Math.max(wp.getUnderlineThickness(), 1.0f), decorationXLeft2, decorationXRight, (float) i3);
                    } else {
                        i = i3;
                    }
                    if (info.isStrikeThruText) {
                        drawStroke(wp, c, wp.getColor(), wp.getStrikeThruPosition(), Math.max(wp.getStrikeThruThickness(), 1.0f), decorationXLeft2, decorationXRight, (float) i);
                    }
                    i4 = i5 + 1;
                    TextPaint textPaint3 = wp;
                    i2 = start;
                    Paint.FontMetricsInt fontMetricsInt2 = fmi;
                    arrayList = decorations;
                    i3 = i;
                }
            }
            int i10 = i3;
        } else {
            totalWidth = totalWidth2;
            int i11 = i3;
        }
        return runIsRtl ? -totalWidth : totalWidth;
    }

    private float handleReplacement(ReplacementSpan replacement, TextPaint wp, int start, int limit, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth) {
        float ret;
        float x2;
        Paint.FontMetricsInt fontMetricsInt = fmi;
        float ret2 = 0.0f;
        int textStart = this.mStart + start;
        int textLimit = this.mStart + limit;
        if (needWidth || (c != null && runIsRtl)) {
            int previousTop = 0;
            int previousAscent = 0;
            int previousDescent = 0;
            int previousBottom = 0;
            int previousLeading = 0;
            boolean needUpdateMetrics = fontMetricsInt != null;
            if (needUpdateMetrics) {
                previousTop = fontMetricsInt.top;
                previousAscent = fontMetricsInt.ascent;
                previousDescent = fontMetricsInt.descent;
                previousBottom = fontMetricsInt.bottom;
                previousLeading = fontMetricsInt.leading;
            }
            int previousTop2 = previousTop;
            int previousAscent2 = previousAscent;
            int previousDescent2 = previousDescent;
            int previousBottom2 = previousBottom;
            int previousLeading2 = previousLeading;
            ret2 = (float) replacement.getSize(wp, this.mText, textStart, textLimit, fmi);
            if (needUpdateMetrics) {
                updateMetrics(fmi, previousTop2, previousAscent2, previousDescent2, previousBottom2, previousLeading2);
            }
        }
        float ret3 = ret2;
        if (c != null) {
            if (runIsRtl) {
                x2 = x - ret3;
            } else {
                x2 = x;
            }
            ret = ret3;
            replacement.draw(c, this.mText, textStart, textLimit, x2, top, y, bottom, wp);
        } else {
            ret = ret3;
            float f = x;
        }
        return runIsRtl ? -ret : ret;
    }

    private int adjustStartHyphenEdit(int start, int startHyphenEdit) {
        if (start > 0) {
            return 0;
        }
        return startHyphenEdit;
    }

    private int adjustEndHyphenEdit(int limit, int endHyphenEdit) {
        if (limit < this.mLen) {
            return 0;
        }
        return endHyphenEdit;
    }

    private static final class DecorationInfo {
        public int end;
        public boolean isStrikeThruText;
        public boolean isUnderlineText;
        public int start;
        public int underlineColor;
        public float underlineThickness;

        private DecorationInfo() {
            this.start = -1;
            this.end = -1;
        }

        public boolean hasDecoration() {
            return this.isStrikeThruText || this.isUnderlineText || this.underlineColor != 0;
        }

        public DecorationInfo copyInfo() {
            DecorationInfo copy = new DecorationInfo();
            copy.isStrikeThruText = this.isStrikeThruText;
            copy.isUnderlineText = this.isUnderlineText;
            copy.underlineColor = this.underlineColor;
            copy.underlineThickness = this.underlineThickness;
            return copy;
        }
    }

    private void extractDecorationInfo(TextPaint paint, DecorationInfo info) {
        info.isStrikeThruText = paint.isStrikeThruText();
        if (info.isStrikeThruText) {
            paint.setStrikeThruText(false);
        }
        info.isUnderlineText = paint.isUnderlineText();
        if (info.isUnderlineText) {
            paint.setUnderlineText(false);
        }
        info.underlineColor = paint.underlineColor;
        info.underlineThickness = paint.underlineThickness;
        paint.setUnderlineText(0, 0.0f);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v35, resolved type: android.text.style.MetricAffectingSpan[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v36, resolved type: android.text.style.DynamicDrawableSpan} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x027c  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x028e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float handleRun(int r38, int r39, int r40, boolean r41, android.graphics.Canvas r42, float r43, int r44, int r45, int r46, android.graphics.Paint.FontMetricsInt r47, boolean r48) {
        /*
            r37 = this;
            r15 = r37
            r14 = r38
            r13 = r39
            r12 = r40
            r11 = r47
            if (r13 < r14) goto L_0x0321
            if (r13 > r12) goto L_0x0321
            if (r14 != r13) goto L_0x001e
            android.text.TextPaint r0 = r15.mWorkPaint
            android.text.TextPaint r1 = r15.mPaint
            r0.set(r1)
            if (r11 == 0) goto L_0x001c
            expandMetricsFromPaint(r11, r0)
        L_0x001c:
            r1 = 0
            return r1
        L_0x001e:
            android.text.Spanned r0 = r15.mSpanned
            r16 = 0
            r17 = 1
            if (r0 != 0) goto L_0x0028
            r0 = 0
            goto L_0x0054
        L_0x0028:
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r0 = r15.mMetricAffectingSpanSpanSet
            android.text.Spanned r1 = r15.mSpanned
            int r2 = r15.mStart
            int r2 = r2 + r14
            int r3 = r15.mStart
            int r3 = r3 + r12
            r0.init(r1, r2, r3)
            android.text.SpanSet<android.text.style.CharacterStyle> r0 = r15.mCharacterStyleSpanSet
            android.text.Spanned r1 = r15.mSpanned
            int r2 = r15.mStart
            int r2 = r2 + r14
            int r3 = r15.mStart
            int r3 = r3 + r12
            r0.init(r1, r2, r3)
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r0 = r15.mMetricAffectingSpanSpanSet
            int r0 = r0.numberOfSpans
            if (r0 != 0) goto L_0x0052
            android.text.SpanSet<android.text.style.CharacterStyle> r0 = r15.mCharacterStyleSpanSet
            int r0 = r0.numberOfSpans
            if (r0 == 0) goto L_0x004f
            goto L_0x0052
        L_0x004f:
            r0 = r16
            goto L_0x0054
        L_0x0052:
            r0 = r17
        L_0x0054:
            r18 = r0
            if (r18 != 0) goto L_0x009d
            android.text.TextPaint r10 = r15.mWorkPaint
            android.text.TextPaint r0 = r15.mPaint
            r10.set(r0)
            int r0 = r10.getStartHyphenEdit()
            int r0 = r15.adjustStartHyphenEdit(r14, r0)
            r10.setStartHyphenEdit(r0)
            int r0 = r10.getEndHyphenEdit()
            int r0 = r15.adjustEndHyphenEdit(r12, r0)
            r10.setEndHyphenEdit(r0)
            r16 = 0
            r0 = r37
            r1 = r10
            r2 = r38
            r3 = r40
            r4 = r38
            r5 = r40
            r6 = r41
            r7 = r42
            r8 = r43
            r9 = r44
            r17 = r10
            r10 = r45
            r11 = r46
            r12 = r47
            r13 = r48
            r14 = r39
            r15 = r16
            float r0 = r0.handleText(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return r0
        L_0x009d:
            r19 = r43
            r13 = r43
            r0 = r38
        L_0x00a3:
            r15 = r0
            r14 = r39
            if (r15 >= r14) goto L_0x031e
            r12 = r37
            android.text.TextPaint r11 = r12.mWorkPaint
            android.text.TextPaint r0 = r12.mPaint
            r11.set(r0)
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r0 = r12.mMetricAffectingSpanSpanSet
            int r1 = r12.mStart
            int r1 = r1 + r15
            int r2 = r12.mStart
            r10 = r40
            int r2 = r2 + r10
            int r0 = r0.getNextTransition(r1, r2)
            int r1 = r12.mStart
            int r9 = r0 - r1
            int r8 = java.lang.Math.min(r9, r14)
            r0 = 0
            r20 = r0
            r0 = r16
        L_0x00cc:
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r1 = r12.mMetricAffectingSpanSpanSet
            int r1 = r1.numberOfSpans
            if (r0 >= r1) goto L_0x0124
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r1 = r12.mMetricAffectingSpanSpanSet
            int[] r1 = r1.spanStarts
            r1 = r1[r0]
            int r2 = r12.mStart
            int r2 = r2 + r8
            if (r1 >= r2) goto L_0x0121
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r1 = r12.mMetricAffectingSpanSpanSet
            int[] r1 = r1.spanEnds
            r1 = r1[r0]
            int r2 = r12.mStart
            int r2 = r2 + r15
            if (r1 > r2) goto L_0x00e9
            goto L_0x0121
        L_0x00e9:
            int r1 = r12.mStart
            int r2 = r12.mEllipsisStart
            int r1 = r1 + r2
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r2 = r12.mMetricAffectingSpanSpanSet
            int[] r2 = r2.spanStarts
            r2 = r2[r0]
            if (r1 > r2) goto L_0x0106
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r1 = r12.mMetricAffectingSpanSpanSet
            int[] r1 = r1.spanEnds
            r1 = r1[r0]
            int r2 = r12.mStart
            int r3 = r12.mEllipsisEnd
            int r2 = r2 + r3
            if (r1 > r2) goto L_0x0106
            r1 = r17
            goto L_0x0108
        L_0x0106:
            r1 = r16
        L_0x0108:
            android.text.SpanSet<android.text.style.MetricAffectingSpan> r2 = r12.mMetricAffectingSpanSpanSet
            E[] r2 = r2.spans
            android.text.style.MetricAffectingSpan[] r2 = (android.text.style.MetricAffectingSpan[]) r2
            r2 = r2[r0]
            boolean r3 = r2 instanceof android.text.style.ReplacementSpan
            if (r3 == 0) goto L_0x011e
            if (r1 != 0) goto L_0x011a
            r3 = r2
            android.text.style.ReplacementSpan r3 = (android.text.style.ReplacementSpan) r3
            goto L_0x011b
        L_0x011a:
            r3 = 0
        L_0x011b:
            r20 = r3
            goto L_0x0121
        L_0x011e:
            r2.updateDrawState(r11)
        L_0x0121:
            int r0 = r0 + 1
            goto L_0x00cc
        L_0x0124:
            if (r20 == 0) goto L_0x0158
            if (r48 != 0) goto L_0x012e
            if (r8 >= r14) goto L_0x012b
            goto L_0x012e
        L_0x012b:
            r21 = r16
            goto L_0x0130
        L_0x012e:
            r21 = r17
        L_0x0130:
            r0 = r37
            r1 = r20
            r2 = r11
            r3 = r15
            r4 = r8
            r5 = r41
            r6 = r42
            r7 = r13
            r22 = r8
            r8 = r44
            r23 = r9
            r9 = r45
            r10 = r46
            r14 = r11
            r11 = r47
            r24 = r14
            r14 = r12
            r12 = r21
            float r0 = r0.handleReplacement(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            float r13 = r13 + r0
            r24 = r15
            goto L_0x031a
        L_0x0158:
            r22 = r8
            r23 = r9
            r24 = r11
            r14 = r12
            android.text.TextPaint r12 = r14.mActivePaint
            android.text.TextPaint r0 = r14.mPaint
            r12.set(r0)
            r0 = r15
            r1 = r22
            android.text.TextLine$DecorationInfo r11 = r14.mDecorationInfo
            java.util.ArrayList<android.text.TextLine$DecorationInfo> r2 = r14.mDecorations
            r2.clear()
            r10 = r1
            r21 = r13
            r13 = r0
        L_0x0174:
            r9 = r0
            r8 = r22
            if (r9 >= r8) goto L_0x02a4
            android.text.SpanSet<android.text.style.CharacterStyle> r0 = r14.mCharacterStyleSpanSet
            int r1 = r14.mStart
            int r1 = r1 + r9
            int r2 = r14.mStart
            int r2 = r2 + r23
            int r0 = r0.getNextTransition(r1, r2)
            int r1 = r14.mStart
            int r7 = r0 - r1
            int r22 = java.lang.Math.min(r7, r8)
            android.text.TextPaint r0 = r14.mPaint
            r1 = r24
            r1.set(r0)
            r0 = r16
        L_0x0197:
            android.text.SpanSet<android.text.style.CharacterStyle> r2 = r14.mCharacterStyleSpanSet
            int r2 = r2.numberOfSpans
            if (r0 >= r2) goto L_0x01c3
            android.text.SpanSet<android.text.style.CharacterStyle> r2 = r14.mCharacterStyleSpanSet
            int[] r2 = r2.spanStarts
            r2 = r2[r0]
            int r3 = r14.mStart
            int r3 = r3 + r22
            if (r2 >= r3) goto L_0x01c0
            android.text.SpanSet<android.text.style.CharacterStyle> r2 = r14.mCharacterStyleSpanSet
            int[] r2 = r2.spanEnds
            r2 = r2[r0]
            int r3 = r14.mStart
            int r3 = r3 + r9
            if (r2 > r3) goto L_0x01b5
            goto L_0x01c0
        L_0x01b5:
            android.text.SpanSet<android.text.style.CharacterStyle> r2 = r14.mCharacterStyleSpanSet
            E[] r2 = r2.spans
            android.text.style.CharacterStyle[] r2 = (android.text.style.CharacterStyle[]) r2
            r2 = r2[r0]
            r2.updateDrawState(r1)
        L_0x01c0:
            int r0 = r0 + 1
            goto L_0x0197
        L_0x01c3:
            r14.extractDecorationInfo(r1, r11)
            if (r9 != r15) goto L_0x01e1
            r12.set(r1)
            r28 = r7
            r29 = r8
            r30 = r9
            r31 = r10
            r32 = r11
            r34 = r13
            r24 = r15
            r15 = r1
            r35 = r14
            r14 = r12
            r12 = r35
            goto L_0x0272
        L_0x01e1:
            boolean r0 = equalAttributes(r1, r12)
            if (r0 != 0) goto L_0x025e
            android.text.TextPaint r0 = r14.mPaint
            int r0 = r0.getStartHyphenEdit()
            int r0 = r14.adjustStartHyphenEdit(r13, r0)
            r12.setStartHyphenEdit(r0)
            android.text.TextPaint r0 = r14.mPaint
            int r0 = r0.getEndHyphenEdit()
            int r0 = r14.adjustEndHyphenEdit(r10, r0)
            r12.setEndHyphenEdit(r0)
            if (r48 != 0) goto L_0x020c
            r5 = r1
            r6 = r39
            if (r10 >= r6) goto L_0x0209
            goto L_0x020f
        L_0x0209:
            r24 = r16
            goto L_0x0211
        L_0x020c:
            r5 = r1
            r6 = r39
        L_0x020f:
            r24 = r17
        L_0x0211:
            int r25 = java.lang.Math.min(r10, r8)
            java.util.ArrayList<android.text.TextLine$DecorationInfo> r4 = r14.mDecorations
            r0 = r37
            r1 = r12
            r2 = r13
            r3 = r10
            r26 = r4
            r4 = r15
            r27 = r5
            r5 = r23
            r6 = r41
            r28 = r7
            r7 = r42
            r29 = r8
            r8 = r21
            r30 = r9
            r9 = r44
            r31 = r10
            r10 = r45
            r32 = r11
            r11 = r46
            r33 = r12
            r12 = r47
            r34 = r13
            r13 = r24
            r14 = r25
            r24 = r15
            r15 = r26
            float r0 = r0.handleText(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            float r21 = r21 + r0
            r13 = r30
            r15 = r27
            r14 = r33
            r14.set(r15)
            r12 = r37
            java.util.ArrayList<android.text.TextLine$DecorationInfo> r0 = r12.mDecorations
            r0.clear()
            goto L_0x0274
        L_0x025e:
            r28 = r7
            r29 = r8
            r30 = r9
            r31 = r10
            r32 = r11
            r34 = r13
            r24 = r15
            r15 = r1
            r35 = r14
            r14 = r12
            r12 = r35
        L_0x0272:
            r13 = r34
        L_0x0274:
            r10 = r28
            boolean r0 = r32.hasDecoration()
            if (r0 == 0) goto L_0x028e
            android.text.TextLine$DecorationInfo r0 = r32.copyInfo()
            r1 = r30
            r0.start = r1
            r2 = r28
            r0.end = r2
            java.util.ArrayList<android.text.TextLine$DecorationInfo> r3 = r12.mDecorations
            r3.add(r0)
            goto L_0x0292
        L_0x028e:
            r2 = r28
            r1 = r30
        L_0x0292:
            r0 = r2
            r22 = r29
            r11 = r32
            r35 = r14
            r14 = r12
            r12 = r35
            r36 = r24
            r24 = r15
            r15 = r36
            goto L_0x0174
        L_0x02a4:
            r29 = r8
            r31 = r10
            r32 = r11
            r34 = r13
            r35 = r14
            r14 = r12
            r12 = r35
            r36 = r24
            r24 = r15
            r15 = r36
            android.text.TextPaint r0 = r12.mPaint
            int r0 = r0.getStartHyphenEdit()
            int r0 = r12.adjustStartHyphenEdit(r13, r0)
            r14.setStartHyphenEdit(r0)
            android.text.TextPaint r0 = r12.mPaint
            int r0 = r0.getEndHyphenEdit()
            r11 = r31
            int r0 = r12.adjustEndHyphenEdit(r11, r0)
            r14.setEndHyphenEdit(r0)
            if (r48 != 0) goto L_0x02dd
            r10 = r39
            if (r11 >= r10) goto L_0x02da
            goto L_0x02df
        L_0x02da:
            r22 = r16
            goto L_0x02e1
        L_0x02dd:
            r10 = r39
        L_0x02df:
            r22 = r17
        L_0x02e1:
            r9 = r29
            int r25 = java.lang.Math.min(r11, r9)
            java.util.ArrayList<android.text.TextLine$DecorationInfo> r8 = r12.mDecorations
            r0 = r37
            r1 = r14
            r2 = r13
            r3 = r11
            r4 = r24
            r5 = r23
            r6 = r41
            r7 = r42
            r26 = r8
            r8 = r21
            r27 = r9
            r9 = r44
            r10 = r45
            r28 = r11
            r11 = r46
            r12 = r47
            r29 = r13
            r13 = r22
            r22 = r14
            r14 = r25
            r25 = r15
            r15 = r26
            float r0 = r0.handleText(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            float r21 = r21 + r0
            r13 = r21
        L_0x031a:
            r0 = r23
            goto L_0x00a3
        L_0x031e:
            float r0 = r13 - r19
            return r0
        L_0x0321:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "measureLimit ("
            r1.append(r2)
            r2 = r39
            r1.append(r2)
            java.lang.String r3 = ") is out of start ("
            r1.append(r3)
            r3 = r38
            r1.append(r3)
            java.lang.String r4 = ") and limit ("
            r1.append(r4)
            r4 = r40
            r1.append(r4)
            java.lang.String r5 = ") bounds"
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextLine.handleRun(int, int, int, boolean, android.graphics.Canvas, float, int, int, int, android.graphics.Paint$FontMetricsInt, boolean):float");
    }

    private void drawTextRun(Canvas c, TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, float x, int y) {
        int i = y;
        if (this.mCharsValid) {
            c.drawTextRun(this.mChars, start, end - start, contextStart, contextEnd - contextStart, x, (float) i, runIsRtl, (Paint) wp);
            return;
        }
        int delta = this.mStart;
        c.drawTextRun(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, x, (float) i, runIsRtl, (Paint) wp);
    }

    /* access modifiers changed from: package-private */
    public float nextTab(float h) {
        if (this.mTabs != null) {
            return this.mTabs.nextTab(h);
        }
        return Layout.TabStops.nextDefaultStop(h, 20.0f);
    }

    private boolean isStretchableWhitespace(int ch) {
        return ch == 32;
    }

    private int countStretchableSpaces(int start, int end) {
        int count = 0;
        for (int i = start; i < end; i++) {
            if (isStretchableWhitespace(this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i))) {
                count++;
            }
        }
        return count;
    }

    public static boolean isLineEndSpace(char ch) {
        return ch == ' ' || ch == 9 || ch == 5760 || (8192 <= ch && ch <= 8202 && ch != 8199) || ch == 8287 || ch == 12288;
    }

    private static boolean equalAttributes(TextPaint lp, TextPaint rp) {
        return lp.getColorFilter() == rp.getColorFilter() && lp.getMaskFilter() == rp.getMaskFilter() && lp.getShader() == rp.getShader() && lp.getTypeface() == rp.getTypeface() && lp.getXfermode() == rp.getXfermode() && lp.getTextLocales().equals(rp.getTextLocales()) && TextUtils.equals(lp.getFontFeatureSettings(), rp.getFontFeatureSettings()) && TextUtils.equals(lp.getFontVariationSettings(), rp.getFontVariationSettings()) && lp.getShadowLayerRadius() == rp.getShadowLayerRadius() && lp.getShadowLayerDx() == rp.getShadowLayerDx() && lp.getShadowLayerDy() == rp.getShadowLayerDy() && lp.getShadowLayerColor() == rp.getShadowLayerColor() && lp.getFlags() == rp.getFlags() && lp.getHinting() == rp.getHinting() && lp.getStyle() == rp.getStyle() && lp.getColor() == rp.getColor() && lp.getStrokeWidth() == rp.getStrokeWidth() && lp.getStrokeMiter() == rp.getStrokeMiter() && lp.getStrokeCap() == rp.getStrokeCap() && lp.getStrokeJoin() == rp.getStrokeJoin() && lp.getTextAlign() == rp.getTextAlign() && lp.isElegantTextHeight() == rp.isElegantTextHeight() && lp.getTextSize() == rp.getTextSize() && lp.getTextScaleX() == rp.getTextScaleX() && lp.getTextSkewX() == rp.getTextSkewX() && lp.getLetterSpacing() == rp.getLetterSpacing() && lp.getWordSpacing() == rp.getWordSpacing() && lp.getStartHyphenEdit() == rp.getStartHyphenEdit() && lp.getEndHyphenEdit() == rp.getEndHyphenEdit() && lp.bgColor == rp.bgColor && lp.baselineShift == rp.baselineShift && lp.linkColor == rp.linkColor && lp.drawableState == rp.drawableState && lp.density == rp.density && lp.underlineColor == rp.underlineColor && lp.underlineThickness == rp.underlineThickness;
    }
}
