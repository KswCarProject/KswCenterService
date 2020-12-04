package android.media.session;

import android.app.PendingIntent;
import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.KeyEvent;

public interface ISessionController extends IInterface {
    void adjustVolume(String str, String str2, ISessionControllerCallback iSessionControllerCallback, int i, int i2) throws RemoteException;

    void fastForward(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    Bundle getExtras() throws RemoteException;

    long getFlags() throws RemoteException;

    PendingIntent getLaunchPendingIntent() throws RemoteException;

    MediaMetadata getMetadata() throws RemoteException;

    String getPackageName() throws RemoteException;

    PlaybackState getPlaybackState() throws RemoteException;

    ParceledListSlice getQueue() throws RemoteException;

    CharSequence getQueueTitle() throws RemoteException;

    int getRatingType() throws RemoteException;

    Bundle getSessionInfo() throws RemoteException;

    String getTag() throws RemoteException;

    MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException;

    void next(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void pause(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void play(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void playFromMediaId(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void playFromSearch(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void playFromUri(String str, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException;

    void prepare(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void prepareFromMediaId(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void prepareFromSearch(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void prepareFromUri(String str, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException;

    void previous(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void rate(String str, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException;

    void registerCallback(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void rewind(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void seekTo(String str, ISessionControllerCallback iSessionControllerCallback, long j) throws RemoteException;

    void sendCommand(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException;

    void sendCustomAction(String str, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    boolean sendMediaButton(String str, ISessionControllerCallback iSessionControllerCallback, KeyEvent keyEvent) throws RemoteException;

    void setPlaybackSpeed(String str, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException;

    void setVolumeTo(String str, String str2, ISessionControllerCallback iSessionControllerCallback, int i, int i2) throws RemoteException;

    void skipToQueueItem(String str, ISessionControllerCallback iSessionControllerCallback, long j) throws RemoteException;

    void stop(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    public static class Default implements ISessionController {
        public void sendCommand(String packageName, ISessionControllerCallback caller, String command, Bundle args, ResultReceiver cb) throws RemoteException {
        }

        public boolean sendMediaButton(String packageName, ISessionControllerCallback caller, KeyEvent mediaButton) throws RemoteException {
            return false;
        }

        public void registerCallback(String packageName, ISessionControllerCallback cb) throws RemoteException {
        }

        public void unregisterCallback(ISessionControllerCallback cb) throws RemoteException {
        }

        public String getPackageName() throws RemoteException {
            return null;
        }

        public String getTag() throws RemoteException {
            return null;
        }

        public Bundle getSessionInfo() throws RemoteException {
            return null;
        }

        public PendingIntent getLaunchPendingIntent() throws RemoteException {
            return null;
        }

        public long getFlags() throws RemoteException {
            return 0;
        }

        public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
            return null;
        }

        public void adjustVolume(String packageName, String opPackageName, ISessionControllerCallback caller, int direction, int flags) throws RemoteException {
        }

        public void setVolumeTo(String packageName, String opPackageName, ISessionControllerCallback caller, int value, int flags) throws RemoteException {
        }

        public void prepare(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void prepareFromMediaId(String packageName, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
        }

        public void prepareFromSearch(String packageName, ISessionControllerCallback caller, String string, Bundle extras) throws RemoteException {
        }

        public void prepareFromUri(String packageName, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
        }

        public void play(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void playFromMediaId(String packageName, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
        }

        public void playFromSearch(String packageName, ISessionControllerCallback caller, String string, Bundle extras) throws RemoteException {
        }

        public void playFromUri(String packageName, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
        }

        public void skipToQueueItem(String packageName, ISessionControllerCallback caller, long id) throws RemoteException {
        }

        public void pause(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void stop(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void next(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void previous(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void fastForward(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void rewind(String packageName, ISessionControllerCallback caller) throws RemoteException {
        }

        public void seekTo(String packageName, ISessionControllerCallback caller, long pos) throws RemoteException {
        }

        public void rate(String packageName, ISessionControllerCallback caller, Rating rating) throws RemoteException {
        }

        public void setPlaybackSpeed(String packageName, ISessionControllerCallback caller, float speed) throws RemoteException {
        }

        public void sendCustomAction(String packageName, ISessionControllerCallback caller, String action, Bundle args) throws RemoteException {
        }

        public MediaMetadata getMetadata() throws RemoteException {
            return null;
        }

        public PlaybackState getPlaybackState() throws RemoteException {
            return null;
        }

        public ParceledListSlice getQueue() throws RemoteException {
            return null;
        }

        public CharSequence getQueueTitle() throws RemoteException {
            return null;
        }

        public Bundle getExtras() throws RemoteException {
            return null;
        }

        public int getRatingType() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISessionController {
        private static final String DESCRIPTOR = "android.media.session.ISessionController";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 26;
        static final int TRANSACTION_getExtras = 36;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 32;
        static final int TRANSACTION_getPackageName = 5;
        static final int TRANSACTION_getPlaybackState = 33;
        static final int TRANSACTION_getQueue = 34;
        static final int TRANSACTION_getQueueTitle = 35;
        static final int TRANSACTION_getRatingType = 37;
        static final int TRANSACTION_getSessionInfo = 7;
        static final int TRANSACTION_getTag = 6;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_next = 24;
        static final int TRANSACTION_pause = 22;
        static final int TRANSACTION_play = 17;
        static final int TRANSACTION_playFromMediaId = 18;
        static final int TRANSACTION_playFromSearch = 19;
        static final int TRANSACTION_playFromUri = 20;
        static final int TRANSACTION_prepare = 13;
        static final int TRANSACTION_prepareFromMediaId = 14;
        static final int TRANSACTION_prepareFromSearch = 15;
        static final int TRANSACTION_prepareFromUri = 16;
        static final int TRANSACTION_previous = 25;
        static final int TRANSACTION_rate = 29;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_rewind = 27;
        static final int TRANSACTION_seekTo = 28;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 31;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setPlaybackSpeed = 30;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 21;
        static final int TRANSACTION_stop = 23;
        static final int TRANSACTION_unregisterCallback = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISessionController)) {
                return new Proxy(obj);
            }
            return (ISessionController) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "sendCommand";
                case 2:
                    return "sendMediaButton";
                case 3:
                    return "registerCallback";
                case 4:
                    return "unregisterCallback";
                case 5:
                    return "getPackageName";
                case 6:
                    return "getTag";
                case 7:
                    return "getSessionInfo";
                case 8:
                    return "getLaunchPendingIntent";
                case 9:
                    return "getFlags";
                case 10:
                    return "getVolumeAttributes";
                case 11:
                    return "adjustVolume";
                case 12:
                    return "setVolumeTo";
                case 13:
                    return "prepare";
                case 14:
                    return "prepareFromMediaId";
                case 15:
                    return "prepareFromSearch";
                case 16:
                    return "prepareFromUri";
                case 17:
                    return "play";
                case 18:
                    return "playFromMediaId";
                case 19:
                    return "playFromSearch";
                case 20:
                    return "playFromUri";
                case 21:
                    return "skipToQueueItem";
                case 22:
                    return "pause";
                case 23:
                    return "stop";
                case 24:
                    return "next";
                case 25:
                    return "previous";
                case 26:
                    return "fastForward";
                case 27:
                    return "rewind";
                case 28:
                    return "seekTo";
                case 29:
                    return TextToSpeech.Engine.KEY_PARAM_RATE;
                case 30:
                    return "setPlaybackSpeed";
                case 31:
                    return "sendCustomAction";
                case 32:
                    return "getMetadata";
                case 33:
                    return "getPlaybackState";
                case 34:
                    return "getQueue";
                case 35:
                    return "getQueueTitle";
                case 36:
                    return "getExtras";
                case 37:
                    return "getRatingType";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v29, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v37, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v57, resolved type: android.media.Rating} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v63, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v72 */
        /* JADX WARNING: type inference failed for: r1v73 */
        /* JADX WARNING: type inference failed for: r1v74 */
        /* JADX WARNING: type inference failed for: r1v75 */
        /* JADX WARNING: type inference failed for: r1v76 */
        /* JADX WARNING: type inference failed for: r1v77 */
        /* JADX WARNING: type inference failed for: r1v78 */
        /* JADX WARNING: type inference failed for: r1v79 */
        /* JADX WARNING: type inference failed for: r1v80 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.media.session.ISessionController"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x0453
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0412;
                    case 2: goto L_0x03e8;
                    case 3: goto L_0x03d2;
                    case 4: goto L_0x03c0;
                    case 5: goto L_0x03b2;
                    case 6: goto L_0x03a4;
                    case 7: goto L_0x038d;
                    case 8: goto L_0x0376;
                    case 9: goto L_0x0368;
                    case 10: goto L_0x0351;
                    case 11: goto L_0x0327;
                    case 12: goto L_0x02fd;
                    case 13: goto L_0x02e7;
                    case 14: goto L_0x02bd;
                    case 15: goto L_0x0293;
                    case 16: goto L_0x025d;
                    case 17: goto L_0x0247;
                    case 18: goto L_0x021d;
                    case 19: goto L_0x01f3;
                    case 20: goto L_0x01bd;
                    case 21: goto L_0x01a3;
                    case 22: goto L_0x018d;
                    case 23: goto L_0x0177;
                    case 24: goto L_0x0161;
                    case 25: goto L_0x014b;
                    case 26: goto L_0x0135;
                    case 27: goto L_0x011f;
                    case 28: goto L_0x0105;
                    case 29: goto L_0x00df;
                    case 30: goto L_0x00c5;
                    case 31: goto L_0x009b;
                    case 32: goto L_0x0084;
                    case 33: goto L_0x006d;
                    case 34: goto L_0x0056;
                    case 35: goto L_0x003f;
                    case 36: goto L_0x0028;
                    case 37: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                int r0 = r17.getRatingType()
                r20.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x0028:
                r8.enforceInterface(r10)
                android.os.Bundle r1 = r17.getExtras()
                r20.writeNoException()
                if (r1 == 0) goto L_0x003b
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x003e
            L_0x003b:
                r9.writeInt(r0)
            L_0x003e:
                return r11
            L_0x003f:
                r8.enforceInterface(r10)
                java.lang.CharSequence r1 = r17.getQueueTitle()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0052
                r9.writeInt(r11)
                android.text.TextUtils.writeToParcel(r1, r9, r11)
                goto L_0x0055
            L_0x0052:
                r9.writeInt(r0)
            L_0x0055:
                return r11
            L_0x0056:
                r8.enforceInterface(r10)
                android.content.pm.ParceledListSlice r1 = r17.getQueue()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0069
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x006c
            L_0x0069:
                r9.writeInt(r0)
            L_0x006c:
                return r11
            L_0x006d:
                r8.enforceInterface(r10)
                android.media.session.PlaybackState r1 = r17.getPlaybackState()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0080
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x0083
            L_0x0080:
                r9.writeInt(r0)
            L_0x0083:
                return r11
            L_0x0084:
                r8.enforceInterface(r10)
                android.media.MediaMetadata r1 = r17.getMetadata()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0097
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x009a
            L_0x0097:
                r9.writeInt(r0)
            L_0x009a:
                return r11
            L_0x009b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x00bd
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00be
            L_0x00bd:
            L_0x00be:
                r6.sendCustomAction(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x00c5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                float r2 = r19.readFloat()
                r6.setPlaybackSpeed(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x00df:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x00fd
                android.os.Parcelable$Creator<android.media.Rating> r1 = android.media.Rating.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.media.Rating r1 = (android.media.Rating) r1
                goto L_0x00fe
            L_0x00fd:
            L_0x00fe:
                r6.rate(r0, r2, r1)
                r20.writeNoException()
                return r11
            L_0x0105:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                long r2 = r19.readLong()
                r6.seekTo(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x011f:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.rewind(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0135:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.fastForward(r0, r1)
                r20.writeNoException()
                return r11
            L_0x014b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.previous(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0161:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.next(r0, r1)
                r20.writeNoException()
                return r11
            L_0x0177:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.stop(r0, r1)
                r20.writeNoException()
                return r11
            L_0x018d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.pause(r0, r1)
                r20.writeNoException()
                return r11
            L_0x01a3:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                long r2 = r19.readLong()
                r6.skipToQueueItem(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x01bd:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x01db
                android.os.Parcelable$Creator<android.net.Uri> r3 = android.net.Uri.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.net.Uri r3 = (android.net.Uri) r3
                goto L_0x01dc
            L_0x01db:
                r3 = r1
            L_0x01dc:
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x01eb
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x01ec
            L_0x01eb:
            L_0x01ec:
                r6.playFromUri(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x01f3:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x0215
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0216
            L_0x0215:
            L_0x0216:
                r6.playFromSearch(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x021d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x023f
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0240
            L_0x023f:
            L_0x0240:
                r6.playFromMediaId(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x0247:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.play(r0, r1)
                r20.writeNoException()
                return r11
            L_0x025d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x027b
                android.os.Parcelable$Creator<android.net.Uri> r3 = android.net.Uri.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.net.Uri r3 = (android.net.Uri) r3
                goto L_0x027c
            L_0x027b:
                r3 = r1
            L_0x027c:
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x028b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x028c
            L_0x028b:
            L_0x028c:
                r6.prepareFromUri(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x0293:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x02b5
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x02b6
            L_0x02b5:
            L_0x02b6:
                r6.prepareFromSearch(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x02bd:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                java.lang.String r3 = r19.readString()
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x02df
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x02e0
            L_0x02df:
            L_0x02e0:
                r6.prepareFromMediaId(r0, r2, r3, r1)
                r20.writeNoException()
                return r11
            L_0x02e7:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.prepare(r0, r1)
                r20.writeNoException()
                return r11
            L_0x02fd:
                r8.enforceInterface(r10)
                java.lang.String r12 = r19.readString()
                java.lang.String r13 = r19.readString()
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.setVolumeTo(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x0327:
                r8.enforceInterface(r10)
                java.lang.String r12 = r19.readString()
                java.lang.String r13 = r19.readString()
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.adjustVolume(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x0351:
                r8.enforceInterface(r10)
                android.media.session.MediaController$PlaybackInfo r1 = r17.getVolumeAttributes()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0364
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x0367
            L_0x0364:
                r9.writeInt(r0)
            L_0x0367:
                return r11
            L_0x0368:
                r8.enforceInterface(r10)
                long r0 = r17.getFlags()
                r20.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x0376:
                r8.enforceInterface(r10)
                android.app.PendingIntent r1 = r17.getLaunchPendingIntent()
                r20.writeNoException()
                if (r1 == 0) goto L_0x0389
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x038c
            L_0x0389:
                r9.writeInt(r0)
            L_0x038c:
                return r11
            L_0x038d:
                r8.enforceInterface(r10)
                android.os.Bundle r1 = r17.getSessionInfo()
                r20.writeNoException()
                if (r1 == 0) goto L_0x03a0
                r9.writeInt(r11)
                r1.writeToParcel(r9, r11)
                goto L_0x03a3
            L_0x03a0:
                r9.writeInt(r0)
            L_0x03a3:
                return r11
            L_0x03a4:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getTag()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x03b2:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.getPackageName()
                r20.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x03c0:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r0 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                r6.unregisterCallback(r0)
                r20.writeNoException()
                return r11
            L_0x03d2:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r1 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r1 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                r6.registerCallback(r0, r1)
                r20.writeNoException()
                return r11
            L_0x03e8:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                android.os.IBinder r2 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r2 = android.media.session.ISessionControllerCallback.Stub.asInterface(r2)
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x0406
                android.os.Parcelable$Creator<android.view.KeyEvent> r1 = android.view.KeyEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.view.KeyEvent r1 = (android.view.KeyEvent) r1
                goto L_0x0407
            L_0x0406:
            L_0x0407:
                boolean r3 = r6.sendMediaButton(r0, r2, r1)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0412:
                r8.enforceInterface(r10)
                java.lang.String r12 = r19.readString()
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISessionControllerCallback r13 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                java.lang.String r14 = r19.readString()
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0435
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r4 = r0
                goto L_0x0436
            L_0x0435:
                r4 = r1
            L_0x0436:
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0446
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                r5 = r0
                goto L_0x0447
            L_0x0446:
                r5 = r1
            L_0x0447:
                r0 = r17
                r1 = r12
                r2 = r13
                r3 = r14
                r0.sendCommand(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x0453:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ISessionController.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISessionController {
            public static ISessionController sDefaultImpl;
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

            public void sendCommand(String packageName, ISessionControllerCallback caller, String command, Bundle args, ResultReceiver cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(command);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (cb != null) {
                        _data.writeInt(1);
                        cb.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendCommand(packageName, caller, command, args, cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean sendMediaButton(String packageName, ISessionControllerCallback caller, KeyEvent mediaButton) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    boolean _result = true;
                    if (mediaButton != null) {
                        _data.writeInt(1);
                        mediaButton.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendMediaButton(packageName, caller, mediaButton);
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

            public void registerCallback(String packageName, ISessionControllerCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerCallback(packageName, cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallback(ISessionControllerCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterCallback(cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackageName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getTag() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTag();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getSessionInfo() throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSessionInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                PendingIntent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLaunchPendingIntent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PendingIntent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PendingIntent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getFlags() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFlags();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
                MediaController.PlaybackInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumeAttributes();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = MediaController.PlaybackInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    MediaController.PlaybackInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void adjustVolume(String packageName, String opPackageName, ISessionControllerCallback caller, int direction, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().adjustVolume(packageName, opPackageName, caller, direction, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeTo(String packageName, String opPackageName, ISessionControllerCallback caller, int value, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeInt(value);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumeTo(packageName, opPackageName, caller, value, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepare(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepare(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromMediaId(String packageName, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(mediaId);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareFromMediaId(packageName, caller, mediaId, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromSearch(String packageName, ISessionControllerCallback caller, String string, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(string);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareFromSearch(packageName, caller, string, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromUri(String packageName, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareFromUri(packageName, caller, uri, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void play(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().play(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromMediaId(String packageName, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(mediaId);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().playFromMediaId(packageName, caller, mediaId, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromSearch(String packageName, ISessionControllerCallback caller, String string, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(string);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().playFromSearch(packageName, caller, string, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromUri(String packageName, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().playFromUri(packageName, caller, uri, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void skipToQueueItem(String packageName, ISessionControllerCallback caller, long id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeLong(id);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().skipToQueueItem(packageName, caller, id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pause(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pause(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stop(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stop(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void next(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().next(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void previous(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().previous(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fastForward(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().fastForward(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rewind(String packageName, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().rewind(packageName, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void seekTo(String packageName, ISessionControllerCallback caller, long pos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeLong(pos);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().seekTo(packageName, caller, pos);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rate(String packageName, ISessionControllerCallback caller, Rating rating) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (rating != null) {
                        _data.writeInt(1);
                        rating.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().rate(packageName, caller, rating);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPlaybackSpeed(String packageName, ISessionControllerCallback caller, float speed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeFloat(speed);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPlaybackSpeed(packageName, caller, speed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendCustomAction(String packageName, ISessionControllerCallback caller, String action, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeString(action);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendCustomAction(packageName, caller, action, args);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public MediaMetadata getMetadata() throws RemoteException {
                MediaMetadata _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMetadata();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = MediaMetadata.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    MediaMetadata _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PlaybackState getPlaybackState() throws RemoteException {
                PlaybackState _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlaybackState();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PlaybackState.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PlaybackState _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getQueue() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getQueue();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CharSequence getQueueTitle() throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getQueueTitle();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CharSequence _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getExtras() throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getExtras();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRatingType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRatingType();
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
        }

        public static boolean setDefaultImpl(ISessionController impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISessionController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
