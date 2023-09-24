package android.net.wifi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ISoftApCallback extends IInterface {
    void onNumClientsChanged(int i) throws RemoteException;

    void onStaConnected(String str, int i) throws RemoteException;

    void onStaDisconnected(String str, int i) throws RemoteException;

    void onStateChanged(int i, int i2) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISoftApCallback {
        @Override // android.net.wifi.ISoftApCallback
        public void onStateChanged(int state, int failureReason) throws RemoteException {
        }

        @Override // android.net.wifi.ISoftApCallback
        public void onNumClientsChanged(int numClients) throws RemoteException {
        }

        @Override // android.net.wifi.ISoftApCallback
        public void onStaConnected(String Macaddr, int numClients) throws RemoteException {
        }

        @Override // android.net.wifi.ISoftApCallback
        public void onStaDisconnected(String Macaddr, int numClients) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof ISoftApCallback)) {
                return (ISoftApCallback) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    onStateChanged(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onNumClientsChanged(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    int _arg12 = data.readInt();
                    onStaConnected(_arg03, _arg12);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    int _arg13 = data.readInt();
                    onStaDisconnected(_arg04, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISoftApCallback {
            public static ISoftApCallback sDefaultImpl;
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

            @Override // android.net.wifi.ISoftApCallback
            public void onStateChanged(int state, int failureReason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(failureReason);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(state, failureReason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.ISoftApCallback
            public void onNumClientsChanged(int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numClients);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNumClientsChanged(numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.ISoftApCallback
            public void onStaConnected(String Macaddr, int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(Macaddr);
                    _data.writeInt(numClients);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStaConnected(Macaddr, numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.ISoftApCallback
            public void onStaDisconnected(String Macaddr, int numClients) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(Macaddr);
                    _data.writeInt(numClients);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStaDisconnected(Macaddr, numClients);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISoftApCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISoftApCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
