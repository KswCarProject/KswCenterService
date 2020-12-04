package android.net.lowpan;

import android.net.IpPrefix;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanInterfaceListener extends IInterface {
    void onConnectedChanged(boolean z) throws RemoteException;

    void onEnabledChanged(boolean z) throws RemoteException;

    void onLinkAddressAdded(String str) throws RemoteException;

    void onLinkAddressRemoved(String str) throws RemoteException;

    void onLinkNetworkAdded(IpPrefix ipPrefix) throws RemoteException;

    void onLinkNetworkRemoved(IpPrefix ipPrefix) throws RemoteException;

    void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) throws RemoteException;

    void onReceiveFromCommissioner(byte[] bArr) throws RemoteException;

    void onRoleChanged(String str) throws RemoteException;

    void onStateChanged(String str) throws RemoteException;

    void onUpChanged(boolean z) throws RemoteException;

    public static class Default implements ILowpanInterfaceListener {
        public void onEnabledChanged(boolean value) throws RemoteException {
        }

        public void onConnectedChanged(boolean value) throws RemoteException {
        }

        public void onUpChanged(boolean value) throws RemoteException {
        }

        public void onRoleChanged(String value) throws RemoteException {
        }

        public void onStateChanged(String value) throws RemoteException {
        }

        public void onLowpanIdentityChanged(LowpanIdentity value) throws RemoteException {
        }

        public void onLinkNetworkAdded(IpPrefix value) throws RemoteException {
        }

        public void onLinkNetworkRemoved(IpPrefix value) throws RemoteException {
        }

        public void onLinkAddressAdded(String value) throws RemoteException {
        }

        public void onLinkAddressRemoved(String value) throws RemoteException {
        }

        public void onReceiveFromCommissioner(byte[] packet) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILowpanInterfaceListener {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanInterfaceListener";
        static final int TRANSACTION_onConnectedChanged = 2;
        static final int TRANSACTION_onEnabledChanged = 1;
        static final int TRANSACTION_onLinkAddressAdded = 9;
        static final int TRANSACTION_onLinkAddressRemoved = 10;
        static final int TRANSACTION_onLinkNetworkAdded = 7;
        static final int TRANSACTION_onLinkNetworkRemoved = 8;
        static final int TRANSACTION_onLowpanIdentityChanged = 6;
        static final int TRANSACTION_onReceiveFromCommissioner = 11;
        static final int TRANSACTION_onRoleChanged = 4;
        static final int TRANSACTION_onStateChanged = 5;
        static final int TRANSACTION_onUpChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanInterfaceListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILowpanInterfaceListener)) {
                return new Proxy(obj);
            }
            return (ILowpanInterfaceListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onEnabledChanged";
                case 2:
                    return "onConnectedChanged";
                case 3:
                    return "onUpChanged";
                case 4:
                    return "onRoleChanged";
                case 5:
                    return "onStateChanged";
                case 6:
                    return "onLowpanIdentityChanged";
                case 7:
                    return "onLinkNetworkAdded";
                case 8:
                    return "onLinkNetworkRemoved";
                case 9:
                    return "onLinkAddressAdded";
                case 10:
                    return "onLinkAddressRemoved";
                case 11:
                    return "onReceiveFromCommissioner";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.net.lowpan.LowpanIdentity} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.net.IpPrefix} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.net.IpPrefix} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v26 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.net.lowpan.ILowpanInterfaceListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x00be
                r1 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x00ae;
                    case 2: goto L_0x009e;
                    case 3: goto L_0x008e;
                    case 4: goto L_0x0083;
                    case 5: goto L_0x0078;
                    case 6: goto L_0x0061;
                    case 7: goto L_0x004a;
                    case 8: goto L_0x0033;
                    case 9: goto L_0x0028;
                    case 10: goto L_0x001d;
                    case 11: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0012:
                r6.enforceInterface(r0)
                byte[] r1 = r6.createByteArray()
                r4.onReceiveFromCommissioner(r1)
                return r2
            L_0x001d:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                r4.onLinkAddressRemoved(r1)
                return r2
            L_0x0028:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                r4.onLinkAddressAdded(r1)
                return r2
            L_0x0033:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0045
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.net.IpPrefix r1 = (android.net.IpPrefix) r1
                goto L_0x0046
            L_0x0045:
            L_0x0046:
                r4.onLinkNetworkRemoved(r1)
                return r2
            L_0x004a:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x005c
                android.os.Parcelable$Creator<android.net.IpPrefix> r1 = android.net.IpPrefix.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.net.IpPrefix r1 = (android.net.IpPrefix) r1
                goto L_0x005d
            L_0x005c:
            L_0x005d:
                r4.onLinkNetworkAdded(r1)
                return r2
            L_0x0061:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0073
                android.os.Parcelable$Creator<android.net.lowpan.LowpanIdentity> r1 = android.net.lowpan.LowpanIdentity.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.net.lowpan.LowpanIdentity r1 = (android.net.lowpan.LowpanIdentity) r1
                goto L_0x0074
            L_0x0073:
            L_0x0074:
                r4.onLowpanIdentityChanged(r1)
                return r2
            L_0x0078:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                r4.onStateChanged(r1)
                return r2
            L_0x0083:
                r6.enforceInterface(r0)
                java.lang.String r1 = r6.readString()
                r4.onRoleChanged(r1)
                return r2
            L_0x008e:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0099
                r3 = r2
            L_0x0099:
                r1 = r3
                r4.onUpChanged(r1)
                return r2
            L_0x009e:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00a9
                r3 = r2
            L_0x00a9:
                r1 = r3
                r4.onConnectedChanged(r1)
                return r2
            L_0x00ae:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00b9
                r3 = r2
            L_0x00b9:
                r1 = r3
                r4.onEnabledChanged(r1)
                return r2
            L_0x00be:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.lowpan.ILowpanInterfaceListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILowpanInterfaceListener {
            public static ILowpanInterfaceListener sDefaultImpl;
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

            public void onEnabledChanged(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onEnabledChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectedChanged(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectedChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onUpChanged(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUpChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRoleChanged(String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRoleChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStateChanged(String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStateChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLowpanIdentityChanged(LowpanIdentity value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLowpanIdentityChanged(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLinkNetworkAdded(IpPrefix value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLinkNetworkAdded(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLinkNetworkRemoved(IpPrefix value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLinkNetworkRemoved(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLinkAddressAdded(String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLinkAddressAdded(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLinkAddressRemoved(String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLinkAddressRemoved(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onReceiveFromCommissioner(byte[] packet) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(packet);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onReceiveFromCommissioner(packet);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILowpanInterfaceListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILowpanInterfaceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
