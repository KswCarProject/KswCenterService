package android.net.lowpan;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ILowpanNetScanCallback extends IInterface {
    void onNetScanBeacon(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException;

    void onNetScanFinished() throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ILowpanNetScanCallback {
        @Override // android.net.lowpan.ILowpanNetScanCallback
        public void onNetScanBeacon(LowpanBeaconInfo beacon) throws RemoteException {
        }

        @Override // android.net.lowpan.ILowpanNetScanCallback
        public void onNetScanFinished() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILowpanNetScanCallback {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanNetScanCallback";
        static final int TRANSACTION_onNetScanBeacon = 1;
        static final int TRANSACTION_onNetScanFinished = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanNetScanCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILowpanNetScanCallback)) {
                return (ILowpanNetScanCallback) iin;
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
                    return "onNetScanBeacon";
                case 2:
                    return "onNetScanFinished";
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
            LowpanBeaconInfo _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = LowpanBeaconInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onNetScanBeacon(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onNetScanFinished();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ILowpanNetScanCallback {
            public static ILowpanNetScanCallback sDefaultImpl;
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

            @Override // android.net.lowpan.ILowpanNetScanCallback
            public void onNetScanBeacon(LowpanBeaconInfo beacon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (beacon != null) {
                        _data.writeInt(1);
                        beacon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetScanBeacon(beacon);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.lowpan.ILowpanNetScanCallback
            public void onNetScanFinished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetScanFinished();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILowpanNetScanCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILowpanNetScanCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
