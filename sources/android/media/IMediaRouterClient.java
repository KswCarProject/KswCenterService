package android.media;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaRouterClient extends IInterface {
    void onRestoreRoute() throws RemoteException;

    void onSelectedRouteChanged(String str) throws RemoteException;

    void onStateChanged() throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IMediaRouterClient {
        @Override // android.media.IMediaRouterClient
        public void onStateChanged() throws RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onRestoreRoute() throws RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onSelectedRouteChanged(String routeId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMediaRouterClient {
        private static final String DESCRIPTOR = "android.media.IMediaRouterClient";
        static final int TRANSACTION_onRestoreRoute = 2;
        static final int TRANSACTION_onSelectedRouteChanged = 3;
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaRouterClient)) {
                return (IMediaRouterClient) iin;
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
                    return "onStateChanged";
                case 2:
                    return "onRestoreRoute";
                case 3:
                    return "onSelectedRouteChanged";
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
                    onStateChanged();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onRestoreRoute();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    onSelectedRouteChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IMediaRouterClient {
            public static IMediaRouterClient sDefaultImpl;
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

            @Override // android.media.IMediaRouterClient
            public void onStateChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onRestoreRoute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRestoreRoute();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onSelectedRouteChanged(String routeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(routeId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSelectedRouteChanged(routeId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMediaRouterClient impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMediaRouterClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
