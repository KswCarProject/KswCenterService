package com.android.internal.telecom;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICallScreeningAdapter extends IInterface {
    void allowCall(String str) throws RemoteException;

    void disallowCall(String str, boolean z, boolean z2, boolean z3, ComponentName componentName) throws RemoteException;

    void silenceCall(String str) throws RemoteException;

    public static class Default implements ICallScreeningAdapter {
        public void allowCall(String callId) throws RemoteException {
        }

        public void silenceCall(String callId) throws RemoteException {
        }

        public void disallowCall(String callId, boolean shouldReject, boolean shouldAddToCallLog, boolean shouldShowNotification, ComponentName componentName) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICallScreeningAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallScreeningAdapter";
        static final int TRANSACTION_allowCall = 1;
        static final int TRANSACTION_disallowCall = 3;
        static final int TRANSACTION_silenceCall = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallScreeningAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICallScreeningAdapter)) {
                return new Proxy(obj);
            }
            return (ICallScreeningAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "allowCall";
                case 2:
                    return "silenceCall";
                case 3:
                    return "disallowCall";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ComponentName _arg4;
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        allowCall(data.readString());
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        silenceCall(data.readString());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        boolean _arg1 = data.readInt() != 0;
                        boolean _arg2 = data.readInt() != 0;
                        boolean _arg3 = data.readInt() != 0;
                        if (data.readInt() != 0) {
                            _arg4 = ComponentName.CREATOR.createFromParcel(data);
                        } else {
                            _arg4 = null;
                        }
                        disallowCall(_arg0, _arg1, _arg2, _arg3, _arg4);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ICallScreeningAdapter {
            public static ICallScreeningAdapter sDefaultImpl;
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

            public void allowCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().allowCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void silenceCall(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().silenceCall(callId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void disallowCall(String callId, boolean shouldReject, boolean shouldAddToCallLog, boolean shouldShowNotification, ComponentName componentName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeInt(shouldReject);
                    _data.writeInt(shouldAddToCallLog);
                    _data.writeInt(shouldShowNotification);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disallowCall(callId, shouldReject, shouldAddToCallLog, shouldShowNotification, componentName);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICallScreeningAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICallScreeningAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
