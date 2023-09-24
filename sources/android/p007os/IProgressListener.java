package android.p007os;

/* renamed from: android.os.IProgressListener */
/* loaded from: classes3.dex */
public interface IProgressListener extends IInterface {
    void onFinished(int i, Bundle bundle) throws RemoteException;

    void onProgress(int i, int i2, Bundle bundle) throws RemoteException;

    void onStarted(int i, Bundle bundle) throws RemoteException;

    /* renamed from: android.os.IProgressListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IProgressListener {
        @Override // android.p007os.IProgressListener
        public void onStarted(int id, Bundle extras) throws RemoteException {
        }

        @Override // android.p007os.IProgressListener
        public void onProgress(int id, int progress, Bundle extras) throws RemoteException {
        }

        @Override // android.p007os.IProgressListener
        public void onFinished(int id, Bundle extras) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IProgressListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IProgressListener {
        private static final String DESCRIPTOR = "android.os.IProgressListener";
        static final int TRANSACTION_onFinished = 3;
        static final int TRANSACTION_onProgress = 2;
        static final int TRANSACTION_onStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IProgressListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IProgressListener)) {
                return (IProgressListener) iin;
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
                    return "onStarted";
                case 2:
                    return "onProgress";
                case 3:
                    return "onFinished";
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
            Bundle _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    _arg1 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onStarted(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    _arg1 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onProgress(_arg02, _arg12, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    _arg1 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onFinished(_arg03, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IProgressListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IProgressListener {
            public static IProgressListener sDefaultImpl;
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

            @Override // android.p007os.IProgressListener
            public void onStarted(int id, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStarted(id, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IProgressListener
            public void onProgress(int id, int progress, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(progress);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgress(id, progress, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IProgressListener
            public void onFinished(int id, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(id, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IProgressListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
