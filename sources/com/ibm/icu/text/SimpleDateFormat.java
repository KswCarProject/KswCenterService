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
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
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

public class SimpleDateFormat extends DateFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] CALENDAR_FIELD_TO_LEVEL = {0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
    static final UnicodeSet DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();
    private static final int DECIMAL_BUF_SIZE = 10;
    static boolean DelayedHebrewMonthCheck = false;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int ISOSpecialEra = -32000;
    private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
    private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
    private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE = new SimpleCache();
    private static final boolean[] PATTERN_CHAR_IS_SYNTAX = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    private static final int[] PATTERN_CHAR_TO_INDEX = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 36, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, 35, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, 34, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1};
    private static final int[] PATTERN_CHAR_TO_LEVEL = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, 10, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1};
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = {0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2};
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = {DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR_WOY, DateFormat.Field.DOW_LOCAL, DateFormat.Field.EXTENDED_YEAR, DateFormat.Field.JULIAN_DAY, DateFormat.Field.MILLISECONDS_IN_DAY, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.MONTH, DateFormat.Field.QUARTER, DateFormat.Field.QUARTER, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.RELATED_YEAR, DateFormat.Field.AM_PM_MIDNIGHT_NOON, DateFormat.Field.FLEXIBLE_DAY_PERIOD, DateFormat.Field.TIME_SEPARATOR};
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    private static final String SUPPRESS_NEGATIVE_PREFIX = "꬀";
    private static ULocale cachedDefaultLocale = null;
    private static String cachedDefaultPattern = null;
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

    private enum ContextValue {
        UNKNOWN,
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
        CAPITALIZATION_FOR_UI_LIST_OR_MENU,
        CAPITALIZATION_FOR_STANDALONE
    }

    private static int getLevelFromChar(char ch) {
        if (ch < PATTERN_CHAR_TO_LEVEL.length) {
            return PATTERN_CHAR_TO_LEVEL[ch & 255];
        }
        return -1;
    }

    private static boolean isSyntaxChar(char ch) {
        if (ch < PATTERN_CHAR_IS_SYNTAX.length) {
            return PATTERN_CHAR_IS_SYNTAX[ch & 255];
        }
        return false;
    }

    public SimpleDateFormat() {
        this(getDefaultPattern(), (DateFormatSymbols) null, (Calendar) null, (NumberFormat) null, (ULocale) null, true, (String) null);
    }

    public SimpleDateFormat(String pattern2) {
        this(pattern2, (DateFormatSymbols) null, (Calendar) null, (NumberFormat) null, (ULocale) null, true, (String) null);
    }

    public SimpleDateFormat(String pattern2, Locale loc) {
        this(pattern2, (DateFormatSymbols) null, (Calendar) null, (NumberFormat) null, ULocale.forLocale(loc), true, (String) null);
    }

    public SimpleDateFormat(String pattern2, ULocale loc) {
        this(pattern2, (DateFormatSymbols) null, (Calendar) null, (NumberFormat) null, loc, true, (String) null);
    }

    public SimpleDateFormat(String pattern2, String override2, ULocale loc) {
        this(pattern2, (DateFormatSymbols) null, (Calendar) null, (NumberFormat) null, loc, false, override2);
    }

    public SimpleDateFormat(String pattern2, DateFormatSymbols formatData2) {
        this(pattern2, (DateFormatSymbols) formatData2.clone(), (Calendar) null, (NumberFormat) null, (ULocale) null, true, (String) null);
    }

    @Deprecated
    public SimpleDateFormat(String pattern2, DateFormatSymbols formatData2, ULocale loc) {
        this(pattern2, (DateFormatSymbols) formatData2.clone(), (Calendar) null, (NumberFormat) null, loc, true, (String) null);
    }

    SimpleDateFormat(String pattern2, DateFormatSymbols formatData2, Calendar calendar, ULocale locale2, boolean useFastFormat2, String override2) {
        this(pattern2, (DateFormatSymbols) formatData2.clone(), (Calendar) calendar.clone(), (NumberFormat) null, locale2, useFastFormat2, override2);
    }

    private SimpleDateFormat(String pattern2, DateFormatSymbols formatData2, Calendar calendar, NumberFormat numberFormat, ULocale locale2, boolean useFastFormat2, String override2) {
        this.serialVersionOnStream = 2;
        this.capitalizationBrkIter = null;
        this.pattern = pattern2;
        this.formatData = formatData2;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = locale2;
        this.useFastFormat = useFastFormat2;
        this.override = override2;
        initialize();
    }

    @Deprecated
    public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfig) {
        String ostr = formatConfig.getOverrideString();
        return new SimpleDateFormat(formatConfig.getPatternString(), formatConfig.getDateFormatSymbols(), formatConfig.getCalendar(), (NumberFormat) null, formatConfig.getLocale(), ostr != null && ostr.length() > 0, formatConfig.getOverrideString());
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
                this.numberFormat = NumberFormat.getInstance((ULocale) this.locale);
            } else {
                this.numberFormat = new DateNumberFormat(this.locale, digitString, ns.getName());
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
            String[] strDigits = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
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
                this.tzFormat = this.tzFormat.cloneAsThawed();
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
        Calendar cal;
        synchronized (SimpleDateFormat.class) {
            ULocale defaultLocale = ULocale.getDefault(ULocale.Category.FORMAT);
            if (!defaultLocale.equals(cachedDefaultLocale)) {
                cachedDefaultLocale = defaultLocale;
                Calendar cal2 = Calendar.getInstance(cachedDefaultLocale);
                try {
                    ICUResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", cachedDefaultLocale);
                    ICUResourceBundle patternsRb = rb.findWithFallback("calendar/" + cal2.getType() + "/DateTimePatterns");
                    if (patternsRb == null) {
                        patternsRb = rb.findWithFallback("calendar/gregorian/DateTimePatterns");
                    }
                    if (patternsRb != null) {
                        if (patternsRb.getSize() >= 9) {
                            int defaultIndex = 8;
                            if (patternsRb.getSize() >= 13) {
                                defaultIndex = 8 + 4;
                            }
                            cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(patternsRb.getString(defaultIndex), 2, 2, new CharSequence[]{patternsRb.getString(3), patternsRb.getString(7)});
                        }
                    }
                    cachedDefaultPattern = FALLBACKPATTERN;
                } catch (MissingResourceException e) {
                    cachedDefaultPattern = FALLBACKPATTERN;
                }
            }
            cal = cachedDefaultPattern;
        }
        return cal;
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

    public void setContext(DisplayContext context) {
        super.setContext(context);
        if (this.capitalizationBrkIter != null) {
            return;
        }
        if (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }

    public StringBuffer format(Calendar cal, StringBuffer toAppendTo, FieldPosition pos) {
        TimeZone backupTZ = null;
        if (cal != this.calendar && !cal.getType().equals(this.calendar.getType())) {
            this.calendar.setTimeInMillis(cal.getTimeInMillis());
            backupTZ = this.calendar.getTimeZone();
            this.calendar.setTimeZone(cal.getTimeZone());
            cal = this.calendar;
        }
        StringBuffer result = format(cal, getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, (List<FieldPosition>) null);
        if (backupTZ != null) {
            this.calendar.setTimeZone(backupTZ);
        }
        return result;
    }

    private StringBuffer format(Calendar cal, DisplayContext capitalizationContext, StringBuffer toAppendTo, FieldPosition pos, List<FieldPosition> attributes) {
        int start;
        StringBuffer stringBuffer = toAppendTo;
        FieldPosition fieldPosition = pos;
        List<FieldPosition> list = attributes;
        int end = 0;
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        Object[] items = getPatternItems();
        while (true) {
            int i = end;
            if (i >= items.length) {
                return stringBuffer;
            }
            if (items[i] instanceof String) {
                stringBuffer.append((String) items[i]);
            } else {
                PatternItem item = (PatternItem) items[i];
                int start2 = 0;
                if (list != null) {
                    start2 = toAppendTo.length();
                }
                int start3 = start2;
                if (this.useFastFormat) {
                    start = start3;
                    subFormat(toAppendTo, item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, cal);
                } else {
                    start = start3;
                    stringBuffer.append(subFormat(item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, cal));
                }
                if (list != null) {
                    int end2 = toAppendTo.length();
                    if (end2 - start > 0) {
                        FieldPosition fp = new FieldPosition(patternCharToDateFormatField(item.type));
                        fp.setBeginIndex(start);
                        fp.setEndIndex(end2);
                        list.add(fp);
                    }
                }
            }
            end = i + 1;
            FieldPosition fieldPosition2 = pos;
        }
    }

    private static int getIndexFromChar(char ch) {
        if (ch < PATTERN_CHAR_TO_INDEX.length) {
            return PATTERN_CHAR_TO_INDEX[ch & 255];
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public DateFormat.Field patternCharToDateFormatField(char ch) {
        int patternCharIndex = getIndexFromChar(ch);
        if (patternCharIndex != -1) {
            return PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[patternCharIndex];
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String subFormat(char ch, int count, int beginOffset, FieldPosition pos, DateFormatSymbols fmtData, Calendar cal) throws IllegalArgumentException {
        return subFormat(ch, count, beginOffset, 0, DisplayContext.CAPITALIZATION_NONE, pos, cal);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String subFormat(char ch, int count, int beginOffset, int fieldNum, DisplayContext capitalizationContext, FieldPosition pos, Calendar cal) {
        StringBuffer buf = new StringBuffer();
        subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
        return buf.toString();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0321, code lost:
        r17 = r0;
        r0 = r20;
        r23 = r27;
        r1 = r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00ac, code lost:
        r22 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x04fa, code lost:
        r23 = r27;
        r19 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x0532, code lost:
        r17 = r0;
        r0 = r20;
        r23 = r27;
        r1 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x05a6, code lost:
        r19 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x05a8, code lost:
        r23 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x05b5, code lost:
        if (r12 != 5) goto L_0x05c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x05b7, code lost:
        safeAppend(r9.formatData.narrowWeekdays, r1, r10);
        r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x05c0, code lost:
        r0 = r20;
        r23 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x05c4, code lost:
        r22 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x05c8, code lost:
        if (r12 != 4) goto L_0x05d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x05ca, code lost:
        safeAppend(r9.formatData.weekdays, r1, r10);
        r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x05d5, code lost:
        if (r12 != 6) goto L_0x05e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x05db, code lost:
        if (r9.formatData.shorterWeekdays == null) goto L_0x05e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x05dd, code lost:
        safeAppend(r9.formatData.shorterWeekdays, r1, r10);
        r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x05e7, code lost:
        safeAppend(r9.formatData.shortWeekdays, r1, r10);
        r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:331:0x07b8, code lost:
        if (r9.override == null) goto L_0x07db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:333:0x07c2, code lost:
        if (r9.override.compareTo("hebr") == 0) goto L_0x07ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:335:0x07cc, code lost:
        if (r9.override.indexOf("y=hebr") < 0) goto L_0x07db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:337:0x07d0, code lost:
        if (r3 <= 5000) goto L_0x07db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:339:0x07d4, code lost:
        if (r3 >= HEBREW_CAL_CUR_MILLENIUM_END_YEAR) goto L_0x07db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:340:0x07d6, code lost:
        r19 = r3 - 5000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:341:0x07db, code lost:
        r19 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:342:0x07dd, code lost:
        if (r12 != 2) goto L_0x07f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:343:0x07df, code lost:
        r22 = r5;
        zeroPaddingNumber(r18, r42, r19, 2, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:344:0x07f3, code lost:
        r22 = r5;
        zeroPaddingNumber(r18, r42, r19, r44, Integer.MAX_VALUE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:352:0x0835, code lost:
        r1 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:357:0x085b, code lost:
        r1 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:358:0x085d, code lost:
        r0 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:359:0x085f, code lost:
        if (r46 != 0) goto L_0x08ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:0x0861, code lost:
        if (r14 == null) goto L_0x08ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:361:0x0863, code lost:
        r3 = r33;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:362:0x086d, code lost:
        if (com.ibm.icu.lang.UCharacter.isLowerCase(r10.codePointAt(r3)) == false) goto L_0x08c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:363:0x086f, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:364:0x0878, code lost:
        switch(r47) {
            case com.ibm.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE :com.ibm.icu.text.DisplayContext: goto L_0x089c;
            case com.ibm.icu.text.DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU :com.ibm.icu.text.DisplayContext: goto L_0x087e;
            case com.ibm.icu.text.DisplayContext.CAPITALIZATION_FOR_STANDALONE :com.ibm.icu.text.DisplayContext: goto L_0x087e;
            default: goto L_0x087b;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:365:0x087b, code lost:
        r39 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:367:0x0882, code lost:
        if (r9.formatData.capitalization == null) goto L_0x087b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:0x0884, code lost:
        r5 = r9.formatData.capitalization.get(r0);
        r39 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:369:0x0892, code lost:
        if (r14 != com.ibm.icu.text.DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU) goto L_0x0897;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:0x0894, code lost:
        r0 = r5[r22];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:371:0x0897, code lost:
        r0 = r5[1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:372:0x089a, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:373:0x089c, code lost:
        r39 = r0;
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:374:0x08a0, code lost:
        if (r4 == false) goto L_0x08c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:376:0x08a4, code lost:
        if (r9.capitalizationBrkIter != null) goto L_0x08ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:377:0x08a6, code lost:
        r9.capitalizationBrkIter = com.ibm.icu.text.BreakIterator.getSentenceInstance(r9.locale);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:378:0x08ae, code lost:
        r40 = r1;
        r10.replace(r3, r42.length(), com.ibm.icu.lang.UCharacter.toTitleCase(r9.locale, r10.substring(r3), r9.capitalizationBrkIter, 768));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:0x08c6, code lost:
        r40 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:0x08c9, code lost:
        r39 = r0;
        r40 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:0x08ce, code lost:
        r39 = r0;
        r40 = r1;
        r3 = r33;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:383:0x08dc, code lost:
        if (r48.getBeginIndex() != r48.getEndIndex()) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:385:0x08e6, code lost:
        if (r48.getField() != PATTERN_INDEX_TO_DATE_FORMAT_FIELD[r23]) goto L_0x08f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:0x08e8, code lost:
        r15.setBeginIndex(r13);
        r15.setEndIndex((r42.length() + r13) - r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:0x08fd, code lost:
        if (r48.getFieldAttribute() != PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[r23]) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:0x08ff, code lost:
        r15.setBeginIndex(r13);
        r15.setEndIndex((r42.length() + r13) - r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:392:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:267:0x069e  */
    /* JADX WARNING: Removed duplicated region for block: B:271:0x06a6  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0182  */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void subFormat(java.lang.StringBuffer r42, char r43, int r44, int r45, int r46, com.ibm.icu.text.DisplayContext r47, java.text.FieldPosition r48, com.ibm.icu.util.Calendar r49) {
        /*
            r41 = this;
            r9 = r41
            r10 = r42
            r11 = r43
            r12 = r44
            r13 = r45
            r14 = r47
            r15 = r48
            r8 = r49
            r16 = 2147483647(0x7fffffff, float:NaN)
            int r7 = r42.length()
            com.ibm.icu.util.TimeZone r6 = r49.getTimeZone()
            long r4 = r49.getTimeInMillis()
            r17 = 0
            int r3 = getIndexFromChar(r43)
            r1 = -1
            if (r3 != r1) goto L_0x0053
            r1 = 108(0x6c, float:1.51E-43)
            if (r11 != r1) goto L_0x002d
            return
        L_0x002d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r0 = "Illegal pattern character '"
            r2.append(r0)
            r2.append(r11)
            java.lang.String r0 = "' in \""
            r2.append(r0)
            java.lang.String r0 = r9.pattern
            r2.append(r0)
            r0 = 34
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x0053:
            int[] r0 = PATTERN_INDEX_TO_CALENDAR_FIELD
            r2 = r0[r3]
            r0 = 0
            if (r2 < 0) goto L_0x0069
            r1 = 34
            if (r3 == r1) goto L_0x0063
            int r1 = r8.get(r2)
            goto L_0x0067
        L_0x0063:
            int r1 = r49.getRelatedYear()
        L_0x0067:
            r0 = r1
            goto L_0x006a
        L_0x0069:
            r1 = r0
        L_0x006a:
            com.ibm.icu.text.NumberFormat r18 = r9.getNumberFormat(r11)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.OTHER
            r0 = 12
            r24 = r2
            r25 = r4
            r4 = 2
            r2 = 4
            r5 = 3
            switch(r3) {
                case 0: goto L_0x0806;
                case 1: goto L_0x07ad;
                case 2: goto L_0x066f;
                case 3: goto L_0x007c;
                case 4: goto L_0x0633;
                case 5: goto L_0x007c;
                case 6: goto L_0x007c;
                case 7: goto L_0x007c;
                case 8: goto L_0x05f1;
                case 9: goto L_0x05ac;
                case 10: goto L_0x007c;
                case 11: goto L_0x007c;
                case 12: goto L_0x007c;
                case 13: goto L_0x007c;
                case 14: goto L_0x057e;
                case 15: goto L_0x053c;
                case 16: goto L_0x007c;
                case 17: goto L_0x0507;
                case 18: goto L_0x07ad;
                case 19: goto L_0x04de;
                case 20: goto L_0x007c;
                case 21: goto L_0x007c;
                case 22: goto L_0x007c;
                case 23: goto L_0x04ac;
                case 24: goto L_0x047d;
                case 25: goto L_0x0422;
                case 26: goto L_0x066f;
                case 27: goto L_0x03e3;
                case 28: goto L_0x03a4;
                case 29: goto L_0x035a;
                case 30: goto L_0x032b;
                case 31: goto L_0x02f7;
                case 32: goto L_0x02a5;
                case 33: goto L_0x0248;
                case 34: goto L_0x007c;
                case 35: goto L_0x01c6;
                case 36: goto L_0x00b0;
                case 37: goto L_0x0099;
                default: goto L_0x007c;
            }
        L_0x007c:
            r19 = r1
            r23 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r22 = 0
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r19
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x085b
        L_0x0099:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String r0 = r0.getTimeSeparatorString()
            r10.append(r0)
            r19 = r1
            r23 = r3
            r33 = r7
            r11 = r8
            r7 = r25
        L_0x00ac:
            r22 = 0
            goto L_0x085b
        L_0x00b0:
            com.ibm.icu.util.ULocale r4 = r41.getLocale()
            com.ibm.icu.impl.DayPeriodRules r4 = com.ibm.icu.impl.DayPeriodRules.getInstance(r4)
            if (r4 != 0) goto L_0x00e8
            r2 = 97
            r0 = r41
            r5 = r1
            r1 = r42
            r11 = 1
            r27 = r3
            r3 = r44
            r30 = r4
            r28 = r25
            r4 = r45
            r31 = r5
            r5 = r46
            r32 = r6
            r6 = r47
            r33 = r7
            r7 = r48
            r11 = r8
            r8 = r49
            r0.subFormat(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x00df:
            r23 = r27
            r7 = r28
            r19 = r31
            r6 = r32
            goto L_0x00ac
        L_0x00e8:
            r31 = r1
            r27 = r3
            r30 = r4
            r32 = r6
            r33 = r7
            r11 = r8
            r28 = r25
            r1 = 11
            int r8 = r11.get(r1)
            r1 = 0
            r3 = 0
            boolean r4 = r9.hasMinute
            if (r4 == 0) goto L_0x0105
            int r1 = r11.get(r0)
        L_0x0105:
            r19 = r1
            boolean r1 = r9.hasSecond
            if (r1 == 0) goto L_0x0111
            r1 = 13
            int r3 = r11.get(r1)
        L_0x0111:
            r21 = r3
            if (r8 != 0) goto L_0x0124
            if (r19 != 0) goto L_0x0124
            if (r21 != 0) goto L_0x0124
            boolean r1 = r30.hasMidnight()
            if (r1 == 0) goto L_0x0124
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.MIDNIGHT
        L_0x0121:
            r7 = r30
            goto L_0x0139
        L_0x0124:
            if (r8 != r0) goto L_0x0133
            if (r19 != 0) goto L_0x0133
            if (r21 != 0) goto L_0x0133
            boolean r0 = r30.hasNoon()
            if (r0 == 0) goto L_0x0133
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.NOON
            goto L_0x0121
        L_0x0133:
            r7 = r30
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = r7.getDayPeriodForHour(r8)
        L_0x0139:
            r1 = 0
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r3 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.AM
            if (r0 == r3) goto L_0x0167
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r3 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.PM
            if (r0 == r3) goto L_0x0167
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r3 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.MIDNIGHT
            if (r0 == r3) goto L_0x0167
            int r3 = r0.ordinal()
            if (r12 > r5) goto L_0x0154
            com.ibm.icu.text.DateFormatSymbols r4 = r9.formatData
            java.lang.String[] r4 = r4.abbreviatedDayPeriods
            r1 = r4[r3]
            goto L_0x0167
        L_0x0154:
            if (r12 == r2) goto L_0x0161
            r4 = 5
            if (r12 <= r4) goto L_0x015a
            goto L_0x0161
        L_0x015a:
            com.ibm.icu.text.DateFormatSymbols r4 = r9.formatData
            java.lang.String[] r4 = r4.narrowDayPeriods
            r1 = r4[r3]
            goto L_0x0167
        L_0x0161:
            com.ibm.icu.text.DateFormatSymbols r4 = r9.formatData
            java.lang.String[] r4 = r4.wideDayPeriods
            r1 = r4[r3]
        L_0x0167:
            if (r1 != 0) goto L_0x0195
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r3 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.MIDNIGHT
            if (r0 == r3) goto L_0x0171
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r3 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.NOON
            if (r0 != r3) goto L_0x0195
        L_0x0171:
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = r7.getDayPeriodForHour(r8)
            int r3 = r0.ordinal()
            if (r12 > r5) goto L_0x0182
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.abbreviatedDayPeriods
            r1 = r2[r3]
            goto L_0x0195
        L_0x0182:
            if (r12 == r2) goto L_0x018f
            r2 = 5
            if (r12 <= r2) goto L_0x0188
            goto L_0x018f
        L_0x0188:
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.narrowDayPeriods
            r1 = r2[r3]
            goto L_0x0195
        L_0x018f:
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.wideDayPeriods
            r1 = r2[r3]
        L_0x0195:
            r6 = r0
            r5 = r1
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.AM
            if (r6 == r0) goto L_0x01a7
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r0 = com.ibm.icu.impl.DayPeriodRules.DayPeriod.PM
            if (r6 == r0) goto L_0x01a7
            if (r5 != 0) goto L_0x01a2
            goto L_0x01a7
        L_0x01a2:
            r10.append(r5)
            goto L_0x00df
        L_0x01a7:
            r2 = 97
            r0 = r41
            r1 = r42
            r3 = r44
            r4 = r45
            r22 = r5
            r5 = r46
            r23 = r6
            r6 = r47
            r25 = r7
            r7 = r48
            r26 = r8
            r8 = r49
            r0.subFormat(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x00df
        L_0x01c6:
            r31 = r1
            r27 = r3
            r32 = r6
            r33 = r7
            r11 = r8
            r28 = r25
            r1 = 11
            int r8 = r11.get(r1)
            r1 = 0
            if (r8 != r0) goto L_0x0215
            boolean r3 = r9.hasMinute
            if (r3 == 0) goto L_0x01e4
            int r0 = r11.get(r0)
            if (r0 != 0) goto L_0x0215
        L_0x01e4:
            boolean r0 = r9.hasSecond
            if (r0 == 0) goto L_0x01f0
            r0 = 13
            int r0 = r11.get(r0)
            if (r0 != 0) goto L_0x0215
        L_0x01f0:
            r0 = 9
            int r0 = r11.get(r0)
            if (r12 > r5) goto L_0x0201
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.abbreviatedDayPeriods
            r1 = r2[r0]
        L_0x01fe:
            r31 = r0
            goto L_0x0215
        L_0x0201:
            if (r12 == r2) goto L_0x020e
            r2 = 5
            if (r12 <= r2) goto L_0x0207
            goto L_0x020e
        L_0x0207:
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.narrowDayPeriods
            r1 = r2[r0]
            goto L_0x01fe
        L_0x020e:
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.wideDayPeriods
            r1 = r2[r0]
            goto L_0x01fe
        L_0x0215:
            r7 = r1
            if (r7 != 0) goto L_0x0232
            r2 = 97
            r0 = r41
            r1 = r42
            r3 = r44
            r4 = r45
            r5 = r46
            r6 = r47
            r34 = r7
            r7 = r48
            r19 = r8
            r8 = r49
            r0.subFormat(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x023c
        L_0x0232:
            r34 = r7
            r19 = r8
            r1 = r34
            r10.append(r1)
        L_0x023c:
            r0 = r20
            r23 = r27
            r7 = r28
            r1 = r31
            r6 = r32
            goto L_0x05c4
        L_0x0248:
            r31 = r1
            r27 = r3
            r32 = r6
            r33 = r7
            r11 = r8
            r28 = r25
            r0 = 1
            if (r12 != r0) goto L_0x0267
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT
            r7 = r28
            r6 = r32
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
        L_0x0264:
            r0 = r17
            goto L_0x02a0
        L_0x0267:
            r7 = r28
            r6 = r32
            if (r12 != r4) goto L_0x0278
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x0264
        L_0x0278:
            if (r12 != r5) goto L_0x0285
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x0264
        L_0x0285:
            if (r12 != r2) goto L_0x0292
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x0264
        L_0x0292:
            r0 = 5
            if (r12 != r0) goto L_0x0264
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x0264
        L_0x02a0:
            r10.append(r0)
            goto L_0x0321
        L_0x02a5:
            r31 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r0 = 1
            if (r12 != r0) goto L_0x02be
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_SHORT
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
        L_0x02bb:
            r0 = r17
            goto L_0x02f3
        L_0x02be:
            if (r12 != r4) goto L_0x02cb
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FIXED
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x02bb
        L_0x02cb:
            if (r12 != r5) goto L_0x02d8
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FIXED
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x02bb
        L_0x02d8:
            if (r12 != r2) goto L_0x02e5
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FULL
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x02bb
        L_0x02e5:
            r0 = 5
            if (r12 != r0) goto L_0x02bb
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x02bb
        L_0x02f3:
            r10.append(r0)
            goto L_0x0321
        L_0x02f7:
            r31 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r0 = 1
            if (r12 != r0) goto L_0x0310
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT_SHORT
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
        L_0x030d:
            r0 = r17
            goto L_0x031d
        L_0x0310:
            if (r12 != r2) goto L_0x030d
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x030d
        L_0x031d:
            r10.append(r0)
        L_0x0321:
            r17 = r0
            r0 = r20
            r23 = r27
            r1 = r31
            goto L_0x05c4
        L_0x032b:
            r31 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shortYearNames
            if (r0 == 0) goto L_0x0353
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shortYearNames
            int r0 = r0.length
            r3 = r31
            if (r3 > r0) goto L_0x034e
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shortYearNames
            int r1 = r3 + -1
            safeAppend(r0, r1, r10)
            goto L_0x05a6
        L_0x034e:
            r23 = r27
            r5 = 0
            goto L_0x07b6
        L_0x0353:
            r23 = r27
            r3 = r31
            r5 = 0
            goto L_0x07b6
        L_0x035a:
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r3 = r1
            r0 = 1
            if (r12 != r0) goto L_0x0372
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID_SHORT
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
        L_0x036f:
            r0 = r17
            goto L_0x039b
        L_0x0372:
            if (r12 != r4) goto L_0x037f
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x036f
        L_0x037f:
            if (r12 != r5) goto L_0x038c
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.EXEMPLAR_LOCATION
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x036f
        L_0x038c:
            if (r12 != r2) goto L_0x036f
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LOCATION
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG
            goto L_0x036f
        L_0x039b:
            r10.append(r0)
            r17 = r0
            r1 = r3
            goto L_0x05c0
        L_0x03a4:
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r3 = r1
            if (r12 < r2) goto L_0x03b9
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneQuarters
            int r1 = r3 / 3
            safeAppend(r0, r1, r10)
            goto L_0x05a6
        L_0x03b9:
            if (r12 != r5) goto L_0x03c6
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneShortQuarters
            int r1 = r3 / 3
            safeAppend(r0, r1, r10)
            goto L_0x05a6
        L_0x03c6:
            int r1 = r3 / 3
            r0 = 1
            int r4 = r1 + 1
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r35 = r3
            r3 = r4
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            r23 = r27
            r19 = r35
            goto L_0x00ac
        L_0x03e3:
            r35 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            if (r12 < r2) goto L_0x03fd
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.quarters
            r4 = r35
            int r1 = r4 / 3
            safeAppend(r0, r1, r10)
        L_0x03f9:
            r19 = r4
            goto L_0x05a8
        L_0x03fd:
            r4 = r35
            if (r12 != r5) goto L_0x040b
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shortQuarters
            int r1 = r4 / 3
            safeAppend(r0, r1, r10)
            goto L_0x03f9
        L_0x040b:
            int r1 = r4 / 3
            r0 = 1
            int r3 = r1 + 1
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r36 = r4
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x04fa
        L_0x0422:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            if (r12 >= r5) goto L_0x043e
            r4 = 1
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r36
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x04fa
        L_0x043e:
            r0 = 7
            int r1 = r11.get(r0)
            r0 = 5
            if (r12 != r0) goto L_0x0451
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneNarrowWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW
            goto L_0x05c0
        L_0x0451:
            if (r12 != r2) goto L_0x045e
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE
            goto L_0x05c0
        L_0x045e:
            r0 = 6
            if (r12 != r0) goto L_0x0472
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneShorterWeekdays
            if (r0 == 0) goto L_0x0472
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneShorterWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE
            goto L_0x05c0
        L_0x0472:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneShortWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE
            goto L_0x05c0
        L_0x047d:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r0 = 1
            if (r12 != r0) goto L_0x0498
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_SHORT
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT
        L_0x0495:
            r0 = r17
            goto L_0x04a7
        L_0x0498:
            if (r12 != r2) goto L_0x0495
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LONG
            java.lang.String r17 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG
            goto L_0x0495
        L_0x04a7:
            r10.append(r0)
            goto L_0x0532
        L_0x04ac:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            if (r12 >= r2) goto L_0x04c2
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL
            java.lang.String r0 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x04da
        L_0x04c2:
            r0 = 5
            if (r12 != r0) goto L_0x04d0
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL
            java.lang.String r0 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            goto L_0x04da
        L_0x04d0:
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT
            java.lang.String r0 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
        L_0x04da:
            r10.append(r0)
            goto L_0x0532
        L_0x04de:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            if (r12 >= r5) goto L_0x0500
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r36
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
        L_0x04fa:
            r23 = r27
            r19 = r36
            goto L_0x00ac
        L_0x0500:
            r0 = 7
            int r1 = r11.get(r0)
            goto L_0x05b4
        L_0x0507:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            if (r12 >= r2) goto L_0x0521
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_SHORT
            java.lang.String r0 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r1 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT
        L_0x051e:
            r20 = r1
            goto L_0x052e
        L_0x0521:
            com.ibm.icu.text.TimeZoneFormat r0 = r41.tzFormat()
            com.ibm.icu.text.TimeZoneFormat$Style r1 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_LONG
            java.lang.String r0 = r0.format((com.ibm.icu.text.TimeZoneFormat.Style) r1, (com.ibm.icu.util.TimeZone) r6, (long) r7)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r1 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG
            goto L_0x051e
        L_0x052e:
            r10.append(r0)
        L_0x0532:
            r17 = r0
            r0 = r20
            r23 = r27
            r1 = r36
            goto L_0x05c4
        L_0x053c:
            r36 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r5 = r36
            if (r5 != 0) goto L_0x056b
            r0 = 10
            int r0 = r11.getLeastMaximum(r0)
            r1 = 1
            int r3 = r0 + 1
            r19 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r4 = r44
            r37 = r5
            r5 = r19
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
        L_0x0565:
            r23 = r27
            r19 = r37
            goto L_0x00ac
        L_0x056b:
            r37 = r5
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r37
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x0565
        L_0x057e:
            r37 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r0 = 5
            if (r12 < r0) goto L_0x059d
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.ampmsNarrow
            if (r0 != 0) goto L_0x0593
            r3 = r37
            goto L_0x059f
        L_0x0593:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.ampmsNarrow
            r3 = r37
            safeAppend(r0, r3, r10)
            goto L_0x05a6
        L_0x059d:
            r3 = r37
        L_0x059f:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.ampms
            safeAppend(r0, r3, r10)
        L_0x05a6:
            r19 = r3
        L_0x05a8:
            r23 = r27
            goto L_0x00ac
        L_0x05ac:
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r3 = r1
        L_0x05b4:
            r0 = 5
            if (r12 != r0) goto L_0x05c8
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.narrowWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW
        L_0x05c0:
            r0 = r20
            r23 = r27
        L_0x05c4:
            r22 = 0
            goto L_0x085f
        L_0x05c8:
            if (r12 != r2) goto L_0x05d4
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.weekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT
            goto L_0x05c0
        L_0x05d4:
            r0 = 6
            if (r12 != r0) goto L_0x05e7
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shorterWeekdays
            if (r0 == 0) goto L_0x05e7
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shorterWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT
            goto L_0x05c0
        L_0x05e7:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.shortWeekdays
            safeAppend(r0, r1, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT
            goto L_0x05c0
        L_0x05f1:
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r3 = r1
            com.ibm.icu.text.NumberFormat r0 = r9.numberFormat
            int r1 = java.lang.Math.min(r5, r12)
            r0.setMinimumIntegerDigits(r1)
            com.ibm.icu.text.NumberFormat r0 = r9.numberFormat
            r1 = 2147483647(0x7fffffff, float:NaN)
            r0.setMaximumIntegerDigits(r1)
            r0 = 1
            if (r12 != r0) goto L_0x0610
            int r1 = r3 / 100
            goto L_0x0616
        L_0x0610:
            if (r12 != r4) goto L_0x0615
            int r1 = r3 / 10
            goto L_0x0616
        L_0x0615:
            r1 = r3
        L_0x0616:
            java.text.FieldPosition r0 = new java.text.FieldPosition
            r2 = -1
            r0.<init>(r2)
            com.ibm.icu.text.NumberFormat r2 = r9.numberFormat
            long r3 = (long) r1
            r2.format((long) r3, (java.lang.StringBuffer) r10, (java.text.FieldPosition) r0)
            if (r12 <= r5) goto L_0x0632
            com.ibm.icu.text.NumberFormat r2 = r9.numberFormat
            int r3 = r12 + -3
            r2.setMinimumIntegerDigits(r3)
            com.ibm.icu.text.NumberFormat r2 = r9.numberFormat
            r3 = 0
            r2.format((long) r3, (java.lang.StringBuffer) r10, (java.text.FieldPosition) r0)
        L_0x0632:
            goto L_0x05c0
        L_0x0633:
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r3 = r1
            if (r3 != 0) goto L_0x065e
            r0 = 11
            int r0 = r11.getMaximum(r0)
            r1 = 1
            int r4 = r0 + 1
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r38 = r3
            r3 = r4
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
        L_0x0658:
            r23 = r27
            r19 = r38
            goto L_0x00ac
        L_0x065e:
            r38 = r3
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x0658
        L_0x066f:
            r38 = r1
            r27 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            java.lang.String r0 = r49.getType()
            java.lang.String r1 = "hebrew"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x06ab
            r0 = 1
            int r1 = r11.get(r0)
            boolean r0 = com.ibm.icu.util.HebrewCalendar.isLeapYear(r1)
            if (r0 == 0) goto L_0x0699
            r3 = r38
            r1 = 6
            if (r3 != r1) goto L_0x069b
            if (r12 < r5) goto L_0x069b
            r1 = 13
            goto L_0x069c
        L_0x0699:
            r3 = r38
        L_0x069b:
            r1 = r3
        L_0x069c:
            if (r0 != 0) goto L_0x06a6
            r3 = 6
            if (r1 < r3) goto L_0x06a7
            if (r12 >= r5) goto L_0x06a7
            int r1 = r1 + -1
            goto L_0x06a7
        L_0x06a6:
            r3 = 6
        L_0x06a7:
            r19 = r3
            r3 = r1
            goto L_0x06af
        L_0x06ab:
            r3 = r38
            r19 = 6
        L_0x06af:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            if (r0 == 0) goto L_0x06c4
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            int r0 = r0.length
            r1 = 7
            if (r0 < r1) goto L_0x06c4
            r0 = 22
            int r0 = r11.get(r0)
            goto L_0x06c5
        L_0x06c4:
            r0 = 0
        L_0x06c5:
            r21 = r0
            r22 = 0
            r0 = 5
            if (r12 != r0) goto L_0x0701
            r1 = r27
            if (r1 != r4) goto L_0x06e4
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.narrowMonths
            if (r21 == 0) goto L_0x06df
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.leapMonthPatterns
            r22 = r2[r4]
        L_0x06dc:
            r2 = r22
            goto L_0x06e0
        L_0x06df:
            goto L_0x06dc
        L_0x06e0:
            safeAppendWithMonthPattern(r0, r3, r10, r2)
            goto L_0x06f8
        L_0x06e4:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneNarrowMonths
            if (r21 == 0) goto L_0x06f4
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.leapMonthPatterns
            r4 = 5
            r22 = r2[r4]
        L_0x06f1:
            r2 = r22
            goto L_0x06f5
        L_0x06f4:
            goto L_0x06f1
        L_0x06f5:
            safeAppendWithMonthPattern(r0, r3, r10, r2)
        L_0x06f8:
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW
            r23 = r1
            r1 = r3
            r0 = r20
            goto L_0x05c4
        L_0x0701:
            r1 = r27
            if (r12 != r2) goto L_0x073c
            if (r1 != r4) goto L_0x0725
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.months
            if (r21 == 0) goto L_0x0717
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.leapMonthPatterns
            r4 = 0
            r22 = r2[r4]
        L_0x0714:
            r2 = r22
            goto L_0x0719
        L_0x0717:
            r4 = 0
            goto L_0x0714
        L_0x0719:
            safeAppendWithMonthPattern(r0, r3, r10, r2)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT
        L_0x071e:
            r23 = r1
            r1 = r3
            r22 = r4
            goto L_0x085d
        L_0x0725:
            r4 = 0
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.standaloneMonths
            if (r21 == 0) goto L_0x0735
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.leapMonthPatterns
            r22 = r2[r5]
        L_0x0732:
            r2 = r22
            goto L_0x0736
        L_0x0735:
            goto L_0x0732
        L_0x0736:
            safeAppendWithMonthPattern(r0, r3, r10, r2)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE
            goto L_0x071e
        L_0x073c:
            r0 = 0
            if (r12 != r5) goto L_0x0773
            if (r1 != r4) goto L_0x075d
            com.ibm.icu.text.DateFormatSymbols r2 = r9.formatData
            java.lang.String[] r2 = r2.shortMonths
            if (r21 == 0) goto L_0x0751
            com.ibm.icu.text.DateFormatSymbols r4 = r9.formatData
            java.lang.String[] r4 = r4.leapMonthPatterns
            r5 = 1
            r22 = r4[r5]
        L_0x074e:
            r4 = r22
            goto L_0x0752
        L_0x0751:
            goto L_0x074e
        L_0x0752:
            safeAppendWithMonthPattern(r2, r3, r10, r4)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT
        L_0x0757:
            r22 = r0
            r23 = r1
            goto L_0x0835
        L_0x075d:
            com.ibm.icu.text.DateFormatSymbols r4 = r9.formatData
            java.lang.String[] r4 = r4.standaloneShortMonths
            if (r21 == 0) goto L_0x076c
            com.ibm.icu.text.DateFormatSymbols r5 = r9.formatData
            java.lang.String[] r5 = r5.leapMonthPatterns
            r22 = r5[r2]
        L_0x0769:
            r2 = r22
            goto L_0x076d
        L_0x076c:
            goto L_0x0769
        L_0x076d:
            safeAppendWithMonthPattern(r4, r3, r10, r2)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE
            goto L_0x0757
        L_0x0773:
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            int r4 = r3 + 1
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r23 = r1
            r1 = r18
            r25 = r3
            r3 = r4
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            r0 = 1
            java.lang.String[] r1 = new java.lang.String[r0]
            java.lang.String r0 = r2.toString()
            r5 = 0
            r1[r5] = r0
            if (r21 == 0) goto L_0x07a0
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            r22 = r0[r19]
        L_0x079d:
            r0 = r22
            goto L_0x07a1
        L_0x07a0:
            goto L_0x079d
        L_0x07a1:
            safeAppendWithMonthPattern(r1, r5, r10, r0)
            r22 = r5
            r0 = r20
            r1 = r25
            goto L_0x085f
        L_0x07ad:
            r23 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r5 = 0
            r3 = r1
        L_0x07b6:
            java.lang.String r0 = r9.override
            if (r0 == 0) goto L_0x07db
            java.lang.String r0 = r9.override
            java.lang.String r1 = "hebr"
            int r0 = r0.compareTo(r1)
            if (r0 == 0) goto L_0x07ce
            java.lang.String r0 = r9.override
            java.lang.String r1 = "y=hebr"
            int r0 = r0.indexOf(r1)
            if (r0 < 0) goto L_0x07db
        L_0x07ce:
            r0 = 5000(0x1388, float:7.006E-42)
            if (r3 <= r0) goto L_0x07db
            r0 = 6000(0x1770, float:8.408E-42)
            if (r3 >= r0) goto L_0x07db
            int r1 = r3 + -5000
            r19 = r1
            goto L_0x07dd
        L_0x07db:
            r19 = r3
        L_0x07dd:
            if (r12 != r4) goto L_0x07f3
            r4 = 2
            r21 = 2
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r19
            r22 = r5
            r5 = r21
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x085b
        L_0x07f3:
            r22 = r5
            r5 = 2147483647(0x7fffffff, float:NaN)
            r0 = r41
            r1 = r18
            r2 = r42
            r3 = r19
            r4 = r44
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
            goto L_0x085b
        L_0x0806:
            r23 = r3
            r33 = r7
            r11 = r8
            r7 = r25
            r22 = 0
            r3 = r1
            java.lang.String r0 = r49.getType()
            java.lang.String r1 = "chinese"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x084d
            java.lang.String r0 = r49.getType()
            java.lang.String r1 = "dangi"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0829
            goto L_0x084d
        L_0x0829:
            r0 = 5
            if (r12 != r0) goto L_0x0837
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.narrowEras
            safeAppend(r0, r3, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW
        L_0x0835:
            r1 = r3
            goto L_0x085d
        L_0x0837:
            if (r12 != r2) goto L_0x0843
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.eraNames
            safeAppend(r0, r3, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE
            goto L_0x0835
        L_0x0843:
            com.ibm.icu.text.DateFormatSymbols r0 = r9.formatData
            java.lang.String[] r0 = r0.eras
            safeAppend(r0, r3, r10)
            com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage r20 = com.ibm.icu.text.DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV
            goto L_0x0835
        L_0x084d:
            r4 = 1
            r5 = 9
            r0 = r41
            r1 = r18
            r2 = r42
            r19 = r3
            r0.zeroPaddingNumber(r1, r2, r3, r4, r5)
        L_0x085b:
            r1 = r19
        L_0x085d:
            r0 = r20
        L_0x085f:
            if (r46 != 0) goto L_0x08ce
            if (r14 == 0) goto L_0x08ce
            r3 = r33
            int r4 = r10.codePointAt(r3)
            boolean r4 = com.ibm.icu.lang.UCharacter.isLowerCase(r4)
            if (r4 == 0) goto L_0x08c9
            r4 = 0
            int[] r5 = com.ibm.icu.text.SimpleDateFormat.AnonymousClass1.$SwitchMap$com$ibm$icu$text$DisplayContext
            int r19 = r47.ordinal()
            r5 = r5[r19]
            switch(r5) {
                case 1: goto L_0x089c;
                case 2: goto L_0x087e;
                case 3: goto L_0x087e;
                default: goto L_0x087b;
            }
        L_0x087b:
            r39 = r0
            goto L_0x08a0
        L_0x087e:
            com.ibm.icu.text.DateFormatSymbols r5 = r9.formatData
            java.util.Map<com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage, boolean[]> r5 = r5.capitalization
            if (r5 == 0) goto L_0x087b
            com.ibm.icu.text.DateFormatSymbols r5 = r9.formatData
            java.util.Map<com.ibm.icu.text.DateFormatSymbols$CapitalizationContextUsage, boolean[]> r5 = r5.capitalization
            java.lang.Object r5 = r5.get(r0)
            boolean[] r5 = (boolean[]) r5
            r39 = r0
            com.ibm.icu.text.DisplayContext r0 = com.ibm.icu.text.DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU
            if (r14 != r0) goto L_0x0897
            boolean r0 = r5[r22]
            goto L_0x089a
        L_0x0897:
            r0 = 1
            boolean r0 = r5[r0]
        L_0x089a:
            r4 = r0
            goto L_0x08a0
        L_0x089c:
            r39 = r0
            r4 = 1
        L_0x08a0:
            if (r4 == 0) goto L_0x08c6
            com.ibm.icu.text.BreakIterator r0 = r9.capitalizationBrkIter
            if (r0 != 0) goto L_0x08ae
            com.ibm.icu.util.ULocale r0 = r9.locale
            com.ibm.icu.text.BreakIterator r0 = com.ibm.icu.text.BreakIterator.getSentenceInstance((com.ibm.icu.util.ULocale) r0)
            r9.capitalizationBrkIter = r0
        L_0x08ae:
            java.lang.String r0 = r10.substring(r3)
            com.ibm.icu.util.ULocale r5 = r9.locale
            r40 = r1
            com.ibm.icu.text.BreakIterator r1 = r9.capitalizationBrkIter
            r2 = 768(0x300, float:1.076E-42)
            java.lang.String r1 = com.ibm.icu.lang.UCharacter.toTitleCase(r5, r0, r1, r2)
            int r2 = r42.length()
            r10.replace(r3, r2, r1)
            goto L_0x08d4
        L_0x08c6:
            r40 = r1
            goto L_0x08d4
        L_0x08c9:
            r39 = r0
            r40 = r1
            goto L_0x08d4
        L_0x08ce:
            r39 = r0
            r40 = r1
            r3 = r33
        L_0x08d4:
            int r0 = r48.getBeginIndex()
            int r1 = r48.getEndIndex()
            if (r0 != r1) goto L_0x090b
            int r0 = r48.getField()
            int[] r1 = PATTERN_INDEX_TO_DATE_FORMAT_FIELD
            r1 = r1[r23]
            if (r0 != r1) goto L_0x08f5
            r15.setBeginIndex(r13)
            int r0 = r42.length()
            int r0 = r0 + r13
            int r0 = r0 - r3
            r15.setEndIndex(r0)
            goto L_0x090b
        L_0x08f5:
            java.text.Format$Field r0 = r48.getFieldAttribute()
            com.ibm.icu.text.DateFormat$Field[] r1 = PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE
            r1 = r1[r23]
            if (r0 != r1) goto L_0x090b
            r15.setBeginIndex(r13)
            int r0 = r42.length()
            int r0 = r0 + r13
            int r0 = r0 - r3
            r15.setEndIndex(r0)
        L_0x090b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.subFormat(java.lang.StringBuffer, char, int, int, int, com.ibm.icu.text.DisplayContext, java.text.FieldPosition, com.ibm.icu.util.Calendar):void");
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
            appendTo.append(SimpleFormatterImpl.formatRawPattern(monthPattern, 1, 1, new CharSequence[]{array[value]}));
        }
    }

    private static class PatternItem {
        final boolean isNumeric;
        final int length;
        final char type;

        PatternItem(char type2, int length2) {
            this.type = type2;
            this.length = length2;
            this.isNumeric = SimpleDateFormat.isNumeric(type2, length2);
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
                } else if (!isSyntaxChar(ch)) {
                    if (itemType != 0) {
                        items.add(new PatternItem(itemType, itemLength));
                        itemType = 0;
                    }
                    text.append(ch);
                } else if (ch == itemType) {
                    itemLength++;
                } else {
                    if (itemType != 0) {
                        items.add(new PatternItem(itemType, itemLength));
                    } else if (text.length() > 0) {
                        items.add(text.toString());
                        text.setLength(0);
                    }
                    itemType = ch;
                    itemLength = 1;
                }
            }
        }
        if (itemType != 0) {
            items.add(new PatternItem(itemType, itemLength));
        } else if (text.length() > 0) {
            items.add(text.toString());
            text.setLength(0);
        }
        this.patternItems = items.toArray(new Object[items.size()]);
        PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
        return this.patternItems;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void zeroPaddingNumber(NumberFormat nf, StringBuffer buf, int value, int minDigits, int maxDigits) {
        if (!this.useLocalZeroPaddingNumberFormat || value < 0) {
            nf.setMinimumIntegerDigits(minDigits);
            nf.setMaximumIntegerDigits(maxDigits);
            nf.format((long) value, buf, new FieldPosition(-1));
            return;
        }
        fastZeroPaddingNumber(buf, value, minDigits, maxDigits);
    }

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
            String[] tmpDigits = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
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
                int padding = minDigits - (limit - index);
            } else {
                index--;
            }
        }
        int padding2 = minDigits - (limit - index);
        while (padding2 > 0 && index > 0) {
            index--;
            this.decimalBuf[index] = this.decDigits[0];
            padding2--;
        }
        while (padding2 > 0) {
            buf.append(this.decDigits[0]);
            padding2--;
        }
        buf.append(this.decimalBuf, index, limit - index);
    }

    /* access modifiers changed from: protected */
    public String zeroPaddingNumber(long value, int minDigits, int maxDigits) {
        this.numberFormat.setMinimumIntegerDigits(minDigits);
        this.numberFormat.setMaximumIntegerDigits(maxDigits);
        return this.numberFormat.format(value);
    }

    /* access modifiers changed from: private */
    public static final boolean isNumeric(char formatChar, int count) {
        return NUMERIC_FORMAT_CHARS.indexOf(formatChar) >= 0 || (count <= 2 && NUMERIC_FORMAT_CHARS2.indexOf(formatChar) >= 0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r11v14 */
    /* JADX WARNING: type inference failed for: r11v16 */
    /* JADX WARNING: type inference failed for: r11v17 */
    /* JADX WARNING: type inference failed for: r11v41 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x04d9  */
    /* JADX WARNING: Removed duplicated region for block: B:241:0x04e9  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x04f1  */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x0505  */
    /* JADX WARNING: Removed duplicated region for block: B:272:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parse(java.lang.String r53, com.ibm.icu.util.Calendar r54, java.text.ParsePosition r55) {
        /*
            r52 = this;
            r13 = r52
            r14 = r55
            r0 = 0
            r1 = 0
            com.ibm.icu.util.Calendar r2 = r13.calendar
            r3 = r54
            if (r3 == r2) goto L_0x003c
            java.lang.String r2 = r54.getType()
            com.ibm.icu.util.Calendar r4 = r13.calendar
            java.lang.String r4 = r4.getType()
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x003c
            com.ibm.icu.util.Calendar r2 = r13.calendar
            long r4 = r54.getTimeInMillis()
            r2.setTimeInMillis(r4)
            com.ibm.icu.util.Calendar r2 = r13.calendar
            com.ibm.icu.util.TimeZone r0 = r2.getTimeZone()
            com.ibm.icu.util.Calendar r2 = r13.calendar
            com.ibm.icu.util.TimeZone r4 = r54.getTimeZone()
            r2.setTimeZone(r4)
            r1 = r54
            com.ibm.icu.util.Calendar r2 = r13.calendar
            r12 = r0
            r11 = r1
            r15 = r2
            goto L_0x003f
        L_0x003c:
            r12 = r0
            r11 = r1
            r15 = r3
        L_0x003f:
            int r0 = r55.getIndex()
            r10 = 0
            if (r0 >= 0) goto L_0x004a
            r14.setErrorIndex(r10)
            return
        L_0x004a:
            r9 = r0
            com.ibm.icu.util.Output r1 = new com.ibm.icu.util.Output
            r8 = 0
            r1.<init>(r8)
            r7 = r1
            com.ibm.icu.util.Output r1 = new com.ibm.icu.util.Output
            com.ibm.icu.text.TimeZoneFormat$TimeType r2 = com.ibm.icu.text.TimeZoneFormat.TimeType.UNKNOWN
            r1.<init>(r2)
            r6 = r1
            r5 = 1
            boolean[] r1 = new boolean[r5]
            r1[r10] = r10
            r16 = r1
            r1 = -1
            r2 = 0
            r3 = 0
            r4 = 0
            com.ibm.icu.text.DateFormatSymbols r5 = r13.formatData
            java.lang.String[] r5 = r5.leapMonthPatterns
            if (r5 == 0) goto L_0x0083
            com.ibm.icu.text.DateFormatSymbols r5 = r13.formatData
            java.lang.String[] r5 = r5.leapMonthPatterns
            int r5 = r5.length
            r8 = 7
            if (r5 < r8) goto L_0x0083
            com.ibm.icu.text.MessageFormat r5 = new com.ibm.icu.text.MessageFormat
            com.ibm.icu.text.DateFormatSymbols r8 = r13.formatData
            java.lang.String[] r8 = r8.leapMonthPatterns
            r19 = 6
            r8 = r8[r19]
            com.ibm.icu.util.ULocale r10 = r13.locale
            r5.<init>((java.lang.String) r8, (com.ibm.icu.util.ULocale) r10)
            r4 = r5
        L_0x0083:
            r19 = r4
            java.lang.Object[] r10 = r52.getPatternItems()
            r8 = r0
            r21 = r2
            r22 = r3
            r0 = 0
        L_0x008f:
            r5 = r0
            int r0 = r10.length
            if (r5 >= r0) goto L_0x0245
            r0 = r10[r5]
            boolean r0 = r0 instanceof com.ibm.icu.text.SimpleDateFormat.PatternItem
            if (r0 == 0) goto L_0x0201
            r0 = r10[r5]
            r4 = r0
            com.ibm.icu.text.SimpleDateFormat$PatternItem r4 = (com.ibm.icu.text.SimpleDateFormat.PatternItem) r4
            boolean r0 = r4.isNumeric
            r2 = -1
            if (r0 == 0) goto L_0x00c5
            if (r1 != r2) goto L_0x00c5
            int r0 = r5 + 1
            int r3 = r10.length
            if (r0 >= r3) goto L_0x00c5
            int r0 = r5 + 1
            r0 = r10[r0]
            boolean r0 = r0 instanceof com.ibm.icu.text.SimpleDateFormat.PatternItem
            if (r0 == 0) goto L_0x00c5
            int r0 = r5 + 1
            r0 = r10[r0]
            com.ibm.icu.text.SimpleDateFormat$PatternItem r0 = (com.ibm.icu.text.SimpleDateFormat.PatternItem) r0
            boolean r0 = r0.isNumeric
            if (r0 == 0) goto L_0x00c5
            r0 = r5
            int r1 = r4.length
            r3 = r8
            r21 = r1
            r22 = r3
            goto L_0x00c6
        L_0x00c5:
            r0 = r1
        L_0x00c6:
            if (r0 == r2) goto L_0x013b
            int r1 = r4.length
            if (r0 != r5) goto L_0x00ce
            r1 = r21
        L_0x00ce:
            r23 = r1
            char r3 = r4.type
            r24 = 1
            r25 = 0
            r1 = r52
            r2 = r53
            r26 = r3
            r3 = r8
            r27 = r4
            r4 = r26
            r17 = r5
            r5 = r23
            r29 = r6
            r6 = r24
            r30 = r7
            r7 = r25
            r31 = r8
            r18 = 0
            r8 = r16
            r32 = r9
            r9 = r15
            r33 = r10
            r10 = r19
            r34 = r11
            r11 = r29
            int r8 = r1.subParse(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            if (r8 >= 0) goto L_0x012d
            int r21 = r21 + -1
            if (r21 != 0) goto L_0x0118
            r11 = r32
            r14.setIndex(r11)
            r14.setErrorIndex(r8)
            if (r12 == 0) goto L_0x0117
            com.ibm.icu.util.Calendar r1 = r13.calendar
            r1.setTimeZone(r12)
        L_0x0117:
            return
        L_0x0118:
            r11 = r32
            r1 = r0
            r8 = r22
            r9 = r11
            r6 = r29
            r7 = r30
            r10 = r33
            r11 = r34
            r50 = r1
            r0 = r50
            goto L_0x008f
        L_0x012d:
            r11 = r32
            r20 = r0
            r9 = r11
            r37 = r15
            r5 = r17
            r7 = r33
            r15 = r12
            goto L_0x01f9
        L_0x013b:
            r27 = r4
            r17 = r5
            r29 = r6
            r30 = r7
            r31 = r8
            r33 = r10
            r34 = r11
            r18 = 0
            r11 = r9
            r10 = r27
            char r1 = r10.type
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 == r2) goto L_0x01ed
            r20 = -1
            r9 = r31
            char r4 = r10.type
            int r5 = r10.length
            r6 = 0
            r7 = 1
            r1 = r52
            r2 = r53
            r3 = r31
            r8 = r16
            r35 = r9
            r9 = r15
            r23 = r10
            r10 = r19
            r36 = r11
            r11 = r29
            r37 = r15
            r15 = r12
            r12 = r30
            int r8 = r1.subParse(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            if (r8 >= 0) goto L_0x01e6
            r0 = -32000(0xffffffffffff8300, float:NaN)
            if (r8 != r0) goto L_0x01d2
            r8 = r35
            int r5 = r17 + 1
            r7 = r33
            int r0 = r7.length
            if (r5 >= r0) goto L_0x01cd
            r1 = r18
            int r5 = r17 + 1
            r0 = r7[r5]     // Catch:{ ClassCastException -> 0x01ba }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ ClassCastException -> 0x01ba }
            if (r0 != 0) goto L_0x019b
            int r5 = r17 + 1
            r1 = r7[r5]
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
        L_0x019b:
            int r1 = r0.length()
            r2 = 0
        L_0x01a0:
            if (r2 >= r1) goto L_0x01af
            char r3 = r0.charAt(r2)
            boolean r4 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r3)
            if (r4 == 0) goto L_0x01af
            int r2 = r2 + 1
            goto L_0x01a0
        L_0x01af:
            if (r2 != r1) goto L_0x01b5
            int r5 = r17 + 1
            r17 = r5
        L_0x01b5:
            r5 = r17
            r9 = r36
            goto L_0x01f9
        L_0x01ba:
            r0 = move-exception
            r9 = r36
            r14.setIndex(r9)
            r2 = r35
            r14.setErrorIndex(r2)
            if (r15 == 0) goto L_0x01cc
            com.ibm.icu.util.Calendar r3 = r13.calendar
            r3.setTimeZone(r15)
        L_0x01cc:
            return
        L_0x01cd:
            r9 = r36
            r5 = r17
            goto L_0x01f9
        L_0x01d2:
            r7 = r33
            r2 = r35
            r9 = r36
            r14.setIndex(r9)
            r14.setErrorIndex(r2)
            if (r15 == 0) goto L_0x01e5
            com.ibm.icu.util.Calendar r0 = r13.calendar
            r0.setTimeZone(r15)
        L_0x01e5:
            return
        L_0x01e6:
            r7 = r33
            r9 = r36
            r5 = r17
            goto L_0x01f9
        L_0x01ed:
            r9 = r11
            r37 = r15
            r7 = r33
            r15 = r12
            r20 = r0
            r5 = r17
            r8 = r31
        L_0x01f9:
            r17 = r5
            r1 = r8
            r0 = r20
            r2 = 0
            r8 = 1
            goto L_0x0234
        L_0x0201:
            r17 = r5
            r29 = r6
            r30 = r7
            r31 = r8
            r7 = r10
            r34 = r11
            r37 = r15
            r18 = 0
            r15 = r12
            r0 = -1
            r8 = 1
            boolean[] r10 = new boolean[r8]
            r1 = r52
            r2 = r53
            r3 = r31
            r4 = r7
            r6 = r10
            int r1 = r1.matchLiteral(r2, r3, r4, r5, r6)
            r2 = 0
            boolean r3 = r10[r2]
            if (r3 != 0) goto L_0x0234
            r14.setIndex(r9)
            r14.setErrorIndex(r1)
            if (r15 == 0) goto L_0x0233
            com.ibm.icu.util.Calendar r2 = r13.calendar
            r2.setTimeZone(r15)
        L_0x0233:
            return
        L_0x0234:
            int r3 = r17 + 1
            r8 = r1
            r10 = r7
            r12 = r15
            r6 = r29
            r7 = r30
            r11 = r34
            r15 = r37
            r1 = r0
            r0 = r3
            goto L_0x008f
        L_0x0245:
            r17 = r5
            r29 = r6
            r30 = r7
            r31 = r8
            r7 = r10
            r34 = r11
            r37 = r15
            r2 = 0
            r8 = 1
            r15 = r12
            int r0 = r53.length()
            r3 = r31
            if (r3 >= r0) goto L_0x0285
            r4 = r53
            char r0 = r4.charAt(r3)
            r5 = 46
            if (r0 != r5) goto L_0x0287
            com.ibm.icu.text.DateFormat$BooleanAttribute r5 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE
            boolean r5 = r13.getBooleanAttribute(r5)
            if (r5 == 0) goto L_0x0287
            int r5 = r7.length
            if (r5 == 0) goto L_0x0287
            int r5 = r7.length
            int r5 = r5 - r8
            r5 = r7[r5]
            boolean r6 = r5 instanceof com.ibm.icu.text.SimpleDateFormat.PatternItem
            if (r6 == 0) goto L_0x0287
            r6 = r5
            com.ibm.icu.text.SimpleDateFormat$PatternItem r6 = (com.ibm.icu.text.SimpleDateFormat.PatternItem) r6
            boolean r6 = r6.isNumeric
            if (r6 != 0) goto L_0x0287
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0287
        L_0x0285:
            r4 = r53
        L_0x0287:
            r5 = r30
            java.lang.Object r0 = r5.value
            if (r0 == 0) goto L_0x032b
            com.ibm.icu.util.ULocale r0 = r52.getLocale()
            com.ibm.icu.impl.DayPeriodRules r0 = com.ibm.icu.impl.DayPeriodRules.getInstance(r0)
            r6 = 10
            r10 = r37
            boolean r11 = r10.isSet(r6)
            r8 = 11
            if (r11 != 0) goto L_0x02cc
            boolean r11 = r10.isSet(r8)
            if (r11 != 0) goto L_0x02cc
            java.lang.Object r6 = r5.value
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r6 = (com.ibm.icu.impl.DayPeriodRules.DayPeriod) r6
            r38 = r3
            double r2 = r0.getMidPointForDayPeriod(r6)
            int r6 = (int) r2
            double r12 = (double) r6
            double r11 = r2 - r12
            r23 = 0
            int r11 = (r11 > r23 ? 1 : (r11 == r23 ? 0 : -1))
            if (r11 <= 0) goto L_0x02be
            r11 = 30
            goto L_0x02bf
        L_0x02be:
            r11 = 0
        L_0x02bf:
            r10.set(r8, r6)
            r8 = 12
            r10.set(r8, r11)
            r39 = r1
            goto L_0x0331
        L_0x02cc:
            r38 = r3
            boolean r2 = r10.isSet(r8)
            if (r2 == 0) goto L_0x02d9
            int r2 = r10.get(r8)
            goto L_0x02e1
        L_0x02d9:
            int r2 = r10.get(r6)
            if (r2 != 0) goto L_0x02e1
            r2 = 12
        L_0x02e1:
            if (r2 == 0) goto L_0x0325
            r3 = 13
            if (r3 > r2) goto L_0x02ef
            r3 = 23
            if (r2 > r3) goto L_0x02ef
            r39 = r1
            goto L_0x0327
        L_0x02ef:
            r3 = 12
            if (r2 != r3) goto L_0x02f4
            r2 = 0
        L_0x02f4:
            double r11 = (double) r2
            int r3 = r10.get(r3)
            r39 = r1
            r40 = r2
            double r1 = (double) r3
            r23 = 4633641066610819072(0x404e000000000000, double:60.0)
            double r1 = r1 / r23
            double r11 = r11 + r1
            java.lang.Object r1 = r5.value
            com.ibm.icu.impl.DayPeriodRules$DayPeriod r1 = (com.ibm.icu.impl.DayPeriodRules.DayPeriod) r1
            double r1 = r0.getMidPointForDayPeriod(r1)
            double r23 = r11 - r1
            r25 = -4604930618986332160(0xc018000000000000, double:-6.0)
            int r3 = (r25 > r23 ? 1 : (r25 == r23 ? 0 : -1))
            r6 = 9
            if (r3 > 0) goto L_0x0320
            r25 = 4618441417868443648(0x4018000000000000, double:6.0)
            int r3 = (r23 > r25 ? 1 : (r23 == r25 ? 0 : -1))
            if (r3 >= 0) goto L_0x0320
            r3 = 0
            r10.set(r6, r3)
            goto L_0x0331
        L_0x0320:
            r3 = 1
            r10.set(r6, r3)
            goto L_0x0331
        L_0x0325:
            r39 = r1
        L_0x0327:
            r10.set(r8, r2)
            goto L_0x0331
        L_0x032b:
            r39 = r1
            r38 = r3
            r10 = r37
        L_0x0331:
            r3 = r38
            r14.setIndex(r3)
            r1 = r29
            java.lang.Object r0 = r1.value     // Catch:{ IllegalArgumentException -> 0x04f4 }
            com.ibm.icu.text.TimeZoneFormat$TimeType r0 = (com.ibm.icu.text.TimeZoneFormat.TimeType) r0     // Catch:{ IllegalArgumentException -> 0x04f4 }
            r2 = 0
            boolean r6 = r16[r2]     // Catch:{ IllegalArgumentException -> 0x04f4 }
            if (r6 != 0) goto L_0x0357
            com.ibm.icu.text.TimeZoneFormat$TimeType r2 = com.ibm.icu.text.TimeZoneFormat.TimeType.UNKNOWN     // Catch:{ IllegalArgumentException -> 0x034c }
            if (r0 == r2) goto L_0x0346
            goto L_0x0357
        L_0x0346:
            r43 = r1
            r45 = r5
            goto L_0x04d5
        L_0x034c:
            r0 = move-exception
            r43 = r1
        L_0x034f:
            r45 = r5
        L_0x0351:
            r1 = r34
            r2 = r52
            goto L_0x04fd
        L_0x0357:
            r2 = 0
            boolean r6 = r16[r2]     // Catch:{ IllegalArgumentException -> 0x04f4 }
            if (r6 == 0) goto L_0x037a
            java.lang.Object r2 = r10.clone()     // Catch:{ IllegalArgumentException -> 0x034c }
            com.ibm.icu.util.Calendar r2 = (com.ibm.icu.util.Calendar) r2     // Catch:{ IllegalArgumentException -> 0x034c }
            java.util.Date r6 = r2.getTime()     // Catch:{ IllegalArgumentException -> 0x034c }
            java.util.Date r8 = r52.getDefaultCenturyStart()     // Catch:{ IllegalArgumentException -> 0x034c }
            boolean r8 = r6.before(r8)     // Catch:{ IllegalArgumentException -> 0x034c }
            if (r8 == 0) goto L_0x037a
            int r8 = r52.getDefaultCenturyStartYear()     // Catch:{ IllegalArgumentException -> 0x034c }
            int r8 = r8 + 100
            r11 = 1
            r10.set(r11, r8)     // Catch:{ IllegalArgumentException -> 0x034c }
        L_0x037a:
            com.ibm.icu.text.TimeZoneFormat$TimeType r2 = com.ibm.icu.text.TimeZoneFormat.TimeType.UNKNOWN     // Catch:{ IllegalArgumentException -> 0x04f4 }
            if (r0 == r2) goto L_0x04d1
            java.lang.Object r2 = r10.clone()     // Catch:{ IllegalArgumentException -> 0x04c7 }
            com.ibm.icu.util.Calendar r2 = (com.ibm.icu.util.Calendar) r2     // Catch:{ IllegalArgumentException -> 0x04c7 }
            com.ibm.icu.util.TimeZone r6 = r2.getTimeZone()     // Catch:{ IllegalArgumentException -> 0x04c7 }
            r8 = 0
            boolean r11 = r6 instanceof com.ibm.icu.util.BasicTimeZone     // Catch:{ IllegalArgumentException -> 0x04c7 }
            if (r11 == 0) goto L_0x0391
            r11 = r6
            com.ibm.icu.util.BasicTimeZone r11 = (com.ibm.icu.util.BasicTimeZone) r11     // Catch:{ IllegalArgumentException -> 0x034c }
            r8 = r11
        L_0x0391:
            r11 = 15
            r12 = 0
            r2.set(r11, r12)     // Catch:{ IllegalArgumentException -> 0x04c7 }
            r13 = 16
            r2.set(r13, r12)     // Catch:{ IllegalArgumentException -> 0x04c7 }
            long r23 = r2.getTimeInMillis()     // Catch:{ IllegalArgumentException -> 0x04c7 }
            r41 = r23
            r12 = 2
            int[] r12 = new int[r12]     // Catch:{ IllegalArgumentException -> 0x04c7 }
            if (r8 == 0) goto L_0x03d0
            com.ibm.icu.text.TimeZoneFormat$TimeType r13 = com.ibm.icu.text.TimeZoneFormat.TimeType.STANDARD     // Catch:{ IllegalArgumentException -> 0x034c }
            if (r0 != r13) goto L_0x03c2
            r26 = 1
            r27 = 1
            r23 = r8
            r24 = r41
            r28 = r12
            r23.getOffsetFromLocal(r24, r26, r27, r28)     // Catch:{ IllegalArgumentException -> 0x034c }
        L_0x03b8:
            r43 = r1
            r44 = r2
            r45 = r5
            r1 = r41
            r11 = 1
            goto L_0x0402
        L_0x03c2:
            r26 = 3
            r27 = 3
            r23 = r8
            r24 = r41
            r28 = r12
            r23.getOffsetFromLocal(r24, r26, r27, r28)     // Catch:{ IllegalArgumentException -> 0x034c }
            goto L_0x03b8
        L_0x03d0:
            r43 = r1
            r44 = r2
            r1 = r41
            r13 = 1
            r6.getOffset(r1, r13, r12)     // Catch:{ IllegalArgumentException -> 0x04bf }
            com.ibm.icu.text.TimeZoneFormat$TimeType r11 = com.ibm.icu.text.TimeZoneFormat.TimeType.STANDARD     // Catch:{ IllegalArgumentException -> 0x04bf }
            if (r0 != r11) goto L_0x03e8
            r11 = r12[r13]     // Catch:{ IllegalArgumentException -> 0x03e5 }
            if (r11 != 0) goto L_0x03e3
            goto L_0x03e8
        L_0x03e3:
            r11 = 1
            goto L_0x03f1
        L_0x03e5:
            r0 = move-exception
            goto L_0x034f
        L_0x03e8:
            com.ibm.icu.text.TimeZoneFormat$TimeType r11 = com.ibm.icu.text.TimeZoneFormat.TimeType.DAYLIGHT     // Catch:{ IllegalArgumentException -> 0x04bf }
            if (r0 != r11) goto L_0x03ff
            r11 = 1
            r13 = r12[r11]     // Catch:{ IllegalArgumentException -> 0x04bf }
            if (r13 != 0) goto L_0x03fc
        L_0x03f1:
            r23 = 86400000(0x5265c00, double:4.2687272E-316)
            r45 = r5
            long r4 = r1 - r23
            r6.getOffset(r4, r11, r12)     // Catch:{ IllegalArgumentException -> 0x04bc }
            goto L_0x0402
        L_0x03fc:
            r45 = r5
            goto L_0x0402
        L_0x03ff:
            r45 = r5
            r11 = 1
        L_0x0402:
            r4 = r12[r11]     // Catch:{ IllegalArgumentException -> 0x04bc }
            com.ibm.icu.text.TimeZoneFormat$TimeType r5 = com.ibm.icu.text.TimeZoneFormat.TimeType.STANDARD     // Catch:{ IllegalArgumentException -> 0x04bc }
            if (r0 != r5) goto L_0x041b
            r5 = r12[r11]     // Catch:{ IllegalArgumentException -> 0x04bc }
            if (r5 == 0) goto L_0x0413
            r4 = 0
            r47 = r0
            r48 = r1
            goto L_0x04ae
        L_0x0413:
            r47 = r0
            r48 = r1
            r46 = r4
            goto L_0x04ac
        L_0x041b:
            r5 = r12[r11]     // Catch:{ IllegalArgumentException -> 0x04bc }
            if (r5 != 0) goto L_0x04a6
            if (r8 == 0) goto L_0x0495
            r5 = 0
            r11 = r12[r5]     // Catch:{ IllegalArgumentException -> 0x04bc }
            r46 = r4
            long r4 = (long) r11     // Catch:{ IllegalArgumentException -> 0x04bc }
            long r41 = r1 + r4
            r4 = r41
            r23 = r41
            r11 = 0
            r47 = r0
            r48 = r1
            r0 = r4
            r4 = 0
        L_0x0434:
            r2 = 1
            com.ibm.icu.util.TimeZoneTransition r5 = r8.getPreviousTransition(r0, r2)     // Catch:{ IllegalArgumentException -> 0x04bc }
            if (r5 != 0) goto L_0x0443
        L_0x043c:
            r50 = r0
            r0 = r23
            r23 = r50
            goto L_0x0457
        L_0x0443:
            long r25 = r5.getTime()     // Catch:{ IllegalArgumentException -> 0x04bc }
            r27 = 1
            long r0 = r25 - r27
            com.ibm.icu.util.TimeZoneRule r13 = r5.getFrom()     // Catch:{ IllegalArgumentException -> 0x04bc }
            int r13 = r13.getDSTSavings()     // Catch:{ IllegalArgumentException -> 0x04bc }
            r11 = r13
            if (r11 == 0) goto L_0x0434
            goto L_0x043c
        L_0x0457:
            r2 = 0
            com.ibm.icu.util.TimeZoneTransition r13 = r8.getNextTransition(r0, r2)     // Catch:{ IllegalArgumentException -> 0x04bc }
            r2 = r13
            if (r2 != 0) goto L_0x0460
            goto L_0x0472
        L_0x0460:
            long r25 = r2.getTime()     // Catch:{ IllegalArgumentException -> 0x04bc }
            r0 = r25
            com.ibm.icu.util.TimeZoneRule r13 = r2.getTo()     // Catch:{ IllegalArgumentException -> 0x04bc }
            int r13 = r13.getDSTSavings()     // Catch:{ IllegalArgumentException -> 0x04bc }
            r4 = r13
            if (r4 == 0) goto L_0x0457
        L_0x0472:
            if (r5 == 0) goto L_0x0482
            if (r2 == 0) goto L_0x0482
            long r25 = r41 - r23
            long r27 = r0 - r41
            int r13 = (r25 > r27 ? 1 : (r25 == r27 ? 0 : -1))
            if (r13 <= 0) goto L_0x0480
            r13 = r4
            goto L_0x0492
        L_0x0480:
            r13 = r11
            goto L_0x0492
        L_0x0482:
            if (r5 == 0) goto L_0x0488
            if (r11 == 0) goto L_0x0488
            r13 = r11
            goto L_0x0492
        L_0x0488:
            if (r2 == 0) goto L_0x048e
            if (r4 == 0) goto L_0x048e
            r13 = r4
            goto L_0x0492
        L_0x048e:
            int r13 = r8.getDSTSavings()     // Catch:{ IllegalArgumentException -> 0x04bc }
        L_0x0492:
            r4 = r13
            goto L_0x04a0
        L_0x0495:
            r47 = r0
            r48 = r1
            r46 = r4
            int r0 = r6.getDSTSavings()     // Catch:{ IllegalArgumentException -> 0x04bc }
            r4 = r0
        L_0x04a0:
            if (r4 != 0) goto L_0x04ae
            r4 = 3600000(0x36ee80, float:5.044674E-39)
            goto L_0x04ae
        L_0x04a6:
            r47 = r0
            r48 = r1
            r46 = r4
        L_0x04ac:
            r4 = r46
        L_0x04ae:
            r0 = 0
            r0 = r12[r0]     // Catch:{ IllegalArgumentException -> 0x04bc }
            r1 = 15
            r10.set(r1, r0)     // Catch:{ IllegalArgumentException -> 0x04bc }
            r0 = 16
            r10.set(r0, r4)     // Catch:{ IllegalArgumentException -> 0x04bc }
            goto L_0x04d5
        L_0x04bc:
            r0 = move-exception
            goto L_0x0351
        L_0x04bf:
            r0 = move-exception
            r45 = r5
            r1 = r34
            r2 = r52
            goto L_0x04fd
        L_0x04c7:
            r0 = move-exception
            r43 = r1
            r45 = r5
            r1 = r34
            r2 = r52
            goto L_0x04fd
        L_0x04d1:
            r43 = r1
            r45 = r5
        L_0x04d5:
            r1 = r34
            if (r1 == 0) goto L_0x04e7
            com.ibm.icu.util.TimeZone r0 = r10.getTimeZone()
            r1.setTimeZone(r0)
            long r4 = r10.getTimeInMillis()
            r1.setTimeInMillis(r4)
        L_0x04e7:
            if (r15 == 0) goto L_0x04f1
            r2 = r52
            com.ibm.icu.util.Calendar r0 = r2.calendar
            r0.setTimeZone(r15)
            goto L_0x04f3
        L_0x04f1:
            r2 = r52
        L_0x04f3:
            return
        L_0x04f4:
            r0 = move-exception
            r43 = r1
            r45 = r5
            r1 = r34
            r2 = r52
        L_0x04fd:
            r14.setErrorIndex(r3)
            r14.setIndex(r9)
            if (r15 == 0) goto L_0x050a
            com.ibm.icu.util.Calendar r4 = r2.calendar
            r4.setTimeZone(r15)
        L_0x050a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.parse(java.lang.String, com.ibm.icu.util.Calendar, java.text.ParsePosition):void");
    }

    private int matchLiteral(String text, int pos, Object[] items, int itemIndex, boolean[] complete) {
        String str = text;
        Object[] objArr = items;
        int i = itemIndex;
        int originalPos = pos;
        String patternLiteral = (String) objArr[i];
        int plen = patternLiteral.length();
        int tlen = text.length();
        int pos2 = pos;
        int idx = 0;
        while (idx < plen && pos2 < tlen) {
            char pch = patternLiteral.charAt(idx);
            char ich = str.charAt(pos2);
            if (!PatternProps.isWhiteSpace(pch) || !PatternProps.isWhiteSpace(ich)) {
                if (pch != ich) {
                    if (ich != '.' || pos2 != originalPos || i <= 0 || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                        if ((pch != ' ' && pch != '.') || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                            if (pos2 == originalPos || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)) {
                                break;
                            }
                            idx++;
                        } else {
                            idx++;
                        }
                    } else {
                        Object before = objArr[i - 1];
                        if (!(before instanceof PatternItem) || ((PatternItem) before).isNumeric) {
                            break;
                        }
                        pos2++;
                    }
                }
            } else {
                while (idx + 1 < plen && PatternProps.isWhiteSpace(patternLiteral.charAt(idx + 1))) {
                    idx++;
                }
                while (pos2 + 1 < tlen && PatternProps.isWhiteSpace(str.charAt(pos2 + 1))) {
                    pos2++;
                }
            }
            idx++;
            pos2++;
        }
        complete[0] = idx == plen;
        if (complete[0] || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) || i <= 0 || i >= objArr.length - 1 || originalPos >= tlen) {
            return pos2;
        }
        Object before2 = objArr[i - 1];
        Object after = objArr[i + 1];
        if (!(before2 instanceof PatternItem) || !(after instanceof PatternItem)) {
            return pos2;
        }
        if (DATE_PATTERN_TYPE.contains((int) ((PatternItem) before2).type) == DATE_PATTERN_TYPE.contains((int) ((PatternItem) after).type)) {
            return pos2;
        }
        int newPos = originalPos;
        while (newPos < tlen && PatternProps.isWhiteSpace(str.charAt(newPos))) {
            newPos++;
        }
        complete[0] = newPos > originalPos;
        return newPos;
    }

    /* access modifiers changed from: protected */
    public int matchString(String text, int start, int field, String[] data, Calendar cal) {
        return matchString(text, start, field, data, (String) null, cal);
    }

    @Deprecated
    private int matchString(String text, int start, int field, String[] data, String monthPattern, Calendar cal) {
        String str = text;
        int i = start;
        int i2 = field;
        String[] strArr = data;
        String str2 = monthPattern;
        Calendar calendar = cal;
        int i3 = 0;
        int count = strArr.length;
        if (i2 == 7) {
            i3 = 1;
        }
        int isLeapMonth = 0;
        int bestMatch = -1;
        int bestMatchLength = 0;
        for (int i4 = i3; i4 < count; i4++) {
            int length = strArr[i4].length();
            if (length > bestMatchLength) {
                int regionMatchesWithOptionalDot = regionMatchesWithOptionalDot(str, i, strArr[i4], length);
                int matchLength = regionMatchesWithOptionalDot;
                if (regionMatchesWithOptionalDot >= 0) {
                    bestMatch = i4;
                    bestMatchLength = matchLength;
                    isLeapMonth = 0;
                }
            }
            if (str2 != null) {
                String leapMonthName = SimpleFormatterImpl.formatRawPattern(str2, 1, 1, new CharSequence[]{strArr[i4]});
                int length2 = leapMonthName.length();
                if (length2 > bestMatchLength) {
                    int regionMatchesWithOptionalDot2 = regionMatchesWithOptionalDot(str, i, leapMonthName, length2);
                    int matchLength2 = regionMatchesWithOptionalDot2;
                    if (regionMatchesWithOptionalDot2 >= 0) {
                        bestMatch = i4;
                        bestMatchLength = matchLength2;
                        isLeapMonth = 1;
                    }
                }
            }
        }
        if (bestMatch < 0) {
            return ~i;
        }
        if (i2 >= 0) {
            if (i2 == 1) {
                bestMatch++;
            }
            calendar.set(i2, bestMatch);
            if (str2 != null) {
                calendar.set(22, isLeapMonth);
            }
        }
        return i + bestMatchLength;
    }

    private int regionMatchesWithOptionalDot(String text, int start, String data, int length) {
        if (text.regionMatches(true, start, data, 0, length)) {
            return length;
        }
        if (data.length() <= 0 || data.charAt(data.length() - 1) != '.') {
            return -1;
        }
        if (text.regionMatches(true, start, data, 0, length - 1)) {
            return length - 1;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int matchQuarterString(String text, int start, int field, String[] data, Calendar cal) {
        int count = data.length;
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < count; i++) {
            int length = data[i].length();
            if (length > bestMatchLength) {
                int regionMatchesWithOptionalDot = regionMatchesWithOptionalDot(text, start, data[i], length);
                int matchLength = regionMatchesWithOptionalDot;
                if (regionMatchesWithOptionalDot >= 0) {
                    bestMatch = i;
                    bestMatchLength = matchLength;
                }
            }
        }
        if (bestMatch < 0) {
            return -start;
        }
        cal.set(field, bestMatch * 3);
        return start + bestMatchLength;
    }

    private int matchDayPeriodString(String text, int start, String[] data, int dataLength, Output<DayPeriodRules.DayPeriod> dayPeriod) {
        int length;
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < dataLength; i++) {
            if (data[i] != null && (length = data[i].length()) > bestMatchLength) {
                int regionMatchesWithOptionalDot = regionMatchesWithOptionalDot(text, start, data[i], length);
                int matchLength = regionMatchesWithOptionalDot;
                if (regionMatchesWithOptionalDot >= 0) {
                    bestMatch = i;
                    bestMatchLength = matchLength;
                }
            }
        }
        if (bestMatch < 0) {
            return -start;
        }
        dayPeriod.value = DayPeriodRules.DayPeriod.VALUES[bestMatch];
        return start + bestMatchLength;
    }

    /* access modifiers changed from: protected */
    public int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal) {
        return subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, (MessageFormat) null, (Output<TimeZoneFormat.TimeType>) null);
    }

    private int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal, MessageFormat numericLeapMonthFormatter, Output<TimeZoneFormat.TimeType> output) {
        return subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, (MessageFormat) null, (Output<TimeZoneFormat.TimeType>) null, (Output<DayPeriodRules.DayPeriod>) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0286, code lost:
        if (r14 == 4) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x038e, code lost:
        if (r6 > r12.formatData.shortYearNames.length) goto L_0x0393;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int subParse(java.lang.String r38, int r39, char r40, int r41, boolean r42, boolean r43, boolean[] r44, com.ibm.icu.util.Calendar r45, com.ibm.icu.text.MessageFormat r46, com.ibm.icu.util.Output<com.ibm.icu.text.TimeZoneFormat.TimeType> r47, com.ibm.icu.util.Output<com.ibm.icu.impl.DayPeriodRules.DayPeriod> r48) {
        /*
            r37 = this;
            r12 = r37
            r13 = r38
            r14 = r41
            r11 = r43
            r10 = r45
            r9 = r46
            r8 = r47
            r0 = 0
            r1 = 0
            r6 = 0
            java.text.ParsePosition r2 = new java.text.ParsePosition
            r7 = 0
            r2.<init>(r7)
            r5 = r2
            int r4 = getIndexFromChar(r40)
            r2 = -1
            if (r4 != r2) goto L_0x0023
            r2 = r39
            int r3 = ~r2
            return r3
        L_0x0023:
            r2 = r39
            r3 = r40
            com.ibm.icu.text.NumberFormat r1 = r12.getNumberFormat(r3)
            int[] r16 = PATTERN_INDEX_TO_CALENDAR_FIELD
            r7 = r16[r4]
            if (r9 == 0) goto L_0x0038
            r18 = r0
            r0 = 0
            r9.setFormatByArgumentIndex(r0, r1)
            goto L_0x003a
        L_0x0038:
            r18 = r0
        L_0x003a:
            java.lang.String r0 = r45.getType()
            r19 = r1
            java.lang.String r1 = "chinese"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0057
            java.lang.String r0 = r45.getType()
            java.lang.String r1 = "dangi"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0055
            goto L_0x0057
        L_0x0055:
            r0 = 0
            goto L_0x0058
        L_0x0057:
            r0 = 1
        L_0x0058:
            r16 = r0
            int r0 = r38.length()
            if (r2 < r0) goto L_0x0062
            int r0 = ~r2
            return r0
        L_0x0062:
            int r0 = com.ibm.icu.text.UTF16.charAt((java.lang.String) r13, (int) r2)
            boolean r1 = com.ibm.icu.lang.UCharacter.isUWhiteSpace(r0)
            if (r1 == 0) goto L_0x007c
            boolean r1 = com.ibm.icu.impl.PatternProps.isWhiteSpace(r0)
            if (r1 != 0) goto L_0x0073
            goto L_0x007c
        L_0x0073:
            int r1 = com.ibm.icu.text.UTF16.getCharCount(r0)
            int r2 = r2 + r1
            r0 = r16
            goto L_0x0058
        L_0x007c:
            r5.setIndex(r2)
            r1 = 2
            r0 = 4
            if (r4 == r0) goto L_0x00bd
            r0 = 15
            if (r4 == r0) goto L_0x00bd
            if (r4 != r1) goto L_0x008b
            if (r14 <= r1) goto L_0x00bd
        L_0x008b:
            r0 = 26
            if (r4 == r0) goto L_0x00bd
            r0 = 19
            if (r4 == r0) goto L_0x00bd
            r0 = 25
            if (r4 == r0) goto L_0x00bd
            r0 = 1
            if (r4 == r0) goto L_0x00bd
            r0 = 18
            if (r4 == r0) goto L_0x00bd
            r0 = 30
            if (r4 == r0) goto L_0x00bd
            if (r4 != 0) goto L_0x00a6
            if (r16 != 0) goto L_0x00bd
        L_0x00a6:
            r0 = 27
            if (r4 == r0) goto L_0x00bd
            r0 = 28
            if (r4 == r0) goto L_0x00bd
            r0 = 8
            if (r4 != r0) goto L_0x00b3
            goto L_0x00bd
        L_0x00b3:
            r15 = r2
            r10 = r4
            r9 = r5
            r26 = r7
            r5 = r19
            r7 = 1
            goto L_0x0155
        L_0x00bd:
            r0 = 0
            if (r9 == 0) goto L_0x00ff
            if (r4 == r1) goto L_0x00cb
            r1 = 26
            if (r4 != r1) goto L_0x00c7
            goto L_0x00cb
        L_0x00c7:
            r23 = r0
            r0 = 1
            goto L_0x0102
        L_0x00cb:
            java.lang.Object[] r1 = r9.parse(r13, r5)
            r23 = r0
            if (r1 == 0) goto L_0x00f2
            int r0 = r5.getIndex()
            if (r0 <= r2) goto L_0x00f2
            r0 = 0
            r3 = r1[r0]
            boolean r3 = r3 instanceof java.lang.Number
            if (r3 == 0) goto L_0x00f2
            r3 = 1
            r17 = r1[r0]
            java.lang.Number r17 = (java.lang.Number) r17
            r24 = r1
            r0 = 1
            r1 = 22
            r10.set(r1, r0)
            r23 = r3
            r18 = r17
            goto L_0x0102
        L_0x00f2:
            r24 = r1
            r0 = 1
            r1 = 22
            r5.setIndex(r2)
            r3 = 0
            r10.set(r1, r3)
            goto L_0x0102
        L_0x00ff:
            r23 = r0
            r0 = 1
        L_0x0102:
            if (r23 != 0) goto L_0x0143
            if (r42 == 0) goto L_0x012b
            int r1 = r2 + r14
            int r3 = r38.length()
            if (r1 <= r3) goto L_0x0110
            int r0 = ~r2
            return r0
        L_0x0110:
            r1 = r0
            r3 = 4
            r0 = r37
            r26 = r7
            r25 = r19
            r7 = r1
            r1 = r38
            r15 = r2
            r2 = r41
            r10 = r3
            r3 = r5
            r10 = r4
            r4 = r43
            r9 = r5
            r5 = r25
            java.lang.Number r0 = r0.parseInt(r1, r2, r3, r4, r5)
            goto L_0x0139
        L_0x012b:
            r15 = r2
            r10 = r4
            r9 = r5
            r26 = r7
            r25 = r19
            r7 = r0
            r5 = r25
            java.lang.Number r0 = r12.parseInt(r13, r9, r11, r5)
        L_0x0139:
            if (r0 != 0) goto L_0x014d
            boolean r1 = r12.allowNumericFallback(r10)
            if (r1 != 0) goto L_0x014d
            int r1 = ~r15
            return r1
        L_0x0143:
            r15 = r2
            r10 = r4
            r9 = r5
            r26 = r7
            r5 = r19
            r7 = r0
            r0 = r18
        L_0x014d:
            if (r0 == 0) goto L_0x0153
            int r6 = r0.intValue()
        L_0x0153:
            r18 = r0
        L_0x0155:
            r4 = 6
            r3 = 5
            r2 = 3
            switch(r10) {
                case 0: goto L_0x08fa;
                case 1: goto L_0x0885;
                case 2: goto L_0x0760;
                case 3: goto L_0x015b;
                case 4: goto L_0x0741;
                case 5: goto L_0x015b;
                case 6: goto L_0x015b;
                case 7: goto L_0x015b;
                case 8: goto L_0x070f;
                case 9: goto L_0x0665;
                case 10: goto L_0x015b;
                case 11: goto L_0x015b;
                case 12: goto L_0x015b;
                case 13: goto L_0x015b;
                case 14: goto L_0x05f8;
                case 15: goto L_0x05d9;
                case 16: goto L_0x015b;
                case 17: goto L_0x05b2;
                case 18: goto L_0x0885;
                case 19: goto L_0x0589;
                case 20: goto L_0x015b;
                case 21: goto L_0x015b;
                case 22: goto L_0x015b;
                case 23: goto L_0x055d;
                case 24: goto L_0x0536;
                case 25: goto L_0x0493;
                case 26: goto L_0x0760;
                case 27: goto L_0x0430;
                case 28: goto L_0x03cd;
                case 29: goto L_0x039f;
                case 30: goto L_0x0351;
                case 31: goto L_0x0329;
                case 32: goto L_0x02f6;
                case 33: goto L_0x02bd;
                case 34: goto L_0x015b;
                case 35: goto L_0x0231;
                case 36: goto L_0x01c5;
                case 37: goto L_0x016f;
                default: goto L_0x015b;
            }
        L_0x015b:
            r27 = r5
            r11 = r9
            r9 = r10
            r7 = r26
            r10 = r8
            r8 = r6
            if (r42 == 0) goto L_0x0968
            int r2 = r15 + r14
            int r0 = r38.length()
            if (r2 <= r0) goto L_0x0953
            int r0 = -r15
            return r0
        L_0x016f:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r2)
            r7 = r0
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String r0 = r0.getTimeSeparatorString()
            r7.add(r0)
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String r0 = r0.getTimeSeparatorString()
            java.lang.String r1 = ":"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0191
            java.lang.String r0 = ":"
            r7.add(r0)
        L_0x0191:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x01ac
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String r0 = r0.getTimeSeparatorString()
            java.lang.String r1 = "."
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x01ac
            java.lang.String r0 = "."
            r7.add(r0)
        L_0x01ac:
            r3 = -1
            r0 = 0
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.Object[] r0 = r7.toArray(r0)
            r4 = r0
            java.lang.String[] r4 = (java.lang.String[]) r4
            r0 = r37
            r1 = r38
            r2 = r15
            r27 = r5
            r5 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5)
            return r0
        L_0x01c5:
            r27 = r5
            r7 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x01d2
            if (r14 != r2) goto L_0x01ea
        L_0x01d2:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.abbreviatedDayPeriods
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.abbreviatedDayPeriods
            int r4 = r0.length
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x01ea
            return r7
        L_0x01ea:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x01f5
            r0 = 4
            if (r14 != r0) goto L_0x020d
        L_0x01f5:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.wideDayPeriods
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.wideDayPeriods
            int r4 = r0.length
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x020d
            return r7
        L_0x020d:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0218
            r5 = 4
            if (r14 != r5) goto L_0x0230
        L_0x0218:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.narrowDayPeriods
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.narrowDayPeriods
            int r4 = r0.length
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x0230
            return r7
        L_0x0230:
            return r7
        L_0x0231:
            r27 = r5
            r5 = 4
            r3 = 97
            r0 = r37
            r1 = r38
            r7 = r2
            r2 = r15
            r4 = r41
            r17 = r5
            r5 = r42
            r28 = r6
            r6 = r43
            r29 = r26
            r7 = r44
            r8 = r45
            r30 = r9
            r9 = r46
            r31 = r10
            r10 = r47
            r11 = r48
            int r6 = r0.subParse(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            if (r6 <= 0) goto L_0x025d
            return r6
        L_0x025d:
            r7 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0269
            r8 = 3
            if (r14 != r8) goto L_0x027d
        L_0x0269:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.abbreviatedDayPeriods
            r4 = 2
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x027d
            return r7
        L_0x027d:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0289
            r9 = 4
            if (r14 != r9) goto L_0x029e
            goto L_0x028a
        L_0x0289:
            r9 = 4
        L_0x028a:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.wideDayPeriods
            r4 = 2
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x029e
            return r7
        L_0x029e:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x02a8
            if (r14 != r9) goto L_0x02bc
        L_0x02a8:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r3 = r0.narrowDayPeriods
            r4 = 2
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r48
            int r0 = r0.matchDayPeriodString(r1, r2, r3, r4, r5)
            r7 = r0
            if (r0 <= 0) goto L_0x02bc
            return r7
        L_0x02bc:
            return r7
        L_0x02bd:
            r27 = r5
            r28 = r6
            r30 = r9
            r31 = r10
            r29 = r26
            switch(r14) {
                case 1: goto L_0x02d6;
                case 2: goto L_0x02d3;
                case 3: goto L_0x02d0;
                case 4: goto L_0x02cd;
                default: goto L_0x02ca;
            }
        L_0x02ca:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL
            goto L_0x02d9
        L_0x02cd:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL
            goto L_0x02d9
        L_0x02d0:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED
            goto L_0x02d9
        L_0x02d3:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED
            goto L_0x02d9
        L_0x02d6:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT
        L_0x02d9:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            r11 = r30
            r10 = r47
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x02f2
            r8 = r45
            r8.setTimeZone(r1)
            int r2 = r11.getIndex()
            return r2
        L_0x02f2:
            r8 = r45
            int r2 = ~r15
            return r2
        L_0x02f6:
            r27 = r5
            r28 = r6
            r11 = r9
            r31 = r10
            r29 = r26
            r10 = r8
            r8 = r45
            switch(r14) {
                case 1: goto L_0x0311;
                case 2: goto L_0x030e;
                case 3: goto L_0x030b;
                case 4: goto L_0x0308;
                default: goto L_0x0305;
            }
        L_0x0305:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL
            goto L_0x0314
        L_0x0308:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FULL
            goto L_0x0314
        L_0x030b:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FIXED
            goto L_0x0314
        L_0x030e:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_FIXED
            goto L_0x0314
        L_0x0311:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_SHORT
        L_0x0314:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x0327
            r8.setTimeZone(r1)
            int r2 = r11.getIndex()
            return r2
        L_0x0327:
            int r2 = ~r15
            return r2
        L_0x0329:
            r27 = r5
            r28 = r6
            r11 = r9
            r31 = r10
            r29 = r26
            r9 = 4
            r10 = r8
            r8 = r45
            if (r14 >= r9) goto L_0x033b
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT_SHORT
            goto L_0x033d
        L_0x033b:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT
        L_0x033d:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x034f
            r8.setTimeZone(r1)
            int r2 = r11.getIndex()
            return r2
        L_0x034f:
            int r2 = ~r15
            return r2
        L_0x0351:
            r27 = r5
            r28 = r6
            r11 = r9
            r31 = r10
            r29 = r26
            r10 = r8
            r8 = r45
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.shortYearNames
            if (r0 == 0) goto L_0x0377
            r3 = 1
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.shortYearNames
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            if (r0 <= 0) goto L_0x0377
            return r0
        L_0x0377:
            if (r18 == 0) goto L_0x039b
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0391
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.shortYearNames
            if (r0 == 0) goto L_0x0391
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.shortYearNames
            int r0 = r0.length
            r6 = r28
            if (r6 <= r0) goto L_0x039d
            goto L_0x0393
        L_0x0391:
            r6 = r28
        L_0x0393:
            r8.set(r7, r6)
            int r0 = r11.getIndex()
            return r0
        L_0x039b:
            r6 = r28
        L_0x039d:
            int r0 = ~r15
            return r0
        L_0x039f:
            r27 = r5
            r11 = r9
            r31 = r10
            r29 = r26
            r10 = r8
            r8 = r45
            r0 = 0
            switch(r14) {
                case 1: goto L_0x03b6;
                case 2: goto L_0x03b3;
                case 3: goto L_0x03b0;
                default: goto L_0x03ad;
            }
        L_0x03ad:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LOCATION
            goto L_0x03b9
        L_0x03b0:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.EXEMPLAR_LOCATION
            goto L_0x03b9
        L_0x03b3:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID
            goto L_0x03b9
        L_0x03b6:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ZONE_ID_SHORT
        L_0x03b9:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x03cb
            r8.setTimeZone(r1)
            int r2 = r11.getIndex()
            return r2
        L_0x03cb:
            int r2 = ~r15
            return r2
        L_0x03cd:
            r27 = r5
            r11 = r9
            r31 = r10
            r29 = r26
            r7 = r45
            r9 = 4
            r10 = r8
            r8 = r2
            r5 = 2
            if (r14 <= r5) goto L_0x0425
            if (r18 == 0) goto L_0x03e7
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x03e7
            goto L_0x0425
        L_0x03e7:
            r17 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x03f3
            if (r14 != r9) goto L_0x0408
        L_0x03f3:
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneQuarters
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r45
            int r0 = r0.matchQuarterString(r1, r2, r3, r4, r5)
            r17 = r0
            if (r0 <= 0) goto L_0x0408
            return r17
        L_0x0408:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0414
            if (r14 != r8) goto L_0x0413
            goto L_0x0414
        L_0x0413:
            return r17
        L_0x0414:
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneShortQuarters
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r45
            int r0 = r0.matchQuarterString(r1, r2, r3, r4, r5)
            return r0
        L_0x0425:
            int r0 = r6 + -1
            int r0 = r0 * r8
            r7.set(r5, r0)
            int r0 = r11.getIndex()
            return r0
        L_0x0430:
            r27 = r5
            r11 = r9
            r31 = r10
            r29 = r26
            r5 = 2
            r7 = r45
            r9 = 4
            r10 = r8
            r8 = r2
            if (r14 <= r5) goto L_0x0488
            if (r18 == 0) goto L_0x044a
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x044a
            goto L_0x0488
        L_0x044a:
            r17 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0456
            if (r14 != r9) goto L_0x046b
        L_0x0456:
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.quarters
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r45
            int r0 = r0.matchQuarterString(r1, r2, r3, r4, r5)
            r17 = r0
            if (r0 <= 0) goto L_0x046b
            return r17
        L_0x046b:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0477
            if (r14 != r8) goto L_0x0476
            goto L_0x0477
        L_0x0476:
            return r17
        L_0x0477:
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.shortQuarters
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r45
            int r0 = r0.matchQuarterString(r1, r2, r3, r4, r5)
            return r0
        L_0x0488:
            int r0 = r6 + -1
            int r0 = r0 * r8
            r7.set(r5, r0)
            int r0 = r11.getIndex()
            return r0
        L_0x0493:
            r27 = r5
            r11 = r9
            r31 = r10
            r29 = r26
            r5 = r45
            r9 = 4
            r10 = r8
            r8 = r2
            if (r14 == r7) goto L_0x0527
            if (r18 == 0) goto L_0x04b0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x04b0
            r9 = r5
            r33 = r6
            goto L_0x052a
        L_0x04b0:
            r7 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x04c3
            if (r14 != r9) goto L_0x04bc
            goto L_0x04c3
        L_0x04bc:
            r9 = r5
            r33 = r6
            r17 = r7
            r7 = r4
            goto L_0x04e4
        L_0x04c3:
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r9 = r0.standaloneWeekdays
            r17 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r32 = r7
            r7 = r4
            r4 = r9
            r9 = r5
            r5 = r17
            r33 = r6
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r1 = r0
            if (r0 <= 0) goto L_0x04e2
            return r1
        L_0x04e2:
            r17 = r1
        L_0x04e4:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x04ee
            if (r14 != r8) goto L_0x0504
        L_0x04ee:
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneShortWeekdays
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x0504
            return r17
        L_0x0504:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x050e
            if (r14 != r7) goto L_0x0526
        L_0x050e:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.standaloneShorterWeekdays
            if (r0 == 0) goto L_0x0526
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneShorterWeekdays
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            return r0
        L_0x0526:
            return r17
        L_0x0527:
            r9 = r5
            r33 = r6
        L_0x052a:
            r6 = r29
            r4 = r33
            r9.set(r6, r4)
            int r0 = r11.getIndex()
            return r0
        L_0x0536:
            r27 = r5
            r4 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r2 = r45
            r9 = 4
            r10 = r8
            if (r14 >= r9) goto L_0x0547
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_SHORT
            goto L_0x0549
        L_0x0547:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.GENERIC_LONG
        L_0x0549:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x055b
            r2.setTimeZone(r1)
            int r3 = r11.getIndex()
            return r3
        L_0x055b:
            int r3 = ~r15
            return r3
        L_0x055d:
            r27 = r5
            r4 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r2 = r45
            r9 = 4
            r10 = r8
            if (r14 >= r9) goto L_0x056e
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL
            goto L_0x0575
        L_0x056e:
            if (r14 != r3) goto L_0x0573
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.ISO_EXTENDED_FULL
            goto L_0x0575
        L_0x0573:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.LOCALIZED_GMT
        L_0x0575:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x0587
            r2.setTimeZone(r1)
            int r3 = r11.getIndex()
            return r3
        L_0x0587:
            int r3 = ~r15
            return r3
        L_0x0589:
            r7 = r4
            r27 = r5
            r4 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r5 = 2
            r9 = 4
            r10 = r8
            r8 = r2
            r2 = r45
            if (r14 <= r5) goto L_0x05aa
            if (r18 == 0) goto L_0x05a5
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x05a5
            goto L_0x05aa
        L_0x05a5:
            r5 = r4
            r4 = r7
            r7 = r3
            goto L_0x0671
        L_0x05aa:
            r2.set(r6, r4)
            int r0 = r11.getIndex()
            return r0
        L_0x05b2:
            r27 = r5
            r4 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r2 = r45
            r9 = 4
            r10 = r8
            if (r14 >= r9) goto L_0x05c3
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_SHORT
            goto L_0x05c5
        L_0x05c3:
            com.ibm.icu.text.TimeZoneFormat$Style r0 = com.ibm.icu.text.TimeZoneFormat.Style.SPECIFIC_LONG
        L_0x05c5:
            com.ibm.icu.text.TimeZoneFormat r1 = r37.tzFormat()
            com.ibm.icu.util.TimeZone r1 = r1.parse(r0, r13, r11, r10)
            if (r1 == 0) goto L_0x05d7
            r2.setTimeZone(r1)
            int r3 = r11.getIndex()
            return r3
        L_0x05d7:
            int r3 = ~r15
            return r3
        L_0x05d9:
            r27 = r5
            r4 = r6
            r3 = r7
            r11 = r9
            r31 = r10
            r6 = r26
            r2 = r45
            r10 = r8
            r0 = 10
            int r1 = r2.getLeastMaximum(r0)
            int r1 = r1 + r3
            if (r4 != r1) goto L_0x05f0
            r1 = 0
            r4 = r1
        L_0x05f0:
            r2.set(r0, r4)
            int r0 = r11.getIndex()
            return r0
        L_0x05f8:
            r27 = r5
            r4 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r2 = r45
            r10 = r8
            r7 = 0
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.ampmsNarrow
            if (r0 == 0) goto L_0x061b
            if (r14 < r3) goto L_0x061b
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x0615
            goto L_0x061b
        L_0x0615:
            r8 = r4
            r9 = r6
            r17 = r7
            r7 = r3
            goto L_0x063c
        L_0x061b:
            r5 = 9
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r8 = r0.ampms
            r9 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r34 = r7
            r7 = r3
            r3 = r5
            r5 = r4
            r4 = r8
            r8 = r5
            r5 = r9
            r9 = r6
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r1 = r0
            if (r0 <= 0) goto L_0x063a
            return r1
        L_0x063a:
            r17 = r1
        L_0x063c:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.ampmsNarrow
            if (r0 == 0) goto L_0x0663
            if (r14 >= r7) goto L_0x064c
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x0663
        L_0x064c:
            r3 = 9
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.ampmsNarrow
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x0663
            return r17
        L_0x0663:
            int r0 = ~r15
            return r0
        L_0x0665:
            r7 = r3
            r27 = r5
            r5 = r6
            r11 = r9
            r31 = r10
            r6 = r26
            r9 = 4
            r10 = r8
            r8 = r2
        L_0x0671:
            r17 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x0683
            if (r14 != r9) goto L_0x067e
            goto L_0x0683
        L_0x067e:
            r7 = r4
            r9 = r5
            r35 = r6
            goto L_0x06a1
        L_0x0683:
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r9 = r0.weekdays
            r19 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r7 = r4
            r4 = r9
            r9 = r5
            r5 = r19
            r35 = r6
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x06a1
            return r17
        L_0x06a1:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x06ab
            if (r14 != r8) goto L_0x06c1
        L_0x06ab:
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.shortWeekdays
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x06c1
            return r17
        L_0x06c1:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x06cb
            if (r14 != r7) goto L_0x06e7
        L_0x06cb:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.shorterWeekdays
            if (r0 == 0) goto L_0x06e7
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.shorterWeekdays
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x06e7
            return r17
        L_0x06e7:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x06f2
            r0 = 5
            if (r14 != r0) goto L_0x070e
        L_0x06f2:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.narrowWeekdays
            if (r0 == 0) goto L_0x070e
            r3 = 7
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.narrowWeekdays
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            r17 = r0
            if (r0 <= 0) goto L_0x070e
            return r17
        L_0x070e:
            return r17
        L_0x070f:
            r27 = r5
            r3 = r7
            r11 = r9
            r31 = r10
            r35 = r26
            r9 = r6
            r10 = r8
            r8 = r2
            int r0 = r11.getIndex()
            int r0 = countDigits(r13, r15, r0)
            if (r0 >= r8) goto L_0x072b
        L_0x0724:
            if (r0 >= r8) goto L_0x0735
            int r9 = r9 * 10
            int r0 = r0 + 1
            goto L_0x0724
        L_0x072b:
        L_0x072c:
            r1 = r3
            if (r0 <= r8) goto L_0x0734
            int r3 = r1 * 10
            int r0 = r0 + -1
            goto L_0x072c
        L_0x0734:
            int r9 = r9 / r1
        L_0x0735:
            r1 = 14
            r6 = r45
            r6.set(r1, r9)
            int r1 = r11.getIndex()
            return r1
        L_0x0741:
            r27 = r5
            r3 = r7
            r11 = r9
            r31 = r10
            r35 = r26
            r9 = r6
            r10 = r8
            r6 = r45
            r0 = 11
            int r1 = r6.getMaximum(r0)
            int r1 = r1 + r3
            if (r9 != r1) goto L_0x0758
            r1 = 0
            r9 = r1
        L_0x0758:
            r6.set(r0, r9)
            int r0 = r11.getIndex()
            return r0
        L_0x0760:
            r27 = r5
            r3 = r7
            r11 = r9
            r31 = r10
            r35 = r26
            r5 = 2
            r9 = 4
            r7 = r4
            r4 = r6
            r10 = r8
            r6 = r45
            r8 = r2
            if (r14 <= r5) goto L_0x084d
            if (r18 == 0) goto L_0x0784
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 == 0) goto L_0x0784
            r1 = r3
            r36 = r4
            r0 = r5
            r9 = r31
            goto L_0x0853
        L_0x0784:
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            if (r0 == 0) goto L_0x0794
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            int r0 = r0.length
            r1 = 7
            if (r0 < r1) goto L_0x0794
            r0 = r3
            goto L_0x0795
        L_0x0794:
            r0 = 0
        L_0x0795:
            r7 = r0
            r19 = 0
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            r20 = 0
            if (r0 != 0) goto L_0x07aa
            if (r14 != r9) goto L_0x07a5
            goto L_0x07aa
        L_0x07a5:
            r36 = r4
            r9 = r31
            goto L_0x0800
        L_0x07aa:
            r2 = r31
            if (r2 != r5) goto L_0x07db
            r21 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r1 = r0.months
            if (r7 == 0) goto L_0x07c1
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            r17 = 0
            r0 = r0[r17]
            r17 = r0
            goto L_0x07c4
        L_0x07c1:
            r17 = r20
        L_0x07c4:
            r0 = r37
            r22 = r1
            r1 = r38
            r9 = r2
            r2 = r15
            r3 = r21
            r36 = r4
            r4 = r22
            r5 = r17
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            goto L_0x07fb
        L_0x07db:
            r9 = r2
            r36 = r4
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneMonths
            if (r7 == 0) goto L_0x07ed
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            r0 = r0[r8]
            r5 = r0
            goto L_0x07f0
        L_0x07ed:
            r5 = r20
        L_0x07f0:
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
        L_0x07fb:
            r19 = r0
            if (r19 <= 0) goto L_0x0800
            return r19
        L_0x0800:
            com.ibm.icu.text.DateFormat$BooleanAttribute r0 = com.ibm.icu.text.DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH
            boolean r0 = r12.getBooleanAttribute(r0)
            if (r0 != 0) goto L_0x080c
            if (r14 != r8) goto L_0x080b
            goto L_0x080c
        L_0x080b:
            return r19
        L_0x080c:
            r0 = 2
            if (r9 != r0) goto L_0x082e
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.shortMonths
            if (r7 == 0) goto L_0x081f
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            r1 = 1
            r0 = r0[r1]
            r5 = r0
            goto L_0x0822
        L_0x081f:
            r5 = r20
        L_0x0822:
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            goto L_0x084c
        L_0x082e:
            r3 = 2
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.standaloneShortMonths
            if (r7 == 0) goto L_0x083e
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r0 = r0.leapMonthPatterns
            r1 = 4
            r0 = r0[r1]
            r5 = r0
            goto L_0x0841
        L_0x083e:
            r5 = r20
        L_0x0841:
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
        L_0x084c:
            return r0
        L_0x084d:
            r1 = r3
            r36 = r4
            r0 = r5
            r9 = r31
        L_0x0853:
            r8 = r36
            int r6 = r8 + -1
            r5 = r45
            r5.set(r0, r6)
            java.lang.String r2 = r45.getType()
            java.lang.String r3 = "hebrew"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0880
            if (r8 < r7) goto L_0x0880
            boolean r2 = r5.isSet(r1)
            if (r2 == 0) goto L_0x087e
            int r1 = r5.get(r1)
            boolean r1 = com.ibm.icu.util.HebrewCalendar.isLeapYear(r1)
            if (r1 != 0) goto L_0x0880
            r5.set(r0, r8)
            goto L_0x0880
        L_0x087e:
            DelayedHebrewMonthCheck = r1
        L_0x0880:
            int r0 = r11.getIndex()
            return r0
        L_0x0885:
            r27 = r5
            r1 = r7
            r11 = r9
            r9 = r10
            r35 = r26
            r0 = 2
            r5 = r45
            r10 = r8
            r8 = r6
            java.lang.String r2 = r12.override
            if (r2 == 0) goto L_0x08b0
            java.lang.String r2 = r12.override
            java.lang.String r3 = "hebr"
            int r2 = r2.compareTo(r3)
            if (r2 == 0) goto L_0x08a9
            java.lang.String r2 = r12.override
            java.lang.String r3 = "y=hebr"
            int r2 = r2.indexOf(r3)
            if (r2 < 0) goto L_0x08b0
        L_0x08a9:
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r8 >= r2) goto L_0x08b0
            int r6 = r8 + 5000
            goto L_0x08e0
        L_0x08b0:
            if (r14 != r0) goto L_0x08df
            int r2 = r11.getIndex()
            int r2 = countDigits(r13, r15, r2)
            if (r2 != r0) goto L_0x08df
            boolean r2 = r45.haveDefaultCentury()
            if (r2 == 0) goto L_0x08df
            int r2 = r37.getDefaultCenturyStartYear()
            r7 = 100
            int r2 = r2 % r7
            if (r8 != r2) goto L_0x08cd
            r3 = r1
            goto L_0x08ce
        L_0x08cd:
            r3 = 0
        L_0x08ce:
            r4 = 0
            r44[r4] = r3
            int r3 = r37.getDefaultCenturyStartYear()
            int r3 = r3 / r7
            int r3 = r3 * r7
            if (r8 >= r2) goto L_0x08da
            goto L_0x08db
        L_0x08da:
            r7 = 0
        L_0x08db:
            int r3 = r3 + r7
            int r6 = r8 + r3
            goto L_0x08e0
        L_0x08df:
            r6 = r8
        L_0x08e0:
            r7 = r35
            r5.set(r7, r6)
            boolean r2 = DelayedHebrewMonthCheck
            if (r2 == 0) goto L_0x08f5
            boolean r2 = com.ibm.icu.util.HebrewCalendar.isLeapYear(r6)
            if (r2 != 0) goto L_0x08f2
            r5.add(r0, r1)
        L_0x08f2:
            r0 = 0
            DelayedHebrewMonthCheck = r0
        L_0x08f5:
            int r0 = r11.getIndex()
            return r0
        L_0x08fa:
            r27 = r5
            r11 = r9
            r9 = r10
            r7 = r26
            r0 = 0
            r5 = r45
            r10 = r8
            r8 = r6
            if (r16 == 0) goto L_0x090f
            r5.set(r0, r8)
            int r0 = r11.getIndex()
            return r0
        L_0x090f:
            r17 = 0
            r0 = 5
            if (r14 != r0) goto L_0x0927
            r3 = 0
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.narrowEras
            r6 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r5 = r6
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            goto L_0x094d
        L_0x0927:
            r0 = 4
            if (r14 != r0) goto L_0x093c
            r3 = 0
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.eraNames
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
            goto L_0x094d
        L_0x093c:
            r3 = 0
            com.ibm.icu.text.DateFormatSymbols r0 = r12.formatData
            java.lang.String[] r4 = r0.eras
            r5 = 0
            r0 = r37
            r1 = r38
            r2 = r15
            r6 = r45
            int r0 = r0.matchString(r1, r2, r3, r4, r5, r6)
        L_0x094d:
            int r1 = ~r15
            if (r0 != r1) goto L_0x0952
            r0 = -32000(0xffffffffffff8300, float:NaN)
        L_0x0952:
            return r0
        L_0x0953:
            r0 = r37
            r1 = r38
            r2 = r41
            r3 = r11
            r4 = r43
            r5 = r27
            java.lang.Number r0 = r0.parseInt(r1, r2, r3, r4, r5)
            r2 = r0
            r1 = r27
            r0 = r43
            goto L_0x0970
        L_0x0968:
            r1 = r27
            r0 = r43
            java.lang.Number r2 = r12.parseInt(r13, r11, r0, r1)
        L_0x0970:
            if (r2 == 0) goto L_0x098e
            r3 = 34
            if (r9 == r3) goto L_0x0980
            int r3 = r2.intValue()
            r4 = r45
            r4.set(r7, r3)
            goto L_0x0989
        L_0x0980:
            r4 = r45
            int r3 = r2.intValue()
            r4.setRelatedYear(r3)
        L_0x0989:
            int r3 = r11.getIndex()
            return r3
        L_0x098e:
            r4 = r45
            int r3 = ~r15
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.subParse(java.lang.String, int, char, int, boolean, boolean, boolean[], com.ibm.icu.util.Calendar, com.ibm.icu.text.MessageFormat, com.ibm.icu.util.Output, com.ibm.icu.util.Output):int");
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
        if (maxDigits <= 0 || (nDigits = pos.getIndex() - oldPos) <= maxDigits) {
            return number;
        }
        double val = number.doubleValue();
        for (int nDigits2 = nDigits - maxDigits; nDigits2 > 0; nDigits2--) {
            val /= 10.0d;
        }
        pos.setIndex(oldPos + maxDigits);
        return Integer.valueOf((int) val);
    }

    private static int countDigits(String text, int start, int end) {
        int numDigits = 0;
        int idx = start;
        while (idx < end) {
            int cp = text.codePointAt(idx);
            if (UCharacter.isDigit(cp)) {
                numDigits++;
            }
            idx += UCharacter.charCount(cp);
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
        if (!inQuote) {
            return result.toString();
        }
        throw new IllegalArgumentException("Unfinished quote in pattern");
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
        setLocale((ULocale) null, (ULocale) null);
        this.patternItems = null;
    }

    public void applyLocalizedPattern(String pat) {
        this.pattern = translatePattern(pat, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
        setLocale((ULocale) null, (ULocale) null);
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols) this.formatData.clone();
    }

    public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
        this.formatData = (DateFormatSymbols) newFormatSymbols.clone();
    }

    /* access modifiers changed from: protected */
    public DateFormatSymbols getSymbols() {
        return this.formatData;
    }

    public TimeZoneFormat getTimeZoneFormat() {
        return tzFormat().freeze();
    }

    public void setTimeZoneFormat(TimeZoneFormat tzfmt) {
        if (tzfmt.isFrozen()) {
            this.tzFormat = tzfmt;
        } else {
            this.tzFormat = tzfmt.cloneAsThawed().freeze();
        }
    }

    public Object clone() {
        SimpleDateFormat other = (SimpleDateFormat) super.clone();
        other.formatData = (DateFormatSymbols) this.formatData.clone();
        if (this.decimalBuf != null) {
            other.decimalBuf = new char[10];
        }
        return other;
    }

    public int hashCode() {
        return this.pattern.hashCode();
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        SimpleDateFormat that = (SimpleDateFormat) obj;
        if (!this.pattern.equals(that.pattern) || !this.formatData.equals(that.formatData)) {
            return false;
        }
        return true;
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
                if (context.value() == capitalizationSettingValue) {
                    setContext(context);
                    break;
                }
                i++;
            }
        }
        if (!getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, false);
        }
        parsePattern();
    }

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
        ArrayList arrayList = new ArrayList();
        format(cal, getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, arrayList);
        AttributedString as = new AttributedString(toAppendTo.toString());
        for (int i = 0; i < arrayList.size(); i++) {
            FieldPosition fp = (FieldPosition) arrayList.get(i);
            Format.Field attribute = fp.getFieldAttribute();
            as.addAttribute(attribute, attribute, fp.getBeginIndex(), fp.getEndIndex());
        }
        return as.getIterator();
    }

    /* access modifiers changed from: package-private */
    public ULocale getLocale() {
        return this.locale;
    }

    /* access modifiers changed from: package-private */
    public boolean isFieldUnitIgnored(int field) {
        return isFieldUnitIgnored(this.pattern, field);
    }

    static boolean isFieldUnitIgnored(String pattern2, int field) {
        int fieldLevel = CALENDAR_FIELD_TO_LEVEL[field];
        char prevCh = 0;
        int count = 0;
        boolean inQuote = false;
        int i = 0;
        while (i < pattern2.length()) {
            char ch = pattern2.charAt(i);
            if (ch != prevCh && count > 0) {
                if (fieldLevel <= getLevelFromChar(prevCh)) {
                    return false;
                }
                count = 0;
            }
            if (ch == '\'') {
                if (i + 1 >= pattern2.length() || pattern2.charAt(i + 1) != '\'') {
                    inQuote = !inQuote;
                } else {
                    i++;
                }
            } else if (!inQuote && isSyntaxChar(ch)) {
                prevCh = ch;
                count++;
            }
            i++;
        }
        return count <= 0 || fieldLevel > getLevelFromChar(prevCh);
    }

    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) throws IllegalArgumentException {
        int highestLevel;
        int i;
        int diffEnd;
        SimpleDateFormat simpleDateFormat = this;
        Calendar calendar = fromCalendar;
        Calendar calendar2 = toCalendar;
        StringBuffer stringBuffer = appendTo;
        FieldPosition fieldPosition = pos;
        if (fromCalendar.isEquivalentTo(toCalendar)) {
            Object[] items = getPatternItems();
            int diffBegin = -1;
            int diffEnd2 = -1;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                try {
                    if (i3 >= items.length) {
                        break;
                    } else if (simpleDateFormat.diffCalFieldValue(calendar, calendar2, items, i3)) {
                        diffBegin = i3;
                        break;
                    } else {
                        i3++;
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(e.toString());
                }
            }
            if (diffBegin == -1) {
                return simpleDateFormat.format(calendar, stringBuffer, fieldPosition);
            }
            int i4 = items.length - 1;
            while (true) {
                if (i4 < diffBegin) {
                    break;
                } else if (simpleDateFormat.diffCalFieldValue(calendar, calendar2, items, i4)) {
                    diffEnd2 = i4;
                    break;
                } else {
                    i4--;
                }
            }
            if (diffBegin == 0 && diffEnd2 == items.length - 1) {
                simpleDateFormat.format(calendar, stringBuffer, fieldPosition);
                stringBuffer.append(" – ");
                simpleDateFormat.format(calendar2, stringBuffer, fieldPosition);
                return stringBuffer;
            }
            int highestLevel2 = 1000;
            for (int i5 = diffBegin; i5 <= diffEnd2; i5++) {
                if (!(items[i5] instanceof String)) {
                    char ch = ((PatternItem) items[i5]).type;
                    int patternCharIndex = getIndexFromChar(ch);
                    if (patternCharIndex == -1) {
                        throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + simpleDateFormat.pattern + '\"');
                    } else if (patternCharIndex < highestLevel2) {
                        highestLevel2 = patternCharIndex;
                    }
                }
            }
            int i6 = 0;
            while (true) {
                if (i6 >= diffBegin) {
                    break;
                }
                try {
                    if (simpleDateFormat.lowerLevel(items, i6, highestLevel2)) {
                        diffBegin = i6;
                        break;
                    }
                    i6++;
                } catch (IllegalArgumentException e2) {
                    e = e2;
                    int i7 = diffBegin;
                    throw new IllegalArgumentException(e.toString());
                }
            }
            int diffBegin2 = diffBegin;
            try {
                int i8 = items.length - 1;
                while (true) {
                    if (i8 <= diffEnd2) {
                        i8 = diffEnd2;
                        break;
                    }
                    try {
                        if (simpleDateFormat.lowerLevel(items, i8, highestLevel2)) {
                            int diffEnd3 = i8;
                            break;
                        }
                        i8--;
                    } catch (IllegalArgumentException e3) {
                        e = e3;
                        throw new IllegalArgumentException(e.toString());
                    }
                }
                if (diffBegin2 == 0 && i8 == items.length - 1) {
                    simpleDateFormat.format(calendar, stringBuffer, fieldPosition);
                    stringBuffer.append(" – ");
                    simpleDateFormat.format(calendar2, stringBuffer, fieldPosition);
                    return stringBuffer;
                }
                fieldPosition.setBeginIndex(0);
                fieldPosition.setEndIndex(0);
                DisplayContext capSetting = simpleDateFormat.getContext(DisplayContext.Type.CAPITALIZATION);
                while (true) {
                    int i9 = i2;
                    if (i9 > i8) {
                        break;
                    }
                    if (items[i9] instanceof String) {
                        stringBuffer.append((String) items[i9]);
                        diffEnd = i8;
                        i = i9;
                        highestLevel = highestLevel2;
                    } else {
                        PatternItem item = (PatternItem) items[i9];
                        if (simpleDateFormat.useFastFormat) {
                            diffEnd = i8;
                            PatternItem patternItem = item;
                            i = i9;
                            highestLevel = highestLevel2;
                            subFormat(appendTo, item.type, item.length, appendTo.length(), i9, capSetting, pos, fromCalendar);
                        } else {
                            diffEnd = i8;
                            PatternItem item2 = item;
                            i = i9;
                            highestLevel = highestLevel2;
                            stringBuffer.append(subFormat(item2.type, item2.length, appendTo.length(), i, capSetting, pos, fromCalendar));
                        }
                    }
                    i2 = i + 1;
                    i8 = diffEnd;
                    highestLevel2 = highestLevel;
                }
                int diffEnd4 = i8;
                int i10 = highestLevel2;
                stringBuffer.append(" – ");
                int i11 = diffBegin2;
                while (i11 < items.length) {
                    if (items[i11] instanceof String) {
                        stringBuffer.append((String) items[i11]);
                    } else {
                        PatternItem item3 = (PatternItem) items[i11];
                        if (simpleDateFormat.useFastFormat) {
                            PatternItem patternItem2 = item3;
                            subFormat(appendTo, item3.type, item3.length, appendTo.length(), i11, capSetting, pos, toCalendar);
                        } else {
                            PatternItem item4 = item3;
                            stringBuffer.append(subFormat(item4.type, item4.length, appendTo.length(), i11, capSetting, pos, toCalendar));
                        }
                    }
                    i11++;
                    simpleDateFormat = this;
                }
                return stringBuffer;
            } catch (IllegalArgumentException e4) {
                e = e4;
                int i12 = highestLevel2;
                throw new IllegalArgumentException(e.toString());
            }
        } else {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
    }

    private boolean diffCalFieldValue(Calendar fromCalendar, Calendar toCalendar, Object[] items, int i) throws IllegalArgumentException {
        if (items[i] instanceof String) {
            return false;
        }
        char ch = items[i].type;
        int patternCharIndex = getIndexFromChar(ch);
        if (patternCharIndex != -1) {
            int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
            if (field < 0 || fromCalendar.get(field) == toCalendar.get(field)) {
                return false;
            }
            return true;
        }
        throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '\"');
    }

    private boolean lowerLevel(Object[] items, int i, int level) throws IllegalArgumentException {
        if (items[i] instanceof String) {
            return false;
        }
        char ch = items[i].type;
        int patternCharIndex = getLevelFromChar(ch);
        if (patternCharIndex == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '\"');
        } else if (patternCharIndex >= level) {
            return true;
        } else {
            return false;
        }
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
        int i = 0;
        while (i < fields.length()) {
            char field = fields.charAt(i);
            if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(field) != -1) {
                this.overrideMap.put(Character.valueOf(field), nsName);
                this.numberFormatters.put(nsName, overrideNF);
                i++;
            } else {
                throw new IllegalArgumentException("Illegal field character '" + field + "' in setNumberFormat.");
            }
        }
        this.useLocalZeroPaddingNumberFormat = false;
    }

    public NumberFormat getNumberFormat(char field) {
        Character ovrField = Character.valueOf(field);
        if (this.overrideMap == null || !this.overrideMap.containsKey(ovrField)) {
            return this.numberFormat;
        }
        return this.numberFormatters.get(this.overrideMap.get(ovrField).toString());
    }

    private void initNumberFormatters(ULocale loc) {
        this.numberFormatters = new HashMap<>();
        this.overrideMap = new HashMap<>();
        processOverrideString(loc, this.override);
    }

    private void processOverrideString(ULocale loc, String str) {
        int end;
        boolean fullOverride;
        String nsName;
        if (str != null && str.length() != 0) {
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
                    this.overrideMap.put(Character.valueOf(currentString.charAt(0)), nsName);
                    fullOverride = false;
                }
                NumberFormat nf = NumberFormat.createInstance(new ULocale(loc.getBaseName() + "@numbers=" + nsName), 0);
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
