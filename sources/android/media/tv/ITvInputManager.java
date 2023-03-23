package android.media.tv;

import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.ITvInputHardware;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.Surface;
import java.util.List;

public interface ITvInputManager extends IInterface {
    ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2) throws RemoteException;

    void addBlockedRating(String str, int i) throws RemoteException;

    boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) throws RemoteException;

    void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException;

    void createSession(ITvInputClient iTvInputClient, String str, boolean z, int i, int i2) throws RemoteException;

    void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException;

    List<TvStreamConfig> getAvailableTvStreamConfigList(String str, int i) throws RemoteException;

    List<String> getBlockedRatings(int i) throws RemoteException;

    List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException;

    List<TvInputHardwareInfo> getHardwareList() throws RemoteException;

    List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws RemoteException;

    TvInputInfo getTvInputInfo(String str, int i) throws RemoteException;

    List<TvInputInfo> getTvInputList(int i) throws RemoteException;

    int getTvInputState(String str, int i) throws RemoteException;

    boolean isParentalControlsEnabled(int i) throws RemoteException;

    boolean isRatingBlocked(String str, int i) throws RemoteException;

    boolean isSingleSessionActive(int i) throws RemoteException;

    ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) throws RemoteException;

    void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException;

    void relayoutOverlayView(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void releaseSession(IBinder iBinder, int i) throws RemoteException;

    void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) throws RemoteException;

    void removeBlockedRating(String str, int i) throws RemoteException;

    void removeOverlayView(IBinder iBinder, int i) throws RemoteException;

    void requestChannelBrowsable(Uri uri, int i) throws RemoteException;

    void selectTrack(IBinder iBinder, int i, String str, int i2) throws RemoteException;

    void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void sendTvInputNotifyIntent(Intent intent, int i) throws RemoteException;

    void setCaptionEnabled(IBinder iBinder, boolean z, int i) throws RemoteException;

    void setMainSession(IBinder iBinder, int i) throws RemoteException;

    void setParentalControlsEnabled(boolean z, int i) throws RemoteException;

    void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException;

    void setVolume(IBinder iBinder, float f, int i) throws RemoteException;

    void startRecording(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void stopRecording(IBinder iBinder, int i) throws RemoteException;

    void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) throws RemoteException;

    void timeShiftPause(IBinder iBinder, int i) throws RemoteException;

    void timeShiftPlay(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void timeShiftResume(IBinder iBinder, int i) throws RemoteException;

    void timeShiftSeekTo(IBinder iBinder, long j, int i) throws RemoteException;

    void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) throws RemoteException;

    void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) throws RemoteException;

    void unblockContent(IBinder iBinder, String str, int i) throws RemoteException;

    void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) throws RemoteException;

    void updateTvInputInfo(TvInputInfo tvInputInfo, int i) throws RemoteException;

    public static class Default implements ITvInputManager {
        public List<TvInputInfo> getTvInputList(int userId) throws RemoteException {
            return null;
        }

        public TvInputInfo getTvInputInfo(String inputId, int userId) throws RemoteException {
            return null;
        }

        public void updateTvInputInfo(TvInputInfo inputInfo, int userId) throws RemoteException {
        }

        public int getTvInputState(String inputId, int userId) throws RemoteException {
            return 0;
        }

        public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int userId) throws RemoteException {
            return null;
        }

        public void registerCallback(ITvInputManagerCallback callback, int userId) throws RemoteException {
        }

        public void unregisterCallback(ITvInputManagerCallback callback, int userId) throws RemoteException {
        }

        public boolean isParentalControlsEnabled(int userId) throws RemoteException {
            return false;
        }

        public void setParentalControlsEnabled(boolean enabled, int userId) throws RemoteException {
        }

        public boolean isRatingBlocked(String rating, int userId) throws RemoteException {
            return false;
        }

        public List<String> getBlockedRatings(int userId) throws RemoteException {
            return null;
        }

        public void addBlockedRating(String rating, int userId) throws RemoteException {
        }

        public void removeBlockedRating(String rating, int userId) throws RemoteException {
        }

        public void createSession(ITvInputClient client, String inputId, boolean isRecordingSession, int seq, int userId) throws RemoteException {
        }

        public void releaseSession(IBinder sessionToken, int userId) throws RemoteException {
        }

        public void setMainSession(IBinder sessionToken, int userId) throws RemoteException {
        }

        public void setSurface(IBinder sessionToken, Surface surface, int userId) throws RemoteException {
        }

        public void dispatchSurfaceChanged(IBinder sessionToken, int format, int width, int height, int userId) throws RemoteException {
        }

        public void setVolume(IBinder sessionToken, float volume, int userId) throws RemoteException {
        }

        public void tune(IBinder sessionToken, Uri channelUri, Bundle params, int userId) throws RemoteException {
        }

        public void setCaptionEnabled(IBinder sessionToken, boolean enabled, int userId) throws RemoteException {
        }

        public void selectTrack(IBinder sessionToken, int type, String trackId, int userId) throws RemoteException {
        }

        public void sendAppPrivateCommand(IBinder sessionToken, String action, Bundle data, int userId) throws RemoteException {
        }

        public void createOverlayView(IBinder sessionToken, IBinder windowToken, Rect frame, int userId) throws RemoteException {
        }

        public void relayoutOverlayView(IBinder sessionToken, Rect frame, int userId) throws RemoteException {
        }

        public void removeOverlayView(IBinder sessionToken, int userId) throws RemoteException {
        }

        public void unblockContent(IBinder sessionToken, String unblockedRating, int userId) throws RemoteException {
        }

        public void timeShiftPlay(IBinder sessionToken, Uri recordedProgramUri, int userId) throws RemoteException {
        }

        public void timeShiftPause(IBinder sessionToken, int userId) throws RemoteException {
        }

        public void timeShiftResume(IBinder sessionToken, int userId) throws RemoteException {
        }

        public void timeShiftSeekTo(IBinder sessionToken, long timeMs, int userId) throws RemoteException {
        }

        public void timeShiftSetPlaybackParams(IBinder sessionToken, PlaybackParams params, int userId) throws RemoteException {
        }

        public void timeShiftEnablePositionTracking(IBinder sessionToken, boolean enable, int userId) throws RemoteException {
        }

        public void startRecording(IBinder sessionToken, Uri programUri, int userId) throws RemoteException {
        }

        public void stopRecording(IBinder sessionToken, int userId) throws RemoteException {
        }

        public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
            return null;
        }

        public ITvInputHardware acquireTvInputHardware(int deviceId, ITvInputHardwareCallback callback, TvInputInfo info, int userId) throws RemoteException {
            return null;
        }

        public void releaseTvInputHardware(int deviceId, ITvInputHardware hardware, int userId) throws RemoteException {
        }

        public List<TvStreamConfig> getAvailableTvStreamConfigList(String inputId, int userId) throws RemoteException {
            return null;
        }

        public boolean captureFrame(String inputId, Surface surface, TvStreamConfig config, int userId) throws RemoteException {
            return false;
        }

        public boolean isSingleSessionActive(int userId) throws RemoteException {
            return false;
        }

        public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo info, int device) throws RemoteException {
            return null;
        }

        public void sendTvInputNotifyIntent(Intent intent, int userId) throws RemoteException {
        }

        public void requestChannelBrowsable(Uri channelUri, int userId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInputManager {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputManager";
        static final int TRANSACTION_acquireTvInputHardware = 37;
        static final int TRANSACTION_addBlockedRating = 12;
        static final int TRANSACTION_captureFrame = 40;
        static final int TRANSACTION_createOverlayView = 24;
        static final int TRANSACTION_createSession = 14;
        static final int TRANSACTION_dispatchSurfaceChanged = 18;
        static final int TRANSACTION_getAvailableTvStreamConfigList = 39;
        static final int TRANSACTION_getBlockedRatings = 11;
        static final int TRANSACTION_getDvbDeviceList = 42;
        static final int TRANSACTION_getHardwareList = 36;
        static final int TRANSACTION_getTvContentRatingSystemList = 5;
        static final int TRANSACTION_getTvInputInfo = 2;
        static final int TRANSACTION_getTvInputList = 1;
        static final int TRANSACTION_getTvInputState = 4;
        static final int TRANSACTION_isParentalControlsEnabled = 8;
        static final int TRANSACTION_isRatingBlocked = 10;
        static final int TRANSACTION_isSingleSessionActive = 41;
        static final int TRANSACTION_openDvbDevice = 43;
        static final int TRANSACTION_registerCallback = 6;
        static final int TRANSACTION_relayoutOverlayView = 25;
        static final int TRANSACTION_releaseSession = 15;
        static final int TRANSACTION_releaseTvInputHardware = 38;
        static final int TRANSACTION_removeBlockedRating = 13;
        static final int TRANSACTION_removeOverlayView = 26;
        static final int TRANSACTION_requestChannelBrowsable = 45;
        static final int TRANSACTION_selectTrack = 22;
        static final int TRANSACTION_sendAppPrivateCommand = 23;
        static final int TRANSACTION_sendTvInputNotifyIntent = 44;
        static final int TRANSACTION_setCaptionEnabled = 21;
        static final int TRANSACTION_setMainSession = 16;
        static final int TRANSACTION_setParentalControlsEnabled = 9;
        static final int TRANSACTION_setSurface = 17;
        static final int TRANSACTION_setVolume = 19;
        static final int TRANSACTION_startRecording = 34;
        static final int TRANSACTION_stopRecording = 35;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 33;
        static final int TRANSACTION_timeShiftPause = 29;
        static final int TRANSACTION_timeShiftPlay = 28;
        static final int TRANSACTION_timeShiftResume = 30;
        static final int TRANSACTION_timeShiftSeekTo = 31;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 32;
        static final int TRANSACTION_tune = 20;
        static final int TRANSACTION_unblockContent = 27;
        static final int TRANSACTION_unregisterCallback = 7;
        static final int TRANSACTION_updateTvInputInfo = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITvInputManager)) {
                return new Proxy(obj);
            }
            return (ITvInputManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getTvInputList";
                case 2:
                    return "getTvInputInfo";
                case 3:
                    return "updateTvInputInfo";
                case 4:
                    return "getTvInputState";
                case 5:
                    return "getTvContentRatingSystemList";
                case 6:
                    return "registerCallback";
                case 7:
                    return "unregisterCallback";
                case 8:
                    return "isParentalControlsEnabled";
                case 9:
                    return "setParentalControlsEnabled";
                case 10:
                    return "isRatingBlocked";
                case 11:
                    return "getBlockedRatings";
                case 12:
                    return "addBlockedRating";
                case 13:
                    return "removeBlockedRating";
                case 14:
                    return "createSession";
                case 15:
                    return "releaseSession";
                case 16:
                    return "setMainSession";
                case 17:
                    return "setSurface";
                case 18:
                    return "dispatchSurfaceChanged";
                case 19:
                    return "setVolume";
                case 20:
                    return "tune";
                case 21:
                    return "setCaptionEnabled";
                case 22:
                    return "selectTrack";
                case 23:
                    return "sendAppPrivateCommand";
                case 24:
                    return "createOverlayView";
                case 25:
                    return "relayoutOverlayView";
                case 26:
                    return "removeOverlayView";
                case 27:
                    return "unblockContent";
                case 28:
                    return "timeShiftPlay";
                case 29:
                    return "timeShiftPause";
                case 30:
                    return "timeShiftResume";
                case 31:
                    return "timeShiftSeekTo";
                case 32:
                    return "timeShiftSetPlaybackParams";
                case 33:
                    return "timeShiftEnablePositionTracking";
                case 34:
                    return "startRecording";
                case 35:
                    return "stopRecording";
                case 36:
                    return "getHardwareList";
                case 37:
                    return "acquireTvInputHardware";
                case 38:
                    return "releaseTvInputHardware";
                case 39:
                    return "getAvailableTvStreamConfigList";
                case 40:
                    return "captureFrame";
                case 41:
                    return "isSingleSessionActive";
                case 42:
                    return "getDvbDeviceList";
                case 43:
                    return "openDvbDevice";
                case 44:
                    return "sendTvInputNotifyIntent";
                case 45:
                    return "requestChannelBrowsable";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v29, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v43, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v56, resolved type: android.media.PlaybackParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v71, resolved type: android.media.tv.TvStreamConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v76, resolved type: android.media.tv.DvbDeviceInfo} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v3 */
        /* JADX WARNING: type inference failed for: r1v23, types: [android.view.Surface] */
        /* JADX WARNING: type inference failed for: r1v49, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v61, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v66, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v80 */
        /* JADX WARNING: type inference failed for: r1v84 */
        /* JADX WARNING: type inference failed for: r1v88 */
        /* JADX WARNING: type inference failed for: r1v89 */
        /* JADX WARNING: type inference failed for: r1v90 */
        /* JADX WARNING: type inference failed for: r1v91 */
        /* JADX WARNING: type inference failed for: r1v92 */
        /* JADX WARNING: type inference failed for: r1v93 */
        /* JADX WARNING: type inference failed for: r1v94 */
        /* JADX WARNING: type inference failed for: r1v95 */
        /* JADX WARNING: type inference failed for: r1v96 */
        /* JADX WARNING: type inference failed for: r1v97 */
        /* JADX WARNING: type inference failed for: r1v98 */
        /* JADX WARNING: type inference failed for: r1v99 */
        /* JADX WARNING: type inference failed for: r1v100 */
        /* JADX WARNING: type inference failed for: r1v101 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.media.tv.ITvInputManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x04de
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x04cc;
                    case 2: goto L_0x04ad;
                    case 3: goto L_0x048d;
                    case 4: goto L_0x0477;
                    case 5: goto L_0x0465;
                    case 6: goto L_0x044f;
                    case 7: goto L_0x0439;
                    case 8: goto L_0x0427;
                    case 9: goto L_0x0411;
                    case 10: goto L_0x03fb;
                    case 11: goto L_0x03e9;
                    case 12: goto L_0x03d7;
                    case 13: goto L_0x03c5;
                    case 14: goto L_0x0398;
                    case 15: goto L_0x0386;
                    case 16: goto L_0x0374;
                    case 17: goto L_0x0352;
                    case 18: goto L_0x032c;
                    case 19: goto L_0x0316;
                    case 20: goto L_0x02e4;
                    case 21: goto L_0x02ca;
                    case 22: goto L_0x02b0;
                    case 23: goto L_0x028a;
                    case 24: goto L_0x0264;
                    case 25: goto L_0x0242;
                    case 26: goto L_0x0230;
                    case 27: goto L_0x021a;
                    case 28: goto L_0x01f8;
                    case 29: goto L_0x01e6;
                    case 30: goto L_0x01d4;
                    case 31: goto L_0x01be;
                    case 32: goto L_0x019c;
                    case 33: goto L_0x0182;
                    case 34: goto L_0x0160;
                    case 35: goto L_0x014e;
                    case 36: goto L_0x0140;
                    case 37: goto L_0x010b;
                    case 38: goto L_0x00f1;
                    case 39: goto L_0x00db;
                    case 40: goto L_0x00a5;
                    case 41: goto L_0x0093;
                    case 42: goto L_0x0085;
                    case 43: goto L_0x005a;
                    case 44: goto L_0x003a;
                    case 45: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x002d
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x002e
            L_0x002d:
            L_0x002e:
                r0 = r1
                int r1 = r19.readInt()
                r6.requestChannelBrowsable(r0, r1)
                r20.writeNoException()
                return r11
            L_0x003a:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x004d
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x004e
            L_0x004d:
            L_0x004e:
                r0 = r1
                int r1 = r19.readInt()
                r6.sendTvInputNotifyIntent(r0, r1)
                r20.writeNoException()
                return r11
            L_0x005a:
                r8.enforceInterface(r10)
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x006c
                android.os.Parcelable$Creator<android.media.tv.DvbDeviceInfo> r1 = android.media.tv.DvbDeviceInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.tv.DvbDeviceInfo r1 = (android.media.tv.DvbDeviceInfo) r1
                goto L_0x006d
            L_0x006c:
            L_0x006d:
                int r2 = r19.readInt()
                android.os.ParcelFileDescriptor r3 = r6.openDvbDevice(r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x0081
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x0084
            L_0x0081:
                r9.writeInt(r0)
            L_0x0084:
                return r11
            L_0x0085:
                r8.enforceInterface(r10)
                java.util.List r0 = r17.getDvbDeviceList()
                r20.writeNoException()
                r9.writeTypedList(r0)
                return r11
            L_0x0093:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.isSingleSessionActive(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00a5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x00bb
                android.os.Parcelable$Creator<android.view.Surface> r2 = android.view.Surface.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.view.Surface r2 = (android.view.Surface) r2
                goto L_0x00bc
            L_0x00bb:
                r2 = r1
            L_0x00bc:
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x00cb
                android.os.Parcelable$Creator<android.media.tv.TvStreamConfig> r1 = android.media.tv.TvStreamConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.tv.TvStreamConfig r1 = (android.media.tv.TvStreamConfig) r1
                goto L_0x00cc
            L_0x00cb:
            L_0x00cc:
                int r3 = r19.readInt()
                boolean r4 = r6.captureFrame(r0, r2, r1, r3)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x00db:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                java.util.List r2 = r6.getAvailableTvStreamConfigList(r0, r1)
                r20.writeNoException()
                r9.writeTypedList(r2)
                return r11
            L_0x00f1:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.tv.ITvInputHardware r1 = android.media.tv.ITvInputHardware.Stub.asInterface(r1)
                int r2 = r19.readInt()
                r6.releaseTvInputHardware(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x010b:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.tv.ITvInputHardwareCallback r2 = android.media.tv.ITvInputHardwareCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x0129
                android.os.Parcelable$Creator<android.media.tv.TvInputInfo> r3 = android.media.tv.TvInputInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.media.tv.TvInputInfo r3 = (android.media.tv.TvInputInfo) r3
                goto L_0x012a
            L_0x0129:
                r3 = r1
            L_0x012a:
                int r4 = r19.readInt()
                android.media.tv.ITvInputHardware r5 = r6.acquireTvInputHardware(r0, r2, r3, r4)
                r20.writeNoException()
                if (r5 == 0) goto L_0x013c
                android.os.IBinder r1 = r5.asBinder()
            L_0x013c:
                r9.writeStrongBinder(r1)
                return r11
            L_0x0140:
                r8.enforceInterface(r10)
                java.util.List r0 = r17.getHardwareList()
                r20.writeNoException()
                r9.writeTypedList(r0)
                return r11
            L_0x014e:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.stopRecording(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0160:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0176
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0177
            L_0x0176:
            L_0x0177:
                int r2 = r19.readInt()
                r6.startRecording(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0182:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0191
                r0 = r11
            L_0x0191:
                int r2 = r19.readInt()
                r6.timeShiftEnablePositionTracking(r1, r0, r2)
                r20.writeNoException()
                return r11
            L_0x019c:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01b2
                android.os.Parcelable$Creator<android.media.PlaybackParams> r1 = android.media.PlaybackParams.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.PlaybackParams r1 = (android.media.PlaybackParams) r1
                goto L_0x01b3
            L_0x01b2:
            L_0x01b3:
                int r2 = r19.readInt()
                r6.timeShiftSetPlaybackParams(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x01be:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                long r1 = r19.readLong()
                int r3 = r19.readInt()
                r6.timeShiftSeekTo(r0, r1, r3)
                r20.writeNoException()
                return r11
            L_0x01d4:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.timeShiftResume(r0, r1)
                r20.writeNoException()
                return r11
            L_0x01e6:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.timeShiftPause(r0, r1)
                r20.writeNoException()
                return r11
            L_0x01f8:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x020e
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x020f
            L_0x020e:
            L_0x020f:
                int r2 = r19.readInt()
                r6.timeShiftPlay(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x021a:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                r6.unblockContent(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0230:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.removeOverlayView(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0242:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0258
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x0259
            L_0x0258:
            L_0x0259:
                int r2 = r19.readInt()
                r6.relayoutOverlayView(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0264:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.os.IBinder r2 = r19.readStrongBinder()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x027e
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x027f
            L_0x027e:
            L_0x027f:
                int r3 = r19.readInt()
                r6.createOverlayView(r0, r2, r1, r3)
                r20.writeNoException()
                return r11
            L_0x028a:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                java.lang.String r2 = r19.readString()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x02a4
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x02a5
            L_0x02a4:
            L_0x02a5:
                int r3 = r19.readInt()
                r6.sendAppPrivateCommand(r0, r2, r1, r3)
                r20.writeNoException()
                return r11
            L_0x02b0:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                java.lang.String r2 = r19.readString()
                int r3 = r19.readInt()
                r6.selectTrack(r0, r1, r2, r3)
                r20.writeNoException()
                return r11
            L_0x02ca:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x02d9
                r0 = r11
            L_0x02d9:
                int r2 = r19.readInt()
                r6.setCaptionEnabled(r1, r0, r2)
                r20.writeNoException()
                return r11
            L_0x02e4:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x02fa
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x02fb
            L_0x02fa:
                r2 = r1
            L_0x02fb:
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x030a
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x030b
            L_0x030a:
            L_0x030b:
                int r3 = r19.readInt()
                r6.tune(r0, r2, r1, r3)
                r20.writeNoException()
                return r11
            L_0x0316:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                float r1 = r19.readFloat()
                int r2 = r19.readInt()
                r6.setVolume(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x032c:
                r8.enforceInterface(r10)
                android.os.IBinder r12 = r19.readStrongBinder()
                int r13 = r19.readInt()
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.dispatchSurfaceChanged(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x0352:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x0368
                android.os.Parcelable$Creator<android.view.Surface> r1 = android.view.Surface.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.view.Surface r1 = (android.view.Surface) r1
                goto L_0x0369
            L_0x0368:
            L_0x0369:
                int r2 = r19.readInt()
                r6.setSurface(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x0374:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.setMainSession(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0386:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                int r1 = r19.readInt()
                r6.releaseSession(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0398:
                r8.enforceInterface(r10)
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.tv.ITvInputClient r12 = android.media.tv.ITvInputClient.Stub.asInterface(r1)
                java.lang.String r13 = r19.readString()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x03af
                r3 = r11
                goto L_0x03b0
            L_0x03af:
                r3 = r0
            L_0x03b0:
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r4 = r14
                r5 = r15
                r0.createSession(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x03c5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                r6.removeBlockedRating(r0, r1)
                r20.writeNoException()
                return r11
            L_0x03d7:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                r6.addBlockedRating(r0, r1)
                r20.writeNoException()
                return r11
            L_0x03e9:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.util.List r1 = r6.getBlockedRatings(r0)
                r20.writeNoException()
                r9.writeStringList(r1)
                return r11
            L_0x03fb:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                boolean r2 = r6.isRatingBlocked(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0411:
                r8.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x041c
                r0 = r11
            L_0x041c:
                int r1 = r19.readInt()
                r6.setParentalControlsEnabled(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0427:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                boolean r1 = r6.isParentalControlsEnabled(r0)
                r20.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0439:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.tv.ITvInputManagerCallback r0 = android.media.tv.ITvInputManagerCallback.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.unregisterCallback(r0, r1)
                r20.writeNoException()
                return r11
            L_0x044f:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.tv.ITvInputManagerCallback r0 = android.media.tv.ITvInputManagerCallback.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r6.registerCallback(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0465:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.util.List r1 = r6.getTvContentRatingSystemList(r0)
                r20.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x0477:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                int r2 = r6.getTvInputState(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x048d:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x04a0
                android.os.Parcelable$Creator<android.media.tv.TvInputInfo> r0 = android.media.tv.TvInputInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r1 = r0
                android.media.tv.TvInputInfo r1 = (android.media.tv.TvInputInfo) r1
                goto L_0x04a1
            L_0x04a0:
            L_0x04a1:
                r0 = r1
                int r1 = r19.readInt()
                r6.updateTvInputInfo(r0, r1)
                r20.writeNoException()
                return r11
            L_0x04ad:
                r8.enforceInterface(r10)
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                android.media.tv.TvInputInfo r3 = r6.getTvInputInfo(r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x04c8
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x04cb
            L_0x04c8:
                r9.writeInt(r0)
            L_0x04cb:
                return r11
            L_0x04cc:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                java.util.List r1 = r6.getTvInputList(r0)
                r20.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x04de:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.tv.ITvInputManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITvInputManager {
            public static ITvInputManager sDefaultImpl;
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

            public List<TvInputInfo> getTvInputList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTvInputList(userId);
                    }
                    _reply.readException();
                    List<TvInputInfo> _result = _reply.createTypedArrayList(TvInputInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public TvInputInfo getTvInputInfo(String inputId, int userId) throws RemoteException {
                TvInputInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTvInputInfo(inputId, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TvInputInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    TvInputInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateTvInputInfo(TvInputInfo inputInfo, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputInfo != null) {
                        _data.writeInt(1);
                        inputInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateTvInputInfo(inputInfo, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getTvInputState(String inputId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTvInputState(inputId, userId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<TvContentRatingSystemInfo> getTvContentRatingSystemList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTvContentRatingSystemList(userId);
                    }
                    _reply.readException();
                    List<TvContentRatingSystemInfo> _result = _reply.createTypedArrayList(TvContentRatingSystemInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerCallback(ITvInputManagerCallback callback, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerCallback(callback, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallback(ITvInputManagerCallback callback, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterCallback(callback, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isParentalControlsEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isParentalControlsEnabled(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setParentalControlsEnabled(boolean enabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setParentalControlsEnabled(enabled, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRatingBlocked(String rating, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRatingBlocked(rating, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getBlockedRatings(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBlockedRatings(userId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addBlockedRating(String rating, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addBlockedRating(rating, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeBlockedRating(String rating, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeBlockedRating(rating, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createSession(ITvInputClient client, String inputId, boolean isRecordingSession, int seq, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeString(inputId);
                    _data.writeInt(isRecordingSession);
                    _data.writeInt(seq);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createSession(client, inputId, isRecordingSession, seq, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseSession(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseSession(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMainSession(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMainSession(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSurface(IBinder sessionToken, Surface surface, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSurface(sessionToken, surface, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dispatchSurfaceChanged(IBinder sessionToken, int format, int width, int height, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(format);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispatchSurfaceChanged(sessionToken, format, width, height, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolume(IBinder sessionToken, float volume, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeFloat(volume);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolume(sessionToken, volume, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tune(IBinder sessionToken, Uri channelUri, Bundle params, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
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
                    _data.writeInt(userId);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tune(sessionToken, channelUri, params, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCaptionEnabled(IBinder sessionToken, boolean enabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(enabled);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCaptionEnabled(sessionToken, enabled, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void selectTrack(IBinder sessionToken, int type, String trackId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(type);
                    _data.writeString(trackId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().selectTrack(sessionToken, type, trackId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendAppPrivateCommand(IBinder sessionToken, String action, Bundle data, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(action);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendAppPrivateCommand(sessionToken, action, data, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createOverlayView(IBinder sessionToken, IBinder windowToken, Rect frame, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeStrongBinder(windowToken);
                    if (frame != null) {
                        _data.writeInt(1);
                        frame.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createOverlayView(sessionToken, windowToken, frame, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void relayoutOverlayView(IBinder sessionToken, Rect frame, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    if (frame != null) {
                        _data.writeInt(1);
                        frame.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().relayoutOverlayView(sessionToken, frame, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOverlayView(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOverlayView(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unblockContent(IBinder sessionToken, String unblockedRating, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeString(unblockedRating);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unblockContent(sessionToken, unblockedRating, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftPlay(IBinder sessionToken, Uri recordedProgramUri, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    if (recordedProgramUri != null) {
                        _data.writeInt(1);
                        recordedProgramUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftPlay(sessionToken, recordedProgramUri, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftPause(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftPause(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftResume(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftResume(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftSeekTo(IBinder sessionToken, long timeMs, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeLong(timeMs);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftSeekTo(sessionToken, timeMs, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftSetPlaybackParams(IBinder sessionToken, PlaybackParams params, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftSetPlaybackParams(sessionToken, params, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void timeShiftEnablePositionTracking(IBinder sessionToken, boolean enable, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(enable);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().timeShiftEnablePositionTracking(sessionToken, enable, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startRecording(IBinder sessionToken, Uri programUri, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    if (programUri != null) {
                        _data.writeInt(1);
                        programUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startRecording(sessionToken, programUri, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopRecording(IBinder sessionToken, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopRecording(sessionToken, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<TvInputHardwareInfo> getHardwareList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHardwareList();
                    }
                    _reply.readException();
                    List<TvInputHardwareInfo> _result = _reply.createTypedArrayList(TvInputHardwareInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ITvInputHardware acquireTvInputHardware(int deviceId, ITvInputHardwareCallback callback, TvInputInfo info, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().acquireTvInputHardware(deviceId, callback, info, userId);
                    }
                    _reply.readException();
                    ITvInputHardware _result = ITvInputHardware.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseTvInputHardware(int deviceId, ITvInputHardware hardware, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongBinder(hardware != null ? hardware.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseTvInputHardware(deviceId, hardware, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<TvStreamConfig> getAvailableTvStreamConfigList(String inputId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAvailableTvStreamConfigList(inputId, userId);
                    }
                    _reply.readException();
                    List<TvStreamConfig> _result = _reply.createTypedArrayList(TvStreamConfig.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean captureFrame(String inputId, Surface surface, TvStreamConfig config, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    boolean _result = true;
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().captureFrame(inputId, surface, config, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSingleSessionActive(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSingleSessionActive(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<DvbDeviceInfo> getDvbDeviceList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDvbDeviceList();
                    }
                    _reply.readException();
                    List<DvbDeviceInfo> _result = _reply.createTypedArrayList(DvbDeviceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo info, int device) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(device);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openDvbDevice(info, device);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendTvInputNotifyIntent(Intent intent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendTvInputNotifyIntent(intent, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestChannelBrowsable(Uri channelUri, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (channelUri != null) {
                        _data.writeInt(1);
                        channelUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestChannelBrowsable(channelUri, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITvInputManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
