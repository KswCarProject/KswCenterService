package android.support.p011v4.app;

import android.app.ActivityManager;
import android.p007os.Build;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.app.ActivityManagerCompat */
/* loaded from: classes3.dex */
public final class ActivityManagerCompat {
    private ActivityManagerCompat() {
    }

    public static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return false;
    }
}
