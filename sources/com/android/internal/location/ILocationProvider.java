package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.WorkSource;

public interface ILocationProvider extends IInterface {
    @UnsupportedAppUsage
    int getStatus(Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    long getStatusUpdateTime() throws RemoteException;

    void sendExtraCommand(String str, Bundle bundle) throws RemoteException;

    void setLocationProviderManager(ILocationProviderManager iLocationProviderManager) throws RemoteException;

    void setRequest(ProviderRequest providerRequest, WorkSource workSource) throws RemoteException;

    public static class Default implements ILocationProvider {
        public void setLocationProviderManager(ILocationProviderManager manager) throws RemoteException {
        }

        public void setRequest(ProviderRequest request, WorkSource ws) throws RemoteException {
        }

        public void sendExtraCommand(String command, Bundle extras) throws RemoteException {
        }

        public int getStatus(Bundle extras) throws RemoteException {
            return 0;
        }

        public long getStatusUpdateTime() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILocationProvider {
        private static final String DESCRIPTOR = "com.android.internal.location.ILocationProvider";
        static final int TRANSACTION_getStatus = 4;
        static final int TRANSACTION_getStatusUpdateTime = 5;
        static final int TRANSACTION_sendExtraCommand = 3;
        static final int TRANSACTION_setLocationProviderManager = 1;
        static final int TRANSACTION_setRequest = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILocationProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILocationProvider)) {
                return new Proxy(obj);
            }
            return (ILocationProvider) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setLocationProviderManager";
                case 2:
                    return "setRequest";
                case 3:
                    return "sendExtraCommand";
                case 4:
                    return "getStatus";
                case 5:
                    return "getStatusUpdateTime";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v14 */
        /* JADX WARNING: type inference failed for: r1v15 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "com.android.internal.location.ILocationProvider"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x008a
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x007b;
                    case 2: goto L_0x0054;
                    case 3: goto L_0x0039;
                    case 4: goto L_0x001f;
                    case 5: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                long r3 = r5.getStatusUpdateTime()
                r8.writeNoException()
                r8.writeLong(r3)
                return r2
            L_0x001f:
                r7.enforceInterface(r0)
                android.os.Bundle r1 = new android.os.Bundle
                r1.<init>()
                int r3 = r5.getStatus(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                r8.writeInt(r2)
                r1.writeToParcel(r8, r2)
                return r2
            L_0x0039:
                r7.enforceInterface(r0)
                java.lang.String r3 = r7.readString()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x004f
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0050
            L_0x004f:
            L_0x0050:
                r5.sendExtraCommand(r3, r1)
                return r2
            L_0x0054:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0066
                android.os.Parcelable$Creator<com.android.internal.location.ProviderRequest> r3 = com.android.internal.location.ProviderRequest.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                com.android.internal.location.ProviderRequest r3 = (com.android.internal.location.ProviderRequest) r3
                goto L_0x0067
            L_0x0066:
                r3 = r1
            L_0x0067:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0076
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.WorkSource r1 = (android.os.WorkSource) r1
                goto L_0x0077
            L_0x0076:
            L_0x0077:
                r5.setRequest(r3, r1)
                return r2
            L_0x007b:
                r7.enforceInterface(r0)
                android.os.IBinder r1 = r7.readStrongBinder()
                com.android.internal.location.ILocationProviderManager r1 = com.android.internal.location.ILocationProviderManager.Stub.asInterface(r1)
                r5.setLocationProviderManager(r1)
                return r2
            L_0x008a:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.location.ILocationProvider.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ILocationProvider {
            public static ILocationProvider sDefaultImpl;
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

            public void setLocationProviderManager(ILocationProviderManager manager) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(manager != null ? manager.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setLocationProviderManager(manager);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setRequest(ProviderRequest request, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setRequest(request, ws);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void sendExtraCommand(String command, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().sendExtraCommand(command, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public int getStatus(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatus(extras);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        extras.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getStatusUpdateTime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatusUpdateTime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILocationProvider impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILocationProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
