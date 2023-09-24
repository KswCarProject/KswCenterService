package android.text.format;

import android.content.res.Resources;
import com.android.internal.C3132R;
import java.nio.CharBuffer;
import java.util.Locale;
import libcore.icu.LocaleData;
import libcore.util.ZoneInfo;

/* loaded from: classes4.dex */
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
    private java.util.Formatter numberFormatter;
    private StringBuilder outputBuilder;
    private final String timeOnlyFormat;

    public TimeFormatter() {
        synchronized (TimeFormatter.class) {
            Locale locale = Locale.getDefault();
            if (sLocale == null || !locale.equals(sLocale)) {
                sLocale = locale;
                sLocaleData = LocaleData.get(locale);
                Resources r = Resources.getSystem();
                sTimeOnlyFormat = r.getString(C3132R.string.time_of_day);
                sDateOnlyFormat = r.getString(C3132R.string.month_day_year);
                sDateTimeFormat = r.getString(C3132R.string.date_and_time);
            }
            this.dateTimeFormat = sDateTimeFormat;
            this.timeOnlyFormat = sTimeOnlyFormat;
            this.dateOnlyFormat = sDateOnlyFormat;
            this.localeData = sLocaleData;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.StringBuilder, java.util.Formatter] */
    public String format(String pattern, ZoneInfo.WallTime wallTime, ZoneInfo zoneInfo) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            this.outputBuilder = stringBuilder;
            this.numberFormatter = new java.util.Formatter(stringBuilder, Locale.US);
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
            char currentChar = formatBuffer.get(formatBuffer.position());
            if (currentChar == '%') {
                outputCurrentChar = handleToken(formatBuffer, wallTime, zoneInfo);
            }
            if (outputCurrentChar) {
                this.outputBuilder.append(formatBuffer.get(formatBuffer.position()));
            }
            formatBuffer.position(formatBuffer.position() + 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x0325, code lost:
        r9 = r20.getYear();
        r10 = r20.getYearDay();
        r11 = r20.getWeekDay();
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0335, code lost:
        if (isLeap(r9) == false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0337, code lost:
        r12 = 366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x033a, code lost:
        r12 = 365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x033c, code lost:
        r15 = (((r10 + 11) - r11) % 7) - 3;
        r13 = r15 - (r12 % 7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0347, code lost:
        if (r13 >= (-3)) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0349, code lost:
        r13 = r13 + 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x034c, code lost:
        if (r10 < (r13 + r12)) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x034e, code lost:
        r9 = r9 + 1;
        r8 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0352, code lost:
        if (r10 < r15) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0354, code lost:
        r8 = ((r10 - r15) / 7) + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x035d, code lost:
        if (r6 != 86) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x035f, code lost:
        r18.numberFormatter.format(getFormat(r5, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x037a, code lost:
        r14 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x037d, code lost:
        if (r6 != 103) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x037f, code lost:
        outputYear(r9, r14, true, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0383, code lost:
        outputYear(r9, true, true, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0386, code lost:
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0387, code lost:
        r9 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x038d, code lost:
        if (isLeap(r9) == false) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x038f, code lost:
        r17 = 366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0392, code lost:
        r17 = 365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0394, code lost:
        r10 = r10 + r17;
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:?, code lost:
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01e8, code lost:
        if (r20.getMonth() < 0) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01ee, code lost:
        if (r20.getMonth() < 12) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01f1, code lost:
        r7 = r18.localeData.shortMonthNames[r20.getMonth()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01fc, code lost:
        r7 = "?";
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01fe, code lost:
        modifyAndAppend(r7, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0201, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean handleToken(CharBuffer formatBuffer, ZoneInfo.WallTime wallTime, ZoneInfo zoneInfo) {
        String str;
        String str2;
        char sign;
        String str3;
        boolean z = false;
        int modifier = 0;
        while (true) {
            if (formatBuffer.remaining() > 1) {
                formatBuffer.position(formatBuffer.position() + 1);
                int currentChar = formatBuffer.get(formatBuffer.position());
                switch (currentChar) {
                    case 65:
                        modifyAndAppend((wallTime.getWeekDay() < 0 || wallTime.getWeekDay() >= 7) ? "?" : this.localeData.longWeekdayNames[wallTime.getWeekDay() + 1], modifier);
                        return false;
                    case 66:
                        if (modifier == 45) {
                            if (wallTime.getMonth() < 0 || wallTime.getMonth() >= 12) {
                                str = "?";
                            } else {
                                str = this.localeData.longStandAloneMonthNames[wallTime.getMonth()];
                            }
                            modifyAndAppend(str, modifier);
                            return false;
                        }
                        modifyAndAppend((wallTime.getMonth() < 0 || wallTime.getMonth() >= 12) ? "?" : this.localeData.longMonthNames[wallTime.getMonth()], modifier);
                        return false;
                    case 67:
                        outputYear(wallTime.getYear(), true, false, modifier);
                        return false;
                    case 68:
                        formatInternal("%m/%d/%y", wallTime, zoneInfo);
                        return false;
                    case 69:
                        break;
                    case 70:
                        formatInternal("%Y-%m-%d", wallTime, zoneInfo);
                        return false;
                    case 71:
                        break;
                    case 72:
                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour()));
                        return false;
                    case 73:
                        int hour = wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12;
                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(hour));
                        return false;
                    default:
                        switch (currentChar) {
                            case 79:
                                break;
                            case 80:
                                if (wallTime.getHour() >= 12) {
                                    str2 = this.localeData.amPm[1];
                                } else {
                                    str2 = this.localeData.amPm[0];
                                }
                                modifyAndAppend(str2, -1);
                                return false;
                            default:
                                switch (currentChar) {
                                    case 82:
                                        formatInternal(DateUtils.HOUR_MINUTE_24, wallTime, zoneInfo);
                                        return false;
                                    case 83:
                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getSecond()));
                                        return false;
                                    case 84:
                                        formatInternal("%H:%M:%S", wallTime, zoneInfo);
                                        return false;
                                    case 85:
                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(((wallTime.getYearDay() + 7) - wallTime.getWeekDay()) / 7));
                                        return false;
                                    case 86:
                                        break;
                                    case 87:
                                        int n = ((wallTime.getYearDay() + 7) - (wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() - 1 : 6)) / 7;
                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(n));
                                        return false;
                                    case 88:
                                        formatInternal(this.timeOnlyFormat, wallTime, zoneInfo);
                                        return false;
                                    case 89:
                                        outputYear(wallTime.getYear(), true, true, modifier);
                                        return false;
                                    case 90:
                                        if (wallTime.getIsDst() < 0) {
                                            return false;
                                        }
                                        boolean isDst = wallTime.getIsDst() != 0;
                                        modifyAndAppend(zoneInfo.getDisplayName(isDst, 0), modifier);
                                        return false;
                                    default:
                                        switch (currentChar) {
                                            case 94:
                                            case 95:
                                                break;
                                            default:
                                                switch (currentChar) {
                                                    case 97:
                                                        modifyAndAppend((wallTime.getWeekDay() < 0 || wallTime.getWeekDay() >= 7) ? "?" : this.localeData.shortWeekdayNames[wallTime.getWeekDay() + 1], modifier);
                                                        return false;
                                                    case 98:
                                                        break;
                                                    case 99:
                                                        formatInternal(this.dateTimeFormat, wallTime, zoneInfo);
                                                        return false;
                                                    case 100:
                                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonthDay()));
                                                        return false;
                                                    case 101:
                                                        this.numberFormatter.format(getFormat(modifier, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonthDay()));
                                                        return false;
                                                    default:
                                                        switch (currentChar) {
                                                            case 103:
                                                                break;
                                                            case 104:
                                                                break;
                                                            default:
                                                                switch (currentChar) {
                                                                    case 106:
                                                                        int yearDay = wallTime.getYearDay() + 1;
                                                                        this.numberFormatter.format(getFormat(modifier, "%03d", "%3d", "%d", "%03d"), Integer.valueOf(yearDay));
                                                                        return false;
                                                                    case 107:
                                                                        this.numberFormatter.format(getFormat(modifier, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour()));
                                                                        return false;
                                                                    case 108:
                                                                        int n2 = wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12;
                                                                        this.numberFormatter.format(getFormat(modifier, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(n2));
                                                                        return false;
                                                                    case 109:
                                                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonth() + 1));
                                                                        return false;
                                                                    case 110:
                                                                        this.outputBuilder.append('\n');
                                                                        return false;
                                                                    default:
                                                                        switch (currentChar) {
                                                                            case 114:
                                                                                formatInternal("%I:%M:%S %p", wallTime, zoneInfo);
                                                                                return false;
                                                                            case 115:
                                                                                int timeInSeconds = wallTime.mktime(zoneInfo);
                                                                                this.outputBuilder.append(Integer.toString(timeInSeconds));
                                                                                return false;
                                                                            case 116:
                                                                                this.outputBuilder.append('\t');
                                                                                return false;
                                                                            case 117:
                                                                                int day = wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() : 7;
                                                                                this.numberFormatter.format("%d", Integer.valueOf(day));
                                                                                return false;
                                                                            case 118:
                                                                                formatInternal("%e-%b-%Y", wallTime, zoneInfo);
                                                                                return false;
                                                                            case 119:
                                                                                this.numberFormatter.format("%d", Integer.valueOf(wallTime.getWeekDay()));
                                                                                return false;
                                                                            case 120:
                                                                                formatInternal(this.dateOnlyFormat, wallTime, zoneInfo);
                                                                                return false;
                                                                            case 121:
                                                                                outputYear(wallTime.getYear(), false, true, modifier);
                                                                                return false;
                                                                            case 122:
                                                                                if (wallTime.getIsDst() < 0) {
                                                                                    return false;
                                                                                }
                                                                                int diff = wallTime.getGmtOffset();
                                                                                if (diff < 0) {
                                                                                    sign = '-';
                                                                                    diff = -diff;
                                                                                } else {
                                                                                    sign = '+';
                                                                                }
                                                                                this.outputBuilder.append(sign);
                                                                                int diff2 = diff / 60;
                                                                                this.numberFormatter.format(getFormat(modifier, "%04d", "%4d", "%d", "%04d"), Integer.valueOf(((diff2 / 60) * 100) + (diff2 % 60)));
                                                                                return false;
                                                                            default:
                                                                                switch (currentChar) {
                                                                                    case 35:
                                                                                    case 45:
                                                                                    case 48:
                                                                                        break;
                                                                                    case 43:
                                                                                        formatInternal("%a %b %e %H:%M:%S %Z %Y", wallTime, zoneInfo);
                                                                                        return false;
                                                                                    case 77:
                                                                                        this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMinute()));
                                                                                        return false;
                                                                                    case 112:
                                                                                        if (wallTime.getHour() >= 12) {
                                                                                            str3 = this.localeData.amPm[1];
                                                                                        } else {
                                                                                            str3 = this.localeData.amPm[0];
                                                                                        }
                                                                                        modifyAndAppend(str3, modifier);
                                                                                        return false;
                                                                                    default:
                                                                                        return true;
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                        modifier = currentChar;
                                        continue;
                                }
                        }
                }
            } else {
                return true;
            }
        }
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
            if (lead == 0 && trail2 < 0) {
                this.outputBuilder.append("-0");
            } else {
                this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(lead));
            }
        }
        if (outputBottom) {
            int n = trail2 < 0 ? -trail2 : trail2;
            this.numberFormatter.format(getFormat(modifier, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(n));
        }
    }

    private static String getFormat(int modifier, String normal, String underscore, String dash, String zero) {
        if (modifier != 45) {
            if (modifier != 48) {
                if (modifier == 95) {
                    return underscore;
                }
                return normal;
            }
            return zero;
        }
        return dash;
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
        if (input >= 'A' && input <= 'Z') {
            return (char) ((input - 'A') + 97);
        }
        return input;
    }

    private static char brokenToUpper(char input) {
        if (input >= 'a' && input <= 'z') {
            return (char) ((input - 'a') + 65);
        }
        return input;
    }
}
