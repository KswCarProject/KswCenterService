package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public class RcsOutgoingMessageDelivery {
    private final RcsControllerCall mRcsControllerCall;
    private final int mRcsOutgoingMessageId;
    private final int mRecipientId;

    RcsOutgoingMessageDelivery(RcsControllerCall rcsControllerCall, int recipientId, int messageId) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRecipientId = recipientId;
        this.mRcsOutgoingMessageId = messageId;
    }

    public void setDeliveredTimestamp(long deliveredTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(deliveredTimestamp) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliveryDeliveredTimestamp(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, this.f$1, str);
            }
        });
    }

    public long getDeliveredTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getOutgoingDeliveryDeliveredTimestamp(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
            }
        })).longValue();
    }

    public void setSeenTimestamp(long seenTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(seenTimestamp) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliverySeenTimestamp(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, this.f$1, str);
            }
        });
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getOutgoingDeliverySeenTimestamp(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
            }
        })).longValue();
    }

    public void setStatus(int status) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(status) {
            private final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setOutgoingDeliveryStatus(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, this.f$1, str);
            }
        });
    }

    public int getStatus() throws RcsMessageStoreException {
        return ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getOutgoingDeliveryStatus(RcsOutgoingMessageDelivery.this.mRcsOutgoingMessageId, RcsOutgoingMessageDelivery.this.mRecipientId, str));
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
