package android.support.p011v4.p013os;

/* renamed from: android.support.v4.os.OperationCanceledException */
/* loaded from: classes3.dex */
public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    public OperationCanceledException(String message) {
        super(message != null ? message : "The operation has been canceled.");
    }
}
