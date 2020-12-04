package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricServiceReceiver extends IInterface {
    void onAcquired(int i, String str) throws RemoteException;

    void onAuthenticationFailed() throws RemoteException;

    void onAuthenticationSucceeded() throws RemoteException;

    void onDialogDismissed(int i) throws RemoteException;

    void onError(int i, String str) throws RemoteException;

    public static class Default implements IBiometricServiceReceiver {
        public void onAuthenticationSucceeded() throws RemoteException {
        }

        public void onAuthenticationFailed() throws RemoteException {
        }

        public void onError(int error, String message) throws RemoteException {
        }

        public void onAcquired(int acquiredInfo, String message) throws RemoteException {
        }

        public void onDialogDismissed(int reason) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBiometricServiceReceiver {
        private static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceReceiver";
        static final int TRANSACTION_onAcquired = 4;
        static final int TRANSACTION_onAuthenticationFailed = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 1;
        static final int TRANSACTION_onDialogDismissed = 5;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBiometricServiceReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBiometricServiceReceiver)) {
                return new Proxy(obj);
            }
            return (IBiometricServiceReceiver) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onAuthenticationSucceeded";
                case 2:
                    return "onAuthenticationFailed";
                case 3:
                    return "onError";
                case 4:
                    return "onAcquired";
                case 5:
                    return "onDialogDismissed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onAuthenticationSucceeded();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onAuthenticationFailed();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onError(data.readInt(), data.readString());
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        onAcquired(data.readInt(), data.readString());
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onDialogDismissed(data.readInt());
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IBiometricServiceReceiver {
            public static IBiometricServiceReceiver sDefaultImpl;
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

            public void onAuthenticationSucceeded() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAuthenticationSucceeded();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAuthenticationFailed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAuthenticationFailed();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int error, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    _data.writeString(message);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(error, message);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAcquired(int acquiredInfo, String message) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(acquiredInfo);
                    _data.writeString(message);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAcquired(acquiredInfo, message);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDialogDismissed(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDialogDismissed(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBiometricServiceReceiver impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBiometricServiceReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
