package android.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPeriodicAdvertisingCallback extends IInterface {
    void onPeriodicAdvertisingReport(PeriodicAdvertisingReport periodicAdvertisingReport) throws RemoteException;

    void onSyncEstablished(int i, BluetoothDevice bluetoothDevice, int i2, int i3, int i4, int i5) throws RemoteException;

    void onSyncLost(int i) throws RemoteException;

    public static class Default implements IPeriodicAdvertisingCallback {
        public void onSyncEstablished(int syncHandle, BluetoothDevice device, int advertisingSid, int skip, int timeout, int status) throws RemoteException {
        }

        public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport report) throws RemoteException {
        }

        public void onSyncLost(int syncHandle) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPeriodicAdvertisingCallback {
        private static final String DESCRIPTOR = "android.bluetooth.le.IPeriodicAdvertisingCallback";
        static final int TRANSACTION_onPeriodicAdvertisingReport = 2;
        static final int TRANSACTION_onSyncEstablished = 1;
        static final int TRANSACTION_onSyncLost = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPeriodicAdvertisingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPeriodicAdvertisingCallback)) {
                return new Proxy(obj);
            }
            return (IPeriodicAdvertisingCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSyncEstablished";
                case 2:
                    return "onPeriodicAdvertisingReport";
                case 3:
                    return "onSyncLost";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.bluetooth.le.PeriodicAdvertisingReport} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.bluetooth.le.PeriodicAdvertisingReport} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.bluetooth.le.PeriodicAdvertisingReport} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.bluetooth.le.PeriodicAdvertisingReport} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.bluetooth.le.PeriodicAdvertisingReport} */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.bluetooth.BluetoothDevice] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r18
                r9 = r19
                java.lang.String r10 = "android.bluetooth.le.IPeriodicAdvertisingCallback"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x006d
                r0 = 0
                switch(r8) {
                    case 1: goto L_0x0039;
                    case 2: goto L_0x0022;
                    case 3: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x0017:
                r9.enforceInterface(r10)
                int r0 = r19.readInt()
                r7.onSyncLost(r0)
                return r11
            L_0x0022:
                r9.enforceInterface(r10)
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0034
                android.os.Parcelable$Creator<android.bluetooth.le.PeriodicAdvertisingReport> r0 = android.bluetooth.le.PeriodicAdvertisingReport.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.bluetooth.le.PeriodicAdvertisingReport r0 = (android.bluetooth.le.PeriodicAdvertisingReport) r0
                goto L_0x0035
            L_0x0034:
            L_0x0035:
                r7.onPeriodicAdvertisingReport(r0)
                return r11
            L_0x0039:
                r9.enforceInterface(r10)
                int r12 = r19.readInt()
                int r1 = r19.readInt()
                if (r1 == 0) goto L_0x0050
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.bluetooth.BluetoothDevice r0 = (android.bluetooth.BluetoothDevice) r0
            L_0x004e:
                r2 = r0
                goto L_0x0051
            L_0x0050:
                goto L_0x004e
            L_0x0051:
                int r13 = r19.readInt()
                int r14 = r19.readInt()
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r6 = r16
                r0.onSyncEstablished(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x006d:
                r0 = r20
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.le.IPeriodicAdvertisingCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPeriodicAdvertisingCallback {
            public static IPeriodicAdvertisingCallback sDefaultImpl;
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

            public void onSyncEstablished(int syncHandle, BluetoothDevice device, int advertisingSid, int skip, int timeout, int status) throws RemoteException {
                BluetoothDevice bluetoothDevice = device;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(syncHandle);
                        if (bluetoothDevice != null) {
                            _data.writeInt(1);
                            bluetoothDevice.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        int i = advertisingSid;
                        int i2 = skip;
                        int i3 = timeout;
                        int i4 = status;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(advertisingSid);
                        try {
                            _data.writeInt(skip);
                            try {
                                _data.writeInt(timeout);
                            } catch (Throwable th2) {
                                th = th2;
                                int i42 = status;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i32 = timeout;
                            int i422 = status;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i22 = skip;
                        int i322 = timeout;
                        int i4222 = status;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(status);
                        try {
                            if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onSyncEstablished(syncHandle, device, advertisingSid, skip, timeout, status);
                            _data.recycle();
                        } catch (Throwable th5) {
                            th = th5;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i5 = syncHandle;
                    int i6 = advertisingSid;
                    int i222 = skip;
                    int i3222 = timeout;
                    int i42222 = status;
                    _data.recycle();
                    throw th;
                }
            }

            public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport report) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (report != null) {
                        _data.writeInt(1);
                        report.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPeriodicAdvertisingReport(report);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSyncLost(int syncHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(syncHandle);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSyncLost(syncHandle);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPeriodicAdvertisingCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPeriodicAdvertisingCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
