package android.net.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISoftApCallback extends IInterface {
    void onNumClientsChanged(int i) throws RemoteException;

    void onStaConnected(String str, int i) throws RemoteException;

    void onStaDisconnected(String str, int i) throws RemoteException;

    void onStateChanged(int i, int i2) throws RemoteException;

    public static class Default implements ISoftApCallback {
        public void onStateChanged(int state, int failureReason) throws RemoteException {
        }

        public void onNumClientsChanged(int numClients) throws RemoteException {
        }

        public void onStaConnected(String Macaddr, int numClients) throws RemoteException {
        }

        public void onStaDisconnected(String Macaddr, int numClients) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISoftApCallback {
        private static final String DESCRIPTOR = "android.net.wifi.ISoftApCallback";
        static final int TRANSACTION_onNumClientsChanged = 2;
        static final int TRANSACTION_onStaConnected = 3;
        static final int TRANSACTION_onStaDisconnected = 4;
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoftApCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISoftApCallback)) {
                return new Proxy(obj);
            }
            return (ISoftApCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onStateChanged";
                case 2:
                    return "onNumClientsChanged";
                case 3:
                    return "onStaConnected";
                case 4:
                    return "onStaDisconnected";
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
                        onStateChanged(data.readInt(), data.readInt());
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onNumClientsChanged(data.readInt());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onStaConnected(data.readString(), data.readInt());
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        onStaDisconnected(data.readString(), data.readInt());
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ISoftApCallback {
            public static ISoftApCallback sDefaultImpl;
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

            public void onStateChanged(int state, int failureReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(failureReason);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStateChanged(state, failureReason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNumClientsChanged(int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numClients);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNumClientsChanged(numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStaConnected(String Macaddr, int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(Macaddr);
                    _data.writeInt(numClients);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStaConnected(Macaddr, numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStaDisconnected(String Macaddr, int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(Macaddr);
                    _data.writeInt(numClients);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStaDisconnected(Macaddr, numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISoftApCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISoftApCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
