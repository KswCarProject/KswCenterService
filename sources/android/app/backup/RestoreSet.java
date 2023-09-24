package android.app.backup;

import android.annotation.SystemApi;
import android.p007os.Parcel;
import android.p007os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public class RestoreSet implements Parcelable {
    public static final Parcelable.Creator<RestoreSet> CREATOR = new Parcelable.Creator<RestoreSet>() { // from class: android.app.backup.RestoreSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RestoreSet createFromParcel(Parcel in) {
            return new RestoreSet(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public RestoreSet[] newArray(int size) {
            return new RestoreSet[size];
        }
    };
    public String device;
    public String name;
    public long token;

    public RestoreSet() {
    }

    public RestoreSet(String _name, String _dev, long _token) {
        this.name = _name;
        this.device = _dev;
        this.token = _token;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeString(this.device);
        out.writeLong(this.token);
    }

    private RestoreSet(Parcel in) {
        this.name = in.readString();
        this.device = in.readString();
        this.token = in.readLong();
    }
}
