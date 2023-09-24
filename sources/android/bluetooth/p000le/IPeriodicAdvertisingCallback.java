package android.bluetooth.p000le;

import android.bluetooth.BluetoothDevice;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.bluetooth.le.IPeriodicAdvertisingCallback */
/* loaded from: classes.dex */
public interface IPeriodicAdvertisingCallback extends IInterface {
    void onPeriodicAdvertisingReport(PeriodicAdvertisingReport periodicAdvertisingReport) throws RemoteException;

    void onSyncEstablished(int i, BluetoothDevice bluetoothDevice, int i2, int i3, int i4, int i5) throws RemoteException;

    void onSyncLost(int i) throws RemoteException;

    /* renamed from: android.bluetooth.le.IPeriodicAdvertisingCallback$Default */
    /* loaded from: classes.dex */
    public static class Default implements IPeriodicAdvertisingCallback {
        @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
        public void onSyncEstablished(int syncHandle, BluetoothDevice device, int advertisingSid, int skip, int timeout, int status) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
        public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport report) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
        public void onSyncLost(int syncHandle) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.bluetooth.le.IPeriodicAdvertisingCallback$Stub */
    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof IPeriodicAdvertisingCallback)) {
                return (IPeriodicAdvertisingCallback) iin;
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
                    return "onSyncEstablished";
                case 2:
                    return "onPeriodicAdvertisingReport";
                case 3:
                    return "onSyncLost";
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
                    int _arg0 = data.readInt();
                    BluetoothDevice _arg1 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    onSyncEstablished(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    PeriodicAdvertisingReport _arg02 = data.readInt() != 0 ? PeriodicAdvertisingReport.CREATOR.createFromParcel(data) : null;
                    onPeriodicAdvertisingReport(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    onSyncLost(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.bluetooth.le.IPeriodicAdvertisingCallback$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IPeriodicAdvertisingCallback {
            public static IPeriodicAdvertisingCallback sDefaultImpl;
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

            @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
            public void onSyncEstablished(int syncHandle, BluetoothDevice device, int advertisingSid, int skip, int timeout, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(syncHandle);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(advertisingSid);
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(skip);
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(timeout);
                        try {
                            _data.writeInt(status);
                        } catch (Throwable th4) {
                            th = th4;
                        }
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
                try {
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSyncEstablished(syncHandle, device, advertisingSid, skip, timeout, status);
                        _data.recycle();
                        return;
                    }
                    _data.recycle();
                } catch (Throwable th7) {
                    th = th7;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPeriodicAdvertisingReport(report);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.p000le.IPeriodicAdvertisingCallback
            public void onSyncLost(int syncHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(syncHandle);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSyncLost(syncHandle);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPeriodicAdvertisingCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPeriodicAdvertisingCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
