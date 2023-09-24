package com.android.internal.telephony;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import com.android.internal.telephony.ISmsSecurityAgent;

/* loaded from: classes4.dex */
public interface ISmsSecurityService extends IInterface {
    boolean register(ISmsSecurityAgent iSmsSecurityAgent) throws RemoteException;

    boolean sendResponse(SmsAuthorizationRequest smsAuthorizationRequest, boolean z) throws RemoteException;

    boolean unregister(ISmsSecurityAgent iSmsSecurityAgent) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements ISmsSecurityService {
        @Override // com.android.internal.telephony.ISmsSecurityService
        public boolean register(ISmsSecurityAgent agent) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISmsSecurityService
        public boolean unregister(ISmsSecurityAgent agent) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISmsSecurityService
        public boolean sendResponse(SmsAuthorizationRequest request, boolean authorized) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements ISmsSecurityService {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ISmsSecurityService";
        static final int TRANSACTION_register = 1;
        static final int TRANSACTION_sendResponse = 3;
        static final int TRANSACTION_unregister = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISmsSecurityService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISmsSecurityService)) {
                return (ISmsSecurityService) iin;
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
                    return "register";
                case 2:
                    return "unregister";
                case 3:
                    return "sendResponse";
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
            SmsAuthorizationRequest _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    ISmsSecurityAgent _arg02 = ISmsSecurityAgent.Stub.asInterface(data.readStrongBinder());
                    boolean register = register(_arg02);
                    reply.writeNoException();
                    reply.writeInt(register ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ISmsSecurityAgent _arg03 = ISmsSecurityAgent.Stub.asInterface(data.readStrongBinder());
                    boolean unregister = unregister(_arg03);
                    reply.writeNoException();
                    reply.writeInt(unregister ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = SmsAuthorizationRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    boolean _arg1 = data.readInt() != 0;
                    boolean sendResponse = sendResponse(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(sendResponse ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements ISmsSecurityService {
            public static ISmsSecurityService sDefaultImpl;
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

            @Override // com.android.internal.telephony.ISmsSecurityService
            public boolean register(ISmsSecurityAgent agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(agent != null ? agent.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().register(agent);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISmsSecurityService
            public boolean unregister(ISmsSecurityAgent agent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(agent != null ? agent.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregister(agent);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISmsSecurityService
            public boolean sendResponse(SmsAuthorizationRequest request, boolean authorized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(authorized ? 1 : 0);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendResponse(request, authorized);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISmsSecurityService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISmsSecurityService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
