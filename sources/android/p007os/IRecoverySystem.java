package android.p007os;

import android.p007os.IRecoverySystemProgressListener;

/* renamed from: android.os.IRecoverySystem */
/* loaded from: classes3.dex */
public interface IRecoverySystem extends IInterface {
    boolean clearBcb() throws RemoteException;

    void rebootRecoveryWithCommand(String str) throws RemoteException;

    boolean setupBcb(String str) throws RemoteException;

    boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException;

    /* renamed from: android.os.IRecoverySystem$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IRecoverySystem {
        @Override // android.p007os.IRecoverySystem
        public boolean uncrypt(String packageFile, IRecoverySystemProgressListener listener) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IRecoverySystem
        public boolean setupBcb(String command) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IRecoverySystem
        public boolean clearBcb() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IRecoverySystem
        public void rebootRecoveryWithCommand(String command) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IRecoverySystem$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IRecoverySystem {
        private static final String DESCRIPTOR = "android.os.IRecoverySystem";
        static final int TRANSACTION_clearBcb = 3;
        static final int TRANSACTION_rebootRecoveryWithCommand = 4;
        static final int TRANSACTION_setupBcb = 2;
        static final int TRANSACTION_uncrypt = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecoverySystem asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecoverySystem)) {
                return (IRecoverySystem) iin;
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
                    return "uncrypt";
                case 2:
                    return "setupBcb";
                case 3:
                    return "clearBcb";
                case 4:
                    return "rebootRecoveryWithCommand";
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
                    IRecoverySystemProgressListener _arg1 = IRecoverySystemProgressListener.Stub.asInterface(data.readStrongBinder());
                    boolean uncrypt = uncrypt(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(uncrypt ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    boolean z = setupBcb(_arg02);
                    reply.writeNoException();
                    reply.writeInt(z ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    boolean clearBcb = clearBcb();
                    reply.writeNoException();
                    reply.writeInt(clearBcb ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    rebootRecoveryWithCommand(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IRecoverySystem$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IRecoverySystem {
            public static IRecoverySystem sDefaultImpl;
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

            @Override // android.p007os.IRecoverySystem
            public boolean uncrypt(String packageFile, IRecoverySystemProgressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageFile);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().uncrypt(packageFile, listener);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IRecoverySystem
            public boolean setupBcb(String command) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setupBcb(command);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IRecoverySystem
            public boolean clearBcb() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearBcb();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IRecoverySystem
            public void rebootRecoveryWithCommand(String command) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rebootRecoveryWithCommand(command);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRecoverySystem impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRecoverySystem getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}