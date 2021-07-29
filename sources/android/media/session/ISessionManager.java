package android.media.session;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.media.IRemoteVolumeController;
import android.media.Session2Token;
import android.media.session.ISession;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import java.util.List;

public interface ISessionManager extends IInterface {
    void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) throws RemoteException;

    void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) throws RemoteException;

    ISession createSession(String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) throws RemoteException;

    void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) throws RemoteException;

    void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) throws RemoteException;

    boolean dispatchMediaKeyEventToSessionAsSystemService(String str, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException;

    void dispatchVolumeKeyEvent(String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) throws RemoteException;

    void dispatchVolumeKeyEventToSessionAsSystemService(String str, String str2, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException;

    ParceledListSlice getSession2Tokens(int i) throws RemoteException;

    List<MediaSession.Token> getSessions(ComponentName componentName, int i) throws RemoteException;

    boolean isGlobalPriorityActive() throws RemoteException;

    boolean isTrusted(String str, int i, int i2) throws RemoteException;

    void notifySession2Created(Session2Token session2Token) throws RemoteException;

    void registerRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException;

    void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException;

    void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException;

    void setCallback(ICallback iCallback) throws RemoteException;

    void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException;

    void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException;

    void unregisterRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException;

    public static class Default implements ISessionManager {
        public ISession createSession(String packageName, ISessionCallback sessionCb, String tag, Bundle sessionInfo, int userId) throws RemoteException {
            return null;
        }

        public void notifySession2Created(Session2Token sessionToken) throws RemoteException {
        }

        public List<MediaSession.Token> getSessions(ComponentName compName, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getSession2Tokens(int userId) throws RemoteException {
            return null;
        }

        public void dispatchMediaKeyEvent(String packageName, boolean asSystemService, KeyEvent keyEvent, boolean needWakeLock) throws RemoteException {
        }

        public boolean dispatchMediaKeyEventToSessionAsSystemService(String packageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
            return false;
        }

        public void dispatchVolumeKeyEvent(String packageName, String opPackageName, boolean asSystemService, KeyEvent keyEvent, int stream, boolean musicOnly) throws RemoteException {
        }

        public void dispatchVolumeKeyEventToSessionAsSystemService(String packageName, String opPackageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
        }

        public void dispatchAdjustVolume(String packageName, String opPackageName, int suggestedStream, int delta, int flags) throws RemoteException {
        }

        public void addSessionsListener(IActiveSessionsListener listener, ComponentName compName, int userId) throws RemoteException {
        }

        public void removeSessionsListener(IActiveSessionsListener listener) throws RemoteException {
        }

        public void addSession2TokensListener(ISession2TokensListener listener, int userId) throws RemoteException {
        }

        public void removeSession2TokensListener(ISession2TokensListener listener) throws RemoteException {
        }

        public void registerRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
        }

        public void unregisterRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
        }

        public boolean isGlobalPriorityActive() throws RemoteException {
            return false;
        }

        public void setCallback(ICallback callback) throws RemoteException {
        }

        public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener listener) throws RemoteException {
        }

        public void setOnMediaKeyListener(IOnMediaKeyListener listener) throws RemoteException {
        }

        public boolean isTrusted(String controllerPackageName, int controllerPid, int controllerUid) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISessionManager {
        private static final String DESCRIPTOR = "android.media.session.ISessionManager";
        static final int TRANSACTION_addSession2TokensListener = 12;
        static final int TRANSACTION_addSessionsListener = 10;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_dispatchAdjustVolume = 9;
        static final int TRANSACTION_dispatchMediaKeyEvent = 5;
        static final int TRANSACTION_dispatchMediaKeyEventToSessionAsSystemService = 6;
        static final int TRANSACTION_dispatchVolumeKeyEvent = 7;
        static final int TRANSACTION_dispatchVolumeKeyEventToSessionAsSystemService = 8;
        static final int TRANSACTION_getSession2Tokens = 4;
        static final int TRANSACTION_getSessions = 3;
        static final int TRANSACTION_isGlobalPriorityActive = 16;
        static final int TRANSACTION_isTrusted = 20;
        static final int TRANSACTION_notifySession2Created = 2;
        static final int TRANSACTION_registerRemoteVolumeController = 14;
        static final int TRANSACTION_removeSession2TokensListener = 13;
        static final int TRANSACTION_removeSessionsListener = 11;
        static final int TRANSACTION_setCallback = 17;
        static final int TRANSACTION_setOnMediaKeyListener = 19;
        static final int TRANSACTION_setOnVolumeKeyLongPressListener = 18;
        static final int TRANSACTION_unregisterRemoteVolumeController = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISessionManager)) {
                return new Proxy(obj);
            }
            return (ISessionManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createSession";
                case 2:
                    return "notifySession2Created";
                case 3:
                    return "getSessions";
                case 4:
                    return "getSession2Tokens";
                case 5:
                    return "dispatchMediaKeyEvent";
                case 6:
                    return "dispatchMediaKeyEventToSessionAsSystemService";
                case 7:
                    return "dispatchVolumeKeyEvent";
                case 8:
                    return "dispatchVolumeKeyEventToSessionAsSystemService";
                case 9:
                    return "dispatchAdjustVolume";
                case 10:
                    return "addSessionsListener";
                case 11:
                    return "removeSessionsListener";
                case 12:
                    return "addSession2TokensListener";
                case 13:
                    return "removeSession2TokensListener";
                case 14:
                    return "registerRemoteVolumeController";
                case 15:
                    return "unregisterRemoteVolumeController";
                case 16:
                    return "isGlobalPriorityActive";
                case 17:
                    return "setCallback";
                case 18:
                    return "setOnVolumeKeyLongPressListener";
                case 19:
                    return "setOnMediaKeyListener";
                case 20:
                    return "isTrusted";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.media.Session2Token} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v18, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v22, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v25, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v26, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v27, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v28, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v29, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v30, resolved type: android.os.IBinder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v31, resolved type: android.os.IBinder} */
        /* JADX WARNING: type inference failed for: r6v5, types: [android.media.Session2Token] */
        /* JADX WARNING: type inference failed for: r6v8, types: [android.content.ComponentName] */
        /* JADX WARNING: type inference failed for: r6v11, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r6v14, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r6v20, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r6v24, types: [android.content.ComponentName] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r18
                r9 = r19
                r10 = r20
                java.lang.String r11 = "android.media.session.ISessionManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x0297
                r0 = 0
                r6 = 0
                switch(r8) {
                    case 1: goto L_0x0256;
                    case 2: goto L_0x023a;
                    case 3: goto L_0x0216;
                    case 4: goto L_0x01fb;
                    case 5: goto L_0x01ca;
                    case 6: goto L_0x0196;
                    case 7: goto L_0x0158;
                    case 8: goto L_0x0124;
                    case 9: goto L_0x00fe;
                    case 10: goto L_0x00d6;
                    case 11: goto L_0x00c4;
                    case 12: goto L_0x00ae;
                    case 13: goto L_0x009c;
                    case 14: goto L_0x008a;
                    case 15: goto L_0x0078;
                    case 16: goto L_0x006a;
                    case 17: goto L_0x0058;
                    case 18: goto L_0x0046;
                    case 19: goto L_0x0034;
                    case 20: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                int r2 = r19.readInt()
                boolean r3 = r7.isTrusted(r0, r1, r2)
                r20.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x0034:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.IOnMediaKeyListener r0 = android.media.session.IOnMediaKeyListener.Stub.asInterface(r0)
                r7.setOnMediaKeyListener(r0)
                r20.writeNoException()
                return r12
            L_0x0046:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.IOnVolumeKeyLongPressListener r0 = android.media.session.IOnVolumeKeyLongPressListener.Stub.asInterface(r0)
                r7.setOnVolumeKeyLongPressListener(r0)
                r20.writeNoException()
                return r12
            L_0x0058:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ICallback r0 = android.media.session.ICallback.Stub.asInterface(r0)
                r7.setCallback(r0)
                r20.writeNoException()
                return r12
            L_0x006a:
                r9.enforceInterface(r11)
                boolean r0 = r17.isGlobalPriorityActive()
                r20.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0078:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.IRemoteVolumeController r0 = android.media.IRemoteVolumeController.Stub.asInterface(r0)
                r7.unregisterRemoteVolumeController(r0)
                r20.writeNoException()
                return r12
            L_0x008a:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.IRemoteVolumeController r0 = android.media.IRemoteVolumeController.Stub.asInterface(r0)
                r7.registerRemoteVolumeController(r0)
                r20.writeNoException()
                return r12
            L_0x009c:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISession2TokensListener r0 = android.media.session.ISession2TokensListener.Stub.asInterface(r0)
                r7.removeSession2TokensListener(r0)
                r20.writeNoException()
                return r12
            L_0x00ae:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISession2TokensListener r0 = android.media.session.ISession2TokensListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                r7.addSession2TokensListener(r0, r1)
                r20.writeNoException()
                return r12
            L_0x00c4:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.IActiveSessionsListener r0 = android.media.session.IActiveSessionsListener.Stub.asInterface(r0)
                r7.removeSessionsListener(r0)
                r20.writeNoException()
                return r12
            L_0x00d6:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.IActiveSessionsListener r0 = android.media.session.IActiveSessionsListener.Stub.asInterface(r0)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x00f1
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r6 = r1
                android.content.ComponentName r6 = (android.content.ComponentName) r6
                goto L_0x00f2
            L_0x00f1:
            L_0x00f2:
                r1 = r6
                int r2 = r19.readInt()
                r7.addSessionsListener(r0, r1, r2)
                r20.writeNoException()
                return r12
            L_0x00fe:
                r9.enforceInterface(r11)
                java.lang.String r6 = r19.readString()
                java.lang.String r13 = r19.readString()
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r6
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.dispatchAdjustVolume(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r12
            L_0x0124:
                r9.enforceInterface(r11)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x013e
                android.os.Parcelable$Creator<android.media.session.MediaSession$Token> r2 = android.media.session.MediaSession.Token.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.media.session.MediaSession$Token r2 = (android.media.session.MediaSession.Token) r2
                goto L_0x013f
            L_0x013e:
                r2 = r6
            L_0x013f:
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x014f
                android.os.Parcelable$Creator<android.view.KeyEvent> r3 = android.view.KeyEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                r6 = r3
                android.view.KeyEvent r6 = (android.view.KeyEvent) r6
                goto L_0x0150
            L_0x014f:
            L_0x0150:
                r3 = r6
                r7.dispatchVolumeKeyEventToSessionAsSystemService(r0, r1, r2, r3)
                r20.writeNoException()
                return r12
            L_0x0158:
                r9.enforceInterface(r11)
                java.lang.String r13 = r19.readString()
                java.lang.String r14 = r19.readString()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x016b
                r3 = r12
                goto L_0x016c
            L_0x016b:
                r3 = r0
            L_0x016c:
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x017c
                android.os.Parcelable$Creator<android.view.KeyEvent> r1 = android.view.KeyEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.KeyEvent r1 = (android.view.KeyEvent) r1
                r4 = r1
                goto L_0x017d
            L_0x017c:
                r4 = r6
            L_0x017d:
                int r15 = r19.readInt()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0189
                r6 = r12
                goto L_0x018a
            L_0x0189:
                r6 = r0
            L_0x018a:
                r0 = r17
                r1 = r13
                r2 = r14
                r5 = r15
                r0.dispatchVolumeKeyEvent(r1, r2, r3, r4, r5, r6)
                r20.writeNoException()
                return r12
            L_0x0196:
                r9.enforceInterface(r11)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x01ac
                android.os.Parcelable$Creator<android.media.session.MediaSession$Token> r1 = android.media.session.MediaSession.Token.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.media.session.MediaSession$Token r1 = (android.media.session.MediaSession.Token) r1
                goto L_0x01ad
            L_0x01ac:
                r1 = r6
            L_0x01ad:
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01bd
                android.os.Parcelable$Creator<android.view.KeyEvent> r2 = android.view.KeyEvent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                r6 = r2
                android.view.KeyEvent r6 = (android.view.KeyEvent) r6
                goto L_0x01be
            L_0x01bd:
            L_0x01be:
                r2 = r6
                boolean r3 = r7.dispatchMediaKeyEventToSessionAsSystemService(r0, r1, r2)
                r20.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x01ca:
                r9.enforceInterface(r11)
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01d9
                r2 = r12
                goto L_0x01da
            L_0x01d9:
                r2 = r0
            L_0x01da:
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x01ea
                android.os.Parcelable$Creator<android.view.KeyEvent> r3 = android.view.KeyEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                r6 = r3
                android.view.KeyEvent r6 = (android.view.KeyEvent) r6
                goto L_0x01eb
            L_0x01ea:
            L_0x01eb:
                r3 = r6
                int r4 = r19.readInt()
                if (r4 == 0) goto L_0x01f4
                r0 = r12
            L_0x01f4:
                r7.dispatchMediaKeyEvent(r1, r2, r3, r0)
                r20.writeNoException()
                return r12
            L_0x01fb:
                r9.enforceInterface(r11)
                int r1 = r19.readInt()
                android.content.pm.ParceledListSlice r2 = r7.getSession2Tokens(r1)
                r20.writeNoException()
                if (r2 == 0) goto L_0x0212
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x0215
            L_0x0212:
                r10.writeInt(r0)
            L_0x0215:
                return r12
            L_0x0216:
                r9.enforceInterface(r11)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0229
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r6 = r0
                android.content.ComponentName r6 = (android.content.ComponentName) r6
                goto L_0x022a
            L_0x0229:
            L_0x022a:
                r0 = r6
                int r1 = r19.readInt()
                java.util.List r2 = r7.getSessions(r0, r1)
                r20.writeNoException()
                r10.writeTypedList(r2)
                return r12
            L_0x023a:
                r9.enforceInterface(r11)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x024d
                android.os.Parcelable$Creator r0 = android.media.Session2Token.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r6 = r0
                android.media.Session2Token r6 = (android.media.Session2Token) r6
                goto L_0x024e
            L_0x024d:
            L_0x024e:
                r0 = r6
                r7.notifySession2Created(r0)
                r20.writeNoException()
                return r12
            L_0x0256:
                r9.enforceInterface(r11)
                java.lang.String r13 = r19.readString()
                android.os.IBinder r0 = r19.readStrongBinder()
                android.media.session.ISessionCallback r14 = android.media.session.ISessionCallback.Stub.asInterface(r0)
                java.lang.String r15 = r19.readString()
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0279
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r4 = r0
                goto L_0x027a
            L_0x0279:
                r4 = r6
            L_0x027a:
                int r16 = r19.readInt()
                r0 = r17
                r1 = r13
                r2 = r14
                r3 = r15
                r5 = r16
                android.media.session.ISession r0 = r0.createSession(r1, r2, r3, r4, r5)
                r20.writeNoException()
                if (r0 == 0) goto L_0x0293
                android.os.IBinder r6 = r0.asBinder()
            L_0x0293:
                r10.writeStrongBinder(r6)
                return r12
            L_0x0297:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.session.ISessionManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISessionManager {
            public static ISessionManager sDefaultImpl;
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

            public ISession createSession(String packageName, ISessionCallback sessionCb, String tag, Bundle sessionInfo, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(sessionCb != null ? sessionCb.asBinder() : null);
                    _data.writeString(tag);
                    if (sessionInfo != null) {
                        _data.writeInt(1);
                        sessionInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createSession(packageName, sessionCb, tag, sessionInfo, userId);
                    }
                    _reply.readException();
                    ISession _result = ISession.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifySession2Created(Session2Token sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifySession2Created(sessionToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<MediaSession.Token> getSessions(ComponentName compName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (compName != null) {
                        _data.writeInt(1);
                        compName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSessions(compName, userId);
                    }
                    _reply.readException();
                    List<MediaSession.Token> _result = _reply.createTypedArrayList(MediaSession.Token.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getSession2Tokens(int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSession2Tokens(userId);
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

            public void dispatchMediaKeyEvent(String packageName, boolean asSystemService, KeyEvent keyEvent, boolean needWakeLock) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(asSystemService);
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(needWakeLock);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispatchMediaKeyEvent(packageName, asSystemService, keyEvent, needWakeLock);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean dispatchMediaKeyEventToSessionAsSystemService(String packageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dispatchMediaKeyEventToSessionAsSystemService(packageName, sessionToken, keyEvent);
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

            public void dispatchVolumeKeyEvent(String packageName, String opPackageName, boolean asSystemService, KeyEvent keyEvent, int stream, boolean musicOnly) throws RemoteException {
                KeyEvent keyEvent2 = keyEvent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(packageName);
                        try {
                            _data.writeString(opPackageName);
                        } catch (Throwable th) {
                            th = th;
                            boolean z = asSystemService;
                            int i = stream;
                            boolean z2 = musicOnly;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(asSystemService ? 1 : 0);
                            if (keyEvent2 != null) {
                                _data.writeInt(1);
                                keyEvent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeInt(stream);
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z22 = musicOnly;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(musicOnly ? 1 : 0);
                                if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().dispatchVolumeKeyEvent(packageName, opPackageName, asSystemService, keyEvent, stream, musicOnly);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i2 = stream;
                            boolean z222 = musicOnly;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str = opPackageName;
                        boolean z3 = asSystemService;
                        int i22 = stream;
                        boolean z2222 = musicOnly;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str2 = packageName;
                    String str3 = opPackageName;
                    boolean z32 = asSystemService;
                    int i222 = stream;
                    boolean z22222 = musicOnly;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void dispatchVolumeKeyEventToSessionAsSystemService(String packageName, String opPackageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    if (sessionToken != null) {
                        _data.writeInt(1);
                        sessionToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispatchVolumeKeyEventToSessionAsSystemService(packageName, opPackageName, sessionToken, keyEvent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dispatchAdjustVolume(String packageName, String opPackageName, int suggestedStream, int delta, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    _data.writeInt(suggestedStream);
                    _data.writeInt(delta);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispatchAdjustVolume(packageName, opPackageName, suggestedStream, delta, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addSessionsListener(IActiveSessionsListener listener, ComponentName compName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (compName != null) {
                        _data.writeInt(1);
                        compName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addSessionsListener(listener, compName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeSessionsListener(IActiveSessionsListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeSessionsListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addSession2TokensListener(ISession2TokensListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addSession2TokensListener(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeSession2TokensListener(ISession2TokensListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeSession2TokensListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rvc != null ? rvc.asBinder() : null);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerRemoteVolumeController(rvc);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rvc != null ? rvc.asBinder() : null);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterRemoteVolumeController(rvc);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isGlobalPriorityActive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGlobalPriorityActive();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCallback(ICallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOnVolumeKeyLongPressListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOnMediaKeyListener(IOnMediaKeyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOnMediaKeyListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTrusted(String controllerPackageName, int controllerPid, int controllerUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(controllerPackageName);
                    _data.writeInt(controllerPid);
                    _data.writeInt(controllerUid);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTrusted(controllerPackageName, controllerPid, controllerUid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISessionManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISessionManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
