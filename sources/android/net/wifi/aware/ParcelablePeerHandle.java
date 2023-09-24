package android.net.wifi.aware;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes3.dex */
public final class ParcelablePeerHandle extends PeerHandle implements Parcelable {
    public static final Parcelable.Creator<ParcelablePeerHandle> CREATOR = new Parcelable.Creator<ParcelablePeerHandle>() { // from class: android.net.wifi.aware.ParcelablePeerHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ParcelablePeerHandle[] newArray(int size) {
            return new ParcelablePeerHandle[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ParcelablePeerHandle createFromParcel(Parcel in) {
            int peerHandle = in.readInt();
            return new ParcelablePeerHandle(new PeerHandle(peerHandle));
        }
    };

    public ParcelablePeerHandle(PeerHandle peerHandle) {
        super(peerHandle.peerId);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.peerId);
    }
}
