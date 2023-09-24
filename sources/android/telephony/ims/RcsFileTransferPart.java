package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
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
    /* loaded from: classes4.dex */
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

    public void setFileTransferSessionId(final String sessionId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$eRysznIV0Pr9U0YPttLhvYxp2JE
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferSessionId(RcsFileTransferPart.this.mId, sessionId, str);
            }
        });
    }

    public String getFileTransferSessionId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$KCwtK0S-DWMMpZpRsslXFJ_BwLM
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String fileTransferSessionId;
                fileTransferSessionId = iRcs.getFileTransferSessionId(RcsFileTransferPart.this.mId, str);
                return fileTransferSessionId;
            }
        });
    }

    public void setContentUri(final Uri contentUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$gHrYiSj4B912GPuzgw6v3qjIwX4
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferContentUri(RcsFileTransferPart.this.mId, contentUri, str);
            }
        });
    }

    public Uri getContentUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$kvkf6ASdU-q8pR3hQ4h9sWdIiOQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Uri fileTransferContentUri;
                fileTransferContentUri = iRcs.getFileTransferContentUri(RcsFileTransferPart.this.mId, str);
                return fileTransferContentUri;
            }
        });
    }

    public void setContentMimeType(final String contentMimeType) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$_U_JpxTv_8vqlG8zHOxxNMMBqjQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferContentType(RcsFileTransferPart.this.mId, contentMimeType, str);
            }
        });
    }

    public String getContentMimeType() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$X3yfwvMihWzA9VZLnUyeAlq_rVc
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String fileTransferContentType;
                fileTransferContentType = iRcs.getFileTransferContentType(RcsFileTransferPart.this.mId, str);
                return fileTransferContentType;
            }
        });
    }

    public void setFileSize(final long contentLength) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$iFRtCc6m4Iup_st7fFqTiBlhq4o
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferFileSize(RcsFileTransferPart.this.mId, contentLength, str);
            }
        });
    }

    public long getFileSize() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$RUTTVEFxx0RPDq0oORm2TF6GoJ8
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getFileTransferFileSize(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setTransferOffset(final long transferOffset) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$NeUx42-gy02-DXOOj3iF2Y92GoU
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferTransferOffset(RcsFileTransferPart.this.mId, transferOffset, str);
            }
        });
    }

    public long getTransferOffset() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$m0Uztiu9azOAnoxBEWLsT8Br_HE
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getFileTransferTransferOffset(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setFileTransferStatus(final int status) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$1I5TANd1JGzUvxVPbWbmYgYHgZg
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferStatus(RcsFileTransferPart.this.mId, status, str);
            }
        });
    }

    public int getFileTransferStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$5nq0jbEkQm3ys2NrT291eV7NXn8
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getFileTransferStatus(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).intValue();
    }

    public int getWidth() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$cbwg3i9EtuBNKXI5md4IWJQ_GDo
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getFileTransferWidth(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).intValue();
    }

    public void setWidth(final int width) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$dlGXDrIqL-9NsNgH4LIS6Yg7j6k
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferWidth(RcsFileTransferPart.this.mId, width, str);
            }
        });
    }

    public int getHeight() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$A_4O6faLVs6mpaPsKJIA9HefwvU
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getFileTransferHeight(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).intValue();
    }

    public void setHeight(final int height) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$Ju03J4o5Gnha0Ynbq35sw9HL5nU
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferHeight(RcsFileTransferPart.this.mId, height, str);
            }
        });
    }

    public long getLength() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$B5UxN0BhElRx-FWpAZgbz41DxuY
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getFileTransferLength(RcsFileTransferPart.this.mId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setLength(final long length) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$kXXTp4pKFNyBztnIElEJdJrz8F8
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferLength(RcsFileTransferPart.this.mId, length, str);
            }
        });
    }

    public Uri getPreviewUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$pZ6z6R9RPQvoiIFOh-auV7YAePw
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Uri fileTransferPreviewUri;
                fileTransferPreviewUri = iRcs.getFileTransferPreviewUri(RcsFileTransferPart.this.mId, str);
                return fileTransferPreviewUri;
            }
        });
    }

    public void setPreviewUri(final Uri previewUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$4bTF8UNuphmPWGI1zJtDN0vEMKQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferPreviewUri(RcsFileTransferPart.this.mId, previewUri, str);
            }
        });
    }

    public String getPreviewMimeType() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$B5FCShigB8L98Le8jQF4kRDSfhk
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String fileTransferPreviewType;
                fileTransferPreviewType = iRcs.getFileTransferPreviewType(RcsFileTransferPart.this.mId, str);
                return fileTransferPreviewType;
            }
        });
    }

    public void setPreviewMimeType(final String previewMimeType) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsFileTransferPart$Js49W5j_aEL3sBPRKR3zwBZEwQc
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setFileTransferPreviewType(RcsFileTransferPart.this.mId, previewMimeType, str);
            }
        });
    }
}
