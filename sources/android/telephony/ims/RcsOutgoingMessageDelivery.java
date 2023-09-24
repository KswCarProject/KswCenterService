package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public class RcsOutgoingMessageDelivery {
    private final RcsControllerCall mRcsControllerCall;
    private final int mRcsOutgoingMessageId;
    private final int mRecipientId;

    RcsOutgoingMessageDelivery(RcsControllerCall rcsControllerCall, int recipientId, int messageId) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRecipientId = recipientId;
        this.mRcsOutgoingMessageId = messageId;
    }

    public void setDeliveredTimestamp(final long deliveredTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$RRb0ymf6fqzeTy7WOV3ylkaBJDA
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliveryDeliveredTimestamp(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, deliveredTimestamp, str);
            }
        });
    }

    public long getDeliveredTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$XobnngqskscGHACfd0qrHXy-W6A
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getOutgoingDeliveryDeliveredTimestamp(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setSeenTimestamp(final long seenTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$P2OcWKWejNP6qsda0ef9G0jKYKs
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliverySeenTimestamp(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, seenTimestamp, str);
            }
        });
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$fxSVb-4v4N7q2YgopxM2Hg_pCH0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getOutgoingDeliverySeenTimestamp(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setStatus(final int status) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$l9Yzsl9k4Z30dUsRJ0yJpKeg9jk
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliveryStatus(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, status, str);
            }
        });
    }

    public int getStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessageDelivery$Hwf3ep_etCKWfwwAtq0Sdu0dtwY
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getOutgoingDeliveryStatus(r0.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
                return valueOf;
            }
        })).intValue();
    }

    public RcsParticipant getRecipient() {
        return new RcsParticipant(this.mRcsControllerCall, this.mRecipientId);
    }

    public RcsOutgoingMessage getMessage() {
        return new RcsOutgoingMessage(this.mRcsControllerCall, this.mRcsOutgoingMessageId);
    }
}
