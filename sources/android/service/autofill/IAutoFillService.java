package android.service.autofill;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.service.autofill.IFillCallback;
import android.service.autofill.ISaveCallback;

/* loaded from: classes3.dex */
public interface IAutoFillService extends IInterface {
    void onConnectedStateChanged(boolean z) throws RemoteException;

    void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException;

    void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IAutoFillService {
        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean connected) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IAutoFillService {
        private static final String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAutoFillService)) {
                return (IAutoFillService) iin;
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
                    return "onConnectedStateChanged";
                case 2:
                    return "onFillRequest";
                case 3:
                    return "onSaveRequest";
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
                    boolean _arg0 = data.readInt() != 0;
                    onConnectedStateChanged(_arg0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    FillRequest _arg02 = data.readInt() != 0 ? FillRequest.CREATOR.createFromParcel(data) : null;
                    IFillCallback _arg1 = IFillCallback.Stub.asInterface(data.readStrongBinder());
                    onFillRequest(_arg02, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    SaveRequest _arg03 = data.readInt() != 0 ? SaveRequest.CREATOR.createFromParcel(data) : null;
                    ISaveCallback _arg12 = ISaveCallback.Stub.asInterface(data.readStrongBinder());
                    onSaveRequest(_arg03, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IAutoFillService {
            public static IAutoFillService sDefaultImpl;
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

            @Override // android.service.autofill.IAutoFillService
            public void onConnectedStateChanged(boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(connected ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectedStateChanged(connected);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFillRequest(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSaveRequest(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAutoFillService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAutoFillService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
