package android.os;

import android.annotation.UnsupportedAppUsage;
import android.net.INetworkManagementEventObserver;
import android.net.ITetheringStatsProvider;
import android.net.InterfaceConfiguration;
import android.net.Network;
import android.net.NetworkStats;
import android.net.RouteInfo;
import android.net.UidRange;
import java.util.List;

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

    public static class Default implements INetworkManagementService {
        public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {
        }

        public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {
        }

        public String[] listInterfaces() throws RemoteException {
            return null;
        }

        public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
            return null;
        }

        public void setInterfaceConfig(String iface, InterfaceConfiguration cfg) throws RemoteException {
        }

        public void clearInterfaceAddresses(String iface) throws RemoteException {
        }

        public void setInterfaceDown(String iface) throws RemoteException {
        }

        public void setInterfaceUp(String iface) throws RemoteException {
        }

        public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable) throws RemoteException {
        }

        public void disableIpv6(String iface) throws RemoteException {
        }

        public void enableIpv6(String iface) throws RemoteException {
        }

        public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {
        }

        public void addRoute(int netId, RouteInfo route) throws RemoteException {
        }

        public void removeRoute(int netId, RouteInfo route) throws RemoteException {
        }

        public void setMtu(String iface, int mtu) throws RemoteException {
        }

        public void shutdown() throws RemoteException {
        }

        public boolean getIpForwardingEnabled() throws RemoteException {
            return false;
        }

        public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
        }

        public void startTethering(String[] dhcpRanges) throws RemoteException {
        }

        public void stopTethering() throws RemoteException {
        }

        public boolean isTetheringStarted() throws RemoteException {
            return false;
        }

        public void tetherInterface(String iface) throws RemoteException {
        }

        public void untetherInterface(String iface) throws RemoteException {
        }

        public String[] listTetheredInterfaces() throws RemoteException {
            return null;
        }

        public void setDnsForwarders(Network network, String[] dns) throws RemoteException {
        }

        public String[] getDnsForwarders() throws RemoteException {
            return null;
        }

        public void startInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
        }

        public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
        }

        public void enableNat(String internalInterface, String externalInterface) throws RemoteException {
        }

        public void disableNat(String internalInterface, String externalInterface) throws RemoteException {
        }

        public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name) throws RemoteException {
        }

        public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider) throws RemoteException {
        }

        public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {
        }

        public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
            return null;
        }

        public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
            return null;
        }

        public NetworkStats getNetworkStatsDetail() throws RemoteException {
            return null;
        }

        public NetworkStats getNetworkStatsUidDetail(int uid, String[] ifaces) throws RemoteException {
            return null;
        }

        public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
            return null;
        }

        public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
        }

        public void removeInterfaceQuota(String iface) throws RemoteException {
        }

        public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
        }

        public void removeInterfaceAlert(String iface) throws RemoteException {
        }

        public void setGlobalAlert(long alertBytes) throws RemoteException {
        }

        public void setUidMeteredNetworkBlacklist(int uid, boolean enable) throws RemoteException {
        }

        public void setUidMeteredNetworkWhitelist(int uid, boolean enable) throws RemoteException {
        }

        public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
        }

        public boolean isBandwidthControlEnabled() throws RemoteException {
            return false;
        }

        public void addIdleTimer(String iface, int timeout, int type) throws RemoteException {
        }

        public void removeIdleTimer(String iface) throws RemoteException {
        }

        public void setFirewallEnabled(boolean enabled) throws RemoteException {
        }

        public boolean isFirewallEnabled() throws RemoteException {
            return false;
        }

        public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {
        }

        public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
        }

        public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {
        }

        public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
        }

        public void addVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
        }

        public void removeVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
        }

        public void registerNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
        }

        public void unregisterNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
        }

        public boolean isNetworkActive() throws RemoteException {
            return false;
        }

        public void addInterfaceToNetwork(String iface, int netId) throws RemoteException {
        }

        public void removeInterfaceFromNetwork(String iface, int netId) throws RemoteException {
        }

        public void addLegacyRouteForNetId(int netId, RouteInfo routeInfo, int uid) throws RemoteException {
        }

        public void setDefaultNetId(int netId) throws RemoteException {
        }

        public void clearDefaultNetId() throws RemoteException {
        }

        public void setNetworkPermission(int netId, int permission) throws RemoteException {
        }

        public void allowProtect(int uid) throws RemoteException {
        }

        public void denyProtect(int uid) throws RemoteException {
        }

        public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> list) throws RemoteException {
        }

        public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {
        }

        public int removeRoutesFromLocalNetwork(List<RouteInfo> list) throws RemoteException {
            return 0;
        }

        public void setAllowOnlyVpnForUids(boolean enable, UidRange[] uidRanges) throws RemoteException {
        }

        public boolean isNetworkRestricted(int uid) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof INetworkManagementService)) {
                return new Proxy(obj);
            }
            return (INetworkManagementService) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.net.InterfaceConfiguration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.net.RouteInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.net.RouteInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v36, resolved type: android.net.Network} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v84, resolved type: android.net.RouteInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v100 */
        /* JADX WARNING: type inference failed for: r1v101 */
        /* JADX WARNING: type inference failed for: r1v102 */
        /* JADX WARNING: type inference failed for: r1v103 */
        /* JADX WARNING: type inference failed for: r1v104 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.os.INetworkManagementService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x057c
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x056a;
                    case 2: goto L_0x0558;
                    case 3: goto L_0x054a;
                    case 4: goto L_0x052f;
                    case 5: goto L_0x0511;
                    case 6: goto L_0x0503;
                    case 7: goto L_0x04f5;
                    case 8: goto L_0x04e7;
                    case 9: goto L_0x04d1;
                    case 10: goto L_0x04c3;
                    case 11: goto L_0x04b5;
                    case 12: goto L_0x04a3;
                    case 13: goto L_0x0485;
                    case 14: goto L_0x0467;
                    case 15: goto L_0x0455;
                    case 16: goto L_0x044b;
                    case 17: goto L_0x043d;
                    case 18: goto L_0x042a;
                    case 19: goto L_0x041c;
                    case 20: goto L_0x0412;
                    case 21: goto L_0x0404;
                    case 22: goto L_0x03f6;
                    case 23: goto L_0x03e8;
                    case 24: goto L_0x03da;
                    case 25: goto L_0x03bc;
                    case 26: goto L_0x03ae;
                    case 27: goto L_0x039c;
                    case 28: goto L_0x038a;
                    case 29: goto L_0x0378;
                    case 30: goto L_0x0366;
                    case 31: goto L_0x0350;
                    case 32: goto L_0x033e;
                    case 33: goto L_0x032c;
                    case 34: goto L_0x0315;
                    case 35: goto L_0x02fe;
                    case 36: goto L_0x02e7;
                    case 37: goto L_0x02c8;
                    case 38: goto L_0x02ad;
                    case 39: goto L_0x029b;
                    case 40: goto L_0x028d;
                    case 41: goto L_0x027b;
                    case 42: goto L_0x026d;
                    case 43: goto L_0x025f;
                    case 44: goto L_0x0249;
                    case 45: goto L_0x0233;
                    case 46: goto L_0x021c;
                    case 47: goto L_0x020a;
                    case 48: goto L_0x01fc;
                    case 49: goto L_0x01e6;
                    case 50: goto L_0x01d8;
                    case 51: goto L_0x01c5;
                    case 52: goto L_0x01b7;
                    case 53: goto L_0x01a1;
                    case 54: goto L_0x018b;
                    case 55: goto L_0x0175;
                    case 56: goto L_0x015f;
                    case 57: goto L_0x0149;
                    case 58: goto L_0x0133;
                    case 59: goto L_0x0121;
                    case 60: goto L_0x010f;
                    case 61: goto L_0x0101;
                    case 62: goto L_0x00ef;
                    case 63: goto L_0x00dd;
                    case 64: goto L_0x00bb;
                    case 65: goto L_0x00ad;
                    case 66: goto L_0x00a3;
                    case 67: goto L_0x0091;
                    case 68: goto L_0x0083;
                    case 69: goto L_0x0075;
                    case 70: goto L_0x0061;
                    case 71: goto L_0x0053;
                    case 72: goto L_0x003f;
                    case 73: goto L_0x0024;
                    case 74: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                boolean r3 = r6.isNetworkRestricted(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0024:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x002f
                r3 = r2
            L_0x002f:
                r1 = r3
                android.os.Parcelable$Creator<android.net.UidRange> r3 = android.net.UidRange.CREATOR
                java.lang.Object[] r3 = r8.createTypedArray(r3)
                android.net.UidRange[] r3 = (android.net.UidRange[]) r3
                r6.setAllowOnlyVpnForUids(r1, r3)
                r9.writeNoException()
                return r2
            L_0x003f:
                r8.enforceInterface(r0)
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.util.ArrayList r1 = r8.createTypedArrayList(r1)
                int r3 = r6.removeRoutesFromLocalNetwork(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0053:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.removeInterfaceFromLocalNetwork(r1)
                r9.writeNoException()
                return r2
            L_0x0061:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                android.os.Parcelable$Creator<android.net.RouteInfo> r3 = android.net.RouteInfo.CREATOR
                java.util.ArrayList r3 = r8.createTypedArrayList(r3)
                r6.addInterfaceToLocalNetwork(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0075:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.denyProtect(r1)
                r9.writeNoException()
                return r2
            L_0x0083:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.allowProtect(r1)
                r9.writeNoException()
                return r2
            L_0x0091:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.setNetworkPermission(r1, r3)
                r9.writeNoException()
                return r2
            L_0x00a3:
                r8.enforceInterface(r0)
                r6.clearDefaultNetId()
                r9.writeNoException()
                return r2
            L_0x00ad:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.setDefaultNetId(r1)
                r9.writeNoException()
                return r2
            L_0x00bb:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00d1
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.RouteInfo r1 = (android.net.RouteInfo) r1
                goto L_0x00d2
            L_0x00d1:
            L_0x00d2:
                int r4 = r8.readInt()
                r6.addLegacyRouteForNetId(r3, r1, r4)
                r9.writeNoException()
                return r2
            L_0x00dd:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.removeInterfaceFromNetwork(r1, r3)
                r9.writeNoException()
                return r2
            L_0x00ef:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.addInterfaceToNetwork(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0101:
                r8.enforceInterface(r0)
                boolean r1 = r6.isNetworkActive()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x010f:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.os.INetworkActivityListener r1 = android.os.INetworkActivityListener.Stub.asInterface(r1)
                r6.unregisterNetworkActivityListener(r1)
                r9.writeNoException()
                return r2
            L_0x0121:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.os.INetworkActivityListener r1 = android.os.INetworkActivityListener.Stub.asInterface(r1)
                r6.registerNetworkActivityListener(r1)
                r9.writeNoException()
                return r2
            L_0x0133:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.os.Parcelable$Creator<android.net.UidRange> r3 = android.net.UidRange.CREATOR
                java.lang.Object[] r3 = r8.createTypedArray(r3)
                android.net.UidRange[] r3 = (android.net.UidRange[]) r3
                r6.removeVpnUidRanges(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0149:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.os.Parcelable$Creator<android.net.UidRange> r3 = android.net.UidRange.CREATOR
                java.lang.Object[] r3 = r8.createTypedArray(r3)
                android.net.UidRange[] r3 = (android.net.UidRange[]) r3
                r6.addVpnUidRanges(r1, r3)
                r9.writeNoException()
                return r2
            L_0x015f:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x016e
                r3 = r2
            L_0x016e:
                r6.setFirewallChainEnabled(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0175:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int[] r3 = r8.createIntArray()
                int[] r4 = r8.createIntArray()
                r6.setFirewallUidRules(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x018b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                r6.setFirewallUidRule(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x01a1:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01b0
                r3 = r2
            L_0x01b0:
                r6.setFirewallInterfaceRule(r1, r3)
                r9.writeNoException()
                return r2
            L_0x01b7:
                r8.enforceInterface(r0)
                boolean r1 = r6.isFirewallEnabled()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x01c5:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01d0
                r3 = r2
            L_0x01d0:
                r1 = r3
                r6.setFirewallEnabled(r1)
                r9.writeNoException()
                return r2
            L_0x01d8:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.removeIdleTimer(r1)
                r9.writeNoException()
                return r2
            L_0x01e6:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                r6.addIdleTimer(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x01fc:
                r8.enforceInterface(r0)
                boolean r1 = r6.isBandwidthControlEnabled()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x020a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.setUidCleartextNetworkPolicy(r1, r3)
                r9.writeNoException()
                return r2
            L_0x021c:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0227
                r3 = r2
            L_0x0227:
                r1 = r3
                boolean r3 = r6.setDataSaverModeEnabled(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0233:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0242
                r3 = r2
            L_0x0242:
                r6.setUidMeteredNetworkWhitelist(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0249:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0258
                r3 = r2
            L_0x0258:
                r6.setUidMeteredNetworkBlacklist(r1, r3)
                r9.writeNoException()
                return r2
            L_0x025f:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.setGlobalAlert(r3)
                r9.writeNoException()
                return r2
            L_0x026d:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.removeInterfaceAlert(r1)
                r9.writeNoException()
                return r2
            L_0x027b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                long r3 = r8.readLong()
                r6.setInterfaceAlert(r1, r3)
                r9.writeNoException()
                return r2
            L_0x028d:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.removeInterfaceQuota(r1)
                r9.writeNoException()
                return r2
            L_0x029b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                long r3 = r8.readLong()
                r6.setInterfaceQuota(r1, r3)
                r9.writeNoException()
                return r2
            L_0x02ad:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.net.NetworkStats r4 = r6.getNetworkStatsTethering(r1)
                r9.writeNoException()
                if (r4 == 0) goto L_0x02c4
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x02c7
            L_0x02c4:
                r9.writeInt(r3)
            L_0x02c7:
                return r2
            L_0x02c8:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                java.lang.String[] r4 = r8.createStringArray()
                android.net.NetworkStats r5 = r6.getNetworkStatsUidDetail(r1, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x02e3
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x02e6
            L_0x02e3:
                r9.writeInt(r3)
            L_0x02e6:
                return r2
            L_0x02e7:
                r8.enforceInterface(r0)
                android.net.NetworkStats r1 = r6.getNetworkStatsDetail()
                r9.writeNoException()
                if (r1 == 0) goto L_0x02fa
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                goto L_0x02fd
            L_0x02fa:
                r9.writeInt(r3)
            L_0x02fd:
                return r2
            L_0x02fe:
                r8.enforceInterface(r0)
                android.net.NetworkStats r1 = r6.getNetworkStatsSummaryXt()
                r9.writeNoException()
                if (r1 == 0) goto L_0x0311
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                goto L_0x0314
            L_0x0311:
                r9.writeInt(r3)
            L_0x0314:
                return r2
            L_0x0315:
                r8.enforceInterface(r0)
                android.net.NetworkStats r1 = r6.getNetworkStatsSummaryDev()
                r9.writeNoException()
                if (r1 == 0) goto L_0x0328
                r9.writeInt(r2)
                r1.writeToParcel(r9, r2)
                goto L_0x032b
            L_0x0328:
                r9.writeInt(r3)
            L_0x032b:
                return r2
            L_0x032c:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.net.ITetheringStatsProvider r1 = android.net.ITetheringStatsProvider.Stub.asInterface(r1)
                r6.tetherLimitReached(r1)
                r9.writeNoException()
                return r2
            L_0x033e:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.net.ITetheringStatsProvider r1 = android.net.ITetheringStatsProvider.Stub.asInterface(r1)
                r6.unregisterTetheringStatsProvider(r1)
                r9.writeNoException()
                return r2
            L_0x0350:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.net.ITetheringStatsProvider r1 = android.net.ITetheringStatsProvider.Stub.asInterface(r1)
                java.lang.String r3 = r8.readString()
                r6.registerTetheringStatsProvider(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0366:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.disableNat(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0378:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.enableNat(r1, r3)
                r9.writeNoException()
                return r2
            L_0x038a:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.stopInterfaceForwarding(r1, r3)
                r9.writeNoException()
                return r2
            L_0x039c:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.startInterfaceForwarding(r1, r3)
                r9.writeNoException()
                return r2
            L_0x03ae:
                r8.enforceInterface(r0)
                java.lang.String[] r1 = r6.getDnsForwarders()
                r9.writeNoException()
                r9.writeStringArray(r1)
                return r2
            L_0x03bc:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x03ce
                android.os.Parcelable$Creator<android.net.Network> r1 = android.net.Network.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Network r1 = (android.net.Network) r1
                goto L_0x03cf
            L_0x03ce:
            L_0x03cf:
                java.lang.String[] r3 = r8.createStringArray()
                r6.setDnsForwarders(r1, r3)
                r9.writeNoException()
                return r2
            L_0x03da:
                r8.enforceInterface(r0)
                java.lang.String[] r1 = r6.listTetheredInterfaces()
                r9.writeNoException()
                r9.writeStringArray(r1)
                return r2
            L_0x03e8:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.untetherInterface(r1)
                r9.writeNoException()
                return r2
            L_0x03f6:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.tetherInterface(r1)
                r9.writeNoException()
                return r2
            L_0x0404:
                r8.enforceInterface(r0)
                boolean r1 = r6.isTetheringStarted()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0412:
                r8.enforceInterface(r0)
                r6.stopTethering()
                r9.writeNoException()
                return r2
            L_0x041c:
                r8.enforceInterface(r0)
                java.lang.String[] r1 = r8.createStringArray()
                r6.startTethering(r1)
                r9.writeNoException()
                return r2
            L_0x042a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0435
                r3 = r2
            L_0x0435:
                r1 = r3
                r6.setIpForwardingEnabled(r1)
                r9.writeNoException()
                return r2
            L_0x043d:
                r8.enforceInterface(r0)
                boolean r1 = r6.getIpForwardingEnabled()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x044b:
                r8.enforceInterface(r0)
                r6.shutdown()
                r9.writeNoException()
                return r2
            L_0x0455:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.setMtu(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0467:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x047d
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.RouteInfo r1 = (android.net.RouteInfo) r1
                goto L_0x047e
            L_0x047d:
            L_0x047e:
                r6.removeRoute(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0485:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x049b
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.RouteInfo r1 = (android.net.RouteInfo) r1
                goto L_0x049c
            L_0x049b:
            L_0x049c:
                r6.addRoute(r3, r1)
                r9.writeNoException()
                return r2
            L_0x04a3:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.setIPv6AddrGenMode(r1, r3)
                r9.writeNoException()
                return r2
            L_0x04b5:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.enableIpv6(r1)
                r9.writeNoException()
                return r2
            L_0x04c3:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.disableIpv6(r1)
                r9.writeNoException()
                return r2
            L_0x04d1:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x04e0
                r3 = r2
            L_0x04e0:
                r6.setInterfaceIpv6PrivacyExtensions(r1, r3)
                r9.writeNoException()
                return r2
            L_0x04e7:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setInterfaceUp(r1)
                r9.writeNoException()
                return r2
            L_0x04f5:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setInterfaceDown(r1)
                r9.writeNoException()
                return r2
            L_0x0503:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.clearInterfaceAddresses(r1)
                r9.writeNoException()
                return r2
            L_0x0511:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0527
                android.os.Parcelable$Creator<android.net.InterfaceConfiguration> r1 = android.net.InterfaceConfiguration.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.InterfaceConfiguration r1 = (android.net.InterfaceConfiguration) r1
                goto L_0x0528
            L_0x0527:
            L_0x0528:
                r6.setInterfaceConfig(r3, r1)
                r9.writeNoException()
                return r2
            L_0x052f:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                android.net.InterfaceConfiguration r4 = r6.getInterfaceConfig(r1)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0546
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0549
            L_0x0546:
                r9.writeInt(r3)
            L_0x0549:
                return r2
            L_0x054a:
                r8.enforceInterface(r0)
                java.lang.String[] r1 = r6.listInterfaces()
                r9.writeNoException()
                r9.writeStringArray(r1)
                return r2
            L_0x0558:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.net.INetworkManagementEventObserver r1 = android.net.INetworkManagementEventObserver.Stub.asInterface(r1)
                r6.unregisterObserver(r1)
                r9.writeNoException()
                return r2
            L_0x056a:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.net.INetworkManagementEventObserver r1 = android.net.INetworkManagementEventObserver.Stub.asInterface(r1)
                r6.registerObserver(r1)
                r9.writeNoException()
                return r2
            L_0x057c:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.INetworkManagementService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INetworkManagementService {
            public static INetworkManagementService sDefaultImpl;
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

            public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(obs != null ? obs.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerObserver(obs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(obs != null ? obs.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterObserver(obs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] listInterfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listInterfaces();
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

            public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
                InterfaceConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInterfaceConfig(iface);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = InterfaceConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    InterfaceConfiguration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceConfig(iface, cfg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearInterfaceAddresses(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearInterfaceAddresses(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterfaceDown(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceDown(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterfaceUp(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceUp(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceIpv6PrivacyExtensions(iface, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableIpv6(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableIpv6(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableIpv6(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableIpv6(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIPv6AddrGenMode(iface, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRoute(netId, route);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRoute(netId, route);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMtu(String iface, int mtu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(mtu);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMtu(iface, mtu);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().shutdown();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getIpForwardingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIpForwardingEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIpForwardingEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startTethering(String[] dhcpRanges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(dhcpRanges);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startTethering(dhcpRanges);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopTethering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopTethering();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTetheringStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTetheringStarted();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tetherInterface(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void untetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().untetherInterface(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] listTetheredInterfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listTetheredInterfaces();
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
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDnsForwarders(network, dns);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getDnsForwarders() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDnsForwarders();
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

            public void startInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromIface);
                    _data.writeString(toIface);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startInterfaceForwarding(fromIface, toIface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromIface);
                    _data.writeString(toIface);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopInterfaceForwarding(fromIface, toIface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableNat(String internalInterface, String externalInterface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableNat(internalInterface, externalInterface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableNat(String internalInterface, String externalInterface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableNat(internalInterface, externalInterface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    _data.writeString(name);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerTetheringStatsProvider(provider, name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterTetheringStatsProvider(provider);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider != null ? provider.asBinder() : null);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tetherLimitReached(provider);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsSummaryDev();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsSummaryXt();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getNetworkStatsDetail() throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsDetail();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getNetworkStatsUidDetail(int uid, String[] ifaces) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(ifaces);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsUidDetail(uid, ifaces);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(how);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkStatsTethering(how);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(quotaBytes);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceQuota(iface, quotaBytes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeInterfaceQuota(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeInterfaceQuota(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(alertBytes);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInterfaceAlert(iface, alertBytes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeInterfaceAlert(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeInterfaceAlert(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGlobalAlert(long alertBytes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(alertBytes);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGlobalAlert(alertBytes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUidMeteredNetworkBlacklist(int uid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUidMeteredNetworkBlacklist(uid, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUidMeteredNetworkWhitelist(int uid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUidMeteredNetworkWhitelist(uid, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    boolean z = false;
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDataSaverModeEnabled(enable);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUidCleartextNetworkPolicy(uid, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBandwidthControlEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBandwidthControlEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addIdleTimer(String iface, int timeout, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(timeout);
                    _data.writeInt(type);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addIdleTimer(iface, timeout, type);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeIdleTimer(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeIdleTimer(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFirewallEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFirewallEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isFirewallEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isFirewallEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(allow);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFirewallInterfaceRule(iface, allow);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeInt(uid);
                    _data.writeInt(rule);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFirewallUidRule(chain, uid, rule);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeIntArray(uids);
                    _data.writeIntArray(rules);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFirewallUidRules(chain, uids, rules);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFirewallChainEnabled(chain, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeTypedArray(ranges, 0);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addVpnUidRanges(netId, ranges);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeVpnUidRanges(int netId, UidRange[] ranges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeTypedArray(ranges, 0);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeVpnUidRanges(netId, ranges);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerNetworkActivityListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterNetworkActivityListener(INetworkActivityListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterNetworkActivityListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNetworkActive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkActive();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addInterfaceToNetwork(String iface, int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(netId);
                    if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addInterfaceToNetwork(iface, netId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeInterfaceFromNetwork(String iface, int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(netId);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeInterfaceFromNetwork(iface, netId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addLegacyRouteForNetId(netId, routeInfo, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDefaultNetId(int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDefaultNetId(netId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearDefaultNetId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearDefaultNetId();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNetworkPermission(int netId, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeInt(permission);
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNetworkPermission(netId, permission);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void allowProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().allowProtect(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void denyProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().denyProtect(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> routes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeTypedList(routes);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addInterfaceToLocalNetwork(iface, routes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeInterfaceFromLocalNetwork(iface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int removeRoutesFromLocalNetwork(List<RouteInfo> routes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(routes);
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeRoutesFromLocalNetwork(routes);
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

            public void setAllowOnlyVpnForUids(boolean enable, UidRange[] uidRanges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    _data.writeTypedArray(uidRanges, 0);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAllowOnlyVpnForUids(enable, uidRanges);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNetworkRestricted(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    boolean z = false;
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNetworkRestricted(uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkManagementService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkManagementService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
