package android.app;

import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUriGrantsManager extends IInterface {
    void clearGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) throws RemoteException;

    void grantUriPermissionFromOwner(IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4) throws RemoteException;

    void releasePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    void takePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    public static class Default implements IUriGrantsManager {
        public void takePersistableUriPermission(Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
        }

        public void releasePersistableUriPermission(Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
        }

        public void grantUriPermissionFromOwner(IBinder owner, int fromUid, String targetPkg, Uri uri, int mode, int sourceUserId, int targetUserId) throws RemoteException {
        }

        public ParceledListSlice getGrantedUriPermissions(String packageName, int userId) throws RemoteException {
            return null;
        }

        public void clearGrantedUriPermissions(String packageName, int userId) throws RemoteException {
        }

        public ParceledListSlice getUriPermissions(String packageName, boolean incoming, boolean persistedOnly) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUriGrantsManager {
        private static final String DESCRIPTOR = "android.app.IUriGrantsManager";
        static final int TRANSACTION_clearGrantedUriPermissions = 5;
        static final int TRANSACTION_getGrantedUriPermissions = 4;
        static final int TRANSACTION_getUriPermissions = 6;
        static final int TRANSACTION_grantUriPermissionFromOwner = 3;
        static final int TRANSACTION_releasePersistableUriPermission = 2;
        static final int TRANSACTION_takePersistableUriPermission = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUriGrantsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUriGrantsManager)) {
                return new Proxy(obj);
            }
            return (IUriGrantsManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "takePersistableUriPermission";
                case 2:
                    return "releasePersistableUriPermission";
                case 3:
                    return "grantUriPermissionFromOwner";
                case 4:
                    return "getGrantedUriPermissions";
                case 5:
                    return "clearGrantedUriPermissions";
                case 6:
                    return "getUriPermissions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            if (i != 1598968902) {
                Uri _arg0 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = Uri.CREATOR.createFromParcel(parcel);
                        }
                        takePersistableUriPermission(_arg0, data.readInt(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = Uri.CREATOR.createFromParcel(parcel);
                        }
                        releasePersistableUriPermission(_arg0, data.readInt(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        IBinder _arg02 = data.readStrongBinder();
                        int _arg1 = data.readInt();
                        String _arg2 = data.readString();
                        if (data.readInt() != 0) {
                            _arg0 = Uri.CREATOR.createFromParcel(parcel);
                        }
                        grantUriPermissionFromOwner(_arg02, _arg1, _arg2, _arg0, data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        ParceledListSlice _result = getGrantedUriPermissions(data.readString(), data.readInt());
                        reply.writeNoException();
                        if (_result != null) {
                            parcel2.writeInt(1);
                            _result.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        clearGrantedUriPermissions(data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        ParceledListSlice _result2 = getUriPermissions(data.readString(), data.readInt() != 0, data.readInt() != 0);
                        reply.writeNoException();
                        if (_result2 != null) {
                            parcel2.writeInt(1);
                            _result2.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IUriGrantsManager {
            public static IUriGrantsManager sDefaultImpl;
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

            public void takePersistableUriPermission(Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(modeFlags);
                    _data.writeString(toPackage);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().takePersistableUriPermission(uri, modeFlags, toPackage, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releasePersistableUriPermission(Uri uri, int modeFlags, String toPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(modeFlags);
                    _data.writeString(toPackage);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releasePersistableUriPermission(uri, modeFlags, toPackage, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantUriPermissionFromOwner(IBinder owner, int fromUid, String targetPkg, Uri uri, int mode, int sourceUserId, int targetUserId) throws RemoteException {
                Uri uri2 = uri;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(owner);
                        try {
                            _data.writeInt(fromUid);
                        } catch (Throwable th) {
                            th = th;
                            String str = targetPkg;
                            int i = mode;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(targetPkg);
                            if (uri2 != null) {
                                _data.writeInt(1);
                                uri2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(mode);
                                _data.writeInt(sourceUserId);
                                _data.writeInt(targetUserId);
                                if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().grantUriPermissionFromOwner(owner, fromUid, targetPkg, uri, mode, sourceUserId, targetUserId);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i2 = mode;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i3 = fromUid;
                        String str2 = targetPkg;
                        int i22 = mode;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    IBinder iBinder = owner;
                    int i32 = fromUid;
                    String str22 = targetPkg;
                    int i222 = mode;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public ParceledListSlice getGrantedUriPermissions(String packageName, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGrantedUriPermissions(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearGrantedUriPermissions(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearGrantedUriPermissions(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getUriPermissions(String packageName, boolean incoming, boolean persistedOnly) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(incoming);
                    _data.writeInt(persistedOnly);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUriPermissions(packageName, incoming, persistedOnly);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUriGrantsManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUriGrantsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
