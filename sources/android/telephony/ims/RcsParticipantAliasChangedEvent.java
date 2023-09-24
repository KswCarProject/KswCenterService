package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public final class RcsParticipantAliasChangedEvent extends RcsEvent {
    private final String mNewAlias;
    private final RcsParticipant mParticipant;

    public RcsParticipantAliasChangedEvent(long timestamp, RcsParticipant participant, String newAlias) {
        super(timestamp);
        this.mParticipant = participant;
        this.mNewAlias = newAlias;
    }

    public RcsParticipant getParticipant() {
        return this.mParticipant;
    }

    public String getNewAlias() {
        return this.mNewAlias;
    }

    @Override // android.telephony.ims.RcsEvent
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsParticipantAliasChangedEvent$iaidodGQwVEX4DZ8FekRuR-x3gQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.createParticipantAliasChangedEvent(r0.getTimestamp(), r0.getParticipant().getId(), RcsParticipantAliasChangedEvent.this.getNewAlias(), str));
                return valueOf;
            }
        });
    }
}
