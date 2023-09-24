package android.bluetooth.p000le;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* renamed from: android.bluetooth.le.IScannerCallback */
/* loaded from: classes.dex */
public interface IScannerCallback extends IInterface {
    void onBatchScanResults(List<ScanResult> list) throws RemoteException;

    void onFoundOrLost(boolean z, ScanResult scanResult) throws RemoteException;

    void onScanManagerErrorCallback(int i) throws RemoteException;

    void onScanResult(ScanResult scanResult) throws RemoteException;

    void onScannerRegistered(int i, int i2) throws RemoteException;

    /* renamed from: android.bluetooth.le.IScannerCallback$Default */
    /* loaded from: classes.dex */
    public static class Default implements IScannerCallback {
        @Override // android.bluetooth.p000le.IScannerCallback
        public void onScannerRegistered(int status, int scannerId) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IScannerCallback
        public void onScanResult(ScanResult scanResult) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IScannerCallback
        public void onBatchScanResults(List<ScanResult> batchResults) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IScannerCallback
        public void onFoundOrLost(boolean onFound, ScanResult scanResult) throws RemoteException {
        }

        @Override // android.bluetooth.p000le.IScannerCallback
        public void onScanManagerErrorCallback(int errorCode) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.bluetooth.le.IScannerCallback$Stub */
    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IScannerCallback {
        private static final String DESCRIPTOR = "android.bluetooth.le.IScannerCallback";
        static final int TRANSACTION_onBatchScanResults = 3;
        static final int TRANSACTION_onFoundOrLost = 4;
        static final int TRANSACTION_onScanManagerErrorCallback = 5;
        static final int TRANSACTION_onScanResult = 2;
        static final int TRANSACTION_onScannerRegistered = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IScannerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IScannerCallback)) {
                return (IScannerCallback) iin;
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
                    return "onScannerRegistered";
                case 2:
                    return "onScanResult";
                case 3:
                    return "onBatchScanResults";
                case 4:
                    return "onFoundOrLost";
                case 5:
                    return "onScanManagerErrorCallback";
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
            ScanResult _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    onScannerRegistered(_arg0, data.readInt());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg1 = data.readInt() != 0 ? ScanResult.CREATOR.createFromParcel(data) : null;
                    onScanResult(_arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    List<ScanResult> _arg02 = data.createTypedArrayList(ScanResult.CREATOR);
                    onBatchScanResults(_arg02);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg03 = data.readInt() != 0;
                    _arg1 = data.readInt() != 0 ? ScanResult.CREATOR.createFromParcel(data) : null;
                    onFoundOrLost(_arg03, _arg1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    onScanManagerErrorCallback(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.bluetooth.le.IScannerCallback$Stub$Proxy */
        /* loaded from: classes.dex */
        private static class Proxy implements IScannerCallback {
            public static IScannerCallback sDefaultImpl;
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

            @Override // android.bluetooth.p000le.IScannerCallback
            public void onScannerRegistered(int status, int scannerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(scannerId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScannerRegistered(status, scannerId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.p000le.IScannerCallback
            public void onScanResult(ScanResult scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scanResult != null) {
                        _data.writeInt(1);
                        scanResult.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScanResult(scanResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.p000le.IScannerCallback
            public void onBatchScanResults(List<ScanResult> batchResults) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(batchResults);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBatchScanResults(batchResults);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.p000le.IScannerCallback
            public void onFoundOrLost(boolean onFound, ScanResult scanResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(onFound ? 1 : 0);
                    if (scanResult != null) {
                        _data.writeInt(1);
                        scanResult.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFoundOrLost(onFound, scanResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.bluetooth.p000le.IScannerCallback
            public void onScanManagerErrorCallback(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onScanManagerErrorCallback(errorCode);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IScannerCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IScannerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
