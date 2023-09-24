package com.ibm.icu.text;

/* compiled from: NFSubstitution.java */
/* loaded from: classes5.dex */
class MultiplierSubstitution extends NFSubstitution {
    long divisor;

    MultiplierSubstitution(int pos, NFRule rule, NFRuleSet ruleSet, String description) {
        super(pos, ruleSet, description);
        this.divisor = rule.getDivisor();
        if (this.divisor == 0) {
            throw new IllegalStateException("Substitution with divisor 0 " + description.substring(0, pos) + " | " + description.substring(pos));
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void setDivisor(int radix, short exponent) {
        this.divisor = NFRule.power(radix, exponent);
        if (this.divisor == 0) {
            throw new IllegalStateException("Substitution with divisor 0");
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean equals(Object that) {
        return super.equals(that) && this.divisor == ((MultiplierSubstitution) that).divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long number) {
        return (long) Math.floor(number / this.divisor);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double number) {
        if (this.ruleSet == null) {
            return number / this.divisor;
        }
        return Math.floor(number / this.divisor);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double newRuleValue, double oldRuleValue) {
        return this.divisor * newRuleValue;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double oldUpperBound) {
        return this.divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return '<';
    }
}
