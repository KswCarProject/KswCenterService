package com.ibm.icu.text;

import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes5.dex */
public class RuleBasedNumberFormat extends NumberFormat {
    public static final int DURATION = 3;
    public static final int NUMBERING_SYSTEM = 4;
    public static final int ORDINAL = 2;
    public static final int SPELLOUT = 1;
    static final long serialVersionUID = -7664252765575395068L;
    private transient BreakIterator capitalizationBrkIter;
    private boolean capitalizationForListOrMenu;
    private boolean capitalizationForStandAlone;
    private boolean capitalizationInfoIsSet;
    private transient DecimalFormat decimalFormat;
    private transient DecimalFormatSymbols decimalFormatSymbols;
    private transient NFRule defaultInfinityRule;
    private transient NFRule defaultNaNRule;
    private transient NFRuleSet defaultRuleSet;
    private boolean lenientParse;
    private transient String lenientParseRules;
    private ULocale locale;
    private transient boolean lookedForScanner;
    private transient String postProcessRules;
    private transient RBNFPostProcessor postProcessor;
    private String[] publicRuleSetNames;
    private int roundingMode;
    private Map<String, String[]> ruleSetDisplayNames;
    private transient NFRuleSet[] ruleSets;
    private transient Map<String, NFRuleSet> ruleSetsMap;
    private transient RbnfLenientScannerProvider scannerProvider;
    private static final boolean DEBUG = ICUDebug.enabled("rbnf");
    private static final String[] rulenames = {"SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules"};
    private static final String[] locnames = {"SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations"};
    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
    private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);

    public RuleBasedNumberFormat(String description) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        init(description, null);
    }

    public RuleBasedNumberFormat(String description, String[][] localizations) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        init(description, localizations);
    }

    public RuleBasedNumberFormat(String description, Locale locale) {
        this(description, ULocale.forLocale(locale));
    }

    public RuleBasedNumberFormat(String description, ULocale locale) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        init(description, null);
    }

    public RuleBasedNumberFormat(String description, String[][] localizations, ULocale locale) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        init(description, localizations);
    }

    public RuleBasedNumberFormat(Locale locale, int format) {
        this(ULocale.forLocale(locale), format);
    }

    public RuleBasedNumberFormat(ULocale locale, int format) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        ICUResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b/rbnf", locale);
        ULocale uloc = bundle.getULocale();
        setLocale(uloc, uloc);
        StringBuilder description = new StringBuilder();
        String[][] localizations = null;
        try {
            ICUResourceBundle rules = bundle.getWithFallback("RBNFRules/" + rulenames[format - 1]);
            UResourceBundleIterator it = rules.getIterator();
            while (it.hasNext()) {
                description.append(it.nextString());
            }
        } catch (MissingResourceException e) {
        }
        ICUResourceBundle findTopLevel = bundle.findTopLevel(locnames[format - 1]);
        if (findTopLevel != null) {
            localizations = new String[findTopLevel.getSize()];
            for (int i = 0; i < localizations.length; i++) {
                localizations[i] = findTopLevel.get(i).getStringArray();
            }
        }
        init(description.toString(), localizations);
    }

    public RuleBasedNumberFormat(int format) {
        this(ULocale.getDefault(ULocale.Category.FORMAT), format);
    }

    @Override // com.ibm.icu.text.NumberFormat, java.text.Format
    public Object clone() {
        return super.clone();
    }

    @Override // com.ibm.icu.text.NumberFormat
    public boolean equals(Object that) {
        if (that instanceof RuleBasedNumberFormat) {
            RuleBasedNumberFormat that2 = (RuleBasedNumberFormat) that;
            if (this.locale.equals(that2.locale) && this.lenientParse == that2.lenientParse && this.ruleSets.length == that2.ruleSets.length) {
                for (int i = 0; i < this.ruleSets.length; i++) {
                    if (!this.ruleSets[i].equals(that2.ruleSets[i])) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // com.ibm.icu.text.NumberFormat
    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        NFRuleSet[] nFRuleSetArr;
        StringBuilder result = new StringBuilder();
        for (NFRuleSet ruleSet : this.ruleSets) {
            result.append(ruleSet.toString());
        }
        return result.toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(toString());
        out.writeObject(this.locale);
        out.writeInt(this.roundingMode);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        ULocale loc;
        String description = in.readUTF();
        try {
            loc = (ULocale) in.readObject();
        } catch (Exception e) {
            loc = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        try {
            this.roundingMode = in.readInt();
        } catch (Exception e2) {
        }
        RuleBasedNumberFormat temp = new RuleBasedNumberFormat(description, loc);
        this.ruleSets = temp.ruleSets;
        this.ruleSetsMap = temp.ruleSetsMap;
        this.defaultRuleSet = temp.defaultRuleSet;
        this.publicRuleSetNames = temp.publicRuleSetNames;
        this.decimalFormatSymbols = temp.decimalFormatSymbols;
        this.decimalFormat = temp.decimalFormat;
        this.locale = temp.locale;
        this.defaultInfinityRule = temp.defaultInfinityRule;
        this.defaultNaNRule = temp.defaultNaNRule;
    }

    public String[] getRuleSetNames() {
        return (String[]) this.publicRuleSetNames.clone();
    }

    public ULocale[] getRuleSetDisplayNameLocales() {
        if (this.ruleSetDisplayNames != null) {
            Set<String> s = this.ruleSetDisplayNames.keySet();
            String[] locales = (String[]) s.toArray(new String[s.size()]);
            Arrays.sort(locales, String.CASE_INSENSITIVE_ORDER);
            ULocale[] result = new ULocale[locales.length];
            for (int i = 0; i < locales.length; i++) {
                result[i] = new ULocale(locales[i]);
            }
            return result;
        }
        return null;
    }

    private String[] getNameListForLocale(ULocale loc) {
        if (loc != null && this.ruleSetDisplayNames != null) {
            String[] localeNames = {loc.getBaseName(), ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName()};
            int length = localeNames.length;
            for (int i = 0; i < length; i++) {
                for (String lname = localeNames[i]; lname.length() > 0; lname = ULocale.getFallback(lname)) {
                    String[] names = this.ruleSetDisplayNames.get(lname);
                    if (names != null) {
                        return names;
                    }
                }
            }
            return null;
        }
        return null;
    }

    public String[] getRuleSetDisplayNames(ULocale loc) {
        String[] names = getNameListForLocale(loc);
        if (names != null) {
            return (String[]) names.clone();
        }
        String[] names2 = getRuleSetNames();
        for (int i = 0; i < names2.length; i++) {
            names2[i] = names2[i].substring(1);
        }
        return names2;
    }

    public String[] getRuleSetDisplayNames() {
        return getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getRuleSetDisplayName(String ruleSetName, ULocale loc) {
        String[] rsnames = this.publicRuleSetNames;
        for (int ix = 0; ix < rsnames.length; ix++) {
            if (rsnames[ix].equals(ruleSetName)) {
                String[] names = getNameListForLocale(loc);
                if (names != null) {
                    return names[ix];
                }
                return rsnames[ix].substring(1);
            }
        }
        throw new IllegalArgumentException("unrecognized rule set name: " + ruleSetName);
    }

    public String getRuleSetDisplayName(String ruleSetName) {
        return getRuleSetDisplayName(ruleSetName, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String format(double number, String ruleSet) throws IllegalArgumentException {
        if (ruleSet.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return adjustForContext(format(number, findRuleSet(ruleSet)));
    }

    public String format(long number, String ruleSet) throws IllegalArgumentException {
        if (ruleSet.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return adjustForContext(format(number, findRuleSet(ruleSet)));
    }

    @Override // com.ibm.icu.text.NumberFormat
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition ignore) {
        if (toAppendTo.length() == 0) {
            toAppendTo.append(adjustForContext(format(number, this.defaultRuleSet)));
        } else {
            toAppendTo.append(format(number, this.defaultRuleSet));
        }
        return toAppendTo;
    }

    @Override // com.ibm.icu.text.NumberFormat
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition ignore) {
        if (toAppendTo.length() == 0) {
            toAppendTo.append(adjustForContext(format(number, this.defaultRuleSet)));
        } else {
            toAppendTo.append(format(number, this.defaultRuleSet));
        }
        return toAppendTo;
    }

    @Override // com.ibm.icu.text.NumberFormat
    public StringBuffer format(BigInteger number, StringBuffer toAppendTo, FieldPosition pos) {
        return format(new BigDecimal(number), toAppendTo, pos);
    }

    @Override // com.ibm.icu.text.NumberFormat
    public StringBuffer format(java.math.BigDecimal number, StringBuffer toAppendTo, FieldPosition pos) {
        return format(new BigDecimal(number), toAppendTo, pos);
    }

    @Override // com.ibm.icu.text.NumberFormat
    public StringBuffer format(BigDecimal number, StringBuffer toAppendTo, FieldPosition pos) {
        if (MIN_VALUE.compareTo(number) > 0 || MAX_VALUE.compareTo(number) < 0) {
            return getDecimalFormat().format(number, toAppendTo, pos);
        }
        if (number.scale() == 0) {
            return format(number.longValue(), toAppendTo, pos);
        }
        return format(number.doubleValue(), toAppendTo, pos);
    }

    @Override // com.ibm.icu.text.NumberFormat
    public Number parse(String text, ParsePosition parsePosition) {
        String workingText = text.substring(parsePosition.getIndex());
        ParsePosition workingPos = new ParsePosition(0);
        Number result = NFRule.ZERO;
        ParsePosition highWaterMark = new ParsePosition(workingPos.getIndex());
        int i = this.ruleSets.length - 1;
        Number result2 = result;
        while (true) {
            int i2 = i;
            if (i2 < 0) {
                break;
            }
            if (this.ruleSets[i2].isPublic() && this.ruleSets[i2].isParseable()) {
                Number tempResult = this.ruleSets[i2].parse(workingText, workingPos, Double.MAX_VALUE, 0);
                if (workingPos.getIndex() > highWaterMark.getIndex()) {
                    highWaterMark.setIndex(workingPos.getIndex());
                    result2 = tempResult;
                }
                if (highWaterMark.getIndex() == workingText.length()) {
                    break;
                }
                workingPos.setIndex(0);
            }
            i = i2 - 1;
        }
        parsePosition.setIndex(parsePosition.getIndex() + highWaterMark.getIndex());
        return result2;
    }

    public void setLenientParseMode(boolean enabled) {
        this.lenientParse = enabled;
    }

    public boolean lenientParseEnabled() {
        return this.lenientParse;
    }

    public void setLenientScannerProvider(RbnfLenientScannerProvider scannerProvider) {
        this.scannerProvider = scannerProvider;
    }

    public RbnfLenientScannerProvider getLenientScannerProvider() {
        if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
            try {
                this.lookedForScanner = true;
                Class<?> cls = Class.forName("com.ibm.icu.impl.text.RbnfScannerProviderImpl");
                RbnfLenientScannerProvider provider = (RbnfLenientScannerProvider) cls.newInstance();
                setLenientScannerProvider(provider);
            } catch (Exception e) {
            }
        }
        return this.scannerProvider;
    }

    public void setDefaultRuleSet(String ruleSetName) {
        String currentName;
        if (ruleSetName == null) {
            if (this.publicRuleSetNames.length > 0) {
                this.defaultRuleSet = findRuleSet(this.publicRuleSetNames[0]);
                return;
            }
            this.defaultRuleSet = null;
            int n = this.ruleSets.length;
            do {
                n--;
                if (n >= 0) {
                    currentName = this.ruleSets[n].getName();
                    if (currentName.equals("%spellout-numbering") || currentName.equals("%digits-ordinal")) {
                        break;
                    }
                } else {
                    int n2 = this.ruleSets.length;
                    do {
                        n2--;
                        if (n2 < 0) {
                            return;
                        }
                    } while (!this.ruleSets[n2].isPublic());
                    this.defaultRuleSet = this.ruleSets[n2];
                    return;
                }
            } while (!currentName.equals("%duration"));
            this.defaultRuleSet = this.ruleSets[n];
        } else if (ruleSetName.startsWith("%%")) {
            throw new IllegalArgumentException("cannot use private rule set: " + ruleSetName);
        } else {
            this.defaultRuleSet = findRuleSet(ruleSetName);
        }
    }

    public String getDefaultRuleSetName() {
        if (this.defaultRuleSet != null && this.defaultRuleSet.isPublic()) {
            return this.defaultRuleSet.getName();
        }
        return "";
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
        NFRuleSet[] nFRuleSetArr;
        if (newSymbols != null) {
            this.decimalFormatSymbols = (DecimalFormatSymbols) newSymbols.clone();
            if (this.decimalFormat != null) {
                this.decimalFormat.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
            if (this.defaultInfinityRule != null) {
                this.defaultInfinityRule = null;
                getDefaultInfinityRule();
            }
            if (this.defaultNaNRule != null) {
                this.defaultNaNRule = null;
                getDefaultNaNRule();
            }
            for (NFRuleSet ruleSet : this.ruleSets) {
                ruleSet.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
        }
    }

    @Override // com.ibm.icu.text.NumberFormat
    public void setContext(DisplayContext context) {
        super.setContext(context);
        if (!this.capitalizationInfoIsSet && (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            initCapitalizationContextInfo(this.locale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null) {
            if (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || ((context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu) || (context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone))) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
        }
    }

    @Override // com.ibm.icu.text.NumberFormat
    public int getRoundingMode() {
        return this.roundingMode;
    }

    @Override // com.ibm.icu.text.NumberFormat
    public void setRoundingMode(int roundingMode) {
        if (roundingMode < 0 || roundingMode > 7) {
            throw new IllegalArgumentException("Invalid rounding mode: " + roundingMode);
        }
        this.roundingMode = roundingMode;
    }

    NFRuleSet getDefaultRuleSet() {
        return this.defaultRuleSet;
    }

    RbnfLenientScanner getLenientScanner() {
        RbnfLenientScannerProvider provider;
        if (this.lenientParse && (provider = getLenientScannerProvider()) != null) {
            return provider.get(this.locale, this.lenientParseRules);
        }
        return null;
    }

    DecimalFormatSymbols getDecimalFormatSymbols() {
        if (this.decimalFormatSymbols == null) {
            this.decimalFormatSymbols = new DecimalFormatSymbols(this.locale);
        }
        return this.decimalFormatSymbols;
    }

    DecimalFormat getDecimalFormat() {
        if (this.decimalFormat == null) {
            String pattern = getPattern(this.locale, 0);
            this.decimalFormat = new DecimalFormat(pattern, getDecimalFormatSymbols());
        }
        return this.decimalFormat;
    }

    PluralFormat createPluralFormat(PluralRules.PluralType pluralType, String pattern) {
        return new PluralFormat(this.locale, pluralType, pattern, getDecimalFormat());
    }

    NFRule getDefaultInfinityRule() {
        if (this.defaultInfinityRule == null) {
            this.defaultInfinityRule = new NFRule(this, "Inf: " + getDecimalFormatSymbols().getInfinity());
        }
        return this.defaultInfinityRule;
    }

    NFRule getDefaultNaNRule() {
        if (this.defaultNaNRule == null) {
            this.defaultNaNRule = new NFRule(this, "NaN: " + getDecimalFormatSymbols().getNaN());
        }
        return this.defaultNaNRule;
    }

    private String extractSpecial(StringBuilder description, String specialName) {
        int lp = description.indexOf(specialName);
        if (lp == -1) {
            return null;
        }
        if (lp != 0 && description.charAt(lp - 1) != ';') {
            return null;
        }
        int lpEnd = description.indexOf(";%", lp);
        if (lpEnd == -1) {
            lpEnd = description.length() - 1;
        }
        int lpStart = specialName.length() + lp;
        while (lpStart < lpEnd && PatternProps.isWhiteSpace(description.charAt(lpStart))) {
            lpStart++;
        }
        String result = description.substring(lpStart, lpEnd);
        description.delete(lp, lpEnd + 1);
        return result;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0129, code lost:
        r9 = r9 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void init(String description, String[][] localizations) {
        initLocalizations(localizations);
        StringBuilder descBuf = stripWhitespace(description);
        this.lenientParseRules = extractSpecial(descBuf, "%%lenient-parse:");
        this.postProcessRules = extractSpecial(descBuf, "%%post-process:");
        int numRuleSets = 1;
        int numRuleSets2 = 0;
        while (true) {
            int p = descBuf.indexOf(";%", numRuleSets2);
            if (p == -1) {
                break;
            }
            numRuleSets++;
            numRuleSets2 = p + 2;
        }
        this.ruleSets = new NFRuleSet[numRuleSets];
        this.ruleSetsMap = new HashMap((numRuleSets * 2) + 1);
        this.defaultRuleSet = null;
        String[] ruleSetDescriptions = new String[numRuleSets];
        int curRuleSet = 0;
        int publicRuleSetCount = 0;
        int start = 0;
        while (curRuleSet < this.ruleSets.length) {
            int p2 = descBuf.indexOf(";%", start);
            if (p2 < 0) {
                p2 = descBuf.length() - 1;
            }
            ruleSetDescriptions[curRuleSet] = descBuf.substring(start, p2 + 1);
            NFRuleSet ruleSet = new NFRuleSet(this, ruleSetDescriptions, curRuleSet);
            this.ruleSets[curRuleSet] = ruleSet;
            String currentName = ruleSet.getName();
            this.ruleSetsMap.put(currentName, ruleSet);
            if (!currentName.startsWith("%%")) {
                publicRuleSetCount++;
                if ((this.defaultRuleSet == null && currentName.equals("%spellout-numbering")) || currentName.equals("%digits-ordinal") || currentName.equals("%duration")) {
                    this.defaultRuleSet = ruleSet;
                }
            }
            curRuleSet++;
            start = p2 + 1;
        }
        if (this.defaultRuleSet == null) {
            int i = this.ruleSets.length - 1;
            while (true) {
                if (i >= 0) {
                    if (!this.ruleSets[i].getName().startsWith("%%")) {
                        this.defaultRuleSet = this.ruleSets[i];
                        break;
                    } else {
                        i--;
                    }
                } else {
                    break;
                }
            }
        }
        if (this.defaultRuleSet == null) {
            this.defaultRuleSet = this.ruleSets[this.ruleSets.length - 1];
        }
        for (int i2 = 0; i2 < this.ruleSets.length; i2++) {
            this.ruleSets[i2].parseRules(ruleSetDescriptions[i2]);
        }
        String[] publicRuleSetTemp = new String[publicRuleSetCount];
        int publicRuleSetCount2 = 0;
        for (int i3 = this.ruleSets.length - 1; i3 >= 0; i3--) {
            if (!this.ruleSets[i3].getName().startsWith("%%")) {
                publicRuleSetTemp[publicRuleSetCount2] = this.ruleSets[i3].getName();
                publicRuleSetCount2++;
            }
        }
        if (this.publicRuleSetNames != null) {
            int i4 = 0;
            while (i4 < this.publicRuleSetNames.length) {
                String name = this.publicRuleSetNames[i4];
                for (String str : publicRuleSetTemp) {
                    if (name.equals(str)) {
                        break;
                    }
                }
                throw new IllegalArgumentException("did not find public rule set: " + name);
            }
            this.defaultRuleSet = findRuleSet(this.publicRuleSetNames[0]);
            return;
        }
        this.publicRuleSetNames = publicRuleSetTemp;
    }

    private void initLocalizations(String[][] localizations) {
        if (localizations != null) {
            this.publicRuleSetNames = (String[]) localizations[0].clone();
            Map<String, String[]> m = new HashMap<>();
            for (int i = 1; i < localizations.length; i++) {
                String[] data = localizations[i];
                String loc = data[0];
                String[] names = new String[data.length - 1];
                if (names.length == this.publicRuleSetNames.length) {
                    System.arraycopy(data, 1, names, 0, names.length);
                    m.put(loc, names);
                } else {
                    throw new IllegalArgumentException("public name length: " + this.publicRuleSetNames.length + " != localized names[" + i + "] length: " + names.length);
                }
            }
            if (!m.isEmpty()) {
                this.ruleSetDisplayNames = m;
            }
        }
    }

    private void initCapitalizationContextInfo(ULocale theLocale) {
        ICUResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", theLocale);
        try {
            ICUResourceBundle rdb = rb.getWithFallback("contextTransforms/number-spellout");
            int[] intVector = rdb.getIntVector();
            if (intVector.length >= 2) {
                this.capitalizationForListOrMenu = intVector[0] != 0;
                this.capitalizationForStandAlone = intVector[1] != 0;
            }
        } catch (MissingResourceException e) {
        }
    }

    private StringBuilder stripWhitespace(String description) {
        StringBuilder result = new StringBuilder();
        int descriptionLength = description.length();
        int start = 0;
        while (true) {
            if (start >= descriptionLength) {
                break;
            }
            while (start < descriptionLength && PatternProps.isWhiteSpace(description.charAt(start))) {
                start++;
            }
            if (start < descriptionLength && description.charAt(start) == ';') {
                start++;
            } else {
                int p = description.indexOf(59, start);
                if (p == -1) {
                    result.append(description.substring(start));
                    break;
                } else if (p >= descriptionLength) {
                    break;
                } else {
                    result.append(description.substring(start, p + 1));
                    start = p + 1;
                }
            }
        }
        return result;
    }

    private String format(double number, NFRuleSet ruleSet) {
        StringBuilder result = new StringBuilder();
        if (getRoundingMode() != 7 && !Double.isNaN(number) && !Double.isInfinite(number)) {
            number = new BigDecimal(Double.toString(number)).setScale(getMaximumFractionDigits(), this.roundingMode).doubleValue();
        }
        ruleSet.format(number, result, 0, 0);
        postProcess(result, ruleSet);
        return result.toString();
    }

    private String format(long number, NFRuleSet ruleSet) {
        StringBuilder result = new StringBuilder();
        if (number == Long.MIN_VALUE) {
            result.append(getDecimalFormat().format(Long.MIN_VALUE));
        } else {
            ruleSet.format(number, result, 0, 0);
        }
        postProcess(result, ruleSet);
        return result.toString();
    }

    private void postProcess(StringBuilder result, NFRuleSet ruleSet) {
        if (this.postProcessRules != null) {
            if (this.postProcessor == null) {
                int ix = this.postProcessRules.indexOf(";");
                if (ix == -1) {
                    ix = this.postProcessRules.length();
                }
                String ppClassName = this.postProcessRules.substring(0, ix).trim();
                try {
                    Class<?> cls = Class.forName(ppClassName);
                    this.postProcessor = (RBNFPostProcessor) cls.newInstance();
                    this.postProcessor.init(this, this.postProcessRules);
                } catch (Exception e) {
                    if (DEBUG) {
                        PrintStream printStream = System.out;
                        printStream.println("could not locate " + ppClassName + ", error " + e.getClass().getName() + ", " + e.getMessage());
                    }
                    this.postProcessor = null;
                    this.postProcessRules = null;
                    return;
                }
            }
            this.postProcessor.process(result, ruleSet);
        }
    }

    private String adjustForContext(String result) {
        DisplayContext capitalization = getContext(DisplayContext.Type.CAPITALIZATION);
        if (capitalization != DisplayContext.CAPITALIZATION_NONE && result != null && result.length() > 0 && UCharacter.isLowerCase(result.codePointAt(0)) && (capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || ((capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu) || (capitalization == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)))) {
            if (this.capitalizationBrkIter == null) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
            return UCharacter.toTitleCase(this.locale, result, this.capitalizationBrkIter, 768);
        }
        return result;
    }

    NFRuleSet findRuleSet(String name) throws IllegalArgumentException {
        NFRuleSet result = this.ruleSetsMap.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No rule set named " + name);
        }
        return result;
    }
}
