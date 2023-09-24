package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.ims.RcsTypeIdPair;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RcsMessageQueryResultParcelable implements Parcelable {
    public static final Parcelable.Creator<RcsMessageQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsMessageQueryResultParcelable>() { // from class: android.telephony.ims.RcsMessageQueryResultParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsMessageQueryResultParcelable createFromParcel(Parcel in) {
            return new RcsMessageQueryResultParcelable(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsMessageQueryResultParcelable[] newArray(int size) {
            return new RcsMessageQueryResultParcelable[size];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<RcsTypeIdPair> mMessageTypeIdPairs;

    public RcsMessageQueryResultParcelable(RcsQueryContinuationToken continuationToken, List<RcsTypeIdPair> messageTypeIdPairs) {
        this.mContinuationToken = continuationToken;
        this.mMessageTypeIdPairs = messageTypeIdPairs;
    }

    private RcsMessageQueryResultParcelable(Parcel in) {
        this.mContinuationToken = (RcsQueryContinuationToken) in.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mMessageTypeIdPairs = new ArrayList();
        in.readTypedList(this.mMessageTypeIdPairs, RcsTypeIdPair.CREATOR);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mContinuationToken, flags);
        dest.writeTypedList(this.mMessageTypeIdPairs);
    }
}
