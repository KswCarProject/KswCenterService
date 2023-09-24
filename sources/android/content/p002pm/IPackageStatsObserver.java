package android.content.p002pm;

import android.annotation.UnsupportedAppUsage;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.content.pm.IPackageStatsObserver */
/* loaded from: classes.dex */
public interface IPackageStatsObserver extends IInterface {
    @UnsupportedAppUsage
    void onGetStatsCompleted(PackageStats packageStats, boolean z) throws RemoteException;

    /* renamed from: android.content.pm.IPackageStatsObserver$Default */
    /* loaded from: classes.dex */
    public static class Default implements IPackageStatsObserver {
        @Override // android.content.p002pm.IPackageStatsObserver
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.content.pm.IPackageStatsObserver$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IPackageStatsObserver {
        private static final String DESCRIPTOR = "android.content.pm.IPackageStatsObserver";
        static final int TRANSACTION_onGetStatsCompleted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageStatsObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageStatsObserver)) {
                return (IPackageStatsObserver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onGetStatsCompleted";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            PackageStats _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = PackageStats.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            boolean _arg1 = data.readInt() != 0;
            onGetStatsCompleted(_arg0, _arg1);
            return true;
        }

        /* renamed from: android.content.pm.IPackageStatsObserver$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IPackageStatsObserver {
            public static IPackageStatsObserver sDefaultImpl;
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

            @Override // android.content.p002pm.IPackageStatsObserver
            public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pStats != null) {
                        _data.writeInt(1);
                        pStats.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(succeeded ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetStatsCompleted(pStats, succeeded);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPackageStatsObserver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPackageStatsObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
