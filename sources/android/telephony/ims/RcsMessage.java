package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
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
    /* loaded from: classes4.dex */
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
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$aRPnqqKzd_0-r7d0L-yxEGwwqhc
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getMessageSubId(r0.mId, RcsMessage.this.isIncoming(), str));
                return valueOf;
            }
        })).intValue();
    }

    public void setSubscriptionId(final int subId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$-LY9H-_5LQIoU4Xq6-Om0qdYMVI
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageSubId(r0.mId, RcsMessage.this.isIncoming(), subId, str);
            }
        });
    }

    public void setStatus(final int rcsMessageStatus) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$HOpRnAgYuj5X-zRrkxcAiJKt3Yc
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageStatus(r0.mId, RcsMessage.this.isIncoming(), rcsMessageStatus, str);
            }
        });
    }

    public int getStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$ENpJTtPeUTVSc1EYo7vY4el8CTs
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getMessageStatus(r0.mId, RcsMessage.this.isIncoming(), str));
                return valueOf;
            }
        })).intValue();
    }

    public void setOriginationTimestamp(final long timestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$tq1Iu9i2c3B7IAVANp7f9nz6BQI
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageOriginationTimestamp(r0.mId, RcsMessage.this.isIncoming(), timestamp, str);
            }
        });
    }

    public long getOriginationTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$g_U1Cuc_BEv4JwISu6moBuf_gk0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getMessageOriginationTimestamp(r0.mId, RcsMessage.this.isIncoming(), str));
                return valueOf;
            }
        })).longValue();
    }

    public void setRcsMessageId(final String rcsMessageGlobalId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$g8Us4wB8C4Z6FrAxP2EuVIs7uxg
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGlobalMessageIdForMessage(r0.mId, RcsMessage.this.isIncoming(), rcsMessageGlobalId, str);
            }
        });
    }

    public String getRcsMessageId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$Q3LSjskzCcY_LjdyGsUpqO_r8VY
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String globalMessageIdForMessage;
                globalMessageIdForMessage = iRcs.getGlobalMessageIdForMessage(r0.mId, RcsMessage.this.isIncoming(), str);
                return globalMessageIdForMessage;
            }
        });
    }

    public String getText() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$jYDUGwQFl9jl0oYVhZlCKVq8rao
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String textForMessage;
                textForMessage = iRcs.getTextForMessage(r0.mId, RcsMessage.this.isIncoming(), str);
                return textForMessage;
            }
        });
    }

    public void setText(final String text) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$OAV9C_4ygCWHuq6dzQZ6ryQxcng
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setTextForMessage(r0.mId, RcsMessage.this.isIncoming(), text, str);
            }
        });
    }

    public double getLatitude() throws RcsMessageStoreException {
        return ((Double) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$kreYSW19iRp_OhyMXMbvQXAxPUo
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Double valueOf;
                valueOf = Double.valueOf(iRcs.getLatitudeForMessage(r0.mId, RcsMessage.this.isIncoming(), str));
                return valueOf;
            }
        })).doubleValue();
    }

    public void setLatitude(final double latitude) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$OWkNB5jXq4SHPk-hN01pKQSg5Z0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setLatitudeForMessage(r0.mId, RcsMessage.this.isIncoming(), latitude, str);
            }
        });
    }

    public double getLongitude() throws RcsMessageStoreException {
        return ((Double) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$x3G08QqJukFKk5K0JbtI4g5JW5o
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Double valueOf;
                valueOf = Double.valueOf(iRcs.getLongitudeForMessage(r0.mId, RcsMessage.this.isIncoming(), str));
                return valueOf;
            }
        })).doubleValue();
    }

    public void setLongitude(final double longitude) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$LUddD5B3is0XmrdznFFrh7_BWBA
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setLongitudeForMessage(r0.mId, RcsMessage.this.isIncoming(), longitude, str);
            }
        });
    }

    public RcsFileTransferPart insertFileTransfer(final RcsFileTransferCreationParams fileTransferCreationParameters) throws RcsMessageStoreException {
        return new RcsFileTransferPart(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$b6noI0B_AJvyHWAuKOL2fM-kHI4
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.storeFileTransfer(r0.mId, RcsMessage.this.isIncoming(), fileTransferCreationParameters, str));
                return valueOf;
            }
        })).intValue());
    }

    public Set<RcsFileTransferPart> getFileTransferParts() throws RcsMessageStoreException {
        Set<RcsFileTransferPart> fileTransferParts = new HashSet<>();
        int[] fileTransferIds = (int[]) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$0kBwAJ2w-8hy0pyzXvF4qM9OTJY
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                int[] fileTransfersAttachedToMessage;
                fileTransfersAttachedToMessage = iRcs.getFileTransfersAttachedToMessage(r0.mId, RcsMessage.this.isIncoming(), str);
                return fileTransfersAttachedToMessage;
            }
        });
        for (int fileTransfer : fileTransferIds) {
            fileTransferParts.add(new RcsFileTransferPart(this.mRcsControllerCall, fileTransfer));
        }
        return Collections.unmodifiableSet(fileTransferParts);
    }

    public void removeFileTransferPart(final RcsFileTransferPart fileTransferPart) throws RcsMessageStoreException {
        if (fileTransferPart == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsMessage$ArUQB5LoWlQIN8Wq6WO2D83-1MM
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.deleteFileTransfer(RcsFileTransferPart.this.getId(), str);
            }
        });
    }
}
