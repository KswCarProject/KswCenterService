package android.text.method;

import android.annotation.UnsupportedAppUsage;

public class HideReturnsTransformationMethod extends ReplacementTransformationMethod {
    private static char[] ORIGINAL = {13};
    private static char[] REPLACEMENT = {65279};
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private static HideReturnsTransformationMethod sInstance;

    /* access modifiers changed from: protected */
    public char[] getOriginal() {
        return ORIGINAL;
    }

    /* access modifiers changed from: protected */
    public char[] getReplacement() {
        return REPLACEMENT;
    }

    public static HideReturnsTransformationMethod getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new HideReturnsTransformationMethod();
        return sInstance;
    }
}
