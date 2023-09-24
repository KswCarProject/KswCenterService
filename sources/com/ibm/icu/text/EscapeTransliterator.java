package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Transliterator;

/* loaded from: classes5.dex */
class EscapeTransliterator extends Transliterator {
    private boolean grokSupplementals;
    private int minDigits;
    private String prefix;
    private int radix;
    private String suffix;
    private EscapeTransliterator supplementalHandler;

    static void register() {
        Transliterator.registerFactory("Any-Hex/Unicode", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Java", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.2
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/C", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.3
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, null));
            }
        });
        Transliterator.registerFactory("Any-Hex/XML", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.4
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/XML10", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.5
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Perl", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.6
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Plain", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.7
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex", new Transliterator.Factory() { // from class: com.ibm.icu.text.EscapeTransliterator.8
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String ID) {
                return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, null);
            }
        });
    }

    EscapeTransliterator(String ID, String prefix, String suffix, int radix, int minDigits, boolean grokSupplementals, EscapeTransliterator supplementalHandler) {
        super(ID, null);
        this.prefix = prefix;
        this.suffix = suffix;
        this.radix = radix;
        this.minDigits = minDigits;
        this.grokSupplementals = grokSupplementals;
        this.supplementalHandler = supplementalHandler;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean incremental) {
        int start = pos.start;
        int limit = pos.limit;
        StringBuilder buf = new StringBuilder(this.prefix);
        int prefixLen = this.prefix.length();
        int limit2 = limit;
        int limit3 = start;
        boolean redoPrefix = false;
        while (limit3 < limit2) {
            int c = this.grokSupplementals ? text.char32At(limit3) : text.charAt(limit3);
            int charLen = this.grokSupplementals ? UTF16.getCharCount(c) : 1;
            if (((-65536) & c) != 0 && this.supplementalHandler != null) {
                buf.setLength(0);
                buf.append(this.supplementalHandler.prefix);
                Utility.appendNumber(buf, c, this.supplementalHandler.radix, this.supplementalHandler.minDigits);
                buf.append(this.supplementalHandler.suffix);
                redoPrefix = true;
            } else {
                if (redoPrefix) {
                    buf.setLength(0);
                    buf.append(this.prefix);
                    redoPrefix = false;
                } else {
                    buf.setLength(prefixLen);
                }
                Utility.appendNumber(buf, c, this.radix, this.minDigits);
                buf.append(this.suffix);
            }
            text.replace(limit3, limit3 + charLen, buf.toString());
            limit3 += buf.length();
            limit2 += buf.length() - charLen;
        }
        pos.contextLimit += limit2 - pos.limit;
        pos.limit = limit2;
        pos.start = limit3;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        sourceSet.addAll(getFilterAsUnicodeSet(inputFilter));
        for (EscapeTransliterator it = this; it != null; it = it.supplementalHandler) {
            if (inputFilter.size() != 0) {
                targetSet.addAll(it.prefix);
                targetSet.addAll(it.suffix);
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < it.radix; i++) {
                    Utility.appendNumber(buffer, i, it.radix, it.minDigits);
                }
                targetSet.addAll(buffer.toString());
            }
        }
    }
}
