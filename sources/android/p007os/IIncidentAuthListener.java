package android.p007os;

/* renamed from: android.os.IIncidentAuthListener */
/* loaded from: classes3.dex */
public interface IIncidentAuthListener extends IInterface {
    void onReportApproved() throws RemoteException;

    void onReportDenied() throws RemoteException;

    /* renamed from: android.os.IIncidentAuthListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IIncidentAuthListener {
        @Override // android.p007os.IIncidentAuthListener
        public void onReportApproved() throws RemoteException {
        }

        @Override // android.p007os.IIncidentAuthListener
        public void onReportDenied() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IIncidentAuthListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIncidentAuthListener {
        private static final String DESCRIPTOR = "android.os.IIncidentAuthListener";
        static final int TRANSACTION_onReportApproved = 1;
        static final int TRANSACTION_onReportDenied = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentAuthListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IIncidentAuthListener)) {
                return (IIncidentAuthListener) iin;
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
                    return "onReportApproved";
                case 2:
                    return "onReportDenied";
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
                    onReportApproved();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onReportDenied();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IIncidentAuthListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IIncidentAuthListener {
            public static IIncidentAuthListener sDefaultImpl;
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

            @Override // android.p007os.IIncidentAuthListener
            public void onReportApproved() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportApproved();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IIncidentAuthListener
            public void onReportDenied() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportDenied();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IIncidentAuthListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IIncidentAuthListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
