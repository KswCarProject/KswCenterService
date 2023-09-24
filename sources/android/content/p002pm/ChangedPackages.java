package android.content.p002pm;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.util.List;

/* renamed from: android.content.pm.ChangedPackages */
/* loaded from: classes.dex */
public final class ChangedPackages implements Parcelable {
    public static final Parcelable.Creator<ChangedPackages> CREATOR = new Parcelable.Creator<ChangedPackages>() { // from class: android.content.pm.ChangedPackages.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ChangedPackages createFromParcel(Parcel in) {
            return new ChangedPackages(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ChangedPackages[] newArray(int size) {
            return new ChangedPackages[size];
        }
    };
    private final List<String> mPackageNames;
    private final int mSequenceNumber;

    public ChangedPackages(int sequenceNumber, List<String> packageNames) {
        this.mSequenceNumber = sequenceNumber;
        this.mPackageNames = packageNames;
    }

    protected ChangedPackages(Parcel in) {
        this.mSequenceNumber = in.readInt();
        this.mPackageNames = in.createStringArrayList();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSequenceNumber);
        dest.writeStringList(this.mPackageNames);
    }

    public int getSequenceNumber() {
        return this.mSequenceNumber;
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }
}
