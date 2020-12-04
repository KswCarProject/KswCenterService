package android.permission;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.List;

public interface IPermissionController extends IInterface {
    void countPermissionApps(List<String> list, int i, RemoteCallback remoteCallback) throws RemoteException;

    void getAppPermissions(String str, RemoteCallback remoteCallback) throws RemoteException;

    void getPermissionUsages(boolean z, long j, RemoteCallback remoteCallback) throws RemoteException;

    void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback remoteCallback) throws RemoteException;

    void restoreDelayedRuntimePermissionBackup(String str, UserHandle userHandle, RemoteCallback remoteCallback) throws RemoteException;

    void restoreRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void revokeRuntimePermission(String str, String str2) throws RemoteException;

    void revokeRuntimePermissions(Bundle bundle, boolean z, int i, String str, RemoteCallback remoteCallback) throws RemoteException;

    void setRuntimePermissionGrantStateByDeviceAdmin(String str, String str2, String str3, int i, RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IPermissionController {
        public void revokeRuntimePermissions(Bundle request, boolean doDryRun, int reason, String callerPackageName, RemoteCallback callback) throws RemoteException {
        }

        public void getRuntimePermissionBackup(UserHandle user, ParcelFileDescriptor pipe) throws RemoteException {
        }

        public void restoreRuntimePermissionBackup(UserHandle user, ParcelFileDescriptor pipe) throws RemoteException {
        }

        public void restoreDelayedRuntimePermissionBackup(String packageName, UserHandle user, RemoteCallback callback) throws RemoteException {
        }

        public void getAppPermissions(String packageName, RemoteCallback callback) throws RemoteException {
        }

        public void revokeRuntimePermission(String packageName, String permissionName) throws RemoteException {
        }

        public void countPermissionApps(List<String> list, int flags, RemoteCallback callback) throws RemoteException {
        }

        public void getPermissionUsages(boolean countSystem, long numMillis, RemoteCallback callback) throws RemoteException {
        }

        public void setRuntimePermissionGrantStateByDeviceAdmin(String callerPackageName, String packageName, String permission, int grantState, RemoteCallback callback) throws RemoteException {
        }

        public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPermissionController {
        private static final String DESCRIPTOR = "android.permission.IPermissionController";
        static final int TRANSACTION_countPermissionApps = 7;
        static final int TRANSACTION_getAppPermissions = 5;
        static final int TRANSACTION_getPermissionUsages = 8;
        static final int TRANSACTION_getRuntimePermissionBackup = 2;
        static final int TRANSACTION_grantOrUpgradeDefaultRuntimePermissions = 10;
        static final int TRANSACTION_restoreDelayedRuntimePermissionBackup = 4;
        static final int TRANSACTION_restoreRuntimePermissionBackup = 3;
        static final int TRANSACTION_revokeRuntimePermission = 6;
        static final int TRANSACTION_revokeRuntimePermissions = 1;
        static final int TRANSACTION_setRuntimePermissionGrantStateByDeviceAdmin = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPermissionController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPermissionController)) {
                return new Proxy(obj);
            }
            return (IPermissionController) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "revokeRuntimePermissions";
                case 2:
                    return "getRuntimePermissionBackup";
                case 3:
                    return "restoreRuntimePermissionBackup";
                case 4:
                    return "restoreDelayedRuntimePermissionBackup";
                case 5:
                    return "getAppPermissions";
                case 6:
                    return "revokeRuntimePermission";
                case 7:
                    return "countPermissionApps";
                case 8:
                    return "getPermissionUsages";
                case 9:
                    return "setRuntimePermissionGrantStateByDeviceAdmin";
                case 10:
                    return "grantOrUpgradeDefaultRuntimePermissions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX WARNING: type inference failed for: r1v7, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r3v4, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r1v11, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: type inference failed for: r3v7, types: [android.os.ParcelFileDescriptor] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.permission.IPermissionController"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x0188
                r0 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x014c;
                    case 2: goto L_0x0123;
                    case 3: goto L_0x00fa;
                    case 4: goto L_0x00cd;
                    case 5: goto L_0x00b0;
                    case 6: goto L_0x00a1;
                    case 7: goto L_0x0080;
                    case 8: goto L_0x005d;
                    case 9: goto L_0x0030;
                    case 10: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0017:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x002a
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                r3 = r0
                android.os.RemoteCallback r3 = (android.os.RemoteCallback) r3
                goto L_0x002b
            L_0x002a:
            L_0x002b:
                r0 = r3
                r15.grantOrUpgradeDefaultRuntimePermissions(r0)
                return r10
            L_0x0030:
                r8.enforceInterface(r9)
                java.lang.String r11 = r17.readString()
                java.lang.String r12 = r17.readString()
                java.lang.String r13 = r17.readString()
                int r14 = r17.readInt()
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0053
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r5 = r0
                goto L_0x0054
            L_0x0053:
                r5 = r3
            L_0x0054:
                r0 = r15
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r0.setRuntimePermissionGrantStateByDeviceAdmin(r1, r2, r3, r4, r5)
                return r10
            L_0x005d:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0068
                r0 = r10
            L_0x0068:
                long r1 = r17.readLong()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x007b
                android.os.Parcelable$Creator<android.os.RemoteCallback> r3 = android.os.RemoteCallback.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.RemoteCallback r3 = (android.os.RemoteCallback) r3
                goto L_0x007c
            L_0x007b:
            L_0x007c:
                r15.getPermissionUsages(r0, r1, r3)
                return r10
            L_0x0080:
                r8.enforceInterface(r9)
                java.util.ArrayList r0 = r17.createStringArrayList()
                int r1 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.os.RemoteCallback> r2 = android.os.RemoteCallback.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                r3 = r2
                android.os.RemoteCallback r3 = (android.os.RemoteCallback) r3
                goto L_0x009c
            L_0x009b:
            L_0x009c:
                r2 = r3
                r15.countPermissionApps(r0, r1, r2)
                return r10
            L_0x00a1:
                r8.enforceInterface(r9)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                r15.revokeRuntimePermission(r0, r1)
                return r10
            L_0x00b0:
                r8.enforceInterface(r9)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00c7
                android.os.Parcelable$Creator<android.os.RemoteCallback> r1 = android.os.RemoteCallback.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.RemoteCallback r3 = (android.os.RemoteCallback) r3
                goto L_0x00c8
            L_0x00c7:
            L_0x00c8:
                r1 = r3
                r15.getAppPermissions(r0, r1)
                return r10
            L_0x00cd:
                r8.enforceInterface(r9)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00e3
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.UserHandle r1 = (android.os.UserHandle) r1
                goto L_0x00e4
            L_0x00e3:
                r1 = r3
            L_0x00e4:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00f4
                android.os.Parcelable$Creator<android.os.RemoteCallback> r2 = android.os.RemoteCallback.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                r3 = r2
                android.os.RemoteCallback r3 = (android.os.RemoteCallback) r3
                goto L_0x00f5
            L_0x00f4:
            L_0x00f5:
                r2 = r3
                r15.restoreDelayedRuntimePermissionBackup(r0, r1, r2)
                return r10
            L_0x00fa:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x010c
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x010d
            L_0x010c:
                r0 = r3
            L_0x010d:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x011d
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x011e
            L_0x011d:
            L_0x011e:
                r1 = r3
                r15.restoreRuntimePermissionBackup(r0, r1)
                return r10
            L_0x0123:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0135
                android.os.Parcelable$Creator<android.os.UserHandle> r0 = android.os.UserHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.UserHandle r0 = (android.os.UserHandle) r0
                goto L_0x0136
            L_0x0135:
                r0 = r3
            L_0x0136:
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0146
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r1 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.ParcelFileDescriptor r3 = (android.os.ParcelFileDescriptor) r3
                goto L_0x0147
            L_0x0146:
            L_0x0147:
                r1 = r3
                r15.getRuntimePermissionBackup(r0, r1)
                return r10
            L_0x014c:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x015e
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x015f
            L_0x015e:
                r1 = r3
            L_0x015f:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0167
                r2 = r10
                goto L_0x0168
            L_0x0167:
                r2 = r0
            L_0x0168:
                int r11 = r17.readInt()
                java.lang.String r12 = r17.readString()
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0180
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r5 = r0
                goto L_0x0181
            L_0x0180:
                r5 = r3
            L_0x0181:
                r0 = r15
                r3 = r11
                r4 = r12
                r0.revokeRuntimePermissions(r1, r2, r3, r4, r5)
                return r10
            L_0x0188:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.permission.IPermissionController.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPermissionController {
            public static IPermissionController sDefaultImpl;
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

            public void revokeRuntimePermissions(Bundle request, boolean doDryRun, int reason, String callerPackageName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(doDryRun);
                    _data.writeInt(reason);
                    _data.writeString(callerPackageName);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().revokeRuntimePermissions(request, doDryRun, reason, callerPackageName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getRuntimePermissionBackup(UserHandle user, ParcelFileDescriptor pipe) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (pipe != null) {
                        _data.writeInt(1);
                        pipe.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getRuntimePermissionBackup(user, pipe);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void restoreRuntimePermissionBackup(UserHandle user, ParcelFileDescriptor pipe) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (pipe != null) {
                        _data.writeInt(1);
                        pipe.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().restoreRuntimePermissionBackup(user, pipe);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void restoreDelayedRuntimePermissionBackup(String packageName, UserHandle user, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().restoreDelayedRuntimePermissionBackup(packageName, user, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAppPermissions(String packageName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAppPermissions(packageName, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void revokeRuntimePermission(String packageName, String permissionName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().revokeRuntimePermission(packageName, permissionName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void countPermissionApps(List<String> permissionNames, int flags, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(permissionNames);
                    _data.writeInt(flags);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().countPermissionApps(permissionNames, flags, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getPermissionUsages(boolean countSystem, long numMillis, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(countSystem);
                    _data.writeLong(numMillis);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getPermissionUsages(countSystem, numMillis, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRuntimePermissionGrantStateByDeviceAdmin(String callerPackageName, String packageName, String permission, int grantState, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    _data.writeInt(grantState);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRuntimePermissionGrantStateByDeviceAdmin(callerPackageName, packageName, permission, grantState, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().grantOrUpgradeDefaultRuntimePermissions(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPermissionController impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPermissionController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
