package android.content.p002pm.dex;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.dex.ISnapshotRuntimeProfileCallback */
/* loaded from: classes.dex */
public interface ISnapshotRuntimeProfileCallback extends IInterface {
    void onError(int i) throws RemoteException;

    void onSuccess(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    /* renamed from: android.content.pm.dex.ISnapshotRuntimeProfileCallback$Default */
    /* loaded from: classes.dex */
    public static class Default implements ISnapshotRuntimeProfileCallback {
        @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(ParcelFileDescriptor profileReadFd) throws RemoteException {
        }

        @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(int errCode) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.dex.ISnapshotRuntimeProfileCallback$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ISnapshotRuntimeProfileCallback {
        private static final String DESCRIPTOR = "android.content.pm.dex.ISnapshotRuntimeProfileCallback";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISnapshotRuntimeProfileCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISnapshotRuntimeProfileCallback)) {
                return (ISnapshotRuntimeProfileCallback) iin;
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
                    return "onSuccess";
                case 2:
                    return "onError";
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
            ParcelFileDescriptor _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = ParcelFileDescriptor.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onSuccess(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.dex.ISnapshotRuntimeProfileCallback$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements ISnapshotRuntimeProfileCallback {
            public static ISnapshotRuntimeProfileCallback sDefaultImpl;
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

            @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
            public void onSuccess(ParcelFileDescriptor profileReadFd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (profileReadFd != null) {
                        _data.writeInt(1);
                        profileReadFd.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(profileReadFd);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.dex.ISnapshotRuntimeProfileCallback
            public void onError(int errCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errCode);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(errCode);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISnapshotRuntimeProfileCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISnapshotRuntimeProfileCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
