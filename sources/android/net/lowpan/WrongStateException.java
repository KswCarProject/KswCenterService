package android.net.lowpan;

/* loaded from: classes3.dex */
public class WrongStateException extends LowpanException {
    public WrongStateException() {
    }

    public WrongStateException(String message) {
        super(message);
    }

    public WrongStateException(String message, Throwable cause) {
        super(message, cause);
    }

    protected WrongStateException(Exception cause) {
        super(cause);
    }
}
