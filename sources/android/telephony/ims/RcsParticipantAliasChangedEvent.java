package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

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

    /* access modifiers changed from: package-private */
    public void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createParticipantAliasChangedEvent(RcsParticipantAliasChangedEvent.this.getTimestamp(), RcsParticipantAliasChangedEvent.this.getParticipant().getId(), RcsParticipantAliasChangedEvent.this.getNewAlias(), str));
            }
        });
    }
}
