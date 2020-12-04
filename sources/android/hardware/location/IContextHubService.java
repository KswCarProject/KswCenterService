package android.hardware.location;

import android.app.PendingIntent;
import android.hardware.location.IContextHubClient;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IContextHubService extends IInterface {
    IContextHubClient createClient(int i, IContextHubClientCallback iContextHubClientCallback) throws RemoteException;

    IContextHubClient createPendingIntentClient(int i, PendingIntent pendingIntent, long j) throws RemoteException;

    void disableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    void enableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    int[] findNanoAppOnHub(int i, NanoAppFilter nanoAppFilter) throws RemoteException;

    int[] getContextHubHandles() throws RemoteException;

    ContextHubInfo getContextHubInfo(int i) throws RemoteException;

    List<ContextHubInfo> getContextHubs() throws RemoteException;

    NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws RemoteException;

    int loadNanoApp(int i, NanoApp nanoApp) throws RemoteException;

    void loadNanoAppOnHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException;

    void queryNanoApps(int i, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException;

    int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException;

    int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException;

    int unloadNanoApp(int i) throws RemoteException;

    void unloadNanoAppFromHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    public static class Default implements IContextHubService {
        public int registerCallback(IContextHubCallback callback) throws RemoteException {
            return 0;
        }

        public int[] getContextHubHandles() throws RemoteException {
            return null;
        }

        public ContextHubInfo getContextHubInfo(int contextHubHandle) throws RemoteException {
            return null;
        }

        public int loadNanoApp(int contextHubHandle, NanoApp nanoApp) throws RemoteException {
            return 0;
        }

        public int unloadNanoApp(int nanoAppHandle) throws RemoteException {
            return 0;
        }

        public NanoAppInstanceInfo getNanoAppInstanceInfo(int nanoAppHandle) throws RemoteException {
            return null;
        }

        public int[] findNanoAppOnHub(int contextHubHandle, NanoAppFilter filter) throws RemoteException {
            return null;
        }

        public int sendMessage(int contextHubHandle, int nanoAppHandle, ContextHubMessage msg) throws RemoteException {
            return 0;
        }

        public IContextHubClient createClient(int contextHubId, IContextHubClientCallback client) throws RemoteException {
            return null;
        }

        public IContextHubClient createPendingIntentClient(int contextHubId, PendingIntent pendingIntent, long nanoAppId) throws RemoteException {
            return null;
        }

        public List<ContextHubInfo> getContextHubs() throws RemoteException {
            return null;
        }

        public void loadNanoAppOnHub(int contextHubId, IContextHubTransactionCallback transactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
        }

        public void unloadNanoAppFromHub(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
        }

        public void enableNanoApp(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
        }

        public void disableNanoApp(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
        }

        public void queryNanoApps(int contextHubId, IContextHubTransactionCallback transactionCallback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContextHubService {
        private static final String DESCRIPTOR = "android.hardware.location.IContextHubService";
        static final int TRANSACTION_createClient = 9;
        static final int TRANSACTION_createPendingIntentClient = 10;
        static final int TRANSACTION_disableNanoApp = 15;
        static final int TRANSACTION_enableNanoApp = 14;
        static final int TRANSACTION_findNanoAppOnHub = 7;
        static final int TRANSACTION_getContextHubHandles = 2;
        static final int TRANSACTION_getContextHubInfo = 3;
        static final int TRANSACTION_getContextHubs = 11;
        static final int TRANSACTION_getNanoAppInstanceInfo = 6;
        static final int TRANSACTION_loadNanoApp = 4;
        static final int TRANSACTION_loadNanoAppOnHub = 12;
        static final int TRANSACTION_queryNanoApps = 16;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendMessage = 8;
        static final int TRANSACTION_unloadNanoApp = 5;
        static final int TRANSACTION_unloadNanoAppFromHub = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContextHubService)) {
                return new Proxy(obj);
            }
            return (IContextHubService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "getContextHubHandles";
                case 3:
                    return "getContextHubInfo";
                case 4:
                    return "loadNanoApp";
                case 5:
                    return "unloadNanoApp";
                case 6:
                    return "getNanoAppInstanceInfo";
                case 7:
                    return "findNanoAppOnHub";
                case 8:
                    return "sendMessage";
                case 9:
                    return "createClient";
                case 10:
                    return "createPendingIntentClient";
                case 11:
                    return "getContextHubs";
                case 12:
                    return "loadNanoAppOnHub";
                case 13:
                    return "unloadNanoAppFromHub";
                case 14:
                    return "enableNanoApp";
                case 15:
                    return "disableNanoApp";
                case 16:
                    return "queryNanoApps";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.hardware.location.NanoApp} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: android.hardware.location.NanoAppFilter} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: android.hardware.location.ContextHubMessage} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: android.hardware.location.NanoAppBinary} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v17, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v19, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r9, android.os.Parcel r10, android.os.Parcel r11, int r12) throws android.os.RemoteException {
            /*
                r8 = this;
                java.lang.String r0 = "android.hardware.location.IContextHubService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r9 == r1) goto L_0x01ce
                r1 = 0
                r3 = 0
                switch(r9) {
                    case 1: goto L_0x01b8;
                    case 2: goto L_0x01aa;
                    case 3: goto L_0x018f;
                    case 4: goto L_0x016d;
                    case 5: goto L_0x015b;
                    case 6: goto L_0x0140;
                    case 7: goto L_0x011e;
                    case 8: goto L_0x00f8;
                    case 9: goto L_0x00d7;
                    case 10: goto L_0x00aa;
                    case 11: goto L_0x009c;
                    case 12: goto L_0x0076;
                    case 13: goto L_0x005c;
                    case 14: goto L_0x0042;
                    case 15: goto L_0x0028;
                    case 16: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r9, r10, r11, r12)
                return r1
            L_0x0012:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r3 = r10.readStrongBinder()
                android.hardware.location.IContextHubTransactionCallback r3 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(r3)
                r8.queryNanoApps(r1, r3)
                r11.writeNoException()
                return r2
            L_0x0028:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r3 = r10.readStrongBinder()
                android.hardware.location.IContextHubTransactionCallback r3 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(r3)
                long r4 = r10.readLong()
                r8.disableNanoApp(r1, r3, r4)
                r11.writeNoException()
                return r2
            L_0x0042:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r3 = r10.readStrongBinder()
                android.hardware.location.IContextHubTransactionCallback r3 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(r3)
                long r4 = r10.readLong()
                r8.enableNanoApp(r1, r3, r4)
                r11.writeNoException()
                return r2
            L_0x005c:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r3 = r10.readStrongBinder()
                android.hardware.location.IContextHubTransactionCallback r3 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(r3)
                long r4 = r10.readLong()
                r8.unloadNanoAppFromHub(r1, r3, r4)
                r11.writeNoException()
                return r2
            L_0x0076:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r4 = r10.readStrongBinder()
                android.hardware.location.IContextHubTransactionCallback r4 = android.hardware.location.IContextHubTransactionCallback.Stub.asInterface(r4)
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.hardware.location.NanoAppBinary> r3 = android.hardware.location.NanoAppBinary.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.hardware.location.NanoAppBinary r3 = (android.hardware.location.NanoAppBinary) r3
                goto L_0x0095
            L_0x0094:
            L_0x0095:
                r8.loadNanoAppOnHub(r1, r4, r3)
                r11.writeNoException()
                return r2
            L_0x009c:
                r10.enforceInterface(r0)
                java.util.List r1 = r8.getContextHubs()
                r11.writeNoException()
                r11.writeTypedList(r1)
                return r2
            L_0x00aa:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r4 = r10.readInt()
                if (r4 == 0) goto L_0x00c0
                android.os.Parcelable$Creator<android.app.PendingIntent> r4 = android.app.PendingIntent.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r10)
                android.app.PendingIntent r4 = (android.app.PendingIntent) r4
                goto L_0x00c1
            L_0x00c0:
                r4 = r3
            L_0x00c1:
                long r5 = r10.readLong()
                android.hardware.location.IContextHubClient r7 = r8.createPendingIntentClient(r1, r4, r5)
                r11.writeNoException()
                if (r7 == 0) goto L_0x00d3
                android.os.IBinder r3 = r7.asBinder()
            L_0x00d3:
                r11.writeStrongBinder(r3)
                return r2
            L_0x00d7:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                android.os.IBinder r4 = r10.readStrongBinder()
                android.hardware.location.IContextHubClientCallback r4 = android.hardware.location.IContextHubClientCallback.Stub.asInterface(r4)
                android.hardware.location.IContextHubClient r5 = r8.createClient(r1, r4)
                r11.writeNoException()
                if (r5 == 0) goto L_0x00f4
                android.os.IBinder r3 = r5.asBinder()
            L_0x00f4:
                r11.writeStrongBinder(r3)
                return r2
            L_0x00f8:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r4 = r10.readInt()
                int r5 = r10.readInt()
                if (r5 == 0) goto L_0x0112
                android.os.Parcelable$Creator<android.hardware.location.ContextHubMessage> r3 = android.hardware.location.ContextHubMessage.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.hardware.location.ContextHubMessage r3 = (android.hardware.location.ContextHubMessage) r3
                goto L_0x0113
            L_0x0112:
            L_0x0113:
                int r5 = r8.sendMessage(r1, r4, r3)
                r11.writeNoException()
                r11.writeInt(r5)
                return r2
            L_0x011e:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r4 = r10.readInt()
                if (r4 == 0) goto L_0x0134
                android.os.Parcelable$Creator<android.hardware.location.NanoAppFilter> r3 = android.hardware.location.NanoAppFilter.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.hardware.location.NanoAppFilter r3 = (android.hardware.location.NanoAppFilter) r3
                goto L_0x0135
            L_0x0134:
            L_0x0135:
                int[] r4 = r8.findNanoAppOnHub(r1, r3)
                r11.writeNoException()
                r11.writeIntArray(r4)
                return r2
            L_0x0140:
                r10.enforceInterface(r0)
                int r3 = r10.readInt()
                android.hardware.location.NanoAppInstanceInfo r4 = r8.getNanoAppInstanceInfo(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x0157
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x015a
            L_0x0157:
                r11.writeInt(r1)
            L_0x015a:
                return r2
            L_0x015b:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r3 = r8.unloadNanoApp(r1)
                r11.writeNoException()
                r11.writeInt(r3)
                return r2
            L_0x016d:
                r10.enforceInterface(r0)
                int r1 = r10.readInt()
                int r4 = r10.readInt()
                if (r4 == 0) goto L_0x0183
                android.os.Parcelable$Creator<android.hardware.location.NanoApp> r3 = android.hardware.location.NanoApp.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.hardware.location.NanoApp r3 = (android.hardware.location.NanoApp) r3
                goto L_0x0184
            L_0x0183:
            L_0x0184:
                int r4 = r8.loadNanoApp(r1, r3)
                r11.writeNoException()
                r11.writeInt(r4)
                return r2
            L_0x018f:
                r10.enforceInterface(r0)
                int r3 = r10.readInt()
                android.hardware.location.ContextHubInfo r4 = r8.getContextHubInfo(r3)
                r11.writeNoException()
                if (r4 == 0) goto L_0x01a6
                r11.writeInt(r2)
                r4.writeToParcel(r11, r2)
                goto L_0x01a9
            L_0x01a6:
                r11.writeInt(r1)
            L_0x01a9:
                return r2
            L_0x01aa:
                r10.enforceInterface(r0)
                int[] r1 = r8.getContextHubHandles()
                r11.writeNoException()
                r11.writeIntArray(r1)
                return r2
            L_0x01b8:
                r10.enforceInterface(r0)
                android.os.IBinder r1 = r10.readStrongBinder()
                android.hardware.location.IContextHubCallback r1 = android.hardware.location.IContextHubCallback.Stub.asInterface(r1)
                int r3 = r8.registerCallback(r1)
                r11.writeNoException()
                r11.writeInt(r3)
                return r2
            L_0x01ce:
                r11.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.location.IContextHubService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContextHubService {
            public static IContextHubService sDefaultImpl;
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

            public int registerCallback(IContextHubCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerCallback(callback);
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

            public int[] getContextHubHandles() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContextHubHandles();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ContextHubInfo getContextHubInfo(int contextHubHandle) throws RemoteException {
                ContextHubInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubHandle);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContextHubInfo(contextHubHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ContextHubInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ContextHubInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int loadNanoApp(int contextHubHandle, NanoApp nanoApp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubHandle);
                    if (nanoApp != null) {
                        _data.writeInt(1);
                        nanoApp.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadNanoApp(contextHubHandle, nanoApp);
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

            public int unloadNanoApp(int nanoAppHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(nanoAppHandle);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unloadNanoApp(nanoAppHandle);
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

            public NanoAppInstanceInfo getNanoAppInstanceInfo(int nanoAppHandle) throws RemoteException {
                NanoAppInstanceInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(nanoAppHandle);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNanoAppInstanceInfo(nanoAppHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NanoAppInstanceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NanoAppInstanceInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] findNanoAppOnHub(int contextHubHandle, NanoAppFilter filter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubHandle);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().findNanoAppOnHub(contextHubHandle, filter);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int sendMessage(int contextHubHandle, int nanoAppHandle, ContextHubMessage msg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubHandle);
                    _data.writeInt(nanoAppHandle);
                    if (msg != null) {
                        _data.writeInt(1);
                        msg.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendMessage(contextHubHandle, nanoAppHandle, msg);
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

            public IContextHubClient createClient(int contextHubId, IContextHubClientCallback client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createClient(contextHubId, client);
                    }
                    _reply.readException();
                    IContextHubClient _result = IContextHubClient.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IContextHubClient createPendingIntentClient(int contextHubId, PendingIntent pendingIntent, long nanoAppId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    if (pendingIntent != null) {
                        _data.writeInt(1);
                        pendingIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(nanoAppId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createPendingIntentClient(contextHubId, pendingIntent, nanoAppId);
                    }
                    _reply.readException();
                    IContextHubClient _result = IContextHubClient.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ContextHubInfo> getContextHubs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContextHubs();
                    }
                    _reply.readException();
                    List<ContextHubInfo> _result = _reply.createTypedArrayList(ContextHubInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void loadNanoAppOnHub(int contextHubId, IContextHubTransactionCallback transactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(transactionCallback != null ? transactionCallback.asBinder() : null);
                    if (nanoAppBinary != null) {
                        _data.writeInt(1);
                        nanoAppBinary.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().loadNanoAppOnHub(contextHubId, transactionCallback, nanoAppBinary);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unloadNanoAppFromHub(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(transactionCallback != null ? transactionCallback.asBinder() : null);
                    _data.writeLong(nanoAppId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unloadNanoAppFromHub(contextHubId, transactionCallback, nanoAppId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableNanoApp(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(transactionCallback != null ? transactionCallback.asBinder() : null);
                    _data.writeLong(nanoAppId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableNanoApp(contextHubId, transactionCallback, nanoAppId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableNanoApp(int contextHubId, IContextHubTransactionCallback transactionCallback, long nanoAppId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(transactionCallback != null ? transactionCallback.asBinder() : null);
                    _data.writeLong(nanoAppId);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableNanoApp(contextHubId, transactionCallback, nanoAppId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void queryNanoApps(int contextHubId, IContextHubTransactionCallback transactionCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contextHubId);
                    _data.writeStrongBinder(transactionCallback != null ? transactionCallback.asBinder() : null);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().queryNanoApps(contextHubId, transactionCallback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContextHubService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContextHubService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
