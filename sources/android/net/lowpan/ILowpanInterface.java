package android.net.lowpan;

import android.net.IpPrefix;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

public interface ILowpanInterface extends IInterface {
    public static final int ERROR_ALREADY = 9;
    public static final int ERROR_BUSY = 8;
    public static final int ERROR_CANCELED = 10;
    public static final int ERROR_DISABLED = 3;
    public static final int ERROR_FEATURE_NOT_SUPPORTED = 11;
    public static final int ERROR_FORM_FAILED_AT_SCAN = 15;
    public static final int ERROR_INVALID_ARGUMENT = 2;
    public static final int ERROR_IO_FAILURE = 6;
    public static final int ERROR_JOIN_FAILED_AT_AUTH = 14;
    public static final int ERROR_JOIN_FAILED_AT_SCAN = 13;
    public static final int ERROR_JOIN_FAILED_UNKNOWN = 12;
    public static final int ERROR_NCP_PROBLEM = 7;
    public static final int ERROR_TIMEOUT = 5;
    public static final int ERROR_UNSPECIFIED = 1;
    public static final int ERROR_WRONG_STATE = 4;
    public static final String KEY_CHANNEL_MASK = "android.net.lowpan.property.CHANNEL_MASK";
    public static final String KEY_MAX_TX_POWER = "android.net.lowpan.property.MAX_TX_POWER";
    public static final String NETWORK_TYPE_THREAD_V1 = "org.threadgroup.thread.v1";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String PERM_ACCESS_LOWPAN_STATE = "android.permission.ACCESS_LOWPAN_STATE";
    public static final String PERM_CHANGE_LOWPAN_STATE = "android.permission.CHANGE_LOWPAN_STATE";
    public static final String PERM_READ_LOWPAN_CREDENTIAL = "android.permission.READ_LOWPAN_CREDENTIAL";
    public static final String ROLE_COORDINATOR = "coordinator";
    public static final String ROLE_DETACHED = "detached";
    public static final String ROLE_END_DEVICE = "end-device";
    public static final String ROLE_LEADER = "leader";
    public static final String ROLE_ROUTER = "router";
    public static final String ROLE_SLEEPY_END_DEVICE = "sleepy-end-device";
    public static final String ROLE_SLEEPY_ROUTER = "sleepy-router";
    public static final String STATE_ATTACHED = "attached";
    public static final String STATE_ATTACHING = "attaching";
    public static final String STATE_COMMISSIONING = "commissioning";
    public static final String STATE_FAULT = "fault";
    public static final String STATE_OFFLINE = "offline";

    void addExternalRoute(IpPrefix ipPrefix, int i) throws RemoteException;

    void addListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException;

    void addOnMeshPrefix(IpPrefix ipPrefix, int i) throws RemoteException;

    void attach(LowpanProvision lowpanProvision) throws RemoteException;

    void beginLowPower() throws RemoteException;

    void closeCommissioningSession() throws RemoteException;

    void form(LowpanProvision lowpanProvision) throws RemoteException;

    String getDriverVersion() throws RemoteException;

    byte[] getExtendedAddress() throws RemoteException;

    String[] getLinkAddresses() throws RemoteException;

    IpPrefix[] getLinkNetworks() throws RemoteException;

    LowpanCredential getLowpanCredential() throws RemoteException;

    LowpanIdentity getLowpanIdentity() throws RemoteException;

    byte[] getMacAddress() throws RemoteException;

    String getName() throws RemoteException;

    String getNcpVersion() throws RemoteException;

    String getPartitionId() throws RemoteException;

    String getRole() throws RemoteException;

    String getState() throws RemoteException;

    LowpanChannelInfo[] getSupportedChannels() throws RemoteException;

    String[] getSupportedNetworkTypes() throws RemoteException;

    boolean isCommissioned() throws RemoteException;

    boolean isConnected() throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isUp() throws RemoteException;

    void join(LowpanProvision lowpanProvision) throws RemoteException;

    void leave() throws RemoteException;

    void onHostWake() throws RemoteException;

    void pollForData() throws RemoteException;

    void removeExternalRoute(IpPrefix ipPrefix) throws RemoteException;

    void removeListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException;

    void removeOnMeshPrefix(IpPrefix ipPrefix) throws RemoteException;

    void reset() throws RemoteException;

    void sendToCommissioner(byte[] bArr) throws RemoteException;

    void setEnabled(boolean z) throws RemoteException;

    void startCommissioningSession(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException;

    void startEnergyScan(Map map, ILowpanEnergyScanCallback iLowpanEnergyScanCallback) throws RemoteException;

    void startNetScan(Map map, ILowpanNetScanCallback iLowpanNetScanCallback) throws RemoteException;

    void stopEnergyScan() throws RemoteException;

    void stopNetScan() throws RemoteException;

    public static class Default implements ILowpanInterface {
        public String getName() throws RemoteException {
            return null;
        }

        public String getNcpVersion() throws RemoteException {
            return null;
        }

        public String getDriverVersion() throws RemoteException {
            return null;
        }

        public LowpanChannelInfo[] getSupportedChannels() throws RemoteException {
            return null;
        }

        public String[] getSupportedNetworkTypes() throws RemoteException {
            return null;
        }

        public byte[] getMacAddress() throws RemoteException {
            return null;
        }

        public boolean isEnabled() throws RemoteException {
            return false;
        }

        public void setEnabled(boolean enabled) throws RemoteException {
        }

        public boolean isUp() throws RemoteException {
            return false;
        }

        public boolean isCommissioned() throws RemoteException {
            return false;
        }

        public boolean isConnected() throws RemoteException {
            return false;
        }

        public String getState() throws RemoteException {
            return null;
        }

        public String getRole() throws RemoteException {
            return null;
        }

        public String getPartitionId() throws RemoteException {
            return null;
        }

        public byte[] getExtendedAddress() throws RemoteException {
            return null;
        }

        public LowpanIdentity getLowpanIdentity() throws RemoteException {
            return null;
        }

        public LowpanCredential getLowpanCredential() throws RemoteException {
            return null;
        }

        public String[] getLinkAddresses() throws RemoteException {
            return null;
        }

        public IpPrefix[] getLinkNetworks() throws RemoteException {
            return null;
        }

        public void join(LowpanProvision provision) throws RemoteException {
        }

        public void form(LowpanProvision provision) throws RemoteException {
        }

        public void attach(LowpanProvision provision) throws RemoteException {
        }

        public void leave() throws RemoteException {
        }

        public void reset() throws RemoteException {
        }

        public void startCommissioningSession(LowpanBeaconInfo beaconInfo) throws RemoteException {
        }

        public void closeCommissioningSession() throws RemoteException {
        }

        public void sendToCommissioner(byte[] packet) throws RemoteException {
        }

        public void beginLowPower() throws RemoteException {
        }

        public void pollForData() throws RemoteException {
        }

        public void onHostWake() throws RemoteException {
        }

        public void addListener(ILowpanInterfaceListener listener) throws RemoteException {
        }

        public void removeListener(ILowpanInterfaceListener listener) throws RemoteException {
        }

        public void startNetScan(Map properties, ILowpanNetScanCallback listener) throws RemoteException {
        }

        public void stopNetScan() throws RemoteException {
        }

        public void startEnergyScan(Map properties, ILowpanEnergyScanCallback listener) throws RemoteException {
        }

        public void stopEnergyScan() throws RemoteException {
        }

        public void addOnMeshPrefix(IpPrefix prefix, int flags) throws RemoteException {
        }

        public void removeOnMeshPrefix(IpPrefix prefix) throws RemoteException {
        }

        public void addExternalRoute(IpPrefix prefix, int flags) throws RemoteException {
        }

        public void removeExternalRoute(IpPrefix prefix) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILowpanInterface {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanInterface";
        static final int TRANSACTION_addExternalRoute = 39;
        static final int TRANSACTION_addListener = 31;
        static final int TRANSACTION_addOnMeshPrefix = 37;
        static final int TRANSACTION_attach = 22;
        static final int TRANSACTION_beginLowPower = 28;
        static final int TRANSACTION_closeCommissioningSession = 26;
        static final int TRANSACTION_form = 21;
        static final int TRANSACTION_getDriverVersion = 3;
        static final int TRANSACTION_getExtendedAddress = 15;
        static final int TRANSACTION_getLinkAddresses = 18;
        static final int TRANSACTION_getLinkNetworks = 19;
        static final int TRANSACTION_getLowpanCredential = 17;
        static final int TRANSACTION_getLowpanIdentity = 16;
        static final int TRANSACTION_getMacAddress = 6;
        static final int TRANSACTION_getName = 1;
        static final int TRANSACTION_getNcpVersion = 2;
        static final int TRANSACTION_getPartitionId = 14;
        static final int TRANSACTION_getRole = 13;
        static final int TRANSACTION_getState = 12;
        static final int TRANSACTION_getSupportedChannels = 4;
        static final int TRANSACTION_getSupportedNetworkTypes = 5;
        static final int TRANSACTION_isCommissioned = 10;
        static final int TRANSACTION_isConnected = 11;
        static final int TRANSACTION_isEnabled = 7;
        static final int TRANSACTION_isUp = 9;
        static final int TRANSACTION_join = 20;
        static final int TRANSACTION_leave = 23;
        static final int TRANSACTION_onHostWake = 30;
        static final int TRANSACTION_pollForData = 29;
        static final int TRANSACTION_removeExternalRoute = 40;
        static final int TRANSACTION_removeListener = 32;
        static final int TRANSACTION_removeOnMeshPrefix = 38;
        static final int TRANSACTION_reset = 24;
        static final int TRANSACTION_sendToCommissioner = 27;
        static final int TRANSACTION_setEnabled = 8;
        static final int TRANSACTION_startCommissioningSession = 25;
        static final int TRANSACTION_startEnergyScan = 35;
        static final int TRANSACTION_startNetScan = 33;
        static final int TRANSACTION_stopEnergyScan = 36;
        static final int TRANSACTION_stopNetScan = 34;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILowpanInterface)) {
                return new Proxy(obj);
            }
            return (ILowpanInterface) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getName";
                case 2:
                    return "getNcpVersion";
                case 3:
                    return "getDriverVersion";
                case 4:
                    return "getSupportedChannels";
                case 5:
                    return "getSupportedNetworkTypes";
                case 6:
                    return "getMacAddress";
                case 7:
                    return "isEnabled";
                case 8:
                    return "setEnabled";
                case 9:
                    return "isUp";
                case 10:
                    return "isCommissioned";
                case 11:
                    return "isConnected";
                case 12:
                    return "getState";
                case 13:
                    return "getRole";
                case 14:
                    return "getPartitionId";
                case 15:
                    return "getExtendedAddress";
                case 16:
                    return "getLowpanIdentity";
                case 17:
                    return "getLowpanCredential";
                case 18:
                    return "getLinkAddresses";
                case 19:
                    return "getLinkNetworks";
                case 20:
                    return "join";
                case 21:
                    return "form";
                case 22:
                    return "attach";
                case 23:
                    return "leave";
                case 24:
                    return "reset";
                case 25:
                    return "startCommissioningSession";
                case 26:
                    return "closeCommissioningSession";
                case 27:
                    return "sendToCommissioner";
                case 28:
                    return "beginLowPower";
                case 29:
                    return "pollForData";
                case 30:
                    return "onHostWake";
                case 31:
                    return "addListener";
                case 32:
                    return "removeListener";
                case 33:
                    return "startNetScan";
                case 34:
                    return "stopNetScan";
                case 35:
                    return "startEnergyScan";
                case 36:
                    return "stopEnergyScan";
                case 37:
                    return "addOnMeshPrefix";
                case 38:
                    return "removeOnMeshPrefix";
                case 39:
                    return "addExternalRoute";
                case 40:
                    return "removeExternalRoute";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.net.lowpan.LowpanBeaconInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v46, resolved type: android.net.IpPrefix} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v50, resolved type: android.net.IpPrefix} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v54, resolved type: android.net.IpPrefix} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v58, resolved type: android.net.IpPrefix} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v36, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v37, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v38, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v39, resolved type: android.net.lowpan.LowpanProvision} */
        /* JADX WARNING: type inference failed for: r3v15, types: [android.net.lowpan.LowpanBeaconInfo] */
        /* JADX WARNING: type inference failed for: r3v21, types: [android.net.IpPrefix] */
        /* JADX WARNING: type inference failed for: r3v24, types: [android.net.IpPrefix] */
        /* JADX WARNING: type inference failed for: r3v28, types: [android.net.IpPrefix] */
        /* JADX WARNING: type inference failed for: r3v31, types: [android.net.IpPrefix] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.net.lowpan.ILowpanInterface"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x02c0
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x02b2;
                    case 2: goto L_0x02a4;
                    case 3: goto L_0x0296;
                    case 4: goto L_0x0288;
                    case 5: goto L_0x027a;
                    case 6: goto L_0x026c;
                    case 7: goto L_0x025e;
                    case 8: goto L_0x024c;
                    case 9: goto L_0x023e;
                    case 10: goto L_0x0230;
                    case 11: goto L_0x0222;
                    case 12: goto L_0x0214;
                    case 13: goto L_0x0206;
                    case 14: goto L_0x01f8;
                    case 15: goto L_0x01ea;
                    case 16: goto L_0x01d3;
                    case 17: goto L_0x01bc;
                    case 18: goto L_0x01ae;
                    case 19: goto L_0x01a0;
                    case 20: goto L_0x0184;
                    case 21: goto L_0x0168;
                    case 22: goto L_0x014c;
                    case 23: goto L_0x0142;
                    case 24: goto L_0x0138;
                    case 25: goto L_0x011c;
                    case 26: goto L_0x0112;
                    case 27: goto L_0x0107;
                    case 28: goto L_0x00fd;
                    case 29: goto L_0x00f6;
                    case 30: goto L_0x00ef;
                    case 31: goto L_0x00dd;
                    case 32: goto L_0x00ce;
                    case 33: goto L_0x00b0;
                    case 34: goto L_0x00a9;
                    case 35: goto L_0x008b;
                    case 36: goto L_0x0084;
                    case 37: goto L_0x0064;
                    case 38: goto L_0x004b;
                    case 39: goto L_0x002b;
                    case 40: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0025
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.IpPrefix r3 = (android.net.IpPrefix) r3
                goto L_0x0026
            L_0x0025:
            L_0x0026:
                r1 = r3
                r5.removeExternalRoute(r1)
                return r2
            L_0x002b:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.IpPrefix r3 = (android.net.IpPrefix) r3
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                r1 = r3
                int r3 = r7.readInt()
                r5.addExternalRoute(r1, r3)
                r8.writeNoException()
                return r2
            L_0x004b:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x005e
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.IpPrefix r3 = (android.net.IpPrefix) r3
                goto L_0x005f
            L_0x005e:
            L_0x005f:
                r1 = r3
                r5.removeOnMeshPrefix(r1)
                return r2
            L_0x0064:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.IpPrefix r3 = (android.net.IpPrefix) r3
                goto L_0x0078
            L_0x0077:
            L_0x0078:
                r1 = r3
                int r3 = r7.readInt()
                r5.addOnMeshPrefix(r1, r3)
                r8.writeNoException()
                return r2
            L_0x0084:
                r7.enforceInterface(r0)
                r5.stopEnergyScan()
                return r2
            L_0x008b:
                r7.enforceInterface(r0)
                java.lang.Class r1 = r5.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r3 = r7.readHashMap(r1)
                android.os.IBinder r4 = r7.readStrongBinder()
                android.net.lowpan.ILowpanEnergyScanCallback r4 = android.net.lowpan.ILowpanEnergyScanCallback.Stub.asInterface(r4)
                r5.startEnergyScan(r3, r4)
                r8.writeNoException()
                return r2
            L_0x00a9:
                r7.enforceInterface(r0)
                r5.stopNetScan()
                return r2
            L_0x00b0:
                r7.enforceInterface(r0)
                java.lang.Class r1 = r5.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r3 = r7.readHashMap(r1)
                android.os.IBinder r4 = r7.readStrongBinder()
                android.net.lowpan.ILowpanNetScanCallback r4 = android.net.lowpan.ILowpanNetScanCallback.Stub.asInterface(r4)
                r5.startNetScan(r3, r4)
                r8.writeNoException()
                return r2
            L_0x00ce:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.net.lowpan.ILowpanInterfaceListener r1 = android.net.lowpan.ILowpanInterfaceListener.Stub.asInterface(r1)
                r5.removeListener(r1)
                return r2
            L_0x00dd:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                android.net.lowpan.ILowpanInterfaceListener r1 = android.net.lowpan.ILowpanInterfaceListener.Stub.asInterface(r1)
                r5.addListener(r1)
                r8.writeNoException()
                return r2
            L_0x00ef:
                r7.enforceInterface(r0)
                r5.onHostWake()
                return r2
            L_0x00f6:
                r7.enforceInterface(r0)
                r5.pollForData()
                return r2
            L_0x00fd:
                r7.enforceInterface(r0)
                r5.beginLowPower()
                r8.writeNoException()
                return r2
            L_0x0107:
                r7.enforceInterface(r0)
                byte[] r1 = r7.createByteArray()
                r5.sendToCommissioner(r1)
                return r2
            L_0x0112:
                r7.enforceInterface(r0)
                r5.closeCommissioningSession()
                r8.writeNoException()
                return r2
            L_0x011c:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x012f
                android.os.Parcelable$Creator<android.net.lowpan.LowpanBeaconInfo> r1 = android.net.lowpan.LowpanBeaconInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.lowpan.LowpanBeaconInfo r3 = (android.net.lowpan.LowpanBeaconInfo) r3
                goto L_0x0130
            L_0x012f:
            L_0x0130:
                r1 = r3
                r5.startCommissioningSession(r1)
                r8.writeNoException()
                return r2
            L_0x0138:
                r7.enforceInterface(r0)
                r5.reset()
                r8.writeNoException()
                return r2
            L_0x0142:
                r7.enforceInterface(r0)
                r5.leave()
                r8.writeNoException()
                return r2
            L_0x014c:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x015f
                android.os.Parcelable$Creator<android.net.lowpan.LowpanProvision> r1 = android.net.lowpan.LowpanProvision.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.lowpan.LowpanProvision r3 = (android.net.lowpan.LowpanProvision) r3
                goto L_0x0160
            L_0x015f:
            L_0x0160:
                r1 = r3
                r5.attach(r1)
                r8.writeNoException()
                return r2
            L_0x0168:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x017b
                android.os.Parcelable$Creator<android.net.lowpan.LowpanProvision> r1 = android.net.lowpan.LowpanProvision.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.lowpan.LowpanProvision r3 = (android.net.lowpan.LowpanProvision) r3
                goto L_0x017c
            L_0x017b:
            L_0x017c:
                r1 = r3
                r5.form(r1)
                r8.writeNoException()
                return r2
            L_0x0184:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0197
                android.os.Parcelable$Creator<android.net.lowpan.LowpanProvision> r1 = android.net.lowpan.LowpanProvision.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.lowpan.LowpanProvision r3 = (android.net.lowpan.LowpanProvision) r3
                goto L_0x0198
            L_0x0197:
            L_0x0198:
                r1 = r3
                r5.join(r1)
                r8.writeNoException()
                return r2
            L_0x01a0:
                r7.enforceInterface(r0)
                android.net.IpPrefix[] r1 = r5.getLinkNetworks()
                r8.writeNoException()
                r8.writeTypedArray(r1, r2)
                return r2
            L_0x01ae:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r5.getLinkAddresses()
                r8.writeNoException()
                r8.writeStringArray(r1)
                return r2
            L_0x01bc:
                r7.enforceInterface(r0)
                android.net.lowpan.LowpanCredential r3 = r5.getLowpanCredential()
                r8.writeNoException()
                if (r3 == 0) goto L_0x01cf
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x01d2
            L_0x01cf:
                r8.writeInt(r1)
            L_0x01d2:
                return r2
            L_0x01d3:
                r7.enforceInterface(r0)
                android.net.lowpan.LowpanIdentity r3 = r5.getLowpanIdentity()
                r8.writeNoException()
                if (r3 == 0) goto L_0x01e6
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x01e9
            L_0x01e6:
                r8.writeInt(r1)
            L_0x01e9:
                return r2
            L_0x01ea:
                r7.enforceInterface(r0)
                byte[] r1 = r5.getExtendedAddress()
                r8.writeNoException()
                r8.writeByteArray(r1)
                return r2
            L_0x01f8:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getPartitionId()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x0206:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getRole()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x0214:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getState()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x0222:
                r7.enforceInterface(r0)
                boolean r1 = r5.isConnected()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0230:
                r7.enforceInterface(r0)
                boolean r1 = r5.isCommissioned()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x023e:
                r7.enforceInterface(r0)
                boolean r1 = r5.isUp()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x024c:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0257
                r1 = r2
            L_0x0257:
                r5.setEnabled(r1)
                r8.writeNoException()
                return r2
            L_0x025e:
                r7.enforceInterface(r0)
                boolean r1 = r5.isEnabled()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x026c:
                r7.enforceInterface(r0)
                byte[] r1 = r5.getMacAddress()
                r8.writeNoException()
                r8.writeByteArray(r1)
                return r2
            L_0x027a:
                r7.enforceInterface(r0)
                java.lang.String[] r1 = r5.getSupportedNetworkTypes()
                r8.writeNoException()
                r8.writeStringArray(r1)
                return r2
            L_0x0288:
                r7.enforceInterface(r0)
                android.net.lowpan.LowpanChannelInfo[] r1 = r5.getSupportedChannels()
                r8.writeNoException()
                r8.writeTypedArray(r1, r2)
                return r2
            L_0x0296:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getDriverVersion()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x02a4:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getNcpVersion()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x02b2:
                r7.enforceInterface(r0)
                java.lang.String r1 = r5.getName()
                r8.writeNoException()
                r8.writeString(r1)
                return r2
            L_0x02c0:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.lowpan.ILowpanInterface.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILowpanInterface {
            public static ILowpanInterface sDefaultImpl;
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

            public String getName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getName();
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

            public String getNcpVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNcpVersion();
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

            public String getDriverVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDriverVersion();
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

            public LowpanChannelInfo[] getSupportedChannels() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedChannels();
                    }
                    _reply.readException();
                    LowpanChannelInfo[] _result = (LowpanChannelInfo[]) _reply.createTypedArray(LowpanChannelInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getSupportedNetworkTypes() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedNetworkTypes();
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

            public byte[] getMacAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMacAddress();
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

            public boolean isEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEnabled();
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

            public void setEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUp();
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

            public boolean isCommissioned() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCommissioned();
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

            public boolean isConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConnected();
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

            public String getState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getState();
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

            public String getRole() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRole();
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

            public String getPartitionId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPartitionId();
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

            public byte[] getExtendedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getExtendedAddress();
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

            public LowpanIdentity getLowpanIdentity() throws RemoteException {
                LowpanIdentity _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLowpanIdentity();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LowpanIdentity.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LowpanIdentity _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public LowpanCredential getLowpanCredential() throws RemoteException {
                LowpanCredential _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLowpanCredential();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = LowpanCredential.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    LowpanCredential _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getLinkAddresses() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkAddresses();
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

            public IpPrefix[] getLinkNetworks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLinkNetworks();
                    }
                    _reply.readException();
                    IpPrefix[] _result = (IpPrefix[]) _reply.createTypedArray(IpPrefix.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void join(LowpanProvision provision) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provision != null) {
                        _data.writeInt(1);
                        provision.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().join(provision);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void form(LowpanProvision provision) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provision != null) {
                        _data.writeInt(1);
                        provision.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().form(provision);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void attach(LowpanProvision provision) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provision != null) {
                        _data.writeInt(1);
                        provision.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().attach(provision);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void leave() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().leave();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reset();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startCommissioningSession(LowpanBeaconInfo beaconInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (beaconInfo != null) {
                        _data.writeInt(1);
                        beaconInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startCommissioningSession(beaconInfo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeCommissioningSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeCommissioningSession();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendToCommissioner(byte[] packet) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(packet);
                    if (this.mRemote.transact(27, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendToCommissioner(packet);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void beginLowPower() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().beginLowPower();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pollForData() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(29, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().pollForData();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onHostWake() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(30, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onHostWake();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addListener(ILowpanInterfaceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeListener(ILowpanInterfaceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(32, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeListener(listener);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startNetScan(Map properties, ILowpanNetScanCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(properties);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startNetScan(properties, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopNetScan() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopNetScan();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startEnergyScan(Map properties, ILowpanEnergyScanCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(properties);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startEnergyScan(properties, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopEnergyScan() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopEnergyScan();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addOnMeshPrefix(IpPrefix prefix, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (prefix != null) {
                        _data.writeInt(1);
                        prefix.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnMeshPrefix(prefix, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnMeshPrefix(IpPrefix prefix) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (prefix != null) {
                        _data.writeInt(1);
                        prefix.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(38, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeOnMeshPrefix(prefix);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addExternalRoute(IpPrefix prefix, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (prefix != null) {
                        _data.writeInt(1);
                        prefix.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addExternalRoute(prefix, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeExternalRoute(IpPrefix prefix) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (prefix != null) {
                        _data.writeInt(1);
                        prefix.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(40, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeExternalRoute(prefix);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILowpanInterface impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILowpanInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
