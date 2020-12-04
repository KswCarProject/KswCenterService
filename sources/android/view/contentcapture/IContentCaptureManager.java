package android.view.contentcapture;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.os.IResultReceiver;

public interface IContentCaptureManager extends IInterface {
    void finishSession(int i) throws RemoteException;

    void getContentCaptureConditions(String str, IResultReceiver iResultReceiver) throws RemoteException;

    void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException;

    void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException;

    void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException;

    void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException;

    void startSession(IBinder iBinder, ComponentName componentName, int i, int i2, IResultReceiver iResultReceiver) throws RemoteException;

    public static class Default implements IContentCaptureManager {
        public void startSession(IBinder activityToken, ComponentName componentName, int sessionId, int flags, IResultReceiver result) throws RemoteException {
        }

        public void finishSession(int sessionId) throws RemoteException {
        }

        public void getServiceComponentName(IResultReceiver result) throws RemoteException {
        }

        public void removeData(DataRemovalRequest request) throws RemoteException {
        }

        public void isContentCaptureFeatureEnabled(IResultReceiver result) throws RemoteException {
        }

        public void getServiceSettingsActivity(IResultReceiver result) throws RemoteException {
        }

        public void getContentCaptureConditions(String packageName, IResultReceiver result) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContentCaptureManager {
        private static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureManager";
        static final int TRANSACTION_finishSession = 2;
        static final int TRANSACTION_getContentCaptureConditions = 7;
        static final int TRANSACTION_getServiceComponentName = 3;
        static final int TRANSACTION_getServiceSettingsActivity = 6;
        static final int TRANSACTION_isContentCaptureFeatureEnabled = 5;
        static final int TRANSACTION_removeData = 4;
        static final int TRANSACTION_startSession = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentCaptureManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContentCaptureManager)) {
                return new Proxy(obj);
            }
            return (IContentCaptureManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startSession";
                case 2:
                    return "finishSession";
                case 3:
                    return "getServiceComponentName";
                case 4:
                    return "removeData";
                case 5:
                    return "isContentCaptureFeatureEnabled";
                case 6:
                    return "getServiceSettingsActivity";
                case 7:
                    return "getContentCaptureConditions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.view.contentcapture.DataRemovalRequest} */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.content.ComponentName] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.view.contentcapture.IContentCaptureManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x00a9
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x0078;
                    case 2: goto L_0x006d;
                    case 3: goto L_0x005e;
                    case 4: goto L_0x0047;
                    case 5: goto L_0x0038;
                    case 6: goto L_0x0029;
                    case 7: goto L_0x0016;
                    default: goto L_0x0011;
                }
            L_0x0011:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0016:
                r8.enforceInterface(r9)
                java.lang.String r0 = r17.readString()
                android.os.IBinder r1 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r1 = com.android.internal.os.IResultReceiver.Stub.asInterface(r1)
                r15.getContentCaptureConditions(r0, r1)
                return r10
            L_0x0029:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r15.getServiceSettingsActivity(r0)
                return r10
            L_0x0038:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r15.isContentCaptureFeatureEnabled(r0)
                return r10
            L_0x0047:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0059
                android.os.Parcelable$Creator<android.view.contentcapture.DataRemovalRequest> r0 = android.view.contentcapture.DataRemovalRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.contentcapture.DataRemovalRequest r0 = (android.view.contentcapture.DataRemovalRequest) r0
                goto L_0x005a
            L_0x0059:
            L_0x005a:
                r15.removeData(r0)
                return r10
            L_0x005e:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r15.getServiceComponentName(r0)
                return r10
            L_0x006d:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                r15.finishSession(r0)
                return r10
            L_0x0078:
                r8.enforceInterface(r9)
                android.os.IBinder r11 = r17.readStrongBinder()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x008f
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
            L_0x008d:
                r2 = r0
                goto L_0x0090
            L_0x008f:
                goto L_0x008d
            L_0x0090:
                int r12 = r17.readInt()
                int r13 = r17.readInt()
                android.os.IBinder r0 = r17.readStrongBinder()
                com.android.internal.os.IResultReceiver r14 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r0 = r15
                r1 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.startSession(r1, r2, r3, r4, r5)
                return r10
            L_0x00a9:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.contentcapture.IContentCaptureManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContentCaptureManager {
            public static IContentCaptureManager sDefaultImpl;
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

            public void startSession(IBinder activityToken, ComponentName componentName, int sessionId, int flags, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sessionId);
                    _data.writeInt(flags);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startSession(activityToken, componentName, sessionId, flags, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finishSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finishSession(sessionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getServiceComponentName(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getServiceComponentName(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeData(DataRemovalRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeData(request);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isContentCaptureFeatureEnabled(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isContentCaptureFeatureEnabled(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getServiceSettingsActivity(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getServiceSettingsActivity(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getContentCaptureConditions(String packageName, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getContentCaptureConditions(packageName, result);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentCaptureManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContentCaptureManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
