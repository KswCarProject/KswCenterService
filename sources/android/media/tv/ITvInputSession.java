package android.media.tv;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

public interface ITvInputSession extends IInterface {
    void appPrivateCommand(String str, Bundle bundle) throws RemoteException;

    void createOverlayView(IBinder iBinder, Rect rect) throws RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException;

    void relayoutOverlayView(Rect rect) throws RemoteException;

    void release() throws RemoteException;

    void removeOverlayView() throws RemoteException;

    void selectTrack(int i, String str) throws RemoteException;

    void setCaptionEnabled(boolean z) throws RemoteException;

    void setMain(boolean z) throws RemoteException;

    void setSurface(Surface surface) throws RemoteException;

    void setVolume(float f) throws RemoteException;

    void startRecording(Uri uri) throws RemoteException;

    void stopRecording() throws RemoteException;

    void timeShiftEnablePositionTracking(boolean z) throws RemoteException;

    void timeShiftPause() throws RemoteException;

    void timeShiftPlay(Uri uri) throws RemoteException;

    void timeShiftResume() throws RemoteException;

    void timeShiftSeekTo(long j) throws RemoteException;

    void timeShiftSetPlaybackParams(PlaybackParams playbackParams) throws RemoteException;

    void tune(Uri uri, Bundle bundle) throws RemoteException;

    void unblockContent(String str) throws RemoteException;

    public static class Default implements ITvInputSession {
        public void release() throws RemoteException {
        }

        public void setMain(boolean isMain) throws RemoteException {
        }

        public void setSurface(Surface surface) throws RemoteException {
        }

        public void dispatchSurfaceChanged(int format, int width, int height) throws RemoteException {
        }

        public void setVolume(float volume) throws RemoteException {
        }

        public void tune(Uri channelUri, Bundle params) throws RemoteException {
        }

        public void setCaptionEnabled(boolean enabled) throws RemoteException {
        }

        public void selectTrack(int type, String trackId) throws RemoteException {
        }

        public void appPrivateCommand(String action, Bundle data) throws RemoteException {
        }

        public void createOverlayView(IBinder windowToken, Rect frame) throws RemoteException {
        }

        public void relayoutOverlayView(Rect frame) throws RemoteException {
        }

        public void removeOverlayView() throws RemoteException {
        }

        public void unblockContent(String unblockedRating) throws RemoteException {
        }

        public void timeShiftPlay(Uri recordedProgramUri) throws RemoteException {
        }

        public void timeShiftPause() throws RemoteException {
        }

        public void timeShiftResume() throws RemoteException {
        }

        public void timeShiftSeekTo(long timeMs) throws RemoteException {
        }

        public void timeShiftSetPlaybackParams(PlaybackParams params) throws RemoteException {
        }

        public void timeShiftEnablePositionTracking(boolean enable) throws RemoteException {
        }

        public void startRecording(Uri programUri) throws RemoteException {
        }

        public void stopRecording() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInputSession {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputSession";
        static final int TRANSACTION_appPrivateCommand = 9;
        static final int TRANSACTION_createOverlayView = 10;
        static final int TRANSACTION_dispatchSurfaceChanged = 4;
        static final int TRANSACTION_relayoutOverlayView = 11;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeOverlayView = 12;
        static final int TRANSACTION_selectTrack = 8;
        static final int TRANSACTION_setCaptionEnabled = 7;
        static final int TRANSACTION_setMain = 2;
        static final int TRANSACTION_setSurface = 3;
        static final int TRANSACTION_setVolume = 5;
        static final int TRANSACTION_startRecording = 20;
        static final int TRANSACTION_stopRecording = 21;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 19;
        static final int TRANSACTION_timeShiftPause = 15;
        static final int TRANSACTION_timeShiftPlay = 14;
        static final int TRANSACTION_timeShiftResume = 16;
        static final int TRANSACTION_timeShiftSeekTo = 17;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 18;
        static final int TRANSACTION_tune = 6;
        static final int TRANSACTION_unblockContent = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITvInputSession)) {
                return new Proxy(obj);
            }
            return (ITvInputSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "release";
                case 2:
                    return "setMain";
                case 3:
                    return "setSurface";
                case 4:
                    return "dispatchSurfaceChanged";
                case 5:
                    return "setVolume";
                case 6:
                    return "tune";
                case 7:
                    return "setCaptionEnabled";
                case 8:
                    return "selectTrack";
                case 9:
                    return "appPrivateCommand";
                case 10:
                    return "createOverlayView";
                case 11:
                    return "relayoutOverlayView";
                case 12:
                    return "removeOverlayView";
                case 13:
                    return "unblockContent";
                case 14:
                    return "timeShiftPlay";
                case 15:
                    return "timeShiftPause";
                case 16:
                    return "timeShiftResume";
                case 17:
                    return "timeShiftSeekTo";
                case 18:
                    return "timeShiftSetPlaybackParams";
                case 19:
                    return "timeShiftEnablePositionTracking";
                case 20:
                    return "startRecording";
                case 21:
                    return "stopRecording";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: android.graphics.Rect} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v2 */
        /* JADX WARNING: type inference failed for: r3v20 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: type inference failed for: r3v27 */
        /* JADX WARNING: type inference failed for: r3v31 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.media.tv.ITvInputSession"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x017f
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x0178;
                    case 2: goto L_0x0169;
                    case 3: goto L_0x0150;
                    case 4: goto L_0x013d;
                    case 5: goto L_0x0132;
                    case 6: goto L_0x010b;
                    case 7: goto L_0x00fc;
                    case 8: goto L_0x00ed;
                    case 9: goto L_0x00d2;
                    case 10: goto L_0x00b7;
                    case 11: goto L_0x009e;
                    case 12: goto L_0x0097;
                    case 13: goto L_0x008c;
                    case 14: goto L_0x0073;
                    case 15: goto L_0x006c;
                    case 16: goto L_0x0065;
                    case 17: goto L_0x005a;
                    case 18: goto L_0x0041;
                    case 19: goto L_0x0032;
                    case 20: goto L_0x0019;
                    case 21: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                r5.stopRecording()
                return r2
            L_0x0019:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x002c
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.Uri r3 = (android.net.Uri) r3
                goto L_0x002d
            L_0x002c:
            L_0x002d:
                r1 = r3
                r5.startRecording(r1)
                return r2
            L_0x0032:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x003d
                r1 = r2
            L_0x003d:
                r5.timeShiftEnablePositionTracking(r1)
                return r2
            L_0x0041:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0054
                android.os.Parcelable$Creator<android.media.PlaybackParams> r1 = android.media.PlaybackParams.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.media.PlaybackParams r3 = (android.media.PlaybackParams) r3
                goto L_0x0055
            L_0x0054:
            L_0x0055:
                r1 = r3
                r5.timeShiftSetPlaybackParams(r1)
                return r2
            L_0x005a:
                r7.enforceInterface(r0)
                long r3 = r7.readLong()
                r5.timeShiftSeekTo(r3)
                return r2
            L_0x0065:
                r7.enforceInterface(r0)
                r5.timeShiftResume()
                return r2
            L_0x006c:
                r7.enforceInterface(r0)
                r5.timeShiftPause()
                return r2
            L_0x0073:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0086
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.net.Uri r3 = (android.net.Uri) r3
                goto L_0x0087
            L_0x0086:
            L_0x0087:
                r1 = r3
                r5.timeShiftPlay(r1)
                return r2
            L_0x008c:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                r5.unblockContent(r1)
                return r2
            L_0x0097:
                r7.enforceInterface(r0)
                r5.removeOverlayView()
                return r2
            L_0x009e:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x00b1
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.graphics.Rect r3 = (android.graphics.Rect) r3
                goto L_0x00b2
            L_0x00b1:
            L_0x00b2:
                r1 = r3
                r5.relayoutOverlayView(r1)
                return r2
            L_0x00b7:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<android.graphics.Rect> r3 = android.graphics.Rect.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.graphics.Rect r3 = (android.graphics.Rect) r3
                goto L_0x00ce
            L_0x00cd:
            L_0x00ce:
                r5.createOverlayView(r1, r3)
                return r2
            L_0x00d2:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00e8
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x00e9
            L_0x00e8:
            L_0x00e9:
                r5.appPrivateCommand(r1, r3)
                return r2
            L_0x00ed:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                java.lang.String r3 = r7.readString()
                r5.selectTrack(r1, r3)
                return r2
            L_0x00fc:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0107
                r1 = r2
            L_0x0107:
                r5.setCaptionEnabled(r1)
                return r2
            L_0x010b:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x011d
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x011e
            L_0x011d:
                r1 = r3
            L_0x011e:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x012d
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x012e
            L_0x012d:
            L_0x012e:
                r5.tune(r1, r3)
                return r2
            L_0x0132:
                r7.enforceInterface(r0)
                float r1 = r7.readFloat()
                r5.setVolume(r1)
                return r2
            L_0x013d:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r3 = r7.readInt()
                int r4 = r7.readInt()
                r5.dispatchSurfaceChanged(r1, r3, r4)
                return r2
            L_0x0150:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0163
                android.os.Parcelable$Creator<android.view.Surface> r1 = android.view.Surface.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                r3 = r1
                android.view.Surface r3 = (android.view.Surface) r3
                goto L_0x0164
            L_0x0163:
            L_0x0164:
                r1 = r3
                r5.setSurface(r1)
                return r2
            L_0x0169:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0174
                r1 = r2
            L_0x0174:
                r5.setMain(r1)
                return r2
            L_0x0178:
                r7.enforceInterface(r0)
                r5.release()
                return r2
            L_0x017f:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.tv.ITvInputSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITvInputSession {
            public static ITvInputSession sDefaultImpl;
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

            public void release() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().release();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setMain(boolean isMain) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isMain);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setMain(isMain);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSurface(Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSurface(surface);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchSurfaceChanged(int format, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(format);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchSurfaceChanged(format, width, height);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setVolume(float volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(volume);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setVolume(volume);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void tune(Uri channelUri, Bundle params) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().tune(channelUri, params);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setCaptionEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setCaptionEnabled(enabled);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void selectTrack(int type, String trackId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(trackId);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().selectTrack(type, trackId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void appPrivateCommand(String action, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().appPrivateCommand(action, data);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void createOverlayView(IBinder windowToken, Rect frame) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    if (frame != null) {
                        _data.writeInt(1);
                        frame.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().createOverlayView(windowToken, frame);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void relayoutOverlayView(Rect frame) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (frame != null) {
                        _data.writeInt(1);
                        frame.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().relayoutOverlayView(frame);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeOverlayView() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeOverlayView();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unblockContent(String unblockedRating) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(unblockedRating);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unblockContent(unblockedRating);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftPlay(Uri recordedProgramUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recordedProgramUri != null) {
                        _data.writeInt(1);
                        recordedProgramUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftPlay(recordedProgramUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftPause();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftResume() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftResume();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftSeekTo(long timeMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftSeekTo(timeMs);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftSetPlaybackParams(PlaybackParams params) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftSetPlaybackParams(params);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void timeShiftEnablePositionTracking(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().timeShiftEnablePositionTracking(enable);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startRecording(Uri programUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (programUri != null) {
                        _data.writeInt(1);
                        programUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startRecording(programUri);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopRecording() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopRecording();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITvInputSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
