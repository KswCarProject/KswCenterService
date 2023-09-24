package android.text.method;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes4.dex */
public class HideReturnsTransformationMethod extends ReplacementTransformationMethod {
    private static char[] ORIGINAL = {'\r'};
    private static char[] REPLACEMENT = {'\ufeff'};
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private static HideReturnsTransformationMethod sInstance;

    @Override // android.text.method.ReplacementTransformationMethod
    protected char[] getOriginal() {
        return ORIGINAL;
    }

    @Override // android.text.method.ReplacementTransformationMethod
    protected char[] getReplacement() {
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
