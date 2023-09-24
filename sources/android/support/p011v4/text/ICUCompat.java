package android.support.p011v4.text;

import android.p007os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import java.util.Locale;

/* renamed from: android.support.v4.text.ICUCompat */
/* loaded from: classes3.dex */
public final class ICUCompat {
    private static final ICUCompatBaseImpl IMPL;

    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatBaseImpl */
    /* loaded from: classes3.dex */
    static class ICUCompatBaseImpl {
        ICUCompatBaseImpl() {
        }

        public String maximizeAndGetScript(Locale locale) {
            return ICUCompatIcs.maximizeAndGetScript(locale);
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatApi21Impl */
    /* loaded from: classes3.dex */
    static class ICUCompatApi21Impl extends ICUCompatBaseImpl {
        ICUCompatApi21Impl() {
        }

        @Override // android.support.p011v4.text.ICUCompat.ICUCompatBaseImpl
        public String maximizeAndGetScript(Locale locale) {
            return ICUCompatApi21.maximizeAndGetScript(locale);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ICUCompatApi21Impl();
        } else {
            IMPL = new ICUCompatBaseImpl();
        }
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        return IMPL.maximizeAndGetScript(locale);
    }

    private ICUCompat() {
    }
}
