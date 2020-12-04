package android.media.tv;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;
import java.util.List;

public interface ITvInputClient extends IInterface {
    void onChannelRetuned(Uri uri, int i) throws RemoteException;

    void onContentAllowed(int i) throws RemoteException;

    void onContentBlocked(String str, int i) throws RemoteException;

    void onError(int i, int i2) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRecordingStopped(Uri uri, int i) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionEvent(String str, Bundle bundle, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onTimeShiftCurrentPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftStartPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftStatusChanged(int i, int i2) throws RemoteException;

    void onTrackSelected(int i, String str, int i2) throws RemoteException;

    void onTracksChanged(List<TvTrackInfo> list, int i) throws RemoteException;

    void onTuned(int i, Uri uri) throws RemoteException;

    void onVideoAvailable(int i) throws RemoteException;

    void onVideoUnavailable(int i, int i2) throws RemoteException;

    public static class Default implements ITvInputClient {
        public void onSessionCreated(String inputId, IBinder token, InputChannel channel, int seq) throws RemoteException {
        }

        public void onSessionReleased(int seq) throws RemoteException {
        }

        public void onSessionEvent(String name, Bundle args, int seq) throws RemoteException {
        }

        public void onChannelRetuned(Uri channelUri, int seq) throws RemoteException {
        }

        public void onTracksChanged(List<TvTrackInfo> list, int seq) throws RemoteException {
        }

        public void onTrackSelected(int type, String trackId, int seq) throws RemoteException {
        }

        public void onVideoAvailable(int seq) throws RemoteException {
        }

        public void onVideoUnavailable(int reason, int seq) throws RemoteException {
        }

        public void onContentAllowed(int seq) throws RemoteException {
        }

        public void onContentBlocked(String rating, int seq) throws RemoteException {
        }

        public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
        }

        public void onTimeShiftStatusChanged(int status, int seq) throws RemoteException {
        }

        public void onTimeShiftStartPositionChanged(long timeMs, int seq) throws RemoteException {
        }

        public void onTimeShiftCurrentPositionChanged(long timeMs, int seq) throws RemoteException {
        }

        public void onTuned(int seq, Uri channelUri) throws RemoteException {
        }

        public void onRecordingStopped(Uri recordedProgramUri, int seq) throws RemoteException {
        }

        public void onError(int error, int seq) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInputClient {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputClient";
        static final int TRANSACTION_onChannelRetuned = 4;
        static final int TRANSACTION_onContentAllowed = 9;
        static final int TRANSACTION_onContentBlocked = 10;
        static final int TRANSACTION_onError = 17;
        static final int TRANSACTION_onLayoutSurface = 11;
        static final int TRANSACTION_onRecordingStopped = 16;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 3;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 14;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 13;
        static final int TRANSACTION_onTimeShiftStatusChanged = 12;
        static final int TRANSACTION_onTrackSelected = 6;
        static final int TRANSACTION_onTracksChanged = 5;
        static final int TRANSACTION_onTuned = 15;
        static final int TRANSACTION_onVideoAvailable = 7;
        static final int TRANSACTION_onVideoUnavailable = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITvInputClient)) {
                return new Proxy(obj);
            }
            return (ITvInputClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionReleased";
                case 3:
                    return "onSessionEvent";
                case 4:
                    return "onChannelRetuned";
                case 5:
                    return "onTracksChanged";
                case 6:
                    return "onTrackSelected";
                case 7:
                    return "onVideoAvailable";
                case 8:
                    return "onVideoUnavailable";
                case 9:
                    return "onContentAllowed";
                case 10:
                    return "onContentBlocked";
                case 11:
                    return "onLayoutSurface";
                case 12:
                    return "onTimeShiftStatusChanged";
                case 13:
                    return "onTimeShiftStartPositionChanged";
                case 14:
                    return "onTimeShiftCurrentPositionChanged";
                case 15:
                    return "onTuned";
                case 16:
                    return "onRecordingStopped";
                case 17:
                    return "onError";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.view.InputChannel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v27, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v31, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v37 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: type inference failed for: r0v41 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                r16 = this;
                r6 = r16
                r7 = r17
                r8 = r18
                java.lang.String r9 = "android.media.tv.ITvInputClient"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x016b
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x0148;
                    case 2: goto L_0x013d;
                    case 3: goto L_0x011e;
                    case 4: goto L_0x0103;
                    case 5: goto L_0x00f2;
                    case 6: goto L_0x00df;
                    case 7: goto L_0x00d4;
                    case 8: goto L_0x00c5;
                    case 9: goto L_0x00ba;
                    case 10: goto L_0x00ab;
                    case 11: goto L_0x0089;
                    case 12: goto L_0x007a;
                    case 13: goto L_0x006b;
                    case 14: goto L_0x005c;
                    case 15: goto L_0x0041;
                    case 16: goto L_0x0026;
                    case 17: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r17, r18, r19, r20)
                return r0
            L_0x0017:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                int r1 = r18.readInt()
                r6.onError(r0, r1)
                return r10
            L_0x0026:
                r8.enforceInterface(r9)
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x0038
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0039
            L_0x0038:
            L_0x0039:
                int r1 = r18.readInt()
                r6.onRecordingStopped(r0, r1)
                return r10
            L_0x0041:
                r8.enforceInterface(r9)
                int r1 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0057
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0058
            L_0x0057:
            L_0x0058:
                r6.onTuned(r1, r0)
                return r10
            L_0x005c:
                r8.enforceInterface(r9)
                long r0 = r18.readLong()
                int r2 = r18.readInt()
                r6.onTimeShiftCurrentPositionChanged(r0, r2)
                return r10
            L_0x006b:
                r8.enforceInterface(r9)
                long r0 = r18.readLong()
                int r2 = r18.readInt()
                r6.onTimeShiftStartPositionChanged(r0, r2)
                return r10
            L_0x007a:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                int r1 = r18.readInt()
                r6.onTimeShiftStatusChanged(r0, r1)
                return r10
            L_0x0089:
                r8.enforceInterface(r9)
                int r11 = r18.readInt()
                int r12 = r18.readInt()
                int r13 = r18.readInt()
                int r14 = r18.readInt()
                int r15 = r18.readInt()
                r0 = r16
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r0.onLayoutSurface(r1, r2, r3, r4, r5)
                return r10
            L_0x00ab:
                r8.enforceInterface(r9)
                java.lang.String r0 = r18.readString()
                int r1 = r18.readInt()
                r6.onContentBlocked(r0, r1)
                return r10
            L_0x00ba:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                r6.onContentAllowed(r0)
                return r10
            L_0x00c5:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                int r1 = r18.readInt()
                r6.onVideoUnavailable(r0, r1)
                return r10
            L_0x00d4:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                r6.onVideoAvailable(r0)
                return r10
            L_0x00df:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                java.lang.String r1 = r18.readString()
                int r2 = r18.readInt()
                r6.onTrackSelected(r0, r1, r2)
                return r10
            L_0x00f2:
                r8.enforceInterface(r9)
                android.os.Parcelable$Creator<android.media.tv.TvTrackInfo> r0 = android.media.tv.TvTrackInfo.CREATOR
                java.util.ArrayList r0 = r8.createTypedArrayList(r0)
                int r1 = r18.readInt()
                r6.onTracksChanged(r0, r1)
                return r10
            L_0x0103:
                r8.enforceInterface(r9)
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x0115
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0116
            L_0x0115:
            L_0x0116:
                int r1 = r18.readInt()
                r6.onChannelRetuned(r0, r1)
                return r10
            L_0x011e:
                r8.enforceInterface(r9)
                java.lang.String r1 = r18.readString()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0134
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0135
            L_0x0134:
            L_0x0135:
                int r2 = r18.readInt()
                r6.onSessionEvent(r1, r0, r2)
                return r10
            L_0x013d:
                r8.enforceInterface(r9)
                int r0 = r18.readInt()
                r6.onSessionReleased(r0)
                return r10
            L_0x0148:
                r8.enforceInterface(r9)
                java.lang.String r1 = r18.readString()
                android.os.IBinder r2 = r18.readStrongBinder()
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x0162
                android.os.Parcelable$Creator<android.view.InputChannel> r0 = android.view.InputChannel.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.InputChannel r0 = (android.view.InputChannel) r0
                goto L_0x0163
            L_0x0162:
            L_0x0163:
                int r3 = r18.readInt()
                r6.onSessionCreated(r1, r2, r0, r3)
                return r10
            L_0x016b:
                r0 = r19
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.tv.ITvInputClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITvInputClient {
            public static ITvInputClient sDefaultImpl;
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

            public void onSessionCreated(String inputId, IBinder token, InputChannel channel, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    _data.writeStrongBinder(token);
                    if (channel != null) {
                        _data.writeInt(1);
                        channel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionCreated(inputId, token, channel, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionReleased(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionReleased(seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionEvent(String name, Bundle args, int seq) throws RemoteException {
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
                    _data.writeInt(seq);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionEvent(name, args, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onChannelRetuned(Uri channelUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onChannelRetuned(channelUri, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTracksChanged(List<TvTrackInfo> tracks, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(tracks);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTracksChanged(tracks, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTrackSelected(int type, String trackId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(trackId);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTrackSelected(type, trackId, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVideoAvailable(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVideoAvailable(seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVideoUnavailable(int reason, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVideoUnavailable(reason, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onContentAllowed(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onContentAllowed(seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onContentBlocked(String rating, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onContentBlocked(rating, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLayoutSurface(left, top, right, bottom, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftStatusChanged(int status, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftStatusChanged(status, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftStartPositionChanged(long timeMs, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftStartPositionChanged(timeMs, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTimeShiftCurrentPositionChanged(long timeMs, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTimeShiftCurrentPositionChanged(timeMs, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTuned(int seq, Uri channelUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTuned(seq, channelUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRecordingStopped(Uri recordedProgramUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recordedProgramUri != null) {
                        _data.writeInt(1);
                        recordedProgramUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(seq);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRecordingStopped(recordedProgramUri, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int error, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    _data.writeInt(seq);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(error, seq);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITvInputClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
