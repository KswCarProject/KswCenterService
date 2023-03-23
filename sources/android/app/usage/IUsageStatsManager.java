package android.app.usage;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUsageStatsManager extends IInterface {
    void forceUsageSourceSettingRead() throws RemoteException;

    int getAppStandbyBucket(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException;

    int getUsageSource() throws RemoteException;

    @UnsupportedAppUsage
    boolean isAppInactive(String str, int i) throws RemoteException;

    void onCarrierPrivilegedAppsChanged() throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException;

    ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException;

    UsageEvents queryEvents(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException;

    UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    ParceledListSlice queryUsageStats(int i, long j, long j2, String str) throws RemoteException;

    void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException;

    void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException;

    void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException;

    void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException;

    void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException;

    @UnsupportedAppUsage
    void setAppInactive(String str, boolean z, int i) throws RemoteException;

    void setAppStandbyBucket(String str, int i, int i2) throws RemoteException;

    void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException;

    void unregisterAppUsageObserver(int i, String str) throws RemoteException;

    void unregisterUsageSessionObserver(int i, String str) throws RemoteException;

    void whitelistAppTemporarily(String str, long j, int i) throws RemoteException;

    public static class Default implements IUsageStatsManager {
        public ParceledListSlice queryUsageStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryConfigurationStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        public ParceledListSlice queryEventStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        public UsageEvents queryEvents(long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        public UsageEvents queryEventsForPackage(long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        public UsageEvents queryEventsForUser(long beginTime, long endTime, int userId, String callingPackage) throws RemoteException {
            return null;
        }

        public UsageEvents queryEventsForPackageForUser(long beginTime, long endTime, int userId, String pkg, String callingPackage) throws RemoteException {
            return null;
        }

        public void setAppInactive(String packageName, boolean inactive, int userId) throws RemoteException {
        }

        public boolean isAppInactive(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void whitelistAppTemporarily(String packageName, long duration, int userId) throws RemoteException {
        }

        public void onCarrierPrivilegedAppsChanged() throws RemoteException {
        }

        public void reportChooserSelection(String packageName, int userId, String contentType, String[] annotations, String action) throws RemoteException {
        }

        public int getAppStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        public void setAppStandbyBucket(String packageName, int bucket, int userId) throws RemoteException {
        }

        public ParceledListSlice getAppStandbyBuckets(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        public void setAppStandbyBuckets(ParceledListSlice appBuckets, int userId) throws RemoteException {
        }

        public void registerAppUsageObserver(int observerId, String[] packages, long timeLimitMs, PendingIntent callback, String callingPackage) throws RemoteException {
        }

        public void unregisterAppUsageObserver(int observerId, String callingPackage) throws RemoteException {
        }

        public void registerUsageSessionObserver(int sessionObserverId, String[] observed, long timeLimitMs, long sessionThresholdTimeMs, PendingIntent limitReachedCallbackIntent, PendingIntent sessionEndCallbackIntent, String callingPackage) throws RemoteException {
        }

        public void unregisterUsageSessionObserver(int sessionObserverId, String callingPackage) throws RemoteException {
        }

        public void registerAppUsageLimitObserver(int observerId, String[] packages, long timeLimitMs, long timeUsedMs, PendingIntent callback, String callingPackage) throws RemoteException {
        }

        public void unregisterAppUsageLimitObserver(int observerId, String callingPackage) throws RemoteException {
        }

        public void reportUsageStart(IBinder activity, String token, String callingPackage) throws RemoteException {
        }

        public void reportPastUsageStart(IBinder activity, String token, long timeAgoMs, String callingPackage) throws RemoteException {
        }

        public void reportUsageStop(IBinder activity, String token, String callingPackage) throws RemoteException {
        }

        public int getUsageSource() throws RemoteException {
            return 0;
        }

        public void forceUsageSourceSettingRead() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUsageStatsManager {
        private static final String DESCRIPTOR = "android.app.usage.IUsageStatsManager";
        static final int TRANSACTION_forceUsageSourceSettingRead = 27;
        static final int TRANSACTION_getAppStandbyBucket = 13;
        static final int TRANSACTION_getAppStandbyBuckets = 15;
        static final int TRANSACTION_getUsageSource = 26;
        static final int TRANSACTION_isAppInactive = 9;
        static final int TRANSACTION_onCarrierPrivilegedAppsChanged = 11;
        static final int TRANSACTION_queryConfigurationStats = 2;
        static final int TRANSACTION_queryEventStats = 3;
        static final int TRANSACTION_queryEvents = 4;
        static final int TRANSACTION_queryEventsForPackage = 5;
        static final int TRANSACTION_queryEventsForPackageForUser = 7;
        static final int TRANSACTION_queryEventsForUser = 6;
        static final int TRANSACTION_queryUsageStats = 1;
        static final int TRANSACTION_registerAppUsageLimitObserver = 21;
        static final int TRANSACTION_registerAppUsageObserver = 17;
        static final int TRANSACTION_registerUsageSessionObserver = 19;
        static final int TRANSACTION_reportChooserSelection = 12;
        static final int TRANSACTION_reportPastUsageStart = 24;
        static final int TRANSACTION_reportUsageStart = 23;
        static final int TRANSACTION_reportUsageStop = 25;
        static final int TRANSACTION_setAppInactive = 8;
        static final int TRANSACTION_setAppStandbyBucket = 14;
        static final int TRANSACTION_setAppStandbyBuckets = 16;
        static final int TRANSACTION_unregisterAppUsageLimitObserver = 22;
        static final int TRANSACTION_unregisterAppUsageObserver = 18;
        static final int TRANSACTION_unregisterUsageSessionObserver = 20;
        static final int TRANSACTION_whitelistAppTemporarily = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsageStatsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUsageStatsManager)) {
                return new Proxy(obj);
            }
            return (IUsageStatsManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "queryUsageStats";
                case 2:
                    return "queryConfigurationStats";
                case 3:
                    return "queryEventStats";
                case 4:
                    return "queryEvents";
                case 5:
                    return "queryEventsForPackage";
                case 6:
                    return "queryEventsForUser";
                case 7:
                    return "queryEventsForPackageForUser";
                case 8:
                    return "setAppInactive";
                case 9:
                    return "isAppInactive";
                case 10:
                    return "whitelistAppTemporarily";
                case 11:
                    return "onCarrierPrivilegedAppsChanged";
                case 12:
                    return "reportChooserSelection";
                case 13:
                    return "getAppStandbyBucket";
                case 14:
                    return "setAppStandbyBucket";
                case 15:
                    return "getAppStandbyBuckets";
                case 16:
                    return "setAppStandbyBuckets";
                case 17:
                    return "registerAppUsageObserver";
                case 18:
                    return "unregisterAppUsageObserver";
                case 19:
                    return "registerUsageSessionObserver";
                case 20:
                    return "unregisterUsageSessionObserver";
                case 21:
                    return "registerAppUsageLimitObserver";
                case 22:
                    return "unregisterAppUsageLimitObserver";
                case 23:
                    return "reportUsageStart";
                case 24:
                    return "reportPastUsageStart";
                case 25:
                    return "reportUsageStop";
                case 26:
                    return "getUsageSource";
                case 27:
                    return "forceUsageSourceSettingRead";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v50, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v51, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v53, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX WARNING: type inference failed for: r0v31, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r0v37, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r0v43, types: [android.app.PendingIntent] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r24, android.os.Parcel r25, android.os.Parcel r26, int r27) throws android.os.RemoteException {
            /*
                r23 = this;
                r10 = r23
                r11 = r24
                r12 = r25
                r13 = r26
                java.lang.String r14 = "android.app.usage.IUsageStatsManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r15 = 1
                if (r11 == r0) goto L_0x0395
                r0 = 0
                r8 = 0
                switch(r11) {
                    case 1: goto L_0x0366;
                    case 2: goto L_0x0337;
                    case 3: goto L_0x0308;
                    case 4: goto L_0x02df;
                    case 5: goto L_0x02b6;
                    case 6: goto L_0x0287;
                    case 7: goto L_0x0251;
                    case 8: goto L_0x0236;
                    case 9: goto L_0x0220;
                    case 10: goto L_0x020a;
                    case 11: goto L_0x0200;
                    case 12: goto L_0x01da;
                    case 13: goto L_0x01c0;
                    case 14: goto L_0x01aa;
                    case 15: goto L_0x018b;
                    case 16: goto L_0x016d;
                    case 17: goto L_0x013b;
                    case 18: goto L_0x0129;
                    case 19: goto L_0x00dd;
                    case 20: goto L_0x00cb;
                    case 21: goto L_0x0091;
                    case 22: goto L_0x007f;
                    case 23: goto L_0x0069;
                    case 24: goto L_0x0048;
                    case 25: goto L_0x0032;
                    case 26: goto L_0x0024;
                    case 27: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r24, r25, r26, r27)
                return r0
            L_0x001a:
                r12.enforceInterface(r14)
                r23.forceUsageSourceSettingRead()
                r26.writeNoException()
                return r15
            L_0x0024:
                r12.enforceInterface(r14)
                int r0 = r23.getUsageSource()
                r26.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0032:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r25.readStrongBinder()
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                r10.reportUsageStop(r0, r1, r2)
                r26.writeNoException()
                return r15
            L_0x0048:
                r12.enforceInterface(r14)
                android.os.IBinder r6 = r25.readStrongBinder()
                java.lang.String r7 = r25.readString()
                long r8 = r25.readLong()
                java.lang.String r16 = r25.readString()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r5 = r16
                r0.reportPastUsageStart(r1, r2, r3, r5)
                r26.writeNoException()
                return r15
            L_0x0069:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r25.readStrongBinder()
                java.lang.String r1 = r25.readString()
                java.lang.String r2 = r25.readString()
                r10.reportUsageStart(r0, r1, r2)
                r26.writeNoException()
                return r15
            L_0x007f:
                r12.enforceInterface(r14)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r10.unregisterAppUsageLimitObserver(r0, r1)
                r26.writeNoException()
                return r15
            L_0x0091:
                r12.enforceInterface(r14)
                int r9 = r25.readInt()
                java.lang.String[] r16 = r25.createStringArray()
                long r17 = r25.readLong()
                long r19 = r25.readLong()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x00b4
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x00b2:
                r7 = r0
                goto L_0x00b5
            L_0x00b4:
                goto L_0x00b2
            L_0x00b5:
                java.lang.String r21 = r25.readString()
                r0 = r23
                r1 = r9
                r2 = r16
                r3 = r17
                r5 = r19
                r8 = r21
                r0.registerAppUsageLimitObserver(r1, r2, r3, r5, r7, r8)
                r26.writeNoException()
                return r15
            L_0x00cb:
                r12.enforceInterface(r14)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r10.unregisterUsageSessionObserver(r0, r1)
                r26.writeNoException()
                return r15
            L_0x00dd:
                r12.enforceInterface(r14)
                int r16 = r25.readInt()
                java.lang.String[] r17 = r25.createStringArray()
                long r18 = r25.readLong()
                long r20 = r25.readLong()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0100
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                r7 = r1
                goto L_0x0101
            L_0x0100:
                r7 = r0
            L_0x0101:
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0111
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x010f:
                r8 = r0
                goto L_0x0112
            L_0x0111:
                goto L_0x010f
            L_0x0112:
                java.lang.String r22 = r25.readString()
                r0 = r23
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r20
                r9 = r22
                r0.registerUsageSessionObserver(r1, r2, r3, r5, r7, r8, r9)
                r26.writeNoException()
                return r15
            L_0x0129:
                r12.enforceInterface(r14)
                int r0 = r25.readInt()
                java.lang.String r1 = r25.readString()
                r10.unregisterAppUsageObserver(r0, r1)
                r26.writeNoException()
                return r15
            L_0x013b:
                r12.enforceInterface(r14)
                int r7 = r25.readInt()
                java.lang.String[] r8 = r25.createStringArray()
                long r16 = r25.readLong()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x015a
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x0158:
                r5 = r0
                goto L_0x015b
            L_0x015a:
                goto L_0x0158
            L_0x015b:
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r7
                r2 = r8
                r3 = r16
                r6 = r9
                r0.registerAppUsageObserver(r1, r2, r3, r5, r6)
                r26.writeNoException()
                return r15
            L_0x016d:
                r12.enforceInterface(r14)
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x017f
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x0180
            L_0x017f:
            L_0x0180:
                int r1 = r25.readInt()
                r10.setAppStandbyBuckets(r0, r1)
                r26.writeNoException()
                return r15
            L_0x018b:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                int r1 = r25.readInt()
                android.content.pm.ParceledListSlice r2 = r10.getAppStandbyBuckets(r0, r1)
                r26.writeNoException()
                if (r2 == 0) goto L_0x01a6
                r13.writeInt(r15)
                r2.writeToParcel(r13, r15)
                goto L_0x01a9
            L_0x01a6:
                r13.writeInt(r8)
            L_0x01a9:
                return r15
            L_0x01aa:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                int r1 = r25.readInt()
                int r2 = r25.readInt()
                r10.setAppStandbyBucket(r0, r1, r2)
                r26.writeNoException()
                return r15
            L_0x01c0:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                java.lang.String r1 = r25.readString()
                int r2 = r25.readInt()
                int r3 = r10.getAppStandbyBucket(r0, r1, r2)
                r26.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x01da:
                r12.enforceInterface(r14)
                java.lang.String r6 = r25.readString()
                int r7 = r25.readInt()
                java.lang.String r8 = r25.readString()
                java.lang.String[] r9 = r25.createStringArray()
                java.lang.String r16 = r25.readString()
                r0 = r23
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                r0.reportChooserSelection(r1, r2, r3, r4, r5)
                r26.writeNoException()
                return r15
            L_0x0200:
                r12.enforceInterface(r14)
                r23.onCarrierPrivilegedAppsChanged()
                r26.writeNoException()
                return r15
            L_0x020a:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                long r1 = r25.readLong()
                int r3 = r25.readInt()
                r10.whitelistAppTemporarily(r0, r1, r3)
                r26.writeNoException()
                return r15
            L_0x0220:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                int r1 = r25.readInt()
                boolean r2 = r10.isAppInactive(r0, r1)
                r26.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0236:
                r12.enforceInterface(r14)
                java.lang.String r0 = r25.readString()
                int r1 = r25.readInt()
                if (r1 == 0) goto L_0x0245
                r8 = r15
            L_0x0245:
                r1 = r8
                int r2 = r25.readInt()
                r10.setAppInactive(r0, r1, r2)
                r26.writeNoException()
                return r15
            L_0x0251:
                r12.enforceInterface(r14)
                long r16 = r25.readLong()
                long r18 = r25.readLong()
                int r9 = r25.readInt()
                java.lang.String r20 = r25.readString()
                java.lang.String r21 = r25.readString()
                r0 = r23
                r1 = r16
                r3 = r18
                r5 = r9
                r6 = r20
                r7 = r21
                android.app.usage.UsageEvents r0 = r0.queryEventsForPackageForUser(r1, r3, r5, r6, r7)
                r26.writeNoException()
                if (r0 == 0) goto L_0x0283
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x0286
            L_0x0283:
                r13.writeInt(r8)
            L_0x0286:
                return r15
            L_0x0287:
                r12.enforceInterface(r14)
                long r16 = r25.readLong()
                long r18 = r25.readLong()
                int r7 = r25.readInt()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r16
                r3 = r18
                r5 = r7
                r6 = r9
                android.app.usage.UsageEvents r0 = r0.queryEventsForUser(r1, r3, r5, r6)
                r26.writeNoException()
                if (r0 == 0) goto L_0x02b2
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x02b5
            L_0x02b2:
                r13.writeInt(r8)
            L_0x02b5:
                return r15
            L_0x02b6:
                r12.enforceInterface(r14)
                long r6 = r25.readLong()
                long r16 = r25.readLong()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r6
                r3 = r16
                r5 = r9
                android.app.usage.UsageEvents r0 = r0.queryEventsForPackage(r1, r3, r5)
                r26.writeNoException()
                if (r0 == 0) goto L_0x02db
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x02de
            L_0x02db:
                r13.writeInt(r8)
            L_0x02de:
                return r15
            L_0x02df:
                r12.enforceInterface(r14)
                long r6 = r25.readLong()
                long r16 = r25.readLong()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r6
                r3 = r16
                r5 = r9
                android.app.usage.UsageEvents r0 = r0.queryEvents(r1, r3, r5)
                r26.writeNoException()
                if (r0 == 0) goto L_0x0304
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x0307
            L_0x0304:
                r13.writeInt(r8)
            L_0x0307:
                return r15
            L_0x0308:
                r12.enforceInterface(r14)
                int r7 = r25.readInt()
                long r16 = r25.readLong()
                long r18 = r25.readLong()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r7
                r2 = r16
                r4 = r18
                r6 = r9
                android.content.pm.ParceledListSlice r0 = r0.queryEventStats(r1, r2, r4, r6)
                r26.writeNoException()
                if (r0 == 0) goto L_0x0333
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x0336
            L_0x0333:
                r13.writeInt(r8)
            L_0x0336:
                return r15
            L_0x0337:
                r12.enforceInterface(r14)
                int r7 = r25.readInt()
                long r16 = r25.readLong()
                long r18 = r25.readLong()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r7
                r2 = r16
                r4 = r18
                r6 = r9
                android.content.pm.ParceledListSlice r0 = r0.queryConfigurationStats(r1, r2, r4, r6)
                r26.writeNoException()
                if (r0 == 0) goto L_0x0362
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x0365
            L_0x0362:
                r13.writeInt(r8)
            L_0x0365:
                return r15
            L_0x0366:
                r12.enforceInterface(r14)
                int r7 = r25.readInt()
                long r16 = r25.readLong()
                long r18 = r25.readLong()
                java.lang.String r9 = r25.readString()
                r0 = r23
                r1 = r7
                r2 = r16
                r4 = r18
                r6 = r9
                android.content.pm.ParceledListSlice r0 = r0.queryUsageStats(r1, r2, r4, r6)
                r26.writeNoException()
                if (r0 == 0) goto L_0x0391
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x0394
            L_0x0391:
                r13.writeInt(r8)
            L_0x0394:
                return r15
            L_0x0395:
                r13.writeString(r14)
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.usage.IUsageStatsManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IUsageStatsManager {
            public static IUsageStatsManager sDefaultImpl;
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

            public ParceledListSlice queryUsageStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(bucketType);
                        try {
                            _data.writeLong(beginTime);
                        } catch (Throwable th) {
                            th = th;
                            long j = endTime;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeLong(endTime);
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                                    } else {
                                        _result = null;
                                    }
                                    ParceledListSlice _result2 = _result;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result2;
                                }
                                ParceledListSlice queryUsageStats = Stub.getDefaultImpl().queryUsageStats(bucketType, beginTime, endTime, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return queryUsageStats;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        long j2 = beginTime;
                        long j3 = endTime;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = bucketType;
                    long j22 = beginTime;
                    long j32 = endTime;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public ParceledListSlice queryConfigurationStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(bucketType);
                        try {
                            _data.writeLong(beginTime);
                        } catch (Throwable th) {
                            th = th;
                            long j = endTime;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeLong(endTime);
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                                    } else {
                                        _result = null;
                                    }
                                    ParceledListSlice _result2 = _result;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result2;
                                }
                                ParceledListSlice queryConfigurationStats = Stub.getDefaultImpl().queryConfigurationStats(bucketType, beginTime, endTime, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return queryConfigurationStats;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        long j2 = beginTime;
                        long j3 = endTime;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = bucketType;
                    long j22 = beginTime;
                    long j32 = endTime;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public ParceledListSlice queryEventStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(bucketType);
                        try {
                            _data.writeLong(beginTime);
                        } catch (Throwable th) {
                            th = th;
                            long j = endTime;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeLong(endTime);
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                                    } else {
                                        _result = null;
                                    }
                                    ParceledListSlice _result2 = _result;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result2;
                                }
                                ParceledListSlice queryEventStats = Stub.getDefaultImpl().queryEventStats(bucketType, beginTime, endTime, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return queryEventStats;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        long j2 = beginTime;
                        long j3 = endTime;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = bucketType;
                    long j22 = beginTime;
                    long j32 = endTime;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public UsageEvents queryEvents(long beginTime, long endTime, String callingPackage) throws RemoteException {
                UsageEvents _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryEvents(beginTime, endTime, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UsageEvents.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UsageEvents _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UsageEvents queryEventsForPackage(long beginTime, long endTime, String callingPackage) throws RemoteException {
                UsageEvents _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryEventsForPackage(beginTime, endTime, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UsageEvents.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UsageEvents _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UsageEvents queryEventsForUser(long beginTime, long endTime, int userId, String callingPackage) throws RemoteException {
                UsageEvents _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(beginTime);
                        try {
                            _data.writeLong(endTime);
                        } catch (Throwable th) {
                            th = th;
                            int i = userId;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = UsageEvents.CREATOR.createFromParcel(_reply);
                                    } else {
                                        _result = null;
                                    }
                                    UsageEvents _result2 = _result;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result2;
                                }
                                UsageEvents queryEventsForUser = Stub.getDefaultImpl().queryEventsForUser(beginTime, endTime, userId, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return queryEventsForUser;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        long j = endTime;
                        int i2 = userId;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    long j2 = beginTime;
                    long j3 = endTime;
                    int i22 = userId;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public UsageEvents queryEventsForPackageForUser(long beginTime, long endTime, int userId, String pkg, String callingPackage) throws RemoteException {
                UsageEvents _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(beginTime);
                        try {
                            _data.writeLong(endTime);
                        } catch (Throwable th) {
                            th = th;
                            int i = userId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(userId);
                            _data.writeString(pkg);
                            _data.writeString(callingPackage);
                            if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = UsageEvents.CREATOR.createFromParcel(_reply);
                                } else {
                                    _result = null;
                                }
                                UsageEvents _result2 = _result;
                                _reply.recycle();
                                _data.recycle();
                                return _result2;
                            }
                            UsageEvents queryEventsForPackageForUser = Stub.getDefaultImpl().queryEventsForPackageForUser(beginTime, endTime, userId, pkg, callingPackage);
                            _reply.recycle();
                            _data.recycle();
                            return queryEventsForPackageForUser;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = endTime;
                        int i2 = userId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    long j2 = beginTime;
                    long j3 = endTime;
                    int i22 = userId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setAppInactive(String packageName, boolean inactive, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(inactive);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAppInactive(packageName, inactive, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAppInactive(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAppInactive(packageName, userId);
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

            public void whitelistAppTemporarily(String packageName, long duration, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(duration);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().whitelistAppTemporarily(packageName, duration, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onCarrierPrivilegedAppsChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onCarrierPrivilegedAppsChanged();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportChooserSelection(String packageName, int userId, String contentType, String[] annotations, String action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(contentType);
                    _data.writeStringArray(annotations);
                    _data.writeString(action);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportChooserSelection(packageName, userId, contentType, annotations, action);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getAppStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppStandbyBucket(packageName, callingPackage, userId);
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

            public void setAppStandbyBucket(String packageName, int bucket, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(bucket);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAppStandbyBucket(packageName, bucket, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAppStandbyBuckets(String callingPackage, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppStandbyBuckets(callingPackage, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAppStandbyBuckets(ParceledListSlice appBuckets, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (appBuckets != null) {
                        _data.writeInt(1);
                        appBuckets.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAppStandbyBuckets(appBuckets, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerAppUsageObserver(int observerId, String[] packages, long timeLimitMs, PendingIntent callback, String callingPackage) throws RemoteException {
                PendingIntent pendingIntent = callback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(observerId);
                        try {
                            _data.writeStringArray(packages);
                        } catch (Throwable th) {
                            th = th;
                            long j = timeLimitMs;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeLong(timeLimitMs);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().registerAppUsageObserver(observerId, packages, timeLimitMs, callback, callingPackage);
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
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String[] strArr = packages;
                        long j2 = timeLimitMs;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = observerId;
                    String[] strArr2 = packages;
                    long j22 = timeLimitMs;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterAppUsageObserver(int observerId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterAppUsageObserver(observerId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerUsageSessionObserver(int sessionObserverId, String[] observed, long timeLimitMs, long sessionThresholdTimeMs, PendingIntent limitReachedCallbackIntent, PendingIntent sessionEndCallbackIntent, String callingPackage) throws RemoteException {
                PendingIntent pendingIntent = limitReachedCallbackIntent;
                PendingIntent pendingIntent2 = sessionEndCallbackIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionObserverId);
                        _data.writeStringArray(observed);
                        _data.writeLong(timeLimitMs);
                        _data.writeLong(sessionThresholdTimeMs);
                        if (pendingIntent != null) {
                            _data.writeInt(1);
                            pendingIntent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            _data.writeInt(1);
                            pendingIntent2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeString(callingPackage);
                        if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().registerUsageSessionObserver(sessionObserverId, observed, timeLimitMs, sessionThresholdTimeMs, limitReachedCallbackIntent, sessionEndCallbackIntent, callingPackage);
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
                    int i = sessionObserverId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterUsageSessionObserver(int sessionObserverId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionObserverId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterUsageSessionObserver(sessionObserverId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerAppUsageLimitObserver(int observerId, String[] packages, long timeLimitMs, long timeUsedMs, PendingIntent callback, String callingPackage) throws RemoteException {
                PendingIntent pendingIntent = callback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(observerId);
                        try {
                            _data.writeStringArray(packages);
                            _data.writeLong(timeLimitMs);
                            _data.writeLong(timeUsedMs);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(callingPackage);
                            if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().registerAppUsageLimitObserver(observerId, packages, timeLimitMs, timeUsedMs, callback, callingPackage);
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
                        String[] strArr = packages;
                        String str2 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = observerId;
                    String[] strArr2 = packages;
                    String str22 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterAppUsageLimitObserver(int observerId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterAppUsageLimitObserver(observerId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportUsageStart(IBinder activity, String token, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportUsageStart(activity, token, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportPastUsageStart(IBinder activity, String token, long timeAgoMs, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeLong(timeAgoMs);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportPastUsageStart(activity, token, timeAgoMs, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportUsageStop(IBinder activity, String token, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportUsageStop(activity, token, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUsageSource() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUsageSource();
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

            public void forceUsageSourceSettingRead() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceUsageSourceSettingRead();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUsageStatsManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUsageStatsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
