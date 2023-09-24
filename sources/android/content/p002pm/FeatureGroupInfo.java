package android.content.p002pm;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* renamed from: android.content.pm.FeatureGroupInfo */
/* loaded from: classes.dex */
public final class FeatureGroupInfo implements Parcelable {
    public static final Parcelable.Creator<FeatureGroupInfo> CREATOR = new Parcelable.Creator<FeatureGroupInfo>() { // from class: android.content.pm.FeatureGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public FeatureGroupInfo createFromParcel(Parcel source) {
            FeatureGroupInfo group = new FeatureGroupInfo();
            group.features = (FeatureInfo[]) source.createTypedArray(FeatureInfo.CREATOR);
            return group;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public FeatureGroupInfo[] newArray(int size) {
            return new FeatureGroupInfo[size];
        }
    };
    public FeatureInfo[] features;

    public FeatureGroupInfo() {
    }

    public FeatureGroupInfo(FeatureGroupInfo other) {
        this.features = other.features;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.features, flags);
    }
}
