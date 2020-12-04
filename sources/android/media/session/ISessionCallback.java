package android.media.session;

import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

public interface ISessionCallback extends IInterface {
    void onAdjustVolume(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, int i3) throws RemoteException;

    void onCommand(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException;

    void onCustomAction(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void onFastForward(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onMediaButton(String str, int i, int i2, Intent intent, int i3, ResultReceiver resultReceiver) throws RemoteException;

    void onMediaButtonFromController(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, Intent intent) throws RemoteException;

    void onNext(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onPause(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onPlay(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onPlayFromMediaId(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void onPlayFromSearch(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void onPlayFromUri(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException;

    void onPrepare(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onPrepareFromMediaId(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void onPrepareFromSearch(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, String str2, Bundle bundle) throws RemoteException;

    void onPrepareFromUri(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, Uri uri, Bundle bundle) throws RemoteException;

    void onPrevious(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onRate(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, Rating rating) throws RemoteException;

    void onRewind(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void onSeekTo(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, long j) throws RemoteException;

    void onSetPlaybackSpeed(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, float f) throws RemoteException;

    void onSetVolumeTo(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, int i3) throws RemoteException;

    void onSkipToTrack(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback, long j) throws RemoteException;

    void onStop(String str, int i, int i2, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    public static class Default implements ISessionCallback {
        public void onCommand(String packageName, int pid, int uid, ISessionControllerCallback caller, String command, Bundle args, ResultReceiver cb) throws RemoteException {
        }

        public void onMediaButton(String packageName, int pid, int uid, Intent mediaButtonIntent, int sequenceNumber, ResultReceiver cb) throws RemoteException {
        }

        public void onMediaButtonFromController(String packageName, int pid, int uid, ISessionControllerCallback caller, Intent mediaButtonIntent) throws RemoteException {
        }

        public void onPrepare(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onPrepareFromMediaId(String packageName, int pid, int uid, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
        }

        public void onPrepareFromSearch(String packageName, int pid, int uid, ISessionControllerCallback caller, String query, Bundle extras) throws RemoteException {
        }

        public void onPrepareFromUri(String packageName, int pid, int uid, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
        }

        public void onPlay(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onPlayFromMediaId(String packageName, int pid, int uid, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
        }

        public void onPlayFromSearch(String packageName, int pid, int uid, ISessionControllerCallback caller, String query, Bundle extras) throws RemoteException {
        }

        public void onPlayFromUri(String packageName, int pid, int uid, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
        }

        public void onSkipToTrack(String packageName, int pid, int uid, ISessionControllerCallback caller, long id) throws RemoteException {
        }

        public void onPause(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onStop(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onNext(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onPrevious(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onFastForward(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onRewind(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
        }

        public void onSeekTo(String packageName, int pid, int uid, ISessionControllerCallback caller, long pos) throws RemoteException {
        }

        public void onRate(String packageName, int pid, int uid, ISessionControllerCallback caller, Rating rating) throws RemoteException {
        }

        public void onSetPlaybackSpeed(String packageName, int pid, int uid, ISessionControllerCallback caller, float speed) throws RemoteException {
        }

        public void onCustomAction(String packageName, int pid, int uid, ISessionControllerCallback caller, String action, Bundle args) throws RemoteException {
        }

        public void onAdjustVolume(String packageName, int pid, int uid, ISessionControllerCallback caller, int direction) throws RemoteException {
        }

        public void onSetVolumeTo(String packageName, int pid, int uid, ISessionControllerCallback caller, int value) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISessionCallback {
        private static final String DESCRIPTOR = "android.media.session.ISessionCallback";
        static final int TRANSACTION_onAdjustVolume = 23;
        static final int TRANSACTION_onCommand = 1;
        static final int TRANSACTION_onCustomAction = 22;
        static final int TRANSACTION_onFastForward = 17;
        static final int TRANSACTION_onMediaButton = 2;
        static final int TRANSACTION_onMediaButtonFromController = 3;
        static final int TRANSACTION_onNext = 15;
        static final int TRANSACTION_onPause = 13;
        static final int TRANSACTION_onPlay = 8;
        static final int TRANSACTION_onPlayFromMediaId = 9;
        static final int TRANSACTION_onPlayFromSearch = 10;
        static final int TRANSACTION_onPlayFromUri = 11;
        static final int TRANSACTION_onPrepare = 4;
        static final int TRANSACTION_onPrepareFromMediaId = 5;
        static final int TRANSACTION_onPrepareFromSearch = 6;
        static final int TRANSACTION_onPrepareFromUri = 7;
        static final int TRANSACTION_onPrevious = 16;
        static final int TRANSACTION_onRate = 20;
        static final int TRANSACTION_onRewind = 18;
        static final int TRANSACTION_onSeekTo = 19;
        static final int TRANSACTION_onSetPlaybackSpeed = 21;
        static final int TRANSACTION_onSetVolumeTo = 24;
        static final int TRANSACTION_onSkipToTrack = 12;
        static final int TRANSACTION_onStop = 14;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISessionCallback)) {
                return new Proxy(obj);
            }
            return (ISessionCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCommand";
                case 2:
                    return "onMediaButton";
                case 3:
                    return "onMediaButtonFromController";
                case 4:
                    return "onPrepare";
                case 5:
                    return "onPrepareFromMediaId";
                case 6:
                    return "onPrepareFromSearch";
                case 7:
                    return "onPrepareFromUri";
                case 8:
                    return "onPlay";
                case 9:
                    return "onPlayFromMediaId";
                case 10:
                    return "onPlayFromSearch";
                case 11:
                    return "onPlayFromUri";
                case 12:
                    return "onSkipToTrack";
                case 13:
                    return "onPause";
                case 14:
                    return "onStop";
                case 15:
                    return "onNext";
                case 16:
                    return "onPrevious";
                case 17:
                    return "onFastForward";
                case 18:
                    return "onRewind";
                case 19:
                    return "onSeekTo";
                case 20:
                    return "onRate";
                case 21:
                    return "onSetPlaybackSpeed";
                case 22:
                    return "onCustomAction";
                case 23:
                    return "onAdjustVolume";
                case 24:
                    return "onSetVolumeTo";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v35, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v60, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: android.media.Rating} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v67, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v77, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v79, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v80, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v81, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v83, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v85, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v86, resolved type: android.os.ResultReceiver} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v87, resolved type: android.os.ResultReceiver} */
        /* JADX WARNING: type inference failed for: r5v2, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v17, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v23, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v28, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v33, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v39, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v44, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v49, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v64, types: [android.media.Rating] */
        /* JADX WARNING: type inference failed for: r0v71, types: [android.os.Bundle] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r8 = r18
                r9 = r19
                r10 = r20
                java.lang.String r11 = "android.media.session.ISessionCallback"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r9 == r0) goto L_0x043a
                r0 = 0
                switch(r9) {
                    case 1: goto L_0x03f0;
                    case 2: goto L_0x03b1;
                    case 3: goto L_0x037f;
                    case 4: goto L_0x0364;
                    case 5: goto L_0x032c;
                    case 6: goto L_0x02f4;
                    case 7: goto L_0x02b1;
                    case 8: goto L_0x0296;
                    case 9: goto L_0x025e;
                    case 10: goto L_0x0226;
                    case 11: goto L_0x01e3;
                    case 12: goto L_0x01bc;
                    case 13: goto L_0x01a1;
                    case 14: goto L_0x0186;
                    case 15: goto L_0x016b;
                    case 16: goto L_0x0150;
                    case 17: goto L_0x0135;
                    case 18: goto L_0x011a;
                    case 19: goto L_0x00f3;
                    case 20: goto L_0x00c1;
                    case 21: goto L_0x009b;
                    case 22: goto L_0x0063;
                    case 23: goto L_0x003d;
                    case 24: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x0017:
                r10.enforceInterface(r11)
                java.lang.String r6 = r20.readString()
                int r7 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                int r15 = r20.readInt()
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r13
                r4 = r14
                r5 = r15
                r0.onSetVolumeTo(r1, r2, r3, r4, r5)
                return r12
            L_0x003d:
                r10.enforceInterface(r11)
                java.lang.String r6 = r20.readString()
                int r7 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                int r15 = r20.readInt()
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r13
                r4 = r14
                r5 = r15
                r0.onAdjustVolume(r1, r2, r3, r4, r5)
                return r12
            L_0x0063:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x008c:
                r6 = r0
                goto L_0x008f
            L_0x008e:
                goto L_0x008c
            L_0x008f:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onCustomAction(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x009b:
                r10.enforceInterface(r11)
                java.lang.String r6 = r20.readString()
                int r7 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                float r15 = r20.readFloat()
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r13
                r4 = r14
                r5 = r15
                r0.onSetPlaybackSpeed(r1, r2, r3, r4, r5)
                return r12
            L_0x00c1:
                r10.enforceInterface(r11)
                java.lang.String r6 = r20.readString()
                int r7 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x00e8
                android.os.Parcelable$Creator<android.media.Rating> r0 = android.media.Rating.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.media.Rating r0 = (android.media.Rating) r0
            L_0x00e6:
                r5 = r0
                goto L_0x00e9
            L_0x00e8:
                goto L_0x00e6
            L_0x00e9:
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r13
                r4 = r14
                r0.onRate(r1, r2, r3, r4, r5)
                return r12
            L_0x00f3:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                long r16 = r20.readLong()
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onSeekTo(r1, r2, r3, r4, r5)
                return r12
            L_0x011a:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onRewind(r0, r1, r2, r3)
                return r12
            L_0x0135:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onFastForward(r0, r1, r2, r3)
                return r12
            L_0x0150:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onPrevious(r0, r1, r2, r3)
                return r12
            L_0x016b:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onNext(r0, r1, r2, r3)
                return r12
            L_0x0186:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onStop(r0, r1, r2, r3)
                return r12
            L_0x01a1:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onPause(r0, r1, r2, r3)
                return r12
            L_0x01bc:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r0)
                long r16 = r20.readLong()
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onSkipToTrack(r1, r2, r3, r4, r5)
                return r12
            L_0x01e3:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x020a
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Uri r1 = (android.net.Uri) r1
                r5 = r1
                goto L_0x020b
            L_0x020a:
                r5 = r0
            L_0x020b:
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x021b
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0219:
                r6 = r0
                goto L_0x021c
            L_0x021b:
                goto L_0x0219
            L_0x021c:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r0.onPlayFromUri(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x0226:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0251
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x024f:
                r6 = r0
                goto L_0x0252
            L_0x0251:
                goto L_0x024f
            L_0x0252:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onPlayFromSearch(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x025e:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0289
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0287:
                r6 = r0
                goto L_0x028a
            L_0x0289:
                goto L_0x0287
            L_0x028a:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onPlayFromMediaId(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x0296:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onPlay(r0, r1, r2, r3)
                return r12
            L_0x02b1:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x02d8
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.net.Uri r1 = (android.net.Uri) r1
                r5 = r1
                goto L_0x02d9
            L_0x02d8:
                r5 = r0
            L_0x02d9:
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x02e9
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x02e7:
                r6 = r0
                goto L_0x02ea
            L_0x02e9:
                goto L_0x02e7
            L_0x02ea:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r0.onPrepareFromUri(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x02f4:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x031f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x031d:
                r6 = r0
                goto L_0x0320
            L_0x031f:
                goto L_0x031d
            L_0x0320:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onPrepareFromSearch(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x032c:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r15 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r16 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x0357
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0355:
                r6 = r0
                goto L_0x0358
            L_0x0357:
                goto L_0x0355
            L_0x0358:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.onPrepareFromMediaId(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x0364:
                r10.enforceInterface(r11)
                java.lang.String r0 = r20.readString()
                int r1 = r20.readInt()
                int r2 = r20.readInt()
                android.os.IBinder r3 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r3 = android.media.session.ISessionControllerCallback.Stub.asInterface(r3)
                r8.onPrepare(r0, r1, r2, r3)
                return r12
            L_0x037f:
                r10.enforceInterface(r11)
                java.lang.String r6 = r20.readString()
                int r7 = r20.readInt()
                int r13 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r14 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03a6
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
            L_0x03a4:
                r5 = r0
                goto L_0x03a7
            L_0x03a6:
                goto L_0x03a4
            L_0x03a7:
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r13
                r4 = r14
                r0.onMediaButtonFromController(r1, r2, r3, r4, r5)
                return r12
            L_0x03b1:
                r10.enforceInterface(r11)
                java.lang.String r7 = r20.readString()
                int r13 = r20.readInt()
                int r14 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03d0
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.Intent r1 = (android.content.Intent) r1
                r4 = r1
                goto L_0x03d1
            L_0x03d0:
                r4 = r0
            L_0x03d1:
                int r15 = r20.readInt()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x03e5
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
            L_0x03e3:
                r6 = r0
                goto L_0x03e6
            L_0x03e5:
                goto L_0x03e3
            L_0x03e6:
                r0 = r18
                r1 = r7
                r2 = r13
                r3 = r14
                r5 = r15
                r0.onMediaButton(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x03f0:
                r10.enforceInterface(r11)
                java.lang.String r13 = r20.readString()
                int r14 = r20.readInt()
                int r15 = r20.readInt()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.media.session.ISessionControllerCallback r16 = android.media.session.ISessionControllerCallback.Stub.asInterface(r1)
                java.lang.String r17 = r20.readString()
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x041b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r6 = r1
                goto L_0x041c
            L_0x041b:
                r6 = r0
            L_0x041c:
                int r1 = r20.readInt()
                if (r1 == 0) goto L_0x042c
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
            L_0x042a:
                r7 = r0
                goto L_0x042d
            L_0x042c:
                goto L_0x042a
            L_0x042d:
                r0 = r18
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r0.onCommand(r1, r2, r3, r4, r5, r6, r7)
                return r12
            L_0x043a:
                r0 = r21
                r0.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ISessionCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISessionCallback {
            public static ISessionCallback sDefaultImpl;
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

            public void onCommand(String packageName, int pid, int uid, ISessionControllerCallback caller, String command, Bundle args, ResultReceiver cb) throws RemoteException {
                Bundle bundle = args;
                ResultReceiver resultReceiver = cb;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(pid);
                        } catch (Throwable th) {
                            th = th;
                            int i = uid;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(uid);
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                            _data.writeString(command);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (resultReceiver != null) {
                                _data.writeInt(1);
                                resultReceiver.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onCommand(packageName, pid, uid, caller, command, args, cb);
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i2 = pid;
                        int i3 = uid;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = packageName;
                    int i22 = pid;
                    int i32 = uid;
                    _data.recycle();
                    throw th;
                }
            }

            public void onMediaButton(String packageName, int pid, int uid, Intent mediaButtonIntent, int sequenceNumber, ResultReceiver cb) throws RemoteException {
                Intent intent = mediaButtonIntent;
                ResultReceiver resultReceiver = cb;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(pid);
                        } catch (Throwable th) {
                            th = th;
                            int i = uid;
                            int i2 = sequenceNumber;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(uid);
                            if (intent != null) {
                                _data.writeInt(1);
                                intent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(sequenceNumber);
                                if (resultReceiver != null) {
                                    _data.writeInt(1);
                                    resultReceiver.writeToParcel(_data, 0);
                                } else {
                                    _data.writeInt(0);
                                }
                                try {
                                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                        _data.recycle();
                                        return;
                                    }
                                    Stub.getDefaultImpl().onMediaButton(packageName, pid, uid, mediaButtonIntent, sequenceNumber, cb);
                                    _data.recycle();
                                } catch (Throwable th2) {
                                    th = th2;
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = sequenceNumber;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = pid;
                        int i4 = uid;
                        int i222 = sequenceNumber;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str = packageName;
                    int i32 = pid;
                    int i42 = uid;
                    int i2222 = sequenceNumber;
                    _data.recycle();
                    throw th;
                }
            }

            public void onMediaButtonFromController(String packageName, int pid, int uid, ISessionControllerCallback caller, Intent mediaButtonIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (mediaButtonIntent != null) {
                        _data.writeInt(1);
                        mediaButtonIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMediaButtonFromController(packageName, pid, uid, caller, mediaButtonIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPrepare(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPrepare(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPrepareFromMediaId(String packageName, int pid, int uid, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        String str = mediaId;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = uid;
                        String str2 = mediaId;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(uid);
                        _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        try {
                            _data.writeString(mediaId);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPrepareFromMediaId(packageName, pid, uid, caller, mediaId, extras);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str22 = mediaId;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str3 = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    String str222 = mediaId;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPrepareFromSearch(String packageName, int pid, int uid, ISessionControllerCallback caller, String query, Bundle extras) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        String str = query;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = uid;
                        String str2 = query;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(uid);
                        _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        try {
                            _data.writeString(query);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPrepareFromSearch(packageName, pid, uid, caller, query, extras);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str22 = query;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str3 = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    String str222 = query;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPrepareFromUri(String packageName, int pid, int uid, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
                Uri uri2 = uri;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(pid);
                        } catch (Throwable th) {
                            th = th;
                            int i = uid;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(uid);
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                            if (uri2 != null) {
                                _data.writeInt(1);
                                uri2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPrepareFromUri(packageName, pid, uid, caller, uri, extras);
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i2 = pid;
                        int i3 = uid;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str = packageName;
                    int i22 = pid;
                    int i32 = uid;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPlay(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPlay(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPlayFromMediaId(String packageName, int pid, int uid, ISessionControllerCallback caller, String mediaId, Bundle extras) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        String str = mediaId;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = uid;
                        String str2 = mediaId;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(uid);
                        _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        try {
                            _data.writeString(mediaId);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPlayFromMediaId(packageName, pid, uid, caller, mediaId, extras);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str22 = mediaId;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str3 = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    String str222 = mediaId;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPlayFromSearch(String packageName, int pid, int uid, ISessionControllerCallback caller, String query, Bundle extras) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        String str = query;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = uid;
                        String str2 = query;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(uid);
                        _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        try {
                            _data.writeString(query);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPlayFromSearch(packageName, pid, uid, caller, query, extras);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str22 = query;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str3 = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    String str222 = query;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPlayFromUri(String packageName, int pid, int uid, ISessionControllerCallback caller, Uri uri, Bundle extras) throws RemoteException {
                Uri uri2 = uri;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeInt(pid);
                        } catch (Throwable th) {
                            th = th;
                            int i = uid;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(uid);
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                            if (uri2 != null) {
                                _data.writeInt(1);
                                uri2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onPlayFromUri(packageName, pid, uid, caller, uri, extras);
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i2 = pid;
                        int i3 = uid;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str = packageName;
                    int i22 = pid;
                    int i32 = uid;
                    _data.recycle();
                    throw th;
                }
            }

            public void onSkipToTrack(String packageName, int pid, int uid, ISessionControllerCallback caller, long id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        long j = id;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                        try {
                            _data.writeInt(uid);
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                            try {
                                _data.writeLong(id);
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            long j2 = id;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onSkipToTrack(packageName, pid, uid, caller, id);
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i22 = uid;
                        long j22 = id;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    long j222 = id;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPause(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPause(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStop(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStop(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNext(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNext(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPrevious(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPrevious(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFastForward(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFastForward(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRewind(String packageName, int pid, int uid, ISessionControllerCallback caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRewind(packageName, pid, uid, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSeekTo(String packageName, int pid, int uid, ISessionControllerCallback caller, long pos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        long j = pos;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                        try {
                            _data.writeInt(uid);
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                            try {
                                _data.writeLong(pos);
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            long j2 = pos;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onSeekTo(packageName, pid, uid, caller, pos);
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i22 = uid;
                        long j22 = pos;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    long j222 = pos;
                    _data.recycle();
                    throw th;
                }
            }

            public void onRate(String packageName, int pid, int uid, ISessionControllerCallback caller, Rating rating) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    if (rating != null) {
                        _data.writeInt(1);
                        rating.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRate(packageName, pid, uid, caller, rating);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSetPlaybackSpeed(String packageName, int pid, int uid, ISessionControllerCallback caller, float speed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeFloat(speed);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSetPlaybackSpeed(packageName, pid, uid, caller, speed);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCustomAction(String packageName, int pid, int uid, ISessionControllerCallback caller, String action, Bundle args) throws RemoteException {
                Bundle bundle = args;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        int i = pid;
                        int i2 = uid;
                        String str = action;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(pid);
                    } catch (Throwable th2) {
                        th = th2;
                        int i22 = uid;
                        String str2 = action;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(uid);
                        _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        try {
                            _data.writeString(action);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onCustomAction(packageName, pid, uid, caller, action, args);
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str22 = action;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str3 = packageName;
                    int i3 = pid;
                    int i222 = uid;
                    String str222 = action;
                    _data.recycle();
                    throw th;
                }
            }

            public void onAdjustVolume(String packageName, int pid, int uid, ISessionControllerCallback caller, int direction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeInt(direction);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAdjustVolume(packageName, pid, uid, caller, direction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSetVolumeTo(String packageName, int pid, int uid, ISessionControllerCallback caller, int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                    _data.writeInt(value);
                    if (this.mRemote.transact(24, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSetVolumeTo(packageName, pid, uid, caller, value);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISessionCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISessionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
