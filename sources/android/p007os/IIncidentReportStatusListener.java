package android.p007os;

/* renamed from: android.os.IIncidentReportStatusListener */
/* loaded from: classes3.dex */
public interface IIncidentReportStatusListener extends IInterface {
    public static final int STATUS_FINISHED = 2;
    public static final int STATUS_STARTING = 1;

    void onReportFailed() throws RemoteException;

    void onReportFinished() throws RemoteException;

    void onReportSectionStatus(int i, int i2) throws RemoteException;

    void onReportStarted() throws RemoteException;

    /* renamed from: android.os.IIncidentReportStatusListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IIncidentReportStatusListener {
        @Override // android.p007os.IIncidentReportStatusListener
        public void onReportStarted() throws RemoteException {
        }

        @Override // android.p007os.IIncidentReportStatusListener
        public void onReportSectionStatus(int section, int status) throws RemoteException {
        }

        @Override // android.p007os.IIncidentReportStatusListener
        public void onReportFinished() throws RemoteException {
        }

        @Override // android.p007os.IIncidentReportStatusListener
        public void onReportFailed() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IIncidentReportStatusListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IIncidentReportStatusListener {
        private static final String DESCRIPTOR = "android.os.IIncidentReportStatusListener";
        static final int TRANSACTION_onReportFailed = 4;
        static final int TRANSACTION_onReportFinished = 3;
        static final int TRANSACTION_onReportSectionStatus = 2;
        static final int TRANSACTION_onReportStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentReportStatusListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IIncidentReportStatusListener)) {
                return (IIncidentReportStatusListener) iin;
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
                    return "onReportStarted";
                case 2:
                    return "onReportSectionStatus";
                case 3:
                    return "onReportFinished";
                case 4:
                    return "onReportFailed";
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
                    onReportStarted();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    onReportSectionStatus(_arg0, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onReportFinished();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onReportFailed();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IIncidentReportStatusListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IIncidentReportStatusListener {
            public static IIncidentReportStatusListener sDefaultImpl;
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

            @Override // android.p007os.IIncidentReportStatusListener
            public void onReportStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportStarted();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IIncidentReportStatusListener
            public void onReportSectionStatus(int section, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(section);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportSectionStatus(section, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IIncidentReportStatusListener
            public void onReportFinished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportFinished();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IIncidentReportStatusListener
            public void onReportFailed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportFailed();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IIncidentReportStatusListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IIncidentReportStatusListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}