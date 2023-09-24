package com.ibm.icu.text;

import java.io.ObjectStreamException;
import java.io.Serializable;

/* loaded from: classes5.dex */
class PluralRulesSerialProxy implements Serializable {
    private static final long serialVersionUID = 42;
    private final String data;

    PluralRulesSerialProxy(String rules) {
        this.data = rules;
    }

    private Object readResolve() throws ObjectStreamException {
        return PluralRules.createRules(this.data);
    }
}
