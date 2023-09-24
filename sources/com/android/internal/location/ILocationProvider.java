package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.WorkSource;
import com.android.internal.location.ILocationProviderManager;

/* loaded from: classes4.dex */
public interface ILocationProvider extends IInterface {
    @UnsupportedAppUsage
    int getStatus(Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    long getStatusUpdateTime() throws RemoteException;

    void sendExtraCommand(String str, Bundle bundle) throws RemoteException;

    void setLocationProviderManager(ILocationProviderManager iLocationProviderManager) throws RemoteException;

    void setRequest(ProviderRequest providerRequest, WorkSource workSource) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements ILocationProvider {
        @Override // com.android.internal.location.ILocationProvider
        public void setLocationProviderManager(ILocationProviderManager manager) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProvider
        public void setRequest(ProviderRequest request, WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProvider
        public void sendExtraCommand(String command, Bundle extras) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProvider
        public int getStatus(Bundle extras) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.location.ILocationProvider
        public long getStatusUpdateTime() throws RemoteException {
            return 0L;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements ILocationProvider {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProvider";
        static final int TRANSACTION_getStatus = 4;
        static final int TRANSACTION_getStatusUpdateTime = 5;
        static final int TRANSACTION_sendExtraCommand = 3;
        static final int TRANSACTION_setLocationProviderManager = 1;
        static final int TRANSACTION_setRequest = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILocationProvider)) {
                return (ILocationProvider) iin;
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
                    return "setLocationProviderManager";
                case 2:
                    return "setRequest";
                case 3:
                    return "sendExtraCommand";
                case 4:
                    return "getStatus";
                case 5:
                    return "getStatusUpdateTime";
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
            ProviderRequest _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    ILocationProviderManager _arg02 = ILocationProviderManager.Stub.asInterface(data.readStrongBinder());
                    setLocationProviderManager(_arg02);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = ProviderRequest.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    WorkSource _arg1 = data.readInt() != 0 ? WorkSource.CREATOR.createFromParcel(data) : null;
                    setRequest(_arg0, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    Bundle _arg12 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    sendExtraCommand(_arg03, _arg12);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _arg04 = new Bundle();
                    int _result = getStatus(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    reply.writeInt(1);
                    _arg04.writeToParcel(reply, 1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    long _result2 = getStatusUpdateTime();
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements ILocationProvider {
            public static ILocationProvider sDefaultImpl;
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

            @Override // com.android.internal.location.ILocationProvider
            public void setLocationProviderManager(ILocationProviderManager manager) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(manager != null ? manager.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLocationProviderManager(manager);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProvider
            public void setRequest(ProviderRequest request, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequest(request, ws);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProvider
            public void sendExtraCommand(String command, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendExtraCommand(command, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProvider
            public int getStatus(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatus(extras);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        extras.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProvider
            public long getStatusUpdateTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatusUpdateTime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationProvider impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILocationProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
