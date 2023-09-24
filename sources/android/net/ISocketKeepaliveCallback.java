package android.net;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ISocketKeepaliveCallback extends IInterface {
    void onDataReceived() throws RemoteException;

    void onError(int i) throws RemoteException;

    void onStarted(int i) throws RemoteException;

    void onStopped() throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ISocketKeepaliveCallback {
        @Override // android.net.ISocketKeepaliveCallback
        public void onStarted(int slot) throws RemoteException {
        }

        @Override // android.net.ISocketKeepaliveCallback
        public void onStopped() throws RemoteException {
        }

        @Override // android.net.ISocketKeepaliveCallback
        public void onError(int error) throws RemoteException {
        }

        @Override // android.net.ISocketKeepaliveCallback
        public void onDataReceived() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISocketKeepaliveCallback {
        private static final String DESCRIPTOR = "android.net.ISocketKeepaliveCallback";
        static final int TRANSACTION_onDataReceived = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onStarted = 1;
        static final int TRANSACTION_onStopped = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISocketKeepaliveCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISocketKeepaliveCallback)) {
                return (ISocketKeepaliveCallback) iin;
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
                    return "onStarted";
                case 2:
                    return "onStopped";
                case 3:
                    return "onError";
                case 4:
                    return "onDataReceived";
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
                    onStarted(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onStopped();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onError(_arg02);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onDataReceived();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISocketKeepaliveCallback {
            public static ISocketKeepaliveCallback sDefaultImpl;
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

            @Override // android.net.ISocketKeepaliveCallback
            public void onStarted(int slot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slot);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStarted(slot);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onStopped() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStopped();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(error);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.ISocketKeepaliveCallback
            public void onDataReceived() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataReceived();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISocketKeepaliveCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISocketKeepaliveCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
