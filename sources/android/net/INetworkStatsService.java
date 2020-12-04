package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.INetworkStatsSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.net.VpnInfo;

public interface INetworkStatsService extends IInterface {
    @UnsupportedAppUsage
    void forceUpdate() throws RemoteException;

    void forceUpdateIfaces(Network[] networkArr, VpnInfo[] vpnInfoArr, NetworkState[] networkStateArr, String str) throws RemoteException;

    @UnsupportedAppUsage
    NetworkStats getDataLayerSnapshotForUid(int i) throws RemoteException;

    NetworkStats getDetailedUidStats(String[] strArr) throws RemoteException;

    long getIfaceStats(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    String[] getMobileIfaces() throws RemoteException;

    long getTotalStats(int i) throws RemoteException;

    long getUidStats(int i, int i2) throws RemoteException;

    void incrementOperationCount(int i, int i2, int i3) throws RemoteException;

    @UnsupportedAppUsage
    INetworkStatsSession openSession() throws RemoteException;

    @UnsupportedAppUsage
    INetworkStatsSession openSessionForUsageStats(int i, String str) throws RemoteException;

    DataUsageRequest registerUsageCallback(String str, DataUsageRequest dataUsageRequest, Messenger messenger, IBinder iBinder) throws RemoteException;

    void unregisterUsageRequest(DataUsageRequest dataUsageRequest) throws RemoteException;

    public static class Default implements INetworkStatsService {
        public INetworkStatsSession openSession() throws RemoteException {
            return null;
        }

        public INetworkStatsSession openSessionForUsageStats(int flags, String callingPackage) throws RemoteException {
            return null;
        }

        public NetworkStats getDataLayerSnapshotForUid(int uid) throws RemoteException {
            return null;
        }

        public NetworkStats getDetailedUidStats(String[] requiredIfaces) throws RemoteException {
            return null;
        }

        public String[] getMobileIfaces() throws RemoteException {
            return null;
        }

        public void incrementOperationCount(int uid, int tag, int operationCount) throws RemoteException {
        }

        public void forceUpdateIfaces(Network[] defaultNetworks, VpnInfo[] vpnArray, NetworkState[] networkStates, String activeIface) throws RemoteException {
        }

        public void forceUpdate() throws RemoteException {
        }

        public DataUsageRequest registerUsageCallback(String callingPackage, DataUsageRequest request, Messenger messenger, IBinder binder) throws RemoteException {
            return null;
        }

        public void unregisterUsageRequest(DataUsageRequest request) throws RemoteException {
        }

        public long getUidStats(int uid, int type) throws RemoteException {
            return 0;
        }

        public long getIfaceStats(String iface, int type) throws RemoteException {
            return 0;
        }

        public long getTotalStats(int type) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkStatsService {
        private static final String DESCRIPTOR = "android.net.INetworkStatsService";
        static final int TRANSACTION_forceUpdate = 8;
        static final int TRANSACTION_forceUpdateIfaces = 7;
        static final int TRANSACTION_getDataLayerSnapshotForUid = 3;
        static final int TRANSACTION_getDetailedUidStats = 4;
        static final int TRANSACTION_getIfaceStats = 12;
        static final int TRANSACTION_getMobileIfaces = 5;
        static final int TRANSACTION_getTotalStats = 13;
        static final int TRANSACTION_getUidStats = 11;
        static final int TRANSACTION_incrementOperationCount = 6;
        static final int TRANSACTION_openSession = 1;
        static final int TRANSACTION_openSessionForUsageStats = 2;
        static final int TRANSACTION_registerUsageCallback = 9;
        static final int TRANSACTION_unregisterUsageRequest = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkStatsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkStatsService)) {
                return new Proxy(obj);
            }
            return (INetworkStatsService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "openSession";
                case 2:
                    return "openSessionForUsageStats";
                case 3:
                    return "getDataLayerSnapshotForUid";
                case 4:
                    return "getDetailedUidStats";
                case 5:
                    return "getMobileIfaces";
                case 6:
                    return "incrementOperationCount";
                case 7:
                    return "forceUpdateIfaces";
                case 8:
                    return "forceUpdate";
                case 9:
                    return "registerUsageCallback";
                case 10:
                    return "unregisterUsageRequest";
                case 11:
                    return "getUidStats";
                case 12:
                    return "getIfaceStats";
                case 13:
                    return "getTotalStats";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: android.os.Messenger} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v3, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v15 */
        /* JADX WARNING: type inference failed for: r3v21 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: type inference failed for: r3v24 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r9, android.os.Parcel r10, android.os.Parcel r11, int r12) throws android.os.RemoteException {
            /*
                r8 = this;
                java.lang.String r0 = "android.net.INetworkStatsService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r9 == r1) goto L_0x0167
                r1 = 0
                r3 = 0
                switch(r9) {
                    case 1: goto L_0x0152;
                    case 2: goto L_0x0135;
                    case 3: goto L_0x011a;
                    case 4: goto L_0x00ff;
                    case 5: goto L_0x00f1;
                    case 6: goto L_0x00db;
                    case 7: goto L_0x00b5;
                    case 8: goto L_0x00ab;
                    case 9: goto L_0x006c;
                    case 10: goto L_0x0050;
                    case 11: goto L_0x003a;
                    case 12: goto L_0x0024;
                    case 13: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r9, r10, r11, r12)
                return r1
            L_0x0012:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                long r3 = r8.getTotalStats(r1)
                r11.writeNoException()
                r11.writeLong(r3)
                return r2
            L_0x0024:
                r10.enforceInterface(r0)
                java.lang.String r1 = r10.readString()
                int r3 = r10.readInt()
                long r4 = r8.getIfaceStats(r1, r3)
                r11.writeNoException()
                r11.writeLong(r4)
                return r2
            L_0x003a:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r3 = r10.readInt()
                long r4 = r8.getUidStats(r1, r3)
                r11.writeNoException()
                r11.writeLong(r4)
                return r2
            L_0x0050:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                if (r1 == 0) goto L_0x0063
                android.os.Parcelable$Creator<android.net.DataUsageRequest> r1 = android.net.DataUsageRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r3 = r1
                android.net.DataUsageRequest r3 = (android.net.DataUsageRequest) r3
                goto L_0x0064
            L_0x0063:
            L_0x0064:
                r1 = r3
                r8.unregisterUsageRequest(r1)
                r11.writeNoException()
                return r2
            L_0x006c:
                r10.enforceInterface(r0)
                java.lang.String r4 = r10.readString()
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x0082
                android.os.Parcelable$Creator<android.net.DataUsageRequest> r5 = android.net.DataUsageRequest.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r10)
                android.net.DataUsageRequest r5 = (android.net.DataUsageRequest) r5
                goto L_0x0083
            L_0x0082:
                r5 = r3
            L_0x0083:
                int r6 = r10.readInt()
                if (r6 == 0) goto L_0x0092
                android.os.Parcelable$Creator<android.os.Messenger> r3 = android.os.Messenger.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.os.Messenger r3 = (android.os.Messenger) r3
                goto L_0x0093
            L_0x0092:
            L_0x0093:
                android.os.IBinder r6 = r10.readStrongBinder()
                android.net.DataUsageRequest r7 = r8.registerUsageCallback(r4, r5, r3, r6)
                r11.writeNoException()
                if (r7 == 0) goto L_0x00a7
                r11.writeInt(r2)
                r7.writeToParcel(r11, r2)
                goto L_0x00aa
            L_0x00a7:
                r11.writeInt(r1)
            L_0x00aa:
                return r2
            L_0x00ab:
                r10.enforceInterface(r0)
                r8.forceUpdate()
                r11.writeNoException()
                return r2
            L_0x00b5:
                r10.enforceInterface(r0)
                android.os.Parcelable$Creator<android.net.Network> r1 = android.net.Network.CREATOR
                java.lang.Object[] r1 = r10.createTypedArray(r1)
                android.net.Network[] r1 = (android.net.Network[]) r1
                android.os.Parcelable$Creator<com.android.internal.net.VpnInfo> r3 = com.android.internal.net.VpnInfo.CREATOR
                java.lang.Object[] r3 = r10.createTypedArray(r3)
                com.android.internal.net.VpnInfo[] r3 = (com.android.internal.net.VpnInfo[]) r3
                android.os.Parcelable$Creator<android.net.NetworkState> r4 = android.net.NetworkState.CREATOR
                java.lang.Object[] r4 = r10.createTypedArray(r4)
                android.net.NetworkState[] r4 = (android.net.NetworkState[]) r4
                java.lang.String r5 = r10.readString()
                r8.forceUpdateIfaces(r1, r3, r4, r5)
                r11.writeNoException()
                return r2
            L_0x00db:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r3 = r10.readInt()
                int r4 = r10.readInt()
                r8.incrementOperationCount(r1, r3, r4)
                r11.writeNoException()
                return r2
            L_0x00f1:
                r10.enforceInterface(r0)
                java.lang.String[] r1 = r8.getMobileIfaces()
                r11.writeNoException()
                r11.writeStringArray(r1)
                return r2
            L_0x00ff:
                r10.enforceInterface(r0)
                java.lang.String[] r3 = r10.createStringArray()
                android.net.NetworkStats r4 = r8.getDetailedUidStats(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x0116
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x0119
            L_0x0116:
                r11.writeInt(r1)
            L_0x0119:
                return r2
            L_0x011a:
                r10.enforceInterface(r0)
                int r3 = r10.readInt()
                android.net.NetworkStats r4 = r8.getDataLayerSnapshotForUid(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x0131
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x0134
            L_0x0131:
                r11.writeInt(r1)
            L_0x0134:
                return r2
            L_0x0135:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                java.lang.String r4 = r10.readString()
                android.net.INetworkStatsSession r5 = r8.openSessionForUsageStats(r1, r4)
                r11.writeNoException()
                if (r5 == 0) goto L_0x014e
                android.os.IBinder r3 = r5.asBinder()
            L_0x014e:
                r11.writeStrongBinder(r3)
                return r2
            L_0x0152:
                r10.enforceInterface(r0)
                android.net.INetworkStatsSession r1 = r8.openSession()
                r11.writeNoException()
                if (r1 == 0) goto L_0x0163
                android.os.IBinder r3 = r1.asBinder()
            L_0x0163:
                r11.writeStrongBinder(r3)
                return r2
            L_0x0167:
                r11.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.INetworkStatsService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INetworkStatsService {
            public static INetworkStatsService sDefaultImpl;
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

            public INetworkStatsSession openSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openSession();
                    }
                    _reply.readException();
                    INetworkStatsSession _result = INetworkStatsSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INetworkStatsSession openSessionForUsageStats(int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openSessionForUsageStats(flags, callingPackage);
                    }
                    _reply.readException();
                    INetworkStatsSession _result = INetworkStatsSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getDataLayerSnapshotForUid(int uid) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataLayerSnapshotForUid(uid);
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

            public NetworkStats getDetailedUidStats(String[] requiredIfaces) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(requiredIfaces);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDetailedUidStats(requiredIfaces);
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

            public String[] getMobileIfaces() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMobileIfaces();
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

            public void incrementOperationCount(int uid, int tag, int operationCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(tag);
                    _data.writeInt(operationCount);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().incrementOperationCount(uid, tag, operationCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceUpdateIfaces(Network[] defaultNetworks, VpnInfo[] vpnArray, NetworkState[] networkStates, String activeIface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(defaultNetworks, 0);
                    _data.writeTypedArray(vpnArray, 0);
                    _data.writeTypedArray(networkStates, 0);
                    _data.writeString(activeIface);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceUpdateIfaces(defaultNetworks, vpnArray, networkStates, activeIface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceUpdate();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DataUsageRequest registerUsageCallback(String callingPackage, DataUsageRequest request, Messenger messenger, IBinder binder) throws RemoteException {
                DataUsageRequest _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
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
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerUsageCallback(callingPackage, request, messenger, binder);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DataUsageRequest.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    DataUsageRequest _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterUsageRequest(DataUsageRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterUsageRequest(request);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getUidStats(int uid, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(type);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidStats(uid, type);
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

            public long getIfaceStats(String iface, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(type);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIfaceStats(iface, type);
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

            public long getTotalStats(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTotalStats(type);
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
        }

        public static boolean setDefaultImpl(INetworkStatsService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkStatsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
