package android.service.watchdog;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IExplicitHealthCheckService extends IInterface {
    void cancel(String str) throws RemoteException;

    void getRequestedPackages(RemoteCallback remoteCallback) throws RemoteException;

    void getSupportedPackages(RemoteCallback remoteCallback) throws RemoteException;

    void request(String str) throws RemoteException;

    void setCallback(RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IExplicitHealthCheckService {
        public void setCallback(RemoteCallback callback) throws RemoteException {
        }

        public void request(String packageName) throws RemoteException {
        }

        public void cancel(String packageName) throws RemoteException {
        }

        public void getSupportedPackages(RemoteCallback callback) throws RemoteException {
        }

        public void getRequestedPackages(RemoteCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IExplicitHealthCheckService {
        private static final String DESCRIPTOR = "android.service.watchdog.IExplicitHealthCheckService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_getRequestedPackages = 5;
        static final int TRANSACTION_getSupportedPackages = 4;
        static final int TRANSACTION_request = 2;
        static final int TRANSACTION_setCallback = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IExplicitHealthCheckService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IExplicitHealthCheckService)) {
                return new Proxy(obj);
            }
            return (IExplicitHealthCheckService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setCallback";
                case 2:
                    return "request";
                case 3:
                    return "cancel";
                case 4:
                    return "getSupportedPackages";
                case 5:
                    return "getRequestedPackages";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                RemoteCallback _arg0 = null;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        setCallback(_arg0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        request(data.readString());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        cancel(data.readString());
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        getSupportedPackages(_arg0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        getRequestedPackages(_arg0);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IExplicitHealthCheckService {
            public static IExplicitHealthCheckService sDefaultImpl;
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

            public void setCallback(RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void request(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().request(packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancel(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancel(packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getSupportedPackages(RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getSupportedPackages(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getRequestedPackages(RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getRequestedPackages(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IExplicitHealthCheckService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IExplicitHealthCheckService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
