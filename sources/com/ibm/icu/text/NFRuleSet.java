package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
final class NFRuleSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IMPROPER_FRACTION_RULE_INDEX = 1;
    static final int INFINITY_RULE_INDEX = 4;
    static final int MASTER_RULE_INDEX = 3;
    static final int NAN_RULE_INDEX = 5;
    static final int NEGATIVE_RULE_INDEX = 0;
    static final int PROPER_FRACTION_RULE_INDEX = 2;
    private static final int RECURSION_LIMIT = 64;
    LinkedList<NFRule> fractionRules;
    private final boolean isParseable;
    private final String name;
    final RuleBasedNumberFormat owner;
    private NFRule[] rules;
    final NFRule[] nonNumericalRules = new NFRule[6];
    private boolean isFractionRuleSet = false;

    public NFRuleSet(RuleBasedNumberFormat owner, String[] descriptions, int index) throws IllegalArgumentException {
        this.owner = owner;
        String description = descriptions[index];
        if (description.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (description.charAt(0) == '%') {
            int pos = description.indexOf(58);
            if (pos == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            String name = description.substring(0, pos);
            this.isParseable = true ^ name.endsWith("@noparse");
            this.name = this.isParseable ? name : name.substring(0, name.length() - 8);
            while (pos < description.length()) {
                pos++;
                if (!PatternProps.isWhiteSpace(description.charAt(pos))) {
                    break;
                }
            }
            description = description.substring(pos);
            descriptions[index] = description;
        } else {
            this.name = "%default";
            this.isParseable = true;
        }
        if (description.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
    }

    public void parseRules(String description) {
        List<NFRule> tempRules = new ArrayList<>();
        NFRule predecessor = null;
        int oldP = 0;
        int descriptionLen = description.length();
        do {
            int p = description.indexOf(59, oldP);
            if (p < 0) {
                p = descriptionLen;
            }
            NFRule.makeRules(description.substring(oldP, p), this, predecessor, this.owner, tempRules);
            if (!tempRules.isEmpty()) {
                NFRule predecessor2 = tempRules.get(tempRules.size() - 1);
                predecessor = predecessor2;
            }
            oldP = p + 1;
        } while (oldP < descriptionLen);
        long defaultBaseValue = 0;
        for (NFRule rule : tempRules) {
            long baseValue = rule.getBaseValue();
            if (baseValue == 0) {
                rule.setBaseValue(defaultBaseValue);
            } else if (baseValue < defaultBaseValue) {
                throw new IllegalArgumentException("Rules are not in order, base: " + baseValue + " < " + defaultBaseValue);
            } else {
                defaultBaseValue = baseValue;
            }
            if (!this.isFractionRuleSet) {
                defaultBaseValue++;
            }
        }
        this.rules = new NFRule[tempRules.size()];
        tempRules.toArray(this.rules);
    }

    void setNonNumericalRule(NFRule rule) {
        long baseValue = rule.getBaseValue();
        if (baseValue == -1) {
            this.nonNumericalRules[0] = rule;
        } else if (baseValue == -2) {
            setBestFractionRule(1, rule, true);
        } else if (baseValue == -3) {
            setBestFractionRule(2, rule, true);
        } else if (baseValue == -4) {
            setBestFractionRule(3, rule, true);
        } else if (baseValue == -5) {
            this.nonNumericalRules[4] = rule;
        } else if (baseValue == -6) {
            this.nonNumericalRules[5] = rule;
        }
    }

    private void setBestFractionRule(int originalIndex, NFRule newRule, boolean rememberRule) {
        if (rememberRule) {
            if (this.fractionRules == null) {
                this.fractionRules = new LinkedList<>();
            }
            this.fractionRules.add(newRule);
        }
        NFRule bestResult = this.nonNumericalRules[originalIndex];
        if (bestResult == null) {
            this.nonNumericalRules[originalIndex] = newRule;
            return;
        }
        DecimalFormatSymbols decimalFormatSymbols = this.owner.getDecimalFormatSymbols();
        if (decimalFormatSymbols.getDecimalSeparator() == newRule.getDecimalPoint()) {
            this.nonNumericalRules[originalIndex] = newRule;
        }
    }

    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }

    public boolean equals(Object that) {
        if (that instanceof NFRuleSet) {
            NFRuleSet that2 = (NFRuleSet) that;
            if (this.name.equals(that2.name) && this.rules.length == that2.rules.length && this.isFractionRuleSet == that2.isFractionRuleSet) {
                for (int i = 0; i < this.nonNumericalRules.length; i++) {
                    if (!Objects.equals(this.nonNumericalRules[i], that2.nonNumericalRules[i])) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < this.rules.length; i2++) {
                    if (!this.rules[i2].equals(that2.rules[i2])) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return 42;
    }

    public String toString() {
        NFRule[] nFRuleArr;
        StringBuilder result = new StringBuilder();
        result.append(this.name);
        result.append(":\n");
        for (NFRule rule : this.rules) {
            result.append(rule.toString());
            result.append("\n");
        }
        for (NFRule rule2 : this.nonNumericalRules) {
            if (rule2 != null) {
                if (rule2.getBaseValue() == -2 || rule2.getBaseValue() == -3 || rule2.getBaseValue() == -4) {
                    Iterator<NFRule> it = this.fractionRules.iterator();
                    while (it.hasNext()) {
                        NFRule fractionRule = it.next();
                        if (fractionRule.getBaseValue() == rule2.getBaseValue()) {
                            result.append(fractionRule.toString());
                            result.append("\n");
                        }
                    }
                } else {
                    result.append(rule2.toString());
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPublic() {
        return !this.name.startsWith("%%");
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public void format(long number, StringBuilder toInsertInto, int pos, int recursionCount) {
        if (recursionCount >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        NFRule applicableRule = findNormalRule(number);
        applicableRule.doFormat(number, toInsertInto, pos, recursionCount + 1);
    }

    public void format(double number, StringBuilder toInsertInto, int pos, int recursionCount) {
        if (recursionCount >= 64) {
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        NFRule applicableRule = findRule(number);
        applicableRule.doFormat(number, toInsertInto, pos, recursionCount + 1);
    }

    NFRule findRule(double number) {
        if (this.isFractionRuleSet) {
            return findFractionRuleSetRule(number);
        }
        if (Double.isNaN(number)) {
            NFRule rule = this.nonNumericalRules[5];
            if (rule == null) {
                return this.owner.getDefaultNaNRule();
            }
            return rule;
        }
        if (number < 0.0d) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            number = -number;
        }
        if (Double.isInfinite(number)) {
            NFRule rule2 = this.nonNumericalRules[4];
            if (rule2 == null) {
                return this.owner.getDefaultInfinityRule();
            }
            return rule2;
        }
        if (number != Math.floor(number)) {
            if (number < 1.0d && this.nonNumericalRules[2] != null) {
                return this.nonNumericalRules[2];
            }
            if (this.nonNumericalRules[1] != null) {
                return this.nonNumericalRules[1];
            }
        }
        if (this.nonNumericalRules[3] != null) {
            return this.nonNumericalRules[3];
        }
        return findNormalRule(Math.round(number));
    }

    private NFRule findNormalRule(long number) {
        if (this.isFractionRuleSet) {
            return findFractionRuleSetRule(number);
        }
        if (number < 0) {
            if (this.nonNumericalRules[0] != null) {
                return this.nonNumericalRules[0];
            }
            number = -number;
        }
        int lo = 0;
        int hi = this.rules.length;
        if (hi > 0) {
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                long ruleBaseValue = this.rules[mid].getBaseValue();
                if (ruleBaseValue == number) {
                    return this.rules[mid];
                }
                if (ruleBaseValue > number) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            if (hi == 0) {
                throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + number);
            }
            NFRule result = this.rules[hi - 1];
            if (result.shouldRollBack(number)) {
                if (hi == 1) {
                    throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + result + "'");
                }
                return this.rules[hi - 2];
            }
            return result;
        }
        return this.nonNumericalRules[3];
    }

    private NFRule findFractionRuleSetRule(double number) {
        long leastCommonMultiple = this.rules[0].getBaseValue();
        for (int i = 1; i < this.rules.length; i++) {
            leastCommonMultiple = lcm(leastCommonMultiple, this.rules[i].getBaseValue());
        }
        long numerator = Math.round(leastCommonMultiple * number);
        long difference = Long.MAX_VALUE;
        int winner = 0;
        for (int i2 = 0; i2 < this.rules.length; i2++) {
            long tempDifference = (this.rules[i2].getBaseValue() * numerator) % leastCommonMultiple;
            if (leastCommonMultiple - tempDifference < tempDifference) {
                tempDifference = leastCommonMultiple - tempDifference;
            }
            if (tempDifference < difference) {
                difference = tempDifference;
                winner = i2;
                if (difference == 0) {
                    break;
                }
            }
        }
        int i3 = winner + 1;
        if (i3 < this.rules.length && this.rules[winner + 1].getBaseValue() == this.rules[winner].getBaseValue() && (Math.round(this.rules[winner].getBaseValue() * number) < 1 || Math.round(this.rules[winner].getBaseValue() * number) >= 2)) {
            winner++;
        }
        return this.rules[winner];
    }

    private static long lcm(long x, long y) {
        long y1;
        long y12;
        long x1 = x;
        long y13 = y;
        int p2 = 0;
        while ((x1 & 1) == 0 && (y13 & 1) == 0) {
            p2++;
            x1 >>= 1;
            y13 >>= 1;
        }
        if ((x1 & 1) == 1) {
            long j = x1;
            x1 = -y13;
            y1 = y13;
            y12 = j;
        } else {
            y1 = y13;
            y12 = x1;
        }
        while (x1 != 0) {
            while ((x1 & 1) == 0) {
                x1 >>= 1;
            }
            if (x1 > 0) {
                y12 = x1;
            } else {
                y1 = -x1;
            }
            x1 = y12 - y1;
        }
        long gcd = y12 << p2;
        return (x / gcd) * y;
    }

    public Number parse(String text, ParsePosition parsePosition, double upperBound, int nonNumericalExecutedRuleMask) {
        ParsePosition highWaterMark = new ParsePosition(0);
        Number result = NFRule.ZERO;
        if (text.length() == 0) {
            return result;
        }
        int nonNumericalExecutedRuleMask2 = nonNumericalExecutedRuleMask;
        Number result2 = result;
        int nonNumericalRuleIdx = 0;
        while (true) {
            int nonNumericalRuleIdx2 = nonNumericalRuleIdx;
            if (nonNumericalRuleIdx2 >= this.nonNumericalRules.length) {
                break;
            }
            NFRule nonNumericalRule = this.nonNumericalRules[nonNumericalRuleIdx2];
            if (nonNumericalRule != null && ((nonNumericalExecutedRuleMask2 >> nonNumericalRuleIdx2) & 1) == 0) {
                nonNumericalExecutedRuleMask2 |= 1 << nonNumericalRuleIdx2;
                Number tempResult = nonNumericalRule.doParse(text, parsePosition, false, upperBound, nonNumericalExecutedRuleMask2);
                if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                    result2 = tempResult;
                    highWaterMark.setIndex(parsePosition.getIndex());
                }
                parsePosition.setIndex(0);
            }
            nonNumericalRuleIdx = nonNumericalRuleIdx2 + 1;
        }
        int i = this.rules.length - 1;
        while (true) {
            int i2 = i;
            if (i2 < 0 || highWaterMark.getIndex() >= text.length()) {
                break;
            }
            if (this.isFractionRuleSet || this.rules[i2].getBaseValue() < upperBound) {
                Number tempResult2 = this.rules[i2].doParse(text, parsePosition, this.isFractionRuleSet, upperBound, nonNumericalExecutedRuleMask2);
                if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                    result2 = tempResult2;
                    highWaterMark.setIndex(parsePosition.getIndex());
                }
                parsePosition.setIndex(0);
            }
            i = i2 - 1;
        }
        parsePosition.setIndex(highWaterMark.getIndex());
        return result2;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
        NFRule[] nFRuleArr;
        for (NFRule rule : this.rules) {
            rule.setDecimalFormatSymbols(newSymbols);
        }
        if (this.fractionRules != null) {
            for (int nonNumericalIdx = 1; nonNumericalIdx <= 3; nonNumericalIdx++) {
                if (this.nonNumericalRules[nonNumericalIdx] != null) {
                    Iterator<NFRule> it = this.fractionRules.iterator();
                    while (it.hasNext()) {
                        NFRule rule2 = it.next();
                        if (this.nonNumericalRules[nonNumericalIdx].getBaseValue() == rule2.getBaseValue()) {
                            setBestFractionRule(nonNumericalIdx, rule2, false);
                        }
                    }
                }
            }
        }
        for (NFRule rule3 : this.nonNumericalRules) {
            if (rule3 != null) {
                rule3.setDecimalFormatSymbols(newSymbols);
            }
        }
    }
}
