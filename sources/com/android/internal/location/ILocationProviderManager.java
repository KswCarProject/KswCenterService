package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Location;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes4.dex */
public interface ILocationProviderManager extends IInterface {
    @UnsupportedAppUsage
    void onReportLocation(Location location) throws RemoteException;

    void onSetAdditionalProviderPackages(List<String> list) throws RemoteException;

    @UnsupportedAppUsage
    void onSetEnabled(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void onSetProperties(ProviderProperties providerProperties) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements ILocationProviderManager {
        @Override // com.android.internal.location.ILocationProviderManager
        public void onSetAdditionalProviderPackages(List<String> packageNames) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProviderManager
        public void onSetEnabled(boolean enabled) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProviderManager
        public void onSetProperties(ProviderProperties properties) throws RemoteException {
        }

        @Override // com.android.internal.location.ILocationProviderManager
        public void onReportLocation(Location location) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements ILocationProviderManager {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProviderManager";
        static final int TRANSACTION_onReportLocation = 4;
        static final int TRANSACTION_onSetAdditionalProviderPackages = 1;
        static final int TRANSACTION_onSetEnabled = 2;
        static final int TRANSACTION_onSetProperties = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProviderManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILocationProviderManager)) {
                return (ILocationProviderManager) iin;
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
                    return "onSetAdditionalProviderPackages";
                case 2:
                    return "onSetEnabled";
                case 3:
                    return "onSetProperties";
                case 4:
                    return "onReportLocation";
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
                    List<String> _arg0 = data.createStringArrayList();
                    onSetAdditionalProviderPackages(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg02 = data.readInt() != 0;
                    onSetEnabled(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    ProviderProperties _arg03 = data.readInt() != 0 ? ProviderProperties.CREATOR.createFromParcel(data) : null;
                    onSetProperties(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    Location _arg04 = data.readInt() != 0 ? Location.CREATOR.createFromParcel(data) : null;
                    onReportLocation(_arg04);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements ILocationProviderManager {
            public static ILocationProviderManager sDefaultImpl;
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

            @Override // com.android.internal.location.ILocationProviderManager
            public void onSetAdditionalProviderPackages(List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetAdditionalProviderPackages(packageNames);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProviderManager
            public void onSetEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled ? 1 : 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetEnabled(enabled);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProviderManager
            public void onSetProperties(ProviderProperties properties) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (properties != null) {
                        _data.writeInt(1);
                        properties.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetProperties(properties);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.location.ILocationProviderManager
            public void onReportLocation(Location location) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReportLocation(location);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationProviderManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILocationProviderManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
