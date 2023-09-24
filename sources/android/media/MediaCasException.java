package android.media;

/* loaded from: classes3.dex */
public class MediaCasException extends Exception {
    private MediaCasException(String detailMessage) {
        super(detailMessage);
    }

    static void throwExceptionIfNeeded(int error) throws MediaCasException {
        if (error == 0) {
            return;
        }
        if (error == 7) {
            throw new NotProvisionedException(null);
        }
        if (error == 8) {
            throw new ResourceBusyException(null);
        }
        if (error == 11) {
            throw new DeniedByServerException(null);
        }
        MediaCasStateException.throwExceptionIfNeeded(error);
    }

    /* loaded from: classes3.dex */
    public static final class UnsupportedCasException extends MediaCasException {
        public UnsupportedCasException(String detailMessage) {
            super(detailMessage);
        }
    }

    /* loaded from: classes3.dex */
    public static final class NotProvisionedException extends MediaCasException {
        public NotProvisionedException(String detailMessage) {
            super(detailMessage);
        }
    }

    /* loaded from: classes3.dex */
    public static final class DeniedByServerException extends MediaCasException {
        public DeniedByServerException(String detailMessage) {
            super(detailMessage);
        }
    }

    /* loaded from: classes3.dex */
    public static final class ResourceBusyException extends MediaCasException {
        public ResourceBusyException(String detailMessage) {
            super(detailMessage);
        }
    }
}
