package com.android.internal.util;

import android.content.res.Resources;
import android.p007os.Build;
import android.p007os.SystemProperties;
import android.view.ViewRootImpl;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class ScreenShapeHelper {
    public static int getWindowOutsetBottomPx(Resources resources) {
        if (Build.IS_EMULATOR) {
            return SystemProperties.getInt(ViewRootImpl.PROPERTY_EMULATOR_WIN_OUTSET_BOTTOM_PX, 0);
        }
        return resources.getInteger(C3132R.integer.config_windowOutsetBottom);
    }
}
