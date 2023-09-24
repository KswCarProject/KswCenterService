package android.net.wifi;

import android.annotation.SystemApi;
import android.p007os.Parcel;
import android.p007os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public class WifiNetworkConnectionStatistics implements Parcelable {
    public static final Parcelable.Creator<WifiNetworkConnectionStatistics> CREATOR = new Parcelable.Creator<WifiNetworkConnectionStatistics>() { // from class: android.net.wifi.WifiNetworkConnectionStatistics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public WifiNetworkConnectionStatistics createFromParcel(Parcel in) {
            int numConnection = in.readInt();
            int numUsage = in.readInt();
            WifiNetworkConnectionStatistics stats = new WifiNetworkConnectionStatistics(numConnection, numUsage);
            return stats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public WifiNetworkConnectionStatistics[] newArray(int size) {
            return new WifiNetworkConnectionStatistics[size];
        }
    };
    private static final String TAG = "WifiNetworkConnnectionStatistics";
    public int numConnection;
    public int numUsage;

    public WifiNetworkConnectionStatistics(int connection, int usage) {
        this.numConnection = connection;
        this.numUsage = usage;
    }

    public WifiNetworkConnectionStatistics() {
    }

    public String toString() {
        return "c=" + this.numConnection + " u=" + this.numUsage;
    }

    public WifiNetworkConnectionStatistics(WifiNetworkConnectionStatistics source) {
        this.numConnection = source.numConnection;
        this.numUsage = source.numUsage;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numConnection);
        dest.writeInt(this.numUsage);
    }
}
