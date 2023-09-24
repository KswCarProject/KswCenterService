package android.media.session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.KeyEvent;

/* loaded from: classes3.dex */
public interface ICallback extends IInterface {
    void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName componentName) throws RemoteException;

    void onAddressedPlayerChangedToMediaSession(MediaSession.Token token) throws RemoteException;

    void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent keyEvent, ComponentName componentName) throws RemoteException;

    void onMediaKeyEventDispatchedToMediaSession(KeyEvent keyEvent, MediaSession.Token token) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ICallback {
        @Override // android.media.session.ICallback
        public void onMediaKeyEventDispatchedToMediaSession(KeyEvent event, MediaSession.Token sessionToken) throws RemoteException {
        }

        @Override // android.media.session.ICallback
        public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent event, ComponentName mediaButtonReceiver) throws RemoteException {
        }

        @Override // android.media.session.ICallback
        public void onAddressedPlayerChangedToMediaSession(MediaSession.Token sessionToken) throws RemoteException {
        }

        @Override // android.media.session.ICallback
        public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName mediaButtonReceiver) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ICallback {
        private static final String DESCRIPTOR = "android.media.session.ICallback";
        static final int TRANSACTION_onAddressedPlayerChangedToMediaButtonReceiver = 4;
        static final int TRANSACTION_onAddressedPlayerChangedToMediaSession = 3;
        static final int TRANSACTION_onMediaKeyEventDispatchedToMediaButtonReceiver = 2;
        static final int TRANSACTION_onMediaKeyEventDispatchedToMediaSession = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICallback)) {
                return (ICallback) iin;
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
                    return "onMediaKeyEventDispatchedToMediaSession";
                case 2:
                    return "onMediaKeyEventDispatchedToMediaButtonReceiver";
                case 3:
                    return "onAddressedPlayerChangedToMediaSession";
                case 4:
                    return "onAddressedPlayerChangedToMediaButtonReceiver";
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
            KeyEvent _arg0;
            KeyEvent _arg02;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = KeyEvent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    MediaSession.Token _arg1 = data.readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel(data) : null;
                    onMediaKeyEventDispatchedToMediaSession(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = KeyEvent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    ComponentName _arg12 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    onMediaKeyEventDispatchedToMediaButtonReceiver(_arg02, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    MediaSession.Token _arg03 = data.readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel(data) : null;
                    onAddressedPlayerChangedToMediaSession(_arg03);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg04 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    onAddressedPlayerChangedToMediaButtonReceiver(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ICallback {
            public static ICallback sDefaultImpl;
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

            @Override // android.media.session.ICallback
            public void onMediaKeyEventDispatchedToMediaSession(KeyEvent event, MediaSession.Token sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaSession(event, sessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.session.ICallback
            public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent event, ComponentName mediaButtonReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (mediaButtonReceiver != null) {
                        _data.writeInt(1);
                        mediaButtonReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaButtonReceiver(event, mediaButtonReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.session.ICallback
            public void onAddressedPlayerChangedToMediaSession(MediaSession.Token sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaSession(sessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.session.ICallback
            public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName mediaButtonReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaButtonReceiver != null) {
                        _data.writeInt(1);
                        mediaButtonReceiver.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaButtonReceiver(mediaButtonReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
