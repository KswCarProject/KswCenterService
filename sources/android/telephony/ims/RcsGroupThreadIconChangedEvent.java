package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public final class RcsGroupThreadIconChangedEvent extends RcsGroupThreadEvent {
    private final Uri mNewIcon;

    public RcsGroupThreadIconChangedEvent(long timestamp, RcsGroupThread rcsGroupThread, RcsParticipant originatingParticipant, Uri newIcon) {
        super(timestamp, rcsGroupThread, originatingParticipant);
        this.mNewIcon = newIcon;
    }

    public Uri getNewIcon() {
        return this.mNewIcon;
    }

    @Override // android.telephony.ims.RcsEvent
    void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException {
        rcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThreadIconChangedEvent$XfKd9jzuhr_hAT3mvSOBgWj08Js
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.createGroupThreadIconChangedEvent(r0.getTimestamp(), r0.getRcsGroupThread().getThreadId(), r0.getOriginatingParticipant().getId(), RcsGroupThreadIconChangedEvent.this.mNewIcon, str));
                return valueOf;
            }
        });
    }
}
