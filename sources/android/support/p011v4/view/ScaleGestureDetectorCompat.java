package android.support.p011v4.view;

import android.p007os.Build;
import android.view.ScaleGestureDetector;

/* renamed from: android.support.v4.view.ScaleGestureDetectorCompat */
/* loaded from: classes3.dex */
public final class ScaleGestureDetectorCompat {
    private ScaleGestureDetectorCompat() {
    }

    @Deprecated
    public static void setQuickScaleEnabled(Object scaleGestureDetector, boolean enabled) {
        setQuickScaleEnabled((ScaleGestureDetector) scaleGestureDetector, enabled);
    }

    public static void setQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector, boolean enabled) {
        if (Build.VERSION.SDK_INT >= 19) {
            scaleGestureDetector.setQuickScaleEnabled(enabled);
        }
    }

    @Deprecated
    public static boolean isQuickScaleEnabled(Object scaleGestureDetector) {
        return isQuickScaleEnabled((ScaleGestureDetector) scaleGestureDetector);
    }

    public static boolean isQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= 19) {
            return scaleGestureDetector.isQuickScaleEnabled();
        }
        return false;
    }
}
