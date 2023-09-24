package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class RcsEventQueryResultDescriptor implements Parcelable {
    public static final Parcelable.Creator<RcsEventQueryResultDescriptor> CREATOR = new Parcelable.Creator<RcsEventQueryResultDescriptor>() { // from class: android.telephony.ims.RcsEventQueryResultDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsEventQueryResultDescriptor createFromParcel(Parcel in) {
            return new RcsEventQueryResultDescriptor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsEventQueryResultDescriptor[] newArray(int size) {
            return new RcsEventQueryResultDescriptor[size];
        }
    };
    private final RcsQueryContinuationToken mContinuationToken;
    private final List<RcsEventDescriptor> mEvents;

    public RcsEventQueryResultDescriptor(RcsQueryContinuationToken continuationToken, List<RcsEventDescriptor> events) {
        this.mContinuationToken = continuationToken;
        this.mEvents = events;
    }

    protected RcsEventQueryResult getRcsEventQueryResult(final RcsControllerCall rcsControllerCall) {
        List<RcsEvent> rcsEvents = (List) this.mEvents.stream().map(new Function() { // from class: android.telephony.ims.-$$Lambda$RcsEventQueryResultDescriptor$0eoTyoA0JNoBx53J3zXvi1fQcnA
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                RcsEvent createRcsEvent;
                createRcsEvent = ((RcsEventDescriptor) obj).createRcsEvent(RcsControllerCall.this);
                return createRcsEvent;
            }
        }).collect(Collectors.toList());
        return new RcsEventQueryResult(this.mContinuationToken, rcsEvents);
    }

    protected RcsEventQueryResultDescriptor(Parcel in) {
        this.mContinuationToken = (RcsQueryContinuationToken) in.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mEvents = new LinkedList();
        in.readList(this.mEvents, null);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mContinuationToken, flags);
        dest.writeList(this.mEvents);
    }
}
