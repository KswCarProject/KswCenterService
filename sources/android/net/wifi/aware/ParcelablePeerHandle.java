package android.net.wifi.aware;

import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelablePeerHandle extends PeerHandle implements Parcelable {
    public static final Parcelable.Creator<ParcelablePeerHandle> CREATOR = new Parcelable.Creator<ParcelablePeerHandle>() {
        public ParcelablePeerHandle[] newArray(int size) {
            return new ParcelablePeerHandle[size];
        }

        public ParcelablePeerHandle createFromParcel(Parcel in) {
            return new ParcelablePeerHandle(new PeerHandle(in.readInt()));
        }
    };

    public ParcelablePeerHandle(PeerHandle peerHandle) {
        super(peerHandle.peerId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.peerId);
    }
}
