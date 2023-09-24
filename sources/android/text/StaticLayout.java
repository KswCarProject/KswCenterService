package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import android.graphics.text.LineBreaker;
import android.graphics.text.MeasuredText;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class StaticLayout extends Layout {
    private static final char CHAR_NEW_LINE = '\n';
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DEFAULT_MAX_LINE_HEIGHT = -1;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    @UnsupportedAppUsage
    private static final int ELLIPSIS_START = 5;
    private static final int END_HYPHEN_MASK = 7;
    private static final int EXTRA = 3;
    private static final double EXTRA_ROUNDING = 0.5d;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    private static final int START = 0;
    private static final int START_HYPHEN_BITS_SHIFT = 3;
    private static final int START_HYPHEN_MASK = 24;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final float TAB_INCREMENT = 20.0f;
    private static final int TAB_MASK = 536870912;
    static final String TAG = "StaticLayout";
    private static final int TOP = 1;
    private int mBottomPadding;
    @UnsupportedAppUsage
    private int mColumns;
    private boolean mEllipsized;
    private int mEllipsizedWidth;
    private int[] mLeftIndents;
    @UnsupportedAppUsage
    private int mLineCount;
    @UnsupportedAppUsage
    private Layout.Directions[] mLineDirections;
    @UnsupportedAppUsage
    private int[] mLines;
    private int mMaxLineHeight;
    @UnsupportedAppUsage
    private int mMaximumVisibleLineCount;
    private int[] mRightIndents;
    private int mTopPadding;

    /* loaded from: classes4.dex */
    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool<>(3);
        private boolean mAddLastLineLineSpacing;
        private Layout.Alignment mAlignment;
        private int mBreakStrategy;
        private TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private int mEnd;
        private boolean mFallbackLineSpacing;
        private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private int[] mLeftIndents;
        private int mMaxLines;
        private TextPaint mPaint;
        private int[] mRightIndents;
        private float mSpacingAdd;
        private float mSpacingMult;
        private int mStart;
        private CharSequence mText;
        private TextDirectionHeuristic mTextDir;
        private int mWidth;

        private Builder() {
        }

        public static Builder obtain(CharSequence source, int start, int end, TextPaint paint, int width) {
            Builder b = sPool.acquire();
            if (b == null) {
                b = new Builder();
            }
            b.mText = source;
            b.mStart = start;
            b.mEnd = end;
            b.mPaint = paint;
            b.mWidth = width;
            b.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            b.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            b.mSpacingMult = 1.0f;
            b.mSpacingAdd = 0.0f;
            b.mIncludePad = true;
            b.mFallbackLineSpacing = false;
            b.mEllipsizedWidth = width;
            b.mEllipsize = null;
            b.mMaxLines = Integer.MAX_VALUE;
            b.mBreakStrategy = 0;
            b.mHyphenationFrequency = 0;
            b.mJustificationMode = 0;
            return b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void recycle(Builder b) {
            b.mPaint = null;
            b.mText = null;
            b.mLeftIndents = null;
            b.mRightIndents = null;
            sPool.release(b);
        }

        void finish() {
            this.mText = null;
            this.mPaint = null;
            this.mLeftIndents = null;
            this.mRightIndents = null;
        }

        public Builder setText(CharSequence source) {
            return setText(source, 0, source.length());
        }

        public Builder setText(CharSequence source, int start, int end) {
            this.mText = source;
            this.mStart = start;
            this.mEnd = end;
            return this;
        }

        public Builder setPaint(TextPaint paint) {
            this.mPaint = paint;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            if (this.mEllipsize == null) {
                this.mEllipsizedWidth = width;
            }
            return this;
        }

        public Builder setAlignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setTextDirection(TextDirectionHeuristic textDir) {
            this.mTextDir = textDir;
            return this;
        }

        public Builder setLineSpacing(float spacingAdd, float spacingMult) {
            this.mSpacingAdd = spacingAdd;
            this.mSpacingMult = spacingMult;
            return this;
        }

        public Builder setIncludePad(boolean includePad) {
            this.mIncludePad = includePad;
            return this;
        }

        public Builder setUseLineSpacingFromFallbacks(boolean useLineSpacingFromFallbacks) {
            this.mFallbackLineSpacing = useLineSpacingFromFallbacks;
            return this;
        }

        public Builder setEllipsizedWidth(int ellipsizedWidth) {
            this.mEllipsizedWidth = ellipsizedWidth;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt ellipsize) {
            this.mEllipsize = ellipsize;
            return this;
        }

        public Builder setMaxLines(int maxLines) {
            this.mMaxLines = maxLines;
            return this;
        }

        public Builder setBreakStrategy(int breakStrategy) {
            this.mBreakStrategy = breakStrategy;
            return this;
        }

        public Builder setHyphenationFrequency(int hyphenationFrequency) {
            this.mHyphenationFrequency = hyphenationFrequency;
            return this;
        }

        public Builder setIndents(int[] leftIndents, int[] rightIndents) {
            this.mLeftIndents = leftIndents;
            this.mRightIndents = rightIndents;
            return this;
        }

        public Builder setJustificationMode(int justificationMode) {
            this.mJustificationMode = justificationMode;
            return this;
        }

        Builder setAddLastLineLineSpacing(boolean value) {
            this.mAddLastLineLineSpacing = value;
            return this;
        }

        public StaticLayout build() {
            StaticLayout result = new StaticLayout(this);
            recycle(this);
            return result;
        }
    }

    @Deprecated
    public StaticLayout(CharSequence source, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(source, 0, source.length(), paint, width, align, spacingmult, spacingadd, includepad);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(source, bufstart, bufend, paint, outerwidth, align, spacingmult, spacingadd, includepad, null, 0);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth) {
        this(source, bufstart, bufend, paint, outerwidth, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth, Integer.MAX_VALUE);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 117521430)
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, TextDirectionHeuristic textDir, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth, int maxLines) {
        super(r1, paint, outerwidth, align, textDir, spacingmult, spacingadd);
        CharSequence spannedEllipsizer;
        if (ellipsize == null) {
            spannedEllipsizer = source;
        } else {
            spannedEllipsizer = source instanceof Spanned ? new Layout.SpannedEllipsizer(source) : new Layout.Ellipsizer(source);
        }
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        Builder b = Builder.obtain(source, bufstart, bufend, paint, outerwidth).setAlignment(align).setTextDirection(textDir).setLineSpacing(spacingadd, spacingmult).setIncludePad(includepad).setEllipsizedWidth(ellipsizedWidth).setEllipsize(ellipsize).setMaxLines(maxLines);
        if (ellipsize != null) {
            Layout.Ellipsizer e = (Layout.Ellipsizer) getText();
            e.mLayout = this;
            e.mWidth = ellipsizedWidth;
            e.mMethod = ellipsize;
            this.mEllipsizedWidth = ellipsizedWidth;
            this.mColumns = 7;
        } else {
            this.mColumns = 5;
            this.mEllipsizedWidth = outerwidth;
        }
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = maxLines;
        generate(b, b.mIncludePad, b.mIncludePad);
        Builder.recycle(b);
    }

    StaticLayout(CharSequence text) {
        super(text, null, 0, null, 0.0f, 0.0f);
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = 7;
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private StaticLayout(Builder b) {
        super(r0, b.mPaint, b.mWidth, b.mAlignment, b.mTextDir, b.mSpacingMult, b.mSpacingAdd);
        CharSequence ellipsizer;
        if (b.mEllipsize == null) {
            ellipsizer = b.mText;
        } else if (b.mText instanceof Spanned) {
            ellipsizer = new Layout.SpannedEllipsizer(b.mText);
        } else {
            ellipsizer = new Layout.Ellipsizer(b.mText);
        }
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        if (b.mEllipsize != null) {
            Layout.Ellipsizer e = (Layout.Ellipsizer) getText();
            e.mLayout = this;
            e.mWidth = b.mEllipsizedWidth;
            e.mMethod = b.mEllipsize;
            this.mEllipsizedWidth = b.mEllipsizedWidth;
            this.mColumns = 7;
        } else {
            this.mColumns = 5;
            this.mEllipsizedWidth = b.mWidth;
        }
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = b.mMaxLines;
        this.mLeftIndents = b.mLeftIndents;
        this.mRightIndents = b.mRightIndents;
        setJustificationMode(b.mJustificationMode);
        generate(b, b.mIncludePad, b.mIncludePad);
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x030d, code lost:
        if (r3 != android.text.TextUtils.TruncateAt.MARQUEE) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x061a, code lost:
        r5 = r92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x061c, code lost:
        if (r0 == r5) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x061e, code lost:
        r7 = r93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0628, code lost:
        if (r7.charAt(r0 - 1) != '\n') goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0631, code lost:
        r7 = r93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0637, code lost:
        if (r9.mLineCount >= r9.mMaximumVisibleLineCount) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x063a, code lost:
        r10 = android.text.MeasuredParagraph.buildForBidi(r7, r0, r0, r89, null);
        r11 = r90;
        r11.getFontMetricsInt(r6);
        out(r7, r0, r0, r6.ascent, r6.descent, r6.top, r6.bottom, r1, r48, r49, null, null, r6, false, 0, r16, r10, r0, r99, r100, r50, null, r5, r53, r57, 0.0f, r11, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0691, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:?, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x031d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x05c8 A[LOOP:0: B:51:0x0176->B:179:0x05c8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x05be A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02c0 A[LOOP:3: B:97:0x02be->B:98:0x02c0, LOOP_END] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void generate(Builder b, boolean includepad, boolean trackpad) {
        int[] indents;
        Paint.FontMetricsInt fm;
        LineBreaker lineBreaker;
        float ellipsizedWidth;
        int lineBreakCapacity;
        TextUtils.TruncateAt ellipsize;
        boolean z;
        Spanned spanned;
        LineBreaker.ParagraphConstraints constraints;
        TextDirectionHeuristic textDir;
        TextPaint paint;
        int bufEnd;
        int bufStart;
        CharSequence source;
        StaticLayout staticLayout;
        TextUtils.TruncateAt ellipsize2;
        Paint.FontMetricsInt fm2;
        int fmDescent;
        int paraStart;
        TextDirectionHeuristic textDir2;
        TextPaint paint2;
        int bufStart2;
        CharSequence source2;
        int[] chooseHtv;
        int firstWidthLineCount;
        int firstWidth;
        int restWidth;
        LineHeightSpan[] chooseHt;
        float[] stops;
        int breakCount;
        LineBreaker lineBreaker2;
        int lineBreakCapacity2;
        int[] breaks;
        float[] lineWidths;
        float[] ascents;
        float[] descents;
        boolean[] hasTabs;
        int[] hyphenEdits;
        int i;
        int remainingLineCount;
        Spanned spanned2;
        TextUtils.TruncateAt ellipsize3;
        boolean z2;
        TextUtils.TruncateAt ellipsize4;
        float[] variableTabStops;
        int restWidth2;
        int spanStart;
        int fmDescent2;
        int ascent;
        int paraStart2;
        int paraStart3;
        Paint.FontMetricsInt fm3;
        int restWidth3;
        StaticLayout staticLayout2 = this;
        CharSequence source3 = b.mText;
        int bufStart3 = b.mStart;
        int bufEnd2 = b.mEnd;
        TextPaint paint3 = b.mPaint;
        int outerWidth = b.mWidth;
        TextDirectionHeuristic textDir3 = b.mTextDir;
        boolean fallbackLineSpacing = b.mFallbackLineSpacing;
        float spacingmult = b.mSpacingMult;
        float spacingadd = b.mSpacingAdd;
        float ellipsizedWidth2 = b.mEllipsizedWidth;
        TextUtils.TruncateAt ellipsize5 = b.mEllipsize;
        boolean addLastLineSpacing = b.mAddLastLineLineSpacing;
        int[] breaks2 = null;
        float[] lineWidths2 = null;
        float[] ascents2 = null;
        float[] descents2 = null;
        boolean[] hasTabs2 = null;
        int[] hyphenEdits2 = null;
        staticLayout2.mLineCount = 0;
        staticLayout2.mEllipsized = false;
        staticLayout2.mMaxLineHeight = staticLayout2.mMaximumVisibleLineCount < 1 ? 0 : -1;
        int v = 0;
        boolean needMultiply = (spacingmult == 1.0f && spacingadd == 0.0f) ? false : true;
        Paint.FontMetricsInt fm4 = b.mFontMetricsInt;
        if (staticLayout2.mLeftIndents != null || staticLayout2.mRightIndents != null) {
            int leftLen = staticLayout2.mLeftIndents == null ? 0 : staticLayout2.mLeftIndents.length;
            int rightLen = staticLayout2.mRightIndents == null ? 0 : staticLayout2.mRightIndents.length;
            int indentsLen = Math.max(leftLen, rightLen);
            indents = new int[indentsLen];
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= leftLen) {
                    break;
                }
                indents[i3] = staticLayout2.mLeftIndents[i3];
                i2 = i3 + 1;
            }
            int i4 = 0;
            while (i4 < rightLen) {
                indents[i4] = indents[i4] + staticLayout2.mRightIndents[i4];
                i4++;
                leftLen = leftLen;
            }
        } else {
            indents = null;
        }
        LineBreaker lineBreaker3 = new LineBreaker.Builder().setBreakStrategy(b.mBreakStrategy).setHyphenationFrequency(b.mHyphenationFrequency).setJustificationMode(b.mJustificationMode).setIndents(indents).build();
        LineBreaker.ParagraphConstraints constraints2 = new LineBreaker.ParagraphConstraints();
        PrecomputedText.ParagraphInfo[] paragraphInfo = null;
        Spanned spanned3 = source3 instanceof Spanned ? (Spanned) source3 : null;
        if (source3 instanceof PrecomputedText) {
            PrecomputedText precomputed = (PrecomputedText) source3;
            ellipsizedWidth = ellipsizedWidth2;
            spanned = spanned3;
            lineBreakCapacity = 0;
            constraints = constraints2;
            fm = fm4;
            lineBreaker = lineBreaker3;
            ellipsize = ellipsize5;
            z = false;
            int checkResult = precomputed.checkResultUsable(bufStart3, bufEnd2, textDir3, paint3, b.mBreakStrategy, b.mHyphenationFrequency);
            switch (checkResult) {
                case 1:
                    PrecomputedText.Params newParams = new PrecomputedText.Params.Builder(paint3).setBreakStrategy(b.mBreakStrategy).setHyphenationFrequency(b.mHyphenationFrequency).setTextDirection(textDir3).build();
                    paragraphInfo = PrecomputedText.create(precomputed, newParams).getParagraphInfo();
                    break;
                case 2:
                    paragraphInfo = precomputed.getParagraphInfo();
                    break;
            }
        } else {
            fm = fm4;
            lineBreaker = lineBreaker3;
            ellipsizedWidth = ellipsizedWidth2;
            lineBreakCapacity = 0;
            ellipsize = ellipsize5;
            z = false;
            spanned = spanned3;
            constraints = constraints2;
        }
        if (paragraphInfo == null) {
            PrecomputedText.Params param = new PrecomputedText.Params(paint3, textDir3, b.mBreakStrategy, b.mHyphenationFrequency);
            paragraphInfo = PrecomputedText.createMeasuredParagraphs(source3, param, bufStart3, bufEnd2, z);
        }
        PrecomputedText.ParagraphInfo[] paragraphInfo2 = paragraphInfo;
        int bufEnd3 = z;
        int[] chooseHtv2 = null;
        int lineBreakCapacity3 = lineBreakCapacity;
        while (true) {
            ?? r3 = bufEnd3;
            int paraIndex = paragraphInfo2.length;
            if (r3 < paraIndex) {
                if (r3 == 0) {
                    paraStart = bufStart3;
                } else {
                    int paraIndex2 = r3 == true ? 1 : 0;
                    paraStart = paragraphInfo2[paraIndex2 - 1].paragraphEnd;
                }
                int paraIndex3 = r3 == true ? 1 : 0;
                int paraEnd = paragraphInfo2[paraIndex3].paragraphEnd;
                int firstWidth2 = outerWidth;
                int restWidth4 = outerWidth;
                if (spanned != null) {
                    LeadingMarginSpan[] sp = (LeadingMarginSpan[]) getParagraphSpans(spanned, paraStart, paraEnd, LeadingMarginSpan.class);
                    textDir2 = textDir3;
                    int firstWidthLineCount2 = 1;
                    int firstWidthLineCount3 = 0;
                    while (true) {
                        paint2 = paint3;
                        if (firstWidthLineCount3 < sp.length) {
                            LeadingMarginSpan lms = sp[firstWidthLineCount3];
                            int bufStart4 = bufStart3;
                            CharSequence source4 = source3;
                            firstWidth2 -= sp[firstWidthLineCount3].getLeadingMargin(true);
                            restWidth4 -= sp[firstWidthLineCount3].getLeadingMargin(false);
                            if (lms instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                                LeadingMarginSpan.LeadingMarginSpan2 lms2 = (LeadingMarginSpan.LeadingMarginSpan2) lms;
                                firstWidthLineCount2 = Math.max(firstWidthLineCount2, lms2.getLeadingMarginLineCount());
                            }
                            firstWidthLineCount3++;
                            paint3 = paint2;
                            bufStart3 = bufStart4;
                            source3 = source4;
                        } else {
                            bufStart2 = bufStart3;
                            source2 = source3;
                            LineHeightSpan[] chooseHt2 = (LineHeightSpan[]) getParagraphSpans(spanned, paraStart, paraEnd, LineHeightSpan.class);
                            if (chooseHt2.length == 0) {
                                chooseHt2 = null;
                            } else {
                                if (chooseHtv2 == null || chooseHtv2.length < chooseHt2.length) {
                                    chooseHtv2 = ArrayUtils.newUnpaddedIntArray(chooseHt2.length);
                                }
                                for (int i5 = 0; i5 < chooseHt2.length; i5++) {
                                    int o = spanned.getSpanStart(chooseHt2[i5]);
                                    if (o < paraStart) {
                                        chooseHtv2[i5] = staticLayout2.getLineTop(staticLayout2.getLineForOffset(o));
                                    } else {
                                        chooseHtv2[i5] = v;
                                    }
                                }
                            }
                            chooseHtv = chooseHtv2;
                            chooseHt = chooseHt2;
                            firstWidthLineCount = firstWidthLineCount2;
                            firstWidth = firstWidth2;
                            restWidth = restWidth4;
                        }
                    }
                } else {
                    textDir2 = textDir3;
                    paint2 = paint3;
                    bufStart2 = bufStart3;
                    source2 = source3;
                    chooseHtv = chooseHtv2;
                    firstWidthLineCount = 1;
                    firstWidth = firstWidth2;
                    restWidth = restWidth4;
                    chooseHt = null;
                }
                Object obj = null;
                if (spanned != null) {
                    TabStopSpan[] spans = (TabStopSpan[]) getParagraphSpans(spanned, paraStart, paraEnd, TabStopSpan.class);
                    if (spans.length > 0) {
                        stops = new float[spans.length];
                        int i6 = 0;
                        while (true) {
                            Object obj2 = obj;
                            if (i6 < spans.length) {
                                stops[i6] = spans[i6].getTabStop();
                                i6++;
                                obj = obj2;
                            } else {
                                Arrays.sort(stops, 0, stops.length);
                                int paraIndex4 = r3 == true ? 1 : 0;
                                MeasuredParagraph measuredPara = paragraphInfo2[paraIndex4].measured;
                                char[] chs = measuredPara.getChars();
                                int[] spanEndCache = measuredPara.getSpanEndCache().getRawArray();
                                int[] fmCache = measuredPara.getFontMetrics().getRawArray();
                                constraints.setWidth(restWidth);
                                constraints.setIndent(firstWidth, firstWidthLineCount);
                                constraints.setTabStops(stops, TAB_INCREMENT);
                                MeasuredText measuredText = measuredPara.getMeasuredText();
                                int i7 = staticLayout2.mLineCount;
                                int paraIndex5 = r3 == true ? 1 : 0;
                                int paraIndex6 = paraIndex5;
                                LineBreaker lineBreaker4 = lineBreaker;
                                LineBreaker.Result res = lineBreaker4.computeLineBreaks(measuredText, constraints, i7);
                                breakCount = res.getLineCount();
                                if (lineBreakCapacity3 >= breakCount) {
                                    lineBreaker2 = lineBreaker4;
                                    int[] breaks3 = new int[breakCount];
                                    breaks = breaks3;
                                    float[] lineWidths3 = new float[breakCount];
                                    lineWidths = lineWidths3;
                                    float[] lineWidths4 = new float[breakCount];
                                    ascents = lineWidths4;
                                    float[] ascents3 = new float[breakCount];
                                    descents = ascents3;
                                    boolean[] hasTabs3 = new boolean[breakCount];
                                    hasTabs = hasTabs3;
                                    lineBreakCapacity2 = breakCount;
                                    hyphenEdits = new int[breakCount];
                                } else {
                                    lineBreaker2 = lineBreaker4;
                                    lineBreakCapacity2 = lineBreakCapacity3;
                                    breaks = breaks2;
                                    lineWidths = lineWidths2;
                                    ascents = ascents2;
                                    descents = descents2;
                                    hasTabs = hasTabs2;
                                    hyphenEdits = hyphenEdits2;
                                }
                                i = 0;
                                while (i < breakCount) {
                                    breaks[i] = res.getLineBreakOffset(i);
                                    lineWidths[i] = res.getLineWidth(i);
                                    ascents[i] = res.getLineAscent(i);
                                    descents[i] = res.getLineDescent(i);
                                    hasTabs[i] = res.hasLineTab(i);
                                    hyphenEdits[i] = packHyphenEdit(res.getStartLineHyphenEdit(i), res.getEndLineHyphenEdit(i));
                                    i++;
                                    paragraphInfo2 = paragraphInfo2;
                                }
                                PrecomputedText.ParagraphInfo[] paragraphInfo3 = paragraphInfo2;
                                remainingLineCount = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                                if (ellipsize == null) {
                                    ellipsize3 = ellipsize;
                                    if (ellipsize3 != TextUtils.TruncateAt.END) {
                                        spanned2 = spanned;
                                        if (staticLayout2.mMaximumVisibleLineCount == 1) {
                                        }
                                    } else {
                                        spanned2 = spanned;
                                    }
                                    z2 = true;
                                    boolean ellipsisMayBeApplied = z2;
                                    if (remainingLineCount <= 0 && remainingLineCount < breakCount && ellipsisMayBeApplied) {
                                        float width = 0.0f;
                                        boolean hasTab = false;
                                        int i8 = remainingLineCount - 1;
                                        while (true) {
                                            int i9 = i8;
                                            ellipsize4 = ellipsize3;
                                            if (i9 < breakCount) {
                                                float[] variableTabStops2 = stops;
                                                if (i9 == breakCount - 1) {
                                                    width += lineWidths[i9];
                                                    restWidth3 = restWidth;
                                                } else {
                                                    int j = i9 == 0 ? 0 : breaks[i9 - 1];
                                                    while (true) {
                                                        restWidth3 = restWidth;
                                                        if (j < breaks[i9]) {
                                                            width += measuredPara.getCharWidthAt(j);
                                                            j++;
                                                            restWidth = restWidth3;
                                                        }
                                                    }
                                                }
                                                hasTab |= hasTabs[i9];
                                                i8 = i9 + 1;
                                                ellipsize3 = ellipsize4;
                                                stops = variableTabStops2;
                                                restWidth = restWidth3;
                                            } else {
                                                variableTabStops = stops;
                                                restWidth2 = restWidth;
                                                breaks[remainingLineCount - 1] = breaks[breakCount - 1];
                                                lineWidths[remainingLineCount - 1] = width;
                                                hasTabs[remainingLineCount - 1] = hasTab;
                                                breakCount = remainingLineCount;
                                            }
                                        }
                                    } else {
                                        ellipsize4 = ellipsize3;
                                        variableTabStops = stops;
                                        restWidth2 = restWidth;
                                    }
                                    int breakCount2 = breakCount;
                                    spanStart = paraStart;
                                    int fmTop = 0;
                                    int fmCacheIndex = 0;
                                    int spanEndCacheIndex = 0;
                                    int breakIndex = 0;
                                    MeasuredParagraph measuredPara2 = measuredPara;
                                    int fmDescent3 = 0;
                                    int fmAscent = 0;
                                    int fmBottom = 0;
                                    int fmBottom2 = spanStart;
                                    while (spanStart < paraEnd) {
                                        int spanEndCacheIndex2 = spanEndCacheIndex + 1;
                                        int spanEnd = spanEndCache[spanEndCacheIndex];
                                        int spanEndCacheIndex3 = fmCacheIndex * 4;
                                        int i10 = 0;
                                        int here = fmBottom2;
                                        int here2 = fmCache[spanEndCacheIndex3 + 0];
                                        int firstWidthLineCount4 = firstWidthLineCount;
                                        Paint.FontMetricsInt fm5 = fm;
                                        fm5.top = here2;
                                        boolean z3 = true;
                                        fm5.bottom = fmCache[(fmCacheIndex * 4) + 1];
                                        fm5.ascent = fmCache[(fmCacheIndex * 4) + 2];
                                        fm5.descent = fmCache[(fmCacheIndex * 4) + 3];
                                        int fmCacheIndex2 = fmCacheIndex + 1;
                                        if (fm5.top < fmTop) {
                                            fmTop = fm5.top;
                                        }
                                        if (fm5.ascent < fmAscent) {
                                            fmAscent = fm5.ascent;
                                        }
                                        if (fm5.descent > fmDescent3) {
                                            fmDescent3 = fm5.descent;
                                        }
                                        if (fm5.bottom > fmBottom) {
                                            fmBottom = fm5.bottom;
                                        }
                                        int breakIndex2 = breakIndex;
                                        while (true) {
                                            if (breakIndex2 < breakCount2) {
                                                fmDescent2 = fmDescent3;
                                                int fmDescent4 = paraStart + breaks[breakIndex2];
                                                if (fmDescent4 < spanStart) {
                                                    breakIndex2++;
                                                    fmDescent3 = fmDescent2;
                                                }
                                            } else {
                                                fmDescent2 = fmDescent3;
                                            }
                                        }
                                        int fmTop2 = fmTop;
                                        int fmBottom3 = fmBottom;
                                        int fmBottom4 = fmAscent;
                                        int v2 = v;
                                        int fmBottom5 = fmDescent2;
                                        int fmAscent2 = breakIndex2;
                                        while (fmAscent2 < breakCount2 && breaks[fmAscent2] + paraStart <= spanEnd) {
                                            int endPos = breaks[fmAscent2] + paraStart;
                                            boolean moreChars = endPos < bufEnd2 ? z3 : i10;
                                            if (fallbackLineSpacing) {
                                                ascent = Math.min(fmBottom4, Math.round(ascents[fmAscent2]));
                                            } else {
                                                ascent = fmBottom4;
                                            }
                                            if (fallbackLineSpacing) {
                                                paraStart2 = paraStart;
                                                int paraStart4 = Math.round(descents[fmAscent2]);
                                                paraStart3 = Math.max(fmBottom5, paraStart4);
                                            } else {
                                                paraStart2 = paraStart;
                                                paraStart3 = fmBottom5;
                                            }
                                            int paraEnd2 = paraEnd;
                                            int paraEnd3 = paraStart3;
                                            boolean z4 = hasTabs[fmAscent2];
                                            LineBreaker.ParagraphConstraints constraints3 = constraints;
                                            int i11 = hyphenEdits[fmAscent2];
                                            LineBreaker.Result res2 = res;
                                            boolean z5 = z3;
                                            int fmDescent5 = i10;
                                            LineBreaker lineBreaker5 = lineBreaker2;
                                            TextUtils.TruncateAt ellipsize6 = ellipsize4;
                                            float f = lineWidths[fmAscent2];
                                            int paraStart5 = paraStart2;
                                            MeasuredParagraph measuredPara3 = measuredPara2;
                                            int spanStart2 = spanStart;
                                            int paraIndex7 = paraIndex6;
                                            int remainingLineCount2 = remainingLineCount;
                                            PrecomputedText.ParagraphInfo[] paragraphInfo4 = paragraphInfo3;
                                            Spanned spanned4 = spanned2;
                                            int fmAscent3 = fmBottom3;
                                            int breakIndex3 = fmAscent2;
                                            TextDirectionHeuristic textDir4 = textDir2;
                                            float[] variableTabStops3 = variableTabStops;
                                            int breakIndex4 = v2;
                                            TextPaint paint4 = paint2;
                                            int restWidth5 = restWidth2;
                                            int breakCount3 = breakCount2;
                                            int bufEnd4 = bufEnd2;
                                            int bufStart5 = bufStart2;
                                            int bufStart6 = firstWidth;
                                            Paint.FontMetricsInt fm6 = fm5;
                                            CharSequence source5 = source2;
                                            int firstWidthLineCount5 = firstWidthLineCount4;
                                            int spanEnd2 = spanEnd;
                                            v2 = out(source2, here, endPos, ascent, paraEnd3, fmTop2, fmAscent3, breakIndex4, spacingmult, spacingadd, chooseHt, chooseHtv, fm6, z4, i11, needMultiply, measuredPara3, bufEnd4, includepad, trackpad, addLastLineSpacing, chs, paraStart5, ellipsize6, ellipsizedWidth, f, paint4, moreChars);
                                            if (endPos < spanEnd2) {
                                                fm3 = fm6;
                                                int fmTop3 = fm3.top;
                                                int fmBottom6 = fm3.bottom;
                                                fmBottom4 = fm3.ascent;
                                                fmTop2 = fmTop3;
                                                fmBottom3 = fmBottom6;
                                                fmBottom5 = fm3.descent;
                                            } else {
                                                fm3 = fm6;
                                                fmBottom3 = fmDescent5;
                                                fmTop2 = fmDescent5;
                                                fmBottom4 = fmDescent5;
                                                fmBottom5 = fmDescent5;
                                            }
                                            here = endPos;
                                            fmAscent2 = breakIndex3 + 1;
                                            if (this.mLineCount < this.mMaximumVisibleLineCount || !this.mEllipsized) {
                                                spanEnd = spanEnd2;
                                                fm5 = fm3;
                                                res = res2;
                                                z3 = z5;
                                                i10 = fmDescent5;
                                                paraStart = paraStart5;
                                                constraints = constraints3;
                                                ellipsize4 = ellipsize6;
                                                lineBreaker2 = lineBreaker5;
                                                measuredPara2 = measuredPara3;
                                                spanStart = spanStart2;
                                                paraIndex6 = paraIndex7;
                                                remainingLineCount = remainingLineCount2;
                                                paragraphInfo3 = paragraphInfo4;
                                                spanned2 = spanned4;
                                                restWidth2 = restWidth5;
                                                breakCount2 = breakCount3;
                                                firstWidth = bufStart6;
                                                firstWidthLineCount4 = firstWidthLineCount5;
                                                variableTabStops = variableTabStops3;
                                                paraEnd = paraEnd2;
                                                textDir2 = textDir4;
                                                paint2 = paint4;
                                                bufEnd2 = bufEnd4;
                                                bufStart2 = bufStart5;
                                                source2 = source5;
                                            } else {
                                                return;
                                            }
                                        }
                                        int paraStart6 = paraStart;
                                        int fmDescent6 = fmBottom5;
                                        int remainingLineCount3 = remainingLineCount;
                                        int fmAscent4 = fmBottom4;
                                        int breakIndex5 = fmAscent2;
                                        Paint.FontMetricsInt fm7 = fm5;
                                        int paraStart7 = spanEnd;
                                        spanStart = paraStart7;
                                        staticLayout2 = this;
                                        spanEndCacheIndex = spanEndCacheIndex2;
                                        fmCacheIndex = fmCacheIndex2;
                                        v = v2;
                                        fmTop = fmTop2;
                                        fmBottom = fmBottom3;
                                        res = res;
                                        paraStart = paraStart6;
                                        constraints = constraints;
                                        ellipsize4 = ellipsize4;
                                        lineBreaker2 = lineBreaker2;
                                        measuredPara2 = measuredPara2;
                                        paraIndex6 = paraIndex6;
                                        paragraphInfo3 = paragraphInfo3;
                                        spanned2 = spanned2;
                                        fmAscent = fmAscent4;
                                        breakIndex = breakIndex5;
                                        restWidth2 = restWidth2;
                                        breakCount2 = breakCount2;
                                        firstWidth = firstWidth;
                                        firstWidthLineCount = firstWidthLineCount4;
                                        variableTabStops = variableTabStops;
                                        fmBottom2 = here;
                                        fmDescent3 = fmDescent6;
                                        paraEnd = paraEnd;
                                        textDir2 = textDir2;
                                        paint2 = paint2;
                                        bufEnd2 = bufEnd2;
                                        bufStart2 = bufStart2;
                                        source2 = source2;
                                        fm = fm7;
                                        remainingLineCount = remainingLineCount3;
                                    }
                                    LineBreaker.ParagraphConstraints constraints4 = constraints;
                                    textDir = textDir2;
                                    paint = paint2;
                                    bufStart = bufStart2;
                                    source = source2;
                                    int paraIndex8 = paraIndex6;
                                    LineBreaker lineBreaker6 = lineBreaker2;
                                    PrecomputedText.ParagraphInfo[] paragraphInfo5 = paragraphInfo3;
                                    Spanned spanned5 = spanned2;
                                    ellipsize2 = ellipsize4;
                                    staticLayout = staticLayout2;
                                    fm2 = fm;
                                    bufEnd = bufEnd2;
                                    if (paraEnd != bufEnd) {
                                        int fmDescent7 = paraIndex8 + 1;
                                        bufEnd2 = bufEnd;
                                        bufEnd3 = fmDescent7;
                                        fm = fm2;
                                        staticLayout2 = staticLayout;
                                        chooseHtv2 = chooseHtv;
                                        hyphenEdits2 = hyphenEdits;
                                        lineBreakCapacity3 = lineBreakCapacity2;
                                        constraints = constraints4;
                                        paragraphInfo2 = paragraphInfo5;
                                        spanned = spanned5;
                                        breaks2 = breaks;
                                        lineWidths2 = lineWidths;
                                        ascents2 = ascents;
                                        descents2 = descents;
                                        hasTabs2 = hasTabs;
                                        textDir3 = textDir;
                                        paint3 = paint;
                                        bufStart3 = bufStart;
                                        source3 = source;
                                        ellipsize = ellipsize2;
                                        lineBreaker = lineBreaker6;
                                    } else {
                                        fmDescent = v;
                                    }
                                } else {
                                    spanned2 = spanned;
                                    ellipsize3 = ellipsize;
                                }
                                z2 = false;
                                boolean ellipsisMayBeApplied2 = z2;
                                if (remainingLineCount <= 0) {
                                }
                                ellipsize4 = ellipsize3;
                                variableTabStops = stops;
                                restWidth2 = restWidth;
                                int breakCount22 = breakCount;
                                spanStart = paraStart;
                                int fmTop4 = 0;
                                int fmCacheIndex3 = 0;
                                int spanEndCacheIndex4 = 0;
                                int breakIndex6 = 0;
                                MeasuredParagraph measuredPara22 = measuredPara;
                                int fmDescent32 = 0;
                                int fmAscent5 = 0;
                                int fmBottom7 = 0;
                                int fmBottom22 = spanStart;
                                while (spanStart < paraEnd) {
                                }
                                LineBreaker.ParagraphConstraints constraints42 = constraints;
                                textDir = textDir2;
                                paint = paint2;
                                bufStart = bufStart2;
                                source = source2;
                                int paraIndex82 = paraIndex6;
                                LineBreaker lineBreaker62 = lineBreaker2;
                                PrecomputedText.ParagraphInfo[] paragraphInfo52 = paragraphInfo3;
                                Spanned spanned52 = spanned2;
                                ellipsize2 = ellipsize4;
                                staticLayout = staticLayout2;
                                fm2 = fm;
                                bufEnd = bufEnd2;
                                if (paraEnd != bufEnd) {
                                }
                            }
                        }
                    }
                }
                stops = null;
                int paraIndex42 = r3 == true ? 1 : 0;
                MeasuredParagraph measuredPara4 = paragraphInfo2[paraIndex42].measured;
                char[] chs2 = measuredPara4.getChars();
                int[] spanEndCache2 = measuredPara4.getSpanEndCache().getRawArray();
                int[] fmCache2 = measuredPara4.getFontMetrics().getRawArray();
                constraints.setWidth(restWidth);
                constraints.setIndent(firstWidth, firstWidthLineCount);
                constraints.setTabStops(stops, TAB_INCREMENT);
                MeasuredText measuredText2 = measuredPara4.getMeasuredText();
                int i72 = staticLayout2.mLineCount;
                int paraIndex52 = r3 == true ? 1 : 0;
                int paraIndex62 = paraIndex52;
                LineBreaker lineBreaker42 = lineBreaker;
                LineBreaker.Result res3 = lineBreaker42.computeLineBreaks(measuredText2, constraints, i72);
                breakCount = res3.getLineCount();
                if (lineBreakCapacity3 >= breakCount) {
                }
                i = 0;
                while (i < breakCount) {
                }
                PrecomputedText.ParagraphInfo[] paragraphInfo32 = paragraphInfo2;
                remainingLineCount = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                if (ellipsize == null) {
                }
                z2 = false;
                boolean ellipsisMayBeApplied22 = z2;
                if (remainingLineCount <= 0) {
                }
                ellipsize4 = ellipsize3;
                variableTabStops = stops;
                restWidth2 = restWidth;
                int breakCount222 = breakCount;
                spanStart = paraStart;
                int fmTop42 = 0;
                int fmCacheIndex32 = 0;
                int spanEndCacheIndex42 = 0;
                int breakIndex62 = 0;
                MeasuredParagraph measuredPara222 = measuredPara4;
                int fmDescent322 = 0;
                int fmAscent52 = 0;
                int fmBottom72 = 0;
                int fmBottom222 = spanStart;
                while (spanStart < paraEnd) {
                }
                LineBreaker.ParagraphConstraints constraints422 = constraints;
                textDir = textDir2;
                paint = paint2;
                bufStart = bufStart2;
                source = source2;
                int paraIndex822 = paraIndex62;
                LineBreaker lineBreaker622 = lineBreaker2;
                PrecomputedText.ParagraphInfo[] paragraphInfo522 = paragraphInfo32;
                Spanned spanned522 = spanned2;
                ellipsize2 = ellipsize4;
                staticLayout = staticLayout2;
                fm2 = fm;
                bufEnd = bufEnd2;
                if (paraEnd != bufEnd) {
                }
            } else {
                textDir = textDir3;
                paint = paint3;
                bufEnd = bufEnd2;
                bufStart = bufStart3;
                source = source3;
                staticLayout = staticLayout2;
                ellipsize2 = ellipsize;
                fm2 = fm;
                fmDescent = v;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int out(CharSequence text, int start, int end, int above, int below, int top, int bottom, int v, float spacingmult, float spacingadd, LineHeightSpan[] chooseHt, int[] chooseHtv, Paint.FontMetricsInt fm, boolean hasTab, int hyphenEdit, boolean needMultiply, MeasuredParagraph measured, int bufEnd, boolean includePad, boolean trackPad, boolean addLastLineLineSpacing, char[] chs, int widthStart, TextUtils.TruncateAt ellipsize, float ellipsisWidth, float textWidth, TextPaint paint, boolean moreChars) {
        int i;
        int j;
        TextUtils.TruncateAt truncateAt;
        int above2;
        int below2;
        int top2;
        int bottom2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int extra;
        int want;
        int j2;
        TextUtils.TruncateAt truncateAt2;
        boolean z;
        TextUtils.TruncateAt truncateAt3 = ellipsize;
        int j3 = this.mLineCount;
        int off = j3 * this.mColumns;
        boolean z2 = true;
        int want2 = off + this.mColumns + 1;
        int[] lines = this.mLines;
        int dir = measured.getParagraphDir();
        if (want2 >= lines.length) {
            int[] grow = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(want2));
            System.arraycopy(lines, 0, grow, 0, lines.length);
            this.mLines = grow;
            lines = grow;
        }
        int[] lines2 = lines;
        if (j3 >= this.mLineDirections.length) {
            Layout.Directions[] grow2 = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, GrowingArrayUtils.growSize(j3));
            System.arraycopy(this.mLineDirections, 0, grow2, 0, this.mLineDirections.length);
            this.mLineDirections = grow2;
        }
        if (chooseHt != null) {
            fm.ascent = above;
            fm.descent = below;
            fm.top = top;
            fm.bottom = bottom;
            int j4 = 0;
            while (true) {
                int i7 = j4;
                if (i7 >= chooseHt.length) {
                    break;
                }
                if (chooseHt[i7] instanceof LineHeightSpan.WithDensity) {
                    int j5 = chooseHtv[i7];
                    z = false;
                    want = want2;
                    j2 = j3;
                    truncateAt2 = truncateAt3;
                    ((LineHeightSpan.WithDensity) chooseHt[i7]).chooseHeight(text, start, end, j5, v, fm, paint);
                } else {
                    want = want2;
                    j2 = j3;
                    truncateAt2 = truncateAt3;
                    z = false;
                    chooseHt[i7].chooseHeight(text, start, end, chooseHtv[i7], v, fm);
                }
                truncateAt3 = truncateAt2;
                j3 = j2;
                want2 = want;
                z2 = true;
                j4 = i7 + 1;
            }
            j = j3;
            truncateAt = truncateAt3;
            i = 0;
            above2 = fm.ascent;
            below2 = fm.descent;
            top2 = fm.top;
            bottom2 = fm.bottom;
        } else {
            i = 0;
            j = j3;
            truncateAt = truncateAt3;
            above2 = above;
            below2 = below;
            top2 = top;
            bottom2 = bottom;
        }
        int i8 = j == 0 ? 1 : i;
        int i9 = j + 1 == this.mMaximumVisibleLineCount ? 1 : i;
        if (truncateAt != null) {
            boolean forceEllipsis = (moreChars && this.mLineCount + 1 == this.mMaximumVisibleLineCount) ? 1 : i;
            if (((((!(this.mMaximumVisibleLineCount == 1 && moreChars) && (i8 == 0 || moreChars)) || truncateAt == TextUtils.TruncateAt.MARQUEE) && (i8 != 0 || ((i9 == 0 && moreChars) || truncateAt != TextUtils.TruncateAt.END))) ? i : 1) != 0) {
                i4 = 1;
                i2 = widthStart;
                i3 = bufEnd;
                calculateEllipsis(start, end, measured, widthStart, ellipsisWidth, ellipsize, j, textWidth, paint, forceEllipsis);
            } else {
                i4 = 1;
                i2 = widthStart;
                i3 = bufEnd;
            }
        } else {
            i2 = widthStart;
            i3 = bufEnd;
            i4 = 1;
        }
        if (this.mEllipsized) {
            i6 = 1;
        } else {
            if (i2 != i3 && i3 > 0) {
                if (text.charAt(i3 - 1) == '\n') {
                    i5 = i4;
                    if (end != i3 && i5 == 0) {
                        i6 = 1;
                    } else if (start != i3 && i5 != 0) {
                        i6 = 1;
                    } else {
                        i6 = i;
                    }
                }
            }
            i5 = i;
            if (end != i3) {
            }
            if (start != i3) {
            }
            i6 = i;
        }
        int i10 = i6;
        if (i8 != 0) {
            if (trackPad) {
                this.mTopPadding = top2 - above2;
            }
            if (includePad) {
                above2 = top2;
            }
        }
        if (i10 != 0) {
            if (trackPad) {
                this.mBottomPadding = bottom2 - below2;
            }
            if (includePad) {
                below2 = bottom2;
            }
        }
        if (needMultiply && (addLastLineLineSpacing || i10 == 0)) {
            double ex = ((below2 - above2) * (spacingmult - 1.0f)) + spacingadd;
            extra = ex >= 0.0d ? (int) (EXTRA_ROUNDING + ex) : -((int) ((-ex) + EXTRA_ROUNDING));
            lines2[off + 0] = start;
            int i11 = i4;
            lines2[off + 1] = v;
            lines2[off + 2] = below2 + extra;
            lines2[off + 3] = extra;
            if (!this.mEllipsized && i9 != 0) {
                int maxLineBelow = !includePad ? bottom2 : below2;
                this.mMaxLineHeight = v + (maxLineBelow - above2);
            }
            int maxLineBelow2 = below2 - above2;
            int v2 = v + maxLineBelow2 + extra;
            lines2[off + this.mColumns + 0] = end;
            lines2[off + this.mColumns + i11] = v2;
            int i12 = off + 0;
            int i13 = lines2[i12];
            if (hasTab) {
                i = 536870912;
            }
            lines2[i12] = i13 | i;
            lines2[off + 4] = hyphenEdit;
            int i14 = off + 0;
            lines2[i14] = lines2[i14] | (dir << 30);
            this.mLineDirections[j] = measured.getDirections(start - i2, end - i2);
            this.mLineCount++;
            return v2;
        }
        extra = i;
        lines2[off + 0] = start;
        int i112 = i4;
        lines2[off + 1] = v;
        lines2[off + 2] = below2 + extra;
        lines2[off + 3] = extra;
        if (!this.mEllipsized) {
            if (!includePad) {
            }
            this.mMaxLineHeight = v + (maxLineBelow - above2);
        }
        int maxLineBelow22 = below2 - above2;
        int v22 = v + maxLineBelow22 + extra;
        lines2[off + this.mColumns + 0] = end;
        lines2[off + this.mColumns + i112] = v22;
        int i122 = off + 0;
        int i132 = lines2[i122];
        if (hasTab) {
        }
        lines2[i122] = i132 | i;
        lines2[off + 4] = hyphenEdit;
        int i142 = off + 0;
        lines2[i142] = lines2[i142] | (dir << 30);
        this.mLineDirections[j] = measured.getDirections(start - i2, end - i2);
        this.mLineCount++;
        return v22;
    }

    private void calculateEllipsis(int lineStart, int lineEnd, MeasuredParagraph measured, int widthStart, float avail, TextUtils.TruncateAt where, int line, float textWidth, TextPaint paint, boolean forceEllipsis) {
        int i;
        float lsum;
        int right;
        float avail2 = avail - getTotalInsets(line);
        if (textWidth <= avail2 && !forceEllipsis) {
            this.mLines[(this.mColumns * line) + 5] = 0;
            this.mLines[(this.mColumns * line) + 6] = 0;
            return;
        }
        float ellipsisWidth = paint.measureText(TextUtils.getEllipsisString(where));
        int ellipsisStart = 0;
        int ellipsisCount = 0;
        int len = lineEnd - lineStart;
        if (where == TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                float sum = 0.0f;
                int i2 = len;
                while (true) {
                    if (i2 <= 0) {
                        break;
                    }
                    float w = measured.getCharWidthAt(((i2 - 1) + lineStart) - widthStart);
                    if (w + sum + ellipsisWidth > avail2) {
                        while (i2 < len && measured.getCharWidthAt((i2 + lineStart) - widthStart) == 0.0f) {
                            i2++;
                        }
                    } else {
                        sum += w;
                        i2--;
                    }
                }
                ellipsisStart = 0;
                ellipsisCount = i2;
            } else if (Log.isLoggable(TAG, 5)) {
                Log.m64w(TAG, "Start Ellipsis only supported with one line");
            }
        } else if (where == TextUtils.TruncateAt.END || where == TextUtils.TruncateAt.MARQUEE || where == TextUtils.TruncateAt.END_SMALL) {
            float sum2 = 0.0f;
            int i3 = 0;
            while (true) {
                i = i3;
                if (i >= len) {
                    break;
                }
                float w2 = measured.getCharWidthAt((i + lineStart) - widthStart);
                if (w2 + sum2 + ellipsisWidth > avail2) {
                    break;
                }
                sum2 += w2;
                i3 = i + 1;
            }
            int ellipsisCount2 = len - i;
            if (!forceEllipsis || ellipsisCount2 != 0 || len <= 0) {
                ellipsisStart = i;
                ellipsisCount = ellipsisCount2;
            } else {
                ellipsisCount = 1;
                ellipsisStart = len - 1;
            }
        } else if (this.mMaximumVisibleLineCount == 1) {
            float rsum = 0.0f;
            boolean z = false;
            int right2 = len;
            float ravail = (avail2 - ellipsisWidth) / 2.0f;
            while (true) {
                if (right2 > 0) {
                    float w3 = measured.getCharWidthAt(((right2 - 1) + lineStart) - widthStart);
                    if (w3 + rsum <= ravail) {
                        rsum += w3;
                        right2--;
                    } else {
                        lsum = 0.0f;
                        right = right2;
                        while (right < len) {
                            boolean z2 = z;
                            int left = (right + lineStart) - widthStart;
                            if (measured.getCharWidthAt(left) != 0.0f) {
                                break;
                            }
                            right++;
                            z = z2;
                        }
                    }
                } else {
                    lsum = 0.0f;
                    right = right2;
                    break;
                }
            }
            float lavail = (avail2 - ellipsisWidth) - rsum;
            int left2 = 0;
            while (left2 < right) {
                float rsum2 = rsum;
                float w4 = measured.getCharWidthAt((left2 + lineStart) - widthStart);
                if (w4 + lsum > lavail) {
                    break;
                }
                lsum += w4;
                left2++;
                rsum = rsum2;
            }
            ellipsisStart = left2;
            ellipsisCount = right - left2;
        } else if (Log.isLoggable(TAG, 5)) {
            Log.m64w(TAG, "Middle Ellipsis only supported with one line");
        }
        this.mEllipsized = true;
        this.mLines[(this.mColumns * line) + 5] = ellipsisStart;
        this.mLines[(this.mColumns * line) + 6] = ellipsisCount;
    }

    private float getTotalInsets(int line) {
        int totalIndent = 0;
        if (this.mLeftIndents != null) {
            totalIndent = this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
        }
        if (this.mRightIndents != null) {
            totalIndent += this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
        }
        return totalIndent;
    }

    @Override // android.text.Layout
    public int getLineForVertical(int vertical) {
        int high = this.mLineCount;
        int low = -1;
        int[] lines = this.mLines;
        while (high - low > 1) {
            int guess = (high + low) >> 1;
            if (lines[(this.mColumns * guess) + 1] > vertical) {
                high = guess;
            } else {
                low = guess;
            }
        }
        if (low < 0) {
            return 0;
        }
        return low;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mLineCount;
    }

    @Override // android.text.Layout
    public int getLineTop(int line) {
        return this.mLines[(this.mColumns * line) + 1];
    }

    @Override // android.text.Layout
    public int getLineExtra(int line) {
        return this.mLines[(this.mColumns * line) + 3];
    }

    @Override // android.text.Layout
    public int getLineDescent(int line) {
        return this.mLines[(this.mColumns * line) + 2];
    }

    @Override // android.text.Layout
    public int getLineStart(int line) {
        return this.mLines[(this.mColumns * line) + 0] & 536870911;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int line) {
        return this.mLines[(this.mColumns * line) + 0] >> 30;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int line) {
        return (this.mLines[(this.mColumns * line) + 0] & 536870912) != 0;
    }

    @Override // android.text.Layout
    public final Layout.Directions getLineDirections(int line) {
        if (line > getLineCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mLineDirections[line];
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return this.mTopPadding;
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    static int packHyphenEdit(int start, int end) {
        return (start << 3) | end;
    }

    static int unpackStartHyphenEdit(int packedHyphenEdit) {
        return (packedHyphenEdit & 24) >> 3;
    }

    static int unpackEndHyphenEdit(int packedHyphenEdit) {
        return packedHyphenEdit & 7;
    }

    @Override // android.text.Layout
    public int getStartHyphenEdit(int lineNumber) {
        return unpackStartHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int lineNumber) {
        return unpackEndHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getIndentAdjust(int line, Layout.Alignment align) {
        if (align == Layout.Alignment.ALIGN_LEFT) {
            if (this.mLeftIndents == null) {
                return 0;
            }
            return this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
        } else if (align == Layout.Alignment.ALIGN_RIGHT) {
            if (this.mRightIndents == null) {
                return 0;
            }
            return -this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
        } else if (align == Layout.Alignment.ALIGN_CENTER) {
            int left = 0;
            if (this.mLeftIndents != null) {
                left = this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
            }
            int right = 0;
            if (this.mRightIndents != null) {
                right = this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
            }
            return (left - right) >> 1;
        } else {
            throw new AssertionError("unhandled alignment " + align);
        }
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 6];
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 5];
    }

    @Override // android.text.Layout
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override // android.text.Layout
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public int getHeight(boolean cap) {
        if (cap && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && Log.isLoggable(TAG, 5)) {
            Log.m64w(TAG, "maxLineHeight should not be -1.  maxLines:" + this.mMaximumVisibleLineCount + " lineCount:" + this.mLineCount);
        }
        return (!cap || this.mLineCount <= this.mMaximumVisibleLineCount || this.mMaxLineHeight == -1) ? super.getHeight() : this.mMaxLineHeight;
    }

    /* loaded from: classes4.dex */
    static class LineBreaks {
        private static final int INITIAL_SIZE = 16;
        @UnsupportedAppUsage
        public int[] breaks = new int[16];
        @UnsupportedAppUsage
        public float[] widths = new float[16];
        @UnsupportedAppUsage
        public float[] ascents = new float[16];
        @UnsupportedAppUsage
        public float[] descents = new float[16];
        @UnsupportedAppUsage
        public int[] flags = new int[16];

        LineBreaks() {
        }
    }
}
