package android.p007os.storage;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.os.storage.IStorageShutdownObserver */
/* loaded from: classes3.dex */
public interface IStorageShutdownObserver extends IInterface {
    void onShutDownComplete(int i) throws RemoteException;

    /* renamed from: android.os.storage.IStorageShutdownObserver$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IStorageShutdownObserver {
        @Override // android.p007os.storage.IStorageShutdownObserver
        public void onShutDownComplete(int statusCode) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.storage.IStorageShutdownObserver$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IStorageShutdownObserver {
        private static final String DESCRIPTOR = "android.os.storage.IStorageShutdownObserver";
        static final int TRANSACTION_onShutDownComplete = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageShutdownObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IStorageShutdownObserver)) {
                return (IStorageShutdownObserver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onShutDownComplete";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            onShutDownComplete(_arg0);
            reply.writeNoException();
            return true;
        }

        /* renamed from: android.os.storage.IStorageShutdownObserver$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IStorageShutdownObserver {
            public static IStorageShutdownObserver sDefaultImpl;
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

            @Override // android.p007os.storage.IStorageShutdownObserver
            public void onShutDownComplete(int statusCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(statusCode);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShutDownComplete(statusCode);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStorageShutdownObserver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IStorageShutdownObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
