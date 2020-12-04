package android.service.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface IMediaBrowserService extends IInterface {
    void addSubscription(String str, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void addSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void connect(String str, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void getMediaItem(String str, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void removeSubscription(String str, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void removeSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    public static class Default implements IMediaBrowserService {
        public void connect(String pkg, Bundle rootHints, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void disconnect(IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void addSubscriptionDeprecated(String uri, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void removeSubscriptionDeprecated(String uri, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void getMediaItem(String uri, ResultReceiver cb, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void addSubscription(String uri, IBinder token, Bundle options, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public void removeSubscription(String uri, IBinder token, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMediaBrowserService {
        private static final String DESCRIPTOR = "android.service.media.IMediaBrowserService";
        static final int TRANSACTION_addSubscription = 6;
        static final int TRANSACTION_addSubscriptionDeprecated = 3;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMediaItem = 5;
        static final int TRANSACTION_removeSubscription = 7;
        static final int TRANSACTION_removeSubscriptionDeprecated = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IMediaBrowserService)) {
                return new Proxy(obj);
            }
            return (IMediaBrowserService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "connect";
                case 2:
                    return "disconnect";
                case 3:
                    return "addSubscriptionDeprecated";
                case 4:
                    return "removeSubscriptionDeprecated";
                case 5:
                    return "getMediaItem";
                case 6:
                    return "addSubscription";
                case 7:
                    return "removeSubscription";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v10, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.service.media.IMediaBrowserService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00ca
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00a7;
                    case 2: goto L_0x0098;
                    case 3: goto L_0x0085;
                    case 4: goto L_0x0072;
                    case 5: goto L_0x004f;
                    case 6: goto L_0x0028;
                    case 7: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                android.os.IBinder r3 = r8.readStrongBinder()
                android.os.IBinder r4 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r4 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r4)
                r6.removeSubscription(r1, r3, r4)
                return r2
            L_0x0028:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                android.os.IBinder r4 = r8.readStrongBinder()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0042
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0043
            L_0x0042:
            L_0x0043:
                android.os.IBinder r5 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r5 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r5)
                r6.addSubscription(r3, r4, r1, r5)
                return r2
            L_0x004f:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0065
                android.os.Parcelable$Creator<android.os.ResultReceiver> r1 = android.os.ResultReceiver.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.ResultReceiver r1 = (android.os.ResultReceiver) r1
                goto L_0x0066
            L_0x0065:
            L_0x0066:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r4 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r4)
                r6.getMediaItem(r3, r1, r4)
                return r2
            L_0x0072:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                android.os.IBinder r3 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r3 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r3)
                r6.removeSubscriptionDeprecated(r1, r3)
                return r2
            L_0x0085:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                android.os.IBinder r3 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r3 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r3)
                r6.addSubscriptionDeprecated(r1, r3)
                return r2
            L_0x0098:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r1 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r1)
                r6.disconnect(r1)
                return r2
            L_0x00a7:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00bd
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00be
            L_0x00bd:
            L_0x00be:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.service.media.IMediaBrowserServiceCallbacks r4 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(r4)
                r6.connect(r3, r1, r4)
                return r2
            L_0x00ca:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.media.IMediaBrowserService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMediaBrowserService {
            public static IMediaBrowserService sDefaultImpl;
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

            public void connect(String pkg, Bundle rootHints, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (rootHints != null) {
                        _data.writeInt(1);
                        rootHints.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().connect(pkg, rootHints, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void disconnect(IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disconnect(callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addSubscriptionDeprecated(String uri, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addSubscriptionDeprecated(uri, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeSubscriptionDeprecated(String uri, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeSubscriptionDeprecated(uri, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getMediaItem(String uri, ResultReceiver cb, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (cb != null) {
                        _data.writeInt(1);
                        cb.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getMediaItem(uri, cb, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addSubscription(String uri, IBinder token, Bundle options, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeStrongBinder(token);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addSubscription(uri, token, options, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeSubscription(String uri, IBinder token, IMediaBrowserServiceCallbacks callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeSubscription(uri, token, callbacks);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMediaBrowserService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMediaBrowserService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
