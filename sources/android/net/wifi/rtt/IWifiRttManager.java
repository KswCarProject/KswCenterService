package android.net.wifi.rtt;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.WorkSource;

public interface IWifiRttManager extends IInterface {
    void cancelRanging(WorkSource workSource) throws RemoteException;

    boolean isAvailable() throws RemoteException;

    void startRanging(IBinder iBinder, String str, WorkSource workSource, RangingRequest rangingRequest, IRttCallback iRttCallback) throws RemoteException;

    public static class Default implements IWifiRttManager {
        public boolean isAvailable() throws RemoteException {
            return false;
        }

        public void startRanging(IBinder binder, String callingPackage, WorkSource workSource, RangingRequest request, IRttCallback callback) throws RemoteException {
        }

        public void cancelRanging(WorkSource workSource) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWifiRttManager {
        private static final String DESCRIPTOR = "android.net.wifi.rtt.IWifiRttManager";
        static final int TRANSACTION_cancelRanging = 3;
        static final int TRANSACTION_isAvailable = 1;
        static final int TRANSACTION_startRanging = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWifiRttManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWifiRttManager)) {
                return new Proxy(obj);
            }
            return (IWifiRttManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isAvailable";
                case 2:
                    return "startRanging";
                case 3:
                    return "cancelRanging";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: android.net.wifi.rtt.RangingRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.os.WorkSource} */
        /* JADX WARNING: type inference failed for: r1v8, types: [android.net.wifi.rtt.RangingRequest] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r12, android.os.Parcel r13, android.os.Parcel r14, int r15) throws android.os.RemoteException {
            /*
                r11 = this;
                java.lang.String r0 = "android.net.wifi.rtt.IWifiRttManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r12 == r1) goto L_0x0079
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x006b;
                    case 2: goto L_0x002b;
                    case 3: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r12, r13, r14, r15)
                return r1
            L_0x0011:
                r13.enforceInterface(r0)
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.os.WorkSource r1 = (android.os.WorkSource) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                r11.cancelRanging(r1)
                r14.writeNoException()
                return r2
            L_0x002b:
                r13.enforceInterface(r0)
                android.os.IBinder r9 = r13.readStrongBinder()
                java.lang.String r10 = r13.readString()
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0046
                android.os.Parcelable$Creator<android.os.WorkSource> r3 = android.os.WorkSource.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r13)
                android.os.WorkSource r3 = (android.os.WorkSource) r3
                r6 = r3
                goto L_0x0047
            L_0x0046:
                r6 = r1
            L_0x0047:
                int r3 = r13.readInt()
                if (r3 == 0) goto L_0x0057
                android.os.Parcelable$Creator<android.net.wifi.rtt.RangingRequest> r1 = android.net.wifi.rtt.RangingRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.net.wifi.rtt.RangingRequest r1 = (android.net.wifi.rtt.RangingRequest) r1
            L_0x0055:
                r7 = r1
                goto L_0x0058
            L_0x0057:
                goto L_0x0055
            L_0x0058:
                android.os.IBinder r1 = r13.readStrongBinder()
                android.net.wifi.rtt.IRttCallback r1 = android.net.wifi.rtt.IRttCallback.Stub.asInterface(r1)
                r3 = r11
                r4 = r9
                r5 = r10
                r8 = r1
                r3.startRanging(r4, r5, r6, r7, r8)
                r14.writeNoException()
                return r2
            L_0x006b:
                r13.enforceInterface(r0)
                boolean r1 = r11.isAvailable()
                r14.writeNoException()
                r14.writeInt(r1)
                return r2
            L_0x0079:
                r14.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.rtt.IWifiRttManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWifiRttManager {
            public static IWifiRttManager sDefaultImpl;
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

            public boolean isAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvailable();
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

            public void startRanging(IBinder binder, String callingPackage, WorkSource workSource, RangingRequest request, IRttCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(callingPackage);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startRanging(binder, callingPackage, workSource, request, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelRanging(WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelRanging(workSource);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWifiRttManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWifiRttManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
