package android.content.p002pm;

import android.appwidget.AppWidgetProviderInfo;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.IPinItemRequest */
/* loaded from: classes.dex */
public interface IPinItemRequest extends IInterface {
    boolean accept(Bundle bundle) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException;

    Bundle getExtras() throws RemoteException;

    ShortcutInfo getShortcutInfo() throws RemoteException;

    boolean isValid() throws RemoteException;

    /* renamed from: android.content.pm.IPinItemRequest$Default */
    /* loaded from: classes.dex */
    public static class Default implements IPinItemRequest {
        @Override // android.content.p002pm.IPinItemRequest
        public boolean isValid() throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public boolean accept(Bundle options) throws RemoteException {
            return false;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public ShortcutInfo getShortcutInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.IPinItemRequest
        public Bundle getExtras() throws RemoteException {
            return null;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.IPinItemRequest$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IPinItemRequest {
        private static final String DESCRIPTOR = "android.content.pm.IPinItemRequest";
        static final int TRANSACTION_accept = 2;
        static final int TRANSACTION_getAppWidgetProviderInfo = 4;
        static final int TRANSACTION_getExtras = 5;
        static final int TRANSACTION_getShortcutInfo = 3;
        static final int TRANSACTION_isValid = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPinItemRequest asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPinItemRequest)) {
                return (IPinItemRequest) iin;
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
                    return "isValid";
                case 2:
                    return "accept";
                case 3:
                    return "getShortcutInfo";
                case 4:
                    return "getAppWidgetProviderInfo";
                case 5:
                    return "getExtras";
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
            Bundle _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isValid = isValid();
                    reply.writeNoException();
                    reply.writeInt(isValid ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    boolean accept = accept(_arg0);
                    reply.writeNoException();
                    reply.writeInt(accept ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    ShortcutInfo _result = getShortcutInfo();
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    AppWidgetProviderInfo _result2 = getAppWidgetProviderInfo();
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _result3 = getExtras();
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.IPinItemRequest$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IPinItemRequest {
            public static IPinItemRequest sDefaultImpl;
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

            @Override // android.content.p002pm.IPinItemRequest
            public boolean isValid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isValid();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public boolean accept(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().accept(options);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public ShortcutInfo getShortcutInfo() throws RemoteException {
                ShortcutInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShortcutInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ShortcutInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public AppWidgetProviderInfo getAppWidgetProviderInfo() throws RemoteException {
                AppWidgetProviderInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetProviderInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AppWidgetProviderInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPinItemRequest
            public Bundle getExtras() throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getExtras();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPinItemRequest impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPinItemRequest getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}