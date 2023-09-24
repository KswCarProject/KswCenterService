package android.support.p011v4.text;

import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@RequiresApi(21)
/* renamed from: android.support.v4.text.ICUCompatApi21 */
/* loaded from: classes3.dex */
class ICUCompatApi21 {
    private static final String TAG = "ICUCompatApi21";
    private static Method sAddLikelySubtagsMethod;

    ICUCompatApi21() {
    }

    static {
        try {
            Class<?> clazz = Class.forName("libcore.icu.ICU");
            sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", Locale.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        try {
            Object[] args = {locale};
            return ((Locale) sAddLikelySubtagsMethod.invoke(null, args)).getScript();
        } catch (IllegalAccessException e) {
            Log.m62w(TAG, e);
            return locale.getScript();
        } catch (InvocationTargetException e2) {
            Log.m62w(TAG, e2);
            return locale.getScript();
        }
    }
}
