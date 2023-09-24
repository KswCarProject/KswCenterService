package com.android.internal.policy;

import android.content.res.Resources;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class ScreenDecorationsUtils {
    public static float getWindowCornerRadius(Resources resources) {
        if (supportsRoundedCornersOnWindows(resources)) {
            float defaultRadius = resources.getDimension(C3132R.dimen.rounded_corner_radius);
            float topRadius = resources.getDimension(C3132R.dimen.rounded_corner_radius_top);
            if (topRadius == 0.0f) {
                topRadius = defaultRadius;
            }
            float bottomRadius = resources.getDimension(C3132R.dimen.rounded_corner_radius_bottom);
            if (bottomRadius == 0.0f) {
                bottomRadius = defaultRadius;
            }
            return Math.min(topRadius, bottomRadius);
        }
        return 0.0f;
    }

    public static boolean supportsRoundedCornersOnWindows(Resources resources) {
        return resources.getBoolean(C3132R.bool.config_supportsRoundedCornersOnWindows);
    }
}
