package com.ibm.icu.text;

/* loaded from: classes5.dex */
interface RBNFPostProcessor {
    void init(RuleBasedNumberFormat ruleBasedNumberFormat, String str);

    void process(StringBuilder sb, NFRuleSet nFRuleSet);
}
