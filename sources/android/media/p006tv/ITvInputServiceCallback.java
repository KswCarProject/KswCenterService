package android.media.p006tv;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.media.tv.ITvInputServiceCallback */
/* loaded from: classes3.dex */
public interface ITvInputServiceCallback extends IInterface {
    void addHardwareInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

    void addHdmiInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

    void removeHardwareInput(String str) throws RemoteException;

    /* renamed from: android.media.tv.ITvInputServiceCallback$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ITvInputServiceCallback {
        @Override // android.media.p006tv.ITvInputServiceCallback
        public void addHardwareInput(int deviceId, TvInputInfo inputInfo) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputServiceCallback
        public void addHdmiInput(int id, TvInputInfo inputInfo) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputServiceCallback
        public void removeHardwareInput(String inputId) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.media.tv.ITvInputServiceCallback$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ITvInputServiceCallback {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputServiceCallback";
        static final int TRANSACTION_addHardwareInput = 1;
        static final int TRANSACTION_addHdmiInput = 2;
        static final int TRANSACTION_removeHardwareInput = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInputServiceCallback)) {
                return (ITvInputServiceCallback) iin;
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
                    return "addHardwareInput";
                case 2:
                    return "addHdmiInput";
                case 3:
                    return "removeHardwareInput";
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
            TvInputInfo _arg1;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    _arg1 = data.readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel(data) : null;
                    addHardwareInput(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    _arg1 = data.readInt() != 0 ? TvInputInfo.CREATOR.createFromParcel(data) : null;
                    addHdmiInput(_arg02, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    removeHardwareInput(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.media.tv.ITvInputServiceCallback$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ITvInputServiceCallback {
            public static ITvInputServiceCallback sDefaultImpl;
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

            @Override // android.media.p006tv.ITvInputServiceCallback
            public void addHardwareInput(int deviceId, TvInputInfo inputInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    if (inputInfo != null) {
                        _data.writeInt(1);
                        inputInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHardwareInput(deviceId, inputInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputServiceCallback
            public void addHdmiInput(int id, TvInputInfo inputInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (inputInfo != null) {
                        _data.writeInt(1);
                        inputInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHdmiInput(id, inputInfo);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputServiceCallback
            public void removeHardwareInput(String inputId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeHardwareInput(inputId);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputServiceCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ITvInputServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
