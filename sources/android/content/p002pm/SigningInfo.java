package android.content.p002pm;

import android.content.p002pm.PackageParser;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* renamed from: android.content.pm.SigningInfo */
/* loaded from: classes.dex */
public final class SigningInfo implements Parcelable {
    public static final Parcelable.Creator<SigningInfo> CREATOR = new Parcelable.Creator<SigningInfo>() { // from class: android.content.pm.SigningInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SigningInfo createFromParcel(Parcel source) {
            return new SigningInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SigningInfo[] newArray(int size) {
            return new SigningInfo[size];
        }
    };
    private final PackageParser.SigningDetails mSigningDetails;

    public SigningInfo() {
        this.mSigningDetails = PackageParser.SigningDetails.UNKNOWN;
    }

    public SigningInfo(PackageParser.SigningDetails signingDetails) {
        this.mSigningDetails = new PackageParser.SigningDetails(signingDetails);
    }

    public SigningInfo(SigningInfo orig) {
        this.mSigningDetails = new PackageParser.SigningDetails(orig.mSigningDetails);
    }

    private SigningInfo(Parcel source) {
        this.mSigningDetails = PackageParser.SigningDetails.CREATOR.createFromParcel(source);
    }

    public boolean hasMultipleSigners() {
        return this.mSigningDetails.signatures != null && this.mSigningDetails.signatures.length > 1;
    }

    public boolean hasPastSigningCertificates() {
        return (this.mSigningDetails.signatures == null || this.mSigningDetails.pastSigningCertificates == null) ? false : true;
    }

    public Signature[] getSigningCertificateHistory() {
        if (hasMultipleSigners()) {
            return null;
        }
        if (!hasPastSigningCertificates()) {
            return this.mSigningDetails.signatures;
        }
        return this.mSigningDetails.pastSigningCertificates;
    }

    public Signature[] getApkContentsSigners() {
        return this.mSigningDetails.signatures;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        this.mSigningDetails.writeToParcel(dest, parcelableFlags);
    }
}
