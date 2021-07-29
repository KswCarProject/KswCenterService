package com.ibm.icu.text;

import com.ibm.icu.text.SearchIterator;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public final class StringSearch extends SearchIterator {
    private static int CE_LEVEL2_BASE = 5;
    private static int CE_LEVEL3_BASE = 327680;
    private static final int CE_MATCH = -1;
    private static final int CE_NO_MATCH = 0;
    private static final int CE_SKIP_PATN = 2;
    private static final int CE_SKIP_TARG = 1;
    private static final int INITIAL_ARRAY_SIZE_ = 256;
    private static final int PRIMARYORDERMASK = -65536;
    private static final int SECONDARYORDERMASK = 65280;
    private static final int TERTIARYORDERMASK = 255;
    int ceMask_;
    private RuleBasedCollator collator_;
    private Normalizer2 nfd_;
    /* access modifiers changed from: private */
    public Pattern pattern_;
    private int strength_;
    private CollationElementIterator textIter_;
    /* access modifiers changed from: private */
    public CollationPCE textProcessedIter_;
    private boolean toShift_;
    private CollationElementIterator utilIter_;
    int variableTop_;

    public StringSearch(String pattern, CharacterIterator target, RuleBasedCollator collator, BreakIterator breakiter) {
        super(target, breakiter);
        if (!collator.getNumericCollation()) {
            this.collator_ = collator;
            this.strength_ = collator.getStrength();
            this.ceMask_ = getMask(this.strength_);
            this.toShift_ = collator.isAlternateHandlingShifted();
            this.variableTop_ = collator.getVariableTop();
            this.nfd_ = Normalizer2.getNFDInstance();
            this.pattern_ = new Pattern(pattern);
            this.search_.setMatchedLength(0);
            this.search_.matchedIndex_ = -1;
            this.utilIter_ = null;
            this.textIter_ = new CollationElementIterator(target, collator);
            this.textProcessedIter_ = null;
            ULocale collLocale = collator.getLocale(ULocale.VALID_LOCALE);
            this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance(collLocale == null ? ULocale.ROOT : collLocale);
            this.search_.internalBreakIter_.setText((CharacterIterator) target.clone());
            initialize();
            return;
        }
        throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
    }

    public StringSearch(String pattern, CharacterIterator target, RuleBasedCollator collator) {
        this(pattern, target, collator, (BreakIterator) null);
    }

    public StringSearch(String pattern, CharacterIterator target, Locale locale) {
        this(pattern, target, ULocale.forLocale(locale));
    }

    public StringSearch(String pattern, CharacterIterator target, ULocale locale) {
        this(pattern, target, (RuleBasedCollator) Collator.getInstance(locale), (BreakIterator) null);
    }

    public StringSearch(String pattern, String target) {
        this(pattern, new StringCharacterIterator(target), (RuleBasedCollator) Collator.getInstance(), (BreakIterator) null);
    }

    public RuleBasedCollator getCollator() {
        return this.collator_;
    }

    public void setCollator(RuleBasedCollator collator) {
        if (collator != null) {
            this.collator_ = collator;
            this.ceMask_ = getMask(this.collator_.getStrength());
            ULocale collLocale = collator.getLocale(ULocale.VALID_LOCALE);
            this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance(collLocale == null ? ULocale.ROOT : collLocale);
            this.search_.internalBreakIter_.setText((CharacterIterator) this.search_.text().clone());
            this.toShift_ = collator.isAlternateHandlingShifted();
            this.variableTop_ = collator.getVariableTop();
            this.textIter_ = new CollationElementIterator(this.pattern_.text_, collator);
            this.utilIter_ = new CollationElementIterator(this.pattern_.text_, collator);
            initialize();
            return;
        }
        throw new IllegalArgumentException("Collator can not be null");
    }

    public String getPattern() {
        return this.pattern_.text_;
    }

    public void setPattern(String pattern) {
        if (pattern == null || pattern.length() <= 0) {
            throw new IllegalArgumentException("Pattern to search for can not be null or of length 0");
        }
        this.pattern_.text_ = pattern;
        initialize();
    }

    public boolean isCanonical() {
        return this.search_.isCanonicalMatch_;
    }

    public void setCanonical(boolean allowCanonical) {
        this.search_.isCanonicalMatch_ = allowCanonical;
    }

    public void setTarget(CharacterIterator text) {
        super.setTarget(text);
        this.textIter_.setText(text);
    }

    public int getIndex() {
        int result = this.textIter_.getOffset();
        if (isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), result)) {
            return -1;
        }
        return result;
    }

    public void setIndex(int position) {
        super.setIndex(position);
        this.textIter_.setOffset(position);
    }

    public void reset() {
        boolean sameCollAttribute = true;
        int newStrength = this.collator_.getStrength();
        if ((this.strength_ < 3 && newStrength >= 3) || (this.strength_ >= 3 && newStrength < 3)) {
            sameCollAttribute = false;
        }
        this.strength_ = this.collator_.getStrength();
        int ceMask = getMask(this.strength_);
        if (this.ceMask_ != ceMask) {
            this.ceMask_ = ceMask;
            sameCollAttribute = false;
        }
        boolean shift = this.collator_.isAlternateHandlingShifted();
        if (this.toShift_ != shift) {
            this.toShift_ = shift;
            sameCollAttribute = false;
        }
        int varTop = this.collator_.getVariableTop();
        if (this.variableTop_ != varTop) {
            this.variableTop_ = varTop;
            sameCollAttribute = false;
        }
        if (!sameCollAttribute) {
            initialize();
        }
        this.textIter_.setText(this.search_.text());
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }

    /* access modifiers changed from: protected */
    public int handleNext(int position) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? getIndex() : this.search_.matchedIndex_ + 1;
            this.search_.setMatchedLength(0);
            this.textIter_.setOffset(this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.endIndex()) {
                this.search_.matchedIndex_ = -1;
            }
            return -1;
        }
        if (this.search_.matchedLength() <= 0) {
            this.search_.matchedIndex_ = position - 1;
        }
        this.textIter_.setOffset(position);
        if (this.search_.isCanonicalMatch_) {
            handleNextCanonical();
        } else {
            handleNextExact();
        }
        if (this.search_.matchedIndex_ == -1) {
            this.textIter_.setOffset(this.search_.endIndex());
        } else {
            this.textIter_.setOffset(this.search_.matchedIndex_);
        }
        return this.search_.matchedIndex_;
    }

    /* access modifiers changed from: protected */
    public int handlePrevious(int position) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? getIndex() : this.search_.matchedIndex_;
            if (this.search_.matchedIndex_ == this.search_.beginIndex()) {
                setMatchNotFound();
            } else {
                SearchIterator.Search search = this.search_;
                search.matchedIndex_--;
                this.textIter_.setOffset(this.search_.matchedIndex_);
                this.search_.setMatchedLength(0);
            }
        } else {
            this.textIter_.setOffset(position);
            if (this.search_.isCanonicalMatch_) {
                handlePreviousCanonical();
            } else {
                handlePreviousExact();
            }
        }
        return this.search_.matchedIndex_;
    }

    private static int getMask(int strength) {
        switch (strength) {
            case 0:
                return -65536;
            case 1:
                return -256;
            default:
                return -1;
        }
    }

    private int getCE(int sourcece) {
        int sourcece2 = sourcece & this.ceMask_;
        if (this.toShift_) {
            if (this.variableTop_ <= sourcece2) {
                return sourcece2;
            }
            if (this.strength_ >= 3) {
                return sourcece2 & -65536;
            }
            return 0;
        } else if (this.strength_ < 3 || sourcece2 != 0) {
            return sourcece2;
        } else {
            return 65535;
        }
    }

    private static int[] addToIntArray(int[] destination, int offset, int value, int increments) {
        int newlength = destination.length;
        if (offset + 1 == newlength) {
            int[] temp = new int[(newlength + increments)];
            System.arraycopy(destination, 0, temp, 0, offset);
            destination = temp;
        }
        destination[offset] = value;
        return destination;
    }

    private static long[] addToLongArray(long[] destination, int offset, int destinationlength, long value, int increments) {
        int newlength = destinationlength;
        if (offset + 1 == newlength) {
            long[] temp = new long[(newlength + increments)];
            System.arraycopy(destination, 0, temp, 0, offset);
            destination = temp;
        }
        destination[offset] = value;
        return destination;
    }

    private int initializePatternCETable() {
        int[] cetable = new int[256];
        int patternlength = this.pattern_.text_.length();
        CollationElementIterator coleiter = this.utilIter_;
        if (coleiter == null) {
            coleiter = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = coleiter;
        } else {
            coleiter.setText(this.pattern_.text_);
        }
        int offset = 0;
        int[] cetable2 = cetable;
        int result = 0;
        while (true) {
            int next = coleiter.next();
            int ce = next;
            if (next != -1) {
                int newce = getCE(ce);
                if (newce != 0) {
                    int[] temp = addToIntArray(cetable2, offset, newce, (patternlength - coleiter.getOffset()) + 1);
                    offset++;
                    cetable2 = temp;
                }
                result += coleiter.getMaxExpansion(ce) - 1;
            } else {
                cetable2[offset] = 0;
                this.pattern_.CE_ = cetable2;
                this.pattern_.CELength_ = offset;
                return result;
            }
        }
    }

    private int initializePatternPCETable() {
        long[] pcetable = new long[256];
        int pcetablesize = pcetable.length;
        int patternlength = this.pattern_.text_.length();
        CollationElementIterator coleiter = this.utilIter_;
        if (coleiter == null) {
            coleiter = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = coleiter;
        } else {
            coleiter.setText(this.pattern_.text_);
        }
        CollationElementIterator coleiter2 = coleiter;
        CollationPCE iter = new CollationPCE(coleiter2);
        int offset = 0;
        while (true) {
            CollationPCE iter2 = iter;
            long nextProcessed = iter2.nextProcessed((CollationPCE.Range) null);
            long pce = nextProcessed;
            if (nextProcessed != -1) {
                offset++;
                pcetable = addToLongArray(pcetable, offset, pcetablesize, pce, (patternlength - coleiter2.getOffset()) + 1);
                iter = iter2;
            } else {
                pcetable[offset] = 0;
                this.pattern_.PCE_ = pcetable;
                this.pattern_.PCELength_ = offset;
                return 0;
            }
        }
    }

    private int initializePattern() {
        this.pattern_.PCE_ = null;
        return initializePatternCETable();
    }

    private void initialize() {
        initializePattern();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setMatchNotFound() {
        super.setMatchNotFound();
        if (this.search_.isForwardSearching_) {
            this.textIter_.setOffset(this.search_.text().getEndIndex());
        } else {
            this.textIter_.setOffset(0);
        }
    }

    private static final boolean isOutOfBounds(int textstart, int textlimit, int offset) {
        return offset < textstart || offset > textlimit;
    }

    private boolean checkIdentical(int start, int end) {
        if (this.strength_ != 15) {
            return true;
        }
        String textstr = getString(this.targetText, start, end - start);
        if (Normalizer.quickCheck(textstr, Normalizer.NFD, 0) == Normalizer.NO) {
            textstr = Normalizer.decompose(textstr, false);
        }
        String patternstr = this.pattern_.text_;
        if (Normalizer.quickCheck(patternstr, Normalizer.NFD, 0) == Normalizer.NO) {
            patternstr = Normalizer.decompose(patternstr, false);
        }
        return textstr.equals(patternstr);
    }

    /* access modifiers changed from: private */
    public boolean initTextProcessedIter() {
        if (this.textProcessedIter_ == null) {
            this.textProcessedIter_ = new CollationPCE(this.textIter_);
            return true;
        }
        this.textProcessedIter_.init(this.textIter_);
        return true;
    }

    private int nextBoundaryAfter(int startIndex) {
        BreakIterator breakiterator = this.search_.breakIter();
        if (breakiterator == null) {
            breakiterator = this.search_.internalBreakIter_;
        }
        if (breakiterator != null) {
            return breakiterator.following(startIndex);
        }
        return startIndex;
    }

    private boolean isBreakBoundary(int index) {
        BreakIterator breakiterator = this.search_.breakIter();
        if (breakiterator == null) {
            breakiterator = this.search_.internalBreakIter_;
        }
        return breakiterator != null && breakiterator.isBoundary(index);
    }

    private static int compareCE64s(long targCE, long patCE, SearchIterator.ElementComparisonType compareType) {
        SearchIterator.ElementComparisonType elementComparisonType = compareType;
        if (targCE == patCE) {
            return -1;
        }
        if (elementComparisonType == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 0;
        }
        long targCEshifted = targCE >>> 32;
        long patCEshifted = patCE >>> 32;
        int targLev1 = (int) (targCEshifted & 4294901760L);
        int patLev1 = (int) (patCEshifted & 4294901760L);
        if (targLev1 == patLev1) {
            int targLev2 = (int) (targCEshifted & 65535);
            long j = targCEshifted;
            int patLev2 = (int) (patCEshifted & 65535);
            if (targLev2 == patLev2) {
                int targLev3 = (int) (targCE & 4294901760L);
                int patLev3 = (int) (patCE & 4294901760L);
                return (targLev3 == patLev3 || patLev3 == CE_LEVEL3_BASE || (elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev3 == CE_LEVEL3_BASE)) ? -1 : 0;
            } else if (targLev2 == 0) {
                return 1;
            } else {
                if (patLev2 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                    return 2;
                }
                return (patLev2 == CE_LEVEL2_BASE || (elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev2 == CE_LEVEL2_BASE)) ? -1 : 0;
            }
        } else if (targLev1 == 0) {
            return 1;
        } else {
            if (patLev1 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 2;
            }
            return 0;
        }
    }

    private static class Match {
        int limit_;
        int start_;

        private Match() {
            this.start_ = -1;
            this.limit_ = -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01ab A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0191  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean search(int r31, com.ibm.icu.text.StringSearch.Match r32) {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            r2 = r32
            com.ibm.icu.text.StringSearch$Pattern r3 = r0.pattern_
            int r3 = r3.CELength_
            if (r3 == 0) goto L_0x01f3
            com.ibm.icu.text.SearchIterator$Search r3 = r0.search_
            int r3 = r3.beginIndex()
            if (r1 < r3) goto L_0x01f3
            com.ibm.icu.text.SearchIterator$Search r3 = r0.search_
            int r3 = r3.endIndex()
            if (r1 > r3) goto L_0x01f3
            com.ibm.icu.text.StringSearch$Pattern r3 = r0.pattern_
            long[] r3 = r3.PCE_
            if (r3 != 0) goto L_0x0025
            r30.initializePatternPCETable()
        L_0x0025:
            com.ibm.icu.text.CollationElementIterator r3 = r0.textIter_
            r3.setOffset(r1)
            com.ibm.icu.text.StringSearch$CEBuffer r3 = new com.ibm.icu.text.StringSearch$CEBuffer
            r3.<init>(r0)
            r4 = 0
            r5 = 0
            r6 = -1
            r7 = -1
            r4 = 0
        L_0x0034:
            r8 = 1
            r9 = 0
            r10 = 0
            com.ibm.icu.text.StringSearch$CEI r12 = r3.get(r4)
            if (r12 == 0) goto L_0x01d0
            r14 = r5
            r5 = 0
        L_0x0040:
            com.ibm.icu.text.StringSearch$Pattern r15 = r0.pattern_
            int r15 = r15.PCELength_
            r13 = 1
            if (r5 >= r15) goto L_0x0077
            com.ibm.icu.text.StringSearch$Pattern r15 = r0.pattern_
            long[] r15 = r15.PCE_
            r10 = r15[r5]
            int r15 = r4 + r5
            int r15 = r15 + r9
            com.ibm.icu.text.StringSearch$CEI r14 = r3.get(r15)
            r17 = r6
            r18 = r7
            long r6 = r14.ce_
            com.ibm.icu.text.SearchIterator$Search r15 = r0.search_
            com.ibm.icu.text.SearchIterator$ElementComparisonType r15 = r15.elementComparisonType_
            int r6 = compareCE64s(r6, r10, r15)
            if (r6 != 0) goto L_0x0066
            r8 = 0
            goto L_0x007b
        L_0x0066:
            if (r6 <= 0) goto L_0x0071
            if (r6 != r13) goto L_0x006f
            int r5 = r5 + -1
            int r9 = r9 + 1
            goto L_0x0071
        L_0x006f:
            int r9 = r9 + -1
        L_0x0071:
            int r5 = r5 + r13
            r6 = r17
            r7 = r18
            goto L_0x0040
        L_0x0077:
            r17 = r6
            r18 = r7
        L_0x007b:
            com.ibm.icu.text.StringSearch$Pattern r6 = r0.pattern_
            int r6 = r6.PCELength_
            int r9 = r9 + r6
            r6 = -1
            if (r8 != 0) goto L_0x0099
            if (r14 == 0) goto L_0x008d
            long r1 = r14.ce_
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 == 0) goto L_0x0099
        L_0x008d:
            r28 = r3
            r25 = r5
            r6 = r17
            r7 = r18
            r3 = r32
            goto L_0x01c6
        L_0x0099:
            if (r8 != 0) goto L_0x00a2
            r28 = r3
            r25 = r5
            goto L_0x01b0
        L_0x00a2:
            int r1 = r4 + r9
            int r1 = r1 - r13
            com.ibm.icu.text.StringSearch$CEI r1 = r3.get(r1)
            int r2 = r12.lowIndex_
            int r15 = r1.lowIndex_
            r17 = 0
            com.ibm.icu.text.SearchIterator$Search r13 = r0.search_
            com.ibm.icu.text.SearchIterator$ElementComparisonType r13 = r13.elementComparisonType_
            com.ibm.icu.text.SearchIterator$ElementComparisonType r6 = com.ibm.icu.text.SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON
            r21 = 0
            r23 = 4294901760(0xffff0000, double:2.121963412E-314)
            if (r13 != r6) goto L_0x00e1
            int r6 = r4 + r9
            com.ibm.icu.text.StringSearch$CEI r6 = r3.get(r6)
            int r13 = r6.lowIndex_
            int r7 = r6.lowIndex_
            r25 = r5
            int r5 = r6.highIndex_
            if (r7 != r5) goto L_0x00db
            r26 = r8
            long r7 = r6.ce_
            r19 = -1
            int r5 = (r7 > r19 ? 1 : (r7 == r19 ? 0 : -1))
            if (r5 == 0) goto L_0x00dd
            r8 = 0
            r5 = r6
            goto L_0x0121
        L_0x00db:
            r26 = r8
        L_0x00dd:
            r5 = r6
        L_0x00de:
            r8 = r26
            goto L_0x0121
        L_0x00e1:
            r25 = r5
            r26 = r8
        L_0x00e5:
            int r5 = r4 + r9
            com.ibm.icu.text.StringSearch$CEI r5 = r3.get(r5)
            int r13 = r5.lowIndex_
            long r6 = r5.ce_
            r19 = -1
            int r6 = (r6 > r19 ? 1 : (r6 == r19 ? 0 : -1))
            if (r6 != 0) goto L_0x00f6
            goto L_0x00de
        L_0x00f6:
            long r6 = r5.ce_
            r8 = 32
            long r6 = r6 >>> r8
            long r6 = r6 & r23
            int r6 = (r6 > r21 ? 1 : (r6 == r21 ? 0 : -1))
            if (r6 != 0) goto L_0x0119
            long r6 = r5.ce_
            com.ibm.icu.text.SearchIterator$Search r8 = r0.search_
            com.ibm.icu.text.SearchIterator$ElementComparisonType r8 = r8.elementComparisonType_
            int r6 = compareCE64s(r6, r10, r8)
            if (r6 == 0) goto L_0x0117
            r7 = 2
            if (r6 != r7) goto L_0x0111
            goto L_0x0117
        L_0x0111:
            int r9 = r9 + 1
            r17 = r5
            goto L_0x00e5
        L_0x0117:
            r8 = 0
            goto L_0x0121
        L_0x0119:
            int r6 = r5.lowIndex_
            int r7 = r5.highIndex_
            if (r6 != r7) goto L_0x00de
            r8 = 0
        L_0x0121:
            boolean r6 = r0.isBreakBoundary(r2)
            if (r6 != 0) goto L_0x0128
            r8 = 0
        L_0x0128:
            int r6 = r12.highIndex_
            if (r2 != r6) goto L_0x012d
            r8 = 0
        L_0x012d:
            com.ibm.icu.text.BreakIterator r7 = r0.breakIterator
            if (r7 != 0) goto L_0x0166
            r27 = r6
            long r6 = r5.ce_
            r17 = 32
            long r6 = r6 >>> r17
            long r6 = r6 & r23
            int r6 = (r6 > r21 ? 1 : (r6 == r21 ? 0 : -1))
            if (r6 == 0) goto L_0x0168
            int r6 = r1.highIndex_
            if (r13 < r6) goto L_0x0168
            int r6 = r5.highIndex_
            if (r6 <= r13) goto L_0x0168
            com.ibm.icu.text.Normalizer2 r6 = r0.nfd_
            java.text.CharacterIterator r7 = r0.targetText
            int r7 = codePointAt(r7, r13)
            boolean r6 = r6.hasBoundaryBefore(r7)
            if (r6 != 0) goto L_0x0163
            com.ibm.icu.text.Normalizer2 r6 = r0.nfd_
            java.text.CharacterIterator r7 = r0.targetText
            int r7 = codePointBefore(r7, r13)
            boolean r6 = r6.hasBoundaryAfter(r7)
            if (r6 == 0) goto L_0x0168
        L_0x0163:
            r16 = 1
            goto L_0x016a
        L_0x0166:
            r27 = r6
        L_0x0168:
            r16 = 0
        L_0x016a:
            r6 = r16
            r7 = r13
            if (r15 >= r13) goto L_0x0191
            r28 = r3
            int r3 = r1.highIndex_
            if (r15 != r3) goto L_0x0180
            boolean r3 = r0.isBreakBoundary(r15)
            if (r3 == 0) goto L_0x0180
            r3 = r15
            r7 = r3
            r29 = r5
            goto L_0x0195
        L_0x0180:
            int r3 = r0.nextBoundaryAfter(r15)
            r29 = r5
            int r5 = r1.highIndex_
            if (r3 < r5) goto L_0x0195
            if (r6 == 0) goto L_0x018e
            if (r3 >= r13) goto L_0x0195
        L_0x018e:
            r7 = r3
            goto L_0x0195
        L_0x0191:
            r28 = r3
            r29 = r5
        L_0x0195:
            if (r6 != 0) goto L_0x01a1
            if (r7 <= r13) goto L_0x019a
            r8 = 0
        L_0x019a:
            boolean r3 = r0.isBreakBoundary(r7)
            if (r3 != 0) goto L_0x01a1
            r8 = 0
        L_0x01a1:
            boolean r3 = r0.checkIdentical(r2, r7)
            if (r3 != 0) goto L_0x01a9
            r3 = 0
            r8 = r3
        L_0x01a9:
            if (r8 == 0) goto L_0x01c3
            r17 = r2
            r18 = r7
        L_0x01b0:
            if (r8 != 0) goto L_0x01b6
            r18 = -1
            r17 = -1
        L_0x01b6:
            r1 = r17
            r2 = r18
            r3 = r32
            if (r3 == 0) goto L_0x01c2
            r3.start_ = r1
            r3.limit_ = r2
        L_0x01c2:
            return r8
        L_0x01c3:
            r3 = r32
            r6 = r2
        L_0x01c6:
            int r4 = r4 + 1
            r2 = r3
            r5 = r14
            r3 = r28
            r1 = r31
            goto L_0x0034
        L_0x01d0:
            r28 = r3
            r17 = r6
            r18 = r7
            r3 = r2
            com.ibm.icu.util.ICUException r1 = new com.ibm.icu.util.ICUException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "CEBuffer.get("
            r2.append(r6)
            r2.append(r4)
            java.lang.String r6 = ") returned null."
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x01f3:
            r3 = r2
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "search("
            r2.append(r4)
            r4 = r31
            r2.append(r4)
            java.lang.String r5 = ", m) - expected position to be between "
            r2.append(r5)
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            int r5 = r5.beginIndex()
            r2.append(r5)
            java.lang.String r5 = " and "
            r2.append(r5)
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            int r5 = r5.endIndex()
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.StringSearch.search(int, com.ibm.icu.text.StringSearch$Match):boolean");
    }

    private static int codePointAt(CharacterIterator iter, int index) {
        int currentIterIndex = iter.getIndex();
        char codeUnit = iter.setIndex(index);
        int cp = codeUnit;
        if (Character.isHighSurrogate(codeUnit)) {
            char nextUnit = iter.next();
            if (Character.isLowSurrogate(nextUnit)) {
                cp = Character.toCodePoint(codeUnit, nextUnit);
            }
        }
        iter.setIndex(currentIterIndex);
        return cp;
    }

    private static int codePointBefore(CharacterIterator iter, int index) {
        int currentIterIndex = iter.getIndex();
        iter.setIndex(index);
        char codeUnit = iter.previous();
        int cp = codeUnit;
        if (Character.isLowSurrogate(codeUnit)) {
            char prevUnit = iter.previous();
            if (Character.isHighSurrogate(prevUnit)) {
                cp = Character.toCodePoint(prevUnit, codeUnit);
            }
        }
        iter.setIndex(currentIterIndex);
        return cp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x013f, code lost:
        if (r0.nfd_.hasBoundaryAfter(codePointBefore(r0.targetText, r7)) != false) goto L_0x0147;
     */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0161  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean searchBackwards(int r26, com.ibm.icu.text.StringSearch.Match r27) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r27
            com.ibm.icu.text.StringSearch$Pattern r3 = r0.pattern_
            int r3 = r3.CELength_
            if (r3 == 0) goto L_0x01c7
            com.ibm.icu.text.SearchIterator$Search r3 = r0.search_
            int r3 = r3.beginIndex()
            if (r1 < r3) goto L_0x01c7
            com.ibm.icu.text.SearchIterator$Search r3 = r0.search_
            int r3 = r3.endIndex()
            if (r1 > r3) goto L_0x01c7
            com.ibm.icu.text.StringSearch$Pattern r3 = r0.pattern_
            long[] r3 = r3.PCE_
            if (r3 != 0) goto L_0x0025
            r25.initializePatternPCETable()
        L_0x0025:
            com.ibm.icu.text.StringSearch$CEBuffer r3 = new com.ibm.icu.text.StringSearch$CEBuffer
            r3.<init>(r0)
            r4 = 0
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            int r5 = r5.endIndex()
            if (r1 >= r5) goto L_0x004e
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            com.ibm.icu.text.BreakIterator r5 = r5.internalBreakIter_
            int r6 = r5.following(r1)
            com.ibm.icu.text.CollationElementIterator r7 = r0.textIter_
            r7.setOffset(r6)
            r4 = 0
        L_0x0041:
            com.ibm.icu.text.StringSearch$CEI r7 = r3.getPrevious(r4)
            int r7 = r7.lowIndex_
            if (r7 >= r1) goto L_0x004b
            goto L_0x0053
        L_0x004b:
            int r4 = r4 + 1
            goto L_0x0041
        L_0x004e:
            com.ibm.icu.text.CollationElementIterator r5 = r0.textIter_
            r5.setOffset(r1)
        L_0x0053:
            r5 = 0
            r6 = r4
            r7 = -1
            r8 = -1
        L_0x0058:
            r9 = 1
            com.ibm.icu.text.StringSearch$CEI r10 = r3.getPrevious(r4)
            if (r10 == 0) goto L_0x01a3
            r11 = 0
            com.ibm.icu.text.StringSearch$Pattern r12 = r0.pattern_
            int r12 = r12.PCELength_
            r13 = 1
            int r12 = r12 - r13
        L_0x0066:
            if (r12 < 0) goto L_0x00a5
            com.ibm.icu.text.StringSearch$Pattern r14 = r0.pattern_
            long[] r14 = r14.PCE_
            r13 = r14[r12]
            com.ibm.icu.text.StringSearch$Pattern r15 = r0.pattern_
            int r15 = r15.PCELength_
            int r15 = r15 + r4
            r16 = 1
            int r17 = r15 + -1
            int r17 = r17 - r12
            int r15 = r17 + r11
            com.ibm.icu.text.StringSearch$CEI r5 = r3.getPrevious(r15)
            r19 = r6
            r20 = r7
            long r6 = r5.ce_
            com.ibm.icu.text.SearchIterator$Search r15 = r0.search_
            com.ibm.icu.text.SearchIterator$ElementComparisonType r15 = r15.elementComparisonType_
            int r6 = compareCE64s(r6, r13, r15)
            if (r6 != 0) goto L_0x0091
            r9 = 0
            goto L_0x00a9
        L_0x0091:
            if (r6 <= 0) goto L_0x009d
            r7 = 1
            if (r6 != r7) goto L_0x009b
            int r12 = r12 + 1
            int r11 = r11 + 1
            goto L_0x009d
        L_0x009b:
            int r11 = r11 + -1
        L_0x009d:
            int r12 = r12 + -1
            r6 = r19
            r7 = r20
            r13 = 1
            goto L_0x0066
        L_0x00a5:
            r19 = r6
            r20 = r7
        L_0x00a9:
            r6 = -1
            if (r9 != 0) goto L_0x00bc
            if (r5 == 0) goto L_0x00b6
            long r13 = r5.ce_
            int r13 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r13 == 0) goto L_0x00bc
        L_0x00b6:
            r21 = r3
            r7 = r20
            goto L_0x019b
        L_0x00bc:
            if (r9 != 0) goto L_0x00c5
            r21 = r3
            r14 = r20
            goto L_0x018f
        L_0x00c5:
            com.ibm.icu.text.StringSearch$Pattern r13 = r0.pattern_
            int r13 = r13.PCELength_
            int r13 = r13 + r4
            r16 = 1
            int r13 = r13 + -1
            int r13 = r13 + r11
            com.ibm.icu.text.StringSearch$CEI r13 = r3.getPrevious(r13)
            int r14 = r13.lowIndex_
            boolean r15 = r0.isBreakBoundary(r14)
            if (r15 != 0) goto L_0x00dc
            r9 = 0
        L_0x00dc:
            int r15 = r13.highIndex_
            if (r14 != r15) goto L_0x00e1
            r9 = 0
        L_0x00e1:
            int r15 = r10.lowIndex_
            if (r4 <= 0) goto L_0x0172
            int r6 = r4 + -1
            com.ibm.icu.text.StringSearch$CEI r6 = r3.getPrevious(r6)
            int r7 = r6.lowIndex_
            r21 = r3
            int r3 = r6.highIndex_
            if (r7 != r3) goto L_0x00ff
            r22 = r8
            long r7 = r6.ce_
            r17 = -1
            int r3 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
            if (r3 == 0) goto L_0x0101
            r9 = 0
            goto L_0x0101
        L_0x00ff:
            r22 = r8
        L_0x0101:
            int r3 = r6.lowIndex_
            r7 = r3
            com.ibm.icu.text.BreakIterator r8 = r0.breakIterator
            if (r8 != 0) goto L_0x0142
            r23 = r9
            long r8 = r6.ce_
            r17 = 32
            long r8 = r8 >>> r17
            r17 = 4294901760(0xffff0000, double:2.121963412E-314)
            long r8 = r8 & r17
            r17 = 0
            int r8 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            if (r8 == 0) goto L_0x0144
            int r8 = r10.highIndex_
            if (r7 < r8) goto L_0x0144
            int r8 = r6.highIndex_
            if (r8 <= r7) goto L_0x0144
            com.ibm.icu.text.Normalizer2 r8 = r0.nfd_
            java.text.CharacterIterator r9 = r0.targetText
            int r9 = codePointAt(r9, r7)
            boolean r8 = r8.hasBoundaryBefore(r9)
            if (r8 != 0) goto L_0x0141
            com.ibm.icu.text.Normalizer2 r8 = r0.nfd_
            java.text.CharacterIterator r9 = r0.targetText
            int r9 = codePointBefore(r9, r7)
            boolean r8 = r8.hasBoundaryAfter(r9)
            if (r8 == 0) goto L_0x0144
        L_0x0141:
            goto L_0x0147
        L_0x0142:
            r23 = r9
        L_0x0144:
            r8 = 0
            r16 = r8
        L_0x0147:
            r8 = r16
            if (r15 >= r7) goto L_0x015b
            int r9 = r0.nextBoundaryAfter(r15)
            r24 = r3
            int r3 = r10.highIndex_
            if (r9 < r3) goto L_0x015d
            if (r8 == 0) goto L_0x0159
            if (r9 >= r7) goto L_0x015d
        L_0x0159:
            r3 = r9
            goto L_0x015f
        L_0x015b:
            r24 = r3
        L_0x015d:
            r3 = r24
        L_0x015f:
            if (r8 != 0) goto L_0x016f
            if (r3 <= r7) goto L_0x0166
            r9 = 0
            r23 = r9
        L_0x0166:
            boolean r9 = r0.isBreakBoundary(r3)
            if (r9 != 0) goto L_0x016f
            r6 = 0
            r9 = r6
            goto L_0x0171
        L_0x016f:
            r9 = r23
        L_0x0171:
            goto L_0x0183
        L_0x0172:
            r21 = r3
            r22 = r8
            int r3 = r0.nextBoundaryAfter(r15)
            if (r3 <= 0) goto L_0x0180
            if (r1 <= r3) goto L_0x0180
            r6 = r3
            goto L_0x0181
        L_0x0180:
            r6 = r1
        L_0x0181:
            r7 = r6
            r3 = r6
        L_0x0183:
            r8 = r3
            boolean r3 = r0.checkIdentical(r14, r8)
            if (r3 != 0) goto L_0x018c
            r3 = 0
            r9 = r3
        L_0x018c:
            if (r9 == 0) goto L_0x019a
        L_0x018f:
            if (r9 != 0) goto L_0x0193
            r8 = -1
            r14 = -1
        L_0x0193:
            if (r2 == 0) goto L_0x0199
            r2.start_ = r14
            r2.limit_ = r8
        L_0x0199:
            return r9
        L_0x019a:
            r7 = r14
        L_0x019b:
            int r4 = r4 + 1
            r6 = r19
            r3 = r21
            goto L_0x0058
        L_0x01a3:
            r21 = r3
            r19 = r6
            r20 = r7
            r22 = r8
            com.ibm.icu.util.ICUException r3 = new com.ibm.icu.util.ICUException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "CEBuffer.getPrevious("
            r6.append(r7)
            r6.append(r4)
            java.lang.String r7 = ") returned null."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r3.<init>(r6)
            throw r3
        L_0x01c7:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "searchBackwards("
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = ", m) - expected position to be between "
            r4.append(r5)
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            int r5 = r5.beginIndex()
            r4.append(r5)
            java.lang.String r5 = " and "
            r4.append(r5)
            com.ibm.icu.text.SearchIterator$Search r5 = r0.search_
            int r5 = r5.endIndex()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.StringSearch.searchBackwards(int, com.ibm.icu.text.StringSearch$Match):boolean");
    }

    private boolean handleNextExact() {
        return handleNextCommonImpl();
    }

    private boolean handleNextCanonical() {
        return handleNextCommonImpl();
    }

    private boolean handleNextCommonImpl() {
        int textOffset = this.textIter_.getOffset();
        Match match = new Match();
        if (search(textOffset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        setMatchNotFound();
        return false;
    }

    private boolean handlePreviousExact() {
        return handlePreviousCommonImpl();
    }

    private boolean handlePreviousCanonical() {
        return handlePreviousCommonImpl();
    }

    private boolean handlePreviousCommonImpl() {
        int textOffset;
        if (!this.search_.isOverlap_) {
            textOffset = this.textIter_.getOffset();
        } else if (this.search_.matchedIndex_ != -1) {
            textOffset = (this.search_.matchedIndex_ + this.search_.matchedLength()) - 1;
        } else {
            initializePatternPCETable();
            if (!initTextProcessedIter()) {
                setMatchNotFound();
                return false;
            }
            for (int nPCEs = 0; nPCEs < this.pattern_.PCELength_ - 1 && this.textProcessedIter_.nextProcessed((CollationPCE.Range) null) != -1; nPCEs++) {
            }
            textOffset = this.textIter_.getOffset();
        }
        Match match = new Match();
        if (searchBackwards(textOffset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        setMatchNotFound();
        return false;
    }

    private static final String getString(CharacterIterator text, int start, int length) {
        StringBuilder result = new StringBuilder(length);
        int offset = text.getIndex();
        text.setIndex(start);
        for (int i = 0; i < length; i++) {
            result.append(text.current());
            text.next();
        }
        text.setIndex(offset);
        return result.toString();
    }

    private static final class Pattern {
        int CELength_ = 0;
        int[] CE_;
        int PCELength_ = 0;
        long[] PCE_;
        String text_;

        protected Pattern(String pattern) {
            this.text_ = pattern;
        }
    }

    private static class CollationPCE {
        private static final int BUFFER_GROW = 8;
        private static final int CONTINUATION_MARKER = 192;
        private static final int DEFAULT_BUFFER_SIZE = 16;
        private static final int PRIMARYORDERMASK = -65536;
        public static final long PROCESSED_NULLORDER = -1;
        private CollationElementIterator cei_;
        private boolean isShifted_;
        private PCEBuffer pceBuffer_ = new PCEBuffer();
        private int strength_;
        private boolean toShift_;
        private int variableTop_;

        public static final class Range {
            int ixHigh_;
            int ixLow_;
        }

        public CollationPCE(CollationElementIterator iter) {
            init(iter);
        }

        public void init(CollationElementIterator iter) {
            this.cei_ = iter;
            init(iter.getRuleBasedCollator());
        }

        private void init(RuleBasedCollator coll) {
            this.strength_ = coll.getStrength();
            this.toShift_ = coll.isAlternateHandlingShifted();
            this.isShifted_ = false;
            this.variableTop_ = coll.getVariableTop();
        }

        private long processCE(int ce) {
            long secondary = 0;
            long tertiary = 0;
            long quaternary = 0;
            switch (this.strength_) {
                case 0:
                    break;
                case 1:
                    break;
                default:
                    tertiary = (long) CollationElementIterator.tertiaryOrder(ce);
                    break;
            }
            secondary = (long) CollationElementIterator.secondaryOrder(ce);
            long primary = (long) CollationElementIterator.primaryOrder(ce);
            if ((!this.toShift_ || this.variableTop_ <= ce || primary == 0) && (!this.isShifted_ || primary != 0)) {
                if (this.strength_ >= 3) {
                    quaternary = 65535;
                }
                this.isShifted_ = false;
            } else if (primary == 0) {
                return 0;
            } else {
                if (this.strength_ >= 3) {
                    quaternary = primary;
                }
                tertiary = 0;
                secondary = 0;
                primary = 0;
                this.isShifted_ = true;
            }
            return (primary << 48) | (secondary << 32) | (tertiary << 16) | quaternary;
        }

        public long nextProcessed(Range range) {
            int low;
            int high;
            long result;
            this.pceBuffer_.reset();
            while (true) {
                low = this.cei_.getOffset();
                int ce = this.cei_.next();
                high = this.cei_.getOffset();
                if (ce != -1) {
                    result = processCE(ce);
                    if (result != 0) {
                        break;
                    }
                } else {
                    result = -1;
                    break;
                }
            }
            if (range != null) {
                range.ixLow_ = low;
                range.ixHigh_ = high;
            }
            return result;
        }

        public long previousProcessed(Range range) {
            int high;
            int low;
            int high2 = 0;
            int high3 = 0;
            while (true) {
                if (!this.pceBuffer_.empty()) {
                    break;
                }
                RCEBuffer rceb = new RCEBuffer();
                int i = high2;
                int low2 = high3;
                boolean finish = false;
                while (true) {
                    high = this.cei_.getOffset();
                    int ce = this.cei_.previous();
                    low = this.cei_.getOffset();
                    if (ce != -1) {
                        rceb.put(ce, low, high);
                        if ((-65536 & ce) != 0 && !isContinuation(ce)) {
                            break;
                        }
                    } else if (rceb.empty()) {
                        finish = true;
                    }
                }
                if (finish) {
                    int i2 = high;
                    int high4 = low;
                    break;
                }
                while (!rceb.empty()) {
                    RCEI rcei = rceb.get();
                    long result = processCE(rcei.ce_);
                    if (result != 0) {
                        this.pceBuffer_.put(result, rcei.low_, rcei.high_);
                    }
                }
                high3 = high;
                high2 = low;
            }
            if (!this.pceBuffer_.empty()) {
                PCEI pcei = this.pceBuffer_.get();
                if (range != null) {
                    range.ixLow_ = pcei.low_;
                    range.ixHigh_ = pcei.high_;
                }
                return pcei.ce_;
            } else if (range == null) {
                return -1;
            } else {
                range.ixLow_ = -1;
                range.ixHigh_ = -1;
                return -1;
            }
        }

        private static boolean isContinuation(int ce) {
            return (ce & 192) == 192;
        }

        private static final class PCEI {
            long ce_;
            int high_;
            int low_;

            private PCEI() {
            }
        }

        private static final class PCEBuffer {
            private int bufferIndex_;
            private PCEI[] buffer_;

            private PCEBuffer() {
                this.buffer_ = new PCEI[16];
                this.bufferIndex_ = 0;
            }

            /* access modifiers changed from: package-private */
            public void reset() {
                this.bufferIndex_ = 0;
            }

            /* access modifiers changed from: package-private */
            public boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            /* access modifiers changed from: package-private */
            public void put(long ce, int ixLow, int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    PCEI[] newBuffer = new PCEI[(this.buffer_.length + 8)];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new PCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                this.bufferIndex_++;
            }

            /* access modifiers changed from: package-private */
            public PCEI get() {
                if (this.bufferIndex_ <= 0) {
                    return null;
                }
                PCEI[] pceiArr = this.buffer_;
                int i = this.bufferIndex_ - 1;
                this.bufferIndex_ = i;
                return pceiArr[i];
            }
        }

        private static final class RCEI {
            int ce_;
            int high_;
            int low_;

            private RCEI() {
            }
        }

        private static final class RCEBuffer {
            private int bufferIndex_;
            private RCEI[] buffer_;

            private RCEBuffer() {
                this.buffer_ = new RCEI[16];
                this.bufferIndex_ = 0;
            }

            /* access modifiers changed from: package-private */
            public boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            /* access modifiers changed from: package-private */
            public void put(int ce, int ixLow, int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    RCEI[] newBuffer = new RCEI[(this.buffer_.length + 8)];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new RCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                this.bufferIndex_++;
            }

            /* access modifiers changed from: package-private */
            public RCEI get() {
                if (this.bufferIndex_ <= 0) {
                    return null;
                }
                RCEI[] rceiArr = this.buffer_;
                int i = this.bufferIndex_ - 1;
                this.bufferIndex_ = i;
                return rceiArr[i];
            }
        }
    }

    private static class CEI {
        long ce_;
        int highIndex_;
        int lowIndex_;

        private CEI() {
        }
    }

    private static class CEBuffer {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CEBUFFER_EXTRA = 32;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_JAMO_L = 8;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_OTHER = 3;
        int bufSize_;
        CEI[] buf_;
        int firstIx_;
        int limitIx_;
        StringSearch strSearch_;

        static {
            Class<StringSearch> cls = StringSearch.class;
        }

        CEBuffer(StringSearch ss) {
            String patText;
            this.strSearch_ = ss;
            this.bufSize_ = ss.pattern_.PCELength_ + 32;
            if (!(ss.search_.elementComparisonType_ == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON || (patText = ss.pattern_.text_) == null)) {
                for (int i = 0; i < patText.length(); i++) {
                    if (MIGHT_BE_JAMO_L(patText.charAt(i))) {
                        this.bufSize_ += 8;
                    } else {
                        this.bufSize_ += 3;
                    }
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (ss.initTextProcessedIter()) {
                this.buf_ = new CEI[this.bufSize_];
            }
        }

        /* access modifiers changed from: package-private */
        public CEI get(int index) {
            int i = index % this.bufSize_;
            if (index >= this.firstIx_ && index < this.limitIx_) {
                return this.buf_[i];
            }
            if (index != this.limitIx_) {
                return null;
            }
            this.limitIx_++;
            if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                this.firstIx_++;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            if (this.buf_[i] == null) {
                this.buf_[i] = new CEI();
            }
            this.buf_[i].ce_ = this.strSearch_.textProcessedIter_.nextProcessed(range);
            this.buf_[i].lowIndex_ = range.ixLow_;
            this.buf_[i].highIndex_ = range.ixHigh_;
            return this.buf_[i];
        }

        /* access modifiers changed from: package-private */
        public CEI getPrevious(int index) {
            int i = index % this.bufSize_;
            if (index >= this.firstIx_ && index < this.limitIx_) {
                return this.buf_[i];
            }
            if (index != this.limitIx_) {
                return null;
            }
            this.limitIx_++;
            if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                this.firstIx_++;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            if (this.buf_[i] == null) {
                this.buf_[i] = new CEI();
            }
            this.buf_[i].ce_ = this.strSearch_.textProcessedIter_.previousProcessed(range);
            this.buf_[i].lowIndex_ = range.ixLow_;
            this.buf_[i].highIndex_ = range.ixHigh_;
            return this.buf_[i];
        }

        static boolean MIGHT_BE_JAMO_L(char c) {
            return (c >= 4352 && c <= 4446) || (c >= 12593 && c <= 12622) || (c >= 12645 && c <= 12678);
        }
    }
}
