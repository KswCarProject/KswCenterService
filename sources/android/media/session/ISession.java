package android.media.session;

import android.app.PendingIntent;
import android.content.pm.ParceledListSlice;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.session.ISessionController;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ISession extends IInterface {
    void destroySession() throws RemoteException;

    ISessionController getController() throws RemoteException;

    void sendEvent(String str, Bundle bundle) throws RemoteException;

    void setActive(boolean z) throws RemoteException;

    void setCurrentVolume(int i) throws RemoteException;

    void setExtras(Bundle bundle) throws RemoteException;

    void setFlags(int i) throws RemoteException;

    void setLaunchPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void setMediaButtonReceiver(PendingIntent pendingIntent) throws RemoteException;

    void setMetadata(MediaMetadata mediaMetadata, long j, String str) throws RemoteException;

    void setPlaybackState(PlaybackState playbackState) throws RemoteException;

    void setPlaybackToLocal(AudioAttributes audioAttributes) throws RemoteException;

    void setPlaybackToRemote(int i, int i2) throws RemoteException;

    void setQueue(ParceledListSlice parceledListSlice) throws RemoteException;

    void setQueueTitle(CharSequence charSequence) throws RemoteException;

    void setRatingType(int i) throws RemoteException;

    public static class Default implements ISession {
        public void sendEvent(String event, Bundle data) throws RemoteException {
        }

        public ISessionController getController() throws RemoteException {
            return null;
        }

        public void setFlags(int flags) throws RemoteException {
        }

        public void setActive(boolean active) throws RemoteException {
        }

        public void setMediaButtonReceiver(PendingIntent mbr) throws RemoteException {
        }

        public void setLaunchPendingIntent(PendingIntent pi) throws RemoteException {
        }

        public void destroySession() throws RemoteException {
        }

        public void setMetadata(MediaMetadata metadata, long duration, String metadataDescription) throws RemoteException {
        }

        public void setPlaybackState(PlaybackState state) throws RemoteException {
        }

        public void setQueue(ParceledListSlice queue) throws RemoteException {
        }

        public void setQueueTitle(CharSequence title) throws RemoteException {
        }

        public void setExtras(Bundle extras) throws RemoteException {
        }

        public void setRatingType(int type) throws RemoteException {
        }

        public void setPlaybackToLocal(AudioAttributes attributes) throws RemoteException {
        }

        public void setPlaybackToRemote(int control, int max) throws RemoteException {
        }

        public void setCurrentVolume(int currentVolume) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISession {
        private static final String DESCRIPTOR = "android.media.session.ISession";
        static final int TRANSACTION_destroySession = 7;
        static final int TRANSACTION_getController = 2;
        static final int TRANSACTION_sendEvent = 1;
        static final int TRANSACTION_setActive = 4;
        static final int TRANSACTION_setCurrentVolume = 16;
        static final int TRANSACTION_setExtras = 12;
        static final int TRANSACTION_setFlags = 3;
        static final int TRANSACTION_setLaunchPendingIntent = 6;
        static final int TRANSACTION_setMediaButtonReceiver = 5;
        static final int TRANSACTION_setMetadata = 8;
        static final int TRANSACTION_setPlaybackState = 9;
        static final int TRANSACTION_setPlaybackToLocal = 14;
        static final int TRANSACTION_setPlaybackToRemote = 15;
        static final int TRANSACTION_setQueue = 10;
        static final int TRANSACTION_setQueueTitle = 11;
        static final int TRANSACTION_setRatingType = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISession)) {
                return new Proxy(obj);
            }
            return (ISession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "sendEvent";
                case 2:
                    return "getController";
                case 3:
                    return "setFlags";
                case 4:
                    return "setActive";
                case 5:
                    return "setMediaButtonReceiver";
                case 6:
                    return "setLaunchPendingIntent";
                case 7:
                    return "destroySession";
                case 8:
                    return "setMetadata";
                case 9:
                    return "setPlaybackState";
                case 10:
                    return "setQueue";
                case 11:
                    return "setQueueTitle";
                case 12:
                    return "setExtras";
                case 13:
                    return "setRatingType";
                case 14:
                    return "setPlaybackToLocal";
                case 15:
                    return "setPlaybackToRemote";
                case 16:
                    return "setCurrentVolume";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.media.MediaMetadata} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.media.session.PlaybackState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v29, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v37, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v42, resolved type: android.media.AudioAttributes} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v33, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v54 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: type inference failed for: r1v56 */
        /* JADX WARNING: type inference failed for: r1v57 */
        /* JADX WARNING: type inference failed for: r1v58 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.media.session.ISession"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0175
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0157;
                    case 2: goto L_0x0142;
                    case 3: goto L_0x0134;
                    case 4: goto L_0x0121;
                    case 5: goto L_0x0107;
                    case 6: goto L_0x00ed;
                    case 7: goto L_0x00e3;
                    case 8: goto L_0x00c1;
                    case 9: goto L_0x00a7;
                    case 10: goto L_0x008d;
                    case 11: goto L_0x0073;
                    case 12: goto L_0x0059;
                    case 13: goto L_0x004b;
                    case 14: goto L_0x0031;
                    case 15: goto L_0x001f;
                    case 16: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.setCurrentVolume(r1)
                r9.writeNoException()
                return r2
            L_0x001f:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                r6.setPlaybackToRemote(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0031:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0043
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x0044
            L_0x0043:
            L_0x0044:
                r6.setPlaybackToLocal(r1)
                r9.writeNoException()
                return r2
            L_0x004b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.setRatingType(r1)
                r9.writeNoException()
                return r2
            L_0x0059:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x006b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x006c
            L_0x006b:
            L_0x006c:
                r6.setExtras(r1)
                r9.writeNoException()
                return r2
            L_0x0073:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0085
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x0086
            L_0x0085:
            L_0x0086:
                r6.setQueueTitle(r1)
                r9.writeNoException()
                return r2
            L_0x008d:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x009f
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x00a0
            L_0x009f:
            L_0x00a0:
                r6.setQueue(r1)
                r9.writeNoException()
                return r2
            L_0x00a7:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00b9
                android.os.Parcelable$Creator<android.media.session.PlaybackState> r1 = android.media.session.PlaybackState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.session.PlaybackState r1 = (android.media.session.PlaybackState) r1
                goto L_0x00ba
            L_0x00b9:
            L_0x00ba:
                r6.setPlaybackState(r1)
                r9.writeNoException()
                return r2
            L_0x00c1:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00d3
                android.os.Parcelable$Creator<android.media.MediaMetadata> r1 = android.media.MediaMetadata.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.MediaMetadata r1 = (android.media.MediaMetadata) r1
                goto L_0x00d4
            L_0x00d3:
            L_0x00d4:
                long r3 = r8.readLong()
                java.lang.String r5 = r8.readString()
                r6.setMetadata(r1, r3, r5)
                r9.writeNoException()
                return r2
            L_0x00e3:
                r8.enforceInterface(r0)
                r6.destroySession()
                r9.writeNoException()
                return r2
            L_0x00ed:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00ff
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0100
            L_0x00ff:
            L_0x0100:
                r6.setLaunchPendingIntent(r1)
                r9.writeNoException()
                return r2
            L_0x0107:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0119
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x011a
            L_0x0119:
            L_0x011a:
                r6.setMediaButtonReceiver(r1)
                r9.writeNoException()
                return r2
            L_0x0121:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x012c
                r1 = r2
                goto L_0x012d
            L_0x012c:
                r1 = 0
            L_0x012d:
                r6.setActive(r1)
                r9.writeNoException()
                return r2
            L_0x0134:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.setFlags(r1)
                r9.writeNoException()
                return r2
            L_0x0142:
                r8.enforceInterface(r0)
                android.media.session.ISessionController r3 = r6.getController()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0153
                android.os.IBinder r1 = r3.asBinder()
            L_0x0153:
                r9.writeStrongBinder(r1)
                return r2
            L_0x0157:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x016d
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x016e
            L_0x016d:
            L_0x016e:
                r6.sendEvent(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0175:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ISession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISession {
            public static ISession sDefaultImpl;
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

            public void sendEvent(String event, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(event);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendEvent(event, data);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ISessionController getController() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getController();
                    }
                    _reply.readException();
                    ISessionController _result = ISessionController.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFlags(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFlags(flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setActive(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(active);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setActive(active);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMediaButtonReceiver(PendingIntent mbr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mbr != null) {
                        _data.writeInt(1);
                        mbr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMediaButtonReceiver(mbr);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLaunchPendingIntent(PendingIntent pi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pi != null) {
                        _data.writeInt(1);
                        pi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLaunchPendingIntent(pi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroySession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroySession();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMetadata(MediaMetadata metadata, long duration, String metadataDescription) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (metadata != null) {
                        _data.writeInt(1);
                        metadata.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(duration);
                    _data.writeString(metadataDescription);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMetadata(metadata, duration, metadataDescription);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPlaybackState(PlaybackState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPlaybackState(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setQueue(ParceledListSlice queue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queue != null) {
                        _data.writeInt(1);
                        queue.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setQueue(queue);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setQueueTitle(CharSequence title) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (title != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(title, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setQueueTitle(title);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setExtras(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setExtras(extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRatingType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRatingType(type);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPlaybackToLocal(AudioAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (attributes != null) {
                        _data.writeInt(1);
                        attributes.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPlaybackToLocal(attributes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPlaybackToRemote(int control, int max) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(control);
                    _data.writeInt(max);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPlaybackToRemote(control, max);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCurrentVolume(int currentVolume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(currentVolume);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCurrentVolume(currentVolume);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
