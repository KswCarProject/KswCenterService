package android.net;

import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.Parcelable;

/* loaded from: classes3.dex */
public final class TestNetworkInterface implements Parcelable {
    public static final Parcelable.Creator<TestNetworkInterface> CREATOR = new Parcelable.Creator<TestNetworkInterface>() { // from class: android.net.TestNetworkInterface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public TestNetworkInterface createFromParcel(Parcel in) {
            return new TestNetworkInterface(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public TestNetworkInterface[] newArray(int size) {
            return new TestNetworkInterface[size];
        }
    };
    private final ParcelFileDescriptor mFileDescriptor;
    private final String mInterfaceName;

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return this.mFileDescriptor != null ? 1 : 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.mFileDescriptor, 1);
        out.writeString(this.mInterfaceName);
    }

    public TestNetworkInterface(ParcelFileDescriptor pfd, String intf) {
        this.mFileDescriptor = pfd;
        this.mInterfaceName = intf;
    }

    private TestNetworkInterface(Parcel in) {
        this.mFileDescriptor = (ParcelFileDescriptor) in.readParcelable(ParcelFileDescriptor.class.getClassLoader());
        this.mInterfaceName = in.readString();
    }

    public ParcelFileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public String getInterfaceName() {
        return this.mInterfaceName;
    }
}
