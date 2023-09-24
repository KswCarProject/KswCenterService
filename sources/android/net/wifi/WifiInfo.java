package android.net.wifi;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.NetworkInfo;
import android.net.NetworkUtils;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumMap;
import java.util.Locale;

/* loaded from: classes3.dex */
public class WifiInfo implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiInfo> CREATOR;
    @UnsupportedAppUsage
    public static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    public static final String FREQUENCY_UNITS = "MHz";
    @UnsupportedAppUsage
    public static final int INVALID_RSSI = -127;
    public static final String LINK_SPEED_UNITS = "Mbps";
    public static final int LINK_SPEED_UNKNOWN = -1;
    public static final int MAX_RSSI = 200;
    public static final int MIN_RSSI = -126;
    private static final String TAG = "WifiInfo";
    private static final EnumMap<SupplicantState, NetworkInfo.DetailedState> stateMap = new EnumMap<>(SupplicantState.class);
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mBSSID;
    private boolean mEphemeral;
    private String mFqdn;
    private int mFrequency;
    @UnsupportedAppUsage
    private InetAddress mIpAddress;
    private int mLinkSpeed;
    @UnsupportedAppUsage
    private String mMacAddress;
    private boolean mMeteredHint;
    private int mNetworkId;
    private String mNetworkSuggestionOrSpecifierPackageName;
    private boolean mOsuAp;
    private String mProviderFriendlyName;
    private int mRssi;
    private int mRxLinkSpeed;
    private SupplicantState mSupplicantState;
    private boolean mTrusted;
    private boolean mTwtSupport;
    private int mTxLinkSpeed;
    private boolean mVhtMax8SpatialStreamsSupport;
    private int mWifiGeneration;
    @UnsupportedAppUsage
    private WifiSsid mWifiSsid;
    public long rxSuccess;
    public double rxSuccessRate;
    @UnsupportedAppUsage
    public int score;
    public long txBad;
    public double txBadRate;
    public long txRetries;
    public double txRetriesRate;
    public long txSuccess;
    public double txSuccessRate;

    static {
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.DISCONNECTED, (SupplicantState) NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.INTERFACE_DISABLED, (SupplicantState) NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.INACTIVE, (SupplicantState) NetworkInfo.DetailedState.IDLE);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.SCANNING, (SupplicantState) NetworkInfo.DetailedState.SCANNING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.AUTHENTICATING, (SupplicantState) NetworkInfo.DetailedState.CONNECTING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.ASSOCIATING, (SupplicantState) NetworkInfo.DetailedState.CONNECTING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.ASSOCIATED, (SupplicantState) NetworkInfo.DetailedState.CONNECTING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.FOUR_WAY_HANDSHAKE, (SupplicantState) NetworkInfo.DetailedState.AUTHENTICATING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.GROUP_HANDSHAKE, (SupplicantState) NetworkInfo.DetailedState.AUTHENTICATING);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.COMPLETED, (SupplicantState) NetworkInfo.DetailedState.OBTAINING_IPADDR);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.DORMANT, (SupplicantState) NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.UNINITIALIZED, (SupplicantState) NetworkInfo.DetailedState.IDLE);
        stateMap.put((EnumMap<SupplicantState, NetworkInfo.DetailedState>) SupplicantState.INVALID, (SupplicantState) NetworkInfo.DetailedState.FAILED);
        CREATOR = new Parcelable.Creator<WifiInfo>() { // from class: android.net.wifi.WifiInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public WifiInfo createFromParcel(Parcel in) {
                WifiInfo info = new WifiInfo();
                info.setNetworkId(in.readInt());
                info.setRssi(in.readInt());
                info.setLinkSpeed(in.readInt());
                info.setTxLinkSpeedMbps(in.readInt());
                info.setRxLinkSpeedMbps(in.readInt());
                info.setFrequency(in.readInt());
                if (in.readByte() == 1) {
                    try {
                        info.setInetAddress(InetAddress.getByAddress(in.createByteArray()));
                    } catch (UnknownHostException e) {
                    }
                }
                if (in.readInt() == 1) {
                    info.mWifiSsid = WifiSsid.CREATOR.createFromParcel(in);
                }
                info.mBSSID = in.readString();
                info.mMacAddress = in.readString();
                info.mMeteredHint = in.readInt() != 0;
                info.mEphemeral = in.readInt() != 0;
                info.mTrusted = in.readInt() != 0;
                info.score = in.readInt();
                info.txSuccess = in.readLong();
                info.txSuccessRate = in.readDouble();
                info.txRetries = in.readLong();
                info.txRetriesRate = in.readDouble();
                info.txBad = in.readLong();
                info.txBadRate = in.readDouble();
                info.rxSuccess = in.readLong();
                info.rxSuccessRate = in.readDouble();
                info.mSupplicantState = SupplicantState.CREATOR.createFromParcel(in);
                info.mOsuAp = in.readInt() != 0;
                info.mNetworkSuggestionOrSpecifierPackageName = in.readString();
                info.mFqdn = in.readString();
                info.mProviderFriendlyName = in.readString();
                info.mWifiGeneration = in.readInt();
                info.mVhtMax8SpatialStreamsSupport = in.readInt() != 0;
                info.mTwtSupport = in.readInt() != 0;
                return info;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public WifiInfo[] newArray(int size) {
                return new WifiInfo[size];
            }
        };
    }

    @UnsupportedAppUsage
    public WifiInfo() {
        this.mMacAddress = "02:00:00:00:00:00";
        this.mWifiSsid = null;
        this.mBSSID = null;
        this.mNetworkId = -1;
        this.mSupplicantState = SupplicantState.UNINITIALIZED;
        this.mRssi = -127;
        this.mLinkSpeed = -1;
        this.mFrequency = -1;
        this.mWifiGeneration = -1;
    }

    public void reset() {
        setInetAddress(null);
        setBSSID(null);
        setSSID(null);
        setNetworkId(-1);
        setRssi(-127);
        setLinkSpeed(-1);
        setTxLinkSpeedMbps(-1);
        setRxLinkSpeedMbps(-1);
        setFrequency(-1);
        setMeteredHint(false);
        setEphemeral(false);
        setOsuAp(false);
        setNetworkSuggestionOrSpecifierPackageName(null);
        setFQDN(null);
        setProviderFriendlyName(null);
        setWifiGeneration(-1);
        setTwtSupport(false);
        setVhtMax8SpatialStreamsSupport(false);
        this.txBad = 0L;
        this.txSuccess = 0L;
        this.rxSuccess = 0L;
        this.txRetries = 0L;
        this.txBadRate = 0.0d;
        this.txSuccessRate = 0.0d;
        this.rxSuccessRate = 0.0d;
        this.txRetriesRate = 0.0d;
        this.score = 0;
    }

    public WifiInfo(WifiInfo source) {
        this.mMacAddress = "02:00:00:00:00:00";
        if (source != null) {
            this.mSupplicantState = source.mSupplicantState;
            this.mBSSID = source.mBSSID;
            this.mWifiSsid = source.mWifiSsid;
            this.mNetworkId = source.mNetworkId;
            this.mRssi = source.mRssi;
            this.mLinkSpeed = source.mLinkSpeed;
            this.mTxLinkSpeed = source.mTxLinkSpeed;
            this.mRxLinkSpeed = source.mRxLinkSpeed;
            this.mFrequency = source.mFrequency;
            this.mIpAddress = source.mIpAddress;
            this.mMacAddress = source.mMacAddress;
            this.mMeteredHint = source.mMeteredHint;
            this.mEphemeral = source.mEphemeral;
            this.mTrusted = source.mTrusted;
            this.mNetworkSuggestionOrSpecifierPackageName = source.mNetworkSuggestionOrSpecifierPackageName;
            this.mOsuAp = source.mOsuAp;
            this.mFqdn = source.mFqdn;
            this.mProviderFriendlyName = source.mProviderFriendlyName;
            this.mWifiGeneration = source.mWifiGeneration;
            this.mVhtMax8SpatialStreamsSupport = source.mVhtMax8SpatialStreamsSupport;
            this.mTwtSupport = source.mTwtSupport;
            this.txBad = source.txBad;
            this.txRetries = source.txRetries;
            this.txSuccess = source.txSuccess;
            this.rxSuccess = source.rxSuccess;
            this.txBadRate = source.txBadRate;
            this.txRetriesRate = source.txRetriesRate;
            this.txSuccessRate = source.txSuccessRate;
            this.rxSuccessRate = source.rxSuccessRate;
            this.score = source.score;
        }
    }

    public void setSSID(WifiSsid wifiSsid) {
        this.mWifiSsid = wifiSsid;
    }

    public String getSSID() {
        if (this.mWifiSsid != null) {
            String unicode = this.mWifiSsid.toString();
            if (!TextUtils.isEmpty(unicode)) {
                return "\"" + unicode + "\"";
            }
            String hex = this.mWifiSsid.getHexString();
            return hex != null ? hex : WifiSsid.NONE;
        }
        return WifiSsid.NONE;
    }

    @UnsupportedAppUsage
    public WifiSsid getWifiSsid() {
        return this.mWifiSsid;
    }

    @UnsupportedAppUsage
    public void setBSSID(String BSSID) {
        this.mBSSID = BSSID;
    }

    public String getBSSID() {
        return this.mBSSID;
    }

    public int getRssi() {
        return this.mRssi;
    }

    @UnsupportedAppUsage
    public void setRssi(int rssi) {
        if (rssi < -127) {
            rssi = -127;
        }
        if (rssi > 200) {
            rssi = 200;
        }
        this.mRssi = rssi;
    }

    public int getLinkSpeed() {
        return this.mLinkSpeed;
    }

    @UnsupportedAppUsage
    public void setLinkSpeed(int linkSpeed) {
        this.mLinkSpeed = linkSpeed;
    }

    public int getTxLinkSpeedMbps() {
        return this.mTxLinkSpeed;
    }

    public void setTxLinkSpeedMbps(int txLinkSpeed) {
        this.mTxLinkSpeed = txLinkSpeed;
    }

    public int getRxLinkSpeedMbps() {
        return this.mRxLinkSpeed;
    }

    public void setRxLinkSpeedMbps(int rxLinkSpeed) {
        this.mRxLinkSpeed = rxLinkSpeed;
    }

    public int getFrequency() {
        return this.mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public boolean is24GHz() {
        return ScanResult.is24GHz(this.mFrequency);
    }

    @UnsupportedAppUsage
    public boolean is5GHz() {
        return ScanResult.is5GHz(this.mFrequency);
    }

    @UnsupportedAppUsage
    public void setMacAddress(String macAddress) {
        this.mMacAddress = macAddress;
    }

    public String getMacAddress() {
        return this.mMacAddress;
    }

    public boolean hasRealMacAddress() {
        return (this.mMacAddress == null || "02:00:00:00:00:00".equals(this.mMacAddress)) ? false : true;
    }

    public void setMeteredHint(boolean meteredHint) {
        this.mMeteredHint = meteredHint;
    }

    @UnsupportedAppUsage
    public boolean getMeteredHint() {
        return this.mMeteredHint;
    }

    public void setEphemeral(boolean ephemeral) {
        this.mEphemeral = ephemeral;
    }

    @UnsupportedAppUsage
    public boolean isEphemeral() {
        return this.mEphemeral;
    }

    public void setTrusted(boolean trusted) {
        this.mTrusted = trusted;
    }

    public boolean isTrusted() {
        return this.mTrusted;
    }

    public void setOsuAp(boolean osuAp) {
        this.mOsuAp = osuAp;
    }

    @SystemApi
    public boolean isOsuAp() {
        return this.mOsuAp;
    }

    @SystemApi
    public boolean isPasspointAp() {
        return (this.mFqdn == null || this.mProviderFriendlyName == null) ? false : true;
    }

    public void setFQDN(String fqdn) {
        this.mFqdn = fqdn;
    }

    public String getPasspointFqdn() {
        return this.mFqdn;
    }

    public void setProviderFriendlyName(String providerFriendlyName) {
        this.mProviderFriendlyName = providerFriendlyName;
    }

    public String getPasspointProviderFriendlyName() {
        return this.mProviderFriendlyName;
    }

    public void setNetworkSuggestionOrSpecifierPackageName(String packageName) {
        this.mNetworkSuggestionOrSpecifierPackageName = packageName;
    }

    public String getNetworkSuggestionOrSpecifierPackageName() {
        return this.mNetworkSuggestionOrSpecifierPackageName;
    }

    @UnsupportedAppUsage
    public void setNetworkId(int id) {
        this.mNetworkId = id;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public SupplicantState getSupplicantState() {
        return this.mSupplicantState;
    }

    @UnsupportedAppUsage
    public void setSupplicantState(SupplicantState state) {
        this.mSupplicantState = state;
    }

    public void setInetAddress(InetAddress address) {
        this.mIpAddress = address;
    }

    public int getIpAddress() {
        if (!(this.mIpAddress instanceof Inet4Address)) {
            return 0;
        }
        int result = NetworkUtils.inetAddressToInt((Inet4Address) this.mIpAddress);
        return result;
    }

    public boolean getHiddenSSID() {
        if (this.mWifiSsid == null) {
            return false;
        }
        return this.mWifiSsid.isHidden();
    }

    public static NetworkInfo.DetailedState getDetailedStateOf(SupplicantState suppState) {
        return stateMap.get(suppState);
    }

    @UnsupportedAppUsage
    void setSupplicantState(String stateName) {
        this.mSupplicantState = valueOf(stateName);
    }

    static SupplicantState valueOf(String stateName) {
        if ("4WAY_HANDSHAKE".equalsIgnoreCase(stateName)) {
            return SupplicantState.FOUR_WAY_HANDSHAKE;
        }
        try {
            return SupplicantState.valueOf(stateName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return SupplicantState.INVALID;
        }
    }

    public void setWifiGeneration(int generation) {
        this.mWifiGeneration = generation;
    }

    public int getWifiGeneration() {
        return this.mWifiGeneration;
    }

    public void setVhtMax8SpatialStreamsSupport(boolean vhtMax8SpatialStreamsSupport) {
        this.mVhtMax8SpatialStreamsSupport = vhtMax8SpatialStreamsSupport;
    }

    public boolean isVhtMax8SpatialStreamsSupported() {
        return this.mVhtMax8SpatialStreamsSupport;
    }

    public void setTwtSupport(boolean twtSupport) {
        this.mTwtSupport = twtSupport;
    }

    public boolean isTwtSupported() {
        return this.mTwtSupport;
    }

    @UnsupportedAppUsage
    public static String removeDoubleQuotes(String string) {
        if (string == null) {
            return null;
        }
        int length = string.length();
        if (length > 1 && string.charAt(0) == '\"' && string.charAt(length - 1) == '\"') {
            return string.substring(1, length - 1);
        }
        return string;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SSID: ");
        sb.append(this.mWifiSsid == null ? WifiSsid.NONE : this.mWifiSsid);
        sb.append(", BSSID: ");
        sb.append(this.mBSSID == null ? "<none>" : this.mBSSID);
        sb.append(", MAC: ");
        sb.append(this.mMacAddress == null ? "<none>" : this.mMacAddress);
        sb.append(", Supplicant state: ");
        sb.append(this.mSupplicantState == null ? "<none>" : this.mSupplicantState);
        sb.append(", Wifi Generation: ");
        sb.append(this.mWifiGeneration);
        sb.append(", TWT support: ");
        sb.append(this.mTwtSupport);
        sb.append(", Eight Max VHT Spatial streams support: ");
        sb.append(this.mVhtMax8SpatialStreamsSupport);
        sb.append(", RSSI: ");
        sb.append(this.mRssi);
        sb.append(", Link speed: ");
        sb.append(this.mLinkSpeed);
        sb.append(LINK_SPEED_UNITS);
        sb.append(", Tx Link speed: ");
        sb.append(this.mTxLinkSpeed);
        sb.append(LINK_SPEED_UNITS);
        sb.append(", Rx Link speed: ");
        sb.append(this.mRxLinkSpeed);
        sb.append(LINK_SPEED_UNITS);
        sb.append(", Frequency: ");
        sb.append(this.mFrequency);
        sb.append(FREQUENCY_UNITS);
        sb.append(", Net ID: ");
        sb.append(this.mNetworkId);
        sb.append(", Metered hint: ");
        sb.append(this.mMeteredHint);
        sb.append(", score: ");
        sb.append(Integer.toString(this.score));
        return sb.toString();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mNetworkId);
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mLinkSpeed);
        dest.writeInt(this.mTxLinkSpeed);
        dest.writeInt(this.mRxLinkSpeed);
        dest.writeInt(this.mFrequency);
        if (this.mIpAddress != null) {
            dest.writeByte((byte) 1);
            dest.writeByteArray(this.mIpAddress.getAddress());
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mWifiSsid != null) {
            dest.writeInt(1);
            this.mWifiSsid.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        dest.writeString(this.mBSSID);
        dest.writeString(this.mMacAddress);
        dest.writeInt(this.mMeteredHint ? 1 : 0);
        dest.writeInt(this.mEphemeral ? 1 : 0);
        dest.writeInt(this.mTrusted ? 1 : 0);
        dest.writeInt(this.score);
        dest.writeLong(this.txSuccess);
        dest.writeDouble(this.txSuccessRate);
        dest.writeLong(this.txRetries);
        dest.writeDouble(this.txRetriesRate);
        dest.writeLong(this.txBad);
        dest.writeDouble(this.txBadRate);
        dest.writeLong(this.rxSuccess);
        dest.writeDouble(this.rxSuccessRate);
        this.mSupplicantState.writeToParcel(dest, flags);
        dest.writeInt(this.mOsuAp ? 1 : 0);
        dest.writeString(this.mNetworkSuggestionOrSpecifierPackageName);
        dest.writeString(this.mFqdn);
        dest.writeString(this.mProviderFriendlyName);
        dest.writeInt(this.mWifiGeneration);
        dest.writeInt(this.mVhtMax8SpatialStreamsSupport ? 1 : 0);
        dest.writeInt(this.mTwtSupport ? 1 : 0);
    }
}
