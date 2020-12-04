package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;

public final class RcsMessageSnippet implements Parcelable {
    public static final Parcelable.Creator<RcsMessageSnippet> CREATOR = new Parcelable.Creator<RcsMessageSnippet>() {
        public RcsMessageSnippet createFromParcel(Parcel in) {
            return new RcsMessageSnippet(in);
        }

        public RcsMessageSnippet[] newArray(int size) {
            return new RcsMessageSnippet[size];
        }
    };
    private final int mStatus;
    private final String mText;
    private final long mTimestamp;

    public RcsMessageSnippet(String text, int status, long timestamp) {
        this.mText = text;
        this.mStatus = status;
        this.mTimestamp = timestamp;
    }

    public String getSnippetText() {
        return this.mText;
    }

    public int getSnippetStatus() {
        return this.mStatus;
    }

    public long getSnippetTimestamp() {
        return this.mTimestamp;
    }

    private RcsMessageSnippet(Parcel in) {
        this.mText = in.readString();
        this.mStatus = in.readInt();
        this.mTimestamp = in.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mText);
        dest.writeInt(this.mStatus);
        dest.writeLong(this.mTimestamp);
    }
}
