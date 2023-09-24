package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public class Rcs1To1Thread extends RcsThread {
    private int mThreadId;

    public Rcs1To1Thread(RcsControllerCall rcsControllerCall, int threadId) {
        super(rcsControllerCall, threadId);
        this.mThreadId = threadId;
    }

    @Override // android.telephony.ims.RcsThread
    public boolean isGroup() {
        return false;
    }

    public long getFallbackThreadId() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$Rcs1To1Thread$_6gUCvjDS6WXqf0AClQwrZ7ZpSc
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Long valueOf;
                valueOf = Long.valueOf(iRcs.get1To1ThreadFallbackThreadId(Rcs1To1Thread.this.mThreadId, str));
                return valueOf;
            }
        })).longValue();
    }

    public void setFallbackThreadId(final long fallbackThreadId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$Rcs1To1Thread$vx_evSYitgJIMB6l-hANvSJpdBE
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.set1To1ThreadFallbackThreadId(Rcs1To1Thread.this.mThreadId, fallbackThreadId, str);
            }
        });
    }

    public RcsParticipant getRecipient() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$Rcs1To1Thread$DlCgifrXUJFouqWWh-0GG6hzH-s
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.get1To1ThreadOtherParticipantId(Rcs1To1Thread.this.mThreadId, str));
                return valueOf;
            }
        })).intValue());
    }
}
