package com.ibm.icu.text;

import com.ibm.icu.text.SearchIterator;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.util.Locale;

/* loaded from: classes5.dex */
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
    private Pattern pattern_;
    private int strength_;
    private CollationElementIterator textIter_;
    private CollationPCE textProcessedIter_;
    private boolean toShift_;
    private CollationElementIterator utilIter_;
    int variableTop_;

    public StringSearch(String pattern, CharacterIterator target, RuleBasedCollator collator, BreakIterator breakiter) {
        super(target, breakiter);
        if (collator.getNumericCollation()) {
            throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
        }
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
    }

    public StringSearch(String pattern, CharacterIterator target, RuleBasedCollator collator) {
        this(pattern, target, collator, null);
    }

    public StringSearch(String pattern, CharacterIterator target, Locale locale) {
        this(pattern, target, ULocale.forLocale(locale));
    }

    public StringSearch(String pattern, CharacterIterator target, ULocale locale) {
        this(pattern, target, (RuleBasedCollator) Collator.getInstance(locale), null);
    }

    public StringSearch(String pattern, String target) {
        this(pattern, new java.text.StringCharacterIterator(target), (RuleBasedCollator) Collator.getInstance(), null);
    }

    public RuleBasedCollator getCollator() {
        return this.collator_;
    }

    public void setCollator(RuleBasedCollator collator) {
        if (collator == null) {
            throw new IllegalArgumentException("Collator can not be null");
        }
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

    @Override // com.ibm.icu.text.SearchIterator
    public void setTarget(CharacterIterator text) {
        super.setTarget(text);
        this.textIter_.setText(text);
    }

    @Override // com.ibm.icu.text.SearchIterator
    public int getIndex() {
        int result = this.textIter_.getOffset();
        if (isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), result)) {
            return -1;
        }
        return result;
    }

    @Override // com.ibm.icu.text.SearchIterator
    public void setIndex(int position) {
        super.setIndex(position);
        this.textIter_.setOffset(position);
    }

    @Override // com.ibm.icu.text.SearchIterator
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

    @Override // com.ibm.icu.text.SearchIterator
    protected int handleNext(int position) {
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

    @Override // com.ibm.icu.text.SearchIterator
    protected int handlePrevious(int position) {
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
            if (this.variableTop_ > sourcece2) {
                if (this.strength_ >= 3) {
                    return sourcece2 & (-65536);
                }
                return 0;
            }
            return sourcece2;
        } else if (this.strength_ >= 3 && sourcece2 == 0) {
            return 65535;
        } else {
            return sourcece2;
        }
    }

    private static int[] addToIntArray(int[] destination, int offset, int value, int increments) {
        int newlength = destination.length;
        if (offset + 1 == newlength) {
            int[] temp = new int[newlength + increments];
            System.arraycopy(destination, 0, temp, 0, offset);
            destination = temp;
        }
        destination[offset] = value;
        return destination;
    }

    private static long[] addToLongArray(long[] destination, int offset, int destinationlength, long value, int increments) {
        if (offset + 1 == destinationlength) {
            int newlength = destinationlength + increments;
            long[] temp = new long[newlength];
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
            int ce = coleiter.next();
            if (ce != -1) {
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
            long pce = iter2.nextProcessed(null);
            if (pce != -1) {
                long[] temp = addToLongArray(pcetable, offset, pcetablesize, pce, (patternlength - coleiter2.getOffset()) + 1);
                offset++;
                pcetable = temp;
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

    @Override // com.ibm.icu.text.SearchIterator
    @Deprecated
    protected void setMatchNotFound() {
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
        if (Normalizer.quickCheck(textstr, Normalizer.NFD, 0) == Normalizer.f2546NO) {
            textstr = Normalizer.decompose(textstr, false);
        }
        String patternstr = this.pattern_.text_;
        if (Normalizer.quickCheck(patternstr, Normalizer.NFD, 0) == Normalizer.f2546NO) {
            patternstr = Normalizer.decompose(patternstr, false);
        }
        return textstr.equals(patternstr);
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        if (targCE == patCE) {
            return -1;
        }
        if (compareType == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 0;
        }
        long targCEshifted = targCE >>> 32;
        long patCEshifted = patCE >>> 32;
        int targLev1 = (int) (targCEshifted & 4294901760L);
        int patLev1 = (int) (patCEshifted & 4294901760L);
        if (targLev1 != patLev1) {
            if (targLev1 == 0) {
                return 1;
            }
            return (patLev1 == 0 && compareType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) ? 2 : 0;
        }
        int targLev2 = (int) (targCEshifted & 65535);
        int patLev2 = (int) (patCEshifted & 65535);
        if (targLev2 != patLev2) {
            if (targLev2 == 0) {
                return 1;
            }
            if (patLev2 == 0 && compareType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 2;
            }
            return (patLev2 == CE_LEVEL2_BASE || (compareType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev2 == CE_LEVEL2_BASE)) ? -1 : 0;
        }
        long mask = targCE & 4294901760L;
        int targLev3 = (int) mask;
        int patLev3 = (int) (patCE & 4294901760L);
        return (targLev3 == patLev3 || patLev3 == CE_LEVEL3_BASE || (compareType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev3 == CE_LEVEL3_BASE)) ? -1 : 0;
    }

    /* loaded from: classes5.dex */
    private static class Match {
        int limit_;
        int start_;

        private Match() {
            this.start_ = -1;
            this.limit_ = -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0117, code lost:
        r8 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0166 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0197  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean search(int startIdx, Match m) {
        boolean found;
        int mStart;
        int mLimit;
        boolean found2;
        CEI nextCEI;
        int maxLimit;
        int secondIx;
        boolean z;
        boolean allowMidclusterMatch;
        CEBuffer ceb;
        Match match;
        Match match2 = m;
        if (this.pattern_.CELength_ == 0 || startIdx < this.search_.beginIndex() || startIdx > this.search_.endIndex()) {
            throw new IllegalArgumentException("search(" + startIdx + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            initializePatternPCETable();
        }
        this.textIter_.setOffset(startIdx);
        CEBuffer ceb2 = new CEBuffer(this);
        CEI targetCEI = null;
        int mStart2 = -1;
        int mLimit2 = -1;
        int targetIx = 0;
        while (true) {
            found = true;
            int targetIxOffset = 0;
            long patCE = 0;
            CEI firstCEI = ceb2.get(targetIx);
            if (firstCEI == null) {
                throw new ICUException("CEBuffer.get(" + targetIx + ") returned null.");
            }
            CEI targetCEI2 = targetCEI;
            int patIx = 0;
            while (true) {
                if (patIx < this.pattern_.PCELength_) {
                    patCE = this.pattern_.PCE_[patIx];
                    targetCEI2 = ceb2.get(targetIx + patIx + targetIxOffset);
                    mStart = mStart2;
                    mLimit = mLimit2;
                    int ceMatch = compareCE64s(targetCEI2.ce_, patCE, this.search_.elementComparisonType_);
                    if (ceMatch == 0) {
                        found = false;
                        break;
                    }
                    if (ceMatch > 0) {
                        if (ceMatch == 1) {
                            patIx--;
                            targetIxOffset++;
                        } else {
                            targetIxOffset--;
                        }
                    }
                    patIx++;
                    mStart2 = mStart;
                    mLimit2 = mLimit;
                } else {
                    mStart = mStart2;
                    mLimit = mLimit2;
                    break;
                }
            }
            int targetIxOffset2 = targetIxOffset + this.pattern_.PCELength_;
            if (!found && (targetCEI2 == null || targetCEI2.ce_ != -1)) {
                ceb = ceb2;
                mStart2 = mStart;
                mLimit2 = mLimit;
                match = m;
            } else if (!found) {
                break;
            } else {
                CEI lastCEI = ceb2.get((targetIx + targetIxOffset2) - 1);
                int mStart3 = firstCEI.lowIndex_;
                int minLimit = lastCEI.lowIndex_;
                if (this.search_.elementComparisonType_ == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
                    CEI nextCEI2 = ceb2.get(targetIx + targetIxOffset2);
                    maxLimit = nextCEI2.lowIndex_;
                    int i = nextCEI2.lowIndex_;
                    int patIx2 = nextCEI2.highIndex_;
                    if (i == patIx2) {
                        found2 = found;
                        if (nextCEI2.ce_ != -1) {
                            found = false;
                            nextCEI = nextCEI2;
                            if (!isBreakBoundary(mStart3)) {
                                found = false;
                            }
                            secondIx = firstCEI.highIndex_;
                            if (mStart3 == secondIx) {
                                found = false;
                            }
                            if (this.breakIterator != null && ((nextCEI.ce_ >>> 32) & 4294901760L) != 0 && maxLimit >= lastCEI.highIndex_ && nextCEI.highIndex_ > maxLimit && (this.nfd_.hasBoundaryBefore(codePointAt(this.targetText, maxLimit)) || this.nfd_.hasBoundaryAfter(codePointBefore(this.targetText, maxLimit)))) {
                                z = true;
                                allowMidclusterMatch = z;
                                mLimit2 = maxLimit;
                                if (minLimit < maxLimit) {
                                    ceb = ceb2;
                                    if (minLimit != lastCEI.highIndex_ || !isBreakBoundary(minLimit)) {
                                        int nba = nextBoundaryAfter(minLimit);
                                        if (nba >= lastCEI.highIndex_ && (!allowMidclusterMatch || nba < maxLimit)) {
                                            mLimit2 = nba;
                                        }
                                    } else {
                                        mLimit2 = minLimit;
                                    }
                                } else {
                                    ceb = ceb2;
                                }
                                if (!allowMidclusterMatch) {
                                    if (mLimit2 > maxLimit) {
                                        found = false;
                                    }
                                    if (!isBreakBoundary(mLimit2)) {
                                        found = false;
                                    }
                                }
                                if (!checkIdentical(mStart3, mLimit2)) {
                                    found = false;
                                }
                                if (!found) {
                                    match = m;
                                    mStart2 = mStart3;
                                } else {
                                    mStart = mStart3;
                                    mLimit = mLimit2;
                                    break;
                                }
                            }
                            z = false;
                            allowMidclusterMatch = z;
                            mLimit2 = maxLimit;
                            if (minLimit < maxLimit) {
                            }
                            if (!allowMidclusterMatch) {
                            }
                            if (!checkIdentical(mStart3, mLimit2)) {
                            }
                            if (!found) {
                            }
                        }
                    } else {
                        found2 = found;
                    }
                    nextCEI = nextCEI2;
                    found = found2;
                    if (!isBreakBoundary(mStart3)) {
                    }
                    secondIx = firstCEI.highIndex_;
                    if (mStart3 == secondIx) {
                    }
                    if (this.breakIterator != null) {
                        z = true;
                        allowMidclusterMatch = z;
                        mLimit2 = maxLimit;
                        if (minLimit < maxLimit) {
                        }
                        if (!allowMidclusterMatch) {
                        }
                        if (!checkIdentical(mStart3, mLimit2)) {
                        }
                        if (!found) {
                        }
                    }
                    z = false;
                    allowMidclusterMatch = z;
                    mLimit2 = maxLimit;
                    if (minLimit < maxLimit) {
                    }
                    if (!allowMidclusterMatch) {
                    }
                    if (!checkIdentical(mStart3, mLimit2)) {
                    }
                    if (!found) {
                    }
                } else {
                    found2 = found;
                    while (true) {
                        int patIx3 = targetIx + targetIxOffset2;
                        nextCEI = ceb2.get(patIx3);
                        maxLimit = nextCEI.lowIndex_;
                        if (nextCEI.ce_ == -1) {
                            break;
                        } else if (((nextCEI.ce_ >>> 32) & 4294901760L) == 0) {
                            int ceMatch2 = compareCE64s(nextCEI.ce_, patCE, this.search_.elementComparisonType_);
                            if (ceMatch2 == 0 || ceMatch2 == 2) {
                                break;
                            }
                            targetIxOffset2++;
                        } else if (nextCEI.lowIndex_ == nextCEI.highIndex_) {
                            found = false;
                        }
                    }
                    found = found2;
                    if (!isBreakBoundary(mStart3)) {
                    }
                    secondIx = firstCEI.highIndex_;
                    if (mStart3 == secondIx) {
                    }
                    if (this.breakIterator != null) {
                    }
                    z = false;
                    allowMidclusterMatch = z;
                    mLimit2 = maxLimit;
                    if (minLimit < maxLimit) {
                    }
                    if (!allowMidclusterMatch) {
                    }
                    if (!checkIdentical(mStart3, mLimit2)) {
                    }
                    if (!found) {
                    }
                }
            }
            targetIx++;
            match2 = match;
            targetCEI = targetCEI2;
            ceb2 = ceb;
        }
        if (!found) {
            mLimit = -1;
            mStart = -1;
        }
        int mStart4 = mStart;
        int mLimit3 = mLimit;
        if (m != null) {
            m.start_ = mStart4;
            m.limit_ = mLimit3;
        }
        return found;
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0193, code lost:
        if (r27 == null) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0195, code lost:
        r27.start_ = r14;
        r27.limit_ = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0199, code lost:
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x013f, code lost:
        if (r25.nfd_.hasBoundaryAfter(codePointBefore(r25.targetText, r3)) != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x018f, code lost:
        if (r9 != false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0191, code lost:
        r8 = -1;
        r14 = -1;
     */
    /* JADX WARN: Removed duplicated region for block: B:73:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0161  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean searchBackwards(int startIdx, Match m) {
        int limitIx;
        int mStart;
        int mStart2;
        CEBuffer ceb;
        int mLimit;
        boolean found;
        boolean allowMidclusterMatch;
        int mLimit2;
        if (this.pattern_.CELength_ == 0 || startIdx < this.search_.beginIndex() || startIdx > this.search_.endIndex()) {
            throw new IllegalArgumentException("searchBackwards(" + startIdx + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            initializePatternPCETable();
        }
        CEBuffer ceb2 = new CEBuffer(this);
        int targetIx = 0;
        if (startIdx < this.search_.endIndex()) {
            BreakIterator bi = this.search_.internalBreakIter_;
            int next = bi.following(startIdx);
            this.textIter_.setOffset(next);
            targetIx = 0;
            while (ceb2.getPrevious(targetIx).lowIndex_ >= startIdx) {
                targetIx++;
            }
        } else {
            this.textIter_.setOffset(startIdx);
        }
        CEI targetCEI = null;
        int limitIx2 = targetIx;
        int mStart3 = -1;
        int mLimit3 = -1;
        while (true) {
            boolean found2 = true;
            CEI lastCEI = ceb2.getPrevious(targetIx);
            if (lastCEI == null) {
                throw new ICUException("CEBuffer.getPrevious(" + targetIx + ") returned null.");
            }
            int targetIxOffset = 0;
            int patIx = this.pattern_.PCELength_ - 1;
            while (true) {
                if (patIx >= 0) {
                    long patCE = this.pattern_.PCE_[patIx];
                    targetCEI = ceb2.getPrevious((((this.pattern_.PCELength_ + targetIx) - 1) - patIx) + targetIxOffset);
                    limitIx = limitIx2;
                    mStart = mStart3;
                    int ceMatch = compareCE64s(targetCEI.ce_, patCE, this.search_.elementComparisonType_);
                    if (ceMatch == 0) {
                        found2 = false;
                        break;
                    }
                    if (ceMatch > 0) {
                        if (ceMatch == 1) {
                            patIx++;
                            targetIxOffset++;
                        } else {
                            targetIxOffset--;
                        }
                    }
                    patIx--;
                    limitIx2 = limitIx;
                    mStart3 = mStart;
                } else {
                    limitIx = limitIx2;
                    mStart = mStart3;
                    break;
                }
            }
            if (!found2 && (targetCEI == null || targetCEI.ce_ != -1)) {
                ceb = ceb2;
                mStart3 = mStart;
            } else if (!found2) {
                mStart2 = mStart;
                break;
            } else {
                boolean z = true;
                CEI firstCEI = ceb2.getPrevious(((this.pattern_.PCELength_ + targetIx) - 1) + targetIxOffset);
                mStart2 = firstCEI.lowIndex_;
                if (!isBreakBoundary(mStart2)) {
                    found2 = false;
                }
                if (mStart2 == firstCEI.highIndex_) {
                    found2 = false;
                }
                int minLimit = lastCEI.lowIndex_;
                if (targetIx > 0) {
                    CEI nextCEI = ceb2.getPrevious(targetIx - 1);
                    ceb = ceb2;
                    if (nextCEI.lowIndex_ == nextCEI.highIndex_ && nextCEI.ce_ != -1) {
                        found2 = false;
                    }
                    int mLimit4 = nextCEI.lowIndex_;
                    if (this.breakIterator == null) {
                        found = found2;
                        if (((nextCEI.ce_ >>> 32) & 4294901760L) != 0) {
                            if (mLimit4 >= lastCEI.highIndex_) {
                                if (nextCEI.highIndex_ > mLimit4) {
                                    if (!this.nfd_.hasBoundaryBefore(codePointAt(this.targetText, mLimit4))) {
                                    }
                                    allowMidclusterMatch = z;
                                    if (minLimit >= mLimit4) {
                                        int nba = nextBoundaryAfter(minLimit);
                                        mLimit2 = mLimit4;
                                        int mLimit5 = lastCEI.highIndex_;
                                        if (nba >= mLimit5 && (!allowMidclusterMatch || nba < mLimit4)) {
                                            mLimit = nba;
                                            if (!allowMidclusterMatch) {
                                                if (mLimit > mLimit4) {
                                                    found = false;
                                                }
                                                if (!isBreakBoundary(mLimit)) {
                                                    found2 = false;
                                                }
                                            }
                                            found2 = found;
                                        }
                                    } else {
                                        mLimit2 = mLimit4;
                                    }
                                    mLimit = mLimit2;
                                    if (!allowMidclusterMatch) {
                                    }
                                    found2 = found;
                                }
                            }
                        }
                    } else {
                        found = found2;
                    }
                    z = false;
                    allowMidclusterMatch = z;
                    if (minLimit >= mLimit4) {
                    }
                    mLimit = mLimit2;
                    if (!allowMidclusterMatch) {
                    }
                    found2 = found;
                } else {
                    ceb = ceb2;
                    int nba2 = nextBoundaryAfter(minLimit);
                    mLimit = (nba2 <= 0 || startIdx <= nba2) ? startIdx : nba2;
                }
                mLimit3 = mLimit;
                if (!checkIdentical(mStart2, mLimit3)) {
                    found2 = false;
                }
                if (found2) {
                    break;
                }
                mStart3 = mStart2;
            }
            targetIx++;
            limitIx2 = limitIx;
            ceb2 = ceb;
        }
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
        if (this.search_.isOverlap_) {
            if (this.search_.matchedIndex_ != -1) {
                textOffset = (this.search_.matchedIndex_ + this.search_.matchedLength()) - 1;
            } else {
                initializePatternPCETable();
                if (!initTextProcessedIter()) {
                    setMatchNotFound();
                    return false;
                }
                for (int nPCEs = 0; nPCEs < this.pattern_.PCELength_ - 1; nPCEs++) {
                    long pce = this.textProcessedIter_.nextProcessed(null);
                    if (pce == -1) {
                        break;
                    }
                }
                textOffset = this.textIter_.getOffset();
            }
        } else {
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

    /* loaded from: classes5.dex */
    private static final class Pattern {
        int[] CE_;
        long[] PCE_;
        String text_;
        int PCELength_ = 0;
        int CELength_ = 0;

        protected Pattern(String pattern) {
            this.text_ = pattern;
        }
    }

    /* loaded from: classes5.dex */
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

        /* loaded from: classes5.dex */
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
            long quaternary;
            long secondary = 0;
            long tertiary = 0;
            switch (this.strength_) {
                case 0:
                    break;
                default:
                    tertiary = CollationElementIterator.tertiaryOrder(ce);
                case 1:
                    secondary = CollationElementIterator.secondaryOrder(ce);
                    break;
            }
            long primary = CollationElementIterator.primaryOrder(ce);
            if ((this.toShift_ && this.variableTop_ > ce && primary != 0) || (this.isShifted_ && primary == 0)) {
                if (primary == 0) {
                    return 0L;
                }
                quaternary = this.strength_ >= 3 ? primary : 0L;
                tertiary = 0;
                secondary = 0;
                primary = 0;
                this.isShifted_ = true;
            } else {
                quaternary = this.strength_ >= 3 ? 65535L : 0L;
                this.isShifted_ = false;
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
                if (ce == -1) {
                    result = -1;
                    break;
                }
                result = processCE(ce);
                if (result != 0) {
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
            while (this.pceBuffer_.empty()) {
                RCEBuffer rceb = new RCEBuffer();
                boolean finish = false;
                while (true) {
                    high = this.cei_.getOffset();
                    int ce = this.cei_.previous();
                    low = this.cei_.getOffset();
                    if (ce == -1) {
                        if (rceb.empty()) {
                            finish = true;
                        }
                    } else {
                        rceb.put(ce, low, high);
                        if (((-65536) & ce) != 0 && !isContinuation(ce)) {
                            break;
                        }
                    }
                }
                if (finish) {
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
            if (this.pceBuffer_.empty()) {
                if (range != null) {
                    range.ixLow_ = -1;
                    range.ixHigh_ = -1;
                    return -1L;
                }
                return -1L;
            }
            PCEI pcei = this.pceBuffer_.get();
            if (range != null) {
                range.ixLow_ = pcei.low_;
                range.ixHigh_ = pcei.high_;
            }
            return pcei.ce_;
        }

        private static boolean isContinuation(int ce) {
            return (ce & 192) == 192;
        }

        /* loaded from: classes5.dex */
        private static final class PCEI {
            long ce_;
            int high_;
            int low_;

            private PCEI() {
            }
        }

        /* loaded from: classes5.dex */
        private static final class PCEBuffer {
            private int bufferIndex_;
            private PCEI[] buffer_;

            private PCEBuffer() {
                this.buffer_ = new PCEI[16];
                this.bufferIndex_ = 0;
            }

            void reset() {
                this.bufferIndex_ = 0;
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(long ce, int ixLow, int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    PCEI[] newBuffer = new PCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new PCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                this.bufferIndex_++;
            }

            PCEI get() {
                if (this.bufferIndex_ > 0) {
                    PCEI[] pceiArr = this.buffer_;
                    int i = this.bufferIndex_ - 1;
                    this.bufferIndex_ = i;
                    return pceiArr[i];
                }
                return null;
            }
        }

        /* loaded from: classes5.dex */
        private static final class RCEI {
            int ce_;
            int high_;
            int low_;

            private RCEI() {
            }
        }

        /* loaded from: classes5.dex */
        private static final class RCEBuffer {
            private int bufferIndex_;
            private RCEI[] buffer_;

            private RCEBuffer() {
                this.buffer_ = new RCEI[16];
                this.bufferIndex_ = 0;
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(int ce, int ixLow, int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    RCEI[] newBuffer = new RCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new RCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                this.bufferIndex_++;
            }

            RCEI get() {
                if (this.bufferIndex_ > 0) {
                    RCEI[] rceiArr = this.buffer_;
                    int i = this.bufferIndex_ - 1;
                    this.bufferIndex_ = i;
                    return rceiArr[i];
                }
                return null;
            }
        }
    }

    /* loaded from: classes5.dex */
    private static class CEI {
        long ce_;
        int highIndex_;
        int lowIndex_;

        private CEI() {
        }
    }

    /* loaded from: classes5.dex */
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

        CEBuffer(StringSearch ss) {
            String patText;
            this.strSearch_ = ss;
            this.bufSize_ = ss.pattern_.PCELength_ + 32;
            if (ss.search_.elementComparisonType_ != SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON && (patText = ss.pattern_.text_) != null) {
                for (int i = 0; i < patText.length(); i++) {
                    char c = patText.charAt(i);
                    if (MIGHT_BE_JAMO_L(c)) {
                        this.bufSize_ += 8;
                    } else {
                        this.bufSize_ += 3;
                    }
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (!ss.initTextProcessedIter()) {
                return;
            }
            this.buf_ = new CEI[this.bufSize_];
        }

        CEI get(int index) {
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

        CEI getPrevious(int index) {
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
            return (c >= '\u1100' && c <= '\u115e') || (c >= '\u3131' && c <= '\u314e') || (c >= '\u3165' && c <= '\u3186');
        }
    }
}
