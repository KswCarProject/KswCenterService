package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.List;

public interface IAppOpsService extends IInterface {
    void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException;

    int checkAudioOperation(int i, int i2, int i3, String str) throws RemoteException;

    int checkOperation(int i, int i2, String str) throws RemoteException;

    int checkOperationRaw(int i, int i2, String str) throws RemoteException;

    int checkPackage(int i, String str) throws RemoteException;

    void clearHistory() throws RemoteException;

    @UnsupportedAppUsage
    void finishOperation(IBinder iBinder, int i, int i2, String str) throws RemoteException;

    void getHistoricalOps(int i, String str, List<String> list, long j, long j2, int i2, RemoteCallback remoteCallback) throws RemoteException;

    void getHistoricalOpsFromDiskRaw(int i, String str, List<String> list, long j, long j2, int i2, RemoteCallback remoteCallback) throws RemoteException;

    @UnsupportedAppUsage
    List<AppOpsManager.PackageOps> getOpsForPackage(int i, String str, int[] iArr) throws RemoteException;

    @UnsupportedAppUsage
    List<AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws RemoteException;

    IBinder getToken(IBinder iBinder) throws RemoteException;

    List<AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws RemoteException;

    boolean isOperationActive(int i, int i2, String str) throws RemoteException;

    int noteOperation(int i, int i2, String str) throws RemoteException;

    int noteProxyOperation(int i, int i2, String str, int i3, String str2) throws RemoteException;

    void offsetHistory(long j) throws RemoteException;

    int permissionToOpCode(String str) throws RemoteException;

    void reloadNonHistoricalState() throws RemoteException;

    void removeUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    void resetAllModes(int i, String str) throws RemoteException;

    void resetHistoryParameters() throws RemoteException;

    void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) throws RemoteException;

    void setHistoryParameters(int i, long j, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void setMode(int i, int i2, String str, int i3) throws RemoteException;

    void setUidMode(int i, int i2, int i3) throws RemoteException;

    void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, String[] strArr) throws RemoteException;

    void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) throws RemoteException;

    int startOperation(IBinder iBinder, int i, int i2, String str, boolean z) throws RemoteException;

    void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException;

    void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void startWatchingModeWithFlags(int i, String str, int i2, IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException;

    void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException;

    void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException;

    public static class Default implements IAppOpsService {
        public int checkOperation(int code, int uid, String packageName) throws RemoteException {
            return 0;
        }

        public int noteOperation(int code, int uid, String packageName) throws RemoteException {
            return 0;
        }

        public int startOperation(IBinder token, int code, int uid, String packageName, boolean startIfModeDefault) throws RemoteException {
            return 0;
        }

        public void finishOperation(IBinder token, int code, int uid, String packageName) throws RemoteException {
        }

        public void startWatchingMode(int op, String packageName, IAppOpsCallback callback) throws RemoteException {
        }

        public void stopWatchingMode(IAppOpsCallback callback) throws RemoteException {
        }

        public IBinder getToken(IBinder clientToken) throws RemoteException {
            return null;
        }

        public int permissionToOpCode(String permission) throws RemoteException {
            return 0;
        }

        public int checkAudioOperation(int code, int usage, int uid, String packageName) throws RemoteException {
            return 0;
        }

        public int noteProxyOperation(int code, int proxyUid, String proxyPackageName, int callingUid, String callingPackageName) throws RemoteException {
            return 0;
        }

        public int checkPackage(int uid, String packageName) throws RemoteException {
            return 0;
        }

        public List<AppOpsManager.PackageOps> getPackagesForOps(int[] ops) throws RemoteException {
            return null;
        }

        public List<AppOpsManager.PackageOps> getOpsForPackage(int uid, String packageName, int[] ops) throws RemoteException {
            return null;
        }

        public void getHistoricalOps(int uid, String packageName, List<String> list, long beginTimeMillis, long endTimeMillis, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void getHistoricalOpsFromDiskRaw(int uid, String packageName, List<String> list, long beginTimeMillis, long endTimeMillis, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void offsetHistory(long duration) throws RemoteException {
        }

        public void setHistoryParameters(int mode, long baseSnapshotInterval, int compressionStep) throws RemoteException {
        }

        public void addHistoricalOps(AppOpsManager.HistoricalOps ops) throws RemoteException {
        }

        public void resetHistoryParameters() throws RemoteException {
        }

        public void clearHistory() throws RemoteException {
        }

        public List<AppOpsManager.PackageOps> getUidOps(int uid, int[] ops) throws RemoteException {
            return null;
        }

        public void setUidMode(int code, int uid, int mode) throws RemoteException {
        }

        public void setMode(int code, int uid, String packageName, int mode) throws RemoteException {
        }

        public void resetAllModes(int reqUserId, String reqPackageName) throws RemoteException {
        }

        public void setAudioRestriction(int code, int usage, int uid, int mode, String[] exceptionPackages) throws RemoteException {
        }

        public void setUserRestrictions(Bundle restrictions, IBinder token, int userHandle) throws RemoteException {
        }

        public void setUserRestriction(int code, boolean restricted, IBinder token, int userHandle, String[] exceptionPackages) throws RemoteException {
        }

        public void removeUser(int userHandle) throws RemoteException {
        }

        public void startWatchingActive(int[] ops, IAppOpsActiveCallback callback) throws RemoteException {
        }

        public void stopWatchingActive(IAppOpsActiveCallback callback) throws RemoteException {
        }

        public boolean isOperationActive(int code, int uid, String packageName) throws RemoteException {
            return false;
        }

        public void startWatchingModeWithFlags(int op, String packageName, int flags, IAppOpsCallback callback) throws RemoteException {
        }

        public void startWatchingNoted(int[] ops, IAppOpsNotedCallback callback) throws RemoteException {
        }

        public void stopWatchingNoted(IAppOpsNotedCallback callback) throws RemoteException {
        }

        public int checkOperationRaw(int code, int uid, String packageName) throws RemoteException {
            return 0;
        }

        public void reloadNonHistoricalState() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppOpsService {
        private static final String DESCRIPTOR = "com.android.internal.app.IAppOpsService";
        static final int TRANSACTION_addHistoricalOps = 18;
        static final int TRANSACTION_checkAudioOperation = 9;
        static final int TRANSACTION_checkOperation = 1;
        static final int TRANSACTION_checkOperationRaw = 35;
        static final int TRANSACTION_checkPackage = 11;
        static final int TRANSACTION_clearHistory = 20;
        static final int TRANSACTION_finishOperation = 4;
        static final int TRANSACTION_getHistoricalOps = 14;
        static final int TRANSACTION_getHistoricalOpsFromDiskRaw = 15;
        static final int TRANSACTION_getOpsForPackage = 13;
        static final int TRANSACTION_getPackagesForOps = 12;
        static final int TRANSACTION_getToken = 7;
        static final int TRANSACTION_getUidOps = 21;
        static final int TRANSACTION_isOperationActive = 31;
        static final int TRANSACTION_noteOperation = 2;
        static final int TRANSACTION_noteProxyOperation = 10;
        static final int TRANSACTION_offsetHistory = 16;
        static final int TRANSACTION_permissionToOpCode = 8;
        static final int TRANSACTION_reloadNonHistoricalState = 36;
        static final int TRANSACTION_removeUser = 28;
        static final int TRANSACTION_resetAllModes = 24;
        static final int TRANSACTION_resetHistoryParameters = 19;
        static final int TRANSACTION_setAudioRestriction = 25;
        static final int TRANSACTION_setHistoryParameters = 17;
        static final int TRANSACTION_setMode = 23;
        static final int TRANSACTION_setUidMode = 22;
        static final int TRANSACTION_setUserRestriction = 27;
        static final int TRANSACTION_setUserRestrictions = 26;
        static final int TRANSACTION_startOperation = 3;
        static final int TRANSACTION_startWatchingActive = 29;
        static final int TRANSACTION_startWatchingMode = 5;
        static final int TRANSACTION_startWatchingModeWithFlags = 32;
        static final int TRANSACTION_startWatchingNoted = 33;
        static final int TRANSACTION_stopWatchingActive = 30;
        static final int TRANSACTION_stopWatchingMode = 6;
        static final int TRANSACTION_stopWatchingNoted = 34;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppOpsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAppOpsService)) {
                return new Proxy(obj);
            }
            return (IAppOpsService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "checkOperation";
                case 2:
                    return "noteOperation";
                case 3:
                    return "startOperation";
                case 4:
                    return "finishOperation";
                case 5:
                    return "startWatchingMode";
                case 6:
                    return "stopWatchingMode";
                case 7:
                    return "getToken";
                case 8:
                    return "permissionToOpCode";
                case 9:
                    return "checkAudioOperation";
                case 10:
                    return "noteProxyOperation";
                case 11:
                    return "checkPackage";
                case 12:
                    return "getPackagesForOps";
                case 13:
                    return "getOpsForPackage";
                case 14:
                    return "getHistoricalOps";
                case 15:
                    return "getHistoricalOpsFromDiskRaw";
                case 16:
                    return "offsetHistory";
                case 17:
                    return "setHistoryParameters";
                case 18:
                    return "addHistoricalOps";
                case 19:
                    return "resetHistoryParameters";
                case 20:
                    return "clearHistory";
                case 21:
                    return "getUidOps";
                case 22:
                    return "setUidMode";
                case 23:
                    return "setMode";
                case 24:
                    return "resetAllModes";
                case 25:
                    return "setAudioRestriction";
                case 26:
                    return "setUserRestrictions";
                case 27:
                    return "setUserRestriction";
                case 28:
                    return "removeUser";
                case 29:
                    return "startWatchingActive";
                case 30:
                    return "stopWatchingActive";
                case 31:
                    return "isOperationActive";
                case 32:
                    return "startWatchingModeWithFlags";
                case 33:
                    return "startWatchingNoted";
                case 34:
                    return "stopWatchingNoted";
                case 35:
                    return "checkOperationRaw";
                case 36:
                    return "reloadNonHistoricalState";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: android.app.AppOpsManager$HistoricalOps} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.app.AppOpsManager$HistoricalOps} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.app.AppOpsManager$HistoricalOps} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v38, resolved type: android.app.AppOpsManager$HistoricalOps} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: android.app.AppOpsManager$HistoricalOps} */
        /* JADX WARNING: type inference failed for: r1v28, types: [android.os.Bundle] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r25, android.os.Parcel r26, android.os.Parcel r27, int r28) throws android.os.RemoteException {
            /*
                r24 = this;
                r10 = r24
                r11 = r25
                r12 = r26
                r13 = r27
                java.lang.String r14 = "com.android.internal.app.IAppOpsService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r15 = 1
                if (r11 == r0) goto L_0x03ca
                r0 = 0
                r1 = 0
                switch(r11) {
                    case 1: goto L_0x03b0;
                    case 2: goto L_0x0396;
                    case 3: goto L_0x0369;
                    case 4: goto L_0x034f;
                    case 5: goto L_0x0335;
                    case 6: goto L_0x0323;
                    case 7: goto L_0x0311;
                    case 8: goto L_0x02ff;
                    case 9: goto L_0x02e1;
                    case 10: goto L_0x02b7;
                    case 11: goto L_0x02a1;
                    case 12: goto L_0x028f;
                    case 13: goto L_0x0275;
                    case 14: goto L_0x0234;
                    case 15: goto L_0x01f3;
                    case 16: goto L_0x01e5;
                    case 17: goto L_0x01cf;
                    case 18: goto L_0x01b3;
                    case 19: goto L_0x01a9;
                    case 20: goto L_0x019f;
                    case 21: goto L_0x0189;
                    case 22: goto L_0x0173;
                    case 23: goto L_0x0159;
                    case 24: goto L_0x0147;
                    case 25: goto L_0x0121;
                    case 26: goto L_0x00fd;
                    case 27: goto L_0x00d4;
                    case 28: goto L_0x00c6;
                    case 29: goto L_0x00b0;
                    case 30: goto L_0x009e;
                    case 31: goto L_0x0084;
                    case 32: goto L_0x0066;
                    case 33: goto L_0x0050;
                    case 34: goto L_0x003e;
                    case 35: goto L_0x0024;
                    case 36: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r25, r26, r27, r28)
                return r0
            L_0x001a:
                r12.enforceInterface(r14)
                r24.reloadNonHistoricalState()
                r27.writeNoException()
                return r15
            L_0x0024:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                int r3 = r10.checkOperationRaw(r0, r1, r2)
                r27.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x003e:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsNotedCallback r0 = com.android.internal.app.IAppOpsNotedCallback.Stub.asInterface(r0)
                r10.stopWatchingNoted(r0)
                r27.writeNoException()
                return r15
            L_0x0050:
                r12.enforceInterface(r14)
                int[] r0 = r26.createIntArray()
                android.os.IBinder r1 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsNotedCallback r1 = com.android.internal.app.IAppOpsNotedCallback.Stub.asInterface(r1)
                r10.startWatchingNoted(r0, r1)
                r27.writeNoException()
                return r15
            L_0x0066:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                int r2 = r26.readInt()
                android.os.IBinder r3 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsCallback r3 = com.android.internal.app.IAppOpsCallback.Stub.asInterface(r3)
                r10.startWatchingModeWithFlags(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x0084:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                boolean r3 = r10.isOperationActive(r0, r1, r2)
                r27.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x009e:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsActiveCallback r0 = com.android.internal.app.IAppOpsActiveCallback.Stub.asInterface(r0)
                r10.stopWatchingActive(r0)
                r27.writeNoException()
                return r15
            L_0x00b0:
                r12.enforceInterface(r14)
                int[] r0 = r26.createIntArray()
                android.os.IBinder r1 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsActiveCallback r1 = com.android.internal.app.IAppOpsActiveCallback.Stub.asInterface(r1)
                r10.startWatchingActive(r0, r1)
                r27.writeNoException()
                return r15
            L_0x00c6:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                r10.removeUser(r0)
                r27.writeNoException()
                return r15
            L_0x00d4:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x00e3
                r2 = r15
                goto L_0x00e4
            L_0x00e3:
                r2 = r0
            L_0x00e4:
                android.os.IBinder r7 = r26.readStrongBinder()
                int r8 = r26.readInt()
                java.lang.String[] r9 = r26.createStringArray()
                r0 = r24
                r1 = r6
                r3 = r7
                r4 = r8
                r5 = r9
                r0.setUserRestriction(r1, r2, r3, r4, r5)
                r27.writeNoException()
                return r15
            L_0x00fd:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x0110
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0111
            L_0x0110:
            L_0x0111:
                r0 = r1
                android.os.IBinder r1 = r26.readStrongBinder()
                int r2 = r26.readInt()
                r10.setUserRestrictions(r0, r1, r2)
                r27.writeNoException()
                return r15
            L_0x0121:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                int r8 = r26.readInt()
                int r9 = r26.readInt()
                java.lang.String[] r16 = r26.createStringArray()
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                r0.setAudioRestriction(r1, r2, r3, r4, r5)
                r27.writeNoException()
                return r15
            L_0x0147:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                r10.resetAllModes(r0, r1)
                r27.writeNoException()
                return r15
            L_0x0159:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                int r3 = r26.readInt()
                r10.setMode(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x0173:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                r10.setUidMode(r0, r1, r2)
                r27.writeNoException()
                return r15
            L_0x0189:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int[] r1 = r26.createIntArray()
                java.util.List r2 = r10.getUidOps(r0, r1)
                r27.writeNoException()
                r13.writeTypedList(r2)
                return r15
            L_0x019f:
                r12.enforceInterface(r14)
                r24.clearHistory()
                r27.writeNoException()
                return r15
            L_0x01a9:
                r12.enforceInterface(r14)
                r24.resetHistoryParameters()
                r27.writeNoException()
                return r15
            L_0x01b3:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x01c6
                android.os.Parcelable$Creator<android.app.AppOpsManager$HistoricalOps> r0 = android.app.AppOpsManager.HistoricalOps.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.app.AppOpsManager$HistoricalOps r1 = (android.app.AppOpsManager.HistoricalOps) r1
                goto L_0x01c7
            L_0x01c6:
            L_0x01c7:
                r0 = r1
                r10.addHistoricalOps(r0)
                r27.writeNoException()
                return r15
            L_0x01cf:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                long r1 = r26.readLong()
                int r3 = r26.readInt()
                r10.setHistoryParameters(r0, r1, r3)
                r27.writeNoException()
                return r15
            L_0x01e5:
                r12.enforceInterface(r14)
                long r0 = r26.readLong()
                r10.offsetHistory(r0)
                r27.writeNoException()
                return r15
            L_0x01f3:
                r12.enforceInterface(r14)
                int r16 = r26.readInt()
                java.lang.String r17 = r26.readString()
                java.util.ArrayList r18 = r26.createStringArrayList()
                long r19 = r26.readLong()
                long r21 = r26.readLong()
                int r23 = r26.readInt()
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x021e
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r9 = r0
                goto L_0x021f
            L_0x021e:
                r9 = r1
            L_0x021f:
                r0 = r24
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r6 = r21
                r8 = r23
                r0.getHistoricalOpsFromDiskRaw(r1, r2, r3, r4, r6, r8, r9)
                r27.writeNoException()
                return r15
            L_0x0234:
                r12.enforceInterface(r14)
                int r16 = r26.readInt()
                java.lang.String r17 = r26.readString()
                java.util.ArrayList r18 = r26.createStringArrayList()
                long r19 = r26.readLong()
                long r21 = r26.readLong()
                int r23 = r26.readInt()
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x025f
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r9 = r0
                goto L_0x0260
            L_0x025f:
                r9 = r1
            L_0x0260:
                r0 = r24
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r6 = r21
                r8 = r23
                r0.getHistoricalOps(r1, r2, r3, r4, r6, r8, r9)
                r27.writeNoException()
                return r15
            L_0x0275:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                int[] r2 = r26.createIntArray()
                java.util.List r3 = r10.getOpsForPackage(r0, r1, r2)
                r27.writeNoException()
                r13.writeTypedList(r3)
                return r15
            L_0x028f:
                r12.enforceInterface(r14)
                int[] r0 = r26.createIntArray()
                java.util.List r1 = r10.getPackagesForOps(r0)
                r27.writeNoException()
                r13.writeTypedList(r1)
                return r15
            L_0x02a1:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                int r2 = r10.checkPackage(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x02b7:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                java.lang.String r8 = r26.readString()
                int r9 = r26.readInt()
                java.lang.String r16 = r26.readString()
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                int r0 = r0.noteProxyOperation(r1, r2, r3, r4, r5)
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x02e1:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                java.lang.String r3 = r26.readString()
                int r4 = r10.checkAudioOperation(r0, r1, r2, r3)
                r27.writeNoException()
                r13.writeInt(r4)
                return r15
            L_0x02ff:
                r12.enforceInterface(r14)
                java.lang.String r0 = r26.readString()
                int r1 = r10.permissionToOpCode(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0311:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.os.IBinder r1 = r10.getToken(r0)
                r27.writeNoException()
                r13.writeStrongBinder(r1)
                return r15
            L_0x0323:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsCallback r0 = com.android.internal.app.IAppOpsCallback.Stub.asInterface(r0)
                r10.stopWatchingMode(r0)
                r27.writeNoException()
                return r15
            L_0x0335:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                android.os.IBinder r2 = r26.readStrongBinder()
                com.android.internal.app.IAppOpsCallback r2 = com.android.internal.app.IAppOpsCallback.Stub.asInterface(r2)
                r10.startWatchingMode(r0, r1, r2)
                r27.writeNoException()
                return r15
            L_0x034f:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                java.lang.String r3 = r26.readString()
                r10.finishOperation(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x0369:
                r12.enforceInterface(r14)
                android.os.IBinder r6 = r26.readStrongBinder()
                int r7 = r26.readInt()
                int r8 = r26.readInt()
                java.lang.String r9 = r26.readString()
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0384
                r5 = r15
                goto L_0x0385
            L_0x0384:
                r5 = r0
            L_0x0385:
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                int r0 = r0.startOperation(r1, r2, r3, r4, r5)
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0396:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                int r3 = r10.noteOperation(r0, r1, r2)
                r27.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x03b0:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                int r3 = r10.checkOperation(r0, r1, r2)
                r27.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x03ca:
                r13.writeString(r14)
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.IAppOpsService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAppOpsService {
            public static IAppOpsService sDefaultImpl;
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

            public int checkOperation(int code, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkOperation(code, uid, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int noteOperation(int code, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().noteOperation(code, uid, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startOperation(IBinder token, int code, int uid, String packageName, boolean startIfModeDefault) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeInt(startIfModeDefault);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startOperation(token, code, uid, packageName, startIfModeDefault);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishOperation(IBinder token, int code, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishOperation(token, code, uid, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startWatchingMode(int op, String packageName, IAppOpsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(op);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWatchingMode(op, packageName, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWatchingMode(IAppOpsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWatchingMode(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBinder getToken(IBinder clientToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getToken(clientToken);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int permissionToOpCode(String permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().permissionToOpCode(permission);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkAudioOperation(int code, int usage, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(usage);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkAudioOperation(code, usage, uid, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int noteProxyOperation(int code, int proxyUid, String proxyPackageName, int callingUid, String callingPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(proxyUid);
                    _data.writeString(proxyPackageName);
                    _data.writeInt(callingUid);
                    _data.writeString(callingPackageName);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().noteProxyOperation(code, proxyUid, proxyPackageName, callingUid, callingPackageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkPackage(int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkPackage(uid, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AppOpsManager.PackageOps> getPackagesForOps(int[] ops) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(ops);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesForOps(ops);
                    }
                    _reply.readException();
                    List<AppOpsManager.PackageOps> _result = _reply.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AppOpsManager.PackageOps> getOpsForPackage(int uid, String packageName, int[] ops) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeIntArray(ops);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOpsForPackage(uid, packageName, ops);
                    }
                    _reply.readException();
                    List<AppOpsManager.PackageOps> _result = _reply.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getHistoricalOps(int uid, String packageName, List<String> ops, long beginTimeMillis, long endTimeMillis, int flags, RemoteCallback callback) throws RemoteException {
                RemoteCallback remoteCallback = callback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(uid);
                        try {
                            _data.writeString(packageName);
                            _data.writeStringList(ops);
                            _data.writeLong(beginTimeMillis);
                            _data.writeLong(endTimeMillis);
                            _data.writeInt(flags);
                            if (remoteCallback != null) {
                                _data.writeInt(1);
                                remoteCallback.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().getHistoricalOps(uid, packageName, ops, beginTimeMillis, endTimeMillis, flags, callback);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = packageName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = uid;
                    String str2 = packageName;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void getHistoricalOpsFromDiskRaw(int uid, String packageName, List<String> ops, long beginTimeMillis, long endTimeMillis, int flags, RemoteCallback callback) throws RemoteException {
                RemoteCallback remoteCallback = callback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(uid);
                        try {
                            _data.writeString(packageName);
                            _data.writeStringList(ops);
                            _data.writeLong(beginTimeMillis);
                            _data.writeLong(endTimeMillis);
                            _data.writeInt(flags);
                            if (remoteCallback != null) {
                                _data.writeInt(1);
                                remoteCallback.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().getHistoricalOpsFromDiskRaw(uid, packageName, ops, beginTimeMillis, endTimeMillis, flags, callback);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = packageName;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = uid;
                    String str2 = packageName;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void offsetHistory(long duration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(duration);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().offsetHistory(duration);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setHistoryParameters(int mode, long baseSnapshotInterval, int compressionStep) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeLong(baseSnapshotInterval);
                    _data.writeInt(compressionStep);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setHistoryParameters(mode, baseSnapshotInterval, compressionStep);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addHistoricalOps(AppOpsManager.HistoricalOps ops) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ops != null) {
                        _data.writeInt(1);
                        ops.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addHistoricalOps(ops);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetHistoryParameters() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetHistoryParameters();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearHistory() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearHistory();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AppOpsManager.PackageOps> getUidOps(int uid, int[] ops) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeIntArray(ops);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidOps(uid, ops);
                    }
                    _reply.readException();
                    List<AppOpsManager.PackageOps> _result = _reply.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUidMode(int code, int uid, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUidMode(code, uid, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMode(int code, int uid, String packageName, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMode(code, uid, packageName, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetAllModes(int reqUserId, String reqPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reqUserId);
                    _data.writeString(reqPackageName);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetAllModes(reqUserId, reqPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAudioRestriction(int code, int usage, int uid, int mode, String[] exceptionPackages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(usage);
                    _data.writeInt(uid);
                    _data.writeInt(mode);
                    _data.writeStringArray(exceptionPackages);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAudioRestriction(code, usage, uid, mode, exceptionPackages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserRestrictions(Bundle restrictions, IBinder token, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (restrictions != null) {
                        _data.writeInt(1);
                        restrictions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(token);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserRestrictions(restrictions, token, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserRestriction(int code, boolean restricted, IBinder token, int userHandle, String[] exceptionPackages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(restricted);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userHandle);
                    _data.writeStringArray(exceptionPackages);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserRestriction(code, restricted, token, userHandle, exceptionPackages);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeUser(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startWatchingActive(int[] ops, IAppOpsActiveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(ops);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWatchingActive(ops, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWatchingActive(IAppOpsActiveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWatchingActive(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOperationActive(int code, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOperationActive(code, uid, packageName);
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

            public void startWatchingModeWithFlags(int op, String packageName, int flags, IAppOpsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(op);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWatchingModeWithFlags(op, packageName, flags, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startWatchingNoted(int[] ops, IAppOpsNotedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(ops);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startWatchingNoted(ops, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWatchingNoted(IAppOpsNotedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWatchingNoted(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkOperationRaw(int code, int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkOperationRaw(code, uid, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reloadNonHistoricalState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reloadNonHistoricalState();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAppOpsService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAppOpsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
