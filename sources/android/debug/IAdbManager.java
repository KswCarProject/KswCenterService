package android.debug;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;

/* loaded from: classes.dex */
public interface IAdbManager extends IInterface {
    void allowDebugging(boolean z, String str) throws RemoteException;

    void clearDebuggingKeys() throws RemoteException;

    void denyDebugging() throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements IAdbManager {
        @Override // android.debug.IAdbManager
        public void allowDebugging(boolean alwaysAllow, String publicKey) throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void denyDebugging() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void clearDebuggingKeys() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IAdbManager {
        private static final String DESCRIPTOR = "android.debug.IAdbManager";
        static final int TRANSACTION_allowDebugging = 1;
        static final int TRANSACTION_clearDebuggingKeys = 3;
        static final int TRANSACTION_denyDebugging = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAdbManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAdbManager)) {
                return (IAdbManager) iin;
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
                    return "allowDebugging";
                case 2:
                    return "denyDebugging";
                case 3:
                    return "clearDebuggingKeys";
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
                    String _arg1 = data.readString();
                    allowDebugging(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    denyDebugging();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    clearDebuggingKeys();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IAdbManager {
            public static IAdbManager sDefaultImpl;
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

            @Override // android.debug.IAdbManager
            public void allowDebugging(boolean alwaysAllow, String publicKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(alwaysAllow ? 1 : 0);
                    _data.writeString(publicKey);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allowDebugging(alwaysAllow, publicKey);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void denyDebugging() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().denyDebugging();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void clearDebuggingKeys() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDebuggingKeys();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAdbManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAdbManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
