package android.telephony;

import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.util.Objects;

public final class CellSignalStrengthTdscdma extends CellSignalStrength implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthTdscdma> CREATOR = new Parcelable.Creator<CellSignalStrengthTdscdma>() {
        public CellSignalStrengthTdscdma createFromParcel(Parcel in) {
            return new CellSignalStrengthTdscdma(in);
        }

        public CellSignalStrengthTdscdma[] newArray(int size) {
            return new CellSignalStrengthTdscdma[size];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthTdscdma";
    private static final int TDSCDMA_RSCP_GOOD = -73;
    private static final int TDSCDMA_RSCP_GREAT = -49;
    private static final int TDSCDMA_RSCP_MAX = -24;
    private static final int TDSCDMA_RSCP_MIN = -120;
    private static final int TDSCDMA_RSCP_MODERATE = -97;
    private static final int TDSCDMA_RSCP_POOR = -110;
    private static final CellSignalStrengthTdscdma sInvalid = new CellSignalStrengthTdscdma();
    private int mBitErrorRate;
    private int mLevel;
    private int mRscp;
    private int mRssi;

    public CellSignalStrengthTdscdma() {
        setDefaultValues();
    }

    public CellSignalStrengthTdscdma(int rssi, int ber, int rscp) {
        this.mRssi = inRangeOrUnavailable(rssi, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(ber, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(rscp, -120, -24);
        updateLevel((PersistableBundle) null, (ServiceState) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public CellSignalStrengthTdscdma(TdScdmaSignalStrength tdscdma) {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, tdscdma.rscp != Integer.MAX_VALUE ? -tdscdma.rscp : tdscdma.rscp);
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            setDefaultValues();
        }
    }

    public CellSignalStrengthTdscdma(TdscdmaSignalStrength tdscdma) {
        this(getRssiDbmFromAsu(tdscdma.signalStrength), tdscdma.bitErrorRate, getRscpDbmFromAsu(tdscdma.rscp));
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            setDefaultValues();
        }
    }

    public CellSignalStrengthTdscdma(CellSignalStrengthTdscdma s) {
        copyFrom(s);
    }

    /* access modifiers changed from: protected */
    public void copyFrom(CellSignalStrengthTdscdma s) {
        this.mRssi = s.mRssi;
        this.mBitErrorRate = s.mBitErrorRate;
        this.mRscp = s.mRscp;
        this.mLevel = s.mLevel;
    }

    public CellSignalStrengthTdscdma copy() {
        return new CellSignalStrengthTdscdma(this);
    }

    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public void updateLevel(PersistableBundle cc, ServiceState ss) {
        if (this.mRscp > -24) {
            this.mLevel = 0;
        } else if (this.mRscp >= -49) {
            this.mLevel = 4;
        } else if (this.mRscp >= TDSCDMA_RSCP_GOOD) {
            this.mLevel = 3;
        } else if (this.mRscp >= -97) {
            this.mLevel = 2;
        } else if (this.mRscp >= -110) {
            this.mLevel = 1;
        } else {
            this.mLevel = 0;
        }
    }

    public int getDbm() {
        return this.mRscp;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    public int getAsuLevel() {
        if (this.mRscp != Integer.MAX_VALUE) {
            return getAsuFromRscpDbm(this.mRscp);
        }
        if (this.mRssi != Integer.MAX_VALUE) {
            return getAsuFromRssiDbm(this.mRssi);
        }
        return getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mRssi), Integer.valueOf(this.mBitErrorRate), Integer.valueOf(this.mRscp), Integer.valueOf(this.mLevel)});
    }

    public boolean isValid() {
        return !equals(sInvalid);
    }

    public boolean equals(Object o) {
        if (!(o instanceof CellSignalStrengthTdscdma)) {
            return false;
        }
        CellSignalStrengthTdscdma s = (CellSignalStrengthTdscdma) o;
        if (this.mRssi == s.mRssi && this.mBitErrorRate == s.mBitErrorRate && this.mRscp == s.mRscp && this.mLevel == s.mLevel) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "CellSignalStrengthTdscdma: rssi=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " level=" + this.mLevel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mBitErrorRate);
        dest.writeInt(this.mRscp);
        dest.writeInt(this.mLevel);
    }

    private CellSignalStrengthTdscdma(Parcel in) {
        this.mRssi = in.readInt();
        this.mBitErrorRate = in.readInt();
        this.mRscp = in.readInt();
        this.mLevel = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    private static void log(String s) {
        Rlog.w(LOG_TAG, s);
    }
}
