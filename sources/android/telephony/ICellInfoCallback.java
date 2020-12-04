package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelableException;
import android.os.RemoteException;
import java.util.List;

public interface ICellInfoCallback extends IInterface {
    void onCellInfo(List<CellInfo> list) throws RemoteException;

    void onError(int i, ParcelableException parcelableException) throws RemoteException;

    public static class Default implements ICellInfoCallback {
        public void onCellInfo(List<CellInfo> list) throws RemoteException {
        }

        public void onError(int errorCode, ParcelableException detail) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICellInfoCallback {
        private static final String DESCRIPTOR = "android.telephony.ICellInfoCallback";
        static final int TRANSACTION_onCellInfo = 1;
        static final int TRANSACTION_onError = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICellInfoCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICellInfoCallback)) {
                return new Proxy(obj);
            }
            return (ICellInfoCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCellInfo";
                case 2:
                    return "onError";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelableException _arg1;
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onCellInfo(data.createTypedArrayList(CellInfo.CREATOR));
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg0 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = ParcelableException.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        onError(_arg0, _arg1);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ICellInfoCallback {
            public static ICellInfoCallback sDefaultImpl;
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

            public void onCellInfo(List<CellInfo> state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(state);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCellInfo(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int errorCode, ParcelableException detail) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    if (detail != null) {
                        _data.writeInt(1);
                        detail.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(errorCode, detail);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICellInfoCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ICellInfoCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
