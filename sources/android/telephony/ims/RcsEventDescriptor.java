package android.telephony.ims;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;

/* loaded from: classes4.dex */
public abstract class RcsEventDescriptor implements Parcelable {
    protected final long mTimestamp;

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PROTECTED)
    public abstract RcsEvent createRcsEvent(RcsControllerCall rcsControllerCall);

    RcsEventDescriptor(long timestamp) {
        this.mTimestamp = timestamp;
    }

    RcsEventDescriptor(Parcel in) {
        this.mTimestamp = in.readLong();
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mTimestamp);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
