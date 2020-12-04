package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadParticipantLeftEvent extends RcsGroupThreadEvent {
    private RcsParticipant mLeavingParticipant;

    public RcsGroupThreadParticipantLeftEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, RcsParticipant leavingParticipant) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mLeavingParticipant = leavingParticipant;
    }

    public RcsParticipant getLeavingParticipant() {
        return this.mLeavingParticipant;
    }

    /* access modifiers changed from: package-private */
    public void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createGroupThreadParticipantLeftEvent(RcsGroupThreadParticipantLeftEvent.this.getTimestamp(), RcsGroupThreadParticipantLeftEvent.this.getRcsGroupThread().getThreadId(), RcsGroupThreadParticipantLeftEvent.this.getOriginatingParticipant().getId(), RcsGroupThreadParticipantLeftEvent.this.getLeavingParticipant().getId(), str));
            }
        });
    }
}
