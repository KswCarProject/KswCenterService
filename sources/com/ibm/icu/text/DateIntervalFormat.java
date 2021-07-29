package com.ibm.icu.text;

import android.text.format.DateFormat;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateIntervalFormat extends UFormat {
    private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache();
    private static final long serialVersionUID = 1;
    private SimpleDateFormat fDateFormat;
    private String fDatePattern = null;
    private String fDateTimeFormat = null;
    private Calendar fFromCalendar;
    private DateIntervalInfo fInfo;
    private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns = null;
    private String fSkeleton = null;
    private String fTimePattern = null;
    private Calendar fToCalendar;
    private boolean isDateIntervalInfoDefault;

    static final class BestMatchInfo {
        final int bestMatchDistanceInfo;
        final String bestMatchSkeleton;

        BestMatchInfo(String bestSkeleton, int difference) {
            this.bestMatchSkeleton = bestSkeleton;
            this.bestMatchDistanceInfo = difference;
        }
    }

    private static final class SkeletonAndItsBestMatch {
        final String bestMatchSkeleton;
        final String skeleton;

        SkeletonAndItsBestMatch(String skeleton2, String bestMatch) {
            this.skeleton = skeleton2;
            this.bestMatchSkeleton = bestMatch;
        }
    }

    private DateIntervalFormat() {
    }

    @Deprecated
    public DateIntervalFormat(String skeleton, DateIntervalInfo dtItvInfo, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        dtItvInfo.freeze();
        this.fSkeleton = skeleton;
        this.fInfo = dtItvInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        initializePattern((ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>>) null);
    }

    private DateIntervalFormat(String skeleton, ULocale locale, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        this.fSkeleton = skeleton;
        this.fInfo = new DateIntervalInfo(locale).freeze();
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
        return new DateIntervalFormat(skeleton, locale, new SimpleDateFormat(DateTimePatternGenerator.getInstance(locale).getBestPattern(skeleton), locale));
    }

    public static final DateIntervalFormat getInstance(String skeleton, DateIntervalInfo dtitvinf) {
        return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT), dtitvinf);
    }

    public static final DateIntervalFormat getInstance(String skeleton, Locale locale, DateIntervalInfo dtitvinf) {
        return getInstance(skeleton, ULocale.forLocale(locale), dtitvinf);
    }

    public static final DateIntervalFormat getInstance(String skeleton, ULocale locale, DateIntervalInfo dtitvinf) {
        return new DateIntervalFormat(skeleton, (DateIntervalInfo) dtitvinf.clone(), new SimpleDateFormat(DateTimePatternGenerator.getInstance(locale).getBestPattern(skeleton), locale));
    }

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
        } else if (fromCalendar.get(13) == toCalendar.get(13)) {
            return null;
        } else {
            field = 13;
        }
        DateIntervalInfo.PatternInfo intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
        part2.value = intervalPattern.getSecondPart();
        return intervalPattern.getFirstPart();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00bf A[SYNTHETIC, Splitter:B:49:0x00bf] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.lang.StringBuffer format(com.ibm.icu.util.Calendar r18, com.ibm.icu.util.Calendar r19, java.lang.StringBuffer r20, java.text.FieldPosition r21) {
        /*
            r17 = this;
            r12 = r17
            r0 = r18
            r13 = r19
            r14 = r20
            r15 = r21
            monitor-enter(r17)
            boolean r1 = r18.isEquivalentTo(r19)     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x0146
            r1 = -1
            r2 = 0
            int r3 = r0.get(r2)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r2)     // Catch:{ all -> 0x014e }
            r5 = 13
            r6 = 12
            r7 = 10
            r8 = 9
            r9 = 1
            if (r3 == r4) goto L_0x0029
            r1 = 0
        L_0x0027:
            r11 = r1
            goto L_0x0083
        L_0x0029:
            int r3 = r0.get(r9)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r9)     // Catch:{ all -> 0x014e }
            if (r3 == r4) goto L_0x0035
            r1 = 1
            goto L_0x0027
        L_0x0035:
            r3 = 2
            int r4 = r0.get(r3)     // Catch:{ all -> 0x014e }
            int r3 = r13.get(r3)     // Catch:{ all -> 0x014e }
            if (r4 == r3) goto L_0x0042
            r1 = 2
            goto L_0x0027
        L_0x0042:
            r3 = 5
            int r4 = r0.get(r3)     // Catch:{ all -> 0x014e }
            int r3 = r13.get(r3)     // Catch:{ all -> 0x014e }
            if (r4 == r3) goto L_0x004f
            r1 = 5
            goto L_0x0027
        L_0x004f:
            int r3 = r0.get(r8)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r8)     // Catch:{ all -> 0x014e }
            if (r3 == r4) goto L_0x005c
            r1 = 9
            goto L_0x0027
        L_0x005c:
            int r3 = r0.get(r7)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r7)     // Catch:{ all -> 0x014e }
            if (r3 == r4) goto L_0x0069
            r1 = 10
            goto L_0x0027
        L_0x0069:
            int r3 = r0.get(r6)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r6)     // Catch:{ all -> 0x014e }
            if (r3 == r4) goto L_0x0076
            r1 = 12
            goto L_0x0027
        L_0x0076:
            int r3 = r0.get(r5)     // Catch:{ all -> 0x014e }
            int r4 = r13.get(r5)     // Catch:{ all -> 0x014e }
            if (r3 == r4) goto L_0x013e
            r1 = 13
            goto L_0x0027
        L_0x0083:
            if (r11 == r8) goto L_0x008e
            if (r11 == r7) goto L_0x008e
            if (r11 == r6) goto L_0x008e
            if (r11 != r5) goto L_0x008c
            goto L_0x008e
        L_0x008c:
            r4 = r2
            goto L_0x008f
        L_0x008e:
            r4 = r9
        L_0x008f:
            java.util.Map<java.lang.String, com.ibm.icu.text.DateIntervalInfo$PatternInfo> r1 = r12.fIntervalPatterns     // Catch:{ all -> 0x014e }
            java.lang.String[] r2 = com.ibm.icu.text.DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER     // Catch:{ all -> 0x014e }
            r2 = r2[r11]     // Catch:{ all -> 0x014e }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x014e }
            com.ibm.icu.text.DateIntervalInfo$PatternInfo r1 = (com.ibm.icu.text.DateIntervalInfo.PatternInfo) r1     // Catch:{ all -> 0x014e }
            r16 = r1
            if (r16 != 0) goto L_0x00bf
            com.ibm.icu.text.SimpleDateFormat r1 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            boolean r1 = r1.isFieldUnitIgnored(r11)     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x00af
            com.ibm.icu.text.SimpleDateFormat r1 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            java.lang.StringBuffer r1 = r1.format(r0, r14, r15)     // Catch:{ all -> 0x014e }
            monitor-exit(r17)
            return r1
        L_0x00af:
            r1 = r17
            r2 = r18
            r3 = r19
            r5 = r20
            r6 = r21
            java.lang.StringBuffer r1 = r1.fallbackFormat(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x014e }
            monitor-exit(r17)
            return r1
        L_0x00bf:
            java.lang.String r1 = r16.getFirstPart()     // Catch:{ all -> 0x014e }
            if (r1 != 0) goto L_0x00dd
            java.lang.String r1 = r16.getSecondPart()     // Catch:{ all -> 0x014e }
            r5 = r17
            r6 = r18
            r7 = r19
            r8 = r4
            r9 = r20
            r10 = r21
            r2 = r11
            r11 = r1
            java.lang.StringBuffer r1 = r5.fallbackFormat(r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x014e }
            monitor-exit(r17)
            return r1
        L_0x00dd:
            r2 = r11
            boolean r1 = r16.firstDateInPtnIsLaterDate()     // Catch:{ all -> 0x014e }
            if (r1 == 0) goto L_0x00e9
            r1 = r19
            r3 = r18
            goto L_0x00ec
        L_0x00e9:
            r1 = r18
            r3 = r13
        L_0x00ec:
            com.ibm.icu.text.SimpleDateFormat r5 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            java.lang.String r5 = r5.toPattern()     // Catch:{ all -> 0x014e }
            com.ibm.icu.text.SimpleDateFormat r6 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            java.lang.String r7 = r16.getFirstPart()     // Catch:{ all -> 0x014e }
            r6.applyPattern(r7)     // Catch:{ all -> 0x014e }
            com.ibm.icu.text.SimpleDateFormat r6 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            r6.format(r1, r14, r15)     // Catch:{ all -> 0x014e }
            java.lang.String r6 = r16.getSecondPart()     // Catch:{ all -> 0x014e }
            if (r6 == 0) goto L_0x0137
            com.ibm.icu.text.SimpleDateFormat r6 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            java.lang.String r7 = r16.getSecondPart()     // Catch:{ all -> 0x014e }
            r6.applyPattern(r7)     // Catch:{ all -> 0x014e }
            java.text.FieldPosition r6 = new java.text.FieldPosition     // Catch:{ all -> 0x014e }
            int r7 = r21.getField()     // Catch:{ all -> 0x014e }
            r6.<init>(r7)     // Catch:{ all -> 0x014e }
            com.ibm.icu.text.SimpleDateFormat r7 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            r7.format(r3, r14, r6)     // Catch:{ all -> 0x014e }
            int r7 = r21.getEndIndex()     // Catch:{ all -> 0x014e }
            if (r7 != 0) goto L_0x0137
            int r7 = r6.getEndIndex()     // Catch:{ all -> 0x014e }
            if (r7 <= 0) goto L_0x0137
            int r7 = r6.getBeginIndex()     // Catch:{ all -> 0x014e }
            r15.setBeginIndex(r7)     // Catch:{ all -> 0x014e }
            int r7 = r6.getEndIndex()     // Catch:{ all -> 0x014e }
            r15.setEndIndex(r7)     // Catch:{ all -> 0x014e }
        L_0x0137:
            com.ibm.icu.text.SimpleDateFormat r6 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            r6.applyPattern(r5)     // Catch:{ all -> 0x014e }
            monitor-exit(r17)
            return r14
        L_0x013e:
            com.ibm.icu.text.SimpleDateFormat r2 = r12.fDateFormat     // Catch:{ all -> 0x014e }
            java.lang.StringBuffer r2 = r2.format(r0, r14, r15)     // Catch:{ all -> 0x014e }
            monitor-exit(r17)
            return r2
        L_0x0146:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x014e }
            java.lang.String r2 = "can not format on two different calendars"
            r1.<init>(r2)     // Catch:{ all -> 0x014e }
            throw r1     // Catch:{ all -> 0x014e }
        L_0x014e:
            r0 = move-exception
            monitor-exit(r17)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateIntervalFormat.format(com.ibm.icu.util.Calendar, com.ibm.icu.util.Calendar, java.lang.StringBuffer, java.text.FieldPosition):java.lang.StringBuffer");
    }

    private void adjustPosition(String combiningPattern, String pat0, FieldPosition pos0, String pat1, FieldPosition pos1, FieldPosition posResult) {
        int index0 = combiningPattern.indexOf("{0}");
        int index1 = combiningPattern.indexOf("{1}");
        if (index0 >= 0 && index1 >= 0) {
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
    }

    private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos) {
        String fallbackRange;
        Calendar calendar = fromCalendar;
        StringBuffer stringBuffer = appendTo;
        String fullPattern = null;
        boolean formatDatePlusTimeRange = (!fromToOnSameDay || this.fDatePattern == null || this.fTimePattern == null) ? false : true;
        if (formatDatePlusTimeRange) {
            fullPattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(this.fTimePattern);
        }
        String fullPattern2 = fullPattern;
        FieldPosition otherPos = new FieldPosition(pos.getField());
        StringBuffer earlierDate = this.fDateFormat.format(calendar, new StringBuffer(64), pos);
        StringBuffer laterDate = this.fDateFormat.format(toCalendar, new StringBuffer(64), otherPos);
        String fallbackPattern = this.fInfo.getFallbackIntervalPattern();
        adjustPosition(fallbackPattern, earlierDate.toString(), pos, laterDate.toString(), otherPos, pos);
        String fallbackPattern2 = fallbackPattern;
        String fallbackRange2 = SimpleFormatterImpl.formatRawPattern(fallbackPattern2, 2, 2, new CharSequence[]{earlierDate, laterDate});
        if (formatDatePlusTimeRange) {
            this.fDateFormat.applyPattern(this.fDatePattern);
            StringBuffer datePortion = new StringBuffer(64);
            otherPos.setBeginIndex(0);
            otherPos.setEndIndex(0);
            StringBuffer datePortion2 = this.fDateFormat.format(calendar, datePortion, otherPos);
            String str = fallbackPattern2;
            adjustPosition(this.fDateTimeFormat, fallbackRange2, pos, datePortion2.toString(), otherPos, pos);
            fallbackRange = SimpleFormatterImpl.formatRawPattern(this.fDateTimeFormat, 2, 2, new CharSequence[]{fallbackRange2, datePortion2});
        } else {
            fallbackRange = fallbackRange2;
        }
        stringBuffer.append(fallbackRange);
        if (formatDatePlusTimeRange) {
            this.fDateFormat.applyPattern(fullPattern2);
        }
        return stringBuffer;
    }

    private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos, String fullPattern) {
        String originalPattern = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(fullPattern);
        fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos);
        this.fDateFormat.applyPattern(originalPattern);
        return appendTo;
    }

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
        this.fInfo.freeze();
        if (this.fDateFormat != null) {
            initializePattern((ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>>) null);
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.util.Map<java.lang.String, com.ibm.icu.text.DateIntervalInfo$PatternInfo>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initializePattern(com.ibm.icu.impl.ICUCache<java.lang.String, java.util.Map<java.lang.String, com.ibm.icu.text.DateIntervalInfo.PatternInfo>> r7) {
        /*
            r6 = this;
            com.ibm.icu.text.SimpleDateFormat r0 = r6.fDateFormat
            java.lang.String r0 = r0.toPattern()
            com.ibm.icu.text.SimpleDateFormat r1 = r6.fDateFormat
            com.ibm.icu.util.ULocale r1 = r1.getLocale()
            r2 = 0
            r3 = 0
            if (r7 == 0) goto L_0x0056
            java.lang.String r4 = r6.fSkeleton
            if (r4 == 0) goto L_0x0037
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r1.toString()
            r4.append(r5)
            java.lang.String r5 = "+"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = "+"
            r4.append(r5)
            java.lang.String r5 = r6.fSkeleton
            r4.append(r5)
            java.lang.String r2 = r4.toString()
            goto L_0x004f
        L_0x0037:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r1.toString()
            r4.append(r5)
            java.lang.String r5 = "+"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r2 = r4.toString()
        L_0x004f:
            java.lang.Object r4 = r7.get(r2)
            r3 = r4
            java.util.Map r3 = (java.util.Map) r3
        L_0x0056:
            if (r3 != 0) goto L_0x0065
            java.util.Map r4 = r6.initializeIntervalPattern(r0, r1)
            java.util.Map r3 = java.util.Collections.unmodifiableMap(r4)
            if (r7 == 0) goto L_0x0065
            r7.put(r2, r3)
        L_0x0065:
            r6.fIntervalPatterns = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateIntervalFormat.initializePattern(com.ibm.icu.impl.ICUCache):void");
    }

    private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String fullPattern, ULocale locale) {
        DateTimePatternGenerator dtpng = DateTimePatternGenerator.getInstance(locale);
        if (this.fSkeleton == null) {
            this.fSkeleton = dtpng.getSkeleton(fullPattern);
        } else {
            String str = fullPattern;
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
        if (time.length() == 0 || date.length() == 0) {
            ULocale uLocale = locale;
        } else {
            this.fDateTimeFormat = getConcatenationPattern(locale);
        }
        if (!genSeparateDateTimePtn(normalizedDateSkeleton, normalizedTimeSkeleton, intervalPatterns, dtpng)) {
            if (time.length() == 0 || date.length() != 0) {
                StringBuilder sb = normalizedTime;
            } else {
                StringBuilder sb2 = normalizedDate;
                StringBuilder sb3 = normalizedTime;
                DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo((String) null, dtpng.getBestPattern(DateFormat.YEAR_NUM_MONTH_DAY + timeSkeleton), this.fInfo.getDefaultOrder());
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
            }
            return intervalPatterns;
        }
        StringBuilder sb4 = normalizedTime;
        if (time.length() != 0) {
            if (date.length() == 0) {
                DateIntervalInfo.PatternInfo ptn2 = new DateIntervalInfo.PatternInfo((String) null, dtpng.getBestPattern(DateFormat.YEAR_NUM_MONTH_DAY + timeSkeleton), this.fInfo.getDefaultOrder());
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
        ICUResourceBundle concatenationPatternRb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b", locale).getWithFallback("calendar/gregorian/DateTimePatterns").get(8);
        if (concatenationPatternRb.getType() == 0) {
            return concatenationPatternRb.getString();
        }
        return concatenationPatternRb.getString(0);
    }

    private void genFallbackPattern(int field, String skeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng) {
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], new DateIntervalInfo.PatternInfo((String) null, dtpng.getBestPattern(skeleton), this.fInfo.getDefaultOrder()));
    }

    private static void getDateTimeSkeleton(String skeleton, StringBuilder dateSkeleton, StringBuilder normalizedDateSkeleton, StringBuilder timeSkeleton, StringBuilder normalizedTimeSkeleton) {
        StringBuilder sb = dateSkeleton;
        StringBuilder sb2 = normalizedDateSkeleton;
        StringBuilder sb3 = timeSkeleton;
        StringBuilder sb4 = normalizedTimeSkeleton;
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
                    sb3.append(ch);
                    sb4.append(ch);
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
                    sb2.append(ch);
                    sb.append(ch);
                    break;
                case 'E':
                    sb.append(ch);
                    ECount++;
                    break;
                case 'H':
                    sb3.append(ch);
                    HCount++;
                    break;
                case 'M':
                    sb.append(ch);
                    MCount++;
                    break;
                case 'a':
                    sb3.append(ch);
                    break;
                case 'd':
                    sb.append(ch);
                    dCount++;
                    break;
                case 'h':
                    sb3.append(ch);
                    hCount++;
                    break;
                case 'm':
                    sb3.append(ch);
                    mCount++;
                    break;
                case 'v':
                    vCount++;
                    sb3.append(ch);
                    break;
                case 'y':
                    sb.append(ch);
                    yCount++;
                    break;
                case 'z':
                    zCount++;
                    sb3.append(ch);
                    break;
            }
        }
        String str = skeleton;
        if (yCount != 0) {
            for (int i2 = 0; i2 < yCount; i2++) {
                sb2.append('y');
            }
        }
        if (MCount != 0) {
            if (MCount < 3) {
                sb2.append(DateFormat.MONTH);
            } else {
                int i3 = 0;
                while (i3 < MCount && i3 < 5) {
                    sb2.append(DateFormat.MONTH);
                    i3++;
                }
            }
        }
        if (ECount != 0) {
            if (ECount <= 3) {
                sb2.append(DateFormat.DAY);
            } else {
                int i4 = 0;
                while (i4 < ECount && i4 < 5) {
                    sb2.append(DateFormat.DAY);
                    i4++;
                }
            }
        }
        if (dCount != 0) {
            sb2.append(DateFormat.DATE);
        }
        if (HCount != 0) {
            sb4.append('H');
        } else if (hCount != 0) {
            sb4.append(DateFormat.HOUR);
        }
        if (mCount != 0) {
            sb4.append(DateFormat.MINUTE);
        }
        if (zCount != 0) {
            sb4.append(DateFormat.TIME_ZONE);
        }
        if (vCount != 0) {
            sb4.append('v');
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
            String str2 = bestSkeleton;
            int i = differenceInfo;
            Map<String, DateIntervalInfo.PatternInfo> map = intervalPatterns;
            genIntervalPattern(5, str, str2, i, map);
            SkeletonAndItsBestMatch skeletons = genIntervalPattern(2, str, str2, i, map);
            if (skeletons != null) {
                bestSkeleton = skeletons.skeleton;
                skeleton = skeletons.bestMatchSkeleton;
            }
            genIntervalPattern(1, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            return true;
        }
        String str3 = skeleton;
        String str4 = bestSkeleton;
        int i2 = differenceInfo;
        Map<String, DateIntervalInfo.PatternInfo> map2 = intervalPatterns;
        genIntervalPattern(12, str3, str4, i2, map2);
        genIntervalPattern(10, str3, str4, i2, map2);
        genIntervalPattern(9, str3, str4, i2, map2);
        return true;
    }

    private SkeletonAndItsBestMatch genIntervalPattern(int field, String skeleton, String bestSkeleton, int differenceInfo, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
        SkeletonAndItsBestMatch retValue = null;
        DateIntervalInfo.PatternInfo pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
        if (pattern == null) {
            if (SimpleDateFormat.isFieldUnitIgnored(bestSkeleton, field)) {
                intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), (String) null, this.fInfo.getDefaultOrder()));
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
                    if (!(tmpBestSkeleton.length() == 0 || differenceInfo == -1)) {
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
                pattern = new DateIntervalInfo.PatternInfo(adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo), adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo), pattern.firstDateInPtnIsLaterDate());
            }
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
        }
        return retValue;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009b, code lost:
        if (r13 > 'z') goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String adjustFieldWidth(java.lang.String r21, java.lang.String r22, java.lang.String r23, int r24) {
        /*
            r0 = r23
            if (r0 != 0) goto L_0x0006
            r1 = 0
            return r1
        L_0x0006:
            r1 = 58
            int[] r2 = new int[r1]
            int[] r1 = new int[r1]
            r3 = r21
            com.ibm.icu.text.DateIntervalInfo.parseSkeleton(r3, r2)
            r4 = r22
            com.ibm.icu.text.DateIntervalInfo.parseSkeleton(r4, r1)
            r5 = 2
            r6 = 122(0x7a, float:1.71E-43)
            r7 = r24
            if (r7 != r5) goto L_0x0023
            r5 = 118(0x76, float:1.65E-43)
            java.lang.String r0 = r0.replace(r5, r6)
        L_0x0023:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r0)
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 65
            int r12 = r5.length()
            r14 = r8
            r8 = 0
        L_0x0033:
            r15 = 76
            if (r8 >= r12) goto L_0x00b2
            char r13 = r5.charAt(r8)
            if (r13 == r9) goto L_0x0071
            if (r10 <= 0) goto L_0x0071
            r17 = r9
            r6 = r17
            if (r6 != r15) goto L_0x0048
            r17 = 77
            goto L_0x004a
        L_0x0048:
            r17 = r6
        L_0x004a:
            int r6 = r17 - r11
            r6 = r1[r6]
            int r15 = r17 - r11
            r15 = r2[r15]
            if (r6 != r10) goto L_0x006d
            if (r15 <= r6) goto L_0x006d
            int r10 = r15 - r6
            r18 = 0
        L_0x005a:
            r19 = r18
            r20 = r0
            r0 = r19
            if (r0 >= r10) goto L_0x006a
            r5.insert(r8, r9)
            int r18 = r0 + 1
            r0 = r20
            goto L_0x005a
        L_0x006a:
            int r8 = r8 + r10
            int r12 = r12 + r10
            goto L_0x006f
        L_0x006d:
            r20 = r0
        L_0x006f:
            r10 = 0
            goto L_0x0073
        L_0x0071:
            r20 = r0
        L_0x0073:
            r0 = 39
            if (r13 != r0) goto L_0x0093
            int r15 = r8 + 1
            int r6 = r5.length()
            if (r15 >= r6) goto L_0x008c
            int r6 = r8 + 1
            char r6 = r5.charAt(r6)
            if (r6 != r0) goto L_0x008c
            int r8 = r8 + 1
        L_0x0089:
            r0 = 122(0x7a, float:1.71E-43)
            goto L_0x00ac
        L_0x008c:
            if (r14 != 0) goto L_0x0090
            r0 = 1
            goto L_0x0091
        L_0x0090:
            r0 = 0
        L_0x0091:
            r14 = r0
            goto L_0x0089
        L_0x0093:
            if (r14 != 0) goto L_0x0089
            r0 = 97
            if (r13 < r0) goto L_0x009e
            r0 = 122(0x7a, float:1.71E-43)
            if (r13 <= r0) goto L_0x00a8
            goto L_0x00a0
        L_0x009e:
            r0 = 122(0x7a, float:1.71E-43)
        L_0x00a0:
            r6 = 65
            if (r13 < r6) goto L_0x00ac
            r6 = 90
            if (r13 > r6) goto L_0x00ac
        L_0x00a8:
            r6 = r13
            int r10 = r10 + 1
            r9 = r6
        L_0x00ac:
            r6 = 1
            int r8 = r8 + r6
            r6 = r0
            r0 = r20
            goto L_0x0033
        L_0x00b2:
            r20 = r0
            if (r10 <= 0) goto L_0x00d5
            r0 = r9
            if (r0 != r15) goto L_0x00bb
            r0 = 77
        L_0x00bb:
            int r6 = r0 - r11
            r6 = r1[r6]
            int r8 = r0 - r11
            r8 = r2[r8]
            if (r6 != r10) goto L_0x00d5
            if (r8 <= r6) goto L_0x00d5
            int r10 = r8 - r6
            r16 = 0
        L_0x00cb:
            r13 = r16
            if (r13 >= r10) goto L_0x00d5
            r5.append(r9)
            int r16 = r13 + 1
            goto L_0x00cb
        L_0x00d5:
            java.lang.String r0 = r5.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateIntervalFormat.adjustFieldWidth(java.lang.String, java.lang.String, java.lang.String, int):java.lang.String");
    }

    private void concatSingleDate2TimeInterval(String dtfmt, String datePattern, int field, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
        DateIntervalInfo.PatternInfo timeItvPtnInfo = intervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
        if (timeItvPtnInfo != null) {
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], DateIntervalInfo.genPatternInfo(SimpleFormatterImpl.formatRawPattern(dtfmt, 2, 2, new CharSequence[]{timeItvPtnInfo.getFirstPart() + timeItvPtnInfo.getSecondPart(), datePattern}), timeItvPtnInfo.firstDateInPtnIsLaterDate()));
        }
    }

    private static boolean fieldExistsInSkeleton(int field, String skeleton) {
        return skeleton.indexOf(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]) != -1;
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