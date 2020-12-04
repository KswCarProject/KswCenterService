package android.app.role;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IRoleController extends IInterface {
    void grantDefaultRoles(RemoteCallback remoteCallback) throws RemoteException;

    void isApplicationQualifiedForRole(String str, String str2, RemoteCallback remoteCallback) throws RemoteException;

    void isRoleVisible(String str, RemoteCallback remoteCallback) throws RemoteException;

    void onAddRoleHolder(String str, String str2, int i, RemoteCallback remoteCallback) throws RemoteException;

    void onClearRoleHolders(String str, int i, RemoteCallback remoteCallback) throws RemoteException;

    void onRemoveRoleHolder(String str, String str2, int i, RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IRoleController {
        public void grantDefaultRoles(RemoteCallback callback) throws RemoteException {
        }

        public void onAddRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void onRemoveRoleHolder(String roleName, String packageName, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void onClearRoleHolders(String roleName, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void isApplicationQualifiedForRole(String roleName, String packageName, RemoteCallback callback) throws RemoteException {
        }

        public void isRoleVisible(String roleName, RemoteCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IRoleController)) {
                return new Proxy(obj);
            }
            return (IRoleController) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                RemoteCallback _arg1 = null;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        grantDefaultRoles(_arg1);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        String _arg12 = data.readString();
                        int _arg2 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        onAddRoleHolder(_arg0, _arg12, _arg2, _arg1);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        String _arg13 = data.readString();
                        int _arg22 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        onRemoveRoleHolder(_arg02, _arg13, _arg22, _arg1);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        int _arg14 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        onClearRoleHolders(_arg03, _arg14, _arg1);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        String _arg15 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        isApplicationQualifiedForRole(_arg04, _arg15, _arg1);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = RemoteCallback.CREATOR.createFromParcel(data);
                        }
                        isRoleVisible(_arg05, _arg1);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IRoleController {
            public static IRoleController sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().grantDefaultRoles(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAddRoleHolder(roleName, packageName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRemoveRoleHolder(roleName, packageName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onClearRoleHolders(roleName, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isApplicationQualifiedForRole(roleName, packageName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isRoleVisible(roleName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRoleController impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IRoleController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
