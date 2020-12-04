package android.text.format;

import android.content.res.Resources;
import com.android.internal.R;
import java.nio.CharBuffer;
import java.util.Formatter;
import java.util.Locale;
import libcore.icu.LocaleData;
import libcore.util.ZoneInfo;

class TimeFormatter {
    private static final int DAYSPERLYEAR = 366;
    private static final int DAYSPERNYEAR = 365;
    private static final int DAYSPERWEEK = 7;
    private static final int FORCE_LOWER_CASE = -1;
    private static final int HOURSPERDAY = 24;
    private static final int MINSPERHOUR = 60;
    private static final int MONSPERYEAR = 12;
    private static final int SECSPERMIN = 60;
    private static String sDateOnlyFormat;
    private static String sDateTimeFormat;
    private static Locale sLocale;
    private static LocaleData sLocaleData;
    private static String sTimeOnlyFormat;
    private final String dateOnlyFormat;
    private final String dateTimeFormat;
    private final LocaleData localeData;
    private Formatter numberFormatter;
    private StringBuilder outputBuilder;
    private final String timeOnlyFormat;

    public TimeFormatter() {
        synchronized (TimeFormatter.class) {
            Locale locale = Locale.getDefault();
            if (sLocale == null || !locale.equals(sLocale)) {
                sLocale = locale;
                sLocaleData = LocaleData.get(locale);
                Resources r = Resources.getSystem();
                sTimeOnlyFormat = r.getString(R.string.time_of_day);
                sDateOnlyFormat = r.getString(R.string.month_day_year);
                sDateTimeFormat = r.getString(R.string.date_and_time);
            }
            this.dateTimeFormat = sDateTimeFormat;
            this.timeOnlyFormat = sTimeOnlyFormat;
            this.dateOnlyFormat = sDateOnlyFormat;
            this.localeData = sLocaleData;
        }
    }

    public String format(String pattern, ZoneInfo.WallTime wallTime, ZoneInfo zoneInfo) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            this.outputBuilder = stringBuilder;
            this.numberFormatter = new Formatter(stringBuilder, Locale.US);
            formatInternal(pattern, wallTime, zoneInfo);
            String result = stringBuilder.toString();
            if (this.localeData.zeroDigit != '0') {
                result = localizeDigits(result);
            }
            return result;
        } finally {
            this.outputBuilder = null;
            this.numberFormatter = null;
        }
    }

    private String localizeDigits(String s) {
        int length = s.length();
        int offsetToLocalizedDigits = this.localeData.zeroDigit - '0';
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                ch = (char) (ch + offsetToLocalizedDigits);
            }
            result.append(ch);
        }
        return result.toString();
    }

    private void formatInternal(String pattern, ZoneInfo.WallTime wallTime, ZoneInfo zoneInfo) {
        CharBuffer formatBuffer = CharBuffer.wrap(pattern);
        while (formatBuffer.remaining() > 0) {
            boolean outputCurrentChar = true;
            if (formatBuffer.get(formatBuffer.position()) == '%') {
                outputCurrentChar = handleToken(formatBuffer, wallTime, zoneInfo);
            }
            if (outputCurrentChar) {
                this.outputBuilder.append(formatBuffer.get(formatBuffer.position()));
            }
            formatBuffer.position(formatBuffer.position() + 1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:230:0x000a, code lost:
        continue;
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01e8, code lost:
        if (r20.getMonth() < 0) goto L_0x01fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01ee, code lost:
        if (r20.getMonth() < 12) goto L_0x01f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01f1, code lost:
        r7 = r0.localeData.shortMonthNames[r20.getMonth()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01fc, code lost:
        r7 = "?";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01fe, code lost:
        modifyAndAppend(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0201, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0221, code lost:
        r5 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleToken(java.nio.CharBuffer r19, libcore.util.ZoneInfo.WallTime r20, libcore.util.ZoneInfo r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = 0
            r5 = r4
        L_0x000a:
            int r6 = r19.remaining()
            r7 = 1
            if (r6 <= r7) goto L_0x0411
            int r6 = r19.position()
            int r6 = r6 + r7
            r1.position(r6)
            int r6 = r19.position()
            char r6 = r1.get(r6)
            r8 = 7
            r9 = 12
            switch(r6) {
                case 65: goto L_0x03f1;
                case 66: goto L_0x03b0;
                case 67: goto L_0x03a8;
                case 68: goto L_0x03a2;
                case 69: goto L_0x03a0;
                case 70: goto L_0x0399;
                case 71: goto L_0x0325;
                case 72: goto L_0x0307;
                case 73: goto L_0x02de;
                default: goto L_0x0027;
            }
        L_0x0027:
            switch(r6) {
                case 79: goto L_0x03a0;
                case 80: goto L_0x02c6;
                default: goto L_0x002a;
            }
        L_0x002a:
            switch(r6) {
                case 82: goto L_0x02c0;
                case 83: goto L_0x02a2;
                case 84: goto L_0x029c;
                case 85: goto L_0x0277;
                case 86: goto L_0x0325;
                case 87: goto L_0x0249;
                case 88: goto L_0x0243;
                case 89: goto L_0x023b;
                case 90: goto L_0x0224;
                default: goto L_0x002d;
            }
        L_0x002d:
            switch(r6) {
                case 94: goto L_0x0221;
                case 95: goto L_0x0221;
                default: goto L_0x0030;
            }
        L_0x0030:
            switch(r6) {
                case 97: goto L_0x0202;
                case 98: goto L_0x01e4;
                case 99: goto L_0x01de;
                case 100: goto L_0x01c0;
                case 101: goto L_0x01a2;
                default: goto L_0x0033;
            }
        L_0x0033:
            switch(r6) {
                case 103: goto L_0x0325;
                case 104: goto L_0x01e4;
                default: goto L_0x0036;
            }
        L_0x0036:
            switch(r6) {
                case 106: goto L_0x0183;
                case 107: goto L_0x0165;
                case 108: goto L_0x013c;
                case 109: goto L_0x011d;
                case 110: goto L_0x0115;
                default: goto L_0x0039;
            }
        L_0x0039:
            switch(r6) {
                case 114: goto L_0x010f;
                case 115: goto L_0x0101;
                case 116: goto L_0x00f9;
                case 117: goto L_0x00de;
                case 118: goto L_0x00d8;
                case 119: goto L_0x00c4;
                case 120: goto L_0x00be;
                case 121: goto L_0x00b6;
                case 122: goto L_0x007b;
                default: goto L_0x003c;
            }
        L_0x003c:
            switch(r6) {
                case 35: goto L_0x0221;
                case 43: goto L_0x0075;
                case 45: goto L_0x0221;
                case 48: goto L_0x0221;
                case 77: goto L_0x0057;
                case 112: goto L_0x0040;
                default: goto L_0x003f;
            }
        L_0x003f:
            return r7
        L_0x0040:
            int r8 = r20.getHour()
            if (r8 < r9) goto L_0x004d
            libcore.icu.LocaleData r8 = r0.localeData
            java.lang.String[] r8 = r8.amPm
            r7 = r8[r7]
            goto L_0x0053
        L_0x004d:
            libcore.icu.LocaleData r7 = r0.localeData
            java.lang.String[] r7 = r7.amPm
            r7 = r7[r4]
        L_0x0053:
            r0.modifyAndAppend(r7, r5)
            return r4
        L_0x0057:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%02d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getMinute()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x0075:
            java.lang.String r7 = "%a %b %e %H:%M:%S %Z %Y"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x007b:
            int r8 = r20.getIsDst()
            if (r8 >= 0) goto L_0x0082
            return r4
        L_0x0082:
            int r8 = r20.getGmtOffset()
            if (r8 >= 0) goto L_0x008c
            r9 = 45
            int r8 = -r8
            goto L_0x008e
        L_0x008c:
            r9 = 43
        L_0x008e:
            java.lang.StringBuilder r10 = r0.outputBuilder
            r10.append(r9)
            int r8 = r8 / 60
            int r10 = r8 / 60
            int r10 = r10 * 100
            int r11 = r8 % 60
            int r10 = r10 + r11
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r11 = "%04d"
            java.lang.String r12 = "%4d"
            java.lang.String r13 = "%d"
            java.lang.String r14 = "%04d"
            java.lang.String r11 = getFormat(r5, r11, r12, r13, r14)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)
            r7[r4] = r12
            r8.format(r11, r7)
            return r4
        L_0x00b6:
            int r8 = r20.getYear()
            r0.outputYear(r8, r4, r7, r5)
            return r4
        L_0x00be:
            java.lang.String r7 = r0.dateOnlyFormat
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x00c4:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%d"
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getWeekDay()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x00d8:
            java.lang.String r7 = "%e-%b-%Y"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x00de:
            int r9 = r20.getWeekDay()
            if (r9 != 0) goto L_0x00e5
            goto L_0x00e9
        L_0x00e5:
            int r8 = r20.getWeekDay()
        L_0x00e9:
            java.util.Formatter r9 = r0.numberFormatter
            java.lang.String r10 = "%d"
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r8)
            r7[r4] = r11
            r9.format(r10, r7)
            return r4
        L_0x00f9:
            java.lang.StringBuilder r7 = r0.outputBuilder
            r8 = 9
            r7.append(r8)
            return r4
        L_0x0101:
            int r7 = r20.mktime(r21)
            java.lang.StringBuilder r8 = r0.outputBuilder
            java.lang.String r9 = java.lang.Integer.toString(r7)
            r8.append(r9)
            return r4
        L_0x010f:
            java.lang.String r7 = "%I:%M:%S %p"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x0115:
            java.lang.StringBuilder r7 = r0.outputBuilder
            r8 = 10
            r7.append(r8)
            return r4
        L_0x011d:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%02d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r10 = new java.lang.Object[r7]
            int r11 = r20.getMonth()
            int r11 = r11 + r7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r11)
            r10[r4] = r7
            r8.format(r9, r10)
            return r4
        L_0x013c:
            int r8 = r20.getHour()
            int r8 = r8 % r9
            if (r8 == 0) goto L_0x014a
            int r8 = r20.getHour()
            int r9 = r8 % 12
        L_0x014a:
            r8 = r9
            java.util.Formatter r9 = r0.numberFormatter
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%2d"
            java.lang.String r12 = "%d"
            java.lang.String r13 = "%02d"
            java.lang.String r10 = getFormat(r5, r10, r11, r12, r13)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r8)
            r7[r4] = r11
            r9.format(r10, r7)
            return r4
        L_0x0165:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%2d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getHour()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x0183:
            int r8 = r20.getYearDay()
            int r8 = r8 + r7
            java.util.Formatter r9 = r0.numberFormatter
            java.lang.String r10 = "%03d"
            java.lang.String r11 = "%3d"
            java.lang.String r12 = "%d"
            java.lang.String r13 = "%03d"
            java.lang.String r10 = getFormat(r5, r10, r11, r12, r13)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r8)
            r7[r4] = r11
            r9.format(r10, r7)
            return r4
        L_0x01a2:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%2d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getMonthDay()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x01c0:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%02d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getMonthDay()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x01de:
            java.lang.String r7 = r0.dateTimeFormat
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x01e4:
            int r7 = r20.getMonth()
            if (r7 < 0) goto L_0x01fc
            int r7 = r20.getMonth()
            if (r7 < r9) goto L_0x01f1
            goto L_0x01fc
        L_0x01f1:
            libcore.icu.LocaleData r7 = r0.localeData
            java.lang.String[] r7 = r7.shortMonthNames
            int r8 = r20.getMonth()
            r7 = r7[r8]
            goto L_0x01fe
        L_0x01fc:
            java.lang.String r7 = "?"
        L_0x01fe:
            r0.modifyAndAppend(r7, r5)
            return r4
        L_0x0202:
            int r9 = r20.getWeekDay()
            if (r9 < 0) goto L_0x021b
            int r9 = r20.getWeekDay()
            if (r9 < r8) goto L_0x020f
            goto L_0x021b
        L_0x020f:
            libcore.icu.LocaleData r8 = r0.localeData
            java.lang.String[] r8 = r8.shortWeekdayNames
            int r9 = r20.getWeekDay()
            int r9 = r9 + r7
            r7 = r8[r9]
            goto L_0x021d
        L_0x021b:
            java.lang.String r7 = "?"
        L_0x021d:
            r0.modifyAndAppend(r7, r5)
            return r4
        L_0x0221:
            r5 = r6
            goto L_0x000a
        L_0x0224:
            int r8 = r20.getIsDst()
            if (r8 >= 0) goto L_0x022b
            return r4
        L_0x022b:
            int r8 = r20.getIsDst()
            if (r8 == 0) goto L_0x0232
            goto L_0x0233
        L_0x0232:
            r7 = r4
        L_0x0233:
            java.lang.String r8 = r3.getDisplayName(r7, r4)
            r0.modifyAndAppend(r8, r5)
            return r4
        L_0x023b:
            int r8 = r20.getYear()
            r0.outputYear(r8, r7, r7, r5)
            return r4
        L_0x0243:
            java.lang.String r7 = r0.timeOnlyFormat
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x0249:
            int r9 = r20.getYearDay()
            int r9 = r9 + r8
            int r10 = r20.getWeekDay()
            if (r10 == 0) goto L_0x025a
            int r10 = r20.getWeekDay()
            int r10 = r10 - r7
            goto L_0x025b
        L_0x025a:
            r10 = 6
        L_0x025b:
            int r9 = r9 - r10
            int r9 = r9 / r8
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r10 = "%02d"
            java.lang.String r11 = "%2d"
            java.lang.String r12 = "%d"
            java.lang.String r13 = "%02d"
            java.lang.String r10 = getFormat(r5, r10, r11, r12, r13)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)
            r7[r4] = r11
            r8.format(r10, r7)
            return r4
        L_0x0277:
            java.util.Formatter r9 = r0.numberFormatter
            java.lang.String r10 = "%02d"
            java.lang.String r11 = "%2d"
            java.lang.String r12 = "%d"
            java.lang.String r13 = "%02d"
            java.lang.String r10 = getFormat(r5, r10, r11, r12, r13)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r11 = r20.getYearDay()
            int r11 = r11 + r8
            int r12 = r20.getWeekDay()
            int r11 = r11 - r12
            int r11 = r11 / r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r11)
            r7[r4] = r8
            r9.format(r10, r7)
            return r4
        L_0x029c:
            java.lang.String r7 = "%H:%M:%S"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x02a2:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%02d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getSecond()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x02c0:
            java.lang.String r7 = "%H:%M"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x02c6:
            int r8 = r20.getHour()
            if (r8 < r9) goto L_0x02d3
            libcore.icu.LocaleData r8 = r0.localeData
            java.lang.String[] r8 = r8.amPm
            r7 = r8[r7]
            goto L_0x02d9
        L_0x02d3:
            libcore.icu.LocaleData r7 = r0.localeData
            java.lang.String[] r7 = r7.amPm
            r7 = r7[r4]
        L_0x02d9:
            r8 = -1
            r0.modifyAndAppend(r7, r8)
            return r4
        L_0x02de:
            int r8 = r20.getHour()
            int r8 = r8 % r9
            if (r8 == 0) goto L_0x02ec
            int r8 = r20.getHour()
            int r9 = r8 % 12
        L_0x02ec:
            r8 = r9
            java.util.Formatter r9 = r0.numberFormatter
            java.lang.String r10 = "%02d"
            java.lang.String r11 = "%2d"
            java.lang.String r12 = "%d"
            java.lang.String r13 = "%02d"
            java.lang.String r10 = getFormat(r5, r10, r11, r12, r13)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r8)
            r7[r4] = r11
            r9.format(r10, r7)
            return r4
        L_0x0307:
            java.util.Formatter r8 = r0.numberFormatter
            java.lang.String r9 = "%02d"
            java.lang.String r10 = "%2d"
            java.lang.String r11 = "%d"
            java.lang.String r12 = "%02d"
            java.lang.String r9 = getFormat(r5, r9, r10, r11, r12)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            int r10 = r20.getHour()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r7[r4] = r10
            r8.format(r9, r7)
            return r4
        L_0x0325:
            int r9 = r20.getYear()
            int r10 = r20.getYearDay()
            int r11 = r20.getWeekDay()
        L_0x0331:
            boolean r12 = isLeap(r9)
            if (r12 == 0) goto L_0x033a
            r12 = 366(0x16e, float:5.13E-43)
            goto L_0x033c
        L_0x033a:
            r12 = 365(0x16d, float:5.11E-43)
        L_0x033c:
            int r15 = r10 + 11
            int r15 = r15 - r11
            int r15 = r15 % r8
            int r15 = r15 + -3
            int r16 = r12 % 7
            int r13 = r15 - r16
            r14 = -3
            if (r13 >= r14) goto L_0x034b
            int r13 = r13 + 7
        L_0x034b:
            int r13 = r13 + r12
            if (r10 < r13) goto L_0x0352
            int r9 = r9 + 1
            r8 = 1
            goto L_0x035a
        L_0x0352:
            if (r10 < r15) goto L_0x0387
            int r14 = r10 - r15
            int r14 = r14 / r8
            int r8 = r14 + 1
        L_0x035a:
            r12 = 86
            if (r6 != r12) goto L_0x037a
            java.util.Formatter r12 = r0.numberFormatter
            java.lang.String r13 = "%02d"
            java.lang.String r14 = "%2d"
            java.lang.String r15 = "%d"
            java.lang.String r4 = "%02d"
            java.lang.String r4 = getFormat(r5, r13, r14, r15, r4)
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r13 = java.lang.Integer.valueOf(r8)
            r14 = 0
            r7[r14] = r13
            r12.format(r4, r7)
            goto L_0x0386
        L_0x037a:
            r14 = r4
            r4 = 103(0x67, float:1.44E-43)
            if (r6 != r4) goto L_0x0383
            r0.outputYear(r9, r14, r7, r5)
            goto L_0x0386
        L_0x0383:
            r0.outputYear(r9, r7, r7, r5)
        L_0x0386:
            return r14
        L_0x0387:
            int r9 = r9 + -1
            boolean r4 = isLeap(r9)
            if (r4 == 0) goto L_0x0392
            r17 = 366(0x16e, float:5.13E-43)
            goto L_0x0394
        L_0x0392:
            r17 = 365(0x16d, float:5.11E-43)
        L_0x0394:
            int r10 = r10 + r17
            r4 = 0
            goto L_0x0331
        L_0x0399:
            java.lang.String r4 = "%Y-%m-%d"
            r0.formatInternal(r4, r2, r3)
            r4 = 0
            return r4
        L_0x03a0:
            goto L_0x000a
        L_0x03a2:
            java.lang.String r7 = "%m/%d/%y"
            r0.formatInternal(r7, r2, r3)
            return r4
        L_0x03a8:
            int r8 = r20.getYear()
            r0.outputYear(r8, r7, r4, r5)
            return r4
        L_0x03b0:
            r4 = 45
            if (r5 != r4) goto L_0x03d2
            int r4 = r20.getMonth()
            if (r4 < 0) goto L_0x03cc
            int r4 = r20.getMonth()
            if (r4 < r9) goto L_0x03c1
            goto L_0x03cc
        L_0x03c1:
            libcore.icu.LocaleData r4 = r0.localeData
            java.lang.String[] r4 = r4.longStandAloneMonthNames
            int r7 = r20.getMonth()
            r4 = r4[r7]
            goto L_0x03ce
        L_0x03cc:
            java.lang.String r4 = "?"
        L_0x03ce:
            r0.modifyAndAppend(r4, r5)
            goto L_0x03ef
        L_0x03d2:
            int r4 = r20.getMonth()
            if (r4 < 0) goto L_0x03ea
            int r4 = r20.getMonth()
            if (r4 < r9) goto L_0x03df
            goto L_0x03ea
        L_0x03df:
            libcore.icu.LocaleData r4 = r0.localeData
            java.lang.String[] r4 = r4.longMonthNames
            int r7 = r20.getMonth()
            r4 = r4[r7]
            goto L_0x03ec
        L_0x03ea:
            java.lang.String r4 = "?"
        L_0x03ec:
            r0.modifyAndAppend(r4, r5)
        L_0x03ef:
            r4 = 0
            return r4
        L_0x03f1:
            int r4 = r20.getWeekDay()
            if (r4 < 0) goto L_0x040a
            int r4 = r20.getWeekDay()
            if (r4 < r8) goto L_0x03fe
            goto L_0x040a
        L_0x03fe:
            libcore.icu.LocaleData r4 = r0.localeData
            java.lang.String[] r4 = r4.longWeekdayNames
            int r8 = r20.getWeekDay()
            int r8 = r8 + r7
            r4 = r4[r8]
            goto L_0x040c
        L_0x040a:
            java.lang.String r4 = "?"
        L_0x040c:
            r0.modifyAndAppend(r4, r5)
            r4 = 0
            return r4
        L_0x0411:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.format.TimeFormatter.handleToken(java.nio.CharBuffer, libcore.util.ZoneInfo$WallTime, libcore.util.ZoneInfo):boolean");
    }

    private void modifyAndAppend(CharSequence str, int modifier) {
        int i = 0;
        if (modifier == -1) {
            while (true) {
                int i2 = i;
                if (i2 < str.length()) {
                    this.outputBuilder.append(brokenToLower(str.charAt(i2)));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else if (modifier == 35) {
            while (true) {
                int i3 = i;
                if (i3 < str.length()) {
                    char c = str.charAt(i3);
                    if (brokenIsUpper(c)) {
                        c = brokenToLower(c);
                    } else if (brokenIsLower(c)) {
                        c = brokenToUpper(c);
                    }
                    this.outputBuilder.append(c);
                    i = i3 + 1;
                } else {
                    return;
                }
            }
        } else if (modifier != 94) {
            this.outputBuilder.append(str);
        } else {
            while (true) {
                int i4 = i;
                if (i4 < str.length()) {
                    this.outputBuilder.append(brokenToUpper(str.charAt(i4)));
                    i = i4 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private void outputYear(int value, boolean outputTop, boolean outputBottom, int modifier) {
        int trail = value % 100;
        int lead = (value / 100) + (trail / 100);
        int trail2 = trail % 100;
        if (trail2 < 0 && lead > 0) {
            trail2 += 100;
            lead--;
        } else if (lead < 0 && trail2 > 0) {
            trail2 -= 100;
            lead++;
        }
        if (outputTop) {
            if (lead != 0 || trail2 >= 0) {
                this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), new Object[]{Integer.valueOf(lead)});
            } else {
                this.outputBuilder.append("-0");
            }
        }
        if (outputBottom) {
            this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), new Object[]{Integer.valueOf(trail2 < 0 ? -trail2 : trail2)});
        }
    }

    private static String getFormat(int modifier, String normal, String underscore, String dash, String zero) {
        if (modifier == 45) {
            return dash;
        }
        if (modifier == 48) {
            return zero;
        }
        if (modifier != 95) {
            return normal;
        }
        return underscore;
    }

    private static boolean isLeap(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    private static boolean brokenIsUpper(char toCheck) {
        return toCheck >= 'A' && toCheck <= 'Z';
    }

    private static boolean brokenIsLower(char toCheck) {
        return toCheck >= 'a' && toCheck <= 'z';
    }

    private static char brokenToLower(char input) {
        if (input < 'A' || input > 'Z') {
            return input;
        }
        return (char) ((input - 'A') + 97);
    }

    private static char brokenToUpper(char input) {
        if (input < 'a' || input > 'z') {
            return input;
        }
        return (char) ((input - 'a') + 65);
    }
}
