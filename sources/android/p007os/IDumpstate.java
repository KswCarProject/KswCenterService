package android.p007os;

import android.p007os.IDumpstateListener;
import android.p007os.IDumpstateToken;
import java.io.FileDescriptor;

/* renamed from: android.os.IDumpstate */
/* loaded from: classes3.dex */
public interface IDumpstate extends IInterface {
    public static final int BUGREPORT_MODE_DEFAULT = 6;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;

    void cancelBugreport() throws RemoteException;

    IDumpstateToken setListener(String str, IDumpstateListener iDumpstateListener, boolean z) throws RemoteException;

    void startBugreport(int i, String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int i2, IDumpstateListener iDumpstateListener) throws RemoteException;

    /* renamed from: android.os.IDumpstate$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IDumpstate {
        @Override // android.p007os.IDumpstate
        public IDumpstateToken setListener(String name, IDumpstateListener listener, boolean getSectionDetails) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IDumpstate
        public void startBugreport(int callingUid, String callingPackage, FileDescriptor bugreportFd, FileDescriptor screenshotFd, int bugreportMode, IDumpstateListener listener) throws RemoteException {
        }

        @Override // android.p007os.IDumpstate
        public void cancelBugreport() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IDumpstate$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IDumpstate {
        private static final String DESCRIPTOR = "android.os.IDumpstate";
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_startBugreport = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDumpstate asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDumpstate)) {
                return (IDumpstate) iin;
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
                    return "setListener";
                case 2:
                    return "startBugreport";
                case 3:
                    return "cancelBugreport";
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
                    String _arg0 = data.readString();
                    IDumpstateListener _arg1 = IDumpstateListener.Stub.asInterface(data.readStrongBinder());
                    boolean _arg2 = data.readInt() != 0;
                    IDumpstateToken _result = setListener(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    FileDescriptor _arg22 = data.readRawFileDescriptor();
                    FileDescriptor _arg3 = data.readRawFileDescriptor();
                    int _arg4 = data.readInt();
                    IDumpstateListener _arg5 = IDumpstateListener.Stub.asInterface(data.readStrongBinder());
                    startBugreport(_arg02, _arg12, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    cancelBugreport();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IDumpstate$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IDumpstate {
            public static IDumpstate sDefaultImpl;
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

            @Override // android.p007os.IDumpstate
            public IDumpstateToken setListener(String name, IDumpstateListener listener, boolean getSectionDetails) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(getSectionDetails ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setListener(name, listener, getSectionDetails);
                    }
                    _reply.readException();
                    IDumpstateToken _result = IDumpstateToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IDumpstate
            public void startBugreport(int callingUid, String callingPackage, FileDescriptor bugreportFd, FileDescriptor screenshotFd, int bugreportMode, IDumpstateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(callingUid);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeRawFileDescriptor(bugreportFd);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeRawFileDescriptor(screenshotFd);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(bugreportMode);
                        _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                        boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startBugreport(callingUid, callingPackage, bugreportFd, screenshotFd, bugreportMode, listener);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.p007os.IDumpstate
            public void cancelBugreport() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelBugreport();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDumpstate impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IDumpstate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
