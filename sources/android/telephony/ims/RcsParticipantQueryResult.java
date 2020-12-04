package android.telephony.ims;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class RcsParticipantQueryResult {
    private final RcsControllerCall mRcsControllerCall;
    private final RcsParticipantQueryResultParcelable mRcsParticipantQueryResultParcelable;

    RcsParticipantQueryResult(RcsControllerCall rcsControllerCall, RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRcsParticipantQueryResultParcelable = rcsParticipantQueryResultParcelable;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mRcsParticipantQueryResultParcelable.mContinuationToken;
    }

    public List<RcsParticipant> getParticipants() {
        return (List) this.mRcsParticipantQueryResultParcelable.mParticipantIds.stream().map(new Function() {
            public final Object apply(Object obj) {
                return RcsParticipantQueryResult.lambda$getParticipants$0(RcsParticipantQueryResult.this, (Integer) obj);
            }
        }).collect(Collectors.toList());
    }

    public static /* synthetic */ RcsParticipant lambda$getParticipants$0(RcsParticipantQueryResult rcsParticipantQueryResult, Integer participantId) {
        return new RcsParticipant(rcsParticipantQueryResult.mRcsControllerCall, participantId.intValue());
    }
}
