package com.android.internal.net;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.net.NetworkInfo;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.util.Log;

/* loaded from: classes4.dex */
public class LegacyVpnInfo implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<LegacyVpnInfo> CREATOR = new Parcelable.Creator<LegacyVpnInfo>() { // from class: com.android.internal.net.LegacyVpnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LegacyVpnInfo createFromParcel(Parcel in) {
            LegacyVpnInfo info = new LegacyVpnInfo();
            info.key = in.readString();
            info.state = in.readInt();
            info.intent = (PendingIntent) in.readParcelable(null);
            return info;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public LegacyVpnInfo[] newArray(int size) {
            return new LegacyVpnInfo[size];
        }
    };
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 5;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_TIMEOUT = 4;
    private static final String TAG = "LegacyVpnInfo";
    public PendingIntent intent;
    @UnsupportedAppUsage
    public String key;
    @UnsupportedAppUsage
    public int state = -1;

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.key);
        out.writeInt(this.state);
        out.writeParcelable(this.intent, flags);
    }

    public static int stateFromNetworkInfo(NetworkInfo info) {
        switch (info.getDetailedState()) {
            case CONNECTING:
                return 2;
            case CONNECTED:
                return 3;
            case DISCONNECTED:
                return 0;
            case FAILED:
                return 5;
            default:
                Log.m64w(TAG, "Unhandled state " + info.getDetailedState() + " ; treating as disconnected");
                return 0;
        }
    }
}
