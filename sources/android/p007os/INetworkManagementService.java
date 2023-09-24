package android.p007os;

import android.annotation.UnsupportedAppUsage;
import android.net.INetworkManagementEventObserver;
import android.net.ITetheringStatsProvider;
import android.net.InterfaceConfiguration;
import android.net.Network;
import android.net.NetworkStats;
import android.net.RouteInfo;
import android.net.UidRange;
import android.p007os.INetworkActivityListener;
import java.util.List;

/* renamed from: android.os.INetworkManagementService */
/* loaded from: classes3.dex */
public interface INetworkManagementService extends IInterface {
    void addIdleTimer(String str, int i, int i2) throws RemoteException;

    void addInterfaceToLocalNetwork(String str, List<RouteInfo> list) throws RemoteException;

    void addInterfaceToNetwork(String str, int i) throws RemoteException;

    void addLegacyRouteForNetId(int i, RouteInfo routeInfo, int i2) throws RemoteException;

    void addRoute(int i, RouteInfo routeInfo) throws RemoteException;

    void addVpnUidRanges(int i, UidRange[] uidRangeArr) throws RemoteException;

    void allowProtect(int i) throws RemoteException;

    void clearDefaultNetId() throws RemoteException;

    @UnsupportedAppUsage
    void clearInterfaceAddresses(String str) throws RemoteException;

    void denyProtect(int i) throws RemoteException;

    @UnsupportedAppUsage
    void disableIpv6(String str) throws RemoteException;

    @UnsupportedAppUsage
    void disableNat(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void enableIpv6(String str) throws RemoteException;

    @UnsupportedAppUsage
    void enableNat(String str, String str2) throws RemoteException;

    String[] getDnsForwarders() throws RemoteException;

    @UnsupportedAppUsage
    InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean getIpForwardingEnabled() throws RemoteException;

    NetworkStats getNetworkStatsDetail() throws RemoteException;

    NetworkStats getNetworkStatsSummaryDev() throws RemoteException;

    NetworkStats getNetworkStatsSummaryXt() throws RemoteException;

    NetworkStats getNetworkStatsTethering(int i) throws RemoteException;

    NetworkStats getNetworkStatsUidDetail(int i, String[] strArr) throws RemoteException;

    @UnsupportedAppUsage
    boolean isBandwidthControlEnabled() throws RemoteException;

    boolean isFirewallEnabled() throws RemoteException;

    boolean isNetworkActive() throws RemoteException;

    boolean isNetworkRestricted(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean isTetheringStarted() throws RemoteException;

    String[] listInterfaces() throws RemoteException;

    String[] listTetheredInterfaces() throws RemoteException;

    void registerNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException;

    @UnsupportedAppUsage
    void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException;

    void registerTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider, String str) throws RemoteException;

    void removeIdleTimer(String str) throws RemoteException;

    void removeInterfaceAlert(String str) throws RemoteException;

    void removeInterfaceFromLocalNetwork(String str) throws RemoteException;

    void removeInterfaceFromNetwork(String str, int i) throws RemoteException;

    void removeInterfaceQuota(String str) throws RemoteException;

    void removeRoute(int i, RouteInfo routeInfo) throws RemoteException;

    int removeRoutesFromLocalNetwork(List<RouteInfo> list) throws RemoteException;

    void removeVpnUidRanges(int i, UidRange[] uidRangeArr) throws RemoteException;

    void setAllowOnlyVpnForUids(boolean z, UidRange[] uidRangeArr) throws RemoteException;

    boolean setDataSaverModeEnabled(boolean z) throws RemoteException;

    void setDefaultNetId(int i) throws RemoteException;

    void setDnsForwarders(Network network, String[] strArr) throws RemoteException;

    void setFirewallChainEnabled(int i, boolean z) throws RemoteException;

    void setFirewallEnabled(boolean z) throws RemoteException;

    void setFirewallInterfaceRule(String str, boolean z) throws RemoteException;

    void setFirewallUidRule(int i, int i2, int i3) throws RemoteException;

    void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException;

    void setGlobalAlert(long j) throws RemoteException;

    @UnsupportedAppUsage
    void setIPv6AddrGenMode(String str, int i) throws RemoteException;

    void setInterfaceAlert(String str, long j) throws RemoteException;

    @UnsupportedAppUsage
    void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) throws RemoteException;

    void setInterfaceDown(String str) throws RemoteException;

    @UnsupportedAppUsage
    void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException;

    void setInterfaceQuota(String str, long j) throws RemoteException;

    void setInterfaceUp(String str) throws RemoteException;

    @UnsupportedAppUsage
    void setIpForwardingEnabled(boolean z) throws RemoteException;

    void setMtu(String str, int i) throws RemoteException;

    void setNetworkPermission(int i, int i2) throws RemoteException;

    void setUidCleartextNetworkPolicy(int i, int i2) throws RemoteException;

    void setUidMeteredNetworkBlacklist(int i, boolean z) throws RemoteException;

    void setUidMeteredNetworkWhitelist(int i, boolean z) throws RemoteException;

    void shutdown() throws RemoteException;

    void startInterfaceForwarding(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void startTethering(String[] strArr) throws RemoteException;

    void stopInterfaceForwarding(String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void stopTethering() throws RemoteException;

    @UnsupportedAppUsage
    void tetherInterface(String str) throws RemoteException;

    void tetherLimitReached(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException;

    void unregisterNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException;

    @UnsupportedAppUsage
    void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException;

    void unregisterTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException;

    @UnsupportedAppUsage
    void untetherInterface(String str) throws RemoteException;

    /* renamed from: android.os.INetworkManagementService$Default */
    /* loaded from: classes3.dex */
    public static class Default implements INetworkManagementService {
        @Override // android.p007os.INetworkManagementService
        public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public String[] listInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceConfig(String iface, InterfaceConfiguration cfg) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void clearInterfaceAddresses(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceDown(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceUp(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void disableIpv6(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void enableIpv6(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void addRoute(int netId, RouteInfo route) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeRoute(int netId, RouteInfo route) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setMtu(String iface, int mtu) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void shutdown() throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean getIpForwardingEnabled() throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void startTethering(String[] dhcpRanges) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void stopTethering() throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean isTetheringStarted() throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void tetherInterface(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void untetherInterface(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public String[] listTetheredInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public void setDnsForwarders(Network network, String[] dns) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public String[] getDnsForwarders() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public void startInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void enableNat(String internalInterface, String externalInterface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void disableNat(String internalInterface, String externalInterface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public NetworkStats getNetworkStatsDetail() throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public NetworkStats getNetworkStatsUidDetail(int uid, String[] ifaces) throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
            return null;
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeInterfaceQuota(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeInterfaceAlert(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setGlobalAlert(long alertBytes) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setUidMeteredNetworkBlacklist(int uid, boolean enable) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setUidMeteredNetworkWhitelist(int uid, boolean enable) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean isBandwidthControlEnabled() throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void addIdleTimer(String iface, int timeout, int type) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeIdleTimer(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setFirewallEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean isFirewallEnabled() throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void addVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void registerNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void unregisterNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean isNetworkActive() throws RemoteException {
            return false;
        }

        @Override // android.p007os.INetworkManagementService
        public void addInterfaceToNetwork(String iface, int netId) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeInterfaceFromNetwork(String iface, int netId) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void addLegacyRouteForNetId(int netId, RouteInfo routeInfo, int uid) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setDefaultNetId(int netId) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void clearDefaultNetId() throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void setNetworkPermission(int netId, int permission) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void allowProtect(int uid) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void denyProtect(int uid) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> routes) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public int removeRoutesFromLocalNetwork(List<RouteInfo> routes) throws RemoteException {
            return 0;
        }

        @Override // android.p007os.INetworkManagementService
        public void setAllowOnlyVpnForUids(boolean enable, UidRange[] uidRanges) throws RemoteException {
        }

        @Override // android.p007os.INetworkManagementService
        public boolean isNetworkRestricted(int uid) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.INetworkManagementService$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements INetworkManagementService {
        private static final String DESCRIPTOR = "android.os.INetworkManagementService";
        static final int TRANSACTION_addIdleTimer = 49;
        static final int TRANSACTION_addInterfaceToLocalNetwork = 70;
        static final int TRANSACTION_addInterfaceToNetwork = 62;
        static final int TRANSACTION_addLegacyRouteForNetId = 64;
        static final int TRANSACTION_addRoute = 13;
        static final int TRANSACTION_addVpnUidRanges = 57;
        static final int TRANSACTION_allowProtect = 68;
        static final int TRANSACTION_clearDefaultNetId = 66;
        static final int TRANSACTION_clearInterfaceAddresses = 6;
        static final int TRANSACTION_denyProtect = 69;
        static final int TRANSACTION_disableIpv6 = 10;
        static final int TRANSACTION_disableNat = 30;
        static final int TRANSACTION_enableIpv6 = 11;
        static final int TRANSACTION_enableNat = 29;
        static final int TRANSACTION_getDnsForwarders = 26;
        static final int TRANSACTION_getInterfaceConfig = 4;
        static final int TRANSACTION_getIpForwardingEnabled = 17;
        static final int TRANSACTION_getNetworkStatsDetail = 36;
        static final int TRANSACTION_getNetworkStatsSummaryDev = 34;
        static final int TRANSACTION_getNetworkStatsSummaryXt = 35;
        static final int TRANSACTION_getNetworkStatsTethering = 38;
        static final int TRANSACTION_getNetworkStatsUidDetail = 37;
        static final int TRANSACTION_isBandwidthControlEnabled = 48;
        static final int TRANSACTION_isFirewallEnabled = 52;
        static final int TRANSACTION_isNetworkActive = 61;
        static final int TRANSACTION_isNetworkRestricted = 74;
        static final int TRANSACTION_isTetheringStarted = 21;
        static final int TRANSACTION_listInterfaces = 3;
        static final int TRANSACTION_listTetheredInterfaces = 24;
        static final int TRANSACTION_registerNetworkActivityListener = 59;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_registerTetheringStatsProvider = 31;
        static final int TRANSACTION_removeIdleTimer = 50;
        static final int TRANSACTION_removeInterfaceAlert = 42;
        static final int TRANSACTION_removeInterfaceFromLocalNetwork = 71;
        static final int TRANSACTION_removeInterfaceFromNetwork = 63;
        static final int TRANSACTION_removeInterfaceQuota = 40;
        static final int TRANSACTION_removeRoute = 14;
        static final int TRANSACTION_removeRoutesFromLocalNetwork = 72;
        static final int TRANSACTION_removeVpnUidRanges = 58;
        static final int TRANSACTION_setAllowOnlyVpnForUids = 73;
        static final int TRANSACTION_setDataSaverModeEnabled = 46;
        static final int TRANSACTION_setDefaultNetId = 65;
        static final int TRANSACTION_setDnsForwarders = 25;
        static final int TRANSACTION_setFirewallChainEnabled = 56;
        static final int TRANSACTION_setFirewallEnabled = 51;
        static final int TRANSACTION_setFirewallInterfaceRule = 53;
        static final int TRANSACTION_setFirewallUidRule = 54;
        static final int TRANSACTION_setFirewallUidRules = 55;
        static final int TRANSACTION_setGlobalAlert = 43;
        static final int TRANSACTION_setIPv6AddrGenMode = 12;
        static final int TRANSACTION_setInterfaceAlert = 41;
        static final int TRANSACTION_setInterfaceConfig = 5;
        static final int TRANSACTION_setInterfaceDown = 7;
        static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
        static final int TRANSACTION_setInterfaceQuota = 39;
        static final int TRANSACTION_setInterfaceUp = 8;
        static final int TRANSACTION_setIpForwardingEnabled = 18;
        static final int TRANSACTION_setMtu = 15;
        static final int TRANSACTION_setNetworkPermission = 67;
        static final int TRANSACTION_setUidCleartextNetworkPolicy = 47;
        static final int TRANSACTION_setUidMeteredNetworkBlacklist = 44;
        static final int TRANSACTION_setUidMeteredNetworkWhitelist = 45;
        static final int TRANSACTION_shutdown = 16;
        static final int TRANSACTION_startInterfaceForwarding = 27;
        static final int TRANSACTION_startTethering = 19;
        static final int TRANSACTION_stopInterfaceForwarding = 28;
        static final int TRANSACTION_stopTethering = 20;
        static final int TRANSACTION_tetherInterface = 22;
        static final int TRANSACTION_tetherLimitReached = 33;
        static final int TRANSACTION_unregisterNetworkActivityListener = 60;
        static final int TRANSACTION_unregisterObserver = 2;
        static final int TRANSACTION_unregisterTetheringStatsProvider = 32;
        static final int TRANSACTION_untetherInterface = 23;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkManagementService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof INetworkManagementService)) {
                return (INetworkManagementService) iin;
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
                    return "registerObserver";
                case 2:
                    return "unregisterObserver";
                case 3:
                    return "listInterfaces";
                case 4:
                    return "getInterfaceConfig";
                case 5:
                    return "setInterfaceConfig";
                case 6:
                    return "clearInterfaceAddresses";
                case 7:
                    return "setInterfaceDown";
                case 8:
                    return "setInterfaceUp";
                case 9:
                    return "setInterfaceIpv6PrivacyExtensions";
                case 10:
                    return "disableIpv6";
                case 11:
                    return "enableIpv6";
                case 12:
                    return "setIPv6AddrGenMode";
                case 13:
                    return "addRoute";
                case 14:
                    return "removeRoute";
                case 15:
                    return "setMtu";
                case 16:
                    return "shutdown";
                case 17:
                    return "getIpForwardingEnabled";
                case 18:
                    return "setIpForwardingEnabled";
                case 19:
                    return "startTethering";
                case 20:
                    return "stopTethering";
                case 21:
                    return "isTetheringStarted";
                case 22:
                    return "tetherInterface";
                case 23:
                    return "untetherInterface";
                case 24:
                    return "listTetheredInterfaces";
                case 25:
                    return "setDnsForwarders";
                case 26:
                    return "getDnsForwarders";
                case 27:
                    return "startInterfaceForwarding";
                case 28:
                    return "stopInterfaceForwarding";
                case 29:
                    return "enableNat";
                case 30:
                    return "disableNat";
                case 31:
                    return "registerTetheringStatsProvider";
                case 32:
                    return "unregisterTetheringStatsProvider";
                case 33:
                    return "tetherLimitReached";
                case 34:
                    return "getNetworkStatsSummaryDev";
                case 35:
                    return "getNetworkStatsSummaryXt";
                case 36:
                    return "getNetworkStatsDetail";
                case 37:
                    return "getNetworkStatsUidDetail";
                case 38:
                    return "getNetworkStatsTethering";
                case 39:
                    return "setInterfaceQuota";
                case 40:
                    return "removeInterfaceQuota";
                case 41:
                    return "setInterfaceAlert";
                case 42:
                    return "removeInterfaceAlert";
                case 43:
                    return "setGlobalAlert";
                case 44:
                    return "setUidMeteredNetworkBlacklist";
                case 45:
                    return "setUidMeteredNetworkWhitelist";
                case 46:
                    return "setDataSaverModeEnabled";
                case 47:
                    return "setUidCleartextNetworkPolicy";
                case 48:
                    return "isBandwidthControlEnabled";
                case 49:
                    return "addIdleTimer";
                case 50:
                    return "removeIdleTimer";
                case 51:
                    return "setFirewallEnabled";
                case 52:
                    return "isFirewallEnabled";
                case 53:
                    return "setFirewallInterfaceRule";
                case 54:
                    return "setFirewallUidRule";
                case 55:
                    return "setFirewallUidRules";
                case 56:
                    return "setFirewallChainEnabled";
                case 57:
                    return "addVpnUidRanges";
                case 58:
                    return "removeVpnUidRanges";
                case 59:
                    return "registerNetworkActivityListener";
                case 60:
                    return "unregisterNetworkActivityListener";
                case 61:
                    return "isNetworkActive";
                case 62:
                    return "addInterfaceToNetwork";
                case 63:
                    return "removeInterfaceFromNetwork";
                case 64:
                    return "addLegacyRouteForNetId";
                case 65:
                    return "setDefaultNetId";
                case 66:
                    return "clearDefaultNetId";
                case 67:
                    return "setNetworkPermission";
                case 68:
                    return "allowProtect";
                case 69:
                    return "denyProtect";
                case 70:
                    return "addInterfaceToLocalNetwork";
                case 71:
                    return "removeInterfaceFromLocalNetwork";
                case 72:
                    return "removeRoutesFromLocalNetwork";
                case 73:
                    return "setAllowOnlyVpnForUids";
                case 74:
                    return "isNetworkRestricted";
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
            boolean _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    INetworkManagementEventObserver _arg0 = INetworkManagementEventObserver.Stub.asInterface(data.readStrongBinder());
                    registerObserver(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    INetworkManagementEventObserver _arg02 = INetworkManagementEventObserver.Stub.asInterface(data.readStrongBinder());
                    unregisterObserver(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result = listInterfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    InterfaceConfiguration _result2 = getInterfaceConfig(_arg03);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    setInterfaceConfig(_arg04, data.readInt() != 0 ? InterfaceConfiguration.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    clearInterfaceAddresses(_arg05);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg06 = data.readString();
                    setInterfaceDown(_arg06);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg07 = data.readString();
                    setInterfaceUp(_arg07);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    _arg1 = data.readInt() != 0;
                    setInterfaceIpv6PrivacyExtensions(_arg08, _arg1);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    disableIpv6(_arg09);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    enableIpv6(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    setIPv6AddrGenMode(_arg011, data.readInt());
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    addRoute(_arg012, data.readInt() != 0 ? RouteInfo.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg013 = data.readInt();
                    removeRoute(_arg013, data.readInt() != 0 ? RouteInfo.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg014 = data.readString();
                    setMtu(_arg014, data.readInt());
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    shutdown();
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    boolean ipForwardingEnabled = getIpForwardingEnabled();
                    reply.writeNoException();
                    reply.writeInt(ipForwardingEnabled ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0;
                    boolean _arg015 = _arg1;
                    setIpForwardingEnabled(_arg015);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _arg016 = data.createStringArray();
                    startTethering(_arg016);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    stopTethering();
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isTetheringStarted = isTetheringStarted();
                    reply.writeNoException();
                    reply.writeInt(isTetheringStarted ? 1 : 0);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg017 = data.readString();
                    tetherInterface(_arg017);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg018 = data.readString();
                    untetherInterface(_arg018);
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result3 = listTetheredInterfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result3);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    Network _arg019 = data.readInt() != 0 ? Network.CREATOR.createFromParcel(data) : null;
                    setDnsForwarders(_arg019, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _result4 = getDnsForwarders();
                    reply.writeNoException();
                    reply.writeStringArray(_result4);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    startInterfaceForwarding(_arg020, data.readString());
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg021 = data.readString();
                    stopInterfaceForwarding(_arg021, data.readString());
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg022 = data.readString();
                    enableNat(_arg022, data.readString());
                    reply.writeNoException();
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg023 = data.readString();
                    disableNat(_arg023, data.readString());
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    ITetheringStatsProvider _arg024 = ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
                    registerTetheringStatsProvider(_arg024, data.readString());
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    ITetheringStatsProvider _arg025 = ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
                    unregisterTetheringStatsProvider(_arg025);
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    ITetheringStatsProvider _arg026 = ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
                    tetherLimitReached(_arg026);
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkStats _result5 = getNetworkStatsSummaryDev();
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkStats _result6 = getNetworkStatsSummaryXt();
                    reply.writeNoException();
                    if (_result6 != null) {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    NetworkStats _result7 = getNetworkStatsDetail();
                    reply.writeNoException();
                    if (_result7 != null) {
                        reply.writeInt(1);
                        _result7.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg027 = data.readInt();
                    NetworkStats _result8 = getNetworkStatsUidDetail(_arg027, data.createStringArray());
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg028 = data.readInt();
                    NetworkStats _result9 = getNetworkStatsTethering(_arg028);
                    reply.writeNoException();
                    if (_result9 != null) {
                        reply.writeInt(1);
                        _result9.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg029 = data.readString();
                    setInterfaceQuota(_arg029, data.readLong());
                    reply.writeNoException();
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg030 = data.readString();
                    removeInterfaceQuota(_arg030);
                    reply.writeNoException();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg031 = data.readString();
                    setInterfaceAlert(_arg031, data.readLong());
                    reply.writeNoException();
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg032 = data.readString();
                    removeInterfaceAlert(_arg032);
                    reply.writeNoException();
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg033 = data.readLong();
                    setGlobalAlert(_arg033);
                    reply.writeNoException();
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg034 = data.readInt();
                    _arg1 = data.readInt() != 0;
                    setUidMeteredNetworkBlacklist(_arg034, _arg1);
                    reply.writeNoException();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg035 = data.readInt();
                    _arg1 = data.readInt() != 0;
                    setUidMeteredNetworkWhitelist(_arg035, _arg1);
                    reply.writeNoException();
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0;
                    boolean _arg036 = _arg1;
                    boolean dataSaverModeEnabled = setDataSaverModeEnabled(_arg036);
                    reply.writeNoException();
                    reply.writeInt(dataSaverModeEnabled ? 1 : 0);
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg037 = data.readInt();
                    setUidCleartextNetworkPolicy(_arg037, data.readInt());
                    reply.writeNoException();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isBandwidthControlEnabled = isBandwidthControlEnabled();
                    reply.writeNoException();
                    reply.writeInt(isBandwidthControlEnabled ? 1 : 0);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg038 = data.readString();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    addIdleTimer(_arg038, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg039 = data.readString();
                    removeIdleTimer(_arg039);
                    reply.writeNoException();
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0;
                    boolean _arg040 = _arg1;
                    setFirewallEnabled(_arg040);
                    reply.writeNoException();
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isFirewallEnabled = isFirewallEnabled();
                    reply.writeNoException();
                    reply.writeInt(isFirewallEnabled ? 1 : 0);
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg041 = data.readString();
                    _arg1 = data.readInt() != 0;
                    setFirewallInterfaceRule(_arg041, _arg1);
                    reply.writeNoException();
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg042 = data.readInt();
                    int _arg13 = data.readInt();
                    int _arg22 = data.readInt();
                    setFirewallUidRule(_arg042, _arg13, _arg22);
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg043 = data.readInt();
                    int[] _arg14 = data.createIntArray();
                    int[] _arg23 = data.createIntArray();
                    setFirewallUidRules(_arg043, _arg14, _arg23);
                    reply.writeNoException();
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg044 = data.readInt();
                    _arg1 = data.readInt() != 0;
                    setFirewallChainEnabled(_arg044, _arg1);
                    reply.writeNoException();
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg045 = data.readInt();
                    addVpnUidRanges(_arg045, (UidRange[]) data.createTypedArray(UidRange.CREATOR));
                    reply.writeNoException();
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg046 = data.readInt();
                    removeVpnUidRanges(_arg046, (UidRange[]) data.createTypedArray(UidRange.CREATOR));
                    reply.writeNoException();
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    INetworkActivityListener _arg047 = INetworkActivityListener.Stub.asInterface(data.readStrongBinder());
                    registerNetworkActivityListener(_arg047);
                    reply.writeNoException();
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    INetworkActivityListener _arg048 = INetworkActivityListener.Stub.asInterface(data.readStrongBinder());
                    unregisterNetworkActivityListener(_arg048);
                    reply.writeNoException();
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isNetworkActive = isNetworkActive();
                    reply.writeNoException();
                    reply.writeInt(isNetworkActive ? 1 : 0);
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg049 = data.readString();
                    addInterfaceToNetwork(_arg049, data.readInt());
                    reply.writeNoException();
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg050 = data.readString();
                    removeInterfaceFromNetwork(_arg050, data.readInt());
                    reply.writeNoException();
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg051 = data.readInt();
                    RouteInfo _arg15 = data.readInt() != 0 ? RouteInfo.CREATOR.createFromParcel(data) : null;
                    int _arg24 = data.readInt();
                    addLegacyRouteForNetId(_arg051, _arg15, _arg24);
                    reply.writeNoException();
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg052 = data.readInt();
                    setDefaultNetId(_arg052);
                    reply.writeNoException();
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    clearDefaultNetId();
                    reply.writeNoException();
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg053 = data.readInt();
                    setNetworkPermission(_arg053, data.readInt());
                    reply.writeNoException();
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg054 = data.readInt();
                    allowProtect(_arg054);
                    reply.writeNoException();
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg055 = data.readInt();
                    denyProtect(_arg055);
                    reply.writeNoException();
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg056 = data.readString();
                    addInterfaceToLocalNetwork(_arg056, data.createTypedArrayList(RouteInfo.CREATOR));
                    reply.writeNoException();
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg057 = data.readString();
                    removeInterfaceFromLocalNetwork(_arg057);
                    reply.writeNoException();
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    List<RouteInfo> _arg058 = data.createTypedArrayList(RouteInfo.CREATOR);
                    int _result10 = removeRoutesFromLocalNetwork(_arg058);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0;
                    boolean _arg059 = _arg1;
                    setAllowOnlyVpnForUids(_arg059, (UidRange[]) data.createTypedArray(UidRange.CREATOR));
                    reply.writeNoException();
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg060 = data.readInt();
                    boolean isNetworkRestricted = isNetworkRestricted(_arg060);
                    reply.writeNoException();
                    reply.writeInt(isNetworkRestricted ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.INetworkManagementService$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements INetworkManagementService {
            public static INetworkManagementService sDefaultImpl;
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

            @Override // android.p007os.INetworkManagementService
            public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(obs != null ? obs.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerObserver(obs);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(obs != null ? obs.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterObserver(obs);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public String[] listInterfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listInterfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
                InterfaceConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInterfaceConfig(iface);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InterfaceConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceConfig(String iface, InterfaceConfiguration cfg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (cfg != null) {
                        _data.writeInt(1);
                        cfg.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceConfig(iface, cfg);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void clearInterfaceAddresses(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearInterfaceAddresses(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceDown(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceDown(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceUp(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceUp(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceIpv6PrivacyExtensions(iface, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void disableIpv6(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableIpv6(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void enableIpv6(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableIpv6(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIPv6AddrGenMode(iface, mode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addRoute(int netId, RouteInfo route) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    if (route != null) {
                        _data.writeInt(1);
                        route.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addRoute(netId, route);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeRoute(int netId, RouteInfo route) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    if (route != null) {
                        _data.writeInt(1);
                        route.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeRoute(netId, route);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setMtu(String iface, int mtu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(mtu);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMtu(iface, mtu);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean getIpForwardingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIpForwardingEnabled();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIpForwardingEnabled(enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void startTethering(String[] dhcpRanges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(dhcpRanges);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTethering(dhcpRanges);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void stopTethering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopTethering();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean isTetheringStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTetheringStarted();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void tetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tetherInterface(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void untetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().untetherInterface(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public String[] listTetheredInterfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listTetheredInterfaces();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setDnsForwarders(Network network, String[] dns) throws RemoteException {
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
                    _data.writeStringArray(dns);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDnsForwarders(network, dns);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public String[] getDnsForwarders() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDnsForwarders();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void startInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromIface);
                    _data.writeString(toIface);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startInterfaceForwarding(fromIface, toIface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromIface);
                    _data.writeString(toIface);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopInterfaceForwarding(fromIface, toIface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void enableNat(String internalInterface, String externalInterface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableNat(internalInterface, externalInterface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void disableNat(String internalInterface, String externalInterface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableNat(internalInterface, externalInterface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    _data.writeString(name);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTetheringStatsProvider(provider, name);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTetheringStatsProvider(provider);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tetherLimitReached(provider);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsSummaryDev();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsSummaryXt();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public NetworkStats getNetworkStatsDetail() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsDetail();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public NetworkStats getNetworkStatsUidDetail(int uid, String[] ifaces) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(ifaces);
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsUidDetail(uid, ifaces);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(how);
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsTethering(how);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(quotaBytes);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceQuota(iface, quotaBytes);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeInterfaceQuota(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceQuota(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(alertBytes);
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceAlert(iface, alertBytes);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeInterfaceAlert(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceAlert(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setGlobalAlert(long alertBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(alertBytes);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalAlert(alertBytes);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setUidMeteredNetworkBlacklist(int uid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidMeteredNetworkBlacklist(uid, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setUidMeteredNetworkWhitelist(int uid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidMeteredNetworkWhitelist(uid, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDataSaverModeEnabled(enable);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidCleartextNetworkPolicy(uid, policy);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean isBandwidthControlEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBandwidthControlEnabled();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addIdleTimer(String iface, int timeout, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(timeout);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addIdleTimer(iface, timeout, type);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeIdleTimer(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeIdleTimer(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setFirewallEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallEnabled(enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean isFirewallEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isFirewallEnabled();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(allow ? 1 : 0);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallInterfaceRule(iface, allow);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeInt(uid);
                    _data.writeInt(rule);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallUidRule(chain, uid, rule);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeIntArray(uids);
                    _data.writeIntArray(rules);
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallUidRules(chain, uids, rules);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeInt(enable ? 1 : 0);
                    boolean _status = this.mRemote.transact(56, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallChainEnabled(chain, enable);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeTypedArray(ranges, 0);
                    boolean _status = this.mRemote.transact(57, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addVpnUidRanges(netId, ranges);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeTypedArray(ranges, 0);
                    boolean _status = this.mRemote.transact(58, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeVpnUidRanges(netId, ranges);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void registerNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(59, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerNetworkActivityListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void unregisterNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(60, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkActivityListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean isNetworkActive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(61, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkActive();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addInterfaceToNetwork(String iface, int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(netId);
                    boolean _status = this.mRemote.transact(62, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInterfaceToNetwork(iface, netId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeInterfaceFromNetwork(String iface, int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(netId);
                    boolean _status = this.mRemote.transact(63, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceFromNetwork(iface, netId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addLegacyRouteForNetId(int netId, RouteInfo routeInfo, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    if (routeInfo != null) {
                        _data.writeInt(1);
                        routeInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(64, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addLegacyRouteForNetId(netId, routeInfo, uid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setDefaultNetId(int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    boolean _status = this.mRemote.transact(65, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultNetId(netId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void clearDefaultNetId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(66, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDefaultNetId();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setNetworkPermission(int netId, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeInt(permission);
                    boolean _status = this.mRemote.transact(67, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkPermission(netId, permission);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void allowProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(68, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allowProtect(uid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void denyProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(69, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().denyProtect(uid);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> routes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeTypedList(routes);
                    boolean _status = this.mRemote.transact(70, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInterfaceToLocalNetwork(iface, routes);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    boolean _status = this.mRemote.transact(71, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceFromLocalNetwork(iface);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public int removeRoutesFromLocalNetwork(List<RouteInfo> routes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(routes);
                    boolean _status = this.mRemote.transact(72, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeRoutesFromLocalNetwork(routes);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public void setAllowOnlyVpnForUids(boolean enable, UidRange[] uidRanges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    _data.writeTypedArray(uidRanges, 0);
                    boolean _status = this.mRemote.transact(73, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAllowOnlyVpnForUids(enable, uidRanges);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.INetworkManagementService
            public boolean isNetworkRestricted(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(74, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkRestricted(uid);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkManagementService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static INetworkManagementService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
