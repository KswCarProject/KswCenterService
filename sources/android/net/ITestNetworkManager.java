package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITestNetworkManager extends IInterface {
    TestNetworkInterface createTapInterface() throws RemoteException;

    TestNetworkInterface createTunInterface(LinkAddress[] linkAddressArr) throws RemoteException;

    void setupTestNetwork(String str, IBinder iBinder) throws RemoteException;

    void teardownTestNetwork(int i) throws RemoteException;

    public static class Default implements ITestNetworkManager {
        public TestNetworkInterface createTunInterface(LinkAddress[] linkAddrs) throws RemoteException {
            return null;
        }

        public TestNetworkInterface createTapInterface() throws RemoteException {
            return null;
        }

        public void setupTestNetwork(String iface, IBinder binder) throws RemoteException {
        }

        public void teardownTestNetwork(int netId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITestNetworkManager {
        private static final String DESCRIPTOR = "android.net.ITestNetworkManager";
        static final int TRANSACTION_createTapInterface = 2;
        static final int TRANSACTION_createTunInterface = 1;
        static final int TRANSACTION_setupTestNetwork = 3;
        static final int TRANSACTION_teardownTestNetwork = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITestNetworkManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITestNetworkManager)) {
                return new Proxy(obj);
            }
            return (ITestNetworkManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createTunInterface";
                case 2:
                    return "createTapInterface";
                case 3:
                    return "setupTestNetwork";
                case 4:
                    return "teardownTestNetwork";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        TestNetworkInterface _result = createTunInterface((LinkAddress[]) data.createTypedArray(LinkAddress.CREATOR));
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
                        TestNetworkInterface _result2 = createTapInterface();
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
                        setupTestNetwork(data.readString(), data.readStrongBinder());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        teardownTestNetwork(data.readInt());
                        reply.writeNoException();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ITestNetworkManager {
            public static ITestNetworkManager sDefaultImpl;
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

            public TestNetworkInterface createTunInterface(LinkAddress[] linkAddrs) throws RemoteException {
                TestNetworkInterface _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(linkAddrs, 0);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createTunInterface(linkAddrs);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TestNetworkInterface.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    TestNetworkInterface _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public TestNetworkInterface createTapInterface() throws RemoteException {
                TestNetworkInterface _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createTapInterface();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TestNetworkInterface.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    TestNetworkInterface _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setupTestNetwork(String iface, IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeStrongBinder(binder);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setupTestNetwork(iface, binder);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void teardownTestNetwork(int netId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().teardownTestNetwork(netId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITestNetworkManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITestNetworkManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
