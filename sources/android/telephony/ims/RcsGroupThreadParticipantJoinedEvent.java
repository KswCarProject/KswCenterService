package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadParticipantJoinedEvent extends RcsGroupThreadEvent {
    private final RcsParticipant mJoinedParticipantId;

    public RcsGroupThreadParticipantJoinedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, RcsParticipant joinedParticipant) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mJoinedParticipantId = joinedParticipant;
    }

    public RcsParticipant getJoinedParticipant() {
        return this.mJoinedParticipantId;
    }

    /* access modifiers changed from: package-private */
    public void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createGroupThreadParticipantJoinedEvent(RcsGroupThreadParticipantJoinedEvent.this.getTimestamp(), RcsGroupThreadParticipantJoinedEvent.this.getRcsGroupThread().getThreadId(), RcsGroupThreadParticipantJoinedEvent.this.getOriginatingParticipant().getId(), RcsGroupThreadParticipantJoinedEvent.this.getJoinedParticipant().getId(), str));
            }
        });
    }
}
