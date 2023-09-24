package android.location;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes.dex */
public final class LocationTime implements Parcelable {
    public static final Parcelable.Creator<LocationTime> CREATOR = new Parcelable.Creator<LocationTime>() { // from class: android.location.LocationTime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LocationTime createFromParcel(Parcel in) {
            long time = in.readLong();
            long elapsedRealtimeNanos = in.readLong();
            return new LocationTime(time, elapsedRealtimeNanos);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LocationTime[] newArray(int size) {
            return new LocationTime[size];
        }
    };
    private final long mElapsedRealtimeNanos;
    private final long mTime;

    public LocationTime(long time, long elapsedRealtimeNanos) {
        this.mTime = time;
        this.mElapsedRealtimeNanos = elapsedRealtimeNanos;
    }

    public long getTime() {
        return this.mTime;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.mTime);
        out.writeLong(this.mElapsedRealtimeNanos);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
