package com.android.internal.telephony;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public class CallInfo implements Parcelable {
    public static final Parcelable.Creator<CallInfo> CREATOR = new Parcelable.Creator<CallInfo>() { // from class: com.android.internal.telephony.CallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CallInfo createFromParcel(Parcel source) {
            return new CallInfo(source.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public CallInfo[] newArray(int size) {
            return new CallInfo[size];
        }
    };
    private String handle;

    public CallInfo(String handle) {
        this.handle = handle;
    }

    public String getHandle() {
        return this.handle;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(this.handle);
    }
}
