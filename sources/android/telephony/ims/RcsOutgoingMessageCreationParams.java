package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.telephony.ims.RcsMessageCreationParams;

/* loaded from: classes4.dex */
public final class RcsOutgoingMessageCreationParams extends RcsMessageCreationParams implements Parcelable {
    public static final Parcelable.Creator<RcsOutgoingMessageCreationParams> CREATOR = new Parcelable.Creator<RcsOutgoingMessageCreationParams>() { // from class: android.telephony.ims.RcsOutgoingMessageCreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsOutgoingMessageCreationParams createFromParcel(Parcel in) {
            return new RcsOutgoingMessageCreationParams(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RcsOutgoingMessageCreationParams[] newArray(int size) {
            return new RcsOutgoingMessageCreationParams[size];
        }
    };

    /* loaded from: classes4.dex */
    public static class Builder extends RcsMessageCreationParams.Builder {
        public Builder(long originationTimestamp, int subscriptionId) {
            super(originationTimestamp, subscriptionId);
        }

        @Override // android.telephony.ims.RcsMessageCreationParams.Builder
        public RcsOutgoingMessageCreationParams build() {
            return new RcsOutgoingMessageCreationParams(this);
        }
    }

    private RcsOutgoingMessageCreationParams(Builder builder) {
        super(builder);
    }

    private RcsOutgoingMessageCreationParams(Parcel in) {
        super(in);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest);
    }
}
