package com.android.internal.net;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.format.DateFormat;

/* loaded from: classes4.dex */
public class VpnInfo implements Parcelable {
    public static final Parcelable.Creator<VpnInfo> CREATOR = new Parcelable.Creator<VpnInfo>() { // from class: com.android.internal.net.VpnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VpnInfo createFromParcel(Parcel source) {
            VpnInfo info = new VpnInfo();
            info.ownerUid = source.readInt();
            info.vpnIface = source.readString();
            info.primaryUnderlyingIface = source.readString();
            return info;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VpnInfo[] newArray(int size) {
            return new VpnInfo[size];
        }
    };
    public int ownerUid;
    public String primaryUnderlyingIface;
    public String vpnIface;

    public String toString() {
        return "VpnInfo{ownerUid=" + this.ownerUid + ", vpnIface='" + this.vpnIface + DateFormat.QUOTE + ", primaryUnderlyingIface='" + this.primaryUnderlyingIface + DateFormat.QUOTE + '}';
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ownerUid);
        dest.writeString(this.vpnIface);
        dest.writeString(this.primaryUnderlyingIface);
    }
}
