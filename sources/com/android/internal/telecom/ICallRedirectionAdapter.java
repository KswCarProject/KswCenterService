package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;

public interface ICallRedirectionAdapter extends IInterface {
    void cancelCall() throws RemoteException;

    void placeCallUnmodified() throws RemoteException;

    void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    public static class Default implements ICallRedirectionAdapter {
        public void cancelCall() throws RemoteException {
        }

        public void placeCallUnmodified() throws RemoteException {
        }

        public void redirectCall(Uri handle, PhoneAccountHandle targetPhoneAccount, boolean confirmFirst) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICallRedirectionAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionAdapter";
        static final int TRANSACTION_cancelCall = 1;
        static final int TRANSACTION_placeCallUnmodified = 2;
        static final int TRANSACTION_redirectCall = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallRedirectionAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICallRedirectionAdapter)) {
                return new Proxy(obj);
            }
            return (ICallRedirectionAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "cancelCall";
                case 2:
                    return "placeCallUnmodified";
                case 3:
                    return "redirectCall";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Uri _arg0;
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        cancelCall();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        placeCallUnmodified();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle _arg1 = null;
                        if (data.readInt() != 0) {
                            _arg0 = Uri.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg1 = PhoneAccountHandle.CREATOR.createFromParcel(data);
                        }
                        redirectCall(_arg0, _arg1, data.readInt() != 0);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ICallRedirectionAdapter {
            public static ICallRedirectionAdapter sDefaultImpl;
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

            public void cancelCall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelCall();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void placeCallUnmodified() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().placeCallUnmodified();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void redirectCall(Uri handle, PhoneAccountHandle targetPhoneAccount, boolean confirmFirst) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (handle != null) {
                        _data.writeInt(1);
                        handle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (targetPhoneAccount != null) {
                        _data.writeInt(1);
                        targetPhoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(confirmFirst);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().redirectCall(handle, targetPhoneAccount, confirmFirst);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICallRedirectionAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICallRedirectionAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
