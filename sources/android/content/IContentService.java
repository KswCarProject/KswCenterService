package android.content;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IContentService extends IInterface {
    void addPeriodicSync(Account account, String str, Bundle bundle, long j) throws RemoteException;

    void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) throws RemoteException;

    void cancelRequest(SyncRequest syncRequest) throws RemoteException;

    @UnsupportedAppUsage
    void cancelSync(Account account, String str, ComponentName componentName) throws RemoteException;

    void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    Bundle getCache(String str, Uri uri, int i) throws RemoteException;

    List<SyncInfo> getCurrentSyncs() throws RemoteException;

    List<SyncInfo> getCurrentSyncsAsUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getIsSyncable(Account account, String str) throws RemoteException;

    int getIsSyncableAsUser(Account account, String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean getMasterSyncAutomatically() throws RemoteException;

    boolean getMasterSyncAutomaticallyAsUser(int i) throws RemoteException;

    List<PeriodicSync> getPeriodicSyncs(Account account, String str, ComponentName componentName) throws RemoteException;

    String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    SyncAdapterType[] getSyncAdapterTypes() throws RemoteException;

    SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws RemoteException;

    boolean getSyncAutomatically(Account account, String str) throws RemoteException;

    boolean getSyncAutomaticallyAsUser(Account account, String str, int i) throws RemoteException;

    SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) throws RemoteException;

    SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean isSyncActive(Account account, String str, ComponentName componentName) throws RemoteException;

    boolean isSyncPending(Account account, String str, ComponentName componentName) throws RemoteException;

    boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    void notifyChange(Uri uri, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) throws RemoteException;

    void onDbCorruption(String str, String str2, String str3) throws RemoteException;

    void putCache(String str, Uri uri, Bundle bundle, int i) throws RemoteException;

    void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) throws RemoteException;

    void removePeriodicSync(Account account, String str, Bundle bundle) throws RemoteException;

    void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException;

    void requestSync(Account account, String str, Bundle bundle, String str2) throws RemoteException;

    void resetTodayStats() throws RemoteException;

    void setIsSyncable(Account account, String str, int i) throws RemoteException;

    void setIsSyncableAsUser(Account account, String str, int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void setMasterSyncAutomatically(boolean z) throws RemoteException;

    void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws RemoteException;

    void setSyncAutomatically(Account account, String str, boolean z) throws RemoteException;

    void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) throws RemoteException;

    void sync(SyncRequest syncRequest, String str) throws RemoteException;

    void syncAsUser(SyncRequest syncRequest, int i, String str) throws RemoteException;

    void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException;

    public static class Default implements IContentService {
        public void unregisterContentObserver(IContentObserver observer) throws RemoteException {
        }

        public void registerContentObserver(Uri uri, boolean notifyForDescendants, IContentObserver observer, int userHandle, int targetSdkVersion) throws RemoteException {
        }

        public void notifyChange(Uri uri, IContentObserver observer, boolean observerWantsSelfNotifications, int flags, int userHandle, int targetSdkVersion, String callingPackage) throws RemoteException {
        }

        public void requestSync(Account account, String authority, Bundle extras, String callingPackage) throws RemoteException {
        }

        public void sync(SyncRequest request, String callingPackage) throws RemoteException {
        }

        public void syncAsUser(SyncRequest request, int userId, String callingPackage) throws RemoteException {
        }

        public void cancelSync(Account account, String authority, ComponentName cname) throws RemoteException {
        }

        public void cancelSyncAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
        }

        public void cancelRequest(SyncRequest request) throws RemoteException {
        }

        public boolean getSyncAutomatically(Account account, String providerName) throws RemoteException {
            return false;
        }

        public boolean getSyncAutomaticallyAsUser(Account account, String providerName, int userId) throws RemoteException {
            return false;
        }

        public void setSyncAutomatically(Account account, String providerName, boolean sync) throws RemoteException {
        }

        public void setSyncAutomaticallyAsUser(Account account, String providerName, boolean sync, int userId) throws RemoteException {
        }

        public List<PeriodicSync> getPeriodicSyncs(Account account, String providerName, ComponentName cname) throws RemoteException {
            return null;
        }

        public void addPeriodicSync(Account account, String providerName, Bundle extras, long pollFrequency) throws RemoteException {
        }

        public void removePeriodicSync(Account account, String providerName, Bundle extras) throws RemoteException {
        }

        public int getIsSyncable(Account account, String providerName) throws RemoteException {
            return 0;
        }

        public int getIsSyncableAsUser(Account account, String providerName, int userId) throws RemoteException {
            return 0;
        }

        public void setIsSyncable(Account account, String providerName, int syncable) throws RemoteException {
        }

        public void setIsSyncableAsUser(Account account, String providerName, int syncable, int userId) throws RemoteException {
        }

        public void setMasterSyncAutomatically(boolean flag) throws RemoteException {
        }

        public void setMasterSyncAutomaticallyAsUser(boolean flag, int userId) throws RemoteException {
        }

        public boolean getMasterSyncAutomatically() throws RemoteException {
            return false;
        }

        public boolean getMasterSyncAutomaticallyAsUser(int userId) throws RemoteException {
            return false;
        }

        public List<SyncInfo> getCurrentSyncs() throws RemoteException {
            return null;
        }

        public List<SyncInfo> getCurrentSyncsAsUser(int userId) throws RemoteException {
            return null;
        }

        public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
            return null;
        }

        public SyncAdapterType[] getSyncAdapterTypesAsUser(int userId) throws RemoteException {
            return null;
        }

        public String[] getSyncAdapterPackagesForAuthorityAsUser(String authority, int userId) throws RemoteException {
            return null;
        }

        public boolean isSyncActive(Account account, String authority, ComponentName cname) throws RemoteException {
            return false;
        }

        public SyncStatusInfo getSyncStatus(Account account, String authority, ComponentName cname) throws RemoteException {
            return null;
        }

        public SyncStatusInfo getSyncStatusAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
            return null;
        }

        public boolean isSyncPending(Account account, String authority, ComponentName cname) throws RemoteException {
            return false;
        }

        public boolean isSyncPendingAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
            return false;
        }

        public void addStatusChangeListener(int mask, ISyncStatusObserver callback) throws RemoteException {
        }

        public void removeStatusChangeListener(ISyncStatusObserver callback) throws RemoteException {
        }

        public void putCache(String packageName, Uri key, Bundle value, int userId) throws RemoteException {
        }

        public Bundle getCache(String packageName, Uri key, int userId) throws RemoteException {
            return null;
        }

        public void resetTodayStats() throws RemoteException {
        }

        public void onDbCorruption(String tag, String message, String stacktrace) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContentService {
        private static final String DESCRIPTOR = "android.content.IContentService";
        static final int TRANSACTION_addPeriodicSync = 15;
        static final int TRANSACTION_addStatusChangeListener = 35;
        static final int TRANSACTION_cancelRequest = 9;
        static final int TRANSACTION_cancelSync = 7;
        static final int TRANSACTION_cancelSyncAsUser = 8;
        static final int TRANSACTION_getCache = 38;
        static final int TRANSACTION_getCurrentSyncs = 25;
        static final int TRANSACTION_getCurrentSyncsAsUser = 26;
        static final int TRANSACTION_getIsSyncable = 17;
        static final int TRANSACTION_getIsSyncableAsUser = 18;
        static final int TRANSACTION_getMasterSyncAutomatically = 23;
        static final int TRANSACTION_getMasterSyncAutomaticallyAsUser = 24;
        static final int TRANSACTION_getPeriodicSyncs = 14;
        static final int TRANSACTION_getSyncAdapterPackagesForAuthorityAsUser = 29;
        static final int TRANSACTION_getSyncAdapterTypes = 27;
        static final int TRANSACTION_getSyncAdapterTypesAsUser = 28;
        static final int TRANSACTION_getSyncAutomatically = 10;
        static final int TRANSACTION_getSyncAutomaticallyAsUser = 11;
        static final int TRANSACTION_getSyncStatus = 31;
        static final int TRANSACTION_getSyncStatusAsUser = 32;
        static final int TRANSACTION_isSyncActive = 30;
        static final int TRANSACTION_isSyncPending = 33;
        static final int TRANSACTION_isSyncPendingAsUser = 34;
        static final int TRANSACTION_notifyChange = 3;
        static final int TRANSACTION_onDbCorruption = 40;
        static final int TRANSACTION_putCache = 37;
        static final int TRANSACTION_registerContentObserver = 2;
        static final int TRANSACTION_removePeriodicSync = 16;
        static final int TRANSACTION_removeStatusChangeListener = 36;
        static final int TRANSACTION_requestSync = 4;
        static final int TRANSACTION_resetTodayStats = 39;
        static final int TRANSACTION_setIsSyncable = 19;
        static final int TRANSACTION_setIsSyncableAsUser = 20;
        static final int TRANSACTION_setMasterSyncAutomatically = 21;
        static final int TRANSACTION_setMasterSyncAutomaticallyAsUser = 22;
        static final int TRANSACTION_setSyncAutomatically = 12;
        static final int TRANSACTION_setSyncAutomaticallyAsUser = 13;
        static final int TRANSACTION_sync = 5;
        static final int TRANSACTION_syncAsUser = 6;
        static final int TRANSACTION_unregisterContentObserver = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContentService)) {
                return new Proxy(obj);
            }
            return (IContentService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "unregisterContentObserver";
                case 2:
                    return "registerContentObserver";
                case 3:
                    return "notifyChange";
                case 4:
                    return "requestSync";
                case 5:
                    return "sync";
                case 6:
                    return "syncAsUser";
                case 7:
                    return "cancelSync";
                case 8:
                    return "cancelSyncAsUser";
                case 9:
                    return "cancelRequest";
                case 10:
                    return "getSyncAutomatically";
                case 11:
                    return "getSyncAutomaticallyAsUser";
                case 12:
                    return "setSyncAutomatically";
                case 13:
                    return "setSyncAutomaticallyAsUser";
                case 14:
                    return "getPeriodicSyncs";
                case 15:
                    return "addPeriodicSync";
                case 16:
                    return "removePeriodicSync";
                case 17:
                    return "getIsSyncable";
                case 18:
                    return "getIsSyncableAsUser";
                case 19:
                    return "setIsSyncable";
                case 20:
                    return "setIsSyncableAsUser";
                case 21:
                    return "setMasterSyncAutomatically";
                case 22:
                    return "setMasterSyncAutomaticallyAsUser";
                case 23:
                    return "getMasterSyncAutomatically";
                case 24:
                    return "getMasterSyncAutomaticallyAsUser";
                case 25:
                    return "getCurrentSyncs";
                case 26:
                    return "getCurrentSyncsAsUser";
                case 27:
                    return "getSyncAdapterTypes";
                case 28:
                    return "getSyncAdapterTypesAsUser";
                case 29:
                    return "getSyncAdapterPackagesForAuthorityAsUser";
                case 30:
                    return "isSyncActive";
                case 31:
                    return "getSyncStatus";
                case 32:
                    return "getSyncStatusAsUser";
                case 33:
                    return "isSyncPending";
                case 34:
                    return "isSyncPendingAsUser";
                case 35:
                    return "addStatusChangeListener";
                case 36:
                    return "removeStatusChangeListener";
                case 37:
                    return "putCache";
                case 38:
                    return "getCache";
                case 39:
                    return "resetTodayStats";
                case 40:
                    return "onDbCorruption";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v19, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v46, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v76, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v80, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v84, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v88, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v92, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r2v0 */
        /* JADX WARNING: type inference failed for: r2v12 */
        /* JADX WARNING: type inference failed for: r2v15 */
        /* JADX WARNING: type inference failed for: r2v27 */
        /* JADX WARNING: type inference failed for: r2v30 */
        /* JADX WARNING: type inference failed for: r2v34 */
        /* JADX WARNING: type inference failed for: r2v38 */
        /* JADX WARNING: type inference failed for: r2v42 */
        /* JADX WARNING: type inference failed for: r2v55 */
        /* JADX WARNING: type inference failed for: r2v59 */
        /* JADX WARNING: type inference failed for: r2v63 */
        /* JADX WARNING: type inference failed for: r2v67 */
        /* JADX WARNING: type inference failed for: r2v96, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r2v101 */
        /* JADX WARNING: type inference failed for: r2v102 */
        /* JADX WARNING: type inference failed for: r2v103 */
        /* JADX WARNING: type inference failed for: r2v104 */
        /* JADX WARNING: type inference failed for: r2v105 */
        /* JADX WARNING: type inference failed for: r2v106 */
        /* JADX WARNING: type inference failed for: r2v107 */
        /* JADX WARNING: type inference failed for: r2v108 */
        /* JADX WARNING: type inference failed for: r2v109 */
        /* JADX WARNING: type inference failed for: r2v110 */
        /* JADX WARNING: type inference failed for: r2v111 */
        /* JADX WARNING: type inference failed for: r2v112 */
        /* JADX WARNING: type inference failed for: r2v113 */
        /* JADX WARNING: type inference failed for: r2v114 */
        /* JADX WARNING: type inference failed for: r2v115 */
        /* JADX WARNING: type inference failed for: r2v116 */
        /* JADX WARNING: type inference failed for: r2v117 */
        /* JADX WARNING: type inference failed for: r2v118 */
        /* JADX WARNING: type inference failed for: r2v119 */
        /* JADX WARNING: type inference failed for: r2v120 */
        /* JADX WARNING: type inference failed for: r2v121 */
        /* JADX WARNING: type inference failed for: r2v122 */
        /* JADX WARNING: type inference failed for: r2v123 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r8 = r19
                r9 = r20
                r10 = r21
                r11 = r22
                java.lang.String r12 = "android.content.IContentService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x05c6
                r0 = 0
                r2 = 0
                switch(r9) {
                    case 1: goto L_0x05b4;
                    case 2: goto L_0x057c;
                    case 3: goto L_0x0537;
                    case 4: goto L_0x0505;
                    case 5: goto L_0x04e5;
                    case 6: goto L_0x04c1;
                    case 7: goto L_0x0493;
                    case 8: goto L_0x0461;
                    case 9: goto L_0x0445;
                    case 10: goto L_0x0421;
                    case 11: goto L_0x03f9;
                    case 12: goto L_0x03d1;
                    case 13: goto L_0x03a5;
                    case 14: goto L_0x0373;
                    case 15: goto L_0x033b;
                    case 16: goto L_0x030d;
                    case 17: goto L_0x02e9;
                    case 18: goto L_0x02c1;
                    case 19: goto L_0x029d;
                    case 20: goto L_0x0275;
                    case 21: goto L_0x0263;
                    case 22: goto L_0x024d;
                    case 23: goto L_0x023f;
                    case 24: goto L_0x022d;
                    case 25: goto L_0x021f;
                    case 26: goto L_0x020d;
                    case 27: goto L_0x01ff;
                    case 28: goto L_0x01ed;
                    case 29: goto L_0x01d7;
                    case 30: goto L_0x01a5;
                    case 31: goto L_0x016a;
                    case 32: goto L_0x012b;
                    case 33: goto L_0x00f9;
                    case 34: goto L_0x00c3;
                    case 35: goto L_0x00ad;
                    case 36: goto L_0x009b;
                    case 37: goto L_0x0069;
                    case 38: goto L_0x003a;
                    case 39: goto L_0x0030;
                    case 40: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r8.onDbCorruption(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x0030:
                r10.enforceInterface(r12)
                r19.resetTodayStats()
                r22.writeNoException()
                return r13
            L_0x003a:
                r10.enforceInterface(r12)
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0050
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x0051
            L_0x0050:
            L_0x0051:
                int r3 = r21.readInt()
                android.os.Bundle r4 = r8.getCache(r1, r2, r3)
                r22.writeNoException()
                if (r4 == 0) goto L_0x0065
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0068
            L_0x0065:
                r11.writeInt(r0)
            L_0x0068:
                return r13
            L_0x0069:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x007f
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0080
            L_0x007f:
                r1 = r2
            L_0x0080:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x008f
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.os.Bundle r2 = (android.os.Bundle) r2
                goto L_0x0090
            L_0x008f:
            L_0x0090:
                int r3 = r21.readInt()
                r8.putCache(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x009b:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.content.ISyncStatusObserver r0 = android.content.ISyncStatusObserver.Stub.asInterface(r0)
                r8.removeStatusChangeListener(r0)
                r22.writeNoException()
                return r13
            L_0x00ad:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                android.os.IBinder r1 = r21.readStrongBinder()
                android.content.ISyncStatusObserver r1 = android.content.ISyncStatusObserver.Stub.asInterface(r1)
                r8.addStatusChangeListener(r0, r1)
                r22.writeNoException()
                return r13
            L_0x00c3:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x00d5
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x00d6
            L_0x00d5:
                r0 = r2
            L_0x00d6:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x00e9
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x00ea
            L_0x00e9:
            L_0x00ea:
                int r3 = r21.readInt()
                boolean r4 = r8.isSyncPendingAsUser(r0, r1, r2, r3)
                r22.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x00f9:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x010b
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x010c
            L_0x010b:
                r0 = r2
            L_0x010c:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x011f
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0120
            L_0x011f:
            L_0x0120:
                boolean r3 = r8.isSyncPending(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x012b:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x013d
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x013e
            L_0x013d:
                r1 = r2
            L_0x013e:
                java.lang.String r3 = r21.readString()
                int r4 = r21.readInt()
                if (r4 == 0) goto L_0x0151
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0152
            L_0x0151:
            L_0x0152:
                int r4 = r21.readInt()
                android.content.SyncStatusInfo r5 = r8.getSyncStatusAsUser(r1, r3, r2, r4)
                r22.writeNoException()
                if (r5 == 0) goto L_0x0166
                r11.writeInt(r13)
                r5.writeToParcel(r11, r13)
                goto L_0x0169
            L_0x0166:
                r11.writeInt(r0)
            L_0x0169:
                return r13
            L_0x016a:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x017c
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x017d
            L_0x017c:
                r1 = r2
            L_0x017d:
                java.lang.String r3 = r21.readString()
                int r4 = r21.readInt()
                if (r4 == 0) goto L_0x0190
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0191
            L_0x0190:
            L_0x0191:
                android.content.SyncStatusInfo r4 = r8.getSyncStatus(r1, r3, r2)
                r22.writeNoException()
                if (r4 == 0) goto L_0x01a1
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x01a4
            L_0x01a1:
                r11.writeInt(r0)
            L_0x01a4:
                return r13
            L_0x01a5:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x01b7
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x01b8
            L_0x01b7:
                r0 = r2
            L_0x01b8:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x01cb
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x01cc
            L_0x01cb:
            L_0x01cc:
                boolean r3 = r8.isSyncActive(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x01d7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                java.lang.String[] r2 = r8.getSyncAdapterPackagesForAuthorityAsUser(r0, r1)
                r22.writeNoException()
                r11.writeStringArray(r2)
                return r13
            L_0x01ed:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                android.content.SyncAdapterType[] r1 = r8.getSyncAdapterTypesAsUser(r0)
                r22.writeNoException()
                r11.writeTypedArray(r1, r13)
                return r13
            L_0x01ff:
                r10.enforceInterface(r12)
                android.content.SyncAdapterType[] r0 = r19.getSyncAdapterTypes()
                r22.writeNoException()
                r11.writeTypedArray(r0, r13)
                return r13
            L_0x020d:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                java.util.List r1 = r8.getCurrentSyncsAsUser(r0)
                r22.writeNoException()
                r11.writeTypedList(r1)
                return r13
            L_0x021f:
                r10.enforceInterface(r12)
                java.util.List r0 = r19.getCurrentSyncs()
                r22.writeNoException()
                r11.writeTypedList(r0)
                return r13
            L_0x022d:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                boolean r1 = r8.getMasterSyncAutomaticallyAsUser(r0)
                r22.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x023f:
                r10.enforceInterface(r12)
                boolean r0 = r19.getMasterSyncAutomatically()
                r22.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x024d:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0258
                r0 = r13
            L_0x0258:
                int r1 = r21.readInt()
                r8.setMasterSyncAutomaticallyAsUser(r0, r1)
                r22.writeNoException()
                return r13
            L_0x0263:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x026e
                r0 = r13
            L_0x026e:
                r8.setMasterSyncAutomatically(r0)
                r22.writeNoException()
                return r13
            L_0x0275:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0288
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x0289
            L_0x0288:
            L_0x0289:
                r0 = r2
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                r8.setIsSyncableAsUser(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x029d:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x02b0
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x02b1
            L_0x02b0:
            L_0x02b1:
                r0 = r2
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                r8.setIsSyncable(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x02c1:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x02d4
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x02d5
            L_0x02d4:
            L_0x02d5:
                r0 = r2
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                int r3 = r8.getIsSyncableAsUser(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x02e9:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x02fc
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x02fd
            L_0x02fc:
            L_0x02fd:
                r0 = r2
                java.lang.String r1 = r21.readString()
                int r2 = r8.getIsSyncable(r0, r1)
                r22.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x030d:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x031f
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0320
            L_0x031f:
                r0 = r2
            L_0x0320:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0333
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.os.Bundle r2 = (android.os.Bundle) r2
                goto L_0x0334
            L_0x0333:
            L_0x0334:
                r8.removePeriodicSync(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x033b:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x034e
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                r1 = r0
                goto L_0x034f
            L_0x034e:
                r1 = r2
            L_0x034f:
                java.lang.String r6 = r21.readString()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0363
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r3 = r0
                goto L_0x0364
            L_0x0363:
                r3 = r2
            L_0x0364:
                long r14 = r21.readLong()
                r0 = r19
                r2 = r6
                r4 = r14
                r0.addPeriodicSync(r1, r2, r3, r4)
                r22.writeNoException()
                return r13
            L_0x0373:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0385
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0386
            L_0x0385:
                r0 = r2
            L_0x0386:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0399
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x039a
            L_0x0399:
            L_0x039a:
                java.util.List r3 = r8.getPeriodicSyncs(r0, r1, r2)
                r22.writeNoException()
                r11.writeTypedList(r3)
                return r13
            L_0x03a5:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x03b8
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r2 = r1
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x03b9
            L_0x03b8:
            L_0x03b9:
                r1 = r2
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x03c6
                r0 = r13
            L_0x03c6:
                int r3 = r21.readInt()
                r8.setSyncAutomaticallyAsUser(r1, r2, r0, r3)
                r22.writeNoException()
                return r13
            L_0x03d1:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x03e4
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                r2 = r1
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x03e5
            L_0x03e4:
            L_0x03e5:
                r1 = r2
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x03f2
                r0 = r13
            L_0x03f2:
                r8.setSyncAutomatically(r1, r2, r0)
                r22.writeNoException()
                return r13
            L_0x03f9:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x040c
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x040d
            L_0x040c:
            L_0x040d:
                r0 = r2
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                boolean r3 = r8.getSyncAutomaticallyAsUser(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0421:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0434
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x0435
            L_0x0434:
            L_0x0435:
                r0 = r2
                java.lang.String r1 = r21.readString()
                boolean r2 = r8.getSyncAutomatically(r0, r1)
                r22.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0445:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0458
                android.os.Parcelable$Creator<android.content.SyncRequest> r0 = android.content.SyncRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.content.SyncRequest r2 = (android.content.SyncRequest) r2
                goto L_0x0459
            L_0x0458:
            L_0x0459:
                r0 = r2
                r8.cancelRequest(r0)
                r22.writeNoException()
                return r13
            L_0x0461:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0473
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0474
            L_0x0473:
                r0 = r2
            L_0x0474:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0487
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x0488
            L_0x0487:
            L_0x0488:
                int r3 = r21.readInt()
                r8.cancelSyncAsUser(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x0493:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04a5
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x04a6
            L_0x04a5:
                r0 = r2
            L_0x04a6:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x04b9
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x04ba
            L_0x04b9:
            L_0x04ba:
                r8.cancelSync(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x04c1:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04d4
                android.os.Parcelable$Creator<android.content.SyncRequest> r0 = android.content.SyncRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.content.SyncRequest r2 = (android.content.SyncRequest) r2
                goto L_0x04d5
            L_0x04d4:
            L_0x04d5:
                r0 = r2
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r8.syncAsUser(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x04e5:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04f8
                android.os.Parcelable$Creator<android.content.SyncRequest> r0 = android.content.SyncRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r2 = r0
                android.content.SyncRequest r2 = (android.content.SyncRequest) r2
                goto L_0x04f9
            L_0x04f8:
            L_0x04f9:
                r0 = r2
                java.lang.String r1 = r21.readString()
                r8.sync(r0, r1)
                r22.writeNoException()
                return r13
            L_0x0505:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0517
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0518
            L_0x0517:
                r0 = r2
            L_0x0518:
                java.lang.String r1 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x052b
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.os.Bundle r2 = (android.os.Bundle) r2
                goto L_0x052c
            L_0x052b:
            L_0x052c:
                java.lang.String r3 = r21.readString()
                r8.requestSync(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x0537:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0549
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x054a
            L_0x0549:
                r1 = r2
            L_0x054a:
                android.os.IBinder r2 = r21.readStrongBinder()
                android.database.IContentObserver r14 = android.database.IContentObserver.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x055a
                r3 = r13
                goto L_0x055b
            L_0x055a:
                r3 = r0
            L_0x055b:
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                int r17 = r21.readInt()
                java.lang.String r18 = r21.readString()
                r0 = r19
                r2 = r14
                r4 = r15
                r5 = r16
                r6 = r17
                r7 = r18
                r0.notifyChange(r1, r2, r3, r4, r5, r6, r7)
                r22.writeNoException()
                return r13
            L_0x057c:
                r10.enforceInterface(r12)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x058e
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x058f
            L_0x058e:
                r1 = r2
            L_0x058f:
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0597
                r2 = r13
                goto L_0x0598
            L_0x0597:
                r2 = r0
            L_0x0598:
                android.os.IBinder r0 = r21.readStrongBinder()
                android.database.IContentObserver r6 = android.database.IContentObserver.Stub.asInterface(r0)
                int r7 = r21.readInt()
                int r14 = r21.readInt()
                r0 = r19
                r3 = r6
                r4 = r7
                r5 = r14
                r0.registerContentObserver(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r13
            L_0x05b4:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.database.IContentObserver r0 = android.database.IContentObserver.Stub.asInterface(r0)
                r8.unregisterContentObserver(r0)
                r22.writeNoException()
                return r13
            L_0x05c6:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.IContentService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContentService {
            public static IContentService sDefaultImpl;
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

            public void unregisterContentObserver(IContentObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterContentObserver(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerContentObserver(Uri uri, boolean notifyForDescendants, IContentObserver observer, int userHandle, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(notifyForDescendants);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    _data.writeInt(userHandle);
                    _data.writeInt(targetSdkVersion);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerContentObserver(uri, notifyForDescendants, observer, userHandle, targetSdkVersion);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyChange(Uri uri, IContentObserver observer, boolean observerWantsSelfNotifications, int flags, int userHandle, int targetSdkVersion, String callingPackage) throws RemoteException {
                Uri uri2 = uri;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri2 != null) {
                        _data.writeInt(1);
                        uri2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    try {
                        _data.writeInt(observerWantsSelfNotifications ? 1 : 0);
                    } catch (Throwable th) {
                        th = th;
                        int i = flags;
                        int i2 = userHandle;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        try {
                            _data.writeInt(userHandle);
                            _data.writeInt(targetSdkVersion);
                            _data.writeString(callingPackage);
                            if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().notifyChange(uri, observer, observerWantsSelfNotifications, flags, userHandle, targetSdkVersion, callingPackage);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i22 = userHandle;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    boolean z = observerWantsSelfNotifications;
                    int i3 = flags;
                    int i222 = userHandle;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void requestSync(Account account, String authority, Bundle extras, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestSync(account, authority, extras, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sync(SyncRequest request, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sync(request, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void syncAsUser(SyncRequest request, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().syncAsUser(request, userId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelSync(Account account, String authority, ComponentName cname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelSync(account, authority, cname);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelSyncAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelSyncAsUser(account, authority, cname, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelRequest(SyncRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelRequest(request);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getSyncAutomatically(Account account, String providerName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncAutomatically(account, providerName);
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

            public boolean getSyncAutomaticallyAsUser(Account account, String providerName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncAutomaticallyAsUser(account, providerName, userId);
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

            public void setSyncAutomatically(Account account, String providerName, boolean sync) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(sync);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSyncAutomatically(account, providerName, sync);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSyncAutomaticallyAsUser(Account account, String providerName, boolean sync, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(sync);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSyncAutomaticallyAsUser(account, providerName, sync, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PeriodicSync> getPeriodicSyncs(Account account, String providerName, ComponentName cname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPeriodicSyncs(account, providerName, cname);
                    }
                    _reply.readException();
                    List<PeriodicSync> _result = _reply.createTypedArrayList(PeriodicSync.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addPeriodicSync(Account account, String providerName, Bundle extras, long pollFrequency) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(pollFrequency);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addPeriodicSync(account, providerName, extras, pollFrequency);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removePeriodicSync(Account account, String providerName, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removePeriodicSync(account, providerName, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getIsSyncable(Account account, String providerName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIsSyncable(account, providerName);
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

            public int getIsSyncableAsUser(Account account, String providerName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIsSyncableAsUser(account, providerName, userId);
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

            public void setIsSyncable(Account account, String providerName, int syncable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(syncable);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIsSyncable(account, providerName, syncable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setIsSyncableAsUser(Account account, String providerName, int syncable, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(providerName);
                    _data.writeInt(syncable);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setIsSyncableAsUser(account, providerName, syncable, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMasterSyncAutomatically(boolean flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMasterSyncAutomatically(flag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMasterSyncAutomaticallyAsUser(boolean flag, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMasterSyncAutomaticallyAsUser(flag, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getMasterSyncAutomatically() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMasterSyncAutomatically();
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

            public boolean getMasterSyncAutomaticallyAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMasterSyncAutomaticallyAsUser(userId);
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

            public List<SyncInfo> getCurrentSyncs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentSyncs();
                    }
                    _reply.readException();
                    List<SyncInfo> _result = _reply.createTypedArrayList(SyncInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<SyncInfo> getCurrentSyncsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentSyncsAsUser(userId);
                    }
                    _reply.readException();
                    List<SyncInfo> _result = _reply.createTypedArrayList(SyncInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncAdapterTypes();
                    }
                    _reply.readException();
                    SyncAdapterType[] _result = (SyncAdapterType[]) _reply.createTypedArray(SyncAdapterType.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SyncAdapterType[] getSyncAdapterTypesAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncAdapterTypesAsUser(userId);
                    }
                    _reply.readException();
                    SyncAdapterType[] _result = (SyncAdapterType[]) _reply.createTypedArray(SyncAdapterType.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getSyncAdapterPackagesForAuthorityAsUser(String authority, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(authority);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncAdapterPackagesForAuthorityAsUser(authority, userId);
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

            public boolean isSyncActive(Account account, String authority, ComponentName cname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSyncActive(account, authority, cname);
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

            public SyncStatusInfo getSyncStatus(Account account, String authority, ComponentName cname) throws RemoteException {
                SyncStatusInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncStatus(account, authority, cname);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SyncStatusInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SyncStatusInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SyncStatusInfo getSyncStatusAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
                SyncStatusInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSyncStatusAsUser(account, authority, cname, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SyncStatusInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SyncStatusInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSyncPending(Account account, String authority, ComponentName cname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSyncPending(account, authority, cname);
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

            public boolean isSyncPendingAsUser(Account account, String authority, ComponentName cname, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authority);
                    if (cname != null) {
                        _data.writeInt(1);
                        cname.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSyncPendingAsUser(account, authority, cname, userId);
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

            public void addStatusChangeListener(int mask, ISyncStatusObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mask);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addStatusChangeListener(mask, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeStatusChangeListener(ISyncStatusObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeStatusChangeListener(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void putCache(String packageName, Uri key, Bundle value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (key != null) {
                        _data.writeInt(1);
                        key.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().putCache(packageName, key, value, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getCache(String packageName, Uri key, int userId) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (key != null) {
                        _data.writeInt(1);
                        key.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCache(packageName, key, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetTodayStats() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetTodayStats();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onDbCorruption(String tag, String message, String stacktrace) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeString(message);
                    _data.writeString(stacktrace);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDbCorruption(tag, message, stacktrace);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContentService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
