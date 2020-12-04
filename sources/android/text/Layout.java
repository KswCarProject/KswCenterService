package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.ParagraphStyle;
import android.text.style.ReplacementSpan;
import android.text.style.TabStopSpan;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class Layout {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final float DEFAULT_LINESPACING_ADDITION = 0.0f;
    public static final float DEFAULT_LINESPACING_MULTIPLIER = 1.0f;
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @UnsupportedAppUsage
    public static final Directions DIRS_ALL_LEFT_TO_RIGHT = new Directions(new int[]{0, RUN_LENGTH_MASK});
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @UnsupportedAppUsage
    public static final Directions DIRS_ALL_RIGHT_TO_LEFT = new Directions(new int[]{0, 134217727});
    public static final int DIR_LEFT_TO_RIGHT = 1;
    @UnsupportedAppUsage
    static final int DIR_REQUEST_DEFAULT_LTR = 2;
    static final int DIR_REQUEST_DEFAULT_RTL = -2;
    static final int DIR_REQUEST_LTR = 1;
    static final int DIR_REQUEST_RTL = -1;
    public static final int DIR_RIGHT_TO_LEFT = -1;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    private static final ParagraphStyle[] NO_PARA_SPANS = ((ParagraphStyle[]) ArrayUtils.emptyArray(ParagraphStyle.class));
    static final int RUN_LENGTH_MASK = 67108863;
    static final int RUN_LEVEL_MASK = 63;
    static final int RUN_LEVEL_SHIFT = 26;
    static final int RUN_RTL_FLAG = 67108864;
    private static final float TAB_INCREMENT = 20.0f;
    public static final int TEXT_SELECTION_LAYOUT_LEFT_TO_RIGHT = 1;
    public static final int TEXT_SELECTION_LAYOUT_RIGHT_TO_LEFT = 0;
    private static final Rect sTempRect = new Rect();
    private Alignment mAlignment;
    private int mJustificationMode;
    private SpanSet<LineBackgroundSpan> mLineBackgroundSpans;
    @UnsupportedAppUsage
    private TextPaint mPaint;
    private float mSpacingAdd;
    private float mSpacingMult;
    private boolean mSpannedText;
    private CharSequence mText;
    private TextDirectionHeuristic mTextDir;
    private int mWidth;
    private TextPaint mWorkPaint;

    public enum Alignment {
        ALIGN_NORMAL,
        ALIGN_OPPOSITE,
        ALIGN_CENTER,
        ALIGN_LEFT,
        ALIGN_RIGHT
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BreakStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HyphenationFrequency {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface JustificationMode {
    }

    @FunctionalInterface
    public interface SelectionRectangleConsumer {
        void accept(float f, float f2, float f3, float f4, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSelectionLayout {
    }

    public abstract int getBottomPadding();

    public abstract int getEllipsisCount(int i);

    public abstract int getEllipsisStart(int i);

    public abstract boolean getLineContainsTab(int i);

    public abstract int getLineCount();

    public abstract int getLineDescent(int i);

    public abstract Directions getLineDirections(int i);

    public abstract int getLineStart(int i);

    public abstract int getLineTop(int i);

    public abstract int getParagraphDirection(int i);

    public abstract int getTopPadding();

    public static float getDesiredWidth(CharSequence source, TextPaint paint) {
        return getDesiredWidth(source, 0, source.length(), paint);
    }

    public static float getDesiredWidth(CharSequence source, int start, int end, TextPaint paint) {
        return getDesiredWidth(source, start, end, paint, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    public static float getDesiredWidth(CharSequence source, int start, int end, TextPaint paint, TextDirectionHeuristic textDir) {
        return getDesiredWidthWithLimit(source, start, end, paint, textDir, Float.MAX_VALUE);
    }

    public static float getDesiredWidthWithLimit(CharSequence source, int start, int end, TextPaint paint, TextDirectionHeuristic textDir, float upperLimit) {
        float need = 0.0f;
        int i = start;
        while (i <= end) {
            int next = TextUtils.indexOf(source, 10, i, end);
            if (next < 0) {
                next = end;
            }
            float w = measurePara(paint, source, i, next, textDir);
            if (w > upperLimit) {
                return upperLimit;
            }
            if (w > need) {
                need = w;
            }
            i = next + 1;
        }
        return need;
    }

    protected Layout(CharSequence text, TextPaint paint, int width, Alignment align, float spacingMult, float spacingAdd) {
        this(text, paint, width, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingMult, spacingAdd);
    }

    protected Layout(CharSequence text, TextPaint paint, int width, Alignment align, TextDirectionHeuristic textDir, float spacingMult, float spacingAdd) {
        this.mWorkPaint = new TextPaint();
        this.mAlignment = Alignment.ALIGN_NORMAL;
        if (width >= 0) {
            if (paint != null) {
                paint.bgColor = 0;
                paint.baselineShift = 0;
            }
            this.mText = text;
            this.mPaint = paint;
            this.mWidth = width;
            this.mAlignment = align;
            this.mSpacingMult = spacingMult;
            this.mSpacingAdd = spacingAdd;
            this.mSpannedText = text instanceof Spanned;
            this.mTextDir = textDir;
            return;
        }
        throw new IllegalArgumentException("Layout: " + width + " < 0");
    }

    /* access modifiers changed from: protected */
    public void setJustificationMode(int justificationMode) {
        this.mJustificationMode = justificationMode;
    }

    /* access modifiers changed from: package-private */
    public void replaceWith(CharSequence text, TextPaint paint, int width, Alignment align, float spacingmult, float spacingadd) {
        if (width >= 0) {
            this.mText = text;
            this.mPaint = paint;
            this.mWidth = width;
            this.mAlignment = align;
            this.mSpacingMult = spacingmult;
            this.mSpacingAdd = spacingadd;
            this.mSpannedText = text instanceof Spanned;
            return;
        }
        throw new IllegalArgumentException("Layout: " + width + " < 0");
    }

    public void draw(Canvas c) {
        draw(c, (Path) null, (Paint) null, 0);
    }

    public void draw(Canvas canvas, Path highlight, Paint highlightPaint, int cursorOffsetVertical) {
        long lineRange = getLineRangeForDraw(canvas);
        int firstLine = TextUtils.unpackRangeStartFromLong(lineRange);
        int lastLine = TextUtils.unpackRangeEndFromLong(lineRange);
        if (lastLine >= 0) {
            drawBackground(canvas, highlight, highlightPaint, cursorOffsetVertical, firstLine, lastLine);
            drawText(canvas, firstLine, lastLine);
        }
    }

    private boolean isJustificationRequired(int lineNum) {
        int lineEnd;
        if (this.mJustificationMode == 0 || (lineEnd = getLineEnd(lineNum)) >= this.mText.length() || this.mText.charAt(lineEnd - 1) == 10) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: type inference failed for: r13v5, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float getJustifyWidth(int r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            android.text.Layout$Alignment r2 = r0.mAlignment
            r3 = 0
            int r4 = r0.mWidth
            int r5 = r17.getParagraphDirection(r18)
            android.text.style.ParagraphStyle[] r6 = NO_PARA_SPANS
            boolean r7 = r0.mSpannedText
            r8 = 1
            if (r7 == 0) goto L_0x00a3
            java.lang.CharSequence r7 = r0.mText
            android.text.Spanned r7 = (android.text.Spanned) r7
            int r9 = r17.getLineStart(r18)
            if (r9 == 0) goto L_0x002d
            java.lang.CharSequence r11 = r0.mText
            int r12 = r9 + -1
            char r11 = r11.charAt(r12)
            r12 = 10
            if (r11 != r12) goto L_0x002b
            goto L_0x002d
        L_0x002b:
            r11 = 0
            goto L_0x002e
        L_0x002d:
            r11 = r8
        L_0x002e:
            if (r11 == 0) goto L_0x005b
            java.lang.CharSequence r12 = r0.mText
            int r12 = r12.length()
            java.lang.Class<android.text.style.ParagraphStyle> r13 = android.text.style.ParagraphStyle.class
            int r12 = r7.nextSpanTransition(r9, r12, r13)
            java.lang.Class<android.text.style.ParagraphStyle> r13 = android.text.style.ParagraphStyle.class
            java.lang.Object[] r13 = getParagraphSpans(r7, r9, r12, r13)
            r6 = r13
            android.text.style.ParagraphStyle[] r6 = (android.text.style.ParagraphStyle[]) r6
            int r13 = r6.length
            int r13 = r13 - r8
        L_0x0047:
            if (r13 < 0) goto L_0x005b
            r14 = r6[r13]
            boolean r14 = r14 instanceof android.text.style.AlignmentSpan
            if (r14 == 0) goto L_0x0058
            r14 = r6[r13]
            android.text.style.AlignmentSpan r14 = (android.text.style.AlignmentSpan) r14
            android.text.Layout$Alignment r2 = r14.getAlignment()
            goto L_0x005b
        L_0x0058:
            int r13 = r13 + -1
            goto L_0x0047
        L_0x005b:
            int r12 = r6.length
            r13 = r11
            r14 = 0
        L_0x005e:
            if (r14 >= r12) goto L_0x0082
            r15 = r6[r14]
            boolean r15 = r15 instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2
            if (r15 == 0) goto L_0x007e
            r15 = r6[r14]
            android.text.style.LeadingMarginSpan$LeadingMarginSpan2 r15 = (android.text.style.LeadingMarginSpan.LeadingMarginSpan2) r15
            int r15 = r15.getLeadingMarginLineCount()
            r10 = r6[r14]
            int r10 = r7.getSpanStart(r10)
            int r10 = r0.getLineForOffset(r10)
            int r8 = r10 + r15
            if (r1 >= r8) goto L_0x007e
            r13 = 1
            goto L_0x0082
        L_0x007e:
            int r14 = r14 + 1
            r8 = 1
            goto L_0x005e
        L_0x0082:
            r16 = 0
        L_0x0084:
            r8 = r16
            if (r8 >= r12) goto L_0x00a3
            r10 = r6[r8]
            boolean r10 = r10 instanceof android.text.style.LeadingMarginSpan
            if (r10 == 0) goto L_0x00a0
            r10 = r6[r8]
            android.text.style.LeadingMarginSpan r10 = (android.text.style.LeadingMarginSpan) r10
            r14 = -1
            if (r5 != r14) goto L_0x009b
            int r14 = r10.getLeadingMargin(r13)
            int r4 = r4 - r14
            goto L_0x00a0
        L_0x009b:
            int r14 = r10.getLeadingMargin(r13)
            int r3 = r3 + r14
        L_0x00a0:
            int r16 = r8 + 1
            goto L_0x0084
        L_0x00a3:
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_LEFT
            if (r2 != r7) goto L_0x00b0
            r7 = 1
            if (r5 != r7) goto L_0x00ad
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_NORMAL
            goto L_0x00af
        L_0x00ad:
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_OPPOSITE
        L_0x00af:
            goto L_0x00be
        L_0x00b0:
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_RIGHT
            if (r2 != r7) goto L_0x00bd
            r7 = 1
            if (r5 != r7) goto L_0x00ba
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            goto L_0x00bc
        L_0x00ba:
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_NORMAL
        L_0x00bc:
            goto L_0x00be
        L_0x00bd:
            r7 = r2
        L_0x00be:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_NORMAL
            if (r7 != r8) goto L_0x00d4
            r8 = 1
            if (r5 != r8) goto L_0x00cc
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_LEFT
            int r8 = r0.getIndentAdjust(r1, r8)
            goto L_0x00f0
        L_0x00cc:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r8 = r0.getIndentAdjust(r1, r8)
            int r8 = -r8
            goto L_0x00f0
        L_0x00d4:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            if (r7 != r8) goto L_0x00ea
            r8 = 1
            if (r5 != r8) goto L_0x00e3
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r8 = r0.getIndentAdjust(r1, r8)
            int r8 = -r8
            goto L_0x00f0
        L_0x00e3:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_LEFT
            int r8 = r0.getIndentAdjust(r1, r8)
            goto L_0x00f0
        L_0x00ea:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_CENTER
            int r8 = r0.getIndentAdjust(r1, r8)
        L_0x00f0:
            int r9 = r4 - r3
            int r9 = r9 - r8
            float r9 = (float) r9
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getJustifyWidth(int):float");
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x010f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01f0 A[EDGE_INSN: B:109:0x01f0->B:48:0x01f0 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x011d  */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawText(android.graphics.Canvas r46, int r47, int r48) {
        /*
            r45 = this;
            r15 = r45
            r14 = r47
            int r0 = r15.getLineTop(r14)
            int r1 = r15.getLineStart(r14)
            android.text.style.ParagraphStyle[] r2 = NO_PARA_SPANS
            r3 = 0
            android.text.TextPaint r13 = r15.mWorkPaint
            android.text.TextPaint r4 = r15.mPaint
            r13.set(r4)
            java.lang.CharSequence r12 = r15.mText
            android.text.Layout$Alignment r4 = r15.mAlignment
            r5 = 0
            r6 = 0
            android.text.TextLine r11 = android.text.TextLine.obtain()
            r10 = r5
            r7 = r6
            r6 = r3
            r3 = r2
            r2 = r0
            r0 = r14
        L_0x0026:
            r9 = r0
            r8 = r48
            if (r9 > r8) goto L_0x0351
            r0 = r1
            int r5 = r9 + 1
            int r1 = r15.getLineStart(r5)
            boolean r20 = r15.isJustificationRequired(r9)
            int r21 = r15.getLineVisibleEnd(r9, r0, r1)
            int r5 = r15.getStartHyphenEdit(r9)
            r13.setStartHyphenEdit(r5)
            int r5 = r15.getEndHyphenEdit(r9)
            r13.setEndHyphenEdit(r5)
            r5 = r2
            r22 = r1
            int r1 = r9 + 1
            int r23 = r15.getLineTop(r1)
            r24 = r23
            int r1 = r15.getLineDescent(r9)
            int r2 = r23 - r1
            int r1 = r15.getParagraphDirection(r9)
            r16 = 0
            r25 = r2
            int r2 = r15.mWidth
            r26 = r2
            boolean r2 = r15.mSpannedText
            r27 = r5
            if (r2 == 0) goto L_0x0207
            r2 = r12
            android.text.Spanned r2 = (android.text.Spanned) r2
            int r5 = r12.length()
            if (r0 == 0) goto L_0x0085
            r30 = r3
            int r3 = r0 + -1
            char r3 = r12.charAt(r3)
            r31 = r4
            r4 = 10
            if (r3 != r4) goto L_0x0083
            goto L_0x0089
        L_0x0083:
            r3 = 0
            goto L_0x008a
        L_0x0085:
            r30 = r3
            r31 = r4
        L_0x0089:
            r3 = 1
        L_0x008a:
            r19 = r3
            if (r0 < r6) goto L_0x00d0
            if (r9 == r14) goto L_0x0098
            if (r19 == 0) goto L_0x0093
            goto L_0x0098
        L_0x0093:
            r32 = r0
            r17 = 1
            goto L_0x00d4
        L_0x0098:
            java.lang.Class<android.text.style.ParagraphStyle> r3 = android.text.style.ParagraphStyle.class
            int r6 = r2.nextSpanTransition(r0, r5, r3)
            java.lang.Class<android.text.style.ParagraphStyle> r3 = android.text.style.ParagraphStyle.class
            java.lang.Object[] r3 = getParagraphSpans(r2, r0, r6, r3)
            android.text.style.ParagraphStyle[] r3 = (android.text.style.ParagraphStyle[]) r3
            android.text.Layout$Alignment r4 = r15.mAlignment
            r32 = r0
            int r0 = r3.length
            r17 = 1
            int r0 = r0 + -1
        L_0x00af:
            if (r0 < 0) goto L_0x00c7
            r33 = r4
            r4 = r3[r0]
            boolean r4 = r4 instanceof android.text.style.AlignmentSpan
            if (r4 == 0) goto L_0x00c2
            r4 = r3[r0]
            android.text.style.AlignmentSpan r4 = (android.text.style.AlignmentSpan) r4
            android.text.Layout$Alignment r4 = r4.getAlignment()
            goto L_0x00c9
        L_0x00c2:
            int r0 = r0 + -1
            r4 = r33
            goto L_0x00af
        L_0x00c7:
            r33 = r4
        L_0x00c9:
            r0 = 0
            r29 = r0
            r7 = r3
            r31 = r4
            goto L_0x00d8
        L_0x00d0:
            r32 = r0
            r17 = 1
        L_0x00d4:
            r29 = r7
            r7 = r30
        L_0x00d8:
            r30 = r6
            int r6 = r7.length
            r0 = r19
            r3 = 0
        L_0x00de:
            if (r3 >= r6) goto L_0x010f
            r4 = r7[r3]
            boolean r4 = r4 instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2
            if (r4 == 0) goto L_0x0104
            r4 = r7[r3]
            android.text.style.LeadingMarginSpan$LeadingMarginSpan2 r4 = (android.text.style.LeadingMarginSpan.LeadingMarginSpan2) r4
            int r4 = r4.getLeadingMarginLineCount()
            r34 = r0
            r0 = r7[r3]
            int r0 = r2.getSpanStart(r0)
            int r0 = r15.getLineForOffset(r0)
            r35 = r2
            int r2 = r0 + r4
            if (r9 >= r2) goto L_0x0108
            r2 = 1
            r4 = r2
            goto L_0x0115
        L_0x0104:
            r34 = r0
            r35 = r2
        L_0x0108:
            int r3 = r3 + 1
            r0 = r34
            r2 = r35
            goto L_0x00de
        L_0x010f:
            r34 = r0
            r35 = r2
            r4 = r34
        L_0x0115:
            r33 = r26
            r0 = 0
            r26 = r16
        L_0x011a:
            r3 = r0
            if (r3 >= r6) goto L_0x01f0
            r0 = r7[r3]
            boolean r0 = r0 instanceof android.text.style.LeadingMarginSpan
            if (r0 == 0) goto L_0x01ad
            r0 = r7[r3]
            r2 = r0
            android.text.style.LeadingMarginSpan r2 = (android.text.style.LeadingMarginSpan) r2
            r0 = -1
            if (r1 != r0) goto L_0x016b
            r0 = r2
            r36 = r1
            r1 = r46
            r15 = r2
            r37 = r25
            r25 = r35
            r2 = r13
            r34 = r3
            r3 = r33
            r14 = r4
            r4 = r36
            r35 = r6
            r6 = r37
            r38 = r7
            r7 = r23
            r8 = r12
            r39 = r9
            r9 = r32
            r40 = r10
            r10 = r21
            r41 = r11
            r11 = r19
            r42 = r12
            r12 = r45
            r44 = r27
            r27 = r5
            r5 = r44
            r0.drawLeadingMargin(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            int r0 = r15.getLeadingMargin(r14)
            int r33 = r33 - r0
            r0 = r13
            r2 = r14
            r3 = r45
            goto L_0x01ca
        L_0x016b:
            r36 = r1
            r15 = r2
            r34 = r3
            r14 = r4
            r38 = r7
            r39 = r9
            r40 = r10
            r41 = r11
            r42 = r12
            r37 = r25
            r25 = r35
            r35 = r6
            r44 = r27
            r27 = r5
            r5 = r44
            r6 = r15
            r7 = r46
            r8 = r13
            r9 = r26
            r10 = r36
            r11 = r5
            r12 = r37
            r0 = r13
            r13 = r23
            r2 = r14
            r14 = r42
            r1 = r15
            r3 = r45
            r15 = r32
            r16 = r21
            r17 = r19
            r18 = r45
            r6.drawLeadingMargin(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            int r4 = r1.getLeadingMargin(r2)
            int r26 = r26 + r4
            goto L_0x01ca
        L_0x01ad:
            r36 = r1
            r34 = r3
            r2 = r4
            r38 = r7
            r39 = r9
            r40 = r10
            r41 = r11
            r42 = r12
            r0 = r13
            r3 = r15
            r37 = r25
            r25 = r35
            r35 = r6
            r44 = r27
            r27 = r5
            r5 = r44
        L_0x01ca:
            int r1 = r34 + 1
            r14 = r47
            r8 = r48
            r13 = r0
            r0 = r1
            r4 = r2
            r15 = r3
            r6 = r35
            r1 = r36
            r7 = r38
            r9 = r39
            r10 = r40
            r11 = r41
            r12 = r42
            r17 = 1
            r35 = r25
            r25 = r37
            r44 = r27
            r27 = r5
            r5 = r44
            goto L_0x011a
        L_0x01f0:
            r36 = r1
            r38 = r7
            r39 = r9
            r40 = r10
            r41 = r11
            r42 = r12
            r0 = r13
            r3 = r15
            r37 = r25
            r5 = r27
            r4 = r31
            r1 = r38
            goto L_0x0227
        L_0x0207:
            r32 = r0
            r36 = r1
            r30 = r3
            r31 = r4
            r39 = r9
            r40 = r10
            r41 = r11
            r42 = r12
            r0 = r13
            r3 = r15
            r37 = r25
            r5 = r27
            r29 = r7
            r33 = r26
            r1 = r30
            r30 = r6
            r26 = r16
        L_0x0227:
            r2 = r39
            boolean r25 = r3.getLineContainsTab(r2)
            if (r25 == 0) goto L_0x0246
            if (r29 != 0) goto L_0x0246
            r6 = 1101004800(0x41a00000, float:20.0)
            r8 = r40
            if (r8 != 0) goto L_0x023f
            android.text.Layout$TabStops r7 = new android.text.Layout$TabStops
            r7.<init>(r6, r1)
            r10 = r7
            r8 = r10
            goto L_0x0242
        L_0x023f:
            r8.reset(r6, r1)
        L_0x0242:
            r6 = 1
            r29 = r6
            goto L_0x0248
        L_0x0246:
            r8 = r40
        L_0x0248:
            r15 = r8
            r6 = r4
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_LEFT
            if (r6 != r7) goto L_0x025a
            r14 = r36
            r7 = 1
            if (r14 != r7) goto L_0x0256
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_NORMAL
            goto L_0x0258
        L_0x0256:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_OPPOSITE
        L_0x0258:
            r6 = r8
            goto L_0x0269
        L_0x025a:
            r14 = r36
            r7 = 1
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_RIGHT
            if (r6 != r8) goto L_0x0269
            if (r14 != r7) goto L_0x0266
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            goto L_0x0268
        L_0x0266:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_NORMAL
        L_0x0268:
            r6 = r8
        L_0x0269:
            r13 = r6
            android.text.Layout$Alignment r6 = android.text.Layout.Alignment.ALIGN_NORMAL
            if (r13 != r6) goto L_0x0285
            if (r14 != r7) goto L_0x027b
            android.text.Layout$Alignment r6 = android.text.Layout.Alignment.ALIGN_LEFT
            int r6 = r3.getIndentAdjust(r2, r6)
            int r7 = r26 + r6
        L_0x0278:
            r27 = r6
            goto L_0x02b9
        L_0x027b:
            android.text.Layout$Alignment r6 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r6 = r3.getIndentAdjust(r2, r6)
            int r6 = -r6
            int r7 = r33 - r6
            goto L_0x0278
        L_0x0285:
            r6 = 0
            float r6 = r3.getLineExtent(r2, r15, r6)
            int r6 = (int) r6
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            if (r13 != r8) goto L_0x02a9
            if (r14 != r7) goto L_0x029f
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_RIGHT
            int r7 = r3.getIndentAdjust(r2, r7)
            int r7 = -r7
            int r8 = r33 - r6
            int r8 = r8 - r7
        L_0x029b:
            r27 = r7
            r7 = r8
            goto L_0x02b9
        L_0x029f:
            android.text.Layout$Alignment r7 = android.text.Layout.Alignment.ALIGN_LEFT
            int r7 = r3.getIndentAdjust(r2, r7)
            int r8 = r26 - r6
            int r8 = r8 + r7
            goto L_0x029b
        L_0x02a9:
            android.text.Layout$Alignment r8 = android.text.Layout.Alignment.ALIGN_CENTER
            int r8 = r3.getIndentAdjust(r2, r8)
            r6 = r6 & -2
            int r9 = r33 + r26
            int r9 = r9 - r6
            int r7 = r9 >> 1
            int r7 = r7 + r8
            r27 = r8
        L_0x02b9:
            r12 = r7
            android.text.Layout$Directions r11 = r3.getLineDirections(r2)
            android.text.Layout$Directions r6 = DIRS_ALL_LEFT_TO_RIGHT
            if (r11 != r6) goto L_0x02ea
            boolean r6 = r3.mSpannedText
            if (r6 != 0) goto L_0x02ea
            if (r25 != 0) goto L_0x02ea
            if (r20 != 0) goto L_0x02ea
            float r10 = (float) r12
            r9 = r37
            float r8 = (float) r9
            r6 = r46
            r7 = r42
            r16 = r8
            r8 = r32
            r28 = r9
            r9 = r21
            r31 = r11
            r11 = r16
            r43 = r1
            r1 = r12
            r12 = r0
            r6.drawText((java.lang.CharSequence) r7, (int) r8, (int) r9, (float) r10, (float) r11, (android.graphics.Paint) r12)
            r36 = r15
            r9 = r41
            goto L_0x0339
        L_0x02ea:
            r43 = r1
            r31 = r11
            r1 = r12
            r28 = r37
            int r16 = r3.getEllipsisStart(r2)
            int r6 = r3.getEllipsisStart(r2)
            int r7 = r3.getEllipsisCount(r2)
            int r17 = r6 + r7
            r6 = r41
            r7 = r0
            r8 = r42
            r9 = r32
            r10 = r21
            r11 = r14
            r12 = r31
            r34 = r13
            r13 = r25
            r35 = r14
            r14 = r15
            r36 = r15
            r15 = r16
            r16 = r17
            r6.set(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            if (r20 == 0) goto L_0x0328
            int r6 = r33 - r26
            int r6 = r6 - r27
            float r6 = (float) r6
            r9 = r41
            r9.justify(r6)
            goto L_0x032a
        L_0x0328:
            r9 = r41
        L_0x032a:
            float r6 = (float) r1
            r14 = r9
            r15 = r46
            r16 = r6
            r17 = r5
            r18 = r28
            r19 = r23
            r14.draw(r15, r16, r17, r18, r19)
        L_0x0339:
            int r1 = r2 + 1
            r14 = r47
            r13 = r0
            r0 = r1
            r15 = r3
            r11 = r9
            r1 = r22
            r2 = r24
            r7 = r29
            r6 = r30
            r10 = r36
            r12 = r42
            r3 = r43
            goto L_0x0026
        L_0x0351:
            r30 = r3
            r31 = r4
            r8 = r10
            r9 = r11
            r42 = r12
            r0 = r13
            r3 = r15
            android.text.TextLine.recycle(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.drawText(android.graphics.Canvas, int, int):void");
    }

    @UnsupportedAppUsage
    public void drawBackground(Canvas canvas, Path highlight, Paint highlightPaint, int cursorOffsetVertical, int firstLine, int lastLine) {
        int spanEnd;
        int i;
        ParagraphStyle[] spans;
        int spansLength;
        int start;
        Canvas canvas2 = canvas;
        int i2 = cursorOffsetVertical;
        int i3 = firstLine;
        if (this.mSpannedText) {
            if (this.mLineBackgroundSpans == null) {
                this.mLineBackgroundSpans = new SpanSet<>(LineBackgroundSpan.class);
            }
            Spanned buffer = (Spanned) this.mText;
            int textLength = buffer.length();
            this.mLineBackgroundSpans.init(buffer, 0, textLength);
            if (this.mLineBackgroundSpans.numberOfSpans > 0) {
                int previousLineBottom = getLineTop(i3);
                int previousLineEnd = getLineStart(i3);
                ParagraphStyle[] spans2 = NO_PARA_SPANS;
                TextPaint paint = this.mPaint;
                int width = this.mWidth;
                int spanEnd2 = 0;
                int spanEnd3 = 0;
                int spansLength2 = previousLineEnd;
                int previousLineEnd2 = previousLineBottom;
                int i4 = i3;
                while (i4 <= lastLine) {
                    int start2 = spansLength2;
                    int end = getLineStart(i4 + 1);
                    int previousLineEnd3 = end;
                    int ltop = previousLineEnd2;
                    int lbottom = getLineTop(i4 + 1);
                    int previousLineBottom2 = lbottom;
                    int lbaseline = lbottom - getLineDescent(i4);
                    if (end >= spanEnd2) {
                        start = start2;
                        int spanEnd4 = this.mLineBackgroundSpans.getNextTransition(start, textLength);
                        int spansLength3 = 0;
                        if (start != end || start == 0) {
                            ParagraphStyle[] spans3 = spans2;
                            int j = 0;
                            while (true) {
                                i = i4;
                                if (j >= this.mLineBackgroundSpans.numberOfSpans) {
                                    break;
                                }
                                if (this.mLineBackgroundSpans.spanStarts[j] < end && this.mLineBackgroundSpans.spanEnds[j] > start) {
                                    spansLength3++;
                                    spans3 = (ParagraphStyle[]) GrowingArrayUtils.append((T[]) spans3, spansLength3, ((LineBackgroundSpan[]) this.mLineBackgroundSpans.spans)[j]);
                                }
                                j++;
                                i4 = i;
                            }
                            spanEnd = spanEnd4;
                            spans = spans3;
                        } else {
                            i = i4;
                            spanEnd = spanEnd4;
                            spans = spans2;
                        }
                        spansLength = spansLength3;
                    } else {
                        i = i4;
                        start = start2;
                        spans = spans2;
                        spanEnd = spanEnd2;
                        spansLength = spanEnd3;
                    }
                    int n = 0;
                    while (true) {
                        int n2 = n;
                        if (n2 >= spansLength) {
                            break;
                        }
                        int start3 = start;
                        int end2 = end;
                        ((LineBackgroundSpan) spans[n2]).drawBackground(canvas, paint, 0, width, ltop, lbaseline, lbottom, buffer, start3, end2, i);
                        n = n2 + 1;
                        end = end2;
                        start = start3;
                        spansLength = spansLength;
                        width = width;
                        paint = paint;
                        textLength = textLength;
                        buffer = buffer;
                    }
                    int spansLength4 = spansLength;
                    int i5 = width;
                    TextPaint textPaint = paint;
                    int i6 = textLength;
                    Spanned spanned = buffer;
                    i4 = i + 1;
                    spans2 = spans;
                    spansLength2 = previousLineEnd3;
                    previousLineEnd2 = previousLineBottom2;
                    spanEnd2 = spanEnd;
                    spanEnd3 = spansLength4;
                }
            }
            Spanned spanned2 = buffer;
            this.mLineBackgroundSpans.recycle();
        }
        if (highlight != null) {
            if (i2 != 0) {
                canvas2.translate(0.0f, (float) i2);
            }
            canvas.drawPath(highlight, highlightPaint);
            if (i2 != 0) {
                canvas2.translate(0.0f, (float) (-i2));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        r0 = java.lang.Math.max(r1, 0);
        r5 = java.lang.Math.min(getLineTop(getLineCount()), r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        if (r0 < r5) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        return android.text.TextUtils.packRangeInLong(0, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        return android.text.TextUtils.packRangeInLong(getLineForVertical(r0), getLineForVertical(r5));
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getLineRangeForDraw(android.graphics.Canvas r7) {
        /*
            r6 = this;
            android.graphics.Rect r0 = sTempRect
            monitor-enter(r0)
            android.graphics.Rect r1 = sTempRect     // Catch:{ all -> 0x0040 }
            boolean r1 = r7.getClipBounds(r1)     // Catch:{ all -> 0x0040 }
            r2 = -1
            r3 = 0
            if (r1 != 0) goto L_0x0013
            long r1 = android.text.TextUtils.packRangeInLong(r3, r2)     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return r1
        L_0x0013:
            android.graphics.Rect r1 = sTempRect     // Catch:{ all -> 0x0040 }
            int r1 = r1.top     // Catch:{ all -> 0x0040 }
            android.graphics.Rect r4 = sTempRect     // Catch:{ all -> 0x0040 }
            int r4 = r4.bottom     // Catch:{ all -> 0x0040 }
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            int r0 = java.lang.Math.max(r1, r3)
            int r5 = r6.getLineCount()
            int r5 = r6.getLineTop(r5)
            int r5 = java.lang.Math.min(r5, r4)
            if (r0 < r5) goto L_0x0033
            long r2 = android.text.TextUtils.packRangeInLong(r3, r2)
            return r2
        L_0x0033:
            int r2 = r6.getLineForVertical(r0)
            int r3 = r6.getLineForVertical(r5)
            long r2 = android.text.TextUtils.packRangeInLong(r2, r3)
            return r2
        L_0x0040:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getLineRangeForDraw(android.graphics.Canvas):long");
    }

    private int getLineStartPos(int line, int left, int right) {
        int x;
        Alignment align = getParagraphAlignment(line);
        int dir = getParagraphDirection(line);
        if (align == Alignment.ALIGN_LEFT) {
            align = dir == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
        } else if (align == Alignment.ALIGN_RIGHT) {
            align = dir == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
        }
        if (align != Alignment.ALIGN_NORMAL) {
            TabStops tabStops = null;
            if (this.mSpannedText && getLineContainsTab(line)) {
                Spanned spanned = (Spanned) this.mText;
                int start = getLineStart(line);
                TabStopSpan[] tabSpans = (TabStopSpan[]) getParagraphSpans(spanned, start, spanned.nextSpanTransition(start, spanned.length(), TabStopSpan.class), TabStopSpan.class);
                if (tabSpans.length > 0) {
                    tabStops = new TabStops(TAB_INCREMENT, tabSpans);
                }
            }
            int max = (int) getLineExtent(line, tabStops, false);
            if (align == Alignment.ALIGN_OPPOSITE) {
                if (dir == 1) {
                    x = (right - max) + getIndentAdjust(line, Alignment.ALIGN_RIGHT);
                } else {
                    x = (left - max) + getIndentAdjust(line, Alignment.ALIGN_LEFT);
                }
                return x;
            }
            return ((left + right) - (max & -2)) >> (getIndentAdjust(line, Alignment.ALIGN_CENTER) + 1);
        } else if (dir == 1) {
            return getIndentAdjust(line, Alignment.ALIGN_LEFT) + left;
        } else {
            return getIndentAdjust(line, Alignment.ALIGN_RIGHT) + right;
        }
    }

    public final CharSequence getText() {
        return this.mText;
    }

    public final TextPaint getPaint() {
        return this.mPaint;
    }

    public final int getWidth() {
        return this.mWidth;
    }

    public int getEllipsizedWidth() {
        return this.mWidth;
    }

    public final void increaseWidthTo(int wid) {
        if (wid >= this.mWidth) {
            this.mWidth = wid;
            return;
        }
        throw new RuntimeException("attempted to reduce Layout width");
    }

    public int getHeight() {
        return getLineTop(getLineCount());
    }

    public int getHeight(boolean cap) {
        return getHeight();
    }

    public final Alignment getAlignment() {
        return this.mAlignment;
    }

    public final float getSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public final float getSpacingAdd() {
        return this.mSpacingAdd;
    }

    public final TextDirectionHeuristic getTextDirectionHeuristic() {
        return this.mTextDir;
    }

    public int getLineBounds(int line, Rect bounds) {
        if (bounds != null) {
            bounds.left = 0;
            bounds.top = getLineTop(line);
            bounds.right = this.mWidth;
            bounds.bottom = getLineTop(line + 1);
        }
        return getLineBaseline(line);
    }

    public int getStartHyphenEdit(int line) {
        return 0;
    }

    public int getEndHyphenEdit(int line) {
        return 0;
    }

    public int getIndentAdjust(int line, Alignment alignment) {
        return 0;
    }

    @UnsupportedAppUsage
    public boolean isLevelBoundary(int offset) {
        int line = getLineForOffset(offset);
        Directions dirs = getLineDirections(line);
        if (dirs == DIRS_ALL_LEFT_TO_RIGHT || dirs == DIRS_ALL_RIGHT_TO_LEFT) {
            return false;
        }
        int[] runs = dirs.mDirections;
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        if (offset == lineStart || offset == lineEnd) {
            if (((runs[(offset == lineStart ? 0 : runs.length - 2) + 1] >>> 26) & 63) != (getParagraphDirection(line) == 1 ? 0 : 1)) {
                return true;
            }
            return false;
        }
        int offset2 = offset - lineStart;
        for (int i = 0; i < runs.length; i += 2) {
            if (offset2 == runs[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isRtlCharAt(int offset) {
        int line = getLineForOffset(offset);
        Directions dirs = getLineDirections(line);
        if (dirs == DIRS_ALL_LEFT_TO_RIGHT) {
            return false;
        }
        if (dirs == DIRS_ALL_RIGHT_TO_LEFT) {
            return true;
        }
        int[] runs = dirs.mDirections;
        int lineStart = getLineStart(line);
        int i = 0;
        while (i < runs.length) {
            int start = runs[i] + lineStart;
            int limit = (runs[i + 1] & RUN_LENGTH_MASK) + start;
            if (offset < start || offset >= limit) {
                i += 2;
            } else if (((runs[i + 1] >>> 26) & 63 & 1) != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public long getRunRange(int offset) {
        int line = getLineForOffset(offset);
        Directions dirs = getLineDirections(line);
        if (dirs == DIRS_ALL_LEFT_TO_RIGHT || dirs == DIRS_ALL_RIGHT_TO_LEFT) {
            return TextUtils.packRangeInLong(0, getLineEnd(line));
        }
        int[] runs = dirs.mDirections;
        int lineStart = getLineStart(line);
        for (int i = 0; i < runs.length; i += 2) {
            int start = runs[i] + lineStart;
            int limit = (runs[i + 1] & RUN_LENGTH_MASK) + start;
            if (offset >= start && offset < limit) {
                return TextUtils.packRangeInLong(start, limit);
            }
        }
        return TextUtils.packRangeInLong(0, getLineEnd(line));
    }

    @VisibleForTesting
    public boolean primaryIsTrailingPrevious(int offset) {
        int line = getLineForOffset(offset);
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        int[] runs = getLineDirections(line).mDirections;
        int levelAt = -1;
        int i = 0;
        while (true) {
            if (i >= runs.length) {
                break;
            }
            int start = runs[i] + lineStart;
            int limit = (runs[i + 1] & RUN_LENGTH_MASK) + start;
            if (limit > lineEnd) {
                limit = lineEnd;
            }
            if (offset < start || offset >= limit) {
                i += 2;
            } else if (offset > start) {
                return false;
            } else {
                levelAt = (runs[i + 1] >>> 26) & 63;
            }
        }
        if (levelAt == -1) {
            levelAt = getParagraphDirection(line) == 1 ? 0 : 1;
        }
        int levelBefore = -1;
        if (offset != lineStart) {
            int offset2 = offset - 1;
            int i2 = 0;
            while (true) {
                if (i2 < runs.length) {
                    int start2 = runs[i2] + lineStart;
                    int limit2 = (runs[i2 + 1] & RUN_LENGTH_MASK) + start2;
                    if (limit2 > lineEnd) {
                        limit2 = lineEnd;
                    }
                    if (offset2 >= start2 && offset2 < limit2) {
                        levelBefore = (runs[i2 + 1] >>> 26) & 63;
                        break;
                    }
                    i2 += 2;
                } else {
                    break;
                }
            }
        } else {
            levelBefore = getParagraphDirection(line) == 1 ? 0 : 1;
        }
        if (levelBefore < levelAt) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean[] primaryIsTrailingPreviousAllLineOffsets(int line) {
        byte b;
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        int[] runs = getLineDirections(line).mDirections;
        boolean[] trailing = new boolean[((lineEnd - lineStart) + 1)];
        byte[] level = new byte[((lineEnd - lineStart) + 1)];
        for (int i = 0; i < runs.length; i += 2) {
            int start = runs[i] + lineStart;
            int limit = (runs[i + 1] & RUN_LENGTH_MASK) + start;
            if (limit > lineEnd) {
                limit = lineEnd;
            }
            if (limit != start) {
                level[(limit - lineStart) - 1] = (byte) ((runs[i + 1] >>> 26) & 63);
            }
        }
        for (int i2 = 0; i2 < runs.length; i2 += 2) {
            int start2 = runs[i2] + lineStart;
            byte currentLevel = (byte) ((runs[i2 + 1] >>> 26) & 63);
            int i3 = start2 - lineStart;
            if (start2 == lineStart) {
                b = getParagraphDirection(line) == 1 ? (byte) 0 : 1;
            } else {
                b = level[(start2 - lineStart) - 1];
            }
            trailing[i3] = currentLevel > b;
        }
        return trailing;
    }

    public float getPrimaryHorizontal(int offset) {
        return getPrimaryHorizontal(offset, false);
    }

    @UnsupportedAppUsage
    public float getPrimaryHorizontal(int offset, boolean clamped) {
        return getHorizontal(offset, primaryIsTrailingPrevious(offset), clamped);
    }

    public float getSecondaryHorizontal(int offset) {
        return getSecondaryHorizontal(offset, false);
    }

    @UnsupportedAppUsage
    public float getSecondaryHorizontal(int offset, boolean clamped) {
        return getHorizontal(offset, !primaryIsTrailingPrevious(offset), clamped);
    }

    /* access modifiers changed from: private */
    public float getHorizontal(int offset, boolean primary) {
        return primary ? getPrimaryHorizontal(offset) : getSecondaryHorizontal(offset);
    }

    private float getHorizontal(int offset, boolean trailing, boolean clamped) {
        return getHorizontal(offset, trailing, getLineForOffset(offset), clamped);
    }

    private float getHorizontal(int offset, boolean trailing, int line, boolean clamped) {
        int i = line;
        int start = getLineStart(i);
        int end = getLineEnd(i);
        int dir = getParagraphDirection(i);
        boolean hasTab = getLineContainsTab(i);
        Directions directions = getLineDirections(i);
        TabStops tabStops = null;
        if (hasTab && (this.mText instanceof Spanned)) {
            TabStopSpan[] tabs = (TabStopSpan[]) getParagraphSpans((Spanned) this.mText, start, end, TabStopSpan.class);
            if (tabs.length > 0) {
                tabStops = new TabStops(TAB_INCREMENT, tabs);
            }
        }
        TextLine tl = TextLine.obtain();
        TextPaint textPaint = this.mPaint;
        CharSequence charSequence = this.mText;
        int i2 = end;
        TextLine tl2 = tl;
        tl.set(textPaint, charSequence, start, end, dir, directions, hasTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i));
        float wid = tl2.measure(offset - start, trailing, (Paint.FontMetricsInt) null);
        TextLine.recycle(tl2);
        if (clamped && wid > ((float) this.mWidth)) {
            wid = (float) this.mWidth;
        }
        return ((float) getLineStartPos(i, getParagraphLeft(i), getParagraphRight(i))) + wid;
    }

    /* access modifiers changed from: private */
    public float[] getLineHorizontals(int line, boolean clamped, boolean primary) {
        int start = getLineStart(line);
        int end = getLineEnd(line);
        int dir = getParagraphDirection(line);
        boolean hasTab = getLineContainsTab(line);
        Directions directions = getLineDirections(line);
        TabStops tabStops = null;
        if (hasTab && (this.mText instanceof Spanned)) {
            TabStopSpan[] tabs = (TabStopSpan[]) getParagraphSpans((Spanned) this.mText, start, end, TabStopSpan.class);
            if (tabs.length > 0) {
                tabStops = new TabStops(TAB_INCREMENT, tabs);
            }
        }
        TextLine tl = TextLine.obtain();
        int i = dir;
        TextLine tl2 = tl;
        tl.set(this.mPaint, this.mText, start, end, dir, directions, hasTab, tabStops, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        boolean[] trailings = primaryIsTrailingPreviousAllLineOffsets(line);
        if (!primary) {
            for (int offset = 0; offset < trailings.length; offset++) {
                trailings[offset] = !trailings[offset];
            }
        }
        float[] wid = tl2.measureAllOffsets(trailings, (Paint.FontMetricsInt) null);
        TextLine.recycle(tl2);
        if (clamped) {
            for (int offset2 = 0; offset2 < wid.length; offset2++) {
                if (wid[offset2] > ((float) this.mWidth)) {
                    wid[offset2] = (float) this.mWidth;
                }
            }
        }
        int lineStartPos = getLineStartPos(line, getParagraphLeft(line), getParagraphRight(line));
        float[] horizontal = new float[((end - start) + 1)];
        for (int offset3 = 0; offset3 < horizontal.length; offset3++) {
            horizontal[offset3] = ((float) lineStartPos) + wid[offset3];
        }
        return horizontal;
    }

    public float getLineLeft(int line) {
        Alignment resultAlign;
        int dir = getParagraphDirection(line);
        Alignment align = getParagraphAlignment(line);
        if (align == null) {
            align = Alignment.ALIGN_CENTER;
        }
        switch (align) {
            case ALIGN_NORMAL:
                if (dir != -1) {
                    resultAlign = Alignment.ALIGN_LEFT;
                    break;
                } else {
                    resultAlign = Alignment.ALIGN_RIGHT;
                    break;
                }
            case ALIGN_OPPOSITE:
                if (dir != -1) {
                    resultAlign = Alignment.ALIGN_RIGHT;
                    break;
                } else {
                    resultAlign = Alignment.ALIGN_LEFT;
                    break;
                }
            case ALIGN_CENTER:
                resultAlign = Alignment.ALIGN_CENTER;
                break;
            case ALIGN_RIGHT:
                resultAlign = Alignment.ALIGN_RIGHT;
                break;
            default:
                resultAlign = Alignment.ALIGN_LEFT;
                break;
        }
        switch (resultAlign) {
            case ALIGN_CENTER:
                return (float) Math.floor((double) (((float) getParagraphLeft(line)) + ((((float) this.mWidth) - getLineMax(line)) / 2.0f)));
            case ALIGN_RIGHT:
                return ((float) this.mWidth) - getLineMax(line);
            default:
                return 0.0f;
        }
    }

    public float getLineRight(int line) {
        Alignment resultAlign;
        int dir = getParagraphDirection(line);
        Alignment align = getParagraphAlignment(line);
        if (align == null) {
            align = Alignment.ALIGN_CENTER;
        }
        switch (align) {
            case ALIGN_NORMAL:
                if (dir != -1) {
                    resultAlign = Alignment.ALIGN_LEFT;
                    break;
                } else {
                    resultAlign = Alignment.ALIGN_RIGHT;
                    break;
                }
            case ALIGN_OPPOSITE:
                if (dir != -1) {
                    resultAlign = Alignment.ALIGN_RIGHT;
                    break;
                } else {
                    resultAlign = Alignment.ALIGN_LEFT;
                    break;
                }
            case ALIGN_CENTER:
                resultAlign = Alignment.ALIGN_CENTER;
                break;
            case ALIGN_RIGHT:
                resultAlign = Alignment.ALIGN_RIGHT;
                break;
            default:
                resultAlign = Alignment.ALIGN_LEFT;
                break;
        }
        switch (resultAlign) {
            case ALIGN_CENTER:
                return (float) Math.ceil((double) (((float) getParagraphRight(line)) - ((((float) this.mWidth) - getLineMax(line)) / 2.0f)));
            case ALIGN_RIGHT:
                return (float) this.mWidth;
            default:
                return getLineMax(line);
        }
    }

    public float getLineMax(int line) {
        float margin = (float) getParagraphLeadingMargin(line);
        float signedExtent = getLineExtent(line, false);
        return (signedExtent >= 0.0f ? signedExtent : -signedExtent) + margin;
    }

    public float getLineWidth(int line) {
        float margin = (float) getParagraphLeadingMargin(line);
        float signedExtent = getLineExtent(line, true);
        return (signedExtent >= 0.0f ? signedExtent : -signedExtent) + margin;
    }

    private float getLineExtent(int line, boolean full) {
        int start = getLineStart(line);
        int end = full ? getLineEnd(line) : getLineVisibleEnd(line);
        boolean hasTabs = getLineContainsTab(line);
        TabStops tabStops = null;
        if (hasTabs && (this.mText instanceof Spanned)) {
            TabStopSpan[] tabs = (TabStopSpan[]) getParagraphSpans((Spanned) this.mText, start, end, TabStopSpan.class);
            if (tabs.length > 0) {
                tabStops = new TabStops(TAB_INCREMENT, tabs);
            }
        }
        TabStops tabStops2 = tabStops;
        Directions directions = getLineDirections(line);
        if (directions == null) {
            return 0.0f;
        }
        int dir = getParagraphDirection(line);
        TextLine tl = TextLine.obtain();
        TextPaint paint = this.mWorkPaint;
        paint.set(this.mPaint);
        paint.setStartHyphenEdit(getStartHyphenEdit(line));
        paint.setEndHyphenEdit(getEndHyphenEdit(line));
        TextPaint textPaint = paint;
        TextLine tl2 = tl;
        tl.set(paint, this.mText, start, end, dir, directions, hasTabs, tabStops2, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        if (isJustificationRequired(line)) {
            tl2.justify(getJustifyWidth(line));
        }
        float width = tl2.metrics((Paint.FontMetricsInt) null);
        TextLine.recycle(tl2);
        return width;
    }

    private float getLineExtent(int line, TabStops tabStops, boolean full) {
        int start = getLineStart(line);
        int end = full ? getLineEnd(line) : getLineVisibleEnd(line);
        boolean hasTabs = getLineContainsTab(line);
        Directions directions = getLineDirections(line);
        int dir = getParagraphDirection(line);
        TextLine tl = TextLine.obtain();
        TextPaint paint = this.mWorkPaint;
        paint.set(this.mPaint);
        paint.setStartHyphenEdit(getStartHyphenEdit(line));
        paint.setEndHyphenEdit(getEndHyphenEdit(line));
        TextPaint textPaint = paint;
        TextLine tl2 = tl;
        tl.set(paint, this.mText, start, end, dir, directions, hasTabs, tabStops, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        if (isJustificationRequired(line)) {
            tl2.justify(getJustifyWidth(line));
        }
        float width = tl2.metrics((Paint.FontMetricsInt) null);
        TextLine.recycle(tl2);
        return width;
    }

    public int getLineForVertical(int vertical) {
        int high = getLineCount();
        int low = -1;
        while (high - low > 1) {
            int guess = (high + low) / 2;
            if (getLineTop(guess) > vertical) {
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

    public int getLineForOffset(int offset) {
        int high = getLineCount();
        int low = -1;
        while (high - low > 1) {
            int guess = (high + low) / 2;
            if (getLineStart(guess) > offset) {
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

    public int getOffsetForHorizontal(int line, float horiz) {
        return getOffsetForHorizontal(line, horiz, true);
    }

    public int getOffsetForHorizontal(int line, float horiz, boolean primary) {
        TextLine tl;
        int max;
        int best;
        int low;
        boolean z;
        Layout layout = this;
        int i = line;
        int lineEndOffset = getLineEnd(line);
        int lineStartOffset = getLineStart(line);
        Directions dirs = getLineDirections(line);
        TextLine tl2 = TextLine.obtain();
        TextLine tl3 = tl2;
        Directions dirs2 = dirs;
        tl2.set(layout.mPaint, layout.mText, lineStartOffset, lineEndOffset, getParagraphDirection(line), dirs, false, (TabStops) null, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        HorizontalMeasurementProvider horizontal = new HorizontalMeasurementProvider(i, primary);
        if (i == getLineCount() - 1) {
            max = lineEndOffset;
            tl = tl3;
        } else {
            tl = tl3;
            max = tl.getOffsetToLeftRightOf(lineEndOffset - lineStartOffset, !layout.isRtlCharAt(lineEndOffset - 1)) + lineStartOffset;
        }
        float bestdist = Math.abs(horizontal.get(lineStartOffset) - horiz);
        int guess = lineStartOffset;
        int i2 = 0;
        while (true) {
            Directions dirs3 = dirs2;
            if (i2 >= dirs3.mDirections.length) {
                break;
            }
            int here = dirs3.mDirections[i2] + lineStartOffset;
            int there = (dirs3.mDirections[i2 + 1] & RUN_LENGTH_MASK) + here;
            boolean isRtl = (dirs3.mDirections[i2 + 1] & 67108864) != 0;
            int swap = isRtl ? -1 : 1;
            if (there > max) {
                there = max;
            }
            int i3 = 1;
            int high = (there - 1) + 1;
            int low2 = (here + 1) - 1;
            while (true) {
                best = guess;
                low = low2;
                if (high - low <= i3) {
                    break;
                }
                int guess2 = (high + low) / 2;
                int adguess = layout.getOffsetAtStartOf(guess2);
                int i4 = adguess;
                int swap2 = swap;
                if (horizontal.get(adguess) * ((float) swap2) >= ((float) swap2) * horiz) {
                    high = guess2;
                    low2 = low;
                } else {
                    low2 = guess2;
                }
                swap = swap2;
                guess = best;
                layout = this;
                i3 = 1;
                boolean z2 = primary;
            }
            int swap3 = swap;
            if (low < here + 1) {
                low = here + 1;
            }
            if (low < there) {
                int aft = tl.getOffsetToLeftRightOf(low - lineStartOffset, isRtl) + lineStartOffset;
                int i5 = aft - lineStartOffset;
                if (!isRtl) {
                    int i6 = swap3;
                    z = true;
                } else {
                    int i7 = swap3;
                    z = false;
                }
                int low3 = tl.getOffsetToLeftRightOf(i5, z) + lineStartOffset;
                if (low3 >= here && low3 < there) {
                    float dist = Math.abs(horizontal.get(low3) - horiz);
                    if (aft < there) {
                        float other = Math.abs(horizontal.get(aft) - horiz);
                        if (other < dist) {
                            dist = other;
                            low3 = aft;
                        }
                    }
                    if (dist < bestdist) {
                        bestdist = dist;
                        best = low3;
                    }
                }
            }
            float dist2 = Math.abs(horizontal.get(here) - horiz);
            if (dist2 < bestdist) {
                guess = here;
                bestdist = dist2;
            } else {
                guess = best;
            }
            i2 += 2;
            dirs2 = dirs3;
            layout = this;
            int i8 = line;
            boolean z3 = primary;
        }
        int best2 = guess;
        if (Math.abs(horizontal.get(max) - horiz) <= bestdist) {
            best2 = max;
        }
        TextLine.recycle(tl);
        return best2;
    }

    private class HorizontalMeasurementProvider {
        private float[] mHorizontals;
        private final int mLine;
        private int mLineStartOffset;
        private final boolean mPrimary;

        HorizontalMeasurementProvider(int line, boolean primary) {
            this.mLine = line;
            this.mPrimary = primary;
            init();
        }

        private void init() {
            if (Layout.this.getLineDirections(this.mLine) != Layout.DIRS_ALL_LEFT_TO_RIGHT) {
                this.mHorizontals = Layout.this.getLineHorizontals(this.mLine, false, this.mPrimary);
                this.mLineStartOffset = Layout.this.getLineStart(this.mLine);
            }
        }

        /* access modifiers changed from: package-private */
        public float get(int offset) {
            int index = offset - this.mLineStartOffset;
            if (this.mHorizontals == null || index < 0 || index >= this.mHorizontals.length) {
                return Layout.this.getHorizontal(offset, this.mPrimary);
            }
            return this.mHorizontals[index];
        }
    }

    public final int getLineEnd(int line) {
        return getLineStart(line + 1);
    }

    public int getLineVisibleEnd(int line) {
        return getLineVisibleEnd(line, getLineStart(line), getLineStart(line + 1));
    }

    private int getLineVisibleEnd(int line, int start, int end) {
        CharSequence text = this.mText;
        if (line == getLineCount() - 1) {
            return end;
        }
        while (end > start) {
            char ch = text.charAt(end - 1);
            if (ch == 10) {
                return end - 1;
            }
            if (!TextLine.isLineEndSpace(ch)) {
                break;
            }
            end--;
        }
        return end;
    }

    public final int getLineBottom(int line) {
        return getLineTop(line + 1);
    }

    public final int getLineBottomWithoutSpacing(int line) {
        return getLineTop(line + 1) - getLineExtra(line);
    }

    public final int getLineBaseline(int line) {
        return getLineTop(line + 1) - getLineDescent(line);
    }

    public final int getLineAscent(int line) {
        return getLineTop(line) - (getLineTop(line + 1) - getLineDescent(line));
    }

    public int getLineExtra(int line) {
        return 0;
    }

    public int getOffsetToLeftOf(int offset) {
        return getOffsetToLeftRightOf(offset, true);
    }

    public int getOffsetToRightOf(int offset) {
        return getOffsetToLeftRightOf(offset, false);
    }

    private int getOffsetToLeftRightOf(int caret, boolean toLeft) {
        int i = caret;
        boolean toLeft2 = toLeft;
        int line = getLineForOffset(caret);
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        int lineDir = getParagraphDirection(line);
        boolean lineChanged = false;
        boolean advance = false;
        if (toLeft2 == (lineDir == -1)) {
            advance = true;
        }
        if (advance) {
            if (i == lineEnd) {
                if (line >= getLineCount() - 1) {
                    return i;
                }
                lineChanged = true;
                line++;
            }
        } else if (i == lineStart) {
            if (line <= 0) {
                return i;
            }
            lineChanged = true;
            line--;
        }
        if (lineChanged) {
            lineStart = getLineStart(line);
            lineEnd = getLineEnd(line);
            int newDir = getParagraphDirection(line);
            if (newDir != lineDir) {
                toLeft2 = !toLeft2;
                lineDir = newDir;
            }
        }
        Directions directions = getLineDirections(line);
        TextLine tl = TextLine.obtain();
        TextPaint textPaint = this.mPaint;
        CharSequence charSequence = this.mText;
        int ellipsisStart = getEllipsisStart(line);
        int ellipsisStart2 = getEllipsisStart(line) + getEllipsisCount(line);
        TextLine tl2 = tl;
        tl.set(textPaint, charSequence, lineStart, lineEnd, lineDir, directions, false, (TabStops) null, ellipsisStart, ellipsisStart2);
        int caret2 = tl2.getOffsetToLeftRightOf(i - lineStart, toLeft2) + lineStart;
        TextLine.recycle(tl2);
        return caret2;
    }

    private int getOffsetAtStartOf(int offset) {
        char c1;
        if (offset == 0) {
            return 0;
        }
        CharSequence text = this.mText;
        char c = text.charAt(offset);
        if (c >= 56320 && c <= 57343 && (c1 = text.charAt(offset - 1)) >= 55296 && c1 <= 56319) {
            offset--;
        }
        if (this.mSpannedText != 0) {
            ReplacementSpan[] spans = (ReplacementSpan[]) ((Spanned) text).getSpans(offset, offset, ReplacementSpan.class);
            for (int i = 0; i < spans.length; i++) {
                int start = ((Spanned) text).getSpanStart(spans[i]);
                int end = ((Spanned) text).getSpanEnd(spans[i]);
                if (start < offset && end > offset) {
                    offset = start;
                }
            }
        }
        return offset;
    }

    @UnsupportedAppUsage
    public boolean shouldClampCursor(int line) {
        int i = AnonymousClass1.$SwitchMap$android$text$Layout$Alignment[getParagraphAlignment(line).ordinal()];
        if (i != 1) {
            return i == 5;
        }
        if (getParagraphDirection(line) > 0) {
            return true;
        }
        return false;
    }

    public void getCursorPath(int point, Path dest, CharSequence editingBuffer) {
        Path path = dest;
        CharSequence charSequence = editingBuffer;
        dest.reset();
        int line = getLineForOffset(point);
        int top = getLineTop(line);
        int bottom = getLineBottomWithoutSpacing(line);
        float h1 = getPrimaryHorizontal(point, shouldClampCursor(line)) - 0.5f;
        int caps = TextKeyListener.getMetaState(charSequence, 1) | TextKeyListener.getMetaState(charSequence, 2048);
        int fn = TextKeyListener.getMetaState(charSequence, 2);
        int dist = 0;
        if (!(caps == 0 && fn == 0)) {
            dist = (bottom - top) >> 2;
            if (fn != 0) {
                top += dist;
            }
            if (caps != 0) {
                bottom -= dist;
            }
        }
        if (h1 < 0.5f) {
            h1 = 0.5f;
        }
        path.moveTo(h1, (float) top);
        path.lineTo(h1, (float) bottom);
        if (caps == 2) {
            path.moveTo(h1, (float) bottom);
            path.lineTo(h1 - ((float) dist), (float) (bottom + dist));
            path.lineTo(h1, (float) bottom);
            path.lineTo(((float) dist) + h1, (float) (bottom + dist));
        } else if (caps == 1) {
            path.moveTo(h1, (float) bottom);
            path.lineTo(h1 - ((float) dist), (float) (bottom + dist));
            path.moveTo(h1 - ((float) dist), ((float) (bottom + dist)) - 0.5f);
            path.lineTo(((float) dist) + h1, ((float) (bottom + dist)) - 0.5f);
            path.moveTo(((float) dist) + h1, (float) (bottom + dist));
            path.lineTo(h1, (float) bottom);
        }
        if (fn == 2) {
            path.moveTo(h1, (float) top);
            path.lineTo(h1 - ((float) dist), (float) (top - dist));
            path.lineTo(h1, (float) top);
            path.lineTo(((float) dist) + h1, (float) (top - dist));
        } else if (fn == 1) {
            path.moveTo(h1, (float) top);
            path.lineTo(h1 - ((float) dist), (float) (top - dist));
            path.moveTo(h1 - ((float) dist), ((float) (top - dist)) + 0.5f);
            path.lineTo(((float) dist) + h1, ((float) (top - dist)) + 0.5f);
            path.moveTo(((float) dist) + h1, (float) (top - dist));
            path.lineTo(h1, (float) top);
        }
    }

    private void addSelection(int line, int start, int end, int top, int bottom, SelectionRectangleConsumer consumer) {
        int st;
        int en;
        Layout layout = this;
        int i = line;
        int i2 = start;
        int i3 = end;
        int linestart = getLineStart(line);
        int lineend = getLineEnd(line);
        Directions dirs = getLineDirections(line);
        if (lineend > linestart && layout.mText.charAt(lineend - 1) == 10) {
            lineend--;
        }
        boolean z = false;
        int i4 = 0;
        while (i4 < dirs.mDirections.length) {
            int here = dirs.mDirections[i4] + linestart;
            int there = (dirs.mDirections[i4 + 1] & RUN_LENGTH_MASK) + here;
            if (there > lineend) {
                there = lineend;
            }
            if (i2 > there || i3 < here || (st = Math.max(i2, here)) == (en = Math.min(i3, there))) {
                int i5 = top;
                int i6 = bottom;
            } else {
                float h1 = layout.getHorizontal(st, z, i, z);
                float h2 = layout.getHorizontal(en, true, i, z);
                consumer.accept(Math.min(h1, h2), (float) top, Math.max(h1, h2), (float) bottom, (dirs.mDirections[i4 + 1] & 67108864) != 0 ? 0 : 1);
            }
            i4 += 2;
            layout = this;
            i = line;
            z = false;
        }
        int i7 = top;
        int i8 = bottom;
    }

    public void getSelectionPath(int start, int end, Path dest) {
        dest.reset();
        getSelection(start, end, new SelectionRectangleConsumer() {
            public final void accept(float f, float f2, float f3, float f4, int i) {
                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
            }
        });
    }

    public final void getSelection(int start, int end, SelectionRectangleConsumer consumer) {
        int i = start;
        int end2 = end;
        if (i != end2) {
            if (end2 < i) {
                end2 = start;
                i = end;
            }
            int start2 = i;
            int end3 = end2;
            int startline = getLineForOffset(start2);
            int endline = getLineForOffset(end3);
            int top = getLineTop(startline);
            int bottom = getLineBottomWithoutSpacing(endline);
            if (startline == endline) {
                addSelection(startline, start2, end3, top, bottom, consumer);
                return;
            }
            float width = (float) this.mWidth;
            addSelection(startline, start2, getLineEnd(startline), top, getLineBottom(startline), consumer);
            if (getParagraphDirection(startline) == -1) {
                consumer.accept(getLineLeft(startline), (float) top, 0.0f, (float) getLineBottom(startline), 0);
            } else {
                consumer.accept(getLineRight(startline), (float) top, width, (float) getLineBottom(startline), 1);
            }
            for (int i2 = startline + 1; i2 < endline; i2++) {
                int top2 = getLineTop(i2);
                int bottom2 = getLineBottom(i2);
                if (getParagraphDirection(i2) == -1) {
                    consumer.accept(0.0f, (float) top2, width, (float) bottom2, 0);
                } else {
                    consumer.accept(0.0f, (float) top2, width, (float) bottom2, 1);
                }
            }
            int top3 = getLineTop(endline);
            int bottom3 = getLineBottomWithoutSpacing(endline);
            addSelection(endline, getLineStart(endline), end3, top3, bottom3, consumer);
            if (getParagraphDirection(endline) == -1) {
                consumer.accept(width, (float) top3, getLineRight(endline), (float) bottom3, 0);
                return;
            }
            consumer.accept(0.0f, (float) top3, getLineLeft(endline), (float) bottom3, 1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r2 = (android.text.style.AlignmentSpan[]) getParagraphSpans((android.text.Spanned) r5.mText, getLineStart(r6), getLineEnd(r6), android.text.style.AlignmentSpan.class);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.text.Layout.Alignment getParagraphAlignment(int r6) {
        /*
            r5 = this;
            android.text.Layout$Alignment r0 = r5.mAlignment
            boolean r1 = r5.mSpannedText
            if (r1 == 0) goto L_0x0025
            java.lang.CharSequence r1 = r5.mText
            android.text.Spanned r1 = (android.text.Spanned) r1
            int r2 = r5.getLineStart(r6)
            int r3 = r5.getLineEnd(r6)
            java.lang.Class<android.text.style.AlignmentSpan> r4 = android.text.style.AlignmentSpan.class
            java.lang.Object[] r2 = getParagraphSpans(r1, r2, r3, r4)
            android.text.style.AlignmentSpan[] r2 = (android.text.style.AlignmentSpan[]) r2
            int r3 = r2.length
            if (r3 <= 0) goto L_0x0025
            int r4 = r3 + -1
            r4 = r2[r4]
            android.text.Layout$Alignment r0 = r4.getAlignment()
        L_0x0025:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.getParagraphAlignment(int):android.text.Layout$Alignment");
    }

    public final int getParagraphLeft(int line) {
        if (getParagraphDirection(line) == -1 || !this.mSpannedText) {
            return 0;
        }
        return getParagraphLeadingMargin(line);
    }

    public final int getParagraphRight(int line) {
        int right = this.mWidth;
        if (getParagraphDirection(line) == 1 || !this.mSpannedText) {
            return right;
        }
        return right - getParagraphLeadingMargin(line);
    }

    private int getParagraphLeadingMargin(int line) {
        if (!this.mSpannedText) {
            return 0;
        }
        Spanned spanned = (Spanned) this.mText;
        int lineStart = getLineStart(line);
        LeadingMarginSpan[] spans = (LeadingMarginSpan[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, getLineEnd(line), LeadingMarginSpan.class), LeadingMarginSpan.class);
        if (spans.length == 0) {
            return 0;
        }
        int margin = 0;
        boolean useFirstLineMargin = lineStart == 0 || spanned.charAt(lineStart + -1) == 10;
        for (int i = 0; i < spans.length; i++) {
            if (spans[i] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                useFirstLineMargin |= line < getLineForOffset(spanned.getSpanStart(spans[i])) + ((LeadingMarginSpan.LeadingMarginSpan2) spans[i]).getLeadingMarginLineCount();
            }
        }
        for (LeadingMarginSpan span : spans) {
            margin += span.getLeadingMargin(useFirstLineMargin);
        }
        return margin;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00eb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static float measurePara(android.text.TextPaint r26, java.lang.CharSequence r27, int r28, int r29, android.text.TextDirectionHeuristic r30) {
        /*
            r12 = r27
            r13 = r28
            r14 = r29
            r1 = 0
            android.text.TextLine r0 = android.text.TextLine.obtain()
            r15 = r0
            r11 = r30
            android.text.MeasuredParagraph r0 = android.text.MeasuredParagraph.buildForBidi(r12, r13, r14, r11, r1)     // Catch:{ all -> 0x00e4 }
            r10 = r0
            char[] r0 = r10.getChars()     // Catch:{ all -> 0x00e0 }
            int r1 = r0.length     // Catch:{ all -> 0x00e0 }
            r9 = r1
            r1 = 0
            android.text.Layout$Directions r7 = r10.getDirections(r1, r9)     // Catch:{ all -> 0x00e0 }
            int r6 = r10.getParagraphDir()     // Catch:{ all -> 0x00e0 }
            r2 = 0
            r3 = 0
            r4 = 0
            boolean r5 = r12 instanceof android.text.Spanned     // Catch:{ all -> 0x00e0 }
            if (r5 == 0) goto L_0x005a
            r5 = r12
            android.text.Spanned r5 = (android.text.Spanned) r5     // Catch:{ all -> 0x0057 }
            java.lang.Class<android.text.style.LeadingMarginSpan> r8 = android.text.style.LeadingMarginSpan.class
            java.lang.Object[] r8 = getParagraphSpans(r5, r13, r14, r8)     // Catch:{ all -> 0x0057 }
            android.text.style.LeadingMarginSpan[] r8 = (android.text.style.LeadingMarginSpan[]) r8     // Catch:{ all -> 0x0057 }
            int r1 = r8.length     // Catch:{ all -> 0x0057 }
            r17 = r4
            r4 = 0
        L_0x0038:
            if (r4 >= r1) goto L_0x0052
            r18 = r8[r4]     // Catch:{ all -> 0x0057 }
            r19 = r18
            r20 = r1
            r1 = 1
            r21 = r2
            r2 = r19
            int r1 = r2.getLeadingMargin(r1)     // Catch:{ all -> 0x0057 }
            int r17 = r17 + r1
            int r4 = r4 + 1
            r1 = r20
            r2 = r21
            goto L_0x0038
        L_0x0052:
            r21 = r2
            r8 = r17
            goto L_0x005d
        L_0x0057:
            r0 = move-exception
            goto L_0x00e6
        L_0x005a:
            r21 = r2
            r8 = r4
        L_0x005d:
            r16 = 0
        L_0x005f:
            r1 = r16
            if (r1 >= r9) goto L_0x00a4
            char r2 = r0[r1]     // Catch:{ all -> 0x0057 }
            r4 = 9
            if (r2 != r4) goto L_0x009d
            r2 = 1
            boolean r4 = r12 instanceof android.text.Spanned     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0096
            r4 = r12
            android.text.Spanned r4 = (android.text.Spanned) r4     // Catch:{ all -> 0x0057 }
            java.lang.Class<android.text.style.TabStopSpan> r5 = android.text.style.TabStopSpan.class
            int r5 = r4.nextSpanTransition(r13, r14, r5)     // Catch:{ all -> 0x0057 }
            r22 = r0
            java.lang.Class<android.text.style.TabStopSpan> r0 = android.text.style.TabStopSpan.class
            java.lang.Object[] r0 = getParagraphSpans(r4, r13, r5, r0)     // Catch:{ all -> 0x0057 }
            android.text.style.TabStopSpan[] r0 = (android.text.style.TabStopSpan[]) r0     // Catch:{ all -> 0x0057 }
            r23 = r2
            int r2 = r0.length     // Catch:{ all -> 0x0057 }
            if (r2 <= 0) goto L_0x0091
            android.text.Layout$TabStops r2 = new android.text.Layout$TabStops     // Catch:{ all -> 0x0057 }
            r24 = r3
            r3 = 1101004800(0x41a00000, float:20.0)
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0057 }
            r3 = r2
            goto L_0x0093
        L_0x0091:
            r24 = r3
        L_0x0093:
            r24 = r3
            goto L_0x00aa
        L_0x0096:
            r22 = r0
            r23 = r2
            r24 = r3
            goto L_0x00aa
        L_0x009d:
            r22 = r0
            r24 = r3
            int r16 = r1 + 1
            goto L_0x005f
        L_0x00a4:
            r22 = r0
            r24 = r3
            r23 = r21
        L_0x00aa:
            r0 = 0
            r16 = 0
            r1 = r15
            r2 = r26
            r3 = r27
            r4 = r28
            r5 = r29
            r25 = r8
            r8 = r23
            r17 = r9
            r9 = r24
            r18 = r10
            r10 = r0
            r11 = r16
            r1.set(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00dc }
            r4 = r25
            float r0 = (float) r4     // Catch:{ all -> 0x00dc }
            r1 = 0
            float r1 = r15.metrics(r1)     // Catch:{ all -> 0x00dc }
            float r1 = java.lang.Math.abs(r1)     // Catch:{ all -> 0x00dc }
            float r0 = r0 + r1
            android.text.TextLine.recycle(r15)
            if (r18 == 0) goto L_0x00db
            r18.recycle()
        L_0x00db:
            return r0
        L_0x00dc:
            r0 = move-exception
            r10 = r18
            goto L_0x00e6
        L_0x00e0:
            r0 = move-exception
            r18 = r10
            goto L_0x00e6
        L_0x00e4:
            r0 = move-exception
            r10 = r1
        L_0x00e6:
            android.text.TextLine.recycle(r15)
            if (r10 == 0) goto L_0x00ee
            r10.recycle()
        L_0x00ee:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.Layout.measurePara(android.text.TextPaint, java.lang.CharSequence, int, int, android.text.TextDirectionHeuristic):float");
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public static class TabStops {
        private float mIncrement;
        private int mNumStops;
        private float[] mStops;

        public TabStops(float increment, Object[] spans) {
            reset(increment, spans);
        }

        /* access modifiers changed from: package-private */
        public void reset(float increment, Object[] spans) {
            this.mIncrement = increment;
            int ns = 0;
            if (spans != null) {
                float[] stops = this.mStops;
                float[] stops2 = stops;
                int ns2 = 0;
                for (TabStopSpan tabStopSpan : spans) {
                    if (tabStopSpan instanceof TabStopSpan) {
                        if (stops2 == null) {
                            stops2 = new float[10];
                        } else if (ns2 == stops2.length) {
                            float[] nstops = new float[(ns2 * 2)];
                            for (int i = 0; i < ns2; i++) {
                                nstops[i] = stops2[i];
                            }
                            stops2 = nstops;
                        }
                        stops2[ns2] = (float) tabStopSpan.getTabStop();
                        ns2++;
                    }
                }
                if (ns2 > 1) {
                    Arrays.sort(stops2, 0, ns2);
                }
                if (stops2 != this.mStops) {
                    this.mStops = stops2;
                }
                ns = ns2;
            }
            this.mNumStops = ns;
        }

        /* access modifiers changed from: package-private */
        public float nextTab(float h) {
            int ns = this.mNumStops;
            if (ns > 0) {
                float[] stops = this.mStops;
                for (int i = 0; i < ns; i++) {
                    float stop = stops[i];
                    if (stop > h) {
                        return stop;
                    }
                }
            }
            return nextDefaultStop(h, this.mIncrement);
        }

        public static float nextDefaultStop(float h, float inc) {
            return ((float) ((int) ((h + inc) / inc))) * inc;
        }
    }

    static float nextTab(CharSequence text, int start, int end, float h, Object[] tabs) {
        float nh = Float.MAX_VALUE;
        boolean alltabs = false;
        if (text instanceof Spanned) {
            if (tabs == null) {
                tabs = getParagraphSpans((Spanned) text, start, end, TabStopSpan.class);
                alltabs = true;
            }
            for (int i = 0; i < tabs.length; i++) {
                if (alltabs || (tabs[i] instanceof TabStopSpan)) {
                    int where = ((TabStopSpan) tabs[i]).getTabStop();
                    if (((float) where) < nh && ((float) where) > h) {
                        nh = (float) where;
                    }
                }
            }
            if (nh != Float.MAX_VALUE) {
                return nh;
            }
        }
        return ((float) ((int) ((h + TAB_INCREMENT) / TAB_INCREMENT))) * TAB_INCREMENT;
    }

    /* access modifiers changed from: protected */
    public final boolean isSpanned() {
        return this.mSpannedText;
    }

    static <T> T[] getParagraphSpans(Spanned text, int start, int end, Class<T> type) {
        if (start == end && start > 0) {
            return ArrayUtils.emptyArray(type);
        }
        if (text instanceof SpannableStringBuilder) {
            return ((SpannableStringBuilder) text).getSpans(start, end, type, false);
        }
        return text.getSpans(start, end, type);
    }

    /* access modifiers changed from: private */
    public void ellipsize(int start, int end, int line, char[] dest, int destoff, TextUtils.TruncateAt method) {
        char c;
        int i = start;
        int i2 = line;
        int ellipsisCount = getEllipsisCount(i2);
        if (ellipsisCount != 0) {
            int ellipsisStart = getEllipsisStart(i2);
            int lineStart = getLineStart(i2);
            String ellipsisString = TextUtils.getEllipsisString(method);
            int ellipsisStringLen = ellipsisString.length();
            boolean useEllipsisString = ellipsisCount >= ellipsisStringLen;
            for (int i3 = 0; i3 < ellipsisCount; i3++) {
                if (!useEllipsisString || i3 >= ellipsisStringLen) {
                    c = 65279;
                } else {
                    c = ellipsisString.charAt(i3);
                }
                int a = i3 + ellipsisStart + lineStart;
                if (i > a) {
                    int i4 = end;
                } else if (a < end) {
                    dest[(destoff + a) - i] = c;
                }
            }
            int i5 = end;
        }
    }

    public static class Directions {
        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public int[] mDirections;

        @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
        public Directions(int[] dirs) {
            this.mDirections = dirs;
        }

        public int getRunCount() {
            return this.mDirections.length / 2;
        }

        public int getRunStart(int runIndex) {
            return this.mDirections[runIndex * 2];
        }

        public int getRunLength(int runIndex) {
            return this.mDirections[(runIndex * 2) + 1] & Layout.RUN_LENGTH_MASK;
        }

        public boolean isRunRtl(int runIndex) {
            return (this.mDirections[(runIndex * 2) + 1] & 67108864) != 0;
        }
    }

    static class Ellipsizer implements CharSequence, GetChars {
        Layout mLayout;
        TextUtils.TruncateAt mMethod;
        CharSequence mText;
        int mWidth;

        public Ellipsizer(CharSequence s) {
            this.mText = s;
        }

        public char charAt(int off) {
            char[] buf = TextUtils.obtain(1);
            getChars(off, off + 1, buf, 0);
            char ret = buf[0];
            TextUtils.recycle(buf);
            return ret;
        }

        public void getChars(int start, int end, char[] dest, int destoff) {
            int line1 = this.mLayout.getLineForOffset(start);
            int line2 = this.mLayout.getLineForOffset(end);
            TextUtils.getChars(this.mText, start, end, dest, destoff);
            for (int i = line1; i <= line2; i++) {
                this.mLayout.ellipsize(start, end, i, dest, destoff, this.mMethod);
            }
        }

        public int length() {
            return this.mText.length();
        }

        public CharSequence subSequence(int start, int end) {
            char[] s = new char[(end - start)];
            getChars(start, end, s, 0);
            return new String(s);
        }

        public String toString() {
            char[] s = new char[length()];
            getChars(0, length(), s, 0);
            return new String(s);
        }
    }

    static class SpannedEllipsizer extends Ellipsizer implements Spanned {
        private Spanned mSpanned;

        public SpannedEllipsizer(CharSequence display) {
            super(display);
            this.mSpanned = (Spanned) display;
        }

        public <T> T[] getSpans(int start, int end, Class<T> type) {
            return this.mSpanned.getSpans(start, end, type);
        }

        public int getSpanStart(Object tag) {
            return this.mSpanned.getSpanStart(tag);
        }

        public int getSpanEnd(Object tag) {
            return this.mSpanned.getSpanEnd(tag);
        }

        public int getSpanFlags(Object tag) {
            return this.mSpanned.getSpanFlags(tag);
        }

        public int nextSpanTransition(int start, int limit, Class type) {
            return this.mSpanned.nextSpanTransition(start, limit, type);
        }

        public CharSequence subSequence(int start, int end) {
            char[] s = new char[(end - start)];
            getChars(start, end, s, 0);
            SpannableString ss = new SpannableString(new String(s));
            TextUtils.copySpansFrom(this.mSpanned, start, end, Object.class, ss, 0);
            return ss;
        }
    }
}
