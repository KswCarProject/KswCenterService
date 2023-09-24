package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public class SmsRawData implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<SmsRawData> CREATOR = new Parcelable.Creator<SmsRawData>() { // from class: com.android.internal.telephony.SmsRawData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SmsRawData createFromParcel(Parcel source) {
            int size = source.readInt();
            byte[] data = new byte[size];
            source.readByteArray(data);
            return new SmsRawData(data);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SmsRawData[] newArray(int size) {
            return new SmsRawData[size];
        }
    };
    byte[] data;

    @UnsupportedAppUsage
    public SmsRawData(byte[] data) {
        this.data = data;
    }

    @UnsupportedAppUsage
    public byte[] getBytes() {
        return this.data;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.data.length);
        dest.writeByteArray(this.data);
    }
}
