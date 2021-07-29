package android.net.wifi;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import java.util.List;
import java.util.Map;

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

    public static class Default implements IWifiManager {
        public long getSupportedFeatures() throws RemoteException {
            return 0;
        }

        public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
            return null;
        }

        public void requestActivityInfo(ResultReceiver result) throws RemoteException {
        }

        public ParceledListSlice getConfiguredNetworks(String packageName) throws RemoteException {
            return null;
        }

        public ParceledListSlice getPrivilegedConfiguredNetworks(String packageName) throws RemoteException {
            return null;
        }

        public Map getAllMatchingFqdnsForScanResults(List<ScanResult> list) throws RemoteException {
            return null;
        }

        public Map getMatchingOsuProviders(List<ScanResult> list) throws RemoteException {
            return null;
        }

        public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> list) throws RemoteException {
            return null;
        }

        public int addOrUpdateNetwork(WifiConfiguration config, String packageName) throws RemoteException {
            return 0;
        }

        public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration config, String packageName) throws RemoteException {
            return false;
        }

        public boolean removePasspointConfiguration(String fqdn, String packageName) throws RemoteException {
            return false;
        }

        public List<PasspointConfiguration> getPasspointConfigurations(String packageName) throws RemoteException {
            return null;
        }

        public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> list) throws RemoteException {
            return null;
        }

        public void queryPasspointIcon(long bssid, String fileName) throws RemoteException {
        }

        public int matchProviderWithCurrentNetwork(String fqdn) throws RemoteException {
            return 0;
        }

        public void deauthenticateNetwork(long holdoff, boolean ess) throws RemoteException {
        }

        public boolean removeNetwork(int netId, String packageName) throws RemoteException {
            return false;
        }

        public boolean enableNetwork(int netId, boolean disableOthers, String packageName) throws RemoteException {
            return false;
        }

        public boolean disableNetwork(int netId, String packageName) throws RemoteException {
            return false;
        }

        public boolean startScan(String packageName) throws RemoteException {
            return false;
        }

        public List<ScanResult> getScanResults(String callingPackage) throws RemoteException {
            return null;
        }

        public boolean disconnect(String packageName) throws RemoteException {
            return false;
        }

        public boolean reconnect(String packageName) throws RemoteException {
            return false;
        }

        public boolean reassociate(String packageName) throws RemoteException {
            return false;
        }

        public WifiInfo getConnectionInfo(String callingPackage) throws RemoteException {
            return null;
        }

        public boolean setWifiEnabled(String packageName, boolean enable) throws RemoteException {
            return false;
        }

        public int getWifiEnabledState() throws RemoteException {
            return 0;
        }

        public void setCountryCode(String country) throws RemoteException {
        }

        public String getCountryCode() throws RemoteException {
            return null;
        }

        public boolean isDualBandSupported() throws RemoteException {
            return false;
        }

        public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
            return false;
        }

        public DhcpInfo getDhcpInfo() throws RemoteException {
            return null;
        }

        public boolean isScanAlwaysAvailable() throws RemoteException {
            return false;
        }

        public boolean acquireWifiLock(IBinder lock, int lockType, String tag, WorkSource ws) throws RemoteException {
            return false;
        }

        public void updateWifiLockWorkSource(IBinder lock, WorkSource ws) throws RemoteException {
        }

        public boolean releaseWifiLock(IBinder lock) throws RemoteException {
            return false;
        }

        public void initializeMulticastFiltering() throws RemoteException {
        }

        public boolean isMulticastEnabled() throws RemoteException {
            return false;
        }

        public void acquireMulticastLock(IBinder binder, String tag) throws RemoteException {
        }

        public void releaseMulticastLock(String tag) throws RemoteException {
        }

        public void updateInterfaceIpState(String ifaceName, int mode) throws RemoteException {
        }

        public boolean startSoftAp(WifiConfiguration wifiConfig) throws RemoteException {
            return false;
        }

        public boolean stopSoftAp() throws RemoteException {
            return false;
        }

        public int startLocalOnlyHotspot(Messenger messenger, IBinder binder, String packageName) throws RemoteException {
            return 0;
        }

        public void stopLocalOnlyHotspot() throws RemoteException {
        }

        public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder binder) throws RemoteException {
        }

        public void stopWatchLocalOnlyHotspot() throws RemoteException {
        }

        public int getWifiApEnabledState() throws RemoteException {
            return 0;
        }

        public WifiConfiguration getWifiApConfiguration() throws RemoteException {
            return null;
        }

        public boolean setWifiApConfiguration(WifiConfiguration wifiConfig, String packageName) throws RemoteException {
            return false;
        }

        public void notifyUserOfApBandConversion(String packageName) throws RemoteException {
        }

        public Messenger getWifiServiceMessenger(String packageName) throws RemoteException {
            return null;
        }

        public void enableTdls(String remoteIPAddress, boolean enable) throws RemoteException {
        }

        public void enableTdlsWithMacAddress(String remoteMacAddress, boolean enable) throws RemoteException {
        }

        public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
            return null;
        }

        public void enableVerboseLogging(int verbose) throws RemoteException {
        }

        public int getVerboseLoggingLevel() throws RemoteException {
            return 0;
        }

        public void enableWifiConnectivityManager(boolean enabled) throws RemoteException {
        }

        public void disableEphemeralNetwork(String SSID, String packageName) throws RemoteException {
        }

        public void factoryReset(String packageName) throws RemoteException {
        }

        public Network getCurrentNetwork() throws RemoteException {
            return null;
        }

        public byte[] retrieveBackupData() throws RemoteException {
            return null;
        }

        public void restoreBackupData(byte[] data) throws RemoteException {
        }

        public void restoreSupplicantBackupData(byte[] supplicantData, byte[] ipConfigData) throws RemoteException {
        }

        public void startSubscriptionProvisioning(OsuProvider provider, IProvisioningCallback callback) throws RemoteException {
        }

        public void registerSoftApCallback(IBinder binder, ISoftApCallback callback, int callbackIdentifier) throws RemoteException {
        }

        public void unregisterSoftApCallback(int callbackIdentifier) throws RemoteException {
        }

        public void addOnWifiUsabilityStatsListener(IBinder binder, IOnWifiUsabilityStatsListener listener, int listenerIdentifier) throws RemoteException {
        }

        public void removeOnWifiUsabilityStatsListener(int listenerIdentifier) throws RemoteException {
        }

        public void registerTrafficStateCallback(IBinder binder, ITrafficStateCallback callback, int callbackIdentifier) throws RemoteException {
        }

        public void unregisterTrafficStateCallback(int callbackIdentifier) throws RemoteException {
        }

        public String getCapabilities(String capaType) throws RemoteException {
            return null;
        }

        public int dppAddBootstrapQrCode(String uri) throws RemoteException {
            return 0;
        }

        public int dppBootstrapGenerate(WifiDppConfig config) throws RemoteException {
            return 0;
        }

        public String dppGetUri(int bootstrap_id) throws RemoteException {
            return null;
        }

        public int dppBootstrapRemove(int bootstrap_id) throws RemoteException {
            return 0;
        }

        public int dppListen(String frequency, int dpp_role, boolean qr_mutual, boolean netrole_ap) throws RemoteException {
            return 0;
        }

        public void dppStopListen() throws RemoteException {
        }

        public int dppConfiguratorAdd(String curve, String key, int expiry) throws RemoteException {
            return 0;
        }

        public int dppConfiguratorRemove(int config_id) throws RemoteException {
            return 0;
        }

        public int dppStartAuth(WifiDppConfig config) throws RemoteException {
            return 0;
        }

        public String dppConfiguratorGetKey(int id) throws RemoteException {
            return null;
        }

        public boolean isExtendingWifi() throws RemoteException {
            return false;
        }

        public boolean isWifiCoverageExtendFeatureEnabled() throws RemoteException {
            return false;
        }

        public void enableWifiCoverageExtendFeature(boolean enable) throws RemoteException {
        }

        public void registerNetworkRequestMatchCallback(IBinder binder, INetworkRequestMatchCallback callback, int callbackIdentifier) throws RemoteException {
        }

        public void unregisterNetworkRequestMatchCallback(int callbackIdentifier) throws RemoteException {
        }

        public int addNetworkSuggestions(List<WifiNetworkSuggestion> list, String packageName) throws RemoteException {
            return 0;
        }

        public int removeNetworkSuggestions(List<WifiNetworkSuggestion> list, String packageName) throws RemoteException {
            return 0;
        }

        public String[] getFactoryMacAddresses() throws RemoteException {
            return null;
        }

        public void setDeviceMobilityState(int state) throws RemoteException {
        }

        public void startDppAsConfiguratorInitiator(IBinder binder, String enrolleeUri, int selectedNetworkId, int netRole, IDppCallback callback) throws RemoteException {
        }

        public void startDppAsEnrolleeInitiator(IBinder binder, String configuratorUri, IDppCallback callback) throws RemoteException {
        }

        public void stopDppSession() throws RemoteException {
        }

        public void updateWifiUsabilityScore(int seqNum, int score, int predictionHorizonSec) throws RemoteException {
        }

        public int getSoftApWifiGeneration() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IWifiManager)) {
                return new Proxy(obj);
            }
            return (IWifiManager) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.net.wifi.WifiConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.net.wifi.hotspot2.PasspointConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v61, resolved type: android.net.wifi.WifiConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v66, resolved type: android.os.Messenger} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v70, resolved type: android.os.Messenger} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v76, resolved type: android.net.wifi.WifiConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v95, resolved type: android.net.wifi.hotspot2.OsuProvider} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v107, resolved type: android.net.wifi.WifiDppConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v116, resolved type: android.net.wifi.WifiDppConfig} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v139 */
        /* JADX WARNING: type inference failed for: r0v140 */
        /* JADX WARNING: type inference failed for: r0v141 */
        /* JADX WARNING: type inference failed for: r0v142 */
        /* JADX WARNING: type inference failed for: r0v143 */
        /* JADX WARNING: type inference failed for: r0v144 */
        /* JADX WARNING: type inference failed for: r0v145 */
        /* JADX WARNING: type inference failed for: r0v146 */
        /* JADX WARNING: type inference failed for: r0v147 */
        /* JADX WARNING: type inference failed for: r0v148 */
        /* JADX WARNING: type inference failed for: r0v149 */
        /* JADX WARNING: type inference failed for: r0v150 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.net.wifi.IWifiManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x07cd
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x07bf;
                    case 2: goto L_0x07a8;
                    case 3: goto L_0x0791;
                    case 4: goto L_0x0776;
                    case 5: goto L_0x075b;
                    case 6: goto L_0x0747;
                    case 7: goto L_0x0733;
                    case 8: goto L_0x071f;
                    case 9: goto L_0x06fd;
                    case 10: goto L_0x06db;
                    case 11: goto L_0x06c5;
                    case 12: goto L_0x06b3;
                    case 13: goto L_0x06a1;
                    case 14: goto L_0x068f;
                    case 15: goto L_0x067d;
                    case 16: goto L_0x0666;
                    case 17: goto L_0x0650;
                    case 18: goto L_0x0632;
                    case 19: goto L_0x061c;
                    case 20: goto L_0x060a;
                    case 21: goto L_0x05f8;
                    case 22: goto L_0x05e6;
                    case 23: goto L_0x05d4;
                    case 24: goto L_0x05c2;
                    case 25: goto L_0x05a7;
                    case 26: goto L_0x058d;
                    case 27: goto L_0x057f;
                    case 28: goto L_0x0571;
                    case 29: goto L_0x0563;
                    case 30: goto L_0x0555;
                    case 31: goto L_0x0547;
                    case 32: goto L_0x0530;
                    case 33: goto L_0x0522;
                    case 34: goto L_0x04f8;
                    case 35: goto L_0x04da;
                    case 36: goto L_0x04c8;
                    case 37: goto L_0x04be;
                    case 38: goto L_0x04b0;
                    case 39: goto L_0x049e;
                    case 40: goto L_0x0490;
                    case 41: goto L_0x047e;
                    case 42: goto L_0x0460;
                    case 43: goto L_0x0452;
                    case 44: goto L_0x042c;
                    case 45: goto L_0x0422;
                    case 46: goto L_0x0404;
                    case 47: goto L_0x03fa;
                    case 48: goto L_0x03ec;
                    case 49: goto L_0x03d5;
                    case 50: goto L_0x03b3;
                    case 51: goto L_0x03a5;
                    case 52: goto L_0x038a;
                    case 53: goto L_0x0374;
                    case 54: goto L_0x035e;
                    case 55: goto L_0x0350;
                    case 56: goto L_0x0342;
                    case 57: goto L_0x0334;
                    case 58: goto L_0x0321;
                    case 59: goto L_0x030f;
                    case 60: goto L_0x0301;
                    case 61: goto L_0x02ea;
                    case 62: goto L_0x02dc;
                    case 63: goto L_0x02ce;
                    case 64: goto L_0x02bc;
                    case 65: goto L_0x029a;
                    case 66: goto L_0x0280;
                    case 67: goto L_0x0272;
                    case 68: goto L_0x0258;
                    case 69: goto L_0x024a;
                    case 70: goto L_0x0230;
                    case 71: goto L_0x0222;
                    case 72: goto L_0x0210;
                    case 73: goto L_0x01fe;
                    case 74: goto L_0x01e0;
                    case 75: goto L_0x01ce;
                    case 76: goto L_0x01bc;
                    case 77: goto L_0x0195;
                    case 78: goto L_0x018b;
                    case 79: goto L_0x0171;
                    case 80: goto L_0x015f;
                    case 81: goto L_0x0141;
                    case 82: goto L_0x012f;
                    case 83: goto L_0x0121;
                    case 84: goto L_0x0113;
                    case 85: goto L_0x0100;
                    case 86: goto L_0x00e6;
                    case 87: goto L_0x00d8;
                    case 88: goto L_0x00c0;
                    case 89: goto L_0x00a8;
                    case 90: goto L_0x009a;
                    case 91: goto L_0x008c;
                    case 92: goto L_0x0062;
                    case 93: goto L_0x0048;
                    case 94: goto L_0x003e;
                    case 95: goto L_0x0028;
                    case 96: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r0 = r17.getSoftApWifiGeneration()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0028:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                r6.updateWifiUsabilityScore(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x003e:
                r8.enforceInterface(r10)
                r17.stopDppSession()
                r20.writeNoException()
                return r11
            L_0x0048:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r1 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.net.wifi.IDppCallback r2 = android.net.wifi.IDppCallback.Stub.asInterface(r2)
                r6.startDppAsEnrolleeInitiator(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0062:
                r8.enforceInterface(r10)
                android.os.IBinder r12 = r19.readStrongBinder()
                java.lang.String r13 = r19.readString()
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                android.os.IBinder r0 = r19.readStrongBinder()
                android.net.wifi.IDppCallback r16 = android.net.wifi.IDppCallback.Stub.asInterface(r0)
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.startDppAsConfiguratorInitiator(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x008c:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.setDeviceMobilityState(r0)
                r20.writeNoException()
                return r11
            L_0x009a:
                r8.enforceInterface(r10)
                java.lang.String[] r0 = r17.getFactoryMacAddresses()
                r20.writeNoException()
                r9.writeStringArray(r0)
                return r11
            L_0x00a8:
                r8.enforceInterface(r10)
                android.os.Parcelable$Creator<android.net.wifi.WifiNetworkSuggestion> r0 = android.net.wifi.WifiNetworkSuggestion.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                java.lang.String r1 = r19.readString()
                int r2 = r6.removeNetworkSuggestions(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x00c0:
                r8.enforceInterface(r10)
                android.os.Parcelable$Creator<android.net.wifi.WifiNetworkSuggestion> r0 = android.net.wifi.WifiNetworkSuggestion.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                java.lang.String r1 = r19.readString()
                int r2 = r6.addNetworkSuggestions(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x00d8:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.unregisterNetworkRequestMatchCallback(r0)
                r20.writeNoException()
                return r11
            L_0x00e6:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.wifi.INetworkRequestMatchCallback r1 = android.net.wifi.INetworkRequestMatchCallback.Stub.asInterface(r1)
                int r2 = r19.readInt()
                r6.registerNetworkRequestMatchCallback(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0100:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x010b
                r1 = r11
            L_0x010b:
                r0 = r1
                r6.enableWifiCoverageExtendFeature(r0)
                r20.writeNoException()
                return r11
            L_0x0113:
                r8.enforceInterface(r10)
                boolean r0 = r17.isWifiCoverageExtendFeatureEnabled()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0121:
                r8.enforceInterface(r10)
                boolean r0 = r17.isExtendingWifi()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x012f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.lang.String r1 = r6.dppConfiguratorGetKey(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0141:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0153
                android.os.Parcelable$Creator<android.net.wifi.WifiDppConfig> r0 = android.net.wifi.WifiDppConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.WifiDppConfig r0 = (android.net.wifi.WifiDppConfig) r0
                goto L_0x0154
            L_0x0153:
            L_0x0154:
                int r1 = r6.dppStartAuth(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x015f:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.dppConfiguratorRemove(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0171:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                int r3 = r6.dppConfiguratorAdd(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x018b:
                r8.enforceInterface(r10)
                r17.dppStopListen()
                r20.writeNoException()
                return r11
            L_0x0195:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x01a8
                r3 = r11
                goto L_0x01a9
            L_0x01a8:
                r3 = r1
            L_0x01a9:
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x01b1
                r1 = r11
            L_0x01b1:
                int r4 = r6.dppListen(r0, r2, r3, r1)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x01bc:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r6.dppBootstrapRemove(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x01ce:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.lang.String r1 = r6.dppGetUri(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x01e0:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x01f2
                android.os.Parcelable$Creator<android.net.wifi.WifiDppConfig> r0 = android.net.wifi.WifiDppConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.WifiDppConfig r0 = (android.net.wifi.WifiDppConfig) r0
                goto L_0x01f3
            L_0x01f2:
            L_0x01f3:
                int r1 = r6.dppBootstrapGenerate(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x01fe:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r6.dppAddBootstrapQrCode(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0210:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r6.getCapabilities(r0)
                r20.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0222:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.unregisterTrafficStateCallback(r0)
                r20.writeNoException()
                return r11
            L_0x0230:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.wifi.ITrafficStateCallback r1 = android.net.wifi.ITrafficStateCallback.Stub.asInterface(r1)
                int r2 = r19.readInt()
                r6.registerTrafficStateCallback(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x024a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.removeOnWifiUsabilityStatsListener(r0)
                r20.writeNoException()
                return r11
            L_0x0258:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.wifi.IOnWifiUsabilityStatsListener r1 = android.net.wifi.IOnWifiUsabilityStatsListener.Stub.asInterface(r1)
                int r2 = r19.readInt()
                r6.addOnWifiUsabilityStatsListener(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0272:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.unregisterSoftApCallback(r0)
                r20.writeNoException()
                return r11
            L_0x0280:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.wifi.ISoftApCallback r1 = android.net.wifi.ISoftApCallback.Stub.asInterface(r1)
                int r2 = r19.readInt()
                r6.registerSoftApCallback(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x029a:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x02ac
                android.os.Parcelable$Creator<android.net.wifi.hotspot2.OsuProvider> r0 = android.net.wifi.hotspot2.OsuProvider.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.hotspot2.OsuProvider r0 = (android.net.wifi.hotspot2.OsuProvider) r0
                goto L_0x02ad
            L_0x02ac:
            L_0x02ad:
                android.os.IBinder r1 = r19.readStrongBinder()
                android.net.wifi.hotspot2.IProvisioningCallback r1 = android.net.wifi.hotspot2.IProvisioningCallback.Stub.asInterface(r1)
                r6.startSubscriptionProvisioning(r0, r1)
                r20.writeNoException()
                return r11
            L_0x02bc:
                r8.enforceInterface(r10)
                byte[] r0 = r19.createByteArray()
                byte[] r1 = r19.createByteArray()
                r6.restoreSupplicantBackupData(r0, r1)
                r20.writeNoException()
                return r11
            L_0x02ce:
                r8.enforceInterface(r10)
                byte[] r0 = r19.createByteArray()
                r6.restoreBackupData(r0)
                r20.writeNoException()
                return r11
            L_0x02dc:
                r8.enforceInterface(r10)
                byte[] r0 = r17.retrieveBackupData()
                r20.writeNoException()
                r9.writeByteArray(r0)
                return r11
            L_0x02ea:
                r8.enforceInterface(r10)
                android.net.Network r0 = r17.getCurrentNetwork()
                r20.writeNoException()
                if (r0 == 0) goto L_0x02fd
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x0300
            L_0x02fd:
                r9.writeInt(r1)
            L_0x0300:
                return r11
            L_0x0301:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.factoryReset(r0)
                r20.writeNoException()
                return r11
            L_0x030f:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                r6.disableEphemeralNetwork(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0321:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x032c
                r1 = r11
            L_0x032c:
                r0 = r1
                r6.enableWifiConnectivityManager(r0)
                r20.writeNoException()
                return r11
            L_0x0334:
                r8.enforceInterface(r10)
                int r0 = r17.getVerboseLoggingLevel()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0342:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                r6.enableVerboseLogging(r0)
                r20.writeNoException()
                return r11
            L_0x0350:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getCurrentNetworkWpsNfcConfigurationToken()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x035e:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x036d
                r1 = r11
            L_0x036d:
                r6.enableTdlsWithMacAddress(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0374:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0383
                r1 = r11
            L_0x0383:
                r6.enableTdls(r0, r1)
                r20.writeNoException()
                return r11
            L_0x038a:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.Messenger r2 = r6.getWifiServiceMessenger(r0)
                r20.writeNoException()
                if (r2 == 0) goto L_0x03a1
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x03a4
            L_0x03a1:
                r9.writeInt(r1)
            L_0x03a4:
                return r11
            L_0x03a5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.notifyUserOfApBandConversion(r0)
                r20.writeNoException()
                return r11
            L_0x03b3:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x03c5
                android.os.Parcelable$Creator<android.net.wifi.WifiConfiguration> r0 = android.net.wifi.WifiConfiguration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.WifiConfiguration r0 = (android.net.wifi.WifiConfiguration) r0
                goto L_0x03c6
            L_0x03c5:
            L_0x03c6:
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.setWifiApConfiguration(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x03d5:
                r8.enforceInterface(r10)
                android.net.wifi.WifiConfiguration r0 = r17.getWifiApConfiguration()
                r20.writeNoException()
                if (r0 == 0) goto L_0x03e8
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x03eb
            L_0x03e8:
                r9.writeInt(r1)
            L_0x03eb:
                return r11
            L_0x03ec:
                r8.enforceInterface(r10)
                int r0 = r17.getWifiApEnabledState()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x03fa:
                r8.enforceInterface(r10)
                r17.stopWatchLocalOnlyHotspot()
                r20.writeNoException()
                return r11
            L_0x0404:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0416
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Messenger r0 = (android.os.Messenger) r0
                goto L_0x0417
            L_0x0416:
            L_0x0417:
                android.os.IBinder r1 = r19.readStrongBinder()
                r6.startWatchLocalOnlyHotspot(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0422:
                r8.enforceInterface(r10)
                r17.stopLocalOnlyHotspot()
                r20.writeNoException()
                return r11
            L_0x042c:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x043e
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Messenger r0 = (android.os.Messenger) r0
                goto L_0x043f
            L_0x043e:
            L_0x043f:
                android.os.IBinder r1 = r19.readStrongBinder()
                java.lang.String r2 = r19.readString()
                int r3 = r6.startLocalOnlyHotspot(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0452:
                r8.enforceInterface(r10)
                boolean r0 = r17.stopSoftAp()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0460:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0472
                android.os.Parcelable$Creator<android.net.wifi.WifiConfiguration> r0 = android.net.wifi.WifiConfiguration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.WifiConfiguration r0 = (android.net.wifi.WifiConfiguration) r0
                goto L_0x0473
            L_0x0472:
            L_0x0473:
                boolean r1 = r6.startSoftAp(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x047e:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                r6.updateInterfaceIpState(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0490:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.releaseMulticastLock(r0)
                r20.writeNoException()
                return r11
            L_0x049e:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r1 = r19.readString()
                r6.acquireMulticastLock(r0, r1)
                r20.writeNoException()
                return r11
            L_0x04b0:
                r8.enforceInterface(r10)
                boolean r0 = r17.isMulticastEnabled()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x04be:
                r8.enforceInterface(r10)
                r17.initializeMulticastFiltering()
                r20.writeNoException()
                return r11
            L_0x04c8:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                boolean r1 = r6.releaseWifiLock(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x04da:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x04f0
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x04f1
            L_0x04f0:
            L_0x04f1:
                r6.updateWifiLockWorkSource(r1, r0)
                r20.writeNoException()
                return r11
            L_0x04f8:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                int r2 = r19.readInt()
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x0516
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x0517
            L_0x0516:
            L_0x0517:
                boolean r4 = r6.acquireWifiLock(r1, r2, r3, r0)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x0522:
                r8.enforceInterface(r10)
                boolean r0 = r17.isScanAlwaysAvailable()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0530:
                r8.enforceInterface(r10)
                android.net.DhcpInfo r0 = r17.getDhcpInfo()
                r20.writeNoException()
                if (r0 == 0) goto L_0x0543
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x0546
            L_0x0543:
                r9.writeInt(r1)
            L_0x0546:
                return r11
            L_0x0547:
                r8.enforceInterface(r10)
                boolean r0 = r17.needs5GHzToAnyApBandConversion()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0555:
                r8.enforceInterface(r10)
                boolean r0 = r17.isDualBandSupported()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0563:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getCountryCode()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x0571:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                r6.setCountryCode(r0)
                r20.writeNoException()
                return r11
            L_0x057f:
                r8.enforceInterface(r10)
                int r0 = r17.getWifiEnabledState()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x058d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x059c
                r1 = r11
            L_0x059c:
                boolean r2 = r6.setWifiEnabled(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x05a7:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.net.wifi.WifiInfo r2 = r6.getConnectionInfo(r0)
                r20.writeNoException()
                if (r2 == 0) goto L_0x05be
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x05c1
            L_0x05be:
                r9.writeInt(r1)
            L_0x05c1:
                return r11
            L_0x05c2:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.reassociate(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x05d4:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.reconnect(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x05e6:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.disconnect(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x05f8:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.util.List r1 = r6.getScanResults(r0)
                r20.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x060a:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                boolean r1 = r6.startScan(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x061c:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.disableNetwork(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0632:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0641
                r1 = r11
            L_0x0641:
                java.lang.String r2 = r19.readString()
                boolean r3 = r6.enableNetwork(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0650:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.removeNetwork(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0666:
                r8.enforceInterface(r10)
                long r2 = r19.readLong()
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0675
                r1 = r11
            L_0x0675:
                r0 = r1
                r6.deauthenticateNetwork(r2, r0)
                r20.writeNoException()
                return r11
            L_0x067d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r6.matchProviderWithCurrentNetwork(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x068f:
                r8.enforceInterface(r10)
                long r0 = r19.readLong()
                java.lang.String r2 = r19.readString()
                r6.queryPasspointIcon(r0, r2)
                r20.writeNoException()
                return r11
            L_0x06a1:
                r8.enforceInterface(r10)
                java.util.ArrayList r0 = r19.createStringArrayList()
                java.util.List r1 = r6.getWifiConfigsForPasspointProfiles(r0)
                r20.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x06b3:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.util.List r1 = r6.getPasspointConfigurations(r0)
                r20.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x06c5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.removePasspointConfiguration(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x06db:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x06ed
                android.os.Parcelable$Creator<android.net.wifi.hotspot2.PasspointConfiguration> r0 = android.net.wifi.hotspot2.PasspointConfiguration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.hotspot2.PasspointConfiguration r0 = (android.net.wifi.hotspot2.PasspointConfiguration) r0
                goto L_0x06ee
            L_0x06ed:
            L_0x06ee:
                java.lang.String r1 = r19.readString()
                boolean r2 = r6.addOrUpdatePasspointConfiguration(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x06fd:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x070f
                android.os.Parcelable$Creator<android.net.wifi.WifiConfiguration> r0 = android.net.wifi.WifiConfiguration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.wifi.WifiConfiguration r0 = (android.net.wifi.WifiConfiguration) r0
                goto L_0x0710
            L_0x070f:
            L_0x0710:
                java.lang.String r1 = r19.readString()
                int r2 = r6.addOrUpdateNetwork(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x071f:
                r8.enforceInterface(r10)
                android.os.Parcelable$Creator<android.net.wifi.hotspot2.OsuProvider> r0 = android.net.wifi.hotspot2.OsuProvider.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                java.util.Map r1 = r6.getMatchingPasspointConfigsForOsuProviders(r0)
                r20.writeNoException()
                r9.writeMap(r1)
                return r11
            L_0x0733:
                r8.enforceInterface(r10)
                android.os.Parcelable$Creator<android.net.wifi.ScanResult> r0 = android.net.wifi.ScanResult.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                java.util.Map r1 = r6.getMatchingOsuProviders(r0)
                r20.writeNoException()
                r9.writeMap(r1)
                return r11
            L_0x0747:
                r8.enforceInterface(r10)
                android.os.Parcelable$Creator<android.net.wifi.ScanResult> r0 = android.net.wifi.ScanResult.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                java.util.Map r1 = r6.getAllMatchingFqdnsForScanResults(r0)
                r20.writeNoException()
                r9.writeMap(r1)
                return r11
            L_0x075b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.content.pm.ParceledListSlice r2 = r6.getPrivilegedConfiguredNetworks(r0)
                r20.writeNoException()
                if (r2 == 0) goto L_0x0772
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x0775
            L_0x0772:
                r9.writeInt(r1)
            L_0x0775:
                return r11
            L_0x0776:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.content.pm.ParceledListSlice r2 = r6.getConfiguredNetworks(r0)
                r20.writeNoException()
                if (r2 == 0) goto L_0x078d
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x0790
            L_0x078d:
                r9.writeInt(r1)
            L_0x0790:
                return r11
            L_0x0791:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x07a3
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x07a4
            L_0x07a3:
            L_0x07a4:
                r6.requestActivityInfo(r0)
                return r11
            L_0x07a8:
                r8.enforceInterface(r10)
                android.net.wifi.WifiActivityEnergyInfo r0 = r17.reportActivityInfo()
                r20.writeNoException()
                if (r0 == 0) goto L_0x07bb
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x07be
            L_0x07bb:
                r9.writeInt(r1)
            L_0x07be:
                return r11
            L_0x07bf:
                r8.enforceInterface(r10)
                long r0 = r17.getSupportedFeatures()
                r20.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x07cd:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.IWifiManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWifiManager {
            public static IWifiManager sDefaultImpl;
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

            public long getSupportedFeatures() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedFeatures();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
                WifiActivityEnergyInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reportActivityInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiActivityEnergyInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WifiActivityEnergyInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestActivityInfo(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public ParceledListSlice getConfiguredNetworks(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfiguredNetworks(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getPrivilegedConfiguredNetworks(String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrivilegedConfiguredNetworks(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getAllMatchingFqdnsForScanResults(List<ScanResult> scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResult);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllMatchingFqdnsForScanResults(scanResult);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getMatchingOsuProviders(List<ScanResult> scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(scanResult);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMatchingOsuProviders(scanResult);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> osuProviders) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(osuProviders);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMatchingPasspointConfigsForOsuProviders(osuProviders);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOrUpdateNetwork(config, packageName);
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

            public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration config, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOrUpdatePasspointConfiguration(config, packageName);
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

            public boolean removePasspointConfiguration(String fqdn, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fqdn);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removePasspointConfiguration(fqdn, packageName);
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

            public List<PasspointConfiguration> getPasspointConfigurations(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasspointConfigurations(packageName);
                    }
                    _reply.readException();
                    List<PasspointConfiguration> _result = _reply.createTypedArrayList(PasspointConfiguration.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> fqdnList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(fqdnList);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiConfigsForPasspointProfiles(fqdnList);
                    }
                    _reply.readException();
                    List<WifiConfiguration> _result = _reply.createTypedArrayList(WifiConfiguration.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void queryPasspointIcon(long bssid, String fileName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(bssid);
                    _data.writeString(fileName);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().queryPasspointIcon(bssid, fileName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int matchProviderWithCurrentNetwork(String fqdn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fqdn);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().matchProviderWithCurrentNetwork(fqdn);
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

            public void deauthenticateNetwork(long holdoff, boolean ess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(holdoff);
                    _data.writeInt(ess);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deauthenticateNetwork(holdoff, ess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeNetwork(int netId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeNetwork(netId, packageName);
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

            public boolean enableNetwork(int netId, boolean disableOthers, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeInt(disableOthers);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableNetwork(netId, disableOthers, packageName);
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

            public boolean disableNetwork(int netId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableNetwork(netId, packageName);
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

            public boolean startScan(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startScan(packageName);
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

            public List<ScanResult> getScanResults(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getScanResults(callingPackage);
                    }
                    _reply.readException();
                    List<ScanResult> _result = _reply.createTypedArrayList(ScanResult.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean disconnect(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disconnect(packageName);
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

            public boolean reconnect(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reconnect(packageName);
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

            public boolean reassociate(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reassociate(packageName);
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

            public WifiInfo getConnectionInfo(String callingPackage) throws RemoteException {
                WifiInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConnectionInfo(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WifiInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setWifiEnabled(String packageName, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(enable);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWifiEnabled(packageName, enable);
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

            public int getWifiEnabledState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiEnabledState();
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

            public void setCountryCode(String country) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(country);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCountryCode(country);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCountryCode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCountryCode();
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

            public boolean isDualBandSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDualBandSupported();
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

            public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().needs5GHzToAnyApBandConversion();
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

            public DhcpInfo getDhcpInfo() throws RemoteException {
                DhcpInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDhcpInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DhcpInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    DhcpInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isScanAlwaysAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isScanAlwaysAvailable();
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

            public boolean acquireWifiLock(IBinder lock, int lockType, String tag, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(lockType);
                    _data.writeString(tag);
                    boolean _result = true;
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().acquireWifiLock(lock, lockType, tag, ws);
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
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateWifiLockWorkSource(lock, ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean releaseWifiLock(IBinder lock) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    boolean z = false;
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().releaseWifiLock(lock);
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

            public void initializeMulticastFiltering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().initializeMulticastFiltering();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMulticastEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMulticastEnabled();
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

            public void acquireMulticastLock(IBinder binder, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(tag);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acquireMulticastLock(binder, tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseMulticastLock(String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseMulticastLock(tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateInterfaceIpState(String ifaceName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateInterfaceIpState(ifaceName, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startSoftAp(WifiConfiguration wifiConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (wifiConfig != null) {
                        _data.writeInt(1);
                        wifiConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startSoftAp(wifiConfig);
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

            public boolean stopSoftAp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopSoftAp();
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
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startLocalOnlyHotspot(messenger, binder, packageName);
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

            public void stopLocalOnlyHotspot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopLocalOnlyHotspot();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWatchLocalOnlyHotspot(messenger, binder);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWatchLocalOnlyHotspot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWatchLocalOnlyHotspot();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getWifiApEnabledState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiApEnabledState();
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

            public WifiConfiguration getWifiApConfiguration() throws RemoteException {
                WifiConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiApConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WifiConfiguration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setWifiApConfiguration(WifiConfiguration wifiConfig, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (wifiConfig != null) {
                        _data.writeInt(1);
                        wifiConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setWifiApConfiguration(wifiConfig, packageName);
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

            public void notifyUserOfApBandConversion(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyUserOfApBandConversion(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Messenger getWifiServiceMessenger(String packageName) throws RemoteException {
                Messenger _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiServiceMessenger(packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Messenger.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Messenger _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableTdls(String remoteIPAddress, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(remoteIPAddress);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableTdls(remoteIPAddress, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableTdlsWithMacAddress(String remoteMacAddress, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(remoteMacAddress);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableTdlsWithMacAddress(remoteMacAddress, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentNetworkWpsNfcConfigurationToken();
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

            public void enableVerboseLogging(int verbose) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(verbose);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableVerboseLogging(verbose);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVerboseLoggingLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVerboseLoggingLevel();
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

            public void enableWifiConnectivityManager(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableWifiConnectivityManager(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableEphemeralNetwork(String SSID, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(SSID);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableEphemeralNetwork(SSID, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().factoryReset(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Network getCurrentNetwork() throws RemoteException {
                Network _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentNetwork();
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

            public byte[] retrieveBackupData() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().retrieveBackupData();
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

            public void restoreBackupData(byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(data);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreBackupData(data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restoreSupplicantBackupData(byte[] supplicantData, byte[] ipConfigData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(supplicantData);
                    _data.writeByteArray(ipConfigData);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreSupplicantBackupData(supplicantData, ipConfigData);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startSubscriptionProvisioning(provider, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerSoftApCallback(IBinder binder, ISoftApCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerSoftApCallback(binder, callback, callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterSoftApCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterSoftApCallback(callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addOnWifiUsabilityStatsListener(IBinder binder, IOnWifiUsabilityStatsListener listener, int listenerIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(listenerIdentifier);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnWifiUsabilityStatsListener(binder, listener, listenerIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnWifiUsabilityStatsListener(int listenerIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(listenerIdentifier);
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOnWifiUsabilityStatsListener(listenerIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerTrafficStateCallback(IBinder binder, ITrafficStateCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerTrafficStateCallback(binder, callback, callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterTrafficStateCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterTrafficStateCallback(callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCapabilities(String capaType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(capaType);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCapabilities(capaType);
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

            public int dppAddBootstrapQrCode(String uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppAddBootstrapQrCode(uri);
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
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppBootstrapGenerate(config);
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

            public String dppGetUri(int bootstrap_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bootstrap_id);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppGetUri(bootstrap_id);
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

            public int dppBootstrapRemove(int bootstrap_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bootstrap_id);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppBootstrapRemove(bootstrap_id);
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

            public int dppListen(String frequency, int dpp_role, boolean qr_mutual, boolean netrole_ap) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(frequency);
                    _data.writeInt(dpp_role);
                    _data.writeInt(qr_mutual);
                    _data.writeInt(netrole_ap);
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppListen(frequency, dpp_role, qr_mutual, netrole_ap);
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

            public void dppStopListen() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dppStopListen();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int dppConfiguratorAdd(String curve, String key, int expiry) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(curve);
                    _data.writeString(key);
                    _data.writeInt(expiry);
                    if (!this.mRemote.transact(79, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorAdd(curve, key, expiry);
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

            public int dppConfiguratorRemove(int config_id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(config_id);
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorRemove(config_id);
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
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppStartAuth(config);
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

            public String dppConfiguratorGetKey(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dppConfiguratorGetKey(id);
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

            public boolean isExtendingWifi() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isExtendingWifi();
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

            public boolean isWifiCoverageExtendFeatureEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWifiCoverageExtendFeatureEnabled();
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

            public void enableWifiCoverageExtendFeature(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(85, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableWifiCoverageExtendFeature(enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerNetworkRequestMatchCallback(IBinder binder, INetworkRequestMatchCallback callback, int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerNetworkRequestMatchCallback(binder, callback, callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterNetworkRequestMatchCallback(int callbackIdentifier) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterNetworkRequestMatchCallback(callbackIdentifier);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int addNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(networkSuggestions);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(88, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addNetworkSuggestions(networkSuggestions, packageName);
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

            public int removeNetworkSuggestions(List<WifiNetworkSuggestion> networkSuggestions, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(networkSuggestions);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeNetworkSuggestions(networkSuggestions, packageName);
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

            public String[] getFactoryMacAddresses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFactoryMacAddresses();
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

            public void setDeviceMobilityState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(91, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDeviceMobilityState(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startDppAsConfiguratorInitiator(binder, enrolleeUri, selectedNetworkId, netRole, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startDppAsEnrolleeInitiator(IBinder binder, String configuratorUri, IDppCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(configuratorUri);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(93, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startDppAsEnrolleeInitiator(binder, configuratorUri, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopDppSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(94, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopDppSession();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateWifiUsabilityScore(int seqNum, int score, int predictionHorizonSec) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seqNum);
                    _data.writeInt(score);
                    _data.writeInt(predictionHorizonSec);
                    if (this.mRemote.transact(95, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateWifiUsabilityScore(seqNum, score, predictionHorizonSec);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSoftApWifiGeneration() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoftApWifiGeneration();
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
        }

        public static boolean setDefaultImpl(IWifiManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWifiManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
