package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadNameChangedEvent extends RcsGroupThreadEvent {
    private final String mNewName;

    public RcsGroupThreadNameChangedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, String newName) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mNewName = newName;
    }

    public String getNewName() {
        return this.mNewName;
    }

    /* access modifiers changed from: package-private */
    public void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createGroupThreadNameChangedEvent(RcsGroupThreadNameChangedEvent.this.getTimestamp(), RcsGroupThreadNameChangedEvent.this.getRcsGroupThread().getThreadId(), RcsGroupThreadNameChangedEvent.this.getOriginatingParticipant().getId(), RcsGroupThreadNameChangedEvent.this.mNewName, str));
            }
        });
    }
}
