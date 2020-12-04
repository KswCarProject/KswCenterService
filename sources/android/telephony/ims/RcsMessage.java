package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class RcsMessage {
    public static final int DRAFT = 1;
    public static final int FAILED = 6;
    public static final double LOCATION_NOT_SET = Double.MIN_VALUE;
    public static final int NOT_SET = 0;
    public static final int QUEUED = 2;
    public static final int RECEIVED = 7;
    public static final int RETRYING = 5;
    public static final int SEEN = 9;
    public static final int SENDING = 3;
    public static final int SENT = 4;
    protected final int mId;
    protected final RcsControllerCall mRcsControllerCall;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RcsMessageStatus {
    }

    public abstract boolean isIncoming();

    RcsMessage(RcsControllerCall rcsControllerCall, int id) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public int getSubscriptionId() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getMessageSubId(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str));
            }
        })).intValue();
    }

    public void setSubscriptionId(int subId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(subId) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageSubId(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public void setStatus(int rcsMessageStatus) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(rcsMessageStatus) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageStatus(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public int getStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getMessageStatus(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str));
            }
        })).intValue();
    }

    public void setOriginationTimestamp(long timestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(timestamp) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageOriginationTimestamp(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public long getOriginationTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getMessageOriginationTimestamp(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str));
            }
        })).longValue();
    }

    public void setRcsMessageId(String rcsMessageGlobalId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(rcsMessageGlobalId) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGlobalMessageIdForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public String getRcsMessageId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getGlobalMessageIdForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str);
            }
        });
    }

    public String getText() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getTextForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str);
            }
        });
    }

    public void setText(String text) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(text) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setTextForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public double getLatitude() throws RcsMessageStoreException {
        return ((Double) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Double.valueOf(iRcs.getLatitudeForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str));
            }
        })).doubleValue();
    }

    public void setLatitude(double latitude) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(latitude) {
            private final /* synthetic */ double f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setLatitudeForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public double getLongitude() throws RcsMessageStoreException {
        return ((Double) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Double.valueOf(iRcs.getLongitudeForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str));
            }
        })).doubleValue();
    }

    public void setLongitude(double longitude) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(longitude) {
            private final /* synthetic */ double f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setLongitudeForMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str);
            }
        });
    }

    public RcsFileTransferPart insertFileTransfer(RcsFileTransferCreationParams fileTransferCreationParameters) throws RcsMessageStoreException {
        return new RcsFileTransferPart(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall(fileTransferCreationParameters) {
            private final /* synthetic */ RcsFileTransferCreationParams f$1;

            {
                this.f$1 = r2;
            }

            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.storeFileTransfer(RcsMessage.this.mId, RcsMessage.this.isIncoming(), this.f$1, str));
            }
        })).intValue());
    }

    public Set<RcsFileTransferPart> getFileTransferParts() throws RcsMessageStoreException {
        Set<RcsFileTransferPart> fileTransferParts = new HashSet<>();
        for (int fileTransfer : (int[]) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getFileTransfersAttachedToMessage(RcsMessage.this.mId, RcsMessage.this.isIncoming(), str);
            }
        })) {
            fileTransferParts.add(new RcsFileTransferPart(this.mRcsControllerCall, fileTransfer));
        }
        return Collections.unmodifiableSet(fileTransferParts);
    }

    public void removeFileTransferPart(RcsFileTransferPart fileTransferPart) throws RcsMessageStoreException {
        if (fileTransferPart != null) {
            this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() {
                public final void methodOnIRcs(IRcs iRcs, String str) {
                    iRcs.deleteFileTransfer(RcsFileTransferPart.this.getId(), str);
                }
            });
        }
    }
}
