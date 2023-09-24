package android.support.p011v4.p013os;

import android.p007os.Build;
import android.p007os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* renamed from: android.support.v4.os.EnvironmentCompat */
/* loaded from: classes3.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    public static String getStorageState(File path) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getStorageState(path);
        }
        try {
            String canonicalPath = path.getCanonicalPath();
            String canonicalExternal = Environment.getExternalStorageDirectory().getCanonicalPath();
            if (canonicalPath.startsWith(canonicalExternal)) {
                return Environment.getExternalStorageState();
            }
            return "unknown";
        } catch (IOException e) {
            Log.m64w(TAG, "Failed to resolve canonical path: " + e);
            return "unknown";
        }
    }

    private EnvironmentCompat() {
    }
}