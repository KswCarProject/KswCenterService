package android.content.p002pm;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.IPackageInstallObserver2 */
/* loaded from: classes.dex */
public interface IPackageInstallObserver2 extends IInterface {
    @UnsupportedAppUsage
    void onPackageInstalled(String str, int i, String str2, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void onUserActionRequired(Intent intent) throws RemoteException;

    /* renamed from: android.content.pm.IPackageInstallObserver2$Default */
    /* loaded from: classes.dex */
    public static class Default implements IPackageInstallObserver2 {
        @Override // android.content.p002pm.IPackageInstallObserver2
        public void onUserActionRequired(Intent intent) throws RemoteException {
        }

        @Override // android.content.p002pm.IPackageInstallObserver2
        public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.IPackageInstallObserver2$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IPackageInstallObserver2 {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstallObserver2";
        static final int TRANSACTION_onPackageInstalled = 2;
        static final int TRANSACTION_onUserActionRequired = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstallObserver2 asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageInstallObserver2)) {
                return (IPackageInstallObserver2) iin;
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
                    return "onUserActionRequired";
                case 2:
                    return "onPackageInstalled";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Intent _arg0 = data.readInt() != 0 ? Intent.CREATOR.createFromParcel(data) : null;
                    onUserActionRequired(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    Bundle _arg3 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    onPackageInstalled(_arg02, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.content.pm.IPackageInstallObserver2$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IPackageInstallObserver2 {
            public static IPackageInstallObserver2 sDefaultImpl;
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

            @Override // android.content.p002pm.IPackageInstallObserver2
            public void onUserActionRequired(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserActionRequired(intent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPackageInstallObserver2
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(basePackageName);
                    _data.writeInt(returnCode);
                    _data.writeString(msg);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPackageInstalled(basePackageName, returnCode, msg, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPackageInstallObserver2 impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPackageInstallObserver2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
