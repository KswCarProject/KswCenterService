package android.hardware.camera2.params;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;

/* loaded from: classes.dex */
public final class VendorTagDescriptor implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptor> CREATOR = new Parcelable.Creator<VendorTagDescriptor>() { // from class: android.hardware.camera2.params.VendorTagDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VendorTagDescriptor createFromParcel(Parcel source) {
            try {
                VendorTagDescriptor vendorDescriptor = new VendorTagDescriptor(source);
                return vendorDescriptor;
            } catch (Exception e) {
                Log.m69e(VendorTagDescriptor.TAG, "Exception creating VendorTagDescriptor from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VendorTagDescriptor[] newArray(int size) {
            return new VendorTagDescriptor[size];
        }
    };
    private static final String TAG = "VendorTagDescriptor";

    private VendorTagDescriptor(Parcel source) {
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
    }
}
