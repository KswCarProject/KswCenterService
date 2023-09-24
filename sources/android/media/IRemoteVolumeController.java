package android.media;

import android.media.session.MediaSession;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteVolumeController extends IInterface {
    void remoteVolumeChanged(MediaSession.Token token, int i) throws RemoteException;

    void updateRemoteController(MediaSession.Token token) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IRemoteVolumeController {
        @Override // android.media.IRemoteVolumeController
        public void remoteVolumeChanged(MediaSession.Token sessionToken, int flags) throws RemoteException {
        }

        @Override // android.media.IRemoteVolumeController
        public void updateRemoteController(MediaSession.Token sessionToken) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IRemoteVolumeController {
        private static final String DESCRIPTOR = "android.media.IRemoteVolumeController";
        static final int TRANSACTION_remoteVolumeChanged = 1;
        static final int TRANSACTION_updateRemoteController = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteVolumeController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteVolumeController)) {
                return (IRemoteVolumeController) iin;
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
                    return "remoteVolumeChanged";
                case 2:
                    return "updateRemoteController";
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
            MediaSession.Token _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel(data) : null;
                    int _arg1 = data.readInt();
                    remoteVolumeChanged(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel(data) : null;
                    updateRemoteController(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IRemoteVolumeController {
            public static IRemoteVolumeController sDefaultImpl;
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

            @Override // android.media.IRemoteVolumeController
            public void remoteVolumeChanged(MediaSession.Token sessionToken, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remoteVolumeChanged(sessionToken, flags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRemoteVolumeController
            public void updateRemoteController(MediaSession.Token sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateRemoteController(sessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteVolumeController impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IRemoteVolumeController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
