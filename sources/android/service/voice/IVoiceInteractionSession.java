package android.service.voice;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ThreadedRenderer;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;

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

    public static class Default implements IVoiceInteractionSession {
        public void show(Bundle sessionArgs, int flags, IVoiceInteractionSessionShowCallback showCallback) throws RemoteException {
        }

        public void hide() throws RemoteException {
        }

        public void handleAssist(int taskId, IBinder activityId, Bundle assistData, AssistStructure structure, AssistContent content, int index, int count) throws RemoteException {
        }

        public void handleScreenshot(Bitmap screenshot) throws RemoteException {
        }

        public void taskStarted(Intent intent, int taskId) throws RemoteException {
        }

        public void taskFinished(Intent intent, int taskId) throws RemoteException {
        }

        public void closeSystemDialogs() throws RemoteException {
        }

        public void onLockscreenShown() throws RemoteException {
        }

        public void destroy() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IVoiceInteractionSession)) {
                return new Proxy(obj);
            }
            return (IVoiceInteractionSession) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.graphics.Bitmap} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v7 */
        /* JADX WARNING: type inference failed for: r0v16, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v20, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v25 */
        /* JADX WARNING: type inference failed for: r0v26 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: type inference failed for: r0v28 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r8 = r17
                r9 = r18
                r10 = r19
                java.lang.String r11 = "android.service.voice.IVoiceInteractionSession"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r9 == r0) goto L_0x00f4
                r0 = 0
                switch(r9) {
                    case 1: goto L_0x00d1;
                    case 2: goto L_0x00ca;
                    case 3: goto L_0x0079;
                    case 4: goto L_0x0062;
                    case 5: goto L_0x0047;
                    case 6: goto L_0x002c;
                    case 7: goto L_0x0025;
                    case 8: goto L_0x001e;
                    case 9: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x0017:
                r10.enforceInterface(r11)
                r17.destroy()
                return r12
            L_0x001e:
                r10.enforceInterface(r11)
                r17.onLockscreenShown()
                return r12
            L_0x0025:
                r10.enforceInterface(r11)
                r17.closeSystemDialogs()
                return r12
            L_0x002c:
                r10.enforceInterface(r11)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                int r1 = r19.readInt()
                r8.taskFinished(r0, r1)
                return r12
            L_0x0047:
                r10.enforceInterface(r11)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0059
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x005a
            L_0x0059:
            L_0x005a:
                int r1 = r19.readInt()
                r8.taskStarted(r0, r1)
                return r12
            L_0x0062:
                r10.enforceInterface(r11)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0074
                android.os.Parcelable$Creator<android.graphics.Bitmap> r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
                goto L_0x0075
            L_0x0074:
            L_0x0075:
                r8.handleScreenshot(r0)
                return r12
            L_0x0079:
                r10.enforceInterface(r11)
                int r13 = r19.readInt()
                android.os.IBinder r14 = r19.readStrongBinder()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r3 = r1
                goto L_0x0095
            L_0x0094:
                r3 = r0
            L_0x0095:
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x00a5
                android.os.Parcelable$Creator<android.app.assist.AssistStructure> r1 = android.app.assist.AssistStructure.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.app.assist.AssistStructure r1 = (android.app.assist.AssistStructure) r1
                r4 = r1
                goto L_0x00a6
            L_0x00a5:
                r4 = r0
            L_0x00a6:
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x00b6
                android.os.Parcelable$Creator<android.app.assist.AssistContent> r0 = android.app.assist.AssistContent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.app.assist.AssistContent r0 = (android.app.assist.AssistContent) r0
            L_0x00b4:
                r5 = r0
                goto L_0x00b7
            L_0x00b6:
                goto L_0x00b4
            L_0x00b7:
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r13
                r2 = r14
                r6 = r15
                r7 = r16
                r0.handleAssist(r1, r2, r3, r4, r5, r6, r7)
                return r12
            L_0x00ca:
                r10.enforceInterface(r11)
                r17.hide()
                return r12
            L_0x00d1:
                r10.enforceInterface(r11)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x00e3
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x00e4
            L_0x00e3:
            L_0x00e4:
                int r1 = r19.readInt()
                android.os.IBinder r2 = r19.readStrongBinder()
                com.android.internal.app.IVoiceInteractionSessionShowCallback r2 = com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.asInterface(r2)
                r8.show(r0, r1, r2)
                return r12
            L_0x00f4:
                r0 = r20
                r0.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.voice.IVoiceInteractionSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IVoiceInteractionSession {
            public static IVoiceInteractionSession sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().show(sessionArgs, flags, showCallback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void hide() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hide();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void handleAssist(int taskId, IBinder activityId, Bundle assistData, AssistStructure structure, AssistContent content, int index, int count) throws RemoteException {
                Bundle bundle = assistData;
                AssistStructure assistStructure = structure;
                AssistContent assistContent = content;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(taskId);
                        try {
                            _data.writeStrongBinder(activityId);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (assistStructure != null) {
                                _data.writeInt(1);
                                assistStructure.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (assistContent != null) {
                                _data.writeInt(1);
                                assistContent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            int i = index;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        IBinder iBinder = activityId;
                        int i2 = index;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(index);
                        _data.writeInt(count);
                        if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().handleAssist(taskId, activityId, assistData, structure, content, index, count);
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i3 = taskId;
                    IBinder iBinder2 = activityId;
                    int i22 = index;
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().handleScreenshot(screenshot);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().taskStarted(intent, taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().taskFinished(intent, taskId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void closeSystemDialogs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().closeSystemDialogs();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onLockscreenShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLockscreenShown();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void destroy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().destroy();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVoiceInteractionSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IVoiceInteractionSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
