package android.app.timedetector;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.TimestampedValue;
import java.util.Objects;

public final class TimeSignal implements Parcelable {
    public static final Parcelable.Creator<TimeSignal> CREATOR = new Parcelable.Creator<TimeSignal>() {
        public TimeSignal createFromParcel(Parcel in) {
            return TimeSignal.createFromParcel(in);
        }

        public TimeSignal[] newArray(int size) {
            return new TimeSignal[size];
        }
    };
    public static final String SOURCE_ID_NITZ = "nitz";
    private final String mSourceId;
    private final TimestampedValue<Long> mUtcTime;

    /* JADX WARNING: type inference failed for: r3v0, types: [android.util.TimestampedValue<java.lang.Long>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TimeSignal(java.lang.String r2, android.util.TimestampedValue<java.lang.Long> r3) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = java.util.Objects.requireNonNull(r2)
            java.lang.String r0 = (java.lang.String) r0
            r1.mSourceId = r0
            java.lang.Object r0 = java.util.Objects.requireNonNull(r3)
            android.util.TimestampedValue r0 = (android.util.TimestampedValue) r0
            r1.mUtcTime = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.timedetector.TimeSignal.<init>(java.lang.String, android.util.TimestampedValue):void");
    }

    /* access modifiers changed from: private */
    public static TimeSignal createFromParcel(Parcel in) {
        return new TimeSignal(in.readString(), TimestampedValue.readFromParcel(in, (ClassLoader) null, Long.class));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSourceId);
        TimestampedValue.writeToParcel(dest, this.mUtcTime);
    }

    public String getSourceId() {
        return this.mSourceId;
    }

    public TimestampedValue<Long> getUtcTime() {
        return this.mUtcTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeSignal that = (TimeSignal) o;
        if (!Objects.equals(this.mSourceId, that.mSourceId) || !Objects.equals(this.mUtcTime, that.mUtcTime)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.mSourceId, this.mUtcTime});
    }

    public String toString() {
        return "TimeSignal{mSourceId='" + this.mSourceId + DateFormat.QUOTE + ", mUtcTime=" + this.mUtcTime + '}';
    }
}
