package com.android.internal.policy;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IKeyguardService extends IInterface {
    void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) throws RemoteException;

    void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

    @UnsupportedAppUsage
    void doKeyguardTimeout(Bundle bundle) throws RemoteException;

    void onBootCompleted() throws RemoteException;

    void onDreamingStarted() throws RemoteException;

    void onDreamingStopped() throws RemoteException;

    void onFinishedGoingToSleep(int i, boolean z) throws RemoteException;

    void onFinishedWakingUp() throws RemoteException;

    void onScreenTurnedOff() throws RemoteException;

    void onScreenTurnedOn() throws RemoteException;

    void onScreenTurningOff() throws RemoteException;

    void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) throws RemoteException;

    void onShortPowerPressedGoHome() throws RemoteException;

    void onStartedGoingToSleep(int i) throws RemoteException;

    void onStartedWakingUp() throws RemoteException;

    void onSystemReady() throws RemoteException;

    void setCurrentUser(int i) throws RemoteException;

    @UnsupportedAppUsage
    void setKeyguardEnabled(boolean z) throws RemoteException;

    void setOccluded(boolean z, boolean z2) throws RemoteException;

    void setSwitchingUser(boolean z) throws RemoteException;

    void startKeyguardExitAnimation(long j, long j2) throws RemoteException;

    void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) throws RemoteException;

    public static class Default implements IKeyguardService {
        public void setOccluded(boolean isOccluded, boolean animate) throws RemoteException {
        }

        public void addStateMonitorCallback(IKeyguardStateCallback callback) throws RemoteException {
        }

        public void verifyUnlock(IKeyguardExitCallback callback) throws RemoteException {
        }

        public void dismiss(IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
        }

        public void onDreamingStarted() throws RemoteException {
        }

        public void onDreamingStopped() throws RemoteException {
        }

        public void onStartedGoingToSleep(int reason) throws RemoteException {
        }

        public void onFinishedGoingToSleep(int reason, boolean cameraGestureTriggered) throws RemoteException {
        }

        public void onStartedWakingUp() throws RemoteException {
        }

        public void onFinishedWakingUp() throws RemoteException {
        }

        public void onScreenTurningOn(IKeyguardDrawnCallback callback) throws RemoteException {
        }

        public void onScreenTurnedOn() throws RemoteException {
        }

        public void onScreenTurningOff() throws RemoteException {
        }

        public void onScreenTurnedOff() throws RemoteException {
        }

        public void setKeyguardEnabled(boolean enabled) throws RemoteException {
        }

        public void onSystemReady() throws RemoteException {
        }

        public void doKeyguardTimeout(Bundle options) throws RemoteException {
        }

        public void setSwitchingUser(boolean switching) throws RemoteException {
        }

        public void setCurrentUser(int userId) throws RemoteException {
        }

        public void onBootCompleted() throws RemoteException {
        }

        public void startKeyguardExitAnimation(long startTime, long fadeoutDuration) throws RemoteException {
        }

        public void onShortPowerPressedGoHome() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IKeyguardService {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardService";
        static final int TRANSACTION_addStateMonitorCallback = 2;
        static final int TRANSACTION_dismiss = 4;
        static final int TRANSACTION_doKeyguardTimeout = 17;
        static final int TRANSACTION_onBootCompleted = 20;
        static final int TRANSACTION_onDreamingStarted = 5;
        static final int TRANSACTION_onDreamingStopped = 6;
        static final int TRANSACTION_onFinishedGoingToSleep = 8;
        static final int TRANSACTION_onFinishedWakingUp = 10;
        static final int TRANSACTION_onScreenTurnedOff = 14;
        static final int TRANSACTION_onScreenTurnedOn = 12;
        static final int TRANSACTION_onScreenTurningOff = 13;
        static final int TRANSACTION_onScreenTurningOn = 11;
        static final int TRANSACTION_onShortPowerPressedGoHome = 22;
        static final int TRANSACTION_onStartedGoingToSleep = 7;
        static final int TRANSACTION_onStartedWakingUp = 9;
        static final int TRANSACTION_onSystemReady = 16;
        static final int TRANSACTION_setCurrentUser = 19;
        static final int TRANSACTION_setKeyguardEnabled = 15;
        static final int TRANSACTION_setOccluded = 1;
        static final int TRANSACTION_setSwitchingUser = 18;
        static final int TRANSACTION_startKeyguardExitAnimation = 21;
        static final int TRANSACTION_verifyUnlock = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IKeyguardService)) {
                return new Proxy(obj);
            }
            return (IKeyguardService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setOccluded";
                case 2:
                    return "addStateMonitorCallback";
                case 3:
                    return "verifyUnlock";
                case 4:
                    return "dismiss";
                case 5:
                    return "onDreamingStarted";
                case 6:
                    return "onDreamingStopped";
                case 7:
                    return "onStartedGoingToSleep";
                case 8:
                    return "onFinishedGoingToSleep";
                case 9:
                    return "onStartedWakingUp";
                case 10:
                    return "onFinishedWakingUp";
                case 11:
                    return "onScreenTurningOn";
                case 12:
                    return "onScreenTurnedOn";
                case 13:
                    return "onScreenTurningOff";
                case 14:
                    return "onScreenTurnedOff";
                case 15:
                    return "setKeyguardEnabled";
                case 16:
                    return "onSystemReady";
                case 17:
                    return "doKeyguardTimeout";
                case 18:
                    return "setSwitchingUser";
                case 19:
                    return "setCurrentUser";
                case 20:
                    return "onBootCompleted";
                case 21:
                    return "startKeyguardExitAnimation";
                case 22:
                    return "onShortPowerPressedGoHome";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.internal.policy.IKeyguardService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x012b
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x0113;
                    case 2: goto L_0x0104;
                    case 3: goto L_0x00f5;
                    case 4: goto L_0x00d6;
                    case 5: goto L_0x00cf;
                    case 6: goto L_0x00c8;
                    case 7: goto L_0x00bd;
                    case 8: goto L_0x00aa;
                    case 9: goto L_0x00a3;
                    case 10: goto L_0x009c;
                    case 11: goto L_0x008d;
                    case 12: goto L_0x0086;
                    case 13: goto L_0x007f;
                    case 14: goto L_0x0078;
                    case 15: goto L_0x0068;
                    case 16: goto L_0x0061;
                    case 17: goto L_0x004a;
                    case 18: goto L_0x003a;
                    case 19: goto L_0x002f;
                    case 20: goto L_0x0028;
                    case 21: goto L_0x0019;
                    case 22: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                r7.onShortPowerPressedGoHome()
                return r2
            L_0x0019:
                r9.enforceInterface(r0)
                long r3 = r9.readLong()
                long r5 = r9.readLong()
                r7.startKeyguardExitAnimation(r3, r5)
                return r2
            L_0x0028:
                r9.enforceInterface(r0)
                r7.onBootCompleted()
                return r2
            L_0x002f:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.setCurrentUser(r1)
                return r2
            L_0x003a:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0045
                r3 = r2
            L_0x0045:
                r1 = r3
                r7.setSwitchingUser(r1)
                return r2
            L_0x004a:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x005c
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x005d
            L_0x005c:
            L_0x005d:
                r7.doKeyguardTimeout(r1)
                return r2
            L_0x0061:
                r9.enforceInterface(r0)
                r7.onSystemReady()
                return r2
            L_0x0068:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0073
                r3 = r2
            L_0x0073:
                r1 = r3
                r7.setKeyguardEnabled(r1)
                return r2
            L_0x0078:
                r9.enforceInterface(r0)
                r7.onScreenTurnedOff()
                return r2
            L_0x007f:
                r9.enforceInterface(r0)
                r7.onScreenTurningOff()
                return r2
            L_0x0086:
                r9.enforceInterface(r0)
                r7.onScreenTurnedOn()
                return r2
            L_0x008d:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.internal.policy.IKeyguardDrawnCallback r1 = com.android.internal.policy.IKeyguardDrawnCallback.Stub.asInterface(r1)
                r7.onScreenTurningOn(r1)
                return r2
            L_0x009c:
                r9.enforceInterface(r0)
                r7.onFinishedWakingUp()
                return r2
            L_0x00a3:
                r9.enforceInterface(r0)
                r7.onStartedWakingUp()
                return r2
            L_0x00aa:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00b9
                r3 = r2
            L_0x00b9:
                r7.onFinishedGoingToSleep(r1, r3)
                return r2
            L_0x00bd:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.onStartedGoingToSleep(r1)
                return r2
            L_0x00c8:
                r9.enforceInterface(r0)
                r7.onDreamingStopped()
                return r2
            L_0x00cf:
                r9.enforceInterface(r0)
                r7.onDreamingStarted()
                return r2
            L_0x00d6:
                r9.enforceInterface(r0)
                android.os.IBinder r3 = r9.readStrongBinder()
                com.android.internal.policy.IKeyguardDismissCallback r3 = com.android.internal.policy.IKeyguardDismissCallback.Stub.asInterface(r3)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00f0
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x00f1
            L_0x00f0:
            L_0x00f1:
                r7.dismiss(r3, r1)
                return r2
            L_0x00f5:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.internal.policy.IKeyguardExitCallback r1 = com.android.internal.policy.IKeyguardExitCallback.Stub.asInterface(r1)
                r7.verifyUnlock(r1)
                return r2
            L_0x0104:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                com.android.internal.policy.IKeyguardStateCallback r1 = com.android.internal.policy.IKeyguardStateCallback.Stub.asInterface(r1)
                r7.addStateMonitorCallback(r1)
                return r2
            L_0x0113:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x011e
                r1 = r2
                goto L_0x011f
            L_0x011e:
                r1 = r3
            L_0x011f:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0127
                r3 = r2
            L_0x0127:
                r7.setOccluded(r1, r3)
                return r2
            L_0x012b:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.IKeyguardService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IKeyguardService {
            public static IKeyguardService sDefaultImpl;
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

            public void setOccluded(boolean isOccluded, boolean animate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isOccluded);
                    _data.writeInt(animate);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setOccluded(isOccluded, animate);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addStateMonitorCallback(IKeyguardStateCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addStateMonitorCallback(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void verifyUnlock(IKeyguardExitCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().verifyUnlock(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dismiss(IKeyguardDismissCallback callback, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dismiss(callback, message);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDreamingStarted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDreamingStarted();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDreamingStopped() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDreamingStopped();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStartedGoingToSleep(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStartedGoingToSleep(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFinishedGoingToSleep(int reason, boolean cameraGestureTriggered) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    _data.writeInt(cameraGestureTriggered);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFinishedGoingToSleep(reason, cameraGestureTriggered);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStartedWakingUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStartedWakingUp();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFinishedWakingUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFinishedWakingUp();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onScreenTurningOn(IKeyguardDrawnCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onScreenTurningOn(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onScreenTurnedOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onScreenTurnedOn();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onScreenTurningOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onScreenTurningOff();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onScreenTurnedOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onScreenTurnedOff();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setKeyguardEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setKeyguardEnabled(enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSystemReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSystemReady();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void doKeyguardTimeout(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().doKeyguardTimeout(options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSwitchingUser(boolean switching) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(switching);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSwitchingUser(switching);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCurrentUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCurrentUser(userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onBootCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onBootCompleted();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startKeyguardExitAnimation(long startTime, long fadeoutDuration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(startTime);
                    _data.writeLong(fadeoutDuration);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startKeyguardExitAnimation(startTime, fadeoutDuration);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onShortPowerPressedGoHome() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onShortPowerPressedGoHome();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IKeyguardService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IKeyguardService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
