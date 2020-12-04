package android.os;

public interface IIncidentAuthListener extends IInterface {
    void onReportApproved() throws RemoteException;

    void onReportDenied() throws RemoteException;

    public static class Default implements IIncidentAuthListener {
        public void onReportApproved() throws RemoteException {
        }

        public void onReportDenied() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IIncidentAuthListener)) {
                return new Proxy(obj);
            }
            return (IIncidentAuthListener) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
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
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IIncidentAuthListener {
            public static IIncidentAuthListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void onReportApproved() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onReportApproved();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onReportDenied() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onReportDenied();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IIncidentAuthListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IIncidentAuthListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
