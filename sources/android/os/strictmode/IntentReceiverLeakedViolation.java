package android.os.strictmode;

public final class IntentReceiverLeakedViolation extends Violation {
    public IntentReceiverLeakedViolation(Throwable originStack) {
        super((String) null);
        setStackTrace(originStack.getStackTrace());
    }
}
