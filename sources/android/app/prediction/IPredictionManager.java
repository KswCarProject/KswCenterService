package android.app.prediction;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPredictionManager extends IInterface {
    void createPredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException;

    void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException;

    void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    public static class Default implements IPredictionManager {
        public void createPredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) throws RemoteException {
        }

        public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) throws RemoteException {
        }

        public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) throws RemoteException {
        }

        public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) throws RemoteException {
        }

        public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
        }

        public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
        }

        public void requestPredictionUpdate(AppPredictionSessionId sessionId) throws RemoteException {
        }

        public void onDestroyPredictionSession(AppPredictionSessionId sessionId) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPredictionManager {
        private static final String DESCRIPTOR = "android.app.prediction.IPredictionManager";
        static final int TRANSACTION_createPredictionSession = 1;
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPredictionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPredictionManager)) {
                return new Proxy(obj);
            }
            return (IPredictionManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createPredictionSession";
                case 2:
                    return "notifyAppTargetEvent";
                case 3:
                    return "notifyLaunchLocationShown";
                case 4:
                    return "sortAppTargets";
                case 5:
                    return "registerPredictionUpdates";
                case 6:
                    return "unregisterPredictionUpdates";
                case 7:
                    return "requestPredictionUpdate";
                case 8:
                    return "onDestroyPredictionSession";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.app.prediction.AppPredictionSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.app.prediction.AppTargetEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.app.prediction.AppPredictionSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.app.prediction.AppPredictionSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.app.prediction.AppPredictionSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.app.prediction.AppPredictionSessionId} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.app.prediction.IPredictionManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x013d
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0113;
                    case 2: goto L_0x00e9;
                    case 3: goto L_0x00bb;
                    case 4: goto L_0x0089;
                    case 5: goto L_0x0067;
                    case 6: goto L_0x0045;
                    case 7: goto L_0x002b;
                    case 8: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r1 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r1 = (android.app.prediction.AppPredictionSessionId) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r6.onDestroyPredictionSession(r1)
                r9.writeNoException()
                return r2
            L_0x002b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x003d
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r1 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r1 = (android.app.prediction.AppPredictionSessionId) r1
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                r6.requestPredictionUpdate(r1)
                r9.writeNoException()
                return r2
            L_0x0045:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0057
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r1 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r1 = (android.app.prediction.AppPredictionSessionId) r1
                goto L_0x0058
            L_0x0057:
            L_0x0058:
                android.os.IBinder r3 = r8.readStrongBinder()
                android.app.prediction.IPredictionCallback r3 = android.app.prediction.IPredictionCallback.Stub.asInterface(r3)
                r6.unregisterPredictionUpdates(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0067:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0079
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r1 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r1 = (android.app.prediction.AppPredictionSessionId) r1
                goto L_0x007a
            L_0x0079:
            L_0x007a:
                android.os.IBinder r3 = r8.readStrongBinder()
                android.app.prediction.IPredictionCallback r3 = android.app.prediction.IPredictionCallback.Stub.asInterface(r3)
                r6.registerPredictionUpdates(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0089:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r3 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r3 = (android.app.prediction.AppPredictionSessionId) r3
                goto L_0x009c
            L_0x009b:
                r3 = r1
            L_0x009c:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00ab
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x00ac
            L_0x00ab:
            L_0x00ac:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.app.prediction.IPredictionCallback r4 = android.app.prediction.IPredictionCallback.Stub.asInterface(r4)
                r6.sortAppTargets(r3, r1, r4)
                r9.writeNoException()
                return r2
            L_0x00bb:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00cd
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r3 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r3 = (android.app.prediction.AppPredictionSessionId) r3
                goto L_0x00ce
            L_0x00cd:
                r3 = r1
            L_0x00ce:
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00e1
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x00e2
            L_0x00e1:
            L_0x00e2:
                r6.notifyLaunchLocationShown(r3, r4, r1)
                r9.writeNoException()
                return r2
            L_0x00e9:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x00fb
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r3 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r3 = (android.app.prediction.AppPredictionSessionId) r3
                goto L_0x00fc
            L_0x00fb:
                r3 = r1
            L_0x00fc:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x010b
                android.os.Parcelable$Creator<android.app.prediction.AppTargetEvent> r1 = android.app.prediction.AppTargetEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppTargetEvent r1 = (android.app.prediction.AppTargetEvent) r1
                goto L_0x010c
            L_0x010b:
            L_0x010c:
                r6.notifyAppTargetEvent(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0113:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0125
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionContext> r3 = android.app.prediction.AppPredictionContext.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.app.prediction.AppPredictionContext r3 = (android.app.prediction.AppPredictionContext) r3
                goto L_0x0126
            L_0x0125:
                r3 = r1
            L_0x0126:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0135
                android.os.Parcelable$Creator<android.app.prediction.AppPredictionSessionId> r1 = android.app.prediction.AppPredictionSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.prediction.AppPredictionSessionId r1 = (android.app.prediction.AppPredictionSessionId) r1
                goto L_0x0136
            L_0x0135:
            L_0x0136:
                r6.createPredictionSession(r3, r1)
                r9.writeNoException()
                return r2
            L_0x013d:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.prediction.IPredictionManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPredictionManager {
            public static IPredictionManager sDefaultImpl;
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

            public void createPredictionSession(AppPredictionContext context, AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (context != null) {
                        _data.writeInt(1);
                        context.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createPredictionSession(context, sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyAppTargetEvent(AppPredictionSessionId sessionId, AppTargetEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyAppTargetEvent(sessionId, event);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyLaunchLocationShown(AppPredictionSessionId sessionId, String launchLocation, ParceledListSlice targetIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(launchLocation);
                    if (targetIds != null) {
                        _data.writeInt(1);
                        targetIds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyLaunchLocationShown(sessionId, launchLocation, targetIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sortAppTargets(AppPredictionSessionId sessionId, ParceledListSlice targets, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (targets != null) {
                        _data.writeInt(1);
                        targets.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sortAppTargets(sessionId, targets, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerPredictionUpdates(sessionId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterPredictionUpdates(AppPredictionSessionId sessionId, IPredictionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterPredictionUpdates(sessionId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestPredictionUpdate(AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestPredictionUpdate(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onDestroyPredictionSession(AppPredictionSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onDestroyPredictionSession(sessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPredictionManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPredictionManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
