package android.print;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPrintSpoolerClient extends IInterface {
    void onAllPrintJobsForServiceHandled(ComponentName componentName) throws RemoteException;

    void onAllPrintJobsHandled() throws RemoteException;

    void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException;

    void onPrintJobStateChanged(PrintJobInfo printJobInfo) throws RemoteException;

    public static class Default implements IPrintSpoolerClient {
        public void onPrintJobQueued(PrintJobInfo printJob) throws RemoteException {
        }

        public void onAllPrintJobsForServiceHandled(ComponentName printService) throws RemoteException {
        }

        public void onAllPrintJobsHandled() throws RemoteException {
        }

        public void onPrintJobStateChanged(PrintJobInfo printJob) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IPrintSpoolerClient)) {
                return new Proxy(obj);
            }
            return (IPrintSpoolerClient) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.print.PrintJobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.print.PrintJobInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.print.IPrintSpoolerClient"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x005d
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x0046;
                    case 2: goto L_0x002f;
                    case 3: goto L_0x0028;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrintJobInfo r1 = (android.print.PrintJobInfo) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r4.onPrintJobStateChanged(r1)
                return r2
            L_0x0028:
                r6.enforceInterface(r0)
                r4.onAllPrintJobsHandled()
                return r2
            L_0x002f:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0041
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0042
            L_0x0041:
            L_0x0042:
                r4.onAllPrintJobsForServiceHandled(r1)
                return r2
            L_0x0046:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0058
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrintJobInfo r1 = (android.print.PrintJobInfo) r1
                goto L_0x0059
            L_0x0058:
            L_0x0059:
                r4.onPrintJobQueued(r1)
                return r2
            L_0x005d:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.IPrintSpoolerClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintSpoolerClient {
            public static IPrintSpoolerClient sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPrintJobQueued(printJob);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAllPrintJobsForServiceHandled(printService);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAllPrintJobsHandled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAllPrintJobsHandled();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPrintJobStateChanged(printJob);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintSpoolerClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintSpoolerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
