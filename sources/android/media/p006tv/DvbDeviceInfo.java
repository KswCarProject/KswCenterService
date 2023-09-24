package android.media.p006tv;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;

/* renamed from: android.media.tv.DvbDeviceInfo */
/* loaded from: classes3.dex */
public final class DvbDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DvbDeviceInfo> CREATOR = new Parcelable.Creator<DvbDeviceInfo>() { // from class: android.media.tv.DvbDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DvbDeviceInfo createFromParcel(Parcel source) {
            try {
                return new DvbDeviceInfo(source);
            } catch (Exception e) {
                Log.m69e(DvbDeviceInfo.TAG, "Exception creating DvbDeviceInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public DvbDeviceInfo[] newArray(int size) {
            return new DvbDeviceInfo[size];
        }
    };
    static final String TAG = "DvbDeviceInfo";
    private final int mAdapterId;
    private final int mDeviceId;

    private DvbDeviceInfo(Parcel source) {
        this.mAdapterId = source.readInt();
        this.mDeviceId = source.readInt();
    }

    public DvbDeviceInfo(int adapterId, int deviceId) {
        this.mAdapterId = adapterId;
        this.mDeviceId = deviceId;
    }

    public int getAdapterId() {
        return this.mAdapterId;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAdapterId);
        dest.writeInt(this.mDeviceId);
    }
}
