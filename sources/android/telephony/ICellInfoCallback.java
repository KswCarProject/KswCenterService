package android.telephony;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.ParcelableException;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface ICellInfoCallback extends IInterface {
    void onCellInfo(List<CellInfo> list) throws RemoteException;

    void onError(int i, ParcelableException parcelableException) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements ICellInfoCallback {
        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(List<CellInfo> state) throws RemoteException {
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(int errorCode, ParcelableException detail) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof ICellInfoCallback)) {
                return (ICellInfoCallback) iin;
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
                    return "onCellInfo";
                case 2:
                    return "onError";
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
            ParcelableException _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    List<CellInfo> _arg0 = data.createTypedArrayList(CellInfo.CREATOR);
                    onCellInfo(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = ParcelableException.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    onError(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements ICellInfoCallback {
            public static ICellInfoCallback sDefaultImpl;
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

            @Override // android.telephony.ICellInfoCallback
            public void onCellInfo(List<CellInfo> state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(state);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCellInfo(state);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ICellInfoCallback
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
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(errorCode, detail);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICellInfoCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ICellInfoCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
