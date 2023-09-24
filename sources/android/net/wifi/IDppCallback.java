package android.net.wifi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IDppCallback extends IInterface {
    void onFailure(int i) throws RemoteException;

    void onProgress(int i) throws RemoteException;

    void onSuccess(int i) throws RemoteException;

    void onSuccessConfigReceived(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IDppCallback {
        @Override // android.net.wifi.IDppCallback
        public void onSuccessConfigReceived(int newNetworkId) throws RemoteException {
        }

        @Override // android.net.wifi.IDppCallback
        public void onSuccess(int status) throws RemoteException {
        }

        @Override // android.net.wifi.IDppCallback
        public void onFailure(int status) throws RemoteException {
        }

        @Override // android.net.wifi.IDppCallback
        public void onProgress(int status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDppCallback {
        private static final String DESCRIPTOR = "android.net.wifi.IDppCallback";
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onProgress = 4;
        static final int TRANSACTION_onSuccess = 2;
        static final int TRANSACTION_onSuccessConfigReceived = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDppCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDppCallback)) {
                return (IDppCallback) iin;
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
                    return "onSuccessConfigReceived";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
                case 4:
                    return "onProgress";
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
                    onSuccessConfigReceived(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onSuccess(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    onFailure(_arg03);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    onProgress(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IDppCallback {
            public static IDppCallback sDefaultImpl;
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

            @Override // android.net.wifi.IDppCallback
            public void onSuccessConfigReceived(int newNetworkId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newNetworkId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccessConfigReceived(newNetworkId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IDppCallback
            public void onSuccess(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IDppCallback
            public void onFailure(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailure(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IDppCallback
            public void onProgress(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgress(status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDppCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IDppCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
