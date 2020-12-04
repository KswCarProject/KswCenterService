package android.telephony.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Telephony;
import android.telephony.ServiceState;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.telephony.PhoneConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApnSetting implements Parcelable {
    private static final Map<Integer, String> APN_TYPE_INT_MAP = new ArrayMap();
    private static final Map<String, Integer> APN_TYPE_STRING_MAP = new ArrayMap();
    public static final int AUTH_TYPE_CHAP = 2;
    public static final int AUTH_TYPE_NONE = 0;
    public static final int AUTH_TYPE_PAP = 1;
    public static final int AUTH_TYPE_PAP_OR_CHAP = 3;
    public static final Parcelable.Creator<ApnSetting> CREATOR = new Parcelable.Creator<ApnSetting>() {
        public ApnSetting createFromParcel(Parcel in) {
            return ApnSetting.readFromParcel(in);
        }

        public ApnSetting[] newArray(int size) {
            return new ApnSetting[size];
        }
    };
    private static final String LOG_TAG = "ApnSetting";
    public static final int MVNO_TYPE_GID = 2;
    public static final int MVNO_TYPE_ICCID = 3;
    public static final int MVNO_TYPE_IMSI = 1;
    private static final Map<Integer, String> MVNO_TYPE_INT_MAP = new ArrayMap();
    public static final int MVNO_TYPE_SPN = 0;
    private static final Map<String, Integer> MVNO_TYPE_STRING_MAP = new ArrayMap();
    private static final Map<Integer, String> PROTOCOL_INT_MAP = new ArrayMap();
    public static final int PROTOCOL_IP = 0;
    public static final int PROTOCOL_IPV4V6 = 2;
    public static final int PROTOCOL_IPV6 = 1;
    public static final int PROTOCOL_NON_IP = 4;
    public static final int PROTOCOL_PPP = 3;
    private static final Map<String, Integer> PROTOCOL_STRING_MAP = new ArrayMap();
    public static final int PROTOCOL_UNSTRUCTURED = 5;
    public static final int TYPE_ALL = 255;
    public static final int TYPE_CBS = 128;
    public static final int TYPE_DEFAULT = 17;
    public static final int TYPE_DUN = 8;
    public static final int TYPE_EMERGENCY = 512;
    public static final int TYPE_FOTA = 32;
    public static final int TYPE_HIPRI = 16;
    public static final int TYPE_IA = 256;
    public static final int TYPE_IMS = 64;
    public static final int TYPE_MCX = 1024;
    public static final int TYPE_MMS = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SUPL = 4;
    public static final int UNSET_MTU = 0;
    private static final int UNSPECIFIED_INT = -1;
    private static final String UNSPECIFIED_STRING = "";
    private static final String V2_FORMAT_REGEX = "^\\[ApnSettingV2\\]\\s*";
    private static final String V3_FORMAT_REGEX = "^\\[ApnSettingV3\\]\\s*";
    private static final String V4_FORMAT_REGEX = "^\\[ApnSettingV4\\]\\s*";
    private static final String V5_FORMAT_REGEX = "^\\[ApnSettingV5\\]\\s*";
    private static final String V6_FORMAT_REGEX = "^\\[ApnSettingV6\\]\\s*";
    private static final String V7_FORMAT_REGEX = "^\\[ApnSettingV7\\]\\s*";
    private static final boolean VDBG = false;
    private final String mApnName;
    private final int mApnSetId;
    private final int mApnTypeBitmask;
    private final int mAuthType;
    private final boolean mCarrierEnabled;
    private final int mCarrierId;
    private final String mEntryName;
    private final int mId;
    private final int mMaxConns;
    private final int mMaxConnsTime;
    private final String mMmsProxyAddress;
    private final int mMmsProxyPort;
    private final Uri mMmsc;
    private final int mMtu;
    private final String mMvnoMatchData;
    private final int mMvnoType;
    private final int mNetworkTypeBitmask;
    private final String mOperatorNumeric;
    private final String mPassword;
    private boolean mPermanentFailed;
    private final boolean mPersistent;
    private final int mProfileId;
    private final int mProtocol;
    private final String mProxyAddress;
    private final int mProxyPort;
    private final int mRoamingProtocol;
    private final int mSkip464Xlat;
    private final String mUser;
    private final int mWaitTime;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApnType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MvnoType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtocolType {
    }

    static {
        APN_TYPE_STRING_MAP.put("*", 255);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_DEFAULT, 17);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_MMS, 2);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_SUPL, 4);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_DUN, 8);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_HIPRI, 16);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_FOTA, 32);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_IMS, 64);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_CBS, 128);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_IA, 256);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_EMERGENCY, 512);
        APN_TYPE_STRING_MAP.put(PhoneConstants.APN_TYPE_MCX, 1024);
        APN_TYPE_INT_MAP.put(17, PhoneConstants.APN_TYPE_DEFAULT);
        APN_TYPE_INT_MAP.put(2, PhoneConstants.APN_TYPE_MMS);
        APN_TYPE_INT_MAP.put(4, PhoneConstants.APN_TYPE_SUPL);
        APN_TYPE_INT_MAP.put(8, PhoneConstants.APN_TYPE_DUN);
        APN_TYPE_INT_MAP.put(16, PhoneConstants.APN_TYPE_HIPRI);
        APN_TYPE_INT_MAP.put(32, PhoneConstants.APN_TYPE_FOTA);
        APN_TYPE_INT_MAP.put(64, PhoneConstants.APN_TYPE_IMS);
        APN_TYPE_INT_MAP.put(128, PhoneConstants.APN_TYPE_CBS);
        APN_TYPE_INT_MAP.put(256, PhoneConstants.APN_TYPE_IA);
        APN_TYPE_INT_MAP.put(512, PhoneConstants.APN_TYPE_EMERGENCY);
        APN_TYPE_INT_MAP.put(1024, PhoneConstants.APN_TYPE_MCX);
        PROTOCOL_STRING_MAP.put("IP", 0);
        PROTOCOL_STRING_MAP.put("IPV6", 1);
        PROTOCOL_STRING_MAP.put("IPV4V6", 2);
        PROTOCOL_STRING_MAP.put("PPP", 3);
        PROTOCOL_STRING_MAP.put("NON-IP", 4);
        PROTOCOL_STRING_MAP.put("UNSTRUCTURED", 5);
        PROTOCOL_INT_MAP.put(0, "IP");
        PROTOCOL_INT_MAP.put(1, "IPV6");
        PROTOCOL_INT_MAP.put(2, "IPV4V6");
        PROTOCOL_INT_MAP.put(3, "PPP");
        PROTOCOL_INT_MAP.put(4, "NON-IP");
        PROTOCOL_INT_MAP.put(5, "UNSTRUCTURED");
        MVNO_TYPE_STRING_MAP.put("spn", 0);
        MVNO_TYPE_STRING_MAP.put(SubscriptionManager.IMSI, 1);
        MVNO_TYPE_STRING_MAP.put("gid", 2);
        MVNO_TYPE_STRING_MAP.put("iccid", 3);
        MVNO_TYPE_INT_MAP.put(0, "spn");
        MVNO_TYPE_INT_MAP.put(1, SubscriptionManager.IMSI);
        MVNO_TYPE_INT_MAP.put(2, "gid");
        MVNO_TYPE_INT_MAP.put(3, "iccid");
    }

    public int getMtu() {
        return this.mMtu;
    }

    public int getProfileId() {
        return this.mProfileId;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public int getMaxConns() {
        return this.mMaxConns;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public int getMaxConnsTime() {
        return this.mMaxConnsTime;
    }

    public String getMvnoMatchData() {
        return this.mMvnoMatchData;
    }

    public int getApnSetId() {
        return this.mApnSetId;
    }

    public boolean getPermanentFailed() {
        return this.mPermanentFailed;
    }

    public void setPermanentFailed(boolean permanentFailed) {
        this.mPermanentFailed = permanentFailed;
    }

    public String getEntryName() {
        return this.mEntryName;
    }

    public String getApnName() {
        return this.mApnName;
    }

    @Deprecated
    public InetAddress getProxyAddress() {
        return inetAddressFromString(this.mProxyAddress);
    }

    public String getProxyAddressAsString() {
        return this.mProxyAddress;
    }

    public int getProxyPort() {
        return this.mProxyPort;
    }

    public Uri getMmsc() {
        return this.mMmsc;
    }

    @Deprecated
    public InetAddress getMmsProxyAddress() {
        return inetAddressFromString(this.mMmsProxyAddress);
    }

    public String getMmsProxyAddressAsString() {
        return this.mMmsProxyAddress;
    }

    public int getMmsProxyPort() {
        return this.mMmsProxyPort;
    }

    public String getUser() {
        return this.mUser;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getAuthType() {
        return this.mAuthType;
    }

    public int getApnTypeBitmask() {
        return this.mApnTypeBitmask;
    }

    public int getId() {
        return this.mId;
    }

    public String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getRoamingProtocol() {
        return this.mRoamingProtocol;
    }

    public boolean isEnabled() {
        return this.mCarrierEnabled;
    }

    public int getNetworkTypeBitmask() {
        return this.mNetworkTypeBitmask;
    }

    public int getMvnoType() {
        return this.mMvnoType;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getSkip464Xlat() {
        return this.mSkip464Xlat;
    }

    private ApnSetting(Builder builder) {
        this.mPermanentFailed = false;
        this.mEntryName = builder.mEntryName;
        this.mApnName = builder.mApnName;
        this.mProxyAddress = builder.mProxyAddress;
        this.mProxyPort = builder.mProxyPort;
        this.mMmsc = builder.mMmsc;
        this.mMmsProxyAddress = builder.mMmsProxyAddress;
        this.mMmsProxyPort = builder.mMmsProxyPort;
        this.mUser = builder.mUser;
        this.mPassword = builder.mPassword;
        this.mAuthType = builder.mAuthType;
        this.mApnTypeBitmask = builder.mApnTypeBitmask;
        this.mId = builder.mId;
        this.mOperatorNumeric = builder.mOperatorNumeric;
        this.mProtocol = builder.mProtocol;
        this.mRoamingProtocol = builder.mRoamingProtocol;
        this.mMtu = builder.mMtu;
        this.mCarrierEnabled = builder.mCarrierEnabled;
        this.mNetworkTypeBitmask = builder.mNetworkTypeBitmask;
        this.mProfileId = builder.mProfileId;
        this.mPersistent = builder.mModemCognitive;
        this.mMaxConns = builder.mMaxConns;
        this.mWaitTime = builder.mWaitTime;
        this.mMaxConnsTime = builder.mMaxConnsTime;
        this.mMvnoType = builder.mMvnoType;
        this.mMvnoMatchData = builder.mMvnoMatchData;
        this.mApnSetId = builder.mApnSetId;
        this.mCarrierId = builder.mCarrierId;
        this.mSkip464Xlat = builder.mSkip464Xlat;
    }

    public static ApnSetting makeApnSetting(int id, String operatorNumeric, String entryName, String apnName, String proxyAddress, int proxyPort, Uri mmsc, String mmsProxyAddress, int mmsProxyPort, String user, String password, int authType, int mApnTypeBitmask2, int protocol, int roamingProtocol, boolean carrierEnabled, int networkTypeBitmask, int profileId, boolean modemCognitive, int maxConns, int waitTime, int maxConnsTime, int mtu, int mvnoType, String mvnoMatchData, int apnSetId, int carrierId, int skip464xlat) {
        return new Builder().setId(id).setOperatorNumeric(operatorNumeric).setEntryName(entryName).setApnName(apnName).setProxyAddress(proxyAddress).setProxyPort(proxyPort).setMmsc(mmsc).setMmsProxyAddress(mmsProxyAddress).setMmsProxyPort(mmsProxyPort).setUser(user).setPassword(password).setAuthType(authType).setApnTypeBitmask(mApnTypeBitmask2).setProtocol(protocol).setRoamingProtocol(roamingProtocol).setCarrierEnabled(carrierEnabled).setNetworkTypeBitmask(networkTypeBitmask).setProfileId(profileId).setModemCognitive(modemCognitive).setMaxConns(maxConns).setWaitTime(waitTime).setMaxConnsTime(maxConnsTime).setMtu(mtu).setMvnoType(mvnoType).setMvnoMatchData(mvnoMatchData).setApnSetId(apnSetId).setCarrierId(carrierId).setSkip464Xlat(skip464xlat).buildWithoutCheck();
    }

    public static ApnSetting makeApnSetting(int id, String operatorNumeric, String entryName, String apnName, String proxyAddress, int proxyPort, Uri mmsc, String mmsProxyAddress, int mmsProxyPort, String user, String password, int authType, int mApnTypeBitmask2, int protocol, int roamingProtocol, boolean carrierEnabled, int networkTypeBitmask, int profileId, boolean modemCognitive, int maxConns, int waitTime, int maxConnsTime, int mtu, int mvnoType, String mvnoMatchData) {
        return makeApnSetting(id, operatorNumeric, entryName, apnName, proxyAddress, proxyPort, mmsc, mmsProxyAddress, mmsProxyPort, user, password, authType, mApnTypeBitmask2, protocol, roamingProtocol, carrierEnabled, networkTypeBitmask, profileId, modemCognitive, maxConns, waitTime, maxConnsTime, mtu, mvnoType, mvnoMatchData, 0, -1, -1);
    }

    public static ApnSetting makeApnSetting(Cursor cursor) {
        Cursor cursor2 = cursor;
        int apnTypesBitmask = getApnTypesBitmaskFromString(cursor2.getString(cursor2.getColumnIndexOrThrow("type")));
        int networkTypeBitmask = cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.NETWORK_TYPE_BITMASK));
        if (networkTypeBitmask == 0) {
            networkTypeBitmask = ServiceState.convertBearerBitmaskToNetworkTypeBitmask(cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.BEARER_BITMASK)));
        }
        int networkTypeBitmask2 = networkTypeBitmask;
        return makeApnSetting(cursor2.getInt(cursor2.getColumnIndexOrThrow("_id")), cursor2.getString(cursor2.getColumnIndexOrThrow(Telephony.Carriers.NUMERIC)), cursor2.getString(cursor2.getColumnIndexOrThrow("name")), cursor2.getString(cursor2.getColumnIndexOrThrow("apn")), cursor2.getString(cursor2.getColumnIndexOrThrow("proxy")), portFromString(cursor2.getString(cursor2.getColumnIndexOrThrow("port"))), UriFromString(cursor2.getString(cursor2.getColumnIndexOrThrow(Telephony.Carriers.MMSC))), cursor2.getString(cursor2.getColumnIndexOrThrow(Telephony.Carriers.MMSPROXY)), portFromString(cursor2.getString(cursor2.getColumnIndexOrThrow(Telephony.Carriers.MMSPORT))), cursor2.getString(cursor2.getColumnIndexOrThrow("user")), cursor2.getString(cursor2.getColumnIndexOrThrow("password")), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.AUTH_TYPE)), apnTypesBitmask, getProtocolIntFromString(cursor2.getString(cursor2.getColumnIndexOrThrow("protocol"))), getProtocolIntFromString(cursor2.getString(cursor2.getColumnIndexOrThrow(Telephony.Carriers.ROAMING_PROTOCOL))), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.CARRIER_ENABLED)) == 1, networkTypeBitmask2, cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.PROFILE_ID)), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.MODEM_PERSIST)) == 1, cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.MAX_CONNECTIONS)), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.WAIT_TIME_RETRY)), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.TIME_LIMIT_FOR_MAX_CONNECTIONS)), cursor2.getInt(cursor2.getColumnIndexOrThrow("mtu")), getMvnoTypeIntFromString(cursor2.getString(cursor2.getColumnIndexOrThrow("mvno_type"))), cursor2.getString(cursor2.getColumnIndexOrThrow("mvno_match_data")), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.APN_SET_ID)), cursor2.getInt(cursor2.getColumnIndexOrThrow("carrier_id")), cursor2.getInt(cursor2.getColumnIndexOrThrow(Telephony.Carriers.SKIP_464XLAT)));
    }

    public static ApnSetting makeApnSetting(ApnSetting apn) {
        ApnSetting apnSetting = apn;
        int i = apnSetting.mId;
        return makeApnSetting(i, apnSetting.mOperatorNumeric, apnSetting.mEntryName, apnSetting.mApnName, apnSetting.mProxyAddress, apnSetting.mProxyPort, apnSetting.mMmsc, apnSetting.mMmsProxyAddress, apnSetting.mMmsProxyPort, apnSetting.mUser, apnSetting.mPassword, apnSetting.mAuthType, apnSetting.mApnTypeBitmask, apnSetting.mProtocol, apnSetting.mRoamingProtocol, apnSetting.mCarrierEnabled, apnSetting.mNetworkTypeBitmask, apnSetting.mProfileId, apnSetting.mPersistent, apnSetting.mMaxConns, apnSetting.mWaitTime, apnSetting.mMaxConnsTime, apnSetting.mMtu, apnSetting.mMvnoType, apnSetting.mMvnoMatchData, apnSetting.mApnSetId, apnSetting.mCarrierId, apnSetting.mSkip464Xlat);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0082 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.telephony.data.ApnSetting fromString(java.lang.String r49) {
        /*
            r0 = r49
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.String r2 = "^\\[ApnSettingV7\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            r3 = 1
            if (r2 == 0) goto L_0x001a
            r2 = 7
            java.lang.String r4 = "^\\[ApnSettingV7\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
        L_0x0018:
            r4 = r0
            goto L_0x0076
        L_0x001a:
            java.lang.String r2 = "^\\[ApnSettingV6\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            if (r2 == 0) goto L_0x002c
            r2 = 6
            java.lang.String r4 = "^\\[ApnSettingV6\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            goto L_0x0018
        L_0x002c:
            java.lang.String r2 = "^\\[ApnSettingV5\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            if (r2 == 0) goto L_0x003e
            r2 = 5
            java.lang.String r4 = "^\\[ApnSettingV5\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            goto L_0x0018
        L_0x003e:
            java.lang.String r2 = "^\\[ApnSettingV4\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            if (r2 == 0) goto L_0x0050
            r2 = 4
            java.lang.String r4 = "^\\[ApnSettingV4\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            goto L_0x0018
        L_0x0050:
            java.lang.String r2 = "^\\[ApnSettingV3\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            if (r2 == 0) goto L_0x0062
            r2 = 3
            java.lang.String r4 = "^\\[ApnSettingV3\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            goto L_0x0018
        L_0x0062:
            java.lang.String r2 = "^\\[ApnSettingV2\\]\\s*.*"
            boolean r2 = r0.matches(r2)
            if (r2 == 0) goto L_0x0074
            r2 = 2
            java.lang.String r4 = "^\\[ApnSettingV2\\]\\s*"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replaceFirst(r4, r5)
            goto L_0x0018
        L_0x0074:
            r4 = r0
            r2 = r3
        L_0x0076:
            java.lang.String r0 = "\\s*,\\s*"
            r5 = -1
            java.lang.String[] r5 = r4.split(r0, r5)
            int r0 = r5.length
            r6 = 14
            if (r0 >= r6) goto L_0x0083
            return r1
        L_0x0083:
            r0 = 12
            r7 = 0
            r0 = r5[r0]     // Catch:{ NumberFormatException -> 0x0090 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0090 }
            r19 = r0
            goto L_0x0093
        L_0x0090:
            r0 = move-exception
            r19 = r7
        L_0x0093:
            r0 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            java.lang.String r15 = ""
            java.lang.String r16 = ""
            r17 = 0
            r18 = -1
            r20 = -1
            r6 = 13
            if (r2 != r3) goto L_0x00e9
            int r1 = r5.length
            int r1 = r1 - r6
            java.lang.String[] r1 = new java.lang.String[r1]
            int r3 = r5.length
            int r3 = r3 - r6
            java.lang.System.arraycopy(r5, r6, r1, r7, r3)
            java.util.Map<java.lang.Integer, java.lang.String> r3 = PROTOCOL_INT_MAP
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)
            java.lang.Object r3 = r3.get(r6)
            java.lang.String r3 = (java.lang.String) r3
            java.util.Map<java.lang.Integer, java.lang.String> r6 = PROTOCOL_INT_MAP
            r37 = r0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r7)
            java.lang.Object r0 = r6.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r6 = 1
            r7 = r9
            r36 = r10
            r38 = r11
            r39 = r12
            r40 = r13
            r41 = r14
            r42 = r15
            r43 = r16
            r44 = r17
            r45 = r18
            r46 = r20
            r48 = r6
            r6 = r3
            r3 = r48
            goto L_0x01ba
        L_0x00e9:
            r37 = r0
            int r0 = r5.length
            r3 = 18
            if (r0 >= r3) goto L_0x00f1
            return r1
        L_0x00f1:
            r0 = r5[r6]
            java.lang.String r1 = "\\s*\\|\\s*"
            java.lang.String[] r1 = r0.split(r1)
            r6 = 14
            r6 = r5[r6]
            r0 = 15
            r21 = r5[r0]
            r0 = 16
            r0 = r5[r0]
            boolean r22 = java.lang.Boolean.parseBoolean(r0)
            r0 = 17
            r0 = r5[r0]
            int r23 = android.telephony.ServiceState.getBitmaskFromString(r0)
            int r0 = r5.length
            r7 = 22
            if (r0 <= r7) goto L_0x0140
            r0 = 19
            r0 = r5[r0]
            boolean r10 = java.lang.Boolean.parseBoolean(r0)
            r0 = r5[r3]     // Catch:{ NumberFormatException -> 0x013f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x013f }
            r9 = r0
            r0 = 20
            r0 = r5[r0]     // Catch:{ NumberFormatException -> 0x013f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x013f }
            r11 = r0
            r0 = 21
            r0 = r5[r0]     // Catch:{ NumberFormatException -> 0x013f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x013f }
            r12 = r0
            r0 = r5[r7]     // Catch:{ NumberFormatException -> 0x013f }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x013f }
            r13 = r0
            goto L_0x0140
        L_0x013f:
            r0 = move-exception
        L_0x0140:
            int r0 = r5.length
            r3 = 23
            if (r0 <= r3) goto L_0x014e
            r0 = r5[r3]     // Catch:{ NumberFormatException -> 0x014d }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x014d }
            r14 = r0
            goto L_0x014e
        L_0x014d:
            r0 = move-exception
        L_0x014e:
            int r0 = r5.length
            r3 = 25
            if (r0 <= r3) goto L_0x015a
            r0 = 24
            r0 = r5[r0]
            r16 = r5[r3]
            r15 = r0
        L_0x015a:
            int r0 = r5.length
            r3 = 26
            if (r0 <= r3) goto L_0x0166
            r0 = r5[r3]
            int r0 = android.telephony.ServiceState.getBitmaskFromString(r0)
            r8 = r0
        L_0x0166:
            int r0 = r5.length
            r3 = 27
            if (r0 <= r3) goto L_0x0171
            r0 = r5[r3]
            int r17 = java.lang.Integer.parseInt(r0)
        L_0x0171:
            int r0 = r5.length
            r3 = 28
            if (r0 <= r3) goto L_0x017c
            r0 = r5[r3]
            int r18 = java.lang.Integer.parseInt(r0)
        L_0x017c:
            int r0 = r5.length
            r3 = 29
            if (r0 <= r3) goto L_0x019f
            r0 = r5[r3]     // Catch:{ NumberFormatException -> 0x019e }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x019e }
            r46 = r0
            r7 = r9
            r36 = r10
            r38 = r11
            r39 = r12
            r40 = r13
            r41 = r14
            r42 = r15
            r43 = r16
            r44 = r17
            r45 = r18
            goto L_0x01b4
        L_0x019e:
            r0 = move-exception
        L_0x019f:
            r7 = r9
            r36 = r10
            r38 = r11
            r39 = r12
            r40 = r13
            r41 = r14
            r42 = r15
            r43 = r16
            r44 = r17
            r45 = r18
            r46 = r20
        L_0x01b4:
            r0 = r21
            r3 = r22
            r37 = r23
        L_0x01ba:
            if (r8 != 0) goto L_0x01c1
            int r8 = android.telephony.ServiceState.convertBearerBitmaskToNetworkTypeBitmask(r37)
        L_0x01c1:
            r47 = r8
            r8 = -1
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r10 = 10
            r10 = r5[r10]
            r9.append(r10)
            r10 = 11
            r10 = r5[r10]
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r10 = 0
            r10 = r5[r10]
            r11 = 1
            r11 = r5[r11]
            r12 = 2
            r12 = r5[r12]
            r13 = 3
            r13 = r5[r13]
            int r13 = portFromString(r13)
            r14 = 7
            r14 = r5[r14]
            android.net.Uri r14 = UriFromString(r14)
            r15 = 8
            r15 = r5[r15]
            r16 = 9
            r16 = r5[r16]
            int r16 = portFromString(r16)
            r17 = 4
            r17 = r5[r17]
            r18 = 5
            r18 = r5[r18]
            java.lang.String r8 = ","
            java.lang.String r8 = android.text.TextUtils.join((java.lang.CharSequence) r8, (java.lang.Object[]) r1)
            int r20 = getApnTypesBitmaskFromString(r8)
            int r21 = getProtocolIntFromString(r6)
            int r22 = getProtocolIntFromString(r0)
            int r31 = getMvnoTypeIntFromString(r42)
            r23 = r3
            r24 = r47
            r25 = r7
            r26 = r36
            r27 = r38
            r28 = r39
            r29 = r40
            r30 = r41
            r32 = r43
            r33 = r44
            r34 = r45
            r35 = r46
            r8 = -1
            android.telephony.data.ApnSetting r8 = makeApnSetting(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.data.ApnSetting.fromString(java.lang.String):android.telephony.data.ApnSetting");
    }

    public static List<ApnSetting> arrayFromString(String data) {
        List<ApnSetting> retVal = new ArrayList<>();
        if (TextUtils.isEmpty(data)) {
            return retVal;
        }
        for (String apnString : data.split("\\s*;\\s*")) {
            ApnSetting apn = fromString(apnString);
            if (apn != null) {
                retVal.add(apn);
            }
        }
        return retVal;
    }

    public String toString() {
        return "[ApnSettingV7] " + this.mEntryName + ", " + this.mId + ", " + this.mOperatorNumeric + ", " + this.mApnName + ", " + this.mProxyAddress + ", " + UriToString(this.mMmsc) + ", " + this.mMmsProxyAddress + ", " + portToString(this.mMmsProxyPort) + ", " + portToString(this.mProxyPort) + ", " + this.mAuthType + ", " + TextUtils.join((CharSequence) " | ", (Object[]) getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(SmsManager.REGEX_PREFIX_DELIMITER)) + ", " + PROTOCOL_INT_MAP.get(Integer.valueOf(this.mProtocol)) + ", " + PROTOCOL_INT_MAP.get(Integer.valueOf(this.mRoamingProtocol)) + ", " + this.mCarrierEnabled + ", " + this.mProfileId + ", " + this.mPersistent + ", " + this.mMaxConns + ", " + this.mWaitTime + ", " + this.mMaxConnsTime + ", " + this.mMtu + ", " + MVNO_TYPE_INT_MAP.get(Integer.valueOf(this.mMvnoType)) + ", " + this.mMvnoMatchData + ", " + this.mPermanentFailed + ", " + this.mNetworkTypeBitmask + ", " + this.mApnSetId + ", " + this.mCarrierId + ", " + this.mSkip464Xlat;
    }

    public boolean hasMvnoParams() {
        return !TextUtils.isEmpty(getMvnoTypeStringFromInt(this.mMvnoType)) && !TextUtils.isEmpty(this.mMvnoMatchData);
    }

    private boolean hasApnType(int type) {
        return (this.mApnTypeBitmask & type) == type;
    }

    public boolean canHandleType(int type) {
        if (this.mCarrierEnabled && hasApnType(type)) {
            return true;
        }
        return false;
    }

    private boolean typeSameAny(ApnSetting first, ApnSetting second) {
        if ((first.mApnTypeBitmask & second.mApnTypeBitmask) != 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ApnSetting)) {
            return false;
        }
        ApnSetting other = (ApnSetting) o;
        if (!this.mEntryName.equals(other.mEntryName) || !Objects.equals(Integer.valueOf(this.mId), Integer.valueOf(other.mId)) || !Objects.equals(this.mOperatorNumeric, other.mOperatorNumeric) || !Objects.equals(this.mApnName, other.mApnName) || !Objects.equals(this.mProxyAddress, other.mProxyAddress) || !Objects.equals(this.mMmsc, other.mMmsc) || !Objects.equals(this.mMmsProxyAddress, other.mMmsProxyAddress) || !Objects.equals(Integer.valueOf(this.mMmsProxyPort), Integer.valueOf(other.mMmsProxyPort)) || !Objects.equals(Integer.valueOf(this.mProxyPort), Integer.valueOf(other.mProxyPort)) || !Objects.equals(this.mUser, other.mUser) || !Objects.equals(this.mPassword, other.mPassword) || !Objects.equals(Integer.valueOf(this.mAuthType), Integer.valueOf(other.mAuthType)) || !Objects.equals(Integer.valueOf(this.mApnTypeBitmask), Integer.valueOf(other.mApnTypeBitmask)) || !Objects.equals(Integer.valueOf(this.mProtocol), Integer.valueOf(other.mProtocol)) || !Objects.equals(Integer.valueOf(this.mRoamingProtocol), Integer.valueOf(other.mRoamingProtocol)) || !Objects.equals(Boolean.valueOf(this.mCarrierEnabled), Boolean.valueOf(other.mCarrierEnabled)) || !Objects.equals(Integer.valueOf(this.mProfileId), Integer.valueOf(other.mProfileId)) || !Objects.equals(Boolean.valueOf(this.mPersistent), Boolean.valueOf(other.mPersistent)) || !Objects.equals(Integer.valueOf(this.mMaxConns), Integer.valueOf(other.mMaxConns)) || !Objects.equals(Integer.valueOf(this.mWaitTime), Integer.valueOf(other.mWaitTime)) || !Objects.equals(Integer.valueOf(this.mMaxConnsTime), Integer.valueOf(other.mMaxConnsTime)) || !Objects.equals(Integer.valueOf(this.mMtu), Integer.valueOf(other.mMtu)) || !Objects.equals(Integer.valueOf(this.mMvnoType), Integer.valueOf(other.mMvnoType)) || !Objects.equals(this.mMvnoMatchData, other.mMvnoMatchData) || !Objects.equals(Integer.valueOf(this.mNetworkTypeBitmask), Integer.valueOf(other.mNetworkTypeBitmask)) || !Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(other.mApnSetId)) || !Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(other.mCarrierId)) || !Objects.equals(Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(other.mSkip464Xlat))) {
            return false;
        }
        return true;
    }

    public boolean equals(Object o, boolean isDataRoaming) {
        if (!(o instanceof ApnSetting)) {
            return false;
        }
        ApnSetting other = (ApnSetting) o;
        if (!this.mEntryName.equals(other.mEntryName) || !Objects.equals(this.mOperatorNumeric, other.mOperatorNumeric) || !Objects.equals(this.mApnName, other.mApnName) || !Objects.equals(this.mProxyAddress, other.mProxyAddress) || !Objects.equals(this.mMmsc, other.mMmsc) || !Objects.equals(this.mMmsProxyAddress, other.mMmsProxyAddress) || !Objects.equals(Integer.valueOf(this.mMmsProxyPort), Integer.valueOf(other.mMmsProxyPort)) || !Objects.equals(Integer.valueOf(this.mProxyPort), Integer.valueOf(other.mProxyPort)) || !Objects.equals(this.mUser, other.mUser) || !Objects.equals(this.mPassword, other.mPassword) || !Objects.equals(Integer.valueOf(this.mAuthType), Integer.valueOf(other.mAuthType)) || !Objects.equals(Integer.valueOf(this.mApnTypeBitmask), Integer.valueOf(other.mApnTypeBitmask))) {
            return false;
        }
        if (!isDataRoaming && !Objects.equals(Integer.valueOf(this.mProtocol), Integer.valueOf(other.mProtocol))) {
            return false;
        }
        if ((!isDataRoaming || Objects.equals(Integer.valueOf(this.mRoamingProtocol), Integer.valueOf(other.mRoamingProtocol))) && Objects.equals(Boolean.valueOf(this.mCarrierEnabled), Boolean.valueOf(other.mCarrierEnabled)) && Objects.equals(Integer.valueOf(this.mProfileId), Integer.valueOf(other.mProfileId)) && Objects.equals(Boolean.valueOf(this.mPersistent), Boolean.valueOf(other.mPersistent)) && Objects.equals(Integer.valueOf(this.mMaxConns), Integer.valueOf(other.mMaxConns)) && Objects.equals(Integer.valueOf(this.mWaitTime), Integer.valueOf(other.mWaitTime)) && Objects.equals(Integer.valueOf(this.mMaxConnsTime), Integer.valueOf(other.mMaxConnsTime)) && Objects.equals(Integer.valueOf(this.mMtu), Integer.valueOf(other.mMtu)) && Objects.equals(Integer.valueOf(this.mMvnoType), Integer.valueOf(other.mMvnoType)) && Objects.equals(this.mMvnoMatchData, other.mMvnoMatchData) && Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(other.mApnSetId)) && Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(other.mCarrierId)) && Objects.equals(Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(other.mSkip464Xlat))) {
            return true;
        }
        return false;
    }

    public boolean similar(ApnSetting other) {
        return !canHandleType(8) && !other.canHandleType(8) && Objects.equals(this.mApnName, other.mApnName) && !typeSameAny(this, other) && xorEqualsString(this.mProxyAddress, other.mProxyAddress) && xorEqualsInt(this.mProxyPort, other.mProxyPort) && xorEqualsInt(this.mProtocol, other.mProtocol) && xorEqualsInt(this.mRoamingProtocol, other.mRoamingProtocol) && Objects.equals(Boolean.valueOf(this.mCarrierEnabled), Boolean.valueOf(other.mCarrierEnabled)) && Objects.equals(Integer.valueOf(this.mProfileId), Integer.valueOf(other.mProfileId)) && Objects.equals(Integer.valueOf(this.mMvnoType), Integer.valueOf(other.mMvnoType)) && Objects.equals(this.mMvnoMatchData, other.mMvnoMatchData) && xorEquals(this.mMmsc, other.mMmsc) && xorEqualsString(this.mMmsProxyAddress, other.mMmsProxyAddress) && xorEqualsInt(this.mMmsProxyPort, other.mMmsProxyPort) && Objects.equals(Integer.valueOf(this.mNetworkTypeBitmask), Integer.valueOf(other.mNetworkTypeBitmask)) && Objects.equals(Integer.valueOf(this.mApnSetId), Integer.valueOf(other.mApnSetId)) && Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(other.mCarrierId)) && Objects.equals(Integer.valueOf(this.mSkip464Xlat), Integer.valueOf(other.mSkip464Xlat));
    }

    private boolean xorEquals(Object first, Object second) {
        return first == null || second == null || first.equals(second);
    }

    private boolean xorEqualsString(String first, String second) {
        return TextUtils.isEmpty(first) || TextUtils.isEmpty(second) || first.equals(second);
    }

    private boolean xorEqualsInt(int first, int second) {
        return first == -1 || second == -1 || Objects.equals(Integer.valueOf(first), Integer.valueOf(second));
    }

    private String nullToEmpty(String stringValue) {
        return stringValue == null ? "" : stringValue;
    }

    public ContentValues toContentValues() {
        ContentValues apnValue = new ContentValues();
        apnValue.put(Telephony.Carriers.NUMERIC, nullToEmpty(this.mOperatorNumeric));
        apnValue.put("name", nullToEmpty(this.mEntryName));
        apnValue.put("apn", nullToEmpty(this.mApnName));
        apnValue.put("proxy", nullToEmpty(this.mProxyAddress));
        apnValue.put("port", nullToEmpty(portToString(this.mProxyPort)));
        apnValue.put(Telephony.Carriers.MMSC, nullToEmpty(UriToString(this.mMmsc)));
        apnValue.put(Telephony.Carriers.MMSPORT, nullToEmpty(portToString(this.mMmsProxyPort)));
        apnValue.put(Telephony.Carriers.MMSPROXY, nullToEmpty(this.mMmsProxyAddress));
        apnValue.put("user", nullToEmpty(this.mUser));
        apnValue.put("password", nullToEmpty(this.mPassword));
        apnValue.put(Telephony.Carriers.AUTH_TYPE, Integer.valueOf(this.mAuthType));
        apnValue.put("type", nullToEmpty(getApnTypesStringFromBitmask(this.mApnTypeBitmask)));
        apnValue.put("protocol", getProtocolStringFromInt(this.mProtocol));
        apnValue.put(Telephony.Carriers.ROAMING_PROTOCOL, getProtocolStringFromInt(this.mRoamingProtocol));
        apnValue.put(Telephony.Carriers.CARRIER_ENABLED, Boolean.valueOf(this.mCarrierEnabled));
        apnValue.put("mvno_type", getMvnoTypeStringFromInt(this.mMvnoType));
        apnValue.put(Telephony.Carriers.NETWORK_TYPE_BITMASK, Integer.valueOf(this.mNetworkTypeBitmask));
        apnValue.put("carrier_id", Integer.valueOf(this.mCarrierId));
        apnValue.put(Telephony.Carriers.SKIP_464XLAT, Integer.valueOf(this.mSkip464Xlat));
        return apnValue;
    }

    public List<Integer> getApnTypes() {
        List<Integer> types = new ArrayList<>();
        for (Integer type : APN_TYPE_INT_MAP.keySet()) {
            if ((this.mApnTypeBitmask & type.intValue()) == type.intValue()) {
                types.add(type);
            }
        }
        return types;
    }

    public static String getApnTypesStringFromBitmask(int apnTypeBitmask) {
        List<String> types = new ArrayList<>();
        for (Integer type : APN_TYPE_INT_MAP.keySet()) {
            if ((type.intValue() & apnTypeBitmask) == type.intValue()) {
                types.add(APN_TYPE_INT_MAP.get(type));
            }
        }
        return TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) types);
    }

    public static String getApnTypeString(int apnType) {
        if (apnType == 255) {
            return "*";
        }
        String apnTypeString = APN_TYPE_INT_MAP.get(Integer.valueOf(apnType));
        return apnTypeString == null ? "Unknown" : apnTypeString;
    }

    public static int getApnTypesBitmaskFromString(String types) {
        if (TextUtils.isEmpty(types)) {
            return 255;
        }
        int result = 0;
        for (String str : types.split(SmsManager.REGEX_PREFIX_DELIMITER)) {
            Integer type = APN_TYPE_STRING_MAP.get(str.toLowerCase());
            if (type != null) {
                result |= type.intValue();
            }
        }
        return result;
    }

    public static int getMvnoTypeIntFromString(String mvnoType) {
        Integer mvnoTypeInt = MVNO_TYPE_STRING_MAP.get(TextUtils.isEmpty(mvnoType) ? mvnoType : mvnoType.toLowerCase());
        if (mvnoTypeInt == null) {
            return -1;
        }
        return mvnoTypeInt.intValue();
    }

    public static String getMvnoTypeStringFromInt(int mvnoType) {
        String mvnoTypeString = MVNO_TYPE_INT_MAP.get(Integer.valueOf(mvnoType));
        return mvnoTypeString == null ? "" : mvnoTypeString;
    }

    public static int getProtocolIntFromString(String protocol) {
        Integer protocolInt = PROTOCOL_STRING_MAP.get(protocol);
        if (protocolInt == null) {
            return -1;
        }
        return protocolInt.intValue();
    }

    public static String getProtocolStringFromInt(int protocol) {
        String protocolString = PROTOCOL_INT_MAP.get(Integer.valueOf(protocol));
        return protocolString == null ? "" : protocolString;
    }

    private static Uri UriFromString(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return null;
        }
        return Uri.parse(uri);
    }

    private static String UriToString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static InetAddress inetAddressFromString(String inetAddress) {
        if (TextUtils.isEmpty(inetAddress)) {
            return null;
        }
        try {
            return InetAddress.getByName(inetAddress);
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, "Can't parse InetAddress from string: unknown host.");
            return null;
        }
    }

    public static String inetAddressToString(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        String inetAddressString = inetAddress.toString();
        if (TextUtils.isEmpty(inetAddressString)) {
            return null;
        }
        String hostName = inetAddressString.substring(0, inetAddressString.indexOf("/"));
        String address = inetAddressString.substring(inetAddressString.indexOf("/") + 1);
        if (!TextUtils.isEmpty(hostName) || !TextUtils.isEmpty(address)) {
            return TextUtils.isEmpty(hostName) ? address : hostName;
        }
        return null;
    }

    private static int portFromString(String strPort) {
        if (TextUtils.isEmpty(strPort)) {
            return -1;
        }
        try {
            return Integer.parseInt(strPort);
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Can't parse port from String");
            return -1;
        }
    }

    private static String portToString(int port) {
        if (port == -1) {
            return null;
        }
        return Integer.toString(port);
    }

    public boolean canSupportNetworkType(int networkType) {
        if (networkType != 16 || (((long) this.mNetworkTypeBitmask) & 3) == 0) {
            return ServiceState.bitmaskHasTech(this.mNetworkTypeBitmask, networkType);
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mOperatorNumeric);
        dest.writeString(this.mEntryName);
        dest.writeString(this.mApnName);
        dest.writeString(this.mProxyAddress);
        dest.writeInt(this.mProxyPort);
        dest.writeValue(this.mMmsc);
        dest.writeString(this.mMmsProxyAddress);
        dest.writeInt(this.mMmsProxyPort);
        dest.writeString(this.mUser);
        dest.writeString(this.mPassword);
        dest.writeInt(this.mAuthType);
        dest.writeInt(this.mApnTypeBitmask);
        dest.writeInt(this.mProtocol);
        dest.writeInt(this.mRoamingProtocol);
        dest.writeBoolean(this.mCarrierEnabled);
        dest.writeInt(this.mMvnoType);
        dest.writeInt(this.mNetworkTypeBitmask);
        dest.writeInt(this.mApnSetId);
        dest.writeInt(this.mCarrierId);
        dest.writeInt(this.mSkip464Xlat);
    }

    /* access modifiers changed from: private */
    public static ApnSetting readFromParcel(Parcel in) {
        Parcel parcel = in;
        return makeApnSetting(in.readInt(), in.readString(), in.readString(), in.readString(), in.readString(), in.readInt(), (Uri) parcel.readValue(Uri.class.getClassLoader()), in.readString(), in.readInt(), in.readString(), in.readString(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readBoolean(), in.readInt(), 0, false, 0, 0, 0, 0, in.readInt(), (String) null, in.readInt(), in.readInt(), in.readInt());
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String mApnName;
        /* access modifiers changed from: private */
        public int mApnSetId;
        /* access modifiers changed from: private */
        public int mApnTypeBitmask;
        /* access modifiers changed from: private */
        public int mAuthType;
        /* access modifiers changed from: private */
        public boolean mCarrierEnabled;
        /* access modifiers changed from: private */
        public int mCarrierId = -1;
        /* access modifiers changed from: private */
        public String mEntryName;
        /* access modifiers changed from: private */
        public int mId;
        /* access modifiers changed from: private */
        public int mMaxConns;
        /* access modifiers changed from: private */
        public int mMaxConnsTime;
        /* access modifiers changed from: private */
        public String mMmsProxyAddress;
        /* access modifiers changed from: private */
        public int mMmsProxyPort = -1;
        /* access modifiers changed from: private */
        public Uri mMmsc;
        /* access modifiers changed from: private */
        public boolean mModemCognitive;
        /* access modifiers changed from: private */
        public int mMtu;
        /* access modifiers changed from: private */
        public String mMvnoMatchData;
        /* access modifiers changed from: private */
        public int mMvnoType = -1;
        /* access modifiers changed from: private */
        public int mNetworkTypeBitmask;
        /* access modifiers changed from: private */
        public String mOperatorNumeric;
        /* access modifiers changed from: private */
        public String mPassword;
        /* access modifiers changed from: private */
        public int mProfileId;
        /* access modifiers changed from: private */
        public int mProtocol = -1;
        /* access modifiers changed from: private */
        public String mProxyAddress;
        /* access modifiers changed from: private */
        public int mProxyPort = -1;
        /* access modifiers changed from: private */
        public int mRoamingProtocol = -1;
        /* access modifiers changed from: private */
        public int mSkip464Xlat = -1;
        /* access modifiers changed from: private */
        public String mUser;
        /* access modifiers changed from: private */
        public int mWaitTime;

        /* access modifiers changed from: private */
        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setMtu(int mtu) {
            this.mMtu = mtu;
            return this;
        }

        public Builder setProfileId(int profileId) {
            this.mProfileId = profileId;
            return this;
        }

        public Builder setModemCognitive(boolean modemCognitive) {
            this.mModemCognitive = modemCognitive;
            return this;
        }

        public Builder setMaxConns(int maxConns) {
            this.mMaxConns = maxConns;
            return this;
        }

        public Builder setWaitTime(int waitTime) {
            this.mWaitTime = waitTime;
            return this;
        }

        public Builder setMaxConnsTime(int maxConnsTime) {
            this.mMaxConnsTime = maxConnsTime;
            return this;
        }

        public Builder setMvnoMatchData(String mvnoMatchData) {
            this.mMvnoMatchData = mvnoMatchData;
            return this;
        }

        public Builder setApnSetId(int apnSetId) {
            this.mApnSetId = apnSetId;
            return this;
        }

        public Builder setEntryName(String entryName) {
            this.mEntryName = entryName;
            return this;
        }

        public Builder setApnName(String apnName) {
            this.mApnName = apnName;
            return this;
        }

        @Deprecated
        public Builder setProxyAddress(InetAddress proxy) {
            this.mProxyAddress = ApnSetting.inetAddressToString(proxy);
            return this;
        }

        public Builder setProxyAddress(String proxy) {
            this.mProxyAddress = proxy;
            return this;
        }

        public Builder setProxyPort(int port) {
            this.mProxyPort = port;
            return this;
        }

        public Builder setMmsc(Uri mmsc) {
            this.mMmsc = mmsc;
            return this;
        }

        @Deprecated
        public Builder setMmsProxyAddress(InetAddress mmsProxy) {
            this.mMmsProxyAddress = ApnSetting.inetAddressToString(mmsProxy);
            return this;
        }

        public Builder setMmsProxyAddress(String mmsProxy) {
            this.mMmsProxyAddress = mmsProxy;
            return this;
        }

        public Builder setMmsProxyPort(int mmsPort) {
            this.mMmsProxyPort = mmsPort;
            return this;
        }

        public Builder setUser(String user) {
            this.mUser = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        public Builder setAuthType(int authType) {
            this.mAuthType = authType;
            return this;
        }

        public Builder setApnTypeBitmask(int apnTypeBitmask) {
            this.mApnTypeBitmask = apnTypeBitmask;
            return this;
        }

        public Builder setOperatorNumeric(String operatorNumeric) {
            this.mOperatorNumeric = operatorNumeric;
            return this;
        }

        public Builder setProtocol(int protocol) {
            this.mProtocol = protocol;
            return this;
        }

        public Builder setRoamingProtocol(int roamingProtocol) {
            this.mRoamingProtocol = roamingProtocol;
            return this;
        }

        public Builder setCarrierEnabled(boolean carrierEnabled) {
            this.mCarrierEnabled = carrierEnabled;
            return this;
        }

        public Builder setNetworkTypeBitmask(int networkTypeBitmask) {
            this.mNetworkTypeBitmask = networkTypeBitmask;
            return this;
        }

        public Builder setMvnoType(int mvnoType) {
            this.mMvnoType = mvnoType;
            return this;
        }

        public Builder setCarrierId(int carrierId) {
            this.mCarrierId = carrierId;
            return this;
        }

        public Builder setSkip464Xlat(int skip464xlat) {
            this.mSkip464Xlat = skip464xlat;
            return this;
        }

        public ApnSetting build() {
            if ((this.mApnTypeBitmask & 255) == 0 || TextUtils.isEmpty(this.mApnName) || TextUtils.isEmpty(this.mEntryName)) {
                return null;
            }
            return new ApnSetting(this);
        }

        public ApnSetting buildWithoutCheck() {
            return new ApnSetting(this);
        }
    }
}
