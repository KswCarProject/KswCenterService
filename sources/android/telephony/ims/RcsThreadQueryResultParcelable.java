package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.ims.RcsTypeIdPair;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class RcsThreadQueryResultParcelable implements Parcelable {
    public static final Parcelable.Creator<RcsThreadQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsThreadQueryResultParcelable>() { // from class: android.telephony.ims.RcsThreadQueryResultParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsThreadQueryResultParcelable createFromParcel(Parcel in) {
            return new RcsThreadQueryResultParcelable(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsThreadQueryResultParcelable[] newArray(int size) {
            return new RcsThreadQueryResultParcelable[size];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<RcsTypeIdPair> mRcsThreadIds;

    public RcsThreadQueryResultParcelable(RcsQueryContinuationToken continuationToken, List<RcsTypeIdPair> rcsThreadIds) {
        this.mContinuationToken = continuationToken;
        this.mRcsThreadIds = rcsThreadIds;
    }

    private RcsThreadQueryResultParcelable(Parcel in) {
        this.mContinuationToken = (RcsQueryContinuationToken) in.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mRcsThreadIds = new ArrayList();
        in.readList(this.mRcsThreadIds, RcsTypeIdPair.class.getClassLoader());
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mContinuationToken, flags);
        dest.writeList(this.mRcsThreadIds);
    }
}
