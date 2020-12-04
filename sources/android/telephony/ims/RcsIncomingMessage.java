package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public class RcsIncomingMessage extends RcsMessage {
    RcsIncomingMessage(RcsControllerCall rcsControllerCall, int id) {
        super(rcsControllerCall, id);
    }

    public void setArrivalTimestamp(long arrivalTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(arrivalTimestamp) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageArrivalTimestamp(RcsIncomingMessage.this.mId, true, this.f$1, str);
            }
        });
    }

    public long getArrivalTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getMessageArrivalTimestamp(RcsIncomingMessage.this.mId, true, str));
            }
        })).longValue();
    }

    public void setSeenTimestamp(long notifiedTimestamp) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(notifiedTimestamp) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setMessageSeenTimestamp(RcsIncomingMessage.this.mId, true, this.f$1, str);
            }
        });
    }

    public long getSeenTimestamp() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.getMessageSeenTimestamp(RcsIncomingMessage.this.mId, true, str));
            }
        })).longValue();
    }

    public RcsParticipant getSenderParticipant() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getSenderParticipant(RcsIncomingMessage.this.mId, str));
            }
        })).intValue());
    }

    public boolean isIncoming() {
        return true;
    }
}
