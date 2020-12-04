package android.media.session;

import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ISessionControllerCallback extends IInterface {
    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException;

    void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException;

    void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException;

    public static class Default implements ISessionControllerCallback {
        public void onEvent(String event, Bundle extras) throws RemoteException {
        }

        public void onSessionDestroyed() throws RemoteException {
        }

        public void onPlaybackStateChanged(PlaybackState state) throws RemoteException {
        }

        public void onMetadataChanged(MediaMetadata metadata) throws RemoteException {
        }

        public void onQueueChanged(ParceledListSlice queue) throws RemoteException {
        }

        public void onQueueTitleChanged(CharSequence title) throws RemoteException {
        }

        public void onExtrasChanged(Bundle extras) throws RemoteException {
        }

        public void onVolumeInfoChanged(MediaController.PlaybackInfo info) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISessionControllerCallback {
        private static final String DESCRIPTOR = "android.media.session.ISessionControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionControllerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISessionControllerCallback)) {
                return new Proxy(obj);
            }
            return (ISessionControllerCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onEvent";
                case 2:
                    return "onSessionDestroyed";
                case 3:
                    return "onPlaybackStateChanged";
                case 4:
                    return "onMetadataChanged";
                case 5:
                    return "onQueueChanged";
                case 6:
                    return "onQueueTitleChanged";
                case 7:
                    return "onExtrasChanged";
                case 8:
                    return "onVolumeInfoChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.media.session.PlaybackState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.media.MediaMetadata} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.media.session.MediaController$PlaybackInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v18, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v34 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.media.session.ISessionControllerCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00bd
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00a2;
                    case 2: goto L_0x009b;
                    case 3: goto L_0x0084;
                    case 4: goto L_0x006d;
                    case 5: goto L_0x0056;
                    case 6: goto L_0x003f;
                    case 7: goto L_0x0028;
                    case 8: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.media.session.MediaController$PlaybackInfo> r1 = android.media.session.MediaController.PlaybackInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.session.MediaController$PlaybackInfo r1 = (android.media.session.MediaController.PlaybackInfo) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r5.onVolumeInfoChanged(r1)
                return r2
            L_0x0028:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x003b
            L_0x003a:
            L_0x003b:
                r5.onExtrasChanged(r1)
                return r2
            L_0x003f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0051
                android.os.Parcelable$Creator<java.lang.CharSequence> r1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                goto L_0x0052
            L_0x0051:
            L_0x0052:
                r5.onQueueTitleChanged(r1)
                return r2
            L_0x0056:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0068
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x0069
            L_0x0068:
            L_0x0069:
                r5.onQueueChanged(r1)
                return r2
            L_0x006d:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x007f
                android.os.Parcelable$Creator<android.media.MediaMetadata> r1 = android.media.MediaMetadata.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.MediaMetadata r1 = (android.media.MediaMetadata) r1
                goto L_0x0080
            L_0x007f:
            L_0x0080:
                r5.onMetadataChanged(r1)
                return r2
            L_0x0084:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0096
                android.os.Parcelable$Creator<android.media.session.PlaybackState> r1 = android.media.session.PlaybackState.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.media.session.PlaybackState r1 = (android.media.session.PlaybackState) r1
                goto L_0x0097
            L_0x0096:
            L_0x0097:
                r5.onPlaybackStateChanged(r1)
                return r2
            L_0x009b:
                r7.enforceInterface(r0)
                r5.onSessionDestroyed()
                return r2
            L_0x00a2:
                r7.enforceInterface(r0)
                java.lang.String r3 = r7.readString()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00b8
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00b9
            L_0x00b8:
            L_0x00b9:
                r5.onEvent(r3, r1)
                return r2
            L_0x00bd:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ISessionControllerCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISessionControllerCallback {
            public static ISessionControllerCallback sDefaultImpl;
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

            public void onEvent(String event, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(event);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onEvent(event, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionDestroyed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionDestroyed();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPlaybackStateChanged(PlaybackState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPlaybackStateChanged(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMetadataChanged(MediaMetadata metadata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (metadata != null) {
                        _data.writeInt(1);
                        metadata.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMetadataChanged(metadata);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onQueueChanged(ParceledListSlice queue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queue != null) {
                        _data.writeInt(1);
                        queue.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onQueueChanged(queue);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onQueueTitleChanged(CharSequence title) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (title != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(title, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onQueueTitleChanged(title);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onExtrasChanged(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onExtrasChanged(extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVolumeInfoChanged(MediaController.PlaybackInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVolumeInfoChanged(info);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISessionControllerCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISessionControllerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
