package android.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IWindow;
import java.util.List;

public interface IAccessibilityManager extends IInterface {
    int addAccessibilityInteractionConnection(IWindow iWindow, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException;

    long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException;

    String getAccessibilityShortcutService() throws RemoteException;

    int getAccessibilityWindowId(IBinder iBinder) throws RemoteException;

    @UnsupportedAppUsage
    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException;

    List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException;

    long getRecommendedTimeoutMillis() throws RemoteException;

    IBinder getWindowToken(int i, int i2) throws RemoteException;

    void interrupt(int i) throws RemoteException;

    void notifyAccessibilityButtonClicked(int i) throws RemoteException;

    void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException;

    void performAccessibilityShortcut() throws RemoteException;

    void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i) throws RemoteException;

    void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException;

    void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException;

    boolean sendFingerprintGesture(int i) throws RemoteException;

    void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException;

    void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName componentName, boolean z) throws RemoteException;

    void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException;

    public static class Default implements IAccessibilityManager {
        public void interrupt(int userId) throws RemoteException {
        }

        public void sendAccessibilityEvent(AccessibilityEvent uiEvent, int userId) throws RemoteException {
        }

        public long addClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
            return 0;
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int userId) throws RemoteException {
            return null;
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType, int userId) throws RemoteException {
            return null;
        }

        public int addAccessibilityInteractionConnection(IWindow windowToken, IAccessibilityInteractionConnection connection, String packageName, int userId) throws RemoteException {
            return 0;
        }

        public void removeAccessibilityInteractionConnection(IWindow windowToken) throws RemoteException {
        }

        public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) throws RemoteException {
        }

        public void registerUiTestAutomationService(IBinder owner, IAccessibilityServiceClient client, AccessibilityServiceInfo info, int flags) throws RemoteException {
        }

        public void unregisterUiTestAutomationService(IAccessibilityServiceClient client) throws RemoteException {
        }

        public void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName service, boolean touchExplorationEnabled) throws RemoteException {
        }

        public IBinder getWindowToken(int windowId, int userId) throws RemoteException {
            return null;
        }

        public void notifyAccessibilityButtonClicked(int displayId) throws RemoteException {
        }

        public void notifyAccessibilityButtonVisibilityChanged(boolean available) throws RemoteException {
        }

        public void performAccessibilityShortcut() throws RemoteException {
        }

        public String getAccessibilityShortcutService() throws RemoteException {
            return null;
        }

        public boolean sendFingerprintGesture(int gestureKeyCode) throws RemoteException {
            return false;
        }

        public int getAccessibilityWindowId(IBinder windowToken) throws RemoteException {
            return 0;
        }

        public long getRecommendedTimeoutMillis() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityManager {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManager";
        static final int TRANSACTION_addAccessibilityInteractionConnection = 6;
        static final int TRANSACTION_addClient = 3;
        static final int TRANSACTION_getAccessibilityShortcutService = 16;
        static final int TRANSACTION_getAccessibilityWindowId = 18;
        static final int TRANSACTION_getEnabledAccessibilityServiceList = 5;
        static final int TRANSACTION_getInstalledAccessibilityServiceList = 4;
        static final int TRANSACTION_getRecommendedTimeoutMillis = 19;
        static final int TRANSACTION_getWindowToken = 12;
        static final int TRANSACTION_interrupt = 1;
        static final int TRANSACTION_notifyAccessibilityButtonClicked = 13;
        static final int TRANSACTION_notifyAccessibilityButtonVisibilityChanged = 14;
        static final int TRANSACTION_performAccessibilityShortcut = 15;
        static final int TRANSACTION_registerUiTestAutomationService = 9;
        static final int TRANSACTION_removeAccessibilityInteractionConnection = 7;
        static final int TRANSACTION_sendAccessibilityEvent = 2;
        static final int TRANSACTION_sendFingerprintGesture = 17;
        static final int TRANSACTION_setPictureInPictureActionReplacingConnection = 8;
        static final int TRANSACTION_temporaryEnableAccessibilityStateUntilKeyguardRemoved = 11;
        static final int TRANSACTION_unregisterUiTestAutomationService = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccessibilityManager)) {
                return new Proxy(obj);
            }
            return (IAccessibilityManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "interrupt";
                case 2:
                    return "sendAccessibilityEvent";
                case 3:
                    return "addClient";
                case 4:
                    return "getInstalledAccessibilityServiceList";
                case 5:
                    return "getEnabledAccessibilityServiceList";
                case 6:
                    return "addAccessibilityInteractionConnection";
                case 7:
                    return "removeAccessibilityInteractionConnection";
                case 8:
                    return "setPictureInPictureActionReplacingConnection";
                case 9:
                    return "registerUiTestAutomationService";
                case 10:
                    return "unregisterUiTestAutomationService";
                case 11:
                    return "temporaryEnableAccessibilityStateUntilKeyguardRemoved";
                case 12:
                    return "getWindowToken";
                case 13:
                    return "notifyAccessibilityButtonClicked";
                case 14:
                    return "notifyAccessibilityButtonVisibilityChanged";
                case 15:
                    return "performAccessibilityShortcut";
                case 16:
                    return "getAccessibilityShortcutService";
                case 17:
                    return "sendFingerprintGesture";
                case 18:
                    return "getAccessibilityWindowId";
                case 19:
                    return "getRecommendedTimeoutMillis";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.accessibilityservice.AccessibilityServiceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: type inference failed for: r3v24 */
        /* JADX WARNING: type inference failed for: r3v25 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.view.accessibility.IAccessibilityManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x01a4
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x0199;
                    case 2: goto L_0x017c;
                    case 3: goto L_0x0162;
                    case 4: goto L_0x0150;
                    case 5: goto L_0x013a;
                    case 6: goto L_0x0114;
                    case 7: goto L_0x0102;
                    case 8: goto L_0x00f0;
                    case 9: goto L_0x00c6;
                    case 10: goto L_0x00b4;
                    case 11: goto L_0x0092;
                    case 12: goto L_0x007c;
                    case 13: goto L_0x006e;
                    case 14: goto L_0x005c;
                    case 15: goto L_0x0052;
                    case 16: goto L_0x0044;
                    case 17: goto L_0x0032;
                    case 18: goto L_0x0020;
                    case 19: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                long r3 = r7.getRecommendedTimeoutMillis()
                r10.writeNoException()
                r10.writeLong(r3)
                return r2
            L_0x0020:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                int r3 = r7.getAccessibilityWindowId(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0032:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                boolean r3 = r7.sendFingerprintGesture(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0044:
                r9.enforceInterface(r0)
                java.lang.String r1 = r7.getAccessibilityShortcutService()
                r10.writeNoException()
                r10.writeString(r1)
                return r2
            L_0x0052:
                r9.enforceInterface(r0)
                r7.performAccessibilityShortcut()
                r10.writeNoException()
                return r2
            L_0x005c:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0067
                r1 = r2
            L_0x0067:
                r7.notifyAccessibilityButtonVisibilityChanged(r1)
                r10.writeNoException()
                return r2
            L_0x006e:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.notifyAccessibilityButtonClicked(r1)
                r10.writeNoException()
                return r2
            L_0x007c:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                android.os.IBinder r4 = r7.getWindowToken(r1, r3)
                r10.writeNoException()
                r10.writeStrongBinder(r4)
                return r2
            L_0x0092:
                r9.enforceInterface(r0)
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00a4
                android.os.Parcelable$Creator<android.content.ComponentName> r3 = android.content.ComponentName.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                goto L_0x00a5
            L_0x00a4:
            L_0x00a5:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00ad
                r1 = r2
            L_0x00ad:
                r7.temporaryEnableAccessibilityStateUntilKeyguardRemoved(r3, r1)
                r10.writeNoException()
                return r2
            L_0x00b4:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.accessibilityservice.IAccessibilityServiceClient r1 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(r1)
                r7.unregisterUiTestAutomationService(r1)
                r10.writeNoException()
                return r2
            L_0x00c6:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.os.IBinder r4 = r9.readStrongBinder()
                android.accessibilityservice.IAccessibilityServiceClient r4 = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x00e4
                android.os.Parcelable$Creator<android.accessibilityservice.AccessibilityServiceInfo> r3 = android.accessibilityservice.AccessibilityServiceInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.accessibilityservice.AccessibilityServiceInfo r3 = (android.accessibilityservice.AccessibilityServiceInfo) r3
                goto L_0x00e5
            L_0x00e4:
            L_0x00e5:
                int r5 = r9.readInt()
                r7.registerUiTestAutomationService(r1, r4, r3, r5)
                r10.writeNoException()
                return r2
            L_0x00f0:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnection r1 = android.view.accessibility.IAccessibilityInteractionConnection.Stub.asInterface(r1)
                r7.setPictureInPictureActionReplacingConnection(r1)
                r10.writeNoException()
                return r2
            L_0x0102:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                r7.removeAccessibilityInteractionConnection(r1)
                r10.writeNoException()
                return r2
            L_0x0114:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                android.os.IBinder r3 = r9.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnection r3 = android.view.accessibility.IAccessibilityInteractionConnection.Stub.asInterface(r3)
                java.lang.String r4 = r9.readString()
                int r5 = r9.readInt()
                int r6 = r7.addAccessibilityInteractionConnection(r1, r3, r4, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x013a:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                java.util.List r4 = r7.getEnabledAccessibilityServiceList(r1, r3)
                r10.writeNoException()
                r10.writeTypedList(r4)
                return r2
            L_0x0150:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                java.util.List r3 = r7.getInstalledAccessibilityServiceList(r1)
                r10.writeNoException()
                r10.writeTypedList(r3)
                return r2
            L_0x0162:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.view.accessibility.IAccessibilityManagerClient r1 = android.view.accessibility.IAccessibilityManagerClient.Stub.asInterface(r1)
                int r3 = r9.readInt()
                long r4 = r7.addClient(r1, r3)
                r10.writeNoException()
                r10.writeLong(r4)
                return r2
            L_0x017c:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x018f
                android.os.Parcelable$Creator<android.view.accessibility.AccessibilityEvent> r1 = android.view.accessibility.AccessibilityEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r3 = r1
                android.view.accessibility.AccessibilityEvent r3 = (android.view.accessibility.AccessibilityEvent) r3
                goto L_0x0190
            L_0x018f:
            L_0x0190:
                r1 = r3
                int r3 = r9.readInt()
                r7.sendAccessibilityEvent(r1, r3)
                return r2
            L_0x0199:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.interrupt(r1)
                return r2
            L_0x01a4:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.IAccessibilityManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccessibilityManager {
            public static IAccessibilityManager sDefaultImpl;
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

            public void interrupt(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interrupt(userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendAccessibilityEvent(AccessibilityEvent uiEvent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uiEvent != null) {
                        _data.writeInt(1);
                        uiEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendAccessibilityEvent(uiEvent, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public long addClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addClient(client, userId);
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

            public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledAccessibilityServiceList(userId);
                    }
                    _reply.readException();
                    List<AccessibilityServiceInfo> _result = _reply.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feedbackType);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEnabledAccessibilityServiceList(feedbackType, userId);
                    }
                    _reply.readException();
                    List<AccessibilityServiceInfo> _result = _reply.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int addAccessibilityInteractionConnection(IWindow windowToken, IAccessibilityInteractionConnection connection, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(windowToken != null ? windowToken.asBinder() : null);
                    if (connection != null) {
                        iBinder = connection.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAccessibilityInteractionConnection(windowToken, connection, packageName, userId);
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

            public void removeAccessibilityInteractionConnection(IWindow windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken != null ? windowToken.asBinder() : null);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAccessibilityInteractionConnection(windowToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPictureInPictureActionReplacingConnection(connection);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerUiTestAutomationService(IBinder owner, IAccessibilityServiceClient client, AccessibilityServiceInfo info, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(owner);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerUiTestAutomationService(owner, client, info, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterUiTestAutomationService(IAccessibilityServiceClient client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterUiTestAutomationService(client);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void temporaryEnableAccessibilityStateUntilKeyguardRemoved(ComponentName service, boolean touchExplorationEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (service != null) {
                        _data.writeInt(1);
                        service.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(touchExplorationEnabled);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().temporaryEnableAccessibilityStateUntilKeyguardRemoved(service, touchExplorationEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBinder getWindowToken(int windowId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowToken(windowId, userId);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyAccessibilityButtonClicked(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyAccessibilityButtonClicked(displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyAccessibilityButtonVisibilityChanged(boolean available) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(available);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyAccessibilityButtonVisibilityChanged(available);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void performAccessibilityShortcut() throws RemoteException {
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
                    Stub.getDefaultImpl().performAccessibilityShortcut();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getAccessibilityShortcutService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccessibilityShortcutService();
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

            public boolean sendFingerprintGesture(int gestureKeyCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(gestureKeyCode);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendFingerprintGesture(gestureKeyCode);
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

            public int getAccessibilityWindowId(IBinder windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccessibilityWindowId(windowToken);
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

            public long getRecommendedTimeoutMillis() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRecommendedTimeoutMillis();
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
        }

        public static boolean setDefaultImpl(IAccessibilityManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccessibilityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
