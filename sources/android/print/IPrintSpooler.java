package android.print;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

public interface IPrintSpooler extends IInterface {
    void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException;

    void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws RemoteException;

    void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int i, int i2, int i3) throws RemoteException;

    void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException;

    void removeObsoletePrintJobs() throws RemoteException;

    void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException;

    void setPrintJobCancelling(PrintJobId printJobId, boolean z) throws RemoteException;

    void setPrintJobState(PrintJobId printJobId, int i, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws RemoteException;

    void setPrintJobTag(PrintJobId printJobId, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void setProgress(PrintJobId printJobId, float f) throws RemoteException;

    void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException;

    void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException;

    void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException;

    public static class Default implements IPrintSpooler {
        public void removeObsoletePrintJobs() throws RemoteException {
        }

        public void getPrintJobInfos(IPrintSpoolerCallbacks callback, ComponentName componentName, int state, int appId, int sequence) throws RemoteException {
        }

        public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks callback, int appId, int sequence) throws RemoteException {
        }

        public void createPrintJob(PrintJobInfo printJob) throws RemoteException {
        }

        public void setPrintJobState(PrintJobId printJobId, int status, String stateReason, IPrintSpoolerCallbacks callback, int sequence) throws RemoteException {
        }

        public void setProgress(PrintJobId printJobId, float progress) throws RemoteException {
        }

        public void setStatus(PrintJobId printJobId, CharSequence status) throws RemoteException {
        }

        public void setStatusRes(PrintJobId printJobId, int status, CharSequence appPackageName) throws RemoteException {
        }

        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
        }

        public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
        }

        public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
        }

        public void setPrintJobTag(PrintJobId printJobId, String tag, IPrintSpoolerCallbacks callback, int sequence) throws RemoteException {
        }

        public void writePrintJobData(ParcelFileDescriptor fd, PrintJobId printJobId) throws RemoteException {
        }

        public void setClient(IPrintSpoolerClient client) throws RemoteException {
        }

        public void setPrintJobCancelling(PrintJobId printJobId, boolean cancelling) throws RemoteException {
        }

        public void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintSpooler {
        private static final String DESCRIPTOR = "android.print.IPrintSpooler";
        static final int TRANSACTION_clearCustomPrinterIconCache = 11;
        static final int TRANSACTION_createPrintJob = 4;
        static final int TRANSACTION_getCustomPrinterIcon = 10;
        static final int TRANSACTION_getPrintJobInfo = 3;
        static final int TRANSACTION_getPrintJobInfos = 2;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 9;
        static final int TRANSACTION_pruneApprovedPrintServices = 16;
        static final int TRANSACTION_removeObsoletePrintJobs = 1;
        static final int TRANSACTION_setClient = 14;
        static final int TRANSACTION_setPrintJobCancelling = 15;
        static final int TRANSACTION_setPrintJobState = 5;
        static final int TRANSACTION_setPrintJobTag = 12;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpooler asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintSpooler)) {
                return new Proxy(obj);
            }
            return (IPrintSpooler) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "removeObsoletePrintJobs";
                case 2:
                    return "getPrintJobInfos";
                case 3:
                    return "getPrintJobInfo";
                case 4:
                    return "createPrintJob";
                case 5:
                    return "setPrintJobState";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onCustomPrinterIconLoaded";
                case 10:
                    return "getCustomPrinterIcon";
                case 11:
                    return "clearCustomPrinterIconCache";
                case 12:
                    return "setPrintJobTag";
                case 13:
                    return "writePrintJobData";
                case 14:
                    return "setClient";
                case 15:
                    return "setPrintJobCancelling";
                case 16:
                    return "pruneApprovedPrintServices";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.print.PrintJobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: android.graphics.drawable.Icon} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.print.PrinterId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: android.print.PrintJobId} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v3 */
        /* JADX WARNING: type inference failed for: r0v16 */
        /* JADX WARNING: type inference failed for: r0v26, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v30, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v61 */
        /* JADX WARNING: type inference failed for: r0v62 */
        /* JADX WARNING: type inference failed for: r0v63 */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v65 */
        /* JADX WARNING: type inference failed for: r0v66 */
        /* JADX WARNING: type inference failed for: r0v67 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v69 */
        /* JADX WARNING: type inference failed for: r0v70 */
        /* JADX WARNING: type inference failed for: r0v71 */
        /* JADX WARNING: type inference failed for: r0v72 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.print.IPrintSpooler"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x021d
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x0216;
                    case 2: goto L_0x01e5;
                    case 3: goto L_0x01be;
                    case 4: goto L_0x01a7;
                    case 5: goto L_0x0176;
                    case 6: goto L_0x015b;
                    case 7: goto L_0x0134;
                    case 8: goto L_0x0109;
                    case 9: goto L_0x00d6;
                    case 10: goto L_0x00b3;
                    case 11: goto L_0x00a0;
                    case 12: goto L_0x0079;
                    case 13: goto L_0x0052;
                    case 14: goto L_0x0043;
                    case 15: goto L_0x0023;
                    case 16: goto L_0x0016;
                    default: goto L_0x0011;
                }
            L_0x0011:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0016:
                r8.enforceInterface(r9)
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                r15.pruneApprovedPrintServices(r0)
                return r10
            L_0x0023:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0035
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x0036
            L_0x0035:
            L_0x0036:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x003e
                r1 = r10
                goto L_0x003f
            L_0x003e:
                r1 = 0
            L_0x003f:
                r15.setPrintJobCancelling(r0, r1)
                return r10
            L_0x0043:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                android.print.IPrintSpoolerClient r0 = android.print.IPrintSpoolerClient.Stub.asInterface(r0)
                r15.setClient(r0)
                return r10
            L_0x0052:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0064
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ParcelFileDescriptor r1 = (android.os.ParcelFileDescriptor) r1
                goto L_0x0065
            L_0x0064:
                r1 = r0
            L_0x0065:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0074
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x0075
            L_0x0074:
            L_0x0075:
                r15.writePrintJobData(r1, r0)
                return r10
            L_0x0079:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x008b
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x008c
            L_0x008b:
            L_0x008c:
                java.lang.String r1 = r17.readString()
                android.os.IBinder r2 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r2 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r2)
                int r3 = r17.readInt()
                r15.setPrintJobTag(r0, r1, r2, r3)
                return r10
            L_0x00a0:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r0 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r0)
                int r1 = r17.readInt()
                r15.clearCustomPrinterIconCache(r0, r1)
                return r10
            L_0x00b3:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00c5
                android.os.Parcelable$Creator<android.print.PrinterId> r0 = android.print.PrinterId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrinterId r0 = (android.print.PrinterId) r0
                goto L_0x00c6
            L_0x00c5:
            L_0x00c6:
                android.os.IBinder r1 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r1 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r1)
                int r2 = r17.readInt()
                r15.getCustomPrinterIcon(r0, r1, r2)
                return r10
            L_0x00d6:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00e8
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrinterId r1 = (android.print.PrinterId) r1
                goto L_0x00e9
            L_0x00e8:
                r1 = r0
            L_0x00e9:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00f8
                android.os.Parcelable$Creator<android.graphics.drawable.Icon> r0 = android.graphics.drawable.Icon.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.graphics.drawable.Icon r0 = (android.graphics.drawable.Icon) r0
                goto L_0x00f9
            L_0x00f8:
            L_0x00f9:
                android.os.IBinder r2 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r2 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r2)
                int r3 = r17.readInt()
                r15.onCustomPrinterIconLoaded(r1, r0, r2, r3)
                return r10
            L_0x0109:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x011b
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x011c
            L_0x011b:
                r1 = r0
            L_0x011c:
                int r2 = r17.readInt()
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x012f
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0130
            L_0x012f:
            L_0x0130:
                r15.setStatusRes(r1, r2, r0)
                return r10
            L_0x0134:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0146
                android.os.Parcelable$Creator<android.print.PrintJobId> r1 = android.print.PrintJobId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.print.PrintJobId r1 = (android.print.PrintJobId) r1
                goto L_0x0147
            L_0x0146:
                r1 = r0
            L_0x0147:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0156
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                goto L_0x0157
            L_0x0156:
            L_0x0157:
                r15.setStatus(r1, r0)
                return r10
            L_0x015b:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x016d
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x016e
            L_0x016d:
            L_0x016e:
                float r1 = r17.readFloat()
                r15.setProgress(r0, r1)
                return r10
            L_0x0176:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0189
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
            L_0x0187:
                r1 = r0
                goto L_0x018a
            L_0x0189:
                goto L_0x0187
            L_0x018a:
                int r11 = r17.readInt()
                java.lang.String r12 = r17.readString()
                android.os.IBinder r0 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r13 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r0)
                int r14 = r17.readInt()
                r0 = r15
                r2 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.setPrintJobState(r1, r2, r3, r4, r5)
                return r10
            L_0x01a7:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x01b9
                android.os.Parcelable$Creator<android.print.PrintJobInfo> r0 = android.print.PrintJobInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobInfo r0 = (android.print.PrintJobInfo) r0
                goto L_0x01ba
            L_0x01b9:
            L_0x01ba:
                r15.createPrintJob(r0)
                return r10
            L_0x01be:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x01d0
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x01d1
            L_0x01d0:
            L_0x01d1:
                android.os.IBinder r1 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r1 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r1)
                int r2 = r17.readInt()
                int r3 = r17.readInt()
                r15.getPrintJobInfo(r0, r1, r2, r3)
                return r10
            L_0x01e5:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.print.IPrintSpoolerCallbacks r11 = android.print.IPrintSpoolerCallbacks.Stub.asInterface(r1)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0200
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
            L_0x01fe:
                r2 = r0
                goto L_0x0201
            L_0x0200:
                goto L_0x01fe
            L_0x0201:
                int r12 = r17.readInt()
                int r13 = r17.readInt()
                int r14 = r17.readInt()
                r0 = r15
                r1 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.getPrintJobInfos(r1, r2, r3, r4, r5)
                return r10
            L_0x0216:
                r8.enforceInterface(r9)
                r15.removeObsoletePrintJobs()
                return r10
            L_0x021d:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.IPrintSpooler.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintSpooler {
            public static IPrintSpooler sDefaultImpl;
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

            public void removeObsoletePrintJobs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeObsoletePrintJobs();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getPrintJobInfos(IPrintSpoolerCallbacks callback, ComponentName componentName, int state, int appId, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(state);
                    _data.writeInt(appId);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getPrintJobInfos(callback, componentName, state, appId, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks callback, int appId, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(appId);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getPrintJobInfo(printJobId, callback, appId, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void createPrintJob(PrintJobInfo printJob) throws RemoteException {
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
                        Stub.getDefaultImpl().createPrintJob(printJob);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPrintJobState(PrintJobId printJobId, int status, String stateReason, IPrintSpoolerCallbacks callback, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(status);
                    _data.writeString(stateReason);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPrintJobState(printJobId, status, stateReason, callback, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setProgress(PrintJobId printJobId, float progress) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeFloat(progress);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setProgress(printJobId, progress);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setStatus(PrintJobId printJobId, CharSequence status) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setStatus(printJobId, status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setStatusRes(PrintJobId printJobId, int status, CharSequence appPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setStatusRes(printJobId, status, appPackageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCustomPrinterIconLoaded(printerId, icon, callbacks, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        _data.writeInt(1);
                        printerId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getCustomPrinterIcon(printerId, callbacks, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks callbacks, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearCustomPrinterIconCache(callbacks, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPrintJobTag(PrintJobId printJobId, String tag, IPrintSpoolerCallbacks callback, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(tag);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPrintJobTag(printJobId, tag, callback, sequence);
                    }
                } finally {
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
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().writePrintJobData(fd, printJobId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setClient(IPrintSpoolerClient client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setClient(client);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setPrintJobCancelling(PrintJobId printJobId, boolean cancelling) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        _data.writeInt(1);
                        printJobId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(cancelling);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPrintJobCancelling(printJobId, cancelling);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void pruneApprovedPrintServices(List<ComponentName> servicesToKeep) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(servicesToKeep);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().pruneApprovedPrintServices(servicesToKeep);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintSpooler impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintSpooler getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
