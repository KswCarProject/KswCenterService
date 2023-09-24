package android.p007os;

import java.util.Map;

/* renamed from: android.os.VintfObject */
/* loaded from: classes3.dex */
public class VintfObject {
    public static native String[] getHalNamesAndVersions();

    public static native String getSepolicyVersion();

    public static native Long getTargetFrameworkCompatibilityMatrixVersion();

    public static native Map<String, String[]> getVndkSnapshots();

    public static native String[] report();

    public static native int verify(String[] strArr);

    public static native int verifyWithoutAvb();

    private VintfObject() {
    }
}
