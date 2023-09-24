package android.app.role;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteCallback;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IRoleController extends IInterface {
    void grantDefaultRoles(RemoteCallback remoteCallback) throws RemoteException;

    void isApplicationQualifiedForRole(String str, String str2, RemoteCallback remoteCallback) throws RemoteException;

    void isRoleVisible(String str, RemoteCallback remoteCallback) throws RemoteException;

    void onAddRoleHolder(String str, String str2, int i, RemoteCallback remoteCallback) throws RemoteException;

    void onClearRoleHolders(String str, int i, RemoteCallback remoteCallback) throws RemoteException;

    void onRemoveRoleHolder(String str, String str2, int i, RemoteCallback remoteCallback) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IRoleController {
        @Override // android.app.role.IRoleController
        public void grantDefaultRoles(RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.role.IRoleController
        public void onAddRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.role.IRoleController
        public void onRemoveRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.role.IRoleController
        public void onClearRoleHolders(String roleName, int flags, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.role.IRoleController
        public void isApplicationQualifiedForRole(String roleName, String packageName, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.role.IRoleController
        public void isRoleVisible(String roleName, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IRoleController {
        private static final String DESCRIPTOR = "android.app.role.IRoleController";
        static final int TRANSACTION_grantDefaultRoles = 1;
        static final int TRANSACTION_isApplicationQualifiedForRole = 5;
        static final int TRANSACTION_isRoleVisible = 6;
        static final int TRANSACTION_onAddRoleHolder = 2;
        static final int TRANSACTION_onClearRoleHolders = 4;
        static final int TRANSACTION_onRemoveRoleHolder = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRoleController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRoleController)) {
                return (IRoleController) iin;
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
                    return "grantDefaultRoles";
                case 2:
                    return "onAddRoleHolder";
                case 3:
                    return "onRemoveRoleHolder";
                case 4:
                    return "onClearRoleHolders";
                case 5:
                    return "isApplicationQualifiedForRole";
                case 6:
                    return "isRoleVisible";
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
            RemoteCallback _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    grantDefaultRoles(_arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    onAddRoleHolder(_arg0, _arg12, _arg2, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    String _arg13 = data.readString();
                    int _arg22 = data.readInt();
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    onRemoveRoleHolder(_arg02, _arg13, _arg22, _arg1);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    int _arg14 = data.readInt();
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    onClearRoleHolders(_arg03, _arg14, _arg1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    String _arg15 = data.readString();
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    isApplicationQualifiedForRole(_arg04, _arg15, _arg1);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    _arg1 = data.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(data) : null;
                    isRoleVisible(_arg05, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IRoleController {
            public static IRoleController sDefaultImpl;
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

            @Override // android.app.role.IRoleController
            public void grantDefaultRoles(RemoteCallback callback) throws RemoteException {
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
                        Stub.getDefaultImpl().grantDefaultRoles(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.role.IRoleController
            public void onAddRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddRoleHolder(roleName, packageName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.role.IRoleController
            public void onRemoveRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRemoveRoleHolder(roleName, packageName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.role.IRoleController
            public void onClearRoleHolders(String roleName, int flags, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeInt(flags);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClearRoleHolders(roleName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.role.IRoleController
            public void isApplicationQualifiedForRole(String roleName, String packageName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isApplicationQualifiedForRole(roleName, packageName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.role.IRoleController
            public void isRoleVisible(String roleName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isRoleVisible(roleName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRoleController impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRoleController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
