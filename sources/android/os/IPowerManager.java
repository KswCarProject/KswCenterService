package android.os;

import android.annotation.UnsupportedAppUsage;

public interface IPowerManager extends IInterface {
    void acquireWakeLock(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) throws RemoteException;

    void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2) throws RemoteException;

    void boostScreenBrightness(long j) throws RemoteException;

    void crash(String str) throws RemoteException;

    boolean forceSuspend() throws RemoteException;

    int getLastShutdownReason() throws RemoteException;

    int getLastSleepReason() throws RemoteException;

    int getPowerSaveModeTrigger() throws RemoteException;

    PowerSaveState getPowerSaveState(int i) throws RemoteException;

    @UnsupportedAppUsage
    void goToSleep(long j, int i, int i2) throws RemoteException;

    boolean isDeviceIdleMode() throws RemoteException;

    @UnsupportedAppUsage
    boolean isInteractive() throws RemoteException;

    boolean isLightDeviceIdleMode() throws RemoteException;

    boolean isPowerSaveMode() throws RemoteException;

    boolean isScreenBrightnessBoosted() throws RemoteException;

    boolean isWakeLockLevelSupported(int i) throws RemoteException;

    void nap(long j) throws RemoteException;

    void powerHint(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    void reboot(boolean z, String str, boolean z2) throws RemoteException;

    void rebootSafeMode(boolean z, boolean z2) throws RemoteException;

    @UnsupportedAppUsage
    void releaseWakeLock(IBinder iBinder, int i) throws RemoteException;

    boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException;

    boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException;

    void setAttentionLight(boolean z, int i) throws RemoteException;

    void setDozeAfterScreenOff(boolean z) throws RemoteException;

    boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException;

    boolean setPowerSaveModeEnabled(boolean z) throws RemoteException;

    void setStayOnSetting(int i) throws RemoteException;

    void shutdown(boolean z, String str, boolean z2) throws RemoteException;

    void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException;

    void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) throws RemoteException;

    @UnsupportedAppUsage
    void userActivity(long j, int i, int i2) throws RemoteException;

    void wakeUp(long j, int i, String str, String str2) throws RemoteException;

    public static class Default implements IPowerManager {
        public void acquireWakeLock(IBinder lock, int flags, String tag, String packageName, WorkSource ws, String historyTag) throws RemoteException {
        }

        public void acquireWakeLockWithUid(IBinder lock, int flags, String tag, String packageName, int uidtoblame) throws RemoteException {
        }

        public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {
        }

        public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {
        }

        public void powerHint(int hintId, int data) throws RemoteException {
        }

        public void updateWakeLockWorkSource(IBinder lock, WorkSource ws, String historyTag) throws RemoteException {
        }

        public boolean isWakeLockLevelSupported(int level) throws RemoteException {
            return false;
        }

        public void userActivity(long time, int event, int flags) throws RemoteException {
        }

        public void wakeUp(long time, int reason, String details, String opPackageName) throws RemoteException {
        }

        public void goToSleep(long time, int reason, int flags) throws RemoteException {
        }

        public void nap(long time) throws RemoteException {
        }

        public boolean isInteractive() throws RemoteException {
            return false;
        }

        public boolean isPowerSaveMode() throws RemoteException {
            return false;
        }

        public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
            return null;
        }

        public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
            return false;
        }

        public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold) throws RemoteException {
            return false;
        }

        public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config) throws RemoteException {
            return false;
        }

        public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
            return false;
        }

        public int getPowerSaveModeTrigger() throws RemoteException {
            return 0;
        }

        public boolean isDeviceIdleMode() throws RemoteException {
            return false;
        }

        public boolean isLightDeviceIdleMode() throws RemoteException {
            return false;
        }

        public void reboot(boolean confirm, String reason, boolean wait) throws RemoteException {
        }

        public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {
        }

        public void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException {
        }

        public void crash(String message) throws RemoteException {
        }

        public int getLastShutdownReason() throws RemoteException {
            return 0;
        }

        public int getLastSleepReason() throws RemoteException {
            return 0;
        }

        public void setStayOnSetting(int val) throws RemoteException {
        }

        public void boostScreenBrightness(long time) throws RemoteException {
        }

        public boolean isScreenBrightnessBoosted() throws RemoteException {
            return false;
        }

        public void setAttentionLight(boolean on, int color) throws RemoteException {
        }

        public void setDozeAfterScreenOff(boolean on) throws RemoteException {
        }

        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPowerManager {
        private static final String DESCRIPTOR = "android.os.IPowerManager";
        static final int TRANSACTION_acquireWakeLock = 1;
        static final int TRANSACTION_acquireWakeLockWithUid = 2;
        static final int TRANSACTION_boostScreenBrightness = 29;
        static final int TRANSACTION_crash = 25;
        static final int TRANSACTION_forceSuspend = 33;
        static final int TRANSACTION_getLastShutdownReason = 26;
        static final int TRANSACTION_getLastSleepReason = 27;
        static final int TRANSACTION_getPowerSaveModeTrigger = 19;
        static final int TRANSACTION_getPowerSaveState = 14;
        static final int TRANSACTION_goToSleep = 10;
        static final int TRANSACTION_isDeviceIdleMode = 20;
        static final int TRANSACTION_isInteractive = 12;
        static final int TRANSACTION_isLightDeviceIdleMode = 21;
        static final int TRANSACTION_isPowerSaveMode = 13;
        static final int TRANSACTION_isScreenBrightnessBoosted = 30;
        static final int TRANSACTION_isWakeLockLevelSupported = 7;
        static final int TRANSACTION_nap = 11;
        static final int TRANSACTION_powerHint = 5;
        static final int TRANSACTION_reboot = 22;
        static final int TRANSACTION_rebootSafeMode = 23;
        static final int TRANSACTION_releaseWakeLock = 3;
        static final int TRANSACTION_setAdaptivePowerSaveEnabled = 18;
        static final int TRANSACTION_setAdaptivePowerSavePolicy = 17;
        static final int TRANSACTION_setAttentionLight = 31;
        static final int TRANSACTION_setDozeAfterScreenOff = 32;
        static final int TRANSACTION_setDynamicPowerSaveHint = 16;
        static final int TRANSACTION_setPowerSaveModeEnabled = 15;
        static final int TRANSACTION_setStayOnSetting = 28;
        static final int TRANSACTION_shutdown = 24;
        static final int TRANSACTION_updateWakeLockUids = 4;
        static final int TRANSACTION_updateWakeLockWorkSource = 6;
        static final int TRANSACTION_userActivity = 8;
        static final int TRANSACTION_wakeUp = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPowerManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPowerManager)) {
                return new Proxy(obj);
            }
            return (IPowerManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "acquireWakeLock";
                case 2:
                    return "acquireWakeLockWithUid";
                case 3:
                    return "releaseWakeLock";
                case 4:
                    return "updateWakeLockUids";
                case 5:
                    return "powerHint";
                case 6:
                    return "updateWakeLockWorkSource";
                case 7:
                    return "isWakeLockLevelSupported";
                case 8:
                    return "userActivity";
                case 9:
                    return "wakeUp";
                case 10:
                    return "goToSleep";
                case 11:
                    return "nap";
                case 12:
                    return "isInteractive";
                case 13:
                    return "isPowerSaveMode";
                case 14:
                    return "getPowerSaveState";
                case 15:
                    return "setPowerSaveModeEnabled";
                case 16:
                    return "setDynamicPowerSaveHint";
                case 17:
                    return "setAdaptivePowerSavePolicy";
                case 18:
                    return "setAdaptivePowerSaveEnabled";
                case 19:
                    return "getPowerSaveModeTrigger";
                case 20:
                    return "isDeviceIdleMode";
                case 21:
                    return "isLightDeviceIdleMode";
                case 22:
                    return "reboot";
                case 23:
                    return "rebootSafeMode";
                case 24:
                    return "shutdown";
                case 25:
                    return "crash";
                case 26:
                    return "getLastShutdownReason";
                case 27:
                    return "getLastSleepReason";
                case 28:
                    return "setStayOnSetting";
                case 29:
                    return "boostScreenBrightness";
                case 30:
                    return "isScreenBrightnessBoosted";
                case 31:
                    return "setAttentionLight";
                case 32:
                    return "setDozeAfterScreenOff";
                case 33:
                    return "forceSuspend";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.os.BatterySaverPolicyConfig} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v60 */
        /* JADX WARNING: type inference failed for: r0v61 */
        /* JADX WARNING: type inference failed for: r0v62 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r7 = r18
                r8 = r19
                r9 = r20
                r10 = r21
                java.lang.String r11 = "android.os.IPowerManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x02e7
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x02af;
                    case 2: goto L_0x0289;
                    case 3: goto L_0x0277;
                    case 4: goto L_0x0265;
                    case 5: goto L_0x0256;
                    case 6: goto L_0x0234;
                    case 7: goto L_0x0222;
                    case 8: goto L_0x020c;
                    case 9: goto L_0x01eb;
                    case 10: goto L_0x01d5;
                    case 11: goto L_0x01c7;
                    case 12: goto L_0x01b9;
                    case 13: goto L_0x01ab;
                    case 14: goto L_0x0190;
                    case 15: goto L_0x0179;
                    case 16: goto L_0x015e;
                    case 17: goto L_0x0140;
                    case 18: goto L_0x0129;
                    case 19: goto L_0x011b;
                    case 20: goto L_0x010d;
                    case 21: goto L_0x00ff;
                    case 22: goto L_0x00e0;
                    case 23: goto L_0x00c5;
                    case 24: goto L_0x00a6;
                    case 25: goto L_0x0098;
                    case 26: goto L_0x008a;
                    case 27: goto L_0x007c;
                    case 28: goto L_0x006e;
                    case 29: goto L_0x0060;
                    case 30: goto L_0x0052;
                    case 31: goto L_0x003b;
                    case 32: goto L_0x0028;
                    case 33: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                boolean r0 = r18.forceSuspend()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0028:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0033
                r1 = r12
            L_0x0033:
                r0 = r1
                r7.setDozeAfterScreenOff(r0)
                r21.writeNoException()
                return r12
            L_0x003b:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0046
                r1 = r12
            L_0x0046:
                r0 = r1
                int r1 = r20.readInt()
                r7.setAttentionLight(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0052:
                r9.enforceInterface(r11)
                boolean r0 = r18.isScreenBrightnessBoosted()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0060:
                r9.enforceInterface(r11)
                long r0 = r20.readLong()
                r7.boostScreenBrightness(r0)
                r21.writeNoException()
                return r12
            L_0x006e:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                r7.setStayOnSetting(r0)
                r21.writeNoException()
                return r12
            L_0x007c:
                r9.enforceInterface(r11)
                int r0 = r18.getLastSleepReason()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x008a:
                r9.enforceInterface(r11)
                int r0 = r18.getLastShutdownReason()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0098:
                r9.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                r7.crash(r0)
                r21.writeNoException()
                return r12
            L_0x00a6:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x00b1
                r0 = r12
                goto L_0x00b2
            L_0x00b1:
                r0 = r1
            L_0x00b2:
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x00be
                r1 = r12
            L_0x00be:
                r7.shutdown(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x00c5:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x00d0
                r0 = r12
                goto L_0x00d1
            L_0x00d0:
                r0 = r1
            L_0x00d1:
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00d9
                r1 = r12
            L_0x00d9:
                r7.rebootSafeMode(r0, r1)
                r21.writeNoException()
                return r12
            L_0x00e0:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x00eb
                r0 = r12
                goto L_0x00ec
            L_0x00eb:
                r0 = r1
            L_0x00ec:
                java.lang.String r2 = r20.readString()
                int r3 = r20.readInt()
                if (r3 == 0) goto L_0x00f8
                r1 = r12
            L_0x00f8:
                r7.reboot(r0, r2, r1)
                r21.writeNoException()
                return r12
            L_0x00ff:
                r9.enforceInterface(r11)
                boolean r0 = r18.isLightDeviceIdleMode()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x010d:
                r9.enforceInterface(r11)
                boolean r0 = r18.isDeviceIdleMode()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x011b:
                r9.enforceInterface(r11)
                int r0 = r18.getPowerSaveModeTrigger()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0129:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0134
                r1 = r12
            L_0x0134:
                r0 = r1
                boolean r1 = r7.setAdaptivePowerSaveEnabled(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0140:
                r9.enforceInterface(r11)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0152
                android.os.Parcelable$Creator<android.os.BatterySaverPolicyConfig> r0 = android.os.BatterySaverPolicyConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.BatterySaverPolicyConfig r0 = (android.os.BatterySaverPolicyConfig) r0
                goto L_0x0153
            L_0x0152:
            L_0x0153:
                boolean r1 = r7.setAdaptivePowerSavePolicy(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x015e:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0169
                r1 = r12
            L_0x0169:
                r0 = r1
                int r1 = r20.readInt()
                boolean r2 = r7.setDynamicPowerSaveHint(r0, r1)
                r21.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0179:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0184
                r1 = r12
            L_0x0184:
                r0 = r1
                boolean r1 = r7.setPowerSaveModeEnabled(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0190:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                android.os.PowerSaveState r2 = r7.getPowerSaveState(r0)
                r21.writeNoException()
                if (r2 == 0) goto L_0x01a7
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x01aa
            L_0x01a7:
                r10.writeInt(r1)
            L_0x01aa:
                return r12
            L_0x01ab:
                r9.enforceInterface(r11)
                boolean r0 = r18.isPowerSaveMode()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x01b9:
                r9.enforceInterface(r11)
                boolean r0 = r18.isInteractive()
                r21.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x01c7:
                r9.enforceInterface(r11)
                long r0 = r20.readLong()
                r7.nap(r0)
                r21.writeNoException()
                return r12
            L_0x01d5:
                r9.enforceInterface(r11)
                long r0 = r20.readLong()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                r7.goToSleep(r0, r2, r3)
                r21.writeNoException()
                return r12
            L_0x01eb:
                r9.enforceInterface(r11)
                long r13 = r20.readLong()
                int r6 = r20.readInt()
                java.lang.String r15 = r20.readString()
                java.lang.String r16 = r20.readString()
                r0 = r18
                r1 = r13
                r3 = r6
                r4 = r15
                r5 = r16
                r0.wakeUp(r1, r3, r4, r5)
                r21.writeNoException()
                return r12
            L_0x020c:
                r9.enforceInterface(r11)
                long r0 = r20.readLong()
                int r2 = r20.readInt()
                int r3 = r20.readInt()
                r7.userActivity(r0, r2, r3)
                r21.writeNoException()
                return r12
            L_0x0222:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                boolean r1 = r7.isWakeLockLevelSupported(r0)
                r21.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x0234:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r20.readStrongBinder()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x024a
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x024b
            L_0x024a:
            L_0x024b:
                java.lang.String r2 = r20.readString()
                r7.updateWakeLockWorkSource(r1, r0, r2)
                r21.writeNoException()
                return r12
            L_0x0256:
                r9.enforceInterface(r11)
                int r0 = r20.readInt()
                int r1 = r20.readInt()
                r7.powerHint(r0, r1)
                return r12
            L_0x0265:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                int[] r1 = r20.createIntArray()
                r7.updateWakeLockUids(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0277:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r20.readStrongBinder()
                int r1 = r20.readInt()
                r7.releaseWakeLock(r0, r1)
                r21.writeNoException()
                return r12
            L_0x0289:
                r9.enforceInterface(r11)
                android.os.IBinder r6 = r20.readStrongBinder()
                int r13 = r20.readInt()
                java.lang.String r14 = r20.readString()
                java.lang.String r15 = r20.readString()
                int r16 = r20.readInt()
                r0 = r18
                r1 = r6
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.acquireWakeLockWithUid(r1, r2, r3, r4, r5)
                r21.writeNoException()
                return r12
            L_0x02af:
                r9.enforceInterface(r11)
                android.os.IBinder r13 = r20.readStrongBinder()
                int r14 = r20.readInt()
                java.lang.String r15 = r20.readString()
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x02d2
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
            L_0x02d0:
                r5 = r0
                goto L_0x02d3
            L_0x02d2:
                goto L_0x02d0
            L_0x02d3:
                java.lang.String r17 = r20.readString()
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r6 = r17
                r0.acquireWakeLock(r1, r2, r3, r4, r5, r6)
                r21.writeNoException()
                return r12
            L_0x02e7:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.IPowerManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPowerManager {
            public static IPowerManager sDefaultImpl;
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

            public void acquireWakeLock(IBinder lock, int flags, String tag, String packageName, WorkSource ws, String historyTag) throws RemoteException {
                WorkSource workSource = ws;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(lock);
                        try {
                            _data.writeInt(flags);
                        } catch (Throwable th) {
                            th = th;
                            String str = tag;
                            String str2 = packageName;
                            String str3 = historyTag;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(tag);
                        } catch (Throwable th2) {
                            th = th2;
                            String str22 = packageName;
                            String str32 = historyTag;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i = flags;
                        String str4 = tag;
                        String str222 = packageName;
                        String str322 = historyTag;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(packageName);
                        if (workSource != null) {
                            _data.writeInt(1);
                            workSource.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeString(historyTag);
                            if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().acquireWakeLock(lock, flags, tag, packageName, ws, historyTag);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str3222 = historyTag;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    IBinder iBinder = lock;
                    int i2 = flags;
                    String str42 = tag;
                    String str2222 = packageName;
                    String str32222 = historyTag;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void acquireWakeLockWithUid(IBinder lock, int flags, String tag, String packageName, int uidtoblame) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    _data.writeString(tag);
                    _data.writeString(packageName);
                    _data.writeInt(uidtoblame);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acquireWakeLockWithUid(lock, flags, tag, packageName, uidtoblame);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseWakeLock(lock, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeIntArray(uids);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateWakeLockUids(lock, uids);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void powerHint(int hintId, int data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hintId);
                    _data.writeInt(data);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().powerHint(hintId, data);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateWakeLockWorkSource(IBinder lock, WorkSource ws, String historyTag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(historyTag);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateWakeLockWorkSource(lock, ws, historyTag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWakeLockLevelSupported(int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWakeLockLevelSupported(level);
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

            public void userActivity(long time, int event, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(event);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().userActivity(time, event, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void wakeUp(long time, int reason, String details, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeString(details);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().wakeUp(time, reason, details, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void goToSleep(long time, int reason, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().goToSleep(time, reason, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void nap(long time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().nap(time);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInteractive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInteractive();
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

            public boolean isPowerSaveMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPowerSaveMode();
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

            public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
                PowerSaveState _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceType);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPowerSaveState(serviceType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PowerSaveState.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PowerSaveState _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPowerSaveModeEnabled(mode);
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

            public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerSaveHint);
                    _data.writeInt(disableThreshold);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDynamicPowerSaveHint(powerSaveHint, disableThreshold);
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

            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAdaptivePowerSavePolicy(config);
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

            public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAdaptivePowerSaveEnabled(enabled);
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

            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPowerSaveModeTrigger();
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

            public boolean isDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDeviceIdleMode();
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

            public boolean isLightDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLightDeviceIdleMode();
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

            public void reboot(boolean confirm, String reason, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm);
                    _data.writeString(reason);
                    _data.writeInt(wait);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reboot(confirm, reason, wait);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm);
                    _data.writeInt(wait);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().rebootSafeMode(confirm, wait);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm);
                    _data.writeString(reason);
                    _data.writeInt(wait);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().shutdown(confirm, reason, wait);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void crash(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().crash(message);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLastShutdownReason() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastShutdownReason();
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

            public int getLastSleepReason() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastSleepReason();
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

            public void setStayOnSetting(int val) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(val);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStayOnSetting(val);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void boostScreenBrightness(long time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().boostScreenBrightness(time);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isScreenBrightnessBoosted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isScreenBrightnessBoosted();
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

            public void setAttentionLight(boolean on, int color) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    _data.writeInt(color);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAttentionLight(on, color);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDozeAfterScreenOff(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDozeAfterScreenOff(on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean forceSuspend() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().forceSuspend();
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
        }

        public static boolean setDefaultImpl(IPowerManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPowerManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
