package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ILocationProviderManager extends IInterface {
    @UnsupportedAppUsage
    void onReportLocation(Location location) throws RemoteException;

    void onSetAdditionalProviderPackages(List<String> list) throws RemoteException;

    @UnsupportedAppUsage
    void onSetEnabled(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void onSetProperties(ProviderProperties providerProperties) throws RemoteException;

    public static class Default implements ILocationProviderManager {
        public void onSetAdditionalProviderPackages(List<String> list) throws RemoteException {
        }

        public void onSetEnabled(boolean enabled) throws RemoteException {
        }

        public void onSetProperties(ProviderProperties properties) throws RemoteException {
        }

        public void onReportLocation(Location location) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILocationProviderManager {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProviderManager";
        static final int TRANSACTION_onReportLocation = 4;
        static final int TRANSACTION_onSetAdditionalProviderPackages = 1;
        static final int TRANSACTION_onSetEnabled = 2;
        static final int TRANSACTION_onSetProperties = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProviderManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILocationProviderManager)) {
                return new Proxy(obj);
            }
            return (ILocationProviderManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSetAdditionalProviderPackages";
                case 2:
                    return "onSetEnabled";
                case 3:
                    return "onSetProperties";
                case 4:
                    return "onReportLocation";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.android.internal.location.ProviderProperties} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: android.location.Location} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "com.android.internal.location.ILocationProviderManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x0066
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x0058;
                    case 2: goto L_0x0045;
                    case 3: goto L_0x002b;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.location.Location> r1 = android.location.Location.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.location.Location r1 = (android.location.Location) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r4.onReportLocation(r1)
                r7.writeNoException()
                return r2
            L_0x002b:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003d
                android.os.Parcelable$Creator<com.android.internal.location.ProviderProperties> r1 = com.android.internal.location.ProviderProperties.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                com.android.internal.location.ProviderProperties r1 = (com.android.internal.location.ProviderProperties) r1
                goto L_0x003e
            L_0x003d:
            L_0x003e:
                r4.onSetProperties(r1)
                r7.writeNoException()
                return r2
            L_0x0045:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0050
                r1 = r2
                goto L_0x0051
            L_0x0050:
                r1 = 0
            L_0x0051:
                r4.onSetEnabled(r1)
                r7.writeNoException()
                return r2
            L_0x0058:
                r6.enforceInterface(r0)
                java.util.ArrayList r1 = r6.createStringArrayList()
                r4.onSetAdditionalProviderPackages(r1)
                r7.writeNoException()
                return r2
            L_0x0066:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.location.ILocationProviderManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILocationProviderManager {
            public static ILocationProviderManager sDefaultImpl;
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

            public void onSetAdditionalProviderPackages(List<String> packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSetAdditionalProviderPackages(packageNames);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onSetEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSetEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onSetProperties(ProviderProperties properties) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (properties != null) {
                        _data.writeInt(1);
                        properties.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSetProperties(properties);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onReportLocation(Location location) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (location != null) {
                        _data.writeInt(1);
                        location.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onReportLocation(location);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationProviderManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILocationProviderManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
