package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public final class RcsGroupThreadParticipantJoinedEvent extends RcsGroupThreadEvent {
    private final RcsParticipant mJoinedParticipantId;

    public RcsGroupThreadParticipantJoinedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, RcsParticipant joinedParticipant) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mJoinedParticipantId = joinedParticipant;
    }

    public RcsParticipant getJoinedParticipant() {
        return this.mJoinedParticipantId;
    }

    @Override // android.telephony.ims.RcsEvent
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThreadParticipantJoinedEvent$KF8KQ4WJfLnGm4G9rOgwA9MjEj8
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.createGroupThreadParticipantJoinedEvent(r0.getTimestamp(), r0.getRcsGroupThread().getThreadId(), r0.getOriginatingParticipant().getId(), RcsGroupThreadParticipantJoinedEvent.this.getJoinedParticipant().getId(), str));
                return valueOf;
            }
        });
    }
}
