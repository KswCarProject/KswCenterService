package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;

/* loaded from: classes4.dex */
public class OptionsCapInfo implements Parcelable {
    public static final Parcelable.Creator<OptionsCapInfo> CREATOR = new Parcelable.Creator<OptionsCapInfo>() { // from class: com.android.ims.internal.uce.options.OptionsCapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OptionsCapInfo createFromParcel(Parcel source) {
            return new OptionsCapInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public OptionsCapInfo[] newArray(int size) {
            return new OptionsCapInfo[size];
        }
    };
    private CapInfo mCapInfo;
    private String mSdp;

    public static OptionsCapInfo getOptionsCapInfoInstance() {
        return new OptionsCapInfo();
    }

    @UnsupportedAppUsage
    public String getSdp() {
        return this.mSdp;
    }

    @UnsupportedAppUsage
    public void setSdp(String sdp) {
        this.mSdp = sdp;
    }

    @UnsupportedAppUsage
    public OptionsCapInfo() {
        this.mSdp = "";
        this.mCapInfo = new CapInfo();
    }

    @UnsupportedAppUsage
    public CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    @UnsupportedAppUsage
    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSdp);
        dest.writeParcelable(this.mCapInfo, flags);
    }

    private OptionsCapInfo(Parcel source) {
        this.mSdp = "";
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        this.mSdp = source.readString();
        this.mCapInfo = (CapInfo) source.readParcelable(CapInfo.class.getClassLoader());
    }
}
