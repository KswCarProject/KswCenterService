package android.util;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes4.dex */
public final class Slog {
    private Slog() {
    }

    @UnsupportedAppUsage
    /* renamed from: v */
    public static int m52v(String tag, String msg) {
        return Log.println_native(3, 2, tag, msg);
    }

    /* renamed from: v */
    public static int m51v(String tag, String msg, Throwable tr) {
        return Log.println_native(3, 2, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: d */
    public static int m58d(String tag, String msg) {
        return Log.println_native(3, 3, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: d */
    public static int m57d(String tag, String msg, Throwable tr) {
        return Log.println_native(3, 3, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: i */
    public static int m54i(String tag, String msg) {
        return Log.println_native(3, 4, tag, msg);
    }

    /* renamed from: i */
    public static int m53i(String tag, String msg, Throwable tr) {
        return Log.println_native(3, 4, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: w */
    public static int m50w(String tag, String msg) {
        return Log.println_native(3, 5, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: w */
    public static int m49w(String tag, String msg, Throwable tr) {
        return Log.println_native(3, 5, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    /* renamed from: w */
    public static int m48w(String tag, Throwable tr) {
        return Log.println_native(3, 5, tag, Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    /* renamed from: e */
    public static int m56e(String tag, String msg) {
        return Log.println_native(3, 6, tag, msg);
    }

    @UnsupportedAppUsage
    /* renamed from: e */
    public static int m55e(String tag, String msg, Throwable tr) {
        return Log.println_native(3, 6, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

    @UnsupportedAppUsage
    public static int wtf(String tag, String msg) {
        return Log.wtf(3, tag, msg, null, false, true);
    }

    public static void wtfQuiet(String tag, String msg) {
        Log.wtfQuiet(3, tag, msg, true);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static int wtfStack(String tag, String msg) {
        return Log.wtf(3, tag, msg, null, true, true);
    }

    public static int wtf(String tag, Throwable tr) {
        return Log.wtf(3, tag, tr.getMessage(), tr, false, true);
    }

    @UnsupportedAppUsage
    public static int wtf(String tag, String msg, Throwable tr) {
        return Log.wtf(3, tag, msg, tr, false, true);
    }

    @UnsupportedAppUsage
    public static int println(int priority, String tag, String msg) {
        return Log.println_native(3, priority, tag, msg);
    }
}
