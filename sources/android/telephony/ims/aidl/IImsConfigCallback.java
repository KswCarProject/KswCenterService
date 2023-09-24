package android.telephony.ims.aidl;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsConfigCallback extends IInterface {
    void onIntConfigChanged(int i, int i2) throws RemoteException;

    void onStringConfigChanged(int i, String str) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IImsConfigCallback {
        @Override // android.telephony.ims.aidl.IImsConfigCallback
        public void onIntConfigChanged(int item, int value) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfigCallback
        public void onStringConfigChanged(int item, String value) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IImsConfigCallback {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfigCallback";
        static final int TRANSACTION_onIntConfigChanged = 1;
        static final int TRANSACTION_onStringConfigChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsConfigCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IImsConfigCallback)) {
                return (IImsConfigCallback) iin;
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
                    return "onIntConfigChanged";
                case 2:
                    return "onStringConfigChanged";
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
                    onIntConfigChanged(_arg0, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    onStringConfigChanged(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IImsConfigCallback {
            public static IImsConfigCallback sDefaultImpl;
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

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public void onIntConfigChanged(int item, int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeInt(value);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onIntConfigChanged(item, value);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public void onStringConfigChanged(int item, String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(item);
                    _data.writeString(value);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStringConfigChanged(item, value);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsConfigCallback impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IImsConfigCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
