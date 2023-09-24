package android.p007os;

/* renamed from: android.os.IVoldTaskListener */
/* loaded from: classes3.dex */
public interface IVoldTaskListener extends IInterface {
    void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException;

    void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException;

    /* renamed from: android.os.IVoldTaskListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IVoldTaskListener {
        @Override // android.p007os.IVoldTaskListener
        public void onStatus(int status, PersistableBundle extras) throws RemoteException {
        }

        @Override // android.p007os.IVoldTaskListener
        public void onFinished(int status, PersistableBundle extras) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IVoldTaskListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IVoldTaskListener {
        private static final String DESCRIPTOR = "android.os.IVoldTaskListener";
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onStatus = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoldTaskListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVoldTaskListener)) {
                return (IVoldTaskListener) iin;
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
                    return "onStatus";
                case 2:
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
            PersistableBundle _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    _arg1 = data.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(data) : null;
                    onStatus(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    _arg1 = data.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(data) : null;
                    onFinished(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IVoldTaskListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IVoldTaskListener {
            public static IVoldTaskListener sDefaultImpl;
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

            @Override // android.p007os.IVoldTaskListener
            public void onStatus(int status, PersistableBundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatus(status, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IVoldTaskListener
            public void onFinished(int status, PersistableBundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(status, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVoldTaskListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IVoldTaskListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
