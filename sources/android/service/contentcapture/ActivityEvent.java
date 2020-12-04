package android.service.contentcapture;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ActivityEvent implements Parcelable {
    public static final Parcelable.Creator<ActivityEvent> CREATOR = new Parcelable.Creator<ActivityEvent>() {
        public ActivityEvent createFromParcel(Parcel parcel) {
            return new ActivityEvent((ComponentName) parcel.readParcelable((ClassLoader) null), parcel.readInt());
        }

        public ActivityEvent[] newArray(int size) {
            return new ActivityEvent[size];
        }
    };
    public static final int TYPE_ACTIVITY_DESTROYED = 24;
    public static final int TYPE_ACTIVITY_PAUSED = 2;
    public static final int TYPE_ACTIVITY_RESUMED = 1;
    public static final int TYPE_ACTIVITY_STOPPED = 23;
    private final ComponentName mComponentName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityEventType {
    }

    public ActivityEvent(ComponentName componentName, int type) {
        this.mComponentName = componentName;
        this.mType = type;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getEventType() {
        return this.mType;
    }

    public static String getTypeAsString(int type) {
        switch (type) {
            case 1:
                return "ACTIVITY_RESUMED";
            case 2:
                return "ACTIVITY_PAUSED";
            case 23:
                return "ACTIVITY_STOPPED";
            case 24:
                return "ACTIVITY_DESTROYED";
            default:
                return "UKNOWN_TYPE: " + type;
        }
    }

    public String toString() {
        return "ActivityEvent[" + this.mComponentName.toShortString() + "]:" + getTypeAsString(this.mType);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mComponentName, flags);
        parcel.writeInt(this.mType);
    }
}
