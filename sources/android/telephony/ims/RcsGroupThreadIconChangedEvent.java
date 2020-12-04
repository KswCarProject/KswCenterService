package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class RcsGroupThreadIconChangedEvent extends RcsGroupThreadEvent {
    private final Uri mNewIcon;

    public RcsGroupThreadIconChangedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, Uri newIcon) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mNewIcon = newIcon;
    }

    public Uri getNewIcon() {
        return this.mNewIcon;
    }

    /* access modifiers changed from: package-private */
    public void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createGroupThreadIconChangedEvent(RcsGroupThreadIconChangedEvent.this.getTimestamp(), RcsGroupThreadIconChangedEvent.this.getRcsGroupThread().getThreadId(), RcsGroupThreadIconChangedEvent.this.getOriginatingParticipant().getId(), RcsGroupThreadIconChangedEvent.this.mNewIcon, str));
            }
        });
    }
}
