package android.p007os;

/* renamed from: android.os.IMaintenanceActivityListener */
/* loaded from: classes3.dex */
public interface IMaintenanceActivityListener extends IInterface {
    void onMaintenanceActivityChanged(boolean z) throws RemoteException;

    /* renamed from: android.os.IMaintenanceActivityListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IMaintenanceActivityListener {
        @Override // android.p007os.IMaintenanceActivityListener
        public void onMaintenanceActivityChanged(boolean active) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IMaintenanceActivityListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMaintenanceActivityListener {
        private static final String DESCRIPTOR = "android.os.IMaintenanceActivityListener";
        static final int TRANSACTION_onMaintenanceActivityChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMaintenanceActivityListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMaintenanceActivityListener)) {
                return (IMaintenanceActivityListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onMaintenanceActivityChanged";
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
            boolean _arg0 = data.readInt() != 0;
            onMaintenanceActivityChanged(_arg0);
            return true;
        }

        /* renamed from: android.os.IMaintenanceActivityListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IMaintenanceActivityListener {
            public static IMaintenanceActivityListener sDefaultImpl;
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

            @Override // android.p007os.IMaintenanceActivityListener
            public void onMaintenanceActivityChanged(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMaintenanceActivityChanged(active);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMaintenanceActivityListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMaintenanceActivityListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}