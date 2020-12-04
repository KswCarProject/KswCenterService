package android.content.pm;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

public interface IOnAppsChangedListener extends IInterface {
    void onPackageAdded(UserHandle userHandle, String str) throws RemoteException;

    void onPackageChanged(UserHandle userHandle, String str) throws RemoteException;

    void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException;

    void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException;

    void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException;

    void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    public static class Default implements IOnAppsChangedListener {
        public void onPackageRemoved(UserHandle user, String packageName) throws RemoteException {
        }

        public void onPackageAdded(UserHandle user, String packageName) throws RemoteException {
        }

        public void onPackageChanged(UserHandle user, String packageName) throws RemoteException {
        }

        public void onPackagesAvailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
        }

        public void onPackagesUnavailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
        }

        public void onPackagesSuspended(UserHandle user, String[] packageNames, Bundle launcherExtras) throws RemoteException {
        }

        public void onPackagesUnsuspended(UserHandle user, String[] packageNames) throws RemoteException {
        }

        public void onShortcutChanged(UserHandle user, String packageName, ParceledListSlice shortcuts) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOnAppsChangedListener {
        private static final String DESCRIPTOR = "android.content.pm.IOnAppsChangedListener";
        static final int TRANSACTION_onPackageAdded = 2;
        static final int TRANSACTION_onPackageChanged = 3;
        static final int TRANSACTION_onPackageRemoved = 1;
        static final int TRANSACTION_onPackagesAvailable = 4;
        static final int TRANSACTION_onPackagesSuspended = 6;
        static final int TRANSACTION_onPackagesUnavailable = 5;
        static final int TRANSACTION_onPackagesUnsuspended = 7;
        static final int TRANSACTION_onShortcutChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOnAppsChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IOnAppsChangedListener)) {
                return new Proxy(obj);
            }
            return (IOnAppsChangedListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onPackageRemoved";
                case 2:
                    return "onPackageAdded";
                case 3:
                    return "onPackageChanged";
                case 4:
                    return "onPackagesAvailable";
                case 5:
                    return "onPackagesUnavailable";
                case 6:
                    return "onPackagesSuspended";
                case 7:
                    return "onPackagesUnsuspended";
                case 8:
                    return "onShortcutChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r3v5 */
        /* JADX WARNING: type inference failed for: r3v9 */
        /* JADX WARNING: type inference failed for: r3v25 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.content.pm.IOnAppsChangedListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0122
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x0105;
                    case 2: goto L_0x00e8;
                    case 3: goto L_0x00cb;
                    case 4: goto L_0x00a8;
                    case 5: goto L_0x0085;
                    case 6: goto L_0x005a;
                    case 7: goto L_0x003d;
                    case 8: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0024
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.UserHandle r1 = (android.os.UserHandle) r1
                goto L_0x0025
            L_0x0024:
                r1 = r3
            L_0x0025:
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0038
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r3 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.content.pm.ParceledListSlice r3 = (android.content.pm.ParceledListSlice) r3
                goto L_0x0039
            L_0x0038:
            L_0x0039:
                r6.onShortcutChanged(r1, r4, r3)
                return r2
            L_0x003d:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0050
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x0051
            L_0x0050:
            L_0x0051:
                r1 = r3
                java.lang.String[] r3 = r8.createStringArray()
                r6.onPackagesUnsuspended(r1, r3)
                return r2
            L_0x005a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x006c
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.UserHandle r1 = (android.os.UserHandle) r1
                goto L_0x006d
            L_0x006c:
                r1 = r3
            L_0x006d:
                java.lang.String[] r4 = r8.createStringArray()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x0080
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x0081
            L_0x0080:
            L_0x0081:
                r6.onPackagesSuspended(r1, r4, r3)
                return r2
            L_0x0085:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0097
                android.os.Parcelable$Creator<android.os.UserHandle> r3 = android.os.UserHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x0098
            L_0x0097:
            L_0x0098:
                java.lang.String[] r4 = r8.createStringArray()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00a4
                r1 = r2
            L_0x00a4:
                r6.onPackagesUnavailable(r3, r4, r1)
                return r2
            L_0x00a8:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x00ba
                android.os.Parcelable$Creator<android.os.UserHandle> r3 = android.os.UserHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x00bb
            L_0x00ba:
            L_0x00bb:
                java.lang.String[] r4 = r8.createStringArray()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00c7
                r1 = r2
            L_0x00c7:
                r6.onPackagesAvailable(r3, r4, r1)
                return r2
            L_0x00cb:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00de
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x00df
            L_0x00de:
            L_0x00df:
                r1 = r3
                java.lang.String r3 = r8.readString()
                r6.onPackageChanged(r1, r3)
                return r2
            L_0x00e8:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00fb
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x00fc
            L_0x00fb:
            L_0x00fc:
                r1 = r3
                java.lang.String r3 = r8.readString()
                r6.onPackageAdded(r1, r3)
                return r2
            L_0x0105:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0118
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x0119
            L_0x0118:
            L_0x0119:
                r1 = r3
                java.lang.String r3 = r8.readString()
                r6.onPackageRemoved(r1, r3)
                return r2
            L_0x0122:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IOnAppsChangedListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IOnAppsChangedListener {
            public static IOnAppsChangedListener sDefaultImpl;
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

            public void onPackageRemoved(UserHandle user, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackageRemoved(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackageAdded(UserHandle user, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackageAdded(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackageChanged(UserHandle user, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackageChanged(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackagesAvailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(packageNames);
                    _data.writeInt(replacing);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackagesAvailable(user, packageNames, replacing);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackagesUnavailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(packageNames);
                    _data.writeInt(replacing);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackagesUnavailable(user, packageNames, replacing);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackagesSuspended(UserHandle user, String[] packageNames, Bundle launcherExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(packageNames);
                    if (launcherExtras != null) {
                        _data.writeInt(1);
                        launcherExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackagesSuspended(user, packageNames, launcherExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPackagesUnsuspended(UserHandle user, String[] packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(packageNames);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackagesUnsuspended(user, packageNames);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onShortcutChanged(UserHandle user, String packageName, ParceledListSlice shortcuts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (shortcuts != null) {
                        _data.writeInt(1);
                        shortcuts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onShortcutChanged(user, packageName, shortcuts);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOnAppsChangedListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IOnAppsChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
