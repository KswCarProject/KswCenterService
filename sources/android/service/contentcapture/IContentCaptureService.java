package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.DataRemovalRequest;
import com.android.internal.os.IResultReceiver;

public interface IContentCaptureService extends IInterface {
    void onActivityEvent(ActivityEvent activityEvent) throws RemoteException;

    void onActivitySnapshot(int i, SnapshotData snapshotData) throws RemoteException;

    void onConnected(IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onSessionFinished(int i) throws RemoteException;

    void onSessionStarted(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) throws RemoteException;

    public static class Default implements IContentCaptureService {
        public void onConnected(IBinder callback, boolean verbose, boolean debug) throws RemoteException {
        }

        public void onDisconnected() throws RemoteException {
        }

        public void onSessionStarted(ContentCaptureContext context, int sessionId, int uid, IResultReceiver clientReceiver, int initialState) throws RemoteException {
        }

        public void onSessionFinished(int sessionId) throws RemoteException {
        }

        public void onActivitySnapshot(int sessionId, SnapshotData snapshotData) throws RemoteException {
        }

        public void onDataRemovalRequest(DataRemovalRequest request) throws RemoteException {
        }

        public void onActivityEvent(ActivityEvent event) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContentCaptureService {
        private static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureService";
        static final int TRANSACTION_onActivityEvent = 7;
        static final int TRANSACTION_onActivitySnapshot = 5;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDataRemovalRequest = 6;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onSessionFinished = 4;
        static final int TRANSACTION_onSessionStarted = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContentCaptureService)) {
                return new Proxy(obj);
            }
            return (IContentCaptureService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onSessionStarted";
                case 4:
                    return "onSessionFinished";
                case 5:
                    return "onActivitySnapshot";
                case 6:
                    return "onDataRemovalRequest";
                case 7:
                    return "onActivityEvent";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: android.service.contentcapture.SnapshotData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: android.service.contentcapture.ActivityEvent} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v4 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r0v25 */
        /* JADX WARNING: type inference failed for: r0v26 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.service.contentcapture.IContentCaptureService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x00bf
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x00a2;
                    case 2: goto L_0x009b;
                    case 3: goto L_0x006a;
                    case 4: goto L_0x005f;
                    case 5: goto L_0x0044;
                    case 6: goto L_0x002d;
                    case 7: goto L_0x0016;
                    default: goto L_0x0011;
                }
            L_0x0011:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0016:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0028
                android.os.Parcelable$Creator<android.service.contentcapture.ActivityEvent> r0 = android.service.contentcapture.ActivityEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.service.contentcapture.ActivityEvent r0 = (android.service.contentcapture.ActivityEvent) r0
                goto L_0x0029
            L_0x0028:
            L_0x0029:
                r15.onActivityEvent(r0)
                return r10
            L_0x002d:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x003f
                android.os.Parcelable$Creator<android.view.contentcapture.DataRemovalRequest> r0 = android.view.contentcapture.DataRemovalRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.contentcapture.DataRemovalRequest r0 = (android.view.contentcapture.DataRemovalRequest) r0
                goto L_0x0040
            L_0x003f:
            L_0x0040:
                r15.onDataRemovalRequest(r0)
                return r10
            L_0x0044:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<android.service.contentcapture.SnapshotData> r0 = android.service.contentcapture.SnapshotData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.service.contentcapture.SnapshotData r0 = (android.service.contentcapture.SnapshotData) r0
                goto L_0x005b
            L_0x005a:
            L_0x005b:
                r15.onActivitySnapshot(r1, r0)
                return r10
            L_0x005f:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                r15.onSessionFinished(r0)
                return r10
            L_0x006a:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x007d
                android.os.Parcelable$Creator<android.view.contentcapture.ContentCaptureContext> r0 = android.view.contentcapture.ContentCaptureContext.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.contentcapture.ContentCaptureContext r0 = (android.view.contentcapture.ContentCaptureContext) r0
            L_0x007b:
                r1 = r0
                goto L_0x007e
            L_0x007d:
                goto L_0x007b
            L_0x007e:
                int r11 = r17.readInt()
                int r12 = r17.readInt()
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r13 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                int r14 = r17.readInt()
                r0 = r15
                r2 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.onSessionStarted(r1, r2, r3, r4, r5)
                return r10
            L_0x009b:
                r8.enforceInterface(r9)
                r15.onDisconnected()
                return r10
            L_0x00a2:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                int r1 = r17.readInt()
                r2 = 0
                if (r1 == 0) goto L_0x00b2
                r1 = r10
                goto L_0x00b3
            L_0x00b2:
                r1 = r2
            L_0x00b3:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x00bb
                r2 = r10
            L_0x00bb:
                r15.onConnected(r0, r1, r2)
                return r10
            L_0x00bf:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.contentcapture.IContentCaptureService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContentCaptureService {
            public static IContentCaptureService sDefaultImpl;
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

            public void onConnected(IBinder callback, boolean verbose, boolean debug) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    _data.writeInt(verbose);
                    _data.writeInt(debug);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnected(callback, verbose, debug);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDisconnected();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionStarted(ContentCaptureContext context, int sessionId, int uid, IResultReceiver clientReceiver, int initialState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (context != null) {
                        _data.writeInt(1);
                        context.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sessionId);
                    _data.writeInt(uid);
                    _data.writeStrongBinder(clientReceiver != null ? clientReceiver.asBinder() : null);
                    _data.writeInt(initialState);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionStarted(context, sessionId, uid, clientReceiver, initialState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionFinished(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSessionFinished(sessionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivitySnapshot(int sessionId, SnapshotData snapshotData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (snapshotData != null) {
                        _data.writeInt(1);
                        snapshotData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivitySnapshot(sessionId, snapshotData);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDataRemovalRequest(DataRemovalRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDataRemovalRequest(request);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActivityEvent(ActivityEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActivityEvent(event);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentCaptureService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContentCaptureService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
