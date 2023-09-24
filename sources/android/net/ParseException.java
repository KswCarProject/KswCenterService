package android.net;

/* loaded from: classes3.dex */
public class ParseException extends RuntimeException {
    public String response;

    ParseException(String response) {
        super(response);
        this.response = response;
    }

    ParseException(String response, Throwable cause) {
        super(response, cause);
        this.response = response;
    }
}
