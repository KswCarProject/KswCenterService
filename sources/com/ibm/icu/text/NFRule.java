package com.ibm.icu.text;

import android.net.wifi.WifiEnterpriseConfig;
import android.provider.SettingsStringUtil;
import android.text.format.DateFormat;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.PluralRules;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;
import java.util.Objects;

final class NFRule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IMPROPER_FRACTION_RULE = -2;
    static final int INFINITY_RULE = -5;
    static final int MASTER_RULE = -4;
    static final int NAN_RULE = -6;
    static final int NEGATIVE_NUMBER_RULE = -1;
    static final int PROPER_FRACTION_RULE = -3;
    private static final String[] RULE_PREFIXES = {"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"};
    static final Long ZERO = 0L;
    private long baseValue;
    private char decimalPoint = 0;
    private short exponent = 0;
    private final RuleBasedNumberFormat formatter;
    private int radix = 10;
    private PluralFormat rulePatternFormat;
    private String ruleText;
    private NFSubstitution sub1;
    private NFSubstitution sub2;

    public static void makeRules(String description, NFRuleSet owner, NFRule predecessor, RuleBasedNumberFormat ownersOwner, List<NFRule> returnList) {
        NFRuleSet nFRuleSet = owner;
        NFRule nFRule = predecessor;
        RuleBasedNumberFormat ruleBasedNumberFormat = ownersOwner;
        List<NFRule> list = returnList;
        NFRule rule1 = new NFRule(ruleBasedNumberFormat, description);
        String description2 = rule1.ruleText;
        int brack1 = description2.indexOf(91);
        int brack2 = brack1 < 0 ? -1 : description2.indexOf(93);
        if (brack2 < 0 || brack1 > brack2 || rule1.baseValue == -3 || rule1.baseValue == -1 || rule1.baseValue == -5 || rule1.baseValue == -6) {
            rule1.extractSubstitutions(nFRuleSet, description2, nFRule);
        } else {
            NFRule rule2 = null;
            StringBuilder sbuf = new StringBuilder();
            if ((rule1.baseValue > 0 && rule1.baseValue % power((long) rule1.radix, rule1.exponent) == 0) || rule1.baseValue == -2 || rule1.baseValue == -4) {
                rule2 = new NFRule(ruleBasedNumberFormat, (String) null);
                if (rule1.baseValue >= 0) {
                    rule2.baseValue = rule1.baseValue;
                    if (!owner.isFractionSet()) {
                        rule1.baseValue++;
                    }
                } else if (rule1.baseValue == -2) {
                    rule2.baseValue = -3;
                } else if (rule1.baseValue == -4) {
                    rule2.baseValue = rule1.baseValue;
                    rule1.baseValue = -2;
                }
                rule2.radix = rule1.radix;
                rule2.exponent = rule1.exponent;
                sbuf.append(description2.substring(0, brack1));
                if (brack2 + 1 < description2.length()) {
                    sbuf.append(description2.substring(brack2 + 1));
                }
                rule2.extractSubstitutions(nFRuleSet, sbuf.toString(), nFRule);
            }
            sbuf.setLength(0);
            sbuf.append(description2.substring(0, brack1));
            sbuf.append(description2.substring(brack1 + 1, brack2));
            if (brack2 + 1 < description2.length()) {
                sbuf.append(description2.substring(brack2 + 1));
            }
            rule1.extractSubstitutions(nFRuleSet, sbuf.toString(), nFRule);
            if (rule2 != null) {
                if (rule2.baseValue >= 0) {
                    list.add(rule2);
                } else {
                    nFRuleSet.setNonNumericalRule(rule2);
                }
            }
        }
        if (rule1.baseValue >= 0) {
            list.add(rule1);
        } else {
            nFRuleSet.setNonNumericalRule(rule1);
        }
    }

    public NFRule(RuleBasedNumberFormat formatter2, String ruleText2) {
        String str = null;
        this.ruleText = null;
        this.rulePatternFormat = null;
        this.sub1 = null;
        this.sub2 = null;
        this.formatter = formatter2;
        this.ruleText = ruleText2 != null ? parseRuleDescriptor(ruleText2) : str;
    }

    private String parseRuleDescriptor(String description) {
        int p;
        String description2 = description;
        int p2 = description2.indexOf(SettingsStringUtil.DELIMITER);
        if (p2 != -1) {
            String descriptor = description2.substring(0, p2);
            while (true) {
                p2++;
                if (p2 >= description.length() || !PatternProps.isWhiteSpace(description2.charAt(p2))) {
                    description2 = description2.substring(p2);
                    int descriptorLength = descriptor.length();
                    char firstChar = descriptor.charAt(0);
                    char lastChar = descriptor.charAt(descriptorLength - 1);
                    char c = '0';
                }
            }
            description2 = description2.substring(p2);
            int descriptorLength2 = descriptor.length();
            char firstChar2 = descriptor.charAt(0);
            char lastChar2 = descriptor.charAt(descriptorLength2 - 1);
            char c2 = '0';
            if (firstChar2 >= '0') {
                char c3 = '9';
                if (firstChar2 <= '9' && lastChar2 != 'x') {
                    long tempValue = 0;
                    char c4 = 0;
                    int p3 = 0;
                    while (p < descriptorLength2) {
                        c4 = descriptor.charAt(p);
                        if (c4 < '0' || c4 > '9') {
                            if (c4 == '/' || c4 == '>') {
                                break;
                            } else if (!(PatternProps.isWhiteSpace(c4) || c4 == ',' || c4 == '.')) {
                                throw new IllegalArgumentException("Illegal character " + c4 + " in rule descriptor");
                            }
                        } else {
                            tempValue = (10 * tempValue) + ((long) (c4 - '0'));
                        }
                        p3 = p + 1;
                    }
                    setBaseValue(tempValue);
                    if (c4 == '/') {
                        long tempValue2 = 0;
                        p++;
                        while (p < descriptorLength2) {
                            c4 = descriptor.charAt(p);
                            if (c4 >= c2 && c4 <= c3) {
                                tempValue2 = (tempValue2 * 10) + ((long) (c4 - '0'));
                            } else if (c4 == '>') {
                                break;
                            } else if (!(PatternProps.isWhiteSpace(c4) || c4 == ',' || c4 == '.')) {
                                throw new IllegalArgumentException("Illegal character " + c4 + " in rule descriptor");
                            }
                            p++;
                            c2 = '0';
                            c3 = '9';
                        }
                        this.radix = (int) tempValue2;
                        if (this.radix != 0) {
                            this.exponent = expectedExponent();
                        } else {
                            throw new IllegalArgumentException("Rule can't have radix of 0");
                        }
                    }
                    if (c4 == '>') {
                        while (p < descriptorLength2) {
                            if (descriptor.charAt(p) != '>' || this.exponent <= 0) {
                                throw new IllegalArgumentException("Illegal character in rule descriptor");
                            }
                            this.exponent = (short) (this.exponent - 1);
                            p++;
                        }
                    }
                }
            }
            if (descriptor.equals("-x")) {
                setBaseValue(-1);
            } else if (descriptorLength2 == 3) {
                if (firstChar2 == '0' && lastChar2 == 'x') {
                    setBaseValue(-3);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (firstChar2 == 'x' && lastChar2 == 'x') {
                    setBaseValue(-2);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (firstChar2 == 'x' && lastChar2 == '0') {
                    setBaseValue(-4);
                    this.decimalPoint = descriptor.charAt(1);
                } else if (descriptor.equals("NaN")) {
                    setBaseValue(-6);
                } else if (descriptor.equals("Inf")) {
                    setBaseValue(-5);
                }
            }
        }
        if (description2.length() <= 0 || description2.charAt(0) != '\'') {
            return description2;
        }
        return description2.substring(1);
    }

    private void extractSubstitutions(NFRuleSet owner, String ruleText2, NFRule predecessor) {
        PluralRules.PluralType pluralType;
        this.ruleText = ruleText2;
        this.sub1 = extractSubstitution(owner, predecessor);
        if (this.sub1 == null) {
            this.sub2 = null;
        } else {
            this.sub2 = extractSubstitution(owner, predecessor);
        }
        String ruleText3 = this.ruleText;
        int pluralRuleStart = ruleText3.indexOf("$(");
        int pluralRuleEnd = pluralRuleStart >= 0 ? ruleText3.indexOf(")$", pluralRuleStart) : -1;
        if (pluralRuleEnd >= 0) {
            int endType = ruleText3.indexOf(44, pluralRuleStart);
            if (endType >= 0) {
                String type = this.ruleText.substring(pluralRuleStart + 2, endType);
                if ("cardinal".equals(type)) {
                    pluralType = PluralRules.PluralType.CARDINAL;
                } else if ("ordinal".equals(type)) {
                    pluralType = PluralRules.PluralType.ORDINAL;
                } else {
                    throw new IllegalArgumentException(type + " is an unknown type");
                }
                this.rulePatternFormat = this.formatter.createPluralFormat(pluralType, ruleText3.substring(endType + 1, pluralRuleEnd));
                return;
            }
            throw new IllegalArgumentException("Rule \"" + ruleText3 + "\" does not have a defined type");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0047 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.ibm.icu.text.NFSubstitution extractSubstitution(com.ibm.icu.text.NFRuleSet r9, com.ibm.icu.text.NFRule r10) {
        /*
            r8 = this;
            java.lang.String r0 = r8.ruleText
            int r0 = indexOfAnyRulePrefix(r0)
            r1 = 0
            r2 = -1
            if (r0 != r2) goto L_0x000b
            return r1
        L_0x000b:
            java.lang.String r3 = r8.ruleText
            java.lang.String r4 = ">>>"
            boolean r3 = r3.startsWith(r4, r0)
            if (r3 == 0) goto L_0x0019
            int r3 = r0 + 2
        L_0x0017:
            r7 = r3
            goto L_0x0045
        L_0x0019:
            java.lang.String r3 = r8.ruleText
            char r3 = r3.charAt(r0)
            java.lang.String r4 = r8.ruleText
            int r5 = r0 + 1
            int r4 = r4.indexOf(r3, r5)
            r5 = 60
            if (r3 != r5) goto L_0x0044
            if (r4 == r2) goto L_0x0044
            java.lang.String r5 = r8.ruleText
            int r5 = r5.length()
            int r5 = r5 + -1
            if (r4 >= r5) goto L_0x0044
            java.lang.String r5 = r8.ruleText
            int r6 = r4 + 1
            char r5 = r5.charAt(r6)
            if (r5 != r3) goto L_0x0044
            int r3 = r4 + 1
            goto L_0x0017
        L_0x0044:
            r7 = r4
        L_0x0045:
            if (r7 != r2) goto L_0x0048
            return r1
        L_0x0048:
            com.ibm.icu.text.RuleBasedNumberFormat r5 = r8.formatter
            java.lang.String r1 = r8.ruleText
            int r2 = r7 + 1
            java.lang.String r6 = r1.substring(r0, r2)
            r1 = r0
            r2 = r8
            r3 = r10
            r4 = r9
            com.ibm.icu.text.NFSubstitution r1 = com.ibm.icu.text.NFSubstitution.makeSubstitution(r1, r2, r3, r4, r5, r6)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r8.ruleText
            r4 = 0
            java.lang.String r3 = r3.substring(r4, r0)
            r2.append(r3)
            java.lang.String r3 = r8.ruleText
            int r4 = r7 + 1
            java.lang.String r3 = r3.substring(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r8.ruleText = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NFRule.extractSubstitution(com.ibm.icu.text.NFRuleSet, com.ibm.icu.text.NFRule):com.ibm.icu.text.NFSubstitution");
    }

    /* access modifiers changed from: package-private */
    public final void setBaseValue(long newBaseValue) {
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
        this.exponent = 0;
    }

    private short expectedExponent() {
        if (this.radix == 0 || this.baseValue < 1) {
            return 0;
        }
        short tempResult = (short) ((int) (Math.log((double) this.baseValue) / Math.log((double) this.radix)));
        if (power((long) this.radix, (short) (tempResult + 1)) <= this.baseValue) {
            return (short) (tempResult + 1);
        }
        return tempResult;
    }

    private static int indexOfAnyRulePrefix(String ruleText2) {
        int result = -1;
        if (ruleText2.length() > 0) {
            for (String string : RULE_PREFIXES) {
                int pos = ruleText2.indexOf(string);
                if (pos != -1 && (result == -1 || pos < result)) {
                    result = pos;
                }
            }
        }
        return result;
    }

    public boolean equals(Object that) {
        if (!(that instanceof NFRule)) {
            return false;
        }
        NFRule that2 = (NFRule) that;
        if (this.baseValue != that2.baseValue || this.radix != that2.radix || this.exponent != that2.exponent || !this.ruleText.equals(that2.ruleText) || !Objects.equals(this.sub1, that2.sub1) || !Objects.equals(this.sub2, that2.sub2)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 42;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.baseValue == -1) {
            result.append("-x: ");
        } else {
            char c = '.';
            if (this.baseValue == -2) {
                result.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
                if (this.decimalPoint != 0) {
                    c = this.decimalPoint;
                }
                result.append(c);
                result.append("x: ");
            } else if (this.baseValue == -3) {
                result.append('0');
                if (this.decimalPoint != 0) {
                    c = this.decimalPoint;
                }
                result.append(c);
                result.append("x: ");
            } else if (this.baseValue == -4) {
                result.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
                if (this.decimalPoint != 0) {
                    c = this.decimalPoint;
                }
                result.append(c);
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
        }
        if (this.ruleText.startsWith(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER) && (this.sub1 == null || this.sub1.getPos() != 0)) {
            result.append(DateFormat.QUOTE);
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
        return power((long) this.radix, this.exponent);
    }

    public void doFormat(long number, StringBuilder toInsertInto, int pos, int recursionCount) {
        int pluralRuleStart = this.ruleText.length();
        int lengthOffset = 0;
        int i = 0;
        if (this.rulePatternFormat == null) {
            toInsertInto.insert(pos, this.ruleText);
        } else {
            pluralRuleStart = this.ruleText.indexOf("$(");
            int pluralRuleEnd = this.ruleText.indexOf(")$", pluralRuleStart);
            int initialLength = toInsertInto.length();
            if (pluralRuleEnd < this.ruleText.length() - 1) {
                toInsertInto.insert(pos, this.ruleText.substring(pluralRuleEnd + 2));
            }
            toInsertInto.insert(pos, this.rulePatternFormat.format((double) (number / power((long) this.radix, this.exponent))));
            if (pluralRuleStart > 0) {
                toInsertInto.insert(pos, this.ruleText.substring(0, pluralRuleStart));
            }
            lengthOffset = this.ruleText.length() - (toInsertInto.length() - initialLength);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(number, toInsertInto, pos - (this.sub2.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
        if (this.sub1 != null) {
            NFSubstitution nFSubstitution = this.sub1;
            if (this.sub1.getPos() > pluralRuleStart) {
                i = lengthOffset;
            }
            nFSubstitution.doSubstitution(number, toInsertInto, pos - i, recursionCount);
        }
    }

    public void doFormat(double number, StringBuilder toInsertInto, int pos, int recursionCount) {
        double pluralVal;
        int pluralRuleStart = this.ruleText.length();
        int lengthOffset = 0;
        int i = 0;
        if (this.rulePatternFormat == null) {
            toInsertInto.insert(pos, this.ruleText);
        } else {
            pluralRuleStart = this.ruleText.indexOf("$(");
            int pluralRuleEnd = this.ruleText.indexOf(")$", pluralRuleStart);
            int initialLength = toInsertInto.length();
            if (pluralRuleEnd < this.ruleText.length() - 1) {
                toInsertInto.insert(pos, this.ruleText.substring(pluralRuleEnd + 2));
            }
            double pluralVal2 = number;
            if (0.0d > pluralVal2 || pluralVal2 >= 1.0d) {
                pluralVal = pluralVal2 / ((double) power((long) this.radix, this.exponent));
            } else {
                pluralVal = (double) Math.round(((double) power((long) this.radix, this.exponent)) * pluralVal2);
            }
            toInsertInto.insert(pos, this.rulePatternFormat.format((double) ((long) pluralVal)));
            if (pluralRuleStart > 0) {
                toInsertInto.insert(pos, this.ruleText.substring(0, pluralRuleStart));
            }
            lengthOffset = this.ruleText.length() - (toInsertInto.length() - initialLength);
        }
        if (this.sub2 != null) {
            this.sub2.doSubstitution(number, toInsertInto, pos - (this.sub2.getPos() > pluralRuleStart ? lengthOffset : 0), recursionCount);
        }
        if (this.sub1 != null) {
            NFSubstitution nFSubstitution = this.sub1;
            if (this.sub1.getPos() > pluralRuleStart) {
                i = lengthOffset;
            }
            nFSubstitution.doSubstitution(number, toInsertInto, pos - i, recursionCount);
        }
    }

    static long power(long base, short exponent2) {
        if (exponent2 < 0) {
            throw new IllegalArgumentException("Exponent can not be negative");
        } else if (base >= 0) {
            long result = 1;
            while (exponent2 > 0) {
                if ((exponent2 & 1) == 1) {
                    result *= base;
                }
                base *= base;
                exponent2 = (short) (exponent2 >> 1);
            }
            return result;
        } else {
            throw new IllegalArgumentException("Base can not be negative");
        }
    }

    public boolean shouldRollBack(long number) {
        if ((this.sub1 == null || !this.sub1.isModulusSubstitution()) && (this.sub2 == null || !this.sub2.isModulusSubstitution())) {
            return false;
        }
        long divisor = power((long) this.radix, this.exponent);
        if (number % divisor != 0 || this.baseValue % divisor == 0) {
            return false;
        }
        return true;
    }

    public Number doParse(String text, ParsePosition parsePosition, boolean isFractionRule, double upperBound, int nonNumericalExecutedRuleMask) {
        int sub2Pos;
        int i;
        String workText;
        ParsePosition pp;
        NFRule nFRule;
        int start;
        NFRule nFRule2 = this;
        ParsePosition parsePosition2 = parsePosition;
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
            parsePosition2.setIndex(pp2.getIndex());
            return Double.valueOf(Double.POSITIVE_INFINITY);
        } else if (nFRule2.baseValue == -6) {
            parsePosition2.setIndex(pp2.getIndex());
            return Double.valueOf(Double.NaN);
        } else {
            int highWaterMark = 0;
            double result = 0.0d;
            int start2 = 0;
            double tempBaseValue = (double) Math.max(0, nFRule2.baseValue);
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
                    pp = pp2;
                    ParsePosition pp22 = new ParsePosition(0);
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
                        result = partialResult2;
                        highWaterMark = prefixLength + pp.getIndex() + pp22.getIndex();
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
                int sub1Pos3 = sub1Pos2;
                int sub2Pos4 = sub2Pos;
                if (sub1Pos3 == sub2Pos4 || pp.getIndex() <= 0 || pp.getIndex() >= workText.length() || pp.getIndex() == start) {
                    parsePosition.setIndex(highWaterMark);
                } else {
                    String str = text;
                    sub1Pos = sub1Pos3;
                    sub2Pos2 = sub2Pos4;
                    start2 = start;
                    nFRule2 = nFRule;
                    pp2 = pp;
                    workText2 = workText;
                    i2 = i;
                    ParsePosition parsePosition3 = parsePosition;
                }
            }
            parsePosition.setIndex(highWaterMark);
            if (isFractionRule && highWaterMark > 0 && nFRule.sub1 == null) {
                result = 1.0d / result;
            }
            double result2 = result;
            if (result2 == ((double) ((long) result2))) {
                return Long.valueOf((long) result2);
            }
            return new Double(result2);
        }
    }

    private String stripPrefix(String text, String prefix, ParsePosition pp) {
        int pfl;
        if (prefix.length() == 0 || (pfl = prefixLength(text, prefix)) == 0) {
            return text;
        }
        pp.setIndex(pp.getIndex() + pfl);
        return text.substring(pfl);
    }

    private Number matchToDelimiter(String text, int startPos, double baseVal, String delimiter, PluralFormat pluralFormatDelimiter, ParsePosition pp, NFSubstitution sub, double upperBound, int nonNumericalExecutedRuleMask) {
        String str = text;
        String str2 = delimiter;
        PluralFormat pluralFormat = pluralFormatDelimiter;
        ParsePosition parsePosition = pp;
        if (!allIgnorable(str2)) {
            ParsePosition tempPP = new ParsePosition(0);
            int[] temp = findText(str, str2, pluralFormat, startPos);
            int dPos = temp[0];
            int dLen = temp[1];
            while (dPos >= 0) {
                String subText = str.substring(0, dPos);
                if (subText.length() > 0) {
                    Number tempResult = sub.doParse(subText, tempPP, baseVal, upperBound, this.formatter.lenientParseEnabled(), nonNumericalExecutedRuleMask);
                    if (tempPP.getIndex() == dPos) {
                        parsePosition.setIndex(dPos + dLen);
                        return tempResult;
                    }
                }
                tempPP.setIndex(0);
                int[] temp2 = findText(str, str2, pluralFormat, dPos + dLen);
                dPos = temp2[0];
                dLen = temp2[1];
            }
            parsePosition.setIndex(0);
            return ZERO;
        }
        int i = startPos;
        if (sub == null) {
            return Double.valueOf(baseVal);
        }
        ParsePosition tempPP2 = new ParsePosition(0);
        Number result = ZERO;
        Number tempResult2 = sub.doParse(text, tempPP2, baseVal, upperBound, this.formatter.lenientParseEnabled(), nonNumericalExecutedRuleMask);
        if (tempPP2.getIndex() == 0) {
            return result;
        }
        parsePosition.setIndex(tempPP2.getIndex());
        if (tempResult2 != null) {
            return tempResult2;
        }
        return result;
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
        String str2 = str;
        String str3 = key;
        PluralFormat pluralFormat = pluralFormatKey;
        int i = startingAt;
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (pluralFormat != null) {
            FieldPosition position = new FieldPosition(0);
            position.setBeginIndex(i);
            pluralFormat.parseType(str2, scanner, position);
            int start = position.getBeginIndex();
            if (start >= 0) {
                int pluralRuleStart = this.ruleText.indexOf("$(");
                int matchLen = position.getEndIndex() - start;
                String prefix = this.ruleText.substring(0, pluralRuleStart);
                String suffix = this.ruleText.substring(this.ruleText.indexOf(")$", pluralRuleStart) + 2);
                if (str2.regionMatches(start - prefix.length(), prefix, 0, prefix.length()) && str2.regionMatches(start + matchLen, suffix, 0, suffix.length())) {
                    return new int[]{start - prefix.length(), prefix.length() + matchLen + suffix.length()};
                }
            }
            return new int[]{-1, 0};
        } else if (scanner != null) {
            return scanner.findText(str2, str3, i);
        } else {
            return new int[]{str2.indexOf(str3, i), key.length()};
        }
    }

    private boolean allIgnorable(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (scanner == null || !scanner.allIgnorable(str)) {
            return false;
        }
        return true;
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
