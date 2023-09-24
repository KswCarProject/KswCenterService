package android.net.wifi;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes3.dex */
public class PasspointManagementObjectDefinition implements Parcelable {
    public static final Parcelable.Creator<PasspointManagementObjectDefinition> CREATOR = new Parcelable.Creator<PasspointManagementObjectDefinition>() { // from class: android.net.wifi.PasspointManagementObjectDefinition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PasspointManagementObjectDefinition createFromParcel(Parcel in) {
            return new PasspointManagementObjectDefinition(in.readString(), in.readString(), in.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public PasspointManagementObjectDefinition[] newArray(int size) {
            return new PasspointManagementObjectDefinition[size];
        }
    };
    private final String mBaseUri;
    private final String mMoTree;
    private final String mUrn;

    public PasspointManagementObjectDefinition(String baseUri, String urn, String moTree) {
        this.mBaseUri = baseUri;
        this.mUrn = urn;
        this.mMoTree = moTree;
    }

    public String getBaseUri() {
        return this.mBaseUri;
    }

    public String getUrn() {
        return this.mUrn;
    }

    public String getMoTree() {
        return this.mMoTree;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBaseUri);
        dest.writeString(this.mUrn);
        dest.writeString(this.mMoTree);
    }
}
