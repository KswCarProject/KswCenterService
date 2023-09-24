package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public class RcsIncomingMessage extends RcsMessage {
    RcsIncomingMessage(RcsControllerCall rcsControllerCall, int id) {
        super(rcsControllerCall, id);
    }

    public void setArrivalTimestamp(final long arrivalTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsIncomingMessage$OdAmvZkbLfGMknLzGuOOXKVYczw
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageArrivalTimestamp(RcsIncomingMessage.this.mId, true, arrivalTimestamp, str);
            }
        });
    }

    public long getArrivalTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsIncomingMessage$FSzDY0-cZbSPckAubiU3QaXu_Yg
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getMessageArrivalTimestamp(RcsIncomingMessage.this.mId, true, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setSeenTimestamp(final long notifiedTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsIncomingMessage$OvvfqgFG2FNYN7ohCBbWdETfeuQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageSeenTimestamp(RcsIncomingMessage.this.mId, true, notifiedTimestamp, str);
            }
        });
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsIncomingMessage$21fHX_-vVRTL95x404C5b4eGWok
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.getMessageSeenTimestamp(RcsIncomingMessage.this.mId, true, str));
                return valueOf;
            }
        })).longValue();
    }

    public RcsParticipant getSenderParticipant() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsIncomingMessage$ye8KwJqH7fqnRAZlQY1PRVyh2b0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getSenderParticipant(RcsIncomingMessage.this.mId, str));
                return valueOf;
            }
        })).intValue());
    }

    @Override // android.telephony.ims.RcsMessage
    public boolean isIncoming() {
        return true;
    }
}
