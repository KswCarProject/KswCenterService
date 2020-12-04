package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public final class ConnectionInfo implements Parcelable {
    public static final Parcelable.Creator<ConnectionInfo> CREATOR = new Parcelable.Creator<ConnectionInfo>() {
        public ConnectionInfo createFromParcel(Parcel in) {
            try {
                try {
                    return new ConnectionInfo(in.readInt(), new InetSocketAddress(InetAddress.getByAddress(in.createByteArray()), in.readInt()), new InetSocketAddress(InetAddress.getByAddress(in.createByteArray()), in.readInt()));
                } catch (UnknownHostException e) {
                    throw new IllegalArgumentException("Invalid InetAddress");
                }
            } catch (UnknownHostException e2) {
                throw new IllegalArgumentException("Invalid InetAddress");
            }
        }

        public ConnectionInfo[] newArray(int size) {
            return new ConnectionInfo[size];
        }
    };
    public final InetSocketAddress local;
    public final int protocol;
    public final InetSocketAddress remote;

    public int describeContents() {
        return 0;
    }

    public ConnectionInfo(int protocol2, InetSocketAddress local2, InetSocketAddress remote2) {
        this.protocol = protocol2;
        this.local = local2;
        this.remote = remote2;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.protocol);
        out.writeByteArray(this.local.getAddress().getAddress());
        out.writeInt(this.local.getPort());
        out.writeByteArray(this.remote.getAddress().getAddress());
        out.writeInt(this.remote.getPort());
    }
}
