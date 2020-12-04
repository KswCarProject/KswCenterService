package android.location;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILocationListener extends IInterface {
    @UnsupportedAppUsage
    void onLocationChanged(Location location) throws RemoteException;

    @UnsupportedAppUsage
    void onProviderDisabled(String str) throws RemoteException;

    @UnsupportedAppUsage
    void onProviderEnabled(String str) throws RemoteException;

    @UnsupportedAppUsage
    void onStatusChanged(String str, int i, Bundle bundle) throws RemoteException;

    public static class Default implements ILocationListener {
        public void onLocationChanged(Location location) throws RemoteException {
        }

        public void onProviderEnabled(String provider) throws RemoteException {
        }

        public void onProviderDisabled(String provider) throws RemoteException {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILocationListener {
        private static final String DESCRIPTOR = "android.location.ILocationListener";
        static final int TRANSACTION_onLocationChanged = 1;
        static final int TRANSACTION_onProviderDisabled = 3;
        static final int TRANSACTION_onProviderEnabled = 2;
        static final int TRANSACTION_onStatusChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILocationListener)) {
                return new Proxy(obj);
            }
            return (ILocationListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onLocationChanged";
                case 2:
                    return "onProviderEnabled";
                case 3:
                    return "onProviderDisabled";
                case 4:
                    return "onStatusChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.location.Location} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v13 */
        /* JADX WARNING: type inference failed for: r1v14 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.location.ILocationListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x005d
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0046;
                    case 2: goto L_0x003b;
                    case 3: goto L_0x0030;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x002b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x002c
            L_0x002b:
            L_0x002c:
                r6.onStatusChanged(r3, r4, r1)
                return r2
            L_0x0030:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.onProviderDisabled(r1)
                return r2
            L_0x003b:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.onProviderEnabled(r1)
                return r2
            L_0x0046:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x0058
                android.os.Parcelable$Creator<android.location.Location> r1 = android.location.Location.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.location.Location r1 = (android.location.Location) r1
                goto L_0x0059
            L_0x0058:
            L_0x0059:
                r6.onLocationChanged(r1)
                return r2
            L_0x005d:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.location.ILocationListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILocationListener {
            public static ILocationListener sDefaultImpl;
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

            public void onLocationChanged(Location location) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onLocationChanged(location);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProviderEnabled(String provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProviderEnabled(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onProviderDisabled(String provider) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onProviderDisabled(provider);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeInt(status);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onStatusChanged(provider, status, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILocationListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
