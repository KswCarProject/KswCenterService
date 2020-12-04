package android.telephony.ims;

import com.android.ims.RcsTypeIdPair;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class RcsMessageQueryResult {
    private final RcsControllerCall mRcsControllerCall;
    private final RcsMessageQueryResultParcelable mRcsMessageQueryResultParcelable;

    RcsMessageQueryResult(RcsControllerCall rcsControllerCall, RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRcsMessageQueryResultParcelable = rcsMessageQueryResultParcelable;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mRcsMessageQueryResultParcelable.mContinuationToken;
    }

    public List<RcsMessage> getMessages() {
        return (List) this.mRcsMessageQueryResultParcelable.mMessageTypeIdPairs.stream().map(new Function() {
            public final Object apply(Object obj) {
                return RcsMessageQueryResult.lambda$getMessages$0(RcsMessageQueryResult.this, (RcsTypeIdPair) obj);
            }
        }).collect(Collectors.toList());
    }

    public static /* synthetic */ RcsMessage lambda$getMessages$0(RcsMessageQueryResult rcsMessageQueryResult, RcsTypeIdPair typeIdPair) {
        if (typeIdPair.getType() == 1) {
            return new RcsIncomingMessage(rcsMessageQueryResult.mRcsControllerCall, typeIdPair.getId());
        }
        return new RcsOutgoingMessage(rcsMessageQueryResult.mRcsControllerCall, typeIdPair.getId());
    }
}
