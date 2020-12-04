package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;

public class RcsParticipantAliasChangedEventDescriptor extends RcsEventDescriptor {
    public static final Parcelable.Creator<RcsParticipantAliasChangedEventDescriptor> CREATOR = new Parcelable.Creator<RcsParticipantAliasChangedEventDescriptor>() {
        public RcsParticipantAliasChangedEventDescriptor createFromParcel(Parcel in) {
            return new RcsParticipantAliasChangedEventDescriptor(in);
        }

        public RcsParticipantAliasChangedEventDescriptor[] newArray(int size) {
            return new RcsParticipantAliasChangedEventDescriptor[size];
        }
    };
    protected String mNewAlias;
    protected int mParticipantId;

    public RcsParticipantAliasChangedEventDescriptor(long timestamp, int participantId, String newAlias) {
        super(timestamp);
        this.mParticipantId = participantId;
        this.mNewAlias = newAlias;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
    public RcsParticipantAliasChangedEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsParticipantAliasChangedEvent(this.mTimestamp, new RcsParticipant(rcsControllerCall, this.mParticipantId), this.mNewAlias);
    }

    protected RcsParticipantAliasChangedEventDescriptor(Parcel in) {
        super(in);
        this.mNewAlias = in.readString();
        this.mParticipantId = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mNewAlias);
        dest.writeInt(this.mParticipantId);
    }
}
