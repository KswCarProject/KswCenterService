package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes5.dex */
public abstract class NumberFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ACCOUNTINGCURRENCYSTYLE = 7;
    public static final int CASHCURRENCYSTYLE = 8;
    public static final int CURRENCYSTYLE = 1;
    public static final int FRACTION_FIELD = 1;
    public static final int INTEGERSTYLE = 4;
    public static final int INTEGER_FIELD = 0;
    public static final int ISOCURRENCYSTYLE = 5;
    public static final int NUMBERSTYLE = 0;
    public static final int PERCENTSTYLE = 2;
    public static final int PLURALCURRENCYSTYLE = 6;
    public static final int SCIENTIFICSTYLE = 3;
    public static final int STANDARDCURRENCYSTYLE = 9;
    static final int currentSerialVersion = 2;
    private static final char[] doubleCurrencySign = {'\u00a4', '\u00a4'};
    private static final String doubleCurrencyStr = new String(doubleCurrencySign);
    private static final long serialVersionUID = -2308460125733713944L;
    private static NumberFormatShim shim;
    private Currency currency;
    private boolean parseStrict;
    private boolean groupingUsed = true;
    private byte maxIntegerDigits = 40;
    private byte minIntegerDigits = 1;
    private byte maxFractionDigits = 3;
    private byte minFractionDigits = 0;
    private boolean parseIntegerOnly = false;
    private int maximumIntegerDigits = 40;
    private int minimumIntegerDigits = 1;
    private int maximumFractionDigits = 3;
    private int minimumFractionDigits = 0;
    private int serialVersionOnStream = 2;
    private DisplayContext capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;

    public abstract StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition);

    public abstract StringBuffer format(long j, StringBuffer stringBuffer, FieldPosition fieldPosition);

    public abstract StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition);

    public abstract StringBuffer format(java.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition);

    public abstract StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition);

    public abstract Number parse(String str, ParsePosition parsePosition);

    @Override // java.text.Format
    public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
        if (number instanceof Long) {
            return format(((Long) number).longValue(), toAppendTo, pos);
        }
        if (number instanceof BigInteger) {
            return format((BigInteger) number, toAppendTo, pos);
        }
        if (number instanceof java.math.BigDecimal) {
            return format((java.math.BigDecimal) number, toAppendTo, pos);
        }
        if (number instanceof BigDecimal) {
            return format((BigDecimal) number, toAppendTo, pos);
        }
        if (number instanceof CurrencyAmount) {
            return format((CurrencyAmount) number, toAppendTo, pos);
        }
        if (number instanceof Number) {
            return format(((Number) number).doubleValue(), toAppendTo, pos);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }

    @Override // java.text.Format
    public final Object parseObject(String source, ParsePosition parsePosition) {
        return parse(source, parsePosition);
    }

    public final String format(double number) {
        return format(number, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(long number) {
        StringBuffer buf = new StringBuffer(19);
        FieldPosition pos = new FieldPosition(0);
        format(number, buf, pos);
        return buf.toString();
    }

    public final String format(BigInteger number) {
        return format(number, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(java.math.BigDecimal number) {
        return format(number, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(BigDecimal number) {
        return format(number, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(CurrencyAmount currAmt) {
        return format(currAmt, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public StringBuffer format(CurrencyAmount currAmt, StringBuffer toAppendTo, FieldPosition pos) {
        synchronized (this) {
            Currency save = getCurrency();
            Currency curr = currAmt.getCurrency();
            boolean same = curr.equals(save);
            if (!same) {
                setCurrency(curr);
            }
            format(currAmt.getNumber(), toAppendTo, pos);
            if (!same) {
                setCurrency(save);
            }
        }
        return toAppendTo;
    }

    public Number parse(String text) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Number result = parse(text, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Unparseable number: \"" + text + '\"', parsePosition.getErrorIndex());
        }
        return result;
    }

    public CurrencyAmount parseCurrency(CharSequence text, ParsePosition pos) {
        Number n = parse(text.toString(), pos);
        if (n == null) {
            return null;
        }
        return new CurrencyAmount(n, getEffectiveCurrency());
    }

    public boolean isParseIntegerOnly() {
        return this.parseIntegerOnly;
    }

    public void setParseIntegerOnly(boolean value) {
        this.parseIntegerOnly = value;
    }

    public void setParseStrict(boolean value) {
        this.parseStrict = value;
    }

    public boolean isParseStrict() {
        return this.parseStrict;
    }

    public void setContext(DisplayContext context) {
        if (context.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationSetting = context;
        }
    }

    public DisplayContext getContext(DisplayContext.Type type) {
        return (type != DisplayContext.Type.CAPITALIZATION || this.capitalizationSetting == null) ? DisplayContext.CAPITALIZATION_NONE : this.capitalizationSetting;
    }

    public static final NumberFormat getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static NumberFormat getInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 0);
    }

    public static NumberFormat getInstance(ULocale inLocale) {
        return getInstance(inLocale, 0);
    }

    public static final NumberFormat getInstance(int style) {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), style);
    }

    public static NumberFormat getInstance(Locale inLocale, int style) {
        return getInstance(ULocale.forLocale(inLocale), style);
    }

    public static final NumberFormat getNumberInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static NumberFormat getNumberInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 0);
    }

    public static NumberFormat getNumberInstance(ULocale inLocale) {
        return getInstance(inLocale, 0);
    }

    public static final NumberFormat getIntegerInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 4);
    }

    public static NumberFormat getIntegerInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 4);
    }

    public static NumberFormat getIntegerInstance(ULocale inLocale) {
        return getInstance(inLocale, 4);
    }

    public static final NumberFormat getCurrencyInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 1);
    }

    public static NumberFormat getCurrencyInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 1);
    }

    public static NumberFormat getCurrencyInstance(ULocale inLocale) {
        return getInstance(inLocale, 1);
    }

    public static final NumberFormat getPercentInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 2);
    }

    public static NumberFormat getPercentInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 2);
    }

    public static NumberFormat getPercentInstance(ULocale inLocale) {
        return getInstance(inLocale, 2);
    }

    public static final NumberFormat getScientificInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 3);
    }

    public static NumberFormat getScientificInstance(Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale), 3);
    }

    public static NumberFormat getScientificInstance(ULocale inLocale) {
        return getInstance(inLocale, 3);
    }

    /* loaded from: classes5.dex */
    public static abstract class NumberFormatFactory {
        public static final int FORMAT_CURRENCY = 1;
        public static final int FORMAT_INTEGER = 4;
        public static final int FORMAT_NUMBER = 0;
        public static final int FORMAT_PERCENT = 2;
        public static final int FORMAT_SCIENTIFIC = 3;

        public abstract Set<String> getSupportedLocaleNames();

        public boolean visible() {
            return true;
        }

        public NumberFormat createFormat(ULocale loc, int formatType) {
            return createFormat(loc.toLocale(), formatType);
        }

        public NumberFormat createFormat(Locale loc, int formatType) {
            return createFormat(ULocale.forLocale(loc), formatType);
        }

        protected NumberFormatFactory() {
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class SimpleNumberFormatFactory extends NumberFormatFactory {
        final Set<String> localeNames;
        final boolean visible;

        public SimpleNumberFormatFactory(Locale locale) {
            this(locale, true);
        }

        public SimpleNumberFormatFactory(Locale locale, boolean visible) {
            this.localeNames = Collections.singleton(ULocale.forLocale(locale).getBaseName());
            this.visible = visible;
        }

        public SimpleNumberFormatFactory(ULocale locale) {
            this(locale, true);
        }

        public SimpleNumberFormatFactory(ULocale locale, boolean visible) {
            this.localeNames = Collections.singleton(locale.getBaseName());
            this.visible = visible;
        }

        @Override // com.ibm.icu.text.NumberFormat.NumberFormatFactory
        public final boolean visible() {
            return this.visible;
        }

        @Override // com.ibm.icu.text.NumberFormat.NumberFormatFactory
        public final Set<String> getSupportedLocaleNames() {
            return this.localeNames;
        }
    }

    /* loaded from: classes5.dex */
    static abstract class NumberFormatShim {
        abstract NumberFormat createInstance(ULocale uLocale, int i);

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract Object registerFactory(NumberFormatFactory numberFormatFactory);

        abstract boolean unregister(Object obj);

        NumberFormatShim() {
        }
    }

    private static NumberFormatShim getShim() {
        if (shim == null) {
            try {
                Class<?> cls = Class.forName("com.ibm.icu.text.NumberFormatServiceShim");
                shim = (NumberFormatShim) cls.newInstance();
            } catch (MissingResourceException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }
        return shim;
    }

    public static Locale[] getAvailableLocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return getShim().getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return getShim().getAvailableULocales();
    }

    public static Object registerFactory(NumberFormatFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        return getShim().registerFactory(factory);
    }

    public static boolean unregister(Object registryKey) {
        if (registryKey == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        if (shim == null) {
            return false;
        }
        return shim.unregister(registryKey);
    }

    public int hashCode() {
        return (this.maximumIntegerDigits * 37) + this.maxFractionDigits;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NumberFormat other = (NumberFormat) obj;
        if (this.maximumIntegerDigits != other.maximumIntegerDigits || this.minimumIntegerDigits != other.minimumIntegerDigits || this.maximumFractionDigits != other.maximumFractionDigits || this.minimumFractionDigits != other.minimumFractionDigits || this.groupingUsed != other.groupingUsed || this.parseIntegerOnly != other.parseIntegerOnly || this.parseStrict != other.parseStrict || this.capitalizationSetting != other.capitalizationSetting) {
            return false;
        }
        return true;
    }

    @Override // java.text.Format
    public Object clone() {
        NumberFormat other = (NumberFormat) super.clone();
        return other;
    }

    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }

    public void setGroupingUsed(boolean newValue) {
        this.groupingUsed = newValue;
    }

    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    public void setMaximumIntegerDigits(int newValue) {
        this.maximumIntegerDigits = Math.max(0, newValue);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.minimumIntegerDigits = this.maximumIntegerDigits;
        }
    }

    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    public void setMinimumIntegerDigits(int newValue) {
        this.minimumIntegerDigits = Math.max(0, newValue);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = this.minimumIntegerDigits;
        }
    }

    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    public void setMaximumFractionDigits(int newValue) {
        this.maximumFractionDigits = Math.max(0, newValue);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.minimumFractionDigits = this.maximumFractionDigits;
        }
    }

    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    public void setMinimumFractionDigits(int newValue) {
        this.minimumFractionDigits = Math.max(0, newValue);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.maximumFractionDigits = this.minimumFractionDigits;
        }
    }

    public void setCurrency(Currency theCurrency) {
        this.currency = theCurrency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Deprecated
    protected Currency getEffectiveCurrency() {
        Currency c = getCurrency();
        if (c == null) {
            ULocale uloc = getLocale(ULocale.VALID_LOCALE);
            if (uloc == null) {
                uloc = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            return Currency.getInstance(uloc);
        }
        return c;
    }

    public int getRoundingMode() {
        throw new UnsupportedOperationException("getRoundingMode must be implemented by the subclass implementation.");
    }

    public void setRoundingMode(int roundingMode) {
        throw new UnsupportedOperationException("setRoundingMode must be implemented by the subclass implementation.");
    }

    public static NumberFormat getInstance(ULocale desiredLocale, int choice) {
        if (choice < 0 || choice > 9) {
            throw new IllegalArgumentException("choice should be from NUMBERSTYLE to STANDARDCURRENCYSTYLE");
        }
        return getShim().createInstance(desiredLocale, choice);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static NumberFormat createInstance(ULocale desiredLocale, int choice) {
        String temp;
        NumberFormat format;
        ULocale nsLoc;
        String nsRuleSetName;
        String pattern = getPattern(desiredLocale, choice);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(desiredLocale);
        if ((choice == 1 || choice == 5 || choice == 7 || choice == 8 || choice == 9) && (temp = symbols.getCurrencyPattern()) != null) {
            pattern = temp;
        }
        if (choice == 5) {
            pattern = pattern.replace("\u00a4", doubleCurrencyStr);
        }
        NumberingSystem ns = NumberingSystem.getInstance(desiredLocale);
        if (ns == null) {
            return null;
        }
        if (ns != null && ns.isAlgorithmic()) {
            int desiredRulesType = 4;
            String nsDesc = ns.getDescription();
            int firstSlash = nsDesc.indexOf("/");
            int lastSlash = nsDesc.lastIndexOf("/");
            if (lastSlash > firstSlash) {
                String nsLocID = nsDesc.substring(0, firstSlash);
                String nsRuleSetGroup = nsDesc.substring(firstSlash + 1, lastSlash);
                nsRuleSetName = nsDesc.substring(lastSlash + 1);
                nsLoc = new ULocale(nsLocID);
                if (nsRuleSetGroup.equals("SpelloutRules")) {
                    desiredRulesType = 1;
                }
            } else {
                nsLoc = desiredLocale;
                nsRuleSetName = nsDesc;
            }
            RuleBasedNumberFormat r = new RuleBasedNumberFormat(nsLoc, desiredRulesType);
            r.setDefaultRuleSet(nsRuleSetName);
            format = r;
        } else {
            DecimalFormat f = new DecimalFormat(pattern, symbols, choice);
            if (choice == 4) {
                f.setMaximumFractionDigits(0);
                f.setDecimalSeparatorAlwaysShown(false);
                f.setParseIntegerOnly(true);
            }
            if (choice == 8) {
                f.setCurrencyUsage(Currency.CurrencyUsage.CASH);
            }
            if (choice == 6) {
                f.setCurrencyPluralInfo(CurrencyPluralInfo.getInstance(desiredLocale));
            }
            format = f;
        }
        ULocale valid = symbols.getLocale(ULocale.VALID_LOCALE);
        ULocale actual = symbols.getLocale(ULocale.ACTUAL_LOCALE);
        format.setLocale(valid, actual);
        return format;
    }

    @Deprecated
    protected static String getPattern(Locale forLocale, int choice) {
        return getPattern(ULocale.forLocale(forLocale), choice);
    }

    protected static String getPattern(ULocale forLocale, int choice) {
        return getPatternForStyle(forLocale, choice);
    }

    @Deprecated
    public static String getPatternForStyle(ULocale forLocale, int choice) {
        NumberingSystem ns = NumberingSystem.getInstance(forLocale);
        String nsName = ns.getName();
        return getPatternForStyleAndNumberingSystem(forLocale, nsName, choice);
    }

    @Deprecated
    public static String getPatternForStyleAndNumberingSystem(ULocale forLocale, String nsName, int choice) {
        String patternKey;
        switch (choice) {
            case 0:
            case 4:
            case 6:
                patternKey = "decimalFormat";
                break;
            case 1:
                String cfKeyValue = forLocale.getKeywordValue("cf");
                patternKey = (cfKeyValue == null || !cfKeyValue.equals("account")) ? "currencyFormat" : "accountingFormat";
                break;
            case 2:
                patternKey = "percentFormat";
                break;
            case 3:
                patternKey = "scientificFormat";
                break;
            case 5:
            case 8:
            case 9:
                patternKey = "currencyFormat";
                break;
            case 7:
                patternKey = "accountingFormat";
                break;
            default:
                patternKey = "decimalFormat";
                break;
        }
        ICUResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", forLocale);
        String result = rb.findStringWithFallback("NumberElements/" + nsName + "/patterns/" + patternKey);
        if (result == null) {
            return rb.getStringWithFallback("NumberElements/latn/patterns/" + patternKey);
        }
        return result;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.maximumIntegerDigits = this.maxIntegerDigits;
            this.minimumIntegerDigits = this.minIntegerDigits;
            this.maximumFractionDigits = this.maxFractionDigits;
            this.minimumFractionDigits = this.minFractionDigits;
        }
        if (this.serialVersionOnStream < 2) {
            this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        }
        if (this.minimumIntegerDigits > this.maximumIntegerDigits || this.minimumFractionDigits > this.maximumFractionDigits || this.minimumIntegerDigits < 0 || this.minimumFractionDigits < 0) {
            throw new InvalidObjectException("Digit count range invalid");
        }
        this.serialVersionOnStream = 2;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        int i = this.maximumIntegerDigits;
        byte b = Bidi.LEVEL_DEFAULT_RTL;
        this.maxIntegerDigits = i > 127 ? Byte.MAX_VALUE : (byte) this.maximumIntegerDigits;
        this.minIntegerDigits = this.minimumIntegerDigits > 127 ? Byte.MAX_VALUE : (byte) this.minimumIntegerDigits;
        this.maxFractionDigits = this.maximumFractionDigits > 127 ? Byte.MAX_VALUE : (byte) this.maximumFractionDigits;
        if (this.minimumFractionDigits <= 127) {
            b = (byte) this.minimumFractionDigits;
        }
        this.minFractionDigits = b;
        stream.defaultWriteObject();
    }

    /* loaded from: classes5.dex */
    public static class Field extends Format.Field {
        static final long serialVersionUID = -4516273749929385842L;
        public static final Field SIGN = new Field("sign");
        public static final Field INTEGER = new Field("integer");
        public static final Field FRACTION = new Field("fraction");
        public static final Field EXPONENT = new Field("exponent");
        public static final Field EXPONENT_SIGN = new Field("exponent sign");
        public static final Field EXPONENT_SYMBOL = new Field("exponent symbol");
        public static final Field DECIMAL_SEPARATOR = new Field("decimal separator");
        public static final Field GROUPING_SEPARATOR = new Field("grouping separator");
        public static final Field PERCENT = new Field("percent");
        public static final Field PERMILLE = new Field("per mille");
        public static final Field CURRENCY = new Field("currency");

        protected Field(String fieldName) {
            super(fieldName);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        protected Object readResolve() throws InvalidObjectException {
            if (getName().equals(INTEGER.getName())) {
                return INTEGER;
            }
            if (getName().equals(FRACTION.getName())) {
                return FRACTION;
            }
            if (getName().equals(EXPONENT.getName())) {
                return EXPONENT;
            }
            if (getName().equals(EXPONENT_SIGN.getName())) {
                return EXPONENT_SIGN;
            }
            if (getName().equals(EXPONENT_SYMBOL.getName())) {
                return EXPONENT_SYMBOL;
            }
            if (getName().equals(CURRENCY.getName())) {
                return CURRENCY;
            }
            if (getName().equals(DECIMAL_SEPARATOR.getName())) {
                return DECIMAL_SEPARATOR;
            }
            if (getName().equals(GROUPING_SEPARATOR.getName())) {
                return GROUPING_SEPARATOR;
            }
            if (getName().equals(PERCENT.getName())) {
                return PERCENT;
            }
            if (getName().equals(PERMILLE.getName())) {
                return PERMILLE;
            }
            if (getName().equals(SIGN.getName())) {
                return SIGN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }
}
