package android.net.apf;

import android.annotation.SystemApi;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.R;

@SystemApi
public final class ApfCapabilities implements Parcelable {
    public static final Parcelable.Creator<ApfCapabilities> CREATOR = new Parcelable.Creator<ApfCapabilities>() {
        public ApfCapabilities createFromParcel(Parcel in) {
            return new ApfCapabilities(in);
        }

        public ApfCapabilities[] newArray(int size) {
            return new ApfCapabilities[size];
        }
    };
    public final int apfPacketFormat;
    public final int apfVersionSupported;
    public final int maximumApfProgramSize;

    public ApfCapabilities(int apfVersionSupported2, int maximumApfProgramSize2, int apfPacketFormat2) {
        this.apfVersionSupported = apfVersionSupported2;
        this.maximumApfProgramSize = maximumApfProgramSize2;
        this.apfPacketFormat = apfPacketFormat2;
    }

    private ApfCapabilities(Parcel in) {
        this.apfVersionSupported = in.readInt();
        this.maximumApfProgramSize = in.readInt();
        this.apfPacketFormat = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.apfVersionSupported);
        dest.writeInt(this.maximumApfProgramSize);
        dest.writeInt(this.apfPacketFormat);
    }

    public String toString() {
        return String.format("%s{version: %d, maxSize: %d, format: %d}", new Object[]{getClass().getSimpleName(), Integer.valueOf(this.apfVersionSupported), Integer.valueOf(this.maximumApfProgramSize), Integer.valueOf(this.apfPacketFormat)});
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ApfCapabilities)) {
            return false;
        }
        ApfCapabilities other = (ApfCapabilities) obj;
        if (this.apfVersionSupported == other.apfVersionSupported && this.maximumApfProgramSize == other.maximumApfProgramSize && this.apfPacketFormat == other.apfPacketFormat) {
            return true;
        }
        return false;
    }

    public boolean hasDataAccess() {
        return this.apfVersionSupported >= 4;
    }

    public static boolean getApfDrop8023Frames() {
        return Resources.getSystem().getBoolean(R.bool.config_apfDrop802_3Frames);
    }

    public static int[] getApfEtherTypeBlackList() {
        return Resources.getSystem().getIntArray(R.array.config_apfEthTypeBlackList);
    }
}
