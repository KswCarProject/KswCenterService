package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.WcdmaSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class CellSignalStrengthWcdma extends CellSignalStrength implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthWcdma> CREATOR = new Parcelable.Creator<CellSignalStrengthWcdma>() {
        public CellSignalStrengthWcdma createFromParcel(Parcel in) {
            return new CellSignalStrengthWcdma(in);
        }

        public CellSignalStrengthWcdma[] newArray(int size) {
            return new CellSignalStrengthWcdma[size];
        }
    };
    private static final boolean DBG = false;
    private static final String DEFAULT_LEVEL_CALCULATION_METHOD = "rssi";
    public static final String LEVEL_CALCULATION_METHOD_RSCP = "rscp";
    public static final String LEVEL_CALCULATION_METHOD_RSSI = "rssi";
    private static final String LOG_TAG = "CellSignalStrengthWcdma";
    private static final int WCDMA_RSCP_GOOD = -95;
    private static final int WCDMA_RSCP_GREAT = -85;
    private static final int WCDMA_RSCP_MAX = -24;
    private static final int WCDMA_RSCP_MIN = -120;
    private static final int WCDMA_RSCP_MODERATE = -105;
    private static final int WCDMA_RSCP_POOR = -115;
    private static final int WCDMA_RSSI_GOOD = -87;
    private static final int WCDMA_RSSI_GREAT = -77;
    private static final int WCDMA_RSSI_MAX = -51;
    private static final int WCDMA_RSSI_MIN = -113;
    private static final int WCDMA_RSSI_MODERATE = -97;
    private static final int WCDMA_RSSI_POOR = -107;
    private static final CellSignalStrengthWcdma sInvalid = new CellSignalStrengthWcdma();
    private static final int[] sRscpThresholds = {-115, -105, -95, WCDMA_RSCP_GREAT};
    private static final int[] sRssiThresholds = {-107, -97, WCDMA_RSSI_GOOD, WCDMA_RSSI_GREAT};
    @UnsupportedAppUsage
    private int mBitErrorRate;
    private int mEcNo;
    private int mLevel;
    private int mRscp;
    private int mRssi;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LevelCalculationMethod {
    }

    public CellSignalStrengthWcdma() {
        setDefaultValues();
    }

    public CellSignalStrengthWcdma(int rssi, int ber, int rscp, int ecno) {
        this.mRssi = inRangeOrUnavailable(rssi, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(ber, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(rscp, -120, -24);
        this.mEcNo = inRangeOrUnavailable(ecno, -24, 1);
        updateLevel((PersistableBundle) null, (ServiceState) null);
    }

    public CellSignalStrengthWcdma(WcdmaSignalStrength wcdma) {
        this(getRssiDbmFromAsu(wcdma.signalStrength), wcdma.bitErrorRate, Integer.MAX_VALUE, Integer.MAX_VALUE);
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            setDefaultValues();
        }
    }

    public CellSignalStrengthWcdma(android.hardware.radio.V1_2.WcdmaSignalStrength wcdma) {
        this(getRssiDbmFromAsu(wcdma.base.signalStrength), wcdma.base.bitErrorRate, getRscpDbmFromAsu(wcdma.rscp), getEcNoDbFromAsu(wcdma.ecno));
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            setDefaultValues();
        }
    }

    public CellSignalStrengthWcdma(CellSignalStrengthWcdma s) {
        copyFrom(s);
    }

    /* access modifiers changed from: protected */
    public void copyFrom(CellSignalStrengthWcdma s) {
        this.mRssi = s.mRssi;
        this.mBitErrorRate = s.mBitErrorRate;
        this.mRscp = s.mRscp;
        this.mEcNo = s.mEcNo;
        this.mLevel = s.mLevel;
    }

    public CellSignalStrengthWcdma copy() {
        return new CellSignalStrengthWcdma(this);
    }

    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mEcNo = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public int getLevel() {
        return this.mLevel;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateLevel(android.os.PersistableBundle r8, android.telephony.ServiceState r9) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0008
            java.lang.String r0 = "rssi"
            int[] r1 = sRscpThresholds
            goto L_0x002a
        L_0x0008:
            java.lang.String r0 = "wcdma_default_signal_strength_measurement_string"
            java.lang.String r1 = "rssi"
            java.lang.String r0 = r8.getString(r0, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x001b
            java.lang.String r0 = "rssi"
        L_0x001b:
            java.lang.String r1 = "wcdma_rscp_thresholds_int_array"
            int[] r1 = r8.getIntArray(r1)
            if (r1 == 0) goto L_0x0028
            int r2 = r1.length
            r3 = 4
            if (r2 == r3) goto L_0x002a
        L_0x0028:
            int[] r1 = sRscpThresholds
        L_0x002a:
            r2 = 4
            int r3 = r0.hashCode()
            r4 = 3509870(0x358e6e, float:4.918375E-39)
            r5 = 2
            r6 = 0
            if (r3 == r4) goto L_0x0047
            r4 = 3510359(0x359057, float:4.91906E-39)
            if (r3 == r4) goto L_0x003c
            goto L_0x0052
        L_0x003c:
            java.lang.String r3 = "rssi"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0052
            r3 = r5
            goto L_0x0053
        L_0x0047:
            java.lang.String r3 = "rscp"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0052
            r3 = r6
            goto L_0x0053
        L_0x0052:
            r3 = -1
        L_0x0053:
            if (r3 == 0) goto L_0x008d
            if (r3 == r5) goto L_0x006b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid Level Calculation Method for CellSignalStrengthWcdma = "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            loge(r3)
        L_0x006b:
            int r3 = r7.mRssi
            r4 = -113(0xffffffffffffff8f, float:NaN)
            if (r3 < r4) goto L_0x008a
            int r3 = r7.mRssi
            r4 = -51
            if (r3 <= r4) goto L_0x0078
            goto L_0x008a
        L_0x0078:
            if (r2 <= 0) goto L_0x0087
            int r3 = r7.mRssi
            int[] r4 = sRssiThresholds
            int r5 = r2 + -1
            r4 = r4[r5]
            if (r3 >= r4) goto L_0x0087
            int r2 = r2 + -1
            goto L_0x0078
        L_0x0087:
            r7.mLevel = r2
            return
        L_0x008a:
            r7.mLevel = r6
            return
        L_0x008d:
            int r3 = r7.mRscp
            r4 = -120(0xffffffffffffff88, float:NaN)
            if (r3 < r4) goto L_0x00aa
            int r3 = r7.mRscp
            r4 = -24
            if (r3 <= r4) goto L_0x009a
            goto L_0x00aa
        L_0x009a:
            if (r2 <= 0) goto L_0x00a7
            int r3 = r7.mRscp
            int r4 = r2 + -1
            r4 = r1[r4]
            if (r3 >= r4) goto L_0x00a7
            int r2 = r2 + -1
            goto L_0x009a
        L_0x00a7:
            r7.mLevel = r2
            return
        L_0x00aa:
            r7.mLevel = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.CellSignalStrengthWcdma.updateLevel(android.os.PersistableBundle, android.telephony.ServiceState):void");
    }

    public int getDbm() {
        if (this.mRscp != Integer.MAX_VALUE) {
            return this.mRscp;
        }
        return this.mRssi;
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

    public int getRssi() {
        return this.mRssi;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getEcNo() {
        return this.mEcNo;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mRssi), Integer.valueOf(this.mBitErrorRate), Integer.valueOf(this.mRscp), Integer.valueOf(this.mEcNo), Integer.valueOf(this.mLevel)});
    }

    public boolean isValid() {
        return !equals(sInvalid);
    }

    public boolean equals(Object o) {
        if (!(o instanceof CellSignalStrengthWcdma)) {
            return false;
        }
        CellSignalStrengthWcdma s = (CellSignalStrengthWcdma) o;
        if (this.mRssi == s.mRssi && this.mBitErrorRate == s.mBitErrorRate && this.mRscp == s.mRscp && this.mEcNo == s.mEcNo && this.mLevel == s.mLevel) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "CellSignalStrengthWcdma: ss=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " ecno=" + this.mEcNo + " level=" + this.mLevel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mBitErrorRate);
        dest.writeInt(this.mRscp);
        dest.writeInt(this.mEcNo);
        dest.writeInt(this.mLevel);
    }

    private CellSignalStrengthWcdma(Parcel in) {
        this.mRssi = in.readInt();
        this.mBitErrorRate = in.readInt();
        this.mRscp = in.readInt();
        this.mEcNo = in.readInt();
        this.mLevel = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    private static void log(String s) {
        Rlog.w(LOG_TAG, s);
    }

    private static void loge(String s) {
        Rlog.e(LOG_TAG, s);
    }
}
