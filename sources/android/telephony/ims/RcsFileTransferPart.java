package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RcsFileTransferPart {
    public static final int DOWNLOADING = 6;
    public static final int DOWNLOADING_CANCELLED = 9;
    public static final int DOWNLOADING_FAILED = 8;
    public static final int DOWNLOADING_PAUSED = 7;
    public static final int DRAFT = 1;
    public static final int NOT_SET = 0;
    public static final int SENDING = 2;
    public static final int SENDING_CANCELLED = 5;
    public static final int SENDING_FAILED = 4;
    public static final int SENDING_PAUSED = 3;
    public static final int SUCCEEDED = 10;
    private int mId;
    private final RcsControllerCall mRcsControllerCall;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RcsFileTransferStatus {
    }

    RcsFileTransferPart(RcsControllerCall rcsControllerCall, int id) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = id;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public void setFileTransferSessionId(String sessionId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(sessionId) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferSessionId(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public String getFileTransferSessionId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransferSessionId(RcsFileTransferPart.this.mId, str);
            }
        });
    }

    public void setContentUri(Uri contentUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(contentUri) {
            private final /* synthetic */ Uri f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferContentUri(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public Uri getContentUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransferContentUri(RcsFileTransferPart.this.mId, str);
            }
        });
    }

    public void setContentMimeType(String contentMimeType) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(contentMimeType) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferContentType(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public String getContentMimeType() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransferContentType(RcsFileTransferPart.this.mId, str);
            }
        });
    }

    public void setFileSize(long contentLength) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(contentLength) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferFileSize(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public long getFileSize() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getFileTransferFileSize(RcsFileTransferPart.this.mId, str));
            }
        })).longValue();
    }

    public void setTransferOffset(long transferOffset) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(transferOffset) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferTransferOffset(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public long getTransferOffset() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getFileTransferTransferOffset(RcsFileTransferPart.this.mId, str));
            }
        })).longValue();
    }

    public void setFileTransferStatus(int status) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(status) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferStatus(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public int getFileTransferStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getFileTransferStatus(RcsFileTransferPart.this.mId, str));
            }
        })).intValue();
    }

    public int getWidth() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getFileTransferWidth(RcsFileTransferPart.this.mId, str));
            }
        })).intValue();
    }

    public void setWidth(int width) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(width) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferWidth(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public int getHeight() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getFileTransferHeight(RcsFileTransferPart.this.mId, str));
            }
        })).intValue();
    }

    public void setHeight(int height) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(height) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferHeight(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public long getLength() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getFileTransferLength(RcsFileTransferPart.this.mId, str));
            }
        })).longValue();
    }

    public void setLength(long length) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(length) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferLength(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public Uri getPreviewUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransferPreviewUri(RcsFileTransferPart.this.mId, str);
            }
        });
    }

    public void setPreviewUri(Uri previewUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(previewUri) {
            private final /* synthetic */ Uri f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferPreviewUri(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }

    public String getPreviewMimeType() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransferPreviewType(RcsFileTransferPart.this.mId, str);
            }
        });
    }

    public void setPreviewMimeType(String previewMimeType) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(previewMimeType) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferPreviewType(RcsFileTransferPart.this.mId, this.f$1, str);
            }
        });
    }
}
