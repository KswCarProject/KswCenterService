package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import com.android.internal.annotations.VisibleForTesting;

public abstract class RcsThread {
    protected final RcsControllerCall mRcsControllerCall;
    protected int mThreadId;

    public abstract boolean isGroup();

    protected RcsThread(RcsControllerCall rcsControllerCall, int threadId) {
        this.mThreadId = threadId;
        this.mRcsControllerCall = rcsControllerCall;
    }

    public RcsMessageSnippet getSnippet() throws RcsMessageStoreException {
        return (RcsMessageSnippet) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getMessageSnippet(RcsThread.this.mThreadId, str);
            }
        });
    }

    public RcsIncomingMessage addIncomingMessage(RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams) throws RcsMessageStoreException {
        return new RcsIncomingMessage(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall(rcsIncomingMessageCreationParams) {
            private final /* synthetic */ RcsIncomingMessageCreationParams f$1;

            {
                this.f$1 = r2;
            }

            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.addIncomingMessage(RcsThread.this.mThreadId, this.f$1, str));
            }
        })).intValue());
    }

    public RcsOutgoingMessage addOutgoingMessage(RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) throws RcsMessageStoreException {
        return new RcsOutgoingMessage(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall(rcsOutgoingMessageCreationParams) {
            private final /* synthetic */ RcsOutgoingMessageCreationParams f$1;

            {
                this.f$1 = r2;
            }

            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.addOutgoingMessage(RcsThread.this.mThreadId, this.f$1, str));
            }
        })).intValue());
    }

    public void deleteMessage(RcsMessage rcsMessage) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(rcsMessage) {
            private final /* synthetic */ RcsMessage f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.deleteMessage(this.f$1.getId(), this.f$1.isIncoming(), RcsThread.this.mThreadId, RcsThread.this.isGroup(), str);
            }
        });
    }

    public RcsMessageQueryResult getMessages() throws RcsMessageStoreException {
        return new RcsMessageQueryResult(this.mRcsControllerCall, (RcsMessageQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getMessages(RcsMessageQueryParams.this, str);
            }
        }));
    }

    @VisibleForTesting
    public int getThreadId() {
        return this.mThreadId;
    }

    public int getThreadType() {
        return isGroup() ? 1 : 0;
    }
}
