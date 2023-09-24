package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import com.android.internal.content.NativeLibraryHelper;

@Deprecated
/* loaded from: classes.dex */
public class NeighboringCellInfo implements Parcelable {
    public static final Parcelable.Creator<NeighboringCellInfo> CREATOR = new Parcelable.Creator<NeighboringCellInfo>() { // from class: android.telephony.NeighboringCellInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public NeighboringCellInfo createFromParcel(Parcel in) {
            return new NeighboringCellInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public NeighboringCellInfo[] newArray(int size) {
            return new NeighboringCellInfo[size];
        }
    };
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mCid;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mLac;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mNetworkType;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mPsc;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mRssi;

    @Deprecated
    public NeighboringCellInfo() {
        this.mRssi = 99;
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
        this.mNetworkType = 0;
    }

    @Deprecated
    public NeighboringCellInfo(int rssi, int cid) {
        this.mRssi = rssi;
        this.mCid = cid;
    }

    public NeighboringCellInfo(CellInfoGsm info) {
        this.mNetworkType = 1;
        this.mRssi = info.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = info.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = info.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = -1;
    }

    public NeighboringCellInfo(CellInfoWcdma info) {
        this.mNetworkType = 3;
        this.mRssi = info.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = info.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = info.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = info.getCellIdentity().getPsc();
        if (this.mPsc == Integer.MAX_VALUE) {
            this.mPsc = -1;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0068 -> B:23:0x0070). Please submit an issue!!! */
    public NeighboringCellInfo(int rssi, String location, int radioType) {
        this.mRssi = rssi;
        this.mNetworkType = 0;
        this.mPsc = -1;
        this.mLac = -1;
        this.mCid = -1;
        int l = location.length();
        if (l > 8) {
            return;
        }
        if (l < 8) {
            String location2 = location;
            for (int i = 0; i < 8 - l; i++) {
                location2 = "0" + location2;
            }
            location = location2;
        }
        try {
            switch (radioType) {
                case 1:
                case 2:
                    this.mNetworkType = radioType;
                    if (!location.equalsIgnoreCase("FFFFFFFF")) {
                        this.mCid = Integer.parseInt(location.substring(4), 16);
                        this.mLac = Integer.parseInt(location.substring(0, 4), 16);
                        break;
                    }
                    break;
                default:
                    switch (radioType) {
                        case 8:
                        case 9:
                        case 10:
                            break;
                        default:
                            return;
                    }
                case 3:
                    this.mNetworkType = radioType;
                    this.mPsc = Integer.parseInt(location, 16);
                    break;
            }
        } catch (NumberFormatException e) {
            this.mPsc = -1;
            this.mLac = -1;
            this.mCid = -1;
            this.mNetworkType = 0;
        }
    }

    public NeighboringCellInfo(Parcel in) {
        this.mRssi = in.readInt();
        this.mLac = in.readInt();
        this.mCid = in.readInt();
        this.mPsc = in.readInt();
        this.mNetworkType = in.readInt();
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getPsc() {
        return this.mPsc;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @Deprecated
    public void setCid(int cid) {
        this.mCid = cid;
    }

    @Deprecated
    public void setRssi(int rssi) {
        this.mRssi = rssi;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (this.mPsc != -1) {
            sb.append(Integer.toHexString(this.mPsc));
            sb.append("@");
            sb.append(this.mRssi == 99 ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE : Integer.valueOf(this.mRssi));
        } else if (this.mLac != -1 && this.mCid != -1) {
            sb.append(Integer.toHexString(this.mLac));
            sb.append(Integer.toHexString(this.mCid));
            sb.append("@");
            sb.append(this.mRssi == 99 ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE : Integer.valueOf(this.mRssi));
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mLac);
        dest.writeInt(this.mCid);
        dest.writeInt(this.mPsc);
        dest.writeInt(this.mNetworkType);
    }
}
