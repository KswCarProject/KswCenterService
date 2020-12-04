package android.hardware.face;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

public final class Face extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Face> CREATOR = new Parcelable.Creator<Face>() {
        public Face createFromParcel(Parcel in) {
            return new Face(in);
        }

        public Face[] newArray(int size) {
            return new Face[size];
        }
    };

    public Face(CharSequence name, int faceId, long deviceId) {
        super(name, faceId, deviceId);
    }

    private Face(Parcel in) {
        super(in.readString(), in.readInt(), in.readLong());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName().toString());
        out.writeInt(getBiometricId());
        out.writeLong(getDeviceId());
    }
}
