package android.companion;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ICompanionDeviceManager extends IInterface {
    void associate(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String str) throws RemoteException;

    void disassociate(String str, String str2) throws RemoteException;

    List<String> getAssociations(String str, int i) throws RemoteException;

    boolean hasNotificationAccess(ComponentName componentName) throws RemoteException;

    PendingIntent requestNotificationAccess(ComponentName componentName) throws RemoteException;

    void stopScan(AssociationRequest associationRequest, IFindDeviceCallback iFindDeviceCallback, String str) throws RemoteException;

    public static class Default implements ICompanionDeviceManager {
        public void associate(AssociationRequest request, IFindDeviceCallback callback, String callingPackage) throws RemoteException {
        }

        public void stopScan(AssociationRequest request, IFindDeviceCallback callback, String callingPackage) throws RemoteException {
        }

        public List<String> getAssociations(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        public void disassociate(String deviceMacAddress, String callingPackage) throws RemoteException {
        }

        public boolean hasNotificationAccess(ComponentName component) throws RemoteException {
            return false;
        }

        public PendingIntent requestNotificationAccess(ComponentName component) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICompanionDeviceManager {
        private static final String DESCRIPTOR = "android.companion.ICompanionDeviceManager";
        static final int TRANSACTION_associate = 1;
        static final int TRANSACTION_disassociate = 4;
        static final int TRANSACTION_getAssociations = 3;
        static final int TRANSACTION_hasNotificationAccess = 5;
        static final int TRANSACTION_requestNotificationAccess = 6;
        static final int TRANSACTION_stopScan = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICompanionDeviceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICompanionDeviceManager)) {
                return new Proxy(obj);
            }
            return (ICompanionDeviceManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "associate";
                case 2:
                    return "stopScan";
                case 3:
                    return "getAssociations";
                case 4:
                    return "disassociate";
                case 5:
                    return "hasNotificationAccess";
                case 6:
                    return "requestNotificationAccess";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.companion.AssociationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.companion.AssociationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.companion.ICompanionDeviceManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x00cb
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x00a5;
                    case 2: goto L_0x007f;
                    case 3: goto L_0x0069;
                    case 4: goto L_0x0057;
                    case 5: goto L_0x0039;
                    case 6: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                android.app.PendingIntent r3 = r5.requestNotificationAccess(r1)
                r8.writeNoException()
                if (r3 == 0) goto L_0x0034
                r8.writeInt(r2)
                r3.writeToParcel(r8, r2)
                goto L_0x0038
            L_0x0034:
                r4 = 0
                r8.writeInt(r4)
            L_0x0038:
                return r2
            L_0x0039:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x004b
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x004c
            L_0x004b:
            L_0x004c:
                boolean r3 = r5.hasNotificationAccess(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0057:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                java.lang.String r3 = r7.readString()
                r5.disassociate(r1, r3)
                r8.writeNoException()
                return r2
            L_0x0069:
                r7.enforceInterface(r0)
                java.lang.String r1 = r7.readString()
                int r3 = r7.readInt()
                java.util.List r4 = r5.getAssociations(r1, r3)
                r8.writeNoException()
                r8.writeStringList(r4)
                return r2
            L_0x007f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0091
                android.os.Parcelable$Creator<android.companion.AssociationRequest> r1 = android.companion.AssociationRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.companion.AssociationRequest r1 = (android.companion.AssociationRequest) r1
                goto L_0x0092
            L_0x0091:
            L_0x0092:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.companion.IFindDeviceCallback r3 = android.companion.IFindDeviceCallback.Stub.asInterface(r3)
                java.lang.String r4 = r7.readString()
                r5.stopScan(r1, r3, r4)
                r8.writeNoException()
                return r2
            L_0x00a5:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00b7
                android.os.Parcelable$Creator<android.companion.AssociationRequest> r1 = android.companion.AssociationRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.companion.AssociationRequest r1 = (android.companion.AssociationRequest) r1
                goto L_0x00b8
            L_0x00b7:
            L_0x00b8:
                android.os.IBinder r3 = r7.readStrongBinder()
                android.companion.IFindDeviceCallback r3 = android.companion.IFindDeviceCallback.Stub.asInterface(r3)
                java.lang.String r4 = r7.readString()
                r5.associate(r1, r3, r4)
                r8.writeNoException()
                return r2
            L_0x00cb:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.companion.ICompanionDeviceManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ICompanionDeviceManager {
            public static ICompanionDeviceManager sDefaultImpl;
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

            public void associate(AssociationRequest request, IFindDeviceCallback callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().associate(request, callback, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopScan(AssociationRequest request, IFindDeviceCallback callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopScan(request, callback, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getAssociations(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAssociations(callingPackage, userId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disassociate(String deviceMacAddress, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceMacAddress);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disassociate(deviceMacAddress, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasNotificationAccess(ComponentName component) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasNotificationAccess(component);
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

            public PendingIntent requestNotificationAccess(ComponentName component) throws RemoteException {
                PendingIntent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (component != null) {
                        _data.writeInt(1);
                        component.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestNotificationAccess(component);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PendingIntent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PendingIntent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICompanionDeviceManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICompanionDeviceManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
