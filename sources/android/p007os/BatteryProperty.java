package android.p007os;

import android.p007os.Parcelable;

/* renamed from: android.os.BatteryProperty */
/* loaded from: classes3.dex */
public class BatteryProperty implements Parcelable {
    public static final Parcelable.Creator<BatteryProperty> CREATOR = new Parcelable.Creator<BatteryProperty>() { // from class: android.os.BatteryProperty.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public BatteryProperty createFromParcel(Parcel p) {
            return new BatteryProperty(p);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public BatteryProperty[] newArray(int size) {
            return new BatteryProperty[size];
        }
    };
    private long mValueLong;

    public BatteryProperty() {
        this.mValueLong = Long.MIN_VALUE;
    }

    public long getLong() {
        return this.mValueLong;
    }

    public void setLong(long val) {
        this.mValueLong = val;
    }

    private BatteryProperty(Parcel p) {
        readFromParcel(p);
    }

    public void readFromParcel(Parcel p) {
        this.mValueLong = p.readLong();
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(this.mValueLong);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
