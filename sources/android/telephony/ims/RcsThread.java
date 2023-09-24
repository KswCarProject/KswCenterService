package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.aidl.IRcs;
import com.android.internal.annotations.VisibleForTesting;

/* loaded from: classes4.dex */
public abstract class RcsThread {
    protected final RcsControllerCall mRcsControllerCall;
    protected int mThreadId;

    public abstract boolean isGroup();

    protected RcsThread(RcsControllerCall rcsControllerCall, int threadId) {
        this.mThreadId = threadId;
        this.mRcsControllerCall = rcsControllerCall;
    }

    public RcsMessageSnippet getSnippet() throws RcsMessageStoreException {
        return (RcsMessageSnippet) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsThread$TwqOqnkLjl05BhB2arTpJkBo73Y
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                RcsMessageSnippet messageSnippet;
                messageSnippet = iRcs.getMessageSnippet(RcsThread.this.mThreadId, str);
                return messageSnippet;
            }
        });
    }

    public RcsIncomingMessage addIncomingMessage(final RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams) throws RcsMessageStoreException {
        int messageId = ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsThread$9gFw0KtL-BczxOxCksL2zOV2xHM
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.addIncomingMessage(RcsThread.this.mThreadId, rcsIncomingMessageCreationParams, str));
                return valueOf;
            }
        })).intValue();
        return new RcsIncomingMessage(this.mRcsControllerCall, messageId);
    }

    public RcsOutgoingMessage addOutgoingMessage(final RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) throws RcsMessageStoreException {
        int messageId = ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsThread$_9zf-uqUJl6VjAbIMvQwKcAyzUs
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.addOutgoingMessage(RcsThread.this.mThreadId, rcsOutgoingMessageCreationParams, str));
                return valueOf;
            }
        })).intValue();
        return new RcsOutgoingMessage(this.mRcsControllerCall, messageId);
    }

    public void deleteMessage(final RcsMessage rcsMessage) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsThread$uAkHFwrvypgP5w5y0Uy4uwQ6blY
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.deleteMessage(r1.getId(), rcsMessage.isIncoming(), r0.mThreadId, RcsThread.this.isGroup(), str);
            }
        });
    }

    public RcsMessageQueryResult getMessages() throws RcsMessageStoreException {
        final RcsMessageQueryParams queryParams = new RcsMessageQueryParams.Builder().setThread(this).build();
        return new RcsMessageQueryResult(this.mRcsControllerCall, (RcsMessageQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsThread$A9iPL3bU3iiRv1xCYNUNP76n6Vw
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                RcsMessageQueryResultParcelable messages;
                messages = iRcs.getMessages(RcsMessageQueryParams.this, str);
                return messages;
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
