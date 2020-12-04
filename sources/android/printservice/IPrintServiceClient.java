package android.printservice;

import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.text.TextUtils;
import java.util.List;

public interface IPrintServiceClient extends IInterface {
    PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException;

    List<PrintJobInfo> getPrintJobInfos() throws RemoteException;

    void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException;

    void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException;

    void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException;

    boolean setPrintJobState(PrintJobId printJobId, int i, String str) throws RemoteException;

    boolean setPrintJobTag(PrintJobId printJobId, String str) throws RemoteException;

    void setProgress(PrintJobId printJobId, float f) throws RemoteException;

    void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException;

    void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException;

    void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException;

    public static class Default implements IPrintServiceClient {
        public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
            return null;
        }

        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException {
            return null;
        }

        public boolean setPrintJobState(PrintJobId printJobId, int state, String error) throws RemoteException {
            return false;
        }

        public boolean setPrintJobTag(PrintJobId printJobId, String tag) throws RemoteException {
            return false;
        }

        public void writePrintJobData(ParcelFileDescriptor fd, PrintJobId printJobId) throws RemoteException {
        }

        public void setProgress(PrintJobId printJobId, float progress) throws RemoteException {
        }

        public void setStatus(PrintJobId printJobId, CharSequence status) throws RemoteException {
        }

        public void setStatusRes(PrintJobId printJobId, int status, CharSequence appPackageName) throws RemoteException {
        }

        public void onPrintersAdded(ParceledListSlice printers) throws RemoteException {
        }

        public void onPrintersRemoved(ParceledListSlice printerIds) throws RemoteException {
        }

        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintServiceClient {
        private static final String DESCRIPTOR = "android.printservice.IPrintServiceClient";
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 11;
        static final int TRANSACTION_onPrintersAdded = 9;
        static final int TRANSACTION_onPrintersRemoved = 10;
        static final int TRANSACTION_setPrintJobState = 3;
        static final int TRANSACTION_setPrintJobTag = 4;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintServiceClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintServiceClient)) {
                return new Proxy(obj);
            }
            return (IPrintServiceClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getPrintJobInfos";
                case 2:
                    return "getPrintJobInfo";
                case 3:
                    return "setPrintJobState";
                case 4:
                    return "setPrintJobTag";
                case 5:
                    return "writePrintJobData";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onPrintersAdded";
                case 10:
                    return "onPrintersRemoved";
                case 11:
                    return "onCustomPrinterIconLoaded";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v31, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: android.graphics.drawable.Icon} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v23, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v27, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.printservice.IPrintServiceClient"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x018a
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x017c;
                    case 2: goto L_0x0154;
                    case 3: goto L_0x012e;
                    case 4: goto L_0x010c;
                    case 5: goto L_0x00e5;
                    case 6: goto L_0x00c7;
                    case 7: goto L_0x009d;
                    case 8: goto L_0x006f;
                    case 9: goto L_0x0055;
                    case 10: goto L_0x003b;
                    case 11: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.print.PrinterId> r3 = android.print.PrinterId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.print.PrinterId r3 = (android.print.PrinterId) r3
                goto L_0x0024
            L_0x0023:
                r3 = r1
            L_0x0024:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0033
                android.os.Parcelable$Creator<android.graphics.drawable.Icon> r1 = android.graphics.drawable.Icon.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.graphics.drawable.Icon r1 = (android.graphics.drawable.Icon) r1
                goto L_0x0034
            L_0x0033:
            L_0x0034:
                r6.onCustomPrinterIconLoaded(r3, r1)
                r9.writeNoException()
                return r2
            L_0x003b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x004d
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x004e
            L_0x004d:
            L_0x004e:
                r6.onPrintersRemoved(r1)
                r9.writeNoException()
                return r2
            L_0x0055:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0067
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x0068
            L_0x0067:
            L_0x0068:
                r6.onPrintersAdded(r1)
                r9.writeNoException()
                return r2
            L_0x006f:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0081
                android.os.Parcelable$Creator<android.print.PrintJobId> r3 = android.print.PrintJobId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.print.PrintJobId r3 = (android.print.PrintJobId) r3
                goto L_0x0082
            L_0x0081:
                r3 = r1
            L_0x0082:
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0095
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x0096
            L_0x0095:
            L_0x0096:
                r6.setStatusRes(r3, r4, r1)
                r9.writeNoException()
                return r2
            L_0x009d:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00af
                android.os.Parcelable$Creator<android.print.PrintJobId> r3 = android.print.PrintJobId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.print.PrintJobId r3 = (android.print.PrintJobId) r3
                goto L_0x00b0
            L_0x00af:
                r3 = r1
            L_0x00b0:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x00c0
            L_0x00bf:
            L_0x00c0:
                r6.setStatus(r3, r1)
                r9.writeNoException()
                return r2
            L_0x00c7:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00d9
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x00da
            L_0x00d9:
            L_0x00da:
                float r3 = r8.readFloat()
                r6.setProgress(r1, r3)
                r9.writeNoException()
                return r2
            L_0x00e5:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00f7
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r3 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x00f8
            L_0x00f7:
                r3 = r1
            L_0x00f8:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0107
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x0108
            L_0x0107:
            L_0x0108:
                r6.writePrintJobData(r3, r1)
                return r2
            L_0x010c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x011e
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x011f
            L_0x011e:
            L_0x011f:
                java.lang.String r3 = r8.readString()
                boolean r4 = r6.setPrintJobTag(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x012e:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0140
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x0141
            L_0x0140:
            L_0x0141:
                int r3 = r8.readInt()
                java.lang.String r4 = r8.readString()
                boolean r5 = r6.setPrintJobState(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x0154:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0166
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x0167
            L_0x0166:
            L_0x0167:
                android.print.PrintJobInfo r3 = r6.getPrintJobInfo(r1)
                r9.writeNoException()
                if (r3 == 0) goto L_0x0177
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x017b
            L_0x0177:
                r4 = 0
                r9.writeInt(r4)
            L_0x017b:
                return r2
            L_0x017c:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getPrintJobInfos()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x018a:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.printservice.IPrintServiceClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintServiceClient {
            public static IPrintServiceClient sDefaultImpl;
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

            public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintJobInfos();
                    }
                    _reply.readException();
                    List<PrintJobInfo> _result = _reply.createTypedArrayList(PrintJobInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException {
                PrintJobInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintJobInfo(printJobId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PrintJobInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PrintJobInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPrintJobState(PrintJobId printJobId, int state, String error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(state);
                    _data.writeString(error);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPrintJobState(printJobId, state, error);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPrintJobTag(PrintJobId printJobId, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(tag);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPrintJobTag(printJobId, tag);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void writePrintJobData(ParcelFileDescriptor fd, PrintJobId printJobId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().writePrintJobData(fd, printJobId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setProgress(PrintJobId printJobId, float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeFloat(progress);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setProgress(printJobId, progress);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setStatus(PrintJobId printJobId, CharSequence status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (status != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(status, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStatus(printJobId, status);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setStatusRes(PrintJobId printJobId, int status, CharSequence appPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(status);
                    if (appPackageName != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(appPackageName, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStatusRes(printJobId, status, appPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPrintersAdded(ParceledListSlice printers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printers != null) {
                        _data.writeInt(1);
                        printers.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPrintersAdded(printers);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPrintersRemoved(ParceledListSlice printerIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerIds != null) {
                        _data.writeInt(1);
                        printerIds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPrintersRemoved(printerIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        _data.writeInt(1);
                        printerId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onCustomPrinterIconLoaded(printerId, icon);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintServiceClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
