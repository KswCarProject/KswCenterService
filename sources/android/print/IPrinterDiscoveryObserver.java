package android.print;

import android.content.p002pm.ParceledListSlice;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IPrinterDiscoveryObserver extends IInterface {
    void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException;

    void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IPrinterDiscoveryObserver {
        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersAdded(ParceledListSlice printers) throws RemoteException {
        }

        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersRemoved(ParceledListSlice printerIds) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IPrinterDiscoveryObserver {
        private static final String DESCRIPTOR = "android.print.IPrinterDiscoveryObserver";
        static final int TRANSACTION_onPrintersAdded = 1;
        static final int TRANSACTION_onPrintersRemoved = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrinterDiscoveryObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPrinterDiscoveryObserver)) {
                return (IPrinterDiscoveryObserver) iin;
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
                    return "onPrintersAdded";
                case 2:
                    return "onPrintersRemoved";
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
            ParceledListSlice _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? ParceledListSlice.CREATOR.createFromParcel(data) : null;
                    onPrintersAdded(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0 ? ParceledListSlice.CREATOR.createFromParcel(data) : null;
                    onPrintersRemoved(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IPrinterDiscoveryObserver {
            public static IPrinterDiscoveryObserver sDefaultImpl;
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

            @Override // android.print.IPrinterDiscoveryObserver
            public void onPrintersAdded(ParceledListSlice printers) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printers != null) {
                        _data.writeInt(1);
                        printers.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersAdded(printers);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrinterDiscoveryObserver
            public void onPrintersRemoved(ParceledListSlice printerIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerIds != null) {
                        _data.writeInt(1);
                        printerIds.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersRemoved(printerIds);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPrinterDiscoveryObserver impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IPrinterDiscoveryObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
