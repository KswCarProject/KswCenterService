package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.text.DateIntervalInfo;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.DateInterval;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes5.dex */
public class DateIntervalFormat extends UFormat {
    private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache();
    private static final long serialVersionUID = 1;
    private SimpleDateFormat fDateFormat;
    private String fDatePattern;
    private String fDateTimeFormat;
    private Calendar fFromCalendar;
    private DateIntervalInfo fInfo;
    private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns;
    private String fSkeleton;
    private String fTimePattern;
    private Calendar fToCalendar;
    private boolean isDateIntervalInfoDefault;

    /* loaded from: classes5.dex */
    static final class BestMatchInfo {
        final int bestMatchDistanceInfo;
        final String bestMatchSkeleton;

        BestMatchInfo(String bestSkeleton, int difference) {
            this.bestMatchSkeleton = bestSkeleton;
            this.bestMatchDistanceInfo = difference;
        }
    }

    /* loaded from: classes5.dex */
    private static final class SkeletonAndItsBestMatch {
        final String bestMatchSkeleton;
        final String skeleton;

        SkeletonAndItsBestMatch(String skeleton, String bestMatch) {
            this.skeleton = skeleton;
            this.bestMatchSkeleton = bestMatch;
        }
    }

    private DateIntervalFormat() {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
    }

    @Deprecated
    public DateIntervalFormat(String skeleton, DateIntervalInfo dtItvInfo, SimpleDateFormat simpleDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
        this.fDateFormat = simpleDateFormat;
        dtItvInfo.m200freeze();
        this.fSkeleton = skeleton;
        this.fInfo = dtItvInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        initializePattern(null);
    }

    private DateIntervalFormat(String skeleton, ULocale locale, SimpleDateFormat simpleDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
        this.fDateFormat = simpleDateFormat;
        this.fSkeleton = skeleton;
        this.fInfo = new DateIntervalInfo(locale).m200freeze();
        this.isDateIntervalInfoDefault = true;
        this.fFromCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        initializePattern(LOCAL_PATTERN_CACHE);
    }

    public static final DateIntervalFormat getInstance(String skeleton) {
        return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateIntervalFormat getInstance(String skeleton, Locale locale) {
        return getInstance(skeleton, ULocale.forLocale(locale));
    }

    public static final DateIntervalFormat getInstance(String skeleton, ULocale locale) {
        DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
        return new DateIntervalFormat(skeleton, locale, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
    }

    public static final DateIntervalFormat getInstance(String skeleton, DateIntervalInfo dtitvinf) {
        return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT), dtitvinf);
    }

    public static final DateIntervalFormat getInstance(String skeleton, Locale locale, DateIntervalInfo dtitvinf) {
        return getInstance(skeleton, ULocale.forLocale(locale), dtitvinf);
    }

    public static final DateIntervalFormat getInstance(String skeleton, ULocale locale, DateIntervalInfo dtitvinf) {
        DateIntervalInfo dtitvinf2 = (DateIntervalInfo) dtitvinf.clone();
        DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
        return new DateIntervalFormat(skeleton, dtitvinf2, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
    }

    @Override // java.text.Format
    public synchronized Object clone() {
        DateIntervalFormat other;
        other = (DateIntervalFormat) super.clone();
        other.fDateFormat = (SimpleDateFormat) this.fDateFormat.clone();
        other.fInfo = (DateIntervalInfo) this.fInfo.clone();
        other.fFromCalendar = (Calendar) this.fFromCalendar.clone();
        other.fToCalendar = (Calendar) this.fToCalendar.clone();
        other.fDatePattern = this.fDatePattern;
        other.fTimePattern = this.fTimePattern;
        other.fDateTimeFormat = this.fDateTimeFormat;
        return other;
    }

    @Override // java.text.Format
    public final StringBuffer format(Object obj, StringBuffer appendTo, FieldPosition fieldPosition) {
        if (obj instanceof DateInterval) {
            return format((DateInterval) obj, appendTo, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a DateInterval");
    }

    public final synchronized StringBuffer format(DateInterval dtInterval, StringBuffer appendTo, FieldPosition fieldPosition) {
        this.fFromCalendar.setTimeInMillis(dtInterval.getFromDate());
        this.fToCalendar.setTimeInMillis(dtInterval.getToDate());
        return format(this.fFromCalendar, this.fToCalendar, appendTo, fieldPosition);
    }

    @Deprecated
    public String getPatterns(Calendar fromCalendar, Calendar toCalendar, Output<String> part2) {
        int field;
        if (fromCalendar.get(0) != toCalendar.get(0)) {
            field = 0;
        } else if (fromCalendar.get(1) != toCalendar.get(1)) {
            field = 1;
        } else if (fromCalendar.get(2) != toCalendar.get(2)) {
            field = 2;
        } else if (fromCalendar.get(5) != toCalendar.get(5)) {
            field = 5;
        } else if (fromCalendar.get(9) != toCalendar.get(9)) {
            field = 9;
        } else if (fromCalendar.get(10) != toCalendar.get(10)) {
            field = 10;
        } else if (fromCalendar.get(12) != toCalendar.get(12)) {
            field = 12;
        } else if (fromCalendar.get(13) != toCalendar.get(13)) {
            field = 13;
        } else {
            return null;
        }
        DateIntervalInfo.PatternInfo intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
        part2.value = intervalPattern.getSecondPart();
        return intervalPattern.getFirstPart();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009f A[Catch: all -> 0x014e, TryCatch #0 {, blocks: (B:4:0x000b, B:6:0x0011, B:38:0x008f, B:40:0x009f, B:42:0x00a7, B:46:0x00b9, B:49:0x00bf, B:51:0x00c6, B:55:0x00de, B:59:0x00ec, B:61:0x0106, B:63:0x0123, B:65:0x0129, B:66:0x0137, B:10:0x0029, B:13:0x0035, B:16:0x0042, B:19:0x004f, B:22:0x005c, B:25:0x0069, B:28:0x0076, B:69:0x013e, B:72:0x0146, B:73:0x014d), top: B:77:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00bf A[Catch: all -> 0x014e, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x000b, B:6:0x0011, B:38:0x008f, B:40:0x009f, B:42:0x00a7, B:46:0x00b9, B:49:0x00bf, B:51:0x00c6, B:55:0x00de, B:59:0x00ec, B:61:0x0106, B:63:0x0123, B:65:0x0129, B:66:0x0137, B:10:0x0029, B:13:0x0035, B:16:0x0042, B:19:0x004f, B:22:0x005c, B:25:0x0069, B:28:0x0076, B:69:0x013e, B:72:0x0146, B:73:0x014d), top: B:77:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized StringBuffer format(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) {
        int field;
        boolean fromToOnSameDay;
        DateIntervalInfo.PatternInfo intervalPattern;
        Calendar firstCal;
        Calendar secondCal;
        if (!fromCalendar.isEquivalentTo(toCalendar)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        if (fromCalendar.get(0) != toCalendar.get(0)) {
            field = 0;
        } else if (fromCalendar.get(1) != toCalendar.get(1)) {
            field = 1;
        } else if (fromCalendar.get(2) != toCalendar.get(2)) {
            field = 2;
        } else if (fromCalendar.get(5) != toCalendar.get(5)) {
            field = 5;
        } else if (fromCalendar.get(9) != toCalendar.get(9)) {
            field = 9;
        } else if (fromCalendar.get(10) != toCalendar.get(10)) {
            field = 10;
        } else if (fromCalendar.get(12) != toCalendar.get(12)) {
            field = 12;
        } else if (fromCalendar.get(13) != toCalendar.get(13)) {
            field = 13;
        } else {
            return this.fDateFormat.format(fromCalendar, appendTo, pos);
        }
        int field2 = field;
        if (field2 != 9 && field2 != 10 && field2 != 12 && field2 != 13) {
            fromToOnSameDay = false;
            intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field2]);
            if (intervalPattern != null) {
                if (this.fDateFormat.isFieldUnitIgnored(field2)) {
                    return this.fDateFormat.format(fromCalendar, appendTo, pos);
                }
                return fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos);
            } else if (intervalPattern.getFirstPart() == null) {
                return fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos, intervalPattern.getSecondPart());
            } else {
                if (intervalPattern.firstDateInPtnIsLaterDate()) {
                    firstCal = toCalendar;
                    secondCal = fromCalendar;
                } else {
                    firstCal = fromCalendar;
                    secondCal = toCalendar;
                }
                String originalPattern = this.fDateFormat.toPattern();
                this.fDateFormat.applyPattern(intervalPattern.getFirstPart());
                this.fDateFormat.format(firstCal, appendTo, pos);
                if (intervalPattern.getSecondPart() != null) {
                    this.fDateFormat.applyPattern(intervalPattern.getSecondPart());
                    FieldPosition otherPos = new FieldPosition(pos.getField());
                    this.fDateFormat.format(secondCal, appendTo, otherPos);
                    if (pos.getEndIndex() == 0 && otherPos.getEndIndex() > 0) {
                        pos.setBeginIndex(otherPos.getBeginIndex());
                        pos.setEndIndex(otherPos.getEndIndex());
                    }
                }
                this.fDateFormat.applyPattern(originalPattern);
                return appendTo;
            }
        }
        fromToOnSameDay = true;
        intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field2]);
        if (intervalPattern != null) {
        }
    }

    private void adjustPosition(String combiningPattern, String pat0, FieldPosition pos0, String pat1, FieldPosition pos1, FieldPosition posResult) {
        int index0 = combiningPattern.indexOf("{0}");
        int index1 = combiningPattern.indexOf("{1}");
        if (index0 < 0 || index1 < 0) {
            return;
        }
        if (index0 < index1) {
            if (pos0.getEndIndex() > 0) {
                posResult.setBeginIndex(pos0.getBeginIndex() + index0);
                posResult.setEndIndex(pos0.getEndIndex() + index0);
            } else if (pos1.getEndIndex() > 0) {
                int index12 = index1 + (pat0.length() - 3);
                posResult.setBeginIndex(pos1.getBeginIndex() + index12);
                posResult.setEndIndex(pos1.getEndIndex() + index12);
            }
        } else if (pos1.getEndIndex() > 0) {
            posResult.setBeginIndex(pos1.getBeginIndex() + index1);
            posResult.setEndIndex(pos1.getEndIndex() + index1);
        } else if (pos0.getEndIndex() > 0) {
            int index02 = index0 + (pat1.length() - 3);
            posResult.setBeginIndex(pos0.getBeginIndex() + index02);
            posResult.setEndIndex(pos0.getEndIndex() + index02);
        }
    }

    private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos) {
        String fallbackRange;
        String fullPattern = null;
        boolean formatDatePlusTimeRange = (!fromToOnSameDay || this.fDatePattern == null || this.fTimePattern == null) ? false : true;
        if (formatDatePlusTimeRange) {
            fullPattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(this.fTimePattern);
        }
        String fullPattern2 = fullPattern;
        FieldPosition otherPos = new FieldPosition(pos.getField());
        StringBuffer earlierDate = this.fDateFormat.format(fromCalendar, new StringBuffer(64), pos);
        StringBuffer laterDate = this.fDateFormat.format(toCalendar, new StringBuffer(64), otherPos);
        String fallbackPattern = this.fInfo.getFallbackIntervalPattern();
        adjustPosition(fallbackPattern, earlierDate.toString(), pos, laterDate.toString(), otherPos, pos);
        String fallbackRange2 = SimpleFormatterImpl.formatRawPattern(fallbackPattern, 2, 2, new CharSequence[]{earlierDate, laterDate});
        if (formatDatePlusTimeRange) {
            this.fDateFormat.applyPattern(this.fDatePattern);
            StringBuffer datePortion = new StringBuffer(64);
            otherPos.setBeginIndex(0);
            otherPos.setEndIndex(0);
            StringBuffer datePortion2 = this.fDateFormat.format(fromCalendar, datePortion, otherPos);
            adjustPosition(this.fDateTimeFormat, fallbackRange2, pos, datePortion2.toString(), otherPos, pos);
            fallbackRange = SimpleFormatterImpl.formatRawPattern(this.fDateTimeFormat, 2, 2, new CharSequence[]{fallbackRange2, datePortion2});
        } else {
            fallbackRange = fallbackRange2;
        }
        appendTo.append(fallbackRange);
        if (formatDatePlusTimeRange) {
            this.fDateFormat.applyPattern(fullPattern2);
        }
        return appendTo;
    }

    private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos, String fullPattern) {
        String originalPattern = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(fullPattern);
        fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos);
        this.fDateFormat.applyPattern(originalPattern);
        return appendTo;
    }

    @Override // java.text.Format
    @Deprecated
    public Object parseObject(String source, ParsePosition parse_pos) {
        throw new UnsupportedOperationException("parsing is not supported");
    }

    public DateIntervalInfo getDateIntervalInfo() {
        return (DateIntervalInfo) this.fInfo.clone();
    }

    public void setDateIntervalInfo(DateIntervalInfo newItvPattern) {
        this.fInfo = (DateIntervalInfo) newItvPattern.clone();
        this.isDateIntervalInfoDefault = false;
        this.fInfo.m200freeze();
        if (this.fDateFormat != null) {
            initializePattern(null);
        }
    }

    public TimeZone getTimeZone() {
        if (this.fDateFormat != null) {
            return (TimeZone) this.fDateFormat.getTimeZone().clone();
        }
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone zone) {
        TimeZone zoneToSet = (TimeZone) zone.clone();
        if (this.fDateFormat != null) {
            this.fDateFormat.setTimeZone(zoneToSet);
        }
        if (this.fFromCalendar != null) {
            this.fFromCalendar.setTimeZone(zoneToSet);
        }
        if (this.fToCalendar != null) {
            this.fToCalendar.setTimeZone(zoneToSet);
        }
    }

    public synchronized DateFormat getDateFormat() {
        return (DateFormat) this.fDateFormat.clone();
    }

    private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> cache) {
        String fullPattern = this.fDateFormat.toPattern();
        ULocale locale = this.fDateFormat.getLocale();
        String key = null;
        Map<String, DateIntervalInfo.PatternInfo> patterns = null;
        if (cache != null) {
            if (this.fSkeleton != null) {
                key = locale.toString() + "+" + fullPattern + "+" + this.fSkeleton;
            } else {
                key = locale.toString() + "+" + fullPattern;
            }
            patterns = (Map) cache.get(key);
        }
        if (patterns == null) {
            Map<String, DateIntervalInfo.PatternInfo> intervalPatterns = initializeIntervalPattern(fullPattern, locale);
            patterns = Collections.unmodifiableMap(intervalPatterns);
            if (cache != null) {
                cache.put(key, patterns);
            }
        }
        this.fIntervalPatterns = patterns;
    }

    private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String fullPattern, ULocale locale) {
        DateTimePatternGenerator dtpng = DateTimePatternGenerator.getInstance(locale);
        if (this.fSkeleton == null) {
            this.fSkeleton = dtpng.getSkeleton(fullPattern);
        }
        String skeleton = this.fSkeleton;
        HashMap<String, DateIntervalInfo.PatternInfo> intervalPatterns = new HashMap<>();
        StringBuilder date = new StringBuilder(skeleton.length());
        StringBuilder normalizedDate = new StringBuilder(skeleton.length());
        StringBuilder time = new StringBuilder(skeleton.length());
        StringBuilder normalizedTime = new StringBuilder(skeleton.length());
        getDateTimeSkeleton(skeleton, date, normalizedDate, time, normalizedTime);
        String dateSkeleton = date.toString();
        String timeSkeleton = time.toString();
        String normalizedDateSkeleton = normalizedDate.toString();
        String normalizedTimeSkeleton = normalizedTime.toString();
        if (time.length() != 0 && date.length() != 0) {
            this.fDateTimeFormat = getConcatenationPattern(locale);
        }
        boolean found = genSeparateDateTimePtn(normalizedDateSkeleton, normalizedTimeSkeleton, intervalPatterns, dtpng);
        if (!found) {
            if (time.length() != 0 && date.length() == 0) {
                String pattern = dtpng.getBestPattern(DateFormat.YEAR_NUM_MONTH_DAY + timeSkeleton);
                DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
            }
            return intervalPatterns;
        }
        if (time.length() != 0) {
            if (date.length() == 0) {
                String pattern2 = dtpng.getBestPattern(DateFormat.YEAR_NUM_MONTH_DAY + timeSkeleton);
                DateIntervalInfo.PatternInfo ptn2 = new DateIntervalInfo.PatternInfo(null, pattern2, this.fInfo.getDefaultOrder());
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn2);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn2);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn2);
            } else {
                if (!fieldExistsInSkeleton(5, dateSkeleton)) {
                    skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + skeleton;
                    genFallbackPattern(5, skeleton, intervalPatterns, dtpng);
                }
                if (!fieldExistsInSkeleton(2, dateSkeleton)) {
                    skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + skeleton;
                    genFallbackPattern(2, skeleton, intervalPatterns, dtpng);
                }
                if (!fieldExistsInSkeleton(1, dateSkeleton)) {
                    genFallbackPattern(1, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + skeleton, intervalPatterns, dtpng);
                }
                if (this.fDateTimeFormat == null) {
                    this.fDateTimeFormat = "{1} {0}";
                }
                String datePattern = dtpng.getBestPattern(dateSkeleton);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 9, intervalPatterns);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 10, intervalPatterns);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 12, intervalPatterns);
            }
        }
        return intervalPatterns;
    }

    private String getConcatenationPattern(ULocale locale) {
        ICUResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", locale);
        ICUResourceBundle dtPatternsRb = rb.getWithFallback("calendar/gregorian/DateTimePatterns");
        ICUResourceBundle concatenationPatternRb = dtPatternsRb.get(8);
        if (concatenationPatternRb.getType() == 0) {
            return concatenationPatternRb.getString();
        }
        return concatenationPatternRb.getString(0);
    }

    private void genFallbackPattern(int field, String skeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng) {
        String pattern = dtpng.getBestPattern(skeleton);
        DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptn);
    }

    private static void getDateTimeSkeleton(String skeleton, StringBuilder dateSkeleton, StringBuilder normalizedDateSkeleton, StringBuilder timeSkeleton, StringBuilder normalizedTimeSkeleton) {
        int ECount = 0;
        int dCount = 0;
        int MCount = 0;
        int yCount = 0;
        int hCount = 0;
        int HCount = 0;
        int mCount = 0;
        int vCount = 0;
        int zCount = 0;
        for (int i = 0; i < skeleton.length(); i++) {
            char ch = skeleton.charAt(i);
            switch (ch) {
                case 'A':
                case 'K':
                case 'S':
                case 'V':
                case 'Z':
                case 'j':
                case 'k':
                case 's':
                    timeSkeleton.append(ch);
                    normalizedTimeSkeleton.append(ch);
                    break;
                case 'D':
                case 'F':
                case 'G':
                case 'L':
                case 'Q':
                case 'U':
                case 'W':
                case 'Y':
                case 'c':
                case 'e':
                case 'g':
                case 'l':
                case 'q':
                case 'r':
                case 'u':
                case 'w':
                    normalizedDateSkeleton.append(ch);
                    dateSkeleton.append(ch);
                    break;
                case 'E':
                    dateSkeleton.append(ch);
                    ECount++;
                    break;
                case 'H':
                    timeSkeleton.append(ch);
                    HCount++;
                    break;
                case 'M':
                    dateSkeleton.append(ch);
                    MCount++;
                    break;
                case 'a':
                    timeSkeleton.append(ch);
                    break;
                case 'd':
                    dateSkeleton.append(ch);
                    dCount++;
                    break;
                case 'h':
                    timeSkeleton.append(ch);
                    hCount++;
                    break;
                case 'm':
                    timeSkeleton.append(ch);
                    mCount++;
                    break;
                case 'v':
                    vCount++;
                    timeSkeleton.append(ch);
                    break;
                case 'y':
                    dateSkeleton.append(ch);
                    yCount++;
                    break;
                case 'z':
                    zCount++;
                    timeSkeleton.append(ch);
                    break;
            }
        }
        if (yCount != 0) {
            for (int i2 = 0; i2 < yCount; i2++) {
                normalizedDateSkeleton.append('y');
            }
        }
        if (MCount != 0) {
            if (MCount < 3) {
                normalizedDateSkeleton.append(android.text.format.DateFormat.MONTH);
            } else {
                for (int i3 = 0; i3 < MCount && i3 < 5; i3++) {
                    normalizedDateSkeleton.append(android.text.format.DateFormat.MONTH);
                }
            }
        }
        if (ECount != 0) {
            if (ECount <= 3) {
                normalizedDateSkeleton.append(android.text.format.DateFormat.DAY);
            } else {
                for (int i4 = 0; i4 < ECount && i4 < 5; i4++) {
                    normalizedDateSkeleton.append(android.text.format.DateFormat.DAY);
                }
            }
        }
        if (dCount != 0) {
            normalizedDateSkeleton.append(android.text.format.DateFormat.DATE);
        }
        if (HCount != 0) {
            normalizedTimeSkeleton.append('H');
        } else if (hCount != 0) {
            normalizedTimeSkeleton.append(android.text.format.DateFormat.HOUR);
        }
        if (mCount != 0) {
            normalizedTimeSkeleton.append(android.text.format.DateFormat.MINUTE);
        }
        if (zCount != 0) {
            normalizedTimeSkeleton.append(android.text.format.DateFormat.TIME_ZONE);
        }
        if (vCount != 0) {
            normalizedTimeSkeleton.append('v');
        }
    }

    private boolean genSeparateDateTimePtn(String dateSkeleton, String timeSkeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng) {
        String skeleton;
        if (timeSkeleton.length() != 0) {
            skeleton = timeSkeleton;
        } else {
            skeleton = dateSkeleton;
        }
        BestMatchInfo retValue = this.fInfo.getBestSkeleton(skeleton);
        String bestSkeleton = retValue.bestMatchSkeleton;
        int differenceInfo = retValue.bestMatchDistanceInfo;
        if (dateSkeleton.length() != 0) {
            this.fDatePattern = dtpng.getBestPattern(dateSkeleton);
        }
        if (timeSkeleton.length() != 0) {
            this.fTimePattern = dtpng.getBestPattern(timeSkeleton);
        }
        if (differenceInfo == -1) {
            return false;
        }
        if (timeSkeleton.length() == 0) {
            String str = skeleton;
            genIntervalPattern(5, str, bestSkeleton, differenceInfo, intervalPatterns);
            SkeletonAndItsBestMatch skeletons = genIntervalPattern(2, str, bestSkeleton, differenceInfo, intervalPatterns);
            if (skeletons != null) {
                bestSkeleton = skeletons.skeleton;
                skeleton = skeletons.bestMatchSkeleton;
            }
            genIntervalPattern(1, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            return true;
        }
        String str2 = skeleton;
        genIntervalPattern(12, str2, bestSkeleton, differenceInfo, intervalPatterns);
        genIntervalPattern(10, str2, bestSkeleton, differenceInfo, intervalPatterns);
        genIntervalPattern(9, str2, bestSkeleton, differenceInfo, intervalPatterns);
        return true;
    }

    private SkeletonAndItsBestMatch genIntervalPattern(int field, String skeleton, String bestSkeleton, int differenceInfo, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
        SkeletonAndItsBestMatch retValue = null;
        DateIntervalInfo.PatternInfo pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
        if (pattern == null) {
            if (SimpleDateFormat.isFieldUnitIgnored(bestSkeleton, field)) {
                DateIntervalInfo.PatternInfo ptnInfo = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder());
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptnInfo);
                return null;
            } else if (field == 9) {
                DateIntervalInfo.PatternInfo pattern2 = this.fInfo.getIntervalPattern(bestSkeleton, 10);
                if (pattern2 != null) {
                    intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern2);
                }
                return null;
            } else {
                String fieldLetter = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
                bestSkeleton = fieldLetter + bestSkeleton;
                skeleton = fieldLetter + skeleton;
                pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
                if (pattern == null && differenceInfo == 0) {
                    BestMatchInfo tmpRetValue = this.fInfo.getBestSkeleton(skeleton);
                    String tmpBestSkeleton = tmpRetValue.bestMatchSkeleton;
                    differenceInfo = tmpRetValue.bestMatchDistanceInfo;
                    if (tmpBestSkeleton.length() != 0 && differenceInfo != -1) {
                        pattern = this.fInfo.getIntervalPattern(tmpBestSkeleton, field);
                        bestSkeleton = tmpBestSkeleton;
                    }
                }
                if (pattern != null) {
                    retValue = new SkeletonAndItsBestMatch(skeleton, bestSkeleton);
                }
            }
        }
        if (pattern != null) {
            if (differenceInfo != 0) {
                String part1 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo);
                String part2 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo);
                pattern = new DateIntervalInfo.PatternInfo(part1, part2, pattern.firstDateInPtnIsLaterDate());
            }
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
        }
        return retValue;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x009b, code lost:
        if (r13 > 'z') goto L48;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String adjustFieldWidth(String inputSkeleton, String bestMatchSkeleton, String bestMatchIntervalPattern, int differenceInfo) {
        String bestMatchIntervalPattern2;
        char c;
        String bestMatchIntervalPattern3 = bestMatchIntervalPattern;
        if (bestMatchIntervalPattern3 == null) {
            return null;
        }
        int[] inputSkeletonFieldWidth = new int[58];
        int[] bestMatchSkeletonFieldWidth = new int[58];
        DateIntervalInfo.parseSkeleton(inputSkeleton, inputSkeletonFieldWidth);
        DateIntervalInfo.parseSkeleton(bestMatchSkeleton, bestMatchSkeletonFieldWidth);
        if (differenceInfo == 2) {
            bestMatchIntervalPattern3 = bestMatchIntervalPattern3.replace('v', android.text.format.DateFormat.TIME_ZONE);
        }
        StringBuilder adjustedPtn = new StringBuilder(bestMatchIntervalPattern3);
        char prevCh = 0;
        int count = 0;
        int adjustedPtnLength = adjustedPtn.length();
        boolean inQuote = false;
        int i = 0;
        while (i < adjustedPtnLength) {
            char ch = adjustedPtn.charAt(i);
            if (ch == prevCh || count <= 0) {
                bestMatchIntervalPattern2 = bestMatchIntervalPattern3;
            } else {
                char skeletonChar = prevCh;
                char skeletonChar2 = skeletonChar == 'L' ? android.text.format.DateFormat.MONTH : skeletonChar;
                int fieldCount = bestMatchSkeletonFieldWidth[skeletonChar2 - android.text.format.DateFormat.CAPITAL_AM_PM];
                int inputFieldCount = inputSkeletonFieldWidth[skeletonChar2 - android.text.format.DateFormat.CAPITAL_AM_PM];
                if (fieldCount != count || inputFieldCount <= fieldCount) {
                    bestMatchIntervalPattern2 = bestMatchIntervalPattern3;
                } else {
                    int count2 = inputFieldCount - fieldCount;
                    int j = 0;
                    while (true) {
                        int j2 = j;
                        bestMatchIntervalPattern2 = bestMatchIntervalPattern3;
                        if (j2 >= count2) {
                            break;
                        }
                        adjustedPtn.insert(i, prevCh);
                        j = j2 + 1;
                        bestMatchIntervalPattern3 = bestMatchIntervalPattern2;
                    }
                    i += count2;
                    adjustedPtnLength += count2;
                }
                count = 0;
            }
            if (ch == '\'') {
                if (i + 1 >= adjustedPtn.length() || adjustedPtn.charAt(i + 1) != '\'') {
                    inQuote = !inQuote;
                } else {
                    i++;
                }
            } else if (!inQuote) {
                if (ch >= 'a') {
                    c = android.text.format.DateFormat.TIME_ZONE;
                } else {
                    c = android.text.format.DateFormat.TIME_ZONE;
                }
                if (ch >= 'A') {
                    if (ch > 'Z') {
                    }
                    count++;
                    prevCh = ch;
                }
                i++;
                bestMatchIntervalPattern3 = bestMatchIntervalPattern2;
            }
            c = android.text.format.DateFormat.TIME_ZONE;
            i++;
            bestMatchIntervalPattern3 = bestMatchIntervalPattern2;
        }
        if (count > 0) {
            char skeletonChar3 = prevCh;
            if (skeletonChar3 == 'L') {
                skeletonChar3 = android.text.format.DateFormat.MONTH;
            }
            int fieldCount2 = bestMatchSkeletonFieldWidth[skeletonChar3 - android.text.format.DateFormat.CAPITAL_AM_PM];
            int inputFieldCount2 = inputSkeletonFieldWidth[skeletonChar3 - android.text.format.DateFormat.CAPITAL_AM_PM];
            if (fieldCount2 == count && inputFieldCount2 > fieldCount2) {
                int count3 = inputFieldCount2 - fieldCount2;
                int j3 = 0;
                while (true) {
                    int j4 = j3;
                    if (j4 >= count3) {
                        break;
                    }
                    adjustedPtn.append(prevCh);
                    j3 = j4 + 1;
                }
            }
        }
        return adjustedPtn.toString();
    }

    private void concatSingleDate2TimeInterval(String dtfmt, String datePattern, int field, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
        DateIntervalInfo.PatternInfo timeItvPtnInfo = intervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
        if (timeItvPtnInfo != null) {
            String timeIntervalPattern = timeItvPtnInfo.getFirstPart() + timeItvPtnInfo.getSecondPart();
            String pattern = SimpleFormatterImpl.formatRawPattern(dtfmt, 2, 2, new CharSequence[]{timeIntervalPattern, datePattern});
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], DateIntervalInfo.genPatternInfo(pattern, timeItvPtnInfo.firstDateInPtnIsLaterDate()));
        }
    }

    private static boolean fieldExistsInSkeleton(int field, String skeleton) {
        String fieldChar = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
        return skeleton.indexOf(fieldChar) != -1;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
    }

    @Deprecated
    public Map<String, DateIntervalInfo.PatternInfo> getRawPatterns() {
        return this.fIntervalPatterns;
    }
}
