package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public final class BluetoothHealthAppConfiguration implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<BluetoothHealthAppConfiguration> CREATOR = new Parcelable.Creator<BluetoothHealthAppConfiguration>() {
        public BluetoothHealthAppConfiguration createFromParcel(Parcel in) {
            return new BluetoothHealthAppConfiguration();
        }

        public BluetoothHealthAppConfiguration[] newArray(int size) {
            return new BluetoothHealthAppConfiguration[size];
        }
    };

    BluetoothHealthAppConfiguration() {
    }

    public int describeContents() {
        return 0;
    }

    @Deprecated
    public int getDataType() {
        return 0;
    }

    @Deprecated
    public String getName() {
        return null;
    }

    @Deprecated
    public int getRole() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
    }
}
