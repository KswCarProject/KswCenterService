package android.accounts;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountAuthenticatorResponse extends IInterface {
    @UnsupportedAppUsage
    void onError(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    void onRequestContinued() throws RemoteException;

    @UnsupportedAppUsage
    void onResult(Bundle bundle) throws RemoteException;

    public static class Default implements IAccountAuthenticatorResponse {
        public void onResult(Bundle value) throws RemoteException {
        }

        public void onRequestContinued() throws RemoteException {
        }

        public void onError(int errorCode, String errorMessage) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccountAuthenticatorResponse {
        private static final String DESCRIPTOR = "android.accounts.IAccountAuthenticatorResponse";
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccountAuthenticatorResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccountAuthenticatorResponse)) {
                return new Proxy(obj);
            }
            return (IAccountAuthenticatorResponse) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onResult";
                case 2:
                    return "onRequestContinued";
                case 3:
                    return "onError";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg0;
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = Bundle.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        onResult(_arg0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onRequestContinued();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onError(data.readInt(), data.readString());
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IAccountAuthenticatorResponse {
            public static IAccountAuthenticatorResponse sDefaultImpl;
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

            public void onResult(Bundle value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onResult(value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRequestContinued() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRequestContinued();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int errorCode, String errorMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(errorCode, errorMessage);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccountAuthenticatorResponse impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccountAuthenticatorResponse getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
