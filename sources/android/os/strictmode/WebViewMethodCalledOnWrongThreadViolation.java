package android.os.strictmode;

public final class WebViewMethodCalledOnWrongThreadViolation extends Violation {
    public WebViewMethodCalledOnWrongThreadViolation(Throwable originStack) {
        super((String) null);
        setStackTrace(originStack.getStackTrace());
    }
}
