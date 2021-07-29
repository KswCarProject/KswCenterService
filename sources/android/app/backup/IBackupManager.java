package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.IRestoreSession;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;

public interface IBackupManager extends IInterface {
    @UnsupportedAppUsage
    void acknowledgeFullBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException;

    void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException;

    void adbBackup(int i, ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) throws RemoteException;

    void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void agentConnected(String str, IBinder iBinder) throws RemoteException;

    void agentConnectedForUser(int i, String str, IBinder iBinder) throws RemoteException;

    void agentDisconnected(String str) throws RemoteException;

    void agentDisconnectedForUser(int i, String str) throws RemoteException;

    void backupNow() throws RemoteException;

    void backupNowForUser(int i) throws RemoteException;

    IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) throws RemoteException;

    void cancelBackups() throws RemoteException;

    void cancelBackupsForUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    void clearBackupData(String str, String str2) throws RemoteException;

    void clearBackupDataForUser(int i, String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void dataChanged(String str) throws RemoteException;

    void dataChangedForUser(int i, String str) throws RemoteException;

    String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) throws RemoteException;

    void fullTransportBackupForUser(int i, String[] strArr) throws RemoteException;

    long getAvailableRestoreTokenForUser(int i, String str) throws RemoteException;

    Intent getConfigurationIntent(String str) throws RemoteException;

    Intent getConfigurationIntentForUser(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    String getCurrentTransport() throws RemoteException;

    ComponentName getCurrentTransportComponentForUser(int i) throws RemoteException;

    String getCurrentTransportForUser(int i) throws RemoteException;

    Intent getDataManagementIntent(String str) throws RemoteException;

    Intent getDataManagementIntentForUser(int i, String str) throws RemoteException;

    CharSequence getDataManagementLabelForUser(int i, String str) throws RemoteException;

    String getDestinationString(String str) throws RemoteException;

    String getDestinationStringForUser(int i, String str) throws RemoteException;

    String[] getTransportWhitelist() throws RemoteException;

    UserHandle getUserForAncestralSerialNumber(long j) throws RemoteException;

    boolean hasBackupPassword() throws RemoteException;

    void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) throws RemoteException;

    boolean isAppEligibleForBackupForUser(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean isBackupEnabled() throws RemoteException;

    boolean isBackupEnabledForUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean isBackupServiceActive(int i) throws RemoteException;

    ComponentName[] listAllTransportComponentsForUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    String[] listAllTransports() throws RemoteException;

    String[] listAllTransportsForUser(int i) throws RemoteException;

    void opComplete(int i, long j) throws RemoteException;

    void opCompleteForUser(int i, int i2, long j) throws RemoteException;

    int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) throws RemoteException;

    int requestBackupForUser(int i, String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i2) throws RemoteException;

    void restoreAtInstall(String str, int i) throws RemoteException;

    void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException;

    @UnsupportedAppUsage
    String selectBackupTransport(String str) throws RemoteException;

    void selectBackupTransportAsyncForUser(int i, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException;

    String selectBackupTransportForUser(int i, String str) throws RemoteException;

    void setAncestralSerialNumber(long j) throws RemoteException;

    @UnsupportedAppUsage
    void setAutoRestore(boolean z) throws RemoteException;

    void setAutoRestoreForUser(int i, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setBackupEnabled(boolean z) throws RemoteException;

    void setBackupEnabledForUser(int i, boolean z) throws RemoteException;

    boolean setBackupPassword(String str, String str2) throws RemoteException;

    void setBackupServiceActive(int i, boolean z) throws RemoteException;

    void updateTransportAttributesForUser(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) throws RemoteException;

    public static class Default implements IBackupManager {
        public void dataChangedForUser(int userId, String packageName) throws RemoteException {
        }

        public void dataChanged(String packageName) throws RemoteException {
        }

        public void clearBackupDataForUser(int userId, String transportName, String packageName) throws RemoteException {
        }

        public void clearBackupData(String transportName, String packageName) throws RemoteException {
        }

        public void initializeTransportsForUser(int userId, String[] transportNames, IBackupObserver observer) throws RemoteException {
        }

        public void agentConnectedForUser(int userId, String packageName, IBinder agent) throws RemoteException {
        }

        public void agentConnected(String packageName, IBinder agent) throws RemoteException {
        }

        public void agentDisconnectedForUser(int userId, String packageName) throws RemoteException {
        }

        public void agentDisconnected(String packageName) throws RemoteException {
        }

        public void restoreAtInstallForUser(int userId, String packageName, int token) throws RemoteException {
        }

        public void restoreAtInstall(String packageName, int token) throws RemoteException {
        }

        public void setBackupEnabledForUser(int userId, boolean isEnabled) throws RemoteException {
        }

        public void setBackupEnabled(boolean isEnabled) throws RemoteException {
        }

        public void setAutoRestoreForUser(int userId, boolean doAutoRestore) throws RemoteException {
        }

        public void setAutoRestore(boolean doAutoRestore) throws RemoteException {
        }

        public boolean isBackupEnabledForUser(int userId) throws RemoteException {
            return false;
        }

        public boolean isBackupEnabled() throws RemoteException {
            return false;
        }

        public boolean setBackupPassword(String currentPw, String newPw) throws RemoteException {
            return false;
        }

        public boolean hasBackupPassword() throws RemoteException {
            return false;
        }

        public void backupNowForUser(int userId) throws RemoteException {
        }

        public void backupNow() throws RemoteException {
        }

        public void adbBackup(int userId, ParcelFileDescriptor fd, boolean includeApks, boolean includeObbs, boolean includeShared, boolean doWidgets, boolean allApps, boolean allIncludesSystem, boolean doCompress, boolean doKeyValue, String[] packageNames) throws RemoteException {
        }

        public void fullTransportBackupForUser(int userId, String[] packageNames) throws RemoteException {
        }

        public void adbRestore(int userId, ParcelFileDescriptor fd) throws RemoteException {
        }

        public void acknowledgeFullBackupOrRestoreForUser(int userId, int token, boolean allow, String curPassword, String encryptionPassword, IFullBackupRestoreObserver observer) throws RemoteException {
        }

        public void acknowledgeFullBackupOrRestore(int token, boolean allow, String curPassword, String encryptionPassword, IFullBackupRestoreObserver observer) throws RemoteException {
        }

        public void updateTransportAttributesForUser(int userId, ComponentName transportComponent, String name, Intent configurationIntent, String currentDestinationString, Intent dataManagementIntent, CharSequence dataManagementLabel) throws RemoteException {
        }

        public String getCurrentTransportForUser(int userId) throws RemoteException {
            return null;
        }

        public String getCurrentTransport() throws RemoteException {
            return null;
        }

        public ComponentName getCurrentTransportComponentForUser(int userId) throws RemoteException {
            return null;
        }

        public String[] listAllTransportsForUser(int userId) throws RemoteException {
            return null;
        }

        public String[] listAllTransports() throws RemoteException {
            return null;
        }

        public ComponentName[] listAllTransportComponentsForUser(int userId) throws RemoteException {
            return null;
        }

        public String[] getTransportWhitelist() throws RemoteException {
            return null;
        }

        public String selectBackupTransportForUser(int userId, String transport) throws RemoteException {
            return null;
        }

        public String selectBackupTransport(String transport) throws RemoteException {
            return null;
        }

        public void selectBackupTransportAsyncForUser(int userId, ComponentName transport, ISelectBackupTransportCallback listener) throws RemoteException {
        }

        public Intent getConfigurationIntentForUser(int userId, String transport) throws RemoteException {
            return null;
        }

        public Intent getConfigurationIntent(String transport) throws RemoteException {
            return null;
        }

        public String getDestinationStringForUser(int userId, String transport) throws RemoteException {
            return null;
        }

        public String getDestinationString(String transport) throws RemoteException {
            return null;
        }

        public Intent getDataManagementIntentForUser(int userId, String transport) throws RemoteException {
            return null;
        }

        public Intent getDataManagementIntent(String transport) throws RemoteException {
            return null;
        }

        public CharSequence getDataManagementLabelForUser(int userId, String transport) throws RemoteException {
            return null;
        }

        public IRestoreSession beginRestoreSessionForUser(int userId, String packageName, String transportID) throws RemoteException {
            return null;
        }

        public void opCompleteForUser(int userId, int token, long result) throws RemoteException {
        }

        public void opComplete(int token, long result) throws RemoteException {
        }

        public void setBackupServiceActive(int whichUser, boolean makeActive) throws RemoteException {
        }

        public boolean isBackupServiceActive(int whichUser) throws RemoteException {
            return false;
        }

        public long getAvailableRestoreTokenForUser(int userId, String packageName) throws RemoteException {
            return 0;
        }

        public boolean isAppEligibleForBackupForUser(int userId, String packageName) throws RemoteException {
            return false;
        }

        public String[] filterAppsEligibleForBackupForUser(int userId, String[] packages) throws RemoteException {
            return null;
        }

        public int requestBackupForUser(int userId, String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags) throws RemoteException {
            return 0;
        }

        public int requestBackup(String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags) throws RemoteException {
            return 0;
        }

        public void cancelBackupsForUser(int userId) throws RemoteException {
        }

        public void cancelBackups() throws RemoteException {
        }

        public UserHandle getUserForAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {
            return null;
        }

        public void setAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBackupManager {
        private static final String DESCRIPTOR = "android.app.backup.IBackupManager";
        static final int TRANSACTION_acknowledgeFullBackupOrRestore = 26;
        static final int TRANSACTION_acknowledgeFullBackupOrRestoreForUser = 25;
        static final int TRANSACTION_adbBackup = 22;
        static final int TRANSACTION_adbRestore = 24;
        static final int TRANSACTION_agentConnected = 7;
        static final int TRANSACTION_agentConnectedForUser = 6;
        static final int TRANSACTION_agentDisconnected = 9;
        static final int TRANSACTION_agentDisconnectedForUser = 8;
        static final int TRANSACTION_backupNow = 21;
        static final int TRANSACTION_backupNowForUser = 20;
        static final int TRANSACTION_beginRestoreSessionForUser = 45;
        static final int TRANSACTION_cancelBackups = 56;
        static final int TRANSACTION_cancelBackupsForUser = 55;
        static final int TRANSACTION_clearBackupData = 4;
        static final int TRANSACTION_clearBackupDataForUser = 3;
        static final int TRANSACTION_dataChanged = 2;
        static final int TRANSACTION_dataChangedForUser = 1;
        static final int TRANSACTION_filterAppsEligibleForBackupForUser = 52;
        static final int TRANSACTION_fullTransportBackupForUser = 23;
        static final int TRANSACTION_getAvailableRestoreTokenForUser = 50;
        static final int TRANSACTION_getConfigurationIntent = 39;
        static final int TRANSACTION_getConfigurationIntentForUser = 38;
        static final int TRANSACTION_getCurrentTransport = 29;
        static final int TRANSACTION_getCurrentTransportComponentForUser = 30;
        static final int TRANSACTION_getCurrentTransportForUser = 28;
        static final int TRANSACTION_getDataManagementIntent = 43;
        static final int TRANSACTION_getDataManagementIntentForUser = 42;
        static final int TRANSACTION_getDataManagementLabelForUser = 44;
        static final int TRANSACTION_getDestinationString = 41;
        static final int TRANSACTION_getDestinationStringForUser = 40;
        static final int TRANSACTION_getTransportWhitelist = 34;
        static final int TRANSACTION_getUserForAncestralSerialNumber = 57;
        static final int TRANSACTION_hasBackupPassword = 19;
        static final int TRANSACTION_initializeTransportsForUser = 5;
        static final int TRANSACTION_isAppEligibleForBackupForUser = 51;
        static final int TRANSACTION_isBackupEnabled = 17;
        static final int TRANSACTION_isBackupEnabledForUser = 16;
        static final int TRANSACTION_isBackupServiceActive = 49;
        static final int TRANSACTION_listAllTransportComponentsForUser = 33;
        static final int TRANSACTION_listAllTransports = 32;
        static final int TRANSACTION_listAllTransportsForUser = 31;
        static final int TRANSACTION_opComplete = 47;
        static final int TRANSACTION_opCompleteForUser = 46;
        static final int TRANSACTION_requestBackup = 54;
        static final int TRANSACTION_requestBackupForUser = 53;
        static final int TRANSACTION_restoreAtInstall = 11;
        static final int TRANSACTION_restoreAtInstallForUser = 10;
        static final int TRANSACTION_selectBackupTransport = 36;
        static final int TRANSACTION_selectBackupTransportAsyncForUser = 37;
        static final int TRANSACTION_selectBackupTransportForUser = 35;
        static final int TRANSACTION_setAncestralSerialNumber = 58;
        static final int TRANSACTION_setAutoRestore = 15;
        static final int TRANSACTION_setAutoRestoreForUser = 14;
        static final int TRANSACTION_setBackupEnabled = 13;
        static final int TRANSACTION_setBackupEnabledForUser = 12;
        static final int TRANSACTION_setBackupPassword = 18;
        static final int TRANSACTION_setBackupServiceActive = 48;
        static final int TRANSACTION_updateTransportAttributesForUser = 27;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBackupManager)) {
                return new Proxy(obj);
            }
            return (IBackupManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "dataChangedForUser";
                case 2:
                    return "dataChanged";
                case 3:
                    return "clearBackupDataForUser";
                case 4:
                    return "clearBackupData";
                case 5:
                    return "initializeTransportsForUser";
                case 6:
                    return "agentConnectedForUser";
                case 7:
                    return "agentConnected";
                case 8:
                    return "agentDisconnectedForUser";
                case 9:
                    return "agentDisconnected";
                case 10:
                    return "restoreAtInstallForUser";
                case 11:
                    return "restoreAtInstall";
                case 12:
                    return "setBackupEnabledForUser";
                case 13:
                    return "setBackupEnabled";
                case 14:
                    return "setAutoRestoreForUser";
                case 15:
                    return "setAutoRestore";
                case 16:
                    return "isBackupEnabledForUser";
                case 17:
                    return "isBackupEnabled";
                case 18:
                    return "setBackupPassword";
                case 19:
                    return "hasBackupPassword";
                case 20:
                    return "backupNowForUser";
                case 21:
                    return "backupNow";
                case 22:
                    return "adbBackup";
                case 23:
                    return "fullTransportBackupForUser";
                case 24:
                    return "adbRestore";
                case 25:
                    return "acknowledgeFullBackupOrRestoreForUser";
                case 26:
                    return "acknowledgeFullBackupOrRestore";
                case 27:
                    return "updateTransportAttributesForUser";
                case 28:
                    return "getCurrentTransportForUser";
                case 29:
                    return "getCurrentTransport";
                case 30:
                    return "getCurrentTransportComponentForUser";
                case 31:
                    return "listAllTransportsForUser";
                case 32:
                    return "listAllTransports";
                case 33:
                    return "listAllTransportComponentsForUser";
                case 34:
                    return "getTransportWhitelist";
                case 35:
                    return "selectBackupTransportForUser";
                case 36:
                    return "selectBackupTransport";
                case 37:
                    return "selectBackupTransportAsyncForUser";
                case 38:
                    return "getConfigurationIntentForUser";
                case 39:
                    return "getConfigurationIntent";
                case 40:
                    return "getDestinationStringForUser";
                case 41:
                    return "getDestinationString";
                case 42:
                    return "getDataManagementIntentForUser";
                case 43:
                    return "getDataManagementIntent";
                case 44:
                    return "getDataManagementLabelForUser";
                case 45:
                    return "beginRestoreSessionForUser";
                case 46:
                    return "opCompleteForUser";
                case 47:
                    return "opComplete";
                case 48:
                    return "setBackupServiceActive";
                case 49:
                    return "isBackupServiceActive";
                case 50:
                    return "getAvailableRestoreTokenForUser";
                case 51:
                    return "isAppEligibleForBackupForUser";
                case 52:
                    return "filterAppsEligibleForBackupForUser";
                case 53:
                    return "requestBackupForUser";
                case 54:
                    return "requestBackup";
                case 55:
                    return "cancelBackupsForUser";
                case 56:
                    return "cancelBackups";
                case 57:
                    return "getUserForAncestralSerialNumber";
                case 58:
                    return "setAncestralSerialNumber";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v62, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r0v38, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r0v48 */
        /* JADX WARNING: type inference failed for: r0v73, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v91 */
        /* JADX WARNING: type inference failed for: r0v92 */
        /* JADX WARNING: type inference failed for: r0v93 */
        /* JADX WARNING: type inference failed for: r0v94 */
        /* JADX WARNING: type inference failed for: r0v95 */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r12 = r19
                r13 = r20
                r14 = r21
                r15 = r22
                java.lang.String r11 = "android.app.backup.IBackupManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r13 == r0) goto L_0x0612
                r0 = 0
                r1 = 0
                switch(r13) {
                    case 1: goto L_0x05fd;
                    case 2: goto L_0x05ec;
                    case 3: goto L_0x05d3;
                    case 4: goto L_0x05be;
                    case 5: goto L_0x05a1;
                    case 6: goto L_0x0588;
                    case 7: goto L_0x0573;
                    case 8: goto L_0x055e;
                    case 9: goto L_0x054d;
                    case 10: goto L_0x0534;
                    case 11: goto L_0x051f;
                    case 12: goto L_0x0505;
                    case 13: goto L_0x04ee;
                    case 14: goto L_0x04d4;
                    case 15: goto L_0x04bd;
                    case 16: goto L_0x04a8;
                    case 17: goto L_0x0497;
                    case 18: goto L_0x047e;
                    case 19: goto L_0x046d;
                    case 20: goto L_0x045c;
                    case 21: goto L_0x044f;
                    case 22: goto L_0x03db;
                    case 23: goto L_0x03c9;
                    case 24: goto L_0x03ab;
                    case 25: goto L_0x0377;
                    case 26: goto L_0x034a;
                    case 27: goto L_0x02ea;
                    case 28: goto L_0x02d8;
                    case 29: goto L_0x02ca;
                    case 30: goto L_0x02af;
                    case 31: goto L_0x029d;
                    case 32: goto L_0x028f;
                    case 33: goto L_0x027d;
                    case 34: goto L_0x026f;
                    case 35: goto L_0x0259;
                    case 36: goto L_0x0247;
                    case 37: goto L_0x0221;
                    case 38: goto L_0x0202;
                    case 39: goto L_0x01e7;
                    case 40: goto L_0x01d1;
                    case 41: goto L_0x01bf;
                    case 42: goto L_0x01a0;
                    case 43: goto L_0x0185;
                    case 44: goto L_0x0166;
                    case 45: goto L_0x0145;
                    case 46: goto L_0x012f;
                    case 47: goto L_0x011d;
                    case 48: goto L_0x0107;
                    case 49: goto L_0x00f5;
                    case 50: goto L_0x00df;
                    case 51: goto L_0x00c9;
                    case 52: goto L_0x00b3;
                    case 53: goto L_0x0081;
                    case 54: goto L_0x005b;
                    case 55: goto L_0x004d;
                    case 56: goto L_0x0043;
                    case 57: goto L_0x0028;
                    case 58: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r14.enforceInterface(r11)
                long r0 = r21.readLong()
                r12.setAncestralSerialNumber(r0)
                r22.writeNoException()
                return r10
            L_0x0028:
                r14.enforceInterface(r11)
                long r2 = r21.readLong()
                android.os.UserHandle r0 = r12.getUserForAncestralSerialNumber(r2)
                r22.writeNoException()
                if (r0 == 0) goto L_0x003f
                r15.writeInt(r10)
                r0.writeToParcel((android.os.Parcel) r15, (int) r10)
                goto L_0x0042
            L_0x003f:
                r15.writeInt(r1)
            L_0x0042:
                return r10
            L_0x0043:
                r14.enforceInterface(r11)
                r19.cancelBackups()
                r22.writeNoException()
                return r10
            L_0x004d:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                r12.cancelBackupsForUser(r0)
                r22.writeNoException()
                return r10
            L_0x005b:
                r14.enforceInterface(r11)
                java.lang.String[] r0 = r21.createStringArray()
                android.os.IBinder r1 = r21.readStrongBinder()
                android.app.backup.IBackupObserver r1 = android.app.backup.IBackupObserver.Stub.asInterface(r1)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.app.backup.IBackupManagerMonitor r2 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(r2)
                int r3 = r21.readInt()
                int r4 = r12.requestBackup(r0, r1, r2, r3)
                r22.writeNoException()
                r15.writeInt(r4)
                return r10
            L_0x0081:
                r14.enforceInterface(r11)
                int r6 = r21.readInt()
                java.lang.String[] r7 = r21.createStringArray()
                android.os.IBinder r0 = r21.readStrongBinder()
                android.app.backup.IBackupObserver r8 = android.app.backup.IBackupObserver.Stub.asInterface(r0)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.app.backup.IBackupManagerMonitor r9 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(r0)
                int r16 = r21.readInt()
                r0 = r19
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                int r0 = r0.requestBackupForUser(r1, r2, r3, r4, r5)
                r22.writeNoException()
                r15.writeInt(r0)
                return r10
            L_0x00b3:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String[] r1 = r21.createStringArray()
                java.lang.String[] r2 = r12.filterAppsEligibleForBackupForUser(r0, r1)
                r22.writeNoException()
                r15.writeStringArray(r2)
                return r10
            L_0x00c9:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                boolean r2 = r12.isAppEligibleForBackupForUser(r0, r1)
                r22.writeNoException()
                r15.writeInt(r2)
                return r10
            L_0x00df:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                long r2 = r12.getAvailableRestoreTokenForUser(r0, r1)
                r22.writeNoException()
                r15.writeLong(r2)
                return r10
            L_0x00f5:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                boolean r1 = r12.isBackupServiceActive(r0)
                r22.writeNoException()
                r15.writeInt(r1)
                return r10
            L_0x0107:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0116
                r1 = r10
            L_0x0116:
                r12.setBackupServiceActive(r0, r1)
                r22.writeNoException()
                return r10
            L_0x011d:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                long r1 = r21.readLong()
                r12.opComplete(r0, r1)
                r22.writeNoException()
                return r10
            L_0x012f:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                long r2 = r21.readLong()
                r12.opCompleteForUser(r0, r1, r2)
                r22.writeNoException()
                return r10
            L_0x0145:
                r14.enforceInterface(r11)
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                java.lang.String r3 = r21.readString()
                android.app.backup.IRestoreSession r4 = r12.beginRestoreSessionForUser(r1, r2, r3)
                r22.writeNoException()
                if (r4 == 0) goto L_0x0162
                android.os.IBinder r0 = r4.asBinder()
            L_0x0162:
                r15.writeStrongBinder(r0)
                return r10
            L_0x0166:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                java.lang.CharSequence r3 = r12.getDataManagementLabelForUser(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0181
                r15.writeInt(r10)
                android.text.TextUtils.writeToParcel(r3, r15, r10)
                goto L_0x0184
            L_0x0181:
                r15.writeInt(r1)
            L_0x0184:
                return r10
            L_0x0185:
                r14.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                android.content.Intent r2 = r12.getDataManagementIntent(r0)
                r22.writeNoException()
                if (r2 == 0) goto L_0x019c
                r15.writeInt(r10)
                r2.writeToParcel(r15, r10)
                goto L_0x019f
            L_0x019c:
                r15.writeInt(r1)
            L_0x019f:
                return r10
            L_0x01a0:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.content.Intent r3 = r12.getDataManagementIntentForUser(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x01bb
                r15.writeInt(r10)
                r3.writeToParcel(r15, r10)
                goto L_0x01be
            L_0x01bb:
                r15.writeInt(r1)
            L_0x01be:
                return r10
            L_0x01bf:
                r14.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r12.getDestinationString(r0)
                r22.writeNoException()
                r15.writeString(r1)
                return r10
            L_0x01d1:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r12.getDestinationStringForUser(r0, r1)
                r22.writeNoException()
                r15.writeString(r2)
                return r10
            L_0x01e7:
                r14.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                android.content.Intent r2 = r12.getConfigurationIntent(r0)
                r22.writeNoException()
                if (r2 == 0) goto L_0x01fe
                r15.writeInt(r10)
                r2.writeToParcel(r15, r10)
                goto L_0x0201
            L_0x01fe:
                r15.writeInt(r1)
            L_0x0201:
                return r10
            L_0x0202:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.content.Intent r3 = r12.getConfigurationIntentForUser(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x021d
                r15.writeInt(r10)
                r3.writeToParcel(r15, r10)
                goto L_0x0220
            L_0x021d:
                r15.writeInt(r1)
            L_0x0220:
                return r10
            L_0x0221:
                r14.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0237
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x0238
            L_0x0237:
            L_0x0238:
                android.os.IBinder r2 = r21.readStrongBinder()
                android.app.backup.ISelectBackupTransportCallback r2 = android.app.backup.ISelectBackupTransportCallback.Stub.asInterface(r2)
                r12.selectBackupTransportAsyncForUser(r1, r0, r2)
                r22.writeNoException()
                return r10
            L_0x0247:
                r14.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r12.selectBackupTransport(r0)
                r22.writeNoException()
                r15.writeString(r1)
                return r10
            L_0x0259:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r12.selectBackupTransportForUser(r0, r1)
                r22.writeNoException()
                r15.writeString(r2)
                return r10
            L_0x026f:
                r14.enforceInterface(r11)
                java.lang.String[] r0 = r19.getTransportWhitelist()
                r22.writeNoException()
                r15.writeStringArray(r0)
                return r10
            L_0x027d:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                android.content.ComponentName[] r1 = r12.listAllTransportComponentsForUser(r0)
                r22.writeNoException()
                r15.writeTypedArray(r1, r10)
                return r10
            L_0x028f:
                r14.enforceInterface(r11)
                java.lang.String[] r0 = r19.listAllTransports()
                r22.writeNoException()
                r15.writeStringArray(r0)
                return r10
            L_0x029d:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String[] r1 = r12.listAllTransportsForUser(r0)
                r22.writeNoException()
                r15.writeStringArray(r1)
                return r10
            L_0x02af:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                android.content.ComponentName r2 = r12.getCurrentTransportComponentForUser(r0)
                r22.writeNoException()
                if (r2 == 0) goto L_0x02c6
                r15.writeInt(r10)
                r2.writeToParcel((android.os.Parcel) r15, (int) r10)
                goto L_0x02c9
            L_0x02c6:
                r15.writeInt(r1)
            L_0x02c9:
                return r10
            L_0x02ca:
                r14.enforceInterface(r11)
                java.lang.String r0 = r19.getCurrentTransport()
                r22.writeNoException()
                r15.writeString(r0)
                return r10
            L_0x02d8:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r12.getCurrentTransportForUser(r0)
                r22.writeNoException()
                r15.writeString(r1)
                return r10
            L_0x02ea:
                r14.enforceInterface(r11)
                int r8 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0301
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                r2 = r1
                goto L_0x0302
            L_0x0301:
                r2 = r0
            L_0x0302:
                java.lang.String r9 = r21.readString()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0316
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.content.Intent r1 = (android.content.Intent) r1
                r4 = r1
                goto L_0x0317
            L_0x0316:
                r4 = r0
            L_0x0317:
                java.lang.String r16 = r21.readString()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x032b
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.content.Intent r1 = (android.content.Intent) r1
                r6 = r1
                goto L_0x032c
            L_0x032b:
                r6 = r0
            L_0x032c:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x033c
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            L_0x033a:
                r7 = r0
                goto L_0x033d
            L_0x033c:
                goto L_0x033a
            L_0x033d:
                r0 = r19
                r1 = r8
                r3 = r9
                r5 = r16
                r0.updateTransportAttributesForUser(r1, r2, r3, r4, r5, r6, r7)
                r22.writeNoException()
                return r10
            L_0x034a:
                r14.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0359
                r2 = r10
                goto L_0x035a
            L_0x0359:
                r2 = r1
            L_0x035a:
                java.lang.String r7 = r21.readString()
                java.lang.String r8 = r21.readString()
                android.os.IBinder r0 = r21.readStrongBinder()
                android.app.backup.IFullBackupRestoreObserver r9 = android.app.backup.IFullBackupRestoreObserver.Stub.asInterface(r0)
                r0 = r19
                r1 = r6
                r3 = r7
                r4 = r8
                r5 = r9
                r0.acknowledgeFullBackupOrRestore(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r10
            L_0x0377:
                r14.enforceInterface(r11)
                int r7 = r21.readInt()
                int r8 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x038a
                r3 = r10
                goto L_0x038b
            L_0x038a:
                r3 = r1
            L_0x038b:
                java.lang.String r9 = r21.readString()
                java.lang.String r16 = r21.readString()
                android.os.IBinder r0 = r21.readStrongBinder()
                android.app.backup.IFullBackupRestoreObserver r17 = android.app.backup.IFullBackupRestoreObserver.Stub.asInterface(r0)
                r0 = r19
                r1 = r7
                r2 = r8
                r4 = r9
                r5 = r16
                r6 = r17
                r0.acknowledgeFullBackupOrRestoreForUser(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                return r10
            L_0x03ab:
                r14.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x03c1
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
                goto L_0x03c2
            L_0x03c1:
            L_0x03c2:
                r12.adbRestore(r1, r0)
                r22.writeNoException()
                return r10
            L_0x03c9:
                r14.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String[] r1 = r21.createStringArray()
                r12.fullTransportBackupForUser(r0, r1)
                r22.writeNoException()
                return r10
            L_0x03db:
                r14.enforceInterface(r11)
                int r16 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x03f2
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r0 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0
            L_0x03f0:
                r2 = r0
                goto L_0x03f3
            L_0x03f2:
                goto L_0x03f0
            L_0x03f3:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x03fb
                r3 = r10
                goto L_0x03fc
            L_0x03fb:
                r3 = r1
            L_0x03fc:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0404
                r4 = r10
                goto L_0x0405
            L_0x0404:
                r4 = r1
            L_0x0405:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x040d
                r5 = r10
                goto L_0x040e
            L_0x040d:
                r5 = r1
            L_0x040e:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0416
                r6 = r10
                goto L_0x0417
            L_0x0416:
                r6 = r1
            L_0x0417:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x041f
                r7 = r10
                goto L_0x0420
            L_0x041f:
                r7 = r1
            L_0x0420:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0428
                r8 = r10
                goto L_0x0429
            L_0x0428:
                r8 = r1
            L_0x0429:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0431
                r9 = r10
                goto L_0x0432
            L_0x0431:
                r9 = r1
            L_0x0432:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x043a
                r1 = r10
            L_0x043a:
                r17 = r10
                r10 = r1
                java.lang.String[] r18 = r21.createStringArray()
                r0 = r19
                r1 = r16
                r13 = r11
                r11 = r18
                r0.adbBackup(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r22.writeNoException()
                return r17
            L_0x044f:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                r19.backupNow()
                r22.writeNoException()
                return r17
            L_0x045c:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                r12.backupNowForUser(r0)
                r22.writeNoException()
                return r17
            L_0x046d:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                boolean r0 = r19.hasBackupPassword()
                r22.writeNoException()
                r15.writeInt(r0)
                return r17
            L_0x047e:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                boolean r2 = r12.setBackupPassword(r0, r1)
                r22.writeNoException()
                r15.writeInt(r2)
                return r17
            L_0x0497:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                boolean r0 = r19.isBackupEnabled()
                r22.writeNoException()
                r15.writeInt(r0)
                return r17
            L_0x04a8:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                boolean r1 = r12.isBackupEnabledForUser(r0)
                r22.writeNoException()
                r15.writeInt(r1)
                return r17
            L_0x04bd:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04cc
                r1 = r17
            L_0x04cc:
                r0 = r1
                r12.setAutoRestore(r0)
                r22.writeNoException()
                return r17
            L_0x04d4:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x04e7
                r1 = r17
            L_0x04e7:
                r12.setAutoRestoreForUser(r0, r1)
                r22.writeNoException()
                return r17
            L_0x04ee:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04fd
                r1 = r17
            L_0x04fd:
                r0 = r1
                r12.setBackupEnabled(r0)
                r22.writeNoException()
                return r17
            L_0x0505:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0518
                r1 = r17
            L_0x0518:
                r12.setBackupEnabledForUser(r0, r1)
                r22.writeNoException()
                return r17
            L_0x051f:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                r12.restoreAtInstall(r0, r1)
                r22.writeNoException()
                return r17
            L_0x0534:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                r12.restoreAtInstallForUser(r0, r1, r2)
                r22.writeNoException()
                return r17
            L_0x054d:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                r12.agentDisconnected(r0)
                r22.writeNoException()
                return r17
            L_0x055e:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                r12.agentDisconnectedForUser(r0, r1)
                r22.writeNoException()
                return r17
            L_0x0573:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                android.os.IBinder r1 = r21.readStrongBinder()
                r12.agentConnected(r0, r1)
                r22.writeNoException()
                return r17
            L_0x0588:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                android.os.IBinder r2 = r21.readStrongBinder()
                r12.agentConnectedForUser(r0, r1, r2)
                r22.writeNoException()
                return r17
            L_0x05a1:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String[] r1 = r21.createStringArray()
                android.os.IBinder r2 = r21.readStrongBinder()
                android.app.backup.IBackupObserver r2 = android.app.backup.IBackupObserver.Stub.asInterface(r2)
                r12.initializeTransportsForUser(r0, r1, r2)
                r22.writeNoException()
                return r17
            L_0x05be:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                r12.clearBackupData(r0, r1)
                r22.writeNoException()
                return r17
            L_0x05d3:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r12.clearBackupDataForUser(r0, r1, r2)
                r22.writeNoException()
                return r17
            L_0x05ec:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                r12.dataChanged(r0)
                r22.writeNoException()
                return r17
            L_0x05fd:
                r17 = r10
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                r12.dataChangedForUser(r0, r1)
                r22.writeNoException()
                return r17
            L_0x0612:
                r17 = r10
                r13 = r11
                r15.writeString(r13)
                return r17
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.backup.IBackupManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBackupManager {
            public static IBackupManager sDefaultImpl;
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

            public void dataChangedForUser(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dataChangedForUser(userId, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dataChanged(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dataChanged(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearBackupDataForUser(int userId, String transportName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transportName);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearBackupDataForUser(userId, transportName, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearBackupData(String transportName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(transportName);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearBackupData(transportName, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void initializeTransportsForUser(int userId, String[] transportNames, IBackupObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(transportNames);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().initializeTransportsForUser(userId, transportNames, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void agentConnectedForUser(int userId, String packageName, IBinder agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(agent);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().agentConnectedForUser(userId, packageName, agent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void agentConnected(String packageName, IBinder agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(agent);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().agentConnected(packageName, agent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void agentDisconnectedForUser(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().agentDisconnectedForUser(userId, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void agentDisconnected(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().agentDisconnected(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restoreAtInstallForUser(int userId, String packageName, int token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeInt(token);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreAtInstallForUser(userId, packageName, token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void restoreAtInstall(String packageName, int token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(token);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().restoreAtInstall(packageName, token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBackupEnabledForUser(int userId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBackupEnabledForUser(userId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBackupEnabled(boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBackupEnabled(isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAutoRestoreForUser(int userId, boolean doAutoRestore) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(doAutoRestore);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAutoRestoreForUser(userId, doAutoRestore);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAutoRestore(boolean doAutoRestore) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(doAutoRestore);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAutoRestore(doAutoRestore);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBackupEnabledForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackupEnabledForUser(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBackupEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackupEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setBackupPassword(String currentPw, String newPw) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(currentPw);
                    _data.writeString(newPw);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBackupPassword(currentPw, newPw);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasBackupPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasBackupPassword();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void backupNowForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().backupNowForUser(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void backupNow() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().backupNow();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void adbBackup(int userId, ParcelFileDescriptor fd, boolean includeApks, boolean includeObbs, boolean includeShared, boolean doWidgets, boolean allApps, boolean allIncludesSystem, boolean doCompress, boolean doKeyValue, String[] packageNames) throws RemoteException {
                ParcelFileDescriptor parcelFileDescriptor = fd;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (parcelFileDescriptor != null) {
                        _data.writeInt(1);
                        parcelFileDescriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(includeApks ? 1 : 0);
                    _data.writeInt(includeObbs ? 1 : 0);
                    _data.writeInt(includeShared ? 1 : 0);
                    _data.writeInt(doWidgets ? 1 : 0);
                    _data.writeInt(allApps ? 1 : 0);
                    _data.writeInt(allIncludesSystem ? 1 : 0);
                    _data.writeInt(doCompress ? 1 : 0);
                    _data.writeInt(doKeyValue ? 1 : 0);
                    _data.writeStringArray(packageNames);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().adbBackup(userId, fd, includeApks, includeObbs, includeShared, doWidgets, allApps, allIncludesSystem, doCompress, doKeyValue, packageNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fullTransportBackupForUser(int userId, String[] packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packageNames);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().fullTransportBackupForUser(userId, packageNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void adbRestore(int userId, ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (fd != null) {
                        _data.writeInt(1);
                        fd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().adbRestore(userId, fd);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void acknowledgeFullBackupOrRestoreForUser(int userId, int token, boolean allow, String curPassword, String encryptionPassword, IFullBackupRestoreObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(userId);
                        try {
                            _data.writeInt(token);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = allow;
                            String str = curPassword;
                            String str2 = encryptionPassword;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(allow ? 1 : 0);
                            try {
                                _data.writeString(curPassword);
                            } catch (Throwable th2) {
                                th = th2;
                                String str22 = encryptionPassword;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeString(encryptionPassword);
                                _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                                if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().acknowledgeFullBackupOrRestoreForUser(userId, token, allow, curPassword, encryptionPassword, observer);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            String str3 = curPassword;
                            String str222 = encryptionPassword;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i = token;
                        boolean z2 = allow;
                        String str32 = curPassword;
                        String str2222 = encryptionPassword;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i2 = userId;
                    int i3 = token;
                    boolean z22 = allow;
                    String str322 = curPassword;
                    String str22222 = encryptionPassword;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void acknowledgeFullBackupOrRestore(int token, boolean allow, String curPassword, String encryptionPassword, IFullBackupRestoreObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeInt(allow);
                    _data.writeString(curPassword);
                    _data.writeString(encryptionPassword);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acknowledgeFullBackupOrRestore(token, allow, curPassword, encryptionPassword, observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateTransportAttributesForUser(int userId, ComponentName transportComponent, String name, Intent configurationIntent, String currentDestinationString, Intent dataManagementIntent, CharSequence dataManagementLabel) throws RemoteException {
                ComponentName componentName = transportComponent;
                Intent intent = configurationIntent;
                Intent intent2 = dataManagementIntent;
                CharSequence charSequence = dataManagementLabel;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(userId);
                        if (componentName != null) {
                            _data.writeInt(1);
                            componentName.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeString(name);
                        if (intent != null) {
                            _data.writeInt(1);
                            intent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeString(currentDestinationString);
                        if (intent2 != null) {
                            _data.writeInt(1);
                            intent2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (charSequence != null) {
                            _data.writeInt(1);
                            TextUtils.writeToParcel(charSequence, _data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().updateTransportAttributesForUser(userId, transportComponent, name, configurationIntent, currentDestinationString, dataManagementIntent, dataManagementLabel);
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
                    int i = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String getCurrentTransportForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentTransportForUser(userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCurrentTransport() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentTransport();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName getCurrentTransportComponentForUser(int userId) throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentTransportComponentForUser(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] listAllTransportsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listAllTransportsForUser(userId);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] listAllTransports() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listAllTransports();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ComponentName[] listAllTransportComponentsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().listAllTransportComponentsForUser(userId);
                    }
                    _reply.readException();
                    ComponentName[] _result = (ComponentName[]) _reply.createTypedArray(ComponentName.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getTransportWhitelist() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTransportWhitelist();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String selectBackupTransportForUser(int userId, String transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().selectBackupTransportForUser(userId, transport);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String selectBackupTransport(String transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().selectBackupTransport(transport);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void selectBackupTransportAsyncForUser(int userId, ComponentName transport, ISelectBackupTransportCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (transport != null) {
                        _data.writeInt(1);
                        transport.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().selectBackupTransportAsyncForUser(userId, transport, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent getConfigurationIntentForUser(int userId, String transport) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfigurationIntentForUser(userId, transport);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent getConfigurationIntent(String transport) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfigurationIntent(transport);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDestinationStringForUser(int userId, String transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDestinationStringForUser(userId, transport);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDestinationString(String transport) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDestinationString(transport);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent getDataManagementIntentForUser(int userId, String transport) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataManagementIntentForUser(userId, transport);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent getDataManagementIntent(String transport) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataManagementIntent(transport);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getDataManagementLabelForUser(int userId, String transport) throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(transport);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataManagementLabelForUser(userId, transport);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CharSequence _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IRestoreSession beginRestoreSessionForUser(int userId, String packageName, String transportID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeString(transportID);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().beginRestoreSessionForUser(userId, packageName, transportID);
                    }
                    _reply.readException();
                    IRestoreSession _result = IRestoreSession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void opCompleteForUser(int userId, int token, long result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(token);
                    _data.writeLong(result);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().opCompleteForUser(userId, token, result);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void opComplete(int token, long result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeLong(result);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().opComplete(token, result);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBackupServiceActive(int whichUser, boolean makeActive) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(whichUser);
                    _data.writeInt(makeActive);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBackupServiceActive(whichUser, makeActive);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBackupServiceActive(int whichUser) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(whichUser);
                    boolean z = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBackupServiceActive(whichUser);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getAvailableRestoreTokenForUser(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAvailableRestoreTokenForUser(userId, packageName);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAppEligibleForBackupForUser(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAppEligibleForBackupForUser(userId, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] filterAppsEligibleForBackupForUser(int userId, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packages);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().filterAppsEligibleForBackupForUser(userId, packages);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int requestBackupForUser(int userId, String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packages);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (monitor != null) {
                        iBinder = monitor.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBackupForUser(userId, packages, observer, monitor, flags);
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

            public int requestBackup(String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packages);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (monitor != null) {
                        iBinder = monitor.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestBackup(packages, observer, monitor, flags);
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

            public void cancelBackupsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelBackupsForUser(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelBackups() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelBackups();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UserHandle getUserForAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {
                UserHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(ancestralSerialNumber);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserForAncestralSerialNumber(ancestralSerialNumber);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(ancestralSerialNumber);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAncestralSerialNumber(ancestralSerialNumber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBackupManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBackupManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
