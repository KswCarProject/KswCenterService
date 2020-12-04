package android.media.session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;

public interface ICallback extends IInterface {
    void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName componentName) throws RemoteException;

    void onAddressedPlayerChangedToMediaSession(MediaSession.Token token) throws RemoteException;

    void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent keyEvent, ComponentName componentName) throws RemoteException;

    void onMediaKeyEventDispatchedToMediaSession(KeyEvent keyEvent, MediaSession.Token token) throws RemoteException;

    public static class Default implements ICallback {
        public void onMediaKeyEventDispatchedToMediaSession(KeyEvent event, MediaSession.Token sessionToken) throws RemoteException {
        }

        public void onMediaKeyEventDispatchedToMediaButtonReceiver(KeyEvent event, ComponentName mediaButtonReceiver) throws RemoteException {
        }

        public void onAddressedPlayerChangedToMediaSession(MediaSession.Token sessionToken) throws RemoteException {
        }

        public void onAddressedPlayerChangedToMediaButtonReceiver(ComponentName mediaButtonReceiver) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof ICallback)) {
                return new Proxy(obj);
            }
            return (ICallback) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.media.session.MediaSession$Token} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.media.session.MediaSession$Token} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v19 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.media.session.ICallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x008d
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x0066;
                    case 2: goto L_0x003f;
                    case 3: goto L_0x0028;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r5.onAddressedPlayerChangedToMediaButtonReceiver(r1)
                return r2
            L_0x0028:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.media.session.MediaSession$Token> r1 = android.media.session.MediaSession.Token.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.session.MediaSession$Token r1 = (android.media.session.MediaSession.Token) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r5.onAddressedPlayerChangedToMediaSession(r1)
                return r2
            L_0x003f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0051
                android.os.Parcelable$Creator<android.view.KeyEvent> r3 = android.view.KeyEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
                goto L_0x0052
            L_0x0051:
                r3 = r1
            L_0x0052:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0061
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0062
            L_0x0061:
            L_0x0062:
                r5.onMediaKeyEventDispatchedToMediaButtonReceiver(r3, r1)
                return r2
            L_0x0066:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0078
                android.os.Parcelable$Creator<android.view.KeyEvent> r3 = android.view.KeyEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
                goto L_0x0079
            L_0x0078:
                r3 = r1
            L_0x0079:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0088
                android.os.Parcelable$Creator<android.media.session.MediaSession$Token> r1 = android.media.session.MediaSession.Token.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.session.MediaSession$Token r1 = (android.media.session.MediaSession.Token) r1
                goto L_0x0089
            L_0x0088:
            L_0x0089:
                r5.onMediaKeyEventDispatchedToMediaSession(r3, r1)
                return r2
            L_0x008d:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ICallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ICallback {
            public static ICallback sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaSession(event, sessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMediaKeyEventDispatchedToMediaButtonReceiver(event, mediaButtonReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaSession(sessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAddressedPlayerChangedToMediaButtonReceiver(mediaButtonReceiver);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
