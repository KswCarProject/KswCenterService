package android.util;

import android.annotation.UnsupportedAppUsage;
import android.p007os.SystemClock;
import android.text.format.DateFormat;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import libcore.timezone.CountryTimeZones;
import libcore.timezone.TimeZoneFinder;
import libcore.timezone.ZoneInfoDB;

/* loaded from: classes4.dex */
public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    public static final long NANOS_PER_MS = 1000000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static SimpleDateFormat sLoggingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sDumpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Object sFormatSync = new Object();
    private static char[] sFormatStr = new char[29];
    private static char[] sTmpFormatStr = new char[29];

    public static TimeZone getTimeZone(int offset, boolean dst, long when, String country) {
        android.icu.util.TimeZone icuTimeZone = getIcuTimeZone(offset, dst, when, country);
        if (icuTimeZone != null) {
            return TimeZone.getTimeZone(icuTimeZone.getID());
        }
        return null;
    }

    private static android.icu.util.TimeZone getIcuTimeZone(int offset, boolean dst, long when, String country) {
        if (country == null) {
            return null;
        }
        android.icu.util.TimeZone bias = android.icu.util.TimeZone.getDefault();
        return TimeZoneFinder.getInstance().lookupTimeZoneByCountryAndOffset(country, offset, dst, when, bias);
    }

    public static List<String> getTimeZoneIdsForCountryCode(String countryCode) {
        if (countryCode == null) {
            throw new NullPointerException("countryCode == null");
        }
        TimeZoneFinder timeZoneFinder = TimeZoneFinder.getInstance();
        CountryTimeZones countryTimeZones = timeZoneFinder.lookupCountryTimeZones(countryCode.toLowerCase());
        if (countryTimeZones == null) {
            return null;
        }
        List<String> timeZoneIds = new ArrayList<>();
        for (CountryTimeZones.TimeZoneMapping timeZoneMapping : countryTimeZones.getTimeZoneMappings()) {
            if (timeZoneMapping.showInPicker) {
                timeZoneIds.add(timeZoneMapping.timeZoneId);
            }
        }
        return Collections.unmodifiableList(timeZoneIds);
    }

    public static String getTimeZoneDatabaseVersion() {
        return ZoneInfoDB.getInstance().getVersion();
    }

    private static int accumField(int amt, int suffix, boolean always, int zeropad) {
        int num = 0;
        if (amt > 999) {
            while (amt != 0) {
                num++;
                amt /= 10;
            }
            return num + suffix;
        } else if (amt > 99 || (always && zeropad >= 3)) {
            return suffix + 3;
        } else {
            if (amt > 9 || (always && zeropad >= 2)) {
                return suffix + 2;
            }
            if (!always && amt <= 0) {
                return 0;
            }
            return suffix + 1;
        }
    }

    private static int printFieldLocked(char[] formatStr, int amt, char suffix, int pos, boolean always, int zeropad) {
        if (always || amt > 0) {
            if (amt > 999) {
                int tmp = 0;
                while (amt != 0 && tmp < sTmpFormatStr.length) {
                    sTmpFormatStr[tmp] = (char) ((amt % 10) + 48);
                    tmp++;
                    amt /= 10;
                }
                for (int tmp2 = tmp - 1; tmp2 >= 0; tmp2--) {
                    formatStr[pos] = sTmpFormatStr[tmp2];
                    pos++;
                }
            } else {
                if ((always && zeropad >= 3) || amt > 99) {
                    int dig = amt / 100;
                    formatStr[pos] = (char) (dig + 48);
                    pos++;
                    amt -= dig * 100;
                }
                if ((always && zeropad >= 2) || amt > 9 || pos != pos) {
                    int dig2 = amt / 10;
                    formatStr[pos] = (char) (dig2 + 48);
                    pos++;
                    amt -= dig2 * 10;
                }
                formatStr[pos] = (char) (amt + 48);
                pos++;
            }
            formatStr[pos] = suffix;
            return pos + 1;
        }
        return pos;
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0135, code lost:
        if (r9 != r7) goto L70;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int formatDurationLocked(long duration, int fieldLen) {
        char c;
        int hours;
        int seconds;
        int minutes;
        int start;
        int myLen;
        long duration2 = duration;
        if (sFormatStr.length < fieldLen) {
            sFormatStr = new char[fieldLen];
        }
        char[] formatStr = sFormatStr;
        if (duration2 == 0) {
            int pos = 0;
            int fieldLen2 = fieldLen - 1;
            while (pos < fieldLen2) {
                formatStr[pos] = ' ';
                pos++;
            }
            formatStr[pos] = '0';
            return pos + 1;
        }
        if (duration2 > 0) {
            c = '+';
        } else {
            c = '-';
            duration2 = -duration2;
        }
        char prefix = c;
        int millis = (int) (duration2 % 1000);
        int seconds2 = (int) Math.floor(duration2 / 1000);
        int days = 0;
        if (seconds2 >= SECONDS_PER_DAY) {
            days = seconds2 / SECONDS_PER_DAY;
            seconds2 -= SECONDS_PER_DAY * days;
        }
        int days2 = days;
        if (seconds2 >= 3600) {
            int hours2 = seconds2 / 3600;
            seconds2 -= hours2 * 3600;
            hours = hours2;
        } else {
            hours = 0;
        }
        if (seconds2 >= 60) {
            int minutes2 = seconds2 / 60;
            seconds = seconds2 - (minutes2 * 60);
            minutes = minutes2;
        } else {
            seconds = seconds2;
            minutes = 0;
        }
        int pos2 = 0;
        int pos3 = 3;
        if (fieldLen != 0) {
            int myLen2 = accumField(days2, 1, false, 0);
            int myLen3 = myLen2 + accumField(hours, 1, myLen2 > 0, 2);
            int myLen4 = myLen3 + accumField(minutes, 1, myLen3 > 0, 2);
            for (int myLen5 = myLen + accumField(millis, 2, true, myLen4 + accumField(seconds, 1, myLen4 > 0, 2) > 0 ? 3 : 0) + 1; myLen5 < fieldLen; myLen5++) {
                formatStr[pos2] = ' ';
                pos2++;
            }
        }
        formatStr[pos2] = prefix;
        int pos4 = pos2 + 1;
        boolean zeropad = fieldLen != 0;
        int pos5 = printFieldLocked(formatStr, days2, DateFormat.DATE, pos4, false, 0);
        int pos6 = printFieldLocked(formatStr, hours, DateFormat.HOUR, pos5, pos5 != pos4, zeropad ? 2 : 0);
        int pos7 = printFieldLocked(formatStr, minutes, DateFormat.MINUTE, pos6, pos6 != pos4, zeropad ? 2 : 0);
        int pos8 = printFieldLocked(formatStr, seconds, 's', pos7, pos7 != pos4, zeropad ? 2 : 0);
        if (zeropad) {
            start = pos4;
        } else {
            start = pos4;
        }
        pos3 = 0;
        int pos9 = printFieldLocked(formatStr, millis, DateFormat.MINUTE, pos8, true, pos3);
        formatStr[pos9] = 's';
        return pos9 + 1;
    }

    public static void formatDuration(long duration, StringBuilder builder) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, 0);
            builder.append(sFormatStr, 0, len);
        }
    }

    public static void formatDuration(long duration, StringBuilder builder, int fieldLen) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, fieldLen);
            builder.append(sFormatStr, 0, len);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static void formatDuration(long duration, PrintWriter pw, int fieldLen) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, fieldLen);
            pw.print(new String(sFormatStr, 0, len));
        }
    }

    public static String formatDuration(long duration) {
        String str;
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, 0);
            str = new String(sFormatStr, 0, len);
        }
        return str;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static void formatDuration(long duration, PrintWriter pw) {
        formatDuration(duration, pw, 0);
    }

    public static void formatDuration(long time, long now, PrintWriter pw) {
        if (time == 0) {
            pw.print("--");
        } else {
            formatDuration(time - now, pw, 0);
        }
    }

    public static String formatUptime(long time) {
        long diff = time - SystemClock.uptimeMillis();
        if (diff > 0) {
            return time + " (in " + diff + " ms)";
        } else if (diff < 0) {
            return time + " (" + (-diff) + " ms ago)";
        } else {
            return time + " (now)";
        }
    }

    @UnsupportedAppUsage
    public static String logTimeOfDay(long millis) {
        Calendar c = Calendar.getInstance();
        if (millis >= 0) {
            c.setTimeInMillis(millis);
            return String.format("%tm-%td %tH:%tM:%tS.%tL", c, c, c, c, c, c);
        }
        return Long.toString(millis);
    }

    public static String formatForLogging(long millis) {
        if (millis <= 0) {
            return "unknown";
        }
        return sLoggingFormat.format(new Date(millis));
    }

    public static void dumpTime(PrintWriter pw, long time) {
        pw.print(sDumpDateFormat.format(new Date(time)));
    }

    public static void dumpTimeWithDelta(PrintWriter pw, long time, long now) {
        pw.print(sDumpDateFormat.format(new Date(time)));
        if (time == now) {
            pw.print(" (now)");
            return;
        }
        pw.print(" (");
        formatDuration(time, now, pw);
        pw.print(")");
    }
}
