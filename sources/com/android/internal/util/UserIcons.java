package com.android.internal.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class UserIcons {
    private static final int[] USER_ICON_COLORS = {C3132R.color.user_icon_1, C3132R.color.user_icon_2, C3132R.color.user_icon_3, C3132R.color.user_icon_4, C3132R.color.user_icon_5, C3132R.color.user_icon_6, C3132R.color.user_icon_7, C3132R.color.user_icon_8};

    public static Bitmap convertToBitmap(Drawable icon) {
        if (icon == null) {
            return null;
        }
        int width = icon.getIntrinsicWidth();
        int height = icon.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        icon.setBounds(0, 0, width, height);
        icon.draw(canvas);
        return bitmap;
    }

    public static Drawable getDefaultUserIcon(Resources resources, int userId, boolean light) {
        int colorResId = light ? C3132R.color.user_icon_default_white : C3132R.color.user_icon_default_gray;
        if (userId != -10000) {
            colorResId = USER_ICON_COLORS[userId % USER_ICON_COLORS.length];
        }
        Drawable icon = resources.getDrawable(C3132R.C3133drawable.ic_account_circle, null).mutate();
        icon.setColorFilter(resources.getColor(colorResId, null), PorterDuff.Mode.SRC_IN);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        return icon;
    }
}
