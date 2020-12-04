package android.os.strictmode;

public final class ServiceConnectionLeakedViolation extends Violation {
    public ServiceConnectionLeakedViolation(Throwable originStack) {
        super((String) null);
        setStackTrace(originStack.getStackTrace());
    }
}
