package android.net.wifi;

import android.annotation.UnsupportedAppUsage;
import android.content.p002pm.ParceledListSlice;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.wifi.IDppCallback;
import android.net.wifi.INetworkRequestMatchCallback;
import android.net.wifi.IOnWifiUsabilityStatsListener;
import android.net.wifi.ISoftApCallback;
import android.net.wifi.ITrafficStateCallback;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Messenger;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.ResultReceiver;
import android.p007os.WorkSource;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IWifiManager extends IInterface {
    void acquireMulticastLock(IBinder iBinder, String str) throws RemoteException;

    boolean acquireWifiLock(IBinder iBinder, int i, String str, WorkSource workSource) throws RemoteException;

    int addNetworkSuggestions(List<WifiNetworkSuggestion> list, String str) throws RemoteException;

    void addOnWifiUsabilityStatsListener(IBinder iBinder, IOnWifiUsabilityStatsListener iOnWifiUsabilityStatsListener, int i) throws RemoteException;

    int addOrUpdateNetwork(WifiConfiguration wifiConfiguration, String str) throws RemoteException;

    boolean addOrUpdatePasspointConfiguration(PasspointConfiguration passpointConfiguration, String str) throws RemoteException;

    void deauthenticateNetwork(long j, boolean z) throws RemoteException;

    void disableEphemeralNetwork(String str, String str2) throws RemoteException;

    boolean disableNetwork(int i, String str) throws RemoteException;

    boolean disconnect(String str) throws RemoteException;

    int dppAddBootstrapQrCode(String str) throws RemoteException;

    int dppBootstrapGenerate(WifiDppConfig wifiDppConfig) throws RemoteException;

    int dppBootstrapRemove(int i) throws RemoteException;

    int dppConfiguratorAdd(String str, String str2, int i) throws RemoteException;

    String dppConfiguratorGetKey(int i) throws RemoteException;

    int dppConfiguratorRemove(int i) throws RemoteException;

    String dppGetUri(int i) throws RemoteException;

    int dppListen(String str, int i, boolean z, boolean z2) throws RemoteException;

    int dppStartAuth(WifiDppConfig wifiDppConfig) throws RemoteException;

    void dppStopListen() throws RemoteException;

    boolean enableNetwork(int i, boolean z, String str) throws RemoteException;

    void enableTdls(String str, boolean z) throws RemoteException;

    void enableTdlsWithMacAddress(String str, boolean z) throws RemoteException;

    void enableVerboseLogging(int i) throws RemoteException;

    void enableWifiConnectivityManager(boolean z) throws RemoteException;

    void enableWifiCoverageExtendFeature(boolean z) throws RemoteException;

    void factoryReset(String str) throws RemoteException;

    Map getAllMatchingFqdnsForScanResults(List<ScanResult> list) throws RemoteException;

    String getCapabilities(String str) throws RemoteException;

    ParceledListSlice getConfiguredNetworks(String str) throws RemoteException;

    WifiInfo getConnectionInfo(String str) throws RemoteException;

    String getCountryCode() throws RemoteException;

    @UnsupportedAppUsage
    Network getCurrentNetwork() throws RemoteException;

    String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException;

    DhcpInfo getDhcpInfo() throws RemoteException;

    String[] getFactoryMacAddresses() throws RemoteException;

    Map getMatchingOsuProviders(List<ScanResult> list) throws RemoteException;

    Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> list) throws RemoteException;

    List<PasspointConfiguration> getPasspointConfigurations(String str) throws RemoteException;

    ParceledListSlice getPrivilegedConfiguredNetworks(String str) throws RemoteException;

    List<ScanResult> getScanResults(String str) throws RemoteException;

    int getSoftApWifiGeneration() throws RemoteException;

    long getSupportedFeatures() throws RemoteException;

    int getVerboseLoggingLevel() throws RemoteException;

    @UnsupportedAppUsage
    WifiConfiguration getWifiApConfiguration() throws RemoteException;

    @UnsupportedAppUsage
    int getWifiApEnabledState() throws RemoteException;

    List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> list) throws RemoteException;

    int getWifiEnabledState() throws RemoteException;

    Messenger getWifiServiceMessenger(String str) throws RemoteException;

    void initializeMulticastFiltering() throws RemoteException;

    boolean isDualBandSupported() throws RemoteException;

    boolean isExtendingWifi() throws RemoteException;

    boolean isMulticastEnabled() throws RemoteException;

    boolean isScanAlwaysAvailable() throws RemoteException;

    boolean isWifiCoverageExtendFeatureEnabled() throws RemoteException;

    int matchProviderWithCurrentNetwork(String str) throws RemoteException;

    boolean needs5GHzToAnyApBandConversion() throws RemoteException;

    void notifyUserOfApBandConversion(String str) throws RemoteException;

    void queryPasspointIcon(long j, String str) throws RemoteException;

    boolean reassociate(String str) throws RemoteException;

    boolean reconnect(String str) throws RemoteException;

    void registerNetworkRequestMatchCallback(IBinder iBinder, INetworkRequestMatchCallback iNetworkRequestMatchCallback, int i) throws RemoteException;

    void registerSoftApCallback(IBinder iBinder, ISoftApCallback iSoftApCallback, int i) throws RemoteException;

    void registerTrafficStateCallback(IBinder iBinder, ITrafficStateCallback iTrafficStateCallback, int i) throws RemoteException;

    void releaseMulticastLock(String str) throws RemoteException;

    boolean releaseWifiLock(IBinder iBinder) throws RemoteException;

    boolean removeNetwork(int i, String str) throws RemoteException;

    int removeNetworkSuggestions(List<WifiNetworkSuggestion> list, String str) throws RemoteException;

    void removeOnWifiUsabilityStatsListener(int i) throws RemoteException;

    boolean removePasspointConfiguration(String str, String str2) throws RemoteException;

    WifiActivityEnergyInfo reportActivityInfo() throws RemoteException;

    void requestActivityInfo(ResultReceiver resultReceiver) throws RemoteException;

    void restoreBackupData(byte[] bArr) throws RemoteException;

    void restoreSupplicantBackupData(byte[] bArr, byte[] bArr2) throws RemoteException;

    byte[] retrieveBackupData() throws RemoteException;

    void setCountryCode(String str) throws RemoteException;

    void setDeviceMobilityState(int i) throws RemoteException;

    boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration, String str) throws RemoteException;

    boolean setWifiEnabled(String str, boolean z) throws RemoteException;

    void startDppAsConfiguratorInitiator(IBinder iBinder, String str, int i, int i2, IDppCallback iDppCallback) throws RemoteException;

    void startDppAsEnrolleeInitiator(IBinder iBinder, String str, IDppCallback iDppCallback) throws RemoteException;

    int startLocalOnlyHotspot(Messenger messenger, IBinder iBinder, String str) throws RemoteException;

    boolean startScan(String str) throws RemoteException;

    boolean startSoftAp(WifiConfiguration wifiConfiguration) throws RemoteException;

    void startSubscriptionProvisioning(OsuProvider osuProvider, IProvisioningCallback iProvisioningCallback) throws RemoteException;

    void startWatchLocalOnlyHotspot(Messenger messenger, IBinder iBinder) throws RemoteException;

    void stopDppSession() throws RemoteException;

    void stopLocalOnlyHotspot() throws RemoteException;

    boolean stopSoftAp() throws RemoteException;

    void stopWatchLocalOnlyHotspot() throws RemoteException;

    void unregisterNetworkRequestMatchCallback(int i) throws RemoteException;

    void unregisterSoftApCallback(int i) throws RemoteException;

    void unregisterTrafficStateCallback(int i) throws RemoteException;

    void updateInterfaceIpState(String str, int i) throws RemoteException;

    void updateWifiLockWorkSource(IBinder iBinder, WorkSource workSource) throws RemoteException;

    void updateWifiUsabilityScore(int i, int i2, int i3) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IWifiManager {
        @Override // android.net.wifi.IWifiManager
        public long getSupportedFeatures() throws RemoteException {
            return 0L;
        }

        @Override // android.net.wifi.IWifiManager
        public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void requestActivityInfo(ResultReceiver result) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public ParceledListSlice getConfiguredNetworks(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public ParceledListSlice getPrivilegedConfiguredNetworks(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public Map getAllMatchingFqdnsForScanResults(List<ScanResult> scanResult) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public Map getMatchingOsuProviders(List<ScanResult> scanResult) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> osuProviders) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public int addOrUpdateNetwork(WifiConfiguration config, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration config, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean removePasspointConfiguration(String fqdn, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public List<PasspointConfiguration> getPasspointConfigurations(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> fqdnList) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void queryPasspointIcon(long bssid, String fileName) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int matchProviderWithCurrentNetwork(String fqdn) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public void deauthenticateNetwork(long holdoff, boolean ess) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public boolean removeNetwork(int netId, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean enableNetwork(int netId, boolean disableOthers, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean disableNetwork(int netId, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean startScan(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public List<ScanResult> getScanResults(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean disconnect(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean reconnect(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean reassociate(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public WifiInfo getConnectionInfo(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean setWifiEnabled(String packageName, boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public int getWifiEnabledState() throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public void setCountryCode(String country) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public String getCountryCode() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean isDualBandSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public DhcpInfo getDhcpInfo() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean isScanAlwaysAvailable() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean acquireWifiLock(IBinder lock, int lockType, String tag, WorkSource ws) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public void updateWifiLockWorkSource(IBinder lock, WorkSource ws) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public boolean releaseWifiLock(IBinder lock) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public void initializeMulticastFiltering() throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public boolean isMulticastEnabled() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public void acquireMulticastLock(IBinder binder, String tag) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void releaseMulticastLock(String tag) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void updateInterfaceIpState(String ifaceName, int mode) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public boolean startSoftAp(WifiConfiguration wifiConfig) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean stopSoftAp() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public int startLocalOnlyHotspot(Messenger messenger, IBinder binder, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public void stopLocalOnlyHotspot() throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder binder) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void stopWatchLocalOnlyHotspot() throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int getWifiApEnabledState() throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public WifiConfiguration getWifiApConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean setWifiApConfiguration(WifiConfiguration wifiConfig, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public void notifyUserOfApBandConversion(String packageName) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public Messenger getWifiServiceMessenger(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void enableTdls(String remoteIPAddress, boolean enable) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void enableTdlsWithMacAddress(String remoteMacAddress, boolean enable) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void enableVerboseLogging(int verbose) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int getVerboseLoggingLevel() throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public void enableWifiConnectivityManager(boolean enabled) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void disableEphemeralNetwork(String SSID, String packageName) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void factoryReset(String packageName) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public Network getCurrentNetwork() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public byte[] retrieveBackupData() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void restoreBackupData(byte[] data) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void restoreSupplicantBackupData(byte[] supplicantData, byte[] ipConfigData) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void startSubscriptionProvisioning(OsuProvider provider, IProvisioningCallback callback) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void registerSoftApCallback(IBinder binder, ISoftApCallback callback, int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void unregisterSoftApCallback(int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void addOnWifiUsabilityStatsListener(IBinder binder, IOnWifiUsabilityStatsListener listener, int listenerIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void removeOnWifiUsabilityStatsListener(int listenerIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void registerTrafficStateCallback(IBinder binder, ITrafficStateCallback callback, int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void unregisterTrafficStateCallback(int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public String getCapabilities(String capaType) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppAddBootstrapQrCode(String uri) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppBootstrapGenerate(WifiDppConfig config) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public String dppGetUri(int bootstrap_id) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppBootstrapRemove(int bootstrap_id) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppListen(String frequency, int dpp_role, boolean qr_mutual, boolean netrole_ap) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public void dppStopListen() throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int dppConfiguratorAdd(String curve, String key, int expiry) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppConfiguratorRemove(int config_id) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public int dppStartAuth(WifiDppConfig config) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public String dppConfiguratorGetKey(int id) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean isExtendingWifi() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public boolean isWifiCoverageExtendFeatureEnabled() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IWifiManager
        public void enableWifiCoverageExtendFeature(boolean enable) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void registerNetworkRequestMatchCallback(IBinder binder, INetworkRequestMatchCallback callback, int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void unregisterNetworkRequestMatchCallback(int callbackIdentifier) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int addNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public int removeNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IWifiManager
        public String[] getFactoryMacAddresses() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IWifiManager
        public void setDeviceMobilityState(int state) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void startDppAsConfiguratorInitiator(IBinder binder, String enrolleeUri, int selectedNetworkId, int netRole, IDppCallback callback) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void startDppAsEnrolleeInitiator(IBinder binder, String configuratorUri, IDppCallback callback) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void stopDppSession() throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public void updateWifiUsabilityScore(int seqNum, int score, int predictionHorizonSec) throws RemoteException {
        }

        @Override // android.net.wifi.IWifiManager
        public int getSoftApWifiGeneration() throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IWifiManager {
        private static final String DESCRIPTOR = "android.net.wifi.IWifiManager";
        static final int TRANSACTION_acquireMulticastLock = 39;
        static final int TRANSACTION_acquireWifiLock = 34;
        static final int TRANSACTION_addNetworkSuggestions = 88;
        static final int TRANSACTION_addOnWifiUsabilityStatsListener = 68;
        static final int TRANSACTION_addOrUpdateNetwork = 9;
        static final int TRANSACTION_addOrUpdatePasspointConfiguration = 10;
        static final int TRANSACTION_deauthenticateNetwork = 16;
        static final int TRANSACTION_disableEphemeralNetwork = 59;
        static final int TRANSACTION_disableNetwork = 19;
        static final int TRANSACTION_disconnect = 22;
        static final int TRANSACTION_dppAddBootstrapQrCode = 73;
        static final int TRANSACTION_dppBootstrapGenerate = 74;
        static final int TRANSACTION_dppBootstrapRemove = 76;
        static final int TRANSACTION_dppConfiguratorAdd = 79;
        static final int TRANSACTION_dppConfiguratorGetKey = 82;
        static final int TRANSACTION_dppConfiguratorRemove = 80;
        static final int TRANSACTION_dppGetUri = 75;
        static final int TRANSACTION_dppListen = 77;
        static final int TRANSACTION_dppStartAuth = 81;
        static final int TRANSACTION_dppStopListen = 78;
        static final int TRANSACTION_enableNetwork = 18;
        static final int TRANSACTION_enableTdls = 53;
        static final int TRANSACTION_enableTdlsWithMacAddress = 54;
        static final int TRANSACTION_enableVerboseLogging = 56;
        static final int TRANSACTION_enableWifiConnectivityManager = 58;
        static final int TRANSACTION_enableWifiCoverageExtendFeature = 85;
        static final int TRANSACTION_factoryReset = 60;
        static final int TRANSACTION_getAllMatchingFqdnsForScanResults = 6;
        static final int TRANSACTION_getCapabilities = 72;
        static final int TRANSACTION_getConfiguredNetworks = 4;
        static final int TRANSACTION_getConnectionInfo = 25;
        static final int TRANSACTION_getCountryCode = 29;
        static final int TRANSACTION_getCurrentNetwork = 61;
        static final int TRANSACTION_getCurrentNetworkWpsNfcConfigurationToken = 55;
        static final int TRANSACTION_getDhcpInfo = 32;
        static final int TRANSACTION_getFactoryMacAddresses = 90;
        static final int TRANSACTION_getMatchingOsuProviders = 7;
        static final int TRANSACTION_getMatchingPasspointConfigsForOsuProviders = 8;
        static final int TRANSACTION_getPasspointConfigurations = 12;
        static final int TRANSACTION_getPrivilegedConfiguredNetworks = 5;
        static final int TRANSACTION_getScanResults = 21;
        static final int TRANSACTION_getSoftApWifiGeneration = 96;
        static final int TRANSACTION_getSupportedFeatures = 1;
        static final int TRANSACTION_getVerboseLoggingLevel = 57;
        static final int TRANSACTION_getWifiApConfiguration = 49;
        static final int TRANSACTION_getWifiApEnabledState = 48;
        static final int TRANSACTION_getWifiConfigsForPasspointProfiles = 13;
        static final int TRANSACTION_getWifiEnabledState = 27;
        static final int TRANSACTION_getWifiServiceMessenger = 52;
        static final int TRANSACTION_initializeMulticastFiltering = 37;
        static final int TRANSACTION_isDualBandSupported = 30;
        static final int TRANSACTION_isExtendingWifi = 83;
        static final int TRANSACTION_isMulticastEnabled = 38;
        static final int TRANSACTION_isScanAlwaysAvailable = 33;
        static final int TRANSACTION_isWifiCoverageExtendFeatureEnabled = 84;
        static final int TRANSACTION_matchProviderWithCurrentNetwork = 15;
        static final int TRANSACTION_needs5GHzToAnyApBandConversion = 31;
        static final int TRANSACTION_notifyUserOfApBandConversion = 51;
        static final int TRANSACTION_queryPasspointIcon = 14;
        static final int TRANSACTION_reassociate = 24;
        static final int TRANSACTION_reconnect = 23;
        static final int TRANSACTION_registerNetworkRequestMatchCallback = 86;
        static final int TRANSACTION_registerSoftApCallback = 66;
        static final int TRANSACTION_registerTrafficStateCallback = 70;
        static final int TRANSACTION_releaseMulticastLock = 40;
        static final int TRANSACTION_releaseWifiLock = 36;
        static final int TRANSACTION_removeNetwork = 17;
        static final int TRANSACTION_removeNetworkSuggestions = 89;
        static final int TRANSACTION_removeOnWifiUsabilityStatsListener = 69;
        static final int TRANSACTION_removePasspointConfiguration = 11;
        static final int TRANSACTION_reportActivityInfo = 2;
        static final int TRANSACTION_requestActivityInfo = 3;
        static final int TRANSACTION_restoreBackupData = 63;
        static final int TRANSACTION_restoreSupplicantBackupData = 64;
        static final int TRANSACTION_retrieveBackupData = 62;
        static final int TRANSACTION_setCountryCode = 28;
        static final int TRANSACTION_setDeviceMobilityState = 91;
        static final int TRANSACTION_setWifiApConfiguration = 50;
        static final int TRANSACTION_setWifiEnabled = 26;
        static final int TRANSACTION_startDppAsConfiguratorInitiator = 92;
        static final int TRANSACTION_startDppAsEnrolleeInitiator = 93;
        static final int TRANSACTION_startLocalOnlyHotspot = 44;
        static final int TRANSACTION_startScan = 20;
        static final int TRANSACTION_startSoftAp = 42;
        static final int TRANSACTION_startSubscriptionProvisioning = 65;
        static final int TRANSACTION_startWatchLocalOnlyHotspot = 46;
        static final int TRANSACTION_stopDppSession = 94;
        static final int TRANSACTION_stopLocalOnlyHotspot = 45;
        static final int TRANSACTION_stopSoftAp = 43;
        static final int TRANSACTION_stopWatchLocalOnlyHotspot = 47;
        static final int TRANSACTION_unregisterNetworkRequestMatchCallback = 87;
        static final int TRANSACTION_unregisterSoftApCallback = 67;
        static final int TRANSACTION_unregisterTrafficStateCallback = 71;
        static final int TRANSACTION_updateInterfaceIpState = 41;
        static final int TRANSACTION_updateWifiLockWorkSource = 35;
        static final int TRANSACTION_updateWifiUsabilityScore = 95;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWifiManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWifiManager)) {
                return (IWifiManager) iin;
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
                    return "getSupportedFeatures";
                case 2:
                    return "reportActivityInfo";
                case 3:
                    return "requestActivityInfo";
                case 4:
                    return "getConfiguredNetworks";
                case 5:
                    return "getPrivilegedConfiguredNetworks";
                case 6:
                    return "getAllMatchingFqdnsForScanResults";
                case 7:
                    return "getMatchingOsuProviders";
                case 8:
                    return "getMatchingPasspointConfigsForOsuProviders";
                case 9:
                    return "addOrUpdateNetwork";
                case 10:
                    return "addOrUpdatePasspointConfiguration";
                case 11:
                    return "removePasspointConfiguration";
                case 12:
                    return "getPasspointConfigurations";
                case 13:
                    return "getWifiConfigsForPasspointProfiles";
                case 14:
                    return "queryPasspointIcon";
                case 15:
                    return "matchProviderWithCurrentNetwork";
                case 16:
                    return "deauthenticateNetwork";
                case 17:
                    return "removeNetwork";
                case 18:
                    return "enableNetwork";
                case 19:
                    return "disableNetwork";
                case 20:
                    return "startScan";
                case 21:
                    return "getScanResults";
                case 22:
                    return "disconnect";
                case 23:
                    return "reconnect";
                case 24:
                    return "reassociate";
                case 25:
                    return "getConnectionInfo";
                case 26:
                    return "setWifiEnabled";
                case 27:
                    return "getWifiEnabledState";
                case 28:
                    return "setCountryCode";
                case 29:
                    return "getCountryCode";
                case 30:
                    return "isDualBandSupported";
                case 31:
                    return "needs5GHzToAnyApBandConversion";
                case 32:
                    return "getDhcpInfo";
                case 33:
                    return "isScanAlwaysAvailable";
                case 34:
                    return "acquireWifiLock";
                case 35:
                    return "updateWifiLockWorkSource";
                case 36:
                    return "releaseWifiLock";
                case 37:
                    return "initializeMulticastFiltering";
                case 38:
                    return "isMulticastEnabled";
                case 39:
                    return "acquireMulticastLock";
                case 40:
                    return "releaseMulticastLock";
                case 41:
                    return "updateInterfaceIpState";
                case 42:
                    return "startSoftAp";
                case 43:
                    return "stopSoftAp";
                case 44:
                    return "startLocalOnlyHotspot";
                case 45:
                    return "stopLocalOnlyHotspot";
                case 46:
                    return "startWatchLocalOnlyHotspot";
                case 47:
                    return "stopWatchLocalOnlyHotspot";
                case 48:
                    return "getWifiApEnabledState";
                case 49:
                    return "getWifiApConfiguration";
                case 50:
                    return "setWifiApConfiguration";
                case 51:
                    return "notifyUserOfApBandConversion";
                case 52:
                    return "getWifiServiceMessenger";
                case 53:
                    return "enableTdls";
                case 54:
                    return "enableTdlsWithMacAddress";
                case 55:
                    return "getCurrentNetworkWpsNfcConfigurationToken";
                case 56:
                    return "enableVerboseLogging";
                case 57:
                    return "getVerboseLoggingLevel";
                case 58:
                    return "enableWifiConnectivityManager";
                case 59:
                    return "disableEphemeralNetwork";
                case 60:
                    return "factoryReset";
                case 61:
                    return "getCurrentNetwork";
                case 62:
                    return "retrieveBackupData";
                case 63:
                    return "restoreBackupData";
                case 64:
                    return "restoreSupplicantBackupData";
                case 65:
                    return "startSubscriptionProvisioning";
                case 66:
                    return "registerSoftApCallback";
                case 67:
                    return "unregisterSoftApCallback";
                case 68:
                    return "addOnWifiUsabilityStatsListener";
                case 69:
                    return "removeOnWifiUsabilityStatsListener";
                case 70:
                    return "registerTrafficStateCallback";
                case 71:
                    return "unregisterTrafficStateCallback";
                case 72:
                    return "getCapabilities";
                case 73:
                    return "dppAddBootstrapQrCode";
                case 74:
                    return "dppBootstrapGenerate";
                case 75:
                    return "dppGetUri";
                case 76:
                    return "dppBootstrapRemove";
                case 77:
                    return "dppListen";
                case 78:
                    return "dppStopListen";
                case 79:
                    return "dppConfiguratorAdd";
                case 80:
                    return "dppConfiguratorRemove";
                case 81:
                    return "dppStartAuth";
                case 82:
                    return "dppConfiguratorGetKey";
                case 83:
                    return "isExtendingWifi";
                case 84:
                    return "isWifiCoverageExtendFeatureEnabled";
                case 85:
                    return "enableWifiCoverageExtendFeature";
                case 86:
                    return "registerNetworkRequestMatchCallback";
                case 87:
                    return "unregisterNetworkRequestMatchCallback";
                case 88:
                    return "addNetworkSuggestions";
                case 89:
                    return "removeNetworkSuggestions";
                case 90:
                    return "getFactoryMacAddresses";
                case 91:
                    return "setDeviceMobilityState";
                case 92:
                    return "startDppAsConfiguratorInitiator";
                case 93:
                    return "startDppAsEnrolleeInitiator";
                case 94:
                    return "stopDppSession";
                case 95:
                    return "updateWifiUsabilityScore";
                case 96:
                    return "getSoftApWifiGeneration";
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
            boolean _arg3;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    long _result = getSupportedFeatures();
                    reply.writeNoException();
                    reply.writeLong(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    WifiActivityEnergyInfo _result2 = reportActivityInfo();
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
                    ResultReceiver _arg0 = data.readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel(data) : null;
                    requestActivityInfo(_arg0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    ParceledListSlice _result3 = getConfiguredNetworks(_arg02);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    ParceledListSlice _result4 = getPrivilegedConfiguredNetworks(_arg03);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    List<ScanResult> _arg04 = data.createTypedArrayList(ScanResult.CREATOR);
                    Map _result5 = getAllMatchingFqdnsForScanResults(_arg04);
                    reply.writeNoException();
                    reply.writeMap(_result5);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    List<ScanResult> _arg05 = data.createTypedArrayList(ScanResult.CREATOR);
                    Map _result6 = getMatchingOsuProviders(_arg05);
                    reply.writeNoException();
                    reply.writeMap(_result6);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    List<OsuProvider> _arg06 = data.createTypedArrayList(OsuProvider.CREATOR);
                    Map _result7 = getMatchingPasspointConfigsForOsuProviders(_arg06);
                    reply.writeNoException();
                    reply.writeMap(_result7);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    WifiConfiguration _arg07 = data.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(data) : null;
                    String _arg1 = data.readString();
                    int _result8 = addOrUpdateNetwork(_arg07, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    PasspointConfiguration _arg08 = data.readInt() != 0 ? PasspointConfiguration.CREATOR.createFromParcel(data) : null;
                    String _arg12 = data.readString();
                    boolean addOrUpdatePasspointConfiguration = addOrUpdatePasspointConfiguration(_arg08, _arg12);
                    reply.writeNoException();
                    reply.writeInt(addOrUpdatePasspointConfiguration ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    String _arg13 = data.readString();
                    boolean removePasspointConfiguration = removePasspointConfiguration(_arg09, _arg13);
                    reply.writeNoException();
                    reply.writeInt(removePasspointConfiguration ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    List<PasspointConfiguration> _result9 = getPasspointConfigurations(_arg010);
                    reply.writeNoException();
                    reply.writeTypedList(_result9);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    List<String> _arg011 = data.createStringArrayList();
                    List<WifiConfiguration> _result10 = getWifiConfigsForPasspointProfiles(_arg011);
                    reply.writeNoException();
                    reply.writeTypedList(_result10);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg012 = data.readLong();
                    String _arg14 = data.readString();
                    queryPasspointIcon(_arg012, _arg14);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg013 = data.readString();
                    int _result11 = matchProviderWithCurrentNetwork(_arg013);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg014 = data.readLong();
                    _arg3 = data.readInt() != 0;
                    boolean _arg15 = _arg3;
                    deauthenticateNetwork(_arg014, _arg15);
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg015 = data.readInt();
                    String _arg16 = data.readString();
                    boolean removeNetwork = removeNetwork(_arg015, _arg16);
                    reply.writeNoException();
                    reply.writeInt(removeNetwork ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg016 = data.readInt();
                    _arg3 = data.readInt() != 0;
                    String _arg2 = data.readString();
                    boolean enableNetwork = enableNetwork(_arg016, _arg3, _arg2);
                    reply.writeNoException();
                    reply.writeInt(enableNetwork ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg017 = data.readInt();
                    String _arg17 = data.readString();
                    boolean disableNetwork = disableNetwork(_arg017, _arg17);
                    reply.writeNoException();
                    reply.writeInt(disableNetwork ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg018 = data.readString();
                    boolean startScan = startScan(_arg018);
                    reply.writeNoException();
                    reply.writeInt(startScan ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    List<ScanResult> _result12 = getScanResults(_arg019);
                    reply.writeNoException();
                    reply.writeTypedList(_result12);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    boolean disconnect = disconnect(_arg020);
                    reply.writeNoException();
                    reply.writeInt(disconnect ? 1 : 0);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg021 = data.readString();
                    boolean reconnect = reconnect(_arg021);
                    reply.writeNoException();
                    reply.writeInt(reconnect ? 1 : 0);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg022 = data.readString();
                    boolean reassociate = reassociate(_arg022);
                    reply.writeNoException();
                    reply.writeInt(reassociate ? 1 : 0);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg023 = data.readString();
                    WifiInfo _result13 = getConnectionInfo(_arg023);
                    reply.writeNoException();
                    if (_result13 != null) {
                        reply.writeInt(1);
                        _result13.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg024 = data.readString();
                    _arg3 = data.readInt() != 0;
                    boolean wifiEnabled = setWifiEnabled(_arg024, _arg3);
                    reply.writeNoException();
                    reply.writeInt(wifiEnabled ? 1 : 0);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    int _result14 = getWifiEnabledState();
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg025 = data.readString();
                    setCountryCode(_arg025);
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    String _result15 = getCountryCode();
                    reply.writeNoException();
                    reply.writeString(_result15);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isDualBandSupported = isDualBandSupported();
                    reply.writeNoException();
                    reply.writeInt(isDualBandSupported ? 1 : 0);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    boolean needs5GHzToAnyApBandConversion = needs5GHzToAnyApBandConversion();
                    reply.writeNoException();
                    reply.writeInt(needs5GHzToAnyApBandConversion ? 1 : 0);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    DhcpInfo _result16 = getDhcpInfo();
                    reply.writeNoException();
                    if (_result16 != null) {
                        reply.writeInt(1);
                        _result16.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isScanAlwaysAvailable = isScanAlwaysAvailable();
                    reply.writeNoException();
                    reply.writeInt(isScanAlwaysAvailable ? 1 : 0);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg026 = data.readStrongBinder();
                    int _arg18 = data.readInt();
                    String _arg22 = data.readString();
                    boolean acquireWifiLock = acquireWifiLock(_arg026, _arg18, _arg22, data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    reply.writeInt(acquireWifiLock ? 1 : 0);
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg027 = data.readStrongBinder();
                    WorkSource _arg19 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    updateWifiLockWorkSource(_arg027, _arg19);
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg028 = data.readStrongBinder();
                    boolean releaseWifiLock = releaseWifiLock(_arg028);
                    reply.writeNoException();
                    reply.writeInt(releaseWifiLock ? 1 : 0);
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    initializeMulticastFiltering();
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isMulticastEnabled = isMulticastEnabled();
                    reply.writeNoException();
                    reply.writeInt(isMulticastEnabled ? 1 : 0);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg029 = data.readStrongBinder();
                    String _arg110 = data.readString();
                    acquireMulticastLock(_arg029, _arg110);
                    reply.writeNoException();
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg030 = data.readString();
                    releaseMulticastLock(_arg030);
                    reply.writeNoException();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg031 = data.readString();
                    int _arg111 = data.readInt();
                    updateInterfaceIpState(_arg031, _arg111);
                    reply.writeNoException();
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    WifiConfiguration _arg032 = data.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(data) : null;
                    boolean startSoftAp = startSoftAp(_arg032);
                    reply.writeNoException();
                    reply.writeInt(startSoftAp ? 1 : 0);
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    boolean stopSoftAp = stopSoftAp();
                    reply.writeNoException();
                    reply.writeInt(stopSoftAp ? 1 : 0);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    Messenger _arg033 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    IBinder _arg112 = data.readStrongBinder();
                    String _arg23 = data.readString();
                    int _result17 = startLocalOnlyHotspot(_arg033, _arg112, _arg23);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    stopLocalOnlyHotspot();
                    reply.writeNoException();
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    Messenger _arg034 = data.readInt() != 0 ? Messenger.CREATOR.createFromParcel(data) : null;
                    IBinder _arg113 = data.readStrongBinder();
                    startWatchLocalOnlyHotspot(_arg034, _arg113);
                    reply.writeNoException();
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    stopWatchLocalOnlyHotspot();
                    reply.writeNoException();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    int _result18 = getWifiApEnabledState();
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    WifiConfiguration _result19 = getWifiApConfiguration();
                    reply.writeNoException();
                    if (_result19 != null) {
                        reply.writeInt(1);
                        _result19.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    WifiConfiguration _arg035 = data.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(data) : null;
                    String _arg114 = data.readString();
                    boolean wifiApConfiguration = setWifiApConfiguration(_arg035, _arg114);
                    reply.writeNoException();
                    reply.writeInt(wifiApConfiguration ? 1 : 0);
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg036 = data.readString();
                    notifyUserOfApBandConversion(_arg036);
                    reply.writeNoException();
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg037 = data.readString();
                    Messenger _result20 = getWifiServiceMessenger(_arg037);
                    reply.writeNoException();
                    if (_result20 != null) {
                        reply.writeInt(1);
                        _result20.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg038 = data.readString();
                    _arg3 = data.readInt() != 0;
                    enableTdls(_arg038, _arg3);
                    reply.writeNoException();
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg039 = data.readString();
                    _arg3 = data.readInt() != 0;
                    enableTdlsWithMacAddress(_arg039, _arg3);
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    String _result21 = getCurrentNetworkWpsNfcConfigurationToken();
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg040 = data.readInt();
                    enableVerboseLogging(_arg040);
                    reply.writeNoException();
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    int _result22 = getVerboseLoggingLevel();
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    _arg3 = data.readInt() != 0;
                    boolean _arg041 = _arg3;
                    enableWifiConnectivityManager(_arg041);
                    reply.writeNoException();
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg042 = data.readString();
                    String _arg115 = data.readString();
                    disableEphemeralNetwork(_arg042, _arg115);
                    reply.writeNoException();
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg043 = data.readString();
                    factoryReset(_arg043);
                    reply.writeNoException();
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    Network _result23 = getCurrentNetwork();
                    reply.writeNoException();
                    if (_result23 != null) {
                        reply.writeInt(1);
                        _result23.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result24 = retrieveBackupData();
                    reply.writeNoException();
                    reply.writeByteArray(_result24);
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg044 = data.createByteArray();
                    restoreBackupData(_arg044);
                    reply.writeNoException();
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg045 = data.createByteArray();
                    byte[] _arg116 = data.createByteArray();
                    restoreSupplicantBackupData(_arg045, _arg116);
                    reply.writeNoException();
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    OsuProvider _arg046 = data.readInt() != 0 ? OsuProvider.CREATOR.createFromParcel(data) : null;
                    IProvisioningCallback _arg117 = IProvisioningCallback.Stub.asInterface(data.readStrongBinder());
                    startSubscriptionProvisioning(_arg046, _arg117);
                    reply.writeNoException();
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg047 = data.readStrongBinder();
                    ISoftApCallback _arg118 = ISoftApCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg24 = data.readInt();
                    registerSoftApCallback(_arg047, _arg118, _arg24);
                    reply.writeNoException();
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg048 = data.readInt();
                    unregisterSoftApCallback(_arg048);
                    reply.writeNoException();
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg049 = data.readStrongBinder();
                    IOnWifiUsabilityStatsListener _arg119 = IOnWifiUsabilityStatsListener.Stub.asInterface(data.readStrongBinder());
                    int _arg25 = data.readInt();
                    addOnWifiUsabilityStatsListener(_arg049, _arg119, _arg25);
                    reply.writeNoException();
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg050 = data.readInt();
                    removeOnWifiUsabilityStatsListener(_arg050);
                    reply.writeNoException();
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg051 = data.readStrongBinder();
                    ITrafficStateCallback _arg120 = ITrafficStateCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg26 = data.readInt();
                    registerTrafficStateCallback(_arg051, _arg120, _arg26);
                    reply.writeNoException();
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg052 = data.readInt();
                    unregisterTrafficStateCallback(_arg052);
                    reply.writeNoException();
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg053 = data.readString();
                    String _result25 = getCapabilities(_arg053);
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg054 = data.readString();
                    int _result26 = dppAddBootstrapQrCode(_arg054);
                    reply.writeNoException();
                    reply.writeInt(_result26);
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    WifiDppConfig _arg055 = data.readInt() != 0 ? WifiDppConfig.CREATOR.createFromParcel(data) : null;
                    int _result27 = dppBootstrapGenerate(_arg055);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 75:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg056 = data.readInt();
                    String _result28 = dppGetUri(_arg056);
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 76:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg057 = data.readInt();
                    int _result29 = dppBootstrapRemove(_arg057);
                    reply.writeNoException();
                    reply.writeInt(_result29);
                    return true;
                case 77:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg058 = data.readString();
                    int _arg121 = data.readInt();
                    boolean _arg27 = data.readInt() != 0;
                    _arg3 = data.readInt() != 0;
                    int _result30 = dppListen(_arg058, _arg121, _arg27, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result30);
                    return true;
                case 78:
                    data.enforceInterface(DESCRIPTOR);
                    dppStopListen();
                    reply.writeNoException();
                    return true;
                case 79:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg059 = data.readString();
                    String _arg122 = data.readString();
                    int _arg28 = data.readInt();
                    int _result31 = dppConfiguratorAdd(_arg059, _arg122, _arg28);
                    reply.writeNoException();
                    reply.writeInt(_result31);
                    return true;
                case 80:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg060 = data.readInt();
                    int _result32 = dppConfiguratorRemove(_arg060);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 81:
                    data.enforceInterface(DESCRIPTOR);
                    WifiDppConfig _arg061 = data.readInt() != 0 ? WifiDppConfig.CREATOR.createFromParcel(data) : null;
                    int _result33 = dppStartAuth(_arg061);
                    reply.writeNoException();
                    reply.writeInt(_result33);
                    return true;
                case 82:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg062 = data.readInt();
                    String _result34 = dppConfiguratorGetKey(_arg062);
                    reply.writeNoException();
                    reply.writeString(_result34);
                    return true;
                case 83:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isExtendingWifi = isExtendingWifi();
                    reply.writeNoException();
                    reply.writeInt(isExtendingWifi ? 1 : 0);
                    return true;
                case 84:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isWifiCoverageExtendFeatureEnabled = isWifiCoverageExtendFeatureEnabled();
                    reply.writeNoException();
                    reply.writeInt(isWifiCoverageExtendFeatureEnabled ? 1 : 0);
                    return true;
                case 85:
                    data.enforceInterface(DESCRIPTOR);
                    _arg3 = data.readInt() != 0;
                    boolean _arg063 = _arg3;
                    enableWifiCoverageExtendFeature(_arg063);
                    reply.writeNoException();
                    return true;
                case 86:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg064 = data.readStrongBinder();
                    INetworkRequestMatchCallback _arg123 = INetworkRequestMatchCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg29 = data.readInt();
                    registerNetworkRequestMatchCallback(_arg064, _arg123, _arg29);
                    reply.writeNoException();
                    return true;
                case 87:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg065 = data.readInt();
                    unregisterNetworkRequestMatchCallback(_arg065);
                    reply.writeNoException();
                    return true;
                case 88:
                    data.enforceInterface(DESCRIPTOR);
                    List<WifiNetworkSuggestion> _arg066 = data.createTypedArrayList(WifiNetworkSuggestion.CREATOR);
                    String _arg124 = data.readString();
                    int _result35 = addNetworkSuggestions(_arg066, _arg124);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 89:
                    data.enforceInterface(DESCRIPTOR);
                    List<WifiNetworkSuggestion> _arg067 = data.createTypedArrayList(WifiNetworkSuggestion.CREATOR);
                    String _arg125 = data.readString();
                    int _result36 = removeNetworkSuggestions(_arg067, _arg125);
                    reply.writeNoException();
                    reply.writeInt(_result36);
                    return true;
                case 90:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result37 = getFactoryMacAddresses();
                    reply.writeNoException();
                    reply.writeStringArray(_result37);
                    return true;
                case 91:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg068 = data.readInt();
                    setDeviceMobilityState(_arg068);
                    reply.writeNoException();
                    return true;
                case 92:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg069 = data.readStrongBinder();
                    String _arg126 = data.readString();
                    int _arg210 = data.readInt();
                    int _arg32 = data.readInt();
                    IDppCallback _arg4 = IDppCallback.Stub.asInterface(data.readStrongBinder());
                    startDppAsConfiguratorInitiator(_arg069, _arg126, _arg210, _arg32, _arg4);
                    reply.writeNoException();
                    return true;
                case 93:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg070 = data.readStrongBinder();
                    String _arg127 = data.readString();
                    IDppCallback _arg211 = IDppCallback.Stub.asInterface(data.readStrongBinder());
                    startDppAsEnrolleeInitiator(_arg070, _arg127, _arg211);
                    reply.writeNoException();
                    return true;
                case 94:
                    data.enforceInterface(DESCRIPTOR);
                    stopDppSession();
                    reply.writeNoException();
                    return true;
                case 95:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg071 = data.readInt();
                    int _arg128 = data.readInt();
                    int _arg212 = data.readInt();
                    updateWifiUsabilityScore(_arg071, _arg128, _arg212);
                    reply.writeNoException();
                    return true;
                case 96:
                    data.enforceInterface(DESCRIPTOR);
                    int _result38 = getSoftApWifiGeneration();
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IWifiManager {
            public static IWifiManager sDefaultImpl;
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

            @Override // android.net.wifi.IWifiManager
            public long getSupportedFeatures() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedFeatures();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
                WifiActivityEnergyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reportActivityInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiActivityEnergyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void requestActivityInfo(ResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestActivityInfo(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public ParceledListSlice getConfiguredNetworks(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfiguredNetworks(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public ParceledListSlice getPrivilegedConfiguredNetworks(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrivilegedConfiguredNetworks(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public Map getAllMatchingFqdnsForScanResults(List<ScanResult> scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResult);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllMatchingFqdnsForScanResults(scanResult);
                    }
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public Map getMatchingOsuProviders(List<ScanResult> scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResult);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMatchingOsuProviders(scanResult);
                    }
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> osuProviders) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(osuProviders);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMatchingPasspointConfigsForOsuProviders(osuProviders);
                    }
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int addOrUpdateNetwork(WifiConfiguration config, String packageName) throws RemoteException {
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
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOrUpdateNetwork(config, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration config, String packageName) throws RemoteException {
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
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOrUpdatePasspointConfiguration(config, packageName);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean removePasspointConfiguration(String fqdn, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fqdn);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removePasspointConfiguration(fqdn, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public List<PasspointConfiguration> getPasspointConfigurations(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasspointConfigurations(packageName);
                    }
                    _reply.readException();
                    List<PasspointConfiguration> _result = _reply.createTypedArrayList(PasspointConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> fqdnList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(fqdnList);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiConfigsForPasspointProfiles(fqdnList);
                    }
                    _reply.readException();
                    List<WifiConfiguration> _result = _reply.createTypedArrayList(WifiConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void queryPasspointIcon(long bssid, String fileName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(bssid);
                    _data.writeString(fileName);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().queryPasspointIcon(bssid, fileName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int matchProviderWithCurrentNetwork(String fqdn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fqdn);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().matchProviderWithCurrentNetwork(fqdn);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void deauthenticateNetwork(long holdoff, boolean ess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(holdoff);
                    _data.writeInt(ess ? 1 : 0);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deauthenticateNetwork(holdoff, ess);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean removeNetwork(int netId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeNetwork(netId, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean enableNetwork(int netId, boolean disableOthers, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeInt(disableOthers ? 1 : 0);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableNetwork(netId, disableOthers, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean disableNetwork(int netId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableNetwork(netId, packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean startScan(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startScan(packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public List<ScanResult> getScanResults(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScanResults(callingPackage);
                    }
                    _reply.readException();
                    List<ScanResult> _result = _reply.createTypedArrayList(ScanResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean disconnect(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnect(packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean reconnect(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reconnect(packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean reassociate(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reassociate(packageName);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public WifiInfo getConnectionInfo(String callingPackage) throws RemoteException {
                WifiInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionInfo(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean setWifiEnabled(String packageName, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWifiEnabled(packageName, enable);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int getWifiEnabledState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiEnabledState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void setCountryCode(String country) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(country);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCountryCode(country);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String getCountryCode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCountryCode();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean isDualBandSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDualBandSupported();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().needs5GHzToAnyApBandConversion();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public DhcpInfo getDhcpInfo() throws RemoteException {
                DhcpInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDhcpInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DhcpInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean isScanAlwaysAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isScanAlwaysAvailable();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean acquireWifiLock(IBinder lock, int lockType, String tag, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(lockType);
                    _data.writeString(tag);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().acquireWifiLock(lock, lockType, tag, ws);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void updateWifiLockWorkSource(IBinder lock, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWifiLockWorkSource(lock, ws);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean releaseWifiLock(IBinder lock) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().releaseWifiLock(lock);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void initializeMulticastFiltering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initializeMulticastFiltering();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean isMulticastEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMulticastEnabled();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void acquireMulticastLock(IBinder binder, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acquireMulticastLock(binder, tag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void releaseMulticastLock(String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseMulticastLock(tag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void updateInterfaceIpState(String ifaceName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateInterfaceIpState(ifaceName, mode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean startSoftAp(WifiConfiguration wifiConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfig != null) {
                        _data.writeInt(1);
                        wifiConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startSoftAp(wifiConfig);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean stopSoftAp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopSoftAp();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int startLocalOnlyHotspot(Messenger messenger, IBinder binder, String packageName) throws RemoteException {
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
                    _data.writeStrongBinder(binder);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startLocalOnlyHotspot(messenger, binder, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void stopLocalOnlyHotspot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopLocalOnlyHotspot();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder binder) throws RemoteException {
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
                    _data.writeStrongBinder(binder);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchLocalOnlyHotspot(messenger, binder);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void stopWatchLocalOnlyHotspot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWatchLocalOnlyHotspot();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int getWifiApEnabledState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiApEnabledState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public WifiConfiguration getWifiApConfiguration() throws RemoteException {
                WifiConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiApConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean setWifiApConfiguration(WifiConfiguration wifiConfig, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfig != null) {
                        _data.writeInt(1);
                        wifiConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWifiApConfiguration(wifiConfig, packageName);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void notifyUserOfApBandConversion(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyUserOfApBandConversion(packageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public Messenger getWifiServiceMessenger(String packageName) throws RemoteException {
                Messenger _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiServiceMessenger(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Messenger.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void enableTdls(String remoteIPAddress, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(remoteIPAddress);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableTdls(remoteIPAddress, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void enableTdlsWithMacAddress(String remoteMacAddress, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(remoteMacAddress);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableTdlsWithMacAddress(remoteMacAddress, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentNetworkWpsNfcConfigurationToken();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void enableVerboseLogging(int verbose) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(verbose);
                    boolean _status = this.mRemote.transact(56, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableVerboseLogging(verbose);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int getVerboseLoggingLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(57, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVerboseLoggingLevel();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void enableWifiConnectivityManager(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(58, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableWifiConnectivityManager(enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void disableEphemeralNetwork(String SSID, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(SSID);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(59, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableEphemeralNetwork(SSID, packageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void factoryReset(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(60, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset(packageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public Network getCurrentNetwork() throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(61, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentNetwork();
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

            @Override // android.net.wifi.IWifiManager
            public byte[] retrieveBackupData() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(62, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().retrieveBackupData();
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void restoreBackupData(byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(data);
                    boolean _status = this.mRemote.transact(63, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreBackupData(data);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void restoreSupplicantBackupData(byte[] supplicantData, byte[] ipConfigData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(supplicantData);
                    _data.writeByteArray(ipConfigData);
                    boolean _status = this.mRemote.transact(64, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreSupplicantBackupData(supplicantData, ipConfigData);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void startSubscriptionProvisioning(OsuProvider provider, IProvisioningCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provider != null) {
                        _data.writeInt(1);
                        provider.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(65, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSubscriptionProvisioning(provider, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void registerSoftApCallback(IBinder binder, ISoftApCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(66, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerSoftApCallback(binder, callback, callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void unregisterSoftApCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(67, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterSoftApCallback(callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void addOnWifiUsabilityStatsListener(IBinder binder, IOnWifiUsabilityStatsListener listener, int listenerIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(listenerIdentifier);
                    boolean _status = this.mRemote.transact(68, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnWifiUsabilityStatsListener(binder, listener, listenerIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void removeOnWifiUsabilityStatsListener(int listenerIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(listenerIdentifier);
                    boolean _status = this.mRemote.transact(69, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnWifiUsabilityStatsListener(listenerIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void registerTrafficStateCallback(IBinder binder, ITrafficStateCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(70, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTrafficStateCallback(binder, callback, callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void unregisterTrafficStateCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(71, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTrafficStateCallback(callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String getCapabilities(String capaType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(capaType);
                    boolean _status = this.mRemote.transact(72, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCapabilities(capaType);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppAddBootstrapQrCode(String uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    boolean _status = this.mRemote.transact(73, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppAddBootstrapQrCode(uri);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppBootstrapGenerate(WifiDppConfig config) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(74, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppBootstrapGenerate(config);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String dppGetUri(int bootstrap_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bootstrap_id);
                    boolean _status = this.mRemote.transact(75, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppGetUri(bootstrap_id);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppBootstrapRemove(int bootstrap_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bootstrap_id);
                    boolean _status = this.mRemote.transact(76, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppBootstrapRemove(bootstrap_id);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppListen(String frequency, int dpp_role, boolean qr_mutual, boolean netrole_ap) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(frequency);
                    _data.writeInt(dpp_role);
                    _data.writeInt(qr_mutual ? 1 : 0);
                    _data.writeInt(netrole_ap ? 1 : 0);
                    boolean _status = this.mRemote.transact(77, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppListen(frequency, dpp_role, qr_mutual, netrole_ap);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void dppStopListen() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(78, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dppStopListen();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppConfiguratorAdd(String curve, String key, int expiry) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(curve);
                    _data.writeString(key);
                    _data.writeInt(expiry);
                    boolean _status = this.mRemote.transact(79, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorAdd(curve, key, expiry);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppConfiguratorRemove(int config_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(config_id);
                    boolean _status = this.mRemote.transact(80, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorRemove(config_id);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int dppStartAuth(WifiDppConfig config) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(81, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppStartAuth(config);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String dppConfiguratorGetKey(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    boolean _status = this.mRemote.transact(82, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorGetKey(id);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean isExtendingWifi() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(83, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isExtendingWifi();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public boolean isWifiCoverageExtendFeatureEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(84, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWifiCoverageExtendFeatureEnabled();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void enableWifiCoverageExtendFeature(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(85, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableWifiCoverageExtendFeature(enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void registerNetworkRequestMatchCallback(IBinder binder, INetworkRequestMatchCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(86, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerNetworkRequestMatchCallback(binder, callback, callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void unregisterNetworkRequestMatchCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    boolean _status = this.mRemote.transact(87, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkRequestMatchCallback(callbackIdentifier);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int addNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(networkSuggestions);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(88, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addNetworkSuggestions(networkSuggestions, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int removeNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(networkSuggestions);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(89, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeNetworkSuggestions(networkSuggestions, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public String[] getFactoryMacAddresses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(90, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFactoryMacAddresses();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void setDeviceMobilityState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(91, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceMobilityState(state);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void startDppAsConfiguratorInitiator(IBinder binder, String enrolleeUri, int selectedNetworkId, int netRole, IDppCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(enrolleeUri);
                    _data.writeInt(selectedNetworkId);
                    _data.writeInt(netRole);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(92, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDppAsConfiguratorInitiator(binder, enrolleeUri, selectedNetworkId, netRole, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void startDppAsEnrolleeInitiator(IBinder binder, String configuratorUri, IDppCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(configuratorUri);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(93, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDppAsEnrolleeInitiator(binder, configuratorUri, callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void stopDppSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(94, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDppSession();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public void updateWifiUsabilityScore(int seqNum, int score, int predictionHorizonSec) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seqNum);
                    _data.writeInt(score);
                    _data.writeInt(predictionHorizonSec);
                    boolean _status = this.mRemote.transact(95, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWifiUsabilityScore(seqNum, score, predictionHorizonSec);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IWifiManager
            public int getSoftApWifiGeneration() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(96, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoftApWifiGeneration();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWifiManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IWifiManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
