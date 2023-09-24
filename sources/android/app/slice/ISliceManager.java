package android.app.slice;

import android.net.Uri;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface ISliceManager extends IInterface {
    void applyRestore(byte[] bArr, int i) throws RemoteException;

    int checkSlicePermission(Uri uri, String str, String str2, int i, int i2, String[] strArr) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    Uri[] getPinnedSlices(String str) throws RemoteException;

    SliceSpec[] getPinnedSpecs(Uri uri, String str) throws RemoteException;

    void grantPermissionFromUser(Uri uri, String str, String str2, boolean z) throws RemoteException;

    void grantSlicePermission(String str, String str2, Uri uri) throws RemoteException;

    boolean hasSliceAccess(String str) throws RemoteException;

    void pinSlice(String str, Uri uri, SliceSpec[] sliceSpecArr, IBinder iBinder) throws RemoteException;

    void revokeSlicePermission(String str, String str2, Uri uri) throws RemoteException;

    void unpinSlice(String str, Uri uri, IBinder iBinder) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements ISliceManager {
        @Override // android.app.slice.ISliceManager
        public void pinSlice(String pkg, Uri uri, SliceSpec[] specs, IBinder token) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void unpinSlice(String pkg, Uri uri, IBinder token) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public boolean hasSliceAccess(String pkg) throws RemoteException {
            return false;
        }

        @Override // android.app.slice.ISliceManager
        public SliceSpec[] getPinnedSpecs(Uri uri, String pkg) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public Uri[] getPinnedSlices(String pkg) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public byte[] getBackupPayload(int user) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public void applyRestore(byte[] payload, int user) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void grantSlicePermission(String callingPkg, String toPkg, Uri uri) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void revokeSlicePermission(String callingPkg, String toPkg, Uri uri) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public int checkSlicePermission(Uri uri, String callingPkg, String pkg, int pid, int uid, String[] autoGrantPermissions) throws RemoteException {
            return 0;
        }

        @Override // android.app.slice.ISliceManager
        public void grantPermissionFromUser(Uri uri, String pkg, String callingPkg, boolean allSlices) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ISliceManager {
        private static final String DESCRIPTOR = "android.app.slice.ISliceManager";
        static final int TRANSACTION_applyRestore = 7;
        static final int TRANSACTION_checkSlicePermission = 10;
        static final int TRANSACTION_getBackupPayload = 6;
        static final int TRANSACTION_getPinnedSlices = 5;
        static final int TRANSACTION_getPinnedSpecs = 4;
        static final int TRANSACTION_grantPermissionFromUser = 11;
        static final int TRANSACTION_grantSlicePermission = 8;
        static final int TRANSACTION_hasSliceAccess = 3;
        static final int TRANSACTION_pinSlice = 1;
        static final int TRANSACTION_revokeSlicePermission = 9;
        static final int TRANSACTION_unpinSlice = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISliceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISliceManager)) {
                return (ISliceManager) iin;
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
                    return "pinSlice";
                case 2:
                    return "unpinSlice";
                case 3:
                    return "hasSliceAccess";
                case 4:
                    return "getPinnedSpecs";
                case 5:
                    return "getPinnedSlices";
                case 6:
                    return "getBackupPayload";
                case 7:
                    return "applyRestore";
                case 8:
                    return "grantSlicePermission";
                case 9:
                    return "revokeSlicePermission";
                case 10:
                    return "checkSlicePermission";
                case 11:
                    return "grantPermissionFromUser";
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
            Uri _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    SliceSpec[] _arg2 = (SliceSpec[]) data.createTypedArray(SliceSpec.CREATOR);
                    IBinder _arg3 = data.readStrongBinder();
                    pinSlice(_arg02, _arg0, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    IBinder _arg22 = data.readStrongBinder();
                    unpinSlice(_arg03, _arg0, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    boolean hasSliceAccess = hasSliceAccess(data.readString());
                    reply.writeNoException();
                    reply.writeInt(hasSliceAccess ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    String _arg1 = data.readString();
                    SliceSpec[] _result = getPinnedSpecs(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    Uri[] _result2 = getPinnedSlices(data.readString());
                    reply.writeNoException();
                    reply.writeTypedArray(_result2, 1);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result3 = getBackupPayload(data.readInt());
                    reply.writeNoException();
                    reply.writeByteArray(_result3);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg04 = data.createByteArray();
                    int _arg12 = data.readInt();
                    applyRestore(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    String _arg13 = data.readString();
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    grantSlicePermission(_arg05, _arg13, _arg0);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg06 = data.readString();
                    String _arg14 = data.readString();
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    revokeSlicePermission(_arg06, _arg14, _arg0);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    Uri _arg07 = _arg0;
                    String _arg15 = data.readString();
                    String _arg23 = data.readString();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    String[] _arg5 = data.createStringArray();
                    int _result4 = checkSlicePermission(_arg07, _arg15, _arg23, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    String _arg16 = data.readString();
                    String _arg24 = data.readString();
                    boolean _arg33 = data.readInt() != 0;
                    grantPermissionFromUser(_arg0, _arg16, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements ISliceManager {
            public static ISliceManager sDefaultImpl;
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

            @Override // android.app.slice.ISliceManager
            public void pinSlice(String pkg, Uri uri, SliceSpec[] specs, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedArray(specs, 0);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pinSlice(pkg, uri, specs, token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void unpinSlice(String pkg, Uri uri, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unpinSlice(pkg, uri, token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public boolean hasSliceAccess(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasSliceAccess(pkg);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public SliceSpec[] getPinnedSpecs(Uri uri, String pkg) throws RemoteException {
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
                    _data.writeString(pkg);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPinnedSpecs(uri, pkg);
                    }
                    _reply.readException();
                    SliceSpec[] _result = (SliceSpec[]) _reply.createTypedArray(SliceSpec.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public Uri[] getPinnedSlices(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPinnedSlices(pkg);
                    }
                    _reply.readException();
                    Uri[] _result = (Uri[]) _reply.createTypedArray(Uri.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public byte[] getBackupPayload(int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBackupPayload(user);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void applyRestore(byte[] payload, int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(payload);
                    _data.writeInt(user);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyRestore(payload, user);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantSlicePermission(String callingPkg, String toPkg, Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(toPkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantSlicePermission(callingPkg, toPkg, uri);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void revokeSlicePermission(String callingPkg, String toPkg, Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(toPkg);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeSlicePermission(callingPkg, toPkg, uri);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public int checkSlicePermission(Uri uri, String callingPkg, String pkg, int pid, int uid, String[] autoGrantPermissions) throws RemoteException {
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
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPkg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(pkg);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(pid);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(uid);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStringArray(autoGrantPermissions);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int checkSlicePermission = Stub.getDefaultImpl().checkSlicePermission(uri, callingPkg, pkg, pid, uid, autoGrantPermissions);
                        _reply.recycle();
                        _data.recycle();
                        return checkSlicePermission;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantPermissionFromUser(Uri uri, String pkg, String callingPkg, boolean allSlices) throws RemoteException {
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
                    _data.writeString(pkg);
                    _data.writeString(callingPkg);
                    _data.writeInt(allSlices ? 1 : 0);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantPermissionFromUser(uri, pkg, callingPkg, allSlices);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISliceManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISliceManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
