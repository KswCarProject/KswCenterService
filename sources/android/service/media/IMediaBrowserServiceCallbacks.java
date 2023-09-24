package android.service.media;

import android.annotation.UnsupportedAppUsage;
import android.content.p002pm.ParceledListSlice;
import android.media.session.MediaSession;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaBrowserServiceCallbacks extends IInterface {
    @UnsupportedAppUsage
    void onConnect(String str, MediaSession.Token token, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void onConnectFailed() throws RemoteException;

    void onLoadChildren(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onLoadChildrenWithOptions(String str, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IMediaBrowserServiceCallbacks {
        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnect(String root, MediaSession.Token session, Bundle extras) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnectFailed() throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildren(String mediaId, ParceledListSlice list) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildrenWithOptions(String mediaId, ParceledListSlice list, Bundle options) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IMediaBrowserServiceCallbacks {
        private static final String DESCRIPTOR = "android.service.media.IMediaBrowserServiceCallbacks";
        static final int TRANSACTION_onConnect = 1;
        static final int TRANSACTION_onConnectFailed = 2;
        static final int TRANSACTION_onLoadChildren = 3;
        static final int TRANSACTION_onLoadChildrenWithOptions = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserServiceCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaBrowserServiceCallbacks)) {
                return (IMediaBrowserServiceCallbacks) iin;
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
                    return "onConnect";
                case 2:
                    return "onConnectFailed";
                case 3:
                    return "onLoadChildren";
                case 4:
                    return "onLoadChildrenWithOptions";
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
            MediaSession.Token _arg1;
            ParceledListSlice _arg12;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = MediaSession.Token.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    Bundle _arg2 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onConnect(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onConnectFailed();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    ParceledListSlice _arg13 = data.readInt() != 0 ? ParceledListSlice.CREATOR.createFromParcel(data) : null;
                    onLoadChildren(_arg02, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = ParceledListSlice.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    Bundle _arg22 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onLoadChildrenWithOptions(_arg03, _arg12, _arg22);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IMediaBrowserServiceCallbacks {
            public static IMediaBrowserServiceCallbacks sDefaultImpl;
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

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnect(String root, MediaSession.Token session, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(root);
                    if (session != null) {
                        _data.writeInt(1);
                        session.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnect(root, session, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnectFailed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectFailed();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onLoadChildren(String mediaId, ParceledListSlice list) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mediaId);
                    if (list != null) {
                        _data.writeInt(1);
                        list.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLoadChildren(mediaId, list);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onLoadChildrenWithOptions(String mediaId, ParceledListSlice list, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mediaId);
                    if (list != null) {
                        _data.writeInt(1);
                        list.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLoadChildrenWithOptions(mediaId, list, options);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMediaBrowserServiceCallbacks impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IMediaBrowserServiceCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
