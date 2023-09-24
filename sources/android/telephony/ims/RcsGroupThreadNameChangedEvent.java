package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public final class RcsGroupThreadNameChangedEvent extends RcsGroupThreadEvent {
    private final String mNewName;

    public RcsGroupThreadNameChangedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, String newName) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mNewName = newName;
    }

    public String getNewName() {
        return this.mNewName;
    }

    @Override // android.telephony.ims.RcsEvent
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThreadNameChangedEvent$_UcLy20x7aG6AEgcOgmZOeqTok0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.createGroupThreadNameChangedEvent(r0.getTimestamp(), r0.getRcsGroupThread().getThreadId(), r0.getOriginatingParticipant().getId(), RcsGroupThreadNameChangedEvent.this.mNewName, str));
                return valueOf;
            }
        });
    }
}
