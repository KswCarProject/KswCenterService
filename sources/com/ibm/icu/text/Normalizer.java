package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.nio.CharBuffer;
import java.text.CharacterIterator;

public final class Normalizer implements Cloneable {
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    private static final int COMPARE_EQUIV = 524288;
    public static final int COMPARE_IGNORE_CASE = 65536;
    @Deprecated
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
    @Deprecated
    public static final Mode COMPOSE = NFC;
    @Deprecated
    public static final Mode COMPOSE_COMPAT = NFKC;
    @Deprecated
    public static final Mode DECOMP = NFD;
    @Deprecated
    public static final Mode DECOMP_COMPAT = NFKD;
    @Deprecated
    public static final Mode DEFAULT = NFC;
    @Deprecated
    public static final int DONE = -1;
    @Deprecated
    public static final Mode FCD = new FCDMode();
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final int INPUT_IS_FCD = 131072;
    public static final QuickCheckResult MAYBE = new QuickCheckResult(2);
    @Deprecated
    public static final Mode NFC = new NFCMode();
    @Deprecated
    public static final Mode NFD = new NFDMode();
    @Deprecated
    public static final Mode NFKC = new NFKCMode();
    @Deprecated
    public static final Mode NFKD = new NFKDMode();
    public static final QuickCheckResult NO = new QuickCheckResult(0);
    @Deprecated
    public static final Mode NONE = new NONEMode();
    @Deprecated
    public static final Mode NO_OP = NONE;
    @Deprecated
    public static final int UNICODE_3_2 = 32;
    public static final QuickCheckResult YES = new QuickCheckResult(1);
    private StringBuilder buffer;
    private int bufferPos;
    private int currentIndex;
    private Mode mode;
    private int nextIndex;
    private Normalizer2 norm2;
    private int options;
    private UCharacterIterator text;

    private static final class ModeImpl {
        /* access modifiers changed from: private */
        public final Normalizer2 normalizer2;

        private ModeImpl(Normalizer2 n2) {
            this.normalizer2 = n2;
        }
    }

    private static final class NFDModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance());

        private NFDModeImpl() {
        }
    }

    private static final class NFKDModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance());

        private NFKDModeImpl() {
        }
    }

    private static final class NFCModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance());

        private NFCModeImpl() {
        }
    }

    private static final class NFKCModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance());

        private NFKCModeImpl() {
        }
    }

    private static final class FCDModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2());

        private FCDModeImpl() {
        }
    }

    private static final class Unicode32 {
        /* access modifiers changed from: private */
        public static final UnicodeSet INSTANCE = new UnicodeSet("[:age=3.2:]").freeze();

        private Unicode32() {
        }
    }

    private static final class NFD32ModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Unicode32.INSTANCE));

        private NFD32ModeImpl() {
        }
    }

    private static final class NFKD32ModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Unicode32.INSTANCE));

        private NFKD32ModeImpl() {
        }
    }

    private static final class NFC32ModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Unicode32.INSTANCE));

        private NFC32ModeImpl() {
        }
    }

    private static final class NFKC32ModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Unicode32.INSTANCE));

        private NFKC32ModeImpl() {
        }
    }

    private static final class FCD32ModeImpl {
        /* access modifiers changed from: private */
        public static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.INSTANCE));

        private FCD32ModeImpl() {
        }
    }

    @Deprecated
    public static abstract class Mode {
        /* access modifiers changed from: protected */
        @Deprecated
        public abstract Normalizer2 getNormalizer2(int i);

        @Deprecated
        protected Mode() {
        }
    }

    private static final class NONEMode extends Mode {
        private NONEMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }
    }

    private static final class NFDMode extends Mode {
        private NFDMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return ((options & 32) != 0 ? NFD32ModeImpl.INSTANCE : NFDModeImpl.INSTANCE).normalizer2;
        }
    }

    private static final class NFKDMode extends Mode {
        private NFKDMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return ((options & 32) != 0 ? NFKD32ModeImpl.INSTANCE : NFKDModeImpl.INSTANCE).normalizer2;
        }
    }

    private static final class NFCMode extends Mode {
        private NFCMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return ((options & 32) != 0 ? NFC32ModeImpl.INSTANCE : NFCModeImpl.INSTANCE).normalizer2;
        }
    }

    private static final class NFKCMode extends Mode {
        private NFKCMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return ((options & 32) != 0 ? NFKC32ModeImpl.INSTANCE : NFKCModeImpl.INSTANCE).normalizer2;
        }
    }

    private static final class FCDMode extends Mode {
        private FCDMode() {
        }

        /* access modifiers changed from: protected */
        public Normalizer2 getNormalizer2(int options) {
            return ((options & 32) != 0 ? FCD32ModeImpl.INSTANCE : FCDModeImpl.INSTANCE).normalizer2;
        }
    }

    public static final class QuickCheckResult {
        private QuickCheckResult(int value) {
        }
    }

    @Deprecated
    public Normalizer(String str, Mode mode2, int opt) {
        this.text = UCharacterIterator.getInstance(str);
        this.mode = mode2;
        this.options = opt;
        this.norm2 = mode2.getNormalizer2(opt);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(CharacterIterator iter, Mode mode2, int opt) {
        this.text = UCharacterIterator.getInstance((CharacterIterator) iter.clone());
        this.mode = mode2;
        this.options = opt;
        this.norm2 = mode2.getNormalizer2(opt);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(UCharacterIterator iter, Mode mode2, int options2) {
        try {
            this.text = (UCharacterIterator) iter.clone();
            this.mode = mode2;
            this.options = options2;
            this.norm2 = mode2.getNormalizer2(options2);
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

    private static final Normalizer2 getComposeNormalizer2(boolean compat, int options2) {
        return (compat ? NFKC : NFC).getNormalizer2(options2);
    }

    private static final Normalizer2 getDecomposeNormalizer2(boolean compat, int options2) {
        return (compat ? NFKD : NFD).getNormalizer2(options2);
    }

    @Deprecated
    public static String compose(String str, boolean compat) {
        return compose(str, compat, 0);
    }

    @Deprecated
    public static String compose(String str, boolean compat, int options2) {
        return getComposeNormalizer2(compat, options2).normalize(str);
    }

    @Deprecated
    public static int compose(char[] source, char[] target, boolean compat, int options2) {
        return compose(source, 0, source.length, target, 0, target.length, compat, options2);
    }

    @Deprecated
    public static int compose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options2) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        getComposeNormalizer2(compat, options2).normalize((CharSequence) srcBuffer, (Appendable) app);
        return app.length();
    }

    @Deprecated
    public static String decompose(String str, boolean compat) {
        return decompose(str, compat, 0);
    }

    @Deprecated
    public static String decompose(String str, boolean compat, int options2) {
        return getDecomposeNormalizer2(compat, options2).normalize(str);
    }

    @Deprecated
    public static int decompose(char[] source, char[] target, boolean compat, int options2) {
        return decompose(source, 0, source.length, target, 0, target.length, compat, options2);
    }

    @Deprecated
    public static int decompose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options2) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        getDecomposeNormalizer2(compat, options2).normalize((CharSequence) srcBuffer, (Appendable) app);
        return app.length();
    }

    @Deprecated
    public static String normalize(String str, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).normalize(str);
    }

    @Deprecated
    public static String normalize(String src, Mode mode2) {
        return normalize(src, mode2, 0);
    }

    @Deprecated
    public static int normalize(char[] source, char[] target, Mode mode2, int options2) {
        return normalize(source, 0, source.length, target, 0, target.length, mode2, options2);
    }

    @Deprecated
    public static int normalize(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, Mode mode2, int options2) {
        CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
        CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
        mode2.getNormalizer2(options2).normalize((CharSequence) srcBuffer, (Appendable) app);
        return app.length();
    }

    @Deprecated
    public static String normalize(int char32, Mode mode2, int options2) {
        if (mode2 != NFD || options2 != 0) {
            return normalize(UTF16.valueOf(char32), mode2, options2);
        }
        String decomposition = Normalizer2.getNFCInstance().getDecomposition(char32);
        if (decomposition == null) {
            return UTF16.valueOf(char32);
        }
        return decomposition;
    }

    @Deprecated
    public static String normalize(int char32, Mode mode2) {
        return normalize(char32, mode2, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String source, Mode mode2) {
        return quickCheck(source, mode2, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String source, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).quickCheck(source);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] source, Mode mode2, int options2) {
        return quickCheck(source, 0, source.length, mode2, options2);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] source, int start, int limit, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).quickCheck(CharBuffer.wrap(source, start, limit - start));
    }

    @Deprecated
    public static boolean isNormalized(char[] src, int start, int limit, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).isNormalized(CharBuffer.wrap(src, start, limit - start));
    }

    @Deprecated
    public static boolean isNormalized(String str, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).isNormalized(str);
    }

    @Deprecated
    public static boolean isNormalized(int char32, Mode mode2, int options2) {
        return isNormalized(UTF16.valueOf(char32), mode2, options2);
    }

    public static int compare(char[] s1, int s1Start, int s1Limit, char[] s2, int s2Start, int s2Limit, int options2) {
        if (s1 != null && s1Start >= 0 && s1Limit >= 0 && s2 != null && s2Start >= 0 && s2Limit >= 0 && s1Limit >= s1Start && s2Limit >= s2Start) {
            return internalCompare(CharBuffer.wrap(s1, s1Start, s1Limit - s1Start), CharBuffer.wrap(s2, s2Start, s2Limit - s2Start), options2);
        }
        throw new IllegalArgumentException();
    }

    public static int compare(String s1, String s2, int options2) {
        return internalCompare(s1, s2, options2);
    }

    public static int compare(char[] s1, char[] s2, int options2) {
        return internalCompare(CharBuffer.wrap(s1), CharBuffer.wrap(s2), options2);
    }

    public static int compare(int char32a, int char32b, int options2) {
        return internalCompare(UTF16.valueOf(char32a), UTF16.valueOf(char32b), 131072 | options2);
    }

    public static int compare(int char32a, String str2, int options2) {
        return internalCompare(UTF16.valueOf(char32a), str2, options2);
    }

    @Deprecated
    public static int concatenate(char[] left, int leftStart, int leftLimit, char[] right, int rightStart, int rightLimit, char[] dest, int destStart, int destLimit, Mode mode2, int options2) {
        if (dest == null) {
            throw new IllegalArgumentException();
        } else if (right != dest || rightStart >= destLimit || destStart >= rightLimit) {
            StringBuilder destBuilder = new StringBuilder((((leftLimit - leftStart) + rightLimit) - rightStart) + 16);
            destBuilder.append(left, leftStart, leftLimit - leftStart);
            mode2.getNormalizer2(options2).append(destBuilder, CharBuffer.wrap(right, rightStart, rightLimit - rightStart));
            int destLength = destBuilder.length();
            if (destLength <= destLimit - destStart) {
                destBuilder.getChars(0, destLength, dest, destStart);
                return destLength;
            }
            throw new IndexOutOfBoundsException(Integer.toString(destLength));
        } else {
            throw new IllegalArgumentException("overlapping right and dst ranges");
        }
    }

    @Deprecated
    public static String concatenate(char[] left, char[] right, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).append(new StringBuilder(left.length + right.length + 16).append(left), CharBuffer.wrap(right)).toString();
    }

    @Deprecated
    public static String concatenate(String left, String right, Mode mode2, int options2) {
        return mode2.getNormalizer2(options2).append(new StringBuilder(left.length() + right.length() + 16).append(left), right).toString();
    }

    @Deprecated
    public static int getFC_NFKC_Closure(int c, char[] dest) {
        String closure = getFC_NFKC_Closure(c);
        int length = closure.length();
        if (!(length == 0 || dest == null || length > dest.length)) {
            closure.getChars(0, length, dest, 0);
        }
        return length;
    }

    @Deprecated
    public static String getFC_NFKC_Closure(int c) {
        Norm2AllModes.Normalizer2WithImpl access$300 = NFKCModeImpl.INSTANCE.normalizer2;
        UCaseProps csp = UCaseProps.INSTANCE;
        StringBuilder folded = new StringBuilder();
        int folded1Length = csp.toFullFolding(c, folded, 0);
        if (folded1Length < 0) {
            Normalizer2Impl nfkcImpl = access$300.impl;
            if (nfkcImpl.getCompQuickCheck(nfkcImpl.getNorm16(c)) != 0) {
                return "";
            }
            folded.appendCodePoint(c);
        } else if (folded1Length > 31) {
            folded.appendCodePoint(folded1Length);
        }
        String kc1 = access$300.normalize(folded);
        String kc2 = access$300.normalize(UCharacter.foldCase(kc1, 0));
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
        if (this.bufferPos >= this.buffer.length() && !nextNormalize()) {
            return -1;
        }
        int c = this.buffer.codePointAt(this.bufferPos);
        this.bufferPos += Character.charCount(c);
        return c;
    }

    @Deprecated
    public int previous() {
        if (this.bufferPos <= 0 && !previousNormalize()) {
            return -1;
        }
        int c = this.buffer.codePointBefore(this.bufferPos);
        this.bufferPos -= Character.charCount(c);
        return c;
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
        if (newIter != null) {
            this.text = newIter;
            reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(char[] newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter != null) {
            this.text = newIter;
            reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(String newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter != null) {
            this.text = newIter;
            reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(CharacterIterator newText) {
        UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
        if (newIter != null) {
            this.text = newIter;
            reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(UCharacterIterator newText) {
        try {
            UCharacterIterator newIter = (UCharacterIterator) newText.clone();
            if (newIter != null) {
                this.text = newIter;
                reset();
                return;
            }
            throw new IllegalStateException("Could not create a new UCharacterIterator");
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
            int nextCodePoint = this.text.nextCodePoint();
            int c2 = nextCodePoint;
            if (nextCodePoint < 0) {
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
        if (this.buffer.length() != 0) {
            return true;
        }
        return false;
    }

    private boolean previousNormalize() {
        int c;
        clearBuffer();
        this.nextIndex = this.currentIndex;
        this.text.setIndex(this.currentIndex);
        StringBuilder segment = new StringBuilder();
        do {
            int previousCodePoint = this.text.previousCodePoint();
            c = previousCodePoint;
            if (previousCodePoint < 0) {
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
        if (this.buffer.length() != 0) {
            return true;
        }
        return false;
    }

    private static int internalCompare(CharSequence s1, CharSequence s2, int options2) {
        Normalizer2 n2;
        int normOptions = options2 >>> 20;
        int options3 = options2 | 524288;
        if ((131072 & options3) == 0 || (options3 & 1) != 0) {
            if ((options3 & 1) != 0) {
                n2 = NFD.getNormalizer2(normOptions);
            } else {
                n2 = FCD.getNormalizer2(normOptions);
            }
            int spanQCYes1 = n2.spanQuickCheckYes(s1);
            int spanQCYes2 = n2.spanQuickCheckYes(s2);
            if (spanQCYes1 < s1.length()) {
                s1 = n2.normalizeSecondAndAppend(new StringBuilder(s1.length() + 16).append(s1, 0, spanQCYes1), s1.subSequence(spanQCYes1, s1.length()));
            }
            if (spanQCYes2 < s2.length()) {
                s2 = n2.normalizeSecondAndAppend(new StringBuilder(s2.length() + 16).append(s2, 0, spanQCYes2), s2.subSequence(spanQCYes2, s2.length()));
            }
        }
        return cmpEquivFold(s1, s2, options3);
    }

    private static final class CmpEquivLevel {
        CharSequence cs;
        int s;

        private CmpEquivLevel() {
        }
    }

    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[]{new CmpEquivLevel(), new CmpEquivLevel()};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0332, code lost:
        if (java.lang.Character.isLowSurrogate(r2.charAt(r11)) != false) goto L_0x0351;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0362, code lost:
        if (java.lang.Character.isLowSurrogate(r13.charAt(r14)) != false) goto L_0x0386;
     */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0249  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x02a4  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x02b7  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0312 A[EDGE_INSN: B:188:0x0312->B:149:0x0312 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0175  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01db  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int cmpEquivFold(java.lang.CharSequence r35, java.lang.CharSequence r36, int r37) {
        /*
            r0 = r37
            r1 = 0
            r2 = 0
            r3 = 524288(0x80000, float:7.34684E-40)
            r4 = r0 & r3
            r5 = 0
            if (r4 == 0) goto L_0x0012
            com.ibm.icu.impl.Norm2AllModes r4 = com.ibm.icu.impl.Norm2AllModes.getNFCInstance()
            com.ibm.icu.impl.Normalizer2Impl r4 = r4.impl
            goto L_0x0013
        L_0x0012:
            r4 = r5
        L_0x0013:
            r6 = 65536(0x10000, float:9.18355E-41)
            r7 = r0 & r6
            if (r7 == 0) goto L_0x0026
            com.ibm.icu.impl.UCaseProps r7 = com.ibm.icu.impl.UCaseProps.INSTANCE
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            goto L_0x0029
        L_0x0026:
            r7 = 0
            r9 = r5
            r8 = r5
        L_0x0029:
            r10 = 0
            int r11 = r35.length()
            r12 = 0
            int r13 = r36.length()
            r14 = 0
            r15 = r14
            r16 = r14
            r17 = -1
            r18 = r17
            r19 = r12
            r20 = r13
            r21 = r15
            r13 = r36
            r15 = r2
            r12 = r11
            r2 = r35
            r11 = r10
            r10 = r1
            r1 = r17
        L_0x004b:
            if (r1 >= 0) goto L_0x006f
        L_0x004d:
            if (r11 != r12) goto L_0x0067
            if (r16 != 0) goto L_0x0053
            r1 = -1
            goto L_0x006f
        L_0x0053:
            int r16 = r16 + -1
            r5 = r10[r16]
            java.lang.CharSequence r2 = r5.cs
            if (r2 == 0) goto L_0x0065
            r5 = r10[r16]
            int r11 = r5.s
            int r12 = r2.length()
            r5 = 0
            goto L_0x004d
        L_0x0065:
            r5 = 0
            goto L_0x0053
        L_0x0067:
            int r5 = r11 + 1
            char r1 = r2.charAt(r11)
            r11 = r5
        L_0x006f:
            r5 = r16
            if (r18 >= 0) goto L_0x00aa
            r6 = r13
            r13 = r19
            r3 = r20
        L_0x0078:
            if (r13 != r3) goto L_0x009a
            if (r21 != 0) goto L_0x0086
            r18 = -1
            r22 = r4
            r14 = r13
            r4 = r21
            r13 = r6
            goto L_0x00a6
        L_0x0086:
            int r21 = r21 + -1
            r14 = r15[r21]
            java.lang.CharSequence r6 = r14.cs
            if (r6 == 0) goto L_0x0098
            r14 = r15[r21]
            int r13 = r14.s
            int r3 = r6.length()
            r14 = 0
            goto L_0x0078
        L_0x0098:
            r14 = 0
            goto L_0x0086
        L_0x009a:
            int r14 = r13 + 1
            char r18 = r6.charAt(r13)
            r22 = r4
            r13 = r6
            r4 = r21
        L_0x00a6:
            r6 = r3
            r3 = r18
            goto L_0x00b4
        L_0x00aa:
            r22 = r4
            r3 = r18
            r14 = r19
            r6 = r20
            r4 = r21
        L_0x00b4:
            if (r1 != r3) goto L_0x00d2
            if (r1 >= 0) goto L_0x00bb
            r16 = 0
            return r16
        L_0x00bb:
            r18 = r17
            r1 = r17
            r21 = r4
            r16 = r5
            r20 = r6
            r19 = r14
            r4 = r22
        L_0x00ca:
            r3 = 524288(0x80000, float:7.34684E-40)
            r5 = 0
        L_0x00cd:
            r6 = 65536(0x10000, float:9.18355E-41)
            r14 = 0
            goto L_0x004b
        L_0x00d2:
            if (r1 >= 0) goto L_0x00d5
            return r17
        L_0x00d5:
            if (r3 >= 0) goto L_0x00da
            r16 = 1
            return r16
        L_0x00da:
            r16 = r1
            r23 = r15
            char r15 = (char) r1
            boolean r15 = com.ibm.icu.text.UTF16.isSurrogate(r15)
            if (r15 == 0) goto L_0x011c
            boolean r15 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r1)
            if (r15 == 0) goto L_0x0103
            if (r11 == r12) goto L_0x011c
            char r15 = r2.charAt(r11)
            r24 = r15
            boolean r15 = java.lang.Character.isLowSurrogate(r15)
            if (r15 == 0) goto L_0x011c
            char r15 = (char) r1
            r25 = r12
            r12 = r24
            int r16 = java.lang.Character.toCodePoint(r15, r12)
            goto L_0x011e
        L_0x0103:
            r25 = r12
            int r12 = r11 + -2
            if (r12 < 0) goto L_0x011e
            int r12 = r11 + -2
            char r12 = r2.charAt(r12)
            r15 = r12
            boolean r12 = java.lang.Character.isHighSurrogate(r12)
            if (r12 == 0) goto L_0x011e
            char r12 = (char) r1
            int r16 = java.lang.Character.toCodePoint(r15, r12)
            goto L_0x011e
        L_0x011c:
            r25 = r12
        L_0x011e:
            r12 = r16
            r15 = r3
            r26 = r15
            char r15 = (char) r3
            boolean r15 = com.ibm.icu.text.UTF16.isSurrogate(r15)
            if (r15 == 0) goto L_0x0161
            boolean r15 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r3)
            if (r15 == 0) goto L_0x0148
            if (r14 == r6) goto L_0x0161
            char r15 = r13.charAt(r14)
            r27 = r15
            boolean r15 = java.lang.Character.isLowSurrogate(r15)
            if (r15 == 0) goto L_0x0161
            char r15 = (char) r3
            r28 = r6
            r6 = r27
            int r15 = java.lang.Character.toCodePoint(r15, r6)
            goto L_0x0165
        L_0x0148:
            r28 = r6
            int r6 = r14 + -2
            if (r6 < 0) goto L_0x0163
            int r6 = r14 + -2
            char r6 = r13.charAt(r6)
            r15 = r6
            boolean r6 = java.lang.Character.isHighSurrogate(r6)
            if (r6 == 0) goto L_0x0163
            char r6 = (char) r3
            int r15 = java.lang.Character.toCodePoint(r15, r6)
            goto L_0x0165
        L_0x0161:
            r28 = r6
        L_0x0163:
            r15 = r26
        L_0x0165:
            if (r5 != 0) goto L_0x01cb
            r16 = 65536(0x10000, float:9.18355E-41)
            r18 = r0 & r16
            if (r18 == 0) goto L_0x01cb
            int r16 = r7.toFullFolding(r12, r8, r0)
            r29 = r16
            if (r16 < 0) goto L_0x01cb
            char r6 = (char) r1
            boolean r6 = com.ibm.icu.text.UTF16.isSurrogate(r6)
            if (r6 == 0) goto L_0x018d
            boolean r6 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r1)
            if (r6 == 0) goto L_0x0185
            int r11 = r11 + 1
            goto L_0x018d
        L_0x0185:
            int r14 = r14 + -1
            int r6 = r14 + -1
            char r3 = r13.charAt(r6)
        L_0x018d:
            r18 = r3
            r19 = r14
            if (r10 != 0) goto L_0x0198
            com.ibm.icu.text.Normalizer$CmpEquivLevel[] r3 = createCmpEquivLevelStack()
            r10 = r3
        L_0x0198:
            r3 = 0
            r6 = r10[r3]
            r6.cs = r2
            r6 = r10[r3]
            r6.s = r11
            int r16 = r5 + 1
            r5 = r29
            r6 = 31
            if (r5 > r6) goto L_0x01b2
            int r6 = r8.length()
            int r6 = r6 - r5
            r8.delete(r3, r6)
            goto L_0x01b8
        L_0x01b2:
            r8.setLength(r3)
            r8.appendCodePoint(r5)
        L_0x01b8:
            r2 = r8
            r11 = 0
            int r3 = r8.length()
            r1 = -1
            r12 = r3
            r21 = r4
            r4 = r22
            r15 = r23
            r20 = r28
            goto L_0x00ca
        L_0x01cb:
            if (r4 != 0) goto L_0x023d
            r6 = 65536(0x10000, float:9.18355E-41)
            r16 = r0 & r6
            if (r16 == 0) goto L_0x023d
            int r16 = r7.toFullFolding(r15, r9, r0)
            r30 = r16
            if (r16 < 0) goto L_0x023d
            char r6 = (char) r3
            boolean r6 = com.ibm.icu.text.UTF16.isSurrogate(r6)
            if (r6 == 0) goto L_0x01f3
            boolean r6 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r3)
            if (r6 == 0) goto L_0x01eb
            int r14 = r14 + 1
            goto L_0x01f3
        L_0x01eb:
            int r11 = r11 + -1
            int r6 = r11 + -1
            char r1 = r2.charAt(r6)
        L_0x01f3:
            if (r23 != 0) goto L_0x01fa
            com.ibm.icu.text.Normalizer$CmpEquivLevel[] r6 = createCmpEquivLevelStack()
            goto L_0x01fc
        L_0x01fa:
            r6 = r23
        L_0x01fc:
            r32 = r1
            r31 = r7
            r7 = 0
            r1 = r6[r7]
            r1.cs = r13
            r1 = r6[r7]
            r1.s = r14
            int r21 = r4 + 1
            r1 = r30
            r4 = 31
            if (r1 > r4) goto L_0x021a
            int r4 = r9.length()
            int r4 = r4 - r1
            r9.delete(r7, r4)
            goto L_0x0220
        L_0x021a:
            r9.setLength(r7)
            r9.appendCodePoint(r1)
        L_0x0220:
            r13 = r9
            r19 = 0
            int r20 = r9.length()
            r18 = -1
            r16 = r5
            r15 = r6
            r14 = r7
            r4 = r22
            r12 = r25
            r7 = r31
            r1 = r32
            r3 = 524288(0x80000, float:7.34684E-40)
            r5 = 0
            r6 = 65536(0x10000, float:9.18355E-41)
            goto L_0x004b
        L_0x023d:
            r31 = r7
            r7 = 0
            r6 = 2
            if (r5 >= r6) goto L_0x02a4
            r16 = 524288(0x80000, float:7.34684E-40)
            r18 = r0 & r16
            if (r18 == 0) goto L_0x02a4
            r7 = r22
            java.lang.String r16 = r7.getDecomposition(r12)
            r18 = r16
            if (r16 == 0) goto L_0x02a6
            char r6 = (char) r1
            boolean r6 = com.ibm.icu.text.UTF16.isSurrogate(r6)
            if (r6 == 0) goto L_0x026b
            boolean r6 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r1)
            if (r6 == 0) goto L_0x0263
            int r11 = r11 + 1
            goto L_0x026b
        L_0x0263:
            int r14 = r14 + -1
            int r6 = r14 + -1
            char r3 = r13.charAt(r6)
        L_0x026b:
            r19 = r14
            if (r10 != 0) goto L_0x0274
            com.ibm.icu.text.Normalizer$CmpEquivLevel[] r6 = createCmpEquivLevelStack()
            r10 = r6
        L_0x0274:
            r6 = r10[r5]
            r6.cs = r2
            r6 = r10[r5]
            r6.s = r11
            int r5 = r5 + 1
            r6 = 2
            if (r5 >= r6) goto L_0x028b
            int r6 = r5 + 1
            r5 = r10[r5]
            r14 = 0
            r5.cs = r14
            r16 = r6
            goto L_0x028d
        L_0x028b:
            r16 = r5
        L_0x028d:
            r2 = r18
            r11 = 0
            int r5 = r18.length()
            r1 = -1
            r18 = r3
            r21 = r4
            r12 = r5
            r4 = r7
            r15 = r23
            r20 = r28
            r7 = r31
            goto L_0x00ca
        L_0x02a4:
            r7 = r22
        L_0x02a6:
            r6 = 2
            if (r4 >= r6) goto L_0x0312
            r6 = 524288(0x80000, float:7.34684E-40)
            r16 = r0 & r6
            if (r16 == 0) goto L_0x0312
            java.lang.String r16 = r7.getDecomposition(r15)
            r18 = r16
            if (r16 == 0) goto L_0x0312
            char r6 = (char) r3
            boolean r6 = com.ibm.icu.text.UTF16.isSurrogate(r6)
            if (r6 == 0) goto L_0x02cf
            boolean r6 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r3)
            if (r6 == 0) goto L_0x02c7
            int r14 = r14 + 1
            goto L_0x02cf
        L_0x02c7:
            int r11 = r11 + -1
            int r6 = r11 + -1
            char r1 = r2.charAt(r6)
        L_0x02cf:
            if (r23 != 0) goto L_0x02d6
            com.ibm.icu.text.Normalizer$CmpEquivLevel[] r6 = createCmpEquivLevelStack()
            goto L_0x02d8
        L_0x02d6:
            r6 = r23
        L_0x02d8:
            r33 = r1
            r1 = r6[r4]
            r1.cs = r13
            r1 = r6[r4]
            r1.s = r14
            int r4 = r4 + 1
            r1 = 2
            if (r4 >= r1) goto L_0x02f3
            int r1 = r4 + 1
            r4 = r6[r4]
            r34 = r5
            r5 = 0
            r4.cs = r5
            r21 = r1
            goto L_0x02f8
        L_0x02f3:
            r34 = r5
            r5 = 0
            r21 = r4
        L_0x02f8:
            r13 = r18
            r19 = 0
            int r20 = r18.length()
            r1 = -1
            r18 = r1
            r15 = r6
            r4 = r7
            r12 = r25
            r7 = r31
            r1 = r33
            r16 = r34
            r3 = 524288(0x80000, float:7.34684E-40)
            goto L_0x00cd
        L_0x0312:
            r34 = r5
            r5 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r5) goto L_0x0382
            if (r3 < r5) goto L_0x0382
            r5 = 32768(0x8000, float:4.5918E-41)
            r5 = r5 & r0
            if (r5 == 0) goto L_0x0382
            r5 = 56319(0xdbff, float:7.892E-41)
            if (r1 > r5) goto L_0x0335
            r6 = r25
            if (r11 == r6) goto L_0x0337
            char r16 = r2.charAt(r11)
            boolean r16 = java.lang.Character.isLowSurrogate(r16)
            if (r16 != 0) goto L_0x0351
            goto L_0x0337
        L_0x0335:
            r6 = r25
        L_0x0337:
            char r5 = (char) r1
            boolean r5 = java.lang.Character.isLowSurrogate(r5)
            if (r5 == 0) goto L_0x034f
            int r5 = r11 + -1
            if (r5 == 0) goto L_0x034f
            int r5 = r11 + -2
            char r5 = r2.charAt(r5)
            boolean r5 = java.lang.Character.isHighSurrogate(r5)
            if (r5 == 0) goto L_0x034f
            goto L_0x0351
        L_0x034f:
            int r1 = r1 + -10240
        L_0x0351:
            r5 = 56319(0xdbff, float:7.892E-41)
            if (r3 > r5) goto L_0x0365
            r5 = r28
            if (r14 == r5) goto L_0x0367
            char r16 = r13.charAt(r14)
            boolean r16 = java.lang.Character.isLowSurrogate(r16)
            if (r16 != 0) goto L_0x0386
            goto L_0x0367
        L_0x0365:
            r5 = r28
        L_0x0367:
            char r0 = (char) r3
            boolean r0 = java.lang.Character.isLowSurrogate(r0)
            if (r0 == 0) goto L_0x037f
            int r0 = r14 + -1
            if (r0 == 0) goto L_0x037f
            int r0 = r14 + -2
            char r0 = r13.charAt(r0)
            boolean r0 = java.lang.Character.isHighSurrogate(r0)
            if (r0 == 0) goto L_0x037f
            goto L_0x0386
        L_0x037f:
            int r3 = r3 + -10240
            goto L_0x0386
        L_0x0382:
            r6 = r25
            r5 = r28
        L_0x0386:
            int r0 = r1 - r3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Normalizer.cmpEquivFold(java.lang.CharSequence, java.lang.CharSequence, int):int");
    }

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

        public Appendable append(char c) {
            if (this.offset < this.limit) {
                this.chars[this.offset] = c;
            }
            this.offset++;
            return this;
        }

        public Appendable append(CharSequence s) {
            return append(s, 0, s.length());
        }

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
