package com.ibm.icu.text;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.text.MessagePattern;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public class PluralFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 1;
    private transient MessagePattern msgPattern;
    private NumberFormat numberFormat;
    private transient double offset;
    private Map<String, String> parsedValues;
    private String pattern;
    private PluralRules pluralRules;
    private transient PluralSelectorAdapter pluralRulesWrapper;
    private ULocale ulocale;

    /* loaded from: classes5.dex */
    interface PluralSelector {
        String select(Object obj, double d);
    }

    public PluralFormat() {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale ulocale) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
    }

    public PluralFormat(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public PluralFormat(PluralRules rules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale ulocale, PluralRules rules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
    }

    public PluralFormat(Locale locale, PluralRules rules) {
        this(ULocale.forLocale(locale), rules);
    }

    public PluralFormat(ULocale ulocale, PluralRules.PluralType type) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, type, ulocale, null);
    }

    public PluralFormat(Locale locale, PluralRules.PluralType type) {
        this(ULocale.forLocale(locale), type);
    }

    public PluralFormat(String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        applyPattern(pattern);
    }

    public PluralFormat(ULocale ulocale, String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
        applyPattern(pattern);
    }

    public PluralFormat(PluralRules rules, String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        applyPattern(pattern);
    }

    public PluralFormat(ULocale ulocale, PluralRules rules, String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
        applyPattern(pattern);
    }

    public PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, type, ulocale, null);
        applyPattern(pattern);
    }

    PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern, NumberFormat numberFormat) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0d;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        init(null, type, ulocale, numberFormat);
        applyPattern(pattern);
    }

    private void init(PluralRules rules, PluralRules.PluralType type, ULocale locale, NumberFormat numberFormat) {
        this.ulocale = locale;
        this.pluralRules = rules == null ? PluralRules.forLocale(this.ulocale, type) : rules;
        resetPattern();
        this.numberFormat = numberFormat == null ? NumberFormat.getInstance(this.ulocale) : numberFormat;
    }

    private void resetPattern() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        this.offset = 0.0d;
    }

    public void applyPattern(String pattern) {
        this.pattern = pattern;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parsePluralStyle(pattern);
            this.offset = this.msgPattern.getPluralOffset(0);
        } catch (RuntimeException e) {
            resetPattern();
            throw e;
        }
    }

    public String toPattern() {
        return this.pattern;
    }

    static int findSubMessage(MessagePattern pattern, int partIndex, PluralSelector selector, Object context, double number) {
        double offset;
        int partIndex2;
        int count = pattern.countParts();
        MessagePattern.Part part = pattern.getPart(partIndex);
        if (part.getType().hasNumericValue()) {
            offset = pattern.getNumericValue(part);
            partIndex2 = partIndex + 1;
        } else {
            offset = 0.0d;
            partIndex2 = partIndex;
        }
        String keyword = null;
        boolean haveKeywordMatch = false;
        int msgStart = 0;
        do {
            int partIndex3 = partIndex2 + 1;
            MessagePattern.Part part2 = pattern.getPart(partIndex2);
            MessagePattern.Part.Type type = part2.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            if (pattern.getPartType(partIndex3).hasNumericValue()) {
                int partIndex4 = partIndex3 + 1;
                if (number == pattern.getNumericValue(pattern.getPart(partIndex3))) {
                    return partIndex4;
                }
                partIndex3 = partIndex4;
            } else if (!haveKeywordMatch) {
                if (pattern.partSubstringMatches(part2, "other")) {
                    if (msgStart == 0) {
                        msgStart = partIndex3;
                        if (keyword != null && keyword.equals("other")) {
                            haveKeywordMatch = true;
                        }
                    }
                } else {
                    if (keyword == null) {
                        keyword = selector.select(context, number - offset);
                        if (msgStart != 0 && keyword.equals("other")) {
                            haveKeywordMatch = true;
                        }
                    }
                    if (!haveKeywordMatch && pattern.partSubstringMatches(part2, keyword)) {
                        msgStart = partIndex3;
                        haveKeywordMatch = true;
                    }
                }
            }
            partIndex2 = pattern.getLimitPartIndex(partIndex3) + 1;
        } while (partIndex2 < count);
        return msgStart;
    }

    /* loaded from: classes5.dex */
    private final class PluralSelectorAdapter implements PluralSelector {
        private PluralSelectorAdapter() {
        }

        @Override // com.ibm.icu.text.PluralFormat.PluralSelector
        public String select(Object context, double number) {
            PluralRules.IFixedDecimal dec = (PluralRules.IFixedDecimal) context;
            return PluralFormat.this.pluralRules.select(dec);
        }
    }

    public final String format(double number) {
        return format(Double.valueOf(number), number);
    }

    @Override // java.text.Format
    public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
        if (!(number instanceof Number)) {
            throw new IllegalArgumentException("'" + number + "' is not a Number");
        }
        Number numberObject = (Number) number;
        toAppendTo.append(format(numberObject, numberObject.doubleValue()));
        return toAppendTo;
    }

    private String format(Number numberObject, double number) {
        String numberString;
        String numberString2;
        PluralRules.IFixedDecimal dec;
        int index;
        FormattedNumber result;
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            return this.numberFormat.format(numberObject);
        }
        double numberMinusOffset = number - this.offset;
        if (this.numberFormat instanceof DecimalFormat) {
            LocalizedNumberFormatter f = ((DecimalFormat) this.numberFormat).toNumberFormatter();
            if (this.offset == 0.0d) {
                result = f.format(numberObject);
            } else {
                result = f.format(numberMinusOffset);
            }
            numberString2 = result.toString();
            dec = result.getFixedDecimal();
        } else {
            if (this.offset == 0.0d) {
                numberString = this.numberFormat.format(numberObject);
            } else {
                numberString = this.numberFormat.format(numberMinusOffset);
            }
            numberString2 = numberString;
            dec = new PluralRules.FixedDecimal(numberMinusOffset);
        }
        int partIndex = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, dec, number);
        StringBuilder result2 = null;
        int prevIndex = this.msgPattern.getPart(partIndex).getLimit();
        while (true) {
            partIndex++;
            MessagePattern.Part part = this.msgPattern.getPart(partIndex);
            MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            } else if (type == MessagePattern.Part.Type.REPLACE_NUMBER || (type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode())) {
                if (result2 == null) {
                    result2 = new StringBuilder();
                }
                result2.append((CharSequence) this.pattern, prevIndex, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    result2.append(numberString2);
                }
                prevIndex = part.getLimit();
            } else if (type == MessagePattern.Part.Type.ARG_START) {
                if (result2 == null) {
                    result2 = new StringBuilder();
                }
                result2.append((CharSequence) this.pattern, prevIndex, index);
                partIndex = this.msgPattern.getLimitPartIndex(partIndex);
                int index2 = this.msgPattern.getPart(partIndex).getLimit();
                MessagePattern.appendReducedApostrophes(this.pattern, index, index2, result2);
                prevIndex = index2;
            }
        }
        if (result2 == null) {
            return this.pattern.substring(prevIndex, index);
        }
        result2.append((CharSequence) this.pattern, prevIndex, index);
        return result2.toString();
    }

    public Number parse(String text, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.Format
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }

    String parseType(String source, RbnfLenientScanner scanner, FieldPosition pos) {
        int currMatchIndex;
        PluralFormat pluralFormat = this;
        String str = source;
        if (pluralFormat.msgPattern == null || pluralFormat.msgPattern.countParts() == 0) {
            pos.setBeginIndex(-1);
            pos.setEndIndex(-1);
            return null;
        }
        int count = pluralFormat.msgPattern.countParts();
        int startingAt = pos.getBeginIndex();
        if (startingAt < 0) {
            startingAt = 0;
        }
        String matchedWord = null;
        String keyword = null;
        int partIndex = 0;
        int partIndex2 = -1;
        while (partIndex < count) {
            int partIndex3 = partIndex + 1;
            MessagePattern.Part partSelector = pluralFormat.msgPattern.getPart(partIndex);
            if (partSelector.getType() != MessagePattern.Part.Type.ARG_SELECTOR) {
                partIndex = partIndex3;
            } else {
                int partIndex4 = partIndex3 + 1;
                MessagePattern.Part partStart = pluralFormat.msgPattern.getPart(partIndex3);
                if (partStart.getType() != MessagePattern.Part.Type.MSG_START) {
                    partIndex = partIndex4;
                } else {
                    int partIndex5 = partIndex4 + 1;
                    MessagePattern.Part partLimit = pluralFormat.msgPattern.getPart(partIndex4);
                    if (partLimit.getType() != MessagePattern.Part.Type.MSG_LIMIT) {
                        partIndex = partIndex5;
                    } else {
                        String currArg = pluralFormat.pattern.substring(partStart.getLimit(), partLimit.getIndex());
                        if (scanner != null) {
                            int[] scannerMatchResult = scanner.findText(str, currArg, startingAt);
                            currMatchIndex = scannerMatchResult[0];
                        } else {
                            currMatchIndex = str.indexOf(currArg, startingAt);
                        }
                        if (currMatchIndex >= 0 && currMatchIndex >= partIndex2 && (matchedWord == null || currArg.length() > matchedWord.length())) {
                            int matchedIndex = currMatchIndex;
                            String matchedWord2 = pluralFormat.pattern;
                            keyword = matchedWord2.substring(partStart.getLimit(), partLimit.getIndex());
                            matchedWord = currArg;
                            partIndex2 = matchedIndex;
                        }
                        partIndex = partIndex5;
                        pluralFormat = this;
                        str = source;
                    }
                }
            }
        }
        if (keyword != null) {
            pos.setBeginIndex(partIndex2);
            pos.setEndIndex(matchedWord.length() + partIndex2);
            return keyword;
        }
        pos.setBeginIndex(-1);
        pos.setEndIndex(-1);
        return null;
    }

    @Deprecated
    public void setLocale(ULocale ulocale) {
        if (ulocale == null) {
            ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
    }

    public void setNumberFormat(NumberFormat format) {
        this.numberFormat = format;
    }

    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (rhs == null || getClass() != rhs.getClass()) {
            return false;
        }
        PluralFormat pf = (PluralFormat) rhs;
        if (Objects.equals(this.ulocale, pf.ulocale) && Objects.equals(this.pluralRules, pf.pluralRules) && Objects.equals(this.msgPattern, pf.msgPattern) && Objects.equals(this.numberFormat, pf.numberFormat)) {
            return true;
        }
        return false;
    }

    public boolean equals(PluralFormat rhs) {
        return equals((Object) rhs);
    }

    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("locale=" + this.ulocale);
        buf.append(", rules='" + this.pluralRules + "'");
        buf.append(", pattern='" + this.pattern + "'");
        buf.append(", format='" + this.numberFormat + "'");
        return buf.toString();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.parsedValues = null;
        if (this.pattern != null) {
            applyPattern(this.pattern);
        }
    }
}
