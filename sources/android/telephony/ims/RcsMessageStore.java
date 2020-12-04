package android.telephony.ims;

import android.content.Context;
import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.util.List;

public class RcsMessageStore {
    RcsControllerCall mRcsControllerCall;

    RcsMessageStore(Context context) {
        this.mRcsControllerCall = new RcsControllerCall(context);
    }

    public RcsThreadQueryResult getRcsThreads(RcsThreadQueryParams queryParameters) throws RcsMessageStoreException {
        return new RcsThreadQueryResult(this.mRcsControllerCall, (RcsThreadQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getRcsThreads(RcsThreadQueryParams.this, str);
            }
        }));
    }

    public RcsThreadQueryResult getRcsThreads(RcsQueryContinuationToken continuationToken) throws RcsMessageStoreException {
        return new RcsThreadQueryResult(this.mRcsControllerCall, (RcsThreadQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getRcsThreadsWithToken(RcsQueryContinuationToken.this, str);
            }
        }));
    }

    public RcsParticipantQueryResult getRcsParticipants(RcsParticipantQueryParams queryParameters) throws RcsMessageStoreException {
        return new RcsParticipantQueryResult(this.mRcsControllerCall, (RcsParticipantQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getParticipants(RcsParticipantQueryParams.this, str);
            }
        }));
    }

    public RcsParticipantQueryResult getRcsParticipants(RcsQueryContinuationToken continuationToken) throws RcsMessageStoreException {
        return new RcsParticipantQueryResult(this.mRcsControllerCall, (RcsParticipantQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getParticipantsWithToken(RcsQueryContinuationToken.this, str);
            }
        }));
    }

    public RcsMessageQueryResult getRcsMessages(RcsMessageQueryParams queryParams) throws RcsMessageStoreException {
        return new RcsMessageQueryResult(this.mRcsControllerCall, (RcsMessageQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getMessages(RcsMessageQueryParams.this, str);
            }
        }));
    }

    public RcsMessageQueryResult getRcsMessages(RcsQueryContinuationToken continuationToken) throws RcsMessageStoreException {
        return new RcsMessageQueryResult(this.mRcsControllerCall, (RcsMessageQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getMessagesWithToken(RcsQueryContinuationToken.this, str);
            }
        }));
    }

    public RcsEventQueryResult getRcsEvents(RcsEventQueryParams queryParams) throws RcsMessageStoreException {
        return ((RcsEventQueryResultDescriptor) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getEvents(RcsEventQueryParams.this, str);
            }
        })).getRcsEventQueryResult(this.mRcsControllerCall);
    }

    public RcsEventQueryResult getRcsEvents(RcsQueryContinuationToken continuationToken) throws RcsMessageStoreException {
        return ((RcsEventQueryResultDescriptor) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getEventsWithToken(RcsQueryContinuationToken.this, str);
            }
        })).getRcsEventQueryResult(this.mRcsControllerCall);
    }

    public void persistRcsEvent(RcsEvent rcsEvent) throws RcsMessageStoreException {
        rcsEvent.persist(this.mRcsControllerCall);
    }

    public Rcs1To1Thread createRcs1To1Thread(RcsParticipant recipient) throws RcsMessageStoreException {
        return new Rcs1To1Thread(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createRcs1To1Thread(RcsParticipant.this.getId(), str));
            }
        })).intValue());
    }

    public RcsGroupThread createGroupThread(List<RcsParticipant> recipients, String groupName, Uri groupIcon) throws RcsMessageStoreException {
        int[] recipientIds = null;
        if (recipients != null) {
            recipientIds = new int[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                recipientIds[i] = recipients.get(i).getId();
            }
        }
        return new RcsGroupThread(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall(recipientIds, groupName, groupIcon) {
            private final /* synthetic */ int[] f$0;
            private final /* synthetic */ String f$1;
            private final /* synthetic */ Uri f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createGroupThread(this.f$0, this.f$1, this.f$2, str));
            }
        })).intValue());
    }

    public void deleteThread(RcsThread thread) throws RcsMessageStoreException {
        if (thread != null && !((Boolean) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Boolean.valueOf(iRcs.deleteThread(RcsThread.this.getThreadId(), RcsThread.this.getThreadType(), str));
            }
        })).booleanValue()) {
            throw new RcsMessageStoreException("Could not delete RcsThread");
        }
    }

    public RcsParticipant createRcsParticipant(String canonicalAddress, String alias) throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall(canonicalAddress, alias) {
            private final /* synthetic */ String f$0;
            private final /* synthetic */ String f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.createRcsParticipant(this.f$0, this.f$1, str));
            }
        })).intValue());
    }
}
