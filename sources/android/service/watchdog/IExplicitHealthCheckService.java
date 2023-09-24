package android.service.watchdog;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteCallback;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IExplicitHealthCheckService extends IInterface {
    void cancel(String str) throws RemoteException;

    void getRequestedPackages(RemoteCallback remoteCallback) throws RemoteException;

    void getSupportedPackages(RemoteCallback remoteCallback) throws RemoteException;

    void request(String str) throws RemoteException;

    void setCallback(RemoteCallback remoteCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IExplicitHealthCheckService {
        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void setCallback(RemoteCallback callback) throws RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void request(String packageName) throws RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void cancel(String packageName) throws RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getSupportedPackages(RemoteCallback callback) throws RemoteException {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getRequestedPackages(RemoteCallback callback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof IExplicitHealthCheckService)) {
                return (IExplicitHealthCheckService) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            RemoteCallback _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
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
                    _arg0 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    getSupportedPackages(_arg0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    getRequestedPackages(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IExplicitHealthCheckService {
            public static IExplicitHealthCheckService sDefaultImpl;
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

            @Override // android.service.watchdog.IExplicitHealthCheckService
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void request(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().request(packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
            public void cancel(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel(packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
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
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getSupportedPackages(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.watchdog.IExplicitHealthCheckService
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
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getRequestedPackages(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IExplicitHealthCheckService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IExplicitHealthCheckService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
