package android.service.voice;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Bitmap;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.ThreadedRenderer;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;

/* loaded from: classes3.dex */
public interface IVoiceInteractionSession extends IInterface {
    void closeSystemDialogs() throws RemoteException;

    void destroy() throws RemoteException;

    void handleAssist(int i, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int i2, int i3) throws RemoteException;

    void handleScreenshot(Bitmap bitmap) throws RemoteException;

    void hide() throws RemoteException;

    void onLockscreenShown() throws RemoteException;

    void show(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException;

    void taskFinished(Intent intent, int i) throws RemoteException;

    void taskStarted(Intent intent, int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IVoiceInteractionSession {
        @Override // android.service.voice.IVoiceInteractionSession
        public void show(Bundle sessionArgs, int flags, IVoiceInteractionSessionShowCallback showCallback) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void hide() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleAssist(int taskId, IBinder activityId, Bundle assistData, AssistStructure structure, AssistContent content, int index, int count) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleScreenshot(Bitmap screenshot) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskStarted(Intent intent, int taskId) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskFinished(Intent intent, int taskId) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void closeSystemDialogs() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void onLockscreenShown() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void destroy() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IVoiceInteractionSession {
        private static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionSession";
        static final int TRANSACTION_closeSystemDialogs = 7;
        static final int TRANSACTION_destroy = 9;
        static final int TRANSACTION_handleAssist = 3;
        static final int TRANSACTION_handleScreenshot = 4;
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_onLockscreenShown = 8;
        static final int TRANSACTION_show = 1;
        static final int TRANSACTION_taskFinished = 6;
        static final int TRANSACTION_taskStarted = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVoiceInteractionSession)) {
                return (IVoiceInteractionSession) iin;
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
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 2:
                    return "hide";
                case 3:
                    return "handleAssist";
                case 4:
                    return "handleScreenshot";
                case 5:
                    return "taskStarted";
                case 6:
                    return "taskFinished";
                case 7:
                    return "closeSystemDialogs";
                case 8:
                    return "onLockscreenShown";
                case 9:
                    return "destroy";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _arg0 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg1 = data.readInt();
                    IVoiceInteractionSessionShowCallback _arg2 = IVoiceInteractionSessionShowCallback.Stub.asInterface(data.readStrongBinder());
                    show(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    hide();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    IBinder _arg12 = data.readStrongBinder();
                    Bundle _arg22 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    AssistStructure _arg3 = data.readInt() != 0 ? AssistStructure.CREATOR.createFromParcel(data) : null;
                    AssistContent _arg4 = data.readInt() != 0 ? AssistContent.CREATOR.createFromParcel(data) : null;
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    handleAssist(_arg02, _arg12, _arg22, _arg3, _arg4, _arg5, _arg6);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    Bitmap _arg03 = data.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(data) : null;
                    handleScreenshot(_arg03);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    Intent _arg04 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    int _arg13 = data.readInt();
                    taskStarted(_arg04, _arg13);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    Intent _arg05 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    int _arg14 = data.readInt();
                    taskFinished(_arg05, _arg14);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    closeSystemDialogs();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    onLockscreenShown();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    destroy();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IVoiceInteractionSession {
            public static IVoiceInteractionSession sDefaultImpl;
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

            @Override // android.service.voice.IVoiceInteractionSession
            public void show(Bundle sessionArgs, int flags, IVoiceInteractionSessionShowCallback showCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionArgs != null) {
                        _data.writeInt(1);
                        sessionArgs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    _data.writeStrongBinder(showCallback != null ? showCallback.asBinder() : null);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().show(sessionArgs, flags, showCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void hide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hide();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleAssist(int taskId, IBinder activityId, Bundle assistData, AssistStructure structure, AssistContent content, int index, int count) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(taskId);
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongBinder(activityId);
                        if (assistData != null) {
                            _data.writeInt(1);
                            assistData.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (structure != null) {
                            _data.writeInt(1);
                            structure.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (content != null) {
                            _data.writeInt(1);
                            content.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(index);
                        _data.writeInt(count);
                        boolean _status = this.mRemote.transact(3, _data, null, 1);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().handleAssist(taskId, activityId, assistData, structure, content, index, count);
                            _data.recycle();
                            return;
                        }
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleScreenshot(Bitmap screenshot) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (screenshot != null) {
                        _data.writeInt(1);
                        screenshot.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleScreenshot(screenshot);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskStarted(Intent intent, int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().taskStarted(intent, taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskFinished(Intent intent, int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(taskId);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().taskFinished(intent, taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void closeSystemDialogs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSystemDialogs();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void onLockscreenShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLockscreenShown();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void destroy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroy();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVoiceInteractionSession impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IVoiceInteractionSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
