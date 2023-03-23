package android.app.role;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.telephony.IFinancialSmsCallback;
import java.util.List;

public interface IRoleManager extends IInterface {
    void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int i) throws RemoteException;

    void addRoleHolderAsUser(String str, String str2, int i, int i2, RemoteCallback remoteCallback) throws RemoteException;

    boolean addRoleHolderFromController(String str, String str2) throws RemoteException;

    void clearRoleHoldersAsUser(String str, int i, int i2, RemoteCallback remoteCallback) throws RemoteException;

    String getDefaultSmsPackage(int i) throws RemoteException;

    List<String> getHeldRolesFromController(String str) throws RemoteException;

    List<String> getRoleHoldersAsUser(String str, int i) throws RemoteException;

    void getSmsMessagesForFinancialApp(String str, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException;

    boolean isRoleAvailable(String str) throws RemoteException;

    boolean isRoleHeld(String str, String str2) throws RemoteException;

    void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener, int i) throws RemoteException;

    void removeRoleHolderAsUser(String str, String str2, int i, int i2, RemoteCallback remoteCallback) throws RemoteException;

    boolean removeRoleHolderFromController(String str, String str2) throws RemoteException;

    void setRoleNamesFromController(List<String> list) throws RemoteException;

    public static class Default implements IRoleManager {
        public boolean isRoleAvailable(String roleName) throws RemoteException {
            return false;
        }

        public boolean isRoleHeld(String roleName, String packageName) throws RemoteException {
            return false;
        }

        public List<String> getRoleHoldersAsUser(String roleName, int userId) throws RemoteException {
            return null;
        }

        public void addRoleHolderAsUser(String roleName, String packageName, int flags, int userId, RemoteCallback callback) throws RemoteException {
        }

        public void removeRoleHolderAsUser(String roleName, String packageName, int flags, int userId, RemoteCallback callback) throws RemoteException {
        }

        public void clearRoleHoldersAsUser(String roleName, int flags, int userId, RemoteCallback callback) throws RemoteException {
        }

        public void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener listener, int userId) throws RemoteException {
        }

        public void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener listener, int userId) throws RemoteException {
        }

        public void setRoleNamesFromController(List<String> list) throws RemoteException {
        }

        public boolean addRoleHolderFromController(String roleName, String packageName) throws RemoteException {
            return false;
        }

        public boolean removeRoleHolderFromController(String roleName, String packageName) throws RemoteException {
            return false;
        }

        public List<String> getHeldRolesFromController(String packageName) throws RemoteException {
            return null;
        }

        public String getDefaultSmsPackage(int userId) throws RemoteException {
            return null;
        }

        public void getSmsMessagesForFinancialApp(String callingPkg, Bundle params, IFinancialSmsCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRoleManager {
        private static final String DESCRIPTOR = "android.app.role.IRoleManager";
        static final int TRANSACTION_addOnRoleHoldersChangedListenerAsUser = 7;
        static final int TRANSACTION_addRoleHolderAsUser = 4;
        static final int TRANSACTION_addRoleHolderFromController = 10;
        static final int TRANSACTION_clearRoleHoldersAsUser = 6;
        static final int TRANSACTION_getDefaultSmsPackage = 13;
        static final int TRANSACTION_getHeldRolesFromController = 12;
        static final int TRANSACTION_getRoleHoldersAsUser = 3;
        static final int TRANSACTION_getSmsMessagesForFinancialApp = 14;
        static final int TRANSACTION_isRoleAvailable = 1;
        static final int TRANSACTION_isRoleHeld = 2;
        static final int TRANSACTION_removeOnRoleHoldersChangedListenerAsUser = 8;
        static final int TRANSACTION_removeRoleHolderAsUser = 5;
        static final int TRANSACTION_removeRoleHolderFromController = 11;
        static final int TRANSACTION_setRoleNamesFromController = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRoleManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRoleManager)) {
                return new Proxy(obj);
            }
            return (IRoleManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isRoleAvailable";
                case 2:
                    return "isRoleHeld";
                case 3:
                    return "getRoleHoldersAsUser";
                case 4:
                    return "addRoleHolderAsUser";
                case 5:
                    return "removeRoleHolderAsUser";
                case 6:
                    return "clearRoleHoldersAsUser";
                case 7:
                    return "addOnRoleHoldersChangedListenerAsUser";
                case 8:
                    return "removeOnRoleHoldersChangedListenerAsUser";
                case 9:
                    return "setRoleNamesFromController";
                case 10:
                    return "addRoleHolderFromController";
                case 11:
                    return "removeRoleHolderFromController";
                case 12:
                    return "getHeldRolesFromController";
                case 13:
                    return "getDefaultSmsPackage";
                case 14:
                    return "getSmsMessagesForFinancialApp";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.os.RemoteCallback} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v5 */
        /* JADX WARNING: type inference failed for: r0v10 */
        /* JADX WARNING: type inference failed for: r0v33 */
        /* JADX WARNING: type inference failed for: r0v34 */
        /* JADX WARNING: type inference failed for: r0v35 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                r16 = this;
                r6 = r16
                r7 = r17
                r8 = r18
                r9 = r19
                java.lang.String r10 = "android.app.role.IRoleManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x018f
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x017d;
                    case 2: goto L_0x0167;
                    case 3: goto L_0x0151;
                    case 4: goto L_0x0120;
                    case 5: goto L_0x00ef;
                    case 6: goto L_0x00c9;
                    case 7: goto L_0x00b3;
                    case 8: goto L_0x009d;
                    case 9: goto L_0x008f;
                    case 10: goto L_0x0079;
                    case 11: goto L_0x0063;
                    case 12: goto L_0x0051;
                    case 13: goto L_0x003f;
                    case 14: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r17, r18, r19, r20)
                return r0
            L_0x0019:
                r8.enforceInterface(r10)
                java.lang.String r1 = r18.readString()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x002f
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0030
            L_0x002f:
            L_0x0030:
                android.os.IBinder r2 = r18.readStrongBinder()
                android.telephony.IFinancialSmsCallback r2 = android.telephony.IFinancialSmsCallback.Stub.asInterface(r2)
                r6.getSmsMessagesForFinancialApp(r1, r0, r2)
                r19.writeNoException()
                return r11
            L_0x003f:
                r8.enforceInterface(r10)
                int r0 = r18.readInt()
                java.lang.String r1 = r6.getDefaultSmsPackage(r0)
                r19.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0051:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                java.util.List r1 = r6.getHeldRolesFromController(r0)
                r19.writeNoException()
                r9.writeStringList(r1)
                return r11
            L_0x0063:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                java.lang.String r1 = r18.readString()
                boolean r2 = r6.removeRoleHolderFromController(r0, r1)
                r19.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0079:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                java.lang.String r1 = r18.readString()
                boolean r2 = r6.addRoleHolderFromController(r0, r1)
                r19.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x008f:
                r8.enforceInterface(r10)
                java.util.ArrayList r0 = r18.createStringArrayList()
                r6.setRoleNamesFromController(r0)
                r19.writeNoException()
                return r11
            L_0x009d:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r18.readStrongBinder()
                android.app.role.IOnRoleHoldersChangedListener r0 = android.app.role.IOnRoleHoldersChangedListener.Stub.asInterface(r0)
                int r1 = r18.readInt()
                r6.removeOnRoleHoldersChangedListenerAsUser(r0, r1)
                r19.writeNoException()
                return r11
            L_0x00b3:
                r8.enforceInterface(r10)
                android.os.IBinder r0 = r18.readStrongBinder()
                android.app.role.IOnRoleHoldersChangedListener r0 = android.app.role.IOnRoleHoldersChangedListener.Stub.asInterface(r0)
                int r1 = r18.readInt()
                r6.addOnRoleHoldersChangedListenerAsUser(r0, r1)
                r19.writeNoException()
                return r11
            L_0x00c9:
                r8.enforceInterface(r10)
                java.lang.String r1 = r18.readString()
                int r2 = r18.readInt()
                int r3 = r18.readInt()
                int r4 = r18.readInt()
                if (r4 == 0) goto L_0x00e7
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                goto L_0x00e8
            L_0x00e7:
            L_0x00e8:
                r6.clearRoleHoldersAsUser(r1, r2, r3, r0)
                r19.writeNoException()
                return r11
            L_0x00ef:
                r8.enforceInterface(r10)
                java.lang.String r12 = r18.readString()
                java.lang.String r13 = r18.readString()
                int r14 = r18.readInt()
                int r15 = r18.readInt()
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x0112
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
            L_0x0110:
                r5 = r0
                goto L_0x0113
            L_0x0112:
                goto L_0x0110
            L_0x0113:
                r0 = r16
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r0.removeRoleHolderAsUser(r1, r2, r3, r4, r5)
                r19.writeNoException()
                return r11
            L_0x0120:
                r8.enforceInterface(r10)
                java.lang.String r12 = r18.readString()
                java.lang.String r13 = r18.readString()
                int r14 = r18.readInt()
                int r15 = r18.readInt()
                int r1 = r18.readInt()
                if (r1 == 0) goto L_0x0143
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
            L_0x0141:
                r5 = r0
                goto L_0x0144
            L_0x0143:
                goto L_0x0141
            L_0x0144:
                r0 = r16
                r1 = r12
                r2 = r13
                r3 = r14
                r4 = r15
                r0.addRoleHolderAsUser(r1, r2, r3, r4, r5)
                r19.writeNoException()
                return r11
            L_0x0151:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                int r1 = r18.readInt()
                java.util.List r2 = r6.getRoleHoldersAsUser(r0, r1)
                r19.writeNoException()
                r9.writeStringList(r2)
                return r11
            L_0x0167:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                java.lang.String r1 = r18.readString()
                boolean r2 = r6.isRoleHeld(r0, r1)
                r19.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x017d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r18.readString()
                boolean r1 = r6.isRoleAvailable(r0)
                r19.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x018f:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.role.IRoleManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IRoleManager {
            public static IRoleManager sDefaultImpl;
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

            public boolean isRoleAvailable(String roleName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRoleAvailable(roleName);
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

            public boolean isRoleHeld(String roleName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRoleHeld(roleName, packageName);
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

            public List<String> getRoleHoldersAsUser(String roleName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRoleHoldersAsUser(roleName, userId);
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

            public void addRoleHolderAsUser(String roleName, String packageName, int flags, int userId, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addRoleHolderAsUser(roleName, packageName, flags, userId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeRoleHolderAsUser(String roleName, String packageName, int flags, int userId, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeRoleHolderAsUser(roleName, packageName, flags, userId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearRoleHoldersAsUser(String roleName, int flags, int userId, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearRoleHoldersAsUser(roleName, flags, userId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOnRoleHoldersChangedListenerAsUser(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeOnRoleHoldersChangedListenerAsUser(IOnRoleHoldersChangedListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeOnRoleHoldersChangedListenerAsUser(listener, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRoleNamesFromController(List<String> roleNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(roleNames);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRoleNamesFromController(roleNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addRoleHolderFromController(String roleName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addRoleHolderFromController(roleName, packageName);
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

            public boolean removeRoleHolderFromController(String roleName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(roleName);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeRoleHolderFromController(roleName, packageName);
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

            public List<String> getHeldRolesFromController(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHeldRolesFromController(packageName);
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

            public String getDefaultSmsPackage(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultSmsPackage(userId);
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

            public void getSmsMessagesForFinancialApp(String callingPkg, Bundle params, IFinancialSmsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getSmsMessagesForFinancialApp(callingPkg, params, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRoleManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IRoleManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
