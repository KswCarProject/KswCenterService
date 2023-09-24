package android.media.session;

import android.content.ComponentName;
import android.content.p002pm.ParceledListSlice;
import android.media.IRemoteVolumeController;
import android.media.Session2Token;
import android.media.session.IActiveSessionsListener;
import android.media.session.ICallback;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionCallback;
import android.media.session.MediaSession;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.KeyEvent;
import java.util.List;

/* loaded from: classes3.dex */
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

    /* loaded from: classes3.dex */
    public static class Default implements ISessionManager {
        @Override // android.media.session.ISessionManager
        public ISession createSession(String packageName, ISessionCallback sessionCb, String tag, Bundle sessionInfo, int userId) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public void notifySession2Created(Session2Token sessionToken) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public List<MediaSession.Token> getSessions(ComponentName compName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public ParceledListSlice getSession2Tokens(int userId) throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchMediaKeyEvent(String packageName, boolean asSystemService, KeyEvent keyEvent, boolean needWakeLock) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean dispatchMediaKeyEventToSessionAsSystemService(String packageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEvent(String packageName, String opPackageName, boolean asSystemService, KeyEvent keyEvent, int stream, boolean musicOnly) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchVolumeKeyEventToSessionAsSystemService(String packageName, String opPackageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void dispatchAdjustVolume(String packageName, String opPackageName, int suggestedStream, int delta, int flags) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSessionsListener(IActiveSessionsListener listener, ComponentName compName, int userId) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSessionsListener(IActiveSessionsListener listener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void addSession2TokensListener(ISession2TokensListener listener, int userId) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void removeSession2TokensListener(ISession2TokensListener listener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void registerRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void unregisterRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean isGlobalPriorityActive() throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionManager
        public void setCallback(ICallback callback) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener listener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public void setOnMediaKeyListener(IOnMediaKeyListener listener) throws RemoteException {
        }

        @Override // android.media.session.ISessionManager
        public boolean isTrusted(String controllerPackageName, int controllerPid, int controllerUid) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
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
            if (iin != null && (iin instanceof ISessionManager)) {
                return (ISessionManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            MediaSession.Token _arg1;
            MediaSession.Token _arg2;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    ISessionCallback _arg12 = ISessionCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg22 = data.readString();
                    Bundle _arg3 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg4 = data.readInt();
                    ISession _result = createSession(_arg0, _arg12, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    Session2Token _arg02 = data.readInt() != 0 ? (Session2Token) Session2Token.CREATOR.createFromParcel(data) : null;
                    notifySession2Created(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    ComponentName _arg03 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    int _arg13 = data.readInt();
                    List<MediaSession.Token> _result2 = getSessions(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    ParceledListSlice _result3 = getSession2Tokens(_arg04);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    boolean _arg14 = data.readInt() != 0;
                    KeyEvent _arg23 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    KeyEvent _arg24 = _arg23;
                    boolean _arg32 = data.readInt() != 0;
                    dispatchMediaKeyEvent(_arg05, _arg14, _arg24, _arg32);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg06 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = MediaSession.Token.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    KeyEvent _arg25 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    boolean dispatchMediaKeyEventToSessionAsSystemService = dispatchMediaKeyEventToSessionAsSystemService(_arg06, _arg1, _arg25);
                    reply.writeNoException();
                    reply.writeInt(dispatchMediaKeyEventToSessionAsSystemService ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg07 = data.readString();
                    String _arg15 = data.readString();
                    boolean _arg26 = data.readInt() != 0;
                    KeyEvent _arg33 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    int _arg42 = data.readInt();
                    boolean _arg5 = data.readInt() != 0;
                    dispatchVolumeKeyEvent(_arg07, _arg15, _arg26, _arg33, _arg42, _arg5);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    String _arg16 = data.readString();
                    if (data.readInt() != 0) {
                        _arg2 = MediaSession.Token.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    KeyEvent _arg34 = data.readInt() != 0 ? KeyEvent.CREATOR.createFromParcel(data) : null;
                    dispatchVolumeKeyEventToSessionAsSystemService(_arg08, _arg16, _arg2, _arg34);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    String _arg17 = data.readString();
                    int _arg27 = data.readInt();
                    int _arg35 = data.readInt();
                    int _arg43 = data.readInt();
                    dispatchAdjustVolume(_arg09, _arg17, _arg27, _arg35, _arg43);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IActiveSessionsListener _arg010 = IActiveSessionsListener.Stub.asInterface(data.readStrongBinder());
                    ComponentName _arg18 = data.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(data) : null;
                    int _arg28 = data.readInt();
                    addSessionsListener(_arg010, _arg18, _arg28);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    IActiveSessionsListener _arg011 = IActiveSessionsListener.Stub.asInterface(data.readStrongBinder());
                    removeSessionsListener(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    ISession2TokensListener _arg012 = ISession2TokensListener.Stub.asInterface(data.readStrongBinder());
                    int _arg19 = data.readInt();
                    addSession2TokensListener(_arg012, _arg19);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    ISession2TokensListener _arg013 = ISession2TokensListener.Stub.asInterface(data.readStrongBinder());
                    removeSession2TokensListener(_arg013);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    IRemoteVolumeController _arg014 = IRemoteVolumeController.Stub.asInterface(data.readStrongBinder());
                    registerRemoteVolumeController(_arg014);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    IRemoteVolumeController _arg015 = IRemoteVolumeController.Stub.asInterface(data.readStrongBinder());
                    unregisterRemoteVolumeController(_arg015);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isGlobalPriorityActive = isGlobalPriorityActive();
                    reply.writeNoException();
                    reply.writeInt(isGlobalPriorityActive ? 1 : 0);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    ICallback _arg016 = ICallback.Stub.asInterface(data.readStrongBinder());
                    setCallback(_arg016);
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    IOnVolumeKeyLongPressListener _arg017 = IOnVolumeKeyLongPressListener.Stub.asInterface(data.readStrongBinder());
                    setOnVolumeKeyLongPressListener(_arg017);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    IOnMediaKeyListener _arg018 = IOnMediaKeyListener.Stub.asInterface(data.readStrongBinder());
                    setOnMediaKeyListener(_arg018);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg019 = data.readString();
                    int _arg110 = data.readInt();
                    int _arg29 = data.readInt();
                    boolean isTrusted = isTrusted(_arg019, _arg110, _arg29);
                    reply.writeNoException();
                    reply.writeInt(isTrusted ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ISessionManager {
            public static ISessionManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createSession(packageName, sessionCb, tag, sessionInfo, userId);
                    }
                    _reply.readException();
                    ISession _result = ISession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySession2Created(sessionToken);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSessions(compName, userId);
                    }
                    _reply.readException();
                    List<MediaSession.Token> _result = _reply.createTypedArrayList(MediaSession.Token.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public ParceledListSlice getSession2Tokens(int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSession2Tokens(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchMediaKeyEvent(String packageName, boolean asSystemService, KeyEvent keyEvent, boolean needWakeLock) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(asSystemService ? 1 : 0);
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(needWakeLock ? 1 : 0);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchMediaKeyEvent(packageName, asSystemService, keyEvent, needWakeLock);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean dispatchMediaKeyEventToSessionAsSystemService(String packageName, MediaSession.Token sessionToken, KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
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
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dispatchMediaKeyEventToSessionAsSystemService(packageName, sessionToken, keyEvent);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void dispatchVolumeKeyEvent(String packageName, String opPackageName, boolean asSystemService, KeyEvent keyEvent, int stream, boolean musicOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(packageName);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(opPackageName);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(asSystemService ? 1 : 0);
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(stream);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(musicOnly ? 1 : 0);
                        boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dispatchVolumeKeyEvent(packageName, opPackageName, asSystemService, keyEvent, stream, musicOnly);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchVolumeKeyEventToSessionAsSystemService(packageName, opPackageName, sessionToken, keyEvent);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAdjustVolume(packageName, opPackageName, suggestedStream, delta, flags);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
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
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSessionsListener(listener, compName, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSessionsListener(IActiveSessionsListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSessionsListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void addSession2TokensListener(ISession2TokensListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSession2TokensListener(listener, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void removeSession2TokensListener(ISession2TokensListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSession2TokensListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void registerRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rvc != null ? rvc.asBinder() : null);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRemoteVolumeController(rvc);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void unregisterRemoteVolumeController(IRemoteVolumeController rvc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rvc != null ? rvc.asBinder() : null);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterRemoteVolumeController(rvc);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isGlobalPriorityActive() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGlobalPriorityActive();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setCallback(ICallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCallback(callback);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnVolumeKeyLongPressListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public void setOnMediaKeyListener(IOnMediaKeyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnMediaKeyListener(listener);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.session.ISessionManager
            public boolean isTrusted(String controllerPackageName, int controllerPid, int controllerUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(controllerPackageName);
                    _data.writeInt(controllerPid);
                    _data.writeInt(controllerUid);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTrusted(controllerPackageName, controllerPid, controllerUid);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISessionManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISessionManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
