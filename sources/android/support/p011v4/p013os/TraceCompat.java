package android.support.p011v4.p013os;

import android.p007os.Build;
import android.p007os.Trace;

/* renamed from: android.support.v4.os.TraceCompat */
/* loaded from: classes3.dex */
public final class TraceCompat {
    public static void beginSection(String sectionName) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(sectionName);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }

    private TraceCompat() {
    }
}
