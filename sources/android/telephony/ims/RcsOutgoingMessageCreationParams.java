package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsMessageCreationParams;

public final class RcsOutgoingMessageCreationParams extends RcsMessageCreationParams implements Parcelable {
    public static final Parcelable.Creator<RcsOutgoingMessageCreationParams> CREATOR = new Parcelable.Creator<RcsOutgoingMessageCreationParams>() {
        public RcsOutgoingMessageCreationParams createFromParcel(Parcel in) {
            return new RcsOutgoingMessageCreationParams(in);
        }

        public RcsOutgoingMessageCreationParams[] newArray(int size) {
            return new RcsOutgoingMessageCreationParams[size];
        }
    };

    public static class Builder extends RcsMessageCreationParams.Builder {
        public Builder(long originationTimestamp, int subscriptionId) {
            super(originationTimestamp, subscriptionId);
        }

        public RcsOutgoingMessageCreationParams build() {
            return new RcsOutgoingMessageCreationParams(this);
        }
    }

    private RcsOutgoingMessageCreationParams(Builder builder) {
        super((RcsMessageCreationParams.Builder) builder);
    }

    private RcsOutgoingMessageCreationParams(Parcel in) {
        super(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest);
    }
}
