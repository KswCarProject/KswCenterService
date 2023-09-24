package android.net.lowpan;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface ILowpanEnergyScanCallback extends IInterface {
    void onEnergyScanFinished() throws RemoteException;

    void onEnergyScanResult(int i, int i2) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements ILowpanEnergyScanCallback {
        @Override // android.net.lowpan.ILowpanEnergyScanCallback
        public void onEnergyScanResult(int channel, int rssi) throws RemoteException {
        }

        @Override // android.net.lowpan.ILowpanEnergyScanCallback
        public void onEnergyScanFinished() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ILowpanEnergyScanCallback {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanEnergyScanCallback";
        static final int TRANSACTION_onEnergyScanFinished = 2;
        static final int TRANSACTION_onEnergyScanResult = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanEnergyScanCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILowpanEnergyScanCallback)) {
                return (ILowpanEnergyScanCallback) iin;
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
                    return "onEnergyScanResult";
                case 2:
                    return "onEnergyScanFinished";
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
                    int _arg1 = data.readInt();
                    onEnergyScanResult(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onEnergyScanFinished();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements ILowpanEnergyScanCallback {
            public static ILowpanEnergyScanCallback sDefaultImpl;
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

            @Override // android.net.lowpan.ILowpanEnergyScanCallback
            public void onEnergyScanResult(int channel, int rssi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(channel);
                    _data.writeInt(rssi);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnergyScanResult(channel, rssi);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.lowpan.ILowpanEnergyScanCallback
            public void onEnergyScanFinished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnergyScanFinished();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ILowpanEnergyScanCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ILowpanEnergyScanCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
