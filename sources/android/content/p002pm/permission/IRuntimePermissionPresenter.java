package android.content.p002pm.permission;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteCallback;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.permission.IRuntimePermissionPresenter */
/* loaded from: classes.dex */
public interface IRuntimePermissionPresenter extends IInterface {
    void getAppPermissions(String str, RemoteCallback remoteCallback) throws RemoteException;

    /* renamed from: android.content.pm.permission.IRuntimePermissionPresenter$Default */
    /* loaded from: classes.dex */
    public static class Default implements IRuntimePermissionPresenter {
        @Override // android.content.p002pm.permission.IRuntimePermissionPresenter
        public void getAppPermissions(String packageName, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.permission.IRuntimePermissionPresenter$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IRuntimePermissionPresenter {
        private static final String DESCRIPTOR = "android.content.pm.permission.IRuntimePermissionPresenter";
        static final int TRANSACTION_getAppPermissions = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRuntimePermissionPresenter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRuntimePermissionPresenter)) {
                return (IRuntimePermissionPresenter) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "getAppPermissions";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            RemoteCallback _arg1;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            String _arg0 = data.readString();
            if (data.readInt() != 0) {
                _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
            } else {
                _arg1 = null;
            }
            getAppPermissions(_arg0, _arg1);
            return true;
        }

        /* renamed from: android.content.pm.permission.IRuntimePermissionPresenter$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IRuntimePermissionPresenter {
            public static IRuntimePermissionPresenter sDefaultImpl;
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

            @Override // android.content.p002pm.permission.IRuntimePermissionPresenter
            public void getAppPermissions(String packageName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAppPermissions(packageName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRuntimePermissionPresenter impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRuntimePermissionPresenter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
