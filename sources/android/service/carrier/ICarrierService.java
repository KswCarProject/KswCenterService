package android.service.carrier;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.ResultReceiver;

/* loaded from: classes3.dex */
public interface ICarrierService extends IInterface {
    void getCarrierConfig(CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ICarrierService {
        @Override // android.service.carrier.ICarrierService
        public void getCarrierConfig(CarrierIdentifier id, ResultReceiver result) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ICarrierService {
        private static final String DESCRIPTOR = "android.service.carrier.ICarrierService";
        static final int TRANSACTION_getCarrierConfig = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICarrierService)) {
                return (ICarrierService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "getCarrierConfig";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            CarrierIdentifier _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = CarrierIdentifier.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            ResultReceiver _arg1 = data.readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel(data) : null;
            getCarrierConfig(_arg0, _arg1);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ICarrierService {
            public static ICarrierService sDefaultImpl;
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

            @Override // android.service.carrier.ICarrierService
            public void getCarrierConfig(CarrierIdentifier id, ResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getCarrierConfig(id, result);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICarrierService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICarrierService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
