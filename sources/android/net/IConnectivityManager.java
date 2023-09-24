package android.net;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.net.ISocketKeepaliveCallback;
import android.net.ITetheringEventCallback;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Messenger;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.p007os.ResultReceiver;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import java.io.FileDescriptor;
import java.util.List;

/* loaded from: classes3.dex */
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

    /* loaded from: classes3.dex */
    public static class Default implements IConnectivityManager {
        @Override // android.net.IConnectivityManager
        public Network getActiveNetwork() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public Network getActiveNetworkForUid(int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkInfo getActiveNetworkInfo() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkInfo getActiveNetworkInfoForUid(int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkInfo getNetworkInfo(int networkType) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkInfo getNetworkInfoForUid(Network network, int uid, boolean ignoreBlocked) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public Network getNetworkForType(int networkType) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public Network[] getAllNetworks() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public boolean isNetworkSupported(int networkType) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public LinkProperties getActiveLinkProperties() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public LinkProperties getLinkPropertiesForType(int networkType) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public LinkProperties getLinkProperties(Network network) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkCapabilities getNetworkCapabilities(Network network) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkState[] getAllNetworkState() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public boolean isActiveNetworkMetered() throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean requestRouteToHostAddress(int networkType, byte[] hostAddress) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public int tether(String iface, String callerPkg) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public int untether(String iface, String callerPkg) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public int getLastTetherError(String iface) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public boolean isTetheringSupported(String callerPkg) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public void startTethering(int type, ResultReceiver receiver, boolean showProvisioningUi, String callerPkg) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void stopTethering(int type, String callerPkg) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetherableIfaces() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetheredIfaces() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetheringErroredIfaces() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetheredDhcpRanges() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetherableUsbRegexs() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetherableWifiRegexs() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public String[] getTetherableBluetoothRegexs() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public int setUsbTethering(boolean enable, String callerPkg) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public void reportInetCondition(int networkType, int percentage) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void reportNetworkConnectivity(Network network, boolean hasConnectivity) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public ProxyInfo getGlobalProxy() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public void setGlobalProxy(ProxyInfo p) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public ProxyInfo getProxyForNetwork(Network nework) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public boolean prepareVpn(String oldPackage, String newPackage, int userId) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public void setVpnPackageAuthorization(String packageName, int userId, boolean authorized) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public ParcelFileDescriptor establishVpn(VpnConfig config) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public VpnConfig getVpnConfig(int userId) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public void startLegacyVpn(VpnProfile profile) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public boolean updateLockdownVpn() throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean setAlwaysOnVpnPackage(int userId, String packageName, boolean lockdown, List<String> lockdownWhitelist) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public List<String> getVpnLockdownWhitelist(int userId) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public int checkMobileProvisioning(int suggestedTimeOutMs) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public String getMobileProvisioningUrl() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public void setProvisioningNotificationVisible(boolean visible, int networkType, String action) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void setAirplaneMode(boolean enable) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public int registerNetworkFactory(Messenger messenger, String name) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public boolean requestBandwidthUpdate(Network network) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public void unregisterNetworkFactory(Messenger messenger) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc, int factorySerialNumber) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public NetworkRequest requestNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, int timeoutSec, IBinder binder, int legacy) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public NetworkRequest pendingRequestForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public void releasePendingNetworkRequest(PendingIntent operation) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public NetworkRequest listenForNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, IBinder binder) throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent operation) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void setAcceptUnvalidated(Network network, boolean accept, boolean always) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void setAcceptPartialConnectivity(Network network, boolean accept, boolean always) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void setAvoidUnvalidated(Network network) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void startCaptivePortalApp(Network network) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void startCaptivePortalAppInternal(Network network, Bundle appExtras) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public boolean shouldAvoidBadWifi() throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public int getMultipathPreference(Network Network) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public NetworkRequest getDefaultRequest() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public int getRestoreDefaultNetworkDelay(int networkType) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public void factoryReset() throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void startNattKeepalive(Network network, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, int srcPort, String dstAddr) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void startNattKeepaliveWithFd(Network network, FileDescriptor fd, int resourceId, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, String dstAddr) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void startTcpKeepalive(Network network, FileDescriptor fd, int intervalSeconds, ISocketKeepaliveCallback cb) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void stopKeepalive(Network network, int slot) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public String getCaptivePortalServerUrl() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
            return null;
        }

        @Override // android.net.IConnectivityManager
        public int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException {
            return 0;
        }

        @Override // android.net.IConnectivityManager
        public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
            return false;
        }

        @Override // android.net.IConnectivityManager
        public void getLatestTetheringEntitlementResult(int type, ResultReceiver receiver, boolean showEntitlementUi, String callerPkg) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
        }

        @Override // android.net.IConnectivityManager
        public IBinder startOrGetTestNetworkService() throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof IConnectivityManager)) {
                return (IConnectivityManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Messenger _arg0;
            NetworkInfo _arg1;
            LinkProperties _arg2;
            NetworkCapabilities _arg3;
            NetworkCapabilities _arg02;
            NetworkCapabilities _arg03;
            NetworkCapabilities _arg04;
            NetworkCapabilities _arg05;
            Network _arg06;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Network _result = getActiveNetwork();
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    boolean _arg12 = data.readInt() != 0;
                    Network _result2 = getActiveNetworkForUid(_arg07, _arg12);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkInfo _result3 = getActiveNetworkInfo();
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    boolean _arg13 = data.readInt() != 0;
                    NetworkInfo _result4 = getActiveNetworkInfoForUid(_arg08, _arg13);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg09 = data.readInt();
                    NetworkInfo _result5 = getNetworkInfo(_arg09);
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg010 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    int _arg14 = data.readInt();
                    boolean _arg22 = data.readInt() != 0;
                    NetworkInfo _result6 = getNetworkInfoForUid(_arg010, _arg14, _arg22);
                    reply.writeNoException();
                    if (_result6 != null) {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkInfo[] _result7 = getAllNetworkInfo();
                    reply.writeNoException();
                    reply.writeTypedArray(_result7, 1);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg011 = data.readInt();
                    Network _result8 = getNetworkForType(_arg011);
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    Network[] _result9 = getAllNetworks();
                    reply.writeNoException();
                    reply.writeTypedArray(_result9, 1);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    NetworkCapabilities[] _result10 = getDefaultNetworkCapabilitiesForUser(_arg012);
                    reply.writeNoException();
                    reply.writeTypedArray(_result10, 1);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg013 = data.readInt();
                    boolean isNetworkSupported = isNetworkSupported(_arg013);
                    reply.writeNoException();
                    reply.writeInt(isNetworkSupported ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    LinkProperties _result11 = getActiveLinkProperties();
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        _result11.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg014 = data.readInt();
                    LinkProperties _result12 = getLinkPropertiesForType(_arg014);
                    reply.writeNoException();
                    if (_result12 != null) {
                        reply.writeInt(1);
                        _result12.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg015 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    LinkProperties _result13 = getLinkProperties(_arg015);
                    reply.writeNoException();
                    if (_result13 != null) {
                        reply.writeInt(1);
                        _result13.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg016 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    NetworkCapabilities _result14 = getNetworkCapabilities(_arg016);
                    reply.writeNoException();
                    if (_result14 != null) {
                        reply.writeInt(1);
                        _result14.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkState[] _result15 = getAllNetworkState();
                    reply.writeNoException();
                    reply.writeTypedArray(_result15, 1);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkQuotaInfo _result16 = getActiveNetworkQuotaInfo();
                    reply.writeNoException();
                    if (_result16 != null) {
                        reply.writeInt(1);
                        _result16.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isActiveNetworkMetered = isActiveNetworkMetered();
                    reply.writeNoException();
                    reply.writeInt(isActiveNetworkMetered ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg017 = data.readInt();
                    byte[] _arg15 = data.createByteArray();
                    boolean requestRouteToHostAddress = requestRouteToHostAddress(_arg017, _arg15);
                    reply.writeNoException();
                    reply.writeInt(requestRouteToHostAddress ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg018 = data.readString();
                    String _arg16 = data.readString();
                    int _result17 = tether(_arg018, _arg16);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    String _arg17 = data.readString();
                    int _result18 = untether(_arg019, _arg17);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    int _result19 = getLastTetherError(_arg020);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg021 = data.readString();
                    boolean isTetheringSupported = isTetheringSupported(_arg021);
                    reply.writeNoException();
                    reply.writeInt(isTetheringSupported ? 1 : 0);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg022 = data.readInt();
                    ResultReceiver _arg18 = data.readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel(data) : null;
                    boolean _arg23 = data.readInt() != 0;
                    String _arg32 = data.readString();
                    startTethering(_arg022, _arg18, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg023 = data.readInt();
                    String _arg19 = data.readString();
                    stopTethering(_arg023, _arg19);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result20 = getTetherableIfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result20);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result21 = getTetheredIfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result21);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result22 = getTetheringErroredIfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result22);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result23 = getTetheredDhcpRanges();
                    reply.writeNoException();
                    reply.writeStringArray(_result23);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result24 = getTetherableUsbRegexs();
                    reply.writeNoException();
                    reply.writeStringArray(_result24);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result25 = getTetherableWifiRegexs();
                    reply.writeNoException();
                    reply.writeStringArray(_result25);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result26 = getTetherableBluetoothRegexs();
                    reply.writeNoException();
                    reply.writeStringArray(_result26);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg024 = data.readInt() != 0;
                    String _arg110 = data.readString();
                    int _result27 = setUsbTethering(_arg024, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    int _arg111 = data.readInt();
                    reportInetCondition(_arg025, _arg111);
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg026 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    boolean _arg112 = data.readInt() != 0;
                    reportNetworkConnectivity(_arg026, _arg112);
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    ProxyInfo _result28 = getGlobalProxy();
                    reply.writeNoException();
                    if (_result28 != null) {
                        reply.writeInt(1);
                        _result28.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    ProxyInfo _arg027 = data.readInt() != 0 ? ProxyInfo.CREATOR.createFromParcel(data) : null;
                    setGlobalProxy(_arg027);
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg028 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    ProxyInfo _result29 = getProxyForNetwork(_arg028);
                    reply.writeNoException();
                    if (_result29 != null) {
                        reply.writeInt(1);
                        _result29.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg029 = data.readString();
                    String _arg113 = data.readString();
                    int _arg24 = data.readInt();
                    boolean prepareVpn = prepareVpn(_arg029, _arg113, _arg24);
                    reply.writeNoException();
                    reply.writeInt(prepareVpn ? 1 : 0);
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg030 = data.readString();
                    int _arg114 = data.readInt();
                    boolean _arg25 = data.readInt() != 0;
                    setVpnPackageAuthorization(_arg030, _arg114, _arg25);
                    reply.writeNoException();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    VpnConfig _arg031 = data.readInt() != 0 ? VpnConfig.CREATOR.createFromParcel(data) : null;
                    ParcelFileDescriptor _result30 = establishVpn(_arg031);
                    reply.writeNoException();
                    if (_result30 != null) {
                        reply.writeInt(1);
                        _result30.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg032 = data.readInt();
                    VpnConfig _result31 = getVpnConfig(_arg032);
                    reply.writeNoException();
                    if (_result31 != null) {
                        reply.writeInt(1);
                        _result31.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    VpnProfile _arg033 = data.readInt() != 0 ? VpnProfile.CREATOR.createFromParcel(data) : null;
                    startLegacyVpn(_arg033);
                    reply.writeNoException();
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg034 = data.readInt();
                    LegacyVpnInfo _result32 = getLegacyVpnInfo(_arg034);
                    reply.writeNoException();
                    if (_result32 != null) {
                        reply.writeInt(1);
                        _result32.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    boolean updateLockdownVpn = updateLockdownVpn();
                    reply.writeNoException();
                    reply.writeInt(updateLockdownVpn ? 1 : 0);
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg035 = data.readInt();
                    String _arg115 = data.readString();
                    boolean isAlwaysOnVpnPackageSupported = isAlwaysOnVpnPackageSupported(_arg035, _arg115);
                    reply.writeNoException();
                    reply.writeInt(isAlwaysOnVpnPackageSupported ? 1 : 0);
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg036 = data.readInt();
                    String _arg116 = data.readString();
                    boolean _arg26 = data.readInt() != 0;
                    List<String> _arg33 = data.createStringArrayList();
                    boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(_arg036, _arg116, _arg26, _arg33);
                    reply.writeNoException();
                    reply.writeInt(alwaysOnVpnPackage ? 1 : 0);
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg037 = data.readInt();
                    String _result33 = getAlwaysOnVpnPackage(_arg037);
                    reply.writeNoException();
                    reply.writeString(_result33);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg038 = data.readInt();
                    boolean isVpnLockdownEnabled = isVpnLockdownEnabled(_arg038);
                    reply.writeNoException();
                    reply.writeInt(isVpnLockdownEnabled ? 1 : 0);
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg039 = data.readInt();
                    List<String> _result34 = getVpnLockdownWhitelist(_arg039);
                    reply.writeNoException();
                    reply.writeStringList(_result34);
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg040 = data.readInt();
                    int _result35 = checkMobileProvisioning(_arg040);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    String _result36 = getMobileProvisioningUrl();
                    reply.writeNoException();
                    reply.writeString(_result36);
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg041 = data.readInt() != 0;
                    int _arg117 = data.readInt();
                    String _arg27 = data.readString();
                    setProvisioningNotificationVisible(_arg041, _arg117, _arg27);
                    reply.writeNoException();
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg042 = data.readInt() != 0;
                    setAirplaneMode(_arg042);
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    Messenger _arg043 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    String _arg118 = data.readString();
                    int _result37 = registerNetworkFactory(_arg043, _arg118);
                    reply.writeNoException();
                    reply.writeInt(_result37);
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg044 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    boolean requestBandwidthUpdate = requestBandwidthUpdate(_arg044);
                    reply.writeNoException();
                    reply.writeInt(requestBandwidthUpdate ? 1 : 0);
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    Messenger _arg045 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    unregisterNetworkFactory(_arg045);
                    reply.writeNoException();
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Messenger.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = NetworkInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg2 = LinkProperties.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg3 = NetworkCapabilities.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    int _arg4 = data.readInt();
                    NetworkMisc _arg5 = data.readInt() != 0 ? NetworkMisc.CREATOR.createFromParcel(data) : null;
                    int _arg6 = data.readInt();
                    int _result38 = registerNetworkAgent(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = NetworkCapabilities.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    Messenger _arg119 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    int _arg28 = data.readInt();
                    IBinder _arg34 = data.readStrongBinder();
                    int _arg42 = data.readInt();
                    NetworkRequest _result39 = requestNetwork(_arg02, _arg119, _arg28, _arg34, _arg42);
                    reply.writeNoException();
                    if (_result39 != null) {
                        reply.writeInt(1);
                        _result39.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = NetworkCapabilities.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    PendingIntent _arg120 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    NetworkRequest _result40 = pendingRequestForNetwork(_arg03, _arg120);
                    reply.writeNoException();
                    if (_result40 != null) {
                        reply.writeInt(1);
                        _result40.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    PendingIntent _arg046 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    releasePendingNetworkRequest(_arg046);
                    reply.writeNoException();
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = NetworkCapabilities.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    Messenger _arg121 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    IBinder _arg29 = data.readStrongBinder();
                    NetworkRequest _result41 = listenForNetwork(_arg04, _arg121, _arg29);
                    reply.writeNoException();
                    if (_result41 != null) {
                        reply.writeInt(1);
                        _result41.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg05 = NetworkCapabilities.CREATOR.createFromParcel(data);
                    } else {
                        _arg05 = null;
                    }
                    PendingIntent _arg122 = data.readInt() != 0 ? PendingIntent.CREATOR.createFromParcel(data) : null;
                    pendingListenForNetwork(_arg05, _arg122);
                    reply.writeNoException();
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkRequest _arg047 = data.readInt() != 0 ? NetworkRequest.CREATOR.createFromParcel(data) : null;
                    releaseNetworkRequest(_arg047);
                    reply.writeNoException();
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg048 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    boolean _arg123 = data.readInt() != 0;
                    boolean _arg210 = data.readInt() != 0;
                    setAcceptUnvalidated(_arg048, _arg123, _arg210);
                    reply.writeNoException();
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg049 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    boolean _arg124 = data.readInt() != 0;
                    boolean _arg211 = data.readInt() != 0;
                    setAcceptPartialConnectivity(_arg049, _arg124, _arg211);
                    reply.writeNoException();
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg050 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    setAvoidUnvalidated(_arg050);
                    reply.writeNoException();
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg051 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    startCaptivePortalApp(_arg051);
                    reply.writeNoException();
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg06 = Network.CREATOR.createFromParcel(data);
                    } else {
                        _arg06 = null;
                    }
                    Bundle _arg125 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    startCaptivePortalAppInternal(_arg06, _arg125);
                    reply.writeNoException();
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    boolean shouldAvoidBadWifi = shouldAvoidBadWifi();
                    reply.writeNoException();
                    reply.writeInt(shouldAvoidBadWifi ? 1 : 0);
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg052 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    int _result42 = getMultipathPreference(_arg052);
                    reply.writeNoException();
                    reply.writeInt(_result42);
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkRequest _result43 = getDefaultRequest();
                    reply.writeNoException();
                    if (_result43 != null) {
                        reply.writeInt(1);
                        _result43.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg053 = data.readInt();
                    int _result44 = getRestoreDefaultNetworkDelay(_arg053);
                    reply.writeNoException();
                    reply.writeInt(_result44);
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg054 = data.readString();
                    int _arg126 = data.readInt();
                    boolean addVpnAddress = addVpnAddress(_arg054, _arg126);
                    reply.writeNoException();
                    reply.writeInt(addVpnAddress ? 1 : 0);
                    return true;
                case 75:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg055 = data.readString();
                    int _arg127 = data.readInt();
                    boolean removeVpnAddress = removeVpnAddress(_arg055, _arg127);
                    reply.writeNoException();
                    reply.writeInt(removeVpnAddress ? 1 : 0);
                    return true;
                case 76:
                    data.enforceInterface(DESCRIPTOR);
                    Network[] _arg056 = (Network[]) data.createTypedArray(Network.CREATOR);
                    boolean underlyingNetworksForVpn = setUnderlyingNetworksForVpn(_arg056);
                    reply.writeNoException();
                    reply.writeInt(underlyingNetworksForVpn ? 1 : 0);
                    return true;
                case 77:
                    data.enforceInterface(DESCRIPTOR);
                    factoryReset();
                    reply.writeNoException();
                    return true;
                case 78:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg057 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    int _arg128 = data.readInt();
                    ISocketKeepaliveCallback _arg212 = ISocketKeepaliveCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg35 = data.readString();
                    int _arg43 = data.readInt();
                    String _arg52 = data.readString();
                    startNattKeepalive(_arg057, _arg128, _arg212, _arg35, _arg43, _arg52);
                    reply.writeNoException();
                    return true;
                case 79:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg058 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    FileDescriptor _arg129 = data.readRawFileDescriptor();
                    int _arg213 = data.readInt();
                    int _arg36 = data.readInt();
                    ISocketKeepaliveCallback _arg44 = ISocketKeepaliveCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg53 = data.readString();
                    String _arg62 = data.readString();
                    startNattKeepaliveWithFd(_arg058, _arg129, _arg213, _arg36, _arg44, _arg53, _arg62);
                    reply.writeNoException();
                    return true;
                case 80:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg059 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    FileDescriptor _arg130 = data.readRawFileDescriptor();
                    int _arg214 = data.readInt();
                    ISocketKeepaliveCallback _arg37 = ISocketKeepaliveCallback.Stub.asInterface(data.readStrongBinder());
                    startTcpKeepalive(_arg059, _arg130, _arg214, _arg37);
                    reply.writeNoException();
                    return true;
                case 81:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg060 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    int _arg131 = data.readInt();
                    stopKeepalive(_arg060, _arg131);
                    reply.writeNoException();
                    return true;
                case 82:
                    data.enforceInterface(DESCRIPTOR);
                    String _result45 = getCaptivePortalServerUrl();
                    reply.writeNoException();
                    reply.writeString(_result45);
                    return true;
                case 83:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result46 = getNetworkWatchlistConfigHash();
                    reply.writeNoException();
                    reply.writeByteArray(_result46);
                    return true;
                case 84:
                    data.enforceInterface(DESCRIPTOR);
                    ConnectionInfo _arg061 = data.readInt() != 0 ? ConnectionInfo.CREATOR.createFromParcel(data) : null;
                    int _result47 = getConnectionOwnerUid(_arg061);
                    reply.writeNoException();
                    reply.writeInt(_result47);
                    return true;
                case 85:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isCallerCurrentAlwaysOnVpnApp = isCallerCurrentAlwaysOnVpnApp();
                    reply.writeNoException();
                    reply.writeInt(isCallerCurrentAlwaysOnVpnApp ? 1 : 0);
                    return true;
                case 86:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isCallerCurrentAlwaysOnVpnLockdownApp = isCallerCurrentAlwaysOnVpnLockdownApp();
                    reply.writeNoException();
                    reply.writeInt(isCallerCurrentAlwaysOnVpnLockdownApp ? 1 : 0);
                    return true;
                case 87:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg062 = data.readInt();
                    ResultReceiver _arg132 = data.readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel(data) : null;
                    boolean _arg215 = data.readInt() != 0;
                    String _arg38 = data.readString();
                    getLatestTetheringEntitlementResult(_arg062, _arg132, _arg215, _arg38);
                    reply.writeNoException();
                    return true;
                case 88:
                    data.enforceInterface(DESCRIPTOR);
                    ITetheringEventCallback _arg063 = ITetheringEventCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg133 = data.readString();
                    registerTetheringEventCallback(_arg063, _arg133);
                    reply.writeNoException();
                    return true;
                case 89:
                    data.enforceInterface(DESCRIPTOR);
                    ITetheringEventCallback _arg064 = ITetheringEventCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg134 = data.readString();
                    unregisterTetheringEventCallback(_arg064, _arg134);
                    reply.writeNoException();
                    return true;
                case 90:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _result48 = startOrGetTestNetworkService();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result48);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IConnectivityManager {
            public static IConnectivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.net.IConnectivityManager
            public Network getActiveNetwork() throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetwork();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public Network getActiveNetworkForUid(int uid, boolean ignoreBlocked) throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(ignoreBlocked ? 1 : 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkForUid(uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkInfo getActiveNetworkInfo() throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkInfo getActiveNetworkInfoForUid(int uid, boolean ignoreBlocked) throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(ignoreBlocked ? 1 : 0);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkInfoForUid(uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkInfo getNetworkInfo(int networkType) throws RemoteException {
                NetworkInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkInfo(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(ignoreBlocked ? 1 : 0);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkInfoForUid(network, uid, ignoreBlocked);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworkInfo();
                    }
                    _reply.readException();
                    NetworkInfo[] _result = (NetworkInfo[]) _reply.createTypedArray(NetworkInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public Network getNetworkForType(int networkType) throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkForType(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Network.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public Network[] getAllNetworks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworks();
                    }
                    _reply.readException();
                    Network[] _result = (Network[]) _reply.createTypedArray(Network.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultNetworkCapabilitiesForUser(userId);
                    }
                    _reply.readException();
                    NetworkCapabilities[] _result = (NetworkCapabilities[]) _reply.createTypedArray(NetworkCapabilities.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isNetworkSupported(int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkSupported(networkType);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public LinkProperties getActiveLinkProperties() throws RemoteException {
                LinkProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveLinkProperties();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public LinkProperties getLinkPropertiesForType(int networkType) throws RemoteException {
                LinkProperties _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkPropertiesForType(networkType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkProperties(network);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LinkProperties.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkCapabilities(network);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkCapabilities.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkState[] getAllNetworkState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllNetworkState();
                    }
                    _reply.readException();
                    NetworkState[] _result = (NetworkState[]) _reply.createTypedArray(NetworkState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
                NetworkQuotaInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveNetworkQuotaInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkQuotaInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isActiveNetworkMetered() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isActiveNetworkMetered();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean requestRouteToHostAddress(int networkType, byte[] hostAddress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeByteArray(hostAddress);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestRouteToHostAddress(networkType, hostAddress);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int tether(String iface, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().tether(iface, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int untether(String iface, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().untether(iface, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int getLastTetherError(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastTetherError(iface);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isTetheringSupported(String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTetheringSupported(callerPkg);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(showProvisioningUi ? 1 : 0);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTethering(type, receiver, showProvisioningUi, callerPkg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void stopTethering(int type, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopTethering(type, callerPkg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetherableIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetheredIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheredIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetheringErroredIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheringErroredIfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetheredDhcpRanges() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetheredDhcpRanges();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetherableUsbRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableUsbRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetherableWifiRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableWifiRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String[] getTetherableBluetoothRegexs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherableBluetoothRegexs();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int setUsbTethering(boolean enable, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUsbTethering(enable, callerPkg);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void reportInetCondition(int networkType, int percentage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeInt(percentage);
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportInetCondition(networkType, percentage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(hasConnectivity ? 1 : 0);
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportNetworkConnectivity(network, hasConnectivity);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public ProxyInfo getGlobalProxy() throws RemoteException {
                ProxyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalProxy();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProxyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalProxy(p);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProxyForNetwork(nework);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ProxyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean prepareVpn(String oldPackage, String newPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldPackage);
                    _data.writeString(newPackage);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().prepareVpn(oldPackage, newPackage, userId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void setVpnPackageAuthorization(String packageName, int userId, boolean authorized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(authorized ? 1 : 0);
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVpnPackageAuthorization(packageName, userId, authorized);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().establishVpn(config);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public VpnConfig getVpnConfig(int userId) throws RemoteException {
                VpnConfig _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVpnConfig(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VpnConfig.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLegacyVpn(profile);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public LegacyVpnInfo getLegacyVpnInfo(int userId) throws RemoteException {
                LegacyVpnInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLegacyVpnInfo(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LegacyVpnInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean updateLockdownVpn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateLockdownVpn();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isAlwaysOnVpnPackageSupported(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAlwaysOnVpnPackageSupported(userId, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean setAlwaysOnVpnPackage(int userId, String packageName, boolean lockdown, List<String> lockdownWhitelist) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeInt(lockdown ? 1 : 0);
                    _data.writeStringList(lockdownWhitelist);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAlwaysOnVpnPackage(userId, packageName, lockdown, lockdownWhitelist);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String getAlwaysOnVpnPackage(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAlwaysOnVpnPackage(userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isVpnLockdownEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVpnLockdownEnabled(userId);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public List<String> getVpnLockdownWhitelist(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVpnLockdownWhitelist(userId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int checkMobileProvisioning(int suggestedTimeOutMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(suggestedTimeOutMs);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkMobileProvisioning(suggestedTimeOutMs);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String getMobileProvisioningUrl() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMobileProvisioningUrl();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void setProvisioningNotificationVisible(boolean visible, int networkType, String action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible ? 1 : 0);
                    _data.writeInt(networkType);
                    _data.writeString(action);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProvisioningNotificationVisible(visible, networkType, action);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void setAirplaneMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAirplaneMode(enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerNetworkFactory(messenger, name);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean requestBandwidthUpdate(Network network) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(56, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBandwidthUpdate(network);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(57, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkFactory(messenger);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int registerNetworkAgent(Messenger messenger, NetworkInfo ni, LinkProperties lp, NetworkCapabilities nc, int score, NetworkMisc misc, int factorySerialNumber) throws RemoteException {
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
                    if (ni != null) {
                        _data.writeInt(1);
                        ni.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (lp != null) {
                        _data.writeInt(1);
                        lp.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (nc != null) {
                        _data.writeInt(1);
                        nc.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(score);
                    if (misc != null) {
                        _data.writeInt(1);
                        misc.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(factorySerialNumber);
                    boolean _status = this.mRemote.transact(58, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerNetworkAgent(messenger, ni, lp, nc, score, misc, factorySerialNumber);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(59, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestNetwork(networkCapabilities, messenger, timeoutSec, binder, legacy);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(60, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pendingRequestForNetwork(networkCapabilities, operation);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(61, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releasePendingNetworkRequest(operation);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(62, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listenForNetwork(networkCapabilities, messenger, binder);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(63, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pendingListenForNetwork(networkCapabilities, operation);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(64, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseNetworkRequest(networkRequest);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(accept ? 1 : 0);
                    _data.writeInt(always ? 1 : 0);
                    boolean _status = this.mRemote.transact(65, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAcceptUnvalidated(network, accept, always);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(accept ? 1 : 0);
                    _data.writeInt(always ? 1 : 0);
                    boolean _status = this.mRemote.transact(66, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAcceptPartialConnectivity(network, accept, always);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(67, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAvoidUnvalidated(network);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(68, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCaptivePortalApp(network);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(69, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCaptivePortalAppInternal(network, appExtras);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean shouldAvoidBadWifi() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(70, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldAvoidBadWifi();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(71, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMultipathPreference(Network);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public NetworkRequest getDefaultRequest() throws RemoteException {
                NetworkRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(72, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultRequest();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public int getRestoreDefaultNetworkDelay(int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    boolean _status = this.mRemote.transact(73, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestoreDefaultNetworkDelay(networkType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean addVpnAddress(String address, int prefixLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prefixLength);
                    boolean _status = this.mRemote.transact(74, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addVpnAddress(address, prefixLength);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean removeVpnAddress(String address, int prefixLength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prefixLength);
                    boolean _status = this.mRemote.transact(75, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeVpnAddress(address, prefixLength);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean setUnderlyingNetworksForVpn(Network[] networks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(networks, 0);
                    boolean _status = this.mRemote.transact(76, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUnderlyingNetworksForVpn(networks);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(77, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void startNattKeepalive(Network network, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, int srcPort, String dstAddr) throws RemoteException {
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
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(intervalSeconds);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(srcAddr);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(srcPort);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(dstAddr);
                    boolean _status = this.mRemote.transact(78, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startNattKeepalive(network, intervalSeconds, cb, srcAddr, srcPort, dstAddr);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.net.IConnectivityManager
            public void startNattKeepaliveWithFd(Network network, FileDescriptor fd, int resourceId, int intervalSeconds, ISocketKeepaliveCallback cb, String srcAddr, String dstAddr) throws RemoteException {
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
                    try {
                        _data.writeRawFileDescriptor(fd);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(resourceId);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(intervalSeconds);
                        _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                        _data.writeString(srcAddr);
                        _data.writeString(dstAddr);
                        boolean _status = this.mRemote.transact(79, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startNattKeepaliveWithFd(network, fd, resourceId, intervalSeconds, cb, srcAddr, dstAddr);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
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
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(80, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTcpKeepalive(network, fd, intervalSeconds, cb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(81, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopKeepalive(network, slot);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public String getCaptivePortalServerUrl() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(82, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCaptivePortalServerUrl();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(83, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkWatchlistConfigHash();
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    boolean _status = this.mRemote.transact(84, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionOwnerUid(connectionInfo);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(85, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnApp();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(86, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnLockdownApp();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
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
                    _data.writeInt(showEntitlementUi ? 1 : 0);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(87, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getLatestTetheringEntitlementResult(type, receiver, showEntitlementUi, callerPkg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(88, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTetheringEventCallback(callback, callerPkg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callerPkg);
                    boolean _status = this.mRemote.transact(89, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTetheringEventCallback(callback, callerPkg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.IConnectivityManager
            public IBinder startOrGetTestNetworkService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(90, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startOrGetTestNetworkService();
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IConnectivityManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IConnectivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
