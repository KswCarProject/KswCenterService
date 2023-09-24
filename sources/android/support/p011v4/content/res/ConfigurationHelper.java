package android.support.p011v4.content.res;

import android.content.res.Resources;
import android.p007os.Build;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.content.res.ConfigurationHelper */
/* loaded from: classes3.dex */
public final class ConfigurationHelper {
    private ConfigurationHelper() {
    }

    @Deprecated
    public static int getScreenHeightDp(@NonNull Resources resources) {
        return resources.getConfiguration().screenHeightDp;
    }

    @Deprecated
    public static int getScreenWidthDp(@NonNull Resources resources) {
        return resources.getConfiguration().screenWidthDp;
    }

    @Deprecated
    public static int getSmallestScreenWidthDp(@NonNull Resources resources) {
        return resources.getConfiguration().smallestScreenWidthDp;
    }

    public static int getDensityDpi(@NonNull Resources resources) {
        if (Build.VERSION.SDK_INT >= 17) {
            return resources.getConfiguration().densityDpi;
        }
        return resources.getDisplayMetrics().densityDpi;
    }
}
