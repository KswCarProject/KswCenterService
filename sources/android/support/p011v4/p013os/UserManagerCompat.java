package android.support.p011v4.p013os;

import android.content.Context;
import android.p007os.Build;
import android.p007os.UserManager;

/* renamed from: android.support.v4.os.UserManagerCompat */
/* loaded from: classes3.dex */
public class UserManagerCompat {
    private UserManagerCompat() {
    }

    public static boolean isUserUnlocked(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked();
        }
        return true;
    }
}
