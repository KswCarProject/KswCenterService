package android.content.p002pm;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* renamed from: android.content.pm.PackageInfoLite */
/* loaded from: classes.dex */
public class PackageInfoLite implements Parcelable {
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static final Parcelable.Creator<PackageInfoLite> CREATOR = new Parcelable.Creator<PackageInfoLite>() { // from class: android.content.pm.PackageInfoLite.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PackageInfoLite createFromParcel(Parcel source) {
            return new PackageInfoLite(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PackageInfoLite[] newArray(int size) {
            return new PackageInfoLite[size];
        }
    };
    public int baseRevisionCode;
    public int installLocation;
    public boolean multiArch;
    public String packageName;
    public int recommendedInstallLocation;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public VerifierInfo[] verifiers;
    @Deprecated
    public int versionCode;
    public int versionCodeMajor;

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public PackageInfoLite() {
    }

    public String toString() {
        return "PackageInfoLite{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.packageName + "}";
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeString(this.packageName);
        dest.writeStringArray(this.splitNames);
        dest.writeInt(this.versionCode);
        dest.writeInt(this.versionCodeMajor);
        dest.writeInt(this.baseRevisionCode);
        dest.writeIntArray(this.splitRevisionCodes);
        dest.writeInt(this.recommendedInstallLocation);
        dest.writeInt(this.installLocation);
        dest.writeInt(this.multiArch ? 1 : 0);
        if (this.verifiers == null || this.verifiers.length == 0) {
            dest.writeInt(0);
            return;
        }
        dest.writeInt(this.verifiers.length);
        dest.writeTypedArray(this.verifiers, parcelableFlags);
    }

    private PackageInfoLite(Parcel source) {
        this.packageName = source.readString();
        this.splitNames = source.createStringArray();
        this.versionCode = source.readInt();
        this.versionCodeMajor = source.readInt();
        this.baseRevisionCode = source.readInt();
        this.splitRevisionCodes = source.createIntArray();
        this.recommendedInstallLocation = source.readInt();
        this.installLocation = source.readInt();
        this.multiArch = source.readInt() != 0;
        int verifiersLength = source.readInt();
        if (verifiersLength == 0) {
            this.verifiers = new VerifierInfo[0];
            return;
        }
        this.verifiers = new VerifierInfo[verifiersLength];
        source.readTypedArray(this.verifiers, VerifierInfo.CREATOR);
    }
}
