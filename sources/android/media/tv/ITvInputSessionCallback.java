package android.media.tv;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ITvInputSessionCallback extends IInterface {
    void onChannelRetuned(Uri uri) throws RemoteException;

    void onContentAllowed() throws RemoteException;

    void onContentBlocked(String str) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException;

    void onRecordingStopped(Uri uri) throws RemoteException;

    void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) throws RemoteException;

    void onSessionEvent(String str, Bundle bundle) throws RemoteException;

    void onTimeShiftCurrentPositionChanged(long j) throws RemoteException;

    void onTimeShiftStartPositionChanged(long j) throws RemoteException;

    void onTimeShiftStatusChanged(int i) throws RemoteException;

    void onTrackSelected(int i, String str) throws RemoteException;

    void onTracksChanged(List<TvTrackInfo> list) throws RemoteException;

    void onTuned(Uri uri) throws RemoteException;

    void onVideoAvailable() throws RemoteException;

    void onVideoUnavailable(int i) throws RemoteException;

    public static class Default implements ITvInputSessionCallback {
        public void onSessionCreated(ITvInputSession session, IBinder hardwareSessionToken) throws RemoteException {
        }

        public void onSessionEvent(String name, Bundle args) throws RemoteException {
        }

        public void onChannelRetuned(Uri channelUri) throws RemoteException {
        }

        public void onTracksChanged(List<TvTrackInfo> list) throws RemoteException {
        }

        public void onTrackSelected(int type, String trackId) throws RemoteException {
        }

        public void onVideoAvailable() throws RemoteException {
        }

        public void onVideoUnavailable(int reason) throws RemoteException {
        }

        public void onContentAllowed() throws RemoteException {
        }

        public void onContentBlocked(String rating) throws RemoteException {
        }

        public void onLayoutSurface(int left, int top, int right, int bottom) throws RemoteException {
        }

        public void onTimeShiftStatusChanged(int status) throws RemoteException {
        }

        public void onTimeShiftStartPositionChanged(long timeMs) throws RemoteException {
        }

        public void onTimeShiftCurrentPositionChanged(long timeMs) throws RemoteException {
        }

        public void onTuned(Uri channelUri) throws RemoteException {
        }

        public void onRecordingStopped(Uri recordedProgramUri) throws RemoteException {
        }

        public void onError(int error) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInputSessionCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputSessionCallback";
        static final int TRANSACTION_onChannelRetuned = 3;
        static final int TRANSACTION_onContentAllowed = 8;
        static final int TRANSACTION_onContentBlocked = 9;
        static final int TRANSACTION_onError = 16;
        static final int TRANSACTION_onLayoutSurface = 10;
        static final int TRANSACTION_onRecordingStopped = 15;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 2;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 13;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 12;
        static final int TRANSACTION_onTimeShiftStatusChanged = 11;
        static final int TRANSACTION_onTrackSelected = 5;
        static final int TRANSACTION_onTracksChanged = 4;
        static final int TRANSACTION_onTuned = 14;
        static final int TRANSACTION_onVideoAvailable = 6;
        static final int TRANSACTION_onVideoUnavailable = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITvInputSessionCallback)) {
                return new Proxy(obj);
            }
            return (ITvInputSessionCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionEvent";
                case 3:
                    return "onChannelRetuned";
                case 4:
                    return "onTracksChanged";
                case 5:
                    return "onTrackSelected";
                case 6:
                    return "onVideoAvailable";
                case 7:
                    return "onVideoUnavailable";
                case 8:
                    return "onContentAllowed";
                case 9:
                    return "onContentBlocked";
                case 10:
                    return "onLayoutSurface";
                case 11:
                    return "onTimeShiftStatusChanged";
                case 12:
                    return "onTimeShiftStartPositionChanged";
                case 13:
                    return "onTimeShiftCurrentPositionChanged";
                case 14:
                    return "onTuned";
                case 15:
                    return "onRecordingStopped";
                case 16:
                    return "onError";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v8, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v19, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v23, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.media.tv.ITvInputSessionCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0107
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00f4;
                    case 2: goto L_0x00d9;
                    case 3: goto L_0x00c2;
                    case 4: goto L_0x00b5;
                    case 5: goto L_0x00a6;
                    case 6: goto L_0x009f;
                    case 7: goto L_0x0094;
                    case 8: goto L_0x008d;
                    case 9: goto L_0x0082;
                    case 10: goto L_0x006b;
                    case 11: goto L_0x0060;
                    case 12: goto L_0x0055;
                    case 13: goto L_0x004a;
                    case 14: goto L_0x0033;
                    case 15: goto L_0x001c;
                    case 16: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onError(r1)
                return r2
            L_0x001c:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x002e
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x002f
            L_0x002e:
            L_0x002f:
                r6.onRecordingStopped(r1)
                return r2
            L_0x0033:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0045
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0046
            L_0x0045:
            L_0x0046:
                r6.onTuned(r1)
                return r2
            L_0x004a:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.onTimeShiftCurrentPositionChanged(r3)
                return r2
            L_0x0055:
                r8.enforceInterface(r0)
                long r3 = r8.readLong()
                r6.onTimeShiftStartPositionChanged(r3)
                return r2
            L_0x0060:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onTimeShiftStatusChanged(r1)
                return r2
            L_0x006b:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                r6.onLayoutSurface(r1, r3, r4, r5)
                return r2
            L_0x0082:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.onContentBlocked(r1)
                return r2
            L_0x008d:
                r8.enforceInterface(r0)
                r6.onContentAllowed()
                return r2
            L_0x0094:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                r6.onVideoUnavailable(r1)
                return r2
            L_0x009f:
                r8.enforceInterface(r0)
                r6.onVideoAvailable()
                return r2
            L_0x00a6:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                java.lang.String r3 = r8.readString()
                r6.onTrackSelected(r1, r3)
                return r2
            L_0x00b5:
                r8.enforceInterface(r0)
                android.os.Parcelable$Creator<android.media.tv.TvTrackInfo> r1 = android.media.tv.TvTrackInfo.CREATOR
                java.util.ArrayList r1 = r8.createTypedArrayList(r1)
                r6.onTracksChanged(r1)
                return r2
            L_0x00c2:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00d4
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x00d5
            L_0x00d4:
            L_0x00d5:
                r6.onChannelRetuned(r1)
                return r2
            L_0x00d9:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00ef
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00f0
            L_0x00ef:
            L_0x00f0:
                r6.onSessionEvent(r3, r1)
                return r2
            L_0x00f4:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.media.tv.ITvInputSession r1 = android.media.tv.ITvInputSession.Stub.asInterface(r1)
                android.os.IBinder r3 = r8.readStrongBinder()
                r6.onSessionCreated(r1, r3)
                return r2
            L_0x0107:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.tv.ITvInputSessionCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITvInputSessionCallback {
            public static ITvInputSessionCallback sDefaultImpl;
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

            public void onSessionCreated(ITvInputSession session, IBinder hardwareSessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    _data.writeStrongBinder(hardwareSessionToken);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionCreated(session, hardwareSessionToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionEvent(String name, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionEvent(name, args);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onChannelRetuned(Uri channelUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onChannelRetuned(channelUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTracksChanged(List<TvTrackInfo> tracks) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(tracks);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTracksChanged(tracks);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTrackSelected(int type, String trackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(trackId);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTrackSelected(type, trackId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVideoAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVideoAvailable();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVideoUnavailable(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVideoUnavailable(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onContentAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onContentAllowed();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onContentBlocked(String rating) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onContentBlocked(rating);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLayoutSurface(int left, int top, int right, int bottom) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutSurface(left, top, right, bottom);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftStatusChanged(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftStatusChanged(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftStartPositionChanged(long timeMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftStartPositionChanged(timeMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftCurrentPositionChanged(long timeMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftCurrentPositionChanged(timeMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTuned(Uri channelUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTuned(channelUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRecordingStopped(Uri recordedProgramUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recordedProgramUri != null) {
                        _data.writeInt(1);
                        recordedProgramUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRecordingStopped(recordedProgramUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(error);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputSessionCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITvInputSessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
