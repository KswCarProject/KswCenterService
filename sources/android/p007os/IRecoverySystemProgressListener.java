package android.p007os;

/* renamed from: android.os.IRecoverySystemProgressListener */
/* loaded from: classes3.dex */
public interface IRecoverySystemProgressListener extends IInterface {
    void onProgress(int i) throws RemoteException;

    /* renamed from: android.os.IRecoverySystemProgressListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IRecoverySystemProgressListener {
        @Override // android.p007os.IRecoverySystemProgressListener
        public void onProgress(int progress) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IRecoverySystemProgressListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IRecoverySystemProgressListener {
        private static final String DESCRIPTOR = "android.os.IRecoverySystemProgressListener";
        static final int TRANSACTION_onProgress = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecoverySystemProgressListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecoverySystemProgressListener)) {
                return (IRecoverySystemProgressListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onProgress";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            onProgress(_arg0);
            return true;
        }

        /* renamed from: android.os.IRecoverySystemProgressListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IRecoverySystemProgressListener {
            public static IRecoverySystemProgressListener sDefaultImpl;
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

            @Override // android.p007os.IRecoverySystemProgressListener
            public void onProgress(int progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(progress);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgress(progress);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRecoverySystemProgressListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRecoverySystemProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}