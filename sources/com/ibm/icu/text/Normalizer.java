package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.nio.CharBuffer;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
public final class Normalizer implements Cloneable {
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    private static final int COMPARE_EQUIV = 524288;
    public static final int COMPARE_IGNORE_CASE = 65536;
    @Deprecated
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
    @Deprecated
    public static final int DONE = -1;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final int INPUT_IS_FCD = 131072;
    @Deprecated
    public static final int UNICODE_3_2 = 32;
    private StringBuilder buffer;
    private int bufferPos;
    private int currentIndex;
    private Mode mode;
    private int nextIndex;
    private Normalizer2 norm2;
    private int options;
    private UCharacterIterator text;
    @Deprecated
    public static final Mode NONE = new NONEMode();
    @Deprecated
    public static final Mode NFD = new NFDMode();
    @Deprecated
    public static final Mode NFKD = new NFKDMode();
    @Deprecated
    public static final Mode NFC = new NFCMode();
    @Deprecated
    public static final Mode DEFAULT = NFC;
    @Deprecated
    public static final Mode NFKC = new NFKCMode();
    @Deprecated
    public static final Mode FCD = new FCDMode();
    @Deprecated
    public static final Mode NO_OP = NONE;
    @Deprecated
    public static final Mode COMPOSE = NFC;
    @Deprecated
    public static final Mode COMPOSE_COMPAT = NFKC;
    @Deprecated
    public static final Mode DECOMP = NFD;
    @Deprecated
    public static final Mode DECOMP_COMPAT = NFKD;

    /* renamed from: NO */
    public static final QuickCheckResult f2546NO = new QuickCheckResult(0);
    public static final QuickCheckResult YES = new QuickCheckResult(1);
    public static final QuickCheckResult MAYBE = new QuickCheckResult(2);

    /* loaded from: classes5.dex */
    private static final class ModeImpl {
        private final Normalizer2 normalizer2;

        private ModeImpl(Normalizer2 n2) {
            this.normalizer2 = n2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance());

        private NFDModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance());

        private NFKDModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance());

        private NFCModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance());

        private NFKCModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class FCDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2());

        private FCDModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class Unicode32 {
        private static final UnicodeSet INSTANCE = new UnicodeSet("[:age=3.2:]").m211freeze();

        private Unicode32() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Unicode32.INSTANCE));

        private NFD32ModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Unicode32.INSTANCE));

        private NFKD32ModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Unicode32.INSTANCE));

        private NFC32ModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Unicode32.INSTANCE));

        private NFKC32ModeImpl() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class FCD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.INSTANCE));

        private FCD32ModeImpl() {
        }
    }

    @Deprecated
    /* loaded from: classes5.dex */
    public static abstract class Mode {
        @Deprecated
        protected abstract Normalizer2 getNormalizer2(int i);

        @Deprecated
        protected Mode() {
        }
    }

    /* loaded from: classes5.dex */
    private static final class NONEMode extends Mode {
        private NONEMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFDMode extends Mode {
        private NFDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            ModeImpl modeImpl;
            if ((options & 32) == 0) {
                modeImpl = NFDModeImpl.INSTANCE;
            } else {
                modeImpl = NFD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKDMode extends Mode {
        private NFKDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            ModeImpl modeImpl;
            if ((options & 32) == 0) {
                modeImpl = NFKDModeImpl.INSTANCE;
            } else {
                modeImpl = NFKD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFCMode extends Mode {
        private NFCMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            ModeImpl modeImpl;
            if ((options & 32) == 0) {
                modeImpl = NFCModeImpl.INSTANCE;
            } else {
                modeImpl = NFC32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class NFKCMode extends Mode {
        private NFKCMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            ModeImpl modeImpl;
            if ((options & 32) == 0) {
                modeImpl = NFKCModeImpl.INSTANCE;
            } else {
                modeImpl = NFKC32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    /* loaded from: classes5.dex */
    private static final class FCDMode extends Mode {
        private FCDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int options) {
            ModeImpl modeImpl;
            if ((options & 32) == 0) {
                modeImpl = FCDModeImpl.INSTANCE;
            } else {
                modeImpl = FCD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    /* loaded from: classes5.dex */
    public static final class QuickCheckResult {
        private QuickCheckResult(int value) {
        }
    }

    @Deprecated
    public Normalizer(String str, Mode mode, int opt) {
        this.text = UCharacterIterator.getInstance(str);
        this.mode = mode;
        this.options = opt;
        this.norm2 = mode.getNormalizer2(opt);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(CharacterIterator iter, Mode mode, int opt) {
        this.text = UCharacterIterator.getInstance((CharacterIterator) iter.clone());
        this.mode = mode;
        this.options = opt;
        this.norm2 = mode.getNormalizer2(opt);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(UCharacterIterator iter, Mode mode, int options) {
        try {
            this.text = (UCharacterIterator) iter.clone();
            this.mode = mode;
            this.options = options;
            this.norm2 = mode.getNormalizer2(options);
            this.buffer = new StringBuilder();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    @Deprecated
    public Object clone() {
        try {
            Normalizer copy = (Normalizer) super.clone();
            copy.text = (UCharacterIterator) this.text.clone();
            copy.mode = this.mode;
            copy.options = this.options;
            copy.norm2 = this.norm2;
            copy.buffer = new StringBuilder(this.buffer);
            copy.bufferPos = this.bufferPos;
            copy.currentIndex = this.currentIndex;
            copy.nextIndex = this.nextIndex;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    private static final Normalizer2 getComposeNormalizer2(boolean compat, int options) {
        return (compat ? NFKC : NFC).getNormalizer2(options);
    }

    private static final Normalizer2 getDecomposeNormalizer2(boolean compat, int options) {
        return (compat ? NFKD : NFD).getNormalizer2(options);
    }

    @Deprecated
    public static String compose(String str, boolean compat) {
        return compose(str, compat, 0);
    }

    @Deprecated
    public static String compose(String str, boolean compat, int options) {
        return getComposeNormalizer2(compat, options).normalize(str);
    }

    @Deprecated
    public static int compose(char[] source, char[] target, boolean compat, int options) {
        return compose(source, 0, source.length, target, 0, target.length, compat, options);
    }

    @Deprecated
    public static int compose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        getComposeNormalizer2(compat, options).normalize(srcBuffer, app);
        return app.length();
    }

    @Deprecated
    public static String decompose(String str, boolean compat) {
        return decompose(str, compat, 0);
    }

    @Deprecated
    public static String decompose(String str, boolean compat, int options) {
        return getDecomposeNormalizer2(compat, options).normalize(str);
    }

    @Deprecated
    public static int decompose(char[] source, char[] target, boolean compat, int options) {
        return decompose(source, 0, source.length, target, 0, target.length, compat, options);
    }

    @Deprecated
    public static int decompose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        getDecomposeNormalizer2(compat, options).normalize(srcBuffer, app);
        return app.length();
    }

    @Deprecated
    public static String normalize(String str, Mode mode, int options) {
        return mode.getNormalizer2(options).normalize(str);
    }

    @Deprecated
    public static String normalize(String src, Mode mode) {
        return normalize(src, mode, 0);
    }

    @Deprecated
    public static int normalize(char[] source, char[] target, Mode mode, int options) {
        return normalize(source, 0, source.length, target, 0, target.length, mode, options);
    }

    @Deprecated
    public static int normalize(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, Mode mode, int options) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        mode.getNormalizer2(options).normalize(srcBuffer, app);
        return app.length();
    }

    @Deprecated
    public static String normalize(int char32, Mode mode, int options) {
        if (mode == NFD && options == 0) {
            String decomposition = Normalizer2.getNFCInstance().getDecomposition(char32);
            if (decomposition == null) {
                return UTF16.valueOf(char32);
            }
            return decomposition;
        }
        return normalize(UTF16.valueOf(char32), mode, options);
    }

    @Deprecated
    public static String normalize(int char32, Mode mode) {
        return normalize(char32, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String source, Mode mode) {
        return quickCheck(source, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String source, Mode mode, int options) {
        return mode.getNormalizer2(options).quickCheck(source);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] source, Mode mode, int options) {
        return quickCheck(source, 0, source.length, mode, options);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] source, int start, int limit, Mode mode, int options) {
        CharBuffer srcBuffer = CharBuffer.wrap(source, start, limit - start);
        return mode.getNormalizer2(options).quickCheck(srcBuffer);
    }

    @Deprecated
    public static boolean isNormalized(char[] src, int start, int limit, Mode mode, int options) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, start, limit - start);
        return mode.getNormalizer2(options).isNormalized(srcBuffer);
    }

    @Deprecated
    public static boolean isNormalized(String str, Mode mode, int options) {
        return mode.getNormalizer2(options).isNormalized(str);
    }

    @Deprecated
    public static boolean isNormalized(int char32, Mode mode, int options) {
        return isNormalized(UTF16.valueOf(char32), mode, options);
    }

    public static int compare(char[] s1, int s1Start, int s1Limit, char[] s2, int s2Start, int s2Limit, int options) {
        if (s1 == null || s1Start < 0 || s1Limit < 0 || s2 == null || s2Start < 0 || s2Limit < 0 || s1Limit < s1Start || s2Limit < s2Start) {
            throw new IllegalArgumentException();
        }
        return internalCompare(CharBuffer.wrap(s1, s1Start, s1Limit - s1Start), CharBuffer.wrap(s2, s2Start, s2Limit - s2Start), options);
    }

    public static int compare(String s1, String s2, int options) {
        return internalCompare(s1, s2, options);
    }

    public static int compare(char[] s1, char[] s2, int options) {
        return internalCompare(CharBuffer.wrap(s1), CharBuffer.wrap(s2), options);
    }

    public static int compare(int char32a, int char32b, int options) {
        return internalCompare(UTF16.valueOf(char32a), UTF16.valueOf(char32b), 131072 | options);
    }

    public static int compare(int char32a, String str2, int options) {
        return internalCompare(UTF16.valueOf(char32a), str2, options);
    }

    @Deprecated
    public static int concatenate(char[] left, int leftStart, int leftLimit, char[] right, int rightStart, int rightLimit, char[] dest, int destStart, int destLimit, Mode mode, int options) {
        if (dest == null) {
            throw new IllegalArgumentException();
        }
        if (right == dest && rightStart < destLimit && destStart < rightLimit) {
            throw new IllegalArgumentException("overlapping right and dst ranges");
        }
        StringBuilder destBuilder = new StringBuilder((((leftLimit - leftStart) + rightLimit) - rightStart) + 16);
        destBuilder.append(left, leftStart, leftLimit - leftStart);
        CharBuffer rightBuffer = CharBuffer.wrap(right, rightStart, rightLimit - rightStart);
        mode.getNormalizer2(options).append(destBuilder, rightBuffer);
        int destLength = destBuilder.length();
        if (destLength <= destLimit - destStart) {
            destBuilder.getChars(0, destLength, dest, destStart);
            return destLength;
        }
        throw new IndexOutOfBoundsException(Integer.toString(destLength));
    }

    @Deprecated
    public static String concatenate(char[] left, char[] right, Mode mode, int options) {
        StringBuilder dest = new StringBuilder(left.length + right.length + 16).append(left);
        return mode.getNormalizer2(options).append(dest, CharBuffer.wrap(right)).toString();
    }

    @Deprecated
    public static String concatenate(String left, String right, Mode mode, int options) {
        StringBuilder dest = new StringBuilder(left.length() + right.length() + 16).append(left);
        return mode.getNormalizer2(options).append(dest, right).toString();
    }

    @Deprecated
    public static int getFC_NFKC_Closure(int c, char[] dest) {
        String closure = getFC_NFKC_Closure(c);
        int length = closure.length();
        if (length != 0 && dest != null && length <= dest.length) {
            closure.getChars(0, length, dest, 0);
        }
        return length;
    }

    @Deprecated
    public static String getFC_NFKC_Closure(int c) {
        Norm2AllModes.Normalizer2WithImpl normalizer2WithImpl = NFKCModeImpl.INSTANCE.normalizer2;
        UCaseProps csp = UCaseProps.INSTANCE;
        StringBuilder folded = new StringBuilder();
        int folded1Length = csp.toFullFolding(c, folded, 0);
        if (folded1Length < 0) {
            Normalizer2Impl nfkcImpl = normalizer2WithImpl.impl;
            if (nfkcImpl.getCompQuickCheck(nfkcImpl.getNorm16(c)) != 0) {
                return "";
            }
            folded.appendCodePoint(c);
        } else if (folded1Length > 31) {
            folded.appendCodePoint(folded1Length);
        }
        String kc1 = normalizer2WithImpl.normalize(folded);
        String kc2 = normalizer2WithImpl.normalize(UCharacter.foldCase(kc1, 0));
        if (kc1.equals(kc2)) {
            return "";
        }
        return kc2;
    }

    @Deprecated
    public int current() {
        if (this.bufferPos < this.buffer.length() || nextNormalize()) {
            return this.buffer.codePointAt(this.bufferPos);
        }
        return -1;
    }

    @Deprecated
    public int next() {
        if (this.bufferPos < this.buffer.length() || nextNormalize()) {
            int c = this.buffer.codePointAt(this.bufferPos);
            this.bufferPos += Character.charCount(c);
            return c;
        }
        return -1;
    }

    @Deprecated
    public int previous() {
        if (this.bufferPos > 0 || previousNormalize()) {
            int c = this.buffer.codePointBefore(this.bufferPos);
            this.bufferPos -= Character.charCount(c);
            return c;
        }
        return -1;
    }

    @Deprecated
    public void reset() {
        this.text.setToStart();
        this.nextIndex = 0;
        this.currentIndex = 0;
        clearBuffer();
    }

    @Deprecated
    public void setIndexOnly(int index) {
        this.text.setIndex(index);
        this.nextIndex = index;
        this.currentIndex = index;
        clearBuffer();
    }

    @Deprecated
    public int setIndex(int index) {
        setIndexOnly(index);
        return current();
    }

    @Deprecated
    public int getBeginIndex() {
        return 0;
    }

    @Deprecated
    public int getEndIndex() {
        return endIndex();
    }

    @Deprecated
    public int first() {
        reset();
        return next();
    }

    @Deprecated
    public int last() {
        this.text.setToLimit();
        int index = this.text.getIndex();
        this.nextIndex = index;
        this.currentIndex = index;
        clearBuffer();
        return previous();
    }

    @Deprecated
    public int getIndex() {
        if (this.bufferPos < this.buffer.length()) {
            return this.currentIndex;
        }
        return this.nextIndex;
    }

    @Deprecated
    public int startIndex() {
        return 0;
    }

    @Deprecated
    public int endIndex() {
        return this.text.getLength();
    }

    @Deprecated
    public void setMode(Mode newMode) {
        this.mode = newMode;
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public Mode getMode() {
        return this.mode;
    }

    @Deprecated
    public void setOption(int option, boolean value) {
        if (value) {
            this.options |= option;
        } else {
            this.options &= ~option;
        }
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public int getOption(int option) {
        if ((this.options & option) != 0) {
            return 1;
        }
        return 0;
    }

    @Deprecated
    public int getText(char[] fillIn) {
        return this.text.getText(fillIn);
    }

    @Deprecated
    public int getLength() {
        return this.text.getLength();
    }

    @Deprecated
    public String getText() {
        return this.text.getText();
    }

    @Deprecated
    public void setText(StringBuffer newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = newIter;
        reset();
    }

    @Deprecated
    public void setText(char[] newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = newIter;
        reset();
    }

    @Deprecated
    public void setText(String newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = newIter;
        reset();
    }

    @Deprecated
    public void setText(CharacterIterator newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = newIter;
        reset();
    }

    @Deprecated
    public void setText(UCharacterIterator newText) {
        try {
            UCharacterIterator newIter = (UCharacterIterator) newText.clone();
            if (newIter == null) {
                throw new IllegalStateException("Could not create a new UCharacterIterator");
            }
            this.text = newIter;
            reset();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Could not clone the UCharacterIterator", e);
        }
    }

    private void clearBuffer() {
        this.buffer.setLength(0);
        this.bufferPos = 0;
    }

    private boolean nextNormalize() {
        clearBuffer();
        this.currentIndex = this.nextIndex;
        this.text.setIndex(this.nextIndex);
        int c = this.text.nextCodePoint();
        if (c < 0) {
            return false;
        }
        StringBuilder segment = new StringBuilder().appendCodePoint(c);
        while (true) {
            int c2 = this.text.nextCodePoint();
            if (c2 < 0) {
                break;
            } else if (this.norm2.hasBoundaryBefore(c2)) {
                this.text.moveCodePointIndex(-1);
                break;
            } else {
                segment.appendCodePoint(c2);
            }
        }
        this.nextIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence) segment, this.buffer);
        return this.buffer.length() != 0;
    }

    private boolean previousNormalize() {
        int c;
        clearBuffer();
        this.nextIndex = this.currentIndex;
        this.text.setIndex(this.currentIndex);
        StringBuilder segment = new StringBuilder();
        do {
            c = this.text.previousCodePoint();
            if (c < 0) {
                break;
            } else if (c <= 65535) {
                segment.insert(0, (char) c);
            } else {
                segment.insert(0, Character.toChars(c));
            }
        } while (!this.norm2.hasBoundaryBefore(c));
        this.currentIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence) segment, this.buffer);
        this.bufferPos = this.buffer.length();
        return this.buffer.length() != 0;
    }

    private static int internalCompare(CharSequence s1, CharSequence s2, int options) {
        Normalizer2 n2;
        int normOptions = options >>> 20;
        int options2 = options | 524288;
        if ((131072 & options2) == 0 || (options2 & 1) != 0) {
            if ((options2 & 1) != 0) {
                n2 = NFD.getNormalizer2(normOptions);
            } else {
                n2 = FCD.getNormalizer2(normOptions);
            }
            int spanQCYes1 = n2.spanQuickCheckYes(s1);
            int spanQCYes2 = n2.spanQuickCheckYes(s2);
            if (spanQCYes1 < s1.length()) {
                StringBuilder fcd1 = new StringBuilder(s1.length() + 16).append(s1, 0, spanQCYes1);
                s1 = n2.normalizeSecondAndAppend(fcd1, s1.subSequence(spanQCYes1, s1.length()));
            }
            if (spanQCYes2 < s2.length()) {
                StringBuilder fcd2 = new StringBuilder(s2.length() + 16).append(s2, 0, spanQCYes2);
                s2 = n2.normalizeSecondAndAppend(fcd2, s2.subSequence(spanQCYes2, s2.length()));
            }
        }
        return cmpEquivFold(s1, s2, options2);
    }

    /* loaded from: classes5.dex */
    private static final class CmpEquivLevel {

        /* renamed from: cs */
        CharSequence f2547cs;

        /* renamed from: s */
        int f2548s;

        private CmpEquivLevel() {
        }
    }

    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[]{new CmpEquivLevel(), new CmpEquivLevel()};
    }

    /* JADX WARN: Code restructure failed: missing block: B:154:0x0317, code lost:
        if (r1 < 55296) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0319, code lost:
        if (r3 < 55296) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x031f, code lost:
        if ((32768 & r37) == 0) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0324, code lost:
        if (r1 > 56319) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0328, code lost:
        if (r11 == r25) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0332, code lost:
        if (java.lang.Character.isLowSurrogate(r2.charAt(r11)) != false) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x033c, code lost:
        if (java.lang.Character.isLowSurrogate((char) r1) == false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0340, code lost:
        if ((r11 - 1) == 0) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x034c, code lost:
        if (java.lang.Character.isHighSurrogate(r2.charAt(r11 - 2)) == false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x034f, code lost:
        r1 = r1 - 10240;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0354, code lost:
        if (r3 > 56319) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0358, code lost:
        if (r14 == r28) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0362, code lost:
        if (java.lang.Character.isLowSurrogate(r13.charAt(r14)) != false) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x036c, code lost:
        if (java.lang.Character.isLowSurrogate((char) r3) == false) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0370, code lost:
        if ((r14 - 1) == 0) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x037c, code lost:
        if (java.lang.Character.isHighSurrogate(r13.charAt(r14 - 2)) == false) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x037f, code lost:
        r3 = r3 - 10240;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0388, code lost:
        return r1 - r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0312 A[ADDED_TO_REGION, EDGE_INSN: B:193:0x0312->B:153:0x0312 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x023d A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static int cmpEquivFold(CharSequence cs1, CharSequence cs2, int options) {
        UCaseProps csp;
        StringBuilder fold2;
        StringBuilder fold1;
        Normalizer2Impl nfcImpl;
        int c2;
        int s2;
        int limit2;
        int level2;
        int limit1;
        int limit22;
        int cp2;
        Normalizer2Impl nfcImpl2;
        String decomp2;
        int level1;
        int length;
        int length2;
        int c22;
        Normalizer2Impl nfcImpl3 = (options & 524288) != 0 ? Norm2AllModes.getNFCInstance().impl : null;
        if ((options & 65536) != 0) {
            csp = UCaseProps.INSTANCE;
            fold1 = new StringBuilder();
            fold2 = new StringBuilder();
        } else {
            csp = null;
            fold2 = null;
            fold1 = null;
        }
        int limit12 = cs1.length();
        int limit23 = cs2.length();
        int level12 = 0;
        int c23 = -1;
        int s22 = 0;
        int limit24 = limit23;
        int level22 = 0;
        CharSequence cs22 = cs2;
        CmpEquivLevel[] stack2 = null;
        int limit13 = limit12;
        CharSequence cs12 = cs1;
        int s1 = 0;
        CmpEquivLevel[] stack1 = null;
        int c1 = -1;
        while (true) {
            if (c1 < 0) {
                while (true) {
                    if (s1 != limit13) {
                        c1 = cs12.charAt(s1);
                        s1++;
                        break;
                    } else if (level12 == 0) {
                        c1 = -1;
                        break;
                    } else {
                        while (true) {
                            level12--;
                            cs12 = stack1[level12].f2547cs;
                            if (cs12 != null) {
                                break;
                            }
                        }
                        s1 = stack1[level12].f2548s;
                        limit13 = cs12.length();
                    }
                }
            }
            int level13 = level12;
            if (c23 < 0) {
                CharSequence cs23 = cs22;
                int s23 = s22;
                int limit25 = limit24;
                while (true) {
                    if (s23 != limit25) {
                        s2 = s23 + 1;
                        c22 = cs23.charAt(s23);
                        nfcImpl = nfcImpl3;
                        cs22 = cs23;
                        level2 = level22;
                        break;
                    } else if (level22 == 0) {
                        c22 = -1;
                        nfcImpl = nfcImpl3;
                        s2 = s23;
                        level2 = level22;
                        cs22 = cs23;
                        break;
                    } else {
                        while (true) {
                            level22--;
                            cs23 = stack2[level22].f2547cs;
                            if (cs23 != null) {
                                break;
                            }
                        }
                        s23 = stack2[level22].f2548s;
                        limit25 = cs23.length();
                    }
                }
                limit2 = limit25;
                c2 = c22;
            } else {
                nfcImpl = nfcImpl3;
                c2 = c23;
                s2 = s22;
                limit2 = limit24;
                level2 = level22;
            }
            if (c1 == c2) {
                if (c1 < 0) {
                    return 0;
                }
                c23 = -1;
                c1 = -1;
                level22 = level2;
                level12 = level13;
                limit24 = limit2;
                s22 = s2;
                nfcImpl3 = nfcImpl;
            } else if (c1 < 0) {
                return -1;
            } else {
                if (c2 < 0) {
                    return 1;
                }
                int cp1 = c1;
                CmpEquivLevel[] stack22 = stack2;
                if (UTF16.isSurrogate((char) c1)) {
                    if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                        limit1 = limit13;
                        int limit14 = s1 - 2;
                        if (limit14 >= 0) {
                            char c = cs12.charAt(s1 - 2);
                            if (Character.isHighSurrogate(c)) {
                                cp1 = Character.toCodePoint(c, (char) c1);
                            }
                        }
                    } else if (s1 != limit13) {
                        char c3 = cs12.charAt(s1);
                        if (Character.isLowSurrogate(c3)) {
                            limit1 = limit13;
                            cp1 = Character.toCodePoint((char) c1, c3);
                        }
                    }
                    int cp12 = cp1;
                    int cp22 = c2;
                    if (UTF16.isSurrogate((char) c2)) {
                        if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                            limit22 = limit2;
                            int limit26 = s2 - 2;
                            if (limit26 >= 0) {
                                char c4 = cs22.charAt(s2 - 2);
                                if (Character.isHighSurrogate(c4)) {
                                    cp2 = Character.toCodePoint(c4, (char) c2);
                                }
                            }
                            cp2 = cp22;
                        } else if (s2 != limit2) {
                            char c5 = cs22.charAt(s2);
                            if (Character.isLowSurrogate(c5)) {
                                limit22 = limit2;
                                cp2 = Character.toCodePoint((char) c2, c5);
                            }
                        }
                        if (level13 != 0 && (options & 65536) != 0 && (length2 = csp.toFullFolding(cp12, fold1, options)) >= 0) {
                            if (UTF16.isSurrogate((char) c1)) {
                                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                                    s1++;
                                } else {
                                    s2--;
                                    c2 = cs22.charAt(s2 - 1);
                                }
                            }
                            c23 = c2;
                            s22 = s2;
                            if (stack1 == null) {
                                stack1 = createCmpEquivLevelStack();
                            }
                            stack1[0].f2547cs = cs12;
                            stack1[0].f2548s = s1;
                            level12 = level13 + 1;
                            if (length2 <= 31) {
                                fold1.delete(0, fold1.length() - length2);
                            } else {
                                fold1.setLength(0);
                                fold1.appendCodePoint(length2);
                            }
                            cs12 = fold1;
                            s1 = 0;
                            int limit15 = fold1.length();
                            c1 = -1;
                            limit13 = limit15;
                            level22 = level2;
                            nfcImpl3 = nfcImpl;
                            stack2 = stack22;
                            limit24 = limit22;
                        } else if (level2 != 0 || (options & 65536) == 0 || (length = csp.toFullFolding(cp2, fold2, options)) < 0) {
                            UCaseProps csp2 = csp;
                            if (level13 < 2 || (options & 524288) == 0) {
                                nfcImpl2 = nfcImpl;
                            } else {
                                nfcImpl2 = nfcImpl;
                                String decomp1 = nfcImpl2.getDecomposition(cp12);
                                if (decomp1 != null) {
                                    if (UTF16.isSurrogate((char) c1)) {
                                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                                            s1++;
                                        } else {
                                            s2--;
                                            c2 = cs22.charAt(s2 - 1);
                                        }
                                    }
                                    s22 = s2;
                                    if (stack1 == null) {
                                        stack1 = createCmpEquivLevelStack();
                                    }
                                    stack1[level13].f2547cs = cs12;
                                    stack1[level13].f2548s = s1;
                                    int level14 = level13 + 1;
                                    if (level14 < 2) {
                                        stack1[level14].f2547cs = null;
                                        level12 = level14 + 1;
                                    } else {
                                        level12 = level14;
                                    }
                                    cs12 = decomp1;
                                    s1 = 0;
                                    int limit16 = decomp1.length();
                                    c1 = -1;
                                    c23 = c2;
                                    level22 = level2;
                                    limit13 = limit16;
                                    nfcImpl3 = nfcImpl2;
                                    stack2 = stack22;
                                    limit24 = limit22;
                                    csp = csp2;
                                }
                            }
                            if (level2 < 2 || (options & 524288) == 0 || (decomp2 = nfcImpl2.getDecomposition(cp2)) == null) {
                                break;
                            }
                            if (UTF16.isSurrogate((char) c2)) {
                                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                                    s2++;
                                } else {
                                    s1--;
                                    c1 = cs12.charAt(s1 - 1);
                                }
                            }
                            CmpEquivLevel[] stack23 = stack22 == null ? createCmpEquivLevelStack() : stack22;
                            int c12 = c1;
                            stack23[level2].f2547cs = cs22;
                            stack23[level2].f2548s = s2;
                            int level23 = level2 + 1;
                            if (level23 < 2) {
                                level1 = level13;
                                stack23[level23].f2547cs = null;
                                level22 = level23 + 1;
                            } else {
                                level1 = level13;
                                level22 = level23;
                            }
                            cs22 = decomp2;
                            s22 = 0;
                            limit24 = decomp2.length();
                            c23 = -1;
                            stack2 = stack23;
                            nfcImpl3 = nfcImpl2;
                            limit13 = limit1;
                            csp = csp2;
                            c1 = c12;
                            level12 = level1;
                        } else {
                            if (UTF16.isSurrogate((char) c2)) {
                                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                                    s2++;
                                } else {
                                    s1--;
                                    c1 = cs12.charAt(s1 - 1);
                                }
                            }
                            CmpEquivLevel[] stack24 = stack22 == null ? createCmpEquivLevelStack() : stack22;
                            int c13 = c1;
                            UCaseProps csp3 = csp;
                            stack24[0].f2547cs = cs22;
                            stack24[0].f2548s = s2;
                            level22 = level2 + 1;
                            if (length <= 31) {
                                fold2.delete(0, fold2.length() - length);
                            } else {
                                fold2.setLength(0);
                                fold2.appendCodePoint(length);
                            }
                            cs22 = fold2;
                            s22 = 0;
                            limit24 = fold2.length();
                            c23 = -1;
                            level12 = level13;
                            stack2 = stack24;
                            nfcImpl3 = nfcImpl;
                            limit13 = limit1;
                            csp = csp3;
                            c1 = c13;
                        }
                    }
                    limit22 = limit2;
                    cp2 = cp22;
                    if (level13 != 0) {
                    }
                    if (level2 != 0) {
                    }
                    UCaseProps csp22 = csp;
                    if (level13 < 2) {
                    }
                    nfcImpl2 = nfcImpl;
                    if (level2 < 2) {
                        break;
                    }
                    break;
                }
                limit1 = limit13;
                int cp122 = cp1;
                int cp222 = c2;
                if (UTF16.isSurrogate((char) c2)) {
                }
                limit22 = limit2;
                cp2 = cp222;
                if (level13 != 0) {
                }
                if (level2 != 0) {
                }
                UCaseProps csp222 = csp;
                if (level13 < 2) {
                }
                nfcImpl2 = nfcImpl;
                if (level2 < 2) {
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    private static final class CharsAppendable implements Appendable {
        private final char[] chars;
        private final int limit;
        private int offset;
        private final int start;

        public CharsAppendable(char[] dest, int destStart, int destLimit) {
            this.chars = dest;
            this.offset = destStart;
            this.start = destStart;
            this.limit = destLimit;
        }

        public int length() {
            int len = this.offset - this.start;
            if (this.offset <= this.limit) {
                return len;
            }
            throw new IndexOutOfBoundsException(Integer.toString(len));
        }

        @Override // java.lang.Appendable
        public Appendable append(char c) {
            if (this.offset < this.limit) {
                this.chars[this.offset] = c;
            }
            this.offset++;
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence s) {
            return append(s, 0, s.length());
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence s, int sStart, int sLimit) {
            int len = sLimit - sStart;
            if (len <= this.limit - this.offset) {
                while (sStart < sLimit) {
                    char[] cArr = this.chars;
                    int i = this.offset;
                    this.offset = i + 1;
                    cArr[i] = s.charAt(sStart);
                    sStart++;
                }
            } else {
                this.offset += len;
            }
            return this;
        }
    }
}
