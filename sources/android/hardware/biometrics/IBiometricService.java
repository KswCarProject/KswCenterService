package android.hardware.biometrics;

import android.hardware.biometrics.IBiometricConfirmDeviceCredentialCallback;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IBiometricService extends IInterface {
    void authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, Bundle bundle, IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException;

    int canAuthenticate(String str) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str) throws RemoteException;

    void onConfirmDeviceCredentialError(int i, String str) throws RemoteException;

    void onConfirmDeviceCredentialSuccess() throws RemoteException;

    void onReadyForAuthentication(int i, boolean z, int i2) throws RemoteException;

    void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) throws RemoteException;

    void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException;

    void resetLockout(byte[] bArr) throws RemoteException;

    void setActiveUser(int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IBiometricService {
        @Override // android.hardware.biometrics.IBiometricService
        public void authenticate(IBinder token, long sessionId, int userId, IBiometricServiceReceiver receiver, String opPackageName, Bundle bundle, IBiometricConfirmDeviceCredentialCallback callback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void cancelAuthentication(IBinder token, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int canAuthenticate(String opPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback callback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void setActiveUser(int userId) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void onReadyForAuthentication(int cookie, boolean requireConfirmation, int userId) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockout(byte[] token) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void onConfirmDeviceCredentialSuccess() throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void onConfirmDeviceCredentialError(int error, String message) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback callback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IBiometricService {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricService";
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_canAuthenticate = 3;
        static final int TRANSACTION_cancelAuthentication = 2;
        static final int TRANSACTION_onConfirmDeviceCredentialError = 9;
        static final int TRANSACTION_onConfirmDeviceCredentialSuccess = 8;
        static final int TRANSACTION_onReadyForAuthentication = 6;
        static final int TRANSACTION_registerCancellationCallback = 10;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 4;
        static final int TRANSACTION_resetLockout = 7;
        static final int TRANSACTION_setActiveUser = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBiometricService)) {
                return (IBiometricService) iin;
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
                    return "authenticate";
                case 2:
                    return "cancelAuthentication";
                case 3:
                    return "canAuthenticate";
                case 4:
                    return "registerEnabledOnKeyguardCallback";
                case 5:
                    return "setActiveUser";
                case 6:
                    return "onReadyForAuthentication";
                case 7:
                    return "resetLockout";
                case 8:
                    return "onConfirmDeviceCredentialSuccess";
                case 9:
                    return "onConfirmDeviceCredentialError";
                case 10:
                    return "registerCancellationCallback";
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
                    IBinder _arg0 = data.readStrongBinder();
                    long _arg1 = data.readLong();
                    int _arg2 = data.readInt();
                    IBiometricServiceReceiver _arg3 = IBiometricServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    String _arg4 = data.readString();
                    Bundle _arg5 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    IBiometricConfirmDeviceCredentialCallback _arg6 = IBiometricConfirmDeviceCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    authenticate(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg02 = data.readStrongBinder();
                    String _arg12 = data.readString();
                    cancelAuthentication(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    int _result = canAuthenticate(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    IBiometricEnabledOnKeyguardCallback _arg04 = IBiometricEnabledOnKeyguardCallback.Stub.asInterface(data.readStrongBinder());
                    registerEnabledOnKeyguardCallback(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    setActiveUser(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    boolean _arg13 = data.readInt() != 0;
                    int _arg22 = data.readInt();
                    onReadyForAuthentication(_arg06, _arg13, _arg22);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg07 = data.createByteArray();
                    resetLockout(_arg07);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    onConfirmDeviceCredentialSuccess();
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    String _arg14 = data.readString();
                    onConfirmDeviceCredentialError(_arg08, _arg14);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IBiometricConfirmDeviceCredentialCallback _arg09 = IBiometricConfirmDeviceCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    registerCancellationCallback(_arg09);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IBiometricService {
            public static IBiometricService sDefaultImpl;
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

            @Override // android.hardware.biometrics.IBiometricService
            public void authenticate(IBinder token, long sessionId, int userId, IBiometricServiceReceiver receiver, String opPackageName, Bundle bundle, IBiometricConfirmDeviceCredentialCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(token);
                    try {
                        _data.writeLong(sessionId);
                        _data.writeInt(userId);
                        _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                        _data.writeString(opPackageName);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                        boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().authenticate(token, sessionId, userId, receiver, opPackageName, bundle, callback);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
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
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void cancelAuthentication(IBinder token, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAuthentication(token, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int canAuthenticate(String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canAuthenticate(opPackageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerEnabledOnKeyguardCallback(callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void setActiveUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActiveUser(userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onReadyForAuthentication(int cookie, boolean requireConfirmation, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cookie);
                    _data.writeInt(requireConfirmation ? 1 : 0);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReadyForAuthentication(cookie, requireConfirmation, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockout(byte[] token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(token);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetLockout(token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onConfirmDeviceCredentialSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfirmDeviceCredentialSuccess();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onConfirmDeviceCredentialError(int error, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    _data.writeString(message);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfirmDeviceCredentialError(error, message);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerCancellationCallback(callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBiometricService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IBiometricService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
