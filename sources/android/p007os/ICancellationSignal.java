package android.p007os;

/* renamed from: android.os.ICancellationSignal */
/* loaded from: classes3.dex */
public interface ICancellationSignal extends IInterface {
    void cancel() throws RemoteException;

    /* renamed from: android.os.ICancellationSignal$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ICancellationSignal {
        @Override // android.p007os.ICancellationSignal
        public void cancel() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.ICancellationSignal$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ICancellationSignal {
        private static final String DESCRIPTOR = "android.os.ICancellationSignal";
        static final int TRANSACTION_cancel = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICancellationSignal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICancellationSignal)) {
                return (ICancellationSignal) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "cancel";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                cancel();
                return true;
            } else if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            } else {
                return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.ICancellationSignal$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ICancellationSignal {
            public static ICancellationSignal sDefaultImpl;
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

            @Override // android.p007os.ICancellationSignal
            public void cancel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICancellationSignal impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICancellationSignal getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
