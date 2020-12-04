package android.net.wifi.hotspot2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProvisioningCallback extends IInterface {
    void onProvisioningComplete() throws RemoteException;

    void onProvisioningFailure(int i) throws RemoteException;

    void onProvisioningStatus(int i) throws RemoteException;

    public static class Default implements IProvisioningCallback {
        public void onProvisioningFailure(int status) throws RemoteException {
        }

        public void onProvisioningStatus(int status) throws RemoteException {
        }

        public void onProvisioningComplete() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProvisioningCallback {
        private static final String DESCRIPTOR = "android.net.wifi.hotspot2.IProvisioningCallback";
        static final int TRANSACTION_onProvisioningComplete = 3;
        static final int TRANSACTION_onProvisioningFailure = 1;
        static final int TRANSACTION_onProvisioningStatus = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IProvisioningCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IProvisioningCallback)) {
                return new Proxy(obj);
            }
            return (IProvisioningCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onProvisioningFailure";
                case 2:
                    return "onProvisioningStatus";
                case 3:
                    return "onProvisioningComplete";
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
                        onProvisioningFailure(data.readInt());
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onProvisioningStatus(data.readInt());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onProvisioningComplete();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IProvisioningCallback {
            public static IProvisioningCallback sDefaultImpl;
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

            public void onProvisioningFailure(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProvisioningFailure(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProvisioningStatus(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProvisioningStatus(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProvisioningComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProvisioningComplete();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IProvisioningCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IProvisioningCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
