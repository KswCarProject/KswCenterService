package android.bluetooth.p000le;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* renamed from: android.bluetooth.le.PeriodicAdvertisingParameters */
/* loaded from: classes.dex */
public final class PeriodicAdvertisingParameters implements Parcelable {
    public static final Parcelable.Creator<PeriodicAdvertisingParameters> CREATOR = new Parcelable.Creator<PeriodicAdvertisingParameters>() { // from class: android.bluetooth.le.PeriodicAdvertisingParameters.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PeriodicAdvertisingParameters[] newArray(int size) {
            return new PeriodicAdvertisingParameters[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PeriodicAdvertisingParameters createFromParcel(Parcel in) {
            return new PeriodicAdvertisingParameters(in);
        }
    };
    private static final int INTERVAL_MAX = 65519;
    private static final int INTERVAL_MIN = 80;
    private final boolean mIncludeTxPower;
    private final int mInterval;

    private PeriodicAdvertisingParameters(boolean includeTxPower, int interval) {
        this.mIncludeTxPower = includeTxPower;
        this.mInterval = interval;
    }

    private PeriodicAdvertisingParameters(Parcel in) {
        this.mIncludeTxPower = in.readInt() != 0;
        this.mInterval = in.readInt();
    }

    public boolean getIncludeTxPower() {
        return this.mIncludeTxPower;
    }

    public int getInterval() {
        return this.mInterval;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mIncludeTxPower ? 1 : 0);
        dest.writeInt(this.mInterval);
    }

    /* renamed from: android.bluetooth.le.PeriodicAdvertisingParameters$Builder */
    /* loaded from: classes.dex */
    public static final class Builder {
        private boolean mIncludeTxPower = false;
        private int mInterval = PeriodicAdvertisingParameters.INTERVAL_MAX;

        public Builder setIncludeTxPower(boolean includeTxPower) {
            this.mIncludeTxPower = includeTxPower;
            return this;
        }

        public Builder setInterval(int interval) {
            if (interval < 80 || interval > PeriodicAdvertisingParameters.INTERVAL_MAX) {
                throw new IllegalArgumentException("Invalid interval (must be 80-65519)");
            }
            this.mInterval = interval;
            return this;
        }

        public PeriodicAdvertisingParameters build() {
            return new PeriodicAdvertisingParameters(this.mIncludeTxPower, this.mInterval);
        }
    }
}
