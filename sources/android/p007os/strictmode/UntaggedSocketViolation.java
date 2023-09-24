package android.p007os.strictmode;

/* renamed from: android.os.strictmode.UntaggedSocketViolation */
/* loaded from: classes3.dex */
public final class UntaggedSocketViolation extends Violation {
    public UntaggedSocketViolation() {
        super("Untagged socket detected; use TrafficStats.setThreadSocketTag() to track all network usage");
    }
}
