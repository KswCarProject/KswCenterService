package android.security.keystore;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.security.keymaster.ExportResult;

/* loaded from: classes3.dex */
public interface IKeystoreExportKeyCallback extends IInterface {
    void onFinished(ExportResult exportResult) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IKeystoreExportKeyCallback {
        @Override // android.security.keystore.IKeystoreExportKeyCallback
        public void onFinished(ExportResult result) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IKeystoreExportKeyCallback {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreExportKeyCallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreExportKeyCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeystoreExportKeyCallback)) {
                return (IKeystoreExportKeyCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onFinished";
            }
            return null;
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ExportResult _arg0;
            if (code != 1) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }
            data.enforceInterface(DESCRIPTOR);
            if (data.readInt() != 0) {
                _arg0 = ExportResult.CREATOR.createFromParcel(data);
            } else {
                _arg0 = null;
            }
            onFinished(_arg0);
            return true;
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IKeystoreExportKeyCallback {
            public static IKeystoreExportKeyCallback sDefaultImpl;
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

            @Override // android.security.keystore.IKeystoreExportKeyCallback
            public void onFinished(ExportResult result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(result);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IKeystoreExportKeyCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IKeystoreExportKeyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
