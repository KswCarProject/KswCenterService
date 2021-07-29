package android.net;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import java.io.FileDescriptor;
import java.util.List;

public interface IConnectivityManager extends IInterface {
    boolean addVpnAddress(String str, int i) throws RemoteException;

    int checkMobileProvisioning(int i) throws RemoteException;

    ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException;

    void factoryReset() throws RemoteException;

    @UnsupportedAppUsage
    LinkProperties getActiveLinkProperties() throws RemoteException;

    Network getActiveNetwork() throws RemoteException;

    Network getActiveNetworkForUid(int i, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    NetworkInfo getActiveNetworkInfo() throws RemoteException;

    NetworkInfo getActiveNetworkInfoForUid(int i, boolean z) throws RemoteException;

    NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException;

    @UnsupportedAppUsage
    NetworkInfo[] getAllNetworkInfo() throws RemoteException;

    @UnsupportedAppUsage
    NetworkState[] getAllNetworkState() throws RemoteException;

    Network[] getAllNetworks() throws RemoteException;

    String getAlwaysOnVpnPackage(int i) throws RemoteException;

    String getCaptivePortalServerUrl() throws RemoteException;

    int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException;

    NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int i) throws RemoteException;

    NetworkRequest getDefaultRequest() throws RemoteException;

    ProxyInfo getGlobalProxy() throws RemoteException;

    @UnsupportedAppUsage
    int getLastTetherError(String str) throws RemoteException;

    void getLatestTetheringEntitlementResult(int i, ResultReceiver resultReceiver, boolean z, String str) throws RemoteException;

    LegacyVpnInfo getLegacyVpnInfo(int i) throws RemoteException;

    LinkProperties getLinkProperties(Network network) throws RemoteException;

    LinkProperties getLinkPropertiesForType(int i) throws RemoteException;

    String getMobileProvisioningUrl() throws RemoteException;

    int getMultipathPreference(Network network) throws RemoteException;

    NetworkCapabilities getNetworkCapabilities(Network network) throws RemoteException;

    Network getNetworkForType(int i) throws RemoteException;

    NetworkInfo getNetworkInfo(int i) throws RemoteException;

    NetworkInfo getNetworkInfoForUid(Network network, int i, boolean z) throws RemoteException;

    byte[] getNetworkWatchlistConfigHash() throws RemoteException;

    ProxyInfo getProxyForNetwork(Network network) throws RemoteException;

    int getRestoreDefaultNetworkDelay(int i) throws RemoteException;

    String[] getTetherableBluetoothRegexs() throws RemoteException;

    @UnsupportedAppUsage
    String[] getTetherableIfaces() throws RemoteException;

    @UnsupportedAppUsage
    String[] getTetherableUsbRegexs() throws RemoteException;

    @UnsupportedAppUsage
    String[] getTetherableWifiRegexs() throws RemoteException;

    String[] getTetheredDhcpRanges() throws RemoteException;

    @UnsupportedAppUsage
    String[] getTetheredIfaces() throws RemoteException;

    @UnsupportedAppUsage
    String[] getTetheringErroredIfaces() throws RemoteException;

    VpnConfig getVpnConfig(int i) throws RemoteException;

    List<String> getVpnLockdownWhitelist(int i) throws RemoteException;

    boolean isActiveNetworkMetered() throws RemoteException;

    boolean isAlwaysOnVpnPackageSupported(int i, String str) throws RemoteException;

    boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException;

    boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException;

    boolean isNetworkSupported(int i) throws RemoteException;

    boolean isTetheringSupported(String str) throws RemoteException;

    boolean isVpnLockdownEnabled(int i) throws RemoteException;

    NetworkRequest listenForNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, IBinder iBinder) throws RemoteException;

    void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent pendingIntent) throws RemoteException;

    NetworkRequest pendingRequestForNetwork(NetworkCapabilities networkCapabilities, PendingIntent pendingIntent) throws RemoteException;

    boolean prepareVpn(String str, String str2, int i) throws RemoteException;

    int registerNetworkAgent(Messenger messenger, NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int i, NetworkMisc networkMisc, int i2) throws RemoteException;

    int registerNetworkFactory(Messenger messenger, String str) throws RemoteException;

    void registerTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String str) throws RemoteException;

    void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException;

    void releasePendingNetworkRequest(PendingIntent pendingIntent) throws RemoteException;

    boolean removeVpnAddress(String str, int i) throws RemoteException;

    void reportInetCondition(int i, int i2) throws RemoteException;

    void reportNetworkConnectivity(Network network, boolean z) throws RemoteException;

    boolean requestBandwidthUpdate(Network network) throws RemoteException;

    NetworkRequest requestNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, int i, IBinder iBinder, int i2) throws RemoteException;

    boolean requestRouteToHostAddress(int i, byte[] bArr) throws RemoteException;

    void setAcceptPartialConnectivity(Network network, boolean z, boolean z2) throws RemoteException;

    void setAcceptUnvalidated(Network network, boolean z, boolean z2) throws RemoteException;

    void setAirplaneMode(boolean z) throws RemoteException;

    boolean setAlwaysOnVpnPackage(int i, String str, boolean z, List<String> list) throws RemoteException;

    void setAvoidUnvalidated(Network network) throws RemoteException;

    void setGlobalProxy(ProxyInfo proxyInfo) throws RemoteException;

    void setProvisioningNotificationVisible(boolean z, int i, String str) throws RemoteException;

    boolean setUnderlyingNetworksForVpn(Network[] networkArr) throws RemoteException;

    int setUsbTethering(boolean z, String str) throws RemoteException;

    void setVpnPackageAuthorization(String str, int i, boolean z) throws RemoteException;

    boolean shouldAvoidBadWifi() throws RemoteException;

    void startCaptivePortalApp(Network network) throws RemoteException;

    void startCaptivePortalAppInternal(Network network, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException;

    void startNattKeepalive(Network network, int i, ISocketKeepaliveCallback iSocketKeepaliveCallback, String str, int i2, String str2) throws RemoteException;

    void startNattKeepaliveWithFd(Network network, FileDescriptor fileDescriptor, int i, int i2, ISocketKeepaliveCallback iSocketKeepaliveCallback, String str, String str2) throws RemoteException;

    IBinder startOrGetTestNetworkService() throws RemoteException;

    void startTcpKeepalive(Network network, FileDescriptor fileDescriptor, int i, ISocketKeepaliveCallback iSocketKeepaliveCallback) throws RemoteException;

    void startTethering(int i, ResultReceiver resultReceiver, boolean z, String str) throws RemoteException;

    void stopKeepalive(Network network, int i) throws RemoteException;

    void stopTethering(int i, String str) throws RemoteException;

    int tether(String str, String str2) throws RemoteException;

    void unregisterNetworkFactory(Messenger messenger) throws RemoteException;

    void unregisterTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String str) throws RemoteException;

    int untether(String str, String str2) throws RemoteException;

    boolean updateLockdownVpn() throws RemoteException;

    public static class Default implements IConnectivityManager {
        public Network getActiveNetwork() throws RemoteException {
            return null;
        }

        public Network getActiveNetworkForUid(int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        public NetworkInfo getActiveNetworkInfo() throws RemoteException {
            return null;
        }

        public NetworkInfo getActiveNetworkInfoForUid(int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        public NetworkInfo getNetworkInfo(int networkType) throws RemoteException {
            return null;
        }

        public NetworkInfo getNetworkInfoForUid(Network network, int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
            return null;
        }

        public Network getNetworkForType(int networkType) throws RemoteException {
            return null;
        }

        public Network[] getAllNetworks() throws RemoteException {
            return null;
        }

        public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int userId) throws RemoteException {
            return null;
        }

        public boolean isNetworkSupported(int networkType) throws RemoteException {
            return false;
        }

        public LinkProperties getActiveLinkProperties() throws RemoteException {
            return null;
        }

        public LinkProperties getLinkPropertiesForType(int networkType) throws RemoteException {
            return null;
        }

        public LinkProperties getLinkProperties(Network network) throws RemoteException {
            return null;
        }

        public NetworkCapabilities getNetworkCapabilities(Network network) throws RemoteException {
            return null;
        }

        public NetworkState[] getAllNetworkState() throws RemoteException {
            return null;
        }

        public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
            return null;
        }

        public boolean isActiveNetworkMetered() throws RemoteException {
            return false;
        }

        public boolean requestRouteToHostAddress(int networkType, byte[] hostAddress) throws RemoteException {
            return false;
        }

        public int tether(String iface, String callerPkg) throws RemoteException {
            return 0;
        }

        public int untether(String iface, String callerPkg) throws RemoteException {
            return 0;
        }

        public int getLastTetherError(String iface) throws RemoteException {
            return 0;
        }

        public boolean isTetheringSupported(String callerPkg) throws RemoteException {
            return false;
        }

        public void startTethering(int type, ResultReceiver receiver, boolean showProvisioningUi, String callerPkg) throws RemoteException {
        }

        public void stopTethering(int type, String callerPkg) throws RemoteException {
        }

        public String[] getTetherableIfaces() throws RemoteException {
            return null;
        }

        public String[] getTetheredIfaces() throws RemoteException {
            return null;
        }

        public String[] getTetheringErroredIfaces() throws RemoteException {
            return null;
        }

        public String[] getTetheredDhcpRanges() throws RemoteException {
            return null;
        }

        public String[] getTetherableUsbRegexs() throws RemoteException {
            return null;
        }

        public String[] getTetherableWifiRegexs() throws RemoteException {
            return null;
        }

        public String[] getTetherableBluetoothRegexs() throws RemoteException {
            return null;
        }

        public int setUsbTethering(boolean enable, String callerPkg) throws RemoteException {
            return 0;
        }

        public void reportInetCondition(int networkType, int percentage) throws RemoteException {
        }

        public void reportNetworkConnectivity(Network network, boolean hasConnectivity) throws RemoteException {
        }

        public ProxyInfo getGlobalProxy() throws RemoteException {
            return null;
        }

        public void setGlobalProxy(ProxyInfo p) throws RemoteException {
        }

        public ProxyInfo getProxyForNetwork(Network nework) throws RemoteException {
            return null;
        }

        public boolean prepareVpn(String oldPackage, String newPackage, int userId) throws RemoteException {
            return false;
        }

        public void setVpnPackageAuthorization(String packageName, int userId, boolean authorized) throws RemoteException {
        }

        public ParcelFileDescriptor establishVpn(VpnConfig config) throws RemoteException {
            return null;
        }

        public VpnConfig getVpnConfig(int userId) throws RemoteException {
            return null;
        }

        public void startLegacyVpn(VpnProfile profile) throws RemoteException {
        }

        public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
            return null;
        }

        public boolean updateLockdownVpn() throws RemoteException {
            return false;
        }

        public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName) throws RemoteException {
            return false;
        }

        public boolean setAlwaysOnVpnPackage(int userId, String packageName, boolean lockdown, List<String> list) throws RemoteException {
            return false;
        }

        public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
            return null;
        }

        public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
            return false;
        }

        public List<String> getVpnLockdownWhitelist(int userId) throws RemoteException {
            return null;
        }

        public int checkMobileProvisioning(int suggestedTimeOutMs) throws RemoteException {
            return 0;
        }

        public String getMobileProvisioningUrl() throws RemoteException {
            return null;
        }

        public void setProvisioningNotificationVisible(boolean visible, int networkType, String action) throws RemoteException {
        }

        public void setAirplaneMode(boolean enable) throws RemoteException {
        }

        public int registerNetworkFactory(Messenger messenger, String name) throws RemoteException {
            return 0;
        }

        public boolean requestBandwidthUpdate(Network network) throws RemoteException {
            return false;
        }

        public void unregisterNetworkFactory(Messenger messenger) throws RemoteException {
        }

        public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc, int factorySerialNumber) throws RemoteException {
            return 0;
        }

        public NetworkRequest requestNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, int timeoutSec, IBinder binder, int legacy) throws RemoteException {
            return null;
        }

        public NetworkRequest pendingRequestForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
            return null;
        }

        public void releasePendingNetworkRequest(PendingIntent operation) throws RemoteException {
        }

        public NetworkRequest listenForNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, IBinder binder) throws RemoteException {
            return null;
        }

        public void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
        }

        public void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException {
        }

        public void setAcceptUnvalidated(Network network, boolean accept, boolean always) throws RemoteException {
        }

        public void setAcceptPartialConnectivity(Network network, boolean accept, boolean always) throws RemoteException {
        }

        public void setAvoidUnvalidated(Network network) throws RemoteException {
        }

        public void startCaptivePortalApp(Network network) throws RemoteException {
        }

        public void startCaptivePortalAppInternal(Network network, Bundle appExtras) throws RemoteException {
        }

        public boolean shouldAvoidBadWifi() throws RemoteException {
            return false;
        }

        public int getMultipathPreference(Network Network) throws RemoteException {
            return 0;
        }

        public NetworkRequest getDefaultRequest() throws RemoteException {
            return null;
        }

        public int getRestoreDefaultNetworkDelay(int networkType) throws RemoteException {
            return 0;
        }

        public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
            return false;
        }

        public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
            return false;
        }

        public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
            return false;
        }

        public void factoryReset() throws RemoteException {
        }

        public void startNattKeepalive(Network network, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, int srcPort, String dstAddr) throws RemoteException {
        }

        public void startNattKeepaliveWithFd(Network network, FileDescriptor fd, int resourceId, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, String dstAddr) throws RemoteException {
        }

        public void startTcpKeepalive(Network network, FileDescriptor fd, int intervalSeconds, ISocketKeepaliveCallback cb) throws RemoteException {
        }

        public void stopKeepalive(Network network, int slot) throws RemoteException {
        }

        public String getCaptivePortalServerUrl() throws RemoteException {
            return null;
        }

        public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
            return null;
        }

        public int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException {
            return 0;
        }

        public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
            return false;
        }

        public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
            return false;
        }

        public void getLatestTetheringEntitlementResult(int type, ResultReceiver receiver, boolean showEntitlementUi, String callerPkg) throws RemoteException {
        }

        public void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
        }

        public void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
        }

        public IBinder startOrGetTestNetworkService() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IConnectivityManager {
        private static final String DESCRIPTOR = "android.net.IConnectivityManager";
        static final int TRANSACTION_addVpnAddress = 74;
        static final int TRANSACTION_checkMobileProvisioning = 51;
        static final int TRANSACTION_establishVpn = 41;
        static final int TRANSACTION_factoryReset = 77;
        static final int TRANSACTION_getActiveLinkProperties = 12;
        static final int TRANSACTION_getActiveNetwork = 1;
        static final int TRANSACTION_getActiveNetworkForUid = 2;
        static final int TRANSACTION_getActiveNetworkInfo = 3;
        static final int TRANSACTION_getActiveNetworkInfoForUid = 4;
        static final int TRANSACTION_getActiveNetworkQuotaInfo = 17;
        static final int TRANSACTION_getAllNetworkInfo = 7;
        static final int TRANSACTION_getAllNetworkState = 16;
        static final int TRANSACTION_getAllNetworks = 9;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 48;
        static final int TRANSACTION_getCaptivePortalServerUrl = 82;
        static final int TRANSACTION_getConnectionOwnerUid = 84;
        static final int TRANSACTION_getDefaultNetworkCapabilitiesForUser = 10;
        static final int TRANSACTION_getDefaultRequest = 72;
        static final int TRANSACTION_getGlobalProxy = 36;
        static final int TRANSACTION_getLastTetherError = 22;
        static final int TRANSACTION_getLatestTetheringEntitlementResult = 87;
        static final int TRANSACTION_getLegacyVpnInfo = 44;
        static final int TRANSACTION_getLinkProperties = 14;
        static final int TRANSACTION_getLinkPropertiesForType = 13;
        static final int TRANSACTION_getMobileProvisioningUrl = 52;
        static final int TRANSACTION_getMultipathPreference = 71;
        static final int TRANSACTION_getNetworkCapabilities = 15;
        static final int TRANSACTION_getNetworkForType = 8;
        static final int TRANSACTION_getNetworkInfo = 5;
        static final int TRANSACTION_getNetworkInfoForUid = 6;
        static final int TRANSACTION_getNetworkWatchlistConfigHash = 83;
        static final int TRANSACTION_getProxyForNetwork = 38;
        static final int TRANSACTION_getRestoreDefaultNetworkDelay = 73;
        static final int TRANSACTION_getTetherableBluetoothRegexs = 32;
        static final int TRANSACTION_getTetherableIfaces = 26;
        static final int TRANSACTION_getTetherableUsbRegexs = 30;
        static final int TRANSACTION_getTetherableWifiRegexs = 31;
        static final int TRANSACTION_getTetheredDhcpRanges = 29;
        static final int TRANSACTION_getTetheredIfaces = 27;
        static final int TRANSACTION_getTetheringErroredIfaces = 28;
        static final int TRANSACTION_getVpnConfig = 42;
        static final int TRANSACTION_getVpnLockdownWhitelist = 50;
        static final int TRANSACTION_isActiveNetworkMetered = 18;
        static final int TRANSACTION_isAlwaysOnVpnPackageSupported = 46;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnApp = 85;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnLockdownApp = 86;
        static final int TRANSACTION_isNetworkSupported = 11;
        static final int TRANSACTION_isTetheringSupported = 23;
        static final int TRANSACTION_isVpnLockdownEnabled = 49;
        static final int TRANSACTION_listenForNetwork = 62;
        static final int TRANSACTION_pendingListenForNetwork = 63;
        static final int TRANSACTION_pendingRequestForNetwork = 60;
        static final int TRANSACTION_prepareVpn = 39;
        static final int TRANSACTION_registerNetworkAgent = 58;
        static final int TRANSACTION_registerNetworkFactory = 55;
        static final int TRANSACTION_registerTetheringEventCallback = 88;
        static final int TRANSACTION_releaseNetworkRequest = 64;
        static final int TRANSACTION_releasePendingNetworkRequest = 61;
        static final int TRANSACTION_removeVpnAddress = 75;
        static final int TRANSACTION_reportInetCondition = 34;
        static final int TRANSACTION_reportNetworkConnectivity = 35;
        static final int TRANSACTION_requestBandwidthUpdate = 56;
        static final int TRANSACTION_requestNetwork = 59;
        static final int TRANSACTION_requestRouteToHostAddress = 19;
        static final int TRANSACTION_setAcceptPartialConnectivity = 66;
        static final int TRANSACTION_setAcceptUnvalidated = 65;
        static final int TRANSACTION_setAirplaneMode = 54;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 47;
        static final int TRANSACTION_setAvoidUnvalidated = 67;
        static final int TRANSACTION_setGlobalProxy = 37;
        static final int TRANSACTION_setProvisioningNotificationVisible = 53;
        static final int TRANSACTION_setUnderlyingNetworksForVpn = 76;
        static final int TRANSACTION_setUsbTethering = 33;
        static final int TRANSACTION_setVpnPackageAuthorization = 40;
        static final int TRANSACTION_shouldAvoidBadWifi = 70;
        static final int TRANSACTION_startCaptivePortalApp = 68;
        static final int TRANSACTION_startCaptivePortalAppInternal = 69;
        static final int TRANSACTION_startLegacyVpn = 43;
        static final int TRANSACTION_startNattKeepalive = 78;
        static final int TRANSACTION_startNattKeepaliveWithFd = 79;
        static final int TRANSACTION_startOrGetTestNetworkService = 90;
        static final int TRANSACTION_startTcpKeepalive = 80;
        static final int TRANSACTION_startTethering = 24;
        static final int TRANSACTION_stopKeepalive = 81;
        static final int TRANSACTION_stopTethering = 25;
        static final int TRANSACTION_tether = 20;
        static final int TRANSACTION_unregisterNetworkFactory = 57;
        static final int TRANSACTION_unregisterTetheringEventCallback = 89;
        static final int TRANSACTION_untether = 21;
        static final int TRANSACTION_updateLockdownVpn = 45;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConnectivityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IConnectivityManager)) {
                return new Proxy(obj);
            }
            return (IConnectivityManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getActiveNetwork";
                case 2:
                    return "getActiveNetworkForUid";
                case 3:
                    return "getActiveNetworkInfo";
                case 4:
                    return "getActiveNetworkInfoForUid";
                case 5:
                    return "getNetworkInfo";
                case 6:
                    return "getNetworkInfoForUid";
                case 7:
                    return "getAllNetworkInfo";
                case 8:
                    return "getNetworkForType";
                case 9:
                    return "getAllNetworks";
                case 10:
                    return "getDefaultNetworkCapabilitiesForUser";
                case 11:
                    return "isNetworkSupported";
                case 12:
                    return "getActiveLinkProperties";
                case 13:
                    return "getLinkPropertiesForType";
                case 14:
                    return "getLinkProperties";
                case 15:
                    return "getNetworkCapabilities";
                case 16:
                    return "getAllNetworkState";
                case 17:
                    return "getActiveNetworkQuotaInfo";
                case 18:
                    return "isActiveNetworkMetered";
                case 19:
                    return "requestRouteToHostAddress";
                case 20:
                    return "tether";
                case 21:
                    return "untether";
                case 22:
                    return "getLastTetherError";
                case 23:
                    return "isTetheringSupported";
                case 24:
                    return "startTethering";
                case 25:
                    return "stopTethering";
                case 26:
                    return "getTetherableIfaces";
                case 27:
                    return "getTetheredIfaces";
                case 28:
                    return "getTetheringErroredIfaces";
                case 29:
                    return "getTetheredDhcpRanges";
                case 30:
                    return "getTetherableUsbRegexs";
                case 31:
                    return "getTetherableWifiRegexs";
                case 32:
                    return "getTetherableBluetoothRegexs";
                case 33:
                    return "setUsbTethering";
                case 34:
                    return "reportInetCondition";
                case 35:
                    return "reportNetworkConnectivity";
                case 36:
                    return "getGlobalProxy";
                case 37:
                    return "setGlobalProxy";
                case 38:
                    return "getProxyForNetwork";
                case 39:
                    return "prepareVpn";
                case 40:
                    return "setVpnPackageAuthorization";
                case 41:
                    return "establishVpn";
                case 42:
                    return "getVpnConfig";
                case 43:
                    return "startLegacyVpn";
                case 44:
                    return "getLegacyVpnInfo";
                case 45:
                    return "updateLockdownVpn";
                case 46:
                    return "isAlwaysOnVpnPackageSupported";
                case 47:
                    return "setAlwaysOnVpnPackage";
                case 48:
                    return "getAlwaysOnVpnPackage";
                case 49:
                    return "isVpnLockdownEnabled";
                case 50:
                    return "getVpnLockdownWhitelist";
                case 51:
                    return "checkMobileProvisioning";
                case 52:
                    return "getMobileProvisioningUrl";
                case 53:
                    return "setProvisioningNotificationVisible";
                case 54:
                    return "setAirplaneMode";
                case 55:
                    return "registerNetworkFactory";
                case 56:
                    return "requestBandwidthUpdate";
                case 57:
                    return "unregisterNetworkFactory";
                case 58:
                    return "registerNetworkAgent";
                case 59:
                    return "requestNetwork";
                case 60:
                    return "pendingRequestForNetwork";
                case 61:
                    return "releasePendingNetworkRequest";
                case 62:
                    return "listenForNetwork";
                case 63:
                    return "pendingListenForNetwork";
                case 64:
                    return "releaseNetworkRequest";
                case 65:
                    return "setAcceptUnvalidated";
                case 66:
                    return "setAcceptPartialConnectivity";
                case 67:
                    return "setAvoidUnvalidated";
                case 68:
                    return "startCaptivePortalApp";
                case 69:
                    return "startCaptivePortalAppInternal";
                case 70:
                    return "shouldAvoidBadWifi";
                case 71:
                    return "getMultipathPreference";
                case 72:
                    return "getDefaultRequest";
                case 73:
                    return "getRestoreDefaultNetworkDelay";
                case 74:
                    return "addVpnAddress";
                case 75:
                    return "removeVpnAddress";
                case 76:
                    return "setUnderlyingNetworksForVpn";
                case 77:
                    return "factoryReset";
                case 78:
                    return "startNattKeepalive";
                case 79:
                    return "startNattKeepaliveWithFd";
                case 80:
                    return "startTcpKeepalive";
                case 81:
                    return "stopKeepalive";
                case 82:
                    return "getCaptivePortalServerUrl";
                case 83:
                    return "getNetworkWatchlistConfigHash";
                case 84:
                    return "getConnectionOwnerUid";
                case 85:
                    return "isCallerCurrentAlwaysOnVpnApp";
                case 86:
                    return "isCallerCurrentAlwaysOnVpnLockdownApp";
                case 87:
                    return "getLatestTetheringEntitlementResult";
                case 88:
                    return "registerTetheringEventCallback";
                case 89:
                    return "unregisterTetheringEventCallback";
                case 90:
                    return "startOrGetTestNetworkService";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: android.net.ProxyInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v64, resolved type: com.android.internal.net.VpnConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v69, resolved type: com.android.internal.net.VpnProfile} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v86, resolved type: android.os.Messenger} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v90, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v94, resolved type: android.os.Messenger} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v110, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v114, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v118, resolved type: android.os.Messenger} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v122, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v126, resolved type: android.net.NetworkRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v130, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v134, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v138, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v142, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v146, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v151, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v174, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v178, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v184, resolved type: android.net.ConnectionInfo} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v34, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v98 */
        /* JADX WARNING: type inference failed for: r0v104 */
        /* JADX WARNING: type inference failed for: r0v162 */
        /* JADX WARNING: type inference failed for: r0v168 */
        /* JADX WARNING: type inference failed for: r0v190, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v200 */
        /* JADX WARNING: type inference failed for: r0v201 */
        /* JADX WARNING: type inference failed for: r0v202 */
        /* JADX WARNING: type inference failed for: r0v203 */
        /* JADX WARNING: type inference failed for: r0v204 */
        /* JADX WARNING: type inference failed for: r0v205 */
        /* JADX WARNING: type inference failed for: r0v206 */
        /* JADX WARNING: type inference failed for: r0v207 */
        /* JADX WARNING: type inference failed for: r0v208 */
        /* JADX WARNING: type inference failed for: r0v209 */
        /* JADX WARNING: type inference failed for: r0v210 */
        /* JADX WARNING: type inference failed for: r0v211 */
        /* JADX WARNING: type inference failed for: r0v212 */
        /* JADX WARNING: type inference failed for: r0v213 */
        /* JADX WARNING: type inference failed for: r0v214 */
        /* JADX WARNING: type inference failed for: r0v215 */
        /* JADX WARNING: type inference failed for: r0v216 */
        /* JADX WARNING: type inference failed for: r0v217 */
        /* JADX WARNING: type inference failed for: r0v218 */
        /* JADX WARNING: type inference failed for: r0v219 */
        /* JADX WARNING: type inference failed for: r0v220 */
        /* JADX WARNING: type inference failed for: r0v221 */
        /* JADX WARNING: type inference failed for: r0v222 */
        /* JADX WARNING: type inference failed for: r0v223 */
        /* JADX WARNING: type inference failed for: r0v224 */
        /* JADX WARNING: type inference failed for: r0v225 */
        /* JADX WARNING: type inference failed for: r0v226 */
        /* JADX WARNING: type inference failed for: r0v227 */
        /* JADX WARNING: type inference failed for: r0v228 */
        /* JADX WARNING: type inference failed for: r0v229 */
        /* JADX WARNING: type inference failed for: r0v230 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r21, android.os.Parcel r22, android.os.Parcel r23, int r24) throws android.os.RemoteException {
            /*
                r20 = this;
                r8 = r20
                r9 = r21
                r10 = r22
                r11 = r23
                java.lang.String r12 = "android.net.IConnectivityManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x09c8
                r6 = 0
                r0 = 0
                switch(r9) {
                    case 1: goto L_0x09b1;
                    case 2: goto L_0x098d;
                    case 3: goto L_0x0976;
                    case 4: goto L_0x0952;
                    case 5: goto L_0x0937;
                    case 6: goto L_0x0903;
                    case 7: goto L_0x08f5;
                    case 8: goto L_0x08da;
                    case 9: goto L_0x08cc;
                    case 10: goto L_0x08ba;
                    case 11: goto L_0x08a8;
                    case 12: goto L_0x0891;
                    case 13: goto L_0x0876;
                    case 14: goto L_0x084f;
                    case 15: goto L_0x0828;
                    case 16: goto L_0x081a;
                    case 17: goto L_0x0803;
                    case 18: goto L_0x07f5;
                    case 19: goto L_0x07df;
                    case 20: goto L_0x07c9;
                    case 21: goto L_0x07b3;
                    case 22: goto L_0x07a1;
                    case 23: goto L_0x078f;
                    case 24: goto L_0x0764;
                    case 25: goto L_0x0752;
                    case 26: goto L_0x0744;
                    case 27: goto L_0x0736;
                    case 28: goto L_0x0728;
                    case 29: goto L_0x071a;
                    case 30: goto L_0x070c;
                    case 31: goto L_0x06fe;
                    case 32: goto L_0x06f0;
                    case 33: goto L_0x06d5;
                    case 34: goto L_0x06c3;
                    case 35: goto L_0x06a0;
                    case 36: goto L_0x0689;
                    case 37: goto L_0x066f;
                    case 38: goto L_0x0648;
                    case 39: goto L_0x062e;
                    case 40: goto L_0x0613;
                    case 41: goto L_0x05ec;
                    case 42: goto L_0x05d1;
                    case 43: goto L_0x05b7;
                    case 44: goto L_0x059c;
                    case 45: goto L_0x058e;
                    case 46: goto L_0x0578;
                    case 47: goto L_0x0555;
                    case 48: goto L_0x0543;
                    case 49: goto L_0x0531;
                    case 50: goto L_0x051f;
                    case 51: goto L_0x050d;
                    case 52: goto L_0x04ff;
                    case 53: goto L_0x04e4;
                    case 54: goto L_0x04d1;
                    case 55: goto L_0x04af;
                    case 56: goto L_0x0491;
                    case 57: goto L_0x0477;
                    case 58: goto L_0x040c;
                    case 59: goto L_0x03c3;
                    case 60: goto L_0x038c;
                    case 61: goto L_0x0372;
                    case 62: goto L_0x0337;
                    case 63: goto L_0x030d;
                    case 64: goto L_0x02f3;
                    case 65: goto L_0x02c7;
                    case 66: goto L_0x029b;
                    case 67: goto L_0x0281;
                    case 68: goto L_0x0267;
                    case 69: goto L_0x023d;
                    case 70: goto L_0x022f;
                    case 71: goto L_0x0211;
                    case 72: goto L_0x01fa;
                    case 73: goto L_0x01e8;
                    case 74: goto L_0x01d2;
                    case 75: goto L_0x01bc;
                    case 76: goto L_0x01a6;
                    case 77: goto L_0x019c;
                    case 78: goto L_0x0160;
                    case 79: goto L_0x011d;
                    case 80: goto L_0x00f3;
                    case 81: goto L_0x00d5;
                    case 82: goto L_0x00c7;
                    case 83: goto L_0x00b9;
                    case 84: goto L_0x009b;
                    case 85: goto L_0x008d;
                    case 86: goto L_0x007f;
                    case 87: goto L_0x0054;
                    case 88: goto L_0x003e;
                    case 89: goto L_0x0028;
                    case 90: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r21, r22, r23, r24)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.startOrGetTestNetworkService()
                r23.writeNoException()
                r11.writeStrongBinder(r0)
                return r13
            L_0x0028:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.net.ITetheringEventCallback r0 = android.net.ITetheringEventCallback.Stub.asInterface(r0)
                java.lang.String r1 = r22.readString()
                r8.unregisterTetheringEventCallback(r0, r1)
                r23.writeNoException()
                return r13
            L_0x003e:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r22.readStrongBinder()
                android.net.ITetheringEventCallback r0 = android.net.ITetheringEventCallback.Stub.asInterface(r0)
                java.lang.String r1 = r22.readString()
                r8.registerTetheringEventCallback(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0054:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x006a
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x006b
            L_0x006a:
            L_0x006b:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0073
                r6 = r13
            L_0x0073:
                r2 = r6
                java.lang.String r3 = r22.readString()
                r8.getLatestTetheringEntitlementResult(r1, r0, r2, r3)
                r23.writeNoException()
                return r13
            L_0x007f:
                r10.enforceInterface(r12)
                boolean r0 = r20.isCallerCurrentAlwaysOnVpnLockdownApp()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x008d:
                r10.enforceInterface(r12)
                boolean r0 = r20.isCallerCurrentAlwaysOnVpnApp()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x009b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x00ad
                android.os.Parcelable$Creator<android.net.ConnectionInfo> r0 = android.net.ConnectionInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.ConnectionInfo r0 = (android.net.ConnectionInfo) r0
                goto L_0x00ae
            L_0x00ad:
            L_0x00ae:
                int r1 = r8.getConnectionOwnerUid(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x00b9:
                r10.enforceInterface(r12)
                byte[] r0 = r20.getNetworkWatchlistConfigHash()
                r23.writeNoException()
                r11.writeByteArray(r0)
                return r13
            L_0x00c7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getCaptivePortalServerUrl()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x00d5:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x00e7
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x00e8
            L_0x00e7:
            L_0x00e8:
                int r1 = r22.readInt()
                r8.stopKeepalive(r0, r1)
                r23.writeNoException()
                return r13
            L_0x00f3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0105
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x0106
            L_0x0105:
            L_0x0106:
                java.io.FileDescriptor r1 = r22.readRawFileDescriptor()
                int r2 = r22.readInt()
                android.os.IBinder r3 = r22.readStrongBinder()
                android.net.ISocketKeepaliveCallback r3 = android.net.ISocketKeepaliveCallback.Stub.asInterface(r3)
                r8.startTcpKeepalive(r0, r1, r2, r3)
                r23.writeNoException()
                return r13
            L_0x011d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0130
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
            L_0x012e:
                r1 = r0
                goto L_0x0131
            L_0x0130:
                goto L_0x012e
            L_0x0131:
                java.io.FileDescriptor r14 = r22.readRawFileDescriptor()
                int r15 = r22.readInt()
                int r16 = r22.readInt()
                android.os.IBinder r0 = r22.readStrongBinder()
                android.net.ISocketKeepaliveCallback r17 = android.net.ISocketKeepaliveCallback.Stub.asInterface(r0)
                java.lang.String r18 = r22.readString()
                java.lang.String r19 = r22.readString()
                r0 = r20
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                r7 = r19
                r0.startNattKeepaliveWithFd(r1, r2, r3, r4, r5, r6, r7)
                r23.writeNoException()
                return r13
            L_0x0160:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0173
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
            L_0x0171:
                r1 = r0
                goto L_0x0174
            L_0x0173:
                goto L_0x0171
            L_0x0174:
                int r7 = r22.readInt()
                android.os.IBinder r0 = r22.readStrongBinder()
                android.net.ISocketKeepaliveCallback r14 = android.net.ISocketKeepaliveCallback.Stub.asInterface(r0)
                java.lang.String r15 = r22.readString()
                int r16 = r22.readInt()
                java.lang.String r17 = r22.readString()
                r0 = r20
                r2 = r7
                r3 = r14
                r4 = r15
                r5 = r16
                r6 = r17
                r0.startNattKeepalive(r1, r2, r3, r4, r5, r6)
                r23.writeNoException()
                return r13
            L_0x019c:
                r10.enforceInterface(r12)
                r20.factoryReset()
                r23.writeNoException()
                return r13
            L_0x01a6:
                r10.enforceInterface(r12)
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object[] r0 = r10.createTypedArray(r0)
                android.net.Network[] r0 = (android.net.Network[]) r0
                boolean r1 = r8.setUnderlyingNetworksForVpn(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x01bc:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.removeVpnAddress(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x01d2:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.addVpnAddress(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x01e8:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.getRestoreDefaultNetworkDelay(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x01fa:
                r10.enforceInterface(r12)
                android.net.NetworkRequest r0 = r20.getDefaultRequest()
                r23.writeNoException()
                if (r0 == 0) goto L_0x020d
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x0210
            L_0x020d:
                r11.writeInt(r6)
            L_0x0210:
                return r13
            L_0x0211:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0223
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x0224
            L_0x0223:
            L_0x0224:
                int r1 = r8.getMultipathPreference(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x022f:
                r10.enforceInterface(r12)
                boolean r0 = r20.shouldAvoidBadWifi()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x023d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x024f
                android.os.Parcelable$Creator<android.net.Network> r1 = android.net.Network.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Network r1 = (android.net.Network) r1
                goto L_0x0250
            L_0x024f:
                r1 = r0
            L_0x0250:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x025f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0260
            L_0x025f:
            L_0x0260:
                r8.startCaptivePortalAppInternal(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0267:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0279
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x027a
            L_0x0279:
            L_0x027a:
                r8.startCaptivePortalApp(r0)
                r23.writeNoException()
                return r13
            L_0x0281:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0293
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x0294
            L_0x0293:
            L_0x0294:
                r8.setAvoidUnvalidated(r0)
                r23.writeNoException()
                return r13
            L_0x029b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02ad
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x02ae
            L_0x02ad:
            L_0x02ae:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02b6
                r1 = r13
                goto L_0x02b7
            L_0x02b6:
                r1 = r6
            L_0x02b7:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x02bf
                r6 = r13
            L_0x02bf:
                r2 = r6
                r8.setAcceptPartialConnectivity(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x02c7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02d9
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x02da
            L_0x02d9:
            L_0x02da:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x02e2
                r1 = r13
                goto L_0x02e3
            L_0x02e2:
                r1 = r6
            L_0x02e3:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x02eb
                r6 = r13
            L_0x02eb:
                r2 = r6
                r8.setAcceptUnvalidated(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x02f3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0305
                android.os.Parcelable$Creator<android.net.NetworkRequest> r0 = android.net.NetworkRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.NetworkRequest r0 = (android.net.NetworkRequest) r0
                goto L_0x0306
            L_0x0305:
            L_0x0306:
                r8.releaseNetworkRequest(r0)
                r23.writeNoException()
                return r13
            L_0x030d:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x031f
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r1 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.NetworkCapabilities r1 = (android.net.NetworkCapabilities) r1
                goto L_0x0320
            L_0x031f:
                r1 = r0
            L_0x0320:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x032f
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x0330
            L_0x032f:
            L_0x0330:
                r8.pendingListenForNetwork(r1, r0)
                r23.writeNoException()
                return r13
            L_0x0337:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0349
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r1 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.NetworkCapabilities r1 = (android.net.NetworkCapabilities) r1
                goto L_0x034a
            L_0x0349:
                r1 = r0
            L_0x034a:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0359
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Messenger r0 = (android.os.Messenger) r0
                goto L_0x035a
            L_0x0359:
            L_0x035a:
                android.os.IBinder r2 = r22.readStrongBinder()
                android.net.NetworkRequest r3 = r8.listenForNetwork(r1, r0, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x036e
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0371
            L_0x036e:
                r11.writeInt(r6)
            L_0x0371:
                return r13
            L_0x0372:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0384
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x0385
            L_0x0384:
            L_0x0385:
                r8.releasePendingNetworkRequest(r0)
                r23.writeNoException()
                return r13
            L_0x038c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x039e
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r1 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.NetworkCapabilities r1 = (android.net.NetworkCapabilities) r1
                goto L_0x039f
            L_0x039e:
                r1 = r0
            L_0x039f:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x03ae
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                goto L_0x03af
            L_0x03ae:
            L_0x03af:
                android.net.NetworkRequest r2 = r8.pendingRequestForNetwork(r1, r0)
                r23.writeNoException()
                if (r2 == 0) goto L_0x03bf
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x03c2
            L_0x03bf:
                r11.writeInt(r6)
            L_0x03c2:
                return r13
            L_0x03c3:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x03d5
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r1 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.NetworkCapabilities r1 = (android.net.NetworkCapabilities) r1
                goto L_0x03d6
            L_0x03d5:
                r1 = r0
            L_0x03d6:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x03e6
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Messenger r0 = (android.os.Messenger) r0
            L_0x03e4:
                r2 = r0
                goto L_0x03e7
            L_0x03e6:
                goto L_0x03e4
            L_0x03e7:
                int r7 = r22.readInt()
                android.os.IBinder r14 = r22.readStrongBinder()
                int r15 = r22.readInt()
                r0 = r20
                r3 = r7
                r4 = r14
                r5 = r15
                android.net.NetworkRequest r0 = r0.requestNetwork(r1, r2, r3, r4, r5)
                r23.writeNoException()
                if (r0 == 0) goto L_0x0408
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x040b
            L_0x0408:
                r11.writeInt(r6)
            L_0x040b:
                return r13
            L_0x040c:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x041e
                android.os.Parcelable$Creator<android.os.Messenger> r1 = android.os.Messenger.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Messenger r1 = (android.os.Messenger) r1
                goto L_0x041f
            L_0x041e:
                r1 = r0
            L_0x041f:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x042e
                android.os.Parcelable$Creator<android.net.NetworkInfo> r2 = android.net.NetworkInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.net.NetworkInfo r2 = (android.net.NetworkInfo) r2
                goto L_0x042f
            L_0x042e:
                r2 = r0
            L_0x042f:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x043e
                android.os.Parcelable$Creator<android.net.LinkProperties> r3 = android.net.LinkProperties.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.net.LinkProperties r3 = (android.net.LinkProperties) r3
                goto L_0x043f
            L_0x043e:
                r3 = r0
            L_0x043f:
                int r4 = r22.readInt()
                if (r4 == 0) goto L_0x044e
                android.os.Parcelable$Creator<android.net.NetworkCapabilities> r4 = android.net.NetworkCapabilities.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r10)
                android.net.NetworkCapabilities r4 = (android.net.NetworkCapabilities) r4
                goto L_0x044f
            L_0x044e:
                r4 = r0
            L_0x044f:
                int r14 = r22.readInt()
                int r5 = r22.readInt()
                if (r5 == 0) goto L_0x0463
                android.os.Parcelable$Creator<android.net.NetworkMisc> r0 = android.net.NetworkMisc.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.NetworkMisc r0 = (android.net.NetworkMisc) r0
            L_0x0461:
                r6 = r0
                goto L_0x0464
            L_0x0463:
                goto L_0x0461
            L_0x0464:
                int r15 = r22.readInt()
                r0 = r20
                r5 = r14
                r7 = r15
                int r0 = r0.registerNetworkAgent(r1, r2, r3, r4, r5, r6, r7)
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0477:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0489
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Messenger r0 = (android.os.Messenger) r0
                goto L_0x048a
            L_0x0489:
            L_0x048a:
                r8.unregisterNetworkFactory(r0)
                r23.writeNoException()
                return r13
            L_0x0491:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04a3
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x04a4
            L_0x04a3:
            L_0x04a4:
                boolean r1 = r8.requestBandwidthUpdate(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x04af:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x04c1
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Messenger r0 = (android.os.Messenger) r0
                goto L_0x04c2
            L_0x04c1:
            L_0x04c2:
                java.lang.String r1 = r22.readString()
                int r2 = r8.registerNetworkFactory(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x04d1:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x04dc
                r6 = r13
            L_0x04dc:
                r0 = r6
                r8.setAirplaneMode(r0)
                r23.writeNoException()
                return r13
            L_0x04e4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x04ef
                r6 = r13
            L_0x04ef:
                r0 = r6
                int r1 = r22.readInt()
                java.lang.String r2 = r22.readString()
                r8.setProvisioningNotificationVisible(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x04ff:
                r10.enforceInterface(r12)
                java.lang.String r0 = r20.getMobileProvisioningUrl()
                r23.writeNoException()
                r11.writeString(r0)
                return r13
            L_0x050d:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r8.checkMobileProvisioning(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x051f:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.util.List r1 = r8.getVpnLockdownWhitelist(r0)
                r23.writeNoException()
                r11.writeStringList(r1)
                return r13
            L_0x0531:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.isVpnLockdownEnabled(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0543:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r8.getAlwaysOnVpnPackage(r0)
                r23.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x0555:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0568
                r6 = r13
            L_0x0568:
                r2 = r6
                java.util.ArrayList r3 = r22.createStringArrayList()
                boolean r4 = r8.setAlwaysOnVpnPackage(r0, r1, r2, r3)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x0578:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r22.readString()
                boolean r2 = r8.isAlwaysOnVpnPackageSupported(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x058e:
                r10.enforceInterface(r12)
                boolean r0 = r20.updateLockdownVpn()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x059c:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                com.android.internal.net.LegacyVpnInfo r1 = r8.getLegacyVpnInfo(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x05b3
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x05b6
            L_0x05b3:
                r11.writeInt(r6)
            L_0x05b6:
                return r13
            L_0x05b7:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x05c9
                android.os.Parcelable$Creator<com.android.internal.net.VpnProfile> r0 = com.android.internal.net.VpnProfile.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                com.android.internal.net.VpnProfile r0 = (com.android.internal.net.VpnProfile) r0
                goto L_0x05ca
            L_0x05c9:
            L_0x05ca:
                r8.startLegacyVpn(r0)
                r23.writeNoException()
                return r13
            L_0x05d1:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                com.android.internal.net.VpnConfig r1 = r8.getVpnConfig(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x05e8
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x05eb
            L_0x05e8:
                r11.writeInt(r6)
            L_0x05eb:
                return r13
            L_0x05ec:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x05fe
                android.os.Parcelable$Creator<com.android.internal.net.VpnConfig> r0 = com.android.internal.net.VpnConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                com.android.internal.net.VpnConfig r0 = (com.android.internal.net.VpnConfig) r0
                goto L_0x05ff
            L_0x05fe:
            L_0x05ff:
                android.os.ParcelFileDescriptor r1 = r8.establishVpn(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x060f
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0612
            L_0x060f:
                r11.writeInt(r6)
            L_0x0612:
                return r13
            L_0x0613:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0626
                r6 = r13
            L_0x0626:
                r2 = r6
                r8.setVpnPackageAuthorization(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x062e:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                boolean r3 = r8.prepareVpn(r0, r1, r2)
                r23.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0648:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x065a
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x065b
            L_0x065a:
            L_0x065b:
                android.net.ProxyInfo r1 = r8.getProxyForNetwork(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x066b
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x066e
            L_0x066b:
                r11.writeInt(r6)
            L_0x066e:
                return r13
            L_0x066f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0681
                android.os.Parcelable$Creator<android.net.ProxyInfo> r0 = android.net.ProxyInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.ProxyInfo r0 = (android.net.ProxyInfo) r0
                goto L_0x0682
            L_0x0681:
            L_0x0682:
                r8.setGlobalProxy(r0)
                r23.writeNoException()
                return r13
            L_0x0689:
                r10.enforceInterface(r12)
                android.net.ProxyInfo r0 = r20.getGlobalProxy()
                r23.writeNoException()
                if (r0 == 0) goto L_0x069c
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x069f
            L_0x069c:
                r11.writeInt(r6)
            L_0x069f:
                return r13
            L_0x06a0:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06b2
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x06b3
            L_0x06b2:
            L_0x06b3:
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x06bb
                r6 = r13
            L_0x06bb:
                r1 = r6
                r8.reportNetworkConnectivity(r0, r1)
                r23.writeNoException()
                return r13
            L_0x06c3:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                r8.reportInetCondition(r0, r1)
                r23.writeNoException()
                return r13
            L_0x06d5:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x06e0
                r6 = r13
            L_0x06e0:
                r0 = r6
                java.lang.String r1 = r22.readString()
                int r2 = r8.setUsbTethering(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x06f0:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetherableBluetoothRegexs()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x06fe:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetherableWifiRegexs()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x070c:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetherableUsbRegexs()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x071a:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetheredDhcpRanges()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x0728:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetheringErroredIfaces()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x0736:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetheredIfaces()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x0744:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r20.getTetherableIfaces()
                r23.writeNoException()
                r11.writeStringArray(r0)
                return r13
            L_0x0752:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                java.lang.String r1 = r22.readString()
                r8.stopTethering(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0764:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x077a
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x077b
            L_0x077a:
            L_0x077b:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0783
                r6 = r13
            L_0x0783:
                r2 = r6
                java.lang.String r3 = r22.readString()
                r8.startTethering(r1, r0, r2, r3)
                r23.writeNoException()
                return r13
            L_0x078f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                boolean r1 = r8.isTetheringSupported(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x07a1:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r8.getLastTetherError(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x07b3:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r8.untether(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x07c9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                java.lang.String r1 = r22.readString()
                int r2 = r8.tether(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x07df:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                byte[] r1 = r22.createByteArray()
                boolean r2 = r8.requestRouteToHostAddress(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x07f5:
                r10.enforceInterface(r12)
                boolean r0 = r20.isActiveNetworkMetered()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0803:
                r10.enforceInterface(r12)
                android.net.NetworkQuotaInfo r0 = r20.getActiveNetworkQuotaInfo()
                r23.writeNoException()
                if (r0 == 0) goto L_0x0816
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x0819
            L_0x0816:
                r11.writeInt(r6)
            L_0x0819:
                return r13
            L_0x081a:
                r10.enforceInterface(r12)
                android.net.NetworkState[] r0 = r20.getAllNetworkState()
                r23.writeNoException()
                r11.writeTypedArray(r0, r13)
                return r13
            L_0x0828:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x083a
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x083b
            L_0x083a:
            L_0x083b:
                android.net.NetworkCapabilities r1 = r8.getNetworkCapabilities(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x084b
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x084e
            L_0x084b:
                r11.writeInt(r6)
            L_0x084e:
                return r13
            L_0x084f:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0861
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x0862
            L_0x0861:
            L_0x0862:
                android.net.LinkProperties r1 = r8.getLinkProperties(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x0872
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0875
            L_0x0872:
                r11.writeInt(r6)
            L_0x0875:
                return r13
            L_0x0876:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.net.LinkProperties r1 = r8.getLinkPropertiesForType(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x088d
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0890
            L_0x088d:
                r11.writeInt(r6)
            L_0x0890:
                return r13
            L_0x0891:
                r10.enforceInterface(r12)
                android.net.LinkProperties r0 = r20.getActiveLinkProperties()
                r23.writeNoException()
                if (r0 == 0) goto L_0x08a4
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x08a7
            L_0x08a4:
                r11.writeInt(r6)
            L_0x08a7:
                return r13
            L_0x08a8:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                boolean r1 = r8.isNetworkSupported(r0)
                r23.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x08ba:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.net.NetworkCapabilities[] r1 = r8.getDefaultNetworkCapabilitiesForUser(r0)
                r23.writeNoException()
                r11.writeTypedArray(r1, r13)
                return r13
            L_0x08cc:
                r10.enforceInterface(r12)
                android.net.Network[] r0 = r20.getAllNetworks()
                r23.writeNoException()
                r11.writeTypedArray(r0, r13)
                return r13
            L_0x08da:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.net.Network r1 = r8.getNetworkForType(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x08f1
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x08f4
            L_0x08f1:
                r11.writeInt(r6)
            L_0x08f4:
                return r13
            L_0x08f5:
                r10.enforceInterface(r12)
                android.net.NetworkInfo[] r0 = r20.getAllNetworkInfo()
                r23.writeNoException()
                r11.writeTypedArray(r0, r13)
                return r13
            L_0x0903:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0915
                android.os.Parcelable$Creator<android.net.Network> r0 = android.net.Network.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.net.Network r0 = (android.net.Network) r0
                goto L_0x0916
            L_0x0915:
            L_0x0916:
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x0922
                r2 = r13
                goto L_0x0923
            L_0x0922:
                r2 = r6
            L_0x0923:
                android.net.NetworkInfo r3 = r8.getNetworkInfoForUid(r0, r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0933
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0936
            L_0x0933:
                r11.writeInt(r6)
            L_0x0936:
                return r13
            L_0x0937:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                android.net.NetworkInfo r1 = r8.getNetworkInfo(r0)
                r23.writeNoException()
                if (r1 == 0) goto L_0x094e
                r11.writeInt(r13)
                r1.writeToParcel(r11, r13)
                goto L_0x0951
            L_0x094e:
                r11.writeInt(r6)
            L_0x0951:
                return r13
            L_0x0952:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x0961
                r1 = r13
                goto L_0x0962
            L_0x0961:
                r1 = r6
            L_0x0962:
                android.net.NetworkInfo r2 = r8.getActiveNetworkInfoForUid(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x0972
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0975
            L_0x0972:
                r11.writeInt(r6)
            L_0x0975:
                return r13
            L_0x0976:
                r10.enforceInterface(r12)
                android.net.NetworkInfo r0 = r20.getActiveNetworkInfo()
                r23.writeNoException()
                if (r0 == 0) goto L_0x0989
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x098c
            L_0x0989:
                r11.writeInt(r6)
            L_0x098c:
                return r13
            L_0x098d:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                int r1 = r22.readInt()
                if (r1 == 0) goto L_0x099c
                r1 = r13
                goto L_0x099d
            L_0x099c:
                r1 = r6
            L_0x099d:
                android.net.Network r2 = r8.getActiveNetworkForUid(r0, r1)
                r23.writeNoException()
                if (r2 == 0) goto L_0x09ad
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x09b0
            L_0x09ad:
                r11.writeInt(r6)
            L_0x09b0:
                return r13
            L_0x09b1:
                r10.enforceInterface(r12)
                android.net.Network r0 = r20.getActiveNetwork()
                r23.writeNoException()
                if (r0 == 0) goto L_0x09c4
                r11.writeInt(r13)
                r0.writeToParcel(r11, r13)
                goto L_0x09c7
            L_0x09c4:
                r11.writeInt(r6)
            L_0x09c7:
                return r13
            L_0x09c8:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.IConnectivityManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IConnectivityManager {
            public static IConnectivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public Network getActiveNetwork() throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetwork();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Network _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Network getActiveNetworkForUid(int uid, boolean ignoreBlocked) throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(ignoreBlocked);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkForUid(uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Network _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkInfo getActiveNetworkInfo() throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkInfo getActiveNetworkInfoForUid(int uid, boolean ignoreBlocked) throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(ignoreBlocked);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkInfoForUid(uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkInfo getNetworkInfo(int networkType) throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkInfo(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkInfo getNetworkInfoForUid(Network network, int uid, boolean ignoreBlocked) throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(uid);
                    _data.writeInt(ignoreBlocked);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkInfoForUid(network, uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworkInfo();
                    }
                    _reply.readException();
                    NetworkInfo[] _result = (NetworkInfo[]) _reply.createTypedArray(NetworkInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Network getNetworkForType(int networkType) throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkForType(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Network _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Network[] getAllNetworks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworks();
                    }
                    _reply.readException();
                    Network[] _result = (Network[]) _reply.createTypedArray(Network.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultNetworkCapabilitiesForUser(userId);
                    }
                    _reply.readException();
                    NetworkCapabilities[] _result = (NetworkCapabilities[]) _reply.createTypedArray(NetworkCapabilities.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNetworkSupported(int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkSupported(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LinkProperties getActiveLinkProperties() throws RemoteException {
                LinkProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveLinkProperties();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LinkProperties _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LinkProperties getLinkPropertiesForType(int networkType) throws RemoteException {
                LinkProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkPropertiesForType(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LinkProperties _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LinkProperties getLinkProperties(Network network) throws RemoteException {
                LinkProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkProperties(network);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LinkProperties _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkCapabilities getNetworkCapabilities(Network network) throws RemoteException {
                NetworkCapabilities _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkCapabilities(network);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkCapabilities.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkCapabilities _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkState[] getAllNetworkState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworkState();
                    }
                    _reply.readException();
                    NetworkState[] _result = (NetworkState[]) _reply.createTypedArray(NetworkState.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
                NetworkQuotaInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkQuotaInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkQuotaInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkQuotaInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isActiveNetworkMetered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActiveNetworkMetered();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean requestRouteToHostAddress(int networkType, byte[] hostAddress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeByteArray(hostAddress);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestRouteToHostAddress(networkType, hostAddress);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int tether(String iface, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(callerPkg);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().tether(iface, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int untether(String iface, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(callerPkg);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().untether(iface, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLastTetherError(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastTetherError(iface);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTetheringSupported(String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPkg);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTetheringSupported(callerPkg);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startTethering(int type, ResultReceiver receiver, boolean showProvisioningUi, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (receiver != null) {
                        _data.writeInt(1);
                        receiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(showProvisioningUi);
                    _data.writeString(callerPkg);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startTethering(type, receiver, showProvisioningUi, callerPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopTethering(int type, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(callerPkg);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopTethering(type, callerPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetherableIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetheredIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheredIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetheringErroredIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheringErroredIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetheredDhcpRanges() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheredDhcpRanges();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetherableUsbRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableUsbRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetherableWifiRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableWifiRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTetherableBluetoothRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableBluetoothRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setUsbTethering(boolean enable, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    _data.writeString(callerPkg);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUsbTethering(enable, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportInetCondition(int networkType, int percentage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeInt(percentage);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportInetCondition(networkType, percentage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportNetworkConnectivity(Network network, boolean hasConnectivity) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(hasConnectivity);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportNetworkConnectivity(network, hasConnectivity);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ProxyInfo getGlobalProxy() throws RemoteException {
                ProxyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalProxy();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProxyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ProxyInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGlobalProxy(ProxyInfo p) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (p != null) {
                        _data.writeInt(1);
                        p.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGlobalProxy(p);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ProxyInfo getProxyForNetwork(Network nework) throws RemoteException {
                ProxyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (nework != null) {
                        _data.writeInt(1);
                        nework.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProxyForNetwork(nework);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProxyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ProxyInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean prepareVpn(String oldPackage, String newPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldPackage);
                    _data.writeString(newPackage);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().prepareVpn(oldPackage, newPackage, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVpnPackageAuthorization(String packageName, int userId, boolean authorized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(authorized);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVpnPackageAuthorization(packageName, userId, authorized);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor establishVpn(VpnConfig config) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().establishVpn(config);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VpnConfig getVpnConfig(int userId) throws RemoteException {
                VpnConfig _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVpnConfig(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VpnConfig.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VpnConfig _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startLegacyVpn(VpnProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profile != null) {
                        _data.writeInt(1);
                        profile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startLegacyVpn(profile);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
                LegacyVpnInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLegacyVpnInfo(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LegacyVpnInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LegacyVpnInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean updateLockdownVpn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateLockdownVpn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAlwaysOnVpnPackageSupported(userId, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setAlwaysOnVpnPackage(int userId, String packageName, boolean lockdown, List<String> lockdownWhitelist) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeInt(lockdown);
                    _data.writeStringList(lockdownWhitelist);
                    boolean z = false;
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAlwaysOnVpnPackage(userId, packageName, lockdown, lockdownWhitelist);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlwaysOnVpnPackage(userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVpnLockdownEnabled(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getVpnLockdownWhitelist(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVpnLockdownWhitelist(userId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkMobileProvisioning(int suggestedTimeOutMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(suggestedTimeOutMs);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkMobileProvisioning(suggestedTimeOutMs);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getMobileProvisioningUrl() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMobileProvisioningUrl();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setProvisioningNotificationVisible(boolean visible, int networkType, String action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    _data.writeInt(networkType);
                    _data.writeString(action);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProvisioningNotificationVisible(visible, networkType, action);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAirplaneMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAirplaneMode(enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int registerNetworkFactory(Messenger messenger, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messenger != null) {
                        _data.writeInt(1);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(name);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerNetworkFactory(messenger, name);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean requestBandwidthUpdate(Network network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBandwidthUpdate(network);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterNetworkFactory(Messenger messenger) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messenger != null) {
                        _data.writeInt(1);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterNetworkFactory(messenger);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc, int factorySerialNumber) throws RemoteException {
                Messenger messenger2 = messenger;
                NetworkInfo networkInfo = ni;
                LinkProperties linkProperties = lp;
                NetworkCapabilities networkCapabilities = nc;
                NetworkMisc networkMisc = misc;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messenger2 != null) {
                        _data.writeInt(1);
                        messenger2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (networkInfo != null) {
                        _data.writeInt(1);
                        networkInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (linkProperties != null) {
                        _data.writeInt(1);
                        linkProperties.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (networkCapabilities != null) {
                        _data.writeInt(1);
                        networkCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(score);
                    if (networkMisc != null) {
                        _data.writeInt(1);
                        networkMisc.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(factorySerialNumber);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerNetworkAgent(messenger, ni, lp, nc, score, misc, factorySerialNumber);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkRequest requestNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, int timeoutSec, IBinder binder, int legacy) throws RemoteException {
                NetworkRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkCapabilities != null) {
                        _data.writeInt(1);
                        networkCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (messenger != null) {
                        _data.writeInt(1);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(timeoutSec);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(legacy);
                    if (!this.mRemote.transact(59, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestNetwork(networkCapabilities, messenger, timeoutSec, binder, legacy);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkRequest _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkRequest pendingRequestForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
                NetworkRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkCapabilities != null) {
                        _data.writeInt(1);
                        networkCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (operation != null) {
                        _data.writeInt(1);
                        operation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(60, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pendingRequestForNetwork(networkCapabilities, operation);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkRequest _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releasePendingNetworkRequest(PendingIntent operation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (operation != null) {
                        _data.writeInt(1);
                        operation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releasePendingNetworkRequest(operation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkRequest listenForNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, IBinder binder) throws RemoteException {
                NetworkRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkCapabilities != null) {
                        _data.writeInt(1);
                        networkCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (messenger != null) {
                        _data.writeInt(1);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(binder);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listenForNetwork(networkCapabilities, messenger, binder);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkRequest _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkCapabilities != null) {
                        _data.writeInt(1);
                        networkCapabilities.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (operation != null) {
                        _data.writeInt(1);
                        operation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pendingListenForNetwork(networkCapabilities, operation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkRequest != null) {
                        _data.writeInt(1);
                        networkRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseNetworkRequest(networkRequest);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAcceptUnvalidated(Network network, boolean accept, boolean always) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(accept);
                    _data.writeInt(always);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAcceptUnvalidated(network, accept, always);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAcceptPartialConnectivity(Network network, boolean accept, boolean always) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(accept);
                    _data.writeInt(always);
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAcceptPartialConnectivity(network, accept, always);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAvoidUnvalidated(Network network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAvoidUnvalidated(network);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startCaptivePortalApp(Network network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startCaptivePortalApp(network);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startCaptivePortalAppInternal(Network network, Bundle appExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (appExtras != null) {
                        _data.writeInt(1);
                        appExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startCaptivePortalAppInternal(network, appExtras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldAvoidBadWifi() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldAvoidBadWifi();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMultipathPreference(Network Network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (Network != null) {
                        _data.writeInt(1);
                        Network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMultipathPreference(Network);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkRequest getDefaultRequest() throws RemoteException {
                NetworkRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultRequest();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkRequest _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRestoreDefaultNetworkDelay(int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestoreDefaultNetworkDelay(networkType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prefixLength);
                    boolean z = false;
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addVpnAddress(address, prefixLength);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prefixLength);
                    boolean z = false;
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeVpnAddress(address, prefixLength);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    _data.writeTypedArray(networks, 0);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUnderlyingNetworksForVpn(networks);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().factoryReset();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startNattKeepalive(Network network, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, int srcPort, String dstAddr) throws RemoteException {
                Network network2 = network;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network2 != null) {
                        _data.writeInt(1);
                        network2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(intervalSeconds);
                        _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    } catch (Throwable th) {
                        th = th;
                        String str = srcAddr;
                        int i = srcPort;
                        String str2 = dstAddr;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(srcAddr);
                        try {
                            _data.writeInt(srcPort);
                        } catch (Throwable th2) {
                            th = th2;
                            String str22 = dstAddr;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(dstAddr);
                            if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().startNattKeepalive(network, intervalSeconds, cb, srcAddr, srcPort, dstAddr);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i2 = srcPort;
                        String str222 = dstAddr;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i3 = intervalSeconds;
                    String str3 = srcAddr;
                    int i22 = srcPort;
                    String str2222 = dstAddr;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void startNattKeepaliveWithFd(Network network, FileDescriptor fd, int resourceId, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, String dstAddr) throws RemoteException {
                Network network2 = network;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network2 != null) {
                        _data.writeInt(1);
                        network2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeRawFileDescriptor(fd);
                        try {
                            _data.writeInt(resourceId);
                        } catch (Throwable th) {
                            th = th;
                            int i = intervalSeconds;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(intervalSeconds);
                            _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                            _data.writeString(srcAddr);
                            _data.writeString(dstAddr);
                            if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().startNattKeepaliveWithFd(network, fd, resourceId, intervalSeconds, cb, srcAddr, dstAddr);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i2 = resourceId;
                        int i3 = intervalSeconds;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    FileDescriptor fileDescriptor = fd;
                    int i22 = resourceId;
                    int i32 = intervalSeconds;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void startTcpKeepalive(Network network, FileDescriptor fd, int intervalSeconds, ISocketKeepaliveCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeRawFileDescriptor(fd);
                    _data.writeInt(intervalSeconds);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startTcpKeepalive(network, fd, intervalSeconds, cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopKeepalive(Network network, int slot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        _data.writeInt(1);
                        network.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(slot);
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopKeepalive(network, slot);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCaptivePortalServerUrl() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCaptivePortalServerUrl();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkWatchlistConfigHash();
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectionInfo != null) {
                        _data.writeInt(1);
                        connectionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionOwnerUid(connectionInfo);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnApp();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(86, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnLockdownApp();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getLatestTetheringEntitlementResult(int type, ResultReceiver receiver, boolean showEntitlementUi, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (receiver != null) {
                        _data.writeInt(1);
                        receiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(showEntitlementUi);
                    _data.writeString(callerPkg);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getLatestTetheringEntitlementResult(type, receiver, showEntitlementUi, callerPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callerPkg);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerTetheringEventCallback(callback, callerPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callerPkg);
                    if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterTetheringEventCallback(callback, callerPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBinder startOrGetTestNetworkService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startOrGetTestNetworkService();
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IConnectivityManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IConnectivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
