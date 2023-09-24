package android.p007os;

/* renamed from: android.os.IThermalStatusListener */
/* loaded from: classes3.dex */
public interface IThermalStatusListener extends IInterface {
    void onStatusChange(int i) throws RemoteException;

    /* renamed from: android.os.IThermalStatusListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IThermalStatusListener {
        @Override // android.p007os.IThermalStatusListener
        public void onStatusChange(int status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IThermalStatusListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IThermalStatusListener {
        private static final String DESCRIPTOR = "android.os.IThermalStatusListener";
        static final int TRANSACTION_onStatusChange = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IThermalStatusListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IThermalStatusListener)) {
                return (IThermalStatusListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onStatusChange";
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
            onStatusChange(_arg0);
            return true;
        }

        /* renamed from: android.os.IThermalStatusListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IThermalStatusListener {
            public static IThermalStatusListener sDefaultImpl;
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

            @Override // android.p007os.IThermalStatusListener
            public void onStatusChange(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStatusChange(status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IThermalStatusListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IThermalStatusListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
