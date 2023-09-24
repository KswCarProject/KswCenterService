package android.telecom;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.Process;
import android.p007os.UserHandle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PhoneAccountHandle implements Parcelable {
    public static final Parcelable.Creator<PhoneAccountHandle> CREATOR = new Parcelable.Creator<PhoneAccountHandle>() { // from class: android.telecom.PhoneAccountHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PhoneAccountHandle createFromParcel(Parcel in) {
            return new PhoneAccountHandle(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PhoneAccountHandle[] newArray(int size) {
            return new PhoneAccountHandle[size];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 127403196)
    private final ComponentName mComponentName;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private final String mId;
    private final UserHandle mUserHandle;

    public PhoneAccountHandle(ComponentName componentName, String id) {
        this(componentName, id, Process.myUserHandle());
    }

    public PhoneAccountHandle(ComponentName componentName, String id, UserHandle userHandle) {
        checkParameters(componentName, userHandle);
        this.mComponentName = componentName;
        this.mId = id;
        this.mUserHandle = userHandle;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getId() {
        return this.mId;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public int hashCode() {
        return Objects.hash(this.mComponentName, this.mId, this.mUserHandle);
    }

    public String toString() {
        return this.mComponentName + ", " + Log.pii(this.mId) + ", " + this.mUserHandle;
    }

    public boolean equals(Object other) {
        return other != null && (other instanceof PhoneAccountHandle) && Objects.equals(((PhoneAccountHandle) other).getComponentName(), getComponentName()) && Objects.equals(((PhoneAccountHandle) other).getId(), getId()) && Objects.equals(((PhoneAccountHandle) other).getUserHandle(), getUserHandle());
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        this.mComponentName.writeToParcel(out, flags);
        out.writeString(this.mId);
        this.mUserHandle.writeToParcel(out, flags);
    }

    private void checkParameters(ComponentName componentName, UserHandle userHandle) {
        if (componentName == null) {
            android.util.Log.m62w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null ComponentName!"));
        }
        if (userHandle == null) {
            android.util.Log.m62w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null UserHandle!"));
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private PhoneAccountHandle(Parcel in) {
        this(ComponentName.CREATOR.createFromParcel(in), in.readString(), UserHandle.CREATOR.createFromParcel(in));
    }

    public static boolean areFromSamePackage(PhoneAccountHandle a, PhoneAccountHandle b) {
        String aPackageName = a != null ? a.getComponentName().getPackageName() : null;
        String bPackageName = b != null ? b.getComponentName().getPackageName() : null;
        return Objects.equals(aPackageName, bPackageName);
    }
}
