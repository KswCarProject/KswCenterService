package android.content.p002pm;

import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.UserHandle;

/* renamed from: android.content.pm.IOnAppsChangedListener */
/* loaded from: classes.dex */
public interface IOnAppsChangedListener extends IInterface {
    void onPackageAdded(UserHandle userHandle, String str) throws RemoteException;

    void onPackageChanged(UserHandle userHandle, String str) throws RemoteException;

    void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException;

    void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException;

    void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException;

    void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException;

    void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    /* renamed from: android.content.pm.IOnAppsChangedListener$Default */
    /* loaded from: classes.dex */
    public static class Default implements IOnAppsChangedListener {
        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackageRemoved(UserHandle user, String packageName) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackageAdded(UserHandle user, String packageName) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackageChanged(UserHandle user, String packageName) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackagesAvailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackagesUnavailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackagesSuspended(UserHandle user, String[] packageNames, Bundle launcherExtras) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onPackagesUnsuspended(UserHandle user, String[] packageNames) throws RemoteException {
        }

        @Override // android.content.p002pm.IOnAppsChangedListener
        public void onShortcutChanged(UserHandle user, String packageName, ParceledListSlice shortcuts) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.IOnAppsChangedListener$Stub */
    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof IOnAppsChangedListener)) {
                return (IOnAppsChangedListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg2;
            UserHandle _arg0;
            UserHandle _arg02;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg03 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg04 = _arg03;
                    String _arg1 = data.readString();
                    onPackageRemoved(_arg04, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg05 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg06 = _arg05;
                    String _arg12 = data.readString();
                    onPackageAdded(_arg06, _arg12);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg07 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg08 = _arg07;
                    String _arg13 = data.readString();
                    onPackageChanged(_arg08, _arg13);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg09 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    String[] _arg14 = data.createStringArray();
                    _arg2 = data.readInt() != 0;
                    onPackagesAvailable(_arg09, _arg14, _arg2);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg010 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    String[] _arg15 = data.createStringArray();
                    _arg2 = data.readInt() != 0;
                    onPackagesUnavailable(_arg010, _arg15, _arg2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = UserHandle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    String[] _arg16 = data.createStringArray();
                    Bundle _arg22 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onPackagesSuspended(_arg0, _arg16, _arg22);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    UserHandle _arg011 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    UserHandle _arg012 = _arg011;
                    String[] _arg17 = data.createStringArray();
                    onPackagesUnsuspended(_arg012, _arg17);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = UserHandle.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    String _arg18 = data.readString();
                    ParceledListSlice _arg23 = data.readInt() != 0 ? ParceledListSlice.CREATOR.createFromParcel(data) : null;
                    onShortcutChanged(_arg02, _arg18, _arg23);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.IOnAppsChangedListener$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IOnAppsChangedListener {
            public static IOnAppsChangedListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageRemoved(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageAdded(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageChanged(user, packageName);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    _data.writeInt(replacing ? 1 : 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesAvailable(user, packageNames, replacing);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    _data.writeInt(replacing ? 1 : 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesUnavailable(user, packageNames, replacing);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesSuspended(user, packageNames, launcherExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackagesUnsuspended(user, packageNames);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IOnAppsChangedListener
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
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShortcutChanged(user, packageName, shortcuts);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOnAppsChangedListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IOnAppsChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
