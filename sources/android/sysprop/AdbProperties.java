package android.sysprop;

import android.os.SystemProperties;
import android.telephony.SmsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

public final class AdbProperties {
    private AdbProperties() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean tryParseBoolean(java.lang.String r2) {
        /*
            java.util.Locale r0 = java.util.Locale.US
            java.lang.String r0 = r2.toLowerCase(r0)
            int r1 = r0.hashCode()
            switch(r1) {
                case 48: goto L_0x002d;
                case 49: goto L_0x0023;
                case 3569038: goto L_0x0018;
                case 97196323: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0037
        L_0x000e:
            java.lang.String r1 = "false"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0037
            r0 = 3
            goto L_0x0038
        L_0x0018:
            java.lang.String r1 = "true"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0037
            r0 = 1
            goto L_0x0038
        L_0x0023:
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0037
            r0 = 0
            goto L_0x0038
        L_0x002d:
            java.lang.String r1 = "0"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0037
            r0 = 2
            goto L_0x0038
        L_0x0037:
            r0 = -1
        L_0x0038:
            switch(r0) {
                case 0: goto L_0x0040;
                case 1: goto L_0x0040;
                case 2: goto L_0x003d;
                case 3: goto L_0x003d;
                default: goto L_0x003b;
            }
        L_0x003b:
            r0 = 0
            return r0
        L_0x003d:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            return r0
        L_0x0040:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sysprop.AdbProperties.tryParseBoolean(java.lang.String):java.lang.Boolean");
    }

    private static Integer tryParseInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long tryParseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String tryParseString(String str) {
        if ("".equals(str)) {
            return null;
        }
        return str;
    }

    private static <T extends Enum<T>> T tryParseEnum(Class<T> enumType, String str) {
        try {
            return Enum.valueOf(enumType, str.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> elementParser, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        List<T> ret = new ArrayList<>();
        for (String element : str.split(SmsManager.REGEX_PREFIX_DELIMITER)) {
            ret.add(elementParser.apply(element));
        }
        return ret;
    }

    private static <T extends Enum<T>> List<T> tryParseEnumList(Class<T> enumType, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        List<T> ret = new ArrayList<>();
        for (String element : str.split(SmsManager.REGEX_PREFIX_DELIMITER)) {
            ret.add(tryParseEnum(enumType, element));
        }
        return ret;
    }

    private static <T> String formatList(List<T> list) {
        StringJoiner joiner = new StringJoiner(SmsManager.REGEX_PREFIX_DELIMITER);
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : element.toString());
        }
        return joiner.toString();
    }

    private static <T extends Enum<T>> String formatEnumList(List<T> list, Function<T, String> elementFormatter) {
        StringJoiner joiner = new StringJoiner(SmsManager.REGEX_PREFIX_DELIMITER);
        for (T element : list) {
            joiner.add(element == null ? "" : elementFormatter.apply(element));
        }
        return joiner.toString();
    }

    public static Optional<Boolean> secure() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.adb.secure")));
    }

    public static void secure(Boolean value) {
        SystemProperties.set("ro.adb.secure", value == null ? "" : value.toString());
    }
}
