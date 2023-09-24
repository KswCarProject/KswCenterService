package android.net.wifi.hotspot2;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IProvisioningCallback extends IInterface {
    void onProvisioningComplete() throws RemoteException;

    void onProvisioningFailure(int i) throws RemoteException;

    void onProvisioningStatus(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IProvisioningCallback {
        @Override // android.net.wifi.hotspot2.IProvisioningCallback
        public void onProvisioningFailure(int status) throws RemoteException {
        }

        @Override // android.net.wifi.hotspot2.IProvisioningCallback
        public void onProvisioningStatus(int status) throws RemoteException {
        }

        @Override // android.net.wifi.hotspot2.IProvisioningCallback
        public void onProvisioningComplete() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IProvisioningCallback {
        private static final String DESCRIPTOR = "android.net.wifi.hotspot2.IProvisioningCallback";
        static final int TRANSACTION_onProvisioningComplete = 3;
        static final int TRANSACTION_onProvisioningFailure = 1;
        static final int TRANSACTION_onProvisioningStatus = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IProvisioningCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IProvisioningCallback)) {
                return (IProvisioningCallback) iin;
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
                    return "onProvisioningFailure";
                case 2:
                    return "onProvisioningStatus";
                case 3:
                    return "onProvisioningComplete";
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
                    onProvisioningFailure(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onProvisioningStatus(_arg02);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onProvisioningComplete();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IProvisioningCallback {
            public static IProvisioningCallback sDefaultImpl;
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

            @Override // android.net.wifi.hotspot2.IProvisioningCallback
            public void onProvisioningFailure(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningFailure(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.hotspot2.IProvisioningCallback
            public void onProvisioningStatus(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningStatus(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.hotspot2.IProvisioningCallback
            public void onProvisioningComplete() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningComplete();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IProvisioningCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IProvisioningCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
