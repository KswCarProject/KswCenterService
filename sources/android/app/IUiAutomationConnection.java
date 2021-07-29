package android.app;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.InputEvent;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;

public interface IUiAutomationConnection extends IInterface {
    void adoptShellPermissionIdentity(int i, String[] strArr) throws RemoteException;

    void clearWindowAnimationFrameStats() throws RemoteException;

    boolean clearWindowContentFrameStats(int i) throws RemoteException;

    void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException;

    void disconnect() throws RemoteException;

    void dropShellPermissionIdentity() throws RemoteException;

    void executeShellCommand(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException;

    WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException;

    WindowContentFrameStats getWindowContentFrameStats(int i) throws RemoteException;

    void grantRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean injectInputEvent(InputEvent inputEvent, boolean z) throws RemoteException;

    void revokeRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean setRotation(int i) throws RemoteException;

    void shutdown() throws RemoteException;

    void syncInputTransactions() throws RemoteException;

    Bitmap takeScreenshot(Rect rect, int i) throws RemoteException;

    public static class Default implements IUiAutomationConnection {
        public void connect(IAccessibilityServiceClient client, int flags) throws RemoteException {
        }

        public void disconnect() throws RemoteException {
        }

        public boolean injectInputEvent(InputEvent event, boolean sync) throws RemoteException {
            return false;
        }

        public void syncInputTransactions() throws RemoteException {
        }

        public boolean setRotation(int rotation) throws RemoteException {
            return false;
        }

        public Bitmap takeScreenshot(Rect crop, int rotation) throws RemoteException {
            return null;
        }

        public boolean clearWindowContentFrameStats(int windowId) throws RemoteException {
            return false;
        }

        public WindowContentFrameStats getWindowContentFrameStats(int windowId) throws RemoteException {
            return null;
        }

        public void clearWindowAnimationFrameStats() throws RemoteException {
        }

        public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
            return null;
        }

        public void executeShellCommand(String command, ParcelFileDescriptor sink, ParcelFileDescriptor source) throws RemoteException {
        }

        public void grantRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
        }

        public void revokeRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
        }

        public void adoptShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
        }

        public void dropShellPermissionIdentity() throws RemoteException {
        }

        public void shutdown() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUiAutomationConnection {
        private static final String DESCRIPTOR = "android.app.IUiAutomationConnection";
        static final int TRANSACTION_adoptShellPermissionIdentity = 14;
        static final int TRANSACTION_clearWindowAnimationFrameStats = 9;
        static final int TRANSACTION_clearWindowContentFrameStats = 7;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_dropShellPermissionIdentity = 15;
        static final int TRANSACTION_executeShellCommand = 11;
        static final int TRANSACTION_getWindowAnimationFrameStats = 10;
        static final int TRANSACTION_getWindowContentFrameStats = 8;
        static final int TRANSACTION_grantRuntimePermission = 12;
        static final int TRANSACTION_injectInputEvent = 3;
        static final int TRANSACTION_revokeRuntimePermission = 13;
        static final int TRANSACTION_setRotation = 5;
        static final int TRANSACTION_shutdown = 16;
        static final int TRANSACTION_syncInputTransactions = 4;
        static final int TRANSACTION_takeScreenshot = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUiAutomationConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUiAutomationConnection)) {
                return new Proxy(obj);
            }
            return (IUiAutomationConnection) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "connect";
                case 2:
                    return "disconnect";
                case 3:
                    return "injectInputEvent";
                case 4:
                    return "syncInputTransactions";
                case 5:
                    return "setRotation";
                case 6:
                    return "takeScreenshot";
                case 7:
                    return "clearWindowContentFrameStats";
                case 8:
                    return "getWindowContentFrameStats";
                case 9:
                    return "clearWindowAnimationFrameStats";
                case 10:
                    return "getWindowAnimationFrameStats";
                case 11:
                    return "executeShellCommand";
                case 12:
                    return "grantRuntimePermission";
                case 13:
                    return "revokeRuntimePermission";
                case 14:
                    return "adoptShellPermissionIdentity";
                case 15:
                    return "dropShellPermissionIdentity";
                case 16:
                    return "shutdown";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.graphics.Rect} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v2, types: [android.view.InputEvent] */
        /* JADX WARNING: type inference failed for: r3v14, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r3v21 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.app.IUiAutomationConnection"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x016a
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x0154;
                    case 2: goto L_0x014a;
                    case 3: goto L_0x0124;
                    case 4: goto L_0x011a;
                    case 5: goto L_0x0108;
                    case 6: goto L_0x00dd;
                    case 7: goto L_0x00cb;
                    case 8: goto L_0x00b0;
                    case 9: goto L_0x00a6;
                    case 10: goto L_0x008f;
                    case 11: goto L_0x0061;
                    case 12: goto L_0x004b;
                    case 13: goto L_0x0035;
                    case 14: goto L_0x0023;
                    case 15: goto L_0x0019;
                    case 16: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                r6.shutdown()
                return r2
            L_0x0019:
                r8.enforceInterface(r0)
                r6.dropShellPermissionIdentity()
                r9.writeNoException()
                return r2
            L_0x0023:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                java.lang.String[] r3 = r8.createStringArray()
                r6.adoptShellPermissionIdentity(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0035:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                r6.revokeRuntimePermission(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x004b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                r6.grantRuntimePermission(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x0061:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0077
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r4 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r8)
                android.os.ParcelFileDescriptor r4 = (android.os.ParcelFileDescriptor) r4
                goto L_0x0078
            L_0x0077:
                r4 = r3
            L_0x0078:
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0087
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r3 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x0088
            L_0x0087:
            L_0x0088:
                r6.executeShellCommand(r1, r4, r3)
                r9.writeNoException()
                return r2
            L_0x008f:
                r8.enforceInterface(r0)
                android.view.WindowAnimationFrameStats r3 = r6.getWindowAnimationFrameStats()
                r9.writeNoException()
                if (r3 == 0) goto L_0x00a2
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x00a5
            L_0x00a2:
                r9.writeInt(r1)
            L_0x00a5:
                return r2
            L_0x00a6:
                r8.enforceInterface(r0)
                r6.clearWindowAnimationFrameStats()
                r9.writeNoException()
                return r2
            L_0x00b0:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                android.view.WindowContentFrameStats r4 = r6.getWindowContentFrameStats(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x00c7
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x00ca
            L_0x00c7:
                r9.writeInt(r1)
            L_0x00ca:
                return r2
            L_0x00cb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                boolean r3 = r6.clearWindowContentFrameStats(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00dd:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00ef
                android.os.Parcelable$Creator<android.graphics.Rect> r3 = android.graphics.Rect.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.graphics.Rect r3 = (android.graphics.Rect) r3
                goto L_0x00f0
            L_0x00ef:
            L_0x00f0:
                int r4 = r8.readInt()
                android.graphics.Bitmap r5 = r6.takeScreenshot(r3, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x0104
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x0107
            L_0x0104:
                r9.writeInt(r1)
            L_0x0107:
                return r2
            L_0x0108:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                boolean r3 = r6.setRotation(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x011a:
                r8.enforceInterface(r0)
                r6.syncInputTransactions()
                r9.writeNoException()
                return r2
            L_0x0124:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0136
                android.os.Parcelable$Creator<android.view.InputEvent> r3 = android.view.InputEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.view.InputEvent r3 = (android.view.InputEvent) r3
                goto L_0x0137
            L_0x0136:
            L_0x0137:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x013f
                r1 = r2
            L_0x013f:
                boolean r4 = r6.injectInputEvent(r3, r1)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x014a:
                r8.enforceInterface(r0)
                r6.disconnect()
                r9.writeNoException()
                return r2
            L_0x0154:
                r8.enforceInterface(r0)
                android.os.IBinder r1 = r8.readStrongBinder()
                android.accessibilityservice.IAccessibilityServiceClient r1 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(r1)
                int r3 = r8.readInt()
                r6.connect(r1, r3)
                r9.writeNoException()
                return r2
            L_0x016a:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.IUiAutomationConnection.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IUiAutomationConnection {
            public static IUiAutomationConnection sDefaultImpl;
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

            public void connect(IAccessibilityServiceClient client, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().connect(client, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disconnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disconnect();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean injectInputEvent(InputEvent event, boolean sync) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sync);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().injectInputEvent(event, sync);
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

            public void syncInputTransactions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().syncInputTransactions();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRotation(int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    boolean z = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRotation(rotation);
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

            public Bitmap takeScreenshot(Rect crop, int rotation) throws RemoteException {
                Bitmap _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (crop != null) {
                        _data.writeInt(1);
                        crop.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(rotation);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().takeScreenshot(crop, rotation);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bitmap.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bitmap _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean clearWindowContentFrameStats(int windowId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().clearWindowContentFrameStats(windowId);
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

            public WindowContentFrameStats getWindowContentFrameStats(int windowId) throws RemoteException {
                WindowContentFrameStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowContentFrameStats(windowId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WindowContentFrameStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WindowContentFrameStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearWindowAnimationFrameStats() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearWindowAnimationFrameStats();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
                WindowAnimationFrameStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowAnimationFrameStats();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WindowAnimationFrameStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WindowAnimationFrameStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void executeShellCommand(String command, ParcelFileDescriptor sink, ParcelFileDescriptor source) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    if (sink != null) {
                        _data.writeInt(1);
                        sink.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (source != null) {
                        _data.writeInt(1);
                        source.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().executeShellCommand(command, sink, source);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void grantRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().grantRuntimePermission(packageName, permission, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void revokeRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().revokeRuntimePermission(packageName, permission, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void adoptShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(permissions);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().adoptShellPermissionIdentity(uid, permissions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dropShellPermissionIdentity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dropShellPermissionIdentity();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().shutdown();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUiAutomationConnection impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUiAutomationConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
