package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallObserver2 extends IInterface {
    @UnsupportedAppUsage
    void onPackageInstalled(String str, int i, String str2, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void onUserActionRequired(Intent intent) throws RemoteException;

    public static class Default implements IPackageInstallObserver2 {
        public void onUserActionRequired(Intent intent) throws RemoteException {
        }

        public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof IPackageInstallObserver2)) {
                return new Proxy(obj);
            }
            return (IPackageInstallObserver2) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v2, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v11 */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.content.pm.IPackageInstallObserver2"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x004b
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0034;
                    case 2: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                java.lang.String r5 = r9.readString()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x002f
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0030
            L_0x002f:
            L_0x0030:
                r7.onPackageInstalled(r3, r4, r5, r1)
                return r2
            L_0x0034:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0046
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x0047
            L_0x0046:
            L_0x0047:
                r7.onUserActionRequired(r1)
                return r2
            L_0x004b:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IPackageInstallObserver2.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPackageInstallObserver2 {
            public static IPackageInstallObserver2 sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onUserActionRequired(intent);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPackageInstalled(basePackageName, returnCode, msg, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPackageInstallObserver2 impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPackageInstallObserver2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
