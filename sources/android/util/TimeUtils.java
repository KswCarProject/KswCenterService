package android.util;

import android.annotation.UnsupportedAppUsage;
import android.os.SystemClock;
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

public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    public static final long NANOS_PER_MS = 1000000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    public static final SimpleDateFormat sDumpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static char[] sFormatStr = new char[29];
    private static final Object sFormatSync = new Object();
    private static SimpleDateFormat sLoggingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        return TimeZoneFinder.getInstance().lookupTimeZoneByCountryAndOffset(country, offset, dst, when, android.icu.util.TimeZone.getDefault());
    }

    public static List<String> getTimeZoneIdsForCountryCode(String countryCode) {
        if (countryCode != null) {
            CountryTimeZones countryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(countryCode.toLowerCase());
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
        throw new NullPointerException("countryCode == null");
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
            if (always || amt > 0) {
                return suffix + 1;
            }
            return 0;
        }
    }

    private static int printFieldLocked(char[] formatStr, int amt, char suffix, int pos, boolean always, int zeropad) {
        if (!always && amt <= 0) {
            return pos;
        }
        int startPos = pos;
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
            if ((always && zeropad >= 2) || amt > 9 || startPos != pos) {
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

    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0135, code lost:
        if (r9 != r7) goto L_0x013c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int formatDurationLocked(long r27, int r29) {
        /*
            r0 = r27
            r2 = r29
            char[] r3 = sFormatStr
            int r3 = r3.length
            if (r3 >= r2) goto L_0x000d
            char[] r3 = new char[r2]
            sFormatStr = r3
        L_0x000d:
            char[] r3 = sFormatStr
            r4 = 0
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            r7 = 32
            if (r6 != 0) goto L_0x0029
            r4 = 0
            int r2 = r2 + -1
        L_0x001a:
            if (r4 >= r2) goto L_0x0022
            int r5 = r4 + 1
            r3[r4] = r7
            r4 = r5
            goto L_0x001a
        L_0x0022:
            r5 = 48
            r3[r4] = r5
            int r5 = r4 + 1
            return r5
        L_0x0029:
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0031
            r4 = 43
        L_0x002f:
            r10 = r4
            goto L_0x0035
        L_0x0031:
            r4 = 45
            long r0 = -r0
            goto L_0x002f
        L_0x0035:
            r4 = 1000(0x3e8, double:4.94E-321)
            long r8 = r0 % r4
            int r11 = (int) r8
            long r4 = r0 / r4
            double r4 = (double) r4
            double r4 = java.lang.Math.floor(r4)
            int r4 = (int) r4
            r5 = 0
            r6 = 0
            r8 = 0
            r9 = 86400(0x15180, float:1.21072E-40)
            if (r4 < r9) goto L_0x004e
            int r5 = r4 / r9
            int r9 = r9 * r5
            int r4 = r4 - r9
        L_0x004e:
            r12 = r5
            r5 = 3600(0xe10, float:5.045E-42)
            if (r4 < r5) goto L_0x005a
            int r5 = r4 / 3600
            int r6 = r5 * 3600
            int r4 = r4 - r6
            r13 = r5
            goto L_0x005b
        L_0x005a:
            r13 = r6
        L_0x005b:
            r5 = 60
            if (r4 < r5) goto L_0x0067
            int r5 = r4 / 60
            int r6 = r5 * 60
            int r4 = r4 - r6
            r15 = r4
            r14 = r5
            goto L_0x0069
        L_0x0067:
            r15 = r4
            r14 = r8
        L_0x0069:
            r4 = 0
            r16 = 3
            r9 = 2
            r8 = 0
            r6 = 1
            if (r2 == 0) goto L_0x00a7
            int r5 = accumField(r12, r6, r8, r8)
            if (r5 <= 0) goto L_0x0079
            r8 = r6
        L_0x0079:
            int r8 = accumField(r13, r6, r8, r9)
            int r5 = r5 + r8
            if (r5 <= 0) goto L_0x0082
            r8 = r6
            goto L_0x0083
        L_0x0082:
            r8 = 0
        L_0x0083:
            int r8 = accumField(r14, r6, r8, r9)
            int r5 = r5 + r8
            if (r5 <= 0) goto L_0x008c
            r8 = r6
            goto L_0x008d
        L_0x008c:
            r8 = 0
        L_0x008d:
            int r8 = accumField(r15, r6, r8, r9)
            int r5 = r5 + r8
            if (r5 <= 0) goto L_0x0097
            r8 = r16
            goto L_0x0098
        L_0x0097:
            r8 = 0
        L_0x0098:
            int r8 = accumField(r11, r9, r6, r8)
            int r8 = r8 + r6
            int r5 = r5 + r8
        L_0x009e:
            if (r5 >= r2) goto L_0x00a7
            r3[r4] = r7
            int r4 = r4 + 1
            int r5 = r5 + 1
            goto L_0x009e
        L_0x00a7:
            r3[r4] = r10
            int r18 = r4 + 1
            r8 = r18
            if (r2 == 0) goto L_0x00b1
            r4 = r6
            goto L_0x00b2
        L_0x00b1:
            r4 = 0
        L_0x00b2:
            r19 = r4
            r7 = 100
            r20 = 0
            r21 = 0
            r4 = r3
            r5 = r12
            r22 = r6
            r6 = r7
            r7 = r18
            r23 = r8
            r17 = 0
            r8 = r20
            r20 = r9
            r9 = r21
            int r9 = printFieldLocked(r4, r5, r6, r7, r8, r9)
            r6 = 104(0x68, float:1.46E-43)
            r8 = r23
            if (r9 == r8) goto L_0x00d8
            r18 = r22
            goto L_0x00da
        L_0x00d8:
            r18 = r17
        L_0x00da:
            if (r19 == 0) goto L_0x00df
            r21 = r20
            goto L_0x00e1
        L_0x00df:
            r21 = r17
        L_0x00e1:
            r4 = r3
            r5 = r13
            r7 = r9
            r24 = r8
            r8 = r18
            r18 = r9
            r9 = r21
            int r9 = printFieldLocked(r4, r5, r6, r7, r8, r9)
            r6 = 109(0x6d, float:1.53E-43)
            r8 = r24
            if (r9 == r8) goto L_0x00f9
            r18 = r22
            goto L_0x00fb
        L_0x00f9:
            r18 = r17
        L_0x00fb:
            if (r19 == 0) goto L_0x0100
            r21 = r20
            goto L_0x0102
        L_0x0100:
            r21 = r17
        L_0x0102:
            r4 = r3
            r5 = r14
            r7 = r9
            r25 = r8
            r8 = r18
            r18 = r9
            r9 = r21
            int r9 = printFieldLocked(r4, r5, r6, r7, r8, r9)
            r6 = 115(0x73, float:1.61E-43)
            r8 = r25
            if (r9 == r8) goto L_0x0118
            goto L_0x011a
        L_0x0118:
            r22 = r17
        L_0x011a:
            if (r19 == 0) goto L_0x011d
            goto L_0x011f
        L_0x011d:
            r20 = r17
        L_0x011f:
            r4 = r3
            r5 = r15
            r7 = r9
            r26 = r8
            r8 = r22
            r18 = r9
            r9 = r20
            int r9 = printFieldLocked(r4, r5, r6, r7, r8, r9)
            r6 = 109(0x6d, float:1.53E-43)
            r8 = 1
            if (r19 == 0) goto L_0x0138
            r7 = r26
            if (r9 == r7) goto L_0x013a
            goto L_0x013c
        L_0x0138:
            r7 = r26
        L_0x013a:
            r16 = r17
        L_0x013c:
            r4 = r3
            r5 = r11
            r17 = r7
            r7 = r9
            r18 = r9
            r9 = r16
            int r4 = printFieldLocked(r4, r5, r6, r7, r8, r9)
            r5 = 115(0x73, float:1.61E-43)
            r3[r4] = r5
            int r5 = r4 + 1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.TimeUtils.formatDurationLocked(long, int):int");
    }

    public static void formatDuration(long duration, StringBuilder builder) {
        synchronized (sFormatSync) {
            builder.append(sFormatStr, 0, formatDurationLocked(duration, 0));
        }
    }

    public static void formatDuration(long duration, StringBuilder builder, int fieldLen) {
        synchronized (sFormatSync) {
            builder.append(sFormatStr, 0, formatDurationLocked(duration, fieldLen));
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static void formatDuration(long duration, PrintWriter pw, int fieldLen) {
        synchronized (sFormatSync) {
            pw.print(new String(sFormatStr, 0, formatDurationLocked(duration, fieldLen)));
        }
    }

    public static String formatDuration(long duration) {
        String str;
        synchronized (sFormatSync) {
            str = new String(sFormatStr, 0, formatDurationLocked(duration, 0));
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
        if (millis < 0) {
            return Long.toString(millis);
        }
        c.setTimeInMillis(millis);
        return String.format("%tm-%td %tH:%tM:%tS.%tL", new Object[]{c, c, c, c, c, c});
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
