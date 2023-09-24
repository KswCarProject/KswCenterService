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
/* loaded from: classes4.dex */
public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private static final TextLine[] sCached = new TextLine[3];
    private float mAddedWidthForJustify;
    private char[] mChars;
    private boolean mCharsValid;
    private PrecomputedText mComputed;
    private int mDir;
    private Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    private TextPaint mPaint;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private Spanned mSpanned;
    private int mStart;
    private Layout.TabStops mTabs;
    @UnsupportedAppUsage
    private CharSequence mText;
    private final TextPaint mWorkPaint = new TextPaint();
    private final TextPaint mActivePaint = new TextPaint();
    @UnsupportedAppUsage
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet = new SpanSet<>(MetricAffectingSpan.class);
    @UnsupportedAppUsage
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<>(CharacterStyle.class);
    @UnsupportedAppUsage
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet = new SpanSet<>(ReplacementSpan.class);
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList<>();

    @UnsupportedAppUsage
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public static TextLine obtain() {
        synchronized (sCached) {
            int i = sCached.length;
            do {
                i--;
                if (i < 0) {
                    TextLine tl = new TextLine();
                    return tl;
                }
            } while (sCached[i] == null);
            TextLine tl2 = sCached[i];
            sCached[i] = null;
            return tl2;
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
                } else if (sCached[i] != null) {
                    i++;
                } else {
                    sCached[i] = tl;
                    break;
                }
            }
        }
        return null;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void set(TextPaint paint, CharSequence text, int start, int limit, int dir, Layout.Directions directions, boolean hasTabs, Layout.TabStops tabStops, int ellipsisStart, int ellipsisEnd) {
        this.mPaint = paint;
        this.mText = text;
        this.mStart = start;
        this.mLen = limit - start;
        this.mDir = dir;
        this.mDirections = directions;
        if (this.mDirections == null) {
            throw new IllegalArgumentException("Directions cannot be null");
        }
        this.mHasTabs = hasTabs;
        this.mSpanned = null;
        boolean hasReplacement = false;
        int i = 1;
        if (text instanceof Spanned) {
            this.mSpanned = (Spanned) text;
            this.mReplacementSpanSpanSet.init(this.mSpanned, start, limit);
            hasReplacement = this.mReplacementSpanSpanSet.numberOfSpans > 0;
        }
        this.mComputed = null;
        if (text instanceof PrecomputedText) {
            this.mComputed = (PrecomputedText) text;
            if (!this.mComputed.getParams().getTextPaint().equalsForTextMeasurement(paint)) {
                this.mComputed = null;
            }
        }
        this.mCharsValid = hasReplacement;
        if (this.mCharsValid) {
            if (this.mChars == null || this.mChars.length < this.mLen) {
                this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
            }
            TextUtils.getChars(text, start, limit, this.mChars, 0);
            if (hasReplacement) {
                char[] chars = this.mChars;
                int i2 = start;
                while (i2 < limit) {
                    int inext = this.mReplacementSpanSpanSet.getNextTransition(i2, limit);
                    if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i2, inext) && (i2 - start >= ellipsisEnd || inext - start <= ellipsisStart)) {
                        chars[i2 - start] = '\ufffc';
                        int j = (i2 - start) + i;
                        int e = inext - start;
                        while (true) {
                            int e2 = e;
                            if (j < e2) {
                                chars[j] = '\ufeff';
                                j++;
                                e = e2;
                            }
                        }
                    }
                    i2 = inext;
                    i = 1;
                }
            }
        }
        this.mTabs = tabStops;
        this.mAddedWidthForJustify = 0.0f;
        this.mIsJustifying = false;
        this.mEllipsisStart = ellipsisStart != ellipsisEnd ? ellipsisStart : 0;
        this.mEllipsisEnd = ellipsisStart != ellipsisEnd ? ellipsisEnd : 0;
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
            float width = Math.abs(measure(end, false, null));
            this.mAddedWidthForJustify = (justifyWidth - width) / spaces;
            this.mIsJustifying = true;
        }
    }

    void draw(Canvas c, float x, int top, int y, int bottom) {
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
                int j2 = this.mHasTabs ? runStart : runLimit;
                int segStart = runStart;
                float h2 = h;
                while (true) {
                    int j3 = j2;
                    if (j3 <= runLimit) {
                        if (j3 != runLimit && charAt(j3) != '\t') {
                            j = j3;
                        } else {
                            j = j3;
                            h2 += drawRun(c, segStart, j3, runIsRtl, x + h2, top, y, bottom, (runIndex2 == runCount + (-1) && j3 == this.mLen) ? false : true);
                            if (j != runLimit) {
                                h2 = this.mDir * nextTab(this.mDir * h2);
                            }
                            segStart = j + 1;
                        }
                        j2 = j + 1;
                    }
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
        boolean z;
        boolean z2;
        int j;
        if (offset > this.mLen) {
            throw new IndexOutOfBoundsException("offset(" + offset + ") should be less than line limit(" + this.mLen + ")");
        }
        int target = trailing ? offset - 1 : offset;
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
                int j3 = this.mHasTabs ? runStart : runLimit;
                int segStart = runStart;
                h = h2;
                while (true) {
                    int j4 = j3;
                    if (j4 <= runLimit) {
                        if (j4 != runLimit && charAt(j4) != '\t') {
                            j = j4;
                        } else {
                            boolean z3 = true;
                            if (target < segStart || target >= j4) {
                                z = false;
                            } else {
                                z = true;
                            }
                            boolean targetIsInThisSegment = z;
                            if (this.mDir != -1) {
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            if (z2 != runIsRtl) {
                                z3 = false;
                            }
                            boolean sameDirection = z3;
                            if (targetIsInThisSegment && sameDirection) {
                                return measureRun(segStart, offset, j4, runIsRtl, fmi) + h;
                            }
                            float segmentWidth = measureRun(segStart, j4, j4, runIsRtl, fmi);
                            h += sameDirection ? segmentWidth : -segmentWidth;
                            if (targetIsInThisSegment) {
                                return measureRun(segStart, offset, j4, runIsRtl, null) + h;
                            }
                            j = j4;
                            if (j != runLimit) {
                                if (offset != j) {
                                    float h3 = this.mDir * nextTab(this.mDir * h);
                                    if (target == j) {
                                        return h3;
                                    }
                                    h = h3;
                                } else {
                                    return h;
                                }
                            }
                            segStart = j + 1;
                        }
                        j3 = j + 1;
                    }
                }
            } else {
                return h2;
            }
            j2 = runIndex + 1;
            h2 = h;
        }
    }

    @VisibleForTesting
    public float[] measureAllOffsets(boolean[] trailing, Paint.FontMetricsInt fmi) {
        int j;
        int offset;
        float w;
        boolean z = true;
        float[] measurement = new float[this.mLen + 1];
        int[] target = new int[this.mLen + 1];
        boolean z2 = false;
        for (int offset2 = 0; offset2 < target.length; offset2++) {
            target[offset2] = trailing[offset2] ? offset2 - 1 : offset2;
        }
        int offset3 = target[0];
        if (offset3 < 0) {
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
            int j2 = this.mHasTabs ? runStart : runLimit;
            int segStart = runStart;
            float h2 = h;
            while (true) {
                int j3 = j2;
                if (j3 <= runLimit) {
                    if (j3 != runLimit && charAt(j3) != '\t') {
                        j = j3;
                    } else {
                        float oldh = h2;
                        boolean advance = (this.mDir == -1 ? z : z2) == runIsRtl ? z : z2;
                        j = j3;
                        int segStart2 = segStart;
                        float w2 = measureRun(segStart, j3, j3, runIsRtl, fmi);
                        h2 += advance ? w2 : -w2;
                        float baseh = advance ? oldh : h2;
                        Paint.FontMetricsInt crtfmi = advance ? fmi : null;
                        int offset4 = segStart2;
                        while (true) {
                            int offset5 = offset4;
                            if (offset5 > j || offset5 > this.mLen) {
                                break;
                            }
                            if (target[offset5] < segStart2 || target[offset5] >= j) {
                                offset = offset5;
                                w = w2;
                            } else {
                                offset = offset5;
                                w = w2;
                                measurement[offset] = baseh + measureRun(segStart2, offset5, j, runIsRtl, crtfmi);
                            }
                            offset4 = offset + 1;
                            w2 = w;
                        }
                        if (j != runLimit) {
                            if (target[j] == j) {
                                measurement[j] = h2;
                            }
                            float h3 = this.mDir * nextTab(this.mDir * h2);
                            if (target[j + 1] == j) {
                                measurement[j + 1] = h3;
                            }
                            h2 = h3;
                        }
                        segStart = j + 1;
                    }
                    j2 = j + 1;
                    z = true;
                    z2 = false;
                }
            }
            runIndex = runIndex2 + 1;
            h = h2;
            z = true;
            z2 = false;
        }
        if (target[this.mLen] == this.mLen) {
            measurement[this.mLen] = h;
        }
        return measurement;
    }

    private float drawRun(Canvas c, int start, int limit, boolean runIsRtl, float x, int top, int y, int bottom, boolean needWidth) {
        if ((this.mDir == 1) == runIsRtl) {
            float w = -measureRun(start, limit, limit, runIsRtl, null);
            handleRun(start, limit, limit, runIsRtl, c, x + w, top, y, bottom, null, false);
            return w;
        }
        return handleRun(start, limit, limit, runIsRtl, c, x, top, y, bottom, null, needWidth);
    }

    private float measureRun(int start, int offset, int limit, boolean runIsRtl, Paint.FontMetricsInt fmi) {
        return handleRun(start, offset, limit, runIsRtl, null, 0.0f, 0, 0, 0, fmi, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:109:0x0178, code lost:
        r1 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x017b, code lost:
        if (r13 != (-1)) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x017d, code lost:
        if (r0 == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x017f, code lost:
        r1 = r27.mLen + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0184, code lost:
        r13 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0186, code lost:
        if (r13 > r11) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0188, code lost:
        if (r0 == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x018a, code lost:
        r1 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x018c, code lost:
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x018d, code lost:
        r13 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x018e, code lost:
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:?, code lost:
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:?, code lost:
        return r13;
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0178 A[ADDED_TO_REGION, EDGE_INSN: B:120:0x0178->B:109:0x0178 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int getOffsetToLeftRightOf(int cursor, boolean toLeft) {
        int runLevel;
        int runIndex;
        int runStart;
        boolean trailing;
        int runLimit;
        int[] runs;
        int runLevel2;
        int newCaret;
        int runStart2;
        int runStart3;
        int length;
        int otherRunIndex;
        int otherRunLevel;
        int lineEnd = this.mLen;
        boolean paraIsRtl = this.mDir == -1;
        int[] runs2 = this.mDirections.mDirections;
        boolean trailing2 = false;
        if (cursor == 0) {
            length = -2;
        } else if (cursor == lineEnd) {
            length = runs2.length;
        } else {
            int runLimit2 = lineEnd;
            int runLimit3 = 0;
            int runIndex2 = 0;
            while (true) {
                if (runIndex2 < runs2.length) {
                    int runStart4 = 0 + runs2[runIndex2];
                    if (cursor >= runStart4) {
                        int runLimit4 = (runs2[runIndex2 + 1] & 67108863) + runStart4;
                        if (runLimit4 > lineEnd) {
                            runLimit4 = lineEnd;
                        }
                        if (cursor >= runLimit4) {
                            runStart2 = runStart4;
                            runLimit2 = runLimit4;
                        } else {
                            int runLevel3 = (runs2[runIndex2 + 1] >>> 26) & 63;
                            if (cursor != runStart4) {
                                runLevel = runLevel3;
                                runIndex = runIndex2;
                                runLimit = runLimit4;
                                runStart = runStart4;
                                trailing = false;
                            } else {
                                int pos = cursor - 1;
                                int prevRunIndex = 0;
                                while (true) {
                                    int prevRunIndex2 = prevRunIndex;
                                    if (prevRunIndex2 < runs2.length) {
                                        int prevRunStart = runs2[prevRunIndex2] + 0;
                                        if (pos >= prevRunStart) {
                                            int prevRunLimit = prevRunStart + (runs2[prevRunIndex2 + 1] & 67108863);
                                            if (prevRunLimit > lineEnd) {
                                                prevRunLimit = lineEnd;
                                            }
                                            if (pos < prevRunLimit) {
                                                runStart3 = runStart4;
                                                int runStart5 = (runs2[prevRunIndex2 + 1] >>> 26) & 63;
                                                if (runStart5 < runLevel3) {
                                                    runIndex2 = prevRunIndex2;
                                                    runLevel3 = runStart5;
                                                    runStart = prevRunStart;
                                                    runLimit4 = prevRunLimit;
                                                    trailing2 = true;
                                                    break;
                                                }
                                                prevRunIndex = prevRunIndex2 + 2;
                                                runStart4 = runStart3;
                                            }
                                        }
                                        runStart3 = runStart4;
                                        prevRunIndex = prevRunIndex2 + 2;
                                        runStart4 = runStart3;
                                    } else {
                                        runStart = runStart4;
                                        break;
                                    }
                                }
                                runLevel = runLevel3;
                                runIndex = runIndex2;
                                runLimit = runLimit4;
                                trailing = trailing2;
                            }
                        }
                    } else {
                        runStart2 = runStart4;
                    }
                    runIndex2 += 2;
                    runLimit3 = runStart2;
                } else {
                    runLevel = 0;
                    runIndex = runIndex2;
                    runStart = runLimit3;
                    trailing = false;
                    runLimit = runLimit2;
                    break;
                }
            }
            int runLevel4 = runs2.length;
            if (runIndex != runLevel4) {
                boolean runIsRtl = (runLevel & 1) != 0;
                boolean advance = toLeft == runIsRtl;
                if (cursor == (advance ? runLimit : runStart) && advance == trailing) {
                    runs = runs2;
                } else {
                    runs = runs2;
                    int newCaret2 = getOffsetBeforeAfter(runIndex, runStart, runLimit, runIsRtl, cursor, advance);
                    if (newCaret2 != (advance ? runLimit : runStart)) {
                        return newCaret2;
                    }
                    runLevel2 = runLevel;
                    newCaret = newCaret2;
                    while (true) {
                        boolean advance2 = toLeft == paraIsRtl;
                        otherRunIndex = runIndex + (advance2 ? 2 : -2);
                        if (otherRunIndex >= 0 || otherRunIndex >= runs.length) {
                            break;
                        }
                        int otherRunStart = 0 + runs[otherRunIndex];
                        int otherRunLimit = otherRunStart + (runs[otherRunIndex + 1] & 67108863);
                        if (otherRunLimit > lineEnd) {
                            otherRunLimit = lineEnd;
                        }
                        int otherRunLimit2 = otherRunLimit;
                        otherRunLevel = (runs[otherRunIndex + 1] >>> 26) & 63;
                        boolean otherRunIsRtl = (otherRunLevel & 1) != 0;
                        boolean advance3 = toLeft == otherRunIsRtl;
                        if (newCaret != -1) {
                            if (otherRunLevel < runLevel2) {
                                int newCaret3 = advance3 ? otherRunStart : otherRunLimit2;
                                return newCaret3;
                            }
                            return newCaret;
                        }
                        newCaret = getOffsetBeforeAfter(otherRunIndex, otherRunStart, otherRunLimit2, otherRunIsRtl, advance3 ? otherRunStart : otherRunLimit2, advance3);
                        if (newCaret == (advance3 ? otherRunLimit2 : otherRunStart)) {
                            runIndex = otherRunIndex;
                            runLevel2 = otherRunLevel;
                        } else {
                            return newCaret;
                        }
                    }
                }
            } else {
                runs = runs2;
            }
            runLevel2 = runLevel;
            newCaret = -1;
            while (true) {
                if (toLeft == paraIsRtl) {
                }
                otherRunIndex = runIndex + (advance2 ? 2 : -2);
                if (otherRunIndex >= 0) {
                    break;
                }
                break;
                runIndex = otherRunIndex;
                runLevel2 = otherRunLevel;
            }
        }
        runLevel2 = 0;
        runIndex = length;
        runs = runs2;
        newCaret = -1;
        while (true) {
            if (toLeft == paraIsRtl) {
            }
            otherRunIndex = runIndex + (advance2 ? 2 : -2);
            if (otherRunIndex >= 0) {
            }
            runIndex = otherRunIndex;
            runLevel2 = otherRunLevel;
        }
    }

    private int getOffsetBeforeAfter(int runIndex, int runStart, int runLimit, boolean runIsRtl, int offset, boolean after) {
        int spanLimit;
        int spanStart;
        int spanLimit2;
        if (runIndex >= 0) {
            if (offset != (after ? this.mLen : 0)) {
                TextPaint wp = this.mWorkPaint;
                wp.set(this.mPaint);
                if (this.mIsJustifying) {
                    wp.setWordSpacing(this.mAddedWidthForJustify);
                }
                int spanStart2 = runStart;
                if (this.mSpanned != null) {
                    int target = after ? offset + 1 : offset;
                    int limit = this.mStart + runLimit;
                    while (true) {
                        spanLimit = this.mSpanned.nextSpanTransition(this.mStart + spanStart2, limit, MetricAffectingSpan.class) - this.mStart;
                        if (spanLimit >= target) {
                            break;
                        }
                        spanStart2 = spanLimit;
                    }
                    MetricAffectingSpan[] spans = (MetricAffectingSpan[]) TextUtils.removeEmptySpans((MetricAffectingSpan[]) this.mSpanned.getSpans(this.mStart + spanStart2, this.mStart + spanLimit, MetricAffectingSpan.class), this.mSpanned, MetricAffectingSpan.class);
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
                            return after ? spanLimit : spanStart2;
                        }
                    }
                    spanStart = spanStart2;
                    spanLimit2 = spanLimit;
                } else {
                    spanStart = spanStart2;
                    spanLimit2 = runLimit;
                }
                int cursorOpt = after ? 0 : 2;
                if (!this.mCharsValid) {
                    return wp.getTextRunCursor(this.mText, this.mStart + spanStart, this.mStart + spanLimit2, runIsRtl, this.mStart + offset, cursorOpt) - this.mStart;
                }
                return wp.getTextRunCursor(this.mChars, spanStart, spanLimit2 - spanStart, runIsRtl, offset, cursorOpt);
            }
        }
        return after ? TextUtils.getOffsetAfter(this.mText, this.mStart + offset) - this.mStart : TextUtils.getOffsetBefore(this.mText, this.mStart + offset) - this.mStart;
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
        float strokeTop = baseline + wp.baselineShift + position;
        int previousColor = wp.getColor();
        Paint.Style previousStyle = wp.getStyle();
        boolean previousAntiAlias = wp.isAntiAlias();
        wp.setStyle(Paint.Style.FILL);
        wp.setAntiAlias(true);
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
        if (this.mComputed == null) {
            return wp.getRunAdvance(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, runIsRtl, delta + offset);
        }
        return this.mComputed.getWidth(start + delta, end + delta);
    }

    private float handleText(TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth, int offset, ArrayList<DecorationInfo> decorations) {
        int numDecorations;
        float totalWidth;
        float leftX;
        float rightX;
        float decorationXLeft;
        float f;
        int i;
        int i2 = start;
        int i3 = y;
        ArrayList<DecorationInfo> arrayList = decorations;
        if (this.mIsJustifying) {
            wp.setWordSpacing(this.mAddedWidthForJustify);
        }
        if (fmi != null) {
            expandMetricsFromPaint(fmi, wp);
        }
        if (end == i2) {
            return 0.0f;
        }
        float totalWidth2 = 0.0f;
        int i4 = 0;
        int numDecorations2 = arrayList == null ? 0 : decorations.size();
        if (needWidth || (c != null && (wp.bgColor != 0 || numDecorations2 != 0 || runIsRtl))) {
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
            if (wp.bgColor != 0) {
                int previousColor = wp.getColor();
                Paint.Style previousStyle = wp.getStyle();
                wp.setColor(wp.bgColor);
                wp.setStyle(Paint.Style.FILL);
                c.drawRect(leftX2, top, rightX2, bottom, wp);
                wp.setStyle(previousStyle);
                wp.setColor(previousColor);
            }
            totalWidth = totalWidth2;
            drawTextRun(c, wp, start, end, contextStart, contextEnd, runIsRtl, leftX2, i3 + wp.baselineShift);
            if (numDecorations != 0) {
                while (true) {
                    int i5 = i4;
                    if (i5 >= numDecorations) {
                        break;
                    }
                    DecorationInfo info = arrayList.get(i5);
                    int decorationStart = Math.max(info.start, i2);
                    int decorationEnd = Math.min(info.end, offset);
                    float decorationStartAdvance = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, decorationStart);
                    float decorationEndAdvance = getRunAdvance(wp, start, end, contextStart, contextEnd, runIsRtl, decorationEnd);
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
                        drawStroke(wp, c, info.underlineColor, wp.getUnderlinePosition(), info.underlineThickness, decorationXLeft2, decorationXRight, i3);
                    }
                    if (info.isUnderlineText) {
                        float thickness = Math.max(wp.getUnderlineThickness(), 1.0f);
                        i = i3;
                        drawStroke(wp, c, wp.getColor(), wp.getUnderlinePosition(), thickness, decorationXLeft2, decorationXRight, i3);
                    } else {
                        i = i3;
                    }
                    if (info.isStrikeThruText) {
                        float thickness2 = Math.max(wp.getStrikeThruThickness(), 1.0f);
                        drawStroke(wp, c, wp.getColor(), wp.getStrikeThruPosition(), thickness2, decorationXLeft2, decorationXRight, i);
                    }
                    i4 = i5 + 1;
                    i2 = start;
                    arrayList = decorations;
                    i3 = i;
                }
            }
        } else {
            totalWidth = totalWidth2;
        }
        return runIsRtl ? -totalWidth : totalWidth;
    }

    private float handleReplacement(ReplacementSpan replacement, TextPaint wp, int start, int limit, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth) {
        float ret;
        float ret2 = 0.0f;
        int textStart = this.mStart + start;
        int textLimit = this.mStart + limit;
        if (needWidth || (c != null && runIsRtl)) {
            int previousTop = 0;
            int previousAscent = 0;
            int previousDescent = 0;
            int previousBottom = 0;
            int previousLeading = 0;
            boolean needUpdateMetrics = fmi != null;
            if (needUpdateMetrics) {
                previousTop = fmi.top;
                previousAscent = fmi.ascent;
                previousDescent = fmi.descent;
                previousBottom = fmi.bottom;
                previousLeading = fmi.leading;
            }
            int previousTop2 = previousTop;
            int previousAscent2 = previousAscent;
            int previousDescent2 = previousDescent;
            int previousBottom2 = previousBottom;
            int previousLeading2 = previousLeading;
            ret2 = replacement.getSize(wp, this.mText, textStart, textLimit, fmi);
            if (needUpdateMetrics) {
                updateMetrics(fmi, previousTop2, previousAscent2, previousDescent2, previousBottom2, previousLeading2);
            }
        }
        float ret3 = ret2;
        if (c != null) {
            ret = ret3;
            replacement.draw(c, this.mText, textStart, textLimit, runIsRtl ? x - ret3 : x, top, y, bottom, wp);
        } else {
            ret = ret3;
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

    /* loaded from: classes4.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:88:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x028e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float handleRun(int start, int measureLimit, int limit, boolean runIsRtl, Canvas c, float x, int top, int y, int bottom, Paint.FontMetricsInt fmi, boolean needWidth) {
        boolean needsSpanMeasurement;
        int inext;
        int mlimit;
        boolean z;
        int jnext;
        int mlimit2;
        int j;
        DecorationInfo decorationInfo;
        int i;
        int i2;
        TextPaint wp;
        TextPaint activePaint;
        TextLine textLine;
        TextPaint wp2;
        boolean z2;
        int jnext2;
        if (measureLimit < start || measureLimit > limit) {
            throw new IndexOutOfBoundsException("measureLimit (" + measureLimit + ") is out of start (" + start + ") and limit (" + limit + ") bounds");
        } else if (start == measureLimit) {
            TextPaint wp3 = this.mWorkPaint;
            wp3.set(this.mPaint);
            if (fmi != null) {
                expandMetricsFromPaint(fmi, wp3);
                return 0.0f;
            }
            return 0.0f;
        } else {
            if (this.mSpanned != null) {
                this.mMetricAffectingSpanSpanSet.init(this.mSpanned, this.mStart + start, this.mStart + limit);
                this.mCharacterStyleSpanSet.init(this.mSpanned, this.mStart + start, this.mStart + limit);
                needsSpanMeasurement = (this.mMetricAffectingSpanSpanSet.numberOfSpans == 0 && this.mCharacterStyleSpanSet.numberOfSpans == 0) ? false : true;
            } else {
                needsSpanMeasurement = false;
            }
            if (!needsSpanMeasurement) {
                TextPaint wp4 = this.mWorkPaint;
                wp4.set(this.mPaint);
                wp4.setStartHyphenEdit(adjustStartHyphenEdit(start, wp4.getStartHyphenEdit()));
                wp4.setEndHyphenEdit(adjustEndHyphenEdit(limit, wp4.getEndHyphenEdit()));
                return handleText(wp4, start, limit, start, limit, runIsRtl, c, x, top, y, bottom, fmi, needWidth, measureLimit, null);
            }
            float x2 = x;
            int i3 = start;
            while (true) {
                int i4 = i3;
                if (i4 < measureLimit) {
                    TextPaint wp5 = this.mWorkPaint;
                    wp5.set(this.mPaint);
                    int inext2 = this.mMetricAffectingSpanSpanSet.getNextTransition(this.mStart + i4, this.mStart + limit) - this.mStart;
                    int mlimit3 = Math.min(inext2, measureLimit);
                    ReplacementSpan replacement = null;
                    for (int j2 = 0; j2 < this.mMetricAffectingSpanSpanSet.numberOfSpans; j2++) {
                        if (this.mMetricAffectingSpanSpanSet.spanStarts[j2] < this.mStart + mlimit3 && this.mMetricAffectingSpanSpanSet.spanEnds[j2] > this.mStart + i4) {
                            boolean insideEllipsis = this.mStart + this.mEllipsisStart <= this.mMetricAffectingSpanSpanSet.spanStarts[j2] && this.mMetricAffectingSpanSpanSet.spanEnds[j2] <= this.mStart + this.mEllipsisEnd;
                            MetricAffectingSpan span = this.mMetricAffectingSpanSpanSet.spans[j2];
                            if (span instanceof ReplacementSpan) {
                                replacement = !insideEllipsis ? (ReplacementSpan) span : null;
                            } else {
                                span.updateDrawState(wp5);
                            }
                        }
                    }
                    if (replacement != null) {
                        inext = inext2;
                        x2 += handleReplacement(replacement, wp5, i4, mlimit3, runIsRtl, c, x2, top, y, bottom, fmi, needWidth || mlimit3 < measureLimit);
                    } else {
                        int mlimit4 = mlimit3;
                        inext = inext2;
                        TextPaint wp6 = wp5;
                        TextLine textLine2 = this;
                        TextPaint activePaint2 = textLine2.mActivePaint;
                        activePaint2.set(textLine2.mPaint);
                        int activeStart = i4;
                        DecorationInfo decorationInfo2 = textLine2.mDecorationInfo;
                        textLine2.mDecorations.clear();
                        int activeEnd = mlimit4;
                        float x3 = x2;
                        int activeStart2 = activeStart;
                        while (true) {
                            int j3 = activeStart;
                            mlimit = mlimit4;
                            if (j3 >= mlimit) {
                                break;
                            }
                            int jnext3 = textLine2.mCharacterStyleSpanSet.getNextTransition(textLine2.mStart + j3, textLine2.mStart + inext) - textLine2.mStart;
                            int offset = Math.min(jnext3, mlimit);
                            TextPaint wp7 = wp6;
                            wp7.set(textLine2.mPaint);
                            for (int k = 0; k < textLine2.mCharacterStyleSpanSet.numberOfSpans; k++) {
                                if (textLine2.mCharacterStyleSpanSet.spanStarts[k] < textLine2.mStart + offset && textLine2.mCharacterStyleSpanSet.spanEnds[k] > textLine2.mStart + j3) {
                                    textLine2.mCharacterStyleSpanSet.spans[k].updateDrawState(wp7);
                                }
                            }
                            textLine2.extractDecorationInfo(wp7, decorationInfo2);
                            if (j3 == i4) {
                                activePaint2.set(wp7);
                                jnext = jnext3;
                                mlimit2 = mlimit;
                                j = j3;
                                decorationInfo = decorationInfo2;
                                i = activeStart2;
                                i2 = i4;
                                wp = wp7;
                                TextLine textLine3 = textLine2;
                                activePaint = activePaint2;
                                textLine = textLine3;
                            } else if (!equalAttributes(wp7, activePaint2)) {
                                activePaint2.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(activeStart2, textLine2.mPaint.getStartHyphenEdit()));
                                activePaint2.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(activeEnd, textLine2.mPaint.getEndHyphenEdit()));
                                if (needWidth) {
                                    wp2 = wp7;
                                } else {
                                    wp2 = wp7;
                                    if (activeEnd >= measureLimit) {
                                        z2 = false;
                                        jnext = jnext3;
                                        mlimit2 = mlimit;
                                        j = j3;
                                        decorationInfo = decorationInfo2;
                                        boolean z3 = z2;
                                        i2 = i4;
                                        x3 += handleText(activePaint2, activeStart2, activeEnd, i4, inext, runIsRtl, c, x3, top, y, bottom, fmi, z3, Math.min(activeEnd, mlimit), textLine2.mDecorations);
                                        activeStart2 = j;
                                        wp = wp2;
                                        activePaint = activePaint2;
                                        activePaint.set(wp);
                                        textLine = this;
                                        textLine.mDecorations.clear();
                                        activeEnd = jnext;
                                        if (!decorationInfo.hasDecoration()) {
                                            jnext2 = jnext;
                                        } else {
                                            DecorationInfo copy = decorationInfo.copyInfo();
                                            copy.start = j;
                                            jnext2 = jnext;
                                            copy.end = jnext2;
                                            textLine.mDecorations.add(copy);
                                        }
                                        activeStart = jnext2;
                                        mlimit4 = mlimit2;
                                        decorationInfo2 = decorationInfo;
                                        TextPaint textPaint = activePaint;
                                        textLine2 = textLine;
                                        activePaint2 = textPaint;
                                        int i5 = i2;
                                        wp6 = wp;
                                        i4 = i5;
                                    }
                                }
                                z2 = true;
                                jnext = jnext3;
                                mlimit2 = mlimit;
                                j = j3;
                                decorationInfo = decorationInfo2;
                                boolean z32 = z2;
                                i2 = i4;
                                x3 += handleText(activePaint2, activeStart2, activeEnd, i4, inext, runIsRtl, c, x3, top, y, bottom, fmi, z32, Math.min(activeEnd, mlimit), textLine2.mDecorations);
                                activeStart2 = j;
                                wp = wp2;
                                activePaint = activePaint2;
                                activePaint.set(wp);
                                textLine = this;
                                textLine.mDecorations.clear();
                                activeEnd = jnext;
                                if (!decorationInfo.hasDecoration()) {
                                }
                                activeStart = jnext2;
                                mlimit4 = mlimit2;
                                decorationInfo2 = decorationInfo;
                                TextPaint textPaint2 = activePaint;
                                textLine2 = textLine;
                                activePaint2 = textPaint2;
                                int i52 = i2;
                                wp6 = wp;
                                i4 = i52;
                            } else {
                                jnext = jnext3;
                                mlimit2 = mlimit;
                                j = j3;
                                decorationInfo = decorationInfo2;
                                i = activeStart2;
                                i2 = i4;
                                wp = wp7;
                                TextLine textLine4 = textLine2;
                                activePaint = activePaint2;
                                textLine = textLine4;
                            }
                            activeStart2 = i;
                            activeEnd = jnext;
                            if (!decorationInfo.hasDecoration()) {
                            }
                            activeStart = jnext2;
                            mlimit4 = mlimit2;
                            decorationInfo2 = decorationInfo;
                            TextPaint textPaint22 = activePaint;
                            textLine2 = textLine;
                            activePaint2 = textPaint22;
                            int i522 = i2;
                            wp6 = wp;
                            i4 = i522;
                        }
                        int activeEnd2 = activeEnd;
                        TextLine textLine5 = textLine2;
                        TextPaint activePaint3 = activePaint2;
                        int i6 = i4;
                        activePaint3.setStartHyphenEdit(textLine5.adjustStartHyphenEdit(activeStart2, textLine5.mPaint.getStartHyphenEdit()));
                        activePaint3.setEndHyphenEdit(textLine5.adjustEndHyphenEdit(activeEnd2, textLine5.mPaint.getEndHyphenEdit()));
                        if (!needWidth && activeEnd2 >= measureLimit) {
                            z = false;
                            x2 = x3 + handleText(activePaint3, activeStart2, activeEnd2, i6, inext, runIsRtl, c, x3, top, y, bottom, fmi, z, Math.min(activeEnd2, mlimit), textLine5.mDecorations);
                        }
                        z = true;
                        x2 = x3 + handleText(activePaint3, activeStart2, activeEnd2, i6, inext, runIsRtl, c, x3, top, y, bottom, fmi, z, Math.min(activeEnd2, mlimit), textLine5.mDecorations);
                    }
                    i3 = inext;
                } else {
                    return x2 - x;
                }
            }
        }
    }

    private void drawTextRun(Canvas c, TextPaint wp, int start, int end, int contextStart, int contextEnd, boolean runIsRtl, float x, int y) {
        if (this.mCharsValid) {
            int count = end - start;
            int contextCount = contextEnd - contextStart;
            c.drawTextRun(this.mChars, start, count, contextStart, contextCount, x, y, runIsRtl, wp);
            return;
        }
        int delta = this.mStart;
        c.drawTextRun(this.mText, delta + start, delta + end, delta + contextStart, delta + contextEnd, x, y, runIsRtl, wp);
    }

    float nextTab(float h) {
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
        for (int count2 = start; count2 < end; count2++) {
            char c = this.mCharsValid ? this.mChars[count2] : this.mText.charAt(this.mStart + count2);
            if (isStretchableWhitespace(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isLineEndSpace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\u1680' || ('\u2000' <= ch && ch <= '\u200a' && ch != '\u2007') || ch == '\u205f' || ch == '\u3000';
    }

    private static boolean equalAttributes(TextPaint lp, TextPaint rp) {
        return lp.getColorFilter() == rp.getColorFilter() && lp.getMaskFilter() == rp.getMaskFilter() && lp.getShader() == rp.getShader() && lp.getTypeface() == rp.getTypeface() && lp.getXfermode() == rp.getXfermode() && lp.getTextLocales().equals(rp.getTextLocales()) && TextUtils.equals(lp.getFontFeatureSettings(), rp.getFontFeatureSettings()) && TextUtils.equals(lp.getFontVariationSettings(), rp.getFontVariationSettings()) && lp.getShadowLayerRadius() == rp.getShadowLayerRadius() && lp.getShadowLayerDx() == rp.getShadowLayerDx() && lp.getShadowLayerDy() == rp.getShadowLayerDy() && lp.getShadowLayerColor() == rp.getShadowLayerColor() && lp.getFlags() == rp.getFlags() && lp.getHinting() == rp.getHinting() && lp.getStyle() == rp.getStyle() && lp.getColor() == rp.getColor() && lp.getStrokeWidth() == rp.getStrokeWidth() && lp.getStrokeMiter() == rp.getStrokeMiter() && lp.getStrokeCap() == rp.getStrokeCap() && lp.getStrokeJoin() == rp.getStrokeJoin() && lp.getTextAlign() == rp.getTextAlign() && lp.isElegantTextHeight() == rp.isElegantTextHeight() && lp.getTextSize() == rp.getTextSize() && lp.getTextScaleX() == rp.getTextScaleX() && lp.getTextSkewX() == rp.getTextSkewX() && lp.getLetterSpacing() == rp.getLetterSpacing() && lp.getWordSpacing() == rp.getWordSpacing() && lp.getStartHyphenEdit() == rp.getStartHyphenEdit() && lp.getEndHyphenEdit() == rp.getEndHyphenEdit() && lp.bgColor == rp.bgColor && lp.baselineShift == rp.baselineShift && lp.linkColor == rp.linkColor && lp.drawableState == rp.drawableState && lp.density == rp.density && lp.underlineColor == rp.underlineColor && lp.underlineThickness == rp.underlineThickness;
    }
}
