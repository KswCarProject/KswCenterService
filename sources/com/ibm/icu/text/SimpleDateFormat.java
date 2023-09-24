package com.ibm.icu.text;

import com.ibm.icu.impl.DateNumberFormat;
import com.ibm.icu.impl.DayPeriodRules;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.HebrewCalendar;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

/* loaded from: classes5.dex */
public class SimpleDateFormat extends DateFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DECIMAL_BUF_SIZE = 10;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int ISOSpecialEra = -32000;
    private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
    private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
    private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
    static final int currentSerialVersion = 2;
    private static final int millisPerHour = 3600000;
    private static final long serialVersionUID = 4774881970558875024L;
    private transient BreakIterator capitalizationBrkIter;
    private transient char[] decDigits;
    private transient char[] decimalBuf;
    private transient long defaultCenturyBase;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private DateFormatSymbols formatData;
    private transient boolean hasMinute;
    private transient boolean hasSecond;
    private transient ULocale locale;
    private HashMap<String, NumberFormat> numberFormatters;
    private String override;
    private HashMap<Character, String> overrideMap;
    private String pattern;
    private transient Object[] patternItems;
    private int serialVersionOnStream;
    private volatile TimeZoneFormat tzFormat;
    private transient boolean useFastFormat;
    private transient boolean useLocalZeroPaddingNumberFormat;
    static boolean DelayedHebrewMonthCheck = false;
    private static final int[] CALENDAR_FIELD_TO_LEVEL = {0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
    private static final int[] PATTERN_CHAR_TO_LEVEL = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, 10, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1};
    private static final boolean[] PATTERN_CHAR_IS_SYNTAX = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    private static ULocale cachedDefaultLocale = null;
    private static String cachedDefaultPattern = null;
    private static final int[] PATTERN_CHAR_TO_INDEX = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 36, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, 35, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, 34, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1};
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = {0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2};
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = {DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR_WOY, DateFormat.Field.DOW_LOCAL, DateFormat.Field.EXTENDED_YEAR, DateFormat.Field.JULIAN_DAY, DateFormat.Field.MILLISECONDS_IN_DAY, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.MONTH, DateFormat.Field.QUARTER, DateFormat.Field.QUARTER, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.RELATED_YEAR, DateFormat.Field.AM_PM_MIDNIGHT_NOON, DateFormat.Field.FLEXIBLE_DAY_PERIOD, DateFormat.Field.TIME_SEPARATOR};
    private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE = new SimpleCache();
    static final UnicodeSet DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").m211freeze();

    /* loaded from: classes5.dex */
    private enum ContextValue {
        UNKNOWN,
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
        CAPITALIZATION_FOR_UI_LIST_OR_MENU,
        CAPITALIZATION_FOR_STANDALONE
    }

    private static int getLevelFromChar(char ch) {
        if (ch < PATTERN_CHAR_TO_LEVEL.length) {
            return PATTERN_CHAR_TO_LEVEL[ch & '\u00ff'];
        }
        return -1;
    }

    private static boolean isSyntaxChar(char ch) {
        if (ch < PATTERN_CHAR_IS_SYNTAX.length) {
            return PATTERN_CHAR_IS_SYNTAX[ch & '\u00ff'];
        }
        return false;
    }

    public SimpleDateFormat() {
        this(getDefaultPattern(), null, null, null, null, true, null);
    }

    public SimpleDateFormat(String pattern) {
        this(pattern, null, null, null, null, true, null);
    }

    public SimpleDateFormat(String pattern, Locale loc) {
        this(pattern, null, null, null, ULocale.forLocale(loc), true, null);
    }

    public SimpleDateFormat(String pattern, ULocale loc) {
        this(pattern, null, null, null, loc, true, null);
    }

    public SimpleDateFormat(String pattern, String override, ULocale loc) {
        this(pattern, null, null, null, loc, false, override);
    }

    public SimpleDateFormat(String pattern, DateFormatSymbols formatData) {
        this(pattern, (DateFormatSymbols) formatData.clone(), null, null, null, true, null);
    }

    @Deprecated
    public SimpleDateFormat(String pattern, DateFormatSymbols formatData, ULocale loc) {
        this(pattern, (DateFormatSymbols) formatData.clone(), null, null, loc, true, null);
    }

    SimpleDateFormat(String pattern, DateFormatSymbols formatData, Calendar calendar, ULocale locale, boolean useFastFormat, String override) {
        this(pattern, (DateFormatSymbols) formatData.clone(), (Calendar) calendar.clone(), null, locale, useFastFormat, override);
    }

    private SimpleDateFormat(String pattern, DateFormatSymbols formatData, Calendar calendar, NumberFormat numberFormat, ULocale locale, boolean useFastFormat, String override) {
        this.serialVersionOnStream = 2;
        this.capitalizationBrkIter = null;
        this.pattern = pattern;
        this.formatData = formatData;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = locale;
        this.useFastFormat = useFastFormat;
        this.override = override;
        initialize();
    }

    @Deprecated
    public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfig) {
        String ostr = formatConfig.getOverrideString();
        boolean useFast = ostr != null && ostr.length() > 0;
        return new SimpleDateFormat(formatConfig.getPatternString(), formatConfig.getDateFormatSymbols(), formatConfig.getCalendar(), null, formatConfig.getLocale(), useFast, formatConfig.getOverrideString());
    }

    private void initialize() {
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (this.formatData == null) {
            this.formatData = new DateFormatSymbols(this.locale);
        }
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.locale);
        }
        if (this.numberFormat == null) {
            NumberingSystem ns = NumberingSystem.getInstance(this.locale);
            String digitString = ns.getDescription();
            if (ns.isAlgorithmic() || digitString.length() != 10) {
                this.numberFormat = NumberFormat.getInstance(this.locale);
            } else {
                String nsName = ns.getName();
                this.numberFormat = new DateNumberFormat(this.locale, digitString, nsName);
            }
        }
        if (this.numberFormat instanceof DecimalFormat) {
            fixNumberFormatForDates(this.numberFormat);
        }
        this.defaultCenturyBase = System.currentTimeMillis();
        setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
        initLocalZeroPaddingNumberFormat();
        if (this.override != null) {
            initNumberFormatters(this.locale);
        }
        parsePattern();
    }

    private synchronized void initializeTimeZoneFormat(boolean bForceUpdate) {
        if (!bForceUpdate) {
            try {
                if (this.tzFormat == null) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.tzFormat = TimeZoneFormat.getInstance(this.locale);
        String digits = null;
        if (this.numberFormat instanceof DecimalFormat) {
            DecimalFormatSymbols decsym = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols();
            String[] strDigits = decsym.getDigitStringsLocal();
            StringBuilder digitsBuf = new StringBuilder();
            for (String digit : strDigits) {
                digitsBuf.append(digit);
            }
            digits = digitsBuf.toString();
        } else if (this.numberFormat instanceof DateNumberFormat) {
            digits = new String(this.numberFormat.getDigits());
        }
        if (digits != null && !this.tzFormat.getGMTOffsetDigits().equals(digits)) {
            if (this.tzFormat.isFrozen()) {
                this.tzFormat = this.tzFormat.m208cloneAsThawed();
            }
            this.tzFormat.setGMTOffsetDigits(digits);
        }
    }

    private TimeZoneFormat tzFormat() {
        if (this.tzFormat == null) {
            initializeTimeZoneFormat(false);
        }
        return this.tzFormat;
    }

    private static synchronized String getDefaultPattern() {
        String str;
        synchronized (SimpleDateFormat.class) {
            ULocale defaultLocale = ULocale.getDefault(ULocale.Category.FORMAT);
            if (!defaultLocale.equals(cachedDefaultLocale)) {
                cachedDefaultLocale = defaultLocale;
                Calendar cal = Calendar.getInstance(cachedDefaultLocale);
                try {
                    ICUResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", cachedDefaultLocale);
                    String resourcePath = "calendar/" + cal.getType() + "/DateTimePatterns";
                    ICUResourceBundle patternsRb = rb.findWithFallback(resourcePath);
                    if (patternsRb == null) {
                        patternsRb = rb.findWithFallback("calendar/gregorian/DateTimePatterns");
                    }
                    if (patternsRb != null && patternsRb.getSize() >= 9) {
                        int defaultIndex = 8;
                        if (patternsRb.getSize() >= 13) {
                            defaultIndex = 8 + 4;
                        }
                        String basePattern = patternsRb.getString(defaultIndex);
                        cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(basePattern, 2, 2, new CharSequence[]{patternsRb.getString(3), patternsRb.getString(7)});
                    }
                    cachedDefaultPattern = FALLBACKPATTERN;
                } catch (MissingResourceException e) {
                    cachedDefaultPattern = FALLBACKPATTERN;
                }
            }
            str = cachedDefaultPattern;
        }
        return str;
    }

    private void parseAmbiguousDatesAsAfter(Date startDate) {
        this.defaultCenturyStart = startDate;
        this.calendar.setTime(startDate);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }

    private void initializeDefaultCenturyStart(long baseTime) {
        this.defaultCenturyBase = baseTime;
        Calendar tmpCal = (Calendar) this.calendar.clone();
        tmpCal.setTimeInMillis(baseTime);
        tmpCal.add(1, -80);
        this.defaultCenturyStart = tmpCal.getTime();
        this.defaultCenturyStartYear = tmpCal.get(1);
    }

    private Date getDefaultCenturyStart() {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStart;
    }

    private int getDefaultCenturyStartYear() {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStartYear;
    }

    public void set2DigitYearStart(Date startDate) {
        parseAmbiguousDatesAsAfter(startDate);
    }

    public Date get2DigitYearStart() {
        return getDefaultCenturyStart();
    }

    @Override // com.ibm.icu.text.DateFormat
    public void setContext(DisplayContext context) {
        super.setContext(context);
        if (this.capitalizationBrkIter == null) {
            if (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
        }
    }

    @Override // com.ibm.icu.text.DateFormat
    public StringBuffer format(Calendar cal, StringBuffer toAppendTo, FieldPosition pos) {
        TimeZone backupTZ = null;
        if (cal != this.calendar && !cal.getType().equals(this.calendar.getType())) {
            this.calendar.setTimeInMillis(cal.getTimeInMillis());
            backupTZ = this.calendar.getTimeZone();
            this.calendar.setTimeZone(cal.getTimeZone());
            cal = this.calendar;
        }
        StringBuffer result = format(cal, getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, null);
        if (backupTZ != null) {
            this.calendar.setTimeZone(backupTZ);
        }
        return result;
    }

    private StringBuffer format(Calendar cal, DisplayContext capitalizationContext, StringBuffer toAppendTo, FieldPosition pos, List<FieldPosition> attributes) {
        int start;
        int end = 0;
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        Object[] items = getPatternItems();
        while (true) {
            int i = end;
            if (i >= items.length) {
                return toAppendTo;
            }
            if (items[i] instanceof String) {
                toAppendTo.append((String) items[i]);
            } else {
                PatternItem item = (PatternItem) items[i];
                int start2 = 0;
                if (attributes != null) {
                    start2 = toAppendTo.length();
                }
                int start3 = start2;
                if (this.useFastFormat) {
                    start = start3;
                    subFormat(toAppendTo, item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, cal);
                } else {
                    start = start3;
                    toAppendTo.append(subFormat(item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, cal));
                }
                if (attributes != null) {
                    int end2 = toAppendTo.length();
                    if (end2 - start > 0) {
                        DateFormat.Field attr = patternCharToDateFormatField(item.type);
                        FieldPosition fp = new FieldPosition(attr);
                        fp.setBeginIndex(start);
                        fp.setEndIndex(end2);
                        attributes.add(fp);
                    }
                }
            }
            end = i + 1;
        }
    }

    private static int getIndexFromChar(char ch) {
        if (ch < PATTERN_CHAR_TO_INDEX.length) {
            return PATTERN_CHAR_TO_INDEX[ch & '\u00ff'];
        }
        return -1;
    }

    protected DateFormat.Field patternCharToDateFormatField(char ch) {
        int patternCharIndex = getIndexFromChar(ch);
        if (patternCharIndex != -1) {
            return PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[patternCharIndex];
        }
        return null;
    }

    protected String subFormat(char ch, int count, int beginOffset, FieldPosition pos, DateFormatSymbols fmtData, Calendar cal) throws IllegalArgumentException {
        return subFormat(ch, count, beginOffset, 0, DisplayContext.CAPITALIZATION_NONE, pos, cal);
    }

    @Deprecated
    protected String subFormat(char ch, int count, int beginOffset, int fieldNum, DisplayContext capitalizationContext, FieldPosition pos, Calendar cal) {
        StringBuffer buf = new StringBuffer();
        subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
        return buf.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:237:0x05b7  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x05c8  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x069e  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x07df  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x07f3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0182  */
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void subFormat(StringBuffer buf, char ch, int count, int beginOffset, int fieldNum, DisplayContext capitalizationContext, FieldPosition pos, Calendar cal) {
        int patternCharIndex;
        int bufstart;
        char c;
        int value;
        int value2;
        char c2;
        int value3;
        int i;
        DateFormatSymbols.CapitalizationContextUsage capContextUsageType;
        char c3;
        int value4;
        int value5;
        int value6;
        int value7;
        int patternCharIndex2;
        int value8;
        int value9;
        int patternCharIndex3;
        String result;
        DateFormatSymbols.CapitalizationContextUsage capitalizationContextUsage;
        int value10;
        int value11;
        int value12;
        int patternCharIndex4;
        String result2;
        int value13;
        int patternCharIndex5;
        TimeZone tz;
        long date;
        DayPeriodRules ruleSet;
        DayPeriodRules.DayPeriod periodType;
        String toAppend;
        DayPeriodRules.DayPeriod periodType2;
        int bufstart2;
        int bufstart3 = buf.length();
        TimeZone tz2 = cal.getTimeZone();
        long date2 = cal.getTimeInMillis();
        String result3 = null;
        int patternCharIndex6 = getIndexFromChar(ch);
        if (patternCharIndex6 == -1) {
            if (ch == 'l') {
                return;
            }
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '\"');
        }
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex6];
        int value14 = field >= 0 ? patternCharIndex6 != 34 ? cal.get(field) : cal.getRelatedYear() : 0;
        NumberFormat currentNumberFormat = getNumberFormat(ch);
        DateFormatSymbols.CapitalizationContextUsage capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.OTHER;
        switch (patternCharIndex6) {
            case 0:
                patternCharIndex = patternCharIndex6;
                bufstart = bufstart3;
                c = 0;
                value = value14;
                if (cal.getType().equals("chinese") || cal.getType().equals("dangi")) {
                    value2 = value;
                    zeroPaddingNumber(currentNumberFormat, buf, value, 1, 9);
                    value14 = value2;
                    capContextUsageType = capContextUsageType2;
                    break;
                } else {
                    if (count == 5) {
                        safeAppend(this.formatData.narrowEras, value, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW;
                    } else if (count == 4) {
                        safeAppend(this.formatData.eraNames, value, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE;
                    } else {
                        safeAppend(this.formatData.eras, value, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV;
                    }
                    value14 = value;
                    capContextUsageType = capContextUsageType2;
                }
                break;
            case 1:
            case 18:
                patternCharIndex = patternCharIndex6;
                bufstart = bufstart3;
                c2 = 0;
                value3 = value14;
                value2 = (this.override != null || (this.override.compareTo("hebr") != 0 && this.override.indexOf("y=hebr") < 0) || value3 <= 5000 || value3 >= HEBREW_CAL_CUR_MILLENIUM_END_YEAR) ? value3 : value3 - 5000;
                if (count != 2) {
                    c = c2;
                    zeroPaddingNumber(currentNumberFormat, buf, value2, 2, 2);
                } else {
                    c = c2;
                    zeroPaddingNumber(currentNumberFormat, buf, value2, count, Integer.MAX_VALUE);
                }
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 2:
            case 26:
                int value15 = value14;
                bufstart = bufstart3;
                if (cal.getType().equals("hebrew")) {
                    boolean isLeap = HebrewCalendar.isLeapYear(cal.get(1));
                    if (isLeap) {
                        value4 = value15;
                        if (value4 == 6 && count >= 3) {
                            value5 = 13;
                            if (isLeap) {
                                value6 = 6;
                                if (value5 >= 6 && count < 3) {
                                    value5--;
                                }
                            } else {
                                value6 = 6;
                            }
                            i = value6;
                            value = value5;
                        }
                    } else {
                        value4 = value15;
                    }
                    value5 = value4;
                    if (isLeap) {
                    }
                    i = value6;
                    value = value5;
                } else {
                    value = value15;
                    i = 6;
                }
                int isLeapMonth = (this.formatData.leapMonthPatterns == null || this.formatData.leapMonthPatterns.length < 7) ? 0 : cal.get(22);
                if (count != 5) {
                    if (count != 4) {
                        if (count != 3) {
                            StringBuffer monthNumber = new StringBuffer();
                            patternCharIndex = patternCharIndex6;
                            int value16 = value;
                            zeroPaddingNumber(currentNumberFormat, monthNumber, value + 1, count, Integer.MAX_VALUE);
                            String[] monthNumberStrings = {monthNumber.toString()};
                            safeAppendWithMonthPattern(monthNumberStrings, 0, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[i] : null);
                            c = 0;
                            capContextUsageType = capContextUsageType2;
                            value14 = value16;
                            break;
                        } else {
                            if (patternCharIndex6 == 2) {
                                safeAppendWithMonthPattern(this.formatData.shortMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[1] : null);
                                capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                            } else {
                                safeAppendWithMonthPattern(this.formatData.standaloneShortMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[4] : null);
                                capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                            }
                            c = 0;
                            patternCharIndex = patternCharIndex6;
                            value14 = value;
                            capContextUsageType = capContextUsageType2;
                        }
                    } else {
                        if (patternCharIndex6 == 2) {
                            String[] strArr = this.formatData.months;
                            if (isLeapMonth != 0) {
                                c3 = 0;
                                r22 = this.formatData.leapMonthPatterns[0];
                            } else {
                                c3 = 0;
                            }
                            safeAppendWithMonthPattern(strArr, value, buf, r22);
                            capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                        } else {
                            c3 = 0;
                            safeAppendWithMonthPattern(this.formatData.standaloneMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[3] : null);
                            capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                        }
                        patternCharIndex = patternCharIndex6;
                        value14 = value;
                        c = c3;
                        capContextUsageType = capContextUsageType2;
                        break;
                    }
                } else {
                    if (patternCharIndex6 == 2) {
                        safeAppendWithMonthPattern(this.formatData.narrowMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[2] : null);
                    } else {
                        safeAppendWithMonthPattern(this.formatData.standaloneNarrowMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[5] : null);
                    }
                    patternCharIndex = patternCharIndex6;
                    value14 = value;
                    capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW;
                    c = 0;
                    break;
                }
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 20:
            case 21:
            case 22:
            case 34:
            default:
                value2 = value14;
                patternCharIndex = patternCharIndex6;
                bufstart = bufstart3;
                c = 0;
                zeroPaddingNumber(currentNumberFormat, buf, value2, count, Integer.MAX_VALUE);
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 4:
                bufstart = bufstart3;
                int patternCharIndex7 = value14;
                if (patternCharIndex7 == 0) {
                    value7 = patternCharIndex7;
                    zeroPaddingNumber(currentNumberFormat, buf, cal.getMaximum(11) + 1, count, Integer.MAX_VALUE);
                } else {
                    value7 = patternCharIndex7;
                    zeroPaddingNumber(currentNumberFormat, buf, patternCharIndex7, count, Integer.MAX_VALUE);
                }
                patternCharIndex = patternCharIndex6;
                value2 = value7;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 8:
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                int patternCharIndex8 = value14;
                this.numberFormat.setMinimumIntegerDigits(Math.min(3, count));
                this.numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
                value14 = count == 1 ? patternCharIndex8 / 100 : count == 2 ? patternCharIndex8 / 10 : patternCharIndex8;
                FieldPosition p = new FieldPosition(-1);
                this.numberFormat.format(value14, buf, p);
                if (count > 3) {
                    this.numberFormat.setMinimumIntegerDigits(count - 3);
                    this.numberFormat.format(0L, buf, p);
                }
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex2;
                c = 0;
                break;
            case 9:
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (count != 5) {
                    safeAppend(this.formatData.narrowWeekdays, value14, buf);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                } else if (count == 4) {
                    safeAppend(this.formatData.weekdays, value14, buf);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                } else if (count != 6 || this.formatData.shorterWeekdays == null) {
                    safeAppend(this.formatData.shortWeekdays, value14, buf);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                } else {
                    safeAppend(this.formatData.shorterWeekdays, value14, buf);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                }
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex2;
                c = 0;
                break;
            case 14:
                int value17 = value14;
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (count < 5) {
                    value3 = value17;
                } else if (this.formatData.ampmsNarrow != null) {
                    value3 = value17;
                    safeAppend(this.formatData.ampmsNarrow, value3, buf);
                    value2 = value3;
                    patternCharIndex = patternCharIndex2;
                    c = 0;
                    value14 = value2;
                    capContextUsageType = capContextUsageType2;
                    break;
                } else {
                    value3 = value17;
                }
                safeAppend(this.formatData.ampms, value3, buf);
                value2 = value3;
                patternCharIndex = patternCharIndex2;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
            case 15:
                int value18 = value14;
                bufstart = bufstart3;
                if (value18 == 0) {
                    value8 = value18;
                    zeroPaddingNumber(currentNumberFormat, buf, cal.getLeastMaximum(10) + 1, count, Integer.MAX_VALUE);
                } else {
                    value8 = value18;
                    zeroPaddingNumber(currentNumberFormat, buf, value8, count, Integer.MAX_VALUE);
                }
                patternCharIndex = patternCharIndex6;
                value2 = value8;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 17:
                value9 = value14;
                patternCharIndex3 = patternCharIndex6;
                bufstart = bufstart3;
                if (count < 4) {
                    result = tzFormat().format(TimeZoneFormat.Style.SPECIFIC_SHORT, tz2, date2);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else {
                    result = tzFormat().format(TimeZoneFormat.Style.SPECIFIC_LONG, tz2, date2);
                    capitalizationContextUsage = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                }
                capContextUsageType2 = capitalizationContextUsage;
                buf.append(result);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex3;
                value14 = value9;
                c = 0;
                break;
            case 19:
                value10 = value14;
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (count >= 3) {
                    value14 = cal.get(7);
                    if (count != 5) {
                    }
                    capContextUsageType = capContextUsageType2;
                    patternCharIndex = patternCharIndex2;
                    c = 0;
                    break;
                } else {
                    zeroPaddingNumber(currentNumberFormat, buf, value10, count, Integer.MAX_VALUE);
                    patternCharIndex = patternCharIndex2;
                    value2 = value10;
                    c = 0;
                    value14 = value2;
                    capContextUsageType = capContextUsageType2;
                    break;
                }
                break;
            case 23:
                value9 = value14;
                patternCharIndex3 = patternCharIndex6;
                bufstart = bufstart3;
                result = count < 4 ? tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, tz2, date2) : count == 5 ? tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, tz2, date2) : tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, tz2, date2);
                buf.append(result);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex3;
                value14 = value9;
                c = 0;
                break;
            case 24:
                value9 = value14;
                patternCharIndex3 = patternCharIndex6;
                bufstart = bufstart3;
                if (count == 1) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.GENERIC_SHORT, tz2, date2);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else if (count == 4) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.GENERIC_LONG, tz2, date2);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                }
                result = result3;
                buf.append(result);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex3;
                value14 = value9;
                c = 0;
                break;
            case 25:
                value10 = value14;
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (count >= 3) {
                    value14 = cal.get(7);
                    if (count == 5) {
                        safeAppend(this.formatData.standaloneNarrowWeekdays, value14, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                    } else if (count == 4) {
                        safeAppend(this.formatData.standaloneWeekdays, value14, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    } else if (count != 6 || this.formatData.standaloneShorterWeekdays == null) {
                        safeAppend(this.formatData.standaloneShortWeekdays, value14, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    } else {
                        safeAppend(this.formatData.standaloneShorterWeekdays, value14, buf);
                        capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    }
                    capContextUsageType = capContextUsageType2;
                    patternCharIndex = patternCharIndex2;
                    c = 0;
                    break;
                } else {
                    zeroPaddingNumber(currentNumberFormat, buf, value10, 1, Integer.MAX_VALUE);
                    patternCharIndex = patternCharIndex2;
                    value2 = value10;
                    c = 0;
                    value14 = value2;
                    capContextUsageType = capContextUsageType2;
                    break;
                }
            case 27:
                int value19 = value14;
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (count < 4) {
                    value11 = value19;
                    if (count != 3) {
                        value10 = value11;
                        zeroPaddingNumber(currentNumberFormat, buf, (value11 / 3) + 1, count, Integer.MAX_VALUE);
                        patternCharIndex = patternCharIndex2;
                        value2 = value10;
                        c = 0;
                        value14 = value2;
                        capContextUsageType = capContextUsageType2;
                        break;
                    } else {
                        safeAppend(this.formatData.shortQuarters, value11 / 3, buf);
                    }
                } else {
                    value11 = value19;
                    safeAppend(this.formatData.quarters, value11 / 3, buf);
                }
                value2 = value11;
                patternCharIndex = patternCharIndex2;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
            case 28:
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                value3 = value14;
                if (count < 4) {
                    if (count != 3) {
                        zeroPaddingNumber(currentNumberFormat, buf, (value3 / 3) + 1, count, Integer.MAX_VALUE);
                        patternCharIndex = patternCharIndex2;
                        value2 = value3;
                        c = 0;
                        value14 = value2;
                        capContextUsageType = capContextUsageType2;
                        break;
                    } else {
                        safeAppend(this.formatData.standaloneShortQuarters, value3 / 3, buf);
                    }
                } else {
                    safeAppend(this.formatData.standaloneQuarters, value3 / 3, buf);
                }
                value2 = value3;
                patternCharIndex = patternCharIndex2;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
            case 29:
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                int patternCharIndex9 = value14;
                if (count == 1) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ZONE_ID_SHORT, tz2, date2);
                } else if (count == 2) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ZONE_ID, tz2, date2);
                } else if (count == 3) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.EXEMPLAR_LOCATION, tz2, date2);
                } else if (count == 4) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.GENERIC_LOCATION, tz2, date2);
                    capContextUsageType2 = DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG;
                }
                buf.append(result3);
                value14 = patternCharIndex9;
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex2;
                c = 0;
                break;
            case 30:
                int value20 = value14;
                patternCharIndex2 = patternCharIndex6;
                bufstart = bufstart3;
                if (this.formatData.shortYearNames != null) {
                    value3 = value20;
                    if (value3 <= this.formatData.shortYearNames.length) {
                        safeAppend(this.formatData.shortYearNames, value3 - 1, buf);
                        value2 = value3;
                        patternCharIndex = patternCharIndex2;
                        c = 0;
                        value14 = value2;
                        capContextUsageType = capContextUsageType2;
                        break;
                    } else {
                        patternCharIndex = patternCharIndex2;
                        c2 = 0;
                    }
                } else {
                    patternCharIndex = patternCharIndex2;
                    value3 = value20;
                    c2 = 0;
                }
                if (this.override != null) {
                    break;
                }
                if (count != 2) {
                }
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 31:
                value12 = value14;
                patternCharIndex4 = patternCharIndex6;
                bufstart = bufstart3;
                if (count == 1) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT_SHORT, tz2, date2);
                } else if (count == 4) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, tz2, date2);
                }
                result2 = result3;
                buf.append(result2);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex4;
                value14 = value12;
                c = 0;
                break;
            case 32:
                value12 = value14;
                patternCharIndex4 = patternCharIndex6;
                bufstart = bufstart3;
                if (count == 1) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_SHORT, tz2, date2);
                } else if (count == 2) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FIXED, tz2, date2);
                } else if (count == 3) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FIXED, tz2, date2);
                } else if (count == 4) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FULL, tz2, date2);
                } else if (count == 5) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, tz2, date2);
                }
                result2 = result3;
                buf.append(result2);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex4;
                value14 = value12;
                c = 0;
                break;
            case 33:
                value12 = value14;
                patternCharIndex4 = patternCharIndex6;
                bufstart = bufstart3;
                if (count == 1) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT, tz2, date2);
                } else if (count == 2) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED, tz2, date2);
                } else if (count == 3) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED, tz2, date2);
                } else if (count == 4) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, tz2, date2);
                } else if (count == 5) {
                    result3 = tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL, tz2, date2);
                }
                result2 = result3;
                buf.append(result2);
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex4;
                value14 = value12;
                c = 0;
                break;
            case 35:
                int value21 = value14;
                bufstart = bufstart3;
                String toAppend2 = null;
                if (cal.get(11) == 12 && ((!this.hasMinute || cal.get(12) == 0) && (!this.hasSecond || cal.get(13) == 0))) {
                    int value22 = cal.get(9);
                    toAppend2 = count <= 3 ? this.formatData.abbreviatedDayPeriods[value22] : (count == 4 || count > 5) ? this.formatData.wideDayPeriods[value22] : this.formatData.narrowDayPeriods[value22];
                    value21 = value22;
                }
                String toAppend3 = toAppend2;
                if (toAppend3 == null) {
                    subFormat(buf, android.text.format.DateFormat.AM_PM, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
                } else {
                    buf.append(toAppend3);
                }
                capContextUsageType = capContextUsageType2;
                patternCharIndex = patternCharIndex6;
                value14 = value21;
                c = 0;
                break;
            case 36:
                DayPeriodRules ruleSet2 = DayPeriodRules.getInstance(getLocale());
                if (ruleSet2 == null) {
                    patternCharIndex5 = patternCharIndex6;
                    date = date2;
                    value13 = value14;
                    tz = tz2;
                    bufstart = bufstart3;
                    subFormat(buf, android.text.format.DateFormat.AM_PM, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
                } else {
                    value13 = value14;
                    patternCharIndex5 = patternCharIndex6;
                    tz = tz2;
                    bufstart = bufstart3;
                    date = date2;
                    int hour = cal.get(11);
                    int minute = this.hasMinute ? cal.get(12) : 0;
                    int second = this.hasSecond ? cal.get(13) : 0;
                    if (hour == 0 && minute == 0 && second == 0 && ruleSet2.hasMidnight()) {
                        periodType = DayPeriodRules.DayPeriod.MIDNIGHT;
                    } else if (hour == 12 && minute == 0 && second == 0 && ruleSet2.hasNoon()) {
                        periodType = DayPeriodRules.DayPeriod.NOON;
                    } else {
                        ruleSet = ruleSet2;
                        periodType = ruleSet.getDayPeriodForHour(hour);
                        toAppend = null;
                        if (periodType != DayPeriodRules.DayPeriod.AM && periodType != DayPeriodRules.DayPeriod.PM && periodType != DayPeriodRules.DayPeriod.MIDNIGHT) {
                            int index = periodType.ordinal();
                            toAppend = count > 3 ? this.formatData.abbreviatedDayPeriods[index] : (count == 4 || count > 5) ? this.formatData.wideDayPeriods[index] : this.formatData.narrowDayPeriods[index];
                        }
                        if (toAppend == null && (periodType == DayPeriodRules.DayPeriod.MIDNIGHT || periodType == DayPeriodRules.DayPeriod.NOON)) {
                            periodType = ruleSet.getDayPeriodForHour(hour);
                            int index2 = periodType.ordinal();
                            toAppend = count > 3 ? this.formatData.abbreviatedDayPeriods[index2] : (count == 4 || count > 5) ? this.formatData.wideDayPeriods[index2] : this.formatData.narrowDayPeriods[index2];
                        }
                        periodType2 = periodType;
                        String toAppend4 = toAppend;
                        if (periodType2 != DayPeriodRules.DayPeriod.AM || periodType2 == DayPeriodRules.DayPeriod.PM || toAppend4 == null) {
                            subFormat(buf, android.text.format.DateFormat.AM_PM, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
                        } else {
                            buf.append(toAppend4);
                        }
                    }
                    ruleSet = ruleSet2;
                    toAppend = null;
                    if (periodType != DayPeriodRules.DayPeriod.AM) {
                        int index3 = periodType.ordinal();
                        if (count > 3) {
                        }
                    }
                    if (toAppend == null) {
                        periodType = ruleSet.getDayPeriodForHour(hour);
                        int index22 = periodType.ordinal();
                        if (count > 3) {
                        }
                    }
                    periodType2 = periodType;
                    String toAppend42 = toAppend;
                    if (periodType2 != DayPeriodRules.DayPeriod.AM) {
                    }
                    subFormat(buf, android.text.format.DateFormat.AM_PM, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
                }
                patternCharIndex = patternCharIndex5;
                value2 = value13;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
            case 37:
                buf.append(this.formatData.getTimeSeparatorString());
                value2 = value14;
                patternCharIndex = patternCharIndex6;
                bufstart = bufstart3;
                c = 0;
                value14 = value2;
                capContextUsageType = capContextUsageType2;
                break;
        }
        if (fieldNum != 0 || capitalizationContext == null) {
            bufstart2 = bufstart;
        } else {
            bufstart2 = bufstart;
            if (UCharacter.isLowerCase(buf.codePointAt(bufstart2))) {
                boolean titlecase = false;
                switch (capitalizationContext) {
                    case CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE:
                        titlecase = true;
                        break;
                    case CAPITALIZATION_FOR_UI_LIST_OR_MENU:
                    case CAPITALIZATION_FOR_STANDALONE:
                        if (this.formatData.capitalization != null) {
                            boolean[] transforms = this.formatData.capitalization.get(capContextUsageType);
                            titlecase = capitalizationContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? transforms[c] : transforms[1];
                            break;
                        }
                }
                if (titlecase) {
                    if (this.capitalizationBrkIter == null) {
                        this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
                    }
                    String firstField = buf.substring(bufstart2);
                    String firstFieldTitleCase = UCharacter.toTitleCase(this.locale, firstField, this.capitalizationBrkIter, 768);
                    buf.replace(bufstart2, buf.length(), firstFieldTitleCase);
                }
            }
        }
        if (pos.getBeginIndex() == pos.getEndIndex()) {
            if (pos.getField() == PATTERN_INDEX_TO_DATE_FORMAT_FIELD[patternCharIndex]) {
                pos.setBeginIndex(beginOffset);
                pos.setEndIndex((buf.length() + beginOffset) - bufstart2);
            } else if (pos.getFieldAttribute() == PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[patternCharIndex]) {
                pos.setBeginIndex(beginOffset);
                pos.setEndIndex((buf.length() + beginOffset) - bufstart2);
            }
        }
    }

    private static void safeAppend(String[] array, int value, StringBuffer appendTo) {
        if (array != null && value >= 0 && value < array.length) {
            appendTo.append(array[value]);
        }
    }

    private static void safeAppendWithMonthPattern(String[] array, int value, StringBuffer appendTo, String monthPattern) {
        if (array != null && value >= 0 && value < array.length) {
            if (monthPattern == null) {
                appendTo.append(array[value]);
                return;
            }
            String s = SimpleFormatterImpl.formatRawPattern(monthPattern, 1, 1, new CharSequence[]{array[value]});
            appendTo.append(s);
        }
    }

    /* loaded from: classes5.dex */
    private static class PatternItem {
        final boolean isNumeric;
        final int length;
        final char type;

        PatternItem(char type, int length) {
            this.type = type;
            this.length = length;
            this.isNumeric = SimpleDateFormat.isNumeric(type, length);
        }
    }

    private Object[] getPatternItems() {
        if (this.patternItems != null) {
            return this.patternItems;
        }
        this.patternItems = (Object[]) PARSED_PATTERN_CACHE.get(this.pattern);
        if (this.patternItems != null) {
            return this.patternItems;
        }
        StringBuilder text = new StringBuilder();
        char itemType = 0;
        List<Object> items = new ArrayList<>();
        int itemLength = 1;
        boolean inQuote = false;
        boolean isPrevQuote = false;
        for (int i = 0; i < this.pattern.length(); i++) {
            char ch = this.pattern.charAt(i);
            if (ch == '\'') {
                if (isPrevQuote) {
                    text.append(android.text.format.DateFormat.QUOTE);
                    isPrevQuote = false;
                } else {
                    isPrevQuote = true;
                    if (itemType != 0) {
                        items.add(new PatternItem(itemType, itemLength));
                        itemType = 0;
                    }
                }
                inQuote = !inQuote;
            } else {
                isPrevQuote = false;
                if (inQuote) {
                    text.append(ch);
                } else if (isSyntaxChar(ch)) {
                    if (ch == itemType) {
                        itemLength++;
                    } else {
                        if (itemType == 0) {
                            if (text.length() > 0) {
                                items.add(text.toString());
                                text.setLength(0);
                            }
                        } else {
                            items.add(new PatternItem(itemType, itemLength));
                        }
                        itemType = ch;
                        itemLength = 1;
                    }
                } else {
                    if (itemType != 0) {
                        items.add(new PatternItem(itemType, itemLength));
                        itemType = 0;
                    }
                    text.append(ch);
                }
            }
        }
        if (itemType == 0) {
            if (text.length() > 0) {
                items.add(text.toString());
                text.setLength(0);
            }
        } else {
            items.add(new PatternItem(itemType, itemLength));
        }
        this.patternItems = items.toArray(new Object[items.size()]);
        PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
        return this.patternItems;
    }

    @Deprecated
    protected void zeroPaddingNumber(NumberFormat nf, StringBuffer buf, int value, int minDigits, int maxDigits) {
        if (this.useLocalZeroPaddingNumberFormat && value >= 0) {
            fastZeroPaddingNumber(buf, value, minDigits, maxDigits);
            return;
        }
        nf.setMinimumIntegerDigits(minDigits);
        nf.setMaximumIntegerDigits(maxDigits);
        nf.format(value, buf, new FieldPosition(-1));
    }

    @Override // com.ibm.icu.text.DateFormat
    public void setNumberFormat(NumberFormat newNumberFormat) {
        super.setNumberFormat(newNumberFormat);
        initLocalZeroPaddingNumberFormat();
        initializeTimeZoneFormat(true);
        if (this.numberFormatters != null) {
            this.numberFormatters = null;
        }
        if (this.overrideMap != null) {
            this.overrideMap = null;
        }
    }

    private void initLocalZeroPaddingNumberFormat() {
        if (this.numberFormat instanceof DecimalFormat) {
            DecimalFormatSymbols tmpDecfs = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols();
            String[] tmpDigits = tmpDecfs.getDigitStringsLocal();
            this.useLocalZeroPaddingNumberFormat = true;
            this.decDigits = new char[10];
            int i = 0;
            while (true) {
                if (i >= 10) {
                    break;
                } else if (tmpDigits[i].length() > 1) {
                    this.useLocalZeroPaddingNumberFormat = false;
                    break;
                } else {
                    this.decDigits[i] = tmpDigits[i].charAt(0);
                    i++;
                }
            }
        } else if (this.numberFormat instanceof DateNumberFormat) {
            this.decDigits = this.numberFormat.getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        } else {
            this.useLocalZeroPaddingNumberFormat = false;
        }
        if (this.useLocalZeroPaddingNumberFormat) {
            this.decimalBuf = new char[10];
        }
    }

    private void fastZeroPaddingNumber(StringBuffer buf, int value, int minDigits, int maxDigits) {
        int limit = this.decimalBuf.length < maxDigits ? this.decimalBuf.length : maxDigits;
        int index = limit - 1;
        while (true) {
            this.decimalBuf[index] = this.decDigits[value % 10];
            value /= 10;
            if (index == 0 || value == 0) {
                break;
            }
            index--;
        }
        int padding = minDigits - (limit - index);
        while (padding > 0 && index > 0) {
            index--;
            this.decimalBuf[index] = this.decDigits[0];
            padding--;
        }
        while (padding > 0) {
            buf.append(this.decDigits[0]);
            padding--;
        }
        buf.append(this.decimalBuf, index, limit - index);
    }

    protected String zeroPaddingNumber(long value, int minDigits, int maxDigits) {
        this.numberFormat.setMinimumIntegerDigits(minDigits);
        this.numberFormat.setMaximumIntegerDigits(maxDigits);
        return this.numberFormat.format(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isNumeric(char formatChar, int count) {
        return NUMERIC_FORMAT_CHARS.indexOf(formatChar) >= 0 || (count <= 2 && NUMERIC_FORMAT_CHARS2.indexOf(formatChar) >= 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:237:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x04e9  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0505  */
    /* JADX WARN: Removed duplicated region for block: B:283:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v41 */
    /* JADX WARN: Type inference failed for: r11v42 */
    @Override // com.ibm.icu.text.DateFormat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void parse(String text, Calendar cal, ParsePosition parsePos) {
        TimeZone backupTZ;
        Calendar resultCal;
        Calendar cal2;
        int pos;
        Calendar cal3;
        SimpleDateFormat simpleDateFormat;
        long localMillis;
        boolean z;
        ?? r11;
        int i;
        int resolvedSavings;
        TimeZoneTransition beforeTrs;
        TimeZoneTransition afterTrs;
        int hourOfDay;
        int i2;
        Output<TimeZoneFormat.TimeType> tzTimeType;
        Output<DayPeriodRules.DayPeriod> dayPeriod;
        Object[] items;
        Calendar resultCal2;
        Calendar cal4;
        TimeZone backupTZ2;
        int numericFieldStart;
        int pos2;
        int numericFieldStart2;
        int numericFieldStart3;
        int i3;
        int pos3;
        if (cal == this.calendar || cal.getType().equals(this.calendar.getType())) {
            backupTZ = null;
            resultCal = null;
            cal2 = cal;
        } else {
            this.calendar.setTimeInMillis(cal.getTimeInMillis());
            TimeZone backupTZ3 = this.calendar.getTimeZone();
            this.calendar.setTimeZone(cal.getTimeZone());
            backupTZ = backupTZ3;
            resultCal = cal;
            cal2 = this.calendar;
        }
        int pos4 = parsePos.getIndex();
        if (pos4 < 0) {
            parsePos.setErrorIndex(0);
            return;
        }
        int start = pos4;
        Output<DayPeriodRules.DayPeriod> dayPeriod2 = new Output<>((Object) null);
        Output<TimeZoneFormat.TimeType> tzTimeType2 = new Output<>(TimeZoneFormat.TimeType.UNKNOWN);
        boolean[] ambiguousYear = {false};
        int i4 = -1;
        MessageFormat numericLeapMonthFormatter = null;
        if (this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7) {
            numericLeapMonthFormatter = new MessageFormat(this.formatData.leapMonthPatterns[6], this.locale);
        }
        MessageFormat numericLeapMonthFormatter2 = numericLeapMonthFormatter;
        Object[] items2 = getPatternItems();
        int pos5 = pos4;
        int numericFieldLength = 0;
        int numericStartPos = 0;
        int numericFieldStart4 = 0;
        while (true) {
            int i5 = numericFieldStart4;
            int i6 = items2.length;
            if (i5 < i6) {
                if (items2[i5] instanceof PatternItem) {
                    PatternItem field = (PatternItem) items2[i5];
                    if (field.isNumeric && i4 == -1 && i5 + 1 < items2.length && (items2[i5 + 1] instanceof PatternItem) && ((PatternItem) items2[i5 + 1]).isNumeric) {
                        numericFieldStart2 = i5;
                        int numericFieldStart5 = field.length;
                        numericFieldLength = numericFieldStart5;
                        numericStartPos = pos5;
                    } else {
                        numericFieldStart2 = i4;
                    }
                    if (numericFieldStart2 != -1) {
                        int len = field.length;
                        if (numericFieldStart2 == i5) {
                            len = numericFieldLength;
                        }
                        tzTimeType = tzTimeType2;
                        dayPeriod = dayPeriod2;
                        int start2 = start;
                        Object[] items3 = items2;
                        resultCal2 = resultCal;
                        pos3 = subParse(text, pos5, field.type, len, true, false, ambiguousYear, cal2, numericLeapMonthFormatter2, tzTimeType);
                        if (pos3 < 0) {
                            numericFieldLength--;
                            if (numericFieldLength == 0) {
                                parsePos.setIndex(start2);
                                parsePos.setErrorIndex(pos3);
                                if (backupTZ != null) {
                                    this.calendar.setTimeZone(backupTZ);
                                    return;
                                }
                                return;
                            }
                            i4 = numericFieldStart2;
                            pos5 = numericStartPos;
                            start = start2;
                            tzTimeType2 = tzTimeType;
                            dayPeriod2 = dayPeriod;
                            items2 = items3;
                            resultCal = resultCal2;
                            numericFieldStart4 = i4;
                        } else {
                            numericFieldStart3 = numericFieldStart2;
                            start = start2;
                            cal4 = cal2;
                            i3 = i5;
                            items = items3;
                            backupTZ2 = backupTZ;
                        }
                    } else {
                        int i7 = i5;
                        tzTimeType = tzTimeType2;
                        dayPeriod = dayPeriod2;
                        int pos6 = pos5;
                        Object[] items4 = items2;
                        resultCal2 = resultCal;
                        int start3 = start;
                        if (field.type != 'l') {
                            numericFieldStart3 = -1;
                            Calendar calendar = cal2;
                            cal4 = cal2;
                            backupTZ2 = backupTZ;
                            pos3 = subParse(text, pos6, field.type, field.length, false, true, ambiguousYear, calendar, numericLeapMonthFormatter2, tzTimeType, dayPeriod);
                            if (pos3 >= 0) {
                                items = items4;
                                start = start3;
                                i3 = i7;
                            } else if (pos3 != ISOSpecialEra) {
                                parsePos.setIndex(start3);
                                parsePos.setErrorIndex(pos6);
                                if (backupTZ2 != null) {
                                    this.calendar.setTimeZone(backupTZ2);
                                    return;
                                }
                                return;
                            } else {
                                pos3 = pos6;
                                items = items4;
                                if (i7 + 1 < items.length) {
                                    try {
                                        String patl = (String) items[i7 + 1];
                                        if (patl == null) {
                                            patl = (String) items[i7 + 1];
                                        }
                                        int plen = patl.length();
                                        int idx = 0;
                                        while (idx < plen) {
                                            char pch = patl.charAt(idx);
                                            if (!PatternProps.isWhiteSpace(pch)) {
                                                break;
                                            }
                                            idx++;
                                        }
                                        if (idx == plen) {
                                            i7++;
                                        }
                                        i3 = i7;
                                        start = start3;
                                    } catch (ClassCastException e) {
                                        parsePos.setIndex(start3);
                                        parsePos.setErrorIndex(pos6);
                                        if (backupTZ2 != null) {
                                            this.calendar.setTimeZone(backupTZ2);
                                            return;
                                        }
                                        return;
                                    }
                                } else {
                                    start = start3;
                                    i3 = i7;
                                }
                            }
                        } else {
                            start = start3;
                            cal4 = cal2;
                            items = items4;
                            backupTZ2 = backupTZ;
                            numericFieldStart3 = numericFieldStart2;
                            i3 = i7;
                            pos3 = pos6;
                        }
                    }
                    i2 = i3;
                    pos2 = pos3;
                    numericFieldStart = numericFieldStart3;
                } else {
                    i2 = i5;
                    tzTimeType = tzTimeType2;
                    dayPeriod = dayPeriod2;
                    items = items2;
                    resultCal2 = resultCal;
                    cal4 = cal2;
                    backupTZ2 = backupTZ;
                    numericFieldStart = -1;
                    boolean[] complete = new boolean[1];
                    pos2 = matchLiteral(text, pos5, items, i5, complete);
                    if (!complete[0]) {
                        parsePos.setIndex(start);
                        parsePos.setErrorIndex(pos2);
                        if (backupTZ2 != null) {
                            this.calendar.setTimeZone(backupTZ2);
                            return;
                        }
                        return;
                    }
                }
                pos5 = pos2;
                items2 = items;
                backupTZ = backupTZ2;
                tzTimeType2 = tzTimeType;
                dayPeriod2 = dayPeriod;
                resultCal = resultCal2;
                cal2 = cal4;
                i4 = numericFieldStart;
                numericFieldStart4 = i2 + 1;
            } else {
                Output<TimeZoneFormat.TimeType> tzTimeType3 = tzTimeType2;
                Output<DayPeriodRules.DayPeriod> dayPeriod3 = dayPeriod2;
                Object[] items5 = items2;
                Calendar resultCal3 = resultCal;
                Calendar cal5 = cal2;
                TimeZone backupTZ4 = backupTZ;
                int pos7 = pos5;
                if (pos7 < text.length()) {
                    char extra = text.charAt(pos7);
                    if (extra == '.' && getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && items5.length != 0) {
                        Object lastItem = items5[items5.length - 1];
                        if ((lastItem instanceof PatternItem) && !((PatternItem) lastItem).isNumeric) {
                            pos7++;
                        }
                    }
                }
                if (dayPeriod3.value != null) {
                    DayPeriodRules ruleSet = DayPeriodRules.getInstance(getLocale());
                    cal3 = cal5;
                    if (cal3.isSet(10) || cal3.isSet(11)) {
                        pos = pos7;
                        if (cal3.isSet(11)) {
                            hourOfDay = cal3.get(11);
                        } else {
                            hourOfDay = cal3.get(10);
                            if (hourOfDay == 0) {
                                hourOfDay = 12;
                            }
                        }
                        if (hourOfDay != 0 && (13 > hourOfDay || hourOfDay > 23)) {
                            if (hourOfDay == 12) {
                                hourOfDay = 0;
                            }
                            double currentHour = hourOfDay + (cal3.get(12) / 60.0d);
                            double hoursAheadMidPoint = currentHour - ruleSet.getMidPointForDayPeriod((DayPeriodRules.DayPeriod) dayPeriod3.value);
                            if (-6.0d > hoursAheadMidPoint || hoursAheadMidPoint >= 6.0d) {
                                cal3.set(9, 1);
                            } else {
                                cal3.set(9, 0);
                            }
                        }
                        cal3.set(11, hourOfDay);
                    } else {
                        pos = pos7;
                        double midPoint = ruleSet.getMidPointForDayPeriod((DayPeriodRules.DayPeriod) dayPeriod3.value);
                        int midPointHour = (int) midPoint;
                        int midPointMinute = midPoint - ((double) midPointHour) > 0.0d ? 30 : 0;
                        cal3.set(11, midPointHour);
                        cal3.set(12, midPointMinute);
                    }
                } else {
                    pos = pos7;
                    cal3 = cal5;
                }
                int pos8 = pos;
                parsePos.setIndex(pos8);
                try {
                    TimeZoneFormat.TimeType tztype = (TimeZoneFormat.TimeType) tzTimeType3.value;
                    if (!ambiguousYear[0]) {
                        try {
                            if (tztype == TimeZoneFormat.TimeType.UNKNOWN) {
                                if (resultCal3 != null) {
                                    resultCal3.setTimeZone(cal3.getTimeZone());
                                    resultCal3.setTimeInMillis(cal3.getTimeInMillis());
                                }
                                if (backupTZ4 == null) {
                                    this.calendar.setTimeZone(backupTZ4);
                                    return;
                                }
                                return;
                            }
                        } catch (IllegalArgumentException e2) {
                            simpleDateFormat = this;
                            parsePos.setErrorIndex(pos8);
                            parsePos.setIndex(start);
                            if (backupTZ4 == null) {
                            }
                        }
                    }
                    if (ambiguousYear[0]) {
                        Date parsedDate = ((Calendar) cal3.clone()).getTime();
                        if (parsedDate.before(getDefaultCenturyStart())) {
                            cal3.set(1, getDefaultCenturyStartYear() + 100);
                        }
                    }
                    if (tztype != TimeZoneFormat.TimeType.UNKNOWN) {
                        try {
                            Calendar copy = (Calendar) cal3.clone();
                            BasicTimeZone timeZone = copy.getTimeZone();
                            BasicTimeZone btz = timeZone instanceof BasicTimeZone ? timeZone : null;
                            copy.set(15, 0);
                            copy.set(16, 0);
                            long localMillis2 = copy.getTimeInMillis();
                            int[] offsets = new int[2];
                            if (btz != null) {
                                if (tztype == TimeZoneFormat.TimeType.STANDARD) {
                                    btz.getOffsetFromLocal(localMillis2, 1, 1, offsets);
                                } else {
                                    btz.getOffsetFromLocal(localMillis2, 3, 3, offsets);
                                }
                                localMillis = localMillis2;
                                r11 = 1;
                            } else {
                                localMillis = localMillis2;
                                try {
                                    timeZone.getOffset(localMillis, true, offsets);
                                    if (tztype == TimeZoneFormat.TimeType.STANDARD) {
                                        try {
                                            if (offsets[1] != 0) {
                                                z = true;
                                                try {
                                                    timeZone.getOffset(localMillis - 86400000, z, offsets);
                                                    r11 = z;
                                                } catch (IllegalArgumentException e3) {
                                                    simpleDateFormat = this;
                                                    parsePos.setErrorIndex(pos8);
                                                    parsePos.setIndex(start);
                                                    if (backupTZ4 == null) {
                                                    }
                                                }
                                            }
                                        } catch (IllegalArgumentException e4) {
                                            simpleDateFormat = this;
                                            parsePos.setErrorIndex(pos8);
                                            parsePos.setIndex(start);
                                            if (backupTZ4 == null) {
                                                simpleDateFormat.calendar.setTimeZone(backupTZ4);
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                    if (tztype == TimeZoneFormat.TimeType.DAYLIGHT) {
                                        z = true;
                                        r11 = 1;
                                        if (offsets[1] != 0) {
                                        }
                                        timeZone.getOffset(localMillis - 86400000, z, offsets);
                                        r11 = z;
                                    } else {
                                        r11 = 1;
                                    }
                                } catch (IllegalArgumentException e5) {
                                    simpleDateFormat = this;
                                    parsePos.setErrorIndex(pos8);
                                    parsePos.setIndex(start);
                                    if (backupTZ4 == null) {
                                    }
                                }
                            }
                            int resolvedSavings2 = offsets[r11];
                            if (tztype == TimeZoneFormat.TimeType.STANDARD) {
                                if (offsets[r11] != 0) {
                                    resolvedSavings = 0;
                                    cal3.set(15, offsets[0]);
                                    cal3.set(16, resolvedSavings);
                                } else {
                                    i = resolvedSavings2;
                                    resolvedSavings = i;
                                    cal3.set(15, offsets[0]);
                                    cal3.set(16, resolvedSavings);
                                }
                            } else if (offsets[r11] == 0) {
                                if (btz != null) {
                                    long time = localMillis + offsets[0];
                                    int beforeSav = 0;
                                    long beforeT = time;
                                    int afterSav = 0;
                                    do {
                                        beforeTrs = btz.getPreviousTransition(beforeT, true);
                                        if (beforeTrs == null) {
                                            break;
                                        }
                                        beforeT = beforeTrs.getTime() - 1;
                                        beforeSav = beforeTrs.getFrom().getDSTSavings();
                                    } while (beforeSav == 0);
                                    long beforeT2 = beforeT;
                                    long afterT = time;
                                    do {
                                        afterTrs = btz.getNextTransition(afterT, false);
                                        if (afterTrs == null) {
                                            break;
                                        }
                                        afterT = afterTrs.getTime();
                                        afterSav = afterTrs.getTo().getDSTSavings();
                                    } while (afterSav == 0);
                                    resolvedSavings = (beforeTrs == null || afterTrs == null) ? (beforeTrs == null || beforeSav == 0) ? (afterTrs == null || afterSav == 0) ? btz.getDSTSavings() : afterSav : beforeSav : time - beforeT2 > afterT - time ? afterSav : beforeSav;
                                } else {
                                    resolvedSavings = timeZone.getDSTSavings();
                                }
                                if (resolvedSavings == 0) {
                                    resolvedSavings = millisPerHour;
                                }
                                cal3.set(15, offsets[0]);
                                cal3.set(16, resolvedSavings);
                            } else {
                                i = resolvedSavings2;
                                resolvedSavings = i;
                                cal3.set(15, offsets[0]);
                                cal3.set(16, resolvedSavings);
                            }
                        } catch (IllegalArgumentException e6) {
                            simpleDateFormat = this;
                        }
                    }
                    if (resultCal3 != null) {
                    }
                    if (backupTZ4 == null) {
                    }
                } catch (IllegalArgumentException e7) {
                    simpleDateFormat = this;
                }
            }
        }
    }

    private int matchLiteral(String text, int pos, Object[] items, int itemIndex, boolean[] complete) {
        String patternLiteral = (String) items[itemIndex];
        int plen = patternLiteral.length();
        int tlen = text.length();
        int pos2 = pos;
        int idx = 0;
        while (idx < plen && pos2 < tlen) {
            char pch = patternLiteral.charAt(idx);
            char ich = text.charAt(pos2);
            if (PatternProps.isWhiteSpace(pch) && PatternProps.isWhiteSpace(ich)) {
                while (idx + 1 < plen && PatternProps.isWhiteSpace(patternLiteral.charAt(idx + 1))) {
                    idx++;
                }
                while (pos2 + 1 < tlen && PatternProps.isWhiteSpace(text.charAt(pos2 + 1))) {
                    pos2++;
                }
            } else if (pch != ich) {
                if (ich != '.' || pos2 != pos || itemIndex <= 0 || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                    if ((pch == ' ' || pch == '.') && getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                        idx++;
                    } else if (pos2 == pos || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)) {
                        break;
                    } else {
                        idx++;
                    }
                } else {
                    Object before = items[itemIndex - 1];
                    if (!(before instanceof PatternItem)) {
                        break;
                    }
                    boolean isNumeric = ((PatternItem) before).isNumeric;
                    if (isNumeric) {
                        break;
                    }
                    pos2++;
                }
            }
            idx++;
            pos2++;
        }
        complete[0] = idx == plen;
        if (!complete[0] && getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && itemIndex > 0 && itemIndex < items.length - 1 && pos < tlen) {
            Object before2 = items[itemIndex - 1];
            Object after = items[itemIndex + 1];
            if ((before2 instanceof PatternItem) && (after instanceof PatternItem)) {
                char beforeType = ((PatternItem) before2).type;
                char afterType = ((PatternItem) after).type;
                if (DATE_PATTERN_TYPE.contains(beforeType) != DATE_PATTERN_TYPE.contains(afterType)) {
                    int newPos = pos;
                    while (newPos < tlen && PatternProps.isWhiteSpace(text.charAt(newPos))) {
                        newPos++;
                    }
                    complete[0] = newPos > pos;
                    return newPos;
                }
                return pos2;
            }
            return pos2;
        }
        return pos2;
    }

    protected int matchString(String text, int start, int field, String[] data, Calendar cal) {
        return matchString(text, start, field, data, null, cal);
    }

    @Deprecated
    private int matchString(String text, int start, int field, String[] data, String monthPattern, Calendar cal) {
        String leapMonthName;
        int length;
        int matchLength;
        int matchLength2;
        int count = data.length;
        int i = field == 7 ? 1 : 0;
        int isLeapMonth = 0;
        int bestMatch = -1;
        int bestMatchLength = 0;
        for (int bestMatchLength2 = i; bestMatchLength2 < count; bestMatchLength2++) {
            int length2 = data[bestMatchLength2].length();
            if (length2 > bestMatchLength && (matchLength2 = regionMatchesWithOptionalDot(text, start, data[bestMatchLength2], length2)) >= 0) {
                bestMatch = bestMatchLength2;
                bestMatchLength = matchLength2;
                isLeapMonth = 0;
            }
            if (monthPattern != null && (length = (leapMonthName = SimpleFormatterImpl.formatRawPattern(monthPattern, 1, 1, new CharSequence[]{data[bestMatchLength2]})).length()) > bestMatchLength && (matchLength = regionMatchesWithOptionalDot(text, start, leapMonthName, length)) >= 0) {
                bestMatch = bestMatchLength2;
                bestMatchLength = matchLength;
                isLeapMonth = 1;
            }
        }
        if (bestMatch >= 0) {
            if (field >= 0) {
                if (field == 1) {
                    bestMatch++;
                }
                cal.set(field, bestMatch);
                if (monthPattern != null) {
                    cal.set(22, isLeapMonth);
                }
            }
            return start + bestMatchLength;
        }
        return ~start;
    }

    private int regionMatchesWithOptionalDot(String text, int start, String data, int length) {
        boolean matches = text.regionMatches(true, start, data, 0, length);
        if (matches) {
            return length;
        }
        if (data.length() > 0 && data.charAt(data.length() - 1) == '.' && text.regionMatches(true, start, data, 0, length - 1)) {
            return length - 1;
        }
        return -1;
    }

    protected int matchQuarterString(String text, int start, int field, String[] data, Calendar cal) {
        int matchLength;
        int count = data.length;
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < count; i++) {
            int length = data[i].length();
            if (length > bestMatchLength && (matchLength = regionMatchesWithOptionalDot(text, start, data[i], length)) >= 0) {
                bestMatch = i;
                bestMatchLength = matchLength;
            }
        }
        if (bestMatch >= 0) {
            cal.set(field, bestMatch * 3);
            return start + bestMatchLength;
        }
        return -start;
    }

    private int matchDayPeriodString(String text, int start, String[] data, int dataLength, Output<DayPeriodRules.DayPeriod> dayPeriod) {
        int length;
        int matchLength;
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < dataLength; i++) {
            if (data[i] != null && (length = data[i].length()) > bestMatchLength && (matchLength = regionMatchesWithOptionalDot(text, start, data[i], length)) >= 0) {
                bestMatch = i;
                bestMatchLength = matchLength;
            }
        }
        if (bestMatch >= 0) {
            dayPeriod.value = DayPeriodRules.DayPeriod.VALUES[bestMatch];
            return start + bestMatchLength;
        }
        return -start;
    }

    protected int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal) {
        return subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, null, null);
    }

    private int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal, MessageFormat numericLeapMonthFormatter, Output<TimeZoneFormat.TimeType> tzTimeType) {
        return subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, null, null, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0197, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH) == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01a5, code lost:
        if (r37.formatData.getTimeSeparatorString().equals(".") != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01a7, code lost:
        r0.add(".");
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01c4, code lost:
        return matchString(r38, r15, -1, (java.lang.String[]) r0.toArray(new java.lang.String[0]), r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01c5, code lost:
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01ce, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01d0, code lost:
        if (r41 != 3) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01d2, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.abbreviatedDayPeriods, r37.formatData.abbreviatedDayPeriods.length, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01e7, code lost:
        if (r0 <= 0) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x01e9, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x01f0, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x01f3, code lost:
        if (r41 != 4) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01f5, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.wideDayPeriods, r37.formatData.wideDayPeriods.length, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x020a, code lost:
        if (r0 <= 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x020c, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0213, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0216, code lost:
        if (r41 != 4) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0218, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.narrowDayPeriods, r37.formatData.narrowDayPeriods.length, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x022d, code lost:
        if (r0 <= 0) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x022f, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0230, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0231, code lost:
        r6 = subParse(r38, r15, android.text.format.DateFormat.AM_PM, r41, r42, r43, r44, r45, r46, r47, r48);
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x025a, code lost:
        if (r6 <= 0) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x025c, code lost:
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x025d, code lost:
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0264, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0267, code lost:
        if (r41 != 3) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0269, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.abbreviatedDayPeriods, 2, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x027a, code lost:
        if (r0 <= 0) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x027c, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0283, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0285, code lost:
        r9 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0286, code lost:
        if (r41 != 4) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0289, code lost:
        r9 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x028a, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.wideDayPeriods, 2, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x029b, code lost:
        if (r0 <= 0) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x029d, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02a4, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02a6, code lost:
        if (r41 != r9) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02a8, code lost:
        r0 = matchDayPeriodString(r38, r15, r37.formatData.narrowDayPeriods, 2, r48);
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02b9, code lost:
        if (r0 <= 0) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02bb, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02bc, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02bd, code lost:
        r30 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02c7, code lost:
        switch(r41) {
            case 1: goto L137;
            case 2: goto L136;
            case 3: goto L135;
            case 4: goto L134;
            default: goto L127;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02ca, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02cd, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02d0, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02d3, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02d6, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02d9, code lost:
        r1 = tzFormat().parse(r0, r38, r30, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02e6, code lost:
        if (r1 == null) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02e8, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02f1, code lost:
        return r30.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02f5, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02f6, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0302, code lost:
        switch(r41) {
            case 1: goto L150;
            case 2: goto L149;
            case 3: goto L148;
            case 4: goto L147;
            default: goto L140;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0305, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0308, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x030b, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FIXED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x030e, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FIXED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0311, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0314, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x031d, code lost:
        if (r1 == null) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x031f, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0326, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0328, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0329, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0336, code lost:
        if (r41 >= 4) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0338, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x033b, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x033d, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0345, code lost:
        if (r1 == null) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0347, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x034e, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0350, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0351, code lost:
        r28 = r6;
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0361, code lost:
        if (r37.formatData.shortYearNames == null) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0363, code lost:
        r0 = matchString(r38, r15, 1, r37.formatData.shortYearNames, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0374, code lost:
        if (r0 <= 0) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0376, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0377, code lost:
        if (r18 == null) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x037f, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) != false) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0385, code lost:
        if (r37.formatData.shortYearNames == null) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0387, code lost:
        r6 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x038e, code lost:
        if (r6 <= r37.formatData.shortYearNames.length) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0391, code lost:
        r6 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0393, code lost:
        r45.set(r7, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x039a, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x039e, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x039f, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x03aa, code lost:
        switch(r41) {
            case 1: goto L190;
            case 2: goto L189;
            case 3: goto L188;
            default: goto L181;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x03ad, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LOCATION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x03b0, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.EXEMPLAR_LOCATION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x03b3, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x03b6, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x03b9, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x03c1, code lost:
        if (r1 == null) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x03c3, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x03ca, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x03cc, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x03cd, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x03da, code lost:
        if (r41 <= 2) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x03dc, code lost:
        if (r18 == null) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x03e4, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) == false) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x03e7, code lost:
        r17 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x03ef, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x03f1, code lost:
        if (r41 != 4) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x03f3, code lost:
        r0 = matchQuarterString(r38, r15, 2, r37.formatData.standaloneQuarters, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0405, code lost:
        if (r0 <= 0) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x0407, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x040e, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x0410, code lost:
        if (r41 != 3) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x0413, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x0424, code lost:
        return matchQuarterString(r38, r15, 2, r37.formatData.standaloneShortQuarters, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0425, code lost:
        r45.set(2, (r6 - 1) * 3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x042f, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x0430, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x043d, code lost:
        if (r41 <= 2) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x043f, code lost:
        if (r18 == null) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x0447, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) == false) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x044a, code lost:
        r17 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0452, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x0454, code lost:
        if (r41 != 4) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x0456, code lost:
        r0 = matchQuarterString(r38, r15, 2, r37.formatData.quarters, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0468, code lost:
        if (r0 <= 0) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x046a, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x0471, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x0473, code lost:
        if (r41 != 3) goto L221;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0476, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x0487, code lost:
        return matchQuarterString(r38, r15, 2, r37.formatData.shortQuarters, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0488, code lost:
        r45.set(2, (r6 - 1) * 3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x0492, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0493, code lost:
        r11 = r9;
        r29 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x049f, code lost:
        if (r41 == r7) goto L259;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x04a1, code lost:
        if (r18 == null) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x04a9, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) == false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x04ab, code lost:
        r9 = r45;
        r33 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x04b7, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x04b9, code lost:
        if (r41 != 4) goto L240;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x04bc, code lost:
        r17 = 0;
        r7 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x04c3, code lost:
        r7 = 6;
        r0 = matchString(r38, r15, 7, r37.formatData.standaloneWeekdays, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x04df, code lost:
        if (r0 <= 0) goto L258;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x04e1, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x04e2, code lost:
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x04ea, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x04ec, code lost:
        if (r41 != 3) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007c, code lost:
        r2.setIndex(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x04ee, code lost:
        r0 = matchString(r38, r15, 7, r37.formatData.standaloneShortWeekdays, null, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x0501, code lost:
        if (r0 <= 0) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x0503, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x050a, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x050c, code lost:
        if (r41 != r7) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0512, code lost:
        if (r37.formatData.standaloneShorterWeekdays == null) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x0525, code lost:
        return matchString(r38, r15, 7, r37.formatData.standaloneShorterWeekdays, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0081, code lost:
        if (r4 == 4) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x0526, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x0527, code lost:
        r9 = r45;
        r33 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x052a, code lost:
        r9.set(r29, r33);
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0535, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x0536, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x0542, code lost:
        if (r41 >= 4) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x0544, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x0547, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LONG;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x0549, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x0551, code lost:
        if (r1 == null) goto L267;
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0553, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x055a, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x055c, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x055d, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:305:0x0569, code lost:
        if (r41 >= 4) goto L279;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x056b, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x056e, code lost:
        if (r41 != 5) goto L281;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0570, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x0573, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0085, code lost:
        if (r4 == 15) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:310:0x0575, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x057d, code lost:
        if (r1 == null) goto L277;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x057f, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x0586, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x0588, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x0589, code lost:
        r4 = r6;
        r11 = r9;
        r6 = r26;
        r9 = 4;
        r8 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x0598, code lost:
        if (r41 <= 2) goto L319;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x059a, code lost:
        if (r18 == null) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0087, code lost:
        if (r4 != 2) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x05a2, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) == false) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x05a5, code lost:
        r5 = r4;
        r4 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x05aa, code lost:
        r45.set(r6, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x05b1, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x05b2, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x05be, code lost:
        if (r41 >= 4) goto L330;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x05c0, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_SHORT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x05c3, code lost:
        r0 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_LONG;
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x05c5, code lost:
        r1 = tzFormat().parse(r0, r38, r11, r47);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0089, code lost:
        if (r41 <= 2) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x05cd, code lost:
        if (r1 == null) goto L328;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x05cf, code lost:
        r45.setTimeZone(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x05d6, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x05d8, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x05d9, code lost:
        r4 = r6;
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x05ec, code lost:
        if (r4 != (r45.getLeastMaximum(10) + r7)) goto L334;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x05ee, code lost:
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x05f0, code lost:
        r45.set(10, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x05f7, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x0608, code lost:
        if (r37.formatData.ampmsNarrow == null) goto L352;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x060a, code lost:
        if (r41 < 5) goto L352;
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x0612, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) == false) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x0615, code lost:
        r7 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x061b, code lost:
        r7 = 5;
        r0 = matchString(r38, r15, 9, r37.formatData.ampms, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x0637, code lost:
        if (r0 <= 0) goto L355;
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x0639, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008d, code lost:
        if (r4 == 26) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x0640, code lost:
        if (r37.formatData.ampmsNarrow == null) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x0642, code lost:
        if (r41 >= r7) goto L347;
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x064a, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) == false) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x064c, code lost:
        r0 = matchString(r38, r15, 9, r37.formatData.ampmsNarrow, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x0660, code lost:
        if (r0 <= 0) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:358:0x0662, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x0664, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x0665, code lost:
        r5 = r6;
        r6 = r26;
        r9 = 4;
        r8 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x0671, code lost:
        r17 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x0679, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:364:0x067b, code lost:
        if (r41 != r9) goto L291;
     */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x067e, code lost:
        r7 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x0683, code lost:
        r7 = r4;
        r0 = matchString(r38, r15, 7, r37.formatData.weekdays, null, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x069e, code lost:
        if (r0 <= 0) goto L292;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x06a0, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0091, code lost:
        if (r4 == 19) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x06a7, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L313;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x06a9, code lost:
        if (r41 != r8) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x06ab, code lost:
        r0 = matchString(r38, r15, 7, r37.formatData.shortWeekdays, null, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x06be, code lost:
        if (r0 <= 0) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x06c0, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x06c7, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x06c9, code lost:
        if (r41 != r7) goto L298;
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x06cf, code lost:
        if (r37.formatData.shorterWeekdays == null) goto L298;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x06d1, code lost:
        r0 = matchString(r38, r15, 7, r37.formatData.shorterWeekdays, null, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x06e4, code lost:
        if (r0 <= 0) goto L298;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x06e6, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x06ed, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x06f0, code lost:
        if (r41 != 5) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x06f6, code lost:
        if (r37.formatData.narrowWeekdays == null) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0095, code lost:
        if (r4 == 25) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x06f8, code lost:
        r0 = matchString(r38, r15, 7, r37.formatData.narrowWeekdays, null, r45);
        r17 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x070b, code lost:
        if (r0 <= 0) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x070d, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x070e, code lost:
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:394:0x070f, code lost:
        r3 = r7;
        r11 = r9;
        r9 = r6;
        r0 = countDigits(r38, r15, r11.getIndex());
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x0722, code lost:
        if (r0 >= 3) goto L364;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x0724, code lost:
        if (r0 >= 3) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x0726, code lost:
        r9 = r9 * 10;
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x072c, code lost:
        r1 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x072d, code lost:
        if (r0 <= 3) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x072f, code lost:
        r3 = r1 * 10;
        r0 = r0 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0734, code lost:
        r9 = r9 / r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x0735, code lost:
        r45.set(14, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x0740, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0741, code lost:
        r11 = r9;
        r9 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x0754, code lost:
        if (r9 != (r45.getMaximum(11) + r7)) goto L372;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x0756, code lost:
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x0758, code lost:
        r45.set(11, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x075f, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0098, code lost:
        if (r4 == 1) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0760, code lost:
        r3 = r7;
        r11 = r9;
        r31 = r10;
        r4 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0770, code lost:
        if (r41 <= 2) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x0772, code lost:
        if (r18 == null) goto L391;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x077a, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) == false) goto L391;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x077c, code lost:
        r1 = r3;
        r36 = r4;
        r0 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x0788, code lost:
        if (r37.formatData.leapMonthPatterns == null) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x0790, code lost:
        if (r37.formatData.leapMonthPatterns.length < 7) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x0792, code lost:
        r0 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0794, code lost:
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0795, code lost:
        r7 = r0;
        r19 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x07a0, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L418;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x07a2, code lost:
        if (r41 != 4) goto L399;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x07a5, code lost:
        r9 = r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x07ac, code lost:
        if (r31 != 2) goto L428;
     */
    /* JADX WARN: Code restructure failed: missing block: B:429:0x07ae, code lost:
        r1 = r37.formatData.months;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009c, code lost:
        if (r4 == 18) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x07b4, code lost:
        if (r7 == 0) goto L427;
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x07b6, code lost:
        r17 = r37.formatData.leapMonthPatterns[0];
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x07c1, code lost:
        r17 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x07c4, code lost:
        r9 = r31;
        r0 = matchString(r38, r15, 2, r1, r17, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x07db, code lost:
        r9 = r31;
        r4 = r37.formatData.standaloneMonths;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x07e3, code lost:
        if (r7 == 0) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x07e5, code lost:
        r5 = r37.formatData.leapMonthPatterns[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x07ed, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x07f0, code lost:
        r0 = matchString(r38, r15, 2, r4, r5, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x07fb, code lost:
        r19 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x07fd, code lost:
        if (r19 <= 0) goto L400;
     */
    /* JADX WARN: Code restructure failed: missing block: B:441:0x07ff, code lost:
        return r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:443:0x0806, code lost:
        if (getBooleanAttribute(com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) goto L404;
     */
    /* JADX WARN: Code restructure failed: missing block: B:444:0x0808, code lost:
        if (r41 != 3) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x080b, code lost:
        return r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:448:0x080d, code lost:
        if (r9 != 2) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:449:0x080f, code lost:
        r4 = r37.formatData.shortMonths;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a0, code lost:
        if (r4 == 30) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:450:0x0814, code lost:
        if (r7 == 0) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:451:0x0816, code lost:
        r5 = r37.formatData.leapMonthPatterns[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:452:0x081f, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:454:0x082e, code lost:
        r4 = r37.formatData.standaloneShortMonths;
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x0833, code lost:
        if (r7 == 0) goto L417;
     */
    /* JADX WARN: Code restructure failed: missing block: B:456:0x0835, code lost:
        r5 = r37.formatData.leapMonthPatterns[4];
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x083e, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:459:0x084c, code lost:
        return matchString(r38, r15, 2, r4, r5, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a2, code lost:
        if (r4 != 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x084d, code lost:
        r1 = r3;
        r36 = r4;
        r0 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:461:0x0853, code lost:
        r8 = r36;
        r45.set(r0, r8 - 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:462:0x0866, code lost:
        if (r45.getType().equals("hebrew") == false) goto L389;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x0868, code lost:
        if (r8 < 6) goto L389;
     */
    /* JADX WARN: Code restructure failed: missing block: B:465:0x086e, code lost:
        if (r45.isSet((int) r1) == false) goto L388;
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x0878, code lost:
        if (com.ibm.icu.util.HebrewCalendar.isLeapYear(r45.get((int) r1)) != false) goto L389;
     */
    /* JADX WARN: Code restructure failed: missing block: B:468:0x087a, code lost:
        r45.set(r0, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x087e, code lost:
        com.ibm.icu.text.SimpleDateFormat.DelayedHebrewMonthCheck = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a4, code lost:
        if (r16 != false) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:471:0x0884, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:472:0x0885, code lost:
        r1 = r7;
        r11 = r9;
        r35 = r26;
        r8 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:473:0x0893, code lost:
        if (r37.override == null) goto L452;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x089d, code lost:
        if (r37.override.compareTo("hebr") == 0) goto L441;
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x08a7, code lost:
        if (r37.override.indexOf("y=hebr") < 0) goto L452;
     */
    /* JADX WARN: Code restructure failed: missing block: B:479:0x08ab, code lost:
        if (r8 >= 1000) goto L452;
     */
    /* JADX WARN: Code restructure failed: missing block: B:480:0x08ad, code lost:
        r6 = r8 + 5000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:481:0x08b0, code lost:
        if (r41 != 2) goto L465;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x08ba, code lost:
        if (countDigits(r38, r15, r11.getIndex()) != 2) goto L465;
     */
    /* JADX WARN: Code restructure failed: missing block: B:485:0x08c0, code lost:
        if (r45.haveDefaultCentury() == false) goto L465;
     */
    /* JADX WARN: Code restructure failed: missing block: B:486:0x08c2, code lost:
        r7 = 100;
        r2 = getDefaultCenturyStartYear() % 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:487:0x08c9, code lost:
        if (r8 != r2) goto L464;
     */
    /* JADX WARN: Code restructure failed: missing block: B:488:0x08cb, code lost:
        r3 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:489:0x08cd, code lost:
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a8, code lost:
        if (r4 == 27) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:490:0x08ce, code lost:
        r44[0] = r3;
        r3 = (getDefaultCenturyStartYear() / 100) * 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:491:0x08d7, code lost:
        if (r8 >= r2) goto L462;
     */
    /* JADX WARN: Code restructure failed: missing block: B:493:0x08da, code lost:
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:494:0x08db, code lost:
        r6 = r8 + (r3 + r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:495:0x08df, code lost:
        r6 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:496:0x08e0, code lost:
        r45.set(r35, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:497:0x08e7, code lost:
        if (com.ibm.icu.text.SimpleDateFormat.DelayedHebrewMonthCheck == false) goto L450;
     */
    /* JADX WARN: Code restructure failed: missing block: B:499:0x08ed, code lost:
        if (com.ibm.icu.util.HebrewCalendar.isLeapYear(r6) != false) goto L449;
     */
    /* JADX WARN: Code restructure failed: missing block: B:500:0x08ef, code lost:
        r45.add(2, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:501:0x08f2, code lost:
        com.ibm.icu.text.SimpleDateFormat.DelayedHebrewMonthCheck = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:503:0x08f9, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:504:0x08fa, code lost:
        r11 = r9;
        r8 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:505:0x0905, code lost:
        if (r16 == false) goto L470;
     */
    /* JADX WARN: Code restructure failed: missing block: B:506:0x0907, code lost:
        r45.set(0, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:507:0x090e, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:509:0x0912, code lost:
        if (r41 != 5) goto L477;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ac, code lost:
        if (r4 == 28) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:510:0x0914, code lost:
        r0 = matchString(r38, r15, 0, r37.formatData.narrowEras, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:512:0x0928, code lost:
        if (r41 != 4) goto L480;
     */
    /* JADX WARN: Code restructure failed: missing block: B:513:0x092a, code lost:
        r0 = matchString(r38, r15, 0, r37.formatData.eraNames, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:514:0x093c, code lost:
        r0 = matchString(r38, r15, 0, r37.formatData.eras, null, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x094e, code lost:
        if (r0 != (~r15)) goto L476;
     */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x0950, code lost:
        return com.ibm.icu.text.SimpleDateFormat.ISOSpecialEra;
     */
    /* JADX WARN: Code restructure failed: missing block: B:519:0x0953, code lost:
        r2 = parseInt(r38, r41, r11, r43, r27);
     */
    /* JADX WARN: Code restructure failed: missing block: B:520:0x0968, code lost:
        r2 = parseInt(r38, r11, r43, r27);
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x0970, code lost:
        if (r2 == null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:523:0x0974, code lost:
        if (r9 == 34) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x0976, code lost:
        r45.set(r7, r2.intValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0980, code lost:
        r45.setRelatedYear(r2.intValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:527:0x098d, code lost:
        return r11.getIndex();
     */
    /* JADX WARN: Code restructure failed: missing block: B:529:0x0991, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b0, code lost:
        if (r4 != 8) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:536:?, code lost:
        return matchString(r38, r15, 2, r4, r5, r45);
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:?, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00b3, code lost:
        r15 = r2;
        r10 = r4;
        r9 = r2;
        r26 = r7;
        r5 = r1;
        r7 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00be, code lost:
        if (r46 == null) goto L512;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c0, code lost:
        if (r4 == 2) goto L504;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c4, code lost:
        if (r4 != 26) goto L486;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c7, code lost:
        r23 = false;
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cb, code lost:
        r1 = r46.parse(r38, r2);
        r23 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d1, code lost:
        if (r1 == null) goto L511;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d7, code lost:
        if (r2.getIndex() <= r2) goto L511;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00de, code lost:
        if ((r1[0] instanceof java.lang.Number) == false) goto L511;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00e0, code lost:
        r17 = (java.lang.Number) r1[0];
        r0 = 1;
        r45.set(22, 1);
        r23 = true;
        r18 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00f2, code lost:
        r0 = 1;
        r2.setIndex(r2);
        r45.set(22, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ff, code lost:
        r23 = false;
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0102, code lost:
        if (r23 != false) goto L503;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0104, code lost:
        if (r42 == false) goto L502;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x010c, code lost:
        if ((r2 + r41) <= r38.length()) goto L493;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x010f, code lost:
        return ~r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0110, code lost:
        r26 = r7;
        r7 = r0;
        r15 = r2;
        r10 = r4;
        r9 = r2;
        r5 = r1;
        r0 = parseInt(r38, r41, r2, r43, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x012b, code lost:
        r15 = r2;
        r10 = r4;
        r9 = r2;
        r26 = r7;
        r7 = r0;
        r5 = r1;
        r0 = parseInt(r38, r9, r43, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0139, code lost:
        if (r0 != null) goto L499;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x013f, code lost:
        if (allowNumericFallback(r10) != false) goto L499;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0142, code lost:
        return ~r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0143, code lost:
        r15 = r2;
        r10 = r4;
        r9 = r2;
        r26 = r7;
        r5 = r1;
        r7 = r0;
        r0 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x014d, code lost:
        if (r0 == null) goto L501;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x014f, code lost:
        r6 = r0.intValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0153, code lost:
        r18 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0155, code lost:
        r4 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0158, code lost:
        switch(r10) {
            case 0: goto L466;
            case 1: goto L435;
            case 2: goto L374;
            case 3: goto L52;
            case 4: goto L369;
            case 5: goto L52;
            case 6: goto L52;
            case 7: goto L52;
            case 8: goto L357;
            case 9: goto L356;
            case 10: goto L52;
            case 11: goto L52;
            case 12: goto L52;
            case 13: goto L52;
            case 14: goto L336;
            case 15: goto L331;
            case 16: goto L52;
            case 17: goto L321;
            case 18: goto L435;
            case 19: goto L282;
            case 20: goto L52;
            case 21: goto L52;
            case 22: goto L52;
            case 23: goto L270;
            case 24: goto L260;
            case 25: goto L229;
            case 26: goto L374;
            case 27: goto L210;
            case 28: goto L191;
            case 29: goto L179;
            case 30: goto L161;
            case 31: goto L151;
            case 32: goto L138;
            case 33: goto L125;
            case 34: goto L52;
            case 35: goto L100;
            case 36: goto L79;
            case 37: goto L69;
            default: goto L52;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x015b, code lost:
        r27 = r5;
        r11 = r9;
        r9 = r10;
        r7 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0163, code lost:
        if (r42 == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x016b, code lost:
        if ((r15 + r41) <= r38.length()) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x016e, code lost:
        return -r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x016f, code lost:
        r0 = new java.util.ArrayList<>(3);
        r0.add(r37.formatData.getTimeSeparatorString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x018a, code lost:
        if (r37.formatData.getTimeSeparatorString().equals(android.provider.SettingsStringUtil.DELIMITER) != false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x018c, code lost:
        r0.add(android.provider.SettingsStringUtil.DELIMITER);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r1v31 */
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal, MessageFormat numericLeapMonthFormatter, Output<TimeZoneFormat.TimeType> tzTimeType, Output<DayPeriodRules.DayPeriod> dayPeriod) {
        Number number;
        int value = 0;
        ParsePosition pos = new ParsePosition(0);
        int patternCharIndex = getIndexFromChar(ch);
        if (patternCharIndex == -1) {
            return ~start;
        }
        int start2 = start;
        NumberFormat currentNumberFormat = getNumberFormat(ch);
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
        if (numericLeapMonthFormatter != null) {
            number = null;
            numericLeapMonthFormatter.setFormatByArgumentIndex(0, currentNumberFormat);
        } else {
            number = null;
        }
        boolean isChineseCalendar = cal.getType().equals("chinese") || cal.getType().equals("dangi");
        while (true) {
            boolean isChineseCalendar2 = isChineseCalendar;
            if (start2 >= text.length()) {
                return ~start2;
            }
            int c = UTF16.charAt(text, start2);
            if (!UCharacter.isUWhiteSpace(c) || !PatternProps.isWhiteSpace(c)) {
                break;
            }
            start2 += UTF16.getCharCount(c);
            isChineseCalendar = isChineseCalendar2;
        }
    }

    private boolean allowNumericFallback(int patternCharIndex) {
        if (patternCharIndex == 26 || patternCharIndex == 19 || patternCharIndex == 25 || patternCharIndex == 30 || patternCharIndex == 27 || patternCharIndex == 28) {
            return true;
        }
        return false;
    }

    private Number parseInt(String text, ParsePosition pos, boolean allowNegative, NumberFormat fmt) {
        return parseInt(text, -1, pos, allowNegative, fmt);
    }

    private Number parseInt(String text, int maxDigits, ParsePosition pos, boolean allowNegative, NumberFormat fmt) {
        Number number;
        int nDigits;
        int oldPos = pos.getIndex();
        if (allowNegative) {
            number = fmt.parse(text, pos);
        } else if (fmt instanceof DecimalFormat) {
            String oldPrefix = ((DecimalFormat) fmt).getNegativePrefix();
            ((DecimalFormat) fmt).setNegativePrefix(SUPPRESS_NEGATIVE_PREFIX);
            number = fmt.parse(text, pos);
            ((DecimalFormat) fmt).setNegativePrefix(oldPrefix);
        } else {
            boolean dateNumberFormat = fmt instanceof DateNumberFormat;
            if (dateNumberFormat) {
                ((DateNumberFormat) fmt).setParsePositiveOnly(true);
            }
            number = fmt.parse(text, pos);
            if (dateNumberFormat) {
                ((DateNumberFormat) fmt).setParsePositiveOnly(false);
            }
        }
        if (maxDigits > 0 && (nDigits = pos.getIndex() - oldPos) > maxDigits) {
            double val = number.doubleValue();
            for (int nDigits2 = nDigits - maxDigits; nDigits2 > 0; nDigits2--) {
                val /= 10.0d;
            }
            pos.setIndex(oldPos + maxDigits);
            return Integer.valueOf((int) val);
        }
        return number;
    }

    private static int countDigits(String text, int start, int end) {
        int numDigits = 0;
        int numDigits2 = start;
        while (numDigits2 < end) {
            int cp = text.codePointAt(numDigits2);
            if (UCharacter.isDigit(cp)) {
                numDigits++;
            }
            numDigits2 += UCharacter.charCount(cp);
        }
        return numDigits;
    }

    private String translatePattern(String pat, String from, String to) {
        int ci;
        StringBuilder result = new StringBuilder();
        boolean inQuote = false;
        for (int i = 0; i < pat.length(); i++) {
            char c = pat.charAt(i);
            if (inQuote) {
                if (c == '\'') {
                    inQuote = false;
                }
            } else if (c == '\'') {
                inQuote = true;
            } else if (isSyntaxChar(c) && (ci = from.indexOf(c)) != -1) {
                c = to.charAt(ci);
            }
            result.append(c);
        }
        if (inQuote) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return result.toString();
    }

    public String toPattern() {
        return this.pattern;
    }

    public String toLocalizedPattern() {
        return translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB", this.formatData.localPatternChars);
    }

    public void applyPattern(String pat) {
        this.pattern = pat;
        parsePattern();
        setLocale(null, null);
        this.patternItems = null;
    }

    public void applyLocalizedPattern(String pat) {
        this.pattern = translatePattern(pat, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
        setLocale(null, null);
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols) this.formatData.clone();
    }

    public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
        this.formatData = (DateFormatSymbols) newFormatSymbols.clone();
    }

    protected DateFormatSymbols getSymbols() {
        return this.formatData;
    }

    public TimeZoneFormat getTimeZoneFormat() {
        return tzFormat().m209freeze();
    }

    public void setTimeZoneFormat(TimeZoneFormat tzfmt) {
        if (tzfmt.isFrozen()) {
            this.tzFormat = tzfmt;
        } else {
            this.tzFormat = tzfmt.m208cloneAsThawed().m209freeze();
        }
    }

    @Override // com.ibm.icu.text.DateFormat, java.text.Format
    public Object clone() {
        SimpleDateFormat other = (SimpleDateFormat) super.clone();
        other.formatData = (DateFormatSymbols) this.formatData.clone();
        if (this.decimalBuf != null) {
            other.decimalBuf = new char[10];
        }
        return other;
    }

    @Override // com.ibm.icu.text.DateFormat
    public int hashCode() {
        return this.pattern.hashCode();
    }

    @Override // com.ibm.icu.text.DateFormat
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            SimpleDateFormat that = (SimpleDateFormat) obj;
            return this.pattern.equals(that.pattern) && this.formatData.equals(that.formatData);
        }
        return false;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        initializeTimeZoneFormat(false);
        stream.defaultWriteObject();
        stream.writeInt(getContext(DisplayContext.Type.CAPITALIZATION).value());
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int capitalizationSettingValue = this.serialVersionOnStream > 1 ? stream.readInt() : -1;
        if (this.serialVersionOnStream < 1) {
            this.defaultCenturyBase = System.currentTimeMillis();
        } else {
            parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 2;
        this.locale = getLocale(ULocale.VALID_LOCALE);
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        initLocalZeroPaddingNumberFormat();
        setContext(DisplayContext.CAPITALIZATION_NONE);
        if (capitalizationSettingValue >= 0) {
            DisplayContext[] values = DisplayContext.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                DisplayContext context = values[i];
                if (context.value() != capitalizationSettingValue) {
                    i++;
                } else {
                    setContext(context);
                    break;
                }
            }
        }
        if (!getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, false);
        }
        parsePattern();
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        Calendar cal = this.calendar;
        if (obj instanceof Calendar) {
            cal = (Calendar) obj;
        } else if (obj instanceof Date) {
            this.calendar.setTime((Date) obj);
        } else if (obj instanceof Number) {
            this.calendar.setTimeInMillis(((Number) obj).longValue());
        } else {
            throw new IllegalArgumentException("Cannot format given Object as a Date");
        }
        StringBuffer toAppendTo = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        List<FieldPosition> attributes = new ArrayList<>();
        format(cal, getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, attributes);
        AttributedString as = new AttributedString(toAppendTo.toString());
        for (int i = 0; i < attributes.size(); i++) {
            FieldPosition fp = attributes.get(i);
            Format.Field attribute = fp.getFieldAttribute();
            as.addAttribute(attribute, attribute, fp.getBeginIndex(), fp.getEndIndex());
        }
        return as.getIterator();
    }

    ULocale getLocale() {
        return this.locale;
    }

    boolean isFieldUnitIgnored(int field) {
        return isFieldUnitIgnored(this.pattern, field);
    }

    static boolean isFieldUnitIgnored(String pattern, int field) {
        int fieldLevel = CALENDAR_FIELD_TO_LEVEL[field];
        char prevCh = 0;
        int count = 0;
        boolean inQuote = false;
        int i = 0;
        while (i < pattern.length()) {
            char ch = pattern.charAt(i);
            if (ch != prevCh && count > 0) {
                int level = getLevelFromChar(prevCh);
                if (fieldLevel <= level) {
                    return false;
                }
                count = 0;
            }
            if (ch == '\'') {
                if (i + 1 < pattern.length() && pattern.charAt(i + 1) == '\'') {
                    i++;
                } else {
                    inQuote = !inQuote;
                }
            } else if (!inQuote && isSyntaxChar(ch)) {
                prevCh = ch;
                count++;
            }
            i++;
        }
        if (count > 0) {
            int level2 = getLevelFromChar(prevCh);
            if (fieldLevel <= level2) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) throws IllegalArgumentException {
        IllegalArgumentException e;
        int diffEnd;
        int i;
        int highestLevel;
        SimpleDateFormat simpleDateFormat = this;
        if (!fromCalendar.isEquivalentTo(toCalendar)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        Object[] items = getPatternItems();
        int diffBegin = -1;
        int diffEnd2 = -1;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            try {
                if (i3 < items.length) {
                    if (!simpleDateFormat.diffCalFieldValue(fromCalendar, toCalendar, items, i3)) {
                        i3++;
                    } else {
                        diffBegin = i3;
                        break;
                    }
                } else {
                    break;
                }
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException(e2.toString());
            }
        }
        if (diffBegin == -1) {
            return simpleDateFormat.format(fromCalendar, appendTo, pos);
        }
        int i4 = items.length - 1;
        while (true) {
            if (i4 >= diffBegin) {
                if (simpleDateFormat.diffCalFieldValue(fromCalendar, toCalendar, items, i4)) {
                    diffEnd2 = i4;
                    break;
                } else {
                    i4--;
                }
            } else {
                break;
            }
        }
        if (diffBegin == 0 && diffEnd2 == items.length - 1) {
            simpleDateFormat.format(fromCalendar, appendTo, pos);
            appendTo.append(" \u2013 ");
            simpleDateFormat.format(toCalendar, appendTo, pos);
            return appendTo;
        }
        int highestLevel2 = 1000;
        for (int highestLevel3 = diffBegin; highestLevel3 <= diffEnd2; highestLevel3++) {
            if (!(items[highestLevel3] instanceof String)) {
                char ch = ((PatternItem) items[highestLevel3]).type;
                int patternCharIndex = getIndexFromChar(ch);
                if (patternCharIndex == -1) {
                    throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + simpleDateFormat.pattern + '\"');
                } else if (patternCharIndex < highestLevel2) {
                    highestLevel2 = patternCharIndex;
                }
            }
        }
        int i5 = 0;
        while (true) {
            if (i5 >= diffBegin) {
                break;
            }
            try {
                if (!simpleDateFormat.lowerLevel(items, i5, highestLevel2)) {
                    i5++;
                } else {
                    diffBegin = i5;
                    break;
                }
            } catch (IllegalArgumentException e3) {
                e = e3;
                throw new IllegalArgumentException(e.toString());
            }
        }
        int diffBegin2 = diffBegin;
        try {
            int i6 = items.length - 1;
            while (true) {
                if (i6 > diffEnd2) {
                    try {
                        if (simpleDateFormat.lowerLevel(items, i6, highestLevel2)) {
                            break;
                        }
                        i6--;
                    } catch (IllegalArgumentException e4) {
                        e = e4;
                        throw new IllegalArgumentException(e.toString());
                    }
                } else {
                    i6 = diffEnd2;
                    break;
                }
            }
            if (diffBegin2 == 0 && i6 == items.length - 1) {
                simpleDateFormat.format(fromCalendar, appendTo, pos);
                appendTo.append(" \u2013 ");
                simpleDateFormat.format(toCalendar, appendTo, pos);
                return appendTo;
            }
            pos.setBeginIndex(0);
            pos.setEndIndex(0);
            DisplayContext capSetting = simpleDateFormat.getContext(DisplayContext.Type.CAPITALIZATION);
            while (true) {
                int i7 = i2;
                if (i7 > i6) {
                    break;
                }
                if (items[i7] instanceof String) {
                    appendTo.append((String) items[i7]);
                    diffEnd = i6;
                    i = i7;
                    highestLevel = highestLevel2;
                } else {
                    PatternItem item = (PatternItem) items[i7];
                    if (simpleDateFormat.useFastFormat) {
                        diffEnd = i6;
                        i = i7;
                        highestLevel = highestLevel2;
                        subFormat(appendTo, item.type, item.length, appendTo.length(), i7, capSetting, pos, fromCalendar);
                    } else {
                        diffEnd = i6;
                        i = i7;
                        highestLevel = highestLevel2;
                        appendTo.append(subFormat(item.type, item.length, appendTo.length(), i, capSetting, pos, fromCalendar));
                    }
                }
                i2 = i + 1;
                i6 = diffEnd;
                highestLevel2 = highestLevel;
            }
            appendTo.append(" \u2013 ");
            int i8 = diffBegin2;
            while (i8 < items.length) {
                if (items[i8] instanceof String) {
                    appendTo.append((String) items[i8]);
                } else {
                    PatternItem item2 = (PatternItem) items[i8];
                    if (simpleDateFormat.useFastFormat) {
                        subFormat(appendTo, item2.type, item2.length, appendTo.length(), i8, capSetting, pos, toCalendar);
                    } else {
                        appendTo.append(subFormat(item2.type, item2.length, appendTo.length(), i8, capSetting, pos, toCalendar));
                    }
                }
                i8++;
                simpleDateFormat = this;
            }
            return appendTo;
        } catch (IllegalArgumentException e5) {
            e = e5;
            throw new IllegalArgumentException(e.toString());
        }
    }

    private boolean diffCalFieldValue(Calendar fromCalendar, Calendar toCalendar, Object[] items, int i) throws IllegalArgumentException {
        if (items[i] instanceof String) {
            return false;
        }
        PatternItem item = (PatternItem) items[i];
        char ch = item.type;
        int patternCharIndex = getIndexFromChar(ch);
        if (patternCharIndex == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '\"');
        }
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
        if (field >= 0) {
            int value = fromCalendar.get(field);
            int value_2 = toCalendar.get(field);
            if (value != value_2) {
                return true;
            }
        }
        return false;
    }

    private boolean lowerLevel(Object[] items, int i, int level) throws IllegalArgumentException {
        if (items[i] instanceof String) {
            return false;
        }
        PatternItem item = (PatternItem) items[i];
        char ch = item.type;
        int patternCharIndex = getLevelFromChar(ch);
        if (patternCharIndex != -1) {
            return patternCharIndex >= level;
        }
        throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '\"');
    }

    public void setNumberFormat(String fields, NumberFormat overrideNF) {
        overrideNF.setGroupingUsed(false);
        String nsName = "$" + UUID.randomUUID().toString();
        if (this.numberFormatters == null) {
            this.numberFormatters = new HashMap<>();
        }
        if (this.overrideMap == null) {
            this.overrideMap = new HashMap<>();
        }
        for (int i = 0; i < fields.length(); i++) {
            char field = fields.charAt(i);
            if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(field) == -1) {
                throw new IllegalArgumentException("Illegal field character '" + field + "' in setNumberFormat.");
            }
            this.overrideMap.put(Character.valueOf(field), nsName);
            this.numberFormatters.put(nsName, overrideNF);
        }
        this.useLocalZeroPaddingNumberFormat = false;
    }

    public NumberFormat getNumberFormat(char field) {
        Character ovrField = Character.valueOf(field);
        if (this.overrideMap != null && this.overrideMap.containsKey(ovrField)) {
            String nsName = this.overrideMap.get(ovrField).toString();
            NumberFormat nf = this.numberFormatters.get(nsName);
            return nf;
        }
        return this.numberFormat;
    }

    private void initNumberFormatters(ULocale loc) {
        this.numberFormatters = new HashMap<>();
        this.overrideMap = new HashMap<>();
        processOverrideString(loc, this.override);
    }

    private void processOverrideString(ULocale loc, String str) {
        int end;
        String nsName;
        boolean fullOverride;
        if (str == null || str.length() == 0) {
            return;
        }
        int start = 0;
        boolean moreToProcess = true;
        while (moreToProcess) {
            int delimiterPosition = str.indexOf(";", start);
            if (delimiterPosition == -1) {
                moreToProcess = false;
                end = str.length();
            } else {
                end = delimiterPosition;
            }
            String currentString = str.substring(start, end);
            int equalSignPosition = currentString.indexOf("=");
            if (equalSignPosition == -1) {
                nsName = currentString;
                fullOverride = true;
            } else {
                nsName = currentString.substring(equalSignPosition + 1);
                Character ovrField = Character.valueOf(currentString.charAt(0));
                this.overrideMap.put(ovrField, nsName);
                fullOverride = false;
            }
            ULocale ovrLoc = new ULocale(loc.getBaseName() + "@numbers=" + nsName);
            NumberFormat nf = NumberFormat.createInstance(ovrLoc, 0);
            nf.setGroupingUsed(false);
            if (fullOverride) {
                setNumberFormat(nf);
            } else {
                this.useLocalZeroPaddingNumberFormat = false;
            }
            if (!fullOverride && !this.numberFormatters.containsKey(nsName)) {
                this.numberFormatters.put(nsName, nf);
            }
            start = delimiterPosition + 1;
        }
    }

    private void parsePattern() {
        this.hasMinute = false;
        this.hasSecond = false;
        boolean inQuote = false;
        for (int i = 0; i < this.pattern.length(); i++) {
            char ch = this.pattern.charAt(i);
            if (ch == '\'') {
                inQuote = !inQuote;
            }
            if (!inQuote) {
                if (ch == 'm') {
                    this.hasMinute = true;
                }
                if (ch == 's') {
                    this.hasSecond = true;
                }
            }
        }
    }
}
