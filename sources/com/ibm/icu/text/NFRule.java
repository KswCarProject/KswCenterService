package com.ibm.icu.text;

import android.net.wifi.WifiEnterpriseConfig;
import android.provider.SettingsStringUtil;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.PluralRules;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
final class NFRule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IMPROPER_FRACTION_RULE = -2;
    static final int INFINITY_RULE = -5;
    static final int MASTER_RULE = -4;
    static final int NAN_RULE = -6;
    static final int NEGATIVE_NUMBER_RULE = -1;
    static final int PROPER_FRACTION_RULE = -3;
    private long baseValue;
    private final RuleBasedNumberFormat formatter;
    private String ruleText;
    static final Long ZERO = 0L;
    private static final String[] RULE_PREFIXES = {"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"};
    private int radix = 10;
    private short exponent = 0;
    private char decimalPoint = 0;
    private PluralFormat rulePatternFormat = null;
    private NFSubstitution sub1 = null;
    private NFSubstitution sub2 = null;

    public static void makeRules(String description, NFRuleSet owner, NFRule predecessor, RuleBasedNumberFormat ownersOwner, List<NFRule> returnList) {
        NFRule rule1 = new NFRule(ownersOwner, description);
        String description2 = rule1.ruleText;
        int brack1 = description2.indexOf(91);
        int brack2 = brack1 < 0 ? -1 : description2.indexOf(93);
        if (brack2 < 0 || brack1 > brack2 || rule1.baseValue == -3 || rule1.baseValue == -1 || rule1.baseValue == -5 || rule1.baseValue == -6) {
            rule1.extractSubstitutions(owner, description2, predecessor);
        } else {
            NFRule rule2 = null;
            StringBuilder sbuf = new StringBuilder();
            if ((rule1.baseValue > 0 && rule1.baseValue % power(rule1.radix, rule1.exponent) == 0) || rule1.baseValue == -2 || rule1.baseValue == -4) {
                rule2 = new NFRule(ownersOwner, null);
                if (rule1.baseValue >= 0) {
                    rule2.baseValue = rule1.baseValue;
                    if (!owner.isFractionSet()) {
                        rule1.baseValue++;
                    }
                } else if (rule1.baseValue == -2) {
                    rule2.baseValue = -3L;
                } else if (rule1.baseValue == -4) {
                    rule2.baseValue = rule1.baseValue;
                    rule1.baseValue = -2L;
                }
                rule2.radix = rule1.radix;
                rule2.exponent = rule1.exponent;
                sbuf.append(description2.substring(0, brack1));
                if (brack2 + 1 < description2.length()) {
                    sbuf.append(description2.substring(brack2 + 1));
                }
                rule2.extractSubstitutions(owner, sbuf.toString(), predecessor);
            }
            sbuf.setLength(0);
            sbuf.append(description2.substring(0, brack1));
            sbuf.append(description2.substring(brack1 + 1, brack2));
            if (brack2 + 1 < description2.length()) {
                sbuf.append(description2.substring(brack2 + 1));
            }
            rule1.extractSubstitutions(owner, sbuf.toString(), predecessor);
            if (rule2 != null) {
                if (rule2.baseValue >= 0) {
                    returnList.add(rule2);
                } else {
                    owner.setNonNumericalRule(rule2);
                }
            }
        }
        if (rule1.baseValue >= 0) {
            returnList.add(rule1);
        } else {
            owner.setNonNumericalRule(rule1);
        }
    }

    public NFRule(RuleBasedNumberFormat formatter, String ruleText) {
        this.ruleText = null;
        this.formatter = formatter;
        this.ruleText = ruleText != null ? parseRuleDescriptor(ruleText) : null;
    }

    private String parseRuleDescriptor(String description) {
        String description2 = description;
        int p = description2.indexOf(SettingsStringUtil.DELIMITER);
        if (p != -1) {
            String descriptor = description2.substring(0, p);
            while (true) {
                p++;
                if (p >= description.length() || !PatternProps.isWhiteSpace(description2.charAt(p))) {
                    break;
                }
            }
            description2 = description2.substring(p);
            int descriptorLength = descriptor.length();
            char firstChar = descriptor.charAt(0);
            char lastChar = descriptor.charAt(descriptorLength - 1);
            char c = '0';
            if (firstChar >= '0') {
                char c2 = '9';
                if (firstChar <= '9' && lastChar != 'x') {
                    long tempValue = 0;
                    char c3 = 0;
                    int p2 = 0;
                    while (p2 < descriptorLength) {
                        c3 = descriptor.charAt(p2);
                        if (c3 >= '0' && c3 <= '9') {
                            tempValue = (10 * tempValue) + (c3 - '0');
                        } else if (c3 == '/' || c3 == '>') {
                            break;
                        } else if (!PatternProps.isWhiteSpace(c3) && c3 != ',' && c3 != '.') {
                            throw new IllegalArgumentException("Illegal character " + c3 + " in rule descriptor");
                        }
                        p2++;
                    }
                    setBaseValue(tempValue);
                    if (c3 == '/') {
                        long tempValue2 = 0;
                        p2++;
                        while (p2 < descriptorLength) {
                            c3 = descriptor.charAt(p2);
                            if (c3 >= c && c3 <= c2) {
                                tempValue2 = (tempValue2 * 10) + (c3 - '0');
                            } else if (c3 == '>') {
                                break;
                            } else if (!PatternProps.isWhiteSpace(c3) && c3 != ',' && c3 != '.') {
                                throw new IllegalArgumentException("Illegal character " + c3 + " in rule descriptor");
                            }
                            p2++;
                            c = '0';
                            c2 = '9';
                        }
                        this.radix = (int) tempValue2;
                        if (this.radix != 0) {
                            this.exponent = expectedExponent();
                        } else {
                            throw new IllegalArgumentException("Rule can't have radix of 0");
                        }
                    }
                    if (c3 == '>') {
                        while (p2 < descriptorLength) {
                            char c4 = descriptor.charAt(p2);
                            if (c4 == '>' && this.exponent > 0) {
                                this.exponent = (short) (this.exponent - 1);
                                p2++;
                            } else {
                                throw new IllegalArgumentException("Illegal character in rule descriptor");
                            }
                        }
                    }
                }
            }
            if (descriptor.equals("-x")) {
                setBaseValue(-1L);
            } else if (descriptorLength == 3) {
                if (firstChar == '0' && lastChar == 'x') {
                    setBaseValue(-3L);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (firstChar == 'x' && lastChar == 'x') {
                    setBaseValue(-2L);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (firstChar == 'x' && lastChar == '0') {
                    setBaseValue(-4L);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (descriptor.equals("NaN")) {
                    setBaseValue(-6L);
                } else if (descriptor.equals("Inf")) {
                    setBaseValue(-5L);
                }
            }
        }
        if (description2.length() > 0 && description2.charAt(0) == '\'') {
            return description2.substring(1);
        }
        return description2;
    }

    private void extractSubstitutions(NFRuleSet owner, String ruleText, NFRule predecessor) {
        PluralRules.PluralType pluralType;
        this.ruleText = ruleText;
        this.sub1 = extractSubstitution(owner, predecessor);
        if (this.sub1 == null) {
            this.sub2 = null;
        } else {
            this.sub2 = extractSubstitution(owner, predecessor);
        }
        String ruleText2 = this.ruleText;
        int pluralRuleStart = ruleText2.indexOf("$(");
        int pluralRuleEnd = pluralRuleStart >= 0 ? ruleText2.indexOf(")$", pluralRuleStart) : -1;
        if (pluralRuleEnd >= 0) {
            int endType = ruleText2.indexOf(44, pluralRuleStart);
            if (endType < 0) {
                throw new IllegalArgumentException("Rule \"" + ruleText2 + "\" does not have a defined type");
            }
            String type = this.ruleText.substring(pluralRuleStart + 2, endType);
            if ("cardinal".equals(type)) {
                pluralType = PluralRules.PluralType.CARDINAL;
            } else if ("ordinal".equals(type)) {
                pluralType = PluralRules.PluralType.ORDINAL;
            } else {
                throw new IllegalArgumentException(type + " is an unknown type");
            }
            this.rulePatternFormat = this.formatter.createPluralFormat(pluralType, ruleText2.substring(endType + 1, pluralRuleEnd));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private NFSubstitution extractSubstitution(NFRuleSet owner, NFRule predecessor) {
        int subEnd;
        int i;
        int subStart = indexOfAnyRulePrefix(this.ruleText);
        if (subStart == -1) {
            return null;
        }
        if (this.ruleText.startsWith(">>>", subStart)) {
            i = subStart + 2;
        } else {
            char c = this.ruleText.charAt(subStart);
            int subEnd2 = this.ruleText.indexOf(c, subStart + 1);
            if (c == '<' && subEnd2 != -1 && subEnd2 < this.ruleText.length() - 1 && this.ruleText.charAt(subEnd2 + 1) == c) {
                i = subEnd2 + 1;
            } else {
                subEnd = subEnd2;
                if (subEnd != -1) {
                    return null;
                }
                NFSubstitution result = NFSubstitution.makeSubstitution(subStart, this, predecessor, owner, this.formatter, this.ruleText.substring(subStart, subEnd + 1));
                this.ruleText = this.ruleText.substring(0, subStart) + this.ruleText.substring(subEnd + 1);
                return result;
            }
        }
        subEnd = i;
        if (subEnd != -1) {
        }
    }

    final void setBaseValue(long newBaseValue) {
        this.baseValue = newBaseValue;
        this.radix = 10;
        if (this.baseValue >= 1) {
            this.exponent = expectedExponent();
            if (this.sub1 != null) {
                this.sub1.setDivisor(this.radix, this.exponent);
            }
            if (this.sub2 != null) {
                this.sub2.setDivisor(this.radix, this.exponent);
                return;
            }
            return;
        }
        this.exponent = (short) 0;
    }

    private short expectedExponent() {
        if (this.radix == 0 || this.baseValue < 1) {
            return (short) 0;
        }
        short tempResult = (short) (Math.log(this.baseValue) / Math.log(this.radix));
        if (power(this.radix, (short) (tempResult + 1)) <= this.baseValue) {
            return (short) (tempResult + 1);
        }
        return tempResult;
    }

    private static int indexOfAnyRulePrefix(String ruleText) {
        String[] strArr;
        int result = -1;
        if (ruleText.length() > 0) {
            for (String string : RULE_PREFIXES) {
                int pos = ruleText.indexOf(string);
                if (pos != -1 && (result == -1 || pos < result)) {
                    result = pos;
                }
            }
        }
        return result;
    }

    public boolean equals(Object that) {
        if (that instanceof NFRule) {
            NFRule that2 = (NFRule) that;
            return this.baseValue == that2.baseValue && this.radix == that2.radix && this.exponent == that2.exponent && this.ruleText.equals(that2.ruleText) && Objects.equals(this.sub1, that2.sub1) && Objects.equals(this.sub2, that2.sub2);
        }
        return false;
    }

    public int hashCode() {
        return 42;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.baseValue == -1) {
            result.append("-x: ");
        } else if (this.baseValue == -2) {
            result.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            result.append(this.decimalPoint != 0 ? this.decimalPoint : '.');
            result.append("x: ");
        } else if (this.baseValue == -3) {
            result.append('0');
            result.append(this.decimalPoint != 0 ? this.decimalPoint : '.');
            result.append("x: ");
        } else if (this.baseValue == -4) {
            result.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            result.append(this.decimalPoint != 0 ? this.decimalPoint : '.');
            result.append("0: ");
        } else if (this.baseValue == -5) {
            result.append("Inf: ");
        } else if (this.baseValue == -6) {
            result.append("NaN: ");
        } else {
            result.append(String.valueOf(this.baseValue));
            if (this.radix != 10) {
                result.append('/');
                result.append(this.radix);
            }
            int numCarets = expectedExponent() - this.exponent;
            for (int i = 0; i < numCarets; i++) {
                result.append('>');
            }
            result.append(PluralRules.KEYWORD_RULE_SEPARATOR);
        }
        if (this.ruleText.startsWith(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER) && (this.sub1 == null || this.sub1.getPos() != 0)) {
            result.append(android.text.format.DateFormat.QUOTE);
        }
        StringBuilder ruleTextCopy = new StringBuilder(this.ruleText);
        if (this.sub2 != null) {
            ruleTextCopy.insert(this.sub2.getPos(), this.sub2.toString());
        }
        if (this.sub1 != null) {
            ruleTextCopy.insert(this.sub1.getPos(), this.sub1.toString());
        }
        result.append(ruleTextCopy.toString());
        result.append(';');
        return result.toString();
    }

    public final char getDecimalPoint() {
        return this.decimalPoint;
    }

    public final long getBaseValue() {
        return this.baseValue;
    }

    public long getDivisor() {
        return power(this.radix, this.exponent);
    }

    public void doFormat(long number, StringBuilder toInsertInto, int pos, int recursionCount) {
        int pluralRuleStart = this.ruleText.length();
        int lengthOffset = 0;
        if (this.rulePatternFormat == null) {
            toInsertInto.insert(pos, this.ruleText);
        } else {
            pluralRuleStart = this.ruleText.indexOf("$(");
            int pluralRuleEnd = this.ruleText.indexOf(")$", pluralRuleStart);
            int initialLength = toInsertInto.length();
            if (pluralRuleEnd < this.ruleText.length() - 1) {
                toInsertInto.insert(pos, this.ruleText.substring(pluralRuleEnd + 2));
            }
            toInsertInto.insert(pos, this.rulePatternFormat.format(number / power(this.radix, this.exponent)));
            if (pluralRuleStart > 0) {
                toInsertInto.insert(pos, this.ruleText.substring(0, pluralRuleStart));
            }
            lengthOffset = this.ruleText.length() - (toInsertInto.length() - initialLength);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(number, toInsertInto, pos - (this.sub2.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
        if (this.sub1 != null) {
            this.sub1.doSubstitution(number, toInsertInto, pos - (this.sub1.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
    }

    public void doFormat(double number, StringBuilder toInsertInto, int pos, int recursionCount) {
        int pluralRuleStart = this.ruleText.length();
        int lengthOffset = 0;
        if (this.rulePatternFormat == null) {
            toInsertInto.insert(pos, this.ruleText);
        } else {
            pluralRuleStart = this.ruleText.indexOf("$(");
            int pluralRuleEnd = this.ruleText.indexOf(")$", pluralRuleStart);
            int initialLength = toInsertInto.length();
            if (pluralRuleEnd < this.ruleText.length() - 1) {
                toInsertInto.insert(pos, this.ruleText.substring(pluralRuleEnd + 2));
            }
            double pluralVal = (0.0d > number || number >= 1.0d) ? number / power(this.radix, this.exponent) : Math.round(power(this.radix, this.exponent) * number);
            toInsertInto.insert(pos, this.rulePatternFormat.format((long) pluralVal));
            if (pluralRuleStart > 0) {
                toInsertInto.insert(pos, this.ruleText.substring(0, pluralRuleStart));
            }
            lengthOffset = this.ruleText.length() - (toInsertInto.length() - initialLength);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(number, toInsertInto, pos - (this.sub2.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
        if (this.sub1 != null) {
            this.sub1.doSubstitution(number, toInsertInto, pos - (this.sub1.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
    }

    static long power(long base, short exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent can not be negative");
        }
        if (base < 0) {
            throw new IllegalArgumentException("Base can not be negative");
        }
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= base;
            }
            base *= base;
            exponent = (short) (exponent >> 1);
        }
        return result;
    }

    public boolean shouldRollBack(long number) {
        if ((this.sub1 == null || !this.sub1.isModulusSubstitution()) && (this.sub2 == null || !this.sub2.isModulusSubstitution())) {
            return false;
        }
        long divisor = power(this.radix, this.exponent);
        return number % divisor == 0 && this.baseValue % divisor != 0;
    }

    public Number doParse(String text, ParsePosition parsePosition, boolean isFractionRule, double upperBound, int nonNumericalExecutedRuleMask) {
        int start;
        ParsePosition pp;
        int i;
        String workText;
        int sub2Pos;
        NFRule nFRule;
        NFRule nFRule2 = this;
        int i2 = 0;
        ParsePosition pp2 = new ParsePosition(0);
        int sub1Pos = nFRule2.sub1 != null ? nFRule2.sub1.getPos() : nFRule2.ruleText.length();
        int sub2Pos2 = nFRule2.sub2 != null ? nFRule2.sub2.getPos() : nFRule2.ruleText.length();
        String workText2 = nFRule2.stripPrefix(text, nFRule2.ruleText.substring(0, sub1Pos), pp2);
        int prefixLength = text.length() - workText2.length();
        if (pp2.getIndex() == 0 && sub1Pos != 0) {
            return ZERO;
        }
        if (nFRule2.baseValue == -5) {
            parsePosition.setIndex(pp2.getIndex());
            return Double.valueOf(Double.POSITIVE_INFINITY);
        } else if (nFRule2.baseValue == -6) {
            parsePosition.setIndex(pp2.getIndex());
            return Double.valueOf(Double.NaN);
        } else {
            double tempBaseValue = Math.max(0L, nFRule2.baseValue);
            int highWaterMark = 0;
            double result = 0.0d;
            int start2 = 0;
            while (true) {
                pp2.setIndex(i2);
                int highWaterMark2 = highWaterMark;
                String workText3 = workText2;
                int sub2Pos3 = sub2Pos2;
                int sub1Pos2 = sub1Pos;
                double partialResult = matchToDelimiter(workText2, start2, tempBaseValue, nFRule2.ruleText.substring(sub1Pos, sub2Pos2), nFRule2.rulePatternFormat, pp2, nFRule2.sub1, upperBound, nonNumericalExecutedRuleMask).doubleValue();
                if (pp2.getIndex() != 0 || nFRule2.sub1 == null) {
                    start = pp2.getIndex();
                    String workText22 = workText3.substring(pp2.getIndex());
                    ParsePosition pp22 = new ParsePosition(0);
                    pp = pp2;
                    i = 0;
                    workText = workText3;
                    sub2Pos = sub2Pos3;
                    double partialResult2 = matchToDelimiter(workText22, 0, partialResult, nFRule2.ruleText.substring(sub2Pos3), nFRule2.rulePatternFormat, pp22, nFRule2.sub2, upperBound, nonNumericalExecutedRuleMask).doubleValue();
                    if (pp22.getIndex() == 0) {
                        nFRule = this;
                        if (nFRule.sub2 != null) {
                            highWaterMark = highWaterMark2;
                        }
                    } else {
                        nFRule = this;
                    }
                    highWaterMark = highWaterMark2;
                    if (prefixLength + pp.getIndex() + pp22.getIndex() > highWaterMark) {
                        int highWaterMark3 = prefixLength + pp.getIndex() + pp22.getIndex();
                        result = partialResult2;
                        highWaterMark = highWaterMark3;
                    }
                } else {
                    pp = pp2;
                    workText = workText3;
                    nFRule = nFRule2;
                    sub2Pos = sub2Pos3;
                    start = start2;
                    highWaterMark = highWaterMark2;
                    i = 0;
                }
                int sub2Pos4 = sub2Pos;
                if (sub1Pos2 == sub2Pos4 || pp.getIndex() <= 0 || pp.getIndex() >= workText.length() || pp.getIndex() == start) {
                    break;
                }
                sub1Pos = sub1Pos2;
                sub2Pos2 = sub2Pos4;
                start2 = start;
                nFRule2 = nFRule;
                pp2 = pp;
                workText2 = workText;
                i2 = i;
            }
            parsePosition.setIndex(highWaterMark);
            if (isFractionRule && highWaterMark > 0 && nFRule.sub1 == null) {
                result = 1.0d / result;
            }
            double result2 = result;
            if (result2 == ((long) result2)) {
                return Long.valueOf((long) result2);
            }
            return new Double(result2);
        }
    }

    private String stripPrefix(String text, String prefix, ParsePosition pp) {
        if (prefix.length() == 0) {
            return text;
        }
        int pfl = prefixLength(text, prefix);
        if (pfl != 0) {
            pp.setIndex(pp.getIndex() + pfl);
            return text.substring(pfl);
        }
        return text;
    }

    private Number matchToDelimiter(String text, int startPos, double baseVal, String delimiter, PluralFormat pluralFormatDelimiter, ParsePosition pp, NFSubstitution sub, double upperBound, int nonNumericalExecutedRuleMask) {
        if (allIgnorable(delimiter)) {
            if (sub == null) {
                return Double.valueOf(baseVal);
            }
            ParsePosition tempPP = new ParsePosition(0);
            Number result = ZERO;
            Number tempResult = sub.doParse(text, tempPP, baseVal, upperBound, this.formatter.lenientParseEnabled(), nonNumericalExecutedRuleMask);
            if (tempPP.getIndex() != 0) {
                pp.setIndex(tempPP.getIndex());
                if (tempResult != null) {
                    return tempResult;
                }
                return result;
            }
            return result;
        }
        ParsePosition tempPP2 = new ParsePosition(0);
        int[] temp = findText(text, delimiter, pluralFormatDelimiter, startPos);
        int dPos = temp[0];
        int dLen = temp[1];
        while (dPos >= 0) {
            String subText = text.substring(0, dPos);
            if (subText.length() > 0) {
                Number tempResult2 = sub.doParse(subText, tempPP2, baseVal, upperBound, this.formatter.lenientParseEnabled(), nonNumericalExecutedRuleMask);
                if (tempPP2.getIndex() == dPos) {
                    pp.setIndex(dPos + dLen);
                    return tempResult2;
                }
            }
            tempPP2.setIndex(0);
            int[] temp2 = findText(text, delimiter, pluralFormatDelimiter, dPos + dLen);
            dPos = temp2[0];
            dLen = temp2[1];
        }
        pp.setIndex(0);
        return ZERO;
    }

    private int prefixLength(String str, String prefix) {
        if (prefix.length() == 0) {
            return 0;
        }
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (scanner != null) {
            return scanner.prefixLength(str, prefix);
        }
        if (str.startsWith(prefix)) {
            return prefix.length();
        }
        return 0;
    }

    private int[] findText(String str, String key, PluralFormat pluralFormatKey, int startingAt) {
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (pluralFormatKey == null) {
            return scanner != null ? scanner.findText(str, key, startingAt) : new int[]{str.indexOf(key, startingAt), key.length()};
        }
        FieldPosition position = new FieldPosition(0);
        position.setBeginIndex(startingAt);
        pluralFormatKey.parseType(str, scanner, position);
        int start = position.getBeginIndex();
        if (start >= 0) {
            int pluralRuleStart = this.ruleText.indexOf("$(");
            int pluralRuleSuffix = this.ruleText.indexOf(")$", pluralRuleStart) + 2;
            int matchLen = position.getEndIndex() - start;
            String prefix = this.ruleText.substring(0, pluralRuleStart);
            String suffix = this.ruleText.substring(pluralRuleSuffix);
            if (str.regionMatches(start - prefix.length(), prefix, 0, prefix.length()) && str.regionMatches(start + matchLen, suffix, 0, suffix.length())) {
                return new int[]{start - prefix.length(), prefix.length() + matchLen + suffix.length()};
            }
        }
        return new int[]{-1, 0};
    }

    private boolean allIgnorable(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        return scanner != null && scanner.allIgnorable(str);
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
        if (this.sub1 != null) {
            this.sub1.setDecimalFormatSymbols(newSymbols);
        }
        if (this.sub2 != null) {
            this.sub2.setDecimalFormatSymbols(newSymbols);
        }
    }
}
