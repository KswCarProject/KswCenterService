package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public class Rcs1To1Thread extends RcsThread {
    private int mThreadId;

    public Rcs1To1Thread(RcsControllerCall rcsControllerCall, int threadId) {
        super(rcsControllerCall, threadId);
        this.mThreadId = threadId;
    }

    public boolean isGroup() {
        return false;
    }

    public long getFallbackThreadId() throws RcsMessageStoreException {
        return ((Long) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Long.valueOf(iRcs.get1To1ThreadFallbackThreadId(Rcs1To1Thread.this.mThreadId, str));
            }
        })).longValue();
    }

    public void setFallbackThreadId(long fallbackThreadId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(fallbackThreadId) {
            private final /* synthetic */ long f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.set1To1ThreadFallbackThreadId(Rcs1To1Thread.this.mThreadId, this.f$1, str);
            }
        });
    }

    public RcsParticipant getRecipient() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.get1To1ThreadOtherParticipantId(Rcs1To1Thread.this.mThreadId, str));
            }
        })).intValue());
    }
}
