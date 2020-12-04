package android.net.lowpan;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanNetScanCallback extends IInterface {
    void onNetScanBeacon(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException;

    void onNetScanFinished() throws RemoteException;

    public static class Default implements ILowpanNetScanCallback {
        public void onNetScanBeacon(LowpanBeaconInfo beacon) throws RemoteException {
        }

        public void onNetScanFinished() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
            if (iin == null || !(iin instanceof ILowpanNetScanCallback)) {
                return new Proxy(obj);
            }
            return (ILowpanNetScanCallback) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            LowpanBeaconInfo _arg0;
            if (code != 1598968902) {
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
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ILowpanNetScanCallback {
            public static ILowpanNetScanCallback sDefaultImpl;
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
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNetScanBeacon(beacon);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onNetScanFinished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onNetScanFinished();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILowpanNetScanCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ILowpanNetScanCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
