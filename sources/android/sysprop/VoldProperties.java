package android.sysprop;

import android.p007os.SystemProperties;
import android.telephony.SmsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class VoldProperties {
    private VoldProperties() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static Boolean tryParseBoolean(String str) {
        char c;
        String lowerCase = str.toLowerCase(Locale.US);
        switch (lowerCase.hashCode()) {
            case 48:
                if (lowerCase.equals("0")) {
                    c = 2;
                    break;
                }
                c = '\uffff';
                break;
            case 49:
                if (lowerCase.equals("1")) {
                    c = 0;
                    break;
                }
                c = '\uffff';
                break;
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 1;
                    break;
                }
                c = '\uffff';
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    c = 3;
                    break;
                }
                c = '\uffff';
                break;
            default:
                c = '\uffff';
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return Boolean.TRUE;
            case 2:
            case 3:
                return Boolean.FALSE;
            default:
                return null;
        }
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
            return (T) Enum.valueOf(enumType, str.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> elementParser, String str) {
        String[] split;
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
        String[] split;
        if ("".equals(str)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (String element : str.split(SmsManager.REGEX_PREFIX_DELIMITER)) {
            arrayList.add(tryParseEnum(enumType, element));
        }
        return arrayList;
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
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : elementFormatter.apply(element));
        }
        return joiner.toString();
    }

    public static Optional<String> decrypt() {
        String value = SystemProperties.get("vold.decrypt");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void decrypt(String value) {
        SystemProperties.set("vold.decrypt", value == null ? "" : value.toString());
    }

    public static Optional<String> encrypt_progress() {
        String value = SystemProperties.get("vold.encrypt_progress");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void encrypt_progress(String value) {
        SystemProperties.set("vold.encrypt_progress", value == null ? "" : value.toString());
    }

    public static Optional<Integer> encrypt_time_remaining() {
        String value = SystemProperties.get("vold.encrypt_time_remaining");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void encrypt_time_remaining(Integer value) {
        SystemProperties.set("vold.encrypt_time_remaining", value == null ? "" : value.toString());
    }
}
