package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.INetworkServiceCallback;

public interface INetworkService extends IInterface {
    void createNetworkServiceProvider(int i) throws RemoteException;

    void registerForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    void removeNetworkServiceProvider(int i) throws RemoteException;

    void requestNetworkRegistrationInfo(int i, int i2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    void unregisterForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException;

    public static class Default implements INetworkService {
        public void createNetworkServiceProvider(int slotId) throws RemoteException {
        }

        public void removeNetworkServiceProvider(int slotId) throws RemoteException {
        }

        public void requestNetworkRegistrationInfo(int slotId, int domain, INetworkServiceCallback callback) throws RemoteException {
        }

        public void registerForNetworkRegistrationInfoChanged(int slotId, INetworkServiceCallback callback) throws RemoteException {
        }

        public void unregisterForNetworkRegistrationInfoChanged(int slotId, INetworkServiceCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkService {
        private static final String DESCRIPTOR = "android.telephony.INetworkService";
        static final int TRANSACTION_createNetworkServiceProvider = 1;
        static final int TRANSACTION_registerForNetworkRegistrationInfoChanged = 4;
        static final int TRANSACTION_removeNetworkServiceProvider = 2;
        static final int TRANSACTION_requestNetworkRegistrationInfo = 3;
        static final int TRANSACTION_unregisterForNetworkRegistrationInfoChanged = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkService)) {
                return new Proxy(obj);
            }
            return (INetworkService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createNetworkServiceProvider";
                case 2:
                    return "removeNetworkServiceProvider";
                case 3:
                    return "requestNetworkRegistrationInfo";
                case 4:
                    return "registerForNetworkRegistrationInfoChanged";
                case 5:
                    return "unregisterForNetworkRegistrationInfoChanged";
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
                        createNetworkServiceProvider(data.readInt());
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        removeNetworkServiceProvider(data.readInt());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        requestNetworkRegistrationInfo(data.readInt(), data.readInt(), INetworkServiceCallback.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        registerForNetworkRegistrationInfoChanged(data.readInt(), INetworkServiceCallback.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        unregisterForNetworkRegistrationInfoChanged(data.readInt(), INetworkServiceCallback.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements INetworkService {
            public static INetworkService sDefaultImpl;
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

            public void createNetworkServiceProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createNetworkServiceProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeNetworkServiceProvider(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeNetworkServiceProvider(slotId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestNetworkRegistrationInfo(int slotId, int domain, INetworkServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(domain);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestNetworkRegistrationInfo(slotId, domain, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void registerForNetworkRegistrationInfoChanged(int slotId, INetworkServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().registerForNetworkRegistrationInfoChanged(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unregisterForNetworkRegistrationInfoChanged(int slotId, INetworkServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterForNetworkRegistrationInfoChanged(slotId, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INetworkService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
