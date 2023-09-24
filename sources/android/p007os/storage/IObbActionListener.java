package android.p007os.storage;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* renamed from: android.os.storage.IObbActionListener */
/* loaded from: classes3.dex */
public interface IObbActionListener extends IInterface {
    void onObbResult(String str, int i, int i2) throws RemoteException;

    /* renamed from: android.os.storage.IObbActionListener$Default */
    /* loaded from: classes3.dex */
    public static class Default implements IObbActionListener {
        @Override // android.p007os.storage.IObbActionListener
        public void onObbResult(String filename, int nonce, int status) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.os.storage.IObbActionListener$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IObbActionListener {
        private static final String DESCRIPTOR = "android.os.storage.IObbActionListener";
        static final int TRANSACTION_onObbResult = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IObbActionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IObbActionListener)) {
                return (IObbActionListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "onObbResult";
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
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            onObbResult(_arg0, _arg1, _arg2);
            return true;
        }

        /* renamed from: android.os.storage.IObbActionListener$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements IObbActionListener {
            public static IObbActionListener sDefaultImpl;
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

            @Override // android.p007os.storage.IObbActionListener
            public void onObbResult(String filename, int nonce, int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(filename);
                    _data.writeInt(nonce);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onObbResult(filename, nonce, status);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IObbActionListener impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IObbActionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}