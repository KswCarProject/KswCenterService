package com.ibm.icu.text;

import java.text.ParsePosition;

/* compiled from: NFSubstitution.java */
/* loaded from: classes5.dex */
class NumeratorSubstitution extends NFSubstitution {
    private final double denominator;
    private final boolean withZeros;

    NumeratorSubstitution(int pos, double denominator, NFRuleSet ruleSet, String description) {
        super(pos, ruleSet, fixdesc(description));
        this.denominator = denominator;
        this.withZeros = description.endsWith("<<");
    }

    static String fixdesc(String description) {
        if (!description.endsWith("<<")) {
            return description;
        }
        return description.substring(0, description.length() - 1);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean equals(Object that) {
        if (super.equals(that)) {
            NumeratorSubstitution that2 = (NumeratorSubstitution) that;
            return this.denominator == that2.denominator && this.withZeros == that2.withZeros;
        }
        return false;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
        int position2;
        int len;
        double numberToFormat = transformNumber(number);
        if (!this.withZeros || this.ruleSet == null) {
            position2 = position;
        } else {
            long nf = (long) numberToFormat;
            int len2 = toInsertInto.length();
            while (true) {
                len = len2;
                long nf2 = 10 * nf;
                if (nf2 >= this.denominator) {
                    break;
                }
                toInsertInto.insert(position + this.pos, ' ');
                this.ruleSet.format(0L, toInsertInto, position + this.pos, recursionCount);
                len2 = len;
                nf = nf2;
            }
            position2 = position + (toInsertInto.length() - len);
        }
        if (numberToFormat == Math.floor(numberToFormat) && this.ruleSet != null) {
            this.ruleSet.format((long) numberToFormat, toInsertInto, position2 + this.pos, recursionCount);
        } else if (this.ruleSet != null) {
            this.ruleSet.format(numberToFormat, toInsertInto, position2 + this.pos, recursionCount);
        } else {
            toInsertInto.insert(this.pos + position2, this.numberFormat.format(numberToFormat));
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long number) {
        return Math.round(number * this.denominator);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double number) {
        return Math.round(this.denominator * number);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse, int nonNumericalExecutedRuleMask) {
        int zeroCount;
        String text2;
        int zeroCount2 = 0;
        if (this.withZeros) {
            String workText = text;
            ParsePosition workPos = new ParsePosition(1);
            while (workText.length() > 0 && workPos.getIndex() != 0) {
                workPos.setIndex(0);
                this.ruleSet.parse(workText, workPos, 1.0d, nonNumericalExecutedRuleMask).intValue();
                if (workPos.getIndex() == 0) {
                    break;
                }
                zeroCount2++;
                parsePosition.setIndex(parsePosition.getIndex() + workPos.getIndex());
                workText = workText.substring(workPos.getIndex());
                while (workText.length() > 0 && workText.charAt(0) == ' ') {
                    workText = workText.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
            String text3 = text.substring(parsePosition.getIndex());
            parsePosition.setIndex(0);
            zeroCount = zeroCount2;
            text2 = text3;
        } else {
            zeroCount = 0;
            text2 = text;
        }
        Number result = super.doParse(text2, parsePosition, this.withZeros ? 1.0d : baseValue, upperBound, false, nonNumericalExecutedRuleMask);
        if (this.withZeros) {
            long n = result.longValue();
            long d = 1;
            while (d <= n) {
                d *= 10;
            }
            while (zeroCount > 0) {
                d *= 10;
                zeroCount--;
            }
            return new Double(n / d);
        }
        return result;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double newRuleValue, double oldRuleValue) {
        return newRuleValue / oldRuleValue;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double oldUpperBound) {
        return this.denominator;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return '<';
    }
}
