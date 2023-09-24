package android.service.euicc;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes3.dex */
public interface IOtaStatusChangedCallback extends IInterface {
    void onOtaStatusChanged(int i) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IOtaStatusChangedCallback {
        @Override // android.service.euicc.IOtaStatusChangedCallback
        public void onOtaStatusChanged(int status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IOtaStatusChangedCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IOtaStatusChangedCallback";
        static final int TRANSACTION_onOtaStatusChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOtaStatusChangedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IOtaStatusChangedCallback)) {
                return (IOtaStatusChangedCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onOtaStatusChanged";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            int _arg0 = data.readInt();
            onOtaStatusChanged(_arg0);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IOtaStatusChangedCallback {
            public static IOtaStatusChangedCallback sDefaultImpl;
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

            @Override // android.service.euicc.IOtaStatusChangedCallback
            public void onOtaStatusChanged(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOtaStatusChanged(status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOtaStatusChangedCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IOtaStatusChangedCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
