package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class Log {
    static final boolean ENABLE_FULL_LOGGING = android.util.Log.isLoggable(TextClassifier.DEFAULT_LOG_TAG, 2);

    private Log() {
    }

    /* renamed from: v */
    public static void m38v(String tag, String msg) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.m66v(tag, msg);
        }
    }

    /* renamed from: d */
    public static void m40d(String tag, String msg) {
        android.util.Log.m72d(tag, msg);
    }

    /* renamed from: w */
    public static void m37w(String tag, String msg) {
        android.util.Log.m64w(tag, msg);
    }

    /* renamed from: e */
    public static void m39e(String tag, String msg, Throwable tr) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.m69e(tag, msg, tr);
            return;
        }
        String trString = tr != null ? tr.getClass().getSimpleName() : "??";
        android.util.Log.m72d(tag, String.format("%s (%s)", msg, trString));
    }
}
