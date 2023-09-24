package android.net.wifi.aware;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IWifiAwareEventCallback extends IInterface {
    void onConnectFail(int i) throws RemoteException;

    void onConnectSuccess(int i) throws RemoteException;

    void onIdentityChanged(byte[] bArr) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IWifiAwareEventCallback {
        @Override // android.net.wifi.aware.IWifiAwareEventCallback
        public void onConnectSuccess(int clientId) throws RemoteException {
        }

        @Override // android.net.wifi.aware.IWifiAwareEventCallback
        public void onConnectFail(int reason) throws RemoteException {
        }

        @Override // android.net.wifi.aware.IWifiAwareEventCallback
        public void onIdentityChanged(byte[] mac) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IWifiAwareEventCallback {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareEventCallback";
        static final int TRANSACTION_onConnectFail = 2;
        static final int TRANSACTION_onConnectSuccess = 1;
        static final int TRANSACTION_onIdentityChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWifiAwareEventCallback)) {
                return (IWifiAwareEventCallback) iin;
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
                    return "onConnectSuccess";
                case 2:
                    return "onConnectFail";
                case 3:
                    return "onIdentityChanged";
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
                    onConnectSuccess(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onConnectFail(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg03 = data.createByteArray();
                    onIdentityChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IWifiAwareEventCallback {
            public static IWifiAwareEventCallback sDefaultImpl;
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

            @Override // android.net.wifi.aware.IWifiAwareEventCallback
            public void onConnectSuccess(int clientId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectSuccess(clientId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.aware.IWifiAwareEventCallback
            public void onConnectFail(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectFail(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.aware.IWifiAwareEventCallback
            public void onIdentityChanged(byte[] mac) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(mac);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onIdentityChanged(mac);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWifiAwareEventCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IWifiAwareEventCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
