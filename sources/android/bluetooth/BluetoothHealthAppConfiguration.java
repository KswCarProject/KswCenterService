package android.bluetooth;

import android.p007os.Parcel;
import android.p007os.Parcelable;

@Deprecated
/* loaded from: classes.dex */
public final class BluetoothHealthAppConfiguration implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<BluetoothHealthAppConfiguration> CREATOR = new Parcelable.Creator<BluetoothHealthAppConfiguration>() { // from class: android.bluetooth.BluetoothHealthAppConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public BluetoothHealthAppConfiguration createFromParcel(Parcel in) {
            return new BluetoothHealthAppConfiguration();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public BluetoothHealthAppConfiguration[] newArray(int size) {
            return new BluetoothHealthAppConfiguration[size];
        }
    };

    BluetoothHealthAppConfiguration() {
    }

    @Override // android.p007os.Parcelable
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

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
    }
}
