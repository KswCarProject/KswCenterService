package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public final class RcsGroupThreadParticipantLeftEvent extends RcsGroupThreadEvent {
    private RcsParticipant mLeavingParticipant;

    public RcsGroupThreadParticipantLeftEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, RcsParticipant leavingParticipant) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mLeavingParticipant = leavingParticipant;
    }

    public RcsParticipant getLeavingParticipant() {
        return this.mLeavingParticipant;
    }

    @Override // android.telephony.ims.RcsEvent
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThreadParticipantLeftEvent$vX6x1bZueUi684uTuoFiWxhgs80
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.createGroupThreadParticipantLeftEvent(r0.getTimestamp(), r0.getRcsGroupThread().getThreadId(), r0.getOriginatingParticipant().getId(), RcsGroupThreadParticipantLeftEvent.this.getLeavingParticipant().getId(), str));
                return valueOf;
            }
        });
    }
}
