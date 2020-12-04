package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.RILConstants;

public class RadioAccessFamily implements Parcelable {
    private static final int CDMA = 72;
    public static final Parcelable.Creator<RadioAccessFamily> CREATOR = new Parcelable.Creator<RadioAccessFamily>() {
        public RadioAccessFamily createFromParcel(Parcel in) {
            return new RadioAccessFamily(in.readInt(), in.readInt());
        }

        public RadioAccessFamily[] newArray(int size) {
            return new RadioAccessFamily[size];
        }
    };
    private static final int EVDO = 10288;
    private static final int GSM = 32771;
    private static final int HS = 17280;
    private static final int LTE = 266240;
    private static final int NR = 524288;
    public static final int RAF_1xRTT = 64;
    public static final int RAF_EDGE = 2;
    public static final int RAF_EHRPD = 8192;
    public static final int RAF_EVDO_0 = 16;
    public static final int RAF_EVDO_A = 32;
    public static final int RAF_EVDO_B = 2048;
    public static final int RAF_GPRS = 1;
    public static final int RAF_GSM = 32768;
    public static final int RAF_HSDPA = 128;
    public static final int RAF_HSPA = 512;
    public static final int RAF_HSPAP = 16384;
    public static final int RAF_HSUPA = 256;
    public static final int RAF_IS95A = 8;
    public static final int RAF_IS95B = 8;
    public static final int RAF_LTE = 4096;
    public static final int RAF_LTE_CA = 262144;
    public static final int RAF_NR = 524288;
    public static final int RAF_TD_SCDMA = 65536;
    public static final int RAF_UMTS = 4;
    public static final int RAF_UNKNOWN = 0;
    private static final int WCDMA = 17284;
    private int mPhoneId;
    private int mRadioAccessFamily;

    @UnsupportedAppUsage
    public RadioAccessFamily(int phoneId, int radioAccessFamily) {
        this.mPhoneId = phoneId;
        this.mRadioAccessFamily = radioAccessFamily;
    }

    @UnsupportedAppUsage
    public int getPhoneId() {
        return this.mPhoneId;
    }

    @UnsupportedAppUsage
    public int getRadioAccessFamily() {
        return this.mRadioAccessFamily;
    }

    public String toString() {
        return "{ mPhoneId = " + this.mPhoneId + ", mRadioAccessFamily = " + this.mRadioAccessFamily + "}";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeInt(this.mPhoneId);
        outParcel.writeInt(this.mRadioAccessFamily);
    }

    @UnsupportedAppUsage
    public static int getRafFromNetworkType(int type) {
        switch (type) {
            case 0:
                return 50055;
            case 1:
                return 32771;
            case 2:
                return WCDMA;
            case 3:
                return 50055;
            case 4:
                return 10360;
            case 5:
                return 72;
            case 6:
                return EVDO;
            case 7:
                return 60415;
            case 8:
                return 276600;
            case 9:
                return 316295;
            case 10:
                return 326655;
            case 11:
                return 266240;
            case 12:
                return 283524;
            case 13:
                return 65536;
            case 14:
                return 82820;
            case 15:
                return 331776;
            case 16:
                return 98307;
            case 17:
                return 364547;
            case 18:
                return 115591;
            case 19:
                return 349060;
            case 20:
                return 381831;
            case 21:
                return 125951;
            case 22:
                return 392191;
            case 23:
                return 524288;
            case 24:
                return 790528;
            case 25:
                return 800888;
            case 26:
                return 840583;
            case 27:
                return 850943;
            case 28:
                return 807812;
            case 29:
                return 856064;
            case 30:
                return 888835;
            case 31:
                return 873348;
            case 32:
                return 906119;
            case 33:
                return 916479;
            default:
                return 0;
        }
    }

    private static int getAdjustedRaf(int raf) {
        int raf2 = (raf & 32771) > 0 ? 32771 | raf : raf;
        int raf3 = (raf2 & WCDMA) > 0 ? raf2 | WCDMA : raf2;
        int raf4 = (raf3 & 72) > 0 ? raf3 | 72 : raf3;
        int raf5 = (raf4 & EVDO) > 0 ? raf4 | EVDO : raf4;
        int raf6 = (raf5 & 266240) > 0 ? 266240 | raf5 : raf5;
        return (raf6 & 524288) > 0 ? 524288 | raf6 : raf6;
    }

    public static int getHighestRafCapability(int raf) {
        if ((266240 & raf) > 0) {
            return 3;
        }
        if (((raf & WCDMA) | 27568) > 0) {
            return 2;
        }
        if ((32771 | (raf & 72)) > 0) {
            return 1;
        }
        return 0;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static int getNetworkTypeFromRaf(int raf) {
        switch (getAdjustedRaf(raf)) {
            case 72:
                return 5;
            case EVDO /*10288*/:
                return 6;
            case 10360:
                return 4;
            case WCDMA /*17284*/:
                return 2;
            case 32771:
                return 1;
            case 50055:
                return 0;
            case 60415:
                return 7;
            case 65536:
                return 13;
            case 82820:
                return 14;
            case 98307:
                return 16;
            case 115591:
                return 18;
            case 125951:
                return 21;
            case 266240:
                return 11;
            case 276600:
                return 8;
            case 283524:
                return 12;
            case 316295:
                return 9;
            case 326655:
                return 10;
            case 331776:
                return 15;
            case 349060:
                return 19;
            case 364547:
                return 17;
            case 381831:
                return 20;
            case 392191:
                return 22;
            case 524288:
                return 23;
            case 790528:
                return 24;
            case 800888:
                return 25;
            case 807812:
                return 28;
            case 840583:
                return 26;
            case 850943:
                return 27;
            case 856064:
                return 29;
            case 873348:
                return 31;
            case 888835:
                return 30;
            case 906119:
                return 32;
            case 916479:
                return 33;
            default:
                return RILConstants.PREFERRED_NETWORK_MODE;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int singleRafTypeFromString(java.lang.String r7) {
        /*
            int r0 = r7.hashCode()
            r1 = 16
            r2 = 4
            r3 = 2
            r4 = 1
            r5 = 0
            r6 = 8
            switch(r0) {
                case -2039427040: goto L_0x0107;
                case -908593671: goto L_0x00fd;
                case 2315: goto L_0x00f2;
                case 2500: goto L_0x00e7;
                case 70881: goto L_0x00dc;
                case 75709: goto L_0x00d1;
                case 2063797: goto L_0x00c6;
                case 2123197: goto L_0x00bc;
                case 2140412: goto L_0x00b1;
                case 2194666: goto L_0x00a6;
                case 2227260: goto L_0x009a;
                case 2608919: goto L_0x008f;
                case 47955627: goto L_0x0084;
                case 65949251: goto L_0x0078;
                case 69034058: goto L_0x006d;
                case 69045140: goto L_0x0061;
                case 69050395: goto L_0x0055;
                case 69946171: goto L_0x004a;
                case 69946172: goto L_0x003f;
                case 82410124: goto L_0x0033;
                case 2056938925: goto L_0x0028;
                case 2056938942: goto L_0x001d;
                case 2056938943: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0112
        L_0x0011:
            java.lang.String r0 = "EVDO_B"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 11
            goto L_0x0113
        L_0x001d:
            java.lang.String r0 = "EVDO_A"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 7
            goto L_0x0113
        L_0x0028:
            java.lang.String r0 = "EVDO_0"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 6
            goto L_0x0113
        L_0x0033:
            java.lang.String r0 = "WCDMA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 20
            goto L_0x0113
        L_0x003f:
            java.lang.String r0 = "IS95B"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r2
            goto L_0x0113
        L_0x004a:
            java.lang.String r0 = "IS95A"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 3
            goto L_0x0113
        L_0x0055:
            java.lang.String r0 = "HSUPA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 9
            goto L_0x0113
        L_0x0061:
            java.lang.String r0 = "HSPAP"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 14
            goto L_0x0113
        L_0x006d:
            java.lang.String r0 = "HSDPA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r6
            goto L_0x0113
        L_0x0078:
            java.lang.String r0 = "EHRPD"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 12
            goto L_0x0113
        L_0x0084:
            java.lang.String r0 = "1XRTT"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 5
            goto L_0x0113
        L_0x008f:
            java.lang.String r0 = "UMTS"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r3
            goto L_0x0113
        L_0x009a:
            java.lang.String r0 = "HSPA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 10
            goto L_0x0113
        L_0x00a6:
            java.lang.String r0 = "GPRS"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r5
            goto L_0x0113
        L_0x00b1:
            java.lang.String r0 = "EVDO"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 19
            goto L_0x0113
        L_0x00bc:
            java.lang.String r0 = "EDGE"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r4
            goto L_0x0113
        L_0x00c6:
            java.lang.String r0 = "CDMA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 18
            goto L_0x0113
        L_0x00d1:
            java.lang.String r0 = "LTE"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 13
            goto L_0x0113
        L_0x00dc:
            java.lang.String r0 = "GSM"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 15
            goto L_0x0113
        L_0x00e7:
            java.lang.String r0 = "NR"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 22
            goto L_0x0113
        L_0x00f2:
            java.lang.String r0 = "HS"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 17
            goto L_0x0113
        L_0x00fd:
            java.lang.String r0 = "TD_SCDMA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = r1
            goto L_0x0113
        L_0x0107:
            java.lang.String r0 = "LTE_CA"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0112
            r0 = 21
            goto L_0x0113
        L_0x0112:
            r0 = -1
        L_0x0113:
            switch(r0) {
                case 0: goto L_0x0150;
                case 1: goto L_0x014f;
                case 2: goto L_0x014e;
                case 3: goto L_0x014d;
                case 4: goto L_0x014c;
                case 5: goto L_0x0149;
                case 6: goto L_0x0148;
                case 7: goto L_0x0145;
                case 8: goto L_0x0142;
                case 9: goto L_0x013f;
                case 10: goto L_0x013c;
                case 11: goto L_0x0139;
                case 12: goto L_0x0136;
                case 13: goto L_0x0133;
                case 14: goto L_0x0130;
                case 15: goto L_0x012c;
                case 16: goto L_0x0129;
                case 17: goto L_0x0126;
                case 18: goto L_0x0123;
                case 19: goto L_0x0120;
                case 20: goto L_0x011d;
                case 21: goto L_0x011a;
                case 22: goto L_0x0117;
                default: goto L_0x0116;
            }
        L_0x0116:
            return r5
        L_0x0117:
            r0 = 524288(0x80000, float:7.34684E-40)
            return r0
        L_0x011a:
            r0 = 262144(0x40000, float:3.67342E-40)
            return r0
        L_0x011d:
            r0 = 17284(0x4384, float:2.422E-41)
            return r0
        L_0x0120:
            r0 = 10288(0x2830, float:1.4417E-41)
            return r0
        L_0x0123:
            r0 = 72
            return r0
        L_0x0126:
            r0 = 17280(0x4380, float:2.4214E-41)
            return r0
        L_0x0129:
            r0 = 65536(0x10000, float:9.18355E-41)
            return r0
        L_0x012c:
            r0 = 32768(0x8000, float:4.5918E-41)
            return r0
        L_0x0130:
            r0 = 16384(0x4000, float:2.2959E-41)
            return r0
        L_0x0133:
            r0 = 4096(0x1000, float:5.74E-42)
            return r0
        L_0x0136:
            r0 = 8192(0x2000, float:1.14794E-41)
            return r0
        L_0x0139:
            r0 = 2048(0x800, float:2.87E-42)
            return r0
        L_0x013c:
            r0 = 512(0x200, float:7.175E-43)
            return r0
        L_0x013f:
            r0 = 256(0x100, float:3.59E-43)
            return r0
        L_0x0142:
            r0 = 128(0x80, float:1.794E-43)
            return r0
        L_0x0145:
            r0 = 32
            return r0
        L_0x0148:
            return r1
        L_0x0149:
            r0 = 64
            return r0
        L_0x014c:
            return r6
        L_0x014d:
            return r6
        L_0x014e:
            return r2
        L_0x014f:
            return r3
        L_0x0150:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.RadioAccessFamily.singleRafTypeFromString(java.lang.String):int");
    }

    public static int rafTypeFromString(String rafList) {
        int result = 0;
        for (String raf : rafList.toUpperCase().split("\\|")) {
            int rafType = singleRafTypeFromString(raf.trim());
            if (rafType == 0) {
                return rafType;
            }
            result |= rafType;
        }
        return result;
    }
}
