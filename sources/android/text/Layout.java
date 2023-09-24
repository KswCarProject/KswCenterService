package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.text.style.AlignmentSpan;
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

/* loaded from: classes4.dex */
public abstract class Layout {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final float DEFAULT_LINESPACING_ADDITION = 0.0f;
    public static final float DEFAULT_LINESPACING_MULTIPLIER = 1.0f;
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
    static final int RUN_LEVEL_MASK = 63;
    static final int RUN_LEVEL_SHIFT = 26;
    static final int RUN_RTL_FLAG = 67108864;
    private static final float TAB_INCREMENT = 20.0f;
    public static final int TEXT_SELECTION_LAYOUT_LEFT_TO_RIGHT = 1;
    public static final int TEXT_SELECTION_LAYOUT_RIGHT_TO_LEFT = 0;
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
    private static final ParagraphStyle[] NO_PARA_SPANS = (ParagraphStyle[]) ArrayUtils.emptyArray(ParagraphStyle.class);
    private static final Rect sTempRect = new Rect();
    static final int RUN_LENGTH_MASK = 67108863;
    @UnsupportedAppUsage
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public static final Directions DIRS_ALL_LEFT_TO_RIGHT = new Directions(new int[]{0, RUN_LENGTH_MASK});
    @UnsupportedAppUsage
    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public static final Directions DIRS_ALL_RIGHT_TO_LEFT = new Directions(new int[]{0, 134217727});

    /* loaded from: classes4.dex */
    public enum Alignment {
        ALIGN_NORMAL,
        ALIGN_OPPOSITE,
        ALIGN_CENTER,
        ALIGN_LEFT,
        ALIGN_RIGHT
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface BreakStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface Direction {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface HyphenationFrequency {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface JustificationMode {
    }

    @FunctionalInterface
    /* loaded from: classes4.dex */
    public interface SelectionRectangleConsumer {
        void accept(float f, float f2, float f3, float f4, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
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
            int next = TextUtils.indexOf(source, '\n', i, end);
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
        if (width < 0) {
            throw new IllegalArgumentException("Layout: " + width + " < 0");
        }
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
    }

    protected void setJustificationMode(int justificationMode) {
        this.mJustificationMode = justificationMode;
    }

    void replaceWith(CharSequence text, TextPaint paint, int width, Alignment align, float spacingmult, float spacingadd) {
        if (width < 0) {
            throw new IllegalArgumentException("Layout: " + width + " < 0");
        }
        this.mText = text;
        this.mPaint = paint;
        this.mWidth = width;
        this.mAlignment = align;
        this.mSpacingMult = spacingmult;
        this.mSpacingAdd = spacingadd;
        this.mSpannedText = text instanceof Spanned;
    }

    public void draw(Canvas c) {
        draw(c, null, null, 0);
    }

    public void draw(Canvas canvas, Path highlight, Paint highlightPaint, int cursorOffsetVertical) {
        long lineRange = getLineRangeForDraw(canvas);
        int firstLine = TextUtils.unpackRangeStartFromLong(lineRange);
        int lastLine = TextUtils.unpackRangeEndFromLong(lineRange);
        if (lastLine < 0) {
            return;
        }
        drawBackground(canvas, highlight, highlightPaint, cursorOffsetVertical, firstLine, lastLine);
        drawText(canvas, firstLine, lastLine);
    }

    private boolean isJustificationRequired(int lineNum) {
        int lineEnd;
        return (this.mJustificationMode == 0 || (lineEnd = getLineEnd(lineNum)) >= this.mText.length() || this.mText.charAt(lineEnd + (-1)) == '\n') ? false : true;
    }

    private float getJustifyWidth(int lineNum) {
        Alignment align;
        int indentWidth;
        Alignment paraAlign = this.mAlignment;
        int left = 0;
        int right = this.mWidth;
        int dir = getParagraphDirection(lineNum);
        ParagraphStyle[] spans = NO_PARA_SPANS;
        if (this.mSpannedText) {
            Spanned sp = (Spanned) this.mText;
            int start = getLineStart(lineNum);
            boolean isFirstParaLine = start == 0 || this.mText.charAt(start + (-1)) == '\n';
            if (isFirstParaLine) {
                spans = (ParagraphStyle[]) getParagraphSpans(sp, start, sp.nextSpanTransition(start, this.mText.length(), ParagraphStyle.class), ParagraphStyle.class);
                int n = spans.length - 1;
                while (true) {
                    if (n < 0) {
                        break;
                    } else if (!(spans[n] instanceof AlignmentSpan)) {
                        n--;
                    } else {
                        paraAlign = ((AlignmentSpan) spans[n]).getAlignment();
                        break;
                    }
                }
            }
            int spanEnd = spans.length;
            boolean useFirstLineMargin = isFirstParaLine;
            int n2 = 0;
            while (true) {
                if (n2 >= spanEnd) {
                    break;
                }
                if (spans[n2] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                    int count = ((LeadingMarginSpan.LeadingMarginSpan2) spans[n2]).getLeadingMarginLineCount();
                    int startLine = getLineForOffset(sp.getSpanStart(spans[n2]));
                    if (lineNum < startLine + count) {
                        useFirstLineMargin = true;
                        break;
                    }
                }
                n2++;
            }
            int n3 = 0;
            while (true) {
                int n4 = n3;
                if (n4 >= spanEnd) {
                    break;
                }
                if (spans[n4] instanceof LeadingMarginSpan) {
                    LeadingMarginSpan margin = (LeadingMarginSpan) spans[n4];
                    if (dir == -1) {
                        right -= margin.getLeadingMargin(useFirstLineMargin);
                    } else {
                        left += margin.getLeadingMargin(useFirstLineMargin);
                    }
                }
                n3 = n4 + 1;
            }
        }
        if (paraAlign == Alignment.ALIGN_LEFT) {
            align = dir == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
        } else if (paraAlign == Alignment.ALIGN_RIGHT) {
            align = dir == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
        } else {
            align = paraAlign;
        }
        if (align == Alignment.ALIGN_NORMAL) {
            indentWidth = dir == 1 ? getIndentAdjust(lineNum, Alignment.ALIGN_LEFT) : -getIndentAdjust(lineNum, Alignment.ALIGN_RIGHT);
        } else if (align != Alignment.ALIGN_OPPOSITE) {
            indentWidth = getIndentAdjust(lineNum, Alignment.ALIGN_CENTER);
        } else {
            indentWidth = dir == 1 ? -getIndentAdjust(lineNum, Alignment.ALIGN_RIGHT) : getIndentAdjust(lineNum, Alignment.ALIGN_LEFT);
        }
        return (right - left) - indentWidth;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x010f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01f0 A[EDGE_INSN: B:111:0x01f0->B:52:0x01f0 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011d  */
    @UnsupportedAppUsage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void drawText(Canvas canvas, int firstLine, int lastLine) {
        int start;
        int dir;
        int lineNum;
        TabStops tabStops;
        TextLine tl;
        CharSequence buf;
        TextPaint paint;
        Layout layout;
        int lbaseline;
        int ltop;
        boolean tabStopsIsInitialized;
        int right;
        ParagraphStyle[] spans;
        int spanEnd;
        int left;
        TabStops tabStops2;
        int dir2;
        int i;
        int x;
        int indentWidth;
        int indentWidth2;
        int i2;
        ParagraphStyle[] spans2;
        TabStops tabStops3;
        TextLine tl2;
        int indentWidth3;
        ParagraphStyle[] spans3;
        Alignment paraAlign;
        boolean z;
        ParagraphStyle[] spans4;
        int length;
        boolean useFirstLineMargin;
        int n;
        Spanned sp;
        boolean useFirstLineMargin2;
        int n2;
        int n3;
        int dir3;
        int n4;
        boolean useFirstLineMargin3;
        ParagraphStyle[] spans5;
        int lineNum2;
        TabStops tabStops4;
        TextLine tl3;
        CharSequence buf2;
        TextPaint paint2;
        Layout layout2;
        int lbaseline2;
        Spanned sp2;
        int length2;
        int ltop2;
        int textLength;
        boolean useFirstLineMargin4;
        Layout layout3 = this;
        int i3 = firstLine;
        int previousLineBottom = layout3.getLineTop(i3);
        int previousLineEnd = layout3.getLineStart(i3);
        ParagraphStyle[] spans6 = NO_PARA_SPANS;
        TextPaint paint3 = layout3.mWorkPaint;
        paint3.set(layout3.mPaint);
        CharSequence buf3 = layout3.mText;
        Alignment paraAlign2 = layout3.mAlignment;
        TextLine tl4 = TextLine.obtain();
        TabStops tabStops5 = null;
        boolean tabStopsIsInitialized2 = false;
        int spanEnd2 = 0;
        ParagraphStyle[] spans7 = spans6;
        int lineNum3 = previousLineBottom;
        int previousLineBottom2 = i3;
        while (true) {
            int lineNum4 = previousLineBottom2;
            if (lineNum4 > lastLine) {
                TextLine.recycle(tl4);
                return;
            }
            int start2 = previousLineEnd;
            int previousLineEnd2 = layout3.getLineStart(lineNum4 + 1);
            boolean justify = layout3.isJustificationRequired(lineNum4);
            int end = layout3.getLineVisibleEnd(lineNum4, start2, previousLineEnd2);
            paint3.setStartHyphenEdit(layout3.getStartHyphenEdit(lineNum4));
            paint3.setEndHyphenEdit(layout3.getEndHyphenEdit(lineNum4));
            int ltop3 = lineNum3;
            int previousLineEnd3 = lineNum4 + 1;
            int lbottom = layout3.getLineTop(previousLineEnd3);
            int lbaseline3 = lbottom - layout3.getLineDescent(lineNum4);
            int dir4 = layout3.getParagraphDirection(lineNum4);
            int lbaseline4 = lbaseline3;
            int lbaseline5 = layout3.mWidth;
            int textLength2 = ltop3;
            if (layout3.mSpannedText) {
                Spanned sp3 = (Spanned) buf3;
                int ltop4 = buf3.length();
                if (start2 != 0) {
                    spans3 = spans7;
                    paraAlign = paraAlign2;
                    if (buf3.charAt(start2 - 1) != '\n') {
                        z = false;
                        boolean isFirstParaLine = z;
                        if (start2 >= spanEnd2) {
                            start = start2;
                        } else if (lineNum4 == i3 || isFirstParaLine) {
                            spanEnd2 = sp3.nextSpanTransition(start2, ltop4, ParagraphStyle.class);
                            ParagraphStyle[] spans8 = (ParagraphStyle[]) getParagraphSpans(sp3, start2, spanEnd2, ParagraphStyle.class);
                            Alignment paraAlign3 = layout3.mAlignment;
                            start = start2;
                            int n5 = spans8.length - 1;
                            while (true) {
                                if (n5 < 0) {
                                    break;
                                }
                                Alignment paraAlign4 = paraAlign3;
                                if (spans8[n5] instanceof AlignmentSpan) {
                                    paraAlign3 = ((AlignmentSpan) spans8[n5]).getAlignment();
                                    break;
                                } else {
                                    n5--;
                                    paraAlign3 = paraAlign4;
                                }
                            }
                            tabStopsIsInitialized = false;
                            spans4 = spans8;
                            paraAlign = paraAlign3;
                            spanEnd = spanEnd2;
                            length = spans4.length;
                            useFirstLineMargin = isFirstParaLine;
                            n = 0;
                            while (true) {
                                if (n >= length) {
                                    sp = sp3;
                                    useFirstLineMargin2 = useFirstLineMargin;
                                    break;
                                }
                                if (spans4[n] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                                    int count = ((LeadingMarginSpan.LeadingMarginSpan2) spans4[n]).getLeadingMarginLineCount();
                                    useFirstLineMargin4 = useFirstLineMargin;
                                    int startLine = layout3.getLineForOffset(sp3.getSpanStart(spans4[n]));
                                    sp = sp3;
                                    if (lineNum4 < startLine + count) {
                                        useFirstLineMargin2 = true;
                                        break;
                                    }
                                } else {
                                    useFirstLineMargin4 = useFirstLineMargin;
                                    sp = sp3;
                                }
                                n++;
                                useFirstLineMargin = useFirstLineMargin4;
                                sp3 = sp;
                            }
                            right = lbaseline5;
                            n2 = 0;
                            left = 0;
                            while (true) {
                                n3 = n2;
                                if (n3 >= length) {
                                    break;
                                }
                                if (spans4[n3] instanceof LeadingMarginSpan) {
                                    LeadingMarginSpan margin = (LeadingMarginSpan) spans4[n3];
                                    if (dir4 == -1) {
                                        dir3 = dir4;
                                        lbaseline2 = lbaseline4;
                                        sp2 = sp;
                                        n4 = n3;
                                        int n6 = right;
                                        boolean useFirstLineMargin5 = useFirstLineMargin2;
                                        length2 = length;
                                        spans5 = spans4;
                                        lineNum2 = lineNum4;
                                        int lineNum5 = start;
                                        tabStops4 = tabStops5;
                                        tl3 = tl4;
                                        buf2 = buf3;
                                        int i4 = textLength2;
                                        ltop2 = ltop4;
                                        textLength = i4;
                                        margin.drawLeadingMargin(canvas, paint3, n6, dir3, textLength, lbaseline2, lbottom, buf3, lineNum5, end, isFirstParaLine, this);
                                        right -= margin.getLeadingMargin(useFirstLineMargin5);
                                        paint2 = paint3;
                                        useFirstLineMargin3 = useFirstLineMargin5;
                                        layout2 = this;
                                    } else {
                                        dir3 = dir4;
                                        n4 = n3;
                                        spans5 = spans4;
                                        lineNum2 = lineNum4;
                                        tabStops4 = tabStops5;
                                        tl3 = tl4;
                                        buf2 = buf3;
                                        lbaseline2 = lbaseline4;
                                        sp2 = sp;
                                        length2 = length;
                                        int i5 = textLength2;
                                        ltop2 = ltop4;
                                        textLength = i5;
                                        paint2 = paint3;
                                        useFirstLineMargin3 = useFirstLineMargin2;
                                        layout2 = this;
                                        margin.drawLeadingMargin(canvas, paint3, left, dir3, textLength, lbaseline2, lbottom, buf2, start, end, isFirstParaLine, this);
                                        left += margin.getLeadingMargin(useFirstLineMargin3);
                                    }
                                } else {
                                    dir3 = dir4;
                                    n4 = n3;
                                    useFirstLineMargin3 = useFirstLineMargin2;
                                    spans5 = spans4;
                                    lineNum2 = lineNum4;
                                    tabStops4 = tabStops5;
                                    tl3 = tl4;
                                    buf2 = buf3;
                                    paint2 = paint3;
                                    layout2 = layout3;
                                    lbaseline2 = lbaseline4;
                                    sp2 = sp;
                                    length2 = length;
                                    int i6 = textLength2;
                                    ltop2 = ltop4;
                                    textLength = i6;
                                }
                                paint3 = paint2;
                                n2 = n4 + 1;
                                useFirstLineMargin2 = useFirstLineMargin3;
                                layout3 = layout2;
                                length = length2;
                                dir4 = dir3;
                                spans4 = spans5;
                                lineNum4 = lineNum2;
                                tabStops5 = tabStops4;
                                tl4 = tl3;
                                buf3 = buf2;
                                sp = sp2;
                                lbaseline4 = lbaseline2;
                                int i7 = ltop2;
                                textLength2 = textLength;
                                ltop4 = i7;
                            }
                            dir = dir4;
                            lineNum = lineNum4;
                            tabStops = tabStops5;
                            tl = tl4;
                            buf = buf3;
                            paint = paint3;
                            layout = layout3;
                            lbaseline = lbaseline4;
                            ltop = textLength2;
                            paraAlign2 = paraAlign;
                            spans = spans4;
                        } else {
                            start = start2;
                        }
                        tabStopsIsInitialized = tabStopsIsInitialized2;
                        spans4 = spans3;
                        spanEnd = spanEnd2;
                        length = spans4.length;
                        useFirstLineMargin = isFirstParaLine;
                        n = 0;
                        while (true) {
                            if (n >= length) {
                            }
                            n++;
                            useFirstLineMargin = useFirstLineMargin4;
                            sp3 = sp;
                        }
                        right = lbaseline5;
                        n2 = 0;
                        left = 0;
                        while (true) {
                            n3 = n2;
                            if (n3 >= length) {
                            }
                            paint3 = paint2;
                            n2 = n4 + 1;
                            useFirstLineMargin2 = useFirstLineMargin3;
                            layout3 = layout2;
                            length = length2;
                            dir4 = dir3;
                            spans4 = spans5;
                            lineNum4 = lineNum2;
                            tabStops5 = tabStops4;
                            tl4 = tl3;
                            buf3 = buf2;
                            sp = sp2;
                            lbaseline4 = lbaseline2;
                            int i72 = ltop2;
                            textLength2 = textLength;
                            ltop4 = i72;
                        }
                        dir = dir4;
                        lineNum = lineNum4;
                        tabStops = tabStops5;
                        tl = tl4;
                        buf = buf3;
                        paint = paint3;
                        layout = layout3;
                        lbaseline = lbaseline4;
                        ltop = textLength2;
                        paraAlign2 = paraAlign;
                        spans = spans4;
                    }
                } else {
                    spans3 = spans7;
                    paraAlign = paraAlign2;
                }
                z = true;
                boolean isFirstParaLine2 = z;
                if (start2 >= spanEnd2) {
                }
                tabStopsIsInitialized = tabStopsIsInitialized2;
                spans4 = spans3;
                spanEnd = spanEnd2;
                length = spans4.length;
                useFirstLineMargin = isFirstParaLine2;
                n = 0;
                while (true) {
                    if (n >= length) {
                    }
                    n++;
                    useFirstLineMargin = useFirstLineMargin4;
                    sp3 = sp;
                }
                right = lbaseline5;
                n2 = 0;
                left = 0;
                while (true) {
                    n3 = n2;
                    if (n3 >= length) {
                    }
                    paint3 = paint2;
                    n2 = n4 + 1;
                    useFirstLineMargin2 = useFirstLineMargin3;
                    layout3 = layout2;
                    length = length2;
                    dir4 = dir3;
                    spans4 = spans5;
                    lineNum4 = lineNum2;
                    tabStops5 = tabStops4;
                    tl4 = tl3;
                    buf3 = buf2;
                    sp = sp2;
                    lbaseline4 = lbaseline2;
                    int i722 = ltop2;
                    textLength2 = textLength;
                    ltop4 = i722;
                }
                dir = dir4;
                lineNum = lineNum4;
                tabStops = tabStops5;
                tl = tl4;
                buf = buf3;
                paint = paint3;
                layout = layout3;
                lbaseline = lbaseline4;
                ltop = textLength2;
                paraAlign2 = paraAlign;
                spans = spans4;
            } else {
                start = start2;
                dir = dir4;
                ParagraphStyle[] paragraphStyleArr = spans7;
                lineNum = lineNum4;
                tabStops = tabStops5;
                tl = tl4;
                buf = buf3;
                paint = paint3;
                layout = layout3;
                lbaseline = lbaseline4;
                ltop = textLength2;
                tabStopsIsInitialized = tabStopsIsInitialized2;
                right = lbaseline5;
                spans = paragraphStyleArr;
                spanEnd = spanEnd2;
                left = 0;
            }
            int lineNum6 = lineNum;
            boolean hasTab = layout.getLineContainsTab(lineNum6);
            if (!hasTab || tabStopsIsInitialized) {
                tabStops2 = tabStops;
            } else {
                tabStops2 = tabStops;
                if (tabStops2 == null) {
                    tabStops2 = new TabStops(TAB_INCREMENT, spans);
                } else {
                    tabStops2.reset(TAB_INCREMENT, spans);
                }
                tabStopsIsInitialized = true;
            }
            TabStops tabStops6 = tabStops2;
            Alignment align = paraAlign2;
            if (align == Alignment.ALIGN_LEFT) {
                dir2 = dir;
                i = 1;
                align = dir2 == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
            } else {
                dir2 = dir;
                i = 1;
                if (align == Alignment.ALIGN_RIGHT) {
                    align = dir2 == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
                }
            }
            Alignment align2 = align;
            if (align2 == Alignment.ALIGN_NORMAL) {
                if (dir2 == i) {
                    indentWidth3 = layout.getIndentAdjust(lineNum6, Alignment.ALIGN_LEFT);
                    x = left + indentWidth3;
                } else {
                    indentWidth3 = -layout.getIndentAdjust(lineNum6, Alignment.ALIGN_RIGHT);
                    x = right - indentWidth3;
                }
                indentWidth = indentWidth3;
            } else {
                int max = (int) layout.getLineExtent(lineNum6, tabStops6, false);
                if (align2 == Alignment.ALIGN_OPPOSITE) {
                    if (dir2 == i) {
                        indentWidth2 = -layout.getIndentAdjust(lineNum6, Alignment.ALIGN_RIGHT);
                        i2 = (right - max) - indentWidth2;
                    } else {
                        indentWidth2 = layout.getIndentAdjust(lineNum6, Alignment.ALIGN_LEFT);
                        i2 = (left - max) + indentWidth2;
                    }
                    indentWidth = indentWidth2;
                    x = i2;
                } else {
                    int indentWidth4 = layout.getIndentAdjust(lineNum6, Alignment.ALIGN_CENTER);
                    x = (((right + left) - (max & (-2))) >> 1) + indentWidth4;
                    indentWidth = indentWidth4;
                }
            }
            int x2 = x;
            Directions directions = layout.getLineDirections(lineNum6);
            if (directions != DIRS_ALL_LEFT_TO_RIGHT || layout.mSpannedText || hasTab || justify) {
                spans2 = spans;
                int lbaseline6 = lbaseline;
                tabStops3 = tabStops6;
                tl.set(paint, buf, start, end, dir2, directions, hasTab, tabStops6, layout.getEllipsisStart(lineNum6), layout.getEllipsisStart(lineNum6) + layout.getEllipsisCount(lineNum6));
                if (justify) {
                    tl2 = tl;
                    tl2.justify((right - left) - indentWidth);
                } else {
                    tl2 = tl;
                }
                tl2.draw(canvas, x2, ltop, lbaseline6, lbottom);
            } else {
                spans2 = spans;
                canvas.drawText(buf, start, end, x2, lbaseline, paint);
                tabStops3 = tabStops6;
                tl2 = tl;
            }
            int x3 = lineNum6 + 1;
            i3 = firstLine;
            paint3 = paint;
            previousLineBottom2 = x3;
            layout3 = layout;
            tl4 = tl2;
            previousLineEnd = previousLineEnd2;
            lineNum3 = lbottom;
            tabStopsIsInitialized2 = tabStopsIsInitialized;
            spanEnd2 = spanEnd;
            tabStops5 = tabStops3;
            buf3 = buf;
            spans7 = spans2;
        }
    }

    @UnsupportedAppUsage
    public void drawBackground(Canvas canvas, Path highlight, Paint highlightPaint, int cursorOffsetVertical, int firstLine, int lastLine) {
        int i;
        int start;
        ParagraphStyle[] spans;
        int spanEnd;
        int spansLength;
        if (this.mSpannedText) {
            if (this.mLineBackgroundSpans == null) {
                this.mLineBackgroundSpans = new SpanSet<>(LineBackgroundSpan.class);
            }
            Spanned buffer = (Spanned) this.mText;
            int textLength = buffer.length();
            this.mLineBackgroundSpans.init(buffer, 0, textLength);
            if (this.mLineBackgroundSpans.numberOfSpans > 0) {
                int previousLineBottom = getLineTop(firstLine);
                int previousLineEnd = getLineStart(firstLine);
                ParagraphStyle[] spans2 = NO_PARA_SPANS;
                TextPaint paint = this.mPaint;
                int width = this.mWidth;
                int spanEnd2 = 0;
                int spanEnd3 = 0;
                int spansLength2 = previousLineEnd;
                int previousLineEnd2 = previousLineBottom;
                int i2 = firstLine;
                while (i2 <= lastLine) {
                    int start2 = spansLength2;
                    int end = getLineStart(i2 + 1);
                    int ltop = previousLineEnd2;
                    int lbottom = getLineTop(i2 + 1);
                    int previousLineBottom2 = getLineDescent(i2);
                    int lbaseline = lbottom - previousLineBottom2;
                    if (end >= spanEnd2) {
                        start = start2;
                        int spanEnd4 = this.mLineBackgroundSpans.getNextTransition(start, textLength);
                        int spansLength3 = 0;
                        if (start != end || start == 0) {
                            ParagraphStyle[] spans3 = spans2;
                            int j = 0;
                            while (true) {
                                i = i2;
                                if (j >= this.mLineBackgroundSpans.numberOfSpans) {
                                    break;
                                }
                                if (this.mLineBackgroundSpans.spanStarts[j] < end && this.mLineBackgroundSpans.spanEnds[j] > start) {
                                    ParagraphStyle[] spans4 = (ParagraphStyle[]) GrowingArrayUtils.append((LineBackgroundSpan[]) spans3, spansLength3, this.mLineBackgroundSpans.spans[j]);
                                    spansLength3++;
                                    spans3 = spans4;
                                }
                                j++;
                                i2 = i;
                            }
                            spanEnd = spanEnd4;
                            spans = spans3;
                        } else {
                            i = i2;
                            spanEnd = spanEnd4;
                            spans = spans2;
                        }
                        spansLength = spansLength3;
                    } else {
                        i = i2;
                        start = start2;
                        spans = spans2;
                        spanEnd = spanEnd2;
                        spansLength = spanEnd3;
                    }
                    int n = 0;
                    while (true) {
                        int n2 = n;
                        if (n2 < spansLength) {
                            LineBackgroundSpan lineBackgroundSpan = (LineBackgroundSpan) spans[n2];
                            int start3 = start;
                            int n3 = width;
                            int end2 = end;
                            lineBackgroundSpan.drawBackground(canvas, paint, 0, n3, ltop, lbaseline, lbottom, buffer, start3, end2, i);
                            n = n2 + 1;
                            end = end2;
                            start = start3;
                            spansLength = spansLength;
                            width = width;
                            paint = paint;
                            textLength = textLength;
                            buffer = buffer;
                        }
                    }
                    int spansLength4 = spansLength;
                    i2 = i + 1;
                    spans2 = spans;
                    spansLength2 = end;
                    previousLineEnd2 = lbottom;
                    spanEnd2 = spanEnd;
                    spanEnd3 = spansLength4;
                }
            }
            this.mLineBackgroundSpans.recycle();
        }
        if (highlight != null) {
            if (cursorOffsetVertical != 0) {
                canvas.translate(0.0f, cursorOffsetVertical);
            }
            canvas.drawPath(highlight, highlightPaint);
            if (cursorOffsetVertical != 0) {
                canvas.translate(0.0f, -cursorOffsetVertical);
            }
        }
    }

    @UnsupportedAppUsage
    public long getLineRangeForDraw(Canvas canvas) {
        synchronized (sTempRect) {
            if (!canvas.getClipBounds(sTempRect)) {
                return TextUtils.packRangeInLong(0, -1);
            }
            int dtop = sTempRect.top;
            int dbottom = sTempRect.bottom;
            int top = Math.max(dtop, 0);
            int bottom = Math.min(getLineTop(getLineCount()), dbottom);
            return top >= bottom ? TextUtils.packRangeInLong(0, -1) : TextUtils.packRangeInLong(getLineForVertical(top), getLineForVertical(bottom));
        }
    }

    private int getLineStartPos(int line, int left, int right) {
        int indentAdjust;
        Alignment align = getParagraphAlignment(line);
        int dir = getParagraphDirection(line);
        if (align == Alignment.ALIGN_LEFT) {
            align = dir == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
        } else if (align == Alignment.ALIGN_RIGHT) {
            align = dir == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
        }
        if (align == Alignment.ALIGN_NORMAL) {
            if (dir == 1) {
                int x = getIndentAdjust(line, Alignment.ALIGN_LEFT) + left;
                return x;
            }
            int x2 = getIndentAdjust(line, Alignment.ALIGN_RIGHT) + right;
            return x2;
        }
        TabStops tabStops = null;
        if (this.mSpannedText && getLineContainsTab(line)) {
            Spanned spanned = (Spanned) this.mText;
            int start = getLineStart(line);
            int spanEnd = spanned.nextSpanTransition(start, spanned.length(), TabStopSpan.class);
            TabStopSpan[] tabSpans = (TabStopSpan[]) getParagraphSpans(spanned, start, spanEnd, TabStopSpan.class);
            if (tabSpans.length > 0) {
                tabStops = new TabStops(TAB_INCREMENT, tabSpans);
            }
        }
        int max = (int) getLineExtent(line, tabStops, false);
        if (align == Alignment.ALIGN_OPPOSITE) {
            if (dir == 1) {
                indentAdjust = (right - max) + getIndentAdjust(line, Alignment.ALIGN_RIGHT);
            } else {
                indentAdjust = (left - max) + getIndentAdjust(line, Alignment.ALIGN_LEFT);
            }
            int x3 = indentAdjust;
            return x3;
        }
        int x4 = ((left + right) - (max & (-2))) >> (getIndentAdjust(line, Alignment.ALIGN_CENTER) + 1);
        return x4;
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
        if (wid < this.mWidth) {
            throw new RuntimeException("attempted to reduce Layout width");
        }
        this.mWidth = wid;
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
            int paraLevel = getParagraphDirection(line) == 1 ? 0 : 1;
            int runIndex = offset == lineStart ? 0 : runs.length - 2;
            return ((runs[runIndex + 1] >>> 26) & 63) != paraLevel;
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
        for (int i = 0; i < runs.length; i += 2) {
            int start = runs[i] + lineStart;
            int limit = (runs[i + 1] & RUN_LENGTH_MASK) + start;
            if (offset >= start && offset < limit) {
                int level = (runs[i + 1] >>> 26) & 63;
                return (level & 1) != 0;
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
        int i2 = getLineEnd(line);
        return TextUtils.packRangeInLong(0, i2);
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
        if (offset == lineStart) {
            levelBefore = getParagraphDirection(line) == 1 ? 0 : 1;
        } else {
            int offset2 = offset - 1;
            int i2 = 0;
            while (true) {
                if (i2 < runs.length) {
                    int start2 = runs[i2] + lineStart;
                    int limit2 = (runs[i2 + 1] & RUN_LENGTH_MASK) + start2;
                    if (limit2 > lineEnd) {
                        limit2 = lineEnd;
                    }
                    if (offset2 < start2 || offset2 >= limit2) {
                        i2 += 2;
                    } else {
                        levelBefore = (runs[i2 + 1] >>> 26) & 63;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return levelBefore < levelAt;
    }

    @VisibleForTesting
    public boolean[] primaryIsTrailingPreviousAllLineOffsets(int line) {
        byte b;
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        int[] runs = getLineDirections(line).mDirections;
        boolean[] trailing = new boolean[(lineEnd - lineStart) + 1];
        byte[] level = new byte[(lineEnd - lineStart) + 1];
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
                b = getParagraphDirection(line) == 1 ? (byte) 0 : (byte) 1;
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
        boolean trailing = primaryIsTrailingPrevious(offset);
        return getHorizontal(offset, trailing, clamped);
    }

    public float getSecondaryHorizontal(int offset) {
        return getSecondaryHorizontal(offset, false);
    }

    @UnsupportedAppUsage
    public float getSecondaryHorizontal(int offset, boolean clamped) {
        boolean trailing = primaryIsTrailingPrevious(offset);
        return getHorizontal(offset, !trailing, clamped);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getHorizontal(int offset, boolean primary) {
        return primary ? getPrimaryHorizontal(offset) : getSecondaryHorizontal(offset);
    }

    private float getHorizontal(int offset, boolean trailing, boolean clamped) {
        int line = getLineForOffset(offset);
        return getHorizontal(offset, trailing, line, clamped);
    }

    private float getHorizontal(int offset, boolean trailing, int line, boolean clamped) {
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
        tl.set(this.mPaint, this.mText, start, end, dir, directions, hasTab, tabStops, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        float wid = tl.measure(offset - start, trailing, null);
        TextLine.recycle(tl);
        if (clamped && wid > this.mWidth) {
            wid = this.mWidth;
        }
        int left = getParagraphLeft(line);
        int right = getParagraphRight(line);
        return getLineStartPos(line, left, right) + wid;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        tl.set(this.mPaint, this.mText, start, end, dir, directions, hasTab, tabStops, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        boolean[] trailings = primaryIsTrailingPreviousAllLineOffsets(line);
        if (!primary) {
            for (int offset = 0; offset < trailings.length; offset++) {
                trailings[offset] = !trailings[offset];
            }
        }
        float[] wid = tl.measureAllOffsets(trailings, null);
        TextLine.recycle(tl);
        if (clamped) {
            for (int offset2 = 0; offset2 < wid.length; offset2++) {
                if (wid[offset2] > this.mWidth) {
                    wid[offset2] = this.mWidth;
                }
            }
        }
        int left = getParagraphLeft(line);
        int right = getParagraphRight(line);
        int lineStartPos = getLineStartPos(line, left, right);
        float[] horizontal = new float[(end - start) + 1];
        for (int offset3 = 0; offset3 < horizontal.length; offset3++) {
            horizontal[offset3] = lineStartPos + wid[offset3];
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
                int left = getParagraphLeft(line);
                float max = getLineMax(line);
                return (float) Math.floor(left + ((this.mWidth - max) / 2.0f));
            case ALIGN_RIGHT:
                return this.mWidth - getLineMax(line);
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
                int right = getParagraphRight(line);
                float max = getLineMax(line);
                return (float) Math.ceil(right - ((this.mWidth - max) / 2.0f));
            case ALIGN_RIGHT:
                return this.mWidth;
            default:
                return getLineMax(line);
        }
    }

    public float getLineMax(int line) {
        float margin = getParagraphLeadingMargin(line);
        float signedExtent = getLineExtent(line, false);
        return (signedExtent >= 0.0f ? signedExtent : -signedExtent) + margin;
    }

    public float getLineWidth(int line) {
        float margin = getParagraphLeadingMargin(line);
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
        tl.set(paint, this.mText, start, end, dir, directions, hasTabs, tabStops2, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        if (isJustificationRequired(line)) {
            tl.justify(getJustifyWidth(line));
        }
        float width = tl.metrics(null);
        TextLine.recycle(tl);
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
        tl.set(paint, this.mText, start, end, dir, directions, hasTabs, tabStops, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        if (isJustificationRequired(line)) {
            tl.justify(getJustifyWidth(line));
        }
        float width = tl.metrics(null);
        TextLine.recycle(tl);
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
        Layout layout = this;
        int lineEndOffset = getLineEnd(line);
        int lineStartOffset = getLineStart(line);
        Directions dirs = getLineDirections(line);
        TextLine tl2 = TextLine.obtain();
        Directions dirs2 = dirs;
        tl2.set(layout.mPaint, layout.mText, lineStartOffset, lineEndOffset, getParagraphDirection(line), dirs, false, null, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        HorizontalMeasurementProvider horizontal = new HorizontalMeasurementProvider(line, primary);
        if (line == getLineCount() - 1) {
            max = lineEndOffset;
            tl = tl2;
        } else {
            tl = tl2;
            max = tl.getOffsetToLeftRightOf(lineEndOffset - lineStartOffset, !layout.isRtlCharAt(lineEndOffset - 1)) + lineStartOffset;
        }
        float bestdist = Math.abs(horizontal.get(lineStartOffset) - horiz);
        int guess = lineStartOffset;
        int best2 = 0;
        while (true) {
            Directions dirs3 = dirs2;
            if (best2 >= dirs3.mDirections.length) {
                break;
            }
            int here = dirs3.mDirections[best2] + lineStartOffset;
            int there = (dirs3.mDirections[best2 + 1] & RUN_LENGTH_MASK) + here;
            boolean isRtl = (dirs3.mDirections[best2 + 1] & 67108864) != 0;
            int swap = isRtl ? -1 : 1;
            if (there > max) {
                there = max;
            }
            int i = 1;
            int high = (there - 1) + 1;
            int low2 = (here + 1) - 1;
            while (true) {
                best = guess;
                low = low2;
                int best3 = high - low;
                if (best3 <= i) {
                    break;
                }
                int guess2 = (high + low) / 2;
                int adguess = layout.getOffsetAtStartOf(guess2);
                int swap2 = swap;
                if (horizontal.get(adguess) * swap2 >= swap2 * horiz) {
                    high = guess2;
                    low2 = low;
                } else {
                    low2 = guess2;
                }
                swap = swap2;
                guess = best;
                layout = this;
                i = 1;
            }
            if (low < here + 1) {
                low = here + 1;
            }
            if (low < there) {
                int aft = tl.getOffsetToLeftRightOf(low - lineStartOffset, isRtl) + lineStartOffset;
                int swap3 = tl.getOffsetToLeftRightOf(aft - lineStartOffset, !isRtl);
                int low3 = swap3 + lineStartOffset;
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
            best2 += 2;
            dirs2 = dirs3;
            layout = this;
        }
        int best4 = guess;
        if (Math.abs(horizontal.get(max) - horiz) <= bestdist) {
            best4 = max;
        }
        TextLine.recycle(tl);
        return best4;
    }

    /* loaded from: classes4.dex */
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
            Directions dirs = Layout.this.getLineDirections(this.mLine);
            if (dirs != Layout.DIRS_ALL_LEFT_TO_RIGHT) {
                this.mHorizontals = Layout.this.getLineHorizontals(this.mLine, false, this.mPrimary);
                this.mLineStartOffset = Layout.this.getLineStart(this.mLine);
            }
        }

        float get(int offset) {
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
            if (ch == '\n') {
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
        boolean toLeft2 = toLeft;
        int line = getLineForOffset(caret);
        int lineStart = getLineStart(line);
        int lineEnd = getLineEnd(line);
        int lineDir = getParagraphDirection(line);
        boolean lineChanged = false;
        boolean advance = toLeft2 == (lineDir == -1);
        if (advance) {
            if (caret == lineEnd) {
                if (line >= getLineCount() - 1) {
                    return caret;
                }
                lineChanged = true;
                line++;
            }
        } else if (caret == lineStart) {
            if (line <= 0) {
                return caret;
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
        tl.set(this.mPaint, this.mText, lineStart, lineEnd, lineDir, directions, false, null, getEllipsisStart(line), getEllipsisStart(line) + getEllipsisCount(line));
        int caret2 = tl.getOffsetToLeftRightOf(caret - lineStart, toLeft2) + lineStart;
        TextLine.recycle(tl);
        return caret2;
    }

    private int getOffsetAtStartOf(int offset) {
        char c1;
        if (offset == 0) {
            return 0;
        }
        CharSequence text = this.mText;
        char c = text.charAt(offset);
        if (c >= '\udc00' && c <= '\udfff' && (c1 = text.charAt(offset - 1)) >= '\ud800' && c1 <= '\udbff') {
            offset--;
        }
        if (this.mSpannedText) {
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
        int i = C25841.$SwitchMap$android$text$Layout$Alignment[getParagraphAlignment(line).ordinal()];
        return i != 1 ? i == 5 : getParagraphDirection(line) > 0;
    }

    public void getCursorPath(int point, Path dest, CharSequence editingBuffer) {
        dest.reset();
        int line = getLineForOffset(point);
        int top = getLineTop(line);
        int bottom = getLineBottomWithoutSpacing(line);
        boolean clamped = shouldClampCursor(line);
        float h1 = getPrimaryHorizontal(point, clamped) - 0.5f;
        int caps = TextKeyListener.getMetaState(editingBuffer, 1) | TextKeyListener.getMetaState(editingBuffer, 2048);
        int fn = TextKeyListener.getMetaState(editingBuffer, 2);
        int dist = 0;
        if (caps != 0 || fn != 0) {
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
        dest.moveTo(h1, top);
        dest.lineTo(h1, bottom);
        if (caps == 2) {
            dest.moveTo(h1, bottom);
            dest.lineTo(h1 - dist, bottom + dist);
            dest.lineTo(h1, bottom);
            dest.lineTo(dist + h1, bottom + dist);
        } else if (caps == 1) {
            dest.moveTo(h1, bottom);
            dest.lineTo(h1 - dist, bottom + dist);
            dest.moveTo(h1 - dist, (bottom + dist) - 0.5f);
            dest.lineTo(dist + h1, (bottom + dist) - 0.5f);
            dest.moveTo(dist + h1, bottom + dist);
            dest.lineTo(h1, bottom);
        }
        if (fn == 2) {
            dest.moveTo(h1, top);
            dest.lineTo(h1 - dist, top - dist);
            dest.lineTo(h1, top);
            dest.lineTo(dist + h1, top - dist);
        } else if (fn == 1) {
            dest.moveTo(h1, top);
            dest.lineTo(h1 - dist, top - dist);
            dest.moveTo(h1 - dist, (top - dist) + 0.5f);
            dest.lineTo(dist + h1, (top - dist) + 0.5f);
            dest.moveTo(dist + h1, top - dist);
            dest.lineTo(h1, top);
        }
    }

    private void addSelection(int line, int start, int end, int top, int bottom, SelectionRectangleConsumer consumer) {
        int st;
        int en;
        int layout;
        Layout layout2 = this;
        int i = line;
        int linestart = getLineStart(line);
        int lineend = getLineEnd(line);
        Directions dirs = getLineDirections(line);
        if (lineend > linestart && layout2.mText.charAt(lineend - 1) == '\n') {
            lineend--;
        }
        boolean z = false;
        int i2 = 0;
        while (i2 < dirs.mDirections.length) {
            int here = dirs.mDirections[i2] + linestart;
            int there = (dirs.mDirections[i2 + 1] & RUN_LENGTH_MASK) + here;
            if (there > lineend) {
                there = lineend;
            }
            if (start <= there && end >= here && (st = Math.max(start, here)) != (en = Math.min(end, there))) {
                float h1 = layout2.getHorizontal(st, z, i, z);
                float h2 = layout2.getHorizontal(en, true, i, z);
                float left = Math.min(h1, h2);
                float right = Math.max(h1, h2);
                if ((dirs.mDirections[i2 + 1] & 67108864) == 0) {
                    layout = 1;
                } else {
                    layout = 0;
                }
                consumer.accept(left, top, right, bottom, layout);
            }
            i2 += 2;
            layout2 = this;
            i = line;
            z = false;
        }
    }

    public void getSelectionPath(int start, int end, final Path dest) {
        dest.reset();
        getSelection(start, end, new SelectionRectangleConsumer() { // from class: android.text.-$$Lambda$Layout$MzjK2UE2G8VG0asK8_KWY3gHAmY
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i) {
                Path.this.addRect(f, f2, f3, f4, Path.Direction.CW);
            }
        });
    }

    public final void getSelection(int start, int end, SelectionRectangleConsumer consumer) {
        int i = start;
        int end2 = end;
        if (i == end2) {
            return;
        }
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
        float width = this.mWidth;
        addSelection(startline, start2, getLineEnd(startline), top, getLineBottom(startline), consumer);
        if (getParagraphDirection(startline) == -1) {
            consumer.accept(getLineLeft(startline), top, 0.0f, getLineBottom(startline), 0);
        } else {
            consumer.accept(getLineRight(startline), top, width, getLineBottom(startline), 1);
        }
        for (int i2 = startline + 1; i2 < endline; i2++) {
            int top2 = getLineTop(i2);
            int bottom2 = getLineBottom(i2);
            if (getParagraphDirection(i2) == -1) {
                consumer.accept(0.0f, top2, width, bottom2, 0);
            } else {
                consumer.accept(0.0f, top2, width, bottom2, 1);
            }
        }
        int top3 = getLineTop(endline);
        int bottom3 = getLineBottomWithoutSpacing(endline);
        addSelection(endline, getLineStart(endline), end3, top3, bottom3, consumer);
        if (getParagraphDirection(endline) == -1) {
            consumer.accept(width, top3, getLineRight(endline), bottom3, 0);
        } else {
            consumer.accept(0.0f, top3, getLineLeft(endline), bottom3, 1);
        }
    }

    public final Alignment getParagraphAlignment(int line) {
        Alignment align = this.mAlignment;
        if (this.mSpannedText) {
            Spanned sp = (Spanned) this.mText;
            AlignmentSpan[] spans = (AlignmentSpan[]) getParagraphSpans(sp, getLineStart(line), getLineEnd(line), AlignmentSpan.class);
            int spanLength = spans.length;
            if (spanLength > 0) {
                return spans[spanLength - 1].getAlignment();
            }
            return align;
        }
        return align;
    }

    public final int getParagraphLeft(int line) {
        int dir = getParagraphDirection(line);
        if (dir == -1 || !this.mSpannedText) {
            return 0;
        }
        return getParagraphLeadingMargin(line);
    }

    public final int getParagraphRight(int line) {
        int right = this.mWidth;
        int dir = getParagraphDirection(line);
        if (dir == 1 || !this.mSpannedText) {
            return right;
        }
        return right - getParagraphLeadingMargin(line);
    }

    private int getParagraphLeadingMargin(int line) {
        if (this.mSpannedText) {
            Spanned spanned = (Spanned) this.mText;
            int lineStart = getLineStart(line);
            int lineEnd = getLineEnd(line);
            int spanEnd = spanned.nextSpanTransition(lineStart, lineEnd, LeadingMarginSpan.class);
            LeadingMarginSpan[] spans = (LeadingMarginSpan[]) getParagraphSpans(spanned, lineStart, spanEnd, LeadingMarginSpan.class);
            if (spans.length == 0) {
                return 0;
            }
            int margin = 0;
            boolean useFirstLineMargin = lineStart == 0 || spanned.charAt(lineStart + (-1)) == '\n';
            boolean useFirstLineMargin2 = useFirstLineMargin;
            for (int i = 0; i < spans.length; i++) {
                if (spans[i] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                    int spStart = spanned.getSpanStart(spans[i]);
                    int spanLine = getLineForOffset(spStart);
                    int count = ((LeadingMarginSpan.LeadingMarginSpan2) spans[i]).getLeadingMarginLineCount();
                    useFirstLineMargin2 |= line < spanLine + count;
                }
            }
            for (LeadingMarginSpan span : spans) {
                margin += span.getLeadingMargin(useFirstLineMargin2);
            }
            return margin;
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006c, code lost:
        if ((r27 instanceof android.text.Spanned) == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006e, code lost:
        r4 = (android.text.Spanned) r27;
        r5 = r4.nextSpanTransition(r28, r29, android.text.style.TabStopSpan.class);
        r0 = (android.text.style.TabStopSpan[]) getParagraphSpans(r4, r28, r5, android.text.style.TabStopSpan.class);
        r23 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0084, code lost:
        if (r0.length <= 0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008f, code lost:
        r3 = new android.text.Layout.TabStops(android.text.Layout.TAB_INCREMENT, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0093, code lost:
        r24 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0096, code lost:
        r23 = true;
        r24 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static float measurePara(TextPaint paint, CharSequence text, int start, int end, TextDirectionHeuristic textDir) {
        MeasuredParagraph mt;
        boolean hasTabs;
        int margin;
        TabStops tabStops;
        boolean hasTabs2;
        TextLine tl = TextLine.obtain();
        try {
            mt = MeasuredParagraph.buildForBidi(text, start, end, textDir, null);
            try {
                char[] chars = mt.getChars();
                int len = chars.length;
                Directions directions = mt.getDirections(0, len);
                int dir = mt.getParagraphDir();
                boolean hasTabs3 = false;
                TabStops tabStops2 = null;
                if (text instanceof Spanned) {
                    try {
                        LeadingMarginSpan[] spans = (LeadingMarginSpan[]) getParagraphSpans((Spanned) text, start, end, LeadingMarginSpan.class);
                        int length = spans.length;
                        int margin2 = 0;
                        int margin3 = 0;
                        while (margin3 < length) {
                            LeadingMarginSpan lms = spans[margin3];
                            margin2 += lms.getLeadingMargin(true);
                            margin3++;
                            length = length;
                            hasTabs3 = hasTabs3;
                        }
                        hasTabs = hasTabs3;
                        margin = margin2;
                    } catch (Throwable th) {
                        th = th;
                        TextLine.recycle(tl);
                        if (mt != null) {
                            mt.recycle();
                        }
                        throw th;
                    }
                } else {
                    hasTabs = false;
                    margin = 0;
                }
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= len) {
                        tabStops = null;
                        hasTabs2 = hasTabs;
                        break;
                    } else if (chars[i2] == '\t') {
                        break;
                    } else {
                        i = i2 + 1;
                    }
                }
                int margin4 = margin;
                try {
                    tl.set(paint, text, start, end, dir, directions, hasTabs2, tabStops, 0, 0);
                    float abs = margin4 + Math.abs(tl.metrics(null));
                    TextLine.recycle(tl);
                    if (mt != null) {
                        mt.recycle();
                    }
                    return abs;
                } catch (Throwable th2) {
                    th = th2;
                    mt = mt;
                    TextLine.recycle(tl);
                    if (mt != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            mt = null;
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    /* loaded from: classes4.dex */
    public static class TabStops {
        private float mIncrement;
        private int mNumStops;
        private float[] mStops;

        public TabStops(float increment, Object[] spans) {
            reset(increment, spans);
        }

        void reset(float increment, Object[] spans) {
            this.mIncrement = increment;
            int ns = 0;
            if (spans != null) {
                float[] stops = this.mStops;
                float[] stops2 = stops;
                int ns2 = 0;
                for (Object o : spans) {
                    if (o instanceof TabStopSpan) {
                        if (stops2 == null) {
                            stops2 = new float[10];
                        } else if (ns2 == stops2.length) {
                            float[] nstops = new float[ns2 * 2];
                            for (int i = 0; i < ns2; i++) {
                                nstops[i] = stops2[i];
                            }
                            stops2 = nstops;
                        }
                        stops2[ns2] = ((TabStopSpan) o).getTabStop();
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

        float nextTab(float h) {
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
            return ((int) ((h + inc) / inc)) * inc;
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
                    if (where < nh && where > h) {
                        nh = where;
                    }
                }
            }
            if (nh != Float.MAX_VALUE) {
                return nh;
            }
        }
        return ((int) ((h + TAB_INCREMENT) / TAB_INCREMENT)) * TAB_INCREMENT;
    }

    protected final boolean isSpanned() {
        return this.mSpannedText;
    }

    static <T> T[] getParagraphSpans(Spanned text, int start, int end, Class<T> type) {
        if (start == end && start > 0) {
            return (T[]) ArrayUtils.emptyArray(type);
        }
        if (text instanceof SpannableStringBuilder) {
            return (T[]) ((SpannableStringBuilder) text).getSpans(start, end, type, false);
        }
        return (T[]) text.getSpans(start, end, type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ellipsize(int start, int end, int line, char[] dest, int destoff, TextUtils.TruncateAt method) {
        char c;
        int ellipsisCount = getEllipsisCount(line);
        if (ellipsisCount == 0) {
            return;
        }
        int ellipsisStart = getEllipsisStart(line);
        int lineStart = getLineStart(line);
        String ellipsisString = TextUtils.getEllipsisString(method);
        int ellipsisStringLen = ellipsisString.length();
        boolean useEllipsisString = ellipsisCount >= ellipsisStringLen;
        for (int i = 0; i < ellipsisCount; i++) {
            if (useEllipsisString && i < ellipsisStringLen) {
                c = ellipsisString.charAt(i);
            } else {
                c = '\ufeff';
            }
            int a = i + ellipsisStart + lineStart;
            if (start <= a && a < end) {
                dest[(destoff + a) - start] = c;
            }
        }
    }

    /* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    static class Ellipsizer implements CharSequence, GetChars {
        Layout mLayout;
        TextUtils.TruncateAt mMethod;
        CharSequence mText;
        int mWidth;

        public Ellipsizer(CharSequence s) {
            this.mText = s;
        }

        @Override // java.lang.CharSequence
        public char charAt(int off) {
            char[] buf = TextUtils.obtain(1);
            getChars(off, off + 1, buf, 0);
            char ret = buf[0];
            TextUtils.recycle(buf);
            return ret;
        }

        @Override // android.text.GetChars
        public void getChars(int start, int end, char[] dest, int destoff) {
            int line1 = this.mLayout.getLineForOffset(start);
            int line2 = this.mLayout.getLineForOffset(end);
            TextUtils.getChars(this.mText, start, end, dest, destoff);
            for (int i = line1; i <= line2; i++) {
                this.mLayout.ellipsize(start, end, i, dest, destoff, this.mMethod);
            }
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mText.length();
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int start, int end) {
            char[] s = new char[end - start];
            getChars(start, end, s, 0);
            return new String(s);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            char[] s = new char[length()];
            getChars(0, length(), s, 0);
            return new String(s);
        }
    }

    /* loaded from: classes4.dex */
    static class SpannedEllipsizer extends Ellipsizer implements Spanned {
        private Spanned mSpanned;

        public SpannedEllipsizer(CharSequence display) {
            super(display);
            this.mSpanned = (Spanned) display;
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(int start, int end, Class<T> type) {
            return (T[]) this.mSpanned.getSpans(start, end, type);
        }

        @Override // android.text.Spanned
        public int getSpanStart(Object tag) {
            return this.mSpanned.getSpanStart(tag);
        }

        @Override // android.text.Spanned
        public int getSpanEnd(Object tag) {
            return this.mSpanned.getSpanEnd(tag);
        }

        @Override // android.text.Spanned
        public int getSpanFlags(Object tag) {
            return this.mSpanned.getSpanFlags(tag);
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int start, int limit, Class type) {
            return this.mSpanned.nextSpanTransition(start, limit, type);
        }

        @Override // android.text.Layout.Ellipsizer, java.lang.CharSequence
        public CharSequence subSequence(int start, int end) {
            char[] s = new char[end - start];
            getChars(start, end, s, 0);
            SpannableString ss = new SpannableString(new String(s));
            TextUtils.copySpansFrom(this.mSpanned, start, end, Object.class, ss, 0);
            return ss;
        }
    }
}
