package android.p008se.omapi;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p008se.omapi.ISecureElementSession;

/* renamed from: android.se.omapi.ISecureElementReader */
/* loaded from: classes3.dex */
public interface ISecureElementReader extends IInterface {
    void closeSessions() throws RemoteException;

    boolean isSecureElementPresent() throws RemoteException;

    ISecureElementSession openSession() throws RemoteException;

    /* renamed from: android.se.omapi.ISecureElementReader$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ISecureElementReader {
        @Override // android.p008se.omapi.ISecureElementReader
        public boolean isSecureElementPresent() throws RemoteException {
            return false;
        }

        @Override // android.p008se.omapi.ISecureElementReader
        public ISecureElementSession openSession() throws RemoteException {
            return null;
        }

        @Override // android.p008se.omapi.ISecureElementReader
        public void closeSessions() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.se.omapi.ISecureElementReader$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ISecureElementReader {
        private static final String DESCRIPTOR = "android.se.omapi.ISecureElementReader";
        static final int TRANSACTION_closeSessions = 3;
        static final int TRANSACTION_isSecureElementPresent = 1;
        static final int TRANSACTION_openSession = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementReader asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISecureElementReader)) {
                return (ISecureElementReader) iin;
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
                    return "isSecureElementPresent";
                case 2:
                    return "openSession";
                case 3:
                    return "closeSessions";
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
                    boolean isSecureElementPresent = isSecureElementPresent();
                    reply.writeNoException();
                    reply.writeInt(isSecureElementPresent ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    ISecureElementSession _result = openSession();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result != null ? _result.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    closeSessions();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.se.omapi.ISecureElementReader$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ISecureElementReader {
            public static ISecureElementReader sDefaultImpl;
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

            @Override // android.p008se.omapi.ISecureElementReader
            public boolean isSecureElementPresent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSecureElementPresent();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p008se.omapi.ISecureElementReader
            public ISecureElementSession openSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openSession();
                    }
                    _reply.readException();
                    ISecureElementSession _result = ISecureElementSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p008se.omapi.ISecureElementReader
            public void closeSessions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSessions();
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISecureElementReader impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISecureElementReader getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
