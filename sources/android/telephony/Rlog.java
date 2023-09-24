package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Build;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public final class Rlog {
    private static final boolean USER_BUILD = Build.IS_USER;

    private Rlog() {
    }

    @UnsupportedAppUsage
    /* renamed from: v */
    public static int m82v(String tag, String msg) {
        return Log.println_native(1, 2, tag, msg);
    }

    /* renamed from: v */
    public static int m81v(String tag, String msg, Throwable tr) {
        return Log.println_native(1, 2, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: d */
    public static int m88d(String tag, String msg) {
        return Log.println_native(1, 3, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: d */
    public static int m87d(String tag, String msg, Throwable tr) {
        return Log.println_native(1, 3, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: i */
    public static int m84i(String tag, String msg) {
        return Log.println_native(1, 4, tag, msg);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    /* renamed from: i */
    public static int m83i(String tag, String msg, Throwable tr) {
        return Log.println_native(1, 4, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: w */
    public static int m80w(String tag, String msg) {
        return Log.println_native(1, 5, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: w */
    public static int m79w(String tag, String msg, Throwable tr) {
        return Log.println_native(1, 5, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    /* renamed from: w */
    public static int m78w(String tag, Throwable tr) {
        return Log.println_native(1, 5, tag, Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: e */
    public static int m86e(String tag, String msg) {
        return Log.println_native(1, 6, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: e */
    public static int m85e(String tag, String msg, Throwable tr) {
        return Log.println_native(1, 6, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    public static int println(int priority, String tag, String msg) {
        return Log.println_native(1, priority, tag, msg);
    }

    public static boolean isLoggable(String tag, int level) {
        return Log.isLoggable(tag, level);
    }

    public static String pii(String tag, Object pii) {
        String val = String.valueOf(pii);
        if (pii == null || TextUtils.isEmpty(val) || isLoggable(tag, 2)) {
            return val;
        }
        return "[" + secureHash(val.getBytes()) + "]";
    }

    public static String pii(boolean enablePiiLogging, Object pii) {
        String val = String.valueOf(pii);
        if (pii == null || TextUtils.isEmpty(val) || enablePiiLogging) {
            return val;
        }
        return "[" + secureHash(val.getBytes()) + "]";
    }

    private static String secureHash(byte[] input) {
        if (USER_BUILD) {
            return "****";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KeyProperties.DIGEST_SHA1);
            byte[] result = messageDigest.digest(input);
            return Base64.encodeToString(result, 11);
        } catch (NoSuchAlgorithmException e) {
            return "####";
        }
    }
}
