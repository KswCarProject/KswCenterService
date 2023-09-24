package android.p007os;

import android.annotation.UnsupportedAppUsage;

/* compiled from: ZygoteProcess.java */
/* renamed from: android.os.ZygoteStartFailedEx */
/* loaded from: classes3.dex */
class ZygoteStartFailedEx extends Exception {
    @UnsupportedAppUsage
    ZygoteStartFailedEx(String s) {
        super(s);
    }

    @UnsupportedAppUsage
    ZygoteStartFailedEx(Throwable cause) {
        super(cause);
    }

    ZygoteStartFailedEx(String s, Throwable cause) {
        super(s, cause);
    }
}
