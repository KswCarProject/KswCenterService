package android.media;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteDisplayCallback extends IInterface {
    @UnsupportedAppUsage
    void onStateChanged(RemoteDisplayState remoteDisplayState) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IRemoteDisplayCallback {
        @Override // android.media.IRemoteDisplayCallback
        public void onStateChanged(RemoteDisplayState state) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IRemoteDisplayCallback {
        private static final String DESCRIPTOR = "android.media.IRemoteDisplayCallback";
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteDisplayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteDisplayCallback)) {
                return (IRemoteDisplayCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onStateChanged";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            RemoteDisplayState _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = RemoteDisplayState.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            onStateChanged(_arg0);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IRemoteDisplayCallback {
            public static IRemoteDisplayCallback sDefaultImpl;
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

            @Override // android.media.IRemoteDisplayCallback
            public void onStateChanged(RemoteDisplayState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(state);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteDisplayCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRemoteDisplayCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
