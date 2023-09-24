package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class RcsParticipantQueryResultParcelable implements Parcelable {
    public static final Parcelable.Creator<RcsParticipantQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsParticipantQueryResultParcelable>() { // from class: android.telephony.ims.RcsParticipantQueryResultParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsParticipantQueryResultParcelable createFromParcel(Parcel in) {
            return new RcsParticipantQueryResultParcelable(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsParticipantQueryResultParcelable[] newArray(int size) {
            return new RcsParticipantQueryResultParcelable[size];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<Integer> mParticipantIds;

    public RcsParticipantQueryResultParcelable(RcsQueryContinuationToken continuationToken, List<Integer> participantIds) {
        this.mContinuationToken = continuationToken;
        this.mParticipantIds = participantIds;
    }

    private RcsParticipantQueryResultParcelable(Parcel in) {
        this.mContinuationToken = (RcsQueryContinuationToken) in.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mParticipantIds = new ArrayList();
        in.readList(this.mParticipantIds, Integer.class.getClassLoader());
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mContinuationToken, flags);
        dest.writeList(this.mParticipantIds);
    }
}
