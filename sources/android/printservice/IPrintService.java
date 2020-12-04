package android.printservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import java.util.List;

public interface IPrintService extends IInterface {
    void createPrinterDiscoverySession() throws RemoteException;

    void destroyPrinterDiscoverySession() throws RemoteException;

    void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException;

    void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException;

    void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException;

    void setClient(IPrintServiceClient iPrintServiceClient) throws RemoteException;

    void startPrinterDiscovery(List<PrinterId> list) throws RemoteException;

    void startPrinterStateTracking(PrinterId printerId) throws RemoteException;

    void stopPrinterDiscovery() throws RemoteException;

    void stopPrinterStateTracking(PrinterId printerId) throws RemoteException;

    void validatePrinters(List<PrinterId> list) throws RemoteException;

    public static class Default implements IPrintService {
        public void setClient(IPrintServiceClient client) throws RemoteException {
        }

        public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
        }

        public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
        }

        public void createPrinterDiscoverySession() throws RemoteException {
        }

        public void startPrinterDiscovery(List<PrinterId> list) throws RemoteException {
        }

        public void stopPrinterDiscovery() throws RemoteException {
        }

        public void validatePrinters(List<PrinterId> list) throws RemoteException {
        }

        public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
        }

        public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        public void destroyPrinterDiscoverySession() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintService {
        private static final String DESCRIPTOR = "android.printservice.IPrintService";
        static final int TRANSACTION_createPrinterDiscoverySession = 4;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 11;
        static final int TRANSACTION_onPrintJobQueued = 3;
        static final int TRANSACTION_requestCancelPrintJob = 2;
        static final int TRANSACTION_requestCustomPrinterIcon = 9;
        static final int TRANSACTION_setClient = 1;
        static final int TRANSACTION_startPrinterDiscovery = 5;
        static final int TRANSACTION_startPrinterStateTracking = 8;
        static final int TRANSACTION_stopPrinterDiscovery = 6;
        static final int TRANSACTION_stopPrinterStateTracking = 10;
        static final int TRANSACTION_validatePrinters = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintService)) {
                return new Proxy(obj);
            }
            return (IPrintService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setClient";
                case 2:
                    return "requestCancelPrintJob";
                case 3:
                    return "onPrintJobQueued";
                case 4:
                    return "createPrinterDiscoverySession";
                case 5:
                    return "startPrinterDiscovery";
                case 6:
                    return "stopPrinterDiscovery";
                case 7:
                    return "validatePrinters";
                case 8:
                    return "startPrinterStateTracking";
                case 9:
                    return "requestCustomPrinterIcon";
                case 10:
                    return "stopPrinterStateTracking";
                case 11:
                    return "destroyPrinterDiscoverySession";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.print.PrintJobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.print.PrintJobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.print.PrinterId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.print.PrinterId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.print.PrinterId} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.printservice.IPrintService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x00c2
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x00b3;
                    case 2: goto L_0x009c;
                    case 3: goto L_0x0085;
                    case 4: goto L_0x007e;
                    case 5: goto L_0x0071;
                    case 6: goto L_0x006a;
                    case 7: goto L_0x005d;
                    case 8: goto L_0x0046;
                    case 9: goto L_0x002f;
                    case 10: goto L_0x0018;
                    case 11: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                r4.destroyPrinterDiscoverySession()
                return r2
            L_0x0018:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x002a
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrinterId r1 = (android.print.PrinterId) r1
                goto L_0x002b
            L_0x002a:
            L_0x002b:
                r4.stopPrinterStateTracking(r1)
                return r2
            L_0x002f:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0041
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrinterId r1 = (android.print.PrinterId) r1
                goto L_0x0042
            L_0x0041:
            L_0x0042:
                r4.requestCustomPrinterIcon(r1)
                return r2
            L_0x0046:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0058
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrinterId r1 = (android.print.PrinterId) r1
                goto L_0x0059
            L_0x0058:
            L_0x0059:
                r4.startPrinterStateTracking(r1)
                return r2
            L_0x005d:
                r6.enforceInterface(r0)
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.util.ArrayList r1 = r6.createTypedArrayList(r1)
                r4.validatePrinters(r1)
                return r2
            L_0x006a:
                r6.enforceInterface(r0)
                r4.stopPrinterDiscovery()
                return r2
            L_0x0071:
                r6.enforceInterface(r0)
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.util.ArrayList r1 = r6.createTypedArrayList(r1)
                r4.startPrinterDiscovery(r1)
                return r2
            L_0x007e:
                r6.enforceInterface(r0)
                r4.createPrinterDiscoverySession()
                return r2
            L_0x0085:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0097
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrintJobInfo r1 = (android.print.PrintJobInfo) r1
                goto L_0x0098
            L_0x0097:
            L_0x0098:
                r4.onPrintJobQueued(r1)
                return r2
            L_0x009c:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x00ae
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrintJobInfo r1 = (android.print.PrintJobInfo) r1
                goto L_0x00af
            L_0x00ae:
            L_0x00af:
                r4.requestCancelPrintJob(r1)
                return r2
            L_0x00b3:
                r6.enforceInterface(r0)
                android.os.IBinder r1 = r6.readStrongBinder()
                android.printservice.IPrintServiceClient r1 = android.printservice.IPrintServiceClient.Stub.asInterface(r1)
                r4.setClient(r1)
                return r2
            L_0x00c2:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.printservice.IPrintService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintService {
            public static IPrintService sDefaultImpl;
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

            public void setClient(IPrintServiceClient client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setClient(client);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        _data.writeInt(1);
                        printJobInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestCancelPrintJob(printJobInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        _data.writeInt(1);
                        printJobInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPrintJobQueued(printJobInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void createPrinterDiscoverySession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createPrinterDiscoverySession();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startPrinterDiscovery(List<PrinterId> priorityList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(priorityList);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startPrinterDiscovery(priorityList);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopPrinterDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopPrinterDiscovery();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void validatePrinters(List<PrinterId> printerIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(printerIds);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().validatePrinters(printerIds);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        _data.writeInt(1);
                        printerId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startPrinterStateTracking(printerId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        _data.writeInt(1);
                        printerId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestCustomPrinterIcon(printerId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        _data.writeInt(1);
                        printerId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopPrinterStateTracking(printerId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void destroyPrinterDiscoverySession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().destroyPrinterDiscoverySession();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
