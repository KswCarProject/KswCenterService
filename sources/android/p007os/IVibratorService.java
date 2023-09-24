package android.p007os;

/* renamed from: android.os.IVibratorService */
/* loaded from: classes3.dex */
public interface IVibratorService extends IInterface {
    void cancelVibrate(IBinder iBinder) throws RemoteException;

    boolean hasAmplitudeControl() throws RemoteException;

    boolean hasVibrator() throws RemoteException;

    void vibrate(int i, String str, VibrationEffect vibrationEffect, int i2, String str2, IBinder iBinder) throws RemoteException;

    /* renamed from: android.os.IVibratorService$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IVibratorService {
        @Override // android.p007os.IVibratorService
        public boolean hasVibrator() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IVibratorService
        public boolean hasAmplitudeControl() throws RemoteException {
            return false;
        }

        @Override // android.p007os.IVibratorService
        public void vibrate(int uid, String opPkg, VibrationEffect effect, int usageHint, String reason, IBinder token) throws RemoteException {
        }

        @Override // android.p007os.IVibratorService
        public void cancelVibrate(IBinder token) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.IVibratorService$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IVibratorService {
        private static final String DESCRIPTOR = "android.os.IVibratorService";
        static final int TRANSACTION_cancelVibrate = 4;
        static final int TRANSACTION_hasAmplitudeControl = 2;
        static final int TRANSACTION_hasVibrator = 1;
        static final int TRANSACTION_vibrate = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVibratorService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVibratorService)) {
                return (IVibratorService) iin;
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
                    return "hasVibrator";
                case 2:
                    return "hasAmplitudeControl";
                case 3:
                    return "vibrate";
                case 4:
                    return "cancelVibrate";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    boolean hasVibrator = hasVibrator();
                    reply.writeNoException();
                    reply.writeInt(hasVibrator ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    boolean hasAmplitudeControl = hasAmplitudeControl();
                    reply.writeNoException();
                    reply.writeInt(hasAmplitudeControl ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    VibrationEffect _arg2 = data.readInt() != 0 ? VibrationEffect.CREATOR.createFromParcel(data) : null;
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    IBinder _arg5 = data.readStrongBinder();
                    vibrate(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg02 = data.readStrongBinder();
                    cancelVibrate(_arg02);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.os.IVibratorService$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IVibratorService {
            public static IVibratorService sDefaultImpl;
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

            @Override // android.p007os.IVibratorService
            public boolean hasVibrator() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasVibrator();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IVibratorService
            public boolean hasAmplitudeControl() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasAmplitudeControl();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p007os.IVibratorService
            public void vibrate(int uid, String opPkg, VibrationEffect effect, int usageHint, String reason, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(uid);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(opPkg);
                    if (effect != null) {
                        _data.writeInt(1);
                        effect.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(usageHint);
                    try {
                        _data.writeString(reason);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongBinder(token);
                        boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().vibrate(uid, opPkg, effect, usageHint, reason, token);
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

            @Override // android.p007os.IVibratorService
            public void cancelVibrate(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelVibrate(token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVibratorService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IVibratorService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
