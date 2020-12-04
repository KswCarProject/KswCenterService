package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;

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

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool<>(3);
        /* access modifiers changed from: private */
        public boolean mAddLastLineLineSpacing;
        /* access modifiers changed from: private */
        public Layout.Alignment mAlignment;
        /* access modifiers changed from: private */
        public int mBreakStrategy;
        /* access modifiers changed from: private */
        public TextUtils.TruncateAt mEllipsize;
        /* access modifiers changed from: private */
        public int mEllipsizedWidth;
        /* access modifiers changed from: private */
        public int mEnd;
        /* access modifiers changed from: private */
        public boolean mFallbackLineSpacing;
        /* access modifiers changed from: private */
        public final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();
        /* access modifiers changed from: private */
        public int mHyphenationFrequency;
        /* access modifiers changed from: private */
        public boolean mIncludePad;
        /* access modifiers changed from: private */
        public int mJustificationMode;
        /* access modifiers changed from: private */
        public int[] mLeftIndents;
        /* access modifiers changed from: private */
        public int mMaxLines;
        /* access modifiers changed from: private */
        public TextPaint mPaint;
        /* access modifiers changed from: private */
        public int[] mRightIndents;
        /* access modifiers changed from: private */
        public float mSpacingAdd;
        /* access modifiers changed from: private */
        public float mSpacingMult;
        /* access modifiers changed from: private */
        public int mStart;
        /* access modifiers changed from: private */
        public CharSequence mText;
        /* access modifiers changed from: private */
        public TextDirectionHeuristic mTextDir;
        /* access modifiers changed from: private */
        public int mWidth;

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

        /* access modifiers changed from: private */
        public static void recycle(Builder b) {
            b.mPaint = null;
            b.mText = null;
            b.mLeftIndents = null;
            b.mRightIndents = null;
            sPool.release(b);
        }

        /* access modifiers changed from: package-private */
        public void finish() {
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

        /* access modifiers changed from: package-private */
        public Builder setAddLastLineLineSpacing(boolean value) {
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
        this(source, bufstart, bufend, paint, outerwidth, align, spacingmult, spacingadd, includepad, (TextUtils.TruncateAt) null, 0);
    }

    @Deprecated
    public StaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth) {
        this(source, bufstart, bufend, paint, outerwidth, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth, Integer.MAX_VALUE);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    @android.annotation.UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 117521430)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public StaticLayout(java.lang.CharSequence r15, int r16, int r17, android.text.TextPaint r18, int r19, android.text.Layout.Alignment r20, android.text.TextDirectionHeuristic r21, float r22, float r23, boolean r24, android.text.TextUtils.TruncateAt r25, int r26, int r27) {
        /*
            r14 = this;
            r8 = r14
            r9 = r15
            r10 = r25
            r11 = r26
            r12 = r27
            if (r10 != 0) goto L_0x000d
            r1 = r9
            goto L_0x001e
        L_0x000d:
            boolean r0 = r9 instanceof android.text.Spanned
            if (r0 == 0) goto L_0x0018
            android.text.Layout$SpannedEllipsizer r0 = new android.text.Layout$SpannedEllipsizer
            r0.<init>(r15)
        L_0x0016:
            r1 = r0
            goto L_0x001e
        L_0x0018:
            android.text.Layout$Ellipsizer r0 = new android.text.Layout$Ellipsizer
            r0.<init>(r15)
            goto L_0x0016
        L_0x001e:
            r0 = r14
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r22
            r7 = r23
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r0 = -1
            r8.mMaxLineHeight = r0
            r0 = 2147483647(0x7fffffff, float:NaN)
            r8.mMaximumVisibleLineCount = r0
            android.text.StaticLayout$Builder r0 = android.text.StaticLayout.Builder.obtain(r15, r16, r17, r18, r19)
            r1 = r20
            android.text.StaticLayout$Builder r0 = r0.setAlignment(r1)
            r2 = r21
            android.text.StaticLayout$Builder r0 = r0.setTextDirection(r2)
            r3 = r22
            r4 = r23
            android.text.StaticLayout$Builder r0 = r0.setLineSpacing(r4, r3)
            r5 = r24
            android.text.StaticLayout$Builder r0 = r0.setIncludePad(r5)
            android.text.StaticLayout$Builder r0 = r0.setEllipsizedWidth(r11)
            android.text.StaticLayout$Builder r0 = r0.setEllipsize(r10)
            android.text.StaticLayout$Builder r0 = r0.setMaxLines(r12)
            if (r10 == 0) goto L_0x0077
            java.lang.CharSequence r6 = r14.getText()
            android.text.Layout$Ellipsizer r6 = (android.text.Layout.Ellipsizer) r6
            r6.mLayout = r8
            r6.mWidth = r11
            r6.mMethod = r10
            r8.mEllipsizedWidth = r11
            r7 = 7
            r8.mColumns = r7
            r6 = r19
            goto L_0x007e
        L_0x0077:
            r6 = 5
            r8.mColumns = r6
            r6 = r19
            r8.mEllipsizedWidth = r6
        L_0x007e:
            java.lang.Class<android.text.Layout$Directions> r7 = android.text.Layout.Directions.class
            r13 = 2
            java.lang.Object[] r7 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r7, r13)
            android.text.Layout$Directions[] r7 = (android.text.Layout.Directions[]) r7
            r8.mLineDirections = r7
            int r7 = r8.mColumns
            int r7 = r7 * r13
            int[] r7 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r7)
            r8.mLines = r7
            r8.mMaximumVisibleLineCount = r12
            boolean r7 = r0.mIncludePad
            boolean r13 = r0.mIncludePad
            r14.generate(r0, r7, r13)
            android.text.StaticLayout.Builder.recycle(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.<init>(java.lang.CharSequence, int, int, android.text.TextPaint, int, android.text.Layout$Alignment, android.text.TextDirectionHeuristic, float, float, boolean, android.text.TextUtils$TruncateAt, int, int):void");
    }

    StaticLayout(CharSequence text) {
        super(text, (TextPaint) null, 0, (Layout.Alignment) null, 0.0f, 0.0f);
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = 7;
        this.mLineDirections = (Layout.Directions[]) ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private StaticLayout(android.text.StaticLayout.Builder r11) {
        /*
            r10 = this;
            android.text.TextUtils$TruncateAt r0 = r11.mEllipsize
            if (r0 != 0) goto L_0x000c
            java.lang.CharSequence r0 = r11.mText
        L_0x000a:
            r3 = r0
            goto L_0x0028
        L_0x000c:
            java.lang.CharSequence r0 = r11.mText
            boolean r0 = r0 instanceof android.text.Spanned
            if (r0 == 0) goto L_0x001e
            android.text.Layout$SpannedEllipsizer r0 = new android.text.Layout$SpannedEllipsizer
            java.lang.CharSequence r1 = r11.mText
            r0.<init>(r1)
            goto L_0x000a
        L_0x001e:
            android.text.Layout$Ellipsizer r0 = new android.text.Layout$Ellipsizer
            java.lang.CharSequence r1 = r11.mText
            r0.<init>(r1)
            goto L_0x000a
        L_0x0028:
            android.text.TextPaint r4 = r11.mPaint
            int r5 = r11.mWidth
            android.text.Layout$Alignment r6 = r11.mAlignment
            android.text.TextDirectionHeuristic r7 = r11.mTextDir
            float r8 = r11.mSpacingMult
            float r9 = r11.mSpacingAdd
            r2 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            r0 = -1
            r10.mMaxLineHeight = r0
            r0 = 2147483647(0x7fffffff, float:NaN)
            r10.mMaximumVisibleLineCount = r0
            android.text.TextUtils$TruncateAt r0 = r11.mEllipsize
            if (r0 == 0) goto L_0x0070
            java.lang.CharSequence r0 = r10.getText()
            android.text.Layout$Ellipsizer r0 = (android.text.Layout.Ellipsizer) r0
            r0.mLayout = r10
            int r1 = r11.mEllipsizedWidth
            r0.mWidth = r1
            android.text.TextUtils$TruncateAt r1 = r11.mEllipsize
            r0.mMethod = r1
            int r1 = r11.mEllipsizedWidth
            r10.mEllipsizedWidth = r1
            r1 = 7
            r10.mColumns = r1
            goto L_0x0079
        L_0x0070:
            r0 = 5
            r10.mColumns = r0
            int r0 = r11.mWidth
            r10.mEllipsizedWidth = r0
        L_0x0079:
            java.lang.Class<android.text.Layout$Directions> r0 = android.text.Layout.Directions.class
            r1 = 2
            java.lang.Object[] r0 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r0, r1)
            android.text.Layout$Directions[] r0 = (android.text.Layout.Directions[]) r0
            r10.mLineDirections = r0
            int r0 = r10.mColumns
            int r0 = r0 * r1
            int[] r0 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r0)
            r10.mLines = r0
            int r0 = r11.mMaxLines
            r10.mMaximumVisibleLineCount = r0
            int[] r0 = r11.mLeftIndents
            r10.mLeftIndents = r0
            int[] r0 = r11.mRightIndents
            r10.mRightIndents = r0
            int r0 = r11.mJustificationMode
            r10.setJustificationMode(r0)
            boolean r0 = r11.mIncludePad
            boolean r1 = r11.mIncludePad
            r10.generate(r11, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.<init>(android.text.StaticLayout$Builder):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x030d, code lost:
        if (r3 != android.text.TextUtils.TruncateAt.MARQUEE) goto L_0x0312;
     */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0314  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x031d A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x038d  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x05c8 A[LOOP:2: B:45:0x0176->B:172:0x05c8, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x05bd A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x02ad  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x02c0 A[LOOP:6: B:91:0x02be->B:92:0x02c0, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02fe  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void generate(android.text.StaticLayout.Builder r98, boolean r99, boolean r100) {
        /*
            r97 = this;
            r13 = r97
            java.lang.CharSequence r12 = r98.mText
            int r11 = r98.mStart
            int r10 = r98.mEnd
            android.text.TextPaint r9 = r98.mPaint
            int r46 = r98.mWidth
            android.text.TextDirectionHeuristic r8 = r98.mTextDir
            boolean r47 = r98.mFallbackLineSpacing
            float r48 = r98.mSpacingMult
            float r49 = r98.mSpacingAdd
            int r0 = r98.mEllipsizedWidth
            float r7 = (float) r0
            android.text.TextUtils$TruncateAt r15 = r98.mEllipsize
            boolean r50 = r98.mAddLastLineLineSpacing
            r14 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r6 = 0
            r13.mLineCount = r6
            r13.mEllipsized = r6
            int r0 = r13.mMaximumVisibleLineCount
            r5 = 1
            if (r0 >= r5) goto L_0x004c
            r0 = r6
            goto L_0x004d
        L_0x004c:
            r0 = -1
        L_0x004d:
            r13.mMaxLineHeight = r0
            r23 = 0
            r0 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r48 > r0 ? 1 : (r48 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x0060
            r0 = 0
            int r0 = (r49 > r0 ? 1 : (r49 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x005d
            goto L_0x0060
        L_0x005d:
            r16 = r6
            goto L_0x0062
        L_0x0060:
            r16 = r5
        L_0x0062:
            android.graphics.Paint$FontMetricsInt r4 = r98.mFontMetricsInt
            r24 = 0
            int[] r0 = r13.mLeftIndents
            if (r0 != 0) goto L_0x0073
            int[] r0 = r13.mRightIndents
            if (r0 == 0) goto L_0x0071
            goto L_0x0073
        L_0x0071:
            r3 = 0
            goto L_0x00b2
        L_0x0073:
            int[] r0 = r13.mLeftIndents
            if (r0 != 0) goto L_0x0079
            r0 = r6
            goto L_0x007c
        L_0x0079:
            int[] r0 = r13.mLeftIndents
            int r0 = r0.length
        L_0x007c:
            int[] r1 = r13.mRightIndents
            if (r1 != 0) goto L_0x0082
            r1 = r6
            goto L_0x0085
        L_0x0082:
            int[] r1 = r13.mRightIndents
            int r1 = r1.length
        L_0x0085:
            int r2 = java.lang.Math.max(r0, r1)
            int[] r3 = new int[r2]
            r25 = r6
        L_0x008d:
            r52 = r25
            r5 = r52
            if (r5 >= r0) goto L_0x009e
            int[] r6 = r13.mLeftIndents
            r6 = r6[r5]
            r3[r5] = r6
            int r25 = r5 + 1
            r5 = 1
            r6 = 0
            goto L_0x008d
        L_0x009e:
            r5 = 0
        L_0x009f:
            if (r5 >= r1) goto L_0x00b1
            r6 = r3[r5]
            r55 = r0
            int[] r0 = r13.mRightIndents
            r0 = r0[r5]
            int r6 = r6 + r0
            r3[r5] = r6
            int r5 = r5 + 1
            r0 = r55
            goto L_0x009f
        L_0x00b1:
        L_0x00b2:
            r6 = r3
            android.graphics.text.LineBreaker$Builder r0 = new android.graphics.text.LineBreaker$Builder
            r0.<init>()
            int r1 = r98.mBreakStrategy
            android.graphics.text.LineBreaker$Builder r0 = r0.setBreakStrategy(r1)
            int r1 = r98.mHyphenationFrequency
            android.graphics.text.LineBreaker$Builder r0 = r0.setHyphenationFrequency(r1)
            int r1 = r98.mJustificationMode
            android.graphics.text.LineBreaker$Builder r0 = r0.setJustificationMode(r1)
            android.graphics.text.LineBreaker$Builder r0 = r0.setIndents(r6)
            android.graphics.text.LineBreaker r5 = r0.build()
            android.graphics.text.LineBreaker$ParagraphConstraints r0 = new android.graphics.text.LineBreaker$ParagraphConstraints
            r0.<init>()
            r3 = r0
            r25 = 0
            boolean r0 = r12 instanceof android.text.Spanned
            if (r0 == 0) goto L_0x00e8
            r0 = r12
            android.text.Spanned r0 = (android.text.Spanned) r0
            goto L_0x00e9
        L_0x00e8:
            r0 = 0
        L_0x00e9:
            r2 = r0
            boolean r0 = r12 instanceof android.text.PrecomputedText
            if (r0 == 0) goto L_0x014d
            r1 = r12
            android.text.PrecomputedText r1 = (android.text.PrecomputedText) r1
            int r26 = r98.mBreakStrategy
            int r27 = r98.mHyphenationFrequency
            r0 = r1
            r56 = r1
            r1 = r11
            r57 = r7
            r7 = r2
            r2 = r10
            r58 = r14
            r14 = r3
            r3 = r8
            r59 = r4
            r4 = r9
            r61 = r5
            r60 = r15
            r15 = 1
            r5 = r26
            r51 = r6
            r15 = 0
            r6 = r27
            int r0 = r0.checkResultUsable(r1, r2, r3, r4, r5, r6)
            switch(r0) {
                case 0: goto L_0x014a;
                case 1: goto L_0x0122;
                case 2: goto L_0x011d;
                default: goto L_0x011c;
            }
        L_0x011c:
            goto L_0x015c
        L_0x011d:
            android.text.PrecomputedText$ParagraphInfo[] r25 = r56.getParagraphInfo()
            goto L_0x015c
        L_0x0122:
            android.text.PrecomputedText$Params$Builder r1 = new android.text.PrecomputedText$Params$Builder
            r1.<init>((android.text.TextPaint) r9)
            int r2 = r98.mBreakStrategy
            android.text.PrecomputedText$Params$Builder r1 = r1.setBreakStrategy(r2)
            int r2 = r98.mHyphenationFrequency
            android.text.PrecomputedText$Params$Builder r1 = r1.setHyphenationFrequency(r2)
            android.text.PrecomputedText$Params$Builder r1 = r1.setTextDirection(r8)
            android.text.PrecomputedText$Params r1 = r1.build()
            r2 = r56
            android.text.PrecomputedText r2 = android.text.PrecomputedText.create(r2, r1)
            android.text.PrecomputedText$ParagraphInfo[] r25 = r2.getParagraphInfo()
            goto L_0x015c
        L_0x014a:
            r2 = r56
            goto L_0x015c
        L_0x014d:
            r59 = r4
            r61 = r5
            r51 = r6
            r57 = r7
            r58 = r14
            r60 = r15
            r15 = 0
            r7 = r2
            r14 = r3
        L_0x015c:
            if (r25 != 0) goto L_0x016f
            android.text.PrecomputedText$Params r0 = new android.text.PrecomputedText$Params
            int r1 = r98.mBreakStrategy
            int r2 = r98.mHyphenationFrequency
            r0.<init>(r9, r8, r1, r2)
            android.text.PrecomputedText$ParagraphInfo[] r25 = android.text.PrecomputedText.createMeasuredParagraphs(r12, r0, r11, r10, r15)
        L_0x016f:
            r6 = r25
            r0 = r15
            r1 = r24
            r2 = r58
        L_0x0176:
            r3 = r0
            int r0 = r6.length
            if (r3 >= r0) goto L_0x05f5
            if (r3 != 0) goto L_0x017e
            r0 = r11
            goto L_0x0184
        L_0x017e:
            int r0 = r3 + -1
            r0 = r6[r0]
            int r0 = r0.paragraphEnd
        L_0x0184:
            r4 = r6[r3]
            int r5 = r4.paragraphEnd
            r4 = 1
            r24 = r46
            r25 = r46
            r26 = 0
            if (r7 == 0) goto L_0x0214
            java.lang.Class<android.text.style.LeadingMarginSpan> r15 = android.text.style.LeadingMarginSpan.class
            java.lang.Object[] r15 = getParagraphSpans(r7, r0, r5, r15)
            android.text.style.LeadingMarginSpan[] r15 = (android.text.style.LeadingMarginSpan[]) r15
            r64 = r8
            r8 = r4
            r4 = 0
        L_0x019d:
            r65 = r9
            int r9 = r15.length
            if (r4 >= r9) goto L_0x01d3
            r9 = r15[r4]
            r66 = r11
            r11 = r15[r4]
            r67 = r12
            r12 = 1
            int r11 = r11.getLeadingMargin(r12)
            int r24 = r24 - r11
            r11 = r15[r4]
            r12 = 0
            int r11 = r11.getLeadingMargin(r12)
            int r25 = r25 - r11
            boolean r11 = r9 instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2
            if (r11 == 0) goto L_0x01ca
            r11 = r9
            android.text.style.LeadingMarginSpan$LeadingMarginSpan2 r11 = (android.text.style.LeadingMarginSpan.LeadingMarginSpan2) r11
            int r12 = r11.getLeadingMarginLineCount()
            int r8 = java.lang.Math.max(r8, r12)
        L_0x01ca:
            int r4 = r4 + 1
            r9 = r65
            r11 = r66
            r12 = r67
            goto L_0x019d
        L_0x01d3:
            r66 = r11
            r67 = r12
            java.lang.Class<android.text.style.LineHeightSpan> r4 = android.text.style.LineHeightSpan.class
            java.lang.Object[] r4 = getParagraphSpans(r7, r0, r5, r4)
            android.text.style.LineHeightSpan[] r4 = (android.text.style.LineHeightSpan[]) r4
            int r9 = r4.length
            if (r9 != 0) goto L_0x01ed
            r4 = 0
        L_0x01e3:
            r29 = r1
            r30 = r4
            r12 = r8
            r11 = r24
            r9 = r25
            goto L_0x0225
        L_0x01ed:
            if (r1 == 0) goto L_0x01f3
            int r9 = r1.length
            int r11 = r4.length
            if (r9 >= r11) goto L_0x01f8
        L_0x01f3:
            int r9 = r4.length
            int[] r1 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r9)
        L_0x01f8:
            r9 = 0
        L_0x01f9:
            int r11 = r4.length
            if (r9 >= r11) goto L_0x01e3
            r11 = r4[r9]
            int r11 = r7.getSpanStart(r11)
            if (r11 >= r0) goto L_0x020f
            int r12 = r13.getLineForOffset(r11)
            int r12 = r13.getLineTop(r12)
            r1[r9] = r12
            goto L_0x0211
        L_0x020f:
            r1[r9] = r23
        L_0x0211:
            int r9 = r9 + 1
            goto L_0x01f9
        L_0x0214:
            r64 = r8
            r65 = r9
            r66 = r11
            r67 = r12
            r29 = r1
            r12 = r4
            r11 = r24
            r9 = r25
            r30 = r26
        L_0x0225:
            r1 = 0
            if (r7 == 0) goto L_0x0251
            java.lang.Class<android.text.style.TabStopSpan> r4 = android.text.style.TabStopSpan.class
            java.lang.Object[] r4 = getParagraphSpans(r7, r0, r5, r4)
            android.text.style.TabStopSpan[] r4 = (android.text.style.TabStopSpan[]) r4
            int r8 = r4.length
            if (r8 <= 0) goto L_0x0251
            int r8 = r4.length
            float[] r8 = new float[r8]
            r15 = 0
        L_0x0237:
            r68 = r1
            int r1 = r4.length
            if (r15 >= r1) goto L_0x024a
            r1 = r4[r15]
            int r1 = r1.getTabStop()
            float r1 = (float) r1
            r8[r15] = r1
            int r15 = r15 + 1
            r1 = r68
            goto L_0x0237
        L_0x024a:
            int r1 = r8.length
            r15 = 0
            java.util.Arrays.sort(r8, r15, r1)
            r1 = r8
            goto L_0x0255
        L_0x0251:
            r68 = r1
            r8 = r68
        L_0x0255:
            r1 = r6[r3]
            android.text.MeasuredParagraph r1 = r1.measured
            char[] r31 = r1.getChars()
            android.text.AutoGrowArray$IntArray r4 = r1.getSpanEndCache()
            int[] r32 = r4.getRawArray()
            android.text.AutoGrowArray$IntArray r4 = r1.getFontMetrics()
            int[] r33 = r4.getRawArray()
            float r4 = (float) r9
            r14.setWidth(r4)
            float r4 = (float) r11
            r14.setIndent(r4, r12)
            r4 = 1101004800(0x41a00000, float:20.0)
            r14.setTabStops(r8, r4)
            android.graphics.text.MeasuredText r4 = r1.getMeasuredText()
            int r15 = r13.mLineCount
            r69 = r3
            r3 = r61
            android.graphics.text.LineBreaker$Result r15 = r3.computeLineBreaks(r4, r14, r15)
            int r4 = r15.getLineCount()
            if (r2 >= r4) goto L_0x02ad
            r2 = r4
            r70 = r3
            int[] r3 = new int[r2]
            r71 = r3
            float[] r3 = new float[r2]
            r72 = r3
            float[] r3 = new float[r2]
            r73 = r3
            float[] r3 = new float[r2]
            r74 = r3
            boolean[] r3 = new boolean[r2]
            r75 = r3
            int[] r3 = new int[r2]
            r35 = r2
            r34 = r3
            goto L_0x02bd
        L_0x02ad:
            r70 = r3
            r35 = r2
            r71 = r17
            r72 = r18
            r73 = r19
            r74 = r20
            r75 = r21
            r34 = r22
        L_0x02bd:
            r2 = 0
        L_0x02be:
            if (r2 >= r4) goto L_0x02f4
            int r3 = r15.getLineBreakOffset(r2)
            r71[r2] = r3
            float r3 = r15.getLineWidth(r2)
            r72[r2] = r3
            float r3 = r15.getLineAscent(r2)
            r73[r2] = r3
            float r3 = r15.getLineDescent(r2)
            r74[r2] = r3
            boolean r3 = r15.hasLineTab(r2)
            r75[r2] = r3
            int r3 = r15.getStartLineHyphenEdit(r2)
            r76 = r6
            int r6 = r15.getEndLineHyphenEdit(r2)
            int r3 = packHyphenEdit(r3, r6)
            r34[r2] = r3
            int r2 = r2 + 1
            r6 = r76
            goto L_0x02be
        L_0x02f4:
            r76 = r6
            int r2 = r13.mMaximumVisibleLineCount
            int r3 = r13.mLineCount
            int r6 = r2 - r3
            if (r60 == 0) goto L_0x0314
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.END
            r3 = r60
            if (r3 == r2) goto L_0x0310
            int r2 = r13.mMaximumVisibleLineCount
            r77 = r7
            r7 = 1
            if (r2 != r7) goto L_0x0318
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r3 == r2) goto L_0x0318
            goto L_0x0312
        L_0x0310:
            r77 = r7
        L_0x0312:
            r2 = 1
            goto L_0x0319
        L_0x0314:
            r77 = r7
            r3 = r60
        L_0x0318:
            r2 = 0
        L_0x0319:
            r36 = r2
            if (r6 <= 0) goto L_0x0373
            if (r6 >= r4) goto L_0x0373
            if (r36 == 0) goto L_0x0373
            r2 = 0
            r7 = 0
            int r17 = r6 + -1
        L_0x0325:
            r78 = r17
            r79 = r3
            r3 = r78
            if (r3 >= r4) goto L_0x035d
            r80 = r8
            int r8 = r4 + -1
            if (r3 != r8) goto L_0x0339
            r8 = r72[r3]
            float r2 = r2 + r8
            r81 = r9
            goto L_0x0351
        L_0x0339:
            if (r3 != 0) goto L_0x033d
            r8 = 0
            goto L_0x0341
        L_0x033d:
            int r78 = r3 + -1
            r8 = r71[r78]
        L_0x0341:
            r81 = r9
            r9 = r71[r3]
            if (r8 >= r9) goto L_0x0351
            float r9 = r1.getCharWidthAt(r8)
            float r2 = r2 + r9
            int r8 = r8 + 1
            r9 = r81
            goto L_0x0341
        L_0x0351:
            boolean r8 = r75[r3]
            r7 = r7 | r8
            int r17 = r3 + 1
            r3 = r79
            r8 = r80
            r9 = r81
            goto L_0x0325
        L_0x035d:
            r80 = r8
            r81 = r9
            int r3 = r6 + -1
            int r8 = r4 + -1
            r8 = r71[r8]
            r71[r3] = r8
            int r3 = r6 + -1
            r72[r3] = r2
            int r3 = r6 + -1
            r75[r3] = r7
            r4 = r6
            goto L_0x0379
        L_0x0373:
            r79 = r3
            r80 = r8
            r81 = r9
        L_0x0379:
            r9 = r4
            r2 = r0
            r3 = 0
            r4 = 0
            r7 = 0
            r8 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r82 = r1
            r1 = r8
            r8 = r7
            r7 = r4
            r4 = r2
        L_0x038b:
            if (r2 >= r5) goto L_0x0582
            int r37 = r18 + 1
            r13 = r32[r18]
            int r18 = r17 * 4
            r20 = 0
            int r18 = r18 + 0
            r83 = r4
            r4 = r33[r18]
            r84 = r12
            r12 = r59
            r12.top = r4
            int r4 = r17 * 4
            r18 = 1
            int r4 = r4 + 1
            r4 = r33[r4]
            r12.bottom = r4
            int r4 = r17 * 4
            int r4 = r4 + 2
            r4 = r33[r4]
            r12.ascent = r4
            int r4 = r17 * 4
            int r4 = r4 + 3
            r4 = r33[r4]
            r12.descent = r4
            int r38 = r17 + 1
            int r4 = r12.top
            if (r4 >= r3) goto L_0x03c3
            int r3 = r12.top
        L_0x03c3:
            int r4 = r12.ascent
            if (r4 >= r8) goto L_0x03c9
            int r8 = r12.ascent
        L_0x03c9:
            int r4 = r12.descent
            if (r4 <= r1) goto L_0x03cf
            int r1 = r12.descent
        L_0x03cf:
            int r4 = r12.bottom
            if (r4 <= r7) goto L_0x03d5
            int r7 = r12.bottom
        L_0x03d5:
            r4 = r19
        L_0x03d7:
            if (r4 >= r9) goto L_0x03e6
            r17 = r71[r4]
            r85 = r1
            int r1 = r0 + r17
            if (r1 >= r2) goto L_0x03e8
            int r4 = r4 + 1
            r1 = r85
            goto L_0x03d7
        L_0x03e6:
            r85 = r1
        L_0x03e8:
            r40 = r3
            r41 = r7
            r7 = r8
            r39 = r23
            r3 = r85
            r8 = r4
        L_0x03f2:
            if (r8 >= r9) goto L_0x0504
            r1 = r71[r8]
            int r1 = r1 + r0
            if (r1 > r13) goto L_0x0504
            r1 = r71[r8]
            int r1 = r1 + r0
            if (r1 >= r10) goto L_0x0401
            r28 = r18
            goto L_0x0403
        L_0x0401:
            r28 = r20
        L_0x0403:
            if (r47 == 0) goto L_0x0410
            r4 = r73[r8]
            int r4 = java.lang.Math.round(r4)
            int r4 = java.lang.Math.min(r7, r4)
            goto L_0x0411
        L_0x0410:
            r4 = r7
        L_0x0411:
            if (r47 == 0) goto L_0x0420
            r17 = r74[r8]
            r86 = r0
            int r0 = java.lang.Math.round(r17)
            int r0 = java.lang.Math.max(r3, r0)
            goto L_0x0423
        L_0x0420:
            r86 = r0
            r0 = r3
        L_0x0423:
            r87 = r5
            r5 = r0
            boolean r0 = r75[r8]
            r52 = r14
            r14 = r0
            r0 = r34[r8]
            r42 = r15
            r43 = r18
            r44 = r20
            r54 = r70
            r53 = r79
            r15 = r0
            r26 = r72[r8]
            r45 = r86
            r0 = r97
            r88 = r1
            r55 = r82
            r1 = r67
            r56 = r2
            r2 = r83
            r85 = r3
            r58 = r69
            r3 = r88
            r59 = r6
            r60 = r76
            r6 = r40
            r62 = r7
            r61 = r77
            r7 = r41
            r63 = r8
            r89 = r64
            r68 = r80
            r8 = r39
            r90 = r65
            r64 = r81
            r65 = r9
            r9 = r48
            r91 = r10
            r10 = r49
            r92 = r66
            r66 = r11
            r11 = r30
            r94 = r12
            r93 = r67
            r67 = r84
            r12 = r29
            r95 = r13
            r13 = r94
            r17 = r55
            r18 = r91
            r19 = r99
            r20 = r100
            r21 = r50
            r22 = r31
            r23 = r45
            r24 = r53
            r25 = r57
            r27 = r90
            int r39 = r0.out(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)
            r1 = r88
            r0 = r95
            if (r1 >= r0) goto L_0x04ae
            r6 = r94
            int r2 = r6.top
            int r3 = r6.bottom
            int r7 = r6.ascent
            int r8 = r6.descent
            r40 = r2
            r41 = r3
            r3 = r8
            goto L_0x04be
        L_0x04ae:
            r6 = r94
            r2 = r44
            r3 = r44
            r7 = r44
            r8 = r44
            r41 = r7
            r40 = r8
            r7 = r3
            r3 = r2
        L_0x04be:
            r83 = r1
            int r8 = r63 + 1
            r9 = r97
            int r2 = r9.mLineCount
            int r10 = r9.mMaximumVisibleLineCount
            if (r2 < r10) goto L_0x04cf
            boolean r2 = r9.mEllipsized
            if (r2 == 0) goto L_0x04cf
            return
        L_0x04cf:
            r13 = r0
            r12 = r6
            r15 = r42
            r18 = r43
            r20 = r44
            r0 = r45
            r14 = r52
            r79 = r53
            r70 = r54
            r82 = r55
            r2 = r56
            r69 = r58
            r6 = r59
            r76 = r60
            r77 = r61
            r81 = r64
            r9 = r65
            r11 = r66
            r84 = r67
            r80 = r68
            r5 = r87
            r64 = r89
            r65 = r90
            r10 = r91
            r66 = r92
            r67 = r93
            goto L_0x03f2
        L_0x0504:
            r45 = r0
            r56 = r2
            r85 = r3
            r87 = r5
            r59 = r6
            r62 = r7
            r63 = r8
            r91 = r10
            r6 = r12
            r0 = r13
            r52 = r14
            r42 = r15
            r43 = r18
            r44 = r20
            r89 = r64
            r90 = r65
            r92 = r66
            r93 = r67
            r58 = r69
            r54 = r70
            r60 = r76
            r61 = r77
            r53 = r79
            r68 = r80
            r64 = r81
            r55 = r82
            r67 = r84
            r65 = r9
            r66 = r11
            r9 = r97
            r2 = r0
            r13 = r9
            r18 = r37
            r17 = r38
            r23 = r39
            r3 = r40
            r7 = r41
            r15 = r42
            r0 = r45
            r14 = r52
            r79 = r53
            r70 = r54
            r82 = r55
            r69 = r58
            r76 = r60
            r77 = r61
            r8 = r62
            r19 = r63
            r81 = r64
            r9 = r65
            r11 = r66
            r12 = r67
            r80 = r68
            r4 = r83
            r1 = r85
            r5 = r87
            r64 = r89
            r65 = r90
            r10 = r91
            r66 = r92
            r67 = r93
            r96 = r59
            r59 = r6
            r6 = r96
            goto L_0x038b
        L_0x0582:
            r45 = r0
            r83 = r4
            r87 = r5
            r91 = r10
            r52 = r14
            r42 = r15
            r89 = r64
            r90 = r65
            r92 = r66
            r93 = r67
            r58 = r69
            r54 = r70
            r60 = r76
            r61 = r77
            r53 = r79
            r68 = r80
            r64 = r81
            r55 = r82
            r43 = 1
            r44 = 0
            r65 = r9
            r66 = r11
            r67 = r12
            r9 = r13
            r96 = r59
            r59 = r6
            r6 = r96
            r2 = r87
            r0 = r91
            if (r2 != r0) goto L_0x05c8
            r1 = r23
            r4 = r29
            r3 = r34
            r2 = r35
            goto L_0x061a
        L_0x05c8:
            int r1 = r58 + 1
            r10 = r0
            r0 = r1
            r59 = r6
            r13 = r9
            r1 = r29
            r22 = r34
            r2 = r35
            r15 = r44
            r14 = r52
            r6 = r60
            r7 = r61
            r17 = r71
            r18 = r72
            r19 = r73
            r20 = r74
            r21 = r75
            r8 = r89
            r9 = r90
            r11 = r92
            r12 = r93
            r60 = r53
            r61 = r54
            goto L_0x0176
        L_0x05f5:
            r89 = r8
            r90 = r9
            r0 = r10
            r92 = r11
            r93 = r12
            r9 = r13
            r52 = r14
            r53 = r60
            r54 = r61
            r60 = r6
            r61 = r7
            r6 = r59
            r4 = r1
            r71 = r17
            r72 = r18
            r73 = r19
            r74 = r20
            r75 = r21
            r3 = r22
            r1 = r23
        L_0x061a:
            r5 = r92
            if (r0 == r5) goto L_0x0631
            int r10 = r0 + -1
            r7 = r93
            char r8 = r7.charAt(r10)
            r10 = 10
            if (r8 != r10) goto L_0x062b
            goto L_0x0633
        L_0x062b:
            r8 = r89
            r11 = r90
            goto L_0x0691
        L_0x0631:
            r7 = r93
        L_0x0633:
            int r8 = r9.mLineCount
            int r10 = r9.mMaximumVisibleLineCount
            if (r8 >= r10) goto L_0x068d
            r8 = r89
            r10 = 0
            android.text.MeasuredParagraph r10 = android.text.MeasuredParagraph.buildForBidi(r7, r0, r0, r8, r10)
            r34 = r10
            r11 = r90
            r11.getFontMetricsInt(r6)
            int r12 = r6.ascent
            r21 = r12
            int r12 = r6.descent
            r22 = r12
            int r12 = r6.top
            r23 = r12
            int r12 = r6.bottom
            r24 = r12
            r28 = 0
            r29 = 0
            r31 = 0
            r32 = 0
            r39 = 0
            r43 = 0
            r45 = 0
            r17 = r97
            r18 = r7
            r19 = r0
            r20 = r0
            r25 = r1
            r26 = r48
            r27 = r49
            r30 = r6
            r33 = r16
            r35 = r0
            r36 = r99
            r37 = r100
            r38 = r50
            r40 = r5
            r41 = r53
            r42 = r57
            r44 = r11
            int r1 = r17.out(r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45)
            goto L_0x0691
        L_0x068d:
            r8 = r89
            r11 = r90
        L_0x0691:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.generate(android.text.StaticLayout$Builder, boolean, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:93:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x020d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int out(java.lang.CharSequence r33, int r34, int r35, int r36, int r37, int r38, int r39, int r40, float r41, float r42, android.text.style.LineHeightSpan[] r43, int[] r44, android.graphics.Paint.FontMetricsInt r45, boolean r46, int r47, boolean r48, android.text.MeasuredParagraph r49, int r50, boolean r51, boolean r52, boolean r53, char[] r54, int r55, android.text.TextUtils.TruncateAt r56, float r57, float r58, android.text.TextPaint r59, boolean r60) {
        /*
            r32 = this;
            r11 = r32
            r12 = r34
            r13 = r35
            r15 = r43
            r9 = r45
            r8 = r50
            r10 = r55
            r7 = r56
            int r6 = r11.mLineCount
            int r0 = r11.mColumns
            int r16 = r6 * r0
            int r0 = r11.mColumns
            int r0 = r16 + r0
            r5 = 1
            int r4 = r0 + 1
            int[] r0 = r11.mLines
            int r17 = r49.getParagraphDir()
            int r1 = r0.length
            r3 = 0
            if (r4 < r1) goto L_0x0036
            int r1 = com.android.internal.util.GrowingArrayUtils.growSize(r4)
            int[] r1 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(r1)
            int r2 = r0.length
            java.lang.System.arraycopy(r0, r3, r1, r3, r2)
            r11.mLines = r1
            r0 = r1
        L_0x0036:
            r18 = r0
            android.text.Layout$Directions[] r0 = r11.mLineDirections
            int r0 = r0.length
            if (r6 < r0) goto L_0x0053
            java.lang.Class<android.text.Layout$Directions> r0 = android.text.Layout.Directions.class
            int r1 = com.android.internal.util.GrowingArrayUtils.growSize(r6)
            java.lang.Object[] r0 = com.android.internal.util.ArrayUtils.newUnpaddedArray(r0, r1)
            android.text.Layout$Directions[] r0 = (android.text.Layout.Directions[]) r0
            android.text.Layout$Directions[] r1 = r11.mLineDirections
            android.text.Layout$Directions[] r2 = r11.mLineDirections
            int r2 = r2.length
            java.lang.System.arraycopy(r1, r3, r0, r3, r2)
            r11.mLineDirections = r0
        L_0x0053:
            if (r15 == 0) goto L_0x00da
            r2 = r36
            r9.ascent = r2
            r1 = r37
            r9.descent = r1
            r0 = r38
            r9.top = r0
            r8 = r39
            r9.bottom = r8
            r19 = r3
        L_0x0067:
            r20 = r19
            int r3 = r15.length
            r8 = r20
            if (r8 >= r3) goto L_0x00c2
            r3 = r15[r8]
            boolean r3 = r3 instanceof android.text.style.LineHeightSpan.WithDensity
            if (r3 == 0) goto L_0x0095
            r3 = r15[r8]
            android.text.style.LineHeightSpan$WithDensity r3 = (android.text.style.LineHeightSpan.WithDensity) r3
            r19 = r44[r8]
            r0 = r3
            r1 = r33
            r2 = r34
            r20 = 0
            r3 = r35
            r21 = r4
            r4 = r19
            r14 = r5
            r5 = r40
            r19 = r6
            r6 = r45
            r14 = r7
            r7 = r59
            r0.chooseHeight(r1, r2, r3, r4, r5, r6, r7)
            goto L_0x00ad
        L_0x0095:
            r21 = r4
            r19 = r6
            r14 = r7
            r20 = 0
            r0 = r15[r8]
            r4 = r44[r8]
            r1 = r33
            r2 = r34
            r3 = r35
            r5 = r40
            r6 = r45
            r0.chooseHeight(r1, r2, r3, r4, r5, r6)
        L_0x00ad:
            int r0 = r8 + 1
            r2 = r36
            r1 = r37
            r7 = r14
            r6 = r19
            r3 = r20
            r4 = r21
            r5 = 1
            r8 = r39
            r19 = r0
            r0 = r38
            goto L_0x0067
        L_0x00c2:
            r21 = r4
            r19 = r6
            r14 = r7
            r20 = 0
            int r0 = r9.ascent
            int r1 = r9.descent
            int r2 = r9.top
            int r3 = r9.bottom
            r23 = r0
            r24 = r1
            r25 = r2
            r26 = r3
            goto L_0x00e9
        L_0x00da:
            r20 = r3
            r21 = r4
            r19 = r6
            r14 = r7
            r23 = r36
            r24 = r37
            r25 = r38
            r26 = r39
        L_0x00e9:
            if (r19 != 0) goto L_0x00ed
            r0 = 1
            goto L_0x00ef
        L_0x00ed:
            r0 = r20
        L_0x00ef:
            r27 = r0
            int r6 = r19 + 1
            int r0 = r11.mMaximumVisibleLineCount
            if (r6 != r0) goto L_0x00f9
            r0 = 1
            goto L_0x00fb
        L_0x00f9:
            r0 = r20
        L_0x00fb:
            r28 = r0
            if (r14 == 0) goto L_0x0153
            if (r60 == 0) goto L_0x010b
            int r0 = r11.mLineCount
            r1 = 1
            int r0 = r0 + r1
            int r1 = r11.mMaximumVisibleLineCount
            if (r0 != r1) goto L_0x010b
            r0 = 1
            goto L_0x010d
        L_0x010b:
            r0 = r20
        L_0x010d:
            r8 = r10
            r10 = r0
            int r0 = r11.mMaximumVisibleLineCount
            r7 = 1
            if (r0 != r7) goto L_0x0116
            if (r60 != 0) goto L_0x011a
        L_0x0116:
            if (r27 == 0) goto L_0x011e
            if (r60 != 0) goto L_0x011e
        L_0x011a:
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r14 != r0) goto L_0x0128
        L_0x011e:
            if (r27 != 0) goto L_0x012a
            if (r28 != 0) goto L_0x0124
            if (r60 != 0) goto L_0x012a
        L_0x0124:
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.END
            if (r14 != r0) goto L_0x012a
        L_0x0128:
            r0 = r7
            goto L_0x012c
        L_0x012a:
            r0 = r20
        L_0x012c:
            r22 = r0
            if (r22 == 0) goto L_0x014d
            r0 = r32
            r1 = r34
            r2 = r35
            r3 = r49
            r4 = r55
            r5 = r57
            r6 = r56
            r29 = r7
            r7 = r19
            r15 = r8
            r14 = r50
            r8 = r58
            r9 = r59
            r0.calculateEllipsis(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            goto L_0x0158
        L_0x014d:
            r29 = r7
            r15 = r8
            r14 = r50
            goto L_0x0158
        L_0x0153:
            r15 = r10
            r14 = r50
            r29 = 1
        L_0x0158:
            boolean r0 = r11.mEllipsized
            if (r0 == 0) goto L_0x0160
            r3 = 1
            r1 = r33
            goto L_0x0185
        L_0x0160:
            if (r15 == r14) goto L_0x0173
            if (r14 <= 0) goto L_0x0173
            int r0 = r14 + -1
            r1 = r33
            char r0 = r1.charAt(r0)
            r2 = 10
            if (r0 != r2) goto L_0x0175
            r0 = r29
            goto L_0x0177
        L_0x0173:
            r1 = r33
        L_0x0175:
            r0 = r20
        L_0x0177:
            if (r13 != r14) goto L_0x017d
            if (r0 != 0) goto L_0x017d
            r3 = 1
            goto L_0x0185
        L_0x017d:
            if (r12 != r14) goto L_0x0183
            if (r0 == 0) goto L_0x0183
            r3 = 1
            goto L_0x0185
        L_0x0183:
            r3 = r20
        L_0x0185:
            r0 = r3
            if (r27 == 0) goto L_0x0192
            if (r52 == 0) goto L_0x018e
            int r2 = r25 - r23
            r11.mTopPadding = r2
        L_0x018e:
            if (r51 == 0) goto L_0x0192
            r23 = r25
        L_0x0192:
            if (r0 == 0) goto L_0x019e
            if (r52 == 0) goto L_0x019a
            int r2 = r26 - r24
            r11.mBottomPadding = r2
        L_0x019a:
            if (r51 == 0) goto L_0x019e
            r24 = r26
        L_0x019e:
            if (r48 == 0) goto L_0x01c8
            if (r53 != 0) goto L_0x01a8
            if (r0 != 0) goto L_0x01a5
            goto L_0x01a8
        L_0x01a5:
            r30 = r0
            goto L_0x01ca
        L_0x01a8:
            int r4 = r24 - r23
            float r4 = (float) r4
            r5 = 1065353216(0x3f800000, float:1.0)
            float r5 = r41 - r5
            float r4 = r4 * r5
            float r4 = r4 + r42
            double r7 = (double) r4
            r9 = 0
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r4 < 0) goto L_0x01c0
            double r9 = r9 + r7
            int r4 = (int) r9
            r30 = r0
            goto L_0x01c6
        L_0x01c0:
            r30 = r0
            double r0 = -r7
            double r0 = r0 + r9
            int r0 = (int) r0
            int r4 = -r0
        L_0x01c6:
            r0 = r4
            goto L_0x01cc
        L_0x01c8:
            r30 = r0
        L_0x01ca:
            r0 = r20
        L_0x01cc:
            int r1 = r16 + 0
            r18[r1] = r12
            int r1 = r16 + 1
            r7 = r29
            r18[r1] = r40
            int r1 = r16 + 2
            int r8 = r24 + r0
            r18[r1] = r8
            int r1 = r16 + 3
            r18[r1] = r0
            boolean r1 = r11.mEllipsized
            if (r1 != 0) goto L_0x01f3
            if (r28 == 0) goto L_0x01f3
            if (r51 == 0) goto L_0x01eb
            r1 = r26
            goto L_0x01ed
        L_0x01eb:
            r1 = r24
        L_0x01ed:
            int r8 = r1 - r23
            int r8 = r40 + r8
            r11.mMaxLineHeight = r8
        L_0x01f3:
            int r1 = r24 - r23
            int r1 = r1 + r0
            int r1 = r40 + r1
            int r4 = r11.mColumns
            int r4 = r16 + r4
            int r4 = r4 + 0
            r18[r4] = r13
            int r4 = r11.mColumns
            int r4 = r16 + r4
            int r4 = r4 + r7
            r18[r4] = r1
            int r4 = r16 + 0
            r8 = r18[r4]
            if (r46 == 0) goto L_0x0212
            r10 = 536870912(0x20000000, float:1.0842022E-19)
            r20 = r10
        L_0x0212:
            r8 = r8 | r20
            r18[r4] = r8
            int r4 = r16 + 4
            r18[r4] = r47
            int r4 = r16 + 0
            r10 = r18[r4]
            int r20 = r17 << 30
            r10 = r10 | r20
            r18[r4] = r10
            android.text.Layout$Directions[] r4 = r11.mLineDirections
            int r10 = r12 - r15
            int r7 = r13 - r15
            r31 = r0
            r0 = r49
            android.text.Layout$Directions r7 = r0.getDirections(r10, r7)
            r4[r19] = r7
            int r4 = r11.mLineCount
            r7 = 1
            int r4 = r4 + r7
            r11.mLineCount = r4
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.StaticLayout.out(java.lang.CharSequence, int, int, int, int, int, int, int, float, float, android.text.style.LineHeightSpan[], int[], android.graphics.Paint$FontMetricsInt, boolean, int, boolean, android.text.MeasuredParagraph, int, boolean, boolean, boolean, char[], int, android.text.TextUtils$TruncateAt, float, float, android.text.TextPaint, boolean):int");
    }

    private void calculateEllipsis(int lineStart, int lineEnd, MeasuredParagraph measured, int widthStart, float avail, TextUtils.TruncateAt where, int line, float textWidth, TextPaint paint, boolean forceEllipsis) {
        int i;
        float lsum;
        int right;
        MeasuredParagraph measuredParagraph = measured;
        TextUtils.TruncateAt truncateAt = where;
        int i2 = line;
        float avail2 = avail - getTotalInsets(i2);
        if (textWidth > avail2 || forceEllipsis) {
            float ellipsisWidth = paint.measureText(TextUtils.getEllipsisString(where));
            int ellipsisStart = 0;
            int ellipsisCount = 0;
            int len = lineEnd - lineStart;
            if (truncateAt == TextUtils.TruncateAt.START) {
                if (this.mMaximumVisibleLineCount == 1) {
                    float sum = 0.0f;
                    int i3 = len;
                    while (true) {
                        if (i3 <= 0) {
                            break;
                        }
                        float w = measuredParagraph.getCharWidthAt(((i3 - 1) + lineStart) - widthStart);
                        if (w + sum + ellipsisWidth > avail2) {
                            while (i3 < len && measuredParagraph.getCharWidthAt((i3 + lineStart) - widthStart) == 0.0f) {
                                i3++;
                                TextPaint textPaint = paint;
                            }
                        } else {
                            sum += w;
                            i3--;
                            TextPaint textPaint2 = paint;
                        }
                    }
                    ellipsisStart = 0;
                    ellipsisCount = i3;
                } else if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Start Ellipsis only supported with one line");
                }
            } else if (truncateAt == TextUtils.TruncateAt.END || truncateAt == TextUtils.TruncateAt.MARQUEE || truncateAt == TextUtils.TruncateAt.END_SMALL) {
                float sum2 = 0.0f;
                int i4 = 0;
                while (true) {
                    i = i4;
                    if (i >= len) {
                        break;
                    }
                    float w2 = measuredParagraph.getCharWidthAt((i + lineStart) - widthStart);
                    if (w2 + sum2 + ellipsisWidth > avail2) {
                        break;
                    }
                    sum2 += w2;
                    i4 = i + 1;
                }
                int ellipsisStart2 = i;
                int ellipsisCount2 = len - i;
                if (!forceEllipsis || ellipsisCount2 != 0 || len <= 0) {
                    ellipsisStart = ellipsisStart2;
                    ellipsisCount = ellipsisCount2;
                } else {
                    ellipsisCount = 1;
                    ellipsisStart = len - 1;
                }
            } else if (this.mMaximumVisibleLineCount == 1) {
                float rsum = 0.0f;
                int left = 0;
                int right2 = len;
                float ravail = (avail2 - ellipsisWidth) / 2.0f;
                while (true) {
                    if (right2 <= 0) {
                        lsum = 0.0f;
                        right = right2;
                        break;
                    }
                    float w3 = measuredParagraph.getCharWidthAt(((right2 - 1) + lineStart) - widthStart);
                    if (w3 + rsum > ravail) {
                        lsum = 0.0f;
                        right = right2;
                        while (true) {
                            if (right >= len) {
                                break;
                            }
                            int left2 = left;
                            if (measuredParagraph.getCharWidthAt((right + lineStart) - widthStart) != 0.0f) {
                                break;
                            }
                            right++;
                            left = left2;
                        }
                    } else {
                        rsum += w3;
                        right2--;
                        TextUtils.TruncateAt truncateAt2 = where;
                    }
                }
                float lavail = (avail2 - ellipsisWidth) - rsum;
                int left3 = 0;
                while (true) {
                    if (left3 >= right) {
                        break;
                    }
                    float rsum2 = rsum;
                    float w4 = measuredParagraph.getCharWidthAt((left3 + lineStart) - widthStart);
                    if (w4 + lsum > lavail) {
                        break;
                    }
                    lsum += w4;
                    left3++;
                    rsum = rsum2;
                }
                ellipsisStart = left3;
                ellipsisCount = right - left3;
            } else if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Middle Ellipsis only supported with one line");
            }
            this.mEllipsized = true;
            this.mLines[(this.mColumns * i2) + 5] = ellipsisStart;
            this.mLines[(this.mColumns * i2) + 6] = ellipsisCount;
            return;
        }
        this.mLines[(this.mColumns * i2) + 5] = 0;
        this.mLines[(this.mColumns * i2) + 6] = 0;
    }

    private float getTotalInsets(int line) {
        int totalIndent = 0;
        if (this.mLeftIndents != null) {
            totalIndent = this.mLeftIndents[Math.min(line, this.mLeftIndents.length - 1)];
        }
        if (this.mRightIndents != null) {
            totalIndent += this.mRightIndents[Math.min(line, this.mRightIndents.length - 1)];
        }
        return (float) totalIndent;
    }

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

    public int getLineCount() {
        return this.mLineCount;
    }

    public int getLineTop(int line) {
        return this.mLines[(this.mColumns * line) + 1];
    }

    public int getLineExtra(int line) {
        return this.mLines[(this.mColumns * line) + 3];
    }

    public int getLineDescent(int line) {
        return this.mLines[(this.mColumns * line) + 2];
    }

    public int getLineStart(int line) {
        return this.mLines[(this.mColumns * line) + 0] & 536870911;
    }

    public int getParagraphDirection(int line) {
        return this.mLines[(this.mColumns * line) + 0] >> 30;
    }

    public boolean getLineContainsTab(int line) {
        return (this.mLines[(this.mColumns * line) + 0] & 536870912) != 0;
    }

    public final Layout.Directions getLineDirections(int line) {
        if (line <= getLineCount()) {
            return this.mLineDirections[line];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getTopPadding() {
        return this.mTopPadding;
    }

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

    public int getStartHyphenEdit(int lineNumber) {
        return unpackStartHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

    public int getEndHyphenEdit(int lineNumber) {
        return unpackEndHyphenEdit(this.mLines[(this.mColumns * lineNumber) + 4] & 255);
    }

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

    public int getEllipsisCount(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 6];
    }

    public int getEllipsisStart(int line) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * line) + 5];
    }

    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public int getHeight(boolean cap) {
        if (cap && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "maxLineHeight should not be -1.  maxLines:" + this.mMaximumVisibleLineCount + " lineCount:" + this.mLineCount);
        }
        return (!cap || this.mLineCount <= this.mMaximumVisibleLineCount || this.mMaxLineHeight == -1) ? super.getHeight() : this.mMaxLineHeight;
    }

    static class LineBreaks {
        private static final int INITIAL_SIZE = 16;
        @UnsupportedAppUsage
        public float[] ascents = new float[16];
        @UnsupportedAppUsage
        public int[] breaks = new int[16];
        @UnsupportedAppUsage
        public float[] descents = new float[16];
        @UnsupportedAppUsage
        public int[] flags = new int[16];
        @UnsupportedAppUsage
        public float[] widths = new float[16];

        LineBreaks() {
        }
    }
}
