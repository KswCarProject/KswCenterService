package android.print;

import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IPrintSpoolerCallbacks extends IInterface {
    void customPrinterIconCacheCleared(int i) throws RemoteException;

    void onCancelPrintJobResult(boolean z, int i) throws RemoteException;

    void onCustomPrinterIconCached(int i) throws RemoteException;

    void onGetCustomPrinterIconResult(Icon icon, int i) throws RemoteException;

    void onGetPrintJobInfoResult(PrintJobInfo printJobInfo, int i) throws RemoteException;

    void onGetPrintJobInfosResult(List<PrintJobInfo> list, int i) throws RemoteException;

    void onSetPrintJobStateResult(boolean z, int i) throws RemoteException;

    void onSetPrintJobTagResult(boolean z, int i) throws RemoteException;

    public static class Default implements IPrintSpoolerCallbacks {
        public void onGetPrintJobInfosResult(List<PrintJobInfo> list, int sequence) throws RemoteException {
        }

        public void onCancelPrintJobResult(boolean canceled, int sequence) throws RemoteException {
        }

        public void onSetPrintJobStateResult(boolean success, int sequence) throws RemoteException {
        }

        public void onSetPrintJobTagResult(boolean success, int sequence) throws RemoteException {
        }

        public void onGetPrintJobInfoResult(PrintJobInfo printJob, int sequence) throws RemoteException {
        }

        public void onGetCustomPrinterIconResult(Icon icon, int sequence) throws RemoteException {
        }

        public void onCustomPrinterIconCached(int sequence) throws RemoteException {
        }

        public void customPrinterIconCacheCleared(int sequence) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintSpoolerCallbacks {
        private static final String DESCRIPTOR = "android.print.IPrintSpoolerCallbacks";
        static final int TRANSACTION_customPrinterIconCacheCleared = 8;
        static final int TRANSACTION_onCancelPrintJobResult = 2;
        static final int TRANSACTION_onCustomPrinterIconCached = 7;
        static final int TRANSACTION_onGetCustomPrinterIconResult = 6;
        static final int TRANSACTION_onGetPrintJobInfoResult = 5;
        static final int TRANSACTION_onGetPrintJobInfosResult = 1;
        static final int TRANSACTION_onSetPrintJobStateResult = 3;
        static final int TRANSACTION_onSetPrintJobTagResult = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpoolerCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintSpoolerCallbacks)) {
                return new Proxy(obj);
            }
            return (IPrintSpoolerCallbacks) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onGetPrintJobInfosResult";
                case 2:
                    return "onCancelPrintJobResult";
                case 3:
                    return "onSetPrintJobStateResult";
                case 4:
                    return "onSetPrintJobTagResult";
                case 5:
                    return "onGetPrintJobInfoResult";
                case 6:
                    return "onGetCustomPrinterIconResult";
                case 7:
                    return "onCustomPrinterIconCached";
                case 8:
                    return "customPrinterIconCacheCleared";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.print.PrintJobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.graphics.drawable.Icon} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.print.IPrintSpoolerCallbacks"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x00ab
                r1 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x009a;
                    case 2: goto L_0x0086;
                    case 3: goto L_0x0072;
                    case 4: goto L_0x005e;
                    case 5: goto L_0x0043;
                    case 6: goto L_0x0028;
                    case 7: goto L_0x001d;
                    case 8: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0012:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.customPrinterIconCacheCleared(r1)
                return r2
            L_0x001d:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onCustomPrinterIconCached(r1)
                return r2
            L_0x0028:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.graphics.drawable.Icon> r1 = android.graphics.drawable.Icon.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.graphics.drawable.Icon r1 = (android.graphics.drawable.Icon) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                int r3 = r6.readInt()
                r4.onGetCustomPrinterIconResult(r1, r3)
                return r2
            L_0x0043:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0055
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.print.PrintJobInfo r1 = (android.print.PrintJobInfo) r1
                goto L_0x0056
            L_0x0055:
            L_0x0056:
                int r3 = r6.readInt()
                r4.onGetPrintJobInfoResult(r1, r3)
                return r2
            L_0x005e:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0069
                r3 = r2
            L_0x0069:
                r1 = r3
                int r3 = r6.readInt()
                r4.onSetPrintJobTagResult(r1, r3)
                return r2
            L_0x0072:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x007d
                r3 = r2
            L_0x007d:
                r1 = r3
                int r3 = r6.readInt()
                r4.onSetPrintJobStateResult(r1, r3)
                return r2
            L_0x0086:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0091
                r3 = r2
            L_0x0091:
                r1 = r3
                int r3 = r6.readInt()
                r4.onCancelPrintJobResult(r1, r3)
                return r2
            L_0x009a:
                r6.enforceInterface(r0)
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r1 = android.print.PrintJobInfo.CREATOR
                java.util.ArrayList r1 = r6.createTypedArrayList(r1)
                int r3 = r6.readInt()
                r4.onGetPrintJobInfosResult(r1, r3)
                return r2
            L_0x00ab:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.IPrintSpoolerCallbacks.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintSpoolerCallbacks {
            public static IPrintSpoolerCallbacks sDefaultImpl;
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

            public void onGetPrintJobInfosResult(List<PrintJobInfo> printJob, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(printJob);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGetPrintJobInfosResult(printJob, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCancelPrintJobResult(boolean canceled, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(canceled);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCancelPrintJobResult(canceled, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSetPrintJobStateResult(boolean success, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(success);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSetPrintJobStateResult(success, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSetPrintJobTagResult(boolean success, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(success);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSetPrintJobTagResult(success, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGetPrintJobInfoResult(PrintJobInfo printJob, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJob != null) {
                        _data.writeInt(1);
                        printJob.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGetPrintJobInfoResult(printJob, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGetCustomPrinterIconResult(Icon icon, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGetCustomPrinterIconResult(icon, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCustomPrinterIconCached(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCustomPrinterIconCached(sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void customPrinterIconCacheCleared(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().customPrinterIconCacheCleared(sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintSpoolerCallbacks impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintSpoolerCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
