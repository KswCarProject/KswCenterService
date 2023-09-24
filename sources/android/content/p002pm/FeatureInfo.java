package android.content.p002pm;

import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.proto.ProtoOutputStream;

/* renamed from: android.content.pm.FeatureInfo */
/* loaded from: classes.dex */
public class FeatureInfo implements Parcelable {
    public static final Parcelable.Creator<FeatureInfo> CREATOR = new Parcelable.Creator<FeatureInfo>() { // from class: android.content.pm.FeatureInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public FeatureInfo createFromParcel(Parcel source) {
            return new FeatureInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public FeatureInfo[] newArray(int size) {
            return new FeatureInfo[size];
        }
    };
    public static final int FLAG_REQUIRED = 1;
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public int flags;
    public String name;
    public int reqGlEsVersion;
    public int version;

    public FeatureInfo() {
    }

    public FeatureInfo(FeatureInfo orig) {
        this.name = orig.name;
        this.version = orig.version;
        this.reqGlEsVersion = orig.reqGlEsVersion;
        this.flags = orig.flags;
    }

    public String toString() {
        if (this.name != null) {
            return "FeatureInfo{" + Integer.toHexString(System.identityHashCode(this)) + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + this.name + " v=" + this.version + " fl=0x" + Integer.toHexString(this.flags) + "}";
        }
        return "FeatureInfo{" + Integer.toHexString(System.identityHashCode(this)) + " glEsVers=" + getGlEsVersion() + " fl=0x" + Integer.toHexString(this.flags) + "}";
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeString(this.name);
        dest.writeInt(this.version);
        dest.writeInt(this.reqGlEsVersion);
        dest.writeInt(this.flags);
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        if (this.name != null) {
            proto.write(1138166333441L, this.name);
        }
        proto.write(1120986464258L, this.version);
        proto.write(1138166333443L, getGlEsVersion());
        proto.write(1120986464260L, this.flags);
        proto.end(token);
    }

    private FeatureInfo(Parcel source) {
        this.name = source.readString();
        this.version = source.readInt();
        this.reqGlEsVersion = source.readInt();
        this.flags = source.readInt();
    }

    public String getGlEsVersion() {
        int major = (this.reqGlEsVersion & (-65536)) >> 16;
        int minor = this.reqGlEsVersion & 65535;
        return String.valueOf(major) + "." + String.valueOf(minor);
    }
}
