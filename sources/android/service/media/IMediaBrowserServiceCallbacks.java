package android.service.media;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaBrowserServiceCallbacks extends IInterface {
    @UnsupportedAppUsage
    void onConnect(String str, MediaSession.Token token, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void onConnectFailed() throws RemoteException;

    void onLoadChildren(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onLoadChildrenWithOptions(String str, ParceledListSlice parceledListSlice, Bundle bundle) throws RemoteException;

    public static class Default implements IMediaBrowserServiceCallbacks {
        public void onConnect(String root, MediaSession.Token session, Bundle extras) throws RemoteException {
        }

        public void onConnectFailed() throws RemoteException {
        }

        public void onLoadChildren(String mediaId, ParceledListSlice list) throws RemoteException {
        }

        public void onLoadChildrenWithOptions(String mediaId, ParceledListSlice list, Bundle options) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IMediaBrowserServiceCallbacks)) {
                return new Proxy(obj);
            }
            return (IMediaBrowserServiceCallbacks) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.service.media.IMediaBrowserServiceCallbacks"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0089
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x005e;
                    case 2: goto L_0x0057;
                    case 3: goto L_0x003c;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0027
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r4 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.content.pm.ParceledListSlice r4 = (android.content.pm.ParceledListSlice) r4
                goto L_0x0028
            L_0x0027:
                r4 = r1
            L_0x0028:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0037
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0038
            L_0x0037:
            L_0x0038:
                r6.onLoadChildrenWithOptions(r3, r4, r1)
                return r2
            L_0x003c:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0052
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x0053
            L_0x0052:
            L_0x0053:
                r6.onLoadChildren(r3, r1)
                return r2
            L_0x0057:
                r8.enforceInterface(r0)
                r6.onConnectFailed()
                return r2
            L_0x005e:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0074
                android.os.Parcelable$Creator<android.media.session.MediaSession$Token> r4 = android.media.session.MediaSession.Token.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.media.session.MediaSession$Token r4 = (android.media.session.MediaSession.Token) r4
                goto L_0x0075
            L_0x0074:
                r4 = r1
            L_0x0075:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0084
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0085
            L_0x0084:
            L_0x0085:
                r6.onConnect(r3, r4, r1)
                return r2
            L_0x0089:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.media.IMediaBrowserServiceCallbacks.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMediaBrowserServiceCallbacks {
            public static IMediaBrowserServiceCallbacks sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnect(root, session, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onConnectFailed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnectFailed();
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLoadChildren(mediaId, list);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLoadChildrenWithOptions(mediaId, list, options);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMediaBrowserServiceCallbacks impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMediaBrowserServiceCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
