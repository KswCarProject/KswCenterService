package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.text.style.UpdateLayout;
import android.util.ArraySet;
import android.util.Pools;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.ref.WeakReference;

public class DynamicLayout extends Layout {
    private static final int BLOCK_MINIMUM_CHARACTER_LENGTH = 400;
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int ELLIPSIS_UNDEFINED = Integer.MIN_VALUE;
    private static final int EXTRA = 3;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    public static final int INVALID_BLOCK_INDEX = -1;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM = 4;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM_MASK = 256;
    private static final int PRIORITY = 128;
    private static final int START = 0;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final int TAB_MASK = 536870912;
    private static final int TOP = 1;
    private static StaticLayout.Builder sBuilder = null;
    private static final Object[] sLock = new Object[0];
    @UnsupportedAppUsage
    private static StaticLayout sStaticLayout = null;
    private CharSequence mBase;
    private int[] mBlockEndLines;
    private int[] mBlockIndices;
    private ArraySet<Integer> mBlocksAlwaysNeedToBeRedrawn;
    private int mBottomPadding;
    private int mBreakStrategy;
    private CharSequence mDisplay;
    private boolean mEllipsize;
    private TextUtils.TruncateAt mEllipsizeAt;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mIndexFirstChangedBlock;
    private PackedIntVector mInts;
    private int mJustificationMode;
    private int mNumberOfBlocks;
    private PackedObjectVector<Layout.Directions> mObjects;
    private Rect mTempRect;
    private int mTopPadding;
    private ChangeWatcher mWatcher;

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool<>(3);
        /* access modifiers changed from: private */
        public Layout.Alignment mAlignment;
        /* access modifiers changed from: private */
        public CharSequence mBase;
        /* access modifiers changed from: private */
        public int mBreakStrategy;
        /* access modifiers changed from: private */
        public CharSequence mDisplay;
        /* access modifiers changed from: private */
        public TextUtils.TruncateAt mEllipsize;
        /* access modifiers changed from: private */
        public int mEllipsizedWidth;
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
        public TextPaint mPaint;
        /* access modifiers changed from: private */
        public float mSpacingAdd;
        /* access modifiers changed from: private */
        public float mSpacingMult;
        /* access modifiers changed from: private */
        public TextDirectionHeuristic mTextDir;
        /* access modifiers changed from: private */
        public int mWidth;

        private Builder() {
        }

        public static Builder obtain(CharSequence base, TextPaint paint, int width) {
            Builder b = sPool.acquire();
            if (b == null) {
                b = new Builder();
            }
            b.mBase = base;
            b.mDisplay = base;
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
            b.mBreakStrategy = 0;
            b.mHyphenationFrequency = 0;
            b.mJustificationMode = 0;
            return b;
        }

        /* access modifiers changed from: private */
        public static void recycle(Builder b) {
            b.mBase = null;
            b.mDisplay = null;
            b.mPaint = null;
            sPool.release(b);
        }

        public Builder setDisplayText(CharSequence display) {
            this.mDisplay = display;
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

        public Builder setBreakStrategy(int breakStrategy) {
            this.mBreakStrategy = breakStrategy;
            return this;
        }

        public Builder setHyphenationFrequency(int hyphenationFrequency) {
            this.mHyphenationFrequency = hyphenationFrequency;
            return this;
        }

        public Builder setJustificationMode(int justificationMode) {
            this.mJustificationMode = justificationMode;
            return this;
        }

        public DynamicLayout build() {
            DynamicLayout result = new DynamicLayout(this);
            recycle(this);
            return result;
        }
    }

    @Deprecated
    public DynamicLayout(CharSequence base, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(base, base, paint, width, align, spacingmult, spacingadd, includepad);
    }

    @Deprecated
    public DynamicLayout(CharSequence base, CharSequence display, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        this(base, display, paint, width, align, spacingmult, spacingadd, includepad, (TextUtils.TruncateAt) null, 0);
    }

    @Deprecated
    public DynamicLayout(CharSequence base, CharSequence display, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth) {
        this(base, display, paint, width, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingmult, spacingadd, includepad, 0, 0, 0, ellipsize, ellipsizedWidth);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    @android.annotation.UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DynamicLayout(java.lang.CharSequence r17, java.lang.CharSequence r18, android.text.TextPaint r19, int r20, android.text.Layout.Alignment r21, android.text.TextDirectionHeuristic r22, float r23, float r24, boolean r25, int r26, int r27, int r28, android.text.TextUtils.TruncateAt r29, int r30) {
        /*
            r16 = this;
            r8 = r16
            r9 = r18
            r10 = r29
            java.lang.CharSequence r1 = createEllipsizer(r10, r9)
            r0 = r16
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r23
            r7 = r24
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r8.mTempRect = r0
            r0 = r17
            r1 = r19
            r2 = r20
            android.text.DynamicLayout$Builder r3 = android.text.DynamicLayout.Builder.obtain(r0, r1, r2)
            android.text.DynamicLayout$Builder r3 = r3.setAlignment(r4)
            android.text.DynamicLayout$Builder r3 = r3.setTextDirection(r5)
            android.text.DynamicLayout$Builder r3 = r3.setLineSpacing(r7, r6)
            r11 = r30
            android.text.DynamicLayout$Builder r3 = r3.setEllipsizedWidth(r11)
            android.text.DynamicLayout$Builder r3 = r3.setEllipsize(r10)
            r8.mDisplay = r9
            r12 = r25
            r8.mIncludePad = r12
            r13 = r26
            r8.mBreakStrategy = r13
            r14 = r28
            r8.mJustificationMode = r14
            r15 = r27
            r8.mHyphenationFrequency = r15
            r8.generate(r3)
            android.text.DynamicLayout.Builder.recycle(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.DynamicLayout.<init>(java.lang.CharSequence, java.lang.CharSequence, android.text.TextPaint, int, android.text.Layout$Alignment, android.text.TextDirectionHeuristic, float, float, boolean, int, int, int, android.text.TextUtils$TruncateAt, int):void");
    }

    private DynamicLayout(Builder b) {
        super(createEllipsizer(b.mEllipsize, b.mDisplay), b.mPaint, b.mWidth, b.mAlignment, b.mTextDir, b.mSpacingMult, b.mSpacingAdd);
        this.mTempRect = new Rect();
        this.mDisplay = b.mDisplay;
        this.mIncludePad = b.mIncludePad;
        this.mBreakStrategy = b.mBreakStrategy;
        this.mJustificationMode = b.mJustificationMode;
        this.mHyphenationFrequency = b.mHyphenationFrequency;
        generate(b);
    }

    private static CharSequence createEllipsizer(TextUtils.TruncateAt ellipsize, CharSequence display) {
        if (ellipsize == null) {
            return display;
        }
        if (display instanceof Spanned) {
            return new Layout.SpannedEllipsizer(display);
        }
        return new Layout.Ellipsizer(display);
    }

    private void generate(Builder b) {
        int[] start;
        this.mBase = b.mBase;
        this.mFallbackLineSpacing = b.mFallbackLineSpacing;
        if (b.mEllipsize != null) {
            this.mInts = new PackedIntVector(7);
            this.mEllipsizedWidth = b.mEllipsizedWidth;
            this.mEllipsizeAt = b.mEllipsize;
            Layout.Ellipsizer e = (Layout.Ellipsizer) getText();
            e.mLayout = this;
            e.mWidth = b.mEllipsizedWidth;
            e.mMethod = b.mEllipsize;
            this.mEllipsize = true;
        } else {
            this.mInts = new PackedIntVector(5);
            this.mEllipsizedWidth = b.mWidth;
            this.mEllipsizeAt = null;
        }
        this.mObjects = new PackedObjectVector<>(1);
        if (b.mEllipsize != null) {
            start = new int[7];
            start[5] = Integer.MIN_VALUE;
        } else {
            start = new int[5];
        }
        Layout.Directions[] dirs = {DIRS_ALL_LEFT_TO_RIGHT};
        Paint.FontMetricsInt fm = b.mFontMetricsInt;
        b.mPaint.getFontMetricsInt(fm);
        int asc = fm.ascent;
        int desc = fm.descent;
        start[0] = 1073741824;
        start[1] = 0;
        start[2] = desc;
        this.mInts.insertAt(0, start);
        start[1] = desc - asc;
        this.mInts.insertAt(1, start);
        this.mObjects.insertAt(0, dirs);
        int baseLength = this.mBase.length();
        reflow(this.mBase, 0, 0, baseLength);
        if (this.mBase instanceof Spannable) {
            if (this.mWatcher == null) {
                this.mWatcher = new ChangeWatcher(this);
            }
            Spannable sp = (Spannable) this.mBase;
            ChangeWatcher[] spans = (ChangeWatcher[]) sp.getSpans(0, baseLength, ChangeWatcher.class);
            for (ChangeWatcher removeSpan : spans) {
                sp.removeSpan(removeSpan);
            }
            sp.setSpan(this.mWatcher, 0, baseLength, 8388626);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b4, code lost:
        if (r0 != null) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b6, code lost:
        r5 = android.text.StaticLayout.Builder.obtain(r2, r9, r9 + r10, getPaint(), getWidth());
        r14 = new android.text.StaticLayout((java.lang.CharSequence) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d1, code lost:
        r14 = r0;
        r5 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d4, code lost:
        r21 = r6;
        r22 = r8;
        r0 = r5.setText(r2, r9, r9 + r10).setPaint(getPaint()).setWidth(getWidth()).setTextDirection(getTextDirectionHeuristic()).setLineSpacing(getSpacingAdd(), getSpacingMultiplier()).setUseLineSpacingFromFallbacks(r1.mFallbackLineSpacing).setEllipsizedWidth(r1.mEllipsizedWidth).setEllipsize(r1.mEllipsizeAt).setBreakStrategy(r1.mBreakStrategy).setHyphenationFrequency(r1.mHyphenationFrequency).setJustificationMode(r1.mJustificationMode);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0126, code lost:
        if (r13 != false) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0128, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x012a, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x012c, code lost:
        r0.setAddLastLineLineSpacing(r6);
        r14.generate(r5, false, true);
        r0 = r14.getLineCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x013a, code lost:
        if ((r9 + r10) == r4) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0144, code lost:
        if (r14.getLineStart(r0 - 1) != (r9 + r10)) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0146, code lost:
        r0 = r0 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0148, code lost:
        r6 = r0;
        r1.mInts.deleteAt(r3, r11 - r3);
        r1.mObjects.deleteAt(r3, r11 - r3);
        r0 = r14.getLineTop(r6);
        r8 = 0;
        r18 = 0;
        r23 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0162, code lost:
        if (r1.mIncludePad == 0) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0164, code lost:
        if (r3 != 0) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0166, code lost:
        r8 = r14.getTopPadding();
        r1.mTopPadding = r8;
        r0 = r0 - r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x016f, code lost:
        if (r1.mIncludePad == false) goto L_0x017c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0171, code lost:
        if (r13 == false) goto L_0x017c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0173, code lost:
        r4 = r14.getBottomPadding();
        r1.mBottomPadding = r4;
        r0 = r0 + r4;
        r18 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x017c, code lost:
        r4 = r0;
        r24 = r13;
        r25 = r15;
        r1.mInts.adjustValuesBelow(r3, 0, r10 - r15);
        r1.mInts.adjustValuesBelow(r3, 1, (r7 - r12) + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0195, code lost:
        if (r1.mEllipsize == false) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0197, code lost:
        r0 = new int[7];
        r0[5] = Integer.MIN_VALUE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x019f, code lost:
        r0 = new int[5];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01a1, code lost:
        r15 = r0;
        r13 = new android.text.Layout.Directions[1];
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01a6, code lost:
        if (r0 >= r6) goto L_0x025b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01a8, code lost:
        r27 = r4;
        r4 = r14.getLineStart(r0);
        r15[0] = r4;
        r15[0] = r15[0] | (r14.getParagraphDirection(r0) << 30);
        r20 = r15[0];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01c4, code lost:
        if (r14.getLineContainsTab(r0) == false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01c6, code lost:
        r28 = 536870912;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01c9, code lost:
        r28 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01cb, code lost:
        r15[0] = r20 | r28;
        r20 = r14.getLineTop(r0) + r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01d5, code lost:
        if (r0 <= 0) goto L_0x01d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01d7, code lost:
        r20 = r20 - r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01d9, code lost:
        r15[1] = r20;
        r28 = r14.getLineDescent(r0);
        r29 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01e5, code lost:
        if (r0 != (r6 - 1)) goto L_0x01e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01e7, code lost:
        r28 = r28 + r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01e9, code lost:
        r15[2] = r28;
        r15[3] = r14.getLineExtra(r0);
        r13[0] = r14.getLineDirections(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01fd, code lost:
        if (r0 != (r6 - 1)) goto L_0x0202;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ff, code lost:
        r7 = r9 + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0202, code lost:
        r7 = r14.getLineStart(r0 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0208, code lost:
        r31 = r8;
        r32 = r9;
        r15[4] = android.text.StaticLayout.packHyphenEdit(r14.getStartHyphenEdit(r0), r14.getEndHyphenEdit(r0));
        r8 = r15[4];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0222, code lost:
        if (contentMayProtrudeFromLineTopOrBottom(r2, r4, r7) == false) goto L_0x0227;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0224, code lost:
        r30 = 256;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0227, code lost:
        r30 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0229, code lost:
        r15[4] = r8 | r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x022f, code lost:
        if (r1.mEllipsize == false) goto L_0x0240;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0231, code lost:
        r15[5] = r14.getEllipsisStart(r0);
        r15[6] = r14.getEllipsisCount(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0241, code lost:
        r1.mInts.insertAt(r3 + r0, r15);
        r1.mObjects.insertAt(r3 + r0, r13);
        r0 = r0 + 1;
        r4 = r27;
        r7 = r29;
        r8 = r31;
        r9 = r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x025b, code lost:
        r27 = r4;
        r29 = r7;
        r31 = r8;
        r32 = r9;
        updateBlocks(r3, r11 - 1, r6);
        r5.finish();
        r4 = sLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x026d, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        sStaticLayout = r14;
        sBuilder = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0272, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0273, code lost:
        return;
     */
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void reflow(java.lang.CharSequence r34, int r35, int r36, int r37) {
        /*
            r33 = this;
            r1 = r33
            java.lang.CharSequence r2 = r1.mBase
            r3 = r34
            if (r3 == r2) goto L_0x0009
            return
        L_0x0009:
            java.lang.CharSequence r2 = r1.mDisplay
            int r4 = r2.length()
            int r5 = r35 + -1
            r6 = 10
            int r5 = android.text.TextUtils.lastIndexOf(r2, r6, r5)
            r7 = 1
            if (r5 >= 0) goto L_0x001c
            r5 = 0
            goto L_0x001d
        L_0x001c:
            int r5 = r5 + r7
        L_0x001d:
            int r8 = r35 - r5
            int r9 = r36 + r8
            int r10 = r37 + r8
            int r0 = r35 - r8
            int r8 = r0 + r10
            int r6 = android.text.TextUtils.indexOf((java.lang.CharSequence) r2, (char) r6, (int) r8)
            if (r6 >= 0) goto L_0x002f
            r6 = r4
            goto L_0x0030
        L_0x002f:
            int r6 = r6 + r7
        L_0x0030:
            int r8 = r0 + r10
            int r8 = r6 - r8
            int r9 = r9 + r8
            int r10 = r10 + r8
            boolean r11 = r2 instanceof android.text.Spanned
            if (r11 == 0) goto L_0x007e
            r11 = r2
            android.text.Spanned r11 = (android.text.Spanned) r11
        L_0x003d:
            r13 = 0
            int r14 = r0 + r10
            java.lang.Class<android.text.style.WrapTogetherSpan> r15 = android.text.style.WrapTogetherSpan.class
            java.lang.Object[] r14 = r11.getSpans(r0, r14, r15)
            r15 = r9
            r9 = r0
            r0 = 0
        L_0x0049:
            int r7 = r14.length
            if (r0 >= r7) goto L_0x0075
            r7 = r14[r0]
            int r7 = r11.getSpanStart(r7)
            r12 = r14[r0]
            int r12 = r11.getSpanEnd(r12)
            if (r7 >= r9) goto L_0x0063
            r13 = 1
            int r18 = r9 - r7
            int r15 = r15 + r18
            int r10 = r10 + r18
            int r9 = r9 - r18
        L_0x0063:
            int r3 = r9 + r10
            if (r12 <= r3) goto L_0x006f
            r3 = 1
            int r13 = r9 + r10
            int r13 = r12 - r13
            int r15 = r15 + r13
            int r10 = r10 + r13
            r13 = r3
        L_0x006f:
            int r0 = r0 + 1
            r3 = r34
            r7 = 1
            goto L_0x0049
        L_0x0075:
            if (r13 != 0) goto L_0x0078
            goto L_0x0080
        L_0x0078:
            r0 = r9
            r9 = r15
            r3 = r34
            r7 = 1
            goto L_0x003d
        L_0x007e:
            r15 = r9
            r9 = r0
        L_0x0080:
            int r3 = r1.getLineForOffset(r9)
            int r7 = r1.getLineTop(r3)
            int r0 = r9 + r15
            int r0 = r1.getLineForOffset(r0)
            int r11 = r9 + r10
            if (r11 != r4) goto L_0x0096
            int r0 = r33.getLineCount()
        L_0x0096:
            r11 = r0
            int r12 = r1.getLineTop(r11)
            int r0 = r33.getLineCount()
            if (r11 != r0) goto L_0x00a3
            r0 = 1
            goto L_0x00a4
        L_0x00a3:
            r0 = 0
        L_0x00a4:
            r13 = r0
            java.lang.Object[] r14 = sLock
            monitor-enter(r14)
            android.text.StaticLayout r0 = sStaticLayout     // Catch:{ all -> 0x0287 }
            android.text.StaticLayout$Builder r18 = sBuilder     // Catch:{ all -> 0x0287 }
            r19 = r5
            r5 = 0
            sStaticLayout = r5     // Catch:{ all -> 0x0277 }
            sBuilder = r5     // Catch:{ all -> 0x0277 }
            monitor-exit(r14)     // Catch:{ all -> 0x0277 }
            if (r0 != 0) goto L_0x00d1
            android.text.StaticLayout r14 = new android.text.StaticLayout
            r14.<init>((java.lang.CharSequence) r5)
            r0 = r14
            int r5 = r9 + r10
            android.text.TextPaint r14 = r33.getPaint()
            r20 = r0
            int r0 = r33.getWidth()
            android.text.StaticLayout$Builder r18 = android.text.StaticLayout.Builder.obtain(r2, r9, r5, r14, r0)
            r5 = r18
            r14 = r20
            goto L_0x00d4
        L_0x00d1:
            r14 = r0
            r5 = r18
        L_0x00d4:
            int r0 = r9 + r10
            android.text.StaticLayout$Builder r0 = r5.setText(r2, r9, r0)
            r21 = r6
            android.text.TextPaint r6 = r33.getPaint()
            android.text.StaticLayout$Builder r0 = r0.setPaint(r6)
            int r6 = r33.getWidth()
            android.text.StaticLayout$Builder r0 = r0.setWidth(r6)
            android.text.TextDirectionHeuristic r6 = r33.getTextDirectionHeuristic()
            android.text.StaticLayout$Builder r0 = r0.setTextDirection(r6)
            float r6 = r33.getSpacingAdd()
            r22 = r8
            float r8 = r33.getSpacingMultiplier()
            android.text.StaticLayout$Builder r0 = r0.setLineSpacing(r6, r8)
            boolean r6 = r1.mFallbackLineSpacing
            android.text.StaticLayout$Builder r0 = r0.setUseLineSpacingFromFallbacks(r6)
            int r6 = r1.mEllipsizedWidth
            android.text.StaticLayout$Builder r0 = r0.setEllipsizedWidth(r6)
            android.text.TextUtils$TruncateAt r6 = r1.mEllipsizeAt
            android.text.StaticLayout$Builder r0 = r0.setEllipsize(r6)
            int r6 = r1.mBreakStrategy
            android.text.StaticLayout$Builder r0 = r0.setBreakStrategy(r6)
            int r6 = r1.mHyphenationFrequency
            android.text.StaticLayout$Builder r0 = r0.setHyphenationFrequency(r6)
            int r6 = r1.mJustificationMode
            android.text.StaticLayout$Builder r0 = r0.setJustificationMode(r6)
            if (r13 != 0) goto L_0x012a
            r6 = 1
            goto L_0x012c
        L_0x012a:
            r6 = 0
        L_0x012c:
            r0.setAddLastLineLineSpacing(r6)
            r0 = 1
            r6 = 0
            r14.generate(r5, r6, r0)
            int r0 = r14.getLineCount()
            int r6 = r9 + r10
            if (r6 == r4) goto L_0x0148
            int r6 = r0 + -1
            int r6 = r14.getLineStart(r6)
            int r8 = r9 + r10
            if (r6 != r8) goto L_0x0148
            int r0 = r0 + -1
        L_0x0148:
            r6 = r0
            android.text.PackedIntVector r0 = r1.mInts
            int r8 = r11 - r3
            r0.deleteAt(r3, r8)
            android.text.PackedObjectVector<android.text.Layout$Directions> r0 = r1.mObjects
            int r8 = r11 - r3
            r0.deleteAt(r3, r8)
            int r0 = r14.getLineTop(r6)
            r8 = 0
            r18 = 0
            r23 = r4
            boolean r4 = r1.mIncludePad
            if (r4 == 0) goto L_0x016d
            if (r3 != 0) goto L_0x016d
            int r8 = r14.getTopPadding()
            r1.mTopPadding = r8
            int r0 = r0 - r8
        L_0x016d:
            boolean r4 = r1.mIncludePad
            if (r4 == 0) goto L_0x017c
            if (r13 == 0) goto L_0x017c
            int r4 = r14.getBottomPadding()
            r1.mBottomPadding = r4
            int r0 = r0 + r4
            r18 = r4
        L_0x017c:
            r4 = r0
            android.text.PackedIntVector r0 = r1.mInts
            r24 = r13
            int r13 = r10 - r15
            r25 = r15
            r15 = 0
            r0.adjustValuesBelow(r3, r15, r13)
            android.text.PackedIntVector r0 = r1.mInts
            int r13 = r7 - r12
            int r13 = r13 + r4
            r15 = 1
            r0.adjustValuesBelow(r3, r15, r13)
            boolean r0 = r1.mEllipsize
            r13 = 5
            if (r0 == 0) goto L_0x019f
            r0 = 7
            int[] r0 = new int[r0]
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            r0[r13] = r15
            goto L_0x01a1
        L_0x019f:
            int[] r0 = new int[r13]
        L_0x01a1:
            r15 = r0
            r0 = 1
            android.text.Layout$Directions[] r13 = new android.text.Layout.Directions[r0]
            r0 = 0
        L_0x01a6:
            if (r0 >= r6) goto L_0x025b
            r27 = r4
            int r4 = r14.getLineStart(r0)
            r17 = 0
            r15[r17] = r4
            r20 = r15[r17]
            int r28 = r14.getParagraphDirection(r0)
            int r28 = r28 << 30
            r20 = r20 | r28
            r15[r17] = r20
            r20 = r15[r17]
            boolean r28 = r14.getLineContainsTab(r0)
            if (r28 == 0) goto L_0x01c9
            r28 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x01cb
        L_0x01c9:
            r28 = r17
        L_0x01cb:
            r20 = r20 | r28
            r15[r17] = r20
            int r20 = r14.getLineTop(r0)
            int r20 = r20 + r7
            if (r0 <= 0) goto L_0x01d9
            int r20 = r20 - r8
        L_0x01d9:
            r16 = 1
            r15[r16] = r20
            int r28 = r14.getLineDescent(r0)
            r29 = r7
            int r7 = r6 + -1
            if (r0 != r7) goto L_0x01e9
            int r28 = r28 + r18
        L_0x01e9:
            r7 = 2
            r15[r7] = r28
            r7 = 3
            int r30 = r14.getLineExtra(r0)
            r15[r7] = r30
            android.text.Layout$Directions r7 = r14.getLineDirections(r0)
            r17 = 0
            r13[r17] = r7
            int r7 = r6 + -1
            if (r0 != r7) goto L_0x0202
            int r7 = r9 + r10
            goto L_0x0208
        L_0x0202:
            int r7 = r0 + 1
            int r7 = r14.getLineStart(r7)
        L_0x0208:
            r31 = r8
            int r8 = r14.getStartHyphenEdit(r0)
            r32 = r9
            int r9 = r14.getEndHyphenEdit(r0)
            int r8 = android.text.StaticLayout.packHyphenEdit(r8, r9)
            r9 = 4
            r15[r9] = r8
            r8 = r15[r9]
            boolean r30 = r1.contentMayProtrudeFromLineTopOrBottom(r2, r4, r7)
            if (r30 == 0) goto L_0x0227
            r30 = 256(0x100, float:3.59E-43)
            goto L_0x0229
        L_0x0227:
            r30 = r17
        L_0x0229:
            r8 = r8 | r30
            r15[r9] = r8
            boolean r8 = r1.mEllipsize
            if (r8 == 0) goto L_0x0240
            int r8 = r14.getEllipsisStart(r0)
            r9 = 5
            r15[r9] = r8
            r8 = 6
            int r26 = r14.getEllipsisCount(r0)
            r15[r8] = r26
            goto L_0x0241
        L_0x0240:
            r9 = 5
        L_0x0241:
            android.text.PackedIntVector r8 = r1.mInts
            int r9 = r3 + r0
            r8.insertAt(r9, r15)
            android.text.PackedObjectVector<android.text.Layout$Directions> r8 = r1.mObjects
            int r9 = r3 + r0
            r8.insertAt(r9, r13)
            int r0 = r0 + 1
            r4 = r27
            r7 = r29
            r8 = r31
            r9 = r32
            goto L_0x01a6
        L_0x025b:
            r27 = r4
            r29 = r7
            r31 = r8
            r32 = r9
            int r0 = r11 + -1
            r1.updateBlocks(r3, r0, r6)
            r5.finish()
            java.lang.Object[] r4 = sLock
            monitor-enter(r4)
            sStaticLayout = r14     // Catch:{ all -> 0x0274 }
            sBuilder = r5     // Catch:{ all -> 0x0274 }
            monitor-exit(r4)     // Catch:{ all -> 0x0274 }
            return
        L_0x0274:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0274 }
            throw r0
        L_0x0277:
            r0 = move-exception
            r23 = r4
            r21 = r6
            r29 = r7
            r22 = r8
            r32 = r9
            r24 = r13
            r25 = r15
            goto L_0x0298
        L_0x0287:
            r0 = move-exception
            r23 = r4
            r19 = r5
            r21 = r6
            r29 = r7
            r22 = r8
            r32 = r9
            r24 = r13
            r25 = r15
        L_0x0298:
            monitor-exit(r14)     // Catch:{ all -> 0x029a }
            throw r0
        L_0x029a:
            r0 = move-exception
            goto L_0x0298
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.DynamicLayout.reflow(java.lang.CharSequence, int, int, int):void");
    }

    private boolean contentMayProtrudeFromLineTopOrBottom(CharSequence text, int start, int end) {
        if ((text instanceof Spanned) && ((ReplacementSpan[]) ((Spanned) text).getSpans(start, end, ReplacementSpan.class)).length > 0) {
            return true;
        }
        Paint paint = getPaint();
        if (text instanceof PrecomputedText) {
            ((PrecomputedText) text).getBounds(start, end, this.mTempRect);
        } else {
            paint.getTextBounds(text, start, end, this.mTempRect);
        }
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        if (this.mTempRect.top < fm.top || this.mTempRect.bottom > fm.bottom) {
            return true;
        }
        return false;
    }

    private void createBlocks() {
        int offset = 400;
        this.mNumberOfBlocks = 0;
        CharSequence text = this.mDisplay;
        while (true) {
            int offset2 = TextUtils.indexOf(text, 10, offset);
            if (offset2 < 0) {
                break;
            }
            addBlockAtOffset(offset2);
            offset = offset2 + 400;
        }
        addBlockAtOffset(text.length());
        this.mBlockIndices = new int[this.mBlockEndLines.length];
        for (int i = 0; i < this.mBlockEndLines.length; i++) {
            this.mBlockIndices[i] = -1;
        }
    }

    public ArraySet<Integer> getBlocksAlwaysNeedToBeRedrawn() {
        return this.mBlocksAlwaysNeedToBeRedrawn;
    }

    private void updateAlwaysNeedsToBeRedrawn(int blockIndex) {
        int startLine = blockIndex == 0 ? 0 : this.mBlockEndLines[blockIndex - 1] + 1;
        int endLine = this.mBlockEndLines[blockIndex];
        for (int i = startLine; i <= endLine; i++) {
            if (getContentMayProtrudeFromTopOrBottom(i)) {
                if (this.mBlocksAlwaysNeedToBeRedrawn == null) {
                    this.mBlocksAlwaysNeedToBeRedrawn = new ArraySet<>();
                }
                this.mBlocksAlwaysNeedToBeRedrawn.add(Integer.valueOf(blockIndex));
                return;
            }
        }
        if (this.mBlocksAlwaysNeedToBeRedrawn != null) {
            this.mBlocksAlwaysNeedToBeRedrawn.remove(Integer.valueOf(blockIndex));
        }
    }

    private void addBlockAtOffset(int offset) {
        int line = getLineForOffset(offset);
        if (this.mBlockEndLines == null) {
            this.mBlockEndLines = ArrayUtils.newUnpaddedIntArray(1);
            this.mBlockEndLines[this.mNumberOfBlocks] = line;
            updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            this.mNumberOfBlocks++;
        } else if (line > this.mBlockEndLines[this.mNumberOfBlocks - 1]) {
            this.mBlockEndLines = GrowingArrayUtils.append(this.mBlockEndLines, this.mNumberOfBlocks, line);
            updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            this.mNumberOfBlocks++;
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void updateBlocks(int startLine, int endLine, int newLineCount) {
        boolean createBlock;
        boolean createBlockAfter;
        int lastBlockEndLine;
        int newFirstChangedBlock;
        int i = startLine;
        int i2 = endLine;
        if (this.mBlockEndLines == null) {
            createBlocks();
            return;
        }
        int firstBlock = -1;
        int lastBlock = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= this.mNumberOfBlocks) {
                break;
            } else if (this.mBlockEndLines[i3] >= i) {
                firstBlock = i3;
                break;
            } else {
                i3++;
            }
        }
        int i4 = firstBlock;
        while (true) {
            if (i4 >= this.mNumberOfBlocks) {
                break;
            } else if (this.mBlockEndLines[i4] >= i2) {
                lastBlock = i4;
                break;
            } else {
                i4++;
            }
        }
        int lastBlockEndLine2 = this.mBlockEndLines[lastBlock];
        boolean createBlockBefore = i > (firstBlock == 0 ? 0 : this.mBlockEndLines[firstBlock + -1] + 1);
        boolean createBlock2 = newLineCount > 0;
        boolean createBlockAfter2 = i2 < this.mBlockEndLines[lastBlock];
        int numAddedBlocks = 0;
        if (createBlockBefore) {
            numAddedBlocks = 0 + 1;
        }
        if (createBlock2) {
            numAddedBlocks++;
        }
        if (createBlockAfter2) {
            numAddedBlocks++;
        }
        int numRemovedBlocks = (lastBlock - firstBlock) + 1;
        int newNumberOfBlocks = (this.mNumberOfBlocks + numAddedBlocks) - numRemovedBlocks;
        if (newNumberOfBlocks == 0) {
            this.mBlockEndLines[0] = 0;
            this.mBlockIndices[0] = -1;
            this.mNumberOfBlocks = 1;
            return;
        }
        if (newNumberOfBlocks > this.mBlockEndLines.length) {
            int[] blockEndLines = ArrayUtils.newUnpaddedIntArray(Math.max(this.mBlockEndLines.length * 2, newNumberOfBlocks));
            int[] blockIndices = new int[blockEndLines.length];
            lastBlockEndLine = lastBlockEndLine2;
            System.arraycopy(this.mBlockEndLines, 0, blockEndLines, 0, firstBlock);
            System.arraycopy(this.mBlockIndices, 0, blockIndices, 0, firstBlock);
            createBlockAfter = createBlockAfter2;
            createBlock = createBlock2;
            System.arraycopy(this.mBlockEndLines, lastBlock + 1, blockEndLines, firstBlock + numAddedBlocks, (this.mNumberOfBlocks - lastBlock) - 1);
            System.arraycopy(this.mBlockIndices, lastBlock + 1, blockIndices, firstBlock + numAddedBlocks, (this.mNumberOfBlocks - lastBlock) - 1);
            this.mBlockEndLines = blockEndLines;
            this.mBlockIndices = blockIndices;
        } else {
            lastBlockEndLine = lastBlockEndLine2;
            createBlock = createBlock2;
            createBlockAfter = createBlockAfter2;
            if (numAddedBlocks + numRemovedBlocks != 0) {
                System.arraycopy(this.mBlockEndLines, lastBlock + 1, this.mBlockEndLines, firstBlock + numAddedBlocks, (this.mNumberOfBlocks - lastBlock) - 1);
                System.arraycopy(this.mBlockIndices, lastBlock + 1, this.mBlockIndices, firstBlock + numAddedBlocks, (this.mNumberOfBlocks - lastBlock) - 1);
            }
        }
        if (!(numAddedBlocks + numRemovedBlocks == 0 || this.mBlocksAlwaysNeedToBeRedrawn == null)) {
            ArraySet<Integer> set = new ArraySet<>();
            int changedBlockCount = numAddedBlocks - numRemovedBlocks;
            int i5 = 0;
            while (true) {
                int i6 = i5;
                if (i6 >= this.mBlocksAlwaysNeedToBeRedrawn.size()) {
                    break;
                }
                Integer block = this.mBlocksAlwaysNeedToBeRedrawn.valueAt(i6);
                if (block.intValue() < firstBlock) {
                    set.add(block);
                }
                if (block.intValue() > lastBlock) {
                    set.add(Integer.valueOf(block.intValue() + changedBlockCount));
                }
                i5 = i6 + 1;
            }
            this.mBlocksAlwaysNeedToBeRedrawn = set;
        }
        this.mNumberOfBlocks = newNumberOfBlocks;
        int deltaLines = newLineCount - ((i2 - i) + 1);
        if (deltaLines != 0) {
            newFirstChangedBlock = firstBlock + numAddedBlocks;
            for (int i7 = newFirstChangedBlock; i7 < this.mNumberOfBlocks; i7++) {
                int[] iArr = this.mBlockEndLines;
                iArr[i7] = iArr[i7] + deltaLines;
            }
        } else {
            newFirstChangedBlock = this.mNumberOfBlocks;
        }
        this.mIndexFirstChangedBlock = Math.min(this.mIndexFirstChangedBlock, newFirstChangedBlock);
        int blockIndex = firstBlock;
        if (createBlockBefore) {
            this.mBlockEndLines[blockIndex] = i - 1;
            updateAlwaysNeedsToBeRedrawn(blockIndex);
            this.mBlockIndices[blockIndex] = -1;
            blockIndex++;
        }
        if (createBlock) {
            this.mBlockEndLines[blockIndex] = (i + newLineCount) - 1;
            updateAlwaysNeedsToBeRedrawn(blockIndex);
            this.mBlockIndices[blockIndex] = -1;
            blockIndex++;
        }
        if (createBlockAfter) {
            this.mBlockEndLines[blockIndex] = lastBlockEndLine + deltaLines;
            updateAlwaysNeedsToBeRedrawn(blockIndex);
            this.mBlockIndices[blockIndex] = -1;
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void setBlocksDataForTest(int[] blockEndLines, int[] blockIndices, int numberOfBlocks, int totalLines) {
        this.mBlockEndLines = new int[blockEndLines.length];
        this.mBlockIndices = new int[blockIndices.length];
        System.arraycopy(blockEndLines, 0, this.mBlockEndLines, 0, blockEndLines.length);
        System.arraycopy(blockIndices, 0, this.mBlockIndices, 0, blockIndices.length);
        this.mNumberOfBlocks = numberOfBlocks;
        while (this.mInts.size() < totalLines) {
            this.mInts.insertAt(this.mInts.size(), new int[5]);
        }
    }

    @UnsupportedAppUsage
    public int[] getBlockEndLines() {
        return this.mBlockEndLines;
    }

    @UnsupportedAppUsage
    public int[] getBlockIndices() {
        return this.mBlockIndices;
    }

    public int getBlockIndex(int index) {
        return this.mBlockIndices[index];
    }

    public void setBlockIndex(int index, int blockIndex) {
        this.mBlockIndices[index] = blockIndex;
    }

    @UnsupportedAppUsage
    public int getNumberOfBlocks() {
        return this.mNumberOfBlocks;
    }

    @UnsupportedAppUsage
    public int getIndexFirstChangedBlock() {
        return this.mIndexFirstChangedBlock;
    }

    @UnsupportedAppUsage
    public void setIndexFirstChangedBlock(int i) {
        this.mIndexFirstChangedBlock = i;
    }

    public int getLineCount() {
        return this.mInts.size() - 1;
    }

    public int getLineTop(int line) {
        return this.mInts.getValue(line, 1);
    }

    public int getLineDescent(int line) {
        return this.mInts.getValue(line, 2);
    }

    public int getLineExtra(int line) {
        return this.mInts.getValue(line, 3);
    }

    public int getLineStart(int line) {
        return this.mInts.getValue(line, 0) & 536870911;
    }

    public boolean getLineContainsTab(int line) {
        return (this.mInts.getValue(line, 0) & 536870912) != 0;
    }

    public int getParagraphDirection(int line) {
        return this.mInts.getValue(line, 0) >> 30;
    }

    public final Layout.Directions getLineDirections(int line) {
        return this.mObjects.getValue(line, 0);
    }

    public int getTopPadding() {
        return this.mTopPadding;
    }

    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    public int getStartHyphenEdit(int line) {
        return StaticLayout.unpackStartHyphenEdit(this.mInts.getValue(line, 4) & 255);
    }

    public int getEndHyphenEdit(int line) {
        return StaticLayout.unpackEndHyphenEdit(this.mInts.getValue(line, 4) & 255);
    }

    private boolean getContentMayProtrudeFromTopOrBottom(int line) {
        return (this.mInts.getValue(line, 4) & 256) != 0;
    }

    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    private static class ChangeWatcher implements TextWatcher, SpanWatcher {
        private WeakReference<DynamicLayout> mLayout;

        public ChangeWatcher(DynamicLayout layout) {
            this.mLayout = new WeakReference<>(layout);
        }

        private void reflow(CharSequence s, int where, int before, int after) {
            DynamicLayout ml = (DynamicLayout) this.mLayout.get();
            if (ml != null) {
                ml.reflow(s, where, before, after);
            } else if (s instanceof Spannable) {
                ((Spannable) s).removeSpan(this);
            }
        }

        public void beforeTextChanged(CharSequence s, int where, int before, int after) {
        }

        public void onTextChanged(CharSequence s, int where, int before, int after) {
            reflow(s, where, before, after);
        }

        public void afterTextChanged(Editable s) {
        }

        public void onSpanAdded(Spannable s, Object o, int start, int end) {
            if (o instanceof UpdateLayout) {
                reflow(s, start, end - start, end - start);
            }
        }

        public void onSpanRemoved(Spannable s, Object o, int start, int end) {
            if (o instanceof UpdateLayout) {
                reflow(s, start, end - start, end - start);
            }
        }

        public void onSpanChanged(Spannable s, Object o, int start, int end, int nstart, int nend) {
            if (o instanceof UpdateLayout) {
                if (start > end) {
                    start = 0;
                }
                reflow(s, start, end - start, end - start);
                reflow(s, nstart, nend - nstart, nend - nstart);
            }
        }
    }

    public int getEllipsisStart(int line) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(line, 5);
    }

    public int getEllipsisCount(int line) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(line, 6);
    }
}
