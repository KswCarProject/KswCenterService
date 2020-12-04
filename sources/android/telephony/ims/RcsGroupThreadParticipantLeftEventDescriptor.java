package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;

public class RcsGroupThreadParticipantLeftEventDescriptor extends RcsGroupThreadEventDescriptor {
    public static final Parcelable.Creator<RcsGroupThreadParticipantLeftEventDescriptor> CREATOR = new Parcelable.Creator<RcsGroupThreadParticipantLeftEventDescriptor>() {
        public RcsGroupThreadParticipantLeftEventDescriptor createFromParcel(Parcel in) {
            return new RcsGroupThreadParticipantLeftEventDescriptor(in);
        }

        public RcsGroupThreadParticipantLeftEventDescriptor[] newArray(int size) {
            return new RcsGroupThreadParticipantLeftEventDescriptor[size];
        }
    };
    private int mLeavingParticipantId;

    public RcsGroupThreadParticipantLeftEventDescriptor(long timestamp, int rcsGroupThreadId, int originatingParticipantId, int leavingParticipantId) {
        super(timestamp, rcsGroupThreadId, originatingParticipantId);
        this.mLeavingParticipantId = leavingParticipantId;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
    public RcsGroupThreadParticipantLeftEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsGroupThreadParticipantLeftEvent(this.mTimestamp, new RcsGroupThread(rcsControllerCall, this.mRcsGroupThreadId), new RcsParticipant(rcsControllerCall, this.mOriginatingParticipantId), new RcsParticipant(rcsControllerCall, this.mLeavingParticipantId));
    }

    protected RcsGroupThreadParticipantLeftEventDescriptor(Parcel in) {
        super(in);
        this.mLeavingParticipantId = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mLeavingParticipantId);
    }
}
