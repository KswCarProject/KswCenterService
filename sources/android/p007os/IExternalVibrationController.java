package android.p007os;

/* renamed from: android.os.IExternalVibrationController */
/* loaded from: classes3.dex */
public interface IExternalVibrationController extends IInterface {
    boolean mute() throws RemoteException;

    boolean unmute() throws RemoteException;

    /* renamed from: android.os.IExternalVibrationController$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IExternalVibrationController {
        @Override // android.p007os.IExternalVibrationController
        public boolean mute() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IExternalVibrationController
        public boolean unmute() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IExternalVibrationController$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IExternalVibrationController {
        private static final String DESCRIPTOR = "android.os.IExternalVibrationController";
        static final int TRANSACTION_mute = 1;
        static final int TRANSACTION_unmute = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IExternalVibrationController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IExternalVibrationController)) {
                return (IExternalVibrationController) iin;
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
                    return "mute";
                case 2:
                    return "unmute";
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
                    boolean mute = mute();
                    reply.writeNoException();
                    reply.writeInt(mute ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    boolean unmute = unmute();
                    reply.writeNoException();
                    reply.writeInt(unmute ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IExternalVibrationController$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IExternalVibrationController {
            public static IExternalVibrationController sDefaultImpl;
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

            @Override // android.p007os.IExternalVibrationController
            public boolean mute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().mute();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IExternalVibrationController
            public boolean unmute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unmute();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IExternalVibrationController impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IExternalVibrationController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
