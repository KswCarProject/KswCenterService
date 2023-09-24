package android.p007os;

import android.annotation.UnsupportedAppUsage;

/* renamed from: android.os.IPowerManager */
/* loaded from: classes3.dex */
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

    /* renamed from: android.os.IPowerManager$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IPowerManager {
        @Override // android.p007os.IPowerManager
        public void acquireWakeLock(IBinder lock, int flags, String tag, String packageName, WorkSource ws, String historyTag) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void acquireWakeLockWithUid(IBinder lock, int flags, String tag, String packageName, int uidtoblame) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void powerHint(int hintId, int data) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void updateWakeLockWorkSource(IBinder lock, WorkSource ws, String historyTag) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public boolean isWakeLockLevelSupported(int level) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public void userActivity(long time, int event, int flags) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void wakeUp(long time, int reason, String details, String opPackageName) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void goToSleep(long time, int reason, int flags) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void nap(long time) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public boolean isInteractive() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public boolean isPowerSaveMode() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
            return null;
        }

        @Override // android.p007os.IPowerManager
        public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public int getPowerSaveModeTrigger() throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IPowerManager
        public boolean isDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public boolean isLightDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public void reboot(boolean confirm, String reason, boolean wait) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void crash(String message) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public int getLastShutdownReason() throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IPowerManager
        public int getLastSleepReason() throws RemoteException {
            return 0;
        }

        @Override // android.p007os.IPowerManager
        public void setStayOnSetting(int val) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void boostScreenBrightness(long time) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public boolean isScreenBrightnessBoosted() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IPowerManager
        public void setAttentionLight(boolean on, int color) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public void setDozeAfterScreenOff(boolean on) throws RemoteException {
        }

        @Override // android.p007os.IPowerManager
        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IPowerManager$Stub */
    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof IPowerManager)) {
                return (IPowerManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg2;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    String _arg22 = data.readString();
                    String _arg3 = data.readString();
                    WorkSource _arg4 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    String _arg5 = data.readString();
                    acquireWakeLock(_arg0, _arg1, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg12 = data.readInt();
                    String _arg23 = data.readString();
                    String _arg32 = data.readString();
                    int _arg42 = data.readInt();
                    acquireWakeLockWithUid(_arg02, _arg12, _arg23, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg03 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    releaseWakeLock(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg04 = data.readStrongBinder();
                    int[] _arg14 = data.createIntArray();
                    updateWakeLockUids(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    powerHint(_arg05, _arg15);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg06 = data.readStrongBinder();
                    WorkSource _arg16 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    updateWakeLockWorkSource(_arg06, _arg16, data.readString());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    boolean isWakeLockLevelSupported = isWakeLockLevelSupported(_arg07);
                    reply.writeNoException();
                    reply.writeInt(isWakeLockLevelSupported ? 1 : 0);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg08 = data.readLong();
                    int _arg17 = data.readInt();
                    userActivity(_arg08, _arg17, data.readInt());
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg09 = data.readLong();
                    int _arg18 = data.readInt();
                    String _arg24 = data.readString();
                    String _arg33 = data.readString();
                    wakeUp(_arg09, _arg18, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg010 = data.readLong();
                    int _arg19 = data.readInt();
                    goToSleep(_arg010, _arg19, data.readInt());
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg011 = data.readLong();
                    nap(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isInteractive = isInteractive();
                    reply.writeNoException();
                    reply.writeInt(isInteractive ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isPowerSaveMode = isPowerSaveMode();
                    reply.writeNoException();
                    reply.writeInt(isPowerSaveMode ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    PowerSaveState _result = getPowerSaveState(_arg012);
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    boolean _arg013 = _arg2;
                    boolean powerSaveModeEnabled = setPowerSaveModeEnabled(_arg013);
                    reply.writeNoException();
                    reply.writeInt(powerSaveModeEnabled ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    boolean _arg014 = _arg2;
                    int _arg110 = data.readInt();
                    boolean dynamicPowerSaveHint = setDynamicPowerSaveHint(_arg014, _arg110);
                    reply.writeNoException();
                    reply.writeInt(dynamicPowerSaveHint ? 1 : 0);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    BatterySaverPolicyConfig _arg015 = data.readInt() != 0 ? BatterySaverPolicyConfig.CREATOR.createFromParcel(data) : null;
                    boolean adaptivePowerSavePolicy = setAdaptivePowerSavePolicy(_arg015);
                    reply.writeNoException();
                    reply.writeInt(adaptivePowerSavePolicy ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    boolean _arg016 = _arg2;
                    boolean adaptivePowerSaveEnabled = setAdaptivePowerSaveEnabled(_arg016);
                    reply.writeNoException();
                    reply.writeInt(adaptivePowerSaveEnabled ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = getPowerSaveModeTrigger();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isDeviceIdleMode = isDeviceIdleMode();
                    reply.writeNoException();
                    reply.writeInt(isDeviceIdleMode ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isLightDeviceIdleMode = isLightDeviceIdleMode();
                    reply.writeNoException();
                    reply.writeInt(isLightDeviceIdleMode ? 1 : 0);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg017 = data.readInt() != 0;
                    String _arg111 = data.readString();
                    _arg2 = data.readInt() != 0;
                    reboot(_arg017, _arg111, _arg2);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg018 = data.readInt() != 0;
                    _arg2 = data.readInt() != 0;
                    rebootSafeMode(_arg018, _arg2);
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg019 = data.readInt() != 0;
                    String _arg112 = data.readString();
                    _arg2 = data.readInt() != 0;
                    shutdown(_arg019, _arg112, _arg2);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    crash(_arg020);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    int _result3 = getLastShutdownReason();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    int _result4 = getLastSleepReason();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg021 = data.readInt();
                    setStayOnSetting(_arg021);
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg022 = data.readLong();
                    boostScreenBrightness(_arg022);
                    reply.writeNoException();
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isScreenBrightnessBoosted = isScreenBrightnessBoosted();
                    reply.writeNoException();
                    reply.writeInt(isScreenBrightnessBoosted ? 1 : 0);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    boolean _arg023 = _arg2;
                    int _arg113 = data.readInt();
                    setAttentionLight(_arg023, _arg113);
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    boolean _arg024 = _arg2;
                    setDozeAfterScreenOff(_arg024);
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    boolean forceSuspend = forceSuspend();
                    reply.writeNoException();
                    reply.writeInt(forceSuspend ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IPowerManager$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IPowerManager {
            public static IPowerManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.p007os.IPowerManager
            public void acquireWakeLock(IBinder lock, int flags, String tag, String packageName, WorkSource ws, String historyTag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(lock);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(flags);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(tag);
                    try {
                        _data.writeString(packageName);
                        if (ws != null) {
                            _data.writeInt(1);
                            ws.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(historyTag);
                        boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().acquireWakeLock(lock, flags, tag, packageName, ws, historyTag);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.p007os.IPowerManager
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
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acquireWakeLockWithUid(lock, flags, tag, packageName, uidtoblame);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseWakeLock(lock, flags);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeIntArray(uids);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWakeLockUids(lock, uids);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void powerHint(int hintId, int data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hintId);
                    _data.writeInt(data);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().powerHint(hintId, data);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
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
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWakeLockWorkSource(lock, ws, historyTag);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isWakeLockLevelSupported(int level) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWakeLockLevelSupported(level);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void userActivity(long time, int event, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(event);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userActivity(time, event, flags);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void wakeUp(long time, int reason, String details, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeString(details);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wakeUp(time, reason, details, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void goToSleep(long time, int reason, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().goToSleep(time, reason, flags);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void nap(long time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().nap(time);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isInteractive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInteractive();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isPowerSaveMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPowerSaveMode();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
                PowerSaveState _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceType);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPowerSaveState(serviceType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PowerSaveState.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode ? 1 : 0);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPowerSaveModeEnabled(mode);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerSaveHint ? 1 : 0);
                    _data.writeInt(disableThreshold);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDynamicPowerSaveHint(powerSaveHint, disableThreshold);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAdaptivePowerSavePolicy(config);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAdaptivePowerSaveEnabled(enabled);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPowerSaveModeTrigger();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDeviceIdleMode();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isLightDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isLightDeviceIdleMode();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void reboot(boolean confirm, String reason, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm ? 1 : 0);
                    _data.writeString(reason);
                    _data.writeInt(wait ? 1 : 0);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reboot(confirm, reason, wait);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm ? 1 : 0);
                    _data.writeInt(wait ? 1 : 0);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rebootSafeMode(confirm, wait);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(confirm ? 1 : 0);
                    _data.writeString(reason);
                    _data.writeInt(wait ? 1 : 0);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown(confirm, reason, wait);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void crash(String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().crash(message);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public int getLastShutdownReason() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastShutdownReason();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public int getLastSleepReason() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastSleepReason();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void setStayOnSetting(int val) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(val);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStayOnSetting(val);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void boostScreenBrightness(long time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().boostScreenBrightness(time);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean isScreenBrightnessBoosted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isScreenBrightnessBoosted();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void setAttentionLight(boolean on, int color) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    _data.writeInt(color);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAttentionLight(on, color);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public void setDozeAfterScreenOff(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDozeAfterScreenOff(on);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IPowerManager
            public boolean forceSuspend() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().forceSuspend();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPowerManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPowerManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
