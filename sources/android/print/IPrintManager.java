package android.print;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.printservice.recommendation.RecommendationInfo;
import java.util.List;

public interface IPrintManager extends IInterface {
    void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) throws RemoteException;

    void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException;

    void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException;

    void cancelPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException;

    void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    boolean getBindInstantServiceAllowed(int i) throws RemoteException;

    Icon getCustomPrinterIcon(PrinterId printerId, int i) throws RemoteException;

    PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) throws RemoteException;

    List<PrintJobInfo> getPrintJobInfos(int i, int i2) throws RemoteException;

    List<RecommendationInfo> getPrintServiceRecommendations(int i) throws RemoteException;

    List<PrintServiceInfo> getPrintServices(int i, int i2) throws RemoteException;

    Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) throws RemoteException;

    void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) throws RemoteException;

    void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) throws RemoteException;

    void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) throws RemoteException;

    void restartPrintJob(PrintJobId printJobId, int i, int i2) throws RemoteException;

    void setBindInstantServiceAllowed(int i, boolean z) throws RemoteException;

    void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) throws RemoteException;

    void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int i) throws RemoteException;

    void startPrinterStateTracking(PrinterId printerId, int i) throws RemoteException;

    void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) throws RemoteException;

    void stopPrinterStateTracking(PrinterId printerId, int i) throws RemoteException;

    void validatePrinters(List<PrinterId> list, int i) throws RemoteException;

    public static class Default implements IPrintManager {
        public List<PrintJobInfo> getPrintJobInfos(int appId, int userId) throws RemoteException {
            return null;
        }

        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int appId, int userId) throws RemoteException {
            return null;
        }

        public Bundle print(String printJobName, IPrintDocumentAdapter printAdapter, PrintAttributes attributes, String packageName, int appId, int userId) throws RemoteException {
            return null;
        }

        public void cancelPrintJob(PrintJobId printJobId, int appId, int userId) throws RemoteException {
        }

        public void restartPrintJob(PrintJobId printJobId, int appId, int userId) throws RemoteException {
        }

        public void addPrintJobStateChangeListener(IPrintJobStateChangeListener listener, int appId, int userId) throws RemoteException {
        }

        public void removePrintJobStateChangeListener(IPrintJobStateChangeListener listener, int userId) throws RemoteException {
        }

        public void addPrintServicesChangeListener(IPrintServicesChangeListener listener, int userId) throws RemoteException {
        }

        public void removePrintServicesChangeListener(IPrintServicesChangeListener listener, int userId) throws RemoteException {
        }

        public List<PrintServiceInfo> getPrintServices(int selectionFlags, int userId) throws RemoteException {
            return null;
        }

        public void setPrintServiceEnabled(ComponentName service, boolean isEnabled, int userId) throws RemoteException {
        }

        public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener listener, int userId) throws RemoteException {
        }

        public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener listener, int userId) throws RemoteException {
        }

        public List<RecommendationInfo> getPrintServiceRecommendations(int userId) throws RemoteException {
            return null;
        }

        public void createPrinterDiscoverySession(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
        }

        public void startPrinterDiscovery(IPrinterDiscoveryObserver observer, List<PrinterId> list, int userId) throws RemoteException {
        }

        public void stopPrinterDiscovery(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
        }

        public void validatePrinters(List<PrinterId> list, int userId) throws RemoteException {
        }

        public void startPrinterStateTracking(PrinterId printerId, int userId) throws RemoteException {
        }

        public Icon getCustomPrinterIcon(PrinterId printerId, int userId) throws RemoteException {
            return null;
        }

        public void stopPrinterStateTracking(PrinterId printerId, int userId) throws RemoteException {
        }

        public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
        }

        public boolean getBindInstantServiceAllowed(int userId) throws RemoteException {
            return false;
        }

        public void setBindInstantServiceAllowed(int userId, boolean allowed) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintManager {
        private static final String DESCRIPTOR = "android.print.IPrintManager";
        static final int TRANSACTION_addPrintJobStateChangeListener = 6;
        static final int TRANSACTION_addPrintServiceRecommendationsChangeListener = 12;
        static final int TRANSACTION_addPrintServicesChangeListener = 8;
        static final int TRANSACTION_cancelPrintJob = 4;
        static final int TRANSACTION_createPrinterDiscoverySession = 15;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 22;
        static final int TRANSACTION_getBindInstantServiceAllowed = 23;
        static final int TRANSACTION_getCustomPrinterIcon = 20;
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_getPrintServiceRecommendations = 14;
        static final int TRANSACTION_getPrintServices = 10;
        static final int TRANSACTION_print = 3;
        static final int TRANSACTION_removePrintJobStateChangeListener = 7;
        static final int TRANSACTION_removePrintServiceRecommendationsChangeListener = 13;
        static final int TRANSACTION_removePrintServicesChangeListener = 9;
        static final int TRANSACTION_restartPrintJob = 5;
        static final int TRANSACTION_setBindInstantServiceAllowed = 24;
        static final int TRANSACTION_setPrintServiceEnabled = 11;
        static final int TRANSACTION_startPrinterDiscovery = 16;
        static final int TRANSACTION_startPrinterStateTracking = 19;
        static final int TRANSACTION_stopPrinterDiscovery = 17;
        static final int TRANSACTION_stopPrinterStateTracking = 21;
        static final int TRANSACTION_validatePrinters = 18;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPrintManager)) {
                return new Proxy(obj);
            }
            return (IPrintManager) iin;
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
                    return Context.PRINT_SERVICE;
                case 4:
                    return "cancelPrintJob";
                case 5:
                    return "restartPrintJob";
                case 6:
                    return "addPrintJobStateChangeListener";
                case 7:
                    return "removePrintJobStateChangeListener";
                case 8:
                    return "addPrintServicesChangeListener";
                case 9:
                    return "removePrintServicesChangeListener";
                case 10:
                    return "getPrintServices";
                case 11:
                    return "setPrintServiceEnabled";
                case 12:
                    return "addPrintServiceRecommendationsChangeListener";
                case 13:
                    return "removePrintServiceRecommendationsChangeListener";
                case 14:
                    return "getPrintServiceRecommendations";
                case 15:
                    return "createPrinterDiscoverySession";
                case 16:
                    return "startPrinterDiscovery";
                case 17:
                    return "stopPrinterDiscovery";
                case 18:
                    return "validatePrinters";
                case 19:
                    return "startPrinterStateTracking";
                case 20:
                    return "getCustomPrinterIcon";
                case 21:
                    return "stopPrinterStateTracking";
                case 22:
                    return "destroyPrinterDiscoverySession";
                case 23:
                    return "getBindInstantServiceAllowed";
                case 24:
                    return "setBindInstantServiceAllowed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: android.print.PrintJobId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v47, resolved type: android.print.PrinterId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v51, resolved type: android.print.PrinterId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v55, resolved type: android.print.PrinterId} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v7 */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v65 */
        /* JADX WARNING: type inference failed for: r0v66 */
        /* JADX WARNING: type inference failed for: r0v67 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v69 */
        /* JADX WARNING: type inference failed for: r0v70 */
        /* JADX WARNING: type inference failed for: r0v71 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r7 = r19
                r8 = r20
                r9 = r21
                r10 = r22
                java.lang.String r11 = "android.print.IPrintManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x02c6
                r13 = 0
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x02b0;
                    case 2: goto L_0x0281;
                    case 3: goto L_0x0237;
                    case 4: goto L_0x0215;
                    case 5: goto L_0x01f3;
                    case 6: goto L_0x01d9;
                    case 7: goto L_0x01c3;
                    case 8: goto L_0x01ad;
                    case 9: goto L_0x0197;
                    case 10: goto L_0x0181;
                    case 11: goto L_0x015a;
                    case 12: goto L_0x0144;
                    case 13: goto L_0x012e;
                    case 14: goto L_0x011c;
                    case 15: goto L_0x0106;
                    case 16: goto L_0x00ea;
                    case 17: goto L_0x00d4;
                    case 18: goto L_0x00c0;
                    case 19: goto L_0x00a2;
                    case 20: goto L_0x0077;
                    case 21: goto L_0x0059;
                    case 22: goto L_0x0043;
                    case 23: goto L_0x0031;
                    case 24: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0029
                r13 = r12
            L_0x0029:
                r1 = r13
                r7.setBindInstantServiceAllowed(r0, r1)
                r22.writeNoException()
                return r12
            L_0x0031:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                boolean r1 = r7.getBindInstantServiceAllowed(r0)
                r22.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0043:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrinterDiscoveryObserver r0 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.destroyPrinterDiscoverySession(r0, r1)
                r22.writeNoException()
                return r12
            L_0x0059:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x006b
                android.os.Parcelable$Creator<android.print.PrinterId> r0 = android.print.PrinterId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrinterId r0 = (android.print.PrinterId) r0
                goto L_0x006c
            L_0x006b:
            L_0x006c:
                int r1 = r21.readInt()
                r7.stopPrinterStateTracking(r0, r1)
                r22.writeNoException()
                return r12
            L_0x0077:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0089
                android.os.Parcelable$Creator<android.print.PrinterId> r0 = android.print.PrinterId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrinterId r0 = (android.print.PrinterId) r0
                goto L_0x008a
            L_0x0089:
            L_0x008a:
                int r1 = r21.readInt()
                android.graphics.drawable.Icon r2 = r7.getCustomPrinterIcon(r0, r1)
                r22.writeNoException()
                if (r2 == 0) goto L_0x009e
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x00a1
            L_0x009e:
                r10.writeInt(r13)
            L_0x00a1:
                return r12
            L_0x00a2:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00b4
                android.os.Parcelable$Creator<android.print.PrinterId> r0 = android.print.PrinterId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrinterId r0 = (android.print.PrinterId) r0
                goto L_0x00b5
            L_0x00b4:
            L_0x00b5:
                int r1 = r21.readInt()
                r7.startPrinterStateTracking(r0, r1)
                r22.writeNoException()
                return r12
            L_0x00c0:
                r9.enforceInterface(r11)
                android.os.Parcelable$Creator<android.print.PrinterId> r0 = android.print.PrinterId.CREATOR
                java.util.ArrayList r0 = r9.createTypedArrayList(r0)
                int r1 = r21.readInt()
                r7.validatePrinters(r0, r1)
                r22.writeNoException()
                return r12
            L_0x00d4:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrinterDiscoveryObserver r0 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.stopPrinterDiscovery(r0, r1)
                r22.writeNoException()
                return r12
            L_0x00ea:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrinterDiscoveryObserver r0 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(r0)
                android.os.Parcelable$Creator<android.print.PrinterId> r1 = android.print.PrinterId.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                int r2 = r21.readInt()
                r7.startPrinterDiscovery(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0106:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrinterDiscoveryObserver r0 = android.print.IPrinterDiscoveryObserver.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.createPrinterDiscoverySession(r0, r1)
                r22.writeNoException()
                return r12
            L_0x011c:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.util.List r1 = r7.getPrintServiceRecommendations(r0)
                r22.writeNoException()
                r10.writeTypedList(r1)
                return r12
            L_0x012e:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.printservice.recommendation.IRecommendationsChangeListener r0 = android.printservice.recommendation.IRecommendationsChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.removePrintServiceRecommendationsChangeListener(r0, r1)
                r22.writeNoException()
                return r12
            L_0x0144:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.printservice.recommendation.IRecommendationsChangeListener r0 = android.printservice.recommendation.IRecommendationsChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.addPrintServiceRecommendationsChangeListener(r0, r1)
                r22.writeNoException()
                return r12
            L_0x015a:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x016c
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x016d
            L_0x016c:
            L_0x016d:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0175
                r13 = r12
            L_0x0175:
                r1 = r13
                int r2 = r21.readInt()
                r7.setPrintServiceEnabled(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0181:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.util.List r2 = r7.getPrintServices(r0, r1)
                r22.writeNoException()
                r10.writeTypedList(r2)
                return r12
            L_0x0197:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrintServicesChangeListener r0 = android.print.IPrintServicesChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.removePrintServicesChangeListener(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01ad:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrintServicesChangeListener r0 = android.print.IPrintServicesChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.addPrintServicesChangeListener(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01c3:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrintJobStateChangeListener r0 = android.print.IPrintJobStateChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r7.removePrintJobStateChangeListener(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01d9:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.print.IPrintJobStateChangeListener r0 = android.print.IPrintJobStateChangeListener.Stub.asInterface(r0)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                r7.addPrintJobStateChangeListener(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x01f3:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0205
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x0206
            L_0x0205:
            L_0x0206:
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                r7.restartPrintJob(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0215:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0227
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x0228
            L_0x0227:
            L_0x0228:
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                r7.cancelPrintJob(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0237:
                r9.enforceInterface(r11)
                java.lang.String r14 = r21.readString()
                android.os.IBinder r1 = r21.readStrongBinder()
                android.print.IPrintDocumentAdapter r15 = android.print.IPrintDocumentAdapter.Stub.asInterface(r1)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0256
                android.os.Parcelable$Creator<android.print.PrintAttributes> r0 = android.print.PrintAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrintAttributes r0 = (android.print.PrintAttributes) r0
            L_0x0254:
                r3 = r0
                goto L_0x0257
            L_0x0256:
                goto L_0x0254
            L_0x0257:
                java.lang.String r16 = r21.readString()
                int r17 = r21.readInt()
                int r18 = r21.readInt()
                r0 = r19
                r1 = r14
                r2 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                android.os.Bundle r0 = r0.print(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                if (r0 == 0) goto L_0x027d
                r10.writeInt(r12)
                r0.writeToParcel(r10, r12)
                goto L_0x0280
            L_0x027d:
                r10.writeInt(r13)
            L_0x0280:
                return r12
            L_0x0281:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0293
                android.os.Parcelable$Creator<android.print.PrintJobId> r0 = android.print.PrintJobId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.print.PrintJobId r0 = (android.print.PrintJobId) r0
                goto L_0x0294
            L_0x0293:
            L_0x0294:
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                android.print.PrintJobInfo r3 = r7.getPrintJobInfo(r0, r1, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x02ac
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x02af
            L_0x02ac:
                r10.writeInt(r13)
            L_0x02af:
                return r12
            L_0x02b0:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.util.List r2 = r7.getPrintJobInfos(r0, r1)
                r22.writeNoException()
                r10.writeTypedList(r2)
                return r12
            L_0x02c6:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.IPrintManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPrintManager {
            public static IPrintManager sDefaultImpl;
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

            public List<PrintJobInfo> getPrintJobInfos(int appId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintJobInfos(appId, userId);
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

            public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int appId, int userId) throws RemoteException {
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
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintJobInfo(printJobId, appId, userId);
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

            public Bundle print(String printJobName, IPrintDocumentAdapter printAdapter, PrintAttributes attributes, String packageName, int appId, int userId) throws RemoteException {
                Bundle _result;
                PrintAttributes printAttributes = attributes;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(printJobName);
                        _result = null;
                        _data.writeStrongBinder(printAdapter != null ? printAdapter.asBinder() : null);
                        if (printAttributes != null) {
                            _data.writeInt(1);
                            printAttributes.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        String str = packageName;
                        int i = appId;
                        int i2 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(appId);
                        } catch (Throwable th2) {
                            th = th2;
                            int i22 = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = Bundle.CREATOR.createFromParcel(_reply);
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            Bundle print = Stub.getDefaultImpl().print(printJobName, printAdapter, attributes, packageName, appId, userId);
                            _reply.recycle();
                            _data.recycle();
                            return print;
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i3 = appId;
                        int i222 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str2 = printJobName;
                    String str3 = packageName;
                    int i32 = appId;
                    int i2222 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void cancelPrintJob(PrintJobId printJobId, int appId, int userId) throws RemoteException {
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
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelPrintJob(printJobId, appId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restartPrintJob(PrintJobId printJobId, int appId, int userId) throws RemoteException {
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
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restartPrintJob(printJobId, appId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addPrintJobStateChangeListener(IPrintJobStateChangeListener listener, int appId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPrintJobStateChangeListener(listener, appId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removePrintJobStateChangeListener(IPrintJobStateChangeListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removePrintJobStateChangeListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addPrintServicesChangeListener(IPrintServicesChangeListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPrintServicesChangeListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removePrintServicesChangeListener(IPrintServicesChangeListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removePrintServicesChangeListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PrintServiceInfo> getPrintServices(int selectionFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(selectionFlags);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintServices(selectionFlags, userId);
                    }
                    _reply.readException();
                    List<PrintServiceInfo> _result = _reply.createTypedArrayList(PrintServiceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrintServiceEnabled(ComponentName service, boolean isEnabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isEnabled);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPrintServiceEnabled(service, isEnabled, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPrintServiceRecommendationsChangeListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removePrintServiceRecommendationsChangeListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<RecommendationInfo> getPrintServiceRecommendations(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrintServiceRecommendations(userId);
                    }
                    _reply.readException();
                    List<RecommendationInfo> _result = _reply.createTypedArrayList(RecommendationInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createPrinterDiscoverySession(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createPrinterDiscoverySession(observer, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startPrinterDiscovery(IPrinterDiscoveryObserver observer, List<PrinterId> priorityList, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeTypedList(priorityList);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startPrinterDiscovery(observer, priorityList, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopPrinterDiscovery(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopPrinterDiscovery(observer, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void validatePrinters(List<PrinterId> printerIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(printerIds);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().validatePrinters(printerIds, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startPrinterStateTracking(PrinterId printerId, int userId) throws RemoteException {
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
                    _data.writeInt(userId);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startPrinterStateTracking(printerId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Icon getCustomPrinterIcon(PrinterId printerId, int userId) throws RemoteException {
                Icon _result;
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
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCustomPrinterIcon(printerId, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Icon.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Icon _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopPrinterStateTracking(PrinterId printerId, int userId) throws RemoteException {
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
                    _data.writeInt(userId);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopPrinterStateTracking(printerId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroyPrinterDiscoverySession(observer, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getBindInstantServiceAllowed(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBindInstantServiceAllowed(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBindInstantServiceAllowed(int userId, boolean allowed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(allowed);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBindInstantServiceAllowed(userId, allowed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrintManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPrintManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
