package android.print;

import android.content.ComponentName;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IPrintSpoolerClient extends IInterface {
    void onAllPrintJobsForServiceHandled(ComponentName componentName) throws RemoteException;

    void onAllPrintJobsHandled() throws RemoteException;

    void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException;

    void onPrintJobStateChanged(PrintJobInfo printJobInfo) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IPrintSpoolerClient {
        @Override // android.print.IPrintSpoolerClient
        public void onPrintJobQueued(PrintJobInfo printJob) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onAllPrintJobsForServiceHandled(ComponentName printService) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onAllPrintJobsHandled() throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onPrintJobStateChanged(PrintJobInfo printJob) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IPrintSpoolerClient {
        private static final String DESCRIPTOR = "android.print.IPrintSpoolerClient";
        static final int TRANSACTION_onAllPrintJobsForServiceHandled = 2;
        static final int TRANSACTION_onAllPrintJobsHandled = 3;
        static final int TRANSACTION_onPrintJobQueued = 1;
        static final int TRANSACTION_onPrintJobStateChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpoolerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPrintSpoolerClient)) {
                return (IPrintSpoolerClient) iin;
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
                    return "onPrintJobQueued";
                case 2:
                    return "onAllPrintJobsForServiceHandled";
                case 3:
                    return "onAllPrintJobsHandled";
                case 4:
                    return "onPrintJobStateChanged";
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
                    PrintJobInfo _arg0 = data.readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel(data) : null;
                    onPrintJobQueued(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg02 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    onAllPrintJobsForServiceHandled(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onAllPrintJobsHandled();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    PrintJobInfo _arg03 = data.readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel(data) : null;
                    onPrintJobStateChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IPrintSpoolerClient {
            public static IPrintSpoolerClient sDefaultImpl;
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

            @Override // android.print.IPrintSpoolerClient
            public void onPrintJobQueued(PrintJobInfo printJob) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJob != null) {
                        _data.writeInt(1);
                        printJob.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintJobQueued(printJob);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onAllPrintJobsForServiceHandled(ComponentName printService) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printService != null) {
                        _data.writeInt(1);
                        printService.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAllPrintJobsForServiceHandled(printService);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onAllPrintJobsHandled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAllPrintJobsHandled();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onPrintJobStateChanged(PrintJobInfo printJob) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJob != null) {
                        _data.writeInt(1);
                        printJob.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintJobStateChanged(printJob);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintSpoolerClient impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPrintSpoolerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
