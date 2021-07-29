package com.ibm.icu.text;

import android.provider.MediaStore;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.text.MeasureFormat;
import com.ibm.icu.util.TimeUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

@Deprecated
public class TimeUnitFormat extends MeasureFormat {
    @Deprecated
    public static final int ABBREVIATED_NAME = 1;
    private static final String DEFAULT_PATTERN_FOR_DAY = "{0} d";
    private static final String DEFAULT_PATTERN_FOR_HOUR = "{0} h";
    private static final String DEFAULT_PATTERN_FOR_MINUTE = "{0} min";
    private static final String DEFAULT_PATTERN_FOR_MONTH = "{0} m";
    private static final String DEFAULT_PATTERN_FOR_SECOND = "{0} s";
    private static final String DEFAULT_PATTERN_FOR_WEEK = "{0} w";
    private static final String DEFAULT_PATTERN_FOR_YEAR = "{0} y";
    @Deprecated
    public static final int FULL_NAME = 0;
    private static final int TOTAL_STYLES = 2;
    private static final long serialVersionUID = -3707773153184971529L;
    private NumberFormat format;
    private transient boolean isReady;
    private ULocale locale;
    private transient PluralRules pluralRules;
    private int style;
    private transient Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;

    @Deprecated
    public TimeUnitFormat() {
        this(ULocale.getDefault(), 0);
    }

    @Deprecated
    public TimeUnitFormat(ULocale locale2) {
        this(locale2, 0);
    }

    @Deprecated
    public TimeUnitFormat(Locale locale2) {
        this(locale2, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @Deprecated
    public TimeUnitFormat(ULocale locale2, int style2) {
        super(locale2, style2 == 0 ? MeasureFormat.FormatWidth.WIDE : MeasureFormat.FormatWidth.SHORT);
        this.format = super.getNumberFormatInternal();
        if (style2 < 0 || style2 >= 2) {
            throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
        }
        this.style = style2;
        this.isReady = false;
    }

    private TimeUnitFormat(ULocale locale2, int style2, NumberFormat numberFormat) {
        this(locale2, style2);
        if (numberFormat != null) {
            setNumberFormat((NumberFormat) numberFormat.clone());
        }
    }

    @Deprecated
    public TimeUnitFormat(Locale locale2, int style2) {
        this(ULocale.forLocale(locale2), style2);
    }

    @Deprecated
    public TimeUnitFormat setLocale(ULocale locale2) {
        setLocale(locale2, locale2);
        clearCache();
        return this;
    }

    @Deprecated
    public TimeUnitFormat setLocale(Locale locale2) {
        return setLocale(ULocale.forLocale(locale2));
    }

    @Deprecated
    public TimeUnitFormat setNumberFormat(NumberFormat format2) {
        if (format2 == this.format) {
            return this;
        }
        if (format2 != null) {
            this.format = format2;
        } else if (this.locale == null) {
            this.isReady = false;
        } else {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        clearCache();
        return this;
    }

    @Deprecated
    public NumberFormat getNumberFormat() {
        return (NumberFormat) this.format.clone();
    }

    /* access modifiers changed from: package-private */
    public NumberFormat getNumberFormatInternal() {
        return this.format;
    }

    /* access modifiers changed from: package-private */
    public LocalizedNumberFormatter getNumberFormatter() {
        return ((DecimalFormat) this.format).toNumberFormatter();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r20v0, resolved type: java.lang.Object} */
    /* JADX WARNING: Multi-variable type inference failed */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ibm.icu.util.TimeUnitAmount parseObject(java.lang.String r23, java.text.ParsePosition r24) {
        /*
            r22 = this;
            r1 = r22
            r2 = r24
            boolean r0 = r1.isReady
            if (r0 != 0) goto L_0x000b
            r22.setup()
        L_0x000b:
            r0 = 0
            r3 = 0
            int r4 = r24.getIndex()
            r5 = -1
            r6 = 0
            r7 = 0
            java.util.Map<com.ibm.icu.util.TimeUnit, java.util.Map<java.lang.String, java.lang.Object[]>> r8 = r1.timeUnitToCountToPatterns
            java.util.Set r8 = r8.keySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x001e:
            boolean r9 = r8.hasNext()
            r10 = 2
            r11 = -1
            if (r9 == 0) goto L_0x00db
            java.lang.Object r9 = r8.next()
            com.ibm.icu.util.TimeUnit r9 = (com.ibm.icu.util.TimeUnit) r9
            java.util.Map<com.ibm.icu.util.TimeUnit, java.util.Map<java.lang.String, java.lang.Object[]>> r13 = r1.timeUnitToCountToPatterns
            java.lang.Object r13 = r13.get(r9)
            java.util.Map r13 = (java.util.Map) r13
            java.util.Set r14 = r13.entrySet()
            java.util.Iterator r14 = r14.iterator()
        L_0x003c:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x00d5
            java.lang.Object r15 = r14.next()
            java.util.Map$Entry r15 = (java.util.Map.Entry) r15
            java.lang.Object r16 = r15.getKey()
            java.lang.String r16 = (java.lang.String) r16
            r17 = r5
            r5 = r3
            r3 = r0
            r0 = 0
        L_0x0053:
            r18 = r0
            r12 = r18
            if (r12 >= r10) goto L_0x00c6
            java.lang.Object r0 = r15.getValue()
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r0 = r0[r12]
            r10 = r0
            com.ibm.icu.text.MessageFormat r10 = (com.ibm.icu.text.MessageFormat) r10
            r2.setErrorIndex(r11)
            r2.setIndex(r4)
            r11 = r23
            java.lang.Object r18 = r10.parseObject(r11, r2)
            int r0 = r24.getErrorIndex()
            r20 = r3
            r3 = -1
            if (r0 != r3) goto L_0x00bd
            int r0 = r24.getIndex()
            if (r0 != r4) goto L_0x0080
            goto L_0x00bd
        L_0x0080:
            r3 = 0
            r0 = r18
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            int r0 = r0.length
            if (r0 == 0) goto L_0x00a7
            r0 = r18
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r21 = r3
            r19 = 0
            r3 = r0[r19]
            boolean r0 = r3 instanceof java.lang.Number
            if (r0 == 0) goto L_0x009a
            r0 = r3
            java.lang.Number r0 = (java.lang.Number) r0
            goto L_0x00ab
        L_0x009a:
            com.ibm.icu.text.NumberFormat r0 = r1.format     // Catch:{ ParseException -> 0x00a5 }
            java.lang.String r1 = r3.toString()     // Catch:{ ParseException -> 0x00a5 }
            java.lang.Number r0 = r0.parse(r1)     // Catch:{ ParseException -> 0x00a5 }
            goto L_0x00ab
        L_0x00a5:
            r0 = move-exception
            goto L_0x00bd
        L_0x00a7:
            r21 = r3
            r0 = r21
        L_0x00ab:
            int r1 = r24.getIndex()
            int r1 = r1 - r4
            if (r1 <= r6) goto L_0x00bd
            r3 = r0
            r5 = r9
            int r17 = r24.getIndex()
            r6 = r1
            r0 = r16
            r7 = r0
            goto L_0x00bf
        L_0x00bd:
            r3 = r20
        L_0x00bf:
            int r0 = r12 + 1
            r1 = r22
            r10 = 2
            r11 = -1
            goto L_0x0053
        L_0x00c6:
            r11 = r23
            r20 = r3
            r3 = r5
            r5 = r17
            r0 = r20
            r1 = r22
            r10 = 2
            r11 = -1
            goto L_0x003c
        L_0x00d5:
            r11 = r23
            r1 = r22
            goto L_0x001e
        L_0x00db:
            r11 = r23
            if (r0 != 0) goto L_0x0110
            if (r6 == 0) goto L_0x0110
            java.lang.String r1 = "zero"
            boolean r1 = r7.equals(r1)
            if (r1 == 0) goto L_0x00ef
            r1 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            goto L_0x0110
        L_0x00ef:
            java.lang.String r1 = "one"
            boolean r1 = r7.equals(r1)
            if (r1 == 0) goto L_0x00fd
            r1 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            goto L_0x0110
        L_0x00fd:
            java.lang.String r1 = "two"
            boolean r1 = r7.equals(r1)
            if (r1 == 0) goto L_0x010b
            r1 = 2
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            goto L_0x0110
        L_0x010b:
            r1 = 3
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
        L_0x0110:
            if (r6 != 0) goto L_0x011b
            r2.setIndex(r4)
            r1 = 0
            r2.setErrorIndex(r1)
            r1 = 0
            return r1
        L_0x011b:
            r2.setIndex(r5)
            r1 = -1
            r2.setErrorIndex(r1)
            com.ibm.icu.util.TimeUnitAmount r1 = new com.ibm.icu.util.TimeUnitAmount
            r1.<init>(r0, r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeUnitFormat.parseObject(java.lang.String, java.text.ParsePosition):com.ibm.icu.util.TimeUnitAmount");
    }

    private void setup() {
        if (this.locale == null) {
            if (this.format != null) {
                this.locale = this.format.getLocale((ULocale.Type) null);
            } else {
                this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            setLocale(this.locale, this.locale);
        }
        if (this.format == null) {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        this.pluralRules = PluralRules.forLocale(this.locale);
        this.timeUnitToCountToPatterns = new HashMap();
        Set<String> pluralKeywords = this.pluralRules.getKeywords();
        setup("units/duration", this.timeUnitToCountToPatterns, 0, pluralKeywords);
        setup("unitsShort/duration", this.timeUnitToCountToPatterns, 1, pluralKeywords);
        this.isReady = true;
    }

    private static final class TimeUnitFormatSetupSink extends UResource.Sink {
        boolean beenHere = false;
        ULocale locale;
        Set<String> pluralKeywords;
        int style;
        Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;

        TimeUnitFormatSetupSink(Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns2, int style2, Set<String> pluralKeywords2, ULocale locale2) {
            this.timeUnitToCountToPatterns = timeUnitToCountToPatterns2;
            this.style = style2;
            this.pluralKeywords = pluralKeywords2;
            this.locale = locale2;
        }

        public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
            TimeUnit timeUnit;
            UResource.Key key2 = key;
            UResource.Value value2 = value;
            if (!this.beenHere) {
                this.beenHere = true;
                UResource.Table units = value.getTable();
                for (int i = 0; units.getKeyAndValue(i, key2, value2); i++) {
                    String timeUnitName = key.toString();
                    if (timeUnitName.equals(MediaStore.Audio.AudioColumns.YEAR)) {
                        timeUnit = TimeUnit.YEAR;
                    } else if (timeUnitName.equals("month")) {
                        timeUnit = TimeUnit.MONTH;
                    } else if (timeUnitName.equals("day")) {
                        timeUnit = TimeUnit.DAY;
                    } else if (timeUnitName.equals("hour")) {
                        timeUnit = TimeUnit.HOUR;
                    } else if (timeUnitName.equals("minute")) {
                        timeUnit = TimeUnit.MINUTE;
                    } else if (timeUnitName.equals("second")) {
                        timeUnit = TimeUnit.SECOND;
                    } else if (timeUnitName.equals("week")) {
                        timeUnit = TimeUnit.WEEK;
                    }
                    Map<String, Object[]> countToPatterns = this.timeUnitToCountToPatterns.get(timeUnit);
                    if (countToPatterns == null) {
                        countToPatterns = new TreeMap<>();
                        this.timeUnitToCountToPatterns.put(timeUnit, countToPatterns);
                    }
                    UResource.Table countsToPatternTable = value.getTable();
                    for (int j = 0; countsToPatternTable.getKeyAndValue(j, key2, value2); j++) {
                        String pluralCount = key.toString();
                        if (this.pluralKeywords.contains(pluralCount)) {
                            Object[] pair = countToPatterns.get(pluralCount);
                            if (pair == null) {
                                pair = new Object[2];
                                countToPatterns.put(pluralCount, pair);
                            }
                            if (pair[this.style] == null) {
                                pair[this.style] = new MessageFormat(value.getString(), this.locale);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setup(String resourceKey, Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns2, int style2, Set<String> pluralKeywords) {
        Map<String, Object[]> countToPatterns;
        Map<TimeUnit, Map<String, Object[]>> map = timeUnitToCountToPatterns2;
        int i = style2;
        try {
            try {
                try {
                    UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b/unit", this.locale).getAllItemsWithFallback(resourceKey, new TimeUnitFormatSetupSink(map, i, pluralKeywords, this.locale));
                } catch (MissingResourceException e) {
                }
            } catch (MissingResourceException e2) {
                String str = resourceKey;
            }
        } catch (MissingResourceException e3) {
            String str2 = resourceKey;
            Set<String> set = pluralKeywords;
        }
        TimeUnit[] timeUnits = TimeUnit.values();
        Set<String> keywords = this.pluralRules.getKeywords();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < timeUnits.length) {
                TimeUnit timeUnit = timeUnits[i3];
                Map<String, Object[]> countToPatterns2 = map.get(timeUnit);
                if (countToPatterns2 == null) {
                    countToPatterns2 = new TreeMap<>();
                    map.put(timeUnit, countToPatterns2);
                }
                Map<String, Object[]> countToPatterns3 = countToPatterns2;
                for (String pluralCount : keywords) {
                    if (countToPatterns3.get(pluralCount) == null || countToPatterns3.get(pluralCount)[i] == null) {
                        String str3 = pluralCount;
                        countToPatterns = countToPatterns3;
                        searchInTree(resourceKey, style2, timeUnit, pluralCount, pluralCount, countToPatterns3);
                    } else {
                        countToPatterns = countToPatterns3;
                    }
                    countToPatterns3 = countToPatterns;
                }
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }

    private void searchInTree(String resourceKey, int styl, TimeUnit timeUnit, String srcPluralCount, String searchPluralCount, Map<String, Object[]> countToPatterns) {
        String str = resourceKey;
        TimeUnit timeUnit2 = timeUnit;
        String str2 = srcPluralCount;
        String str3 = searchPluralCount;
        Map<String, Object[]> map = countToPatterns;
        ULocale parentLocale = this.locale;
        String srcTimeUnitName = timeUnit.toString();
        ULocale parentLocale2 = parentLocale;
        while (true) {
            String srcTimeUnitName2 = srcTimeUnitName;
            if (parentLocale2 != null) {
                try {
                    MessageFormat messageFormat = new MessageFormat(UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt63b/unit", parentLocale2).getWithFallback(str).getWithFallback(srcTimeUnitName2).getStringWithFallback(str3), this.locale);
                    Object[] pair = map.get(str2);
                    if (pair == null) {
                        pair = new Object[2];
                        map.put(str2, pair);
                    }
                    pair[styl] = messageFormat;
                    return;
                } catch (MissingResourceException e) {
                    parentLocale2 = parentLocale2.getFallback();
                    srcTimeUnitName = srcTimeUnitName2;
                }
            } else {
                if (parentLocale2 != null || !str.equals("unitsShort")) {
                } else {
                    String str4 = srcTimeUnitName2;
                    searchInTree("units", styl, timeUnit, srcPluralCount, searchPluralCount, countToPatterns);
                    if (!(map.get(str2) == null || map.get(str2)[styl] == null)) {
                        return;
                    }
                }
                if (str3.equals("other")) {
                    MessageFormat messageFormat2 = null;
                    if (timeUnit2 == TimeUnit.SECOND) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_SECOND, this.locale);
                    } else if (timeUnit2 == TimeUnit.MINUTE) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_MINUTE, this.locale);
                    } else if (timeUnit2 == TimeUnit.HOUR) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_HOUR, this.locale);
                    } else if (timeUnit2 == TimeUnit.WEEK) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_WEEK, this.locale);
                    } else if (timeUnit2 == TimeUnit.DAY) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_DAY, this.locale);
                    } else if (timeUnit2 == TimeUnit.MONTH) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_MONTH, this.locale);
                    } else if (timeUnit2 == TimeUnit.YEAR) {
                        messageFormat2 = new MessageFormat(DEFAULT_PATTERN_FOR_YEAR, this.locale);
                    }
                    Object[] pair2 = map.get(str2);
                    if (pair2 == null) {
                        pair2 = new Object[2];
                        map.put(str2, pair2);
                    }
                    pair2[styl] = messageFormat2;
                    return;
                }
                searchInTree(resourceKey, styl, timeUnit, srcPluralCount, "other", countToPatterns);
                return;
            }
        }
    }

    @Deprecated
    public Object clone() {
        TimeUnitFormat result = (TimeUnitFormat) super.clone();
        result.format = (NumberFormat) this.format.clone();
        return result;
    }

    private Object writeReplace() throws ObjectStreamException {
        return super.toTimeUnitProxy();
    }

    private Object readResolve() throws ObjectStreamException {
        return new TimeUnitFormat(this.locale, this.style, this.format);
    }
}
