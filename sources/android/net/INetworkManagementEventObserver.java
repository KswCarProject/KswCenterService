package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkManagementEventObserver extends IInterface {
    void addressRemoved(String str, LinkAddress linkAddress) throws RemoteException;

    void addressUpdated(String str, LinkAddress linkAddress) throws RemoteException;

    void interfaceAdded(String str) throws RemoteException;

    void interfaceClassDataActivityChanged(String str, boolean z, long j) throws RemoteException;

    void interfaceDnsServerInfo(String str, long j, String[] strArr) throws RemoteException;

    void interfaceLinkStateChanged(String str, boolean z) throws RemoteException;

    void interfaceRemoved(String str) throws RemoteException;

    void interfaceStatusChanged(String str, boolean z) throws RemoteException;

    void limitReached(String str, String str2) throws RemoteException;

    void routeRemoved(RouteInfo routeInfo) throws RemoteException;

    void routeUpdated(RouteInfo routeInfo) throws RemoteException;

    public static class Default implements INetworkManagementEventObserver {
        public void interfaceStatusChanged(String iface, boolean up) throws RemoteException {
        }

        public void interfaceLinkStateChanged(String iface, boolean up) throws RemoteException {
        }

        public void interfaceAdded(String iface) throws RemoteException {
        }

        public void interfaceRemoved(String iface) throws RemoteException {
        }

        public void addressUpdated(String iface, LinkAddress address) throws RemoteException {
        }

        public void addressRemoved(String iface, LinkAddress address) throws RemoteException {
        }

        public void limitReached(String limitName, String iface) throws RemoteException {
        }

        public void interfaceClassDataActivityChanged(String label, boolean active, long tsNanos) throws RemoteException {
        }

        public void interfaceDnsServerInfo(String iface, long lifetime, String[] servers) throws RemoteException {
        }

        public void routeUpdated(RouteInfo route) throws RemoteException {
        }

        public void routeRemoved(RouteInfo route) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkManagementEventObserver {
        private static final String DESCRIPTOR = "android.net.INetworkManagementEventObserver";
        static final int TRANSACTION_addressRemoved = 6;
        static final int TRANSACTION_addressUpdated = 5;
        static final int TRANSACTION_interfaceAdded = 3;
        static final int TRANSACTION_interfaceClassDataActivityChanged = 8;
        static final int TRANSACTION_interfaceDnsServerInfo = 9;
        static final int TRANSACTION_interfaceLinkStateChanged = 2;
        static final int TRANSACTION_interfaceRemoved = 4;
        static final int TRANSACTION_interfaceStatusChanged = 1;
        static final int TRANSACTION_limitReached = 7;
        static final int TRANSACTION_routeRemoved = 11;
        static final int TRANSACTION_routeUpdated = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkManagementEventObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkManagementEventObserver)) {
                return new Proxy(obj);
            }
            return (INetworkManagementEventObserver) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "interfaceStatusChanged";
                case 2:
                    return "interfaceLinkStateChanged";
                case 3:
                    return "interfaceAdded";
                case 4:
                    return "interfaceRemoved";
                case 5:
                    return "addressUpdated";
                case 6:
                    return "addressRemoved";
                case 7:
                    return "limitReached";
                case 8:
                    return "interfaceClassDataActivityChanged";
                case 9:
                    return "interfaceDnsServerInfo";
                case 10:
                    return "routeUpdated";
                case 11:
                    return "routeRemoved";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.net.RouteInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.net.RouteInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: android.net.LinkAddress} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: android.net.LinkAddress} */
        /* JADX WARNING: type inference failed for: r3v16, types: [android.net.RouteInfo] */
        /* JADX WARNING: type inference failed for: r3v19, types: [android.net.RouteInfo] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.net.INetworkManagementEventObserver"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00ef
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x00dc;
                    case 2: goto L_0x00c9;
                    case 3: goto L_0x00be;
                    case 4: goto L_0x00b3;
                    case 5: goto L_0x0098;
                    case 6: goto L_0x007d;
                    case 7: goto L_0x006e;
                    case 8: goto L_0x0057;
                    case 9: goto L_0x0044;
                    case 10: goto L_0x002b;
                    case 11: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0025
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.net.RouteInfo r3 = (android.net.RouteInfo) r3
                goto L_0x0026
            L_0x0025:
            L_0x0026:
                r1 = r3
                r6.routeRemoved(r1)
                return r2
            L_0x002b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.net.RouteInfo> r1 = android.net.RouteInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.net.RouteInfo r3 = (android.net.RouteInfo) r3
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                r1 = r3
                r6.routeUpdated(r1)
                return r2
            L_0x0044:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                long r3 = r8.readLong()
                java.lang.String[] r5 = r8.createStringArray()
                r6.interfaceDnsServerInfo(r1, r3, r5)
                return r2
            L_0x0057:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0066
                r1 = r2
            L_0x0066:
                long r4 = r8.readLong()
                r6.interfaceClassDataActivityChanged(r3, r1, r4)
                return r2
            L_0x006e:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                r6.limitReached(r1, r3)
                return r2
            L_0x007d:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0093
                android.os.Parcelable$Creator<android.net.LinkAddress> r3 = android.net.LinkAddress.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.net.LinkAddress r3 = (android.net.LinkAddress) r3
                goto L_0x0094
            L_0x0093:
            L_0x0094:
                r6.addressRemoved(r1, r3)
                return r2
            L_0x0098:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00ae
                android.os.Parcelable$Creator<android.net.LinkAddress> r3 = android.net.LinkAddress.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.net.LinkAddress r3 = (android.net.LinkAddress) r3
                goto L_0x00af
            L_0x00ae:
            L_0x00af:
                r6.addressUpdated(r1, r3)
                return r2
            L_0x00b3:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.interfaceRemoved(r1)
                return r2
            L_0x00be:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.interfaceAdded(r1)
                return r2
            L_0x00c9:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00d8
                r1 = r2
            L_0x00d8:
                r6.interfaceLinkStateChanged(r3, r1)
                return r2
            L_0x00dc:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00eb
                r1 = r2
            L_0x00eb:
                r6.interfaceStatusChanged(r3, r1)
                return r2
            L_0x00ef:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.INetworkManagementEventObserver.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INetworkManagementEventObserver {
            public static INetworkManagementEventObserver sDefaultImpl;
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

            public void interfaceStatusChanged(String iface, boolean up) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(up);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceStatusChanged(iface, up);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interfaceLinkStateChanged(String iface, boolean up) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(up);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceLinkStateChanged(iface, up);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interfaceAdded(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceAdded(iface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interfaceRemoved(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceRemoved(iface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addressUpdated(String iface, LinkAddress address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (address != null) {
                        _data.writeInt(1);
                        address.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addressUpdated(iface, address);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addressRemoved(String iface, LinkAddress address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    if (address != null) {
                        _data.writeInt(1);
                        address.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addressRemoved(iface, address);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void limitReached(String limitName, String iface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(limitName);
                    _data.writeString(iface);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().limitReached(limitName, iface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interfaceClassDataActivityChanged(String label, boolean active, long tsNanos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(label);
                    _data.writeInt(active);
                    _data.writeLong(tsNanos);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceClassDataActivityChanged(label, active, tsNanos);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interfaceDnsServerInfo(String iface, long lifetime, String[] servers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(lifetime);
                    _data.writeStringArray(servers);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interfaceDnsServerInfo(iface, lifetime, servers);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void routeUpdated(RouteInfo route) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (route != null) {
                        _data.writeInt(1);
                        route.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().routeUpdated(route);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void routeRemoved(RouteInfo route) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (route != null) {
                        _data.writeInt(1);
                        route.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().routeRemoved(route);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkManagementEventObserver impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkManagementEventObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
